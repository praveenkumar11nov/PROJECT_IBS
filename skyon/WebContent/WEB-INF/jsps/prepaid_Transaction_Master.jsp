<%@include file="/common/taglibs.jsp"%>

<c:url value="/prepaidservices/createUrl" var="servieccreateUrl"></c:url>
<c:url value="/prepaidservices/readUrl" var="serviecreadUrl"></c:url>
<c:url value="/prepaidservices/serviedestroyUrl" var="serviedestroyUrl"></c:url>

<c:url value="/prepaidservices/calculationCreateUrl" var="calculationCreateUrl"></c:url>
<c:url value="/prepaidservices/calculationReadUrl" var="calculationReadUrl"></c:url>
<c:url value="/prepaidservices/calculationupdateUrl" var="calculationupdateUrl"></c:url>
<c:url value="/common/getAllChecks" var="allChecksUrl" />

<c:url value="/prepaidCharges/createUrl" var="createUrl"></c:url>
<c:url value="/prepaidCharges/readUrl" var="readUrl"></c:url> 
<c:url value="/prepaidCharges/updateUrl" var="updateUrl"></c:url>
<c:url value="/prepaidCharges/destroyUrl" var="destroyUrl"></c:url>
<c:url value="/prepaidCharges/filter" var="commonFilterForTransactionMasterUrl"></c:url>

