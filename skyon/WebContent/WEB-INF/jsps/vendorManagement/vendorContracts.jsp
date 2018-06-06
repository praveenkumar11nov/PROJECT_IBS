<%@include file="/common/taglibs.jsp"%>

 <c:url value="/vendorContracts/read" var="readUrl" />
 <c:url value="/vendorContracts/createVendorContracts" var="createUrl" />
 <c:url value="/vendorContracts/updateVendorContracts" var="updateUrl" />
 
 <c:url value="/vendorContracts/destroy" var="destroyUrl" /> 
 <%-- <c:url value="/vendorPriceList/readPersons" var="personNames" /> --%>
 <%-- <c:url value="/vendorContracts/getVendorNamesAutoUrl" var="vendorNamesAutoUrl" /> --%>
 
 <%-- <c:url value="/vendorContracts/getVendorNamesForVendorcontracts" var="vendorNamesAutoUrl" /> --%>
 <c:url value="/procurement/requisition/getVendorNamesForRequisition" var="vendorNamesForVendorContracts" />
 
 <c:url value="/vendorContractsLineItems/vendorContractsReadLineItems" var="vendorContractsReadLineItems"/>
 <c:url value="/vendorContractsLineItems/vendorContractsCreateLineItems" var="vendorContractsCreateLineItems"/>
 <c:url value="/vendorContractsLineItems/vendorContractsUpdateLineItems" var="vendorContractsUpdateLineItems"/>

<!-- URL'S FOR FILTER -->
 <c:url value="/vcContract/getCamCategoryFilter" var ="getCamCategoryFilter"/>
 <c:url value="/vcContract/getDescription" var ="getDescription"/> 
 <c:url value="/vcContract/getContractNameFilter" var ="getContractNameFilter"/>
 <c:url value="/vcContract/getContractNoFilter" var ="getContractNoFilter"/> 
 <c:url value="/vcContract/getInvoicePayDays" var ="getInvoicePayDays"/>
 <c:url value="/vcContract/getVendorSla" var ="getVendorSla"/>
<c:url value="/vcContract/getContractStatusFilter" var ="getContractStatusFilter"/>
<!-- END FOR FILTER URL'S -->

<!-- URL FOR READING REQUISITION -->
<c:url value="/requisition/readRequistionDetails" var="requisitionReadUrl"/>

<!-- URL FOR READING STOREMASTER -->
<c:url value="/storeMaster/readStoreMasterDetails" var="storeMasterReadUrl"/>
<c:url value="/cascadeBasedOnId/cascadeRequisitionBasedOnVendors" var="cascadeRequisition"/>

