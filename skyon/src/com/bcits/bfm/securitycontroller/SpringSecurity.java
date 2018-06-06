package com.bcits.bfm.securitycontroller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bcits.bfm.serviceImpl.facilityManagement.ParkingSlotsAllotmentServiceImpl;


@Controller
public class SpringSecurity {
	private static final Log logger = LogFactory.getLog(ParkingSlotsAllotmentServiceImpl.class);

	@RequestMapping(value = {
			"/userManagement/users/createButton",
			"/userManagement/users/updateButton",
			"/userManagement/users/resetPasswordButton",
			"/userManagement/groups/createButton",
			"/userManagement/groups/updateButton",
			"/userManagement/groups/deleteButton",
			"/userManagement/roles/createButton",
			"/userManagement/roles/updateButton",
			"/userManagement/roles/deleteButton",
			"/userManagement/designation/createButton",
			"/userManagement/designation/updateButton",
			"/userManagement/designation/deleteButton",
			"/userManagement/department/createButton",
			"/userManagement/department/updateButton",
			"/userManagement/department/deleteButton",
			"/userRoles/assignButton", 
			"/userRoles/unassignButton",
			"/userGroups/assignButton", 
			"/userGroups/unassignButton",
			"/userManagement/users/deleteButton",
			"/patrolTracks/deleteButton",
			"/patrolTrackPoint/deleteButton",
			"/patrolTrackStaff/deleteButton",
			"/visitorvisits/deleteButton"})
	public void securityMethod(String model, HttpServletResponse res)throws IOException {
			logger.info("Sending Response to JSP as "+model);
	}
	
	@RequestMapping(value = {
			"/parkingManagement/parkingslots/updateButton",
			"/parkingManagement/parkingslots/createButton",
			"/parkingManagement/parkingslots/deleteButton",
			"/parkingManagement/parkingslotsAllocation/createButton",
			"/parkingManagement/parkingslotsAllocation/statusButton",
			"/parkingManagement/parkingslotsallocation/deleteButton",
			"/parkingManagement/vehicles/createButton",
			"/parkingManagement/vehicles/updateButton",
			"/parkingManagement/vehicles/deleteButton",
			"/parkingManagement/wrongparking/createButton",
			"/parkingManagement/wrongparking/updateButton",
			"/parkingManagement/WrongParking/deleteButton"
			})			
	public void parkingManagementsecurityMethod(String model, HttpServletResponse res)throws IOException {
		logger.info("Sending Response to JSP as "+model);
	}
	
	@RequestMapping(value = {
			"/maintainance/jobcalender/addeditButton",
			"/maintainance/jobcalender/deleteButton",
			"/maintainance/jobcards/addButton",
			"/maintainance/jobcards/editButton",
			"/maintainance/jobcards/deleteButton",
			"/maintainance/jobcards/totalcostButton",
			"/maintainance/jobcards/jobstatusButton",
			"/maintainance/jobcards/suspendstatusButton",
			"/maintainance/toolmaster/addButton",
			"/maintainance/toolmaster/editButton",
			"/maintainance/toolmaster/deleteButton",
			"/maintainance/maintainancetype/addButton",
			"/maintainance/maintainancetype/editButton",
			"/maintainance/maintainancetype/deleteButton",
			"/maintainance/jobtype/addButton",
			"/maintainance/jobtype/editButton",
			"/maintainance/jobtype/deleteButton",
			"/maintainance/department/assignButton",
			"/maintainance/department/unassignButton",
			"/jobobjective/createbutton",
			"/jobobjective/editbutton",
			"/jobobjective/deletebutton",
			"/jobnotes/createbutton",
			"/jobnotes/editbutton",
			"/jobnotes/deletebutton",
			"/jobnotification/createbutton",
			"/jobnotification/editbutton",
			"/jobnotification/deletebutton",
			"/jobmaterial/createbutton",
			"/jobmaterial/editbutton",
			"/jobmaterial/deletebutton",
			"/jobteam/createbutton",
			"/jobteam/editbutton",
			"/jobteam/deletebutton",
			"/labourtask/createbutton",
			"/labourtask/editbutton",
			"/labourtask/deletebutton",
			"/jobtools/createbutton",
			"/jobtools/editbutton",
			"/jobtools/deletebutton",
			"/jobdocuments/createbutton",
			"/jobdocuments/editbutton",
			"/jobdocuments/deletebutton",
			
	})			
	public void maintainancesecurityMethod(String model, HttpServletResponse res)throws IOException {
		logger.info("Sending Response to JSP as "+model);
	}
	
	@RequestMapping(value = {
			"/mailroomManagement/mailroom/createButton",
			"/mailroomManagement/mailroom/updateButton",
			"/mailroomManagement/mailroom/deleteButton",
			"/mailroomManagement/mailroom/selectSingleCheckBox",
			"/mailroomManagement/mailroom/selectAllCheckBox",
			"/mailroomManagement/mailroomDelivery/updateButton",
			"/mailroomManagement/mailroomDelivery/selectSingleCheckBox",
			"/mailroomManagement/mailroomDelivery/selectAllCheckBox",
			})			
	public void mailroomsecurityMethod(String model, HttpServletResponse res)throws IOException {
			logger.info("Sending Response to JSP as "+model);
	}
	