<kendo:grid name="prepaidGrid" remove="serviceMasterDeleteEvent" pageable="true" resizable="true" edit="ServiceMasterEvent" sortable="true" change="SeriveMasterChange" reorderable="true" selectable="true" scrollable="true" filterable="false" groupable="true" detailTemplate="ServiceMasterTemplate">

    <kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" refresh="true" info="true" previousNext="true">
		<kendo:grid-pageable-messages itemsPerPage="transaction codes per page" empty="No transaction code to display" refresh="Refresh all the transaction codes" 
			display="{0} - {1} of {2} New transaction codes" first="Go to the first page of transaction codes" last="Go to the last page of transaction codes" next="Go to the next page of transaction codes"
			previous="Go to the previous page of transaction codes"/>
	</kendo:grid-pageable>
	<kendo:grid-filterable extra="false">
		<kendo:grid-filterable-operators>
			<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains"/>
			<%-- <kendo:grid-filterable-operators-date gt="Is after" lt="Is before"/> --%>
		</kendo:grid-filterable-operators> 
	</kendo:grid-filterable>
	<kendo:grid-editable mode="popup"/>
		<kendo:grid-toolbar>
		      <kendo:grid-toolbarItem name="create" text="Add New Services" />
		     <%--   <kendo:grid-toolbarItem name="transactionTemplatesDetailsExport" text="Export To Excel" />
		            <kendo:grid-toolbarItem name="transactionPdfTemplatesDetailsExport" text="Export To PDF" /> --%>
		      <kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterTransactionMaster1()>Clear Filter</a>"/>
	    </kendo:grid-toolbar>
	<kendo:grid-columns>
	    
	    <kendo:grid-column title="ServiceID" field="serviceId" width="70px" hidden="true" filterable="false" sortable="false" />
	    
	    <kendo:grid-column title="Service&nbsp;Name&nbsp;*"  field="service_Name" filterable="true" width="80px">
			<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function apCodeFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Servicetype",
									dataSource : {
										transport : {
											read : "${commonFilterForTransactionMasterUrl}/service_Name"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
		<kendo:grid-column title="Valid&nbsp;From&nbsp;*" field="fromDate" format="{0:dd/MM/yyyy}" width="100px" filterable="false"/>
	    
	    <kendo:grid-column title="Valid&nbspTo&nbsp;*" field="toDate" width="100px" format="{0:dd/MM/yyyy}" filterable="false" >
	    </kendo:grid-column>	
		<kendo:grid-column title="Status" field="status" width="70px"  sortable="false" />	
	   <%-- <kendo:grid-column title="Created By" field="created_By" width="70px"  sortable="false" />
	   <kendo:grid-column title="Last Updated By" field="last_updated" width="70px" sortable="false" />
	   <kendo:grid-column title="Last Updated Date" field="last_updated_Dt" format="{0:dd/MM/yyyy}" width="70px" sortable="false" /> --%>
			
			
		<kendo:grid-column title="&nbsp;" width="80px">
			<kendo:grid-column-command>
			    <kendo:grid-column-commandItem name="destroy"/>
			</kendo:grid-column-command>
		</kendo:grid-column>
		
		<kendo:grid-column title=""
			template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Active#=data.serviceId#'>#= data.status == 'Active' ? 'Inactivate' : 'Activate' #</a>"
			width="70px" />
    
	</kendo:grid-columns>

	<kendo:dataSource pageSize="10" requestEnd="onRequestEndService" requestStart="onRequestStartService">
	        <kendo:dataSource-transport>
			<kendo:dataSource-transport-create url="${servieccreateUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-read url="${serviecreadUrl}" dataType="json" type="POST" contentType="application/json" />
			<%-- <kendo:dataSource-transport-update url="${serviecupdateUrl}" dataType="json" type="GET" contentType="application/json" /> --%>
			
			<kendo:dataSource-transport-destroy url="${serviedestroyUrl}/" dataType="json" type="GET" contentType="application/json" />
		</kendo:dataSource-transport> 
<!--  data="data" total="total" groups="data"   -->
		<kendo:dataSource-schema parse="parseServiceCode" >
			<kendo:dataSource-schema-model id="serviceId">
				<kendo:dataSource-schema-model-fields>
					<kendo:dataSource-schema-model-field name="serviceId" type="number"/>
					
					<kendo:dataSource-schema-model-field name="service_Name" type="string">
					</kendo:dataSource-schema-model-field>
					<%-- <kendo:dataSource-schema-model-field name="last_updated" type="string"/>
					
				    <kendo:dataSource-schema-model-field name="created_By" type="string"/>
					
					<kendo:dataSource-schema-model-field name="last_updated_Dt" type="string"/> --%>
					<kendo:dataSource-schema-model-field name="fromDate" type="date">
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="status" type="string"/>
					<kendo:dataSource-schema-model-field name="toDate" type="date">
					</kendo:dataSource-schema-model-field>
					
				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>

	</kendo:dataSource>

</kendo:grid>

<kendo:grid-detailTemplate id="ServiceMasterTemplate">
		
		<kendo:tabStrip name="tabStrip_#=serviceId#">
			<kendo:tabStrip-items>
			    <kendo:tabStrip-item text="Calculation Basis" selected="true" >
					<kendo:tabStrip-item-content>
						<div class="wethear" >
							<kendo:grid name="gridBasis_#=serviceId#" pageable="true" edit="CalculationMasterEvent" 
								resizable="true" sortable="true" reorderable="true" change="maychange"
								selectable="true" scrollable="true">
								<%--  <kendo:grid name="grid" pageable="true" resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true" > --%>
								<kendo:grid-pageable pageSize="10"></kendo:grid-pageable>
								<kendo:grid-editable mode="popup"
									confirmation="Are you sure you want to remove this Address?" />
								<kendo:grid-toolbar>
		                       <kendo:grid-toolbarItem name="create" text="Add New" />
		                <%--   <kendo:grid-toolbarItem name="transactionTemplatesDetailsExport" text="Export To Excel" />
		                      <kendo:grid-toolbarItem name="transactionPdfTemplatesDetailsExport" text="Export To PDF" /> --%>
	    </kendo:grid-toolbar>
	    <kendo:grid-columns>
	    
	    <kendo:grid-column title="CID" field="cid" width="70px" hidden="true" filterable="false" sortable="false" />
<%-- 	    <kendo:grid-column title="ServiceID" field="serviceId" width="70px" hidden="true" filterable="false" sortable="false" />
 --%>	    <kendo:grid-column title="CB&nbsp;Name&nbsp;*"  field="cbName" filterable="true" width="80px" editor="cbNameEditor">
			<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function apCodeFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Servicetype",
									dataSource : {
										transport : {
											read : "${commonFilterForTransactionMasterUrl}/service_Name"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			
	   <kendo:grid-column title="Created By" field="createdBy" width="70px"  sortable="false" />
	   <kendo:grid-column title="Last Updated By" field="lastUpdatedBy" width="70px" sortable="false" />
	   <kendo:grid-column title="Last Updated Date" field="lastUpdated_DT" format="{0:dd/MM/yyyy}" width="70px" sortable="false" />
			
			
		 <kendo:grid-column title="&nbsp;" width="140px">
			<kendo:grid-column-command>
			    <kendo:grid-column-commandItem name="edit"/>
			</kendo:grid-column-command>
		</kendo:grid-column>
	</kendo:grid-columns>

	<kendo:dataSource pageSize="10" requestEnd="onRequestEndCalculation" requestStart="onRequestStartCalculation">
	        <kendo:dataSource-transport>
			<kendo:dataSource-transport-create url="${calculationCreateUrl}/#=serviceId#" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-read url="${calculationReadUrl}/#=serviceId#" dataType="json" type="POST" contentType="application/json" />
			<kendo:dataSource-transport-update url="${calculationupdateUrl}/#=serviceId#" dataType="json" type="GET" contentType="application/json" />
			<%-- 
			<kendo:dataSource-transport-destroy url="${destroyUrl}/" dataType="json" type="GET" contentType="application/json" /> --%>
		</kendo:dataSource-transport> 
<!--  data="data" total="total" groups="data"   -->
		<kendo:dataSource-schema parse="parseCalculationCode" >
			<kendo:dataSource-schema-model id="cid">
				<kendo:dataSource-schema-model-fields>
					<kendo:dataSource-schema-model-field name="cid" type="number"/>
					
					<kendo:dataSource-schema-model-field name="cbName" type="string">
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="lastUpdatedBy" type="string"/>
					
				    <kendo:dataSource-schema-model-field name="createdBy" type="string"/>
					
					<kendo:dataSource-schema-model-field name="lastUpdated_DT" type="string"/>
					<%-- <kendo:dataSource-schema-model-field name="serviceId" type="number"/> --%>
					
					
				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>

	</kendo:dataSource>
							</kendo:grid>
						</div>
					</kendo:tabStrip-item-content>
				</kendo:tabStrip-item>
			
				<kendo:tabStrip-item text="Service Charges" >
					<kendo:tabStrip-item-content>
						<div class="wethear" >
							<kendo:grid name="gridCharges_#=serviceId#" pageable="true" edit="transactionMasterEvent" 
								resizable="true" sortable="true" reorderable="true" change="serviceChargesList"
								selectable="true" scrollable="true">
								<%--  <kendo:grid name="grid" pageable="true" resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true" > --%>
								<kendo:grid-pageable pageSize="10"></kendo:grid-pageable>
								<kendo:grid-editable mode="popup"
									confirmation="Are you sure you want to remove this Address?" />
								<kendo:grid-toolbar>
		                       <kendo:grid-toolbarItem name="create" text="Add New Prepaid Charges" />
		                <%--   <kendo:grid-toolbarItem name="transactionTemplatesDetailsExport" text="Export To Excel" />
		                      <kendo:grid-toolbarItem name="transactionPdfTemplatesDetailsExport" text="Export To PDF" /> --%>
	    </kendo:grid-toolbar>
	    <kendo:grid-columns>
	    
	    <kendo:grid-column title="TransactionId" field="transactionId" width="70px" hidden="true" filterable="false" sortable="false" />
<%-- 	    <kendo:grid-column title="ServiceID" field="serviceId" width="70px" hidden="true" filterable="false" sortable="false" />
 --%>	    <kendo:grid-column title="Service&nbsp;Name&nbsp;*"  field="service_Name" filterable="true" width="80px">
			<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function apCodeFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Servicetype",
									dataSource : {
										transport : {
											read : "${commonFilterForTransactionMasterUrl}/service_Name"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			
	    <kendo:grid-column title="Charge&nbsp;Name&nbsp;*" field="charge_Name" width="100px" filterable="true">
	    <kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function ledgerStatusFilter(element) {
								element.kendoAutoComplete({
											placeholder : "Enter Transaction Name",
											dataSource : {
												transport : {
													read : "${commonFilterForTransactionMasterUrl}/charge_Name"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
		</kendo:grid-column-filterable>
	    </kendo:grid-column>
	    
	    <kendo:grid-column title="Charge&nbsp;Type&nbsp;*" field="charge_Type"  width="110px" filterable="false" editor="chargeType"/>
	    <kendo:grid-column title="Rate&nbsp;*" field="rate" width="70px" filterable="true">
	    <kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function ledgerStatusFilter(element) {
								element.kendoAutoComplete({
											placeholder : "Enter Code",
											dataSource : {
												transport : {
													read : "${commonFilterForTransactionMasterUrl}/rate"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
		</kendo:grid-column-filterable>
	    </kendo:grid-column>
	    
	 <%--   <kendo:grid-column title="Valid&nbsp;From&nbsp;*" field="fromDate" format="{0:dd/MM/yyyy}" width="110px" filterable="false"/>
	    
	    <kendo:grid-column title="Valid&nbspTo&nbsp;*" field="toDate" width="110px" format="{0:dd/MM/yyyy}" filterable="false" >
	    </kendo:grid-column> --%>
	    <kendo:grid-column title="Calculation&nbsp;Basis&nbsp;*" field="cbname"  width="110px" filterable="false"/>
	    <kendo:grid-column title="Valid&nbsp;From&nbsp;*" field="validFrom" format="{0:dd/MM/yyyy}" width="100px" filterable="false"/>
	    		
	    <kendo:grid-column title="Valid&nbspTo&nbsp;*" field="validTo" width="100px" format="{0:dd/MM/yyyy}"   filterable="false" >
	    </kendo:grid-column>
		<kendo:grid-column title="&nbsp;" width="140px">
			<kendo:grid-column-command>
			    <kendo:grid-column-commandItem name="edit"/>
				<kendo:grid-column-commandItem name="destroy" />
			</kendo:grid-column-command>
		</kendo:grid-column>
	</kendo:grid-columns>

	<kendo:dataSource pageSize="10" requestEnd="onRequestEnd" requestStart="onRequestStart">
	        <kendo:dataSource-transport>
			<kendo:dataSource-transport-create url="${createUrl}/#=serviceId#" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-read url="${readUrl}/#=serviceId#" dataType="json" type="POST" contentType="application/json" />
			 <kendo:dataSource-transport-update url="${updateUrl}/#=serviceId#" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-destroy url="${destroyUrl}/" dataType="json" type="GET" contentType="application/json" />
			 
		</kendo:dataSource-transport> 
<!--  data="data" total="total" groups="data"   -->
		<kendo:dataSource-schema parse="parseTransactionCode" >
			<kendo:dataSource-schema-model id="transactionId">
				<kendo:dataSource-schema-model-fields>
					<kendo:dataSource-schema-model-field name="transactionId" type="number"/>
					
					
					<kendo:dataSource-schema-model-field name="service_Name" type="string">
					</kendo:dataSource-schema-model-field>
					
					
					<kendo:dataSource-schema-model-field name="charge_Name" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="rate" type="number" >
					</kendo:dataSource-schema-model-field>
					
					<%-- <kendo:dataSource-schema-model-field name="fromDate" type="date">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="toDate" type="date">
					</kendo:dataSource-schema-model-field> --%>
					
					<kendo:dataSource-schema-model-field name="cbname" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="charge_Type" type="string">
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="validFrom" type="date">
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="validTo" type="date">
					</kendo:dataSource-schema-model-field>
					<%-- <kendo:dataSource-schema-model-field name="serviceId" type="number"/> --%>
					
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



/*  ------------------------------------------------------PARENT---------------------------
 */
 $("#prepaidGrid").on("click", "#temPID", function(e) {
	 var button = $(this), enable = button.text() == "Activate";
		var widget = $("#prepaidGrid").data("kendoGrid");
		var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
		/*  var result=securityCheckForActionsForStatus("./Services/ServiceMaster/activeInactiveButton"); 
		  if(result=="success"){  */ 
					if (enable) 
					{
						$.ajax
						({
							type : "POST",
							url : "./prepaidservices/serviceStatus/" + dataItem.id + "/activate",
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
								button.text('Inactivate');
								$('#prepaidGrid').data('kendoGrid').dataSource.read();
							}
						});
					} 
					else 
					{
						$.ajax
						({
							type : "POST",
							url : "./prepaidservices/serviceStatus/" + dataItem.id + "/deactivate",
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
								button.text('Activate');
								$('#prepaidGrid').data('kendoGrid').dataSource.read();
							}
						});
					}
		       
 });
 
 function serviceMasterDeleteEvent(){
		securityCheckForActions("./Services/ServiceMaster/destroyButton");
		var conf = confirm("Are u sure want to delete this service details?");
		 if(!conf){
		  $("#prepaidGrid").data().kendoGrid.dataSource.read();
		   throw new Error('deletion aborted');
		 }
	}

 
 var SelectedRowId="";
 function SeriveMasterChange(arg) {
	 var gview = $("#prepaidGrid").data("kendoGrid");
 	 var selectedItem = gview.dataItem(gview.select());
 	 SelectedRowId = selectedItem.serviceId;
 	
 	 this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
     //alert("Selected: " + SelectedRowId);
}
 
 function maychange(arg) {
	 var gview = $("#gridBasis_"+SelectedRowId).data("kendoGrid");
 	 var selectedItem = gview.dataItem(gview.select());
 	 selectedItem.cid;
}
 
 var flagTransactionCode1="";
 function ServiceMasterEvent(e)
 {
 	/***************************  to remove the id from pop up  **********************/
 	$('div[data-container-for="serviceId"]').remove();
 	$('label[for="serviceId"]').closest('.k-edit-label').remove();
 	$('div[data-container-for="created_By"]').remove();
 	$('label[for="created_By"]').closest('.k-edit-label').remove();
 	$('div[data-container-for="last_updated"]').remove();
 	$('label[for="last_updated"]').closest('.k-edit-label').remove();
 	$('div[data-container-for="last_updated_Dt"]').remove();
 	$('label[for="last_updated_Dt"]').closest('.k-edit-label').remove();
 	$('div[data-container-for="status"]').remove();
 	$('label[for="status"]').closest('.k-edit-label').remove();
 	$(".k-edit-field").each(function () {
		$(this).find("#temPID").parent().remove();  
   	});
 	
 	e.container.find(".k-grid-cancel").bind("click", function () {
    	var grid = $("#prepaidGrid").data("kendoGrid");
	grid.dataSource.read();
	grid.refresh();
});
 	/* $('div[data-container-for="groupType"]').hide();
 	$('label[for="groupType"]').closest('.k-edit-label').hide();  */
 	
 /* 	$('label[for=camRate]').parent().hide();
 	$('div[data-container-for="camRate"]').hide(); */
 	/************************* Button Alerts *************************/
 	if (e.model.isNew()) 
     {
 		//securityCheckForActions("./Masters/TransactionMaster/createButton");
 		flagTransactionCode1=true;
 		//setApCode = $('input[name="rate"]').val("");
 		$(".k-window-title").text("Add New Services ");
 		$(".k-grid-update").text("Save");		
     }
 	else{
 		
 		//securityCheckForActions("./Masters/TransactionMaster/updateButton");
 		flagTransactionCode1=false;
 		/* flagTransactionCode=false;
 		
 		   var gview = $("#prepaidGrid").data("kendoGrid");
 		   var selectedItem = gview.dataItem(gview.select());
 		   var selectedType = selectedItem.typeOfService;
 		  
 		   if(selectedType=="CAM"){
 			   $('div[data-container-for="camRate"]').show();
 			   $('label[for="camRate"]').closest('.k-edit-label').show();
 				 
 		   }
 		   else{
 			   $('div[data-container-for="camRate"]').hide();
 			   $('label[for="camRate"]').closest('.k-edit-label').hide();
 				 
 		   } */
 		
 		
 		$(".k-window-title").text("Edit Service Master");
 	}
 	
 	$(".k-grid-update").click(function()
 	{
 		
 		var serviceName=$('input[name="service_Name"]').val();
 		var fromDate=$('input[name="fromDate"]').val();
		//alert(fromDate);
		var toDate=$('input[name="toDate"]').val();
 		
 	    if(serviceName == ""){
 			alert("Service Name is Required");
 			return false;
 		}
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
 		
 		 
 		
 	});
 }
 function onRequestStartService(e){
		$('.k-grid-update').hide();
	    $('.k-edit-buttons').append( '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
	    $('.k-grid-cancel').hide();		
			
	}

 function onRequestEndService(e) {
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
							"Error: Creating the transaction master \n\n : "
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
				var grid = $("#prepaidGrid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			} 
			else if (e.type == "create") {
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Service created successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});

				var grid = $("#prepaidGrid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			}
			else if (e.type == "destroy") {
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Service Details  deleted successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});

				var grid = $("#prepaidGrid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			} 
			
			if (e.response.status == "CHILD") {

				$("#alertsBox").html("");
				$("#alertsBox")
						.html(
								"Can't delete service  details, child record found");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				$('#prepaidGrid').data('kendoGrid').refresh();
				$('#prepaidGrid').data('kendoGrid').dataSource.read();
				return false;
			}
			
			if (e.response.status == "ActiveStatuserr") {

				$("#alertsBox").html("");
				$("#alertsBox")
						.html(
								"Active service  details cannot be deleted");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				$('#prepaidGrid').data('kendoGrid').refresh();
				$('#prepaidGrid').data('kendoGrid').dataSource.read();
				return false;
			}
		}

	}
 function clearFilterTransactionMaster1()
 {
    $("form.k-filter-menu button[type='reset']").slice().trigger("click");
    var gridStoreIssue = $("#prepaidGrid").data("kendoGrid");
    gridStoreIssue.dataSource.read();
    gridStoreIssue.refresh();
 }


 var serviceNameArray = [];

  function parseServiceCode(response) {   
      var data = response; 
     // alert(data);
      serviceNameArray = [];
 	 for (var idx = 0, len = data.length; idx < len; idx ++) {
 		var res1 = (data[idx].service_Name);
 		//var res2 = (data[idx].transactionCode);
 		serviceNameArray.push(res1);
 		//transactionCodeArray.push(res2);
 	 }  
 	 return response;
 }  

 // var flag=false;
  (function ($,kendo)
 		{
 	    $.extend(true,kendo.ui.validator,
 	    {
 	    	rules:
 	    	{
 	    		serviceNameUniquevalidation : function(input, params){
 					if(flagTransactionCode1){
 						if (input.filter("[name='service_Name']").length && input.val()) 
 						{
 							var flag = true;
 							$.each(serviceNameArray, function(idx1, elem1) {
 								if(elem1.toLowerCase() == input.val().toLowerCase())
 								{
 									flag = false;
 								}	
 							});
 							return flag;
 						}
 					}
 					return true;
 				}, 
  	    		
 	    		 
 	    		
 				serviceNameCharacterValidator: function (input,params){
 					if(input.filter("[name='service_Name']").length && input.val()){
 						return /^[a-zA-Z\s]*$/.test(input.val());
 					}
 					return true;
 				},
 				
 				
 				
 	    	},
 	    	messages:
 	    	{
 	    		//serviceNameValidation:"Service Name is Required",
 	    		serviceNameCharacterValidator: "Service Name Allows Only Characters",
 	    	    //chargesNameValidator : "Charge Name is Required",
 	    	    serviceNameUniquevalidation : "Service Name Already exist"
 	    	    
 	    	}	
 	    });
 })(jQuery,kendo); 

 /* -------------------------------------Child------------------------------------ */

 var flagTxnCode="";
 function CalculationMasterEvent(e)
 {
 	/***************************  to remove the id from pop up  **********************/
 	$('div[data-container-for="cid"]').remove();
 	$('label[for="cid"]').closest('.k-edit-label').remove();
 	$('div[data-container-for="lastUpdatedBy"]').remove();
 	$('label[for="lastUpdatedBy"]').closest('.k-edit-label').remove();
 	$('div[data-container-for="lastUpdated_DT"]').remove();
 	$('label[for="lastUpdated_DT"]').closest('.k-edit-label').remove();
 	$('div[data-container-for="createdBy"]').remove();
 	$('label[for="createdBy"]').closest('.k-edit-label').remove();
 /* 	$('div[data-container-for="serviceId"]').remove();
 	$('label[for="serviceId"]').closest('.k-edit-label').remove(); */
 	
 	/* $('div[data-container-for="groupType"]').hide();
 	$('label[for="groupType"]').closest('.k-edit-label').hide();  */
 	
 /* 	$('label[for=camRate]').parent().hide();
 	$('div[data-container-for="camRate"]').hide(); */
 	/************************* Button Alerts *************************/
 	if (e.model.isNew()) 
     {
 		//securityCheckForActions("./Masters/TransactionMaster/createButton");
 		flagTxnCode=true;
 		//setApCode = $('input[name="rate"]').val("");
 		$(".k-window-title").text("Add Calculation Basis ");
 		$(".k-grid-update").text("Save");		
     }
 	else{
 		
 		//securityCheckForActions("./Masters/TransactionMaster/updateButton");
 		flagTxnCode=false;
 		/* flagTransactionCode=false;
 		
 		   var gview = $("#prepaidGrid").data("kendoGrid");
 		   var selectedItem = gview.dataItem(gview.select());
 		   var selectedType = selectedItem.typeOfService;
 		  
 		   if(selectedType=="CAM"){
 			   $('div[data-container-for="camRate"]').show();
 			   $('label[for="camRate"]').closest('.k-edit-label').show();
 				 
 		   }
 		   else{
 			   $('div[data-container-for="camRate"]').hide();
 			   $('label[for="camRate"]').closest('.k-edit-label').hide();
 				 
 		   } */
 		
 		
 		$(".k-window-title").text("Edit Service Master");
 	}
 	
 	$(".k-grid-update").click(function()
 	{
 		
 		var cbName=$('input[name="cbName"]').val();
 	
 		
 	    if(cbName == ""){
 			alert("CB Name is Required");
 			return false;
 		}
 		
 		 
 		
 	});
 }
 
 
 function onRequestEndCalculation(e) {
		/* debugger; */

		if (typeof e.response != 'undefined') {
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
							"Error: Adding CB Name<br>" + errorInfo);
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
							"Error: Updating the Calculation Basis record<br>" + errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
				}  

				$('#gridBasis_'+SelectedRowId).data().kendoGrid.dataSource.read();
				/* var grid = $("#propertyGrid_"+SelectedRowId).data("kendoGrid");
				grid.dataSource.read();
				grid.refresh(); */
				return false;
			}

			else if (e.type == "create") 
			{
				$("#alertsBox").html("");
				$("#alertsBox").html("CB Name Created Successfully");
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
				$('#gridBasis_'+SelectedRowId).data().kendoGrid.dataSource.read();
			}

			 else if (e.type == "update") 
			{
				$("#alertsBox").html("");
				$("#alertsBox").html("CB Name updated successfully");
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
				$('#gridBasis_'+SelectedRowId).data().kendoGrid.dataSource.read();
			}
			
			if (e.response.status == "ONEENTRY") {

				$("#alertsBox").html("");
				$("#alertsBox")
						.html(
								"This Allows only one Entry");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				$('#prepaidGrid').data('kendoGrid').refresh();
				$('#prepaidGrid').data('kendoGrid').dataSource.read();
				return false;
			}
        /*
			else if (e.type == "destroy") 
			{
				$("#alertsBox").html("");
				$("#alertsBox").html("Property deleted successfully");
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
				$('#propertyGrid_'+SelectedRowId).data().kendoGrid.dataSource.read();
			}
 */

		}

	}


	function onRequestStartCalculation(e){
		$('.k-grid-update').hide();
	    $('.k-edit-buttons').append( '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
	    $('.k-grid-cancel').hide();		
			
	}


	var cbNameArray = [];

	 function parseCalculationCode(response) {   
	     var data = response; 
	    // alert(data);
	     cbNameArray = [];
		 for (var idx = 0, len = data.length; idx < len; idx ++) {
			var res1 = (data[idx].cbName);
			//var res2 = (data[idx].transactionCode);
			cbNameArray.push(res1);
			//transactionCodeArray.push(res2);
		 }  
		 return response;
	}  

	 (function ($,kendo)
		 		{
		 	    $.extend(true,kendo.ui.validator,
		 	    {
		 	    	rules:
		 	    	{
		 	    		cbNameUniquevalidation : function(input, params){
		 					if(flagTxnCode){
		 						if (input.filter("[name='cbName']").length && input.val()) 
		 						{
		 							var flag = true;
		 							$.each(cbNameArray, function(idx1, elem1) {
		 								if(elem1.toLowerCase() == input.val().toLowerCase())
		 								{
		 									flag = false;
		 								}	
		 							});
		 							return flag;
		 						}
		 					}
		 					return true;
		 				}, 
		  	    		
		 	    		 
		 	    		
		 				cbNameCharacterValidator: function (input,params){
		 					if(input.filter("[name='cbName']").length && input.val()){
		 						return /^[a-zA-Z\s]*$/.test(input.val());
		 					}
		 					return true;
		 				},
		 				
		 				
		 				
		 	    	},
		 	    	messages:
		 	    	{
		 	    		//serviceNameValidation:"Service Name is Required",
		 	    		cbNameCharacterValidator: "CB Name Allows Only Characters",
		 	    	    //chargesNameValidator : "Charge Name is Required",
		 	    	    cbNameUniquevalidation : "CB Name Already exist"
		 	    	    
		 	    	}	
		 	    });
		 })(jQuery,kendo);
 
 /* -----------------------------------------Service charges------------------------------ */
 var txnId=0.0;
 var chargeTp="";
 function serviceChargesList(arg) {
	 var gview = $("#gridCharges_"+SelectedRowId).data("kendoGrid");
 	 var selectedItem = gview.dataItem(gview.select());
 	txnId=selectedItem.transactionId;
 	chargeTp=selectedItem.charge_Type;
 	//alert(chargeTp);
}
 
 function cbNameEditor(container, options) {
		var res = (container.selector).split("=");
		var attribute = res[1].substring(0,res[1].length-1);
		
		$('<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '"required ="true" name = "'+attribute+'" id = "cbName"/>')
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
function chargeType(container, options) {
	var res = (container.selector).split("=");
	var attribute = res[1].substring(0,res[1].length-1);
	
	$('<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '"required ="true" name = "'+attribute+'" id = "charge_Type"/>')
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
 
 function selectModeType(e){
	 var d=$("#charge_Type").val();
	 //alert(d);
		
	 $('label[for="rate"]').text("Rate");
	 $('div[data-container-for="rate"]').show();
		$('label[for="rate"]').closest('.k-edit-label').show();
 }
var flagTransactionCode="";
function transactionMasterEvent(e)
{ // alert("child grid");
	/***************************  to remove the id from pop up  **********************/
	$('div[data-container-for="transactionId"]').remove();
	$('label[for="transactionId"]').closest('.k-edit-label').remove();
	$('div[data-container-for="service_Name"]').remove();
	$('label[for="service_Name"]').closest('.k-edit-label').remove();
	$('div[data-container-for="cbname"]').remove();
	$('label[for="cbname"]').closest('.k-edit-label').remove();
	 $('div[data-container-for="rate"]').hide();
		$('label[for="rate"]').closest('.k-edit-label').hide();
		$('div[data-container-for="serviceId"]').remove();
	 	$('label[for="serviceId"]').closest('.k-edit-label').remove();	
	
	/* $('div[data-container-for="groupType"]').hide();
	$('label[for="groupType"]').closest('.k-edit-label').hide();  */
	
/* 	$('label[for=camRate]').parent().hide();
	$('div[data-container-for="camRate"]').hide(); */
	/************************* Button Alerts *************************/
	if (e.model.isNew()) 
    {
		//securityCheckForActions("./Masters/TransactionMaster/createButton");
		flagTransactionCode=true;
		//setApCode = $('input[name="rate"]').val("");
		$(".k-window-title").text("Add New Transaction Master");
		$(".k-grid-update").text("Save");		
    }
	else{
		 $('div[data-container-for="rate"]').show();
			$('label[for="rate"]').closest('.k-edit-label').show();
		
		//securityCheckForActions("./Masters/TransactionMaster/updateButton");
		flagTransactionCode=false;
		/* flagTransactionCode=false;
		
		   var gview = $("#prepaidGrid").data("kendoGrid");
		   var selectedItem = gview.dataItem(gview.select());
		   var selectedType = selectedItem.typeOfService;
		  
		   if(selectedType=="CAM"){
			   $('div[data-container-for="camRate"]').show();
			   $('label[for="camRate"]').closest('.k-edit-label').show();
				 
		   }
		   else{
			   $('div[data-container-for="camRate"]').hide();
			   $('label[for="camRate"]').closest('.k-edit-label').hide();
				 
		   } */
		
		
		$(".k-window-title").text("Edit Transaction Master");
	}
	
	$(".k-grid-update").click(function()
	{
		var rate=$('input[name="rate"]').val();
		//alert(rate);
		var chargeName=$('input[name="charge_Name"]').val();
		//var serviceName=$('input[name="service_Name"]').val();
		/* var fromDate=$('input[name="fromDate"]').val();
		//alert(fromDate);
		var toDate=$('input[name="toDate"]').val(); */
		if(chargeName == ""){
			alert("Charge Name is Required");
			return false;
		}else if(rate == ""){
			alert("Rate is Required");
			return false;
		}
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
		
	});
}
function onRequestEnd(e) {
	/* debugger; */

	if (typeof e.response != 'undefined') {
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
						"Error: Assigning the Property<br>" + errorInfo);
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
						"Error: Updating the Owner record<br>" + errorInfo);
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
			}  

			$('#gridCharges_'+SelectedRowId).data().kendoGrid.dataSource.read();
			/* var grid = $("#propertyGrid_"+SelectedRowId).data("kendoGrid");
			grid.dataSource.read();
			grid.refresh(); */
			return false;
		}

		else if (e.type == "create") 
		{
			$("#alertsBox").html("");
			$("#alertsBox").html("Charges created Successfully");
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
			$('#gridCharges_'+SelectedRowId).data().kendoGrid.dataSource.read();
		}

		else if (e.type == "update") 
		{
			$("#alertsBox").html("");
			$("#alertsBox").html("Charges updated successfully");
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
			$('#gridCharges_'+SelectedRowId).data().kendoGrid.dataSource.read();
		}

		else if (e.type == "destroy") 
		{
			$("#alertsBox").html("");
			$("#alertsBox").html("Charges deleted successfully");
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
			$('#gridCharges_'+SelectedRowId).data().kendoGrid.dataSource.read();
		}


	}


}


function onRequestStart(e){
	$('.k-grid-update').hide();
    $('.k-edit-buttons').append( '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
    $('.k-grid-cancel').hide();		
		
}



var chargeNameArray = [];

 function parseTransactionCode(response) {   
     var data = response; 
    // alert(data);
     chargeNameArray = [];
	 for (var idx = 0, len = data.length; idx < len; idx ++) {
		var res1 = (data[idx].charge_Name);
		//var res2 = (data[idx].transactionCode);
		chargeNameArray.push(res1);
		//transactionCodeArray.push(res2);
	 }  
	 return response;
}  

// var flag=false;
 (function ($,kendo)
		{
	    $.extend(true,kendo.ui.validator,
	    {
	    	rules:
	    	{
	    		chargeNameUniquevalidation : function(input, params){
					if(flagTransactionCode){
						if (input.filter("[name='charge_Name']").length && input.val()) 
						{
							var flag = true;
							$.each(chargeNameArray, function(idx1, elem1) {
								if(elem1.toLowerCase() == input.val().toLowerCase())
								{
									flag = false;
								}	
							});
							return flag;
						}
					}
					return true;
				}, 
 	    		 /* serviceNameValidation: function (input,params){
	    			if(input.attr("name") == "service_Name"){
	    				return $.trim(input.val())!== "";
	    			}
	    			return true;
	    		}, 
	    		
	    		chargesNameValidator: function (input,params){
	    			if(input.attr("name") == "charge_Name"){
	    				return $.trim(input.val())! == "";
	    			}
	    			return true;
	    		},  */
	    		
	    		/* rateValidation: function (input,params){
	    			if(input.attr("name") == "rate"){
	    				return $.trim(input.val())! == "";
	    			}
	    			return true;
	    		}, */
	    		
	    		 
	    		/* 
				serviceNameCharacterValidator: function (input,params){
					if(input.filter("[name='service_Name']").length && input.val()){
						return /^[a-zA-Z\s]*$/.test(input.val());
					}
					return true;
				}, */
				
				chargeNameCharacterValidation: function (input,params){
					if(input.filter("[name='charge_Name']").length && input.val()){
						return /^[a-zA-Z\s]*$/.test(input.val());
					}
					return true;
				},
	    		
				chaargeNamelengthValidation: function (input ,params){
					if(input.filter("[name='charge_Name']").length && input.val()){
						return /^[\s\S]{1,20}$/.test(input.val());
					}
					return true;
				},
				
				
	    	},
	    	messages:
	    	{
	    		//serviceNameValidation:"Service Name is Required",
	    		//serviceNameCharacterValidator: "Service Name Allows Only Characters",
	    	    //chargesNameValidator : "Charge Name is Required",
	    	    chargeNameUniquevalidation : "Charge Name Already exist",
	    	    chargeNameCharacterValidation : "Charge Name Allows Only Characters",
	    	    chaargeNamelengthValidation : "Charge Name Allows max 20 Characters",
	    	   // rateValidation : "Rate is Required"
	    	}	
	    });
})(jQuery,kendo); 
 
 
 
 </script>