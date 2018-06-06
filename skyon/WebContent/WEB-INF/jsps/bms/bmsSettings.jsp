<%@include file="/common/taglibs.jsp"%>

<c:url value="/bmsSettings/readUrl" var="readUrl" />
<c:url value="/common/getAllChecks" var="allChecksUrl" />
<c:url value="/bmsSettings/getDesignation" var="designationUrl" />
<c:url value="/bmsSettings/getDepartment" var="departmentUrl" />
<c:url value="/bmsSettings/modifyBMSSettingsUrl" var="modifyBMSSettingsUrl" />

<!-- Filter Urls -->
<c:url value="/bmsSettings/departmentFilterUrl" var="departmentFilterUrl" />
<c:url value="/bmsSettings/designationFilterUrl" var="designationFilterUrl" />
<c:url value="/bmsSettings/commonFilterUrl" var="commonFilterUrl" />
<!--Uniqeness -->
<c:url value="/bmsSettings/readTrendLogNamesUniqe" var="readTrendLogNamesUniqe" />
<c:url value="/bmsSettings/readTrendLogIdsUniqe" var="readTrendLogIdsUniqe" />


<div id="loading"></div>
<kendo:grid name="bmsSettingsGrid" pageable="true" edit="bmsSettingsEvent" 
		resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true" change="onChangeBMSSettings"
		filterable="true" groupable="true" >
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" refresh="true" info="true" previousNext="true">
			<kendo:grid-pageable-messages itemsPerPage="BMS Settings per page" empty="No BMS Settings to display" refresh="Refresh all the BMS Settings" 
			display="{0} - {1} of {2}  BMS Settings" first="Go to the first page of BMS Settings" last="Go to the last page of BMS Settings" next="Go to the next page of BMS Settings"
			previous="Go to the previous page of BMS Settings"/>
		</kendo:grid-pageable> 
		
		<kendo:grid-editable mode="popup"/>
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add BMS Settings" />
			<kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterBMSSettings()><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>"/>
		</kendo:grid-toolbar>
			<kendo:grid-filterable extra="false">
		 <kendo:grid-filterable-operators>
		  	<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains"/>
		 </kendo:grid-filterable-operators>
		</kendo:grid-filterable>
		
		
		
		
