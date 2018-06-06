<%@include file="/common/taglibs.jsp"%>


<c:url value="/camConsolidation/camConsolidationRead" var="camConsolidationReadUrl" />
<c:url value="/camConsolidation/camConsolidationCreate" var="camConsolidationCreateUrl" />

<c:url value="/camConsolidation/camConsolidationHeadsRead" var="camConsolidationHeadsReadUrl" />
<c:url value="/camLedgers/subLedgerCreate" var="camSubLedgerCreateUrl" />

<c:url value="/common/getAllChecks" var="allChecksUrl" />
<c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />
<c:url value="/person/getAllAttributeValues" var="filterAutoCompleteUrl" /> 
<c:url value="/camLedgers/transactionCodeList" var="transactionCodeListUrl" />

<!-- Editor URL's Of Open New Ticket -->
<c:url value="/openNewTickets/readTowerNames" var="towerNames" />
<c:url value="/openNewTickets/readPropertyNumbers" var="propertyNum" />
<c:url value="/camConsolidation/readAccountNumbers" var="readAccountNumbers" />

<!-- Filters Data url's -->	
<c:url value="/camConsolidation/filter" var="commonFilterForConsolidationUrl" />

 <kendo:grid name="grid" change="onChangeCamConsolidation" resizable="true" pageable="true" edit="camConsolidationEvent" dataBound="camConsolidationDataBound" selectable="true" detailTemplate="camConsolidationTemplate" sortable="true" scrollable="true" 
		groupable="true">
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10"></kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
		<kendo:grid-filterable-operators>
			<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains"/>
			<kendo:grid-filterable-operators-date eq="Is equal to" gt="Is after" lt="Is before"/>
		</kendo:grid-filterable-operators> 
	</kendo:grid-filterable>
	<kendo:grid-editable mode="popup"/>
		
			<kendo:grid-toolbar>
		      <kendo:grid-toolbarItem name="create" text="CAM Bills" />
		      <kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterCamConsolidation()>Clear Filter</a>"/>
	        </kendo:grid-toolbar>
		<kendo:grid-columns>
			<kendo:grid-column title="ConsolidationId" field="ccId" width="110px" hidden="true"/>
			
			<kendo:grid-column title="CAM&nbsp;Name&nbsp;*" field="camName" hidden="true" filterable="true" width="120px">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function ledgerPeriodFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Period",                                            
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${commonFilterForLedgerUrl}/camHeadProperty"
										}
									}
								});    
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="Period&nbsp;*" field="chargesType" editor="dropDownChecksEditor" filterable="true" width="120px">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function ledgerPeriodFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Period",                                            
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${commonFilterForConsolidationUrl}/chargesType"
										}
									}
								});    
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="Last&nbsp;Transaction&nbsp;Accounted&nbsp;Date" field="fromDate" filterable="true" width="210px" format="{0:dd/MM/yyyy}">
			</kendo:grid-column>
			
			<kendo:grid-column title="Consolidate&nbsp;Upto&nbsp;" field="toDate" filterable="true" width="125px" format="{0:dd/MM/yyyy}">
			</kendo:grid-column>
			
			<kendo:grid-column title="No&nbsp;Of&nbsp;Flats" field="noOfFlats" filterable="true" width="120px">
			</kendo:grid-column>
			
			<kendo:grid-column title="Total&nbsp;SQFT" field="totalSqft" filterable="true"  width="120px">
			</kendo:grid-column>
			
			<kendo:grid-column title="Rate&nbsp;Per&nbsp;Flat" field="ratePerFlat" format="{0:#.00}" width="110px" filterable="true">
			</kendo:grid-column>
			
			 <kendo:grid-column title="&nbsp;" field="calculationBased" editor="calucalationBasedEditor" hidden="true" width="140px" filterable="true">
			 </kendo:grid-column>
			
			<kendo:grid-column title="Rate&nbsp;Per&nbsp;SQFT" field="ratePerSqft" format="{0:#.00}" width="125px" filterable="true">
			</kendo:grid-column>
			
			<kendo:grid-column title="Rebate&nbsp;Per&nbsp;Flat" field="rebateRate" format="{0:#.00}" width="125px" filterable="true">
			</kendo:grid-column>
			
			<kendo:grid-column title="Fixed&nbsp;Per&nbsp;SQFT" field="fixedPerSqft" format="{0:#.00}" width="125px" filterable="true">
			</kendo:grid-column>
			
			<kendo:grid-column title="Actual&nbsp;To&nbsp;Be&nbsp;Billed" field="billed" format="{0:#.00}" width="125px" filterable="true">
			</kendo:grid-column>
			
			<kendo:grid-column title="To&nbsp;Be&nbsp;Billed" field="toBeBilled" format="{0:#.00}" width="125px" filterable="true">
			</kendo:grid-column>
			
			<kendo:grid-column title="No&nbsp;of&nbsp;Flats&nbsp;Billed" field="noFlatsBilled" width="125px" filterable="true">
			</kendo:grid-column>
			
			<kendo:grid-column title="Paid&nbsp;Amount" field="paidAmount" format="{0:#.00}" width="110px" filterable="true">
			</kendo:grid-column>
			
			<kendo:grid-column title="Balance" field="blanceAmount" format="{0:#.00}" width="110px" filterable="true">
			</kendo:grid-column>
			
			<kendo:grid-column title="Status" field="status" width="110px" filterable="true">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function ledgerPeriodFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Period",                                            
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${commonFilterForConsolidationUrl}/status"
										}
									}
								});    
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title=""
				template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Active'>#= data.status == 'Created' ? 'Approve' : 'Post' #</a>"
				width="80px" />
			
			<kendo:grid-column title="&nbsp;" width="120px">
			<kendo:grid-column-command>
			    <kendo:grid-column-commandItem name="Bill" click="billPosted"/>
			</kendo:grid-column-command>
		</kendo:grid-column>
			
			<%-- <kendo:grid-column title=""
				template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Active#=data.ccId#'>#= data.status == 'Active' ? 'In-active' : 'Active' #</a>"
				width="100px" /> --%>
		</kendo:grid-columns>
		
		<kendo:dataSource requestEnd="onRequestEnd" pageSize="20" requestStart="onRequestStart">
			<kendo:dataSource-transport>
			<kendo:dataSource-transport-create url="${camConsolidationCreateUrl}" dataType="json" type="GET" contentType="application/json" />
				<kendo:dataSource-transport-read url="${camConsolidationReadUrl}" dataType="json" type="POST" contentType="application/json" />
				<%-- <kendo:dataSource-transport-update url="${elratemasterUpdateUrl}"
					dataType="json" type="GET" contentType="application/json" />
			    <kendo:dataSource-transport-destroy url="${elratemasterDestroyUrl}"
					dataType="json" type="GET" contentType="application/json" /> --%>
			
			</kendo:dataSource-transport>
			
			<kendo:dataSource-schema>
				<kendo:dataSource-schema-model id="ccId">
					<kendo:dataSource-schema-model-fields>

						<kendo:dataSource-schema-model-field name="ccId" type="number">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="calculationBased" type="string" defaultValue="Actual">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="status" type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="chargesType" type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="camName" type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="fromDate" type="date">
						</kendo:dataSource-schema-model-field>

						<kendo:dataSource-schema-model-field name="toDate" type="date">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="noOfFlats" type="number">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="totalSqft" type="number">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="fixedPerSqft" type="number" defaultValue="">
							<kendo:dataSource-schema-model-field-validation min="0" />	
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="toBeBilled" type="number">
						</kendo:dataSource-schema-model-field>

						<kendo:dataSource-schema-model-field name="ratePerFlat" type="number">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="ratePerSqft" type="number">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="rebateRate" type="number">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="noFlatsBilled" type="number">						
						</kendo:dataSource-schema-model-field>  
						
						<kendo:dataSource-schema-model-field name="billed" type="number">						
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="paidAmount" type="number">						
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="blanceAmount" type="number">						
						</kendo:dataSource-schema-model-field>
						
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
			
		</kendo:dataSource>
	</kendo:grid> 
	
	<kendo:grid-detailTemplate id="camConsolidationTemplate">
		<kendo:tabStrip name="tabStrip_#=ccId#">
			<kendo:tabStrip-items>
			
 			<kendo:tabStrip-item selected="true" text="CAM Heads">
                <kendo:tabStrip-item-content>
                    <div class='wethear' style="width: 75%;">
							<br />
							<kendo:grid name="camHeadsEvent_#=ccId#" pageable="true"
								resizable="true" sortable="true" reorderable="true"
								selectable="true" scrollable="true" edit="camHeadsEvent" filterable="true">
								<kendo:grid-pageable pageSize="10"></kendo:grid-pageable>
								<kendo:grid-filterable extra="false">
			                    <kendo:grid-filterable-operators>
				                    <kendo:grid-filterable-operators-string eq="Is equal to" />
			                    </kendo:grid-filterable-operators>
		                        </kendo:grid-filterable>
								<kendo:grid-editable mode="popup"  confirmation="Are sure you want to delete this item ?"/>
						       <kendo:grid-toolbar >
						            <%-- <kendo:grid-toolbarItem name="create" text="Add Amount" /> --%>
						        </kendo:grid-toolbar> 
        						<kendo:grid-columns>
        							<kendo:grid-column title="Sub&nbsp;Ledger Id" field="camHeadId" hidden="true" width="70px">
        							</kendo:grid-column>
        							
        							<kendo:grid-column title="Group&nbsp;Name" field="groupName" width="140px" filterable="false">
        							</kendo:grid-column>
        							
        							<kendo:grid-column title="Head&nbsp;Name" field="transactionName" width="70px" filterable="false">
        							</kendo:grid-column>
									
									<kendo:grid-column title="Calculation&nbsp;Basis" field="calculationBasis" filterable="false" width="100px">
									</kendo:grid-column>
									 
									<kendo:grid-column title="Amount" field="amount" width="100px" filterable="false">
									</kendo:grid-column>
							            
        						</kendo:grid-columns>
        						
        						  <kendo:dataSource>
						            <kendo:dataSource-transport>
						            <kendo:dataSource-transport-read url="${camConsolidationHeadsReadUrl}/#=ccId#" dataType="json" type="POST" contentType="application/json"/>
						            <kendo:dataSource-transport-create url="${camSubLedgerCreateUrl}/#=ccId#" dataType="json" type="GET" contentType="application/json" />
						            <%-- <kendo:dataSource-transport-update url="${elRateSlabUpdateUrl}/#=elrmid#" dataType="json" type="POST" contentType="application/json" />
						            <kendo:dataSource-transport-destroy url="${elRateSlabDeleteUrl}" dataType="json" type="POST" contentType="application/json" /> --%>
						            </kendo:dataSource-transport>
						            
						            <kendo:dataSource-schema>
						                <kendo:dataSource-schema-model id="ccId">
						                    <kendo:dataSource-schema-model-fields>
						                    
						                    <kendo:dataSource-schema-model-field name="ccId" type="number">
											</kendo:dataSource-schema-model-field>
						                    
											<kendo:dataSource-schema-model-field name="transactionCode" type="string">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="transactionName" type="string">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="groupName" type="string">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="calculationBasis" type="string">
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
            
			</kendo:tabStrip-items>
		</kendo:tabStrip>
	</kendo:grid-detailTemplate>
	
	
