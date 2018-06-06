<%@include file="/common/taglibs.jsp"%>

<c:url value="/interestSettings/interestSettingsCreate" var="createUrl" />
<c:url value="/interestSettings/interestSettingsRead" var="readUrl" />

<c:url value="/common/getAllChecks" var="allChecksUrl" />

<kendo:grid name="grid" remove="interestSettingsDeleteEvent" pageable="true" resizable="true" edit="interestSettingEvent" sortable="true" reorderable="true" selectable="true" scrollable="true" filterable="false" groupable="true">

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
		      <kendo:grid-toolbarItem name="create" text="Add Details" />
	    </kendo:grid-toolbar>
	<kendo:grid-columns>
	    
	    <kendo:grid-column title="settingId" field="settingId" width="70px" hidden="true" filterable="false" sortable="false" />
	    
	    <kendo:grid-column title="Interest&nbsp;Based&nbsp;On&nbsp;*" field="interestBasedOn" editor="interestTypeEditor" width="70px" filterable="false">
	    </kendo:grid-column>

		<%-- <kendo:grid-column title="&nbsp;" width="160px">
			<kendo:grid-column-command>
			    <kendo:grid-column-commandItem name="edit"/>
				<kendo:grid-column-commandItem name="destroy" />
			</kendo:grid-column-command>
		</kendo:grid-column> --%>
	</kendo:grid-columns>

	<kendo:dataSource pageSize="20" requestEnd="onRequestEnd">
		<kendo:dataSource-transport>
			<kendo:dataSource-transport-create url="${createUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json" />
			<kendo:dataSource-transport-update url="${updateUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-destroy url="${destroyUrl}/" dataType="json" type="GET" contentType="application/json" />
		</kendo:dataSource-transport>

		<kendo:dataSource-schema>
			<kendo:dataSource-schema-model id="settingId">
				<kendo:dataSource-schema-model-fields>
					<kendo:dataSource-schema-model-field name="settingId" type="number"/>
					
					<kendo:dataSource-schema-model-field name="interestBasedOn" type="string" defaultValue="Flat Rate">
					</kendo:dataSource-schema-model-field>

				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>

	</kendo:dataSource>

</kendo:grid>
<div id="alertsBox" title="Alert"></div>
<script>

function interestTypeEditor(container, options) {
	$('<input type="radio" name=' + options.field + ' value="Flat Rate" checked="true" /> Flat&nbsp;Rate <br> <input type="radio" name=' + options.field + ' value="Number Of Days"/> Number&nbsp;Of&nbsp;Days <br>')
		.appendTo(container);
}

function dropDownChecksEditor(container, options) {
	var res = (container.selector).split("=");
	var attribute = res[1].substring(0,res[1].length-1);
	
	$('<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '" name = "'+attribute+'" id = "'+attribute+'Id"/>')
			.appendTo(container).kendoDropDownList({
				optionLabel : {
					text : "Select",
					value : "",
				},
				defaultValue : false,
				sortable : true,
				dataSource : {
					transport : {
						read : "${allChecksUrl}/"+attribute,
					}
				}
			});
	 $('<span class="k-invalid-msg" data-for="'+attribute+'"></span>').appendTo(container);
}

function interestSettingsDeleteEvent(){
	//securityCheckForActions("./Masters/MeterStatus/destroyButton");
	var conf = confirm("Are u sure want to delete this item?");
	 if(!conf){
	  $('#grid').data().kendoGrid.dataSource.read();
	   throw new Error('deletion aborted');
	 }
}


var setApCode="";		
function interestSettingEvent(e)
{
	/***************************  to remove the id from pop up  **********************/
	$('div[data-container-for="settingId"]').remove();
	$('label[for="settingId"]').closest('.k-edit-label').remove();
		
	/************************* Button Alerts *************************/
	if (e.model.isNew()) 
    {
		securityCheckForActions("./Masters/InterestSettings/createButton");
		setApCode = $('input[name="settingId"]').val();
		$(".k-window-title").text("Create Interest Calculation Based Details");
		$(".k-grid-update").text("Save");		
    }
	else{
		//securityCheckForActions("./Masters/MeterStatus/updateButton");
		$(".k-window-title").text("Edit Interest Calculation Based Details");
	}
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
						"Interest setting details created successfully");
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
						"Status name is deleted successfully");
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
						"Status name is updated successfully");
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
	
	//register custom validation rules
	(function ($, kendo) 
		{   	  
	    $.extend(true, kendo.ui.validator, 
	    {
	         rules: 
	         { // custom rules
	        	 
	          interestBasedOnRequired : function(input, params){
               if (input.attr("name") == "interestBasedOn")
               {
                return $.trim(input.val()) !== "";
               }
               return true;
              }
	         },
	         messages: 
	         {
				//custom rules messages
				interestBasedOnRequired : "Interest based on is required",
	     	 }
	    });
	    
	})(jQuery, kendo);
	  //End Of Validation
</script>
