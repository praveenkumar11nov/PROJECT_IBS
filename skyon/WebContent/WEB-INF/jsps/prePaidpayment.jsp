<%@include file="/common/taglibs.jsp"%>

<c:url value="/prePaidpaymentAdjustments/prePaidpaymentAdjustmentCreate" var="createUrl" />
<c:url value="/prePaidpaymentAdjustments/readUrl" var="readUrl" />
<c:url value="/prePaidpaymentAdjustments/paymentAdjustmentUpdate" var="updateUrl" />
<c:url value="/billingPayments/getAllBankNames" var="getAllBankNames" />
<c:url value="/prePaidpaymentAdjustments/destroyUrl" var="destroyUrl" />
<%-- <c:url value="/paymentAdjustments/paymentAdjustmentDestroy" var="destroyUrl" />
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
<c:url value="/billingPayments/getPersonListForFileter" var="personNamesFilterUrl" /> --%>
<c:url value="/adjustmentMaster/getAllAdjustmentName" var="adjustmentTypeDropDown" />
<c:url value="/prePaidPayment/getMeterNumberUrl" var="getMeterNumberUrl" />
<c:url value="/prePaidPayment/getPropertyNamesUrl" var="getPropertyNamesUrl" />
<c:url value="/common/getAllChecks" var="allChecksUrl" />
<kendo:grid name="grid" remove="adjustmentsDeleteEvent" change="onChangeAdjustments" pageable="true" resizable="true" edit="paymentAdjustmentEvent" dataBound="paymentAdjustmentDataBound" sortable="true" reorderable="true" selectable="flase" scrollable="true" filterable="false" groupable="true">

    <kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" refresh="true" info="true" previousNext="true">
		<kendo:grid-pageable-messages itemsPerPage="Adjustments per page" empty="No adjustment to display" refresh="Refresh all the adjustments" 
			display="{0} - {1} of {2} New adjustments" first="Go to the first page of adjustments" last="Go to the last page of adjustments" next="Go to the next page of adjustments"
			previous="Go to the previous page of adjustments"/>
	</kendo:grid-pageable>
	<kendo:grid-filterable extra="false">
		<kendo:grid-filterable-operators>
			<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains" />
				<%-- <kendo:grid-filterable-operators-date gt="Is after" lt="Is before"/>
				<kendo:grid-filterable-operators-number eq="Is equal to" gt="Is greather than" gte="IS greather than and equal to" lt="Is less than" lte="Is less than and equal to" neq="Is not equal to"/> --%>
		</kendo:grid-filterable-operators> 
	</kendo:grid-filterable>
	<kendo:grid-editable mode="popup"/>
		<kendo:grid-toolbar>
		      <kendo:grid-toolbarItem name="create" text="Adjustment" />
		      <kendo:grid-toolbarItem template="<label class='category-label'>&nbsp;&nbsp;From&nbsp;Date&nbsp;:&nbsp;&nbsp;</label><input id='fromMonthpicker' style='width:110px'/>"/>
			  <kendo:grid-toolbarItem template="<label class='category-label'>&nbsp;&nbsp;To&nbsp;Date&nbsp;:&nbsp;&nbsp;</label><input id='toMonthpicker' style='width:110px'/>"/>
			  <kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=searchByMonth() title='Search' style='width:90px'>Search</a>"/> 
		      <kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterPayments()>Clear Filter</a>"/>
		   
	    </kendo:grid-toolbar> 
	<kendo:grid-columns>
	    
	    <kendo:grid-column title="adjustmentId" field="adjustmentId" width="10px" hidden="true" filterable="false" sortable="false" />
	     <kendo:grid-column title="Consumer&nbsp;Id&nbsp;*" field="consumerNo" width="95px" filterable="true" editor="meterNumberEditor">
	    </kendo:grid-column>
	    <kendo:grid-column title="Property&nbsp;Number&nbsp;*" field="propertyId" hidden="true" width="140px" filterable="true" >
	    </kendo:grid-column>
	     <kendo:grid-column title="Property&nbsp;No&nbsp;*" field="property_No" filterable="true"  width="90px" editor="propertyEditor">
		</kendo:grid-column>	
	    <kendo:grid-column title="Meter&nbsp;Number&nbsp;*" field="consumerId" width="95px" filterable="true" editor="meterNumberEditor">
	    </kendo:grid-column>
	  
	    <kendo:grid-column title="Person&nbsp;Name" field="personName"  width="110px" filterable="false">
	    	</kendo:grid-column>
	    
	   <kendo:grid-column title="JV&nbsp;Date" field="jvDate" format="{0:dd/MM/yyyy hh:mm tt}" hidden="true" width="135px" filterable="true">
	    </kendo:grid-column> 
	    
	    <kendo:grid-column title="Adjustment&nbsp;Date&nbsp;*" field="adjustmentDate" format="{0:dd/MM/yyyy}" width="125px" filterable="false">
	    </kendo:grid-column>
	    
	    <kendo:grid-column title="Adjustment&nbsp;No&nbsp;*" field="adjustmentNo" width="110px" filterable="true">
	   <%--  <kendo:grid-column-filterable >
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
				</kendo:grid-column-filterable> --%>
	    </kendo:grid-column>
	    
	   <%--  <kendo:grid-column title="Ledger&nbsp;Type&nbsp;*" field="adjustmentLedger" width="115px" filterable="true" editor="dropDownChecksEditor">
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
	    </kendo:grid-column> --%>
	    
	    <kendo:grid-column title="Adjustment&nbsp;Type&nbsp;*" field="paymentMode" width="115px" filterable="true" editor="dropDownChecksEditor">
	   <%--  <kendo:grid-column-filterable >
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
				</kendo:grid-column-filterable> --%>
	    </kendo:grid-column>
	    
	    <kendo:grid-column title="Adjustment&nbsp;Amount&nbsp;*" field="adjustmentAmount" width="125px" filterable="false">
	    </kendo:grid-column>
	    
	    <kendo:grid-column title="Remarks" field="remarks" filterable="true" editor="chequeBounceRemarksEditor" width="120px">
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
	   <%--  <kendo:grid-column-filterable >
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
				</kendo:grid-column-filterable> --%>
	    </kendo:grid-column>
	  
	    
	    <kendo:grid-column title="Status" field="status" width="60px" filterable="true" editor="statusEditor">
	   
	    </kendo:grid-column>
 	    
	<%--    <kendo:grid-column title="TokenNumber" field="tokenNumber" width="60px" filterable="true">
	   
	    </kendo:grid-column>
	    
	     <kendo:grid-column title="TokenStatus" field="tokenStatus" width="60px" filterable="true" >  <!-- editor="tokenstatuseditor" -->
	   
	    </kendo:grid-column> --%>
	  

		<kendo:grid-column title="&nbsp;" width="160px">
			<kendo:grid-column-command>
			    <kendo:grid-column-commandItem name="edit"/>
			    <kendo:grid-column-commandItem name="destroy"/>
			</kendo:grid-column-command>
		</kendo:grid-column>
		
		<%-- <kendo:grid-column title="&nbsp;" width="110px">
				<kendo:grid-column-command >
					 <kendo:grid-column-commandItem name="Paid" click="paymentStatusUpdate" />
				</kendo:grid-column-command>
		    </kendo:grid-column> --%>	
		
		    <%--  <kendo:grid-column title=""
				template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Active'>#= data.status == 'Not Cleared' ? 'Approve' : data.status == 'Approved' #</a>"
				width="80px" />   --%>
				 
				<%-- <kendo:grid-column title="&nbsp;" width="110px">
				<kendo:grid-column-command >
					 <kendo:grid-column-commandItem name="Post To Tally" click="postAdjustmentToTally" />
				</kendo:grid-column-command>
		</kendo:grid-column>	 --%>
		
	</kendo:grid-columns>

	<kendo:dataSource pageSize="20" requestEnd="onRequestEnd" requestStart="onRequestStart">
		<kendo:dataSource-transport>
			<kendo:dataSource-transport-create url="${createUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json" />
			 <kendo:dataSource-transport-update url="${updateUrl}" dataType="json" type="GET" contentType="application/json" />
			 <kendo:dataSource-transport-destroy url="${destroyUrl}/" dataType="json" type="GET" contentType="application/json" /> 
		</kendo:dataSource-transport>

		<kendo:dataSource-schema >
			<kendo:dataSource-schema-model id="adjustmentId">
				<kendo:dataSource-schema-model-fields>
					<kendo:dataSource-schema-model-field name="adjustmentId" type="number"/>
					
					<kendo:dataSource-schema-model-field name="propertyId" type="number" defaultValue="">
					</kendo:dataSource-schema-model-field>
					
				  <kendo:dataSource-schema-model-field name="consumerId" type="string">
					</kendo:dataSource-schema-model-field> 
					
					<kendo:dataSource-schema-model-field name="adjustmentDate" type="date">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="personName" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="property_No" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="adjustmentNo" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="consumerNo" type="string">
					</kendo:dataSource-schema-model-field>
					
					
					<kendo:dataSource-schema-model-field name="adjustmentAmount" type="number" defaultValue="">
					       <kendo:dataSource-schema-model-field-validation />
					</kendo:dataSource-schema-model-field>
					
					<%-- <kendo:dataSource-schema-model-field name="postedToGl" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="postedGlDate" type="date">
					</kendo:dataSource-schema-model-field> --%>
					
					<kendo:dataSource-schema-model-field name="status" type="string">
					</kendo:dataSource-schema-model-field>
					
				<%--	<kendo:dataSource-schema-model-field name="tokenNumber" type="string">
					</kendo:dataSource-schema-model-field>
					
					 <kendo:dataSource-schema-model-field name="tokenStatus" type="string">
					</kendo:dataSource-schema-model-field> --%>
					
					
					<kendo:dataSource-schema-model-field name="paymentMode" type="string">
					</kendo:dataSource-schema-model-field>
					<%-- <kendo:dataSource-schema-model-field name="tallystatus" type="string">
					</kendo:dataSource-schema-model-field> --%>
						<kendo:dataSource-schema-model-field name="bankName" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="instrumentDate" type="date">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="instrumentNo" type="string">
					</kendo:dataSource-schema-model-field>
				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>

	</kendo:dataSource>

