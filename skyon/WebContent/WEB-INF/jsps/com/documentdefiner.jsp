<%@include file="/common/taglibs.jsp"%>

	<c:url value="/documentdefiner/read" var="readDdUrl" />
	<c:url value="/documentdefiner/cu?action=create" var="createDdUrl" />
	<c:url value="/documentdefiner/cu?action=update" var="updateDdUrl" />
	
	<c:url value="/documentdefiner/getddtype" var="readDdTypeUrl" />
	<c:url value="/documentdefiner/getddformat" var="readDdFormatUrl" />
	<c:url value="/contact/contactPrimaryChecks" var="readOptional" />
	<c:url value="/documentdefiner/getDocumentNames" var="documentNameFilterUlr" />
 	<kendo:grid name="grid" pageable="true" resizable="true" sortable="true" edit="documentDefinerEvent" reorderable="true" selectable="true" scrollable="true" filterable="true" groupable="true" >
 	<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" ></kendo:grid-pageable>
								<kendo:grid-filterable extra="false">
							 		<kendo:grid-filterable-operators>
							  			<kendo:grid-filterable-operators-string eq="Is equal to"/>
							 		</kendo:grid-filterable-operators>
								</kendo:grid-filterable>
								<kendo:grid-editable mode="popup" />
						        <kendo:grid-toolbar >
						            <kendo:grid-toolbarItem name="create" text="Add DD" />
						            <kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilter()><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>"/>
						        </kendo:grid-toolbar>
        						<kendo:grid-columns>
        								<kendo:grid-column title="&nbsp;" width="100px" >
							             	<kendo:grid-column-command>
							            		<kendo:grid-column-commandItem name="edit"/>
							            	</kendo:grid-column-command>
							             </kendo:grid-column>
									     <%-- <kendo:grid-column title="Owner Property ID" field="ddId" filterable="false" width="120px"/> --%>
									     <%-- <kendo:grid-column title="Owner Id" field="owner" ></kendo:grid-column> --%>
									     <kendo:grid-column title="DD Type&nbsp;*"  width="150px" field="ddType" editor="ddTypeEditor" >
									     <kendo:grid-column-filterable>
												<kendo:grid-column-filterable-ui>
													<script>
													function ddTypeFilter(element) {
														element.kendoAutoComplete({
															placeholder : "Enter DD Type",
															dataTextField: "name",
						                                    dataValueField: "value",
															dataSource : {
																transport : {
																	read : "${readDdTypeUrl}"
																}
															}
														});
													}
													</script>
												</kendo:grid-column-filterable-ui>
											</kendo:grid-column-filterable>
									     </kendo:grid-column>
									     <kendo:grid-column title="Document Format&nbsp;*" field="ddFormat" width="150px">
									     	<kendo:grid-column-values value="${documentFormat}"/>
									     </kendo:grid-column>
        								 <kendo:grid-column title="Document Name&nbsp;*" field="ddName" width="150px">
        								 	<kendo:grid-column-filterable>
												<kendo:grid-column-filterable-ui>
													<script>
														function documentNameFilter(element) {
															element.kendoAutoComplete({
																placeholder : "Enter Document Name",
																dataSource : {
																	transport : {
																		read : "${documentNameFilterUlr}"
																	}
																}
															});
														}
													</script>
												</kendo:grid-column-filterable-ui>
											</kendo:grid-column-filterable>
        								 </kendo:grid-column>
        								 <kendo:grid-column title="Document Description&nbsp;*" field="ddDescription" filterable="false" editor="documentDescriptionEditor" width="150px"></kendo:grid-column>
        								 <kendo:grid-column title="Optional&nbsp;*" field="ddOptional" width="150px">
        								 	<kendo:grid-column-values value="${statusYesNo}"/>
        								 </kendo:grid-column>
        								 <kendo:grid-column title="Start Date&nbsp;*" format= "{0:dd/MM/yyyy}" field="ddStartDate" width="150px"></kendo:grid-column>
        								 <kendo:grid-column title="End Date&nbsp;*" format= "{0:dd/MM/yyyy}" field="ddEndDate" width="150px"></kendo:grid-column>
        								 <kendo:grid-column title="RV Compliance&nbsp;*" field="ddRvComplaince"  width="140px">
        								 	<kendo:grid-column-values value="${statusYesNo}"/>
        								 </kendo:grid-column>
        								 <kendo:grid-column title="Status" field="status" width="85px"></kendo:grid-column>
        								 
        								 <kendo:grid-column title="&nbsp;" width="100px" >
							            	<kendo:grid-column-command>
							            		<kendo:grid-column-commandItem name="edit"/>
							            	</kendo:grid-column-command>
							            </kendo:grid-column>
							            <kendo:grid-column title="" template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Activate#=data.urId#'>#= data.status == 'Active' ? 'De-activate' : 'Activate' #</a>" width="100px" />
        						</kendo:grid-columns>
        						 <kendo:dataSource requestEnd="onRequestEnd" requestStart="onRequestStart">
						            <kendo:dataSource-transport>
						                <kendo:dataSource-transport-read url="${readDdUrl}" dataType="json" type="POST" contentType="application/json"/>
						                <kendo:dataSource-transport-create url="${createDdUrl}" dataType="json" type="POST" contentType="application/json" />
						                <kendo:dataSource-transport-update url="${updateDdUrl}" dataType="json" type="POST" contentType="application/json" />
						               <kendo:dataSource-transport-parameterMap>
						                	<script>
							                	function parameterMap(options,type) 
							                	{
							                		return JSON.stringify(options);	                		
							                	}
						                	</script>
						                 </kendo:dataSource-transport-parameterMap>
						            </kendo:dataSource-transport>
						            <kendo:dataSource-schema parse="documentDefinerParse">
						                <kendo:dataSource-schema-model id="ddId">
						                    <kendo:dataSource-schema-model-fields>
							                    <kendo:dataSource-schema-model-field name="ddId" editable="false" />
							                    <kendo:dataSource-schema-model-field name="ddType">
							                        	<kendo:dataSource-schema-model-field-validation required = "true"/>
							                     </kendo:dataSource-schema-model-field>
						                    	<kendo:dataSource-schema-model-field name="ddName" type="string">
						                    		<kendo:dataSource-schema-model-field-validation/>
						                    	</kendo:dataSource-schema-model-field>   
						                    	<kendo:dataSource-schema-model-field name="ddFormat" type="string" defaultValue="PDF">
						                    			<kendo:dataSource-schema-model-field-validation required = "true"/>
						                    	</kendo:dataSource-schema-model-field>
						                    	<kendo:dataSource-schema-model-field name="ddDescription" type="string">
						                    			<kendo:dataSource-schema-model-field-validation/>
						                    	</kendo:dataSource-schema-model-field>
						                    	<kendo:dataSource-schema-model-field name="ddOptional" type="string" defaultValue="Yes">
						                    			<kendo:dataSource-schema-model-field-validation required = "true"/>
						                    	</kendo:dataSource-schema-model-field>  
						                    	<kendo:dataSource-schema-model-field name="ddStartDate" type="date">
						                    			<kendo:dataSource-schema-model-field-validation/>
						                    	</kendo:dataSource-schema-model-field>
						                    	
						                    	<kendo:dataSource-schema-model-field name="ddEndDate" type="date">
						                    			<kendo:dataSource-schema-model-field-validation />
						                    	</kendo:dataSource-schema-model-field>
						                    	<kendo:dataSource-schema-model-field name="ddRvComplaince" type="string" defaultValue="Yes">
						                    			<kendo:dataSource-schema-model-field-validation required = "true"/>
						                    	</kendo:dataSource-schema-model-field>
						                    	<kendo:dataSource-schema-model-field name="status" type="string" defaultValue="Active" editable="true"></kendo:dataSource-schema-model-field>      
						                    </kendo:dataSource-schema-model-fields>
						                 </kendo:dataSource-schema-model>
						             </kendo:dataSource-schema>
						          </kendo:dataSource>
 </kendo:grid>
 <div id="alertsBox" title="Alert"></div>
