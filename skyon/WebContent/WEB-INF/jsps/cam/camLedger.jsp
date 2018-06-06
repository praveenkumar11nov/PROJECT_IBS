<%@include file="/common/taglibs.jsp"%>


<c:url value="/camLedgers/camLedgerRead" var="camLedgerReadUrl" />
<c:url value="/camLedgers/camLedgerCreate" var="camLedgerCreateUrl" />
<c:url value="/camLedgers/camLedgerDestroyUrl" var="camLedgerDestroyUrl" />

<c:url value="/camLedgers/subLedgerRead" var="camSubLedgerReadUrl" />
<c:url value="/camLedgers/subLedgerCreate" var="camSubLedgerCreateUrl" />
<c:url value="/camLedgers/subLedgerUpdateUrl" var="camSubLedgerUpdateUrl" />
<c:url value="/camLedgers/subLedgerDestroyUrl" var="camSubLedgerDestroyUrl" />

<c:url value="/common/getAllChecks" var="allChecksUrl" />
<c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />
<c:url value="/person/getAllAttributeValues" var="filterAutoCompleteUrl" /> 
<c:url value="/camLedgers/transactionCodeList" var="transactionCodeListUrl" />
<c:url value="/camLedgers/commonFilterForCAMTransactions" var="commonFilterForCAMTransactionsUrl" />

