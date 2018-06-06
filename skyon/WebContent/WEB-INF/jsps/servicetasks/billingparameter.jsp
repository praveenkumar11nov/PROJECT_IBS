<%@include file="/common/taglibs.jsp"%>

	<c:url value="/billingparameter/read" var="readUrl" />
	<c:url value="/common/getAllChecks" var="allChecksUrl" />
	<c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />
	<c:url value="/billingparameter/saveorupadteordelete" var="serviceSaveOrUpdateOrDelete" />
	<c:url value="/billingparameter/billParameterDestroy" var="billParameterDestroyUrl"/>
	
	<!-- Filter URL's Of Billing Parameter Master -->
    <c:url value="/billingParameterMaster/filter" var="commonFilterForBillingParameterMasterUrl" />
    <c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />
    
	<kendo:grid name="grid" remove="billParameterMasterDeleteEvent" edit="billingParameterMasterEdit"  pageable="true" scrollable="true"
		filterable="true" sortable="true" reorderable="true" groupable="true" selectable="true"
		resizable="true">
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10"
			input="true" numeric="true"></kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
			<kendo:grid-filterable-operators>
				<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains"/>
			</kendo:grid-filterable-operators>
		</kendo:grid-filterable>
		<kendo:grid-editable mode="popup"/>
		<kendo:grid-toolbar>

			<kendo:grid-toolbarItem name="create" text="Enter Billing Parameter" />
			 <kendo:grid-toolbarItem name="billingTemplatesDetailsExport" text="Export To Excel" />
			 <kendo:grid-toolbarItem name="billingPdfTemplatesDetailsExport" text="Export To PDF" />
			<kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterBillingParameterMasters()>Clear Filter</a>"/>
		</kendo:grid-toolbar>


		<kendo:grid-columns>
			<kendo:grid-column title="BVM Id" field="bvmId" width="1px"
				hidden="true">

			</kendo:grid-column>
			<kendo:grid-column title="Service&nbsp;Type&nbsp;*" field="serviceType" editor="dropDownChecksEditor" width="120px">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function apCodeFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Servicetype",
									dataSource : {
										transport : {
											read : "${commonFilterForBillingParameterMasterUrl}/serviceType"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="Sequence&nbsp;*" field="bvmSequence" format="{0:n0}" width="90px">
			</kendo:grid-column>
			
			<kendo:grid-column title="Data&nbsp;Type&nbsp;*" field="bvmDataType" editor="dropDownChecksEditor" width="125px">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function apCodeFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Data Type",
									dataSource : {
										transport : {
											read : "${commonFilterForBillingParameterMasterUrl}/bvmDataType"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			<kendo:grid-column title="Parameter&nbsp&nbsp;Name&nbsp;*" field="bvmName" width="120px">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function apCodeFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Parameter Name",
									dataSource : {
										transport : {
											read : "${commonFilterForBillingParameterMasterUrl}/bvmName"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="Description" field="bvmDescription" width="170px" editor="serviceDescriptionEditor">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function apCodeFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Description",
									dataSource : {
										transport : {
											read : "${commonFilterForBillingParameterMasterUrl}/bvmDescription"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
		 <kendo:grid-column title="Status" field="status" width="80px">
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
			
			<kendo:grid-column title="Created By" field="createdBy" hidden="true" width="120px">
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
			<kendo:grid-column title="Last Updated By" hidden="true" field="lastUpdatedBy"
				width="130px">
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
			<kendo:grid-column title="&nbsp;" width="145px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit" />
					<kendo:grid-column-commandItem name="destroy"/>	
				</kendo:grid-column-command>
			</kendo:grid-column>
			<kendo:grid-column title=""
    template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Active#=data.bvmId#'>#= data.status == 'Active' ? 'Inactivate' : 'Activate' #</a>"
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
				<kendo:dataSource-transport-destroy url="${billParameterDestroyUrl}"
					dataType="json" type="GET" contentType="application/json" /> 
			</kendo:dataSource-transport>
			<kendo:dataSource-schema>
				<kendo:dataSource-schema-model id="bvmId">
					<kendo:dataSource-schema-model-fields>
					
						<kendo:dataSource-schema-model-field name="bvmId" type="number">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="bvmSequence"	type="number" defaultValue="">
							<kendo:dataSource-schema-model-field-validation min="1" />
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="serviceType"	type="string" >
						</kendo:dataSource-schema-model-field>
							
						<kendo:dataSource-schema-model-field name="status"  type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="bvmName" type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="bvmDataType"	type="string" >
						</kendo:dataSource-schema-model-field>
							
						<kendo:dataSource-schema-model-field name="createdBy"	type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="lastUpdatedBy"	type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="bvmDescription" type="string">
						</kendo:dataSource-schema-model-field>

					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>

	</kendo:grid>

<div id="alertsBox" title="Alert"></div>
<script>
$("#grid").on("click",".k-grid-billingTemplatesDetailsExport", function(e) {
	  window.open("./billingTemplate/billingTemplatesDetailsExport");
});	 
$("#grid").on("click",".k-grid-billingPdfTemplatesDetailsExport", function(e) {
	  window.open("./billingPdfTemplate/billingPdfTemplatesDetailsExport");
});	 

var serviceParameterNameArray = [];

function parseServiceParameter(response) {   
    var data = response; 
    serviceParameterNameArray = [];
	 for (var idx = 0, len = data.length; idx < len; idx ++) {
		var res1 = (data[idx].bvmName);
		serviceParameterNameArray.push(res1);
	 }  
	 return response;
} 

function billParameterMasterDeleteEvent(){
	securityCheckForActions("./Masters/BillParameterMaster/destroyButton");
	var conf = confirm("Are u sure want to delete this parameter details?");
	 if(!conf){
	  $("#grid").data().kendoGrid.dataSource.read();
	   throw new Error('deletion aborted');
	 }
}

function clearFilterBillingParameterMasters()
{
   $("form.k-filter-menu button[type='reset']").slice().trigger("click");
   var gridBillingParameter = $("#grid").data("kendoGrid");
   gridBillingParameter.dataSource.read();
   gridBillingParameter.refresh();
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
		$('<textarea data-text-field="bvmDescription" name="bvmDescription" data-bind="value:' + options.field + '" style="width: 148px; height: 40px;"/>')
				.appendTo(container);
		$('<span class="k-invalid-msg" data-for="bvmDescription"></span>').appendTo(container);
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
		
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();	
        
		/* var gridStoreGoodsReturn = $("#grid").data("kendoGrid");
		gridStoreGoodsReturn.cancelRow();	 */	
		
	}
	function onRequestEndServiceMaster(r) 
	{
		/* displayMessage(e, "grid", "Billing Parameter Master"); */
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
				$("#alertsBox").html("Bill parameter details created successfully");
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
				$("#alertsBox").html("Bill parameter details updated successfully");
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
				$("#alertsBox").html("Bill parameter details deleted successfully");
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
	var flagBvmName = "";
	function billingParameterMasterEdit(e)
	{		
		/***************************  to remove the id from pop up  **********************/
		$('div[data-container-for="bvmId"]').remove();
		$('label[for="bvmId"]').closest('.k-edit-label').remove();
		
		$(".k-edit-field").each(function () {
			$(this).find("#temPID").parent().remove();  
	   	});
		
		$('div[data-container-for="lastUpdatedDT"]').remove();
		$('label[for="lastUpdatedDT"]').closest('.k-edit-label').remove();
		
		$('div[data-container-for="lastUpdatedBy"]').remove();
		$('label[for="lastUpdatedBy"]').closest('.k-edit-label').remove();
		
		$('div[data-container-for="createdBy"]').remove();
		$('label[for="createdBy"]').closest('.k-edit-label').remove();
		
		$('div[data-container-for="status"]').remove();
		$('label[for="status"]').closest('.k-edit-label').remove();
		
		/************************* Button Alerts *************************/
		if (e.model.isNew()) 
	    {
			securityCheckForActions("./Masters/BillParameterMaster/createButton");
			flagBvmName=true;
			setApCode = $('input[name="bvmId"]').val();
			$(".k-window-title").text("Add Billing Parameter Details");
			$(".k-grid-update").text("Save");		
	    }
		else{
			securityCheckForActions("./Masters/BillParameterMaster/updateButton");
			
			$('div[data-container-for="bvmName"]').remove();
			$('label[for="bvmName"]').closest('.k-edit-label').remove();
			
			flagBvmName=false;
			$(".k-window-title").text("Edit Billing Parameter Details");
		}
		
		var billUniqueParamName=[];
		$(".k-grid-update").click(function () {
				var ServiceType = $("input[name=serviceType]").data("kendoDropDownList").text();
			 	var bvmName = $('input[name="bvmName"]').val();

			 	$.ajax({
				     url:'./billingparameter/getUniqueParamName/'+"BillParameterMasterEntity/"+"bvmName/"+"serviceType/"+ServiceType,
				     async: false,
				     success: function (response) {
				    	 
				    	 for(var i = 0; i<response.length; i++) {
				    		 billUniqueParamName[i] = response[i];
						} 
				    }
				     
				});
			 	
			 	if((bvmName)!=null || (bvmName)!=""){
			 	   for(var i = 0; i<billUniqueParamName.length; i++){
						   if(bvmName==billUniqueParamName[i]){
							   alert("Parameter name already exist");
							     return false;

						   }
						}
				   }
			 	
			}); 
		
	}
	
	 $("#grid").on("click", "#temPID", function(e) {
		  var button = $(this), enable = button.text() == "Activate";
		  var widget = $("#grid").data("kendoGrid");
		  var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
		  var result=securityCheckForActionsForStatus("./Masters/BillParameterMaster/activeInactiveButton"); 
		  if(result=="success"){
		     if (enable) 
		     {
		      $.ajax
		      ({
		       type : "POST",
		       url : "./billingParameterMaster/billingMaster/" + dataItem.id + "/activate",
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
		       url : "./billingParameterMaster/billingMaster/" + dataItem.id + "/deactivate",
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
		        	 
	           serviceTypeRequired : function(input, params){
	               if (input.attr("name") == "serviceType")
	               {
	                return $.trim(input.val()) !== "";
	               }
	               return true;
	              },
	              sequenceRequired : function(input, params){
	                     if (input.attr("name") == "bvmSequence")
	                     {
	                      return $.trim(input.val()) !== "";
	                     }
	                     return true;
	                    },
	   	          DataTypeRequired : function(input, params){
	                  if (input.attr("name") == "bvmDataType")
	                  {
	                   return $.trim(input.val()) !== "";
	                  }
	                  return true;
	                 },
	                 bvmNameRequired : function(input, params){
		                  if (input.attr("name") == "bvmName")
		                  {
		                   return $.trim(input.val()) !== "";
		                  }
		                  return true;
		                 },
		                 sequenceLengthValidation: function (input, params)
		                 {
		           	  if (input.filter("[name='bvmSequence']").length && input.val() != "") 
		                 {
		           			return /^[0-9]{1,2}$/.test(input.val());
		                 }        
		                 return true;
		                    },
		              parameterNameLengthValidation : function (input, params) 
		                    {         
		                        if (input.filter("[name='bvmName']").length && input.val() != "") 
		                        {
		                       	 return /^[a-zA-Z& ]{1,45}$/.test(input.val());
		                        }        
		                        return true;
		                    },
		              parameterDescLengthValidation: function (input, params)
		                    {
		              	  if (input.filter("[name='bvmDescription']").length && input.val() != "") 
		                    {
		                   	 return /^[\s\S]{1,500}$/.test(input.val());
		                    }        
		                    return true;
		                       },  
		             serviceParameterNameUniquevalidation : function(input, params){
									if(flagBvmName){
										if (input.filter("[name='bvmName']").length && input.val()) 
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
