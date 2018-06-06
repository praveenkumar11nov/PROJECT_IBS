<%@include file="/common/taglibs.jsp"%>
	 
	
	<c:url value="/common/getAllChecks" var="allChecksUrl" />
	<c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />
	
	
	<c:url value="/designation/create" var="createUrl" />
	<c:url value="/designation/read" var="readUrl" />
	<c:url value="/designation/update" var="updateUrl" />
	<c:url value="/designation/destroy" var="destroyUrl" />
	<c:url value="/designation/getdata" var="getData" />
	<c:url value="/designation/designationNameForFilter" var="designationNameUrl" />
	<c:url value="/designation/getdesignationDescriptionForFilter" var="getdesignationDescriptionForFilter" />
	<c:url value="/department/departmentNameForFilter" var="departmentUrl" />


	<c:url value="/users/getDepartmentNamesList" var="departmentNamesUrl" />
	
	

	<kendo:grid name="grid" pageable="true" resizable="true"
		sortable="true" reorderable="true" selectable="true" scrollable="true"
		filterable="true" groupable="true" edit="onEvent" dataBound="found">
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10"
			input="true" numeric="true"></kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
			<kendo:grid-filterable-operators>
				<kendo:grid-filterable-operators-string eq="Is equal to" />
			</kendo:grid-filterable-operators>

		</kendo:grid-filterable>
		<kendo:grid-editable mode="popup"
			confirmation="Are you sure you want to remove this item?" />
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Designation" />
			<kendo:grid-toolbarItem text="Clear Filter" name="Clear_Filter" />
		</kendo:grid-toolbar>
		<kendo:grid-columns>
			<kendo:grid-column title="Designation Name &nbsp; *" field="dn_Name"
				width="150px">

				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function DesignationNameFilter(element) {
								element.kendoAutoComplete({
									dataSource : {
										transport : {
											read : "${designationNameUrl}"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>

			</kendo:grid-column>
			<kendo:grid-column title="Description &nbsp; *" editor="designationDescription" field="dn_Description" filterable="false" width="140px">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getdesignationDescriptionForFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
			</kendo:grid-column>


			<kendo:grid-column title="Department &nbsp; *" field="dept_Name"
				editor="categoryDropDownEditor" width="100px">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function DepartmentNameFilter(element) {
								element.kendoAutoComplete({
									dataSource : {
										transport : {
											read : "${departmentUrl}"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			<kendo:grid-column title="Designation Status" field="dr_Status"
				filterable="true" width="150px" >
				
				<kendo:grid-column-filterable>
		    			<kendo:grid-column-filterable-ui>
	    				<script> 
					function nationalityFilter(element) {
					element.kendoDropDownList({
					optionLabel: "Select",
					dataSource : {
					transport : {
					read : "${filterDropDownUrl}/dr_Status"
					}
					}
					});
					}
						  	</script>		
		    			</kendo:grid-column-filterable-ui>
		    		</kendo:grid-column-filterable>	
				
				</kendo:grid-column>
			

			<kendo:grid-column title="&nbsp;" width="100px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit" click="edit" text="Edit" />
					<%-- <kendo:grid-column-commandItem name="destroy" /> --%>
				</kendo:grid-column-command>
			</kendo:grid-column>

			<kendo:grid-column title=""
				template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Activate#=data.dn_Id#'>#= data.dr_Status == 'Active' ? 'De-activate' : 'Activate' #</a>"
				width="100px" />
		</kendo:grid-columns>

		<kendo:dataSource batch="true" requestEnd="onRequestEnd" requestStart="onRequestStart">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-create url="${createUrl}"
					dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-read url="${readUrl}" dataType="json"
					type="POST" contentType="application/json" />
				<kendo:dataSource-transport-update url="${updateUrl}"
					dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-destroy url="${destroyUrl}"
					dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-parameterMap>
					<script>
						function parameterMap(options, type) {
							if (type === "read") {
								return JSON.stringify(options);
							} else {
								return JSON.stringify(options.models);
							}
						}
					</script>

				</kendo:dataSource-transport-parameterMap>
			</kendo:dataSource-transport>
			<kendo:dataSource-schema>
				<kendo:dataSource-schema-model id="dn_Id">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="dn_Name" type="string"/>
							
						<kendo:dataSource-schema-model-field name="dn_Description"
							type="string">
							
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="dept_Name">
							
						</kendo:dataSource-schema-model-field>
						

						<kendo:dataSource-schema-model-field name="dr_Status"
							  type="string" />
						
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
	</kendo:grid>
	<div id="alertsBox" title="Alert"></div>
	
	
		<script type="text/javascript">
		
		function found()
		{
			
			$.ajax({
	    		type : "GET",
	    		url : '${designationNameUrl}',
	    		dataType : "JSON",
	    		success : function(response) {
	    			$.each(response, function(index, value) {
	    				res1.push(value);
	    			});
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
		 
		
		
		var test1 = "";
		var flag = "";
		var name = "";
		var res = [];
		var editingName = "";
		var flagUserId = "";
		var res1 = [];
		var enterdTrack;
		
function onEvent(e){
	//$('a[id="temPID"]').hide();
	 if (e.model.isNew()){
		 $(".k-grid-Activate").hide();
	
	 }else{
		 $(this).find("#temPID").parent().remove();
		 $('a[id="k-grid-Activate#=data.dn_Id#"]').hide();
	 }
	$('label[for="dr_Status"]').closest('.k-edit-label').hide();
	$('div[data-container-for="dr_Status"]').hide(); 
}

		$("#grid").on(
				"click",
				".k-grid-Clear_Filter",
				function() {
					//custom actions
					$("form.k-filter-menu button[type='reset']").slice()
							.trigger("click");
					var grid = $("#grid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
				});
		

		/*Spring Method level Security for Add Button  */
		$("#grid").on("click", ".k-grid-add", function() {
			
		    	$.ajax({
		    		type : "GET",
		    		url : '${designationNameUrl}',
		    		dataType : "JSON",
		    		success : function(response) {
		    			$.each(response, function(index, value) {
		    				res1.push(value);
		    			});
		    		}
		    	});
		    	
		    	
		    	$(".k-window-title").text("Add New Designation");
				$(".k-grid-update").text("Save");
		  
			$('label[for=dn_Id]').parent().remove();
			
			   $('.k-edit-field .k-input').first().focus();
			
			   securityCheckForActions("./userManagement/designation/createButton");
			
			   if($("#grid").data("kendoGrid").dataSource.filter())
				{
		    		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
		    	    var grid = $("#grid").data("kendoGrid");
		    		grid.dataSource.read();
		    		grid.refresh();
		        }
		});





		function designationDescription(container, options) 
		{
	        $('<textarea name="Description" data-text-field="dn_Description" data-value-field="dn_Description" data-bind="value:' + options.field + '" style="width: 148px; height: 40px;" />')
	             .appendTo(container);
	        $('<span class="k-invalid-msg" data-for="Designation Description"></span>').appendTo(container);
		}

		
		
		







		
		/*Spring Method level Security for Edit Button  */
		function edit(e) {

		    	var dnName = $('input[name="dn_Name"]').val();
			   $.each(res1, function(idx1, elem1) {
			    if(elem1 == dnName)
			    {
			     res1.splice(idx1, 1);
			    } 
			   });
		    	
		    	
		    	
		    	
		    	$(".k-window-title").text("Edit Designation Details");
			var gview = $("#grid").data("kendoGrid");
			//Getting selected item
			var selectedItem = gview.dataItem(gview.select());

			flagUserId = selectedItem.dn_Id;
			$(".k-grid-Activate" + flagUserId).hide();

			securityCheckForActions("./userManagement/designation/updateButton");
			
			
						test1 == "EditUser";

						//accessing selected rows data 
						editingName = selectedItem.dn_Name;

						$.each(res, function(index, value) {
							if (editingName == value) {
								res.splice($.inArray(value, res), 1);
							}
						});
		}
		//register custom validation rules
		//======================================================================================

		function DesignationName(data) {

			$.each(data, function(index, value) {
				if (index == "dn_Name" && value != "" && name != value) {
					if ($.inArray(value, res) == -1)
						res.push(value);
				}

			});
			return data.dn_Name;
		}

		(function($, kendo) {
			$
					.extend(
							true,
							kendo.ui.validator,
							{
								rules : { // custom rules          	
									DesignationNameValidation : function(input,
											params) {
										//check for the name attribute 
										if (input.filter("[name='dn_Name']").length
												&& input.val()) {
											name = input.val();
											$.each(res, function(index, value) {
												if ((name == value)) {
													flag = input.val();
												}
											});
											return /^[a-zA-Z']+[ ._a-zA-Z0-9._]*[a-zA-Z0-9]$/
													.test(input.val());
										}
										return true;
									},
									designationNameUniqueness : function(input,params) {
										//check for the name attribute 
										if (input.filter("[name='dn_Name']").length && input.val()) {
											enterdTrack = input.val().toUpperCase();	
											$.each(res1,function(ind, val) {
														if ((enterdTrack == val.toUpperCase()) && (enterdTrack.length == val.length)) {
														flag = enterdTrack;
														return false;
												}
											});
										}
										return true;
								},
								dnNameUniqueness : function(input) {
									if (input.filter("[name='dn_Name']").length && input.val() && flag != "") {
										flag = "";
										return false;
									}
									return true;
							},  
								
								},
								messages : { //custom rules messages
									DesignationNameValidation : "Designation Name field can not allow special symbols except(_ .)",
									dnNameUniqueness:"Desigantion Name already exists, please try with some other name "
								/// DesignationNameUniqueness: "Designation name already in use, please try with some other name"
								}
							});

		})(jQuery, kendo);

		//================================================================================
		function designationEditor(container, options) {
			var booleanData = [ {
				text : 'Active',
				value : "Active"
			}, {
				text : 'Deactive',
				value : "Deactive"
			} ];

			$('<input />').attr('data-bind', 'value:dr_Status').appendTo(
					container).kendoDropDownList({
				dataSource : booleanData,
				dataTextField : 'text',
				dataValueField : 'value'
			});

		}		

		function onRequestEnd(e) {

			if (typeof e.response != 'undefined') {
				
				if (e.response.status == "FAIL") {
					errorInfo = "";
					//errorInfo = e.response.result.invalid;
					for (i = 0; i < e.response.result.length; i++) {
						errorInfo += (i + 1) + ". "
								+ e.response.result[i].defaultMessage;
					}

					if (e.type == "create") {
						$("#alertsBox").html("");
						$("#alertsBox").html("Error: Creating the Designation record\n \n:-"
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

					if (e.type == "update") {
						$("#alertsBox").html("");
						$("#alertsBox").html("Error: Updating the Designation record\n \n:-"
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
					return false;
				}

				if (e.type == "update" && !e.response.Errors) {
					$("#alertsBox").html("");
					$("#alertsBox").html("Update record is successfull");
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

				if (e.type == "update" && e.response.Errors) {
					$("#alertsBox").html("");
					$("#alertsBox").html("Update record is Un-successfull");
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
					$("#alertsBox").html("");
					$("#alertsBox").html("Create record is successfull");
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


		function categoryDropDownEditor(container, options) 
		{
			$('<select id="departmentNameBox" name="Department" data-text-field="dept_Name" data-value-field="dept_Id" required="true"  data-bind="value:' + options.field + '"/>')
					.appendTo(container).kendoComboBox
					({	
						optionLabel : "Select Department",
						placeholder:"select",
						 autoBind: false,
						 change : function (e) {
					            if (this.value() && this.selectedIndex == -1) {                    
					                alert("Department Name doesn't exist!");
					                $("#departmentNameBox").data("kendoComboBox").value("");
					        	}
						    } ,
						dataSource : {
							transport : {
								read : "${getData}"
							}
						}

					});
			$('<span class="k-invalid-msg" data-for="Department"></span>').appendTo(container);
		}







		$("#grid").on("click","#temPID",function(e) {
			var button = $(this), enable = button.text() == "Activate";
			var widget = $("#grid").data("kendoGrid");
			var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));

			/* $.ajax({
				type : "POST",
				url : "./userManagement/designation/deleteButton",
				success : function(response) {
					if (response == "false") {
						//alert("Access Denied");
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
				
					} else { */
						var result=securityCheckForActionsForStatus("./userManagement/designation/deleteButton");   
					   if(result=="success"){

						if (enable) {
							$.ajax({
								type : "POST",
								url : "./designation/DesignationStatus/"+ dataItem.id+ "/activate",
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
									button.text('Deactivate');
									$('#grid').data('kendoGrid').dataSource.read();
								}
							});
						} else {
							$.ajax({
								type : "POST",
								url : "./designation/DesignationStatus/"+ dataItem.id+ "/deactivate",
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
									button.text('Activate');
									$('#grid').data('kendoGrid').dataSource.read();
								}
							});
						}
					   }
					/* }
				}
			});
 */
		});
		
		

		/*  jQuery(document).ready(function() {
			    
			 
			 gridLangConversion();
				 
			}); */
			
			 (function($, kendo) {
				$.extend(	true,
								kendo.ui.validator,
								{	rules : { // custom rules
								       designationNameValidator : function(input,params) 
	    								{
	    									 if (input.attr("name") == "dn_Name") {
	  						                   return $.trim(input.val()) !== "";
	  						                }
	  						                return true;
	    								},
	    								
	    								
	    								middleNamevalidation1 : function(input,
												params) {
											if (input.filter("[name='dn_Name']").length
													&& input.val()) {
												return /^[a-zA-Z ]{0,45}$/
														.test(input.val());
											}
											return true;
										},
										
										middleNamevalidation2 : function(input,
												params) {
											if (input.filter("[name='Description']").length
													&& input.val()) {
												return /^[a-zA-Z ]{0,225}$/
														.test(input.val());
											}
											return true;
										},
										
										lastNameSpacesvalidation : function(input,
												params) {
	    									 if (input.attr("name") == "Description") {
		  						                   return $.trim(input.val()) !== "";
		  						                }
		  						                return true;
										},
									},
									messages : { 
										designationNameValidator:"Designation name  required",
										middleNamevalidation1:"Designation Name allows Only characters and maximum 45 letters are allowed",
										middleNamevalidation2:"Designation Description allows Only characters and maximum 225 letters are allowed",
										lastNameSpacesvalidation:"Designation Description Required"
										
									}
								});
			})(jQuery, kendo);	 
		
	</script>
