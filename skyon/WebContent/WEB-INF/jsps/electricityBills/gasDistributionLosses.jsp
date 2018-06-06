<%@include file="/common/taglibs.jsp"%>
<c:url value="/gasDistributionLosses/gasDistributionLossesCreate" var="createUrl" />
<c:url value="/gasDistributionLosses/gasDistributionLossesRead" var="readUrl" />
<c:url value="/gasDistributionLosses/gasDistributionLossesDestroy" var="destroyUrl" />
<c:url value="/gasDistributionLosses/gasDistributionLossesUpdate" var="updateUrl" />

<kendo:grid name="gasDistributionLossesGrid" remove="gasDistributionLossesDeleteEvent" pageable="true" resizable="true" edit="gasDistributionLossesEvent" sortable="true" reorderable="true" selectable="true" scrollable="true" filterable="false" groupable="true"
  dataBound="gasDistributionLossesDataBound" >
    <kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" refresh="true" info="true" previousNext="true">
	<kendo:grid-pageable-messages itemsPerPage="Status items per page" empty="No item to display" refresh="Refresh all the items" 
			display="{0} - {1} of {2} New Items" first="Go to the first page of items" last="Go to the last page of  items" next="Go to the next page of items"
			previous="Go to the previous page of items"/>
	</kendo:grid-pageable>
	<kendo:grid-filterable extra="false">
		<kendo:grid-filterable-operators>
			<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains"/>
			<kendo:grid-filterable-operators-date gt="Is after" lt="Is before"/>
		</kendo:grid-filterable-operators> 
	</kendo:grid-filterable>
	<kendo:grid-editable mode="popup"/>
		<kendo:grid-toolbar>
		      <kendo:grid-toolbarItem name="create" text="Create Distribution Losses" />
		      <kendo:grid-toolbarItem text="Clear_Filter" />
	    </kendo:grid-toolbar>
	<kendo:grid-columns>
	    
	    <kendo:grid-column title="gasDistributionLossesID" field="gdlid" width="70px" hidden="true" filterable="false" sortable="false" />
	    <kendo:grid-column title="Month&nbsp;*" field="month" format="{0:MMMM yyyy}" editor="monthEditor" width="70px" filterable="true"/>
	     <kendo:grid-column title="Sub&nbsp;Meter&nbsp;Reading&nbsp;*" field="subMeterSReading"  format="{0:#.00}" editor="subMeterReadingEditor" width="70px" filterable="false"/>
	    <kendo:grid-column title="Main&nbsp;Meter&nbsp;Reading&nbsp;*" field="mainMeterReading" editor="mainMeterReadingEditor" width="70px" filterable="false"/>	   
	    <kendo:grid-column title="Loss&nbsp;Units&nbsp;*" field="lossUnits" width="70px"  format="{0:#.00}" editor="lossEditor" filterable="false"/>
	    <kendo:grid-column title="Loss&nbsp;Percentage(%)&nbsp;*" field="lossPercentage"  format="{0:#.00}" editor="lossPercentageEditor"  width="70px" filterable="false" />
	    <kendo:grid-column title="Status&nbsp;*" field="status" width="70px" filterable="false"/>
	    
		<kendo:grid-column title=""
			template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Active'>#= data.status == 'Created' ? 'Approve' : 'Approved' #</a>"
			width="90px" />
	    
		<kendo:grid-column title="&nbsp;" width="160px">
			<kendo:grid-column-command>
			    <kendo:grid-column-commandItem name="edit"/>
				<kendo:grid-column-commandItem name="destroy" />
			</kendo:grid-column-command>
		</kendo:grid-column>
	</kendo:grid-columns>

	<kendo:dataSource pageSize="20" requestEnd="onRequestEnd">
		<kendo:dataSource-transport>
			<kendo:dataSource-transport-create url="${createUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json" />
			<kendo:dataSource-transport-update url="${updateUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-destroy url="${destroyUrl}/" dataType="json" type="GET" contentType="application/json" />
		</kendo:dataSource-transport>

		<kendo:dataSource-schema parse="parse">
			<kendo:dataSource-schema-model id="gdlid">
				<kendo:dataSource-schema-model-fields>
					<kendo:dataSource-schema-model-field name="gdlid" type="number"/>
					<kendo:dataSource-schema-model-field name="month" type="date"/>
					<kendo:dataSource-schema-model-field name="mainMeterReading" type="number" defaultValue=""/>
					<kendo:dataSource-schema-model-field name="subMeterSReading" type="number" />
					<kendo:dataSource-schema-model-field name="lossUnits" type="number" />
					<kendo:dataSource-schema-model-field name="lossPercentage" type="number" />
					<kendo:dataSource-schema-model-field name="status" type="string"/>
				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>

	</kendo:dataSource>

