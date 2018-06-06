<%@include file="/common/taglibs.jsp"%>

	<c:url value="/procurement/vendorPriceList/read" var="readUrl" />
	<c:url value="/procurement/vendorPriceList/create" var="createUrl" />
	<c:url value="/procurement/vendorPriceList/update" var="updateUrl" />
	<c:url value="/procurement/vendorPriceList/destroy" var="destroyUrl" />
	<c:url value="/procurement/vendorPriceList/readPersons" var="personNames" />
	<c:url value="/procurement/vendorPriceList/readItems" var="itemNames" />
	
	<c:url value="/procurement/vendors/readUomDetails" var="uomDetails"/>
	<c:url value="/vendorContracts/getVendorNamesAutoUrl" var="vendorNamesAutoUrl" />
	
	<!-- URLS'S FOR FILTER -->
	<c:url value="/procurement/vendorPriceList/getVendorNameForPriceListFilter" var="getVendorNameForPriceListFilter" />
	<c:url value="/procurement/vendorPriceList/getItemTypeForPriceListFilter" var="getItemTypeForPriceListFilter" />
	<c:url value="/procurement/vendorPriceList/getItemNameForPriceListFilter" var="getItemNameForPriceListFilter" />
	<c:url value="/procurement/vendorPriceList/getUOMForPriceListFilter" var="getUOMForPriceListFilter" />
	<c:url value="/procurement/vendorPriceList/getRateForPriceListFilter" var="getRateForPriceListFilter" />
	<c:url value="/procurement/vendorPriceList/getDeliveryAtPriceListFilter" var="getDeliveryAtPriceListFilter" />	
	<c:url value="/procurement/vendorPriceList/getPaymentTermsPriceListFilter" var="getPaymentTermsPriceListFilter" />
	<c:url value="/procurement/vendorPriceList/getInvoicePayableDaysPriceListFilter" var="getInvoicePayableDaysPriceListFilter" />
	<c:url value="/procurement/vendorPriceList/getStatusPriceListFilter" var="getStatusPriceListFilter" />	
	<c:url value="/procurement/vendorPriceList/getItemTypesUrl" var="getItemTypesUrl" />
	
	
	
	<kendo:grid name="grid" pageable="true"	change="vendorPriceListChangeEvent" resizable="true" filterable="true" sortable="true" reorderable="true" selectable="true" 
	scrollable="true" groupable="true" edit="VendorPriceListEvent" remove="deleteVendorPriceListEvent">	
		<kendo:grid-editable mode="popup" confirmation="Are you sure you want to remove this item?" />
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Enter VendorPriceList Details" />
			<kendo:grid-toolbarItem text="Clear Filter" name="Clear_Filter"/>
		</kendo:grid-toolbar>
		
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" ></kendo:grid-pageable>
			<kendo:grid-filterable extra="false">
		 		<kendo:grid-filterable-operators>
		  			<kendo:grid-filterable-operators-string eq="Is equal to"/>
		  			<kendo:grid-filterable-operators-date lte="Date Before" gte="Date After"/>
				</kendo:grid-filterable-operators>			
		</kendo:grid-filterable>
		
		<kendo:grid-columns>
		    <kendo:grid-column title="VendorPriceList_Id" field="vpId" hidden="true"/>
		    
		    <kendo:grid-column title="Vendors&nbsp;*" field="firstName" width="145px">
		    <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getVendorNameForPriceListFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	 </kendo:grid-column-filterable>
		    </kendo:grid-column>
		    
		    <kendo:grid-column title="Vendor&nbsp;*" field="vendorId"  editor="PersonNames" hidden="true"/>
		    
		    <kendo:grid-column title="Item Type&nbsp;*" field="itemType" editor="itemTypeNames" width="130px">
		    <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getItemTypeForPriceListFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	 </kendo:grid-column-filterable>
		    </kendo:grid-column>		    
		    
		    <kendo:grid-column title="Item&nbsp;*" field="imName" editor="ItemNames" width="130px">
		    <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getItemNameForPriceListFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	 </kendo:grid-column-filterable>
		    </kendo:grid-column>
		    
		    
		    <kendo:grid-column title="UOM&nbsp;*" field="uomId" editor="UomEditorFunction" hidden="true"/>
		    
		    <kendo:grid-column title="UOM&nbsp;*" field="uom" width="130px">
		    <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getUOMForPriceListFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	 </kendo:grid-column-filterable>
		    </kendo:grid-column>
		    
		    <kendo:grid-column title="Rate&nbsp;*" field="rate" format="{0:n0}" width="130px">
		    <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getRateForPriceListFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	 </kendo:grid-column-filterable>
		    </kendo:grid-column>
		    
		    <kendo:grid-column title="Valid From&nbsp;*" field="validFrom" format="{0:dd/MM/yyyy}" width="165px"/>
		    <kendo:grid-column title="Valid To&nbsp;*" field="validTo" format="{0:dd/MM/yyyy}" width="165px"/>
		    
		    <kendo:grid-column title="Delivery At&nbsp;*" field="deliveryAt" width="140px">
		    <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getDeliveryAtPriceListFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	 </kendo:grid-column-filterable>
		    </kendo:grid-column>
		    
		    <kendo:grid-column title="Payment Terms&nbsp;*" field="paymentTerms" width="165px">
		    <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getPaymentTermsPriceListFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	 </kendo:grid-column-filterable>
		    </kendo:grid-column>
		    
		    <kendo:grid-column title="Invoice PayableDays&nbsp;*" field="Invoice_Payable_days" format="{0:n0}" width="170px">
		     <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getInvoicePayableDaysPriceListFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	 </kendo:grid-column-filterable>
		    </kendo:grid-column>
		    
		    <%-- <kendo:grid-column title="Status" field="status" editor="radioStatusEditor" width="70px"/> --%>
		    <kendo:grid-column title="Status" field="status" width="125px">
		    <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getStatusPriceListFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	 </kendo:grid-column-filterable>
		    </kendo:grid-column>
		    		    
		    <kendo:grid-column title="CreatedBy" field="createdBy" width="70px" hidden="true"/>
		    <kendo:grid-column title="LastUpdatedBy" field="lastUpdatedBy" width="70px" hidden="true"/>
			
			<kendo:grid-column title="&nbsp;" width="80px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit"/>
					<%-- <kendo:grid-column-commandItem name="destroy"/> --%> 
				</kendo:grid-column-command>
			</kendo:grid-column>
			
			<kendo:grid-column title=""
				template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Activate#=data.vpId#'>#= data.status == 'Approved' ? 'Reject' : 'Approve' #</a>"
				width="100px" />
				
		</kendo:grid-columns>
		
		<kendo:dataSource requestEnd="onRequestEnd" requestStart="onVendorPriceListRequestStart">
			<kendo:dataSource-transport>
			<kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json" />
			<kendo:dataSource-transport-create url="${createUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-update url="${updateUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-destroy url="${destroyUrl}" dataType="json" type="GET" contentType="application/json" />
				 <%-- <kendo:dataSource-transport-parameterMap>
					<script>
						function parameterMap(options, type) 
						{
							return JSON.stringify(options);
						}
					</script>
				</kendo:dataSource-transport-parameterMap> --%>
			</kendo:dataSource-transport>
			
			<kendo:dataSource-schema>
				<kendo:dataSource-schema-model id="vpId">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="vpId" type="number"/>
						<kendo:dataSource-schema-model-field name="firstName" type="string"/>
						<kendo:dataSource-schema-model-field name="vendorId" type="string"/>
						<kendo:dataSource-schema-model-field name="itemType" type="string"/>
						<kendo:dataSource-schema-model-field name="imName" type="string"/>
						
						<kendo:dataSource-schema-model-field name="rate" type="number">
							<kendo:dataSource-schema-model-field-validation min="1"/>
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="uomId"/>
						
						<kendo:dataSource-schema-model-field name="validFrom" type="date"/>
						<kendo:dataSource-schema-model-field name="validTo" type="date"/>	
											
						<kendo:dataSource-schema-model-field name="deliveryAt" type="string">
							<%-- <kendo:dataSource-schema-model-field-validation pattern="^.{0,20}$" /> --%>
						</kendo:dataSource-schema-model-field>
											
						<kendo:dataSource-schema-model-field name="paymentTerms" type="string">
						  <%-- <kendo:dataSource-schema-model-field-validation pattern="^.{0,20}$" /> --%>
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="Invoice_Payable_days" type="number">
							<kendo:dataSource-schema-model-field-validation required="true" min="1"/>
						</kendo:dataSource-schema-model-field>
												
						<kendo:dataSource-schema-model-field name="status" type="string" defaultValue="Created"/>
						<kendo:dataSource-schema-model-field name="createdBy" type="string"/>
						<kendo:dataSource-schema-model-field name="lastUpdatedBy" type="string"/>
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
	</kendo:grid>
	
	<div id="alertsBox" title="Alert"></div>
	<script>
	
	var status = "";
	function vendorPriceListChangeEvent(e)
	{
		var gview = $("#grid").data("kendoGrid");
		var selectedItem = gview.dataItem(gview.select());
		status = selectedItem.status;
		this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
	}
	
		/**** FOR CLEAR FILTER *****/
		$("#grid").on("click", ".k-grid-Clear_Filter", function()
		{
	    	//custom actions
	   	 	$("form.k-filter-menu button[type='reset']").slice().trigger("click");
			var grid = $("#grid").data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
		});	
		/**** END FILTER *****/
		
		/* function radioStatusEditor(container, options) {
		$(
			'<input id="myForm" type="radio" name=' + options.field + ' value="Active" /> Active &nbsp;&nbsp;&nbsp;&nbsp; <input type="radio" name=' + options.field + ' value="Inactive" id="myForm" /> Inactive <br>')
			.appendTo(container);
	    } */
		
	    function deleteVendorPriceListEvent(e)
	    {
	    	securityCheckForActions("./procurement/vendorPriceList/createButton"); 
	    }
	    
	    $("#grid").on("click", ".k-grid-add", function() 
	    {
	    	if($("#grid").data("kendoGrid").dataSource.filter())
			{
	    		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
	    	    var grid = $("#grid").data("kendoGrid");
	    		grid.dataSource.read();
	    		grid.refresh();
	        }
	    });
	    
	   /*****  For Adding Requisition Details  ******/
	   function VendorPriceListEvent(e)
	   {
	    	$('label[for=uom]').parent().hide();
			$('div[data-container-for="uom"]').hide();			
			
			$("input[name='deliveryAt']").attr('maxlength', '40');
			$("input[name='paymentTerms']").attr('maxlength', '40');
			$("input[name='Invoice_Payable_days']").attr('maxlength', '9');
			
	    	if (e.model.isNew()) 
			{	
	    		securityCheckForActions("./procurement/vendorPriceList/createButton"); 
	    		
	    		
		    	$('a[id="temPID"]').remove();
				$(".k-window-title").text("Add VendorPriceList Details");
				$(".k-grid-update").text("Save");
				
				$('label[for=vpId]').parent().hide();
				$('div[data-container-for="vpId"]').hide();
				
				$('label[for=createdBy]').parent().hide();
				$('div[data-container-for="createdBy"]').hide();
				
				$('label[for=lastUpdatedBy]').parent().hide();
				$('div[data-container-for="lastUpdatedBy"]').hide();
				
				$('label[for=firstName]').parent().hide();
				$('div[data-container-for="firstName"]').hide(); 
				
				$('label[for=status]').parent().hide();
				$('div[data-container-for="status"]').hide(); 
				
				$('label[for=uom]').parent().hide();
				$('div[data-container-for="uom"]').hide();
								
				/************  CANCEL BUTTON ***************/
				 $(".k-grid-cancel").click(function () 
		         {
					 var grid = $("#grid").data("kendoGrid");
					 grid.dataSource.read();
					 grid.refresh();
		         });		 
				 /***********  END CANCEL  ************/
				 
				/*  $(".k-link").click(function () 
		         {
					 var grid = $("#grid").data("kendoGrid");
					 grid.dataSource.read();
					 grid.refresh();
					 grid.refresh();
		         }); */
		   }
	       else
	       {
	    	   securityCheckForActions("./procurement/vendorPriceList/updateButton"); 
	    	   if(status === "Approved")
				 {
				   $("#alertsBox").html("Alert");
				   $("#alertsBox").html("Vendor Pricelist Approved Cannot Be Edited");
				   $("#alertsBox").dialog
				   ({
					   modal : true,
					   buttons : {
						 "Close" : function(){
						    $(this).dialog("close");
					      }
					   }
				   });	
				   var grid = $("#grid").data("kendoGrid");
				   grid.cancelRow();
				   return false;
				}
	    	   /* securityCheckForActions("./procurement/vendorPriceList/updateButton"); */
	    	   
	    	   $('a[id="temPID"]').remove();
				$(".k-window-title").text("Edit VendorPriceList Details");
				$(".k-grid-update").text("Update");
				
				$('label[for=vpId]').parent().hide();
				$('div[data-container-for="vpId"]').hide();
				
				$('label[for=createdBy]').parent().hide();
				$('div[data-container-for="createdBy"]').hide();
				
				$('label[for=lastUpdatedBy]').parent().hide();
				$('div[data-container-for="lastUpdatedBy"]').hide();			
				
				$('label[for=firstName]').parent().hide();
				$('div[data-container-for="firstName"]').hide();
				
				$('label[for=status]').parent().hide();
				$('div[data-container-for="status"]').hide();	
				
				/************  CANCEL BUTTON ***************/
				 $(".k-grid-cancel").click(function () 
		         {
					 var grid = $("#grid").data("kendoGrid");
					 grid.dataSource.read();
					 grid.refresh();
		         });		 
				 /***********  END CANCEL  ************/
				 
				 $(".k-link").click(function () 
		         {
					 var grid = $("#grid").data("kendoGrid");
					 grid.dataSource.read();
					 grid.refresh();
					 grid.refresh();
		         });
	       }
		}		
		
		/*****  ******/
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
				alert("Error: Creating the Vendor PriceList Details\n\n" + errorInfo);
			}
			if (e.type == "update") 
			{
				alert("Error: Updating the Vendor PriceList Details\n\n" + errorInfo);
			}
			var grid = $("#grid").data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
			return false;
		}}
		
		if (e.type == "update" && !e.response.Errors) 
		{
			$("#alertsBox").html("Alert");
			$("#alertsBox").html("Vendor PriceList Details Updated successfully");
			$("#alertsBox").dialog
			({
			   modal : true,
			   buttons : {
				"Close" : function(){
					$(this).dialog("close");
				 }
			   }
			});
			e.sender.read();
		}
		if (e.type == "create" && !e.response.Errors)
		{
			$("#alertsBox").html("Alert");
			$("#alertsBox").html("Vendor PriceList Details Added successfully");
			$("#alertsBox").dialog
			({
			   modal : true,
			   buttons : {
				"Close" : function(){
					$(this).dialog("close");
				 }
			   }
			});
			e.sender.read();
		}
	}		
		/*****  END ****/
	
		function onVendorPriceListRequestStart(e)
		{
			$('.k-grid-update').hide();
	        $('.k-edit-buttons')
	                .append(
	                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
	        $('.k-grid-cancel').hide();
	        
			/* var grid= $("#grid").data("kendoGrid");
			grid.cancelRow(); */
		}
		
		/****  FOR ITEM TYPE EDITOR *****/
		/****** For Requisition Types *****/
		function itemTypeNames(container, options)
		{
			/*   var booleanData = [ {
			text : 'Select',
			value : ""
		    },{
				text : 'Consumables',
				value : "Consumables"
			},{
				text : 'Assets',
				value : "Assets"
			},{
				text : 'Asset Spares',
				value : "Asset Spares"
			},];

			$('<input name="ItemType" required="true"/>').attr('data-bind', 'value:itemType').appendTo(container).kendoDropDownList
			({
				dataSource : booleanData,
				dataTextField : 'text',
				dataValueField : 'value',
			});
			$('<span class="k-invalid-msg" data-for="ItemType"></span>').appendTo(container);   */
			
			
			 $('<input name="Item Type" required="true" data-text-field="itemType" id="itemSupply" data-value-field="imId" data-bind="value:' + options.field + '"/>')
			.appendTo(container).kendoDropDownList({
				autoBind:false,
				placeholder : "Select Item Type",
				headerTemplate : '<div class="dropdown-header">'
					+ '<span class="k-widget k-header">Photo</span>'
					+ '<span class="k-widget k-header">Contact info</span>'
					+ '</div>',
				template : '<table><tr>'
						+ '<td align="left"><span class="k-state-default">Type:<b>#: data.itemType #</b></span><br>'
						+ '<span class="k-state-default">Item&nbsp;Group:&nbsp;<i>#: data.itemGroup #</i></span><br>'
						+ '<span class="k-state-default">Item&nbsp;Name:&nbsp;<i>#: data.itemName #</i></span><br>'
						+ '</td></tr></table>',
			dataSource : {
				transport : {		
					read : "${getItemTypesUrl}"
				}
			}			
	       });
			$('<span class="k-invalid-msg" data-for="Item Type"></span>').appendTo(container);  
			
			
		}
		
		/*** END FOR ITEM TYPE EDITOR ****/
		
		function PersonNames(container, options) 
		{
			$('<input name="Vendor" id="Vendor" data-text-field="vendorName" data-value-field="vendorId" data-bind="value:' + options.field + '" required="true"/>')
			.appendTo(container).kendoComboBox({
				autoBind : false,
				placeholder : "Select vendor",
				headerTemplate : '<div class="dropdown-header">'
					+ '<span class="k-widget k-header">Photo</span>'
					+ '<span class="k-widget k-header">Contact info</span>'
					+ '</div>',
				    template : '<table><tr><td rowspan=2><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.personId#'/>" width=40 height=55 alt=\"No Image to Display\" /></span></td>'
					+ '<td align="left"><span class="k-state-default"><b>#: data.vendorName #</b></span><br>'
					+ '<span class="k-state-default"><i>#: data.personType #</i></span></td></tr></table>',
					dataSource : {
					    transport : {		
							read :  "${vendorNamesAutoUrl}"
						}
					},
					change : function (e) {
			        if (this.value() && this.selectedIndex == -1) {                    
			                alert("Vendor doesn't exist!");
			                $("#Vendor").data("kendoComboBox").value("");
			        }
				    }  
				});				
				$('<span class="k-invalid-msg" data-for="Vendor"></span>').appendTo(container);
			/* $('<input name="Vendor" id="vendor" data-text-field="firstName" data-value-field="vendorId" data-bind="value:' + options.field + '" required="true"/>')
			.appendTo(container).kendoComboBox({
				autoBind : false,
				placeholder : "Select vendor",
				cascadeFrom: "requisition",
				headerTemplate : '<div class="dropdown-header">'
					+ '<span class="k-widget k-header">Photo</span>'
					+ '<span class="k-widget k-header">Contact info</span>'
					+ '</div>',
				template : '<table><tr><td rowspan=2><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.personId#'/>" width=40 height=35 alt=\"No Image to Display\" /></span></td>'
					+ '<td align="left"><span class="k-state-default"><b>#: data.firstName #</b></span><br>'
					+ '<span class="k-state-default"><i>#: data.personType #</i></span></td></tr></table>',
				dataSource : {
					transport : {		
						read :  "${personNames}"
					}
				},
				change : function (e) {
		            if (this.value() && this.selectedIndex == -1) {                    
		                alert("Vendor doesn't exist!");
		                $("#vendor").data("kendoComboBox").value("");
		        	}
			    } 
			});			
			$('<span class="k-invalid-msg" data-for="Vendor"></span>').appendTo(container); */
		}
		
		function ItemNames(container, options) 
		{
			
			$('<input name="Item Name" id="imId" data-text-field="imName" data-value-field="imId" data-bind="value:' + options.field + '" required="true"/>')
			.appendTo(container).kendoComboBox({
	     		//cascadeFrom: "itemSupply",		
				autoBind : false,
				placeholder : "Select Item",				
				headerTemplate : '<div class="dropdown-header">'
					+ '<span class="k-widget k-header">Photo</span>'
					+ '<span class="k-widget k-header">Contact info</span>'
					+ '</div>',
				template : '<table><tr><td rowspan=2><span class="k-state-default"></span></td>'
					+ '<td align="left"><span class="k-state-default"><b>#: data.imNameType #</b></span><br>'
					+ '</td></tr></table>',
		     	cascadeFrom: "itemSupply",		
				dataSource : {
					transport : {		
						read :  "${itemNames}"
					}
				},
				change : function (e) {
		            if (this.value() && this.selectedIndex == -1) {                    
		                alert("Item doesn't exist!");
		                $("#imId").data("kendoComboBox").value("");
		        	}
			    } 
			});			
			$('<span class="k-invalid-msg" data-for="Item Name"></span>').appendTo(container);
		}
		
		/*************  FOR READING UOM *****************/
		/**** FOR UOM EDITOR ****/
		function UomEditorFunction(container, options)
		{
			$('<input name="Uom" id="uomId" data-text-field="uom" data-value-field="uomId" data-bind="value:' + options.field + '" required="true"/>')
			.appendTo(container).kendoComboBox({
				placeholder : "Select UOM",
				cascadeFrom : "imId",
				dataSource : {
					transport : {
						read : "${uomDetails}"
					}
				}, change : function (e) {
		            if (this.value() && this.selectedIndex == -1) {                    
		                alert("UOM doesn't exist!");
		                $("#reqDetailsGrid"+id).data("kendoComboBox").value("");
		        	}	
				}
			});
	       $('<span class="k-invalid-msg" data-for="Uom"></span>').appendTo(container);			
		}
		/**** END FOR UOM EDITOR  ****/
		
		/************ END FOR READING UOM **************/
		
		/*************  FOR ACTIVATE AND DE-Activate VendorPriceList Status  *************************/
		$("#grid").on("click", "#temPID", function(e) 
	    {
			var button = $(this), enable = button.text() == "Approve";
			var widget = $("#grid").data("kendoGrid");
			var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));					
			
			var result=securityCheckForActionsForStatus("./procurement/vendorPriceList/activateButton");
	 		if(result=="success")
		 	{ 
	 			if (enable)
				{
					$.ajax({
						type : "POST",
						dataType : "text",
						url : "./procurement/vendorPriceList/vendorPriceListStatus/" + dataItem.id + "/Approved",
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
							button.text('Reject');
							$('#grid').data('kendoGrid').dataSource.read();
						}
					});
				} 
				else
				{
					$.ajax
					({
						type : "POST",
						dataType : "text",
						url : "./procurement/vendorPriceList/vendorPriceListStatus/" + dataItem.id + "/Rejected",
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
							$('#grid').data('kendoGrid').dataSource.read();
						}
				   });
	           }
		 	}
			
	    });
		
		var res = [];
    	(function($, kendo) {
    		$
    				.extend(
    						true,
    						kendo.ui.validator,
    						{
    							rules :
    							{ // custom rules          	
    								DeliveryAtName : function(input,params) 
    								{
    									if (input.filter("[name='deliveryAt']").length&& input.val()) 
    									{
    										return /^[a-zA-Z]+[._a-zA-Z0-9]*[a-zA-Z0-9]$/.test(input.val());
    									}
    									 return true;
    								},
    								PaymentTerms : function(input,params) 
									{
								        if (input.filter("[name='paymentTerms']").length&& input.val()) 
								        {
								            return /^[a-zA-Z]+[._a-zA-Z0-9]*[a-zA-Z0-9]$/.test(input.val());
								        }
								        return true;
							        },
							        paymentValidator: function (input, params)
							        {
					                     if (input.attr("name") == "paymentTerms") {
					                      return $.trim(input.val()) !== "";
					                     }
					                     return true;
					                 }, 
					                 DeliveryAtValidator : function (input, params)
								     {
						                     if (input.attr("name") == "deliveryAt") {
						                      return $.trim(input.val()) !== "";
						                     }
						                     return true;
						             },
						             rateValidator : function (input, params)
								     {
						                     if (input.attr("name") == "rate") {
						                      return $.trim(input.val()) !== "";
						                     }
						                     return true;
						             },
						             rateValueValidator : function (input, params)
								     {
						           	  if (input.attr("name") == "rate") {
				  						     return /^[0-9]{0,10}$/.test(input.val());
			  						  }
		  					         return true;
						             },
						             fromValidator : function (input, params)
								     {
						                     if (input.attr("name") == "validFrom") {
						                      return $.trim(input.val()) !== "";
						                     }
						                     return true;
						             },
						             toValidator : function (input, params)
								     {
						                     if (input.attr("name") == "validTo") {
						                      return $.trim(input.val()) !== "";
						                     }
						                     return true;
						             },
						             toDateLessThanValidation : function(input,params) 
						             {
										if (input.filter("[name = 'validTo']").length && input.val()) 
										{
											var selectedDate = input.val();
											var todaysDate = $.datepicker.formatDate('dd/mm/yy',new Date());
											var flagDate = false;
											if ($.datepicker.parseDate('dd/mm/yy',selectedDate) >= $.datepicker.parseDate('dd/mm/yy',todaysDate)) 
											{
											   flagDate = true;
											}
											return flagDate;
										}
										return true;
										}
							        },
    							messages : { 
    								DeliveryAtName: "Enter valid Delivary At Name",
    								PaymentTerms: "Enter valid Payment Terms",
    								paymentValidator : "Enter PaymentTerms",
    								DeliveryAtValidator : "Enter Delivery At Info",
    								rateValidator : "Rate Is Required",
    								fromValidator : "From Date Required",
    								toValidator : "To Date Required",
    								toDateLessThanValidation : "To Date Cannot Be Less Than From Date",
    								rateValueValidator:"Rate is too large it allows upto 10 values",
    							}
    						});
    	})(jQuery, kendo);
		
	</script>
