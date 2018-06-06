<%@include file="/common/taglibs.jsp"%>

	<c:url value="/documentrepository/read" var="readDrUrl" />
	<c:url value="/documentrepository/commonFilterForDocumentUrl" var="commonFilterForDocumentUrl" />
	<c:url value="/documentrepository/personFilterForDocumentUrl" var="personFilterForDocumentUrl" />
	
	
	 
 	<kendo:grid name="documentRepositoryGrid" pageable="true" resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true" filterable="true" groupable="true" >
 	<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" ></kendo:grid-pageable>
								<kendo:grid-filterable extra="false">
							 		<kendo:grid-filterable-operators>
							  			<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains"/>
							 		</kendo:grid-filterable-operators>
								</kendo:grid-filterable>
								<%-- <kendo:grid-editable mode="popup" /> --%>
						        <kendo:grid-toolbar >
						            <%-- <kendo:grid-toolbarItem name="create" text="Add DD" /> --%>
						            <kendo:grid-toolbarItem text="ClearFilter"/>
						        </kendo:grid-toolbar>
        						<kendo:grid-columns>
									     <kendo:grid-column title="Dr Group Id"  field="drGroupId" hidden="true"></kendo:grid-column>
        								 <kendo:grid-column title="Document Name" field="documentName" width="150px">
        								 <kendo:grid-column-filterable >
													<kendo:grid-column-filterable-ui >
														<script>
															function ledgerStatusFilter(element) {
																element
																		.kendoAutoComplete({
																			placeholder : "Enter Doc Name",
																			dataSource : {
																				transport : {
																					read : "${commonFilterForDocumentUrl}/documentName"
																				}
																			}
																		});
															}
														</script>
													</kendo:grid-column-filterable-ui>
												</kendo:grid-column-filterable>
        								 </kendo:grid-column>
        								 <kendo:grid-column title="Person Name" field="personName" width="150px">
        								 <kendo:grid-column-filterable >
													<kendo:grid-column-filterable-ui >
														<script>
															function ledgerStatusFilter(element) {
																element
																		.kendoAutoComplete({
																			placeholder : "Enter Person Name",
																			dataSource : {
																				transport : {
																					read : "${personFilterForDocumentUrl}"
																				}
																			}
																		});
															}
														</script>
													</kendo:grid-column-filterable-ui>
												</kendo:grid-column-filterable>
        								 </kendo:grid-column>
        								 <kendo:grid-column title="Document Number" field="documentNumber" width="150px">
        								 <kendo:grid-column-filterable >
													<kendo:grid-column-filterable-ui >
														<script>
															function ledgerStatusFilter(element) {
																element
																		.kendoAutoComplete({
																			placeholder : "Enter Doc Number",
																			dataSource : {
																				transport : {
																					read : "${commonFilterForDocumentUrl}/documentNumber"
																				}
																			}
																		});
															}
														</script>
													</kendo:grid-column-filterable-ui>
												</kendo:grid-column-filterable>
        								 </kendo:grid-column>
        								 <kendo:grid-column title="Document Description" field="documentDescription" width="150px" filterable="false"></kendo:grid-column>
        								 <kendo:grid-column title="Document Type" field="documentFormat" width="150px">
        								 <kendo:grid-column-filterable >
													<kendo:grid-column-filterable-ui >
														<script>
															function ledgerStatusFilter(element) {
																element
																		.kendoAutoComplete({
																			placeholder : "Enter Doc Type",
																			dataSource : {
																				transport : {
																					read : "${commonFilterForDocumentUrl}/documentType"
																				}
																			}
																		});
															}
														</script>
													</kendo:grid-column-filterable-ui>
												</kendo:grid-column-filterable>
        								 </kendo:grid-column>
        								 <kendo:grid-column title="Approved Status" field="approved" width="150px">
        								 <kendo:grid-column-filterable >
													<kendo:grid-column-filterable-ui >
														<script>
															function ledgerStatusFilter(element) {
																element
																		.kendoAutoComplete({
																			placeholder : "Enter Status",
																			dataSource : {
																				transport : {
																					read : "${commonFilterForDocumentUrl}/approved"
																				}
																			}
																		});
															}
														</script>
													</kendo:grid-column-filterable-ui>
												</kendo:grid-column-filterable>
        								 </kendo:grid-column> 
							            <kendo:grid-column title="" width="150px" template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Activate#=data.urId#'>#= data.approved == 'Yes' ? 'Approved' : 'Approve' #</a>" />
							            <kendo:grid-column title="&nbsp;" width="175px" >
							            	<kendo:grid-column-command>
							            		<kendo:grid-column-commandItem name="View Document" click="downloadFile"/>
							            	</kendo:grid-column-command>
							            </kendo:grid-column>
        						</kendo:grid-columns>
        						 <kendo:dataSource requestEnd="onRequestEnd" requestStart="onRequestStart">
						            <kendo:dataSource-transport>
						                <kendo:dataSource-transport-read url="${readDrUrl}" dataType="json" type="POST" contentType="application/json"/>
						               <kendo:dataSource-transport-parameterMap>
						                	<script>
							                	function parameterMap(options,type) 
							                	{
							                		return JSON.stringify(options);	                		
							                	}
						                	</script>
						                 </kendo:dataSource-transport-parameterMap>
						            </kendo:dataSource-transport>
						            <kendo:dataSource-schema>
						                <kendo:dataSource-schema-model id="drId">
						                    <kendo:dataSource-schema-model-fields>
							                    <kendo:dataSource-schema-model-field name="drId" editable="false" />
							                    <kendo:dataSource-schema-model-field name="drGroupId" >
							                        	<kendo:dataSource-schema-model-field-validation required = "true"/>
							                     </kendo:dataSource-schema-model-field>
						                    	<kendo:dataSource-schema-model-field name="documentName" type="string">
						                    		<kendo:dataSource-schema-model-field-validation required = "true"/>
						                    	</kendo:dataSource-schema-model-field>   
						                    	<kendo:dataSource-schema-model-field name="documentNumber" type="string">
						                    			<kendo:dataSource-schema-model-field-validation required = "true"/>
						                    	</kendo:dataSource-schema-model-field>
						                    	<kendo:dataSource-schema-model-field name="ddDescription" type="string">
						                    			<kendo:dataSource-schema-model-field-validation required = "true"/>
						                    	</kendo:dataSource-schema-model-field>
						                    	<kendo:dataSource-schema-model-field name="documentFormat" type="string">
						                    			<kendo:dataSource-schema-model-field-validation required = "true"/>
						                    	</kendo:dataSource-schema-model-field>  
						                    	<kendo:dataSource-schema-model-field name="approved" type="string">
						                    			<kendo:dataSource-schema-model-field-validation required = "true"/>
						                    	</kendo:dataSource-schema-model-field>
						                    </kendo:dataSource-schema-model-fields>
						                 </kendo:dataSource-schema-model>
						             </kendo:dataSource-schema>
						          </kendo:dataSource>
 </kendo:grid>
 <div id="alertsBox" title="Alert"></div>
