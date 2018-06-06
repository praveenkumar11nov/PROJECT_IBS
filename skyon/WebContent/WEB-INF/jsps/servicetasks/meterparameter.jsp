<%@include file="/common/taglibs.jsp"%>

	<c:url value="/common/getAllChecks" var="allChecksUrl" />
	<c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />
	<c:url value="/meterparameter/read" var="readUrl" />
	<c:url value="/meterparameter/saveorupadteordelete" var="serviceSaveOrUpdateOrDelete" />
	<c:url value="/meterparameter/meterParameterDestroy" var="meterParameterDestroyUrl"/>
	
	<!-- Filter URL's Of Meter Parameter Master -->
    <c:url value="/meterParameterMaster/filter" var="commonFilterForMeterParameterMasterUrl" />
    <c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />
	
	<kendo:grid name="grid" remove="meterParameterMasterDeleteEvent" edit="meterParameterMasterEdit" pageable="true" scrollable="true"
		filterable="true" sortable="true" reorderable="true" groupable="true" selectable="true"
		resizable="true">
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10"
			input="true" numeric="true"></kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
			<kendo:grid-filterable-operators>
				<kendo:grid-filterable-operators-string eq="Is equal to" />
			</kendo:grid-filterable-operators>
		</kendo:grid-filterable>
		<kendo:grid-editable mode="popup"/>
		<kendo:grid-toolbar>

			<kendo:grid-toolbarItem name="create" text="Enter Meter Parameter" />
			 <kendo:grid-toolbarItem name="meterTemplatesDetailsExport" text="Export To Excel" />
			  <kendo:grid-toolbarItem name="meterPdfTemplatesDetailsExport" text="Export To PDF" />
			<kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterMeterParameterMasters()>Clear Filter</a>"/>
		</kendo:grid-toolbar>
		
		<kendo:grid-columns>
			<kendo:grid-column title="MPM Id" field="mpmId" width="1px"	hidden="true">
			</kendo:grid-column>
			
			<kendo:grid-column title="Service&nbsp;Type&nbsp;*" field="mpmserviceType"	editor="dropDownChecksEditor" width="120px">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function apCodeFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Servicetype",
									dataSource : {
										transport : {
											read : "${commonFilterForMeterParameterMasterUrl}/mpmserviceType"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="Sequence&nbsp;*" field="mpmSequence" format="{0:n0}" width="80px">
			</kendo:grid-column>
			
			<kendo:grid-column title="Data&nbsp;Type&nbsp;*" field="mpmDataType" editor="dropDownChecksEditor" width="120px">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function apCodeFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Data Type",
									dataSource : {
										transport : {
											read : "${commonFilterForMeterParameterMasterUrl}/mpmDataType"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="Parameter&nbsp;Name&nbsp;*" field="mpmName" width="120px">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function apCodeFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Data Type",
									dataSource : {
										transport : {
											read : "${commonFilterForMeterParameterMasterUrl}/mpmName"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="Description" field="mpmDescription" width="140px" editor="serviceDescriptionEditor">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function apCodeFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Parameter Name",
									dataSource : {
										transport : {
											read : "${commonFilterForMeterParameterMasterUrl}/mpmName"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			
		 <kendo:grid-column title="Status" field="status" width="140px">
				<kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function ledgerStatusFilter(element) {
								element
										.kendoDropDownList({
											optionLabel : "Select status",
											dataSource : {
												transport : {
													read : "${filterDropDownUrl}/servicePointsStatus"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="Created By" field="createdBy" hidden="true"  width="95px">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function propertyNumberFilter(element) {
								element.kendoAutoComplete({
									dataType : 'JSON',
									dataSource : {
										transport : {
											read : "${whomCreated}"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="Last Updated By" field="lastupdatedBy" hidden="true"
				width="120px">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function propertyNumberFilter(element) {
								element.kendoAutoComplete({
									dataType : 'JSON',
									dataSource : {
										transport : {
											read : "${whomLastUpadted}"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="&nbsp;" width="140px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit" />
					<kendo:grid-column-commandItem name="destroy"/>	
				</kendo:grid-column-command>
			</kendo:grid-column>
			
			<kendo:grid-column title=""
    template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Active#=data.mpmId#'>#= data.status == 'Active' ? 'In-active' : 'Active' #</a>"
    width="90px" />
		</kendo:grid-columns>
		
		<kendo:dataSource requestStart="onRequestStartServiceMaster" requestEnd="onRequestEndServiceMaster">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-read url="${readUrl}" dataType="json"
					type="POST" contentType="application/json" />
				<kendo:dataSource-transport-create url="${serviceSaveOrUpdateOrDelete}/create"
					dataType="json" type="GET" contentType="application/json" />
				 <kendo:dataSource-transport-update url="${serviceSaveOrUpdateOrDelete}/update"
					dataType="json" type="GET" contentType="application/json" />
				<kendo:dataSource-transport-destroy url="${meterParameterDestroyUrl}"
					dataType="json" type="GET" contentType="application/json" /> 
			</kendo:dataSource-transport>
			
			<kendo:dataSource-schema>
				<kendo:dataSource-schema-model id="mpmId">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="mpmId" type="number">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="mpmSequence"	type="number" defaultValue="">
							<kendo:dataSource-schema-model-field-validation min="1" />
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="mpmserviceType" type="string" >
							</kendo:dataSource-schema-model-field>
							
						<kendo:dataSource-schema-model-field name="status"  type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="mpmName" type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="mpmDataType"	type="string" >
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="createdBy" type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="lastUpdatedBy" type="string">
						</kendo:dataSource-schema-model-field>
								
						<kendo:dataSource-schema-model-field name="mpmDescription" type="string">
						</kendo:dataSource-schema-model-field>

					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>

	</kendo:grid>

<div id="alertsBox" title="Alert"></div>
<script>

$("#grid").on("click",".k-grid-meterTemplatesDetailsExport", function(e) {
	  window.open("./meterTemplate/meterTemplatesDetailsExport");
});
$("#grid").on("click",".k-grid-meterPdfTemplatesDetailsExport", function(e) {
	  window.open("./meterPdfTemplate/meterPdfTemplatesDetailsExport");
});

var meterParameterNameArray = [];

function parseMeterParameter(response) {   
    var data = response; 
    meterParameterNameArray = [];
	 for (var idx = 0, len = data.length; idx < len; idx ++) {
		var res1 = (data[idx].mpmName);
		meterParameterNameArray.push(res1);
	 }  
	 return response;
} 

function meterParameterMasterDeleteEvent(){
	securityCheckForActions("./Masters/MeterParameterMaster/destroyButton");
	var conf = confirm("Are u sure want to delete this parameter details?");
	 if(!conf){
	  $("#grid").data().kendoGrid.dataSource.read();
	   throw new Error('deletion aborted');
	 }
}

function clearFilterMeterParameterMasters()
{
   $("form.k-filter-menu button[type='reset']").slice().trigger("click");
   var gridServiceParameter = $("#grid").data("kendoGrid");
   gridServiceParameter.dataSource.read();
   gridServiceParameter.refresh();
}
	
	$("#grid").on("click", ".k-grid-Clear_Filter",
			function() {
				//custom actions

				$("form.k-filter-menu button[type='reset']").slice()
						.trigger("click");
				var gridServiceMaster = $("#grid").data("kendoGrid");
				gridServiceMaster.dataSource.read();
				gridServiceMaster.refresh();
	});
	
	 
	
   
	function serviceDescriptionEditor(container, options) {
		$('<textarea data-text-field="mpmDescription" name="mpmDescription" data-bind="value:' + options.field + '" style="width: 148px; height: 40px;"/>')
				.appendTo(container);
		$('<span class="k-invalid-msg" data-for="mpmDescription"></span>').appendTo(container);
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
	
	 
	function onRequestStartServiceMaster(e)
	{
		
			/* var gridStoreGoodsReturn = $("#grid").data("kendoGrid");
			gridStoreGoodsReturn.cancelRow();	 */	
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();
	}
	function onRequestEndServiceMaster(r) 
	{
		/* displayMessage(e, "grid", "Meter Parameter Master"); */
		if (typeof r.response != 'undefined') {
			if (r.response.status == "FAIL") {

				errorInfo = "";

				for (var s = 0; s < r.response.result.length; s++) {
					errorInfo += (s + 1) + ". "
							+ r.response.result[s].defaultMessage + "<br>";

				}

				if (r.type == "create") {

					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Creating the electricity meter details<br>" + errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});

				}
				if (r.type == "update") {
					//alert("Error: Updating the USER record\n\n" + errorInfo);
					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Updating the electricity meter details<br>" + errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
				}

				$('#grid').data('kendoGrid').refresh();
				$('#grid').data('kendoGrid').dataSource.read();
				return false;
			}
			
			if (r.response.status == "AciveParameterDestroyError") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Active parameter details cannot be deleted");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				$('#grid').data('kendoGrid').refresh();
				$('#grid').data('kendoGrid').dataSource.read();
			return false;
		}		
			if (r.response.status == "CHILD") {

				
					$("#alertsBox").html("");
					$("#alertsBox").html("Can't delete bill parameter details, child record found");
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
					$('#grid').data('kendoGrid').refresh();
					$('#grid').data('kendoGrid').dataSource.read();
				return false;
			}

			else if (r.response.status == "INVALID") {

				errorInfo = "";

				errorInfo = r.response.result.invalid;

				if (r.type == "create") {
					$("#alertsBox").html("");
					$("#alertsBox").html("Error: Creating the electricity meter details<br>" + errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
				}
				$('#grid').data('kendoGrid').refresh();
				$('#grid').data('kendoGrid').dataSource.read();
				return false;
			}

			else if (r.response.status == "EXCEPTION") {

				errorInfo = "";

				errorInfo = r.response.result.exception;

				if (r.type == "create") {
					
					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Creating the electricity meter details<br>" + errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
				}

				if (r.type == "update") {
					$("#alertsBox").html("");
					$("#alertsBox").html("Error: Updating the electricity meter details<br>" + errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
				}

				$('#grid').data('kendoGrid').refresh();
				$('#grid').data('kendoGrid').dataSource.read();
				return false;
			}

			else if (r.type == "create")
			{
				$("#alertsBox").html("");
				$("#alertsBox").html("Meter parameter details created successfully");
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

			else if (r.type == "update") {
				
				$("#alertsBox").html("");
				$("#alertsBox").html("Meter parameter details updated successfully");
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
			
			else if (r.type == "destroy") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Meter parameter details deleted successfully");
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
	
	var setApCode="";
	var flagMpmName = "";
	function meterParameterMasterEdit(e)
	{
		/***************************  to remove the id from pop up  **********************/
		$('div[data-container-for="mpmId"]').remove();
		$('label[for="mpmId"]').closest('.k-edit-label').remove();
		
		$(".k-edit-field").each(function () {
			$(this).find("#temPID").parent().remove();  
	   	});
		
		$('div[data-container-for="lastUpdatedDT"]').remove();
		$('label[for="lastUpdatedDT"]').closest('.k-edit-label').remove();
		
		$('div[data-container-for="lastupdatedBy"]').remove();
		$('label[for="lastupdatedBy"]').closest('.k-edit-label').remove();
		
		$('div[data-container-for="createdBy"]').remove();
		$('label[for="createdBy"]').closest('.k-edit-label').remove();
		
		$('div[data-container-for="status"]').remove();
		$('label[for="status"]').closest('.k-edit-label').remove();
		
		/************************* Button Alerts *************************/
		if (e.model.isNew()) 
	    {
			securityCheckForActions("./Masters/MeterParameterMaster/createButton");
			flagMpmName=true;
			setApCode = $('input[name="mpmId"]').val();
			$(".k-window-title").text("Add Meter Parameter Details");
			$(".k-grid-update").text("Save");		
	    }
		else{
			flagMpmName=false;
			
			$('div[data-container-for="mpmName"]').remove();
			$('label[for="mpmName"]').closest('.k-edit-label').remove();
			
			securityCheckForActions("./Masters/MeterParameterMaster/updateButton");
			$(".k-window-title").text("Edit Meter Parameter Details");
		}
		
		var billUniqueParamName=[];
		$(".k-grid-update").click(function () {
				var mpmserviceType = $("input[name=mpmserviceType]").data("kendoDropDownList").text();
			 	var mpmName = $('input[name="mpmName"]').val();

			 	$.ajax({
				     url:'./billingparameter/getUniqueParamName/'+"MeterParameterMaster/"+"mpmName/"+"mpmserviceType/"+mpmserviceType,
				     async: false,
				     success: function (response) {
				    	 
				    	 for(var i = 0; i<response.length; i++) {
				    		 billUniqueParamName[i] = response[i];
						} 
				    }
				     
				});
			 	
			 	if((mpmName)!=null || (mpmName)!=""){
			 	   for(var i = 0; i<billUniqueParamName.length; i++){
						   if(mpmName==billUniqueParamName[i]){
							   alert("Parameter name already exist");
							     return false;

						   }
						}
				   }
			 	
			}); 

	}
	 $("#grid").on("click", "#temPID", function(e) {
		  var button = $(this), enable = button.text() == "Active";
		  var widget = $("#grid").data("kendoGrid");
		  var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
		  var result=securityCheckForActionsForStatus("./Masters/MeterParameterMaster/activeInactiveButton"); 
		  if(result=="success"){  
		     if (enable) 
		     {
		      $.ajax
		      ({
		       type : "POST",
		       url : "./meterParameterMaster/serviceMaster/" + dataItem.id + "/activate",
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
		        button.text('In-active');
		        $('#grid').data('kendoGrid').dataSource.read();
		       }
		      });
		     } 
		     else 
		     {
		      $.ajax
		      ({
		       type : "POST",
		       url : "./meterParameterMaster/serviceMaster/" + dataItem.id + "/deactivate",
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
		        button.text('Active');
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
		        	 
	           serviceTypeRequired : function(input, params){
	               if (input.attr("name") == "mpmserviceType")
	               {
	                return $.trim(input.val()) !== "";
	               }
	               return true;
	              },
	              sequenceRequired : function(input, params){
	                     if (input.attr("name") == "mpmSequence")
	                     {
	                      return $.trim(input.val()) !== "";
	                     }
	                     return true;
	                    },
	   	          DataTypeRequired : function(input, params){
	                  if (input.attr("name") == "mpmDataType")
	                  {
	                   return $.trim(input.val()) !== "";
	                  }
	                  return true;
	                 },
	                 bvmNameRequired : function(input, params){
		                  if (input.attr("name") == "mpmName")
		                  {
		                   return $.trim(input.val()) !== "";
		                  }
		                  return true;
		                 },
		                 sequenceLengthValidation: function (input, params)
		                 {
		           	  if (input.filter("[name='mpmSequence']").length && input.val() != "") 
		                 {
		           			return /^[0-9]{1,2}$/.test(input.val());
		                 }        
		                 return true;
		                    },
		              parameterNameLengthValidation : function (input, params) 
		                    {         
		                        if (input.filter("[name='mpmName']").length && input.val() != "") 
		                        {
		                       	 return /^[a-zA-Z ]{1,45}$/.test(input.val());
		                        }        
		                        return true;
		                    },
		              parameterDescLengthValidation: function (input, params)
		                    {
		              	  if (input.filter("[name='mpmDescription']").length && input.val() != "") 
		                    {
		                   	 return /^[\s\S]{1,500}$/.test(input.val());
		                    }        
		                    return true;
		                       },  
		             serviceParameterNameUniquevalidation : function(input, params){
									if(flagMpmName){
										if (input.filter("[name='mpmName']").length && input.val()) 
										{
											var flag = true;
											$.each(meterParameterNameArray, function(idx1, elem1) {
												if(elem1.toLowerCase() == input.val().toLowerCase())
												{
													flag = false;
												}	
											});
											return flag;
										}
									}
									return true;
								},    
		         },
		         messages: 
		         {
					//custom rules messages
					serviceTypeRequired : "Service type is required",
					sequenceRequired : "Sequence is required",
					DataTypeRequired : "Data type is required",
					bvmNameRequired : "Parameter name is required",
					sequenceLengthValidation : "Sequence max length 2 digit number only",
					parameterNameLengthValidation : "Name can contain alphabets,spaces and max 45 characters",
					parameterDescLengthValidation : "Description field allows max 500 characters",
					serviceParameterNameUniquevalidation : "Parameter name is already exist"
		     	 }
		    });
		    
		})(jQuery, kendo);
		  //End Of Validation
</script>
