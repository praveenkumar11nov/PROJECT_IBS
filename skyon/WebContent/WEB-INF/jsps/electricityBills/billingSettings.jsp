<%@include file="/common/taglibs.jsp"%>

<c:url value="/billingSettings/billingSettingsCreate" var="createUrl" />
<c:url value="/billingSettings/billingSettingsRead" var="readUrl" />
<c:url value="/billingSettings/billingSettingsDestroy" var="destroyUrl" />
<c:url value="/billingSettings/billingSettingsUpdate" var="updateUrl" />
<c:url value="/billingSettings/getConfigUrl" var="getConfigUrl" />
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
		      <kendo:grid-toolbarItem name="create" text="Create Setting" />
		     <kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterAMRSettings()><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>"/>
	    </kendo:grid-toolbar>
	<kendo:grid-columns>
	    
	    <kendo:grid-column title="Id" field="id" width="70px" hidden="true" filterable="false" sortable="false" />
	    
	     <kendo:grid-column title="Name&nbsp;*" field="configName" width="70px" filterable="true" editor="configNameEditor">
	     <kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function storeNameFilter(element) 
						   	{
								element.kendoAutoComplete({
									dataSource : {
										transport : {		
											read :  "${commonFilterForBillSettingsUrl}/configName"
										}
									} 
								});
						   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	     </kendo:grid-column>
	    
	    <kendo:grid-column title="Value&nbsp;*" field="configValue" width="70px" editor="configValueEditor" filterable="false">
	    <kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function storeNameFilter(element) 
						   	{
								element.kendoAutoComplete({
									dataSource : {
										transport : {		
											read :  "${commonFilterForBillSettingsUrl}/configValue"
										}
									} 
								});
						   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	     </kendo:grid-column>
	     
	      <kendo:grid-column title="Value&nbsp;*" field="configValueDropDown" width="70px"  editor="configValueDropDownEditor" hidden="true" filterable="false"/>
	      
	      <kendo:grid-column title="Description&nbsp;" field="description" width="70px" filterable="true">
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
			<kendo:dataSource-schema-model id="id">
				<kendo:dataSource-schema-model-fields>
					<kendo:dataSource-schema-model-field name="id" type="number"/>
					
					<kendo:dataSource-schema-model-field name="configName" type="string"/>
					
					<kendo:dataSource-schema-model-field name="configValue" type="string"/>
					
					<kendo:dataSource-schema-model-field name="configValueDropDown" type="string"/>
					
					<kendo:dataSource-schema-model-field name="description" type="string"/>
					
  					<kendo:dataSource-schema-model-field name="status" type="string"/>
  
				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>

	</kendo:dataSource>

</kendo:grid>
<div id="alertsBox" title="Alert"></div>
<script>

var statusNameArray = [];

function clearFilterAMRSettings()
{
   $("form.k-filter-menu button[type='reset']").slice().trigger("click");
   var gridStoreIssue = $("#grid").data("kendoGrid");
   gridStoreIssue.dataSource.read();
   gridStoreIssue.refresh();
}

function billingSettingsDeleteEvent(){
	securityCheckForActions("./BillGeneration/billingSettings/delete");
	var conf = confirm("Are u sure want to delete this status name?");
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

	$('div[data-container-for="id"]').remove();
	$('label[for="id"]').closest('.k-edit-label').remove();
	
	$('div[data-container-for="status"]').remove();
	$('label[for="status"]').closest('.k-edit-label').remove();
	
	$('div[data-container-for="configValueDropDown"]').hide();
	$('label[for="configValueDropDown"]').closest('.k-edit-label').hide();
	
	$('div[data-container-for="configValue"]').hide();
	$('label[for="configValue"]').closest('.k-edit-label').hide();
	
	/************************* Button Alerts *************************/
	
	if (e.model.isNew()) 
    {
		securityCheckForActions("./BillGeneration/billingSettings/create");
		setApCode = $('input[name="id"]').val();
		$(".k-window-title").text("Create Setting Name");
		$(".k-grid-update").text("Save");		
    }
	else{
		securityCheckForActions("./BillGeneration/billingSettings/update");
		var value = e.model.configName;
	
		if(value == 'Due Days')
		{
			  $('div[data-container-for="configValue"]').show();
			  $('label[for="configValue"]').closest('.k-edit-label').show();
			  
			  $('div[data-container-for="configValueDropDown"]').hide();
			  $('label[for="configValueDropDown"]').closest('.k-edit-label').hide();
		}
		else if(value == 'CAM Service Tax Amount'){
			
			$('div[data-container-for="configValue"]').show();
			  $('label[for="configValue"]').closest('.k-edit-label').show();
			  
			  $('div[data-container-for="configValueDropDown"]').hide();
			  $('label[for="configValueDropDown"]').closest('.k-edit-label').hide();
		}
	   else if(value == 'Show Particulars')
		{
		   $('div[data-container-for="configValue"]').hide();
		   $('label[for="configValue"]').closest('.k-edit-label').hide();
		   
		   $('div[data-container-for="configValueDropDown"]').show();
		   $('label[for="configValueDropDown"]').closest('.k-edit-label').show();
			
			var dropDownDataSource = [{
			     text : 'Select',
				 value : ""
				    },{
					   text : 'Yes',
					   value : "Yes"
					  },{
					   text : 'No',
					   value : "No"
						},];
			
			$("#configValueDropDown").kendoDropDownList({
		        dataSource    : dropDownDataSource,
		        dataTextField : 'text',
				dataValueField : 'value',
				change : function(e)
				{
					configValue = this.value();
					$("#configValue").val(configValue);
				}
		    });
		}
	   else if (value== 'CAM Charges')
		   {
		   $('div[data-container-for="configValue"]').hide();
		   $('label[for="configValue"]').closest('.k-edit-label').hide();
		   
		   $('div[data-container-for="configValueDropDown"]').show();
		   $('label[for="configValueDropDown"]').closest('.k-edit-label').show();
		   var dropDownDataSource = [ {
			     text : 'Select',
				 value : ""
				    },{
					   text : 'Flat',
					   value : "Flat"
					  },{
					   text : 'PSF',
					   value : "PSF"
						},];
			
			$("#configValueDropDown").kendoDropDownList({
		        dataSource    : dropDownDataSource,
		        dataTextField : 'text',
				dataValueField : 'value',
				change : function(e)
				{
					configValue = this.value();
					$("#configValue").val(configValue);
				}
		    });
		   }
	   else if (value== 'Interest Calculation')
	   {
		   $('div[data-container-for="configValue"]').hide();
		   $('label[for="configValue"]').closest('.k-edit-label').hide();
		   
		   $('div[data-container-for="configValueDropDown"]').show();
		   $('label[for="configValueDropDown"]').closest('.k-edit-label').show();
		   
	   var dropDownDataSource = [ {
		     text : 'Select',
			 value : ""
			    },{
				   text : 'Daywise',
				   value : "Daywise"
				  },{
				   text : 'Monthwise',
				   value : "Monthwise"
					},];
	   
		$("#configValueDropDown").kendoDropDownList({
	        dataSource    : dropDownDataSource,
	        dataTextField : 'text',
			dataValueField : 'value',
			change : function(e)
			{
				configValue = this.value();
				$("#configValue").val(configValue);
			}
	    });
	   }
	   else if (value== 'CAM Bill Generation')
	   {
		   $('div[data-container-for="configValue"]').hide();
		   $('label[for="configValue"]').closest('.k-edit-label').hide();
		   
		   $('div[data-container-for="configValueDropDown"]').show();
		   $('label[for="configValueDropDown"]').closest('.k-edit-label').show();
		   
	   var dropDownDataSource = [ {
		     text : 'Select',
			 value : ""
			    },{
				   text : 'Pre Bill Generation',
				   value : "Pre Bill Generation"
				  },{
				   text : 'Post Bill Generation',
				   value : "Post Bill Generation"
					},];
	   
		$("#configValueDropDown").kendoDropDownList({
	        dataSource    : dropDownDataSource,
	        dataTextField : 'text',
			dataValueField : 'value',
			change : function(e)
			{
				configValue = this.value();
				$("#configValue").val(configValue);
			}
	    });
	   }
		$(".k-window-title").text("Edit Setting Name");
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
						"Setting created successfully");
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
						"Setting deleted successfully");
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
						"Setting updated successfully");
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

		function configNameEditor(container, options) 
	   	{
			$('<input name="Name" id="configName" data-text-field="configName" data-value-field="configName" data-bind="value:' + options.field + '" required="true"/>')
			.appendTo(container).kendoComboBox({
				autoBind : false,
				placeholder : "Select Name",
				dataSource : {
					transport : {		
						read :  "${getConfigUrl}"
					}
				},
				change : function (e) {
		            if (this.value() && this.selectedIndex == -1) {                    
						alert("Name doesn't exist!");
		                $("#configName").data("kendoComboBox").value("");
		        	}
			    },
				select : function(e){
					var dataItem = this.dataItem(e.item.index());
					var value = dataItem.configName;
					if(value == 'Due Days')
					{
						  $('div[data-container-for="configValue"]').show();
						  $('label[for="configValue"]').closest('.k-edit-label').show();
						  
						  $('div[data-container-for="configValueDropDown"]').hide();
						   $('label[for="configValueDropDown"]').closest('.k-edit-label').hide();
					}
					else if(value == 'CAM Service Tax Amount'){
						
						$('div[data-container-for="configValue"]').show();
						  $('label[for="configValue"]').closest('.k-edit-label').show();
						  
						  $('div[data-container-for="configValueDropDown"]').hide();
						  $('label[for="configValueDropDown"]').closest('.k-edit-label').hide();
					}
				   else if(value == 'Show Particulars')
					{
					   $('div[data-container-for="configValue"]').hide();
					   $('label[for="configValue"]').closest('.k-edit-label').hide();
					   
					   $('div[data-container-for="configValueDropDown"]').show();
					   $('label[for="configValueDropDown"]').closest('.k-edit-label').show();
						
						var dropDownDataSource = [{
						     text : 'Select',
							 value : ""
							    },{
								   text : 'Yes',
								   value : "Yes"
								  },{
								   text : 'No',
								   value : "No"
									},];
						
						$("#configValueDropDown").kendoDropDownList({
					        dataSource    : dropDownDataSource,
					        dataTextField : 'text',
							dataValueField : 'value',
							change : function(e)
							{
								configValue = this.value();
								$("#configValue").val(configValue);
							}
					    });
					}
				   else if (value== 'CAM Charges')
					   {
					   $('div[data-container-for="configValue"]').hide();
					   $('label[for="configValue"]').closest('.k-edit-label').hide();
					   
					   $('div[data-container-for="configValueDropDown"]').show();
					   $('label[for="configValueDropDown"]').closest('.k-edit-label').show();
					   var dropDownDataSource = [ {
						     text : 'Select',
							 value : ""
							    },{
								   text : 'Flat',
								   value : "Flat"
								  },{
								   text : 'PSF',
								   value : "PSF"
									},];
						
						$("#configValueDropDown").kendoDropDownList({
					        dataSource    : dropDownDataSource,
					        dataTextField : 'text',
							dataValueField : 'value',
							change : function(e)
							{
								configValue = this.value();
								$("#configValue").val(configValue);
							}
					    });
					   }
				   else if (value== 'Interest Calculation')
				   {
					   $('div[data-container-for="configValue"]').hide();
					   $('label[for="configValue"]').closest('.k-edit-label').hide();
					   
					   $('div[data-container-for="configValueDropDown"]').show();
					   $('label[for="configValueDropDown"]').closest('.k-edit-label').show();
					   
				   var dropDownDataSource = [ {
					     text : 'Select',
						 value : ""
						    },{
							   text : 'Daywise',
							   value : "Daywise"
							  },{
							   text : 'Monthwise',
							   value : "Monthwise"
								},];
				   
					$("#configValueDropDown").kendoDropDownList({
				        dataSource    : dropDownDataSource,
				        dataTextField : 'text',
						dataValueField : 'value',
						change : function(e)
						{
							configValue = this.value();
							$("#configValue").val(configValue);
						}
				    });
				   }
				   else if (value== 'CAM Bill Generation')
				   {
					   $('div[data-container-for="configValue"]').hide();
					   $('label[for="configValue"]').closest('.k-edit-label').hide();
					   
					   $('div[data-container-for="configValueDropDown"]').show();
					   $('label[for="configValueDropDown"]').closest('.k-edit-label').show();
					   
				   var dropDownDataSource = [ {
					     text : 'Select',
						 value : ""
						    },{
							   text : 'Pre Bill Generation',
							   value : "CAM Bill Generation"
							  },{
							   text : 'Post Bill Generation',
							   value : "Post Bill Generation"
								},];
				   
					$("#configValueDropDown").kendoDropDownList({
				        dataSource    : dropDownDataSource,
				        dataTextField : 'text',
						dataValueField : 'value',
						change : function(e)
						{
							configValue = this.value();
							$("#configValue").val(configValue);
						}
				    });
				   }
					
					
				}  
			});
			
			$('<span class="k-invalid-msg" data-for="Name"></span>').appendTo(container);
	   	}
		
		function configValueEditor(container, options){
			  $('<input id="configValue" data-bind="value:' + options.field + '"/>')
		        .appendTo(container)
		        .kendoNumericTextBox({
		         spinners : false,
		         min : 0,
		        });
			 }
		function configValueDropDownEditor(container, options){
			  $('<input name="Value" id="configValueDropDown" data-bind="value:' + options.field + '"/>').appendTo(container);
			 }
		
		
		
	  $("#grid").on("click", "#temPID", function(e) {
		  
		  var button = $(this), enable = button.text() == "Activate";
		  var widget = $("#grid").data("kendoGrid");
		  var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
			  
		  var result=securityCheckForActionsForStatus("./BillGeneration/billingSettings/status"); 
			if(result=="success"){  
				if (enable) 
				{
					$.ajax
					({
						type : "POST",
						url : "./billingSettings/billingSettingsStatus/" +dataItem.id+ "/activate",
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
						url : "./billingSettings/billingSettingsStatus/" + dataItem.id + "/deactivate",
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
	//register custom validation rules
	(function ($, kendo) 
		{   	  
	    $.extend(true, kendo.ui.validator, 
	    {
	         rules: 
	         { // custom rules
	        	 
	        configValueRequired : function(input, params){
               if (input.attr("name") == "configValue")
               {
                return $.trim(input.val()) !== "";
               }
               return true;
              },
	         },
	         messages: 
	         {
				//custom rules messages
				configValueRequired : "Value is required",
	     	 }
	    });
	    
	})(jQuery, kendo);
	  //End Of Validation
</script>
