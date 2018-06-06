<%@include file="/common/taglibs.jsp"%>

<c:url value="/paymentAdjustments/paymentAdjustmentCreate" var="createUrl" />
<c:url value="/paymentAdjustments/paymentAdjustmentRead" var="readUrl" />
<c:url value="/paymentAdjustments/paymentAdjustmentDestroy" var="destroyUrl" />
<c:url value="/paymentAdjustments/paymentAdjustmentUpdate" var="updateUrl" />
<c:url value="/paymentAdjustments/filter" var="commonFilterForAdjustmentsUrl" />

<c:url value="/paymentAdjustments/adjustmentCalcLineRead" var="adjustmentCalcLineReadUrl" />
<c:url value="/paymentAdjustments/adjustmentCalcLineCreate" var="adjustmentCalcLineCreateUrl" />
<c:url value="/paymentAdjustments/adjustmentCalcLineUpdate" var="adjustmentCalcLineUpdateUrl" />
<c:url value="/paymentAdjustments/adjustmentCalcLineDestroy" var="adjustmentCalcLineDestroy" />
<c:url value="/paymentAdjustments/adjustmentCalcLine/filter" var="commonFilterForPaymentSegmentsUrl" />
<c:url value="/paymentAdjustments/getAllPaidAccountNumbers" var="accountNumberAutocomplete" />
<c:url value="/adjustmentMaster/getAllAdjustmentName" var="adjustmentTypeDropDown" />


<c:url value="/paymentAdjustments/getTransactionCodes" var="transactionCodeUrl" />
<c:url value="/paymentAdjustments/commonFilterForAccountNumbersUrl" var="commonFilterForAccountNumbersUrl"/>
<c:url value="/common/getAllChecks" var="allChecksUrl" />
<c:url value="/billingPayments/getPersonListForFileter" var="personNamesFilterUrl" />

<c:url value="/customerCareNotification/getBlockNames" var="getBlockNamesUrl" />
<c:url value="/customerCareNotification/getProperties" var="getPropertyNamesUrl" />