	@RequestMapping(value = {
			"/timeandattendence/patroltarcks/createButton",
			"/timeandattendence/patroltracks/updateButton",
			"/timeandattendence/patroltarcks/deleteButton",
			"/timeandattendence/patroltarcks/statusButton",
			"/timeandattendence/patroltarckpoints/createButton",
			"/timeandattendence/patroltrackpoints/updateButton",
			"/timeandattendence/patroltarckpoints/statusButton",
			"/timeandattendence/patroltarckpoints/deleteButton",
			"/timeandattendence/patrolTrackStaff/updateButton",
			"/timeandattendence/patrolTrackStaff/createButton",
			"/timeandattendence/patrolTrackStaff/deleteButton",
			"/timeandattendence/patrolTrackStaff/statusButton",
			"/timeandattendence/patrolRoleSettings/createButton",
			"/timeandattendence/patrolRoleSettings/deleteButton",
			"/timeandattendence/patrolRoleSettings/statusButton"
			})			
	public void timeandattendencesecurityMethod(String model, HttpServletResponse res)throws IOException {
		logger.info("Sending Response to JSP as "+model);
	}
	@RequestMapping(value = {
			"/conciergeManagement/conciergeServices/createButton",
			"/conciergeManagement/conciergeServices/updateButton",
			"/conciergeManagement/conciergeServices/deleteButton",
			"/conciergeManagement/conciergeServices/statusButton",
			"/conciergeManagement/conciergeServices/addOrEditEndDate",
			"/conciergeManagement/conciergeVendors/createButton",
			"/conciergeManagement/conciergeVendors/updateButton",
			"/conciergeManagement/conciergeVendors/statusButton",
			"/conciergeManagement/conciergeVendors/address/createButton",
			"/conciergeManagement/conciergeVendors/address/updateButton" ,
			"/conciergeManagement/conciergeVendors/address/deleteButton",
			"/conciergeVendor/address/delete",
			"/conciergeManagement/conciergeVendors/contact/createButton",
			"/conciergeManagement/conciergeVendors/contact/updateButton",
			"/conciergeManagement/conciergeVendors/contact/deleteButton",
			"/conciergeVendor/contact/delete",
			"/conciergeManagement/conciergeVendors/documents/createButton",
			"/conciergeManagement/conciergeVendors/documents/updateButton",
			"/conciergeManagement/conciergeVendors/documents/viewButton",
			"/conciergeManagement/conciergeVendors/documents/selectFile",
			"/conciergeManagement/conciergeVendors/documents/deleteButton",
			"/conciergeVendor/ownerDocument/delete",
			"/conciergeManagement/conciergeVendors/commentsRating/createButton" ,
			"/conciergeManagement/conciergeVendors/commentsRating/updateButton",
			"/conciergeManagement/conciergeVendors/commentsRating/deleteButton",
			"/conciergeVendors/commentRate/delete",
			"/conciergeManagement/conciergeVendors/vendorOtherDetails/updateButton",					
			
			"/conciergeManagement/vendorService/createButton",
			"/conciergeManagement/vendorService/updateButton",
			"/conciergeManagement/vendorService/deleteButton",
			"/conciergeManagement/vendorService/statusButton",
			"/conciergeManagement/vendorService/addOrEditEndDate",
			"/conciergeManagement/vendorService/serviceCharge/createButton",
			"/conciergeManagement/vendorService/serviceCharge/updateButton",
			"/conciergeManagement/vendorService/serviceCharge/deleteButton",
			/*"/vendorServices/charge/delete",*/
			"/conciergeManagement/serviceBooking/createButton",
			"/conciergeManagement/serviceBooking/updateButton",
			"/conciergeManagement/serviceBooking/deleteButton",
			"/conciergeManagement/serviceBooking/statusButton",
			
			
			})			
	public void conciergeSecurityMethod(String model, HttpServletResponse res)throws IOException {
		logger.info("Sending Response to JSP as "+model);
	}
	@RequestMapping(value = {
			"/manpower/staffMaster/createButton",
			"/manpower/staffMaster/updateButton",
			"/manpower/staffMaster/deleteButton",
			"/manpower/staffexperience/createButton",
			"/manpower/staffexperience/updateButton",
			"/manpower/staffexperience/deleteButton",
			"/manpower/stafftraining/updateButton",
			"/manpower/stafftraining/createButton",
			"/manpower/stafftraining/deleteButton",
			"/manpower/staffnotices/createButton",		
			"/manpower/staffnotices/deleteButton",			
			"/manpower/staffnotices/updateButton",			
			"/manpower/staffMasterAddress/updateButton",			
			"/manpower/staffMasterAddress/deleteButton",		
			"/manpower/staffMasterAddress/createButton",			
			"/manpower/staffMasterContacts/createButton",			
			"/manpower/staffMasterContacts/deleteButton",			
			"/manpower/staffMasterContacts/updateButton",			
			"/manpower/staffMasterAccessCard/updateButton",			
			"/manpower/staffMasterAccessCard/createButton",			
			"/manpower/staffMasterAccessCardPermission/createButton",			
			"/manpower/staffMasterAccessCardPermission/updateButton",			
			"/manpower/staffMasterDocument/uploadButton",			
			"/manpower/staffMasterMedicalEmergency/deleteButton",			
			"/manpower/staffMasterMedicalEmergency/createButton",			
			"/manpower/staffMasterMedicalEmergency/updateButton",			
			"/manpower/staffMasterArms/deleteButton",			
			"/manpower/staffMasterArms/createButton",			
			"/manpower/staffMasterArms/updateButton",			
			"/manpower/staffMasterPhotoUpload/uploadButton",					
		})			
	public void manpowersecurityMethod(String model, HttpServletResponse res)throws IOException {
			logger.info("Sending Response to JSP as "+model);
	}	
	
	@RequestMapping(value = {
			"/visitormanagement/visitormaster/updateButton",
			"/visitormanagement/visitorwizard/createButton",
			"/visitormanagement/visitorwizard/searchButton",
			"/visitormanagement/visitorwizard/exitButton",
			"/visitormanagement/visitors/updateButton",
			"/visitormanagement/visitors/createButton",
			"/visitormanagement/visitors/uploadButton",
			"/visitormanagement/visitors/viewButton",
			"/visitormanagement/visitordetails/uploadButton",
			})			
	public void visitormanagementsecurityMethod(String model, HttpServletResponse res)throws IOException {
		logger.info("Sending Response to JSP as "+model);
	}
	