<!-- Filters Data url's -->	
<c:url value="/camLedgers/filter" var="commonFilterForCAMLedgerUrl" />

 <kendo:grid name="grid" remove="camLedgerDeleteEvent" resizable="true" pageable="true" selectable="true" change="onChangeCamLedger" edit="camLedgerEvent" dataBound="camLedgerDataBound" sortable="true" scrollable="true" 
		groupable="true">
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10"></kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
			<kendo:grid-filterable-operators>
				<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains" />
				<kendo:grid-filterable-operators-date eq="Is equal to" gt="Is after" lt="Is before"/>
				<kendo:grid-filterable-operators-number eq="Is equal to" gt="Is greather than" gte="IS greather than and equal to" lt="Is less than" lte="Is less than and equal to" neq="Is not equal to"/>
			</kendo:grid-filterable-operators>

		</kendo:grid-filterable>
		<kendo:grid-editable mode="popup"/>
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Cam Heads Amount" />
			<kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterLedger()></span> Clear Filter</a>"/>
			<kendo:grid-toolbarItem name="" text="Import Data From Tally" />
		</kendo:grid-toolbar>
		<kendo:grid-columns>
			
			<kendo:grid-column title="Electricty Ledger Id" field="camLedgerid" width="110px" hidden="true"/>
			
			<<kendo:grid-column title="Transaction&nbsp;Date" field="ledgerDate" filterable="true" format="{0:dd/MM/yyyy}" width="120px">
			</kendo:grid-column>
			
			<kendo:grid-column title="Main&nbsp;Head&nbsp;*" field="camHeadProperty" filterable="true" width="120px" editor="dropDownChecksEditor">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function ledgerPeriodFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Main Head",                                            
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${commonFilterForCAMLedgerUrl}/camHeadProperty"
										}
									}
								});    
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="Sub&nbsp;Head&nbsp;*" field="transactionName" width="140px">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function ledgerPeriodFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Transaction Name",                                            
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${commonFilterForCAMTransactionsUrl}"
										}
									}
								});    
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
        	</kendo:grid-column>
        							
        	<kendo:grid-column title="Sub&nbsp;Head&nbsp;*" field="transactionCode" editor="transactionCodeEditor" hidden="true" width="70px">
        	</kendo:grid-column>
        	
        	<kendo:grid-column title="Charges&nbsp;Based&nbsp;On" field="calculationBased" width="140px">
        	<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function ledgerPeriodFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Calculation Based",                                            
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${commonFilterForCAMLedgerUrl}/calculationBased"
										}
									}
								});    
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
        	</kendo:grid-column>
			
			<kendo:grid-column title="Ledger&nbsp;Period&nbsp;*" hidden="true" field="ledgerPeriod" filterable="true" width="120px">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function ledgerPeriodFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Period",                                            
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${commonFilterForCAMLedgerUrl}/ledgerPeriod"
										}
									}
								});    
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="Bill&nbsp;Posted&nbsp;Date" field="postedBillDate" format="{0:dd/MM/yyyy hh:mm tt}" width="120px">
			</kendo:grid-column>
			
			<%-- <kendo:grid-column title="Post&nbsp;Reference&nbsp;*" field="postReference" width="120px" filterable="true">
			<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function postReferenceFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Post Reference",
									dataSource : {
										transport : {
											read : "${commonFilterForCAMLedgerUrl}/postReference"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="Post&nbsp;Type" field="postType" width="100px" filterable="true" editor="dropDownChecksEditor">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function postTypeFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Post Type",
									dataSource : {
										transport : {
											read : "${commonFilterForCAMLedgerUrl}/postType"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column> --%>
			
			<kendo:grid-column title="Posted&nbsp;To&nbsp;Bill&nbsp*" field="postedToBill" width="110px" filterable="true">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function ledgerPeriodFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Posted To Bill",                                            
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${commonFilterForCAMLedgerUrl}/postedToBill"
										}
									}
								});    
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="Credit&nbsp;Amount" field="creditAmount" format="{0:#.00}" width="110px" filterable="true">
			</kendo:grid-column>
			
			<kendo:grid-column title="Debit&nbsp;Amount" field="debitAmount" format="{0:#.00}" width="110px" filterable="true">
			</kendo:grid-column>
			
			<kendo:grid-column title="Balance&nbsp;Amount" field="balance" format="{0:#.00}" width="120px" filterable="true">
			</kendo:grid-column>
			
			<kendo:grid-column title="Head&nbsp;Balance" field="headBalance" format="{0:#.00}" width="120px" filterable="true">
			</kendo:grid-column>
			
			<kendo:grid-column title="Status" field="status" filterable="true" width="100px">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function ledgerPeriodFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Status",                                            
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${commonFilterForCAMLedgerUrl}/status"
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
				<kendo:grid-column-commandItem name="destroy" />
			</kendo:grid-column-command>
		</kendo:grid-column>

		<kendo:grid-column title="&nbsp;" width="100px">
			<kendo:grid-column-command>
				<kendo:grid-column-commandItem name="Approve"	click="approveStatusClick" />
			</kendo:grid-column-command>
		</kendo:grid-column>
			
			<%-- <kendo:grid-column title="&nbsp;" width="120px">
			<kendo:grid-column-command>
			    <kendo:grid-column-commandItem name="Manual Entry"/>
			</kendo:grid-column-command>
		</kendo:grid-column> --%>
			
			<%-- <kendo:grid-column title=""
				template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Active#=data.camLedgerid#'>#= data.status == 'Active' ? 'In-active' : 'Active' #</a>"
				width="100px" /> --%>
		</kendo:grid-columns>
		
		<kendo:dataSource pageSize="20" requestEnd="onRequestEnd" requestStart="onRequestStart">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-create url="${camLedgerCreateUrl}" dataType="json" type="GET" contentType="application/json" />
				<kendo:dataSource-transport-read url="${camLedgerReadUrl}" dataType="json" type="POST" contentType="application/json" />
			    <kendo:dataSource-transport-destroy url="${camLedgerDestroyUrl}"	dataType="json" type="GET" contentType="application/json" />
			</kendo:dataSource-transport>
			
			<kendo:dataSource-schema parse="parse">
				<kendo:dataSource-schema-model id="camLedgerid">
					<kendo:dataSource-schema-model-fields>

						<kendo:dataSource-schema-model-field name="camLedgerid" type="number">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="calculationBased" type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="camHeadProperty" type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="transactionName" type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="ledgerDate" type="date">
						</kendo:dataSource-schema-model-field>

						<kendo:dataSource-schema-model-field name="accountId" type="number" defaultValue="">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="postedToBill" type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="postType" type="string">
						</kendo:dataSource-schema-model-field>

						<kendo:dataSource-schema-model-field name="ledgerPeriod" type="string">
						</kendo:dataSource-schema-model-field>

						<kendo:dataSource-schema-model-field name="postReference" type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="transactionCode" type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="creditAmount" type="number">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="debitAmount" type="number">
							<kendo:dataSource-schema-model-field-validation/>							
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="balance" type="number">
							<kendo:dataSource-schema-model-field-validation min="1"/>							
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="headBalance" type="number">
							<kendo:dataSource-schema-model-field-validation min="1"/>							
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="postedBillDate" type="date">						
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="status" type="string">						
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="transactionSequence" type="number" defaultValue="">
							<kendo:dataSource-schema-model-field-validation min="1" />							
						</kendo:dataSource-schema-model-field>
						
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
			
		</kendo:dataSource>
	</kendo:grid> 
	
	<%-- <kendo:grid-detailTemplate id="camLedgerTemplate">
		<kendo:tabStrip name="tabStrip_#=camLedgerid#">
			<kendo:tabStrip-items>
			
 			<kendo:tabStrip-item selected="true" text="Cam Sub Ledger">
                <kendo:tabStrip-item-content>
                    <div class='wethear' style="width: 75%;">
							<br />
							<kendo:grid name="subLedgerEvent_#=camLedgerid#" pageable="true"
								resizable="true" sortable="true" reorderable="true"
								selectable="true" scrollable="true" edit="subLedgerEvent" dataBound="camSubLedgerDataBound" filterable="true">
								<kendo:grid-pageable pageSize="10"></kendo:grid-pageable>
								<kendo:grid-filterable extra="false">
			                    <kendo:grid-filterable-operators>
				                    <kendo:grid-filterable-operators-string eq="Is equal to" />
			                    </kendo:grid-filterable-operators>
		                        </kendo:grid-filterable>
								<kendo:grid-editable mode="popup"  confirmation="Are sure you want to delete this item ?"/>
						       <kendo:grid-toolbar >
						            <kendo:grid-toolbarItem name="create" text="Add Amount" />
						        </kendo:grid-toolbar> 
        						<kendo:grid-columns>
        							<kendo:grid-column title="Sub&nbsp;Ledger Id" field="camSubLedgerid" hidden="true" width="70px">
        							</kendo:grid-column>
        							
        							<kendo:grid-column title="camLedgerid" field="camLedgerid" hidden="true" width="70px">
        							</kendo:grid-column>
        							
        							<kendo:grid-column title="Transaction&nbsp;Name&nbsp;*" field="transactionName" footerTemplate="<span style='margin-left: 220px;' id='totlcst'><b>Total : </b></span>" width="140px">
        							</kendo:grid-column>
        							
        							<kendo:grid-column title="Transaction&nbsp;Name&nbsp;*" field="transactionCode" editor="transactionCodeEditor" hidden="true" width="70px">
        							</kendo:grid-column>
									
									<kendo:grid-column title="Amount" field="amount" footerTemplate="<span id='totlcst'><span id='sumTotal_#=camLedgerid#'></span> Rs</span>" filterable="false" width="100px">
									</kendo:grid-column>
									 
									<kendo:grid-column title="Balance&nbsp;Amount" field="balanceAmount" width="100px" filterable="false">
									</kendo:grid-column>
									
									<kendo:grid-column title="&nbsp;" width="120px">
										<kendo:grid-column-command>
											<kendo:grid-column-commandItem name="edit" />
											<kendo:grid-column-commandItem name="destroy" />
										</kendo:grid-column-command>
									</kendo:grid-column>
							            
        						</kendo:grid-columns>
        						
        						  <kendo:dataSource requestEnd="elRateSlabOnRequestEnd" >
						            <kendo:dataSource-transport>
						            <kendo:dataSource-transport-read url="${camSubLedgerReadUrl}/#=camLedgerid#" dataType="json" type="POST" contentType="application/json"/>
						            <kendo:dataSource-transport-create url="${camSubLedgerCreateUrl}/#=camLedgerid#" dataType="json" type="GET" contentType="application/json" />
						            <kendo:dataSource-transport-update url="${camSubLedgerUpdateUrl}/#=camLedgerid#" dataType="json" type="GET" contentType="application/json" />
						            <kendo:dataSource-transport-destroy url="${camSubLedgerDestroyUrl}" dataType="json" type="GET" contentType="application/json" />
						            </kendo:dataSource-transport>
						            
						            <kendo:dataSource-schema>
						                <kendo:dataSource-schema-model id="camSubLedgerid">
						                    <kendo:dataSource-schema-model-fields>
						                    
						                    <kendo:dataSource-schema-model-field name="camSubLedgerid" type="number">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="camLedgerid" type="number">
											</kendo:dataSource-schema-model-field>
						                    
											<kendo:dataSource-schema-model-field name="transactionCode" type="string">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="transactionName" type="string">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="amount" type="number">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="balanceAmount" type="number">
											</kendo:dataSource-schema-model-field>
																
											<kendo:dataSource-schema-model-field name="createdBy">
											</kendo:dataSource-schema-model-field>
						
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
	</kendo:grid-detailTemplate> --%>
	
	
