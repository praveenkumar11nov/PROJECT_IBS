package com.bcits.bfm.serviceImpl.facilityManagement;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bcits.bfm.model.AssetWarranty;
import com.bcits.bfm.model.JobCalender;
import com.bcits.bfm.model.JobCards;
import com.bcits.bfm.model.JobTypes;
import com.bcits.bfm.model.MaintainanceTypes;
import com.bcits.bfm.model.Users;
import com.bcits.bfm.service.customerOccupancyManagement.PersonService;
import com.bcits.bfm.service.facilityManagement.AssetWarrantyService;
import com.bcits.bfm.service.facilityManagement.JobCalenderService;
import com.bcits.bfm.service.facilityManagement.JobCardsService;
import com.bcits.bfm.service.facilityManagement.JobTypesService;
import com.bcits.bfm.service.facilityManagement.MaintainanceTypesService;
import com.bcits.bfm.service.userManagement.DepartmentService;
import com.bcits.bfm.service.userManagement.UsersService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;


@Repository
public class AssetWarrantyServiceImpl extends GenericServiceImpl<AssetWarranty> implements AssetWarrantyService
{
	static Logger logger = LoggerFactory.getLogger(AssetWarrantyServiceImpl.class);
	
	@Autowired
	private JobCalenderService jobCalenderService;
	
	@Autowired
	private JobTypesService jobTypesService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private MaintainanceTypesService maintainanceTypesService; 
	
	@Autowired
	private JobCardsService jobCardsService;
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private PersonService personService;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AssetWarranty> getAllFields(int assetId) {
		return entityManager.createNamedQuery("AssetWarranty.getAllField").setParameter("assetId", assetId).getResultList();
	}

	@SuppressWarnings({ "unchecked", "serial" })
	@Override
	public List<?> setResponse(int assetId) {

		List<AssetWarranty> list =entityManager.createNamedQuery("AssetWarranty.findAll").setParameter("assetId", assetId).getResultList();
		List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();    
		for (final AssetWarranty record :list) {
			response.add(new HashMap<String, Object>() {{
				put("awId", record.getAwId());
				put("assetId", record.getAssetId());
				put("assetName", record.getAsset().getAssetName());
				java.util.Date warrantyFromUtil = record.getWarrantyFromDate();
				java.sql.Date warrantyFromSql = new java.sql.Date(warrantyFromUtil.getTime());
				put("warrantyFromDate", warrantyFromSql);
				java.util.Date warrantyToUtil = record.getWarrantyToDate();
				java.sql.Date warrantyToSql = new java.sql.Date(warrantyToUtil.getTime());
				put("warrantyToDate", warrantyToSql);
				put("warrantyType", record.getWarrantyType());
				put("warrantyValid", record.getWarrantyValid());
				put("createdBy", record.getCreatedBy());
				put("lastUpdatedBy", record.getLastUpdatedBy());
				java.util.Date lastUpdatedDtUtil = record.getLastUpdatedDate();
				java.sql.Date lastUpdatedDtSql = new java.sql.Date(lastUpdatedDtUtil.getTime());
				put("lastUpdatedDate", lastUpdatedDtSql);
			}});
		}
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void warrantyJCCron() {
		
		logger.info("IML OF CRON");
			
		List<AssetWarranty> assetWarrantyList=entityManager.createNamedQuery("AssetWarranty.getAssetWarrantyListBwDates").getResultList();
		if(assetWarrantyList.size()>0 && !assetWarrantyList.isEmpty()){
			for (AssetWarranty assetWarranty : assetWarrantyList) {

				String duplicateCheck = "select o from JobCalender o where o.jobNumber='AW"+assetWarranty.getAwId()+"'";
				List<JobCalender> jcList = jobCalenderService.executeSimpleQuery(duplicateCheck);
				if(jcList.isEmpty() && jcList.size()==0){
					JobCalender jobCalender =  new JobCalender();
					jobCalender.setAssetMaintenanceSchedule(null);
					jobCalender.setDescription("Asset Warranty Renewal");							
					jobCalender.setStart(new Date());
					jobCalender.setEnd(assetWarranty.getWarrantyToDate());
					jobCalender.setJobAssets(assetWarranty.getAsset().getAssetId()+"");
					jobCalender.setIsAllDay(false);
					jobCalender.setScheduleType(1);
					jobCalender.setTitle(assetWarranty.getAsset().getAssetName()+" - Warranty Renewal");
					jobCalender.setJobNumber("AW"+assetWarranty.getAwId());
					jobCalender.setJobPriority("NORMAL");
					List<JobTypes> jt=jobTypesService.executeSimpleQuery("select o from JobTypes o");
					if(jt.size()>0){
						jobCalender.setJobTypes(jt.get(0));
						jobCalender.setExpectedDays(jt.get(0).getJtSla());
					}
					jobCalender.setMaintainanceDepartment(assetWarranty.getAsset().getMaintainanceDepartment().getDepartment());
					
					List<MaintainanceTypes> mList = entityManager.createNamedQuery("MaintainanceTypes.getMaintainanceTypesForJobCalendar").getResultList();
					
					if(mList.size()>0){
						for (MaintainanceTypes maintainanceTypes : mList) {
							if(maintainanceTypes.getMaintainanceType().equalsIgnoreCase("INSPECTION")){
								jobCalender.setMaintainanceTypes(maintainanceTypes);	
								break;
							}else{
								jobCalender.setMaintainanceTypes(maintainanceTypes);
							}
						}
					}
								
					
					List<Users> list = entityManager.createNamedQuery("AssetWarranty.getUsersListBasedOnDeptId").setParameter("department", assetWarranty.getAsset().getMaintainanceDepartment().getDepartment().getDept_Id()).getResultList();


					
					logger.info("\n\nUSERS LIST >>>>>>>>>>>>> "+list.size());
					
					if(list.size()>0)
						jobCalender.setPerson(personService.find(assetWarranty.getAsset().getMaintainanceDepartment().getUsers().getPerson().getPersonId()));
					
					jobCalenderService.save(jobCalender);
					
					JobCards jcards=new JobCards();				
					jcards.setJobNo(jobCalender.getJobNumber());
					jcards.setJobName(jobCalender.getTitle());
					/*jcards.setJobGroup(jc.getJobGroup());*/
					jcards.setJobDescription(jobCalender.getDescription());
					Timestamp date=new Timestamp(new Date().getTime());
					jcards.setJobDt(date);
					jcards.setJobDepartment(jobCalender.getMaintainanceDepartment());
					jcards.setPerson(jobCalender.getPerson());
					jcards.setJobTypes(jobCalender.getJobTypes());
					jcards.setJobExpectedSla(jobCalender.getExpectedDays());
					jcards.setJobAssets(jobCalender.getJobAssets());
					jcards.setJobPriority(jobCalender.getJobPriority());
					jcards.setMaintainanceTypes(jobCalender.getMaintainanceTypes());
					jcards.setCreatedBy("Auto");
					jcards.setLastUpdatedBy("Auto");			
					jcards.setJobGeneratedby("USER DEFINED");
					jcards.setStatus("INIT");
					jcards.setSuspendStatus("RESUME");
					jcards.setJobCalender(jobCalender);
					
					jobCardsService.saveRecords(jcards);
				}
			}
		}
		
	}

}
