<%@include file="/common/taglibs.jsp"%>

<c:url value="/gasRateMaster/read" var="gasRatemasterReadUrl" />
<c:url value="/gasRateMaster/create" var="gasRatemasterCreateUrl" />
<c:url value="/gasRateMaster/update" var="gasRatemasterUpdateUrl" />
<c:url value="/gasRateMaster/destroy" var="gasRatemasterDestroyUrl" />
<c:url value="/common/getAllChecks" var="allChecksUrl" />
<c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />
<c:url value="/gasRateMaster/getToTariffMasterComboBoxUrl" var="gasTariffMasterComboBoxUrl" />
<c:url value="/common/getAllChecks" var="allChecksUrl" />

<!-- for rate slabs -->
<c:url value="/tariff/gasRateslab/read" var="gasRateSlabReadUrl" />
<c:url value="/tariff/gasRateslab/create" var="gasRateSlabCreateUrl" />
<c:url value="/tariff/gasRateslab/update" var="gasRateSlabUpdateUrl" />
<c:url value="/tariff/gasRateslab/destroy" var="gasRateSlabDeleteUrl" />

<c:url value="/gasTariff/getMaxDate" var="getMaxDate"></c:url>

<!-- Filters Data url's -->
<c:url value="/gasTariff/filter" var="commonFilterUrl" />
<c:url value="/gasTariffNameToFilter/filter" var="gasTariffNameToFilter" />

<!-- for merging ids -->