<div id="generateBillDialog" style="display: none;">
<form id="billPost">
<div id="previousDiv" align="center">
<table  style="height: 150px">
	<tr>
		<td>Flats </td>
		<td>
		   <input id="flatsType" name="flatsType" value="1" onchange="flatsTypeChange()">
		</td>	
		</tr>
		
		<tr id="blocks">
		<td>Blocks </td>
		<td>
		   <input id="blockId" name="blockId">
		</td>	
		</tr>
		
		<tr id="property">
		<td>Property Number </td>
		<td>
		   <input id="propertyId" name="propertyId" >
		</td>	
		</tr>
		
		<tr id="account">
		<td>Account Number </td>
		<td>
		   <input id="accountId" name="accountId" >
		</td>	
		</tr>
</table>

</div>

<div align="center">
<table>
<tr><td class="left" align="center" >
	<button type="submit"  id="calcu" class="k-button" onclick="postTheBill()">Bill</button>
	</td></tr>

</table>
</div>
</form>
</div>	
	
<div id='alertsBox'></div>

<script>

var SelectedRowId = "";

function onChangeCamConsolidation(arg) {
	 var gview = $("#grid").data("kendoGrid");
	 var selectedItem = gview.dataItem(gview.select());
	 SelectedRowId = selectedItem.ccId;
	 this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
   
}

