<%@include file="/common/taglibs.jsp"%>

<c:url value="/commonServiceRateMaster/read" var="commonServiceRatemasterReadUrl" />
<c:url value="/commonServiceRateMaster/create" var="commonServiceRatemasterCreateUrl" />
<c:url value="/commonServiceRateMaster/update" var="commonServiceRatemasterUpdateUrl" />
<c:url value="/commonServiceRateMaster/destroy" var="commonServiceRatemasterDestroyUrl" />
<c:url value="/common/getAllChecks" var="allChecksUrl" />
<c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />
<c:url value="/person/getAllAttributeValues" var="filterAutoCompleteUrl" />
<c:url value="/commonServiceRateMaster/getToTariffMasterComboBoxUrl" var="toTariffMasterComboBoxUrl" />
<c:url value="/common/getAllChecks" var="allChecksUrl" />

<!-- for rate slabs -->
<c:url value="/tariff/commonServiceRateslab/read" var="commonServiceRateSlabReadUrl" />
<c:url value="/tariff/commonServiceRateslab/create" var="commonServiceRateSlabCreateUrl" />
<c:url value="/tariff/commonServiceRateslab/update" var="commonServiceRateSlabUpdateUrl" />
<c:url value="/tariff/commonServiceRateslab/destroy" var="commonServiceRateSlabDeleteUrl" />

<!-- for TOD Rates -->
<c:url value="/tariff/commonServiceTODRate/create" var="commonServiceTODRateCreateUrl" />
<c:url value="/tariff/commonServiceTODRate/read" var="commonServiceTODRateReadUrl" />
<c:url value="/tariff/commonServiceTODRate/update" var="commonServiceTODRateUpdateUrl" />
<c:url value="/tariff/commonServiceTODRate/destroy" var="commonServiceTODRateDeleteUrl" />
<c:url value="/tariff/commonServiceTODRate/categories" var="commonServiceRateSlabDropDown" />
<c:url value="/commonServiceTariff/getMaxDate" var="getMaxDate"></c:url>

<!-- Filters Data url's -->
<c:url value="/tariff/filter" var="commonFilterUrl" />
<c:url value="/tariffNameToFilter/filter" var="csTariffNameToFilter" />


<!-- for merging ids -->

