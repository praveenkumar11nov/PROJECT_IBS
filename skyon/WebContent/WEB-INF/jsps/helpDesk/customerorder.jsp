<%@include file="/common/taglibs.jsp"%>
<c:url value="/customerorder/createitemmaster" var="createUrl" />
<c:url value="/customerorder/readitemmaster" var="readUrl" />


<c:url value="/customerpreorder/read" var="readpreUrl" />
<c:url value="/customerpreorder/destroy" var="destroypreUrl" />


<c:url value="/customerorder/updateitemmaster" var="updateUrl" />

<c:url value="/customeritems/createitemmaster" var="CustomerCreateUrl" />
<c:url value="/customeritems/readitemmaster" var="CustomerReadUrl" />
<c:url value="/customer/upload/itemImage" var="customerImage" />

<kendo:grid name="customerGrid" pageable="true" resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true" detailTemplate="customerChild" edit="CustomerGridEvent" change="onChangeProcess">
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true">
		<kendo:grid-pageable-messages itemsPerPage="Notification per page" empty="No Notification to display" refresh="Refresh all the Notification" 
			display="{0} - {1} of {2} Services" first="Go to the first page of Notification" last="Go to the last page of Notification" next="Go to the Notification"
			previous="Go to the previous page of Notification"/>
					</kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
		 <kendo:grid-filterable-operators>
		  	<kendo:grid-filterable-operators-string eq="Is equal to" />
		 </kendo:grid-filterable-operators>
			
		</kendo:grid-filterable>
		  <kendo:grid-editable mode="popup"
			confirmation="Are you sure you want to remove this Notification?" />
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Notification" />
		</kendo:grid-toolbar>
		<kendo:grid-toolbarTemplate>
			<div class="toolbar">
			<a class="k-button k-button-icontext k-grid-add" href="#">
		            <span class="k-icon k-add"></span>
		            Add Customer
	        	</a>
			 <a class='k-button' href='\\#' onclick="clearFilter()"><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>  
			</div>  	
    	</kendo:grid-toolbarTemplate>
    	<kendo:grid-columns>
    	<kendo:grid-column title="Cust Name*" field="custName"  width="120px" >
		</kendo:grid-column>
		
		<kendo:grid-column title="Customer Number*" field="custNum"  width="120px" >
		</kendo:grid-column>
			<kendo:grid-column title="Customer Email*" field="custEmail"  width="120px" >
		</kendo:grid-column>
		
		<kendo:grid-column title="Total Quantity*" field="totalQuantity"  width="120px" >
		</kendo:grid-column>
		
		<kendo:grid-column title="Total Price*" field="totalPrice"  width="120px" >
		</kendo:grid-column>
			
		<kendo:grid-column title="Ordeered Date*" field="lastUpdatedDt"  width="120px" >
		</kendo:grid-column>
			<kendo:grid-column title="PersonId*" field="personId"  width="120px" >
		</kendo:grid-column>
	
		
		<kendo:grid-column title="Image" field="customerImage"
				template="<span onclick='clickToUploadImage()' title='Click to Upload Image' ><img src='./customer/getCustomerimage/#=cid#' id='myImages_#=cid#' alt='No Image to Display' width='80px' height='80px'/></span>"
				filterable="false" width="94px" sortable="false" />
		
		
		 <kendo:grid-column title="Customer&nbspStatus&nbsp;*" field="customerStatus"  filterable="true" width="130px" hidden="true"/>
			<kendo:grid-column  title=""
    template="<a href='\\\#' id='btn1_#=data.cid#' class='k-button k-button-icontext btn-destroy k-grid-Activate#=data.cid#' onClick=statusBtn(this.text)>Order Open</a>&nbsp;<a href='\\\#' id='btn2_#=data.cid#' class='k-button k-btn2' onClick=statusBtn(this.text)>Order Close</a>"
    width="160px" />
    
		<kendo:grid-column title="&nbsp;" width="220px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit" />
					<kendo:grid-column-commandItem name="destroy" />
				</kendo:grid-column-command>
				</kendo:grid-column>
				</kendo:grid-columns>
				
				<kendo:dataSource pageSize="5"  requestEnd="onCustomerRequestEnd" requestStart="onRequestStart" >
			<kendo:dataSource-transport>
<kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json"/>
			<kendo:dataSource-transport-create url="${createUrl}" dataType="json" type="GET" contentType="application/json"/>
 <kendo:dataSource-transport-update url="${updateUrl}" dataType="json" type="GET" contentType="application/json" />
		<%--	<kendo:dataSource-transport-destroy url="${destroyUrl}" dataType="json" type="GET" contentType="application/json"/>   --%>
				
			</kendo:dataSource-transport>
			
			 <kendo:dataSource-schema parse="parse">
				<kendo:dataSource-schema-model id="cid">
					<kendo:dataSource-schema-model-fields>
					<kendo:dataSource-schema-model-field name="cid" type="number" />
						<kendo:dataSource-schema-model-field name="custName" type="String"/>
							<kendo:dataSource-schema-model-field name="custNum" type="String"/>
								<kendo:dataSource-schema-model-field name="custEmail" type="String"/>
								
							
						<kendo:dataSource-schema-model-field name="totalQuantity" type="number"/>
							<kendo:dataSource-schema-model-field name="totalPrice" type="number"/>
						 <kendo:dataSource-schema-model-field name="customerStatus" type="string" defaultValue="Accept"/>
															<kendo:dataSource-schema-model-field name="storeName"  />
																<kendo:dataSource-schema-model-field name="lastUpdatedDt"  />
																<kendo:dataSource-schema-model-field name="personId" type="number" />
															
						</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
			</kendo:dataSource>				
						</kendo:grid>
						
						<kendo:grid-detailTemplate id="customerChild">
							<kendo:tabStrip name="tabStrip_#=cid#">
								<kendo:tabStrip-animation></kendo:tabStrip-animation>
		<kendo:tabStrip-items>
		<kendo:tabStrip-item text="Customer" selected="true" >
					<kendo:tabStrip-item-content>
		
		<kendo:grid name="customerChild#=cid#" pageable="true" resizable="true"
			sortable="true" reorderable="true" selectable="true"
			scrollable="true" editable="true" edit="customerItemsGridEvent" >
			<kendo:grid-pageable pageSize="10"></kendo:grid-pageable>
			<kendo:grid-filterable extra="false">
				<kendo:grid-filterable-operators>
					<kendo:grid-filterable-operators-string eq="Is equal to" />
				</kendo:grid-filterable-operators>
			</kendo:grid-filterable>
			<kendo:grid-editable mode="popup"
				confirmation="Are sure you want to delete this item ?" />
			<kendo:grid-toolbar>
				<kendo:grid-toolbarItem name="create" text="Add Customer Items" />
			</kendo:grid-toolbar>
			<kendo:grid-columns>
				<kendo:grid-column title="child" field="ccid" width="100px"
					hidden="true">
				</kendo:grid-column>
				
					<kendo:grid-column title="child" field="cid" width="100px"
					hidden="true">
				</kendo:grid-column>
				
				
				<kendo:grid-column title="Item Name" field="itemName" width="100px">
			</kendo:grid-column>
			
			<kendo:grid-column title="Item Quantity" field="itemQuantity" width="100px">
			</kendo:grid-column>
				
				<kendo:grid-column title="Item Price" field="itemPrice" width="100px">
			</kendo:grid-column>
				
				<kendo:grid-column title="Item Total Price" field="itemTotalPrice" width="100px">
			</kendo:grid-column>
				
					
					<kendo:grid-column title="Ordeered Date*" field="uom"  width="120px"  editor="uomDropDownEditor">
		</kendo:grid-column>
		
				<kendo:grid-column title="&nbsp;" width="220px">
					<kendo:grid-column-command>
						<kendo:grid-column-commandItem name="edit" />
					
					</kendo:grid-column-command>
				</kendo:grid-column>

			</kendo:grid-columns>
				<kendo:dataSource  requestEnd="onCustomerItemsRequestEnd" requestStart="onRequestStart1" >
				<kendo:dataSource-transport>
			<kendo:dataSource-transport-read url="${CustomerReadUrl}/#=cid#" dataType="json" type="GET" contentType="application/json" />
					<kendo:dataSource-transport-create url="${CustomerCreateUrl}/#=cid#" dataType="json" type="GET" contentType="application/json" />
				<%-- 	<kendo:dataSource-transport-update url="${templateItemUpdateUrl}/#=rid#" dataType="json" type="GET" contentType="application/json" />
					<kendo:dataSource-transport-destroy url="${templateItemDestroyUrl}" dataType="json" type="GET" contentType="application/json" />
				 --%></kendo:dataSource-transport>
				<kendo:dataSource-schema>
					<kendo:dataSource-schema-model id="ccid">
						<kendo:dataSource-schema-model-fields>

   										<kendo:dataSource-schema-model-field name="ccid" type="number">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="cid" type="number">
											</kendo:dataSource-schema-model-field>
											<kendo:dataSource-schema-model-field name="itemName" type="string">
											</kendo:dataSource-schema-model-field>
											<kendo:dataSource-schema-model-field name="itemQuantity" type="number">
											</kendo:dataSource-schema-model-field>
											<kendo:dataSource-schema-model-field name="itemPrice" type="number">
											</kendo:dataSource-schema-model-field>
											<kendo:dataSource-schema-model-field name="itemTotalPrice" type="number">
											</kendo:dataSource-schema-model-field>
												<kendo:dataSource-schema-model-field name="uom" type="String"/>
						</kendo:dataSource-schema-model-fields>
					</kendo:dataSource-schema-model>
				</kendo:dataSource-schema>
			</kendo:dataSource>
		</kendo:grid>
	</kendo:tabStrip-item-content>
	</kendo:tabStrip-item>
	
			<kendo:tabStrip-item text="Customer PreOrder">
					<kendo:tabStrip-item-content>
					<div class="wethear" >
		<kendo:grid name="itemGrid#=cid#" pageable="true" resizable="true" sortable="true" reorderable="true"  scrollable="true" edit="CustomerPreOrderEvent"  >
		
		<kendo:grid-filterable extra="false">
		 <kendo:grid-filterable-operators>
		  	<kendo:grid-filterable-operators-string eq="Is equal to" />
		 </kendo:grid-filterable-operators>
			
		</kendo:grid-filterable>
		  <kendo:grid-editable mode="popup"
			confirmation="Are you sure you want to remove this Notification?" />
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add customerDetails" />
		</kendo:grid-toolbar>
	<%-- 	<kendo:grid-toolbarTemplate>
			<div class="toolbar">
			<a class="k-button k-button-icontext k-grid-add" href="#">
		            <span class="k-icon k-add"></span>
		            Add Item Master
	        	</a>
			 <a class='k-button' href='\\#'><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>  
			</div>  	
    	</kendo:grid-toolbarTemplate> --%>
    	<kendo:grid-columns>
    	<kendo:grid-column title="Item Name*" field="itemName"  width="120px" >
		</kendo:grid-column>
		
			<kendo:grid-column title="Item Quantity*" field="itemQuantity"  width="120px" >
		</kendo:grid-column>
		
			<kendo:grid-column title="Item Price*" field="itemPrice"  width="120px" >
		</kendo:grid-column>
		
			<kendo:grid-column title="Item TPrice*" field="itemTotalPrice"  width="120px" >
		</kendo:grid-column>
		
			<kendo:grid-column title="Item CusName*" field="cusName"  width="120px" >
		</kendo:grid-column>
			<kendo:grid-column title="Cus Num*" field="cusNum"  width="120px" >
		</kendo:grid-column>
		
		<kendo:grid-column title="Email*" field="Email"  width="120px" >
		</kendo:grid-column>
			
				<kendo:grid-column title="status*" field="customerStatus"  width="120px" >
		</kendo:grid-column>
		<kendo:grid-column title="updated date*" field="lastUpdatedDt"  width="120px" >
		</kendo:grid-column>
		
		<kendo:grid-column title="ordered 	date*" field="createdDate"  width="120px" >
		</kendo:grid-column>
		
		<kendo:grid-column title="&nbsp;" width="220px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit" />
					<kendo:grid-column-commandItem name="destroy" />
				</kendo:grid-column-command>
				</kendo:grid-column>
				
			
				
				</kendo:grid-columns>
				
			<kendo:dataSource pageSize="5" requestStart="onRequestStart2">
			<kendo:dataSource-transport>
		<%-- 	<kendo:dataSource-transport-create url="${createpreUrl}/#=personId#" dataType="json" type="GET" contentType="application/json"/>  --%>
  <kendo:dataSource-transport-read url="${readpreUrl}/#=personId#" dataType="json" type="POST" contentType="application/json"/>
 			
