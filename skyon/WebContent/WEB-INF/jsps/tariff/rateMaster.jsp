<%@include file="/common/taglibs.jsp"%>

<c:url value="/elRateMaster/read" var="elratemasterReadUrl" />
<c:url value="/elRateMaster/create" var="elratemasterCreateUrl" />
<c:url value="/elRateMaster/update" var="elratemasterUpdateUrl" />
<c:url value="/elRateMaster/destroy" var="elratemasterDestroyUrl" />
<c:url value="/common/getAllChecks" var="allChecksUrl" />
<c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />
<c:url value="/person/getAllAttributeValues" var="filterAutoCompleteUrl" />
<c:url value="/elRateMaster/getToTariffMasterComboBoxUrl" var="toTariffMasterComboBoxUrl" />
<c:url value="/common/getAllChecks" var="allChecksUrl" />
<c:url value="/stateName/getAllStateName" var="stateNamesUrl" />
<c:url value="/tariffMasters/getAllTariffMasters" var="allTariffMasters" />

<!-- for rate slabs -->
<c:url value="/tariff/elrateslab/read" var="elRateSlabReadUrl" />
<c:url value="/tariff/elrateslab/create" var="elRateSlabCreateUrl" />
<c:url value="/tariff/elrateslab/update" var="elRateSlabUpdateUrl" />
<c:url value="/tariff/elrateslab/destroy" var="elRateSlabDeleteUrl" />

<!-- for TOD Rates -->
<c:url value="/tariff/elTODRate/create" var="elTODRateCreateUrl" />
<c:url value="/tariff/elTODRate/read" var="elTODRateReadUrl" />
<c:url value="/tariff/elTODRate/update" var="elTODRateUpdateUrl" />
<c:url value="/tariff/elTODRate/destroy" var="elTODRateDeleteUrl" />
<c:url value="/tariff/elTODRate/categories" var="elrateSlabDropDown" />
<c:url value="/tariff/getMaxDate" var="getMaxDate"></c:url>

<!-- Filters Data url's -->
<c:url value="/tariff/filter" var="commonFilterUrl" />
<c:url value="/tariffNameToFilter/filter" var="tariffNameToFilter" />
<c:url value="/stateNameToFilter/filter" var="stateNameToFilter" />
<c:url value="/tariff/filter/states" var="stateFilterUrl" />