	@RequestMapping(value = {
						"/filerepositorymanagement/filerepositorymaster/createButton",
						"/filerepositorymanagement/filerepositorymaster/updateButton",
						"/filerepositorymanagement/filerepositorymaster/deleteButton",
						"/filerepositorymanagement/filerepositorymaster/uploadDocumentButton",
						"/filerepositorymanagement/filerepositorymaster/downloadDocumentButton",
						"/filerepositorymaster/filerepositorymaster/statusButton",
						
						"/filerepositorymanagement/filerepository/viewDocumentButton",
						"/filerepositorymanagement/filerepository/deleteButton",
						"/filerepositorymanagement/filerepository/createButton",
						"/filerepositorymanagement/filerepository/updateButton"
						
							})			
	public void FileDrawingsecurityMethod(String model, HttpServletResponse res)throws IOException {
		logger.info("Sending Response to JSP as "+model);
	}
	
	
	
	 @RequestMapping(value = {
			   "/commanagement/project/createButton",
			   "/commanagement/project/updateButton",
			   "/commanagement/project/deleteButton",
			   
			   "/commanagement/block/createButton",
			   "/commanagement/block/updateButton",
			   "/commanagement/block/deleteButton",
			   
			   "/commanagement/property/createButton",
			   "/commanagement/property/updateButton",
			   "/commanagement/property/deleteButton",
			   
			   "/commanagement/owner/createButton",
			   "/commanagement/owner/updateButton",
			   "/commanagement/owner/deleteButton",		   
			   "/commanagement/owneraddress/createButton",
			   "/commanagement/owneraddress/updateButton",
			   "/commanagement/owneraddress/deleteButton",
			   "/commanagement/ownercontact/createButton",
			   "/commanagement/ownercontact/updateButton",
			   "/commanagement/ownercontact/deleteButton",
			   "/commanagement/ownerproperty/createButton",
			   "/commanagement/ownerproperty/updateButton",
			   "/commanagement/ownerproperty/deleteButton",
			   "/commanagement/owneracesscards/createButton",
			   "/commanagement/owneracesscards/updateButton",
			   "/commanagement/owneracesscards/deleteButton",
			   "/commanagement/owneracesscardspermission/createButton",
			   "/commanagement/owneracesscardspermission/updateButton",
			   "/commanagement/owneracesscardspermission/deleteButton",
			   "/commanagement/owneraddressdocuments/createButton",
			   "/commanagement/owneraddressdocuments/updateButton",
			   "/commanagement/owneraddressdocuments/deleteButton",
			   "/commanagement/owneraddressmedicalemergency/createButton",
			   "/commanagement/owneraddressmedicalemergency/updateButton",
			   "/commanagement/owneraddressmedicalemergency/deleteButton",
			   "/commanagement/ownerarms/createButton",
			   "/commanagement/ownerarms/updateButton",
			   "/commanagement/ownerarms/deleteButton",
			   
			   "/commanagement/tenants/createButton",
			   "/commanagement/tenants/updateButton",
			   "/commanagement/tenants/deleteButton",
			   "/commanagement/tenants/statusButton",
			   "/commanagement/tenantsaddress/createButton",
			   "/commanagement/tenantsaddress/updateButton",
			   "/commanagement/tenantsaddress/deleteButton",
			   "/commanagement/tenantscontact/createButton",
			   "/commanagement/tenantscontact/updateButton",
			   "/commanagement/tenantscontact/deleteButton",
			   "/commanagement/tenantsproperty/createButton",
			   "/commanagement/tenantsproperty/updateButton",
			   "/commanagement/tenantsproperty/deleteButton",
			   "/commanagement/tenantsacesscards/createButton",
			   "/commanagement/tenantsacesscards/updateButton",
			   "/commanagement/tenantsacesscards/deleteButton",
			   "/commanagement/tenantsacesscardspermission/createButton",
			   "/commanagement/tenantsacesscardspermission/updateButton",
			   "/commanagement/tenantsacesscardspermission/deleteButton",
			   "/commanagement/tenantsaddressdocuments/createButton",
			   "/commanagement/tenantsaddressdocuments/updateButton",
			   "/commanagement/tenantsaddressdocuments/deleteButton",
			   "/commanagement/tenantsaddressmedicalemergency/createButton",
			   "/commanagement/tenantsaddressmedicalemergency/updateButton",
			   "/commanagement/tenantsaddressmedicalemergency/deleteButton",
			   "/commanagement/tenantsarms/createButton",
			   "/commanagement/tenantsarms/updateButton",
			   "/commanagement/tenantsarms/deleteButton",
			   
			   "/commanagement/family/createButton",
			   "/commanagement/family/updateButton",
			   "/commanagement/family/deleteButton",
			   "/commanagement/familyaddress/createButton",
			   "/commanagement/familyaddress/updateButton",
			   "/commanagement/familyaddress/deleteButton",
			   "/commanagement/familycontact/createButton",
			   "/commanagement/familycontact/updateButton",
			   "/commanagement/familycontact/deleteButton",
			   "/commanagement/familyproperty/createButton",
			   "/commanagement/familyproperty/updateButton",
			   "/commanagement/familyproperty/deleteButton",
			   "/commanagement/familyacesscards/createButton",
			   "/commanagement/familyacesscards/updateButton",
			   "/commanagement/familyacesscards/deleteButton",
			   "/commanagement/familyacesscardspermission/createButton",
			   "/commanagement/familyacesscardspermission/updateButton",
			   "/commanagement/familyacesscardspermission/deleteButton",
			   "/commanagement/familyaddressdocuments/createButton",
			   "/commanagement/familyaddressdocuments/updateButton",
			   "/commanagement/familyaddressdocuments/deleteButton",
			   "/commanagement/familyaddressdocuments/viewButton",
			   "/commanagement/familyaddressdocuments/selectFileButton",
			   "/commanagement/familyaddressmedicalemergency/createButton",
			   "/commanagement/familyaddressmedicalemergency/updateButton",
			   "/commanagement/familyaddressmedicalemergency/deleteButton",
			   "/commanagement/familyarms/createButton",
			   "/commanagement/familyarms/updateButton",
			   "/commanagement/familyarms/deleteButton",
			   
			   
			   "/commanagement/domestichelp/createButton",
			   "/commanagement/domestichelp/updateButton",
			   "/commanagement/domestichelp/deleteButton",
			   "/commanagement/domestichelpaddress/createButton",
			   "/commanagement/domestichelpaddress/updateButton",
			   "/commanagement/domestichelpaddress/deleteButton",
			   "/commanagement/domestichelpcontact/createButton",
			   "/commanagement/domestichelpcontact/updateButton",
			   "/commanagement/domestichelpcontact/deleteButton",
			   "/commanagement/domestichelpproperty/createButton",
			   "/commanagement/domestichelpproperty/updateButton",
			   "/commanagement/domestichelpproperty/deleteButton",
			   "/commanagement/domestichelpacesscards/createButton",
			   "/commanagement/domestichelpacesscards/updateButton",
			   "/commanagement/domestichelpacesscards/deleteButton",
			   "/commanagement/domestichelpacesscardspermission/createButton",
			   "/commanagement/domestichelpacesscardspermission/updateButton",
			   "/commanagement/domestichelpacesscardspermission/deleteButton",
			   "/commanagement/domestichelpaddressdocuments/createButton",
			   "/commanagement/domestichelpaddressdocuments/updateButton",
			   "/commanagement/domestichelpaddressdocuments/deleteButton",
			   "/commanagement/domestichelpaddressmedicalemergency/createButton",
			   "/commanagement/domestichelpaddressmedicalemergency/updateButton",
			   "/commanagement/domestichelpaddressmedicalemergency/deleteButton",
			   "/commanagement/domestichelparms/createButton",
			   "/commanagement/domestichelparms/updateButton",
			   "/commanagement/domestichelparms/deleteButton",
			   
			   "/commanagement/pets/createButton",
			   "/commanagement/pets/updateButton",
			   "/commanagement/pets/activateDeactivateButton",
			   "/commanagement/petsdocuments/createButton",
			   "/commanagement/petsdocuments/updateButton",
			   "/commanagement/petsdocuments/deleteButton",
			   
			   "/commanagement/documentdefiner/createButton",
			   "/commanagement/documentdefiner/updateButton",
			   "/commanagement/documentdefiner/deleteButton",
			   
			   "/commanagement/documentrepository/createButton",
			   "/commanagement/documentrepository/viewButton",
			   "/commanagement/documentrepository/deleteButton",
			   
			   "/commanagement/accesscards/createButton",
			   "/commanagement/accesscards/updateButton",
			   "/commanagement/accesscards/deleteButton",
			   "/commanagement/accesscardspoints/createButton",
			   "/commanagement/accesscardspoints/updateButton",
			   "/commanagement/accesscardspoints/deleteButton",
			   
			   
			  "com/ownerAuditTrail",
			   })   
			 public void comsecurityMethod(String model, HttpServletResponse res)throws IOException {
				  logger.info("Sending Response to JSP as "+model);
			 }
	 
	 
	 /*****  FOR PROCURE MENT USE CASE MODULE  ******/
	 @RequestMapping(value = {
				/** For Requsition **/
			 	"/procurement/requisition/createButton",
				"/procurement/requisition/updateButton",
				"/procurement/requisition/deleteButton",
				"/procurement/requisition/approveButton",
				"/procurement/requisition/rejectButton",
				"/procurement/requisition/printButton",
				
				/** For RequsitionDetails Hierarchy **/
				"/procurement/requisitionDetails/createButton",
				"/procurement/requisitionDetails/updateButton",
				"/procurement/requisitionDetails/deleteButton",
				"/procurement/requisitionDetails/destroyRequisitionDetails",
				
				/** For ItemMater **/
				"/procurement/itemMaster/createButton",
				"/procurement/itemMaster/updateButton",
				"/procurement/itemMaster/deleteButton",
				
				"/procurement/uom/createButton",
				"/procurement/uom/updateButton",
				"/procurement/uom/deleteButton",
				"/procurement/uom/activateButton",
				
				/** For Vendorpricelist **/
				"/procurement/vendorPriceList/createButton",
				"/procurement/vendorPriceList/updateButton",
				"/procurement/vendorPriceList/activateButton",
				
				})			
		public void procurementsecurityMethod(String model, HttpServletResponse res)throws IOException {
			logger.info("Sending Response to JSP as "+model);
		}
	 /*** END FOR PROCUREMENT USE MODULE  *****/
	 
	 
	 /****  FOR VENDOR MANAGEMENT USE CASE  ****/
	 @RequestMapping(value = {
			 
			 	//For Vendors
			 	"/vendorManagement/vendors/createButton",
			 	"/vendorManagement/vendors/updateButton",
			 	"/vendorManagement/vendors/activateButton",
			 	
			 	//For Vendor_Address(Hierarchy)
			 	"/vendorManagement/vendorAddress/createButton",
			 	"/vendorManagement/vendorAddress/updateButton",
			 	"/vendorManagement/vendorAddress/deleteButton",
			 	"/vendorManagement/vendorAddress/contactButton",	
			 	
			 	//For Vendor_Contact(Hierarchy)
			 	"/vendorManagement/contacts/createButton",
			 	"/vendorManagement/contacts/updateButton",
			 	"/vendorManagement/contacts/deleteButton",
			 	
			 	//For Vendor_Documents(Hierarchy)
			 	"/vendorManagement/documents/createButton",
			 	"/vendorManagement/documents/updateButton",
			 	"/vendorManagement/documents/deleteButton",
			 	"/vendorManagement/documents/viewButton",
			 	"/vendorManagement/documents/approveDocumentButton",
			 	"/vendorManagement/documents/selectFilesForDocumentButton",
			 	
			 	//For Vendor_OtherDetails(Hierarchy)
			 	"/vendorManagement/vendorOtherDetails/updateButton",
			 	"/vendorManagement/vendorOtherDetails/deleteButton",
			 	"/vendorManagement/vendorOtherDetails/activateEmpanellButton",
			 	
				// For Vendor Conracts 
			 	"/vendorManagement/vendorContracts/createButton",
				"/vendorManagement/vendorContracts/updateButton",
				"/vendorManagement/vendorContracts/activateButton",
				
				//For VendorContract_LineItems(Hierarchy)
				"/vendorManagement/vendorContractsLineItems/updateButton",
				"/vendorManagement/vendorContractsLineItems/deleteButton",
				
				//For Vendor Invoices
				"/vendorManagement/vendorInvoice/createButton",
				"/vendorManagement/vendorInvoice/updateButton",
				"/vendorManagement/vendorInvoice/activateButton",
				
				//For Vendor_Payments(Hierarchy)
				"/vendorManagement/vendorPayments/createButton",
				"/vendorManagement/vendorPayments/updateButton",
				"/vendorManagement/vendorPayments/deleteButton",
				"/vendorPayments/vendorPaymentsDestroyUrl",
				
				//For VendorInvoice_LineItems(Hierarchy)
				"/vendorManagement/vendorInvoiceLineItems/updateButton",
				"/vendorManagement/vendorInvoiceLineItems/deleteButton",
				
				//For Vendor Incidents
				"/vendorManagement/vendorIncidents/createButton",
				"/vendorManagement/vendorIncidents/updateButton",
				"/vendorManagement/vendorIncidents/activateButton",
				
				//For Vendor Requests
				"/vendorManagement/vendorRequests/createButton",
				"/vendorManagement/vendorRequests/updateButton",
				"/vendorManagement/vendorRequests/updateStatusButton",
				
				
				
				})			
		public void vendorManagementsecurityMethod(String model, HttpServletResponse res)throws IOException {
			logger.info("Sending Response to JSP as "+model);
		}
	 /***  END FOR VENDOR MANAGEMENT USE CASE  ****/
	 
