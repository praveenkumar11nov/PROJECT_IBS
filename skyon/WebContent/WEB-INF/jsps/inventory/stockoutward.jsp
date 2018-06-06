<%@include file="/common/taglibs.jsp"%>


	<!-- Urls for Store Goods Return  -->
	<c:url value="/stockoutward/read" var="readStoreOutwardUrl" />
	<c:url value="/stockoutward/modify" var="modifyStoreOutwardUrl" />
	<c:url value="/stockoutward/deleteItemOutward" var="deleteStoreOutwardUrl" />
	
	<c:url value="/stockoutward/storeNamesoutwardFilterUrl" var="storeNamesoutwardFilterUrl" />
	<c:url value="/stockoutward/itemNamesoutwardFilterUrl" var="itemNamesoutwardFilterUrl" />
	<c:url value="/stockoutward/uomNamesoutwardFilterUrl" var="uomNamesoutwardFilterUrl" />
	<c:url value="/stockoutward/returnedbyNamesFilterUrl" var="returnedbyNamesFilterUrl" />
	<c:url value="/stockoutward/returnedtoNamesFilterUrl" var="returnedtoNamesFilterUrl" />
	
	<c:url value="/common/getPersonListBasedOnPersonType" var="personNamesAutoBasedOnPersonTypeUrl" />
	
	<c:url value="/stockoutward/storeNameCombo" var="storeNameComboBoxUrl" />
	<c:url value="/stockoutward/itmNameCombo" var="itemNameComboBoxUrl" />
	<c:url value="/stockoutward/uomNameCombo" var="uomNameComboBoxUrl" />
	
	<!-- ------------------------------------ Grid -------------------------------------- -->
	
	<kendo:grid name="grid" edit="storeoutwardEdit" remove="deleteoutward" change="onChangeStoreOutward" pageable="true" resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true" groupable="true" filterable="true" navigatable="true">
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" refresh="true" info="true" previousNext="true">
			<kendo:grid-pageable-messages itemsPerPage="Store Goods Outwards per page" empty="No Store Goods Outwards to display" refresh="Refresh all the Store Goods Outwards" 
			display="{0} - {1} of {2} Store Goods Outwards" first="Go to the first page of Store Goods Outwards" last="Go to the last page of Store Goods Outwards" next="Go to the next page of Store Goods Outwards"
			previous="Go to the previous page of Store Goods Outwards"/>
		</kendo:grid-pageable> 
		<kendo:grid-filterable extra="false">
			<kendo:grid-filterable-operators>
				<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains"/>
			</kendo:grid-filterable-operators>
		</kendo:grid-filterable>
		<kendo:grid-editable mode="popup" confirmation="Are you sure you want to delete this Store Goods Outwards record"/>
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Store Goods Outwards" />
			<kendo:grid-toolbarItem name="stockOutwardTemplatesDetailsExport" text="Export To Excel" />
			  <kendo:grid-toolbarItem name="stockOutwardPdfTemplatesDetailsExport" text="Export To PDF" /> 
			<kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterStoreGoodsOutwards()><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>"/>
		</kendo:grid-toolbar>
		<kendo:grid-columns>				
			
			<kendo:grid-column title="Store&nbsp;Name&nbsp;*" editor="storeNameEditor" field="storeName" width="130px">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function storeNameFilter(element) 
						   	{
								element.kendoAutoComplete({
									dataSource : {
										transport : {		
											read :  "${storeNamesoutwardFilterUrl}"
										}
									} 
								});
						   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			 <kendo:grid-column title="Item&nbsp;Name&nbsp;*" editor="itemNameEditor" field="imName" width="100px">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
								function imNameFilter(element) 
							   	{
									element.kendoAutoComplete({
											dataSource : {
											transport : {		
												read :  "${itemNamesoutwardFilterUrl}"
											}
										} 
									});
							   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>	
			<kendo:grid-column title="Unit&nbsp;of&nbsp;Return&nbsp;*" editor="uomNameEditor" field="uom" width="150px">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
								function uomFilter(element) 
							   	{
									element.kendoAutoComplete({
											dataSource : {
											transport : {		
												read :  "${uomNamesoutwardFilterUrl}"
											}
										} 
									});
							   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>	            
	        <kendo:grid-column title="Quantity&nbsp;*" field="itemReturnQuantity" editor="itemReturnQuantityEditor"   filterable="false" width="80px">
	       	</kendo:grid-column>
	        <kendo:grid-column title="Returned&nbsp;By&nbsp;*" field="returnedby" editor="returnbyEditor" width="120px">
			<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function personNameFilter(element) 
						   	{
								element.kendoAutoComplete({
										dataSource : {
										transport : {		
											read :  "${returnedbyNamesFilterUrl}"
										}
									} 
								});
						   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>				
			<kendo:grid-column title="Returned&nbsp;To&nbsp;*" editor="returntoEditor" field="returnedto" width="120px">
			<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function personNameFilter(element) 
						   	{
								element.kendoAutoComplete({
								dataSource : {
										transport : {		
											read :  "${returnedtoNamesFilterUrl}"
										}
									} 
								});
						   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>	
			<kendo:grid-column title="Reason&nbsp;for&nbsp;Return" field="reason" editor="textAreaEditor" width="140px" filterable="false"/>
			<kendo:grid-column title=" " width="159px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit"/>			
					<kendo:grid-column-commandItem name="destroy"  text="Delete"/>
				</kendo:grid-column-command>			
		</kendo:grid-column>
			<kendo:grid-column title="Barcode&nbsp;Generator"   template="<a href='\\\#' class='k-button' id='printSGRBtn'  >Print Gate Pass</a>" width="125px" />
			<kendo:grid-column title="Status" field="status" template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Activate#=data.storeoutwardId#'>#= data.status == 'Approve' ? 'Approve' : 'Approved' #</a>" width="95px" />
      		
        </kendo:grid-columns>
		<kendo:dataSource requestStart="onRequestStartStoreGoodsOutward" requestEnd="onRequestEndStoreGoodsOutward">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-read url="${readStoreOutwardUrl}" dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-create url="${modifyStoreOutwardUrl}/create" dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-update url="${modifyStoreOutwardUrl}/update" dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-destroy url="${deleteStoreOutwardUrl}" dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-parameterMap>
					<script>
						function parameterMap(options, type) {
							return JSON.stringify(options);
						}
					</script>
				</kendo:dataSource-transport-parameterMap>
			</kendo:dataSource-transport>
			<kendo:dataSource-schema parse="StoreGoodsOutwardParse">
				<kendo:dataSource-schema-model id="storeoutwardId">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="storeoutwardId" type="number"/> 
						<kendo:dataSource-schema-model-field name="storeId"/>
						<kendo:dataSource-schema-model-field name="storeName"/>
						<kendo:dataSource-schema-model-field name="imName"/>
						<kendo:dataSource-schema-model-field name="imId"/>
						<kendo:dataSource-schema-model-field name="uom"/>
						<kendo:dataSource-schema-model-field name="uomId"/>
						<kendo:dataSource-schema-model-field name="returnedby"/>
						<kendo:dataSource-schema-model-field name="returnedbyId"/>
						<kendo:dataSource-schema-model-field name="returnedto"/>
						<kendo:dataSource-schema-model-field name="returnedtoId"/>
						<kendo:dataSource-schema-model-field name="status"/>
						<kendo:dataSource-schema-model-field name="itemReturnQuantity" type="number"/>
						<kendo:dataSource-schema-model-field name="reason" type="string"/>
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
	</kendo:grid>
	
	<div id="alertsBox" title="Alert"></div>
	
	<div id="printMain" style="display:none">
	
		<div id="dialogBoxforBarcodeSGR" >
		</div>
		<br>
		
		<button id='print' class="myButton">Print</button>
		
	</div>
	
	<script type="text/javascript">
	$("#grid").on("click",".k-grid-stockOutwardTemplatesDetailsExport", function(e) {
		  window.open("./stockOutwardTemplate/stockOutwardTemplatesDetailsExport");
	});	  

	$("#grid").on("click",".k-grid-stockOutwardPdfTemplatesDetailsExport", function(e) {
		  window.open("./stockOutwardPdfTemplate/stockOutwardPdfTemplatesDetailsExport");
	});
	function clearFilterStoreGoodsOutwards()
	{
	   $("form.k-filter-menu button[type='reset']").slice().trigger("click");
	   var gridStoreIssue = $("#grid").data("kendoGrid");
	   gridStoreIssue.dataSource.read();
	   gridStoreIssue.refresh();
	}
	
	var storeName = "";
	var imName = "";
	var sessionUserLoginName = "";
	
	 $("#grid").on("click", "#temPID", function(e) {
			var button = $(this), enable = button.text() == "Approve";
			var widget = $("#grid").data("kendoGrid");
			var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));

			var result=securityCheckForActionsForStatus("./inventory/stockoutward/statusButton");   
		    if(result=="success"){
	   			if (enable) {
						$.ajax({
							type : "POST",
							url : "./stockoutward/outwardStatus/" + dataItem.id + "/activate",
							dataType : 'text',
							success : function(response) {
								$("#alertsBox").html("");
								$("#alertsBox").html(response);
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
								button.text('Approved');
								$('#grid').data('kendoGrid').dataSource.read();
							}
						});
					} else {
						
							$("#alertsBox").html("");
							$("#alertsBox").html("Already Stock is Approved");
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
		  	 }  						
			
		});	
        
	
	function onChangeStoreOutward(arg) {
		
		var gview = $("#grid").data("kendoGrid");
		var selectedItem = gview.dataItem(gview.select());		
		storeName = selectedItem.storeName;
		imName = selectedItem.imName;
		this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
		
		sessionUserLoginName = '<%=session.getAttribute("userId")%>';
	}
	
	function clearFilterStoreGoodsReturn()
	{
		  $("form.k-filter-menu button[type='reset']").slice().trigger("click");
		  var gridStoreGoodsReturn = $("#gridStoreGoodsReturn").data("kendoGrid");
		  gridStoreGoodsReturn.dataSource.read();
		  gridStoreGoodsReturn.refresh();
	}
	
	function itemReturnQuantityEditor(container, options) 
   	{
		$('<input name="Item return quantity" id="itemReturnQuantity" data-text-field="itemReturnQuantity" data-value-field="itemReturnQuantity" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoNumericTextBox({
			min : 0
		});
		
		$('<span class="k-invalid-msg" data-for="Item return quantity"></span>').appendTo(container);
	}
	
	function storeoutwardEdit(e)
	{$('label[for="status"]').parent().remove();
	$('div[data-container-for="status"]').remove();
		
		
		if($("#grid").data("kendoGrid").dataSource.filter())
		 {
	    		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
	 			var grid = $("#gridStoreGoodsReturn").data("kendoGrid");
	 			grid.dataSource.read();
	 			grid.refresh();
	     } 
		$('a[id=printSGRBtn]').remove();
		$('label[for="undefined"]').closest('.k-edit-label').remove();
		
		if (e.model.isNew()) 
	    {		
			securityCheckForActions("./inventory/stockoutward/createButton");   
			$(".k-window-title").text("New Store Goods Item Outward");
			$(".k-grid-update").text("Save");
			
			$(".k-grid-update").click(function () 
					{
			 			var itemQuantity = $("#itemReturnQuantity").val();
			 			
			 			 if(itemQuantity == 0)
				       	 {
				       		 alert("Quantity cannot be zero");
				       		 return false;
				       	 }
			 			
			  			 if((maximumReturnQuantity > 0) && (itemQuantity > maximumReturnQuantity))
				       	 {
				       		 alert("Maximum "+maximumReturnQuantity+" "+uom+" can be returned to the store with respect to this contract");
				       		 return false;
				       	 }	 
					}); 

	    }
		else
		{		
			securityCheckForActions("./inventory/stockoutward/editButton");   
			
			$('label[for="storeName"]').parent().remove();
			$('div[data-container-for="storeName"]').remove();
			$('label[for="imName"]').parent().remove();
			$('div[data-container-for="imName"]').remove();
			$('label[for="uom"]').parent().remove();
			$('div[data-container-for="uom"]').remove();
			$('label[for="itemReturnQuantity"]').parent().remove();
			$('div[data-container-for="itemReturnQuantity"]').remove();
		
			$(".k-window-title").text("Edit Store Goods Item Outward Details");
			
			e.container.find(".k-grid-cancel").bind("click", function () {
	 	    	var grid = $("#grid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
		    }); 
	 	    
	 	   $(".k-window-action.k-link").click(function(e){
	 		    var grid = $("#grid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
	 	   });
		}
		
		var grid = this;
		e.container.on("keydown", function(e) {        
	        if (e.keyCode == kendo.keys.ENTER) {
	          $(document.activeElement).blur();
	          grid.saveRow();
	        }
	      });
		
		//CLIENT SIDE VALIDATION FOR MULTI SELECT
 		
	}	
	
	function deleteoutward(){
		securityCheckForActions("./inventory/stockoutward/deleteButton");   
	}
	
	function StoreGoodsOutwardParse (response) {   
	    
        return response;
	}
	
	$("#grid").on("click", "#printSGRBtn", function(e) {
		var widget = $("#grid").data("kendoGrid");
		var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
		
		 var data="<table><tbody><tr><th colspan='2' class='thclass'><h1>Store Details</h1></th></tr>";
		 data=data+"<tr><td class='tdclass'>Store Name</td><td class='tdclass'>"+dataItem.storeName+"</td></tr><tr><td class='tdclass'>Item Name</td>"+"<td class='tdclass'>"+dataItem.imName+"</td></tr>"+"<tr><td class='tdclass'>UOM</td><td class='tdclass'>"+dataItem.uom+"</td></tr><tr><td class='tdclass'>Quantity</td><td class='tdclass'>"+dataItem.itemReturnQuantity+"</td></tr><tr><td class='tdclass'>Returned By </td><td class='tdclass'>"+dataItem.returnedby+"</td></tr><tr><td class='tdclass'>Returned To</td><td class='tdclass'>"+dataItem.returnedto+"</td></tr><tr><td class='tdclass'>Reason</td><td class='tdclass'>"+dataItem.reason+"</td></tr></tbody></table>";
       
		$("#dialogBoxforBarcodeSGR").html(data);
		
	 	$("#print").on("click",function(){
			 var prtContent = document.getElementById('dialogBoxforBarcodeSGR');
             var WinPrint = window.open('', '', 'letf=0,top=0,width=800,height=400,toolbar=0,scrollbars=0,status=0');
             WinPrint.document.write(prtContent.innerHTML);
             WinPrint.document.close();
             WinPrint.focus();
             WinPrint.print();
             WinPrint.close();
		}); 
		
         var undo = $("#printMain").kendoWindow({
			visible : false,
			resizable : false,
			modal : true,
			actions : ["Minimize",
					"Maximize", "Close"],
			title : "Gate Pass"
		}).data("kendoWindow");
         undo.open().center();
	});
	
	function storeNameEditor(container, options) 
   	{
		$('<input name="Store" id="storeSGRE" data-text-field="storeName" data-value-field="storeId" data-bind="value:' + options.field + '" required="true"/>')
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
					read :  "${storeNameComboBoxUrl}"
				}
			},
			change : function (e) {
	            if (this.value() && this.selectedIndex == -1) {                    
					alert("Store doesn't exist!");
	                $("#storeSGRE").data("kendoComboBox").value("");
	        	}
	            
	            storeIdSGRE = this.value();

				var combobox = $("#itemsSGRE").data("kendoComboBox");
				combobox.value("");
				var combobox = $("#uomSGRE").data("kendoComboBox");
				combobox.value("");
				var numerictextbox = $("#itemReturnQuantity").data("kendoNumericTextBox");
				numerictextbox.value(0);
				maximumReturnQuantity = 0;
				
				$('label[for="itemReturnQuantity"]').parent().hide();
				$('div[data-container-for="itemReturnQuantity"]').hide();
		    }  
		});
		
		$('<span class="k-invalid-msg" data-for="Store"></span>').appendTo(container);
   	}
	
	function itemNameEditor(container, options) 
   	{
		$('<input name="Store item" id="itemsSGRE" data-text-field="imName" data-value-field="imId" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoComboBox({
			cascadeFrom : "storeSGRE",
			autoBind : false,
			placeholder : "Select item",
			headerTemplate : '<div class="dropdown-header">'
				+ '<span class="k-widget k-header">Photo</span>'
				+ '<span class="k-widget k-header">Contact info</span>'
				+ '</div>',
			template : '<table><tr>'
					+ '<td align="left"><span class="k-state-default"><b>#: data.imName #</b></span><br>'
					+ '<span class="k-state-default"><b>Type:&nbsp;</b>&nbsp;&nbsp;<i>#: data.imType #</i></span><br>'
					+ '</td></tr></table>',
			dataSource : {
				transport : {		
					read :  "${itemNameComboBoxUrl}"
				}
			},
			change : function (e) {
	            if (this.value() && this.selectedIndex == -1) {                    
					alert("Store item doesn't exist!");
	                $("#itemsSGRE").data("kendoComboBox").value("");
	        	}
	            
	           	imIdSGRE = this.value();
	            
				var combobox = $("#uomSGRE").data("kendoComboBox");
				combobox.value("");
				var numerictextbox = $("#itemReturnQuantity").data("kendoNumericTextBox");
				numerictextbox.value(0);
				maximumReturnQuantity = 0;
				
				$('label[for="itemReturnQuantity"]').parent().hide();
				$('div[data-container-for="itemReturnQuantity"]').hide();
		    } 
		});
		
		$('<span class="k-invalid-msg" data-for="Store item"></span>').appendTo(container);
   	}

	function uomNameEditor(container, options) 
   	{
		$('<input name="Item UOM" id="uomSGRE" data-text-field="uom" data-value-field="uomId" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoComboBox({
			cascadeFrom : "itemsSGRE",
			autoBind : false,
			placeholder : "Select uom",
			headerTemplate : '<div class="dropdown-header">'
				+ '<span class="k-widget k-header">Photo</span>'
				+ '<span class="k-widget k-header">Contact info</span>'
				+ '</div>',
			template : '<table><tr>'
				+ '<td align="left"><span class="k-state-default"><b>#: data.uom #</b></span><br>'
				+ '</td></tr></table>',
			dataSource : {
				transport : {		
					read :  "${uomNameComboBoxUrl}"
				}
			},
			change : function (e) {
	            if (this.value() && this.selectedIndex == -1) {                    
					a,lert("Unit of measurement doesn't exist!");
	                $("#uomSGRE").data("kendoComboBox").value("");
	        	}     
	            
	            uomIdSGRE = this.value();
	            var data=this.dataItem();
	           	uom = data.uom;
	           	code = data.code;
	            
	            var numerictextbox = $("#itemReturnQuantity").data("kendoNumericTextBox");
				numerictextbox.value(0);
				
				var data=this.dataItem();
	            
	            var result = 0;
	           	 $.ajax({
	           	     type : "GET",
	           	  	 async: false,
	           	  	 data : {
	           	  	    storeId : storeIdSGRE,
	           	  		imId : imIdSGRE,
	           	  		uomId : uomIdSGRE
						},
	           	     url : './common/getQuantityBasedOnStoreItemAndUom',
	           	     dataType : "JSON",
	           	     success : function(response) {       
	           	     	result=response;
	           	     }
	           	});
	           	 
	           	if(result != -1)
	           	{	
					$('label[for="itemReturnQuantity"]').parent().show();
					$('div[data-container-for="itemReturnQuantity"]').show();
					
					maximumReturnQuantity = result;
					
					$('label[for="itemReturnQuantity"]').text("Item Quantity *\n(Return at max: "+maximumReturnQuantity+" "+uom+")");
				}
	           	else
	           	{
					alert("Item with respect to selected store and item is empty in the ledger");
					$('label[for="itemReturnQuantity"]').parent().hide();
					$('div[data-container-for="itemReturnQuantity"]').hide();
	           	}
		    } 
		});
		
		$('<span class="k-invalid-msg" data-for="Item UOM"></span>').appendTo(container);
   	}

	function returntoEditor(container, options) 
   	{
		$('<input name="Returned to" id="returnedVendor" data-text-field="personName" data-value-field="id" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoComboBox({
			autoBind : false,
			placeholder : "Select vendor",
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
					read :  "${personNamesAutoBasedOnPersonTypeUrl}/Vendor"
				}
			},
			change : function (e) {
	            if (this.value() && this.selectedIndex == -1) {                    
					alert("Vendor doesn't exist!");
	                $("#returnedVendor").data("kendoComboBox").value("");
	        	}
		    } 
		});
		
		$('<span class="k-invalid-msg" data-for="Returned to"></span>').appendTo(container);
	}
	
	function returnbyEditor(container, options) 
   	{
		$('<input name="Returned by" id="returnedStaff" data-text-field="personName" data-value-field="id" data-bind="value:' + options.field + '" required="true"/>')
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
	                $("#returnedStaff").data("kendoComboBox").value("");
	        	}
		    } 
		});
		
		$('<span class="k-invalid-msg" data-for="Returned by"></span>').appendTo(container);
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
	
	function onRequestStartStoreGoodsOutward(e)
	{
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();
        
		/* if (e.type == "create"){
			var gridStoreGoodsReturn = $("#grid").data("kendoGrid");
			gridStoreGoodsReturn.cancelRow();		
		} */	
	}
	
	function onRequestEndStoreGoodsOutward(e) 
	{
		//displayMessage(e, "grid", "Store Goods Outward");
		var resultOperation="";
		if (e.type == "update" && !e.response.Errors) {
   			resultOperation="Record Updated Successfully";	   		
   		}
   		if (e.type == "create" && !e.response.Errors) {
   			resultOperation="Record Created Successfully";	   			
   		}		   		
   		if (e.type == "destroy" && !e.response.Errors) {
   			resultOperation="Record Deleted Successfully";	   			
   		}	
   		
   		if(resultOperation!="" && resultOperation!=null){
   			$("#alertsBox").html("");
   			$("#alertsBox").html(resultOperation);
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
   			
   			var grid = $("#grid").data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
   		}
	}	
	
	</script>
<!-- ------------------------------------------ Style  ------------------------------------------ -->

<style>
	.flat-table {
		  display: block;
		  font-family: sans-serif;
		  -webkit-font-smoothing: antialiased;
		  font-size: 115%;
		  overflow: auto;
		  width: auto;
	  
		  
	}
	.thclass {
		background-color: rgb(112, 196, 105);
		color: white;
		font-weight: normal;
		padding: 20px 30px;
		text-align: center;
	}
	.tdclass {
		background-color: rgb(238, 238, 238);
		color: rgb(111, 111, 111);
		padding: 20px 30px;
	}
	.myButton {
		background-color:white;
		border:1px solid #18ab29;
		
		cursor:pointer;
		color:red;
		font-family:arial;
		font-size:17px;
		padding:8px 15px;
		text-decoration:none;
		text-shadow:0px 1px 0px red;
	}
	
	td {
    	vertical-align: middle;
	}
	
</style>