<%@include file="/common/taglibs.jsp"%>

<c:url value="/amrSetting/amrSettingCreate" var="createUrl" />
<c:url value="/amrSetting/amrSettingRead" var="readUrl" />
<c:url value="/amrSetting/amrSettingDestroy" var="destroyUrl" />
<c:url value="/amrSetting/amrSettingUpdate" var="updateUrl" />

<c:url value="/amrSetting/getAllBlockUrl" var="getAllBlockUrl" />

<c:url value="/amrSetting/getAllPropertyUrl" var="getAllPropertyUrl" />


<c:url value="/amrSetting/blockNameFilterUrl" var="blockNameFilterUrl" />
<c:url value="/amrSetting/propertyNameFilterUrl" var="propertyNameFilterUrl" />
<c:url value="/amrSetting/columnNameFilterUrl" var="columnNameFilterUrl" />
<c:url value="/amrSetting/statusFilterUrl" var="statusFilterUrl" />
<c:url value="/amrSetting/meterNumberFilterUrl" var="meterNumberFilterUrl" />

<kendo:grid name="grid" remove="amrSettingDeleteEvent" pageable="true" resizable="true" edit="amrSettingEvent" sortable="true" reorderable="true" selectable="true" scrollable="true" filterable="false" groupable="true">
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
		      <kendo:grid-toolbarItem name="create" text="Create AMR For Property" />
		      <kendo:grid-toolbarItem name="activateAll" text="Activate All" />
		       <kendo:grid-toolbarItem name="amrTemplatesDetailsExport" text="Export To Excel" />
		       <kendo:grid-toolbarItem name="amrPdfTemplatesDetailsExport" text="Export To PDF" />
		     <kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterAMRSettings()><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>"/>
	    </kendo:grid-toolbar>
	<kendo:grid-columns>
	    
	    <kendo:grid-column title="AMR Id" field="amrId" width="70px" hidden="true" filterable="false" sortable="false" />
	    
	    <kendo:grid-column title="Tower&nbsp;*" field="blockId" width="70px" filterable="false" hidden="true"/>
	    
	     <kendo:grid-column title="Tower&nbsp;*" field="blocksName" width="70px" filterable="true" editor="towerNameEditor">
	     <kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function storeNameFilter(element) 
						   	{
								element.kendoAutoComplete({
									dataSource : {
										transport : {		
											read :  "${blockNameFilterUrl}"
										}
									} 
								});
						   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	     </kendo:grid-column>
	    
	    <kendo:grid-column title="Property&nbsp;*" field="propertyId" width="70px" filterable="false" hidden="true"/>
	    
	    <kendo:grid-column title="Property&nbsp;*" field="propertyName" width="70px" filterable="false" editor="propertyEditor">
	    <kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function storeNameFilter(element) 
						   	{
								element.kendoAutoComplete({
									dataSource : {
										transport : {		
											read :  "${propertyNameFilterUrl}"
										}
									} 
								});
						   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	     </kendo:grid-column>
	     
	      <kendo:grid-column title="Meter&nbsp;Number" field="meterNumber" width="70px" filterable="true">
		   <kendo:grid-column-filterable>
						<kendo:grid-column-filterable-ui>
							<script type="text/javascript">
								function storeNameFilter(element) 
							   	{
									element.kendoAutoComplete({
										dataSource : {
											transport : {		
												read :  "${meterNumberFilterUrl}"
											}
										} 
									});
							   	}
							</script>
						</kendo:grid-column-filterable-ui>
					</kendo:grid-column-filterable>
		    </kendo:grid-column>
	     
	    <kendo:grid-column title="AMR&nbsp;Field&nbsp;Name&nbsp;*" field="columnName" width="70px" filterable="false">
		   <kendo:grid-column-filterable>
						<kendo:grid-column-filterable-ui>
							<script type="text/javascript">
								function storeNameFilter(element) 
							   	{
									element.kendoAutoComplete({
										dataSource : {
											transport : {		
												read :  "${columnNameFilterUrl}"
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
											read :  "${statusFilterUrl}"
										}
									} 
								});
						   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	    </kendo:grid-column>

        <kendo:grid-column title=""
				template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Active#=data.amrId#'>#= data.status == 'Active' ? 'Inactivate' : 'Activate' #</a>"
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

		<kendo:dataSource-schema parse="parseAmrSetting">
			<kendo:dataSource-schema-model id="amrId">
				<kendo:dataSource-schema-model-fields>
					<kendo:dataSource-schema-model-field name="amrId" type="number"/>
					
					<kendo:dataSource-schema-model-field name="blockId" type="number"/>
					
					<kendo:dataSource-schema-model-field name="blocksName" type="string"/>
					
					<kendo:dataSource-schema-model-field name="columnName" type="string"/>
					
					<kendo:dataSource-schema-model-field name="propertyId" type="number"/>
					
					<kendo:dataSource-schema-model-field name="propertyName" type="string"/>
					
					<kendo:dataSource-schema-model-field name="meterNumber" type="string"/>
					
  					<kendo:dataSource-schema-model-field name="status" type="string"/>
  
				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>

	</kendo:dataSource>

</kendo:grid>
<div id="alertsBox" title="Alert"></div>
<script>

$("#grid").on("click",".k-grid-amrTemplatesDetailsExport", function(e) {
	  window.open("./amrTemplate/amrTemplatesDetailsExport");
});	

$("#grid").on("click",".k-grid-amrPdfTemplatesDetailsExport", function(e) {
	  window.open("./amrPdfTemplate/amrPdfTemplatesDetailsExport");
});	


function clearFilterAMRSettings()
{
   $("form.k-filter-menu button[type='reset']").slice().trigger("click");
   var gridStoreIssue = $("#grid").data("kendoGrid");
   gridStoreIssue.dataSource.read();
   gridStoreIssue.refresh();
}

var columnNameArray = [];
function parseAmrSetting(response) {   
    var data = response; 
    columnNameArray = [];
	 for (var idx = 0, len = data.length; idx < len; idx ++) {
		var res1 = (data[idx].columnName);
		columnNameArray.push(res1);
	 }  
	 return response;
} 

function amrSettingDeleteEvent(){
	securityCheckForActions("./Masters/AMRSettings/delete");
	var conf = confirm("Are u sure want to delete this status name?");
	 if(!conf){
	  $('#grid').data().kendoGrid.dataSource.read();
	   throw new Error('deletion aborted');
	 }
}


var setApCode="";
var flagColumnNameCode="";
function amrSettingEvent(e)
{
	/***************************  to remove the id from pop up  **********************/
    $('a[id="temPID"]').remove();
    flagColumnNameCode = true;
	$('div[data-container-for="amrId"]').remove();
	$('label[for="amrId"]').closest('.k-edit-label').remove();
	
	$('div[data-container-for="blockId"]').remove();
	$('label[for="blockId"]').closest('.k-edit-label').remove();
	
	$('div[data-container-for="propertyId"]').remove();
	$('label[for="propertyId"]').closest('.k-edit-label').remove();
	
	$('div[data-container-for="status"]').remove();
	$('label[for="status"]').closest('.k-edit-label').remove();
	
	$('div[data-container-for="meterNumber"]').remove();
	$('label[for="meterNumber"]').closest('.k-edit-label').remove();
	
	/************************* Button Alerts *************************/
	if (e.model.isNew()) 
    {
		securityCheckForActions("./Masters/AMRSettings/create");
		flagColumnNameCode = false;
		setApCode = $('input[name="amrId"]').val();
		$(".k-window-title").text("Create AMR Setting");
		$(".k-grid-update").text("Save");		
    }
	else{
		securityCheckForActions("./Masters/AMRSettings/update");
		$(".k-window-title").text("Edit AMR Setting");
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
						"AMR Property created successfully");
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
						"AMR Property deleted successfully");
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
				$("#alertsBox").html("AMR Property updated successfully");
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

		function towerNameEditor(container, options) 
	   	{
			$('<input name="Tower Name" id="blockId" data-text-field="blocksName" data-value-field="blockId" data-bind="value:' + options.field + '" required="true"/>')
			.appendTo(container).kendoComboBox({
				autoBind : false,			
				dataSource : {
					transport : {		
						read :  "${getAllBlockUrl}"
					}
				},
				change : function (e) {
		            if (this.value() && this.selectedIndex == -1) {                    
						alert("Block doesn't exist!");
		                $("#blockId").data("kendoComboBox").value("");
		        	}
		    
			    }  
			});
			
			$('<span class="k-invalid-msg" data-for="Tower Name"></span>').appendTo(container);
	   	}
	
	function propertyEditor(container, options) 
   	{
		$('<input name="Propery Name" id="blockId" data-text-field="propertyName" data-value-field="propertyId" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoComboBox({
			cascadeFrom : "blockId", 
			autoBind : false,			
			dataSource : {
				transport : {		
					read :  "${getAllPropertyUrl}"
				}
			},
			change : function (e) {
	            if (this.value() && this.selectedIndex == -1) {                    
					alert("P doesn't exist!");
	                $("#blockId").data("kendoComboBox").value("");
	        	}
	    
		    }  
		});
		
		$('<span class="k-invalid-msg" data-for="Propery Name"></span>').appendTo(container);
   	}
	  $("#grid").on("click", "#temPID", function(e) {
		  
		  var button = $(this), enable = button.text() == "Activate";
		  var widget = $("#grid").data("kendoGrid");
		  var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
		  
		  var result=securityCheckForActionsForStatus("./Masters/AMRSettings/status"); 
			if(result=="success"){  
				if (enable) 
				{
					$.ajax
					({
						type : "POST",
						url : "./amrSetting/amrSettingStatus/" +dataItem.amrId+ "/activate",
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
						url : "./amrSetting/amrSettingStatus/" + dataItem.amrId + "/deactivate",
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
			}
	   });
	  
		$("#grid").on("click",".k-grid-activateAll",function(e) {
			var r = confirm("Are u sure you want to activate all the bills?");
			 if(r == true){
								$.ajax({
											type : "POST",
											url : "./amrSetting/activateAll",
											dataType : "text",
											success : function(response) {
												$("#alertsBox").html("");
												$("#alertsBox").html(response);
												$("#alertsBox")
														.dialog({
															modal : true,
															buttons : {"Close" : function() {
																			$(this).dialog("close");
																		}
																	}
																});
												$('#grid').data('kendoGrid').dataSource
														.read();
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
	        	 
	        columnNameRequired : function(input, params){
               if (input.attr("name") == "columnName")
               {
                return $.trim(input.val()) !== "";
               }
               return true;
              },
               columnNameUniqueness : function(input,params){
			        if (input.filter("[name='columnName']").length && input.val()) {
			          enterdService = input.val().toUpperCase(); 
				          for(var i = 0; i<columnNameArray.length; i++) 
				          {
				            if ((enterdService == columnNameArray[i].toUpperCase()) && (enterdService.length == columnNameArray[i].length) ) 
				            {								            
				              return false;								          
				            }
				          }
			         }
		    return true;
		    }, 
	       },
	         messages: 
	         {
				//custom rules messages
				columnNameRequired : "Name is required",
				columnNameUniqueness : "Name already exists",
	     	 }
	    });
	    
	})(jQuery, kendo);
	  //End Of Validation
</script>