</kendo:grid>
<div id="alertsBox" title="Alert"></div>
<script>

var statusNameArray = [];
var los=0;
var losper=0;
var subMeter=0;
function parseMeterStatus(response) {   
	$.each(response, function(idx, elem) {
		if (elem.month && typeof elem.month === "string") {
			elem.month = kendo.parseDate(elem.month,"yyyy-MM-ddTHH:mm:ss.fffZ");
		}
	});
	return response;
} 

function gasDistributionLossesDeleteEvent(){
	securityCheckForActions("./Masters/MeterStatus/destroyButton");
	var conf = confirm("Are u sure want to delete this status name?");
	 if(!conf){
	  $('#gasDistributionLossesGrid').data().kendoGrid.dataSource.read();
	   throw new Error('deletion aborted');
	 }
}

function gasDistributionLossesDataBound(e) {
	
	var data = this.dataSource.view();
	var grid = $("#gasDistributionLossesGrid").data("kendoGrid");
	for (var i = 0; i < data.length; i++) {
		var currentUid = data[i].uid;
		row = this.tbody.children("tr[data-uid='" + data[i].uid + "']");
		var status = data[i].status;
		if (status == 'Approve') {
			var currenRow = grid.table.find("tr[data-uid='" + currentUid+ "']");
			var editButton = $(currenRow).find(".k-grid-edit");
			var activeButton = $(currenRow).find(".k-grid-Active");
			var deleteButton = $(currenRow).find(".k-grid-delete");
			deleteButton.hide();
			activeButton.hide();
			editButton.hide();
		}

	}
}

