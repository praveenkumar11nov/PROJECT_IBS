<%@include file="/common/taglibs.jsp"%>

	<!-- Urls for Common controller  -->
	<c:url value="/common/getAllChecks" var="allChecksUrl" />
	<c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />

	<!-- Urls for Concierge Vendor  -->
	<c:url value="/person/read/" var="readPersonUrl" />
	<c:url value="/person/modify" var="modifyPersonUrl" />
	<c:url value="/conciergeVendorPerson/create" var="createConciergeVendorPersonUrl" />
	
	<c:url value="/comowner/status/readstatus" var="statusReadUrl" />
	
	<!-- Urls for Person  -->
		<c:url value="/person/categories" var="categoriesReadUrlOwner" />
		
		
	<c:url value="/person/getPersonList" var="personNamesAutoUrl" />
	<c:url value="/person/getFirstName" var="firstNameUrl" />
	<c:url value="/person/getMiddleName" var="middleNameUrl" />
	<c:url value="/person/getLastName" var="lastNameUrl" />
	<c:url value="/person/getFatherName" var="fatherNameUrl" />
	<c:url value="/person/getSpouseName" var="spouseNameUrl" />
	<c:url value="/person/getOccupation" var="occupationUrl" />
	
	<c:url value="/person/getAllAttributeValues" var="filterAutoCompleteUrl" />
	
	<c:url value="/person/upload/personImage" var="personImage" />
	<c:url value="/person/getPersonStyleChecks" var="personStyleChecksUrl" />
	
	<!-- Urls for Users  -->
	<c:url value="/users/getPersonNamesList" var="personNameFilterUrl" />

	<!-- Address Grid Access Url's -->
	<c:url value="/address/read" var="readAddressUrl" />
	<c:url value="/address/create" var="createAddressUrl" />
	<c:url value="/address/update" var="updateAddressUrl" />
	<c:url value="/conciergeVendor/address/delete" var="deleteAddressUrl" />
	<c:url value="/address/addressLocationChecks" var="addressLocationUrl" />
	<c:url value="/address/addressPrimaryChecks" var="addressPrimaryUrl" />
	<c:url value="/address/getCountry" var="countryUrl" />
	<c:url value="/address/getState" var="stateUrl" />
	<c:url value="/address/getCity" var="cityUrl" />
	<c:url value="/address/addressSeasonChecks" var="addressSeasonUrl" />

	<!-- Contact Grid Access Urls -->
	<c:url value="/contact/read" var="readContactUrl" />
	<c:url value="/contact/create" var="createContactUrl" />
	<c:url value="/contact/update" var="updateContactUrl" />
	<c:url value="/conciergeVendor/contact/delete" var="deleteContactUrl" />
	<c:url value="/contact/contactLocationChecks" var="contactLocationUrl" />
	<c:url value="/contact/addressforcontact" var="addressforcontactUrl" />
	<c:url value="/contact/contactTypeChecks" var="contactTypeUrl" />
	<c:url value="/contact/contactPrimaryChecks" var="contactPrimaryUrl" />

	<c:url value="/comowner/status/readstatus" var="statusReadUrl" />

	
	<!-- Document Grid Access Urls -->
	<c:url value="/kycComplaints/upload/async/save" var="saveUrl" />
	<c:url value="/ownerDocument/read" var="OwnerPropertyDRReadUrl" />
	<c:url value="/ownerDocument/create" var="OwnerDocumentRepoCreateUrl" />
	<c:url value="/ownerDocument/update" var="OwnerDocumentRepoUpdateUrl" />
	<c:url value="/conciergeVendor/documentsDeleteUrl" var="OwnerDocumentRepoDeleteUrl" />
	<c:url value="/documentdefiner/getAllDocument" var="getDocumentType" />
	<c:url value="/documentDefiner/getDocumentFormat" var="getDocumentTypeFormat" />
	
	<!-- Vendor Comment Rate Url  -->
<c:url value="/conciergeVendors/commentRate/read" var="readVendorCommentsUrl" />
<c:url value="/conciergeVendors/commentRate/create" var="createVendorCommentsUrl" />
<c:url value="/conciergeVendors/commentRate/update" var="updateVendorCommentsUrl" />
<c:url value="/conciergeVendors/commentRate/delete" var="destroyVendorCommentsUrl" />

<c:url value="/conciergeVendors/commentRate/getOwnerNames" var="ownerNamesUrl" />
	
	<!-- Vendor Other Details Url  -->
<c:url value="/conciergeVendors/otherDetails/read" var="readVendorOtherDetailsUrl" />
<c:url value="/conciergeVendors/otherDetails/update" var="updateVendorOtherDetailsUrl" />
<c:url value="/conciergeVendors/otherDetails/delete" var="destroyVendorOtherDetailsUrl" />

<c:url value="/patrolTracks/getStatusList" var="statusUrl" />
<c:url value="/conciergeVendors/maritalStatusList" var="maritalStatusUrl" />
<c:url value="/conciergeVendors/languageList" var="languageUrl" />   
<script>
</script>
	
	

<!-- -------------------------------------------------- Grid data ------------------------------------------------------- -->

