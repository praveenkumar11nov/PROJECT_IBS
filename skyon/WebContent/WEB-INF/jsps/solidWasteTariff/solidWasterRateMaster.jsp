<%@include file="/common/taglibs.jsp"%>

<c:url value="/solidWasteRateMaster/read" var="solidWasteRatemasterReadUrl" />
<c:url value="/solidWasteRateMaster/create" var="solidWasteRatemasterCreateUrl" />
<c:url value="/solidWasteRateMaster/update" var="solidWasteRatemasterUpdateUrl" />
<c:url value="/solidWasteRateMaster/destroy" var="solidWasteRatemasterDestroyUrl" />
<c:url value="/common/getAllChecks" var="allChecksUrl" />
<c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />
<c:url value="/solidWasteRateMaster/getToTariffMasterComboBoxUrl" var="solidWasteTariffMasterComboBoxUrl" />
<c:url value="/common/getAllChecks" var="allChecksUrl" />

<!-- for rate slabs -->
<c:url value="/tariff/solidWasteRateslab/read" var="solidWasteRateSlabReadUrl" />
<c:url value="/tariff/solidWasteRateslab/create" var="solidWasteRateSlabCreateUrl" />
<c:url value="/tariff/solidWasteRateslab/update" var="solidWasteRateSlabUpdateUrl" />
<c:url value="/tariff/solidWasteRateslab/destroy" var="solidWasteRateSlabDeleteUrl" />

<c:url value="/solidWasteTariff/getMaxDate" var="getMaxDate"></c:url>

<!-- Filters Data url's -->
<c:url value="/solidWasteTariff/filter" var="commonFilterUrl" />
<c:url value="/solidWasteTariffNameToFilter/filter" var="solidWasteTariffNameToFilter" />


