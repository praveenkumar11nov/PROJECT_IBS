<%@include file="/common/taglibs.jsp"%>

<!-- Urls for Common controller  -->
	<c:url value="/common/getAllChecks" var="allChecksUrl" />
	<c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />
	<c:url value="/common/getFilterAutoCompleteValues" var="filterAutoCompleteUrl" />
	<c:url value="/common/getPersonNamesFilterList" var="personNamesFilterUrl" />
	<c:url value="/common/getPersonListBasedOnPersonType" var="personNamesAutoBasedOnPersonTypeUrl" />
	
	<!-- Urls for Store Goods Receipt  -->
	<c:url value="/storeGoodsReceipt/read" var="readStoreGoodsReceiptUrl" />
	<c:url value="/storeGoodsReceipt/modify" var="modifyStoreGoodsReceiptUrl" />
	
	<c:url value="/storeGoodsReceipt/getStoreNamesFilterUrl" var="storeNamesFilterUrl" />
	<c:url value="/storeGoodsReceipt/getStoreMasterComboBoxUrl" var="storeMasterComboBoxUrl" />
	<c:url value="/storeGoodsReceipt/getContractNamesFilterUrl" var="contractNamesFilterUrl" />
	<c:url value="/storeGoodsReceipt/getVendorContractComboBoxUrl" var="vendorContractComboBoxUrl" />
	
	<!-- Urls for Store Goods Receipt Items  -->
	<c:url value="/storeGoodsReceiptItems/read" var="readStoreGoodsReceiptItemsUrl" />
	<c:url value="/storeGoodsReceiptItems/modify" var="modifyStoreGoodsReceiptItemsUrl" />
	
	<c:url value="/storeGoodsReceiptItems/getItemsSGRComboBoxUrl" var="itemsSGRComboBoxUrl"/>
	<c:url value="/storeGoodsReceiptItems/getUnitOfMeasurementSGRComboBoxUrl" var="unitOfMeasurementSGRComboBoxUrl" />
	
	
	<!-- ------------------------------------ Grid -------------------------------------- -->
	
	<kendo:grid name="gridStoreGoodsReceipt" edit="storeGoodsReceiptEvent" change="onChangeStoreGoodsReceipt"  detailTemplate="gridStoreGoodsReceiptTemplate" pageable="true" resizable="true"
	sortable="true" reorderable="true" selectable="true" scrollable="true" groupable="true" filterable="true" navigatable="true">
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" refresh="true" info="true" previousNext="true">
			<kendo:grid-pageable-messages itemsPerPage="Store Goods per page" empty="No Store Good to display" refresh="Refresh all the Store Goods" 
			display="{0} - {1} of {2} Store Goods" first="Go to the first page of Store Goods" last="Go to the last page of Store Goods" next="Go to the next page of Store Goods"
			previous="Go to the previous page of Store Goods"/>
		</kendo:grid-pageable> 
		<kendo:grid-filterable extra="false">
			<kendo:grid-filterable-operators>
				<kendo:grid-filterable-operators-string eq="Is equal to"/>
				<kendo:grid-filterable-operators-date gt="Is after" lt="Is before"/>
			</kendo:grid-filterable-operators>
		</kendo:grid-filterable>
		<kendo:grid-editable mode="popup"/>
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Store Goods" />
			<kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterStoreGoodsReceipt()><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>"/>
		</kendo:grid-toolbar>
		<kendo:grid-columns>
		<kendo:grid-column title="Receipt&nbsp;Number" field="stgrId" width="95px">
            <kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function stgrIdFilter(element) {
								element.kendoComboBox({
									placeholder : "Enter receipt no.",
									dataSource : {
										transport : {
											read : "${filterAutoCompleteUrl}/StoreGoodsReceipt/stgrId"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			<kendo:grid-column title="Store" field="storeName" width="75px">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function storeNameFilter(element) 
						   	{
								element.kendoComboBox({
									autoBind : true,
									dataTextField : "storeName",
									dataValueField : "storeName", 
									placeholder : "Enter store name",
									headerTemplate : '<div class="dropdown-header">'
										+ '<span class="k-widget k-header">Photo</span>'
										+ '<span class="k-widget k-header">Contact info</span>'
										+ '</div>',
									template : '<table><tr>'
										+ '<td align="left"><span class="k-state-default"><b>#: data.storeName #</b></span><br>'
										+ '<span class="k-state-default"><i>#: data.storeLocation #</i></span></td></tr></table>',
									dataSource : {
										transport : {		
											read :  "${storeNamesFilterUrl}"
										}
									} 
								});
						   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>	
			<kendo:grid-column title="Store" field="storeId" editor="storeMasterEditor" width="0px" hidden="true">
            </kendo:grid-column>
            <kendo:grid-column title="Vendor&nbsp;Contract" field="contractName" width="95px">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function storeNameFilter(element) 
						   	{
								element.kendoComboBox({
									autoBind : true,
									dataTextField : "contractName",
									dataValueField : "contractName", 
									placeholder : "Enter contract name",
									headerTemplate : '<div class="dropdown-header">'
										+ '<span class="k-widget k-header">Photo</span>'
										+ '<span class="k-widget k-header">Contact info</span>'
										+ '</div>',
									template : '<table><tr>'
										+ '<td align="left"><span class="k-state-default"><b>#: data.contractName #</b></span><br>'
										+ '<span class="k-state-default"><i>#: data.contractNo #</i></span></td></tr></table>',
									dataSource : {
										transport : {		
											read :  "${contractNamesFilterUrl}"
										}
									} 
								});
						   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>	
			<kendo:grid-column title="Vendor&nbsp;Contract" field="vcId" editor="vendorContractEditor" width="0px" hidden="true">
            </kendo:grid-column>
            <kendo:grid-column title="Received&nbsp;Date&nbsp;*" field="poRecDate" format="{0:dd/MM/yyyy}" width="0px" hidden="true"/>
            <kendo:grid-column title="Received&nbsp;Time&nbsp;*" field="poRecTime" format="{0:HH:mm}" editor="timeEditor" width="0px" hidden="true"/>
			<kendo:grid-column title="Received&nbsp;Date&nbsp;Time" field="poRecDt" format="{0:dd/MM/yyyy HH:mm}" editor="dateTimeEditor" width="120px" filterable="true">
				<kendo:grid-column-filterable ui="datetimepicker"></kendo:grid-column-filterable>
			</kendo:grid-column>
			<kendo:grid-column title="Received&nbsp;By" field="receivedByStaffName" width="80px">
			<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function personNameFilter(element) 
						   	{
								element.kendoComboBox({
									autoBind : true,
									dataTextField : "personName",
									dataValueField : "personName", 
									placeholder : "Enter staff name",
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
											read :  "${personNamesFilterUrl}/StoreGoodsReceipt/receivedByStaff"
										}
									} 
								});
						   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>	
			<kendo:grid-column title="Received&nbsp;By" field="receivedByStaffId" editor="personEditorStoreGoodsReceiptReceivedBy" width="0px" hidden="true"/>
			<kendo:grid-column title="Checked&nbsp;By" field="checkedByStaffName" width="80px">
			<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function personNameFilter(element) 
						   	{
								element.kendoComboBox({
									autoBind : true,
									dataTextField : "personName",
									dataValueField : "personName", 
									placeholder : "Enter staff name",
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
											read :  "${personNamesFilterUrl}/StoreGoodsReceipt/checkedByStaff"
										}
									} 
								});
						   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>	
			<kendo:grid-column title="Checked&nbsp;By" field="checkedByStaffId" editor="personEditorStoreGoodsReceiptCheckedBy" width="0px" hidden="true"/>
			<kendo:grid-column title="Shipping&nbsp;Document&nbsp;Number" field="shippingDocumentNumber" width="150px">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function shippingDocumentNumberFilter(element) {
								element.kendoComboBox({
									placeholder : "Enter shipping doc. no.",
									dataSource : {
										transport : {
											read : "${filterAutoCompleteUrl}/StoreGoodsReceipt/shippingDocumentNumber"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
            <kendo:grid-column title="Ledger&nbsp;Update&nbsp;Date&nbsp;Time" format="{0:dd/MM/yyyy HH:mm}" field="ledgerUpdateDt" width="145px" filterable="true">
				<kendo:grid-column-filterable ui="datetimepicker"></kendo:grid-column-filterable>
			</kendo:grid-column>
			<kendo:grid-column title="Created By" field="createdBy" width="0px" hidden="true"/>
			<kendo:grid-column title="Last Updated By" field="lastUpdatedBy" width="0px" hidden="true"/>
			<kendo:grid-column title="&nbsp;" width="70px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit"/>
				</kendo:grid-column-command>
			</kendo:grid-column>
        </kendo:grid-columns>
		<kendo:dataSource requestStart="onRequestStartStoreGoodsReceipt" requestEnd="onRequestEndStoreGoodsReceipt">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-read url="${readStoreGoodsReceiptUrl}" dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-create url="${modifyStoreGoodsReceiptUrl}/create" dataType="json" type="GET" contentType="application/json" />
				<kendo:dataSource-transport-update url="${modifyStoreGoodsReceiptUrl}/update" dataType="json" type="GET" contentType="application/json" />
			</kendo:dataSource-transport>
			<kendo:dataSource-schema parse="storeGoodsReceiptParse">
				<kendo:dataSource-schema-model id="stgrId">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="stgrId" type="number"/> 
						<kendo:dataSource-schema-model-field name="storeId"/>
						<kendo:dataSource-schema-model-field name="storeName" type="string"/>
						<kendo:dataSource-schema-model-field name="shippingDocumentNumber" type="string"/>
						<kendo:dataSource-schema-model-field name="vcId"/>
						<kendo:dataSource-schema-model-field name="contractName" type="string"/>
						<kendo:dataSource-schema-model-field name="poRecDate" type="date"/>
						<kendo:dataSource-schema-model-field name="poRecTime" defaultValue="00:00"/>
						<kendo:dataSource-schema-model-field name="poRecDt" type="date"/>
						<kendo:dataSource-schema-model-field name="receivedByStaffName" type="string"/>
						<kendo:dataSource-schema-model-field name="receivedByStaffId" />
						<kendo:dataSource-schema-model-field name="checkedByStaffName" type="string"/>
						<kendo:dataSource-schema-model-field name="checkedByStaffId" />
						<kendo:dataSource-schema-model-field name="ledgerUpdateDt" type="date"/>
						<kendo:dataSource-schema-model-field name="createdBy" type="string"/>
						<kendo:dataSource-schema-model-field name="lastUpdatedBy" type="string"/>
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
	</kendo:grid>
	
	<kendo:grid-detailTemplate id="gridStoreGoodsReceiptTemplate">
		<kendo:tabStrip name="tabStrip_#=stgrId#">
			<kendo:tabStrip-items>
				<kendo:tabStrip-item text="Store Items" selected="true">
                <kendo:tabStrip-item-content>
                    <div class="wethear">
                       <kendo:grid name="gridStoreGoodsReceiptItems_#=stgrId#" pageable="true" resizable="true" edit="storeGoodsReceiptItemsEvent"  dataBound="dataBoundstoreGoodsReceiptItems"  sortable="true" reorderable="true" change="onChangeStoreGoodsReceiptItems" selectable="true" scrollable="true" >
						<kendo:grid-pageable pageSize="5"  >
								<kendo:grid-pageable-messages itemsPerPage="Store Items per page" empty="No Store Item to display" refresh="Refresh all the Store Items" 
									display="{0} - {1} of {2} Store Items" first="Go to the first page of Store Items" last="Go to the last page of Store Items" next="Go to the next page of Store Items"
									previous="Go to the previous page of Store Items"/>
						</kendo:grid-pageable>
							<kendo:grid-editable mode="popup" confirmation="Are you sure you want to remove this Store Item?" />
							<kendo:grid-toolbar>
								<kendo:grid-toolbarItem name="create" text="Add Store Goods Item" />
							</kendo:grid-toolbar>
					        <kendo:grid-columns>	       

								<kendo:grid-column title="Item" field="imId" editor="itemsSGREditor" width="0px" hidden="true"/>
								<kendo:grid-column title="Item" field="imName" width="80px"/>
								<kendo:grid-column title="UOM&nbsp;Purchase" field="uomPurchase" width="100px"/>
								<kendo:grid-column title="UOM&nbsp;Issue" field="uomIssue"  width="80px"/>
	            				<kendo:grid-column title="Unit&nbsp;of&nbsp;Measurement" field="uomId" editor="unitOfMeasurementSGREditor" width="0px" hidden="true"/>
	            				<kendo:grid-column title="Item&nbsp;Type" field="imType" width="100px"/>
								<kendo:grid-column title="Item&nbsp;Quantity" field="itemQuantity" width="85px"/>
								<kendo:grid-column title="Rate&nbsp;per&nbsp;quantity" field="rate" width="120px"/>
								<kendo:grid-column title="Part&nbsp;Number" field="partNo"  width="90px"/>
								<kendo:grid-column title="Item&nbsp;Manufacturer" field="itemManufacturer" width="120px"/>
								<kendo:grid-column title="Item&nbsp;Manufacture&nbsp;Date" field="itemManufactureDate" format="{0:dd/MM/yyyy}" width="140px"/>
								<kendo:grid-column title="Item&nbsp;Expiry&nbsp;Date" field="itemExpiryDate" format="{0:dd/MM/yyyy}" width="120px"/>
								<kendo:grid-column title="Warranty&nbsp;Expiry&nbsp;Date" field="warrantyValidTill" format="{0:dd/MM/yyyy}" width="140px"/>
								<kendo:grid-column title="Special&nbsp;Instructions" field="specialInstructions" editor="textAreaEditor" width="140px"/>
					           	<kendo:grid-column title="Created By" field="createdBy" width="0px" hidden="true"/>
								<kendo:grid-column title="Last Updated By" field="lastUpdatedBy" width="0px" hidden="true"/>
								<kendo:grid-column title="Receipt Type" field="receiptType" width="90px"/>
								<kendo:grid-column title="Status" field="sgriStatus" filterable="false" width="80px"/>
								<kendo:grid-column title="Activation&nbsp;Date&nbsp;Time" format="{0:dd/MM/yyyy HH:mm}" field="activationDt" width="145px" filterable="true">
									<kendo:grid-column-filterable ui="datetimepicker"></kendo:grid-column-filterable>
								</kendo:grid-column> 
					            <kendo:grid-column title="&nbsp;" width="100px" >
					            	<kendo:grid-column-command>
					            		<kendo:grid-column-commandItem name="edit"/>
					            	</kendo:grid-column-command>
					            </kendo:grid-column>
<%-- 					            <kendo:grid-column title="&nbsp;" width="100px" >
					            	<kendo:grid-column-command>
					            		<kendo:grid-column-commandItem name="destroy"/>
					            	</kendo:grid-column-command>
					            </kendo:grid-column> --%>
					           
					           <kendo:grid-column title="&nbsp;" width="100px" />
					         						         	
					        </kendo:grid-columns>
					        <kendo:dataSource requestStart="onRequestStartStoreGoodsReceiptItems" requestEnd="onRequestEndStoreGoodsReceiptItems">
					            <kendo:dataSource-transport>
					                <kendo:dataSource-transport-read url="${readStoreGoodsReceiptItemsUrl}/#=stgrId#" dataType="json" type="POST" contentType="application/json" />
					                <kendo:dataSource-transport-create url="${modifyStoreGoodsReceiptItemsUrl}/create/#=stgrId#" dataType="json" type="GET" contentType="application/json" />
									<kendo:dataSource-transport-update url="${modifyStoreGoodsReceiptItemsUrl}/update/#=stgrId#" dataType="json" type="GET" contentType="application/json" />
									<kendo:dataSource-transport-destroy url="${modifyStoreGoodsReceiptItemsUrl}/delete/#=stgrId#" dataType="json" type="GET" contentType="application/json" />
					            </kendo:dataSource-transport>
					           	<kendo:dataSource-schema parse="storeGoodsReceiptItemsParse">
					                <kendo:dataSource-schema-model id="sgriId">
					                    <kendo:dataSource-schema-model-fields>
						                    <kendo:dataSource-schema-model-field name="sgriId" type="number"/>
						                    <kendo:dataSource-schema-model-field name="stgrId" type="number"/>					                    
						                    <kendo:dataSource-schema-model-field name="imId"/>
						                    <kendo:dataSource-schema-model-field name="imName"/>
						                    <kendo:dataSource-schema-model-field name="uomId"/>
						                    <kendo:dataSource-schema-model-field name="rate"/>
						                    <kendo:dataSource-schema-model-field name="itemQuantity" type="number">
						                    	<kendo:dataSource-schema-model-field-validation min="0"/>
						                    </kendo:dataSource-schema-model-field>
											<kendo:dataSource-schema-model-field name="itemManufacturer" type="string"/>
											<kendo:dataSource-schema-model-field name="itemManufactureDate" type="date" defaultValue=""/>
					                        <kendo:dataSource-schema-model-field name="itemExpiryDate" type="date" defaultValue=""/>
											<kendo:dataSource-schema-model-field name="warrantyValidTill" type="date" defaultValue=""/>
											<kendo:dataSource-schema-model-field name="specialInstructions" type="string"/>
											<kendo:dataSource-schema-model-field name="uomPurchase" type="string"/>
											<kendo:dataSource-schema-model-field name="uomIssue" type="string"/>
											<kendo:dataSource-schema-model-field name="partNo" type="string"/> 
						                    <kendo:dataSource-schema-model-field name="imType" type="string"/>
					                        <kendo:dataSource-schema-model-field name="createdBy" type="string"/>
											<kendo:dataSource-schema-model-field name="lastUpdatedBy" type="string"/>
											<kendo:dataSource-schema-model-field name="receiptType" type="string" defaultValue="New"/>
											<kendo:dataSource-schema-model-field name="sgriStatus" type="string" defaultValue="Inactive"/>
											<kendo:dataSource-schema-model-field name="activationDt" type="date"/>
											
	
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
	
<script type="text/javascript">

//-- ------------------------------------------ Store Goods Receipt Script  ------------------------------------------ -
	
	var gridStoreGoodsReceipt = "gridStoreGoodsReceipt";
	var SelectedRowIdStoreGoodsReceipt = "";
	var vcId = 0;
	var storeName = "";
	var contractName = "";
var quantity=0;
	function onChangeStoreGoodsReceipt(arg) 
	{
		 var gview = $("#gridStoreGoodsReceipt").data("kendoGrid");
	 	 var selectedItem = gview.dataItem(gview.select());
	 	 SelectedRowIdStoreGoodsReceipt = selectedItem.stgrId;
	 	 vcId = selectedItem.vcId;
	 	 storeName = selectedItem.storeName;
	 	 contractName = selectedItem.contractName;
	 	 this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
	     //alert("Selected: " + SelectedRowIdStoreGoodsReceipt);
	}
	
	function clearFilterStoreGoodsReceipt()
	{
		  $("form.k-filter-menu button[type='reset']").slice().trigger("click");
		  var gridStoreGoodsReceipt = $("#gridStoreGoodsReceipt").data("kendoGrid");
		  gridStoreGoodsReceipt.dataSource.read();
		  gridStoreGoodsReceipt.refresh();
	}
	
	 $("#gridStoreGoodsReceipt").on("click", ".k-grid-add", function(e) { 
     	
		 securityCheckForActions("./inventory/storeGoodsReceipt/createButton");
		 
		 /* if($("#gridStoreGoodsReceipt").data("kendoGrid").dataSource.filter()){
				
	    		//$("#grid").data("kendoGrid").dataSource.filter({});
	    		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
	 			var grid = $("#gridStoreGoodsReceipt").data("kendoGrid");
	 			grid.dataSource.read();
	 			grid.refresh();
	 			
	        } */   
		 
	 });
	 
	function storeGoodsReceiptEvent(e)
	{
		$('label[for="storeId"]').text("Store Name *");
		$('label[for="vcId"]').text("Vendor Contract Name *");
		$('label[for="receivedByStaffId"]').text("Received By Staff Name *");
		$('label[for="checkedByStaffId"]').text("Checked By Staff Name *");
		$('label[for="shippingDocumentNumber"]').text("Shipping Document Number *");
		
		var sessionUserLoginName = '<%=session.getAttribute("userId")%>';
		if (e.model.isNew()) 
	    {
			 $.ajax
				({
					type : "POST",
					url : "./storeGoodsReceipt/checkAllMinimumRequiredStoreGoodsReceiptConstraints",
					success : function(response) 
					{
						if(response != "success")
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
							var gridStoreGoodsReceipt = $("#gridStoreGoodsReceipt").data("kendoGrid");
							gridStoreGoodsReceipt.cancelRow();
						}	
					}
				});
			 
			$(".k-window-title").text("Add New Store Goods Record");
			$(".k-grid-update").text("Save");
			$('input[name="createdBy"]').val(sessionUserLoginName).change();
	    }
		else
		{
			securityCheckForActions("./inventory/storeGoodsReceipt/updateButton");
			
			$(".k-window-title").text("Edit Store Goods Details");
			
			$('div[data-container-for="storeId"]').remove();
			$('label[for="storeId"]').closest('.k-edit-label').remove();
			$('div[data-container-for="vcId"]').remove();
			$('label[for="vcId"]').closest('.k-edit-label').remove();
		}
		
		$('div[data-container-for="storeName"]').remove();
		$('label[for="storeName"]').closest('.k-edit-label').remove();
		$('div[data-container-for="contractName"]').remove();
		$('label[for="contractName"]').closest('.k-edit-label').remove();
		$('div[data-container-for="receivedByStaffName"]').remove();
		$('label[for="receivedByStaffName"]').closest('.k-edit-label').remove();
		$('div[data-container-for="checkedByStaffName"]').remove();
		$('label[for="checkedByStaffName"]').closest('.k-edit-label').remove();
		
		$('input[name="lastUpdatedBy"]').val(sessionUserLoginName).change();
		
		$('label[for="stgrId"]').parent().remove();
		$('div[data-container-for="stgrId"]').hide();
		
		$('label[for="poRecDt"]').parent().remove();
		$('div[data-container-for="poRecDt"]').hide();
	      
		$('label[for="ledgerUpdateDt"]').parent().remove();
		$('div[data-container-for="ledgerUpdateDt"]').hide();
		
		$('label[for="createdBy"]').parent().remove();
		$('div[data-container-for="createdBy"]').hide();
		
		$('label[for="lastUpdatedBy"]').parent().remove();
		$('div[data-container-for="lastUpdatedBy"]').hide();
		
		$('.k-edit-field .k-input').first().focus();
		
		var grid = this;
		e.container.on("keydown", function(e) {        
	        if (e.keyCode == kendo.keys.ENTER) {
	          $(document.activeElement).blur();
	          grid.saveRow();
	        }
	      });
		
		//CLIENT SIDE VALIDATION FOR MULTI SELECT
 		$(".k-grid-update").click(function () 
		{
 			
		}); 
		
	}
	
	//register custom validation rules
	(function($, kendo) {$.extend(true,
						kendo.ui.validator,
						{
							rules : { // custom rules
								   poRecDate_blank : function(input, params){
									if ((input.attr("name") == "poRecDate") && (gridStoreGoodsReceipt == "gridStoreGoodsReceipt"))
									{
										return $.trim(input.val()) !== "";
									}
									return true;
								   },
								   poRecDateValidation: function (input, params) 
						             {
									   	 if (input.attr("name") == "poRecDate")
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
					             poRecTimeValidation: function (input, params) 
					             {               	
					                 if (input.filter("[name='Received time']").length && input.val() ) 
					                 {
					                	var TimeReg = /^([01]?[0-9]|2[0-3]):[0-5][0-9]$/;
					                	if(!TimeReg.test(input.val()))
					                	{
					                		 return false;
					                	}
					                 }        
					                 return true;
					             },
					             shippingDocumentNumber_blank : function(input, params){
									if ((input.attr("name") == "shippingDocumentNumber") && (gridStoreGoodsReceipt == "gridStoreGoodsReceipt"))
									{
										return $.trim(input.val()) !== "";
									}
									return true;
								   },
								   shippingDocumentNumbervalidation: function (input, params) 
						            {               	
										if ((input.attr("name") == "shippingDocumentNumber") && (gridStoreGoodsReceipt == "gridStoreGoodsReceipt") && (input.val().length > 100)) 
						            	{
						                		 return false;
						                }        
						                return true;
						             }
							},
							messages : {
								poRecDate_blank:"Received date is required",
								poRecDateValidation:"Received date must be selected from the past",
								poRecTimeValidation:"Invalid Time ( valid eg: 'HH:mm' - 14:25 and range b/w 00:00 - 23:59)",
								shippingDocumentNumber_blank:"Shipping Document Number is required",
								shippingDocumentNumbervalidation:"Maximum 100 characters are allowed to enter"
							}
						});

	})(jQuery, kendo);
	//End Of Validation
	
	function storeMasterEditor(container, options) 
   	{
		$('<input name="Store" id="store" data-text-field="storeName" data-value-field="storeId" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoComboBox({
			autoBind : false,
			placeholder : "Select store",
			headerTemplate : '<div class="dropdown-header">'
				+ '<span class="k-widget k-header">Photo</span>'
				+ '<span class="k-widget k-header">Contact info</span>'
				+ '</div>',
			template : '<table><tr>'
				+ '<td align="left"><span class="k-state-default"><b>#: data.storeName #</b></span><br>'
				+ '<span class="k-state-default"><i>#: data.storeLocation #</i></span></td></tr></table>',
			dataSource : {
				transport : {		
					read :  "${storeMasterComboBoxUrl}"
				}
			},
			change : function (e) {
	            if (this.value() && this.selectedIndex == -1) {                    
					alert("Store doesn't exist!");
	                $("#store").data("kendoComboBox").value("");
	        	}
		    } 
		});
		
		$('<span class="k-invalid-msg" data-for="Store"></span>').appendTo(container);
   	}
	
	function vendorContractEditor(container, options) 
   	{
		$('<input name="Contract" id="contract" data-text-field="contractName" data-value-field="vcId" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoComboBox({
			cascadeFrom : "store",
			autoBind : false,
			placeholder : "Select contract",
			headerTemplate : '<div class="dropdown-header">'
				+ '<span class="k-widget k-header">Photo</span>'
				+ '<span class="k-widget k-header">Contact info</span>'
				+ '</div>',
			template : '<table><tr>'
				+ '<td align="left"><span class="k-state-default"><b>#: data.contractName #</b></span><br>'
				+ '<span class="k-state-default"><i>#: data.contractNo #</i></span></td></tr></table>',
			dataSource : {
				transport : {		
					read :  "${vendorContractComboBoxUrl}"
				}
			},
			change : function (e) {
	            if (this.value() && this.selectedIndex == -1) {                    
					alert("Contract doesn't exist!");
	                $("#contract").data("kendoComboBox").value("");
	        	}
		    } 
		});
		
		$('<span class="k-invalid-msg" data-for="Contract"></span>').appendTo(container);
   	}
	
	function personEditorStoreGoodsReceiptReceivedBy(container, options) 
   	{
		$('<input name="Received by" id="person1" data-text-field="personName" data-value-field="id" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoComboBox({
			autoBind : false,
			placeholder : "Select staff",
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
					read :  "${personNamesAutoBasedOnPersonTypeUrl}/Staff"
				}
			},
			change : function (e) {
	            if (this.value() && this.selectedIndex == -1) {                    
					alert("Staff doesn't exist!");
	                $("#person1").data("kendoComboBox").value("");
	        	}
		    } 
		});
		
		$('<span class="k-invalid-msg" data-for="Received by"></span>').appendTo(container);
   	}
	
	function personEditorStoreGoodsReceiptCheckedBy(container, options) 
   	{
		$('<input name="Checked by" id="person2" data-text-field="personName" data-value-field="id" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoComboBox({
			autoBind : false,
			placeholder : "Select staff",
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
					read :  "${personNamesAutoBasedOnPersonTypeUrl}/Staff"
				}
			},
			change : function (e) {
	            if (this.value() && this.selectedIndex == -1) {                    
					alert("Staff doesn't exist!");
	                $("#person2").data("kendoComboBox").value("");
	        	}
		    } 
		});
		
		$('<span class="k-invalid-msg" data-for="Checked by"></span>').appendTo(container);
   	}
	
	function storeGoodsReceiptParse (response) {   
	    $.each(response, function (idx, elem) {

	    	if(elem.ledgerUpdateDt == null)
	    	{
	    		elem.ledgerUpdateDt = "";
	    	}	
	    	else
	    	{	
            	elem.ledgerUpdateDt = kendo.parseDate(new Date(elem.ledgerUpdateDt),'dd/MM/yyyy HH:mm');
	    	}

            	elem.poRecDt = kendo.parseDate(new Date(elem.poRecDt),'dd/MM/yyyy HH:mm');
            
        });
        return response;
	}

	function dateTimeEditor(container, options) {
	    $('<input data-text-field="' + options.field + '" data-value-field="' + options.field + '" data-bind="value:' + options.field + '" data-format="' + options.format + '"/>')
	            .appendTo(container)
	            .kendoDateTimePicker({});
	}
	
	function timeEditor(container, options) 
	{
		    $('<input name="Received time" data-text-field="' + options.field + '" data-value-field="' + options.field + '" data-bind="value:' + options.field + '" data-format="' + options.format + '" required="true"/>')
		            .appendTo(container)
		            .kendoTimePicker({});
		    
		    $('<span class="k-invalid-msg" data-for="Received time"></span>').appendTo(container);
	}

	function onRequestStartStoreGoodsReceipt(e)
	{
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();
        
		/* var grid = $("#gridStoreGoodsReceipt").data("kendoGrid");
		if(grid != null)
		{
			grid.cancelRow();
		} */	
	} 
	
	function onRequestEndStoreGoodsReceipt(e) 
	{
		displayMessage(e, "gridStoreGoodsReceipt", "Store Goods");
	}	

	//- ------------------------------------------ Store Goods Receipt Items Script   ------------------------------------------ --

	var SelectedImId = "";
	
	function onChangeStoreGoodsReceiptItems(arg) 
	{
		 var gridStoreGoodsReceiptItems = $("#gridStoreGoodsReceiptItems_"+SelectedRowIdStoreGoodsReceipt).data("kendoGrid");
	 	 var selectedItem = gridStoreGoodsReceiptItems.dataItem(gridStoreGoodsReceiptItems.select());
	 	 SelectedImId = selectedItem.imId;
	 	 this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
	     //alert("Selected: " + SelectedRowIdStoreGoodsReceipt);
	}
	 
	function storeGoodsReceiptItemsEvent(e)
	{
		$('label[for="imId"]').text("Item Name *");
		$('label[for="uomId"]').text("Units of Measurement *");
		$('label[for="itemQuantity"]').text("Item Quantity *");
		$('label[for="itemManufacturer"]').text("Item Manufacturer *");
		$('label[for="itemManufactureDate"]').text("Item Manufacture Date *");
		$('label[for="partNo"]').text("Part No *");
		
		if (e.model.isNew()) 
	    {
			$(".k-window-title").text("Add new item from "+contractName);
			$(".k-grid-update").text("Save");
			
			$('label[for="itemExpiryDate"]').parent().hide();
			$('div[data-container-for="itemExpiryDate"]').hide();
			$('label[for="warrantyValidTill"]').parent().hide();
			$('div[data-container-for="warrantyValidTill"]').hide();
			
	    }
		else
		{
			$(".k-window-title").text("Edit this item from "+contractName);
			
			if(e.model.sgriStatus == "Active")
			{
				$('label[for="itemQuantity"]').parent().remove();
				$('div[data-container-for="itemQuantity"]').remove();
				$('label[for="uomId"]').parent().remove();
				$('div[data-container-for="uomId"]').remove();
				$('label[for="rate"]').parent().remove();
				$('div[data-container-for="rate"]').remove();
				$('label[for="imId"]').parent().remove();
				$('div[data-container-for="imId"]').remove();
				$('label[for="partNo"]').parent().remove();
				$('div[data-container-for="partNo"]').remove();
				$('label[for="itemManufacturer"]').parent().remove();
				$('div[data-container-for="itemManufacturer"]').remove();
				$('label[for="itemManufactureDate"]').parent().remove();
				$('div[data-container-for="itemManufactureDate"]').remove();
			}		
			
			if(e.model.imType == "Consumables")
			{
				$('label[for="itemExpiryDate"]').parent().show();
				$('div[data-container-for="itemExpiryDate"]').show();
				$('label[for="warrantyValidTill"]').parent().hide();
				$('div[data-container-for="warrantyValidTill"]').hide();
			}	
			else
			{
				$('label[for="warrantyValidTill"]').parent().show();
				$('div[data-container-for="warrantyValidTill"]').show();
				$('label[for="warrantyValidTill"]').parent().hide();
				$('div[data-container-for="warrantyValidTill"]').hide();
			}	
		}
		
		$('div[data-container-for="imName"]').remove();
		$('label[for="imName"]').closest('.k-edit-label').remove();
		
		$('label[for="activationDt"]').parent().hide();
		$('div[data-container-for="activationDt"]').hide();
		
		$('label[for="receiptType"]').parent().remove();
		$('div[data-container-for="receiptType"]').hide();
		
		$('div[data-container-for="sgriStatus"]').remove();
		$('label[for="sgriStatus"]').closest('.k-edit-label').remove();
	
		$('label[for="imType"]').parent().remove();
		$('div[data-container-for="imType"]').remove();
		
		$('label[for="createdBy"]').parent().remove();
		$('div[data-container-for="createdBy"]').remove();
		
		$('label[for="lastUpdatedBy"]').parent().remove();
		$('div[data-container-for="lastUpdatedBy"]').remove();

		$('label[for="uomPurchase"]').parent().remove();
		$('div[data-container-for="uomPurchase"]').remove();

		$('label[for="uomIssue"]').parent().remove();
		$('div[data-container-for="uomIssue"]').remove();
		
	 	$('label[for="rate"]').parent().hide();
		$('div[data-container-for="rate"]').hide();
		
		/* $('label[for="itemQuantity"]').parent().hide();
		$('div[data-container-for="itemQuantity"]').hide(); */
		
		$('.k-edit-field .k-input').first().focus();
		
		var grid = this;
		e.container.on("keydown", function(e) {        
	        if (e.keyCode == kendo.keys.ENTER) {
	          $(document.activeElement).blur();
	          grid.saveRow();
	        }
	      });
		
		e.container.find(".k-grid-cancel").bind("click", function () {
		      //your code here
			var gridStoreGoodsReceiptItems = $('#gridStoreGoodsReceiptItems_' + SelectedRowIdStoreGoodsReceipt).data("kendoGrid");
			gridStoreGoodsReceiptItems.dataSource.read();
			gridStoreGoodsReceiptItems.refresh();
		   });
		   
		//CLIENT SIDE VALIDATION FOR MULTI SELECT
 		$(".k-grid-update").click(function () 
		{
 			
 			e.model.set("itemQuantity",quantity);
 			var specialInstructions = $('textarea[data-bind="value:specialInstructions"]').val();
 			if(specialInstructions.length > 225)
		    {
		    	alert("Maximum 225 characters are allowed to enter as Special Instructions");
		    	return false;
		    }   
 			var expiryDate=$('input[name="warrantyValidTill"]').val();
 			
 			var manufacturDate=$('input[name="itemManufactureDate"]').val();
 			
 			/* if($.datepicker.parseDate('dd/mm/yy',expiryDate) < $.datepicker.parseDate('dd/mm/yy',manufacturDate)){
 				alert("Expiry Date Cannot be less than Manufacture Date");
 				return false;
 			} */
		}); 
	}
	
	//register custom validation rules
	(function ($, kendo) 
		{   	  
	    $.extend(true, kendo.ui.validator, 
	    {
	         rules: 
	         { // custom rules
	        	 itemQuantitySizevalidation: function (input, params) 
	             {   if ((input.attr("name") == "itemQuantity") && (gridStoreGoodsReceipt == "gridStoreGoodsReceipt") && (input.val() > 999999999.99))            	
	                 {
	                	 return false;
	                 }        
	                 return true;
	             },
	             itemManufacturerSizevalidation: function (input, params) 
	             {    
	            	 if ((input.attr("name") == "itemManufacturer") && (gridStoreGoodsReceipt == "gridStoreGoodsReceipt") && (input.val().length > 100))
	                 {
	                	 return false;
	                 }        
	                 return true;
	             },
	             itemManufactureDatevalidation: function (input, params) 
	             {
                     if (input.filter("[name = 'itemManufactureDate']").length && input.val()) 
                     {                          
                         var selectedDate = input.val();
                         var todaysDate = $.datepicker.formatDate('dd/mm/yy', new Date());
                         var flagDate = false;

                         if ($.datepicker.parseDate('dd/mm/yy', selectedDate) < $.datepicker.parseDate('dd/mm/yy', todaysDate)) 
                         {
                                flagDate = true;
                         }
                         return flagDate;
                     }
                     return true;
                 },
	             itemQuantitySizeSGRValidation: function (input, params) 
				 {    
	            	 if ((input.attr("name") == "itemQuantity") && (gridStoreGoodsReceipt == "gridStoreGoodsReceipt") && (input.val() == 0))
			    	 {
				        	return false;
			    	 }	 
			         return true;	 
				 },
				 itemQuantity_SRGblank : function(input, params){
					if ((input.attr("name") == "itemQuantity") && (gridStoreGoodsReceipt == "gridStoreGoodsReceipt"))
					{
						return $.trim(input.val()) !== "";
					}
					return true;
				 },
				 itemManufacturer_blank : function(input, params){
					 if ((input.attr("name") == "itemManufacturer") && (gridStoreGoodsReceipt == "gridStoreGoodsReceipt"))
					 {
							return $.trim(input.val()) !== "";
					 }
					 return true;
				 },
				 itemManufactureDate_blank : function(input, params){
					 if ((input.attr("name") == "itemManufactureDate") && (gridStoreGoodsReceipt == "gridStoreGoodsReceipt"))
					 {
						 return $.trim(input.val()) !== "";
					 }
					 return true;
			   	 },
			   	 partNo_blank : function(input, params){
					 if ((input.attr("name") == "partNo") && (gridStoreGoodsReceipt == "gridStoreGoodsReceipt"))
					 {
						 return $.trim(input.val()) !== "";
					 }
					 return true;
			   	 },
			   	partNoSizevalidation: function (input, params) 
	            {               	
					if ((input.attr("name") == "partNo") && (gridStoreGoodsReceipt == "gridStoreGoodsReceipt") && (input.val().length > 100)) 
	            	{
	                		 return false;
	                }        
	                return true;
	             }
	         },
	         messages: 
	         {
				//custom rules messages
				itemQuantitySizevalidation: "Maximum 999999999.99 is allowed to enter",
				itemManufacturerSizevalidation: "Maximum 100 characters are allowed to enter",
				itemManufactureDatevalidation: "Item manufacture date must be selected from the past",
				partNoSizevalidation: "Maximum 100 characters are allowed to enter",
				itemQuantitySizeSGRValidation: "Quantity cannot be zero",
				itemQuantity_SRGblank: "Quantity is required",
				itemManufacturer_blank:"Item manufacturer is required",
				itemManufactureDate_blank:"Item manufacturer date is required",
				partNo_blank:"Part number is required"
	     	 }
	    });
	    
	})(jQuery, kendo);
	  //End Of Validation
	
	function itemsSGREditor(container, options) 
   	{
		$('<input name="Store item" id="itemsSGRId" data-text-field="imName" data-value-field="imId" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoComboBox({
			autoBind : false,
			placeholder : "Select item",
			headerTemplate : '<div class="dropdown-header">'
				+ '<span class="k-widget k-header">Photo</span>'
				+ '<span class="k-widget k-header">Contact info</span>'
				+ '</div>',
			template : '<table><tr>'
				+ '<td align="left"><span class="k-state-default"><b>#: data.imName #</b></span><br>'
				+ '<span class="k-state-default"><b>Type:&nbsp;</b>&nbsp;&nbsp;<i>#: data.imType #</i></span><br>'
				+ '<span class="k-state-default"><b>Group:&nbsp;</b>&nbsp;&nbsp;<i>#: data.imGroup #</i></span><br>'
				+ '<span class="k-state-default"><b>UOM:&nbsp;</b>&nbsp;&nbsp;<i>#: data.uom #</i></span><br>'
				+ '<span class="k-state-default"><b>Quantity:&nbsp;</b>&nbsp;&nbsp;<i>#: data.quantity #</i></span><br>'
				+ '<span class="k-state-default"><b>Rate:&nbsp;</b>&nbsp;<i>#: data.rate#</i></span></td></tr></table>',
			dataSource : {
				transport : {		
					read :  "${itemsSGRComboBoxUrl}/"+vcId
				}
			},
			change : function (e) {
	            if (this.value() && this.selectedIndex == -1) {                    
					alert("Item doesn't exist!");
	                $("#itemsSGRId").data("kendoComboBox").value("");             	
	        	}
	            
	    		$("input[name=itemQuantity]").data("kendoNumericTextBox").value(this.dataItem().quantity);
                quantity=this.dataItem().quantity;
	            /* $('input[name="itemQuantity"]').data("kendoNumericTextBox").value(this.dataItem().quantity); */
	            
	            //alert(JSON.stringify(this.dataItem().imId));
	            var data=this.dataItem();
	            var imType = data.imType; 

	            if((imType == "Assets") || (imType == "Asset Spares"))
				{
					$('label[for="itemExpiryDate"]').parent().hide();
					$('div[data-container-for="itemExpiryDate"]').hide();
					$('label[for="warrantyValidTill"]').parent().show();
					$('div[data-container-for="warrantyValidTill"]').show();
				}	
				else
				{
					$('label[for="warrantyValidTill"]').parent().hide();
					$('div[data-container-for="warrantyValidTill"]').hide();
					$('label[for="itemExpiryDate"]').parent().show();
					$('div[data-container-for="itemExpiryDate"]').show();
				}
	            
	            /* $('label[for="itemQuantity"]').parent().hide();
				$('div[data-container-for="itemQuantity"]').hide(); */
		    } 
		});
		
		$('<span class="k-invalid-msg" data-for="Store item"></span>').appendTo(container);
   	}
	  
	function unitOfMeasurementSGREditor(container, options) 
   	{
		$('<input name="UOM" id="uomSGR" data-text-field="uom" data-value-field="uomId" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoComboBox({
			cascadeFrom : "itemsSGRId",
			autoBind : false,
			placeholder : "Select uom",
			headerTemplate : '<div class="dropdown-header">'
				+ '<span class="k-widget k-header">Photo</span>'
				+ '<span class="k-widget k-header">Contact info</span>'
				+ '</div>',
			template : '<table><tr>'
				+ '<td align="left"><span class="k-state-default"><b>#: data.uom #</b></span><br>'
				+ '<span class="k-state-default">Code: <i>#: data.code #</i></span><br>'
				+ '<span class="k-state-default">Base UOM: <i>#: data.baseUom #</i></span><br>'
				+ '<span class="k-state-default"><b>Quantity:&nbsp;</b>&nbsp;&nbsp;<i>#: data.quantity #</i></span><br>'
				+ '<span class="k-state-default"><b>Rate:&nbsp;</b>&nbsp;<i>#: data.rate#</i></span></td></tr></table>',
			dataSource : {
				transport : {		
					read :  "${unitOfMeasurementSGRComboBoxUrl}/"+vcId
				}
			},
			change : function (e) {
	            if (this.value() && this.selectedIndex == -1) {    
					alert("Unit of measurement doesn't exist!");
	                $("#uomSGR").data("kendoComboBox").value("");
	        	}
	            
	            var data=this.dataItem();
	           // alert(data.rate);
				$('input[name="rate"]').val(data.rate).change();
		    } 
		});
		
		$('<span class="k-invalid-msg" data-for="UOM"></span>').appendTo(container);
   	}
	  
	function storeGoodsReceiptItemsParse (response) {   
	    $.each(response, function (idx, elem) {

	    	if(elem.activationDt == null)
	    	{
	    		elem.activationDt = "";
	    	}	
	    	else
	    	{	
            	elem.activationDt = kendo.parseDate(new Date(elem.activationDt),'dd/MM/yyyy HH:mm');
	    	}

        });
        return response;
	}
	
	function dataBoundstoreGoodsReceiptItems(e) 
	{
		var grid = $('#gridStoreGoodsReceiptItems_' + SelectedRowIdStoreGoodsReceipt).data("kendoGrid");
	    var gridData = grid._data;
	    
	    var i = 0;
		
	 	this.tbody.find("tr td:last-child").each(function (e) 
	   	{
	 		var status = gridData[i].sgriStatus;
	 		 var currentUid = gridData[i].uid;
	 		 
	 		if(status == 'Inactive')
		   	{
		   		$('<button id="test1" class="k-button k-button-icontext" onclick="sgriStatusClick()">Activate</button>').appendTo(this);
		   	}	
	 		else
	 		{
	 			/* var currenRow = grid.table.find("tr[data-uid='" + currentUid + "']");
	            var editButton = $(currenRow).find(".k-grid-edit");
	            editButton.hide(); */
	 			//$('<table><tr><td>'+status+'</td></tr></table>').appendTo(this);
	 		} 
	 		i++;
	   	});
	 	
	 	/* 	this.tbody.find("tr td:last-child").each(function (e) 
	    {
	  		
	 		var status = gridData[i].sgriStatus;
	 		alert(status);
 	 		if(status == 'Active')
	 		{
	 			$(".k-grid-delete", '#gridStoreGoodsReceiptItems_' + SelectedRowIdStoreGoodsReceipt).hide();
	 	 	} 
	 		
	   	});*/ 
	  	
	 	/* var rowSelector = $("#singleselect_"+globalIdVal[id]).closest('td').closest('tr');//">tr:nth-child(" + (i + 1) + ")";
	  	var row = grid.tbody.find(rowSelector); */
	}
	
	function sgriStatusClick()
	{
		var gridStoreGoodsReceiptItems = $('#gridStoreGoodsReceiptItems_' + SelectedRowIdStoreGoodsReceipt).data("kendoGrid");
		var selectedAddressItem = gridStoreGoodsReceiptItems.dataItem(gridStoreGoodsReceiptItems.select());
		var sgriId= selectedAddressItem.sgriId;
		
		$.ajax
		({
			type : "POST",
			url : "./storeGoodsReceiptItems/sgriStatus/" + sgriId,
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
				$('#gridStoreGoodsReceiptItems_' + SelectedRowIdStoreGoodsReceipt).data('kendoGrid').dataSource.read();
			}
		});
	}
	
	function onRequestStartStoreGoodsReceiptItems(e)
	{
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();
        
		/* var grid = $('#gridStoreGoodsReceiptItems_' + SelectedRowIdStoreGoodsReceipt).data("kendoGrid");
		if(grid != null)
		{
			grid.cancelRow();
		} */	
	} 
	
	function onRequestEndStoreGoodsReceiptItems(e) 
	{
		displayMessage(e, 'gridStoreGoodsReceiptItems_' + SelectedRowIdStoreGoodsReceipt, "Store Receipt Item");
	}
	
</script>

<style>

td{
	vertical-align: middle;
}
</style>