function camConsolidationDataBound(e){
	var data = this.dataSource.view(),row;
	var grid = $("#grid").data("kendoGrid");
    for (var i = 0; i < data.length; i++) {
    	var currentUid = data[i].uid;
        row = this.tbody.children("tr[data-uid='" + data[i].uid + "']");
        
        var ledgerStatus = data[i].status;
        var blanceAmount = data[i].blanceAmount;
        
        if(blanceAmount<=0){
			var currenRow = grid.table.find("tr[data-uid='" + currentUid+ "']");
			
			var billButton = $(currenRow).find(".k-grid-Bill");
			billButton.hide();
        }
        
        if (ledgerStatus == 'Approved' || ledgerStatus=='Created') {
			var currenRow = grid.table.find("tr[data-uid='" + currentUid+ "']");
			
			var addCamBillsButton = $(currenRow).find(".k-grid-add");
			addCamBillsButton.hide();
			
			var billButton = $(currenRow).find(".k-grid-Bill");
			billButton.hide();
		}
        
        if(ledgerStatus == 'Posted'){
			var currenRow = grid.table.find("tr[data-uid='" + currentUid+ "']");
			
			var approveButton = $(currenRow).find(".k-grid-Active");
			approveButton.hide();
        }
    }
}

function calucalationBasedEditor(container, options) {
	$('<input type="radio" name=' + options.field + ' value="Actual" checked="true" /> Actual &nbsp;&nbsp;&nbsp; <input type="radio" name=' + options.field + ' value="Fixed Per Sqft"/> Fixed Per Sqft <br>')
		.appendTo(container);
}