<script>

function clearFilter()
{
	  $("form.k-filter-menu button[type='reset']").slice().trigger("click");
	  var gridDocumentDefiner = $("#grid").data("kendoGrid");
	  gridDocumentDefiner.dataSource.read();
	  gridDocumentDefiner.refresh();
}

 
var ddNames = [];
function documentDefinerEvent(e)
{
	$('div[data-container-for="status"]').remove();
	$('label[for="status"]').closest('.k-edit-label').remove();
	$(".k-edit-field").each(function () {
		$(this).find("#temPID").parent().remove();
   	});
	selectedDDType = e.model.ddType;
	 if (e.model.isNew()) 
	  {
	  	$(".k-window-title").text("Define New Document");
	  	$(".k-grid-update").text("Save");
	  }
	  else
	  {
		  $(".k-window-title").text("Edit Defined Document Details");
		  $('[name="ddName"]').attr("disabled", false);
		  $(".k-grid-update").text("Update"); 
		  ddNames.splice(ddNames.indexOf(e.model.ddType+":"+e.model.ddName),1);
	  }	
}

function documentDefinerParse(response)
{
	 	var data = response; //<--data might be in response.data!!!
	 	ddNames = [];
	     
		 for (var idx = 0, len = data.length; idx < len; idx ++)
		 {
			 ddNames.push(data[idx].ddType+":"+data[idx].ddName);
		 }
		// alert(ddNames);
		 return response;
	
}
function documentDescriptionEditor(container, options) 
{
	$('<textarea data-text-field="ddDescription" maxlength="150" name="ddDescription" data-value-field="ddDescription" data-bind="value:' + options.field + '" style="width: 148px; height: 40px;"/>')
			.appendTo(container);
}
$("#grid").on("click", "#temPID", function(e) {
	var button = $(this), enable = button.text() == "Activate";
	var widget = $("#grid").data("kendoGrid");
	var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
	if (enable) 
	{
		$.ajax({
			type : "POST",
			url : "./documentdefiner/documentStatus/" + dataItem.ddId + "/activate",
			dataType : 'text',
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
				button.text('Deactivate');
				$('#grid').data('kendoGrid').dataSource.read();
			}
		});
	} else {
		$.ajax({
			type : "POST",
			url : "./documentdefiner/documentStatus/" + dataItem.ddId + "/deactivate",
			dataType : 'text',
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
				button.text('Activate');
				$('#grid').data('kendoGrid').dataSource.read();
			}
		});
	}
});
var selectedDDType = "";
function ddTypeEditor(container, options) {
	$('<input data-text-field="name" name="ddTypeEE"  required="true" validationmessage="Document Type is required" data-value-field="value" data-bind="value:' + options.field + '"/>')
			.appendTo(container).kendoDropDownList({	
				optionLabel : {
					name : "Select",
					value : "",
				},
				select: function (e) 
		           {
		        	   var dataItem = this.dataItem(e.item.index());
		        	   selectedDDType = dataItem.value;
		        	   //alert(selectedDDType);
		           },
				defaultValue : false,
				sortable : true,
				dataSource : {
					transport : {
						read : "${readDdTypeUrl}"
					}
				}

			});
	$('<span class="k-invalid-msg" data-for="ddTypeEE"></span>').appendTo(container);
}

