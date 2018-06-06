<%@include file="/common/taglibs.jsp"%>

	<c:url value="/vendorInvoices/readVendorInvoiceUrl" var="readVendorInvoiceUrl" />
	<c:url value="/vendorInvoices/createVendorInvoiceUrl" var="createVendorInvoiceUrl" />
	<c:url value="/vendorInvoices/updateVendorInvoiceUrl" var="updateVendorInvoiceUrl" />
	
	<!-- URL TO READ VENDOR CONTRACT -->
	<c:url value="/vendorInvoice/readVendorContracts" var="readVendorContracts" />
	<!-- END FOR VENDOR CONTRACT URL -->
	
	
	<c:url value="/vendorInvoice/readAvailableVendorContractsFromVCLineItems" var="readAvailableVendorContractsFromVCLineItems"/>
	
	<c:url value="/vendorInvoice/getContractNameFilter" var="getContractNameFilter"/>
	
	<!-- URLS'S FOR VENDOR PAYMENTS CHILD GRID -->
	<c:url value="/vendorPayments/vendorPaymentsReadUrl" var="vendorPaymentsReadUrl"/>	
	<c:url value="/vendorPayments/vendorPaymentsCreateUrl" var="vendorPaymentsCreateUrl"/>	
	<c:url value="/vendorPayments/vendorPaymentsUpdateUrl" var="vendorPaymentsUpdateUrl"/>
	<c:url value="/vendorPayments/vendorPaymentsDestroyUrl" var="vendorPaymentsDestroyUrl"/>
	<!-- END FOR VENDOR PAYMENTS CHILD GRID -->
	
	<!-- URL'S FOR VENDOR INVOICES -->
	<c:url value="/vendorInvoice/vendorInvoiceUrl" var="vendorInvoiceUrl"/>
	<!-- END FOR VENDOR INVOICE URL'S -->
	
	<!-- URL'S FOR VENDOR INVOICE LINEITEMS -->
	<c:url value="/vendorInvoiceLineItems/vendorInvoiceLineItemsReadUrl" var="vendorInvoiceLineItemsReadUrl"/>
 	<c:url value="/vendorInvoiceLineItems/vendorInvoiceLineItemsCreateUrl" var="vendorInvoiceLineItemsCreateUrl"/>
 	<c:url value="/vendorInvocieLineItems/vendorInvoiceLineItemsUpdateUrl" var="vendorInvoiceLineItemsUpdateUrl"/>
 	<c:url value="/vendorInvocieLineItems/vendorInvoiceLineItemsDestroyUrl" var="vendorInvoiceLineItemsDestroyUrl"/>
	<!-- END FOR VENDOR INVOICE LINEITEMS -->
	
	<!-- URL'S FOR FILTER -->
	<c:url value="/vendorInvocie/getInvoiceNoFilter" var="getInvoiceNoFilter"/>
	<c:url value="/vendorInvocie/getInvoiceTitleFilter" var="getInvoiceTitleFilter"/>
	<c:url value="/vendorInvocie/getInvoiceDescriptionFilter" var="getInvoiceDescriptionFilter"/>
	<c:url value="/vendorInvocie/getInvoiceStatusFilter" var="getInvoiceStatusFilter"/>
	
	<!-- END FOR FILTER URL'S -->
	
	
	
	<kendo:grid name="vendorInvoicesGrid" change="onChangeVendorInvoicesGrid" detailTemplate="vendorPaymentsTemplate" pageable="true" resizable="true" filterable="true" sortable="true" reorderable="true" selectable="true" 
	 navigatable="true" scrollable="true" groupable="true" edit="addVendorInvoicesEvent" dataBound="vendorDataBound">	
		<kendo:grid-editable mode="popup"/>
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Vendor Invoices"/>
			<kendo:grid-toolbarItem name="vendorInvoiceTemplatesDetailsExport" text="Export To Excel" />
			  <kendo:grid-toolbarItem name="vendorInvoicePdfTemplatesDetailsExport" text="Export To PDF" /> 
			<kendo:grid-toolbarItem text="Clear Filter" name="Clear_Filter"/>
			<%-- <kendo:grid-toolbarItem text="Import"/>
			<kendo:grid-toolbarItem text="Export"/> --%>
		</kendo:grid-toolbar>
		
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" ></kendo:grid-pageable>
			<kendo:grid-filterable extra="false">
		 		<kendo:grid-filterable-operators>
		  			<kendo:grid-filterable-operators-string eq="Is equal to"/>
		  			<kendo:grid-filterable-operators-date lte="Date Before" gte="Date After"/>
				</kendo:grid-filterable-operators>			
		</kendo:grid-filterable>
		
		<kendo:grid-columns>
		    <kendo:grid-column title="Vendor InvoiceId&nbsp;*" field="viId" width="60px" hidden="true"/>
		    
		    <kendo:grid-column title="Vendor Contracts&nbsp;*" field="contractName" width="80px">
		    <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getContractNameFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
		    </kendo:grid-column>
		    
		    <kendo:grid-column title="Vendor Contract&nbsp;*" field="vcId" width="40px" hidden="true" editor="ReadVendorContracts"/>
		    		    
		    <kendo:grid-column title="Invoice Number&nbsp;*" field="invoiceNo" width="55px">
		    	<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getInvoiceNoFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
		    </kendo:grid-column>
		    
		    <kendo:grid-column title="Invoice Date&nbsp;*" field="invoiceDt" width="55px" format="{0:dd/MM/yyyy}" filterable="true">
		    	<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
	    					function fromDateFilter(element) {
								element.kendoDatePicker({
									format:"{0:dd/MM/yyyy}",
					            	
				            	});
					  		}    					
					  	</script>			
	    			</kendo:grid-column-filterable-ui>
	    	</kendo:grid-column-filterable>
		    </kendo:grid-column>
		    
		    <kendo:grid-column title="Invoice Title&nbsp;*" field="invoiceTitle" width="55px">
		      <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getInvoiceTitleFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
		    
		    </kendo:grid-column>
		    
		    <kendo:grid-column title="Description&nbsp;*" field="description" editor="invoiceDescription" width="55px">
		      <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getInvoiceDescriptionFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
		    </kendo:grid-column>
		    
		    <kendo:grid-column title="Status" field="status" width="55px">
		       <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getInvoiceStatusFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>		    
		    </kendo:grid-column>
		    
		    <kendo:grid-column title="Created By" field="createdBy" width="100px" hidden="true"/>
		    <kendo:grid-column title="Last Updated By" field="lastUpdatedBy" width="100px" hidden="true"/>
		    
		    <kendo:grid-column title="&nbsp;" width="45px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit" text="Edit"/>
				</kendo:grid-column-command>
			</kendo:grid-column>
			
			<kendo:grid-column title=""
				template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Activate#=data.vcId#'>#= data.status == 'Approved' ? 'Open' : 'Approve' #</a>"
				width="100px" />
		</kendo:grid-columns>
		
		<kendo:dataSource requestEnd="onRequestEnd" requestStart="onRequestStart">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-read url="${readVendorInvoiceUrl}" dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-create url="${createVendorInvoiceUrl}" dataType="json" type="GET" contentType="application/json" />
				<kendo:dataSource-transport-update url="${updateVendorInvoiceUrl}" dataType="json" type="GET" contentType="application/json" />
				<kendo:dataSource-transport-destroy url="${destroyVendorInvoiceUrl}" dataType="json" type="GET" contentType="application/json" />			
			</kendo:dataSource-transport>
			
			<kendo:dataSource-schema>
				<kendo:dataSource-schema-model id="viId">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="viId" type="number"/>
						<kendo:dataSource-schema-model-field name="vcId" type="number" defaultValue=""/>	
						<kendo:dataSource-schema-model-field name="VendorInvoiceDetails" type="number"/>
						<kendo:dataSource-schema-model-field name="invoiceNo" type="string"/>
						<kendo:dataSource-schema-model-field name="invoiceDt" type="date"/>
						<kendo:dataSource-schema-model-field name="invoiceTitle" type="string"/>
						<kendo:dataSource-schema-model-field name="description" type="string"/>
						<kendo:dataSource-schema-model-field name="status" type="string" defaultValue="Open"/>
						<kendo:dataSource-schema-model-field name="createdBy" type="string"/>
						<kendo:dataSource-schema-model-field name="lastUpdatedBy" type="string"/> 					
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
	</kendo:grid>
	
	<!-------------------------------------------------- FOR VENDOR PAYMENTS CHILD GRID  -------------------------------------------------->	
	<kendo:grid-detailTemplate id="vendorPaymentsTemplate">
	
	<kendo:tabStrip name="tabStrip_#=viId#">
	  <kendo:tabStrip-items>	
		<kendo:tabStrip-item text="Vendor Payments" selected="true">
		  <kendo:tabStrip-item-content>	
			<kendo:grid name="vendorPaymentsGrid_#=viId#" sortable="true" scrollable="true" edit="vendorPaymentsEvent" remove="deleteVendorPaymentEvent" reorderable="true" resizable="true">
			<kendo:grid-editable mode="popup" confirmation="Are you sure you want to remove this item?"  />
			<kendo:grid-toolbar>
				<kendo:grid-toolbarItem name="create" text="Add VendorPayments Details" />
			</kendo:grid-toolbar>

			<kendo:grid-columns>
				<kendo:grid-column title="VendorPayment Id" field="vipId" width="70px" hidden="true"/>				
				
				<kendo:grid-column title="Vendor Invoices" field="vendorInvoiceNoTitle" width="70px"/>
				<kendo:grid-column title="Vendor Invoices&nbsp;*" field="viId" editor="VendorInvoiceDetailsEditor" width="80px" hidden="true"/>
				
				<kendo:grid-column title="VendorPayment Date&nbsp;*" field="vipDt" format="{0: dd/MM/yyyy}" width="80px"/>
				<kendo:grid-column title="VendorPayment Type&nbsp;*" field="vipPayType" editor="vendorPayTypeEditor" width="80px"/>
				<kendo:grid-column title="VendorPayment Amount&nbsp;*" field="vipPayamount" format="{0:n0}" width="90px"/>
				<kendo:grid-column title="VendorPayment PayBy&nbsp;*" field="vipPayBy" editor="vendorPayByEditor" width="90px"/>
				<kendo:grid-column title="VendorPayment Details&nbsp;*" field="vipPaydetails" editor="vendorPayDetails" width="90px"/>
				<kendo:grid-column title="VendorPayment Notes&nbsp;*" field="vipNotes" editor="vendorPaymentNotes" width="90px"/>
				<kendo:grid-column title="CreatedBy" field="createdBy" width="85px" hidden="true"/>
		    	<kendo:grid-column title="LastUpdatedBy" field="lastUpdatedBy" width="85px" hidden="true"/>
				<%-- <kendo:grid-column title="LastUpdatedDt" field="lastUpdatedDt" width="70px" hidden="true"/> --%>
				
				<kendo:grid-column title="&nbsp;" width="95px">
					<kendo:grid-column-command>
						<kendo:grid-column-commandItem name="edit" />
						<kendo:grid-column-commandItem name="destroy"/>
					</kendo:grid-column-command>
				</kendo:grid-column>
			</kendo:grid-columns>

			<kendo:dataSource requestEnd="onVenodrPaymentsRequestEnd" requestStart="onVendorPaymentsRequestStart">
				<kendo:dataSource-transport>
					<kendo:dataSource-transport-read url="${vendorPaymentsReadUrl}/#=viId#" dataType="json" type="POST" contentType="application/json" />
					<kendo:dataSource-transport-create url="${vendorPaymentsCreateUrl}/#=viId#" dataType="json" type="GET" contentType="application/json" />
					<kendo:dataSource-transport-update url="${vendorPaymentsUpdateUrl}/#=viId#" dataType="json" type="GET" contentType="application/json" />
					<kendo:dataSource-transport-destroy url="${vendorPaymentsDestroyUrl}/" dataType="json" type="GET" contentType="application/json" />
					<%-- <kendo:dataSource-transport-parameterMap>
						<script>
							function parameterMap(options, type) {
								return JSON.stringify(options);
							}
						</script>
					</kendo:dataSource-transport-parameterMap> --%>
				</kendo:dataSource-transport>

				<kendo:dataSource-schema>
					<kendo:dataSource-schema-model id="vipId">
						<kendo:dataSource-schema-model-fields>							
							<kendo:dataSource-schema-model-field name="viId"/>
							<kendo:dataSource-schema-model-field name="vipDt" type="date"/>
							<kendo:dataSource-schema-model-field name="vipPayType" type="string"/>
							
							<kendo:dataSource-schema-model-field name="vipPayamount" type="number">
								<kendo:dataSource-schema-model-field-validation  min="1"/>
							</kendo:dataSource-schema-model-field>
							
							<kendo:dataSource-schema-model-field name="vipPayBy" type="string"/>
							<kendo:dataSource-schema-model-field name="vipPaydetails" type="string"/>
							<kendo:dataSource-schema-model-field name="vipNotes" type="string"/>							
							<kendo:dataSource-schema-model-field name="createdBy" type="string"/>
							<kendo:dataSource-schema-model-field name="lastUpdatedBy" type="string"/>
							<%-- <kendo:dataSource-schema-model-field name="lastUpdatedDt" type="date"/> --%>							
						</kendo:dataSource-schema-model-fields>
					</kendo:dataSource-schema-model>
				</kendo:dataSource-schema>
			</kendo:dataSource>
		</kendo:grid>
		</kendo:tabStrip-item-content>
	   </kendo:tabStrip-item>
	  <%-- </kendo:tabStrip-items> --%>
	<%-- </kendo:tabStrip> --%>
	
	<!-------------------------------------------   END FOR VENDOR PAYMENTS CHILD GRID  ------------------------------------------------->
		
		
		
	<!-------------------------------------------  FOR VEDNOR INVOICE LINEITEMS CHILD GRID  -------------------------------------------->	
	<%-- <kendo:tabStrip name="tabStrip_#=viId#"> --%>
	  <%-- <kendo:tabStrip-items> --%>	
		<kendo:tabStrip-item text="Vendor Invoice LineItems">
		  <kendo:tabStrip-item-content>	
				  
		  <kendo:grid name="vendorInvoiceLineItemsGrid_#=viId#" sortable="true" scrollable="true" edit="vendorInvoiceDetailsGridEvent" remove="deleteVcLineItemsEvent">
			<kendo:grid-editable mode="popup" confirmation="Are you sure you want to remove this item?"  />
			  <%-- <kendo:grid-toolbar>
				<kendo:grid-toolbarItem name="create" text="Add VendorInvoice LineItems"/>
			  </kendo:grid-toolbar> --%>

			  <kendo:grid-columns>				
				<kendo:grid-column title="Sl.No" field="vilSlno" format="{0:n0}" width="40px" />
				<kendo:grid-column title="Items" field="imName" width="80px"/>
				<kendo:grid-column title="Vendor LineItems Id" field="vilId" width="40px" hidden="true" />
				<kendo:grid-column title="Vendor Invoices" field="viId" width="40px" hidden="true" />
				<kendo:grid-column title="Item Name" field="imId" width="40px" hidden="true" />
				<kendo:grid-column title="Quantity&nbsp;*" field="quantity" format="{0:n0}" width="40px" />
				<kendo:grid-column title="Rate&nbsp;*" field="rate" format="{0:n0}" width="40px"/>
				<kendo:grid-column title="Amount&nbsp;*" field="amount" format="{0:n0}" width="40px"/>
				<kendo:grid-column title="Requisition Type" field="reqType" width="100px"/>
				<kendo:grid-column title="Created By" field="createdBy" width="100px" hidden="true"/>
		        <kendo:grid-column title="Last Updated By" field="lastUpdatedBy" width="100px" hidden="true"/>				
				
				<kendo:grid-column title="&nbsp;" width="100px">
					<kendo:grid-column-command>
						<kendo:grid-column-commandItem name="edit" />
						<kendo:grid-column-commandItem name="destroy"/>
					</kendo:grid-column-command>
				</kendo:grid-column>
			</kendo:grid-columns>

			<kendo:dataSource requestEnd="vendorInvoiceLineItemsRequestEnd" requestStart="vendorInvoiceLineItemsRequestStart">
				<kendo:dataSource-transport>
					<kendo:dataSource-transport-read url="${vendorInvoiceLineItemsReadUrl}/#=viId#" dataType="json" type="POST" contentType="application/json" />
					<kendo:dataSource-transport-create url="${vendorInvoiceLineItemsCreateUrl}/#=viId#" dataType="json" type="GET" contentType="application/json" />
					<kendo:dataSource-transport-update url="${vendorInvoiceLineItemsUpdateUrl}/#=viId#" dataType="json" type="GET" contentType="application/json" />
					<kendo:dataSource-transport-destroy url="${vendorInvoiceLineItemsDestroyUrl}/" dataType="json" type="GET" contentType="application/json" />					
				</kendo:dataSource-transport>

				<kendo:dataSource-schema>
					<kendo:dataSource-schema-model id="vilId">
						<kendo:dataSource-schema-model-fields>								
							<kendo:dataSource-schema-model-field name="viId" type="number"/>
							<kendo:dataSource-schema-model-field name="vilId" type="number"/>
							<kendo:dataSource-schema-model-field name="imId" type="number"/>
							
							<kendo:dataSource-schema-model-field name="vilSlno" type="number">
								<kendo:dataSource-schema-model-field-validation min="1" />
							</kendo:dataSource-schema-model-field>
							
							<kendo:dataSource-schema-model-field name="quantity" type="number">
							   <kendo:dataSource-schema-model-field-validation min="1" />
							</kendo:dataSource-schema-model-field>
							
							<kendo:dataSource-schema-model-field name="rate" type="number">
							   <kendo:dataSource-schema-model-field-validation min="1" />
							</kendo:dataSource-schema-model-field>
							
							<kendo:dataSource-schema-model-field name="amount" type="number">
							   <kendo:dataSource-schema-model-field-validation min="1" />
							</kendo:dataSource-schema-model-field>
							
							<kendo:dataSource-schema-model-field name="reqType" type="string"/>							
							<kendo:dataSource-schema-model-field name="createdBy" type="string"/>
							<kendo:dataSource-schema-model-field name="lastUpdatedBy" type="string"/>							
													
						</kendo:dataSource-schema-model-fields>
					</kendo:dataSource-schema-model>
				</kendo:dataSource-schema>
			</kendo:dataSource>
		   </kendo:grid>		  
		 </kendo:tabStrip-item-content>
		</kendo:tabStrip-item>
	 </kendo:tabStrip-items>
   </kendo:tabStrip>
		
		
	<!------------------------------------------  END FOR VENDOR INVOICE LINEITEMS CHILD GRID  ------------------------------------------->	
		
	</kendo:grid-detailTemplate>
	
	<!-- END FOR CHILD GRID VENDOR PAYMENTS -->
	
 
