<%@include file="/common/taglibs.jsp"%>

	<!-- Urls for Common controller  -->
	<c:url value="/common/getAllChecks" var="allChecksUrl" />
	<c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />
	<c:url value="/common/getFilterAutoCompleteValues" var="filterAutoCompleteUrl" />
	<c:url value="/common/getPersonNamesFilterList" var="personNamesFilterUrl" />
	<c:url value="/common/getPersonListBasedOnPersonType" var="personNamesAutoBasedOnPersonTypeUrl" />
	
	<!-- Urls for Store Item Ledger  -->
	<c:url value="/storeItemLedger/read" var="readStoreItemLedgerUrl" />
	
	<c:url value="/storeItemLedger/getStoreNamesFilterUrlStoreLedger" var="storeNamesFilterUrlStoreLedger" />
	<c:url value="/storeItemLedger/getImNamesFilterUrlStoreLedger" var="imNamesFilterUrlStoreLedger" />
	<c:url value="/storeItemLedger/getUomFilterUrlStoreLedger" var="uomFilterUrlStoreLedger" />
	
	<!-- Urls for Store Item Ledger Details  -->
	<c:url value="/storeItemLedgerDetails/read" var="readStoreItemLedgerDetailsUrl" />
	
	<!-- ------------------------------------ Grid -------------------------------------- -->
	
	<kendo:grid name="gridStoreItemLedger" pageable="true" resizable="true" change="onChangeStoreItemLedger" detailTemplate="gridStoreItemLedgerDetailsTemplate"
	sortable="true" reorderable="true" selectable="true" scrollable="true" groupable="true" filterable="true" navigatable="true">
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" refresh="true" info="true" previousNext="true">
			<kendo:grid-pageable-messages itemsPerPage="Store Ledgers per page" empty="No Store Ledger to display" refresh="Refresh all the Store Ledgers" 
			display="{0} - {1} of {2} Store Ledgers" first="Go to the first page of Store Ledgers" last="Go to the last page of Store Ledgers" next="Go to the next page of Store Ledgers"
			previous="Go to the previous page of Store Ledgers"/>
		</kendo:grid-pageable> 
		<kendo:grid-filterable extra="false">
			<kendo:grid-filterable-operators>
				<kendo:grid-filterable-operators-string eq="Is equal to"/>
				<kendo:grid-filterable-operators-date gt="Is after" lt="Is before"/>
			</kendo:grid-filterable-operators>
		</kendo:grid-filterable>
		<kendo:grid-toolbar>
		<kendo:grid-toolbarItem name="storeItemTemplatesDetailsExport" text="Export To Excel" />
			  <kendo:grid-toolbarItem name="storeItemPdfTemplatesDetailsExport" text="Export To PDF" />  
			<kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterStoreItemLedger()><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>"/>
		</kendo:grid-toolbar>
		<kendo:grid-columns>
			<kendo:grid-column title="Store" field="storeName" width="80px">
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
									filter:"startswith",	
									dataSource : {
										transport : {		
											read :  "${storeNamesFilterUrlStoreLedger}"
										}
									} 
								});
						   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>	
			<kendo:grid-column title="Item" field="imName" width="80px">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function imNameFilter(element) 
						   	{
								element.kendoComboBox({
									autoBind : true,
									dataTextField : "imName",
									dataValueField : "imName", 
									placeholder : "Enter item name",	
									filter:"startswith",	
									dataSource : {
										transport : {		
											read :  "${imNamesFilterUrlStoreLedger}"
										}
									} 
								});
						   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>	
			<kendo:grid-column title="Balance" field="imBalance" width="80px"/>
			<kendo:grid-column title="Base&nbsp;Unit&nbsp;of&nbsp;Measurement" field="uom" width="80px">
			<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function uomFilter(element) 
						   	{
								element.kendoComboBox({
									autoBind : true,
									dataTextField : "uom",
									dataValueField : "uom", 
									placeholder : "Enter uom",	
									filter:"startswith",
									dataSource : {
										transport : {		
											read :  "${uomFilterUrlStoreLedger}"
										}
									}
								});
						   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>	
			<kendo:grid-column title="Ledger&nbsp;Update&nbsp;Date&nbsp;Time" format="{0:dd/MM/yyyy HH:mm}" field="silDt" width="120px" filterable="true">
				<kendo:grid-column-filterable ui="datetimepicker"></kendo:grid-column-filterable>
			</kendo:grid-column>
        </kendo:grid-columns>
		<kendo:dataSource requestStart="onRequestStartStoreItemLedger" requestEnd="onRequestEndStoreItemLedger">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-read url="${readStoreItemLedgerUrl}" dataType="json" type="POST" contentType="application/json" />
			</kendo:dataSource-transport>
			<kendo:dataSource-schema parse="storeItemLedgerParse">
				<kendo:dataSource-schema-model id="silId">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="silId" type="number"/>
						<kendo:dataSource-schema-model-field name="storeName" type="string"/>
						<kendo:dataSource-schema-model-field name="imName" type="string"/>
						<kendo:dataSource-schema-model-field name="imBalance" type="number"/>
						<kendo:dataSource-schema-model-field name="uom" type="string"/>
						<kendo:dataSource-schema-model-field name="silDt" type="date"/>
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
	</kendo:grid>
	
	<kendo:grid-detailTemplate id="gridStoreItemLedgerDetailsTemplate">
		<kendo:tabStrip name="tabStrip_#=silId#">
			<kendo:tabStrip-items>
				<kendo:tabStrip-item text="Ledger Details" selected="true">
                <kendo:tabStrip-item-content>
                    <div class="wethear">
                      <kendo:grid name="gridStoreItemLedgerDetails_#=silId#" pageable="true" resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true" groupable="false" filterable="false" navigatable="true">
							<kendo:grid-pageable pageSize="10">
								<kendo:grid-pageable-messages itemsPerPage="Ledger Details per page" empty="No Ledger Detail to display" refresh="Refresh all the Ledger Details" 
								display="{0} - {1} of {2} Ledger Details" first="Go to the first page of Ledger Details" last="Go to the last page of Ledger Details" next="Go to the next page of Ledger Details"
								previous="Go to the previous page of Ledger Details"/>
							</kendo:grid-pageable> 
							<kendo:grid-columns>
								<kendo:grid-column title="Transaction&nbsp;Type" field="transactionType" width="140px"/>
						        <kendo:grid-column title="Quantity" field="quantity" width="100px"/>
						        <kendo:grid-column title="Status" field="status" width="100px"/>
						        <kendo:grid-column title="Unit&nbsp;of&nbsp;measuremant" field="uomId" width="100px">
							        <kendo:grid-column-values value="${uom}"/>
								</kendo:grid-column>
						        <kendo:grid-column title="Rate&nbsp;per&nbsp;quantity" field="rate" width="100px"/>
								<kendo:grid-column title="Ledger&nbsp;Update&nbsp;Date&nbsp;Time" format="{0:dd/MM/yyyy HH:mm}" field="lastUpdatedDt" width="170px" filterable="true">
									<kendo:grid-column-filterable ui="datetimepicker"></kendo:grid-column-filterable>
								</kendo:grid-column>
								<kendo:grid-column title="Quantity&nbsp;per&nbsp;base&nbsp;UOM" field="quantityPerBaseUom" width="160px"/>
					        </kendo:grid-columns>
							<kendo:dataSource>
								<kendo:dataSource-transport>
									<kendo:dataSource-transport-read url="${readStoreItemLedgerDetailsUrl}/#=silId#" dataType="json" type="POST" contentType="application/json" />
								</kendo:dataSource-transport>
								<kendo:dataSource-schema parse="storeItemLedgerDetailsParse">
									<kendo:dataSource-schema-model id="sildId">
										<kendo:dataSource-schema-model-fields>
											<kendo:dataSource-schema-model-field name="sildId" type="number"/>
											<kendo:dataSource-schema-model-field name="silId" type="number"/> 
											<kendo:dataSource-schema-model-field name="transactionType" type="string"/>
											<kendo:dataSource-schema-model-field name="quantity" type="number"/>
											<kendo:dataSource-schema-model-field name="status" type="string"/>
											<kendo:dataSource-schema-model-field name="uomId"/>
											<kendo:dataSource-schema-model-field name="rate" type="number"/>
											<kendo:dataSource-schema-model-field name="quantityPerBaseUom" type="string"/>
											<kendo:dataSource-schema-model-field name="lastUpdatedDt" type="date"/>
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
	$("#gridStoreItemLedger").on("click",".k-grid-storeItemTemplatesDetailsExport", function(e) {
		  window.open("./storeItemTemplate/storeItemTemplatesDetailsExport");
	});	  

	$("#gridStoreItemLedger").on("click",".k-grid-storeItemPdfTemplatesDetailsExport", function(e) {
		  window.open("./storeItemPdfTemplate/storeItemPdfTemplatesDetailsExport");
	});	  


	//-- ------------------------------------------ Store Item Ledger Script  ------------------------------------------ -
	
	var silId = 0;
	var imId = 0;
	var uom = "";
	function onChangeStoreItemLedger(arg) {
		
		var gview = $("#gridStoreItemLedger").data("kendoGrid");
		var selectedItem = gview.dataItem(gview.select());
		silId = selectedItem.silId;
		imId = selectedItem.imId;
		uom = selectedItem.uom;
		this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
	}
	
	function clearFilterStoreItemLedger()
	{
		  $("form.k-filter-menu button[type='reset']").slice().trigger("click");
		  var gridStoreItemLedger = $("#gridStoreItemLedger").data("kendoGrid");
		  gridStoreItemLedger.dataSource.read();
		  gridStoreItemLedger.refresh();
	}

	function storeItemLedgerParse (response) {   
	    $.each(response, function (idx, elem) {

            	elem.silDt = kendo.parseDate(new Date(elem.silDt),'dd/MM/yyyy HH:mm');
            
        });
        return response;
	}
	
	function onRequestStartStoreItemLedger(e)
	{
		var gridStoreItemLedger = $("#gridStoreItemLedger").data("kendoGrid");
		gridStoreItemLedger.cancelRow();
	}
	
	function onRequestEndStoreItemLedger(e) 
	{
		displayMessage(e, "gridStoreItemLedger", "Store Item Ledger");
	}	
	
	//-- ------------------------------------------ Store Item Ledger Details Script  ------------------------------------------ -
	
	function storeItemLedgerDetailsParse (response) {   
		
		$('th[data-field="quantityPerBaseUom"]').find('a').html("Quantity per base UOM ("+uom+")");
		
	    $.each(response, function (idx, elem) {	    	
	    	if(elem.lastUpdatedDt == null)
	    	{
	    		elem.lastUpdatedDt = "";
	    	}	
	    	else
	    	{	
            	elem.lastUpdatedDt = kendo.parseDate(new Date(elem.lastUpdatedDt),'dd/MM/yyyy HH:mm');
	    	}

        });
        return response;
	}
	
	</script>

	<style>
	td {
    	vertical-align: middle;
	}
	</style>