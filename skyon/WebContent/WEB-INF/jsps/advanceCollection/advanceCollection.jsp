<%@include file="/common/taglibs.jsp"%>

<c:url value="/advanceCollection/advanceCollectionCreate" var="createUrl" />
<c:url value="/advanceCollection/advanceCollectionRead" var="readUrl" />
<c:url value="/advanceCollection/advanceCollectionDestroy" var="destroyUrl" />
<c:url value="/advanceCollection/advanceCollectionUpdate" var="updateUrl" />
<c:url value="/advanceCollection/filter" var="commonFilterForAdjustmentsUrl" />
<c:url value="/advanceCollection/getAllAccountNumbers" var="accountNumberAutocomplete" />

<c:url value="/advancePayment/advancePaymentRead" var="advancePaymentReadUrl" />
<c:url value="/advancePayment/advancePaymentCreate" var="advancePaymentCreateUrl" />
<c:url value="/advancePayment/advancePaymentUpdate" var="advancePaymentUpdateUrl" />
<c:url value="/advancePayment/advancePaymentDestroy" var="advancePaymentDestroy" />
<c:url value="/advancePayment/advancePayment/filter" var="commonFilterForPaymentSegmentsUrl" />
<c:url value="/advancePayment/servicType" var="servicTypeUrl" />

<c:url value="/clearedPayment/clearedPaymentCreate" var="advanceCollectionCreateUrl" />
<c:url value="/clearedPayment/clearedPaymentRead" var="advanceCollectionReadUrl" />
<c:url value="/clearedPayment/clearedPaymentDestroy" var="advanceCollectionDestroyUrl" />
<c:url value="/clearedPayment/advanceCollectionUpdate" var="advanceCollectionUpdateUrl" />
<c:url value="/advanceCollection/commonFilterForAccountNumbersUrl" var="commonFilterForAccountNumbersUrl" />
<c:url value="/advanceCollection/commonFilterForPropertyNoUrl" var="commonFilterForPropertyNoUrl" />
<c:url value="/advanceCollection/commonFilterForTransactionNameUrl" var="commonFilterForTransactionNameUrl" />
<c:url value="/advanceCollection/getPersonListForFileter" var="personNamesFilterUrl" />
<c:url value="/common/getAllChecks" var="allChecksUrl" />