<div id="alertsBox" title="Alert"></div>
 <script>
 $("#vendorInvoicesGrid").on("click",".k-grid-vendorInvoiceTemplatesDetailsExport", function(e) {
	  window.open("./vendorInvoiceTemplate/vendorInvoiceTemplatesDetailsExport");
});	  

$("#vendorInvoicesGrid").on("click",".k-grid-vendorInvoicePdfTemplatesDetailsExport", function(e) {
	  window.open("./vendorInvoicePdfTemplate/vendorInvoicePdfTemplatesDetailsExport");
});
	 /*** CLEAR FILTER OPTION ***/
	 $("#vendorInvoicesGrid").on("click", ".k-grid-Clear_Filter", function()
	 {		
		    $("form.k-filter-menu button[type='reset']").slice().trigger("click");
			var grid = $("#vendorInvoicesGrid").data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
	 });
	 /*** END FOR CLEAR FILTER OPTION ***/
 	var SelectedItemMasterRowId = "";
 	var vendorStatus = "";
 	function onChangeVendorInvoicesGrid(arg) 
 	{
		var gview = $("#vendorInvoicesGrid").data("kendoGrid");
		var selectedItem = gview.dataItem(gview.select());
		SelectedItemMasterRowId = selectedItem.viId;
		vendorStatus = selectedItem.status;

		this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
	}
 
 	function vendorDataBound(e) {
		var data = this.dataSource.view();	    
	    var grid = $("#vendorInvoicesGrid").data("kendoGrid");
	    for (var i = 0; i < data.length; i++) {
	    	var currentUid = data[i].uid;
	        row = this.tbody.children("tr[data-uid='" + data[i].uid + "']");
	     	        
	        if(data[i].status=="Approved"){
	        	
	        	var currenRow = grid.table.find("tr[data-uid='" + currentUid+ "']");
				var reOpenButton = $(currenRow).find(".k-grid-edit");
				reOpenButton.hide();
	        }
	        
	    }
	}
 	 /************** ON REQUEST END FUNCTION  **********************/
	 function onRequestEnd(e) 
	 {
		 if (typeof e.response != 'undefined')
		 {
		 	if (e.response.status == "FAIL")
			{
				errorInfo = "";			
				errorInfo = e.response.result.invalid;			
				for (i = 0; i < e.response.result.length; i++) 
				{
					errorInfo += (i + 1) + ". "	+ e.response.result[i].defaultMessage + "\n";
				}
				if (e.type == "create") 
				{
					alert("Error: Creating the Vendor Invoice Details\n\n" + errorInfo);
				}
				if (e.type == "update") 
				{
					alert("Error: Updating the Vendor Invoice Details\n\n" + errorInfo);
				}
				var grid = $("#vendorInvoicesGrid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
				return false;
			}
		}
		if (e.type == "update" && !e.response.Errors) 
		{	
			e.sender.read();
			//alert("Vendor Invoice UPDATED successfully");
			$("#alertsBox").html("");
			$("#alertsBox").html("Vendor Invoice UPDATED successfully");
			$("#alertsBox").dialog({
				modal : true,
				draggable: false,
				resizable: false,
				buttons : {
					"Close" : function() {
						$(this).dialog("close");
					}
				}
			});
		}
		if (e.type == "create" && !e.response.Errors)
		{
			e.sender.read();
			//alert("Vendor Invoice Added Successfully");
			$("#alertsBox").html("");
			$("#alertsBox").html("Vendor Invoice Added Successfully");
			$("#alertsBox").dialog({
				modal : true,
				draggable: false,
				resizable: false,
				buttons : {
					"Close" : function() {
						$(this).dialog("close");
					}
				}
			});
		}
		if(e.type == "destroy" && !e.response.Errors)
		{
			//alert("Vendor Invoice Details Deleted Successfully");
			$("#alertsBox").html("");
			$("#alertsBox").html("Vendor Invoice Deleted Successfully");
			$("#alertsBox").dialog({
				modal : true,
				draggable: false,
				resizable: false,
				buttons : {
					"Close" : function() {
						$(this).dialog("close");
					}
				}
			});
			var grid = $("#vendorInvoicesGrid").data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
		}
	 }	
	 function onRequestStart(e)
	 {
		 	$('.k-grid-update').hide();
	        $('.k-edit-buttons')
	                .append(
	                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
	        $('.k-grid-cancel').hide();	
	        
		  /*  var grid= $("#vendorInvoicesGrid").data("kendoGrid");
		   grid.cancelRow(); */
	 }
	 /*********  END FOR ONREQUEST END  ********************/
 
 	 /******  TEXT AREA FOR INVOICE DESCRIPTION  ******/
	 function invoiceDescription(container, options) 
	 {
	      $('<textarea name="Invoice Description" data-text-field="description" data-value-field="description" data-bind="value:' + options.field + '" style="width: 148px; height: 46px;" required="true"/>')
	      .appendTo(container);
	      $('<span class="k-invalid-msg" data-for="Invoice Description"></span>').appendTo(container); 
	 }
	 /******   END FOR INVOICE DESCRIPTION *******/
 
	 $("#vendorInvoicesGrid").on("click", ".k-grid-add", function() 
	 {
	    /* if($("#vendorInvoicesGrid").data("kendoGrid").dataSource.filter())
	    {
		    $("form.k-filter-menu button[type='reset']").slice().trigger("click");
		 	var grid = $("#vendorInvoicesGrid").data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
		} */
	});
	 
 	 /******* EDITOR FUNCTION FOR ADD/CREATE RECORD ********/
	 function addVendorInvoicesEvent(e)
	 {
		 $(".k-window-title").text("Add Vendor Invoices");
		 $(".k-grid-update").text("Save");
		 
		 $('a[id="temPID"]').remove();
		 $('label[for=viId]').parent().hide();
		 $('div[data-container-for="viId"]').hide();
		 
		 $('label[for=vendorInvoiceDetails]').parent().remove();
		 $('div[data-container-for="vendorInvoiceDetails"]').remove(); 
		 
		 $('label[for=status]').parent().hide();
		 $('div[data-container-for="status"]').hide();
			
		 $('label[for=createdBy]').parent().hide();
		 $('div[data-container-for="createdBy"]').hide();
			
		 $('label[for=lastUpdatedBy]').parent().hide();
		 $('div[data-container-for="lastUpdatedBy"]').hide();
		 
		 $('label[for=contractName]').parent().hide();
		 $('div[data-container-for="contractName"]').hide();
		 
		 
		 $(".k-grid-cancel").click(function () 
		 {
			  var grid = $("#vendorInvoicesGrid").data("kendoGrid");
			  grid.dataSource.read();
			  grid.refresh();
		 });		 
		 $(".k-link").click(function () 
		 {
		     var grid = $("#vendorInvoicesGrid").data("kendoGrid");
			 grid.dataSource.read();
			 grid.refresh();
		     grid.refresh();
		 });
		 
		 if (e.model.isNew()) 
		 {		 
			 securityCheckForActions("./vendorManagement/vendorInvoice/createButton"); 
			 $(".k-window-title").text("Add Vendor Invoices");
			 $(".k-grid-update").text("Save");
		 }
		 else
		 {
			 securityCheckForActions("./vendorManagement/vendorInvoice/updateButton"); 	 
			 $(".k-window-title").text("Edit Vendor Invoices");
			 $(".k-grid-update").text("Update");
		 }
	 }
	 /*******END OF EDITOR FUNCTION FOR ADD/CREATE RECORD ********/
	 
	 /******* EDITOR FUNCTION FOR EDIT RECORD ********/
	 /* function editVendorInvoices(e)
	 {
		 $(".k-window-title").text("Update Vendor Invoices");
		 $('a[id="temPID"]').remove();
		 $('label[for=viId]').parent().hide();
		 $('div[data-container-for="viId"]').hide();
		 
		 $('label[for=vendorInvoiceDetails]').parent().remove();
		 $('div[data-container-for="vendorInvoiceDetails"]').remove(); 
		 
		 $('label[for=status]').parent().hide();
		 $('div[data-container-for="status"]').hide();
			
		 $('label[for=createdBy]').parent().hide();
		 $('div[data-container-for="createdBy"]').hide();
			
		 $('label[for=lastUpdatedBy]').parent().hide();
		 $('div[data-container-for="lastUpdatedBy"]').hide();
	 } */
	 /******* END OF EDITOR FUNCTION FOR EDIT RECORD ********/
	 
	 function ReadVendorContracts(container,options)
	 {
		 $('<input data-text-field="contractName" name="Vendor Contracts" id="test" required="true" data-value-field="vcId" data-bind="value:' + options.field + '"/>')
         .appendTo(container).kendoComboBox({
        	 placeholder : "Select Contract",
     		    template : '<table><border><tr><td rowspan=2><span class="k-state-default"></span></td>'
			+ '<td padding="5px"><span class="k-state-default"><b>#: data.vendorInvoiceDetails #</b></span><br></td></tr>'
			+'<tr><td rowspan=2 style="padding: 5px;font-size: 10px;"><b>#: data.contractNo #</b></td></tr></border></table>', 
		   dataSource: {       
	            transport: {
	                read: "${readAvailableVendorContractsFromVCLineItems}"
	            }
	        },
	        change : function (e) {
		        if (this.value() && this.selectedIndex == -1) {                    
		                alert("Contract doesn't exist!");
		                $("#test").data("kendoComboBox").value("");
		    }
			} 
	    });
		 $('<span class="k-invalid-msg" data-for="Vendor Contracts"></span>').appendTo(container);
	 }
	 
	 
	 
	 /*************  FOR ACTIVATE AND DE-Activate VendorPriceList Status  *************************/
	 $("#vendorInvoicesGrid").on("click", "#temPID", function(e) 
	 {
		 var button = $(this), enable = button.text() == "Approve";
		 var widget = $("#vendorInvoicesGrid").data("kendoGrid");
		 var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
		 
		 /* var status = dataItem.status;		
		 if(status == 'Approved')
	     {
			alert("Purchase-Order already Placed");
			return false;
		 }*/	
		 var result=securityCheckForActionsForStatus("./vendorManagement/vendorInvoice/activateButton");
	 	 if(result=="success")
		 {
	 		 if (enable) {
					$.ajax({
						type : "POST",
						dataType : "text",
						url : "./vendorInvoice/vendorInvoiceStatus/" + dataItem.id + "/Approved",
						success : function(response) {
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
							button.text('Open');
							$('#vendorInvoicesGrid').data('kendoGrid').dataSource.read();
						}
					});
				} else {
					$.ajax({
						type : "POST",
						dataType : "text",
						url : "./vendorInvoice/vendorInvoiceStatus/" + dataItem.id + "/Open",
						success : function(response) {
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
							button.text('Approve');
							$('#vendorInvoicesGrid').data('kendoGrid').dataSource.read();
						}
					});
				}
		 }
	 });
	 
	 /*************************************************  SCRIPT FOR VENDOR PAYMENTS GRID   **************************************************/
	 function deleteVendorPaymentEvent(e)
	 {
		 securityCheckForActions("./vendorManagement/vendorPayments/deleteButton");
	 }
	 
	 function vendorPaymentsEvent(e)
	 {
		 
		     
		
			 if (e.model.isNew()) 
			 {
				securityCheckForActions("./vendorManagement/vendorPayments/createButton");
				 
				$(".k-window-title").text("Add Vendor Payment Details");
				$(".k-grid-update").text("Save"); 
				
				$('label[for=vipId]').parent().hide();
				$('div[data-container-for="vipId"]').hide();
	
				$('label[for=createdBy]').parent().hide();
				$('div[data-container-for="createdBy"]').hide();
	
				$('label[for=lastUpdatedBy]').parent().hide();
				$('div[data-container-for="lastUpdatedBy"]').hide();
				
				$('label[for=lastUpdatedDt]').parent().hide();
				$('div[data-container-for="lastUpdatedDt"]').hide();
							
				$('label[for=vendorInvoiceNoTitle]').parent().hide();
				$('div[data-container-for="vendorInvoiceNoTitle"]').hide();
				
				
				
				if(vendorStatus!='Open'){
					var grid = $("#vendorPaymentsGrid_" + SelectedItemMasterRowId).data("kendoGrid");
					grid.cancelChanges();
					
					  $("#alertsBox").html("");
						$("#alertsBox").html("You can't add Vendor Payment Details,once Vendor Invoice is "+vendorStatus);
						$("#alertsBox").dialog({
							modal : true,
							draggable: false,
							resizable: false,
							buttons : {
								"Close" : function() {
									$(this).dialog("close");
									
								}
							}
						}); 
						
					/* alert("You can't add Vendor Payment Details,once Vendor Invoice is "+vendorStatus); */
				}
			 }
			 else
			 {
				 securityCheckForActions("./vendorManagement/vendorPayments/updateButton");
				 
				 $(".k-window-title").text("Edit Vendor Payment Details");
				 $(".k-grid-update").text("Update"); 
					
				 $('label[for=vipId]').parent().hide();
				 $('div[data-container-for="vipId"]').hide();
	
				 $('label[for=createdBy]').parent().hide();
				 $('div[data-container-for="createdBy"]').hide();
	
				 $('label[for=lastUpdatedBy]').parent().hide();
				 $('div[data-container-for="lastUpdatedBy"]').hide();
					
				 $('label[for=lastUpdatedDt]').parent().hide();
				 $('div[data-container-for="lastUpdatedDt"]').hide();
				 
				 $('label[for=vendorInvoiceNoTitle]').parent().hide();
				 $('div[data-container-for="vendorInvoiceNoTitle"]').hide();			 
			 }
		
	 }
	 
	 /*********  FOR VENDOR INVOICE DETAILS EDITOR  ********/
	 function VendorInvoiceDetailsEditor(container, options) 
	 {
		$('<input name="Vendor Invoice" id="vendor" data-text-field="vendorInvoiceNoTitle" data-value-field="viId" data-bind="value:' + options.field + '" required="true"/>').appendTo(container).kendoComboBox
		({
			autoBind : false,
			placeholder : "Select vendorInvoices",
			headerTemplate : '<div class="dropdown-header">'
				+ '<span class="k-widget k-header">Photo</span>'
				+ '<span class="k-widget k-header">Contact info</span>'
				+ '</div>',
			template : '<table><tr><td rowspan=2><span class="k-state-default"></span></td>'
				+ '<td align="left"><span class="k-state-default"><b>#: data.vendorInvoiceNoTitle #</b></span><br>'
				+ '</td></tr></table>',
			dataSource : {
				transport : {		
					read :  "${vendorInvoiceUrl}/"+SelectedItemMasterRowId,
				}
			},
			change : function (e) 
			{
		        if (this.value() && this.selectedIndex == -1) 
		        {                    
		            alert("Vendor doesn't exist!");
		            $("#vendor").data("kendoComboBox").value("");
		       	}
			} 
			});			
			$('<span class="k-invalid-msg" data-for="Vendor Invoice"></span>').appendTo(container);
	  }
	  /**********   END FOR VENODR INVOICE DETAILS  ***********/
	 
	 
	 /**********   PAYMENT TYPE FOR VENDOR  ***********/	 
	 function vendorPayTypeEditor(container, options)
	 {
		 var booleanData = [ {
				text : '--Select--',
				value : ""
			}, {
				text : 'Full',
				value : "Full"
			}, {
				text : 'Partial',
				value : "Partial"
			}];
			$('<input name="Payment Type" required="true"/>').attr('data-bind','value:vipPayType').appendTo(container).kendoDropDownList({
				dataSource : booleanData,
				dataTextField : 'text',
				dataValueField : 'value',
			}); 
			$('<span class="k-invalid-msg" data-for="Payment Type"></span>').appendTo(container); 
	 }
	 /*********  END FOR VENDOR PAYMENT TYPE  ********/
	 
	 
	 /************  PAYBY DETAILS  **********/
	 function vendorPayByEditor(container, options)
	 {
		 var booleanData = [ {
				text : '--Select--',
				value : ""
			},{
				text : 'Cash',
				value : "Cash"
			},{
				text : 'Cheque',
				value : "Cheque"
			},{
				text : 'RTGS',
				value : "RTGS"
			}];
			$('<input name="Payment Mode" required="true"/>').attr('data-bind','value:vipPayBy').appendTo(container).kendoDropDownList({
				dataSource : booleanData,
				dataTextField : 'text',
				dataValueField : 'value',
			});
			$('<span class="k-invalid-msg" data-for="Payment Mode"></span>').appendTo(container);
	 }
	 /************  END FOR PAYBY DETAILS  ************/	 
	 
	 
	 /********  FOR VENDOR PAYMENT DETAILS  ********/
	 function vendorPayDetails(container, options) 
	 {
		  $('<textarea name="Payment Details" data-text-field="vipPaydetails" data-value-field="vipPaydetails" data-bind="value:' + options.field + '" style="width: 148px; height: 46px;" required="true"/>')
		  .appendTo(container);
		  $('<span class="k-invalid-msg" data-for="Payment Details"></span>').appendTo(container);
	 }
	 /*******  END FOR VENDOR PAYMENT DETAILS  ********/
	 
	 /********  FOR VENDOR PAYMENT DETAILS  ********/
	 function vendorPaymentNotes(container, options) 
	 {
		  $('<textarea name="Payment Notes" data-text-field="vipNotes" data-value-field="vipNotes" data-bind="value:' + options.field + '" style="width: 148px; height: 46px;" required="true"/>')
		  .appendTo(container);
		  $('<span class="k-invalid-msg" data-for="Payment Notes"></span>').appendTo(container);
	 }
	 /*******  END FOR VENDOR PAYMENT DETAILS  ********/	  
	  
	  /*********   FOR ON REQUEST END FUNCTION FOR VENDOR PAYMENTS  ******/
	 function onVenodrPaymentsRequestEnd(e) 
	 {
		 if (typeof e.response != 'undefined')
		 {
		 	if (e.response.status == "FAIL")
			{
				errorInfo = "";			
				errorInfo = e.response.result.invalid;			
				for (i = 0; i < e.response.result.length; i++) 
				{
					errorInfo += (i + 1) + ". "	+ e.response.result[i].defaultMessage + "\n";
				}
				if (e.type == "create") 
				{
					alert("Error: Creating the Vendor Payments Details\n\n" + errorInfo);
				}
				if (e.type == "update") 
				{
					alert("Error: Updating the Vendor Payments Details\n\n" + errorInfo);
				}
				var grid = $('#vendorPaymentsGrid_' + SelectedItemMasterRowId).data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
				return false;
			}
		}
		if (e.type == "update" && !e.response.Errors) 
		{	
			e.sender.read();
			//alert("Vendor Payments UPDATED successfully");
			$("#alertsBox").html("");
			$("#alertsBox").html("Vendor Payments UPDATED successfully");
			$("#alertsBox").dialog({
				modal : true,
				draggable: false,
				resizable: false,
				buttons : {
					"Close" : function() {
						$(this).dialog("close");
					}
				}
			});
		}
		if (e.type == "create" && !e.response.Errors)
		{
			e.sender.read();
			//alert("Vendor Payments Added Successfully");
			$("#alertsBox").html("");
			$("#alertsBox").html("Vendor Payments Added Successfully");
			$("#alertsBox").dialog({
				modal : true,
				draggable: false,
				resizable: false,
				buttons : {
					"Close" : function() {
						$(this).dialog("close");
					}
				}
			});
		}
		if(e.type == "destroy" && !e.response.Errors)
		{
			//alert("Vendor Payments Details Deleted Successfully");
			$("#alertsBox").html("");
			$("#alertsBox").html("Vendor Payments Deleted Successfully");
			$("#alertsBox").dialog({
				modal : true,
				draggable: false,
				resizable: false,
				buttons : {
					"Close" : function() {
						$(this).dialog("close");
					}
				}
			});
			
			var grid = $('#vendorPaymentsGrid_' + SelectedItemMasterRowId).data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
			return false;
		}
	 }
	  /********   END FOR ON REQUEST END FUNCTION FOR VENDOR PAYMENTS   ********/
	  
	  function onVendorPaymentsRequestStart(e)
	  {
		  var grid= $('#vendorPaymentsGrid_' + SelectedItemMasterRowId).data("kendoGrid");
		  grid.cancelRow();
	  }
	  
	  
	  /**************  SCRIPT FOR VENDOR INVOICE DETAILS GRID  ***********************/
	  function deleteVcLineItemsEvent(e)
	  {
		  securityCheckForActions("./vendorManagement/vendorInvoiceLineItems/deleteButton"); 
	  }
	  
	  function vendorInvoiceDetailsGridEvent(e)	  
	  {
		  $(".k-window-title").text("Update VendorInvoice LineItems");
			 
		  $('label[for=vilId]').parent().hide();
		  $('div[data-container-for="vilId"]').hide();
		  
		  $('label[for=reqType]').parent().remove();
		  $('div[data-container-for="reqType"]').remove(); 
		  
		  $('label[for=imId]').parent().hide();
		  $('div[data-container-for="imId"]').hide();
		  
		  $('label[for=viId]').parent().hide();
		  $('div[data-container-for="viId"]').hide();		  
		  
		  $('label[for=createdBy]').parent().hide();
		  $('div[data-container-for="createdBy"]').hide();

		  $('label[for=lastUpdatedBy]').parent().hide();
		  $('div[data-container-for="lastUpdatedBy"]').hide();
		  
		  $('label[for=vilSlno]').parent().hide();
		  $('div[data-container-for="vilSlno"]').hide();
		  
		  $('label[for=imName]').parent().hide();
		  $('div[data-container-for="imName"]').hide();
		  
		  
		  $('input[name="imName"]').prop('readonly',true);
		  
		  $('input[name="quantity"]')
			.change(
					function() {
						var rateChange = $('input[name="rate"]').val();
						var qntChange = $('input[name="quantity"]').val();
						var totalAmount = rateChange*qntChange;
				

						$('input[name="amount"]').val(totalAmount);
						$('input[name="amount"]').prop('readonly',true);
					});

			$('input[name="rate"]')
			.change(
					function() {
						var rateChange = $('input[name="rate"]').val();
						var qntChange = $('input[name="quantity"]').val();
						var totalAmount = rateChange*qntChange;
						
						
						$('input[name="amount"]').val(totalAmount);
						$('input[name="amount"]').prop('readonly',true);
					});
		  
		  if (e.model.isNew()) 
		  {
			  securityCheckForActions("./vendorManagement/vendorInvoiceLineItems/createButton");  
		  }
		  else
		  {
			  securityCheckForActions("./vendorManagement/vendorInvoiceLineItems/updateButton");  	  
		  }
	  }
	  
	  /************** ON REQUEST END FUNCTION  **********************/
	  function vendorInvoiceLineItemsRequestEnd(e) 
	  {
	 	 if (typeof e.response != 'undefined')
		 {
		 	if (e.response.status == "FAIL")
			{
				errorInfo = "";			
				errorInfo = e.response.result.invalid;			
				for (i = 0; i < e.response.result.length; i++) 
				{
					errorInfo += (i + 1) + ". "	+ e.response.result[i].defaultMessage + "\n";
				}
				if (e.type == "create") 
				{
					alert("Error: Creating the Vendor InvoiceLineItems Details\n\n" + errorInfo);
				}
				if (e.type == "update") 
				{
					alert("Error: Updating the Vendor InvoiceLineItems Details\n\n" + errorInfo);
				}
				var grid = $('#vendorInvoiceLineItemsGrid_' + SelectedItemMasterRowId).data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
				return false;
			}
		}
		if (e.type == "update" && !e.response.Errors) 
		{	
			e.sender.read();
			//alert("VendorInvoice LineItems UPDATED successfully");
			$("#alertsBox").html("");
			$("#alertsBox").html("VendorInvoice LineItems Updated Successfully");
			$("#alertsBox").dialog
			({
				modal : true,
				draggable: false,
				resizable: false,
				buttons : 
				{
					"Close" : function() {
						$(this).dialog("close");
					}
				}
			});
		}
		if (e.type == "create" && !e.response.Errors)
		{
			e.sender.read();
			//alert("VendorInvoice Added Successfully");
			$("#alertsBox").html("");
			$("#alertsBox").html("VendorInvoice LineItems Created Successfully");
			$("#alertsBox").dialog
			({
				modal : true,
				draggable: false,
				resizable: false,
				buttons : 
				{
					"Close" : function() {
						$(this).dialog("close");
					}
				}
			});
		}
		if(e.type == "destroy" && !e.response.Errors)
		{
			//alert("VendorInvoice LineItems Details Deleted Successfully");			
			$("#alertsBox").html("");
			$("#alertsBox").html("VendorInvoice LineItems Deleted Successfully");
			$("#alertsBox").dialog
			({
				modal : true,
				draggable: false,
				resizable: false,
				buttons : 
				{
					"Close" : function() {
						$(this).dialog("close");
					}
				}
			});
			var grid = $('#vendorInvoiceLineItemsGrid_' + SelectedItemMasterRowId).data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
			return false;
		}
      }	
	  function vendorInvoiceLineItemsRequestStart(e)
	  {
		   var grid= $('#vendorInvoiceLineItemsGrid_' + SelectedItemMasterRowId).data("kendoGrid");
		   grid.cancelRow();
	  }
      /*********  END FOR ONREQUEST END  ********************/
	  
	  
	  
	  
	  /**************  SCRIPT END FOR VENDOR INVOICE DETAILS GRID  ************************/
	  
	 
	 /************************************************   END FOR VENDOR PAYMENTS GRID   **************************************************/
	 
	 /************ VALIDATOR FUNCTION **************/
	 var res = [];
    	(function($, kendo) {
    		$
    				.extend(
    						true,
    						kendo.ui.validator,
    						{
    							rules :
    							{ // custom rules          	
    								invoiceNoRegExp : function(input,params) 
    								{
    									if (input.filter("[name='invoiceNo']").length&& input.val()) 
    									{
    										return /^[a-zA-Z]+[._a-zA-Z0-9]*[a-zA-Z0-9]$/.test(input.val());
    									}
    									 return true;
    								},
    								invoiceNoNullValidator : function (input, params)
								     {
						                     if (input.attr("name") == "invoiceNo") {
						                      return $.trim(input.val()) !== "";
						                     }
						                     return true;
						             },
						             invoiceNullDateValidator : function (input, params)
								     {
						                     if (input.attr("name") == "invoiceDt") {
						                      return $.trim(input.val()) !== "";
						                     }
						                     return true;
						             },
						             invoiceNullTitleValidator : function (input, params)
								     {
						                     if (input.attr("name") == "invoiceTitle") {
						                      return $.trim(input.val()) !== "";
						                     }
						                     return true;
						             },
						             
						             /****** FOR CHLD GRID VENDOR PAYMENTS VALIDATIONS  */
						             vendorPaymentDate : function (input, params)
								     {
						                     if (input.attr("name") == "vipDt") {
						                      return $.trim(input.val()) !== "";
						                     }
						                     return true;
						             },
						             vendorPaymentAmount : function (input, params)
								     {
						                     if (input.attr("name") == "vipPayamount") {
						                      return $.trim(input.val()) !== "";
						                     }
						                     return true;
						             },
						             /*****  END FOR VENDOR PAYMENTS GRID  *******/
						             viSlNoValidator : function(input,params) 
	    							 {
	    									//check for the name attribute 
	    									 if (input.attr("name") == "vilSlno") {
	  						                   return $.trim(input.val()) !== "";
	  						                }
	  						                return true;
	    							 },
						             quantityValidator : function(input,params) 
	    							 {
	    								//check for the name attribute 
	    								 if (input.attr("name") == "quantity") {
	  						                  return $.trim(input.val()) !== "";
	  						               }
	  						               return true;
	    							},
	    							rateValidator : function(input,params) 
	    							{
	    								//check for the name attribute 
	    								 if (input.attr("name") == "rate") {
	  						                  return $.trim(input.val()) !== "";
	  						               }
	  						               return true;
	    							},
	    							amountValidator : function(input,params) 
	    							{
	    								//check for the name attribute 
	    								 if (input.attr("name") == "amount") {
	  						                  return $.trim(input.val()) !== "";
	  						               }
	  						               return true;
	    							},
	    							invoiceDate: function (input, params) 
						            {
					                     if (input.filter("[name = 'invoiceDt']").length && input.val()) 
					                     {                          
					                         var selectedDate = input.val();
					                         var todaysDate = $.datepicker.formatDate('dd/mm/yy', new Date());
					                         var flagDate = false;

					                         if ($.datepicker.parseDate('dd/mm/yy', selectedDate) <= $.datepicker.parseDate('dd/mm/yy', todaysDate)) 
					                         {
					                                flagDate = true;
					                         }
					                         return flagDate;
					                     }
					                     return true;
					                 },
							    },
    							messages : { 
    								invoiceNoRegExp: "Invoice No should be Alphabet Followed By Numbers",
    								invoiceNoNullValidator : "Invoice Number Required",
    								invoiceNullDateValidator : "Invoice Date Required",
    								invoiceNullTitleValidator : "Invoice Title Required",
    								vendorPaymentDate : "Payment Date Required",
    								vendorPaymentAmount : "Payment Amount Required",
    								quantityValidator : "Quantity Is Required",
    								rateValidator : "Rate Is Required",
    								amountValidator : "Amount Is Required",
    								viSlNoValidator : "Contract Sl.No Required",
    								invoiceDate : "Invoice Date Can Be Present date or past"
    								
    							}
    						});
    	})(jQuery, kendo);
	 /**** END FOR VALIDATOR FUNCTION  *********/
	 
 </script>
