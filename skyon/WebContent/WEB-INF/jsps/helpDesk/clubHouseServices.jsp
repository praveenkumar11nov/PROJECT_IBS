<%@include file="/common/taglibs.jsp"%>

<c:url value="/clubHouse/Create" var="createUrl" />
<c:url value="/clubHouse/Read" var="readUrl" />
<c:url value="/clubHouse/Update" var="updateUrl" />
<c:url value="/clubhouse/Destroy" var="destroyUrl" />

<%-- <c:url value="/storemaster/Filter" var="StoreNameFilterUrl" /> --%>


<kendo:grid name="storeGrid" pageable="true" resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true"  edit="StoreGridEvent">
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
			confirmation="Are you sure you want t o remove this Notification?" />
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Notification" />
		</kendo:grid-toolbar>
		<kendo:grid-toolbarTemplate>
			<div class="toolbar">
			<a class="k-button k-button-icontext k-grid-add" href="#">
		            <span class="k-icon k-add"></span>
		            Add Services
	        	</a>
			 <a class='k-button' href='\\#' onclick="clearFilter()"><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>  
			</div>  	
    	</kendo:grid-toolbarTemplate>
    	<kendo:grid-columns>
    	
		<kendo:grid-column title="Service Name*" field="serviceName" width="120px" >
		 <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockFilter(element) {								
								element.kendoAutoComplete({
									dataSource: {
										transport: {
											read: "${StoreNameFilterUrl}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	
	    		</kendo:grid-column>
		
		
		
		
		<kendo:grid-column title="Service Description*" field="serviceDesc"  editor="DescriptionEditor" width="120px" >
		</kendo:grid-column>
		
	
		
			<kendo:grid-column title="&nbsp;" width="220px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit" />
					<kendo:grid-column-commandItem name="destroy" />
				</kendo:grid-column-command>
				</kendo:grid-column>
				
			<%--  <kendo:grid-column title="Store&nbspStatus&nbsp;*" field="storeStatus"  filterable="true" width="130px" />
			<kendo:grid-column title=""
				template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Activate#=data.sId#'>#= data.storeStatus == 'Active' ? 'De-activate' : 'Activate' #</a>"
				width="100px" />
				 --%>
				</kendo:grid-columns>
				
			<kendo:dataSource pageSize="5" requestEnd="onStoreRequestEnd" requestStart="onRequestStart">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-create url="${createUrl}" dataType="json" type="GET" contentType="application/json"/>
	<kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json"/>
	<kendo:dataSource-transport-update url="${updateUrl}" dataType="json" type="GET" contentType="application/json" />
	<kendo:dataSource-transport-destroy url="${destroyUrl}" dataType="json" type="GET" contentType="application/json"/> 
	<%-- 	<kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json"/>  --%>
		<%-- 	<kendo:dataSource-transport-create url="${createUrl}" dataType="json" type="GET" contentType="application/json"/>
			<kendo:dataSource-transport-update url="${updateUrl}" dataType="json" type="GET" contentType="application/json" />
		<kendo:dataSource-transport-destroy url="${destroyUrl}" dataType="json" type="GET" contentType="application/json"/>  --%>
				<%-- 	<kendo:dataSource-transport-parameterMap>
					<script>
						function parameterMap(options, type) {
							return JSON.stringify(options);
						}
					</script>
				</kendo:dataSource-transport-parameterMap>  --%>
			</kendo:dataSource-transport>
			
			 <kendo:dataSource-schema>
				<kendo:dataSource-schema-model id="sId">
					<kendo:dataSource-schema-model-fields>
					<kendo:dataSource-schema-model-field name="sId" type="number" />
						<kendo:dataSource-schema-model-field name="serviceName" type="String"/>
				
						
						<kendo:dataSource-schema-model-field name="serviceDesc">
						
						</kendo:dataSource-schema-model-field>
					<%-- 	 <kendo:dataSource-schema-model-field name="storeStatus" type="string" defaultValue="Inactive"/> --%>
					
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
			</kendo:dataSource>				

</kendo:grid>
<div id="alertsBox" title="Alert"></div>
<script>

function onRequestStart(e){
	$('.k-grid-update').hide();
	$('.k-edit-buttons')
			.append(
					'<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
	$('.k-grid-cancel').hide();
}

function StoreGridEvent(e)
{
	
	$('div[data-container-for="sId"]').remove();
	$('label[for="sId"]').closest('.k-edit-label').remove();
if (e.model.isNew()) 
{
	$(".k-window-title").text("Add Service Details");
	$(".k-grid-update").text("Save");		
}
else{
	$(".k-window-title").text("Edit Service Details");
}


$('label[for="storeStatus"]').parent().remove();
$('div[data-container-for="storeStatus"]').remove();
$('a[id="temPID"]').remove();	
}



function clearFilter()
{
   $("form.k-filter-menu button[type='reset']").slice().trigger("click");
   var gridStoreIssue = $("#storeGrid").data("kendoGrid");
   gridStoreIssue.dataSource.read();
   gridStoreIssue.refresh();
}

function DescriptionEditor(container,options)
{
	  $('<textarea name="Service Description" data-text-field="serviceDesc" data-value-field="serviceDesc" data-bind="value:' + options.field + '" style="width: 161px; height: 46px;" required="true"/>').appendTo(container);
	  $('<span class="k-invalid-msg" data-for="Service Description"></span>').appendTo(container); 
}
function onStoreRequestEnd(e) {
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
			var grid = $("#storeGrid").data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
			return false;
		}
		if (e.type == "update" && !e.response.Errors) {
			e.sender.read();
			$("#alertsBox").html("Alert");
			$("#alertsBox").html("Service Updated successfully");
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
			$("#alertsBox").html("Service Created successfully");
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
			$("#alertsBox").html("Store Master Deleted successfully");
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
			var grid = $("#storeGrid").data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
		}
	}
}

$("#grid").on("click", "#temPID", function(e) {
		var button = $(this), enable = button.text() == "Activate";
		var widget = $("#storeGrid").data("kendoGrid");
		var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));

		var result=securityCheckForActionsForStatus("./parkingManagement/parkingslots/deleteButton");   
	    if(result=="success"){
			if (enable) {
				$.ajax({
					type : "POST",
					url : "./storeMaster/StoreMasterStatus/" + dataItem.id + "/activate",
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
						button.text('Deactivate');
						$('#storeGrid').data('kendoGrid').dataSource.read();
					}
				});
			} else {
				$.ajax({
					type : "POST",
					url : "./storeMaster/StoreMasterStatus/" + dataItem.id + "/deactivate",
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
						button.text('Activate');
						$('#storeGrid').data('kendoGrid').dataSource.read();
					}
				});
			}		
	  	 }  						
		
	});	