<kendo:grid name="gridPersonConciergeVendor" edit="personEventConciergeVendor" change="onChangeConciergeVendor" pageable="true" resizable="true"  detailTemplate="personTemplateConciergeVendor"
	sortable="true" reorderable="true" selectable="true" scrollable="true" groupable="true" filterable="true" navigatable="true">
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" refresh="true" info="true" previousNext="true">
			<kendo:grid-pageable-messages itemsPerPage="Concierge Vendors per page" empty="No Concierge Vendor to display" refresh="Refresh all the Concierge Vendors" 
			display="{0} - {1} of {2} Concierge Vendors" first="Go to the first page of Concierge Vendors" last="Go to the last page of Concierge Vendors" next="Go to the next page of Concierge Vendors"
			previous="Go to the previous page of Concierge Vendors"/>
		</kendo:grid-pageable> 
		
		<kendo:grid-filterable extra="false">
			<kendo:grid-filterable-operators>
				<kendo:grid-filterable-operators-string eq="Is equal to"/>
				<kendo:grid-filterable-operators-number eq="Is equal to" gt="Is greather than" gte="IS greather than and equal to" lt="Is less than" lte="Is less than and equal to" neq="Is not equal to"/>
			</kendo:grid-filterable-operators>
		</kendo:grid-filterable>
		<kendo:grid-editable mode="popup"/>
			<kendo:grid-toolbarTemplate>
			<div class="toolbar">
    			<label class="category-label" for="categoriesConciergeVendor">&nbsp;&nbsp;Select&nbsp;the&nbsp;Category&nbsp;to&nbsp;Add&nbsp;Concierge Vendor&nbsp;:&nbsp;&nbsp;</label>
	    		<kendo:dropDownList name="categoriesOwner" optionLabel="Select" dataTextField="personStyle" 
	    				dataValueField="personStyle" autoBind="false" change="categoriesChangeConciergeVendor">
	    			<kendo:dataSource>
		    			<kendo:dataSource-transport>            	
			                <kendo:dataSource-transport-read url="${categoriesReadUrlOwner}" />                
			            </kendo:dataSource-transport>
	    			</kendo:dataSource>    			   			    			
	    		</kendo:dropDownList>

				<a class="k-button k-button-icontext k-grid-add" href="#">
		            <span class="k-icon k-add"></span>
		            Add Concierge Vendor
	        	</a>
	        	
	        	<!-- <a class="k-button k-button-icontext k-grid-Clear_Filter" href="#">
					<span class=" "></span>
					Clear_Filter
				</a> -->
				
				 <a class='k-button' href='\\#' onclick="clearFilterConciergeVendor()"><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a> 
			</div>  	
				
    	</kendo:grid-toolbarTemplate>
		<kendo:grid-columns>
			<kendo:grid-column title="&nbsp;" width="100px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit" />

				</kendo:grid-column-command>
			</kendo:grid-column>
			
			<kendo:grid-column title="Person ID" field="existedPersonId" hidden="true"
				filterable="false" width="94px">
				</kendo:grid-column>
			<kendo:grid-column title="Image" field="image" template ="<span onclick='clickToUploadImage()' title='Click to Upload Image' ><img src='./person/getpersonimage/#=personId#' id='myImages_#=personId#' alt='Click to Upload Image' width='80px' height='80px'/></span>"
				filterable="false" width="94px" sortable="false">
				</kendo:grid-column>
				<kendo:grid-column title="Category&nbsp;*" field="personStyle" width="100px" filterable="true">
   				<kendo:grid-column-values value="${personStyle}"/>
			</kendo:grid-column>

			<kendo:grid-column title="Title&nbsp;*" field="title" width="60px" >
				<kendo:grid-column-values value="${title}"/>
			</kendo:grid-column>

			<kendo:grid-column title="Name&nbsp;*" field="personName"
				filterable="true" width="140px" >
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function personNameFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter first name",
									dataSource : {
										transport : {
											read : "${personNameFilterUrl}/ConciergeVendor"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="First&nbsp;Name&nbsp;*" field="firstName" width="0px"  editor="firstNameEditorConciergeVendor"
				hidden="true"/>

			<kendo:grid-column title="Middle&nbsp;Name" field="middleName" width="0px"
				hidden="true"/>

			<kendo:grid-column title="Last&nbsp;Name&nbsp;*" field="lastName" width="0px"
				hidden="true"/>

			<kendo:grid-column title="Father&nbsp;Name&nbsp;*" field="fatherName"
				width="120px">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function fatherNameFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter father name",
									dataSource : {
										transport : {
											read : "${filterAutoCompleteUrl}/ConciergeVendor/fatherName"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			<kendo:grid-column title="Marital&nbsp;Status" field="maritalStatus" editor="maritalStatusDropdownEditorConiergeVendor" width="120px">
				<kendo:grid-column-values value="${maritalStatus}"/>
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function maritalStatusFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Marital Status",
									dataSource : {
										transport: {
											read: "${maritalStatusUrl}"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
				<kendo:grid-column title="Spouse&nbsp;Name" field="spouseName" 
				filterable="true" width="120px">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function spouseNameFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter spouse name",
									dataSource : {
										transport : {
											read : "${filterAutoCompleteUrl}/ConciergeVendor/spouseName"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			
					<kendo:grid-column title="Sex" field="sex" width="60px">
				<kendo:grid-column-values value="${sex}"/>
			</kendo:grid-column>
			
		<%-- 	<kendo:grid-column title="Date&nbsp;of&nbsp;Birth&nbsp;*" field="dob"
				format="{0:dd/MM/yyyy}" template="#=dobDisplay(data)#" filterable="true"
				width="100px" /> --%>
				
			<kendo:grid-column title="Date&nbsp;of&nbsp;Birth&nbsp;*" field="dob" format="{0:dd/MM/yyyy}" width="100px"/>
				
			 <kendo:grid-column title="Age&nbsp;*" template="#=ageDisplay(data)#" field="age"
				width="60px"/>  
				
			<kendo:grid-column title="Nationality" field="nationality" width="100px">
				<kendo:grid-column-values value="${nationality}"/>
		    </kendo:grid-column>
		    		
			<kendo:grid-column title="Blood&nbsp;Group" field="bloodGroup" width="120px">
				<kendo:grid-column-values value="${bloodGroup}"/>
		    </kendo:grid-column>	
					
			<kendo:grid-column title="Occupation/Profession" field="occupation" editor="comboBoxChecksEditorConciergeVendor"
				filterable="true" width="150px">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function occupationFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter occupation",
									dataSource : {
										transport : {
											read : "${filterAutoCompleteUrl}/ConciergeVendor/occupation"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="Nature&nbsp;of&nbsp;Work" field="workNature" width="120px">
				<kendo:grid-column-values value="${workNature}"/>
		    </kendo:grid-column>	
		    	
			<kendo:grid-column title="Languages" field="languagesKnown"
				editor="languagesEditorOwner" filterable="true" width="100px" >
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function languageFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Language",
									dataSource : {
										transport: {
											read: "${languageUrl}"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable></kendo:grid-column>
				
			<kendo:grid-column title="Status" field="personStatus" width="100px" >
				 <kendo:grid-column-values value="${status}"/>
				 <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function statusFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Status",
									dataSource: {
										transport: {
											read: "${statusUrl}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	
			</kendo:grid-column>

			<!-- FOR OTHER VENDOR DETAILS -->
			<kendo:grid-column title="CST_No" field="cstNo" width="60px" hidden="true"/>
			<kendo:grid-column title="State Tax No" field="stateTaxNo" width="60px" hidden="true"/>
			<kendo:grid-column title="Service Tax No" field="serviceTaxNo" width="60px" hidden="true"/>
			<kendo:grid-column title="Status" field="status" width="60px" hidden="true"/>
			<!-- END FOR OTHER VENDOR DETAILS -->

			<kendo:grid-column title="&nbsp;" width="100px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit" />

				</kendo:grid-column-command>
			</kendo:grid-column>

		<kendo:grid-column title=""
				template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Activate#=data.personId#'>#= data.personStatus == 'Active' ? 'De-activate' : 'Activate' #</a>"
				width="100px" />
        </kendo:grid-columns>

		<kendo:dataSource requestStart="onRequestStartPersonConciergeVendor" requestEnd="onRequestEndPersonConciergeVendor">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-read url="${readPersonUrl}/ConciergeVendor" dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-create url="${createConciergeVendorPersonUrl}/create/ConciergeVendor" dataType="json" type="GET" contentType="application/json" />
				<kendo:dataSource-transport-update url="${modifyPersonUrl}/update/ConciergeVendor" dataType="json" type="GET" contentType="application/json" />
			</kendo:dataSource-transport>
			<kendo:dataSource-schema parse="parseConciergeVendor">
				<kendo:dataSource-schema-model id="personId">
					<kendo:dataSource-schema-model-fields>

						<kendo:dataSource-schema-model-field name="personName"
							type="string" />
						<kendo:dataSource-schema-model-field name="personId"
							editable="false" />
							<kendo:dataSource-schema-model-field name="existedPersonId"/>
						
						<kendo:dataSource-schema-model-field name="personType" 
							type="string" defaultValue="ConciergeVendor" editable="false"/>
						<kendo:dataSource-schema-model-field name="personStyle" defaultValue="Individual"/>
						<kendo:dataSource-schema-model-field name="title" defaultValue="Mr"/>
						<kendo:dataSource-schema-model-field name="firstName"
							type="string"/>
						<kendo:dataSource-schema-model-field name="middleName"
							type="string" />
						<kendo:dataSource-schema-model-field name="lastName" type="string">
							<kendo:dataSource-schema-model-field-validation required="true"/> 
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="fatherName" type="string">
							<kendo:dataSource-schema-model-field-validation required="true"/> 
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="maritalStatus" defaultValue="None"/>
						<kendo:dataSource-schema-model-field name="spouseName"
							type="string" />
						<kendo:dataSource-schema-model-field name="sex" defaultValue="None"/>
						<kendo:dataSource-schema-model-field name="dob" type="date" defaultValue="">
							<kendo:dataSource-schema-model-field-validation required="true"/> 
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="age"
							type="number"/>
						<kendo:dataSource-schema-model-field name="nationality" defaultValue="None" type="string"/>
						<kendo:dataSource-schema-model-field name="bloodGroup" defaultValue="None"/>
						<kendo:dataSource-schema-model-field name="occupation"
							type="string" defaultValue="None"/>
						<kendo:dataSource-schema-model-field name="workNature" defaultValue="None"/>
						<kendo:dataSource-schema-model-field name="languagesKnown" defaultValue="None"/>
						<kendo:dataSource-schema-model-field name="drGroupId"
							type="number" />
						<kendo:dataSource-schema-model-field name="kycCompliant" type="string" />
						
						<kendo:dataSource-schema-model-field name="personStatus"/>
						
						<!--  FOR VENDOR DETAILS -->
						<kendo:dataSource-schema-model-field name="vendorCategoryList" type="string"/>
						<kendo:dataSource-schema-model-field name="cstNo" type="string"/>
						<kendo:dataSource-schema-model-field name="stateTaxNo" type="string"/>
						<kendo:dataSource-schema-model-field name="serviceTaxNo" type="string"/>
						<kendo:dataSource-schema-model-field name="status" editable="false"/>
						
						<!-- END FOR VENDOR DEATILS -->

					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
	</kendo:grid>


	<kendo:grid-detailTemplate id="personTemplateConciergeVendor">
		<kendo:tabStrip name="tabStrip_#=personId#">
			<kendo:tabStrip-animation>
				<!-- <tabStrip-animation-open effects="fadeIn" /> -->
			</kendo:tabStrip-animation>
			<kendo:tabStrip-items>

				
				<kendo:tabStrip-item text="Address" selected="true">
					<kendo:tabStrip-item-content>
						<div class="wethear" style="width:50%;">
							<kendo:grid name="gridAddress_#=personId#" pageable="true" edit="addressEvent" remove="csVendorAddressRemove"
								resizable="true" sortable="true" reorderable="true"
								selectable="true" scrollable="true">
								<%--  <kendo:grid name="grid" pageable="true" resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true" > --%>
								<kendo:grid-pageable pageSize="10"></kendo:grid-pageable>
								<kendo:grid-editable mode="popup"
									confirmation="Are you sure you want to remove this Address?" />
								<kendo:grid-toolbar>
									<kendo:grid-toolbarItem name="create" text="Add Address" />
								</kendo:grid-toolbar>
								<kendo:grid-columns>

									<kendo:grid-column title="&nbsp;" width="90px">
										<kendo:grid-column-command>
											<kendo:grid-column-commandItem name="Contacts" click="contactInfo"/>
										</kendo:grid-column-command>
									</kendo:grid-column>

									<kendo:grid-column title="Address Type*" field="addressLocation"
										editor="addressLocationEditor" width="100px" />
									<kendo:grid-column title="Primary *" field="addressPrimary"
										editor="addressPrimaryEditor" width="100px" />
									<kendo:grid-column title="Property No. *" format=""
										field="addressNo" width="0px" hidden="true" />

									<kendo:grid-column title="Address *" field="address"
										width="100px" />

									<kendo:grid-column title="Address&nbsp;Line&nbsp;1*" field="address1"
										editor="address1Editor" width="0px" hidden="true" />
									<kendo:grid-column title="Address&nbsp;Line&nbsp;2" field="address2"
										editor="address2Editor" width="0px" hidden="true" />
									<kendo:grid-column title="Address&nbsp;Line&nbsp;3" field="address3"
										editor="address3Editor" width="0px" hidden="true" />
									
									
									<kendo:grid-column title="Country *" field="countryId"
										hidden="true" editor="countryEditor" width="120px" />
									<kendo:grid-column title="Other Country *" field="countryotherId"
										width="0px" hidden="true" />
									<kendo:grid-column title="Country *" field="countryName"
										width="100px" />
									<kendo:grid-column title="State/Province*" field="stateId"
										hidden="true" editor="stateEditor" width="120px" />
									<kendo:grid-column title="Other State *" field="stateotherId"
										width="0px" hidden="true" />
									<kendo:grid-column title="State/Province*" field="stateName"
										editor="stateEditor" width="120px" />
									<kendo:grid-column title="City *" field="cityId" hidden="true"
										editor="cityEditor" width="120px" />
									<kendo:grid-column title="Other City *" field="cityotherId"
										width="0px" hidden="true" />
									<kendo:grid-column title="City *" field="cityName"
										editor="cityEditor" width="120px" />



									<%-- <kendo:grid-column title="Type" field="contactType"
										editor="contactTypeEditor" width="100px" />
									
									<kendo:grid-column title="Contact Content"
										field="contactContent" width="120px" /> --%>
									<kendo:grid-column title="Pin/Zip Code*" field="pincode"
										width="150px" format="" />
									<kendo:grid-column title="Email *" field="emailContent"
										hidden="true" width="100px" />
									<kendo:grid-column title="Mobile *" field="phoneContent" 
										hidden="true" width="100px" />
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

									<kendo:grid-column title="&nbsp;" width="100px">
										<kendo:grid-column-command>
											<kendo:grid-column-commandItem name="edit"
												 />
										</kendo:grid-column-command>
									</kendo:grid-column>
									<kendo:grid-column title="&nbsp;" width="100px">
										<kendo:grid-column-command>
											<kendo:grid-column-commandItem name="destroy" />
										</kendo:grid-column-command>
									</kendo:grid-column>
								</kendo:grid-columns>
								<kendo:dataSource requestEnd="onRequestEndAddress" requestStart="onRequestStartAddress">
									<kendo:dataSource-transport>
										<kendo:dataSource-transport-read
											url="${readAddressUrl}/#=personId#" dataType="json"
											type="POST" contentType="application/json" />
										<kendo:dataSource-transport-create
											url="${createAddressUrl}/#=personId#" dataType="json"
											type="POST" contentType="application/json" />
										<kendo:dataSource-transport-update
											url="${updateAddressUrl}/#=personId#" dataType="json"
											type="POST" contentType="application/json" />
										<kendo:dataSource-transport-destroy
											url="${deleteAddressUrl}" dataType="json"
											type="POST" contentType="application/json" />
										<kendo:dataSource-transport-parameterMap>
											<script>
												function parameterMap(options,
														type) {
													return JSON
															.stringify(options);
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
													<%-- <kendo:dataSource-schema-model-field-validation
														required="true" /> --%>
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
												
												<kendo:dataSource-schema-model-field name="emailContent"
													type="email">
													<kendo:dataSource-schema-model-field-validation
														required="true" />
												</kendo:dataSource-schema-model-field>
												<kendo:dataSource-schema-model-field name="phoneContent"
													type="tel">
													<kendo:dataSource-schema-model-field-validation
														required="true"/>
												</kendo:dataSource-schema-model-field>
												
												<%-- <kendo:dataSource-schema-model-field name="contactType"
													type="string">
													<kendo:dataSource-schema-model-field-validation
														required="true" />
												</kendo:dataSource-schema-model-field>
												
												<kendo:dataSource-schema-model-field name="contactContent"
													type="string">
													<kendo:dataSource-schema-model-field-validation
														required="true" />
												</kendo:dataSource-schema-model-field> --%>
												
												<kendo:dataSource-schema-model-field name="pincode"
													 >
													<kendo:dataSource-schema-model-field-validation 
														required="true" min="1"/>
												</kendo:dataSource-schema-model-field>
												<kendo:dataSource-schema-model-field name="addressContactId"
													type="number">
													<kendo:dataSource-schema-model-field-validation
														required="true" />
												</kendo:dataSource-schema-model-field>
												<kendo:dataSource-schema-model-field name="addressSeason"
													type="boolean" defaultValue="false" />

												<kendo:dataSource-schema-model-field
													name="addressSeasonFrom" type="date" defaultValue="">
													<kendo:dataSource-schema-model-field-validation />
												</kendo:dataSource-schema-model-field>
												<kendo:dataSource-schema-model-field name="addressSeasonTo"
													type="date" defaultValue="">
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
						<div class="wethear" style="width:50%;">
							<kendo:grid name="gridContact_#=personId#" remove="contactRemove" pageable="true" edit="contactEvent"
								resizable="true" sortable="true" reorderable="true"
								selectable="true" scrollable="true">
								<kendo:grid-pageable pageSize="10"></kendo:grid-pageable>
								<kendo:grid-editable mode="popup"
									confirmation="Are you sure you want to remove this Contact?" />
								<kendo:grid-toolbar>
									<kendo:grid-toolbarItem name="create" text="Add Contact" />
								</kendo:grid-toolbar>
								<kendo:grid-columns>

									<%-- <kendo:grid-column title="Contact ID" field="contactId" filterable="false" width="100px"/> 
					        	<kendo:grid-column title="Person Id" field="personId" filterable="true" width="100px" /> --%>

									<kendo:grid-column title="Address" field="addressId"
										editor="addressforcontactEditor" width="0px" hidden="true" />

									<kendo:grid-column title="Address Type*" field="contactLocation"
										editor="contactLocationEditor" width="100px" />
									<kendo:grid-column title="Type *" field="contactType"
										editor="contactTypeEditor" width="100px" />
									<kendo:grid-column title="Primary *" field="contactPrimary"
										editor="contactPrimaryEditor" width="120px" />

									<kendo:grid-column title="Contact Content*"
										field="contactContent" width="120px" />
									<kendo:grid-column title="Preferred Time"
										field="contactPrefferedTime" format="{0:HH:mm}"
										editor="contactPreferredTimeEditor" width="120px" />
									<kendo:grid-column title="Seasonal" field="contactSeason"
										filterable="false" sortable="false" width="1px"/>
									<kendo:grid-column title="Season From"
										field="contactSeasonFrom" format="{0:dd/MM/yyyy}"
										filterable="true" width="100px" />
									<kendo:grid-column title="Season To" field="contactSeasonTo"
										format="{0:dd/MM/yyyy}" filterable="true" width="100px" />
									
									<kendo:grid-column title="&nbsp;" width="100px">
										<kendo:grid-column-command>
											<kendo:grid-column-commandItem name="edit"
												 />
										</kendo:grid-column-command>
									</kendo:grid-column>
									<kendo:grid-column title="&nbsp;" width="100px">
										<kendo:grid-column-command>
											<kendo:grid-column-commandItem name="destroy" />
										</kendo:grid-column-command>
									</kendo:grid-column>

								</kendo:grid-columns>
								<kendo:dataSource requestEnd="onRequestEndContact" requestStart="onRequestStartContact">
									<kendo:dataSource-transport>
										<kendo:dataSource-transport-read
											url="${readContactUrl}/#=personId#" dataType="json"
											type="POST" contentType="application/json" />
										<kendo:dataSource-transport-create
											url="${createContactUrl}/#=personId#" dataType="json"
											type="POST" contentType="application/json" />
										<kendo:dataSource-transport-update
											url="${updateContactUrl}/#=personId#" dataType="json"
											type="POST" contentType="application/json" />
										<kendo:dataSource-transport-destroy
											url="${deleteContactUrl}" dataType="json"
											type="POST" contentType="application/json" />
										<kendo:dataSource-transport-parameterMap>
											<script>
												function parameterMap(options,
														type) {
													return JSON
															.stringify(options);
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

												<kendo:dataSource-schema-model-field name="addressId"
													type="number" defaultValue=""/>
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
													name="contactPrefferedTime" type="string">
													<kendo:dataSource-schema-model-field-validation />
												</kendo:dataSource-schema-model-field>
												<kendo:dataSource-schema-model-field name="contactSeason"
													type="boolean" defaultValue="false" />

												<kendo:dataSource-schema-model-field
													name="contactSeasonFrom" type="date" defaultValue="">
													<kendo:dataSource-schema-model-field-validation/>
												</kendo:dataSource-schema-model-field>
												<kendo:dataSource-schema-model-field name="contactSeasonTo"
													type="date" defaultValue="">
													<kendo:dataSource-schema-model-field-validation/>
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
                    <div class="wethear" style="width:60%;">
                    		<br/>
                    			<a class="k-button k-button-icontext" id="approveDocuments_#=personId#" onclick="approveDocuments()" ><span class="k-icon k-add"></span>Approve Document</a>
						    <br/>
						    <br/>
							<kendo:grid name="documentsUpload_#=personId#" remove="documentRemove" pageable="true" edit="documentEvent" dataBound="UploadFileBound" change="documentChangeEvent" 
								resizable="true" sortable="true" reorderable="true"
								selectable="true" scrollable="true">
								<kendo:grid-pageable pageSize="10"></kendo:grid-pageable>
								<kendo:grid-editable mode="popup"  confirmation="Are sure you want to delete this item ?"/>
						       <kendo:grid-toolbar >
						            <kendo:grid-toolbarItem name="create" text="Add Document" />
						        </kendo:grid-toolbar> 
        						<kendo:grid-columns>
        								<kendo:grid-column title="&nbsp;" width="250px" >
							             	<!-- File Upload Button Purpose -->
							             </kendo:grid-column>
									     <%-- <kendo:grid-column title="Document Repo Id" field="drId" filterable="false" width="120px"/> --%>
									     <%-- <kendo:grid-column title="Dr Group Id" field="drGroupId" ></kendo:grid-column> --%>
									     
									     <kendo:grid-column title="Document Name*"  field="documentName" editor="documentNameEditor" width="100px"></kendo:grid-column>
									     <kendo:grid-column title="Document Format" field="documentFormat" editor="documentFormatEditor" width="100px"></kendo:grid-column>
									     <kendo:grid-column title="Document Number" field="documentNumber" width="100px"></kendo:grid-column>
									     <kendo:grid-column title="Document Description*" field="documentDescription" editor="documentDecriptionEditor" width="100px"></kendo:grid-column>
									     <%-- <kendo:grid-column title="Approved Status"  field="approved"></kendo:grid-column> --%>
        								 <kendo:grid-column title="&nbsp;" width="175px" >
							            	<kendo:grid-column-command>
							            		<kendo:grid-column-commandItem name="edit"/>
							            		<kendo:grid-column-commandItem name="View" click="downloadFile"/>
							            		<%-- <kendo:grid-column-commandItem name="Upload" click="uploadNotice" /> --%>
							            		<kendo:grid-column-commandItem name="destroy" />
							            	</kendo:grid-column-command>
							            </kendo:grid-column>
		
        								 <%-- <kendo:grid-column title="&nbsp;" width="100px" >
							            	<kendo:grid-column-command>
							            		<kendo:grid-column-commandItem name="edit"/>
							            	</kendo:grid-column-command>
							            </kendo:grid-column> --%>
        						</kendo:grid-columns>
        						 <kendo:dataSource requestEnd="DocumentOnRequestEnd" requestStart="DocumentOnRequestStart">
						            <kendo:dataSource-transport>
						                <kendo:dataSource-transport-read url="${OwnerPropertyDRReadUrl}/#=drGroupId#/ConciergeVendor" dataType="json" type="POST" contentType="application/json"/>
						                <kendo:dataSource-transport-create url="${OwnerDocumentRepoCreateUrl}/#=drGroupId#/ConciergeVendor" dataType="json" type="POST" contentType="application/json" />
						                <kendo:dataSource-transport-update url="${OwnerDocumentRepoUpdateUrl}/#=drGroupId#/ConciergeVendor" dataType="json" type="POST" contentType="application/json" />
						                <kendo:dataSource-transport-destroy url="${OwnerDocumentRepoDeleteUrl}" dataType="json" type="POST" contentType="application/json" />
						               <kendo:dataSource-transport-parameterMap>
						                	<script type="text/javascript">
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
							                    <kendo:dataSource-schema-model-field name="drId"  editable="false" />
						                    	<kendo:dataSource-schema-model-field name="documentFormat" type="string"></kendo:dataSource-schema-model-field>   
						                    	<kendo:dataSource-schema-model-field name="documentName" type="string"></kendo:dataSource-schema-model-field>
						                    	<kendo:dataSource-schema-model-field name="documentNumber" type="string"></kendo:dataSource-schema-model-field>
						                    	<kendo:dataSource-schema-model-field name="documentDescription" type="string">
						                    		<kendo:dataSource-schema-model-field-validation required="true"/>
						                    	</kendo:dataSource-schema-model-field>
						                    </kendo:dataSource-schema-model-fields>
						                 </kendo:dataSource-schema-model>
						             </kendo:dataSource-schema>
						          </kendo:dataSource>
        				</kendo:grid>		
                    </div>
                </kendo:tabStrip-item-content>
            </kendo:tabStrip-item>
            
            
            <kendo:tabStrip-item text="Vendor Comments Rating">
				<kendo:tabStrip-item-content>
					<div class="wethear" style="width:40%;">
					<kendo:grid name="gridComments_#=personId#" remove="commentsRateRemove" edit="commentsEvent" pageable="true" resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true">
		
		  <kendo:grid-editable mode="popup"
			confirmation="Are you sure you want to remove this Vendor Comment?" />
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Vendor Comment" />
		</kendo:grid-toolbar>
		<kendo:grid-columns>
		 <kendo:grid-column title="Owner Name *" field="ownerId" hidden="true" width="120px" />
		<kendo:grid-column title="Owner/Tenant Names*" field="ownerNames" editor="ownerNamesEditor" width="150px" />
		<kendo:grid-column title="Comments *" field="comments" editor="commentsEditor" width="120px"/>
		<kendo:grid-column title="Ratings *" field="ratings" width="120px"/>
		<kendo:grid-column title="&nbsp;" width="120px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit"/>
					<kendo:grid-column-commandItem name="destroy"/>
				</kendo:grid-column-command>
			</kendo:grid-column>
		</kendo:grid-columns>
		<kendo:dataSource pageSize="5" requestEnd="vendorCommentsRatingRequestEnd" requestStart="vendorCommentsRatingRequestStart">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-read url="${readVendorCommentsUrl}/#=personId#" dataType="json"
					type="POST" contentType="application/json" />
				<kendo:dataSource-transport-create url="${createVendorCommentsUrl}/#=personId#"
					dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-update url="${updateVendorCommentsUrl}/#=personId#"
					dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-destroy url="${destroyVendorCommentsUrl}"
					dataType="json" type="POST" contentType="application/json" />
				  <kendo:dataSource-transport-parameterMap>
					<script>
						function parameterMap(options, type) {
							return JSON.stringify(options);
						}
					</script>
				</kendo:dataSource-transport-parameterMap> 
			</kendo:dataSource-transport>
			
			 <kendo:dataSource-schema>
				<kendo:dataSource-schema-model id="vcrId">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="vcrId" type="number" />
						<kendo:dataSource-schema-model-field name="csvId"  type="number">
						 <kendo:dataSource-schema-model-field-validation  />
						 </kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="ownerId" type="number">
						<kendo:dataSource-schema-model-field-validation  />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="comments" type="string">
						<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="ratings" type="number">
						<kendo:dataSource-schema-model-field-validation min="0" max="5" required="true"/>
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="createdBy" type="string">
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="lastUpdatedBy" type="string">
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="lastUpdatedDt" type="date"/>
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
			</kendo:dataSource>
			</kendo:grid>
            </div>
            </kendo:tabStrip-item-content>
            </kendo:tabStrip-item>
            <!-----------------------  Vendor Othe Details ------------------------------->
			<kendo:tabStrip-item text="Vendor Other Details">
				<kendo:tabStrip-item-content>
					<div class="wethear" style="width:40%;">

						<kendo:grid name="vendorOtherDetails_#=personId#" edit="otherDetailsEvent"
							 pageable="true" resizable="true"
							sortable="true" reorderable="true" selectable="true"
							scrollable="true">
							<kendo:grid-pageable pageSize="10"></kendo:grid-pageable>
							<kendo:grid-editable mode="popup" />
							<%-- <kendo:grid-toolbar >
					           <kendo:grid-toolbarItem name="create" text="Add Vendor Details" />						           
					       </kendo:grid-toolbar> --%>
							<kendo:grid-columns>
								<kendo:grid-column title="CST No" field="cstNo"
									filterable="false" width="60px" />
								<kendo:grid-column title="State Tax No" field="stateTaxNo"
									width="60px" filterable="false"></kendo:grid-column>
								<kendo:grid-column title="Service Tax No" field="serviceTaxNo"
									width="60px" filterable="false"></kendo:grid-column>
								<kendo:grid-column title="&nbsp;" width="100px">

									<kendo:grid-column-command>
										<kendo:grid-column-commandItem name="edit" />
									</kendo:grid-column-command>
								</kendo:grid-column>

							</kendo:grid-columns>
							<kendo:dataSource requestEnd="vendorDetailsRequestEnd" requestStart="vendorDetailsRequestStart">
								<kendo:dataSource-transport>
									<kendo:dataSource-transport-read
										url="${readVendorOtherDetailsUrl}/#=personId#" dataType="json"
										type="GET" contentType="application/json" />
									<kendo:dataSource-transport-update
										url="${updateVendorOtherDetailsUrl}/#=personId#" dataType="json"
										type="GET" contentType="application/json" />
										<kendo:dataSource-transport-destroy url="${destroyVendorOtherDetailsUrl}"
										dataType="json" type="GET" contentType="application/json" />
									<%-- <kendo:dataSource-transport-parameterMap>
										<script>
											function parameterMap(options, type) {
												return JSON.stringify(options);
											}
										</script>
									</kendo:dataSource-transport-parameterMap>--%>
								</kendo:dataSource-transport> 
								<kendo:dataSource-schema>
									<kendo:dataSource-schema-model id="csvId">
										<kendo:dataSource-schema-model-fields>
											<kendo:dataSource-schema-model-field name="csvId"
												editable="false" />
											<kendo:dataSource-schema-model-field name="personId"
												type="number">
												<kendo:dataSource-schema-model-field-validation
													required="true" />
											</kendo:dataSource-schema-model-field>
											<kendo:dataSource-schema-model-field name="cstNo">
												<kendo:dataSource-schema-model-field-validation
													required="true" />
											</kendo:dataSource-schema-model-field>
											<kendo:dataSource-schema-model-field name="stateTaxNo">
												<kendo:dataSource-schema-model-field-validation
													required="true" />
											</kendo:dataSource-schema-model-field>
											<kendo:dataSource-schema-model-field name="serviceTaxNo">
												<kendo:dataSource-schema-model-field-validation
													required="true" />
											</kendo:dataSource-schema-model-field>
											<kendo:dataSource-schema-model-field name="status"
												type="string"/>
											<kendo:dataSource-schema-model-field name="createdBy"
												type="string">
											</kendo:dataSource-schema-model-field>
											<kendo:dataSource-schema-model-field name="lastUpdatedBy"
												type="string">
											</kendo:dataSource-schema-model-field>
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
	
 <%-- <kendo:window name="details" modal="true" draggable="true" visible="false" /> --%>
 <div id="personPopUp" title="Is this the person you want to add ?"></div>
 <div id="alertsBox" title="Alert"></div>
<div id="uploadDialog" title="Upload Image" style="display: none;">
	<kendo:upload complete="oncomplete" name="files" upload="onpersonImageUpload" multiple="false" success="onImageSuccess">
				<kendo:upload-async autoUpload="true" saveUrl="${personImage}" />
	</kendo:upload>
</div>
<script>
	function clearList()
	{
		 alert("Uploaded Successfully");
		 $(".k-upload-status").remove();
	     $(".k-upload-files.k-reset").find("li").remove();
	     $(".k-upload-status k-upload-status-total").text("");
    }
   </script>
<%-- <div id="uploadDialog2" title="Upload Image" style="display: none;">
	<kendo:upload complete="oncomplete" name="files" upload="onpersonImageUpload"
									multiple="false">
				<kendo:upload-async autoUpload="true" saveUrl="${personImage}" />
	</kendo:upload>
</div>
 --%>
 
 <div id="dialogBoxforContacts" title="Associated Contacts"></div>

<!-- ------------------------------------------ Person Script  ------------------------------------------ -->

<script type="text/javascript">
function parseConciergeVendor (response) { 
    var data = response; //<--data might be in response.data!!!

	 for (var idx = 0, len = data.length; idx < len; idx ++) {
		/* var res1 = [];
		$.each(data[idx].languagesKnown, function(idx1, elem1) 
		{
			res1.push(elem1.text);
		});
		res1.join(",");
		data[idx].languagesKnown = res1.sort().toString(); */
		
		var res3 = [];
		//alert(data[idx].languagesKnown);
		var res4 =(data[idx].languagesKnown).split(',');
		if(res4.length > 1)
		{
			$.each(res4, function(idx1, elem1) 
					{
						res3.push(elem1);
					});
			data[idx].languagesKnown = res3.sort().toString();
		}	
		
		if((data[idx].dob) != null)
		{
			dobArray.push(data[idx].dob); 
		}	
	 }
	 return response;
}
function clearFilterConciergeVendor()
{
	  $("form.k-filter-menu button[type='reset']").slice().trigger("click");
	  var gridPersonConciergeVendor = $("#gridPersonConciergeVendor").data("kendoGrid");
	  gridPersonConciergeVendor.dataSource.read();
	  gridPersonConciergeVendor.refresh();
}

var SelectedPersonStyleConciergeVendor = "Select";
var dobArray = [];

$( document ).ready(function() {
	$('.k-grid-add').hide();
});

	$(".k-grid-toolbar").delegate(".k-grid-add", "click", function(e) {
    e.preventDefault();
    grid.addRow();
}); 
	function categoriesChangeConciergeVendor() {	
		 var value = this.value(),
		 	 grid = $("#gridPersonConciergeVendor").data("kendoGrid");

		 if (value != "Select") {
	    	 $('.k-grid-add').show();
	     } else {
	    	$('.k-grid-add').hide();
	     }
		 SelectedPersonStyleConciergeVendor = value;
	}
	$("#gridPersonConciergeVendor").on("click", ".k-grid-add", function() {
		
		  /* if($("#gridPersonConciergeVendor").data("kendoGrid").dataSource.filter()){
	  	        $("form.k-filter-menu button[type='reset']").slice().trigger("click");
	  	      var grid = $("#gridPersonConciergeVendor").data("kendoGrid");
	  	      grid.dataSource.read();
	  	      grid.refresh();
	  	          } */
		});
	
	function personEventConciergeVendor(e)
	{
		var style = "";
		
		$('label[for="image"]').parent().remove();
		$('div[data-container-for="image"]').remove();
		$('label[for="personName"]').parent().remove();
		$('div[data-container-for="personName"]').remove();
		$('label[for="age"]').parent().remove();
		$('div[data-container-for="age"]').remove();
		
		$('label[for="existedPersonId"]').closest('.k-edit-label').hide();
		$('div[data-container-for="existedPersonId"]').hide(); 
		
		$('div[data-container-for="personStatus"]').remove();
		$('label[for="personStatus"]').parent().remove();
		
		$('label[for="status"]').closest('.k-edit-label').hide();
		$('div[data-container-for="status"]').hide(); 
		
		if (e.model.isNew()) 
	    {
			securityCheckForActions("./conciergeManagement/conciergeVendors/createButton");
			
			$(".k-edit-field").each(function () {
				$(this).find("#temPID").parent().remove();
		   	});
			
			style = SelectedPersonStyleConciergeVendor;
			
			$(".k-grid-update").text("Save");
			
			if(SelectedPersonStyleConciergeVendor == "Individual")
			{
				$(".k-window-title").text("Add New Concierge Vendor Record as an "+SelectedPersonStyleConciergeVendor);
				
 				$(".k-edit-form-container").css({"width" : "750px"});
				$(".k-window").css({"left": "275px", "top" : "150px"});
				$('.k-edit-label:nth-child(22n+1)').each(function(e) {
							$(this).nextAll(':lt(21)').addBack().wrapAll(
									'<div class="wrappers"/>');
						}); 
				$('label[for="firstName"]').text("First Name *");
				$(".k-window").css({"position" : "fixed"});
				$(".wrappers").css({"display":"inline","float":"left","width":"350px","padding-top":"10px"}); 
				
			}
			else
			{
				$(".k-window-title").text("Add New Concierge Vendor Record as a "+SelectedPersonStyleConciergeVendor);
				
				$('label[for="firstName"]').text(SelectedPersonStyleConciergeVendor+" Name *");
				loadFieldsBasedOnPersonStyle(SelectedPersonStyleConciergeVendor);
				
				var grid = $("#gridPersonConciergeVendor").data().kendoGrid.dataSource.data()[0];
			    grid.set('personStyle', SelectedPersonStyleConciergeVendor);
				
			}	
			
			$('label[for="spouseName"]').hide();
			$('div[data-container-for="spouseName"]').hide();
			
			
	    }
		else
		{
			securityCheckForActions("./conciergeManagement/conciergeVendors/updateButton");
			/* if($("#gridPersonConciergeVendor").data("kendoGrid").dataSource.filter()){
		        $("form.k-filter-menu button[type='reset']").slice().trigger("click");
		      var grid = $("#gridPersonConciergeVendor").data("kendoGrid");
		      grid.dataSource.read();
		      grid.refresh();
		          } */
			
		var personStyle =  $("select[name=personStyle] :selected").val();
			
			style = personStyle;
			
			$(".k-window-title").text("Edit "+personStyle+" details of the Concierge Vendor");
			
			$(".k-window-title").text("Edit "+personStyle+" details of the Concierge Vendor");
			
			if(personStyle == "Individual")
			{
				 $('label[for="cstNo"]').closest('.k-edit-label').remove();
				$('div[data-container-for="cstNo"]').remove(); 
				 $('label[for="stateTaxNo"]').closest('.k-edit-label').remove();
				 $('div[data-container-for="stateTaxNo"]').remove(); 
				$('label[for="serviceTaxNo"]').closest('.k-edit-label').remove();
				$('div[data-container-for="serviceTaxNo"]').remove(); 
				 $('label[for="status"]').closest('.k-edit-label').remove();
				$('div[data-container-for="status"]').remove(); 
				
				
				$(".k-edit-form-container").css({"width" : "750px"});
				$(".k-window").css({"left": "275px", "top" : "150px"});
				$('.k-edit-label:nth-child(18n+1)').each(function(e) {
							$(this).nextAll(':lt(17)').addBack().wrapAll(
									'<div class="wrappers"/>');
						}); 
				$(".k-window").css({
					"position" : "fixed"
				});
				$(".wrappers").css({"display":"inline","float":"left","width":"350px","padding-top":"10px"}); 
				$('label[for="firstName"]').text("First Name *");
			}
			else
			{
				$('label[for="firstName"]').text(personStyle+" Name *");
				editLoadFieldsBasedOnPersonStyle(personStyle);
			}	
			
		 	if((e.model.maritalStatus == "Single") || (e.model.maritalStatus == "None"))
		 	{
		 		$('label[for="spouseName"]').hide();
				$('div[data-container-for="spouseName"]').hide();
		 	}	
		 	else
		 	{
		 		$('label[for="spouseName"]').show();
				$('div[data-container-for="spouseName"]').show();
		 	}	
		}
		
		$('label[for="personStyle"]').parent().remove();
		$('div[data-container-for="personStyle"]').remove();
		
		$(".k-edit-field").each(function () {
			$(this).find("#temPID").parent().remove();
	   	});
		
		$('.k-edit-field .k-input').first().focus();
		
		var grid = this;
		e.container.on("keydown", function(e) {        
	        if (e.keyCode == kendo.keys.ENTER) {
	          $(document.activeElement).blur();
	          grid.saveRow();
	        }
	      });
		
		//CLIENT SIDE VALIDATION 
		
 		$(".k-grid-update").click(function () 
		{
			//var field = $('#gridPersonOwner').data().kendoGrid.dataSource.data()[0];
			
			var firstName = $("#firstName").val();
			
			if(style == "Individual")
			{
				var lastName = $('input[name="lastName"]').val();
				var fatherName = $('input[name="fatherName"]').val();
				var dob = $('input[name="dob"]').val();
				
				var maritalStatus = $("select[data-bind='value:maritalStatus'] :selected").val();
				
				var occupation = $("select[data-bind='value:occupation'] :selected").val();
				
				   if((firstName == null) || (firstName == ""))
			       {
			    	   alert("First name cannot be empty");
			     	   return false;
			       }
				   var firstNameReg = /^[a-zA-Z ]{0,100}$/;
			       if(!firstNameReg.test(firstName)) {
			    	   alert("Only alphabets and maximum 100 letters are allowed for First name");
			    	   return false;
			       }
			       
			      /*  if((lastName == null) || (lastName == ""))
			       {
			    	   alert("Last name cannot be empty");
			    	   return false;
			       }
	
			       if((fatherName == null) || (fatherName == ""))
			       {
			    	   alert("Father name cannot be empty");
			    	   return false;
			       } 

			       if((dob == null) || (dob == "") )
			       {
			            alert("Date of birth is not selected");
			            return false;        
			       }  */
			       
			       if((maritalStatus == "None") || (maritalStatus == "Single"))
			       {
			    	   $('input[name="spouseName"]').val("").change();
			       }	   
			       
					var occupationReg = /^[a-zA-Z ]{0,45}$/;
				    if(!occupationReg.test(occupation)) {
				    	alert("Only characters and maximum 45 letters are allowed for Occupation");
				    	return false;
				    }
			}
			else
			{
				   if((firstName == null) || (firstName == ""))
			       {
			     		alert(style+" name cannot be empty");
				    	return false;
			       }
			       
			       else if((firstName.trim()) == "")
			       {
			    	   alert(style+" name cannot contain only spaces");
				       return false;
			       }
			       
			       else if(firstName.length > 150) 
			       {
			    		alert("Maximum 150 letters are allowed for "+style+" name");
			    	    return false;
			       }
				   
				   var styleNameReg = /^[a-zA-Z ]{0,100}$/;
			       if(!styleNameReg.test(firstName)) {
			    	   alert("Only alphabets and maximum 100 letters are allowed for "+style+" name");
			    	   return false;
			       }
			}	
		});  
	}
	
		$("#gridPersonConciergeVendor").on("click", ".k-grid-Clear_Filter", function() {
			$("form.k-filter-menu button[type='reset']").slice().trigger("click");
			var gridPerson = $("#gridPersonConciergeVendor").data("kendoGrid");
			gridPerson.dataSource.read();
			gridPerson.refresh();
		});
			function loadFieldsBasedOnPersonStyle(personStyleCheck)
			{	
		if (personStyleCheck != "Individual") 
		{	
			$('label[for="title"]').parent().remove();
			$('div[data-container-for="title"]').remove();
			$('label[for="middleName"]').parent().remove();
			$('div[data-container-for="middleName"]').remove();
			$('label[for="lastName"]').parent().remove();
			$('div[data-container-for="lastName"]').remove();
			$('label[for="fatherName"]').parent().remove();
			$('div[data-container-for="fatherName"]').remove();
			$('label[for="spouseName"]').parent().remove();
			$('div[data-container-for="spouseName"]').remove();
			$('label[for="dob"]').parent().remove();
			$('div[data-container-for="dob"]').remove();
			$('label[for="occupation"]').parent().remove();
			$('div[data-container-for="occupation"]').remove();
			$('label[for="languagesKnown"]').parent().remove();
			$('div[data-container-for="languagesKnown"]').remove();
			
			$('label[for="maritalStatus"]').parent().remove();
			$('div[data-container-for="maritalStatus"]').remove();
			$('label[for="sex"]').parent().remove();
			$('div[data-container-for="sex"]').remove();
			$('label[for="age"]').parent().remove();
			$('div[data-container-for="age"]').remove();
			$('label[for="nationality"]').parent().remove();
			$('div[data-container-for="nationality"]').remove();
			$('label[for="bloodGroup"]').parent().remove();
			$('div[data-container-for="bloodGroup"]').remove();
			$('label[for="workNature"]').parent().remove();
			$('div[data-container-for="workNature"]').remove();
			
			/* $('input[name="lastName"]').prop('required',false);
			$('input[name="fatherName"]').prop('required',false);
			$('input[name="dob"]').prop('required',false); */
				
		}	
	}
			function editLoadFieldsBasedOnPersonStyle(personStyleCheck)
			{	
		if (personStyleCheck != "Individual") 
		{	
			
			 $('label[for="cstNo"]').closest('.k-edit-label').remove();
			 $('div[data-container-for="cstNo"]').remove(); 
			 $('label[for="stateTaxNo"]').closest('.k-edit-label').remove();
			 $('div[data-container-for="stateTaxNo"]').remove(); 
			$('label[for="serviceTaxNo"]').closest('.k-edit-label').remove();
			$('div[data-container-for="serviceTaxNo"]').remove(); 
			 $('label[for="status"]').closest('.k-edit-label').remove();
			$('div[data-container-for="status"]').remove(); 
			
			$('label[for="title"]').parent().remove();
			$('div[data-container-for="title"]').remove();
			$('label[for="middleName"]').parent().remove();
			$('div[data-container-for="middleName"]').remove();
			$('label[for="lastName"]').parent().remove();
			$('div[data-container-for="lastName"]').remove();
			$('label[for="fatherName"]').parent().remove();
			$('div[data-container-for="fatherName"]').remove();
			$('label[for="spouseName"]').parent().remove();
			$('div[data-container-for="spouseName"]').remove();
			$('label[for="dob"]').parent().remove();
			$('div[data-container-for="dob"]').remove();
			$('label[for="occupation"]').parent().remove();
			$('div[data-container-for="occupation"]').remove();
			$('label[for="languagesKnown"]').parent().remove();
			$('div[data-container-for="languagesKnown"]').remove();
			
			$('label[for="maritalStatus"]').parent().remove();
			$('div[data-container-for="maritalStatus"]').remove();
			$('label[for="sex"]').parent().remove();
			$('div[data-container-for="sex"]').remove();
			$('label[for="age"]').parent().remove();
			$('div[data-container-for="age"]').remove();
			$('label[for="nationality"]').parent().remove();
			$('div[data-container-for="nationality"]').remove();
			$('label[for="bloodGroup"]').parent().remove();
			$('div[data-container-for="bloodGroup"]').remove();
			$('label[for="workNature"]').parent().remove();
			$('div[data-container-for="workNature"]').remove();
			
			/* $('input[name="lastName"]').prop('required',false);
			$('input[name="fatherName"]').prop('required',false);
			$('input[name="dob"]').prop('required',false); */
				
		}	
	}
	
		//register custom validation rules
		(function($, kendo) {
			$
					.extend(
							true,
							kendo.ui.validator,
							{
								rules : { // custom rules          	
									middleNamevalidation : function(input,
											params) {
										if (input.filter("[name='middleName']").length
												&& input.val()) {
											return /^[a-zA-Z ]{0,45}$/
													.test(input.val());
										}
										return true;
									},
									middleNameSpacesvalidation : function(input,
											params) {
										if (input.filter("[name='middleName']").length
												&& input.val()) {
											if(input.val().trim() == "")
											{
												return false;
											}	
										}
										return true;
									},
									lastNamevalidation : function(input, params) {
										if (input.filter("[name='lastName']").length
												&& input.val()) {
											return /^[a-zA-Z ]{0,45}$/
													.test(input.val());
										}
										return true;
									},
									lastNameSpacesvalidation : function(input,
											params) {
										if (input.filter("[name='lastName']").length
												&& input.val()) {
											if(input.val().trim() == "")
											{
												return false;
											}	
										}
										return true;
									},
									fatherNamevalidation : function(input,
											params) {
										if (input.filter("[name='fatherName']").length
												&& input.val()) {
											return /^[a-zA-Z ]{0,45}$/
													.test(input.val());
										}
										return true;
									},
									fatherNameSpacesvalidation : function(input,
											params) {
										if (input.filter("[name='fatherName']").length
												&& input.val()) {
											if(input.val().trim() == "")
											{
												return false;
											}	
										}
										return true;
									},
									dob: function (input, params) 
						             {
					                     if (input.filter("[name = 'dob']").length && input.val()) 
					                     {                          
					                         var selectedDate = input.val();
					                         var todaysDate = $.datepicker.formatDate('dd/mm/yy', new Date());
					                         var flagDate = false;

					                         if ($.datepicker.parseDate('dd/mm/yy', selectedDate) < $.datepicker.parseDate('dd/mm/yy', todaysDate)) 
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
									middleNamevalidation : "Only characters and maximum 45 letters are allowed",
									middleNameSpacesvalidation : "Middle name cannot contain only spaces",
									lastNamevalidation : "Only characters and maximum 45 letters are allowed",
									lastNameSpacesvalidation : "Last name cannot contain only spaces",
									fatherNamevalidation : "Only characters and maximum 45 letters are allowed",
									fatherNameSpacesvalidation : "Father name cannot contain only spaces",
									dob:"Date of birth must be selected from the past"
								}
							});

		})(jQuery, kendo);
		//End Of Validation
		var kycCompliant = "";
		var SelectedRowId = "";
		var SelectedGroupId = "";
		function onChangeConciergeVendor(arg) {
			 var gview = $("#gridPersonConciergeVendor").data("kendoGrid");
		 	 var selectedItem = gview.dataItem(gview.select());
		 	 SelectedRowId = selectedItem.personId;
		 	SelectedGroupId = selectedItem.drGroupId;
		 	 kycCompliant = selectedItem.kycCompliant;
		 	 this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
		     // alert("Selected: " + SelectedRowId);
		     
		}
		function comboBoxChecksEditorConciergeVendor(container, options) 
		{
			var res = (container.selector).split("=");
			var attribute = res[1].substring(0,res[1].length-1);
			
			$("<select data-bind='value:" + attribute + "'/>")
			        .appendTo(container).kendoComboBox({
			        		dataTextField : "text",
							dataValueField : "value",
							placeholder: "Enter or Select",
		                    dataSource: {  
		                        transport:{
		                            read: "${allChecksUrl}/"+attribute,
		                        }
		                    }
		                });
		}
function languagesEditorOwner(container, options) {
			
			var res = (container.selector).split("=");
			var attribute = res[1].substring(0,res[1].length-1);
			
			$("<input name='languagesKnown' data-bind='value : "+attribute+"'/>")
					.appendTo(container).kendoAutoComplete({
						dataTextField : "text",
						dataValueField : "value",
						filter: "startswith",
						separator: ",",
						dataSource : {
							transport : {
								read : "${allChecksUrl}/"+attribute,
							}
					
						}
					});
			
		}
		function firstNameEditorConciergeVendor(container, options) 
		{
			if (options.model.firstName == '') 
			{	
				var	autoCompleteDS = new kendo.data.DataSource({
			        transport: {
			            read: {
			                url     : "${personNamesAutoUrl}/"+SelectedPersonStyleConciergeVendor, // url to remote data source 
			                dataType: "json",
			                type    : 'GET'
			            }
			        },
			    });

					$('<input data-text-field="value" id="firstName" data-value-field="name" data-bind="value:' + options.field + '"/>')
							.appendTo(container)
							.kendoAutoComplete(
							{
								autoBind : false,
								dataTextField : "name",
								select : UserSelectedAuto,
								animation: {
									   close: {
									     effects: "fadeOut zoom:out",
									     duration: 300
									   }
						     	},
								placeholder : "Enter or select",
								headerTemplate : '<div class="dropdown-header">'
										+ '<span class="k-widget k-header">Photo</span>'
										+ '<span class="k-widget k-header">Contact info</span>'
										+ '</div>',
								template : '<table><tr><td rowspan=2><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.personId#'/>" width=40 height=55 alt=\"No Image to Display\" /></span></td>'
										+ '<td align="center"><span class="k-state-default"><b>#: data.name #</b></span><br>'
										+ '<span class="k-state-default"><i>#: data.personStyle #</i></span><br>'
										+ '<span class="k-state-default"><i>#: data.personType #</i></span></td></tr></table>',
								dataSource    : autoCompleteDS	
							});
			}
			else
			{
				 $('<input type="text" class="k-input k-textbox"  id ="firstName" name=' + options.field + ' data-bind="value:' + options.field + '"/>')
			      .appendTo(container);
			}	
		 }

		var SelectedPersonId = 0;
		function UserSelectedAuto(e) 
		{
			var dataItem = this.dataItem(e.item.index());
			var personId = dataItem.personId;
			SelectedPersonId = personId;
			if (dataItem.personType.indexOf("ConciergeVendor") != -1) {
				alert("Concierge Vendor Already Exist !!!");
				 var nameList = $("#firstName").data("kendoAutoComplete");
				 nameList.text("");
				 
			} else {
				var agree = confirm("Are you sure you want to add this person as 'Concierge Vendor' ?");
				if( agree == false ){
					var nameList = $("#firstName").data("kendoAutoComplete");
					 nameList.text("");
				}
				else{
				var firstItem = $('#gridPersonConciergeVendor').data().kendoGrid.dataSource
						.data()[0];
				firstItem.set('existedPersonId', personId);

				//e.model.set('personId', personId);

				$('input[name="lastName"]').prop('required',false);
				$('input[name="fatherName"]').prop('required',false);
				$('input[name="dob"]').prop('required',false);
			
				$('label[for="maritalStatus"]').parent().remove();
				$('div[data-container-for="maritalStatus"]').remove();
				 $('label[for="existedPersonId"]').closest('.k-edit-label').hide();
				$('div[data-container-for="existedPersonId"]').hide(); 
 
				$('label[for="title"]').closest('.k-edit-label').hide();
				$('div[data-container-for="title"]').hide();

				$('label[for="lastName"]').closest('.k-edit-label').hide();
				$('div[data-container-for="lastName"]').hide();

				$('label[for="middleName"]').closest('.k-edit-label').hide();
				$('div[data-container-for="middleName"]').hide();
				
				$('label[for="sex"]').closest('.k-edit-label').hide();
				$('div[data-container-for="sex"]').hide();

				$('label[for="bloodGroup"]').closest('.k-edit-label').hide();
				$('div[data-container-for="bloodGroup"]').hide();
				
				
				$('label[for="fatherName"]').closest('.k-edit-label').hide();
				$('div[data-container-for="fatherName"]').hide();

				$('label[for="dob"]').closest('.k-edit-label').hide();
				$('div[data-container-for="dob"]').hide();
				
				$('label[for="nationality"]').closest('.k-edit-label').hide();
				$('div[data-container-for="nationality"]').hide();
				
				$('label[for="occupation"]').closest('.k-edit-label').hide();
				$('div[data-container-for="occupation"]').hide();
				
				$('label[for="workNature"]').closest('.k-edit-label').hide();
				$('div[data-container-for="workNature"]').hide();
				
				$('label[for="languagesKnown"]').closest('.k-edit-label').hide();
				$('div[data-container-for="languagesKnown"]').hide();
				}
			}
		}

		function maritalStatusDropdownEditorConiergeVendor(container, options) 
		{
			$("<select data-bind='value:" + options.field + "'/>")
			.appendTo(container).kendoDropDownList({
				dataTextField : "text",
				dataValueField : "value",
				change : function (e) {
		            if((this.value() == "Single") || (this.value() == "None"))
		            {
		            	$('label[for="spouseName"]').hide();
						$('div[data-container-for="spouseName"]').hide();
		            }	
		            else
		            {
		            	$('label[for="spouseName"]').show();
						$('div[data-container-for="spouseName"]').show();
		            }	
			    }, 
				dataSource : {
					transport : {
						read : "${allChecksUrl}/"+options.field,
					}
				}
			});
			
		 }
		function saveExistingId(personId)
		 {
			 $('.ui-dialog').remove(); 
			 $.ajax({
					type : "POST",
					dataType:"text",
					url : "./person/updateExistingPerson",
					data :
						{
						 personId : personId,
						 personType : "ConciergeVendor",		
						},
					success : function(response)
					{
						var text = response.search( 'already' );
					      if(parseInt(text) <= 0)
					      {
					       $('#gridPersonConciergeVendor').data().kendoGrid.dataSource.read();
					       
					      }
						
						$("#alertsBox").html("");
						$("#alertsBox").html(response);
						$("#alertsBox").dialog(
						{
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
				 });
		 }

		function dobDisplay(data) {
			if((data.dob == null) || (data.dob == ""))
			{
				return "";
			}	
			else
			{
				return kendo.toString(data.dob, 'dd/MM/yyyy');
			}	
		}
		
		function ageDisplay(data) {
			if((data.dob == null) || (data.dob == ""))
			{
				return "";
			}	
			else
			{
				return kendo.toString(new Date(), 'yyyy') - kendo.toString(data.dob, 'yyyy');
			}	
		}
		
		function comboBoxChecksEditorConciergeVendor(container, options) 
		{
			var res = (container.selector).split("=");
			var attribute = res[1].substring(0,res[1].length-1);
			
			$("<select data-bind='value:" + attribute + "'/>")
			        .appendTo(container).kendoComboBox({
			        		dataTextField : "text",
							dataValueField : "value",
							placeholder: "Enter or Select",
		                    dataSource: {  
		                        transport:{
		                            read: "${allChecksUrl}/"+attribute,
		                        }
		                    }
		                });
		}
		
		$("#gridPersonConciergeVendor").on("click", "#temPID", function(e) {
			var button = $(this), enable = button.text() == "Activate";
			var widget = $("#gridPersonConciergeVendor").data("kendoGrid");
			var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
			result=securityCheckForActionsForStatus("./conciergeManagement/conciergeVendors/statusButton");   
			   if(result=="success"){
						if (enable) 
						{
							$.ajax
							({
								type : "POST",
								dataType:"text",
								url : "./person/personStatus/" + dataItem.id + "/activate/ConciergeVendor",
								success : function(response) 
								{
									$("#alertsBox").html("");
									$("#alertsBox").html(response);
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
									button.text('Deactivate');
									$('#gridPersonConciergeVendor').data('kendoGrid').dataSource.read();
								}
							});
						} 
						else 
						{
							$.ajax
							({
								type : "POST",
								dataType:"text",
								url : "./person/personStatus/" + dataItem.id + "/deactivate/ConciergeVendor",
								success : function(response) 
								{
									$("#alertsBox").html("");
									$("#alertsBox").html(response);
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
									button.text('Activate');
									$('#gridPersonConciergeVendor').data('kendoGrid').dataSource.read();
								}
							});
						}
		}
				});
		
		function onRequestStartPersonConciergeVendor(e) 
		{
			$('.k-grid-update').hide();
		    $('.k-edit-buttons')
		            .append(
		                    '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
		    $('.k-grid-cancel').hide();
		    
			/* var gridPerson = $("#gridPersonConciergeVendor").data("kendoGrid");
			if(gridPerson != null)
			{
				gridPerson.cancelRow();
			} */	
		}	
		
		function onRequestStartAddress(e){
			$('.k-grid-update').hide();
		    $('.k-edit-buttons')
		            .append(
		                    '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
		    $('.k-grid-cancel').hide();
		    
		}
		
		function onRequestStartContact(e) {
			$('.k-grid-update').hide();
		    $('.k-edit-buttons')
		            .append(
		                    '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
		    $('.k-grid-cancel').hide();
		}
		
		function DocumentOnRequestStart(e){
			$('.k-grid-update').hide();
		    $('.k-edit-buttons')
		            .append(
		                    '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
		    $('.k-grid-cancel').hide();	
		}
		
		function vendorCommentsRatingRequestStart(e){
			$('.k-grid-update').hide();
		    $('.k-edit-buttons')
		            .append(
		                    '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
		    $('.k-grid-cancel').hide();	
		}
		
		function vendorDetailsRequestStart(e){
			$('.k-grid-update').hide();
		    $('.k-edit-buttons')
		            .append(
		                    '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
		    $('.k-grid-cancel').hide();
		}
		function onRequestEndPersonConciergeVendor(e) 
		{
			displayMessage(e, "gridPersonConciergeVendor", "Concierge Vendor");
		}	
		
	</script>
	<!-- ---------------------------------------------- Address script -------------------------------------- -->
	<script>


		var seasonFromAddress = "";
		
		//register custom validation rules
		(function($, kendo) {
			$
					.extend(
							true,
							kendo.ui.validator,
							{
								rules : { // custom rules          	
									addressSeasonFrom: function (input, params) 
						             {
					                     if (input.filter("[name = 'addressSeasonFrom']").length && input.val() != "") 
					                     {                          
					                         var selectedDate = input.val();
					                         var todaysDate = $.datepicker.formatDate('dd/mm/yy', new Date());
					                         var flagDate = false;

					                         if ($.datepicker.parseDate('dd/mm/yy', selectedDate) > $.datepicker.parseDate('dd/mm/yy', todaysDate)) 
					                         {
					                        	 	seasonFromAddress = selectedDate;
					                                flagDate = true;
					                         }
					                         return flagDate;
					                     }
					                     return true;
					                 },
					                 addressSeasonTo1: function (input, params) 
						             {
					                     if (input.filter("[name = 'addressSeasonTo']").length && input.val() != "") 
					                     {                          
					                         var flagDate = false;

					                         if (seasonFromAddress != "") 
					                         {
					                                flagDate = true;
					                         }
					                         return flagDate;
					                     }
					                     return true;
					                 },
					                 addressSeasonTo2: function (input, params) 
						             {
					                     if (input.filter("[name = 'addressSeasonTo']").length && input.val() != "") 
					                     {                          
					                         var selectedDate = input.val();
					                         var flagDate = false;

					                         if ($.datepicker.parseDate('dd/mm/yy', selectedDate) > $.datepicker.parseDate('dd/mm/yy', seasonFromAddress)) 
					                         {
					                                flagDate = true;
					                         }
					                         return flagDate;
					                     }
					                     return true;
					                 },
					                 pincodeValidation : function(input,
												params) {
											if (input.filter("[name='pincode']").length
													&& input.val()) {
												return /^(\s*\d{6}\s*)$/
														.test(input.val());
											}
											return true;
										},
									statevalidation : function(input,
												params) {
											if (input.filter("[name='stateotherId']").length
													&& input.val()) {
												return /^[a-zA-Z ]{1,45}$/
														.test(input.val());
											}
											return true;
										},
									cityvalidation : function(input,
												params) {
											if (input.filter("[name='cityotherId']").length
													&& input.val()) {
												return /^[a-zA-Z ]{1,45}$/
														.test(input.val());
											}
											return true;
										},
										phoneContentValidation : function(input,
												params) {
											if (input.filter("[name='phoneContent']").length
													&& input.val()) {
												return /^[0-9]{1,15}$/
												.test(input.val());
											}
											return true;
										}
									/* phoneContentValidation : function(input,
													params) {
												if (input.filter("[name='phoneContent']").length
														&& input.val()) {
													return /^(\d{10})$/
													.test(input.val());
												}
												return true;
											}  , */
									/* mobileValidation : function(input,
													params) {
												//check for the name attribute 
												if (input
														.filter("[name='phoneContent']").length
														&& input.val()) {
													mobileNumber = input.val();
													$
															.each(
																	resContact,
																	function(ind, val) {
																		
																		if ((mobileNumber == val)
																				&& (mobileNumber.length == val.length)) {
																			flag = mobileNumber;
																		}
																	});
													return /^(\d{10})$/
															.test(input.val());
												}
												return true;
											},

									mobileUniqueness : function(input) {
												if (input
														.filter("[name='phoneContent']").length
														&& input.val() && flag != "") {
													flag = "";
													return false;
												}
												return true;
											},
									emailValidation : function(input,
													params) {
												//check for the name attribute 
												if (input
														.filter("[name='emailContent']").length
														&& input.val()) {
													emailId = input.val();
													$.each(resContact,
																	function(ind, val) {
																		if ((emailId == val)
																				&& (emailId.length == val.length)) {
																			flag = emailId;
																		}
																	});
													return /^([0-9a-zA-Z]([-\.\w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-\w]*[0-9a-zA-Z]\.)+[a-zA-Z]{2,9})$/
															.test(input.val());
												}
												return true;
											},

									emailUniqueness : function(input) {
												if (input
														.filter("[name='emailContent']").length
														&& input.val() && flag != "") {
													flag = "";
													return false;
												}
												return true;
											} */
								},
								messages : {
									//custom rules messages
									addressSeasonFrom:"From must be selected in the future",
									addressSeasonTo1:"Select From date first before selecting To date and change To date accordingly",
									addressSeasonTo2:"To date should be after From date",
									pincodeValidation:"Invalid Pincode : Accepts Only 6 digit format",
									phoneContentValidation:"Please Enter Valid Mobile No",
									statevalidation:"Invalid state name, accepts only alphabets",
									cityalidation:"Invalid city name, accepts only alphabets",
									//mobileValidation : "Invalid mobile number, please enter 10 digit number",
									//mobileUniqueness : "Mobile Number Already Exists",
									//emailValidation : "Invalid Email Format",
									//emailUniqueness : "Email Address Already Exists"
								}
							});

		})(jQuery, kendo);
		//End Of Validation
		function csVendorAddressRemove(){
			
			securityCheckForActions("./conciergeManagement/conciergeVendors/address/deleteButton");
			
			
		}
		function contactInfo()
		{
			var aId="";
			var gaddressview = $("#gridAddress_"+SelectedRowId).data("kendoGrid");
			var selectedAddressItem = gaddressview.dataItem(gaddressview.select());
			aId = selectedAddressItem.addressId;
			
			$.ajax({
				type : "POST",
				dataType:"text",
				url : "./manpower/getContacts",
				dataType : "json",
				data : {
					addressId : aId,
				},
				success : function(response) {
			
         		 var contactList = "<table frame=box bgcolor=white width=450px><tr bgcolor=#0A7AC2>";
         		contactList = contactList +"<th><font color=white>Contact Type</font></th><th><font color=white>Contact Content</font></th><th><font color=white>Primary Contact?</font></th></tr>";
         		 
         		if(response.length>0)
         			{
         		 for ( var s = 0, len = response.length; s < len; ++s) {
                 	var contact = response[s];
                 	contactList += "<tr><td align=center>"+contact.contactType +"</td><td align=center>"+contact.contactContent+ "</td><td align=center>"+contact.contactPrimary+"</td></tr>";
             }
         			}
         		else{
         			contactList += "<tr><td>&nbsp;&nbsp;No items to display </td><td></td><td></td></tr>";
                    		
         		}
         		contactList = contactList +"</table>";
				 
				 //$("#contactDialogContent").text(contactList);
				 $('#dialogBoxforContacts').html("");
				 $('#dialogBoxforContacts').html(contactList);
				 $('#dialogBoxforContacts').dialog({
		             	width: 475,
		             	position: 'center',
						modal : true,
					}); 
				 
				
				}
			});
			return false;
		}
		
		function addressLocationEditor(container, options) {
			$('<input name="Address Location" data-bind="value:' + options.field + '" required="true"/>').appendTo(
					container).kendoDropDownList({
				dataTextField : "value",
				dataValueField : "name",
				dataSource : {
					transport : {
						read : "${addressLocationUrl}"
					}
				},
				optionLabel : {
					value : "Select",
					name : "",
				}
			});
			$('<span class="k-invalid-msg" data-for="Address Location"></span>').appendTo(container); 
		}

		function addressPrimaryEditor(container, options) {
			$('<input name="Address Primary" id="addressPrimary" data-bind="value:' + options.field + '" required="true"/>').appendTo(
					container).kendoDropDownList({
				dataTextField : "value",
				dataValueField : "name",
				dataSource : {
					transport : {
						read : "${addressPrimaryUrl}"
					}
				},
				optionLabel : {
					value : "Select",
					name : "",
				}
			});
			$('<span class="k-invalid-msg" data-for="Address Primary"></span>').appendTo(container); 
		}

		function countryEditor(container, options) {
			$(
					'<select name="Country Name" data-text-field="countryName" class="country" data-value-field="countryId" id="countryId" data-bind="value:' + options.field + '" requitred="true"/>')
					.appendTo(container).kendoDropDownList({
						autoBind : false,
						optionLabel : "Select",
						dataSource : {
							transport : {
								read : "${countryUrl}"
							}
						}
					});
			$('<span class="k-invalid-msg" data-for="Country Name"></span>').appendTo(container); 
		}

		$(document).on('change', 'select[class="country"]', function() {

			//alert($('.country:selected').text());

			var sal = $('.country option:selected').eq(0).text();

			if (sal == 'Other') {
				$('label[for="stateId"]').hide();
				$('div[data-container-for="stateId"]').hide();

				$('label[for="cityId"]').hide();
				$('div[data-container-for="cityId"]').hide();

				$('label[for="countryotherId"]').show();
				$('div[data-container-for="countryotherId"]').show();
				$('label[for="stateotherId"]').show();
				$('div[data-container-for="stateotherId"]').show();
				$('label[for="cityotherId"]').show();
				$('div[data-container-for="cityotherId"]').show();
			} else {
				$('label[for="stateId"]').show();
				$('div[data-container-for="stateId"]').show();

				$('label[for="cityId"]').show();
				$('div[data-container-for="cityId"]').show();

				$('label[for="countryotherId"]').hide();
				$('div[data-container-for="countryotherId"]').hide();
				$('label[for="stateotherId"]').hide();
				$('div[data-container-for="stateotherId"]').hide();
				$('label[for="cityotherId"]').hide();
				$('div[data-container-for="cityotherId"]').hide();
			}

		});

		function stateEditor(container, options) {
			$(
					'<select data-text-field="stateName" class = "state" id = "stateId" data-value-field="stateId" data-bind="value:' + options.field + '"/>')
					.appendTo(container).kendoDropDownList({
						cascadeFrom : "countryId",
						optionLabel : "Select",
						autoBind : false,
						dataSource : {
							transport : {
								read : "${stateUrl}"
							}
						}
					});
		}

		$(document).on('change', 'select[class="state"]', function() {

			//alert($('.country:selected').text());

			var sal = $('.state option:selected').eq(0).text();
			//alert(sal);
			if (sal == 'Other') {
				
				$('label[for="cityId"]').hide();
				$('div[data-container-for="cityId"]').hide();

				$('label[for="stateotherId"]').show();
				$('div[data-container-for="stateotherId"]').show();
				$('label[for="cityotherId"]').show();
				$('div[data-container-for="cityotherId"]').show();
				$('input[name="stateotherId"]').prop('required',true);
				$('input[name="cityotherId"]').prop('required',true);
			} else {
				$('label[for="cityId"]').show();
				$('div[data-container-for="cityId"]').show();
				$('input[name="stateotherId"]').prop('required',false);
				$('input[name="cityotherId"]').prop('required',false);
				$('label[for="stateotherId"]').hide();
				$('div[data-container-for="stateotherId"]').hide();
				$('label[for="cityotherId"]').hide();
				$('div[data-container-for="cityotherId"]').hide();
			}

		});

		function cityEditor(container, options) {

			$(
					'<select data-text-field="cityName" class = "city" data-value-field="cityId" data-bind="value:' + options.field + '"/>')
					.appendTo(container).kendoDropDownList({
						cascadeFrom : "stateId",
						optionLabel : "Select",
						autoBind : false,
						dataSource : {

							transport : {
								read : "${cityUrl}"
							},
						}
					});
		}

		$(document).on('change', 'select[class="city"]', function() {

			var sal = $('.city option:selected').eq(0).text();

			if (sal == 'Other') {

				$('label[for="cityotherId"]').show();
				$('div[data-container-for="cityotherId"]').show();
				$('input[name="cityotherId"]').prop('required',true);
			} else {
				$('input[name="cityotherId"]').prop('required',false);
				$('label[for="cityotherId"]').hide();
				$('div[data-container-for="cityotherId"]').hide();
			}

		});

		function addressSeasonEditor(container, options) {
			$('<input data-bind="value:' + options.field + '"/>').appendTo(
					container).kendoDropDownList({
				dataTextField : "value",
				dataValueField : "name",
				dataSource : {
					transport : {
						read : "${addressSeasonUrl}"
					}
				},
				optionLabel : {
					value : "Select",
					name : "",
				}
			});
		}

		function address1Editor(container, options) {
			$(
					'<textarea name="address1" data-text-field="address1" data-value-field="address1" data-bind="value:' + options.field + '" style="width: 148px; height: 61px;" />')
					.appendTo(container);
			$('<span class="k-invalid-msg" data-for="address1"></span>').appendTo(container); 
		}

		function address2Editor(container, options) {
			$(
					'<textarea data-text-field="address2" data-value-field="address2" data-bind="value:' + options.field + '" style="width: 148px; height: 61px;"/>')
					.appendTo(container);
		}

		function address3Editor(container, options) {
			$(
					'<textarea data-text-field="address3" data-value-field="address3" data-bind="value:' + options.field + '" style="width: 148px; height: 61px;"/>')
					.appendTo(container);
		}

		$(document)
				.on(
						'change',
						'input[name="addressSeason"]',
						function() {

							var test = $('input:checked').length ? $(
									'input:checked').val() : '';

							if (test == "") {
								$('input[name="addressSeasonTo"]').prop('required',false);
								$('input[name="addressSeasonFrom"]').prop('required',false);
								$('label[for="addressSeasonFrom"]').hide();
								$('div[data-container-for="addressSeasonFrom"]')
										.hide();

								$('label[for="addressSeasonTo"]').hide();
								$('div[data-container-for="addressSeasonTo"]')
										.hide();
							}
							if (test == "on") {
								$('label[for="addressSeasonFrom"]').show();
								$('div[data-container-for="addressSeasonFrom"]')
										.show();

								$('label[for="addressSeasonTo"]').show();
								$('div[data-container-for="addressSeasonTo"]')
										.show();
								$('input[name="addressSeasonTo"]').prop('required',true);
								$('input[name="addressSeasonFrom"]').prop('required',true);
							}

						});


		function onRequestEndAddress(e) {
			/* debugger; */

			if (typeof e.response != 'undefined') {
				if (e.response.status == "FAIL") {

					errorInfo = "";
					var s;
					for (s = 0; s < e.response.result.length; s++) {
						errorInfo += (s + 1) + ". "
								+ e.response.result[s].defaultMessage + "<br>";

					}

					if (e.type == "create") {
						$('#gridAddress_' + SelectedRowId).data().kendoGrid.dataSource
								.read({
									personId : SelectedRowId
								});

						$("#alertsBox").html("");
						$("#alertsBox").html(
								"Error: Creating the Staff record<br>"
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

					if (e.type == "update") {
						$('#gridAddress_' + SelectedRowId).data().kendoGrid.dataSource
								.read({
									personId : SelectedRowId
								});
						$("#alertsBox").html("");
						$("#alertsBox").html(
								"Error: Updating the Staff record<br>"
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

					return false;
				}

				else if (e.response.status == "INVALID") {

					errorInfo = "";

					errorInfo = e.response.result.invalid;

					if (e.type == "create") {
						$('#gridAddress_' + SelectedRowId).data().kendoGrid.dataSource
								.read({
									personId : SelectedRowId
								});
						$("#alertsBox").html("");
						$("#alertsBox").html(
								"Error: Creating the Staff record<br>"
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

					return false;
				}

				else if (e.response.status == "EXCEPTION") {

					errorInfo = "";

					errorInfo = e.response.result.exception;

					if (e.type == "create") {
						$('#gridAddress_' + SelectedRowId).data().kendoGrid.dataSource
								.read({
									personId : SelectedRowId
								});
						$("#alertsBox").html("");
						$("#alertsBox").html(
								"Error: Creating the Staff record<br>"
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

					if (e.type == "update") {
						$('#gridAddress_' + SelectedRowId).data().kendoGrid.dataSource
								.read({
									personId : SelectedRowId
								});
						$("#alertsBox").html("");
						$("#alertsBox").html(
								"Error: Updating the Staff record<br>"
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

					return false;
				}
				else if (e.response.status == "PRIMARY") {

					errorInfo = "";
					var s;
					for (s = 0; s < e.response.result.length; s++) {
						errorInfo += (s + 1) + ". "
								+ e.response.result[s].defaultMessage + "<br>";

					}

					if (e.type == "create") {
						$('#gridAddress_' + SelectedRowId).data().kendoGrid.dataSource
								.read({
									personId : SelectedRowId
								});

						$("#alertsBox").html("");
						$("#alertsBox").html(
								"Error: Primary address is already exists<br>"
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

					if (e.type == "update") {
						$('#gridAddress_' + SelectedRowId).data().kendoGrid.dataSource
								.read({
									personId : SelectedRowId
								});
						$("#alertsBox").html("");
						$("#alertsBox").html(
								"Error: Primary Address is already exists<br>"
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

					return false;
				}

				else if (e.type == "create") {

					$('#gridAddress_' + SelectedRowId).data().kendoGrid.dataSource
							.read({
								personId : SelectedRowId
							});
					$('#gridContact_' + SelectedRowId).data().kendoGrid.dataSource
					.read({
						personId : SelectedRowId
					});
					$("#alertsBox").html("");
					$("#alertsBox").html("Address Record created successfully");
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
				}

				else if (e.type == "update") {
					$('#gridAddress_' + SelectedRowId).data().kendoGrid.dataSource
							.read({
								personId : SelectedRowId
							});
					$("#alertsBox").html("");
					$("#alertsBox").html("Address Record updated successfully");
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});

				}
				else if (e.response.status == "DELETE") {
					$('#gridContact_' + SelectedRowId).data().kendoGrid.dataSource
					.read({
						personId : SelectedRowId
					});
				}

			}

		}

		function addressEvent(e)
		{
			//$("#mobile-number").intlTelInput();
			if (e.model.isNew()) 
		    {
				securityCheckForActions("./conciergeManagement/conciergeVendors/address/createButton");
				
				$(".k-window-title").text("Add New Address");
				$(".k-grid-update").text("Save");	
				$('label[for="countryName"]').closest('.k-edit-label').remove();
				$('div[data-container-for="countryName"]').remove();

				$('label[for="countryName"]').closest('.k-edit-label').remove();
				$('div[data-container-for="countryName"]').remove();
				
				$('label[for="stateName"]').closest('.k-edit-label').remove();
				$('div[data-container-for="stateName"]').remove();

				$('label[for="cityName"]').closest('.k-edit-label').remove();
				$('div[data-container-for="cityName"]').remove();

				$('label[for="address"]').closest('.k-edit-label').remove();
				$('div[data-container-for="address"]').remove();
				
				$('label[for="addressSeasonFrom"]').hide();
				$('div[data-container-for="addressSeasonFrom"]').hide();

				
				$('label[for="addressSeasonTo"]').hide();
				$('div[data-container-for="addressSeasonTo"]').hide();
				$('label[for="countryotherId"]').hide();
				$('div[data-container-for="countryotherId"]')
						.hide();
				$('label[for="stateotherId"]').hide();
				$('div[data-container-for="stateotherId"]').hide();
				$('label[for="cityotherId"]').hide();
				$('div[data-container-for="cityotherId"]').hide();
				
				$(".k-edit-form-container").css({
					"width" : "550px"
				});
				$(".k-window").css({
					"top" : "150px" 
				});
				$('.k-edit-label:nth-child(12n+1)').each(
						function(e) {
							$(this).nextAll(':lt(11)').addBack().wrapAll(
									'<div class="wrappers"/>');
						});
				$(".k-window").css({"position" : "fixed"});

				/* $.ajax({
					type : "GET",
					url : '${contactContentUrl}',
					dataType : "JSON",
					success : function(response) {
						$.each(response, function(index, value) {
							resContact.push(value);
						});
					}
				}); */
			/* 	
				$.ajax({
					type : "GET",
					url : '${contactContentUrl}/Email',
					dataType : "JSON",
					success : function(response) {
						$.each(response, function(index, value) {
							resEmail.push(value);
						});
					}
				}); */
				
				var addressGrid = $("#gridAddress_"+SelectedRowId).data("kendoGrid");
				var addressGriddata = addressGrid.dataSource.data();
				for (var i = 0; i < addressGriddata.length; i++)
				{
					//alert(addressGriddata[i].addressPrimary);
					if (addressGriddata[i].addressPrimary == "Yes") 
					{
						YesExists = 1;
						alert("Primary Address is already Exists, this address will be your secondary address");
						var dropdownlist = $("#addressPrimary").data("kendoDropDownList");
						dropdownlist.search('Yes');
						dropdownlist.readonly();
						var addressPrimary = $("#gridAddress_"+SelectedRowId).data().kendoGrid.dataSource.data()[0];
						addressPrimary.set('addressPrimary', 'No');
					} 
				}
				
				
			} else {
				
				securityCheckForActions("./conciergeManagement/conciergeVendors/address/updateButton");
				$(".k-window-title").text("Edit Address Details");
				$('.k-edit-field .k-input').first().focus();
				$('label[for="countryName"]').closest('.k-edit-label').remove();
				$('div[data-container-for="countryName"]').remove();
				
				$('label[for="emailContent"]').closest('.k-edit-label').remove();
				$('div[data-container-for="emailContent"]').remove();
				
				$('label[for="phoneContent"]').closest('.k-edit-label').remove();
				$('div[data-container-for="phoneContent"]').remove();

				$('label[for="stateName"]').closest('.k-edit-label').remove();
				$('div[data-container-for="stateName"]').remove();

				$('label[for="cityName"]').closest('.k-edit-label').remove();
				$('div[data-container-for="cityName"]').remove();

				$('label[for="address"]').closest('.k-edit-label').remove();
				$('div[data-container-for="address"]').remove();

				$('label[for="countryotherId"]').hide();
				$('div[data-container-for="countryotherId"]').hide();
				$('label[for="stateotherId"]').hide();
				$('div[data-container-for="stateotherId"]').hide();
				$('label[for="cityotherId"]').hide();
				$('div[data-container-for="cityotherId"]').hide();
				
				var dropdownlist = $("#addressPrimary").data("kendoDropDownList");
				
				dropdownlist.readonly();
				
				$(".k-edit-form-container").css({
					"width" : "550px"
				});
				$(".k-window").css({
					"top" : "150px"
				});
				$('.k-edit-label:nth-child(12n+1)').each(
						function(e) {
							$(this).nextAll(':lt(11)').addBack().wrapAll(
									'<div class="wrappers"/>');
						});
				/* $(".k-window").css({"position" : "fixed"}); */
				
				var test = $('input:checked').length ? $(
				'input:checked').val() : '';

		if (test == "") {
			$('input[name="addressSeasonTo"]').prop('required',false);
			$('input[name="addressSeasonFrom"]').prop('required',false);
			$('label[for="addressSeasonFrom"]').hide();
			$('div[data-container-for="addressSeasonFrom"]')
					.hide();

			$('label[for="addressSeasonTo"]').hide();
			$('div[data-container-for="addressSeasonTo"]')
					.hide();
		}
		if (test == "on") {
			$('label[for="addressSeasonFrom"]').show();
			$('div[data-container-for="addressSeasonFrom"]')
					.show();

			$('label[for="addressSeasonTo"]').show();
			$('div[data-container-for="addressSeasonTo"]')
					.show();
			$('input[name="addressSeasonTo"]').prop('required',true);
			$('input[name="addressSeasonFrom"]').prop('required',true);
		}
		
		
				
			}
			
			$(".k-grid-update").click(function () 
					{
						var countrySelect = $('select[data-text-field="countryName"] :selected').text();
						var stateSelect = $('select[data-text-field="stateName"] :selected').text();	
						var citySelect = $('select[data-text-field="cityName"] :selected').text();
						
						var str = "Please Select \n";
						
						 if(countrySelect === "" || countrySelect === "Select"){
							str = str + "    - Country\n";
						 }
						 
						 if(stateSelect === "" || stateSelect === "Select"){
							str = str + "    - State\n";
						  }
						
						if(citySelect === "" || citySelect === "Select"){
							str = str + "    - City"	;
							alert(str);
								  return false;
							  }
						}); 
		}
	</script>

	<!-- --------------------------------------- Contact script ---------------------------------------- -->
	<script>

		var contactCont = "";
		function contactEvent(e) {
			var addOrEdit = 0;
			if (e.model.isNew()) {
				
				addOrEdit = 1;
				
				securityCheckForActions("./conciergeManagement/conciergeVendors/contact/createButton");

				$(".k-window-title").text("Add New Contact");
				$(".k-grid-update").text("Save");
				$('.k-edit-field .k-input').first().focus();
				$('label[for="contactSeasonFrom"]').hide();
				$('div[data-container-for="contactSeasonFrom"]').hide();

				$('label[for="contactSeasonTo"]').hide();
				$('div[data-container-for="contactSeasonTo"]').hide();

				$('label[for="address"]').closest('.k-edit-label').remove();
				$('div[data-container-for="address"]').remove();
						
				
				

			} else {

				addOrEdit = 0;
				contactCont = $('input[name="contactContent"]').val();

				securityCheckForActions("./conciergeManagement/conciergeVendors/contact/updateButton");
				$(".k-window-title").text("Edit Contact Details");
				$('.k-edit-field .k-input').first().focus();

				var test = $('input:checked').length ? $('input:checked').val()
						: '';

				if (test == "") {

					$('input[name="contactSeasonTo"]').prop('required', false);
					$('input[name="contactSeasonFrom"]')
							.prop('required', false);

					$('label[for="contactSeasonFrom"]').hide();
					$('div[data-container-for="contactSeasonFrom"]').hide();

					$('label[for="contactSeasonTo"]').hide();
					$('div[data-container-for="contactSeasonTo"]').hide();
				}
				if (test == "on") {
					$('label[for="contactSeasonFrom"]').show();
					$('div[data-container-for="contactSeasonFrom"]').show();

					$('label[for="contactSeasonTo"]').show();
					$('div[data-container-for="contactSeasonTo"]').show();

					$('input[name="contactSeasonTo"]').prop('required', true);
					$('input[name="contactSeasonFrom"]').prop('required', true);
				}

			}
			
			$(".k-grid-update").click(function () 
					{
						var contactType = $('select[class="contactType"] :selected').text();
						var contactContent = $('input[name="contactContent"]').val();
						var contactPrimaryStatus = $('select[id="contactPrimary"] :selected').text();
					if(addOrEdit === 1){
			
						var contactGrid = $("#gridContact_"+SelectedRowId).data("kendoGrid");
						var contactGriddata = contactGrid.dataSource.data();
						for (var i = 1; i < contactGriddata.length; i++)
						{
							if (contactGriddata[i].contactPrimary == "Yes" && contactGriddata[i].contactType == contactType && contactPrimaryStatus == 'Yes') 
							{
								YesExists = 1;
								alert("  Primary Contact is already Exists for "+contactType+"\n\nThis "+contactType+" contact will be your secondary contact");
								var dropdownlist = $("#contactPrimary").data("kendoDropDownList");
								dropdownlist.search('Yes');
								//dropdownlist.readonly();
								var contactPrimary = $("#gridContact_"+SelectedRowId).data().kendoGrid.dataSource.data()[0];
								contactPrimary.set('contactPrimary', 'No');
							} 
							}
						}
					
							  if(!$.isNumeric(contactContent) && ((contactType ==='Mobile') ||(contactType ==='HomePhone') ||(contactType ==='Intercom'))){
									alert("Please enter valid "+contactType+" Number");
								  return false;
							  }
							  var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
							  if( !emailReg.test(contactContent) && contactType ==='Email') {
							    	alert("Please enter valid Email Address");
								  return false;
							  } 
						}); 
			
			
		/* 	$.ajax({
				type : "GET",
				url : '${contactContentUrl}',
				dataType : "JSON",
				success : function(response) {
					$.each(response, function(index, value) {
						resContact.push(value);
					});
				}
			}); */
		}
		$(document).on('change', 'select[class="contactType"]', function() {
			var content = $('.contactType option:selected').eq(0).text();
			$('label[for="contactContent"]').text(content);

			/* if(content == 'Mobile'){
				$('input[name="contactContent"]').prop('type','tel');
				$('input[name="contactContent"]').prop('id','mobile-number');
				$('input[name="contactContent"]').prop('placeholder','e.g. +1 702 123 4567');
				$("#mobile-number").intlTelInput();
			}
			else{
				$('input[name="contactContent"]').prop('type','text');
				$('input[name="contactContent"]').prop('id','dummy');
				$('input[name="contactContent"]').prop('placeholder','');
				$('.intl-tel-input').removeClass('intl-tel-input').addClass('new_class');
				$('input[name="contactContent"]').val(' ');
				$('.flag-dropdown').remove();
			} */
		});

		var seasonFromContact = "";

		//register custom validation rules
		(function($, kendo) {
			$
					.extend(
							true,
							kendo.ui.validator,
							{
								rules : { // custom rules          	
									contactSeasonFrom : function(input, params) {
										if (input
												.filter("[name = 'contactSeasonFrom']").length
												&& input.val() != "") {
											var selectedDate = input.val();
											var todaysDate = $.datepicker
													.formatDate('dd/mm/yy',
															new Date());
											var flagDate = false;

											if ($.datepicker.parseDate(
													'dd/mm/yy', selectedDate) > $.datepicker
													.parseDate('dd/mm/yy',
															todaysDate)) {
												seasonFromContact = selectedDate;
												flagDate = true;
											}
											return flagDate;
										}
										return true;
									},
									contactSeasonTo1 : function(input, params) {
										if (input
												.filter("[name = 'contactSeasonTo']").length
												&& input.val() != "") {
											var flagDate = false;

											if (seasonFromContact != "") {
												flagDate = true;
											}
											return flagDate;
										}
										return true;
									},
									contactSeasonTo2 : function(input, params) {
										if (input
												.filter("[name = 'contactSeasonTo']").length
												&& input.val() != "") {
											var selectedDate = input.val();
											var flagDate = false;

											if ($.datepicker.parseDate(
													'dd/mm/yy', selectedDate) > $.datepicker
													.parseDate('dd/mm/yy',
															seasonFromContact)) {
												flagDate = true;
											}
											return flagDate;
										}
										return true;
									}
									/* contactContentValidation : function(input,
											params) {
										//check for the name attribute 
										if (input
												.filter("[name='contactContent']").length
												&& input.val()) {
											contactContents = input.val();
											$
													.each(
															resContact,
															function(ind, val) {

																if (contactCont == contactContents) {
																	
																	contactCont = "";
																	return false;
																} else {

																	if ((contactContents == val)
																			&& (contactContents.length == val.length)) {
																		flag = contactContents;
																		return false;
																	}
																}
															});
										}
										return true;
									},

									contactContentUniqueness : function(input) {
										if (input
												.filter("[name='contactContent']").length
												&& input.val() && flag != "") {
											flag = "";
											return false;
										}
										return true;
									} */
								},
								messages : {
									//custom rules messages
									contactSeasonFrom : "From must be selected in the future",
									contactSeasonTo1 : "Select From date first before selecting To date and change To date accordingly",
									contactSeasonTo2 : "To date should be after From date",
									//contactContentUniqueness : "Contact details already exists"
								}
							});

		})(jQuery, kendo);
		//End Of Validation
		function contactRemove(){
		securityCheckForActions("./conciergeManagement/conciergeVendors/contact/deleteButton");
	}

		function addressforcontactEditor(container, options) {
			$('<input id="addrcontact" data-bind="value:' + options.field + '"/>')
					.appendTo(container)
					.kendoComboBox(
							{
								dataTextField : "addressLocation",
								dataValueField : "addressId",
								template : ''
										+ '<span class="k-state-default"><b>#: data.addressLocation#&nbsp;Address</b></span><br>'
										+ '<span class="k-state-default">Primary? - #: data.addressPrimary#</span><br>'
										+ '<span class="k-state-default"><i> #: data.address#</i></span>',

								dataSource : {
									transport : {
										read : "${addressforcontactUrl}/"
												+ SelectedRowId
									}
								},
								change : function (e) {
						            if (this.value() && this.selectedIndex == -1) {                    
										alert("Address doesn't exist!");
						                $("#addrcontact").data("kendoComboBox").value("");
						        	}
							    },
								placeholder : "Select ...",
							});
		}

		function contactLocationEditor(container, options) {
			$(
					'<input name="Contact Location" data-bind="value:' + options.field + '" required="true"/>')
					.appendTo(container).kendoDropDownList({
						dataTextField : "value",
						dataValueField : "name",
						dataSource : {
							transport : {
								read : "${contactLocationUrl}"
							}
						},
						optionLabel : {
							value : "Select",
							name : "",
						}
					});
			$('<span class="k-invalid-msg" data-for="Contact Location"></span>')
					.appendTo(container);
		}

		function contactTypeEditor(container, options) {
			$(
					'<select class="contactType" name="Contact Type" data-bind="value:' + options.field + '" required="true"/>')
					.appendTo(container).kendoDropDownList({
						dataTextField : "value",
						dataValueField : "name",
						dataSource : {
							transport : {
								read : "${contactTypeUrl}"
							}
						},
						optionLabel : {
							value : "Select",
							name : "",
						}
					});
			$('<span class="k-invalid-msg" data-for="Contact Type"></span>')
					.appendTo(container);
		}

		function contactPrimaryEditor(container, options) {
			$(
					'<select name="Contact Primary" id="contactPrimary" data-bind="value:' + options.field + '" required="true"/>')
					.appendTo(container).kendoDropDownList({
						dataTextField : "value",
						dataValueField : "name",
						dataSource : {
							transport : {
								read : "${contactPrimaryUrl}"
							}
						},
						optionLabel : {
							value : "Select",
							name : "",
						}
					});
			$('<span class="k-invalid-msg" data-for="Contact Primary"></span>')
					.appendTo(container);
		}

		function contactPreferredTimeEditor(container, options) {
			$(
					'<input data-text-field="' + options.field + '" id="timepicker" data-value-field="' + options.field + '" data-bind="value:' + 
						    		options.field + '" data-format="' + ["HH:mm:ss"] + '"/>')
					.appendTo(container).kendoTimePicker({
						name : ("contactPrefferedTime"),
						Value : ("$now")

					});
		}

		$(document)
				.on(
						'change',
						'input[name="contactSeason"]',
						function() {

							var test = $('input:checked').length ? $(
									'input:checked').val() : '';

							if (test == "") {

								$('input[name="contactSeasonTo"]').prop(
										'required', false);
								$('input[name="contactSeasonFrom"]').prop(
										'required', false);

								$('label[for="contactSeasonFrom"]').hide();
								$('div[data-container-for="contactSeasonFrom"]')
										.hide();

								$('label[for="contactSeasonTo"]').hide();
								$('div[data-container-for="contactSeasonTo"]')
										.hide();
							}
							if (test == "on") {
								$('label[for="contactSeasonFrom"]').show();
								$('div[data-container-for="contactSeasonFrom"]')
										.show();

								$('label[for="contactSeasonTo"]').show();
								$('div[data-container-for="contactSeasonTo"]')
										.show();

								$('input[name="contactSeasonTo"]').prop(
										'required', true);
								$('input[name="contactSeasonFrom"]').prop(
										'required', true);
							}

						});

		function onRequestEndContact(e) {
			/* debugger; */

			if (typeof e.response != 'undefined') {
				if (e.response.status == "FAIL") {

					errorInfo = "";
					var s;
					for (s = 0; s < e.response.result.length; s++) {
						errorInfo += (s + 1) + ". "
								+ e.response.result[s].defaultMessage + "<br>";

					}

					if (e.type == "create") {
						$('#gridContact_' + SelectedRowId).data().kendoGrid.dataSource
								.read({
									personId : SelectedRowId
								});

						$("#alertsBox").html("");
						$("#alertsBox").html(
								"Error: Creating the Contact record<br>"
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

					if (e.type == "update") {
						$('#gridContact_' + SelectedRowId).data().kendoGrid.dataSource
								.read({
									personId : SelectedRowId
								});
						$("#alertsBox").html("");
						$("#alertsBox").html(
								"Error: Updating the Contact record<br>"
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

					return false;
				}

				else if (e.response.status == "INVALID") {

					errorInfo = "";

					errorInfo = e.response.result.invalid;

					if (e.type == "create") {
						$('#gridContact_' + SelectedRowId).data().kendoGrid.dataSource
								.read({
									personId : SelectedRowId
								});
						$("#alertsBox").html("");
						$("#alertsBox").html(
								"Error: Creating the Contact record<br>"
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

					return false;
				}

				else if (e.response.status == "EXCEPTION") {

					errorInfo = "";

					errorInfo = e.response.result.exception;

					if (e.type == "create") {
						$('#gridContact_' + SelectedRowId).data().kendoGrid.dataSource
								.read({
									personId : SelectedRowId
								});
						$("#alertsBox").html("");
						$("#alertsBox").html(
								"Error: Creating the Contact record<br>"
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

					if (e.type == "update") {
						$('#gridContact_' + SelectedRowId).data().kendoGrid.dataSource
								.read({
									personId : SelectedRowId
								});
						$("#alertsBox").html("");
						$("#alertsBox").html(
								"Error: Updating the Contact record<br>"
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

					return false;
				}

				else if (e.response.status == "PRIMARY") {

					errorInfo = "";
					var s;
					for (s = 0; s < e.response.result.length; s++) {
						errorInfo += (s + 1) + ". "
								+ e.response.result[s].defaultMessage + "<br>";

					}

					if (e.type == "create") {
						$('#gridContact_' + SelectedRowId).data().kendoGrid.dataSource
								.read({
									personId : SelectedRowId
								});

						$("#alertsBox").html("");
						$("#alertsBox").html(
								"Error: Primary Contact for Respective Type Already Exists<br>"
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

					if (e.type == "update") {
						$('#gridContact_' + SelectedRowId).data().kendoGrid.dataSource
								.read({
									personId : SelectedRowId
								});
						$("#alertsBox").html("");
						$("#alertsBox").html(
								"Error: Primary Contact for Respective Type Already Exists<br>"
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

					return false;
				} else if (e.type == "create") {

					$('#gridContact_' + SelectedRowId).data().kendoGrid.dataSource
							.read({
								personId : SelectedRowId
							});
					$("#alertsBox").html("");
					$("#alertsBox").html("Contact Record created successfully");
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
				}

				else if (e.type == "update") {
					$('#gridContact_' + SelectedRowId).data().kendoGrid.dataSource
							.read({
								personId : SelectedRowId
							});
					$("#alertsBox").html("");
					$("#alertsBox").html("Contact Record updated successfully");
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});

				}
				 else if (e.type == "destroy") {
					 $("#alertsBox").html("");
						$("#alertsBox").html("Contact deleted successfully");
								$("#alertsBox").dialog({
									modal : true,
									buttons : {
										"Close" : function() {
											$(this).dialog("close");
										}
									}
								});
						
				 }

			}
		}
	</script>
	

<!-- -------------------------------- Document script ------------------------------------------------------------------ -->

<script type="text/javascript">

function UploadFileBound(e) {
	var tempArray = "";
	if(kycCompliant !=null)
	{
		tempArray = kycCompliant.split(","); 
	}
 	var found = $.inArray('ConciergeVendor', tempArray) > -1;
 	if(found)
 		{
 			$("#approveDocuments_"+SelectedRowId).hide();
 		}
 	else if(kycCompliant == null || found == false)
 		{
 			$("#approveDocuments_"+SelectedRowId).show();
 		}
	
    this.tbody.find("tr td:first-child").each(function (e) 
    {
        $("<input type='file' name='files'  />").appendTo(this).kendoUpload({
            async: {
                saveUrl: "${saveUrl}",
                removeUrl: "remove",
                autoUpload: false,
            },
        	multiple : false,
            upload : uploadExtraDataAlongWithFile, 
            select : filesSelectedtoUpload,
            success: clearList
        });
    });
    
}
function documentRemove(){
	
	securityCheckForActionsForStatus("./conciergeManagement/conciergeVendors/documents/deleteButton");
	
}
var selectedRowIndex = "";
function documentChangeEvent(args)
{
	var grid = $("#documentsUpload_"+SelectedRowId).data("kendoGrid");
	var selectedRow = grid.select();
	selectedRowIndex = selectedRow.index();
}

		var documentTypeSelected = "";
		function handleSelect(e)
		{
			//alert("Changed>>"+SelectedRowId);
			var dataItem = this.dataItem(e.item.index());
		  //alert("event :: select (" + dataItem.name + " : " + dataItem.value + ")" );
			documentTypeSelected = dataItem.name;
			$.ajax({
				type : "POST",
				dataType:"text",
				url : "./documentDefiner/getDocumentFormat",
				data : {
					documentTypeSelected : dataItem.ddId,
					personType : "ConciergeVendor",
					},
					success : function(response)
					{
						$("#docFormat_"+SelectedRowId).val(response);
					}
				
				});
		}

		function documentNameEditor(container, options)
		  {	
			  $('<input name="Document Name" required="true" validationMessage="Document Name is Required" data-text-field="name" data-value-field="value" data-bind="value:' + options.field + '"/>')
				.appendTo(container).kendoDropDownList({	
					optionLabel : {
						name : "Select",
						value : "",
					},
					defaultValue : false,
					select: onDocumentSelect,
					sortable : true,
					dataSource : {
						transport : {
							read : "${getDocumentType}/ConciergeVendor"
						}
					}
				});

		  }
		
		function documentDecriptionEditor(container, options) 
		{
			$('<textarea data-text-field="documentDescription" name="documentDescription" data-value-field="documentDescription" data-bind="value:' + options.field + '" style="width: 148px; height: 40px;"/>')
					.appendTo(container);
		}

		function onDocumentSelect(e) 
		{
		        var dataItem = this.dataItem(e.item.index());
		        $.ajax({
					type : "POST",
					dataType:"text",
					url : "./documentDefiner/getDocumentFormat",
					data : {
						documentTypeSelected : dataItem.ddId,
						personType : "ConciergeVendor",
						},
						success : function(response)
						{
							$("#docFormatId").val(response);
							var documentFormat = $("#documentsUpload_"+SelectedRowId).data().kendoGrid.dataSource
						      .data()[selectedRowIndex];
							documentFormat.set('documentFormat', response);
						}
					
					});

		}        

		  function documentFormatEditor(container, options)
		  {	
			  $('<input type="text" readonly="true" class="k-input k-textbox" id="docFormatId" name="documentFormat" data-bind="value:' + options.field + '"/>')
				.appendTo(container);
		  }
		 var drId = "";
		 var documentFormatToUpload = "";
		  function uploadNotice()
			{
			    var gview = $("#documentsUpload_"+SelectedRowId).data("kendoGrid");
				//Getting selected item
				
				
				var selectedItem = gview.dataItem(gview.select());
				  if (selectedItem != null) 
				  {
					  drId = selectedItem.drId;
					  documentFormatToUpload = selectedItem.documentFormat;
				  } 
				$('#uploadDialog').dialog({
					modal : true,
				});
				return false;
			}
		
		function uploadExtraData(e)
		{
			  //alert(JSON.stringify(e));
			  //alert(SelectedRowId);
			  var docNumber = $("#docNumber_"+SelectedRowId).val();
			  e.data = { ddType: documentTypeSelected,documentNumber : docNumber,personId : SelectedRowId  };
		} 
		function uploadExtraDataAlongWithFile(e)
		{	
			e.data = { drId:drId};
	    }
		function downloadFile()
		 {
			//securityCheckForActions("./conciergeManagement/conciergeVendors/documents/viewButton");
			
			var result=securityCheckForActionsForStatus("./conciergeManagement/conciergeVendors/documents/viewButton");
			if(result=="success")
		 	{
				var gview = $("#documentsUpload_"+SelectedRowId).data("kendoGrid");
				//Getting selected item
				var selectedItem = gview.dataItem(gview.select());
				window.open("./download/"+selectedItem.drId);
				
		 	}
			
			
			 
				/* $.ajax({
					 type : "POST",
					 url :"./download/"+selectedItem.drId,
					 success : function(response)
					 {
						 alert(response);
					 }
					}); */
		 }

		 var selectedType = "";
		  
		 function getUpload() 
		 {
		     return $("#files").data("kendoUpload");
		 }
		 
		 function onSelect(e) 
		 {
		      
		         var dataItem = this.dataItem(e.item.index());
		         selectedType =  dataItem.value;
		         if((selectedType != '') ||(selectedType != null))
		         {
		             $("#hiddenField").val(selectedType);
		        	 getUpload().enable();
		         }
		       // alert("event :: select (" + dataItem.text + " : " + dataItem.value + ")" );
		     
		 }
		
		function filesSelectedtoUpload(e)
		  {
			var result=securityCheckForActionsForStatus("./conciergeManagement/conciergeVendors/documents/selectFile");
			if(result=="success")
			{
			//alert("document Selected");
			var gview = $("#documentsUpload_"+SelectedRowId).data("kendoGrid");
			//Getting selected item
		
		
			var selectedItem = gview.dataItem(gview.select());
			if (selectedItem != null) 
			{
				  drId = selectedItem.drId;
				  documentFormatToUpload = selectedItem.documentFormat;
			} 
			  var files = e.files;
			  var requiredFormat = $("#docFormat_"+SelectedRowId).val();
			  requiredFormat = "."+documentFormatToUpload.toLowerCase();//requiredFormat.toLowerCase();
			 // alert(requiredFormat);
			 // alert(files.length);
			 if(requiredFormat == '')
				 {
					alert("Please select Document Type");
					return false;
				 }
				if( files.length  > 10 ) {
					alert("Maximum 10 files can be uploaded at a time.");
					e.preventDefault();
					return false;
				}
			 
				for(var fileCntr = 0; fileCntr < files.length; fileCntr ++) {
					if( files[fileCntr].size > 10485760 ) {
						alert("File size more than 10MB can not be uploaded.");
						e.preventDefault();
						return false;
					}
					
					if(documentFormatToUpload == "Image")
						{
						
							//alert(documentFormatToUpload+"----"+files[fileCntr].extension.toLowerCase());
							if( files[fileCntr].extension.toLowerCase() == '.png' || files[fileCntr].extension.toLowerCase() == '.jpg' || files[fileCntr].extension.toLowerCase() == '.jpeg' ) 
							{
								 
							}
							else
							{
								alert("Only Images can be uploaded\nAcceptable formats are png, jpg and jpeg");
								e.preventDefault();
								return false;
							}
						
						}
					else
						{
							if( files[fileCntr].extension.toLowerCase() != requiredFormat ) {
								alert("Please Upload "+documentFormatToUpload+" Format");
								e.preventDefault();
								return false;
							}	
						
						}
			 
				}
				}

		  }
		
		
		function photoSelectedtoUpload(e)
		  {
			  var files = e.files;
			
				if( files.length  > 10 ) {
					alert("Maximum 10 files can be uploaded at a time.");
					e.preventDefault();
					return false;
				}
			 
				for(var fileCntr = 0; fileCntr < files.length; fileCntr ++) {
					if( files[fileCntr].size > 10485760 ) {
						alert("File size more than 10MB can not be uploaded.");
						e.preventDefault();
						return false;
					}
			 
					/* if( files[fileCntr].extension.toLowerCase() != '.png' || files[fileCntr].extension.toLowerCase() != '.jpg') {
						alert("Executable files can not be uploaded.");
						e.preventDefault();
						return false;
					} */
				}

		  }
		
		
		 function ajaxFormSubmit()
		 {
			 if((selectedType == '') ||(selectedType == null))
		     {
			     alert("Select Document Type");
			     return false;
		     }
			 else
				 {
					$.ajax({

						type : "POST",
						dataType:"text",
						url : "/owner/document/upload",
						data :
							{
								selectedType : selectedType,
							},
						success : function(response)
						{
							alert(response);
						}
						

							
						});

				 }
		 }

		 function DocumentOnRequestEnd(e)
		  {
			  if (typeof e.response != 'undefined')
				{
					//alert("Response is Undefined");

					if (e.response.status == "FAIL") 
					{
						errorInfo = "";

						for (var k = 0; k < e.response.result.length; k++) 
						{
							errorInfo += (k + 1) + ". "
									+ e.response.result[k].defaultMessage + "<br>";

						}

						if (e.type == "create") {
							$("#alertsBox").html("");
							$("#alertsBox").html(
									"Error: Assigning Permission to AccessCard<br>" + errorInfo);
							$("#alertsBox").dialog({
								modal : true,
								buttons : {
									"Close" : function() {
										$(this).dialog("close");
									}
								}
							});

						}

						else if (e.type == "update") {
							$("#alertsBox").html("");
							$("#alertsBox").html(
									"Error: Updating the Permission to AccessCard<br>" + errorInfo);
							$("#alertsBox").dialog({
								modal : true,
								buttons : {
									"Close" : function() {
										$(this).dialog("close");
									}
								}
							});
						}

						$('#gridAccessCardPermission_'+SelectedRowId).data().kendoGrid.dataSource.read({personId:SelectedAccessCardId});
						/* var grid = $("#propertyGrid_"+SelectedRowId).data("kendoGrid");
						grid.dataSource.read();
						grid.refresh(); */
						return false;
					}
				

			  else if (e.type == "create") 
				{
					$("#alertsBox").html("");
					$("#alertsBox").html("Document Record Created Successfully");
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

				else if (e.type == "update") 
				{
					$("#alertsBox").html("");
					$("#alertsBox").html("Document Record updated successfully");
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
					var grid = $("#documentsUpload_"+SelectedRowId).data("kendoGrid");
		 			grid.dataSource.read();
		 			grid.refresh();
				}
				
				else if (e.response.status == "AciveItemMasterDestroyError") {
		  			$("#alertsBox").html("");
		  			$("#alertsBox").html("Active Records Cannot be Deleted");
		  			$("#alertsBox").dialog({
		  				modal : true,
		  				buttons : {
		  					"Close" : function() {
		  						$(this).dialog("close");
		  					}
		  				}
		  			});
		  			 var grid= $('#documentsUpload_' + SelectedRowId).data("kendoGrid");
					  grid.dataSource.read();
				      grid.refresh();
		  		return false;
		  	  }

				else if (e.type == "destroy") 
				{
					$("#alertsBox").html("");
					$("#alertsBox").html("Document Record delete successfully");
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

		 (function($, kendo) {
				$
						.extend(
								true,
								kendo.ui.validator,
								{
									rules : { // custom rules          	
										documentNumber: function (input, params) 
							             {
						                     if (input.filter("[name = 'documentNumber']").length && input.val() != "") 
						                     {                          
						                    	 return /^[a-zA-Z0-9 ]{0,35}$/
													.test(input.val());
						                     }
						                     return true;
						                 },
						                 documentDescription: function (input, params) 
							             {
						                     if (input.filter("[name = 'documentDescription']").length && input.val() != "") 
						                     {                          
						                    	 return /^[a-zA-Z0-9 ]{0,50}$/
													.test(input.val());
						                     }
						                     return true;
						                 },
						                 documentDescription_blank :  function (input, params) {
						                     //check for the name attribute 
						                     if (input.attr("name") == "documentDescription") {
						                      return $.trim(input.val()) !== "";
						                     }
						                     return true;
						                 }
									},
									messages : {
										//custom rules messages
										documentNumber:"Only alphanumeric is allowed",
										documentDescription:"Only alphanumeric is allowed",
										documentDescription_blank : "Document Description is required"
									}
								});
			
			})(jQuery, kendo);
			//End Of Validation
			
			function documentEvent(e)
			{
				$('div[data-container-for="documentFormat"]').remove();
				$('label[for="documentFormat"]').closest('.k-edit-label').remove();
				if (e.model.isNew()) 
			    {
					securityCheckForActions("./conciergeManagement/conciergeVendors/documents/createButton");
					
					selectedRowIndex = 0;
					$(".k-window-title").text("Add New Document Details");
					$(".k-grid-update").text("Save");
					$('.k-edit-field .k-input').first().focus();
			    }
				else
				{
					securityCheckForActions("./conciergeManagement/conciergeVendors/documents/updateButton");
					
					$(".k-window-title").text("Update Document Details");
					$(".k-grid-update").text("Update");
					$('.k-edit-field .k-input').first().focus();
				}
				
				$(".k-grid-cancel").click(function () {
				
				var grid = $("#documentsUpload_"+SelectedRowId).data("kendoGrid");
	 			grid.dataSource.read();
	 			grid.refresh();
	 			
				});
			}
			
			function approveDocuments()
			{
				var fileStatus="notExists";
				var gview = $("#documentsUpload_"+SelectedRowId).data("kendoGrid");
				var dataSource = gview.dataSource;
				//records on current view / page   
				var recordsOnCurrentView = dataSource.view().length;
				//total records
				var totalRecords = dataSource.total();
				
				
				if(totalRecords == 0)
				{
					$("#alertsBox").html("");
					$("#alertsBox").html("No Documents to approve");
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
					return false;
				}
				
				
				var r = confirm("Have you checked all the KYC documents before approving ?");
				if (r == true && totalRecords > 0) 
				{
					
					$.ajax({
						type : "POST",
						dataType:"text",
						url : "./documents/checkFile/"+SelectedGroupId+"/ConciergeVendor/",
						success : function(response)
						{
							 if( response == "FILEENOTEXISTS" ){
								$("#alertsBox").html("");
								$("#alertsBox").html("Error: Please Upload Document File Before Approving");
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
							
							 else{
									/*checking wid all document  */
									$.ajax({
										type : "POST",
										url : "./documents/checkAllDocument/"+SelectedGroupId+"/ConciergeVendor/",
										dataType:"text",
										success : function(response)
										{	
											if( response == "NOTAPPROVED" ){
												$("#alertsBox").html("");
												$("#alertsBox").html("Error: Please Add All Mandatory Documents Before Approving");
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
											else{
												
												$.ajax({
													type : "POST",
													url : "./documents/approve/"+SelectedRowId+"/ConciergeVendor/"+kycCompliant,
													dataType:"text",
													success : function(response)
													{
														$("#alertsBox").html("");
														$("#alertsBox").html(response);
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
														kycCompliant = "ConciergeVendor";
														$("#approveDocuments_"+SelectedRowId).hide();
														var grid = $("#documentsUpload_"+SelectedRowId).data("kendoGrid");
											 			grid.dataSource.read();
											 			grid.refresh();
													}
												});
												
											}
										}
									});
									
					
								}
							}
						});
					}
				}
</script>



<!-- -------------------------------- Vendor Other Details script ------------------------------------------------------------------ -->

<script type="text/javascript">

var cstNo = "";
var stTaxNo = "";
var serTxNo = "";
var res = [];

function otherDetailsEvent(e)
{
	if (e.model.isNew()) 
    {}
	else{
		securityCheckForActions("./conciergeManagement/conciergeVendors/vendorOtherDetails/updateButton");
		
		$(".k-window-title").text("Edit Vendor Other Details");
	}
		
	
}
function vendorDetailsRequestEnd(e){
	if (typeof e.response != 'undefined')
	{
		if (e.type == "update") {
			 
			 $("#alertsBox").html("");
				$("#alertsBox").html("Vendor Details are updated successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				
				$('#vendorOtherDetails_'+SelectedRowId).data().kendoGrid.dataSource.read();
		 }
	}
}
//register custom validation rules
(function ($, kendo) 
	{   	  
    $.extend(true, kendo.ui.validator, 
    {
         rules: 
         { // custom rules          	
        	 cstNovalidation: function (input, params) 
             {               	 
                 //check for the name attribute 
                 if (input.filter("[name='cstNo']").length && input.val()) 
                 {
                	 cstNo = input.val();
                	$.each(res, function( index, value ) 
					{	
          				if((cstNo == value))
						{
							flag = input.val();								  
          				}  
          			});  
                	return /^[0-9a-zA-Z]+[ _\-a-zA-Z0-9_\-]*[a-zA-Z0-9]$/.test(input.val());
                	/* return /^[a-zA-Z]*[ a-zA-Z][_]{0,1}[a-zA-Z]*[^_]$/.test(input.val()) */
                 }        
                 return true;
             },
             stateTaxNovalidation: function (input, params) 
             {               	 
                 //check for the name attribute 
                 if (input.filter("[name='stateTaxNo']").length && input.val()) 
                 {
                	 stTaxNo = input.val();
                	$.each(res, function( index, value ) 
					{	
          				if((stTaxNo == value))
						{
							flag = input.val();								
          				}  
          			});  
                	return /^[0-9a-zA-Z]+[ _\-a-zA-Z0-9_\-]*[a-zA-Z0-9]$/.test(input.val());
                	/* return /^[a-zA-Z]*[ a-zA-Z][_]{0,1}[a-zA-Z]*[^_]$/.test(input.val()) */
                 }        
                 return true;
             },
             serviceTaxNovalidation: function (input, params) 
             {               	 
                 //check for the name attribute 
                 if (input.filter("[name='serviceTaxNo']").length && input.val()) 
                 {
                	 serTxNo = input.val();
                	$.each(res, function( index, value ) 
					{	
          				if((serTxNo == value))
						{
							flag = input.val();								
          				}  
          			});  
                	return /^[0-9a-zA-Z]+[ _\-a-zA-Z0-9_\-]*[a-zA-Z0-9]$/.test(input.val());
                	/* return /^[a-zA-Z]*[ a-zA-Z][_]{0,1}[a-zA-Z]*[^_]$/.test(input.val()) */
                 }        
                 return true;
             },
             
             CommentNullValidation: function (input, params) {
                 //check for the name attribute 
                 if (input.attr("name") == "comments") {
                	 return $.trim(input.val()) !== "";
                 }
                 return true;
             },
             
             commentDescLengthValidation : function (input, params){ 
           	  if (input.attr("name") == "comments") { 
                   if (input.filter("[name='comments']").length && input.val() != "") 
                   {
                   return /^[\s\S]{1,500}$/.test(input.val());
                   } 
                }
                   return true;
               },
              
               PropertyNoNullValidation: function (input, params) {
                   //check for the name attribute 
                   if (input.attr("name") == "addressNo") {
                  	 return $.trim(input.val()) !== "";
                   }
                   return true;
               }, 
               
               propertyNoLengthValidation : function (input, params){ 
                	  if (input.attr("name") == "addressNo") { 
                        if (input.filter("[name='addressNo']").length && input.val() != "") 
                        {
                        return /^[\s\S]{1,45}$/.test(input.val());
                        } 
                     }
                        return true;
                    },
                    
                  Address1NullValidation: function (input, params) {
                      //check for the name attribute 
                      if (input.attr("name") == "address1") {
                     	 return $.trim(input.val()) !== "";
                      }
                      return true;
                  },
                  
                  Address1LengthValidation : function (input, params){ 
                	  if (input.attr("name") == "address1") { 
                        if (input.filter("[name='address1']").length && input.val() != "") 
                        {
                        return /^[\s\S]{1,500}$/.test(input.val());
                        } 
                     }
                        return true;
                    },
               
               
         }, 
         messages: 
         {
			//custom rules messages
			cstNovalidation: " Cst Number can not allow special symbols except underscore(_) and hyphen(-) ",
        	stateTaxNovalidation: " State Tax Number can not allow special symbols except underscore(_) and hyphen(-) ",
        	serviceTaxNovalidation: " Service Tax Number can not allow special symbols except underscore(_) and hyphen(-) ",
        	CommentNullValidation:"Comment is Required",
			commentDescLengthValidation : "Comments allows max 500 characters", 
			PropertyNoNullValidation:"Property No is Required",
			propertyNoLengthValidation : "Property No allows max 45 characters", 
			Address1NullValidation:"Address is Required",
			Address1LengthValidation : "Address allows max 500 characters", 


         }
    });
    
})(jQuery, kendo);
  //End Of Validation
</script>

<!-- -------------------------------- Vendor Comments Rating script ------------------------------------------------------------------ -->
<script type="text/javascript">
	
	function commentsEvent(e)
	{
		
		 
		$('input[name="ratings"]').change(
				function() {
					var ratings = $('input[name="ratings"]').val();
					
					if(ratings>5){
					    alert("Ratings cannot be greater than 5");
				    	return false;
				    }
				});
		

		
		if (e.model.isNew()) 
	    {
			securityCheckForActions("./conciergeManagement/conciergeVendors/commentsRating/createButton");
			
			$('label[for="ownerId"]').hide();
			$('div[data-container-for="ownerId"]').hide();
			
			$(".k-window-title").text("Add New Vendor Comment");
			$(".k-grid-update").text("Save");
			$('.k-edit-field .k-input').first().focus();
	    }
		else
		{
			securityCheckForActions("./conciergeManagement/conciergeVendors/commentsRating/updateButton");
			
			$('label[for="ownerId"]').hide();
			$('div[data-container-for="ownerId"]').hide();
			
			$(".k-window-title").text("Update Vendor Comment");
			$(".k-grid-update").text("Update");
			$('.k-edit-field .k-input').first().focus();
		}
		$(".k-grid-update").click(function () 
				{
					var ownersList = $("#ownersList").data("kendoComboBox");
					if( ownersList.select() == -1 ){
						alert("Please Select Owner/Tenant Name");
						return false;
					}
				});
	}
	function commentsRateRemove(){
		securityCheckForActions("./conciergeManagement/conciergeVendors/commentsRating/deleteButton");
	}
	  function ownerNamesEditor(container,options)
		 {
			 $('<input name="Owner Name" id="ownersList" data-text-field="ownerNames"  data-value-field="propertyId"  data-bind="value:' + options.field + '" required="true"/>')
		     .appendTo(container)
		         .kendoComboBox({
		        	 filter: "startswith",
		        	optionLabel : "Select",
			        	 defaultValue : false,
							sortable : true,
							template : '<table><tr>'
								+ '<td align="left"><span class="k-state-default"><b>#: data.ownerNames #</b></span><br>'
								+ '<span class="k-state-default"><i>#:blockName #</i></span><br>'
								+'<span class="k-state-default"><i>#:propertyNumber #</i></span></td></tr></table>',
							
		         dataSource: {  
		             transport:{
		                 read: "${ownerNamesUrl}"
		             }
		         },
		         placeholder :"Select",
		     });
			 /* $('<span class="k-invalid-msg" data-for="Status"></span>').appendTo(container); */

		 }
	  function onChange(e){
		  var dataItem = this.dataItem(e.item.index());
		  var firstItem = $('#serviceBookingGrid').data().kendoGrid.dataSource.data()[0];
		  firstItem.set("bookedBy", dataItem.ownerNames);
		  
	  }
	 function commentsEditor(container, options) 
		{
		 $('<textarea name="comments" data-text-field="comments" data-value-field="comments" data-bind="value:' + options.field + '" style="width: 148px; height: 46px;" placeholder=" "/>')
         .appendTo(container);
	         $('<span class="k-invalid-msg" data-for="comments"></span>').appendTo(container);  
		}
	 
	 function vendorCommentsRatingRequestEnd(e){
			if (typeof e.response != 'undefined')
			{
				if (e.type == "create") {
					 
					 $("#alertsBox").html("");
						$("#alertsBox").html("Vendor Comments created successfully");
						$("#alertsBox").dialog({
							modal : true,
							buttons : {
								"Close" : function() {
									$(this).dialog("close");
								}
							}
						});
						
						$('#gridComments_'+SelectedRowId).data().kendoGrid.dataSource.read();
				 }
				if (e.type == "update") {
					 
					 $("#alertsBox").html("");
						$("#alertsBox").html("Vendor Comments updated successfully");
						$("#alertsBox").dialog({
							modal : true,
							buttons : {
								"Close" : function() {
									$(this).dialog("close");
								}
							}
						});
						
						$('#gridComments_'+SelectedRowId).data().kendoGrid.dataSource.read();
				 }
				if (e.type == "destroy") {
					 
					 $("#alertsBox").html("");
						$("#alertsBox").html("Vendor Comments deleted successfully");
						$("#alertsBox").dialog({
							modal : true,
							buttons : {
								"Close" : function() {
									$(this).dialog("close");
								}
							}
						});
						
						$('#gridComments_'+SelectedRowId).data().kendoGrid.dataSource.read();
				 }
			}
		}
	</script>
	
	
<!-- -------------------------------------------------- Photo upload script ----------------------------------- -->
	<script>
	function oncomplete() {
		$("#myImage").attr(
				"src",
				"<c:url value='/person/getpersonimage/" + SelectedRowId
						+ "?timestamp=" + new Date().getTime() + "'/>");
		$("#myImages_"+SelectedRowId).attr(
				"src",
				"<c:url value='/person/getpersonimage/" + SelectedRowId
						+ "?timestamp=" + new Date().getTime() + "'/>");
	}
	
	function clickToUploadImage(){
		$('#uploadDialog').dialog({
			modal : true,
		});
		return false;
	}
	function onpersonImageUpload(e) {
		var files = e.files;
		// Check the extension of each file and abort the upload if it is not .jpg
		$.each(files, function() {
			if (this.extension === ".png") {
				e.data = {
						personId : SelectedRowId
					};
			}
			else if (this.extension === ".jpg") {
				
				e.data = {
						personId : SelectedRowId
					};
			}
			else if (this.extension === ".jpeg") {
				
				e.data = {
						personId : SelectedRowId
					};
			}
			else {
				alert("Only Images can be uploaded");
				e.preventDefault();
				return false;
			}
		});
    }
	function onImageSuccess(e)
	{
		alert("Uploaded Successfully");
		$(".k-upload-files.k-reset").find("li").remove();
		window.location.reload();
	}
	/* function filesSelectedtoUpload(e)
	  {
		  var files = e.files;
		  var requiredFormat = $("#docFormat_"+SelectedRowId).val();
		  requiredFormat = "."+documentFormatToUpload.toLowerCase();//requiredFormat.toLowerCase();
		 // alert(requiredFormat);
		 // alert(files.length);
		 if(requiredFormat == '')
			 {
				alert("Please select Document Type");
				return false;
			 }
			if( files.length  > 10 ) {
				alert("Maximum 10 files can be uploaded at a time.");
				e.preventDefault();
				return false;
			}
		 
			for(var fileCntr = 0; fileCntr < files.length; fileCntr ++) {
				if( files[fileCntr].size > 10485760 ) {
					alert("File size more than 10MB can not be uploaded.");
					e.preventDefault();
					return false;
				}
		 
				if( files[fileCntr].extension.toLowerCase() != requiredFormat ) {
					alert("Please Upload "+documentFormatToUpload+" Format");
					e.preventDefault();
					return false;
				}
			}

	  } */
	
	function uploadExtraDataAlongWithFile(e)
	{	
		e.data = { drId:drId};
    }
	</script>
	
	<!-- -------------------------------------------------- Common script ----------------------------------- -->
	<script>
	function statusEditor(container,options)
	 {
		 $('<input name="Status"  data-text-field="name"  data-value-field="value"  data-bind="value:' + options.field + '" required="true"/>')
	     .appendTo(container)
	         .kendoDropDownList({
	        	 optionLabel: "Select Status",  		
	         dataSource: {  
	             transport:{
	                 read: "${statusReadUrl}"
	             }
	         },
	         placeholder :"Select",
	     });
		 $('<span class="k-invalid-msg" data-for="Status"></span>').appendTo(container);

	 }
	
	</script>
	
	
	
<!-- -------------------------------------------- STYLE ---------------------------- -->

<style>
.k-edit-form-container {
	/* width: 550px; */
	text-align: center;
	position: relative;
	/*  height: 600px; */
	/* border: 1px solid black; */
	/*  overflow: hidden;  */
}

.wrappers {
	display: inline;
	float: left;
	width: 250px;
	padding-top: 10px;

	/* float:left;  */
	/* border: 1px solid red;
 */
	/* border: 1px solid green;
    overflow: hidden; */
}

  				.photo {
                    width: 140px;                    
                }
                .details {
                    width: 400px;
                }                
                .title {
                    display: block;
                    font-size: 1.6em; 
                }
                .description {
                    display: block;
                    padding-top: 1.6em;
                }
                .employeeID {
                    font-family: "Segoe UI", "Helvetica Neue", Arial, sans-serif;
                    font-size: 50px;
                    font-weight: bold;
                    color: #898989;
                }
                td.photo, .employeeID {
                    text-align: center;
                }
                td {
    				vertical-align: middle;
				}
                #employeesTable td {
                    background: -moz-linear-gradient(top,  rgba(0,0,0,0.05) 0%, rgba(0,0,0,0.15) 100%);
                    background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,rgba(0,0,0,0.05)), color-stop(100%,rgba(0,0,0,0.15)));
                    background: -webkit-linear-gradient(top,  rgba(0,0,0,0.05) 0%,rgba(0,0,0,0.15) 100%);
                    background: -o-linear-gradient(top,  rgba(0,0,0,0.05) 0%,rgba(0,0,0,0.15) 100%);
                    background: -ms-linear-gradient(top,  rgba(0,0,0,0.05) 0%,rgba(0,0,0,0.15) 100%);
                    background: linear-gradient(to bottom,  rgba(0,0,0,0.05) 0%,rgba(0,0,0,0.15) 100%);
                    padding: 20px;
                }
                
                .employeesDropDownWrap
                {
                    display:block;
                    margin:0 auto;
                }
                
                #emplooyeesDropDown-list .k-item {
                    overflow: hidden; 
                }
                
                #emplooyeesDropDown-list img {
                    -moz-box-shadow: 0 0 2px rgba(0,0,0,.4);
                    -webkit-box-shadow: 0 0 2px rgba(0,0,0,.4);
                    box-shadow: 0 0 2px rgba(0,0,0,.4);
                    float: left;
                    margin: 5px 20px 5px 0;
                }
                
                #emplooyeesDropDown-list h3 {
                    margin: 30px 0 10px 0;
                    font-size: 2em;
                }
                
                #emplooyeesDropDown-list p {
                    margin: 0;
                }
				 .k-upload-button input {
                z-index: inherit;
                }
                .k-edit-form-container {
    text-align: left;
}
</style>
<!-- </div> -->
