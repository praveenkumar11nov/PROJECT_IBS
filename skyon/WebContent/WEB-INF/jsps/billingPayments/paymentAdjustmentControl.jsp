<%@include file="/common/taglibs.jsp"%>

<c:url value="/paymentAdjustmentControl/paymentAdjustmentCreate" var="createUrl" />
<c:url value="/paymentAdjustmentControl/paymentAdjustmentRead" var="readUrl" />
<c:url value="/paymentAdjustmentControl/paymentAdjustmentDestroy" var="destroyUrl" />
<c:url value="/paymentAdjustmentControl/paymentAdjustmentUpdate" var="updateUrl" />
<c:url value="/paymentAdjustmentControl/filter" var="commonFilterForAdjustmentsUrl" />

<c:url value="/paymentAdjustmentControl/adjustmentCalcLineRead" var="adjustmentCalcLineReadUrl" />
<c:url value="/paymentAdjustmentControl/adjustmentCalcLineCreate" var="adjustmentCalcLineCreateUrl" />
<c:url value="/paymentAdjustmentControl/adjustmentCalcLineUpdate" var="adjustmentCalcLineUpdateUrl" />
<c:url value="/paymentAdjustmentControl/adjustmentCalcLineDestroy" var="adjustmentCalcLineDestroy" />
<c:url value="/paymentAdjustmentControl/adjustmentCalcLine/filter" var="commonFilterForPaymentSegmentsUrl" />
<c:url value="/paymentAdjustmentControl/getAllPaidAccountNumbers" var="accountNumberAutocomplete" />

<c:url value="/paymentAdjustmentControl/getTransactionCodes" var="transactionCodeUrl" />
<c:url value="/paymentAdjustmentControl/readServiceTypes" var="serviceTypeList" />
<c:url value="/common/getAllChecks" var="allChecksUrl" />