	 /****  FOR ASSET MANAGEMENT USE CASE  ****/
	  @RequestMapping(value = {
	    
	    // For Master 
	     "/asset/master/createButton",
	    "/asset/master/updateButton",
	    "/asset/master/approverejectButton",
	    "/asset/master/uploaddownloadButton",
	    "/asset/master/printButton",
	    
	    // For Spares 
	     "/asset/spares/createButton",
	    "/asset/spares/updateButton",
	    "/asset/spares/deleteButton",
	    
	    // For Warranty 
	     "/asset/warranty/createButton",
	    "/asset/warranty/updateButton",
	    "/asset/warranty/deleteButton",
	    
	    // For Maintenance Cost 
	     "/asset/maintcost/createButton",
	    "/asset/maintcost/updateButton",
	    "/asset/maintcost/deleteButton",
	    
	    // For Ownership 
	    "/asset/ownership/createButton",
	    "/asset/ownership/updateButton",
	    "/asset/ownership/deleteButton",
	    
	    // For Service History 
	    "/asset/service/createButton",
	    "/asset/service/updateButton",
	    "/asset/service/deleteButton",
	    
	    // For Maintenance 
	    "/asset/maintsch/createButton",
	    "/asset/maintsch/updateButton",
	    "/asset/maintsch/deleteButton",
	    "/asset/maintsch/approverejectButton",
	    
	    // For Category 
	    "/asset/cat/createButton",
	    "/asset/cat/updateButton",
	    "/asset/cat/deleteButton",
	    
	    // For Location 
	    "/asset/loc/createButton",
	    "/asset/loc/updateButton",
	    "/asset/loc/deleteButton",
	    
	    // For Physical Survey 
	    "/asset/physurvey/createButton",
	    "/asset/physurvey/updateButton",
	    "/asset/physurvey/deleteButton",
	    "/asset/physurvey/statusButton",
	    
	    // For Physical Survey Report 
	    "/asset/physurveyreport/updateButton",

	    })   
	  public void assetManagementsecurityMethod(String model, HttpServletResponse res)throws IOException {	  
	   logger.info("Sending Response to JSP as "+model);
	  }
		/****  FOR INVENTORY MANAGEMENT MODULE  ****/
	  @RequestMapping(value = {
	    
	    // For Store Master 
	     "/inventory/storeMaster/createButton",
	    "/inventory/storeMaster/updateButton",
	    "/inventory/storeMaster/deleteButton",
	    
	    // For Store Goods Receipt 
	     "/inventory/storeGoodsReceipt/createButton",
	    "/inventory/storeGoodsReceipt/updateButton",
	    "/inventory/storeGoodsReceipt/deleteButton",
	    
	    // For Store Goods Receipt Items 
	     "/inventory/storeGoodsReceiptItems/createButton",
	    "/inventory/storeGoodsReceiptItems/updateButton",
	    "/inventory/storeGoodsReceiptItems/deleteButton",
	    
	    // For Store Issue 
	     "/inventory/storeIssue/createButton",
	    "/inventory/storeIssue/updateButton",
	    "/inventory/storeIssue/deleteButton",
	    
	    // For Store Movement 
	    "/inventory/storeMovement/createButton",
	    "/inventory/storeMovement/updateButton",
	    "/inventory/storeMovement/deleteButton",
	    
	    // For Store Goods Returns 
	    "/inventory/storeGoodsReturns/createButton",
	    "/inventory/storeGoodsReturns/updateButton",
	    "/inventory/storeGoodsReturns/deleteButton",
	    
	    // For Store Adjustments 
	    "/inventory/storeAdjustments/createButton",
	    "/inventory/storeAdjustments/updateButton",
	    "/inventory/storeAdjustments/deleteButton",
	    
	    // For Store Item Ledger
	    "/inventory/storeItemLedger/createButton",
	    "/inventory/storeItemLedger/updateButton",
	    "/inventory/storeItemLedger/deleteButton",
	    
	    // For Store Item Ledger Details 
	    "/inventory/storeItemLedgerDetails/createButton",
	    "/inventory/storeItemLedgerDetails/updateButton",
	    "/inventory/storeItemLedgerDetails/deleteButton",
	    
	    // For Store Physical Inventory Survey 
	    "/inventory/storePhysicalInventory/createButton",
	    "/inventory/storePhysicalInventory/updateButton",
	    "/inventory/storePhysicalInventory/deleteButton",
	    
	    // For Store Physical Inventory Survey Report 
	    "/inventory/storePhysicalInventoryReport/createButton",
	    "/inventory/storePhysicalInventoryReport/updateButton",
	    "/inventory/storePhysicalInventoryReport/deleteButton",
	    
	    "/inventory/stockoutward/deleteButton",
	    "/inventory/stockoutward/editButton",
	    "/inventory/stockoutward/statusButton",
	    "/inventory/stockoutward/createButton",
	    })   
	  public void inventoryManagementsecurityMethod(String model, HttpServletResponse res)throws IOException {	  
	   logger.info("Sending Response to JSP as "+model);
	  }
	  
