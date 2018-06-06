<%@include file="/common/taglibs.jsp"%>


<script type="text/javascript" src="<c:url value='/resources/jquery-validate.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jsPDF/libs/FileSaver.js/FileSaver.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jsPDF/libs/FileSaver.js/FileSaver.min.js' />"></script>
<script src="http://html2canvas.hertzen.com/build/html2canvas.js"></script>

<script src="resources/js/plugins/charts/highcharts.js"></script>
<script src="resources/js/plugins/charts/exporting.js"></script>

<script type="text/javascript" src="http://canvg.googlecode.com/svn/trunk/rgbcolor.js"></script>
<script type="text/javascript" src="http://canvg.googlecode.com/svn/trunk/canvg.js"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jsPDF-master/jspdf.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jsPDF-master/libs/Deflate/adler32cs.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jsPDF-master/libs/FileSaver.js/FileSaver.js"' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jsPDF-master/libs/Blob.js/BlobBuilder.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jsPDF-master/jspdf.plugin.addimage.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jsPDF-master/jspdf.plugin.standard_fonts_metrics.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jsPDF-master/jspdf.plugin.split_text_to_size.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jsPDF-master/jspdf.plugin.from_html.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jsPDF/libs/sprintf.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jsPDF/tableExport.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jsPDF/jquery.base64.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jsPDF/html2canvas.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jsPDF/libs/sprintf.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jsPDF/libs/base64.js' />"></script>

<c:url value="/billingPayments/billingPaymentCreate" var="createUrl" />
<c:url value="/billingPayments/billingPaymentRead" var="readUrl" />
<c:url value="/billingPayments/billingPaymentDestroy" var="destroyUrl" />
<c:url value="/billingPayments/billingPaymentUpdate" var="updateUrl" />
<c:url value="/billingPayments/filter" var="commonFilterForPaymentCollectionUrl" />

<c:url value="/paymentSegments/paymentSegmentRead" var="paymentSegmentReadUrl" />
<c:url value="/paymentSegments/paymentSegmentUpdate" var="paymentSegmentUpdateUrl" />
<c:url value="/paymentSegments/paymentSegmentDestroy" var="paymentSegmentDestroy" />
<c:url value="/paymentSegments/filter" var="commonFilterForPaymentSegmentsUrl" />

<c:url value="/paymentSegments/paymentSegmentCalcLinesRead" var="paymentSegmentCalcLinesReadUrl" />

<c:url value="/common/getAllChecks" var="allChecksUrl" />
<c:url value="/billingPayments/getAllBankNames" var="getAllBankNames" />
<c:url value="/billingPayments/accountNumberAutocomplete" var="accountNumberAutocomplete" />
<c:url value="/billingPayments/getConsolidatedAmount" var="consolidatedIdUrl" />
<c:url value="/billingPayments/commonFilterForAccountNumbersUrl" var="commonFilterForAccountNumbersUrl" />
<c:url value="/billingPayments/getPersonListForFileter" var="personNamesFilterUrl" />
<c:url value="/billingPayments/getAllPropertyNo" var="getAllPropertyNo" />

<div id="dvLoadingbody" class="loadingimg" hidden="true" ></div>

