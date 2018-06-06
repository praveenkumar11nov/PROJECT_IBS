<%@include file="/common/taglibs.jsp"%>


<c:url value="/property/read_property" var="readUrl" />
<c:url value="/property/create_property" var="createUrl" />
<c:url value="/property/update_property" var="updateUrl" />
<c:url value="/property/delete_property" var="deleteUrl" />
<c:url value="/property/getProjectName" var="getProject" />

<c:url value="/property/getBlockNames" var="getBlocks" />



<c:url value="/comowner/getAllOwner" var="readAllOwners" />
<c:url value="/comowner/getAllFamily" var="readAllFamily" />
<c:url value="/comowner/getAllPets" var="readAllPets" />
<c:url value="/comowner/getAllTenant" var="readAllTenant" />
<c:url value="/comowner/getAllDocumentHelp" var="readAllDomesticHelp" />
<c:url value="/comowner/getAllVehicles" var="readVehiclesUrl" />
<c:url value="/property/readallpropertyNumber" var="readAllPropertyNumbers" />

<c:url value="/ownerDocument/read" var="propertyDocumentsReadUrl"/>

<c:url value="/documentdefiner/getAllDocument" var="getDocumentType" />
<c:url value="/ownerDocument/create" var="propertyDocumentsCreateUrl" />
<c:url value="/kycComplaints/upload/async/save" var="saveUrl" />
<c:url value="/ownerDocument/update" var="propertyDocumentsUpdateUrl" />
<c:url value="/property/propertyDocument/delete" var="propertyDocumentsDeleteUrl" />

<c:url value="/project/readallprojectnames" var="readAllProjectNames" />

<c:url value="/project/readallpropertyId" var="readAllPropertyId" />
<c:url value="/project/readallpropertyIdcreate" var="readAllPropertyIdcreated" />
<c:url value="/project/readallpropertyIdUpdate" var="readAllPropertyIdLast" />


<c:url value="/users/getPersonFullNamesList" var="personFullNameFilterUrl" />

<c:url value="/vehicledetails/getRegNameForFilter" var="RegNameFilterUrl" />
<c:url value="/vehicledetails/getSlotForFiltervehicle" var="SlotNameFilterUrl" />
<c:url value="/vehicledetails/getPropertyForFiltervehicle" var="PropertyFilterUrl" />
<c:url value="/vehicledetails/getVehicleMakeForFilter" var="VehicleMakeFilterUrl" />
<c:url value="/vehicledetails/getVehicleModeleForFilter" var="getVehicleModelForFilter" />
<c:url value="/vehicledetails/getTagNameForFilter" var="TagNameFilterUrl" />


<c:url value="/pets/filter" var="commonFilterForPetsUrl" />

<c:url value="/properties/upload" var="uploadDataProperty" />
<c:url value="/properties/getAllParkingSlots" var="readParkingSlotsUrl" />

<script type="text/javascript">
	var filterPropertyTypeData = [ "1BHK", "2BHK", "3BHK", "Villa",
			"Pent House" ];
	var filterMeasuermentTypeData = [ "Sq Mts", "Sq Yds", "Sq Ft" ];
	var filterPropertyStatus = [ "Sold", "Unsold" ];
	var filterPropertyBillable = [ "Yes", "No" ];

	var booleanData = [ {
		text : '1BHK',
		value : "1BHK"
	}, {
		text : '2BHK',
		value : "2BHK"
	}, {
		text : '3BHK',
		value : "3BHK"
	}, {
		text : 'Villa',
		value : "Villa"
	}, {
		text : 'Pent House',
		value : "Pent House"
	} ];
</script>

<kendo:grid-detailTemplate id="customEditor">
	<div class="k-edit-form-container">
		<div class="k-edit-label">
			<label for="projectId">Project Name</label>
		</div>
		<div data-container-for="projectId" class="k-edit-field">
			<kendo:dropDownList name="projectId" dataTextField="projectName"
				dataValueField="projectId" select="projectSelect"
				optionLabel="Select Project">
				<kendo:dataSource>
					<kendo:dataSource-transport>
						<kendo:dataSource-transport-read url="${getProject}" type="GET"
							contentType="application/json" />
						<kendo:dataSource-transport-parameterMap>
                           function(options){return JSON.stringify(options);}
                       </kendo:dataSource-transport-parameterMap>
					</kendo:dataSource-transport>
				</kendo:dataSource>
			</kendo:dropDownList>
		</div>

		<div class="k-edit-label">
			<label for="blocks">Tower/Block Name</label>
		</div>
		<div data-container-for="blocks" class="k-edit-field">
			<kendo:dropDownList name="blocks" dataTextField="blockName"
				dataValueField="blockId" select="blockSelect"
				optionLabel="Select Block" cascadeFrom="projectId">
				<kendo:dataSource>
					<kendo:dataSource-transport>
						<kendo:dataSource-transport-read url="${getBlocks}" type="GET"
							contentType="application/json" />
						<kendo:dataSource-transport-parameterMap>
                           function(options){return JSON.stringify(options);}
                       </kendo:dataSource-transport-parameterMap>
					</kendo:dataSource-transport>
				</kendo:dataSource>
			</kendo:dropDownList>
		</div>

		<!-- <div class="k-edit-label">
        <label for="propertyType">No of towers allowed</label>
      </div>
      
      <div data-container-for="propertyType" class="k-edit-field">
	     <input type="text" class="k-input k-textbox" readonly="readonly" id="propertiesAllowed" />
      </div> -->




		<div class="k-edit-label">
			<label for="propertyType">Property Type</label>
		</div>
		<div data-container-for="propertyType" class="k-edit-field">
			<kendo:dropDownList name="propertyType" dataTextField="text"
				dataValueField="value" optionLabel="Select PropertyType"
				autoBind="true">
				<kendo:dataSource data="${propertyType}">

				</kendo:dataSource>
			</kendo:dropDownList>
		</div>

		<div class="k-edit-label">
			<label for="areaType">Area&nbsp;Measurement</label>
		</div>
		<div data-container-for="areaType" class="k-edit-field">
			<kendo:dropDownList name="areaType" dataTextField="text"
				dataValueField="value" optionLabel="Select Measurement Type">
				<kendo:dataSource data="${propertyMeasurement}">

				</kendo:dataSource>
			</kendo:dropDownList>
		</div>

		<div class="k-edit-label">
			<label for="area">Area</label>
		</div>
		<div data-container-for="area" class="k-edit-field">
			<kendo:numericTextBox name="area" format="{0:n0}"></kendo:numericTextBox>
		</div>

		<div class="k-edit-label">
			<label for="property_No">Property No</label>
		</div>
		<div data-container-for="property_No" class="k-edit-field">
			<input type="text" class="k-input k-textbox" name="property_No"
				data-bind="value:property_No">
		</div>

		<div class="k-edit-label">
			<label for="property_Floor">Property Floor</label>
		</div>
		<div data-container-for="property_Floor" class="k-edit-field">
			<kendo:numericTextBox name="property_Floor" format="{0:n0}" min="0"></kendo:numericTextBox>
		</div>

		<div class="k-edit-label">
			<label for="status">Status</label>
		</div>
		<div data-container-for="status" class="k-edit-field">
			<kendo:dropDownList name="status" dataTextField="text"
				dataValueField="value" change="propertyStatusChange"
				optionLabel="Select Status">
				<kendo:dataSource data="${propertyStatus}"></kendo:dataSource>
			</kendo:dropDownList>
		</div>

		<div class="k-edit-label">
			<label for="tenancyHandoverDate">Possession Date</label>
		</div>
		<div data-container-for="tenancyHandoverDate" class="k-edit-field">
			<kendo:datePicker name="tenancyHandoverDate" format="{0:yyyy-MM-dd}"></kendo:datePicker>
		</div>

		<div class="k-edit-label">
			<label for="propertyBillable">Property Billable</label>
		</div>
		<div data-container-for="propertyBillable" class="k-edit-field">
			<kendo:dropDownList name="propertyBillable" dataTextField="text"
				dataValueField="value" optionLabel="Select Status">
				<kendo:dataSource data="${propertyBillable}"></kendo:dataSource>
			</kendo:dropDownList>
		</div>

		<div class="k-edit-label">
			<label for="no_of_ParkingSlots">No Of Parking Slots</label>
		</div>
		<div data-container-for="no_of_ParkingSlots" class="k-edit-field">
			<kendo:numericTextBox name="no_of_ParkingSlots" format="{0:n0}"
				min="1"></kendo:numericTextBox>
		</div>
	</div>
</kendo:grid-detailTemplate>