</kendo:grid>

<div id="alertsBox" title="Alert"></div>
<%-- <div id="adjustmentToAlldiv" style="display: none;">
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
</div> --%>
<script>
function propertyEditor(container, options) 
{
$('<input name="Property No" id="property_No" data-text-field="property_No" data-value-field="propertyId" data-bind="value:' + options.field + '" required="true"/>')
.appendTo(container).kendoComboBox({
	autoBind : false,			
	dataSource : {
		transport : {		
			read :  "${getPropertyNamesUrl}"
		}
	},
	change : function (e) {
        if (this.value() && this.selectedIndex == -1) {                    
			alert("P doesn't exist!");
            $("#property_No").data("kendoComboBox").value("");
    	}

    }  
});

$('<span class="k-invalid-msg" data-for="Property No"></span>').appendTo(container);
}

function meterNumberEditor(container, options) 
{
$('<input name="Consumer Id" id="consumerId" data-text-field="consumerId" data-value-field="consumerId" data-bind="value:' + options.field + '" required="true"/>')
.appendTo(container).kendoComboBox({
	cascadeFrom : "property_No", 
	autoBind : false,			
	dataSource : {
		transport : {		
			read :  "${getMeterNumberUrl}"
		}
	},
	change : function (e) {
        if (this.value() && this.selectedIndex == -1) {                    
			alert("P doesn't exist!");
            $("#consumerId").data("kendoComboBox").value("");
    	}

    }  
});

$('<span class="k-invalid-msg" data-for="Consumer Id"></span>').appendTo(container);
}
function statusEditor(container, options) {
	var res = (container.selector).split("=");
	var attribute = res[1].substring(0,res[1].length-1);
	
	$('<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '"required ="true" name = "'+attribute+'" id = "status"/>')
			.appendTo(container).kendoComboBox({
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

/* function tokenstatuseditor(container, options) {
	var res = (container.selector).split("=");
	var attribute = res[1].substring(0,res[1].length-1);
	
	$('<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '"required ="true" name = "'+attribute+'" id = "status"/>')
			.appendTo(container).kendoComboBox({
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
 */
function chequeBounceRemarksEditor(container, options){
	$('<textarea data-text-field="remarks"  name="remarks" style="width:150px;height:60px" required="true"/>' ).appendTo(container);
	$('<span class="k-invalid-msg" data-for="Enter Remarks"></span>').appendTo(container);
}

/* var approvebillvalidator = $("#adjustmentToAllForm").kendoValidator().data("kendoValidator");
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
	
} */
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
   	
	
   
   /* 	$("#serviceTypeApprove").kendoDropDownList({
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
		optionLabel : "Select", 
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
		 optionLabel : "Select",  
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
 	}); */
   	 
   	
});


/* function blockBy()
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
 */
 
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
		url : "./prePaidPayment/searchAdjustmentDataByMonth",
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
        if (adjustmentStatus == 'Cleared') {
        	var currenRow = grid.table.find("tr[data-uid='" + currentUid+ "']");
			
			/* var postButton = $(currenRow).find(".k-grid-Active");
			postButton.hide();        	
			
			var editButton = $(currenRow).find(".k-grid-edit");
			editButton.hide(); */
			
			var deleteButton = $(currenRow).find(".k-grid-delete");
			deleteButton.hide();
		} 
        /*    if (tallystatus=='Posted') {
        	
        	var currenRow = grid.table.find("tr[data-uid='" + currentUid+ "']");
			var postTotally = $(currenRow).find(".k-grid-PostToTally");
			postTotally.hide();
        }   */
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
/* function parse (response) {
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
} */

var SelectedRowId = "";
var selectedServiceType="";
var adjustmentStatus = "";
var res="";
var propertyNum="";
var bankname="";
//var consumerID="";
function onChangeAdjustments(arg) {
	 var gview = $("#grid").data("kendoGrid");
	 var selectedItem = gview.dataItem(gview.select());
	 SelectedRowId = selectedItem.adjustmentId;
	 adjustmentStatus = selectedItem.status;
	 propertyNum = selectedItem.property_No;
	/*  bankname = selectedItem.bankName;
	 alert(bankname); */
	 //adjustmentStatus = selectedItem.status;
	
	 //this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
    //alert("Selected: " + SelectedRowId);
    
}

/* function adjustmentCalcLineDeleteEvent(){
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
 */
/* function paymentStatusUpdate()
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
} */

/* function dropDownChecksEditor2(container, options) {
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
	} */

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
		
	 	if(selectedPaymentMode!="Online"){ 
			
			if(selectedPaymentMode=="RTGS/NEFT"){
				   
				$('label[for="instrumentDate"]').text("Created Date *");
			    $('label[for="instrumentNo"]').text("Transaction Id*");
			    $('label[for="bankName"]').text("Bank Name");
			    
			    $('div[data-container-for="instrumentNo"]').show();
				$('label[for="instrumentNo"]').closest('.k-edit-label').show();
				
				$('div[data-container-for="instrumentDate"]').show();
				$('label[for="instrumentDate"]').closest('.k-edit-label').show();
				
				$('div[data-container-for="bankName"]').show();
				$('label[for="bankName"]').closest('.k-edit-label').show();
				
		
				
			
				
			 } else{
				
				$('label[for="instrumentDate"]').text(selectedPaymentMode+" Date *");
			    $('label[for="instrumentNo"]').text(selectedPaymentMode+" Number *");
			    //$('label[for="bankName"]').text("Bank Name");
			    
			    $('div[data-container-for="instrumentNo"]').show();
				$('label[for="instrumentNo"]').closest('.k-edit-label').show();
				
				$('div[data-container-for="instrumentDate"]').show();
				$('label[for="instrumentDate"]').closest('.k-edit-label').show();
				
				$('div[data-container-for="bankName"]').show();
				$('label[for="bankName"]').closest('.k-edit-label').show();
			
			}
	 	}else if(selectedPaymentMode=="Online"){
				$('label[for="instrumentDate"]').text(selectedPaymentMode+" Date *");
			    $('label[for="instrumentNo"]').text(selectedPaymentMode+" Number *");
			    //$('label[for="bankName"]').text("Bank Name");
			    
			    $('div[data-container-for="instrumentNo"]').show();
				$('label[for="instrumentNo"]').closest('.k-edit-label').show();
				
				$('div[data-container-for="instrumentDate"]').show();
				$('label[for="instrumentDate"]').closest('.k-edit-label').show();
				
				
				$('div[data-container-for="bankName"]').hide();
				$('label[for="bankName"]').closest('.k-edit-label').hide();
			
				}
	 	
	}

 
/* function dropDownChecksEditorForServiceType(container, options) {
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

 */
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

/* function accountNumberEditor(container, options) {
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
	} */
	
	/* var selectedAccountId = "";
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
 */
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
	
	$('div[data-container-for="propertyId"]').remove();
	$('label[for="propertyId"]').closest('.k-edit-label').remove();
	
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
	
	$('div[data-container-for="consumerNo"]').remove();
	$('label[for="consumerNo"]').closest('.k-edit-label').remove(); 
	
	$('div[data-container-for="tallystatus"]').remove();
	$('label[for="tallystatus"]').closest('.k-edit-label').remove();
	$('div[data-container-for="instrumentNo"]').hide();
	$('label[for="instrumentNo"]').closest('.k-edit-label').hide();

	$('div[data-container-for="instrumentDate"]').hide();
	$('label[for="instrumentDate"]').closest('.k-edit-label').hide();

	
	 $('div[data-container-for="bankName"]').hide();
		$('label[for="bankName"]').closest('.k-edit-label').hide(); 
	/************************* Button Alerts *************************/
	if (e.model.isNew()) 
    {
		
		
		//securityCheckForActions("./Collections/Adjustments/createButton");
		flagTransactionCode=true;
		setApCode = $('input[name="adjustmentId"]').val();

		$(".k-window-title").text("Add New Adjustment Details");
		$(".k-grid-update").text("Save");		
    }
	else{

			//$("#property_No").data("kendoComboBox").value(propertyNum);
		$('div[data-container-for="property_No"]').hide();
	    $('label[for="property_No"]').closest('.k-edit-label').hide(); 
	    $('div[data-container-for="consumerId"]').hide();
	    $('label[for="consumerId"]').closest('.k-edit-label').hide(); 
	    $('div[data-container-for="paymentMode"]').hide();
	    $('label[for="paymentMode"]').closest('.k-edit-label').hide(); 
		/* $('input[name="adjustmentDate"]').attr('readonly', 'readonly');
		$('input[name="adjustmentNo"]').attr('readonly', 'readonly');
		$('input[name="paymentMode"]').attr('readonly', 'readonly');
		$('input[name="adjustmentAmount"]').attr('readonly', 'readonly');
		$('input[name="remarks"]').attr('readonly', 'readonly'); */
	  
	//securityCheckForActions("./Collections/Adjustments/updateButton");
		
		var gview = $("#grid").data("kendoGrid");
	    var selectedItem = gview.dataItem(gview.select());
	    adjustmentStatus = selectedItem.status;
	   alert(adjustmentStatus);
	  
		if(adjustmentStatus!="Not Cleared"){
			$('input[name="adjustmentAmount"]').prop("readonly",true);
			var grid = $("#grid").data("kendoGrid");
			grid.cancelChanges();
			alert("Once adjustment Status is "+adjustmentStatus+" you can't edit adjustment details");
		}else{
			
		}
		flagTransactionCode=false; 
		$(".k-window-title").text("Edit Adjustment Details");
	}
}