<kendo:grid name="advanceCollectionGrid" change="onChangeAdvanceCollection" pageable="true" resizable="true" edit="advanceCollectionEvent" detailTemplate="advanceCollectionTemplate" sortable="true" reorderable="true" selectable="true" scrollable="true" filterable="false" groupable="true">
    <kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" refresh="true" info="true" previousNext="true">
		<kendo:grid-pageable-messages itemsPerPage="Deposits per page" empty="No deposit to display" refresh="Refresh all the deposits" 
			display="{0} - {1} of {2} New Deposits" first="Go to the first page of deposits" last="Go to the last page of deposits" next="Go to the next page of deposits"
			previous="Go to the previous page of deposits"/>
	</kendo:grid-pageable>
	<kendo:grid-filterable extra="false">
		<kendo:grid-filterable-operators>
			<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains" />
				<kendo:grid-filterable-operators-date eq="Is equal to" gt="Is after" lt="Is before" gte="Is after or equals to" lte="Is before or equals to" neq="Is not equal to"/>
				<kendo:grid-filterable-operators-number eq="Is equal to" gt="Is greather than" gte="IS greather than and equal to" lt="Is less than" lte="Is less than and equal to" neq="Is not equal to"/>
		</kendo:grid-filterable-operators> 
	</kendo:grid-filterable>
	<kendo:grid-editable mode="popup"/>
		<kendo:grid-toolbar>
		     <%--  <kendo:grid-toolbarItem name="create" text="New Advance Collection Details" /> --%>
		      <kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterPayments()>Clear Filter</a>"/>
		    <%--   <kendo:grid-toolbarItem name="approveAdjustment" text="Approve Adjustment"/> --%>
		     <%--  <kendo:grid-toolbarItem name="postAdjustment" text="Post Adjustment"/> --%>
	    </kendo:grid-toolbar>
	<kendo:grid-columns>
	    
	    <kendo:grid-column title="deposits id" field="advCollId" width="10px" hidden="true" filterable="false" sortable="false" />
	    
	    <kendo:grid-column title="Account&nbsp;Number&nbsp;" field="accountNo" width="90px" filterable="true">
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
	    
	    <kendo:grid-column title="Account&nbsp;Number&nbsp;" field="accountId" hidden="true" width="90px" filterable="true" editor="accountNumberEditor"/>
	    
	    <kendo:grid-column title="Person&nbsp;Name" field="personName"  width="120px" filterable="false">
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
	    
	    <kendo:grid-column title="property&nbsp;Number" field="propertyNo"  width="100px" filterable="true">
	    <kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function ledgerTypeFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Property Number",
									dataSource : {
										transport : {
											read : "${commonFilterForPropertyNoUrl}"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	    </kendo:grid-column>
	    
	    <kendo:grid-column title="Transaction&nbsp;Name" field="transactionName"  width="100px" filterable="true">
	    <kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function ledgerTypeFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Transaction Name",
									dataSource : {
										transport : {
											read : "${commonFilterForTransactionNameUrl}"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	    </kendo:grid-column>
	    
	    <kendo:grid-column title="Total&nbsp;Advance&nbsp;Amount" field="totalAmount" width="90px" filterable="true"/>
	    
	    <kendo:grid-column title="Amount&nbsp;Cleared&nbsp;" field="amountCleared" width="90px" filterable="true"/>
	    
	    <kendo:grid-column title="Balance&nbsp;Amount&nbsp;" field="balanceAmount" width="90px" filterable="true"/>
	 
	    <%-- <kendo:grid-column title="Status" field="status" width="90px" filterable="true">
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
	    </kendo:grid-column> --%>

		<%-- <kendo:grid-column title="&nbsp;" width="100px">
			<kendo:grid-column-command>
			    <kendo:grid-column-commandItem name="edit"/>
				<kendo:grid-column-commandItem name="destroy" />
			</kendo:grid-column-command>
		</kendo:grid-column> --%>
				
		  <%-- <kendo:grid-column title=""
				template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Active#=data.elBillId#'>#= data.status == 'Generated' ? 'Approved' : 'Posted' #</a>"
				width="90px" /> --%>
				
	   </kendo:grid-columns>

	<kendo:dataSource pageSize="20" requestEnd="onRequestEnd">
		<kendo:dataSource-transport>
			<kendo:dataSource-transport-create url="${createUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json" />
			<kendo:dataSource-transport-update url="${updateUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-destroy url="${destroyUrl}" dataType="json" type="GET" contentType="application/json" />
		</kendo:dataSource-transport>
<%-- parse="parse" --%>
		<kendo:dataSource-schema>
			<kendo:dataSource-schema-model id="advCollId">
				<kendo:dataSource-schema-model-fields>
					<kendo:dataSource-schema-model-field name="advCollId" type="number"/>
					
					<kendo:dataSource-schema-model-field name="accountId" type="number"/>
					
					<kendo:dataSource-schema-model-field name="personName" type="string"/>
					
					<kendo:dataSource-schema-model-field name="propertyNo" type="string"/>
					
					<kendo:dataSource-schema-model-field name="transactionCode" type="string"/>
					
					<kendo:dataSource-schema-model-field name="transactionName" type="string"/>
					
					<kendo:dataSource-schema-model-field name="totalAmount" type="number"/>
					
					<kendo:dataSource-schema-model-field name="amountCleared" type="number"/>
					
					<kendo:dataSource-schema-model-field name="balanceAmount" type="number"/>
					
					<kendo:dataSource-schema-model-field name="accountNo" type="string"/>
				
					<kendo:dataSource-schema-model-field name="status" type="string"/>
					
				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>

	</kendo:dataSource>

</kendo:grid>

<kendo:grid-detailTemplate id="advanceCollectionTemplate">
		<kendo:tabStrip name="tabStrip_#=advCollId#">
		<kendo:tabStrip-animation>
			</kendo:tabStrip-animation>
			<kendo:tabStrip-items>
			
 			<kendo:tabStrip-item selected="true" text="Advance Payments">
                <kendo:tabStrip-item-content>
                    <div class='wethear' style='width: 100%'>
						    <br/>
							<kendo:grid name="advancePayment_#=advCollId#" pageable="true" resizable="true" sortable="true" reorderable="true"
								selectable="true" scrollable="true" edit="advancePaymentEvent" editable="true" >
								<kendo:grid-pageable pageSize="10"></kendo:grid-pageable>
								<kendo:grid-filterable extra="false">
			                    <kendo:grid-filterable-operators>
				                    <kendo:grid-filterable-operators-string eq="Is equal to" />
			                    </kendo:grid-filterable-operators>
		                        </kendo:grid-filterable>
								<kendo:grid-editable mode="popup"  confirmation="Are sure you want to delete this item ?"/>
<%-- 						       <kendo:grid-toolbar >
						            <kendo:grid-toolbarItem name="create" text="Add Advance Payments" />
						        </kendo:grid-toolbar>  --%>
        						<kendo:grid-columns>
        						    <kendo:grid-column title="Advance payment ID" field="advPayId" hidden="true" width="100px"/> 
									
									<kendo:grid-column title="Advance Collection ID" field="advCollId" hidden="true" width="100px"/> 

									<kendo:grid-column title="Receipt&nbsp;Number" field="receiptNo" filterable="false"  width="110px" />
						    		
									<kendo:grid-column title="Amount" field="amount" format="{0:n2}" width="90px" filterable="false"/> 
									
									<kendo:grid-column title="Advance&nbsp;Payment&nbsp;Date" field="advPaymentDate" format="{0:dd/MM/yyyy}" width="120px" filterable="false"/>
									
        							<%-- <kendo:grid-column title="&nbsp;" width="150px" >
							            <kendo:grid-column-command>
							            	<kendo:grid-column-commandItem name="edit"/>
							            	<kendo:grid-column-commandItem name="destroy"/>
							            </kendo:grid-column-command>
							        </kendo:grid-column> --%>
							   						            
        						</kendo:grid-columns>
        						
        						  <kendo:dataSource requestEnd="calcLineOnRequestEnd" >
						            <kendo:dataSource-transport>
						            <kendo:dataSource-transport-read url="${advancePaymentReadUrl}/#=advCollId#" dataType="json" type="POST" contentType="application/json"/>
						            <kendo:dataSource-transport-create url="${advancePaymentCreateUrl}/#=advCollId#" dataType="json" type="GET" contentType="application/json" />
						            <kendo:dataSource-transport-update url="${advancePaymentUpdateUrl}/#=advCollId#" dataType="json" type="GET" contentType="application/json" />
						            <kendo:dataSource-transport-destroy url="${advancePaymentDestroy}" dataType="json" type="GET" contentType="application/json" />
						            </kendo:dataSource-transport>
						            
						            <kendo:dataSource-schema>
						                <kendo:dataSource-schema-model id="advPayId">
						                    <kendo:dataSource-schema-model-fields>
						                    
						                    <kendo:dataSource-schema-model-field name="advPayId" type="number"/>
											
											<kendo:dataSource-schema-model-field name="advCollId" type="number"/>
											
											<kendo:dataSource-schema-model-field name="receiptNo" type="String"/>
											
											<kendo:dataSource-schema-model-field name="advPaymentDate" type="date"/>
											
											<kendo:dataSource-schema-model-field name="amount" type="number"/>
						                    	
						                    </kendo:dataSource-schema-model-fields>
						                 </kendo:dataSource-schema-model>
						             </kendo:dataSource-schema>
						          </kendo:dataSource>
        				</kendo:grid>		
                    </div>
                </kendo:tabStrip-item-content>
            </kendo:tabStrip-item>
            
             	<kendo:tabStrip-item  text="Cleared Payment">
                <kendo:tabStrip-item-content>
                    <div class='wethear' style='width: 100%'>
						    <br/>
							<kendo:grid name="cleardPayment_#=advCollId#" pageable="true" resizable="true" sortable="true" reorderable="true"
								selectable="true" scrollable="true" edit="cleardPaymentEvent" editable="true" >
								<kendo:grid-pageable pageSize="10"></kendo:grid-pageable>
								<kendo:grid-filterable extra="false">
			                    <kendo:grid-filterable-operators>
				                    <kendo:grid-filterable-operators-string eq="Is equal to" />
			                    </kendo:grid-filterable-operators>
		                        </kendo:grid-filterable>
								<kendo:grid-editable mode="popup"  confirmation="Are sure you want to delete this item ?"/>
						      <%--  <kendo:grid-toolbar >
						            <kendo:grid-toolbarItem name="create" text="Add Cleared Payment" />
						        </kendo:grid-toolbar>  --%>
        						<kendo:grid-columns>
        						    <kendo:grid-column title="Cleared payment ID" field="cpId" hidden="true" width="100px">
									</kendo:grid-column> 
									
									<kendo:grid-column title="Adjustment&nbsp;Number" field="adjId" filterable="false" hidden="true" width="110px" />
									
									<kendo:grid-column title="Adjustment&nbsp;Number" field="adjNo" filterable="false"  width="110px" />
									
									<kendo:grid-column title="Bill&nbsp;Number" field="billId" filterable="false"  width="110px" />
						    		
									<kendo:grid-column title="Amount" field="amount" format="{0:n2}" width="90px" filterable="false"/> 
									
									<kendo:grid-column title="Cleared&nbsp;Payment&nbsp;Date" field="clearedDate" format="{0:dd/MM/yyyy}" width="120px" filterable="false"/>
									
        							<%-- <kendo:grid-column title="&nbsp;" width="150px" >
							            <kendo:grid-column-command>
							            	<kendo:grid-column-commandItem name="edit"/>
							            	<kendo:grid-column-commandItem name="destroy"/>
							            </kendo:grid-column-command>
							        </kendo:grid-column> --%>
							   						            
        						</kendo:grid-columns>
        						
        						  <kendo:dataSource requestEnd="clearedOnRequestEnd" >
						            <kendo:dataSource-transport>
						            <kendo:dataSource-transport-read url="${advanceCollectionReadUrl}/#=advCollId#" dataType="json" type="POST" contentType="application/json"/>
						            <kendo:dataSource-transport-create url="${advanceCollectionCreateUrl}/#=advCollId#" dataType="json" type="GET" contentType="application/json" />
						            <kendo:dataSource-transport-update url="${advanceCollectionUpdateUrl}/#=advCollId#" dataType="json" type="GET" contentType="application/json" />
						            <kendo:dataSource-transport-destroy url="${advanceCollectionDestroyUrl}" dataType="json" type="GET" contentType="application/json" />
						            </kendo:dataSource-transport>

						            <kendo:dataSource-schema>
						                <kendo:dataSource-schema-model id="cpId">
						                    <kendo:dataSource-schema-model-fields>
						                    
						                    <kendo:dataSource-schema-model-field name="cpId" type="number"/>
											
											<kendo:dataSource-schema-model-field name="adjId" type="String"/>
											
											<kendo:dataSource-schema-model-field name="adjNo" type="String"/>
											
											<kendo:dataSource-schema-model-field name="billId" type="String"/>
											
											<kendo:dataSource-schema-model-field name="clearedDate" type="date"/>
											
											<kendo:dataSource-schema-model-field name="amount" type="number"/>
						                    	
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
<div id="alertsBox" title="Alert"></div>
<script>

var adjustmentStatus="";

var SelectedRowId = "";
var res="";
function onChangeAdvanceCollection(arg) {
	 var gview = $("#advanceCollectionGrid").data("kendoGrid");
	 var selectedItem = gview.dataItem(gview.select());
	 SelectedRowId = selectedItem.advCollId;
	 this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
}

function advanceCollectionEvent(e)
{
	 $('div[data-container-for="advPayId"]').remove();
	 $('label[for="advPayId"]').closest('.k-edit-label').remove();  
	 
	 $('div[data-container-for="advCollId"]').remove();
	 $('label[for="advCollId"]').closest('.k-edit-label').remove(); 
	 
	 $('div[data-container-for="advPaymentDate"]').remove();
	 $('label[for="advPaymentDate"]').closest('.k-edit-label').remove();
	 
		if (e.model.isNew()) 
	    {
			$(".k-window-title").text("Add Advance Collection Details");
			$(".k-grid-update").text("Save");
	    }
		else
		{
			$(".k-window-title").text("Edit Advance Collection Details");
		}
}

function advancePaymentEvent(e)
{
	 $('div[data-container-for="advPayId"]').remove();
	 $('label[for="advPayId"]').closest('.k-edit-label').remove();  
	 
	 $('div[data-container-for="advCollId"]').remove();
	 $('label[for="advCollId"]').closest('.k-edit-label').remove();
	 
		if (e.model.isNew()) 
	    {
			$(".k-window-title").text("Add Advance Payment Details");
			$(".k-grid-update").text("Save");
	    }
		else
		{
			$(".k-window-title").text("Edit Advance Payment Details");
		}
}
	
function cleardPaymentEvent(e)
{
	 $('div[data-container-for="cpId"]').remove();
	 $('label[for="cpId"]').closest('.k-edit-label').remove();
	 
	 $('div[data-container-for="adjId"]').remove();
	 $('label[for="adjId"]').closest('.k-edit-label').remove();
	 
		if (e.model.isNew()) 
	    {
			$(".k-window-title").text("Add Advance Payment Details");
			$(".k-grid-update").text("Save");
	    }
		else
		{
			$(".k-window-title").text("Edit Advance Payment Details");
		}
}

function paymentStatusUpdate()
{
	var paymentCollectionId="";
	var gridParameter = $("#advanceCollectionGrid").data("kendoGrid");
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
			$('#advanceCollectionGrid').data('kendoGrid').dataSource.read();
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
	
	$('<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '" name = "receiptNo" id = "receiptNo"/>')
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

function clearFilterPayments()
{
   $("form.k-filter-menu button[type='reset']").slice().trigger("click");
   var gridStoreIssue = $("#advanceCollectionGrid").data("kendoGrid");
   gridStoreIssue.dataSource.read();
   gridStoreIssue.refresh();
}

function paymetsDeleteEvent(){
	var conf = confirm("Are u sure want to delete this payment details?");
	 if(!conf){
	  $('#advanceCollectionGrid').data().kendoGrid.dataSource.read();
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
				+ '<span class="k-state-default"><i>#: data.propertyNo #</i></span><br>'
				+ '<span class="k-state-default"><i>#: data.personType #</i></span></td></tr></table>',
			dataSource: {
				transport: {
					read: "${accountNumberAutocomplete}"
				}
			}
		});
	  $('<span class="k-invalid-msg" data-for="accountNumberEE"></span>').appendTo(container);
	}

$("#advanceCollectionGrid").on("click", "#temPID", function(e) {
	  
	  var button = $(this), enable = button.text() == "Approved";
	  var widget = $("#advanceCollectionGrid").data("kendoGrid");
	  var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
						if (enable) 
						{
							$.ajax
							({
								type : "POST",
								url : "./advanceCollection/setDepositsStatus/" +dataItem.id+ "/activate",
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
									$('#advanceCollectionGrid').data('kendoGrid').dataSource.read();
								}
							});
						} 
						else 
						{
							$.ajax
							({
								type : "POST",
								url : "./advanceCollection/setDepositsStatus/" + dataItem.id + "/deactivate",
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
									$('#advanceCollectionGrid').data('kendoGrid').dataSource.read();
								}
							});
						}
 });

var setApCode="";	
var flagTransactionCode="";
function advanceCollectionEvent(e)
{
	/***************************  to remove the id from pop up  **********************/
	$('div[data-container-for="advCollId"]').remove();
	$('label[for="advCollId"]').closest('.k-edit-label').remove();
	
	$(".k-edit-field").each(function () {
		$(this).find("#temPID").parent().remove();  
   	});
	
   	$('div[data-container-for="accountNo"]').hide();
	$('label[for="accountNo"]').closest('.k-edit-label').hide();
	
	$('div[data-container-for="propertyNo"]').hide();
	$('label[for="propertyNo"]').closest('.k-edit-label').hide();
	
	$('div[data-container-for="totalAmount"]').hide();
	$('label[for="totalAmount"]').closest('.k-edit-label').hide();

	$('div[data-container-for="status"]').remove();
	$('label[for="status"]').closest('.k-edit-label').remove();
	
	/************************* Button Alerts *************************/
	if (e.model.isNew()) 
    {
		$(".k-window-title").text("Add New Deposits Details");
		$(".k-grid-update").text("Save");		
    }
	else
	{
		$(".k-window-title").text("Edit Deposits Details");
	}
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
				var grid = $("#advanceCollectionGrid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			} 
			else if (e.type == "create") {
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Deposit details is created successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});

				var grid = $("#advanceCollectionGrid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			} else if (e.type == "destroy") {
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Deposit details is deleted successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});

				var grid = $("#advanceCollectionGrid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			} else if (e.type == "update") {
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Deposit details is updated successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});

				var grid = $("#advanceCollectionGrid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			}
		}
	}
	function calcLineOnRequestEnd(e)
	 {
		  if (typeof e.response != 'undefined')
			{
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
					return false;
				}
			

		  else if (e.type == "create") 
			{
				$("#alertsBox").html("");
				$("#alertsBox").html("Advance payment details created successfully");
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
				var gridPets = $("#advancePayment_"+SelectedRowId).data("kendoGrid");
				gridPets.dataSource.read();
				gridPets.refresh();
				return false;
			}

			else if (e.type == "update") 
			{
				$("#alertsBox").html("");
				$("#alertsBox").html("Advance payment details updated successfully");
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
				var gridPets = $("#advancePayment_"+SelectedRowId).data("kendoGrid");
				gridPets.dataSource.read();
				gridPets.refresh();
				return false;
			}
				
			else if (e.type == "destroy") 
			{
				$("#alertsBox").html("");
				$("#alertsBox").html("Advance payment details deleted successfully");
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
	
	function clearedOnRequestEnd(e)
	 {
		  if (typeof e.response != 'undefined')
			{
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
					return false;
				}
			

		  else if (e.type == "create") 
			{
				$("#alertsBox").html("");
				$("#alertsBox").html("Advance payment details created successfully");
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
				var gridPets = $("#advancePayment_"+SelectedRowId).data("kendoGrid");
				var grid = $("#advanceCollectionGrid").data("kendoGrid");
				grid.dataSource.read();
				gridPets.dataSource.read();
				gridPets.refresh();
				return false;
			}

			else if (e.type == "update") 
			{
				$("#alertsBox").html("");
				$("#alertsBox").html("Advance payment details updated successfully");
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
				var gridPets = $("#advancePayment_"+SelectedRowId).data("kendoGrid");
				var grid = $("#advanceCollectionGrid").data("kendoGrid");
				grid.dataSource.read();
				gridPets.dataSource.read();
				gridPets.refresh();
				return false;
			}
				
			else if (e.type == "destroy") 
			{
				$("#alertsBox").html("");
				$("#alertsBox").html("Advance payment details deleted successfully");
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
				var grid = $("#advanceCollectionGrid").data("kendoGrid");
				grid.dataSource.read();
		  	  }	
			}
	 }
	(function ($, kendo) 
		{  
	    $.extend(true, kendo.ui.validator, 
	    {
	         rules: 
	         { 
                    /* totalAmountRequired : function(input, params){
                         if (input.attr("name") == "totalAmount")
                            {
                              return $.trim(input.val()) !== "";
                              }
                              return true;
                             }, */
	            },
	         messages: 
	         {
	        	 /* totalAmountRequired:"Amount is required", */
			 }
	    });
	    
	})(jQuery, kendo);
	
</script>