function optionalEditor(container, options) {
	$('<input data-text-field="name" name="optionalField"  required="true" validationmessage="Optional is required" data-value-field="value" data-bind="value:' + options.field + '"/>')
			.appendTo(container).kendoDropDownList({	
				optionLabel : {
					name : "Select",
					value : "",
				},
				defaultValue : false,
				sortable : true,
				dataSource : {
					transport : {
						read : "${readOptional}"
					}
				}
			});
	$('<span class="k-invalid-msg" data-for="optionalField"></span>').appendTo(container);
}
function RvComplainceEditor(container,options)
{
	$('<input data-text-field="name" name="rvComplaince"  required="true" validationmessage="Rv Complaince is required" data-value-field="value" data-bind="value:' + options.field + '"/>')
	.appendTo(container).kendoDropDownList({	
		optionLabel : {
			name : "Select",
			value : "",
		},
		defaultValue : false,
		sortable : true,
		dataSource : {
			transport : {
				read : "${readOptional}"
			}
		}
	});
$('<span class="k-invalid-msg" data-for="rvComplaince"></span>').appendTo(container);
}
function ddFormatEditor(container, options) {
	$('<input data-text-field="name" name="ddFormatEE"  required="true" validationmessage="Document Format is required" data-value-field="value" data-bind="value:' + options.field + '"/>')
			.appendTo(container).kendoDropDownList({	
				optionLabel : {
					name : "Select",
					value : "",
				},
				defaultValue : false,
				sortable : true,
				dataSource : {
					transport : {
						read : "${readDdFormatUrl}"
					}
				}

			});
	$('<span class="k-invalid-msg" data-for="ddFormatEE"></span>').appendTo(container);
}