<kendo:grid name="solidWasteRateMasterGrid" remove="solidWasteRateMasterDeleteEvent" resizable="true" pageable="true"  selectable="true" edit="solidWasteRrateMasterEvent" change="onChangesolidWasteeRateMaster" detailTemplate="solidWasteRateMasterSubGrid" sortable="true" scrollable="true" groupable="true">
	
	<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" ></kendo:grid-pageable>
	<kendo:grid-filterable extra="false">
		<kendo:grid-filterable-operators>
			<kendo:grid-filterable-operators-string eq="Is equal to" />
		</kendo:grid-filterable-operators>

	</kendo:grid-filterable>
	<kendo:grid-editable mode="popup"/>
	<kendo:grid-toolbar>
		<kendo:grid-toolbarItem name="create" text="Add Rate Master" />
		<kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFiltersolidWasteeRateMaster()><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>"/>
		<kendo:grid-toolbarItem name="showAllsolidWasteeRateMasters" text="Show All" />
	</kendo:grid-toolbar>
	<kendo:grid-columns>

		<kendo:grid-column title="BROAD_TELE_RM_ID" field="solidWasteRmId" width="110px" hidden="true" />
		
		<kendo:grid-column title="Tariff Master *" field="solidWasteTariffName" width="100px">
		
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
												read : "${solidWasteTariffNameToFilter}/solidWasteTariffName"
											}
										}
									});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>
		
		<kendo:grid-column title="Tariff Master *" field="solidWasteTariffId" editor="solidWasteTariffMasterEditor" width="100px" hidden="true"> 
		</kendo:grid-column>

		<kendo:grid-column title="Rate&nbsp;Name*" field="solidWasteRateName" editor="solidWasteRateNameEditor" width="85px">
			<kendo:grid-column-values value="${solidWasteRateName}" />
		</kendo:grid-column>

		<kendo:grid-column title="Rate&nbsp;Description*"
			field="solidWasteRateDescription" filterable="true" width="120px">
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function solidWasteRateDescriptionFilter(element) {
							element
									.kendoAutoComplete({
										placeholder : "Enter Description",
										dataType : 'JSON',
										dataSource : {
											transport : {
												read : "${commonFilterUrl}/solidWasteRateDescription"
											}
										}
									});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>

		<kendo:grid-column title="Rate&nbsp;Type&nbsp;*" field="solidWasteRateType" editor="solidWasteRateTypeEditor" width="90px">
			<kendo:grid-column-values value="${solidWasteRateType}" />
		</kendo:grid-column>

		<kendo:grid-column title="Rate&nbsp;UOM&nbsp;*" field="solidWasteRateUOM"  width="90px">
			<kendo:grid-column-values value="${solidWasteRateUOM}" />
		</kendo:grid-column>

		<kendo:grid-column title="Valid&nbsp;From&nbsp;*" field="solidWasteValidFrom" editor="solidWasteValidFromEditor" format="{0:dd/MM/yyyy}" width="90px" >
		
		<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function solidWasteValidFromFilter(element) {
							element.kendoDatePicker({
							        format: "dd/MM/yyyy"
							});
						}
					</script>
				</kendo:grid-column-filterable-ui>
		</kendo:grid-column-filterable>
			
		</kendo:grid-column>

		<kendo:grid-column title="Valid&nbsp;To&nbsp;*" field="solidWasteValidTo" format="{0:dd/MM/yyyy}" width="80px">
				<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function solidWasteValidToFilter(element) {
							element.kendoDatePicker({
								 format: "dd/MM/yyyy"
							});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column> 

		<kendo:grid-column title="Min Rate(In Rs)" field="solidWasteMinRate" width="100px" >
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function solidWasteMinRateFilter(element) {
							element.kendoNumericTextBox({});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>
		<kendo:grid-column title="Max Rate(In Rs)" field="solidWasteMaxRate" width="100px" >
		<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function solidWasteMaxRateToFilter(element) {
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
			template="<a href='\\\#' id='solidWasteTemPID' class='k-button k-button-icontext btn-destroy k-grid-Active#=data.solidWasteRmId#'>#= data.status == 'Active' ? 'Inactive' : 'Active' #</a>"
			width="70px" />
	   </kendo:grid-columns>

	<kendo:dataSource pageSize="20" requestEnd="solidWasteRequestEndRateMaster" requestStart="solidWasteRequestStartRateMaster">
	
		<kendo:dataSource-transport>
			<kendo:dataSource-transport-create url="${solidWasteRatemasterCreateUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-read url="${solidWasteRatemasterReadUrl}" dataType="json" type="POST" contentType="application/json" />
			<kendo:dataSource-transport-update url="${solidWasteRatemasterUpdateUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-destroy url="${solidWasteRatemasterDestroyUrl}" dataType="json" type="GET" contentType="application/json" />
		</kendo:dataSource-transport>

		<kendo:dataSource-schema>
			<kendo:dataSource-schema-model id="solidWasteRmId">
				<kendo:dataSource-schema-model-fields>

					<kendo:dataSource-schema-model-field name="solidWasteRmId" type="number"/>

					<kendo:dataSource-schema-model-field name="solidWasteTariffId" type="string" defaultValue="Select">
						<kendo:dataSource-schema-model-field-validation />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="solidWasteRateName"  defaultValue="Residential Collection">
						<kendo:dataSource-schema-model-field-validation />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="solidWasteRateDescription" type="string">
						<kendo:dataSource-schema-model-field-validation />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="solidWasteRateType"  defaultValue="Single Slab" />

					<kendo:dataSource-schema-model-field name="solidWasteRateUOM"  defaultValue="Liters">
						<kendo:dataSource-schema-model-field-validation required="true" />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="solidWasteValidFrom" type="date">
						<kendo:dataSource-schema-model-field-validation required="true" />
					</kendo:dataSource-schema-model-field>

				 	<kendo:dataSource-schema-model-field name="solidWasteValidTo" type="date">
					</kendo:dataSource-schema-model-field> 

					<kendo:dataSource-schema-model-field name="solidWasteMinRate" type="number">
						<kendo:dataSource-schema-model-field-validation min="0" />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="solidWasteMaxRate" type="number">
						<kendo:dataSource-schema-model-field-validation min="0" />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="status" editable="true" type="string" />
					
					<kendo:dataSource-schema-model-field name="solidWasteTariffName" type="string" />
					
				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>

	</kendo:dataSource>
</kendo:grid>

<kendo:grid-detailTemplate id="solidWasteRateMasterSubGrid">
	<kendo:tabStrip name="solidWasteRateMasterTabStrip_#=solidWasteRmId#" >
		<kendo:tabStrip-items>

			<kendo:tabStrip-item text="Rate Slab" selected="true">
				<kendo:tabStrip-item-content>
					<div class='solidWasteRateSlbDIV'>
						<br />
						<kendo:grid name="solidWasteRateSlabGrid_#=solidWasteRmId#" pageable="true" filterable="true" dataBound="CheckBoxDataBoundsolidWastee"
							change="onGridChangesolidWastee" resizable="true" sortable="true"
							reorderable="true" selectable="true" scrollable="true"
							edit="solidWasteRateSlabEditEvent" remove="solidWasteRateSlabDeleteEvent">
							
							<kendo:grid-pageable pageSizes="true" buttonCount="5"
								pageSize="10" input="true" numeric="true"></kendo:grid-pageable>
							<kendo:grid-filterable extra="false">
								<kendo:grid-filterable-operators>
									<kendo:grid-filterable-operators-string eq="Is equal to" />
								</kendo:grid-filterable-operators>
							</kendo:grid-filterable>
							<kendo:grid-editable mode="popup"/>
							<kendo:grid-toolbar>
								<kendo:grid-toolbarItem name="create" text="Add Rate Slab" />
								<kendo:grid-toolbarItem text="Merge" />
							</kendo:grid-toolbar>

							<kendo:grid-columns>
								<kendo:grid-column title="&nbsp;" width="30px">
									<!-- File Upload Button Purpose -->
								</kendo:grid-column>

								<kendo:grid-column title="WT_RS_ID" field="solidWasteRsId" hidden="true" width="70px" />
								
								<kendo:grid-column title="Slab Number *" field="solidWasteSlabNo" width="70px" filterable="false" />
								
								<kendo:grid-column title="Slab Type *" field="solidWasteSlabType" editor="solidWasteRateSlabTypeEditor" width="70px" filterable="false">
									<kendo:grid-column-values value="${solidWasteSlabType}" />
								</kendo:grid-column>

							    <kendo:grid-column title="Slab From *" field="solidWasteDummySlabFrom" width="80px" filterable="false"/> 
								<kendo:grid-column title="Slab From *" field="solidWasteSlabFrom" width="80px" hidden="true" filterable="false"/>
								
								<kendo:grid-column title="Max Value" field="solidWasteMaxSlabToValue" filterable="false" sortable="false" width="0px" hidden="true" />
								<kendo:grid-column title="Slab To *" field="solidWasteDummySlabTo" width="80px" filterable="false" />
								<kendo:grid-column title="Slab To *" field="solidWasteSlabTo" width="70px" hidden="true" filterable="false" />
								
								<kendo:grid-column title="Rate Type *" field="solidWasteSlabRateType" width="70px" filterable="false" >
									<kendo:grid-column-values value="${solidWasteSlabRateType}" />
								</kendo:grid-column>
								
								<kendo:grid-column title="Rate *" field="solidWasteRate" width="70px" filterable="false" />
								
								<kendo:grid-column title="Slab Status *" field="status" width="70px" filterable="false" >
									<kendo:grid-column-values value="${status}" />
								</kendo:grid-column>

								<kendo:grid-column title="&nbsp;" width="250px">
									<kendo:grid-column-command>
									<kendo:grid-column-commandItem name="Split" click="solidWasteSplitFunction" />
										<kendo:grid-column-commandItem name="edit" />
										<kendo:grid-column-commandItem name="destroy" />
										<kendo:grid-column-commandItem name="Change Status" click="solidWasteRateSlabStatusChange" />
									</kendo:grid-column-command>
								</kendo:grid-column>

							</kendo:grid-columns>

							<kendo:dataSource requestEnd="solidWasteRateSlabRequestEnd" requestStart="solidWasteRateSlabRequestStart">
								<kendo:dataSource-transport>
									<kendo:dataSource-transport-read url="${solidWasteRateSlabReadUrl}/#=solidWasteRmId#" dataType="json" type="POST" contentType="application/json" />
									<kendo:dataSource-transport-create url="${solidWasteRateSlabCreateUrl}/#=solidWasteRmId#" dataType="json" type="GET" contentType="application/json" />
									<kendo:dataSource-transport-update url="${solidWasteRateSlabUpdateUrl}" dataType="json" type="GET" contentType="application/json" />
									<kendo:dataSource-transport-destroy url="${solidWasteRateSlabDeleteUrl}" dataType="json" type="GET" contentType="application/json" />
								</kendo:dataSource-transport>

								<kendo:dataSource-schema parse="solidWasteRateMasterParse">
									<kendo:dataSource-schema-model id="solidWasteRsId">
										<kendo:dataSource-schema-model-fields>

											<kendo:dataSource-schema-model-field name="solidWasteRsId"/>

											<kendo:dataSource-schema-model-field name="solidWasteSlabNo" type="number">
												<kendo:dataSource-schema-model-field-validation min="1" />
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="solidWasteSlabType" type="String" defaultValue="Numeric"/>

											<kendo:dataSource-schema-model-field name="solidWasteSlabRateType" type="String" defaultValue="Paise"/>

											<kendo:dataSource-schema-model-field name="solidWasteRate" type="number">
												<kendo:dataSource-schema-model-field-validation min="0" />
											</kendo:dataSource-schema-model-field>

											<kendo:dataSource-schema-model-field name="solidWasteSlabFrom" type="number">
											<kendo:dataSource-schema-model-field-validation min="0" max="999999"/>
											</kendo:dataSource-schema-model-field>
											
										   <kendo:dataSource-schema-model-field name="solidWasteDummySlabFrom" type="String"/>
											
											<kendo:dataSource-schema-model-field name="solidWasteMaxSlabToValue" type="boolean"/>
											
											<kendo:dataSource-schema-model-field name="solidWasteSlabTo" type="number">
											<kendo:dataSource-schema-model-field-validation min="0" max="999999"/>
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="solidWasteDummySlabTo" type="String"/>
											
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
					<input type="number" name="solidWasteSlabNo" id="solidWasteSlabNo" class="text ui-widget-content ui-corner-all" readonly="readonly">
				</td>
			</tr>

			<tr>
				<td style="height: 10px; width: 100px; text-align: center; vertical-align: middle;">Rate</td>
				<td style="height: 40px; width: 100px; text-align: center; vertical-align: middle;">
					<input type="number" name="solidWasteRate" id="solidWasteRate" class="text ui-widget-content ui-corner-all">
				</td>
			</tr>

			<tr>
				<td style="height: 10px; width: 100px; text-align: center; vertical-align: middle;">Slab From</td>
				<td style="height: 40px; width: 100px; text-align: center; vertical-align: middle;">
					<input type="number" name="solidWasteSlabFrom" id="solidWasteSlabFrom" class="text ui-widget-content ui-corner-all" readonly="readonly">
				</td>
			</tr>

			<tr>
				<td style="height: 10px; width: 100px; text-align: center; vertical-align: middle;">Slab To</td>
				<td style="height: 40px; width: 100px; text-align: center; vertical-align: middle;">
					<input type="number" name="solidWasteSlabTo" id="solidWasteSlabTo" class="text ui-widget-content ui-corner-all">
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

var solidWasteTariffId;
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
	 						    var solidWasteSlabNo = $("#solidWasteSlabNo").val();
								var solidWasteRate = $("#solidWasteRate").val();
								var solidWasteSlabFrom = $("#solidWasteSlabFrom").val();
								var solidWasteSlabTo = $("#solidWasteSlabTo").val();
								var hiddenwtrsId = $("#hiddenwtrsId").val();
								var wtSlabNo1 = $("#wtSlabNo1").val();
								var rate1 = $("#rate1").val();
								var slabFrom1 = $("#slabFrom1").val();
								var slabTo1 = $("#slabTo1").val();
								
								  if (hiddenwtrsId == "") {
									alert("form1 Slab number should not be empty.");
									return false;
								}

								if (solidWasteSlabNo == "") {
									alert("form1 Slab number should not be empty.");
									return false;
								}
								
								if (solidWasteRate == "") {
									alert("form1 Rate should not be empty.");
									return false;
								}
								else
								{
									if (parseInt(solidWasteRate) < 0) 
									{
										alert("form1 Rate should greater than 1.");
										return false;
									}
									
								}
								
								if (solidWasteSlabFrom == "") {
									alert("form1 Slab from should not be empty.");
									return false;
								}

								if (solidWasteSlabTo == "") {
									alert("form1 Slab To should not be empty.");
									return false;
								} else {
									if (parseInt(solidWasteSlabTo) <= parseInt(solidWasteSlabFrom)) {
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
									var form1SlabTo = solidWasteSlabTo;
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
											url : "./tariff/solidWasteRateslab/rateSlabSplit",
											data : {
												hiddenwtrsId : hiddenwtrsId,
												solidWasteSlabNo : solidWasteSlabNo,
												solidWasteRate : solidWasteRate,
												solidWasteSlabFrom : solidWasteSlabFrom,
												solidWasteSlabTo : solidWasteSlabTo,
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
															'#solidWasteRateSlabGrid_'
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

	function CheckBoxDataBoundsolidWastee(e)
	{
		var grid = $("#solidWasteRateSlabGrid_" + SelectedRowId).data("kendoGrid");
		var gridData = grid._data;
		var i = 0;
		this.tbody
				.find("tr td:first-child")
				.each(
						function(e) {
							$(
									'<input type="checkbox" name="chkbox" id="singleSelectChkBx_'
											+ gridData[i].solidWasteRsId
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
										alert("New solidWasteRate should be greater than 1.");
										return false;
									}
									alert("New solidWasteRate should not be empty.");
									return false;
								}

								$
										.ajax({
											type : "POST",
											data : {
												newrate : newrate,
											},
											url : "./tariff/solidWasteRateslab/getNewRate",
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
															'#solidWasteRateSlabGrid_'
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

	$("#solidWasteRateMasterGrid")
			.on(
					"click",
					".k-grid-Merge",
					function() {
				 		var result=securityCheckForActionsForStatus("./Tariff/SolidWaste/RateSlab/mergeRateSlab"); 
						if(result=="success"){ 
						
						if((rateMasterSlabType === 'Single Slab') && (parseInt(nextwtSlabNo) === 1))
						{
						 var grid = $("#solidWasteRateSlabGrid_" + SelectedRowId).data("kendoGrid");
						 grid.cancelRow();
					     alert("Only one solidWasteRate slab allowed for single slab solidWasteRate master.");
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
									url : "./tariff/solidWasteRateslab/merge",
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
													'#solidWasteRateSlabGrid_'
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
													'#solidWasteRateSlabGrid_'
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
	function onChangesolidWasteeRateMaster(arg) {
		var gview = $("#solidWasteRateMasterGrid").data("kendoGrid");
		var selectedItem = gview.dataItem(gview.select());
		SelectedRowId = selectedItem.solidWasteRmId;
		rateMasterSlabType = selectedItem.solidWasteRateType;
		this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));  	 
	    $(".k-master-row.k-state-selected").show();
	}


	var solidWasteRsId = "";
	var solidWasteSlabNo = "";
	var solidWasteRate = "";
	var solidWasteSlabFrom = "";
	var solidWasteSlabTo = "";
	function solidWasteSplitFunction() 
	{
		
 		var result=securityCheckForActionsForStatus("./Tariff/SolidWaste/RateSlab/splitRateSlab"); 
		if(result=="success"){ 
			if((rateMasterSlabType === 'Single Slab') && (parseInt(nextwtSlabNo) === 1))
			{
			 var grid = $("#solidWasteRateSlabGrid_" + SelectedRowId).data("kendoGrid");
			 grid.cancelRow();
		     alert("Only one solidWasteRate slab allowed for single slab solidWasteRate master.");
		     return false;
			}
			
			elRateSlabSplitDialog.dialog("open");
			$("#hiddenwtrsId").val(solidWasteRsId);
			$("#solidWasteSlabNo").val(solidWasteSlabNo);
			var wtSlabNo1 = solidWasteSlabNo + 1;
			$("#wtSlabNo1").val(wtSlabNo1);
			$("#solidWasteRate").val(solidWasteRate);
			$("#solidWasteSlabFrom").val(solidWasteSlabFrom);
			$("#slabTo1").val(solidWasteSlabTo);
		}
	}

	function onGridChangesolidWastee() {
		var elRateSlabGrid = $("#solidWasteRateSlabGrid_" + SelectedRowId)
				.data("kendoGrid");
		var selectedRowItem = elRateSlabGrid.dataItem(elRateSlabGrid.select());
		solidWasteRsId = selectedRowItem.solidWasteRsId;
		solidWasteSlabNo = selectedRowItem.solidWasteSlabNo;
		solidWasteRate = selectedRowItem.solidWasteRate;
		solidWasteSlabFrom = selectedRowItem.solidWasteSlabFrom;
		solidWasteSlabTo = selectedRowItem.solidWasteSlabTo;
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

	$("#solidWasteRateMasterGrid").on("click", "#solidWasteTemPID", function(e) {
		var button = $(this),
		enable = button.text() == "Active";
		var widget = $("#solidWasteRateMasterGrid").data("kendoGrid");
		var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
		
 		var result=securityCheckForActionsForStatus("./Tariff/SolidWaste/RateMaster/activitRateMaster"); 
		if(result=="success"){ 
		if (enable)
		{
			$.ajax({
				type : "POST",
				url : "./solidWasteTariff/tariffStatus/" + dataItem.solidWasteRmId + "/activate",
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
					$('#solidWasteRateMasterGrid').data('kendoGrid').dataSource.read();
				}
			});
		} else {
			$.ajax({
				type : "POST",
				url : "./solidWasteTariff/tariffStatus/" + dataItem.solidWasteRmId + "/deactivate",
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
					button.text('Active');
					$('#solidWasteRateMasterGrid').data('kendoGrid').dataSource.read();
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
	function solidWasteRrateMasterEvent(e)
	{

		/***************************  to remove the id from pop up  **********************/
		$('div[data-container-for="solidWasteRmId"]').remove();
		$('label[for="solidWasteRmId"]').closest('.k-edit-label').remove();
		
		$('div[data-container-for="solidWasteTariffName"]').remove();
		$('label[for="solidWasteTariffName"]').closest('.k-edit-label').remove();
		
		$(".k-edit-field").each(function() {
			$(this).find("#solidWasteTemPID").parent().remove();
		});

		$('label[for="status"]').parent().hide();
		$('div[data-container-for="status"]').hide();
		
		/************************* Button Alerts *************************/
	 	if (e.model.isNew()) {
	 		securityCheckForActions("./Tariff/SolidWaste/RateMaster/createRateMaster");
			$(".k-window-title").text("Add New Rate Master");
			$(".k-grid-update").text("Save");
			
			solidWasteTariffId =  e.model.solidWasteTariffId;
			wtRateName1 = e.model.solidWasteRateName;
			wtRateType1 = e.model.solidWasteRateType;
			selectedDate  =  e.model.solidWasteValidFrom;
			selectedDateFrom = e.model.solidWasteValidFrom;
		} 
	 	else
		{
	 		rateMasterEditFloag = 1;
	 		securityCheckForActions("./Tariff/SolidWaste/RateMaster/updateRateMaster");
			$.ajax({
				type : "GET",
				url : '${getMaxDate}',
				dataType : "JSON",
				data : {
					solidWasteRateName : e.model.solidWasteRateName,
					solidWasteTariffId : e.model.solidWasteTariffId,
					solidWasteRateType : e.model.solidWasteRateType,
				},
				success : function(response)
				{
					nextFromDate = response;
				}
			});
		    selectedDateFrom = e.model.solidWasteValidFrom;
			$(".k-window-title").text("Edit Rate Master Details");
		} 
		
	}

	/********************** to hide the child table id ***************************/
	function solidWasteRateSlabEditEvent(e) {

		$('div[data-container-for="solidWasteRsId"]').remove();
		$('label[for="solidWasteRsId"]').closest('.k-edit-label').remove();

		$('div[data-container-for="status"]').remove();
		$('label[for="status"]').closest('.k-edit-label').remove();
		
		$('div[data-container-for="solidWasteDummySlabTo"]').remove();
		$('label[for="solidWasteDummySlabTo"]').closest('.k-edit-label').remove();
		
		$('div[data-container-for="solidWasteDummySlabFrom"]').remove();
		$('label[for="solidWasteDummySlabFrom"]').closest('.k-edit-label').remove();

		if (e.model.isNew()) 
		{
			securityCheckForActions("./Tariff/SolidWaste/RateSlab/createRateSlab");
			if((rateMasterSlabType === 'Single Slab') && (parseInt(nextwtSlabNo) === 1))
					{
					 var grid = $("#solidWasteRateSlabGrid_" + SelectedRowId).data("kendoGrid");
					 grid.cancelRow();
				     alert("Only one solidWasteRate slab allowed for single slab solidWasteRate master.");
				     return false;
					}
			
			if(parseInt(nextslabTo) === 999999)
				{
				 var grid = $("#solidWasteRateSlabGrid_" + SelectedRowId).data("kendoGrid");
				 grid.cancelRow();
			     alert("You can't the add the new record,Slab to Max is the last record.");
			     return false;
				}
			
			$(".k-window-title").text("Add New Rate Slab");
			$(".k-grid-update").text("Save");
		} else 
		{
			editFloag = 1;
			securityCheckForActions("./Tariff/SolidWaste/RateSlab/updateRateSlab");
			
			if(e.model.solidWasteSlabType == "Not applicable")
			{
			$('label[for="solidWasteSlabTo"]').hide();
	    	$('div[data-container-for="solidWasteSlabTo"]').hide();
			$('input[name="solidWasteSlabTo"]').prop('required',false);
			$('input[name="solidWasteSlabTo"]').val(null);
			
			$('label[for="solidWasteSlabFrom"]').hide();
	    	$('div[data-container-for="solidWasteSlabFrom"]').hide();
			$('input[name="solidWasteSlabFrom"]').prop('required',false);
			
			$('label[for="solidWasteMaxSlabToValue"]').hide();
	    	$('div[data-container-for="solidWasteMaxSlabToValue"]').hide();
			$('input[name="solidWasteMaxSlabToValue"]').prop('required',false);
			
			}
		else
			{
			
			$('label[for="solidWasteSlabTo"]').show();
	    	$('div[data-container-for="solidWasteSlabTo"]').show();
			
			$('label[for="solidWasteSlabFrom"]').show();
	    	$('div[data-container-for="solidWasteSlabFrom"]').show();
			
			$('label[for="solidWasteMaxSlabToValue"]').show();
	    	$('div[data-container-for="solidWasteMaxSlabToValue"]').show();
			
			}
			    if (e.model.solidWasteSlabTo == parseInt(999999))
			    {
			    	$('input[name="solidWasteMaxSlabToValue"]').prop('checked', true);
			    	$('label[for="solidWasteSlabTo"]').hide();
			    	$('div[data-container-for="solidWasteSlabTo"]').hide();
					$('input[name="solidWasteSlabTo"]').prop('required',false);
					$('input[name="solidWasteSlabTo"]').val(999999);
			    }
			
			$(".k-window-title").text("Edit Rate Slab Details");
		}
	}

	 function clearFiltersolidWasteeRateMaster()
	 {
		 $("form.k-filter-menu button[type='reset']").slice().trigger("click");
		   var elRatemaster = $("#solidWasteRateMasterGrid").data("kendoGrid");
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

									if (input.attr("name") == "solidWasteRateName") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},
								descriptionValidation : function(input, params) {

									if (input.attr("name") == "solidWasteRateDescription") {
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

								solidWasteRateType : function(input, params) {
									if (input.attr("name") == "solidWasteRateType") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},

								solidWasteRateUOM : function(input, params) {
									if (input.attr("name") == "solidWasteRateUOM") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},

 								addressSeasonTo2 : function(input, params)
								{
									if (input.filter("[name = 'solidWasteValidTo']").length && input.val() != "")
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
									if (input.filter("[name='solidWasteRateUOM']").length
											&& input.val() != "") {
										return /^[a-zA-Z0-9 ]{1,45}$/
												.test(input.val());
									}
									return true;
								},

								/************** for Inner Grid Validation ************/
								slabNoValidator : function(input, params) {
									if (input.attr("name") == "solidWasteSlabNo") 
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
										if ((input.attr("name") == "solidWasteSlabNo") && (parseInt(input.val()) !=  parseInt((nextwtSlabNo+1))))
										{
												 return false;
										 }
								   }
								
								  	return true;
								}, 
								rateValidator : function(input, params)
								{
									if  ((input.attr("name") == "solidWasteRate") && (input.val() == 0)) {
										return false;
									}
									return true;
								}, 
								slabToValidator : function(input, params)
								{
									if  ((input.attr("name") == "solidWasteSlabTo") && (input.val() == 0))
									{
										return false;
									}
									return true;
								},
							
								slabToGreaterThanSlabFrom : function(input, params)
								{
									if (input.attr("name") == "solidWasteSlabTo") 
									{
										slabToToCompare = input.val();
									}
									if (input.attr("name") == "solidWasteSlabFrom")
									{
										slabFromToCompare = input.val();
									}
									if (input.attr("name") == "solidWasteSlabTo") 
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
								
									if ((input.attr("name") == "solidWasteSlabFrom") && (parseInt(input.val()) < 0)) 
									{
										return false;
									}
									return true;
							},
								slabToValidator : function(input, params)
								{
									if  ((input.attr("name") == "solidWasteSlabTo") && (parseInt(input.val()) <= 0))
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
								solidWasteRateType : "Rate Type should not be empty",
								solidWasteRateUOM : "UOM should not be empty",
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

	function solidWasteRequestStartRateMaster(e){
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();
	}
	
	function solidWasteRateSlabRequestStart(e){
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();
	}
	function solidWasteRequestEndRateMaster(r) {

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
				$('#solidWasteRateMasterGrid').data('kendoGrid').dataSource.read();
				$('#solidWasteRateMasterGrid').data('kendoGrid').refresh();
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
				$('#solidWasteRateMasterGrid').data('kendoGrid').dataSource.read();
				$('#solidWasteRateMasterGrid').data('kendoGrid').refresh();
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
				$('#solidWasteRateMasterGrid').data('kendoGrid').dataSource.read();
				$('#solidWasteRateMasterGrid').data('kendoGrid').refresh();
			}
			
			 if (r.type == "create") {
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
				$('#solidWasteRateMasterGrid').data('kendoGrid').dataSource.read();
				$('#solidWasteRateMasterGrid').data('kendoGrid').refresh();
			}
			
			 if (r.type == "destroy")
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
				$('#solidWasteRateMasterGrid').data('kendoGrid').dataSource.read();
				$('#solidWasteRateMasterGrid').data('kendoGrid').refresh();
			}
		}
	}
	/************************************* for inner rate slab request *********************************/

	function solidWasteRateSlabRequestEnd(e)
	{
			var rateSlab = $("#solidWasteRateSlabGrid_" + SelectedRowId).data("kendoGrid");
			
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

	function solidWasteRateSlabStatusChange() {
		var solidWasteRsId = "";
		var gridParameter = $("#solidWasteRateSlabGrid_" + SelectedRowId).data("kendoGrid");
		var selectedAddressItem = gridParameter
				.dataItem(gridParameter.select());
		solidWasteRsId = selectedAddressItem.solidWasteRsId;
  		var result=securityCheckForActionsForStatus("./Tariff/SolidWaste/RateSlab/activitRateSlab"); 
		if(result=="success")
		{ 
			$.ajax({
				type : "POST",
				url : "./tariff/solidWasteRateSlabStatusUpdateFromInnerGrid/" + solidWasteRsId,
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
					$('#solidWasteRateSlabGrid_' + SelectedRowId).data('kendoGrid').dataSource
							.read();
				}
			});
		}
	}


	function solidWasteTariffMasterEditor(container, options) {
		$(
				'<input name="Tariff Name" id="solidWasteTariffName" data-text-field="solidWasteTariffName" data-value-field="solidWasteTariffId" data-bind="value:' + options.field + '" required="true"/>')
				.appendTo(container).kendoDropDownList({
					autoBind : false,
					placeholder : "Select Tariff Name",
					dataSource : {
						transport : {
							read : "${solidWasteTariffMasterComboBoxUrl}"
						}
					},
					change : function(e) {
						if (this.value() && this.selectedIndex == -1) {
							alert("Tariff Name doesn't exist!");
							$("#solidWasteTariffName").data("kendoComboBox").value("");
						}
						solidWasteTariffId = this.dataItem().solidWasteTariffId;
					}
				});

		$('<span class="k-invalid-msg" data-for="Tariff Name"></span>')
				.appendTo(container);
	}

	function solidWasteRateNameEditor(container, options) {
		var res = (container.selector).split("=");
		var solidWasteRateName = res[1].substring(0, res[1].length - 1);
		$(
				"<select name='Rate Name'id='solidWasteRateName' data-bind='value:" + solidWasteRateName + "'required='true'/>")
				.appendTo(container).kendoDropDownList({
					dataTextField : "text",
					dataValueField : "value",
					placeholder : "Select Rate Name",
					dataSource : {
						transport : {
							read : "${allChecksUrl}/" + solidWasteRateName,
						}
					},
					change : function(e) {
						if (this.value() && this.selectedIndex == -1) {
							alert("Rate Name doesn't exist!");
							$("#solidWasteRateName").data("kendoComboBox").value("");
						}
						wtRateName1 = this.dataItem().text;
					}
				});

		$('<span class="k-invalid-msg" data-for="Rate Name"></span>').appendTo(
				container);
	}

	function solidWasteRateTypeEditor(container, options) {
		var res = (container.selector).split("=");
		var solidWasteRateType = res[1].substring(0, res[1].length - 1);
		$(
				"<select name='Rate Type' id='solidWasteRateType' data-bind='value:" + solidWasteRateType + "'required='true'/>")
				.appendTo(container).kendoDropDownList({
					dataTextField : "text",
					dataValueField : "value",
					placeholder : "Select Rate Type",
					dataSource : {
						transport : {
							read : "${allChecksUrl}/" + solidWasteRateType,
						}
					},
					change : function(e) {
						if (this.value() && this.selectedIndex == -1) {
							alert("Rate Type doesn't exist!");
							$("#solidWasteRateType").data("kendoComboBox").value("");
						}
						wtRateType1 = this.dataItem().text;
					}
				});

		$('<span class="k-invalid-msg" data-for="Rate Type"></span>').appendTo(
				container);
	}
	
	function toRateUOMEditor(container, options) {
		var res = (container.selector).split("=");
		var solidWasteRateUOM = res[1].substring(0, res[1].length - 1);
		$(
				"<select name='Rate UOM' data-bind='value:" + solidWasteRateUOM + "'required='true'/>")
				.appendTo(container).kendoDropDownList({
					dataTextField : "text",
					dataValueField : "value",
					placeholder : "Select Rate UOM",
					dataSource : {
						transport : {
							read : "${allChecksUrl}/" + solidWasteRateUOM,
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
	
	function solidWasteRateMasterParse (response)
	{   
	    $.each(response, function (idx, elem) {
	    	$.each(elem, function (idx1, elem1) 
	    	{
	    		if(idx1 == "solidWasteRsId")
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
	    		
	    		if(idx1 == "solidWasteSlabNo")
		    	{
		    		nextwtSlabNo = elem1;
		    	}
		    	
		    	if(idx1 == "solidWasteSlabFrom")
		    	{
		    		nextslabFrom = elem1;
		    	}
		    	
		    	if(idx1 == "solidWasteSlabTo")
		    	{
		    		nextslabTo = elem1;
		    	}
	        });
            
        });
        return response;
	}

 	 $("#solidWasteRateMasterGrid").on("click", ".k-grid-showAllsolidWasteeRateMasters", function(e)
 	{
/*   		var result=securityCheckForActionsForStatus("./Tariff/SolidWaste/RateMaster/showAllRateMaster"); 
		if(result=="success")
		{  */
			 var showAll = "showAll";
	         $.ajax({
	        	 url : "./solidWasteRateMaster/read",  
	        	 type : 'GET',
	             dataType: "json",
	             data:{showAll:showAll},
	             contentType: "application/json; charset=utf-8",
	             success: function (result)
	             {
	            	  var grid = $("#solidWasteRateMasterGrid").getKendoGrid();
	                  var data = new kendo.data.DataSource();
	                  grid.dataSource.data(result);
	                  grid.refresh();  
	             },
	         });
	/* 	} */
	});  

 function solidWasteValidFromEditor(container, options)
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
							solidWasteRateName : wtRateName1,
							solidWasteTariffId : solidWasteTariffId,
							solidWasteRateType : wtRateType1,
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
 
 $(document).on('change','input[name="solidWasteMaxSlabToValue"]',
		   function() {
		    var test = $('input[name="solidWasteMaxSlabToValue"]:checked').length ? $('input[name="solidWasteMaxSlabToValue"]:checked').val() : '';

		    if (test == "") 
		    {
		        $('label[for="solidWasteSlabTo"]').parent("div").show();
		        $('div[data-container-for="solidWasteSlabTo"]').show();
		    }
		    if (test == "on")
		    {
		    	$('label[for="solidWasteSlabTo"]').parent("div").hide();
		    	$('div[data-container-for="solidWasteSlabTo"]').hide();
				$('input[name="solidWasteSlabTo"]').prop('required',false);
				$('input[name="solidWasteSlabTo"]').val(999999);
		    }
		   });
		 
			
			var slabTypeToValidate="";
			function solidWasteRateSlabTypeEditor(container, options) {
				var res = (container.selector).split("=");
				var solidWasteSlabType = res[1].substring(0, res[1].length - 1);
				$(
						"<select name='Slab Type' id='solidWasteSlabType' data-bind='value:" + solidWasteSlabType + "'required='true'/>")
						.appendTo(container).kendoDropDownList({
							dataTextField : "text",
							dataValueField : "value",
							placeholder : "Select Slab Type",
							dataSource : {
								transport : {
									read : "${allChecksUrl}/" + solidWasteSlabType,
								}
							},
							change : function(e) 
							{
								if (this.value() && this.selectedIndex == -1) {
									alert("Slab Type doesn't exist!");
									$("#solidWasteSlabType").data("kendoComboBox").value("");
								}
								slabTypeToValidate = this.dataItem().text;
								
								if(slabTypeToValidate == "Not applicable" || slabTypeToValidate=="Per Month")
									{
									$('label[for="solidWasteSlabTo"]').parent("div").hide();
							    	$('div[data-container-for="solidWasteSlabTo"]').hide();
									$('input[name="solidWasteSlabTo"]').prop('required',false);
									$('input[name="solidWasteSlabTo"]').val(null);
									
									$('label[for="solidWasteSlabFrom"]').parent("div").hide();
							    	$('div[data-container-for="solidWasteSlabFrom"]').hide();
									$('input[name="solidWasteSlabFrom"]').prop('required',false);
									
									$('label[for="solidWasteMaxSlabToValue"]').parent("div").hide();
							    	$('div[data-container-for="solidWasteMaxSlabToValue"]').hide();
									$('input[name="solidWasteMaxSlabToValue"]').prop('required',false);
									
									}
								else
									{
									
									$('label[for="solidWasteSlabTo"]').parent("div").show();
							    	$('div[data-container-for="solidWasteSlabTo"]').show();
									
									$('label[for="solidWasteSlabFrom"]').parent("div").show();
							    	$('div[data-container-for="solidWasteSlabFrom"]').show();
									
									$('label[for="solidWasteMaxSlabToValue"]').parent("div").show();
							    	$('div[data-container-for="solidWasteMaxSlabToValue"]').show();
									
									}
							}
						});

				$('<span class="k-invalid-msg" data-for="Slab Type"></span>').appendTo(
						container);
			}
			
			function gasRateMasterEndDateClick()
			{
			 var solidWasteRmId="";
			 var gridParameter = $("#solidWasteRateMasterGrid").data("kendoGrid");
			 var selectedAddressItem = gridParameter.dataItem(gridParameter.select());
			 solidWasteRmId = selectedAddressItem.solidWasteRmId;
			 $.ajax
			 ({   
			  type : "POST",
			  url : "./gasRateMaster/endDate/"+solidWasteRmId,
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
			   $('#solidWasteRateMasterGrid').data('kendoGrid').dataSource.read();
			  }
			 });
			}
			
			function solidWasteRateMasterDeleteEvent(){
				securityCheckForActions("./Tariff/SolidWaste/RateMaster/deleteRateMaster");
				var conf = confirm("Are you sure want to delete this Rate Master?");
				    if(!conf){
				    $('#solidWasteRateMasterGrid').data().kendoGrid.dataSource.read();
				    throw new Error('deletion aborted');
				     }
			}
		function solidWasteRateSlabDeleteEvent(){
				securityCheckForActions("./Tariff/SolidWaste/RateSlab/deleteRateSlab");
				var conf = confirm("Are you sure want to delete this Rate Slab?");
				    if(!conf){
				    $('#solidWasteRateSlabGrid_' + SelectedRowId).data().kendoGrid.dataSource.read();
				    throw new Error('deletion aborted');
				     }
			}			
			
</script>
