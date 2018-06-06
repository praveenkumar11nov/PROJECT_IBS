<%@include file="/common/taglibs.jsp"%>

	<!-- Urls for Common controller  -->
	<c:url value="/common/getAllChecks" var="allChecksUrl" />
	<c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />
	<c:url value="/common/getFilterAutoCompleteValues" var="filterAutoCompleteUrl" />
	<c:url value="/common/getPersonNamesFilterList" var="personNamesFilterUrl" />
	<c:url value="/common/getPersonListBasedOnPersonType" var="personNamesAutoBasedOnPersonTypeUrl" />
	
	<!-- Urls for Store Adjustment  -->
	<c:url value="/storeAdjustments/read" var="readStoreAdjustmentUrl" />
	<c:url value="/storeAdjustments/modify" var="modifyStoreAdjustmentUrl" />
	
	<c:url value="/storeAdjustments/getSAStoreNamesFilterUrl" var="storeNamesSAFilterUrl" />
	<c:url value="/storeAdjustments/getSAStoreMasterComboBoxUrl" var="storeMasterSAComboBoxUrl" />
	<c:url value="/storeAdjustments/getContractNamesSAFilterUrl" var="contractNamesSAFilterUrl" />
	<c:url value="/storeAdjustments/getVendorContractSAComboBoxUrl" var="vendorContractSAComboBoxUrl" />
	<c:url value="/storeAdjustments/getImNamesSAFilterUrl" var="imNamesSAFilterUrl"/>
	<c:url value="/storeAdjustments/getItemsSAComboBoxUrl" var="itemsSAComboBoxUrl"/>
	<c:url value="/storeAdjustments/getUomSAFilterUrl" var="uomSAFilterUrl"/>
	<c:url value="/storeIssue/getUnitOfMeasurementSMComboBoxUrl" var="unitOfMeasurementSAComboBoxUrl" />
	
	<!-- ------------------------------------ Grid -------------------------------------- -->
	
	<kendo:grid name="gridStoreAdjustment" edit="storeAdjustmentEdit" pageable="true" resizable="true" dataBound="storeAdjustmentDataBound" change="onChangeStoreAdjustment"
	sortable="true" reorderable="true" selectable="true" scrollable="true" groupable="true" filterable="true" navigatable="true">
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="5" input="true" numeric="true" refresh="true" info="true" previousNext="true">
			<kendo:grid-pageable-messages itemsPerPage="Store Adjustments per page" empty="No Store Adjustment to display" refresh="Refresh all the Store Adjustments" 
			display="{0} - {1} of {2} Store Adjustments" first="Go to the first page of Store Adjustments" last="Go to the last page of Store Adjustments" next="Go to the next page of Store Adjustments"
			previous="Go to the previous page of Store Adjustments"/>
		</kendo:grid-pageable> 
		<kendo:grid-filterable extra="false">
			<kendo:grid-filterable-operators>
				<kendo:grid-filterable-operators-string eq="Is equal to"/>
				<kendo:grid-filterable-operators-date gt="Is after" lt="Is before"/>
			</kendo:grid-filterable-operators>
		</kendo:grid-filterable>
		<kendo:grid-editable mode="popup" confirmation="Are you sure you want to delete this store adjustment record"/>
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Store Adjustment" />
			<kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterStoreAdjustments()><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>"/>
		</kendo:grid-toolbar>
		<kendo:grid-columns>
			<kendo:grid-column title="Store&nbsp;*" field="storeName" width="60px">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function storeNameFilter(element) 
						   	{
								element.kendoComboBox({
									autoBind : true,
									dataTextField : "storeName",
									dataValueField : "storeName", 
									placeholder : "Enter to store name",
									headerTemplate : '<div class="dropdown-header">'
										+ '<span class="k-widget k-header">Photo</span>'
										+ '<span class="k-widget k-header">Contact info</span>'
										+ '</div>',
									template : '<table><tr>'
										+ '<td align="left"><span class="k-state-default"><b>#: data.storeName #</b></span><br>'
										+ '<span class="k-state-default"><i>#: data.storeLocation #</i></span></td></tr></table>',
									dataSource : {
										transport : {		
											read :  "${storeNamesSAFilterUrl}"
										}
									} 
								});
						   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			<kendo:grid-column title="Store&nbsp;*" field="storeId" editor="storeMasterSAEditor" width="0px" hidden="true">
            </kendo:grid-column>	
			
			 <kendo:grid-column title="Contract&nbsp;*" field="contractName" width="70px">
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
											read :  "${contractNamesSAFilterUrl}"
										}
									} 
								});
						   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>	
			<kendo:grid-column title="Contract&nbsp;*" field="vcId" editor="vendorContractSAEditor" width="0px" hidden="true">
            </kendo:grid-column>
            
			 <kendo:grid-column title="Item&nbsp;*" field="imName" width="60px">
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
												read :  "${imNamesSAFilterUrl}"
											}
										} 
									});
							   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>	
			<kendo:grid-column title="Item&nbsp;*" field="imId" editor="itemsSAEditor" width="0px" hidden="true"/>
			<kendo:grid-column title="Unit&nbsp;of&nbsp;Measurement&nbsp;*" field="uom" width="90px">
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
												read :  "${uomSAFilterUrl}"
											}
										} 
									});
							   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>	
	        <kendo:grid-column title="Unit&nbsp;of&nbsp;Measurement&nbsp;*" field="uomId" editor="unitOfMeasurementSAEditor" width="0px" hidden="true"/>
	        <kendo:grid-column title="Quantity&nbsp;*" field="itemQuantity"  editor="itemQuantitySAEditor" width="50px"/>
	        <kendo:grid-column title="Approved&nbsp;By&nbsp;*" field="personName" width="60px">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function personNameFilter(element) 
						   	{
								element.kendoComboBox({
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
											read :  "${personNamesFilterUrl}/StoreAdjustments/users"
										}
									} 
								});
						   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>	
			<kendo:grid-column title="Approved&nbsp;By&nbsp;*" field="approvedByStaffId" editor="personEditorStoreAdjustmentsApprovedBy" width="0px" hidden="true"/>
			
	        <kendo:grid-column title="Adjustment&nbsp;No&nbsp;*" field="saNumber" editor="saNumberEditor" width="75px"/>
	       
	        <kendo:grid-column title="Adjustment&nbsp;Date&nbsp;*" field="saDate" format="{0:dd/MM/yyyy}" width="0px" hidden="true"/>
            <kendo:grid-column title="Adjustment&nbsp;Time&nbsp;*" field="saTime" format="{0:HH:mm}" editor="timeSAEditor" width="0px" hidden="true"/>
			<kendo:grid-column title="Adjustment&nbsp;Date&nbsp;Time" field="saDt" format="{0:dd/MM/yyyy HH:mm}" editor="dateTimeSAEditor" width="100px" filterable="true">
				<kendo:grid-column-filterable ui="datetimepicker"></kendo:grid-column-filterable>
			</kendo:grid-column>
			<kendo:grid-column title="Ledger&nbsp;Update&nbsp;Date&nbsp;Time" format="{0:dd/MM/yyyy HH:mm}" field="ledgerUpdateDt" width="110px" filterable="true">
				<kendo:grid-column-filterable ui="datetimepicker"></kendo:grid-column-filterable>
			</kendo:grid-column>
			<kendo:grid-column title="Reason" field="reasonForAdjustment" editor="textAreaEditor" width="85px"/>
			<kendo:grid-column title="Shipping&nbsp;Document&nbsp;No&nbsp;*" field="shippingDocumentNumber" width="0px" hidden="true"/>
			<kendo:grid-column title="Item&nbsp;Manufacturer&nbsp;*" field="itemManufacturer" width="0px" hidden="true"/>
			<kendo:grid-column title="Item&nbsp;Manufacture&nbsp;Date&nbsp;*" field="itemManufactureDate" format="{0:dd/MM/yyyy}" width="0px" hidden="true"/>
			<kendo:grid-column title="Part&nbsp;No&nbsp;*" field="partNo"  width="0px" hidden="true"/>
			<kendo:grid-column title="contractStatus" field="contractStatus" width="0px" hidden="true"/>
			<kendo:grid-column title="&nbsp;" width="60px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit" />
				</kendo:grid-column-command>
			</kendo:grid-column>
        </kendo:grid-columns>
		<kendo:dataSource requestStart="onRequestStartStoreAdjustment" requestEnd="onRequestEndStoreAdjustment">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-read url="${readStoreAdjustmentUrl}" dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-create url="${modifyStoreAdjustmentUrl}/create" dataType="json" type="GET" contentType="application/json" />
				<kendo:dataSource-transport-update url="${modifyStoreAdjustmentUrl}/update" dataType="json" type="GET" contentType="application/json" />
			</kendo:dataSource-transport>
			<kendo:dataSource-schema parse="storeAdjustmentParse">
				<kendo:dataSource-schema-model id="saId">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="saId" type="number"/> 
						<kendo:dataSource-schema-model-field name="storeId"/>
						<kendo:dataSource-schema-model-field name="storeName"/>
						<kendo:dataSource-schema-model-field name="vcId"/>
						<kendo:dataSource-schema-model-field name="contractName"/>
						<kendo:dataSource-schema-model-field name="imName"/>
						<kendo:dataSource-schema-model-field name="imId"/>
						<kendo:dataSource-schema-model-field name="uom"/>
						<kendo:dataSource-schema-model-field name="uomId"/>
						<kendo:dataSource-schema-model-field name="itemQuantity" type="number"/>
						<kendo:dataSource-schema-model-field name="personName"/>
						<kendo:dataSource-schema-model-field name="approvedByStaffId"/>
						
						<kendo:dataSource-schema-model-field name="saNumber" type="number"/>
						<kendo:dataSource-schema-model-field name="saDate" type="date">
							<kendo:dataSource-schema-model-field-validation required="true"/>
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="saTime" defaultValue="00:00"/>
						<kendo:dataSource-schema-model-field name="saDt" type="date"/>
						<kendo:dataSource-schema-model-field name="ledgerUpdateDt" type="date"/>
						<kendo:dataSource-schema-model-field name="reasonForAdjustment" type="string"/>
						<kendo:dataSource-schema-model-field name="shippingDocumentNumber" type="string"/>
						<kendo:dataSource-schema-model-field name="itemManufacturer" type="string"/>
						<kendo:dataSource-schema-model-field name="itemManufactureDate" type="date" defaultValue=""/>
						<kendo:dataSource-schema-model-field name="partNo" type="string"/>
						<kendo:dataSource-schema-model-field name="contractStatus" type="string"/>
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
	</kendo:grid>
	
	<div id="alertsBox" title="Alert"></div>
	
	<script type="text/javascript">
	
	var type = "";
	var storeIdSA = 0;
	var vcIdSA = 0;
	var minimumQuantity = 0;
	var uomSA = "";
	var saId="";
	var saStatus = "";
	var contractStatus = "";
	
	function onChangeStoreAdjustment(arg) {
		
		var gview = $("#gridStoreAdjustment").data("kendoGrid");
		var selectedItem = gview.dataItem(gview.select());
		saId = selectedItem.saId;
		saStatus = selectedItem.saStatus;
		this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
	}
	
	function clearFilterStoreAdjustments()
	{
		  $("form.k-filter-menu button[type='reset']").slice().trigger("click");
		  var gridStoreAdjustment = $("#gridStoreAdjustment").data("kendoGrid");
		  gridStoreAdjustment.dataSource.read();
		  gridStoreAdjustment.refresh();
	}
	
	$("#gridStoreAdjustment").on("click", ".k-grid-add", function(e) 
	{ 
		securityCheckForActions("./inventory/storeAdjustments/createButton");
		
		if($("#gridStoreAdjustment").data("kendoGrid").dataSource.filter())
		 {
	    		//$("#grid").data("kendoGrid").dataSource.filter({});
	    		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
	 			var grid = $("#gridStoreAdjustment").data("kendoGrid");
	 			grid.dataSource.read();
	 			grid.refresh();
	        }   
	 });
	
	function storeAdjustmentEdit(e)
	{
		$('div[data-container-for="saDt"]').hide();
		$('label[for="saDt"]').parent().remove();
		$('div[data-container-for="ledgerUpdateDt"]').hide();
		$('label[for="ledgerUpdateDt"]').parent().remove();
		$('div[data-container-for="storeName"]').remove();
		$('label[for="storeName"]').closest('.k-edit-label').remove();
		$('div[data-container-for="imName"]').remove();
		$('label[for="imName"]').closest('.k-edit-label').remove();
		$('div[data-container-for="uom"]').remove();
		$('label[for="uom"]').closest('.k-edit-label').remove();
		$('div[data-container-for="personName"]').remove();
		$('label[for="personName"]').closest('.k-edit-label').remove();
		$('div[data-container-for="contractName"]').remove();
		$('label[for="contractName"]').closest('.k-edit-label').remove();
		$('div[data-container-for="contractStatus"]').hide();
		$('label[for="contractStatus"]').closest('.k-edit-label').hide();
		
		if (e.model.isNew()) 
	    {
			type = "create";
			
			$(".k-window-title").text("New store item adjustment");
			$(".k-grid-update").text("Save");
			
			$('label[for="vcId"]').parent().hide();
			$('div[data-container-for="vcId"]').hide();
			$('label[for="imId"]').parent().hide();
			$('div[data-container-for="imId"]').hide();
			$('label[for="uomId"]').parent().hide();
			$('div[data-container-for="uomId"]').hide();
			$('label[for="itemQuantity"]').parent().hide();
			$('div[data-container-for="itemQuantity"]').hide();
			
			$('div[data-container-for="shippingDocumentNumber"]').hide();
			$('label[for="shippingDocumentNumber"]').parent().hide();
			$('div[data-container-for="itemManufacturer"]').hide();
			$('label[for="itemManufacturer"]').parent().hide();
			$('div[data-container-for="itemManufactureDate"]').hide();
			$('label[for="itemManufactureDate"]').parent().hide();
			$('div[data-container-for="partNo"]').hide();
			$('label[for="partNo"]').parent().hide();
	    }
		else
		{
			securityCheckForActions("./inventory/storeAdjustments/updateButton");
			
			type = "update";
			
			$(".k-window-title").text("Edit store adjustment Details");
			
			$('label[for="storeId"]').parent().remove();
			$('div[data-container-for="storeId"]').remove();
			$('label[for="vcId"]').parent().remove();
			$('div[data-container-for="vcId"]').remove();
			$('label[for="imId"]').parent().remove();
			$('div[data-container-for="imId"]').remove();
			$('label[for="uomId"]').parent().remove();
			$('div[data-container-for="uomId"]').remove();
			$('label[for="itemQuantity"]').parent().remove();
			$('div[data-container-for="itemQuantity"]').remove();
			
			$('div[data-container-for="shippingDocumentNumber"]').remove();
			$('label[for="shippingDocumentNumber"]').parent().remove();
			$('div[data-container-for="itemManufacturer"]').remove();
			$('label[for="itemManufacturer"]').parent().remove();
			$('div[data-container-for="itemManufactureDate"]').remove();
			$('label[for="itemManufactureDate"]').parent().remove();
			$('div[data-container-for="partNo"]').remove();
			$('label[for="partNo"]').parent().hide();
		}
		
		 $('.k-edit-field .k-input').first().focus();
		
		 e.container.find(".k-grid-cancel").bind("click", function () {
		   
			var gridStoreAdjustment = $('#gridStoreAdjustment').data("kendoGrid");
			gridStoreAdjustment.dataSource.read();
			gridStoreAdjustment.refresh();
		   }); 
		
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
 			if(type == "create")
 			{
 				var itemQuantity = $("#itemQuantitySA").val();
 	 			
 	 			 if(itemQuantity == 0)
 		       	 {
 		       		 alert("Quantity cannot be zero");
 		       		 return false;
 		       	 }
 	 			
 	 			 if(contractStatus == "old")
 	 			 {
 	 				 
 	 				 var selectedQuantity = ((-1) * (itemQuantity));
 	 				 
 	 				//alert(minimumQuantity+" ===== "+itemQuantity+" ==== selected === "+selectedQuantity);
 	 				if((minimumQuantity > 0) && (selectedQuantity > minimumQuantity))
 	 		       	 {
 	 		       		 alert("Maximum "+minimumQuantity+" "+uom+" is available to transfer in the store with respect to this contract");
 	 		       		 //return getMessage(input);
 	 		       		 return false;
 	 		       	 }
 	 			 }
 	 			 else
 	 			 {
 	 				if(itemQuantity < 0)
 	 		       	{
 	 		       		 alert("Quantity cannot be reduced (negative) since selected item is taken from the new contract");
 	 		       		 return false;
 	 		       	}
 	 				
 	 				var shippingDocumentNumber = $('input[name="shippingDocumentNumber"]').val();
 	 				if((shippingDocumentNumber == null) || (shippingDocumentNumber == "") || (shippingDocumentNumber.trim() == ""))
 	 				{
 	 					alert("Shipping document number is required");
 	 				}
 	 				var itemManufacturer = $('input[name="itemManufacturer"]').val();
 	 				if((itemManufacturer == null) || (itemManufacturer == "") || (itemManufacturer.trim() == ""))
 	 				{
 	 					alert("Item Manufacturer is required");
 	 				}
 	 				var itemManufactureDate = $('input[name="itemManufactureDate"]').val();
 	 				if((itemManufactureDate == null) || (itemManufactureDate == "") )
 				    {
 				         alert("Item Manufacture Date is not selected");
 				         return false;        
 				    } 
 	 			 }	 
 			}	
		});  
	}
	
	function storeAdjustmentDataBound(e) 
	{
		/* var data = this.dataSource.view(),row;
	    for (var i = 0; i < data.length; i++) {
	        row = this.tbody.children("tr[data-uid='" + data[i].uid + "']");
	        
	        var status = data[i].saStatus;
	        var saId = data[i].saId;
	        if (status == 'Inactive') {
				
				$('#saStatusId_' + saId).show();
				//row.find(".k-grid-edit").hide();
		        row.find(".k-grid-delete").show();
			} else {
				$('#saStatusId_' + saId).hide();
				//row.find(".k-grid-edit").hide();
		        row.find(".k-grid-delete").hide();
			}
	    } */
	}
	
	function saStatusClick(text)
	{
		$.ajax
		({
			type : "POST",
			url : "./storeAdjustments/saStatus/" + saId,
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
				$('#gridStoreAdjustment').data('kendoGrid').dataSource.read();
			}
		});
	}
	
	
	function storeAdjustmentParse (response) {   
	    $.each(response, function (idx, elem) {
	    	
	    	if(elem.ledgerUpdateDt == null)
	    	{
	    		elem.ledgerUpdateDt = "";
	    	}	
	    	else
	    	{	
            	elem.ledgerUpdateDt = kendo.parseDate(new Date(elem.ledgerUpdateDt),'dd/MM/yyyy HH:mm');
	    	}

            	elem.saDt = kendo.parseDate(new Date(elem.saDt),'dd/MM/yyyy HH:mm');
            
        });
        return response;
	}

	function storeMasterSAEditor(container, options) 
   	{
		$('<input name="Store" id="storeSA" data-text-field="storeName" data-value-field="storeId" data-bind="value:' + options.field + '" required="true"/>')
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
					read :  "${storeMasterSAComboBoxUrl}"
				}
			},
			change : function (e) {
	            if (this.value() && this.selectedIndex == -1) {                    
	                alert("Store doesn't exist!");
	                $("#storeSA").data("kendoComboBox").value("");
	        	}
	            
	            storeIdSA = this.value();
	            
	            $('label[for="vcId"]').parent().show();
				$('div[data-container-for="vcId"]').show();
				$('label[for="imId"]').parent().show();
				$('div[data-container-for="imId"]').show();
				$('label[for="uomId"]').parent().show();
				$('div[data-container-for="uomId"]').show();
				$('label[for="itemQuantity"]').parent().hide();
				$('div[data-container-for="itemQuantity"]').hide();
				
					var combobox = $("#contractSA").data("kendoComboBox");
					combobox.value("");
					var combobox = $("#itemsSA").data("kendoComboBox");
					combobox.value("");
					var combobox = $("#uomSAId").data("kendoComboBox");
					combobox.value("");
					var numerictextbox = $("#itemQuantitySA").data("kendoNumericTextBox");
					numerictextbox.value(0);
					minimumQuantity = 0;
					
					$('div[data-container-for="shippingDocumentNumber"]').hide();
					$('label[for="shippingDocumentNumber"]').parent().hide();
					$('div[data-container-for="itemManufacturer"]').hide();
					$('label[for="itemManufacturer"]').parent().hide();
					$('div[data-container-for="itemManufactureDate"]').hide();
					$('label[for="itemManufactureDate"]').parent().hide();
					$('div[data-container-for="partNo"]').hide();
					$('label[for="partNo"]').parent().hide();
		    } 
		});
		
		$('<span class="k-invalid-msg" data-for="Store"></span>').appendTo(container);
   	}
	
	function vendorContractSAEditor(container, options) 
   	{
		$('<input name="Contract" id="contractSA" data-text-field="contractName" data-value-field="vcId" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoComboBox({
			autoBind : false,
			cascadeFrom : "storeSA",
			placeholder : "Select contract",
			headerTemplate : '<div class="dropdown-header">'
				+ '<span class="k-widget k-header">Photo</span>'
				+ '<span class="k-widget k-header">Contact info</span>'
				+ '</div>',
			template : '<table><tr>'
				+ '<td align="left"><span class="k-state-default"><b>#: data.contractName #</b></span><br>'
				+ '<span class="k-state-default"><b>Contract&nbsp;No:&nbsp;</b><i>#: data.contractNo #</i></span></td></tr></table>',
			dataSource : {
				transport : {		
					read :  "${vendorContractSAComboBoxUrl}"
				}
			},
			change : function (e) {
	            if (this.value() && this.selectedIndex == -1) {                    
					alert("Contract doesn't exist!");
	                $("#contractSA").data("kendoComboBox").value("");
	        	}
	            
	            vcIdSA = this.value();
	            var data=this.dataItem();
	            storeIdSA = data.storeId;
	            
				$('label[for="itemQuantity"]').parent().hide();
				$('div[data-container-for="itemQuantity"]').hide();
				
					var combobox = $("#itemsSA").data("kendoComboBox");
					combobox.value("");
					var combobox = $("#uomSAId").data("kendoComboBox");
					combobox.value("");
					var numerictextbox = $("#itemQuantitySA").data("kendoNumericTextBox");
					numerictextbox.value(0);
					minimumQuantity = 0;
					
					$('div[data-container-for="shippingDocumentNumber"]').hide();
					$('label[for="shippingDocumentNumber"]').parent().hide();
					$('div[data-container-for="itemManufacturer"]').hide();
					$('label[for="itemManufacturer"]').parent().hide();
					$('div[data-container-for="itemManufactureDate"]').hide();
					$('label[for="itemManufactureDate"]').parent().hide();
					$('div[data-container-for="partNo"]').hide();
					$('label[for="partNo"]').parent().hide();
		    } 
		});
		
		$('<span class="k-invalid-msg" data-for="Contract"></span>').appendTo(container);
   	}
	
	function itemsSAEditor(container, options) 
   	{
		$('<input name="Item" id="itemsSA" data-text-field="imName" data-value-field="imId" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoComboBox({
			cascadeFrom : "contractSA",
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
					read :  "${itemsSAComboBoxUrl}"
				}
			},
			change : function (e) {
	            if (this.value() && this.selectedIndex == -1) {                    
	                alert("Item doesn't exist!");
	                $("#itemsSA").data("kendoComboBox").value("");
	        	}
	            
	            imIdSA = this.value();
	            var data=this.dataItem();
	            vcIdSA = data.vcId;
	            
	            $('label[for="itemQuantity"]').parent().hide();
				$('div[data-container-for="itemQuantity"]').hide();
				
					var combobox = $("#uomSAId").data("kendoComboBox");
					combobox.value("");
					var numerictextbox = $("#itemQuantitySA").data("kendoNumericTextBox");
					numerictextbox.value(0);
					minimumQuantity = 0;
					
					$('div[data-container-for="shippingDocumentNumber"]').hide();
					$('label[for="shippingDocumentNumber"]').parent().hide();
					$('div[data-container-for="itemManufacturer"]').hide();
					$('label[for="itemManufacturer"]').parent().hide();
					$('div[data-container-for="itemManufactureDate"]').hide();
					$('label[for="itemManufactureDate"]').parent().hide();
					$('div[data-container-for="partNo"]').hide();
					$('label[for="partNo"]').parent().hide();
		    } 
		});
		
		$('<span class="k-invalid-msg" data-for="Item"></span>').appendTo(container);
   	}
	function unitOfMeasurementSAEditor(container, options) 
   	{
		$('<input name="UOM" id="uomSAId" data-text-field="uom" data-value-field="uomId" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoComboBox({
			cascadeFrom : "itemsSA",
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
					read :  "${unitOfMeasurementSAComboBoxUrl}"
				}
			},
			change : function (e) {
	            if (this.value() && this.selectedIndex == -1) {                    
	                alert("Unit of measurement doesn't exist!");
	                $("#uomSAId").data("kendoComboBox").value("");
	        	}    
	            
					var data=this.dataItem();
		            
					 var result = 0;
		           	 $.ajax({
		           	     type : "GET",
		           	  	async: false,
		           	  	 data : {
		           	  	    storeId : storeIdSA,
		           	  		vcId : vcIdSA,
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
		           	minimumQuantity = result;
		           	
		           	/* var numerictextbox = $("#itemQuantity").data("kendoNumericTextBox");
					numerictextbox.value(result); */
					
		           	$('label[for="itemQuantity"]').parent().show();
					$('div[data-container-for="itemQuantity"]').show();
					
					if(minimumQuantity == 0)
					{
						alert("Cannot reduce the quantity since balance is empty because selected contract items may be transferred or adjusted with respect to selected store and item");
						$('label[for="itemQuantity"]').text("Item Quantity *\n(Balance: "+minimumQuantity+" "+uom+")");
						contractStatus = "old";
						$('input[name="contractStatus"]').val(contractStatus).change();
						
						$('div[data-container-for="shippingDocumentNumber"]').hide();
						$('label[for="shippingDocumentNumber"]').parent().hide();
						$('div[data-container-for="itemManufacturer"]').hide();
						$('label[for="itemManufacturer"]').parent().hide();
						$('div[data-container-for="itemManufactureDate"]').hide();
						$('label[for="itemManufactureDate"]').parent().hide();
						$('div[data-container-for="partNo"]').hide();
						$('label[for="partNo"]').parent().hide();
					}	
					else if(minimumQuantity == -1)
					{
						$('label[for="itemQuantity"]').text("Item Quantity *\n(New Contract)");
						contractStatus = "new";
						$('input[name="contractStatus"]').val(contractStatus).change();
						
						$('div[data-container-for="shippingDocumentNumber"]').show();
						$('label[for="shippingDocumentNumber"]').parent().show();
						$('div[data-container-for="itemManufacturer"]').show();
						$('label[for="itemManufacturer"]').parent().show();
						$('div[data-container-for="itemManufactureDate"]').show();
						$('label[for="itemManufactureDate"]').parent().show();
						$('div[data-container-for="partNo"]').show();
						$('label[for="partNo"]').parent().show();
					}
					else if(minimumQuantity == -2)
					{
						$('label[for="itemQuantity"]').text("Item Quantity *\n(New Contract/ New Item from Selected Contract)");
						contractStatus = "new";
						$('input[name="contractStatus"]').val(contractStatus).change();
						
						$('div[data-container-for="shippingDocumentNumber"]').show();
						$('label[for="shippingDocumentNumber"]').parent().show();
						$('div[data-container-for="itemManufacturer"]').show();
						$('label[for="itemManufacturer"]').parent().show();
						$('div[data-container-for="itemManufactureDate"]').show();
						$('label[for="itemManufactureDate"]').parent().show();
						$('div[data-container-for="partNo"]').show();
						$('label[for="partNo"]').parent().show();
					}
					else if(minimumQuantity == -3)
					{
						$('label[for="itemQuantity"]').text("Item Quantity *\n(New Contract and New Item)");
						contractStatus = "new";
						$('input[name="contractStatus"]').val(contractStatus).change();
						
						$('div[data-container-for="shippingDocumentNumber"]').show();
						$('label[for="shippingDocumentNumber"]').parent().show();
						$('div[data-container-for="itemManufacturer"]').show();
						$('label[for="itemManufacturer"]').parent().show();
						$('div[data-container-for="itemManufactureDate"]').show();
						$('label[for="itemManufactureDate"]').parent().show();
						$('div[data-container-for="partNo"]').show();
						$('label[for="partNo"]').parent().show();
					}
					else
					{
						$('label[for="itemQuantity"]').text("Item Quantity *\n(Balance: "+minimumQuantity+" "+uom+")");
						contractStatus = "old";
						$('input[name="contractStatus"]').val(contractStatus).change();
						
						$('div[data-container-for="shippingDocumentNumber"]').hide();
						$('label[for="shippingDocumentNumber"]').parent().hide();
						$('div[data-container-for="itemManufacturer"]').hide();
						$('label[for="itemManufacturer"]').parent().hide();
						$('div[data-container-for="itemManufactureDate"]').hide();
						$('label[for="itemManufactureDate"]').parent().hide();
						$('div[data-container-for="partNo"]').hide();
						$('label[for="partNo"]').parent().hide();
					}	
		    	} 
		});
		
		$('<span class="k-invalid-msg" data-for="UOM"></span>').appendTo(container);
   	}
	
	function itemQuantitySAEditor(container, options) 
   	{
		$('<input name="Quantity" id="itemQuantitySA" data-text-field="itemQuantity" data-value-field="itemQuantity" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoNumericTextBox({
			change : function (e) {
	            if((contractStatus == "old") && (this.value() > 0))
	            {
	            	/* $('div[data-container-for="shippingDocumentNumber"]').show();
					$('label[for="shippingDocumentNumber"]').parent().show(); */
					$('div[data-container-for="itemManufacturer"]').show();
					$('label[for="itemManufacturer"]').parent().show();
					$('div[data-container-for="itemManufactureDate"]').show();
					$('label[for="itemManufactureDate"]').parent().show();
					$('div[data-container-for="partNo"]').show();
					$('label[for="partNo"]').parent().show();
	            }	
	            else if(contractStatus == "new")
	            {
	            	$('div[data-container-for="shippingDocumentNumber"]').show();
					$('label[for="shippingDocumentNumber"]').parent().show();
					$('div[data-container-for="itemManufacturer"]').show();
					$('label[for="itemManufacturer"]').parent().show();
					$('div[data-container-for="itemManufactureDate"]').show();
					$('label[for="itemManufactureDate"]').parent().show();
					$('div[data-container-for="partNo"]').show();
					$('label[for="partNo"]').parent().show();
	            }	
	            else
	            {
	            	$('div[data-container-for="shippingDocumentNumber"]').hide();
					$('label[for="shippingDocumentNumber"]').parent().hide();
					$('div[data-container-for="itemManufacturer"]').hide();
					$('label[for="itemManufacturer"]').parent().hide();
					$('div[data-container-for="itemManufactureDate"]').hide();
					$('label[for="itemManufactureDate"]').parent().hide();
					$('div[data-container-for="partNo"]').hide();
					$('label[for="partNo"]').parent().hide();
	            }	
		    } 
		});
		
		$('<span class="k-invalid-msg" data-for="Quantity"></span>').appendTo(container);
	}
	
	function personEditorStoreAdjustmentsApprovedBy(container, options) 
   	{
		$('<input name="Approved by" id="personApproved" data-text-field="personName" data-value-field="id" data-bind="value:' + options.field + '" required="true"/>')
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
	                $("#personApproved").data("kendoComboBox").value("");
	        	}
		    } 
		});
		
		$('<span class="k-invalid-msg" data-for="Approved by"></span>').appendTo(container);
   	}
	
	function saNumberEditor(container, options) 
   	{
		$('<input name="Adjustment number" id="saNumber" data-text-field="saNumber" data-value-field="saNumber" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoNumericTextBox({
			min : 0,
		    decimals: 0,
		    format: '0.'
		});
		
		$('<span class="k-invalid-msg" data-for="Adjustment number"></span>').appendTo(container);
	}
	
	function dateTimeSAEditor(container, options) {
	    $('<input data-text-field="' + options.field + '" data-value-field="' + options.field + '" data-bind="value:' + options.field + '" data-format="' + options.format + '"/>')
	            .appendTo(container)
	            .kendoDateTimePicker({});
	}
	
	function timeSAEditor(container, options) 
	{
		    $('<input name="Received time" data-text-field="' + options.field + '" data-value-field="' + options.field + '" data-bind="value:' + options.field + '" data-format="' + options.format + '" required="true"/>')
		            .appendTo(container)
		            .kendoTimePicker({});
		    
		    $('<span class="k-invalid-msg" data-for="Received time"></span>').appendTo(container);
	}
	
	//register custom validation rules
	(function($, kendo) {$.extend(true,
						kendo.ui.validator,
						{
							rules : { // custom rules
								
							},
							messages : {
								
							}
						});

	})(jQuery, kendo);
	//End Of Validation
	
	function onRequestStartStoreAdjustment(e)
	{
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();
        
		/* var gridStoreAdjustment = $("#gridStoreAdjustment").data("kendoGrid");
		gridStoreAdjustment.cancelRow(); */
	}
	
	function onRequestEndStoreAdjustment(e) 
	{
		displayMessage(e, "gridStoreAdjustment", "Store Adjustment");
	}	
	
	</script>
<!-- ------------------------------------------ Style  ------------------------------------------ -->

	<style>
	td {
    	vertical-align: middle;
	}
	</style>