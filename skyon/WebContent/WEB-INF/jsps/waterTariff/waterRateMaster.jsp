<%@include file="/common/taglibs.jsp"%>

<c:url value="/waterRateMaster/read" var="waterRatemasterReadUrl" />
<c:url value="/waterRateMaster/create" var="waterRatemasterCreateUrl" />
<c:url value="/waterRateMaster/update" var="waterRatemasterUpdateUrl" />
<c:url value="/waterRateMaster/destroy" var="waterRatemasterDestroyUrl" />
<c:url value="/common/getAllChecks" var="allChecksUrl" />
<c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />
<c:url value="/person/getAllAttributeValues" var="filterAutoCompleteUrl" />
<c:url value="/waterRateMaster/getToTariffMasterComboBoxUrl" var="waterTariffMasterComboBoxUrl" />
<c:url value="/common/getAllChecks" var="allChecksUrl" />

<!-- for rate slabs -->
<c:url value="/tariff/waterRateslab/read" var="waterRateSlabReadUrl" />
<c:url value="/tariff/waterRateslab/create" var="waterRateSlabCreateUrl" />
<c:url value="/tariff/waterRateslab/update" var="waterRateSlabUpdateUrl" />
<c:url value="/tariff/waterRateslab/destroy" var="waterRateSlabDeleteUrl" />

<c:url value="/waterTariff/getMaxDate" var="getMaxDate"></c:url>

<!-- Filters Data url's -->
<c:url value="/waterTariff/filter" var="commonFilterUrl" />
<c:url value="/wtTariffNameToFilter/filter" var="wtTariffNameToFilter" />


<!-- for merging ids -->

