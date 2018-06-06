<%@include file="/common/taglibs.jsp"%>
	<c:url value="/servicetasks/read" var="readUrl" />
	<c:url value="/common/getAllChecks" var="allChecksUrl" />
	<c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />
	<c:url value="/servicetasks/saveorupadteordelete" var="serviceSaveOrUpdateOrDelete" />
	<c:url value="/servicetasks/serviceParameterDestroy" var="serviceParameterDestroyUrl"/>
	
	<!-- Filter URL's Of Service Parameter Master -->
    <c:url value="/serviceParameterMaster/filter" var="commonFilterForServiceParameterMasterUrl" />
    <c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />
	
	<kendo:grid name="grid" remove="serviceParameterMasterDeleteEvent" edit="serviceParameterMasterEdit" pageable="true" scrollable="true"
		filterable="true" sortable="true" reorderable="true" groupable="true" selectable="true"
		resizable="true">
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10"
			input="true" numeric="true"></kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
			<kendo:grid-filterable-operators>
				<kendo:grid-filterable-operators-string eq="Is equal to" />
			</kendo:grid-filterable-operators>
		</kendo:grid-filterable>
		<kendo:grid-editable mode="popup" />
		<kendo:grid-toolbar>

			<kendo:grid-toolbarItem name="create"
				text="Enter Service Parameter Master" />
					 <kendo:grid-toolbarItem name="serviceTemplatesDetailsExport" text="Export To Excel" />
					 <kendo:grid-toolbarItem name="pdfTemplatesDetailsExport" text="Export To PDF" />
			<kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterServiceParameterMasters()>Clear Filter</a>"/>
		</kendo:grid-toolbar>


		<kendo:grid-columns>
			<kendo:grid-column title="SPM Id" field="spmId" width="1px"	hidden="true">

			</kendo:grid-column>
			<kendo:grid-column title="Service&nbsp;Type&nbsp;*" field="serviceType"	editor="dropDownChecksEditor" width="120px">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function apCodeFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Servicetype",
									dataSource : {
										transport : {
											read : "${commonFilterForServiceParameterMasterUrl}/serviceType"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="Sequence&nbsp;*" field="spmSequence" format="{0:n0}" width="80px">
			</kendo:grid-column>
			
			<kendo:grid-column title="Data&nbsp;Type&nbsp;*" field="spmdataType" editor="dropDownChecksEditor" width="120px">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function apCodeFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Data Type",
									dataSource : {
										transport : {
											read : "${commonFilterForServiceParameterMasterUrl}/spmdataType"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="Parameter&nbsp;Name&nbsp;*" field="spmName" width="120px">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function apCodeFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Parameter Name",
									dataSource : {
										transport : {
											read : "${commonFilterForServiceParameterMasterUrl}/spmName"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="Description" field="spmDescription" width="140px" editor="serviceDescriptionEditor">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function apCodeFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Description",
									dataSource : {
										transport : {
											read : "${commonFilterForServiceParameterMasterUrl}/spmDescription"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			
		 <kendo:grid-column title="Status" field="status" width="90px">
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
			
			<kendo:grid-column title="Created By" field="createdBy" hidden="true" width="170px">
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
			
			<kendo:grid-column title="Last Updated By" hidden="true" field="lastupdatedBy"	width="160px">
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
    template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Active#=data.spmId#'>#= data.status == 'Active' ? 'In-active' : 'Active' #</a>"
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
				<kendo:dataSource-transport-destroy url="${serviceParameterDestroyUrl}"
					dataType="json" type="GET" contentType="application/json" /> 
			</kendo:dataSource-transport>
			<kendo:dataSource-schema>
				<kendo:dataSource-schema-model id="spmId">
					<kendo:dataSource-schema-model-fields>
					
						<kendo:dataSource-schema-model-field name="spmId" type="number">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="spmSequence"	type="number" defaultValue="">
							<kendo:dataSource-schema-model-field-validation min="1"/>
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="serviceType"	type="string" >
						</kendo:dataSource-schema-model-field>
							
						<kendo:dataSource-schema-model-field name="status"  type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="spmName" type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="spmdataType"	type="string" >
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="createdBy" type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="lastUpdatedBy" type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="spmDescription" type="string">
						</kendo:dataSource-schema-model-field>

					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>

	</kendo:grid>

<div id="alertsBox" title="Alert"></div>

<script>
$("#grid").on("click",".k-grid-serviceTemplatesDetailsExport", function(e) {
	  window.open("./serviceTemplate/serviceTemplatesDetailsExport");
});

$("#grid").on("click",".k-grid-pdfTemplatesDetailsExport", function(e) {
	  window.open("./pdfTemplate/pdfTemplatesDetailsExport");
});


var serviceParameterNameArray = [];

function parseServiceParameter(response) {   
    var data = response; 
    serviceParameterNameArray = [];
	 for (var idx = 0, len = data.length; idx < len; idx ++) {
		var res1 = (data[idx].spmName);
		serviceParameterNameArray.push(res1);
	 }  
	 return response;
} 

function serviceParameterMasterDeleteEvent(){
	securityCheckForActions("./Masters/ServiceParameterMaster/destroyButton");
	var conf = confirm("Are u sure want to delete this parameter details?");
	 if(!conf){
	  $("#grid").data().kendoGrid.dataSource.read();
	   throw new Error('deletion aborted');
	 }
}

function clearFilterServiceParameterMasters()
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
		$('<textarea data-text-field="spmDescription" name="spmDescription" data-bind="value:' + options.field + '" style="width: 148px; height: 40px;"/>')
				.appendTo(container);
		$('<span class="k-invalid-msg" data-for="spmDescription"></span>').appendTo(container);
	}
	
	
	function dropDownChecksEditor(container, options) {
		var res = (container.selector).split("=");
		var attribute = res[1].substring(0, res[1].length - 1);

		$(
				'<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '" name = "'+attribute+'" id = "'+attribute+'Id"/>')
				.appendTo(container).kendoDropDownList({
					optionLabel : {
						text : "Select",
						value : "",
					},
					defaultValue : false,
					sortable : true,
					dataSource : {
						transport : {
							read : "${allChecksUrl}/" + attribute,
						}
					}

				});
		$('<span class="k-invalid-msg" data-for="'+attribute+'"></span>')
				.appendTo(container);
	}
	
	 
	function onRequestStartServiceMaster(e)
	{
		/* var grid = $("#grid").data("kendoGrid");
		grid.cancelRow(); */
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();
	}
	function onRequestEndServiceMaster(r) 
	{
		//displayMessage(r, "grid", "Service Parameter Master");
		
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
					$("#alertsBox")
							.html("Can't delete service parameter details, child record found");
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
				$('#grid').data('kendoGrid').refresh();
				$('#grid').data('kendoGrid').dataSource.read();
				return false;
			}

			else if (r.response.status == "EXCEPTION") {

				errorInfo = "";

				errorInfo = r.response.result.exception;

				if (r.type == "create") {
					
					//alert("Error: Creating the USER record\n\n" + errorInfo);
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
					//alert("Error: Creating the USER record\n\n" + errorInfo);
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

			else if (r.type == "create")
			{
				$("#alertsBox").html("");
				$("#alertsBox").html("Service parameter details created successfully");
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
				$("#alertsBox")
						.html("Service parameter details updated successfully");
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
				$("#alertsBox").html("Service parameter details deleted successfully");
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
	
	var flagSpmName = "";
	function serviceParameterMasterEdit(e)
	{
		/***************************  to remove the id from pop up  **********************/
		$('div[data-container-for="spmId"]').remove();
		$('label[for="spmId"]').closest('.k-edit-label').remove();
		
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
			securityCheckForActions("./Masters/ServiceParameterMaster/createButton");
			flagSpmName=true;
			setApCode = $('input[name="spmId"]').val();
			$(".k-window-title").text("Add Service Parameter Details");
			$(".k-grid-update").text("Save");		
	    }
		else{
			flagSpmName=false;
			
			$('div[data-container-for="spmName"]').remove();
			$('label[for="spmName"]').closest('.k-edit-label').remove();
			
			securityCheckForActions("./Masters/ServiceParameterMaster/updateButton");
			$(".k-window-title").text("Edit Service Parameter Details");
		}
		
		var billUniqueParamName=[];
		var billUniqueSequenceNo=[];

		$(".k-grid-update").click(function () {
				var ServiceType = $("input[name=serviceType]").data("kendoDropDownList").text();
			 	var spmName = $('input[name="spmName"]').val();
			 	var spmSequenceNo = $('input[name="spmSequence"]').val();

			 	$.ajax({
				     url:'./billingparameter/getUniqueParamName/'+"ServiceParameterMaster/"+"spmName/"+"serviceType/"+ServiceType,
				     async: false,
				     success: function (response) {
				    	 
				    	 for(var i = 0; i<response.length; i++) {
				    		 billUniqueParamName[i] = response[i];
						} 
				    }
				     
				});
			 	
			 	
			 	$.ajax({
				     url:'./billingparameter/getUniqueParamName/'+"ServiceParameterMaster/"+"spmSequence/"+"serviceType/"+ServiceType,
				     async: false,
				     success: function (response) {
				    	 
				    	 for(var i = 0; i<response.length; i++) {
				    		 billUniqueSequenceNo[i] = response[i];
						} 
				    }
				     
				});
			 	
			 	
			 	if((spmName)!=null || (spmName)!=""){
			 	   for(var i = 0; i<billUniqueParamName.length; i++){
						   if(spmName==billUniqueParamName[i]){
							   alert("Parameter name already exist");
							     return false;

						   }
						}
				   }
			 	

			 	if((spmSequenceNo)!=null || (spmSequenceNo)!=""){
			 	   for(var i = 0; i<billUniqueSequenceNo.length; i++){
						   if(spmSequenceNo==billUniqueSequenceNo[i]){
							   alert("Sequence No already exist for selected Service Type");
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
		  var result=securityCheckForActionsForStatus("./Masters/ServiceParameterMaster/activeInactiveButton"); 
		  if(result=="success"){ 
		     if (enable) 
		     {
		      $.ajax
		      ({
		       type : "POST",
		       url : "./serviceParameterMaster/serviceMaster/" + dataItem.id + "/activate",
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
		       url : "./serviceParameterMaster/serviceMaster/" + dataItem.id + "/deactivate",
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
	               if (input.attr("name") == "serviceType")
	               {
	                return $.trim(input.val()) !== "";
	               }
	               return true;
	              },
	              sequenceRequired : function(input, params){
	                     if (input.attr("name") == "spmSequence")
	                     {
	                      return $.trim(input.val()) !== "";
	                     }
	                     return true;
	                    },
	   	          DataTypeRequired : function(input, params){
	                  if (input.attr("name") == "spmdataType")
	                  {
	                   return $.trim(input.val()) !== "";
	                  }
	                  return true;
	                 },
	                 bvmNameRequired : function(input, params){
		                  if (input.attr("name") == "spmName")
		                  {
		                   return $.trim(input.val()) !== "";
		                  }
		                  return true;
		                 }   ,
		            sequenceLengthValidation: function (input, params)
		                 {
		           	  if (input.filter("[name='spmSequence']").length && input.val() != "") 
		                 {
		           			return /^[0-9]{1,2}$/.test(input.val());
		                 }        
		                 return true;
		                    },
		              parameterNameLengthValidation : function (input, params) 
		                    {         
		                        if (input.filter("[name='spmName']").length && input.val() != "") 
		                        {
		                       	 return /^[a-zA-Z ]{1,45}$/.test(input.val());
		                        }        
		                        return true;
		                    },
		              parameterDescLengthValidation: function (input, params)
		                    {
		              	  if (input.filter("[name='spmDescription']").length && input.val() != "") 
		                    {
		                   	 return /^[\s\S]{1,500}$/.test(input.val());
		                    }        
		                    return true;
		                       },  
		             serviceParameterNameUniquevalidation : function(input, params){
									if(flagSpmName){
										if (input.filter("[name='spmName']").length && input.val()) 
										{
											var flag = true;
											$.each(serviceParameterNameArray, function(idx1, elem1) {
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
