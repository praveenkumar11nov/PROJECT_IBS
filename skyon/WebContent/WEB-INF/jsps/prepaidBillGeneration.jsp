<%@include file="/common/taglibs.jsp"%>

<script type="text/javascript"
	src="<c:url value='/resources/jquery-validate.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jsPDF/libs/FileSaver.js/FileSaver.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jsPDF/libs/FileSaver.js/FileSaver.min.js' />"></script>
<script src="http://html2canvas.hertzen.com/build/html2canvas.js"></script>
<script src="resources/js/plugins/charts/highcharts.js"></script>
<script src="resources/js/plugins/charts/exporting.js"></script>
<script type="text/javascript"
	src="http://canvg.googlecode.com/svn/trunk/rgbcolor.js"></script>
<script type="text/javascript"
	src="http://canvg.googlecode.com/svn/trunk/canvg.js"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jsPDF-master/jspdf.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jsPDF-master/libs/Deflate/adler32cs.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jsPDF-master/libs/FileSaver.js/FileSaver.js"' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jsPDF-master/libs/Blob.js/BlobBuilder.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jsPDF-master/jspdf.plugin.addimage.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jsPDF-master/jspdf.plugin.standard_fonts_metrics.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jsPDF-master/jspdf.plugin.split_text_to_size.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jsPDF-master/jspdf.plugin.from_html.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jsPDF/libs/sprintf.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jsPDF/tableExport.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jsPDF/jquery.base64.js' />"></script>

<script type="text/javascript"
	src="<c:url value='/resources/js/jsPDF/html2canvas.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jsPDF/libs/sprintf.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jsPDF/libs/base64.js' />"></script>

<script type="text/javascript"
	src="<c:url value='/resources/jquery-validate.js'/>"></script>

<script src="resources/js/plugins/charts/highcharts.js"></script>
<script src="resources/js/plugins/charts/exporting.js"></script>

<script src="./resources/bootbox/bootbox.min.js"></script>





<%-- <c:url value="/tariff/readtariffname" var="readUrl" />
<c:url value="/tariff/readratename" var="productsUrl" />

<c:url value="/electricityBills/billRead" var="electricityBillReadUrl" />
<c:url value="/electricityBills/billCreate"
	var="electricityBillCreateUrl" />

<c:url value="/electricityBills/billLineItemRead"
	var="elBillLineItemReadUrl" />
<c:url value="/electricityBills/billLineItemCreate"
	var="elSubLedgerCreateUrl" />

<c:url value="/electricityBills/billParameterRead"
	var="elBillParameterReadUrl" />
<c:url value="/electricityBills/billParameterCreate"
	var="elBillParameterCreateUrl" />

<c:url value="/common/getAllChecks" var="allChecksUrl" />
<c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />

<!-- Filters Data url's -->

<c:url value="/bills/filter" var="commonFilterForBillUrl" />
<c:url value="/billLineItem/filter" var="commonFilterForBillLineItemUrl" />
<c:url value="//billParameter/filter"
	var="commonFilterForBillParameterUrl" />
<c:url value="/bills/commonFilterForAccountNumbersUrl"
	var="commonFilterForAccountNumbersUrl" />
<c:url value="/openTickets/getPersonListForFileter" var="personNamesFilterUrl" />
<c:url value="/bills/getPersonListForFileter" var="personNamesFilterUrl" />

<c:url
	value="/commonServiceTransactionMaster/getToTransactionMasterComboBoxUrl"
	var="toTransactionMasterComboBoxUrl" />
<c:url
	value="/commonServiceTransactionMaster/getToBillParaMeterMasteMasterComboBoxUrl"
	var="toBillParaMeterMasterComboBoxUrl" />
<c:url value="/bills/readTowerNames" var="towerNames" />
<c:url value="/openNewTickets/readTowerNames" var="camtowerNames" />
<c:url value="/bill/accountNumberAutocomplete"
	var="accountNumberAutocomplete" />
<c:url value="/electricityBills/readServiceTypes" var="serviceTypeList" />
<c:url value="/electricityBills/readServiceTypesForBackBill"
	var="serviceTypesForBackBillList" />
<c:url value="/openNewTickets/readPropertyNumbers" var="propertyNum" />
<c:url value="/camConsolidation/readAccountNumbers"
	var="readAccountNumbers" /> --%>

<div id="dvLoadingbody" class="loadingimg" hidden="true"></div>



