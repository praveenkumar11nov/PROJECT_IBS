```<%@include file="/common/taglibs.jsp"%>
<style type="text/css">
.red {
	color: red;
}
</style> 

 
 <c:url value="/users/getApprovalDetails" var="readApprovalUrl1" />
 <c:url value="/manpower/createapprovalDetails" var="createApprovalUrl1" />
 
 
	<c:url value="/users/readApproval" var="readUrl" />
	<c:url value="/users/update" var="updateUrl" />

	<c:url value="/users/getLoginNames" var="loginNamesUrl" />

	<c:url value="/users/getDesignation" var="designationUrl" />
	<c:url value="/users/getDesignationList" var="designationFilterUrl" />
	<c:url value="/users/getDepartment" var="departmentUrl" />
	<c:url value="/users/getDepartmentList" var="departmentFilterUrl" />

	<c:url value="/users/getGroups" var="groupsUrl" />
	<c:url value="/users/getGroupNamesList" var="groupNamesUrl" />
	<c:url value="/users/getRoles" var="rolesUrl" />
	<c:url value="/users/getRoleNamesList" var="roleNamesUrl" />
	
	<c:url value="/users/getStatusList" var="statusUrl" />
	
	<c:url value="/users/getPersonNamesList" var="personNameFilterUrl" />
	
	<c:url value="/users/getStaffTypeList" var="staffTypeFilterUrl" />
	
	<c:url value="/users/getVendorNamesList" var="vendorNameFilterUrl" />
	
	<!-- Hierarchy Urls -->
	<c:url value="/person/readSinglePerson" var="readPersonUrl" />
	
	<c:url value="/address/read" var="readAddressUrl" />
	
	<c:url value="/contact/read" var="readContactUrl" />
	
	<c:url value="/comowner/accesscardsforperson/read" var="AccessCardsReadUrl" />
	
	<c:url value="/comowner/getAccessCardsBasedOnPersonId" var="getAccessCardsBasedOnPersonId" />
	
	<c:url value="/comowner/accesscardspermisions/read" var="AccessCardsPermissionsReadUrl" />
	<c:url value="/comowner/accesscardspermisions/create" var="AccessCardsPermissionCreateUrl" />
	<c:url value="/comowner/accesscardspermisions/update" var="AccessCardsPermissionUpdateUrl" />
	<c:url value="/comowner/accesscardspermisions/delete" var="AccessCardsPermissionDeleteUrl" />
	<c:url value="/comowner/getAccessCardsBasedOnPersonId" var="getAccessCardsBasedOnPersonId" />
	<c:url value="/comowner/accessrepositoryread/read" var="readAccessRepositoryUrl" />
	
	<c:url value="/medicalEmergencyDisability/read" var="readMedicalEmergencyDisabilityUrl" />
	
	<c:url value="/arms/read" var="readArmsUrl" />
	<c:url value="/ownerDocument/read" var="OwnerPropertyDRReadUrl" />
	
	<!-- <script type="text/javascript">

$(".page-content").ready(function(){   
 
  $('#FileDrawMgm').addClass('start');
  $('.start').css("background","blue")
});

</script> -->
	
	<kendo:grid name="grid" pageable="true" detailTemplate="ownerTemplate" change="onChange"  dataBound="userDataBound"
		resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true"
		filterable="true" groupable="true" >
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" ></kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
		 <kendo:grid-filterable-operators>
		  	<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains"/>
		 </kendo:grid-filterable-operators>
			
		</kendo:grid-filterable>
		<kendo:grid-editable mode="popup" />
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterUsers()><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>"/>
		</kendo:grid-toolbar>
		<kendo:grid-columns>
			
			<kendo:grid-column title="Image" field="image" template ="<img src='./person/getpersonimage/#=personId#' id='myImages_#=personId#' alt='No Image to Display' width='80px' height='80px'/>"
				filterable="false" width="94px" sortable="false"/>
				
			<kendo:grid-column title="Person Name" field="personName" filterable="true" width="130px">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function personNameFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter first name",
									dataSource : {
										transport : {
											read : "${personNameFilterUrl}/Staff"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	    	
			</kendo:grid-column>	
				
			<%-- <kendo:grid-column title="User ID" field="urId" filterable="false"
				width="100px"/> --%>
			<kendo:grid-column title="Login Name" field="urLoginName" 
				 width="110px">
				<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function loginNameFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter login name",
									dataSource: {
										transport: {
											read: "${loginNamesUrl}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	        
			</kendo:grid-column>

			 <kendo:grid-column title="Person ID" field="personId" width="0px" hidden="true"/> 
				
			<kendo:grid-column title="Staff Type" field="staffType" filterable="true" width="90px">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function staffTypeFilter(element) {
								element.kendoDropDownList({
									optionLabel: "Select",
									dataSource : {
										transport : {
											read : "${staffTypeFilterUrl}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	    	
			</kendo:grid-column>	
				
			<%-- <kendo:grid-column title="Vendor ID" field="vendorId" width="100px"/> --%>
			
			<kendo:grid-column title="Vendor Name" field="vendorName" filterable="true" width="130px">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function vendorNameFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter first name",
									dataSource : {
										transport : {
											read : "${vendorNameFilterUrl}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	    	
			</kendo:grid-column>	
		
			<kendo:grid-column title="Department" field="dept_Name" 
				editor="departmentEditor" 
				width="110px">
				<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function departmentFilter(element) {
								element.kendoDropDownList({
									optionLabel: "Select",
									dataSource : {
										transport : {
											read : "${departmentFilterUrl}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	    	
			</kendo:grid-column>

			<kendo:grid-column title="Designation" field="dn_Name" editor="designationEditor" 
				width="110px">
				<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function designationFilter(element) {
								element.kendoDropDownList({
									optionLabel: "Select",
									dataSource : {
										transport : {
											read : "${designationFilterUrl}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	    	
			</kendo:grid-column>	
	
			<kendo:grid-column title="Roles" field="roles" editor="rolesEditor" 
				 width="150px" filterable="false">
  	
			</kendo:grid-column>
			<kendo:grid-column title="Groups" field="groups"
				editor="groupsEditor" width="150px" filterable="false">
			</kendo:grid-column>

			<kendo:grid-column title="User Status" field="status" width="90px" >
				 <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function statusFilter(element) {
								element.kendoDropDownList({
									optionLabel: "Select",
									dataSource : {
										transport : {
											read : "${statusUrl}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	    	
			</kendo:grid-column>

			<%-- <kendo:grid-column title="&nbsp;" width="90px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit" click="edit" /> 
				</kendo:grid-column-command>
			</kendo:grid-column> --%>
			<%-- <kendo:grid-column title=""
				template="<a href='\\\#' id='temPID#=data.urId#' onClick='changeStatus(this.text)' class='k-button k-button-icontext btn-destroy k-grid-Activate#=data.urId#'>#= data.status == 'Inactive' ? 'Activate' : data.status == 'Inprogress' ? 'Approved' : data.status == 'Approved' ? 'De-activate' : data.status == 'Active' ? 'De-activate' : data.status == 'Rejected'? 'Activate' : 'Activate' #</a>"
				width="100px" /> --%>
			
			<%-- <kendo:grid-column title="&nbsp;" width="100px" >
            	<kendo:grid-column-command>
            		<kendo:grid-column-commandItem name="resetPassword" text="Reset Password" click="resetPassword"/>
            	</kendo:grid-column-command>
			</kendo:grid-column> --%>
				
		</kendo:grid-columns>
		<kendo:dataSource requestStart="onRequestStart" requestEnd="onRequestEnd">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-read url="${readUrl}" dataType="json"
					type="POST" contentType="application/json" />
				<kendo:dataSource-transport-update url="${updateUrl}"
					dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-parameterMap>
					<script>
						function parameterMap(options, type) {
							return JSON.stringify(options);
						}
					</script>
				</kendo:dataSource-transport-parameterMap>
			</kendo:dataSource-transport>
			<kendo:dataSource-schema parse="parseUsers">
				<kendo:dataSource-schema-model id="urId" >
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="urId" editable="false" />
						
						<kendo:dataSource-schema-model-field name="urLoginName"	 type="string">
							<kendo:dataSource-schema-model-field-validation required="true"	min="2" max="50" />
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="personId" type="number" />
						<kendo:dataSource-schema-model-field name="personName" type="string"/>
						<kendo:dataSource-schema-model-field name="drGroupId" type="number"/>
						<kendo:dataSource-schema-model-field name="staffType" type = "string"/>
						<kendo:dataSource-schema-model-field name="vendorId" type="number" />
						<kendo:dataSource-schema-model-field name="vendorName" type="string" />

						<kendo:dataSource-schema-model-field name="roles">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="groups">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="dept_Name" >
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="dn_Name" >
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="status" defaultValue="Inactive" type="string" />
						<kendo:dataSource-schema-model-field name="reqInLevel" type="number" />
						<kendo:dataSource-schema-model-field name="lNum" type="number" />
							<%-- <kendo:dataSource-schema-model-field name="status" defaultValue="Inactive" type="string" /> --%>
							
<%-- 							<kendo:dataSource-schema-model-field name="createdBy"
							type="string" editable="false" defaultValue="Admin" />
						<kendo:dataSource-schema-model-field name="lastUpdatedBy"
							type="string" editable="false" defaultValue="Admin" />
						<kendo:dataSource-schema-model-field name="lastUpdatedDt"
							type="date" editable="false" /> --%>
							
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
	</kendo:grid>
	<div id="alertsBox" title="Alert"></div>
	
	<kendo:grid-detailTemplate id="ownerTemplate">
    <kendo:tabStrip name="tabStrip_#=personId#">
        <kendo:tabStrip-items>
             <%-- <kendo:tabStrip-item text="Personal Details" selected="true">
                <kendo:tabStrip-item-content>
                 <div class="wethear">
                       <kendo:grid name="gridPerson_#=personId#" resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true" >
					        <kendo:grid-columns>	
					       		
								<kendo:grid-column title="Type" field="personType" width="140px"/>
								<kendo:grid-column title="Father Name" field="fatherName" width="140px"/>
					            <kendo:grid-column title="Marital Status" field="maritalStatus" width="100"/>
								<kendo:grid-column title="Spouse Name" field="spouseName" width="120"/>
								<kendo:grid-column title="Sex" field="sex" width="80"/>
								<kendo:grid-column title="Date of birth" field="dob" width="120"/>
								<kendo:grid-column title="Age" field="age" width="80"/>
								<kendo:grid-column title="Nationality" field="nationality" width="80"/>
								<kendo:grid-column title="Blood Group" field="bloodGroup" width="100"/>
								<kendo:grid-column title="Occupation" field="occupation" width="100"/>
								<kendo:grid-column title="Work Nature" field="workNature" width="100"/>
								<kendo:grid-column title="Languages" field="languagesKnown" width="100"/>
				
					        </kendo:grid-columns>
					        <kendo:dataSource>
					            <kendo:dataSource-transport>
					                <kendo:dataSource-transport-read url="${readPersonUrl}/#=personId#" dataType="json" type="POST" contentType="application/json"/>
					               <kendo:dataSource-transport-parameterMap>
					                	<script>
						                	function parameterMap(options,type) 
						                	{
						                		return JSON.stringify(options);	                		
						                	}
					                	</script>
					                 </kendo:dataSource-transport-parameterMap>
					       		</kendo:dataSource-transport>
					       		<kendo:dataSource-schema>
							       	<kendo:dataSource-schema-model id="personId">
										<kendo:dataSource-schema-model-fields>
											<kendo:dataSource-schema-model-field name="gender" type="string"/>
											<kendo:dataSource-schema-model-field name="personType" type="string"/>
											<kendo:dataSource-schema-model-field name="fatherName" type="string"/>
											<kendo:dataSource-schema-model-field name="maritalStatus" type="string"/>
											<kendo:dataSource-schema-model-field name="spouseName" type="string" />
											<kendo:dataSource-schema-model-field name="sex" type="string"/>
											<kendo:dataSource-schema-model-field name="dob" type="date" defaultValue=""/>
											<kendo:dataSource-schema-model-field name="age" type="number"/>
											<kendo:dataSource-schema-model-field name="nationality" type="string"/>
											<kendo:dataSource-schema-model-field name="bloodGroup" type="string"/>
											<kendo:dataSource-schema-model-field name="occupation" type="string"/>
											<kendo:dataSource-schema-model-field name="workNature" type="string"/>
											<kendo:dataSource-schema-model-field name="languagesKnown" />
							        	</kendo:dataSource-schema-model-fields>
						            </kendo:dataSource-schema-model>
					            </kendo:dataSource-schema>
					        </kendo:dataSource>
					 </kendo:grid> 
					 </div>
                </kendo:tabStrip-item-content>
            </kendo:tabStrip-item> --%>
                           
				<kendo:tabStrip-item text="Address" selected="true">
					<kendo:tabStrip-item-content>
						<div class="wethear">
							<kendo:grid name="gridAddress_#=personId#" pageable="true"
								resizable="true" sortable="true" reorderable="true"
								selectable="true" scrollable="true">
								<%--  <kendo:grid name="grid" pageable="true" resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true" > --%>
								<kendo:grid-pageable pageSize="10"></kendo:grid-pageable>
								<kendo:grid-columns>
									<kendo:grid-column title="Location" field="addressLocation"
										 width="100px" />
									<kendo:grid-column title="Primary" field="addressPrimary"
										 width="100px" />
									<kendo:grid-column title="Address No" format=""
										field="addressNo" width="0px" hidden="true"/>

									<kendo:grid-column title="Address" field="address"
										width="100px" />

									<kendo:grid-column title="Address Line 1" field="address1"
										 width="0px" hidden="true" />
									<kendo:grid-column title="Address Line 2" field="address2"
										 width="0px" hidden="true" />
									<kendo:grid-column title="Address Line 3" field="address3"
										 width="0px" hidden="true" />
										
									<kendo:grid-column title="Country" field="countryId"
										hidden="true"  width="120px" />
									<kendo:grid-column title="Other Country" field="countryotherId"
										width="0px" hidden="true" />
									<kendo:grid-column title="Country" field="countryName"
										width="100px" />
										
									<kendo:grid-column title="State/Province" field="stateId"
										hidden="true"  width="120px" />
									<kendo:grid-column title="Other State" field="stateotherId"
										width="0px" hidden="true" />
									<kendo:grid-column title="State/Province" field="stateName"
										 width="120px" />
										
									<kendo:grid-column title="City" field="cityId" hidden="true"
										 width="120px" />
									<kendo:grid-column title="Other City" field="cityotherId"
										width="0px" hidden="true" />
									<kendo:grid-column title="City" field="cityName"
										 width="120px" />
										
									<kendo:grid-column title="Pin/Zip Code" format=""
										field="pincode" width="150px" />
									<%--  <kendo:grid-column title="Contact Id" field="addressContactId"  width="100px"/>  --%>
									<kendo:grid-column title="Seasonal" field="addressSeason"
										filterable="false" sortable="false" width="0px" hidden="true" />
									<kendo:grid-column title="Season From"
										field="addressSeasonFrom" format="{0:dd/MM/yyyy}"
										filterable="true" width="100px" />
									<kendo:grid-column title="Season To" field="addressSeasonTo"
										format="{0:dd/MM/yyyy}" filterable="true" width="100px" />

									<%--        <kendo:grid-column title="Created By" field="createdBy" filterable="true" width="120px"/>
					            <kendo:grid-column title="Last Updated By" field="lastUpdatedBy" filterable="true" width="120px" />
					           	<kendo:grid-column title="Last Updated Date" field="lastUpdatedDt"
									template="#= kendo.toString(lastUpdatedDt, 'dd/MM/yy')#"
									filterable="false" width="120px" /> --%>
								</kendo:grid-columns>
								<kendo:dataSource>
									<kendo:dataSource-transport>
										<kendo:dataSource-transport-read
											url="${readAddressUrl}/#=personId#" dataType="json"
											type="POST" contentType="application/json" />
										<kendo:dataSource-transport-destroy
											url="${deleteAddressUrl}/#=personId#" dataType="json"
											type="POST" contentType="application/json" />
										<kendo:dataSource-transport-parameterMap>
											<script>
						                	function parameterMap(options,type) 
						                	{
						                		return JSON.stringify(options);	                		
						                	}
					                	</script>
										</kendo:dataSource-transport-parameterMap>
									</kendo:dataSource-transport>
									<kendo:dataSource-schema>
										<kendo:dataSource-schema-model id="addressId">
											<kendo:dataSource-schema-model-fields>
												<kendo:dataSource-schema-model-field name="addressId"
													editable="false" />
												<kendo:dataSource-schema-model-field name="personId"
													editable="false" />
												<kendo:dataSource-schema-model-field name="addressLocation"
													type="string">
													<kendo:dataSource-schema-model-field-validation
														required="true" />
												</kendo:dataSource-schema-model-field>
												<kendo:dataSource-schema-model-field name="addressPrimary"
													type="string">
													<kendo:dataSource-schema-model-field-validation
														required="true" />
												</kendo:dataSource-schema-model-field>
												<kendo:dataSource-schema-model-field name="addressNo"
													type="string">
													<kendo:dataSource-schema-model-field-validation
														required="true" />
												</kendo:dataSource-schema-model-field>

												<%-- <kendo:dataSource-schema-model-field name="address"/> --%>


												<kendo:dataSource-schema-model-field name="address1"
													type="string">
													<kendo:dataSource-schema-model-field-validation
														required="true" />
												</kendo:dataSource-schema-model-field>
												<kendo:dataSource-schema-model-field name="address2"
													type="string">
													<kendo:dataSource-schema-model-field-validation />
												</kendo:dataSource-schema-model-field>
												<kendo:dataSource-schema-model-field name="address3"
													type="string">
													<kendo:dataSource-schema-model-field-validation />
												</kendo:dataSource-schema-model-field>
												<kendo:dataSource-schema-model-field name="countryId">
													<kendo:dataSource-schema-model-field-validation
														required="true" />
												</kendo:dataSource-schema-model-field>
												<kendo:dataSource-schema-model-field name="countryotherId"
													type="string">
													<kendo:dataSource-schema-model-field-validation />
												</kendo:dataSource-schema-model-field>
												<kendo:dataSource-schema-model-field name="countryName"
													type="string">
													<kendo:dataSource-schema-model-field-validation />
												</kendo:dataSource-schema-model-field>
												<kendo:dataSource-schema-model-field name="stateId">
													<kendo:dataSource-schema-model-field-validation
														required="true" />
												</kendo:dataSource-schema-model-field>
												<kendo:dataSource-schema-model-field name="stateotherId"
													type="string">
													<kendo:dataSource-schema-model-field-validation />
												</kendo:dataSource-schema-model-field>
												<kendo:dataSource-schema-model-field name="stateName"
													type="string">
													<kendo:dataSource-schema-model-field-validation />
												</kendo:dataSource-schema-model-field>
												<kendo:dataSource-schema-model-field name="cityId">
													<kendo:dataSource-schema-model-field-validation
														required="true" />
												</kendo:dataSource-schema-model-field>



												<kendo:dataSource-schema-model-field name="cityotherId"
													type="string">
													<kendo:dataSource-schema-model-field-validation />
												</kendo:dataSource-schema-model-field>
												<kendo:dataSource-schema-model-field name="cityName"
													type="string">
													<kendo:dataSource-schema-model-field-validation />
												</kendo:dataSource-schema-model-field>
												<kendo:dataSource-schema-model-field name="pincode"
													type="number">
													<kendo:dataSource-schema-model-field-validation
														required="true" />
												</kendo:dataSource-schema-model-field>
												<kendo:dataSource-schema-model-field name="addressContactId"
													type="number">
													<kendo:dataSource-schema-model-field-validation
														required="true" />
												</kendo:dataSource-schema-model-field>
												<kendo:dataSource-schema-model-field name="addressSeason"
													type="boolean" defaultValue="false" />

												<kendo:dataSource-schema-model-field
													name="addressSeasonFrom" type="date">
													<kendo:dataSource-schema-model-field-validation />
												</kendo:dataSource-schema-model-field>
												<kendo:dataSource-schema-model-field name="addressSeasonTo"
													type="date">
													<kendo:dataSource-schema-model-field-validation />
												</kendo:dataSource-schema-model-field>

												<%-- 					                        <kendo:dataSource-schema-model-field name="createdBy" type="string" editable="false">
					                        	<kendo:dataSource-schema-model-field-validation required = "true" />
					                        </kendo:dataSource-schema-model-field>
					                        <kendo:dataSource-schema-model-field name="lastUpdatedBy" type="string" editable="false">
					                        	<kendo:dataSource-schema-model-field-validation required = "true" />
					                        </kendo:dataSource-schema-model-field>
					                        <kendo:dataSource-schema-model-field name="lastUpdatedDt" type="date" editable="false">
												<kendo:dataSource-schema-model-field-validation />
					                        </kendo:dataSource-schema-model-field> --%>

											</kendo:dataSource-schema-model-fields>
										</kendo:dataSource-schema-model>
									</kendo:dataSource-schema>
								</kendo:dataSource>
							</kendo:grid>
						</div>
						</kendo:tabStrip-item-content>
						</kendo:tabStrip-item>
						
						<kendo:tabStrip-item text="Contact">
					<kendo:tabStrip-item-content>
						<div class="wethear">
							<kendo:grid name="gridContact_#=personId#" pageable="true"
								resizable="true" sortable="true" reorderable="true"
								selectable="true" scrollable="true">
								<kendo:grid-pageable pageSize="10"></kendo:grid-pageable>
								<kendo:grid-columns>

									<%-- <kendo:grid-column title="Contact ID" field="contactId" filterable="false" width="100px"/> 
					        	<kendo:grid-column title="Person Id" field="personId" filterable="true" width="100px" /> --%>
								
									<kendo:grid-column title="Adress" field="addressId"
										 width="0px" hidden="true" />
								
									<kendo:grid-column title="Location" field="contactLocation"
										 width="100px" />
									<kendo:grid-column title="Type" field="contactType"
										 width="100px" />
									<kendo:grid-column title="Primary" field="contactPrimary"
										 width="120px" />

									<kendo:grid-column title="Contact Content"
										field="contactContent" width="120px" />
									<kendo:grid-column title="Preferred Time"
										field="contactPreferredTime" format="{0:HH:mm}"
										 width="120px" />
									<kendo:grid-column title="Seasonal" field="contactSeason"
										filterable="false" sortable="false" width="1px" />
									<kendo:grid-column title="Season From"
										field="contactSeasonFrom" format="{0:dd/MM/yyyy}"
										filterable="true" width="100px" />
									<kendo:grid-column title="Season To" field="contactSeasonTo"
										format="{0:dd/MM/yyyy}" filterable="true" width="100px" />
								</kendo:grid-columns>
								<kendo:dataSource>
									<kendo:dataSource-transport>
										<kendo:dataSource-transport-read
											url="${readContactUrl}/#=personId#" dataType="json"
											type="POST" contentType="application/json" />
										 
										 
										<kendo:dataSource-transport-destroy
											url="${deleteContactUrl}/#=personId#" dataType="json"
											type="POST" contentType="application/json" />
										<kendo:dataSource-transport-parameterMap>
											<script>
						                	function parameterMap(options,type) 
						                	{
						                		return JSON.stringify(options);	                		
						                	}
					                	</script>
										</kendo:dataSource-transport-parameterMap>
									</kendo:dataSource-transport>
									<kendo:dataSource-schema>
										<kendo:dataSource-schema-model id="contactId">
											<kendo:dataSource-schema-model-fields>
												<kendo:dataSource-schema-model-field name="contactId"
													editable="false" />
												<kendo:dataSource-schema-model-field name="personId"
													editable="false" />
													
												<kendo:dataSource-schema-model-field name="addressId" type="number"/>
												<kendo:dataSource-schema-model-field name="contactLocation"
													type="string">
													<kendo:dataSource-schema-model-field-validation
														required="true" />
												</kendo:dataSource-schema-model-field>
												<kendo:dataSource-schema-model-field name="contactType"
													type="string">
													<kendo:dataSource-schema-model-field-validation
														required="true" />
												</kendo:dataSource-schema-model-field>
												<kendo:dataSource-schema-model-field name="contactPrimary"
													type="string">
													<kendo:dataSource-schema-model-field-validation
														required="true" />
												</kendo:dataSource-schema-model-field>
												<kendo:dataSource-schema-model-field name="contactContent"
													type="string">
													<kendo:dataSource-schema-model-field-validation
														required="true" />
												</kendo:dataSource-schema-model-field>
												<kendo:dataSource-schema-model-field
													name="contactPreferredTime" type="string">
													<kendo:dataSource-schema-model-field-validation />
												</kendo:dataSource-schema-model-field>
												<kendo:dataSource-schema-model-field name="contactSeason"
													type="boolean" defaultValue="false" />

												<kendo:dataSource-schema-model-field
													name="contactSeasonFrom" type="date">
													<kendo:dataSource-schema-model-field-validation />
												</kendo:dataSource-schema-model-field>
												<kendo:dataSource-schema-model-field name="contactSeasonTo"
													type="date">
													<kendo:dataSource-schema-model-field-validation />
												</kendo:dataSource-schema-model-field>
											</kendo:dataSource-schema-model-fields>
										</kendo:dataSource-schema-model>
									</kendo:dataSource-schema>
								</kendo:dataSource>
							</kendo:grid>
						</div>
					</kendo:tabStrip-item-content>
				</kendo:tabStrip-item>
				
				 
 			
 			<kendo:tabStrip-item text="Access Cards">
                <kendo:tabStrip-item-content>
                    <div class='wethear'>
                    	<kendo:grid name="gridAccessCard_#=personId#"     pageable="true"   resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true">
							<kendo:grid-pageable pageSize="10"></kendo:grid-pageable>
        						<kendo:grid-columns>
									     <%-- <kendo:grid-column title="Access Card Id" field="acId" filterable="false" width="120px"/> --%>
									     <%-- <kendo:grid-column title="Person Id="personId" ></kendo:grid-column> --%>
									     
									    <%--  <kendo:grid-column title="Person Name" editor="PersonNames" field="personId"></kendo:grid-column> --%>
									     <kendo:grid-column title="Access Type" field="acType"></kendo:grid-column>
									     <kendo:grid-column title="Access Card Number"  field="acNo"></kendo:grid-column>
									     <%-- <kendo:grid-column title="Start Date" field="acStartDate" format= "{0:dd/MM/yyyy}"   filterable="true" width="100px" />
									     <kendo:grid-column title="End Date" field="acEndDate" format= "{0:dd/MM/yyyy}"   filterable="true" width="100px" /> --%>
									     <kendo:grid-column title="Status"  field="status"></kendo:grid-column>
        						</kendo:grid-columns>
        						 <kendo:dataSource >
						            <kendo:dataSource-transport>
						                <kendo:dataSource-transport-read url="${AccessCardsReadUrl}/#=personId#" dataType="json" type="POST" contentType="application/json"/>
						               <kendo:dataSource-transport-parameterMap>
						                	<script>
							                	function parameterMap(options,type) 
							                	{
							                		return JSON.stringify(options);	                		
							                	}
						                	</script>
						                 </kendo:dataSource-transport-parameterMap>
						            </kendo:dataSource-transport>
						            <kendo:dataSource-schema>
						                <kendo:dataSource-schema-model id="acId">
						                    <kendo:dataSource-schema-model-fields>
							                    <kendo:dataSource-schema-model-field name="acId" editable="false" type="number"/>
						                    	<%-- <kendo:dataSource-schema-model-field name="personId" type="number"></kendo:dataSource-schema-model-field>   --%> 
						                    	<kendo:dataSource-schema-model-field name="acType" type="string"></kendo:dataSource-schema-model-field>
						                    	<kendo:dataSource-schema-model-field name="acNo" type="string"></kendo:dataSource-schema-model-field>
						                    	<%-- <kendo:dataSource-schema-model-field name="acStartDate" type="date"></kendo:dataSource-schema-model-field>
						                    	<kendo:dataSource-schema-model-field name="acEndDate" type="date"></kendo:dataSource-schema-model-field> --%>
						                    	<kendo:dataSource-schema-model-field name="status" type="string"></kendo:dataSource-schema-model-field>
						                    </kendo:dataSource-schema-model-fields>
						                 </kendo:dataSource-schema-model>
						             </kendo:dataSource-schema>
						          </kendo:dataSource>
        				</kendo:grid>	
                    </div>
             	</kendo:tabStrip-item-content>
            </kendo:tabStrip-item>
            
            <kendo:tabStrip-item text="Access Cards Permissions">
                <kendo:tabStrip-item-content>
                    <div class='wethear'>
                    
                    	 <%-- <kendo:dropDownList name="size_#=personId#" dataTextField="name" dataValueField="value" select="handleChange">
					        <kendo:dataSource data="${sizes}" ></kendo:dataSource>
					    </kendo:dropDownList>   --%>
					    <br/>
					    
					   <%--  <kendo:dropDownList name="accessCardAssigned_#=personId#" dataBound="setFirstSelected" optionLabel="Select Card" select="handleChange" dataTextField="acNo" dataValueField="acId" style="width:250px">
				            <kendo:dataSource>
				                <kendo:dataSource-transport>
				                   <kendo:dataSource-transport-read url="${getAccessCardsBasedOnPersonId}/#=personId#" type="POST" contentType="application/json"/>
				                   <kendo:dataSource-transport-parameterMap>
					                	<script>
						                	function parameterMap(options) {
						                		return JSON.stringify(options);
						                	}
					                	</script>
					                </kendo:dataSource-transport-parameterMap>
				                </kendo:dataSource-transport>
				            </kendo:dataSource>
        				</kendo:dropDownList> --%>
					    <br/>
                    	<!-- <input type="text" id="testId" name="testId" value="#=personId#" /> -->
                    	<br/>
                     			<kendo:grid name="gridAccessCardPermission_#=personId#"  pageable="true" 
								resizable="true" sortable="true" reorderable="true"
								selectable="true" scrollable="true">
								<kendo:grid-pageable pageSize="10"></kendo:grid-pageable>
								<kendo:grid-editable mode="popup" confirmation="Are you sure you want to remove this item?" />
						        <kendo:grid-toolbar >
						            <%-- <kendo:grid-toolbarItem name="create" text="Assign Permissions" /> --%>
						        </kendo:grid-toolbar>
        						<kendo:grid-columns>
        								<%-- <kendo:grid-column title="&nbsp;" width="100px" >
							             	<kendo:grid-column-command>
							            		<kendo:grid-column-commandItem name="edit"/>
							            	</kendo:grid-column-command>
							             </kendo:grid-column> --%>
									     <%-- <kendo:grid-column title="Access Permission Id" field="acpId" filterable="false" width="120px"/> --%>
									     <%-- <kendo:grid-column title="Access Card Id="acId" ></kendo:grid-column> --%>
									     
									    <%--  <kendo:grid-column title="Person Name" editor="PersonNames" field="personId"></kendo:grid-column> --%>
									     <%-- <kendo:grid-column title="Access Type" field="acpId"></kendo:grid-column> --%>
									    <%--  <kendo:grid-column title="Access Card"  field="acId"></kendo:grid-column> --%>
									     <kendo:grid-column title="Access Repository" editor="accessRepoEditor" hidden="true" width="0px" field="arId"></kendo:grid-column>
									    	<kendo:grid-column title="Access Repository"  field="acPointName"></kendo:grid-column>
									     <kendo:grid-column title="Start Date" field="acpStartDate" format= "{0:dd/MM/yyyy}"   filterable="true" width="100px" />
									     <kendo:grid-column title="End Date" field="acpEndDate" format= "{0:dd/MM/yyyy}"   filterable="true" width="100px" />
									     <kendo:grid-column title="Status"  field="status" ></kendo:grid-column>
									    
        								 <kendo:grid-column title="&nbsp;" width="172px" >
							            	<kendo:grid-column-command>
							            		<kendo:grid-column-commandItem name="edit"/>
							            		<kendo:grid-column-commandItem name="destroy" />
							            	</kendo:grid-column-command>
							            </kendo:grid-column>
        						</kendo:grid-columns>
        						 <kendo:dataSource >
						            <kendo:dataSource-transport>
						                <kendo:dataSource-transport-read  url="${AccessCardsPermissionsReadUrl}" dataType="json"  type="GET" contentType="application/json"/>
						               <%-- <kendo:dataSource-transport-parameterMap>
						                	<script type="text/javascript">
							                	function parameterMap(options,type) 
							                	{
							                		return JSON.stringify(options);	                		
							                	}
						                	</script>
						                 </kendo:dataSource-transport-parameterMap> --%>
						            </kendo:dataSource-transport>
						            <kendo:dataSource-schema  >
						                <kendo:dataSource-schema-model id="acpId">
						                    <kendo:dataSource-schema-model-fields>
							                    <kendo:dataSource-schema-model-field name="acpId" type="number" />
						                    	<%-- <kendo:dataSource-schema-model-field name="personId" type="number"></kendo:dataSource-schema-model-field>   --%> 
						                    	<kendo:dataSource-schema-model-field name="arId" type="number">
						                    		<kendo:dataSource-schema-model-field-validation required = "true"/>
						                    	</kendo:dataSource-schema-model-field>
						                    	<kendo:dataSource-schema-model-field name="acpStartDate" type="date" defaultValue="">
						                    			<kendo:dataSource-schema-model-field-validation required = "true"/>
						                    	</kendo:dataSource-schema-model-field>
						                    	<kendo:dataSource-schema-model-field name="acpEndDate" type="date" defaultValue="">
						                    			<kendo:dataSource-schema-model-field-validation required = "true"/>
						                    	</kendo:dataSource-schema-model-field>
						                    	<kendo:dataSource-schema-model-field name="status" type="string">
						                    		<kendo:dataSource-schema-model-field-validation required = "true"/>
						                    	</kendo:dataSource-schema-model-field>
						                    	
						                    	<kendo:dataSource-schema-model-field name="acId" type="number">
						                    	</kendo:dataSource-schema-model-field>
						                    	<kendo:dataSource-schema-model-field name="arName" type="string">
						                    		<kendo:dataSource-schema-model-field-validation required = "true"/>
						                    	</kendo:dataSource-schema-model-field>
						                    </kendo:dataSource-schema-model-fields>
						                 </kendo:dataSource-schema-model>
						             </kendo:dataSource-schema>
						          </kendo:dataSource>
        				</kendo:grid>
                    </div>
             	</kendo:tabStrip-item-content>
            </kendo:tabStrip-item>
            
           <kendo:tabStrip-item text="Documents">
                <kendo:tabStrip-item-content>
                    <div class='wethear'>
						<kendo:grid name="documentsUpload_#=personId#" pageable="true" resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true">
							<kendo:grid-pageable pageSize="10"></kendo:grid-pageable>
        						<kendo:grid-columns>
									     <%-- <kendo:grid-column title="Document Repo Id" field="drId" filterable="false" width="120px"/> --%>
									     <%-- <kendo:grid-column title="Dr Group Id" field="drGroupId" ></kendo:grid-column> --%>
									     
									     <kendo:grid-column title="Document Name" field="documentName"></kendo:grid-column>
									     <kendo:grid-column title="Document Number" field="documentNumber"></kendo:grid-column>
									     <kendo:grid-column title="Approved Status"  field="approved"></kendo:grid-column>
        								 <kendo:grid-column title="&nbsp;" width="100px" >
							            	<kendo:grid-column-command>
							            		<kendo:grid-column-commandItem name="view" click="viewDocument"/>
							            	</kendo:grid-column-command>
							            </kendo:grid-column>
        						</kendo:grid-columns>
        						 <kendo:dataSource >
						            <kendo:dataSource-transport>
						                <kendo:dataSource-transport-read url="${OwnerPropertyDRReadUrl}/#=personId#" dataType="json" type="POST" contentType="application/json"/>
						               <kendo:dataSource-transport-parameterMap>
						                	<script>
							                	function parameterMap(options,type) 
							                	{
							                		return JSON.stringify(options);	                		
							                	}
						                	</script>
						                 </kendo:dataSource-transport-parameterMap>
						            </kendo:dataSource-transport>
						            <kendo:dataSource-schema>
						                <kendo:dataSource-schema-model id="drId">
						                    <kendo:dataSource-schema-model-fields>
							                    <kendo:dataSource-schema-model-field name="drId" editable="false" />
						                    	<kendo:dataSource-schema-model-field name="approved" type="string"></kendo:dataSource-schema-model-field>   
						                    	<kendo:dataSource-schema-model-field name="documentName" type="string"></kendo:dataSource-schema-model-field>
						                    	<kendo:dataSource-schema-model-field name="documentNumber" type="string"></kendo:dataSource-schema-model-field>
						                    </kendo:dataSource-schema-model-fields>
						                 </kendo:dataSource-schema-model>
						             </kendo:dataSource-schema>
						          </kendo:dataSource>
        				</kendo:grid>		
                    </div>
                </kendo:tabStrip-item-content>
            </kendo:tabStrip-item>
            
            
            <kendo:tabStrip-item text="Medical Emergency">
                <kendo:tabStrip-item-content>
                 <div class="wethear">
                       <kendo:grid name="gridMedicalEmergency_#=personId#" pageable="true" resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true" >
						<kendo:grid-pageable pageSize="10"  ></kendo:grid-pageable>
					        <kendo:grid-columns>	
					       		<%-- <kendo:grid-column title="Medical Emergency ID" field="meId" filterable="false" width="100px"/> 
					        	<kendo:grid-column title="Person Id" field="personId" filterable="true" width="100px" /> --%>
					        	<kendo:grid-column title="Category" field="meCategory" width="100px" />
					            <kendo:grid-column title="Disability Type" field="disabilityType" width="100px"/>
					            <kendo:grid-column title="Description" field="description"  width="100px"/>
					            <kendo:grid-column title="Hospital Name" field="meHospitalName"  width="100px"/>
					            <kendo:grid-column title="Hospital Contact No" field="meHospitalContact"  width="100px"/>
					            <kendo:grid-column title="Hospital Address" field="meHospitalAddress" width="100px"/>
					
					<%--        <kendo:grid-column title="Created By" field="createdBy" filterable="true" width="120px"/>
					            <kendo:grid-column title="Last Updated By" field="lastUpdatedBy" filterable="true" width="120px" />
					           	<kendo:grid-column title="Last Updated Date" field="lastUpdatedDt"
									template="#= kendo.toString(lastUpdatedDt, 'dd/MM/yy')#"
									filterable="false" width="120px" /> --%>
					           
					             
					        </kendo:grid-columns>
					        <kendo:dataSource>
					            <kendo:dataSource-transport>
					                <kendo:dataSource-transport-read url="${readMedicalEmergencyDisabilityUrl}/#=personId#" dataType="json" type="POST" contentType="application/json"/>
					               <kendo:dataSource-transport-parameterMap>
					                	<script>
						                	function parameterMap(options,type) 
						                	{
						                		return JSON.stringify(options);	                		
						                	}
					                	</script>
					                 </kendo:dataSource-transport-parameterMap>
					            </kendo:dataSource-transport>
					            <kendo:dataSource-schema>
					                <kendo:dataSource-schema-model id="meId">
					                    <kendo:dataSource-schema-model-fields>
					                    <kendo:dataSource-schema-model-field name="meId" editable="false" />
					                    <kendo:dataSource-schema-model-field name="personId" editable="false" />					                    
					                        <kendo:dataSource-schema-model-field name="meCategory"  type="string" >
					                        	<kendo:dataSource-schema-model-field-validation required = "true"/>
					                        </kendo:dataSource-schema-model-field>
					                        <kendo:dataSource-schema-model-field name="disabilityType"  type="string" >
					                        	<kendo:dataSource-schema-model-field-validation required = "true"/>
					                        </kendo:dataSource-schema-model-field>
					                        <kendo:dataSource-schema-model-field name="description" type="string" >
					                        	<kendo:dataSource-schema-model-field-validation required = "true" />
					                        </kendo:dataSource-schema-model-field>
					                        <kendo:dataSource-schema-model-field name="meHospitalName" type="string"  >
					                        	<kendo:dataSource-schema-model-field-validation required = "true" />
					                        </kendo:dataSource-schema-model-field>
					                        <kendo:dataSource-schema-model-field name="meHospitalContact" type="string"  >
					                        	<kendo:dataSource-schema-model-field-validation required = "true" />
					                        </kendo:dataSource-schema-model-field>
					                        <kendo:dataSource-schema-model-field name="meHospitalAddress" type="string"  >
					                        	<kendo:dataSource-schema-model-field-validation required = "true" />
					                        </kendo:dataSource-schema-model-field>

<%-- 					                    <kendo:dataSource-schema-model-field name="createdBy" type="string" editable="false">
					                        	<kendo:dataSource-schema-model-field-validation required = "true" />
					                        </kendo:dataSource-schema-model-field>
					                        <kendo:dataSource-schema-model-field name="lastUpdatedBy" type="string" editable="false">
					                        	<kendo:dataSource-schema-model-field-validation required = "true" />
					                        </kendo:dataSource-schema-model-field>
					                        <kendo:dataSource-schema-model-field name="lastUpdatedDt" type="date" editable="false">
												<kendo:dataSource-schema-model-field-validation />
					                        </kendo:dataSource-schema-model-field> --%>
					                    </kendo:dataSource-schema-model-fields>
					                </kendo:dataSource-schema-model>
					            </kendo:dataSource-schema>
					        </kendo:dataSource>
					 </kendo:grid> 
					 </div>
                </kendo:tabStrip-item-content>
            </kendo:tabStrip-item>
            
            <kendo:tabStrip-item text="Arms">
                <kendo:tabStrip-item-content>
                    <div class="wethear">
                       <kendo:grid name="gridArms_#=personId#" pageable="true" resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true" >
						<kendo:grid-pageable pageSize="10"  ></kendo:grid-pageable>
							 
					        <kendo:grid-columns>	
					       		<%-- <kendo:grid-column title="Arm ID" field="armsId" filterable="false" width="100px"/> 
					        	<kendo:grid-column title="Person Id" field="personId" filterable="true" width="100px" /> --%>
					        	<kendo:grid-column title="Type" field="typeOfArm" width="100px" />
					            <kendo:grid-column title="Make" field="armMake" width="100px"/>
					            <kendo:grid-column title="Licence No" field="licenceNo" width="120px"/>

					            <kendo:grid-column title="Licence Validity" field="licenceValidity"  format= "{0:dd/MM/yyyy}" width="120px"/>
					            <%-- <kendo:grid-column title="Dr Group Id" field="drGroupId"  width="120px" /> --%>
					            <kendo:grid-column title="Issuing Authority" field="issuingAuthority" width="120px"/>
					            <kendo:grid-column title="No Of Rounds" field="noOfRounds" format="" width="100px" />
					
					<%--        <kendo:grid-column title="Created By" field="createdBy" filterable="true" width="120px"/>
					            <kendo:grid-column title="Last Updated By" field="lastUpdatedBy" filterable="true" width="120px" />
					           	<kendo:grid-column title="Last Updated Date" field="lastUpdatedDt"
									template="#= kendo.toString(lastUpdatedDt, 'dd/MM/yy')#"
									filterable="false" width="120px" /> --%>
					        </kendo:grid-columns>
					        <kendo:dataSource>
					            <kendo:dataSource-transport>
					                <kendo:dataSource-transport-read url="${readArmsUrl}/#=personId#" dataType="json" type="POST" contentType="application/json"/>
					               <kendo:dataSource-transport-parameterMap>
					                	<script>
						                	function parameterMap(options,type) 
						                	{
						                		return JSON.stringify(options);	                		
						                	}
					                	</script>
					                 </kendo:dataSource-transport-parameterMap>
					            </kendo:dataSource-transport>
					            <kendo:dataSource-schema>
					                <kendo:dataSource-schema-model id="armsId">
					                    <kendo:dataSource-schema-model-fields>
					                    <kendo:dataSource-schema-model-field name="armsId" editable="false" />
					                    <kendo:dataSource-schema-model-field name="personId" editable="false" />					                    
					                        <kendo:dataSource-schema-model-field name="typeOfArm"  type="string" >
					                        	<kendo:dataSource-schema-model-field-validation />
					                        </kendo:dataSource-schema-model-field>
					                        <kendo:dataSource-schema-model-field name="armMake"  type="string" >
					                        	<kendo:dataSource-schema-model-field-validation />
					                        </kendo:dataSource-schema-model-field>
					                        <kendo:dataSource-schema-model-field name="licenceNo" type="string" >
					                        	<kendo:dataSource-schema-model-field-validation  />
					                        </kendo:dataSource-schema-model-field>
					                        <kendo:dataSource-schema-model-field name="licenceValidity" type="date" defaultValue="" >
					                        	<kendo:dataSource-schema-model-field-validation  />
					                        </kendo:dataSource-schema-model-field>
					                        <kendo:dataSource-schema-model-field name="drGroupId" type="number" editable="false" >
					                        	<kendo:dataSource-schema-model-field-validation />
					                        </kendo:dataSource-schema-model-field>
					                        <kendo:dataSource-schema-model-field name="issuingAuthority" type="string"  >
					                        	<kendo:dataSource-schema-model-field-validation />
					                        </kendo:dataSource-schema-model-field>
					                        <kendo:dataSource-schema-model-field name="noOfRounds"  type="number">
					                        	<kendo:dataSource-schema-model-field-validation />
					                        </kendo:dataSource-schema-model-field>
	
<%-- 					                        <kendo:dataSource-schema-model-field name="createdBy" type="string" editable="false">
					                        	<kendo:dataSource-schema-model-field-validation required = "true" />
					                        </kendo:dataSource-schema-model-field>
					                        <kendo:dataSource-schema-model-field name="lastUpdatedBy" type="string" editable="false">
					                        	<kendo:dataSource-schema-model-field-validation required = "true" />
					                        </kendo:dataSource-schema-model-field>
					                        <kendo:dataSource-schema-model-field name="lastUpdatedDt" type="date" editable="false">
												<kendo:dataSource-schema-model-field-validation />
					                        </kendo:dataSource-schema-model-field> --%>
					                    </kendo:dataSource-schema-model-fields>
					                </kendo:dataSource-schema-model>
					            </kendo:dataSource-schema>
					        </kendo:dataSource>
					 </kendo:grid> 
					 </div>
                </kendo:tabStrip-item-content>
            </kendo:tabStrip-item>
            
        <%--     <kendo:tabStrip-item text="Photo Upload">
					<kendo:tabStrip-item-content>

						<div class="demo-section" style="width: 98% ;height: 300px; vertical-align: middle;">
						
							
							<div class="wrappers">
							<img id="myImage" src="<c:url value='/person/getpersonimage/#=personId#'/>"
								alt="" width="250px;"
								height="270px;" />
								</div><div class="wrappers" style="padding-left: 20px">
								<kendo:upload complete="oncomplete" name="files" multiple="false" >
									<kendo:upload-async autoUpload="true" saveUrl="${personImage}/#=personId#" />
								</kendo:upload>
								
							</div>
							
						</div>

					</kendo:tabStrip-item-content>
				</kendo:tabStrip-item>
						 --%>
						
						
						
			<kendo:tabStrip-item text="Approval">
					<kendo:tabStrip-item-content>
						<div class="wethear"  style='width: 55%'>
							<kendo:grid name="gridApprove_#=urId#"  pageable="true"
								resizable="true" sortable="true" reorderable="true"
								selectable="true" scrollable="true"  edit="ApprovalEvent" dataBound="approveDataBound">
								<kendo:grid-pageable pageSize="10"></kendo:grid-pageable>
								
								
								<kendo:grid-editable mode="popup" confirmation="Are you sure you want to remove this item?" />
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Action" />
			<kendo:grid-toolbarItem text="Clear_Filter" />
		</kendo:grid-toolbar>
								
								<kendo:grid-columns>

									<%-- <kendo:grid-column title="Contact ID" field="contactId" filterable="false" width="100px"/> 
					        	<kendo:grid-column title="Person Id" field="personId" filterable="true" width="100px" /> --%>
								
										 	<kendo:grid-column title="Person Name" field="person"
										 width="0px" hidden="true" />
										 <%-- 
										 <kendo:grid-column title="Person Name" field="userId1"
										 width="0px" hidden="true" /> --%>
										 
										<%--  <kendo:grid-column title="Person Name" field="app_id"
										 width="0px" hidden="true" /> --%>
										 
									<kendo:grid-column title="staffType" field="staffType"
										 width="0px" hidden="true" />
								
									
										 
										 
										 	<kendo:grid-column title="Login Name" field="loginname"
										 width="0px" hidden="true" />
										 
										 	<kendo:grid-column title="Vendor Name" field="vendorsName"
										 width="0px" hidden="true" />
										 	<kendo:grid-column title="Department" field="department"
										 width="0px" hidden="true" />
										 
										 	<kendo:grid-column title="Designation" field="desig"
										 width="0px" hidden="true" />
										 
										 	<kendo:grid-column title="Role" field="role"
										 width="0px" hidden="true" />
										 
										 	<kendo:grid-column title="Group" field="group"
										 width="0px" hidden="true" />
										 
										 <kendo:grid-column title="Approved By:" field="approvedBy"
										 width="50px" />
										 
										 <kendo:grid-column title="Approved Date" field="approveddate"
											width="60px" format="{0:dd/MM/yyyy}" filterable="false" />
										 
										 <kendo:grid-column title="Status" field="status"
										 width="30px" editor="categoryEditor"/>
								</kendo:grid-columns>
								<kendo:dataSource requestEnd="onRecApprovalRequestEnd">
								
								 <kendo:dataSource-transport >
	<kendo:dataSource-transport-read url="${readApprovalUrl1}/#=urId#" dataType="json" type="GET" contentType="application/json" /> 
			<kendo:dataSource-transport-create url="${createApprovalUrl1}/#=urId#" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-update url="${updateTransctionUrl1}/#=urId#" dataType="json" type="GET" contentType="application/json" />
			<%-- <kendo:dataSource-transport-destroy url="${deleteTransctionUrl1}/#=tId#" dataType="json" type="GET" contentType="application/json" /> --%>
		 </kendo:dataSource-transport>
								<%-- 	<kendo:dataSource-transport>
										<kendo:dataSource-transport-read
											url="${readContactUrl}/#=personId#" dataType="json"
											type="POST" contentType="application/json" />
										 
										 
										<kendo:dataSource-transport-destroy
											url="${deleteContactUrl}/#=personId#" dataType="json"
											type="POST" contentType="application/json" />
										<kendo:dataSource-transport-parameterMap>
											<script>
						                	function parameterMap(options,type) 
						                	{
						                		return JSON.stringify(options);	                		
						                	}
					                	</script>
										</kendo:dataSource-transport-parameterMap>
									</kendo:dataSource-transport> --%>
									<kendo:dataSource-schema parse="parse">
										<kendo:dataSource-schema-model id="app_id">
											<kendo:dataSource-schema-model-fields>
											<kendo:dataSource-schema-model-field name="app_id" />
											<kendo:dataSource-schema-model-field name="userId1" />
													
												<%-- <kendo:dataSource-schema-model-field name="staffType" />
												<kendo:dataSource-schema-model-field name="person" />
												<kendo:dataSource-schema-model-field name="department" />
												<kendo:dataSource-schema-model-field name="loginname" />
												<kendo:dataSource-schema-model-field name="vendorsName" />
												<kendo:dataSource-schema-model-field name="desig" />
												<kendo:dataSource-schema-model-field name="role" />
												<kendo:dataSource-schema-model-field name="group" /> --%>
											<kendo:dataSource-schema-model-field name="status" defaultValue="Approve"/>
											
												
											
											</kendo:dataSource-schema-model-fields>
										</kendo:dataSource-schema-model>
									</kendo:dataSource-schema>
								</kendo:dataSource>
							</kendo:grid>
						</div>
					</kendo:tabStrip-item-content>
				</kendo:tabStrip-item>
			</kendo:tabStrip-items>
		</kendo:tabStrip>
		
		
		
	</kendo:grid-detailTemplate>
						
	<div id="alertsBox" title="Alert"></div>
	<script>
	function approveDataBound(e) {

		var data = this.dataSource.view(), row;
		var grid = $("#grid").data("kendoGrid");
		
		
		
		
		for (var i = 0; i < data.length; i++) {

			row = this.tbody.children("tr[data-uid='" + data[i].uid + "']");
			//var userStatus =data[i].status;
			
			if (status1 != "Inprogress") {
				$(".k-grid-add", "#gridApprove_" + SelectedRow).hide();
				
			}/* else{
				$(".k-grid-add", "#gridApprove_" + SelectedRow).hide();
				} */
			}
			
		
		/*  var gview = $("#grid").data("kendoGrid");
		 var selectedItem = gview.dataItem(gview.select());
		 var selectedItems = gview.dataItem(gview.select());
		// SelectedRowId = selectedItem.personId;
		 SelectedRow=selectedItems.urId; */
		
	/* 	for (var i = 0; i < data.length; i++) {
			
			row = this.tbody.children("tr[data-uid='" + data[i].uid + "']");
			var userStatus = data[i].status;
			var urId = data[i].urId;

			if (userStatus == "Inprogress") {
				$(".k-grid-add", "#gridApprove_" + SelectedRow).hide();
			} /* else{
				$(".k-grid-add", "#gridApprove_" + SelectedRow).hide();
			} */
		
	}
	var amountRequired="";
	/* function onChange(arg) {
		alert("ghfgh");
		var gview = $("#grid").data("kendoGrid");
		var selectedItem = gview.dataItem(gview.select());
		SelectedRowId = selectedItem.urId;
		urId= selectedItem.urId;
		amountRequired = selectedItem.staffType;
	
		alert("hjkhkjhkjh"+urId);
		
	} */
	function parse(response) {
		$.each(response, function(idx, elem) {
			if (elem.approveddate === null) {
				elem.approveddate = "";
			} else {
				elem.approveddate = kendo.parseDate(new Date(elem.approveddate),
						'dd/MM/yyyy HH:mm');
			}
		});
		return response;
	}
	function clearFilterUsers()
		{
			  $("form.k-filter-menu button[type='reset']").slice().trigger("click");
			  var grid = $("#grid").data("kendoGrid");
			  grid.dataSource.read();
			  grid.refresh();
		}

	/* function categoryEditor(container, options) {
		   var data = ["Approve" , "Reject"];
		   $(
		     '<input data-text-field="" style="width:180px;" id="ownership" data-value-field="" data-bind="value:' + options.field + '" />')
		     .appendTo(container).kendoDropDownList({
		      optionLabel :"Select",
		    
		      dataSource :data            	                 	      
		   });
	}   */ 
	function categoryEditor(container, options) {
		  $(
		    '<input type="radio" name=' + options.field + ' value="Approve" checked="true" /> Approve &nbsp;&nbsp;&nbsp; <input type="radio" name=' + options.field + ' value="Reject"/> Reject &nbsp;&nbsp;&nbsp;<br>')
		    .appendTo(container);
		 }	
	var action="Approved";
	$(document).on('change', 'input[name="status"]:radio', function() {

		var radioValue = $('input[name=status]:checked').val();
		if(radioValue == "Approve")
		{
			action = "Approved";
		}else{
			action="Rejected";
		}
		
	});
		var flagUserId = "";

		function edit(e) {	 
			
			securityCheckForActions("./userManagement/users/updateButton");

			$('label[for="image"]').closest('.k-edit-label').remove();
			$('div[data-container-for="image"]').remove();
			
 			$('label[for=urLoginName]').parent().remove();   
			$('label[for=personName]').parent().remove(); 
			$('label[for=staffType]').parent().remove();   
			$('label[for=vendorName]').parent().remove();  
			$('label[for=status]').parent().remove();    
			$('label[for=personId]').parent().remove();  			
	
		 	$('input[name="staffType"]').parent().remove();
			$('input[name="personName"]').parent().remove();
			$('input[name="vendorName"]').parent().remove();
			$('input[name="personId"]').parent().remove();
			$('input[name="status"]').parent().remove();
			$('input[name="urLoginName"]').parent().remove();
				
			$(".k-edit-field").each(function () {
				$(this).find("#temPID").parent().remove();
		   	});
			
 			$(".k-window-title").text("Edit User Details");
 			
 			
 			//CLIENT SIDE VALIDATION FOR DROPDOWNS AND MULTI SELECT
			
			$(".k-grid-update").click(function () {
			       if(($("#dept_Id").val()) == "Select")
			       {
			    	   	alert("Select the department");			   
			    	   	return false;
			       }
			       
			       if(($("#dn_Id").val()) == "Select")
			       {
			    	   	alert("Select the designation");
			    	   	return false;
			       }
			       
			       if(($("#roleId").val()) == null)
			       {
			    	   	alert("Select at least one role");
			    	   	return false;
			       }
			       
			       if(($("#groupId").val()) == null)
			       {
			    	   	alert("Select at least one group");
			    	   	return false;
			       }
			});
		} 
 


		//-------------------------------------  
		
		function departmentEditor(container, options) {
			  $('<input name = "departmant" data-text-field="dept_Name" id="dept_Id" data-value-field="dept_Name" data-bind="value:' + options.field + '" required = "required"/>')
			     		.appendTo(container).kendoDropDownList({
			     			optionLabel: "Select",
							dataSource : {
								transport : {		
									read : "${departmentUrl}"
								}
							}			
				});
		}	
	 
	  
	   function designationEditor(container, options) {
			  $('<input data-text-field="dn_Name" id = "dn_Id" data-value-field="dn_Name" data-bind="value:' + options.field + '"/>')
	     		.appendTo(container).kendoDropDownList({
	     			optionLabel: "Select",
					cascadeFrom: "dept_Id", 											
					dataSource : {
						transport : {

							read : "${designationUrl}"
							
						}
					}
					
			});
	  }	
 
	   function parseUsers (response) {   
		    var data = response; //<--data might be in response.data!!!

			 for (var idx = 0, len = data.length; idx < len; idx ++) {
				var res1 = [];
				$.each(data[idx].roles, function(idx1, elem1) {
					res1.push(elem1.rlName);
				});
				res1.join(",");
				data[idx].roles = res1.sort().toString();
				
				var res2 = [];
				
				$.each(data[idx].groups, function(idx2, elem2) {
					res2.push(elem2.gr_name);
				});
				res2.join(",");
				data[idx].groups = res2.sort().toString();
			 }
			 return response;
		}
	   
		function rolesEditor(container, options) {
			var model = options.model;
			model.roles = model.rolesDummy;
			$("<select multiple='multiple' id = 'roleId'" + "data-bind='value : roles'/>")
					.appendTo(container).kendoMultiSelect({
						dataTextField : "rlName",
						dataValueField : "rlId",
						groupable: false,
						placeholder : "Select Role",
						dataSource : {
							transport : {

								read : "${rolesUrl}"
							}
						}
					});
		} 
		
		function groupsEditor(container, options) {
			var model = options.model;
			model.groups = model.groupsDummy;
			$("<select multiple='multiple' id = 'groupId' " + "data-bind='value : groups'/>")
					.appendTo(container).kendoMultiSelect({
						dataTextField : "gr_name",
						dataValueField : "gr_id",
						placeholder : "Select Group",
						dataSource : {
							transport : {

								read : "${groupsUrl}"
							}
						}
					});
		}
/* 
$("#grid").on("click", "a[id^=temPID]", function(e) {
			
			var button = $(this), enable = button.text() == "Activate";
			var widget = $("#grid").data("kendoGrid");
			var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
			
			var result=securityCheckForActionsForStatus("./userManagement/users/deleteButton");	
			
			if(result=="success"){

				if (enable) {					
					var tempArray = "";
					if(kycCompliant !=null)
					{
						tempArray = kycCompliant.split(","); 
					}
					var found = $.inArray('Staff', tempArray) > -1;

					if(kycCompliant == null || found == false)
					{
						$("#alertsBox").html("");
						$("#alertsBox").html("Cannot activate since all the required documents are not submitted");
						$("#alertsBox").dialog({
							modal : true,
							buttons : {
								"Close" : function() {
									$(this).dialog("close");
								}
							}
						});
					}
					else
					{
						//alert(" User Details send for approval!!");
						$.ajax({
							type : "POST",
							url : "./users/UserStatus/" + dataItem.id + "/activate",
							success : function(response) {
								$("#alertsBox").html("Wait,User details send for approval!! ");
									//enable = button.text() == "Send for approval!!";
								//$("#alertsBox").html(response);
								$("#alertsBox").dialog({
									modal : true,
									buttons : {
										"Close" : function() {
											$(this).dialog("close");
										}
									}
								});
								//button.text('Deactivate');
								$('#grid').data('kendoGrid').dataSource.read();
							}
						});
					}
					
				}else {
					$.ajax({
						type : "POST",
						url : "./users/UserStatus/" + dataItem.id + "/deactivate",
						success : function(response) {
							$("#alertsBox").html("User has been deactivated.");
							//$("#alertsBox").html(response);
							$("#alertsBox").dialog({
								modal : true,
								buttons : {
									"Close" : function() {
										$(this).dialog("close");
									}
								}
							});
							//button.text('Activate');
							$('#grid').data('kendoGrid').dataSource.read();
						}
					});
				} 
			
			}	
			
		});
		 */
		function onRequestStart(e)
		{
			var grid = $("#grid").data("kendoGrid");
			if(grid != null)
			{
				grid.cancelRow();
			}	
		}
		
		function onRequestEnd(e) {
			
			if (typeof e.response != 'undefined')
			{
				if (e.response.status == "READ_EXCEPTION") 
				{
					errorInfo = "";
					errorInfo = e.response.result.readException;

					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Loading User records<br>" + errorInfo);
					$("#alertsBox").dialog
					({
						modal : true,
						buttons : 
						{
							"Close" : function() 
							{
								$(this).dialog("close");
							}
						}
					});
				}
				
				else if (e.response.status == "FAIL") 
				{
					errorInfo = "";

					for (var i = 0; i < e.response.result.length; i++) 
					{
						errorInfo += (i + 1) + ". "
								+ e.response.result[i].defaultMessage + "<br>";

					}

					if (e.type == "update") {
						$("#alertsBox").html("");
						$("#alertsBox").html(
								"Error: Updating the User record<br>" + errorInfo);
						$("#alertsBox").dialog({
							modal : true,
							buttons : {
								"Close" : function() {
									$(this).dialog("close");
								}
							}
						});
					}

					var grid = $("#grid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
					return false;
				}
				
				else if (e.response.status == "SAVE_OR_UPDATE_EXCEPTION") {

					errorInfo = "";

					errorInfo = e.response.result.saveOrUpdateException;
					
					if (e.type == "update") {

						$("#alertsBox").html("");
						$("#alertsBox").html(
								"Error: Updating the User record<br>" + errorInfo);
						$("#alertsBox").dialog({
							modal : true,
							buttons : {
								"Close" : function() {
									$(this).dialog("close");
								}
							}
						});
					}

					var grid = $("#grid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
					return false;
				}
				
				else if (e.response.status == "LOAD_EXCEPTION") 
				{
					if (e.type == "update") 
					{
						$(".k-grid-Activate" + flagUserId).show();
						$("#alertsBox").html("");
						$("#alertsBox").html("User record updated successfully");
						$("#alertsBox").dialog
						({
							modal : true,
							buttons : 
							{
								"Close" : function() 
								{
									$(this).dialog("close");
								}
							}
						});
					}
					var grid = $("#grid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
					
					return false;
				}
				

				else if (e.type == "update") 
				{
					$(".k-grid-Activate" + flagUserId).show();
					$("#alertsBox").html("");
					$("#alertsBox").html("User record updated successfully");
					$("#alertsBox").dialog
					({
						modal : true,
						buttons : 
						{
							"Close" : function() 
							{
								$(this).dialog("close");
							}
						}
					});
				}
				
				
			}			
	}
	</script>
	
	<script type="text/javascript">

	var kycCompliant = "";
	var SelectedRowId = "";
	var urId = "";
	var staff="";
	var dept_Name="";
	Person="";
	dep="";
	Login="";
	vendor="";
	Desig="";
	Role="";
	Group="";



	 function onChange(arg) 
	 {
		 var gview = $("#grid").data("kendoGrid");
		 var selectedItem = gview.dataItem(gview.select());
		 var selectedItems = gview.dataItem(gview.select());
		 SelectedRowId = selectedItem.personId;
		 SelectedRow=selectedItems.urId;
		 kycCompliant = selectedItem.kycCompliant;
		 urId = selectedItem.urId;
		 status1=selectedItem.status;
		 //alert(kycCompliant);
		 this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
		// gridLangConversion();
		 /* changeInternalGridLanguage */
		 staff = selectedItem.staffType;
		 dep=selectedItem.dept_Name;
		
		 Person=selectedItem.personName;

		 Login=selectedItem.urLoginName;
		 Vendor=selectedItem.vendorName;
		 Desig=selectedItem.dn_Name;
		 Role=selectedItem.roles;
		 Group=selectedItem.groups;
	 }
	 
	 function ApprovalEvent(e){
		 $('div[data-container-for="approvedBy"]').hide();
			$('label[for="approvedBy"]').closest('.k-edit-label').hide();
			
			$('div[data-container-for="approveddate"]').hide();
			$('label[for="approveddate"]').closest('.k-edit-label').hide();
			
			
			$('input[name="staffType"]').val(staff);
			$('input[name="staffType"]').attr('readonly', 'readonly');
			$('input[name="department"]').val(dep);	
			$('input[name="department"]').attr('readonly', 'readonly');
			$('input[name="person"]').val(Person);	
			$('input[name="person"]').attr('readonly', 'readonly');
			$('input[name="loginname"]').val(Login);	
			$('input[name="loginname"]').attr('readonly', 'readonly');
			$('input[name="vendorsName"]').val(Vendor);	
			$('input[name="vendorsName"]').attr('readonly', 'readonly');
			$('input[name="desig"]').val(Desig);	
			$('input[name="desig"]').attr('readonly', 'readonly');
			
			$('input[name="role"]').val(Role);	
			$('input[name="role"]').attr('readonly', 'readonly');
			$('input[name="group"]').val(Group);	
			$('input[name="group"]').attr('readonly', 'readonly');
			
			if (e.model.isNew()) 
		    {
				$(".k-window-title").text("Add  Approval Details");
				$(".k-grid-update").text("Save");		
		    }
			else{
				$(".k-window-title").text("Edit  Approval Details");
			}

			$('div[data-container-for="userId"]').hide();
			$('label[for="userId"]').closest('.k-edit-label').hide();
			
			$('div[data-container-for="app_id"]').hide();
			$('label[for="app_id"]').closest('.k-edit-label').hide();
			
			
			}
	 var SelectedAccessCardId = "";
		function handleChange(e)
		{
			//alert("Changed>>"+SelectedRowId);
			var dataItem = this.dataItem(e.item.index());
			SelectedAccessCardId = dataItem.acId;
		  //alert("event :: select (" + dataItem.name + " : " + dataItem.value + ")" );
		    //alert(dataItem.acId);
		    if(dataItem.acId != "")
			{
				$('#gridAccessCardPermission_'+SelectedRowId).data().kendoGrid.dataSource.read({personId:dataItem.acId});
		    }
			 
		}
	   
	function viewDocument(e){
		var gview = $("#documentsUpload_"+SelectedRowId).data("kendoGrid");
		var selectedItem = gview.dataItem(gview.select());
		window.open("./download/"+selectedItem.drId);	
	}

	  function resetPassword()
	  {
		  	var result=securityCheckForActionsForStatus("./userManagement/users/resetPasswordButton");			
			if(result=="success"){
				$.ajax({
					   type : "POST",
					   url : "./users/resetPassword",
					   data :
						   {
							personId : SelectedRowId
						   },
					   success : function(response)
					   {
						   $("#alertsBox").html("");
							$("#alertsBox").html(response);
							$("#alertsBox").dialog({
								modal : true,
								buttons : {
									"Close" : function() {
										$(this).dialog("close");
									}
								}
							});

					   }	   	   

					});
			}		 
		  
	  }   
	

	function setFirstSelected(e)
	{
	        this.select(0);
	}
			var seasonFromAccessCardPermission = "";
			
			//register custom validation rules
			(function($, kendo) {
				$
						.extend(
								true,
								kendo.ui.validator,
								{
									rules : { // custom rules          	
										acpStartDate: function (input, params) 
							             {
						                     if (input.filter("[name = 'acpStartDate']").length && input.val() != "") 
						                     {                          
						                         var selectedDate = input.val();
						                         var todaysDate = $.datepicker.formatDate('dd/mm/yy', new Date());
						                         var flagDate = false;
			
						                         if ($.datepicker.parseDate('dd/mm/yy', selectedDate) >= $.datepicker.parseDate('dd/mm/yy', todaysDate)) 
						                         {
						                        	 	seasonFromAccessCardPermission = selectedDate;
						                                flagDate = true;
						                         }
						                         return flagDate;
						                     }
						                     return true;
						                 },
						                 acpEndDate1: function (input, params) 
							             {
						                     if (input.filter("[name = 'acpEndDate']").length && input.val() != "") 
						                     {                          
						                         var flagDate = false;
			
						                         if (seasonFromAccessCardPermission != "") 
						                         {
						                                flagDate = true;
						                         }
						                         return flagDate;
						                     }
						                     return true;
						                 },
						                 acpEndDate2: function (input, params) 
							             {
						                     if (input.filter("[name = 'acpEndDate']").length && input.val() != "") 
						                     {                          
						                         var selectedDate = input.val();
						                         var flagDate = false;
			
						                         if ($.datepicker.parseDate('dd/mm/yy', selectedDate) > $.datepicker.parseDate('dd/mm/yy', seasonFromAccessCardPermission)) 
						                         {
						                                flagDate = true;
						                         }
						                         return flagDate;
						                     }
						                     return true;
						                 }        
									},
									messages : {
										//custom rules messages
										acpStartDate:"Start must be selected in the future",
										acpEndDate1:"Select Start date first before selecting End date and change End date accordingly",
										acpEndDate2:"End date should be after Start date"
									}
								});
			
			})(jQuery, kendo);
			//End Of Validation

			function accessCardPermissionEvent(e)
			{
				  //alert(JSON.stringify(e.model));
				  if (e.model.isNew()) 
				    {
					  $(".k-window-title").text("Add New Acess Card Permission");
				        //set the default value for StateID
				        //alert("Coming to new Script>>"+SelectedAccessCardId);
				        if((SelectedAccessCardId == "")||(SelectedAccessCardId == ' '))
					    {
					        var grid = $("#gridAccessCardPermission_"+SelectedRowId).data("kendoGrid");
							grid.cancelRow();
							$("#alertsBox").html("");
							$("#alertsBox").html("Please Select Access Card");
							$("#alertsBox").dialog({
								modal : true,
								buttons : {
									"Close" : function() {
										$(this).dialog("close");
									}
								}
							});
							return false;
					    }
				        $(".k-window-title").text("Add New Acess Card Permission");
				        e.model.set("acId", SelectedAccessCardId );				        
				    }
				  else
				  {
					  $(".k-window-title").text("Edit Acess Card Permission Details");
				  }
			
			}
			
			  function accessRepoEditor(container, options)
			  {	
				  $('<input name="Access Repository" required="true" data-text-field="name" data-value-field="value" data-bind="value:' + options.field + '"/>')
					.appendTo(container).kendoDropDownList({	
						optionLabel : {
							name : "Select",
							value : "",
						},
						defaultValue : false,
						sortable : true,
						dataSource : {
							transport : {
								read : "${readAccessRepositoryUrl}"
							}
						}

					});

				  $('<span class="k-invalid-msg" data-for="Access Repository"></span>').appendTo(container);

			  }
			  
			   
			  function userDataBound(e) {

					var data = this.dataSource.view(), row;
					var grid = $("#grid").data("kendoGrid");
					for (var i = 0; i < data.length; i++) {
						
						row = this.tbody.children("tr[data-uid='" + data[i].urid + "']");
						var userStatus = data[i].status;
						var urId = data[i].urId;

						if (userStatus === 'Inprogress') {
							row.addClass("bgGreenColor");
							//$('a[id=temPID12258]').hide();
							$('a[id=temPID' + urId+']').hide();
							
						} else{
		
							row.addClass("bgRedColor");
							$('#temPID' + urId).show();
							//alert(" User Details send for approval!!");
							
						} 
					}
				}
		
			  
			  function onRecApprovalRequestEnd(e) 
			  { 
			    if (typeof e.response != 'undefined') {
			     if (e.response.status == "FAIL") {
			      errorInfo = "";
			      for (var i = 0; i < e.response.result.length; i++) {
			       errorInfo += (i + 1) + ". "
			         + e.response.result[i].defaultMessage + "<br>";
			      }
			        if (e.type == "create") {
			       $("#alertsBox").html("");
			       $("#alertsBox").html(
			         "Error: Creating the process details \n\n : "
			           + errorInfo);
			       $("#alertsBox").dialog({
			        modal : true,
			        buttons : {
			         "Close" : function() {
			          $(this).dialog("close");
			         }
			        }
			       });
			      }
			        var gview = $("#gridApprove_"+SelectedRow).data("kendoGrid");
					gview.dataSource.read();
					gview.refresh();
			     } 
			     else if (e.type == "create") {
			      $("#alertsBox").html("");
			      $("#alertsBox").html(
			        "User "+action+" successfully");
			      $("#alertsBox").dialog({
			       modal : true,
			       buttons : {
			        "Close" : function() {
			         $(this).dialog("close");
			        }
			       }
			      });

			      var gview = $("#gridApprove_"+SelectedRow).data("kendoGrid");
					gview.dataSource.read();
					gview.refresh();
					$('#grid').data('kendoGrid').dataSource.read();
			     } else if (e.type == "destroy") {
			      $("#alertsBox").html("");
			      $("#alertsBox").html(
			        "Recoopment Approval is deleted successfully");
			      $("#alertsBox").dialog({
			       modal : true,
			       buttons : {
			        "Close" : function() {
			         $(this).dialog("close");
			        }
			       }
			      });

			      var grid = $("gridApprove_"+SelectedRow).data("kendoGrid");
			      grid.dataSource.read();
			      grid.refresh();
			     } else if (e.type == "update") {
			      $("#alertsBox").html("");
			      $("#alertsBox").html(
			        "Recoopment Appproval is updated successfully");
			      $("#alertsBox").dialog({
			       modal : true,
			       buttons : {
			        "Close" : function() {
			         $(this).dialog("close");
			        }
			       }
			      });
			      
			      var gview = $("#gridApprove_"+SelectedRow).data("kendoGrid");
					gview.dataSource.read();
					gview.refresh();
					
			       }
			   }
			   }
			  function changeStatus(val){
	
						var tempArray = "";
						if(kycCompliant !=null)
						{
							tempArray = kycCompliant.split(","); 
						}
						var found = $.inArray('Staff', tempArray) > -1;

						if(kycCompliant == null || found == false) 
						{
							$("#alertsBox").html("");
							$("#alertsBox").html("Cannot activate since all the required documents are not submitted");
							$("#alertsBox").dialog({
								modal : true,
								buttons : {
									"Close" : function() {
										$(this).dialog("close");
									}
								}
							});
						}else{
							
							  var ask = confirm("Are You Sure? This action cannot be undone");
								if (ask == true) {
									
									if(val=='Activate')
										val='Inprogress';
									if(val=='De-activate')
										val='Inactive';
									
									
									$.ajax({
										type : "POST",
										url : "./users/updatestatus/" + urId + "/" + val,
										dataType : "text",
										success : function(response) {
											alert(response);
											if (response.status === 'DENIED') {
												alert(response.result);
											} else {
												$('#grid').data('kendoGrid').dataSource.read();
											}
										}
									});
								}
							
							
						}
				
				  
				  
				  
				  
				  
				  
			
			  }
	</script>
<!-- -------------------------------------------- STYLE ---------------------------- -->

<style>



td {
	vertical-align: middle;
}


img {
    border-radius: 8px;
   
}
.bgGreenColor{
	background: green
}


</style>
 


     