<kendo:grid name="grid" change="onChangeAdjustments" pageable="true" resizable="true" edit="paymentAdjustmentEvent" detailTemplate="paymentAdjustmentTemplate" sortable="true" reorderable="true" selectable="true" scrollable="true" filterable="false" groupable="true">

    <kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" refresh="true" info="true" previousNext="true">
		<kendo:grid-pageable-messages itemsPerPage="Adjustments per page" empty="No adjustment to display" refresh="Refresh all the adjustments" 
			display="{0} - {1} of {2} New adjustments" first="Go to the first page of adjustments" last="Go to the last page of adjustments" next="Go to the next page of adjustments"
			previous="Go to the previous page of adjustments"/>
	</kendo:grid-pageable>
	<kendo:grid-filterable extra="false">
		<kendo:grid-filterable-operators>
			<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains" />
				<kendo:grid-filterable-operators-date eq="Is equal to" gt="Is after" lt="Is before"/>
				<kendo:grid-filterable-operators-number eq="Is equal to" gt="Is greather than" gte="IS greather than and equal to" lt="Is less than" lte="Is less than and equal to" neq="Is not equal to"/>
		</kendo:grid-filterable-operators> 
	</kendo:grid-filterable>
	<kendo:grid-editable mode="popup"/>
		<kendo:grid-toolbar>
		      <kendo:grid-toolbarItem name="create" text="Add Service Details" />
		      <kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterPayments()>Clear Filter</a>"/>
		      <%-- <kendo:grid-toolbarItem name="approveAdjustment" text="Approve Adjustment"/>
		      <kendo:grid-toolbarItem name="postAdjustment" text="Post Adjustment"/> --%>
	    </kendo:grid-toolbar>
	<kendo:grid-columns>
	    
	    <kendo:grid-column title="pacId" field="pacId" width="10px" hidden="true" filterable="false" sortable="false" />
	    
	    <kendo:grid-column title="Account&nbsp;Number&nbsp;*" field="accountNo" width="140px" filterable="true">
	    </kendo:grid-column>
	    
	    <kendo:grid-column title="Account&nbsp;Number&nbsp;*" field="accountId" hidden="true" width="140px" filterable="true" editor="accountNumberEditor">
	    </kendo:grid-column>
	    
	    <kendo:grid-column title="Service&nbsp;Type&nbsp;*" field="typeOfService" width="115px" filterable="true" editor="serviceTypeEditor">
	    <kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function ledgerStatusFilter(element) {
								element.kendoAutoComplete({
											placeholder : "Enter Service Type",
											dataSource : {
												transport : {
													read : "${commonFilterForAdjustmentsUrl}/typeOfService"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	    </kendo:grid-column>
	    
	    <kendo:grid-column title="Created&nbsp;Date&nbsp;*" field="pacDate" format="{0:dd/MM/yyyy}" width="130px" filterable="true">
	    </kendo:grid-column>
	    
	    <kendo:grid-column title="Status" field="status" width="90px" filterable="true">
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

		<kendo:grid-column title="&nbsp;" width="100px">
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
				template="<a href='\\\#' id='tempId' class='k-button k-button-icontext btn-destroy k-grid-Active#=data.pacId#'>#= data.status == 'Created' ? 'Approved' : 'Posted' #</a>"
				width="80px" />
				
				<kendo:grid-column title="" template='<button id="buttonId" class="k-button" onclick="return parentGridUp(\'#=uid#\')"><img src="./resources/images/adjustmentControl/upArrow.jpg" alt="Up"></button><button class="k-button k-button-icontext btn-destroy" onclick="return parentGridDown(\'#=uid#\')"><img src="./resources/images/adjustmentControl/downArrow.jpg" alt="Down"></button>' width="80px" />
		
	</kendo:grid-columns>

	<kendo:dataSource pageSize="20" requestEnd="onRequestEnd">
		<kendo:dataSource-transport>
			<kendo:dataSource-transport-create url="${createUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json" />
			<kendo:dataSource-transport-update url="${updateUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-destroy url="${destroyUrl}/" dataType="json" type="GET" contentType="application/json" />
		</kendo:dataSource-transport>

		<kendo:dataSource-schema>
			<kendo:dataSource-schema-model id="pacId">
				<kendo:dataSource-schema-model-fields>
					<kendo:dataSource-schema-model-field name="pacId" type="number"/>
					
					<kendo:dataSource-schema-model-field name="accountId" type="number" defaultValue="">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="pacDate" type="date">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="typeOfService" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="accountNo" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="pacLedger" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="status" type="string">
					</kendo:dataSource-schema-model-field>
					
				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>

	</kendo:dataSource>

</kendo:grid>

<kendo:grid-detailTemplate id="paymentAdjustmentTemplate">
		<kendo:tabStrip name="tabStrip_#=pacId#">
		<kendo:tabStrip-animation>
			</kendo:tabStrip-animation>
			<kendo:tabStrip-items>
			
 			<kendo:tabStrip-item selected="true" text="Adjustment Component">
                <kendo:tabStrip-item-content>
                    <div class='wethear' style='width: 50%'>
						    <br/>
							<kendo:grid name="calcLines_#=pacId#" pageable="true"
								resizable="true" sortable="true" reorderable="true"
								selectable="true" scrollable="true" edit="calcLinesEvent" editable="true" >
								<kendo:grid-pageable pageSize="10"></kendo:grid-pageable>
								<kendo:grid-filterable extra="false">
			                    <kendo:grid-filterable-operators>
				                    <kendo:grid-filterable-operators-string eq="Is equal to" />
			                    </kendo:grid-filterable-operators>
		                        </kendo:grid-filterable>
								<kendo:grid-editable mode="popup"  confirmation="Are sure you want to delete this item ?"/>
						       <kendo:grid-toolbar >
						            <kendo:grid-toolbarItem name="create" text="Add Component" />
						        </kendo:grid-toolbar> 
        						<kendo:grid-columns>
        						    <kendo:grid-column title="adjComId" field="adjComId" hidden="true" width="100px">
									</kendo:grid-column> 
									
									<kendo:grid-column title="pacId" field="pacId" hidden="true" width="100px">
									</kendo:grid-column>
									
									<%-- <kendo:grid-column title="Service&nbsp;Type&nbsp;*"  field="typeOfService" hidden="true" filterable="true" width="100px" editor="dropDownChecksEditorForServiceType">
									</kendo:grid-column> --%>
									
									<kendo:grid-column title="Transaction&nbsp;Name&nbsp;*" field="transactionName" filterable="false" width="150px">
						    		</kendo:grid-column>
			
									<kendo:grid-column title="Transaction&nbsp;Name&nbsp;*" field="transactionCode" filterable="false" hidden="true" width="130px" editor="transactionCodeEditor">
									</kendo:grid-column>
									
        							<kendo:grid-column title="&nbsp;" width="110px" >
							            <kendo:grid-column-command>
							            	<kendo:grid-column-commandItem name="edit"/>
							            	<kendo:grid-column-commandItem name="destroy"/>
							            </kendo:grid-column-command>
							        </kendo:grid-column>
							        
							        <kendo:grid-column title="" template='<button id="childButtonId" class="k-button" onclick="return childGridUp(\'#=uid#\')"><img src="./resources/images/adjustmentControl/upArrow.jpg" alt="Up"></button><button class="k-button k-button-icontext btn-destroy" onclick="return childGridDown(\'#=uid#\')"><img src="./resources/images/adjustmentControl/downArrow.jpg" alt="Down"></button>' width="80px" />
							   						            
        						</kendo:grid-columns>
        						
        						  <kendo:dataSource requestEnd="calcLineOnRequestEnd" >
						            <kendo:dataSource-transport>
						            <kendo:dataSource-transport-read url="${adjustmentCalcLineReadUrl}/#=pacId#" dataType="json" type="POST" contentType="application/json"/>
						            <kendo:dataSource-transport-create url="${adjustmentCalcLineCreateUrl}/#=pacId#" dataType="json" type="GET" contentType="application/json" />
						            <kendo:dataSource-transport-update url="${adjustmentCalcLineUpdateUrl}/#=pacId#" dataType="json" type="GET" contentType="application/json" />
						            <kendo:dataSource-transport-destroy url="${adjustmentCalcLineDestroy}" dataType="json" type="GET" contentType="application/json" />
						            </kendo:dataSource-transport>
						            
						            <kendo:dataSource-schema>
						                <kendo:dataSource-schema-model id="adjComId">
						                    <kendo:dataSource-schema-model-fields>
						                    
						                    <kendo:dataSource-schema-model-field name="adjComId" type="number">
											<kendo:dataSource-schema-model-field-validation  />
											</kendo:dataSource-schema-model-field>
											
											 <kendo:dataSource-schema-model-field name="pacId" type="number">
											<kendo:dataSource-schema-model-field-validation  />
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="typeOfService" type="String">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="transactionCode" type="String">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="transactionName" type="string">
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
<script>

var adjustmentStatus="";

var SelectedRowId = "";
var selectedServiceType="";
var res="";
function onChangeAdjustments(arg) {
	 var gview = $("#grid").data("kendoGrid");
	 var selectedItem = gview.dataItem(gview.select());
	 SelectedRowId = selectedItem.pacId;
	 selectedServiceType = selectedItem.typeOfService;    
}

function parentGridUp(uid) {
	  var grid = $("#grid").data("kendoGrid");
	  var dataItem = grid.dataSource.getByUid(uid);
	  var index = grid.dataSource.indexOf(dataItem);
	  var newIndex = Math.max(0, index - 1);

	  if (newIndex != index) {
	    grid.dataSource.remove(dataItem);
	    grid.dataSource.insert(newIndex, dataItem);
	  }

	  return false;
	}

	function parentGridDown(uid) {
	  var grid = $("#grid").data("kendoGrid");
	  var dataItem = grid.dataSource.getByUid(uid);
	  var index = grid.dataSource.indexOf(dataItem);
	  var newIndex = Math.min(grid.dataSource.total() - 1, index + 1);

	  if (newIndex != index) {
	    grid.dataSource.remove(dataItem);
	    grid.dataSource.insert(newIndex, dataItem);
	  }

	  return false;
	}
	
	function childGridUp(uid) {
		  var grid = $("#calcLines_"+SelectedRowId).data("kendoGrid");
		  var dataItem = grid.dataSource.getByUid(uid);
		  var index = grid.dataSource.indexOf(dataItem);
		  var newIndex = Math.max(0, index - 1);

		  if (newIndex != index) {
		    grid.dataSource.remove(dataItem);
		    grid.dataSource.insert(newIndex, dataItem);
		  }

		  return false;
		}

		function childGridDown(uid) {
		  var grid = $("#calcLines_"+SelectedRowId).data("kendoGrid");
		  var dataItem = grid.dataSource.getByUid(uid);
		  var index = grid.dataSource.indexOf(dataItem);
		  var newIndex = Math.min(grid.dataSource.total() - 1, index + 1);

		  if (newIndex != index) {
		    grid.dataSource.remove(dataItem);
		    grid.dataSource.insert(newIndex, dataItem);
		  }

		  return false;
		}

function calcLinesEvent(e)
{
	 $('div[data-container-for="adjComId"]').remove();
	 $('label[for="adjComId"]').closest('.k-edit-label').remove();  
	 
	 $(".k-edit-field").each(function () {
			$(this).find("#childButtonId").parent().remove();  
	   	});
	 
	 $('div[data-container-for="pacId"]').remove();
	 $('label[for="pacId"]').closest('.k-edit-label').remove(); 
	 
	 $('div[data-container-for="transactionName"]').remove();
	 $('label[for="transactionName"]').closest('.k-edit-label').remove(); 
	 
		if (e.model.isNew()) 
	    {
			
			$(".k-window-title").text("Add New Component Details");
			$(".k-grid-update").text("Save");
			
	    }
		else{
			var gview = $("#grid").data("kendoGrid");
		    var selectedItem = gview.dataItem(gview.select());
		    adjustmentStatus = selectedItem.status;
			if(adjustmentStatus!="Created"){
				$('input[name="amount"]').prop("readonly",true);
			}
			setApCode = $('input[name="adjComId"]').val();
			$(".k-window-title").text("Edit Component Details");
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
			placeholder: "Enter Account Number",
			headerTemplate : '<div class="dropdown-header">'
				+ '<span class="k-widget k-header">Photo</span>'
				+ '<span class="k-widget k-header">Contact info</span>'
				+ '</div>',
			template : '<table><tr><td rowspan=2><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.personId#'/>" width=40 height=55 alt=\"No Image to Display\" /></span></td>'
				+ '<td align="left"><span class="k-state-default"><b>#: data.personName #</b></span><br>'
				+ '<span class="k-state-default"><i>#: data.accountNo #</i></span><br>'
				+ '<span class="k-state-default"><i>#: data.personType #</i></span></td></tr></table>',
			change : accountIdFunction,
			dataSource: {
				transport: {
					read: "${accountNumberAutocomplete}"
				}
			}
		});
	  $('<span class="k-invalid-msg" data-for="accountNumberEE"></span>').appendTo(container);
	}
	
function serviceTypeEditor(container, options) 
{
		$('<select data-text-field="typeOfService" name="typeOfServiceEE" data-value-field="typeOfService" required ="true" validationmessage="Service type is required" id="typeOfService" data-bind="value:' + options.field + '"/>')
             .appendTo(container).kendoDropDownList
             ({
            	 placeholder: "Select Service",
            	 cascadeFrom: "accountId",
            	 optionLabel : "Select",
                 autoBind: false,
                 dataSource: {  
                     transport:{
                         read: "${serviceTypeList}"
                     }
                 }
             });
		$('<span class="k-invalid-msg" data-for="typeOfServiceEE"></span>').appendTo(container);
}
	
	function accountIdFunction(e){
		  
	}

function transactionCodeEditor(container, options) {
	$('<input name="transactionCode" data-text-field="transactionName" id="transactionId" data-value-field="transactionCode" validationmessage="Transaction code is required" data-bind="value:' + options.field + '" required="required"/>')
			.appendTo(container).kendoDropDownList({
				autoBind : false,
				optionLabel : "Select",				
				dataSource : {
					transport : {
						read : "${transactionCodeUrl}/"+selectedServiceType,
					}
				}
				
			});
	 $('<span class="k-invalid-msg" data-for="transactionCode"></span>').appendTo(container); 
}

var setApCode="";	
var flagTransactionCode="";
function paymentAdjustmentEvent(e)
{
	 /* $('input[name="paymentAmount"]').prop("readonly",true);
	 $('#paymentCalcLinesPopUp').hide(); */
	
	/***************************  to remove the id from pop up  **********************/
	$('div[data-container-for="pacId"]').remove();
	$('label[for="pacId"]').closest('.k-edit-label').remove();
	
	$(".k-edit-field").each(function () {
		$(this).find("#tempId").parent().remove();  
   	});
   	
   	$(".k-edit-field").each(function () {
		$(this).find("#buttonId").parent().remove();  
   	});
	
   	$('div[data-container-for="accountNo"]').hide();
	$('label[for="accountNo"]').closest('.k-edit-label').hide();
	
	$('div[data-container-for="pacDate"]').hide();
	$('label[for="pacDate"]').closest('.k-edit-label').hide();
	
	$('div[data-container-for="jvDate"]').hide();
	$('label[for="jvDate"]').closest('.k-edit-label').hide();
	
	$('div[data-container-for="pacNo"]').remove();
	$('label[for="pacNo"]').closest('.k-edit-label').remove();
	
	$('div[data-container-for="pacAmount"]').remove();
	$('label[for="pacAmount"]').closest('.k-edit-label').remove();
	
	$('div[data-container-for="postedToGl"]').remove();
	$('label[for="postedToGl"]').closest('.k-edit-label').remove();
	
	$('div[data-container-for="postedGlDate"]').remove();
	$('label[for="postedGlDate"]').closest('.k-edit-label').remove();
	
	$('div[data-container-for="status"]').remove();
	$('label[for="status"]').closest('.k-edit-label').remove();
	
	/************************* Button Alerts *************************/
	if (e.model.isNew()) 
    {
		//securityCheckForActions("./CustomerCare/DepartmentAccessSettings/createButton");
		flagTransactionCode=true;
		setApCode = $('input[name="pacId"]').val();
		$(".k-window-title").text("Add New Adjustment Control Details");
		$(".k-grid-update").text("Save");		
    }
	else{
		//securityCheckForActions("./CustomerCare/HelpTopic/editButton");
		var gview = $("#grid").data("kendoGrid");
	    var selectedItem = gview.dataItem(gview.select());
	    adjustmentStatus = selectedItem.status;
		if(adjustmentStatus!="Created"){
			$('input[name="pacAmount"]').prop("readonly",true);
		}
		flagTransactionCode=false;
		$(".k-window-title").text("Edit Adjustment Control Details");
	}
}

$("#grid").on("click", ".k-grid-approveAdjustment", function(e) {

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
});

$("#grid").on("click", ".k-grid-postAdjustment", function(e) {

	$.ajax
	({
		type : "POST",
		url : "./paymentAdjustments/postpacLedger",
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
});

$("#grid").on("click", "#tempId", function(e) {
	  
	  var button = $(this), enable = button.text() == "Approved";
	  var widget = $("#grid").data("kendoGrid");
	  var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
						if (enable) 
						{
							$.ajax
							({
								type : "POST",
								url : "./paymentAdjustmentControl/setAdjustmentControlStatus/" +dataItem.id+ "/activate",
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
						else 
						{
							$.ajax
							({
								type : "POST",
								url : "./paymentAdjustmentControl/setAdjustmentControlStatus/" + dataItem.id + "/deactivate",
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
                });
		
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
			else if (e.type == "create") {
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Service details is created successfully");
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
						"Service details is deleted successfully");
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
						"Service details is updated successfully");
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
				$("#alertsBox").html("Service component created successfully");
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
				$("#alertsBox").html("Service component updated successfully");
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
				$("#alertsBox").html("Service component deleted successfully");
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
	
	
	//register custom validation rules

	(function ($, kendo) 
		{  
	    $.extend(true, kendo.ui.validator, 
	    {
	         rules: 
	         { // custom rules
                    ledgerTypeRequired : function(input, params){
                        if (input.attr("name") == "typeOfService")
                        {
                         return $.trim(input.val()) !== "";
                        }
                        return true;
                       },
                                    
	            },
	         messages: 
	         {
				//custom rules messages
				ledgerTypeRequired:"Service type is required",
			 }
	    });
	    
	})(jQuery, kendo);
	 //End Of Validation
</script>

<style>
.k-grid tbody button.k-button {
    min-width: 30px;
}
</style>