	  // Customer Care Module Security Things
		 @RequestMapping(value = {	
				 
				 // Open New Tickets 			 
				 "/CustomerCare/OpenNewTicket/createButton",
	             "/CustomerCare/OpenNewTicket/editButton",
	             "/CustomerCare/OpenNewTicket/deleteButton",
	             "/CustomerCare/OpenNewTicket/re-OpenTicketStatusButton",
	             
	             // Open New Tickets 			 
	             "/helpdeskmanagement/faq/createButton",
	             "/helpdeskmanagement/faq/updateButton",
	             "/helpdeskmanagement/faq/deleteButton",
	             
	             "/helpdeskmanagement/lostandfound/createButton",
	             "/helpdeskmanagement/lostandfound/updateButton",
	             "/helpdeskmanagement/lostandfound/deleteButton",
	             
	             //Respond Tickets
				 "/CustomerCare/respondTicket/deptAcceptStatusButton",
				 "/CustomerCare/respondTicket/deptRejectStatusButton",
				 "/CustomerCare/respondTicket/viewConversationButton",
				 "/CustomerCare/RespondTicket/editButton",
				 "/CustomerCare/RespondTicket/PostReply/createButton",
				 "/CustomerCare/RespondTicket/PostReply/editButton",
				 "/CustomerCare/RespondTicket/PostReply/deleteButton",
				 "/CustomerCare/RespondTicket/PostReply/closedTicketButton",
				 "/CustomerCare/RespondTicket/PostInternalNote/createButton",
				 "/CustomerCare/RespondTicket/PostInternalNote/editButton",
				 "/CustomerCare/RespondTicket/PostInternalNote/deleteButton",
				 "/CustomerCare/RespondTicket/PostInternalNote/closedTicketButton",
				 "/CustomerCare/RespondTicket/DepartmentTransfer/createButton",
				 "/CustomerCare/RespondTicket/DepartmentTransfer/editButton",
				 "/CustomerCare/RespondTicket/AssignTicket/createButton",
				 "/CustomerCare/RespondTicket/AssignTicket/editButton",
				 "/CustomerCare/RespondTicket/EscalatedTickets/deleteButton",
				 
				 // Department Transfered Tickets
				 "/CustomerCare/TransferedTickets/deleteButton",
				 
				 // Escalated Tickets
				 "/CustomerCare/EscalatedTickets/deleteButton",
				 
				 // Help Topic 
				 "/CustomerCare/HelpTopic/createButton",
				 "/CustomerCare/HelpTopic/editButton",
				 "/CustomerCare/HelpTopic/deleteButton",
				 "/CustomerCare/HelpTopic/activateInactivateButton",
				 
				// Department Access Settings
				 "/CustomerCare/DepartmentAccessSettings/createButton",
				 "/CustomerCare/DepartmentAccessSettings/deleteButton",
					})			
			public void customerCareSecurityMethod(String model, HttpServletResponse res)throws IOException {
				logger.info("Sending Response to JSP as "+model);
			}
		 
