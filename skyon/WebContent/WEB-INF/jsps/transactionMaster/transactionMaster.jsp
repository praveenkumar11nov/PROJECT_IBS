<%@include file="/common/taglibs.jsp"%>

<c:url value="/transactionMaster/transactionMasterCreate" var="createUrl" />
<c:url value="/transactionMaster/transactionMasterRead" var="readUrl" />
<c:url value="/transactionMaster/transactionMasterDestroy" var="destroyUrl" />
<c:url value="/transactionMaster/transactionMasterUpdate" var="updateUrl" />
<c:url value="/transactionMaster/filter" var="commonFilterForTransactionMasterUrl" />

<c:url value="/common/getAllChecks" var="allChecksUrl" />

<kendo:grid name="grid" remove="transactionMasterDeleteEvent" pageable="true" resizable="true" edit="transactionMasterEvent" sortable="true" reorderable="true" selectable="true" scrollable="true" filterable="false" groupable="true">

    <kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" refresh="true" info="true" previousNext="true">
		<kendo:grid-pageable-messages itemsPerPage="transaction codes per page" empty="No transaction code to display" refresh="Refresh all the transaction codes" 
			display="{0} - {1} of {2} New transaction codes" first="Go to the first page of transaction codes" last="Go to the last page of transaction codes" next="Go to the next page of transaction codes"
			previous="Go to the previous page of transaction codes"/>
	</kendo:grid-pageable>
	<kendo:grid-filterable extra="false">
		<kendo:grid-filterable-operators>
			<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains"/>
			<kendo:grid-filterable-operators-date gt="Is after" lt="Is before"/>
		</kendo:grid-filterable-operators> 
	</kendo:grid-filterable>
	<kendo:grid-editable mode="popup"/>
		<kendo:grid-toolbar>
		      <kendo:grid-toolbarItem name="create" text="Add New Transaction Code" />
		       <kendo:grid-toolbarItem name="transactionTemplatesDetailsExport" text="Export To Excel" />
		            <kendo:grid-toolbarItem name="transactionPdfTemplatesDetailsExport" text="Export To PDF" />
		      <kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterTransactionMaster()>Clear Filter</a>"/>
	    </kendo:grid-toolbar>
	<kendo:grid-columns>
	    
	    <kendo:grid-column title="transactionId" field="transactionId" width="70px" hidden="true" filterable="false" sortable="false" />
	    
	    <kendo:grid-column title="Service&nbsp;Type&nbsp;*"  field="typeOfService" filterable="true" width="100px" editor="dropDownChecksEditor">
			<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function apCodeFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Servicetype",
									dataSource : {
										transport : {
											read : "${commonFilterForTransactionMasterUrl}/typeOfService"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="Group&nbsp;*"  field="groupType" filterable="true" width="100px" editor="dropDownChecksGroupEditor">
			<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function apCodeFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Group",
									dataSource : {
										transport : {
											read : "${commonFilterForTransactionMasterUrl}/groupType"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="Calculation&nbsp;Basis&nbsp;*"  field="calculationBasis" filterable="true" width="110px" editor="dropDownChecksEditorForCalculationBasis">
			<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function apCodeFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Calculation Basis",
									dataSource : {
										transport : {
											read : "${commonFilterForTransactionMasterUrl}/calculationBasis"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
	    
	    <kendo:grid-column title="Transaction&nbsp;Name&nbsp;*" field="transactionName" width="140px" filterable="false">
	    <kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function ledgerStatusFilter(element) {
								element.kendoAutoComplete({
											placeholder : "Enter Transaction Name",
											dataSource : {
												transport : {
													read : "${commonFilterForTransactionMasterUrl}/transactionName"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
		</kendo:grid-column-filterable>
	    </kendo:grid-column>
	    
	    <kendo:grid-column title="Transaction&nbsp;Code&nbsp;*" field="transactionCode" width="130px" filterable="false">
	    <kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function ledgerStatusFilter(element) {
								element.kendoAutoComplete({
											placeholder : "Enter Code",
											dataSource : {
												transport : {
													read : "${commonFilterForTransactionMasterUrl}/transactionCode"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
		</kendo:grid-column-filterable>
	    </kendo:grid-column>
	    
	   <kendo:grid-column title="CAM&nbsp;Rate*" field="camRate" width="90px" filterable="false"/>
	    
	    <kendo:grid-column title="Description&nbsp;*" field="description" width="230px" filterable="false" editor="transactionDescriptionEditor">
	    <kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function ledgerStatusFilter(element) {
								element.kendoAutoComplete({
											placeholder : "Enter Description",
											dataSource : {
												transport : {
													read : "${commonFilterForTransactionMasterUrl}/description"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	    </kendo:grid-column>
	    
	    <kendo:grid-column title="Created&nbsp;By" field="createdBy" width="70px" filterable="false" hidden="true">
	    <kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function ledgerStatusFilter(element) {
								element.kendoAutoComplete({
											placeholder : "Enter Created By Name",
											dataSource : {
												transport : {
													read : "${commonFilterForTransactionMasterUrl}/createdBy"
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
			    <kendo:grid-column-commandItem name="edit"/>
				<kendo:grid-column-commandItem name="destroy" />
			</kendo:grid-column-command>
		</kendo:grid-column>
	</kendo:grid-columns>

	<kendo:dataSource pageSize="10" requestEnd="onRequestEnd" requestStart="onRequestStart">
		<kendo:dataSource-transport>
			<kendo:dataSource-transport-create url="${createUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json" />
			<kendo:dataSource-transport-update url="${updateUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-destroy url="${destroyUrl}/" dataType="json" type="GET" contentType="application/json" />
		</kendo:dataSource-transport>
<!--  data="data" total="total" groups="data"   -->
		<kendo:dataSource-schema  parse="parseTransactionCode">
			<kendo:dataSource-schema-model id="transactionId">
				<kendo:dataSource-schema-model-fields>
					<kendo:dataSource-schema-model-field name="transactionId" type="number"/>
					
					<kendo:dataSource-schema-model-field name="typeOfService" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="groupType" type="string" defaultValue="Common">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="calculationBasis" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="transactionName" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="description" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="transactionCode" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="camRate" type="number" defaultValue="0">
						<kendo:dataSource-schema-model-field-validation min="0" />
					</kendo:dataSource-schema-model-field>
					
				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>

	</kendo:dataSource>

</kendo:grid>
<div id="alertsBox" title="Alert"></div>
<script>


$("#grid").on("click",".k-grid-transactionTemplatesDetailsExport", function(e) {
	  window.open("./transactionTemplate/transactionTemplatesDetailsExport");
});

$("#grid").on("click",".k-grid-transactionPdfTemplatesDetailsExport", function(e) {
	  window.open("./transactionPdfTemplate/transactionPdfTemplatesDetailsExport");
});


function clearFilterTransactionMaster()
{
   $("form.k-filter-menu button[type='reset']").slice().trigger("click");
   var gridStoreIssue = $("#grid").data("kendoGrid");
   gridStoreIssue.dataSource.read();
   gridStoreIssue.refresh();
}

var transactionCodeArray = [];
var transactionNameArray = [];

 function parseTransactionCode(response) {   
     var data = response; 
     transactionCodeArray = [];
     transactionNameArray = [];
	 for (var idx = 0, len = data.length; idx < len; idx ++) {
		var res1 = (data[idx].transactionName);
		var res2 = (data[idx].transactionCode);
		transactionNameArray.push(res1);
		transactionCodeArray.push(res2);
	 }  
	 return response;
} 

function transactionMasterDeleteEvent(){
	securityCheckForActions("./Masters/TransactionMaster/destroyButton");
	var conf = confirm("Are u sure want to delete this transaction code?");
	 if(!conf){
	  $('#grid').data().kendoGrid.dataSource.read();
	   throw new Error('deletion aborted');
	 }
}

function transactionDescriptionEditor(container, options) 
{
 $('<textarea data-text-field="description" name = "description" style="width:150px"/>')
      .appendTo(container);
 $('<span class="k-invalid-msg" data-for="Enter Description"></span>').appendTo(container);
}

function dropDownChecksEditor(container, options) {
	var res = (container.selector).split("=");
	var attribute = res[1].substring(0,res[1].length-1);
	
	$('<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '" name = "typeOfService" id = "typeOfService"/>')
			.appendTo(container).kendoDropDownList({
				optionLabel : {
					text : "Select",
					value : "",
				},
				defaultValue : false,
				select : selectedServiceType,
				sortable : true,
				dataSource : {
					transport : {
						read : "${allChecksUrl}/"+attribute,
					}
				}
			});
	 $('<span class="k-invalid-msg" data-for="'+attribute+'"></span>').appendTo(container);
}

function dropDownChecksEditorForCalculationBasis(container, options) {
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

function dropDownChecksGroupEditor(container, options) {
	
	var data = [ {
		text : "Common",
		value : "Electricity"
	}, {
		text : "Deposit",
		value : "Electricity"
	}, {
		text : "Common",
		value : "Water"
	},	{
		text : "Deposit",
		value : "Water"
	},{
		text : "Common",
		value : "Gas"
	},{
		text : "Deposit",
		value : "Gas"
	},{
		text : "Common",
		value : "CAM"
	},{
		text : "Deposit",
		value : "CAM"
	},{
		text : "Utility Charges",
		value : "CAM"
	},{
		text : "Maintenance Charges",
		value : "CAM"
	},{
		text : "Other services Charges",
		value : "CAM"
	},{
		text : "Advance Fixed Charges",
		value : "CAM"
	},{
		text : "Common",
		value : "Solid Waste"
	},{
		text : "Deposit",
		value : "Solid Waste"
	},{
		text : "Common",
		value : "Telephone Broadband"
	},{
		text : "Deposit",
		value : "Telephone Broadband"
	},{
		text : "Common",
		value : "Others"
	},{
		text : "Deposit",
		value : "Others"
	}];

	$('<input name="Group Type" data-text-field="text" id="groupType" data-value-field="text" data-bind="value:' + options.field + '" required="true"/>')
			.appendTo(container).kendoDropDownList({

				dataTextField : "text",
				dataValueField : "text",
				cascadeFrom : "typeOfService",
				/* optionLabel : {
					text :  "Select",
					value : "",
				},  */
				defaultValue : false,
				sortable : true,
				dataSource : data
			});
	$('<span class="k-invalid-msg" data-for="Group Type"></span>').appendTo(container);
}

function selectedServiceType(e){
	var dataItem = this.dataItem(e.item.index());
	if(dataItem.text=="CAM"){
		 $('div[data-container-for="calculationBasis"]').show();
		 $('label[for="calculationBasis"]').closest('.k-edit-label').show();
		 
		 $('div[data-container-for="camRate"]').show();
		 $('label[for="camRate"]').closest('.k-edit-label').show();
		 
		 $('div[data-container-for="groupType"]').show();
		 $('label[for="groupType"]').closest('.k-edit-label').show();
	}else{
		 $('div[data-container-for="calculationBasis"]').hide();
		 $('label[for="calculationBasis"]').closest('.k-edit-label').hide();
		 
		 $('div[data-container-for="camRate"]').hide();
		 $('label[for="camRate"]').closest('.k-edit-label').hide();
		 
		 /* $('div[data-container-for="groupType"]').show();
		 $('label[for="groupType"]').closest('.k-edit-label').show(); */ 
		 
	}
}

var setApCode="";	
var flagTransactionCode="";
function transactionMasterEvent(e)
{
	/***************************  to remove the id from pop up  **********************/
	$('div[data-container-for="transactionId"]').remove();
	$('label[for="transactionId"]').closest('.k-edit-label').remove();
	
	$('div[data-container-for="createdBy"]').remove();
	$('label[for="createdBy"]').closest('.k-edit-label').remove();
	
	/* $('div[data-container-for="groupType"]').hide();
	$('label[for="groupType"]').closest('.k-edit-label').hide();  */
	
    $('div[data-container-for="calculationBasis"]').hide();
	$('label[for="calculationBasis"]').closest('.k-edit-label').hide(); 
		 
	
	$('label[for=camRate]').parent().hide();
	$('div[data-container-for="camRate"]').hide();
	/************************* Button Alerts *************************/
	if (e.model.isNew()) 
    {
		securityCheckForActions("./Masters/TransactionMaster/createButton");
		flagTransactionCode=true;
		setApCode = $('input[name="transactionId"]').val();
		$(".k-window-title").text("Add New Transaction Master");
		$(".k-grid-update").text("Save");		
    }
	else{
		
		securityCheckForActions("./Masters/TransactionMaster/updateButton");
		flagTransactionCode=false;
		
		   var gview = $("#grid").data("kendoGrid");
		   var selectedItem = gview.dataItem(gview.select());
		   var selectedType = selectedItem.typeOfService;
		  
		   if(selectedType=="CAM"){
			   $('div[data-container-for="camRate"]').show();
			   $('label[for="camRate"]').closest('.k-edit-label').show();
				 
		   }
		   else{
			   $('div[data-container-for="camRate"]').hide();
			   $('label[for="camRate"]').closest('.k-edit-label').hide();
				 
		   }
		
		
		$(".k-window-title").text("Edit Transaction Master");
	}
}
	
   function onRequestStart(e){
	
	   $('.k-grid-update').hide();
       $('.k-edit-buttons')
               .append(
                       '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
       $('.k-grid-cancel').hide();	
       
		/* var gridStoreGoodsReturn = $("#grid").data("kendoGrid");
		gridStoreGoodsReturn.cancelRow(); */		
		
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
							"Error: Creating the transaction master \n\n : "
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
						"Transaction master is created successfully");
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
						"Transaction master is deleted successfully");
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
						"Transaction master is updated successfully");
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

	var flag=false;
	
	(function ($, kendo) 
		{   	  
	    $.extend(true, kendo.ui.validator, 
	    {
	         rules: 
	         { // custom rules
	            
	             
	        	 transactionCodeRequired : function(input, params){
                     if (input.attr("name") == "transactionCode")
                     {
                      return $.trim(input.val()) !== "";
                     }
                     return true;
                    },
                    typeOfServiceRequired : function(input, params){
                        if (input.attr("name") == "typeOfService")
                        {
                        	if(input.val()=="CAM"){
                        		flag = true;
                        	}
                         return $.trim(input.val()) !== "";
                        }
                        return true;
                       },
                       /* groupNameRequired : function(input, params){
                        	  if (input.attr("name") == "groupType")
                              {
                               return $.trim(input.val()) !== "";
                              }
                           return true;
                          }, */
                          calculationBasisRequired : function(input, params){
                              if(flag){
                            	  if (input.attr("name") == "calculationBasis")
                                  {
                                   return $.trim(input.val()) !== "";
                                  }
                              }
                               return true;
                              },
                    transactionNameRequired : function(input, params){
                        if (input.attr("name") == "transactionName")
                        {
                         return $.trim(input.val()) !== "";
                        }
                        return true;
                       },
                   transactionNameUniquevalidation : function(input, params){
									if(flagTransactionCode){
										if (input.filter("[name='transactionName']").length && input.val()) 
										{
											var flag = true;
											$.each(transactionNameArray, function(idx1, elem1) {
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
					transactionCodeUniquevalidation : function(input, params){
									if(flagTransactionCode){
										if (input.filter("[name='transactionCode']").length && input.val()) 
										{
											var flag = true;
											$.each(transactionCodeArray, function(idx1, elem1) {
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
								transactionNameLengthValidation : function (input, params){         
	 		                        if (input.filter("[name='transactionName']").length && input.val() != "") 
	 		                        {
	 		                        return /^[\s\S]{1,45}$/.test(input.val());
	 		                        }        
	 		                        return true;
	 		                    },
	 		                   transactionCodeLengthValidation : function (input, params){         
	 		                        if (input.filter("[name='transactionCode']").length && input.val() != "") 
	 		                        {
	 		                        return /^[\s\S]{1,45}$/.test(input.val());
	 		                        }        
	 		                        return true;
	 		                    },
	 		                   transactionDescLengthValidation : function (input, params){         
	 		                        if (input.filter("[name='description']").length && input.val() != "") 
	 		                        {
	 		                        return /^[\s\S]{1,500}$/.test(input.val());
	 		                        }        
	 		                        return true;
	 		                    },
	 		                   descriptionRequired : function(input, params){
	 		                        if (input.attr("name") == "description")
	 		                        {
	 		                         return $.trim(input.val()) !== "";
	 		                        }
	 		                        return true;
	 		                       },
	            },
	         messages: 
	         {
				//custom rules messages
				transactionCodeRequired:"Transaction code is required",
				typeOfServiceRequired:"Service type is required",
				groupNameRequired : "Group name is required",
				calculationBasisRequired : "Calculation basis is required",
				transactionNameRequired:"Transaction name is required",
				transactionNameUniquevalidation:"Transaction name is already exist",
				transactionCodeUniquevalidation:"Transaction code is already exist",
				transactionNameLengthValidation : "Transaction name max length 45 characters",
				transactionCodeLengthValidation : "Transaction code max length 45 characters",
				transactionDescLengthValidation : "Description field allows max 500 characters", 
				descriptionRequired : "Transaction description is required"
			 }
	    });
	    
	})(jQuery, kendo);
	 //End Of Validation
</script>