<script>

$("#documentRepositoryGrid").on("click", "#temPID", function(e) {
	var button = $(this), enable = button.text() == "Approve";
	var widget = $("#documentRepositoryGrid").data("kendoGrid");
	var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
	//alert(JSON.stringify(dataItem));
	if (enable) 
	{
		$.ajax({
			type : "POST",
			url : "./documentrepository/documentApprove/" + dataItem.drId + "/Approve",
			dataType:'text',
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
				button.text('Approved');
				$('#documentRepositoryGrid').data('kendoGrid').dataSource.read();
			}
		});
	} 
	else 
	{
		$("#alertsBox").html("");
		$("#alertsBox").html("Already Approved");
		$("#alertsBox").dialog({
			modal : true,
			buttons : {
				"Close" : function() {
					$(this).dialog("close");
				}
			}
		});
	}
});

$("#documentRepositoryGrid").on("click",".k-grid-ClearFilter",function() {
	$("form.k-filter-menu button[type='reset']").slice().trigger("click");
	var gridPerson = $("#documentRepositoryGrid").data("kendoGrid");
	gridPerson.dataSource.read();
	gridPerson.refresh();
});

function downloadFile()
{
	 var gview = $("#documentRepositoryGrid").data("kendoGrid");
		//Getting selected item
		var selectedItem = gview.dataItem(gview.select());
		window.open("./download/"+selectedItem.drId);
		/* $.ajax({
			 type : "POST",
			 url :"./download/"+selectedItem.drId,
			 success : function(response)
			 {
				 alert(response);
			 }
			}); */
}

function ddTypeEditor(container, options) {
	$('<input data-text-field="name" data-value-field="value" data-bind="value:' + options.field + '"/>')
			.appendTo(container).kendoDropDownList({	
				optionLabel : {
					name : "Select",
					value : "",
				},
				defaultValue : false,
				sortable : true,
				dataSource : {
					transport : {
						read : "${readDdTypeUrl}"
					}
				}

			});
}

function optionalEditor(container, options) {
	$(
			'<input data-text-field="name" data-value-field="value" data-bind="value:' + options.field + '"/>')
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
}

function ddFormatEditor(container, options) {
	$(
			'<input data-text-field="name" data-value-field="value" data-bind="value:' + options.field + '"/>')
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
			$("#alertsBox").html("Document details updated successfull");
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
			$("#alertsBox").html("Document Defined successfull");
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
			$("#alertsBox").html("Defined Document Deleted successfull");
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
</script>
<!-- </div>
</div> -->