		// Billing Collections Module Security Things
		 
				 @RequestMapping(value = {	
						 
						 // Billing Payments 			 
						 "/Collections/Payments/createButton",
						 "/Collections/Payments/cancelButton",
						 "/Collections/Payments/approvedPostedButton",
						 "/Collections/Payments/approveCollectionButton",
						 "/Collections/Payments/postCollectionButton",
						 "/Collections/PaymentSegments/viewSubSegmentsButton",
						 "/Collections/Payments/receiptGenarationButton",
						 "/Collections/Payments/cancelReceiptButton",
						 
						 // Adjustments
						 "/Collections/Adjustments/createButton",
						 "/Collections/Adjustments/updateButton",
						 "/Collections/Adjustments/destroyButton",
						 "/Collections/Adjustments/approvePostButton",
						 "/Collections/Adjustments/postAllButton",
						 "/Collections/Adjustments/approveAllButton",

						 "/Collections/Adjustments/LineItem/createButton",
						 "/Collections/Adjustments/LineItem/updateButton",
						 "/Collections/Adjustments/LineItem/destroyButton",
						 
						 // Bank Remittance
						 "/Collections/BankRemittance/createButton",
						 "/Collections/BankRemittance/editButton",
						 "/Collections/BankRemittance/destroyButton",
						 "/Collections/BankRemittance/uploadButton",
						 
						// Bank Statements
						 "/Collections/BankStatements/createButton",
						 "/Collections/BankStatements/editButton",
						 "/Collections/BankStatements/destroyButton",
						 "/Collections/BankStatements/uploadFileButton",
						 
						 //Masters
						 //Service Parameter Master
						 "/Masters/ServiceParameterMaster/createButton",
						 "/Masters/ServiceParameterMaster/updateButton",
						 "/Masters/ServiceParameterMaster/destroyButton",
						 "/Masters/ServiceParameterMaster/activeInactiveButton",
						 
						 //Bill Parameter Master
						 "/Masters/BillParameterMaster/createButton",
						 "/Masters/BillParameterMaster/updateButton",
						 "/Masters/BillParameterMaster/destroyButton",
						 "/Masters/BillParameterMaster/activeInactiveButton",

						 //Meter Parameter Master
						 "/Masters/MeterParameterMaster/createButton",
						 "/Masters/MeterParameterMaster/updateButton",
						 "/Masters/MeterParameterMaster/destroyButton",
						 "/Masters/MeterParameterMaster/activeInactiveButton",

						 "/Masters/Meters/createButton",
						 "/Masters/Meters/updateButton",
						 "/Masters/Meters/destroyButton",
						 "/Masters/Meters/activeInactiveButton",

						 "/Masters/Meters/MeterParameters/createButton",
						 "/Masters/Meters/MeterParameters/updateButton",
						 "/Masters/Meters/MeterParameters/destroyButton",
						 "/Masters/Meters/MeterParameters/activeInactiveButton",

						 "/Masters/Meters/MeterLocation/createButton",
						 "/Masters/Meters/MeterLocation/updateButton",
						 "/Masters/Meters/MeterLocation/destroyButton",
						 "/Master/Meters/releaseMeterButton",

						 "/Masters/MeterStatus/createButton",
						 "/Masters/MeterStatus/updateButton",
						 "/Masters/MeterStatus/destroyButton",

						 "/Masters/TransactionMaster/createButton",
						 "/Masters/TransactionMaster/updateButton",
						 "/Masters/TransactionMaster/destroyButton",
						 
						 "/Masters/InterestSettings/createButton",
						 
						 "/Masters/FRChange/createButton",
						 "/Masters/FRChange/editButton",
						 "/Masters/FRChange/destroyButton",
						 
						 
						 "/Masters/AMRSettings/create",
						 "/Masters/AMRSettings/update",
						 "/Masters/AMRSettings/delete",
						 "/Masters/AMRSettings/status",
						 
						 //services
						 // Service Master
						 "/Services/ServiceMaster/createButton",
						 "/Services/ServiceMaster/updateButton",
						 "/Services/ServiceMaster/destroyButton",
						 "/Services/ServiceMaster/activeInactiveButton",
						 "/Services/ServiceMaster/serviceEndDateButton",

						 "/Services/ServiceMaster/ServiceParameter/createButton",
						 "/Services/ServiceMaster/ServiceParameter/updateButton",
						 "/Services/ServiceMaster/ServiceParameter/destroyButton",
						 "/Services/ServiceMaster/ServiceParameter/activeInactiveButton",

						 "/Services/ServiceMaster/ServiceAccount/createButton",
						 "/Services/ServiceMaster/ServiceAccount/updateButton",
						 "/Services/ServiceMaster/ServiceAccount/destroyButton",
						 "/Services/ServiceMaster/ServiceAccount/activeInactiveButton",
						 "/Services/ServiceMaster/ServiceAccount/ledgerEndButton",

						  //Accounts
						 
						 "/Accounts/Account/activeInactiveButton",
						 
						 "/Accounts/ServiceOnBoard/createButton",
						 "/Accounts/ServiceOnBoard/approveButton",

						 "/Accounts/Deposits/createButton",

						 "/Accounts/Deposits/DepositLines/createButton",
						 "/Accounts/Deposits/DepositLines/updateButton",
						 "/Accounts/Deposits/DepositLines/destroyButton",
						 
						 "/Accounts/Deposits/RefundPayments/createButton",

						 //bill generation
						 "/BillGeneration/GenerateBills/GenerateBillButton",
						 "/BillGeneration/GenerateBills/generateTeleBroadBandBill",
						 "/BillGeneration/GenerateBills/bulkBillButton",
						 "/BillGeneration/GenerateBills/raiseDepositButton",
						 "/BillGeneration/GenerateBills/approveAllButton",
						 "/BillGeneration/GenerateBills/postAllButton",
						 "/BillGeneration/GenerateBills/approvePostButton",
						 "/BillGeneration/GenerateBills/cancelButton",
						 "/BillGeneration/GenerateBills/viewBillButton",
						 "/BillGeneration/GenerateBills/GenerateBackBillButton",

						 "/BillGeneration/GenerateBills/BillLineItem/createButton",


						 "/BillGeneration/ConsolidateBills/consolidateBillButton",

						 "/BillGeneration/ViewBills/sendMailButton",
						 "/BillGeneration/ViewBills/consolidateBillButton",
						 "/BillGeneration/ViewBills/individualBillButton",
						 "/BillGeneration/ViewBills/sendBillButton",
						 "/BillGeneration/ViewBills/sendAllBillsButton",

						 "/BillGeneration/AdvanceBilling/createButton",
						 "/BillGeneration/AdvanceBilling/approveButton",
						 
						 //UnAssed Points
						 "/BillGeneration/UnAssedPoints/createButton",
						 "/BillGeneration/UnAssedPoints/editButton",
						 "/BillGeneration/UnAssedPoints/destroyButton",
						 "/BillGeneration/UnAssedPoints/activeInactiveButton",
						 
						 "/BillGeneration/UnAssedDetails/createButton",
						 "/BillGeneration/UnAssedDetails/editButton",
						 
						 "/BillGeneration/GenerateBatchBill/createBatch",
						 "/BillGeneration/GenerateBatchBill/billAll",
						 "/BillGeneration/GenerateBatchBill/exportToExcel",
						 
						 "/BillGeneration/GenerateBills/uploadBatchFile",
						 "/BillGeneration/GenerateBills/uploadXMLFile",
						 
						 "/BillGeneration/billingSettings/create",
						 "/BillGeneration/billingSettings/update",
						 "/BillGeneration/billingSettings/delete",
						 "/BillGeneration/billingSettings/status",
						 
						 
						 // Common Area Maintenance
						 // CAM Ledger
						 "/CAM/CAMLedger/createButton",
						 "/CAM/CAMLedger/destroyButton",
						 "/CAM/CAMLedger/approveButton",
						 
						 "/CAM/CAMBills/createButton",
						 "/CAM/CAMBills/billButton",
						 "/CAM/CAMBills/approvePostButton",
						 
						 "/CAM/CAMCharges/CreateButton",
						 // Notification
						 "/customerCare/Notification/updateButton",
						 "/customerCare/Notification/createButton",
						 "/customerCare/Notification/deleteButton",
						 
							})			
					public void collectionsSecurityMethod(String model, HttpServletResponse res)throws IOException {
						logger.info("Sending Response to JSP as "+model);
					}
				 