<kendo:grid name="waterRateMasterGrid" remove="waterRateMasterDeleteEvent" resizable="true" pageable="true"  selectable="true" edit="waterRrateMasterEvent" change="onChangeWaterRateMaster" detailTemplate="waterRateMasterSubGrid" sortable="true" scrollable="true"
	groupable="true">
	
	<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" ></kendo:grid-pageable>
	<kendo:grid-filterable extra="false">
		<kendo:grid-filterable-operators>
			<kendo:grid-filterable-operators-string eq="Is equal to" />
		</kendo:grid-filterable-operators>

	</kendo:grid-filterable>
	<kendo:grid-editable mode="popup" />
	<kendo:grid-toolbar>
		<kendo:grid-toolbarItem name="create" text="Add Rate Master" />
		<kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterWaterRateMaster()><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>"/>
		<kendo:grid-toolbarItem name="showAllWaterRateMasters" text="Show All" />
	</kendo:grid-toolbar>
	<kendo:grid-columns>

		<kendo:grid-column title="WT_RM_ID" field="wtrmid" width="110px" hidden="true" />
		
		<kendo:grid-column title="Tariff Master *" field="wtTariffName" width="100px">
		
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
												read : "${wtTariffNameToFilter}/tariffName"
											}
										}
									});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>
		
		<kendo:grid-column title="Tariff Master *" field="wtTariffId" editor="waterTariffMasterEditor" width="100px" hidden="true"> 
		</kendo:grid-column>

		<kendo:grid-column title="Rate&nbsp;Name*" field="wtRateName" editor="toRateNameEditor" width="85px">
			<kendo:grid-column-values value="${waterRateName}" />
		</kendo:grid-column>

		<kendo:grid-column title="Rate&nbsp;Description*"
			field="wtRateDescription" filterable="true" width="120px">
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
												read : "${commonFilterUrl}/wtRateDescription"
											}
										}
									});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>

		<kendo:grid-column title="Rate&nbsp;Type&nbsp;*" field="wtRateType" editor="waterRateTypeEditor" width="90px">
			<kendo:grid-column-values value="${waterRateType}" />
		</kendo:grid-column>

		<kendo:grid-column title="Rate&nbsp;UOM&nbsp;*" field="wtRateUOM"  width="90px">
			<kendo:grid-column-values value="${waterRateUOM}" />
		</kendo:grid-column>

		<kendo:grid-column title="Valid&nbsp;From&nbsp;*" field="wtValidFrom" editor="waterValidFromEditor" format="{0:dd/MM/yyyy}" width="90px" >
		
		<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function wtValidFromFilter(element) {
							element.kendoDatePicker({
							        format: "dd/MM/yyyy"
							});
						}
					</script>
				</kendo:grid-column-filterable-ui>
		</kendo:grid-column-filterable>
			
		</kendo:grid-column>

		<kendo:grid-column title="Valid&nbsp;To&nbsp;*" field="wtValidTo" format="{0:dd/MM/yyyy}" width="80px" >
				<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function wtValidToFilter(element) {
							element.kendoDatePicker({
								 format: "dd/MM/yyyy"
							});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>

		<kendo:grid-column title="Min Rate(In Rs)" field="wtMinRate" width="100px" >
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
		<kendo:grid-column title="Max Rate(In Rs)" field="wtMaxRate" width="100px" >
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

		<kendo:grid-column title="Rate Status *" field="wtRateMasterStatus" width="90px">
			<kendo:grid-column-values value="${wtRateMasterStatus}" />
		</kendo:grid-column>

		<kendo:grid-column title="&nbsp;" width="158px">
			<kendo:grid-column-command>
				<kendo:grid-column-commandItem name="edit"/>
				<kendo:grid-column-commandItem name="destroy" />
			</kendo:grid-column-command>
		</kendo:grid-column>

		<kendo:grid-column title=""
			template="<a href='\\\#' id='waterTemPID' class='k-button k-button-icontext btn-destroy k-grid-Active#=data.wtrmid#'>#= data.wtRateMasterStatus == 'Active' ? 'Inactive' : 'Active' #</a>"
			width="70px" />
	   </kendo:grid-columns>

	<kendo:dataSource pageSize="20" requestEnd="waterRequestEndRateMaster" requestStart="waterRequestStartRateMaster">
	
		<kendo:dataSource-transport>
			<kendo:dataSource-transport-create url="${waterRatemasterCreateUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-read url="${waterRatemasterReadUrl}" dataType="json" type="POST" contentType="application/json" />
			<kendo:dataSource-transport-update url="${waterRatemasterUpdateUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-destroy url="${waterRatemasterDestroyUrl}" dataType="json" type="GET" contentType="application/json" />
		</kendo:dataSource-transport>

		<kendo:dataSource-schema>
			<kendo:dataSource-schema-model id="wtrmid">
				<kendo:dataSource-schema-model-fields>

					<kendo:dataSource-schema-model-field name="wtrmid" type="number"/>

					<kendo:dataSource-schema-model-field name="wtTariffId" type="string" defaultValue="Select">
						<kendo:dataSource-schema-model-field-validation />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="wtRateName"  defaultValue="Water charges">
						<kendo:dataSource-schema-model-field-validation />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="wtRateDescription" type="string">
						<kendo:dataSource-schema-model-field-validation />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="wtRateType"  defaultValue="Single Slab" />

					<kendo:dataSource-schema-model-field name="wtRateUOM"  defaultValue="Liters">
						<kendo:dataSource-schema-model-field-validation required="true" />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="wtValidFrom" type="date">
						<kendo:dataSource-schema-model-field-validation required="true" />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="wtValidTo" type="date">
						<kendo:dataSource-schema-model-field-validation required="true" />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="wtMinRate" type="number">
						<kendo:dataSource-schema-model-field-validation min="0" />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="wtMaxRate" type="number">
						<kendo:dataSource-schema-model-field-validation min="0" />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="wtRateMasterStatus" editable="true" type="string" />
					
					<kendo:dataSource-schema-model-field name="wtTariffName" type="string" />
					
				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>

	</kendo:dataSource>
</kendo:grid>

<kendo:grid-detailTemplate id="waterRateMasterSubGrid">
	<kendo:tabStrip name="waterRateMasterTabStrip_#=wtrmid#" >
		<kendo:tabStrip-items>

			<kendo:tabStrip-item text="Rate Slab" selected="true">
				<kendo:tabStrip-item-content>
					<div class='waterRateSlbDIV'>
						<br />
						<kendo:grid name="waterRateSlabGrid_#=wtrmid#" pageable="true" filterable="true" dataBound="CheckBoxDataBoundWater"
							change="onGridChangeWater" resizable="true" sortable="true"
							reorderable="true" selectable="true" scrollable="true"
							edit="waterRateSlabEditEvent" remove="waterRateSlabDeleteEvent">
							
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

								<kendo:grid-column title="WT_RS_ID" field="wtrsId" hidden="true" width="70px" />
								
								<kendo:grid-column title="Slab Number *" field="wtSlabNo" width="70px" filterable="false"/>
								
								<kendo:grid-column title="Slab Type *" field="wtSlabType" editor="waterRateSlabTypeEditor" width="70px" filterable="false">
									<kendo:grid-column-values value="${waterSlabType}" />
								</kendo:grid-column>

							    <kendo:grid-column title="Slab From*" field="wtDummySlabFrom" width="80px" filterable="false"/> 
								<kendo:grid-column title="Slab From*" field="wtSlabFrom" width="80px" hidden="true" filterable="false"/>
								
								<kendo:grid-column title="Max Value" field="waterMaxSlabToValue" filterable="false" sortable="false" width="0px" hidden="true" />
								<kendo:grid-column title="Slab To*" field="wtDummySlabTo" width="80px" filterable="false" />
								<kendo:grid-column title="Slab To*" field="wtSlabTo" width="70px" hidden="true" filterable="false" />
								
								<kendo:grid-column title="Rate Type *" field="wtSlabRateType" width="70px" filterable="false" >
									<kendo:grid-column-values value="${waterSlabRateType}" />
								</kendo:grid-column>
								
								<kendo:grid-column title="Rate *" field="wtRate" width="70px" filterable="false" />
								
								<kendo:grid-column title="Slab Status *" field="wtRateSlabStatus" width="70px" filterable="false" >
									<kendo:grid-column-values value="${wtRateMasterStatus}" />
								</kendo:grid-column>

								<kendo:grid-column title="&nbsp;" width="250px">
									<kendo:grid-column-command>
									<kendo:grid-column-commandItem name="Split" click="waterSplitFunction" />
										<kendo:grid-column-commandItem name="edit" />
										<kendo:grid-column-commandItem name="destroy" />
										<kendo:grid-column-commandItem name="Change Status" click="waterRateSlabStatusChange" />
									</kendo:grid-column-command>
								</kendo:grid-column>

							</kendo:grid-columns>

							<kendo:dataSource requestEnd="waterRateSlabRequestEnd" requestStart="waterRateSlabRequestStart">
								<kendo:dataSource-transport>
									<kendo:dataSource-transport-read url="${waterRateSlabReadUrl}/#=wtrmid#" dataType="json" type="POST" contentType="application/json" />
									<kendo:dataSource-transport-create url="${waterRateSlabCreateUrl}/#=wtrmid#" dataType="json" type="GET" contentType="application/json" />
									<kendo:dataSource-transport-update url="${waterRateSlabUpdateUrl}" dataType="json" type="GET" contentType="application/json" />
									<kendo:dataSource-transport-destroy url="${waterRateSlabDeleteUrl}" dataType="json" type="GET" contentType="application/json" />
								</kendo:dataSource-transport>

								<kendo:dataSource-schema parse="waterRateMasterParse">
									<kendo:dataSource-schema-model id="wtrsId">
										<kendo:dataSource-schema-model-fields>

											<kendo:dataSource-schema-model-field name="wtrsId"/>

											<kendo:dataSource-schema-model-field name="wtSlabNo" type="number">
												<kendo:dataSource-schema-model-field-validation min="1" />
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="wtSlabType" type="String" defaultValue="Numeric"/>

											<kendo:dataSource-schema-model-field name="wtSlabRateType" type="String" defaultValue="Paise"/>

											<kendo:dataSource-schema-model-field name="wtRate" type="number">
												<kendo:dataSource-schema-model-field-validation min="0" />
											</kendo:dataSource-schema-model-field>

											<kendo:dataSource-schema-model-field name="wtSlabFrom" type="number">
											<kendo:dataSource-schema-model-field-validation min="0" max="999999"/>
											</kendo:dataSource-schema-model-field>
											
										   <kendo:dataSource-schema-model-field name="wtDummySlabFrom" type="String"/>
											
											<kendo:dataSource-schema-model-field name="waterMaxSlabToValue" type="boolean"/>
											
											<kendo:dataSource-schema-model-field name="wtSlabTo" type="number">
											<kendo:dataSource-schema-model-field-validation min="0" max="999999"/>
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="wtDummySlabTo" type="String"/>
											
											<kendo:dataSource-schema-model-field name="wtRateSlabStatus" type="string" editable="true" />

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
					<input type="number" name="wtSlabNo" id="wtSlabNo" class="text ui-widget-content ui-corner-all" readonly="readonly">
				</td>
			</tr>

			<tr>
				<td style="height: 10px; width: 100px; text-align: center; vertical-align: middle;">Rate</td>
				<td style="height: 40px; width: 100px; text-align: center; vertical-align: middle;">
					<input type="number" name="wtRate" id="wtRate" class="text ui-widget-content ui-corner-all">
				</td>
			</tr>

			<tr>
				<td style="height: 10px; width: 100px; text-align: center; vertical-align: middle;">Slab From</td>
				<td style="height: 40px; width: 100px; text-align: center; vertical-align: middle;">
					<input type="number" name="wtSlabFrom" id="wtSlabFrom" class="text ui-widget-content ui-corner-all" readonly="readonly">
				</td>
			</tr>

			<tr>
				<td style="height: 10px; width: 100px; text-align: center; vertical-align: middle;">Slab To</td>
				<td style="height: 40px; width: 100px; text-align: center; vertical-align: middle;">
					<input type="number" name="wtSlabTo" id="wtSlabTo" class="text ui-widget-content ui-corner-all">
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

var wtTariffId;
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
	 						    var wtSlabNo = $("#wtSlabNo").val();
								var wtRate = $("#wtRate").val();
								var wtSlabFrom = $("#wtSlabFrom").val();
								var wtSlabTo = $("#wtSlabTo").val();
								var hiddenwtrsId = $("#hiddenwtrsId").val();
								var wtSlabNo1 = $("#wtSlabNo1").val();
								var rate1 = $("#rate1").val();
								var slabFrom1 = $("#slabFrom1").val();
								var slabTo1 = $("#slabTo1").val();
								
								  if (hiddenwtrsId == "") {
									alert("form1 Slab number should not be empty.");
									return false;
								}

								if (wtSlabNo == "") {
									alert("form1 Slab number should not be empty.");
									return false;
								}
								
								if (wtRate == "") {
									alert("form1 Rate should not be empty.");
									return false;
								}
								else
								{
									if (parseInt(wtRate) < 0) 
									{
										alert("form1 Rate should greater than 1.");
										return false;
									}
									
								}
								
								if (wtSlabFrom == "") {
									alert("form1 Slab from should not be empty.");
									return false;
								}

								if (wtSlabTo == "") {
									alert("form1 Slab To should not be empty.");
									return false;
								} else {
									if (parseInt(wtSlabTo) <= parseInt(wtSlabFrom)) {
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
									var form1SlabTo = wtSlabTo;
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
											url : "./tariff/waterRateslab/rateSlabSplit",
											data : {
												hiddenwtrsId : hiddenwtrsId,
												wtSlabNo : wtSlabNo,
												wtRate : wtRate,
												wtSlabFrom : wtSlabFrom,
												wtSlabTo : wtSlabTo,
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
															'#waterRateSlabGrid_'
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

	function CheckBoxDataBoundWater(e)
	{
		var grid = $("#waterRateSlabGrid_" + SelectedRowId).data("kendoGrid");
		var gridData = grid._data;
		var i = 0;
		this.tbody
				.find("tr td:first-child")
				.each(
						function(e) {
							$(
									'<input type="checkbox" name="chkbox" id="singleSelectChkBx_'
											+ gridData[i].wtrsId
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
										alert("New wtRate should be greater than 1.");
										return false;
									}
									alert("New wtRate should not be empty.");
									return false;
								}

								$
										.ajax({
											type : "POST",
											data : {
												newrate : newrate,
											},
											url : "./tariff/waterRateslab/getNewRate",
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
															'#waterRateSlabGrid_'
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

	$("#waterRateMasterGrid")
			.on(
					"click",
					".k-grid-Merge",
					function() {
						var result=securityCheckForActionsForStatus("./Tariff/Water/RateSlab/mergeRateSlab"); 
						if(result=="success"){ 
						
						if((rateMasterSlabType === 'Single Slab') && (parseInt(nextwtSlabNo) === 1))
						{
						 var grid = $("#waterRateSlabGrid_" + SelectedRowId).data("kendoGrid");
						 grid.cancelRow();
					     alert("Only one wtRate slab allowed for single slab wtRate master.");
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
									url : "./tariff/waterRateslab/merge",
									data : {
										"wtrsIds" : globalElrsId.toString()
									},
									success : function(response) {
										if (response.status == "INPUT") {
											elRateSlabMergeDialog
													.dialog("open");
										}

										if (response.status == "SINGLESLAB") {
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
													'#waterRateSlabGrid_'
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
													'#waterRateSlabGrid_'
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
	function onChangeWaterRateMaster(arg) {
		var gview = $("#waterRateMasterGrid").data("kendoGrid");
		var selectedItem = gview.dataItem(gview.select());
		SelectedRowId = selectedItem.wtrmid;
		rateMasterSlabType = selectedItem.wtRateType;
		this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));  	 
	     $(".k-master-row.k-state-selected").show();
	}


	var wtrsId = "";
	var wtSlabNo = "";
	var wtRate = "";
	var wtSlabFrom = "";
	var wtSlabTo = "";
	function waterSplitFunction() 
	{
		var result=securityCheckForActionsForStatus("./Tariff/Water/RateSlab/splitRateSlab"); 
		if(result=="success"){ 
		if((rateMasterSlabType === 'Single Slab') && (parseInt(nextwtSlabNo) === 1))
		{
		 var grid = $("#waterRateSlabGrid_" + SelectedRowId).data("kendoGrid");
		 grid.cancelRow();
	     alert("Only one wtRate slab allowed for single slab wtRate master.");
	     return false;
		}
		
		elRateSlabSplitDialog.dialog("open");
		$("#hiddenwtrsId").val(wtrsId);
		$("#wtSlabNo").val(wtSlabNo);
		var wtSlabNo1 = wtSlabNo + 1;
		$("#wtSlabNo1").val(wtSlabNo1);
		$("#wtRate").val(wtRate);
		$("#wtSlabFrom").val(wtSlabFrom);
		$("#slabTo1").val(wtSlabTo);
		}
	}

	function onGridChangeWater() {
		var elRateSlabGrid = $("#waterRateSlabGrid_" + SelectedRowId)
				.data("kendoGrid");
		var selectedRowItem = elRateSlabGrid.dataItem(elRateSlabGrid.select());
		wtrsId = selectedRowItem.wtrsId;
		wtSlabNo = selectedRowItem.wtSlabNo;
		wtRate = selectedRowItem.wtRate;
		wtSlabFrom = selectedRowItem.wtSlabFrom;
		wtSlabTo = selectedRowItem.wtSlabTo;
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

	$("#waterRateMasterGrid").on("click", "#waterTemPID", function(e) {
		var button = $(this),
		enable = button.text() == "Active";
		var widget = $("#waterRateMasterGrid").data("kendoGrid");
		var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
		
 		var result=securityCheckForActionsForStatus("./Tariff/Water/RateMaster/activitRateMaster"); 
		if(result=="success"){ 

		if (enable)
		{
			$.ajax({
				type : "POST",
				url : "./waterTariff/tariffStatus/" + dataItem.wtrmid + "/activate",
				dataType : "text",
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
					$('#waterRateMasterGrid').data('kendoGrid').dataSource.read();
				}
			});
		} else {
			$.ajax({
				type : "POST",
				url : "./waterTariff/tariffStatus/" + dataItem.wtrmid + "/deactivate",
				dataType : "text",
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
					$('#waterRateMasterGrid').data('kendoGrid').dataSource.read();
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
	function waterRrateMasterEvent(e)
	{

		/***************************  to remove the id from pop up  **********************/
		$('div[data-container-for="wtrmid"]').remove();
		$('label[for="wtrmid"]').closest('.k-edit-label').remove();

		$('div[data-container-for="wtTariffName"]').remove();
		$('label[for="wtTariffName"]').closest('.k-edit-label').remove();
		
		$(".k-edit-field").each(function() {
			$(this).find("#waterTemPID").parent().remove();
		});

		$('label[for="wtRateMasterStatus"]').parent().hide();
		$('div[data-container-for="wtRateMasterStatus"]').hide();

		/************************* Button Alerts *************************/
	 	if (e.model.isNew()) {
			
	 		securityCheckForActions("./Tariff/Water/RateMaster/createRateMaster");
	 		
			$(".k-window-title").text("Add New Rate Master");
			$(".k-grid-update").text("Save");
			
			wtTariffId =  e.model.wtTariffId;
			wtRateName1 = e.model.wtRateName;
			wtRateType1 = e.model.wtRateType;
			selectedDate  =  e.model.wtValidFrom;
			selectedDateFrom = e.model.wtValidFrom;
		} 
	 	else
		{
	 		securityCheckForActions("./Tariff/Water/RateMaster/updateRateMaster");
	 		
	 		rateMasterEditFloag = 1;
			$.ajax({
				type : "GET",
				url : '${getMaxDate}',
				dataType : "JSON",
				data : {
					wtRateName : e.model.wtRateName,
					wtTariffId : e.model.wtTariffId,
					wtRateType : e.model.wtRateType,
				},
				success : function(response)
				{
					nextFromDate = response;
				}
			});
		    selectedDateFrom = e.model.wtValidFrom;
			$(".k-window-title").text("Edit Rate Master Details");
		} 
		
		
		e.container.find(".k-grid-cancel").bind("click", function () {
 	    	var grid = $("#waterRateMasterGrid").data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
	    }); 
		
	}

	/********************** to hide the child table id ***************************/
	function waterRateSlabEditEvent(e) {

		$('div[data-container-for="wtrsId"]').remove();
		$('label[for="wtrsId"]').closest('.k-edit-label').remove();

		$('div[data-container-for="wtRateSlabStatus"]').remove();
		$('label[for="wtRateSlabStatus"]').closest('.k-edit-label').remove();
		
		$('div[data-container-for="wtDummySlabTo"]').remove();
		$('label[for="wtDummySlabTo"]').closest('.k-edit-label').remove();
		
		$('div[data-container-for="wtDummySlabFrom"]').remove();
		$('label[for="wtDummySlabFrom"]').closest('.k-edit-label').remove();

		if (e.model.isNew()) 
		{
			securityCheckForActions("./Tariff/Water/RateSlab/createRateSlab");
			if((rateMasterSlabType === 'Single Slab') && (parseInt(nextwtSlabNo) === 1))
					{
					 var grid = $("#waterRateSlabGrid_" + SelectedRowId).data("kendoGrid");
					 grid.cancelRow();
				     alert("Only one wtRate slab allowed for single slab wtRate master.");
				     return false;
					}
			
			if(parseInt(nextslabTo) === 999999)
				{
				 var grid = $("#waterRateSlabGrid_" + SelectedRowId).data("kendoGrid");
				 grid.cancelRow();
			     alert("You can't the add the new record,Slab to Max is the last record.");
			     return false;
				}
			
			$(".k-window-title").text("Add New Rate Slab");
			$(".k-grid-update").text("Save");
		} else 
		{
			securityCheckForActions("./Tariff/Water/RateSlab/updateRateSlab");
			
			editFloag = 1;
			if(e.model.wtSlabType == "Not applicable")
			{
			$('label[for="wtSlabTo"]').hide();
	    	$('div[data-container-for="wtSlabTo"]').hide();
			$('input[name="wtSlabTo"]').prop('required',false);
			$('input[name="wtSlabTo"]').val(null);
			
			$('label[for="wtSlabFrom"]').hide();
	    	$('div[data-container-for="wtSlabFrom"]').hide();
			$('input[name="wtSlabFrom"]').prop('required',false);
			
			$('label[for="waterMaxSlabToValue"]').hide();
	    	$('div[data-container-for="waterMaxSlabToValue"]').hide();
			$('input[name="waterMaxSlabToValue"]').prop('required',false);
			
			}
		else
			{
			
			$('label[for="wtSlabTo"]').show();
	    	$('div[data-container-for="wtSlabTo"]').show();
			
			$('label[for="wtSlabFrom"]').show();
	    	$('div[data-container-for="wtSlabFrom"]').show();
			
			$('label[for="waterMaxSlabToValue"]').show();
	    	$('div[data-container-for="waterMaxSlabToValue"]').show();
			
			}
			    if (e.model.wtSlabTo == parseInt(999999))
			    {
			    	$('input[name="waterMaxSlabToValue"]').prop('checked', true);
			    	$('label[for="wtSlabTo"]').hide();
			    	$('div[data-container-for="wtSlabTo"]').hide();
					$('input[name="wtSlabTo"]').prop('required',false);
					$('input[name="wtSlabTo"]').val(999999);
			    }
			
			$(".k-window-title").text("Edit Rate Slab Details");
		}
		
		
		e.container.find(".k-grid-cancel").bind("click", function () {
 	    	var grid =$("#waterRateSlabGrid_" + SelectedRowId).data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
	    }); 
	}

	 function clearFilterWaterRateMaster()
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
           	  var grid = $("#waterRateMasterGrid").getKendoGrid();
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

									if (input.attr("name") == "wtRateName") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},
								descriptionValidation : function(input, params) {

									if (input.attr("name") == "wtRateDescription") {
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

								wtRateType : function(input, params) {
									if (input.attr("name") == "wtRateType") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},

								wtRateUOM : function(input, params) {
									if (input.attr("name") == "wtRateUOM") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},

/* 								addressSeasonTo2 : function(input, params)
								{
									if (input.filter("[name = 'wtValidTo']").length && input.val() != "")
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
								}, */
								
								wtRateUOMValidation : function(input, params) {
									if (input.filter("[name='wtRateUOM']").length
											&& input.val() != "") {
										return /^[a-zA-Z0-9 ]{1,45}$/
												.test(input.val());
									}
									return true;
								},

								/************** for Inner Grid Validation ************/
								slabNoValidator : function(input, params) {
									if (input.attr("name") == "wtSlabNo") 
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
										if ((input.attr("name") == "wtSlabNo") && (parseInt(input.val()) !=  parseInt((nextwtSlabNo+1))))
										{
												 return false;
										 }
								   }
								
								  	return true;
								}, 
								rateValidator : function(input, params)
								{
									if  ((input.attr("name") == "wtRate") && (input.val() == 0)) {
										return false;
									}
									return true;
								}, 
								slabToValidator : function(input, params)
								{
									if  ((input.attr("name") == "wtSlabTo") && (input.val() == 0))
									{
										return false;
									}
									return true;
								},
							
								slabToGreaterThanSlabFrom : function(input, params)
								{
									if (input.attr("name") == "wtSlabTo") 
									{
										slabToToCompare = input.val();
									}
									if (input.attr("name") == "wtSlabFrom")
									{
										slabFromToCompare = input.val();
									}
									if (input.attr("name") == "wtSlabTo") 
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
								
									if ((input.attr("name") == "wtSlabFrom") && (parseInt(input.val()) < 0)) 
									{
										return false;
									}
									return true;
							},
								slabToValidator : function(input, params)
								{
									if  ((input.attr("name") == "wtSlabTo") && (parseInt(input.val()) <= 0))
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
								wtRateType : "Rate Type should not be empty",
								wtRateUOM : "UOM should not be empty",
								validatinForNumbers : "Only numbers are allowed,follwed by two decimal points",
								addressSeasonFrom : "From Date must be selected in the future",
								addressSeasonTo1 : "Select From date first before selecting To date and change To date accordingly",
								wtRateUOMValidation : "Rate UOM can contain alphabets and spaces but cannot allow numbers and other special characters and maximum 45 characters are allowed",
								/* addressSeasonTo2 : "To date should be after From date",
 */								slabNoValidator : "Slab Number Cannot Be Empty",
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

	function waterRequestStartRateMaster(e){
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();
	}
	function waterRateSlabRequestStart(e){
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();
	}
	function waterRequestEndRateMaster(r) {

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
				$('#waterRateMasterGrid').data('kendoGrid').dataSource.read();
				$('#waterRateMasterGrid').data('kendoGrid').refresh();
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
				$('#waterRateMasterGrid').data('kendoGrid').dataSource.read();
				$('#waterRateMasterGrid').data('kendoGrid').refresh();
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
				$('#waterRateMasterGrid').data('kendoGrid').dataSource.read();
				$('#waterRateMasterGrid').data('kendoGrid').refresh();
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
				$('#waterRateMasterGrid').data('kendoGrid').dataSource.read();
				$('#waterRateMasterGrid').data('kendoGrid').refresh();
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
				$('#waterRateMasterGrid').data('kendoGrid').dataSource.read();
				$('#waterRateMasterGrid').data('kendoGrid').refresh();
			}
		}
	}
	/************************************* for inner rate slab request *********************************/

	function waterRateSlabRequestEnd(e)
	{
			var rateSlab = $("#waterRateSlabGrid_" + SelectedRowId).data("kendoGrid");
	//		var rateMaster = $('#waterRateMasterGrid').data("kendoGrid");
			
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

	function waterRateSlabStatusChange() {
 		var result=securityCheckForActionsForStatus("./Tariff/Water/RateSlab/activitRateSlab"); 
		if(result=="success"){ 
		var wtrsId = "";
		var gridParameter = $("#waterRateSlabGrid_" + SelectedRowId).data("kendoGrid");
		var selectedAddressItem = gridParameter
				.dataItem(gridParameter.select());
		wtrsId = selectedAddressItem.wtrsId;
		$.ajax({
			type : "POST",
			url : "./tariff/waterRateSlabStatusUpdateFromInnerGrid/" + wtrsId,
			dataType : "text",
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
				$('#waterRateSlabGrid_' + SelectedRowId).data('kendoGrid').dataSource
						.read();
			}
		});
	 }
	}


	function waterTariffMasterEditor(container, options) {
		$(
				'<input name="Tariff Name" id="wtTariffName" data-text-field="wtTariffName" data-value-field="wtTariffId" data-bind="value:' + options.field + '" required="true"/>')
				.appendTo(container).kendoDropDownList({
					autoBind : false,
					placeholder : "Select Tariff Name",
					dataSource : {
						transport : {
							read : "${waterTariffMasterComboBoxUrl}"
						}
					},
					change : function(e) {
						if (this.value() && this.selectedIndex == -1) {
							alert("Tariff Name doesn't exist!");
							$("#wtTariffName").data("kendoComboBox").value("");
						}
						wtTariffId = this.dataItem().wtTariffId;
					}
				});

		$('<span class="k-invalid-msg" data-for="Tariff Name"></span>')
				.appendTo(container);
	}

	function toRateNameEditor(container, options) {
		var res = (container.selector).split("=");
		var wtRateName = res[1].substring(0, res[1].length - 1);
		$(
				"<select name='Rate Name'id='wtRateName' data-bind='value:" + wtRateName + "'required='true'/>")
				.appendTo(container).kendoDropDownList({
					dataTextField : "text",
					dataValueField : "value",
					placeholder : "Select Rate Name",
					dataSource : {
						transport : {
							read : "${allChecksUrl}/" + wtRateName,
						}
					},
					change : function(e) {
						if (this.value() && this.selectedIndex == -1) {
							alert("Rate Name doesn't exist!");
							$("#wtRateName").data("kendoComboBox").value("");
						}
						wtRateName1 = this.dataItem().text;
					}
				});

		$('<span class="k-invalid-msg" data-for="Rate Name"></span>').appendTo(
				container);
	}

	function waterRateTypeEditor(container, options) {
		var res = (container.selector).split("=");
		var wtRateType = res[1].substring(0, res[1].length - 1);
		$(
				"<select name='Rate Type' id='wtRateType' data-bind='value:" + wtRateType + "'required='true'/>")
				.appendTo(container).kendoDropDownList({
					dataTextField : "text",
					dataValueField : "value",
					placeholder : "Select Rate Type",
					dataSource : {
						transport : {
							read : "${allChecksUrl}/" + wtRateType,
						}
					},
					change : function(e) {
						if (this.value() && this.selectedIndex == -1) {
							alert("Rate Type doesn't exist!");
							$("#wtRateType").data("kendoComboBox").value("");
						}
						wtRateType1 = this.dataItem().text;
					}
				});

		$('<span class="k-invalid-msg" data-for="Rate Type"></span>').appendTo(
				container);
	}
	
	function toRateUOMEditor(container, options) {
		var res = (container.selector).split("=");
		var wtRateUOM = res[1].substring(0, res[1].length - 1);
		$(
				"<select name='Rate UOM' data-bind='value:" + wtRateUOM + "'required='true'/>")
				.appendTo(container).kendoDropDownList({
					dataTextField : "text",
					dataValueField : "value",
					placeholder : "Select Rate UOM",
					dataSource : {
						transport : {
							read : "${allChecksUrl}/" + wtRateUOM,
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
	
	function waterRateMasterParse (response)
	{   
	    $.each(response, function (idx, elem) {
	    	$.each(elem, function (idx1, elem1) 
	    	{
	    		if(idx1 == "wtrsId")
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
	    		
	    		if(idx1 == "wtSlabNo")
		    	{
		    		nextwtSlabNo = elem1;
		    	}
		    	
		    	if(idx1 == "wtSlabFrom")
		    	{
		    		nextslabFrom = elem1;
		    	}
		    	
		    	if(idx1 == "wtSlabTo")
		    	{
		    		nextslabTo = elem1;
		    	}
	        });
            
        });
        return response;
	}

 	 $("#waterRateMasterGrid").on("click", ".k-grid-showAllWaterRateMasters", function(e)
 	{
/*  		var result=securityCheckForActionsForStatus("./Tariff/Water/RateMaster/showAllRateMaster"); 
		if(result=="success"){  */
	 		 var showAll = "showAll";
	         $.ajax({
	        	 url : "./waterRateMaster/read",  
	        	 type : 'GET',
	             dataType: "json",
	             data:{showAll:showAll},
	             contentType: "application/json; charset=utf-8",
	             success: function (result)
	             {
	            	  var grid = $("#waterRateMasterGrid").getKendoGrid();
	                  var data = new kendo.data.DataSource();
	                  grid.dataSource.data(result);
	                  grid.refresh();  
	              
	             },
	         });
		/* } */
	});  

 function waterValidFromEditor(container, options)
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
							wtRateName : wtRateName1,
							wtTariffId : wtTariffId,
							wtRateType : wtRateType1,
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
 
 $(document).on('change','input[name="waterMaxSlabToValue"]',
		   function() {
		    var test = $('input[name="waterMaxSlabToValue"]:checked').length ? $('input[name="waterMaxSlabToValue"]:checked').val() : '';

		    if (test == "") 
		    {
		        $('label[for="wtSlabTo"]').parent("div").show();
		        $('div[data-container-for="wtSlabTo"]').show();
		    }
		    if (test == "on")
		    {
		    	$('label[for="wtSlabTo"]').parent("div").hide();
		    	$('div[data-container-for="wtSlabTo"]').hide();
				$('input[name="wtSlabTo"]').prop('required',false);
				$('input[name="wtSlabTo"]').val(999999);
		    }
		   });
		 
			
			var slabTypeToValidate="";
			function waterRateSlabTypeEditor(container, options) {
				var res = (container.selector).split("=");
				var wtSlabType = res[1].substring(0, res[1].length - 1);
				$(
						"<select name='Slab Type' id='wtSlabType' data-bind='value:" + wtSlabType + "'required='true'/>")
						.appendTo(container).kendoDropDownList({
							dataTextField : "text",
							dataValueField : "value",
							placeholder : "Select Slab Type",
							dataSource : {
								transport : {
									read : "${allChecksUrl}/" + wtSlabType,
								}
							},
							change : function(e) 
							{
								if (this.value() && this.selectedIndex == -1) {
									alert("Slab Type doesn't exist!");
									$("#wtSlabType").data("kendoComboBox").value("");
								}
								slabTypeToValidate = this.dataItem().text;
								
								if(slabTypeToValidate == "Not applicable" || slabTypeToValidate=="Per Month")
									{
									$('label[for="wtSlabTo"]').parent("div").hide();
							    	$('div[data-container-for="wtSlabTo"]').hide();
									$('input[name="wtSlabTo"]').prop('required',false);
									$('input[name="wtSlabTo"]').val(null);
									
									$('label[for="wtSlabFrom"]').parent("div").hide();
							    	$('div[data-container-for="wtSlabFrom"]').hide();
									$('input[name="wtSlabFrom"]').prop('required',false);
									
									$('label[for="waterMaxSlabToValue"]').parent("div").hide();
							    	$('div[data-container-for="waterMaxSlabToValue"]').hide();
									$('input[name="waterMaxSlabToValue"]').prop('required',false);
									
									}
								else
									{
									
									$('label[for="wtSlabTo"]').parent("div").show();
							    	$('div[data-container-for="wtSlabTo"]').show();
									
									$('label[for="wtSlabFrom"]').parent("div").show();
							    	$('div[data-container-for="wtSlabFrom"]').show();
									
									$('label[for="waterMaxSlabToValue"]').parent("div").show();
							    	$('div[data-container-for="waterMaxSlabToValue"]').show();
									
									}
							}
						});

				$('<span class="k-invalid-msg" data-for="Slab Type"></span>').appendTo(
						container);
			}

function waterRateMasterDeleteEvent(){
					securityCheckForActions("./Tariff/Water/RateMaster/deleteRateMaster");
					var conf = confirm("Are you sure want to delete this Rate Master?");
					    if(!conf){
					    $('#waterRateMasterGrid').data().kendoGrid.dataSource.read();
					    throw new Error('deletion aborted');
					     }
				}
function waterRateSlabDeleteEvent(){
	securityCheckForActions(".Tariff/Water/RateSlab/deleteRateSlab");
	var conf = confirm("Are you sure want to delete this Rate Slab?");
	    if(!conf){
	    $("#waterRateSlabGrid_" + SelectedRowId).data().kendoGrid.dataSource.read();
	    throw new Error('deletion aborted');
	     }
}

</script>
