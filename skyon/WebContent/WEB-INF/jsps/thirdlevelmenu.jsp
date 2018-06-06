<%@taglib prefix="kendo" uri="/WEB-INF/kendo.tld"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

	<div class="wrapper" id="wrapper">
		<ul class="middleNavA" id="UserManagementInnerMenu" style="display:none;">
			<li><a class="tipN" title="Manage System access to the Users"
				href="#" onclick="RenderLinkInner('users')"><img alt=""
					src="<c:url value='/resources/images/icons/color/user.png'/>"
					height="50" width="50"><span id="mNavUsers"> Users</span></a></li>
					
			<li><a class="tipN" title="Assign Users to the Role" href="#"
				onclick="RenderLinkInner('userroles')"><img alt=""
					src="<c:url value='/resources/images/icons/color/order-149.png'/>"
					height="50" width="50"><span id="mNavUserRole"> User
						Roles</span></a></li>

			<li><a class="tipN" title="Assign Users to the Grroup" href="#"
				onclick="RenderLinkInner('usergroups')"><img alt=""
					src="<c:url value='/resources/images/icons/color/my-account.png'/>"
					height="50" width="50"><span id="mNavUserGroup"> User
						Groups</span></a></li>

			<li><a class="tipN" title="Manage Roles" href="#"
				onclick="RenderLinkInner('role')"><img alt=""
					src="<c:url value='/resources/images/icons/color/administrative-docs.png'/>"
					height="50" width="50"><span id="mNavRoles"> Roles</span></a></li>


			<li><a class="tipN" title="Manage Groups" href="#"
				onclick="RenderLinkInner('groups')"><img alt=""
					src="<c:url value='/resources/images/icons/color/attibutes.png'/>"
					height="50" width="50"><span id="mNavGroups"> Groups</span></a></li>


			<li><a class="tipN" title="Manage Department" href="#"
				onclick="RenderLinkInner('department')"><img alt=""
					src="<c:url value='/resources/images/icons/color/suppliers.png'/>"
					height="50" width="50"><span id="mNavDepartment">
						Departments</span></a></li>

			<li><a class="tipN" title="Manage Designations" href="#"
				onclick="RenderLinkInner('designation')"><img alt=""
					src="<c:url value='/resources/images/icons/color/cost.png'/>"
					height="50" width="50"><span id="mNavDesignation">
						Designations</span></a></li>

		</ul>
		
		<ul class="middleNavA" id="ProjectInnerMenu" style="display:none;">
			<li><a  class="tipN" title="Manage Project" href="#" onclick="RenderLinkInner('comproject')">
					<img width="50" height="50" alt="" src="<c:url value='/resources/images/icons/comimages/project.png'/>">
						<span id="mNavProject">Project</span></a></li>
			<li><a class="tipN" title="Manage Property" href="#" onclick="RenderLinkInner('comproperty')">
					<img width="50" height="50" alt=""src="<c:url value='/resources/images/icons/comimages/property.png'/>">
					<span id="mNavProperty">Property</span>
			</a></li>
		</ul>
		
		<ul class="middleNavA" id="PersonsInnerMenu" style="display:none;">
				<li><a class="tipN" title="Manage Owners" href="#" onclick="RenderLinkInner('comowners')">
						<img width="50" height="50" alt="" src="<c:url value='/resources/images/icons/comimages/Owner2.png'/>">
							<span id="mNavOwners">Owners</span></a></li>
				<li><a class="tipN" title="Manage Tenants" href="#" onclick="RenderLinkInner('comtenants')">
						<img width="50" height="50" alt=""src="<c:url value='/resources/images/icons/color/tenent2.png'/>">
						<span id="mNavTenants">Tenants</span>
				</a></li>
				<li><a class="tipN" title="Manage Family" href="#" onclick="RenderLinkInner('comfamily')">
						<img width="50" height="50" alt=""src="<c:url value='/resources/images/icons/comimages/family-icon.png'/>">
						<span id="mNavfamily">Family</span>
				</a></li>
				<li><a class="tipN" title="Manage Domestic Helps" href="#" onclick="RenderLinkInner('comdomestichelp')">
						<img width="50" height="50" alt=""src="<c:url value='/resources/images/icons/comimages/cheff.png'/>">
						<span id="mNavdomestichelp">Domestic Helps</span>
				</a></li>
				<li><a class="tipN" title="Manage Pets" href="#" onclick="RenderLinkInner('compets')">
						<img width="50" height="50" alt=""src="<c:url value='/resources/images/icons/comimages/Cat-Brown-icon.png'/>">
						<span id="mNavpets">Pets</span>
				</a></li>
		   </ul>
		   
		   <ul class="middleNavA" id="SettingsInnerMenu" style="display:none;">
				<li><a class="tipN" title="Manage Document Definer" href="#" onclick="RenderLinkInner('documentdefiner')">
						<img width="50" height="50" alt="" src="<c:url value='/resources/images/icons/comimages/document.png'/>">
							<span id="mNavDocumentDefiner">Document Definer</span></a></li>
				<li><a class="tipN" title="Manage Document Repository" href="#" onclick="RenderLinkInner('documentrepository')">
						<img width="50" height="50" alt=""src="<c:url value='/resources/images/icons/comimages/repository.png'/>">
						<span id="mNavDocumentRepo">Document Repository</span>
				</a></li>
		   </ul>
		   
		   <ul class="middleNavA" id="AccessCardsInnerMenu" style="display:none;">
				<li><a class="tipN" title="Manage Access Cards" href="#" onclick="RenderLinkInner('comaccesscards')">
						<img width="60" height="50" alt="" src="<c:url value='/resources/images/icons/comimages/add_card.png'/>">
							<span id="mNavDocumentDefiner">Access Cards</span></a></li>
		   </ul>
		   
		   <ul class="middleNavA" id="VehiclesInnerMenu" style="display:none;">
			<li><a class="tipN" title="Parking Slot Creation,Editing And Activating" href="#"
				onclick="RenderLinkInner('parkingslots')"><img alt="" width="50px" height="50px"
					src="<c:url value='/resources/images/icons/color/logo_blue_white.png'/>" height="50" width="50" ><span id="mNavUsers">
						Parking Slots Master</span></a></li>
			<li><a class="tipN" title="Parking Slots Allocation,DeAllocation" href="#"
				onclick="RenderLinkInner('parkingslotallotment')"><img alt="" width="80px" height="50px"
					src="<c:url value='/resources/images/icons/color/parking.png'/>"><span id="mNavUserRole">
						Parking Slots Allocation</span></a></li>	
						
						
						
			<li><a class="tipN" title="Manage Vehicles Registration With Owner and Slots" href="#"
				onclick="RenderLinkInner('vehicles')"><img alt="" width="70px" height="50px"
					src="<c:url value='/resources/images/icons/color/car.png'/>"><span id="mNavRoles">
						Vehicles</span></a></li>	
						
			<li><a class="tipN" title="Manage Wrong Parking Details With Vehicle Details" href="#"
				onclick="RenderLinkInner('wrongparking')"><img alt="" width="50px" height="50px"
					src="<c:url value='/resources/images/icons/color/Wrongparking.png'/>"><span id="mNavGroups">
						Wrong Parking</span></a></li>	
		 </ul>
		 
		 <%-- <ul class="middleNavA" id="ParkingSlotsInnerMenu" style="display:none;">
			<li><a class="tipN" title="Parking Slot Creation,Editing And Activating" href="#"
				onclick="RenderLinkInner('parkingslots')"><img alt="" width="50px" height="50px"
					src="<c:url value='/resources/images/icons/color/logo_blue_white.png'/>" height="50" width="50" ><span id="mNavUsers">
						Parking Slots Master</span></a></li>
			<li><a class="tipN" title="Parking Slots Allocation,DeAllocation" href="#"
				onclick="RenderLinkInner('parkingslotallotment')"><img alt="" width="80px" height="50px"
					src="<c:url value='/resources/images/icons/color/parking.png'/>"><span id="mNavUserRole">
						Parking Slots Allocation</span></a></li>			
		</ul> --%>
		
		<ul class="middleNavA" id="ManageJobTypesInnerMenu" style="display:none;">
			
			<li><a title="Manage Jobs Calender" href="#" onclick="RenderLinkInner('jobcalender')"><img alt="" width="50px" height="50px"
					src="<c:url value='/resources/images/icons/color/jobcalender.png'/>">
					<span id="mNavJobCalender"> Manage Job Calender</span></a>
			</li>
			
			<li><a title="Manage Jobs Types" href="#" onclick="RenderLinkInner('jobtypes')"><img alt="" width="50px" height="50px"
					src="<c:url value='/resources/images/icons/color/jobtypes.jpg'/>">
					<span id="mNavUsers"> Manage Job Types</span></a>
			</li>
					
			<li><a title="Manage Maintenance Types" href="#"
				onclick="RenderLinkInner('maintenancetypes')"><img alt="" width="50px" height="50px"
					src="<c:url value='/resources/images/icons/color/maintenancetypes.jpg'/>"><span id="mNavMaintenance">
						Manage Maintenance Types</span></a>
			</li>	
			<li><a title="Manage Job Cards" href="#"
				onclick="RenderLinkInner('jobcards')"><img alt="" width="50px" height="50px"
					src="<c:url value='/resources/images/icons/color/card_file.png'/>"><span id="mNavJobcards">
						Manage Job Cards</span></a>
			</li>		
			<li><a title="Manage Tool Master" href="#"
				onclick="RenderLinkInner('toolmaster')"><img alt="" width="50px" height="50px"
					src="<c:url value='/resources/images/icons/color/toolmaster.jpg'/>"><span id="mNavToolMaster">
						Manage Tool Master</span></a>
			</li>			
				
			<li><a title="Maintainance Deepartment" href="#"
				onclick="RenderLinkInner('maintainancedepartment')"><img alt="" width="50px" height="50px"
					src="<c:url value='/resources/images/icons/color/order-149.png'/>"><span id="mNavmaintainancedepartment">
						Maintainance Department</span></a>
			</li>				
		</ul>
		
		
		<ul class="middleNavA" id="VendorContractsInnerMenu" style="display:none;">
			<li><a title="Enter Vendor Incidents" href="#"
				onclick="RenderLinkInner('vendorIncidents')"><img alt="" width="50px" height="50px" 
					src="<c:url value='/resources/images/icons/color/vendorIncidents.png'/>"><span id="mNavUserRole">
						Vendor Incidents</span></a></li>
						
			<li><a title="Enter Vendor Requests" href="#"
				onclick="RenderLinkInner('vendorRequests')"><img alt="" width="50px" height="50px"
					src="<c:url value='/resources/images/icons/color/vendorRequests.png'/>"><span id="mNavUserRole">
						Vendor Requests</span></a></li>
						
			<li><a title="Vendor PriceList" href="#"
				onclick="RenderLinkInner('vendorPriceLists')"><img alt="" width="50px" height="50px"
					src="<c:url value='/resources/images/icons/color/vendorPriceList.jpg'/>"><span id="mNavUserRole">
						Vendor PriceList</span></a></li>
						
			<li><a title="Enter Vendor Details" href="#"
				onclick="RenderLinkInner('vendors')"><img alt="" width="50px" height="50px"
					src="<c:url value='/resources/images/icons/color/vendors.png'/>"><span id="mNavUsers">
						Vendors</span></a></li>
						
			<%-- <li><a title="Enter Vendor Contracts Details" href="#"
				onclick="RenderLinkInner('vendorContracts')"><img alt="" width="50px" height="50px"
					src="<c:url value='/resources/images/icons/color/vendorContracts.png'/>"><span id="mNavUserRole">
						Vendor Contracts</span></a></li> --%>
						
			<%-- <li><a title="Enter Vendor Invoices" href="#" 
				onclick="RenderLinkInner('vendorInvoices')"><img alt="" width="50px" height="50px"
					src="<c:url value='/resources/images/icons/color/vendorInvoices.png'/>"><span id="mNavUserRole">
						Vendor Invoices</span></a></li> --%>
		</ul>
		
		
		
		<ul class="middleNavA" id="VisitorWizardInnerMenu" style="display:none;">
			<li><a class="tipN" title="Enter Visitor Record " href="#"
				onclick="RenderLinkInner('visitorwizard')"><img alt=""
					src="<c:url value='/resources/images/icons/color/Person-Male-Light-icon.png'/>"
					height="50" width="50"><span id="mNavVisitorWizard">
						Visitor Wizard</span></a></li>
			<li><a class="tipN"
				title="Enter and View the Visitor  Record  based on  Property Number And Parking Slots"
				href="#" onclick="RenderLinkInner('visitordetails')"><img alt=""
					src="<c:url value='/resources/images/icons/color/people.png'/>"
					height="50" width="50"><span id="mNavUsers"> Visitors</span></a></li>
			<li><a class="tipN"
				title="View and update Visitor Master Record like(Visitor Name,Address,Contact Number)"
				href="#" onclick="RenderLinkInner('storevisitor')"><img alt=""
					src="<c:url value='/resources/images/icons/color/staff-notice.png'/>"
					height="50" width="50"><span id="mNavVisitorRecord">
						Visitor Master</span></a></li>
			<%-- <li><a title="View and update Visitor Visit record" href="#"
				onclick="RenderLinkInner('visitorvisits')"><img alt=""
					src="<c:url value='/resources/images/icons/color/Attendence.png'/>"
					height="50" width="50"><span id="mNavUserRole">
						Visitor Entry Details</span></a></li> --%>
		</ul>
		
		<!-- Inventory Management -->
		
		<ul class="middleNavA" id="StoreReceiptsInnerMenu" style="display:none;">
			<li><a class="tipN" title="Manage Store Receipts" href="#"
				onclick="RenderLinkInner('indexStoreGoodsReceipt')"><img alt=""
					src="<c:url value='/resources/images/icons/color/user.png'/>" width="50px" height="50px"><span id="">
						Store Receipts</span></a></li>
			<li><a class="tipN" title="Manage Store Issues" href="#"
				onclick="RenderLinkInner('indexStoreIssue')"><img alt=""
					src="<c:url value='/resources/images/icons/color/user.png'/>" width="50px" height="50px"><span id="">
						Store Issues</span></a></li>
			<li><a class="tipN" title="Manage Store Item Transfers" href="#"
				onclick="RenderLinkInner('indexStoreItemTransfer')"><img alt=""
					src="<c:url value='/resources/images/icons/color/user.png'/>" width="50px" height="50px"><span id="">
						Store Item Transfers</span></a></li>
			<li><a class="tipN" title="Manage Store Item Returns" href="#"
				onclick="RenderLinkInner('indexStoreGoodsReturns')"><img alt=""
					src="<c:url value='/resources/images/icons/color/user.png'/>" width="50px" height="50px"><span id="">
						Store Item Returns</span></a></li>
			<li><a class="tipN" title="Manage Store Adjustments" href="#"
				onclick="RenderLinkInner('indexStoreAdjustments')"><img alt=""
					src="<c:url value='/resources/images/icons/color/user.png'/>" width="50px" height="50px"><span id="">
						Store Item Adjustments</span></a></li>
			<li><a class="tipN" title="Manage Item Ledger" href="#"
				onclick="RenderLinkInner('indexStoreItemLedger')"><img alt=""
					src="<c:url value='/resources/images/icons/color/user.png'/>" width="50px" height="50px"><span id="">
						Item Ledger</span></a></li>
			<li><a class="tipN" title="Manage Store Master" href="#"
				onclick="RenderLinkInner('indexStoreMaster')"><img alt=""
					src="<c:url value='/resources/images/icons/color/user.png'/>" width="50px" height="50px"><span id="">
						Store Master</span></a></li>
						
			<li><a title="Item Master" href="#"
				onclick="RenderLinkInner('itemMasters')"><img alt=""
					src="<c:url value='/resources/images/icons/color/business-contact.png'/>"><span id="mNavUserRole">
						Item Master</span></a></li>
		</ul>
		
		<!-- Service Master Menu -->
		<ul class="middleNavA" id="MastersInnerMenu" style="display:none;">								
					<li><a title="Service Master" href="#"
						onclick="RenderLinkInner('serviceMaster')"> <img alt=""
							src="<c:url value='/resources/images/billing/servicePoints/serviceMasterImg.png'/>">
							<span id="mNavProject">Service Master</span></a></li>
		</ul>
		
		<!-- Service Points -->
		<ul class="middleNavA" id="ServicePointsInnerMenu" style="display:none;">
					<li><a title="Service Point" href="#"
						onclick="RenderLinkInner('servicePoint')"> <img alt=""
							src="<c:url value='/resources/images/billing/servicePoints/servicePoint.jpg'/>">
							<span id="mNavProject">Service Point</span></a></li>
		</ul>
		
		<!-- Help Desk -->
		<ul class="middleNavA" id="OpenNewTicketInnerMenu" style="display:none;">
					<li><a title="Open New Ticket" href="#"
						onclick="RenderLinkInner('openNewTicket')"> <img alt=""
							src="<c:url value='/resources/images/helpDesk/openNewTicket.png'/>" width="50px" height="50px">
							<span id="mNavProject">Open New Ticket</span></a></li>
							
					<li><a title="Respond Ticket" href="#"
						onclick="RenderLinkInner('respondTicket')"> <img alt=""
							src="<c:url value='/resources/images/helpDesk/respondTicket.png'/>" width="50px" height="50px">
							<span id="mNavProject">Respond Ticket</span></a></li>
							
					<li><a title="Transfered Tickets" href="#"
						onclick="RenderLinkInner('deptTransferedTickets')"> <img alt=""
							src="<c:url value='/resources/images/helpDesk/ticketTransfer.png'/>" width="50px" height="50px">
							<span id="mNavProject">Transfered Tickets</span></a></li>
							
				    <li><a title="Escalated Tickets" href="#"
						onclick="RenderLinkInner('escalatedTickets')"> <img alt=""
							src="<c:url value='/resources/images/helpDesk/ticketTransfer.png'/>" width="50px" height="50px">
							<span id="mNavProject">Escalated Tickets</span></a></li>
							
					<li><a title="Help Topic" href="#"
						onclick="RenderLinkInner('helpTopic')"> <img alt=""
							src="<c:url value='/resources/images/helpDesk/helpTopic.png'/>" width="50px" height="50px">
							<span id="mNavProject">Help Topic</span></a></li>
								
							<li><a title="Help Topic" href="#"
						onclick="RenderLinkInner('customerCareNotification')"> <img alt=""
							src="<c:url value='/resources/images/helpDesk/helpTopic.png'/>" width="50px" height="50px">
							<span id="mNavProject">Notification</span></a></li>		
		</ul>
		
		<ul class="middleNavA" id="DepartmentAccessSettingsInnerMenu" style="display:none;">
					<li><a title="Department Access Settings" href="#"
						onclick="RenderLinkInner('departmentAccessSettings')"> <img alt=""
							src="<c:url value='/resources/images/helpDesk/openNewTicket.png'/>" width="50px" height="50px">
							<span id="mNavProject">Department Access Settings</span></a></li>
		</ul>
		
		<!-- Electricity Menu -->
		<ul class="middleNavA" id="ElectricityInnerMenu" style="display:none;">
			<li><a title="Manage Project" href="#" onclick="RenderLinkInner('electrictyBills')">
					<img alt="" src="<c:url value='resources/images/billing/electricity/inflated-electricity-bills.png'/>">
						<span id="mNavProject">Bills</span></a></li>

			<%-- <li><a title="Manage Project" href="#" onclick="RenderLinkInner('tariffmaster')">
					<img alt="" src="<c:url value='resources/images/billing/electricity/inflated-electricity-bills.png'/>">
						<span id="mNavProject">Tariff Managment</span></a></li> --%>

			<li><a title="Manage Project" href="#" onclick="RenderLinkInner('electrictyMeters')">
					<img alt="" src="<c:url value='resources/images/billing/electricity/electricityMeter.png'/>">
						<span id="mNavProject">Meters</span></a></li>

		</ul>
		
		<!-- Accounts Menu -->
		
		<ul class="middleNavA" id="AccountsInnerMenu" style="display:none;">
   		  
   		   <li><a title="Manage Project" href="#" onclick="RenderLinkInner('account')">
					<img alt="" src="<c:url value='resources/images/billing/electricity/accounts.png'/>">
						<span id="mNavProject">Account</span></a></li>
   		
			<li><a title="Manage Project" href="#" onclick="RenderLinkInner('electrictyLedger')">
					<img alt="" src="<c:url value='resources/images/billing/electricity/ledgerImg.png'/>">
						<span id="mNavProject">Ledgers</span></a></li>			
			 
			 </ul>
			 
		<!-- Billing Wizard Menu -->
		
		<ul class="middleNavA" id="BillingWizardInnerMenu" style="display:none;">
   		  
   		   <li><a title="Billing Wizard" href="#" onclick="RenderLinkInner('billingWizard')">
					<img alt="" src="<c:url value='resources/images/billingwizards/activity_monitor.png'/>">
						<span id="mNavProject">Billing Wizard</span></a></li>
	
			 </ul>	 
		
		<!-- Manpower Management Menu -->
		
		<ul class="middleNavA" id="StaffMasterInnerMenu" style="display:none;">
			<li><a class="tipN" title="Create Staff for the application with an approprite roles, designation and departments" href="#"
				onclick="RenderLinkInner('manpowerindex')"><img alt=""
					src="<c:url value='/resources/images/icons/color/people.png'/>" height="50" width="50"><span id="">
						Staff Master</span></a></li>
			
			<li><a class="tipN" title="Keep track of Staff's Previous Work Experience" href="#"
				onclick="RenderLinkInner('manpowerse')"><img alt=""
					src="<c:url value='/resources/images/icons/color/Diploma-Certificate-icon.png'/>" height="50" width="50"><span id="">
						Staff Experience</span></a></li>	
				
			<li><a class="tipN" title="Recommend Training for the Staff" href="#"
				onclick="RenderLinkInner('manpowerst')"><img alt=""
					src="<c:url value='/resources/images/icons/color/Places-certificate-server-icon.png'/>" height="50" width="50"><span id="">
						Staff Training</span></a></li>
						
			<li><a class="tipN" title="Generate Notice for the Staff on Performance" href="#"
				onclick="RenderLinkInner('manpowersn')"><img alt=""
					src="<c:url value='/resources/images/icons/color/staff-notice.png'/>" height="50" width="50"><span id="">
						Staff Notices</span></a></li>	

		</ul>	 
		
		<!-- Assets Menu -->
		
		<ul class="middleNavA" id="AssetMasterInnerMenu" style="display:none;">
			<li><a class="tipN" title="Asset Master" href="#"
				onclick="RenderLinkInner('assetmaster')"><img alt=""
					src="<c:url value='/resources/images/icons/color/order-149.png'/>" width="50px" height="50px"><span
					id="">Asset Master</span></a></li>
					
			<li><a class="tipN" title="Ownership" href="#"
				onclick="RenderLinkInner('assetownership')"><img alt=""
					src="<c:url value='/resources/images/icons/color/user.png'/>" width="50px" height="50px"><span
					id="">Asset Ownership</span></a></li>
					
			<li><a class="tipN" title="Service History" href="#"
				onclick="RenderLinkInner('assetservicehistory')"><img alt=""
					src="<c:url value='/resources/images/icons/color/order-149.png'/>" width="50px" height="50px"><span
					id="">Asset Service History</span></a></li> 
					
			<li><a class="tipN" title="Physical Survey" href="#"
				onclick="RenderLinkInner('assetphysicalsurvey')"><img alt=""
					src="<c:url value='/resources/images/icons/color/order-149.png'/>" width="50px" height="50px"><span
					id="">Physical Survey</span></a></li>
					
			<li><a class="tipN" title="Asset Maintainance Schedule" href="#"
				onclick="RenderLinkInner('assetmaintenanceschedule')"><img alt=""
					src="<c:url value='/resources/images/icons/color/order-149.png'/>" width="50px" height="50px"><span
					id="">Maintenance Schedule</span></a></li>
					
			<li><a class="tipN" title="Category" href="#"
				onclick="RenderLinkInner('assetcat')"><img alt=""
					src="<c:url value='/resources/images/icons/color/order-149.png'/>" width="50px" height="50px"><span
					id="">Asset Category</span></a></li>
					
			<li><a class="tipN" title="Location" href="#"
				onclick="RenderLinkInner('assetloc')"><img alt=""
					src="<c:url value='/resources/images/icons/color/order-149.png'/>" width="50px" height="50px"><span
					id="">Asset Location</span></a></li>
		</ul>
		
		<!-- Access Points Menu -->
		
		<ul class="middleNavA" id="AccessPointManagementInnerMenu" style="display:none;">
			<li><a class="tipN" title="Access Points" href="#"
				onclick="RenderLinkInner('accesspointindex')"><img alt=""
					src="<c:url value='/resources/images/icons/color/access.png'/>" width="80px" height="50px"><span id="">
						Access Points</span></a></li>

		</ul>
		
		<!-- Mail Room Menu -->
		<ul class="middleNavA" id="IncomingMailsInnerMenu" style="display:none;">
			<li><a title="Enter MailRoom Consignments" href="#"
				onclick="RenderLinkInner('mailroom')"><img alt=""
					src="<c:url value='/resources/images/icons/color/mails-icon.png'/>" height="50" width="50" ><span id="mNavUsers">
						Incoming Mails</span></a></li>
			<li><a title="Update MailRoom Consignment Deliveries" href="#"
				onclick="RenderLinkInner('mailroomDelivery')"><img alt=""
					src="<c:url value='/resources/images/icons/color/checklist-icon.png'/>" height="50" width="60" ><span id="mNavUserRole">
						Delivery List</span></a></li>
		</ul>
		
		<!-- Procurment process Management Menu -->
		<ul class="middleNavA" id="RequisitionInnerMenu" style="display:none;">
			<li><a title="Requisition" href="#"
				onclick="RenderLinkInner('requisitions')"><img alt="" width="50px" height="50px"
					src="<c:url value='/resources/images/icons/color/requisition.jpg'/>"><span id="mNavUsers">
						Requisition</span></a></li>
						
		    <li><a title="Enter Vendor Contracts Details" href="#"
				onclick="RenderLinkInner('vendorContracts')"><img alt="" width="50px" height="50px"
					src="<c:url value='/resources/images/icons/color/vendorContracts.png'/>"><span id="mNavUserRole">
						Vendor Contracts</span></a></li>
						
			<li><a title="Enter Vendor Invoices" href="#" 
				onclick="RenderLinkInner('vendorInvoices')"><img alt="" width="50px" height="50px"
					src="<c:url value='/resources/images/icons/color/vendorInvoices.png'/>"><span id="mNavUserRole">
						Vendor Invoices</span></a></li>
						
			<%-- <li><a title="Item Master" href="#"
				onclick="RenderLinkInner('itemMaster')"><img alt=""
					src="<c:url value='/resources/images/icons/color/business-contact.png'/>"><span id="mNavUserRole">
						Item Master</span></a></li> --%>
						
			<%-- <li><a title="Vendor PriceList" href="#"
				onclick="RenderLinkInner('vendorPriceList')"><img alt="" height="30px"
					src="<c:url value='/resources/images/icons/color/vendorPriceList.jpg'/>"><span id="mNavUserRole">
						Vendor PriceList</span></a></li> --%>
			<%-- <li><a title="Vendor PriceList" href="#"
				onclick="RenderLinkInner('reqDetails')"><img alt=""
					src="<c:url value='/resources/images/icons/color/order-149.png'/>"><span id="mNavUserRole">
						Requisition Details</span></a></li> --%>
		</ul>
		
		<!-- Tariff Management Menu -->
		<ul class="middleNavA" id="TariffManagementInnerMenu" style="display:none;">
			<li><a title="Manage Project" href="#" onclick="RenderLinkInner('tariffmaster')">
					<img alt="" src="<c:url value='/resources/images/icons/comimages/persons.png'/>">
						<span id="mNavProject">Tariff Master</span></a></li>
						
			<li><a title="Manage Project" href="#" onclick="RenderLinkInner('rateMaster')">
					<img alt="" src="<c:url value='/resources/images/icons/comimages/persons.png'/>">
						<span id="mNavProject">Rate Master</span></a></li>
						
			<li><a title="Manage Project" href="#" onclick="RenderLinkInner('tariffCalc')">
					<img alt="" src="<c:url value='/resources/images/icons/comimages/persons.png'/>">
						<span id="mNavProject">Tariff Calc</span></a></li>
		 </ul>
		 
		 <!-- Time and Attendance Menu -->
		 
		 <ul class="middleNavA" id="PatrolTracksInnerMenu" style="display:none;">
			<li><a title="Manage Patrol Tracks" href="#"
				onclick="RenderLinkInner('patrolTracks')"><img alt=""
					src="<c:url value='/resources/images/icons/color/track2.png'/>" height="50" width="50"><span id="mNavPt">
						Patrol Tracks</span></a></li>
			<li><a title="Manage Patrol Points" href="#"
				onclick="RenderLinkInner('PatrolTrackPoints')"><img alt=""
					src="<c:url value='/resources/images/icons/color/point.png'/>" height="50" width="50"><span id="mNavPtp">
						Patrol Track Point</span></a></li>
						
			<li><a title="Manage Patrol Staff" href="#"
				onclick="RenderLinkInner('patrolTrackStaff')"><img alt=""
					src="<c:url value='/resources/images/icons/color/Police-Officer-icon.png'/>" height="50" width="50"><span id="mNavPtpS">
						Patrol Staff</span></a></li>	
						
			<li><a title="Manage Patrol Alert" href="#"
				onclick="RenderLinkInner('patrolTrackAlert')"><img alt=""
					src="<c:url value='/resources/images/icons/color/alert-icon.png'/>" height="50" width="50"><span id="mNavPtA">
						Patrol Alerts</span></a></li>	
						
						<li><a title="Manage Patrol Settings" href="#"
				onclick="RenderLinkInner('patrolSettings')"><img alt=""
					src="<c:url value='/resources/images/icons/color/settings.png'/>" height="50" width="50"><span id="mNavPs">
						Patrol Settings</span></a></li>
						
						<%-- <li><a title="Manage Staff Attendance Summary" href="#"
				onclick="RenderLinkInner('staffAttendanceSummary')"><img alt=""
					src="<c:url value='/resources/images/icons/color/administrative-docs.png'/>"><span id="mNavAtt">
						Staff Attendance Summary</span></a></li>	
						
						<li><a title="Manage Staff Attendance Gateout Alert" href="#"
				onclick="RenderLinkInner('staffAttendanceGateOutAlert')"><img alt=""
					src="<c:url value='/resources/images/icons/color/administrative-docs.png'/>"><span id="mNavGateout">
						Staff Attendance Gateout Alert</span></a></li>	 --%>
		</ul>
			 
		<!-- Time & Attendance Staff Menu -->
		<ul class="middleNavA" id="StaffInnerMenu" style="display:none;">
						<li><a title="Manage Staff Attendance Summary" href="#"
				onclick="RenderLinkInner('staffAttendanceSummary')"><img alt=""
					src="<c:url value='/resources/images/icons/color/Attendence.png'/>" height="50" width="50"><span id="mNavAtt">
						Attendance</span></a></li>	
						
						<li><a title="Manage Staff Attendance Gateout Alert" href="#"
				onclick="RenderLinkInner('staffAttendanceGateOutAlert')"><img alt=""
					src="<c:url value='/resources/images/icons/color/alert-icon.png'/>" height="50" width="50"><span id="mNavGateout">
						 Gate Out Alerts</span></a></li>	
						

			
		</ul>
		<!-- Concierge  Menu -->
		 
		 <ul class="middleNavA" id="ServiceBookingInnerMenu" style="display:none;">
		 <li><a title="Manage Vendor Services" href="#"
				onclick="RenderLinkInner('serviceBooking')"><img alt=""
					src="<c:url value='/resources/images/icons/color/vendorIncidents.png'/>" height="50" width="50"><span id="mNavPt">
						Service Booking</span></a></li>
						<li><a title="Manage Vendor Services" href="#"
				onclick="RenderLinkInner('vendorServices')"><img alt=""
					src="<c:url value='/resources/images/icons/color/services.png'/>" height="50" width="50"><span id="mNavPt">
						Vendor Services</span></a></li>
						<li><a title="Manage Concierge Vendors" href="#"
				onclick="RenderLinkInner('conciergeVendors')"><img alt=""
					src="<c:url value='/resources/images/icons/color/vendor2.png'/>" height="50" width="50"><span id="mNavPt">
						Concierge Vendors</span></a></li>
			<li><a title="Manage Concierge Service" href="#"
				onclick="RenderLinkInner('conciergeServices')"><img alt=""
					src="<c:url value='/resources/images/icons/color/concierge_services.png'/>" height="50" width="50"><span id="mNavPt">
						Concierge Services</span></a></li>
			
			
						
			
		</ul>
		
		<ul class="middleNavA" id="FileRepositoryInnerMenu" style="display:none;">

				<li><a class="tipN"
				title="Enter,View and Update the File Repository Details in File Repository"
				href="#" onclick="RenderLinkInner('filerepositorymaster')"><img alt=""
					src="<c:url value='/resources/images/icons/comimages/document.png'/>" height="50" width="50"><span
					id="mNavUsers">File Repository</span></a></li>
					

				<li><a class="tipN" title="Create and View the Tree of File Repository"
				href="#" onclick="RenderLinkInner('filerepository')"><img alt="" 
					src="<c:url value='/resources/images/icons/comimages/repository.png'/>" height="50" width="50"><span
					id="mNavFileRepository">File Repository Tree</span></a></li>
		</ul>
		
		
		<ul class="middleNavA" id="ServiceParameterMasterInnerMenu" style="display:none;">
			<li><a title="Update Service Parameter Master" href="#"
				onclick="RenderLinkInner('servicetasks')"><img alt=""
					src="<c:url value='/resources/images/icons/color/service.jpg'/>" height="50" width="60" ><span id="mNavUserRole">
						Service Parameter Master</span></a></li>
			<li><a title="Update Billing Parameter Master" href="#"
				onclick="RenderLinkInner('billingparameter')"><img alt=""
					src="<c:url value='/resources/images/icons/color/service.jpg'/>" height="50" width="60" ><span id="mNavUserRole">
						Billing Parameter Master</span></a></li>
			<li><a title="Update Meter Parameter Master" href="#"
				onclick="RenderLinkInner('meterparameter')"><img alt=""
					src="<c:url value='/resources/images/icons/color/service.jpg'/>" height="50" width="60" ><span id="mNavUserRole">
						Meter Parameter Master</span></a></li>
		</ul>
		
		
			 
	</div>
	<div class="divider">
		<span></span>
	</div>
	<br/>