			// Billing Rate Master Module Security Things
				 @RequestMapping(value = {	
						 
						 // Electricity
						 "/Tariff/Electricity/RateMaster/createRateMaster",
						 "/Tariff/Electricity/RateMaster/showAllRateMaster",
						 "/Tariff/Electricity/RateMaster/updateRateMaster",
						 "/Tariff/Electricity/RateMaster/deleteRateMaster",
						 "/Tariff/Electricity/RateMaster/activitRateMaster",
						 "/Tariff/Electricity/RateSlab/createRateSlab",
						 "/Tariff/Electricity/RateSlab/mergeRateSlab",
						 "/Tariff/Electricity/RateSlab/splitRateSlab",
						 "/Tariff/Electricity/RateSlab/updateRateSlab",
						 "/Tariff/Electricity/RateSlab/deleteRateSlab",
						 "/Tariff/Electricity/RateSlab/activitRateSlab",
						 "/Tariff/Electricity/TODRate/createTODRate",
						 "/Tariff/Electricity/TODRate/updateTODRate",
						 "/Tariff/Electricity/TODRate/deleteTODRate",
						 "/Tariff/Electricity/TODRate/activitTODRate",
						 
						// Water
						 "/Tariff/Water/RateMaster/createRateMaster",
						 "/Tariff/Water/RateMaster/showAllRateMaster",
						 "/Tariff/Water/RateMaster/updateRateMaster",
						 "/Tariff/Water/RateMaster/deleteRateMaster",
						 "/Tariff/Water/RateMaster/activitRateMaster",
						 "/Tariff/Water/RateSlab/createRateSlab",
						 "/Tariff/Water/RateSlab/mergeRateSlab",
						 "/Tariff/Water/RateSlab/splitRateSlab",
						 "/Tariff/Water/RateSlab/updateRateSlab",
						 "/Tariff/Water/RateSlab/deleteRateSlab",
						 "/Tariff/Water/RateSlab/activitRateSlab",
						 
						// Gas
						 "/Tariff/Gas/RateMaster/createRateMaster",
						 "/Tariff/Gas/RateMaster/showAllRateMaster",
						 "/Tariff/Gas/RateMaster/updateRateMaster",
						 "/Tariff/Gas/RateMaster/deleteRateMaster",
						 "/Tariff/Gas/RateMaster/activitRateMaster",
						 "/Tariff/Gas/RateSlab/createRateSlab",
						 "/Tariff/Gas/RateSlab/mergeRateSlab",
						 "/Tariff/Gas/RateSlab/splitRateSlab",
						 "/Tariff/Gas/RateSlab/updateRateSlab",
						 "/Tariff/Gas/RateSlab/deleteRateSlab",
						 "/Tariff/Gas/RateSlab/activitRateSlab",
						 
						// Solid Waste
						 "/Tariff/SolidWaste/RateMaster/createRateMaster",
						 "/Tariff/SolidWaste/RateMaster/showAllRateMaster",
						 "/Tariff/SolidWaste/RateMaster/updateRateMaster",
						 "/Tariff/SolidWaste/RateMaster/deleteRateMaster",
						 "/Tariff/SolidWaste/RateMaster/activitRateMaster",
						 "/Tariff/SolidWaste/RateSlab/createRateSlab",
						 "/Tariff/SolidWaste/RateSlab/mergeRateSlab",
						 "/Tariff/SolidWaste/RateSlab/splitRateSlab",
						 "/Tariff/SolidWaste/RateSlab/updateRateSlab",
						 "/Tariff/SolidWaste/RateSlab/deleteRateSlab",
						 "/Tariff/SolidWaste/RateSlab/activitRateSlab",
						 
						 
						// Common Services
						 "/Tariff/CommonServices/RateMaster/createRateMaster",
						/* "/Tariff/CommonServices/RateMaster/showAllRateMaster",*/
						 "/Tariff/CommonServices/RateMaster/updateRateMaster",
						 "/Tariff/CommonServices/RateMaster/deleteRateMaster",
						 "/Tariff/CommonServices/RateMaster/activitRateMaster",
						 "/Tariff/CommonServices/RateSlab/createRateSlab",
						/* "/Tariff/CommonServices/RateSlab/mergeRateSlab",
						 "/Tariff/CommonServices/RateSlab/splitRateSlab",*/
						 "/Tariff/CommonServices/RateSlab/updateRateSlab",
						 "/Tariff/CommonServices/RateSlab/deleteRateSlab",
						 "/Tariff/CommonServices/RateSlab/activitRateSlab",
						 
						 
						 
							})			
					public void ratemasterSecurityMethod(String model, HttpServletResponse res)throws IOException {
						logger.info("Sending Response to JSP as "+model);
					}
				 
