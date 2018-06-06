<%@include file="/common/taglibs.jsp"%>

	<c:url value="/procurement/requisition/read" var="readUrl" />
	<c:url value="/procurement/requisition/create" var="createUrl" />
	<c:url value="/procurement/requisition/update" var="updateUrl" />
	<c:url value="/procurement/requisition/destroy" var="destroyUrl" />
	<c:url value="/procurement/requisitionDetails/readRequisitionDetails" var="readReqDetails" />
	<c:url value="/procurement/requisitionDetails/getRequisitionId" var="readRequisitionId" />
	<c:url value="/procurement/requisitionDetails/getItemMasterId" var="readItemMasterId" />
	<c:url value="/procurement/requisitionDetails/createRequisitionDetails" var="createReqDetails" />
	<c:url value="/procurement/requisitionDetails/updateRequisitionDetails" var="updateReqDetails" />
	<c:url value="/procurement/requisitionDetails/destroyRequisitionDetails" var="destroyReqDetails" />
	
	<%-- <c:url value="/vendorContracts/getVendorNamesAutoUrl" var="vendorNamesAutoUrl" /> --%>
	<c:url value="/procurement/requisition/getVendorNamesForRequisition" var="vendorNamesAutoUrl" />
	
	<!-- URL TO READ STAFF NAME WITH ID -->
	<c:url value="/procurement/readUserStaff/readUserStaffPersons" var="readUserStaffPersons" />
	
	<!-- URL TO READ DEPARTMENTS WITH DEPARTMENT NAME & ID -->	
	<c:url value="/procurement/vendorUsers/readDepartmentForVendors" var="readDepartmentForVendors" />
	
	<!-- URL TO READ VENDORS -->
	<c:url value="/procurement/vendors/readVendors" var="readVendors" /> 
	
	<!-- URL FOR UOM DETAILS -->
	<c:url value="/procurement/vendors/readUomDetails" var="uomDetails"/>	
	<c:url value="/procurement/vendor/readOnlyVendorsDetails" var="readOnlyVendorsDetails"/>
	
	<!-- For ReqName Check Uniqueness -->
	<c:url value="/procurement/requisition/getRequisitionNames" var="readRequisitionNames"/>
	
	<!-- URL'S FOR REQUISITION FILTER -->
	<c:url value="/procurement/requisition/getReqNamesFilter" var="getReqNamesFilter"/>
	<c:url value="/procurement/requisition/getReqTypeFilter" var="getReqTypeFilter"/>
	<c:url value="/procurement/requisition/getReqDepartmentFilter" var="getReqDepartmentFilter"/>
	<c:url value="/procurement/requisition/getReqUsersFilter" var="getReqUsersFilter"/>
	<c:url value="/procurement/requisition/getVendorsFilter" var="getVendorsFilter"/>
	<c:url value="/procurement/requisition/getQuoteFilter" var="getQuoteFilter"/>
	<c:url value="/procurement/requisition/getDescriptionFilter" var="getDescriptionFilter"/>
	<c:url value="/procurement/requisition/getReqStatusFilter" var="getReqStatusFilter"/>
	
	<!-- URL'S FOR REQUISITION DETAILS FILTER -->		
    <c:url value="/procurement/reqDetails/getReqDetailSlNoFilter" var="getReqDetailSlNoFilter"/>
    <c:url value="/procurement/reqDetails/getItemNameFilter" var="getItemNameFilter"/>
    <c:url value="/procurement/reqDetails/getReqDetailsUOMNameFilter" var="getReqDetailsUOMNameFilter"/>
    <c:url value="/procurement/reqDetails/getReqDetailsDescriptionFilter" var="getReqDetailsDescriptionFilter"/>
    <c:url value="/procurement/reqDetails/getReqDetailsQuantityFilter" var="getReqDetailsQuantityFilter"/>
	
	<c:url value="/procurement/reqDetails/readDesignationBasedOnDepartment" var="readDesignationBasedOnDepartment"/>
	<c:url value="/procurement/requisition/getStoreNameFilter" var="getStoreNameFilter"/>
	
	
	
	<c:url value="/stores/readStoresDetails" var="readStores"/>
	
	<kendo:grid name="grid" detailTemplate="template" pageable="true" change="onRequisitionGridChange"	resizable="true" filterable="true" sortable="true" reorderable="true" selectable="true" dataBound="requisitionDataBound" 
	scrollable="true" groupable="true" edit="requisitionMasterEvent" remove="deleteRequisitionEvent" >	
		<kendo:grid-editable mode="popup" confirmation="Are you sure you want to remove this item?" />
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Requisition"/>
			<kendo:grid-toolbarItem text="Clear Filter" name="Clear_Filter"/>
		</kendo:grid-toolbar>
		
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" ></kendo:grid-pageable>
			
                       <kendo:grid-filterable extra="false">
				 		<kendo:grid-filterable-operators>
				  			<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains"/>
				  			<kendo:grid-filterable-operators-date lte="Date Before" gte="Date After"/>
				 		</kendo:grid-filterable-operators>
					</kendo:grid-filterable>
		
		<kendo:grid-columns>
		    
		    <kendo:grid-column title="Requisition Name&nbsp;*" field="reqName" width="155px">	
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
		    
		    <kendo:grid-column title="Requisition Type&nbsp;*" field="reqType" editor="RequisitionTypes" width="155px">	    
		     <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getReqTypeFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	 </kendo:grid-column-filterable>
	    	 </kendo:grid-column>
		    
		    <kendo:grid-column title="Store&nbsp;*" field="storeId" editor="readStoreDetails" hidden="true"/>		    
		    <kendo:grid-column title="Store&nbsp;*" field="storeName" width="150px">
		    <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getStoreNameFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	 </kendo:grid-column-filterable>
		    </kendo:grid-column>
		    
		    
		    <kendo:grid-column title="Department&nbsp;*" field="dept_Id" editor="ReadDepartment" hidden="true"/>
		    
		    <kendo:grid-column title="Department&nbsp;*" field="dept_Name" width="150px">
		    <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getReqDepartmentFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	 </kendo:grid-column-filterable>
	    	 </kendo:grid-column>
		    
		    <kendo:grid-column title="Requisition By&nbsp;*" field="personId" editor="RequisitionUsers"  hidden="true"/>		    
		    
		    <kendo:grid-column title="Requisition By&nbsp;*" field="fullName" width="155px">
		    <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getReqUsersFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	 </kendo:grid-column-filterable>
	    	 </kendo:grid-column>
		    
		    <kendo:grid-column title="Recommended Vendor&nbsp;*" field="vendorId" editor="readVendors" hidden="true"/>
		    
		    
		    <kendo:grid-column title="Recommended Vendor&nbsp;*" field="fullVendorName" width="180px">
		    <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getVendorsFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	 </kendo:grid-column-filterable>
	    	 </kendo:grid-column>
		    		    
		    <kendo:grid-column title="Quote Required&nbsp;*" field="reqVendorQuoteRequisition" editor="VendorQuoteRequisition" width="150px">
		    <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getQuoteFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	 </kendo:grid-column-filterable>
	    	 </kendo:grid-column>
		    		    
		    <kendo:grid-column title="Requisition Date&nbsp;*" field="reqDate" format="{0:dd/MM/yyyy}" width="160px"/>
		    <kendo:grid-column title="Requisition By Date&nbsp;*" field="reqByDate" format="{0:dd/MM/yyyy}" width="160px"/>
		    <kendo:grid-column title="Dr GroupId" field="drGroupId" hidden="true"/>
		    
		    <kendo:grid-column title="Description&nbsp;*" field="reqDescription" editor="requisitionDescription" width="155px" filterable="false">
		    <%-- <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getDescriptionFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	 </kendo:grid-column-filterable> --%>
	    	 </kendo:grid-column>
		    
		    <kendo:grid-column title="Status" field="status" width="120px">
		    <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getReqStatusFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	 </kendo:grid-column-filterable>
	    	 </kendo:grid-column>
		    
		    
		    
		    <kendo:grid-column title="CreatedBy" field="createdBy" width="85px" hidden="true"/>
		    <kendo:grid-column title="LastUpdatedBy" field="lastUpdatedBy" width="85px" hidden="true"/>
		    
			<kendo:grid-column title="&nbsp;" width="200px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit"/>
					<%-- <kendo:grid-column-commandItem name="destroy" /> --%>
					<kendo:grid-column-commandItem name="print" text="Print Requisition" click="printRequisitionData"/>
				</kendo:grid-column-command>
			</kendo:grid-column>
			
			<kendo:grid-column title="" template="<a href='\\\#' id='temPIDApprove' class='k-button k-button-icontext btn-destroy k-grid-Activate#=data.reqId#'>Approve</a>
				<a  href='\\\#' id='temPIDReject' class='k-button k-button-icontext btn-destroy k-grid-Activate#=data.reqId#'>Reject</a>" width="175px" />							
		</kendo:grid-columns>
		
		<kendo:dataSource requestEnd="onRequestEnd" requestStart="onRequsitionStart">
			<kendo:dataSource-transport>
			<kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json" />
			<kendo:dataSource-transport-create url="${createUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-update url="${updateUrl}" dataType="json" type="GET" contentType="application/json" />
			<%-- <kendo:dataSource-transport-create url="${createUrl}" dataType="json" type="POST" contentType="application/json" />
			<kendo:dataSource-transport-update url="${updateUrl}" dataType="json" type="POST" contentType="application/json" />
			<kendo:dataSource-transport-destroy url="${destroyUrl}" dataType="json" type="POST" contentType="application/json" />
				 <kendo:dataSource-transport-parameterMap>
					<script>
						function parameterMap(options, type) 
						{
							return JSON.stringify(options);
						}
					</script>
				</kendo:dataSource-transport-parameterMap> --%>
			</kendo:dataSource-transport>
			
			<kendo:dataSource-schema>
				<kendo:dataSource-schema-model id="reqId">
					<kendo:dataSource-schema-model-fields>						
						<kendo:dataSource-schema-model-field name="personId" type="number"/>
						
						<kendo:dataSource-schema-model-field name="reqName" type="string">
						  <%-- <kendo:dataSource-schema-model-field-validation pattern="^.{0,20}$" /> --%>
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="reqDescription" type="string"/>
						<kendo:dataSource-schema-model-field name="dept_Id"/>
						<kendo:dataSource-schema-model-field name="vendorId"/>
						<kendo:dataSource-schema-model-field name="storeId"/>	
						<kendo:dataSource-schema-model-field name="reqType" type="string"/>					
						<kendo:dataSource-schema-model-field name="fullName" type="string"/>					
						<kendo:dataSource-schema-model-field name="reqDate" type="date"/>						
						<kendo:dataSource-schema-model-field name="reqByDate" type="date"/>						
						<kendo:dataSource-schema-model-field name="drGroupId" type="number"/>						
						<kendo:dataSource-schema-model-field name="reqVendorQuoteRequisition" type="string"/>
						<kendo:dataSource-schema-model-field name="status" type="string" defaultValue="Created"/>						
						<kendo:dataSource-schema-model-field name="createdBy" type="string"/>
						<kendo:dataSource-schema-model-field name="lastUpdatedBy" type="string"/>						
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
	</kendo:grid>
	
	
	<kendo:grid-detailTemplate id="template">
		<kendo:grid name="reqDetailsGrid#=reqId#"  dataBound="onReqDetailsDataBound" sortable="true" scrollable="true" edit="requisitionDetailsEvent" remove="deleteReqDetailsEvent">
		<kendo:grid-editable mode="popup" confirmation="Are you sure you want to remove this item?" />
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Enter Requisition Details" />
		</kendo:grid-toolbar>
			
			<kendo:grid-columns>
		    <kendo:grid-column title="RD_Id" field="rdId" width="70px" hidden="true"/>
		    
		    <kendo:grid-column title="Sl.No&nbsp;*" field="rdSlno" format="{0:n0}" width="70px"/>
		    <kendo:grid-column title="Item&nbsp;*" field="imId" editor="ItemMasterIdValue" width="70px" hidden="true"/>		    
		    <kendo:grid-column title="Item&nbsp;*" field="imName" width="70px"/>		    			
			<kendo:grid-column title="UOM&nbsp;*" field="uomId" editor="UomEditorFunction" width="70px" hidden="true"/>			
		    <kendo:grid-column title="UOM&nbsp;*" field="uom" width="70px"/>		   
	    	<kendo:grid-column title="Designation&nbsp;*" field="dn_Id" editor="designationEditorFunction" width="70px" hidden="true"/>			
		    <kendo:grid-column title="Designation&nbsp;*" field="dn_Name" width="70px"/>		    		    		    
		    <kendo:grid-column title="Description&nbsp;*" field="rdDescription" editor="requisitionDescriptionEditor" width="70px"/>
		    <kendo:grid-column title="Quantity&nbsp;*" field="rdQuantity" format="{0:n0}" width="70px"/>
		    <kendo:grid-column title="Fulfilled&nbsp;*" field="reqFulfilled" format="{0:n0}" width="70px"/>		    		   		    
		    <kendo:grid-column title="CreatedBy" field="createdBy" width="70px" hidden="true"/>
		    <kendo:grid-column title="LastUpdatedBy" field="lastUpdatedBy" width="70px" hidden="true"/>		    
		    <kendo:grid-column title="" field="dummyManpower" width="70px" hidden="true" editor="manPowerDropdownEditor"/>
		     <kendo:grid-column title="Budget&nbsp;*" field="uomBudget" format="{0:n0}" width="70px"/>
			
			<kendo:grid-column title="&nbsp;" width="100px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit"/>
					<kendo:grid-column-commandItem name="destroy"/> 
				</kendo:grid-column-command>
			</kendo:grid-column>
			
		</kendo:grid-columns>
			
			<kendo:dataSource pageSize="5" requestEnd="onRequestEndReqDetails" requestStart="onRequestStartReqDetails">
				<kendo:dataSource-transport>
					<kendo:dataSource-transport-read url="${readReqDetails}/#=reqId#" dataType="json" type="GET" contentType="application/json" />
					<kendo:dataSource-transport-create url="${createReqDetails}/#=reqId#" dataType="json" type="POST" contentType="application/json" />					
					<kendo:dataSource-transport-update url="${updateReqDetails}/#=reqId#" dataType="json" type="POST" contentType="application/json" />
					<kendo:dataSource-transport-destroy url="${destroyReqDetails}" dataType="json" type="POST" contentType="application/json" />
					<kendo:dataSource-transport-parameterMap>
						<script>
							function parameterMap(options, type) 
							{
								return JSON.stringify(options);
							}
						</script>
					</kendo:dataSource-transport-parameterMap>
				</kendo:dataSource-transport>
				
				<kendo:dataSource-schema>
				<kendo:dataSource-schema-model id="rdId">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="rdId" type="number"/>
						
						<kendo:dataSource-schema-model-field name="rdSlno" type="number">
							<kendo:dataSource-schema-model-field-validation min="1" />
						</kendo:dataSource-schema-model-field>
												
						<kendo:dataSource-schema-model-field name="imId" type="number" defaultValue=""/>
						<kendo:dataSource-schema-model-field name="uomId" type="number"/>
						<kendo:dataSource-schema-model-field name="dn_Id" type="number"/>
						<%-- <kendo:dataSource-schema-model-field name="reqType" type="string"/> --%>						
						<kendo:dataSource-schema-model-field name="rdDescription" type="String"/>						
						
						<kendo:dataSource-schema-model-field name="rdQuantity" type="number">
						  <kendo:dataSource-schema-model-field-validation min="1" />
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="uomBudget" type="number">
						  <kendo:dataSource-schema-model-field-validation min="1" />
						</kendo:dataSource-schema-model-field>
												
						<kendo:dataSource-schema-model-field name="createdBy" type="String"/>
						<kendo:dataSource-schema-model-field name="lastUpdatedBy" type="String"/>
						<kendo:dataSource-schema-model-field name="dummyManpower" type="String"/>
						
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
			</kendo:dataSource>
		</kendo:grid>
	</kendo:grid-detailTemplate>
	<div id="alertsBox" title="Alert"></div>
	
	<script>
	function readStoreDetails(container,options)
	{
		$('<input name="Store" id="storeId" data-text-field="storeName" data-value-field="storeId" data-bind="value:' + options.field + '"/>').appendTo(container).kendoDropDownList
		({
			optionLabel : 
			{
				storeName : "Select",
				storeId : "",
			},
		    defaultValue : false,
			sortable : true,
			dataSource :{
				transport :{
					 read : "${readStores}"
				}
			}
		});
		/* $('<span class="k-invalid-msg" data-for="Store"></span>').appendTo(container); */
	}
	
	</script>
	
	
	<script>
		/*****  FOR ONCHANGE EVENT FOR REQUISITION  *****/
		
		var id = "";
		var requisitionType = "";
		var status = "";
		var reqName = "";
		var reqType = "";
		var reqDescription = "";
		var fullName = "";
		var reqDate = "";
		var reqByDate = "";
		var reqVendorQuoteRequisition = "";
		var deptName = "";
		var status = "";
		var deptId = "";
		
		function onRequisitionGridChange(e)
		{
			var gview = $("#grid").data("kendoGrid");
			var selectedItem = gview.dataItem(gview.select());
			id = selectedItem.reqId;
			reqName = selectedItem.reqName;
			reqDescription = selectedItem.reqDescription;
			fullName = selectedItem.fullName;
			reqDate = selectedItem.reqDate;
			reqByDate = selectedItem.reqByDate;
			reqVendorQuoteRequisition = selectedItem.reqVendorQuoteRequisition;
			status = selectedItem.status;
			requisitionType = selectedItem.reqType;
			deptName = selectedItem.dept_Name;
			//dept_Id = selectedItem.dept_Id;
			deptId = selectedItem.dept_Id;
			this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
		}
		/****  END FOR ONCHANGE EVENT FOR REQUISITION  ****/
	</script>
	
	<!----------------------------------------------------@ REQUISITION MASTER SCRIPT @----------------------------------------------------->
	<script>
	
	var requisitionValueSelected = "";
	$(document).on('change', 'select[class="requisitionType"]', function() 
    {
		requisitionValueSelected = $('.requisitionType option:selected').eq(0).text();
		if(requisitionValueSelected == 'Item Supply')
		{
			$('label[for=storeId]').parent().show();
			$('div[data-container-for="storeId"]').show();
		}
		else if(requisitionValueSelected == 'Manpower')
		{
			$('label[for=storeId]').parent().hide();
			$('div[data-container-for="storeId"]').hide();			
		}
		else if(requisitionValueSelected == 'General Contract')
		{
			$('label[for=storeId]').parent().hide();
			$('div[data-container-for="storeId"]').hide();			
		}
		else if(requisitionValueSelected == 'AMC')
		{
			$('label[for=storeId]').parent().hide();
			$('div[data-container-for="storeId"]').hide();			
		}
    });
	
	function printRequisitionData(e)
	{
		var result=securityCheckForActionsForStatus("./procurement/requisition/printButton");
	 	if(result=="success")
		{
	 		var win = window.open('', '_blank', 'width=800, height=500');
	        var doc = win.document.open(); 
	        win.document.write("<html><body onload=window.print()><div id=printpopup style=background-image:url(resources/images/b3.png) ><table width=600px><tr><th colspan=2 text-align:center>Requisition Data</th></tr><tr ><tr><td><label>Requisition Id</label></td><td>"+id+"</td></tr><tr><td><label>Requisition Name:</label></td><td>"+reqName+"<tr><td>Requisition Type:</td><td>"+requisitionType+"</td></tr><tr><td>Requisition Description:</td><td>"+reqDescription+"</td></tr><tr><td><label>Requisition Department</label></td><td>"+deptName+"</td></tr><tr><td>Requesting User : </td><td>"+fullName+"</td></tr><tr><td>Requisition Date:</td><td>"+reqDate+"</td></tr><tr><td>Requisition By Date:</td><td>"+reqByDate+"</td></tr><tr><td>Requisition Vendor Quote:</td><td>"+reqVendorQuoteRequisition+"</td></tr><tr><td>Requisition Status</td><td>"+status+"</td></tr></table></div></html>");
	        win.document.close();
		}
	}
	
	function deleteRequisitionEvent(e)
	{
		securityCheckForActions("./procurement/requisition/deleteButton");
	}
	
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
	
	/********  For Adding Requisition MASTER  *********/	
	var getRequisitionName = "";
    var resReqName = [];
    var flag = "";
    var code2 = ""; 
    var res1 = new Array();
	function requisitionMasterEvent(e)
	{	
		$('label[for=storeName]').parent().hide();
		$('div[data-container-for="storeName"]').hide();
		$("input[name='reqName']").attr('maxlength', '40');
		
		if (e.model.isNew()) 
		{
			securityCheckForActions("./procurement/requisition/createButton"); 
			res1 = [];
			$.ajax
			({
			  type : "GET",
			  dataType:"text",
			  url : '${readRequisitionNames}',
			  dataType : "JSON",
			  success : function(response) 
			  {
				   for(var i = 0; i<response.length; i++) {
				   res1[i] = response[i];			   
				 }
			  }
		    });
			
	    	$('a[id="temPID"]').remove();
	    	$('a[id="temPIDApprove"]').remove();
	    	$('a[id="temPIDReject"]').remove();
	    	
	    	$('[name="reqDate"]').attr("readonly", true);
	    	
		    $('.k-edit-field .k-input').first().focus();
			$('label[for=reqId]').parent().hide();
			$('div[data-container-for="reqId"]').hide();
			
			$(".k-window-title").text("Add Requisition");
			$(".k-grid-update").text("Save");				
			
			$('label[for=drGroupId]').parent().hide();
			$('div[data-container-for="drGroupId"]').hide();
			
			$('label[for=fullName]').parent().hide();
			$('div[data-container-for="fullName"]').hide();		
							
			$('label[for=pn_Name]').parent().hide();
			$('div[data-container-for="pn_Name"]').hide();
			
			$('label[for=fullVendorName]').parent().hide();
			$('div[data-container-for="fullVendorName"]').hide();				
			
			$('label[for=dept_Name]').parent().hide();
			$('div[data-container-for="dept_Name"]').hide();
			
			$('label[for=createdBy]').parent().hide();
			$('div[data-container-for="createdBy"]').hide();
			
			$('label[for=lastUpdatedBy]').parent().hide();
			$('div[data-container-for="lastUpdatedBy"]').hide();
			
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
		}
		else
		{
			securityCheckForActions("./procurement/requisition/updateButton"); 
			getRequisitionName = e.model.reqName;
			var type = e.model.reqType;
			
			if(type === 'Manpower')
			{
				$('label[for=storeId]').parent().hide();
				$('div[data-container-for="storeId"]').hide();	
			}
			if(type === 'General Contract')
			{
				$('label[for=storeId]').parent().hide();
				$('div[data-container-for="storeId"]').hide();
			}
			if(type === 'AMC')
			{
				$('label[for=storeId]').parent().hide();
				$('div[data-container-for="storeId"]').hide();
			}
			
			
			if(status === "Approved")
			{
			   $("#alertsBox").html("Alert");
			   $("#alertsBox").html("Approved Requisition Cannot Be Edited");
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
			if(status === "Automated Requisition")
			{
				$("#alertsBox").html("Alert");
				$("#alertsBox").html("Requisition cannot be editted for automated requisition");
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
			
			if(status === "PO PLaced")
			{
			   $("#alertsBox").html("Alert");
			   $("#alertsBox").html("PO PLaced...! Cannot Be Edited");
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
			
			$('a[id="temPIDApprove"]').remove();
	    	$('a[id="temPIDReject"]').remove();
			$(".k-window-title").text("Edit Requisition");
			
			$('label[for=fullName]').parent().hide();
			$('div[data-container-for="fullName"]').hide();
			
			$('label[for=reqId]').parent().hide();
			$('div[data-container-for="reqId"]').hide();
			
			$('label[for=drGroupId]').parent().hide();
			$('div[data-container-for="drGroupId"]').hide();
			
			$('label[for=pn_Name]').parent().hide();
			$('div[data-container-for="pn_Name"]').hide();
			
			$('label[for=createdBy]').parent().hide();
			$('div[data-container-for="createdBy"]').hide();
			
			$('label[for=lastUpdatedBy]').parent().hide();
			$('div[data-container-for="lastUpdatedBy"]').hide();
			
			$('label[for=fullVendorName]').parent().hide();
			$('div[data-container-for="fullVendorName"]').hide();				
			
			$('label[for=dept_Name]').parent().hide();
			$('div[data-container-for="dept_Name"]').hide();
			
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
		}
	}
    /*******  END FOR ADD REQUISITION MASTER DETAILS ******/	
    
    	function requisitionDataBound(e) {
    	
		var data = this.dataSource.view();	    
	    var grid = $("#grid").data("kendoGrid");
	    for (var i = 0; i < data.length; i++) {
	    	var currentUid = data[i].uid;
	        row = this.tbody.children("tr[data-uid='" + data[i].uid + "']");
	       
	        if(data[i].status=="Approved"){
	        	var currenRow = grid.table.find("tr[data-uid='" + currentUid+ "']");
				var reOpenButton = $(currenRow).find("#temPIDApprove");
				reOpenButton.hide();
	        }
	        
	        if(data[i].status=="Rejected"){
	        	var currenRow = grid.table.find("tr[data-uid='" + currentUid+ "']");
				var reOpenButton = $(currenRow).find("#temPIDReject");
				reOpenButton.hide();
	        }
	        
	    }
	 }
    
    /***********  FOR APPROVING THE REQUISITION STATUS  ***********/
	$("#grid").on("click", "#temPIDApprove", function(e) 
    {
		var gview = $("#grid").data("kendoGrid");
	 	var selectedItem = gview.dataItem(gview.select());
	 	var widget = $("#grid").data("kendoGrid");
		var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
	 	status = selectedItem.status;
	 		 	
	 	if(status === "Rejected")
	 	{
	 	   var result=securityCheckForActionsForStatus("./procurement/requisition/deleteButton");   
	 	   if(result=="success")
	 	   { 
	 		  var x;
		 	    if (confirm("Requisition Rejected..! Do you Want To Approve!") == true)
		 	    {
		 	    	$.ajax
		 	    	({
						type : "POST",
						dataType : "text",
						url : "./procurement/requisition/requisitionStatus/" + dataItem.id + "/Approved",
						success : function(response) 
						{
							$("#alertsBox").html("Alert");
							$("#alertsBox").html(response);
							$("#alertsBox").dialog({
								modal : true,
								buttons : {
									"Close" : function() {
										$(this).dialog("close");
									}
								}
							}); 
							$('#grid').data('kendoGrid').dataSource.read();
						}
					});
		 	    }
	 	   }
	 		
	 		 
	 	}
	 	if(status === "Approved")
	 	{
	 		$("#alertsBox").html("Alert");
			$("#alertsBox").html("Requisition Already Approved");
			$("#alertsBox").dialog({
				modal : true,
				buttons : {
					"Close" : function() {
						$(this).dialog("close");
					}
				}
			}); 
			return false;
	 	}
	 	if(status === "PO Placed")
	 	{
	 		$("#alertsBox").html("Alert");
			$("#alertsBox").html("Purchase Order Placed...!");
			$("#alertsBox").dialog({
				modal : true,
				buttons : {
					"Close" : function() {
						$(this).dialog("close");
					}
				}
			}); 
			return false;
	 	}
	 	if(status === "Created" || status === "Automated Requisition")
    	{
	 		var result=securityCheckForActionsForStatus("./procurement/requisition/deleteButton");
	 		if(result=="success")
		 	{ 
	 			$.ajax
	 	    	({
					type : "POST",
					dataType : "text",
					url : "./procurement/requisition/requisitionStatus/" + dataItem.id + "/Approved",
					success : function(response) 
					{
						if(response == 'No Requisition Details Added')
						{
							alert("No requisition Details Added ! Requisition Cannot be Approved");
							return false;
						}
						else
						{
							$.ajax
				 	    	({
								type : "POST",
								dataType : "text",
								url : "./procurement/requisition/requisitionStatus/" + dataItem.id + "/Approved",
								success : function(response) 
								{
									alert("Requisition Approved");
									$('#grid').data('kendoGrid').dataSource.read();
								}
				 	    	});	
						}
					}	 	    	
				});	
		 	}
    	}
    });
	/************  END FOR APPROVING THE REQUISITION STATUS  *************/
	
	
	/************  FOR REJECTING THE REQUISITION STATUS  *************/
	$("#grid").on("click", "#temPIDReject", function(e) 
    {
		var gview = $("#grid").data("kendoGrid");
	 	var selectedItem = gview.dataItem(gview.select());
	 	var widget = $("#grid").data("kendoGrid");
		var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
	 	status = selectedItem.status;
	 	
	 	if(status == "Approved" || status == "Created")
	 	{
	 		 var result=securityCheckForActionsForStatus("./procurement/requisition/deleteButton");
	 		if(result=="success")
		 	{ 
	 			$.ajax({
					type : "POST",
					dataType : "text",
					url : "./procurement/requisition/requisitionStatus/" + dataItem.id + "/Rejected",
					success : function(response) {
						$("#alertsBox").html("Alert");
						$("#alertsBox").html(response);
						$("#alertsBox").dialog({
							modal : true,
							buttons : {
								"Close" : function() {
									$(this).dialog("close");
								}
							}
						});
						$('#grid').data('kendoGrid').dataSource.read();
					}
				});
		 	}
	 		
	 	}
	 	if(status == "Rejected")
	 	{
	 		$("#alertsBox").html("Alert");
			$("#alertsBox").html("Requisition Already Rejected");
			$("#alertsBox").dialog({
				modal : true,
				buttons : {
					"Close" : function() {
						$(this).dialog("close");
					}
				}
			});	
	 	}
	 	if(status == "PO Placed")
	 	{
	 		$("#alertsBox").html("Alert");
			$("#alertsBox").html("Purchase Order Placed....!Cannot Be Rejected");
			$("#alertsBox").dialog({
				modal : true,
				buttons : {
					"Close" : function() {
						$(this).dialog("close");
					}
				}
			}); 
			return false;
	 	}
	});
	/************  END FOR REJECTING THE REQUISITION STATUS  *************/
	
	 /******  TEXT AREA FOR REQUISITION DESCRIPTION  ******/
	  function requisitionDescription(container, options) 
	  {
	        $('<textarea name="Requisition Description" data-text-field="reqDescription" data-value-field="reqDescription" data-bind="value:' + options.field + '" style="width: 148px; height: 46px;" required="true"/>')
	             .appendTo(container);
	    	$('<span class="k-invalid-msg" data-for="Requisition Description"></span>').appendTo(container); 
	  }
	  /******   END FOR REQUISITION DESCRIPTION *******/
	  
	 /**** FOR REQUISITION MASTER CLEAR FILTER *****/
	 $("#grid").on("click", ".k-grid-Clear_Filter", function()
	 {
	   	//custom actions
		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
		var grid = $("#grid").data("kendoGrid");
		grid.dataSource.read();
		grid.refresh();
	 });	
	/**** END FILTER *****/
	
		/*******  READ DEPARTMENTS WITH DEPARTMENT NAME & DEPARTMENT ID  ***********/
		function ReadDepartment(container, options) 
		{
			$('<input name="Department" required="true" id="dept_Id" data-text-field="dept_Name" data-value-field="dept_Id" data-bind="value:' + options.field + '"/>').appendTo(container).kendoDropDownList
			({
				optionLabel : 
				{
					dept_Name : "Select",
					dept_Id : "",
				},
			    defaultValue : false,
				sortable : true,
				//cascadeFrom: "personId",
				dataSource :{
					transport :{
						 read : "${readDepartmentForVendors}"
					}
				}
			});
			$('<span class="k-invalid-msg" data-for="Department"></span>').appendTo(container);
		}
		/**********  END OF DEPARTMENT  ******/
			
			
		/*******  READ REQUISITION USER ID  ***********/
		function RequisitionUsers(container, options) 
		{
			$('<input name="RequisitionUser" data-text-field="fullName"  data-value-field="personId" data-bind="value:' + options.field + '"/>').appendTo(container).kendoDropDownList
			({
				optionLabel : 
				{
					fullName : "Select",
					personId : "",
				},
				defaultValue : false,
				sortable : true,
				//cascadeFrom : "dept_Id",
				dataSource :{
					transport :{
						read : "${readUserStaffPersons}"
					}
				}
			});
			//$('<span class="k-invalid-msg" data-for="RequisitionUser"></span>').appendTo(container);
		}
		/**********  END OF REQUISITION USER ID  ******/
		
		/********** READ VENDOR NAMES & ID ***********/
		function readVendors(container,options)
		{				
			$('<input name="Recommended Vendor" id="vendor" data-text-field="fullVendorName" required="true" data-value-field="vendorId" data-bind="value:' + options.field + '"/>')
			.appendTo(container).kendoComboBox({
				autoBind : false,
				placeholder : "Select vendor",
				headerTemplate : '<div class="dropdown-header">'
					+ '<span class="k-widget k-header">Photo</span>'
					+ '<span class="k-widget k-header">Contact info</span>'
					+ '</div>',
				    template : '<table><tr><td rowspan=2><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.personId#'/>" width=40 height=55 alt=\"No Image to Display\" /></span></td>'
					+ '<td align="left"><span class="k-state-default"><b>#: data.fullVendorName #</b></span><br>'
					+ '<span class="k-state-default"><i>#: data.personType #</i></span></td></tr></table>',
					dataSource : {
					    transport : {		
							read :  "${vendorNamesAutoUrl}"
						}
					},
					change : function (e) {
			        if (this.value() && this.selectedIndex == -1) {                    
			                alert("Vendor doesn't exist!");
			                $("#vendor").data("kendoComboBox").value("");
			        }
				    }  
				});				
				$('<span class="k-invalid-msg" data-for="Recommended Vendor"></span>').appendTo(container);
		 }
		 /*****  END FOR READING VENDORS  ********/
    
		/***** For Vendor Quote Requisition *****/
		function VendorQuoteRequisition(container, options)
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

			$('<input name="Quote" required="true"/>').attr('data-bind', 'value:reqVendorQuoteRequisition').appendTo(container).kendoDropDownList
			({
				dataSource : booleanData,
				dataTextField : 'text',
				dataValueField : 'value',
			});
			$('<span class="k-invalid-msg" data-for="Quote"></span>').appendTo(container);
		}
		/***** END FOR VENDOR QUOTE REQUISITIONS  ********/
		
		/*****************  FOR REQUISITION MASTER ONREQUESTEND FUNCTION  ********************/ 
		function onRequestEnd(e) 
	    {
		 	if (typeof e.response != 'undefined')
			{
			   if (e.response.status == "FAIL")
			   {
				   	 errorInfo = "";			
					 errorInfo = e.response.result.invalid;			
					 for (var i = 0; i < e.response.result.length; i++) 
					 {
					 	errorInfo += (i + 1) + ". "	+ e.response.result[i].defaultMessage + "\n";
					 }
					if (e.type == "create") 
					{
						alert("Error: Creating the Requisitions \n\n" + errorInfo);
					}
					if (e.type == "update") 
					{
						alert("Error: Updating the Requisitions \n\n" + errorInfo);
					}
					
					var grid = $("#grid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
					return false;
		       }
			   if(e.response.status == "ApprovedRequisitionDestroyError")
			   {
				   $("#alertsBox").html("Alert");
				   $("#alertsBox").html("Approved Requisition Cannot Be Deleted");
				   $("#alertsBox").dialog
				   ({
					   modal : true,
					   draggable: false,
	   				   resizable: false,
					   buttons : {
						"Close" : function(){
							$(this).dialog("close");
						 }
					   }
					});				   
					var grid = $("#grid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
					return false;
			    }
			   if(e.response.status == "CreatedRequisitionDestroyError")
			   {
				   $("#alertsBox").html("Alert");
				   $("#alertsBox").html("Created Requisition Cannot Be Deleted");
				   $("#alertsBox").dialog
				   ({
					   modal : true,
					   draggable: false,
	   				   resizable: false,
					   buttons : {
						"Close" : function(){
							$(this).dialog("close");
						 }
					   }
					});				   
					var grid = $("#grid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
					return false;
			    }
			   if(e.response.status == "POPlacedRequisitionDestroyError")
			   {
				   $("#alertsBox").html("Alert");
				   $("#alertsBox").html("Purchase Order Placed...!Requisition Cannot Be Deleted");
				   $("#alertsBox").dialog
				   ({
					   modal : true,
					   draggable: false,
	   				   resizable: false,
					   buttons : {
						"Close" : function(){
							$(this).dialog("close");
						 }
					   }
					});				   
					var grid = $("#grid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
					return false;  
			   }
			}
			if (e.type == "update" && !e.response.Errors) 
			{
				$("#alertsBox").html("Alert");
				$("#alertsBox").html("Requisition Updated successfully");
				$("#alertsBox").dialog
				({
				   modal : true,
				   draggable: false,
   				   resizable: false,
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
				$("#alertsBox").html("Requisition Added successfully");
				$("#alertsBox").dialog
				({
				   modal : true,
				   draggable: false,
   				   resizable: false,
				   buttons : {
					"Close" : function(){
						$(this).dialog("close");
					 }
				   }
				});	
				e.sender.read();
			}
			if(e.type == "destroy" && !e.response.Errors)
			{
				$("#alertsBox").html("Alert");
				$("#alertsBox").html("Requisition Deleted Successfully");
				$("#alertsBox").dialog
				({
				   modal : true,
				   draggable: false,
   				   resizable: false,
				   buttons : {
					"Close" : function(){
						$(this).dialog("close");
					 }
				   }
				});	
				var grid = $("#grid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			}
	    }	
		function onRequsitionStart(e)
		{
			$('.k-grid-update').hide();
	        $('.k-edit-buttons')
	                .append(
	                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
	        $('.k-grid-cancel').hide();	
	        
		     /* var grid= $("#grid").data("kendoGrid");
		     grid.cancelRow(); */
		}
		/*******  END FOR ONREQUESTEND FUNCTION MASTER REQUISITION  ************/
    
    
	</script>	
	<!---------------------------------------------------@@@ END FOR REQUISITION SCRPIT @@@----------------------------------------------------->
	
	
	
	<!--------------------------------------------------@@@ FOR REQUISITION_DETAILS GRID SCRIPT @@@---------------------------------------------->
	<script>
	var globalReqType = "";
	function onReqDetailsDataBound(e)
	{
		var data = this.dataSource.view(),row;
	    for (var i = 0; i < data.length; i++) 
	    {
	        row = this.tbody.children("tr[data-uid='" + data[i].uid + "']");	        
	        globalReqType = data[i].reqType;
	    }
	}
	
		function deleteReqDetailsEvent(e)
		{
			securityCheckForActions("./procurement/requisitionDetails/deleteButton");
		}
	
		/********  FOR ADDING CHILD REQUISITION_DETAILS  *********/
		function requisitionDetailsEvent(e)
		{
			$("input[name='rdQuantity']").attr('maxlength', '9');
			$("input[name='uomBudget']").attr('maxlength', '9');
			
			//Initially hiding the fields
			$('a[id="temPID"]').remove();
			$('label[for=rdId]').parent().hide();
			$('div[data-container-for="rdId"]').hide();
			
			$('label[for=reqId]').parent().hide();
			$('div[data-container-for="reqId"]').hide();
						
			$('label[for=rdSlno]').parent().remove();
			$('div[data-container-for="rdSlno"]').remove();
			
			$('label[for=rdQuantityType]').parent().hide();
			$('div[data-container-for="rdQuantityType"]').hide();
				
			$(".k-window-title").text("Add Requisition");
			$(".k-grid-update").text("Save");				
				
			$('label[for=drGroupId]').parent().hide();
			$('div[data-container-for="drGroupId"]').hide();
						
			$('label[for=createdBy]').parent().hide();
			$('div[data-container-for="createdBy"]').hide();
			
			$('label[for=reqFulfilled]').parent().hide();
			$('div[data-container-for="reqFulfilled"]').hide();			
						
			$('label[for=lastUpdatedBy]').parent().hide();
			$('div[data-container-for="lastUpdatedBy"]').hide();			
			
			if (e.model.isNew())
			{
				securityCheckForActions("./procurement/requisitionDetails/createButton");
				
				if(status === "Approved")
				{
				   $("#alertsBox").html("Alert");
				   $("#alertsBox").html("Requisition Approved...! Details Cannot Be Added");
				   $("#alertsBox").dialog
				   ({
					   modal : true,
					   buttons : {
						 "Close" : function(){
						    $(this).dialog("close");
					      }
					   }
				   });	
				   var grid = $("#reqDetailsGrid"+id).data("kendoGrid");
				   grid.cancelRow();
				   return false;
				}
				if(status === "PO Placed")
				{
				   $("#alertsBox").html("Alert");
				   $("#alertsBox").html("Purchase order placed...! Details Cannot Be Added");
				   $("#alertsBox").dialog
				   ({
					   modal : true,
					   buttons : {
						 "Close" : function(){
						    $(this).dialog("close");
					      }
					   }
				   });	
				   var grid = $("#reqDetailsGrid"+id).data("kendoGrid");
				   grid.cancelRow();
				   return false;
				}
				//If no child is found for Requisition(for new record entry)
				if(requisitionType == "")
				{
					$('label[for=imId]').parent().hide();
					$('div[data-container-for="imId"]').hide(); 
					
					$('label[for=dummyManpower]').parent().hide();
					$('div[data-container-for="dummyManpower"]').hide(); 
					
					$('label[for=uomId]').parent().hide();
					$('div[data-container-for="uomId"]').hide();	
					
					$('label[for=imName]').parent().hide();
					$('div[data-container-for="imName"]').hide();
					
					$('label[for=uom]').parent().hide();
					$('div[data-container-for="uom"]').hide();
					
					$('label[for=dn_Id]').parent().hide();
					$('div[data-container-for="dn_Id"]').hide();
					
					$('label[for=dn_Name]').parent().hide();
					$('div[data-container-for="dn_Name"]').hide();
				}
				
				//If 2nd entry record for Requisition(for 2nd record entry)
				else
				{				    
					$('label[for=imId]').parent().hide();
					$('div[data-container-for="imId"]').hide(); 
					
					$('label[for=dummyManpower]').parent().hide();
					$('div[data-container-for="dummyManpower"]').hide(); 
					
					$('label[for=uomId]').parent().hide();
					$('div[data-container-for="uomId"]').hide();	
					
					$('label[for=imName]').parent().hide();
					$('div[data-container-for="imName"]').hide();
					
					$('label[for=uom]').parent().hide();
					$('div[data-container-for="uom"]').hide();
					
					var reqGrid = $("#reqDetailsGrid"+id).data("kendoGrid");
					var reqDetailsGridData = reqGrid.dataSource.data();
					for (var i = 0; i < reqDetailsGridData.length; i++)
					{
						if (reqDetailsGridData[i].reqType == globalReqType) 
						{
							if(requisitionType === 'Item Supply')
						    {
						        $('label[for=rdQuantityType]').parent().show();
								$('div[data-container-for="rdQuantityType"]').show();
								
								$('label[for=imId]').parent().show();
								$('div[data-container-for="imId"]').show();
								
								$('label[for=uomId]').parent().show();
								$('div[data-container-for="uomId"]').show();
								
								$('label[for=dummyManpower]').text("UOM").parent().hide();
								$('div[data-container-for="dummyManpower"]').hide();	
								
								$('label[for=dn_Id]').parent().hide();
								$('div[data-container-for="dn_Id"]').hide();
								
								$('label[for=dn_Name]').parent().hide();
								$('div[data-container-for="dn_Name"]').hide();
						    }
							if(requisitionType == 'Manpower')
						    {				
						      	$('label[for=rdQuantityType]').parent().hide();
								$('div[data-container-for="rdQuantityType"]').hide();
								
								$('label[for=imId]').parent().hide();
								$('div[data-container-for="imId"]').hide();
								
								$('label[for=imName]').parent().hide();
								$('div[data-container-for="imName"]').hide();
								
								$('label[for=uomId]').parent().hide();
								$('div[data-container-for="uomId"]').hide();
									
								$('label[for=dummyManpower]').text("UOM").parent().show();
								$('div[data-container-for="dummyManpower"]').show();	
								
								$('label[for=dn_Id]').parent().show();
								$('div[data-container-for="dn_Id"]').show();
								
								$('label[for=dn_Name]').parent().hide();
								$('div[data-container-for="dn_Name"]').hide();
								
						    } 
						    if(requisitionType === 'General Contract')
						    {
						      	$('label[for=rdQuantityType]').parent().hide();
								$('div[data-container-for="rdQuantityType"]').hide();
								
								$('label[for=imId]').parent().hide();
								$('div[data-container-for="imId"]').hide();
								
								$('label[for=uomId]').parent().hide();
								$('div[data-container-for="uomId"]').hide();
								
								$('label[for=imName]').parent().hide();
								$('div[data-container-for="imName"]').hide();
									
								$('label[for=dummyManpower]').text("UOM").parent().hide();
								$('div[data-container-for="dummyManpower"]').hide();
								
								$('label[for=dn_Id]').parent().hide();
								$('div[data-container-for="dn_Id"]').hide();
								
								$('label[for=dn_Name]').parent().hide();
								$('div[data-container-for="dn_Name"]').hide();
						    }
						    if(requisitionType === 'AMC')
						    {
						      	$('label[for=rdQuantityType]').parent().hide();
								$('div[data-container-for="rdQuantityType"]').hide();
								
								$('label[for=imId]').parent().hide();
								$('div[data-container-for="imId"]').hide();
								
								$('label[for=uomId]').parent().hide();
								$('div[data-container-for="uomId"]').hide();
								
								$('label[for=imName]').parent().hide();
								$('div[data-container-for="imName"]').hide();
									
								$('label[for=dummyManpower]').text("UOM").parent().hide();
								$('div[data-container-for="dummyManpower"]').hide();
								
								$('label[for=dn_Id]').parent().hide();
								$('div[data-container-for="dn_Id"]').hide();
								
								$('label[for=dn_Name]').parent().hide();
								$('div[data-container-for="dn_Name"]').hide();
						    }
						    
							/* var dropdownlist = $("#reqTypeVal").data("kendoDropDownList");
							dropdownlist.search(globalReqType);
							dropdownlist.readonly();
							var rtype =$("#reqDetailsGrid"+id).data().kendoGrid.dataSource.data()[0];
							rtype.set('reqType', globalReqType); */
						} 
					}					
				}
				$(".k-grid-update").click(function () 
			    {
					if(requisitionType === 'Item Supply')
					{
						var imid = $('#imId').val();
						var uomid = $('#uomId').val();
						if(imid == "")
					    {
							alert("Select Item");
							return false;
					    }
						if(uomid == "")
						{
							alert("Select UOM");
							return false;
						}
					}
					if(requisitionType === 'Manpower')
					{
						var manpower = $("#dummyManpower").val();
						var designation = $("#designationEditor").val();
						if(designation == "")
						{
							alert("Select Designation");
							return false;
						}
						if(manpower == "")
						{
							alert("Select UOM");
							return false;
						}						
					}
			    });
			}
			else
			{	
				securityCheckForActions("./procurement/requisitionDetails/updateButton");
				
				$('label[for=imName]').parent().hide();
				$('div[data-container-for="imName"]').hide();
				
				$('label[for=uom]').parent().hide();
				$('div[data-container-for="uom"]').hide();
				
				var reqType = e.model.reqType;
				if(status === "Approved")
				{
				   $("#alertsBox").html("Alert");
				   $("#alertsBox").html("Requisition Approved...! Details Cannot Be Edited");
				   $("#alertsBox").dialog
				   ({
					   modal : true,
					   buttons : {
						 "Close" : function(){
						    $(this).dialog("close");
					      }
					   }
				   });	
				   var grid = $("#reqDetailsGrid"+id).data("kendoGrid");
				   grid.cancelRow();
				   return false;
				}
				if(status === "PO Placed")
				{
				   $("#alertsBox").html("Alert");
				   $("#alertsBox").html("Purchase order placed...! Details Cannot Be Edited");
				   $("#alertsBox").dialog
				   ({
					   modal : true,
					   buttons : {
						 "Close" : function(){
						    $(this).dialog("close");
					      }
					   }
				   });	
				   var grid = $("#reqDetailsGrid"+id).data("kendoGrid");
				   grid.cancelRow();
				   return false;
				}
				
				if(requisitionType === 'Item Supply')
			    {
			        $('label[for=rdQuantityType]').parent().show();
					$('div[data-container-for="rdQuantityType"]').show();
					
					$('label[for=imId]').parent().show();
					$('div[data-container-for="imId"]').show();
					
					$('label[for=uomId]').parent().show();
					$('div[data-container-for="uomId"]').show();
					
					$('label[for=dummyManpower]').text("UOM").parent().hide();
					$('div[data-container-for="dummyManpower"]').hide();
					
					$('label[for=dn_Id]').parent().hide();
					$('div[data-container-for="dn_Id"]').hide();
					
					$('label[for=dn_Name]').parent().hide();
					$('div[data-container-for="dn_Name"]').hide();
			    }
			    if(requisitionType == 'Manpower')
			    {
			      	$('label[for=rdQuantityType]').parent().hide();
					$('div[data-container-for="rdQuantityType"]').hide();
					
					$('label[for=imId]').parent().hide();
					$('div[data-container-for="imId"]').hide();
					
					$('label[for=imName]').parent().hide();
					$('div[data-container-for="imName"]').hide();
					
					$('label[for=uomId]').parent().hide();
					$('div[data-container-for="uomId"]').hide();
						
					$('label[for=dummyManpower]').text("UOM").parent().show();
					$('div[data-container-for="dummyManpower"]').show();	
					
					$('label[for=dn_Id]').parent().show();
					$('div[data-container-for="dn_Id"]').show();
					
					$('label[for=dn_Name]').parent().hide();
					$('div[data-container-for="dn_Name"]').hide();
			    } 
			    if(requisitionType === 'General Contract')
			    {
			      	$('label[for=rdQuantityType]').parent().hide();
					$('div[data-container-for="rdQuantityType"]').hide();
					
					$('label[for=imId]').parent().hide();
					$('div[data-container-for="imId"]').hide();
					
					$('label[for=uomId]').parent().hide();
					$('div[data-container-for="uomId"]').hide();
					
					$('label[for=imName]').parent().hide();
					$('div[data-container-for="imName"]').hide();
						
					$('label[for=dummyManpower]').text("UOM").parent().hide();
					$('div[data-container-for="dummyManpower"]').hide(); 
					
					$('label[for=dn_Id]').parent().hide();
					$('div[data-container-for="dn_Id"]').hide();
					
					$('label[for=dn_Name]').parent().hide();
					$('div[data-container-for="dn_Name"]').hide();
			    }
			    if(requisitionType === 'AMC')
			    {
			      	$('label[for=rdQuantityType]').parent().hide();
					$('div[data-container-for="rdQuantityType"]').hide();
					
					$('label[for=imId]').parent().hide();
					$('div[data-container-for="imId"]').hide();
					
					$('label[for=uomId]').parent().hide();
					$('div[data-container-for="uomId"]').hide();
					
					$('label[for=imName]').parent().hide();
					$('div[data-container-for="imName"]').hide();
						
					$('label[for=dummyManpower]').text("UOM").parent().hide();
					$('div[data-container-for="dummyManpower"]').hide(); 
					
					$('label[for=dn_Id]').parent().hide();
					$('div[data-container-for="dn_Id"]').hide();
					
					$('label[for=dn_Name]').parent().hide();
					$('div[data-container-for="dn_Name"]').hide();
			    }
				if(status === "Approved")
				{
				   $("#alertsBox").html("Alert");
				   $("#alertsBox").html("Requisition Approved !Cannot Be Edited");
				   $("#alertsBox").dialog
				   ({
					   modal : true,
					   buttons : {
						 "Close" : function(){
						    $(this).dialog("close");
					      }
					   }
				   });	
				   var grid = $("#reqDetailsGrid"+id).data("kendoGrid");
				   grid.cancelRow();
				   return false;
				}	
				if(status === "PO PLaced")
				{
				   $("#alertsBox").html("Alert");
				   $("#alertsBox").html("Purchase Order Placed.....! Requisition Cannot Be Edited");
				   $("#alertsBox").dialog
				   ({
					   modal : true,
					   buttons : {
						 "Close" : function(){
						    $(this).dialog("close");
					      }
					   }
				   });	
				   var grid = $("#reqDetailsGrid"+id).data("kendoGrid");
				   grid.cancelRow();
				   return false;
				}
			}
		}
		/********  END FOR ADDING CHILD REQUISITION_DETAILS  *********/
	
		/****** For Requisition Types *****/
		function RequisitionTypes(container, options)
		{
			var booleanData = [ {
			text : 'Select',
			value : ""
		    },{
				text : 'Item Supply',
				value : "Item Supply"
			},{
				text : 'Manpower',
				value : "Manpower"
			},{
				text : 'General Contract',
				value : "General Contract"
			},{
				text : 'AMC',
				value : "AMC"
			},];
			$('<select name="Requisition Type" required="true" class="requisitionType"/>').attr('data-bind','value:reqType').appendTo(container).kendoDropDownList({
				dataSource : booleanData,
				dataTextField : 'text',
				dataValueField : 'value',
			}); 
			$('<span class="k-invalid-msg" data-for="Requisition Type"></span>').appendTo(container);
		}
		/***  END FOR REQUISITION TYPES *****/
		
		/** OnSelect Event For requisition type **/
		var typeSelected = "";
		function onTypeSelect(e)
		{
			var dataItem = this.dataItem(e.item.index());
	        typeSelected = dataItem.value;
	        if(typeSelected === 'Item Supply')
		    {
		        $('label[for=rdQuantityType]').parent().show();
				$('div[data-container-for="rdQuantityType"]').show();
				
				$('label[for=imId]').parent().show();
				$('div[data-container-for="imId"]').show();
				
				$('label[for=uomId]').parent().show();
				$('div[data-container-for="uomId"]').show();
				
				$('label[for=dummyManpower]').text("UOM").parent().hide();
				$('div[data-container-for="dummyManpower"]').hide();
		    }
		    if(typeSelected === 'Manpower')
		    {
		      	$('label[for=rdQuantityType]').parent().hide();
				$('div[data-container-for="rdQuantityType"]').hide();
				
				$('label[for=imId]').parent().hide();
				$('div[data-container-for="imId"]').hide();
				
				$('label[for=imName]').parent().hide();
				$('div[data-container-for="imName"]').hide();
				
				$('label[for=uomId]').parent().hide();
				$('div[data-container-for="uomId"]').hide();
					
				$('label[for=dummyManpower]').text("UOM").parent().show();
				$('div[data-container-for="dummyManpower"]').show();			
		    } 
		    if(typeSelected === 'General Contract')
		    {
		      	$('label[for=rdQuantityType]').parent().hide();
				$('div[data-container-for="rdQuantityType"]').hide();
				
				$('label[for=imId]').parent().hide();
				$('div[data-container-for="imId"]').hide();
				
				$('label[for=imName]').parent().hide();
				$('div[data-container-for="imName"]').hide();
					
				$('label[for=dummyManpower]').text("UOM").parent().hide();
				$('div[data-container-for="dummyManpower"]').hide(); 
		    }	        
		} 
		/** End For Requisition Type Event **/
				
		 /******** Manpower Editor function **********/
		function manPowerDropdownEditor(container, options)
		{
			var booleanData = [{
			text : 'Select',
			value : "Select"
			},{
			  text : 'Per Hour',
			  value : 1
			},{
			  text : 'Per Day',
			  value : 2
			  },{
			  text : 'Per Month',
			  value : 3
			},];
			$('<input name="dummyManpower" data-text-field="text" id="dummyManpower"  data-value-field="value" data-bind="value:' + options.field + '"/>')
				.appendTo(container).kendoDropDownList
				({
						dataTextField : "text",
						dataValueField : "value",
						optionLabel : {
							text :  "Select",
							value : "",
						},
						defaultValue : false,
						sortable : true,
						dataSource : booleanData,
						select: onManpowerSelect
				});
		}
		/*** END FOR MANPOWER REQUISITION TYPES  ****/
		
		/** For Manpower Onselect **/
		function onManpowerSelect(e)
		{
			var dataItem = this.dataItem(e.item.index());
	        var selectedManpower = dataItem.value;
	        var firstItem = $('#reqDetailsGrid'+id).data().kendoGrid.dataSource.data()[0];
			firstItem.set('uomId', selectedManpower);
			firstItem.set('imId', 1);
		} 
		/** End for Manpower Onselect **/
		
		/******  FOR READING ITEM_MASTER DETAILS  ******/
		function ItemMasterIdValue(container, options) 
		{
			$('<input name="Items" id="imId" data-text-field="imName" data-value-field="imId" data-bind="value:' + options.field + '"/>')
			.appendTo(container).kendoComboBox({
				autoBind : false,
				placeholder : "Select Item",
				headerTemplate : '<div class="dropdown-header">'
					+ '<span class="k-widget k-header">Photo</span>'
					+ '<span class="k-widget k-header">Contact info</span>'
					+ '</div>',
				template : '<table><tr><td rowspan=2><span class="k-state-default"></span></td>'
					+ '<td align="left"><span class="k-state-default"><b>#: data.imNameType #</b></span><br>'
					+ '</td></tr></table>',
				dataSource : {
					transport : {		
						read :  "${readItemMasterId}"
					}
				},
				change : function (e) {
		            if (this.value() && this.selectedIndex == -1) {                    
		                alert("Item doesn't exist!");
		                $("#reqDetailsGrid"+id).data("kendoComboBox").value("");
		        	}
			    } 
			});			
			$('<span class="k-invalid-msg" data-for="Item"></span>').appendTo(container);				
		}
		/******  END FOR READING ITEM_MASTER DETAILS  ******/
	
	
		/**** FOR UOM EDITOR ****/
		function UomEditorFunction(container, options)
		{
			$('<input name="Uom" id="uomId" data-text-field="uom" data-value-field="uomId" data-bind="value:' + options.field + '"/>')
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
		
		/**** FOR DESIGNATION EDITOR  ****/
		function  designationEditorFunction(container, options)
	    { 
		   $('<input name="Designation" id="designationEditor" data-text-field="dn_Name"  data-value-field="dn_Id" data-bind="value:' + options.field + '"/>').appendTo(container).kendoDropDownList
		   ({
			  placeholder : "Select Designation",
			  optionLabel : 
		 	  {
				 dn_Name : "Select",
				 dn_Id : "",
			  }, 
			  defaultValue : false,
			  sortable : true,
			  dataSource :{
				transport :{
					read : "${readDesignationBasedOnDepartment}/"+deptId,
				}
			}
		  });
	    }
		/**** END FOR DESIGNATION EDITOR  ****/
		
		/********  FOR ONREQUEST END FUNCTION FOR REQUISITION_DETAILS  ******/
		function onRequestEndReqDetails(e) 
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
						alert("Error: Creating the Requisition Details\n\n" + errorInfo);
					}
					if (e.type == "update") 
					{
						alert("Error: Updating the Requisition Details\n\n" + errorInfo);
					}
					var grid = $("#grid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
					return false;deletingRequisitionDetailsError
		       }
			   if(e.response.status == "deletingRequisitionDetailsError")
			   {
				    $("#alertsBox").html("Alert");
					$("#alertsBox").html("Requisition Already Approved ! Cannot Be Deleted");
					$("#alertsBox").dialog
					({
					   modal : true,
					   draggable: false,
	   				   resizable: false,
					   buttons : {
						"Close" : function(){
							$(this).dialog("close");
						 }
					   }
					});				   
					var grid = $("#grid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
					return false;
				}
			   if(e.response.status == "deletingPOPLacedRequisitionDetailsError")
			   {
				    $("#alertsBox").html("Alert");
					$("#alertsBox").html("Purchase Order Placed....! Requisition Details Cannot Be Deleted");
					$("#alertsBox").dialog
					({
					   modal : true,
					   draggable: false,
	   				   resizable: false,
					   buttons : {
						"Close" : function(){
							$(this).dialog("close");
						 }
					   }
					});				   
					var grid = $("#grid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
					return false;
				}
			}
		
		if (e.type == "update" && !e.response.Errors) 
		{
			$("#alertsBox").html("Alert");
			$("#alertsBox").html("Requisition Details Updated Successfully");
			$("#alertsBox").dialog
			({
			   modal : true,
			   draggable: false,
			   resizable: false,
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
			$("#alertsBox").html("Requisition Details Added Successfully");
			$("#alertsBox").dialog
			({
			   modal : true,
			   draggable: false,
			   resizable: false,
			   buttons : {
				"Close" : function(){
					$(this).dialog("close");
				 }
			   }
			});
			e.sender.read();
		}
		if(e.type == "destroy" && !e.response.Errors)
		{
			$("#alertsBox").html("Alert");
			$("#alertsBox").html("Requisition Detail Deleted Successfully");
			$("#alertsBox").dialog
			({
			   modal : true,
			   draggable: false,
			   resizable: false,
			   buttons : {
				"Close" : function(){
					$(this).dialog("close");
				 }
			   }
			});
			var grid = $("#grid").data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
		}
	  }
	  function onRequestStartReqDetails(e)
	  {
		 	$('.k-grid-update').hide();
	        $('.k-edit-buttons')
	                .append(
	                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
	        $('.k-grid-cancel').hide();
	        
		  /* var grid = $("#reqDetailsGrid"+id).data("kendoGrid");
		   grid.cancelRow(); */
	  }
	  /********  END FOR ONREQUEST END FUNCTION FOR REQUISITION_DETAILS  ******/
		
	</script>
	<!-------------------------------------------------@@@  END FOR REQUISITION_DETAILS GRID SCRIPT @@@------------------------------------------>
	
	<script>
		
		/****   FOR REQUISTION DESCRIPTION TEXTAREA  ****/
		function requisitionDescriptionEditor(container, options) 
	    {
	        $('<textarea name="Description" data-text-field="rdDescription" data-value-field="rdDescription" data-bind="value:' + options.field + '" style="width: 148px; height: 46px;" required="true"/>')
	             .appendTo(container);
	    	$('<span class="k-invalid-msg" data-for="Description"></span>').appendTo(container); 
	    }
		/****  END FOR REQUISITION DESCRIPTION TEXTAREA  ****/
		
        
        var res = [];              
    	(function($, kendo) {
    		$
    				.extend(
    						true,
    						kendo.ui.validator,
    						{
    							rules :
    							{ // custom rules          	
    								ReqDepartmentValidation : function(input,params) 
    								{
    									//check for the name attribute 
    									if (input.filter("[name='reqDepartment']").length&& input.val()) 
    									{
    										return /^[a-zA-Z]+[._a-zA-Z0-9]*[a-zA-Z0-9]$/.test(input.val());
    									}
    									 return true;
    								},
    							    RecommendedVendorIdValidation : function(input,params) 
									{
								       //check for the name attribute 
								        if (input.filter("[name='recommendedVendorId']").length&& input.val()) 
								        {
								            return /^[a-zA-Z]+[._a-zA-Z0-9]*[a-zA-Z0-9]$/.test(input.val());
								        }
								        return true;
							        }, 							       
							        rdDescription: function (input, params)
							        {
					                     if (input.attr("name") == "rdDescription") {
					                      return $.trim(input.val()) !== "";
					                     }
					                     return true;
					                },
					                rdQuantityType: function (input, params)
							        {
					                     if (input.attr("name") == "rdQuantityType") {
					                      return $.trim(input.val()) !== "";
					                     }
					                     return true;
					                },
					                rdQuantity: function (input, params)
								    {
						                if (input.attr("name") == "rdQuantity") {
						                   return $.trim(input.val()) !== "";
						                }
						                return true;
						            },
						            uomBudgetVal: function (input, params)
								    {
						                if (input.attr("name") == "uomBudget") {
						                   return $.trim(input.val()) !== "";
						                }
						                return true;
						            },
						            reqNameValidator: function (input, params)
								    {
						            	if (input.attr("name") == "reqName") {
							               return $.trim(input.val()) !== "";
							            }
							            return true;
						            },
						            itemMasterValidator : function(input) {
										if ((input.filter("[name='imId']")) && (input.val() == "Select")) {
											
											return false;
										}
										return true;
									},
									reqDateValidator : function (input, params)
								    {
						                if (input.attr("name") == "reqDate") {
						                   return $.trim(input.val()) !== "";
						                }
						                return true;
						            },						            
						            reqByDateValidator : function (input, params)
								    {
						                if (input.attr("name") == "reqByDate") {
						                   return $.trim(input.val()) !== "";
						                }
						                return true;
						            },
						            reqByDateValidation : function(input,params) {
										if (input
												.filter("[name = 'reqByDate']").length && input.val()) {
											var selectedDate = input.val();
											var todaysDate=$('input[name="reqDate"]').val();
											/* var todaysDate = $.datepicker.formatDate('dd/mm/yy',new Date()); */
											var flagDate = false;

											if ($.datepicker.parseDate('dd/mm/yy',selectedDate) >= $.datepicker.parseDate('dd/mm/yy',todaysDate)) {
												flagDate = true;
											}
											return flagDate;
										}
										return true;
									},
									reqNameUniqueness : function(input,params) 
									{
								        //check for the name attribute 
								        if (input.filter("[name='reqName']").length && input.val()) 
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
    							   },
    							messages : { 
    								ReqDepartmentValidation: "Requisition Department Field can not allow special symbols & numbers",
   									RecommendedVendorIdValidation: "Recommended VendorId Field can not allow special symbols & numbers",
   									rdQuantity : "Requisition Quantity Required",
   								    rdDescription : "Requisition Description Required",
   								    rdQuantityType : "Requisition Quantity Type Required ",
   								    itemMasterValidator : "Required",
   								    reqNameValidator : "Requisition Name Required",
   								 	reqDateValidator : "Requisition Date Required",
   								 	reqByDateValidator : "Requisition By-Date Required",  
   								    reqByDateValidation : "Cannot be less than Requisition date",
   								    uomBudgetVal : "Budget UOM Required",
   								    reqNameUniqueness : "Requisition Name Exists",
    							}
    						});
    	})(jQuery, kendo);
		
	</script>
