<%@include file="/common/taglibs.jsp"%>


     <c:url value="/common/getAllChecks" var="allChecksUrl" />
	 <c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />



	<c:url value="/visitordetails/readproperty" var="propertyname" />
	<c:url value="/visitorvisits/readblocks" var="propertynameURL" />
    <c:url value="/visitorvisits/readTowerNames" var="towerNames"/>
	<c:url value="/visitordetails/getAccessNoForFilter" var="readAccessCardNoForFilter" />
	
	
	
	<c:url value="/storevisitor/readVisitorName" var="readVisitorName" />
	<c:url value="/visitorvisits/read" var="readUrl" />
	<c:url value="/visitorvisits/create" var="createUrl" />
	<c:url value="/visitorvisits/update" var="updateUrl" />
	<c:url value="/visitorvisits/readcontactNo" var="readContact" />
	<c:url value="/property/read_propertyNameForFilter"
		var="getPropertyfilter" />

	<c:url value="/storevisitor/nameForFilter" var="filtervisitorName" />
	<c:url value="/storevisitor/addressForFilter"
		var="filtervisitoraddress" />
	<c:url value="/storevisitor/contactNoForFilter"
		var="filtervisitorContactNo" />




	<kendo:grid name="grid" pageable="true" resizable="true"
		sortable="true" filterable="true" groupable="true" scrollable="true"
		height="430px">
		<kendo:grid-editable mode="popup" />
		<kendo:grid-toolbar>
			<%-- <kendo:grid-toolbarItem name="create" text="Add Visitor Visits Details"/> --%>
			<kendo:grid-toolbarItem text="Clear Filter" name="Clear_Filter"/>
		</kendo:grid-toolbar>
        <kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10"></kendo:grid-pageable>

		<kendo:grid-filterable extra="false">
			<kendo:grid-filterable-operators>
				<kendo:grid-filterable-operators-string eq="Is equal to" />
				<kendo:grid-filterable-operators-date gte="Visited from" lte="Visited Before"  />
			</kendo:grid-filterable-operators>

		</kendo:grid-filterable>

		<kendo:grid-columns>
			<%-- <kendo:grid-column title="ID" field="vmId" />    --%>
			<kendo:grid-column title="Contact No" field="vmContactNo"
				editor="VisitorContact" filterable="true" width="100px">

				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function ContactNoFilter(element) {
								element.kendoAutoComplete({
									dataSource : {
										transport : {
											read : "${filtervisitorContactNo}"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>

			</kendo:grid-column>
			<kendo:grid-column title="Visitor Name" field="vmName"
				editor="VisitorAutoCompleteEditor" width="150px">

				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function ContactNoFilter(element) {
								element.kendoAutoComplete({
									dataSource : {
										transport : {
											read : "${filtervisitorName}"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>

			</kendo:grid-column>
			
			
			<kendo:grid-column title="Gender" field="gender"
					editor="GenderDropDown" width="100px">
					<kendo:grid-column-filterable>
		    			<kendo:grid-column-filterable-ui>
	    				<script type="text/javascript"> 
					function nationalityFilter(element) {
					element.kendoDropDownList({
					optionLabel: "Select",
					dataSource : {
					transport : {
					read : "${filterDropDownUrl}/gender"
					}
					}
					});
					}
						  	</script>		
		    			</kendo:grid-column-filterable-ui>
		    		</kendo:grid-column-filterable>	
					
					
					</kendo:grid-column>
					
				<kendo:grid-column title="Category" field="category"
					editor="CategoryDropDown" width="100px">
					<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
	    				<script type="text/javascript"> 
					function nationalityFilter(element) {
					element.kendoDropDownList({
					optionLabel: "Select",
					dataSource : {
					transport : {
					read : "${filterDropDownUrl}/category"
					}
					}
					});
					}
						  	</script>		
		    			</kendo:grid-column-filterable-ui>
		    		</kendo:grid-column-filterable>	
					
					</kendo:grid-column>
			

			<kendo:grid-column title="Block Name" field="blockName"
				editor="TowerNames" hidden="true" width="100px" />

			<kendo:grid-column title="Property No." field="property_No"
				editor="propertyDropDownEditor" width="120px">

				


				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function ContactNoFilter(element) {
								element.kendoAutoComplete({
									dataSource : {
										transport : {
											read : "${getPropertyfilter}"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>


			</kendo:grid-column>

			<kendo:grid-column title="Access Card No" field="acNo" width="120px" >
			
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script type="text/javascript"> 
				function acNoFilter(element) {
				element.kendoAutoComplete({
				dataSource: {
				transport: {
				read: "${readAccessCardNoForFilter}"
				}
				}
				});
				}
			</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	  
			
			</kendo:grid-column>
			
			<kendo:grid-column title="Purpose Of Visit" field="vpurpose"
				filterable="false" width="150px" editor="VisitorPurpose" />
				
			<kendo:grid-column title="Visitor Status" field="vvstatus"
				filterable="true" editor="VisitorStatus" width="150px" >
				<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
	    				<script type="text/javascript"> 
					function nationalityFilter(element) {
					element.kendoDropDownList({
					optionLabel: "Select",
					dataSource : {
					transport : {
					read : "${filterDropDownUrl}/vvstatus"
					}
					}
					});
					}
						  	</script>		
		    			</kendo:grid-column-filterable-ui>
		    		</kendo:grid-column-filterable>	
				
				</kendo:grid-column>
			<kendo:grid-column title="Entry Date Time" field="vinDt" format="{0:yyyy/MM/dd HH:mm}" 
				filterable="true" width="150px" >
				<kendo:grid-column-filterable>
        <kendo:grid-column-filterable-ui>
         <script> 
         function fromDateFilter(element) {
       element.kendoDatePicker({
        format:"yyyy/MM/dd",
                 
               });
        }
         
        </script>  
        </kendo:grid-column-filterable-ui>
       </kendo:grid-column-filterable>
				</kendo:grid-column>


			<kendo:grid-column title="Exit Date Time" field="voutDt" format="{0:yyyy/MM/dd HH:mm}" 
				filterable="true" width="150px" >
				<kendo:grid-column-filterable>
        <kendo:grid-column-filterable-ui>
         <script> 
         function fromDateFilter(element) {
       element.kendoDatePicker({
        format:"yyyy/MM/dd",
                 
               });
        }
         
        </script>  
        </kendo:grid-column-filterable-ui>
       </kendo:grid-column-filterable>
				
				</kendo:grid-column>
			<%-- <kendo:grid-column title="Created By" field="createdBy" filterable="false" width="150px"  />
           <kendo:grid-column title="Last Updated By" field="lastUpdatedBy" filterable="false" width="150px"  /> --%>



			<kendo:grid-column title="&nbsp;" width="160px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit" click="edit" />

				</kendo:grid-column-command>
			</kendo:grid-column>
			<%--  <kendo:grid-column title=""	template="<a href='\\\#' id='tempvvId' class='k-button k-button-icontext btn-destroy k-grid-IN#=data.vvId#'>#= data.vvstatus == 'IN' ? 'OUT' : 'IN' #</a>" width="100px" /> --%>
		</kendo:grid-columns>
		<kendo:dataSource pageSize="20" requestEnd="onRequestEnd">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-create url="${createUrl}"
					dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-read url="${readUrl}" dataType="json"
					type="GET" contentType="application/json" />
				<kendo:dataSource-transport-update url="${updateUrl}"
					dataType="json" type="POST" contentType="application/json" />

				<kendo:dataSource-transport-parameterMap>
					<script>
						function parameterMap(options, type) {
							return JSON.stringify(options);
						}
					</script>
				</kendo:dataSource-transport-parameterMap>
			</kendo:dataSource-transport>
			<kendo:dataSource-schema>
				<kendo:dataSource-schema-model id="vvId">
					<kendo:dataSource-schema-model-fields>

						<kendo:dataSource-schema-model-field name="vmContactNo"
							type="string">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>

						<kendo:dataSource-schema-model-field name="vmName">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>

						<kendo:dataSource-schema-model-field name="gender">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>

						<kendo:dataSource-schema-model-field name="category">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>

						<kendo:dataSource-schema-model-field name="property_No"
							type="string">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>




						<kendo:dataSource-schema-model-field name="blockName">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>


						<kendo:dataSource-schema-model-field name="acNo" type="string">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>

						<kendo:dataSource-schema-model-field name="vpurpose" type="string">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						<%-- <kendo:dataSource-schema-model-field name="vvstatus" type="string" >
                        	<kendo:dataSource-schema-model-field-validation required="true"  />
                        </kendo:dataSource-schema-model-field> --%>
						<kendo:dataSource-schema-model-field name="vinDt" type="date" editable="false">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>

						<kendo:dataSource-schema-model-field name="voutDt" type="date"
							editable="false">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>

						<kendo:dataSource-schema-model-field name="vvstatus"
							defaultValue="IN" type="string" />

						<%-- <kendo:dataSource-schema-model-field name="drGroupId" type="number"  /> --%>
						<%--   <kendo:dataSource-schema-model-field name="createdBy" type="string" editable="false"  >
                        	<kendo:dataSource-schema-model-field-validation required="true" />
                        </kendo:dataSource-schema-model-field>
                        <kendo:dataSource-schema-model-field name="lastUpdatedBy" editable="false" type="string" >
                        	<kendo:dataSource-schema-model-field-validation required="true" />
                        </kendo:dataSource-schema-model-field> --%>




					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
	</kendo:grid>

<script>
	$("#grid").on("click", ".k-grid-add", function() {
		$('label[for=vinDt]').parent().remove();
		$('label[for=voutDt]').parent().remove();
		/*  $(this).find("#tempvvId").parent().remove(); */
		$(".k-window-title").text("Add Visitor Visits Details");
		$(".k-grid-update").text("Add/Create");
		$(".k-grid-Activate").hide();
		$(".k-grid-IN").hide();
		$('.k-edit-field .k-input').first().focus();

	});

	function VisitorPurpose(container, options) {
		$(
			
							'<textarea name="Purpose Of Visit" data-text-field="vpurpose" data-value-field="vpurpose" data-bind="value:' + options.field + '" style="width: 148px; height: 40px;" required="true"/>')
							.appendTo(container);
					$('<span class="k-invalid-msg" data-for="Purpose Of Visit"></span>').appendTo(container);
				
	}

	function edit(e) {

		$.ajax({
			type : "POST",
			url : "./visitormanagement/visitorvisits/updateButton",
			success : function(response) {
				if (response == "false") {
					$("#alertsBox").html("");
					$("#alertsBox").html("Access Denied");
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
					var grid = $("#grid").data("kendoGrid");
					grid.cancelRow();
					grid.close();
				} else if (response == "timeout") {
					$("#alertsBox").html("");
					$("#alertsBox").html("Session Timeout Please Login Again");
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
					window.location.href = "./logout";

				}
			}
		});

	}

	$("#grid").on("click", ".k-grid-Clear_Filter", function() {
		//custom actions
		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
		var grid = $("#grid").data("kendoGrid");
		grid.dataSource.read();
		grid.refresh();
	});

	(function($, kendo) {
		$
				.extend(
						true,
						kendo.ui.validator,
						{
							rules : { // custom rules
								Visitornamevalidation : function(input, params) {
									//check for the name attribute 
									if (input.filter("[name='vpurpose']").length
											&& input.val()) {
										return /^[A-Za-z ]*$/.test(input.val());
									}
									return true;
								}
							},
							messages : { //custom rules messages
								Visitornamevalidation : " visitor purpose  should use only alphabets "
							}
						});
	})(jQuery, kendo);

	function TowerNames(container, options) {
		$(
				'<input data-text-field="blockName" data-value-field="blockId"  id="blockId" data-bind="value:' + options.field + '"/>')
				.appendTo(container).kendoComboBox({
					optionLabel : "Select Block",
					autoBind : false,
					dataSource : {
						transport : {
							read : "${towerNames}"
						}
					}
				});
	}

	function propertyDropDownEditor(container, options) {
		$(
				'<input data-text-field="property_No" data-value-field="propertyId"  data-bind="value:' + options.field + '"/>')
				.appendTo(container).kendoComboBox({
					optionLabel : "Select Property",
					cascadeFrom : "blockId",
					dataSource : {
						transport : {
							read : "${propertynameURL}"
						}
					}

				});
	}

	function VisitorContact(container, options) {
		$(
				'<input data-text-field="vmContactNo" data-value-field="vmContactNo" id="vmContactNo" data-bind="value:' + options.field +  '"/>')
				.appendTo(container).kendoComboBox({
					defaultValue : false,
					sortable : true,
					dataSource : {
						transport : {
							read : "${readContact}"
						}
					}
				});
	}

	function VisitorAutoCompleteEditor(container, options) {
		$(
				'<input data-text-field="vmName" data-value-field="vmId" data-bind="value:' + options.field +  '"/>')
				.appendTo(container).kendoComboBox({
					defaultValue : false,
					sortable : true,
					cascadeFrom : "vmContactNo",
					dataSource : {
						transport : {
							read : "${readVisitorName}"
						}
					}
				});
	}

	/*  ********************************************************************* */

	function onRequestEnd(e) {

		if (typeof e.response != 'undefined') {
			if (e.response.status == "FAIL") {
				errorInfo = "";
				errorInfo = e.response.result.invalid;
				for (i = 0; i < e.response.result.length; i++) {
					errorInfo += (i + 1) + ". "
							+ e.response.result[i].defaultMessage;
				}

				if (e.type == "create") {

					/* $("#alertsBox").html("");
					$("#alertsBox").html("Error: Creating the VisitorVisit record\n\n" + errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					}); */
					alert("Error: Creating the VisitorVisit record\n\n"
							+ errorInfo);
				}

				if (e.type == "update") {
					/* $("#alertsBox").html("");
					$("#alertsBox").html("Error: Updating the VisitorVisit record\n\n" + errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					}); */
					alert("Error: Updating the VisitorVisit record\n\n"
							+ errorInfo);
				}

				var grid = $("#grid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
				return false;
			}

			if (e.type == "update" && !e.response.Errors) {
				/* $("#alertsBox").html("");
				$("#alertsBox").html("Update record is successfull");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				}); */

				alert("Update record is successfull");
				var grid = $("#grid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			}

			if (e.type == "update" && e.response.Errors) {
				/* $("#alertsBox").html("");
				$("#alertsBox").html("Update record is Un-successfull");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				}); */
				alert("Update record is Un-successfull");
				var grid = $("#grid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			}

			if (e.type == "create" && !e.response.Errors) {
				/* $("#alertsBox").html("");
				$("#alertsBox").html("Create record is successfull");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				}); */
				alert("Create record is successfull");
				var grid = $("#grid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			}
		}

	}

	function CategoryDropDown(container, options) {
		var booleanData = [ {
			text : 'Select Category',
			value : ""
		}, {
			text : 'FMB',
			value : "FMB"
		}, {
			text : 'Services',
			value : "Services"
		}, {
			text : 'Relative/Friend',
			value : "Relative/Friend"
		},  {

			text : 'Post',
			value : 'Post'
		}, 

		{
			text : 'Others',
			value : "Others"
		}

		];

		$('<input/>').attr('data-bind', 'value:category').appendTo(container)
				.kendoDropDownList({

					dataSource : booleanData,
					dataTextField : 'text',
					dataValueField : 'value'
				});

	}

	function VisitorStatus(container, options) {
		var booleanData = [ {

			text : 'select',
			value : ""

		}, {
			text : 'IN',
			value : "IN"
		}, {
			text : 'OUT',
			value : "OUT"
		} ];

		$('<input />').attr('data-bind', 'value:vvstatus').appendTo(container)
				.kendoDropDownList({
					dataSource : booleanData,
					dataTextField : 'text',
					dataValueField : 'value'
				});

	}

	function GenderDropDown(container, options) {
		var booleandata = [ {

			text : 'select Gender',
			value : ""
		}, {
			text : 'Male',
			value : "Male"
		}, {
			text : 'Female',
			value : "Female"
		} ];

		$('<input />').attr('data-bind', 'value:gender').appendTo(container)
				.kendoDropDownList({
					dataSource : booleandata,
					dataTextField : 'text',
					dataValueField : 'value'

				});

	}

	$("#grid").on(
			"click",
			"#tempvvId",
			function(e) {
				var button = $(this), enable = button.text() == "IN";
				var widget = $("#grid").data("kendoGrid");
				var dataItem = widget
						.dataItem($(e.currentTarget).closest("tr"));

				$.ajax({
					type : "POST",
					url : "./visitorvisits/deleteButton",
					dataType : 'text',
					success : function(response) {
						if (response == "false") {
							var grid = $("#grid").data("kendoGrid");
							grid.cancelRow();
							$("#alertsBox").html("");
							$("#alertsBox").html("Access Denied");
							$("#alertsBox").dialog({
								modal : true,
								buttons : {
									"Close" : function() {
										$(this).dialog("close");
									}
								}
							});
						} else {
							if (enable) {
								$.ajax({
									type : "POST",
									url : "./visitorvisits/setvisitorstatus/"
											+ dataItem.id + "/IN",
									dataType : 'text',
									success : function(response) {
										/* 	$("#alertsBox").html("");
											$("#alertsBox").html(response);
											$("#alertsBox").dialog({
												modal : true,
												buttons : {
													"Close" : function() {
														$(this).dialog("close");
													}
												}
											});
										 */
										alert(response);
										button.text('OUT');

										$('#grid').data('kendoGrid').dataSource
												.read();

									}
								});
							} else {
								$.ajax({
									type : "POST",
									url : "./visitorvisits/setvisitorstatus/"
											+ dataItem.id + "/OUT",
									success : function(response) {
										/* $("#alertsBox").html("");
										$("#alertsBox").html(response);
										$("#alertsBox").dialog({
											modal : true,
											buttons : {
												"Close" : function() {
													$(this).dialog("close");
												}
											}
										}); */
										alert(response);
										button.text('IN');
										$('#grid').data('kendoGrid').dataSource
												.read();
									}
								});
							}
						}
					}
				});
			});
</script>