<div id="paymentTallyPostingDiv" style="display: none;">
	<form id="paymentTallyPostingDivForm" style="line-height:  75px;">
		<table>
			<tr>
			    <td></td>
				<td>Month</td>
				<td><kendo:datePicker format="MMM yyyy  "
						name="presentdatepostBillXML" id="presentdatepostBillXML"
						required="required" value="${month}" start="year"
						class="validate[required]" validationMessage="Date is Required"
						depth="year">
					</kendo:datePicker>
				<td></td>
				<td></td>
			</tr>
			<tr>
			<td></td>
			<td class="left" align="center" colspan="4">
					<button type="submit" id="paymentTallyPostingXml" class="k-button"
						style="padding-left: 10px">Export To XML</button> <span
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
<kendo:grid name="grid" remove="adjustmentsDeleteEvent" change="onChangeAdjustments" pageable="true" resizable="true" edit="paymentAdjustmentEvent" dataBound="paymentAdjustmentDataBound" sortable="true" reorderable="true" selectable="true" scrollable="true" filterable="false" groupable="true">

    <kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" refresh="true" info="true" previousNext="true">
		<kendo:grid-pageable-messages itemsPerPage="Adjustments per page" empty="No adjustment to display" refresh="Refresh all the adjustments" 
			display="{0} - {1} of {2} New adjustments" first="Go to the first page of adjustments" last="Go to the last page of adjustments" next="Go to the next page of adjustments"
			previous="Go to the previous page of adjustments"/>
	</kendo:grid-pageable>
	<kendo:grid-filterable extra="false">
		<kendo:grid-filterable-operators>
			<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains" />
				<kendo:grid-filterable-operators-date gt="Is after" lt="Is before"/>
				<kendo:grid-filterable-operators-number eq="Is equal to" gt="Is greather than" gte="IS greather than and equal to" lt="Is less than" lte="Is less than and equal to" neq="Is not equal to"/>
		</kendo:grid-filterable-operators> 
	</kendo:grid-filterable>
	<kendo:grid-editable mode="popup"/>
		<kendo:grid-toolbar>
		      <kendo:grid-toolbarItem name="create" text="New Adjustment Details" />
		      <kendo:grid-toolbarItem template="<label class='category-label'>&nbsp;&nbsp;From&nbsp;Date&nbsp;:&nbsp;&nbsp;</label><input id='fromMonthpicker' style='width:110px'/>"/>
			  <kendo:grid-toolbarItem template="<label class='category-label'>&nbsp;&nbsp;To&nbsp;Date&nbsp;:&nbsp;&nbsp;</label><input id='toMonthpicker' style='width:110px'/>"/>
			  <kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=searchByMonth() title='Search' style='width:90px'>Search</a>"/>
		      <kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterPayments()>Clear Filter</a>"/>
		      <kendo:grid-toolbarItem name="approveAdjustment" text="Approve Adjustment"/>
		      <kendo:grid-toolbarItem name="postAdjustment" text="Post Adjustment"/>
		       <kendo:grid-toolbarItem name="postAllAdjustmentToTally" text="Post All To Tally"/>
		        <kendo:grid-toolbarItem name="adjustmentToAll" text="Adjustment To All"/>
		        <%-- <kendo:grid-toolbarItem name="paymentAdjustmentTemplatesDetailsExport" text="Export To Excel" /> --%>
		         <kendo:grid-toolbarItem name="generateXmlTallyPosting" text="Generate Xml Tally Posting"/> 
                <kendo:grid-toolbarItem template="<input id='monthDatePicker' type=date/>"/>
		       <kendo:grid-toolbarItem template="<button class='k-button' onclick='exportAdjustmentsTOexcel()'>Export To Excel</button>"/>
	    </kendo:grid-toolbar> 
	<kendo:grid-columns>
	    
	    <kendo:grid-column title="adjustmentId" field="adjustmentId" width="10px" hidden="true" filterable="false" sortable="false" />
	    
	    
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
	    
	    <kendo:grid-column title="Account&nbsp;Number&nbsp;*" field="accountId" hidden="true" width="140px" filterable="true" editor="accountNumberEditor">
	    </kendo:grid-column>
	    
	    <kendo:grid-column title="Person&nbsp;Name" field="personName"  width="110px" filterable="false">
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
	    <kendo:grid-column title="Property&nbsp;No&nbsp;*" field="property_No" filterable="true"  width="90px" >
		</kendo:grid-column>	
	    
	    <kendo:grid-column title="JV&nbsp;Date" field="jvDate" format="{0:dd/MM/yyyy hh:mm tt}" hidden="true" width="135px" filterable="true">
	    </kendo:grid-column>
	    
	    <kendo:grid-column title="Adjustment&nbsp;Date&nbsp;*" field="adjustmentDate" format="{0:dd/MM/yyyy}" width="125px" filterable="true">
	    </kendo:grid-column>
	    
	    <kendo:grid-column title="Adjustment&nbsp;No&nbsp;*" field="adjustmentNo" width="110px" filterable="true">
	    <kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function ledgerStatusFilter(element) {
								element.kendoAutoComplete({
											placeholder : "Enter Adjustment Number",
											dataSource : {
												transport : {
													read : "${commonFilterForAdjustmentsUrl}/adjustmentNo"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	    </kendo:grid-column>
	    
	    <kendo:grid-column title="Ledger&nbsp;Type&nbsp;*" field="adjustmentLedger" width="115px" filterable="true" editor="dropDownChecksEditor">
	    <kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function ledgerStatusFilter(element) {
								element.kendoAutoComplete({
											placeholder : "Enter Ledger Type",
											dataSource : {
												transport : {
													read : "${commonFilterForAdjustmentsUrl}/adjustmentLedger"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	    </kendo:grid-column>
	    
	    <kendo:grid-column title="Adjustment&nbsp;Type&nbsp;*" field="adjustmentType" width="115px" filterable="true" editor="dropDownChecksEditor2">
	    <kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function ledgerStatusFilter(element) {
								element.kendoAutoComplete({
											placeholder : "Enter Adjustment Type",
											dataSource : {
												transport : {
													read : "${commonFilterForAdjustmentsUrl}/adjustmentType"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	    </kendo:grid-column>
	    
	    <kendo:grid-column title="Adjustment&nbsp;Amount&nbsp;*" field="adjustmentAmount" width="125px" filterable="false">
	    </kendo:grid-column>
	    
	    <kendo:grid-column title="Remarks" field="remarks" filterable="true" width="120px">
		</kendo:grid-column>
		
	    <kendo:grid-column title="Posted&nbsp;To&nbsp;GL" field="postedToGl" width="100px" filterable="true">
	    <kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function ledgerStatusFilter(element) {
								element.kendoAutoComplete({
											placeholder : "Enter Posted GL",
											dataSource : {
												transport : {
													read : "${commonFilterForAdjustmentsUrl}/postedToGl"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	    </kendo:grid-column>
	    
	    <kendo:grid-column title="Posted&nbsp;GL&nbsp;Date" field="postedGlDate" format="{0:dd/MM/yyyy hh:mm tt}" width="120px" filterable="true">
	    </kendo:grid-column>
	    
	    <kendo:grid-column title="Status" field="status" width="60px" filterable="true">
	    <kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function ledgerStatusFilter(element) {
								element.kendoAutoComplete({
											placeholder : "Enter Status",
											dataSource : {
												transport : {
													read : "${commonFilterForAdjustmentsUrl}/status"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	    </kendo:grid-column>
	        <kendo:grid-column title="Tally&nbsp;Status" field="tallystatus" width="90px" filterable="true">
	    <kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function ledgerStatusFilter(element) {
								element.kendoAutoComplete({
											placeholder : "Enter Status",
											dataSource : {
												transport : {
													read : "${commonFilterForAdjustmentsUrl}/tallystatus"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	    </kendo:grid-column>

		<kendo:grid-column title="&nbsp;" width="160px">
			<kendo:grid-column-command>
			    <kendo:grid-column-commandItem name="edit"/>
				<kendo:grid-column-commandItem name="destroy" />
			</kendo:grid-column-command>
		</kendo:grid-column>
		
		<%-- <kendo:grid-column title="&nbsp;" width="110px">
				<kendo:grid-column-command >
					 <kendo:grid-column-commandItem name="Paid" click="paymentStatusUpdate" />
				</kendo:grid-column-command>
		    </kendo:grid-column> --%>	
		
		<kendo:grid-column title=""
				template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Active'>#= data.status == 'Created' ? 'Approve' : data.status == 'Approved' ? 'Post' : 'Posted' #</a>"
				width="80px" />
				
				<kendo:grid-column title="&nbsp;" width="110px">
				<kendo:grid-column-command >
					 <kendo:grid-column-commandItem name="Post To Tally" click="postAdjustmentToTally" />
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
			<kendo:dataSource-schema-model id="adjustmentId">
				<kendo:dataSource-schema-model-fields>
					<kendo:dataSource-schema-model-field name="adjustmentId" type="number"/>
					
					<kendo:dataSource-schema-model-field name="accountId" type="number" defaultValue="">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="jvDate" type="date">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="adjustmentDate" type="date">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="personName" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="property_No" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="adjustmentNo" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="accountNo" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="adjustmentLedger" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="adjustmentAmount" type="number" defaultValue="">
					       <kendo:dataSource-schema-model-field-validation />
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="postedToGl" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="postedGlDate" type="date">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="status" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="adjustmentType" type="string">
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="tallystatus" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="remarks" type="string">
					</kendo:dataSource-schema-model-field>
					
				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>

	</kendo:dataSource>

</kendo:grid>

<kendo:grid-detailTemplate id="paymentAdjustmentTemplate">
		<kendo:tabStrip name="tabStrip_#=adjustmentId#">
		<kendo:tabStrip-animation>
			</kendo:tabStrip-animation>
			<kendo:tabStrip-items>
			
 			<kendo:tabStrip-item selected="true" text="Adjustment Calc Lines">
                <kendo:tabStrip-item-content>
                    <div class='wethear' style='width: 75%'>
						    <br/>
							<kendo:grid name="calcLines_#=adjustmentId#" pageable="true"
								resizable="true" remove="adjustmentCalcLineDeleteEvent" sortable="true" reorderable="true"
								selectable="true" scrollable="true" edit="calcLinesEvent" editable="true" >
								<kendo:grid-pageable pageSize="10"></kendo:grid-pageable>
								<kendo:grid-filterable extra="false">
			                    <kendo:grid-filterable-operators>
				                    <kendo:grid-filterable-operators-string eq="Is equal to" />
			                    </kendo:grid-filterable-operators>
		                        </kendo:grid-filterable>
								<kendo:grid-editable mode="popup"/>
						       <kendo:grid-toolbar >
						            <kendo:grid-toolbarItem name="create" text="Add Adjustment Line Item" />
						        </kendo:grid-toolbar> 
        						<kendo:grid-columns>
        						    <kendo:grid-column title="calcLineId" field="calcLineId" hidden="true" width="100px">
									</kendo:grid-column> 
									
									<kendo:grid-column title="adjustmentId" field="adjustmentId" hidden="true" width="100px">
									</kendo:grid-column>
									
									<%-- <kendo:grid-column title="Service&nbsp;Type&nbsp;*"  field="typeOfService" hidden="true" filterable="true" width="100px" editor="dropDownChecksEditorForServiceType">
									</kendo:grid-column> --%>
									
									<kendo:grid-column title="Transaction&nbsp;Name&nbsp;*" field="transactionName" filterable="false" width="150px">
						    		</kendo:grid-column>
			
									<kendo:grid-column title="Transaction&nbsp;Name&nbsp;*" field="transactionCode" filterable="false" hidden="true" width="130px" editor="transactionCodeEditor">
									</kendo:grid-column>
									
									<kendo:grid-column title="Amount&nbsp;*" field="amount" format="{0:n2}" width="100px" filterable="false">
									</kendo:grid-column> 
									
        							<kendo:grid-column title="&nbsp;" width="110px" >
							            <kendo:grid-column-command>
							            	<kendo:grid-column-commandItem name="edit"/>
							            	<kendo:grid-column-commandItem name="destroy"/>
							            </kendo:grid-column-command>
							        </kendo:grid-column>
							   						            
        						</kendo:grid-columns>
        						
        						  <kendo:dataSource requestEnd="calcLineOnRequestEnd" requestStart="calcLineOnRequestStart">
						            <kendo:dataSource-transport>
						            <kendo:dataSource-transport-read url="${adjustmentCalcLineReadUrl}/#=adjustmentId#" dataType="json" type="POST" contentType="application/json"/>
						            <kendo:dataSource-transport-create url="${adjustmentCalcLineCreateUrl}/#=adjustmentId#" dataType="json" type="GET" contentType="application/json" />
						            <kendo:dataSource-transport-update url="${adjustmentCalcLineUpdateUrl}/#=adjustmentId#" dataType="json" type="GET" contentType="application/json" />
						            <kendo:dataSource-transport-destroy url="${adjustmentCalcLineDestroy}" dataType="json" type="GET" contentType="application/json" />
						            </kendo:dataSource-transport>
						            
						            <kendo:dataSource-schema>
						                <kendo:dataSource-schema-model id="calcLineId">
						                    <kendo:dataSource-schema-model-fields>
						                    
						                    <kendo:dataSource-schema-model-field name="calcLineId" type="number">
											<kendo:dataSource-schema-model-field-validation  />
											</kendo:dataSource-schema-model-field>
											
											 <kendo:dataSource-schema-model-field name="adjustmentId" type="number">
											<kendo:dataSource-schema-model-field-validation  />
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="typeOfService" type="String">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="transactionCode" type="String">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="transactionName" type="string">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="amount" type="number" defaultValue="">
											   <kendo:dataSource-schema-model-field-validation />
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
	
<!-- <div id="paymentCalcLinesPopUp"></div>	 -->

<div id="alertsBox" title="Alert"></div>
<div id="adjustmentToAlldiv" style="display: none;">
	<form id="adjustmentToAllForm" data-role="validator" novalidate="novalidate">
		<table style="height: 190px;">
		<tr>
		<td></td>
		<td><input type="radio" name="radio1" onclick="blockBy()" value="Block"/>&nbsp;&nbsp;&nbsp;Block&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="radio" name="radio1" value="Property" onclick="propertyBy()"/>&nbsp;&nbsp;&nbsp;Property</td>
		
		</tr>
		<tr></tr>
		<tr class="allBlock" hidden="true">
			<td></td>
			<td><input type="radio" name="radio2" onclick="allblock()" value="All"/>&nbsp;&nbsp;&nbsp;All&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="radio" name="radio2" value="Select Block" onclick="selectBlock()"/>&nbsp;&nbsp;&nbsp;Select Block</td>
		</tr>
		<tr class="propertyNumber" hidden="true">
			<td>Property Numbers</td>
			<td><input  id="adjustmentPropertyNumber" name="adjustmentPropertyNumber" /></td>
		</tr>
		<tr class="blockNames" hidden="true">
			<td>Block Names</td>
			<td><input  id="adjustmentBlockName" name="adjustmentBlockName" /></td>
		</tr>
		
			<tr>
				<td>Ledger Type</td>
				<td><input  id="serviceTypeApprove" name="serviceTypeApprove"
					
					validationMessage="Select Service Type"  />
				</td>
			</tr>
			<tr>
				<td>Adjustment Type</td>
				<td><input  id="adjustmentTypeApprove" name="adjustmentTypeApprove" />
				</td>
			</tr>
			<tr>
				<td>Adjustment Date</td>
				<td><kendo:datePicker format="yyyy/MM/dd "
						name="adjustmentDate" id="adjustmentDate" required="required"
						class="validate[required]" validationMessage="Date is Required">
					</kendo:datePicker>
				<td></td>
			</tr>
			<tr>
				<td>Adjustment Amount</td>
				<td><kendo:numericTextBox name="adjustmentAmount"
									id="adjustmentAmount" style="aria-readonly=true;"
									></kendo:numericTextBox></td>
			</tr>
			<tr>
			
			</tr>
			<tr>
				<td class="left" align="center" colspan="4">                    
					<button type="submit" id="adjustmentAll" class="k-button"
						style="padding-left: 10px">Adjustment To All</button>
						<span id=commitplaceholderNewApprove style="display: none;">
					<img
						src="./resources/images/loading2.gif"
						style="vertical-align: middle; margin-left: 50px" /> 
					</span>
				</td>
			</tr>

		</table>
	</form>
</div>
<script>

function chequeBounceRemarksEditor(container, options){
	$('<textarea data-text-field="remarks" name="remarks" style="width:150px;height:60px"/>').appendTo(container);
	$('<span class="k-invalid-msg" data-for="Enter Remarks"></span>').appendTo(container);
}

var approvebillvalidator = $("#adjustmentToAllForm").kendoValidator().data("kendoValidator");
$("#adjustmentAll").on("click", function() {

	if (approvebillvalidator.validate()) {

		approveAllBill();
	}
});
function approveAllBill()
{
	var ledgerType = $("#serviceTypeApprove").val();
	var adjustmentType = $("#adjustmentTypeApprove").val();
	var propertyId = $("input[name=adjustmentPropertyNumber]").data("kendoMultiSelect").value();
	var blockId =$("input[name=adjustmentBlockName]").data("kendoMultiSelect").value();
	var adjustmentAmount=$("#adjustmentAmount").val();
	var presnetDate=$("#adjustmentDate").val();
	var radio1= $('input[name=radio1]:checked').val();
	var radio2= $('input[name=radio2]:checked').val();
	
	
	$.ajax({		
		url : "./paymentAdjustment/adjustmentToAll",
		type : "POST",
		dataType : "text",
		data :
			{
			ledgerType : ledgerType,
			adjustmentType : adjustmentType,
			propertyId : "" + propertyId + "",
			blockId : "" +  blockId + "",
			adjustmentAmount : adjustmentAmount,
			presnetDate : presnetDate,
			radio1 : radio1,
			radio2 : radio2,
			
			},
		success : function(response) {
			
			alert(response);
			window.location.reload();
		}
		
	});
	
}
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
   	
   	
   	var serviceListDownload = [ {
		text : "Electricity Ledger",
		value : "Electricity Ledger"
	}, {
		text : "Water Ledger",
		value : "Water Ledger"
	}, {
		text : "Gas Ledger",
		value : "Gas Ledger"
	}, {
		text : "CAM Ledger",
		value : "CAM Ledger"
	}, {
		text : "Telephone Broadband Ledger",
		value : "Telephone Broadband Ledger"
	}, {
		text : "Solid Waste Ledger",
		value : "Solid Waste Ledger"
	}, {
		text : "Others Ledger",
		value : "Others Ledger"
	},

	];
   	
   	$("#serviceTypeApprove").kendoDropDownList({
		dataTextField : "text",
		dataValueField : "value",
		optionLabel : {
			text : "Select",
			value : "",
		},
		dataSource : serviceListDownload
	}).data("kendoDropDownList");
   	
   	$("#adjustmentTypeApprove").kendoDropDownList({
		
		optionLabel : "select",
		dataTextField : "adjName",
		dataValueField : "adjName",

		dataSource : {

			transport : {
				read : "./adjustmentMaster/getAllAdjustmentName" 

			}
		}
	}).data("kendoDropDownList");
  	$("#adjustmentBlockName").kendoMultiSelect({
		/*  optionLabel : "Select",  */
		autoBind : false,
		dataValueField : "blockId",
		dataTextField : "blockName",
		  placeholder:"Select", 
		  dataSource: {  
	             transport:{
	                 read: "${getBlockNamesUrl}"
	             }
	         },	});
 	$("#adjustmentPropertyNumber").kendoMultiSelect({
		/*  optionLabel : "Select",  */
		autoBind : false,
		dataValueField : "propertyId",
		dataTextField : "propertyNo",
		  placeholder:"Select", 
		  dataSource: {  
	             transport:{
	                 read: "${getPropertyNamesUrl}"
	             }
	         },	});
  	
 	$("#adjustmentToAllForm").submit(function(e) {
 		e.preventDefault();
 	});
   	
   	
});


function blockBy()
{
	
	$('.allBlock').show();
	$('.propertyNumber').hide();
	$("[name=radio2]").removeAttr("checked");
	
	var multiSelect = $('#adjustmentBlockName').data("kendoMultiSelect");
	multiSelect.value([]);
}

function propertyBy()
{
	$('.allBlock').hide();
	$('.propertyNumber').show();
	$('.blockNames').hide();
	var multiSelect = $('#adjustmentPropertyNumber').data("kendoMultiSelect");
	multiSelect.value([]);
}
	
function selectBlock()
{
	$('.blockNames').show();
	var multiSelect = $('#adjustmentBlockName').data("kendoMultiSelect");
	multiSelect.value([]);
}
function allblock()
{
	$('.blockNames').hide();
}
$("#adjustmentToAllForm").submit(function(e) {
	e.preventDefault();
});
var approvebillvalidator = $("#adjustmentToAllForm").kendoValidator().data("kendoValidator");
$("#approveBill").on("click", function() {

	if (approvebillvalidator.validate()) {

		approveAllBill();
	}
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
	   //alert(fromDate);
	   //alert(toDate);
	  $.ajax({
		type : "GET",
		url : "./paymentAdjustments/searchAdjustmentDataByMonth",
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

function paymentAdjustmentDataBound(e){
	var data = this.dataSource.view(),row;
	var grid = $("#grid").data("kendoGrid");
    for (var i = 0; i < data.length; i++) {
    	var currentUid = data[i].uid;
        row = this.tbody.children("tr[data-uid='" + data[i].uid + "']");
        
        var adjustmentStatus = data[i].status;
        var tallystatus = data[i].tallystatus;
        if (adjustmentStatus == 'Posted') {
        	var currenRow = grid.table.find("tr[data-uid='" + currentUid+ "']");
			
			var postButton = $(currenRow).find(".k-grid-Active");
			postButton.hide();        	
			
			var editButton = $(currenRow).find(".k-grid-edit");
			editButton.hide();
			
			var deleteButton = $(currenRow).find(".k-grid-delete");
			deleteButton.hide();
		} 
           if (tallystatus=='Posted') {
        	
        	var currenRow = grid.table.find("tr[data-uid='" + currentUid+ "']");
			var postTotally = $(currenRow).find(".k-grid-PostToTally");
			postTotally.hide();
        }  
    }
    
    
     
}

function adjustmentsDeleteEvent(){
	securityCheckForActions("./Collections/Adjustments/destroyButton");
	var conf = confirm("Are u sure want to delete this adjustment details?");
	 if(!conf){
	  $("#grid").data().kendoGrid.dataSource.read();
	   throw new Error('deletion aborted');
	 }
}

//for parsing timestamp data fields
function parse (response) {
    $.each(response, function (idx, elem) {   
    	   if(elem.jvDate=== null){
    		   elem.jvDate = "";
    	   }else{
    		   elem.jvDate = kendo.parseDate(new Date(elem.jvDate),'dd/MM/yyyy HH:mm');
    	   }
    	   
    	   if(elem.postedGlDate=== null){
               elem.postedGlDate = "";
            }else{
               elem.postedGlDate = kendo.parseDate(new Date(elem.postedGlDate),'dd/MM/yyyy HH:mm');
            }
            
            if(elem.adjustmentDate=== null){
                elem.adjustmentDate = "";
             }else{
                elem.adjustmentDate = kendo.parseDate(new Date(elem.adjustmentDate),'dd/MM/yyyy');
             }
       });
               
       return response;
}

var SelectedRowId = "";
var selectedServiceType="";
var adjustmentStatus = "";
var res="";
function onChangeAdjustments(arg) {
	 var gview = $("#grid").data("kendoGrid");
	 var selectedItem = gview.dataItem(gview.select());
	 SelectedRowId = selectedItem.adjustmentId;
	 adjustmentStatus = selectedItem.status;
	 selectedServiceType = selectedItem.adjustmentLedger;
	 if(!selectedServiceType=="Tele Broadband Ledger"||!selectedServiceType=="Solid Waste Ledger"){
		res  = selectedServiceType.split(" ",2);
	 }else{
		res = selectedServiceType.split(" ",1);
	 }
	 //this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
    //alert("Selected: " + SelectedRowId);
    
}

function adjustmentCalcLineDeleteEvent(){
	securityCheckForActions("./Collections/Adjustments/LineItem/destroyButton");
	var conf = confirm("Are u sure want to delete this line item details?");
	 if(!conf){
	   $("#calcLines_"+SelectedRowId).data().kendoGrid.dataSource.read();
	   throw new Error('deletion aborted');
	 }
}

function calcLinesEvent(e){
	
	if(adjustmentStatus!='Created'){
		var grid = $("#calcLines_" + SelectedRowId).data("kendoGrid");
		grid.cancelChanges();
		alert("You can't add adjustment line items,once adjustment is "+adjustmentStatus);
	}else{
		
		$('div[data-container-for="calcLineId"]').remove();
		 $('label[for="calcLineId"]').closest('.k-edit-label').remove();  
		 
		 $('div[data-container-for="adjustmentId"]').remove();
		 $('label[for="adjustmentId"]').closest('.k-edit-label').remove(); 
		 
		 $('div[data-container-for="transactionName"]').remove();
		 $('label[for="transactionName"]').closest('.k-edit-label').remove(); 
		 
			if (e.model.isNew()) 
		    {
				securityCheckForActions("./Collections/Adjustments/LineItem/createButton");
				$(".k-window-title").text("Add New Calc Line Details");
				$(".k-grid-update").text("Save");
				
		    }else{
				securityCheckForActions("./Collections/Adjustments/LineItem/updateButton");
				
				$('div[data-container-for="transactionCode"]').remove();
				$('label[for="transactionCode"]').closest('.k-edit-label').remove();
				
				var gview = $("#grid").data("kendoGrid");
			    var selectedItem = gview.dataItem(gview.select());
			    adjustmentStatus = selectedItem.status;
				if(adjustmentStatus!="Created"){
					$('input[name="amount"]').prop("readonly",true);
				}
				setApCode = $('input[name="calcLineId"]').val();
				$(".k-window-title").text("Edit Calc Line Details");
			}
		
	}
	
	 
	}

function paymentStatusUpdate()
{
	var paymentCollectionId="";
	var gridParameter = $("#grid").data("kendoGrid");
	var selectedAddressItem = gridParameter.dataItem(gridParameter.select());
	paymentCollectionId = selectedAddressItem.paymentCollectionId;
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

function dropDownChecksEditor2(container, options) {
	  $('<input name="adjustmentName" id="adjMasterId" data-text-field="adjName" required="true" validationmessage="Adjustment Type is required" data-value-field="adjName" data-bind="value:' + options.field + '"/>')
	     .appendTo(container)
	     .kendoComboBox({
			dataType: 'JSON',
			placeholder: "Select Adjustment Type",
			dataSource: {
				transport: {
					read: "${adjustmentTypeDropDown}"
				}
			}
		});
	  $('<span class="k-invalid-msg" data-for="adjustmentName"></span>').appendTo(container);
	}

function dropDownChecksEditor(container, options) {
	var res = (container.selector).split("=");
	var attribute = res[1].substring(0,res[1].length-1);
	
	$('<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '"required ="true" name = "'+attribute+'" id = "'+attribute+'Id"/>')
			.appendTo(container).kendoDropDownList({
				optionLabel : {
					text : "Select",
					value : "",
				},
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

function dropDownChecksEditorForServiceType(container, options) {
	var res = (container.selector).split("=");
	var attribute = res[1].substring(0,res[1].length-1);
	
	$('<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '"required ="true" name = "'+attribute+'" id = "'+attribute+'Id"/>')
			.appendTo(container).kendoDropDownList({
				optionLabel : {
					text : "Select",
					value : "",
				},
				defaultValue : false,
				select : selectedServiceType,
				sortable : true,
				dataSource : {
					transport : {
						read : "${allChecksUrl}/"+attribute,
					}
				}
			});
	 $('<span class="k-invalid-msg" data-for="'+attribute+'"></span>').appendTo(container);
}

var selectedService="";
function selectedServiceType(e){
	var dataItem = this.dataItem(e.item.index());
	selectedService = dataItem.text;
	alert(selectedService);
} 

function clearFilterPayments()
{
   $("form.k-filter-menu button[type='reset']").slice().trigger("click");
   var gridStoreIssue = $("#grid").data("kendoGrid");
   gridStoreIssue.dataSource.read();
   gridStoreIssue.refresh();
}

function paymetsDeleteEvent(){
	var conf = confirm("Are u sure want to delete this payment details?");
	 if(!conf){
	  $('#grid').data().kendoGrid.dataSource.read();
	   throw new Error('deletion aborted');
	 }
}

function accountNumberEditor(container, options) {
	  $('<input name="accountNumberEE" id="accountId" data-text-field="accountNo" required="true" validationmessage="Account number is required" data-value-field="accountId" data-bind="value:' + options.field + '"/>')
	     .appendTo(container)
	     .kendoComboBox({
			dataType: 'JSON',
			placeholder: "Select Account Number",
			headerTemplate : '<div class="dropdown-header">'
				+ '<span class="k-widget k-header">Photo</span>'
				+ '<span class="k-widget k-header">Contact info</span>'
				+ '</div>',
			template : '<table><tr><td rowspan=2><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.personId#'/>" width=40 height=55 alt=\"No Image to Display\" /></span></td>'
				+ '<td align="left"><span class="k-state-default"><b>#: data.personName #</b></span><br>'
				+ '<span class="k-state-default"><i>#: data.accountNo #</i></span><br>'
				+ '<span class="k-state-default"><i>#: data.personType #</i></span></td></tr></table>',
			select : accountIdFunction,
			dataSource: {
				transport: {
					read: "${accountNumberAutocomplete}"
				}
			}
		});
	  $('<span class="k-invalid-msg" data-for="accountNumberEE"></span>').appendTo(container);
	}
	
	var selectedAccountId = "";
	function accountIdFunction(e){
		var dataItem = this.dataItem(e.item.index());
		 selectedAccountId = dataItem.accountId;
		 
		 $.ajax
			({
				type : "POST",
				url : "./paymentAdjustments/checkForNotPostedAccounts/" +selectedAccountId,
				async: false,
				dataType : "JSON",
				success : function(response) 
				{
					if(response==false){
						 var grid = $("#grid").data("kendoGrid");
						 grid.cancelChanges();
						 alert("Your previous adjustment is not posted, so please post it to create next adjustment");
					} 
				}
			});
	}

function transactionCodeEditor(container, options) {
	$('<input name="transactionCode" data-text-field="transactionName" id="transactionId" data-value-field="transactionCode" validationmessage="Transaction code is required" data-bind="value:' + options.field + '" required="required"/>')
			.appendTo(container).kendoDropDownList({
				autoBind : false,
				optionLabel : "Select",				
				dataSource : {
					transport : {
						read : "${transactionCodeUrl}/"+res+"/"+SelectedRowId,
					}
				}
				
			});
	 $('<span class="k-invalid-msg" data-for="transactionCode"></span>').appendTo(container); 
}

$("#grid").on("click", "#temPID", function(e) {
	  
	  var button = $(this), enable = button.text() == "Approve";
	  var widget = $("#grid").data("kendoGrid");
	  var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
	  var result=securityCheckForActionsForStatus("./Collections/Adjustments/approvePostButton"); 
	  if(result=="success"){    
		  
						if (enable) 
						{
							$.ajax
							({
								type : "POST",
								url : "./paymentAdjustments/setAdjustmentStatus/" +dataItem.id+ "/activate",
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
									button.text('Poste');
									$('#grid').data('kendoGrid').dataSource.read();
								}
							});
						} 
						else 
						{
						    var r = confirm("Are you sure to post this adjustment?");
							if(r == true){
								$('tr[aria-selected="true"]').find('td:nth-child(17)').html("");
								$('tr[aria-selected="true"]').find('td:nth-child(17)').html("<img src='./resources/images/progress.gif' width='100px' height='25px' />");
							$.ajax
							({
								type : "POST",
								url : "./paymentAdjustments/setAdjustmentStatus/" + dataItem.id + "/deactivate",
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
									button.text('Posted');
									$('#grid').data('kendoGrid').dataSource.read();
								}
							});
						}
				  }
	         }   
 });

var setApCode="";	
var flagTransactionCode="";
function paymentAdjustmentEvent(e)
{
	 /* $('input[name="paymentAmount"]').prop("readonly",true);
	 $('#paymentCalcLinesPopUp').hide(); */
	
	/***************************  to remove the id from pop up  **********************/
	$('div[data-container-for="adjustmentId"]').remove();
	$('label[for="adjustmentId"]').closest('.k-edit-label').remove();
	
	$(".k-edit-field").each(function () {
		$(this).find("#temPID").parent().remove();  
   	});
   	
   	$('div[data-container-for="personName"]').hide();
	$('label[for="personName"]').closest('.k-edit-label').hide();
	
	$('div[data-container-for="property_No"]').remove();
	$('label[for="property_No"]').closest('.k-edit-label').remove();
	
   	$('div[data-container-for="accountNo"]').hide();
	$('label[for="accountNo"]').closest('.k-edit-label').hide();
	
	$('div[data-container-for="jvDate"]').hide();
	$('label[for="jvDate"]').closest('.k-edit-label').hide();
	
	$('div[data-container-for="adjustmentNo"]').remove();
	$('label[for="adjustmentNo"]').closest('.k-edit-label').remove();
	
	$('div[data-container-for="postedToGl"]').remove();
	$('label[for="postedToGl"]').closest('.k-edit-label').remove();
	
	$('div[data-container-for="postedGlDate"]').remove();
	$('label[for="postedGlDate"]').closest('.k-edit-label').remove();
	
	$('div[data-container-for="status"]').remove();
	$('label[for="status"]').closest('.k-edit-label').remove();
	
	$('div[data-container-for="tallystatus"]').remove();
	$('label[for="tallystatus"]').closest('.k-edit-label').remove();
	
	/************************* Button Alerts *************************/
	if (e.model.isNew()) 
    {
		securityCheckForActions("./Collections/Adjustments/createButton");
		flagTransactionCode=true;
		setApCode = $('input[name="adjustmentId"]').val();
		$(".k-window-title").text("Add New Adjustment Details");
		$(".k-grid-update").text("Save");		
    }
	else{
		securityCheckForActions("./Collections/Adjustments/updateButton");
		
		$('div[data-container-for="accountId"]').remove();
		$('label[for="accountId"]').closest('.k-edit-label').remove();
		
		var gview = $("#grid").data("kendoGrid");
	    var selectedItem = gview.dataItem(gview.select());
	    adjustmentStatus = selectedItem.status;
		if(adjustmentStatus!="Created"){
			$('input[name="adjustmentAmount"]').prop("readonly",true);
			var grid = $("#grid").data("kendoGrid");
			grid.cancelChanges();
			alert("Once adjustment is "+adjustmentStatus+" you can't edit adjustment details");
		}else{
			$(".k-window-title").text("Edit Adjustment Details");
		}
		flagTransactionCode=false;
	}
}

var getListBasedOnStatus=0;

$("#grid").on("click", ".k-grid-approveAdjustment", function(e) {
	
	var r = confirm("Are u sure you want to approve all the adjustments?");
	 if(r == true){
		 
		 $.ajax({
				type : "POST",
			    async: false,
			    url:'./billingpayments/getListBasedOnStatus/'+"PaymentAdjustmentEntity/"+"status/"+"Created",
				dataType : "text",
				success : function(response) {
					
					getListBasedOnStatus=response;	
					
			   }
		 
		   });
		 
		 if(getListBasedOnStatus==0){
			
				$("#alertsBox").html("");
				$("#alertsBox").html("Adjustment details are already approved"); 
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

	var result=securityCheckForActionsForStatus("./Collections/Adjustments/approveAllButton");	  
	if(result=="success"){ 
	$.ajax
	({
		type : "POST",
		url : "./paymentAdjustments/approveAdjustment",
		dataType:"text",
		success : function(response) 
		{
			//alert("Payment details are posted");
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
});

var getAdjBasedOnStatus=0;

$("#grid").on("click", ".k-grid-postAdjustment", function(e) {
	
	var r = confirm("Are u sure you want to post all the adjustments?");
	 if(r == true){

		 $.ajax({
				type : "POST",
			    async: false,
			    url:'./billingpayments/getListBasedOnStatus/'+"PaymentAdjustmentEntity/"+"status/"+"Approved",
				dataType : "text",
				success : function(response) {
					
					getAdjBasedOnStatus=response;	
					
			   }
		 
		   });
		 
		 if(getAdjBasedOnStatus==0){
			
				$("#alertsBox").html("");
				$("#alertsBox").html("Adjustment details are already posted"); 
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
		 
		 
	var result=securityCheckForActionsForStatus("./Collections/Adjustments/postAllButton");	  
	if(result=="success"){ 
	$.ajax
	({
		type : "POST",
		url : "./paymentAdjustments/postAdjustmentLedger",
		dataType:"text",
		success : function(response) 
		{
			$("#alertsBox").html("");
			$("#alertsBox").html("Adjustment details are posted");
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
});
		
	function onRequestStart(e){
		
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();	
			/* var gridStoreGoodsReturn = $("#grid").data("kendoGrid");
			gridStoreGoodsReturn.cancelRow(); */		
		
	}
	
	function calcLineOnRequestStart(e){
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
							"Error: Creating the adjustment details \n\n : "
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
			} 
			if (e.response.status == "CHILD") {

				
				$("#alertsBox").html("");
				$("#alertsBox").html("Can't Delete Adjustment Details, Child Record Found");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				$('#grid').data('kendoGrid').dataSource.read(); 
			return false;
		}
			else if (e.type == "create") {
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Adjustment details is created successfully");
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
				$("#alertsBox").html(
						"Adjustment details is deleted successfully");
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
				$("#alertsBox").html(
						"Adjustment details is updated successfully");
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
	
	function calcLineOnRequestEnd(e)
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

					$('#calcLines_'+SelectedRowId).data().kendoGrid.dataSource.read({personId:SelectedAccessCardId});
					/* var grid = $("#propertyGrid_"+SelectedRowId).data("kendoGrid");
					grid.dataSource.read();
					grid.refresh(); */
					return false;
				}
			

		  else if (e.type == "create") 
			{
				$("#alertsBox").html("");
				$("#alertsBox").html("Adjustment line item created successfully");
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
				var gridPets = $("#calcLines_"+SelectedRowId).data("kendoGrid");
				gridPets.dataSource.read();
				gridPets.refresh();
				return false;
			}

			else if (e.type == "update") 
			{
				$("#alertsBox").html("");
				$("#alertsBox").html("Adjustment line item updated successfully");
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
				var gridPets = $("#calcLines_"+SelectedRowId).data("kendoGrid");
				gridPets.dataSource.read();
				gridPets.refresh();
				return false;
			}
				
			else if (e.response.status == "AciveInstructionDestroyError") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Active instruction details cannot be deleted");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				var gridPets = $("#paymentSegments_"+SelectedRowId).data("kendoGrid");
				gridPets.dataSource.read();
				gridPets.refresh();
			return false;
		}	

			else if (e.type == "destroy") 
			{
				$("#alertsBox").html("");
				$("#alertsBox").html("Adjustment line item deleted successfully");
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
	  function postAdjustmentToTally(){	
			var gridParameter = $("#grid").data("kendoGrid");
			var selectedAddressItem = gridParameter.dataItem(gridParameter.select());
			adjustmentId = selectedAddressItem.adjustmentId;
			//alert("inside adjustment"+adjustmentId);

			$('tr[aria-selected="true"]').find('td:nth-child(18)').html("");
			$('tr[aria-selected="true"]').find('td:nth-child(18)').html("<img src='./resources/images/151.gif' width='100px' height='25px' />");
			 	$.ajax
				({
					type : "POST",
					url : "./collections/adjustment/postToTally",
					dataType:"text",
					data : {
						adjustmentId : adjustmentId
					},
					success : function(response) 
					{
						alert(response);
						window.location.reload();
						
					}
				}); 
			
		}
	  

		$("#grid").on("click",".k-grid-postAllAdjustmentToTally",function(e) {

				var r = confirm("First 50 Adjustment will be post");
			if(r==true)
				{
				 $('#dvLoadingbody').show();
				$.ajax({
					type : "POST",
					url : "./adjustment/postCollectionToTally",
					dataType : "text",
					success : function(response) {
						//alert("Payment details are posted");
						alert(response);
						window.location.reload();
						$('#grid').data('kendoGrid').dataSource.read();
					}
				});
			}
		
		});
	
	
	//register custom validation rules

	(function ($, kendo) 
		{  
	    $.extend(true, kendo.ui.validator, 
	    {
	         rules: 
	         { // custom rules
	        	 /* adjustmentNoRequired : function(input, params){
                     if (input.attr("name") == "adjustmentNo")
                     {
                      return $.trim(input.val()) !== "";
                     }
                     return true;
                    }, */
                    ledgerTypeRequired : function(input, params){
                        if (input.attr("name") == "adjustmentLedger")
                        {
                         return $.trim(input.val()) !== "";
                        }
                        return true;
                       },
                    adjustmentDateRequired : function(input, params){
                         if (input.attr("name") == "adjustmentDate")
                            {
                              return $.trim(input.val()) !== "";
                              }
                              return true;
                             },
                    adjustmentDateValidation : function(input,params) {
									if (input.filter("[name = 'adjustmentDate']").length && input.val()) {
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
				  adjustmentAmountRequired : function(input, params){
			                           if (input.attr("name") == "adjustmentAmount")
			                           {
			                            return $.trim(input.val()) !== "";
			                           }
			                           return true;
			                          },	 
			       calcLineAmountRequired : function(input,params){
			    	   if (input.attr("name") == "amount")
                       {
                        return $.trim(input.val()) !== "";
                       }
                       return true;
			       }  ,
			       adjustmentAmountLengthValidation: function (input, params) 
		             {         
		                 if (input.filter("[name='adjustmentAmount']").length && input.val() != "") 
		                 {
		                	 return /^[0-9-]{1,10}$/.test(input.val());
		                 }        
		                 return true;
		             }
	            },
	         messages: 
	         {
				//custom rules messages
				/* adjustmentNoRequired:"Adjustment number is required", */
				ledgerTypeRequired:"Ledger type is required",
				adjustmentDateRequired:"Adjustment date is required",
			    adjustmentDateValidation:"Adjustment date must be past",
			    adjustmentAmountRequired:"Amount is required", 
			    calcLineAmountRequired:"Amount is required",
			    adjustmentAmountLengthValidation : "Amount max length is 10 digit number"
			 }
	    });
	    
	})(jQuery, kendo);
	 //End Of Validation
	 
	 
	 $("#grid")
	.on(
			"click",
			".k-grid-adjustmentToAll",
			function(e) {	
				
				 $('#serviceTypeApprove').val('');
				$('#adjustmentTypeApprove').val('select');
				var bbDialog = $("#adjustmentToAlldiv");
				bbDialog.kendoWindow({
					width : "300px",
					height : "250px",
					modal : true,
					draggable : true,
					position : {
						top : 100
					},
					title : "Adjustment To All"
				}).data("kendoWindow").center().open();
				
				$("[name=radio1]").removeAttr("checked");
				$("[name=radio2]").removeAttr("checked");
				$('.propertyNumber').hide();
				$('.blockNames').hide();
				$('.allBlock').hide();
				$('#adjustmentToAllForm').trigger("reset");				
				bbDialog.kendoWindow("open");
				
			});
	/*  $("#monthpicker").kendoDatePicker({
			// defines the start view
			start : "year",
			// defines when the calendar should return date
			depth : "year",
			value : new Date(),
			// display month and year in the input
			format : "MMMM yyyy"
		}); */
	 
	 $("#grid").on("click",".k-grid-paymentAdjustmentTemplatesDetailsExport", function(e) {
		 //var month = $('#fromMonthpicker').val();
		  var fromDate = $('#fromMonthpicker').val();
		  var fDate=fromDate.split("/").join("-");
          var toDate = $('#toMonthpicker').val();
          var toDate1=toDate.split("/").join("-");
          
		  window.open("./paymentAdjustmentTemplate/paymentAdjustmentTemplatesDetailsExport/"+fDate+"/"+toDate1);
	}); 
		
		$("#grid").on("click",".k-grid-generateXmlTallyPosting",function(e){
			//alert("");
			var bbDialog = $("#paymentTallyPostingDiv");
			bbDialog.kendoWindow({
				width : "250",
				height : "150",
				modal : true,
				draggable : true,
				position : {
					top : 100
				},
				title : "Adjustment Tally Posting XML"
			}).data("kendoWindow").center().open();

			bbDialog.kendoWindow("open");
		});
		$("#paymentTallyPostingDivForm").submit(function(e) {
			e.preventDefault();
		});

		$("#paymentTallyPostingXml").on("click", function(){
			var month=$("#presentdatepostBillXML").val();
			//alert(month);
			window.open("./PaymentAdjustment/GenerateXmlTallyPosting?month="+month);
			closeXmlDiv();
		});
			function closeXmlDiv(){
				
				var bbDialog = $("#paymentTallyPostingDiv");
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

function exportAdjustmentsTOexcel()
{	
		var month1 = $("#monthDatePicker").val();	
		alert(month1);	
		if(month1 == '' ){
			alert("Select month to export payments");
		}else{
			window.open("./exportAdjustmentsToExcel?date="+month1);
		}
}
</script>