<kendo:grid name="rateMasterGrid" remove="rateMasterDeleteEvent" resizable="true" pageable="true" selectable="true" edit="ratemasterEvent" change="onChangeRateMaster" detailTemplate="elRateMasterSubGrid" sortable="true" scrollable="true"
	groupable="true">

	<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10"></kendo:grid-pageable>
	<kendo:grid-filterable extra="false">
		<kendo:grid-filterable-operators>
			<kendo:grid-filterable-operators-string eq="Is equal to" />
			<kendo:grid-filterable-operators-date gt="Is after" lt="Is before"/>
		</kendo:grid-filterable-operators>

	</kendo:grid-filterable>
	<kendo:grid-editable mode="popup"  />
	<kendo:grid-toolbar>
		<kendo:grid-toolbarItem name="create" text="Add Rate Master" />
		<kendo:grid-toolbarItem
			template="<a class='k-button' href='\\#' onclick=clearFilterRateMaster()><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>" />
		<kendo:grid-toolbarItem name="showAllRateMasters" text="Show All" />
		<kendo:grid-toolbarItem name="generateDGUnit"
			text="Calculate DG Unit" />
			<kendo:grid-toolbarItem name="showDGUnit"
			text="Show DG Unit" />
	</kendo:grid-toolbar>
	<kendo:grid-columns>

	<kendo:grid-column title="ELRM_ID" field="elrmid" width="110px" hidden="true" />
	<kendo:grid-column title="State Name *" field="stateName" width="100px" editor="stateNameEditor">
		<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function rateDescriptionFilter(element) {
							element
									.kendoAutoComplete({
										placeholder : "Enter Description",
										dataType : 'JSON',
										dataSource : {
											transport : {
												read : "${stateFilterUrl}"
											}
										}
									});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>
		
		<kendo:grid-column title="Tariff Master *" field="tariffName" width="100px">
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function tariffNameFilter(element) {
							element
									.kendoAutoComplete({
										placeholder : "Enter Tariff Name",
										dataType : 'JSON',
										dataSource : {
											transport : {
												read : "${tariffNameToFilter}/tariffName"
											}
										}
									});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>
	<kendo:grid-column title="Tariff Master *" field="elTariffID" width="100px" hidden="true" editor="tariffNameEditor"/>
		
		<kendo:grid-column title="Rate&nbsp;Name*" field="rateName" editor="toRateNameEditor" width="85px">
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function rateDescriptionFilter(element) {
							element.kendoAutoComplete({
										placeholder : "Enter Description",
										dataType : 'JSON',
										dataSource : {
											transport : {
												read : "${commonFilterUrl}/rateName"
											}
										}
									});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>
		
		<kendo:grid-column title="Category&nbsp;*" field="rateNameCategory" editor="rateNameCategoryEditor" width="85px">
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function rateDescriptionFilter(element) {
							element.kendoAutoComplete({
										placeholder : "Enter Description",
										dataType : 'JSON',
										dataSource : {
											transport : {
												read : "${commonFilterUrl}/rateNameCategory"
											}
										}
									});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>

		<kendo:grid-column title="Rate&nbsp;Description*" field="rateDescription" filterable="true" width="120px">
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function rateDescriptionFilter(element) {
							element
									.kendoAutoComplete({
										placeholder : "Enter Description",
										dataType : 'JSON',
										dataSource : {
											transport : {
												read : "${commonFilterUrl}/rateDescription"
											}
										}
									});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>

		<kendo:grid-column title="Rate&nbsp;Type&nbsp;*" field="rateType" editor="toRateTypeEditor" width="90px">
			<kendo:grid-column-values value="${rateTypes}" />
		</kendo:grid-column>

		<kendo:grid-column title="Rate&nbsp;UOM&nbsp;*" field="rateUOM" width="90px">
			<kendo:grid-column-values value="${rateUOM}" />
		</kendo:grid-column>
<!-- editor="validFromEditor" format="{0:dd/MM/yyyy}" -->
		<kendo:grid-column title="Valid&nbsp;From&nbsp;*" field="validFrom" format="{0:dd/MM/yyyy}" width="90px">
		<%-- 
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function validFromFilter(element) {
							element.kendoDatePicker({
								format : "dd/MM/yyyy"
							});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable> --%>
		</kendo:grid-column>
<!--  format="{0:dd/MM/yyyy}" -->
		<kendo:grid-column title="Valid&nbsp;To&nbsp;*" field="validTo" format="{0:dd/MM/yyyy}" width="80px">
			<%-- <kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function validToFilter(element) {
							element.kendoDatePicker({
								format : "dd/MM/yyyy"
							});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable> --%>
		</kendo:grid-column>

		<kendo:grid-column title="Min Rate(In Rs)" field="minRate" width="100px">
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function minRateFilter(element) {
							element.kendoNumericTextBox({});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>
		<kendo:grid-column title="Max Rate(In Rs)" field="maxRate" width="100px">
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function maxRateToFilter(element) {
							element.kendoNumericTextBox({});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>

		<kendo:grid-column title="TOD Type *" field="todType" editor="toTODTypeEditor" width="80px">
			<kendo:grid-column-values value="${todType}" />
		</kendo:grid-column>

		<kendo:grid-column title="Rate Status *" field="status" width="90px">
			<kendo:grid-column-values value="${status}" />
		</kendo:grid-column>

		<kendo:grid-column title="&nbsp;" width="158px">
			<kendo:grid-column-command>
				<kendo:grid-column-commandItem name="edit" />
				<kendo:grid-column-commandItem name="destroy" />
			</kendo:grid-column-command>
		</kendo:grid-column>

		<kendo:grid-column title=""
			template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Active#=data.elrmid#'>#= data.status == 'Active' ? 'Inactive' : 'Active' #</a>"
			width="70px" />
    	</kendo:grid-columns>

	<kendo:dataSource pageSize="20" requestEnd="onRequestEndRateMaster" requestStart="onRequestStartRateMaster">

		<kendo:dataSource-transport>
			<kendo:dataSource-transport-create url="${elratemasterCreateUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-read url="${elratemasterReadUrl}" dataType="json" type="POST" contentType="application/json" />
			<kendo:dataSource-transport-update url="${elratemasterUpdateUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-destroy url="${elratemasterDestroyUrl}" dataType="json" type="GET" contentType="application/json" />
		</kendo:dataSource-transport>

		<kendo:dataSource-schema>
			<kendo:dataSource-schema-model id="elrmid">
				<kendo:dataSource-schema-model-fields>

					<kendo:dataSource-schema-model-field name="elrmid" type="number" />

					<kendo:dataSource-schema-model-field name="elTariffID" defaultValue="Select"/>
					
					<kendo:dataSource-schema-model-field name="rateName" type="string" defaultValue="Select"/>
					
					<kendo:dataSource-schema-model-field name="rateNameCategory" type="string" defaultValue="Select"/>

					<kendo:dataSource-schema-model-field name="rateDescription" type="string"/>
						
					<kendo:dataSource-schema-model-field name="rateType" defaultValue="Single Slab" />

					<kendo:dataSource-schema-model-field name="rateUOM" defaultValue="KW"/>

					<kendo:dataSource-schema-model-field name="validFrom" type="date"/>
						
					<kendo:dataSource-schema-model-field name="validTo" type="date"/>
				
					<kendo:dataSource-schema-model-field name="minRate" type="number"/>
						
					<kendo:dataSource-schema-model-field name="maxRate" type="number"/>
					
					<kendo:dataSource-schema-model-field name="todType" type="string" defaultValue="None" />

					<kendo:dataSource-schema-model-field name="status" editable="true" type="string" />

					<kendo:dataSource-schema-model-field name="stateName" type="string" />
					
					<kendo:dataSource-schema-model-field name="tariffName" type="string" />

				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>

	</kendo:dataSource>
</kendo:grid>

<kendo:grid-detailTemplate id="elRateMasterSubGrid">
	<kendo:tabStrip name="rateMasterTabStrip_#=elrmid#">
		<kendo:tabStrip-items>

			<kendo:tabStrip-item text="Rate Slab" selected="true">
				<kendo:tabStrip-item-content>
					<div class='rateSlbDIV'>
						<br />
						<kendo:grid name="elRateSlabGrid_#=elrmid#" pageable="true"
							filterable="true" dataBound="CheckBoxDataBound"
							change="onGridChange" resizable="true" sortable="true"
							reorderable="true" selectable="true" scrollable="true"
							edit="elRateSlabEditEvent" remove="rateSlabDeleteEvent" >

							<kendo:grid-pageable pageSizes="true" buttonCount="5"
								pageSize="10" input="true" numeric="true"></kendo:grid-pageable>
							<kendo:grid-filterable extra="false">
								<kendo:grid-filterable-operators>
									<kendo:grid-filterable-operators-string eq="Is equal to" />
								</kendo:grid-filterable-operators>
							</kendo:grid-filterable>
							<kendo:grid-editable mode="popup" />
							<kendo:grid-toolbar>
								<kendo:grid-toolbarItem name="create" text="Add Rate Slab" />
								<kendo:grid-toolbarItem text="Merge" />
							</kendo:grid-toolbar>

							<kendo:grid-columns>
								<kendo:grid-column title="&nbsp;" width="30px">
								</kendo:grid-column>

								<kendo:grid-column title="ELRS_ID" field="elrsId" hidden="true" width="70px" />

								<kendo:grid-column title="Slab Number *" field="slabsNo" width="70px" filterable="false"/>

								<kendo:grid-column title="Slab Type *" field="slabType" editor="rateSlabTypeEditor" width="70px" filterable="false">
									<kendo:grid-column-values value="${slabType}" />
								</kendo:grid-column>

								<kendo:grid-column title="Slab From *" field="dummySlabFrom" width="80px" filterable="false"/>
								
								<kendo:grid-column title="Slab From *" field="slabFrom" width="80px" hidden="true" filterable="false" />

								<kendo:grid-column title="Max Value" field="maxSlabToValue" filterable="false" sortable="false" width="0px" hidden="true" />
								
								<kendo:grid-column title="Slab To *" field="dummySlabTo" width="80px" filterable="false" />
								
								<kendo:grid-column title="Slab To *" field="slabTo" width="70px" hidden="true" filterable="false" />

								<kendo:grid-column title="Rate Type *" field="slabRateType"	width="70px" filterable="false">
									<kendo:grid-column-values value="${slabRateType}" />
								</kendo:grid-column>

								<kendo:grid-column title="Rate *" field="rate" width="70px" filterable="false" />

								<kendo:grid-column title="Slab Status *" field="status" width="70px" filterable="false">
									<kendo:grid-column-values value="${status}" />
								</kendo:grid-column>

								<kendo:grid-column title="&nbsp;" width="250px">
									<kendo:grid-column-command>
										<kendo:grid-column-commandItem name="Split" click="splitFunction" />
										<kendo:grid-column-commandItem name="edit" />
										<kendo:grid-column-commandItem name="destroy" />
										<kendo:grid-column-commandItem name="Change Status" click="elRateSlabStatusChange" />
									</kendo:grid-column-command>
								</kendo:grid-column>

							</kendo:grid-columns>

							<kendo:dataSource requestEnd="elRateSlabRequestEnd" requestStart="elRateSlabRequestStart">
								<kendo:dataSource-transport>
									<kendo:dataSource-transport-read url="${elRateSlabReadUrl}/#=elrmid#" dataType="json" type="POST" contentType="application/json" />
									<kendo:dataSource-transport-create url="${elRateSlabCreateUrl}/#=elrmid#" dataType="json" type="GET" contentType="application/json" />
									<kendo:dataSource-transport-update url="${elRateSlabUpdateUrl}" dataType="json" type="GET" contentType="application/json" />
									<kendo:dataSource-transport-destroy url="${elRateSlabDeleteUrl}" dataType="json" type="GET" contentType="application/json" />
								</kendo:dataSource-transport>

								<kendo:dataSource-schema parse="rateMasterParse">
									<kendo:dataSource-schema-model id="elrsId">
										<kendo:dataSource-schema-model-fields>

											<kendo:dataSource-schema-model-field name="elrsId" />

											<kendo:dataSource-schema-model-field name="eltiId" />

											<kendo:dataSource-schema-model-field name="slabsNo" type="number">
												<kendo:dataSource-schema-model-field-validation min="1" />
											</kendo:dataSource-schema-model-field>

											<kendo:dataSource-schema-model-field name="slabType" type="String" defaultValue="Numeric" />

											<kendo:dataSource-schema-model-field name="slabRateType" type="String" defaultValue="Paise" />

											<kendo:dataSource-schema-model-field name="rate" type="number">
												<kendo:dataSource-schema-model-field-validation min="0" />
											</kendo:dataSource-schema-model-field>

											<kendo:dataSource-schema-model-field name="slabFrom" type="number">
												<kendo:dataSource-schema-model-field-validation min="0" max="999999" />
											</kendo:dataSource-schema-model-field>

											<kendo:dataSource-schema-model-field name="dummySlabFrom" type="String" />

											<kendo:dataSource-schema-model-field name="maxSlabToValue" type="boolean" />

											<kendo:dataSource-schema-model-field name="slabTo" type="number">
												<kendo:dataSource-schema-model-field-validation min="0" max="999999" />
											</kendo:dataSource-schema-model-field>

											<kendo:dataSource-schema-model-field name="dummySlabTo" type="String" />

											<kendo:dataSource-schema-model-field name="status" type="string" editable="true" />

										</kendo:dataSource-schema-model-fields>
									</kendo:dataSource-schema-model>
								</kendo:dataSource-schema>
							</kendo:dataSource>
						</kendo:grid>
					</div>
				</kendo:tabStrip-item-content>
			</kendo:tabStrip-item>


			<kendo:tabStrip-item text="TOD Rates">
				<div class='hideTodStrip' style="display: none;">
					<kendo:tabStrip-item-content>
						<br /> Select Slab Number :
						<kendo:dropDownList name="slabDropDown_#=elrmid#"
							dataBound="setFirstSelected" select="handleChange"
							dataTextField="slabsNo" dataValueField="elrsId"
							style="width:100px">
							<kendo:dataSource>
								<kendo:dataSource-transport>
									<kendo:dataSource-transport-read
										url="${elrateSlabDropDown}/#=elrmid#" type="POST"
										contentType="application/json" />
									<kendo:dataSource-transport-parameterMap>
										<script>
											function parameterMap(options) {
												return JSON.stringify(options);
											}
										</script>
									</kendo:dataSource-transport-parameterMap>
								</kendo:dataSource-transport>
							</kendo:dataSource>
						</kendo:dropDownList>
						<br>
						<br>

						<kendo:grid name="elTODRate_#=elrmid#" pageable="true"
							filterable="true" resizable="true" sortable="true"
							reorderable="true" selectable="true" scrollable="true"
							edit="elTODRateEditEvent" remove="TODRateDeleteEvent">
							<kendo:grid-pageable pageSize="10"></kendo:grid-pageable>
							<kendo:grid-filterable extra="false">
								<kendo:grid-filterable-operators>
									<kendo:grid-filterable-operators-string eq="Is equal to" />
								</kendo:grid-filterable-operators>
							</kendo:grid-filterable>
							<kendo:grid-editable mode="popup" />
							<kendo:grid-toolbar>
								<kendo:grid-toolbarItem name="create" text="TOD Rates" />
							</kendo:grid-toolbar>
							<kendo:grid-columns>

								<kendo:grid-column title="ELTR_ID" field="eltiId" hidden="true"
									width="70px" />

								<kendo:grid-column title="Slab Number *" field="elrsId" width="100px" filterable="false" >
									<kendo:grid-column-values value="${RateSlab}" />
								</kendo:grid-column>

								<kendo:grid-column title="From Time *" field="fromTime" format="{0:HH:mm tt}" editor="fromTimePicker" width="80px" filterable="false">
									<kendo:grid-column-filterable>
										<kendo:grid-column-filterable-ui>
											<script>
												function fromTimeFilter(element) {
													element.kendoTimePicker({});
												}
											</script>
										</kendo:grid-column-filterable-ui>
									</kendo:grid-column-filterable>
								</kendo:grid-column>

								<kendo:grid-column title="To Time *" field="toTime" format="{0:HH:mm tt}" editor="toTimePicker" width="80px" filterable="false">
									<kendo:grid-column-filterable>
										<kendo:grid-column-filterable-ui>
											<script>
												function toTimeFilter(element) {
													element.kendoTimePicker({});
												}
											</script>
										</kendo:grid-column-filterable-ui>
									</kendo:grid-column-filterable>
								</kendo:grid-column>

								<kendo:grid-column title="Valid From *" field="todValidFrom" format="{0:dd/MM/yyyy}" width="130px" filterable="false" />

								<kendo:grid-column title="Valid To *" field="todValidTo" format="{0:dd/MM/yyyy}" width="150px" filterable="false" />

								<kendo:grid-column title="Rate Type *" field="todRateType" width="100px" filterable="false">
									<kendo:grid-column-values value="${todRateType}" />
								</kendo:grid-column>

								<kendo:grid-column title="Incremental Rate *" field="incrementalRate" width="130px" filterable="false" />

								<kendo:grid-column title="TOD Status *" field="status"	width="100px" filterable="false">
									<kendo:grid-column-values value="${status}" />
								</kendo:grid-column>

								<kendo:grid-column title="&nbsp;" width="250px">
									<kendo:grid-column-command>
										<kendo:grid-column-commandItem name="edit" />
										<kendo:grid-column-commandItem name="destroy" />
										<kendo:grid-column-commandItem name="Change Status" click="elTODRatesStatusChange" />
									</kendo:grid-column-command>
								</kendo:grid-column>
							</kendo:grid-columns>

							<kendo:dataSource requestEnd="elTODRateRequestEnd">

								<kendo:dataSource-transport>
									<kendo:dataSource-transport-read
										url="${elTODRateReadUrl}/#=elrmid#" dataType="json"
										type="POST" contentType="application/json" />
									<kendo:dataSource-transport-create url="${elTODRateCreateUrl}"
										dataType="json" type="GET" contentType="application/json" />
									<kendo:dataSource-transport-update url="${elTODRateUpdateUrl}"
										dataType="json" type="GET" contentType="application/json" />
									<kendo:dataSource-transport-destroy url="${elTODRateDeleteUrl}"
										dataType="json" type="GET" contentType="application/json" />
								</kendo:dataSource-transport>

								<kendo:dataSource-schema parse="todRateParseFunction">
									<kendo:dataSource-schema-model id="eltiId">
										<kendo:dataSource-schema-model-fields>

											<kendo:dataSource-schema-model-field name="eltiId"
												type="number" />

											<kendo:dataSource-schema-model-field name="elrsId"
												type="number" />

											<kendo:dataSource-schema-model-field name="todRateType"
												type="String" defaultValue="Paise" />

											<kendo:dataSource-schema-model-field name="incrementalRate"
												type="number">
												<kendo:dataSource-schema-model-field-validation
													required="true"/>
											</kendo:dataSource-schema-model-field>

											<kendo:dataSource-schema-model-field name="fromTime">
												<kendo:dataSource-schema-model-field-validation
													required="true" />
											</kendo:dataSource-schema-model-field>

											<kendo:dataSource-schema-model-field name="toTime">
												<kendo:dataSource-schema-model-field-validation
													required="true" />
											</kendo:dataSource-schema-model-field>

											<kendo:dataSource-schema-model-field name="todValidFrom"
												type="date">
												<kendo:dataSource-schema-model-field-validation
													required="true" />
											</kendo:dataSource-schema-model-field>

											<kendo:dataSource-schema-model-field name="todValidTo"
												type="date">
												<kendo:dataSource-schema-model-field-validation
													required="true" />
											</kendo:dataSource-schema-model-field>

											<kendo:dataSource-schema-model-field name="status"
												type="string" />

										</kendo:dataSource-schema-model-fields>
									</kendo:dataSource-schema-model>
								</kendo:dataSource-schema>
							</kendo:dataSource>
						</kendo:grid>
					</kendo:tabStrip-item-content>
				</div>
			</kendo:tabStrip-item>

		</kendo:tabStrip-items>
	</kendo:tabStrip>
</kendo:grid-detailTemplate>

<div id="alertsBox" title="Alert"></div>

<div id="dialog-form" title="Reply to sender" style="width: auto; height: auto;display: none;">
	<form>
		<fieldset>
			<label for="name">Enter New Rate</label> <input type="text"
				name="newrate" id="newrate"
				class="text ui-widget-content ui-corner-all">
		</fieldset>
	</form>
</div>

<div id="dialog-formSplit" title="Enter New values" style="width: auto; height: auto;display: none;">
	<form id="splitform" style="width: 630px;height: 200px;">
		<table
			style="text-align: center; vertical-align: middle; width:300px;">
			<caption>Form 1</caption>
			<tr>
				<td>Slab Number</td>
				<td	>
					<kendo:numericTextBox type="number" name="slabsNo" id="slabsNo"
					class="text ui-widget-content ui-corner-all" readonly="readonly"/>
				</td>
			</tr>

			<tr>
				<td	>Rate</td>
				<td>
					<kendo:numericTextBox type="number" name="rate" id="rate"
					class="text ui-widget-content ui-corner-all"/>
				</td>
			</tr>

			<tr>
				<td>Slab From</td>
				<td>
					<kendo:numericTextBox type="number" name="slabFrom" id="slabFrom"
					class="text ui-widget-content ui-corner-all" readonly="readonly"/>
				</td>
			</tr>

			<tr>
				<td>Slab To</td>
				<td>
					<kendo:numericTextBox type="number" name="slabTo" id="slabTo"
					class="text ui-widget-content ui-corner-all"/>
				</td>
			</tr>
			<tr>
				<td><input type="hidden" name="hiddenelrsName"
					id="hiddenelrsId"></td>
			</tr>
		</table>

		<table
			style="text-align: center; vertical-align: middle; width: 300px; margin-left: 280px; margin-top: -134px">
			<caption>Form 2</caption>
			<tr>
				<td>Slab Number</td>
				<td>
					<kendo:numericTextBox type="number" name="slabsNo1" id="slabsNo1"
					class="text ui-widget-content ui-corner-all" readonly="readonly"/>
				</td>
			</tr>

			<tr>
				<td>Rate</td>
				<td>
					<kendo:numericTextBox type="number" name="rate1" id="rate1"
					class="text ui-widget-content ui-corner-all"/>
				</td>
			</tr>

			<tr>
				<td>Slab From</td>
				<td>
					<kendo:numericTextBox type="number" name="slabFrom1" id="slabFrom1"
					class="text ui-widget-content ui-corner-all"/>
				</td>
			</tr>

			<tr>
				<td>Slab To</td>
				<td>
					<kendo:numericTextBox type="number" name="slabTo1" id="slabTo1"
					class="text ui-widget-content ui-corner-all" readonly="readonly"/>
				</td>
			</tr>
		</table>
		<div style="margin-top: 10px; margin-left: 290px;">
	     <button type="submit" id="splitrate" class="k-button" style="padding-left: 10px; height: 36px; width: 70px;" onclick="splitRateMaster()">Split</button>
	     <div id='loader' style="display: none"> <img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;</div>
	     
        </div>
	</form>
</div>
<!-----------------DG Unit Calcualtion Div----------------------------------------->
<div id="dgCalculation" style="display: none;">
	<form id="dgCalculationform" data-role="validator"
		novalidate="novalidate">
		<div id="bcPreviousDiv" style="display: inline-block;" align="center">
			<table style="height: 135px;">
				<tr>
					
				</tr>
                     <tr>
					<td>Depricatation Cost</td>
					<td><kendo:numericTextBox name="derpriCost"
							id="derpriCost"></kendo:numericTextBox></td>
				</tr>
				<tr>
					<td>Maintainance Cost</td>
					<td><kendo:numericTextBox name="maintainceCost"
							id="maintainceCost"></kendo:numericTextBox></td>
				</tr>
				<tr>
					<td>Unit Consumed</td>
					<td><kendo:numericTextBox name="unitConsumed"
							id="unitConsumed"></kendo:numericTextBox></td>
				</tr>
							
					
					<%-- <tr>
                <td>From-Date</td>
					<td><kendo:datePicker format="dd/MM/yyyy  "
							name="from" id="from" parse="parse()"
							value="${today}" ></kendo:datePicker></td>			
								<tr> --%>				
			              </table>

		</div>
		<div id="presentCorrectBilldiv"
			style="float: right; vertical-align: middle; width: 45%;">
			<table style="height: 90px;">
				<tr>
				<td>Operational Cost</td>
					<td><kendo:numericTextBox name="operationalCost"
							id="operationalCost"></kendo:numericTextBox></td>		

				</tr>
				<tr>
               <td>Fuel Cost</td>
					<td><kendo:numericTextBox name="fuelCost" id="fuelCost"></kendo:numericTextBox></td>		
				</tr>  
			</table>
		</div>

		<div align="center" style="margin-top: 6px;">
			<table>
				<tr>
					<td class="left" align="center">
						<button type="submit" id="calculate" class="k-button"
							style="padding-left: 10px">Calculate
							</button>
							
							<span id=commitplaceholderCorrection style="display: none;"><img
						src="./resources/images/loadingimg.GIF"
						style="vertical-align: middle; margin-left: 50px" /> &nbsp;&nbsp;
						<img src="./resources/images/loading.GIF" alt="loading"
						style="vertical-align: middle margin-left: 50px" height="20px"
						width="200px; " /></span>
							
					</td>
				</tr>

			</table>
		</div>
	</form>
</div>



<!----------------------------------END of DIV----------------------------------->
<script>
	var nextslabsNo=0;
	var nextslabFrom = 0;
	var nextslabTo = 0;

	var elRateSlabMergeDialog;
	var elRateSlabSplitDialog;

	var elTariffID;
	var rateName1;
	var rateType1;
	var nextFromDate;
	var slabFromToCompare;
	var slabToToCompare;
	var editFloag = 0;
	var selectedDateFrom="";
	var rateMasterEditFloag = 0;
	$("#splitform").submit(function(e){
	    e.preventDefault();
	});

	function splitopen(){
		var elRateSlabSplitDialog=$( "#dialog-formSplit" );
		elRateSlabSplitDialog.kendoWindow({
					autoOpen : false,
					height : 'auto',
					width : 'auto',
					modal : true,
					draggable: true,
				    position: { top: 100 },
				    title: "Split Rate"				
					
				}).data("kendoWindow");
		
			elRateSlabSplitDialog.kendoWindow("open"); 

	}
	
	function splitRateMaster(){
		var slabsNo = $("#slabsNo").val();
		var rate = $("#rate").val();
		var slabFrom = $("#slabFrom").val();
		var slabTo = $("#slabTo").val();
		var hiddenelrsId = $("#hiddenelrsId").val();
		var slabsNo1 = $("#slabsNo1").val();
		var rate1 = $("#rate1").val();
		var slabFrom1 = $("#slabFrom1").val();
		var slabTo1 = $("#slabTo1").val();

		if (hiddenelrsId == "") {
			alert("form1 Slab number should not be empty.");
			return false;
		}

		if (slabsNo == "") {
			alert("form1 Slab number should not be empty.");
			return false;
		}

		if (rate == "") {
			alert("form1 Rate should not be empty.");
			return false;
		} else {
			if (parseInt(rate) < 0) {
				alert("form1 Rate should greater than 1.");
				return false;
			}

		}

		if (slabFrom == "") {
			alert("form1 Slab from should not be empty.");
			return false;
		}

		if (slabTo == "") {
			alert("form1 Slab To should not be empty.");
			return false;
		} else {
			if (parseInt(slabTo) <= parseInt(slabFrom)) {
				alert("form1 Slab To always greater than slab from");
				return false;
			}
		}

		if (slabsNo1 == "") {
			alert("form2 Slab number should not be empty.");
			return false;
		}

		if (rate1 == "") {
			alert("form2 Rate should not be empty.");
			return false;
		} else {
			if (rate1 < 0) {
				alert("form2 Rate should greater than 1.");
				return false;

			}
		}
		if (slabFrom1 == "") {
			alert("form2 Slab from should not be empty.");
			return false;
		} else {
			var form1SlabTo = slabTo;
			var form2SlabFrom = parseInt(form1SlabTo) + 1;
			if (parseInt(slabFrom1) != parseInt(form2SlabFrom)) {
				alert("Form 2 Slab From shoub be Form1 Slab To +1");
				return false;
			}

		}

		if (slabTo1 == "") {
			alert("form2 Slab To should not be empty.");
			return false;
		}
		
		  $('#splitrate').hide();
		  $('#loader').show();

		$.ajax({
					type : "POST",
					url : "./tariff/elrateslab/rateSlabSplit",
					async : false,
					data : {
						hiddenelrsId : hiddenelrsId,
						slabsNo : slabsNo,
						rate : rate,
						slabFrom : slabFrom,
						slabTo : slabTo,
						slabsNo1 : slabsNo1,
						rate1 : rate1,
						slabFrom1 : slabFrom1,
						slabTo1 : slabTo1,

					},
					success : function(response) {
						
						if (response.status == "SUCCESS") 
						{
							errorInfo = JSON.stringify(response.result);
							$("#alertsBox").html("");
							$("#alertsBox").html("" + errorInfo);
							$("#alertsBox").dialog({modal : true,});
							$('#elRateSlabGrid_'+ SelectedRowId).data().kendoGrid.dataSource.read();
						
							splitclose();
							$('#loader').hide();
							$('#splitrate').show();
							$('#rateMasterGrid').data('kendoGrid').refresh();
							$('#rateMasterGrid').data('kendoGrid').dataSource.read();
							return false;
							
						}

						if (response.status == "SINGLESLAB")
						{
							errorInfo = JSON.stringify(response.result);
							$("#alertsBox").html("");
							$("#alertsBox").html("" + errorInfo);
							$("#alertsBox").dialog({modal : true,});
							$('#elRateSlabGrid_'+ SelectedRowId).data().kendoGrid.dataSource.read();
							
							splitclose();
							
							$('#loader').hide();
							$('#splitrate').show();
							
							$('#rateMasterGrid').data('kendoGrid').refresh();
							$('#rateMasterGrid').data('kendoGrid').dataSource.read();
							return false;
						}
					}
				});	
	}
	
	function splitclose(){
		var elRateSlabSplitDialog=$( "#dialog-formSplit" );
		//elRateSlabSplitDialog = $("#dialog-formSplit")
		elRateSlabSplitDialog.kendoWindow({
					autoOpen : false,
					height : 'auto',
					width : 'auto',
					modal : true,
					draggable: true,
				    position: { top: 100 },
				    title: "Split Rate"	
		           }).data("kendoWindow");		
			elRateSlabSplitDialog.kendoWindow("close"); 
	}
	var globalElrsId = [];
	function selectSingleCheckBox(fieldId) {
		var temp = fieldId.split("_");
		if ($("#" + fieldId).prop('checked') == true) {
			globalElrsId.push(temp[1]);
		} else if ($("#" + fieldId).prop('checked') == false) {
			globalElrsId.splice($.inArray(temp[1], globalElrsId), 1);
		}

	}

	function CheckBoxDataBound(e)
	{
		if(rateMasterSlabType == 'Single Slab')
			{
			var grid = $("#elRateSlabGrid_" + SelectedRowId).data("kendoGrid");
			var data = this.dataSource.view();
			for (var i = 0; i < data.length; i++) {
				$(".k-grid-add", "#elRateSlabGrid_" + SelectedRowId).hide();
				$(".k-grid-Merge", "#elRateSlabGrid_" + SelectedRowId).hide();
				$(".k-grid-Split", "#elRateSlabGrid_" + SelectedRowId).hide();
			}
			}
		
		if (todTypeToHide === "None")
		{
			$('.hideTodStrip').hide();
			$('li[aria-controls="rateMasterTabStrip_' + SelectedRowId + '-2"]')
					.hide();
		} else {
			$('.hideTodStrip').show();
			$('li[aria-controls="rateMasterTabStrip_' + SelectedRowId + '-2"]')
					.show();
		}
		var grid = $("#elRateSlabGrid_" + SelectedRowId).data("kendoGrid");
		var gridData = grid._data;
		var i = 0;
		this.tbody.find("tr td:first-child").each(function(e) {
							$('<input type="checkbox" name="chkbox" id="singleSelectChkBx_'+ gridData[i].elrsId+ '" onclick="selectSingleCheckBox(this.id)"  />').appendTo(this);
							i++;
						});
	}

	elRateSlabMergeDialog = $("#dialog-form")
			.dialog(
					{
						autoOpen : false,
						height : 200,
						width : 200,
						modal : true,
						buttons : {
							"Save" : function() {
								var newrate = $("#newrate").val();
								

								if (newrate == "") {
									alert("New rate should not be empty");
									return false;
								}

								if(!$.isNumeric(newrate)){
									
									alert("Please enter Numbers Only ");
									return false;
							     }
								
								if (newrate <= 0) {
									alert("New rate should be greater than 1");
									return false;
								}
							

								$
										.ajax({
											type : "POST",
											data : {
												newrate : newrate,
											},
											url : "./tariff/elrateslab/getNewRate",
											success : function(response) {
												if (response.status == "SINGLESLAB") {
													errorInfo = JSON
															.stringify(response.result);

													$("#alertsBox").html("");
													$("#alertsBox").html(
															"" + errorInfo);
													$("#alertsBox")
															.dialog(
																	{
																		modal : true,
																		buttons : {
																			"Close" : function() {
																				$(
																						this)
																						.dialog(
																								"close");
																			}
																		}
																	});
													return false;
												}

												if (response.status == "SUCCESS") {
													errorInfo = JSON
															.stringify(response.result);
													$("#alertsBox").html("");
													$("#alertsBox").html(
															"" + errorInfo);
													$("#alertsBox")
															.dialog(
																	{
																		modal : true,
																		buttons : {
																			"Close" : function() {
																				$(
																						this)
																						.dialog(
																								"close");
																			}
																		}
																	});
													$(
															'#elRateSlabGrid_'
																	+ SelectedRowId)
															.data().kendoGrid.dataSource
															.read();
													return false;
												}
											}
										});

								elRateSlabMergeDialog.dialog("close");
							},
						},
						close : function() {
							elRateSlabMergeDialog.dialog("close");
						}
					});

	$("#rateMasterGrid")
			.on(
					"click",
					".k-grid-Merge",
					function() {
						var result=securityCheckForActionsForStatus("./Tariff/Electricity/RateSlab/mergeRateSlab"); 
						if(result=="success"){ 
						if ((rateMasterSlabType == 'Single Slab') && (parseInt(nextslabsNo) == 1))
						{
							var grid = $("#elRateSlabGrid_" + SelectedRowId).data("kendoGrid");
							grid.cancelRow();
							alert("Only one rate slab allowed for single slab rate master.");
							return false;
						}
                              
						if (globalElrsId.length <= 0)
						 {
							alert("Invalid Selection.");
							return false;
						 }
	                    
	                  if(globalElrsId.length > 2)
	                  {
	                	  alert("Invalid Selection.");
						   return false;
	                  }

						$.ajax({
									type : "POST",
									url : "./tariff/elrateslab/merge",
									data : {
										"elrsIds" : globalElrsId.toString(),
									},
									success : function(response) {
										if (response.status == "INPUT") {
											elRateSlabMergeDialog
													.dialog("open");
										}

										if (response.status == "SINGLESLAB") {
											errorInfo = JSON
													.stringify(response.result);

											$("#alertsBox").html("");
											$("#alertsBox")
													.html("" + errorInfo);
											$("#alertsBox").dialog({
																modal : true,
																buttons : {
																	"Close" : function() {
																		$(this)
																				.dialog(
																						"close");
																	}
																}
															});
											var rateSlabGrid = $('#elRateSlabGrid_'+ SelectedRowId).data().kendoGrid.dataSource.read();
											rateSlabGrid.refresh();
											elRateSlabMergeDialog.dialog("close");
											return false;
										}

										if (response.status == "SUCCESS") {
											errorInfo = JSON.stringify(response.result);
											$("#alertsBox").html("");
											$("#alertsBox").html("" + errorInfo);
											$("#alertsBox")
													.dialog(
															{
																modal : true,
																buttons : {
																	"Close" : function() {
																		$(this)
																				.dialog(
																						"close");
																	}
																}
															});
											var rateSlabGrid = $(
													'#elRateSlabGrid_'
															+ SelectedRowId)
													.data().kendoGrid.dataSource
													.read();
											rateSlabGrid.refresh();
											elRateSlabMergeDialog
													.dialog("close");
											return false;
										}
									}
								});
						elRateSlabMergeDialog.dialog("close");
						}
					});

	/***************************** For split ***************************  */
	var SelectedRowId = "";
	var todTypeToHide = "";
	var rateMasterSlabType = "";
	function 
	onChangeRateMaster(arg) {
		var gview = $("#rateMasterGrid").data("kendoGrid");
		var selectedItem = gview.dataItem(gview.select());
		SelectedRowId = selectedItem.elrmid;
		todTypeToHide = selectedItem.todType;
		rateMasterSlabType = selectedItem.rateType;
		this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));  	 
	     $(".k-master-row.k-state-selected").show();
	}

	function setFirstSelected(e) {
		this.select(0);
	}
	var elrsId = "";
	var slabsNo = "";
	var rate = "";
	var slabFrom = "";
	var slabTo = "";
	function splitFunction() {
		var result=securityCheckForActionsForStatus("./Tariff/Electricity/RateSlab/splitRateSlab"); 
		if(result=="success"){ 		
		if ((rateMasterSlabType == 'Single Slab')&& (parseInt(nextslabsNo) == 1)) 
		{
			var grid = $("#elRateSlabGrid_" + SelectedRowId).data("kendoGrid");
			grid.cancelRow();
			alert("Only one rate slab allowed for single slab rate master.");
			return false;
		}
		
		$("#hiddenelrsId").val(elrsId);
		$("input[name=slabsNo]").data("kendoNumericTextBox").value(slabsNo);
		var slabsNo1 = slabsNo + 1;
		$("input[name=slabsNo1]").data("kendoNumericTextBox").value(slabsNo1);
		$("input[name=rate]").data("kendoNumericTextBox").value(rate);
		$("input[name=slabFrom]").data("kendoNumericTextBox").value(slabFrom);
		$("input[name=slabTo1]").data("kendoNumericTextBox").value(slabTo);
		splitopen();
		}
	}

	function onGridChange() {
		var elRateSlabGrid = $("#elRateSlabGrid_" + SelectedRowId).data(
				"kendoGrid");
		var selectedRowItem = elRateSlabGrid.dataItem(elRateSlabGrid.select());
		elrsId = selectedRowItem.elrsId;
		slabsNo = selectedRowItem.slabsNo;
		rate = selectedRowItem.rate;
		slabFrom = selectedRowItem.slabFrom;
		slabTo = selectedRowItem.slabTo;
	}

	var SelectedelrsId = "";
	function handleChange(e) {
		var dataItem = this.dataItem(e.item.index());
		SelectedelrsId = dataItem.elrsId;
		if (dataItem.elrsId != "") {
			$('#elTODRate_' + SelectedRowId).data().kendoGrid.dataSource.read({
				elrmid : SelectedelrsId
			});
		}
	}

	function dropDownChecksEditor(container, options) {
		var res = (container.selector).split("=");
		var attribute = res[1].substring(0, res[1].length - 1);

		$(
				'<input data-text-field="name" data-value-field="value" data-bind="value:' + options.field + '" name = "'+attribute+'" id = "'+attribute+'Id"/>')
				.appendTo(container).kendoDropDownList({
					optionLabel : {
						name : "Select",
						value : "",
					},
					defaultValue : false,
					sortable : true,
					dataSource : {
						transport : {
							read : "${allChecksUrl}/" + attribute,
						}
					}
				});
		$('<span class="k-invalid-msg" data-for="'+attribute+'"></span>')
				.appendTo(container);
	}

	$("#rateMasterGrid").on(
			"click",
			"#temPID",
			function(e) {
				var button = $(this), enable = button.text() == "Active";
				var widget = $("#rateMasterGrid").data("kendoGrid");
				var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
				var result=securityCheckForActionsForStatus("./Tariff/Electricity/RateMaster/activitRateMaster"); 
				if(result=="success"){  
				if (enable) {
					$.ajax({
						type : "POST",
						url : "./tariff/tariffStatus/" + dataItem.elrmid
								+ "/activate",
								dataType : "text",
						success : function(response) {
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
							button.text('Inactive');
							$('#rateMasterGrid').data('kendoGrid').dataSource
									.read();
						}
					});
				} else {
					$.ajax({
						type : "POST",
						url : "./tariff/tariffStatus/" + dataItem.elrmid
								+ "/deactivate",
						dataType : "text",
						success : function(response) {
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
							button.text('Active');
							$('#rateMasterGrid').data('kendoGrid').dataSource
									.read();
						}
					});
				}
			}
			});

	/*------------------------- activate and deactive for child table ---------------------------  */

	var res = "";
	
	function ratemasterEvent(e) {

		/***************************  to remove the id from pop up  **********************/
		$('div[data-container-for="elrmid"]').remove();
		$('label[for="elrmid"]').closest('.k-edit-label').remove();

	 	$('div[data-container-for="tariffName"]').remove();
		$('label[for="tariffName"]').closest('.k-edit-label').remove();
 
		$(".k-edit-field").each(function() {
			$(this).find("#temPID").parent().remove();
		});

		$('label[for="status"]').parent().hide();
		$('div[data-container-for="status"]').hide();
		/************************* Button Alerts *************************/
		
		if (e.model.isNew()) 
		{
			 securityCheckForActions("./Tariff/Electricity/RateMaster/createRateMaster");
			$(".k-window-title").text("Add New Rate Master");
			$(".k-grid-update").text("Save");

			elTariffID = e.model.elTariffID;
			rateName1 = e.model.rateName;
			rateType1 = e.model.rateType;
			selectedDate = e.model.validFrom;
			selectedDateFrom = e.model.validFrom;
		} else 
		{
			
			securityCheckForActions("./Tariff/Electricity/RateMaster/updateRateMaster");
			rateMasterEditFloag = 1;
			
			  $(".k-edit-form-container").mouseenter(function() {
				   var validFrom = $('input[name="validFrom"]').kendoDatePicker().data("kendoDatePicker");
					validFrom.min($('input[name="validFrom"]').val());
					validFrom.max($('input[name="validFrom"]').val());
					
					 var validTo = $('input[name="validTo"]').kendoDatePicker().data("kendoDatePicker");
					 validTo.min($('input[name="validTo"]').val());
					 validTo.max($('input[name="validTo"]').val());
						
				}).mouseleave(function() {
				    var validFrom = $('input[name="validFrom"]').kendoDatePicker().data("kendoDatePicker");
					validFrom.min($('input[name="validFrom"]').val());
					validFrom.max($('input[name="validFrom"]').val());
					
					var validTo = $('input[name="validTo"]').kendoDatePicker().data("kendoDatePicker");
					validTo.min($('input[name="validTo"]').val());
					validTo.max($('input[name="validTo"]').val());
				});
			
			selectedDateFrom = e.model.validFrom;
			$(".k-window-title").text("Edit Rate Master Details");
		}
		
		var validFrom = $('input[name="validFrom"]').kendoDatePicker().data("kendoDatePicker");
		var validTo = $('input[name="validTo"]').kendoDatePicker().data("kendoDatePicker");
		
		$('input[name="validFrom"]').change(function() {
			validTo.min($('input[name="validFrom"]').val());
		});  
	}
	/********************** to hide the child table id ***************************/
	function elRateSlabEditEvent(e) {

		$('div[data-container-for="elrsId"]').remove();
		$('label[for="elrsId"]').closest('.k-edit-label').remove();

		$('div[data-container-for="status"]').remove();
		$('label[for="status"]').closest('.k-edit-label').remove();

		$('div[data-container-for="dummySlabTo"]').remove();
		$('label[for="dummySlabTo"]').closest('.k-edit-label').remove();

		$('div[data-container-for="dummySlabFrom"]').remove();
		$('label[for="dummySlabFrom"]').closest('.k-edit-label').remove();

		if (e.model.isNew())
		{
			securityCheckForActions("./Tariff/Electricity/RateSlab/createRateSlab");
/* 
			if (parseInt(nextslabTo) == 999999)
			{
				var grid = $("#elRateSlabGrid_" + SelectedRowId).data("kendoGrid");
				grid.cancelRow();
				alert("You can't the add the new record,Slab to Max is the last record.");
				return false;
			} */

			$(".k-window-title").text("Add New Rate Slab");
			$(".k-grid-update").text("Save");
		} else {
			  
			  $('div[data-container-for="slabsNo"]').remove();
			  $('label[for="slabsNo"]').closest('.k-edit-label').remove();
			  
			  $('div[data-container-for="slabType"]').remove();
			  $('label[for="slabType"]').closest('.k-edit-label').remove();
			  
			  $('div[data-container-for="slabRateType"]').remove();
			  $('label[for="slabRateType"]').closest('.k-edit-label').remove();
			  

				
			securityCheckForActions("./Tariff/Electricity/RateSlab/updateRateSlab");
			editFloag = 1;
			if (e.model.slabType == "Not applicable") {
				$('label[for="slabTo"]').hide();
				$('div[data-container-for="slabTo"]').hide();
				$('input[name="slabTo"]').prop('required', false);
				$('input[name="slabTo"]').val(null);

				$('label[for="slabFrom"]').hide();
				$('div[data-container-for="slabFrom"]').hide();
				$('input[name="slabFrom"]').prop('required', false);

				$('label[for="maxSlabToValue"]').hide();
				$('div[data-container-for="maxSlabToValue"]').hide();
				$('input[name="maxSlabToValue"]').prop('required', false);

			} else 
			{
               
				$('label[for="slabTo"]').hide();
				$('div[data-container-for="slabTo"]').hide();

				$('label[for="slabFrom"]').hide();
				$('div[data-container-for="slabFrom"]').hide();

				$('label[for="maxSlabToValue"]').hide();
				$('div[data-container-for="maxSlabToValue"]').hide();

			}
			if (e.model.slabTo == parseInt(999999)) {
				$('input[name="maxSlabToValue"]').prop('checked', true);
				$('label[for="slabTo"]').hide();
				$('div[data-container-for="slabTo"]').hide();
				$('input[name="slabTo"]').prop('required', false);
				$('input[name="slabTo"]').val(999999);
			}
			$(".k-window-title").text("Edit Rate Slab Details");
		}
		
		 $(".k-grid-cancel").click(function() {
			  var rateSlab = $("#elRateSlabGrid_" + SelectedRowId).data("kendoGrid");
				rateSlab.dataSource.read();
				rateSlab.refresh();  
 	     }); 
	}

	/********************** for TOD Rates ***************************/
	function elTODRateEditEvent(e) {
		$('div[data-container-for="eltiId"]').remove();
		$('label[for="eltiId"]').closest('.k-edit-label').remove();

		$('div[data-container-for="status"]').remove();
		$('label[for="status"]').closest('.k-edit-label').remove();

		$('div[data-container-for="elrsId"]').remove();
		$('label[for="elrsId"]').closest('.k-edit-label').remove();

		if (e.model.isNew()) {
			securityCheckForActions("./Tariff/Electricity/TODRate/createTODRate");
			
			if ((SelectedelrsId == "") || (SelectedelrsId == ' ')) {
				var grid = $("#elTODRate_" + SelectedRowId).data("kendoGrid");
				grid.cancelRow();
				alert("Please Select Slab Number");
				return false;
			}
			e.model.set("elrsId", SelectedelrsId);
			$(".k-window-title").text("Add New TOD Rate");
			$(".k-grid-update").text("Save");
		} else 
		{
			securityCheckForActions("./Tariff/Electricity/TODRate/updateTODRate");
			$(".k-window-title").text("Edit TOD Rate Details");
		}
		 
		var todValidFrom = $('input[name="todValidFrom"]').kendoDatePicker().data("kendoDatePicker");
		var todValidTo = $('input[name="todValidTo"]').kendoDatePicker().data("kendoDatePicker");
	}

	function fromTimePicker(container, options) {
		$(
				'<input name="From Time" data-text-field="' + options.field + '" data-value-field="' + options.field + '" data-bind="value:' + options.field + '" data-format="' + options.format + '" required="true"/>')
				.appendTo(container).kendoTimePicker({});

		$('<span class="k-invalid-msg" data-for="From Time"></span>').appendTo(
				container);
	}

	function toTimePicker(container, options) {
		$(
				'<input name="To Time" data-text-field="' + options.field + '" data-value-field="' + options.field + '" data-bind="value:' + options.field + '" data-format="' + options.format + '" required="true"/>')
				.appendTo(container).kendoTimePicker({});

		$('<span class="k-invalid-msg" data-for="To Time"></span>').appendTo(
				container);
	}

	function clearFilterRateMaster() {

		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
		var elRatemaster = $("#rateMasterGrid").data("kendoGrid");
		elRatemaster.dataSource.read();
		elRatemaster.refresh();
	}

	/***************************  Custom message validation  **********************/

	var seasonFromAddress = "";
	(function($, kendo) {
		$
				.extend(
						true,
						kendo.ui.validator,
						{
							rules : {
								tariffNameValidater : function(input, params) {
									if (input.attr("name") == "Tariff Name") {
										return $.trim(input.val()) !== "Select";
									}
									return true;
								},
								rateNameValidation : function(input, params) {

									if (input.attr("name") == "Rate Name") {
										return $.trim(input.val()) !== "Select";
									}
									return true;
								},
								rateNameCategoryValidation : function(input, params) {

									if (input.attr("name") == "Category") {
										return $.trim(input.val()) !== "Select";
									}
									return true;
								},
								
								descriptionValidation : function(input, params) {

									if (input.attr("name") == "rateDescription") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},
								rateofunit : function(input, params) {

									if (input.attr("name") == "rateUnit") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},

								rateType : function(input, params) {
									if (input.attr("name") == "rateType") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},

								rateUOM : function(input, params) {
									if (input.attr("name") == "rateUOM") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},
								
								stateNameValidator : function(input, params) {
									if (input.attr("name") == "stateName") {
										return $.trim(input.val()) !== "Select";
									}
									return true;
								},
								
/* 								addressSeasonTo2 : function(input, params) {
									if (input.filter("[name = 'validTo']").length
											&& input.val() != "") {
										var selectedDate = input.val();
										var contractStartDtVal = selectedDateFrom.getDate()
												+ '/'
												+ (selectedDateFrom.getMonth() + 1)
												+ '/'
												+ selectedDateFrom
														.getFullYear();
										var flagDate = false;
										if ($.datepicker.parseDate('dd/mm/yy',
												selectedDate) > $.datepicker
												.parseDate('dd/mm/yy',
														contractStartDtVal)) {
											flagDate = true;
										}
										return flagDate;
									}
									return true;
								}, */

								rateUOMValidation : function(input, params) {
									if (input.filter("[name='rateUOM']").length
											&& input.val() != "") {
										return /^[a-zA-Z0-9 ]{1,45}$/
												.test(input.val());
									}
									return true;
								},

								todRates : function(input, params) {
									if (input.attr("name") == "eltiId") {
										return $.trim(input.val()) !== "0";
									}
									return true;
								},
								
								/************** for Inner Grid Validation ************/
								slabNoValidator : function(input, params) {
									if (input.attr("name") == "slabsNo") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},
							   nextSlabNoValidator : function(input, params) {
									if (parseInt(editFloag) == 1) {

									} else {
										if ((input.attr("name") == "slabsNo")
												&& (parseInt(input.val()) != parseInt((nextslabsNo + 1)))) {
											return false;
										}
									}
									return true;
								}, 
								rateValidator : function(input, params) {
									if ((input.attr("name") == "rate")
											&& (input.val() == 0)) {
										return false;
									}
									return true;
								},
								
								slabToGreaterThanSlabFrom : function(input,
										params) {
									if (input.attr("name") == "slabTo") {
										slabToToCompare = input.val();
									}
									if (input.attr("name") == "slabFrom") {
										slabFromToCompare = input.val();
									}
									if (input.attr("name") == "slabTo") {
										if (parseInt(slabFromToCompare) > parseInt(slabToToCompare)) {
											return false;
										} else {
											return true;
										}
									}
									return true;
								},
								slabFromValidator : function(input, params) {

									if ((input.attr("name") == "slabFrom")
											&& (parseInt(input.val()) < 0)) {
										return false;
									}
									return true;
								},
								slabToValidator : function(input, params) {
									if ((input.attr("name") == "slabTo")&& (parseInt(input.val()) < 0)) {
										return false;
									}
									return true;
								},

								todValidFromDate : function(input, params) {
									if (input.filter("[name = 'todValidFrom']").length
											&& input.val() != "") {
										var selectedFromDate = input.val();
										seasonFromAddress = selectedFromDate;
										return true;
									}
									return true;
								},

								todValidToDate : function(input, params) {
									if (input.filter("[name = 'todValidTo']").length
											&& input.val() != "") {
										var selectedToDate = input.val();
										var flagDate = false;

										if ($.datepicker.parseDate('dd/mm/yy',
												selectedToDate) > $.datepicker
												.parseDate('dd/mm/yy',
														seasonFromAddress)) {
											flagDate = true;
										}
										return flagDate;
									}
									return true;
								},

							/*******  END FOR INNER GRID VALIODATION ***********/
							},
							//custom rules messages
							messages : {
								tariffNameValidater : "Tariff name is not selected",
								rateNameValidation : "Rate name should not be empty",
								rateNameCategoryValidation : "Category should not be empty",
								descriptionValidation : "Description should not be empty",
								rateofunit : "Rate per unit should not be empty",
								rateType : "Rate Type should not be empty",
								rateUOM : "UOM should not be empty",
								validatinForNumbers : "Only numbers are allowed,follwed by two decimal points",
								addressSeasonFrom : "From Date must be selected in the future",
								addressSeasonTo1 : "Select From date first before selecting To date and change To date accordingly",
								rateUOMValidation : "Rate UOM can contain alphabets and spaces but cannot allow numbers and other special characters and maximum 45 characters are allowed",
								addressSeasonTo2 : "To date should be after From date",
								todRates : "Select atleast one from TOD Rates",
								slabNoValidator : "Slab Number Cannot Be Empty",
								rateValidator : "Rate is required",
								slabFromValidator : "Slab From required",
								slabToValidator : "Slab To required",
								nextSlabNoValidator : "Next Slab Number should start with maximum number +1",
								slabToGreaterThanSlabFrom : "Slab To number always greater than Slab From",
								todValidFromDate : "From Date not be empty",
								todValidToDate : "To date should be after From date",
								stateNameValidator: "Select state name",
								}
						});
	})(jQuery, kendo);

	function onRequestStartRateMaster(e){
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();
	}
	
	function elRateSlabRequestStart(e){
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();
	}
	function onRequestEndRateMaster(r) {
		if (typeof r.response != 'undefined') {
			if (r.response.status == "FAIL") {

				errorInfo = "";

				for (var s = 0; s < r.response.result.length; s++) {
					errorInfo += (s + 1) + ". "+ r.response.result[s].defaultMessage + "<br>";

				}

				if (r.type == "create") {

					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Creating the Rate Master<br>" + errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
					$('#rateMasterGrid').data('kendoGrid').refresh();
					$('#rateMasterGrid').data('kendoGrid').dataSource.read();
					return false;
				}

				if (r.type == "update") {
					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Updating the Rate Master<br>" + errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
				}

				$('#rateMasterGrid').data('kendoGrid').refresh();
				$('#rateMasterGrid').data('kendoGrid').dataSource.read();
				return false;
			}

			if (r.response.status == "CHILD") {

				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Can't Delete Rate Master, Child Record Found");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				$('#rateMasterGrid').data('kendoGrid').refresh();
				$('#rateMasterGrid').data('kendoGrid').dataSource.read();
				return false;
			}

			//FOR Date validation for slabs

			if (r.response.status == "SINGLESLAB") {

				errorInfo = JSON.stringify(r.response.result);

				$("#alertsBox").html("");
				$("#alertsBox").html("" + errorInfo);
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				$('#rateMasterGrid').data('kendoGrid').refresh();
				$('#rateMasterGrid').data('kendoGrid').dataSource.read();
				return false;
			}

			else if (r.response.status == "INVALID") {

				errorInfo = "";

				errorInfo = r.response.result.invalid;

				if (r.type == "create") {
					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Creating the Rate Master<br>" + errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
				}
				$('#rateMasterGrid').data('kendoGrid').refresh();
				$('#rateMasterGrid').data('kendoGrid').dataSource.read();
				return false;
			}

			else if (r.response.status == "EXCEPTION") {

				errorInfo = "";

				errorInfo = r.response.result.exception;


				if (r.type == "update") {
					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Updating the Rate Master<br>" + errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
				}

				$('#rateMasterGrid').data('kendoGrid').refresh();
				$('#rateMasterGrid').data('kendoGrid').dataSource.read();
			}

			if (r.type == "update") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Rate Master updated successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				$('#rateMasterGrid').data('kendoGrid').refresh();
				$('#rateMasterGrid').data('kendoGrid').dataSource.read();
			}
			
			else if (r.type == "create") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Rate Master created successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				
				var grid = $("#rateMasterGrid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
				
			}
			
			else if (r.type == "destroy")
			{
				$("#alertsBox").html("");
				$("#alertsBox").html("Rate Master delete successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				$('#rateMasterGrid').data('kendoGrid').refresh();
				$('#rateMasterGrid').data('kendoGrid').dataSource.read();
			}
		}
	}
	/************************************* for inner rate slab request *********************************/

	function elRateSlabRequestEnd(e) {
		var rateSlab = $("#elRateSlabGrid_" + SelectedRowId).data("kendoGrid");
		var rateMaster = $("#rateMasterGrid").data("kendoGrid");

		if (typeof e.response != 'undefined') {
			if (e.response.status == "CHILD") {
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Can't Delete Rate Master, Child Record Found");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				rateSlab.refresh();
				rateSlab.dataSource.read();
				return false;
			} 
			
			else if (e.response.status == "SINGLESLAB") {

				errorInfo = JSON.stringify(e.response.result);

				$("#alertsBox").html("");
				$("#alertsBox").html("" + errorInfo);
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				rateSlab.dataSource.read();
				rateSlab.refresh();
				return false;
			}
			
			else if (e.type == "create") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Rate Slab Created Successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});

				$.ajax({
					url : "${elrateSlabDropDown}/" + SelectedRowId,
					type : 'GET',
					dataType : "json",
					contentType : "application/json; charset=utf-8",
					success : function(result) {
						var dropdownlist = $("#slabDropDown_" + SelectedRowId).data("kendoDropDownList");
						dropdownlist.dataSource.data(result);
						dropdownlist.refresh();
					},
				});
				rateSlab.dataSource.read();
				rateSlab.refresh();
				 /* rateMaster.dataSource.read();
				rateMaster.refresh();; */ 
			}

			else if (e.type == "update") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Rate Slab updated successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});

				rateSlab.dataSource.read();
				rateSlab.refresh();
				/* rateMaster.dataSource.read();
				rateMaster.refresh();; */
			}

			else if (e.type == "destroy") 
			{
				$("#alertsBox").html("");
				$("#alertsBox").html("Rate Slab deleted successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						 	window.location.reload();

						}
					}
				});
								
				$.ajax({
					url : "${elrateSlabDropDown}/" + SelectedRowId,
					type : 'GET',
					dataType : "json",
					contentType : "application/json; charset=utf-8",
					success : function(result) {
						var dropdownlist = $("#slabDropDown_" + SelectedRowId).data("kendoDropDownList");
						dropdownlist.dataSource.data(result);
						dropdownlist.refresh();
					},
				});
			    rateSlab.dataSource.read();
				rateSlab.refresh(); 
				rateMaster.dataSource.read();
				rateMaster.refresh();

			}
		}

	}

	/************************************* for inner rate slab request *********************************/

	function elTODRateRequestEnd(e) {
		var todRates = $("#elTODRate_" + SelectedRowId).data("kendoGrid");
		var rateMaster = $('#rateMasterGrid').data("kendoGrid");
		if (typeof e.response != 'undefined') {

			if (e.response.status == "SINGLESLAB") {

				errorInfo = JSON.stringify(e.response.result);

				$("#alertsBox").html("");
				$("#alertsBox").html("" + errorInfo);
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				todRates.refresh();
				todRates.dataSource.read();
				/* rateMaster.dataSource.read();
				rateMaster.refresh();
				return false; */
			}

			else if (e.type == "create") {
				$("#alertsBox").html("");
				$("#alertsBox").html("TOD Rates Created Successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				todRates.refresh();
				todRates.dataSource.read();
				/* 	rateMaster.dataSource.read();
					rateMaster.refresh();;
					return false; */
			}

			else if (e.type == "update") {
				$("#alertsBox").html("");
				$("#alertsBox").html("TOD Rates updated successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});

				todRates.refresh();
				todRates.dataSource.read();
				/* rateMaster.dataSource.read();
				rateMaster.refresh();;
				return false; */
			}

			else if (e.type == "destroy") {
				$("#alertsBox").html("");
				$("#alertsBox").html("TOD Rates deleted successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				todRates.refresh();
				todRates.dataSource.read();
				rateMaster.dataSource.read();
				rateMaster.refresh();
				return false;
			}
		}
	}

	//for parsing timestamp 
	function todRateParseFunction(response) {
		$.each(response, function(idx, elem) {
			if (elem.fromTime && typeof elem.fromTime === "string") {
				elem.fromTime = kendo.parseDate(elem.fromTime,"yyyy-MM-ddTHH:mm:ss.fffZ");
			}

			if (elem.toTime && typeof elem.toTime === "string") {
				elem.toTime = kendo.parseDate(elem.toTime,"yyyy-MM-ddTHH:mm:ss.fffZ");
			}
		});
		return response;
	}

	function elRateSlabStatusChange() {
		var result=securityCheckForActionsForStatus("./Tariff/Electricity/RateSlab/activitRateSlab"); 
		if(result=="success"){ 
		var elrsId = "";
		var gridParameter = $("#elRateSlabGrid_" + SelectedRowId).data(
				"kendoGrid");
		var selectedAddressItem = gridParameter
				.dataItem(gridParameter.select());
		elrsId = selectedAddressItem.elrsId;
		$
				.ajax({
					type : "POST",
					url : "./tariff/elRateSlabStatusUpdateFromInnerGrid/"
							+ elrsId,
					dataType : "text",		
					success : function(response) {
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
						$('#elRateSlabGrid_' + SelectedRowId).data('kendoGrid').dataSource
								.read();
					}
				});
		}
	}

	function elTODRatesStatusChange() {
		var result=securityCheckForActionsForStatus("./Tariff/Electricity/TODRate/activitTODRate"); 
		if(result=="success"){ 
		var eltiId = "";
		var gridParameter = $("#elTODRate_" + SelectedRowId).data("kendoGrid");
		var selectedAddressItem = gridParameter.dataItem(gridParameter.select());
		eltiId = selectedAddressItem.eltiId;
		$.ajax({
			type : "POST",
			url : "./tariff/elTODRatesStatusUpdateFromInnerGrid/" + eltiId,
			dataType : "text",
			success : function(response) {
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
				$('#elTODRate_' + SelectedRowId).data('kendoGrid').dataSource
						.read();
			}
		});
	 }
	}
/* 
	function toTariffMasterEditor(container, options) {
		$(
				'<input name="Tariff Name" id="tariffName" data-text-field="tariffName" data-value-field="elTariffID" data-bind="value:' + options.field + '" required="true"/>')
				.appendTo(container).kendoDropDownList({
					autoBind : false,
					placeholder : "Select Tariff Name",
					dataSource : {
						transport : {
							read : "${toTariffMasterComboBoxUrl}"
						}
					},
					change : function(e) {
						if (this.value() && this.selectedIndex == -1) {
							alert("Tariff Name doesn't exist!");
							$("#tariffName").data("kendoComboBox").value("");
						}
						elTariffID = this.dataItem().elTariffID;
					}
				});

		$('<span class="k-invalid-msg" data-for="Tariff Name"></span>')
				.appendTo(container);
	} */

	function toRateNameEditor(container, options) {
		var res = (container.selector).split("=");
		var rateName = res[1].substring(0, res[1].length - 1);
		$(
				"<select name='Rate Name'id='rateName' data-bind='value:" + rateName + "'required='true'/>")
				.appendTo(container).kendoDropDownList({
					dataTextField : "text",
					dataValueField : "value",
					placeholder : "Select Rate Name",
					dataSource : {
						transport : {
							read : "${allChecksUrl}/" + rateName,
						}
					},
					change : function(e) {
						rateName1 = this.dataItem().text;
					}
				});

		$('<span class="k-invalid-msg" data-for="Rate Name"></span>').appendTo(
				container);
	}

	function toRateTypeEditor(container, options) {
		var res = (container.selector).split("=");
		var rateType = res[1].substring(0, res[1].length - 1);
		$(
				"<select name='Rate Type' id='rateType' data-bind='value:" + rateType + "'required='true'/>")
				.appendTo(container).kendoDropDownList({
					dataTextField : "text",
					dataValueField : "value",
					placeholder : "Select Rate Type",
					dataSource : {
						transport : {
							read : "${allChecksUrl}/" + rateType,
						}
					},
					change : function(e) {
						if (this.value() && this.selectedIndex == -1) {
							alert("Rate Type doesn't exist!");
							$("#rateType").data("kendoComboBox").value("");
						}
						rateType1 = this.dataItem().text;
					}
				});

		$('<span class="k-invalid-msg" data-for="Rate Type"></span>').appendTo(
				container);
	}

	function toRateUOMEditor(container, options) {
		var res = (container.selector).split("=");
		var rateUOM = res[1].substring(0, res[1].length - 1);
		$(
				"<select name='Rate UOM' data-bind='value:" + rateUOM + "'required='true'/>")
				.appendTo(container).kendoDropDownList({
					dataTextField : "text",
					dataValueField : "value",
					placeholder : "Select Rate UOM",
					dataSource : {
						transport : {
							read : "${allChecksUrl}/" + rateUOM,
						}
					},
					change : function(e) {
						if (this.value() && this.selectedIndex == -1) {
							alert("Staff doesn't exist!");
							$("#toStore").data("kendoComboBox").value("");
						}
					}
				});

		$('<span class="k-invalid-msg" data-for="Rate UOM"></span>').appendTo(
				container);
	}

	function toTODTypeEditor(container, options) {
		var res = (container.selector).split("=");
		var todType = res[1].substring(0, res[1].length - 1);
		$(
				"<select name='TOD Type' data-bind='value:" + todType + "'required='true'/>")
				.appendTo(container).kendoDropDownList({
					dataTextField : "text",
					dataValueField : "value",
					placeholder : "Select TOD Type",
					dataSource : {
						transport : {
							read : "${allChecksUrl}/" + todType,
						}
					}
				});

		$('<span class="k-invalid-msg" data-for="TOD Type"></span>').appendTo(
				container);
	}

	 function rateMasterParse (response) {
		$.each(response, function(idx, elem) {
			$.each(elem, function(idx1, elem1) {
				if (idx1 == "elrsId") {
					if (elem1 == null) {
						return false;
					} else {
						return true;
					}
				}

				if (idx1 == "slabsNo") {
					nextslabsNo = elem1;
				}

				if (idx1 == "slabFrom") {
					nextslabFrom = elem1;
				}

				if (idx1 == "slabTo") {
					nextslabTo = elem1;
				}
			});

		});
		return response;
	} 

	$("#rateMasterGrid").on("click", ".k-grid-showAllRateMasters", function(e) {
		var showAll = "showAll";

		$.ajax({
			url : "./elRateMaster/read",
			type : 'GET',
			dataType : "json",
			data : {
				showAll : showAll
			},
			contentType : "application/json; charset=utf-8",
			success : function(result) {
				var grid = $("#rateMasterGrid").getKendoGrid();
				var data = new kendo.data.DataSource();
				grid.dataSource.data(result);
				grid.refresh();
			},
		});
	});

	function validFromEditor(container, options) {
		$(
				'<input name="Valid From" data-value-field="' + options.field + '" data-bind="value:' + options.field + '" data-format="' + options.format + '" required="true"/>')
				.appendTo(container)
				.kendoDatePicker(
						{
							change : function(e) {
								if (parseInt(rateMasterEditFloag) === 1)
								{

								} 
								else {
									 
									/* $.ajax({
										type : "GET",
										url : '${getMaxDate}',
										dataType : "JSON",
										async : false,
										data : {
											rateName : rateName1,
											elTariffID : elTariffID,
											rateType : rateType1,
										},
										success : function(response)
										{
											nextFromDate = response;
										}
									}); */
                                  /* if(nextFromDate!=null)
                                    {
                                        selectedDateFrom = this.value();
  										var contractStartDtVal = selectedDateFrom.getFullYear()+ '-'+ (selectedDateFrom.getMonth() + 1) + '-' + selectedDateFrom.getDate();
  										var reqdt = new Date(nextFromDate);
  										if (reqdt != 'Invalid Date') {
  											var reqDateVal = reqdt.getFullYear()+ '-' + (reqdt.getMonth() + 1)+ '-' + reqdt.getDate();
  											if (contractStartDtVal != reqDateVal)
  											{
  												alert("The valid from date should be "+ reqDateVal);
  												return false;
  											}
  										}
                                    } */
								}
							}
						});
		$('<span class="k-invalid-msg" data-for="Valid From"></span>').appendTo(container);
	}
	$(document)
			.on('change','input[name="maxSlabToValue"]',
					function() {
						var test = $('input[name="maxSlabToValue"]:checked').length ? $('input[name="maxSlabToValue"]:checked').val(): '';
						if (test == "")
						{
							$('label[for="slabTo"]').parent("div").show();
							$('div[data-container-for="slabTo"]').show();
						}
						if (test == "on") 
						{
							$('label[for="slabTo"]').parent("div").hide();
							$('div[data-container-for="slabTo"]').hide();
							$('input[name="slabTo"]').prop('required', false);
							$('input[name="slabTo"]').val(999999);
						}
					});

	    var slabTypeToValidate = "";
	    function rateSlabTypeEditor(container, options) {
		var res = (container.selector).split("=");
		var slabType = res[1].substring(0, res[1].length - 1);
		$(
				"<select name='Slab Type' id='slabType' data-bind='value:" + slabType + "'required='true'/>")
				.appendTo(container)
				.kendoDropDownList(
						{
							dataTextField : "text",
							dataValueField : "value",
							placeholder : "Select Slab Type",
							dataSource : {
								transport : {
									read : "${allChecksUrl}/" + slabType,
								}
							},
							change : function(e) {
								if (this.value() && this.selectedIndex == -1) {
									alert("Slab Type doesn't exist!");
									$("#slabType").data("kendoComboBox").value(
											"");
								}
								slabTypeToValidate = this.dataItem().text;
								if (slabTypeToValidate == "Not applicable" || slabTypeToValidate=="Per Month") {
									$('label[for="slabTo"]').parent("div")
											.hide();
									$('div[data-container-for="slabTo"]')
											.hide();
									$('input[name="slabTo"]').prop('required',
											false);
									$('input[name="slabTo"]').val(null);

									$('label[for="slabFrom"]').parent("div")
											.hide();
									$('div[data-container-for="slabFrom"]')
											.hide();
									$('input[name="slabFrom"]').prop(
											'required', false);

									$('label[for="maxSlabToValue"]').parent(
											"div").hide();
									$(
											'div[data-container-for="maxSlabToValue"]')
											.hide();
									$('input[name="maxSlabToValue"]').prop(
											'required', false);

								} else {

									$('label[for="slabTo"]').parent("div")
											.show();
									$('div[data-container-for="slabTo"]')
											.show();

									$('label[for="slabFrom"]').parent("div")
											.show();
									$('div[data-container-for="slabFrom"]')
											.show();

									$('label[for="maxSlabToValue"]').parent(
											"div").show();
									$(
											'div[data-container-for="maxSlabToValue"]')
											.show();
								}
							}
						});

		$('<span class="k-invalid-msg" data-for="Slab Type"></span>').appendTo(
				container);
	}

	 var stateName='';
	 function stateNameEditor(container, options)
	 {
		 $('<input name = "stateName" data-text-field="stateName" data-value-field="stateName" data-bind="value:' + options.field + '"/>').appendTo(container).kendoDropDownList
			({
				optionLabel : 
				{
					stateName : "Select",
					value : "Select",
				},
			    defaultValue : false,
				sortable : true,
				dataSource :{
					transport :{
						 read : "${stateNamesUrl}"
					}
				},
				change : function (e) {
				stateName = this.text();
				dropDownDataSource = new kendo.data.DataSource({
			        transport: {
			            read: {
			            	url : "./rateNames/getAlltariffNames/"+stateName, 
			                dataType: "json",
			                type    : 'GET'
			            }
			        }
			    
			    });
				
				$("#tariffNameDropDown").kendoDropDownList({
			        dataSource    : dropDownDataSource,
			        dataTextField : "tariffName",
			        dataValueField: "elTariffID",
			        optionLabel : 
					{
			        	tariffName : "Select",
			        	elTariffID : "Select",
					},
			        change : function(e)
					{
			        	elTariffID = this.value();
					}
			    });
			 }
			});
			$('<span class="k-invalid-msg" data-for="stateName"></span>').appendTo(container);
	}
	 
 function tariffNameEditor(container, options)
 {
	 $('<input name="Tariff Name" required="true" id="tariffNameDropDown" data-text-field="tariffName" data-value-field="elTariffID" data-bind="value:' + options.field + '"/>').appendTo(container).kendoDropDownList
		({
			dataSource :{
				transport :{
					 read : "${allTariffMasters}"
				}
			}, 
		});
		$('<span class="k-invalid-msg" data-for="Tariff Name"></span>').appendTo(container);
} 
 
 function rateMasterDeleteEvent(){
		securityCheckForActions("./Tariff/Electricity/RateMaster/deleteRateMaster");
		var conf = confirm("Are you sure want to delete this Rate Master?");
		    if(!conf){
		    $('#rateMasterGrid').data().kendoGrid.dataSource.read();
		    throw new Error('deletion aborted');
		     }
	}
 
 function rateSlabDeleteEvent(){
		securityCheckForActions("./Tariff/Electricity/RateSlab/deleteRateSlab");
		var conf = confirm("Are you sure want to delete this Rate Slab?");
		    if(!conf){
		    $("#elRateSlabGrid_" + SelectedRowId).data().kendoGrid.dataSource.read();
		    throw new Error('deletion aborted');
		     }
	}
 
 function TODRateDeleteEvent(){
		securityCheckForActions("./Tariff/Electricity/TODRate/deleteTODRate");
		var conf = confirm("Are you sure want to delete this TOD Rate?");
		    if(!conf){
		    $("#elRateSlabGrid_" + SelectedRowId).data().kendoGrid.dataSource.read();
		    throw new Error('deletion aborted');
		     }
	}
 
 $("#rateMasterGrid").on("click", ".k-grid-generateDGUnit", function(e) {
		

		 var bbDialog = $("#dgCalculation");

		bbDialog.kendoWindow({
			width : "auto",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Calculate DG Unit"
		}).data("kendoWindow").center().open();

		bbDialog.kendoWindow("open");

		/* var dropdownlist1 = $("#serviceTypeList").data("kendoDropDownList");
		dropdownlist1.value("");
		var dropdownlist2 = $("#blockName").data("kendoDropDownList");
		dropdownlist2.value("");
		var presentreading = $("#consumptionUnit");
		presentreading.val("");
		var presentreading = $("#presentdate");
		presentreading.val("");
		  $('#propmultiselect1').remove();
		$('#propmultiselect2').remove(); 
		$('#propmultiselect').append("<td id='propmultiselect1'>Property Name </td><td id='propmultiselect2'><select id='propertyName' name='propertyName'  multiple='multiple' class='k-widget k-multiselect' data-placeholder='Select Properyty...' style='width:150px;'/> </td>"); */
		 /* var EmptyArray = new Array();
		var ddlMulti = $('#propertyName').data('kendoMultiSelect');
		ddlMulti.value(EmptyArray);   */ 

	});
 
 $("#dgCalculationform").submit(function(e) {
		e.preventDefault();
	});

	/* $(function() {
		var container = $("#addform");
		kendo.init(container);
		container.kendoValidator({

		});
	}); */
	 $("#dgCalculationform").kendoValidator({
      messages: {
          // defines a message for the 'custom' validation rule
          custom: "Please enter valid value for my custom rule",

          // overrides the built-in message for the required rule
          required: "My custom required message",
              minlen:1
          // overrides the built-in message for the email rule
          // with a custom function that returns the actual message
          
      },
      rules: {
     	 customRule1: function(input){
              // all of the input must have a value
              return $.trim(input.val()) !== "";
            },
      }
 });

 function getMessage(input) {
   return input.data("message");
 }
 var addvalidator = $("#dgCalculationform").kendoValidator().data("kendoValidator");
 $("#calculate").on("click", function() {
	    if (addvalidator.validate()) {
	        
	    	claculateDGUnit();
	    }
	});
function claculateDGUnit(){
	var operationalCost=$("#operationalCost").val();
	var depriciationCost=$("#derpriCost").val();
	var fuleCost=$("#fuelCost").val();
	//var validTo=$("#valid").val();
	//var validfrom=$("#from").val();
	var maintainanceCost=$("#maintainceCost").val();
	var unitConsumed=$("#unitConsumed").val();
	
	$.ajax({
		type : "GET",
		url : "./ratMaster/claculateDGUnit",
		dataType : "text",
		data : {
			operationalCost : operationalCost,
			depriciationCost : depriciationCost,
			fuleCost : fuleCost,
			maintainanceCost:maintainanceCost,
			unitConsumed:unitConsumed,
		},
		success : function(response) {
			alert("Updated Successfully");
			$('#rateMasterGrid').data('kendoGrid').refresh();
			$('#rateMasterGrid').data('kendoGrid').dataSource.read();
             closeDGDiv();
			}
	});
}
 function closeDGDiv(){
	 var bbDialog = $("#dgCalculation");

		bbDialog.kendoWindow({
			width : "auto",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Calculate DG Unit"
		}).data("kendoWindow").center().close();
		bbDialog.kendoWindow("close"); 
 }
 $("#rateMasterGrid").on("click", ".k-grid-showDGUnit", function(e) {		
     
     $.ajax({
			url : "./ratMaster/showDGUnit",
			type : 'GET',
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			success : function(result) {
				var grid = $("#rateMasterGrid").getKendoGrid();
				var data = new kendo.data.DataSource();
				grid.dataSource.data(result);
				grid.refresh();
			},
		});

	});
 
	function rateNameCategoryEditor(container, options) {
		var res = (container.selector).split("=");
		var rateNameCategory = res[1].substring(0, res[1].length - 1);
		$("<select name='Category' data-bind='value:" + rateNameCategory + "'required='true'/>")
				.appendTo(container).kendoDropDownList({
					dataTextField : "text",
					dataValueField : "value",
					placeholder : "Select Category",
					dataSource : {
						transport : {
							read : "${allChecksUrl}/" + rateNameCategory,
						}
					},
					change : function(e) {
						category = this.dataItem().text;
						$.ajax({
							type : "GET",
							url : '${getMaxDate}',
							dataType : "JSON",
							async : false,
							data : {
								stateName : stateName,
								elTariffID : elTariffID,
								rateName : rateName1,
								category : category,
							},
							success : function(response)
							{
							/* 	 var minDate = kendo.toString(kendo.parseDate(response.minDate), 'yyyy-MM-dd');
								 var min = new Date(minDate);
								 var validFrom = $('input[name="validFrom"]').kendoDatePicker({
										min:min,
									}).data("kendoDatePicker");
 */							}
						});
					}
				});
		$('<span class="k-invalid-msg" data-for="Category"></span>').appendTo(container);
	}
	
</script>
<style>

.k-datepicker span {
	width: 53%;
}
.k-input{
background-color: white !important;
}
.k-picker-wrap{
width: 133px !important;
}
</style>