					// Billing Tariff Module Security Things
				 @RequestMapping(value = {	
						 
						 // Electricity
						 "/Tariff/Electricity/TariffMaster/createTariffMaster",
						 "/Tariff/Electricity/TariffMaster/updateTariffMaster",
						 "/Tariff/Electricity/TariffMaster/deleteTariffMaster",
						 
						 "/Tariff/Water/TariffMaster/createTariffMaster",
						 "/Tariff/Water/TariffMaster/updateTariffMaster",
						 "/Tariff/Water/TariffMaster/deleteTariffMaster",
						 
						 "/Tariff/Gas/TariffMaster/createTariffMaster",
						 "/Tariff/Gas/TariffMaster/updateTariffMaster",
						 "/Tariff/Gas/TariffMaster/deleteTariffMaster",
						 
						 "/Tariff/SolidWaste/TariffMaster/createTariffMaster",
						 "/Tariff/SolidWaste/TariffMaster/updateTariffMaster",
						 "/Tariff/SolidWaste/TariffMaster/deleteTariffMaster",
						 
						 "/Tariff/CommonServices/TariffMaster/createTariffMaster",
						 "/Tariff/CommonServices/TariffMaster/updateTariffMaster",
						 "/Tariff/CommonServices/TariffMaster/deleteTariffMaster",
						 
							})			
					public void tariffMasterSecurityMethod(String model, HttpServletResponse res)throws IOException {
						logger.info("Sending Response to JSP as "+model);
					}
				 

}
	