<%-- <kendo:dataSource-transport-update url="${updateUrl}" dataType="json" type="GET" contentType="application/json" />	 --%>
			<kendo:dataSource-transport-destroy url="${destroypreUrl}" dataType="json" type="GET" contentType="application/json"/>  
			
			</kendo:dataSource-transport>
			
			 <kendo:dataSource-schema parse="parse">
				<kendo:dataSource-schema-model id="cpid">
					<kendo:dataSource-schema-model-fields>
					<kendo:dataSource-schema-model-field name="cpid" type="number" />
					
						<kendo:dataSource-schema-model-field name="cid" type="number" />
						<kendo:dataSource-schema-model-field name="itemName" type="String"/>
						
						<kendo:dataSource-schema-model-field name="itemQuantity" type="String"/>
						<kendo:dataSource-schema-model-field name="itemPrice" type="String"/>
						<kendo:dataSource-schema-model-field name="itemTotalPrice" type="String"/>
							<kendo:dataSource-schema-model-field name="cusName" type="String"/>
								<kendo:dataSource-schema-model-field name="cusNum" type="String"/>
						
							<kendo:dataSource-schema-model-field name="Email" type="String"/>
						
							<kendo:dataSource-schema-model-field name="customerStatus" type="String"/>
						<kendo:dataSource-schema-model-field name="lastUpdatedDt" type="String"/>
						<kendo:dataSource-schema-model-field name="createdDate" type="String"/>
						
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
							