<!-- URL'S FOR FILTER --> 
<c:url value="/procurement/requisition/getReqNamesFilter" var="getReqNamesFilter"/>
<c:url value="/vcContract/getStoreNamesFilter" var ="getStoreNamesFilter"/>
<c:url value="/vcContract/getVendorNamesFilter" var ="getVendorNamesFilter"/>


	<kendo:grid name="vendorContractsGrid" change="onChangeVendorContracts" detailTemplate="vendorContractTemplate" pageable="true"	resizable="true" filterable="true" sortable="true" reorderable="true" selectable="true" 
	scrollable="true" groupable="true" edit="addVendorContractsEvent">	
	
		<kendo:grid-editable mode="popup"/>
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Vendor Contracts"/>
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
		    <kendo:grid-column title="Vendor ContractId" field="vcId" width="60px" hidden="true"/>		    
		     
		    <kendo:grid-column title="Requisitions&nbsp;*" field="reqName" width="105px">
		    <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getReqNamesFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	 </kendo:grid-column-filterable>
	    	</kendo:grid-column>
		    		    	
		    <kendo:grid-column title="Requisitions&nbsp;*" field="reqId" editor="readRequisitionDetails" hidden="true"/>
		    
		     <kendo:grid-column title="Vendor&nbsp;*" field="vendorName" width="105px">
		     <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getVendorNamesFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	 </kendo:grid-column-filterable>
	    	</kendo:grid-column>
		     
		    <kendo:grid-column title="Vendors&nbsp;*" field="vendorId" editor="readVendorPersons" width="0px" hidden="true"/>		    
		    
		    <kendo:grid-column title="Stores&nbsp;" field="storeMasterDetails" width="105px">
		    <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getStoreNamesFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	 </kendo:grid-column-filterable>
	    	</kendo:grid-column>
		    
		    <kendo:grid-column title="Stores&nbsp;" field="storeId" editor="readStoreMasterDetails" hidden="true"/>
		       
		    <kendo:grid-column title="Cam CategoryId&nbsp;*" field="camCategoryId" format="{0:n0}" width="135px" hidden="true">
		    	<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getCamCategoryFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
		    </kendo:grid-column>
		    
		    <kendo:grid-column title="Contract Name&nbsp;*" field="contractName" width="130px">
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
		    
		    <kendo:grid-column title="Description&nbsp;*" field="description" editor="DescriptionTextArea" width="130px">
		        <kendo:grid-column-filterable>
	    		  <kendo:grid-column-filterable-ui>
    				 <script> 
						function blockNameFilter(element) {
							element.kendoAutoComplete({
								dataType: 'JSON',
								dataSource: {
									transport: {
										read: "${getDescription}"
									}
								}
							});
						}
					 </script>		
	    	     </kendo:grid-column-filterable-ui>
	    	    </kendo:grid-column-filterable>
		    </kendo:grid-column>
		    
		    <kendo:grid-column title="Contract Number&nbsp;*" field="contractNo" width="130px">
		          <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getContractNoFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    	     </kendo:grid-column-filterable-ui>
	    	    </kendo:grid-column-filterable>
		    </kendo:grid-column>	 
		    
		    <kendo:grid-column title="Contract Start Date&nbsp;*" field="contractStartDate" format="{0: dd/MM/yyyy}" filterable="true" width="155px"/>
		    <kendo:grid-column title="Contract End Date&nbsp;*" field="contractEndDate" format="{0: dd/MM/yyyy}" filterable="true" width="155px"/>
		    
		    <kendo:grid-column title="Renewal Required&nbsp;*" field="renewalRequired" editor="renewalRequiredEditor" width="130px">
		       <%--  <kendo:grid-column-filterable>
	    		  <kendo:grid-column-filterable-ui>
    				 <script> 
						function blockNameFilter(element) {
							element.kendoAutoComplete({
								dataType: 'JSON',
								dataSource: {
									transport: {
										read: "${renewalRequiredFilterUrl}"
									}
								}
							});
						}
					 </script>		
	    	     </kendo:grid-column-filterable-ui>
	    	    </kendo:grid-column-filterable> --%>
		    </kendo:grid-column>
		    
		    <kendo:grid-column title="DrGroup Id" field="drGroupId" width="120px" hidden="true"/>
		    
		    <kendo:grid-column title="Invoice Payable Days&nbsp;*" field="invoicePayableDays" format="{0:n0}" width="140px">
		     <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getInvoicePayDays}"
										}
									}
								});
					  		}
					  	</script>		
	    	     </kendo:grid-column-filterable-ui>
	    	    </kendo:grid-column-filterable>		    
		    </kendo:grid-column>
		    
		    <kendo:grid-column title="Vendor SLA&nbsp;*" field="vendorSla" format="{0:n0}" width="100px">
		    <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getVendorSla}"
										}
									}
								});
					  		}
					  	</script>		
	    	     </kendo:grid-column-filterable-ui>
	    	    </kendo:grid-column-filterable>
		    
		    </kendo:grid-column>
		    
		    
		    <kendo:grid-column title="Status" field="status" width="100px">
		       <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getContractStatusFilter}"
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
		    
		    <kendo:grid-column title="dummy1" field="dummy1" width="100px" hidden="true"/>
		    <%-- <kendo:grid-column title="dummy2" field="dummy2" width="100px" hidden="true"/> --%>
		    			
			<kendo:grid-column title="&nbsp;" width="85px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit" text="Edit" />
				</kendo:grid-column-command>
			</kendo:grid-column>
			
			<kendo:grid-column title=""
				template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Activate#=data.vcId#'>#= data.status == 'Approved' ? 'Reject' : 'Approve' #</a>"
				width="100px" />
			
		</kendo:grid-columns>
		
		<kendo:dataSource requestEnd="onRequestEnd" requestStart="onRequestStart">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-create url="${createUrl}" dataType="json" type="GET" contentType="application/json" />
				<kendo:dataSource-transport-update url="${updateUrl}" dataType="json" type="GET" contentType="application/json" />
				<kendo:dataSource-transport-destroy url="${destroyUrl}" dataType="json" type="GET" contentType="application/json" />			
			</kendo:dataSource-transport>
			
			<kendo:dataSource-schema>
				<kendo:dataSource-schema-model id="vcId">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="vcId" type="number"/>
						<kendo:dataSource-schema-model-field name="vendorName" type="string"/>
						<kendo:dataSource-schema-model-field name="vendorId" /> 
						
						<kendo:dataSource-schema-model-field name="reqId"/>
						<kendo:dataSource-schema-model-field name="reqName" type="string"/>
						
						<kendo:dataSource-schema-model-field name="storeId"/>
						<kendo:dataSource-schema-model-field name="storeMasterDetails" type="string"/>
						
						<kendo:dataSource-schema-model-field name="renewalRequired" type="string"/>
						
						<kendo:dataSource-schema-model-field name="camCategoryId" type="number">
							<kendo:dataSource-schema-model-field-validation  min="1"/>
						</kendo:dataSource-schema-model-field>
						
						<%-- <kendo:dataSource-schema-model-field name="contractType" type="string"/> --%>
						<kendo:dataSource-schema-model-field name="contractName" type="string"/>
						<kendo:dataSource-schema-model-field name="description" type="string"/>
						<kendo:dataSource-schema-model-field name="contractNo" type="string"/>						
						<kendo:dataSource-schema-model-field name="contractStartDate" type="date" defaultValue=""/>
						<kendo:dataSource-schema-model-field name="contractEndDate" type="date" defaultValue=""/>
						<kendo:dataSource-schema-model-field name="drGroupId" type="number"/>
						
						<kendo:dataSource-schema-model-field name="invoicePayableDays" type="number">
							<kendo:dataSource-schema-model-field-validation  min="1"/>
						</kendo:dataSource-schema-model-field>
												
						<kendo:dataSource-schema-model-field name="vendorSla" type="number">
							<kendo:dataSource-schema-model-field-validation  min="1"/>
						</kendo:dataSource-schema-model-field>						
						
						<kendo:dataSource-schema-model-field name="status" type="string" defaultValue="Created"/>
						<kendo:dataSource-schema-model-field name="createdBy" type="string"/>
						<kendo:dataSource-schema-model-field name="lastUpdatedBy" type="string"/>
						
						<br/>
						<br/>
						<br/>
						
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
	</kendo:grid>
	
	
	<!-- ///////////////////////////// CHILD GRID VENDOR CONTRACT ////////////////////////////////////////////////// -->
	
	<kendo:grid-detailTemplate id="vendorContractTemplate">
	<kendo:tabStrip name="tabStrip_#=vcId#">
			<kendo:tabStrip-items>	
				<kendo:tabStrip-item text="Vendor Contracts Line Items" selected="true">
					<kendo:tabStrip-item-content>
		<kendo:grid name="vendorContractDetailsGrid_#=vcId#" dataBound="UomEventDatabound" sortable="true" scrollable="true" edit="vendorContractsLineItemsEditor" remove="deleteVcLineItemsEvent">
			<kendo:grid-editable mode="popup" confirmation="Are you sure you want to remove this item?"  />
			<%-- <kendo:grid-toolbar>
				<kendo:grid-toolbarItem name="create" text="Add VendorContracts Details" />
			</kendo:grid-toolbar> --%>

			<kendo:grid-columns>
				<kendo:grid-column title="Sl.No&nbsp;*" field="vclSlno" format="{0:n0}" width="40px" />
			 	<kendo:grid-column title="Items&nbsp;*" field="imName" width="80px"/>
			 	<kendo:grid-column title="UOM&nbsp;*" field="uom" width="80px"/>
				<kendo:grid-column title="vclId" field="vclId" width="40px" hidden="true" />
				<kendo:grid-column title="Vendor Contract&nbsp;*" field="vcId" width="40px" hidden="true" />
				<kendo:grid-column title="Item Name&nbsp;*" field="imId" width="40px" hidden="true" />
				<kendo:grid-column title="Quantity&nbsp;*" field="quantity" format="{0:n0}" width="40px" />
				<kendo:grid-column title="Rate&nbsp;*" field="rate" format="{0:n0}" width="40px"/>
				<kendo:grid-column title="Amount&nbsp;*" field="amount"  width="40px"/>
				<kendo:grid-column title="Created By" field="createdBy" width="100px" hidden="true"/>
		        <kendo:grid-column title="Last Updated By" field="lastUpdatedBy" width="100px" hidden="true"/>		        
		        <kendo:grid-column title="UOM" field="uomId" width="100px" hidden="true"/>
		        
		        
		        <kendo:grid-column title="Req Type" field="reqType" width="100px" />

				<kendo:grid-column title="&nbsp;" width="100px">
					<kendo:grid-column-command>
						<kendo:grid-column-commandItem name="edit" />
						<%-- <kendo:grid-column-commandItem name="destroy"/> --%>
					</kendo:grid-column-command>
				</kendo:grid-column>
			</kendo:grid-columns>

			<kendo:dataSource requestEnd="vendorContractLineItemsRequestEnd" requestStart="vendorContractLineItemsRequestStart">
				<kendo:dataSource-transport>
					<kendo:dataSource-transport-read url="${vendorContractsReadLineItems}/#=vcId#" dataType="json" type="GET" contentType="application/json" />
					<kendo:dataSource-transport-create url="${vendorContractsCreateLineItems}/#=vcId#" dataType="json" type="GET" contentType="application/json" />
					<kendo:dataSource-transport-update url="${vendorContractsUpdateLineItems}/#=vcId#" dataType="json" type="GET" contentType="application/json" />
					<%-- <kendo:dataSource-transport-destroy url="${vendorContractsDestroyLineItems}" dataType="json" type="GET" contentType="application/json" /> --%>
		
				</kendo:dataSource-transport>

				<kendo:dataSource-schema>
					<kendo:dataSource-schema-model id="vclId">
						<kendo:dataSource-schema-model-fields>
							<kendo:dataSource-schema-model-field name="vcId" type="number"/>
							<kendo:dataSource-schema-model-field name="vclId" type="number"/>
							<kendo:dataSource-schema-model-field name="imId" type="number"/>
							<kendo:dataSource-schema-model-field name="uomId" type="number"/>
							
							<kendo:dataSource-schema-model-field name="vclSlno" type="number">
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
							
							<kendo:dataSource-schema-model-field name="createdBy" type="string"/>
							<kendo:dataSource-schema-model-field name="lastUpdatedBy" type="string"/>
							
							<kendo:dataSource-schema-model-field name="reqType" type="string"/>
							
						</kendo:dataSource-schema-model-fields>
					  </kendo:dataSource-schema-model>
				  </kendo:dataSource-schema>
			  </kendo:dataSource>
		     </kendo:grid>
		  </kendo:tabStrip-item-content>
	    </kendo:tabStrip-item>
	    
	  </kendo:tabStrip-items>
   </kendo:tabStrip>