<kendo:grid name="grid" pageable="true"
	detailTemplate="propertyTemplate" edit="propertyEvent"
	selectable="true" change="onChangeProperty" groupable="true"
	sortable="true" filterable="true">
	<kendo:grid-editable mode="popup"
		confirmation="Are you sure to delete that record???" >
		<kendo:grid-editable-template>$('#customEditor').html()</kendo:grid-editable-template>
	</kendo:grid-editable>
	
 	<kendo:grid-toolbar>
		<kendo:grid-toolbarItem name="create" text="Add Property" />
		<kendo:grid-toolbarItem
			template="<a class='k-button' href='\\#' onclick=clearFilter()><span class='k-icon k-i-funnel-clear'></span>Clear Filter</a>" />
		 <kendo:grid-toolbarItem template="<input id='undo' class='k-button' style='width:85px;height:25px;' placeholder='Upload Property' />" />
	</kendo:grid-toolbar> 
	<kendo:grid-filterable extra="false">	
		<kendo:grid-filterable-operators>
			<kendo:grid-filterable-operators-string eq="Is equal to" />
			<kendo:grid-filterable-operators-number eq="Is equal to"
				gt="Is greather than" gte="IS greather than and equal to"
				lt="Is less than" lte="Is less than and equal to"
				neq="Is not equal to" />
		</kendo:grid-filterable-operators>
	</kendo:grid-filterable> 

	<kendo:grid-columns>
		<kendo:grid-column title="&nbsp;" width="200px">
			<kendo:grid-column-command>
				<kendo:grid-column-commandItem name="edit" click="edit" />
				<kendo:grid-column-commandItem name="destroy" />
			</kendo:grid-column-command>
		</kendo:grid-column>
		
		 <kendo:grid-column title="Property Id" field="propertyId" format="" width="130px" filterable="false">
		 
		 
		 </kendo:grid-column>
		<kendo:grid-column title="Project Name" field="projectId"
			editor="projectEditor" hidden="true" filterable="false" width="140px" />
		<kendo:grid-column title="Project Name" field="projectName"
			filterable="true" width="140px">
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function projectNameFilter(element) {
							element.kendoAutoComplete({
								placeholder : "Enter Project Name",
								dataSource : {
									transport : {
										read : "${readAllProjectNames}"
									}
								}
							});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>

		<kendo:grid-column title="Property No." field="property_No"
			width="160px">
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function projectNameFilter(element) {
							element.kendoAutoComplete({
								placeholder : "Enter Property Number",
								dataSource : {
									transport : {
										read : "${readAllPropertyNumbers}"
									}
								}
							});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>

		<kendo:grid-column title="Property Type" field="propertyType"
			editor="PropertyType" width="160px">
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function projectNameFilter(element) {
							element.kendoAutoComplete({
								placeholder : "Enter Property Type",
								dataSource : filterPropertyTypeData
							});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>

		<kendo:grid-column title="No Of Parking Slots"
			field="no_of_ParkingSlots" format="{0:n0}" width="200px" />

		<kendo:grid-column title="Possession Date" field="tenancyHandoverDate"
			format="{0:yyyy-MM-dd}" width="230px" />

		<kendo:grid-column title="Area" field="area" format="{0:n0}"
			width="160px" />

		<kendo:grid-column title="Tower No." field="towerNo"  format="{0:n0}"  width="130px" hidden="true" />
		<kendo:grid-column title="Tower Name" field="towerName" template="#=TowerName(data)#"  width="150px" />
		<kendo:grid-column title="Tower/Block Name" editor="towerEditor"
			field="blocks" hidden="true" width="150px" filterable="false" />
		<kendo:grid-column title="Tower/Block Name" field="blockName"
			width="150px" filterable="true">
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function projectNameFilter(element) {
							element.kendoAutoComplete({
								placeholder : "Enter Block Name",
								dataTextField : "blockName",
								dataValueField : "bockId",
								dataSource : {
									transport : {
										read : "${getBlocks}"
									}
								}
							});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>

		<kendo:grid-column title="Property Floor" field="property_Floor"
			format="{0:n0}" width="170px" />

		<kendo:grid-column title="Built Area Measurement"
			editor="measurmentEditor" field="areaType" width="150px">
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function projectNameFilter(element) {
							element.kendoAutoComplete({
								placeholder : "Enter Measurement Type",
								dataSource : filterMeasuermentTypeData
							});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>


		<kendo:grid-column title="Property Name" field="propertyName"  width="170px"/>

		<kendo:grid-column title="Status" field="status"
			editor="PropertyStatus" width="110px">
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function projectNameFilter(element) {
							element.kendoAutoComplete({
								placeholder : "Enter Property Status",
								dataSource : filterPropertyStatus
							});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>

		<kendo:grid-column title="Property Billable" field="propertyBillable"
			editor="PropertyBillable" width="175px">
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function propertyBillableFilter(element) {
							element.kendoAutoComplete({
								placeholder : "Enter Billable Type",
								dataSource : filterPropertyBillable
							});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>


		 <kendo:grid-column title="Created By" field="createdBy" format="" width="140px" filterable="false">
		 <%--  <kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function projectNameFilter(element) {
							element.kendoAutoComplete({
								placeholder : "Enter Created By",
								dataSource : {
									transport : {
										read : "${readAllPropertyIdCreated}"
									}
								}
							});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable> --%>
		 </kendo:grid-column>
		
            <kendo:grid-column title="Last Updated By" field="lastUpdatedBy" format="" width="180px">
             <kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function projectNameFilter(element) {
							element.kendoAutoComplete({
								placeholder : "Enter Last Updated By",
								dataSource : {
									transport : {
										read : "${readAllPropertyIdLast}"
									}
								}
							});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		 </kendo:grid-column>
            
            
        <kendo:grid-column title="&nbsp;" width="220px">
			<kendo:grid-column-command>
				<kendo:grid-column-commandItem name="Upload Doc" click="uploadDocs"
					className="k-grid-upload" />
				<kendo:grid-column-commandItem name="Download Doc"
					click="downloadDocs" className="k-grid-download" />
			</kendo:grid-column-command>
		</kendo:grid-column>
		<kendo:grid-column title="&nbsp;" width="200px">
			<kendo:grid-column-command>
				<kendo:grid-column-commandItem name="edit" click="edit" />
				<kendo:grid-column-commandItem name="destroy" />
			</kendo:grid-column-command>
		</kendo:grid-column>
	</kendo:grid-columns>
	<kendo:dataSource pageSize="10" requestEnd="onRequestEnd" requestStart="onRequestStart">
		<kendo:dataSource-transport>
			<kendo:dataSource-transport-read url="${readUrl}" dataType="json"
				type="GET" contentType="application/json" />
			<kendo:dataSource-transport-create url="${createUrl}" dataType="json"
				type="POST" contentType="application/json" />
			<kendo:dataSource-transport-update url="${updateUrl}" dataType="json"
				type="POST" contentType="application/json" />
			<kendo:dataSource-transport-destroy url="${deleteUrl}"
				dataType="json" type="POST" contentType="application/json" />

			<kendo:dataSource-transport-parameterMap>
				<script>
					function parameterMap(options, type) {
						return JSON.stringify(options);
					}
				</script>
			</kendo:dataSource-transport-parameterMap>
		</kendo:dataSource-transport>
		<kendo:dataSource-schema parse="propertyParse">
			<kendo:dataSource-schema-model id="propertyId">
				<kendo:dataSource-schema-model-fields>
					<kendo:dataSource-schema-model-field name="propertyId"  >
						</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="projectId" type="number">
						<kendo:dataSource-schema-model-field-validation />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="towerNo"  type="number">
                        	<kendo:dataSource-schema-model-field-validation required="true" min="1" />
                        </kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="blocks" type="number">
						<kendo:dataSource-schema-model-field-validation />
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="propertyType"
						type="string">
						<kendo:dataSource-schema-model-field-validation />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="areaType" type="string">
						<kendo:dataSource-schema-model-field-validation required="true" />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="area" type="number"
						defaultValue=" ">
						<kendo:dataSource-schema-model-field-validation min="1" />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="property_No"
						type="string">
						<kendo:dataSource-schema-model-field-validation min="1" />
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="propertyName" type="string">
                        	<kendo:dataSource-schema-model-field-validation required="true"  />
                        </kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="property_Floor"
						type="number">
						<kendo:dataSource-schema-model-field-validation min="1" />
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="status" type="string">
						<kendo:dataSource-schema-model-field-validation required="true" />
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="tenancyHandoverDate"
						type="date" defaultValue="">
						<kendo:dataSource-schema-model-field-validation />
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="propertyBillable"
						type="string">
						<kendo:dataSource-schema-model-field-validation required="true" />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="no_of_ParkingSlots"
						type="number">
						<kendo:dataSource-schema-model-field-validation min="1" />
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="drGroupId" type="number"/>
				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>
	</kendo:dataSource>
</kendo:grid>
<div id="alertsBox" title="Alert"></div>
<kendo:grid-detailTemplate id="propertyTemplate">
	<kendo:tabStrip name="tabStrip_#=propertyId#">
		<kendo:tabStrip-items>
			<kendo:tabStrip-item text="Owners" selected="true">
				<kendo:tabStrip-item-content>
					<div id="sample">
						<kendo:grid name="Owners_#=propertyId#" pageable="true"
							resizable="true" sortable="true" filterable="true"
							selectable="true" scrollable="true">
							<kendo:grid-scrollable />
							<kendo:grid-filterable extra="false">
								<kendo:grid-filterable-operators>
									<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains" />
									<kendo:grid-filterable-operators-number eq="Is equal to"
										gt="Is greather than" gte="IS greather than and equal to"
										lt="Is less than" lte="Is less than and equal to"
										neq="Is not equal to" />
								</kendo:grid-filterable-operators>
							</kendo:grid-filterable>
							<kendo:grid-columns>
								<kendo:grid-column title="Person Name" field="personName"
									width="120px" >

									<kendo:grid-column-filterable>
										<kendo:grid-column-filterable-ui>
											<script type="text/javascript">
												function personNameFilter(
														element) {
													element
															.kendoAutoComplete({
																placeholder : "Enter first name",
																dataSource : {
																	transport : {
																		read : "${personFullNameFilterUrl}/Owner"
																	}
																}
															});
												}
											</script>
										</kendo:grid-column-filterable-ui>
									</kendo:grid-column-filterable>
								</kendo:grid-column>
								<kendo:grid-column title="Age" field="age" width="70px">
								
							</kendo:grid-column>
								<kendo:grid-column title="Blood Group" field="bloodGroup"
									width="80px">
									<kendo:grid-column-values value="${bloodGroup}"/>
									
									</kendo:grid-column>
								<kendo:grid-column title="Gender" field="sex" width="70px">
								
								
										<kendo:grid-column-values value="${sex}"/>
								</kendo:grid-column>
								<kendo:grid-column title="Resident" field="Resident"
									width="70px">


									<kendo:grid-column-filterable>
										<kendo:grid-column-filterable-ui>
											<script>
												function propertyBillableFilter(
														element) {
													element
															.kendoAutoComplete({
																placeholder : "Enter Resident Type",
																dataSource : filterPropertyBillable
															});
												}
											</script>
										</kendo:grid-column-filterable-ui>
									</kendo:grid-column-filterable>
								</kendo:grid-column>
								<kendo:grid-column title="Contact Details"
									field="contactContent" width="130px"></kendo:grid-column>
								<kendo:grid-column title="Status" field="status" width="90px">
								 <kendo:grid-column-values value="${status}"/>
								</kendo:grid-column>
								<kendo:grid-column title="Person Name" field="personId"
									width="200px" hidden="true" />
								<kendo:grid-column title="&nbsp;" width="140px">
									<kendo:grid-column-command>
										<kendo:grid-column-commandItem name="View Family"
											click="OwnerInfo" />
									</kendo:grid-column-command>
								</kendo:grid-column>
							</kendo:grid-columns>

							<kendo:dataSource pageSize="20" requestStart="onRequestStart1">
								<kendo:dataSource-transport>
									<kendo:dataSource-transport-read
										url="${readAllOwners}/Owner/#=propertyId#" dataType="json"
										type="POST" contentType="application/json" />
									<kendo:dataSource-transport-parameterMap>
										<script>
											function parameterMap(options, type) {
												return JSON.stringify(options);
											}
										</script>
									</kendo:dataSource-transport-parameterMap>
								</kendo:dataSource-transport>
								<kendo:dataSource-schema>
									<kendo:dataSource-schema-model id="personId">
										<kendo:dataSource-schema-model-fields>
											<kendo:dataSource-schema-model-field name="personName"
												type="string">
												<kendo:dataSource-schema-model-field-validation
													required="true" />
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="age" type="number"/>
											<kendo:dataSource-schema-model-field name="unitPrice" type="number">
					                        	<kendo:dataSource-schema-model-field-validation required="true" min="1" />
					                        </kendo:dataSource-schema-model-field>
					                        <kendo:dataSource-schema-model-field name="unitsInStock" type="number">
					                        	<kendo:dataSource-schema-model-field-validation required="true" min="0" />
					                        </kendo:dataSource-schema-model-field>
					                        <kendo:dataSource-schema-model-field name="discontinued" type="boolean" />
										</kendo:dataSource-schema-model-fields>
									</kendo:dataSource-schema-model>
								</kendo:dataSource-schema>
							</kendo:dataSource>
							<kendo:grid-pageable input="true" numeric="false" />
						</kendo:grid>
					</div>
				</kendo:tabStrip-item-content>
			</kendo:tabStrip-item>


			<kendo:tabStrip-item text="Tenants">
				<kendo:tabStrip-item-content>
					<div id="sample">
						<kendo:grid name="Tenants_#=propertyId#" pageable="true"
							resizable="true" sortable="true" filterable="true"
							selectable="true">
							<kendo:grid-scrollable />
							<kendo:grid-filterable extra="false">
								<kendo:grid-filterable-operators>
									<kendo:grid-filterable-operators-string eq="Is equal to" />
									<kendo:grid-filterable-operators-number eq="Is equal to"
										gt="Is greather than" gte="IS greather than and equal to"
										lt="Is less than" lte="Is less than and equal to"
										neq="Is not equal to" />
								</kendo:grid-filterable-operators>
							</kendo:grid-filterable>
							<kendo:grid-columns>
								<kendo:grid-column title="Person Name" field="personName"
									width="140px" >
									<kendo:grid-column-filterable>
										<kendo:grid-column-filterable-ui>
											<script type="text/javascript">
												function personNameFilter(
														element) {
													element
															.kendoAutoComplete({
																placeholder : "Enter first name",
																dataSource : {
																	transport : {
																		read : "${personFullNameFilterUrl}/Tenant"
																	}
																}
															});
												}
											</script>
										</kendo:grid-column-filterable-ui>
									</kendo:grid-column-filterable>


								</kendo:grid-column>
								<kendo:grid-column title="Age" field="age" width="60px"></kendo:grid-column>
								<kendo:grid-column title="Blood Group" field="bloodGroup"
									width="95px">
										<kendo:grid-column-values value="${bloodGroup}"/>
									</kendo:grid-column>
								<kendo:grid-column title="Gender" field="sex" width="80px">
										<kendo:grid-column-values value="${sex}"/>
								</kendo:grid-column>
								<kendo:grid-column title="Contact Details"
									field="contactContent" width="160px" filterable="false">
									
									
									</kendo:grid-column>
								<kendo:grid-column title="Status" field="status" width="100px">
								 <kendo:grid-column-values value="${status}"/>
								
								</kendo:grid-column>
								<kendo:grid-column title="Person Name" field="personId"
									width="200px" hidden="true" />

								<kendo:grid-column title="&nbsp;" width="140px">
									<kendo:grid-column-command>
										<kendo:grid-column-commandItem name="View Family"
											click="TenantInfo" />
									</kendo:grid-column-command>
								</kendo:grid-column>
							</kendo:grid-columns>
							<kendo:dataSource pageSize="20" requestStart="onRequestStart2">
								<kendo:dataSource-transport>
									<kendo:dataSource-transport-read
										url="${readAllTenant}/Tenant/#=propertyId#" dataType="json"
										type="POST" contentType="application/json" />
									<kendo:dataSource-transport-parameterMap>
										<script>
											function parameterMap(options, type) {
												return JSON.stringify(options);
											}
										</script>
									</kendo:dataSource-transport-parameterMap>
								</kendo:dataSource-transport>
								<kendo:dataSource-schema>
									<kendo:dataSource-schema-model id="personId">
										<kendo:dataSource-schema-model-fields>
											<kendo:dataSource-schema-model-field name="personName"
												type="string">
												<kendo:dataSource-schema-model-field-validation
													required="true" />
											</kendo:dataSource-schema-model-field>
												<kendo:dataSource-schema-model-field name="age"
												type="number"/>
										</kendo:dataSource-schema-model-fields>
									</kendo:dataSource-schema-model>
								</kendo:dataSource-schema>
							</kendo:dataSource>
							<kendo:grid-pageable input="true" numeric="false" />
						</kendo:grid>
					</div>
				</kendo:tabStrip-item-content>
			</kendo:tabStrip-item>

			<kendo:tabStrip-item text="Domestic Help">
				<kendo:tabStrip-item-content>
					<div id="sample">
						<kendo:grid name="DomesticHelp_#=propertyId#" pageable="true"
							resizable="true" sortable="true" filterable="true"
							selectable="true">
							<kendo:grid-scrollable />
							<kendo:grid-filterable extra="false">
								<kendo:grid-filterable-operators>
									<kendo:grid-filterable-operators-string eq="Is equal to" />
									<kendo:grid-filterable-operators-number eq="Is equal to"
										gt="Is greather than" gte="IS greather than and equal to"
										lt="Is less than" lte="Is less than and equal to"
										neq="Is not equal to" />
								</kendo:grid-filterable-operators>
							</kendo:grid-filterable>
							<kendo:grid-columns>
								<kendo:grid-column title="Person Name" field="personName"
									width="160px" >

									<kendo:grid-column-filterable>
										<kendo:grid-column-filterable-ui>
											<script type="text/javascript">
												function personNameFilter(
														element) {
													element
															.kendoAutoComplete({
																placeholder : "Enter first name",
																dataSource : {
																	transport : {
																		read : "${personFullNameFilterUrl}/DomesticHelp"
																	}
																}
															});
												}
											</script>
										</kendo:grid-column-filterable-ui>
									</kendo:grid-column-filterable>
								</kendo:grid-column>
								<kendo:grid-column title="Age" field="age" width="70px"></kendo:grid-column>
								<kendo:grid-column title="Blood Group" field="bloodGroup"
									width="85px">
									
										<kendo:grid-column-values value="${bloodGroup}"/>
									</kendo:grid-column>
								<kendo:grid-column title="Gender" field="sex" width="70px">
								
								
										<kendo:grid-column-values value="${sex}"/>
								</kendo:grid-column>
								<kendo:grid-column title="Work Nature" field="workNature"
									width="100px" filterable="false"></kendo:grid-column>
								<kendo:grid-column title="Contact Details"
									field="contactContent" width="160px"></kendo:grid-column>
								<kendo:grid-column title="Status" field="status" width="100px"> <kendo:grid-column-values value="${status}"/></kendo:grid-column>
								<kendo:grid-column title="Person Name" field="personId"
									width="200px" hidden="true" />
								<kendo:grid-column title="&nbsp;" width="140px">
									<kendo:grid-column-command>
										<kendo:grid-column-commandItem name="View Family"
											click="DomesticInfo" />
									</kendo:grid-column-command>
								</kendo:grid-column>
							</kendo:grid-columns>
							<kendo:dataSource pageSize="20" requestStart="onRequestStart3">
								<kendo:dataSource-transport>
									<kendo:dataSource-transport-read
										url="${readAllDomesticHelp}/DomesticHelp/#=propertyId#"
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
									<kendo:dataSource-schema-model id="personId">
										<kendo:dataSource-schema-model-fields>
											<kendo:dataSource-schema-model-field name="personName"
												type="string">
												
												<kendo:dataSource-schema-model-field-validation
													required="true" />
											</kendo:dataSource-schema-model-field>
											<kendo:dataSource-schema-model-field name="age"
												type="number"/>
										</kendo:dataSource-schema-model-fields>
									</kendo:dataSource-schema-model>
								</kendo:dataSource-schema>
							</kendo:dataSource>
							<kendo:grid-pageable input="true" numeric="false" />
						</kendo:grid>
					</div>
				</kendo:tabStrip-item-content>
			</kendo:tabStrip-item>

			<kendo:tabStrip-item text="Vehicles">
				<kendo:tabStrip-item-content>
					<div id="sample">
						<kendo:grid name="Vehicles_#=propertyId#" pageable="true"
							sortable="true" resizable="true" groupable="true"
							filterable="true" scrollable="true">
							<kendo:grid-pageable pageSizes="true" buttonCount="5"
								pageSize="10" input="true" numeric="true"></kendo:grid-pageable>
							<kendo:grid-filterable extra="false">
								<kendo:grid-filterable-operators>
									<kendo:grid-filterable-operators-string eq="Is equal to" />
									<kendo:grid-filterable-operators-date lte="Registered Before"
										gte="Registered After" />
								</kendo:grid-filterable-operators>
							</kendo:grid-filterable>
							<kendo:grid-columns>
								<kendo:grid-column title="Registration No"
									field="vhRegistrationNo" width="130px">

									<kendo:grid-column-filterable>
										<kendo:grid-column-filterable-ui>
											<script>
												function psSlotNoFilter(element) {
													element
															.kendoAutoComplete({
																dataSource : {
																	transport : {
																		read : "${RegNameFilterUrl}",
																	}
																},
															});
												}
											</script>
										</kendo:grid-column-filterable-ui>
									</kendo:grid-column-filterable>

								</kendo:grid-column>
								<kendo:grid-column title="Owner/Tenant" field="owner"
									width="130px">


									<kendo:grid-column-filterable>
										<kendo:grid-column-filterable-ui>
											<script>
												function psSlotNoFilter(element) {
													element
															.kendoAutoComplete({
																dataSource : {
																	transport : {
																		read : "${PropertyFilterUrl}",
																	}
																},
															});
												}
											</script>
										</kendo:grid-column-filterable-ui>
									</kendo:grid-column-filterable>

								</kendo:grid-column>
								<kendo:grid-column title="Slot Type" field="slotType"
									width="100px" filterable="false"/>
								<kendo:grid-column title="Slot No" field="validSlotsNo"
									width="150px">


									<kendo:grid-column-filterable>
										<kendo:grid-column-filterable-ui>
											<script>
												function psSlotNoFilter(element) {
													element
															.kendoAutoComplete({
																dataSource : {
																	transport : {
																		read : "${SlotNameFilterUrl}",
																	}
																},
															});
												}
											</script>
										</kendo:grid-column-filterable-ui>
									</kendo:grid-column-filterable>

								</kendo:grid-column>
								<kendo:grid-column title="Vehicle Make" field="vhMake"
									width="130px">

									<kendo:grid-column-filterable>
										<kendo:grid-column-filterable-ui>
											<script>
												function psSlotNoFilter(element) {
													element
															.kendoAutoComplete({
																dataSource : {
																	transport : {
																		read : "${VehicleMakeFilterUrl}",
																	}
																},
															});
												}
											</script>
										</kendo:grid-column-filterable-ui>
									</kendo:grid-column-filterable>
								</kendo:grid-column>
								<kendo:grid-column title="Vehicle Tag No" field="vhTagNo"
									width="130px">




									<kendo:grid-column-filterable>
										<kendo:grid-column-filterable-ui>
											<script>
												function psSlotNoFilter(element) {
													element
															.kendoAutoComplete({
																dataSource : {
																	transport : {
																		read : "${TagNameFilterUrl}",
																	}
																},
															});
												}
											</script>
										</kendo:grid-column-filterable-ui>
									</kendo:grid-column-filterable>


								</kendo:grid-column>
								<kendo:grid-column title="Contact Number" field="contact"
									width="130px" filterable="false"></kendo:grid-column>
							</kendo:grid-columns>
							<kendo:dataSource pageSize="20" requestEnd="onRequestEnd" requestStart="onRequestStart4">
								<kendo:dataSource-transport>
									<kendo:dataSource-transport-read
										url="${readVehiclesUrl}/#=propertyId#" dataType="json"
										type="POST" contentType="application/json" />
									<kendo:dataSource-transport-parameterMap>
										<script>
											function parameterMap(options, type) {
												return JSON.stringify(options);
											}
										</script>
									</kendo:dataSource-transport-parameterMap>
								</kendo:dataSource-transport>

								<kendo:dataSource-schema>
									<kendo:dataSource-schema-model id="vhId">
										<kendo:dataSource-schema-model-fields>
											<kendo:dataSource-schema-model-field name="owner" />
											<kendo:dataSource-schema-model-field name="Idowner" />
											<kendo:dataSource-schema-model-field name="property" />
											<kendo:dataSource-schema-model-field name="propertyId" />
											<kendo:dataSource-schema-model-field name="vhRegistrationNo"
												type="string" />
											<kendo:dataSource-schema-model-field name="vhMake" />
											<kendo:dataSource-schema-model-field name="vhModel" />
											<kendo:dataSource-schema-model-field name="vhMakeOther" />
											<kendo:dataSource-schema-model-field name="vhModelOther" />
											<kendo:dataSource-schema-model-field name="vhTagNo"
												type="string" />
										</kendo:dataSource-schema-model-fields>
									</kendo:dataSource-schema-model>
								</kendo:dataSource-schema>
							</kendo:dataSource>
						</kendo:grid>
					</div>
				</kendo:tabStrip-item-content>
			</kendo:tabStrip-item>

			<kendo:tabStrip-item text="Pets">
				<kendo:tabStrip-item-content>
					<div id="sample">
						<kendo:grid name="Pets_#=propertyId#" pageable="true"
							sortable="true" filterable="true" selectable="true">
							<kendo:grid-scrollable />
							<kendo:grid-filterable extra="false">
								<kendo:grid-filterable-operators>
									<kendo:grid-filterable-operators-string eq="Is equal to" />
									<kendo:grid-filterable-operators-number eq="Is equal to"
										gt="Is greather than" gte="IS greather than and equal to"
										lt="Is less than" lte="Is less than and equal to"
										neq="Is not equal to" />
								</kendo:grid-filterable-operators>
							</kendo:grid-filterable>
							<kendo:grid-columns>
								<kendo:grid-column title="Pet Name" field="petName">
									<kendo:grid-column-filterable>
										<kendo:grid-column-filterable-ui>
											<script>
												function petNameFilter(element) {
													element
															.kendoAutoComplete({
																placeholder : "Enter Pet Name",
																dataSource : {
																	transport : {
																		read : "${commonFilterForPetsUrl}/petName"
																	}
																}
															});
												}
											</script>
										</kendo:grid-column-filterable-ui>
									</kendo:grid-column-filterable>
								</kendo:grid-column>
								<kendo:grid-column title="Breed" field="breedName">

									<kendo:grid-column-filterable>
										<kendo:grid-column-filterable-ui>
											<script>
												function petBreedFilter(element) {
													element
															.kendoAutoComplete({
																placeholder : "Enter Pet Breed",
																dataSource : {
																	transport : {
																		read : "${commonFilterForPetsUrl}/breedName"
																	}
																}
															});
												}
											</script>
										</kendo:grid-column-filterable-ui>
									</kendo:grid-column-filterable>
								</kendo:grid-column>
								<kendo:grid-column title="Size" field="petSize">

									<kendo:grid-column-filterable>
										<kendo:grid-column-filterable-ui>
											<script>
												function petSizeFilter(element) {
													element
															.kendoAutoComplete({
																placeholder : "Enter Pet Size",
																dataSource : {
																	transport : {
																		read : "${commonFilterForPetsUrl}/petSize"
																	}
																}
															});
												}
											</script>
										</kendo:grid-column-filterable-ui>
									</kendo:grid-column-filterable>
								</kendo:grid-column>
								<kendo:grid-column title="Color" field="petColor" >
									<kendo:grid-column-filterable>
										<kendo:grid-column-filterable-ui>
											<script>
												function petColorFilter(element) {
													element
															.kendoAutoComplete({
																placeholder : "Enter Pet Color",
																dataSource : {
																	transport : {
																		read : "${commonFilterForPetsUrl}/petColor"
																	}
																}
															});
												}
											</script>
										</kendo:grid-column-filterable-ui>
									</kendo:grid-column-filterable>
								</kendo:grid-column>
								<kendo:grid-column title="Sex" field="petSex" >
									<kendo:grid-column-filterable>
										<kendo:grid-column-filterable-ui>
											<script>
												function petSexFilter(element) {
													element
															.kendoAutoComplete({
																placeholder : "Enter Pet Sex",
																dataSource : {
																	transport : {
																		read : "${commonFilterForPetsUrl}/petSex"
																	}
																}
															});
												}
											</script>
										</kendo:grid-column-filterable-ui>
									</kendo:grid-column-filterable>
								</kendo:grid-column>
								<kendo:grid-column title="Age" field="petAge">
								
								
								</kendo:grid-column>
								<kendo:grid-column title="Emergency Contact Number"
									field="emergencyContact" filterable="false" />
								<kendo:grid-column title="Status" field="petStatus"
									filterable="false" />
							</kendo:grid-columns>
							<kendo:dataSource pageSize="20" requestStart="onRequestStart5">
								<kendo:dataSource-transport>
									<kendo:dataSource-transport-read
										url="${readAllPets}/Pets/#=propertyId#" dataType="json"
										type="POST" contentType="application/json" />
									<kendo:dataSource-transport-parameterMap>
										<script>
											function parameterMap(options, type) {
												return JSON.stringify(options);
											}
										</script>
									</kendo:dataSource-transport-parameterMap>
								</kendo:dataSource-transport>
								<kendo:dataSource-schema>
									<kendo:dataSource-schema-model id="petId">
										<kendo:dataSource-schema-model-fields>
											<kendo:dataSource-schema-model-field name="petName"
												type="string">
												<kendo:dataSource-schema-model-field-validation
													required="true" />
											</kendo:dataSource-schema-model-field>
											<kendo:dataSource-schema-model-field name="petAge"
												type="number"/>
										</kendo:dataSource-schema-model-fields>
									</kendo:dataSource-schema-model>
								</kendo:dataSource-schema>
							</kendo:dataSource>
							<kendo:grid-pageable input="true" numeric="false" />
						</kendo:grid>
					</div>
				</kendo:tabStrip-item-content>
			</kendo:tabStrip-item>
			
			<kendo:tabStrip-item text="Documents">
			  <kendo:tabStrip-item-content>
			    <div id="sample">
			     <br/>
                  <a class="k-button k-button-icontext" id="approveDocuments_#=propertyId#" onclick="approveDocuments()"><span class="k-icon k-add"></span>Approve Document</a>
				 <br/><br/>
				 <kendo:grid name="documentsUpload_#=propertyId#" edit="documentEvent" dataBound="UploadFileBound" change="documentChangeEvent" remove="removeDocument" pageable="true" sortable="true" filterable="true" selectable="true">
				   <kendo:grid-pageable pageSize="10"></kendo:grid-pageable>
					 <kendo:grid-editable mode="popup"  confirmation="Are sure you want to delete this item ?"/>
					   <kendo:grid-toolbar>
						 <kendo:grid-toolbarItem name="create" text="Add Document" />
					   </kendo:grid-toolbar> 
        			   <kendo:grid-columns>
        				 <kendo:grid-column title="&nbsp;" width="250px" >
						   </kendo:grid-column>
						     <kendo:grid-column title="Document Name"  field="documentName" editor="documentNameEditor" width="100px" filterable="false"></kendo:grid-column>
							 <kendo:grid-column title="Document Format" field="documentFormat" editor="documentFormatEditor" width="100px" filterable="false"></kendo:grid-column>
							 <kendo:grid-column title="Document Number" field="documentNumber" width="100px" filterable="false"></kendo:grid-column>
							 <kendo:grid-column title="Document Description" field="documentDescription" editor="documentDecriptionEditor" width="100px" filterable="false"></kendo:grid-column>
							
        					 <kendo:grid-column title="&nbsp;" width="175px" >
							   <kendo:grid-column-command>
							     <kendo:grid-column-commandItem name="edit"/>
							       <kendo:grid-column-commandItem name="View" click="downloadFile"/>
							         <kendo:grid-column-commandItem name="destroy" />
							   </kendo:grid-column-command>
							 </kendo:grid-column>
							 
        					</kendo:grid-columns>
        					 <kendo:dataSource requestEnd="DocumentOnRequestEnd" requestStart="onRequestStart6">
						            <kendo:dataSource-transport>
						                <kendo:dataSource-transport-read url="${propertyDocumentsReadUrl}/#=drGroupId#/Property" dataType="json" type="POST" contentType="application/json"/>
						                <kendo:dataSource-transport-create url="${propertyDocumentsCreateUrl}/#=drGroupId#/Property" dataType="json" type="POST" contentType="application/json" />
						                <kendo:dataSource-transport-update url="${propertyDocumentsUpdateUrl}/#=drGroupId#/Property" dataType="json" type="POST" contentType="application/json" />
						                <kendo:dataSource-transport-destroy url="${propertyDocumentsDeleteUrl}" dataType="json" type="POST" contentType="application/json" />						                
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
			
			<kendo:tabStrip-item text="Parking Slots">
				<kendo:tabStrip-item-content>
					<div id="sample">
						<kendo:grid name="parkingSlots_#=propertyId#" pageable="true"
							sortable="true" resizable="true" groupable="true"
							filterable="true" scrollable="true">
							<kendo:grid-pageable pageSizes="true" buttonCount="5"
								pageSize="10" input="true" numeric="true"></kendo:grid-pageable>
						
							<kendo:grid-columns>
								<kendo:grid-column title="Slot No"
									field="slotNumber" width="80px" filterable="false"/>
						
								<kendo:grid-column title="OwnerShip" field="ownerShipType"
									width="85px" filterable="false"/>

								<kendo:grid-column title="Parking Method" field="parkingMethod"
									width="100px" filterable="false"/>
								<kendo:grid-column title="Active Date" field="psActiveDate" format="{0:dd/MM/yyyy hh:mm tt}" editor="dateEditor" 
									width="100px" filterable="false"/>

							</kendo:grid-columns>
							<kendo:dataSource pageSize="20">
								<kendo:dataSource-transport>
									<kendo:dataSource-transport-read
										url="${readParkingSlotsUrl}/#=propertyId#" dataType="json"
										type="POST" contentType="application/json" />
										<kendo:dataSource-transport-parameterMap>
							            	<script>
							             		function parameterMap(options) { 
							            			return JSON.stringify(options);
							             		}
							            	</script>
		           						 </kendo:dataSource-transport-parameterMap>
								
								</kendo:dataSource-transport>

								<kendo:dataSource-schema>
									<kendo:dataSource-schema-model>
										<kendo:dataSource-schema-model-fields>
											<kendo:dataSource-schema-model-field name="slotNumber" />
											<kendo:dataSource-schema-model-field name="ownerShipType" />
											<kendo:dataSource-schema-model-field name="parkingMethod" />
											<kendo:dataSource-schema-model-field name="psActiveDate" type="date"/>
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
<div id="dialogBoxforFamilyMembers" title="Associated Family Members">
</div>

 <div id="uploadDialog1" title="Upload Property Document"
	style="display: none;">
	<kendo:upload name="files" multiple="false" upload="uploadExtraData"
		success="onDocSuccess">
		<kendo:upload-async autoUpload="false" saveUrl="./property/upload" />
	</kendo:upload>
</div> 
 
<div id="uploadDialog" title="Upload Document" hidden="true"></div>

<div id="fileupload" title="Upload Excel" hidden="true">
	<kendo:upload  name="files"  id="files1" select="onSelectupload" multiple="false" success="onExcelDocSuccess" error="errorRegardingUploadExcel" template="<span class='k-progress'></span><div class='file-wrapper'> <span class='file-icon #=addExtensionClass(files[0].extension)#'></span><h6 class='file-heading file-name-heading'>Name: #=name#</h6><h6 class='file-heading file-size-heading'>Size: #=size# bytes</h6><button type='button' class='k-upload-action'></button></div>">
				<kendo:upload-async autoUpload="false" saveUrl="${uploadDataProperty}"/>
	</kendo:upload>
</div>

<script>

function onRequestStart(e){
	$('.k-grid-update').hide();
	$('.k-edit-buttons')
			.append(
					'<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
	$('.k-grid-cancel').hide();
}

function onRequestStart1(e){
	$('.k-grid-update').hide();
	$('.k-edit-buttons')
			.append(
					'<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
	$('.k-grid-cancel').hide();
}

function onRequestStart2(e){
	$('.k-grid-update').hide();
	$('.k-edit-buttons')
			.append(
					'<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
	$('.k-grid-cancel').hide();
}
function onRequestStart3(e){
	$('.k-grid-update').hide();
	$('.k-edit-buttons')
			.append(
					'<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
	$('.k-grid-cancel').hide();
}
function onRequestStart4(e){
	$('.k-grid-update').hide();
	$('.k-edit-buttons')
			.append(
					'<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
	$('.k-grid-cancel').hide();
}
function onRequestStart5(e){
	$('.k-grid-update').hide();
	$('.k-edit-buttons')
			.append(
					'<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
	$('.k-grid-cancel').hide();
}

function onRequestStart6(e){
	$('.k-grid-update').hide();
	$('.k-edit-buttons')
			.append(
					'<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
	$('.k-grid-cancel').hide();
}

function dateEditor(container, options) {
    $('<input name="' + options.field + '"/>')
            .appendTo(container)
            .kendoDateTimePicker({
                format:"dd/MM/yyyy hh:mm tt",
                timeFormat:"hh:mm tt"         
                
    });
}

$(document).ready(function() {
    var window = $("#fileupload"),
        undo = $("#undo")
                .bind("click", function() {
                    window.data("kendoWindow").open().center();
                    undo.hide();
                });

    var onClose = function() {
        undo.show();
    }

    if (!window.data("kendoWindow")) {
        window.kendoWindow({
            width: "600px",
            title: "Upload Property Data",
            actions: [
                "Pin",
                "Minimize",
                "Maximize",
                "Close"
            ],
            close: onClose
        });
    }
});

var errorInfo="";
   
function onExcelDocSuccess(e){
    var windowOwner = $("#fileupload").data("kendoWindow");
   	    windowOwner.close();
  
  
	 if (e.response.status == "cannotImport") {
			errorInfo = "";
			errorInfo = e.response.result.cannotImport;
			for (var i = 0; i < e.response.result.length; i++) {
				errorInfo += (i + 1) + ". "
						+ e.response.result[i].defaultMessage;
			}

			$("#alertsBox").html("");
			$("#alertsBox").html(errorInfo);
			$("#alertsBox").dialog({
				modal : true,
				draggable: false,
				resizable: false,
				buttons : {
					"Close" : function() {
						$(this).dialog("close");
					 	window.location.reload();
					}
				}
			});	
			
		  

		}
}
function errorRegardingUploadExcel(e){
	var windowOwner = $("#fileupload").data("kendoWindow");
	    windowOwner.close();

	
			var errorInfo="Uploaded file does not contain valid data";
			$("#alertsBox").html("");
			$("#alertsBox").html(errorInfo);
			$("#alertsBox").dialog({
				modal : true,
				draggable: false,
				resizable: false,
				buttons : {
					"Close" : function() {
						$(this).dialog("close");
					 	window.location.reload();
					}
				}
			});	
			
	

		 
} 

	
function onSelectupload(e){
	
	$.each(e.files, function (index, value) {			
     var ok = (value.extension == ".xlsx" || value.extension == ".xls") ;
     if (!ok) {
         e.preventDefault();
         alert("Please upload XL (.xlsx) files");
     }
 });

}


function addExtensionClass(extension) {
	switch (extension) {
	case '.jpg':
	case '.img':
	case '.png':
	case '.gif':
		return "img-file";
	case '.doc':
	case '.docx':
		return "doc-file";
	case '.xls':
	case '.xlsx':
		return "xls-file";
	case '.pdf':
		return "pdf-file";
	case '.zip':
	case '.rar':
		return "zip-file";
	default:
		return "default-file";
	}
}


   /*  function onDocSucc(e){
      	alert("File Imported Successfully");
       	window.location.reload(); 
        if (e.response.status == "cannotImport") {
        	errorInfo = e.response.result.cannotImport;
        	alert("Cannot Import"+errorInfo);
        	window.location.reload();
        }
       	if(e.response=="Success")
       {
        	alert("Import");
        	window.loocation.reload();
        
    }
   } */
    function errorS(){
      	alert("File Importing Failed:Empty Cells Found or Duplicate Records Found");
      	window.location.reload();
    }
    </script>

<!-------------------------------------------------------------->
 <script>
 

	$("#grid").on("click", ".k-grid-Import", function() 
 {
		$('#uploadDialog').dialog({
			modal : true,
		}); 
 });
	
   function approveDocuments()
   {
	   //securityCheckForActions("./commanagement/owneraddressdocuments/updateButton");		
		var conf = "Approve all document related to owner?";
		if(conf)
		{
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
					url : "./documents/checkFile/"+SelectedGroupId+"/Property/",
					success : function(response)
					{
						 if( response == "FILEENOTEXISTS" ){
							$("#alertsBox").html("");
							$("#alertsBox").html("Error: Please Upload All Files Before Approving");
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
								url : "./documents/checkAllDocument/"+SelectedGroupId+"/Property/",
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
										url : "./documents/approve/"+SelectedRowId+"/Owner/"+kycCompliant,
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
											kycCompliant = "Owner";
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
   }
   function UploadFileBound(e) 
   {
	   var tempArray = "";
		if(kycCompliant !=null)
		{
			tempArray = kycCompliant.split(","); 
		}
	 	var found = $.inArray('Property', tempArray) > -1;
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
   function uploadExtraDataAlongWithFile(e)
   {	
		e.data = { drId:drId};
   }
   function filesSelectedtoUpload(e)
   {
		var gview = $("#documentsUpload_"+SelectedRowId).data("kendoGrid");
		var selectedItem = gview.dataItem(gview.select());
		if (selectedItem != null) 
		{
			  drId = selectedItem.drId;
			  documentFormatToUpload = selectedItem.documentFormat;
		} 
		var files = e.files;
		var requiredFormat = $("#docFormat_"+SelectedRowId).val();
		requiredFormat = "."+documentFormatToUpload.toLowerCase();//requiredFormat.toLowerCase();
	    if(requiredFormat == '')
		{
			alert("Please select Document Type");
		    return false;
		}
		if( files.length  > 10 ) 
		{
		    alert("Maximum 10 files can be uploaded at a time.");
		    e.preventDefault();
			return false;
		}	 
		for(var fileCntr = 0; fileCntr < files.length; fileCntr ++) 
		{
			if( files[fileCntr].size > 10485760 ) 
			{
				alert("File size more than 10MB can not be uploaded.");
				e.preventDefault();
				return false;
			}
			if(documentFormatToUpload == "Image")
			{
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
				if( files[fileCntr].extension.toLowerCase() != requiredFormat ) 
				{
					alert("Please Upload "+documentFormatToUpload+" Format");
					e.preventDefault();
					return false;
				}
			}
		}
	  }
   
    function clearList()
    {
		 $("#alertsBox").html("");
			$("#alertsBox").html("Uploaded Successfully");
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
		 $(".k-upload-status").remove();
	     $(".k-upload-files.k-reset").find("li").remove();
	     $(".k-upload-status k-upload-status-total").text("");
    }
      //END FOR UPLAOD OF DOCUMENT FILE
   
   function removeDocument(e)
   {
		
   }
   var selectedRowIndex = "";
   function documentChangeEvent(args)
   {
	   	var grid = $("#documentsUpload_"+SelectedRowId).data("kendoGrid");
	   	var selectedRow = grid.select();
	   	selectedRowIndex = selectedRow.index();
   }
   function documentEvent(e)
   {
	   $('div[data-container-for="documentFormat"]').remove();
		$('label[for="documentFormat"]').closest('.k-edit-label').remove();
		if (e.model.isNew()) 
	    {
			//securityCheckForActions("./commanagement/owneraddressdocuments/createButton");
			selectedRowIndex = 0;
			$(".k-window-title").text("Add New Document Details");
			$(".k-grid-update").text("Save");
			$('.k-edit-field .k-input').first().focus();
	    }
		else
		{
			//securityCheckForActions("./commanagement/owneraddressdocuments/updateButton");
			$(".k-window-title").text("Update Document Details");
			$(".k-grid-update").text("Update");
			$('.k-edit-field .k-input').first().focus();
		} 
   }
   function downloadFile()
   {
	   var gview = $("#documentsUpload_"+SelectedRowId).data("kendoGrid");
		//Getting selected item
		var selectedItem = gview.dataItem(gview.select());
		window.open("./download/"+selectedItem.drId);
   }
   
   function documentFormatEditor(container, options)
   {	
		  $('<input type="text" readonly="true" class="k-input k-textbox" id="docFormatId" name="documentFormat" data-bind="value:' + options.field + '"/>')
			.appendTo(container);
   }
   
   function documentDecriptionEditor(container, options) 
	{
		$('<textarea data-text-field="documentDescription" name="documentDescription" data-value-field="documentDescription" data-bind="value:' + options.field + '" style="width: 148px; height: 40px;"/>')
				.appendTo(container);
	}
   //For Document Name
   function documentNameEditor(container, options)
   {	
	   $('<input name="Document Name" required="true" validationMessage="Document Name is Required" data-text-field="name" data-value-field="value" data-bind="value:' + options.field + '"/>').appendTo(container).kendoDropDownList({	
			optionLabel : {
				name : "Select",
				value : "",
			},
			defaultValue : false,
			select: onDocumentSelect,
			sortable : true,
			dataSource : {
				transport : {
			     read : "${getDocumentType}/Property"
				}
			}
	 });
	}
   function onDocumentSelect(e) 
	{
	        var dataItem = this.dataItem(e.item.index());
	        $.ajax({
				type : "POST",
				url : "./documentDefiner/getDocumentFormat",
				dataType: "text", 
				data : {
					documentTypeSelected : dataItem.ddId,
					personType : "Property",
					},
					dataType:"text",
					success : function(response)
					{
						$("#docFormatId").val(response);
						var documentFormat = $("#documentsUpload_"+SelectedRowId).data().kendoGrid.dataSource
					      .data()[selectedRowIndex];
						documentFormat.set('documentFormat', response);
					}				
				});
	}
   
   //On Documents Request End Fi=unction
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
 </script>
<!--------------------------------------------------------------->


<script>
	var test1 = "";
	var flag = "";
	var name = "";
	var res = [];
	var editingName = "";
	var SelectedRowId = "";
	var kycCompliant = "";
	var SelectedGroupId = "";
	function onChangeProperty(arg) {
		var gview = $("#grid").data("kendoGrid");
		var selectedItem = gview.dataItem(gview.select());
		SelectedRowId = selectedItem.propertyId;
		kycCompliant = selectedItem.kycCompliant;
		SelectedGroupId = selectedItem.drGroupId;
		this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
		/* changeInternalGridLanguage */
	}

	function TowerName(data) {
		$.each(data, function(index, value) {
			if (index == "towerName" && value != "" && name != value) {
				if ($.inArray(value, res) == -1)
					res.push(value);
			}

		});
		return data.towerName;
	}

	var propertyNumbers = [];
	var projectIds = [];
	var blockIds = [];
	var operation = "";
	function propertyEvent(e) {
		selectedProjectId = e.model.projectId;
		selectedBlockId = e.model.blocks;
		var numeric = $("#area").kendoNumericTextBox();
		$('div[data-container-for="area"]').find(".k-numeric-wrap").find(
				".k-select").hide();

		$(".k-dropdown").css("z-index", "inherit");
		$(".k-icon").css("height", "16px");

		var projectId = $('input[name="ProjectName"]').val();
		$('div[data-container-for="projectName"]').remove();
		$('label[for="projectName"]').closest('.k-edit-label').remove();
		$('div[data-container-for="blockName"]').remove();
		$('label[for="blockName"]').closest('.k-edit-label').remove();

		if (e.model.isNew()) {
			$(".k-window-title").text("Add New Property");
			$(".k-grid-update").text("Save");

			$('label[for="tenancyHandoverDate"]').parent().hide();
			$('div[data-container-for="tenancyHandoverDate"]').hide();
			operation = "NewRecord"
			//checkValidation();
		} else {
			//alert(e.model.projectId+":"+e.model.blocks+":"+e.model.property_No);
			$(".k-window-title").text("Edit Property Details");
			$(".k-grid-update").text("Update");
			propertyNumbers.splice(propertyNumbers.indexOf(e.model.projectId
					+ ":" + e.model.blocks + ":" + e.model.property_No), 1);
			//alert(e.model.status);
			if (e.model.status == "Sold") {
				$('label[for="tenancyHandoverDate"]').parent().show();
				$('div[data-container-for="tenancyHandoverDate"]').show();
				//$('input[name="tenancyHandoverDate"]').val(" ");
				$('input[name="tenancyHandoverDate"]').prop('required', true);
			} else {
				$('label[for="tenancyHandoverDate"]').parent().hide();
				$('div[data-container-for="tenancyHandoverDate"]').hide();
				$('input[name="tenancyHandoverDate"]').prop('required', false);
			}

			operation = "EditRow";
			//alert(propertyNumbers);
			//alert(res);
			//checkValidation();
		}
	}

	function propertyParse(response) {
		var data = response; //<--data might be in response.data!!!
		propertyNumbers = [];
		projectIds = [];
		blockIds = [];
		for (var idx = 0, len = data.length; idx < len; idx++) {
			propertyNumbers.push(data[idx].projectId + ":" + data[idx].blocks
					+ ":" + data[idx].property_No);
			//projectIds.push(data[idx].projectId);
			//blockIds.push(data[idx].blocks);
		}
		//blockIds = jQuery.unique(blockIds);
		// projectIds = 
		// alert(propertyNumbers);
		return response;
	}

	function clearFilter() {
		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
		var gridDocumentDefiner = $("#grid").data("kendoGrid");
		gridDocumentDefiner.dataSource.read();
		gridDocumentDefiner.refresh();
	}

	function edit(e) {

		test1 == "EditUser";

		//Selecting Grid
		var gview = $("#grid").data("kendoGrid");
		//Getting selected item
		var selectedItem = gview.dataItem(gview.select());
		//accessing selected rows data 
		editingName = selectedItem.towerName;

		$.each(res, function(index, value) {
			if (editingName == value) {
				res.splice($.inArray(value, res), 1);
			}
		});
	}

	(function($, kendo) {
		$
				.extend(
						true,
						kendo.ui.validator,
						{
							rules : { // custom rules          	
								TowerName : function(input, params) {
									//check for the name attribute 
									if (input.filter("[name='towerName']").length
											&& input.val()) {
										name = input.val();
										$.each(res, function(index, value) {
											if ((name == value)) {
												flag = input.val();
											}
										});
										return /^[A-Z]+[a-z A-Z 0-9]*[_]{0,1}[a-z A-Z 0-9]*[^_]$/
												.test(input.val());
									}
									return true;
								},
								property_No : function(input, params) {
									if (input.filter("[name='property_No']").length
											&& input.val()) {
										return /^[a-zA-Z0-9-_/ ]{0,25}$/
												.test(input.val());
									}
									return true;
								},
								area : function(input, params) {
									if (input.filter("[name='area']").length
											&& input.val()) {
										return /^[0-9]{1,10}$/
												.test(input.val());
									}
									return true;
								},
								/* tenancyHandoverDate_blank : function(input,params)
								{
								//check for the name attribute 
								    if (input.attr("name") == "tenancyHandoverDate") 
								    {
								     	return $.trim(input.val()) !== "";
								    }
								    return true;
								 
								}, */
								area_blank : function(input, params) {
									//check for the name attribute 
									if (input.attr("name") == "area") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},
								property_No_blank : function(input, params) {
									if (input.attr("name") == "property_No") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},
								property_Floor_blank : function(input, params) {
									if (input.attr("name") == "property_Floor") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},
								no_of_ParkingSlots_blank : function(input,
										params) {
									if (input.attr("name") == "no_of_ParkingSlots") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},
								PropertyNameUniqueness : function(input, params) {
									if (input.filter("[name='property_No']").length
											&& input.val()) {
										var flag = true;
										var fieldValue = selectedProjectId
												+ ":" + selectedBlockId + ":"
												+ input.val();
										$
												.each(
														propertyNumbers,
														function(idx1, elem1) {
															//alert(elem1+"-----"+fieldValue);
															if (elem1
																	.toLowerCase() == fieldValue
																	.toLowerCase()) {
																flag = false;
															}
														});
										return flag;
									}
									return true;
								},
								ProjectId_blank : function(input, params) {
									if (input.attr("name") == "projectId") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},
								Blocks_blank : function(input, params) {
									if (input.attr("name") == "blocks") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},
								PropertyType_blank : function(input, params) {
									if (input.attr("name") == "propertyType") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},
								areaType_blank : function(input, params) {
									if (input.attr("name") == "areaType") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},
								status_blank : function(input, params) {
									if (input.attr("name") == "status") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},
								propertyBillable_blank : function(input, params) {
									if (input.attr("name") == "propertyBillable") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},
							/* propertiesUnderBlock_restriction : function(input, params){
							   if (input.filter("[name='blocks']").length && input.val()) 
								{
									var flag = true;
									alert(propertiesUnderBlock+"-----------"+maxPropertyAllowed);
									 if(propertiesUnderBlock == maxPropertyAllowed )
									 {
										 
										flag = false;	 
								     }
									return flag;
								}
								return true;
							}, */

							/* ProjectNameUniqueness: function(input) 
							{          
								if(input.filter("[name='projectName']").length && input.val() && flag != "" && (test1 !="EditUser")) 
								{
									if(flag == editingName)
									{
										return true;
									}	
									
									flag = "";
							   	 	return false;	
								}
								return true;
							} */
							},
							messages : { //custom rules messages
								TowerName : "TOWER Name should start with a Capital letter, can contain alphanumeric characters and should not contain any special charecters except atmost one underscore (_) but should not end with it and minimum length is 2",
								property_No : "Only alphanumeric with - _ / is allowed",
								area : "Only Numbers are allowed",
								area_blank : "Area is required",
								property_No_blank : "Property Number is required",
								property_Floor_blank : "Property Floor is required",
								no_of_ParkingSlots_blank : "Number of Parking Slots is required",
								//tenancyHandoverDate_blank : "Posession Date is required",
								PropertyNameUniqueness : "Property already exists in selected project and block",
								ProjectId_blank : "Project is required",
								Blocks_blank : "Tower/Block is required",
								PropertyType_blank : "Property Type is required",
								areaType_blank : "Area Measurment is required",
								propertyBillable_blank : "Property Billable is required",
								status_blank : "Property Status is required",
							//propertiesUnderBlock_restriction : "Maximum allowed properties<br/> under this block is exceeded"
							}
						});

	})(jQuery, kendo);

	function PropertyType(container, options) {

		$(
				'<input data-text-field="text" name="propertyTypeEE" required="true" validationmessage="Property Type is required" data-value-field="value"  data-bind="value:' + options.field + '" />')
				.appendTo(container).kendoDropDownList({
					autoBind : false,
					optionLabel : "Select",
					dataSource : booleanData,
				//dataTextField : 'text',
				//dataValueField : 'value'
				});
		$('<span class="k-invalid-msg" data-for="propertyTypeEE"></span>')
				.appendTo(container);
	}

	function measurmentEditor(container, options) {
		var booleanData = [ {
			text : 'Select',
			value : ""
		}, {
			text : 'Sq Mts',
			value : 'Sq Mts'
		}, {
			text : 'Sq Yds',
			value : "Sq Yds"
		}, {
			text : 'Sq Ft',
			value : "Sq Ft"
		} ];

		$(
				'<input name="MeasurementType" required="true" validationmessage="Measurement Type is required" />')
				.attr('data-bind', 'value:areaType').appendTo(container)
				.kendoDropDownList({
					dataSource : booleanData,
					dataTextField : 'text',
					dataValueField : 'value'
				});
		$('<span class="k-invalid-msg" data-for="MeasurementType"></span>')
				.appendTo(container);
	}
	function PropertyBillable(container, options) {
		var booleanData = [ {
			text : 'Select',
			value : ""
		}, {
			text : 'YES',
			value : "Yes"
		}, {
			text : 'NO',
			value : "No"
		} ];

		$(
				'<input name="Billable" required="true" validationmessage="Property Billable is required"/>')
				.attr('data-bind', 'value:propertyBillable')
				.appendTo(container).kendoDropDownList({
					dataSource : booleanData,
					dataTextField : 'text',
					dataValueField : 'value'
				});
		$('<span class="k-invalid-msg" data-for="Billable"></span>').appendTo(
				container);
	}

	function PropertyStatus(container, options) {
		var booleanData = [ {
			text : 'Select',
			value : ""
		}, {
			text : 'Unsold',
			value : "Unsold"
		}, {
			text : 'Sold',
			value : "Sold"
		} ];

		$('<input />')
				.attr('data-bind', 'value:status')
				.appendTo(container)
				.kendoDropDownList(
						{
							dataSource : booleanData,
							dataTextField : 'text',
							dataValueField : 'value',
							change : function(e) {
								if ((this.value() == "Sold")) {
									$('label[for="tenancyHandoverDate"]')
											.parent().show();
									$(
											'div[data-container-for="tenancyHandoverDate"]')
											.show();
									$('input[name="tenancyHandoverDate"]')
											.prop('required', true);
									$('input[name="tenancyHandoverDate"]')
											.prop('message', "Is Required");
								} else {
									$('label[for="tenancyHandoverDate"]')
											.parent().hide();
									$(
											'div[data-container-for="tenancyHandoverDate"]')
											.hide();
									$('input[name="tenancyHandoverDate"]')
											.prop('required', false);
								}
							},
						});

	}

	function propertyStatusChange(e) {
		if ((this.value() == "Sold" || this.value() == "Sold/UnOccupied")) {
			$('label[for="tenancyHandoverDate"]').parent().show();
			$('div[data-container-for="tenancyHandoverDate"]').show();
			$('input[name="tenancyHandoverDate"]').prop('required', true);
			$('input[name="tenancyHandoverDate"]').prop('message',
					"Is Required");
		} else {
			$('label[for="tenancyHandoverDate"]').parent().hide();
			$('div[data-container-for="tenancyHandoverDate"]').hide();
			$('input[name="tenancyHandoverDate"]').prop('required', false);
		}

	}

	var selectedProjectId = "";
	function projectEditor(container, options) {
		$(
				'<select data-text-field="projectName" id="projectId" name="ProjectNameEE" required="true" validationmessage="Project Name is required"  data-value-field="projectId" data-bind="value:' + options.field + '"/>')
				.appendTo(container).kendoDropDownList({
					autoBind : false,
					optionLabel : "Select",
					select : function(e) {
						var dataItem = this.dataItem(e.item.index());
						selectedProjectId = dataItem.projectId;
						//alert("event :: select (" + dataItem.text + " : " + dataItem.projectId + ")" );
					},
					dataSource : {
						transport : {
							read : "${getProject}"
						}
					}
				});
		$('<span class="k-invalid-msg" data-for="ProjectNameEE"></span>')
				.appendTo(container);

	}

	function projectSelect(e) {
		var dataItem = this.dataItem(e.item.index());
		selectedProjectId = dataItem.projectId;
		//alert("event :: select (" + dataItem.text + " : " + dataItem.projectId + ")" );

	}
	var selectedBlockId = "";
	var propertiesUnderBlock = "";
	var maxPropertyAllowed = "";
	function blockSelect(e) {
		var dataItem = this.dataItem(e.item.index());
		selectedBlockId = dataItem.blockId;
		maxPropertyAllowed = dataItem.numOfProperties;
		// alert(dataItem.numOfProperties+"---"+dataItem.blockId);
		//$("#propertiesAllowed").val(dataItem.numOfProperties);

		/* $.ajax({
			type : "GET",
			url : "./property/getPropertyCountInSelectedBlock/"+dataItem.blockId,
			success : function(response)
			{
				//alert(response);
				propertiesUnderBlock = response;
			}
		}); */

		//alert("event :: select (" + dataItem.text + " : " + dataItem.blockId + ")" );
	}

	function towerEditor(container, options) {
		$(
				'<select data-text-field="blockName" name="blockName"  required="true" validationmessage="Tower/Block is required" data-value-field="blockId" data-bind="value:' + options.field + '"/>')
				.appendTo(container).kendoDropDownList({
					autoBind : false,
					optionLabel : "Select",
					cascadeFrom : "projectId",
					select : function(e) {
						var dataItem = this.dataItem(e.item.index());
						selectedBlockId = dataItem.blockId;
						//alert("event :: select (" + dataItem.text + " : " + dataItem.blockId + ")" );
					},
					dataSource : {
						transport : {
							read : "${getBlocks}"
						}
					}
				});
		$('<span class="k-invalid-msg" data-for="blockName"></span>').appendTo(
				container);

	}

	function getProjectId(e) {

		var dataItem = this.dataItem(e.item.index);
		var proId = dataItem.projectId;
		//alert("-----"+proId);

	}

	function onRequestEnd(e) {
		if (typeof e.response != 'undefined') {
			if (e.response.status == "FAIL") {
				errorInfo = "";
				errorInfo = e.response.result.invalid;
				for (i = 0; i < e.response.result.length; i++) {
					errorInfo += (i + 1) + ". "
							+ e.response.result[i].defaultMessage;
				}

				if (e.type == "create") {
					alert("Error: Creating the Project\n\n" + errorInfo);
				}

				if (e.type == "update") {
					alert("Error: Updating the Project\n\n" + errorInfo);
				}

				var grid = $("#grid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
				return false;
			}
			if (e.response.status == "PROPERTY_NUM_EXISTS") {
				errorInfo = "";
				errorInfo = e.response.result.propertyNumberAlreadyExists;

				$("#alertsBox").html("");
				$("#alertsBox").html("Error: Propety Creating<br>" + errorInfo);
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				var grid = $("#grid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
				return false;
			}
			if (e.response.status == "CHILD_FOUND_EXCEPTION") {
				errorInfo = "";
				errorInfo = e.response.result.childFoundException;

				$("#alertsBox").html("");
				$("#alertsBox").html("Error: Delete Property<br>" + errorInfo);
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				var grid = $("#grid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
				return false;
			}

			if (e.type == "update" && !e.response.Errors) {
				//alert("Update record is successfull");
				$("#alertsBox").html("");
				$("#alertsBox").html("Property updated is successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				var grid = $("#grid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			}

			if (e.type == "create" && !e.response.Errors) {
				//alert("Create record is successfull");
				$("#alertsBox").html("");
				$("#alertsBox").html("Property created successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});

				var grid = $("#grid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			}
			if (e.type == "destroy" && !e.response.Errors) {
				$("#alertsBox").html("");
				$("#alertsBox").html("Property deleted successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});

				var grid = $("#grid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			}
		}
	}

	function OwnerInfo() {
		var gaddressview = $("#Owners_" + SelectedRowId).data("kendoGrid");
		var selectedAddressItem = gaddressview.dataItem(gaddressview.select());
		$
				.ajax({
					type : "POST",
					url : "./property/getFamilyMembersBasedOnPersonId",
					dataType : "json",
					data : {
						personId : selectedAddressItem.personId,
					},
					success : function(response) {

						var contactList = "<table frame=box bgcolor=white width=550px><tr bgcolor=#0A7AC2>";
						contactList = contactList
								+ "<th><font color=white></font></th><th><font color=white>Person Name</font></th><th><font color=white>Relationship</font></th><th><font color=white>Contact Details</font></th></tr>";

						if (response.length > 0) {
							for (var s = 0, len = response.length; s < len; ++s) {
								var contact = response[s];
								contactList += "<tr><td align=center><img src='./person/getpersonimage/"+contact.personId+"' id='myImages_#=personId#' alt='Click to Upload Image' width='80px' height='80px'/></td><td align=center>"
										+ contact.personName
										+ "</td><td align=center>"
										+ contact.relationShip
										+ "</td><td align=center>"
										+ contact.contactContent + "</td></tr>";
							}
						} else {
							contactList += "<tr><td>&nbsp;&nbsp;No data to display </td><td></td><td></td></tr>";
						}
						contactList = contactList + "</table>";

						//$("#contactDialogContent").text(contactList);
						$('#dialogBoxforFamilyMembers').html("");
						$('#dialogBoxforFamilyMembers').html(contactList);
						$('#dialogBoxforFamilyMembers').dialog({
							width : 600,
							position : 'center',
							modal : true,
						});

					}
				});
		return false;
	}

	function TenantInfo() {
		var gaddressview = $("#Tenants_" + SelectedRowId).data("kendoGrid");
		var selectedAddressItem = gaddressview.dataItem(gaddressview.select());
		$
				.ajax({
					type : "POST",
					url : "./property/getFamilyMembersBasedOnPersonId",
					dataType : "json",
					data : {
						personId : selectedAddressItem.personId,
					},
					success : function(response) {

						var contactList = "<table frame=box bgcolor=white width=450px><tr bgcolor=#0A7AC2>";
						contactList = contactList
								+ "<th><font color=white></font></th><th><font color=white>Person Name</font></th><th><font color=white>Relationship</font></th><th><font color=white>Contact Details</font></th></tr>";

						if (response.length > 0) {
							for (var s = 0, len = response.length; s < len; ++s) {
								var contact = response[s];
								contactList += "<tr><td align=center><img src='./person/getpersonimage/"+contact.personId+"' id='myImages_#=personId#' alt='Click to Upload Image' width='80px' height='80px'/></td><td align=center>"
										+ contact.personName
										+ "</td><td align=center>"
										+ contact.relationShip
										+ "</td><td align=center>"
										+ contact.contactContent + "</td></tr>";
							}
						} else {
							contactList += "<tr><td>&nbsp;&nbsp;No data to display </td><td></td><td></td></tr>";
						}
						contactList = contactList + "</table>";

						//$("#contactDialogContent").text(contactList);
						$('#dialogBoxforFamilyMembers').html("");
						$('#dialogBoxforFamilyMembers').html(contactList);
						$('#dialogBoxforFamilyMembers').dialog({
							width : 475,
							position : 'center',
							modal : true,
						});

					}
				});
		return false;
	}
	function DomesticInfo() {
		var gaddressview = $("#DomesticHelp_" + SelectedRowId)
				.data("kendoGrid");
		var selectedAddressItem = gaddressview.dataItem(gaddressview.select());
		$
				.ajax({
					type : "POST",
					url : "./property/getFamilyMembersBasedOnPersonId",
					dataType : "json",
					data : {
						personId : selectedAddressItem.personId,
					},
					success : function(response) {

						var contactList = "<table frame=box bgcolor=white width=450px><tr bgcolor=#0A7AC2>";
						contactList = contactList
								+ "<th><font color=white></font></th><th><font color=white>Person Name</font></th><th><font color=white>Relationship</font></th><th><font color=white>Contact Details</font></th></tr>";

						if (response.length > 0) {
							for (var s = 0, len = response.length; s < len; ++s) {
								var contact = response[s];
								contactList += "<tr><td align=center><img src='./person/getpersonimage/"+contact.personId+"' id='myImages_#=personId#' alt='Click to Upload Image' width='80px' height='80px'/></td><td align=center>"
										+ contact.personName
										+ "</td><td align=center>"
										+ contact.relationShip
										+ "</td><td align=center>"
										+ contact.contactContent + "</td></tr>";
							}
						} else {
							contactList += "<tr><td>&nbsp;&nbsp;No data to display </td><td></td><td></td></tr>";
						}
						contactList = contactList + "</table>";

						//$("#contactDialogContent").text(contactList);
						$('#dialogBoxforFamilyMembers').html("");
						$('#dialogBoxforFamilyMembers').html(contactList);
						$('#dialogBoxforFamilyMembers').dialog({
							width : 475,
							position : 'center',
							modal : true,
						});

					}
				});
		return false;
	}
	
	
	function uploadDocs() {
	 var gview = $("#grid").data("kendoGrid");
		var selectedItem = gview.dataItem(gview.select());
		if (selectedItem != null) {
			propertyId = selectedItem.propertyId;
		} 
		 $('#uploadDialog1').dialog({
			modal : true,
		});
		return false;
	} 
	
 function uploadExtraData(e) {
		
		var files = e.files;
		// Check the extension of each file and abort the upload if it is not .jpg
		$.each(files, function() {
			if (this.extension.toLowerCase() == ".pdf") {
				e.data = {propertyId : propertyId , type : this.extension};
			}
			else if (this.extension.toLowerCase() == ".doc") {
				e.data = {propertyId : propertyId , type : this.extension};
			}
			else if (this.extension.toLowerCase() == ".docx") {
				e.data = {propertyId : propertyId , type : this.extension};
			}
			else if (this.extension.toLowerCase() == ".csv") {
				e.data = {propertyId : propertyId , type : this.extension};
			}
			else if (this.extension.toLowerCase() == ".xlsx") {
				e.data = {propertyId : propertyId , type : this.extension };
			}
			else if (this.extension.toLowerCase() == ".xls") {
				e.data = {propertyId : propertyId , type : this.extension };
			}
			else {
				alert("Invalid Document Type:\nAcceptable formats are pdf, doc, docx, csv and xls");
				e.preventDefault();
				return false;
			}
		}); 
	}
	
	
	function downloadDocs() {
		var gview = $("#grid").data("kendoGrid");
		var selectedItem = gview.dataItem(gview.select());
		window.open("./property/download/" + selectedItem.propertyId); 
	}
	
	function onDocSuccess(e){
		alert("Uploaded Successfully !!!");
		$(".k-upload-files.k-reset").find("li").remove();
		$(".k-upload-status-total").remove();
	} 
	
	
</script>
<style>
td {
	vertical-align: middle;
}

,
 .k-window .k-widget {
	z-index: inherit;
} 

.k-icon {
	height: 16px;
}

.k-upload-button input {
	z-index: 10000
}

</style>
<style>
.k-edit-label:after {
	content: " *";
}
.k-tabstrip{
	width: 1150px
}

.file-icon {
		display: inline-block;
		float: left;
		width: 48px;
		height: 48px;
		margin-left: 10px;
		margin-top: 13.5px;
		}
		
		.img-file {
		background-image: url(./resources/kendo/web/upload/jpg.png)
	}
	
	.doc-file {
		background-image: url(./resources/kendo/web/upload/doc.png)
	}
	
	.pdf-file {
		background-image: url(./resources/kendo/web/upload/pdf.png)
	}
	
	.xls-file {
		background-image: url(./resources/kendo/web/upload/xls.png)
	}
	
	.zip-file {
		background-image: url(./resources/kendo/web/upload/zip.png)
	}
	
	.default-file {
		background-image: url(./resources/kendo/web/upload/default.png)
	}
	
	#example .file-heading {
		font-family: Arial;
		font-size: 1.1em;
		display: inline-block;
		float: left;
		width: 450px;
		margin: 0 0 0 20px;
		height: 25px;
		-ms-text-overflow: ellipsis;
		-o-text-overflow: ellipsis;
		text-overflow: ellipsis;
		overflow: hidden;
		white-space: nowrap;
	}
	
	#example .file-name-heading {
		font-weight: bold;
	}
	
	#example .file-size-heading {
		font-weight: normal;
		font-style: italic;
	}
	
	li.k-file .file-wrapper .k-upload-action {
		position: absolute;
		top: 0;
		right: 0;
	}
	
	li.k-file div.file-wrapper {
		position: relative;
		height: 75px;
	}
	
	.ui-dialog-osx {
	    -moz-border-radius: 0 0 8px 8px;
	    -webkit-border-radius: 0 0 8px 8px;
	    border-radius: 0 0 8px 8px; border-width: 0 8px 8px 8px;
	}
</style>
<!-- </div>
    </div> --> 