<%@include file="/common/taglibs.jsp"%>

	<!-- Urls for Common controller  -->
	<c:url value="/common/getAllChecks" var="allChecksUrl" />
	<c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />
	<c:url value="/common/getFilterAutoCompleteValues" var="filterAutoCompleteUrl" />
	<c:url value="/common/getPersonNamesFilterList" var="personNamesFilterUrl" />
	<c:url value="/common/getPersonListBasedOnPersonType" var="personNamesAutoBasedOnPersonTypeUrl" />
	
	<!-- Urls for Store Movement  -->
	<c:url value="/storeMovement/read" var="readStoreMovementUrl" />
	<c:url value="/storeMovement/modify" var="modifyStoreMovementUrl" />
	
	<c:url value="/storeMovement/getToStoreNamesFilterUrl" var="toStoreNamesFilterUrl" />
	<c:url value="/storeMovement/getToStoreMasterComboBoxUrl" var="toStoreMasterComboBoxUrl" />
	<c:url value="/storeMovement/getFromStoreNamesFilterUrl" var="fromStoreNamesFilterUrl" />
	<c:url value="/storeMovement/getFromStoreMasterComboBoxUrl" var="fromStoreMasterComboBoxUrl" />
	<c:url value="/storeMovement/getContractNamesSMFilterUrl" var="contractNamesSMFilterUrl" />
	<c:url value="/storeMovement/getVendorContractSMComboBoxUrl" var="vendorContractSMComboBoxUrl" />
	<c:url value="/storeMovement/getImNamesSMFilterUrl" var="imNamesSMFilterUrl"/>
	<c:url value="/storeMovement/getItemsSMComboBoxUrl" var="itemsSMComboBoxUrl"/>
	<c:url value="/storeMovement/getUomSMFilterUrl" var="uomSMFilterUrl"/>
	<c:url value="/storeIssue/getUnitOfMeasurementSMComboBoxUrl" var="unitOfMeasurementComboBoxUrl" />
	
	<!-- ------------------------------------ Grid -------------------------------------- -->
	
	<kendo:grid name="gridStoreMovement" edit="storeMovementEdit" pageable="true" resizable="true" dataBound="storeMovementDataBound" change="onChangeStoreMovement"
	sortable="true" reorderable="true" selectable="true" scrollable="true" groupable="true" filterable="true" navigatable="true">
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" refresh="true" info="true" previousNext="true">
			<kendo:grid-pageable-messages itemsPerPage="Store Movements per page" empty="No Store Movement to display" refresh="Refresh all the Store Movements" 
			display="{0} - {1} of {2} Store Movements" first="Go to the first page of Store Movements" last="Go to the last page of Store Movements" next="Go to the next page of Store Movements"
			previous="Go to the previous page of Store Movements"/>
		</kendo:grid-pageable> 
		<kendo:grid-filterable extra="false">
			<kendo:grid-filterable-operators>
				<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains"/>
			</kendo:grid-filterable-operators>
		</kendo:grid-filterable>
		<kendo:grid-editable mode="popup"/>
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Store Movement" />
			<kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterStoreMovement()><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>"/>
		</kendo:grid-toolbar>
		<kendo:grid-columns>
			<kendo:grid-column title="To&nbsp;Store&nbsp;Name&nbsp;*" field="toStoreName" width="130px">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function toStoreNameFilter(element) 
						   	{
								element.kendoComboBox({
									autoBind : true,
									dataTextField : "toStoreName",
									dataValueField : "toStoreName", 
									placeholder : "Enter to store name",
									headerTemplate : '<div class="dropdown-header">'
										+ '<span class="k-widget k-header">Photo</span>'
										+ '<span class="k-widget k-header">Contact info</span>'
										+ '</div>',
									template : '<table><tr>'
										+ '<td align="left"><span class="k-state-default"><b>#: data.toStoreName #</b></span><br>'
										+ '<span class="k-state-default"><i>#: data.storeLocation #</i></span></td></tr></table>',
									dataSource : {
										transport : {		
											read :  "${toStoreNamesFilterUrl}"
										}
									} 
								});
						   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>	
			<kendo:grid-column title="To&nbsp;Store&nbsp;Name&nbsp;*" field="toStoreId" editor="toStoreMasterEditor" width="0px" hidden="true">
            </kendo:grid-column>
            
            <kendo:grid-column title="From&nbsp;Store&nbsp;Name&nbsp;*" field="fromStoreName" width="130px">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function fromStoreNameFilter(element) 
						   	{
								element.kendoComboBox({
									autoBind : true,
									dataTextField : "fromStoreName",
									dataValueField : "fromStoreName", 
									placeholder : "Enter from store name",
									headerTemplate : '<div class="dropdown-header">'
										+ '<span class="k-widget k-header">Photo</span>'
										+ '<span class="k-widget k-header">Contact info</span>'
										+ '</div>',
									template : '<table><tr>'
										+ '<td align="left"><span class="k-state-default"><b>#: data.fromStoreName #</b></span><br>'
										+ '<span class="k-state-default"><i>#: data.storeLocation #</i></span></td></tr></table>',
									dataSource : {
										transport : {		
											read :  "${fromStoreNamesFilterUrl}"
										}
									} 
								});
						   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>	
			<kendo:grid-column title="From&nbsp;Store&nbsp;Name&nbsp;*" field="fromStoreId" editor="fromStoreMasterEditor" width="0px" hidden="true">
            </kendo:grid-column>
            <kendo:grid-column title="Vendor&nbsp;Contract&nbsp;Name&nbsp;*" field="contractName" width="155px">
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
											+ '<span class="k-state-default"><b>Contract&nbsp;No:&nbsp;</b><i>#: data.contractNo #</i></span></td></tr></table>',
									dataSource : {
										transport : {		
											read :  "${contractNamesSMFilterUrl}"
										}
									} 
								});
						   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>	
			<kendo:grid-column title="Vendor&nbsp;Contract&nbsp;Name&nbsp;*" field="vcId" editor="vendorContractSMEditor" width="0px" hidden="true">
            </kendo:grid-column>
			 <kendo:grid-column title="Item&nbsp;Name&nbsp;*" field="imName" width="100px">
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
										headerTemplate : '<div class="dropdown-header">'
											+ '<span class="k-widget k-header">Photo</span>'
											+ '<span class="k-widget k-header">Contact info</span>'
											+ '</div>',
										template : '<table><tr>'
											+ '<td align="left"><span class="k-state-default"><b>#: data.imName #</b></span><br>'
											+ '<span class="k-state-default"><b>Type:&nbsp;</b>&nbsp;&nbsp;<i>#: data.imType #</i></span><br>'
											+ '<span class="k-state-default"><b>Group:&nbsp;</b>&nbsp;<i>#: data.imGroup #</i></span></td></tr></table>',
										dataSource : {
											transport : {		
												read :  "${imNamesSMFilterUrl}"
											}
										} 
									});
							   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>	
			<kendo:grid-column title="Item&nbsp;Name&nbsp;*" field="imId" editor="itemsSMEditor" width="0px" hidden="true"/>
			<kendo:grid-column title="Unit&nbsp;of&nbsp;Measurement&nbsp;*" field="uom" width="150px">
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
										headerTemplate : '<div class="dropdown-header">'
											+ '<span class="k-widget k-header">Photo</span>'
											+ '<span class="k-widget k-header">Contact info</span>'
											+ '</div>',
										template : '<table><tr>'
											+ '<td align="left"><span class="k-state-default"><b>#: data.uom #</b></span><br>'
											+ '<span class="k-state-default">Code: <i>#: data.code #</i></span><br>'
											+ '<span class="k-state-default">Base UOM: <i>#: data.baseUom #</i></span><br>'
											+ '<span class="k-state-default"><b>Item: </b><i>#: data.imName #</i></span></td></tr></table>',
										dataSource : {
											transport : {		
												read :  "${uomSMFilterUrl}"
											}
										} 
									});
							   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>	
	        <kendo:grid-column title="Unit&nbsp;of&nbsp;Measurement&nbsp;*" field="uomId" editor="unitOfMeasurementSMEditor" width="0px" hidden="true"/>
	        <kendo:grid-column title="Quantity&nbsp;*" field="itemQuantity" width="80px" editor="itemQuantityEditor"/>
	 		<kendo:grid-column title="Part&nbsp;No&nbsp;*" field="partNo"  width="80px">
	 			<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function partNoFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter part No",
									dataSource : {
										transport : {
											read : "${filterAutoCompleteUrl}/StoreMovement/partNo"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			<kendo:grid-column title="Item&nbsp;Manufacturer&nbsp;*" field="itemManufacturer" width="130px">
			<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function itemManufacturerFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter item manufacturer name",
									dataSource : {
										transport : {
											read : "${filterAutoCompleteUrl}/StoreMovement/itemManufacturer"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			<kendo:grid-column title="Item&nbsp;Expiry&nbsp;Date" field="itemExpiryDate" format="{0:dd/MM/yyyy}" width="120px"/>
			<kendo:grid-column title="Warranty&nbsp;Expiry&nbsp;Date" field="warrantyValidTill" format="{0:dd/MM/yyyy}" width="150px"/>
			<kendo:grid-column title="Special&nbsp;Instructions" field="specialInstructions" width="130px"/>
	       	<kendo:grid-column title="Created By" field="createdBy" width="0px" hidden="true"/>
			<kendo:grid-column title="Last Updated By" field="lastUpdatedBy" width="0px" hidden="true"/>
        </kendo:grid-columns>
		<kendo:dataSource requestStart="onRequestStartStoreMovement" requestEnd="onRequestEndStoreMovement">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-read url="${readStoreMovementUrl}" dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-create url="${modifyStoreMovementUrl}/create" dataType="json" type="GET" contentType="application/json" />
			</kendo:dataSource-transport>
			<kendo:dataSource-schema parse="storeMovementParse">
				<kendo:dataSource-schema-model id="stmId">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="stmId" type="number"/> 
						<kendo:dataSource-schema-model-field name="toStoreName"/>
						<kendo:dataSource-schema-model-field name="toStoreId"/>
						<kendo:dataSource-schema-model-field name="fromStoreId"/>
						<kendo:dataSource-schema-model-field name="fromStoreName"/>
						<kendo:dataSource-schema-model-field name="contractName"/>
						<kendo:dataSource-schema-model-field name="vcId"/>
						<kendo:dataSource-schema-model-field name="imName"/>
						<kendo:dataSource-schema-model-field name="imId"/>
						<kendo:dataSource-schema-model-field name="uom"/>
						<kendo:dataSource-schema-model-field name="uomId"/>
						<kendo:dataSource-schema-model-field name="itemQuantity" type="number"/>
 						<kendo:dataSource-schema-model-field name="itemManufacturer" type="string"/>
					    <kendo:dataSource-schema-model-field name="itemExpiryDate" type="date"/>
						<kendo:dataSource-schema-model-field name="warrantyValidTill" type="date"/>
						<kendo:dataSource-schema-model-field name="specialInstructions" type="string"/>
						<kendo:dataSource-schema-model-field name="partNo" type="string"/> 
					    <kendo:dataSource-schema-model-field name="createdBy" type="string"/>
						<kendo:dataSource-schema-model-field name="lastUpdatedBy" type="string"/>
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
	</kendo:grid>
	
	<div id="alertsBox" title="Alert"></div>
	
	<script type="text/javascript">
	
	var fromStoreId = 0;
	var vcId = 0;
	var maximumQuantity = 0;
	var uom = "";
	var stmId="";
	var stmStatus = "";
	
	function onChangeStoreMovement(arg) {
		
		var gview = $("#gridStoreMovement").data("kendoGrid");
		var selectedItem = gview.dataItem(gview.select());
		stmId = selectedItem.stmId;
		this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
	}
	
	function clearFilterStoreMovement()
	{
		  $("form.k-filter-menu button[type='reset']").slice().trigger("click");
		  var gridStoreMovement = $("#gridStoreMovement").data("kendoGrid");
		  gridStoreMovement.dataSource.read();
		  gridStoreMovement.refresh();
	}
	
	 $("#gridStoreMovement").on("click", ".k-grid-add", function(e) { 
		 
		 securityCheckForActions("./inventory/storeMovement/createButton");
			
		 if($("#gridStoreMovement").data("kendoGrid").dataSource.filter()){
				
	    		//$("#grid").data("kendoGrid").dataSource.filter({});
	    		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
	 			var grid = $("#gridStoreMovement").data("kendoGrid");
	 			grid.dataSource.read();
	 			grid.refresh();
	        }   
	 });
	
	function storeMovementEdit(e)
	{
		$('label[for="createdBy"]').parent().remove();
		$('div[data-container-for="createdBy"]').remove();
		$('label[for="lastUpdatedBy"]').parent().remove();
		$('div[data-container-for="lastUpdatedBy"]').remove();
		$('div[data-container-for="toStoreName"]').remove();
		$('label[for="toStoreName"]').closest('.k-edit-label').remove();
		$('div[data-container-for="fromStoreName"]').remove();
		$('label[for="fromStoreName"]').closest('.k-edit-label').remove();
		$('div[data-container-for="imName"]').remove();
		$('label[for="imName"]').closest('.k-edit-label').remove();
		$('div[data-container-for="uom"]').remove();
		$('label[for="uom"]').closest('.k-edit-label').remove();
		$('div[data-container-for="contractName"]').remove();
		$('label[for="contractName"]').closest('.k-edit-label').remove();
		
		$('label[for="itemManufacturer"]').parent().hide();
		$('div[data-container-for="itemManufacturer"]').hide();
		$('label[for="specialInstructions"]').parent().hide();
		$('div[data-container-for="specialInstructions"]').hide();
		$('label[for="itemExpiryDate"]').parent().hide();
		$('div[data-container-for="itemExpiryDate"]').hide();
		$('label[for="warrantyValidTill"]').parent().hide();
		$('div[data-container-for="warrantyValidTill"]').hide();
		$('label[for="partNo"]').parent().hide();
		$('div[data-container-for="partNo"]').hide();
		
		if (e.model.isNew()) 
	    {
			$.ajax
			({
				type : "POST",
				url : "./storeMovement/checkIsStoreItemLedgerEmpty",
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
						var gridStoreMovement = $("#gridStoreMovement").data("kendoGrid");
						gridStoreMovement.cancelRow();
					}	
				}
			});
			
			$(".k-window-title").text("New store item movement");
			$(".k-grid-update").text("Save");
			
			$('label[for="vcId"]').parent().hide();
			$('div[data-container-for="vcId"]').hide();
			$('label[for="imId"]').parent().hide();
			$('div[data-container-for="imId"]').hide();
			$('label[for="uomId"]').parent().hide();
			$('div[data-container-for="uomId"]').hide();
			$('label[for="itemQuantity"]').parent().hide();
			$('div[data-container-for="itemQuantity"]').hide();
	    }
		else
		{
			
		}
		

		
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
 			var itemQuantity = $("#itemQuantity").val();
 			
 			 if(itemQuantity == 0)
	       	 {
	       		 alert("Quantity cannot be zero");
	       		 return false;
	       	 }
 			
 			 if((maximumQuantity > 0) && (itemQuantity > maximumQuantity))
	       	 {
	       		 alert("Maximum "+maximumQuantity+" "+uom+" is available  to transfer in the store with respect to this contract");
	       		 //return getMessage(input);
	       		 return false;
	       	 }	
		}); 

	}
	
	function storeMovementDataBound(e) 
	{
	
	}
	
	function stmStatusClick(text)
	{
		$.ajax
		({
			type : "POST",
			url : "./storeMovement/stmStatus/" + stmId,
			success : function(response) 
			{
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
				$('#gridStoreMovement').data('kendoGrid').dataSource.read();
			}
		});
	}
	
	
	function storeMovementParse (response) {   

        return response;
	}
	
	function itemQuantityEditor(container, options) 
   	{
		$('<input name="Item quantity" id="itemQuantity" data-text-field="itemQuantity" data-value-field="itemQuantity" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoNumericTextBox({
			min : 0
		});
		
		$('<span class="k-invalid-msg" data-for="Item quantity"></span>').appendTo(container);
	}
	
	function toStoreMasterEditor(container, options) 
   	{
		$('<input name="To store" id="toStore" data-text-field="storeName" data-value-field="toStoreId" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoComboBox({
			autoBind : false,
			placeholder : "Select to store",
			headerTemplate : '<div class="dropdown-header">'
				+ '<span class="k-widget k-header">Photo</span>'
				+ '<span class="k-widget k-header">Contact info</span>'
				+ '</div>',
			template : '<table><tr>'
				+ '<td align="left"><span class="k-state-default"><b>#: data.storeName #</b></span><br>'
				+ '<span class="k-state-default"><i>#: data.storeLocation #</i></span></td></tr></table>',
			dataSource : {
				transport : {		
					read :  "${toStoreMasterComboBoxUrl}"
				}
			},
			change : function (e) {
	            if (this.value() && this.selectedIndex == -1) {                    
					alert("Staff doesn't exist!");
	                $("#toStore").data("kendoComboBox").value("");
	        	}
	            
	            $('label[for="vcId"]').parent().hide();
				$('div[data-container-for="vcId"]').hide();
				$('label[for="imId"]').parent().hide();
				$('div[data-container-for="imId"]').hide();
				$('label[for="uomId"]').parent().hide();
				$('div[data-container-for="uomId"]').hide();
				
	            var combobox = $("#fromStore").data("kendoComboBox");
				combobox.value("");
				var combobox = $("#contractSM").data("kendoComboBox");
				combobox.value("");
				var combobox = $("#itemsSM").data("kendoComboBox");
				combobox.value("");
				var combobox = $("#uomIdSM").data("kendoComboBox");
				combobox.value("");
				var numerictextbox = $("#itemQuantity").data("kendoNumericTextBox");
				numerictextbox.value(0);
				maximumQuantity = 0;
				
				$('label[for="itemExpiryDate"]').parent().hide();
				$('div[data-container-for="itemExpiryDate"]').hide();
				$('label[for="warrantyValidTill"]').parent().hide();
				$('div[data-container-for="warrantyValidTill"]').hide();
				
				$('label[for="itemQuantity"]').parent().hide();
				$('div[data-container-for="itemQuantity"]').hide();
		    } 
		});
		
		$('<span class="k-invalid-msg" data-for="To store"></span>').appendTo(container);
   	}
		
	function fromStoreMasterEditor(container, options) 
   	{
		$('<input name="From store" id="fromStore" data-text-field="storeName" data-value-field="fromStoreId" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoComboBox({
			cascadeFrom : "toStore",
			autoBind : false,
			placeholder : "Select from store",
			headerTemplate : '<div class="dropdown-header">'
				+ '<span class="k-widget k-header">Photo</span>'
				+ '<span class="k-widget k-header">Contact info</span>'
				+ '</div>',
			template : '<table><tr>'
				+ '<td align="left"><span class="k-state-default"><b>#: data.storeName #</b></span><br>'
				+ '<span class="k-state-default"><i>#: data.storeLocation #</i></span></td></tr></table>',
			dataSource : {
				transport : {		
					read :  "${fromStoreMasterComboBoxUrl}"
				}
			},
			change : function (e) {
	            if (this.value() && this.selectedIndex == -1) {                    
					alert("Store doesn't exist!");
	                $("#fromStore").data("kendoComboBox").value("");
	        	}
	            
	            fromStoreId = this.value();

	            $('label[for="vcId"]').parent().show();
				$('div[data-container-for="vcId"]').show();
				$('label[for="imId"]').parent().show();
				$('div[data-container-for="imId"]').show();
				$('label[for="uomId"]').parent().show();
				$('div[data-container-for="uomId"]').show();
				
	            var combobox = $("#contractSM").data("kendoComboBox");
				combobox.value("");
				var combobox = $("#itemsSM").data("kendoComboBox");
				combobox.value("");
				var combobox = $("#uomIdSM").data("kendoComboBox");
				combobox.value("");
				var numerictextbox = $("#itemQuantity").data("kendoNumericTextBox");
				numerictextbox.value(0);
				maximumQuantity = 0;
				
				$('label[for="itemExpiryDate"]').parent().hide();
				$('div[data-container-for="itemExpiryDate"]').hide();
				$('label[for="warrantyValidTill"]').parent().hide();
				$('div[data-container-for="warrantyValidTill"]').hide();
				
				$('label[for="itemQuantity"]').parent().hide();
				$('div[data-container-for="itemQuantity"]').hide();
		    } 
		});
		
		$('<span class="k-invalid-msg" data-for="From store"></span>').appendTo(container);
   	}
	
	function vendorContractSMEditor(container, options) 
   	{
		$('<input name="Contract" id="contractSM" data-text-field="contractName" data-value-field="vcId" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoComboBox({
			
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
					read :  "${vendorContractSMComboBoxUrl}"
				}
			},
			change : function (e) {
	            if (this.value() && this.selectedIndex == -1) {                    
					alert("Contract doesn't exist!");
	                $("#contractSM").data("kendoComboBox").value("");
	        	}
	            
	            var data=this.dataItem();
	            fromStoreId = data.fromStoreId;
	            vcId = data.vcId;

	            var combobox = $("#itemsSM").data("kendoComboBox");
				combobox.value("");
				var combobox = $("#uomIdSM").data("kendoComboBox");
				combobox.value("");
				var numerictextbox = $("#itemQuantity").data("kendoNumericTextBox");
				numerictextbox.value(0);
				maximumQuantity = 0;
				
				$('label[for="itemQuantity"]').parent().hide();
				$('div[data-container-for="itemQuantity"]').hide();
		    } 
		});
		
		$('<span class="k-invalid-msg" data-for="Contract"></span>').appendTo(container);
   	}
	
	function itemsSMEditor(container, options) 
   	{
		$('<input name="Item" id="itemsSM" data-text-field="imName" data-value-field="imId" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoComboBox({
			cascadeFrom : "contractSM",
			autoBind : false,
			placeholder : "Select item",
			headerTemplate : '<div class="dropdown-header">'
				+ '<span class="k-widget k-header">Photo</span>'
				+ '<span class="k-widget k-header">Contact info</span>'
				+ '</div>',
			template : '<table><tr>'
				+ '<td align="left"><span class="k-state-default"><b>#: data.imName #</b></span><br>'
				+ '<span class="k-state-default"><b>Type:&nbsp;</b>&nbsp;&nbsp;<i>#: data.imType #</i></span><br>'
				+ '<span class="k-state-default"><b>Group:&nbsp;</b>&nbsp;<i>#: data.imGroup #</i></span></td></tr></table>',
			dataSource : {
				transport : {		
					read :  "${itemsSMComboBoxUrl}"
				}
			},
			change : function (e) {
	            if (this.value() && this.selectedIndex == -1) {                    
					alert("Item doesn't exist!");
	                $("#itemsSM").data("kendoComboBox").value("");
	        	}
	            
	            var data=this.dataItem();
	            vcId = data.vcId;
	            
	            var combobox = $("#uomIdSM").data("kendoComboBox");
				combobox.value("");
				var numerictextbox = $("#itemQuantity").data("kendoNumericTextBox");
				numerictextbox.value(0);
				maximumQuantity = 0;
				
	            $('label[for="itemQuantity"]').parent().hide();
				$('div[data-container-for="itemQuantity"]').hide();
		    } 
		});
		
		$('<span class="k-invalid-msg" data-for="Item"></span>').appendTo(container);
   	}
	function unitOfMeasurementSMEditor(container, options) 
   	{
		$('<input name="UOM" id="uomIdSM" data-text-field="uom" data-value-field="uomId" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoComboBox({
			cascadeFrom : "itemsSM",
			autoBind : false,
			placeholder : "Select uom",
			headerTemplate : '<div class="dropdown-header">'
				+ '<span class="k-widget k-header">Photo</span>'
				+ '<span class="k-widget k-header">Contact info</span>'
				+ '</div>',
			template : '<table><tr>'
				+ '<td align="left"><span class="k-state-default"><b>#: data.uom #</b></span><br>'
				+ '<span class="k-state-default">Code: <i>#: data.code #</i></span><br>'
				+ '<span class="k-state-default">Base UOM: <i>#: data.baseUom #</i></span></td></tr></table>',
			dataSource : {
				transport : {		
					read :  "${unitOfMeasurementComboBoxUrl}"
				}
			},
			change : function (e) {
	            if (this.value() && this.selectedIndex == -1) {                    
					alert("Unit of measurement doesn't exist!");
	                $("#uomIdSM").data("kendoComboBox").value("");
	        	}           
	            
	            var data=this.dataItem();
	            
	            var result = 0;
	           	 $.ajax({
	           	     type : "GET",
	           	  	async: false,
	           	  	 data : {
	           	  	    storeId : fromStoreId,
	           	  		vcId : vcId,
	           	  		imId : data.imId,
	           	  		uomId : data.uomId,
						},
	           	     url : './common/getQuantityBasedOnContractStoreAndItem',
	           	     dataType : "JSON",
	           	     success : function(response) {       
	           	     	result=response;
	           	     }
	           	});
	           	 
	           	uom = data.uom;
	           	code = data.code;
	           	maximumQuantity = result;
	           	/* var numerictextbox = $("#itemQuantity").data("kendoNumericTextBox");
				numerictextbox.value(result); */
				
	           	$('label[for="itemQuantity"]').parent().show();
				$('div[data-container-for="itemQuantity"]').show();
				
				if(maximumQuantity == 0)
				{
					alert("Balance is empty because selected contract items may be transferred or adjusted with respect to selected store and item");
					 $("#itemQuantity").kendoNumericTextBox({
					    max: 0
					});
				}	
				
				$('label[for="itemQuantity"]').text("Item Quantity *\n(Balance: "+maximumQuantity+" "+uom+")");
		    } 
		});
		
		$('<span class="k-invalid-msg" data-for="UOM"></span>').appendTo(container);
   	}
	
	//register custom validation rules
	(function($, kendo) {$.extend(true,
						kendo.ui.validator,
						{
							rules : { // custom rules
								itemManufacturerSizevalidation: function (input, params) 
					             {               	
					                 if (input.filter("[name='itemManufacturer']").length && input.val()) 
					                 {
					                	 if(input.val().length > 100)
					                	 {
					                		 return false;
					                	 }	 
					                 }        
					                 return true;
					             },
					             itemExpiryDatevalidation: function (input, params) 
					             {
				                     if (input.filter("[name = 'itemExpiryDate']").length && input.val()) 
				                     {                  
				                    	 if((input.val() != null) && ((input.val() != "")))
				                    	 {
				                    		 var selectedDate = input.val();
					                         var todaysDate = $.datepicker.formatDate('dd/mm/yy', new Date());
					                         var flagDate = false;

					                         if ($.datepicker.parseDate('dd/mm/yy', selectedDate) >= $.datepicker.parseDate('dd/mm/yy', todaysDate)) 
					                         {
					                                flagDate = true;
					                         }
					                         return flagDate;
				                    	 }	 
				                     }
				                     return true;
				                 },
				                 warrantyValidTillvalidation: function (input, params) 
					             {
				                     if (input.filter("[name = 'warrantyValidTill']").length && input.val()) 
				                     {                  
				                    	 if((input.val() != null) && ((input.val() != "")))
				                    	 {
				                    		 var selectedDate = input.val();
					                         var todaysDate = $.datepicker.formatDate('dd/mm/yy', new Date());
					                         var flagDate = false;

					                         if ($.datepicker.parseDate('dd/mm/yy', selectedDate) >= $.datepicker.parseDate('dd/mm/yy', todaysDate)) 
					                         {
					                                flagDate = true;
					                         }
					                         return flagDate;
				                    	 }	 
				                     }
				                     return true;
				                 },
								itemQuantitySizeValidation1: function (input, params) 
             					{               	
					                 if (input.filter("[id='itemQuantity']").length && input.val()) 
					                 {
					                	 if((input.val() > 0) && (maximumQuantity == 0))
					                	 {
					                		 return false;
					                	 }
					                 }        
					                 return true;
					             },
							     itemQuantitySizeValidation2: function (input, params) 
								{    
							    	 if (input.filter("[id='itemQuantity']").length && input.val())
							    	 {
							    		 if((maximumQuantity > 0) && (input.val() == 0))
								         {
								        		return false;
								         } 
							    	 }	 
							         return true;
							     } 
							},
							messages : {
								temManufacturerSizevalidation: "Maximum 100 characters are allowed to enter",
								itemExpiryDatevalidation: "Item expiry date must be selected in the future",
								warrantyValidTillvalidation: "Warrenty expiry date must be selected in the future",
								itemQuantitySizeValidation1:"Either balance is empty or you have not selected the unit of measurement" ,
								itemQuantitySizeValidation2:"Quantity cannot be zero" 
							}
						});

	})(jQuery, kendo);
	//End Of Validation
	
	function onRequestStartStoreMovement(e)
	{
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();
		
		/* var gridStoreMovement = $("#gridStoreMovement").data("kendoGrid");
		gridStoreMovement.cancelRow(); */
	}
	
	function onRequestEndStoreMovement(e) 
	{
		displayMessage(e, "gridStoreMovement", "Store Movement");
	}	
	
	</script>
<!-- ------------------------------------------ Style  ------------------------------------------ -->

	<style>
	td {
    	vertical-align: middle;
	}
	</style>