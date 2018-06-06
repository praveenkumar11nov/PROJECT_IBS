<%@include file="/common/taglibs.jsp"%>

<c:url value="/chequeBounce/chequeBounceCreate" var="createUrl" />
<c:url value="/chequeBounce/chequeBouncetRead" var="readUrl" />
<c:url value="/chequeBounce/chequeBounceDestroy" var="destroyUrl" />
<c:url value="/chequeBounce/chequeBounceUpdate" var="updateUrl" />

<c:url value="/paymentAdjustments/getAllPaidAccountNumbers" var="accountNumberAutocomplete"/>
<c:url value="/billingPayments/getAllBankNames" var="getAllBankNames"/>
<c:url value="/chequeBounce/getAllReceiptNos" var="getAllReceptNoBasedOnAccountId"/>

<c:url value="/paymentAdjustments/filter" var="commonFilterForAdjustmentsUrl"/>

<kendo:grid name="grid" remove="chequeBounceDeleteEvent" change="onChangeChequeBounce" pageable="true" dataBound="chequeBounceDataBound" resizable="true" edit="chequeBounceEvent" sortable="true" reorderable="true" selectable="true" scrollable="true" filterable="false" groupable="true">

    <kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" refresh="true" info="true" previousNext="true">
		<kendo:grid-pageable-messages itemsPerPage="Cheques per page" empty="No Cheques to display" refresh="Refresh all the Cheques" 
			display="{0} - {1} of {2} New Cheques" first="Go to the first page of Cheques" last="Go to the last page of Cheques" next="Go to the next page of Cheques"
			previous="Go to the previous page of Cheques"/>
	</kendo:grid-pageable>
	<kendo:grid-filterable extra="false">
		<kendo:grid-filterable-operators>
			<kendo:grid-filterable-operators-string eq="Is equal to" doesnotcontain="Does not contain" endswith="Ends with" neq=" Is not equal to" startswith="Starts with" contains="Contains"/>
				<kendo:grid-filterable-operators-date gt="Is after" lt="Is before" eq="Is equal to" gte="Is after or equal to" lte="Is before or equal to" neq="Is not equal to"/>
				<kendo:grid-filterable-operators-number eq="Is equal to" gt="Is greather than" gte="Is greather than and equal to" lt="Is less than" lte="Is less than and equal to" neq="Is not equal to"/>
		</kendo:grid-filterable-operators> 
	</kendo:grid-filterable>
	<kendo:grid-editable mode="popup"/>
		<kendo:grid-toolbar>
		      <kendo:grid-toolbarItem name="create" text="New Cheque Details" />
		      <kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterChequeBounce()>Clear Filter</a>"/>
		      <kendo:grid-toolbarItem name="chequeBounceTemplatesDetailsExport" text="Export To Excel" /> 
	    </kendo:grid-toolbar>
	<kendo:grid-columns>
	    
	    <kendo:grid-column title="chequeBounceId" field="chequeBounceId" width="10px" hidden="true" filterable="false" sortable="false" />
	    
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
	    
	    <kendo:grid-column title="Cheque&nbsp;No&nbsp;*" field="chequeNo" width="110px" filterable="true">
	    <kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function ledgerStatusFilter(element) {
								element.kendoAutoComplete({
											placeholder : "Enter Instrument Number",
											dataSource : {
												transport : {
													read : "${commonFilterForPaymentCollectionUrl}/chequeNo"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	    </kendo:grid-column>
	    
	    <kendo:grid-column title="Bank&nbsp;Name&nbsp;*" field="bankName" width="110px" filterable="true" editor="bankNames">
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
	    <kendo:grid-column title="Property&nbsp;No&nbsp;*" field="property_No" filterable="true" width="100px" >
		</kendo:grid-column>	
		
	    <kendo:grid-column title="Cheque&nbsp;Date&nbsp;*" field="chequeGivenDate" format="{0:dd/MM/yyyy}" width="145px">
	    </kendo:grid-column>
	    
	    <kendo:grid-column title="Bounced&nbsp;Date&nbsp;*" field="bouncedDate" format="{0:dd/MM/yyyy}" width="110px">
	    </kendo:grid-column>
	    
	    <kendo:grid-column title="Cheque&nbsp;Amount&nbsp;*" field="chequeAmount" filterable="true" width="120px">
		</kendo:grid-column>
		
		<kendo:grid-column title="Bank&nbsp;Charges&nbsp;*" field="bankCharges" filterable="true" width="120px">
		</kendo:grid-column>
		
		<kendo:grid-column title="Cheque&nbsp;Penality&nbsp;Amount&nbsp;*" field="penalityAmount" filterable="true" width="120px">
		</kendo:grid-column>
		
		<kendo:grid-column title="Prev.&nbsp;Payment&nbsp;Late&nbsp;Amount" field="previousLateAmount" filterable="true" width="120px">
		</kendo:grid-column>
		
		<kendo:grid-column title="Valid" field="amountValid" filterable="true" hidden="true" width="120px">
		</kendo:grid-column>
		
		<kendo:grid-column title="Remarks" field="remarks" filterable="true" editor="chequeBounceRemarksEditor" width="120px">
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
													read : "${commonFilterForAdjustmentsUrl}/status"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	    </kendo:grid-column>

		<kendo:grid-column title="&nbsp;" width="110px">
			<kendo:grid-column-command>
			    <%-- <kendo:grid-column-commandItem name="edit"/> --%>
				<kendo:grid-column-commandItem name="destroy" />
			</kendo:grid-column-command>
		</kendo:grid-column>
		
		<kendo:grid-column title="&nbsp;" width="110px">
				<kendo:grid-column-command >
					 <kendo:grid-column-commandItem name="Approve" click="chequebounceDetailsApprove" />
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

		<kendo:dataSource-schema>
			<kendo:dataSource-schema-model id="chequeBounceId">
				<kendo:dataSource-schema-model-fields>
					<kendo:dataSource-schema-model-field name="chequeBounceId" type="number"/>
					
					<kendo:dataSource-schema-model-field name="accountId" type="number" defaultValue="">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="receiptNo" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="chequeNo" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="personName" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="property_No" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="bankName" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="accountNo" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="chequeGivenDate" type="date">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="chequeAmount" type="number" defaultValue="">
					       <kendo:dataSource-schema-model-field-validation min="0"/>
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="penalityAmount" type="number" defaultValue="">
					       <kendo:dataSource-schema-model-field-validation min="0"/>
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="bankCharges" type="number" defaultValue="">
					       <kendo:dataSource-schema-model-field-validation min="0"/>
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="previousLateAmount" type="number" defaultValue="0">
					       <kendo:dataSource-schema-model-field-validation min="0"/>
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="bouncedDate" type="date">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="status" type="string">
					</kendo:dataSource-schema-model-field>
					
				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>

	</kendo:dataSource>

</kendo:grid>


<div id="alertsBox" title="Alert"></div>
<script>

function chequeBounceDeleteEvent(){
	securityCheckForActions("./Collections/Adjustments/destroyButton");
	var conf = confirm("Are u sure want to delete this cheque details?");
	 if(!conf){
	  $("#grid").data().kendoGrid.dataSource.read();
	   throw new Error('deletion aborted');
	 }
}

var SelectedRowId = "";
var res="";
function onChangeChequeBounce(arg) {
	 var gview = $("#grid").data("kendoGrid");
	 var selectedItem = gview.dataItem(gview.select());
	 SelectedRowId = selectedItem.chequeBounceId;
	 this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
}

function clearFilterChequeBounce()
{
   $("form.k-filter-menu button[type='reset']").slice().trigger("click");
   var gridStoreIssue = $("#grid").data("kendoGrid");
   gridStoreIssue.dataSource.read();
   gridStoreIssue.refresh();
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
			dataSource: {
				transport: {
					read: "${accountNumberAutocomplete}"
				}
			},
			change : function (e) {
	            if (this.text() && this.selectedIndex == -1) {                    
					alert("Account doesn't exist!");
	                $("#accountId").data("kendoComboBox").value("");
	        	}
		    }
		});
	  $('<span class="k-invalid-msg" data-for="accountNumberEE"></span>').appendTo(container);
	}	
	
function bankNames(container, options) 
{
		$('<input name="bankName" id="bankName" data-text-field="bankName" data-value-field="bankName" data-bind="value:' + options.field + '"/>')
		.appendTo(container).kendoComboBox({
		 autoBind : false,
		 placeholder : "Select Bank",
		 change : chequeNumberAndBankSelectedFunction,
		 dataSource : {
		  transport : {  
		   read :  "${getAllBankNames}"
		  }
		 }
		});
		
		$('<span class="k-invalid-msg" data-for="bankName"></span>').appendTo(container);
}

var paymentAmount = "";
var accNo="";

function chequeNumberAndBankSelectedFunction(e){
	
	 var chequeNo = $("input[name=chequeNo]").val();
	 var bankName = $("#bankName").val();
	 var receiptNo = $("input[name=receiptNo]").val();
	 
	 $.ajax
		({
			type : "POST",
			url : "./billingPayments/getChequeDetailsBasedOnChequeNumber/"+chequeNo+"/"+bankName+"/"+receiptNo,
			async: false,
			dataType : "JSON",
			success : function(response) 
			{
				if(response.length==0){
					$("input[name=chequeAmount]").data("kendoNumericTextBox").value("");
					$("#accountId").data("kendoComboBox").value("");
					$("input[name=chequeGivenDate]").val(""); 
				}
				for ( var s = 0, len = response.length; s < len; ++s) {
	              	var obj = response[0];
	              	 paymentAmount = obj.paymentAmount;
	              	// receiptNo = obj.receiptNo;
	              	$("input[name=chequeAmount]").data("kendoNumericTextBox").value(obj.paymentAmount);
	              //	$("input[name=chequeGivenDate]").data("kendoDatePicker").value(obj.instrumentDate);
	              	//$("input[name=receiptNo]").val(obj.receiptNo);
	              	$("#accountId").data("kendoComboBox").value(obj.accountNo);
	              //	$("input[name=accountNo]").data("kendoComboBox").value(obj.accountNo);
	              	   accNo =(obj.accountId);
	              	
	               
	              	
	              	var newvalidf=(obj.instrumentDate).split("-").reverse().join("/");
	             	//var date=kendo.toString(obj.instrumentDate,'MM/dd/yyyy');
	              	$("input[name=chequeGivenDate]").val(newvalidf);          
	              	
	              
	              	
	              	$("input[name=chequeGivenDate]").data("kendoDatePicker").enable(false);
	              	
	               $("input[name=chequeGivenDate]").prop("readonly", true);
	               /* $("input[name=receiptNo]").prop("readonly", true); */
	               $("input[name=chequeAmount]").prop("readonly", true);
	               $("input[name=chequeAmount]").data("kendoNumericTextBox").enable(false);
	               $("#accountId").data("kendoComboBox").enable(false);
	               
	              	//$("#accountId").val(obj.accountNo);
				}
			}
		});
}

function chequeBounceRemarksEditor(container, options){
	$('<textarea data-text-field="remarks" name="remarks" style="width:150px;height:60px"/>').appendTo(container);
	$('<span class="k-invalid-msg" data-for="Enter Remarks"></span>').appendTo(container);
}

function chequeBounceDataBound(e){
	
	var data = this.dataSource.view(),row;
	var grid = $("#grid").data("kendoGrid");
    for (var i = 0; i < data.length; i++) {
    	var currentUid = data[i].uid;
        row = this.tbody.children("tr[data-uid='" + data[i].uid + "']");
        
        var chequeStatus = data[i].status;
         
        if (chequeStatus=='Approved') {
        	
        	var currenRow = grid.table.find("tr[data-uid='" + currentUid+ "']");
			var editButton = $(currenRow).find(".k-grid-edit");
			var deleteButton = $(currenRow).find(".k-grid-delete");
			var approveButton = $(currenRow).find(".k-grid-Approve");
			editButton.hide();
			deleteButton.hide();
			approveButton.hide();
        } 
    }
}

function chequebounceDetailsApprove(e){
	
	var widget = $("#grid").data("kendoGrid");
    var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
    
    var r = confirm("Are you sure to approve this cheque details?");
	 if(r == true){
		 $('tr[aria-selected="true"]').find('td:nth-child(19)').html("");
			$('tr[aria-selected="true"]').find('td:nth-child(19)').html("<img src='./resources/images/progress.gif' width='100px' height='25px' />");
    $.ajax
	({
		type : "POST",
		url : "./chequeBounce/changeStatus/" +dataItem.id,
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

var setApCode="";	
var flagTransactionCode="";
function chequeBounceEvent(e)
{
	 /* $('input[name="paymentAmount"]').prop("readonly",true);
	 $('#paymentCalcLinesPopUp').hide(); */
	 $("#accountId").data("kendoComboBox").enable(false);
	 $("input[name=chequeGivenDate]").data("kendoDatePicker").enable(false);
	 $("input[name=chequeAmount]").data("kendoNumericTextBox").enable(false);
	
	/***************************  to remove the id from pop up  **********************/
	$('div[data-container-for="chequeBounceId"]').remove();
	$('label[for="chequeBounceId"]').closest('.k-edit-label').remove();
	
	$(".k-edit-field").each(function () {
		$(this).find("#temPID").parent().remove();  
   	});
   	
   	$('div[data-container-for="personName"]').hide();
	$('label[for="personName"]').closest('.k-edit-label').hide();
	
	$('div[data-container-for="property_No"]').remove();
	$('label[for="property_No"]').closest('.k-edit-label').remove();
	
   	$('div[data-container-for="accountNo"]').hide();
	$('label[for="accountNo"]').closest('.k-edit-label').hide();
	
	$('div[data-container-for="status"]').remove();
	$('label[for="status"]').closest('.k-edit-label').remove();
	
	$('div[data-container-for="previousLateAmount"]').remove();
	$('label[for="previousLateAmount"]').closest('.k-edit-label').remove();
	
	$('div[data-container-for="amountValid"]').remove();
	$('label[for="amountValid"]').closest('.k-edit-label').remove();
	
	/************************* Button Alerts *************************/
	if (e.model.isNew()) 
    {
		//securityCheckForActions("./Collections/Adjustments/createButton");
		flagTransactionCode=true;
		setApCode = $('input[name="chequeBounceId"]').val();
		$(".k-window-title").text("Add New Cheque Bounce Details");
		$(".k-grid-update").text("Save");		
    }
	else{
		//securityCheckForActions("./Collections/Adjustments/updateButton");
		
		var gview = $("#grid").data("kendoGrid");
	    var selectedItem = gview.dataItem(gview.select());
	    var adjustmentStatus = selectedItem.status;
		if(adjustmentStatus!="Created"){
			$('input[name="adjustmentAmount"]').prop("readonly",true);
			var grid = $("#grid").data("kendoGrid");
			grid.cancelChanges();
			alert("Once cheque bounce details are "+adjustmentStatus+" you can't edit cheque bounce details");
		}else{
			$(".k-window-title").text("Edit Cheque Bounce Details");
		}
		flagTransactionCode=false;
	}
	
	$(".k-grid-update").click(function() {

		//e.model.set("receiptNo", receiptNo);
	    e.model.set("chequeAmount", paymentAmount);
	    e.model.set("accountNo", accNo);
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
				$("#alertsBox").html("Can't Delete Cheque Details, Child Record Found");
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
						"Bounced cheque details created successfully");
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
						"Bounced cheque details deleted successfully");
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
						"Bounced cheque details updated successfully");
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

	 $("#grid").on("click",".k-grid-chequeBounceTemplatesDetailsExport", function(e) {
		  window.open("./chequeBounceTemplate/chequeBounceTemplatesDetailsExport");
	});
	//register custom validation rules

	(function ($, kendo) 
		{  
	    $.extend(true, kendo.ui.validator, 
	    {
	         rules: 
	         { // custom rules
	        	receiptNoRequired : function(input, params){
                     if (input.attr("name") == "receiptNo")
                     {
                      return $.trim(input.val()) !== "";
                     }
                     return true;
                    },
                    receiptNoLengthValidation : function (input, params) 
                    {         
                        if (input.filter("[name='receiptNo']").length && input.val() != "") 
                        {
                       	 return /^[a-zA-Z0-9]{1,45}$/.test(input.val());
                        }        
                        return true;
                    }, 
                    chequeNoRequired : function(input, params){
                        if (input.attr("name") == "chequeNo")
                        {
                         return $.trim(input.val()) !== "";
                        }
                        return true;
                       },
                     chequeNoLengthValidation : function (input, params) 
                       {         
                           if (input.filter("[name='chequeNo']").length && input.val() != "") 
                           {
                          	 return /^[a-zA-Z0-9]{1,45}$/.test(input.val());
                           }        
                           return true;
                       },
                       bankNameRequired : function(input, params){
                         if (input.attr("name") == "bankName")
                            {
                              return $.trim(input.val()) !== "";
                              }
                              return true;
                             },
                        chequeGivenDateRequired : function(input, params){
                           if (input.attr("name") == "chequeGivenDate")
                              {
                             	return $.trim(input.val()) !== "";
                               }
                                return true;
                            },
                         bouncedDateRequired : function(input, params){
                            if (input.attr("name") == "bouncedDate")
                               {
                                 return $.trim(input.val()) !== "";
                                }
                                 return true;
                             },
                          chequeAmountRequired : function(input, params){
                             if (input.attr("name") == "chequeAmount")
                                {
                                  return $.trim(input.val()) !== "";
                                 }
                                return true;
                           },
                    penalityAmountRequired : function(input,params){
			    	   if (input.attr("name") == "penalityAmount")
                       {
                        return $.trim(input.val()) !== "";
                       }
                       return true;
			       } ,
			    chequeAmountLengthValidation: function (input, params) 
		             {         
		                 if (input.filter("[name='chequeAmount']").length && input.val() != "") 
		                 {
		                	 return /^[0-9]{1,13}$/.test(input.val());
		                 }        
		                 return true;
		             }, 
		           penalityAmountLengthValidation: function (input, params) 
		             {         
		                 if (input.filter("[name=' penalityAmount']").length && input.val() != "") 
		                 {
		                	 return /^[0-9]{1,13}$/.test(input.val());
		                 }        
		                 return true;
		             }, 
		             bankChargesRequired : function(input,params){
				    	   if (input.attr("name") == "bankCharges")
	                       {
	                        return $.trim(input.val()) !== "";
	                       }
	                       return true;
				       } ,
				       bankChargesLengthValidation: function (input, params) 
			             {         
			                 if (input.filter("[name='bankCharges']").length && input.val() != "") 
			                 {
			                	 return /^[0-9]{1,13}$/.test(input.val());
			                 }        
			                 return true;
			             }, 
		             
	            },
	         messages: 
	         {
				//custom rules messages
				receiptNoRequired:"Receipt number is required", 
				receiptNoLengthValidation:"Receipt number not allowed special characters and max 45 characters", 
				chequeNoRequired:"Cheque number is required",
				chequeNoLengthValidation:"cheque number not allowed special characters and max 45 characters",
				bankNameRequired:"Bank name is required",
				chequeGivenDateRequired:"Cheque date is required",
				bouncedDateRequired:"Cheque bounced date is required",
				chequeAmountRequired:"Cheque amount is required",
				penalityAmountRequired:"Cheque penality amount is required",
				chequeAmountLengthValidation:"Cheque amount max 13 digit number",
				penalityAmountLengthValidation:"cheque penality amount max 13 digit number",
				bankChargesRequired : "Bank charges is required",
				bankChargesLengthValidation : "Bank charges max length 13 digit"
			 }
	    });
	    
	})(jQuery, kendo);
	 //End Of Validation
	 
</script>
