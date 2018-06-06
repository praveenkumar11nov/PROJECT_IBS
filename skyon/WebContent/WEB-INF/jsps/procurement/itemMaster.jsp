<%@include file="/common/taglibs.jsp"%>

<script src="http://code.highcharts.com/modules/data.js"></script>
<script src="resources/js/plugins/charts/highcharts.js"></script>
<script src="resources/js/plugins/charts/exporting.js"></script>

	<c:url value="/procurement/itemMaster/read" var="readUrl" />
	<c:url value="/procurement/itemMaster/create" var="createUrl" />
	<c:url value="/procurement/itemMaster/update" var="updateUrl" />
	<c:url value="/procurement/itemMaster/destroy" var="destroyUrl" />

	<c:url value="/procurement/itemMaster/readItemPurchaseUom" var="itemPurchaseUomUrl" />
	<c:url value="/procurement/itemMaster/readItemUomIssue" var="itemUomIssueUrl" />

	<c:url value="/procurementUom/uom/readUomBasedOnItemId" var="uomRead" />
	<c:url value="/procurementUom/uom/createUom" var="createUom" />
	<c:url value="/procurementUom/uom/updateUom" var="updateUom" />
	<c:url value="/procurementUom/uom/destroyUom" var="uomDestroy" />

	<c:url value="/procurementUom/uom/getUom" var="uomUrl" />
	<c:url value="/procurementUom/uom/getCode" var="codeUrl" />
	
	<!--CHECK imName Unique  -->
	<c:url value="/procurement/itemMaster/getItemNames" var="readItemNames"/>
	
	<!-- URL'S FOR ITEM_MASTER FILTER -->
	<c:url value="/procurement/itemMasterFilter/getItemMasterGroupFilter" var="getItemMasterGroupFilter" />
	<c:url value="/procurement/itemMasterFilter/getItemMasterReqTypeFilter" var="getItemMasterReqTypeFilter" />
	<c:url value="/procurement/itemMasterFilter/getItemMasterUOMFilter" var="getItemMasterUOMFilter" />
	<c:url value="/procurement/itemMasterFilter/getItemMasterNameFilter" var="getItemMasterNameFilter" />
	<c:url value="/procurement/itemMasterFilter/getItemMasterDescriptionNameFilter" var="getItemMasterDescriptionNameFilter" />
	<c:url value="/procurement/itemMasterFilter/getItemMasterOptimalStockFilter" var="getItemMasterOptimalStockFilter" />
	<c:url value="/procurement/itemMasterFilter/getItemMasterPurchaseUOMFilter" var="getItemMasterPurchaseUOMFilter" />
	<c:url value="/procurement/itemMasterFilter/getItemMasterUomIssueFilter" var="getItemMasterUomIssueFilter" />

	<kendo:grid name="grid" change="onChangeItemMaster" detailTemplate="uomTemplate" remove="deleteItemMaster" pageable="true" resizable="true" filterable="true" sortable="true" reorderable="true" selectable="true"
		scrollable="true" groupable="true" edit="itemMasterEvent">
		<kendo:grid-editable mode="popup"
			confirmation="Are you sure you want to remove this item?" />
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Enter ItemMaster Deatils" />
			<kendo:grid-toolbarItem text="Clear Filter" name="Clear_Filter" />
			<%-- <kendo:grid-toolbarItem text="Show Graph" name="showGraph"/> --%>
		</kendo:grid-toolbar>

		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10"
			input="true" numeric="true" refresh="true"></kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
			<kendo:grid-filterable-operators>
				<kendo:grid-filterable-operators-string eq="Is equal to" />
			</kendo:grid-filterable-operators>
		</kendo:grid-filterable>

		<kendo:grid-columns>
			<kendo:grid-column title="Item Group&nbsp;*" field="imGroup" editor="itemGroupEditor" width="95px">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function getItemMasterGroupFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getItemMasterGroupFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	 </kendo:grid-column-filterable>
	    	</kendo:grid-column>
			
			<kendo:grid-column title="Requisition Type&nbsp;*" field="imType" editor="RequisitionTypeNames" width="125px">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function getItemMasterReqTypeFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getItemMasterReqTypeFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	 </kendo:grid-column-filterable>
	    	</kendo:grid-column>
			
			<kendo:grid-column title="Uom Class&nbsp;*" field="uomClass" editor="UomClass" width="110px">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function getItemMasterUOMFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getItemMasterUOMFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	 </kendo:grid-column-filterable>
	    	</kendo:grid-column>
			
			<kendo:grid-column title="Item Name&nbsp;*" field="imName" width="100px">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function getItemMasterNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getItemMasterNameFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	 </kendo:grid-column-filterable>
	    	</kendo:grid-column>
			
			<kendo:grid-column title="Item Description&nbsp;*" field="imDescription" editor="itemDescriptionEditor" width="130px">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function getItemMasterDescriptionNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getItemMasterDescriptionNameFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	 </kendo:grid-column-filterable>
	    	</kendo:grid-column>
			
			<kendo:grid-column title="Item OptimalStock&nbsp;*" field="imOptimal_Stock" format="{0:n0}" width="135px">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function getItemMasterOptimalStockFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getItemMasterOptimalStockFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	 </kendo:grid-column-filterable>
	    	</kendo:grid-column>
			
			<kendo:grid-column title="Vendor Incident Date&nbsp;*" field="imCreatedDate" format="{0: dd/MM/yyyy}" width="145px"/>
			
			<!-- For Child Grid -->
			<kendo:grid-column title="UOM&nbsp;*" field="uom" width="70px" hidden="true"/>
			<kendo:grid-column title="CODE&nbsp;*" field="code" width="70px" hidden="true"/>
			<!-- End For Child grid -->
			
			<kendo:grid-column title="Item Purchase" field="imPurchaseUom" editor="ItemPurchaseUOM" width="115px">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function getItemMasterPurchaseUOMFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getItemMasterPurchaseUOMFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	 </kendo:grid-column-filterable>
	    	</kendo:grid-column>
			
			<kendo:grid-column title="Item UOM Issue" field="imUomIssue" editor="ItemUomIssue" width="125px">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function getItemMasterUomIssueFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getItemMasterUomIssueFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	 </kendo:grid-column-filterable>
	    	</kendo:grid-column>
			<kendo:grid-column title="Reorder Status" field="reorderLevel" width="110px" />
			<kendo:grid-column title="Created By" field="createdBy" width="70px" hidden="true" />
			<kendo:grid-column title="LastUpdatedBy" field="lastUpdatedBy" width="70px" hidden="true" />

			<kendo:grid-column title="&nbsp;" width="195px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit" />
					<kendo:grid-column-commandItem name="Show Graph" click="showGraphClick" />
					<%-- <kendo:grid-column-commandItem name="destroy" /> --%>
				</kendo:grid-column-command>
			</kendo:grid-column>

		</kendo:grid-columns>

		<kendo:dataSource requestEnd="onRequestEnd" requestStart="onItemMasterRequestStart">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="GET" contentType="application/json" />
				<kendo:dataSource-transport-create url="${createUrl}" dataType="json" type="GET" contentType="application/json" />
				<kendo:dataSource-transport-update url="${updateUrl}" dataType="json" type="GET" contentType="application/json" />
				<kendo:dataSource-transport-destroy url="${destroyUrl}" dataType="json" type="POST" contentType="application/json" />
				<%-- <kendo:dataSource-transport-parameterMap>
					<script>
						function parameterMap(options, type) {
							return JSON.stringify(options);
						}
					</script>
				</kendo:dataSource-transport-parameterMap> --%>
			</kendo:dataSource-transport>

			<kendo:dataSource-schema >
				<kendo:dataSource-schema-model id="imId">
					<kendo:dataSource-schema-model-fields>
						<%-- <kendo:dataSource-schema-model-field name="imId" type="number">
						</kendo:dataSource-schema-model-field> --%>
						<kendo:dataSource-schema-model-field name="imGroup" type="string" />
						<kendo:dataSource-schema-model-field name="imType" type="string" />
						<kendo:dataSource-schema-model-field name="uomClass" type="string" />
						
						<kendo:dataSource-schema-model-field name="imName" type="string">
						  <%-- <kendo:dataSource-schema-model-field-validation pattern="^.{0,20}$" /> --%>
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="imDescription" type="string" />
						
						<kendo:dataSource-schema-model-field name="imOptimal_Stock" type="number">
							<kendo:dataSource-schema-model-field-validation  min="1"/>
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="imCreatedDate" type="date" />						
						<kendo:dataSource-schema-model-field name="imPurchaseUom" type="string" />
						<kendo:dataSource-schema-model-field name="imUomIssue" type="string" />
						<kendo:dataSource-schema-model-field name="reorderLevel" defaultValue="No" type="string" />						
						<kendo:dataSource-schema-model-field name="createdBy" type="string" />
						<kendo:dataSource-schema-model-field name="lastUpdatedBy" type="string" />
						
						<!-- FOR Child Grid -->
						<kendo:dataSource-schema-model-field name="uom" type="string" />
						<kendo:dataSource-schema-model-field name="code" type="string" />
						<!-- End For Child Grid -->
						
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
	</kendo:grid>

	<kendo:grid-detailTemplate id="uomTemplate">
		<kendo:grid name="uomDetailsGrid_#=imId#" selectable="true" sortable="true" scrollable="true" edit="uomEvent" dataBound="UomEventDatabound" remove="deleteUOMEvent">
			<kendo:grid-editable mode="popup" confirmation="Are you sure you want to remove this item?"  />
			<kendo:grid-toolbar>
				<kendo:grid-toolbarItem name="create" text="Add UOM Details" />
			</kendo:grid-toolbar>

			<kendo:grid-columns>
				<kendo:grid-column title="UOM Id" field="uomId" width="70px" hidden="true" />
				<kendo:grid-column title="Item Id&nbsp;*" field="imId" width="70px" hidden="true" />
				<kendo:grid-column title="Base UOM&nbsp;*" field="baseUom" editor="BaseUom" width="70px" />
				<kendo:grid-column title="UOM&nbsp;*" field="uom" editor="UomEditor" width="70px" />
				<kendo:grid-column title="Code&nbsp;*" field="code" editor="CodeEditor" width="70px" />
				<kendo:grid-column title="Conversion&nbsp;*" field="uomConversion" format="{0:n0}" width="70px"/>
				<kendo:grid-column title="dummy&nbsp;*" field="dummy" width="70px" hidden="true" />
			
				<kendo:grid-column title="Status" field="status" width="70px"/>

				<kendo:grid-column title="&nbsp;" width="100px">
					<kendo:grid-column-command>
						<kendo:grid-column-commandItem name="edit" />
						<kendo:grid-column-commandItem name="destroy"/>
					</kendo:grid-column-command>
				</kendo:grid-column>
			</kendo:grid-columns>

			<kendo:dataSource requestEnd="onUomRequestEnd" requestStart="onUomRequestStart">
				<kendo:dataSource-transport>
					<kendo:dataSource-transport-read url="${uomRead}/#=imId#" dataType="json" type="GET" contentType="application/json" />
					<kendo:dataSource-transport-create url="${createUom}/#=imId#" dataType="json" type="POST" contentType="application/json" />
					<kendo:dataSource-transport-update url="${updateUom}/#=imId#" dataType="json" type="POST" contentType="application/json" />
					<kendo:dataSource-transport-destroy url="${uomDestroy}" dataType="json" type="POST" contentType="application/json" />
					<kendo:dataSource-transport-parameterMap>
						<script>
							function parameterMap(options, type) {
								return JSON.stringify(options);
							}
						</script>
					</kendo:dataSource-transport-parameterMap>
				</kendo:dataSource-transport>

				<kendo:dataSource-schema>
					<kendo:dataSource-schema-model id="uomId">
						<kendo:dataSource-schema-model-fields>
							<kendo:dataSource-schema-model-field name="baseUom" type="string"/>
							<kendo:dataSource-schema-model-field name="uom" type="string"/>
							<kendo:dataSource-schema-model-field name="code" type="string"/>
							<kendo:dataSource-schema-model-field name="status" type="string"/>
							<kendo:dataSource-schema-model-field name="uomConversion" type="number">
							   <kendo:dataSource-schema-model-field-validation min="1" />
							</kendo:dataSource-schema-model-field>
						</kendo:dataSource-schema-model-fields>
					</kendo:dataSource-schema-model>
				</kendo:dataSource-schema>
			</kendo:dataSource>
		</kendo:grid>
	</kendo:grid-detailTemplate>


	<div id="alertsBox" title="Alert"></div>
    <div id="container" style="min-width: 725px; height: 390px; margin: 0 auto"></div> 
    <div id="shashi" style="color: black"></div>
   
   <script>
    
    var janCredit = "";
    var febCredit = "";
    var marCredit = "";
    var aprCredit = "";
    var mayCredit = "";
    var junCredit = "";
    var julCredit = "";
    var augCredit = "";
    var septCredit = "";
    var octCredit = "";
    var novCredit = "";
    var decCredit = "";
    
    var janDebit = "";
    var febDebit = "";
    var marDebit = "";
    var aprDebit = "";
    var mayDebit = "";
    var junDebit = "";
    var julDebit = "";
    var augDebit = "";
    var septDebit = "";
    var octDebit = "";
    var novDebit = "";
    var decDebit = "";
    
    function showGraphClick(e)
    {
    	$(document).ready(function() 
    	{	
    	   $('#container').hide();
    	      $(function () {
    	  });
    	});
    	
    	//AJAX CALL TO READ PROCURED ITEMS(CREDITTED)
    	$.ajax
	    ({
            type : "POST",
            dataType : "json",
            async : false,
	        url :"./itemMaster/readCreditedItemsForGraph/"+SelectedItemMasterRowId,
	        success : function(response)
	        {
	        	if(response==null || response==""){
	        		
        		 	$("#alertsBox").html("");
					$("#alertsBox").html("Graph is Not Available for this Item");
					$("#alertsBox").dialog({
						modal: true,
						buttons: {
							"Close": function() {
								$( this ).dialog( "close" );
							}
						}
						}); 
	    		 
	    		$('#grid').data('kendoGrid').dataSource.read(); 
        		
        	   }
        	
        	  else {
        	
	        	for(var s = 0; s<response.length; s++)
		 		   { 
		              responseVal = response[s];
		              if(responseVal.monthValue == 'Jan'){
		            	  janCredit = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Feb'){
		            	  febCredit = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Mar'){
		            	  marCredit = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Apr'){
		            	  aprCredit = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'May'){
		            	  mayCredit = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Jun'){
		            	  junCredit = responseVal.id;
		              }
		              if(responseVal.monthValue == 'Jul'){
		            	  julCredit = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Aug'){
		            	  augCredit = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Sept'){
		            	  septCredit = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Oct'){
		            	  octCredit = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Nov'){
		            	  novCredit = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Dec'){
		            	  decCredit = responseVal.graphIndex;
		              }		              
		 		   }
        	  }
	        }
	    });
    	
    	//AJAX CALL TO READ CONSUMED ITEMS(DEBITTED)
    	$.ajax
	    ({
            type : "POST",
            dataType : "json",
            async : false,
	        url :"./itemMaster/readDebittedItemsForGraph/"+SelectedItemMasterRowId,
	        success : function(response)
	        {
	        	if(response==null || response==""){
	        		
	        		 	$("#alertsBox").html("");
						$("#alertsBox").html("Graph is Not Available for this Item");
						$("#alertsBox").dialog({
							modal: true,
							buttons: {
								"Close": function() {
									$( this ).dialog( "close" );
								}
							}
							}); 
		    		 
		    		$('#grid').data('kendoGrid').dataSource.read(); 
	        		
	        	}
	        	
	        	else {
	        	
	        	for(var s = 0; s<response.length; s++)
		 		   { 
		              responseVal = response[s];
		              if(responseVal.monthValue == 'Jan'){
		            	  janDebit = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Feb'){
		            	  febDebit = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Mar'){
		            	  marDebit = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Apr'){
		            	  aprDebit = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'May'){
		            	  mayDebit = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Jun'){
		            	  junDebit = responseVal.id;
		              }
		              if(responseVal.monthValue == 'Jul'){
		            	  julDebit = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Aug'){
		            	  augDebit = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Sept'){
		            	  septDebit = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Oct'){
		            	  octDebit = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Nov'){
		            	  novDebit = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Dec'){
		            	  decDebit = responseVal.graphIndex;
		              }		              
		 		   }
	        	}
	        }
	    })
	    $('#container').highcharts
        ({
 	        chart: {
 	           type: 'column'
 	        },
 	        title: {
 	                text: "Item Name - "+itemName
 	        },
 	        subtitle: {
 	           text: ''
 	        },
 	        xAxis: {
 	           categories: ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sept','Oct','Nov','Dec']
 	        },
 	        yAxis: {
 	           //min: -1,
 	           //max: 250,
 	           tickInterval: 10,
 	           title: {
 	              text: 'Item Optimal Stock'
 	           }
 	        },
 	        credits : {
					enabled : false
				},
 	        tooltip: {
 	             headerFormat: '<span style="font-size:10px">{point.key}</span><table>',pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
 	             '<td style="padding:0"><b>{point.y:.1f}</b></td></tr>',footerFormat: '</table>',
 	             shared: true,
 	             useHTML: true
 	        },
 	        plotOptions: {
 	             column: {
 	               pointPadding: 0.2,
 	               borderWidth: 0
 	             }
 	         },
 	         series: [{
 	              name: 'Procured',
 	              data: [janCredit, febCredit, marCredit,aprCredit, mayCredit, junCredit,julCredit, augCredit, septCredit,octCredit,novCredit,decCredit]
 	         },{
 	              name: 'Consumed',
 	              data: [janDebit, febDebit, marDebit,aprDebit, mayDebit, junDebit,julDebit, augDebit, septDebit,octDebit,novDebit,decDebit]
 	         }]
 	    }); 
 	$('#container').show();
 	var wnd2 = $("#container").kendoWindow(
	    {
		    visible : false,
			resizable : false,
			modal : true,
			actions : [ "Custom", "Minimize","Maximize", "Close" ],
			title : "Item Consumption Graph"
		}).data("kendoWindow");
		wnd2.center().open();
    }
   </script>
   
   
    <!-- <script>
		var janStock = 0;
		var febStock = 0;
		var marStock = 0;
		var aprStock = 0;
		var mayStock = 0;
		var junStock = 0;
		var julStock =0;
		var augStock =0;
		var septStock = 0;
		var octStock = 0;
		var novStock = 0;
		var decStock = 0;
		
		var janDebit = 0;
		var febDebit = 0;
		var marDebit = 0;
		var aprDebit = 0;
		var mayDebit = 0;
		var junDebit = 0;
		var julDebit = 0;
		var augDebit = 0;
		var sepDebit = 0;
		var octDebit = 0;
		var novDebit = 0;
		var decDebit = 0;
		
        $(document).ready(function() 
    	{	
    	    $('#container').hide();
    	    $(function () {
    	       
    	    });
    	});

        var Feb = 0;  
        var resp1 = "";
        var resp2 = "";
        var responseVal = "";
        $("#grid").on("click", ".k-grid-showGraph", function(e)
        { 

		     $.ajax
		    ({
	            type : "POST",
	            dataType : "json",
	            async : false,
		        url :"./itemMaster/readCreditedDataForGraph",
		        success : function(response)
		        {		
		           for(var s = 0; s<response.length; s++)
		 		   { 
		              responseVal = response[s];
		              if(responseVal.monthValue == 'Jan'){
		            	  janStock = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Feb'){
		            	  febStock = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Mar'){
		            	  marStock = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Apr'){
		            	  aprStock = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'May'){
		            	  mayStock = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Jun'){
		            	  junStock = responseVal.id;
		              }
		              if(responseVal.monthValue == 'Jul'){
		            	  julStock = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Aug'){
		            	  augStock = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Sept'){
		            	  septStock = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Oct'){
		            	  octStock = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Nov'){
		            	  novStock = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Dec'){
		            	  decStock = responseVal.graphIndex;
		              }		              
		 		   }
		         }
		     });
		     $.ajax
		    ({
	            type : "POST",
	            dataType : "json",
	            async : false,
		        url :"./itemMaster/readDebittedDataForGraph",
		        success : function(response)
		        {	
		           for(var s = 0; s<response.length; s++)
		 		   { 
		              var responseVal = response[s];
		              if(responseVal.monthValue == 'Jan'){
		            	  janDebit = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Feb'){
		            	  febDebit = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Mar'){
		            	  marDebit = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Apr'){
		            	  aprDebit = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'May'){
		            	  mayDebit = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Jun'){
		            	  junDebit = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Jul'){
		            	  julDebit = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Aug'){
		            	  augDebit = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Sept'){
		            	  sepDebit = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Oct'){
		            	  octDebit = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Nov'){
		            	  novDebit = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Dec'){
		            	  decDebit = responseVal.graphIndex;
		              }		              
		 		   }
		         }
		     });  

		    $('#container').highcharts
	           ({
	    	        chart: {
	    	           type: 'column'
	    	        },
	    	        title: {
	    	                text: 'Average Item Consumption'
	    	        },
	    	        subtitle: {
	    	           text: ''
	    	        },
	    	        xAxis: {
	    	           categories: ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sept','Oct','Nov','Dec']
	    	        },
	    	        yAxis: {
	    	           //min: -1,
	    	           max: 250,
	    	           tickInterval: 10,
	    	           title: {
	    	              text: 'Item Optimal Stock'
	    	           }
	    	        },
	    	        credits : {
						enabled : false
					},
	    	        tooltip: {
	    	             headerFormat: '<span style="font-size:10px">{point.key}</span><table>',pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
	    	             '<td style="padding:0"><b>{point.y:.1f}</b></td></tr>',footerFormat: '</table>',
	    	             shared: true,
	    	             useHTML: true
	    	        },
	    	        plotOptions: {
	    	             column: {
	    	               pointPadding: 0.2,
	    	               borderWidth: 0
	    	             }
	    	         },
	    	         series: [{
	    	              name: 'Procured',
	    	              data: [janStock, febStock, marStock,aprStock, mayStock, junStock,julStock, augStock, septStock,octStock,novStock,decStock]
	    	         },{
	    	              name: 'Consumed',
	    	              data: [janDebit, febDebit, marDebit,aprDebit, mayDebit, junDebit,julDebit, augDebit, sepDebit,octDebit,novDebit,decDebit]
	    	         }]
	    	    }); 
        	$('#container').show();
        	var wnd2 = $("#container").kendoWindow(
		    {
			    visible : false,
				resizable : false,
				modal : true,
				actions : [ "Custom", "Minimize","Maximize", "Close" ],
				title : "Item Consumption"
			}).data("kendoWindow");
			wnd2.center().open(); 
        });
      </script>  -->         

        
	<script>
	function itemGroupEditor(container,options)
	{
		var booleanData = [ {
			text : 'Select',
			value : ""
		    },{
				text : 'Electronics',
				value : "Electronics"
			},{
				text : 'Plumbing',
				value : "Plumbing"
			},];
			$('<select name="Item Group" required="true" class="itemGroupClass"/>').attr('data-bind','value:imGroup').appendTo(container).kendoDropDownList({
				dataSource : booleanData,
				dataTextField : 'text',
				dataValueField : 'value',
			}); 
			$('<span class="k-invalid-msg" data-for="Item Group"></span>').appendTo(container);	
	}
		var SelectedItemMasterRowId = "";
		var itemName = "";
		function onChangeItemMaster(arg) {
			var gview = $("#grid").data("kendoGrid");
			var selectedItem = gview.dataItem(gview.select());
			SelectedItemMasterRowId = selectedItem.imId;
			itemName = selectedItem.imName;
			this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
		}
		
		function itemDescriptionEditor(container, options) 
		{
	        $('<textarea name="Item Description To" data-text-field="imDescription" data-value-field="imDescription" data-bind="value:' + options.field + '" style="width: 148px; height: 46px;" required="true"/>')
	             .appendTo(container);
	    	$('<span class="k-invalid-msg" data-for="Item Description"></span>').appendTo(container); 
		} 
		
		/** DataBound For UOM **/
		var statusVal = "";
		var newStatus = "";
		function UomEventDatabound(e)
		{
			var grid = $('#uomDetailsGrid_' + SelectedItemMasterRowId).data("kendoGrid");
		    var gridData = grid._data;		    
		    var i = 0;
		    this.tbody.find("tr td:last-child").each(function (e) 
		    {
		    	statusVal = gridData[i].status;
			    if(statusVal === "Created")
			    {
			    	newStatus = "Active";
			    	$('<button id="activeButton" class="k-button k-button-icontext" onclick="uomActivateClick()">Activate</button>').appendTo(this);
			    }			    
			    i++;
		    });
		}
		/** End For Databound **/
		
		function uomActivateClick()
		{
			var result=securityCheckForActionsForStatus("./procurement/uom/activateButton");   
		 	if(result=="success")
		 	{
		 		var gridUom = $('#uomDetailsGrid_' + SelectedItemMasterRowId).data("kendoGrid");
				var selectedStatus = gridUom.dataItem(gridUom.select());
				var uomId= selectedStatus.uomId;
				$.ajax
				({
					type : "POST",
					dataType : "text",
					url : "./procurementUom/uomDetails/updateUomStatus/" + uomId +"/"+newStatus,				
					success : function(response) 
					{
						$("#alertsBox").html("");
						$("#alertsBox").html(response);
						$("#alertsBox").dialog
						({
							modal : true,
							buttons :{
							  "Close" : function(){
									$(this).dialog("close");
								}
							}
						});
						$('#uomDetailsGrid_' + SelectedItemMasterRowId).data('kendoGrid').dataSource.read();
					}
				});
		 	}
		}
		
		/**** FOR CLEAR FILTER *****/
		$("#grid").on("click",".k-grid-Clear_Filter",function() 
		{
			$("form.k-filter-menu button[type='reset']").slice().trigger("click");
			var grid = $("#grid").data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
		});
		/**** END FILTER *****/

		/****  FOR REQUISITION TYPES EDITOR ****/
		/****** For Requisition Types *****/
		function RequisitionTypeNames(container, options)
		{
			var booleanData = [ {
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

			$('<input name="Requisition Type" required="true"/>').attr('data-bind', 'value:imType').appendTo(container).kendoDropDownList
			({
				dataSource : booleanData,
				dataTextField : 'text',
				dataValueField : 'value',
			});
			$('<span class="k-invalid-msg" data-for="Requisition Type"></span>').appendTo(container);
		}
		
		
		/****  END FOR REQUISITION TYPES EDITOR  ****/
		$("#grid").on("click", ".k-grid-add", function() 
	    {
	    	/* if($("#grid").data("kendoGrid").dataSource.filter())
			{
	    		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
	    	    var grid = $("#grid").data("kendoGrid");
	    		grid.dataSource.read();
	    		grid.refresh();
	        } */
	    });
		
		var getItemName = "";
		var resItemName = [];
		var res1 = new Array();
		function itemMasterEvent(e) 
		{
			$('label[for=imCreatedDate]').parent().hide();
			$('div[data-container-for="imCreatedDate"]').hide();
			$("input[name='imName']").attr('maxlength', '30');
			$("input[name='imOptimal_Stock']").attr('maxlength', '9');
			$("input[name='uom']").attr('maxlength', '20');
			$("input[name='code']").attr('maxlength', '20');
			
			$('label[for=reorderLevel]').parent().hide();
			$('div[data-container-for="reorderLevel"]').hide();
			
			if (e.model.isNew()) 
			{
				securityCheckForActions("./procurement/itemMaster/createButton");
				$.ajax
				({
				  type : "GET",
				  dataType:"text",
				  url : '${readItemNames}',
				  dataType : "JSON",
				  success : function(response) 
				  {
					   for(var i = 0; i<response.length; i++) {
					   res1[i] = response[i];			   
					 }
				  }
			    });
				
				$('.k-edit-field .k-input').first().focus();
				$(".k-window-title").text("Add ItemMaster Details");
				$(".k-grid-update").text("Save");

				$('label[for=imId]').parent().hide();
				$('div[data-container-for="imId"]').hide();

				$('label[for=createdBy]').parent().hide();
				$('div[data-container-for="createdBy"]').hide();

				$('label[for=lastUpdatedBy]').parent().hide();
				$('div[data-container-for="lastUpdatedBy"]').hide();

				$('label[for=imPurchaseUom]').remove();
				$('div[data-container-for="imPurchaseUom"]').remove();

				$('label[for=imUomIssue]').remove();
				$('div[data-container-for="imUomIssue"]').remove();	
			}
			else
		    {
				securityCheckForActions("./procurement/itemMaster/updateButton"); 
				getItemName = e.model.imName;
				
			    $(".k-window-title").text("Edit Item Master Details");
			    $('label[for=imId]').parent().hide();
				$('div[data-container-for="imId"]').hide();

				$('label[for=createdBy]').parent().hide();
				$('div[data-container-for="createdBy"]').hide();

				$('label[for=lastUpdatedBy]').parent().hide();
				$('div[data-container-for="lastUpdatedBy"]').hide();

				$('label[for=imPurchaseUom]').hide();
				$('div[data-container-for="imPurchaseUom"]').hide();

				$('label[for=imUomIssue]').hide();
				$('div[data-container-for="imUomIssue"]').hide();
				
				$('label[for=uom]').remove();
				$('div[data-container-for="uom"]').remove();
				
				$('label[for=code]').remove();
				$('div[data-container-for="code"]').remove();
		    }
		}

		function deleteItemMaster(e)
		{
			securityCheckForActionsForStatus("./procurement/itemMaster/deleteButton");			
		}
		
		/****** FOR UOM Add New ********/
		var code = "";
		var uom = "";
		var resUom = [];
		var resCode = [];
		
		var getBaseUom = "";
		var getUomVal = "";
		var getCode = "";
		
		function deleteUOMEvent(e)
		{
			securityCheckForActionsForStatus("./procurement/uom/deleteButton");		
		}
		
		function uomEvent(e)
		{
			$("input[name='uom']").attr('maxlength', '10');
			$("input[name='code']").attr('maxlength', '10');
			$("input[name='uomConversion']").attr('maxlength', '9');		
			
			if (e.model.isNew()) 
			{ 
				securityCheckForActionsForStatus("./procurement/uom/createButton");		
				
				$.ajax({
					type : "GET",
					url : "${uomUrl}/"+ SelectedItemMasterRowId,
					/* url : '${uomUrl}', */
					dataType : "JSON",
					success : function(response) {
						$.each(response, function(index, value) {
							resUom.push(value);
						});
					}
				});
				
				$.ajax({
					type : "GET",
					url : "${codeUrl}/"+ SelectedItemMasterRowId,
					//url : '${codeUrl}',
					dataType : "JSON",
					success : function(response) {
						$.each(response, function(index, value) {
							resCode.push(value);
						});
					}
				});				
				$('label[for=uomConversion]').hide();
				$('div[data-container-for="uomConversion"]').hide();

				$('label[for=dummy]').hide();
				$('div[data-container-for="dummy"]').hide();
				
				$('label[for=status]').hide();
				$('div[data-container-for="status"]').hide();

				var uomDetailsGrid = $('#uomDetailsGrid_' + SelectedItemMasterRowId).data("kendoGrid");
				var data = uomDetailsGrid.dataSource.data();
				var totalNumber = data.length;
				var YesExists = 0;

				for (var i = 0; i < totalNumber; i++)
				{
					if (data[i].baseUom == "") 
					{
						YesExists = 1;
						code = data[i].code;
					} 
					else if (data[i].baseUom != "")
					{
						YesExists = 2;
						code = data[i].code;
					}
				}			
				
				var dropdownlist = $("#BaseUomId").data("kendoDropDownList");
				if (YesExists == 1) 
				{
					$('label[for=baseUom]').hide();
					$('div[data-container-for="baseUom"]').hide();
					dropdownlist.search('Yes');
					dropdownlist.readonly();
					var primaryOwner = $('#uomDetailsGrid_' + SelectedItemMasterRowId).data().kendoGrid.dataSource.data()[0];
					primaryOwner.set('baseUom', 'Yes');
				} 
				else if (YesExists > 1)
				{
					$('label[for=baseUom]').hide();
					$('div[data-container-for="baseUom"]').hide();
					dropdownlist.search('Yes');
					dropdownlist.readonly();
					
					var primaryOwner = $('#uomDetailsGrid_' + SelectedItemMasterRowId).data().kendoGrid.dataSource.data()[0];
					primaryOwner.set('baseUom', 'No');
					baseVal = primaryOwner.get('baseUom');
					if (baseVal == 'No')
					{
						$('label[for=uomConversion]').hide();
						$('label[for=dummy]').hide();
						$('div[data-container-for="uomConversion"]').show();
						$('input[name="uomConversion"]').prop('required', true);
						//Here we call to editor function named UomEditor() and CodeEditor() for code conversion
					}
				}				
				$(".k-window-title").text("Add UOM Details");
				$('label[for=uomId]').parent().remove();
				$('div[data-container-for="uomId"]').remove();

				$('label[for=imName]').remove();
				$('div[data-container-for="imName"]').remove();

				$('label[for=imId]').remove();
				$('div[data-container-for="imId"]').remove();				
			} 
			else 
			{
				$(".k-grid-cancel").click(function () 
				{
				     var grid = $('#uomDetailsGrid_' + SelectedItemMasterRowId).data("kendoGrid");
					 grid.dataSource.read();
					 grid.cancelRow();
			    });
				securityCheckForActionsForStatus("./procurement/uom/updateButton");	
				
				var gview = $('#uomDetailsGrid_' + SelectedItemMasterRowId).data("kendoGrid");
				var selectedItem = gview.dataItem(gview.select());
				var uomStatustatus = selectedItem.status;
				
				if(uomStatustatus === "Active")
				{
					$("#alertsBox").html("Alert");
					$("#alertsBox").html("Active UOM Cannot Be Edited");
					$("#alertsBox").dialog({
						modal: true,
						buttons: {
							"Close": function() {
								$( this ).dialog( "close" );
							}
						}
					});
					
					var grid = $('#uomDetailsGrid_' + SelectedItemMasterRowId).data("kendoGrid");
					grid.cancelRow();
					grid.close();
					return false;
				}
				else
				{
				$('label[for=status]').hide();
				$('div[data-container-for="status"]').hide();
				
				getBaseUom = e.model.baseUom;
				getUomVal = e.model.uom;
				getCode = e.model.code;
				
				$.ajax({
					type : "GET",
					url : '${uomUrl}',
					dataType : "JSON",
					success : function(response) {
						$.each(response, function(index, value) {
							resUom.push(value);
						});
					}
				}); 
				
				$.ajax({
					type : "GET",
					url : '${codeUrl}',
					dataType : "JSON",
					success : function(response) {
						$.each(response, function(index, value) {
							resCode.push(value);
						});
					}
				});
				
				$(".k-window-title").text("Edit UOM Deatils");
				$('label[for=uomId]').parent().hide();
				$('div[data-container-for="uomId"]').hide();
				if(getBaseUom == 'Yes')
				{
					$('label[for=imId]').parent().hide();
					$('div[data-container-for="imId"]').hide();
					
					$('label[for=dummy]').parent().hide();
					$('div[data-container-for="dummy"]').hide();

					$('label[for=baseUom]').hide();
					$('div[data-container-for="baseUom"]').hide();
					
					$('label[for=uomConversion]').hide();
					$('div[data-container-for="uomConversion"]').hide();
					
					$('label[for=uom]').show();
					$('div[data-container-for="uom"]').show();
					
					$('label[for=code]').show();
					$('div[data-container-for="code"]').show();					
				}
				else if(getBaseUom == 'No')
				{
					$('label[for=imId]').parent().hide();
					$('div[data-container-for="imId"]').hide();
					
					$('label[for=dummy]').parent().hide();
					$('div[data-container-for="dummy"]').hide();
					
					$('label[for=baseUom]').hide();
					$('div[data-container-for="baseUom"]').hide();
				}
			  }
			}
		}

		/******  EDITOR FUNCTION FOR UOM  *********/
		var value = "";
		var baseVal = "";
		function UomEditor(container, options) 
		{
			$('<input type="text" required="true" class="k-input k-textbox" pattern="[a-zA-Z]+" id ="uom" onblur="uomValue(this.value)" name='
							+ options.field
							+ ' data-bind="value:'
							+ options.field + '"/>').appendTo(container);
		}
		function uomValue(text)
		{
			if (baseVal == 'Yes') 
			{
				$('label[for="uomConversion"]').hide().text("1   " + text).append("&nbsp;&nbsp;&nbsp;&nbsp;=");
			}
			else if (baseVal == 'No') 
			{
				var firstItem = $('#uomDetailsGrid_' + SelectedItemMasterRowId).data().kendoGrid.dataSource.data()[1];
		    	var vehReg=firstItem.get('code');		 	

				$('label[for="uomConversion"]').show().text("1   " + text).append("&nbsp;&nbsp;&nbsp;* &nbsp;=");
				$('div[data-container-for="uomConversion"]').show().append($('label[for="dummy"]').show().text(vehReg));
			}
		}
		/****  END OF UOM EDITOR FUNCTION  *****/
		
		/******  EDITOR FUNCTION FOR CODE  *********/
		function CodeEditor(container, options) 
		{
			$('<input type="text" required="true"  class="k-input k-textbox" pattern="[a-zA-Z]+" id="code" name=' + options.field + ' data-bind="value:' + options.field + '"/>')
					.appendTo(container);
		}
		function codeValue(text) 
		{
			if (baseVal == 'Yes') 
			{
				$('div[data-container-for="uomConversion"]').hide().append($('label[for="dummy"]').show().text(text));
			}
			else if (baseVal == 'No') 
			{
				
			}
		}
		/****  END OF CODE EDITOR FUNCTION  *****/

		/********FOR ITEM MASTER GRID********************/
		function onRequestEnd(e) {
			if (typeof e.response != 'undefined') {
				if (e.response.status == "FAIL") {
					errorInfo = "";
					errorInfo = e.response.result.invalid;
					for (i = 0; i < e.response.result.length; i++) {
						errorInfo += (i + 1) + ". "
								+ e.response.result[i].defaultMessage + "\n";
					}
					if (e.type == "create") {
						alert("Error: Creating the ItemMaster Details\n\n"
								+ errorInfo);
					}
					if (e.type == "update") {
						alert("Error: Updating the ItemMaster Details\n\n"
								+ errorInfo);
					}
					var grid = $("#grid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
					return false;
				}
			}

			if (e.type == "update" && !e.response.Errors)
			{
				$("#alertsBox").html("Alert");
				$("#alertsBox").html("ItemMaster details Updated successfully");
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
				$("#alertsBox").html("ItemMaster details Added successfully");
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
		/********* END OF ITEM MASTER GRID **********/

		function onItemMasterRequestStart(e)
		{
			
			/* $('.k-grid-update').hide();
	        $('.k-edit-buttons')
	                .append(
	                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
	        $('.k-grid-cancel').hide(); */
		    var grid= $("#grid").data("kendoGrid");
		    grid.cancelRow(); 
		}
		
		
		/**********   START OF UOM *************/
		function onUomRequestEnd(e) 
		{
			if (typeof e.response != 'undefined') 
			{
				if (e.response.status == "ERROR") 
				{
					errorInfo = "";
					errorInfo = e.response.result.deleteBaseUomError;
					alert(errorInfo);
					
					var grid = $('#uomDetailsGrid_' + SelectedItemMasterRowId).data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
					return false;
				}
			}
			if (e.type == "update" && !e.response.Errors)
			{
				$("#alertsBox").html("Alert");
				$("#alertsBox").html("UOM details Updated successfully");
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
				$("#alertsBox").html("UOM details Added successfully");
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
		/********* END OF UOM GRID **********/

		function onUomRequestStart(e)
		{
			$('.k-grid-update').hide();
	        $('.k-edit-buttons')
	                .append(
	                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
	        $('.k-grid-cancel').hide();
			var grid= $('#uomDetailsGrid_' + SelectedItemMasterRowId).data("kendoGrid");
		    grid.cancelRow();
		}
		
		function editItemMaster(e)
		{
			$('label[for=imId]').parent().hide();
			$('div[data-container-for="imId"]').hide();

			$('label[for=createdBy]').parent().hide();
			$('div[data-container-for="createdBy"]').hide();

			$('label[for=lastUpdatedBy]').parent().hide();
			$('div[data-container-for="lastUpdatedBy"]').hide();

			$('label[for=imPurchaseUom]').hide();
			$('div[data-container-for="imPurchaseUom"]').hide();

			$('label[for=imUomIssue]').hide();
			$('div[data-container-for="imUomIssue"]').hide();
		}

		function UomClass(container, options) {
			var booleanData = [ {
				text : 'Select',
				value : ""
			}, {
				text : 'Quantity',
				value : "Quantity",
				id : "1"
			}, {
				text : 'Weight',
				value : "Weight",
				id : "2"
			}, {
				text : 'Time',
				value : "Time",
				id : "3"
			}, {
				text : 'Volume',
				value : "Volume",
				id : "4"
			} ];

			$('<input name="UOM Class" required="true"/>').attr('data-bind','value:uomClass').appendTo(container).kendoDropDownList({
				dataSource : booleanData,
				dataTextField : 'text',
				dataValueField : 'value',
			});
		}

		function BaseUom(container, options) {
			var booleanData = [ {
				text : '--Select--',
				value : ""
			}, {
				text : 'Yes',
				value : "Yes"
			}, {
				text : 'No',
				value : "No"
			} ];

			$('<select  id="BaseUomId" />').attr('data-bind', 'value:baseUom')
					.appendTo(container).kendoDropDownList({
						dataSource : booleanData,
						dataTextField : 'text',
						dataValueField : 'value',
					});
			//$('<span class="k-invalid-msg" data-for="Select"></span>').appendTo(container); 
		}

		/****** DROP DOWN FOR ITEM PURCHASE UOM ******/
		function ItemPurchaseUOM(container, options) 
		{
		$('<select data-text-field="imPurchaseUom" data-value-field="uomId" data-bind="value:' + options.field + '" />').appendTo(container).kendoDropDownList
		({
		 	 optionLabel : "-- Select -- ",
			 autoBind : false,
			 dataSource : 
			 {
				transport : {
				read : "${itemPurchaseUomUrl}/"+ SelectedItemMasterRowId
				}
			 }
		});
		}
		/***  END FOR ITEM UOM PURCHASE **/

		/****** DROP DOWN FOR ITEM UOM ISSUE ******/
		function ItemUomIssue(container, options) 
		{
			$('<select data-text-field="imUomIssue" data-value-field="uomId" data-bind="value:' + options.field + '" />').appendTo(container).kendoDropDownList
			({
				optionLabel : "-- Select --",
				autoBind : false,
				dataSource : {
				transport : {
					read : "${itemUomIssueUrl}/"+ SelectedItemMasterRowId
				}
				}
			});
		}
		/***  END FOR UOM ISSUE ***/

		var res = [];
		var uom2 = "";
		var code2 = "";
		var flag = "";
		(function($, kendo) {
			$
					.extend(
							true,
							kendo.ui.validator,
							{
								rules : { // custom rules          	
									itemGroup : function(input, params) {
										//check for the name attribute 
										if (input.filter("[name='imGroup']").length
												&& input.val()) {
											return /^[a-zA-Z]+[ ._a-zA-Z0-9]*[a-zA-Z0-9]$/
													.test(input.val());
										}
										return true;
									},
									itemType : function(input, params) {
										//check for the name attribute 
										if (input.filter("[name='imType']").length
												&& input.val()) {
											return /^[a-zA-Z]+[ ._a-zA-Z0-9]*[a-zA-Z0-9]$/
													.test(input.val());
										}
										return true;
									},
									itemPurchaseUOM : function(input, params) {
										//check for the name attribute 
										if (input
												.filter("[name='imPurchaseUom']").length
												&& input.val()) {
											return /^[a-zA-Z]+[ ._a-zA-Z0-9]*[a-zA-Z0-9]$/
													.test(input.val());
										}
										return true;
									},
									itemUomIssue : function(input, params) {
										//check for the name attribute 
										if (input
												.filter("[name='imUom_Issue']").length
												&& input.val()) {
											return /^[a-zA-Z]+[ ._a-zA-Z0-9]*[a-zA-Z0-9]$/
													.test(input.val());
										}
										return true;
									},
									itemName : function(input, params) {
										//check for the name attribute 
										if (input.filter("[name='imName']").length
												&& input.val()) {
											return /^[a-zA-Z]+[ ._a-zA-Z0-9]*[a-zA-Z0-9]$/
													.test(input.val());
										}
										return true;
									},
									itemDescription : function(input, params) {
										//check for the name attribute 
										if (input
												.filter("[name='imDescription']").length
												&& input.val()) {
											return /^[a-zA-Z]+[ ._a-zA-Z0-9]*[a-zA-Z0-9]$/
													.test(input.val());
										}
										return true;
									},
									UomConversion : function(input, params) {
										//check for the name attribute 
										if (input
												.filter("[name='uomConversion']").length
												&& input.val()) {
											return /^[.0-9]*[.0-9]$/.test(input
													.val());
										}
										return true;
									},
									UomValidation : function(input,params)
									{
										//check for the name attribute 
										if (input.filter("[name='uom']").length&& input.val()) 
										{
											uom2 = input.val();	
											if(uom2 == getUomVal)
											{
												return true;	
											}
											else
											{
											 $.each(resUom,function(ind, val) 
											 {
												 if ((uom2 == val)&& (uom2.length == val.length)) 
												 {
													flag = uom2;
													return false;
												 }
											 });
											}
										}
										return true;
									},
									UomUniqueness : function(input) 
									{
										if (input.filter("[name='uom']").length&& input.val() && flag != "") 
										{
											flag = "";
											return false;
										}
										return true;
									},
									CodeValidation : function(input,params) 
									{
										//check for the name attribute 
										if (input.filter("[name='code']").length && input.val()) 
										{
											code2 = input.val();
											if(code2 == getCode)
											{
												return true;	
											}
											else
											{	
												$.each(resCode,function(ind, val) 
												{			
													if ((code2 == val) && (code2.length == val.length)) 
													{
														flag = code2;
														return false;
													}	
												});
											}
										}
										return true;
									},
									CodeUniqueness : function(input) 
									{
										if (input.filter("[name='code']").length && input.val() && flag != "") 
										{
											flag = "";
											return false;
										}
										return true;
									},
									itemGroupValidator : function(input,params) 
    								{
    									//check for the name attribute 
    									 if (input.attr("name") == "imGroup") {
  						                   return $.trim(input.val()) !== "";
  						                }
  						                return true;
    								},
    								itemTypeValidator : function(input,params) 
    								{
    									//check for the name attribute 
    									 if (input.attr("name") == "imType") {
  						                   return $.trim(input.val()) !== "";
  						                }
  						                return true;
    								},
    								itemNameValidator : function(input,params) 
    								{
    									//check for the name attribute 
    									 if (input.attr("name") == "imName") {
  						                   return $.trim(input.val()) !== "";
  						                }
  						                return true;
    								},
    								itemDescriptionValidator : function(input,params) 
    								{
    									//check for the name attribute 
    									 if (input.attr("name") == "imDescription") {
  						                   return $.trim(input.val()) !== "";
  						                }
  						                return true;
    								},
    								itemOptimalStockValidator : function(input,params) 
    								{
    									//check for the name attribute 
    									 if (input.attr("name") == "imOptimal_Stock") {
  						                   return $.trim(input.val()) !== "";
  						                }
  						                return true;
    								},
    								UOMValidator : function(input,params) 
    								{
    									//check for the name attribute 
    									 if (input.attr("name") == "uom") {
  						                   return $.trim(input.val()) !== "";
  						                }
  						                return true;
    								},
    								CODEValidator : function(input,params) 
    								{
    									//check for the name attribute 
    									 if (input.attr("name") == "code") {
  						                   return $.trim(input.val()) !== "";
  						                }
  						                return true;
    								},
    								itemNameUniqueness : function(input,params) 
									{
								        //check for the name attribute 
								        if (input.filter("[name='imName']").length && input.val()) 
								        {
								          enterdService = input.val().toUpperCase(); 
								          for(var i = 0; i<res1.length; i++) 
								          {
								            if ((enterdService == res1[i].toUpperCase()) && (enterdService.length == res1[i].length) ) 
								            {								            
								              return false;								          
								            }
								          }
								         }
								         return true;
								    },
								    uomRegExValidation : function(input, params) {
											//check for the name attribute 
											if (input.filter("[name='uom']").length
													&& input.val()) {
												return /^[a-zA-Z]+[ ._a-zA-Z0-9]*[a-zA-Z0-9]$/
														.test(input.val());
											}
											return true;
								    },
								    codeRegExValidation : function(input, params) {
										//check for the name attribute 
										if (input.filter("[name='code']").length
												&& input.val()) {
											return /^[a-zA-Z]+[ ._a-zA-Z0-9]*[a-zA-Z0-9]$/
													.test(input.val());
										}
										return true;
							        },
								},								
								messages : {
									itemGroup : "Item Group can not allow special symbols & numbers",
									itemType : "Item Type can not allow special symbols & numbers",
									itemPurchaseUOM : "Item Purchase UOM can not allow special symbols & except(.)",
									itemUomIssue : "Item UOM Issue can not allow special symbols & numbers",
									itemName : "Item Name can not allow special symbols & numbers",
									itemDescription : "Item Description can not allow special symbols & numbers",
									UomConversion : "Enter Positive Numbers",
									UomUniqueness : "Uom Already Exists",
									UomValidation : "Invalid Access Point Code",
									CodeValidation : "Invalid Code",
									CodeUniqueness : "Code Already Exists",
									itemGroupValidator : "Item Group Required",
									itemTypeValidator : "Item type Required",
									itemNameValidator : "Item Name Required",
									itemDescriptionValidator : "Item Description Required",
									itemOptimalStockValidator : "Item Optimal Stock Required",
									UOMValidator : "UOM is Required",
									CODEValidator : "Code is Required",
									itemNameUniqueness : "Item Name Exists",
									uomRegExValidation : "UOM can not allow special symbols & numbers",
									codeRegExValidation : "CODE can not allow special symbols & numbers",
								}
							});
		})(jQuery, kendo);

		/*****************************************************************/
	</script>
	
	<style>
	.wrappers {
	display: inline;
	float: left;
	width: 250px;
	padding-top: 10px;
}
	
	</style>

    <!-- <div id="alertsBox" title="Alert"></div>
    <div id="container" style="min-width: 725px; height: 470px; margin: 0 auto"></div> 
    <div id="shashi" style="color: black"></div>
   
    <script>
		var janStock = 0;
		var febStock = 0;
		var marStock = 0;
		var aprStock = 0;
		var mayStock = 0;
		var junStock = 0;
		var julStock =0;
		var augStock =0;
		var septStock = 0;
		var octStock = 0;
		var novStock = 0;
		var decStock = 0;
		
		var janDebit = 0;
		var febDebit = 0;
		var marDebit = 0;
		var aprDebit = 0;
		var mayDebit = 0;
		var junDebit = 0;
		var julDebit = 0;
		var augDebit = 0;
		var sepDebit = 0;
		var octDebit = 0;
		var novDebit = 0;
		var decDebit = 0;
		
        $(document).ready(function() 
    	{	
    	    $('#container').hide();
    	    $(function () {
    	       
    	    });
    	});

        var Feb = 0;  
        var resp1 = "";
        var resp2 = "";
        var responseVal = "";
        $("#grid").on("click", ".k-grid-showGraph", function(e)
        { 
        	//Read Optimal Stock Response
		    $.ajax
		    ({
	            type : "POST",
	            dataType : "json",
	            async : false,
		        url :"./itemMaster/readCreditedDataForGraph",
		        success : function(response)
		        {		
		           for(var s = 0; s<response.length; s++)
		 		   { 
		              responseVal = response[s];
		              if(responseVal.monthValue == 'Jan'){
		            	  janStock = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Feb'){
		            	  febStock = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Mar'){
		            	  marStock = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Apr'){
		            	  aprStock = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'May'){
		            	  mayStock = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Jun'){
		            	  junStock = responseVal.id;
		              }
		              if(responseVal.monthValue == 'Jul'){
		            	  julStock = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Aug'){
		            	  augStock = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Sept'){
		            	  septStock = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Oct'){
		            	  octStock = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Nov'){
		            	  novStock = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Dec'){
		            	  decStock = responseVal.graphIndex;
		              }		              
		 		   }
		         }
		     });
		     $.ajax
		    ({
	            type : "POST",
	            dataType : "json",
	            async : false,
		        url :"./itemMaster/readDebittedDataForGraph",
		        success : function(response)
		        {	
		           for(var s = 0; s<response.length; s++)
		 		   { 
		              var responseVal = response[s];
		              if(responseVal.monthValue == 'Jan'){
		            	  janDebit = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Feb'){
		            	  febDebit = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Mar'){
		            	  marDebit = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Apr'){
		            	  aprDebit = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'May'){
		            	  mayDebit = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Jun'){
		            	  junDebit = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Jul'){
		            	  julDebit = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Aug'){
		            	  augDebit = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Sept'){
		            	  sepDebit = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Oct'){
		            	  octDebit = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Nov'){
		            	  novDebit = responseVal.graphIndex;
		              }
		              if(responseVal.monthValue == 'Dec'){
		            	  decDebit = responseVal.graphIndex;
		              }		              
		 		   }
		         }
		     }); 
		    $('#container').highcharts
	           ({
	    	        chart: {
	    	           type: 'column'
	    	        },
	    	        title: {
	    	                text: 'Average Item Consumption'
	    	        },
	    	        subtitle: {
	    	           text: ''
	    	        },
	    	        xAxis: {
	    	           categories: ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sept','Oct','Nov','Dec']
	    	        },
	    	        yAxis: {
	    	           //min: -1,
	    	           max: 250,
	    	           tickInterval: 10,
	    	           title: {
	    	              text: 'Item Optimal Stock'
	    	           }
	    	        },
	    	        credits : {
						enabled : false
					},
	    	        tooltip: {
	    	             headerFormat: '<span style="font-size:10px">{point.key}</span><table>',pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
	    	             '<td style="padding:0"><b>{point.y:.1f}</b></td></tr>',footerFormat: '</table>',
	    	             shared: true,
	    	             useHTML: true
	    	        },
	    	        plotOptions: {
	    	             column: {
	    	               pointPadding: 0.2,
	    	               borderWidth: 0
	    	             }
	    	         },
	    	         series: [{
	    	              name: 'Procured',
	    	              data: [janStock, febStock, marStock,aprStock, mayStock, junStock,julStock, augStock, septStock,octStock,novStock,decStock]
	    	         },{
	    	              name: 'Consumed',
	    	              data: [janDebit, febDebit, marDebit,aprDebit, mayDebit, junDebit,julDebit, augDebit, sepDebit,octDebit,novDebit,decDebit]
	    	         }]
	    	    }); 
        	$('#container').show();
        	var wnd2 = $("#container").kendoWindow(
		    {
			    visible : false,
				resizable : false,
				modal : true,
				actions : [ "Custom", "Minimize","Maximize", "Close" ],
				title : "Item Consumption"
			}).data("kendoWindow");
			wnd2.center().open();
        });
      </script>          
        
	<script>
	function itemGroupEditor(container,options)
	{
		var booleanData = [ {
			text : 'Select',
			value : ""
		    },{
				text : 'Electronics',
				value : "Electronics"
			},{
				text : 'Plumbing',
				value : "Plumbing"
			},];
			$('<select name="Item Group" required="true" class="itemGroupClass"/>').attr('data-bind','value:imGroup').appendTo(container).kendoDropDownList({
				dataSource : booleanData,
				dataTextField : 'text',
				dataValueField : 'value',
			}); 
			$('<span class="k-invalid-msg" data-for="Item Group"></span>').appendTo(container);	
	}
		var SelectedItemMasterRowId = "";
		function onChangeItemMaster(arg) {
			var gview = $("#grid").data("kendoGrid");
			var selectedItem = gview.dataItem(gview.select());
			SelectedItemMasterRowId = selectedItem.imId;
			this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
		}
		
		function itemDescriptionEditor(container, options) 
		{
	        $('<textarea name="Item Description To" data-text-field="imDescription" data-value-field="imDescription" data-bind="value:' + options.field + '" style="width: 148px; height: 46px;" required="true"/>')
	             .appendTo(container);
	    	$('<span class="k-invalid-msg" data-for="Item Description"></span>').appendTo(container); 
		} 
		
		/** DataBound For UOM **/
		var statusVal = "";
		var newStatus = "";
		function UomEventDatabound(e)
		{
			var grid = $('#uomDetailsGrid_' + SelectedItemMasterRowId).data("kendoGrid");
		    var gridData = grid._data;		    
		    var i = 0;
		    this.tbody.find("tr td:last-child").each(function (e) 
		    {
		    	statusVal = gridData[i].status;
			    if(statusVal === "Created")
			    {
			    	newStatus = "Active";
			    	$('<button id="activeButton" class="k-button k-button-icontext" onclick="uomActivateClick()">Activate</button>').appendTo(this);
			    }			    
			    i++;
		    });
		}
		/** End For Databound **/
		
		function uomActivateClick()
		{
			var result=securityCheckForActionsForStatus("./procurement/uom/activateButton");   
		 	if(result=="success")
		 	{
		 		var gridUom = $('#uomDetailsGrid_' + SelectedItemMasterRowId).data("kendoGrid");
				var selectedStatus = gridUom.dataItem(gridUom.select());
				var uomId= selectedStatus.uomId;
				$.ajax
				({
					type : "POST",
					dataType : "text",
					url : "./procurementUom/uomDetails/updateUomStatus/" + uomId +"/"+newStatus,				
					success : function(response) 
					{
						$("#alertsBox").html("");
						$("#alertsBox").html(response);
						$("#alertsBox").dialog
						({
							modal : true,
							buttons :{
							  "Close" : function(){
									$(this).dialog("close");
								}
							}
						});
						$('#uomDetailsGrid_' + SelectedItemMasterRowId).data('kendoGrid').dataSource.read();
					}
				});
		 	}
		}
		
		/**** FOR CLEAR FILTER *****/
		$("#grid").on("click",".k-grid-Clear_Filter",function() 
		{
			$("form.k-filter-menu button[type='reset']").slice().trigger("click");
			var grid = $("#grid").data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
		});
		/**** END FILTER *****/

		/****  FOR REQUISITION TYPES EDITOR ****/
		/****** For Requisition Types *****/
		function RequisitionTypeNames(container, options)
		{
			var booleanData = [ {
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

			$('<input name="Requisition Type" required="true"/>').attr('data-bind', 'value:imType').appendTo(container).kendoDropDownList
			({
				dataSource : booleanData,
				dataTextField : 'text',
				dataValueField : 'value',
			});
			$('<span class="k-invalid-msg" data-for="Requisition Type"></span>').appendTo(container);
		}
		
		
		/****  END FOR REQUISITION TYPES EDITOR  ****/
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
		
		var getItemName = "";
		var resItemName = [];
		var res1 = new Array();
		function itemMasterEvent(e) 
		{
			$('label[for=imCreatedDate]').parent().hide();
			$('div[data-container-for="imCreatedDate"]').hide();
			$("input[name='imName']").attr('maxlength', '30');
			$("input[name='imOptimal_Stock']").attr('maxlength', '9');
			$("input[name='uom']").attr('maxlength', '20');
			$("input[name='code']").attr('maxlength', '20');
			
			$('label[for=reorderLevel]').parent().hide();
			$('div[data-container-for="reorderLevel"]').hide();
			
			if (e.model.isNew()) 
			{
				securityCheckForActions("./procurement/itemMaster/createButton");
				$.ajax
				({
				  type : "GET",
				  dataType:"text",
				  url : '${readItemNames}',
				  dataType : "JSON",
				  success : function(response) 
				  {
					   for(var i = 0; i<response.length; i++) {
					   res1[i] = response[i];			   
					 }
				  }
			    });
				
				$('.k-edit-field .k-input').first().focus();
				$(".k-window-title").text("Add ItemMaster Details");
				$(".k-grid-update").text("Save");

				$('label[for=imId]').parent().hide();
				$('div[data-container-for="imId"]').hide();

				$('label[for=createdBy]').parent().hide();
				$('div[data-container-for="createdBy"]').hide();

				$('label[for=lastUpdatedBy]').parent().hide();
				$('div[data-container-for="lastUpdatedBy"]').hide();

				$('label[for=imPurchaseUom]').remove();
				$('div[data-container-for="imPurchaseUom"]').remove();

				$('label[for=imUomIssue]').remove();
				$('div[data-container-for="imUomIssue"]').remove();	
			}
			else
		    {
				securityCheckForActions("./procurement/itemMaster/updateButton"); 
				getItemName = e.model.imName;
				
			    $(".k-window-title").text("Update ItemMaster Details");
			    $('label[for=imId]').parent().hide();
				$('div[data-container-for="imId"]').hide();

				$('label[for=createdBy]').parent().hide();
				$('div[data-container-for="createdBy"]').hide();

				$('label[for=lastUpdatedBy]').parent().hide();
				$('div[data-container-for="lastUpdatedBy"]').hide();

				$('label[for=imPurchaseUom]').hide();
				$('div[data-container-for="imPurchaseUom"]').hide();

				$('label[for=imUomIssue]').hide();
				$('div[data-container-for="imUomIssue"]').hide();
				
				$('label[for=uom]').remove();
				$('div[data-container-for="uom"]').remove();
				
				$('label[for=code]').remove();
				$('div[data-container-for="code"]').remove();
		    }
		}

		function deleteItemMaster(e)
		{
			securityCheckForActionsForStatus("./procurement/itemMaster/deleteButton");			
		}
		
		/****** FOR UOM Add New ********/
		var code = "";
		var uom = "";
		var resUom = [];
		var resCode = [];
		
		var getBaseUom = "";
		var getUomVal = "";
		var getCode = "";
		
		function deleteUOMEvent(e)
		{
			securityCheckForActionsForStatus("./procurement/uom/deleteButton");		
		}
		
		function uomEvent(e)
		{
			$("input[name='uom']").attr('maxlength', '10');
			$("input[name='code']").attr('maxlength', '10');
			$("input[name='uomConversion']").attr('maxlength', '9');		
			
			if (e.model.isNew()) 
			{ 
				securityCheckForActionsForStatus("./procurement/uom/createButton");		
				
				$.ajax({
					type : "GET",
					url : "${uomUrl}/"+ SelectedItemMasterRowId,
					/* url : '${uomUrl}', */
					dataType : "JSON",
					success : function(response) {
						$.each(response, function(index, value) {
							resUom.push(value);
						});
					}
				});
				
				$.ajax({
					type : "GET",
					url : "${codeUrl}/"+ SelectedItemMasterRowId,
					//url : '${codeUrl}',
					dataType : "JSON",
					success : function(response) {
						$.each(response, function(index, value) {
							resCode.push(value);
						});
					}
				});				
				$('label[for=uomConversion]').hide();
				$('div[data-container-for="uomConversion"]').hide();

				$('label[for=dummy]').hide();
				$('div[data-container-for="dummy"]').hide();
				
				$('label[for=status]').hide();
				$('div[data-container-for="status"]').hide();

				var uomDetailsGrid = $('#uomDetailsGrid_' + SelectedItemMasterRowId).data("kendoGrid");
				var data = uomDetailsGrid.dataSource.data();
				var totalNumber = data.length;
				var YesExists = 0;

				for (var i = 0; i < totalNumber; i++)
				{
					if (data[i].baseUom == "") 
					{
						YesExists = 1;
						code = data[i].code;
					} 
					else if (data[i].baseUom != "")
					{
						YesExists = 2;
						code = data[i].code;
					}
				}			
				
				var dropdownlist = $("#BaseUomId").data("kendoDropDownList");
				if (YesExists == 1) 
				{
					$('label[for=baseUom]').hide();
					$('div[data-container-for="baseUom"]').hide();
					dropdownlist.search('Yes');
					dropdownlist.readonly();
					var primaryOwner = $('#uomDetailsGrid_' + SelectedItemMasterRowId).data().kendoGrid.dataSource.data()[0];
					primaryOwner.set('baseUom', 'Yes');
				} 
				else if (YesExists > 1)
				{
					$('label[for=baseUom]').hide();
					$('div[data-container-for="baseUom"]').hide();
					dropdownlist.search('Yes');
					dropdownlist.readonly();
					
					var primaryOwner = $('#uomDetailsGrid_' + SelectedItemMasterRowId).data().kendoGrid.dataSource.data()[0];
					primaryOwner.set('baseUom', 'No');
					baseVal = primaryOwner.get('baseUom');
					if (baseVal == 'No')
					{
						$('label[for=uomConversion]').hide();
						$('label[for=dummy]').hide();
						$('div[data-container-for="uomConversion"]').show();
						$('input[name="uomConversion"]').prop('required', true);
						//Here we call to editor function named UomEditor() and CodeEditor() for code conversion
					}
				}				
				$(".k-window-title").text("Add UOM Details");
				$('label[for=uomId]').parent().remove();
				$('div[data-container-for="uomId"]').remove();

				$('label[for=imName]').remove();
				$('div[data-container-for="imName"]').remove();

				$('label[for=imId]').remove();
				$('div[data-container-for="imId"]').remove();				
			} 
			else 
			{
				$(".k-grid-cancel").click(function () 
				{
				     var grid = $('#uomDetailsGrid_' + SelectedItemMasterRowId).data("kendoGrid");
					 grid.dataSource.read();
					 grid.cancelRow();
			    });
				securityCheckForActionsForStatus("./procurement/uom/updateButton");	
				
				var gview = $('#uomDetailsGrid_' + SelectedItemMasterRowId).data("kendoGrid");
				var selectedItem = gview.dataItem(gview.select());
				var uomStatustatus = selectedItem.status;
				
				if(uomStatustatus === "Active")
				{
					$("#alertsBox").html("Alert");
					$("#alertsBox").html("Active UOM Cannot Be Edited");
					$("#alertsBox").dialog({
						modal: true,
						buttons: {
							"Close": function() {
								$( this ).dialog( "close" );
							}
						}
					});
					
					var grid = $('#uomDetailsGrid_' + SelectedItemMasterRowId).data("kendoGrid");
					grid.cancelRow();
					grid.close();
					return false;
				}
				else
				{
				$('label[for=status]').hide();
				$('div[data-container-for="status"]').hide();
				
				getBaseUom = e.model.baseUom;
				getUomVal = e.model.uom;
				getCode = e.model.code;
				
				$.ajax({
					type : "GET",
					url : '${uomUrl}',
					dataType : "JSON",
					success : function(response) {
						$.each(response, function(index, value) {
							resUom.push(value);
						});
					}
				}); 
				
				$.ajax({
					type : "GET",
					url : '${codeUrl}',
					dataType : "JSON",
					success : function(response) {
						$.each(response, function(index, value) {
							resCode.push(value);
						});
					}
				});
				
				$(".k-window-title").text("Edit UOM Deatils");
				$('label[for=uomId]').parent().hide();
				$('div[data-container-for="uomId"]').hide();
				if(getBaseUom == 'Yes')
				{
					$('label[for=imId]').parent().hide();
					$('div[data-container-for="imId"]').hide();
					
					$('label[for=dummy]').parent().hide();
					$('div[data-container-for="dummy"]').hide();

					$('label[for=baseUom]').hide();
					$('div[data-container-for="baseUom"]').hide();
					
					$('label[for=uomConversion]').hide();
					$('div[data-container-for="uomConversion"]').hide();
					
					$('label[for=uom]').show();
					$('div[data-container-for="uom"]').show();
					
					$('label[for=code]').show();
					$('div[data-container-for="code"]').show();					
				}
				else if(getBaseUom == 'No')
				{
					$('label[for=imId]').parent().hide();
					$('div[data-container-for="imId"]').hide();
					
					$('label[for=dummy]').parent().hide();
					$('div[data-container-for="dummy"]').hide();
					
					$('label[for=baseUom]').hide();
					$('div[data-container-for="baseUom"]').hide();
				}
			  }
			}
		}

		/******  EDITOR FUNCTION FOR UOM  *********/
		var value = "";
		var baseVal = "";
		function UomEditor(container, options) 
		{
			$('<input type="text" required="true" class="k-input k-textbox" pattern="[a-zA-Z]+" id ="uom" onblur="uomValue(this.value)" name='
							+ options.field
							+ ' data-bind="value:'
							+ options.field + '"/>').appendTo(container);
		}
		function uomValue(text)
		{
			if (baseVal == 'Yes') 
			{
				$('label[for="uomConversion"]').hide().text("1   " + text).append("&nbsp;&nbsp;&nbsp;&nbsp;=");
			}
			else if (baseVal == 'No') 
			{
				var firstItem = $('#uomDetailsGrid_' + SelectedItemMasterRowId).data().kendoGrid.dataSource.data()[1];
		    	var vehReg=firstItem.get('code');		 	

				$('label[for="uomConversion"]').show().text("1   " + text).append("&nbsp;&nbsp;&nbsp;* &nbsp;=");
				$('div[data-container-for="uomConversion"]').show().append($('label[for="dummy"]').show().text(vehReg));
			}
		}
		/****  END OF UOM EDITOR FUNCTION  *****/
		
		/******  EDITOR FUNCTION FOR CODE  *********/
		function CodeEditor(container, options) 
		{
			$('<input type="text" required="true"  class="k-input k-textbox" pattern="[a-zA-Z]+" id="code" name=' + options.field + ' data-bind="value:' + options.field + '"/>')
					.appendTo(container);
		}
		function codeValue(text) 
		{
			if (baseVal == 'Yes') 
			{
				$('div[data-container-for="uomConversion"]').hide().append($('label[for="dummy"]').show().text(text));
			}
			else if (baseVal == 'No') 
			{
				
			}
		}
		/****  END OF CODE EDITOR FUNCTION  *****/

		/********FOR ITEM MASTER GRID********************/
		function onRequestEnd(e) {
			if (typeof e.response != 'undefined') {
				if (e.response.status == "FAIL") {
					errorInfo = "";
					errorInfo = e.response.result.invalid;
					for (i = 0; i < e.response.result.length; i++) {
						errorInfo += (i + 1) + ". "
								+ e.response.result[i].defaultMessage + "\n";
					}
					if (e.type == "create") {
						alert("Error: Creating the ItemMaster Details\n\n"
								+ errorInfo);
					}
					if (e.type == "update") {
						alert("Error: Updating the ItemMaster Details\n\n"
								+ errorInfo);
					}
					var grid = $("#grid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
					return false;
				}
			}

			if (e.type == "update" && !e.response.Errors)
			{
				$("#alertsBox").html("Alert");
				$("#alertsBox").html("ItemMaster details Updated successfully");
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
				$("#alertsBox").html("ItemMaster details Added successfully");
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
		/********* END OF ITEM MASTER GRID **********/

		function onItemMasterRequestStart(e)
		{
		     var grid= $("#grid").data("kendoGrid");
		     grid.cancelRow();
		}
		
		
		/**********   START OF UOM *************/
		function onUomRequestEnd(e) 
		{
			if (typeof e.response != 'undefined') 
			{
				if (e.response.status == "ERROR") 
				{
					errorInfo = "";
					errorInfo = e.response.result.deleteBaseUomError;
					alert(errorInfo);
					
					var grid = $('#uomDetailsGrid_' + SelectedItemMasterRowId).data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
					return false;
				}
			}
			if (e.type == "update" && !e.response.Errors)
			{
				$("#alertsBox").html("Alert");
				$("#alertsBox").html("UOM details Updated successfully");
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
				$("#alertsBox").html("UOM details Added successfully");
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
		/********* END OF UOM GRID **********/

		function onUomRequestStart(e)
		{
			var grid= $('#uomDetailsGrid_' + SelectedItemMasterRowId).data("kendoGrid");
		    grid.cancelRow();
		}
		
		function editItemMaster(e)
		{
			$('label[for=imId]').parent().hide();
			$('div[data-container-for="imId"]').hide();

			$('label[for=createdBy]').parent().hide();
			$('div[data-container-for="createdBy"]').hide();

			$('label[for=lastUpdatedBy]').parent().hide();
			$('div[data-container-for="lastUpdatedBy"]').hide();

			$('label[for=imPurchaseUom]').hide();
			$('div[data-container-for="imPurchaseUom"]').hide();

			$('label[for=imUomIssue]').hide();
			$('div[data-container-for="imUomIssue"]').hide();
		}

		function UomClass(container, options) {
			var booleanData = [ {
				text : 'Select',
				value : ""
			}, {
				text : 'Quantity',
				value : "Quantity",
				id : "1"
			}, {
				text : 'Weight',
				value : "Weight",
				id : "2"
			}, {
				text : 'Time',
				value : "Time",
				id : "3"
			}, {
				text : 'Volume',
				value : "Volume",
				id : "4"
			} ];

			$('<input name="UOM Class" required="true"/>').attr('data-bind','value:uomClass').appendTo(container).kendoDropDownList({
				dataSource : booleanData,
				dataTextField : 'text',
				dataValueField : 'value',
			});
		}

		function BaseUom(container, options) {
			var booleanData = [ {
				text : '--Select--',
				value : ""
			}, {
				text : 'Yes',
				value : "Yes"
			}, {
				text : 'No',
				value : "No"
			} ];

			$('<select  id="BaseUomId" />').attr('data-bind', 'value:baseUom')
					.appendTo(container).kendoDropDownList({
						dataSource : booleanData,
						dataTextField : 'text',
						dataValueField : 'value',
					});
			//$('<span class="k-invalid-msg" data-for="Select"></span>').appendTo(container); 
		}

		/****** DROP DOWN FOR ITEM PURCHASE UOM ******/
		function ItemPurchaseUOM(container, options) 
		{
		$('<select data-text-field="imPurchaseUom" data-value-field="uomId" data-bind="value:' + options.field + '" />').appendTo(container).kendoDropDownList
		({
		 	 optionLabel : "-- Select -- ",
			 autoBind : false,
			 dataSource : 
			 {
				transport : {
				read : "${itemPurchaseUomUrl}/"+ SelectedItemMasterRowId
				}
			 }
		});
		}
		/***  END FOR ITEM UOM PURCHASE **/

		/****** DROP DOWN FOR ITEM UOM ISSUE ******/
		function ItemUomIssue(container, options) 
		{
			$('<select data-text-field="imUomIssue" data-value-field="uomId" data-bind="value:' + options.field + '" />').appendTo(container).kendoDropDownList
			({
				optionLabel : "-- Select --",
				autoBind : false,
				dataSource : {
				transport : {
					read : "${itemUomIssueUrl}/"+ SelectedItemMasterRowId
				}
				}
			});
		}
		/***  END FOR UOM ISSUE ***/

		var res = [];
		var uom2 = "";
		var code2 = "";
		var flag = "";
		(function($, kendo) {
			$
					.extend(
							true,
							kendo.ui.validator,
							{
								rules : { // custom rules          	
									itemGroup : function(input, params) {
										//check for the name attribute 
										if (input.filter("[name='imGroup']").length
												&& input.val()) {
											return /^[a-zA-Z]+[ ._a-zA-Z0-9]*[a-zA-Z0-9]$/
													.test(input.val());
										}
										return true;
									},
									itemType : function(input, params) {
										//check for the name attribute 
										if (input.filter("[name='imType']").length
												&& input.val()) {
											return /^[a-zA-Z]+[ ._a-zA-Z0-9]*[a-zA-Z0-9]$/
													.test(input.val());
										}
										return true;
									},
									itemPurchaseUOM : function(input, params) {
										//check for the name attribute 
										if (input
												.filter("[name='imPurchaseUom']").length
												&& input.val()) {
											return /^[a-zA-Z]+[ ._a-zA-Z0-9]*[a-zA-Z0-9]$/
													.test(input.val());
										}
										return true;
									},
									itemUomIssue : function(input, params) {
										//check for the name attribute 
										if (input
												.filter("[name='imUom_Issue']").length
												&& input.val()) {
											return /^[a-zA-Z]+[ ._a-zA-Z0-9]*[a-zA-Z0-9]$/
													.test(input.val());
										}
										return true;
									},
									itemName : function(input, params) {
										//check for the name attribute 
										if (input.filter("[name='imName']").length
												&& input.val()) {
											return /^[a-zA-Z]+[ ._a-zA-Z0-9]*[a-zA-Z0-9]$/
													.test(input.val());
										}
										return true;
									},
									itemDescription : function(input, params) {
										//check for the name attribute 
										if (input
												.filter("[name='imDescription']").length
												&& input.val()) {
											return /^[a-zA-Z]+[ ._a-zA-Z0-9]*[a-zA-Z0-9]$/
													.test(input.val());
										}
										return true;
									},
									UomConversion : function(input, params) {
										//check for the name attribute 
										if (input
												.filter("[name='uomConversion']").length
												&& input.val()) {
											return /^[.0-9]*[.0-9]$/.test(input
													.val());
										}
										return true;
									},
									UomValidation : function(input,params)
									{
										//check for the name attribute 
										if (input.filter("[name='uom']").length&& input.val()) 
										{
											uom2 = input.val();	
											if(uom2 == getUomVal)
											{
												return true;	
											}
											else
											{
											 $.each(resUom,function(ind, val) 
											 {
												 if ((uom2 == val)&& (uom2.length == val.length)) 
												 {
													flag = uom2;
													return false;
												 }
											 });
											}
										}
										return true;
									},
									UomUniqueness : function(input) 
									{
										if (input.filter("[name='uom']").length&& input.val() && flag != "") 
										{
											flag = "";
											return false;
										}
										return true;
									},
									CodeValidation : function(input,params) 
									{
										//check for the name attribute 
										if (input.filter("[name='code']").length && input.val()) 
										{
											code2 = input.val();
											if(code2 == getCode)
											{
												return true;	
											}
											else
											{	
												$.each(resCode,function(ind, val) 
												{			
													if ((code2 == val) && (code2.length == val.length)) 
													{
														flag = code2;
														return false;
													}	
												});
											}
										}
										return true;
									},
									CodeUniqueness : function(input) 
									{
										if (input.filter("[name='code']").length && input.val() && flag != "") 
										{
											flag = "";
											return false;
										}
										return true;
									},
									itemGroupValidator : function(input,params) 
    								{
    									//check for the name attribute 
    									 if (input.attr("name") == "imGroup") {
  						                   return $.trim(input.val()) !== "";
  						                }
  						                return true;
    								},
    								itemTypeValidator : function(input,params) 
    								{
    									//check for the name attribute 
    									 if (input.attr("name") == "imType") {
  						                   return $.trim(input.val()) !== "";
  						                }
  						                return true;
    								},
    								itemNameValidator : function(input,params) 
    								{
    									//check for the name attribute 
    									 if (input.attr("name") == "imName") {
  						                   return $.trim(input.val()) !== "";
  						                }
  						                return true;
    								},
    								itemDescriptionValidator : function(input,params) 
    								{
    									//check for the name attribute 
    									 if (input.attr("name") == "imDescription") {
  						                   return $.trim(input.val()) !== "";
  						                }
  						                return true;
    								},
    								itemOptimalStockValidator : function(input,params) 
    								{
    									//check for the name attribute 
    									 if (input.attr("name") == "imOptimal_Stock") {
  						                   return $.trim(input.val()) !== "";
  						                }
  						                return true;
    								},
    								UOMValidator : function(input,params) 
    								{
    									//check for the name attribute 
    									 if (input.attr("name") == "uom") {
  						                   return $.trim(input.val()) !== "";
  						                }
  						                return true;
    								},
    								CODEValidator : function(input,params) 
    								{
    									//check for the name attribute 
    									 if (input.attr("name") == "code") {
  						                   return $.trim(input.val()) !== "";
  						                }
  						                return true;
    								},
    								itemNameUniqueness : function(input,params) 
									{
								        //check for the name attribute 
								        if (input.filter("[name='imName']").length && input.val()) 
								        {
								          enterdService = input.val().toUpperCase(); 
								          for(var i = 0; i<res1.length; i++) 
								          {
								            if ((enterdService == res1[i].toUpperCase()) && (enterdService.length == res1[i].length) ) 
								            {								            
								              return false;								          
								            }
								          }
								         }
								         return true;
								    },
								    uomRegExValidation : function(input, params) {
											//check for the name attribute 
											if (input.filter("[name='uom']").length
													&& input.val()) {
												return /^[a-zA-Z]+[ ._a-zA-Z0-9]*[a-zA-Z0-9]$/
														.test(input.val());
											}
											return true;
								    },
								    codeRegExValidation : function(input, params) {
										//check for the name attribute 
										if (input.filter("[name='code']").length
												&& input.val()) {
											return /^[a-zA-Z]+[ ._a-zA-Z0-9]*[a-zA-Z0-9]$/
													.test(input.val());
										}
										return true;
							        },
								},								
								messages : {
									itemGroup : "Item Group can not allow special symbols & numbers",
									itemType : "Item Type can not allow special symbols & numbers",
									itemPurchaseUOM : "Item Purchase UOM can not allow special symbols & except(.)",
									itemUomIssue : "Item UOM Issue can not allow special symbols & numbers",
									itemName : "Item Name can not allow special symbols & numbers",
									itemDescription : "Item Description can not allow special symbols & numbers",
									UomConversion : "Enter Positive Numbers",
									UomUniqueness : "Uom Already Exists",
									UomValidation : "Invalid Access Point Code",
									CodeValidation : "Invalid Code",
									CodeUniqueness : "Code Already Exists",
									itemGroupValidator : "Item Group Required",
									itemTypeValidator : "Item type Required",
									itemNameValidator : "Item Name Required",
									itemDescriptionValidator : "Item Description Required",
									itemOptimalStockValidator : "Item Optimal Stock Required",
									UOMValidator : "UOM is Required",
									CODEValidator : "Code is Required",
									itemNameUniqueness : "Item Name Exists",
									uomRegExValidation : "UOM can not allow special symbols & numbers",
									codeRegExValidation : "CODE can not allow special symbols & numbers",
								}
							});
		})(jQuery, kendo);

		/*****************************************************************/
	</script>
	
	<style>
	.wrappers {
	display: inline;
	float: left;
	width: 250px;
	padding-top: 10px;
}
	
	</style> -->
	