<kendo:grid name="commonServiceRateMasterGrid" remove="commonServiceRateMasterDeleteEvent" resizable="true" pageable="true"  selectable="true" edit="commonServiceRatemasterEvent" change="commonServiceChangeRateMaster" detailTemplate="commonServiceRateMasterSubGrid" sortable="true" scrollable="true"
	groupable="true">
	
	<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" ></kendo:grid-pageable>
	<kendo:grid-filterable extra="false">
		<kendo:grid-filterable-operators>
			<kendo:grid-filterable-operators-string eq="Is equal to" />
		</kendo:grid-filterable-operators>

	</kendo:grid-filterable>
	<kendo:grid-editable mode="popup"
		confirmation="Are you sure you want to remove this Rate Master?" />
	<kendo:grid-toolbar>
		<kendo:grid-toolbarItem name="create" text="Add Rate Master" />
		<kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=commonServiceClearFilterRateMaster()><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>"/>
		<kendo:grid-toolbarItem name="commonServiceShowAllRateMasters" text="Show All" />
	</kendo:grid-toolbar>
	<kendo:grid-columns>

		<kendo:grid-column title="ELRM_ID" field="csRmId" width="110px" hidden="true" />
		
		<kendo:grid-column title="Tariff Master *" field="csTariffName" width="100px">
		
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function csTariffNameFilter(element) {
							element
									.kendoAutoComplete({
										placeholder : "Enter Tariff Name",
										dataType : 'JSON',
										dataSource : {
											transport : {
												read : "${csTariffNameToFilter}/csTariffName"
											}
										}
									});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>
		
		<kendo:grid-column title="Tariff Master *" field="csTariffId" editor="commonServiceTariffMasterEditor" width="100px" hidden="true"> 
		</kendo:grid-column>

		<kendo:grid-column title="Rate&nbsp;Name*" field="csRateName" editor="commonServiceRateNameEditor" width="85px">
			<kendo:grid-column-values value="${csRateName}" />
		</kendo:grid-column>

		<kendo:grid-column title="Rate&nbsp;Description*"
			field="csRateDescription" filterable="true" width="120px">
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function csRateDescriptionFilter(element) {
							element
									.kendoAutoComplete({
										placeholder : "Enter Description",
										dataType : 'JSON',
										dataSource : {
											transport : {
												read : "${commonFilterUrl}/csRateDescription"
											}
										}
									});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>

		<%-- <kendo:grid-column title="Rate&nbsp;Unit&nbsp;*" field="rateUnit" format="{0:#.00}" width="85px" filterable="true"/> --%>

		<kendo:grid-column title="Rate&nbsp;Type&nbsp;*" field="csRateType" editor="toRateTypeEditor" width="90px">
			<kendo:grid-column-values value="${csRateTypes}" />
		</kendo:grid-column>

		<kendo:grid-column title="Rate&nbsp;UOM&nbsp;*" field="csRateUOM"  width="90px">
			<kendo:grid-column-values value="${csRateUOM}" />
		</kendo:grid-column>

		<kendo:grid-column title="Valid&nbsp;From&nbsp;*" field="csValidFrom" editor="csValidFromEditor" format="{0:dd/MM/yyyy}" width="90px" >
		
		<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function csValidFromFilter(element) {
							element.kendoDatePicker({
								  format: "dd/MM/yyyy"
							});
						}
					</script>
				</kendo:grid-column-filterable-ui>
		</kendo:grid-column-filterable>
			
		</kendo:grid-column>

		<kendo:grid-column title="Valid&nbsp;To&nbsp;*" field="csValidTo" format="{0:dd/MM/yyyy}" width="80px" >
				<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function csValidToFilter(element) {
							element.kendoDatePicker({
								  format: "dd/MM/yyyy"
							});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>

		<kendo:grid-column title="Min Rate(In Rs)" field="csMinRate" width="100px" >
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function csMinRateFilter(element) {
							element.kendoNumericTextBox({});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>
		<kendo:grid-column title="Max Rate(In Rs)" field="csMaxRate" width="100px" >
		<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function csMaxRateToFilter(element) {
							element.kendoNumericTextBox({});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>

		<kendo:grid-column title="TOD Type *" field="csTodType" editor="toTODTypeEditor" width="80px">
			<kendo:grid-column-values value="${csTodType}" />
		</kendo:grid-column>

		<kendo:grid-column title="Rate Status *" field="status" width="90px">
			<kendo:grid-column-values value="${status}" />
		</kendo:grid-column>

		<kendo:grid-column title="&nbsp;" width="158px">
			<kendo:grid-column-command>
				<kendo:grid-column-commandItem name="edit"/>
				<kendo:grid-column-commandItem name="destroy" />
			</kendo:grid-column-command>
		</kendo:grid-column>

		<kendo:grid-column title=""
			template="<a href='\\\#' id='csTemPID' class='k-button k-button-icontext btn-destroy k-grid-Active#=data.csRmId#'>#= data.status == 'Active' ? 'Inactive' : 'Active' #</a>"
			width="70px" />
	   </kendo:grid-columns>

	<kendo:dataSource pageSize="20" requestEnd="csRequestEndRateMaster" requestStart="csRequestStartRateMaster">
	
		<kendo:dataSource-transport>
			<kendo:dataSource-transport-create url="${commonServiceRatemasterCreateUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-read url="${commonServiceRatemasterReadUrl}" dataType="json" type="POST" contentType="application/json" />
			<kendo:dataSource-transport-update url="${commonServiceRatemasterUpdateUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-destroy url="${commonServiceRatemasterDestroyUrl}" dataType="json" type="GET" contentType="application/json" />
		</kendo:dataSource-transport>

		<kendo:dataSource-schema>
			<kendo:dataSource-schema-model id="csRmId">
				<kendo:dataSource-schema-model-fields>

					<kendo:dataSource-schema-model-field name="csRmId" type="number"/>

					<kendo:dataSource-schema-model-field name="csTariffId" type="string" defaultValue="Select">
						<kendo:dataSource-schema-model-field-validation />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="csRateName"  defaultValue="Charges">
						<kendo:dataSource-schema-model-field-validation />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="csRateDescription" type="string">
						<kendo:dataSource-schema-model-field-validation />
					</kendo:dataSource-schema-model-field>

				<%-- 	<kendo:dataSource-schema-model-field name="rateUnit" type="number">
						<kendo:dataSource-schema-model-field-validation required="true" min="1" />
					</kendo:dataSource-schema-model-field> --%>

					<kendo:dataSource-schema-model-field name="csRateType"  defaultValue="Single Slab" />

					<kendo:dataSource-schema-model-field name="csRateUOM"  defaultValue="Liters">
						<kendo:dataSource-schema-model-field-validation required="true" />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="csValidFrom" type="date">
						<kendo:dataSource-schema-model-field-validation required="true" />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="csValidTo" type="date">
						<kendo:dataSource-schema-model-field-validation required="true" />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="csMinRate" type="number">
						<kendo:dataSource-schema-model-field-validation min="0" />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="csMaxRate" type="number">
						<kendo:dataSource-schema-model-field-validation min="0" />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="csTodType" type="string" defaultValue="None" />

					<kendo:dataSource-schema-model-field name="status" editable="true" type="string" />
					
					<kendo:dataSource-schema-model-field name="csTariffName" type="string" />
					
				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>

	</kendo:dataSource>
</kendo:grid>

<kendo:grid-detailTemplate id="commonServiceRateMasterSubGrid">
	<kendo:tabStrip name="csRateMasterTabStrip_#=csRmId#" >
		<kendo:tabStrip-items>

			<kendo:tabStrip-item text="Rate Slab" selected="true">
				<kendo:tabStrip-item-content>
					<div class='rateSlbDIV'>
						<br />
						<kendo:grid name="csRateSlabGrid_#=csRmId#" pageable="true" filterable="true" dataBound="csCheckBoxDataBound"
							change="csGridChange" resizable="true" sortable="true"
							reorderable="true" selectable="true" scrollable="true"
							edit="csRateSlabEditEvent" remove="commonServiceRateSlabDeleteEvent">
							
							<kendo:grid-pageable pageSizes="true" buttonCount="5"
								pageSize="10" input="true" numeric="true"></kendo:grid-pageable>
							<kendo:grid-filterable extra="false">
								<kendo:grid-filterable-operators>
									<kendo:grid-filterable-operators-string eq="Is equal to" />
								</kendo:grid-filterable-operators>
							</kendo:grid-filterable>
							<kendo:grid-editable mode="popup"
								confirmation="Are sure you want to delete this item ?" />
							<kendo:grid-toolbar>
								<kendo:grid-toolbarItem name="create" text="Add Rate Slab" />
								<%-- <kendo:grid-toolbarItem text="Merge" /> --%>
							</kendo:grid-toolbar>

							<kendo:grid-columns>
								<kendo:grid-column title="&nbsp;" width="30px">
									<!-- File Upload Button Purpose -->
								</kendo:grid-column>

								<kendo:grid-column title="ELRS_ID" field="csRsId" hidden="true" width="70px" />
								
								<kendo:grid-column title="Slab Number *" field="csSlabNo" width="70px" />
								
								<kendo:grid-column title="Slab Type *" field="csSlabType" editor="csRateSlabTypeEditor" width="70px">
									<kendo:grid-column-values value="${csSlabType}" />
								</kendo:grid-column>

							    <kendo:grid-column title="Slab From *" field="csDummySlabFrom" width="80px"/> 
								<kendo:grid-column title="Slab From *" field="csSlabFrom" width="80px" hidden="true"/>
								
								<kendo:grid-column title="Max Value" field="csMaxSlabToValue" filterable="false" sortable="false" width="0px" hidden="true" />
								<kendo:grid-column title="Slab To *" field="csDummySlabTo" width="80px" />
								<kendo:grid-column title="Slab To *" field="csSlabTo" width="70px" hidden="true"/>
								
								<kendo:grid-column title="Rate Type *" field="csSlabRateType" width="70px">
									<kendo:grid-column-values value="${csSlabRateType}" />
								</kendo:grid-column>
								
								<kendo:grid-column title="Rate *" field="csRate" width="70px" />
								
								<kendo:grid-column title="Slab Status *" field="status" width="70px">
									<kendo:grid-column-values value="${status}" />
								</kendo:grid-column>

								<kendo:grid-column title="&nbsp;" width="250px">
									<kendo:grid-column-command>
									<%-- <kendo:grid-column-commandItem name="Split" click="csSplitFunction" /> --%>
										<kendo:grid-column-commandItem name="edit" />
										<kendo:grid-column-commandItem name="destroy" />
										<kendo:grid-column-commandItem name="Change Status" click="csRateSlabStatusChange" />
									</kendo:grid-column-command>
								</kendo:grid-column>

							</kendo:grid-columns>

							<kendo:dataSource requestEnd="csRateSlabRequestEnd" requestStart="csRateSlabRequestStart">
								<kendo:dataSource-transport>
									<kendo:dataSource-transport-read url="${commonServiceRateSlabReadUrl}/#=csRmId#" dataType="json" type="POST" contentType="application/json" />
									<kendo:dataSource-transport-create url="${commonServiceRateSlabCreateUrl}/#=csRmId#" dataType="json" type="GET" contentType="application/json" />
									<kendo:dataSource-transport-update url="${commonServiceRateSlabUpdateUrl}" dataType="json" type="GET" contentType="application/json" />
									<kendo:dataSource-transport-destroy url="${commonServiceRateSlabDeleteUrl}" dataType="json" type="GET" contentType="application/json" />
								</kendo:dataSource-transport>
								
								<kendo:dataSource-schema parse="csRateMasterParse">
									<kendo:dataSource-schema-model id="csRsId">
										<kendo:dataSource-schema-model-fields>

											<kendo:dataSource-schema-model-field name="csRsId"/>

										<%-- 	<kendo:dataSource-schema-model-field name="eltiId"/> --%>

											<kendo:dataSource-schema-model-field name="csSlabNo" type="number">
												<kendo:dataSource-schema-model-field-validation min="1" />
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="csSlabType" type="String" defaultValue="Numeric"/>

											<kendo:dataSource-schema-model-field name="csSlabRateType" type="String" defaultValue="Paise"/>

											<kendo:dataSource-schema-model-field name="csRate" type="number">
												<kendo:dataSource-schema-model-field-validation min="0" />
											</kendo:dataSource-schema-model-field>

											<kendo:dataSource-schema-model-field name="csSlabFrom" type="number">
											<kendo:dataSource-schema-model-field-validation min="0" max="999999"/>
											</kendo:dataSource-schema-model-field>
											
										   <kendo:dataSource-schema-model-field name="csDummySlabFrom" type="String"/>
											
											<kendo:dataSource-schema-model-field name="csMaxSlabToValue" type="boolean"/>
											
											<kendo:dataSource-schema-model-field name="csSlabTo" type="number">
											<kendo:dataSource-schema-model-field-validation min="0" max="999999"/>
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="csDummySlabTo" type="String"/>
											
											<kendo:dataSource-schema-model-field name="status" type="string" editable="true" />

										</kendo:dataSource-schema-model-fields>
									</kendo:dataSource-schema-model>
								</kendo:dataSource-schema>
							</kendo:dataSource>
						</kendo:grid>
					</div>
				</kendo:tabStrip-item-content>
			</kendo:tabStrip-item>


			<kendo:tabStrip-item text="TOD Rates" >
			<div class='csHideTodStrip' style="display: none;">
				<kendo:tabStrip-item-content>
						<br /> Select Slab Number :
						<kendo:dropDownList name="csTODRateRates_#=csRmId#"
							dataBound="csSetFirstSelected" select="csHandleChange"
							dataTextField="csSlabNo" dataValueField="csRsId"
							style="width:100px">
							<kendo:dataSource>
								<kendo:dataSource-transport>
									<kendo:dataSource-transport-read
										url="${commonServiceRateSlabDropDown}/#=csRmId#" type="POST" contentType="application/json" />
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

						<kendo:grid name="csTODRate_#=csRmId#" pageable="true"
							filterable="true" resizable="true" sortable="true"
							reorderable="true" selectable="true" scrollable="true"
							edit="csTODRateEditEvent">
							<kendo:grid-pageable pageSize="10"></kendo:grid-pageable>
							<kendo:grid-filterable extra="false">
								<kendo:grid-filterable-operators>
									<kendo:grid-filterable-operators-string eq="Is equal to" />
								</kendo:grid-filterable-operators>
							</kendo:grid-filterable>
							<kendo:grid-editable mode="popup"
								confirmation="Are sure you want to delete this item ?" />
							<kendo:grid-toolbar>
								<kendo:grid-toolbarItem name="create" text="TOD Rates" />
							</kendo:grid-toolbar>
							<kendo:grid-columns>

								<kendo:grid-column title="ELTR_ID" field="cstiId" hidden="true" width="70px" />
								
								

								<kendo:grid-column title="Slab Number *" field="csRsId" width="100px">
									<kendo:grid-column-values value="${RateSlab}" />
								</kendo:grid-column>

								<kendo:grid-column title="From Time *" field="fromTime" format="{0:HH:mm tt}" editor="fromTimePicker" width="80px">
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

								<kendo:grid-column title="To Time *" field="toTime" format="{0:HH:mm tt}" editor="toTimePicker" width="80px">
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

								<kendo:grid-column title="Valid From *" field="todValidFrom" format="{0:dd/MM/yyyy}" width="130px" />
								
								<kendo:grid-column title="Valid To *" field="todValidTo" format="{0:dd/MM/yyyy}" width="150px" />
								
								<kendo:grid-column title="Rate Type *" field="csTodRateType" width="100px">
									<kendo:grid-column-values value="${csTodRateType}" />
								</kendo:grid-column>
								
								<kendo:grid-column title="Incremental Rate *" field="incrementalRate" width="130px" />
								
								<kendo:grid-column title="TOD Status *" field="status" width="100px">
									<kendo:grid-column-values value="${status}" />
								</kendo:grid-column>

								<kendo:grid-column title="&nbsp;" width="250px">
									<kendo:grid-column-command>
										<kendo:grid-column-commandItem name="edit" />
										<kendo:grid-column-commandItem name="destroy" />
										<kendo:grid-column-commandItem name="Change Status" click="csTODRatesStatusChange" />
									</kendo:grid-column-command>
								</kendo:grid-column>
							</kendo:grid-columns>

							<kendo:dataSource requestEnd="csTODRateRequestEnd">
							
								<kendo:dataSource-transport>
									<kendo:dataSource-transport-read url="${commonServiceTODRateReadUrl}/#=csRmId#" dataType="json" type="POST" contentType="application/json" />
									<kendo:dataSource-transport-create url="${commonServiceTODRateCreateUrl}" dataType="json" type="GET" contentType="application/json" />
									<kendo:dataSource-transport-update url="${commonServiceTODRateUpdateUrl}" dataType="json" type="GET" contentType="application/json" />
									<kendo:dataSource-transport-destroy url="${commonServiceTODRateDeleteUrl}" dataType="json" type="GET" contentType="application/json" />
								</kendo:dataSource-transport>

								<kendo:dataSource-schema parse="csTODRateParseFunction">
									<kendo:dataSource-schema-model id="cstiId">
										<kendo:dataSource-schema-model-fields>

											<kendo:dataSource-schema-model-field name="cstiId" type="number"/>

											<kendo:dataSource-schema-model-field name="csRsId" type="number"/>
											
											<kendo:dataSource-schema-model-field name="csTodRateType" type="String" defaultValue="Paise"/>
											
											<kendo:dataSource-schema-model-field name="incrementalRate" type="number">
												<kendo:dataSource-schema-model-field-validation required="true" min="0"/>
											</kendo:dataSource-schema-model-field>

											<kendo:dataSource-schema-model-field name="fromTime">
												<kendo:dataSource-schema-model-field-validation required="true" />
											</kendo:dataSource-schema-model-field>

											<kendo:dataSource-schema-model-field name="toTime">
												<kendo:dataSource-schema-model-field-validation required="true" />
											</kendo:dataSource-schema-model-field>

											<kendo:dataSource-schema-model-field name="todValidFrom" type="date">
												<kendo:dataSource-schema-model-field-validation required="true" />
											</kendo:dataSource-schema-model-field>

											<kendo:dataSource-schema-model-field name="todValidTo" type="date">
												<kendo:dataSource-schema-model-field-validation required="true" />
											</kendo:dataSource-schema-model-field>

											<kendo:dataSource-schema-model-field name="status" type="string" />

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

<div id="dialog-form" title="Reply to sender"
	style="width: 6000px; height: 6000px">
	<form>
		<fieldset>
			<label for="name">Enter New Rate</label> <input type="text"
				name="newcsRate" id="newcsRate"
				class="text ui-widget-content ui-corner-all">
		</fieldset>
	</form>
</div>

<div id="dialog-formSplit" title="Enter New values" style="width: 600px; height: 700px">
	<!-- <form id="formtoSplit" method="post"> -->
	<form>
		<table
			style="text-align: center; vertical-align: middle; width: 300px; margin-left: -45px;">
			<caption>Form 1</caption>
			<tr>
				<td style="height: 10px; width: 100px; text-align: center; vertical-align: middle;">Slab Number</td>
				<td style="height: 40px; width: 100px; text-align: center; vertical-align: middle;"> 
				<input type="number" name="csSlabNo" id="csSlabNo"
					class="text ui-widget-content ui-corner-all" 
					readonly="readonly">
				</td>
			</tr>

			<tr>
				<td style="height: 10px; width: 100px; text-align: center; vertical-align: middle;">Rate</td>
				<td style="height: 40px; width: 100px; text-align: center; vertical-align: middle;">
					<input type="number" name="csRate" id="csRate"
					class="text ui-widget-content ui-corner-all">
				</td>
			</tr>

			<tr>
				<td style="height: 10px; width: 100px; text-align: center; vertical-align: middle;">Slab From</td>
				<td style="height: 40px; width: 100px; text-align: center; vertical-align: middle;">
					<input type="number" name="csSlabFrom" id="csSlabFrom"
					class="text ui-widget-content ui-corner-all" 
					readonly="readonly">
				</td>
			</tr>

			<tr>
				<td style="height: 10px; width: 100px; text-align: center; vertical-align: middle;">Slab To</td>
				<td style="height: 40px; width: 100px; text-align: center; vertical-align: middle;">
					<input type="number" name="csSlabTo" id="csSlabTo"
					class="text ui-widget-content ui-corner-all">
				</td>
			</tr>
			<tr>
				<td><input type="hidden" name="hiddenelrsName" id="hiddencsRsId"></td>
			</tr>
		</table>

		<table
			style="text-align: center; vertical-align: middle; width: 300px; margin-left: 250px; margin-top: -180px">
			<caption>Form 2</caption>
			<tr>
				<td style="height: 10px; width: 100px; text-align: center; vertical-align: middle;">Slab Number</td>
				<td style="height: 40px; width: 100px; text-align: center; vertical-align: middle;">
					<input type="number" name="csSlabNo1" id="csSlabNo1"
					class="text ui-widget-content ui-corner-all" 
					readonly="readonly">
				</td>
			</tr>

			<tr>
				<td style="height: 10px; width: 100px; text-align: center; vertical-align: middle;">Rate</td>
				<td style="height: 40px; width: 100px; text-align: center; vertical-align: middle;">
					<input type="number" name="csRate1" id="csRate1"
					class="text ui-widget-content ui-corner-all">
				</td>
			</tr>

			<tr>
				<td style="height: 10px; width: 100px; text-align: center; vertical-align: middle;">Slab From</td>
				<td style="height: 40px; width: 100px; text-align: center; vertical-align: middle;">
					<input type="number" name="csSlabFrom1" id="csSlabFrom1"
					class="text ui-widget-content ui-corner-all">
				</td>
			</tr>

			<tr>
				<td style="height: 10px; width: 100px; text-align: center; vertical-align: middle;">Slab To</td>
				<td style="height: 40px; width: 100px; text-align: center; vertical-align: middle;">
					<input type="number" name="csSlabTo1" id="csSlabTo1"
					class="text ui-widget-content ui-corner-all"
					readonly="readonly">
				</td>
			</tr>
		</table>
	</form>
</div>
<script>

var nextcsSlabNo = 0; 
var nextcsSlabFrom = 0;
var nextcsSlabTo = 0;

var elRateSlabMergeDialog;
var elRateSlabSplitDialog;

var csTariffId;
var csRateName1;
var csRateType1;
var nextFromDate;
var csSlabFromToCompare;
var csSlabToToCompare;
var editFloag = 0;
var selectedDateFrom;
var rateMasterEditFloag = 0;

	elRateSlabSplitDialog = $("#dialog-formSplit")
			.dialog({
						autoOpen : false,
						height : 400,
						width : 600,
						modal : true,
						buttons : {
							"Save" : function() {
	 						    var csSlabNo = $("#csSlabNo").val();
								var csRate = $("#csRate").val();
								var csSlabFrom = $("#csSlabFrom").val();
								var csSlabTo = $("#csSlabTo").val();
								var hiddencsRsId = $("#hiddencsRsId").val();
								var csSlabNo1 = $("#csSlabNo1").val();
								var csRate1 = $("#csRate1").val();
								var csSlabFrom1 = $("#csSlabFrom1").val();
								var csSlabTo1 = $("#csSlabTo1").val();
								
								  if (hiddencsRsId == "") {
									alert("form1 Slab number should not be empty.");
									return false;
								}

								if (csSlabNo == "") {
									alert("form1 Slab number should not be empty.");
									return false;
								}
								
								if (csRate == "") {
									alert("form1 Rate should not be empty.");
									return false;
								}
								else
								{
									if (parseInt(csRate) < 0) 
									{
										alert("form1 Rate should greater than 1.");
										return false;
									}
									
								}
								
								if (csSlabFrom == "") {
									alert("form1 Slab from should not be empty.");
									return false;
								}

								if (csSlabTo == "") {
									alert("form1 Slab To should not be empty.");
									return false;
								} else {
									if (parseInt(csSlabTo) <= parseInt(csSlabFrom)) {
										alert("form1 Slab To always greater than slab from");
										return false;
									}
								}
								
								if (csSlabNo1 == "") {
									alert("form2 Slab number should not be empty.");
									return false;
								}
							
								if (csRate1 == "") {
									alert("form2 Rate should not be empty.");
									return false;
								} else {
									if (csRate1 < 0) {
										alert("form2 Rate should greater than 1.");
										return false;

									}
								}
								if (csSlabFrom1 == "") {
									alert("form2 Slab from should not be empty.");
									return false;
								} else {
									var form1SlabTo = csSlabTo;
									var form2SlabFrom = parseInt(form1SlabTo) + 1;
									if (parseInt(csSlabFrom1) != parseInt(form2SlabFrom)) {
										alert("Form 2 Slab From shoub be Form1 Slab To +1");
										return false;
									}

								}

								if (csSlabTo1 == "") {
									alert("form2 Slab To should not be empty.");
									return false;
								} 
							
								$.ajax({
									        type : "POST",
											url : "./tariff/elcsRateslab/csRateSlabSplit",
											data : {
												hiddencsRsId : hiddencsRsId,
												csSlabNo : csSlabNo,
												csRate : csRate,
												csSlabFrom : csSlabFrom,
												csSlabTo : csSlabTo,
												csSlabNo1 : csSlabNo1,
												csRate1 : csRate1,
												csSlabFrom1 : csSlabFrom1,
												csSlabTo1 : csSlabTo1,

											}, 
											success : function(response) {
												if (response.status == "SUCCESS") {
													errorInfo = JSON.stringify(response.result);
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
															'#csRateSlabGrid_'
																	+ SelectedRowId)
															.data().kendoGrid.dataSource
															.read();
													return false;
												}

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
											}
										});

								elRateSlabSplitDialog.dialog("close");
							},
						},
						close : function() {
							elRateSlabSplitDialog.dialog("close");
						}
					});

	var globalElrsId = [];
	function selectSingleCheckBox(fieldId) {
		var temp = fieldId.split("_");
		if ($("#" + fieldId).prop('checked') == true) 
		{
			globalElrsId.push(temp[1]);
		}
		else if ($("#" + fieldId).prop('checked') == false) {
			globalElrsId.splice($.inArray(temp[1], globalElrsId), 1);
		}

	}

	function csCheckBoxDataBound(e)
	{
		if (csTodTypeToHide === "None") 
		 {
			  $('.csHideTodStrip').hide();
			  $('li[aria-controls="csRateMasterTabStrip_'+SelectedRowId+'-2"]').hide();
		 }
		 else
		{
			  $('.csHideTodStrip').show();
			  $('li[aria-controls="csRateMasterTabStrip_'+SelectedRowId+'-2"]').show();
		} 
		var grid = $("#csRateSlabGrid_" + SelectedRowId).data("kendoGrid");
		var gridData = grid._data;
		var i = 0;
		this.tbody
				.find("tr td:first-child")
				.each(
						function(e) {
							$(
									'<input type="checkbox" name="chkbox" id="singleSelectChkBx_'
											+ gridData[i].csRsId
											+ '" onclick="selectSingleCheckBox(this.id)"  />')
									.appendTo(this);
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
								var newcsRate = $("#newcsRate").val();

								if (newcsRate == "") {
									if (newcsRate <= 0) {
										alert("New csRate should be greater than 1.");
										return false;
									}
									alert("New csRate should not be empty.");
									return false;
								}

								$
										.ajax({
											type : "POST",
											data : {
												newcsRate : newcsRate,
											},
											url : "./tariff/elcsRateslab/getNewRate",
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
															'#csRateSlabGrid_'
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

	$("#commonServiceRateMasterGrid")
			.on(
					"click",
					".k-grid-Merge",
					function() {
						
						if((csRateMasterSlabType === 'Single Slab') && (parseInt(nextcsSlabNo) === 1))
						{
						 var grid = $("#csRateSlabGrid_" + SelectedRowId).data("kendoGrid");
						 grid.cancelRow();
					     alert("Only one csRate slab allowed for single slab csRate master.");
					     return false;
						}
						
						if (globalElrsId.length <= 0) {
							alert("Select at two check boxes.");
							return false;
						}

						if (globalElrsId.length > 2) {
							alert("Only two check boxes allowed.");
							return false;
						}

						$
								.ajax({
									type : "POST",
									url : "./tariff/elcsRateslab/merge",
									data : {
										"csRsIds" : globalElrsId.toString()
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
											var csRateSlabGrid = $(
													'#csRateSlabGrid_'
															+ SelectedRowId)
													.data().kendoGrid.dataSource
													.read();
											csRateSlabGrid.refresh();
											elRateSlabMergeDialog
													.dialog("close");
											return false;
										}

										if (response.status == "SUCCESS") {
											errorInfo = JSON
													.stringify(response.result);
											$("#alertsBox").html("");
											$("#alertsBox")
													.html("" + errorInfo);
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
											var csRateSlabGrid = $(
													'#csRateSlabGrid_'
															+ SelectedRowId)
													.data().kendoGrid.dataSource
													.read();
											csRateSlabGrid.refresh();
											elRateSlabMergeDialog
													.dialog("close");
											return false;
										}
									}
								});
						elRateSlabMergeDialog.dialog("close");
					});

	/***************************** For split ***************************  */
	var SelectedRowId = "";
	var csTodTypeToHide = "";
	var csRateMasterSlabType ="";
	function commonServiceChangeRateMaster(arg) {
		var gview = $("#commonServiceRateMasterGrid").data("kendoGrid");
		var selectedItem = gview.dataItem(gview.select());
		SelectedRowId = selectedItem.csRmId;
		csTodTypeToHide= selectedItem.csTodType;
		csRateMasterSlabType = selectedItem.csRateType;
	}

	function csSetFirstSelected(e) {
		this.select(0);
	}
	var csRsId = "";
	var csSlabNo = "";
	var csRate = "";
	var csSlabFrom = "";
	var csSlabTo = "";
	function csSplitFunction() 
	{
		if((csRateMasterSlabType === 'Single Slab') && (parseInt(nextcsSlabNo) === 1))
		{
		 var grid = $("#csRateSlabGrid_" + SelectedRowId).data("kendoGrid");
		 grid.cancelRow();
	     alert("Only one csRate slab allowed for single slab csRate master.");
	     return false;
		}
		
		elRateSlabSplitDialog.dialog("open");
		$("#hiddencsRsId").val(csRsId);
		$("#csSlabNo").val(csSlabNo);
		var csSlabNo1 = csSlabNo + 1;
		$("#csSlabNo1").val(csSlabNo1);
		$("#csRate").val(csRate);
		$("#csSlabFrom").val(csSlabFrom);
		$("#csSlabTo1").val(csSlabTo);
	}

	function csGridChange() {
		var elRateSlabGrid = $("#csRateSlabGrid_" + SelectedRowId)
				.data("kendoGrid");
		var selectedRowItem = elRateSlabGrid.dataItem(elRateSlabGrid.select());
		csRsId = selectedRowItem.csRsId;
		csSlabNo = selectedRowItem.csSlabNo;
		csRate = selectedRowItem.csRate;
		csSlabFrom = selectedRowItem.csSlabFrom;
		csSlabTo = selectedRowItem.csSlabTo;
	}

	var SelectedcsRsId = "";
	function csHandleChange(e) {
		var dataItem = this.dataItem(e.item.index());
		SelectedcsRsId = dataItem.csRsId;
		if (dataItem.csRsId != "") {
			$('#csTODRate_' + SelectedRowId).data().kendoGrid.dataSource.read({
				csRmId : SelectedcsRsId
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

	$("#commonServiceRateMasterGrid").on("click", "#csTemPID", function(e) 
	{
		var button = $(this),
		enable = button.text() == "Active";
		var widget = $("#commonServiceRateMasterGrid").data("kendoGrid");
		var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
	
 		var result=securityCheckForActionsForStatus("./Tariff/CommonServices/RateMaster/activitRateMaster"); 
		if(result=="success")
		{ 
			if (enable)
			{
				$.ajax({
					type : "POST",
					url : "./commonServiceTariff/tariffStatus/" + dataItem.csRmId + "/activate",
					dataType:"text",
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
						$('#commonServiceRateMasterGrid').data('kendoGrid').dataSource.read();
					}
				});
			} else {
				$.ajax({
					type : "POST",
					url : "./commonServiceTariff/tariffStatus/" + dataItem.csRmId + "/deactivate",
					dataType:"text",
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
						$('#commonServiceRateMasterGrid').data('kendoGrid').dataSource.read();
					}
				});
			}
		}

	});

	/*------------------------- activate and deactive for child table ---------------------------  */

/*	var SelectedItemMasterRowId = "";
	 function onChangeItemMaster(arg) {
		var gview = $("#csRateSlabGrid_").data("kendoGrid");
		var selectedItem = gview.dataItem(gview.select());
		SelectedItemMasterRowId = selectedItem.imId;
		this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
	} */
	
	
	var res = "";
	function commonServiceRatemasterEvent(e)
	{

	//var selectedRowIndex = $("#grid").data("kendoGrid").select().index();

		/***************************  to remove the id from pop up  **********************/
		$('div[data-container-for="csRmId"]').remove();
		$('label[for="csRmId"]').closest('.k-edit-label').remove();

		$('div[data-container-for="csTariffName"]').remove();
		$('label[for="csTariffName"]').closest('.k-edit-label').remove();
		
		$(".k-edit-field").each(function() {
			$(this).find("#csTemPID").parent().remove();
		});

		$('label[for="status"]').parent().hide();
		$('div[data-container-for="status"]').hide();

		/************************* Button Alerts *************************/
	 	if (e.model.isNew()) {
	 		securityCheckForActions("./Tariff/CommonServices/RateMaster/createRateMaster");
			$(".k-window-title").text("Add New Rate Master");
			$(".k-grid-update").text("Save");
			
			csTariffId =  e.model.csTariffId;
			csRateName1 = e.model.csRateName;
			csRateType1 = e.model.csRateType;
			selectedDate  =  e.model.csValidFrom;
		} 
	 	else
		{
	 		securityCheckForActions("./Tariff/CommonServices/RateMaster/updateRateMaster");
	 		rateMasterEditFloag = 1;
			$.ajax({
				type : "GET",
				url : '${getMaxDate}',
				dataType : "JSON",
				data : {
					csRateName : e.model.csRateName,
					csTariffId : e.model.csTariffId,
					csRateType : e.model.csRateType,
				},
				success : function(response)
				{
					nextFromDate = response;
				}
			});
		    selectedDateFrom = e.model.csValidFrom;
			$(".k-window-title").text("Edit Rate Master Details");
		} 
		
	}

	/********************** to hide the child table id ***************************/
	function csRateSlabEditEvent(e) {

		$('div[data-container-for="csRsId"]').remove();
		$('label[for="csRsId"]').closest('.k-edit-label').remove();

		$('div[data-container-for="status"]').remove();
		$('label[for="status"]').closest('.k-edit-label').remove();
		
		$('div[data-container-for="csDummySlabTo"]').remove();
		$('label[for="csDummySlabTo"]').closest('.k-edit-label').remove();
		
		$('div[data-container-for="csDummySlabFrom"]').remove();
		$('label[for="csDummySlabFrom"]').closest('.k-edit-label').remove();

		if (e.model.isNew()) 
		{
			securityCheckForActions("./Tariff/CommonServices/RateSlab/createRateSlab");
			if((csRateMasterSlabType === 'Single Slab') && (parseInt(nextcsSlabNo) === 1))
					{
					 var grid = $("#csRateSlabGrid_" + SelectedRowId).data("kendoGrid");
					 grid.cancelRow();
				     alert("Only one csRate slab allowed for single slab csRate master.");
				     return false;
					}
			
			if(parseInt(nextcsSlabTo) === 999999)
				{
				 var grid = $("#csRateSlabGrid_" + SelectedRowId).data("kendoGrid");
				 grid.cancelRow();
			     alert("You can't the add the new record,Slab to Max is the last record.");
			     return false;
				}
			
			$(".k-window-title").text("Add New Rate Slab");
			$(".k-grid-update").text("Save");
		} else 
		{
			securityCheckForActions("./Tariff/CommonServices/RateSlab/updateRateSlab");
			editFloag = 1;
			if(e.model.csSlabType == "Not applicable")
			{
			$('label[for="csSlabTo"]').hide();
	    	$('div[data-container-for="csSlabTo"]').hide();
			$('input[name="csSlabTo"]').prop('required',false);
			$('input[name="csSlabTo"]').val(null);
			
			$('label[for="csSlabFrom"]').hide();
	    	$('div[data-container-for="csSlabFrom"]').hide();
			$('input[name="csSlabFrom"]').prop('required',false);
			
			$('label[for="csMaxSlabToValue"]').hide();
	    	$('div[data-container-for="csMaxSlabToValue"]').hide();
			$('input[name="csMaxSlabToValue"]').prop('required',false);
			
			}
		else
			{
			
			$('label[for="csSlabTo"]').show();
	    	$('div[data-container-for="csSlabTo"]').show();
			
			$('label[for="csSlabFrom"]').show();
	    	$('div[data-container-for="csSlabFrom"]').show();
			
			$('label[for="csMaxSlabToValue"]').show();
	    	$('div[data-container-for="csMaxSlabToValue"]').show();
			
			}
			    if (e.model.csSlabTo == parseInt(999999))
			    {
			    	$('input[name="csMaxSlabToValue"]').prop('checked', true);
			    	$('label[for="csSlabTo"]').hide();
			    	$('div[data-container-for="csSlabTo"]').hide();
					$('input[name="csSlabTo"]').prop('required',false);
					$('input[name="csSlabTo"]').val(999999);
			    }
			
			$(".k-window-title").text("Edit Rate Slab Details");
		}
	}

	/********************** for TOD Rates ***************************/
	function csTODRateEditEvent(e) {
		$('div[data-container-for="cstiId"]').remove();
		$('label[for="cstiId"]').closest('.k-edit-label').remove();

		$('div[data-container-for="status"]').remove();
		$('label[for="status"]').closest('.k-edit-label').remove();

		$('div[data-container-for="csRsId"]').remove();
		$('label[for="csRsId"]').closest('.k-edit-label').remove();

		if (e.model.isNew()) {

			if ((SelectedcsRsId == "") || (SelectedcsRsId == ' ')) {
				var grid = $("#csTODRate_" + SelectedRowId).data("kendoGrid");
				grid.cancelRow();
				alert("Please Select Slab Number");
				return false;
			}
			$(".k-window-title").text("Add New Acess Card Permission");
			e.model.set("csRsId", SelectedcsRsId);
		} else {
		}

		if (e.model.isNew()) {
			$(".k-window-title").text("Add New TOD Rate");
			$(".k-grid-update").text("Save");
		} else {
			$(".k-window-title").text("Edit TOD Rate Details");
		}
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

	 function commonServiceClearFilterRateMaster()
	 {
		 
		  $("form.k-filter-menu button[type='reset']").slice().trigger("click");
		   var elRatemaster = $("#commonServiceRateMasterGrid").data("kendoGrid");
		   elRatemaster.dataSource.read();
		   elRatemaster.refresh();
	  /*   $("form.k-filter-menu button[type='reset']").slice().trigger("click");
	    
	    $.ajax({
       	 url : "./elRateMaster/read",  
       	 type : 'GET',
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            success: function (result)
            {
           	  var grid = $("#commonServiceRateMasterGrid").getKendoGrid();
                 var data = new kendo.data.DataSource();
                 grid.dataSource.data(result);
                 grid.refresh();  
             
            },
        }); */
	 //   RenderLinkInner('csRateMaster');
	 }

	/***************************  Custom message validation  **********************/

	var seasonFromAddress = "";
	(function($, kendo) {
		$.extend(true,kendo.ui.validator,{
							rules : {
								csTariffNameValidater : function(input, params) {
									if ((input.attr("name") == "Tariff Name") && (input.val() == 0)) {
										return false;
									}
									return true;
								},
								csRateNameValidation : function(input, params) {

									if (input.attr("name") == "csRateName") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},
								descriptionValidation : function(input, params) {

									if (input.attr("name") == "csRateDescription") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},
								csRateofunit : function(input, params) {

									if (input.attr("name") == "csRateUnit") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},

								csRateType : function(input, params) {
									if (input.attr("name") == "csRateType") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},

								csRateUOM : function(input, params) {
									if (input.attr("name") == "csRateUOM") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},

								addressSeasonTo2 : function(input, params)
								{
									if (input.filter("[name = 'csValidTo']").length && input.val() != "")
									{
										var selectedDate = input.val();
										var contractStartDtVal = selectedDateFrom.getDate()+ '/'+(selectedDateFrom.getMonth() + 1) + '/' + selectedDateFrom.getFullYear();
										var flagDate = false;
										if ($.datepicker.parseDate('dd/mm/yy', selectedDate) > $.datepicker.parseDate('dd/mm/yy',contractStartDtVal))
										{
											flagDate = true;
										}
										return flagDate;
									}
									return true;
								},
								
								csRateUOMValidation : function(input, params) {
									if (input.filter("[name='csRateUOM']").length
											&& input.val() != "") {
										return /^[a-zA-Z0-9 ]{1,45}$/
												.test(input.val());
									}
									return true;
								},

								todRates : function(input, params) {
									if (input.attr("name") == "cstiId") {
										return $.trim(input.val()) !== "0";
									}
									return true;
								},
								/************** for Inner Grid Validation ************/
								slabNoValidator : function(input, params) {
									if (input.attr("name") == "csSlabNo") 
									{
										return $.trim(input.val()) !== "";
									}
									return true;
								},
							nextSlabNoValidator : function(input, params) 
								{
								   if(parseInt(editFloag) === 1)
									   {
									   
									   }
								   else
								   {
										if ((input.attr("name") == "csSlabNo") && (parseInt(input.val()) !=  parseInt((nextcsSlabNo+1))))
										{
												 return false;
										 }
								   }
								
								  	return true;
								}, 
								csRateValidator : function(input, params)
								{
									if  ((input.attr("name") == "csRate") && (input.val() == 0)) {
										return false;
									}
									return true;
								}, 
								csSlabToValidator : function(input, params)
								{
									if  ((input.attr("name") == "csSlabTo") && (input.val() == 0))
									{
										return false;
									}
									return true;
								},
							
								csSlabToGreaterThanSlabFrom : function(input, params)
								{
									if (input.attr("name") == "csSlabTo") 
									{
										csSlabToToCompare = input.val();
									}
									if (input.attr("name") == "csSlabFrom")
									{
										csSlabFromToCompare = input.val();
									}
									if (input.attr("name") == "csSlabTo") 
									{
									if(parseInt(csSlabFromToCompare) > parseInt(csSlabToToCompare))
										{
										return false;
										}
									else{
										return true;
									}
									}
									return true;
								},
							csSlabFromValidator : function(input, params) 
							{
								
									if ((input.attr("name") == "csSlabFrom") && (parseInt(input.val()) < 0)) 
									{
										return false;
									}
									return true;
							},
								csSlabToValidator : function(input, params)
								{
									if  ((input.attr("name") == "csSlabTo") && (parseFloat(input.val()) <= 0.2))
									{
										return false;
									}
									return true;
								},
								
								todValidFromDate: function (input, params) 
					             {
					                 if (input.filter("[name = 'todValidFrom']").length && input.val() != "") 
					                 {                          
					                     var selectedFromDate = input.val();
					                     seasonFromAddress = selectedFromDate;
					                     return true;
					                 }
					                 return true;
					             },
								
					             todValidToDate: function (input, params) 
					             {
					                 if (input.filter("[name = 'todValidTo']").length && input.val() != "") 
					                 {                          
					                     var selectedToDate = input.val();
					                     var flagDate = false;

					                     if ($.datepicker.parseDate('dd/mm/yy', selectedToDate) > $.datepicker.parseDate('dd/mm/yy', seasonFromAddress)) 
					                     {
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
								csTariffNameValidater : "Tariff name is not selected",
								csRateNameValidation : "Rate name should not be empty",
								descriptionValidation : "Description should not be empty",
								csRateofunit : "Rate per unit should not be empty",
								csRateType : "Rate Type should not be empty",
								csRateUOM : "UOM should not be empty",
								validatinForNumbers : "Only numbers are allowed,follwed by two decimal points",
								addressSeasonFrom : "From Date must be selected in the future",
								addressSeasonTo1 : "Select From date first before selecting To date and change To date accordingly",
								csRateUOMValidation : "Rate UOM can contain alphabets and spaces but cannot allow numbers and other special characters and maximum 45 characters are allowed",
								addressSeasonTo2 : "To date should be after From date",
								todRates : "Select atleast one from TOD Rates",
								slabNoValidator : "Slab Number Cannot Be Empty",
								csRateValidator : "Rate is required",
								csSlabFromValidator : "Slab From fequired",
								csSlabToValidator : "Slab To required",
								nextSlabNoValidator :"Next Slab Number should start with maximum number +1",
								csSlabToGreaterThanSlabFrom : "Slab To number always greater than Slab From",
								todValidFromDate : "From Date not be empty",
								todValidToDate : "To date should be after From date",
								
							}
						});
	})(jQuery, kendo);

	function csRequestStartRateMaster(e){
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();
	}
	function csRateSlabRequestStart(e){
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();
	}
	function csRequestEndRateMaster(r) {

		if (typeof r.response != 'undefined') {

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
				$('#commonServiceRateMasterGrid').data('kendoGrid').dataSource.read();
				$('#commonServiceRateMasterGrid').data('kendoGrid').refresh();
			}

			if (r.response.status == "SINGLESLAB") {

				errorInfo = JSON.stringify(r.response.result);

				$("#alertsBox").html("");
				$("#alertsBox").html("");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				$('#commonServiceRateMasterGrid').data('kendoGrid').dataSource.read();
				$('#commonServiceRateMasterGrid').data('kendoGrid').refresh();
			}

			if (r.type == "update") {
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Updating the Rate Master<br>");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				$('#commonServiceRateMasterGrid').data('kendoGrid').dataSource.read();
				$('#commonServiceRateMasterGrid').data('kendoGrid').refresh();
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
				$('#commonServiceRateMasterGrid').data('kendoGrid').dataSource.read();
				$('#commonServiceRateMasterGrid').data('kendoGrid').refresh();
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
				$('#commonServiceRateMasterGrid').data('kendoGrid').dataSource.read();
				$('#commonServiceRateMasterGrid').data('kendoGrid').refresh();
			}
		}
	}
	/************************************* for inner csRate slab request *********************************/

	function csRateSlabRequestEnd(e)
	{
			var csRateSlab = $("#csRateSlabGrid_" + SelectedRowId).data("kendoGrid");

			if (typeof e.response != 'undefined')
			{
				if (e.response.status == "CHILD") 
				{
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
					csRateSlab.refresh();
					csRateSlab.dataSource.read();
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
					csRateSlab.dataSource.read();
					csRateSlab.refresh();
					/* csRateMaster.dataSource.read();
					csRateMaster.refresh();; */
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

					csRateSlab.dataSource.read();
					csRateSlab.refresh();
					/* csRateMaster.dataSource.read();
					csRateMaster.refresh();; */
				}

				else if (e.type == "destroy")
				{
					$("#alertsBox").html("");
					$("#alertsBox").html("Rate Slab delete successfully");
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
					csRateSlab.dataSource.read();
					csRateSlab.refresh();
				}
			}
		        
	}

	/************************************* for inner csRate slab request *********************************/

	function csTODRateRequestEnd(e) {
		var todRates = $("#csTODRate_" + SelectedRowId).data("kendoGrid");
		var csRateMaster = $('#commonServiceRateMasterGrid').data("kendoGrid");
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
				/* csRateMaster.dataSource.read();
				csRateMaster.refresh();
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
			/* 	csRateMaster.dataSource.read();
				csRateMaster.refresh();;
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
				/* csRateMaster.dataSource.read();
				csRateMaster.refresh();;
				return false; */
			}

			else if (e.type == "destroy") {
				$("#alertsBox").html("");
				$("#alertsBox").html("TOD Rates delete successfully");
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
				csRateMaster.dataSource.read();
				csRateMaster.refresh();
				return false;
			}
		}
	}

	//for parsing timestamp 
 	function csTODRateParseFunction(response) {
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

	function csRateSlabStatusChange() {
		var csRsId = "";
		var gridParameter = $("#csRateSlabGrid_" + SelectedRowId).data("kendoGrid");
		var selectedAddressItem = gridParameter
				.dataItem(gridParameter.select());
		csRsId = selectedAddressItem.csRsId;
		var result=securityCheckForActionsForStatus("./Tariff/SolidWaste/RateSlab/activitRateSlab"); 
		if(result=="success")
		{ 
			$.ajax({
				type : "POST",
				url : "./tariff/elRateSlabStatusUpdateFromInnerGrid/" + csRsId,
				dataType:"text",
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
					$('#csRateSlabGrid_' + SelectedRowId).data('kendoGrid').dataSource
							.read();
				}
			});
		}
	}

	function csTODRatesStatusChange() {
		var cstiId = "";
		var gridParameter = $("#csTODRate_" + SelectedRowId).data("kendoGrid");
		var selectedAddressItem = gridParameter
				.dataItem(gridParameter.select());
		cstiId = selectedAddressItem.cstiId;
		$.ajax({
			type : "POST",
			url : "./tariff/elTODRatesStatusUpdateFromInnerGrid/" + cstiId,
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
				$('#csTODRate_' + SelectedRowId).data('kendoGrid').dataSource
						.read();
			}
		});
	}

	function commonServiceTariffMasterEditor(container, options) {
		$(
				'<input name="Tariff Name" id="csTariffName" data-text-field="csTariffName" data-value-field="csTariffId" data-bind="value:' + options.field + '" required="true"/>')
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
							$("#csTariffName").data("kendoComboBox").value("");
						}
						csTariffId = this.dataItem().csTariffId;
					}
				});

		$('<span class="k-invalid-msg" data-for="Tariff Name"></span>')
				.appendTo(container);
	}

	function commonServiceRateNameEditor(container, options) {
		var res = (container.selector).split("=");
		var csRateName = res[1].substring(0, res[1].length - 1);
		$(
				"<select name='Rate Name'id='csRateName' data-bind='value:" + csRateName + "'required='true'/>")
				.appendTo(container).kendoDropDownList({
					dataTextField : "text",
					dataValueField : "value",
					placeholder : "Select Rate Name",
					dataSource : {
						transport : {
							read : "${allChecksUrl}/" + csRateName,
						}
					},
					change : function(e) {
						if (this.value() && this.selectedIndex == -1) {
							alert("Rate Name doesn't exist!");
							$("#csRateName").data("kendoComboBox").value("");
						}
						csRateName1 = this.dataItem().text;
					}
				});

		$('<span class="k-invalid-msg" data-for="Rate Name"></span>').appendTo(
				container);
	}

	function toRateTypeEditor(container, options) {
		var res = (container.selector).split("=");
		var csRateType = res[1].substring(0, res[1].length - 1);
		$(
				"<select name='Rate Type' id='csRateType' data-bind='value:" + csRateType + "'required='true'/>")
				.appendTo(container).kendoDropDownList({
					dataTextField : "text",
					dataValueField : "value",
					placeholder : "Select Rate Type",
					dataSource : {
						transport : {
							read : "${allChecksUrl}/" + csRateType,
						}
					},
					change : function(e) {
						if (this.value() && this.selectedIndex == -1) {
							alert("Rate Type doesn't exist!");
							$("#csRateType").data("kendoComboBox").value("");
						}
						csRateType1 = this.dataItem().text;
					}
				});

		$('<span class="k-invalid-msg" data-for="Rate Type"></span>').appendTo(
				container);
	}
	
	function toRateUOMEditor(container, options) {
		var res = (container.selector).split("=");
		var csRateUOM = res[1].substring(0, res[1].length - 1);
		$(
				"<select name='Rate UOM' data-bind='value:" + csRateUOM + "'required='true'/>")
				.appendTo(container).kendoDropDownList({
					dataTextField : "text",
					dataValueField : "value",
					placeholder : "Select Rate UOM",
					dataSource : {
						transport : {
							read : "${allChecksUrl}/" + csRateUOM,
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
		var csTodType = res[1].substring(0, res[1].length - 1);
		$(
				"<select name='TOD Type' data-bind='value:" + csTodType + "'required='true'/>")
				.appendTo(container).kendoDropDownList({
					dataTextField : "text",
					dataValueField : "value",
					placeholder : "Select TOD Type",
					dataSource : {
						transport : {
							read : "${allChecksUrl}/" + csTodType,
						}
					}
				});

		$('<span class="k-invalid-msg" data-for="TOD Type"></span>').appendTo(
				container);
	}
	
	function csRateMasterParse (response)
	{   
	    $.each(response, function (idx, elem) {
	    	$.each(elem, function (idx1, elem1) 
	    	{
	    		if(idx1 == "csRsId")
		    	{
	    			if(elem1 == null)
	    			{
	    				return false;
	    			}
	    			else
	    			{
	    				return true;
	    			}
		    	}
	    		
	    		if(idx1 == "csSlabNo")
		    	{
		    		nextcsSlabNo = elem1;
		    	}
		    	
		    	if(idx1 == "csSlabFrom")
		    	{
		    		nextcsSlabFrom = elem1;
		    	}
		    	
		    	if(idx1 == "csSlabTo")
		    	{
		    		nextcsSlabTo = elem1;
		    	}
	        });
            
        });
        return response;
	}

 	 $("#commonServiceRateMasterGrid").on("click", ".k-grid-commonServiceShowAllRateMasters", function(e)
 	{
 		 var showAll = "showAll";
        
         $.ajax({
        	 url : "./commonServiceRateMaster/read",  
        	 type : 'GET',
             dataType: "json",
             data:{showAll:showAll},
             contentType: "application/json; charset=utf-8",
             success: function (result)
             {
            	  var grid = $("#commonServiceRateMasterGrid").getKendoGrid();
                  var data = new kendo.data.DataSource();
                  grid.dataSource.data(result);
                  grid.refresh();  
              
             },
         });
	});  

 function csValidFromEditor(container, options)
 {
		$(
		'<input name="Valid From" data-value-field="' + options.field + '" data-bind="value:' + options.field + '" data-format="' + options.format + '" required="true"/>')
		.appendTo(container).kendoDatePicker({
			change : function(e) {
				
				if(parseInt(rateMasterEditFloag) === 1)
				   {
				   
				   }
				else
					{
					$.ajax({
						type : "GET",
						url : '${getMaxDate}',
						dataType : "JSON",
						data : {
							csRateName : csRateName1,
							csTariffId : csTariffId,
							csRateType : csRateType1,
						},
						success : function(response)
						{
							nextFromDate = response;
							
						}
					});
					
				    	selectedDateFrom = this.value();
						var contractStartDtVal = selectedDateFrom.getFullYear()+ '-'+(selectedDateFrom.getMonth() + 1) + '-' + selectedDateFrom.getDate();
						var reqdt = new Date(nextFromDate);
					    if(reqdt!='Invalid Date')
					    	{
					    	var reqDateVal = reqdt.getFullYear()+ '-' + (reqdt.getMonth() + 1)+ '-' + reqdt.getDate();
						    if(contractStartDtVal != reqDateVal)
						     {
						      alert("The valid from date should be "+reqDateVal);  
						      return false;
						     } 
					    	}
					}
			}
		});

		$('<span class="k-invalid-msg" data-for="Valid From"></span>').appendTo(
				container);
	}
 
 $(document).on('change','input[name="csMaxSlabToValue"]',
		   function() {
		    var test = $('input[name="csMaxSlabToValue"]:checked').length ? $('input[name="csMaxSlabToValue"]:checked').val() : '';

		    if (test == "") 
		    {
		        $('label[for="csSlabTo"]').parent("div").show();
		        $('div[data-container-for="csSlabTo"]').show();
		    }
		    if (test == "on")
		    {
		    	$('label[for="csSlabTo"]').parent("div").hide();
		    	$('div[data-container-for="csSlabTo"]').hide();
				$('input[name="csSlabTo"]').prop('required',false);
				$('input[name="csSlabTo"]').val(999999);
		    }
		   });
		 
			
			var csSlabTypeToValidate="";
			function csRateSlabTypeEditor(container, options) {
				var res = (container.selector).split("=");
				var csSlabType = res[1].substring(0, res[1].length - 1);
				$(
						"<select name='Slab Type' id='csSlabType' data-bind='value:" + csSlabType + "'required='true'/>")
						.appendTo(container).kendoDropDownList({
							dataTextField : "text",
							dataValueField : "value",
							placeholder : "Select Slab Type",
							dataSource : {
								transport : {
									read : "${allChecksUrl}/" + csSlabType,
								}
							},
							change : function(e) 
							{
								if (this.value() && this.selectedIndex == -1) {
									alert("Slab Type doesn't exist!");
									$("#csSlabType").data("kendoComboBox").value("");
								}
								csSlabTypeToValidate = this.dataItem().text;
								
								if(csSlabTypeToValidate == "Not applicable")
									{
									$('label[for="csSlabTo"]').parent("div").hide();
							    	$('div[data-container-for="csSlabTo"]').hide();
									$('input[name="csSlabTo"]').prop('required',false);
									$('input[name="csSlabTo"]').val(null);
									
									$('label[for="csSlabFrom"]').parent("div").hide();
							    	$('div[data-container-for="csSlabFrom"]').hide();
									$('input[name="csSlabFrom"]').prop('required',false);
									
									$('label[for="csMaxSlabToValue"]').parent("div").hide();
							    	$('div[data-container-for="csMaxSlabToValue"]').hide();
									$('input[name="csMaxSlabToValue"]').prop('required',false);
									
									}
								else
									{
									
									$('label[for="csSlabTo"]').parent("div").show();
							    	$('div[data-container-for="csSlabTo"]').show();
									
									$('label[for="csSlabFrom"]').parent("div").show();
							    	$('div[data-container-for="csSlabFrom"]').show();
									
									$('label[for="csMaxSlabToValue"]').parent("div").show();
							    	$('div[data-container-for="csMaxSlabToValue"]').show();
									
									}
							}
						});

				$('<span class="k-invalid-msg" data-for="Slab Type"></span>').appendTo(
						container);
			}

			function commonServiceRateMasterDeleteEvent(){
				securityCheckForActions("./Tariff/CommonServices/RateMaster/deleteRateMaster");
				var conf = confirm("Are you sure want to delete this Rate Master?");
				    if(!conf){
				    $('#commonServiceRateMasterGrid').data().kendoGrid.dataSource.read();
				    throw new Error('deletion aborted');
				     }
			}
			
			function commonServiceRateSlabDeleteEvent(){
				securityCheckForActions("./Tariff/CommonServices/RateSlab/deleteRateSlab");
				var conf = confirm("Are you sure want to delete this Rate Slab?");
				    if(!conf){
				    $('#csRateSlabGrid_' + SelectedRowId).data().kendoGrid.dataSource.read();
				    throw new Error('deletion aborted');
				     }
			}			
			
			
</script>