<kendo:grid name="grid" remove="paymetsDeleteEvent" change="onChangePaymentSegmet" pageable="true" resizable="true" edit="paymentEvent" dataBound="billingPaymentsDataBound" sortable="true" detailTemplate="paymentSegmentsTemplate" reorderable="true" selectable="true" scrollable="true" filterable="false" groupable="true">

    <kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" refresh="true" info="true" previousNext="true">
		<kendo:grid-pageable-messages itemsPerPage="Payments per page" empty="No payment to display" refresh="Refresh all the payments" 
			display="{0} - {1} of {2} New payments" first="Go to the first page of payments" last="Go to the last page of payments" next="Go to the next page of payments"
			previous="Go to the previous page of payments"/>
	</kendo:grid-pageable>
	<kendo:grid-filterable extra="false">
		<kendo:grid-filterable-operators>
			<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains"/>
			<kendo:grid-filterable-operators-date gt="Is after" lt="Is before"/>
		</kendo:grid-filterable-operators> 
	</kendo:grid-filterable>
	<kendo:grid-editable mode="popup"/>
		<kendo:grid-toolbar>
		      <kendo:grid-toolbarItem name="create" text="Add New Payment Details" />
		      <kendo:grid-toolbarItem template="<label class='category-label'>&nbsp;&nbsp;From&nbsp;Date&nbsp;:&nbsp;&nbsp;</label><input id='fromMonthpicker' style='width:110px'/>"/>
			  <kendo:grid-toolbarItem template="<label class='category-label'>&nbsp;&nbsp;To&nbsp;Date&nbsp;:&nbsp;&nbsp;</label><input id='toMonthpicker' style='width:110px'/>"/>
			  <kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=searchByMonth() title='Search' style='width:90px'>Search</a>"/>
		      <kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterPayments()>Clear Filter</a>"/>
		      <kendo:grid-toolbarItem name="approveCollection" text="Approve Collection"/>
		      <kendo:grid-toolbarItem name="postCollection" text="Post Collection"/>
		      <kendo:grid-toolbarItem name="postAllPaymentsToTally" text="Post All To Tally"/>
		       <kendo:grid-toolbarItem name="generateXMLForTally" text="Generate XML for Tally"></kendo:grid-toolbarItem>
		       <kendo:grid-toolbarItem template="<input id='monthDatePicker' type=date/>"/>
		       <kendo:grid-toolbarItem template="<button class='k-button' onclick='exportPaymentsTOexcel()'>Export To Excel</button>"/>
	    </kendo:grid-toolbar>
	<kendo:grid-columns>
	    
	    <kendo:grid-column title="paymentCollectionId" field="paymentCollectionId" width="10px" hidden="true" filterable="false" sortable="false" />
	    
	    <%-- <kendo:grid-column title="Payment&nbsp;Date" field="paymentDate" format="{0:dd/MM/yyyy hh:mm tt}" width="135px" filterable="true">
	    </kendo:grid-column> --%>
	    
	    <kendo:grid-column title="Property&nbsp;No" field="property" hidden="true" width="95px" filterable="true" editor="propertyNoEditor">
	    
	    </kendo:grid-column>
	    
	    <kendo:grid-column title="Receipt&nbsp;No&nbsp;*" field="receiptNo" width="95px" filterable="true">
	    <kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function ledgerStatusFilter(element) {
								element.kendoAutoComplete({
											placeholder : "Enter Receipt Number",
											dataSource : {
												transport : {
													read : "${commonFilterForPaymentCollectionUrl}/receiptNo"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	    </kendo:grid-column>
	    
	    <kendo:grid-column title="Account&nbsp;No&nbsp;*" field="accountNo" width="95px" filterable="true">
	    <kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function ledgerTypeFilter(element) {
								element.kendoAutoComplete({
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
	    
	    <kendo:grid-column title="Account&nbsp;No&nbsp;*" field="accountId" hidden="true" width="95px" filterable="true" editor="accountNumberAutocomplete">
	    </kendo:grid-column>
	    
	    <kendo:grid-column title="Person&nbsp;Name" field="personName"  width="100px" filterable="false">
	    	<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function personNameFilter(element) 
						   	{
								element.kendoAutoComplete({
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
											read :  "${personNamesFilterUrl}"
										}
									} 
								});
						   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	    	</kendo:grid-column>
	    	
	    <kendo:grid-column title="Property&nbsp;No&nbsp;*" field="property_No" filterable="true"  width="90px">
		</kendo:grid-column>
		 <kendo:grid-column title="Service&nbsp;Type&nbsp;*" field="typeOfService"  filterable="false" editor="serviceTypeEditor" hidden="true" width="120px">
	    </kendo:grid-column>
		<kendo:grid-column title="Receipt&nbsp;Date&nbsp;*" field="receiptDate" format="{0:dd/MM/yyyy}" width="105px">
	    </kendo:grid-column>
	    
	    <kendo:grid-column title="Bill&nbsp;Amount&nbsp;*" field="billAmount" filterable="false" hidden="true" width="120px" editor="consolidatedEditor">
		</kendo:grid-column>
		
		<kendo:grid-column title="Late&nbsp;Payment&nbsp;Amount*" field="latePaymentAmount" filterable="false" editor="latePaymentEditor"  hidden="true"  width="120px" >
		</kendo:grid-column>
		
		<%-- <kendo:grid-column title=" " field="paymentComponets"  width="0px" hidden="true" filterable="false" editor="changeEditor">
	    </kendo:grid-column>  --%>
	    
	    <kendo:grid-column title="Payment&nbsp;Amount" field="paymentAmount" editor="testEditor" width="110px" filterable="false">
	    </kendo:grid-column>
	    
	    <kendo:grid-column title="Excess&nbsp;Amount" field="excessAmount" width="100px" editor="excessAmountEditor" filterable="false">
	    </kendo:grid-column>
	    
	   
	    <kendo:grid-column title="Payment&nbsp;Type&nbsp;*" field="paymentType" width="110px" filterable="false" editor="dropDownChecksEditorPaymentType" hidden="true">
	    <kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function ledgerStatusFilter(element) {
								element.kendoAutoComplete({
											placeholder : "Enter Payment Type",
											dataSource : {
												transport : {
													read : "${commonFilterForPaymentCollectionUrl}/paymentType"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
		</kendo:grid-column-filterable>
		</kendo:grid-column> 
	    
	    <kendo:grid-column title="Dispute&nbsp;Flag&nbsp;*" field="disputeFlag" width="115px" filterable="true" editor="dropDownChecksEditorDisputeFlag" hidden="true">
	    <kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function ledgerStatusFilter(element) {
								element.kendoAutoComplete({
											placeholder : "Enter Flag",
											dataSource : {
												transport : {
													read : "${commonFilterForPaymentCollectionUrl}/disputeFlag"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	    </kendo:grid-column>
	     
	    <kendo:grid-column title="Part&nbsp;Payment&nbsp;*" field="partPayment" width="115px" filterable="true" editor="dropDownChecksEditorPartPayment" hidden="true">
	    <kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function ledgerStatusFilter(element) {
								element.kendoAutoComplete({
											placeholder : "Enter Payment Mode",
											dataSource : {
												transport : {
													read : "${commonFilterForPaymentCollectionUrl}/partPayment"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	    </kendo:grid-column>
	    <kendo:grid-column title="Payment&nbsp;Mode&nbsp;*" field="paymentMode" width="115px" filterable="true" editor="dropDownChecksEditor">
	    <kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function ledgerStatusFilter(element) {
								element.kendoAutoComplete({
											placeholder : "Enter Payment Mode",
											dataSource : {
												transport : {
													read : "${commonFilterForPaymentCollectionUrl}/paymentMode"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	    </kendo:grid-column>
	    
	    <kendo:grid-column title="Instrument&nbsp;Date&nbsp;*" field="instrumentDate" format="{0:dd/MM/yyyy}" width="120px" filterable="true" hidden="true">
	    </kendo:grid-column>
	   
	    <kendo:grid-column title="Instrument&nbsp;Number&nbsp;*" field="instrumentNo" width="140px" filterable="true">
	    <kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function ledgerStatusFilter(element) {
								element.kendoAutoComplete({
											placeholder : "Enter Instrument Number",
											dataSource : {
												transport : {
													read : "${commonFilterForPaymentCollectionUrl}/instrumentNo"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	    </kendo:grid-column>
	    
	    <kendo:grid-column title="Bank&nbsp;Name&nbsp;*" field="bankName" width="110px" filterable="true" editor="bankNames" hidden="true">
	    <kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function ledgerStatusFilter(element) {
								element.kendoAutoComplete({
											placeholder : "Enter Bank Name",
											dataSource : {
												transport : {
													read : "${commonFilterForPaymentCollectionUrl}/bankName"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	    </kendo:grid-column>
	    
	    <%-- <kendo:grid-column title="Consolidated&nbsp;Amount&nbsp;*" field="dept_Name" filterable="false" width="150px">
   		</kendo:grid-column> --%>
   		
   		<%-- <kendo:grid-column title="Consolidated&nbsp;Amount&nbsp;*" field="billAmount" filterable="false" hidden="true" width="130px">
		</kendo:grid-column> --%>
			
	    <kendo:grid-column title="Posted&nbsp;To&nbsp;GL&nbsp;*" field="postedToGl" width="110px" filterable="true" hidden="true">
	    <kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function ledgerStatusFilter(element) {
								element.kendoAutoComplete({
											placeholder : "Enter Posted Gl",
											dataSource : {
												transport : {
													read : "${commonFilterForPaymentCollectionUrl}/postedToGl"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	    </kendo:grid-column>
	    
	    <kendo:grid-column title="Posted&nbsp;GL&nbsp;Date" field="postedGlDate" format="{0:dd/MM/yyyy hh:mm tt}" width="120px" filterable="true" hidden="true">
	    </kendo:grid-column>
	    
	    <kendo:grid-column title="Status" field="status" width="80px" filterable="true">
	    <kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function ledgerStatusFilter(element) {
								element.kendoAutoComplete({
											placeholder : "Enter Status",
											dataSource : {
												transport : {
													read : "${commonFilterForPaymentCollectionUrl}/status"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	    </kendo:grid-column>
	    
	     <kendo:grid-column title="Tally&nbsp;Status" field="tallyStatus" width="90px" filterable="true">
	    <kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function ledgerStatusFilter(element) {
								element.kendoAutoComplete({
											placeholder : "Enter Status",
											dataSource : {
												transport : {
													read : "${commonFilterForPaymentCollectionUrl}/tallyStatus"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	    </kendo:grid-column>
	    
		<%-- <kendo:grid-column title="&nbsp;" width="80px">
			<kendo:grid-column-command>
			    <kendo:grid-column-commandItem name="edit"/>
				<kendo:grid-column-commandItem name="destroy" />
			</kendo:grid-column-command>
		</kendo:grid-column> --%>
		
		<kendo:grid-column title=""
				template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Active'>#= data.status == 'Created' ? 'Approve' : data.status == 'Approved' ? 'Post' : 'Posted' #</a>"
				width="80px" />
				
		<kendo:grid-column title="&nbsp;" width="110px">
				<kendo:grid-column-command >
					 <kendo:grid-column-commandItem name="Cancellation" click="canclePayment" />
				</kendo:grid-column-command>
		</kendo:grid-column>
		    
		<kendo:grid-column title="&nbsp;" width="110px">
				<kendo:grid-column-command >
					 <kendo:grid-column-commandItem name="Print Receipt" click="paymentReceipt" />
				</kendo:grid-column-command>
		</kendo:grid-column>    
		
		<kendo:grid-column title="&nbsp;" width="110px">
				<kendo:grid-column-command >
					 <kendo:grid-column-commandItem name="Cancellation Receipt" click="paymentCancelReceipt" />
				</kendo:grid-column-command>
		</kendo:grid-column>
		
		<kendo:grid-column title="&nbsp;" width="110px">
				<kendo:grid-column-command >
					 <kendo:grid-column-commandItem name="Post To Tally" click="postPaymentToTally" />
				</kendo:grid-column-command>
		</kendo:grid-column>
		
			
		
	</kendo:grid-columns>

	<kendo:dataSource pageSize="20" requestEnd="onRequestEnd" requestStart="onRequestStart">
		<kendo:dataSource-transport>
			<kendo:dataSource-transport-create url="${createUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json" />
			<kendo:dataSource-transport-update url="${updateUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-destroy url="${destroyUrl}/" dataType="json" type="GET" contentType="application/json" />
		</kendo:dataSource-transport>

		<kendo:dataSource-schema parse="parse">
			<kendo:dataSource-schema-model id="paymentCollectionId">
				<kendo:dataSource-schema-model-fields>
					<kendo:dataSource-schema-model-field name="paymentCollectionId" type="number"/>
					
					<kendo:dataSource-schema-model-field name="paymentDate" type="date">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="partPayment" type="string" defaultValue="No">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="receiptDate" type="date">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="personName" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="property_No" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="receiptNo" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="excessAmount" type="number" defaultValue="">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="typeOfService" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="accountNo" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="paymentMode" type="string" defaultValue="Cash">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="disputeFlag" type="string" defaultValue="No">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="bankName" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="paymentType" type="string" defaultValue="Pay Bill">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="instrumentDate" type="date">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="instrumentNo" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="cbId" type="number">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="billAmount" type="number">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="accountId" type="number" defaultValue="">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="paymentAmount">
					    <kendo:dataSource-schema-model-field-validation min="0" required="true"/>
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="postedToGl" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="postedGlDate" type="date">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="status" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="tallyStatus" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="createdBy" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="latePaymentAmount" type="number">
					</kendo:dataSource-schema-model-field>

				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>

	</kendo:dataSource>

</kendo:grid>

<kendo:grid-detailTemplate id="paymentSegmentsTemplate">
		<kendo:tabStrip name="tabStrip_#=paymentCollectionId#">
		<kendo:tabStrip-animation>
			</kendo:tabStrip-animation>
			<kendo:tabStrip-items>
			
			<kendo:tabStrip-item selected="true" text="Payment Segments">
                <kendo:tabStrip-item-content>
                    <div class='wethear' style='width: 75%'>
						    <br/>
							<kendo:grid name="paymentSegments_#=paymentCollectionId#" pageable="true"
								resizable="true" sortable="true" reorderable="true"
								selectable="true" scrollable="true" edit="paymentSegmentsEvent" filterable="false" editable="true" >
								<kendo:grid-pageable pageSize="10"></kendo:grid-pageable>
								<kendo:grid-filterable extra="false">
			                    <kendo:grid-filterable-operators>
				                    <kendo:grid-filterable-operators-string eq="Is equal to" />
			                    </kendo:grid-filterable-operators>
		                        </kendo:grid-filterable>
								<kendo:grid-editable mode="popup"  confirmation="Are sure you want to delete this item ?"/>
						       <%-- <kendo:grid-toolbar >
						            <kendo:grid-toolbarItem name="create" text="Add New Instruction" />
						        </kendo:grid-toolbar> --%>
        						<kendo:grid-columns> 
        						    <kendo:grid-column title="paymentSegmentId" field="paymentSegmentId" hidden="true" width="100px">
									</kendo:grid-column> 
									
									<kendo:grid-column title="paymentCollectionId" field="paymentCollectionId" hidden="true" width="100px">
									</kendo:grid-column>
									
									<kendo:grid-column title="Bill&nbsp;Segment" field="billSegment" width="100px" filterable="false">
									<kendo:grid-column-filterable >
										<kendo:grid-column-filterable-ui >
											<script>
												function ledgerStatusFilter(element) {
														element.kendoAutoComplete({
														placeholder : "Enter Service Type",
														dataSource : {
														transport : {
														read : "${commonFilterForPaymentSegmentsUrl}/billSegment"
															}
														}
													});
												}
											</script>
										</kendo:grid-column-filterable-ui>
									</kendo:grid-column-filterable>
									</kendo:grid-column>
									
									<kendo:grid-column title="Bill&nbsp;Month" field="billMonth" format="{0:MMMM,yyyy}" width="150px" filterable="false">
									</kendo:grid-column> 
									
									<kendo:grid-column title="Bill&nbsp;Reference&nbsp;No" field="billReferenceNo" width="130px" filterable="false">
									<kendo:grid-column-filterable >
										<kendo:grid-column-filterable-ui >
											<script>
												function ledgerStatusFilter(element) {
														element.kendoAutoComplete({
														placeholder : "Enter Bill Number",
														dataSource : {
														transport : {
														read : "${commonFilterForPaymentSegmentsUrl}/billReferenceNo"
															}
														}
													});
												}
											</script>
										</kendo:grid-column-filterable-ui>
									</kendo:grid-column-filterable>
									</kendo:grid-column> 
									
									<kendo:grid-column title="Amount" field="amount" format="{0:n2}" width="100px" filterable="false">
									</kendo:grid-column> 
									
									<kendo:grid-column title="Posted&nbsp;Ledger&nbsp;Type" field="postedToLedger" width="120px" filterable="false">
									<kendo:grid-column-filterable >
										<kendo:grid-column-filterable-ui >
											<script>
												function ledgerStatusFilter(element) {
														element.kendoAutoComplete({
														placeholder : "Enter Posted Ledger",
														dataSource : {
														transport : {
														read : "${commonFilterForPaymentSegmentsUrl}/postedToLedger"
															}
														}
													});
												}
											</script>
										</kendo:grid-column-filterable-ui>
									</kendo:grid-column-filterable>
									</kendo:grid-column> 		
									
									<kendo:grid-column title="Posted&nbsp;Ledger&nbsp;Date" field="postedLedgerDate" format="{0:dd/MM/yyyy hh:mm tt}" width="150px" filterable="false">
									</kendo:grid-column> 							
									
									<kendo:grid-column title="Status" field="status" width="80px" >
									<kendo:grid-column-filterable >
										<kendo:grid-column-filterable-ui >
											<script>
												function ledgerStatusFilter(element) {
														element.kendoAutoComplete({
														placeholder : "Enter Status",
														dataSource : {
														transport : {
														read : "${commonFilterForPaymentSegmentsUrl}/status"
															}
														}
													});
												}
											</script>
										</kendo:grid-column-filterable-ui>
									</kendo:grid-column-filterable>
									</kendo:grid-column> 
									
									<%-- <kendo:grid-column title="&nbsp;" width="110px">
										<kendo:grid-column-command >
											 <kendo:grid-column-commandItem name="View Sub Segments" click="calculationLinesClick" />
										</kendo:grid-column-command>
		   							 </kendo:grid-column> --%>
																		     
        							<%-- <kendo:grid-column title="&nbsp;" width="110px" >
							            <kendo:grid-column-command>
							            	<kendo:grid-column-commandItem name="edit"/>
							            	<kendo:grid-column-commandItem name="destroy"/>
							            </kendo:grid-column-command>
							        </kendo:grid-column> --%>
							   						            
        						</kendo:grid-columns>
        						
        						  <kendo:dataSource requestEnd="paymentSegmentsOnRequestEnd" requestStart="paymentSegmentsOnRequestStart">
						            <kendo:dataSource-transport>
						            <kendo:dataSource-transport-read url="${paymentSegmentReadUrl}/#=paymentCollectionId#" dataType="json" type="POST" contentType="application/json"/>
						            <kendo:dataSource-transport-create url="${instructionCreateUrl}/#=paymentCollectionId#" dataType="json" type="GET" contentType="application/json" />
						            <kendo:dataSource-transport-update url="${paymentSegmentUpdateUrl}/#=paymentCollectionId#" dataType="json" type="GET" contentType="application/json" />
						            <kendo:dataSource-transport-destroy url="${paymentSegmentDestroy}" dataType="json" type="GET" contentType="application/json" />
						            </kendo:dataSource-transport>
						            
						            <kendo:dataSource-schema parse="paymentSegmentParse">
						                <kendo:dataSource-schema-model id="paymentSegmentId">
						                    <kendo:dataSource-schema-model-fields>
						                    
						                    <kendo:dataSource-schema-model-field name="paymentSegmentId" type="number">
											<kendo:dataSource-schema-model-field-validation  />
											</kendo:dataSource-schema-model-field>
											
											 <kendo:dataSource-schema-model-field name="paymentCollectionId" type="number">
											<kendo:dataSource-schema-model-field-validation  />
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="postedLedgerDate" type="date">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="billMonth" type="date">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="billSegment" type="string">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="billReferenceNo" type="string">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="postedToLedger" type="string">
											</kendo:dataSource-schema-model-field>
																
											<kendo:dataSource-schema-model-field name="amount" type="number">
											</kendo:dataSource-schema-model-field>
						                    	
						                    </kendo:dataSource-schema-model-fields>
						                 </kendo:dataSource-schema-model>
						             </kendo:dataSource-schema>
						          </kendo:dataSource>
        				</kendo:grid>		
                    </div>
                </kendo:tabStrip-item-content>
            </kendo:tabStrip-item>
			
            <kendo:tabStrip-item text="Payment Information">
                <kendo:tabStrip-item-content>
                     <div class='payment-details' style='width: 285px;'>
							<table>
								<tr><td>
										<dl>
											<table>
											 
												<tr >	
													<td>Dispute&nbsp;Flag&nbsp;</td>
													<td>#= disputeFlag#</td>
												</tr >
												<tr >
													<td>Part&nbsp;Payment&nbsp;</td>
													<td>#= partPayment#</td>
												</tr >
											   <tr >
											        <td>Payment&nbsp;Type&nbsp;</td>
													<td>#= paymentType#</td>
												</tr >
												<tr >	
													<td>Instrument&nbsp;Date&nbsp;</td>
													<td>#= instrumentDate == null? ' ' : kendo.toString(instrumentDate,'dd/MM/yyyy') # </td>
												</tr >
												<tr >	
													<td>Instrument&nbsp;Number&nbsp;</td>
													<td> #= instrumentNo == null? ' ' : instrumentNo # </td>
												</tr >
												<tr >
													<td>Bank&nbsp;Name&nbsp;</td>
													<td> #= bankName == null? ' ' : bankName #</td>
												</tr >
												<tr >
													<td>Posted&nbsp;To&nbsp;GL&nbsp;</td>
													<td> #= postedToGl == null? ' ' : postedToGl # </td>
												</tr >
												<tr >
													<td> Posted&nbsp;GL&nbsp;Date</td>
													<td>#= postedGlDate == null? ' ' : kendo.toString(postedGlDate,'dd/MM/yyyy hh:mm tt') #</td>
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
	</kendo:grid-detailTemplate>
<!-- ==========================================generate XML==================================== -->	
	<div id=generateXMLDiv style="display: none;">
	<form id="generateXMLDivform">
		<table style="height: 110px;">
             <tr>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			 <tr>
				<td>Month</td>
				<td><input type="date" name="fromMonth" id="fromMonth" required="required"></td>
				<td></td>
			</tr>

			<tr>
				<td class="left" align="center" colspan="4">
					<button type="submit" id="exportXml" class="k-button"
						style="padding-left: 10px">Export To XML</button>
			</tr>

		</table>
	</form>
</div>
<!-- =================================================================================================== -->	
<div id="paymentCalcLinesPopUp"></div>	
<div id="billTable" style="color: black"></div>
<div id="alertsBox" title="Alert"></div>
<!-- <a href="#" onclick="javascript:CallPrint('billTable')">Print</a> -->
<script>

$( document ).ready(function() {	
	
	var todayDate = new Date();
	var picker = $("#fromMonthpicker").kendoDatePicker({
		start: "month",
		depth: "month",
		  value:new Date(),
				 format: "dd/MM/yyyy"
			}).data("kendoDatePicker"),
    dateView = picker.dateView;
	dateView.calendar.element.removeData("dateView");        
	picker.max(todayDate);
  	picker.options.depth = dateView.options.depth = 'month';
  	picker.options.start = dateView.options.start = 'month';
   	picker.value(picker.value());
   
   	$('#fromMonthpicker').keyup(function() {
		$('#fromMonthpicker').val("");
	});
   	var todayDate = new Date();
	var picker = $("#toMonthpicker").kendoDatePicker({
		start: "month",
		depth: "month",
		  value:new Date(),
				 format: "dd/MM/yyyy"
			}).data("kendoDatePicker"),
    dateView = picker.dateView;
	dateView.calendar.element.removeData("dateView");        
	picker.max(todayDate);
  	picker.options.depth = dateView.options.depth = 'month';
  	picker.options.start = dateView.options.start = 'month';
   	picker.value(picker.value());
   
   	$('#toMonthpicker').keyup(function() {
		$('#toMonthpicker').val("");
	});
});

function searchByMonth() {
    var fromDate = $('#fromMonthpicker').val();
    var toDate = $('#toMonthpicker').val();
    var splitmonth=fromDate.split("/");
	   var splitmonth1=toDate.split("/");
	   if(splitmonth[2]>splitmonth1[2])
		   {
		   alert("To Date should be greater than From Date");
		   return false;
		   }
	   if(splitmonth[2]==splitmonth1[2])
		   {
		   if(splitmonth[1]>splitmonth1[1])
			   {
			   alert("To Date should be greater than From Date");
			   return false;
			   }
		   else if(splitmonth[1]==splitmonth1[1])
			   {
			   if(splitmonth[0]>splitmonth1[0])
				   {
				   alert("To Date should be greater than From Date");
				   return false;
				   }
			   
			   }
		   }
	  $.ajax({
		type : "GET",
		url : "./billingPayments/searchPaymentDataByMonth",
		dataType : "json",
		data : {
			fromDate : fromDate,
			toDate : toDate
		},
		success : function(result) {
			parse(result);
			var grid = $("#grid").getKendoGrid();
			var data = new kendo.data.DataSource();
			grid.dataSource.data(result);
			grid.refresh();
		}
	}); 
}

var excessAmount = "";
var latepaymentt="";
var receiptDate="";
var rdate="";
function billingPaymentsDataBound(e){
	var data = this.dataSource.view(),row;
	var grid = $("#grid").data("kendoGrid");
    for (var i = 0; i < data.length; i++) {
    	var currentUid = data[i].uid;
        row = this.tbody.children("tr[data-uid='" + data[i].uid + "']");
        
        var paymentStatus = data[i].status;
        var tallyStatus = data[i].tallyStatus;
         
        if (paymentStatus == 'Created' || paymentStatus == 'Cancelled' || tallyStatus=='Posted') {
        	
        	var currenRow = grid.table.find("tr[data-uid='" + currentUid+ "']");
			var postTotally = $(currenRow).find(".k-grid-PostToTally");
			postTotally.hide();
        } 
        
        if (paymentStatus == 'Created') {

        	var currenRow = grid.table.find("tr[data-uid='" + currentUid+ "']");
			/* var cancellationButton = $(currenRow).find(".k-grid-Cancellation");
			cancellationButton.hide(); */
			
			/* var printReceiptButton = $(currenRow).find(".k-grid-PrintReceipt");
			printReceiptButton.hide(); */
			
			var printCancellationReceiptButton = $(currenRow).find(".k-grid-CancellationReceipt");
			printCancellationReceiptButton.hide();
			
        	
		}  if (paymentStatus == 'Approved') {
			
			var currenRow = grid.table.find("tr[data-uid='" + currentUid+ "']");
			var cancellationButton = $(currenRow).find(".k-grid-Cancellation");
			cancellationButton.hide(); 
			
			/* var printReceiptButton = $(currenRow).find(".k-grid-PrintReceipt");
			printReceiptButton.hide(); */
			
			var printCancellationReceiptButton = $(currenRow).find(".k-grid-CancellationReceipt");
			printCancellationReceiptButton.hide();
			
		}  if (paymentStatus == 'Posted') {

			var currenRow = grid.table.find("tr[data-uid='" + currentUid+ "']");
			
			var postButton = $(currenRow).find(".k-grid-Active");
			postButton.hide(); 
			
			var cancellationButton = $(currenRow).find(".k-grid-Cancellation");
			cancellationButton.hide(); 
			
			var printCancellationReceiptButton = $(currenRow).find(".k-grid-CancellationReceipt");
			printCancellationReceiptButton.hide();
			
		} if (paymentStatus == 'Cancelled') {
			
			var currenRow = grid.table.find("tr[data-uid='" + currentUid+ "']");
			var cancellationButton = $(currenRow).find(".k-grid-Cancellation");
			cancellationButton.hide(); 
			
			var postButton = $(currenRow).find(".k-grid-Active");
			postButton.hide();
			
			var printReceiptButton = $(currenRow).find(".k-grid-PrintReceipt");
			printReceiptButton.hide();
		}
    }
}

var SelectedRowId = "";

function onChangePaymentSegmet(arg) {
	 var gview = $("#grid").data("kendoGrid");
	 var selectedItem = gview.dataItem(gview.select());
	 SelectedRowId = selectedItem.paymentCollectionId;
	 this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
    
}

var printFlag = "";
function paymentReceipt(){	
	$("#billTable").empty();
	var paymentCollectionId = "";
	var paymentStatus = "";
	var gridParameter = $("#grid").data("kendoGrid");
	var selectedAddressItem = gridParameter.dataItem(gridParameter.select());
	paymentCollectionId = selectedAddressItem.paymentCollectionId;
	paymentStatus = selectedAddressItem.status;
	
	if(paymentStatus!="Cancelled"){
		var result=securityCheckForActionsForStatus("./Collections/Payments/receiptGenarationButton");	    
		if(result=="success"){ 
		$.ajax
		({
			type : "POST",
			url : "./collections/billingPayments/getPaymentDetails",
			data : {
				paymentCollectionId : paymentCollectionId
			},
			dataType:"text",
			success : function(response) 
			{
				$("#billTable").html(response);
			
				var wnd2 = $("#billTable").kendoWindow({
				    visible:false,
				    resizable: false,
				    modal: true,
				    actions: ["Custom","Close"],
	                title: "Payment Receipt"
				}).data("kendoWindow");
				
				wnd2.center().open();
				
				var kendoWindow = $("#billTable").data("kendoWindow");
				kendoWindow.wrapper.find(".k-i-custom").addClass('icon-printer').addClass('fa').removeClass('k-i-custom').removeClass('k-icon');
				$('.fa').html('');
				$("#billTable").data("kendoWindow").wrapper.find(".icon-printer").click(function(e){
					 var prtContent = document.getElementById('billTable');
			            var WinPrint = window.open('', '', 'letf=0,top=0,width=400,height=400,toolbar=0,scrollbars=0,status=0');
			            WinPrint.document.write(prtContent.innerHTML);
			            WinPrint.document.close();
			            WinPrint.focus();
			            WinPrint.print();
			            WinPrint.close();
				});  
				
			}
		});
	  }
		
	} else{
		alert("You can not generate receipt cancelled payment");
	}
}


function postPaymentToTally(){	
	var gridParameter = $("#grid").data("kendoGrid");
	var selectedAddressItem = gridParameter.dataItem(gridParameter.select());
	paymentCollectionId = selectedAddressItem.paymentCollectionId;

	$('tr[aria-selected="true"]').find('td:nth-child(30)').html("");
	$('tr[aria-selected="true"]').find('td:nth-child(30)').html("<img src='./resources/images/151.gif' width='100px' height='25px' />");
		$.ajax
		({
			type : "POST",
			url : "./collections/billingPayments/postToTally",
			dataType:"text",
			data : {
				paymentCollectionId : paymentCollectionId
			},
			success : function(response) 
			{
				alert(response);
				window.location.reload();
				
			}
		});
	
}

function paymentCancelReceipt(){	
	var paymentCollectionId = "";
	var paymentStatus = "";
	var gridParameter = $("#grid").data("kendoGrid");
	var selectedAddressItem = gridParameter.dataItem(gridParameter.select());
	paymentCollectionId = selectedAddressItem.paymentCollectionId;
	paymentStatus = selectedAddressItem.status;
	
	if(paymentStatus=="Cancelled"){
		var result=securityCheckForActionsForStatus("./Collections/Payments/cancelReceiptButton");	    
		if(result=="success"){ 
		$.ajax
		({
			type : "POST",
			url : "./collections/billingPayments/getPaymentDetails",
			data : {
				paymentCollectionId : paymentCollectionId
			},
			dataType:"text",
			success : function(response) 
			{
				$("#billTable").html(response);
			
				var wnd2 = $("#billTable").kendoWindow({
				    visible:false,
				    resizable: false,
				    modal: true,
				    actions: ["Custom","Close"],
	                title: "Payment Cancelled Receipt"
				}).data("kendoWindow");
				
				wnd2.center().open();
				
				var kendoWindow = $("#billTable").data("kendoWindow");
				kendoWindow.wrapper.find(".k-i-custom").addClass('icon-printer').addClass('fa').removeClass('k-i-custom').removeClass('k-icon');
				$('.fa').html('');
				$("#billTable").data("kendoWindow").wrapper.find(".icon-printer").click(function(e){
					 var prtContent = document.getElementById('billTable');
			            var WinPrint = window.open('', '', 'letf=0,top=0,width=400,height=400,toolbar=0,scrollbars=0,status=0');
			            WinPrint.document.write(prtContent.innerHTML);
			            WinPrint.document.close();
			            WinPrint.focus();
			            WinPrint.print();
			            WinPrint.close();
				});  	
				
			}
		});
	  }
	} else{
		alert("You can generate receipt only cancel payment");
	}
}

//for parsing timestamp data fields
function parse (response) {
    $.each(response, function (idx, elem) {   
    	   if(elem.paymentDate=== null){
    		   elem.paymentDate = "";
    	   }else{
    		   elem.paymentDate = kendo.parseDate(new Date(elem.paymentDate),'dd/MM/yyyy HH:mm');
    	   }
    	   
    	   if(elem.postedGlDate=== null){
               elem.postedGlDate = " ";
            }else{
               elem.postedGlDate = kendo.parseDate(new Date(elem.postedGlDate),'dd/MM/yyyy HH:mm');
            }
            
            if(elem.receiptDate=== null){
                elem.receiptDate = " ";
             }else{
                elem.receiptDate = kendo.parseDate(new Date(elem.receiptDate),'dd/MM/yyyy');
             }
            
            if(elem.instrumentDate=== null){
                elem.instrumentDate = " ";
             }else{
                elem.instrumentDate = kendo.parseDate(new Date(elem.instrumentDate),'dd/MM/yyyy');
             }
       });
               
       return response;
}

var tableData = "";
var totalAmount=0;
	function calculationLinesClick(e){
		
	var widget = $("#paymentSegments_"+SelectedRowId).data("kendoGrid");
    var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
		
	$("#paymentCalcLinesPopUp").empty();
	 var todcal=$( "#paymentCalcLinesPopUp" );
	  todcal.kendoWindow({
	      width: 500,
	      height: 'auto',
	      modal: true,
	      draggable: true,
	      position: { top: 150,left:500 },
	      title: "Payment Information"
	  }).data("kendoWindow").open();

	   todcal.kendoWindow("open");
	 
	 var result=securityCheckForActionsForStatus("./Collections/PaymentSegments/viewSubSegmentsButton");	    
	 if(result=="success"){  
	 $.ajax
		({			
			type : "POST",
			url : "./paymentSegments/paymentSegmentCalcLinesRead/"+dataItem.id,
			async: false,
			dataType : "JSON",
			success : function(response) 
			{	    
				tableData="<table class='tftable' border='1' id='tableData'><tr><th>Transaction Name</th><th>Amount</th></tr>";
				totalAmount=0;
				 $.each(response, function (idx, elem) {	
					 
					 var i=elem.amount;
					 totalAmount+=i;
					 tableData+='<tr><td>'+elem.transactionName+'</td><td>'+elem.amount+'</td></tr>';
			        });
				 tableData+="</table><br><p style='margin-left: 157px;color: rgba(155, 57, 57, 0.97);font-size: 19px;'>Total Amount :"+Math.round(totalAmount)+"</p>";
				 $('#paymentCalcLinesPopUp').html(tableData);  
			
			} 
		});
	  } 
}

function paymentSegmentsEvent(e)
{
	 $('#paymentCalcLinesPopUp').hide();
	 $('div[data-container-for="spInstructionId"]').remove();
	 $('label[for="spInstructionId"]').closest('.k-edit-label').remove();  
	 
	 $('div[data-container-for="servicePointId"]').remove();
	 $('label[for="servicePointId"]').closest('.k-edit-label').remove(); 
	 
	 $('div[data-container-for="dummy"]').remove();
	 $('label[for="dummy"]').closest('.k-edit-label').remove();
	 
	 $('div[data-container-for="status"]').remove();
	 $('label[for="status"]').closest('.k-edit-label').remove(); 
	 
	 
	 $('div[data-container-for="createdBy"]').remove();
	 $('label[for="createdBy"]').closest('.k-edit-label').remove(); 
	 
		if (e.model.isNew()) 
	    {
			
			$(".k-window-title").text("Add New Instruction");
			$(".k-grid-update").text("Save");
			
			
	    }
		else{
			
			setApCode = $('input[name="spInstructionId"]').val();
			$('label[for="status"]').parent().hide();  
			$('div[data-container-for="status"]').hide();
			$(".k-window-title").text("Edit Instruction Details");
		}
	}


function canclePayment()
{
	 var r = confirm("Are you sure to cancel this payment?");
	 if(r == true){
		 var paymentCollectionId="";
			var gridParameter = $("#grid").data("kendoGrid");
			var selectedAddressItem = gridParameter.dataItem(gridParameter.select());
			paymentCollectionId = selectedAddressItem.paymentCollectionId;
			
			var result=securityCheckForActionsForStatus("./Collections/Payments/cancelButton");	  
			if(result=="success"){ 
			$.ajax
			({			
				type : "POST",
				url : "./billingPayments/paymentStatusUpdate/"+paymentCollectionId,
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
					$('#grid').data('kendoGrid').dataSource.read();
				   }
			    });
			   } 
	 }
}



function paymentSegmentParse(response) {
    $.each(response, function (idx, elem) {   
    	   if(elem.postedLedgerDate== null){
    		   elem.postedLedgerDate = "";
    	   }else{
    		   elem.postedLedgerDate = kendo.parseDate(new Date(elem.postedLedgerDate),'dd/MM/yyyy HH:mm');
    	   }
       });
               
       return response;
}

function dropDownChecksEditorPaymentType(container, options) {
	var res = (container.selector).split("=");
	var attribute = res[1].substring(0,res[1].length-1);
	
	$('<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '" name = "'+attribute+'" id = "paymentType"/>')
			.appendTo(container).kendoDropDownList({
				defaultValue : false,
				sortable : true,
				select : selectedPaymentType,
				dataSource : {
					transport : {
						read : "${allChecksUrl}/"+attribute,
					}
				}
			});
	 $('<span class="k-invalid-msg" data-for="'+attribute+'"></span>').appendTo(container);
}

var paymentTypeName="";
function selectedPaymentType(e){
	var dataItem = this.dataItem(e.item.index());
	paymentTypeName=dataItem.text;
	
	if(paymentTypeName=="Pay Bill"){
		
		//$('input[name="paymentAmount"]').prop("readonly",true);
		$('div[data-container-for="billAmount"]').show();
		$('label[for="billAmount"]').closest('.k-edit-label').show();
		
		$('div[data-container-for="partPayment"]').show();
		$('label[for="partPayment"]').closest('.k-edit-label').show();
		
		// Getting accounts from bill table and post type is Bill Code starts 
		var comboBoxDataSource = new kendo.data.DataSource({
	        transport: {
	            read: {
	                url     : "./billingPayments/accountNumberAutocomplete",
	                dataType: "json",
	                type    : 'GET'
	            }
	        },
		           
		 });
		        
	 $("#accountId").kendoComboBox({
	    dataSource    : comboBoxDataSource,
	    dataType: 'JSON',
	    dataTextField : "accountNo",
	    dataValueField: "accountId",
		placeholder: "Enter Account Number",
		headerTemplate : '<div class="dropdown-header">'
			+ '<span class="k-widget k-header">Photo</span>'
			+ '<span class="k-widget k-header">Contact info</span>'
			+ '</div>',
		template : '<table><tr><td rowspan=2><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.personId#'/>" width=40 height=55 alt=\"No Image to Display\" /></span></td>'
			+ '<td align="left"><span class="k-state-default"><b>#: data.personName #</b></span><br>'
			+ '<span class="k-state-default"><i>#: data.accountNo #</i></span><br>'
			+ '<span class="k-state-default"><i>#: data.personType #</i></span></td></tr></table>',
		 select : accountIdFunction,
		 change : function (e) {
	            if (this.value() && this.selectedIndex == -1) {                    
					alert("Account doesn't exist!");
	                $("#accountId").data("kendoComboBox").value("");
	        	}
		    }
	});
	 $("#accountId").data("kendoComboBox").value("");
	 $("#typeOfService1").data("kendoDropDownList").value("");
	// Getting accounts from bill table and post type is Bill Code ends
	 
	// Getting consolidate bill amount from bill table and post type is Bill Code starts
	 var consolidateDataSource = new kendo.data.DataSource({
	        transport: {
	            read: {
	                url     : "./billingPayments/getConsolidatedAmount",
	                dataType: "json",
	                type    : 'GET'
	            }
	        },
		           
		 });
		        
	 $("#consolidatedId").kendoComboBox({
	    dataSource    : consolidateDataSource,
	    placeholder: "Select Amount",
        autoBind: false,
        dataType: 'JSON',
        optionLabel : "Select",		
		cascadeFrom:"typeOfService",
		dataTextField : "cbId",
	    dataValueField: "billAmount",
		select : selectPayment
	});
	 $("#consolidatedId").data("kendoComboBox").value("");
	 
	// Getting consolidate bill amount from bill table and post type is Bill Ends starts
		
	}else if(paymentTypeName=="Pay Deposit"){
		
		$('div[data-container-for="partPayment"]').hide();
		$('label[for="partPayment"]').closest('.k-edit-label').hide();
		$('#partPayCheckBoxId').hide();
		
		$("input[name=latePaymentAmount]").data("kendoNumericTextBox").value(0);
		
		// Getting accounts from bill table and post type is Deposit Code starts
		var comboBoxDataSource = new kendo.data.DataSource({
	        transport: {
	            read: {
	                url     : "./billingPayments/accountNumberBasedOnPayDeposit",
	                dataType: "json",
	                type    : 'GET'
	            }
	        },
		           
		 });
		        
	 $("#accountId").kendoComboBox({
	    dataSource    : comboBoxDataSource,
	    dataType: 'JSON',
	    dataTextField : "accountNo",
	    dataValueField: "accountId",
		placeholder: "Enter Account Number",
		headerTemplate : '<div class="dropdown-header">'
			+ '<span class="k-widget k-header">Photo</span>'
			+ '<span class="k-widget k-header">Contact info</span>'
			+ '</div>',
		template : '<table><tr><td rowspan=2><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.personId#'/>" width=40 height=55 alt=\"No Image to Display\" /></span></td>'
			+ '<td align="left"><span class="k-state-default"><b>#: data.personName #</b></span><br>'
			+ '<span class="k-state-default"><i>#: data.accountNo #</i></span><br>'
			+ '<span class="k-state-default"><i>#: data.personType #</i></span></td></tr></table>',
		 select : accountIdFunction,
		 change : function (e) {
	            if (this.value() && this.selectedIndex == -1) {                    
					alert("Account doesn't exist!");
	                $("#accountId").data("kendoComboBox").value("");
	        	}
		    }
	});
	 $("#accountId").data("kendoComboBox").value("");
	 $("#typeOfService1").data("kendoDropDownList").value("");
	// Getting accounts from bill table and post type is Deposit Code ends
	 
	// Getting consolidate bill amount from bill table and post type is Deposit Code starts
	 var consolidateDataSource = new kendo.data.DataSource({
	        transport: {
	            read: {
	                url     : "./billingPayments/getConsolidatedAmountBasedOnDeposit",
	                dataType: "json",
	                type    : 'GET'
	            }
	        },
		           
		 });
		        
	 $("#consolidatedId").kendoComboBox({
	    dataSource    : consolidateDataSource,
	    placeholder: "Select Amount",
     autoBind: false,
     dataType: 'JSON',
     optionLabel : "Select",		
		cascadeFrom:"accountId",
		dataTextField : "cbId",
	    dataValueField: "billAmount",
		select : selectPayment
	});
	 $("#consolidatedId").data("kendoComboBox").value("");
	// Getting consolidate bill amount from bill table and post type is Deposit Code ends	
	 
	} else if(paymentTypeName=="Pay Advance Bill"){
		
		$('div[data-container-for="partPayment"]').hide();
		$('label[for="partPayment"]').closest('.k-edit-label').hide();
		$('#partPayCheckBoxId').hide();
		
		$("input[name=latePaymentAmount]").data("kendoNumericTextBox").value(0);
		
		// Getting accounts from advance bill table and post type is Advance Bill Code starts
		var comboBoxDataSource = new kendo.data.DataSource({
	        transport: {
	            read: {
	                url     : "./billingPayments/accountNumberBasedOnPayAdvanceBill",
	                dataType: "json",
	                type    : 'GET'
	            }
	        },
		           
		 });
		        
	 $("#accountId").kendoComboBox({
	    dataSource    : comboBoxDataSource,
	    dataType: 'JSON',
	    dataTextField : "accountNo",
	    dataValueField: "accountId",
		placeholder: "Enter Account Number",
		headerTemplate : '<div class="dropdown-header">'
			+ '<span class="k-widget k-header">Photo</span>'
			+ '<span class="k-widget k-header">Contact info</span>'
			+ '</div>',
		template : '<table><tr><td rowspan=2><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.personId#'/>" width=40 height=55 alt=\"No Image to Display\" /></span></td>'
			+ '<td align="left"><span class="k-state-default"><b>#: data.personName #</b></span><br>'
			+ '<span class="k-state-default"><i>#: data.accountNo #</i></span><br>'
			+ '<span class="k-state-default"><i>#: data.personType #</i></span></td></tr></table>',
		 select : accountIdFunction,
		 change : function (e) {
	            if (this.value() && this.selectedIndex == -1) {                    
					alert("Account doesn't exist!");
	                $("#accountId").data("kendoComboBox").value("");
	        	}
		    }
	});
	 $("#accountId").data("kendoComboBox").value("");
	
	  $("#typeOfService1").data("kendoDropDownList").value("");
	// Getting accounts from advance bill table and post type is Advance Bill Code ends
	 
	// Getting consolidate bill amount from AdvanceBill table and post type is Advance Bill Code starts
	 var consolidateDataSource = new kendo.data.DataSource({
	        transport: {
	            read: {
	                url     : "./billingPayments/getConsolidatedAmountBasedOnPayAdvance",
	                dataType: "json",
	                type    : 'GET'
	            }
	        },
		           
		 });
		        
	 $("#consolidatedId").kendoComboBox({
	    dataSource    : consolidateDataSource,
	    placeholder: "Select Amount",
     autoBind: false,
     dataType: 'JSON',
     optionLabel : "Select",		
		cascadeFrom:"accountId",
		dataTextField : "cbId",
	    dataValueField: "billAmount",
		select : selectPayment
	});
	 $("#consolidatedId").data("kendoComboBox").value("");
	// Getting consolidate bill amount from Advance Bill table and post type is Advance Bill Code ends	
		
	}/* else{
		$('div[data-container-for="billAmount"]').hide();
		$('label[for="billAmount"]').closest('.k-edit-label').hide();
		$('input[name="paymentAmount"]').prop("readonly",false);
		$('div[data-container-for="paymentAmount"]').show();
		$('label[for="paymentAmount"]').closest('.k-edit-label').show();
	} */
}

function propertyNoEditor(container, options) {
	  $('<input name="propertyEE" id="propertyId" data-text-field="propertyNo" data-value-field="propertyId" data-bind="value:' + options.field + '"/>')
	     .appendTo(container)
	     .kendoComboBox({
			dataType: 'JSON',
			placeholder: "Enter Property No",
			select : onselectedProperty,
			dataSource: {
				transport: {
					read: "${getAllPropertyNo}"
				}
			},
			change : function (e) {
	            if (this.value() && this.selectedIndex == -1) {                    
					alert("Property No doesn't exist!");
	                $("#propertyId").data("kendoComboBox").value("");
	        	}
		    }
		});
	  $('<span class="k-invalid-msg" data-for="propertyEE"></span>').appendTo(container);
	}
	
	function onselectedProperty(e){
		
		var dataItem = this.dataItem(e.item.index());
		var propertyId = dataItem.propertyId;
		
		var comboBoxDataSource = new kendo.data.DataSource({
	        transport: {
	            read: {
	                url     : "./billingPayments/getAccountsBasedOnseletedProperty/"+propertyId,
	                dataType: "json",
	                type    : 'GET'
	            }
	        },
		           
		 });
		        
	 $("#accountId").kendoComboBox({
	    dataSource    : comboBoxDataSource,
	    dataType: 'JSON',
	    dataTextField : "accountNo",
	    dataValueField: "accountId",
		placeholder: "Enter Account Number",
		headerTemplate : '<div class="dropdown-header">'
			+ '<span class="k-widget k-header">Photo</span>'
			+ '<span class="k-widget k-header">Contact info</span>'
			+ '</div>',
		template : '<table><tr><td rowspan=2><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.personId#'/>" width=40 height=55 alt=\"No Image to Display\" /></span></td>'
			+ '<td align="left"><span class="k-state-default"><b>#: data.personName #</b></span><br>'
			+ '<span class="k-state-default"><i>#: data.accountNo #</i></span><br>'
			+ '<span class="k-state-default"><i>#: data.personType #</i></span></td></tr></table>',
		 select : accountIdFunction,
		 change : function (e) {
	            if (this.value() && this.selectedIndex == -1) {                    
					alert("Account doesn't exist!");
	                $("#accountId").data("kendoComboBox").value("");
	        	}
		    }
	});
	 $("#accountId").data("kendoComboBox").value("");	 
	 $("#typeOfService1").data("kendoDropDownList").value("");
	}

function dropDownChecksEditor(container, options) {
	var res = (container.selector).split("=");
	var attribute = res[1].substring(0,res[1].length-1);
	
	$('<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '"required ="true" name = "'+attribute+'" id = "paymentMode"/>')
			.appendTo(container).kendoComboBox({
				defaultValue : false,
				sortable : true,
				change : selectModeType,
				dataSource : {
					transport : {
						read : "${allChecksUrl}/"+attribute,
					}
				}
			});
	 $('<span class="k-invalid-msg" data-for="'+attribute+'"></span>').appendTo(container);
}
var selectedPaymentMode="";
function selectModeType(e){
	
	selectedPaymentMode=$("#paymentMode").val();
	
	if(selectedPaymentMode!="Cash"){
		
		if(selectedPaymentMode=="RTGS/NEFT"){
			   
			$('label[for="instrumentDate"]').text("Created Date *");
		    $('label[for="instrumentNo"]').text("Account Number *");
		    $('label[for="bankName"]').text("Bank Name");
		    
		    $('div[data-container-for="instrumentNo"]').show();
			$('label[for="instrumentNo"]').closest('.k-edit-label').show();
			
			$('div[data-container-for="instrumentDate"]').show();
			$('label[for="instrumentDate"]').closest('.k-edit-label').show();
			
			$('div[data-container-for="bankName"]').show();
			$('label[for="bankName"]').closest('.k-edit-label').show();
			
	
			
			var partPaymentFlag = $("#partPayment").val();
			
			if(partPaymentFlag == null || partPaymentFlag == 'undefined'  || partPaymentFlag === "" ){
				
			}else if(partPaymentFlag == "Yes"){
				paymentAmountChange(partPaymentFlag);
			}else{
				var sumAmount="";
				var lateamount=$("#latePaymentAmount").val();		
				var billAmoun=$("#consolidatedId").val();
				var excessAmount = $("#excessAmount").val();
				
				if(excessAmount == null || excessAmount == 'undefined'  || excessAmount === "" ){
					sumAmount = (parseFloat(billAmoun) + parseFloat(lateamount));
				}else{
					sumAmount = (parseFloat(billAmoun) + parseFloat(lateamount) + parseFloat(excessAmount));
				}
				$("#paymentAmount").data("kendoNumericTextBox").value(sumAmount);
				paymentAmount = sumAmount;
			}

			
			
		} else{
			
			$('label[for="instrumentDate"]').text(selectedPaymentMode+" Date *");
		    $('label[for="instrumentNo"]').text(selectedPaymentMode+" Number *");
		    
		    $('div[data-container-for="instrumentNo"]').show();
			$('label[for="instrumentNo"]').closest('.k-edit-label').show();
			
			$('div[data-container-for="instrumentDate"]').show();
			$('label[for="instrumentDate"]').closest('.k-edit-label').show();
			
			$('div[data-container-for="bankName"]').show();
			$('label[for="bankName"]').closest('.k-edit-label').show();
			
			var partPaymentFlag = $("#partPayment").val();
			
			if(partPaymentFlag == null || partPaymentFlag == 'undefined'  || partPaymentFlag === "" ){
				
			}else if(partPaymentFlag == "Yes"){
				paymentAmountChange(partPaymentFlag);
			}else{
			
			var sumAmount="";
			var lateamount=$("#latePaymentAmount").val();		
			var billAmoun=$("#consolidatedId").val();
			var excessAmount = $("#excessAmount").val();
			
			if(excessAmount == null || excessAmount == 'undefined'  || excessAmount === "" ){				
				sumAmount = (parseFloat(billAmoun) + parseFloat(lateamount));				
			}else{
				sumAmount = (parseFloat(billAmoun) + parseFloat(lateamount) + parseFloat(excessAmount));
			}
			$("#paymentAmount").data("kendoNumericTextBox").value(sumAmount);
			paymentAmount = sumAmount;
			}
		}
		
	}else if(selectedPaymentMode == "Cash"){
		$('div[data-container-for="instrumentNo"]').hide();
		$('label[for="instrumentNo"]').closest('.k-edit-label').hide();
		 $('label[for="bankName"]').text("Bank Name*");
		
		$('div[data-container-for="instrumentDate"]').hide();
		$('label[for="instrumentDate"]').closest('.k-edit-label').hide();
		
		$('div[data-container-for="bankName"]').hide();
		$('label[for="bankName"]').closest('.k-edit-label').hide();
		
		var partPaymentFlag = $("#partPayment").val();
		
		if(partPaymentFlag == null || partPaymentFlag == 'undefined'  || partPaymentFlag === "" ){
			
		}else if(partPaymentFlag == "Yes"){
			paymentAmountChange(partPaymentFlag);
		}else{
			var sumAmount="";
			var lateamount=$("#latePaymentAmount").val();		
			var billAmoun=$("#consolidatedId").val();
			var excessAmount = $("#excessAmount").val();
			if(excessAmount == null || excessAmount == 'undefined'  || excessAmount === "" ){
				sumAmount = (parseFloat(billAmoun) + parseFloat(lateamount));
			}else{
				sumAmount = (parseFloat(billAmoun) + parseFloat(lateamount) + parseFloat(excessAmount));
			}
			$("#paymentAmount").data("kendoNumericTextBox").value(sumAmount);
			paymentAmount = sumAmount;
		}
		
		
		
	}
}

function dropDownChecksEditorDisputeFlag(container, options) {
	var res = (container.selector).split("=");
	var attribute = res[1].substring(0,res[1].length-1);
	
	$('<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '" name = "'+attribute+'" id = "'+attribute+'Id"/>')
			.appendTo(container).kendoDropDownList({
				defaultValue : false,
				sortable : true,
				dataSource : {
					transport : {
						read : "${allChecksUrl}/"+attribute,
					}
				}
			});
	 $('<span class="k-invalid-msg" data-for="'+attribute+'"></span>').appendTo(container);
}

function bankNames(container, options) 
{
		$('<input name="bankName" id="bankName" data-text-field="bankName" data-value-field="bankName" data-bind="value:' + options.field + '"/>')
		.appendTo(container).kendoComboBox({
		 autoBind : false,
		 placeholder : "Select Bank",
		 dataSource : {
		  transport : {  
		   read :  "${getAllBankNames}"
		  }
		 }
		});
		
		$('<span class="k-invalid-msg" data-for="bankName"></span>').appendTo(container);
}

var selectedAccountId = "";
function accountNumberAutocomplete(container, options) {
	  $('<input name="accountNumberEE" id="accountId" data-text-field="accountNo" required="true" validationmessage="Account number is required" data-value-field="accountId" data-bind="value:' + options.field + '"/>')
	     .appendTo(container)
	     .kendoComboBox({
			dataType: 'JSON',
			placeholder: "Enter Account Number",
			headerTemplate : '<div class="dropdown-header">'
				+ '<span class="k-widget k-header">Photo</span>'
				+ '<span class="k-widget k-header">Contact info</span>'
				+ '</div>',
			template : '<table><tr><td rowspan=2><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.personId#'/>" width=40 height=55 alt=\"No Image to Display\" /></span></td>'
				+ '<td align="left"><span class="k-state-default"><b>#: data.personName #</b></span><br>'
				+ '<span class="k-state-default"><i>#: data.accountNo #</i></span><br>'
				+ '<span class="k-state-default"><i>#: data.property_No #</i></span></td></tr></table>',
			select : accountIdFunction,
			dataSource: {
				transport: {
					read: "${accountNumberAutocomplete}"
				}
			},
			change : function (e) {
	            if (this.value() && this.selectedIndex == -1) {                    
					alert("Account doesn't exist!");
	                $("#accountId").data("kendoComboBox").value("");
	        	}
		    }
		});
	  $('<span class="k-invalid-msg" data-for="accountNumberEE"></span>').appendTo(container);
	 
	}
	
	function checkPaymentPosted(e){
		
	var	 selectedAccountId = $("#accountId").val();
		 var selectedPaymentType = $("#paymentType").val();
	
	var typeOfService=e;
	
	
		 var msg = "";
		 if(selectedPaymentType=="Pay Bill"){
			 msg = "bill";
		 }else if(selectedPaymentType=="Pay Advance Bill"){
			 msg = "advance Bill";
		 }else if(selectedPaymentType=="Pay Deposit"){
			 msg = "deposit";
		 }
		
		$.ajax
		({
			type : "POST",
			url : "./billingPayments/checkForNotPostedAccounts/" +selectedAccountId+"/" +selectedPaymentType +"/" +typeOfService,
			async: false,
			dataType : "JSON",
			success : function(response) 
			{
				if(response==false){
					 var grid = $("#grid").data("kendoGrid");
					 grid.cancelChanges();
					 alert("Your previous "+msg+" payment is not posted, so please post it to create next payment");
				} 
			}
		});
	}
	
	function accountIdFunction(e){
		
		 var dataItem = this.dataItem(e.item.index());
		 selectedAccountId = dataItem.accountId;
		 var selectedPaymentType = $("#paymentType").val();
		 $("#typeOfService1").data("kendoDropDownList").value("");
		 var msg = "";
		 if(selectedPaymentType=="Pay Bill"){
			 msg = "bill";
		 }else if(selectedPaymentType=="Pay Advance Bill"){
			 msg = "advance Bill";
		 }else if(selectedPaymentType=="Pay Deposit"){
			 msg = "deposit";
		 }
		/*  $.ajax
			({
				type : "POST",
				url : "./billingPayments/checkForNotPostedAccounts/" +selectedAccountId+"/"+selectedPaymentType,
				async: false,
				dataType : "JSON",
				success : function(response) 
				{
					if(response==false){
						 var grid = $("#grid").data("kendoGrid");
						 grid.cancelChanges();
						 alert("Your previous "+msg+" payment is not posted, so please post it to create next payment");
					} 
				}
			}); */
		// billAmountchange();
	}

	function excessAmountEditor(container, options) {
		$('<input id="excessAmount" name="excessAmount" readonly="true" data-bind="value:' + options.field + '"/>')
				.appendTo(container).kendoNumericTextBox({
					spinners : false,
					min : 0,

				});

	}

	
	function 	serviceTypeEditor(container, options){
		var res = (container.selector).split("=");
		var attribute = res[1].substring(0, res[1].length - 1);

		$(
				'<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '" name = "'+attribute+'" id = "typeOfService1"/>')
				.appendTo(container).kendoDropDownList({
					 optionLabel : {
						text : "Select",
						value : "",
					},
					defaultValue : false,
					sortable : true,
					 select : billAmountchange,
					dataSource : {
						transport : {
							read : "${allChecksUrl}/" + attribute,
						}
					}
				});
		$('<span class="k-invalid-msg" data-for="'+attribute+'"></span>')
				.appendTo(container);
		
	}
	
	function 	latePaymentEditor(container, options){
		$('<input id="latePaymentAmount" name="latePaymentAmount" readOnly="readOnly" data-bind="value:' + options.field + '"/>')
		.appendTo(container).kendoNumericTextBox({
			spinners : false,
			editable: false,
			min : 0,
			editable: false,
			change : selectPayment   
		});
		
	}
	
	function billAmountchange(e){
		
		 var dataItem = this.dataItem(e.item.index());
		var serviceType = dataItem.value;
		checkPaymentPosted(serviceType);
		var accountId=$("#accountId").val(); 

		$("#consolidatedId").kendoComboBox({
			dataSource : {
				transport : {
					read : {
						url:"./billingPayments/getConsolidatedAmountOnAccount/"+serviceType +"/" +accountId ,
					}
				}
			},
			cascadeFrom : "typeOfService",
		    dataType: 'JSON',
		    dataTextField : "cbId",
		    dataValueField: "billAmount",
			placeholder: "Select Amount",
			change : latepayment,
			
		});
		 $("#consolidatedId").data("kendoComboBox").value("");
	}
	
	function consolidatedEditor(container, options) {
		$('<input name="consolidatedId" data-text-field="cbId" required="true" id="consolidatedId" data-value-field="billAmount" data-bind="value:' + options.field + '"/>')
				.appendTo(container).kendoComboBox({
					placeholder : "Select Amount",
					autoBind : false,
					optionLabel : "Select",
					cascadeFrom : "typeOfService",
					/* dataSource : {
						transport : {
							read : "${consolidatedIdUrl}"
						}
					}, */
					change : latepayment

				});
		$('<span class="k-invalid-msg" data-for="consolidatedId"></span>')
				.appendTo(container);
	}
	
	function latepayment(){
		var accountId=$("#accountId").val();	
		 receiptDate=$('input[name="receiptDate"]').data("kendoDatePicker").value();
		 rdate=kendo.toString(receiptDate,'MM/dd/yyyy');
		var typeOfService=$("#typeOfService1").val();
		
		
		 $.ajax({
				type : "GET",
				url : "./bill/getlatePaymentAmount",
				dataType : "json",
				data : {
					
					accountId  : accountId ,
					rdate:rdate,
					typeOfService : typeOfService,
				},
				success : function(response) {
				var	latepayment=$("input[name=latePaymentAmount]").data("kendoNumericTextBox").value(response);
					
					selectPayment(latepayment);
						
				}

			});
		
	}

	var paymentAmount = "";
	function selectPayment(e) {		
		 latepaymentt=$("#latePaymentAmount").val();
		$('div[data-container-for="paymentAmount"]').show();
		$('label[for="paymentAmount"]').closest('.k-edit-label').show();
		var lateamount=$("#latePaymentAmount").val();		
		var billAmoun=$("#consolidatedId").val();
		var sumAmount = (parseFloat(billAmoun) + parseFloat(lateamount));
		$("input[name=paymentAmount]").data("kendoNumericTextBox").value(sumAmount);
		var paymentAmountMin = $('input[name="paymentAmount"]').kendoNumericTextBox().data("kendoNumericTextBox");
		paymentAmountMin.value(sumAmount);	
		paymentAmount = sumAmount;
	}

	function testEditor(container, options) {
		var c=0;
		$('<input name="paymentAmount" id="paymentAmount"  data-bind="value:' + options.field + '"/>')
				.appendTo(container).kendoNumericTextBox({
					change : function(e) {
						paymentAmountChange(e);
					}
				});
		
		
	//$("#excessAmount").val("");
	}

	
	
	//
	var	 count=0;
	function paymentAmountChange(e) {
	
		
			var inital=0;
			$("input[name=excessAmount]").data("kendoNumericTextBox").value(inital);
			var payAmount = $("#paymentAmount").val();
			
	  		var bilAmoun = $("#consolidatedId").val();
	  		
	  		var lateAmount=$("#latePaymentAmount").val();
	  		var bilAmount=(parseFloat(bilAmoun)+parseFloat(lateAmount));
	  		
	  		
	  		
			var payAmounts=kendo.parseInt(payAmount);
			var billAmounts=kendo.parseInt(bilAmount);
			
			var partPaymentFlag = $("#partPayment").val();

			if (partPaymentFlag == "Yes") {

				if (parseFloat(payAmount)>= parseFloat(lateAmount)) {
				alert("1");
					if( parseFloat(bilAmount) < parseFloat(payAmount)){
						alert("Payment amount should be less than sum of Bill amount and Late payment amount");
			            $("#paymentAmount").data("kendoNumericTextBox").value(lateAmount);
			            paymentAmount = $('#paymentAmount').val(lateAmount);
					}

				} else {
					
					alert("Payment amount should be greater than Late Payment amount");
		            $("#paymentAmount").data("kendoNumericTextBox").value(lateAmount);
		            paymentAmount = $('#paymentAmount').val(lateAmount);
				}

			} else {
				
				if(payAmounts < bilAmount){
					
					alert("Payment amount should be greater than sum amount of Bill and Late Payment amount");
		            $("#paymentAmount").data("kendoNumericTextBox").value(bilAmount);
		            paymentAmount = $('#paymentAmount').val(bilAmount);
		           
				} else{
					 
					paymentAmount = $('#paymentAmount').val();
					var billAmount = $("#consolidatedId").val();
					var latepaymentAmount=$("#latePaymentAmount").val();
					var totalBillAmount=(parseFloat(billAmount)+parseFloat(latepaymentAmount));
					var diffAmount = (parseFloat(paymentAmount) - (parseFloat(billAmount)+parseFloat(latepaymentAmount)));
					if (parseFloat(paymentAmount) >= parseFloat(billAmount)) {
						
						if(diffAmount>=0){
							excessAmount = diffAmount;
							$("input[name=excessAmount]").data("kendoNumericTextBox").value(diffAmount);
							$('input[name="excessAmount"]').attr('readonly', true);
							$('input[name="excessAmount"]').attr('spinners', false);

							$('div[data-container-for="excessAmount"]').show();
							$('label[for="excessAmount"]').closest('.k-edit-label').show();
						}
						
						
						
					} else {
						
						excessAmount = 0;
		                
						$("input[name=paymentAmount]").data("kendoNumericTextBox").value(totalBillAmount);
						var paymentAmountMin = $('input[name="paymentAmount"]').kendoNumericTextBox().data("kendoNumericTextBox");
						paymentAmountMin.value(totalBillAmount);

						$('input[name="excessAmount"]').data("kendoNumericTextBox").value(0);
						$('input[name="excessAmount"]').attr('readonly', true);
						$('input[name="excessAmount"]').attr('spinners', false);

						paymentAmount = $('#paymentAmount').val();
					}

					if (excessAmount > 0 && diffAmount != 0) {
						
						alert("Recieved amount is greater than bill amount by "+ diffAmount + " Rs");
						
						
					}
				}			
				
				

			}
			
			
	
		//
		/* if ($("#paymentMode").val() == "Cheque") {
			alert("Payment through cheque excess amount does not accept");
			$('div[data-container-for="excessAmount"]').hide();
			$('label[for="excessAmount"]').closest('.k-edit-label').hide();

			if ($("#partPayment").val() == "Yes") {
				$("#paymentAmount").data("kendoNumericTextBox").value(amt);
			} else {
				$("#paymentAmount").data("kendoNumericTextBox").value(
						$("#consolidatedId").val());
			}
			$("input[name=excessAmount]").data("kendoNumericTextBox").value(0);
			$('input[name="excessAmount"]').attr('readonly', true);
			$('input[name="excessAmount"]').attr('spinners', false);
			excessAmount = 0;
		} else {
			
		} */

	}
	//function	ravi(diffAmount){
		
		//window.alert("Recieved amount is greater than bill amount by "+ diffAmount + " Rs");
		/* $("#alertsBox").html("");
		$("#alertsBox").html("Recieved amount is greater than bill amount by "+ diffAmount + " Rs");
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
		}); */
		
	//}
	function dropDownChecksEditorPartPayment(container, options) {
		var res = (container.selector).split("=");
		var attribute = res[1].substring(0, res[1].length - 1);

		$(
				'<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '" name = "'+attribute+'" id = "partPayment"/>')
				.appendTo(container).kendoDropDownList({
					/* optionLabel : {
						text : "Select",
						value : "",
					}, */
					defaultValue : false,
					sortable : true,
					select : selectedPartPayment,
					dataSource : {
						transport : {
							read : "${allChecksUrl}/" + attribute,
						}
					}
				});
		$('<span class="k-invalid-msg" data-for="'+attribute+'"></span>')
				.appendTo(container);
	}

	var partPaymentName = "";
	function selectedPartPayment(e) {
		var dataItem = this.dataItem(e.item.index());
		partPaymentName = dataItem.text;
		if (partPaymentName == "Yes") {
			var lateamount = $("#latePaymentAmount").val();
			$("input[name=paymentAmount]").data("kendoNumericTextBox").value(lateamount);
		} else {
			var sumAmount="";
			var lateamount=$("#latePaymentAmount").val();		
			var billAmoun=$("#consolidatedId").val();
			var excessAmount = $("#excessAmount").val();
			
			if(excessAmount == null || excessAmount == 'undefined'  || excessAmount === "" ){
				sumAmount = (parseFloat(billAmoun) + parseFloat(lateamount));
			}else{
				sumAmount = (parseFloat(billAmoun) + parseFloat(lateamount) + parseFloat(excessAmount));
			}
			$("#paymentAmount").data("kendoNumericTextBox").value(sumAmount);
		}
	}
	var amt = 0;
	function changeCheckBoxValue(val) {
		var checked = "";
		var checkedValues = "";
		var str = "";
		checked = $(".partPaymentCheck:checked");
		checkedValues = checked.map(function(i) {
			return $(this).val();
		}).get();
		if (checked.length) {
			str = checkedValues.join();
		}

		var splitter = [];
		splitter = str.split(",");
		var amtTest = 0;
		for (var i = 0; i < splitter.length; i++) {
			amtTest += parseFloat(splitter[i]);
		}
		amt = amtTest;
		//var paymentAmountMin = $('input[name="paymentAmount"]').kendoNumericTextBox().data("kendoNumericTextBox");
		//paymentAmountMin.value(amtTest);
		//paymentAmountMin.min(amtTest);
		
		var lateamount=$("#latePaymentAmount").val();
		
		$("input[name=paymentAmount]").data("kendoNumericTextBox").value(amtTest+parseFloat(lateamount));

		/* 
		paymentAmountTotal = $('input[name="paymentAmount"]').val();
		$(":checkbox:checked").each(function() {
			alert(val);
			var i = parseFloat(val);
			var totalAmountChecked=i;
			amountForExcessChecked = parseFloat(paymentAmountTotal)+parseFloat(totalAmountChecked); 
			$("input[name=paymentAmount]").data("kendoNumericTextBox").value(parseFloat(paymentAmountTotal)+parseFloat(totalAmountChecked));
			});
		$(":checkbox:unchecked").each(function() {
			var i = parseFloat(val);
			var totalAmountChecked=i;
		    
			amountForExcessUnChecked = parseFloat(paymentAmountTotal)-parseFloat(totalAmountChecked);
		    var paymentAmountMin = $('input[name="paymentAmount"]').kendoNumericTextBox().data("kendoNumericTextBox");
		    paymentAmountMin.value(parseFloat(paymentAmountTotal)-parseFloat(totalAmountChecked)); 
		    paymentAmountMin.min(parseFloat(paymentAmountTotal)-parseFloat(totalAmountChecked));
		    $("input[name=paymentAmount]").data("kendoNumericTextBox").value(parseFloat(paymentAmountTotal)-parseFloat(totalAmountChecked));
			}); */
	}

	function changeEditor(container, options) {
		$('<div id="partPayCheckBoxId" style="display:none"></div>').appendTo(container);
	}

	var checkedData, checkedValues = new Array();

	function clearFilterPayments() {
		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
		var gridStoreIssue = $("#grid").data("kendoGrid");
		gridStoreIssue.dataSource.read();
		gridStoreIssue.refresh();
	}

	var transactionCodeArray = [];

	function parseTransactionCode(response) {
		var data = response;
		transactionCodeArray = [];
		for (var idx = 0, len = data.length; idx < len; idx++) {
			var res4 = (data[idx].transactionCode);
			transactionCodeArray.push(res4);
		}
		return response;
	}

	function paymetsDeleteEvent() {
		var conf = confirm("Are u sure want to delete this payment details?");
		if (!conf) {
			$('#grid').data().kendoGrid.dataSource.read();
			throw new Error('deletion aborted');
		}
	}

	function transactionDescriptionEditor(container, options) {
		$(
				'<textarea data-text-field="description" name = "description" required="true" validationmessage="Transaction name description is required" style="width:150px"/>')
				.appendTo(container);
		$('<span class="k-invalid-msg" data-for="Enter Description"></span>')
				.appendTo(container);
	}

	
	var getListBasedOnStatus=0;
	$("#grid").on("click",".k-grid-approveCollection",function(e) {
		
		var r = confirm("Are you sure to approve all the payments?");
		 if(r == true){
			 
			 
					 $.ajax({
							type : "POST",
						    async: false,
						    url:'./billingpayments/getListBasedOnStatus/'+"BillingPaymentsEntity/"+"status/"+"Created",
							dataType : "text",
							success : function(response) {
								
								getListBasedOnStatus=response;	
								
						   }
					 
					   });
					 
					 if(getListBasedOnStatus==0){
						
							$("#alertsBox").html("");
							$("#alertsBox").html("Payment details are already approved"); 
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
							$('#grid').data('kendoGrid').dataSource.read();
					 }
			 
					 else{
						
						var result = securityCheckForActionsForStatus("./Collections/Payments/approveCollectionButton");
						if (result == "success") {
							$.ajax({
								type : "POST",
								url : "./billingPayments/approveCollection",
								dataType : "text",
								success : function(response) {
									//alert("Payment details are posted");
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
									$('#grid').data('kendoGrid').dataSource
											.read();
								}
							});
						}  
					 }
		 }
	});
	
   var getAdjBasedOnStatus=0;
	$("#grid").on("click",".k-grid-postCollection",function(e) {
		
		var r = confirm("Are you sure to post all the payments?");
		 if(r == true){
			 
			 
			 $.ajax({
					type : "POST",
				    async: false,
				    url:'./billingpayments/getListBasedOnStatus/'+"BillingPaymentsEntity/"+"status/"+"Approved",
					dataType : "text",
					success : function(response) {
						
						getAdjBasedOnStatus=response;	
						
				   }
			 
			   });
			 
			 if(getAdjBasedOnStatus==0){
				
					$("#alertsBox").html("");
					$("#alertsBox").html("Payment details are already posted"); 
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
					$('#grid').data('kendoGrid').dataSource.read();
			 }
	 
			 else{

						var result = securityCheckForActionsForStatus("./Collections/Payments/postCollectionButton");
						if (result == "success") {
							$.ajax({
								type : "POST",
								url : "./billingPayments/postCollectionLedger",
								dataType : "text",
								success : function(response) {
									
									 $("#alertsBox").html("");
									 $("#alertsBox").html("Payment details are posted"); 
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
									$('#grid').data('kendoGrid').dataSource
											.read();
								}
							});
						}
			       }
		       }
	});
	
	$("#grid").on("click",".k-grid-postAllPaymentsToTally",function(e) {

/* 		var result = securityCheckForActionsForStatus("./Collections/Payments/postCollectionButton");
		if (result == "success") { */
			var r = confirm("First 50 Payments will be post");
		if(r==true)
			{
			 $('#dvLoadingbody').show();
			$.ajax({
				type : "POST",
				url : "./billingPayments/postCollectionToTally",
				dataType : "text",
				success : function(response) {
					//alert("Payment details are posted");
					alert(response);
					window.location.reload();
					$('#grid').data('kendoGrid').dataSource.read();
				}
			});
		}
	/* 	} */
	});

	$("#grid").on("click","#temPID",function(e) {

						var button = $(this), enable = button.text() == "Approve";
						var widget = $("#grid").data("kendoGrid");
						var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
						var result = securityCheckForActionsForStatus("./Collections/Payments/approvedPostedButton");
						if (result == "success") {
							if (enable) {
								$.ajax({
											type : "POST",
											url : "./billingPayments/setCollectionPaymentStatus/"
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
												$('#grid').data('kendoGrid').dataSource
														.read();
											}
										});
							} else {
								var r = confirm("Are you sure to post this payment?");
								if(r == true){
									$('tr[aria-selected="true"]').find('td:nth-child(26)').html("");
									$('tr[aria-selected="true"]').find('td:nth-child(26)').html("<img src='./resources/images/progress.gif' width='100px' height='25px' />");
										$.ajax({
											type : "POST",
											url : "./billingPayments/setCollectionPaymentStatus/"
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
												$('#grid').data('kendoGrid').dataSource
														.read();
											}
										});
								  }
							}
						}
					});

	var setApCode = "";
	var flagTransactionCode = "";
	
	function paymentEvent(e) {
		$(".k-edit-form-container").css({
			"width" : "700px"
			
		});
		$(".k-window").css({
			"top" : "130px"
		});
		$('.k-edit-label:nth-child(24n+1)').each(
				function(e) {
					$(this).nextAll(':lt(23)').addBack().wrapAll(
							'<div class="wrappers"/>');
				});

		//$('input[name="paymentAmount"]').prop("readonly",true);
		$('#paymentCalcLinesPopUp').hide();

		/***************************  to remove the id from pop up  **********************/
		$('div[data-container-for="paymentCollectionId"]').remove();
		$('label[for="paymentCollectionId"]').closest('.k-edit-label').remove();

		$(".k-edit-field").each(function() {
			$(this).find("#temPID").parent().remove();
		});

		$('div[data-container-for="excessAmount"]').hide();
		$('label[for="excessAmount"]').closest('.k-edit-label').hide();
		
		$('div[data-container-for="tallyStatus"]').hide();
		$('label[for="tallyStatus"]').closest('.k-edit-label').hide();
		
		$('input[name="excessAmount"]').attr('readonly', true);
		$('input[name="excessAmount"]').attr('spinners', false);

		$('div[data-container-for="personName"]').remove();
		$('label[for="personName"]').closest('.k-edit-label').remove();

		$('div[data-container-for="instrumentNo"]').hide();
		$('label[for="instrumentNo"]').closest('.k-edit-label').hide();

		$('div[data-container-for="instrumentDate"]').hide();
		$('label[for="instrumentDate"]').closest('.k-edit-label').hide();

		$('div[data-container-for="bankName"]').hide();
		$('label[for="bankName"]').closest('.k-edit-label').hide();

		/* $('div[data-container-for="billAmount"]').hide();
		$('label[for="billAmount"]').closest('.k-edit-label').hide(); */

		$('div[data-container-for="paymentAmount"]').hide();
		$('label[for="paymentAmount"]').closest('.k-edit-label').hide();

		/* $('div[data-container-for="partPayment"]').hide();
		$('label[for="partPayment"]').closest('.k-edit-label').hide(); */

		/* $('div[data-container-for="receiptDate"]').remove();
		$('label[for="receiptDate"]').closest('.k-edit-label').remove(); */

		$('div[data-container-for="receiptNo"]').remove();
		$('label[for="receiptNo"]').closest('.k-edit-label').remove();

		$('div[data-container-for="accountNo"]').remove();
		$('label[for="accountNo"]').closest('.k-edit-label').remove();

		$('div[data-container-for="paymentDate"]').remove();
		$('label[for="paymentDate"]').closest('.k-edit-label').remove();

		$('div[data-container-for="postedToGl"]').remove();
		$('label[for="postedToGl"]').closest('.k-edit-label').remove();

		$('div[data-container-for="postedGlDate"]').remove();
		$('label[for="postedGlDate"]').closest('.k-edit-label').remove();

		$('div[data-container-for="status"]').remove();
		$('label[for="status"]').closest('.k-edit-label').remove();

		$('div[data-container-for="createdBy"]').remove();
		$('label[for="createdBy"]').closest('.k-edit-label').remove();
		
		$('div[data-container-for="property_No"]').remove();
		$('label[for="property_No"]').closest('.k-edit-label').remove();

		/************************* Button Alerts *************************/
		if (e.model.isNew()) {
			securityCheckForActions("./Collections/Payments/createButton");
			flagTransactionCode = true;
			setApCode = $('input[name="transactionId"]').val();
			$(".k-window-title").text("Add New Payment Details");
			$(".k-grid-update").text("Save");
			excessAmount=0;
			
		} else {
			//securityCheckForActions("./CustomerCare/HelpTopic/editButton");
			flagTransactionCode = false;
			$(".k-window-title").text("Edit Payment Details");
		}

		
		$(".k-grid-update").click(function() {
					/* partPaymentName = $('#partPayment').val();
					if (partPaymentName === "Yes") {
						checkedData = $(":checkbox:checked");
						checkedValues = checkedData.map(function(i) {
							return $(this).attr('id');
						}).get();
						if (checkedData.length) {
							var str = checkedValues.join();
							e.model.set("paymentComponets", str);
						} else {
							e.model.set("paymentComponets", "");
						}

						if (checkedData.length == 0) {
							alert("Please check at least one");
							return false;
						}
					} */  
					
					paymentAmount = $("#paymentAmount").val();
					if(paymentAmount<=0){
						alert("Please enter valid amount");
						return false;
					}
					var partValue= $("#partPayment").val();
					var billPayment=$("#consolidatedId").val();
					var payment=$("#paymentAmount").val();
					var latePayment=$("#latePaymentAmount").val();
					var total=parseFloat(billPayment)+parseFloat(latePayment);
					if(partValue=='No')
						{
						if(payment<total)
							{
							alert("Payment amount should be greater than sum amount of Bill and Late Payment amount ");
							return false;
							}
						}
					if(payment==total)
						{
						var setInitial=0;
						excessAmount=setInitial;
						}
					
					e.model.set("paymentAmount", paymentAmount);
					e.model.set("excessAmount", excessAmount);		 			
					e.model.set("latePaymentAmount", latepaymentt);
					e.model.set("rdate", rdate);
				});

	}

	function onRequestStart(e){
		
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();	
        
			/* var gridStoreGoodsReturn = $("#grid").data("kendoGrid");
			gridStoreGoodsReturn.cancelRow(); */		
		
	}
	
	function paymentSegmentsOnRequestStart(e){
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();	
		
	}
	function onRequestEnd(e) {
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
							"Error: Creating the payment details \n\n : "
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
				var grid = $("#grid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			} else if (e.type == "create") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Payment details is created successfully");
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
			} else if (e.type == "destroy") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Payment details is deleted successfully");
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
			} else if (e.type == "update") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Payment details is updated successfully");
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

	function paymentSegmentsOnRequestEnd(e) {
		if (typeof e.response != 'undefined') {
			//alert("Response is Undefined");

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

				$('#paymentSegments_' + SelectedRowId).data().kendoGrid.dataSource
						.read({
							personId : SelectedAccessCardId
						});
				/* var grid = $("#propertyGrid_"+SelectedRowId).data("kendoGrid");
				grid.dataSource.read();
				grid.refresh(); */
				return false;
			}

			else if (e.type == "create") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Payment segment created successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				var gridPets = $("#paymentSegments_" + SelectedRowId).data(
						"kendoGrid");
				gridPets.dataSource.read();
				gridPets.refresh();
				return false;
			}

			else if (e.type == "update") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Payment segment updated successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				var gridPets = $("#paymentSegments_" + SelectedRowId).data(
						"kendoGrid");
				gridPets.dataSource.read();
				gridPets.refresh();
				return false;
			}

			else if (e.response.status == "AciveInstructionDestroyError") {
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Active instruction details cannot be deleted");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				var gridPets = $("#paymentSegments_" + SelectedRowId).data(
						"kendoGrid");
				gridPets.dataSource.read();
				gridPets.refresh();
				return false;
			}

			else if (e.type == "destroy") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Payment segment deleted successfully");
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

	//register custom validation rules

	var paymentModeFlag = true;
	var rtgsFlag=true;
	(function($, kendo) {
		$
				.extend(
						true,
						kendo.ui.validator,
						{
							rules : { // custom rules
								paymentModeRequired : function(input, params) {
									if (input.attr("name") == "paymentMode") {
										if (input.val() == "Cash") {
											paymentModeFlag = false;
										}else{
											paymentModeFlag = true;
										}
										if (input.val() == "RTGS/NEFT") {
											
											rtgsFlag = false;
										}else{
											rtgsFlag = true;
										}
										
										
										return $.trim(input.val()) !== "";
									}
									return true;
								},

								instrumentDateRequired : function(input, params) {
									if (paymentModeFlag) {
										if (input.attr("name") == "instrumentDate") {
											return $.trim(input.val()) !== "";
										}
									}
									return true;
								},
								instrumentNoRequired : function(input, params) {
									if (paymentModeFlag) {
										if (input.attr("name") == "instrumentNo") {
											return $.trim(input.val()) !== "";
										}
									}
									return true;
								},
								bankNameRequired : function(input, params) {
									if (paymentModeFlag && rtgsFlag) {
										if (input.attr("name") == "bankName") {
											return $.trim(input.val()) !== "";
										}
									}
									return true;
								},
								typeofServiceRequired : function(input, params) {
									
										if (input.attr("name") == "typeOfService") {
											return $.trim(input.val()) !== "";
										}
									
									return true;
								},
								
								instrumentDateValidation : function(input,
										params) {
									if (input.filter("[name = 'instrumentDate']").length && input.val()) {
										var selectedDate = input.val();
										var todaysDate = $.datepicker.formatDate('dd/mm/yy',new Date());
										var flagDate = false;

										if ($.datepicker.parseDate('dd/mm/yy',selectedDate) <= $.datepicker.parseDate('dd/mm/yy',todaysDate)) {
											flagDate = true;
										}
										return flagDate;
									}
									return true;
								},
								instrumentNoLengthValidation : function(input, params) {
									if (paymentModeFlag) {
										if (input.filter("[name='instrumentNo']").length && input.val() != "") 
							              {
							             	 return /^[0-9]{6,15}$/.test(input.val());
							              }        
									}
									return true;
								},
							},
							messages : {
								//custom rules messages
								paymentModeRequired : "Payment mode is required",
								instrumentDateRequired : "Instrument date is required",
								instrumentNoRequired : "Instrument number is required",
								bankNameRequired : "Bank name is required",
								instrumentDateValidation : "Instrument date must be past date",
								instrumentNoLengthValidation : "Instrument number allows only numbers min 6 and max 15",
								typeofServiceRequired:"Service Type is Required"
							}
						});

	})(jQuery, kendo);
	//End Of Validation
	
//=====================================XML Generation Starts=====================================
	$("#fromMonth").kendoDatePicker
	({
		start : "year",// defines the start view
		depth : "year",// defines when the calendar should return date
		value : new Date(),
		format : "MMM yyyy"	// display month and year in the input
	});
   $("#monthPicker").kendoDatePicker
   ({
		start : "year",// defines the start view
		depth : "year",// defines when the calendar should return date
		value : new Date(),
		// display month and year in the input
		format : "MMM yyyy"
	});

	$("#grid").on("click",".k-grid-generateXMLForTally",function(e){
	 
	 var bbDialog = $("#generateXMLDiv");
		bbDialog.kendoWindow({
			width : "auto",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 80
			},
			title : "Export To XML"
		}).data("kendoWindow").center().open();

		bbDialog.kendoWindow("open");
		$("#fromMonth").val("");
 });
 
 $("#exportXml").click(function(){
	 var fromDate=$("#fromMonth").val(); 	alert(fromDate);
	// window.open("./CashManagementCAMPaymentsHistory/exportToXml?fromDate="+fromDate);
	 window.open("billingPayments/GenerateXmlTallyPosting?month="+fromDate);
	 filterclose1(); 
 });
 function filterclose1() {

		var todcal = $("#generateXMLDiv");

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
//=====================================XML Generation ends=====================================
	
$(document).ready(function() {
			var todayDate = new Date();
			var picker = $("#monthDatePicker").kendoDatePicker({
						 format: "MM/yyyy"
					}).data("kendoDatePicker"),
		    dateView = picker.dateView;
			dateView.calendar.element.removeData("dateView");        
			picker.max(todayDate);
	      	picker.options.depth = dateView.options.depth = 'year';
	      	picker.options.start = dateView.options.start = 'year';
	       	picker.value(picker.value());
	       
	    $('#monthDatePicker').keyup(function() {
		$('#monthDatePicker').val("");
		});
});

function exportPaymentsTOexcel()
{	
	var month1 = $("#monthDatePicker").val();	
	//alert(month1);	
	if(month1 == '' ){
		alert("Select month to export payments");
	}else{
		window.open("./exportPaymentsToExcel?date="+month1);
	}
}

</script>

<style type="text/css">
.wrappers {
	display: inline;
	float: left;
	width: 350px;
	padding-top: 10px;

	/* float:left;  */
	/* border: 1px solid red;
 */
	/* border: 1px solid green;
    overflow: hidden; */
}
.tftable {font-size:12px;color:#333333;width:100%;border-width: 1px;border-color: #729ea5;border-collapse: collapse;}
.tftable th {font-size:12px;background-color:#acc8cc;border-width: 1px;padding: 8px;border-style: solid;border-color: #729ea5;text-align:left;}
.tftable tr {background-color:#d4e3e5;}
.tftable td {font-size:12px;border-width: 1px;padding: 8px;border-style: solid;border-color: #729ea5;}
.tftable tr:hover {background-color:#ffffff;}

.loadingimg {
	    background: #FFF url(./resources/images/712.GIF) no-repeat center center;
        position: absolute;
        top: 0%;
        left: 0%;
        width: 100%;
        height: 100%;
        z-index:1001;
        -moz-opacity: 0.8;
        opacity:.80;
        filter: alpha(opacity=80);
       }
</style>