$(document).on('change','input[name="calculationBased"]:radio',function() {

	var radioValue = $('input[name=calculationBased]:checked').val();
	if (radioValue == 'Actual') {
		
		$('div[data-container-for="fixedPerSqft"]').hide();
		$('label[for="fixedPerSqft"]').closest('.k-edit-label').hide();

	} else if(radioValue == 'Fixed Per Sqft'){
		
		$('div[data-container-for="fixedPerSqft"]').show();
		$('label[for="fixedPerSqft"]').closest('.k-edit-label').show();
	}
});

function dropDownChecksEditor(container, options) {
	var res = (container.selector).split("=");
	var attribute = res[1].substring(0,res[1].length-1);
	
	$('<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '" name = "'+attribute+'" id = "'+attribute+'Id"/>')
			.appendTo(container).kendoDropDownList({
				optionLabel : {
					text : "Select",
					value : "",
				},
				defaultValue : false,
				select : selectedChargesType,
				sortable : true,
				dataSource : {
					transport : {
						read : "${allChecksUrl}/"+attribute,
					}
				}
			});
	 $('<span class="k-invalid-msg"  data-for="'+attribute+'"></span>').appendTo(container);
}

function selectedChargesType(e){
	
	var dataItem = this.dataItem(e.item.index());
	if(dataItem.text=="Custom"){
		 $('div[data-container-for="toDate"]').show();
		 $('label[for="toDate"]').closest('.k-edit-label').show();
	}else{
		 $('div[data-container-for="toDate"]').hide();
		 $('label[for="toDate"]').closest('.k-edit-label').hide();		 
	}
}

function flatsTypeChange(){
	var gridStoreIssue = $("#flatsType").val();
	if(gridStoreIssue!="All"){
		$("#blocks").show();
		$("#property").show();
		$("#account").show();
	}else{
		$("#blocks").hide();
		$("#property").hide();
		$("#account").hide();
	}
}

function clearFilterCamConsolidation()
{
   $("form.k-filter-menu button[type='reset']").slice().trigger("click");
   var gridStoreIssue = $("#grid").data("kendoGrid");
   gridStoreIssue.dataSource.read();
   gridStoreIssue.refresh();
}

var SelectedLedgerPeriod="";
function categoriesChangePeriod() {	
	 var value = this.value();
	 if (value != "Select") {
   	 $('.k-grid-add').show();
    } else {
   	$('.k-grid-add').hide();
    }
	 SelectedLedgerPeriod = value;
	 alert(SelectedLedgerPeriod);
}

function postTheBill(){
	var ccId="";
	var gridParameter = $("#grid").data("kendoGrid");
	var selectedAddressItem = gridParameter.dataItem(gridParameter.select());
	ccId = selectedAddressItem.ccId;
    var accountId=$("#accountId").val();
    var propertyId=$("#propertyId").val();
    var flatsType=$("#flatsType").val();
    var result=securityCheckForActionsForStatus("./CAM/CAMBills/billButton");	    
	 if(result=="success"){  
	$.ajax
	({
		type : "POST",
		url : "./camConsolidation/postTheBillData",
		dataType:"text",
		data : {
			accountId : accountId, 
			ccId : ccId,
			propertyId : propertyId,
			flatsType : flatsType,
			 },
		success : function(response) 
		{
			$("#alertsBox").html("Bill Posted Successfully");
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
			close();
		}
	});
	 }
}

function close(){
	
	var todcal=$( "#generateBillDialog" );
	todcal.kendoWindow({
		width : '350',
		height : 'auto',
		modal : true,
		draggable : true,
		position : {
			top : 100
		},
		title : "Bill Information"
	 }).data("kendoWindow").center().close();
	
	todcal.kendoWindow("close");
}

$("#billPost").submit(function(e){
    e.preventDefault();
});

function addCamLedgerData(){
	$.ajax
	({
		type : "POST",
		url : "./camConsolidation/camConsolidationCreate/"+SelectedLedgerPeriod,
		dataType:"text",
		success : function(response) 
		{
			alert("Imported data from cam ledger");
			//$("#alertsBox").html("");
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

$(document).ready(function() {

	 var data = [
                 { text: "All", value: "All" },
                 { text: "Specific", value: "Specific" },                
             ];
	 
	$("#flatsType").kendoDropDownList({
			dataTextField : "text",
			dataValueField : "value",
			optionLabel : {
				text : "Select",
				value : "",
			},

			dataSource : data

		}).data("kendoDropDownList");
	
	 $("#blockId").kendoDropDownList({
		dataTextField : "blockName",
		dataValueField : "blockName",
		autoBind: false,
	    optionLabel : "Select",
	    dataSource: {  
	        transport:{
	            read: "${towerNames}"
	        }
	    }

	}).data("kendoDropDownList"); 

	$("#propertyId").kendoDropDownList({
		cascadeFrom: "blockId",
		dataTextField : "property_No",
		dataValueField : "propertyId",
		optionLabel : "Select",
	    autoBind: false,
	    dataSource: {  
	        transport:{
	            read: "${propertyNum}"
	        }
	    }

	}).data("kendoDropDownList"); 
	
	$("#accountId").kendoComboBox({
		 autoBind : false,
		 cascadeFrom: "propertyId",
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
		 dataSource : {
		  transport : {  
		   read :  "${readAccountNumbers}"
		  }
		 }

	}).data("kendoComboBox"); 
	
	});

	function billPosted() {

		var todcal = $("#generateBillDialog");
		todcal.kendoWindow({
			width : '350',
			height : 'auto',
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Bill Information"
		}).data("kendoWindow").center().open();

		todcal.kendoWindow("open");
		$("#blocks").hide();
		$("#property").hide();
		$("#account").hide();
	}

	//Onclick functions

	function clearFilterLedger() {
		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
		var gridStoreIssue = $("#grid").data("kendoGrid");
		gridStoreIssue.dataSource.read();
		gridStoreIssue.refresh();
	}

	function transactionCodeEditor(container, options) {
		$(
				'<input name="transactionName" data-text-field="transactionName" id="transactionCode" data-value-field="transactionCode" validationmessage="Transaction name is required" data-bind="value:' + options.field + '" required="required"/>')
				.appendTo(container).kendoComboBox(
						{
							autoBind : false,
							placeholder : "Select Transaction Code",
							dataSource : {
								transport : {
									read : "${transactionCodeListUrl}/"
											+ camHeadProperty,
								}
							}

						});
		$('<span class="k-invalid-msg" data-for="transactionName"></span>')
				.appendTo(container);
	}
	
	var setApCode="";	
	var flagTransactionCode="";
	function camConsolidationEvent(e)
	{
		    /***************************  to remove the id from pop up  **********************/
			$('div[data-container-for="ccId"]').remove();
			$('label[for="ccId"]').closest('.k-edit-label').remove();
			
			$(".k-window").css({"top" : "150px"});
			
			$(".k-edit-field").each(function () {
				$(this).find("#temPID").parent().remove();  
		   	});
		   	
		   	$('div[data-container-for="rebateRate"]').remove();
			$('label[for="rebateRate"]').closest('.k-edit-label').remove();
		   	
		   	$('div[data-container-for="calculationBased"]').remove();
			$('label[for="calculationBased"]').closest('.k-edit-label').remove();
		   	
		   	$('div[data-container-for="camName"]').remove();
			$('label[for="camName"]').closest('.k-edit-label').remove();
		   	
		   	$('div[data-container-for="paidAmount"]').hide();
			$('label[for="paidAmount"]').closest('.k-edit-label').hide();
		   	
		   	$('div[data-container-for="fixedPerSqft"]').hide();
			$('label[for="fixedPerSqft"]').closest('.k-edit-label').hide();
		   	
		   	$('div[data-container-for="toBeBilled"]').hide();
			$('label[for="toBeBilled"]').closest('.k-edit-label').hide();
			
			$('div[data-container-for="fromDate"]').hide();
			$('label[for="fromDate"]').closest('.k-edit-label').hide();
			
			$('div[data-container-for="toDate"]').hide();
			$('label[for="toDate"]').closest('.k-edit-label').hide();
			
			$('div[data-container-for="blanceAmount"]').hide();
			$('label[for="blanceAmount"]').closest('.k-edit-label').hide();
			
			$('div[data-container-for="billed"]').remove();
			$('label[for="billed"]').closest('.k-edit-label').remove();
			
			$('div[data-container-for="noFlatsBilled"]').remove();
			$('label[for="noFlatsBilled"]').closest('.k-edit-label').remove();
			
			$('div[data-container-for="ratePerSqft"]').remove();
			$('label[for="ratePerSqft"]').closest('.k-edit-label').remove();
			
			$('div[data-container-for="ratePerFlat"]').remove();
			$('label[for="ratePerFlat"]').closest('.k-edit-label').remove();
			
			$('div[data-container-for="totalSqft"]').remove();
			$('label[for="totalSqft"]').closest('.k-edit-label').remove();
			
			$('div[data-container-for="noOfFlats"]').remove();
			$('label[for="noOfFlats"]').closest('.k-edit-label').remove();
			
			$('div[data-container-for="status"]').remove();
			$('label[for="status"]').closest('.k-edit-label').remove();
				
			/************************* Button Alerts *************************/
			if (e.model.isNew()) 
		    {
				securityCheckForActions("./CAM/CAMBills/createButton");
				flagTransactionCode=true;
				setApCode = $('input[name="transactionId"]').val();
				$(".k-window-title").text("Add CAM Consolidation Details");
				$(".k-grid-update").text("Consolidate");		
		    }
			else{
				//securityCheckForActions("./CustomerCare/HelpTopic/editButton");
				flagTransactionCode=false;
				$(".k-window-title").text("Edit Payment Details");
			}
	}
	
	/********************** to hide the child table id ***************************/
	function camHeadsEvent(e)
	{
		 $('div[data-container-for="camHeadId"]').remove();
		 $('label[for="camHeadId"]').closest('.k-edit-label').remove();  
		 
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
				
				setApCode = $('input[name="camHeadId"]').val();
				$(".k-window-title").text("Edit Sub Ledger Details");
			}
		}

	$("#grid").on("click", ".k-grid-Clear_Filter", function() {
		//custom actions
		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
		var gridPets = $("#grid").data("kendoGrid");
		gridPets.dataSource.read();
		gridPets.refresh();
	});
	
	$("#grid").on("click", "#temPID", function(e) {
		  
		  var button = $(this), enable = button.text() == "Approve";
		  var widget = $("#grid").data("kendoGrid");
		  var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
		  var result=securityCheckForActionsForStatus("./CAM/CAMBills/approvePostButton"); 
		  if(result=="success"){    
			  
							if (enable) 
							{
								$.ajax
								({
									type : "POST",
									url : "./camConsolidationBills/setCamBillsStatus/" +dataItem.id+ "/activate",
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
										button.text('Post');
										$('#grid').data('kendoGrid').dataSource.read();
									}
								});
							} 
							else 
							{
								$.ajax
								({
									type : "POST",
									url : "./camConsolidationBills/setCamBillsStatus/" + dataItem.id + "/deactivate",
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
	 });

	
	function onRequestStart(e){
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
			} 
			else if (e.type == "create") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Cam consolidation details is created successfully");
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
						"Payment details is deleted successfully");
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
						"Payment details is updated successfully");
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

	/***************************  Custom message validation  **********************/

	var chargesFlag = false;
	(function($, kendo) {
		$('#paymentCalcLinesPopUp').hide();
		$.extend(true, kendo.ui.validator, {
			rules : { // custom rules

				camNameRequired : function(input, params) {
					if (input.attr("name") == "camName") {
						return $.trim(input.val()) !== "";
					}
					return true;
				},
				chargesTypeRequired : function(input, params) {
					if (input.attr("name") == "chargesType") {
						if(input.val()=="Custom"){
							chargesFlag = true;
                    	}else{
                    		chargesFlag = false;
                    	}
						return $.trim(input.val()) !== "";
					}
					return true;
				},
				/* fromDateRequired : function(input, params) {
					if (input.attr("name") == "fromDate") {
						return $.trim(input.val()) !== "";
					}
					return true;
				}, */
			   toDateRequired : function(input, params){
				if(chargesFlag){
			  		  if (input.attr("name") == "toDate")
			   			 {
			    			 return $.trim(input.val()) !== "";
			    		 }
			      }
			    return true;
			   }

			},
			messages : {
				//custom rules messages
				camNameRequired : "Cam name is required",
				chargesTypeRequired : "Charges type is required",
				/* fromDateRequired : "From date is required", */
			    toDateRequired: "Consolidate is required" 
			}
		});

	})(jQuery, kendo);
</script>