<kendo:grid name="billGrid" change="onChangeBillsList" resizable="true"
	pageable="true" selectable="true" detailTemplate="panelTemplate"
	edit="electricityBillEvent" sortable="true" scrollable="true"
	groupable="true" dataBound="billGenerationDataBound">
	<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10"></kendo:grid-pageable>
	<kendo:grid-filterable extra="false">
		<kendo:grid-filterable-operators>
			<kendo:grid-filterable-operators-string eq="Is equal to" />
			<kendo:grid-filterable-operators-date gt="Is after" lt="Is before" />
		</kendo:grid-filterable-operators>

	</kendo:grid-filterable>
	<kendo:grid-editable mode="popup"
		confirmation="Are you sure you want to remove this Bill Detail?" />
	<kendo:grid-toolbar>
		
		<kendo:grid-toolbarItem name="generateBill" text="Generate Bill" />
		<kendo:grid-toolbarItem
			template="<input id='monthpicker' style='width:162px' />" />
		<%-- <kendo:grid-toolbarItem
			template="<a class='k-button' href='\\#' onclick=searchByMonth() title='Search'>Search</a>" /> --%>
		<kendo:grid-toolbarItem text="Clear_Filter" />
	</kendo:grid-toolbar>

	<kendo:grid-columns>
		<kendo:grid-column title="Electricty Bill ID" field="elBillId"
			width="100px" hidden="true" />

		<kendo:grid-column title="&nbsp;" width="80px">
			<kendo:grid-column-command>
				<kendo:grid-column-commandItem name="View Bill" click="exportBill" />
			</kendo:grid-column-command>
		</kendo:grid-column>

		<kendo:grid-column title="Account&nbsp;No" field="accountNumber"
			filterable="true" width="85px">
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function ledgerTypeFilter(element) {
							element
									.kendoAutoComplete({
										placeholder : "Enter Account Number",
										dataSource : {
											transport : {
												read : "${commonFilterForAccountNumbersUrl}"
											}
										}
									});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>

		<kendo:grid-column title="Account&nbsp;No" field="accountId"
			editor="accountNumberEditor" filterable="true" width="100px"
			hidden="true" />

		<kendo:grid-column title="Person&nbsp;Name" field="personName"
			width="115px" filterable="true">
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function personNameFilter(element) {
							element
									.kendoAutoComplete({
										autoBind : false,
										dataTextField : "personName",
										dataValueField : "personName",
										placeholder : "Enter name",
										headerTemplate : '<div class="dropdown-header">'
												+ '<span class="k-widget k-header">Photo</span>'
												+ '<span class="k-widget k-header">Contact info</span>'
												+ '</div>',
										template : '<table><tr><td rowspan=2><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.personId#'/>" width=40 height=55 alt=\"No Image to Display\" /></span></td>'
												+ '<td align="left"><span class="k-state-default"><b>#: data.personName #</b></span><br>'
												+ '<span class="k-state-default"><i>#: data.personStyle #</i></span><br>'
												+ '<span class="k-state-default"><i>#: data.personType #</i></span></td></tr></table>',
										dataSource : {
											transport : {
												read : "${personNamesFilterUrl}"
											}
										}
									});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>

		<kendo:grid-column title="Property&nbsp;No" field="propertyNumber"
			filterable="true" width="90px" />

		<kendo:grid-column title="Bill&nbsp;Amount" field="billAmount"
			filterable="true" width="90px" hidden="true" />

		<kendo:grid-column title="Arrears&nbsp;Amount" field="arrearsAmount"
			filterable="true" width="110px" hidden="true" />

		<kendo:grid-column title="Average&nbsp;Amount" field="avgAmount"
			filterable="true" width="110px" hidden="true" />


		<kendo:grid-column title="Cleared&nbsp;Amount"
			field="advanceClearedAmount" filterable="true" width="110px"
			hidden="true" />

		<kendo:grid-column title="Net&nbsp;Amount" field="netAmount"
			format="{0:#.00}" width="90px" filterable="true" />

		<kendo:grid-column title="Service&nbsp;Type" field="typeOfService"
			filterable="true" width="95px" editor="serviceTypeEditor">
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function postTypeFilter(element) {
							element
									.kendoAutoComplete({
										placeholder : "Enter Bill Number",
										dataSource : {
											transport : {
												read : "${commonFilterForBillUrl}/typeOfService"
											}
										}
									});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>

		<kendo:grid-column title="Bill&nbsp;No" field="billNo" width="70px"
			filterable="true" hidden="true">
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function postTypeFilter(element) {
							element
									.kendoAutoComplete({
										placeholder : "Enter Bill Number",
										dataSource : {
											transport : {
												read : "${commonFilterForBillUrl}/billNo"
											}
										}
									});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>

		<kendo:grid-column title="Bill&nbsp;Type" field="billType"
			width="70px" hidden="true">
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function postTypeFilter(element) {
							element
									.kendoAutoComplete({
										placeholder : "Enter Bill Number",
										dataSource : {
											transport : {
												read : "${commonFilterForBillUrl}/billType"
											}
										}
									});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>

		<kendo:grid-column title="Average&nbsp;Count" field="avgCount"
			width="105px" hidden="true" />

		<kendo:grid-column title="Last&nbsp;Bill&nbsp;Date" field="fromDate"
			format="{0:dd/MM/yyyy}" width="100px" hidden="true" />

		<kendo:grid-column title="From&nbsp;Date" field="fromDateBackBill"
			format="{0:dd/MM/yyyy}" width="100px" hidden="true" />

		<kendo:grid-column title="Bill&nbsp;Date" field="billDate"
			format="{0:dd/MM/yyyy}" width="100px" hidden="true"></kendo:grid-column>

		<kendo:grid-column title="To&nbsp;Date" field="toDateBackBill"
			format="{0:dd/MM/yyyy}" width="100px" hidden="true" />

		<kendo:grid-column title="Units" field="unitsBackBill" hidden="true"
			width="105px" />

		<kendo:grid-column title="Bill&nbsp;Issue&nbsp;Date"
			field="elBillDate" format="{0:dd/MM/yyyy hh:mm tt}" width="100px" />

		<kendo:grid-column title="Bill&nbsp;Month" field="billMonth"
			format="{0:MMM,yyyy}" width="80px">
		</kendo:grid-column>

		<kendo:grid-column title="Bill&nbsp;Due&nbsp;Date" field="billDueDate"
			format="{0:dd/MM/yyyy}" width="95px" />

		<kendo:grid-column title="Post&nbsp;Type" field="postType"
			width="80px" filterable="true" editor="dropDownChecksEditor"
			hidden="true">
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function postTypeFilter(element) {
							element
									.kendoAutoComplete({
										placeholder : "Enter Post Type",
										dataSource : {
											transport : {
												read : "${commonFilterForBillUrl}/postType"
											}
										}
									});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>

		<kendo:grid-column title="Bill Status" field="status" width="80px">
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function ledgerStatusFilter(element) {
							element
									.kendoDropDownList({
										optionLabel : "Select status",
										dataSource : {
											transport : {
												read : "${filterDropDownUrl}/billStatus"
											}
										}
									});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>

		<kendo:grid-column title="Tally Status" field="tallyStatus"
			width="85px">
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function ledgerStatusFilter(element) {
							element
									.kendoDropDownList({
										optionLabel : "Select status",
										dataSource : {
											transport : {
												read : "${commonFilterForBillUrl}/tallyStatus"
											}
										}
									});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>

		<kendo:grid-column title=""
			template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Active'>#= data.status == 'Generated' ? 'Approve' : data.status == 'Approved' ? 'Post' : 'Posted' #</a>"
			width="90px">
		</kendo:grid-column>

		<kendo:grid-column title="&nbsp;" width="100px">
			<kendo:grid-column-command>
				<kendo:grid-column-commandItem name="Cancel"
					click="cancelApproveBill" />
				<kendo:grid-column-commandItem name="Correct Bill"
					click="correctBill" />
			</kendo:grid-column-command>
		</kendo:grid-column>
		<kendo:grid-column title="&nbsp;" width="90px">
			<kendo:grid-column-command>
				<kendo:grid-column-commandItem name="Post To Tally"
					click="postToTally" />
			</kendo:grid-column-command>
		</kendo:grid-column>


	</kendo:grid-columns>

	<kendo:dataSource pageSize="20" requestEnd="onRequestEnd">
		<kendo:dataSource-transport>
			<kendo:dataSource-transport-create url="${electricityBillCreateUrl}"
				dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-read url="${electricityBillReadUrl}"
				dataType="json" type="POST" contentType="application/json" />
		</kendo:dataSource-transport>

		<kendo:dataSource-schema parse="parse">
			<kendo:dataSource-schema-model id="elBillId">
				<kendo:dataSource-schema-model-fields>

					<kendo:dataSource-schema-model-field name="elBillId" type="number" />

					<kendo:dataSource-schema-model-field name="typeOfService"
						type="string" />

					<kendo:dataSource-schema-model-field name="personName"
						type="string" />

					<kendo:dataSource-schema-model-field name="accountNumber"
						type="string" />

					<kendo:dataSource-schema-model-field name="accountId" type="number"
						defaultValue="" />

					<kendo:dataSource-schema-model-field name="propertyNumber"
						type="string" />

					<kendo:dataSource-schema-model-field name="billAmount"
						type="number" />

					<kendo:dataSource-schema-model-field name="arrearsAmount"
						type="number" />

					<kendo:dataSource-schema-model-field name="advanceClearedAmount"
						type="number" />

					<kendo:dataSource-schema-model-field name="unitsBackBill"
						type="number" defaultValue="">
						<kendo:dataSource-schema-model-field-validation min="0" />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="postType" type="string" />

					<kendo:dataSource-schema-model-field name="elBillDate" type="date" />

					<kendo:dataSource-schema-model-field name="fromDate" type="date" />

					<kendo:dataSource-schema-model-field name="billDate" type="date" />

					<kendo:dataSource-schema-model-field name="billDueDate" type="date" />

					<kendo:dataSource-schema-model-field name="fromDateBackBill"
						type="date">
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="toDateBackBill"
						type="date">
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="billMonth" type="date" />

					<kendo:dataSource-schema-model-field name="netAmount" type="number" />

					<kendo:dataSource-schema-model-field name="avgAmount" type="number" />

					<kendo:dataSource-schema-model-field name="avgCount" type="number" />

					<kendo:dataSource-schema-model-field name="cbId" type="number" />

					<kendo:dataSource-schema-model-field name="billNo" type="string" />

					<kendo:dataSource-schema-model-field name="billType" type="string" />

					<kendo:dataSource-schema-model-field name="createdBy" />

					<kendo:dataSource-schema-model-field name="status" editable="false"
						type="string" />

					<kendo:dataSource-schema-model-field name="tallyStatus"
						editable="false" type="string" />

				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>

	</kendo:dataSource>
</kendo:grid>

<kendo:grid-detailTemplate id="panelTemplate">
	<kendo:splitter name="vertical_#=elBillId#" orientation="vertical">
		<kendo:splitter-panes>
			<kendo:splitter-pane id="top-pane" collapsible="false">
				<kendo:splitter-pane-content>
					<kendo:splitter name="horizontal_#=elBillId#"
						style="height: 'auto'; width: 'auto';">
						<kendo:splitter-panes>
							<kendo:splitter-pane id="left-pane" collapsible="true"
								size="400px">
								<kendo:splitter-pane-content>
									<div class="pane-content">
										<kendo:tabStrip name="tabStrip_#=elBillId#">
											<kendo:tabStrip-items>

												<kendo:tabStrip-item selected="true" text="Bill Line Item">
													<kendo:tabStrip-item-content>
														<div class='wethear'>
															<br />
															<kendo:grid name="elRateSlab_#=elBillId#" pageable="true"
																dataBound="billLineItemsDataBound" resizable="true"
																sortable="true" reorderable="true" selectable="true"
																scrollable="true" edit="billLineItems">
																<kendo:grid-pageable pageSize="5"></kendo:grid-pageable>
																<kendo:grid-editable mode="popup"
																	confirmation="Are sure you want to delete this item ?" />
																<kendo:grid-toolbar>
																	<kendo:grid-toolbarItem name="create"
																		text="Add New Bill Line Item" />
																</kendo:grid-toolbar>
																<kendo:grid-columns>

																	<kendo:grid-column title="BillLineItemId"
																		field="elBillLineId" hidden="true" width="70px">
																	</kendo:grid-column>
																	<kendo:grid-column title="Transaction&nbsp;Name"
																		field="transactionName" editor="transactionCodeEditer"
																		width="100px"
																		footerTemplate="<span style='margin-left: 120px;' id='totlcst'><b>Total (Rs) : </b></span>">
																		<kendo:grid-column-filterable>
																			<kendo:grid-column-filterable-ui>
																				<script>
																					function postTypeFilter(
																							element) {
																						element
																								.kendoAutoComplete({
																									placeholder : "Enter Post Type",
																									dataSource : {
																										transport : {
																											read : "${commonFilterForBillLineItemUrl}/transactionName"
																										}
																									}
																								});
																					}
																				</script>
																			</kendo:grid-column-filterable-ui>
																		</kendo:grid-column-filterable>
																	</kendo:grid-column>

																	<kendo:grid-column title="Transaction&nbsp;Code"
																		field="transactionCode" width="110px"
																		filterable="true" hidden="true">
																	</kendo:grid-column>
																	<kendo:grid-column title="Amount" field="balanceAmount"
																		width="50px" filterable="true"
																		footerTemplate="<span id='totlcst'><span id='sumTotal_#=elBillId#'></span></span>">
																	</kendo:grid-column>
																</kendo:grid-columns>

																<kendo:dataSource requestEnd="billLineItemsOnRequestEnd">
																	<kendo:dataSource-transport>
																		<kendo:dataSource-transport-read
																			url="${elBillLineItemReadUrl}/#=elBillId#"
																			dataType="json" type="POST"
																			contentType="application/json" />
																		<kendo:dataSource-transport-create
																			url="${elSubLedgerCreateUrl}/#=elBillId#"
																			dataType="json" type="GET"
																			contentType="application/json" />
																	</kendo:dataSource-transport>

																	<kendo:dataSource-schema>
																		<kendo:dataSource-schema-model id="elBillLineId">
																			<kendo:dataSource-schema-model-fields>

																				<kendo:dataSource-schema-model-field
																					name="elBillLineId" type="number" />

																				<kendo:dataSource-schema-model-field
																					name="transactionName" type="string"
																					defaultValue="" />

																				<kendo:dataSource-schema-model-field
																					name="transactionCode" type="string"></kendo:dataSource-schema-model-field>

																				<kendo:dataSource-schema-model-field
																					name="transactionSubCode" type="string" />

																				<kendo:dataSource-schema-model-field
																					name="creditAmount" type="number" />

																				<kendo:dataSource-schema-model-field
																					name="debitAmount" type="number" />

																				<kendo:dataSource-schema-model-field
																					name="balanceAmount" type="number" />

																				<kendo:dataSource-schema-model-field
																					name="createdBy" />

																				<kendo:dataSource-schema-model-field name="status"
																					editable="false" type="string" />

																			</kendo:dataSource-schema-model-fields>
																		</kendo:dataSource-schema-model>
																	</kendo:dataSource-schema>
																</kendo:dataSource>
															</kendo:grid>
														</div>
													</kendo:tabStrip-item-content>
												</kendo:tabStrip-item>

												<kendo:tabStrip-item text="Bill Information">
													<kendo:tabStrip-item-content>
														<div class='bill-details' style='width: auto;'>
															<table>
																<tr>
																	<td>
																		<dl>
																			<table>
																				<tr>
																					<td>Bill Number</td>
																					<td>#= billNo#</td>
																				</tr>

																				<tr>
																					<td>Bill Amount</td>
																					<td>#= billAmount#</td>
																				</tr>

																				<tr>
																					<td>Arrears Amount</td>
																					<td>#= arrearsAmount#</td>
																				</tr>
																				<tr>
																					<td>Average Amount</td>
																					<td>#= avgAmount#</td>
																				</tr>
																				<tr>
																					<td>Cleared Amount</td>
																					<td>#= advanceClearedAmount#</td>
																				</tr>
																				<tr>
																					<td>Average Count</td>
																					<td>#= avgCount#</td>
																				</tr>
																				<tr>
																					<td>Bill Type</td>
																					<td>#= billType#</td>
																				</tr>
																				<tr>
																					<td>Last&nbsp;Bill&nbsp;Date</td>
																					<td>#= kendo.toString(fromDate,'MM/dd/yyyy') #</td>
																				</tr>
																				<tr>
																					<td>Bill&nbsp;Date</td>
																					<td>#= kendo.toString(billDate,'MM/dd/yyyy') #</td>
																				</tr>
																				<!-- <tr>
													<td>Bill&nbsp;Issue&nbsp;Date</td>
													<td>#= kendo.toString(elBillDate,'MM/dd/yyyy hh:mm tt') # </td>
												</tr> -->
																				<tr>
																					<td>Post&nbsp;Type</td>
																					<td>#= postType#</td>
																				</tr>
																			</table>
																		</dl>
																	</td>
																</tr>
															</table>
														</div>
													</kendo:tabStrip-item-content>
												</kendo:tabStrip-item>

											</kendo:tabStrip-items>
										</kendo:tabStrip>
									</div>
								</kendo:splitter-pane-content>
							</kendo:splitter-pane>
							<kendo:splitter-pane id="center-pane" collapsible="true"
								size="500px">
								<kendo:splitter-pane-content>
									<div class="pane-content">
										<kendo:tabStrip name="tabStrip1_#=elBillId#">
											<kendo:tabStrip-items>
												<kendo:tabStrip-item selected="true" text="Bill Parameters">
													<kendo:tabStrip-item-content>
														<div class='wethear' style='width: 700px;'>
															<br />
															<kendo:grid name="elRateSlab1_#=elBillId#"
																pageable="true" resizable="true" sortable="true"
																reorderable="true" selectable="true" scrollable="true"
																edit="billParaMeter">
																<kendo:grid-pageable pageSize="5"></kendo:grid-pageable>

																<kendo:grid-editable mode="popup"
																	confirmation="Are sure you want to delete this item ?" />
																<kendo:grid-columns>
																	<kendo:grid-column title="BillParameterId"
																		field="elBillParameterId" hidden="true" width="70px" />

																	<kendo:grid-column title="Parameter&nbsp;Name"
																		field="bvmName" width="80px" filterable="true">
																		<kendo:grid-column-filterable>
																			<kendo:grid-column-filterable-ui>
																				<script>
																					function postTypeFilter(
																							element) {
																						element
																								.kendoAutoComplete({
																									placeholder : "Enter Post Type",
																									dataSource : {
																										transport : {
																											read : "${commonFilterForBillParameterUrl}/billParameterMasterEntity.bvmName"
																										}
																									}
																								});
																					}
																				</script>
																			</kendo:grid-column-filterable-ui>
																		</kendo:grid-column-filterable>
																	</kendo:grid-column>

																	<kendo:grid-column title="Parameter&nbsp;Name"
																		field="bvmId" editor="billParameterNameEditer"
																		hidden="true" width="50px" filterable="true">
																		<kendo:grid-column-filterable>
																			<kendo:grid-column-filterable-ui>
																				<script>
																					function postTypeFilter(
																							element) {
																						element
																								.kendoAutoComplete({
																									placeholder : "Enter Post Type",
																									dataSource : {
																										transport : {
																											read : "${commonFilterForBillParameterUrl}/billParameterMasterEntity.bvmName"
																										}
																									}
																								});
																					}
																				</script>
																			</kendo:grid-column-filterable-ui>
																		</kendo:grid-column-filterable>
																	</kendo:grid-column>

																	<kendo:grid-column title="Parameter&nbsp;Value"
																		field="elBillParameterValue" width="70px"
																		filterable="true">
																		<kendo:grid-column-filterable>
																			<kendo:grid-column-filterable-ui>
																				<script>
																					function postTypeFilter(
																							element) {
																						element
																								.kendoAutoComplete({
																									placeholder : "Enter Post Type",
																									dataSource : {
																										transport : {
																											read : "${commonFilterForBillParameterUrl}/elBillParameterValue"
																										}
																									}
																								});
																					}
																				</script>
																			</kendo:grid-column-filterable-ui>
																		</kendo:grid-column-filterable>
																	</kendo:grid-column>

																	<kendo:grid-column title="Notes" field="notes"
																		width="100px" filterable="true">
																		<kendo:grid-column-filterable>
																			<kendo:grid-column-filterable-ui>
																				<script>
																					function postTypeFilter(
																							element) {
																						element
																								.kendoAutoComplete({
																									placeholder : "Enter Post Type",
																									dataSource : {
																										transport : {
																											read : "${commonFilterForBillParameterUrl}/notes"
																										}
																									}
																								});
																					}
																				</script>
																			</kendo:grid-column-filterable-ui>
																		</kendo:grid-column-filterable>
																	</kendo:grid-column>
																</kendo:grid-columns>

																<kendo:dataSource
																	requestEnd="billParametersOnRequestEnd">
																	<kendo:dataSource-transport>
																		<kendo:dataSource-transport-read
																			url="${elBillParameterReadUrl}/#=elBillId#"
																			dataType="json" type="POST"
																			contentType="application/json" />
																		<kendo:dataSource-transport-create
																			url="${elBillParameterCreateUrl}/#=elBillId#"
																			dataType="json" type="GET"
																			contentType="application/json" />
																	</kendo:dataSource-transport>

																	<kendo:dataSource-schema>
																		<kendo:dataSource-schema-model id="elBillParameterId">
																			<kendo:dataSource-schema-model-fields>

																				<kendo:dataSource-schema-model-field
																					name="elBillParameterId" type="number" />

																				<kendo:dataSource-schema-model-field name="elBillId"
																					type="number" />

																				<kendo:dataSource-schema-model-field name="bvmId"
																					type="String" />
																				<kendo:dataSource-schema-model-field
																					name="elBillParameterValue" type="string" />

																				<kendo:dataSource-schema-model-field name="notes"
																					type="string" />

																				<kendo:dataSource-schema-model-field
																					name="createdBy" />

																				<kendo:dataSource-schema-model-field name="status"
																					editable="false" type="string" />

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

									</div>
								</kendo:splitter-pane-content>
							</kendo:splitter-pane>

							<%-- 						<kendo:splitter-pane id="last-pane" collapsible="true" size="500px">
						<kendo:splitter-pane-content>
						<div class='bill-details' style='width: auto;'>
							<table>
								<tr><td>
									<h4>Bill Information</h4> 
										<dl>
											<table>
											<tr >
													<td>Bill Number</td>
													<td>#= billNo#</td>
												</tr>
												
											   <tr >
													<td>Bill Amount</td>
													<td>#= billAmount#</td>
												</tr>
												
												<tr>
													<td>Arrears Amount</td>
													<td>#= arrearsAmount#</td>
												</tr>
												<tr>
													<td>Average Amount</td>
													<td>#= avgAmount#</td>
												</tr>
												<tr>
													<td>Cleared Amount</td>
													<td>#= advanceClearedAmount#</td>
												</tr>
												<tr>
													<td>Average Count</td>
													<td>#= avgCount#</td>
												</tr>
												<tr>
													<td>Bill Type</td>
													<td>#= billType#</td>
												</tr>
												<tr>
													<td>Last&nbsp;Bill&nbsp;Date</td>
													<td>#= kendo.toString(fromDate,'MM/dd/yyyy') #</td>
												</tr>
												<tr>
													<td>Bill&nbsp;Date</td>
													<td>#= kendo.toString(billDate,'MM/dd/yyyy') #</td>
												</tr>
												<tr>
													<td>Bill&nbsp;Issue&nbsp;Date</td>
													<td>#= kendo.toString(elBillDate,'MM/dd/yyyy') # </td>
												</tr>
												<tr>
													<td>Post&nbsp;Type</td>
													<td>#= postType#</td>
												</tr>
											</table>									
										</dl> 
									</td>
								</tr>
							</table>
						</div>
						</kendo:splitter-pane-content>
					</kendo:splitter-pane> --%>

						</kendo:splitter-panes>
					</kendo:splitter>
				</kendo:splitter-pane-content>
			</kendo:splitter-pane>
		</kendo:splitter-panes>
	</kendo:splitter>



</kendo:grid-detailTemplate>
<style scoped>
#vertical {
	height: 150px;
	width: 100%;
	margin: 0 auto;
	float: left;
}

#middle-pane {
	background-color: rgba(60, 70, 80, 0.10);
}

#bottom-pane {
	background-color: rgba(60, 70, 80, 0.15);
}

#left-pane,#center-pane,#right-pane {
	background-color: rgba(60, 70, 80, 0.05);
}

.pane-content {
	padding: 0 10px;
}
get
</style>


<div id="alertsBox" title="Alert"></div>
<div id="generateBillDialog" style="display: none;">
	<form id="addform" data-role="validator" novalidate="novalidate">
		<table>
			<tr>
				<td>
					<table id="previousDiv" style="height: 250px;">
						<!--current Table  -->
						<tr>
						<tr>
							<td>Property No</td>
							<td><input id="propertyNo" name="propertyNo"
								onchange="onselectedProp()" />
						</tr>

						<tr>
							<td>Account Name*</td>
							<td><input id="accountNo" name="accountNo"
								onchange="gettariffId()" required="required"
								validationMessage="Select Account No." />
						</tr>

						<tr>
							<td>Services*</td>
							<td><kendo:dropDownList name="rateName" id="rateName"
									cascadeFrom="accountNo"
									onchange="getstatus();getTod();getPreviousStatusAlert()"
									required="required" validationMessage="Select Service Name"></kendo:dropDownList>
							</td>
						</tr>
						<tr>
						<tr id="sWasteDate" style="display: none;">
							<td>Previous Bill Date</td>
							<td><kendo:datePicker format="dd/MM/yyyy  "
									name="previousSWasteDate" id="previousSWasteDate"
									parse="parse()" readonly="readonly">
								</kendo:datePicker></td>
						</tr>
						<tr>
						<tr>
							<td>Present Bill Date*</td>
							<td><kendo:datePicker format="dd/MM/yyyy  "
									name="presentbilldate" id="presentbilldate" required="required"
									class="validate[required]" change="startChange"
									validationMessage="Date is Required">
								</kendo:datePicker>
							<td></td>
						</tr>

						<tr id="pstatus">
							<td id="pstatusTd">Present Status*</td>
							<td id="pstatusTd1"><input id="presentStaus"
								id="presentStaus" value="1" onchange="getMeterStatus()"
								required="required"></td>

						</tr>
						<tr>
							<td>Post Type*</td>
							<td><input id="postType" id="postType" value="1"
								required="required" validationMessage="Select Status."></td>

						</tr>

						<tr id="presenthide">
							<td><span class="replaceme">Present Reading* </span></td>
							<td><input id="presentReading" name="presentReading"
								placeholder="Enter Numbers Only" class="k-textbox"
								required="required" onchange="getUnits()"
								validationMessage="Reading is Required" /></td>
						</tr>
						<tr id="pftr" style="display: none;">
							<td>PF Reading*</td>
							<td><input id="pfReading" name="pfReading"
								placeholder="Enter Numbers Only" class="k-textbox" /></td>
						</tr>
						<tr id="conntr" style="display: none;">
							<td>Recorded Demand*</td>
							<td><input id="connectedLoad" name="connectedLoad"
								placeholder="Enter Numbers Only" class="k-textbox" /></td>
						</tr>
						<tr id="dgtr" style="display: none;">
							<td>Present DG Reading*</td>
							<td><input id="dgReading" name="dgReading"
								placeholder="Enter Numbers Only" class="k-textbox"
								onchange="getdgUnit()" /></td>
						</tr>
						<tr>
							<td>
						</tr>

					</table>

				</td>

				<td>
					<table id="presentdiv" style="height: 250px;">
						<!--Previous Table  -->

						<tr>
						<tr>
							<td>Previous Bill Date</td>
							<td><kendo:datePicker format="dd/MM/yyyy  "
									name="previousbillDate" id="previousbillDate"
									readonly="readonly">
								</kendo:datePicker></td>
						</tr>
						<tr>
						<tr>
							<td>Previous Status</td>
							<td><input id="previousStaus" name="previousStaus"
								placeholder="Enter Previous Status" class="k-textbox"
								readonly="readonly" /></td>
						</tr>
						<tr>
							<td>Previous Reading</td>
							<td><kendo:numericTextBox name="previousReading"
									id="previousReading" style="aria-readonly=true;"
									readonly="readonly"></kendo:numericTextBox></td>
						</tr>

						<tr>
							<td>Units</td>
							<td><kendo:numericTextBox name="units" id="units"
									style="aria-readonly=true;" readonly="readonly"></kendo:numericTextBox>
							</td>
						</tr>
						<tr>
							<td>Average Unit</td>
							<td><kendo:numericTextBox name="avgunits" id="avgunits"
									style="aria-readonly=true;" readonly="readonly"></kendo:numericTextBox>
							</td>
						</tr>
						<tr>
							<td>Meter constant</td>
							<td><kendo:numericTextBox name="meterFactor"
									id="meterFactor" style="aria-readonly=true" readonly="readonly"></kendo:numericTextBox>
							</td>
						</tr>
					</table>
				</td>
			</tr>

			<tr>

				<td>
					<table id="todDiv" style="display: none; height: 130px;">

						<tr>
							<td>Unit Consumed<br> b/w 6 A.M to 6 P.M*
							</td>
							<td><input id="presentTod1" name="presentTod1"
								placeholder="Enter Numbers Only" class="k-textbox" /></td>

						</tr>
						<tr>
							<td>Unit Consumed<br> b/w 6 P.M to 10 P.M*
							</td>
							<td><input id="presentTod2" name="presentTod2"
								placeholder="Enter Numbers Only" class="k-textbox" /></td>

						</tr>
						<tr>
							<td>Unit Consumed<br> b/w 10 P.M to 6 A.M*
							</td>
							<td><input id="presentTod3" name="presentTod3"
								placeholder="Enter Numbers Only" class="k-textbox"
								onchange="getTodUnits()" /></td>
						</tr>
						<tr>
							<td><input type="hidden" id="todcheck" name="todcheck"
								value="0" /></td>
						</tr>
						<tr>
							<td><input type="hidden" id="statuscheck" name="statuscheck"
								value="Not Generated" /></td>
						</tr>
					</table>
				</td>
				<td>
					<table id="previousDgReading" style="display: none; height: 130px;">
						<!--DG Table  -->

						<tr>
							<td>Previous DG <br>Reading
							</td>
							<td><kendo:numericTextBox name="previousDGReading"
									id="previousDGReading" style="aria-readonly=true;"
									readonly="readonly"></kendo:numericTextBox></td>
						</tr>
						<tr>
							<td>DG Units</td>
							<td><kendo:numericTextBox name="dgUnit" id="dgUnit"
									style="aria-readonly=true;" readonly="readonly"></kendo:numericTextBox>
							</td>
						</tr>

						<tr>
							<td>DG Meter constant</td>
							<td><kendo:numericTextBox name="dgmeterFactor"
									id="dgmeterFactor" style="aria-readonly=true"
									readonly="readonly"></kendo:numericTextBox></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>

				<td colspan="2">
			<tr>
				<td class="left" align="right">
					<button type="submit" id="calcu" class="k-button"
						style="padding-left: 10px">Generate Bill</button> <span
					id=commitplaceholder style="display: none;"><img
						src="./resources/images/loadingimg.GIF"
						style="vertical-align: middle; margin-left: 50px" /> &nbsp;&nbsp;
						<img src="./resources/images/loading.GIF" alt="loading"
						style="vertical-align: middle margin-left: 50px" height="20px"
						width="200px; " /></span>
				</td>
			</tr>

		</table>

		<!--END OF Main Table  -->
	</form>

</div>

<div id="generateBTBillDialog" style="display: none;">
	<form id="uploadForm">
		<table>
			<tr>
				<td>Upload Excel</td>
				<td><kendo:upload name="files" id="files"></kendo:upload></td>
		</table>
	</form>
</div>


<div id="uploadBatchFileDialog" style="display: none;">
	<form id="uploadBatchFileForm">
		<table>
			<tr>
				<td>Upload Batch File</td>
				<td><kendo:upload name="files" id="batchFile"></kendo:upload></td>
		</table>
	</form>
</div>


<div id="uploadXMLFileDialog" style="display: none;">
	<form id="uploadXMLFileForm">
		<table>
			<tr>
				<td>Upload XML File</td>
				<td><kendo:upload name="files" id="XMLFile"></kendo:upload></td>
		</table>
	</form>
</div>

<div id="bulkBillingDiv" style="display: none;">
	<form id="bulkBillingForm">
		<table style="height: 270px;">
			<tr>
				<td>Block Name</td>
				<td><input id="blockName" name="blockName" required="required"
					onchange="onselectedProperty()"
					validationMessage="Select Block Name" /> <!-- onchange="getPropertyNo()" --></td>
			</tr>
			<tr id="propmultiselect">
				<td id="propmultiselect1">Property Number</td>
				<td id="propmultiselect2"><kendo:multiSelect id="propertyName"
						name="propertyName"></kendo:multiSelect></td>
			</tr>
			<tr>
				<td>Service Type</td>
				<td><input id="serviceTypeList" name="serviceTypeList"
					required="required" validationMessage="Select Block Name"
					onchange="chanegeUomType()" /></td>
			</tr>
			<tr>
				<td>Present Bill Date</td>
				<td><kendo:datePicker format="dd/MM/yyyy  " name="presentdate"
						id="presentdate" required="required" class="validate[required]"
						validationMessage="Date is Required">
					</kendo:datePicker>
				<td></td>
			</tr>

			<tr>
				<td>Bill Calculation Type</td>
				<td><input id="billCalcType" name="billCalcType"
					required="required" validationMessage="Select Block Name"
					onchange="chanegeBulkReding()" /></td>
			</tr>
			<tr>
				<td style="padding-right: 10px;"><span class="bulkReading">Unit
				</span></td>
				<td><input type="text" id="consumptionUnit"
					name="consumptionUnit" placeholder="Enter Numbers Only"
					class="k-textbox" required="required"
					validationMessage="Reading is Required" /></td>
			</tr>
			<tr>
				<td class="left" align="center" colspan="4">

					<button type="submit" id="bulkCalculate" class="k-button"
						style="padding-left: 10px" onclick="generateBulkBill()">Generate
						Bill</button>
				</td>
			</tr>

		</table>
	</form>
</div>

<div id="amrBillingDiv" style="display: none;">
	<form id="amrBillingForm" data-role="validator" novalidate="novalidate">
		<table style="height: 190px;">
			<tr>
				<td>Block Name</td>
				<td><input id="blockNameAmr" name="blockNameAmr"
					required="required" validationMessage="Select Block Name"
					onchange="getPropertyNoAmr()" /> <!-- onchange="getPropertyNo()" --></td>
			</tr>
			<tr id="protypemultiselectAmr">
				<td>Property Type</td>
				<td><input id="propertyType" name="propertyType"
					required="required" onchange="selectallprop()"
					validationMessage="Select Type" /> <!-- onchange="getPropertyNo()" --></td>
			</tr>
			<tr id="propmultiselectAmr">
				<td id="propmultiselectAmr1">Property Number</td>
				<td id="propmultiselectAmr2"><kendo:multiSelect
						id="propertyNameAmr" name="propertyNameAmr" required="required"
						validationMessage="Select Property No"></kendo:multiSelect></td>
			</tr>

			<tr>
				<td>Present Bill Date</td>
				<td><kendo:datePicker format="dd/MM/yyyy  "
						name="presentdateAmr" id="presentdateAmr" required="required"
						class="validate[required]" validationMessage="Date is Required">
					</kendo:datePicker>
				<td></td>
			</tr>

			<tr>
				<td class="left" align="center" colspan="4">
					<button type="button" id="verify" class="k-button"
						style="padding-left: 10px">Verify</button>
					<button type="submit" id="amrCalculate" class="k-button"
						style="padding-left: 10px">Generate Bill</button> <span
					id=amrplaceholder style="display: none;"><img
						src="./resources/images/loadingimg.GIF"
						style="vertical-align: middle; margin-left: 50px" /> &nbsp;&nbsp;
						<img src="./resources/images/loading.GIF" alt="loading"
						style="vertical-align: middle margin-left: 50px" height="20px"
						width="200px; " /></span>
				</td>
			</tr>

		</table>
	</form>
</div>

<div id="printBillingDiv" style="display: none;">
	<form id="printBillingForm" data-role="validator"
		novalidate="novalidate">
		<table style="height: 190px;">


			<tr>
				<td>Print Bill</td>
				<td><input id="typePrint" name="typePrint" required="required"
					validationMessage="Select Service Type" onchange="billTOPrint()" />
				</td>
			</tr>
			<tr id="accPrint" style="display: none;">
				<td>Account Name</td>
				<td><input id="accountNoprint" name="accountNoprint"
					validationMessage="Select Account No." />
			</tr>

			<tr>
				<td>Service Type</td>
				<td><input id="serviceTypePrint" name="serviceTypePrint"
					validationMessage="Select Service Type" /> <!-- onchange="getPropertyNo()" --></td>
			</tr>
			<tr id="fMonthPrint" style="display: none;">
				<td>From Month</td>
				<td><kendo:datePicker format="MMM yyyy  "
						name="fromdateprintBill" id="fromdateprintBill" value="${month}"
						start="year" depth="year">
					</kendo:datePicker>
				<td></td>
			</tr>

			<tr>
				<td>Month</td>
				<td><kendo:datePicker format="MMM yyyy  "
						name="presentdateprintBill" id="presentdateprintBill"
						required="required" value="${month}" start="year"
						class="validate[required]" validationMessage="Date is Required"
						depth="year">
					</kendo:datePicker>
				<td></td>
			</tr>


			<tr>
				<td class="left" align="center" colspan="4">
					<button type="submit" id="printBill" class="k-button"
						style="padding-left: 10px">Print Bill</button> <span
					id=printplaceholder style="display: none;"><img
						src="./resources/images/loadingimg.GIF"
						style="vertical-align: middle; margin-left: 50px" /> &nbsp;&nbsp;
						<img src="./resources/images/loading.GIF" alt="loading"
						style="vertical-align: middle margin-left: 50px" height="20px"
						width="200px; " /></span>
				</td>
			</tr>

		</table>
	</form>
</div>


<div id="downloadAllBillsDiv" style="display: none;">
	<form id="downloadAllBillsForm" data-role="validator"
		novalidate="novalidate">
		<table style="height: 180px;">
			<tr>
				<td>Service Type</td>
				<td>
					<!-- <input id="blockNameAmr" name="blockNameAmr" required="required" onchange="getPropertyNoAmr()" validationMessage="Select Block Name" /> -->
					<input id="serviceTypeDownload" name="serviceTypeDownload"
					required="required" validationMessage="Select Service Type" />
				</td>
			</tr>
			<tr>
				<td>Bill Month</td>
				<td><kendo:datePicker format="MMMM yyyy"
						name="presentdateDownload" value="${month}" start="year"
						depth="year" id="presentdateDownload" required="required"
						validationMessage="Date is Required">
					</kendo:datePicker>
				<td></td>
			</tr>
			<tr>
				<td class="left" align="center" colspan="4">
					<button type="submit" id="downlodbutton" class="k-button"
						style="padding-left: 10px">Download Bill</button>
				</td>

				<td class="left" align="center" colspan="4">
					<button type="submit" id="verifyDuplicate" class="k-button"
						style="padding-left: 10px">Verify Duplicate</button>
				</td>

				<!-- <td class="left" align="center" colspan="4">
					<button type="submit" id="savelatePaymentAmount" class="k-button"
						style="padding-left: 10px">Save Late Payment</button>
				</td> -->


			</tr>

		</table>
	</form>
</div>

<div id="ddDiv" style="display: none;">
	<form id="ddForm">
		<table style="height: 205px;">

			<tr>
				<td>Account Name</td>
				<td><input id="accountNodd" name="accountNodd"
					onchange="getddService()" required="required"
					validationMessage="Select Account No." />
			</tr>

			<tr>
				<td>Services</td>
				<td><kendo:dropDownList name="servicesNamedd"
						id="servicesNamedd" cascadeFrom="accountNodd"
						onchange="getpreviousdddate()" required="required"
						validationMessage="Select Service Name"></kendo:dropDownList></td>
			</tr>
			<tr>
			<tr>
				<td>Previous Bill Date</td>
				<td><kendo:datePicker format="dd/MM/yyyy  "
						name="previosdddate" id="previosdddate" parse="parse()"
						readonly="readonly">
					</kendo:datePicker></td>
			</tr>
			<tr>
			<tr>
				<td>Present Bill Date</td>
				<td><kendo:datePicker format="dd/MM/yyyy  "
						name="presentddbilldate" id="presentddbilldate"
						required="required" class="validate[required]"
						validationMessage="Date is Required">
					</kendo:datePicker>
				<td></td>
			</tr>
			<tr>
				<td class="left" align="center" colspan="4">

					<button type="submit" id="raisedeposit" class="k-button"
						style="padding-left: 10px" onclick="raisedepositDemand()">Raise
						Deposit Demand</button>
				</td>
			</tr>

		</table>
	</form>
</div>
<div id="cancelAlldiv" style="display: none;">
	<form id="cancelAllForm" data-role="validator" novalidate="novalidate">
		<table style="height: 190px;">
			<tr>
				<td>Service Type</td>
				<td><input id="serviceTypeCancel" name="serviceTypeCancel"
					validationMessage="Select Service Type" /></td>
			</tr>
			<tr id="fMonthPrint" style="display: none;">
				<td>From Month</td>
				<td><kendo:datePicker format="MMM yyyy  "
						name="fromdateprintBill" id="fromdateprintBill" value="${month}"
						start="year" depth="year">
					</kendo:datePicker>
				<td></td>
			</tr>

			<tr>
				<td>Month</td>
				<td><kendo:datePicker format="MMM yyyy  "
						name="presentdatecancelBill" id="presentdatecancelBill"
						required="required" value="${month}" start="year"
						class="validate[required]" validationMessage="Date is Required"
						depth="year">
					</kendo:datePicker>
				<td></td>
			</tr>


			<tr>
				<td class="left" align="center" colspan="4">
					<button type="submit" id="cancelBill" class="k-button"
						style="padding-left: 10px">Cancel All Bills</button> <!-- <span id=printplaceholder style="display: none;"><img
						src="./resources/images/loadingimg.GIF"
						style="vertical-align: middle; margin-left: 50px" /> &nbsp;&nbsp;
						<img src="./resources/images/loading.GIF" alt="loading"
						style="vertical-align: middle margin-left: 50px" height="20px"
						width="200px; " /></span> --> <span id=commitplaceholderNewCancel
					style="display: none;"> <img
						src="./resources/images/loading2.gif"
						style="vertical-align: middle; margin-left: 50px" />
				</span>
				</td>
			</tr>

		</table>
	</form>
</div>
<div id="approveAlldiv" style="display: none;">
	<form id="approveAllForm" data-role="validator" novalidate="novalidate">
		<table style="height: 190px;">
			<tr>
				<td>Service Type</td>
				<td><input id="serviceTypeApprove" name="serviceTypeApprove"
					validationMessage="Select Service Type" /></td>
			</tr>
			<tr id="fMonthPrint" style="display: none;">
				<td>From Month</td>
				<td><kendo:datePicker format="MMM yyyy  "
						name="fromdateprintBill" id="fromdateprintBill" value="${month}"
						start="year" depth="year">
					</kendo:datePicker>
				<td></td>
			</tr>

			<tr>
				<td>Month</td>
				<td><kendo:datePicker format="MMM yyyy  "
						name="presentdateapproveBill" id="presentdateapproveBill"
						required="required" value="${month}" start="year"
						class="validate[required]" validationMessage="Date is Required"
						depth="year">
					</kendo:datePicker>
				<td></td>
			</tr>


			<tr>
				<td class="left" align="center" colspan="4">
					<button type="submit" id="approveBill" class="k-button"
						style="padding-left: 10px">Approve All Bills</button> <!-- <span id=printplaceholder style="display: none;"><img
						src="./resources/images/loadingimg.GIF"
						style="vertical-align: middle; margin-left: 50px" /> &nbsp;&nbsp;
						<img src="./resources/images/loading.GIF" alt="loading"
						style="vertical-align: middle margin-left: 50px" height="20px"
						width="200px; " /></span> --> <span id=commitplaceholderNewApprove
					style="display: none;"> <img
						src="./resources/images/loading2.gif"
						style="vertical-align: middle; margin-left: 50px" />
				</span>
				</td>
			</tr>

		</table>
	</form>
</div>
<div id="postAlldiv" style="display: none;">
	<form id="postAllForm" data-role="validator" novalidate="novalidate">
		<table style="height: 190px;">
			<tr>
				<td>Service Type</td>
				<td><input id="serviceTypePost" name="serviceTypePost"
					validationMessage="Select Service Type" /></td>
			</tr>
			<tr id="fMonthPrint" style="display: none;">
				<td>From Month</td>
				<td><kendo:datePicker format="MMM yyyy  "
						name="fromdateprintBill" id="fromdateprintBill" value="${month}"
						start="year" depth="year">
					</kendo:datePicker>
				<td></td>
			</tr>

			<tr>
				<td>Month</td>
				<td><kendo:datePicker format="MMM yyyy  "
						name="presentdatepostBillLedger" id="presentdatepostBillLedger"
						required="required" value="${month}" start="year"
						class="validate[required]" validationMessage="Date is Required"
						depth="year">
					</kendo:datePicker>
				<td></td>
			</tr>


			<tr>
				<td class="left" align="center" colspan="4">
					<button type="submit" id="postBill" class="k-button"
						style="padding-left: 10px">Post All Bills</button> <!-- <span id=printplaceholder style="display: none;"><img
						src="./resources/images/loadingimg.GIF"
						style="vertical-align: middle; margin-left: 50px" /> &nbsp;&nbsp;
						<img src="./resources/images/loading.GIF" alt="loading"
						style="vertical-align: middle margin-left: 50px" height="20px"
						width="200px; " /></span> --> <span id=commitplaceholderNewPost
					style="display: none;"> <img
						src="./resources/images/loading2.gif"
						style="vertical-align: middle; margin-left: 50px" />
				</span>
				</td>
			</tr>

		</table>
	</form>
</div>

<!--Bill Correction Dialog  -->
<div id="billCorrectionDialog" style="display: none;">
	<form id="billCorrectionform" data-role="validator"
		novalidate="novalidate">
		<table>
			<tr>
				<td>
					<table id="bcPreviousDiv" style="height: 205px;">
						<!--current Table  -->

						<tr>
							<td>Account Name*</td>
							<td><input id="bcAccountNo" class="k-textbox"
								name="bcAccountNo" style="" readonly="readonly"
								required="required" /></td>
						</tr>

						<tr>
							<td>Services*</td>
							<td><input name="bcServiceName" class="k-textbox"
								id="bcServiceName" style="" readonly="readonly"
								required="required" /></td>
						</tr>
						<tr id="bctrSWPresentdate" style="display: none;">
							<td>Previous Bill Date</td>
							<td><kendo:datePicker format="dd/MM/yyyy"
									name="bcSWPresentdate" id="bcSWPresentdate"
									style="aria-readonly=true;" readonly="readonly"></kendo:datePicker>
							<td></td>
						</tr>
						<tr>
							<td>Present Bill Date*</td>
							<td><kendo:datePicker format="dd/MM/yyyy"
									name="bcPresentdate" id="bcPresentdate"></kendo:datePicker>
							<td></td>
						</tr>

						<tr id="bcPstatus">
							<td>Present Status*</td>
							<td><kendo:dropDownList name="bcPresentStaus"
									id="bcPresentStaus" onchange="getBCMeterStatus()"
									required="required"></kendo:dropDownList></td>
						</tr>
						<tr>
						<tr id="bcPresenthide">
							<td><span class="replaceme">Present Reading </span></td>
							<td><input id="bcPresentReading" name="bcPresentReading"
								placeholder="Enter Numbers Only" class="k-textbox"
								required="required" onchange="getBCUnits()" /></td>
						</tr>
						<tr id="bcpftr" style="display: none;">
							<td>PF Reading*</td>
							<td><input id="bcpfReading" name="bcpfReading"
								placeholder="Enter Numbers Only" class="k-textbox" /></td>
						</tr>
						<tr id="bcconntr" style="display: none;">
							<td>Recorded Demand*</td>
							<td><input id="bcconnectedLoad" name="bcconnectedLoad"
								placeholder="Enter Numbers Only" class="k-textbox" /></td>
						</tr>
						<tr id="bcdgtr" style="display: none;">
							<td>Present DG Reading*</td>
							<td><input id="bcdgReading" name="bcdgReading"
								placeholder="Enter Numbers Only" class="k-textbox"
								onchange="getdgUnit()" /></td>
						</tr>

					</table>

				</td>

				<td>
					<table id="presentCorrectBilldiv" style="height: 205px;">
						<!--Previous Table  -->


						<tr>
							<td>Previous Bill Date</td>
							<td><kendo:datePicker format="dd/MM/yyyy  "
									name="bcPreviousDate" id="bcPreviousDate" parse="parse()"
									value="${today}" readonly="readonly"></kendo:datePicker></td>
						</tr>
						<tr>
						<tr id="bctrPreviousStatus">
							<td>Previous Status</td>
							<td><input id="bcPreviousStaus" name="bcPreviousStaus"
								placeholder="Enter Previous Status" class="k-textbox"
								readonly="readonly" /></td>
						</tr>
						<tr id="bctrPreviousReading">
							<td>Previous Reading</td>
							<td><kendo:numericTextBox name="bcPreviousReading"
									id="bcPreviousReading" style="aria-readonly=true;"
									readonly="readonly"></kendo:numericTextBox></td>
						</tr>

						<tr id="bctrUnits">
							<td>Units</td>
							<td><kendo:numericTextBox name="bcUnits" id="bcUnits"
									style="aria-readonly=true;" readonly="readonly"></kendo:numericTextBox>
							</td>
						</tr>
						<%-- <tr>
							<td>Average Unit</td>
							<td><kendo:numericTextBox name="avgunits" id="avgunits"
									style="aria-readonly=true;" readonly="readonly"></kendo:numericTextBox>
							</td>
						</tr> --%>
						<tr id="bctrMeterFactor">
							<td>Meter constant</td>
							<td><kendo:numericTextBox name="bcMeterFactor"
									id="bcMeterFactor" style="aria-readonly=true"
									readonly="readonly"></kendo:numericTextBox></td>
						</tr>

					</table>
				</td>
			</tr>

			<tr>

				<td>
					<table id="bctodDiv" style="display: none; height: 130px;">

						<tr>
							<td>Unit Consumed<br> b/w 6 A.M to 6 P.M*
							</td>
							<td><input id="bcpresentTod1" name="bcpresentTod1"
								placeholder="Enter Numbers Only" class="k-textbox" /></td>

						</tr>
						<tr>
							<td>Unit Consumed<br> b/w 6 P.M to 10 P.M*
							</td>
							<td><input id="bcpresentTod2" name="bcpresentTod2"
								placeholder="Enter Numbers Only" class="k-textbox" /></td>

						</tr>
						<tr>
							<td>Unit Consumed<br> b/w 10 P.M to 6 A.M*
							</td>
							<td><input id="bcpresentTod3" name="bcpresentTod3"
								placeholder="Enter Numbers Only" class="k-textbox"
								onchange="getBcTodUnits()" /></td>
						</tr>

					</table>
				</td>
				<td>
					<table id="bctrpreviousDgReading"
						style="height: 130px; display: none;">
						<!--DG Table  -->

						<tr>
							<td>Previous DG <br>Reading
							</td>
							<td><kendo:numericTextBox name="bcpreviousDGReading"
									id="bcpreviousDGReading" style="aria-readonly=true;"
									readonly="readonly"></kendo:numericTextBox></td>
						</tr>
						<tr>
							<td>DG Units</td>
							<td><kendo:numericTextBox name="bcdgUnit" id="bcdgUnit"
									style="aria-readonly=true;" readonly="readonly"></kendo:numericTextBox>
							</td>
						</tr>

						<tr>
							<td>DG Meter constant</td>
							<td><kendo:numericTextBox name="bcdgmeterFactor"
									id="bcdgmeterFactor" style="aria-readonly=true"
									readonly="readonly"></kendo:numericTextBox></td>
						</tr>

					</table>
				</td>
			</tr>
			<tr>
				<td><input type="hidden" id="todcheck" name="todcheck"
					value="0" /></td>
			</tr>
			<tr>
				<td><input type="hidden" id="statuscheck" name="statuscheck"
					value="Not Generated" /></td>
			</tr>
			<tr>
				<td class="left" align="center">
					<button type="submit" id="recalculation" class="k-button"
						style="padding-left: 10px" onclick="reCalculateBill()">Bill
						Correction</button> <span id=commitplaceholderCorrection
					style="display: none;"><img
						src="./resources/images/loadingimg.GIF"
						style="vertical-align: middle; margin-left: 50px" /> &nbsp;&nbsp;
						<img src="./resources/images/loading.GIF" alt="loading"
						style="vertical-align: middle margin-left: 50px" height="20px"
						width="200px; " /></span>

				</td>
			</tr>

		</table>

		<!--END OF Main Table  -->
	</form>

</div>


<!--  -->
<div id="generateCamBill" style="display: none;">
	<form id="generateCamBillForm">
		<table style="height: 205px;">


			<tr>
				<td>Flats</td>
				<td><kendo:dropDownList id="flats" name="flats"
						onchange="getflat()" required="required"
						validationMessage="Select Flat Type"></kendo:dropDownList>
			</tr>
			<tr id="camBillCAM" style="display: none">
				<td>Bill Date</td>
				<td><kendo:datePicker format="dd/MM/yyyy  " name="camBillDate"
						id="camBillDate"></kendo:datePicker></td>
			</tr>
			<tr id="blockNameCamid" style="display: none;">
				<td>Block Name</td>
				<td><input id="blockNameCam" name="blockNameCam"
					required="required" validationMessage="Select Block Name"
					onchange="getPropertyNoCam()" /> <!-- onchange="getPropertyNo()" --></td>
			</tr>
			<tr id="propertyTypeCamid" style="display: none;">
				<td>Property Type</td>
				<td><input id="propertyTypeCam" name="propertyTypeCam"
					required="required" onchange="selectallpropCam()"
					validationMessage="Select Type" /> <!-- onchange="getPropertyNo()" --></td>
			</tr>
			<tr id="propmultiselectCam" style="display: none;">
				<td id="propmultiselectCam1">Property Number</td>
				<td id="propmultiselectCam2"><kendo:multiSelect
						id="propertyNameCam" style="display: none;" name="propertyNameCam"
						required="required" validationMessage="Select Property No"></kendo:multiSelect></td>
			</tr>
			<tr id="accountCam" style="display: none">
				<td>Account Number</td>
				<td><input id="accountIdCam" name="accountIdCam"
					onchange="getPreviousCamStatus()"></td>
			</tr>

			<%-- <tr id="PreviousBillCAM" style="display: none">
				<td>Previous Bill Date</td>
				<td><kendo:datePicker format="dd/MM/yyyy  "
						name="previousCamDate" id="previousCamDate" value="${today}"
						readonly="readonly"></kendo:datePicker></td>
			</tr> --%>


			<tr>
				<td class="left" align="center" colspan="6">
					<button type="submit" id="camBill" class="k-button"
						style="padding-left: 10px" onclick="postTheBill()">
						Generate Bill</button>
					<div id='loader' style="display: none">
						<img src="./resources/images/preloader.GIF" alt="please wait"
							style="verticle-align: middle" />&nbsp;&nbsp;Please
						Wait....&nbsp;&nbsp;&nbsp;&nbsp;
					</div>
				</td>
			</tr>

		</table>
	</form>
</div>
<div id="generateWaterBill" style="display: none;">
	<form id="generateWaterBillForm">
		<table style="height: 205px;">


			<tr>
				<td>Flats</td>
				<td><kendo:dropDownList id="flats1" name="flats1"
						onchange="getflat1()" required="required"
						validationMessage="Select Flat Type"></kendo:dropDownList>
			</tr>
			<tr id="waterBill" style="display: none">
				<td>Bill Date</td>
				<td><kendo:datePicker format="dd/MM/yyyy  " name="waterBillDate"
						id="waterBillDate"></kendo:datePicker></td>
			</tr>
			<tr id="blockNameWaterid" style="display: none;">
				<td>Block Name</td>
				<td><input id="blockNameWater" name="blockNameWater"
					required="required" validationMessage="Select Block Name"
					onchange="getPropertyNoWater()" /> <!-- onchange="getPropertyNo()" --></td>
			</tr>
			<tr id="propertyTypeWaterid" style="display: none;">
				<td>Property Type</td>
				<td><input id="propertyTypeWater" name="propertyTypeWater"
					required="required" onchange="selectallpropWater()"
					validationMessage="Select Type" /> <!-- onchange="getPropertyNo()" --></td>
			</tr>
			<tr id="propmultiselectWater" style="display: none;">
				<td id="propmultiselectWater1">Property Number</td>
				<td id="propmultiselectWater2"><kendo:multiSelect
						id="propertyNameWater" style="display: none;" name="propertyNameWater"
						required="required" validationMessage="Select Property No"></kendo:multiSelect></td>
			</tr>
			<tr id="accountWater" style="display: none">
				<td>Account Number</td>
				<td><input id="accountIdWater" name="accountIdWater"
					onchange="getPreviousWaterStatus()"></td>
			</tr>

			<%-- <tr id="PreviousBillCAM" style="display: none">
				<td>Previous Bill Date</td>
				<td><kendo:datePicker format="dd/MM/yyyy  "
						name="previousCamDate" id="previousCamDate" value="${today}"
						readonly="readonly"></kendo:datePicker></td>
			</tr> --%>


			<tr>
				<td class="left" align="center" colspan="6">
					<button type="submit" id="WaterBills" class="k-button"
						style="padding-left: 10px" onclick="postWaterBill()">
						Generate Bill</button>
					<div id='loader' style="display: none">
						<img src="./resources/images/preloader.GIF" alt="please wait"
							style="verticle-align: middle" />&nbsp;&nbsp;Please
						Wait....&nbsp;&nbsp;&nbsp;&nbsp;
					</div>
				</td>
			</tr>

		</table>
	</form>
</div>

<div id="filterNetAmountdiv" style="display: none;">
	<form id="filterNetAmountform" data-role="validator"
		novalidate="novalidate">
		<table style="height: 190px;">
			<tr>
				<td>Service Type</td>
				<td><input id="serviceTypeFilter" name="serviceTypeFilter"
					validationMessage="Select Service Type" /></td>
			</tr>
			<tr id="fMonthPrint" style="display: none;">
				<td>From Month</td>
				<td><kendo:datePicker format="MMM yyyy  "
						name="fromdateprintBill" id="fromdateprintBill" value="${month}"
						start="year" depth="year">
					</kendo:datePicker>
				<td></td>
			</tr>

			<tr>
				<td>Month</td>
				<td><kendo:datePicker format="MMM yyyy  "
						name="presentdatefilterAmount" id="presentdatefilterAmount"
						required="required" value="${month}" start="year"
						class="validate[required]" validationMessage="Date is Required"
						depth="year">
					</kendo:datePicker>
				<td></td>
			</tr>

			<tr>
				<td>Filter Amount</td>
				<td><input id="filterAmount" name="filterAmount"
					validationMessage="Select" onchange="amountrangeselect()">
				</td>
			</tr>


			<tr id="amou1" style="display: none">
				<td>Amount 1</td>
				<td><input type="text" class="k-textbox" id="amount1"
					name="amount1" validationMessage="Select"></td>
			</tr>

			<tr id="amou2" style="display: none">
				<td>Amount 2</td>
				<td><input type="text" class="k-textbox" id="amount2"
					name="amount2" validationMessage="Select"></td>
			</tr>



			<tr>
				<td class="left" align="center" colspan="4">
					<button type="submit" id="filterAmountDeatils" class="k-button"
						style="padding-left: 10px">Filter Net Amount</button> <!-- <span id=printplaceholder style="display: none;"><img
						src="./resources/images/loadingimg.GIF"
						style="vertical-align: middle; margin-left: 50px" /> &nbsp;&nbsp;
						<img src="./resources/images/loading.GIF" alt="loading"
						style="vertical-align: middle margin-left: 50px" height="20px"
						width="200px; " /></span> --> <span id=commitplaceholderNewApprove
					style="display: none;"> <img
						src="./resources/images/loading2.gif"
						style="vertical-align: middle; margin-left: 50px" />
				</span>
				</td>
			</tr>

		</table>
	</form>
</div>

<!--  -->
<!--Bill correction Dialog End  -->

<div id="billTable" style="color: black; width: 900px;"></div>


<!-- <canvas id="canvas" height="400px" width="300px"
	style="width: 300px; height: 400px;"></canvas> -->


<script>
	function billTOPrint() {
		var typePrint = $("#typePrint").val();
		if (typePrint != 'All') {
			$("#accPrint").show();
			$("#fMonthPrint").show();
		} else {
			$("#accPrint").hide();
			$("#fMonthPrint").hide();
		}
	}

	$("#billGrid")
			.on(
					"click",
					".k-grid-electricityBillTemplatesDetailsExport",
					function(e) {
						var month = $('#monthpicker').val();
						window
								.open("./electricityBillTemplate/electricityBillTemplatesDetailsExport/"
										+ month);
					});
	$("#billGrid")
			.on(
					"click",
					".k-grid-electricityBillPdfTemplatesDetailsExport",
					function(e) {

						var month = $('#monthpicker').val();
						window
								.open("./electricityBillPdfTemplate/electricityBillPdfTemplatesDetailsExport/"
										+ month);
					});

	function getTod() {

		var accountId = $("#accountNo").val();
		var rateName = $("#rateName").val();
		var rateText = $("input[name=rateName]").data("kendoDropDownList")
				.text();

		$.ajax({
			type : "GET",
			url : "./bill/getTod",
			dataType : "text",
			data : {

				accountId : accountId,
				rateText : rateText,
				rateName : rateName,

			},
			success : function(response) {
				//alert(response);
				if (response == 3) {
					$("#todDiv").show();
					$("#dgtr").hide();
					$("#previousDgReading").hide();
					$("#todcheck").val("3");

				} else if (response == 1) {

					$("#todDiv").hide();
					$("#dgtr").show();
					$("#previousDgReading").show();
					$("#todcheck").val("1");
				} else if (response == 2) {

					$("#todDiv").show();
					$("#dgtr").show();
					$("#previousDgReading").show();
					$("#todcheck").val("2");
				} else {
					$("#todDiv").hide();
					$("#dgtr").hide();
					$("#todcheck").val("0");
					$("#previousDgReading").hide();
				}

			}

		});

	}

	function getddService() {

		var accountId = $("#accountNodd").val();

		$("#servicesNamedd").kendoDropDownList({

			optionLabel : "Select Services ...",
			dataTextField : "typeOfService",
			dataValueField : "serviceMasterId",

			dataSource : {

				transport : {
					read : "./bill/getServiceName?accountId=" + accountId,

				}
			}
		}).data("kendoDropDownList");
	}

	$("#billGrid")
			.on(
					"click",
					"#temPID",
					function(e) {

						var button = $(this), enable = button.text() == "Approve";
						var widget = $("#billGrid").data("kendoGrid");
						var dataItem = widget.dataItem($(e.currentTarget)
								.closest("tr"));
						var result = securityCheckForActionsForStatus("./BillGeneration/GenerateBills/approvePostButton");
						if (result == "success") {
							if (enable) {
								/* 	if (netAmount <= 0) {
										alert("You can not approve the bill if net amount is 0 ");
									} else { */
								$
										.ajax({
											type : "POST",
											url : "./electricityBills/setBillStatus/"
													+ dataItem.id + "/activate",
											dataType : "text",
											success : function(response) {
												$("#alertsBox").html("");
												$("#alertsBox").html(response);
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
												button.text('Poste');
												$('#billGrid')
														.data('kendoGrid').dataSource
														.read();
											}
										});

								/* } */
							} else {
								$
										.ajax({
											type : "POST",
											url : "./electricityBills/setBillStatus/"
													+ dataItem.id
													+ "/deactivate",
											dataType : "text",
											success : function(response) {
												$("#alertsBox").html("");
												$("#alertsBox").html(response);
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
												button.text('Posted');
												$('#billGrid')
														.data('kendoGrid').dataSource
														.read();
											}
										});
							}
						}
					});

	function getdgUnit() {

		var previousDGReading = $('#previousDGReading').val();
		var dgReading = $('#dgReading').val();
		var presentStaus = $("#presentStaus").val();
		var rateName = $("input[name=rateName]").data("kendoDropDownList")
				.text();
		var presentbilldate = $("#presentbilldate").val();
		var accountId = $("#accountNo").val();
		var previousbilldate = $("#previousbillDate").val();
		var serviceId = $("#rateName").val();

		if (presentStaus == "Meter Change") {

			$.ajax({
				type : "GET",
				url : "./bill/getDGMeterChangeReading",
				dataType : "json",
				data : {
					presentStaus : presentStaus,
					rateName : rateName,
					accountId : accountId,
					previousbilldate : previousbilldate,
					presentbilldate : presentbilldate,
					dgReading : dgReading,
					serviceId : serviceId,
					previousDGReading : previousDGReading,

				},
				success : function(response) {

					$("input[name=dgUnit]").data("kendoNumericTextBox").value(
							response.Units);
					//var unit = $('#dgUnit').data("kendoNumericTextBox");
					//unit.value($('#dgUnit').val(response).val());
				}
			});

		} else {
			var response = (parseFloat(dgReading) - parseFloat(previousDGReading));

			var unit = $('#dgUnit').data("kendoNumericTextBox");
			unit.value($('#dgUnit').val(response).val());
		}

	}

	function getTodUnits() {
		//alert("todunits");
		var unit = $('#units').val();
		var billto = $('#previousReading').val();
		var todval1 = $('#presentTod1').val();
		var todval2 = $('#presentTod2').val();
		var todval3 = $('#presentTod3').val();

		$.ajax({
			type : "GET",
			url : "./bill/getTodUnits",
			dataType : "text",

			data : {

				billto : billto,
				todval1 : todval1,
				todval2 : todval2,
				todval3 : todval3,
				unit : unit,
			},
			success : function(response) {
				var result = $.trim(response);

				if (result == 'Less') {
					alert("Tod Slabs can not be less than Unit");
					$('#presentTod3').val("");
				}
				if (result == 'More') {
					alert("Tod Slabs can not be More than Unit");
					$('#presentTod3').val("");
				} else {
				}
			}
		});
	}

	function getBcTodUnits() {
		//alert("todunits");
		var unit = $('#bcUnits').val();
		var billto = $('#bcpreviousReading').val();
		var todval1 = $('#bcpresentTod1').val();
		var todval2 = $('#bcpresentTod2').val();
		var todval3 = $('#bcpresentTod3').val();

		$.ajax({
			type : "GET",
			url : "./bill/getTodUnits",
			dataType : "text",
			data : {

				billto : billto,
				todval1 : todval1,
				todval2 : todval2,
				todval3 : todval3,
				unit : unit,
			},
			success : function(response) {
				var result = $.trim(response);

				if (result == 'Less') {
					alert("Tod Slabs can not be less than Unit");
					$('#bcpresentTod3').val("");
				}
				if (result == 'More') {
					alert("Tod Slabs can not be More than Unit");
					$('#bcpresentTod3').val("");
				} else {
				}
			}
		});
	}

	$("#addform").submit(function(e) {
		ignore: [], e.preventDefault();
	});

	$("#generateCamBillForm").submit(function(e) {
		e.preventDefault();
	});
    
	
	$("#generateWaterBillForm").submit(function(e) {
		e.preventDefault();
	});
	
	$("#uploadForm").submit(function(e) {
		e.preventDefault();
	});

	$("#billCorrectionform").submit(function(e) {
		e.preventDefault();
	});

	$("#ddForm").submit(function(e) {
		e.preventDefault();
	});
	$("#postAllTallyForm").submit(function(e) {
		e.preventDefault();
	});
	$("#postAllForm").submit(function(e) {
		e.preventDefault();
	});
	$("#cancelAllForm").submit(function(e) {
		e.preventDefault();
	});
	$("#approveAllForm").submit(function(e) {
		e.preventDefault();
	});
	$("#filterNetAmountform").submit(function(e) {
		e.preventDefault();
	});

	/* var postAllTallyFormbutton = $("#postAllTallyForm").kendoValidator().data("kendoValidator"); */
	$("#postTallyBill").on("click", function() {
		//alert("fgf");

		/* if (postAllTallyFormButton.validate()) { */

		postAllToTally();
		/* } */
	});

	function getMessage(input) {
		return input.data("message");
	}
	var addvalidator = $("#addform").kendoValidator().data("kendoValidator");
	$("#calcu").on("click", function() {

		if (addvalidator.validate()) {

			generateBill();
		}
	});

	var billdownloadvalidator = $("#downloadAllBillsForm").kendoValidator()
			.data("kendoValidator");
	$("#downlodbutton").on("click", function() {

		if (billdownloadvalidator.validate()) {

			downloadAllBills();
		}
	});

	$("#verifyDuplicate").on("click", function() {

		if (billdownloadvalidator.validate()) {

			getDuplicateBills();
		}
	});
	$("#savelatePaymentAmount").on("click", function() {

		if (billdownloadvalidator.validate()) {

			saveLatePayment();
		}
	});

	var printbillvalidator = $("#printBillingForm").kendoValidator().data(
			"kendoValidator");
	$("#printBill").on("click", function() {

		if (printbillvalidator.validate()) {

			printAllBills();
		}
	});

	var amrcalculatevalidator = $("#amrBillingForm").kendoValidator().data(
			"kendoValidator");
	$("#amrCalculate").on("click", function() {

		if (amrcalculatevalidator.validate()) {

			generateAmrBill();
		}
	});

	$("#verify").on("click", function() {

		if (amrcalculatevalidator.validate()) {

			exportAmrBillData();
		}
	});

	var cancelbillvalidator = $("#cancelAllForm").kendoValidator().data(
			"kendoValidator");
	$("#cancelBill").on("click", function() {

		if (cancelbillvalidator.validate()) {

			cancelAllBill();
		}
	});
	var approvebillvalidator = $("#approveAllForm").kendoValidator().data(
			"kendoValidator");
	$("#approveBill").on("click", function() {

		if (approvebillvalidator.validate()) {

			approveAllBill();
		}
	});
	var postbillvalidator = $("#postAllForm").kendoValidator().data(
			"kendoValidator");
	$("#postBill").on("click", function() {

		if (postbillvalidator.validate()) {

			postAllBill();
		}
	});
	jQuery.fn.ForceNumericOnly = function() {
		return this
				.each(function() {
					$(this)
							.keydown(
									function(e) {
										var key = e.charCode || e.keyCode || 0;
										return (key == 8 || key == 9
												|| key == 13 || key == 46
												|| key == 110 || key == 190
												|| (key >= 35 && key <= 40)
												|| (key >= 48 && key <= 57) || (key >= 96 && key <= 105));
									});
				});
	};

	$("#presentReading").ForceNumericOnly();

	function startChange() {

		var endPicker = $("#presentbilldate").val();
		var accountId = $("#accountNo").val();
		var rateName = $("input[name=rateName]").data("kendoDropDownList")
				.text();

		$.ajax({
			type : "GET",
			url : "./bill/getBillDate",
			dataType : "text",
			data : {

				endPicker : endPicker,
				accountId : accountId,
				rateName : rateName,
			},
			success : function(response) {
				if (response == "calculate") {

				} else {
					alert(response);

				}
			}
		});
	}
	function getMeterStatus() {
		var presentStaus = $("#presentStaus").val();
		var rateName = $("input[name=rateName]").data("kendoDropDownList")
				.text();
		var serviceID = $("#rateName").val();
		var presentbilldate = $("#presentbilldate").val();
		var accountId = $("#accountNo").val();

		var previousbilldate = $("#previousbillDate").val();
		if (presentStaus != "Normal" && presentStaus != "Meter Change") {

			var billto = $('#presentReading').val(0);

			$("#presenthide").hide();

			$.ajax({
				type : "GET",
				url : "./bill/getAvgUnit",
				dataType : "text",
				data : {
					presentStaus : presentStaus,
					rateName : rateName,
					presentbilldate : presentbilldate,
					accountId : accountId,
					serviceID : serviceID,

				},
				success : function(response) {
					var unit = $('#units').data("kendoNumericTextBox");
					unit.value($('#units').val(response).val());
					var billto = $('#avgunits').data("kendoNumericTextBox");
					billto.value($('#avgunits').val(response).val());
				}

			});

		} else {
			var Value = $("#todcheck").val();
			$("#presenthide").show();
			if (Value == 0) {

			} else {

			}
		}
		if (presentStaus == "Meter Change") {

		}

	}
	function avgUnitForMeterchange() {
		var presentStaus = $("#presentStaus").val();
		var rateName = $("input[name=rateName]").data("kendoDropDownList")
				.text();
		var presentbilldate = $("#presentbilldate").val();
		var accountId = $("#accountNo").val();
		var previousbilldate = $("#previousbillDate").val();
		var serviceID = $("#rateName").val();

		$.ajax({
			type : "GET",
			url : "./bill/getAvgUnit",
			dataType : "text",
			data : {
				presentStaus : presentStaus,
				rateName : rateName,
				presentbilldate : presentbilldate,
				accountId : accountId,
				serviceID : serviceID,
			},
			success : function(response) {
				var billto = $('#avgunits').data("kendoNumericTextBox");
				billto.value($('#avgunits').val(response).val());
			}

		});
	}

	function getAvgAlert() {
		var presentStaus = $("#presentStaus").val();
		var rateName = $("input[name=rateName]").data("kendoDropDownList")
				.text();
		var presentbilldate = $("#presentbilldate").val();
		var accountId = $("#accountNo").val();
		var presentReading = $('#presentReading').val();
		var unit = $('#units').val();
		$.ajax({
			type : "GET",
			url : "./bill/getAvgAlert",
			dataType : "text",
			data : {
				presentStaus : presentStaus,
				rateName : rateName,
				presentbilldate : presentbilldate,
				accountId : accountId,
				presentReading : presentReading,
				unit : unit,
			},
			success : function(response) {
				//alert(response + "------");
				//alert(response.subNoormal);
				var result = $.trim(response);
				if (result != "General") {
					alert(response);
				}

			}

		});

	}
	$(document)
			.ready(
					function() {

						var generateTypeList = [ {
							text : "Generate Bill",
							value : "Generate Bill"
						}, {
							text : "Generate BroadBand & Telecom Bill",
							value : "Generate BroadBand & Telecom Bill"
						}, {
							text : "Generate Bulk Bill",
							value : "Generate Bulk Bill"
						}, {
							text : "Raise Deposit Demand",
							value : "Raise Deposit Demand"
						}, {
							text : "Generate AMR Bill",
							value : "Generate AMR Bill"
						}, {
							text : "Generate CAM Bill",
							value : "Generate CAM Bill"
						}, {
							text : "Generate Water Bill",
							value : "Generate Water Bill"
						}, {
							text : "Print Bill",
							value : "Print Bill"
						},

						];

						$("#generateDropDown").kendoComboBox({
							dataTextField : "text",
							dataValueField : "value",
							/* optionLabel : {
								text : "Generate",
								value : "",
							}, */

							dataSource : generateTypeList,
							select : onselect,
							datbound : onselect,

						}).data("kendoComboBox");

						var filterListDownload = [ {
							text : "Range",
							value : "Range"
						}, {
							text : "Is Equal To",
							value : "Is equal to"
						}, {
							text : "Is greater than",
							value : "Is greater than"
						}, {
							text : "Is less than",
							value : "Is less than"
						},

						];

						$("#filterAmount").kendoDropDownList({
							dataTextField : "text",
							dataValueField : "value",
							optionLabel : {
								text : "Select",
								value : "",
							},
							dataSource : filterListDownload
						}).data("kendoDropDownList");

						var camTypeList = [ {
							text : "All",
							value : "All"
						}, {
							text : "Specific",
							value : "Specific"
						}, {
							text : "Not Generated",
							value : "Not Generated"
						},

						];

						$("#flats").kendoDropDownList({
							dataTextField : "text",
							dataValueField : "value",
							optionLabel : {
								text : "Select",
								value : "",
							},

							dataSource : camTypeList

						}).data("kendoDropDownList");
                        
						$("#flats1").kendoDropDownList({
							dataTextField : "text",
							dataValueField : "value",
							optionLabel : {
								text : "Select",
								value : "",
							},

							dataSource : camTypeList

						}).data("kendoDropDownList");
						
						$("#monthpicker").kendoDatePicker({
							// defines the start view
							start : "year",
							// defines when the calendar should return date
							depth : "year",
							value : new Date(),
							// display month and year in the input
							format : "MMMM yyyy"
						});

						$("#blockName").kendoDropDownList({
							autoBind : false,
							optionLabel : "Select",
							dataTextField : "blockName",

							dataValueField : "blockId",
							dataSource : {
								transport : {
									read : "${towerNames}"
								}
							}
						});

						$("#blockNameAmr").kendoDropDownList({
							autoBind : false,
							optionLabel : "Select",
							dataTextField : "blockName",

							dataValueField : "blockId",
							dataSource : {
								transport : {
									read : "${towerNames}"
								}
							}
						});

						$("#blockNameCam").kendoDropDownList({
							autoBind : false,
							optionLabel : "Select",
							dataTextField : "blockName",

							dataValueField : "blockId",
							dataSource : {
								transport : {
									read : "${towerNames}"
								}
							}
						});

						$("#blockNameWater").kendoDropDownList({
							autoBind : false,
							optionLabel : "Select",
							dataTextField : "blockName",

							dataValueField : "blockId",
							dataSource : {
								transport : {
									read : "${towerNames}"
								}
							}
						});

						var propertyTypeList = [ {
							text : "All",
							value : "All"
						}, {
							text : "Specific",
							value : "Specific"
						},

						];

						$("#propertyType").kendoDropDownList({
							dataTextField : "text",
							dataValueField : "value",
							optionLabel : {
								text : "Select",
								value : "",
							},
							dataSource : propertyTypeList
						}).data("kendoDropDownList");

						$("#propertyTypeCam").kendoDropDownList({
							dataTextField : "text",
							dataValueField : "value",
							optionLabel : {
								text : "Select",
								value : "",
							},
							dataSource : propertyTypeList
						}).data("kendoDropDownList");

						$("#propertyTypeWater").kendoDropDownList({
							dataTextField : "text",
							dataValueField : "value",
							optionLabel : {
								text : "Select",
								value : "",
							},
							dataSource : propertyTypeList
						}).data("kendoDropDownList");
						
						$("#typePrint").kendoDropDownList({
							dataTextField : "text",
							dataValueField : "value",
							optionLabel : {
								text : "Select",
								value : "",
							},
							dataSource : propertyTypeList
						}).data("kendoDropDownList");

						var serviceList = [ {
							text : "Water",
							value : "Water"
						}, {
							text : "Solid Waste",
							value : "Solid Waste"
						},

						];

						$("#blockCamId").kendoDropDownList({
							dataTextField : "blockName",
							dataValueField : "blockName",
							autoBind : false,
							optionLabel : "Select",
							dataSource : {
								transport : {
									read : "${camtowerNames}"
								}
							}

						}).data("kendoDropDownList");
                        
						$("#blockWaterId").kendoDropDownList({
							dataTextField : "blockName",
							dataValueField : "blockName",
							autoBind : false,
							optionLabel : "Select",
							dataSource : {
								transport : {
									read : "${camtowerNames}"
								}
							}

						}).data("kendoDropDownList");
						
						$("#propertyIdCam").kendoDropDownList({
							cascadeFrom : "blockCamId",
							dataTextField : "property_No",
							dataValueField : "propertyId",
							optionLabel : "Select",
							autoBind : false,
							dataSource : {
								transport : {
									read : "${propertyNum}"
								}
							}

						}).data("kendoDropDownList");
                      
						$("#propertyIdCam").kendoDropDownList({
							cascadeFrom : "blockWaterId",
							dataTextField : "property_No",
							dataValueField : "propertyId",
							optionLabel : "Select",
							autoBind : false,
							dataSource : {
								transport : {
									read : "${propertyNum}"
								}
							}

						}).data("kendoDropDownList");

						
						$("#accountIdCam")
								.kendoComboBox(
										{
											autoBind : false,
											cascadeFrom : "propertyIdCam",
											dataTextField : "accountNo",
											dataValueField : "accountId",
											placeholder : "Select account",
											headerTemplate : '<div class="dropdown-header">'
													+ '<span class="k-widget k-header">Photo</span>'
													+ '<span class="k-widget k-header">Contact info</span>'
													+ '</div>',
											template : '<table><tr><td rowspan=2><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.personId#'/>" width=40 height=55 alt=\"No Image to Display\" /></span></td>'
													+ '<td align="left"><span class="k-state-default"><b>#: data.personName #</b></span><br>'
													+ '<span class="k-state-default"><i>#: data.accountNo #</i></span><br>'
													+ '<span class="k-state-default"><i>#: data.personType #</i></span></td></tr></table>',
											//select : camPreviousDate,

											dataSource : {
												transport : {
													read : "${readAccountNumbers}"
												}
											}

										}).data("kendoComboBox");
                        
						$("#accountIdWater")
						.kendoComboBox(
								{
									autoBind : false,
									cascadeFrom : "propertyIdCam",
									dataTextField : "accountNo",
									dataValueField : "accountId",
									placeholder : "Select account",
									headerTemplate : '<div class="dropdown-header">'
											+ '<span class="k-widget k-header">Photo</span>'
											+ '<span class="k-widget k-header">Contact info</span>'
											+ '</div>',
									template : '<table><tr><td rowspan=2><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.personId#'/>" width=40 height=55 alt=\"No Image to Display\" /></span></td>'
											+ '<td align="left"><span class="k-state-default"><b>#: data.personName #</b></span><br>'
											+ '<span class="k-state-default"><i>#: data.accountNo #</i></span><br>'
											+ '<span class="k-state-default"><i>#: data.personType #</i></span></td></tr></table>',
									//select : camPreviousDate,

									dataSource : {
										transport : {
											read : "${readAccountNumbers}"
										}
									}

								}).data("kendoComboBox");
						
						function camPreviousDate(e) {

							var dataItem = this.dataItem(e.item.index());
							var accountIdCAM = dataItem.accountId;
							$
									.ajax({
										type : "POST",
										url : "./bill/getPreviousCAMDAte/"
												+ accountIdCAM,

										dataType : "json",
										data : {
											accountId : accountIdCAM,
										},
										success : function(response) {

											var parts = response.split("-");
											var day = parts[2]
													&& parseInt(parts[2], 10);
											var month = parts[1]
													&& parseInt(parts[1], 10);
											var year = parts[0]
													&& parseInt(parts[0], 10);

											$('input[name="previousCamDate"]')
													.val(
															day + "/" + month
																	+ "/"
																	+ year);
											$('input[name="previousCamDate"]')
													.change();
											$('input[name="previousCamDate"]')
													.attr('readonly',
															'readonly');
										}

									});
						}

						var serviceListDownload = [ {
							text : "Electricity",
							value : "Electricity"
						}, {
							text : "Water",
							value : "Water"
						}, {
							text : "Gas",
							value : "Gas"
						}, {
							text : "CAM",
							value : "CAM"
						}, {
							text : "Telephone Broadband",
							value : "Telephone Broadband"
						}, {
							text : "Solid Waste",
							value : "Solid Waste"
						}, {
							text : "Others",
							value : "Others"
						},

						];
						$("#serviceTypePost").kendoDropDownList({
							dataTextField : "text",
							dataValueField : "value",
							optionLabel : {
								text : "Select",
								value : "",
							},
							dataSource : serviceListDownload
						}).data("kendoDropDownList");

						$("#serviceTypeFilter").kendoDropDownList({
							dataTextField : "text",
							dataValueField : "value",
							optionLabel : {
								text : "Select",
								value : "",
							},
							dataSource : serviceListDownload
						}).data("kendoDropDownList");
						$("#serviceTypeApprove").kendoDropDownList({
							dataTextField : "text",
							dataValueField : "value",
							optionLabel : {
								text : "Select",
								value : "",
							},
							dataSource : serviceListDownload
						}).data("kendoDropDownList");
						$("#serviceTypeCancel").kendoDropDownList({
							dataTextField : "text",
							dataValueField : "value",
							optionLabel : {
								text : "Select",
								value : "",
							},
							dataSource : serviceListDownload
						}).data("kendoDropDownList");

						$("#serviceTypePrint").kendoDropDownList({
							dataTextField : "text",
							dataValueField : "value",
							optionLabel : {
								text : "Select",
								value : "",
							},
							dataSource : serviceListDownload
						}).data("kendoDropDownList");

						$("#serviceTypeDownload").kendoDropDownList({
							dataTextField : "text",
							dataValueField : "value",
							optionLabel : {
								text : "Select",
								value : "",
							},
							dataSource : serviceListDownload
						}).data("kendoDropDownList");

						$("#serviceTypeTally").kendoDropDownList({
							dataTextField : "text",
							dataValueField : "value",
							optionLabel : {
								text : "Select",
								value : "",
							},
							dataSource : serviceListDownload
						}).data("kendoDropDownList");

						$("#serviceTypeList").kendoDropDownList({
							dataTextField : "text",
							dataValueField : "value",
							optionLabel : {
								text : "Select",
								value : "",
							},

							dataSource : serviceList

						}).data("kendoDropDownList");

						var bulkBillCalcType = [ {
							text : "Square Feet",
							value : "Square Feet"
						}, {
							text : "Flat Rate",
							value : "Flat Rate"
						},

						];

						$("#billCalcType").kendoDropDownList({
							dataTextField : "text",
							dataValueField : "value",
							optionLabel : {
								text : "Select",
								value : "",
							},

							dataSource : bulkBillCalcType

						}).data("kendoDropDownList");

						var autocomplete = $("#accountNo")
								.kendoComboBox(
										{
											filter : "startswith",
											autoBind : false,
											dataTextField : "accountNumber",
											dataValueField : "accountId",
											placeholder : "Select accountno...",

											headerTemplate : '<div class="dropdown-header">'
													+ '<span class="k-widget k-header">Photo</span>'
													+ '<span class="k-widget k-header">Contact info</span>'
													+ '</div>',
											template : '<table><tr><td rowspan=2><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.personId#'/>" width=40 height=55 alt=\"No Image to Display\" /></span></td>'
													+ '<td align="left"><span class="k-state-default"><b>#: data.personName #</b></span><br>'
													+ '<span class="k-state-default"><i>#: data.accountNumber #</i></span><br>'
													+ '<span class="k-state-default"><i>#: data.personType #</i></span></td></tr></table>',
											dataSource : {
												transport : {
													read : {

														url : "./bill/accountNumberAutocomplete"
													}
												}
											},
											height : 370,
										}).data("kendoComboBox");

						$("#accountNodd")
								.kendoComboBox(
										{
											filter : "startswith",
											autoBind : false,
											dataTextField : "accountNumber",
											dataValueField : "accountId",
											placeholder : "Select accountno...",
											headerTemplate : '<div class="dropdown-header">'
													+ '<span class="k-widget k-header">Photo</span>'
													+ '<span class="k-widget k-header">Contact info</span>'
													+ '</div>',
											template : '<table><tr><td rowspan=2><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.personId#'/>" width=40 height=55 alt=\"No Image to Display\" /></span></td>'
													+ '<td align="left"><span class="k-state-default"><b>#: data.personName #</b></span><br>'
													+ '<span class="k-state-default"><i>#: data.accountNumber #</i></span><br>'
													+ '<span class="k-state-default"><i>#: data.personType #</i></span></td></tr></table>',
											dataSource : {
												transport : {
													read : {

														url : "./bill/accountNumberAutocomplete"
													}
												}
											},
											height : 370,
										}).data("kendoComboBox");

						$("#accountNoprint")
								.kendoComboBox(
										{
											filter : "startswith",
											autoBind : false,
											dataTextField : "accountNumber",
											dataValueField : "accountId",
											placeholder : "Select accountno...",
											headerTemplate : '<div class="dropdown-header">'
													+ '<span class="k-widget k-header">Photo</span>'
													+ '<span class="k-widget k-header">Contact info</span>'
													+ '</div>',
											template : '<table><tr><td rowspan=2><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.personId#'/>" width=40 height=55 alt=\"No Image to Display\" /></span></td>'
													+ '<td align="left"><span class="k-state-default"><b>#: data.personName #</b></span><br>'
													+ '<span class="k-state-default"><i>#: data.accountNumber #</i></span><br>'
													+ '<span class="k-state-default"><i>#: data.personType #</i></span></td></tr></table>',
											dataSource : {
												transport : {
													read : {

														url : "./bill/accountNumberAutocomplete"
													}
												}
											},
											height : 370,
										}).data("kendoComboBox");

						$("#propertyNo")
								.kendoComboBox(
										{
											filter : "startswith",
											autoBind : false,
											dataTextField : "propertyNo",
											dataValueField : "propertyId",
											placeholder : "Select property No...",

											dataSource : {
												transport : {
													read : {

														url : "./billingPayments/getAllPropertyNo"
													}
												}
											},
											height : 370,
										}).data("kendoComboBox");

						var data = [ {
							text : "Normal",
							value : "Normal"
						}, {
							text : "Burnt",
							value : "Burnt"
						}, {
							text : "Door Lock",
							value : "Door Lock"
						}, {
							text : "Direct Connection",
							value : "Direct Connection"
						}, {
							text : "Meter Not Reading",
							value : "Meter Not Reading"
						}, {
							text : "Sticky",
							value : "Sticky"
						}, {
							text : "Meter Change",
							value : "Meter Change"
						},

						];

						$("#presentStaus").kendoDropDownList({
							dataTextField : "text",
							dataValueField : "value",
							optionLabel : {
								text : "Select",
								value : "",
							},

							dataSource : data
						}).data("kendoDropDownList");
						$("#bcPresentStaus").kendoDropDownList({
							dataTextField : "text",
							dataValueField : "value",
							optionLabel : {
								text : "Select",
								value : "",
							},

							dataSource : data
						}).data("kendoDropDownList");

						//billType
						var billData = [ {
							text : "Normal Bill",
							value : "Normal Bill"
						}, {
							text : "Provisional Bill",
							value : "Provisional Bill"
						},

						];

						$("#postType").kendoDropDownList({
							dataTextField : "text",
							dataValueField : "value",
							optionLabel : {
								text : "Select",
								value : "",
							},

							dataSource : data
						}).data("kendoDropDownList");
						$("#postType").kendoDropDownList({
							dataTextField : "text",
							dataValueField : "value",
							optionLabel : {
								text : "Select",
								value : "",
							},

							dataSource : billData
						}).data("kendoDropDownList");

					});

	$("#files").kendoUpload({
		multiple : true,
		upload : onUpload,
		success : onDocSuccess,
		error : AjaxFailed,
		async : {
			saveUrl : "./btbill/upload",
			autoUpload : true
		}
	});

	$("#batchFile").kendoUpload({
		multiple : true,
		upload : onUploadXLS,
		success : onDocSuccessBatch,
		error : AjaxFailedBatch,
		async : {
			saveUrl : "./batchUpdate/uploadBatchBills",
			autoUpload : true
		}
	});

	$("#XMLFile").kendoUpload({
		multiple : true,
		upload : onUploadXML,
		success : onDocSuccessXML,
		error : AjaxFailedXML,
		async : {
			saveUrl : "./batchUpdate/uploadXMLBills",
			autoUpload : true,
		}
	});
	var selectGenType = "";
	function onselect(e) {
		var dataItem = this.dataItem(e.item.index());
		selectGenType = dataItem.text;

		if (selectGenType == "Generate Bill") {
			openGenerateBill();
		} else if (selectGenType == "Generate BroadBand & Telecom Bill") {
			openBtBill();
		} else if (selectGenType == "Raise Deposit Demand") {
			openRaisDD();
		} else if (selectGenType == "Generate Bulk Bill") {
			openBulkBill();
		} else if (selectGenType == "Generate AMR Bill") {
			openAmrBill();
		} else if (selectGenType == "Generate CAM Bill") {
			openCamBill();
		}else if (selectGenType == "Generate Water Bill"){
			openWaterBill();
		}else if (selectGenType == "Print Bill") {

			printAllBill();

		}

	}
	function onDocSuccess() {
		alert("File Imported Successfully");
		window.location.reload();
	}

	function onDocSuccessXML() {
		alert("File Imported Successfully");
		window.location.reload();
	}

	function onDocSuccessBatch() {
		alert("File Imported Successfully");
		window.location.reload();
	}

	function AjaxFailed(e) {
		var err = e.XMLHttpRequest.responseText;
		if (err == "Bill Is Already Generated") {
			alert(err);
		} else {
			alert("File Importing Failed");
		}

		window.location.reload();
	}
	function AjaxFailedBatch(e) {
		var err = e.XMLHttpRequest.responseText;
		if (err == "Bill Is Already Generated") {
			alert(err);
		} else {
			alert("File Importing Failed");
		}
		window.location.reload();
	}

	function AjaxFailedXML(e) {
		var err = e.XMLHttpRequest.responseText;
		if (err == "Bill Is Already Generated") {
			alert(err);
		} else {
			alert("File Importing Failed");
		}
		window.location.reload();
	}

	function onUpload(e) {
		var files = e.files;
		$
				.each(
						files,
						function() {
							if (this.extension.toLowerCase() != ".xlsx") {
								alert(" Invalid Document Type:\n Acceptable formats is .xlsx Only");
								e.preventDefault();
								return false;
							}
						});
	}

	function onUploadXLS(e) {
		var files = e.files;
		$
				.each(
						files,
						function() {
							if (this.extension.toLowerCase() != ".xls") {
								alert(" Invalid Document Type:\n Acceptable formats is .xls Only");
								e.preventDefault();
								return false;
							}
						});
	}

	function onUploadXML(e) {
		var files = e.files;
		$
				.each(
						files,
						function() {
							if (this.extension.toLowerCase() != ".xml") {
								alert(" Invalid Document Type:\n Acceptable formats is .xml Only");
								e.preventDefault();
								return false;
							}
						});
	}

	function gettariffId() {

		var accountId = $("#accountNo").val();

		$("#rateName").kendoDropDownList({

			optionLabel : "Select Services ...",
			dataTextField : "typeOfService",
			dataValueField : "serviceMasterId",

			dataSource : {

				transport : {
					read : "./bill/getServiceName?accountId=" + accountId,

				}
			}
		}).data("kendoDropDownList");

		//datepicker.value(todayDate);
		/* $('#presentbilldate').attr('disabled', 'disabled'); */
		//$('#presentbilldate').attr('max',todayDate);
	}

	function getstatus() {

		var billId = $("#accountNo").val();
		var rateName = $("input[name=rateName]").data("kendoDropDownList")
				.text();
		var serviceId = $("#rateName").val();

		var newvalidf = "";
		var olddatefrom = "";
		if (rateName != "Solid Waste" && rateName != "Others") {
			$('.replaceme').html('Present Reading');
			$('#pstatus').show();
			$('#presentdiv').show();
			$('#sWasteDate').hide();
			$("#presentStaus").prop("required", "required");
			$.ajax({
				type : "GET",
				url : "./bill/getBillStatus",
				data : {
					rateName : rateName,
					billId : billId,
					serviceId : serviceId,
				},
				success : function(response) {

					$('#previousStaus').val(response.previousStatus);
					$('#statuscheck').val(response.billgenerationstatus);
					var billto = $('#previousReading').data(
							"kendoNumericTextBox");
					billto.value($('#previousReading').val(
							response.InitialReading).val());
					var meterFactor = $('#meterFactor').data(
							"kendoNumericTextBox");
					meterFactor.value($('#meterFactor').val(
							response.MeterConstant).val());
					olddatefrom = $('#previousbillDate').val(response.billdate)
							.val();
					newvalidf = olddatefrom.split("-").reverse().join("/")
							+ " ";
					$('#previousbillDate').val(newvalidf);
					var previousdg = $('#previousDGReading').data(
							"kendoNumericTextBox");

					previousdg.value($('#previousDGReading').val(
							response.DGInitialReading).val());

					var dgmeterfactor = $('#dgmeterFactor').data(
							"kendoNumericTextBox");
					dgmeterfactor.value($('#dgmeterFactor').val(
							response.DGMeterConstant).val());

					getpreviousstatus();
					previousdate();
				}

			});
			if (rateName == "Electricity") {

				$.ajax({
					type : "GET",
					url : "./bill/getpfApplicable",
					dataType : "text",
					data : {
						rateName : rateName,
						billId : billId,
						serviceId : serviceId,
					},
					success : function(response) {

						if (response == "Yes") {
							$("#pftr").show();
							$("#conntr").show();
							$('#previousDiv').css("height", "300px");
						} else {
							$("#pftr").hide();
							$("#conntr").hide();
							$('#previousDiv').css("height", "250px");
						}
					}
				});

			} else {

				$('#previousDiv').css("height", "250px");
				$("#pftr").hide();
				$("#conntr").hide();
			}
			var todayDate = new Date();
			$('#presentbilldate').data("kendoDatePicker").max(todayDate);

		} else {
			$('.replaceme').html('Quantity');
			$("#pstatus").hide();
			$('#presentdiv').hide();
			$('#sWasteDate').show();
			//$("#pstatusTd1").removeProp("required");
			$("#presentStaus").prop("required", false);
			if (rateName == "Others") {
				var billto = $('#presentReading').val(0);

				$("#presenthide").hide();
			} else {
				$("#presenthide").show();
			}
			if (rateName == "Others") {
				var billto = $('#presentReading').val(0);

				$("#presenthide").hide();
			} else {
				$("#presenthide").show();
			}

			$.ajax({
				type : "GET",
				url : "./bill/getSolidWasteStatus",
				data : {
					rateName : rateName,
					billId : billId,
					serviceId : serviceId,
				},
				success : function(response) {

					//alert(response[0]);
					var i = 0;

					for (i = 0; i <= response.length; i++) {
						//

					}

					var olddatefrom = $('#previousSWasteDate').val(
							response.billdate).val();
					newvalidf = olddatefrom.split("-").reverse().join("/")
							+ " ";
					$('#previousbillDate').val(newvalidf);
					$('#previousSWasteDate').val(newvalidf);

				}

			});
		}

	}
	function onselectedProp() {

		//var dataItem = this.dataItem(e.item.index());
		var propertyId = $("#propertyNo").val();

		var comboBoxDataSource = new kendo.data.DataSource(
				{
					transport : {
						read : {
							url : "./billingGeneration/getAccountsBasedOnseletedProperty/"
									+ propertyId,
							dataType : "json",
							type : 'GET'
						}
					},

				});

		$("#accountNo")
				.kendoComboBox(
						{
							dataSource : comboBoxDataSource,
							dataType : 'JSON',
							dataTextField : "accountNumber",
							dataValueField : "accountId",
							placeholder : "Enter Account Number",
							headerTemplate : '<div class="dropdown-header">'
									+ '<span class="k-widget k-header">Photo</span>'
									+ '<span class="k-widget k-header">Contact info</span>'
									+ '</div>',
							template : '<table><tr><td rowspan=2><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.personId#'/>" width=40 height=55 alt=\"No Image to Display\" /></span></td>'
									+ '<td align="left"><span class="k-state-default"><b>#: data.personName #</b></span><br>'
									+ '<span class="k-state-default"><i>#: data.accountNumber #</i></span><br>'
									+ '<span class="k-state-default"><i>#: data.personType #</i></span></td></tr></table>',

							change : function() {

							}
						});
		$("#accountNo").data("kendoComboBox").value("");
	}

	function previousdate() {

		var newvalidfpre = $("#previousbillDate").val();

		var newvalid = newvalidfpre.split("/");
		var month = "";
		var year = "";
		var date = "";
		for (var i = 0; i <= newvalid.length; i++) {
			month = (parseFloat(newvalid[1]) - 1);
			date = (parseFloat(newvalid[0]) + 1);
			year = newvalid[2];
		}
		var datepicker = $("#presentbilldate").data("kendoDatePicker");
		datepicker.min(new Date(year, month, date));

	}
	function getPreviousStatusAlert() {
		var billId = $("#accountNo").val();
		var rateName = $("input[name=rateName]").data("kendoDropDownList")
				.text();
		var serviceId = $("#rateName").val();
		$.ajax({
			type : "GET",
			url : "./bill/getPreviousStatusAlert",
			dataType : "text",
			data : {
				rateName : rateName,
				billId : billId,
				serviceId : serviceId,
			},
			success : function(response) {

				//alert(response);

			}

		});

	}

	function getpreviousdddate() {

		var accountId = $("#accountNodd").val();
		var serviceNmae = $("input[name=servicesNamedd]").data(
				"kendoDropDownList").text();

		$.ajax({
			type : "GET",
			url : "./bill/getpreviousdddate",
			dataType : "text",
			data : {

				accountId : accountId,
				serviceNmae : serviceNmae,
			},
			success : function(response) {
				//previosdddate
				var olddatefrom = $('#previosdddate').val(response).val();
				newvalidf = olddatefrom.split("-").reverse().join("/") + " ";
				$('#previosdddate').val(newvalidf);
			}
		});
	}

	function getUnits() {
		var meterFactor = $("#meterFactor").val();
		var previousReading = $("#previousReading").val();
		var presentReading = $("#presentReading").val();
		var presentStaus = $("#presentStaus").val();
		var rateName = $("input[name=rateName]").data("kendoDropDownList")
				.text();
		var presentbilldate = $("#presentbilldate").val();
		var accountId = $("#accountNo").val();
		var previousbilldate = $("#previousbillDate").val();
		var serviceId = $("#rateName").val();
		if (rateName != "Solid Waste" && presentStaus != "Meter Change") {
			$
					.ajax({
						type : "GET",
						url : "./bill/getunits",

						data : {

							presentReading : presentReading,
							meterFactor : meterFactor,

							previousReading : previousReading,

							presentStaus : presentStaus,
							presentbilldate : presentbilldate,
							accountId : accountId,
							rateName : rateName,
							serviceId : serviceId,
						},
						success : function(response) {
							var i = 0;

							for (i = 0; i <= response.length; i++) {
								//

							}

							if (response[0] == 'Present') {
								alert("Present Reading will be more than Previous Reding");
								$("#presentReading").val("");
							} else {
								var unit = $('#units').data(
										"kendoNumericTextBox");
								unit.value($('#units').val(response[0]).val());
								var billto = $('#avgunits').data(
										"kendoNumericTextBox");
								billto.value($('#avgunits').val(response[1])
										.val());
								getAvgAlert();
							}

						}
					});
		} else if (rateName != "Solid Waste" && presentStaus == "Meter Change") {

			$.ajax({
				type : "GET",
				url : "./bill/getMeterChangeReading",
				dataType : "json",
				data : {
					presentStaus : presentStaus,
					rateName : rateName,
					presentbilldate : presentbilldate,
					accountId : accountId,
					previousbilldate : previousbilldate,
					previousReading : previousReading,
					presentReading : presentReading,
					serviceId : serviceId,
				},
				success : function(response) {

					$("input[name=units]").data("kendoNumericTextBox").value(
							response.Units);
					avgUnitForMeterchange();
					$("input[name=meterFactor]").data("kendoNumericTextBox")
							.value(response.meterConstant);
				}
			});

		}

	}
	function generateBill() {
		var result = securityCheckForActionsForStatus("./BillGeneration/GenerateBills/GenerateBillButton");
		if (result == "success") {
			var accountNo = $("input[name=accountNo]").data("kendoComboBox")
					.text();
			var accountId = $("#accountNo").val();
			var eltariffId = $("#rateName").val();
			var rateName = $("input[name=rateName]").data("kendoDropDownList")
					.text();
			var presentReading = $("#presentReading").val();
			var meterFactor = $("#meterFactor").val();
			var previousReading = $("#previousReading").val();
			var previousbillDate = $("#previousbillDate").val();
			var presentbilldate = $("#presentbilldate").val();
			var previousStaus = $("#previousStaus").val();
			var presentStaus = $("#presentStaus").val();
			var sWasteDate = $("#previousSWasteDate").val();
			var units = $("#units").val();
			var presentTod1 = $("#presentTod1").val();
			var presentTod2 = $("#presentTod2").val();
			var presentTod3 = $("#presentTod3").val();
			var dgUnit = $("#dgUnit").val();
			var presentDgReading = $("#dgReading").val();
			var dgmeterconstant = $("#dgmeterFactor").val();
			var previousDgreading = $("#previousDGReading").val();
			var pfReading = $("#pfReading").val();
			var connectedLoad = $("#connectedLoad").val();
			var postType = $("#postType").val();

			$('#commitplaceholder').show();
			$('#calcu').hide();

			$
					.ajax({

						url : "./bill/generateBill",
						type : "GET",
						dataType : "text",
						data : {
							rateName : rateName,
							accountNo : accountNo,
							accountId : accountId,
							eltariffId : eltariffId,
							presentbilldate : presentbilldate,
							presentReading : presentReading,
							meterFactor : meterFactor,
							previousbillDate : previousbillDate,
							previousReading : previousReading,
							previousStaus : previousStaus,
							presentStaus : presentStaus,
							sWasteDate : sWasteDate,
							units : units,
							presentTod1 : presentTod1,
							presentTod3 : presentTod3,
							presentTod2 : presentTod2,
							dgUnit : dgUnit,
							previousDgreading : previousDgreading,
							presentDgReading : presentDgReading,
							dgmeterconstant : dgmeterconstant,
							pfReading : pfReading,
							connectedLoad : connectedLoad,
							postType : postType,
						},

						success : function(response) {
							$('#commitplaceholder').hide();
							$('#calcu').show();
							alert(response);
							$('#billGrid').data('kendoGrid').refresh();
							$('#billGrid').data('kendoGrid').dataSource.read();
							close();
							var combobox = $('#generateDropDown').data(
									"kendoComboBox");
							if (combobox != null) {
								combobox.value("");
							}

						}
					});
		}
	}
	function raisedepositDemand() {
		var accountNo = $("input[name=accountNodd]").data("kendoComboBox")
				.text();
		var accountId = $("#accountNodd").val();
		var serviceID = $("#servicesNamedd").val();
		var serviceName = $("input[name=servicesNamedd]").data(
				"kendoDropDownList").text();
		var previousdate = $("#previosdddate").val();
		var presentdate = $("#presentddbilldate").val();
		$.ajax({

			url : "./bill/raiseDepositDemand",
			type : "GET",
			data : {
				accountId : accountId,
				accountNo : accountNo,
				serviceID : serviceID,
				serviceName : serviceName,
				previousdate : previousdate,
				presentdate : presentdate
			},

			success : function(response) {
				alert("Your Deposit Created Successfully");
				$('#billGrid').data('kendoGrid').refresh();
				$('#billGrid').data('kendoGrid').dataSource.read();
				ddDiveClose();
				var combobox = $('#generateDropDown').data("kendoComboBox");
				if (combobox != null) {
					combobox.value("");
				}
			}
		});

	}
	function close() {
		var todcal = $("#generateBillDialog");
		var dropdownlist = $("#rateName").data("kendoDropDownList");
		dropdownlist.value("");

		var dropdownlist1 = $("#presentStaus").data("kendoDropDownList");
		dropdownlist1.value("");
		var previousreading = $("#previousReading").data("kendoNumericTextBox");
		previousreading.value("");
		$("#presentStaus").val("");
		var presentreading = $("#presentReading");
		presentreading.val("");
		var meterconstant = $("#meterFactor").data("kendoNumericTextBox");
		meterconstant.value("");
		$('#previousbillDate').val("");
		var previousstatus = $("#previousStaus");
		previousstatus.val("");

		todcal.kendoWindow({
			width : "auto",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Generate Bill"
		}).data("kendoWindow").center().close();

		todcal.kendoWindow("close");

		$("#presenthide").show();

	}

	function closeWindow(e) {
		var gridBillGeneration = $("#billGrid").data("kendoGrid");
		if (gridBillGeneration != null) {
			gridBillGeneration.cancelRow();
		}
	}

	function parse(response) {
		$.each(response, function(idx, elem) {
			if (elem.elBillDate === null) {
				elem.elBillDate = "";
			} else {
				elem.elBillDate = kendo.parseDate(new Date(elem.elBillDate),
						'dd/MM/yyyy HH:mm');
			}
		});
		return response;
	}
	function cancelAllBill() {
		var serviceTypeCancel = $("#serviceTypeCancel").val();
		var presentdateCancel = $("#presentdatecancelBill").val();
		$('#commitplaceholderNewCancel').show();

		$('#cancelBill').hide();
		$.ajax({
			url : "./electricityBills/cancelAllBills",
			type : "POST",
			dataType : "text",
			data : {
				serviceTypeCancel : serviceTypeCancel,
				presentdateCancel : presentdateCancel,
			},
			success : function(response) {
				alert("Bill Cancelled Successfully");
				$('#commitplaceholderNewCancel').hide();
				$('#cancelBill').show();
				cancelClose();
				window.location.reload();

			}

		});
	}
	function cancelClose() {

		var todcal = $("#cancelAlldiv");
		var dropdownlist1 = $("#serviceTypeCancel").data("kendoDropDownList");
		dropdownlist1.value("");
		$("#presentdatecancelBill").val("");
		todcal.kendoWindow({
			width : "auto",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Cancel Bills"
		}).data("kendoWindow").center().close();
		todcal.kendoWindow("close");
	}
	function approveAllBill() {
		var serviceType = $("#serviceTypeApprove").val();
		var presentdate = $("#presentdateapproveBill").val();
		$('#commitplaceholderNewApprove').show();

		$('#approveBill').hide();
		$.ajax({
			url : "./electricityBills/approveAllBillToLedger",
			type : "POST",
			dataType : "text",
			data : {
				serviceType : serviceType,
				presentdate : presentdate,
			},
			success : function(response) {
				alert("Approved All Bills")
				$('#commitplaceholderNewApprove').hide();
				$('#approveBill').show();
				approveClose();
				window.location.reload();
			}

		});
	}
	function approveClose() {

		var todcal = $("#approveAlldiv");
		var dropdownlist1 = $("#serviceTypeApprove").data("kendoDropDownList");
		dropdownlist1.value("");
		$("#presentdateapproveBill").val("");

		todcal.kendoWindow({
			width : "auto",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Approve Bills"
		}).data("kendoWindow").center().close();
		todcal.kendoWindow("close");
	}
	function postAllBill() {
		var serviceTypePost = $("#serviceTypePost").val();
		var presentdatePost = $("#presentdatepostBillLedger").val();
		$('#commitplaceholderNewPost').show();
		$('#postBill').hide();

		$.ajax({

			type : "POST",
			url : "./electricityBills/postAllBillToLedger",
			dataType : "json",
			data : {
				serviceTypePost : serviceTypePost,
				presentdatePost : presentdatePost,
			},
			success : function(response) {
				alert("Bill Posted Successfully");
				$('#commitplaceholderNewPost').hide();
				$('#postBill').show();
				postClose();
				window.location.reload();

			}

		});
	}

	function amountrangeselect() {

		var filamount = $("#filterAmount").val();
		if (filamount == 'Range') {
			$("#amou1").show();
			$("#amou2").show();

		} else {
			$("#amou1").show();
			$("#amou2").hide();
		}

	}

	function postClose() {

		var todcal = $("#postAlldiv");
		var dropdownlist1 = $("#serviceTypePost").data("kendoDropDownList");
		dropdownlist1.value("");
		$("#presentdatepostBillLedger").val("");

		todcal.kendoWindow({
			width : "auto",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Approve Bills"
		}).data("kendoWindow").center().close();
		todcal.kendoWindow("close");
	}
	var SelectedRowId = "";
	var billStatus = "";
	var billServiceType = "";
	var totalBillAmount = "";
	var billPostType = "";
	var netAmount = "";
	function onChangeBillsList(arg) {
		var gview = $("#billGrid").data("kendoGrid");
		var selectedItem = gview.dataItem(gview.select());
		SelectedRowId = selectedItem.elBillId;
		billStatus = selectedItem.status;
		billServiceType = selectedItem.typeOfService;
		totalBillAmount = selectedItem.billAmount;
		billPostType = selectedItem.postType;
		netAmount = selectedItem.netAmount;
		this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
		$(".k-master-row.k-state-selected").show();
	}

	$("#billGrid").on("click", ".k-grid-approveAllBills", function(e) {
		var bbDialog = $("#approveAlldiv");
		bbDialog.kendoWindow({
			width : "auto",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Approve All Bills"
		}).data("kendoWindow").center().open();

		bbDialog.kendoWindow("open");

	});
	$("#billGrid").on("click", ".k-grid-cancelAllBills", function(e) {
		var bbDialog = $("#cancelAlldiv");
		bbDialog.kendoWindow({
			width : "auto",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Cancel All Bills"
		}).data("kendoWindow").center().open();

		bbDialog.kendoWindow("open");
	});

	$("#billGrid").on("click", ".k-grid-postAllBills", function(e) {
		var bbDialog = $("#postAlldiv");
		bbDialog.kendoWindow({
			width : "auto",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Post All Bills"
		}).data("kendoWindow").center().open();

		bbDialog.kendoWindow("open");
	});

	$("#billGrid").on("click", ".k-grid-postAllBillsToTally", function(e) {

		/* 		var result = securityCheckForActionsForStatus("./BillGeneration/GenerateBills/postAllButton");
		 if (result == "success") { */
		/* var month=$('#monthpicker').val();
		 alert(month); */

		var bbDialog = $("#postAllTallydiv");
		bbDialog.kendoWindow({
			width : "auto",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Post All Bills"
		}).data("kendoWindow").center().open();

		bbDialog.kendoWindow("open");
		/* } */
	});

	function dropDownChecksEditor(container, options) {
		var res = (container.selector).split("=");
		var attribute = res[1].substring(0, res[1].length - 1);

		$(
				'<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '" name = "'+attribute+'" id = "'+attribute+'Id"/>')
				.appendTo(container).kendoDropDownList({
					optionLabel : {
						text : "Select",
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

	function dropDownChecksEditor(container, options) {
		var res = (container.selector).split("=");
		var attribute = res[1].substring(0, res[1].length - 1);

		$(
				'<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '" name = "'+attribute+'" id = "'+attribute+'Id"/>')
				.appendTo(container).kendoDropDownList({
					optionLabel : {
						text : "Select",
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

	var setApCode = "";
	function electricityBillEvent(e) {
		/***************************  to remove the id from pop up  **********************/
		$('div[data-container-for="elBillId"]').remove();
		$('label[for="elBillId"]').closest('.k-edit-label').remove();

		$(".k-window").css({
			"top" : "100px"
		});

		$(".k-edit-field").each(function() {
			$(this).find("#temPID").parent().remove();
		});

		$('label[for="status"]').parent().hide();
		$('div[data-container-for="status"]').hide();

		$('label[for="propertyNumber"]').parent().hide();
		$('div[data-container-for="propertyNumber"]').hide();

		$('label[for="tallyStatus"]').parent().hide();
		$('div[data-container-for="tallyStatus"]').hide();

		$('label[for="billDate"]').parent().hide();
		$('div[data-container-for="billDate"]').hide();

		$('label[for="accountNumber"]').parent().hide();
		$('div[data-container-for="accountNumber"]').hide();

		$('label[for="personName"]').parent().hide();
		$('div[data-container-for="personName"]').hide();

		$('label[for="billAmount"]').parent().hide();
		$('div[data-container-for="billAmount"]').hide();

		$('label[for="arrearsAmount"]').parent().hide();
		$('div[data-container-for="arrearsAmount"]').hide();

		$('label[for="avgAmount"]').parent().hide();
		$('div[data-container-for="avgAmount"]').hide();

		$('label[for="netAmount"]').parent().hide();
		$('div[data-container-for="netAmount"]').hide();

		$('label[for="billNo"]').parent().hide();
		$('div[data-container-for="billNo"]').hide();

		$('label[for="billType"]').parent().hide();
		$('div[data-container-for="billType"]').hide();

		$('label[for="avgCount"]').parent().hide();
		$('div[data-container-for="avgCount"]').hide();

		$('label[for="billMonth"]').parent().hide();
		$('div[data-container-for="billMonth"]').hide();

		$('label[for="fromDate"]').parent().hide();
		$('div[data-container-for="fromDate"]').hide();

		$('label[for="elBillDate"]').parent().hide();
		$('div[data-container-for="elBillDate"]').hide();

		$('label[for="billDueDate"]').parent().hide();
		$('div[data-container-for="billDueDate"]').hide();

		$('label[for="postType"]').parent().hide();
		$('div[data-container-for="postType"]').hide();

		$('label[for="advanceClearedAmount"]').parent().hide();
		$('div[data-container-for="advanceClearedAmount"]').hide();

		$('label[for="createdBy"]').parent().hide();
		$('div[data-container-for="createdBy"]').hide();

		/************************* Button Alerts *************************/
		if (e.model.isNew()) {
			securityCheckForActions("./BillGeneration/GenerateBills/GenerateBackBillButton");
			$(".k-window-title").text("Add New Back Bill Details");
			$(".k-grid-update").text("Save");
		} else {
			setApCode = $('input[name="elBillId"]').val();
			$(".k-window-title").text("Edit Back Bill Details");
		}
	}

	$("#billGrid").on("click", ".k-grid-Clear_Filter", function() {
		//custom actions
		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
		var gridPets = $("#billGrid").data("kendoGrid");
		gridPets.dataSource.read();
		gridPets.refresh();
	});

	/********************** to hide the child table id ***************************/
	function billLineItems(e) {

		$('div[data-container-for="elBillId"]').remove();
		$('label[for="elBillId"]').closest('.k-edit-label').remove();

		$('div[data-container-for="status"]').remove();
		$('label[for="status"]').closest('.k-edit-label').remove();

		$('div[data-container-for="createdBy"]').remove();
		$('label[for="createdBy"]').closest('.k-edit-label').remove();

		$('div[data-container-for="elBillLineId"]').remove();
		$('label[for="elBillLineId"]').closest('.k-edit-label').remove();

		$('div[data-container-for="transactionCode"]').remove();
		$('label[for="transactionCode"]').closest('.k-edit-label').remove();

		if (e.model.isNew()) {
			securityCheckForActions("./BillGeneration/GenerateBills/BillLineItem/createButton");
			$(".k-window-title").text("Add New Bill Line Items");
			$(".k-grid-update").text("Save");

		} else {
			$(".k-window-title").text("Edit Sub Bill Line Items");
			$(".k-grid-update").text("Update");
		}
	}

	function billParaMeter(e) {
		$('div[data-container-for="status"]').remove();
		$('label[for="status"]').closest('.k-edit-label').remove();

		$('div[data-container-for="createdBy"]').remove();
		$('label[for="createdBy"]').closest('.k-edit-label').remove();

		$('div[data-container-for="bvmName"]').remove();
		$('label[for="bvmName"]').closest('.k-edit-label').remove();

		$('div[data-container-for="elBillParameterId"]').remove();
		$('label[for="elBillParameterId"]').closest('.k-edit-label').remove();

		/*  $('div[data-container-for="bvmName"]').remove();
		 $('label[for="bvmName"]').closest('.k-edit-label').remove();  */

		if (e.model.isNew()) {
			$(".k-window-title").text("Add New Bill Parameters");
			$(".k-grid-update").text("Save");
		} else {
			$(".k-window-title").text("Edit Sub Bill Parameters");
			$(".k-grid-update").text("Update");
		}
	}

	function onRequestEnd(r) {
		if (typeof r.response != 'undefined') {
			if (r.response.status == "FAIL") {

				errorInfo = "";

				for (var s = 0; s < r.response.result.length; s++) {
					errorInfo += (s + 1) + ". "
							+ r.response.result[s].defaultMessage + "<br>";

				}

				if (r.type == "create") {
					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Creating the Bill Details<br>" + errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});

				}

				if (r.type == "update") {
					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Updating the Bill Details<br>" + errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
				}

				$('#billGrid').data('kendoGrid').refresh();
				$('#billGrid').data('kendoGrid').dataSource.read();
				return false;
			}

			if (r.response.status == "CHILD") {

				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Can't Delete Bill Details, Child Record Found");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				$('#billGrid').data('kendoGrid').refresh();
				$('#billGrid').data('kendoGrid').dataSource.read();
				return false;
			}

			else if (r.response.status == "INVALID") {

				errorInfo = "";

				errorInfo = r.response.result.invalid;

				if (r.type == "create") {
					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Creating the Bill Details<br>" + errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
				}
				$('#billGrid').data('kendoGrid').refresh();
				$('#billGrid').data('kendoGrid').dataSource.read();
				return false;
			}

			else if (r.response.status == "EXCEPTION") {

				errorInfo = "";

				errorInfo = r.response.result.exception;

				if (r.type == "create") {

					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Creating the Bill Details<br>" + errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
				}

				if (r.type == "update") {
					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Updating the Bill Details<br>" + errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
				}

				$('#billGrid').data('kendoGrid').refresh();
				$('#billGrid').data('kendoGrid').dataSource.read();
				return false;
			}

			else if (r.type == "create") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Bill Details created successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				var grid = $("#billGrid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();

			}

			else if (r.type == "update") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Bill Details updated successfully");

				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});

				var grid = $("#billGrid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			}
		}
	}

	/************************************* for inner rate slab request *********************************/

	function billLineItemsOnRequestEnd(e) {
		if (typeof e.response != 'undefined') {
			if (e.response.status == "FAIL") {
				errorInfo = "";

				for (var k = 0; k < e.response.result.length; k++) {
					errorInfo += (k + 1) + ". "
							+ e.response.result[k].defaultMessage + "<br>";

				}

				if (e.type == "create") {
					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Assigning Permission to AccessCard<br>"
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

				else if (e.type == "update") {
					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Updating the Permission to AccessCard<br>"
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

				$('#gridAccessCardPermission_' + SelectedRowId).data().kendoGrid.dataSource
						.read({
							personId : SelectedAccessCardId
						});
				return false;
			}

			else if (e.type == "create") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Bill Line Itme Created Successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				$('#billGrid').data('kendoGrid').refresh();
				$('#billGrid').data('kendoGrid').dataSource.read();
			}

			else if (e.type == "update") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Bill Line Itme updated successfully");
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
				$("#alertsBox").html("Bill Line Itme delete successfully");
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
	function billParametersOnRequestEnd(e) {
		if (typeof e.response != 'undefined') {
			if (e.response.status == "FAIL") {
				errorInfo = "";

				for (var k = 0; k < e.response.result.length; k++) {
					errorInfo += (k + 1) + ". "
							+ e.response.result[k].defaultMessage + "<br>";

				}

				if (e.type == "create") {
					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Assigning Permission to AccessCard<br>"
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

				else if (e.type == "update") {
					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Updating the Permission to AccessCard<br>"
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

				$('#gridAccessCardPermission_' + SelectedRowId).data().kendoGrid.dataSource
						.read({
							personId : SelectedAccessCardId
						});
				return false;
			}

			else if (e.type == "create") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Bill Parameters Created Successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				$('#billGrid').data('kendoGrid').refresh();
				$('#billGrid').data('kendoGrid').dataSource.read();
			}

			else if (e.type == "update") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Bill Parameters updated successfully");
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
				$("#alertsBox").html("Bill Parameters delete successfully");
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

	$("#billGrid").on(
			"click",
			".k-grid-generateBill",
			function(e) {
				var todcal = $("#generateBillDialog");
				todcal.kendoWindow({
					width : "auto",
					height : "auto",
					modal : true,
					draggable : true,
					position : {
						top : 100
					},
					title : "Generate Bill"
				}).data("kendoWindow").center().open();

				todcal.kendoWindow("open");
				$("#presenthide").show();
				$("#previousDgReading").hide();
				$("#presentdiv").show();

				var dropdownlist = $("#rateName").data("kendoDropDownList");
				dropdownlist.value("");

				var dropdownlist1 = $("#presentStaus")
						.data("kendoDropDownList");
				dropdownlist1.value("");
				var previousreading = $("#previousReading").data(
						"kendoNumericTextBox");
				previousreading.value("");
				var presentreading = $("#presentReading");
				presentreading.val("");
				var meterconstant = $("#meterFactor").data(
						"kendoNumericTextBox");
				meterconstant.value("");
				var meterconstant = $("#units").data("kendoNumericTextBox");
				meterconstant.value("");
				$("#presentbilldate").val("");
				$("#avgunits").val("");
				$("#previousbillDate").val("");
				$("#previousStaus").val("");
				$('#presentTod1').val("");
				$('#presentTod2').val("");
				$('#presentTod3').val("");
				var combobox = $('#accountNo').data("kendoComboBox");
				$("#todDiv").hide();
				$('#dgUnit').val("");
				$('#dgtr').hide();

				if (combobox != null) {

					combobox.value("");
				}

			});
	function openGenerateBill() {
		var todcal = $("#generateBillDialog");
		todcal.kendoWindow({
			width : "auto",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Generate Bill"
		}).data("kendoWindow").center().open();

		todcal.kendoWindow("open");
		$("#presenthide").show();
		$("#previousDgReading").hide();
		$("#presentdiv").show();

		var dropdownlist = $("#rateName").data("kendoDropDownList");
		dropdownlist.value("");

		var dropdownlist1 = $("#presentStaus").data("kendoDropDownList");
		dropdownlist1.value("");
		var previousreading = $("#previousReading").data("kendoNumericTextBox");
		previousreading.value("");
		var presentreading = $("#presentReading");
		presentreading.val("");
		var meterconstant = $("#meterFactor").data("kendoNumericTextBox");
		meterconstant.value("");
		var meterconstant = $("#units").data("kendoNumericTextBox");
		meterconstant.value("");
		$("#presentbilldate").val("");
		$("#avgunits").val("");
		$("#previousbillDate").val("");
		$("#previousStaus").val("");
		$('#presentTod1').val("");
		$('#presentTod2').val("");
		$('#presentTod3').val("");
		var combobox = $('#accountNo').data("kendoComboBox");
		$("#todDiv").hide();
		$('#dgUnit').val("");
		$('#dgtr').hide();

		if (combobox != null) {

			combobox.value("");
		}

		var presentreadingdg = $("#dgReading");
		presentreadingdg.val("");
		var comboboxprop = $('#propertyNo').data("kendoComboBox");
		var objectA = {};
		if (comboboxprop != null) {

			comboboxprop.value("");
		}
	}
	function openBtBill() {
		var result = securityCheckForActionsForStatus("./BillGeneration/GenerateBills/generateTeleBroadBandBill");
		if (result == "success") {
			var btDialog = $("#generateBTBillDialog");
			btDialog.kendoWindow({
				width : "400",
				height : "100",
				modal : true,
				draggable : true,
				position : {
					top : 100
				},
				title : "Generate Bill"
			}).data("kendoWindow").center().open();

			btDialog.kendoWindow("open");

		}
	}
	function openRaisDD() {
		var result = securityCheckForActionsForStatus("./BillGeneration/GenerateBills/raiseDepositButton");
		if (result == "success") {
			var bbDialog = $("#ddDiv");

			bbDialog.kendoWindow({
				width : "auto",
				height : "auto",
				modal : true,
				draggable : true,
				position : {
					top : 100
				},
				title : "Generate Deposit Demand"
			}).data("kendoWindow").center().open();

			bbDialog.kendoWindow("open");
		}

	}
	function openCamBill() {
		postCAMBillClose();

		var dropdownlist2 = $("#flats").data("kendoDropDownList");
		dropdownlist2.value("");
		var dropdownlist1 = $("#blockNameCam").data("kendoDropDownList");
		dropdownlist1.value("");
		var dropdownlist3 = $("#propertyTypeCam").data("kendoDropDownList");
		dropdownlist3.value("");

		var presentreading = $("#camBillDate");
		presentreading.val("");
		var EmptyArray = new Array();
		var ddlMulti = $('#propertyNameCam').data('kendoMultiSelect');
		ddlMulti.value(EmptyArray);
		var camDialog = $("#generateCamBill");
		camDialog.kendoWindow({
			width : "250",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 50
			},
			title : "Generate CAM Bills"
		}).data("kendoWindow").center().open();
		camDialog.kendoWindow("open");

	}
	
	function openWaterBill() {
		postWaterBillClose();

		var dropdownlist2 = $("#flats1").data("kendoDropDownList");
		dropdownlist2.value("");
		var dropdownlist1 = $("#blockNameWater").data("kendoDropDownList");
		dropdownlist1.value("");
		var dropdownlist3 = $("#propertyTypeWater").data("kendoDropDownList");
		dropdownlist3.value("");

		var presentreading = $("#waterBillDate");
		presentreading.val("");
		var EmptyArray = new Array();
		var ddlMulti = $('#propertyNameWater').data('kendoMultiSelect');
		ddlMulti.value(EmptyArray);
		var camDialog = $("#generateWaterBill");
		camDialog.kendoWindow({
			width : "250",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 50
			},
			title : "Generate Water Bills"
		}).data("kendoWindow").center().open();
		camDialog.kendoWindow("open");

	}
	$("#billGrid").on("click", ".k-grid-filterNetAmount", function(e) {
		var bbDialog = $("#filterNetAmountdiv");
		bbDialog.kendoWindow({
			width : "auto",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Filter For NetAmount"
		}).data("kendoWindow").center().open();

		bbDialog.kendoWindow("open");

	});
	function printAllBill() {
		var bbDialog = $("#printBillingDiv");
		bbDialog.kendoWindow({
			width : "auto",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Print  Bill"
		}).data("kendoWindow").center().open();

		bbDialog.kendoWindow("open");

		var dropdownlist2 = $("#blockNameAmr").data("kendoDropDownList");
		dropdownlist2.value("");
		var dropdownlist1 = $("#propertyType").data("kendoDropDownList");
		dropdownlist1.value("");

		var presentreading = $("#presentdateAmr");
		presentreading.val("");
		var EmptyArray = new Array();
		var ddlMulti = $('#propertyNameAmr').data('kendoMultiSelect');
		ddlMulti.value(EmptyArray);
	}

	function openAmrBill() {
		var bbDialog = $("#amrBillingDiv");
		bbDialog.kendoWindow({
			width : "auto",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Generate  Bill"
		}).data("kendoWindow").center().open();

		bbDialog.kendoWindow("open");

		var dropdownlist2 = $("#blockNameAmr").data("kendoDropDownList");
		dropdownlist2.value("");
		var dropdownlist1 = $("#propertyType").data("kendoDropDownList");
		dropdownlist1.value("");

		var presentreading = $("#presentdateAmr");
		presentreading.val("");
		var EmptyArray = new Array();
		var ddlMulti = $('#propertyNameAmr').data('kendoMultiSelect');
		ddlMulti.value(EmptyArray);
	}
	function openBulkBill() {
		var bbDialog = $("#bulkBillingDiv");
		bbDialog.kendoWindow({
			width : "auto",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Generate Bulk Bill"
		}).data("kendoWindow").center().open();

		bbDialog.kendoWindow("open");

		var dropdownlist1 = $("#serviceTypeList").data("kendoDropDownList");
		dropdownlist1.value("");
		var dropdownlist2 = $("#blockName").data("kendoDropDownList");
		dropdownlist2.value("");
		var presentreading = $("#consumptionUnit");
		presentreading.val("");
		var presentreading = $("#presentdate");
		presentreading.val("");
		var EmptyArray = new Array();
		var ddlMulti = $('#propertyName').data('kendoMultiSelect');
		ddlMulti.value(EmptyArray);
	}
	$("#billGrid")
			.on(
					"click",
					".k-grid-generateBTBill",
					function(e) {
						//alert("Open");
						var result = securityCheckForActionsForStatus("./BillGeneration/GenerateBills/generateTeleBroadBandBill");
						if (result == "success") {
							var btDialog = $("#generateBTBillDialog");
							btDialog.kendoWindow({
								width : "400",
								height : "100",
								modal : true,
								draggable : true,
								position : {
									top : 100
								},
								title : "Generate Bill"
							}).data("kendoWindow").center().open();

							btDialog.kendoWindow("open");

						}
					});

	$("#billGrid")
			.on(
					"click",
					".k-grid-uploadBatchFile",
					function(e) {

						var result = securityCheckForActionsForStatus("./BillGeneration/GenerateBills/uploadBatchFile");
						if (result == "success") {
							var xlsDialog = $("#uploadBatchFileDialog");
							xlsDialog.kendoWindow({
								width : "400",
								height : "150",
								modal : true,
								draggable : true,
								position : {
									top : 80
								},
								title : "Upload Batch Bills"
							}).data("kendoWindow").center().open();
							xlsDialog.kendoWindow("open");
						}
					});

	$("#billGrid")
			.on(
					"click",
					".k-grid-uploadXMLFile",
					function(e) {

						var result = securityCheckForActionsForStatus("./BillGeneration/GenerateBills/uploadXMLFile");
						if (result == "success") {
							var xmlDialog = $("#uploadXMLFileDialog");
							xmlDialog.kendoWindow({
								width : "400",
								height : "150",
								modal : true,
								draggable : true,
								position : {
									top : 80
								},
								title : "Upload Batch Bills"
							}).data("kendoWindow").center().open();
							xmlDialog.kendoWindow("open");
						}
					});

	$("#billGrid")
			.on(
					"click",
					".k-grid-raiseDD",
					function(e) {
						//alert("Open");
						var result = securityCheckForActionsForStatus("./BillGeneration/GenerateBills/raiseDepositButton");
						if (result == "success") {
							var bbDialog = $("#ddDiv");

							bbDialog.kendoWindow({
								width : "auto",
								height : "auto",
								modal : true,
								draggable : true,
								position : {
									top : 100
								},
								title : "Generate Deposit Demand"
							}).data("kendoWindow").center().open();

							bbDialog.kendoWindow("open");
						}
						var dropdownlist1 = $("#servicesNamedd").data(
								"kendoDropDownList");
						dropdownlist1.value("");
						$("#previosdddate").val("");
						$("#presentddbilldate").val("");
						var combobox = $('#accountNodd').data("kendoComboBox");
						if (combobox != null) {

							combobox.value("");
						}

					});

	function ddDiveClose() {
		var bbDialog = $("#ddDiv");

		bbDialog.kendoWindow({
			width : "auto",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Generate Deposit Demand"
		}).data("kendoWindow").center().close();

		bbDialog.kendoWindow("close");
	}

	$("#billGrid").on("click", ".k-grid-generateCamBill", function(e) {

		postCAMBillClose();
		var camDialog = $("#generateCamBill");
		camDialog.kendoWindow({
			width : "250",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 50
			},
			title : "Generate CAM Bills"
		}).data("kendoWindow").center().open();
		camDialog.kendoWindow("open");

	});
	
	$("#billGrid").on("click", ".k-grid-generateWaterBill", function(e) {

		postWaterBillClose();
		var camDialog = $("#generateWaterBill");
		camDialog.kendoWindow({
			width : "250",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 50
			},
			title : "Generate Water Bills"
		}).data("kendoWindow").center().open();
		camDialog.kendoWindow("open");

	});
	/* ----------------------- tariff code drop down ----------------- */
	function transactionCodeEditer(container, options) {
		$(
				'<input name="Transaction Name" id="transactionName" data-text-field="transactionName" data-value-field="transactionCode" data-bind="value:' + options.field + '" required="true"/>')
				.appendTo(container)
				.kendoDropDownList(
						{
							autoBind : false,
							optionLabel : "Select",
							defaultValue : false,
							placeholder : "Select Transaction Name",
							dataSource : {
								transport : {
									read : "${toTransactionMasterComboBoxUrl}/"
											+ billPostType + "/"
											+ billServiceType + "/"
											+ SelectedRowId
								}
							},
							change : function(e) {
								if (this.value() && this.selectedIndex == -1) {
									alert("Transaction Name doesn't exist!");
									$("#transactionName").data("kendoComboBox")
											.value("");
								}
								transactionCode = this.dataItem().transactionCode;
							}
						});

		$('<span class="k-invalid-msg" data-for="Transaction Name"></span>')
				.appendTo(container);
	}

	function billParameterNameEditer(container, options) {
		$(
				'<input data-text-field="bvmName" name="Vendor Contracts" id="test" required="true" data-value-field="bvmId" data-bind="value:' + options.field + '"/>')
				.appendTo(container).kendoComboBox({
					placeholder : "Select Contract",
					dataSource : {
						transport : {
							read : "${toBillParaMeterMasterComboBoxUrl}"
						}
					},
				});
		$('<span class="k-invalid-msg" data-for="Vendor Contracts"></span>')
				.appendTo(container);
	}

	function billLineItemsDataBound(e) {
		if (billServiceType == "Others" || billPostType == "Deposit"
				|| billPostType == "Back Bill") {
			if (billStatus == "Approved" || billStatus == "Generated") {
				$(".k-grid-add", "#vertical_" + SelectedRowId).show();
			} else {
				$(".k-grid-add", "#vertical_" + SelectedRowId).hide();
			}
		} else {
			$(".k-grid-add", "#vertical_" + SelectedRowId).hide();
		}

		var data = this.dataSource.view();
		var sum = 0;
		for (var i = 0; i < data.length; i++) {
			row = this.tbody.children("tr[data-uid='" + data[i].uid + "']");
			sum = sum + data[i].balanceAmount;
		}
		$('#sumTotal_' + SelectedRowId).text(Math.round(sum * 100.0) / 100.0);
		$('#totlcst').show();

	}

	function cancelApproveBill() {
		var elBillId = "";
		var accountId = "";
		var serviceType = "";
		var gridParameter = $("#billGrid").data("kendoGrid");
		var selectedAddressItem = gridParameter
				.dataItem(gridParameter.select());
		elBillId = selectedAddressItem.elBillId;
		accountId = selectedAddressItem.accountId;
		serviceType = selectedAddressItem.typeOfService;
		var result = securityCheckForActionsForStatus("./BillGeneration/GenerateBills/cancelButton");
		if (result == "success") {
			$.ajax({
				type : "POST",
				url : "./electricityBills/cancelApproveBill/" + elBillId + "/"
						+ billStatus + "/" + accountId + "/" + "/"
						+ serviceType + "/",
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
					$('#billGrid').data('kendoGrid').dataSource.read();
				}
			});
		}
	}
	var flag = 0;
	var months = [];
	var values = [];

	var janCredit = "";
	var febCredit = "";
	var marCredit = "";
	var aprCredit = "";
	var mayCredit = "";
	var junCredit = "";
	var julCredit = "";
	var augCredit = "";
	var septCredit = "";
	var octCredit = "";
	var novCredit = "";
	var decCredit = "";

	var janMonth = "";
	var febMonth = "";
	var marMonth = "";
	var aprMonth = "";
	var mayMonth = "";
	var junMonth = "";
	var julMonth = "";
	var augMonth = "";
	var septMonth = "";
	var octMonth = "";
	var novMonth = "";
	var decMonth = "";

	function exportBill() {
		var result = securityCheckForActionsForStatus("./BillGeneration/GenerateBills/viewBillButton");
		if (result == "success") {
			window.open("./bill/getbilltablePDF/" + SelectedRowId);
		}

	}
	function generateBulkBill() {
		var propertyId = $("input[name=propertyName]").data("kendoMultiSelect")
				.value();

		var blocId = $("#blockName").val();
		var blockName = $("input[name=blockName]").data("kendoDropDownList")
				.text();
		var billCalcType = $("input[name=billCalcType]").data(
				"kendoDropDownList").text();

		var consumptionUnit = $("#consumptionUnit").val();
		var presentdate = $("#presentdate").val();
		var serviceTypeList = $("input[name=serviceTypeList]").data(
				"kendoDropDownList").text();
		var result = securityCheckForActionsForStatus("./BillGeneration/GenerateBills/bulkBillButton");
		if (result == "success") {
			$
					.ajax({

						url : "./bill/generateBulkBill",
						type : "GET",
						dataType : "text",

						data : {
							blocId : blocId,
							blockName : blockName,
							serviceTypeList : serviceTypeList,
							propertyId : "" + propertyId + "",
							consumptionUnit : consumptionUnit,
							presentdate : presentdate,
							billCalcType : billCalcType,
						},

						success : function(response) {
							alert(response);
							bulkBillClose();
							$('#billGrid').data('kendoGrid').refresh();
							$('#billGrid').data('kendoGrid').dataSource.read();

							var combobox = $('#generateDropDown').data(
									"kendoComboBox");
							if (combobox != null) {
								combobox.value("");
							}

						}
					});
		}

	}
	function generateAmrBill() {
		$("#amrCalculate").hide();
		$("#amrplaceholder").show();
		var propertyId = $("input[name=propertyNameAmr]").data(
				"kendoMultiSelect").value();
		var blocId = $("#blockNameAmr").val();
		var blockName = $("input[name=blockNameAmr]").data("kendoDropDownList")
				.text();
		var presentdate = $("#presentdateAmr").val();
		$.ajax({
			//url : "./bill/generateAmrBill",
			url : "./bill/generateAmrBillNew",
			type : "GET",
			dataType : "text",
			data : {
				blocId : blocId,
				blockName : blockName,
				propertyId : "" + propertyId + "",
				presentdate : presentdate,
			},
			success : function(response) {
				$("#amrCalculate").show();
				$("#amrplaceholder").hide();
				alert(response);
				closeAmr();
				$('#billGrid').data('kendoGrid').refresh();
				$('#billGrid').data('kendoGrid').dataSource.read();
				var combobox = $('#generateDropDown').data("kendoComboBox");
				if (combobox != null) {
					combobox.value("");
				}

			}
		});
	}

	function closeAmr() {
		var bbDialog = $("#amrBillingDiv");
		bbDialog.kendoWindow({
			width : "auto",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Generate  Bill"
		}).data("kendoWindow").center().close();

		bbDialog.kendoWindow("close");
	}
	function exportAmrBillData() {
		var propertyId = $("input[name=propertyNameAmr]").data(
				"kendoMultiSelect").value();
		var blocId = $("#blockNameAmr").val();
		var blockName = $("input[name=blockNameAmr]").data("kendoDropDownList")
				.text();
		var newvalidf = $("#presentdateAmr").val();
		var presentdate = newvalidf.split("/").join("-");
		//var pdate=kendo.toString(presentdate,'MMM yyyy');

		//window.open("/bill/ExportAmrreading/" + blocId + "/"+ blockName + "/" + propertyId+ "/" + presentdate); 
		window.open("./bill/ExportAmrreading/" + blocId + "/" + blockName + "/"
				+ propertyId + "/" + presentdate);

		/* $.ajax({
		 url : "./bill/ExportAmrreading",
		 type : "GET",
		 dataType : "json",
		 contentType: "application/json; charset=utf-8",
		 data : {
		 blocId : blocId,
		 blockName : blockName,
		 propertyId : "" + propertyId + "",
		 presentdate : presentdate,
		 },
		 success : function(response) {


		 }
		 }); */

	}

	function printAllBills() {
		$("#printBill").hide();
		$("#printplaceholder").show();
		var serviceType = $("input[name=serviceTypePrint]").data(
				"kendoDropDownList").text();
		var presentdate = $("#presentdateprintBill").val();
		var accountNo = $("#accountNoprint").val();

		var fromMonth = $("#fromdateprintBill").val();
		var typetoPrint = $("#typePrint").val();
		if (typetoPrint == 'All') {
			window.open("./bill/printAllBill/" + serviceType + "/"
					+ presentdate);

		} else {

			window.open("./bill/printAccontWiseBill/" + serviceType + "/"
					+ accountNo + "/" + presentdate + "/" + fromMonth);

		}
	}

	function getDuplicateBills() {

		var serviceType = $("input[name=serviceTypeDownload]").data(
				"kendoDropDownList").text();
		var presentdate = $("#presentdateDownload").val();
		$.ajax({
			url : "./electricityBills/getDuplicateBills/" + presentdate + "/"
					+ serviceType,

			type : 'POST',
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			success : function(result) {
				var grid = $("#billGrid").getKendoGrid();
				var data = new kendo.data.DataSource();
				grid.dataSource.data(result);
				grid.refresh();
			},
		});

		closedownloadBill();

	}
	function saveLatePayment() {

		var serviceType = $("input[name=serviceTypeDownload]").data(
				"kendoDropDownList").text();
		var presentdate = $("#presentdateDownload").val();
		$.ajax({
			url : "./electricityBills/saveLatePayment/" + presentdate + "/"
					+ serviceType,

			type : 'POST',
			dataType : "text",

			success : function(result) {
				alert("updated Successfully");
			},
		});

		closedownloadBill();

	}

	function downloadAllBills() {

		var serviceType = $("input[name=serviceTypeDownload]").data(
				"kendoDropDownList").text();
		var presentdate = $("#presentdateDownload").val();

		window.open("./bill/downloadAllBills/" + serviceType + "/"
				+ presentdate);
		closedownloadBill();
		/* $.ajax({
			url : "./bill/downloadAllBills",
			type : "GET",
			dataType : "json",
			contentType: "application/json; charset=utf-8",
			data : {
				
				serviceType : serviceType,				
				presentdate : presentdate,
			},
			success : function(response) {
				$("#downlodbutton").show();
				$("#downlodbuttonloder").hide();
				alert("Bill Downloadeded in "+response);
				closedownloadBill();
				$('#billGrid').data('kendoGrid').refresh();
				$('#billGrid').data('kendoGrid').dataSource.read();

			}
		}); */
	}
	function closedownloadBill() {
		var bbDialog = $("#downloadAllBillsDiv");
		bbDialog.kendoWindow({
			width : "auto",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Download  Bill"
		}).data("kendoWindow").center().close();

		bbDialog.kendoWindow("close");
	}

	$("#billGrid").on(
			"click",
			".k-grid-downloadAllBills",
			function(e) {
				var bbDialog = $("#downloadAllBillsDiv");
				bbDialog.kendoWindow({
					width : "auto",
					height : "auto",
					modal : true,
					draggable : true,
					position : {
						top : 100
					},
					title : "Download  Bill"
				}).data("kendoWindow").center().open();

				bbDialog.kendoWindow("open");

				var dropdownlist1 = $("#serviceTypeDownload").data(
						"kendoDropDownList");
				dropdownlist1.value("");
				var presentreading = $("#presentdateDownload");
				presentreading.val("");

			});

	$("#filterAmountDeatils").click(
			function() {

				var serviceTypeFilter = $("#serviceTypeFilter").val();
				var presentdate = $("#presentdatefilterAmount").val();
				var filterAmount = $("#filterAmount").val();
				if (serviceTypeFilter == "") {
					alert("Service Type is required");
					return false;
				} else if (presentdate == "") {
					alert("Month is required");
					return false;
				} else if (filterAmount == "") {
					alert("Please select filter Amount");
					return false;
				}
				var amount1 = $("#amount1").val();
				var amount2 = $("#amount2").val();
				if (filterAmount == "Range") {
					if (amount1 == "") {
						alert("Amount 1 is required");
						return false;
					} else if (amount2 == "") {
						alert("Amount 2 is required");
						return false;
					} else if (amount1 > amount2) {
						alert("Amount 1 should be less than Amount 2");
						return false;
					} else if (amount1 == amount2) {
						alert("Amount 2 should be greater than Amount 1");
						return false;
					}
				} else {
					if (amount1 == "") {
						alert("Amount 1 is required");
						return false;
					}
				}

				if (filterAmount == "Range") {

					$.ajax({
						url : "./bill/getfilter/" + serviceTypeFilter + "/"
								+ presentdate + "/" + filterAmount + "/"
								+ amount1 + "/" + amount2,
						type : 'GET',
						dataType : "json",
						async : false,
						contentType : "application/json; charset=utf-8",
						success : function(result) {

							filterclose();
							var grid = $("#billGrid").getKendoGrid();
							var data = new kendo.data.DataSource();
							grid.dataSource.data(result);
							grid.refresh();
						},

					});
				} else {

					$.ajax({
						url : "./info/getfilterDetails/" + serviceTypeFilter
								+ "/" + presentdate + "/" + filterAmount + "/"
								+ amount1,
						type : 'GET',
						dataType : "json",
						async : false,
						contentType : "application/json; charset=utf-8",
						success : function(result) {

							filterclose();
							var grid = $("#billGrid").getKendoGrid();
							var data = new kendo.data.DataSource();
							grid.dataSource.data(result);
							grid.refresh();
						},

					});
				}
			});
	function getPropertyNo() {
		var blockId = $('#blockName').val();
		var blockName = $("input[name=blockName]").data("kendoDropDownList")
				.text();
		$('#propmultiselect1').remove();
		$('#propmultiselect2').remove();
		$('#propmultiselect')
				.append(
						"<td id='propmultiselect1'>Property Name </td><td id='propmultiselect2'><input id='propertyName' name='propertyName'onchange='changeDataSource()'/> </td>");
		$("#propertyName").kendoMultiSelect({
			autoBind : false,
			dataValueField : "propertyId",
			dataTextField : "property_No",
			dataSource : {
				transport : {
					read : {
						url : "./bill/getPropertyNo?blockId=" + blockId,
					}
				}
			}
		});

	}
	function getPropertyNoAmr() {
		var blockId = $('#blockNameAmr').val();
		var blockName = $("input[name=blockNameAmr]").data("kendoDropDownList")
				.text();
		$('#propmultiselectAmr1').remove();
		$('#propmultiselectAmr2').remove();
		if (blockName == 'All Blocks') {
			$('#propmultiselectAmr').hide();
			$('#protypemultiselectAmr').hide();
			$('#propertyType').removeAttr('required');
			$('#propmultiselectAmr2').removeAttr('required');
		}

		$('#propmultiselectAmr')
				.append(
						"<td id='propmultiselectAmr1'>Property Name </td><td id='propmultiselectAmr2'><input id='propertyNameAmr' name='propertyNameAmr'/> </td>");
		$("#propertyNameAmr").kendoMultiSelect({
			autoBind : false,
			dataValueField : "propertyId",
			dataTextField : "property_No",
			dataSource : {
				transport : {
					read : {
						url : "./billAmr/getPropertyNo?blockId=" + blockId,
					}
				}
			}
		});

		var datepicker = $("#presentdateAmr").data("kendoDatePicker");
		datepicker.max(new Date());
		recur();
	}

	function getPropertyNoCam() {
		var blockId = $('#blockNameCam').val();
		var blockName = $("input[name=blockNameCam]").data("kendoDropDownList")
				.text();
		$('#propmultiselectCam1').remove();
		$('#propmultiselectCam2').remove();
		var flattype = $("#flats").val();
		//	./bill/printAccontWiseBill/" + serviceType + "/"+ accountNo+ "/" + presentdate + "/" + fromMonth);	
		var serviceType = "CAM";
		var month = $("#camBillDate").val();

		if (flattype == "Not Generated") {

			$('#propmultiselectCam')
					.append(
							"<td id='propmultiselectCam1'>Property Name </td><td id='propmultiselectCam2'><input id='propertyNameCam' name='propertyNameCam'/> </td>");
			$("#propertyNameCam")
					.kendoMultiSelect(
							{
								autoBind : false,
								dataValueField : "propertyId",
								dataTextField : "property_No",
								dataSource : {
									transport : {
										read : {
											url : "./billAmr/getPropertyNotGen?blockId="
													+ blockId
													+ "&serviceType="
													+ serviceType
													+ "&month="
													+ month,
										}
									}
								}
							});

			recurCam();
		} else {

			$('#propmultiselectCam')
					.append(
							"<td id='propmultiselectCam1'>Property Name </td><td id='propmultiselectCam2'><input id='propertyNameCam' name='propertyNameCam'/> </td>");
			$("#propertyNameCam").kendoMultiSelect({
				autoBind : false,
				dataValueField : "propertyId",
				dataTextField : "property_No",
				dataSource : {
					transport : {
						read : {
							url : "./billAmr/getPropertyNo?blockId=" + blockId,
						}
					}
				}
			});

			recurCam();
		}
	}

	function getPropertyNoWater() {
		var blockId = $('#blockNameWater').val();
		var blockName = $("input[name=blockNameWater]").data("kendoDropDownList")
				.text();
		$('#propmultiselectWater1').remove();
		$('#propmultiselectWater2').remove();
		var flattype = $("#flats1").val();
		//	./bill/printAccontWiseBill/" + serviceType + "/"+ accountNo+ "/" + presentdate + "/" + fromMonth);	
		var serviceType = "Water";
		var month = $("#waterBillDate").val();

		if (flattype == "Not Generated") {

			$('#propmultiselectWater')
					.append(
							"<td id='propmultiselectWater1'>Property Name </td><td id='propmultiselectWater2'><input id='propertyNameWater' name='propertyNameWater'/> </td>");
			$("#propertyNameWater")
					.kendoMultiSelect(
							{
								autoBind : false,
								dataValueField : "propertyId",
								dataTextField : "property_No",
								dataSource : {
									transport : {
										read : {
											url : "./billAmr/getPropertyNotGen?blockId="
													+ blockId
													+ "&serviceType="
													+ serviceType
													+ "&month="
													+ month,
										}
									}
								}
							});

			recurWater();
		} else {

			$('#propmultiselectWater')
					.append(
							"<td id='propmultiselectWater1'>Property Name </td><td id='propmultiselectWater2'><input id='propertyNameWater' name='propertyNameWater'/> </td>");
			$("#propertyNameWater").kendoMultiSelect({
				autoBind : false,
				dataValueField : "propertyId",
				dataTextField : "property_No",
				dataSource : {
					transport : {
						read : {
							url : "./billAmr/getPropertyNo?blockId=" + blockId,
						}
					}
				}
			});

			recurWater();
		}
	}
	
	function selectallprop() {

		var mul = $("#propertyNameAmr").text();
		var propertyType = $("input[name=propertyType]").data(
				"kendoDropDownList").text();
		var multiSelect = $("#propertyNameAmr").data("kendoMultiSelect");

		if (propertyType == 'All') {

			//alert(multiSelect.dataSource.data().length);
			if (multiSelect.dataSource.data().length == 0) {
				var dropdownlist1 = $("#propertyType")
						.data("kendoDropDownList");
				dropdownlist1.value("");
				// $("#propmultiselectAmr").hide();

			} else {
				var selectedValues = "";
				var strComma = "";
				for (var i = 0; i < multiSelect.dataSource.data().length; i++) {
					var item = multiSelect.dataSource.data()[i];

					selectedValues += strComma + item.propertyId;
					//alert(selectedValues);
					strComma = ",";
				}
				multiSelect.value(selectedValues.split(","));
			}
		} else {
			//  var multiSelect = $("#propertyNameAmr").data("kendoMultiSelect");
			multiSelect.value(" ");
		}
	}

	function selectallpropCam() {

		var mul = $("#propertyNameCam").text();
		var propertyType = $("input[name=propertyTypeCam]").data(
				"kendoDropDownList").text();
		var multiSelect = $("#propertyNameCam").data("kendoMultiSelect");

		if (propertyType == 'All') {
			if (multiSelect.dataSource.data().length == 0) {
				var dropdownlist1 = $("#propertyTypeCam").data(
						"kendoDropDownList");
				dropdownlist1.value("");

			} else {
				var selectedValues = "";
				var strComma = "";
				for (var i = 0; i < multiSelect.dataSource.data().length; i++) {
					var item = multiSelect.dataSource.data()[i];

					selectedValues += strComma + item.propertyId;
					//alert(selectedValues);
					strComma = ",";
				}
				multiSelect.value(selectedValues.split(","));
			}
		} else {
			//  var multiSelect = $("#propertyNameAmr").data("kendoMultiSelect");
			//multiSelect.value(" ");
		}
	}

	function selectallpropWater() {

		var mul = $("#propertyNameWater").text();
		var propertyType = $("input[name=propertyTypeWater]").data(
				"kendoDropDownList").text();
		var multiSelect = $("#propertyNameWater").data("kendoMultiSelect");

		if (propertyType == 'All') {
			if (multiSelect.dataSource.data().length == 0) {
				var dropdownlist1 = $("#propertyTypeWater").data(
						"kendoDropDownList");
				dropdownlist1.value("");

			} else {
				var selectedValues = "";
				var strComma = "";
				for (var i = 0; i < multiSelect.dataSource.data().length; i++) {
					var item = multiSelect.dataSource.data()[i];

					selectedValues += strComma + item.propertyId;
					//alert(selectedValues);
					strComma = ",";
				}
				multiSelect.value(selectedValues.split(","));
			}
		} else {
			//  var multiSelect = $("#propertyNameAmr").data("kendoMultiSelect");
			//multiSelect.value(" ");
		}
	}
	
	function recur() {
		var multiSelect = $("#propertyNameAmr").data("kendoMultiSelect");
		var selectedValues = "";
		var strComma = "";
		for (var i = 0; i < multiSelect.dataSource.data().length; i++) {
			var item = multiSelect.dataSource.data()[i];
			selectedValues += strComma + item.propertyId;
			strComma = ",";
		}
		multiSelect.value(selectedValues.split(","));
	}

	function recurCam() {
		var multiSelect = $("#propertyNameCam").data("kendoMultiSelect");
		var selectedValues = "";
		var strComma = "";
		for (var i = 0; i < multiSelect.dataSource.data().length; i++) {
			var item = multiSelect.dataSource.data()[i];
			selectedValues += strComma + item.propertyId;
			strComma = ",";
		}
		multiSelect.value(selectedValues.split(","));
	}
  
	function recurWater() {
		var multiSelect = $("#propertyNameWater").data("kendoMultiSelect");
		var selectedValues = "";
		var strComma = "";
		for (var i = 0; i < multiSelect.dataSource.data().length; i++) {
			var item = multiSelect.dataSource.data()[i];
			selectedValues += strComma + item.propertyId;
			strComma = ",";
		}
		multiSelect.value(selectedValues.split(","));
	}
	
	function bulkBillClose() {

		var todcal = $("#bulkBillingDiv");
		var dropdownlist1 = $("#serviceTypeList").data("kendoDropDownList");
		dropdownlist1.value("");
		var dropdownlist2 = $("#blockName").data("kendoDropDownList");
		dropdownlist2.value("");
		var presentreading = $("#consumptionUnit");
		presentreading.val("");
		var presentreading = $("#presentdate");
		presentreading.val("");
		/* $('#propmultiselect1').remove();
		$('#propmultiselect2').remove(); 
		$('#propmultiselect').append("<td id='propmultiselect1'>Property Name </td><td id='propmultiselect2'><select id='propertyName' name='propertyName'  multiple='multiple' data-placeholder='Select Properyty...' style='width:150px;'/> </td>"); */

		todcal.kendoWindow({
			width : "auto",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Generate Bill"
		}).data("kendoWindow").center().close();

		todcal.kendoWindow("close");

	}

	function amrBillClose() {

		var todcal = $("#amrBillingDiv");
		/* var dropdownlist1 = $("#serviceTypeList").data("kendoDropDownList");
		dropdownlist1.value("");
		var dropdownlist2 = $("#blockName").data("kendoDropDownList");
		dropdownlist2.value("");
		var presentreading = $("#consumptionUnit");
		presentreading.val("");
		var presentreading = $("#presentdate");
		presentreading.val(""); */
		/* $('#propmultiselect1').remove();
		$('#propmultiselect2').remove(); 
		$('#propmultiselect').append("<td id='propmultiselect1'>Property Name </td><td id='propmultiselect2'><select id='propertyName' name='propertyName'  multiple='multiple' data-placeholder='Select Properyty...' style='width:150px;'/> </td>"); */

		todcal.kendoWindow({
			width : "auto",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Generate Bill"
		}).data("kendoWindow").center().close();

		todcal.kendoWindow("close");

	}
	function getpreviousstatus() {

		var previousstatus = $('#statuscheck').val();

		if (previousstatus != 'Posted' && previousstatus != 'Not Generated'
				&& previousstatus != 'Paid' && previousstatus != 'Cancelled') {
			alert("Your previous Bill is " + previousstatus
					+ "  so Please Post it to Generate Next Bill");
			close();
		}
	}

	function chanegeUomType() {
		var serviceName = $("input[name=serviceTypeList]").data(
				"kendoDropDownList").text();
		if (serviceName != 'Solid Waste') {
			$('.changeUom').html('Present Reading');
		} else {
			$('.changeUom').html('Amount(per month)');
		}
	}
	function chanegeBulkReding() {
		var serviceName = $("input[name=billCalcType]").data(
				"kendoDropDownList").text();
		if (serviceName != 'Square Feet') {
			$('.bulkReading').html('Unit');
		} else {
			$('.bulkReading').html('Unit(Per Sqft)');
		}
	}
	function changeDataSource() {
		var propertyId = $("input[name=propertyName]").data("kendoMultiSelect")
				.value();
	}
	$("#bulkBillingForm").submit(function(e) {
		e.preventDefault();
	});
	$("#amrBillingForm").submit(function(e) {
		e.preventDefault();
	});

	$("#downloadAllBillsForm").submit(function(e) {
		e.preventDefault();
	});

	function accountNumberEditor(container, options) {

		$(
				'<input name="accountNumberEE" id="accountId" data-text-field="accountNumber" required="true" validationmessage="Account number is required" data-value-field="accountId" data-bind="value:' + options.field + '"/>')
				.appendTo(container)
				.kendoComboBox(
						{
							dataType : 'JSON',
							placeholder : "Select Account Number",
							headerTemplate : '<div class="dropdown-header">'
									+ '<span class="k-widget k-header">Photo</span>'
									+ '<span class="k-widget k-header">Contact info</span>'
									+ '</div>',
							template : '<table><tr><td rowspan=2><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.personId#'/>" width=40 height=55 alt=\"No Image to Display\" /></span></td>'
									+ '<td align="left"><span class="k-state-default"><b>#: data.personName #</b></span><br>'
									+ '<span class="k-state-default"><i>#: data.accountNumber #</i></span><br>'
									+ '<span class="k-state-default"><i>#: data.personType #</i></span></td></tr></table>',
							dataSource : {
								transport : {
									read : "${accountNumberAutocomplete}"
								}
							}
						});
		$('<span class="k-invalid-msg" data-for="accountNumberEE"></span>')
				.appendTo(container);
	}

	function serviceTypeEditor(container, options) {
		$(
				'<select data-text-field="typeOfService" name="typeOfService" data-value-field="typeOfService" required ="true" validationmessage="Service type is required" id="typeOfService" data-bind="value:' + options.field + '"/>')
				.appendTo(container).kendoDropDownList({
					placeholder : "Select Service",
					cascadeFrom : "accountId",
					optionLabel : "Select",
					autoBind : false,
					dataSource : {
						transport : {
							read : "${serviceTypesForBackBillList}"
						}
					}
				});
		$('<span class="k-invalid-msg" data-for="typeOfService"></span>')
				.appendTo(container);
	}

	//register custom validation rules
	(function($, kendo) {
		$
				.extend(
						true,
						kendo.ui.validator,
						{

							rules : { // custom rules
								toDateBackBillValidation : function(input,
										params) {
									if (input
											.filter("[name = 'toDateBackBill']").length
											&& input.val()) {
										var selectedDate = input.val();
										var todaysDate = $.datepicker
												.formatDate('dd/mm/yy',
														new Date());
										var flagDate = false;

										if ($.datepicker.parseDate('dd/mm/yy',
												selectedDate) <= $.datepicker
												.parseDate('dd/mm/yy',
														todaysDate)) {
											flagDate = true;
										}
										return flagDate;
									}
									return true;
								},
								fromDateBackBillValidation : function(input,
										params) {
									if (input
											.filter("[name = 'fromDateBackBill']").length
											&& input.val()) {
										var selectedDate = input.val();
										var todaysDate = $.datepicker
												.formatDate('dd/mm/yy',
														new Date());
										var flagDate = false;

										if ($.datepicker.parseDate('dd/mm/yy',
												selectedDate) <= $.datepicker
												.parseDate('dd/mm/yy',
														todaysDate)) {
											flagDate = true;
										}
										return flagDate;
									}
									return true;
								},
								unitsBackBillRequiredValidation : function(
										input, params) {
									if (input.attr("name") == "unitsBackBill") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},
								unitsBackBillLengthValidation : function(input,
										params) {
									if (input.filter("[name='unitsBackBill']").length
											&& input.val() != "") {
										return /^[0-9]{1,8}$/.test(input.val());
									}
									return true;
								},
								fromDateBackBillRequiredValidation : function(
										input, params) {
									if (input.attr("name") == "fromDateBackBill") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},
								toDateBackBillRequiredValidation : function(
										input, params) {
									if (input.attr("name") == "toDateBackBill") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},

							},
							messages : {
								//custom rules messages
								toDateBackBillValidation : "To date must be past date",
								fromDateBackBillValidation : "From date must be past date",
								unitsBackBillRequiredValidation : "Units required",
								unitsBackBillLengthValidation : "Units field max 8 digit number",
								fromDateBackBillRequiredValidation : "From date is required",
								toDateBackBillRequiredValidation : "To date is required",

							}
						});
	})(jQuery, kendo);
	//End Of Validation

	function correctBill() {
		$.ajax({
			url : "./bill/billCorrection",
			type : "GET",
			dataType : "Json",
			data : {
				billId : SelectedRowId,
			},
			success : function(response) {

				$('#bcAccountNo').val(response.bcAccountNo);
				$('#bcServiceName').val(response.bcServiceName);
				$('#bcPreviousDate').val(response.bcPreviousDate);
				$('#bcPresentdate').val(response.bcPresentdate);
				$("input[name=bcPreviousReading]").data("kendoNumericTextBox")
						.value(response.bcPreviousReading);
				//$('#').val();
				$('#bcPreviousStaus').val(response.bcPreviousStaus);
				$('#bcPresentReading').val(response.bcPresentReading);
				$('#bcPresentStaus').val(response.bcPresentStaus);
				$("input[name=bcMeterFactor]").data("kendoNumericTextBox")
						.value(response.bcMeterFactor);
				//	$('#').val();
				$("input[name=bcUnits]").data("kendoNumericTextBox").value(
						response.bcUnits);
				///$('#').val();
				if (response.bcMeterType == 'Trivector Meter') {
					$('#bcPreviousDiv').css("height", "250px");
					$("#bcpftr").show();
					$("#bcconntr").show();
					$('#bcpfReading').val(response.bcPfReading);
					$('#bcconnectedLoad').val(response.bcRecordedDemand);
				} else {
					$("#bcpftr").hide();
					$("#bcconntr").hide();
					$('#bcPreviousDiv').css("height", "205px");
				}
				if (response.bcServiceName == 'Solid Waste'
						|| response.bcServiceName == 'Others') {
					$('#bcPstatus').hide();
					$('#presentCorrectBilldiv').hide();
					$('#bctrSWPresentdate').show();
					$('#bcSWPresentdate').val(response.bcPreviousReading);
					$('#bcSWPresentdate').val(response.bcSWPresentdate);
					if (response.bcServiceName == 'Others') {
						$('#bcPresenthide').hide();

					} else {
						$('#bcPresenthide').show();
					}

				}

				if (response.dgApplicable == 'Yes') {
					$("#bctrpreviousDgReading").show();
					$("#bcdgtr").show();
					$("input[name=bcpreviousDGReading]").data(
							"kendoNumericTextBox").value(
							response.previousDgReading);
					$("input[name=bcdgmeterFactor]")
							.data("kendoNumericTextBox").value(
									response.dGMeterConstant);
					$("input[name=bcdgUnit]").data("kendoNumericTextBox")
							.value(response.dGUnits);
					$("#bcdgReading").val(response.presentDGReading);

				} else {
					$("#bctrpreviousDgReading").hide();
					$("#bcdgtr").hide();
				}
				if (response.todApplicable == 'Yes') {
					$("#bctodDiv").show();
				} else {
					$("#bctodDiv").hide();
				}
			}
		});

		var todcal = $("#billCorrectionDialog");
		todcal.kendoWindow({
			width : "auto",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Correct Bill"
		}).data("kendoWindow").center().open();
		todcal.kendoWindow("open");
	}

	function postAllToTally() {

		$('#dvLoadingbody').show();
		closeTallyBill();
		var serviceType = $("#serviceTypeTally").val();
		var presentdate = $("#presentdatepostBill").val();

		$.ajax({

			url : "./electricityBills/postAllBillToTally",
			type : "POST",
			dataType : "text",
			data : {
				serviceType : serviceType,
				presentdate : presentdate,

			},
			success : function(response) {
				//bootbox.alert(response);
				//console.log(response);
				alert(response);
				window.location.reload();
			}

		});
	}
	function postToTally() {

		$('tr[aria-selected="true"]').find('td:nth-child(30)').html("");
		$('tr[aria-selected="true"]')
				.find('td:nth-child(30)')
				.html(
						"<img src='./resources/images/151.gif' width='100px' height='25px' />");
		$.ajax({
			url : "./bill/postToTally",
			type : "POST",
			dataType : "text",
			data : {
				billId : SelectedRowId,
			},
			success : function(response) {
				alert(response);
				window.location.reload();
			}
		});
	}

	function reCalculateBill() {

		var elBillId = SelectedRowId;
		var accountNo = $("#bcAccountNo").val();
		var serviceName = $("#bcServiceName").val();
		var presentReading = $("#bcPresentReading").val();
		var previousReading = $("#bcPreviousReading").val();
		var meterFactor = $("#bcMeterFactor").val();
		var previousbillDate = $("#bcPreviousDate").val();
		var presentbilldate = $("#bcPresentdate").val();
		var previousStaus = $("#bcPreviousStaus").val();
		var presentStaus = $("#bcPresentStaus").val();
		var units = $("#bcUnits").val();
		var pfReading = $("#bcpfReading").val();
		var recoredDemand = $("#bcconnectedLoad").val();
		var dgUnit = $("#bcdgUnit").val();
		var presentDgReading = $("#bcdgReading").val();
		var dgmeterconstant = $("#bcdgmeterFactor").val();
		var previousDgreading = $("#bcpreviousDGReading").val();
		var presentTod1 = $("#bcpresentTod1").val();
		var presentTod2 = $("#bcpresentTod2").val();
		var presentTod3 = $("#bcpresentTod3").val();

		$('#commitplaceholderCorrection').show();
		$('#recalculation').hide();

		$.ajax({
			url : "./bill/reCalculateBill",
			type : "GET",
			dataType : "text",
			data : {
				elBillId : elBillId,
				accountNo : accountNo,
				serviceName : serviceName,
				presentbilldate : presentbilldate,
				presentReading : presentReading,
				meterFactor : meterFactor,
				previousbillDate : previousbillDate,
				previousReading : previousReading,
				previousStaus : previousStaus,
				presentStaus : presentStaus,
				units : units,
				pfReading : pfReading,
				recoredDemand : recoredDemand,
				dgUnit : dgUnit,
				presentDgReading : presentDgReading,
				dgmeterconstant : dgmeterconstant,
				previousDgreading : previousDgreading,
				presentTod1 : presentTod1,
				presentTod2 : presentTod2,
				presentTod3 : presentTod3
			},
			success : function(response) {

				$('#commitplaceholderCorrection').hide();
				$('#recalculation').show();

				alert("Your bill has been re-calculated");
				$('#billGrid').data('kendoGrid').refresh();
				$('#billGrid').data('kendoGrid').dataSource.read();
				closebillcorrection();
			}
		});
	}

	function getBCMeterStatus() {

		var presentStaus = $("#bcPresentStaus").val();
		var rateName = $("#bcServiceName").val();
		var accountId = $("#bcAccountNo").val();
		if (presentStaus != "Normal" && presentStaus != "Meter Change") {
			alert("As present Status is " + presentStaus
					+ " so Present Reading is 0");
			$("#presenthide").hide();

			$.ajax({
				type : "GET",
				url : "./bill/getAvgUnit",
				dataType : "text",
				data : {
					presentStaus : presentStaus,
					rateName : rateName,
					accountId : accountId,
				},
				success : function(response) {
					var unit = $('#bcUnits').data("kendoNumericTextBox");
					unit.value($('#bcUnits').val(response).val());
				}
			});
		} else {
			$("#presenthide").show();
		}
	}

	function billGenerationDataBound(e) {
		var data = this.dataSource.view();
		var grid = $("#billGrid").data("kendoGrid");
		for (var i = 0; i < data.length; i++) {
			var currentUid = data[i].uid;
			row = this.tbody.children("tr[data-uid='" + data[i].uid + "']");
			var status = data[i].status;
			var postType = data[i].postType;
			var tallyStatus = data[i].tallyStatus;

			if (status == 'Paid' || status == 'Posted' || status == 'Cancelled') {
				var currenRow = grid.table.find("tr[data-uid='" + currentUid
						+ "']");
				var reOpenButton = $(currenRow).find(".k-grid-Cancel");
				var postedButton = $(currenRow).find(".k-grid-Active");
				postedButton.hide();
				reOpenButton.hide();
			}

			if (status == 'Generated' || status == 'Approved'
					|| status == 'Cancelled' || tallyStatus == 'Posted') {
				var currenRow = grid.table.find("tr[data-uid='" + currentUid
						+ "']");
				var postTotally = $(currenRow).find(".k-grid-PostToTally");
				postTotally.hide();
			}

			if (status == 'Paid' || status == 'Posted' || status == 'Cancelled'
					|| status == 'Approved') {
				var currenRow = grid.table.find("tr[data-uid='" + currentUid
						+ "']");
				var reOpenButton = $(currenRow).find(".k-grid-CorrectBill");
				reOpenButton.hide();
			}
			if (postType == 'Provisional Bill') {
				var currenRow = grid.table.find("tr[data-uid='" + currentUid
						+ "']");
				var cancelButton = $(currenRow).find(".k-grid-Cancel");
				var postedButton = $(currenRow).find(".k-grid-Active");
				var billCorrectionButton = $(currenRow).find(
						".k-grid-CorrectBill");
				billCorrectionButton.hide();
				cancelButton.hide();
				postedButton.hide();
			}

			if (status == 'Generated') {
				row.addClass("bgBlueColor");
			}
			if (status == 'Approved') {
				row.addClass("bgYellowColor");
			}
			if (status == 'Cancelled') {
				row.addClass("bgRedColor");
			}
			if (status == 'Posted') {
				row.addClass("bgGreenColor");
			}
			if (status == 'Paid') {
				row.addClass("bgKhakiColor");
			}

		}
	}

	function getBCUnits() {
		var bcPreviousReading = $('#bcPreviousReading').val();
		var bcPresentReading = $('#bcPresentReading').val();
		var response = (parseFloat(bcPresentReading) - parseFloat(bcPreviousReading));
		var unit = $('#bcUnits').data("kendoNumericTextBox");
		unit.value($('#bcUnits').val(response).val());
	}
	function closebillcorrection() {
		var todcal = $("#billCorrectionDialog");
		todcal.kendoWindow({
			width : "auto",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Correct Bill"
		}).data("kendoWindow").center().close();
		todcal.kendoWindow("close");
	}
	function getflat() {
		var flatType = $("input[name=flats]").data("kendoDropDownList").text();

		if (flatType != "All" && flatType != "Select") {
			//getPropertyNoCam();
			$("#blocksCAM").show();
			$("#blockNameCamid").show();
			$("#propertyTypeCamid").show();
			$("#PreviousBillCAM").show();
			$("#camBillCAM").show();
			$("#propmultiselectCam").show();

		} else if (flatType == "All") {

			$("#blocksCAM").hide();
			$("#propertyCam").hide();
			$("#accountCam").hide();
			$("#PreviousBillCAM").hide();
			$("#camBillCAM").show();

			$.ajax({
				type : "GET",
				url : "./bill/getCamPreviousStatus",
				dataType : "text",

				success : function(response) {
					if (response == 'Generated') {
						alert("Please Post Cam Bill To Generate Next Bill");
						camWindowClose();

					}
				}
			});

		} else {
			$("#blocksCAM").hide();
			$("#propertyCam").hide();
			$("#accountCam").hide();
			$("#PreviousBillCAM").hide();
			$("#camBillCAM").show();
		}

	}

	function getflat1() {
		var flatType = $("input[name=flats1]").data("kendoDropDownList").text();

		if (flatType != "All" && flatType != "Select") {
			//getPropertyNoCam();
			$("#blocksCAM").show();
			$("#blockNameWaterid").show();
			$("#propertyTypeWaterid").show();
			$("#PreviousBillCAM").show();
			$("#waterBill").show();
			$("#propmultiselectWater").show();

		} else if (flatType == "All") {

			$("#blocksCAM").hide();
			$("#propertyCam").hide();
			$("#accountWater").hide();
			$("#PreviousBillCAM").hide();
			$("#waterBill").show();

			$.ajax({
				type : "GET",
				url : "./bill/getCamPreviousStatus",
				dataType : "text",

				success : function(response) {
					if (response == 'Generated') {
						alert("Please Post Cam Bill To Generate Next Bill");
						waterWindowClose();

					}
				}
			});

		} else {
			$("#blocksCAM").hide();
			$("#propertyCam").hide();
			$("#accountWater").hide();
			$("#PreviousBillCAM").hide();
			$("#waterBill").show();
		}

	}
	
	function getPreviousCamStatus() {

		var accountId = $("#accountIdCam").val();

		$.ajax({
			type : "GET",
			url : "./bill/getCamPreviousStatusSpecific",
			dataType : "text",
			data : {
				accountId : accountId,
			},
			success : function(response) {

				if (response == 'Generated') {
					alert("Please Post Cam Bill To Generate Next Bill");
					camWindowClose();

				}
			}
		});

	}

	function getPreviousWaterStatus() {

		var accountId = $("#accountIdWater").val();

		$.ajax({
			type : "GET",
			url : "./bill/getCamPreviousStatusSpecific",
			dataType : "text",
			data : {
				accountId : accountId,
			},
			success : function(response) {

				if (response == 'Generated') {
					alert("Please Post Water Bill To Generate Next Bill");
					waterWindowClose();

				}
			}
		});

	}
	var previousCamDate;
	function postTheBill() {

		var blockCAMId = $("#blockCamId").val();
		var accountId = $("#accountIdCam").val();
		//var propertyId = $("#propertyIdCam").val();
		var propertyId = $("input[name=propertyNameCam]").data(
				"kendoMultiSelect").value();
		var flatsType = $("#flats").val();
		//	previousCamDate = $('#previousCamDate').val();
		var camBillDate = $('#camBillDate').val();

		if (flatsType == "" || flatsType == "Select") {
			alert("Please Select Flat Type");
			return false;
		}
		if (flatsType == "All") {
			if (camBillDate == "") {
				alert("Please Enter Bill Date");
				return false;
			}

		}
		/* if (flatsType != "All") {

			if (blockCAMId == null || blockCAMId == ""
					|| blockCAMId == "Select") {
				alert("Please Select Block");
				return false;
			}

			if (propertyId == "") {
				alert("Please Select Property No");
				return false;
			}

			if (accountId == "") {
				alert("Please Select Account No");
				return false;
			}

			if (camBillDate == "") {
				alert("Please Enter Bill Date");
				return false;
			}

			if (camBillDate != null || camBillDate != "") {

				if ($.datepicker.parseDate('dd/mm/yy', previousCamDate) > $.datepicker
						.parseDate('dd/mm/yy', camBillDate)) {
					alert("Bill date must ge greater than Previous Bill date");
					return false;
				}

			}
		} */

		$('#camBill').hide();
		$('#loader').show();

		$.ajax({
			type : "POST",
			async : false,
			url : "./camBills/generateCAMBills",
			dataType : "text",
			data : {
				accountId : accountId,
				propertyId : "" + propertyId + "",
				flatsType : flatsType,

				camBillDate : camBillDate,
			},
			success : function(response) {

				$("#alertsBox").html("CAM Bill Created Successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				$('#billGrid').data('kendoGrid').dataSource.read();
				close();
				$('#loader').hide();
				$('#camBill').show();

				var combobox = $('#generateDropDown').data("kendoComboBox");
				if (combobox != null) {
					combobox.value("");
				}
			}
		});

		postCAMBillClose();
	}
    
	var previousCamDate;
	function postWaterBill() {

		var blockWaterId = $("#blockWaterId").val();
		var accountId = $("#accountIdWater").val();
		//var propertyId = $("#propertyIdCam").val();
		var propertyId = $("input[name=propertyNameWater]").data(
				"kendoMultiSelect").value();
		var flatsType = $("#flats1").val();
		//	previousCamDate = $('#previousCamDate').val();
		var waterBillDate = $('#waterBillDate').val();

		if (flatsType == "" || flatsType == "Select") {
			alert("Please Select Flat Type");
			return false;
		}
		if (flatsType == "All") {
			if (waterBillDate == "") {
				alert("Please Enter Bill Date");
				return false;
			}

		}
		/* if (flatsType != "All") {

			if (blockCAMId == null || blockCAMId == ""
					|| blockCAMId == "Select") {
				alert("Please Select Block");
				return false;
			}

			if (propertyId == "") {
				alert("Please Select Property No");
				return false;
			}

			if (accountId == "") {
				alert("Please Select Account No");
				return false;
			}

			if (camBillDate == "") {
				alert("Please Enter Bill Date");
				return false;
			}

			if (camBillDate != null || camBillDate != "") {

				if ($.datepicker.parseDate('dd/mm/yy', previousCamDate) > $.datepicker
						.parseDate('dd/mm/yy', camBillDate)) {
					alert("Bill date must ge greater than Previous Bill date");
					return false;
				}

			}
		} */

		$('#WaterBills').hide();
		$('#loader').show();

		$.ajax({
			type : "POST",
			async : false,
			url : "./WaterBills/generateWaterBills",
			dataType : "text",
			data : {
				accountId : accountId,
				propertyId : "" + propertyId + "",
				flatsType : flatsType,

				waterBillDate : waterBillDate,
			},
			success : function(response) {

				$("#alertsBox").html("Water Bill Created Successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				$('#billGrid').data('kendoGrid').dataSource.read();
				close();
				$('#loader').hide();
				$('#WaterBills').show();

				var combobox = $('#generateDropDown').data("kendoComboBox");
				if (combobox != null) {
					combobox.value("");
				}
			}
		});

		postWaterBillClose();
	}
	
	function camWindowClose() {

		var todcal = $("#generateCamBill");
		todcal.kendoWindow({
			width : '250',
			height : 'auto',
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "CAM Bill Information"
		}).data("kendoWindow").center().close();

		todcal.kendoWindow("close");

	}

	function waterWindowClose() {

		var todcal = $("#generateWaterBill");
		todcal.kendoWindow({
			width : '250',
			height : 'auto',
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Water Bill Information"
		}).data("kendoWindow").center().close();

		todcal.kendoWindow("close");

	}

	function postCAMBillClose() {

		var dropdownlist1 = $("#flats").data("kendoDropDownList");

		dropdownlist1.value("");

		//	$("#previousCamDate").val("");
		$("#camBillDate").val("");
		$("#accountIdCam").val("");
		$("#propertyIdCam").val("");

		var todcal = $("#generateCamBill");
		todcal.kendoWindow({
			width : "auto",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Generate CAM Bill"
		}).data("kendoWindow").center().close();
		todcal.kendoWindow("close");
	}
    
	function postWaterBillClose() {

		var dropdownlist1 = $("#flats1").data("kendoDropDownList");

		dropdownlist1.value("");

		//	$("#previousCamDate").val("");
		$("#waterBillDate").val("");
		$("#accountIdWater").val("");
		$("#propertyIdCam").val("");

		var todcal = $("#generateWaterBill");
		todcal.kendoWindow({
			width : "auto",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Generate Water Bill"
		}).data("kendoWindow").center().close();
		todcal.kendoWindow("close");
	}
	
	function searchByMonth() {
		var month = $('#monthpicker').val();
		$.ajax({
			type : "POST",
			url : "./electricityBills/searcchByMonth/" + month,
			dataType : "json",
			success : function(result) {
				var grid = $("#billGrid").getKendoGrid();
				var data = new kendo.data.DataSource();
				grid.dataSource.data(result);
				grid.refresh();
			}
		});
	}

	function saveOldBillDoc() {
		var elBillId = "";
		var gridParameter = $("#billGrid").data("kendoGrid");
		var selectedAddressItem = gridParameter
				.dataItem(gridParameter.select());
		elBillId = selectedAddressItem.elBillId;

		alert(elBillId);

		$.ajax({
			type : "POST",
			url : "./bill/saveOldBill/" + elBillId,
			dataType : "text",
			success : function(response) {

			}
		});

	}

	function closeTallyBill() {
		var bbDialog = $("#postAllTallydiv");
		bbDialog.kendoWindow({
			width : "auto",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Post All Bill"
		}).data("kendoWindow").center().close();

		bbDialog.kendoWindow("close");
	}
	function filterclose() {

		var todcal = $("#filterNetAmountdiv");

		todcal.kendoWindow({
			width : "auto",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Approve Bills"
		}).data("kendoWindow").center().close();
		todcal.kendoWindow("close");
	}
</script>
<style>
td {
	border: 0 none;
	font-size: 100%;
	margin: 0;
	outline: 0 none;
	padding: 0;
	vertical-align: middle;
}

div[id^='elRateSlab_']>div>table td:nth-child(4) {
	text-align: right;
}

span.k-tooltip {
	border-width: 2px;
	display: list-item;
	padding: 1px 5px 8px 25px;
	position: absolute;
	margin-top: -7px;
}

div.k-window-content {
	box-sizing: border-box;
	height: 100%;
	outline: 0 none;
	overflow: auto;
	padding: 2em;
	position: relative;
	z-index: -1;
}

tr[class='k-footer-template'] {
	text-align: right;
}

.k-grid-amrBilling {
	margin-top: 5px !important;
}

.k-grid-Clear_Filter {
	margin-top: 5px !important;
}

.bgGreenColor {
	background: #428f4c
}

.bgBlueColor {
	background: #3883b5
}

.bgRedColor {
	background: #FF8484
}

.bgYellowColor {
	background: #FFD700
}

.bgKhakiColor {
	background: #F0E68C
}

.k-grid-CorrectBill {
	margin-top: 5px !important;
}

.loadingimg {
	background: #FFF url(./resources/images/712.GIF) no-repeat center center;
	position: absolute;
	top: 0%;
	left: 0%;
	width: 100%;
	height: 100%;
	z-index: 1001;
	-moz-opacity: 0.8;
	opacity: .80;
	filter: alpha(opacity = 80);
}
</style>
