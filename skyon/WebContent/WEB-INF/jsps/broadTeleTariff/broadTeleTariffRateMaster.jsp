<%@include file="/common/taglibs.jsp"%>

<c:url value="/broadTeleRateMaster/read" var="broadTeleRatemasterReadUrl" />
<c:url value="/broadTeleRateMaster/create" var="broadTeleRatemasterCreateUrl" />
<c:url value="/broadTeleRateMaster/update" var="broadTeleRatemasterUpdateUrl" />
<c:url value="/broadTeleRateMaster/destroy" var="broadTeleRatemasterDestroyUrl" />
<c:url value="/common/getAllChecks" var="allChecksUrl" />
<c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />
<c:url value="/broadTeleRateMaster/getToTariffMasterComboBoxUrl" var="broadTeleTariffMasterComboBoxUrl" />
<c:url value="/common/getAllChecks" var="allChecksUrl" />

<!-- for rate slabs -->
<c:url value="/tariff/broadTeleRateslab/read" var="broadTeleRateSlabReadUrl" />
<c:url value="/tariff/broadTeleRateslab/create" var="broadTeleRateSlabCreateUrl" />
<c:url value="/tariff/broadTeleRateslab/update" var="broadTeleRateSlabUpdateUrl" />
<c:url value="/tariff/broadTeleRateslab/destroy" var="broadTeleRateSlabDeleteUrl" />

<c:url value="/broadTeleTariff/getMaxDate" var="getMaxDate"></c:url>

<!-- Filters Data url's -->
<c:url value="/broadTeleTariff/filter" var="commonFilterUrl" />
<c:url value="/broadTeleTariffNameToFilter/filter" var="broadTeleTariffNameToFilter" />


<!-- for merging ids -->