<kendo:grid name="gasRateMasterGrid" remove="gasRateMasterDeleteEvent" resizable="true" pageable="true"  selectable="true" edit="gasRrateMasterEvent" change="onChangeGasRateMaster" detailTemplate="gasRateMasterSubGrid" sortable="true" scrollable="true" groupable="true">
	
	<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" ></kendo:grid-pageable>
	<kendo:grid-filterable extra="false">
		<kendo:grid-filterable-operators>
			<kendo:grid-filterable-operators-string eq="Is equal to" />
		</kendo:grid-filterable-operators>

	</kendo:grid-filterable>
	<kendo:grid-editable mode="popup" />
	<kendo:grid-toolbar>
		<kendo:grid-toolbarItem name="create" text="Add Rate Master" />
		<kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterGasRateMaster()><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>"/>
		<kendo:grid-toolbarItem name="showAllGasRateMasters" text="Show All" />
	</kendo:grid-toolbar>
	<kendo:grid-columns>

		<kendo:grid-column title="GAS_RM_ID" field="gasrmid" width="110px" hidden="true" />
		
		<kendo:grid-column title="Tariff Master *" field="gasTariffName" width="100px">
		
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function wtTariffNameFilter(element) {
							element
									.kendoAutoComplete({
										placeholder : "Enter Tariff Name",
										dataType : 'JSON',
										dataSource : {
											transport : {
												read : "${gasTariffNameToFilter}/gastariffName"
											}
										}
									});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>
		
		<kendo:grid-column title="Tariff Master *" field="gasTariffId" editor="gasTariffMasterEditor" width="100px" hidden="true"> 
		</kendo:grid-column>

		<kendo:grid-column title="Rate&nbsp;Name*" field="gasRateName" editor="gasRateNameEditor" width="85px">
			<kendo:grid-column-values value="${gasRateName}" />
		</kendo:grid-column>

		<kendo:grid-column title="Rate&nbsp;Description*"
			field="gasRateDescription" filterable="true" width="120px">
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function wtRateDescriptionFilter(element) {
							element
									.kendoAutoComplete({
										placeholder : "Enter Description",
										dataType : 'JSON',
										dataSource : {
											transport : {
												read : "${commonFilterUrl}/gasRateDescription"
											}
										}
									});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>

		<kendo:grid-column title="Rate&nbsp;Type&nbsp;*" field="gasRateType" editor="gasRateTypeEditor" width="90px">
			<kendo:grid-column-values value="${gasRateType}" />
		</kendo:grid-column>

		<kendo:grid-column title="Rate&nbsp;UOM&nbsp;*" field="gasRateUOM"  width="90px">
			<kendo:grid-column-values value="${gasRateUOM}" />
		</kendo:grid-column>

		<kendo:grid-column title="Valid&nbsp;From&nbsp;*" field="gasValidFrom" editor="gasValidFromEditor" format="{0:dd/MM/yyyy}" width="90px" >
		
		<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function gasValidFromFilter(element) {
							element.kendoDatePicker({
							        format: "dd/MM/yyyy"
							});
						}
					</script>
				</kendo:grid-column-filterable-ui>
		</kendo:grid-column-filterable>
			
		</kendo:grid-column>

		<kendo:grid-column title="Valid&nbsp;To&nbsp;*" field="gasValidTo" format="{0:dd/MM/yyyy}" width="80px">
				<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function gasValidToFilter(element) {
							element.kendoDatePicker({
								 format: "dd/MM/yyyy"
							});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column> 

		<kendo:grid-column title="Min Rate(In Rs)" field="gasMinRate" width="100px" >
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function wtMinRateFilter(element) {
							element.kendoNumericTextBox({});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>
		<kendo:grid-column title="Max Rate(In Rs)" field="gasMaxRate" width="100px" >
		<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function wtMaxRateToFilter(element) {
							element.kendoNumericTextBox({});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
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
		
<%-- 	 <kendo:grid-column title="&nbsp;" width="100px">
		    <kendo:grid-column-command >
		      <kendo:grid-column-commandItem name="End Date" click="gasRateMasterEndDateClick" />
		    </kendo:grid-column-command>
      </kendo:grid-column> --%>

		<kendo:grid-column title=""
			template="<a href='\\\#' id='gasTemPID' class='k-button k-button-icontext btn-destroy k-grid-Active#=data.gasrmid#'>#= data.status == 'Active' ? 'Inactive' : 'Active' #</a>"
			width="70px" />
	   </kendo:grid-columns>

	<kendo:dataSource pageSize="20" requestEnd="gasRequestEndRateMaster" requestStart="gasRequestStartRateMaster">
	
		<kendo:dataSource-transport>
			<kendo:dataSource-transport-create url="${gasRatemasterCreateUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-read url="${gasRatemasterReadUrl}" dataType="json" type="POST" contentType="application/json" />
			<kendo:dataSource-transport-update url="${gasRatemasterUpdateUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-destroy url="${gasRatemasterDestroyUrl}" dataType="json" type="GET" contentType="application/json" />
		</kendo:dataSource-transport>

		<kendo:dataSource-schema>
			<kendo:dataSource-schema-model id="gasrmid">
				<kendo:dataSource-schema-model-fields>

					<kendo:dataSource-schema-model-field name="gasrmid" type="number"/>

					<kendo:dataSource-schema-model-field name="gasTariffId" type="string" defaultValue="Select">
						<kendo:dataSource-schema-model-field-validation />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="gasRateName"  defaultValue="Non Subsidy">
						<kendo:dataSource-schema-model-field-validation />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="gasRateDescription" type="string">
						<kendo:dataSource-schema-model-field-validation />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="gasRateType"  defaultValue="Single Slab" />

					<kendo:dataSource-schema-model-field name="gasRateUOM"  defaultValue="KG">
						<kendo:dataSource-schema-model-field-validation required="true" />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="gasValidFrom" type="date">
						<kendo:dataSource-schema-model-field-validation required="true" />
					</kendo:dataSource-schema-model-field>

				 	<kendo:dataSource-schema-model-field name="gasValidTo" type="date">
					</kendo:dataSource-schema-model-field> 

					<kendo:dataSource-schema-model-field name="gasMinRate" type="number">
						<kendo:dataSource-schema-model-field-validation min="0" />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="gasMaxRate" type="number">
						<kendo:dataSource-schema-model-field-validation min="0" />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="status" editable="true" type="string" />
					
					<kendo:dataSource-schema-model-field name="gasTariffName" type="string" />
					
				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>

	</kendo:dataSource>
</kendo:grid>

<kendo:grid-detailTemplate id="gasRateMasterSubGrid">
	<kendo:tabStrip name="gasRateMasterTabStrip_#=gasrmid#" >
		<kendo:tabStrip-items>

			<kendo:tabStrip-item text="Rate Slab" selected="true">
				<kendo:tabStrip-item-content>
					<div class='gasRateSlbDIV'>
						<br />
						<kendo:grid name="gasRateSlabGrid_#=gasrmid#" pageable="true" filterable="true" dataBound="CheckBoxDataBoundGas"
							change="onGridChangeGas" resizable="true" sortable="true"
							reorderable="true" selectable="true" scrollable="true"
							edit="gasRateSlabEditEvent" remove="waterRateSlabDeleteEvent">
							
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
									<!-- File Upload Button Purpose -->
								</kendo:grid-column>

								<kendo:grid-column title="WT_RS_ID" field="gasrsId" hidden="true" width="70px" />
								
								<kendo:grid-column title="Slab Number *" field="gasSlabNo" width="70px" filterable="false" />
								
								<kendo:grid-column title="Slab Type *" field="gasSlabType" editor="gasRateSlabTypeEditor" width="70px" filterable="false">
									<kendo:grid-column-values value="${gasSlabType}" />
								</kendo:grid-column>

							    <kendo:grid-column title="Slab From *" field="gasDummySlabFrom" width="80px" filterable="false" /> 
								<kendo:grid-column title="Slab From *" field="gasSlabFrom" width="80px" hidden="true" filterable="false" />
								
								<kendo:grid-column title="Max Value" field="gasMaxSlabToValue" filterable="false" sortable="false" width="0px" hidden="true" />
								<kendo:grid-column title="Slab To *" field="gasDummySlabTo" width="80px" filterable="false" />
								<kendo:grid-column title="Slab To *" field="gasSlabTo" width="70px" hidden="true" filterable="false" />
								
								<kendo:grid-column title="Rate Type *" field="gasSlabRateType" width="70px" filterable="false" >
									<kendo:grid-column-values value="${gasSlabRateType}" />
								</kendo:grid-column>
								
								<kendo:grid-column title="Rate *" field="gasRate" width="70px" filterable="false" />
								
								<kendo:grid-column title="Slab Status *" field="status" width="70px" filterable="false" >
									<kendo:grid-column-values value="${status}" />
								</kendo:grid-column>

								<kendo:grid-column title="&nbsp;" width="250px">
									<kendo:grid-column-command>
									<kendo:grid-column-commandItem name="Split" click="gasSplitFunction" />
										<kendo:grid-column-commandItem name="edit" />
										<kendo:grid-column-commandItem name="destroy" />
										<kendo:grid-column-commandItem name="Change Status" click="gasRateSlabStatusChange" />
									</kendo:grid-column-command>
								</kendo:grid-column>

							</kendo:grid-columns>

							<kendo:dataSource requestEnd="gasRateSlabRequestEnd" requestStart="gasRateSlabRequestStart">
								<kendo:dataSource-transport>
									<kendo:dataSource-transport-read url="${gasRateSlabReadUrl}/#=gasrmid#" dataType="json" type="POST" contentType="application/json" />
									<kendo:dataSource-transport-create url="${gasRateSlabCreateUrl}/#=gasrmid#" dataType="json" type="GET" contentType="application/json" />
									<kendo:dataSource-transport-update url="${gasRateSlabUpdateUrl}" dataType="json" type="GET" contentType="application/json" />
									<kendo:dataSource-transport-destroy url="${gasRateSlabDeleteUrl}" dataType="json" type="GET" contentType="application/json" />
								</kendo:dataSource-transport>

								<kendo:dataSource-schema parse="gasRateMasterParse">
									<kendo:dataSource-schema-model id="gasrsId">
										<kendo:dataSource-schema-model-fields>

											<kendo:dataSource-schema-model-field name="gasrsId"/>

											<kendo:dataSource-schema-model-field name="gasSlabNo" type="number">
												<kendo:dataSource-schema-model-field-validation min="1" />
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="gasSlabType" type="String" defaultValue="Numeric"/>

											<kendo:dataSource-schema-model-field name="gasSlabRateType" type="String" defaultValue="Paise"/>

											<kendo:dataSource-schema-model-field name="gasRate" type="number">
												<kendo:dataSource-schema-model-field-validation min="0" />
											</kendo:dataSource-schema-model-field>

											<kendo:dataSource-schema-model-field name="gasSlabFrom" type="number">
											<kendo:dataSource-schema-model-field-validation min="0" max="999999"/>
											</kendo:dataSource-schema-model-field>
											
										   <kendo:dataSource-schema-model-field name="gasDummySlabFrom" type="String"/>
											
											<kendo:dataSource-schema-model-field name="gasMaxSlabToValue" type="boolean"/>
											
											<kendo:dataSource-schema-model-field name="gasSlabTo" type="number">
											<kendo:dataSource-schema-model-field-validation min="0" max="999999"/>
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="gasDummySlabTo" type="String"/>
											
											<kendo:dataSource-schema-model-field name="status" type="string" editable="true" />

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

<div id="waterAlertsBox" title="Alert"></div>

<div id="waterdialog-form" title="Reply to sender"
	style="width: 6000px; height: 6000px">
	<form>
		<fieldset>
			<label for="name">Enter New Rate</label> <input type="text" name="newrate" id="newrate" class="text ui-widget-content ui-corner-all">
		</fieldset>
	</form>
</div>

<div id="waterdialog-formSplit" title="Enter New values" style="width: 600px; height: 700px">
	<!-- <form id="formtoSplit" method="post"> -->
	<form>
		<table
			style="text-align: center; vertical-align: middle; width: 300px; margin-left: -45px;">
			<caption>Form 1</caption>
			<tr>
				<td style="height: 10px; width: 100px; text-align: center; vertical-align: middle;">Slab Number</td>
				<td style="height: 40px; width: 100px; text-align: center; vertical-align: middle;">
					<input type="number" name="gasSlabNo" id="gasSlabNo" class="text ui-widget-content ui-corner-all" readonly="readonly">
				</td>
			</tr>

			<tr>
				<td style="height: 10px; width: 100px; text-align: center; vertical-align: middle;">Rate</td>
				<td style="height: 40px; width: 100px; text-align: center; vertical-align: middle;">
					<input type="number" name="gasRate" id="gasRate" class="text ui-widget-content ui-corner-all">
				</td>
			</tr>

			<tr>
				<td style="height: 10px; width: 100px; text-align: center; vertical-align: middle;">Slab From</td>
				<td style="height: 40px; width: 100px; text-align: center; vertical-align: middle;">
					<input type="number" name="gasSlabFrom" id="gasSlabFrom" class="text ui-widget-content ui-corner-all" readonly="readonly">
				</td>
			</tr>

			<tr>
				<td style="height: 10px; width: 100px; text-align: center; vertical-align: middle;">Slab To</td>
				<td style="height: 40px; width: 100px; text-align: center; vertical-align: middle;">
					<input type="number" name="gasSlabTo" id="gasSlabTo" class="text ui-widget-content ui-corner-all">
				</td>
			</tr>
			<tr>
				<td><input type="hidden" name="hiddenelrsName" id="hiddenwtrsId"></td>
			</tr>
		</table>

		<table
			style="text-align: center; vertical-align: middle; width: 300px; margin-left: 250px; margin-top: -180px">
			<caption>Form 2</caption>
			<tr>
				<td style="height: 10px; width: 100px; text-align: center; vertical-align: middle;">Slab Number</td>
				<td style="height: 40px; width: 100px; text-align: center; vertical-align: middle;">
					<input type="number" name="wtSlabNo1" id="wtSlabNo1" class="text ui-widget-content ui-corner-all" readonly="readonly">
				</td>
			</tr>

			<tr>
				<td style="height: 10px; width: 100px; text-align: center; vertical-align: middle;">Rate</td>
				<td style="height: 40px; width: 100px; text-align: center; vertical-align: middle;">
					<input type="number" name="rate1" id="rate1" class="text ui-widget-content ui-corner-all">
				</td>
			</tr>

			<tr>
				<td style="height: 10px; width: 100px; text-align: center; vertical-align: middle;">Slab From</td>
				<td style="height: 40px; width: 100px; text-align: center; vertical-align: middle;">
					<input type="number" name="slabFrom1" id="slabFrom1" class="text ui-widget-content ui-corner-all">
				</td>
			</tr>

			<tr>
				<td style="height: 10px; width: 100px; text-align: center; vertical-align: middle;">Slab To</td>
				<td style="height: 40px; width: 100px; text-align: center; vertical-align: middle;">
					<input type="number" name="slabTo1" id="slabTo1" class="text ui-widget-content ui-corner-all" readonly="readonly">
				</td>
			</tr>
		</table>
	</form>
</div>
<script>

var nextwtSlabNo = 0; 
var nextslabFrom = 0;
var nextslabTo = 0;

var elRateSlabMergeDialog;
var elRateSlabSplitDialog;

var gasTariffId;
var wtRateName1;
var wtRateType1;
var nextFromDate;
var slabFromToCompare;
var slabToToCompare;
var editFloag = 0;
var selectedDateFrom;
var rateMasterEditFloag = 0;

	elRateSlabSplitDialog = $("#waterdialog-formSplit")
			.dialog({
						autoOpen : false,
						height : 400,
						width : 600,
						modal : true,
						buttons : {
							"Save" : function() {
	 						    var gasSlabNo = $("#gasSlabNo").val();
								var gasRate = $("#gasRate").val();
								var gasSlabFrom = $("#gasSlabFrom").val();
								var gasSlabTo = $("#gasSlabTo").val();
								var hiddenwtrsId = $("#hiddenwtrsId").val();
								var wtSlabNo1 = $("#wtSlabNo1").val();
								var rate1 = $("#rate1").val();
								var slabFrom1 = $("#slabFrom1").val();
								var slabTo1 = $("#slabTo1").val();
								
								  if (hiddenwtrsId == "") {
									alert("form1 Slab number should not be empty.");
									return false;
								}

								if (gasSlabNo == "") {
									alert("form1 Slab number should not be empty.");
									return false;
								}
								
								if (gasRate == "") {
									alert("form1 Rate should not be empty.");
									return false;
								}
								else
								{
									if (parseInt(gasRate) < 0) 
									{
										alert("form1 Rate should greater than 1.");
										return false;
									}
									
								}
								
								if (gasSlabFrom == "") {
									alert("form1 Slab from should not be empty.");
									return false;
								}

								if (gasSlabTo == "") {
									alert("form1 Slab To should not be empty.");
									return false;
								} else {
									if (parseInt(gasSlabTo) <= parseInt(gasSlabFrom)) {
										alert("form1 Slab To always greater than slab from");
										return false;
									}
								}
								
								if (wtSlabNo1 == "") {
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
									var form1SlabTo = gasSlabTo;
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
							
								$.ajax({
									        type : "POST",
											url : "./tariff/gasRateslab/rateSlabSplit",
											data : {
												hiddenwtrsId : hiddenwtrsId,
												gasSlabNo : gasSlabNo,
												gasRate : gasRate,
												gasSlabFrom : gasSlabFrom,
												gasSlabTo : gasSlabTo,
												wtSlabNo1 : wtSlabNo1,
												rate1 : rate1,
												slabFrom1 : slabFrom1,
												slabTo1 : slabTo1,

											}, 
											success : function(response) {
												if (response.status == "SUCCESS") {
													errorInfo = JSON.stringify(response.result);
													$("#waterAlertsBox").html("");
													$("#waterAlertsBox").html(
															"" + errorInfo);
													$("#waterAlertsBox")
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
															'#gasRateSlabGrid_'
																	+ SelectedRowId)
															.data().kendoGrid.dataSource
															.read();
													return false;
												}

												if (response.status == "SINGLESLAB") {
													errorInfo = JSON
															.stringify(response.result);

													$("#waterAlertsBox").html("");
													$("#waterAlertsBox").html(
															"" + errorInfo);
													$("#waterAlertsBox")
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

	function CheckBoxDataBoundGas(e)
	{
		var grid = $("#gasRateSlabGrid_" + SelectedRowId).data("kendoGrid");
		var gridData = grid._data;
		var i = 0;
		this.tbody
				.find("tr td:first-child")
				.each(
						function(e) {
							$(
									'<input type="checkbox" name="chkbox" id="singleSelectChkBx_'
											+ gridData[i].gasrsId
											+ '" onclick="selectSingleCheckBox(this.id)"  />')
									.appendTo(this);
							i++;
						});
	}

	elRateSlabMergeDialog = $("#waterdialog-form")
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
									if (newrate <= 0) {
										alert("New gasRate should be greater than 1.");
										return false;
									}
									alert("New gasRate should not be empty.");
									return false;
								}

								$
										.ajax({
											type : "POST",
											data : {
												newrate : newrate,
											},
											url : "./tariff/gasRateslab/getNewRate",
											success : function(response) {
												if (response.status == "SINGLESLAB") {
													errorInfo = JSON
															.stringify(response.result);

													$("#waterAlertsBox").html("");
													$("#waterAlertsBox").html(
															"" + errorInfo);
													$("#waterAlertsBox")
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
													$("#waterAlertsBox").html("");
													$("#waterAlertsBox").html(
															"" + errorInfo);
													$("#waterAlertsBox")
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
															'#gasRateSlabGrid_'
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

	$("#gasRateMasterGrid")
			.on(
					"click",
					".k-grid-Merge",
					function() {
						
				 		var result=securityCheckForActionsForStatus("./Tariff/Gas/RateSlab/mergeRateSlab"); 
						if(result=="success"){ 
						
						if((rateMasterSlabType === 'Single Slab') && (parseInt(nextwtSlabNo) === 1))
						{
						 var grid = $("#gasRateSlabGrid_" + SelectedRowId).data("kendoGrid");
						 grid.cancelRow();
					     alert("Only one gasRate slab allowed for single slab gasRate master.");
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
									url : "./tariff/gasRateslab/merge",
									data : {
										"wtrsIds" : globalElrsId.toString()
									},
									success : function(response) {
										if (response.status == "INPUT") {
											elRateSlabMergeDialog.dialog("open");
										}

										if (response.status == "SINGLESLAB") {
											errorInfo = JSON.stringify(response.result);

											$("#waterAlertsBox").html("");
											$("#waterAlertsBox")
													.html("" + errorInfo);
											$("#waterAlertsBox")
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
													'#gasRateSlabGrid_'
															+ SelectedRowId)
													.data().kendoGrid.dataSource
													.read();
											rateSlabGrid.refresh();
											elRateSlabMergeDialog
													.dialog("close");
											return false;
										}

										if (response.status == "SUCCESS") {
											errorInfo = JSON
													.stringify(response.result);
											$("#waterAlertsBox").html("");
											$("#waterAlertsBox")
													.html("" + errorInfo);
											$("#waterAlertsBox")
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
													'#gasRateSlabGrid_'
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
	var rateMasterSlabType ="";
	function onChangeGasRateMaster(arg) {
		var gview = $("#gasRateMasterGrid").data("kendoGrid");
		var selectedItem = gview.dataItem(gview.select());
		SelectedRowId = selectedItem.gasrmid;
		rateMasterSlabType = selectedItem.gasRateType;
		this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));  	 
	     $(".k-master-row.k-state-selected").show();
	}


	var gasrsId = "";
	var gasSlabNo = "";
	var gasRate = "";
	var gasSlabFrom = "";
	var gasSlabTo = "";
	function gasSplitFunction() 
	{
 		var result=securityCheckForActionsForStatus("./Tariff/Gas/RateSlab/splitRateSlab"); 
		if(result=="success"){ 
			if((rateMasterSlabType === 'Single Slab') && (parseInt(nextwtSlabNo) === 1))
			{
			 var grid = $("#gasRateSlabGrid_" + SelectedRowId).data("kendoGrid");
			 grid.cancelRow();
		     alert("Only one gasRate slab allowed for single slab gasRate master.");
		     return false;
			}
			
			elRateSlabSplitDialog.dialog("open");
			$("#hiddenwtrsId").val(gasrsId);
			$("#gasSlabNo").val(gasSlabNo);
			var wtSlabNo1 = gasSlabNo + 1;
			$("#wtSlabNo1").val(wtSlabNo1);
			$("#gasRate").val(gasRate);
			$("#gasSlabFrom").val(gasSlabFrom);
			$("#slabTo1").val(gasSlabTo);
		}
	}

	function onGridChangeGas() {
		var elRateSlabGrid = $("#gasRateSlabGrid_" + SelectedRowId)
				.data("kendoGrid");
		var selectedRowItem = elRateSlabGrid.dataItem(elRateSlabGrid.select());
		gasrsId = selectedRowItem.gasrsId;
		gasSlabNo = selectedRowItem.gasSlabNo;
		gasRate = selectedRowItem.gasRate;
		gasSlabFrom = selectedRowItem.gasSlabFrom;
		gasSlabTo = selectedRowItem.gasSlabTo;
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

	$("#gasRateMasterGrid").on("click", "#gasTemPID", function(e) {
  		var result=securityCheckForActionsForStatus("./Tariff/Gas/RateMaster/activitRateMaster"); 
	if(result=="success"){ 
		var button = $(this),
		enable = button.text() == "Active";
		var widget = $("#gasRateMasterGrid").data("kendoGrid");
		var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));

		if (enable)
		{
			$.ajax({
				type : "POST",
				url : "./gasTariff/tariffStatus/" + dataItem.gasrmid + "/activate",
				dataType:"text",
				success : function(response) {
					$("#waterAlertsBox").html("");
					$("#waterAlertsBox").html(response);
					$("#waterAlertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
					button.text('Inactive');
					$('#gasRateMasterGrid').data('kendoGrid').dataSource.read();
				}
			});
		} else {
			$.ajax({
				type : "POST",
				url : "./gasTariff/tariffStatus/" + dataItem.gasrmid + "/deactivate",
				success : function(response) {
					$("#waterAlertsBox").html("");
					$("#waterAlertsBox").html(response);
					$("#waterAlertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
					button.text('Active');
					$('#gasRateMasterGrid').data('kendoGrid').dataSource.read();
				}
			});
		}
	}
	});

	/*------------------------- activate and deactive for child table ---------------------------  */

/*	var SelectedItemMasterRowId = "";
	 function onChangeItemMaster(arg) {
		var gview = $("#elRateSlabGrid_").data("kendoGrid");
		var selectedItem = gview.dataItem(gview.select());
		SelectedItemMasterRowId = selectedItem.imId;
		this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
	} */
	
	
	var res = "";
	function gasRrateMasterEvent(e)
	{

		/***************************  to remove the id from pop up  **********************/
		$('div[data-container-for="gasrmid"]').remove();
		$('label[for="gasrmid"]').closest('.k-edit-label').remove();
		
		$('div[data-container-for="gasTariffName"]').remove();
		$('label[for="gasTariffName"]').closest('.k-edit-label').remove();
		
		$(".k-edit-field").each(function() {
			$(this).find("#gasTemPID").parent().remove();
		});

		$('label[for="status"]').parent().hide();
		$('div[data-container-for="status"]').hide();
		
		/************************* Button Alerts *************************/
	 	if (e.model.isNew()) {
			
	 		securityCheckForActions("./Tariff/Gas/RateMaster/createRateMaster");
			$(".k-window-title").text("Add New Rate Master");
			$(".k-grid-update").text("Save");
			
			gasTariffId =  e.model.gasTariffId;
			wtRateName1 = e.model.gasRateName;
			wtRateType1 = e.model.gasRateType;
			selectedDate  =  e.model.gasValidFrom;
			selectedDateFrom = e.model.gasValidFrom;
		} 
	 	else
		{
	 		securityCheckForActions("./Tariff/Gas/RateMaster/updateRateMaster");
	 		rateMasterEditFloag = 1;
			$.ajax({
				type : "GET",
				url : '${getMaxDate}',
				dataType : "JSON",
				data : {
					gasRateName : e.model.gasRateName,
					gasTariffId : e.model.gasTariffId,
					gasRateType : e.model.gasRateType,
				},
				success : function(response)
				{
					nextFromDate = response;
				}
			});
		    selectedDateFrom = e.model.gasValidFrom;
			$(".k-window-title").text("Edit Rate Master Details");
		} 
		
	}

	/********************** to hide the child table id ***************************/
	function gasRateSlabEditEvent(e) {

		$('div[data-container-for="gasrsId"]').remove();
		$('label[for="gasrsId"]').closest('.k-edit-label').remove();

		$('div[data-container-for="status"]').remove();
		$('label[for="status"]').closest('.k-edit-label').remove();
		
		$('div[data-container-for="gasDummySlabTo"]').remove();
		$('label[for="gasDummySlabTo"]').closest('.k-edit-label').remove();
		
		$('div[data-container-for="gasDummySlabFrom"]').remove();
		$('label[for="gasDummySlabFrom"]').closest('.k-edit-label').remove();

		if (e.model.isNew()) 
		{
			securityCheckForActions("./Tariff/Gas/RateSlab/createRateSlab");
			
			
			if((rateMasterSlabType === 'Single Slab') && (parseInt(nextwtSlabNo) === 1))
					{
					 var grid = $("#gasRateSlabGrid_" + SelectedRowId).data("kendoGrid");
					 grid.cancelRow();
				     alert("Only one gasRate slab allowed for single slab gasRate master.");
				     return false;
					}
			
			if(parseInt(nextslabTo) === 999999)
				{
				 var grid = $("#gasRateSlabGrid_" + SelectedRowId).data("kendoGrid");
				 grid.cancelRow();
			     alert("You can't the add the new record,Slab to Max is the last record.");
			     return false;
				}
			
			$(".k-window-title").text("Add New Rate Slab");
			$(".k-grid-update").text("Save");
		} else 
		{
			editFloag = 1;
			securityCheckForActions("./Tariff/Gas/RateSlab/updateRateSlab");
			if(e.model.gasSlabType == "Not applicable")
			{
			$('label[for="gasSlabTo"]').hide();
	    	$('div[data-container-for="gasSlabTo"]').hide();
			$('input[name="gasSlabTo"]').prop('required',false);
			$('input[name="gasSlabTo"]').val(null);
			
			$('label[for="gasSlabFrom"]').hide();
	    	$('div[data-container-for="gasSlabFrom"]').hide();
			$('input[name="gasSlabFrom"]').prop('required',false);
			
			$('label[for="gasMaxSlabToValue"]').hide();
	    	$('div[data-container-for="gasMaxSlabToValue"]').hide();
			$('input[name="gasMaxSlabToValue"]').prop('required',false);
			
			}
		else
			{
			
			$('label[for="gasSlabTo"]').show();
	    	$('div[data-container-for="gasSlabTo"]').show();
			
			$('label[for="gasSlabFrom"]').show();
	    	$('div[data-container-for="gasSlabFrom"]').show();
			
			$('label[for="gasMaxSlabToValue"]').show();
	    	$('div[data-container-for="gasMaxSlabToValue"]').show();
			
			}
			    if (e.model.gasSlabTo == parseInt(999999))
			    {
			    	$('input[name="gasMaxSlabToValue"]').prop('checked', true);
			    	$('label[for="gasSlabTo"]').hide();
			    	$('div[data-container-for="gasSlabTo"]').hide();
					$('input[name="gasSlabTo"]').prop('required',false);
					$('input[name="gasSlabTo"]').val(999999);
			    }
			
			$(".k-window-title").text("Edit Rate Slab Details");
		}
	}

	 function clearFilterGasRateMaster()
	 {
		 $("form.k-filter-menu button[type='reset']").slice().trigger("click");
		   var elRatemaster = $("#waterRateMasterGrid").data("kendoGrid");
		   elRatemaster.dataSource.read();
		   elRatemaster.refresh();
	   /*  $("form.k-filter-menu button[type='reset']").slice().trigger("click");
        $.ajax({
       	 url : "./waterRateMaster/read",  
       	 type : 'GET',
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            success: function (result)
            {
           	  var grid = $("#gasRateMasterGrid").getKendoGrid();
                 var data = new kendo.data.DataSource();
                 grid.dataSource.data(result);
                 grid.refresh();  
             
            },
        }); */
	  //  RenderLinkInner('rateMaster');
		
	 }

	/***************************  Custom message validation  **********************/

	var seasonFromAddress = "";
	(function($, kendo) {
		$.extend(true,kendo.ui.validator,{
							rules : {
								wtTariffNameValidater : function(input, params) {
									if ((input.attr("name") == "Tariff Name") && (input.val() == 0)) {
										return false;
									}
									return true;
								},
								wtRateNameValidation : function(input, params) {

									if (input.attr("name") == "gasRateName") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},
								descriptionValidation : function(input, params) {

									if (input.attr("name") == "gasRateDescription") {
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

								gasRateType : function(input, params) {
									if (input.attr("name") == "gasRateType") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},

								gasRateUOM : function(input, params) {
									if (input.attr("name") == "gasRateUOM") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},

								/* addressSeasonTo2 : function(input, params)
								{
									if (input.filter("[name = 'gasValidTo']").length && input.val() != "")
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
								},  */
								
								wtRateUOMValidation : function(input, params) {
									if (input.filter("[name='gasRateUOM']").length
											&& input.val() != "") {
										return /^[a-zA-Z0-9 ]{1,45}$/
												.test(input.val());
									}
									return true;
								},

								/************** for Inner Grid Validation ************/
								slabNoValidator : function(input, params) {
									if (input.attr("name") == "gasSlabNo") 
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
										if ((input.attr("name") == "gasSlabNo") && (parseInt(input.val()) !=  parseInt((nextwtSlabNo+1))))
										{
												 return false;
										 }
								   }
								
								  	return true;
								}, 
								rateValidator : function(input, params)
								{
									if  ((input.attr("name") == "gasRate") && (input.val() == 0)) {
										return false;
									}
									return true;
								}, 
								slabToValidator : function(input, params)
								{
									if  ((input.attr("name") == "gasSlabTo") && (input.val() == 0))
									{
										return false;
									}
									return true;
								},
							
								slabToGreaterThanSlabFrom : function(input, params)
								{
									if (input.attr("name") == "gasSlabTo") 
									{
										slabToToCompare = input.val();
									}
									if (input.attr("name") == "gasSlabFrom")
									{
										slabFromToCompare = input.val();
									}
									if (input.attr("name") == "gasSlabTo") 
									{
									if(parseInt(slabFromToCompare) > parseInt(slabToToCompare))
										{
										return false;
										}
									else{
										return true;
									}
									}
									return true;
								},
							slabFromValidator : function(input, params) 
							{
								
									if ((input.attr("name") == "gasSlabFrom") && (parseInt(input.val()) < 0)) 
									{
										return false;
									}
									return true;
							},
								slabToValidator : function(input, params)
								{
									if  ((input.attr("name") == "gasSlabTo") && (parseInt(input.val()) <= 0))
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
								wtTariffNameValidater : "Tariff name is not selected",
								wtRateNameValidation : "Rate name should not be empty",
								descriptionValidation : "Description should not be empty",
								rateofunit : "Rate per unit should not be empty",
								gasRateType : "Rate Type should not be empty",
								gasRateUOM : "UOM should not be empty",
								validatinForNumbers : "Only numbers are allowed,follwed by two decimal points",
								addressSeasonFrom : "From Date must be selected in the future",
								addressSeasonTo1 : "Select From date first before selecting To date and change To date accordingly",
								wtRateUOMValidation : "Rate UOM can contain alphabets and spaces but cannot allow numbers and other special characters and maximum 45 characters are allowed",
								addressSeasonTo2 : "To date should be after From date",
								slabNoValidator : "Slab Number Cannot Be Empty",
								rateValidator : "Rate is required",
								slabFromValidator : "Slab From fequired",
								slabToValidator : "Slab To required",
								nextSlabNoValidator :"Next Slab Number should start with maximum number +1",
								slabToGreaterThanSlabFrom : "Slab To number always greater than Slab From",
								todValidFromDate : "From Date not be empty",
								todValidToDate : "To date should be after From date",
								
							}
						});
	})(jQuery, kendo);

	function gasRequestStartRateMaster(e){
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();	
	}
	
	function gasRateSlabRequestStart(e){
		
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();	
	}
	function gasRequestEndRateMaster(r) {

		if (typeof r.response != 'undefined') {

			if (r.response.status == "CHILD") {

				$("#waterAlertsBox").html("");
				$("#waterAlertsBox").html(
						"Can't Delete Rate Master, Child Record Found");
				$("#waterAlertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				$('#gasRateMasterGrid').data('kendoGrid').dataSource.read();
				$('#gasRateMasterGrid').data('kendoGrid').refresh();
			}

			if (r.response.status == "SINGLESLAB") {

				errorInfo = JSON.stringify(r.response.result);

				$("#waterAlertsBox").html("");
				$("#waterAlertsBox").html("");
				$("#waterAlertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				$('#gasRateMasterGrid').data('kendoGrid').dataSource.read();
				$('#gasRateMasterGrid').data('kendoGrid').refresh();
			}

			if (r.type == "update") {
				$("#waterAlertsBox").html("");
				$("#waterAlertsBox").html(
						"Updating the Rate Master<br>");
				$("#waterAlertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				$('#gasRateMasterGrid').data('kendoGrid').dataSource.read();
				$('#gasRateMasterGrid').data('kendoGrid').refresh();
			}
			
			else if (r.type == "create") {
				$("#waterAlertsBox").html("");
				$("#waterAlertsBox").html("Rate Master created successfully");
				$("#waterAlertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				$('#gasRateMasterGrid').data('kendoGrid').dataSource.read();
				$('#gasRateMasterGrid').data('kendoGrid').refresh();
			}
			
			else if (r.type == "destroy")
			{
				$("#waterAlertsBox").html("");
				$("#waterAlertsBox").html("Rate Master delete successfully");
				$("#waterAlertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				$('#gasRateMasterGrid').data('kendoGrid').dataSource.read();
				$('#gasRateMasterGrid').data('kendoGrid').refresh();
			}
		}
	}
	/************************************* for inner rate slab request *********************************/

	function gasRateSlabRequestEnd(e)
	{
			var rateSlab = $("#gasRateSlabGrid_" + SelectedRowId).data("kendoGrid");
	//		var rateMaster = $('#gasRateMasterGrid').data("kendoGrid");
			
			if (typeof e.response != 'undefined')
			{
				if (e.response.status == "CHILD") 
				{
					$("#waterAlertsBox").html("");
					$("#waterAlertsBox").html(
							"Can't Delete Rate Master, Child Record Found");
					$("#waterAlertsBox").dialog({
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
				else if (e.type == "create") {
					$("#waterAlertsBox").html("");
					$("#waterAlertsBox").html("Rate Slab Created Successfully");
					$("#waterAlertsBox").dialog({
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

				else if (e.type == "update") {
					$("#waterAlertsBox").html("");
					$("#waterAlertsBox").html("Rate Slab updated successfully");
					$("#waterAlertsBox").dialog({
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
					$("#waterAlertsBox").html("");
					$("#waterAlertsBox").html("Rate Slab delete successfully");
					$("#waterAlertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
					rateSlab.dataSource.read();
					rateSlab.refresh();
				}
			}
	}

	function gasRateSlabStatusChange() {
 		var result=securityCheckForActionsForStatus("./Tariff/Gas/RateSlab/activitRateSlab"); 
		if(result=="success"){ 
		var gasrsId = "";
		var gridParameter = $("#gasRateSlabGrid_" + SelectedRowId).data("kendoGrid");
		var selectedAddressItem = gridParameter
				.dataItem(gridParameter.select());
		gasrsId = selectedAddressItem.gasrsId;
		$.ajax({
			type : "POST",
			url : "./tariff/gasRateSlabStatusUpdateFromInnerGrid/" + gasrsId,
			dataType:"text",
			success : function(response) {
				$("#waterAlertsBox").html("");
				$("#waterAlertsBox").html(response);
				$("#waterAlertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				$('#gasRateSlabGrid_' + SelectedRowId).data('kendoGrid').dataSource
						.read();
			}
		});
		}
	}


	function gasTariffMasterEditor(container, options) {
		$(
				'<input name="Tariff Name" id="gasTariffName" data-text-field="gasTariffName" data-value-field="gasTariffId" data-bind="value:' + options.field + '" required="true"/>')
				.appendTo(container).kendoDropDownList({
					autoBind : false,
					placeholder : "Select Tariff Name",
					dataSource : {
						transport : {
							read : "${gasTariffMasterComboBoxUrl}"
						}
					},
					change : function(e) {
						if (this.value() && this.selectedIndex == -1) {
							alert("Tariff Name doesn't exist!");
							$("#gasTariffName").data("kendoComboBox").value("");
						}
						gasTariffId = this.dataItem().gasTariffId;
					}
				});

		$('<span class="k-invalid-msg" data-for="Tariff Name"></span>')
				.appendTo(container);
	}

	function gasRateNameEditor(container, options) {
		var res = (container.selector).split("=");
		var gasRateName = res[1].substring(0, res[1].length - 1);
		$(
				"<select name='Rate Name'id='gasRateName' data-bind='value:" + gasRateName + "'required='true'/>")
				.appendTo(container).kendoDropDownList({
					dataTextField : "text",
					dataValueField : "value",
					placeholder : "Select Rate Name",
					dataSource : {
						transport : {
							read : "${allChecksUrl}/" + gasRateName,
						}
					},
					change : function(e) {
						if (this.value() && this.selectedIndex == -1) {
							alert("Rate Name doesn't exist!");
							$("#gasRateName").data("kendoComboBox").value("");
						}
						wtRateName1 = this.dataItem().text;
					}
				});

		$('<span class="k-invalid-msg" data-for="Rate Name"></span>').appendTo(
				container);
	}

	function gasRateTypeEditor(container, options) {
		var res = (container.selector).split("=");
		var gasRateType = res[1].substring(0, res[1].length - 1);
		$(
				"<select name='Rate Type' id='gasRateType' data-bind='value:" + gasRateType + "'required='true'/>")
				.appendTo(container).kendoDropDownList({
					dataTextField : "text",
					dataValueField : "value",
					placeholder : "Select Rate Type",
					dataSource : {
						transport : {
							read : "${allChecksUrl}/" + gasRateType,
						}
					},
					change : function(e) {
						if (this.value() && this.selectedIndex == -1) {
							alert("Rate Type doesn't exist!");
							$("#gasRateType").data("kendoComboBox").value("");
						}
						wtRateType1 = this.dataItem().text;
					}
				});

		$('<span class="k-invalid-msg" data-for="Rate Type"></span>').appendTo(
				container);
	}
	
	function toRateUOMEditor(container, options) {
		var res = (container.selector).split("=");
		var gasRateUOM = res[1].substring(0, res[1].length - 1);
		$(
				"<select name='Rate UOM' data-bind='value:" + gasRateUOM + "'required='true'/>")
				.appendTo(container).kendoDropDownList({
					dataTextField : "text",
					dataValueField : "value",
					placeholder : "Select Rate UOM",
					dataSource : {
						transport : {
							read : "${allChecksUrl}/" + gasRateUOM,
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
	
	function gasRateMasterParse (response)
	{   
	    $.each(response, function (idx, elem) {
	    	$.each(elem, function (idx1, elem1) 
	    	{
	    		if(idx1 == "gasrsId")
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
	    		
	    		if(idx1 == "gasSlabNo")
		    	{
		    		nextwtSlabNo = elem1;
		    	}
		    	
		    	if(idx1 == "gasSlabFrom")
		    	{
		    		nextslabFrom = elem1;
		    	}
		    	
		    	if(idx1 == "gasSlabTo")
		    	{
		    		nextslabTo = elem1;
		    	}
	        });
            
        });
        return response;
	}

 	 $("#gasRateMasterGrid").on("click", ".k-grid-showAllGasRateMasters", function(e)
 	{
/*   		var result=securityCheckForActionsForStatus("./Tariff/Gas/RateMaster/showAllRateMaster"); 
		if(result=="success"){  */
 		 var showAll = "showAll";
         $.ajax({
        	 url : "./gasRateMaster/read",  
        	 type : 'GET',
             dataType: "json",
             data:{showAll:showAll},
             contentType: "application/json; charset=utf-8",
             success: function (result)
             {
            	  var grid = $("#gasRateMasterGrid").getKendoGrid();
                  var data = new kendo.data.DataSource();
                  grid.dataSource.data(result);
                  grid.refresh();  
              
             },
         });
		/* } */
	});  

 function gasValidFromEditor(container, options)
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
							gasRateName : wtRateName1,
							gasTariffId : gasTariffId,
							gasRateType : wtRateType1,
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
 
 $(document).on('change','input[name="gasMaxSlabToValue"]',
		   function() {
		    var test = $('input[name="gasMaxSlabToValue"]:checked').length ? $('input[name="gasMaxSlabToValue"]:checked').val() : '';

		    if (test == "") 
		    {
		        $('label[for="gasSlabTo"]').parent("div").show();
		        $('div[data-container-for="gasSlabTo"]').show();
		    }
		    if (test == "on")
		    {
		    	$('label[for="gasSlabTo"]').parent("div").hide();
		    	$('div[data-container-for="gasSlabTo"]').hide();
				$('input[name="gasSlabTo"]').prop('required',false);
				$('input[name="gasSlabTo"]').val(999999);
		    }
		   });
		 
			
			var slabTypeToValidate="";
			function gasRateSlabTypeEditor(container, options) {
				var res = (container.selector).split("=");
				var gasSlabType = res[1].substring(0, res[1].length - 1);
				$(
						"<select name='Slab Type' id='gasSlabType' data-bind='value:" + gasSlabType + "'required='true'/>")
						.appendTo(container).kendoDropDownList({
							dataTextField : "text",
							dataValueField : "value",
							placeholder : "Select Slab Type",
							dataSource : {
								transport : {
									read : "${allChecksUrl}/" + gasSlabType,
								}
							},
							change : function(e) 
							{
								if (this.value() && this.selectedIndex == -1) {
									alert("Slab Type doesn't exist!");
									$("#gasSlabType").data("kendoComboBox").value("");
								}
								slabTypeToValidate = this.dataItem().text;
								
								if(slabTypeToValidate == "Not applicable" || slabTypeToValidate=="Per Month")
									{
									$('label[for="gasSlabTo"]').parent("div").hide();
							    	$('div[data-container-for="gasSlabTo"]').hide();
									$('input[name="gasSlabTo"]').prop('required',false);
									$('input[name="gasSlabTo"]').val(null);
									
									$('label[for="gasSlabFrom"]').parent("div").hide();
							    	$('div[data-container-for="gasSlabFrom"]').hide();
									$('input[name="gasSlabFrom"]').prop('required',false);
									
									$('label[for="gasMaxSlabToValue"]').parent("div").hide();
							    	$('div[data-container-for="gasMaxSlabToValue"]').hide();
									$('input[name="gasMaxSlabToValue"]').prop('required',false);
									
									}
								else
									{
									
									$('label[for="gasSlabTo"]').parent("div").show();
							    	$('div[data-container-for="gasSlabTo"]').show();
									
									$('label[for="gasSlabFrom"]').parent("div").show();
							    	$('div[data-container-for="gasSlabFrom"]').show();
									
									$('label[for="gasMaxSlabToValue"]').parent("div").show();
							    	$('div[data-container-for="gasMaxSlabToValue"]').show();
									
									}
							}
						});

				$('<span class="k-invalid-msg" data-for="Slab Type"></span>').appendTo(
						container);
			}
			
			function gasRateMasterEndDateClick()
			{
			 var gasrmid="";
			 var gridParameter = $("#gasRateMasterGrid").data("kendoGrid");
			 var selectedAddressItem = gridParameter.dataItem(gridParameter.select());
			 gasrmid = selectedAddressItem.gasrmid;
			 $.ajax
			 ({   
			  type : "POST",
			  url : "./gasRateMaster/endDate/"+gasrmid,
			  dataType:"text",
			  success : function(response) 
			  {
			   $("#waterAlertsBox").html("");
			   $("#waterAlertsBox").html(response);
			   $("#waterAlertsBox").dialog
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
			   $('#gasRateMasterGrid').data('kendoGrid').dataSource.read();
			  }
			 });
			}
	function gasRateMasterDeleteEvent(){
				securityCheckForActions("./Tariff/Gas/RateMaster/deleteRateMaster");
				var conf = confirm("Are you sure want to delete this Rate Master?");
				    if(!conf){
				    $('#gasRateMasterGrid').data().kendoGrid.dataSource.read();
				    throw new Error('deletion aborted');
				     }
			}
	
	function waterRateSlabDeleteEvent(){
		securityCheckForActions("./Tariff/Gas/RateSlab/deleteRateSlab");
		var conf = confirm("Are you sure want to delete this Rate Slab?");
		    if(!conf){
		    $("#gasRateSlabGrid_" + SelectedRowId).data().kendoGrid.dataSource.read();
		    throw new Error('deletion aborted');
		     }
	}
</script>