function onRequestStart(e){
	$('.k-grid-update').hide();
	$('.k-edit-buttons')
			.append(
					'<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
	$('.k-grid-cancel').hide();
}


function onRequestEnd(e)
{
	if (typeof e.response != 'undefined')
	{
		if (e.response.status == "FAIL") {
			errorInfo = "";
			errorInfo = e.response.result.invalid;
			for (i = 0; i < e.response.result.length; i++) {
				errorInfo += (i + 1) + ". "
						+ e.response.result[i].defaultMessage;
			}

			if (e.type == "create") {
				alert("Error: Creating the Project\n\n" + errorInfo);
			}

			if (e.type == "update") {
				alert("Error: Updating the Project\n\n" + errorInfo);
			}

			var grid = $("#grid").data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
			return false;
		}
		 
		if (e.response.status == "CHILD_FOUND_EXCEPTION") 
		{
			errorInfo = "";
			errorInfo = e.response.result.childFoundException;

			$("#alertsBox").html("");
			$("#alertsBox").html(
					"Error: Delete Property<br>" + errorInfo);
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
			var grid = $("#grid").data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
			return false;
		}

		if (e.type == "update" && !e.response.Errors) {
			//alert("Update record is successfull");
			$("#alertsBox").html("");
			$("#alertsBox").html("Document Details Updated Successfully");
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

		if (e.type == "create" && !e.response.Errors) {
			//alert("Create record is successfull");
			$("#alertsBox").html("");
			$("#alertsBox").html("Document Details Created  Successfully");
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
		if (e.type == "destroy" && !e.response.Errors) 
		{
			$("#alertsBox").html("");
			$("#alertsBox").html("Document Details Deleted Successfully");
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


/* (function ($, kendo) {
    $.extend(true, kendo.ui.validator, {
         rules: { // custom rules
        	 ddName: function (input, params) {
                 //check for the name attribute 
                 if (input.attr("name") == "ddName") {
                  return $.trim(input.val()) !== "";
                 }
                 return true;
             },                             
             ddDescription: function (input, params) {
                 //check for the name attribute 
                 if (input.attr("name") == "ddDescription") 
                 {
                  	return $.trim(input.val()) !== "";
                 }
                 return true;
             },
             ddName_Validation: function (input, params) 
             {
                	 if (input.filter("[name='ddName']").length && input.val()) {
							return /^[a-zA-Z0-9 ]{0,55}$/
									.test(input.val());
						}
						return true;
             },     
             ddDescription_Validation: function (input, params) 
             {
                	 if (input.filter("[name='ddDescription']").length < 50 && input.val()) 
                	 	{
							return /^[a-zA-Z0-9- ]{0,150}$/
									.test(input.val());
						}
                	  
						return true;
             },                     
         },
       //custom rules messages
         messages: { 
        	 ddName: "Document Name is required",
        	 ddDescription:"Document Description is required",
        	 ddName_Validation : "Only alphanumeric is allowed",
        	 ddDescription_Validation : "Only alphanumeric is allowed"           
         }
    });
})(jQuery, kendo); */

//register custom validation rules ddStartDate,ddEndDate
(function($, kendo) {
	$
			.extend(
					true,
					kendo.ui.validator,
					{
						rules : { // custom rules          	
							ddStartDate: function (input, params) 
				             {
			                     if (input.filter("[name = 'ddStartDate']").length && input.val() != "") 
			                     {                          
			                         var selectedDate = input.val();
			                         var todaysDate = $.datepicker.formatDate('dd/mm/yy', new Date());
			                         var flagDate = false;

			                         if ($.datepicker.parseDate('dd/mm/yy', selectedDate) >= $.datepicker.parseDate('dd/mm/yy', todaysDate)) 
			                         {
			                        	 	seasonFromAccessCard = selectedDate;
			                                flagDate = true;
			                         }
			                         return flagDate;
			                     }
			                     return true;
			                 },
			                 ddEndDate1: function (input, params) 
				             {
			                     if (input.filter("[name = 'ddEndDate']").length && input.val() != "") 
			                     {                          
			                         var flagDate = false;

			                         if (seasonFromAccessCard != "") 
			                         {
			                                flagDate = true;
			                         }
			                         return flagDate;
			                     }
			                     return true;
			                 },
			                 ddEndDate2: function (input, params) 
				             {
			                     if (input.filter("[name = 'ddEndDate']").length && input.val() != "") 
			                     {                          
			                         var selectedDate = input.val();
			                         var flagDate = false;

			                         if ($.datepicker.parseDate('dd/mm/yy', selectedDate) > $.datepicker.parseDate('dd/mm/yy', seasonFromAccessCard)) 
			                         {
			                                flagDate = true;
			                         }
			                         return flagDate;
			                     }
			                     return true;
			                 },
			                 
			                 ddName: function (input, params) 
			                 {
			                    	 if (input.filter("[name='ddName']").length && input.val()) {
			    							return /^[a-zA-Z0-9 ]{0,55}$/
			    									.test(input.val());
			    						}
			    						return true;
			                 },     
			                 ddDescription: function (input, params) 
			                 {
			                    	 if (input.filter("[name='ddDescription']").length && input.val()) 
			                    	 	{
			    							return /^[a-zA-Z0-9- ]{0,150}$/
			    									.test(input.val());
			    						}
			                    	  
			    						return true;
			                 },
			                 ddName_blank: function (input, params) {
			                     //check for the name attribute 
			                     if (input.attr("name") == "ddName") {
			                      return $.trim(input.val()) !== "";
			                     }
			                     return true;
			                 },                             
			                 ddDescription_blank: function (input, params) {
			                     //check for the name attribute 
			                     if (input.attr("name") == "ddDescription") 
			                     {
			                      	return $.trim(input.val()) !== "";
			                     }
			                     return true;
			                 },
			                 ddStartDate_blank : function(input,params){
			                	 if (input.attr("name") == "ddStartDate") {
				                      return $.trim(input.val()) !== "";
				                     }
				                     return true;
			                 },
			                 ddEndDate_blank : function(input,params){
			                	 if (input.attr("name") == "ddEndDate") {
				                      return $.trim(input.val()) !== "";
				                     }
				                     return true;
			                 },
			                 ddNameUniqueness : function(input, params){
									if (input.filter("[name='ddName']").length && input.val()) 
									{
										var flag = true;
										var fieldValue =selectedDDType+":"+input.val();
										$.each(ddNames, function(idx1, elem1) {
											//alert(elem1+"-----"+fieldValue);
											if(elem1.toLowerCase() == fieldValue.toLowerCase())
											{
												flag = false;
											}	
										});
										return flag;
									}
									return true;
								}
						},
						messages : {
							//custom rules messages
							ddStartDate:"Start must be selected in the future",
							ddEndDate1:"Select Start date first before selecting End date and change End date accordingly",
							ddEndDate2:"End date should be after Start date",
							 ddName_blank: "Document Name is required",
				        	 ddDescription_blank:"Document Description is required",
				        	 ddName : "Only alphanumeric is allowed",
				        	 ddDescription: "Only alphanumeric is allowed",
				        	 ddStartDate_blank : "Start Date is required",
				        	 ddEndDate_blank : "End Date is required",
				        	 ddNameUniqueness : "Document Name already defined"
						}
					});

})(jQuery, kendo);
//End Of Validation


</script>
<!-- </div>
</div> -->