$("#storeGrid").on("click", "#temPID", function(e) 
		  {
			 var button = $(this),
			 enable = button.text() == "Activate";
			 var widget = $("#storeGrid").data("kendoGrid");
			 var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));		 
			 if (enable)
			 {
				$.ajax
				({
					type : "POST",
					url : "./store/Status/" + dataItem.id + "/activate",
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
						button.text('Deactivate');
						$('#storeGrid').data('kendoGrid').dataSource.read();
					}
				});
			 }
			 else 
			 {
			      $.ajax
				  ({
					   type : "POST",
					   url : "./store/Status/" + dataItem.id + "/deactivate",
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
							  button.text('Activate');
							  $('#storeGrid').data('kendoGrid').dataSource.read();
							 }
							});
			 }	
		   });
(function($, kendo) 
		  {
			 $.extend(true,kendo.ui.validator,
			 { 
				rules : { 
						  storeNamevalidation : function(input, params) 
			  		      { 
							 if (input.filter("[name='storeName']").length && input.val()) 
							 {
								return /^[a-zA-Z]+[ ._a-zA-Z0-9._]*[a-zA-Z0-9]$/.test(input.val());
							 }
							 return true;
						  },
					  storeNameNullValidator : function(input,params) 
  				  { 
  					  if (input.attr("name") == "storeName") 
  					  {
						     return $.trim(input.val()) !== "";
						  }
						  return true;
  		           },
  		           storeDescNullValidator : function(input,params) 
	    				  { 
	    					  if (input.attr("name") == "storeDesc") 
	    					  {
	  						     return $.trim(input.val()) !== "";
	  						  }
	  						  return true;
	    		           },
	    		        
		    		       
		    		           
	    		           
				},
				  messages : 
				  {
					  storeNameNullValidator:"please select amount",
					  storeDescNullValidator:"please select remarks",
					  storeNamevalidation:"should not alllow apecial characters",
				  }
			 });
	})(jQuery, kendo);


</script>