<div id="alertsBox" title="Alert"></div>


<script>

function camLedgerDeleteEvent(){
	securityCheckForActions("./CAM/CAMLedger/destroyButton");
	var conf = confirm("Are u sure want to delete this item?");
	 if(!conf){
	   $("#grid").data().kendoGrid.dataSource.read();
	   throw new Error('deletion aborted');
	 }
}

var SelectedRowId = "";
var camHeadProperty = "";
var camLedgerStatus = "";
function onChangeCamLedger(arg) {
	 var gview = $("#grid").data("kendoGrid");
	 var selectedItem = gview.dataItem(gview.select());
	 SelectedRowId = selectedItem.camLedgerid;
	 camHeadProperty = selectedItem.camHeadProperty;
	 camLedgerStatus = selectedItem.status;
	 this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
    
}

function approveStatusClick()
{	  
  var result=securityCheckForActionsForStatus("./CAM/CAMLedger/approveButton");	  
  if(result=="success"){ 
	  $.ajax
		({			
			type : "POST",
			url : "./camLedgers/camLedgerStatusUpdate/"+SelectedRowId,
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

function camLedgerDataBound(e) {
	var data = this.dataSource.view(),row;
	var grid = $("#grid").data("kendoGrid");
    for (var i = 0; i < data.length; i++) {
    	var currentUid = data[i].uid;
        row = this.tbody.children("tr[data-uid='" + data[i].uid + "']");
        
        var ledgerStatus = data[i].status;
        if (ledgerStatus == 'Approved' || ledgerStatus=='Posted') {
			var currenRow = grid.table.find("tr[data-uid='" + currentUid+ "']");
			
			var deleteButton = $(currenRow).find(".k-grid-delete");
			deleteButton.hide();
			
			var approveButton = $(currenRow).find(".k-grid-Approve");
			approveButton.hide();
		}
    }
}

function camSubLedgerDataBound(e) {
	
	var data = this.dataSource.view();
	var sum = 0;
	for (var i = 0; i < data.length; i++) {
		row = this.tbody.children("tr[data-uid='" + data[i].uid + "']");
		sum = sum + data[i].amount;
	}
	$('#sumTotal_' + SelectedRowId).text(Math.round(sum));
	$('#totlcst').show();
	
	var data = this.dataSource.view(),row;
	var grid = $("#subLedgerEvent_"+SelectedRowId).data("kendoGrid");
    for (var i = 0; i < data.length; i++) {
    	var currentUid = data[i].uid;
        row = this.tbody.children("tr[data-uid='" + data[i].uid + "']");
        
        if (camLedgerStatus == 'Approved' || camLedgerStatus=='Posted') {
			var currenRow = grid.table.find("tr[data-uid='" + currentUid+ "']");
			
			var deleteButton = $(currenRow).find(".k-grid-delete");
			deleteButton.hide();
			
			var editButton = $(currenRow).find(".k-grid-edit");
			editButton.hide();
		}
    }
}

//for parsing timestamp data fields
function parse (response) {
    $.each(response, function (idx, elem) {   
    	   if(elem.postedBillDate=== null){
    		   elem.postedBillDate = "";
    	   }else{
    		   elem.postedBillDate = kendo.parseDate(new Date(elem.postedBillDate),'dd/MM/yyyy HH:mm');
    	   }
       });
       
       return response;
}

//Onclick functions

function clearFilterLedger()
{
   $("form.k-filter-menu button[type='reset']").slice().trigger("click");
   var gridStoreIssue = $("#grid").data("kendoGrid");
   gridStoreIssue.dataSource.read();
   gridStoreIssue.refresh();
}
 
function dropDownChecksEditor(container, options) {
	var res = (container.selector).split("=");
	var attribute = res[1].substring(0,res[1].length-1);
	
	$('<input data-text-field="text" data-value-field="value" required="true" validationmessage="Main head is required" data-bind="value:' + options.field + '" name = "'+attribute+'" id = "'+attribute+'Id"/>')
			.appendTo(container).kendoDropDownList({
				optionLabel : {
					text : "Select",
					value : "",
				},
				defaultValue : false,
				sortable : true,
				change : selectedServiceType,
				dataSource : {
					transport : {
						read : "${allChecksUrl}/"+attribute,
					}
				}
			});
	 $('<span class="k-invalid-msg" data-for="'+attribute+'"></span>').appendTo(container);
}

var selectedMainHead="";
function selectedServiceType(e){
	var dataItem = this.dataItem(e.item);
	selectedMainHead=dataItem.text;
	if(selectedMainHead=="Advance Fixed Charges"){
		
		$('div[data-container-for="debitAmount"]').hide();
		$('label[for="debitAmount"]').closest('.k-edit-label').hide();
		
		var comboBoxDataSource = new kendo.data.DataSource({
	          transport: {
	              read: {
	                  url     : "${transactionCodeListUrl}/"+selectedMainHead,
	                  dataType: "json",
	                  type    : 'GET'
	              }
	          },
		           
		 });
		        
	   $("#transactionCode").kendoComboBox({
	      dataSource    : comboBoxDataSource,
	      optionLabel : "Select",
	      placeholder: "Select Transaction",
	      dataTextField : "transactionName",
	      dataValueField: "transactionCode",
	      select : selectedTransaction,
	  });
	   $("#transactionCode").data("kendoComboBox").value("");
		
	}else{
		
		$('div[data-container-for="debitAmount"]').show();
		$('label[for="debitAmount"]').closest('.k-edit-label').show();
		 
		$('div[data-container-for="transactionCode"]').show();
		$('label[for="transactionCode"]').closest('.k-edit-label').show(); 
		
		var comboBoxDataSource = new kendo.data.DataSource({
	          transport: {
	              read: {
	                  url     : "${transactionCodeListUrl}/"+selectedMainHead,
	                  dataType: "json",
	                  type    : 'GET'
	              }
	          },
		           
		 });
		        
	   $("#transactionCode").kendoComboBox({
	      dataSource    : comboBoxDataSource,
	      optionLabel : "Select",
	      placeholder: "Select Transaction",
	      dataTextField : "transactionName",
	      dataValueField: "transactionCode",
	      select : selectedTransaction,
	  });
	   $("#transactionCode").data("kendoComboBox").value("");
	}
}

function selectedTransaction(e){
	var dataItem = this.dataItem(e.item.index());
	if(dataItem.transactionCode=="CAM_ADVANCE_UTILITY"){
		 $('div[data-container-for="debitAmount"]').hide();
		 $('label[for="debitAmount"]').closest('.k-edit-label').hide();
	}else if(dataItem.transactionCode=="CAM_ADVANCE_MAINTENANCE"){
		 $('div[data-container-for="debitAmount"]').hide();
		 $('label[for="debitAmount"]').closest('.k-edit-label').hide();
	} else if(dataItem.transactionCode=="CAM_ADVANCE_OTHERSERVICES"){
		$('div[data-container-for="debitAmount"]').hide();
		 $('label[for="debitAmount"]').closest('.k-edit-label').hide();
	}
}

function transactionCodeEditor(container, options){
	$('<input name="transactionName" data-text-field="transactionName" id="transactionCode" data-value-field="transactionCode" validationmessage="Transaction name is required" data-bind="value:' + options.field + '" required="required"/>')
			.appendTo(container).kendoComboBox({
				autoBind : false,
				placeholder : "Select Transaction Code",				
				dataSource : {
					transport : {
						read : "${transactionCodeListUrl}/"+selectedMainHead,
					}
				}
				
			});
	 $('<span class="k-invalid-msg" data-for="transactionName"></span>').appendTo(container); 
}
 
var setApCode = "";
function camLedgerEvent(e)
{
	/***************************  to remove the id from pop up  **********************/
	$('div[data-container-for="camLedgerid"]').remove();
	$('label[for="camLedgerid"]').closest('.k-edit-label').remove();
	
	$(".k-edit-field").each(function () {
		$(this).find("#temPID").parent().remove();  
   	});     
   	
   	$('label[for="calculationBased"]').parent().hide();  
	$('div[data-container-for="calculationBased"]').hide();
   	
   	$('label[for="ledgerPeriod"]').parent().hide();  
	$('div[data-container-for="ledgerPeriod"]').hide();
	
	$('label[for="creditAmount"]').parent().hide();  
	$('div[data-container-for="creditAmount"]').hide();
   	
   	$('label[for="accountNo"]').parent().hide();  
	$('div[data-container-for="accountNo"]').hide();
	
	$('label[for="balance"]').parent().hide();  
	$('div[data-container-for="balance"]').hide();
	
	$('label[for="headBalance"]').parent().hide();  
	$('div[data-container-for="headBalance"]').hide();
	
	$('label[for="postedToBill"]').parent().hide();  
	$('div[data-container-for="postedToBill"]').hide();
	
	$('label[for="postedBillDate"]').parent().hide();  
	$('div[data-container-for="postedBillDate"]').hide();
	
	$('label[for="transactionName"]').parent().hide();  
	$('div[data-container-for="transactionName"]').hide();
	
	$('label[for="amount"]').parent().hide();  
	$('div[data-container-for="amount"]').hide();
	
	$('label[for="status"]').parent().hide();  
	$('div[data-container-for="status"]').hide();
	
	/* $('label[for="ledgerDate"]').parent().hide();  
	$('div[data-container-for="ledgerDate"]').hide(); */
	
	/************************* Button Alerts *************************/
	if (e.model.isNew()) 
    {
		securityCheckForActions("./CAM/CAMLedger/createButton");
		$(".k-window-title").text("Add CAM Heads Amount");
		$(".k-grid-update").text("Save");
		
    }
	else{
		
		setApCode = $('input[name="camLedgerid"]').val();
		$(".k-window-title").text("Edit CAM Heads Amount");
	}
}

$("#grid").on("click", ".k-grid-Clear_Filter", function(){
    //custom actions
	$("form.k-filter-menu button[type='reset']").slice().trigger("click");
	var gridPets = $("#grid").data("kendoGrid");
	gridPets.dataSource.read();
	gridPets.refresh();
});


/********************** to hide the child table id ***************************/
function subLedgerEvent(e){
	if(camLedgerStatus == "Approved" || camLedgerStatus == "Posted"){
		var grid = $("#subLedgerEvent_" + SelectedRowId).data("kendoGrid");
		grid.cancelChanges();
		alert("You can't add cam heads amount,because cam ledger already "+camLedgerStatus);
	}else{
		
	 $('div[data-container-for="camLedgerid"]').remove();
	 $('label[for="camLedgerid"]').closest('.k-edit-label').remove();  	
		
	 $('div[data-container-for="camSubLedgerid"]').remove();
	 $('label[for="camSubLedgerid"]').closest('.k-edit-label').remove();  
	 
	 $('div[data-container-for="camLedgerid"]').remove();
	 $('label[for="camLedgerid"]').closest('.k-edit-label').remove(); 
	 
	 $('div[data-container-for="balanceAmount"]').remove();
	 $('label[for="balanceAmount"]').closest('.k-edit-label').remove(); 
	 
	 $('div[data-container-for="transactionName"]').remove();
	 $('label[for="transactionName"]').closest('.k-edit-label').remove(); 
	 
		if (e.model.isNew()) 
	    {
			
			$(".k-window-title").text("Add Amount");
			$(".k-grid-update").text("Save");
			
			
	    }
		else{
			
			setApCode = $('input[name="elSubLedgerid"]').val();
			
			$('div[data-container-for="transactionCode"]').remove();
			$('label[for="transactionCode"]').closest('.k-edit-label').remove();
			
			$(".k-window-title").text("Edit Sub Ledger Details");
		}
	  }
	}


function onRequestStart(e){
	$('.k-grid-update').hide();
    $('.k-edit-buttons')
            .append(
                    '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
    $('.k-grid-cancel').hide();	
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
						"Error: Creating the Cam Ledger Details<br>" + errorInfo);
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
						"Error: Updating the Cam Ledger Details<br>" + errorInfo);
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
			}

			$('#grid').data('kendoGrid').refresh();
			$('#grid').data('kendoGrid').dataSource.read();
			return false;
		}
		
		if (r.response.status == "CHILD") {

			
				$("#alertsBox").html("");
				$("#alertsBox")
						.html("Can't Delete Cam Ledger Details, Child Record Found");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				$('#grid').data('kendoGrid').refresh();
				$('#grid').data('kendoGrid').dataSource.read();
			return false;
		}

		else if (r.response.status == "INVALID") {

			errorInfo = "";

			errorInfo = r.response.result.invalid;

			if (r.type == "create") {
				//alert("Error: Creating the USER record\n\n" + errorInfo);
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Error: Creating the Cam Ledger Details<br>" + errorInfo);
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
			}
			$('#grid').data('kendoGrid').refresh();
			$('#grid').data('kendoGrid').dataSource.read();
			return false;
		}

		else if (r.response.status == "EXCEPTION") {

			errorInfo = "";

			errorInfo = r.response.result.exception;

			if (r.type == "create") {
				
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Error: Creating the Cam Ledger Details<br>" + errorInfo);
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
						"Error: Updating the Cam Ledger Details<br>" + errorInfo);
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
			}

			$('#grid').data('kendoGrid').refresh();
			$('#grid').data('kendoGrid').dataSource.read();
			return false;
		}

		else if (r.type == "create") {
			
			$("#alertsBox").html("");
			$("#alertsBox").html("Add the cam head amounts");
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

		else if (r.type == "update") {
			
			$("#alertsBox").html("");
			$("#alertsBox")
					.html("Cam Ledger Details updated successfully");
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
		
		else if (r.type == "destroy") {
			$("#alertsBox").html("");
			$("#alertsBox").html("Cam Ledger Details deleted successfully");
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


/************************************* for inner rate slab request *********************************/

function elRateSlabOnRequestEnd(e)
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

				$('#gridAccessCardPermission_'+SelectedRowId).data().kendoGrid.dataSource.read({personId:SelectedAccessCardId});
				/* var grid = $("#propertyGrid_"+SelectedRowId).data("kendoGrid");
				grid.dataSource.read();
				grid.refresh(); */
				return false;
			}
		

	  else if (e.type == "create") 
		{
			$("#alertsBox").html("");
			$("#alertsBox").html("Cam Sub Ledger Record Created Successfully");
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
			var grid = $("#subLedgerEvent_"+SelectedRowId).data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
		}

		else if (e.type == "update") 
		{
			$("#alertsBox").html("");
			$("#alertsBox").html("Cam Sub Ledger Record updated successfully");
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
			var grid = $("#subLedgerEvent_"+SelectedRowId).data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
		}

		else if (e.type == "destroy") 
		{
			$("#alertsBox").html("");
			$("#alertsBox").html("Sub Ledger Record deleted successfully");
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
 
/***************************  Custom message validation  **********************/

(function ($, kendo) 
		{  
	    $.extend(true, kendo.ui.validator, 
	    {
	         rules: 
	         { // custom rules
	            
	             
	        	  	/* camHeadPropertyRequired : function(input, params){
                     if (input.attr("name") == "camHeadProperty")
                     {
                      return $.trim(input.val()) !== "";
                     }
                     return true;
                    }, */  
                /* camHeadPropertyRequired : function(input, params){
                        if (input.attr("name") == "camHeadProperty")
                        {
                         return $.trim(input.val()) !== "";
                        }
                        return true;
                       } , */
                ledgerDateRequired : function(input, params){
                           if (input.attr("name") == "ledgerDate")
                           {
                            return $.trim(input.val()) !== "";
                           }
                           return true;
                          } ,
	        	 ledgerDateValidation : function(input,params) {
						if (input.filter("[name = 'ledgerDate']").length && input.val()) {
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
								
	            },
	         messages: 
	         {
				//custom rules messages
				/* camHeadPropertyRequired:"Cam heads is required", */
				/*  camHeadPropertyRequired : "Main head is required",  */
				 ledgerDateRequired : "Transaction date is required", 
	        	 ledgerDateValidation : " Transaction date must be past date"
			 }
	    });
	    
	})(jQuery, kendo); 


</script>