var getListBasedOnStatus=0;

/* $("#grid").on("click", ".k-grid-approveAdjustment", function(e) {
	
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
 */
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
	
	/* function calcLineOnRequestStart(e){
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();	
	} */
	function onRequestEnd(e) {
		if (typeof e.response != 'undefined') {
			if (e.response.status == "FAIL") {
				errorInfo = "";
				for (var i = 0; i < e.response.result.length; i++) {
					errorInfo += (i + 1) + ". "
							+ e.response.result[i].defaultMessage + "<br>";
				}

				if (e.type == "create") {
					alert($("#instrumentDate").val());
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
	
		/* $("#grid").on("click",".k-grid-postAllAdjustmentToTally",function(e) {

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
	
	 */
	//register custom validation rules
var paymentModeFlag = true;
	var rtgsFlag=true;
	(function ($, kendo) 
		{  
	    $.extend(true, kendo.ui.validator, 
	    {
	         rules: 
	         { 
	        	 paymentModeRequired : function(input, params) {
						if (input.attr("name") == "paymentMode") {
							if (input.val() == "Online") {
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
					adjustmentDateRequired : function(input, params){
                        if (input.attr("name") == "adjustmentDate")
                           {
                             return $.trim(input.val()) !== "";
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
     /*   calcLineAmountRequired : function(input,params){
    	   if (input.attr("name") == "amount")
           {
            return $.trim(input.val()) !== "";
           }
           return true;
       }  , */
       adjustmentAmountLengthValidation: function (input, params) 
         {         
             if (input.filter("[name='adjustmentAmount']").length && input.val() != "") 
             {
            	 return /^[0-9-]{1,10}$/.test(input.val());
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
						adjustmentDateRequired:"Adjustment date is required",
					    adjustmentDateValidation:"Adjustment date must be past",
					    adjustmentAmountRequired:"Amount is required", 
					    //calcLineAmountRequired:"Amount is required",
					    adjustmentAmountLengthValidation : "Amount max length is 10 digit number"
				}
	    });
	    
	})(jQuery, kendo);
	 //End Of Validation
	 
	 
	/*  $("#grid")
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
				
			}); */
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
</script>