<div id="uploadDialog" title="Upload Image" style="display: none;">
	<kendo:upload complete="oncomplete" name="files" upload="oncustomerImageUpload" multiple="false" success="onImageSuccess">
				<kendo:upload-async autoUpload="true" saveUrl="${customerImage}" />
	</kendo:upload>
	
	
</div>				
				
						<script>
						
						
						
						 function onRequestStart(e){
								$('.k-grid-update').hide();
								$('.k-edit-buttons')
										.append(
												'<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
								$('.k-grid-cancel').hide();
						}
						 function onRequestStart1(e){
								$('.k-grid-update').hide();
								$('.k-edit-buttons')
										.append(
												'<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
								$('.k-grid-cancel').hide();
						}
						 function onRequestStart2(e){
								$('.k-grid-update').hide();
								$('.k-edit-buttons')
										.append(
												'<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
								$('.k-grid-cancel').hide();
						}
						
var cid="";
						
						function onChangeProcess(arg) {
							var gview = $("#customerGrid").data("kendoGrid");
							var selectedItem = gview.dataItem(gview.select());
							SelectedRowId = selectedItem.cid;
							cid=selectedItem.cid;
						
							
							
							
							// processStatus = selectedItem.processStatus;
						}
						function oncomplete() {
							$("#myImage").attr(
									"src",
									"<c:url value='/users/getUserimage/" + SelectedRowId
											+ "?timestamp=" + new Date().getTime() + "'/>");
							$("#myImages_" + SelectedRowId).attr(
									"src",
									"<c:url value='/users/getUserimage/" + SelectedRowId
											+ "?timestamp=" + new Date().getTime() + "'/>");
						}
						function clickToUploadImage() {
							$('#uploadDialog').dialog({
								modal : true,
							});
							return false;
						}
						function oncustomerImageUpload(e) {
							var files = e.files;
							// Check the extension of each file and abort the upload if it is not .jpg
							$.each(files, function() {
								if (this.extension.toLowerCase() == ".png") {
									e.data = {
											cid : SelectedRowId
										};
								}
								else if (this.extension.toLowerCase() == ".jpg") {
									
									e.data = {
											cid : SelectedRowId
										};
								}
								else if (this.extension.toLowerCase() == ".jpeg") {
									
									e.data = {
											cid : SelectedRowId
										};
								}
								else {
									alert("Only Images can be uploaded\nAcceptable formats are png, jpg and jpeg");
									e.preventDefault();
									return false;
								}
							});
						}
						function onImageSuccess(e) {
							alert("Uploaded Successfully");
							$(".k-upload-files.k-reset").find("li").remove();
							window.location.reload();
						}
						
					/* 	$("#customerGrid").on("click", "#temPID", function(e) 
								  {
									 var button = $(this),
									 enable = button.text() == "Accept";
									 var widget = $("#customerGrid").data("kendoGrid");
									 var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));		 
									 if (enable)
									 {
										$.ajax
										({
											type : "POST",
											url : "./customer/Status/" + dataItem.id + "/activate",
											dataType : 'text',
											success : function(response) 
											{
												$("#alertsBox").html("");
												$("#alertsBox").html(response);
										  	    $("#alertsBox").dialog
												({
													modal : true,
													buttons : {
													   "Close" : function() {
												          $(this).dialog("close");
														}
													}
												});
												button.text('Accept');
												$('#customerGrid').data('kendoGrid').dataSource.read();
											}
										});
									 }
									 else 
									 {
									      $.ajax
										  ({
											   type : "POST",
											   url : "./customer/Status/" + dataItem.id + "/deactivate",
											   dataType : 'text',
											   success : function(response) 
											   {
												   $("#alertsBox").html("");
												   $("#alertsBox").html(response);
												   $("#alertsBox").dialog({
													   modal : true,
													   buttons : 
													   {
														   "Close" : function() {
																$(this).dialog("close");
															 }
															}
													   });
													  button.text('Accepted');
													  $('#customerGrid').data('kendoGrid').dataSource.read();
													 }
													});
									 }	
								   }); */
						function customerItemsGridEvent(e)
						{
								
							$('div[data-container-for="cid"]').hide();
								$('label[for="cid"]').closest('.k-edit-label').hide();

								$('div[data-container-for="ccid"]').hide();
								$('label[for="ccid"]').closest('.k-edit-label').hide();

								$('div[data-container-for="itemTotalPrice"]').hide();
								$('label[for="itemTotalPrice"]').closest('.k-edit-label').hide();

							
								
								if (e.model.isNew()) 
							    {
									$(".k-window-title").text("Add Recoopment Approval Details");
									$(".k-grid-update").text("Save");		
							    }
								else{
									$(".k-window-title").text("Edit Recoopment Approval Details");
								}

							}
						
						function onCustomerItemsRequestEnd(e) 
						{ 
						  if (typeof e.response != 'undefined') {
						   if (e.response.status == "FAIL") {
						    errorInfo = "";
						    for (var i = 0; i < e.response.result.length; i++) {
						     errorInfo += (i + 1) + ". "
						       + e.response.result[i].defaultMessage + "<br>";
						    }
						      if (e.type == "create") {
						     $("#alertsBox").html("");
						     $("#alertsBox").html(
						       "Error: Creating the process details \n\n : "
						         + errorInfo);
						     $("#alertsBox").dialog({
						      modal : true,
						      buttons : {
						       "Close" : function() {
						        $(this).dialog("close");
						       }
						      }
						     });
						    }
						    var grid = $("#customerChild"+SelectedRowId).data("kendoGrid");
						    grid.dataSource.read();
						    grid.refresh();
						   } 
						   else if (e.type == "create") {
						    $("#alertsBox").html("");
						    $("#alertsBox").html(
						      "Recoopment Approval is created successfully");
						    $("#alertsBox").dialog({
						     modal : true,
						     buttons : {
						      "Close" : function() {
						       $(this).dialog("close");
						      }
						     }
						    });

						    var grid = $("#customerChild"+SelectedRowId).data("kendoGrid");
						   grid.dataSource.read();
						    grid.refresh(); 
						   } else if (e.type == "destroy") {
						    $("#alertsBox").html("");
						    $("#alertsBox").html(
						      "Recoopment Approval is deleted successfully");
						    $("#alertsBox").dialog({
						     modal : true,
						     buttons : {
						      "Close" : function() {
						       $(this).dialog("close");
						      }
						     }
						    });

						    var grid = $("#customerChild"+SelectedRowId).data("kendoGrid");
						    grid.dataSource.read();
						    grid.refresh();
						   } else if (e.type == "update") {
						    $("#alertsBox").html("");
						    $("#alertsBox").html(
						      "Recoopment Appproval is updated successfully");
						    $("#alertsBox").dialog({
						     modal : true,
						     buttons : {
						      "Close" : function() {
						       $(this).dialog("close");
						      }
						     }
						    });
						   var grid = $("#customerChild"+SelectedRowId).data("kendoGrid");
						     grid.dataSource.read();
						    grid.refresh(); 
						   }
						 }

						 }
						
						
						//////////////////parent starts here /////////////////////
						function CustomerGridEvent(e){
							$('div[data-container-for="cid"]').remove();
							$('label[for="cid"]').closest('.k-edit-label').remove();
							
							 
						if (e.model.isNew()) 
						{
							$(".k-window-title").text("Add Item Master Details");
							$(".k-grid-update").text("Save");		
						}
						else{
							$(".k-window-title").text("Edit Item Master Details");
						}
						$('label[for="customerStatus"]').parent().remove();
						$('div[data-container-for="customerStatus"]').remove();
						$('a[id="temPID"]').remove();	
						}

						function onCustomerRequestEnd(e) {
							if (typeof e.response != 'undefined') {
								if (e.response.status == "FAIL") {
									errorInfo = "";
									errorInfo = e.response.result.invalid;
									var i = 0;
									for (i = 0; i < e.response.result.length; i++) {
										errorInfo += (i + 1) + ". "
												+ e.response.result[i].defaultMessage + "\n";
									}
									if (e.type == "create") {
										alert("Error: Creating the Store Master Details\n\n"
												+ errorInfo);
									}
									if (e.type == "update") {
										alert("Error: Updating the Store Master Details\n\n"
												+ errorInfo);
									}
									var grid = $("#customerGrid").data("kendoGrid");
									grid.dataSource.read();
									grid.refresh();
									return false;
								}
								if (e.type == "update" && !e.response.Errors) {
									e.sender.read();
									$("#alertsBox").html("Alert");
									$("#alertsBox").html("Item Master Updated successfully");
									$("#alertsBox").dialog({
										modal : true,
										draggable : false,
										resizable : false,
										buttons : {
											"Close" : function() {
												$(this).dialog("close");
											}
										}
									});
								}
								if (e.type == "create" && !e.response.Errors) {
									e.sender.read();
									$("#alertsBox").html("Alert");
									$("#alertsBox").html("Item Master Created successfully");
									$("#alertsBox").dialog({
										modal : true,
										draggable : false,
										resizable : false,
										buttons : {
											"Close" : function() {
												$(this).dialog("close");
											}
										}
									});
								}
								if (e.type == "destroy" && !e.response.Errors) {
									$("#alertsBox").html("Alert");
									$("#alertsBox").html("Item Master Deleted successfully");
									$("#alertsBox").dialog({
										modal : true,
										draggable : false,
										resizable : false,
										buttons : {
											"Close" : function() {
												$(this).dialog("close");
											}
										}
									});
									var grid = $("#customerGrid").data("kendoGrid");
									grid.dataSource.read();
									grid.refresh();
								}
							}
						}
						
						
						function CustomerPreOrderEvent(e){
							
							
							
							 $('div[data-container-for="createdDate"]').remove();
							 $('label[for="createdDate"]').closest('.k-edit-label').remove()
							 
							 $('div[data-container-for="cpid"]').remove();
							 $('label[for="cpid"]').closest('.k-edit-label').remove()
							 
							if (e.model.isNew()) 
							{
								$(".k-window-title").text("Add Item Master Details");
								$(".k-grid-update").text("Save");		
							}
							else{
								$(".k-window-title").text("Edit Item Master Details");
							}
							
						}
						
						function onprojectMilestoneRequestEnd(e)
						{
							if (typeof e.response != 'undefined') 
							{
								if (e.response.status == "ERROR") 
								{
									errorInfo = "";
									errorInfo = e.response.result.deleteBaseUomError;
									alert(errorInfo);
									
									var grid = $('#contractDetailGrid_' + SelectedRowId).data("kendoGrid");
									grid.dataSource.read();
									grid.refresh();
									return false;
								}
							}
							if (e.type == "update" && !e.response.Errors)
							{
								$("#alertsBox").html("Alert");
								$("#alertsBox").html("Contract Details Updated Successfully");
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
								$("#alertsBox").html("Contract Details Added Successfully");
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
						
						function parse (response) {
						
						    $.each(response, function (idx, elem) {   
						    	   if(elem.lastUpdatedDt=== null){
						    		   elem.lastUpdatedDt = "";
						    	   }else{
						    		   elem.lastUpdatedDt = kendo.parseDate(new Date(elem.lastUpdatedDt),'dd/MM/yyyy');
						    	   }
						    	   
						    });
						
						       return response;        
						}
						    
						function uomDropDownEditor(container, options) {
							   var data = ["litres" , "grams","kg","ml","no"];
							   $(
							     '<input data-text-field="" style="width:180px;" id="ownership" data-value-field="" data-bind="value:' + options.field + '" />')
							     .appendTo(container).kendoDropDownList({
							      optionLabel :"Select",
							    
							      dataSource :data            	                 	      
							   });
						}        
						function statusBtn(a) {
							//securityCheckForActions("./customer/order/approverejectButton");
							var ask = confirm("Are You Sure? This action cannot be undone");
							if (ask == true) {
								if (a == 'Order Open')
									a = 'Order Opened';
								else
									a = 'Order Closed';
								$.ajax({
									type : "POST",
									url : "./customer/updatestatus/" + cid + "/" + a,
									dataType : "text",
									success : function(response) {
										alert("Asset gets " + response + " !!!");
										if (response.status === 'DENIED') {
											alert(response.result);
										} else {
											$('#customerGrid').data('kendoGrid').dataSource.read();
										}
									}
								});
							}
						}
						</script>
						<style>
.k-upload-button input {
 z-index: 10000
}
</style>
				