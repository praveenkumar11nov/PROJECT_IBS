package com.bcits.bfm.serviceImpl.facilityManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bcits.bfm.model.Asset;
import com.bcits.bfm.model.AssetOwnerShip;
import com.bcits.bfm.service.facilityManagement.AssetService;
import com.bcits.bfm.serviceImpl.GenericServiceImpl;


@Repository
public class AssetServiceImpl extends GenericServiceImpl<Asset> implements AssetService
{

	@SuppressWarnings("unchecked")
	@Override
	public List<Asset> findAll() {
		return entityManager.createNamedQuery("Asset.findAll").getResultList();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAllAssetOnCatId(int assetCatId) {
		return entityManager.createNamedQuery("Asset.getAllAssetOnCatId").setParameter("assetCatId", assetCatId).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAllAssetOnLocId(int assetLocId) {
		return entityManager.createNamedQuery("Asset.getAllAssetOnLocId").setParameter("assetLocId", assetLocId).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getAllAssetForAll() {
		return entityManager.createNamedQuery("Asset.getAllAssetsForAll").getResultList();
	}
	
	@SuppressWarnings({ "serial", "unchecked" })
	@Override
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Map<String, Object>> setResponse() 
	{
		
		List<Asset> list =  entityManager.createNamedQuery("Asset.findAllList").getResultList();
		List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();    
		for (final Iterator<?> i = list.iterator(); i.hasNext();) {
			response.add(new HashMap<String, Object>() {{
				final Object[] values = (Object[])i.next();

				put("assetId", (Integer)values[0]);
				put("assetName", (String)values[1]);
				put("assetDesc", (String)values[2]);
				put("assetPoDetail", (String)values[3]);
				put("assetCatId", (Integer)values[4]);
				put("assetCatHierarchy", (String)values[5]);
				put("assetType", (String)values[6]);
				put("ownerShip", (String)values[7]);
				put("assetLocId", (Integer)values[8]);
				put("assetLocHierarchy", (String)values[9]);
				put("assetGeoTag", (String)values[10]);
				put("assetCondition", (String)values[11]);
				put("drGroupId", (Integer)values[12]);
				if((String)values[13]!=null){
				put("assetNotes", (String)values[13]);}
				else{
				put("assetNotes", "N/A");}
				put("assetManufacturer", (String)values[14]);
				put("name", (String)values[15]);
				put("ccId", (Integer)values[16]);
				put("mtDpIt", (Integer)values[17]);
				put("department", (String)values[18]);
				
				if((Date)values[19]!=null){
					java.util.Date assetYearUtil =  (Date)values[19];
					java.sql.Date assetYearSql = new java.sql.Date(assetYearUtil.getTime());
					put("assetYear",assetYearSql);
				}
				put("assetModelNo", (String)values[21]);
				put("assetManufacturerSlNo", (String)values[22]);
				put("assetTag", (String)values[23]);
				put("assetVendorId", (Integer)values[24]);
				put("useFullLife", (String)values[25]);
				put("assetStatus", (String)values[26]);
				put("vendorName", (String)values[27]+" "+(String)values[28]);
				put("createdBy", (String)values[29]);
				put("updatedBy", (String)values[30]);
				if((Date)values[20]!=null){
					java.util.Date lastUpdatedDtUtil = (Date)values[20];
					java.sql.Date lastUpdatedDtSql = new java.sql.Date(lastUpdatedDtUtil.getTime());
					put("lastUpdatedDate", lastUpdatedDtSql);
				}
				
				if((Date)values[31]!=null){
					java.util.Date purchaseDateUtil = (Date)values[31];
					java.sql.Date purchaseDateSql = new java.sql.Date(purchaseDateUtil.getTime());
					put("purchaseDate", purchaseDateSql);
				}else{
					put("purchaseDate", "");
				}
				
				if((Date)values[32]!=null){
					java.util.Date warrantyTillUtil = (Date)values[32];
					java.sql.Date warrantyTillSql = new java.sql.Date(warrantyTillUtil.getTime());
					put("warrantyTill", warrantyTillSql);
				}else{
					put("warrantyTill", "");
				}
				
				if((Date)values[33]!=null){
					java.util.Date assetLifeExpiryUtil = (Date)values[33];
					java.sql.Date  assetLifeExpirySql = new java.sql.Date( assetLifeExpiryUtil.getTime());
					put("assetLifeExpiry", assetLifeExpirySql);
				}else{
					put("assetLifeExpiry", "");
				}
				
				put("supplier", (String)values[34]);
				
				if ((String)values[35]!=null){
					put("owner",(String)values[35]);
				}else{
					put("owner","Not Assigned");
				}
				
				if ((String)values[36]!=null){
					put("maintowner",(String)values[36]);
				}else{
					put("maintowner","Not Assigned");
				}
			}});
		}
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Asset> getAssetsonCatIdAndLocId(int assetCatId, int assetLocId) {
		return entityManager.createNamedQuery("Asset.getAssetsonCatIdAndLocId").setParameter("assetCatId", assetCatId).setParameter("assetLocId", assetLocId).getResultList();
	}

	@Override
	public void setAssetStatus(int assetId, String operation,
			HttpServletResponse response) {

		try
		{
			PrintWriter out = response.getWriter();

			
			if(StringUtils.containsIgnoreCase(operation, "Approved"))
				entityManager.createNamedQuery("Asset.setStatus").setParameter("assetStatus", operation).setParameter("assetId", assetId).executeUpdate();
			else if(StringUtils.containsIgnoreCase(operation, "Completed"))
				entityManager.createNamedQuery("Asset.setStatus").setParameter("assetStatus", operation).setParameter("assetId", assetId).executeUpdate();
			else
				entityManager.createNamedQuery("Asset.setStatus").setParameter("assetStatus", operation).setParameter("assetId", assetId).executeUpdate();
			
			out.write(operation);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/** Update the notices document on notice id
	 * @param snId 						Staff notice id
	 * @param noticeDocument 			document to upload
	 * @param noticeDocumentType 		Type of ducument
	 * @see com.bcits.bfm.service.facilityManagement.StaffNoticesService#uploadNoticeOnId(int, java.sql.Blob, java.lang.String)
	 * @return null
	 */
	@Override
	public void uploadAssetOnId(int assetId,Blob assetDocument,String assetDocumentType) {
		entityManager.createNamedQuery("Asset.uploadAssetOnId")
				.setParameter("assetId", assetId)
				.setParameter("assetDocument", assetDocument)
				.setParameter("assetDocumentType", assetDocumentType)
				.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findAllAsset() {
	
		return entityManager.createNamedQuery("Asset.findAllList").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Asset> getDataForViewReport() {
		
		return entityManager.createNamedQuery("Asset.getDataForViewReport").getResultList();
	}
}
