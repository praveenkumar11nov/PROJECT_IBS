<%@include file="/common/taglibs.jsp"%>


<c:url value="onlinePayment/readUrl" var="readUrl"></c:url>
<%-- <c:url value="onlinePayment/GetPropertyNo" var="commonFilterForPropertyNumbersUrl"></c:url> --%>

<kendo:grid name="eventGrid"  resizable="true" pageable="true" selectable="true" sortable="true" scrollable="true"
	groupable="true" >
	<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10"></kendo:grid-pageable>
	<kendo:grid-filterable extra="false">
		<kendo:grid-filterable-operators>
			<kendo:grid-filterable-operators-string eq="Is equal to" />
			<kendo:grid-filterable-operators-date gt="Is after" lt="Is before"/>
		</kendo:grid-filterable-operators>

	</kendo:grid-filterable>
	<kendo:grid-editable mode="popup" />
		
	<kendo:grid-toolbar>
		<kendo:grid-toolbarItem name="uploadPayTimeTxnFile" text="Upload Batch File" />
		
	<%-- 	<kendo:grid-toolbarItem name="uploadPayUTxnFile" text="Upload PayU File" /> --%>
		
		<kendo:grid-toolbarItem name="getPayTmDetails" text="Reconciliation Data"></kendo:grid-toolbarItem>
		
		<kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilter()>Clear Filter</a>"/>
	</kendo:grid-toolbar>
	
	
	<kendo:grid-columns>
	<kendo:grid-column title="ID" field="id" width="100px" hidden="true"   filterable="false"/>
	<kendo:grid-column title="Account&nbsp;No&nbsp;" field="cust_ID" width="100px"    filterable="false">
	
	</kendo:grid-column>
	<kendo:grid-column title="Person Name" field="personName" width="100px" filterable="true">
	
	</kendo:grid-column>			
			<kendo:grid-column title="Property No.&nbsp;*" field="propertyNo"	width="100px" filterable="true">
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function ledgerTypeFilter(element) {
							element.kendoAutoComplete({
										placeholder : "Enter Property Number",
										dataSource : {
											transport : {
												read : "${commonFilterForPropertyNumbersUrl}"
											}
										}
									});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
			</kendo:grid-column>
  <kendo:grid-column title="Transaction&nbsp;ID&nbsp;*" field="txn_ID" width="160px"   filterable="true" >
	    <kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function StatusFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Connection Number",
									dataSource : {
										transport : {
											 read : "${commonFilterForConsumerGroupUrl}/connectionNo" 
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	    </kendo:grid-column>
	    
	     <kendo:grid-column title="MID&nbsp;*" field="mid" width="160px" filterable="true">
	    <kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function StatusFilter(element) {
								element.kendoAutoComplete({
											placeholder : "Enter Group ID",
											dataSource : {
												transport : {
													read : "${commonFilterForConsumerGroupUrl}/groupId" 
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	    </kendo:grid-column>
	    
	    <kendo:grid-column title="Transaction&nbsp;Date" field="txn_Date"  width="150px" filterable="true" >
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
	    	
	   
	   
	    
	    <kendo:grid-column title="Transaction&nbsp;Amount&nbsp;" field="txn_Amount" width="130px"    filterable="false" >
	    <kendo:grid-column-filterable >
					<%-- <kendo:grid-column-filterable-ui >
						
					</kendo:grid-column-filterable-ui> --%>
				</kendo:grid-column-filterable>
	    </kendo:grid-column>
		
		
		
		 <kendo:grid-column title="Issuing Bank" field="issuing_Bank" width="100px"    filterable="false" ></kendo:grid-column>
		 <kendo:grid-column title="Status" field="status" width="100px"    filterable="false" ></kendo:grid-column>
		 <kendo:grid-column title="Settled Amount" field="settled_Amt" width="100px"    filterable="false" ></kendo:grid-column>
		 <kendo:grid-column title="Commission Amount" field="commission_Amt" width="110px"    filterable="false" ></kendo:grid-column>
		 <kendo:grid-column title="Settled Date" field="settled_Date" width="150px"    filterable="false" ></kendo:grid-column>
		 
		 
		<%-- <kendo:grid-column title="&nbsp;" width="50px">
			   <kendo:grid-column-command>
				  <kendo:grid-column-commandItem name="edit"/>	
				  <kendo:grid-column-commandItem name="destroy" />		
			   </kendo:grid-column-command>
	  </kendo:grid-column>
	  		 --%>
	</kendo:grid-columns>

	<kendo:dataSource pageSize="20" >
	  <kendo:dataSource-transport>
			<kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json" />
		</kendo:dataSource-transport>
   
		<kendo:dataSource-schema>
			<kendo:dataSource-schema-model id="id">
				<kendo:dataSource-schema-model-fields>
				    <kendo:dataSource-schema-model-field name="id" type="number"/>
					<kendo:dataSource-schema-model-field name="txn_ID" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="mid" type="string" >
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="personName" type="string" >
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="propertyNo" type="string" >
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="txn_Date" type="string" >
					</kendo:dataSource-schema-model-field>
					
					
					
					<kendo:dataSource-schema-model-field name="txn_Amount" type="string">
					</kendo:dataSource-schema-model-field>
					
					
					
					
					<kendo:dataSource-schema-model-field name="payment_Mode" type="string" >
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="bank_Txn_ID" type="string" >
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="cust_ID" type="string" >
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="issuing_Bank" type="string" >
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="status" type="string" >
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="settled_Amt" type="string" >
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="status_type" type="string" >
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="commission_Amt" type="string" >
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="settled_Date" type="string" >
					</kendo:dataSource-schema-model-field>
					
					
					<kendo:dataSource-schema-model-field name="txn_Updated_Date" type="string" >
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="account_Id" type="string" >
					</kendo:dataSource-schema-model-field>
					

				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>

	</kendo:dataSource>
</kendo:grid>

<div id="uploadTransactionFileDialog" style="display: none;">
	<form id="uploadBatchFileForm">
		<table>
			<tr>
				<td>Upload PayTM Batch File</td>
				<td><kendo:upload name="files" id="batchFile"></kendo:upload></td>
		</table>
	</form>
</div>

<div id="uploadPayUTransactionFileDialog" style="display: none;">
	<form id="uploadBatchFileForm1">
		<table>
			<tr>
				<td>Upload PayU Batch File</td>
				<td><kendo:upload name="files" id="batchFile1"></kendo:upload></td>
		</table>
	</form>
</div>

<div id="reconciliationdiv" style="display: none; width: 270px;  height: 123px; overflow: hidden;" >
	<form id="reconciliationForm" data-role="validator" novalidate="novalidate">
	  
		<table style="height: 100px" >
		       
		    <tr>
		         <td></td>
		         <td></td>
		    </tr>
		
			<tr>
				<td>From Date</td>
				<td><input type="date" id="fromdateLedger" name="fromdateLedger"></td>
				<td></td>
				
			</tr>
              
              <tr>
               
		        <td>To Date</td>
				<td><input type="date" id="todateLedger" name="todateLedger"></td>
				
		      </tr>

			<tr>
				<td class="left" align="center" colspan="4" >
					<button type="submit" id="reConciliationData" class="k-button"
						style="padding-left: 10px">submit</button> <!-- <span id=printplaceholder style="display: none;"><img
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

<script>

$("#fromdateLedger").kendoDatePicker({
	
	 format: "dd-MM-yyyy"
});

$("#todateLedger").kendoDatePicker({
	
	 format: "dd-MM-yyyy"
});

$("#eventGrid")
.on(
		"click",
		".k-grid-uploadPayTimeTxnFile",
		function(e) {

			var result = securityCheckForActionsForStatus("./BillGeneration/GenerateBills/uploadBatchFile");
			if (result == "success") {
				var xlsDialog = $("#uploadTransactionFileDialog");
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


$("#batchFile").kendoUpload({
	multiple : true,
	upload : onUploadXLS,
	success : onDocSuccessBatch,
	error : errorRegardingUploadExcel,
	async : {
		saveUrl : "./BankTransactionHistory/uploadPayTimeTransactionHistory",
		autoUpload : true
	}
});

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

function onDocSuccessBatch() {
	alert("File Imported Successfully");
	 window.location.reload(); 
}

function errorRegardingUploadExcel(e){
	var windowOwner = $("#uploadTransactionFileDialog").data("kendoWindow");
	    windowOwner.close();

	
			var errorInfo="Uploaded file does not contain valid data";
			
			alert(errorInfo);
		/* 	$("#alertsBox").html("");
			$("#alertsBox").html(errorInfo);
			$("#alertsBox").dialog({
				modal : true,
				draggable: false,
				resizable: false,
				buttons : {
					"Close" : function() {
						$(this).dialog("close");
					 	window.location.reload();
					}
				}
			}); */	
		 
}
$("#eventGrid")
.on(
		"click",
		".k-grid-uploadPayUTxnFile",
		function(e) {

			var result = securityCheckForActionsForStatus("./BillGeneration/GenerateBills/uploadBatchFile");
			if (result == "success") {
				var xlsDialog = $("#uploadPayUTransactionFileDialog");
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


$("#batchFile1").kendoUpload({
	multiple : true,
	upload : onUploadXLS,
	success : onDocSuccessBatch,
	error : errorRegardingUploadExcel,
	async : {
		saveUrl : "./BankTransactionHistory/uploadPayUTransactionHistory",
		autoUpload : true
	}
});

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

function onDocSuccessBatch() {
	alert("File Imported Successfully");
	window.location.reload(); 
}

function errorRegardingUploadExcel(e){
	var windowOwner = $("#uploadPayUTransactionFileDialog").data("kendoWindow");
	    windowOwner.close();

	
			var errorInfo="Uploaded file does not contain valid data";
			
			alert(errorInfo);
		/* 	$("#alertsBox").html("");
			$("#alertsBox").html(errorInfo);
			$("#alertsBox").dialog({
				modal : true,
				draggable: false,
				resizable: false,
				buttons : {
					"Close" : function() {
						$(this).dialog("close");
					 	window.location.reload();
					}
				}
			}); */	
		 
}

$("#eventGrid").on("click", ".k-grid-getPayTmDetails", function(e) {
	var bbDialog = $("#reconciliationdiv");
	bbDialog.kendoWindow({
		width : "auto",
		height : "auto",
		modal : true,
		draggable : true,
		position : {
			top : 100
		},
		title : "Get Reconciliation Data"
	}).data("kendoWindow").center().open();

	bbDialog.kendoWindow("open");
});
$("#reconciliationForm").submit(function(e) {
	e.preventDefault();
});

/* var reConciliationDatavalidator = $("#reconciliationForm").kendoValidator().data(
"kendoValidator");
$("#reConciliationData").on("click", function() {

if (reConciliationDatavalidator.validate()) {

	reConciliationDetails();
}
}); */

$("#reConciliationData").click(
		function() {
	
	var presentdatePost = $("#fromdateLedger").val();
	var todatePost = $("#todateLedger").val();
	  if(presentdatePost == ""){
		  alert("From Date is Required");
		  return false;
	  }else if(todatePost == ""){
		  alert("To Date is Required");
		  return false;
	  }else if(presentdatePost>todatePost){
		  alert("From Date Should Be Less Than to Date");
		  return false;
	  }
	//$('#reConciliationData').hide();

	$.ajax({
		url : "./onlinePayTm_PayU/reConciliationDetails/"+presentdatePost+"/"+todatePost,
		type : 'GET',
		dataType : "json",
		async : false,
		contentType : "application/json; charset=utf-8",
		success : function(result) {
			
			postClose();
			var grid = $("#eventGrid").getKendoGrid();
			var data = new kendo.data.DataSource();
			grid.dataSource.data(result);
			grid.refresh();
			//window.location.reload();

		}

	});
});
function postClose() {

	var todcal = $("#reconciliationdiv");
	
	$("#fromdateLedger").val("");
	$("#todateLedger").val("");
	
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

function clearFilter() {
	//custom actions

	$("form.k-filter-menu button[type='reset']").slice()
			.trigger("click");
	var gridServiceMaster = $("#eventGrid").data("kendoGrid");
	gridServiceMaster.dataSource.read();
	gridServiceMaster.refresh();
}
</script>