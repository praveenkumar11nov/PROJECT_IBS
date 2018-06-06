<%@include file="/common/taglibs.jsp"%>

<c:url value="/adjustmentMaster/adjustmentMasterCreate" var="createUrl" />
<c:url value="/adjustmentMaster/adjustmentMasterRead" var="readUrl" />
<c:url value="/adjustmentMaster/adjustmentMasterDestroy" var="destroyUrl" />
<c:url value="/adjustmentMaster/adjustmentMasterUpdate" var="updateUrl" />

<c:url value="/billingSettings/getAllPropertyUrl" var="getAllPropertyUrl" />
<c:url value="/billingSettings/filter" var="commonFilterForBillSettingsUrl" /> 

<kendo:grid name="grid" remove="billingSettingsDeleteEvent" pageable="true" resizable="true" edit="billingSettingsEvent" sortable="true" reorderable="true" selectable="true" scrollable="true" filterable="false" groupable="true">
    <kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" refresh="true" info="true" previousNext="true">
	<kendo:grid-pageable-messages itemsPerPage="Status items per page" empty="No status item to display" refresh="Refresh all the status items" 
			display="{0} - {1} of {2} New Status Items" first="Go to the first page of status items" last="Go to the last page of status items" next="Go to the next page of status items"
			previous="Go to the previous page of status items"/>
	</kendo:grid-pageable>
	<kendo:grid-filterable extra="false">
		<kendo:grid-filterable-operators>
			<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains"/>
			<kendo:grid-filterable-operators-date gt="Is after" lt="Is before"/>
		</kendo:grid-filterable-operators> 
	</kendo:grid-filterable>
	<kendo:grid-editable mode="popup"/>
		<kendo:grid-toolbar>
		      <kendo:grid-toolbarItem name="create" text="Add Adjustment Master" />
		     <kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterAMRSettings()><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>"/>
	    </kendo:grid-toolbar>
	<kendo:grid-columns>
	    
	    <kendo:grid-column title="Id" field="adjMasterId" width="70px" hidden="true" filterable="false" sortable="false" />
	    
	     
	    
	    <kendo:grid-column title="Adjustment Name&nbsp;*" field="adjName" width="70px"  filterable="false">
	    <kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function storeNameFilter(element) 
						   	{
								element.kendoAutoComplete({
									dataSource : {
										transport : {		
											read :  "${commonFilterForBillSettingsUrl}/adjName"
										}
									} 
								});
						   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	     </kendo:grid-column>
	     
	      <%-- <kendo:grid-column title="Value&nbsp;*" field="configValueDropDown" width="70px"   hidden="true" filterable="false"/>--%>
	      
	      <kendo:grid-column title="Description&nbsp;" field="description" editor="descriptionEditor" width="70px" filterable="true">
		   <kendo:grid-column-filterable>
						<kendo:grid-column-filterable-ui>
							<script type="text/javascript">
								function storeNameFilter(element) 
							   	{
									element.kendoAutoComplete({
										dataSource : {
											transport : {		
												read :  "${commonFilterForBillSettingsUrl}/description"
											}
										} 
									});
							   	}
							</script>
						</kendo:grid-column-filterable-ui>
					</kendo:grid-column-filterable>
		    </kendo:grid-column> 

	    <kendo:grid-column title="Status" field="status" width="70px" filterable="true">
	    <kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function storeNameFilter(element) 
						   	{
								element.kendoAutoComplete({
									dataSource : {
										transport : {		
											read :  "${commonFilterForBillSettingsUrl}/status"
										}
									} 
								});
						   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	    </kendo:grid-column>

       <kendo:grid-column title=""
				template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Active#=data.id#'>#= data.status == 'Active' ? 'Inactivate' : 'Activate' #</a>"
				width="80px" />
		<kendo:grid-column title="&nbsp;" width="160px">
		 
			<kendo:grid-column-command>
			    <kendo:grid-column-commandItem name="edit" />
				<kendo:grid-column-commandItem name="destroy" />
			</kendo:grid-column-command>
		</kendo:grid-column>
		
	</kendo:grid-columns>

	<kendo:dataSource pageSize="20" requestEnd="onRequestEnd" requestStart="onRequestStart">
		<kendo:dataSource-transport>
			<kendo:dataSource-transport-create url="${createUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json" />
			<kendo:dataSource-transport-update url="${updateUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-destroy url="${destroyUrl}/" dataType="json" type="GET" contentType="application/json" />
		</kendo:dataSource-transport>

		<kendo:dataSource-schema>
			<kendo:dataSource-schema-model id="adjMasterId">
				<kendo:dataSource-schema-model-fields>
					<kendo:dataSource-schema-model-field name="adjMasterId" type="number"/>
					<kendo:dataSource-schema-model-field name="adjName" type="string"/>					
					<kendo:dataSource-schema-model-field name="description" type="string"/>
  					<kendo:dataSource-schema-model-field name="status" type="string"/>
  
				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>

	</kendo:dataSource>

</kendo:grid>
<div id="alertsBox" title="Alert"></div>
<script>

function descriptionEditor(container, options){
	$('<textarea data-text-field="description" name="description" style="width:150px;height:60px"/>').appendTo(container);
	$('<span class="k-invalid-msg" data-for="Enter Description></span>').appendTo(container);
}

var statusNameArray = [];

function clearFilterAMRSettings()
{
   $("form.k-filter-menu button[type='reset']").slice().trigger("click");
   var gridStoreIssue = $("#grid").data("kendoGrid");
   gridStoreIssue.dataSource.read();
   gridStoreIssue.refresh();
}

function billingSettingsDeleteEvent(){
	//securityCheckForActions("./BillGeneration/billingSettings/delete");
	var conf = confirm("Are u sure  u want Delete Adjustment Master?");
	 if(!conf){
	  $('#grid').data().kendoGrid.dataSource.read();
	   throw new Error('deletion aborted');
	 }
}


var setApCode="";		
function billingSettingsEvent(e)
{
	/***************************  to remove the id from pop up  **********************/
    $('a[id="temPID"]').remove();

	$('div[data-container-for="adjMasterId"]').remove();
	$('label[for="adjMasterId"]').closest('.k-edit-label').remove();
	
	$('div[data-container-for="status"]').remove();
	$('label[for="status"]').closest('.k-edit-label').remove();
	
	
	
	
	
	/************************* Button Alerts *************************/
	
	if (e.model.isNew()) 
    {
		//securityCheckForActions("./BillGeneration/billingSettings/create");
		setApCode = $('input[name="adjMasterId"]').val();
		$(".k-window-title").text("Add Adjustment Master");
		$(".k-grid-update").text("Save");		
    }
	else{
		//securityCheckForActions("./BillGeneration/billingSettings/update");
		
	
		$(".k-window-title").text("Edit Adjustment Master");
	}
}
	
	function onRequestStart(e){
		$('.k-grid-update').hide();
        $('.k-edit-buttons').append( '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();
		
	}
	function onRequestEnd(e) {
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
							"Error: Creating the meter status\n\n : "
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
				var grid = $("#grid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			} 
			else if (e.type == "create") {
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Adjustment Master created successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});

				var grid = $("#grid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			} else if (e.type == "destroy") {
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Adjustment Master deleted successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});

				var grid = $("#grid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			} else if (e.type == "update") {
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Adjustment Master updated successfully");
				$("#alertsBox").dialog({
					modal : true,
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
	}
		
	  $("#grid").on("click", "#temPID", function(e) {
		  
		 
		  var button = $(this), enable = button.text() == "Activate";
		  var widget = $("#grid").data("kendoGrid");
		  var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
			  
		 // var result=securityCheckForActionsForStatus("./BillGeneration/billingSettings/status"); 
		 
				if (enable) 
				{
					$.ajax
					({
						type : "POST",
						url : "./adjustmentMaster/adjustmentMasterStatus/" +dataItem.adjMasterId+ "/activate",
						dataType:"text",
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
							button.text('Inactivate');
							$('#grid').data('kendoGrid').dataSource.read();
						}
					});
				} 
				else 
				{
					$.ajax
					({
						type : "POST",
						url : "./adjustmentMaster/adjustmentMasterStatus/" + dataItem.adjMasterId + "/deactivate",
						dataType:"text",
						success : function(response) 
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
							button.text('Activate');
							$('#grid').data('kendoGrid').dataSource.read();
						}
					});
				}
			
	   });
	//register custom validation rules
	(function ($, kendo) 
		{   	  
	    $.extend(true, kendo.ui.validator, 
	    {
	         rules: 
	         { // custom rules
	        	 
	        configValueRequired : function(input, params){
               if (input.attr("name") == "adjName")
               {
                return $.trim(input.val()) !== "";
               }
               return true;
              },
	         },
	         messages: 
	         {
				//custom rules messages
				configValueRequired : "Adjustment Master Name is Required",
	     	 }
	    });
	    
	})(jQuery, kendo);
	  //End Of Validation
</script>