<kendo:grid name="broadTeleRateMasterGrid" resizable="true" pageable="true"  selectable="true" edit="broadTeleRrateMasterEvent" change="onChangeBroadTeleRateMaster" detailTemplate="broadTeleRateMasterSubGrid" sortable="true" scrollable="true" groupable="true">
	
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
		<kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterBroadTeleRateMaster()><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>"/>
		<kendo:grid-toolbarItem name="showAllBroadTeleRateMasters" text="Show All" />
	</kendo:grid-toolbar>
	<kendo:grid-columns>

		<kendo:grid-column title="BROAD_TELE_RM_ID" field="broadTeleRmid" width="110px" hidden="true" />
		
		<kendo:grid-column title="Traiff Master *" field="broadTeleTariffName" width="100px">
		
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
												read : "${broadTeleTariffNameToFilter}/broadTeleTariffName"
											}
										}
									});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>
		
		<kendo:grid-column title="Tariff Master *" field="broadTeleTariffId" editor="broadTeleTariffMasterEditor" width="100px" hidden="true"> 
		</kendo:grid-column>

		<kendo:grid-column title="Rate&nbsp;Name*" field="broadTeleRateName" editor="broadTeleRateNameEditor" width="85px">
			<kendo:grid-column-values value="${broadTeleRateName}" />
		</kendo:grid-column>

		<kendo:grid-column title="Rate&nbsp;Description*"
			field="broadTeleRateDescription" filterable="true" width="120px">
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function broadTeleRateDescriptionFilter(element) {
							element
									.kendoAutoComplete({
										placeholder : "Enter Description",
										dataType : 'JSON',
										dataSource : {
											transport : {
												read : "${commonFilterUrl}/broadTeleRateDescription"
											}
										}
									});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>

		<kendo:grid-column title="Rate&nbsp;Type&nbsp;*" field="broadTeleRateType" editor="broadTeleRateTypeEditor" width="90px">
			<kendo:grid-column-values value="${broadTeleRateType}" />
		</kendo:grid-column>

		<kendo:grid-column title="Rate&nbsp;UOM&nbsp;*" field="broadTeleRateUOM"  width="90px">
			<kendo:grid-column-values value="${broadTeleRateUOM}" />
		</kendo:grid-column>

		<kendo:grid-column title="Valid&nbsp;From&nbsp;*" field="broadTeleValidFrom" editor="broadTeleValidFromEditor" format="{0:dd/MM/yyyy}" width="90px" >
		
		<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function broadTeleValidFromFilter(element) {
							element.kendoDatePicker({
							        format: "dd/MM/yyyy"
							});
						}
					</script>
				</kendo:grid-column-filterable-ui>
		</kendo:grid-column-filterable>
			
		</kendo:grid-column>

		<kendo:grid-column title="Valid&nbsp;To&nbsp;*" field="broadTeleValidTo" format="{0:dd/MM/yyyy}" width="80px">
				<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function broadTeleValidToFilter(element) {
							element.kendoDatePicker({
								 format: "dd/MM/yyyy"
							});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column> 

		<kendo:grid-column title="Min Rate(In Rs)" field="broadTeleMinRate" width="100px" >
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function broadTeleMinRateFilter(element) {
							element.kendoNumericTextBox({});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>
		<kendo:grid-column title="Max Rate(In Rs)" field="broadTeleMaxRate" width="100px" >
		<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function broadTeleMaxRateToFilter(element) {
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
			template="<a href='\\\#' id='broadTeleTemPID' class='k-button k-button-icontext btn-destroy k-grid-Active#=data.broadTeleRmid#'>#= data.status == 'Active' ? 'Inactive' : 'Active' #</a>"
			width="70px" />
	   </kendo:grid-columns>

	<kendo:dataSource pageSize="20" requestEnd="broadTeleRequestEndRateMaster" requestStart="broadTeleRequestStartRateMaster">
	
		<kendo:dataSource-transport>
			<kendo:dataSource-transport-create url="${broadTeleRatemasterCreateUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-read url="${broadTeleRatemasterReadUrl}" dataType="json" type="POST" contentType="application/json" />
			<kendo:dataSource-transport-update url="${broadTeleRatemasterUpdateUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-destroy url="${broadTeleRatemasterDestroyUrl}" dataType="json" type="GET" contentType="application/json" />
		</kendo:dataSource-transport>

		<kendo:dataSource-schema>
			<kendo:dataSource-schema-model id="broadTeleRmid">
				<kendo:dataSource-schema-model-fields>

					<kendo:dataSource-schema-model-field name="broadTeleRmid" type="number"/>

					<kendo:dataSource-schema-model-field name="broadTeleTariffId" type="string" defaultValue="Select">
						<kendo:dataSource-schema-model-field-validation />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="broadTeleRateName"  defaultValue="Anytime">
						<kendo:dataSource-schema-model-field-validation />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="broadTeleRateDescription" type="string">
						<kendo:dataSource-schema-model-field-validation />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="broadTeleRateType"  defaultValue="Single Slab" />

					<kendo:dataSource-schema-model-field name="broadTeleRateUOM"  defaultValue="MB">
						<kendo:dataSource-schema-model-field-validation required="true" />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="broadTeleValidFrom" type="date">
						<kendo:dataSource-schema-model-field-validation required="true" />
					</kendo:dataSource-schema-model-field>

				 	<kendo:dataSource-schema-model-field name="broadTeleValidTo" type="date">
					</kendo:dataSource-schema-model-field> 

					<kendo:dataSource-schema-model-field name="broadTeleMinRate" type="number">
						<kendo:dataSource-schema-model-field-validation min="0" />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="broadTeleMaxRate" type="number">
						<kendo:dataSource-schema-model-field-validation min="0" />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="status" editable="true" type="string" />
					
					<kendo:dataSource-schema-model-field name="broadTeleTariffName" type="string" />
					
				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>

	</kendo:dataSource>
</kendo:grid>

<kendo:grid-detailTemplate id="broadTeleRateMasterSubGrid">
	<kendo:tabStrip name="broadTeleRateMasterTabStrip_#=broadTeleRmid#" >
		<kendo:tabStrip-items>

			<kendo:tabStrip-item text="Rate Slab" selected="true">
				<kendo:tabStrip-item-content>
					<div class='broadTeleRateSlbDIV'>
						<br />
						<kendo:grid name="broadTeleRateSlabGrid_#=broadTeleRmid#" pageable="true" filterable="true" dataBound="CheckBoxDataBoundBroadTele"
							change="onGridChangeBroadTele" resizable="true" sortable="true"
							reorderable="true" selectable="true" scrollable="true"
							edit="broadTeleRateSlabEditEvent">
							
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
								<kendo:grid-toolbarItem text="Merge" />
							</kendo:grid-toolbar>

							<kendo:grid-columns>
								<kendo:grid-column title="&nbsp;" width="30px">
									<!-- File Upload Button Purpose -->
								</kendo:grid-column>

								<kendo:grid-column title="WT_RS_ID" field="broadTelersId" hidden="true" width="70px" />
								
								<kendo:grid-column title="Slab Number *" field="broadTelSlabNo" width="70px" />
								
								<kendo:grid-column title="Slab Type *" field="broadTelSlabType" editor="broadTeleRateSlabTypeEditor" width="70px">
									<kendo:grid-column-values value="${broadTelSlabType}" />
								</kendo:grid-column>

							    <kendo:grid-column title="Slab From *" field="broadTelDummySlabFrom" width="80px"/> 
								<kendo:grid-column title="Slab From *" field="broadTelSlabFrom" width="80px" hidden="true"/>
								
								<kendo:grid-column title="Max Value" field="broadTeleMaxSlabToValue" filterable="false" sortable="false" width="0px" hidden="true" />
								<kendo:grid-column title="Slab To *" field="broadTelDummySlabTo" width="80px" />
								<kendo:grid-column title="Slab To *" field="broadTelSlabTo" width="70px" hidden="true"/>
								
								<kendo:grid-column title="Rate Type *" field="broadTelSlabRateType" width="70px">
									<kendo:grid-column-values value="${broadTelSlabRateType}" />
								</kendo:grid-column>
								
								<kendo:grid-column title="Rate *" field="broadTelRate" width="70px" />
								
								<kendo:grid-column title="Slab Status *" field="status" width="70px">
									<kendo:grid-column-values value="${status}" />
								</kendo:grid-column>

								<kendo:grid-column title="&nbsp;" width="250px">
									<kendo:grid-column-command>
									<kendo:grid-column-commandItem name="Split" click="broadTeleSplitFunction" />
										<kendo:grid-column-commandItem name="edit" />
										<kendo:grid-column-commandItem name="destroy" />
										<kendo:grid-column-commandItem name="Change Status" click="broadTeleRateSlabStatusChange" />
									</kendo:grid-column-command>
								</kendo:grid-column>

							</kendo:grid-columns>

							<kendo:dataSource requestEnd="broadTeleRateSlabRequestEnd" requestStart="broadTeleRateSlabRequestStart">
								<kendo:dataSource-transport>
									<kendo:dataSource-transport-read url="${broadTeleRateSlabReadUrl}/#=broadTeleRmid#" dataType="json" type="POST" contentType="application/json" />
									<kendo:dataSource-transport-create url="${broadTeleRateSlabCreateUrl}/#=broadTeleRmid#" dataType="json" type="GET" contentType="application/json" />
									<kendo:dataSource-transport-update url="${broadTeleRateSlabUpdateUrl}" dataType="json" type="GET" contentType="application/json" />
									<kendo:dataSource-transport-destroy url="${broadTeleRateSlabDeleteUrl}" dataType="json" type="GET" contentType="application/json" />
								</kendo:dataSource-transport>

								<kendo:dataSource-schema parse="broadTeleRateMasterParse">
									<kendo:dataSource-schema-model id="broadTelersId">
										<kendo:dataSource-schema-model-fields>

											<kendo:dataSource-schema-model-field name="broadTelersId"/>

											<kendo:dataSource-schema-model-field name="broadTelSlabNo" type="number">
												<kendo:dataSource-schema-model-field-validation min="1" />
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="broadTelSlabType" type="String" defaultValue="Numeric"/>

											<kendo:dataSource-schema-model-field name="broadTelSlabRateType" type="String" defaultValue="Paise"/>

											<kendo:dataSource-schema-model-field name="broadTelRate" type="number">
												<kendo:dataSource-schema-model-field-validation min="0" />
											</kendo:dataSource-schema-model-field>

											<kendo:dataSource-schema-model-field name="broadTelSlabFrom" type="number">
											<kendo:dataSource-schema-model-field-validation min="0" max="999999"/>
											</kendo:dataSource-schema-model-field>
											
										   <kendo:dataSource-schema-model-field name="broadTelDummySlabFrom" type="String"/>
											
											<kendo:dataSource-schema-model-field name="broadTeleMaxSlabToValue" type="boolean"/>
											
											<kendo:dataSource-schema-model-field name="broadTelSlabTo" type="number">
											<kendo:dataSource-schema-model-field-validation min="0" max="999999"/>
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="broadTelDummySlabTo" type="String"/>
											
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
					<input type="number" name="broadTelSlabNo" id="broadTelSlabNo" class="text ui-widget-content ui-corner-all" readonly="readonly">
				</td>
			</tr>

			<tr>
				<td style="height: 10px; width: 100px; text-align: center; vertical-align: middle;">Rate</td>
				<td style="height: 40px; width: 100px; text-align: center; vertical-align: middle;">
					<input type="number" name="broadTelRate" id="broadTelRate" class="text ui-widget-content ui-corner-all">
				</td>
			</tr>

			<tr>
				<td style="height: 10px; width: 100px; text-align: center; vertical-align: middle;">Slab From</td>
				<td style="height: 40px; width: 100px; text-align: center; vertical-align: middle;">
					<input type="number" name="broadTelSlabFrom" id="broadTelSlabFrom" class="text ui-widget-content ui-corner-all" readonly="readonly">
				</td>
			</tr>

			<tr>
				<td style="height: 10px; width: 100px; text-align: center; vertical-align: middle;">Slab To</td>
				<td style="height: 40px; width: 100px; text-align: center; vertical-align: middle;">
					<input type="number" name="broadTelSlabTo" id="broadTelSlabTo" class="text ui-widget-content ui-corner-all">
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

var broadTeleTariffId;
var wtRateName1;
var wtRateType1;
var nextFromDate;
var slabFromToCompare;
var slabToToCompare;
var editFloag = 0;
var selectedDateFrom;

	elRateSlabSplitDialog = $("#waterdialog-formSplit")
			.dialog({
						autoOpen : false,
						height : 400,
						width : 600,
						modal : true,
						buttons : {
							"Send" : function() {
	 						    var broadTelSlabNo = $("#broadTelSlabNo").val();
								var broadTelRate = $("#broadTelRate").val();
								var broadTelSlabFrom = $("#broadTelSlabFrom").val();
								var broadTelSlabTo = $("#broadTelSlabTo").val();
								var hiddenwtrsId = $("#hiddenwtrsId").val();
								var wtSlabNo1 = $("#wtSlabNo1").val();
								var rate1 = $("#rate1").val();
								var slabFrom1 = $("#slabFrom1").val();
								var slabTo1 = $("#slabTo1").val();
								
								  if (hiddenwtrsId == "") {
									alert("form1 Slab number should not be empty.");
									return false;
								}

								if (broadTelSlabNo == "") {
									alert("form1 Slab number should not be empty.");
									return false;
								}
								
								if (broadTelRate == "") {
									alert("form1 Rate should not be empty.");
									return false;
								}
								else
								{
									if (parseInt(broadTelRate) < 0) 
									{
										alert("form1 Rate should greater than 1.");
										return false;
									}
									
								}
								
								if (broadTelSlabFrom == "") {
									alert("form1 Slab from should not be empty.");
									return false;
								}

								if (broadTelSlabTo == "") {
									alert("form1 Slab To should not be empty.");
									return false;
								} else {
									if (parseInt(broadTelSlabTo) <= parseInt(broadTelSlabFrom)) {
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
									var form1SlabTo = broadTelSlabTo;
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
											url : "./tariff/broadTeleRateslab/rateSlabSplit",
											data : {
												hiddenwtrsId : hiddenwtrsId,
												broadTelSlabNo : broadTelSlabNo,
												broadTelRate : broadTelRate,
												broadTelSlabFrom : broadTelSlabFrom,
												broadTelSlabTo : broadTelSlabTo,
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
															'#broadTeleRateSlabGrid_'
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

	function CheckBoxDataBoundBroadTele(e)
	{
		var grid = $("#broadTeleRateSlabGrid_" + SelectedRowId).data("kendoGrid");
		var gridData = grid._data;
		var i = 0;
		this.tbody
				.find("tr td:first-child")
				.each(
						function(e) {
							$(
									'<input type="checkbox" name="chkbox" id="singleSelectChkBx_'
											+ gridData[i].broadTelersId
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
							"Send" : function() {
								var newrate = $("#newrate").val();

								if (newrate == "") {
									if (newrate <= 0) {
										alert("New broadTelRate should be greater than 1.");
										return false;
									}
									alert("New broadTelRate should not be empty.");
									return false;
								}

								$
										.ajax({
											type : "POST",
											data : {
												newrate : newrate,
											},
											url : "./tariff/broadTeleRateslab/getNewRate",
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
															'#broadTeleRateSlabGrid_'
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

	$("#broadTeleRateMasterGrid")
			.on(
					"click",
					".k-grid-Merge",
					function() {
						
						if((rateMasterSlabType === 'Single Slab') && (parseInt(nextwtSlabNo) === 1))
						{
						 var grid = $("#broadTeleRateSlabGrid_" + SelectedRowId).data("kendoGrid");
						 grid.cancelRow();
					     alert("Only one broadTelRate slab allowed for single slab broadTelRate master.");
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
									url : "./tariff/broadTeleRateslab/merge",
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
													'#broadTeleRateSlabGrid_'
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
													'#broadTeleRateSlabGrid_'
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
					});

	/***************************** For split ***************************  */
	var SelectedRowId = "";
	var rateMasterSlabType ="";
	function onChangeBroadTeleRateMaster(arg) {
		var gview = $("#broadTeleRateMasterGrid").data("kendoGrid");
		var selectedItem = gview.dataItem(gview.select());
		SelectedRowId = selectedItem.broadTeleRmid;
		rateMasterSlabType = selectedItem.broadTeleRateType;
		this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));  	 
	     $(".k-master-row.k-state-selected").show();
	}


	var broadTelersId = "";
	var broadTelSlabNo = "";
	var broadTelRate = "";
	var broadTelSlabFrom = "";
	var broadTelSlabTo = "";
	function broadTeleSplitFunction() 
	{
		if((rateMasterSlabType === 'Single Slab') && (parseInt(nextwtSlabNo) === 1))
		{
		 var grid = $("#broadTeleRateSlabGrid_" + SelectedRowId).data("kendoGrid");
		 grid.cancelRow();
	     alert("Only one broadTelRate slab allowed for single slab broadTelRate master.");
	     return false;
		}
		
		elRateSlabSplitDialog.dialog("open");
		$("#hiddenwtrsId").val(broadTelersId);
		$("#broadTelSlabNo").val(broadTelSlabNo);
		var wtSlabNo1 = broadTelSlabNo + 1;
		$("#wtSlabNo1").val(wtSlabNo1);
		$("#broadTelRate").val(broadTelRate);
		$("#broadTelSlabFrom").val(broadTelSlabFrom);
		$("#slabTo1").val(broadTelSlabTo);
	}

	function onGridChangeBroadTele() {
		var elRateSlabGrid = $("#broadTeleRateSlabGrid_" + SelectedRowId)
				.data("kendoGrid");
		var selectedRowItem = elRateSlabGrid.dataItem(elRateSlabGrid.select());
		broadTelersId = selectedRowItem.broadTelersId;
		broadTelSlabNo = selectedRowItem.broadTelSlabNo;
		broadTelRate = selectedRowItem.broadTelRate;
		broadTelSlabFrom = selectedRowItem.broadTelSlabFrom;
		broadTelSlabTo = selectedRowItem.broadTelSlabTo;
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

	$("#broadTeleRateMasterGrid").on("click", "#broadTeleTemPID", function(e) {
		var button = $(this),
		enable = button.text() == "Active";
		var widget = $("#broadTeleRateMasterGrid").data("kendoGrid");
		var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));

		if (enable)
		{
			$.ajax({
				type : "POST",
				url : "./broadTeleTariff/tariffStatus/" + dataItem.broadTeleRmid + "/activate",
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
					$('#broadTeleRateMasterGrid').data('kendoGrid').dataSource.read();
				}
			});
		} else {
			$.ajax({
				type : "POST",
				url : "./broadTeleTariff/tariffStatus/" + dataItem.broadTeleRmid + "/deactivate",
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
					$('#broadTeleRateMasterGrid').data('kendoGrid').dataSource.read();
				}
			});
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
	function broadTeleRrateMasterEvent(e)
	{

		/***************************  to remove the id from pop up  **********************/
		$('div[data-container-for="broadTeleRmid"]').remove();
		$('label[for="broadTeleRmid"]').closest('.k-edit-label').remove();
		
		$('div[data-container-for="broadTeleTariffName"]').remove();
		$('label[for="broadTeleTariffName"]').closest('.k-edit-label').remove();
		
		$(".k-edit-field").each(function() {
			$(this).find("#broadTeleTemPID").parent().remove();
		});

		$('label[for="status"]').parent().hide();
		$('div[data-container-for="status"]').hide();
		
		/************************* Button Alerts *************************/
	 	if (e.model.isNew()) {
			
			$(".k-window-title").text("Add New Rate Master");
			$(".k-grid-update").text("Save");
			
			broadTeleTariffId =  e.model.broadTeleTariffId;
			wtRateName1 = e.model.broadTeleRateName;
			wtRateType1 = e.model.broadTeleRateType;
			selectedDate  =  e.model.broadTeleValidFrom;
			selectedDateFrom = e.model.broadTeleValidFrom;
		} 
	 	else
		{
			$.ajax({
				type : "GET",
				url : '${getMaxDate}',
				dataType : "JSON",
				data : {
					broadTeleRateName : e.model.broadTeleRateName,
					broadTeleTariffId : e.model.broadTeleTariffId,
					broadTeleRateType : e.model.broadTeleRateType,
				},
				success : function(response)
				{
					nextFromDate = response;
				}
			});
		    selectedDateFrom = e.model.broadTeleValidFrom;
			$(".k-window-title").text("Edit Rate Master Details");
		} 
		
	}

	/********************** to hide the child table id ***************************/
	function broadTeleRateSlabEditEvent(e) {

		$('div[data-container-for="broadTelersId"]').remove();
		$('label[for="broadTelersId"]').closest('.k-edit-label').remove();

		$('div[data-container-for="status"]').remove();
		$('label[for="status"]').closest('.k-edit-label').remove();
		
		$('div[data-container-for="broadTelDummySlabTo"]').remove();
		$('label[for="broadTelDummySlabTo"]').closest('.k-edit-label').remove();
		
		$('div[data-container-for="broadTelDummySlabFrom"]').remove();
		$('label[for="broadTelDummySlabFrom"]').closest('.k-edit-label').remove();

		if (e.model.isNew()) 
		{
			if((rateMasterSlabType === 'Single Slab') && (parseInt(nextwtSlabNo) === 1))
					{
					 var grid = $("#broadTeleRateSlabGrid_" + SelectedRowId).data("kendoGrid");
					 grid.cancelRow();
				     alert("Only one broadTelRate slab allowed for single slab broadTelRate master.");
				     return false;
					}
			
			if(parseInt(nextslabTo) === 999999)
				{
				 var grid = $("#broadTeleRateSlabGrid_" + SelectedRowId).data("kendoGrid");
				 grid.cancelRow();
			     alert("You can't the add the new record,Slab to Max is the last record.");
			     return false;
				}
			
			$(".k-window-title").text("Add New Rate Slab");
			$(".k-grid-update").text("Save");
		} else 
		{
			editFloag = 1;
			
			if(e.model.broadTelSlabType == "Not applicable")
			{
			$('label[for="broadTelSlabTo"]').hide();
	    	$('div[data-container-for="broadTelSlabTo"]').hide();
			$('input[name="broadTelSlabTo"]').prop('required',false);
			$('input[name="broadTelSlabTo"]').val(null);
			
			$('label[for="broadTelSlabFrom"]').hide();
	    	$('div[data-container-for="broadTelSlabFrom"]').hide();
			$('input[name="broadTelSlabFrom"]').prop('required',false);
			
			$('label[for="broadTeleMaxSlabToValue"]').hide();
	    	$('div[data-container-for="broadTeleMaxSlabToValue"]').hide();
			$('input[name="broadTeleMaxSlabToValue"]').prop('required',false);
			
			}
		else
			{
			
			$('label[for="broadTelSlabTo"]').show();
	    	$('div[data-container-for="broadTelSlabTo"]').show();
			
			$('label[for="broadTelSlabFrom"]').show();
	    	$('div[data-container-for="broadTelSlabFrom"]').show();
			
			$('label[for="broadTeleMaxSlabToValue"]').show();
	    	$('div[data-container-for="broadTeleMaxSlabToValue"]').show();
			
			}
			    if (e.model.broadTelSlabTo == parseInt(999999))
			    {
			    	$('input[name="broadTeleMaxSlabToValue"]').prop('checked', true);
			    	$('label[for="broadTelSlabTo"]').hide();
			    	$('div[data-container-for="broadTelSlabTo"]').hide();
					$('input[name="broadTelSlabTo"]').prop('required',false);
					$('input[name="broadTelSlabTo"]').val(999999);
			    }
			
			$(".k-window-title").text("Edit Rate Slab Details");
		}
	}

	 function clearFilterBroadTeleRateMaster()
	 {
		 $("form.k-filter-menu button[type='reset']").slice().trigger("click");
		   var elRatemaster = $("#broadTeleRateMasterGrid").data("kendoGrid");
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

									if (input.attr("name") == "broadTeleRateName") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},
								descriptionValidation : function(input, params) {

									if (input.attr("name") == "broadTeleRateDescription") {
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

								broadTeleRateType : function(input, params) {
									if (input.attr("name") == "broadTeleRateType") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},

								broadTeleRateUOM : function(input, params) {
									if (input.attr("name") == "broadTeleRateUOM") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},

 								addressSeasonTo2 : function(input, params)
								{
									if (input.filter("[name = 'broadTeleValidTo']").length && input.val() != "")
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
								
								wtRateUOMValidation : function(input, params) {
									if (input.filter("[name='broadTeleRateUOM']").length
											&& input.val() != "") {
										return /^[a-zA-Z0-9 ]{1,45}$/
												.test(input.val());
									}
									return true;
								},

								/************** for Inner Grid Validation ************/
								slabNoValidator : function(input, params) {
									if (input.attr("name") == "broadTelSlabNo") 
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
										if ((input.attr("name") == "broadTelSlabNo") && (parseInt(input.val()) !=  parseInt((nextwtSlabNo+1))))
										{
												 return false;
										 }
								   }
								
								  	return true;
								}, 
								rateValidator : function(input, params)
								{
									if  ((input.attr("name") == "broadTelRate") && (input.val() == 0)) {
										return false;
									}
									return true;
								}, 
								slabToValidator : function(input, params)
								{
									if  ((input.attr("name") == "broadTelSlabTo") && (input.val() == 0))
									{
										return false;
									}
									return true;
								},
							
								slabToGreaterThanSlabFrom : function(input, params)
								{
									if (input.attr("name") == "broadTelSlabTo") 
									{
										slabToToCompare = input.val();
									}
									if (input.attr("name") == "broadTelSlabFrom")
									{
										slabFromToCompare = input.val();
									}
									if (input.attr("name") == "broadTelSlabTo") 
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
								
									if ((input.attr("name") == "broadTelSlabFrom") && (parseInt(input.val()) < 0)) 
									{
										return false;
									}
									return true;
							},
								slabToValidator : function(input, params)
								{
									if  ((input.attr("name") == "broadTelSlabTo") && (parseInt(input.val()) <= 0))
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
								broadTeleRateType : "Rate Type should not be empty",
								broadTeleRateUOM : "UOM should not be empty",
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

	function broadTeleRequestStartRateMaster(e){
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();	
	}
	function broadTeleRateSlabRequestStart(e){
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();
	}
	function broadTeleRequestEndRateMaster(r) {

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
				$('#broadTeleRateMasterGrid').data('kendoGrid').dataSource.read();
				$('#broadTeleRateMasterGrid').data('kendoGrid').refresh();
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
				$('#broadTeleRateMasterGrid').data('kendoGrid').dataSource.read();
				$('#broadTeleRateMasterGrid').data('kendoGrid').refresh();
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
				$('#broadTeleRateMasterGrid').data('kendoGrid').dataSource.read();
				$('#broadTeleRateMasterGrid').data('kendoGrid').refresh();
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
				$('#broadTeleRateMasterGrid').data('kendoGrid').dataSource.read();
				$('#broadTeleRateMasterGrid').data('kendoGrid').refresh();
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
				$('#broadTeleRateMasterGrid').data('kendoGrid').dataSource.read();
				$('#broadTeleRateMasterGrid').data('kendoGrid').refresh();
			}
		}
	}
	/************************************* for inner rate slab request *********************************/

	function broadTeleRateSlabRequestEnd(e)
	{
			var rateSlab = $("#broadTeleRateSlabGrid_" + SelectedRowId).data("kendoGrid");
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

	function broadTeleRateSlabStatusChange() {
		var broadTelersId = "";
		var gridParameter = $("#broadTeleRateSlabGrid_" + SelectedRowId).data("kendoGrid");
		var selectedAddressItem = gridParameter
				.dataItem(gridParameter.select());
		broadTelersId = selectedAddressItem.broadTelersId;
		$.ajax({
			type : "POST",
			url : "./tariff/broadTeleRateSlabStatusUpdateFromInnerGrid/" + broadTelersId,
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
				$('#broadTeleRateSlabGrid_' + SelectedRowId).data('kendoGrid').dataSource
						.read();
			}
		});
	}


	function broadTeleTariffMasterEditor(container, options) {
		$(
				'<input name="Tariff Name" id="broadTeleTariffName" data-text-field="broadTeleTariffName" data-value-field="broadTeleTariffId" data-bind="value:' + options.field + '" required="true"/>')
				.appendTo(container).kendoDropDownList({
					autoBind : false,
					placeholder : "Select Tariff Name",
					dataSource : {
						transport : {
							read : "${broadTeleTariffMasterComboBoxUrl}"
						}
					},
					change : function(e) {
						if (this.value() && this.selectedIndex == -1) {
							alert("Tariff Name doesn't exist!");
							$("#broadTeleTariffName").data("kendoComboBox").value("");
						}
						broadTeleTariffId = this.dataItem().broadTeleTariffId;
					}
				});

		$('<span class="k-invalid-msg" data-for="Tariff Name"></span>')
				.appendTo(container);
	}

	function broadTeleRateNameEditor(container, options) {
		var res = (container.selector).split("=");
		var broadTeleRateName = res[1].substring(0, res[1].length - 1);
		$(
				"<select name='Rate Name'id='broadTeleRateName' data-bind='value:" + broadTeleRateName + "'required='true'/>")
				.appendTo(container).kendoDropDownList({
					dataTextField : "text",
					dataValueField : "value",
					placeholder : "Select Rate Name",
					dataSource : {
						transport : {
							read : "${allChecksUrl}/" + broadTeleRateName,
						}
					},
					change : function(e) {
						if (this.value() && this.selectedIndex == -1) {
							alert("Rate Name doesn't exist!");
							$("#broadTeleRateName").data("kendoComboBox").value("");
						}
						wtRateName1 = this.dataItem().text;
					}
				});

		$('<span class="k-invalid-msg" data-for="Rate Name"></span>').appendTo(
				container);
	}

	function broadTeleRateTypeEditor(container, options) {
		var res = (container.selector).split("=");
		var broadTeleRateType = res[1].substring(0, res[1].length - 1);
		$(
				"<select name='Rate Type' id='broadTeleRateType' data-bind='value:" + broadTeleRateType + "'required='true'/>")
				.appendTo(container).kendoDropDownList({
					dataTextField : "text",
					dataValueField : "value",
					placeholder : "Select Rate Type",
					dataSource : {
						transport : {
							read : "${allChecksUrl}/" + broadTeleRateType,
						}
					},
					change : function(e) {
						if (this.value() && this.selectedIndex == -1) {
							alert("Rate Type doesn't exist!");
							$("#broadTeleRateType").data("kendoComboBox").value("");
						}
						wtRateType1 = this.dataItem().text;
					}
				});

		$('<span class="k-invalid-msg" data-for="Rate Type"></span>').appendTo(
				container);
	}
	
	function toRateUOMEditor(container, options) {
		var res = (container.selector).split("=");
		var broadTeleRateUOM = res[1].substring(0, res[1].length - 1);
		$(
				"<select name='Rate UOM' data-bind='value:" + broadTeleRateUOM + "'required='true'/>")
				.appendTo(container).kendoDropDownList({
					dataTextField : "text",
					dataValueField : "value",
					placeholder : "Select Rate UOM",
					dataSource : {
						transport : {
							read : "${allChecksUrl}/" + broadTeleRateUOM,
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
	
	function broadTeleRateMasterParse (response)
	{   
	    $.each(response, function (idx, elem) {
	    	$.each(elem, function (idx1, elem1) 
	    	{
	    		if(idx1 == "broadTelersId")
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
	    		
	    		if(idx1 == "broadTelSlabNo")
		    	{
		    		nextwtSlabNo = elem1;
		    	}
		    	
		    	if(idx1 == "broadTelSlabFrom")
		    	{
		    		nextslabFrom = elem1;
		    	}
		    	
		    	if(idx1 == "broadTelSlabTo")
		    	{
		    		nextslabTo = elem1;
		    	}
	        });
            
        });
        return response;
	}

 	 $("#broadTeleRateMasterGrid").on("click", ".k-grid-showAllBroadTeleRateMasters", function(e)
 	{
 		 var showAll = "showAll";
        
         $.ajax({
        	 url : "./broadTeleRateMaster/read",  
        	 type : 'GET',
             dataType: "json",
             data:{showAll:showAll},
             contentType: "application/json; charset=utf-8",
             success: function (result)
             {
            	  var grid = $("#broadTeleRateMasterGrid").getKendoGrid();
                  var data = new kendo.data.DataSource();
                  grid.dataSource.data(result);
                  grid.refresh();  
              
             },
         });
	});  

 function broadTeleValidFromEditor(container, options)
 {
		$(
		'<input name="Valid From" data-value-field="' + options.field + '" data-bind="value:' + options.field + '" data-format="' + options.format + '" required="true"/>')
		.appendTo(container).kendoDatePicker({
			change : function(e) {
				$.ajax({
					type : "GET",
					url : '${getMaxDate}',
					dataType : "JSON",
					data : {
						broadTeleRateName : wtRateName1,
						broadTeleTariffId : broadTeleTariffId,
						broadTeleRateType : wtRateType1,
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
		});

		$('<span class="k-invalid-msg" data-for="Valid From"></span>').appendTo(
				container);
	}
 
 $(document).on('change','input[name="broadTeleMaxSlabToValue"]',
		   function() {
		    var test = $('input[name="broadTeleMaxSlabToValue"]:checked').length ? $('input[name="broadTeleMaxSlabToValue"]:checked').val() : '';

		    if (test == "") 
		    {
		        $('label[for="broadTelSlabTo"]').parent("div").show();
		        $('div[data-container-for="broadTelSlabTo"]').show();
		    }
		    if (test == "on")
		    {
		    	$('label[for="broadTelSlabTo"]').parent("div").hide();
		    	$('div[data-container-for="broadTelSlabTo"]').hide();
				$('input[name="broadTelSlabTo"]').prop('required',false);
				$('input[name="broadTelSlabTo"]').val(999999);
		    }
		   });
		 
			
			var slabTypeToValidate="";
			function broadTeleRateSlabTypeEditor(container, options) {
				var res = (container.selector).split("=");
				var broadTelSlabType = res[1].substring(0, res[1].length - 1);
				$(
						"<select name='Slab Type' id='broadTelSlabType' data-bind='value:" + broadTelSlabType + "'required='true'/>")
						.appendTo(container).kendoDropDownList({
							dataTextField : "text",
							dataValueField : "value",
							placeholder : "Select Slab Type",
							dataSource : {
								transport : {
									read : "${allChecksUrl}/" + broadTelSlabType,
								}
							},
							change : function(e) 
							{
								if (this.value() && this.selectedIndex == -1) {
									alert("Slab Type doesn't exist!");
									$("#broadTelSlabType").data("kendoComboBox").value("");
								}
								slabTypeToValidate = this.dataItem().text;
								
								if(slabTypeToValidate == "Not applicable")
									{
									$('label[for="broadTelSlabTo"]').parent("div").hide();
							    	$('div[data-container-for="broadTelSlabTo"]').hide();
									$('input[name="broadTelSlabTo"]').prop('required',false);
									$('input[name="broadTelSlabTo"]').val(null);
									
									$('label[for="broadTelSlabFrom"]').parent("div").hide();
							    	$('div[data-container-for="broadTelSlabFrom"]').hide();
									$('input[name="broadTelSlabFrom"]').prop('required',false);
									
									$('label[for="broadTeleMaxSlabToValue"]').parent("div").hide();
							    	$('div[data-container-for="broadTeleMaxSlabToValue"]').hide();
									$('input[name="broadTeleMaxSlabToValue"]').prop('required',false);
									
									}
								else
									{
									
									$('label[for="broadTelSlabTo"]').parent("div").show();
							    	$('div[data-container-for="broadTelSlabTo"]').show();
									
									$('label[for="broadTelSlabFrom"]').parent("div").show();
							    	$('div[data-container-for="broadTelSlabFrom"]').show();
									
									$('label[for="broadTeleMaxSlabToValue"]').parent("div").show();
							    	$('div[data-container-for="broadTeleMaxSlabToValue"]').show();
									
									}
							}
						});

				$('<span class="k-invalid-msg" data-for="Slab Type"></span>').appendTo(
						container);
			}
			
			function gasRateMasterEndDateClick()
			{
			 var broadTeleRmid="";
			 var gridParameter = $("#broadTeleRateMasterGrid").data("kendoGrid");
			 var selectedAddressItem = gridParameter.dataItem(gridParameter.select());
			 broadTeleRmid = selectedAddressItem.broadTeleRmid;
			 $.ajax
			 ({   
			  type : "POST",
			  url : "./gasRateMaster/endDate/"+broadTeleRmid,
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
			   $('#broadTeleRateMasterGrid').data('kendoGrid').dataSource.read();
			  }
			 });
			}
</script>