var setApCode="";		
function gasDistributionLossesEvent(e)
{
	/***************************  to remove the id from pop up  **********************/
	$('div[data-container-for="gdlid"]').remove();
	$('label[for="gdlid"]').closest('.k-edit-label').remove();
	
	$('div[data-container-for="status"]').remove();
	$('label[for="status"]').closest('.k-edit-label').remove();
	 $('a[id="temPID"]').remove();	 
	/************************* Button Alerts *************************/
	if (e.model.isNew()) 
    {
		$(".k-window-title").text("Create Gas Distribution Losses Name");
		$(".k-grid-update").text("Save");		
    }
	else{
		$(".k-window-title").text("Edit Gas Distribution Losses Name");
	}
	
	$(".k-grid-update").click(function() {
		e.model.set("subMeterSReading",subMeter);
		e.model.set("lossUnits",los);
		e.model.set("lossPercentage",losper);
	});
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
							"Error: Creating the Gas Distribution Losses\n\n : "
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
				var grid = $("#gasDistributionLossesGrid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			} 
			else if (e.type == "create") {
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Distribution Loss created successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});

				var grid = $("#gasDistributionLossesGrid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			} else if (e.type == "destroy") {
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Distribution Loss deleted successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});

				var grid = $("#gasDistributionLossesGrid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			} else if (e.type == "update") {
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Distribution Loss updated successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});

				var grid = $("#gasDistributionLossesGrid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			}
		}
	}

	$("#gasDistributionLossesGrid").on("click","#temPID",function(e) {
						var button = $(this),
						enable = button.text() == "Approve";
						var widget = $("#gasDistributionLossesGrid").data("kendoGrid");
						var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
/* 						var result = securityCheckForActionsForStatus("./BillGeneration/GenerateBills/approvePostButton");
						if (result == "success") { */
							if (enable) {
								$.ajax({
												type : "POST",
												url : "./gasDistributionLosses/setGasDistributionLossestatus/"+ dataItem.id+ "/Approve",
												dataType : "text",
												success : function(response) {
													$("#alertsBox").html("");
													$("#alertsBox").html(response);
													$("#alertsBox")
															.dialog({
																		modal : true,
																		buttons : {
																			"Close" : function() {
																				$(this).dialog("close");
																			}
																		}
																	});
													button.text('Poste');
													$('#gasDistributionLossesGrid').data('kendoGrid').dataSource.read();
												}
											});
							} 
							else 
							{   $.ajax({
											type : "POST",
											url : "./gasDistributionLosses/setGasDistributionLossestatus/"+ dataItem.id+ "/Approved",
											dataType : "text",
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
												$('#gasDistributionLossesGrid').data('kendoGrid').dataSource.read();
											}
										});
							}
						/* } */
					});
	
  function monthEditor(container, options){
		  $('<input id="month" name="month"  data-bind="value:' + options.field + '"/>')
	        .appendTo(container)
	        .kendoDatePicker({
	        	format: "MMMM yyyy",
	        	change : function() {	           
		             onChangeMonth();
		         }
	         
	        });
		  $('<span class="k-invalid-msg" data-for="month"></span>').appendTo(container);
		 }
	function onChangeMonth(){
		var month=$("input[name=month]").val();		
		$.ajax({
			type : "GET",
			url : "./gasDistributionLosses/getSubMeterReading/"+month,
			dataType : "json",			
			success : function(response) {
				if(response.status=='Approve'){
					alert("For "+month+" record has been already created");
				}else if(response.status=='ADD'){
					$("input[name=subMeterSReading]").data("kendoNumericTextBox").value(response.totalUnit);			
					subMeter=(response.totalUnit);
				}else{
					alert("For "+month+" record has been already created");	
				}
			}
		}); 
	} 
	 
    function subMeterReadingEditor(container, options){
		  $('<input id="subMeterSReading" name="subMeterSReading" readonly="true" data-bind="value:' + options.field + '"  />')
	        .appendTo(container)
	        .kendoNumericTextBox({
	         spinners : false,
	         min : 0,
	         
	        });
		 
		 }
	function lossEditor(container, options){
		  $('<input id="lossUnits" name="lossUnits" readonly="true" data-bind="value:' + options.field + '"/>')
	        .appendTo(container)
	        .kendoNumericTextBox({
	         spinners : false,
	         min : 0,
	         
	        });
		 
		 }
	function lossPercentageEditor(container, options){
		  $('<input id="lossPercentage" name="lossPercentage" readonly="true"  data-bind="value:' + options.field + '"/>')
	        .appendTo(container)
	        .kendoNumericTextBox({
	         spinners : false,
	         min : 0,
	         
	        });
		 
		 } 
	
	function mainMeterReadingEditor(container, options){
		  $('<input id="mainMeterReading" name="mainMeterReading" data-bind="value:' + options.field + '" required="true"/>')
	        .appendTo(container)
	        .kendoNumericTextBox({
	         spinners : false,
	         min : 0,
	         change : function() {	           
	             onChangeMainMeterReading();
	         }
	        });
		  $('<span class="k-invalid-msg" data-for="mainMeterReading"></span>').appendTo(container);
		 }
	
	function onChangeMainMeterReading(){		
	 var mainMeterReading=$("#mainMeterReading").val();
	 
      
		$.ajax({
			type : "GET",
			url : "./gasDistributionLosses/getLossInDistibution/"+mainMeterReading+"/"+subMeter,
			dataType : "json",			
			success : function(response) {
				/* $("input[name=subMeterSReading]").data("kendoNumericTextBox").value(response.totalUnit); */
				$("input[name=lossUnits]").data("kendoNumericTextBox").value(response.loss);
				$("input[name=lossPercentage]").data("kendoNumericTextBox").value(response.lossPercentage);
				los=(response.loss);
				losper=(response.lossPercentage);
				
			}
		}); 
	}
	
	//register custom validation rules
	(function ($, kendo) 
		{   	  
	    $.extend(true, kendo.ui.validator, 
	    {
	         rules: 
	         { 
	         monthRequired : function(input, params){
               if (input.attr("name") == "month")
               {
                return $.trim(input.val()) !== "";
               }
               return true;
              },
              mainMeterReadingValidation : function (input, params) 
	                    {         
	                        if (input.filter("[name='mainMeterReading']").length && input.val() != "") 
	                        {
	                        	 return $.trim(input.val()) !== "";
	                        }        
	                        return true;
	                    },
	         },
	         messages: 
	         {
				monthRequired : "Month is required",
				mainMeterReadingValidation : "Main meter reading is required",
	     	 }
	    });
	    
	})(jQuery, kendo);
	
	function parse(response) {
		$.each(response, function(idx, elem) {
			if (elem.month === null) {
				elem.month = "";
			} else {
				elem.month = kendo.parseDate(new Date(elem.month),'dd/MM/yyyy HH:mm');
			}
		});
		return response;
	}
	  //End Of Validation
</script>