</kendo:grid-detailTemplate>
	<!-- ///////////////////// END FOR CHILD VENDOR CONTRACT GRID  //////////////////////////////////////////// -->
	
	<div id="alertsBox" title="Alert"></div>
	
    <script>
    var statusVal = "";
	var newStatus = "";
	function UomEventDatabound(e)
	{
		var grid = $('#vendorContractDetailsGrid_' + SelectedItemMasterRowId).data("kendoGrid");
	    var gridData = grid._data;		    
	    var i = 0;
	    this.tbody.find("tr td:last-child").each(function (e) 
	    {
	    	statusVal = gridData[i].reqType;
		    /* if(statusVal === "Created")
		    {
		    	newStatus = "Active";
		    	$('<button id="activeButton" class="k-button k-button-icontext" onclick="uomActivateClick()">Activate</button>').appendTo(this);
		    }			    */ 
		    i++;
	    });
	}
    
    
     var requisitionTypeResponse = "";
     var reqDate = "";
     var contractStatus = "";
     var reqId = "";
     var budget = 0;
     var contractStatus = "";
     var requisName="";
     function onChangeVendorContracts(e) 
     {
		 var gview = $("#vendorContractsGrid").data("kendoGrid");
		 var selectedItem = gview.dataItem(gview.select());
		 SelectedItemMasterRowId = selectedItem.vcId;
		 contractStatus = selectedItem.status;
		 requisName = selectedItem.reqName;

		 
		 this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));	
	 } 
     
     /*****************  RENEWAL REQUIRED  ************/
     function renewalRequiredEditor(container, options)
	 {
		 var booleanData = [ {
		 text : 'Select',
		 value : ""
		 }, {
		   	text : 'Yes',
			value : "Yes"
		 },{
			text : 'No',
			value : "No"
		}];
		$('<input name="Renewal Type" required="true"/>').attr('data-bind', 'value:renewalRequired').appendTo(container).kendoDropDownList
		({
			dataSource : booleanData,
			dataTextField : 'text',
			dataValueField : 'value',
		});
		$('<span class="k-invalid-msg" data-for="Renewal Type"></span>').appendTo(container);		
	 }
     /********** END FOR RENEWAL REQUIRED **********/
     
    /**********  EDITOR FUNCTION FOR READING REQUISITION DETAILS  *************/
    function readRequisitionDetails(container, options)
    {
    	$('<input name="Requisition" id="requisition" data-text-field="reqName" data-value-field="reqId" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoComboBox({
			autoBind : false,
			/* cascadeFrom: "vendor", */
			placeholder : "Select Requisition",
			headerTemplate : '<div class="dropdown-header">'
				+ '<span class="k-widget k-header">Photo</span>'
				+ '<span class="k-widget k-header">Contact info</span>'
				+ '</div>',
			template : '<table><tr><td rowspan=2><span class="k-state-default"></span></td>'
				+ '<td align="left"><span class="k-state-default"><b>#: data.reqName #</b></span><br>'
				+ '<span class="k-state-default"><i>#: data.reqDescription #</i></span><br>'
				+ '<span class="k-state-default"><b>#: data.reqDate #</b></span><br></td></tr></table>',
			dataSource : {
				transport : {		
					read :  "${requisitionReadUrl}"
				}
			},
			change : function (e) {
				reqId = this.value();
				$.ajax({
					type : "POST",
					url : './requisition/readRequisitionTypeBasedOnReqId/'+reqId, 
					success: function(response)
					{
						//Iterating the response object
						for ( var s = 0, len = response.length; s < len; ++s)
						{
		                 	var reqVal = response[s];
		                 	requisitionTypeResponse = reqVal.reqType;
		                 	reqDate = reqVal.reqDate;
		                 	budget = reqVal.uomBudget;		                 	
		                }			
						if (confirm("UOM Budget for the Requisition is :"+budget+" Rs"+"\n"+"Do you want to place the contract") == true)
				 	    {
				 	    }
						else
						{
							var grid = $("#vendorContractsGrid").data("kendoGrid");
							grid.dataSource.read();
							grid.refresh();
							return false;	
						}
						if(requisitionTypeResponse == 'Item Supply')
						{
							$('label[for=storeId]').parent().show();
							$('div[data-container-for="storeId"]').show(); 
						}
						if(requisitionTypeResponse == 'Manpower')
						{
							$('label[for=storeId]').parent().hide();
							$('div[data-container-for="storeId"]').hide(); 
						}
						if(requisitionTypeResponse == 'General Contract')
						{
							$('label[for=storeId]').parent().hide();
							$('div[data-container-for="storeId"]').hide(); 
						}
						if(requisitionTypeResponse == 'AMC')
						{
							$('label[for=storeId]').parent().hide();
							$('div[data-container-for="storeId"]').hide(); 
						}
				    },
				});
	            if (this.value() && this.selectedIndex == -1) {                    
	                alert("Requisition doesn't exist!");
	                $("#Requisition").data("kendoComboBox").value("");	                
	        	}
		    } 
		});		
		$('<span class="k-invalid-msg" data-for="Requisition"></span>').appendTo(container);    	  	
    }
    /**********  END FOR EDITOR FUNCTION FOR READING REQUISITION DETAILS  *************/
    
     /*******  FOR READING VENDORS NAMES  ***********/
	 function readVendorPersons(container, options) 
	 {
			$('<input name="Vendor" id="vendor" data-text-field="fullVendorName" data-value-field="vendorId" data-bind="value:' + options.field + '" required="true"/>')
			.appendTo(container).kendoComboBox({
				autoBind : false,
				placeholder : "Select vendor",
				//cascadeFrom: "requisition",
				headerTemplate : '<div class="dropdown-header">'
					+ '<span class="k-widget k-header">Photo</span>'
					+ '<span class="k-widget k-header">Contact info</span>'
					+ '</div>',
				template : '<table><tr><td rowspan=2><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.personId#'/>" width=40 height=55 alt=\"No Image to Display\" /></span></td>'
					+ '<td align="left"><span class="k-state-default"><b>#: data.fullVendorName #</b></span><br>'
					+ '<span class="k-state-default"><i>#: data.personType #</i></span></td></tr></table>',
				dataSource : {
					transport : {		
						read :  "${vendorNamesForVendorContracts}"
					}
				},
				change : function (e) {
		            if (this.value() && this.selectedIndex == -1) {                    
		                alert("Vendor doesn't exist!");
		                $("#vendor").data("kendoComboBox").value("");
		        	}
			    } 
			});
			
			$('<span class="k-invalid-msg" data-for="Vendor"></span>').appendTo(container);
	   }
	   /*******  END FOR READING VENDORS NAMES ********/
        
	    
	    /*********  FOR READING STORE MASTER DETAILS RECORDS  ********/
    	function readStoreMasterDetails(container,options)
	    {
    		$('<input name="Stores" id="Stores" data-text-field="storeName" data-value-field="storeId" data-bind="value:' + options.field + '" />')
			.appendTo(container).kendoComboBox({
				autoBind : false,
				placeholder : "Select Stores",
				cascadeFrom: "requisition",
				headerTemplate : '<div class="dropdown-header">'
					+ '<span class="k-widget k-header">Photo</span>'
					+ '<span class="k-widget k-header">Contact info</span>'
					+ '</div>',
				template : '<table><tr><td rowspan=2><span class="k-state-default"></span></td>'
					+ '<td align="left"><span class="k-state-default"><b>#: data.storeName #</b></span><br>'
					+ '<span class="k-state-default"><i>#: data.storeLocation #</i></span></td></tr></table>',
				dataSource : {
					transport : {		
						read :  "${storeMasterReadUrl}"
					}
				},
				change : function (e) {
		            if (this.value() && this.selectedIndex == -1) {                    
		                alert("Stores doesn't exist!");
		                $("#Stores").data("kendoComboBox").value("");
		        	}
			    } 
			});		
			//$('<span class="k-invalid-msg" data-for="Stores"></span>').appendTo(container);
	    }    
	    /*********  END FOR READING STORE MASTER DETAILS RECORDS  *******/
	    
   	
      /*** CLEAR FILTER OPTION ***/
      $("#vendorContractsGrid").on("click", ".k-grid-Clear_Filter", function()
      {		
    	    $("form.k-filter-menu button[type='reset']").slice().trigger("click");
    		var grid = $("#vendorContractsGrid").data("kendoGrid");
    		grid.dataSource.read();
    		grid.refresh();
      });
      /*** END FOR CLEAR FILTER OPTION ***/
      
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
					alert("Error: Creating the Vendor Contracts Details\n\n" + errorInfo);
				}
				if (e.type == "update") 
				{
					alert("Error: Updating the Vendor Contracts Details\n\n" + errorInfo);
				}
				var grid = $("#vendorContractsGrid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
				return false;
			}
		}
		if (e.type == "update" && !e.response.Errors) 
		{	
			e.sender.read();
			//alert("Vendor Contracts UPDATED successfully");
			$("#alertsBox").html("");
			$("#alertsBox").html("Vendor Contracts UPDATED successfully");
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
			//alert("Vendor Contracts Added Successfully");
			$("#alertsBox").html("");
			$("#alertsBox").html("Vendor Contracts Added Successfully");
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
			//alert("Vendor Contracts Details Deleted Successfully");
			$("#alertsBox").html("");
			$("#alertsBox").html("Vendor Contracts DELETED successfully");
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
			var grid = $("#vendorContractsGrid").data("kendoGrid");
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
	        
		  /*  var grid= $("#vendorContractsGrid").data("kendoGrid");
		   grid.cancelRow(); */
	  }
      /*********  END FOR ONREQUEST END  ********************/


      function DescriptionTextArea(container, options) 
	  {
	      $('<textarea name="Description" data-text-field="description" data-value-field="description" data-bind="value:' + options.field + '" style="width: 148px; height: 46px;" required="true"/>')
	      .appendTo(container);
	      $('<span class="k-invalid-msg" data-for="Description"></span>').appendTo(container); 
	  } 
      
      $("#vendorContractsGrid").on("click", ".k-grid-add", function() 
      {
    	   /*  if($("#vendorContractsGrid").data("kendoGrid").dataSource.filter())
    	    {
    		    $("form.k-filter-menu button[type='reset']").slice().trigger("click");
    		 	var grid = $("#vendorContractsGrid").data("kendoGrid");
    			grid.dataSource.read();
    			grid.refresh();
    		} */
      });
      
	  function addVendorContractsEvent(e)
	  {
		  $(".k-grid-update").click(function () 
		  {
			 var req = $("#requisition").data("kendoComboBox");
			 if(req.select() == -1) 
			 {
			     alert("Select Requisition");
				 return false;	
			 }
			 
		     //ContractStartDate
			 var startDt = e.model.contractStartDate;
			 var contractStartDtVal = startDt.getFullYear()+ '-'+(startDt.getMonth() + 1) + '-' + startDt.getDate();
							  			  
			 //RequisitionDate
			 var reqdt = new Date(reqDate);
			 var reqDateVal = reqdt.getFullYear()+ '-' + (reqdt.getMonth() + 1)+ '-' + reqdt.getDate();
			 
			 /* if(contractStartDtVal < reqDateVal)
			 {
				  alert("              \t\tRequisition Created On { "+reqDateVal+" }\nContract StartDateShould Be Greater/Equal To Requisition Date");		
				  return false;
			 }  */
		  });
		  
		  var contractStartDate = $('input[name="contractStartDate"]').kendoDatePicker().data("kendoDatePicker");
		  var contractEndDate = $('input[name="contractEndDate"]').kendoDatePicker().data("kendoDatePicker");
		  var dt = "";
		  
		   $('input[name="contractStartDate"]').change(function() 
		   {
				contractEndDate.min($('input[name="contractStartDate"]').val());
		   });
			
		   $('input[name="contractEndDate"]').change(function() 
		   {
			  contractStartDate.max($('input[name="contractEndDate"]').val());
		   });
		  
		  $.ajax
		  ({
			  type : "POST",
			  url : './requisition/checkApprovedRequisitionsAvailability',
			  success: function(response) 
			  {
				  if(response == 0)
				  {
					  $("#alertsBox").html("");
					  $("#alertsBox").html("No Approved Requisition Available");
					  $("#alertsBox").dialog({
						   modal: true,
						   buttons: {
							  "Close": function() {
								$( this ).dialog( "close" );
							   }
						   }
					 });   
					 var grid = $("#vendorContractsGrid").data("kendoGrid");
					 grid.cancelRow();
					 grid.close();
				  }
			  }
		  });
		  
		   $(".k-edit-form-container").css
		   ({
			  "width" : "540px"
		   });
		   $(".k-window").css
		   ({
			  "top" : "205px"
		   });
		   $('.k-edit-label:nth-child(20n+1)').each(function(e) 
		   {
				$(this).nextAll(':lt(19)').addBack().wrapAll('<div class="wrappers"/>');
		   });
		  
		  $('a[id="temPID"]').remove();
		  $('.k-edit-field .k-input').first().focus();	
		  $(".k-window-title").text("Add Vendor Contract Details");
		  $(".k-grid-update").text("Save");
			
		  $('label[for=dummy1]').parent().remove();
		  $('div[data-container-for="dummy1"]').remove();
		  
		  $('label[for=camCategoryId]').parent().remove();
		  $('div[data-container-for="camCategoryId"]').remove();
		  		  		  
		  $('label[for=reqName]').parent().remove();
		  $('div[data-container-for="reqName"]').remove();
		  
		  $('label[for=storeMasterDetails]').parent().remove();
		  $('div[data-container-for="storeMasterDetails"]').remove();
		  
		  $('label[for=vcId]').parent().hide();
		  $('div[data-container-for="vcId"]').hide();
			
		  $('label[for=drGroupId]').parent().hide();
		  $('div[data-container-for="drGroupId"]').hide();		
			
		  $('label[for=status]').parent().hide();
		  $('div[data-container-for="status"]').hide();
			
		  $('label[for=createdBy]').parent().hide();
		  $('div[data-container-for="createdBy"]').hide();
			
		  $('label[for=lastUpdatedBy]').parent().hide();
		  $('div[data-container-for="lastUpdatedBy"]').hide();
		  
		  $('label[for=vendorName]').parent().hide();
		  $('div[data-container-for="vendorName"]').hide();
		  
		  $(".k-grid-cancel").click(function () 
		  {
			  var grid = $("#vendorContractsGrid").data("kendoGrid");
			  grid.dataSource.read();
			  grid.refresh();
	      });
		  if (e.model.isNew()) 
		  {  
			  securityCheckForActions("./vendorManagement/vendorContracts/createButton");			  
		  }
		  else
		  {
			 
			  $("#reqName").val(requisName);

			  securityCheckForActions("./vendorManagement/vendorContracts/updateButton");
			  $(".k-window-title").text("Edit Vendor Contract Details");
			  $(".k-grid-update").text("Update");
		  }
	 }
	 	
	 /*************  FOR ACTIVATE AND DE-Activate VendorContracts Status  *************************/
	 $("#vendorContractsGrid").on("click", "#temPID", function(e) 
	 {
		 var button = $(this), enable = button.text() == "Approve";
		 var widget = $("#vendorContractsGrid").data("kendoGrid");
		 var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
		 
		 var status = dataItem.status;		
		 /* if(status == 'Approved')
	     {
			alert("Purchase-Order already Placed");
			return false;
		 } */
		 
		 var result=securityCheckForActionsForStatus("./vendorManagement/vendorContracts/activateButton");
	 	 if(result=="success")
		 {
	 		if(enable) {
				$.ajax({
					type : "GET",
					url : "./vendorContracts/vendorContractsStatus/" + dataItem.vcId + "/Approved",
					dataType : "text",
					async: false,
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
						$('#vendorContractsGrid').data('kendoGrid').dataSource.read();
					}
				});
			} else {
				$.ajax({
					type : "GET",
					url : "./vendorContracts/vendorContractsStatus/" + dataItem.vcId + "/Rejected",
					dataType : "text",
					async: false,
					success : function(response) {
					  
						if(response==""){
						$("#alertsBox").html("");
						$("#alertsBox").html("Already Purchase Order Placed So Requisition Cannot be Rejected ");
						$("#alertsBox").dialog({
							modal : true,
							buttons : {
								"Close" : function() {
									$(this).dialog("close");
								}
							}
						});
					   }
						
						else{
							
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
							$('#vendorContractsGrid').data('kendoGrid').dataSource.read();
						}
						
					}
				});
			}
	    }
	});
	 /////////////////////////////  SCRIPTING IMPLEMENTATION FOR VENDOR CONTRACTS LINEITEMS ///////////////////////////////////////////
	 
	 
	 
	 /************** ON REQUEST END FUNCTION  **********************/
	  function vendorContractLineItemsRequestEnd(e) 
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
					alert("Error: Creating the Vendor Contracts LineItems\n\n" + errorInfo);
				}
				if (e.type == "update") 
				{
					alert("Error: Updating the Vendor Contracts LineItems\n\n" + errorInfo);
				}
				var grid = $('#vendorContractDetailsGrid_' + SelectedItemMasterRowId).data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
				return false;
			}
		}
		if (e.type == "update" && !e.response.Errors) 
		{	
			e.sender.read();
			//alert("Vendor Contracts UPDATED successfully");
			$("#alertsBox").html("");
			$("#alertsBox").html("Vendor Contracts UPDATED successfully");
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
			//alert("Vendor Contracts Added Successfully");
			$("#alertsBox").html("");
			$("#alertsBox").html("Vendor Contracts Added Successfully");
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
			//alert("Vendor Contracts Details Deleted Successfully");
			$("#alertsBox").html("");
			$("#alertsBox").html("Vendor Contracts Details Deleted Successfully");
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
			var grid = $('#vendorContractDetailsGrid_' + SelectedItemMasterRowId).data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
		}
     }	
	  function vendorContractLineItemsRequestStart(e)
	  {
		   var grid= $('#vendorContractDetailsGrid_' + SelectedItemMasterRowId).data("kendoGrid");
		   grid.cancelRow();
	  }
     /*********  END FOR ONREQUEST END  ********************/
	 
     function deleteVcLineItemsEvent(e)
     {
    	 securityCheckForActions("./vendorManagement/vendorContractsLineItems/deleteButton"); 
     }
     
	 function vendorContractsLineItemsEditor(e)
     {
		 $('.k-edit-field .k-input').first().focus();	
		 $(".k-window-title").text("Edit Vendor Contracts Line Items");
		 $(".k-grid-update").text("Update");
		 
		 $('label[for=vclId]').parent().hide();
		 $('div[data-container-for="vclId"]').hide();
		 
		 $('label[for=reqType]').parent().hide();
		 $('div[data-container-for="reqType"]').hide();
		 
		 $('[name="imId"]').attr("readonly", true);
		 $('[name="uom"]').attr("readonly", true);
		 
		 $('label[for=imId]').parent().hide();
		 $('div[data-container-for="imId"]').hide();
		 
		 $('label[for=uomId]').parent().hide();
		 $('div[data-container-for="uomId"]').hide();
		 
		 $('label[for=vclSlno]').parent().hide();
		 $('div[data-container-for="vclSlno"]').hide();
			
		 $('label[for=status]').parent().hide();
		 $('div[data-container-for="status"]').hide();
		 
		 $('label[for=createdBy]').parent().hide();
		 $('div[data-container-for="createdBy"]').hide();
			
		 $('label[for=lastUpdatedBy]').parent().hide();
		 $('div[data-container-for="lastUpdatedBy"]').hide();
		 
		 $('label[for=vcId]').parent().hide();
		 $('div[data-container-for="vcId"]').hide();
		 
		 $('input[name="imName"]').prop('readonly',true);
		 $('input[name="amount"]').prop('readonly',true);
		 
		 
		 if (e.model.isNew()) 
		 {
			// securityCheckForActions("./procurement/vendorPriceList/createButton"); 	 
		 }
		 else
		 {
			 if(contractStatus == "Approved")
			 {
				 $("#alertsBox").html("Alert");
				 $("#alertsBox").html("Contract Approved...!Details Cannot be Edited");
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
				 var grid = $('#vendorContractDetailsGrid_' + SelectedItemMasterRowId).data("kendoGrid");
				 grid.dataSource.read();
				 grid.refresh();
				 return false; 
			 }			 
			 if(statusVal == "Manpower")
		     {
				 $('label[for=imName]').parent().hide();
				 $('div[data-container-for="imName"]').hide();
			 }
			 if(statusVal == "General Contract")
		     {
				 $('label[for=imName]').parent().hide();
				 $('div[data-container-for="imName"]').hide();
				 
				 $('label[for=uom]').parent().hide();
				 $('div[data-container-for="uom"]').hide();				 
			 }
			 if(statusVal == "AMC")
		     {
				 $('label[for=imName]').parent().hide();
				 $('div[data-container-for="imName"]').hide();
				 
				 $('label[for=uom]').parent().hide();
				 $('div[data-container-for="uom"]').hide();				 
			 }
			 var quantity = "";
			 var rate = "";
			 var totalAmount = "";
			 var meterFactor = "";
			 securityCheckForActions("./vendorManagement/vendorContractsLineItems/updateButton");
			 $('input[name="rate"]').change(function() 
			 {
				 quantity = $('input[name="quantity"]').val();
				 rate = $('input[name="rate"]').val();
				 totalAmount = rate * quantity;	
				 
				 $('input[name="amount"]').val(totalAmount);
			 });
		 }
     }
	 ///////////////////////////// END FOR SCRIPTING VENDOR CONTRACTS LINEITEMS /////////////////////////////////////////////////
	 
	 
	 /************************************************ VALIDATOR FUNCTION FOR INPUTS ******************************************************/
	  var res = [];
    	(function($, kendo) {
    		$
    				.extend(
    						true,
    						kendo.ui.validator,
    						{
    							rules :
    							{ // custom rules          	
    								firstNameValidator : function(input,params) 
    								{
    									//check for the name attribute 
    									 if (input.attr("name") == "camCategoryId") {
  						                   return $.trim(input.val()) !== "";
  						                }
  						                return true;
    								},
    								invoicePayableDaysValidator : function(input,params) 
    								{
    									//check for the name attribute 
    									 if (input.attr("name") == "invoicePayableDays") {
  						                   return $.trim(input.val()) !== "";
  						                }
  						                return true;
    								},
    								vendorSlaValidator : function(input,params) 
    								{
    									//check for the name attribute 
    									 if (input.attr("name") == "vendorSla") {
  						                   return $.trim(input.val()) !== "";
  						                }
  						                return true;
    								},
    								contractNameValidator : function(input,params) 
    								{
    									//check for the name attribute 
    									 if (input.attr("name") == "contractName") {
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
    								uomValidator : function(input,params) 
    								{
    									//check for the name attribute 
    									 if (input.attr("name") == "uom") {
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
    								contractStartDate : function(input,params) 
    								{
    									//check for the name attribute 
    									 if (input.attr("name") == "contractStartDate") {
  						                   return $.trim(input.val()) !== "";
  						                }
  						                return true;
    								},
    								contractEndDate : function(input,params) 
    								{
    									//check for the name attribute 
    									 if (input.attr("name") == "contractEndDate") {
  						                   return $.trim(input.val()) !== "";
  						                }
  						                return true;
    								},
    							},
    							messages : { 
    								firstNameValidator: "Category Id Required",
    								invoicePayableDaysValidator : "Enter Invoice Payable Days",
    								vendorSlaValidator : "Enter Vendor Sla",
    								contractNameValidator : "Contract Name Required",
    								uomValidator : "UOM Is Required",
    								quantityValidator : "Quantity Is Required",
    								rateValidator : "Rate Is Required",
    								amountValidator : "Amount Is Required",
    								contractEndDate:"Contract End Date is Required",
    								contractStartDate:"Contract Start Date is Required",
    								
    							}
    						});
    	})(jQuery, kendo);
	 
    	
	 
	 
	 
	 /***********************************************  END FOR INPUT VALIDATOR FUNCTION  *******************************************/
</script>

<style>
.wrappers {
    		display: inline;
    		float: left;
    		width: 250px;
    		padding-top: 10px;

    		/* float:left;  */
    		/* border: 1px solid red;
    	 */
    		/* border: 1px solid green;
    	    overflow: hidden; */
    	}
    	td{
            vertical-align: middle;
        }
	 
</style>