<kendo:grid-columns>
	
				
		<kendo:grid-column title="TrendLog&nbsp;Name*" field="bmsElements" editor="bmsSettingsDropDownEditor" width="85px">
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function rateDescriptionFilter(element) {
							element.kendoAutoComplete({
										placeholder : "Enter Description",
										dataType : 'JSON',
										dataSource : {
											transport : {
												read : "${commonFilterUrl}/bmsElements"
											}
										}
									});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>
		
				
				
			<kendo:grid-column title="Department&nbsp;*" field="dept_Name" 
				editor="departmentEditor" 
				width="90px">
				<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    				
					  <script>
						function rateDescriptionFilter(element) {
							element.kendoAutoComplete({
										placeholder : "Enter Department",
										dataType : 'JSON',
										dataSource : {
											transport : {
												read : "${departmentFilterUrl}"
											}
										}
									});
						}
					</script>	
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	    	
			</kendo:grid-column>
				
				
			<kendo:grid-column title="Designation&nbsp;*" field="dn_Name" editor="designationEditor" 
				width="90px">
				<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
    					function rateDescriptionFilter(element) {
							element.kendoAutoComplete({
										placeholder : "Enter Designation",
										dataType : 'JSON',
										dataSource : {
										transport : {
											read : "${designationFilterUrl}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	    	
			</kendo:grid-column>
			
			<kendo:grid-column title="TrendLog&nbsp;Id&nbsp;*" field="paramValue" filterable="false" width="60px"/>
			
			
			
			<kendo:grid-column title="Status" field="status" width="70px">
				 <kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function rateDescriptionFilter(element) {
							element.kendoAutoComplete({
										dataType : 'JSON',
										dataSource : {
											transport : {
												read : "${commonFilterUrl}/status"
											}
										}
									});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>   	
			</kendo:grid-column>
			
			
		<kendo:grid-column title=" " width="50px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit"/>			
				</kendo:grid-column-command>			
		</kendo:grid-column>	
				
				
		
				
		<kendo:grid-column title=""
			template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Active#=data.bmsSettingsId#'>#= data.status == 'Active' ? 'Inactive' : 'Active' #</a>"
			width="70px" />	
			
		</kendo:grid-columns>
		
		<kendo:dataSource pageSize="20" requestEnd="onRequestEnd" requestStart="onRequestStart">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-create url="${modifyBMSSettingsUrl}/create" dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-update url="${modifyBMSSettingsUrl}/update" dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-read url="${readUrl}" dataType="json"
					type="POST" contentType="application/json" />
				<kendo:dataSource-transport-parameterMap>
					<script>
						function parameterMap(options, type) {
							return JSON.stringify(options);
						}
					</script>
				</kendo:dataSource-transport-parameterMap>
			</kendo:dataSource-transport>
			
			<kendo:dataSource-schema>
				<kendo:dataSource-schema-model id="bmsSettingsId">
					<kendo:dataSource-schema-model-fields>
								<kendo:dataSource-schema-model-field name="bmsSettingsId" type="number"/>
								<kendo:dataSource-schema-model-field name="bmsElements" type="string"/>
								<kendo:dataSource-schema-model-field name="paramValue"/>
								<kendo:dataSource-schema-model-field name="dept_Name" type="string"/>
						        <kendo:dataSource-schema-model-field name="dn_Name" type="string"/>
   								<kendo:dataSource-schema-model-field name="dept_Id" type="number"/>
   								<kendo:dataSource-schema-model-field name="dn_Id" type="number"/>
						        <kendo:dataSource-schema-model-field name="status" type="string"/>
						        
							</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
			
			</kendo:dataSource>
		
</kendo:grid>
<div id="alertsBox" title="Alert"></div>


<script type="text/javascript">
var res1 = new Array();
var bmsElement;
var paramValue;
var SelectedRowId = "";
function onChangeBMSSettings(arg) {
	var gview = $("#bmsSettingsGrid").data("kendoGrid");
	var selectedItem = gview.dataItem(gview.select());
	SelectedRowId = selectedItem.bmsSettingsId;
}


function clearFilterBMSSettings()
{
   $("form.k-filter-menu button[type='reset']").slice().trigger("click");
   var gridStoreIssue = $("#bmsSettingsGrid").data("kendoGrid");
   gridStoreIssue.dataSource.read();
   gridStoreIssue.refresh();
}



function bmsSettingsEvent(e) {

	/***************************  to remove the id from pop up  **********************/
	$('div[data-container-for="bmsSettingsId"]').remove();
	$('label[for="bmsSettingsId"]').closest('.k-edit-label').remove();

	{$('label[for="status"]').parent().remove();
	$('div[data-container-for="status"]').remove();

	$(".k-edit-field").each(function() {
		$(this).find("#temPID").parent().remove();
	});

	/************************* Button Alerts *************************/
	
	if (e.model.isNew()) 
	{
		  res1 = [];
			 $.ajax
			 ({
			      type : "GET",
				  dataType:"text",
				  url : '${readTrendLogNamesUniqe}',
				  dataType : "JSON",
				  success : function(response) 
				  {
					 for(var i = 0; i<response.length; i++) 
					 {
					   res1[i] = response[i];	
				     }
				  }
			  });
			 
			 res2 = [];
			 $.ajax
			 ({
			      type : "GET",
				  dataType:"text",
				  url : '${readTrendLogIdsUniqe}',
				  dataType : "JSON",
				  success : function(response) 
				  {
					 for(var i = 0; i<response.length; i++) 
					 {
					   res2[i] = response[i];	
				     }
				  }
			  });
			 
		$(".k-window-title").text("Add BMS Settings");
		$(".k-grid-update").text("Save");

	} else 
	{
		   var gview = $("#bmsSettingsGrid").data("kendoGrid");
		   var selectedItem = gview.dataItem(gview.select());
		   bmsElement = selectedItem.bmsElements;
		   paramValue= selectedItem.paramValue;
		 
		   
		
		res1 = [];
		   $.ajax({
		    type : "GET",
		    dataType : "text",
		    url : '${readTrendLogNamesUniqe}',
		    dataType : "JSON",
		    success : function(response) {
		     var j = 0;
		     for (var i = 0; i < response.length; i++) {
		      if (response[i] != bmsElement) {

		       res1[j] = response[i];
		       j++;
		      }
		     }
		    }
		   });
		   
		   res2 = [];
		   $.ajax({
		    type : "GET",
		    dataType : "text",
		    url : '${readTrendLogIdsUniqe}',
		    dataType : "JSON",
		    success : function(response) {
		     var j = 0;
		     for (var i = 0; i < response.length; i++) {
		      if (response[i] != paramValue) {

		       res2[j] = response[i];
		       j++;
		      }
		     }
		    }
		   });
		   
		$(".k-window-title").text("Edit BMS Settings");
		$(".k-grid-update").text("Update");

	}
	
}
}


$("#bmsSettingsGrid").on(
		"click",
		"#temPID",
		function(e) {
			var button = $(this), enable = button.text() == "Active";
			var widget = $("#bmsSettingsGrid").data("kendoGrid");
			var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
			$('tr[aria-selected="true"]').find('td:nth-child(6)').html("");
			$('tr[aria-selected="true"]').find('td:nth-child(6)').html("<img src='./resources/images/progress.gif' width='100px' height='25px' />");
			if (enable) {
				$.ajax({
					type : "POST",
					url : "./bmsSettings/bmsSettingsStatus/" + SelectedRowId
							+ "/activate",
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
						button.text('Inactive');
						$('#bmsSettingsGrid').data('kendoGrid').dataSource
								.read();
					}
				});
			} else {
				$.ajax({
					type : "POST",
					url : "./bmsSettings/bmsSettingsStatus/" + SelectedRowId
							+ "/deactivate",
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
						button.text('Active');
						$('#bmsSettingsGrid').data('kendoGrid').dataSource
								.read();
					}
				});
			}
		
		});


/* function departmentEditor(container, options) {
	  $('<input name = "departmant" data-text-field="dept_Name" id="dept_Id" data-value-field="dept_Id" data-bind="value:' + options.field + '" required = "required"/>')
	     		.appendTo(container).kendoDropDownList({
	     			optionLabel: "Select",
	     			autoBind:false,
					dataSource : {
						transport : {		
							read : "${departmentUrl}"
						}
					}			
		});
}	


function designationEditor(container, options) {
	  $('<input data-text-field="dn_Name" id = "dn_Id" data-value-field="dn_Id" data-bind="value:' + options.field + '"/>')
		.appendTo(container).kendoDropDownList({
			optionLabel: "Select",
			cascadeFrom: "dept_Id", 											
			dataSource : {
				transport : {

					read : "${designationUrl}"
					
				}
			}
			
	});
}
 */

function departmentEditor(container, options) 
	{
	$('<input name="Department Name" id="blockId" data-text-field="dept_Name" data-value-field="dept_Id" data-bind="value:' + options.field + '" required="true"/>')
	.appendTo(container).kendoComboBox({
		autoBind : false,			
		dataSource : {
			transport : {		
				read :  "${departmentUrl}"
			}
		},
		change : function (e) {
            if (this.value() && this.selectedIndex == -1) {                    
				alert("Department Name doesn't exist!");
                $("#blockId").data("kendoComboBox").value("");
        	}
    
	    }  
	});
	
	$('<span class="k-invalid-msg" data-for="Department Name"></span>').appendTo(container);
	}

function designationEditor(container, options) 
{
$('<input name="Designation Name" id="blockId" data-text-field="dn_Name" data-value-field="dn_Id" data-bind="value:' + options.field + '" required="true"/>')
.appendTo(container).kendoComboBox({
	cascadeFrom : "blockId", 
	autoBind : false,			
	dataSource : {
		transport : {		
			read :  "${designationUrl}"
		}
	},
	change : function (e) {
        if (this.value() && this.selectedIndex == -1) {                    
			alert("Designation Name doesn't exist!");
            $("#blockId").data("kendoComboBox").value("");
    	}

    }  
});

$('<span class="k-invalid-msg" data-for="Designation Name"></span>').appendTo(container);
}


function bmsSettingsDropDownEditor(container, options) {
	var res = (container.selector).split("=");
	var element = res[1].substring(0, res[1].length - 1);
	$(
			"<select name='Parameter' data-bind='value:" + element + "'required='true'/>")
			.appendTo(container).kendoDropDownList({
				dataTextField : "text",
				dataValueField : "value",
				placeholder : "Select Parameter",
				dataSource : {
					transport : {
						read : "${allChecksUrl}/" + element,
					}
				}
			});

	$('<span class="k-invalid-msg" data-for="Parameter"></span>').appendTo(
			container);
}



function onRequestEnd(e) {
	
	kendo.ui.progress($("#loading"), false);
	
	if (typeof e.response != 'undefined')
	{
		if (e.response.status == "FAIL") 
		{
			errorInfo = "";
			for (i = 0; i < e.response.result.length; i++) {
				errorInfo += (i + 1) + ". "
						+ e.response.result[i].defaultMessage + "<br>";
			}

			if (e.type == "create") {
				$("#alertsBox").html("");
	 					$("#alertsBox").html("Error: Creating the BMS Settings\n\n : " + errorInfo);
						$("#alertsBox").dialog({
							modal : true,
							buttons : {
								"Close" : function() {
									$(this).dialog("close");
								}
							}
						});
			}
			var grid = $("#bmsSettingsGrid").data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
		}

		 else if (e.type == "create") {
			 $("#alertsBox").html("");
					$("#alertsBox").html("BMS Settings created successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				
				var grid = $("#bmsSettingsGrid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
		 }
		 else if (e.type == "destroy") {
			 $("#alertsBox").html("");
					$("#alertsBox").html("BMS Settings  deleted successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				
				var grid = $("#bmsSettingsGrid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
		 }
		 else if (e.type == "update") {
			 $("#alertsBox").html("");
					$("#alertsBox").html("BMS Settings  updated successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				
				var grid = $("#bmsSettingsGrid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
		 }
		
	}
	
}


function onRequestStart(e){
	
	kendo.ui.progress($("#loading"), true);

	
	$('.k-grid-update').hide();
    $('.k-edit-buttons')
            .append(
                    '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
    $('.k-grid-cancel').hide();
	
}


(function($, kendo) {
	$
			.extend(
					true,
					kendo.ui.validator,
					{
						rules : {
							elemntNameValidater : function(input, params) {
								if (input.attr("name") == "Parameter") {
									return $.trim(input.val()) !== "Select";
								}
								return true;
							},
							bmsStatusValidation : function(input, params) {

								if (input.attr("name") == "Notified Status") {
									return $.trim(input.val()) !== "Select";
								}
								return true;
							},
							   
							trendLogNameUniqueness : function(input, params) {
									if (input.filter("[name='bmsElements']")) {
										var enterdService = input.val()
												.toUpperCase();
										
										for (var i = 0; i < res1.length; i++) {
											if ((enterdService == res1[i]
													.toUpperCase())
													&& (enterdService.length == res1[i].length)) {
												return false;
											}
										}
									}
									return true;
							},
							trendLogIdUniqueness : function(input, params) {
								if (input.filter("[name='paramValue']").length
										&& input.val()) {
									var enterdService = input.val();
										
									for (var i = 0; i < res2.length; i++) {
										if ((enterdService == res2[i])){
											return false;
										}
									}
								}
								return true;
						    },
						    
						    trendLogNumberValidator : function(input,params) { 
		    					  if (input.attr("name") == "paramValue") {
			  						     return /^[0-9]*$/.test(input.val());
		  						  }
	  				         return true;
	    		             }, 

							/*******  END FOR INNER GRID VALIODATION ***********/
							},
							//custom rules messages
							messages : {
								elemntNameValidater : "Parameter is not Selected",
								bmsStatusValidation : "BMS level is not Selected",
								trendLogNameUniqueness :"Trend Log Name already exists",
								trendLogIdUniqueness : "Trend Log Id already exists",
								trendLogNumberValidator : "Allow Numbers only",
							}
						});
	})(jQuery, kendo);
</script>