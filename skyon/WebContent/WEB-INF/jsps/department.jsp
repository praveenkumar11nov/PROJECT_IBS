<%@include file="/common/taglibs.jsp"%>	
			 
			
			<c:url value="/common/getAllChecks" var="allChecksUrl" />
	<c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />
			
			
<c:url value="/department/create" var="createUrl" /> 
<c:url value="/department/read" var="readUrl" />
 <c:url value="/department/update" var="updateUrl" />
<c:url value="/department/destroy" var="destroyUrl" />
<c:url value="/department/departmentNameForFilter" var="departmentNameUrl"/>

<c:url value="/department/getDepartmentDescriptionForFilter" var="getDepartmentDescriptionForFilter" />


	<kendo:grid name="grid" pageable="true"
		resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true" dataBound="found"
		filterable="true" groupable="true" edit="onEvent" >
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true"  ></kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
		 <kendo:grid-filterable-operators>
		  	<kendo:grid-filterable-operators-string eq="Is equal to"/>
		 </kendo:grid-filterable-operators>
			
		</kendo:grid-filterable>


    
    	<kendo:grid-editable mode="popup" confirmation="Are you sure you want to remove this item?"/>
        <kendo:grid-toolbar>
            <kendo:grid-toolbarItem name="create" text="Add Department"/>
             <kendo:grid-toolbarItem text="Clear Filter" name="Clear_Filter"/>
        </kendo:grid-toolbar>
        <kendo:grid-columns>
           <kendo:grid-column title="Department Name &nbsp; *" field="dept_Name" width="170px">
           
           <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function DepartmentNameFilter(element) {
								element.kendoAutoComplete({
									dataSource: {
										transport: {
											read: "${departmentNameUrl}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	    
           
           </kendo:grid-column>
             <kendo:grid-column title="Description &nbsp; *" filterable="false" field="dept_Desc" editor="departmentDescription" width="160px">
             <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getDepartmentDescriptionForFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
			</kendo:grid-column>
              
                <kendo:grid-column title="Department Status"    field="dept_Status" filterable="true" width="150px"  >
                
                <kendo:grid-column-filterable>
		    			<kendo:grid-column-filterable-ui>
	    				<script> 
					function nationalityFilter(element) {
					element.kendoDropDownList({
					optionLabel: "Select",
					dataSource : {
					transport : {
					read : "${filterDropDownUrl}/dept_Status"
					}
					}
					});
					}
						  	</script>		
		    			</kendo:grid-column-filterable-ui>
		    		</kendo:grid-column-filterable>	
                </kendo:grid-column>
             
            <kendo:grid-column title="&nbsp;" width="100px" >
            	<kendo:grid-column-command>
            		<kendo:grid-column-commandItem name="edit" click = "edit" text="Edit"/>
            		<%-- <kendo:grid-column-commandItem name="destroy" /> --%>
            	</kendo:grid-column-command>
            </kendo:grid-column>
            <kendo:grid-column title="" template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Activate#=data.dept_Id#'>#= data.dept_Status == 'Active' ? 'De-activate' : 'Activate' #</a>" width="100px" />
        </kendo:grid-columns>
        <kendo:dataSource  requestEnd="onRequestEnd" requestStart="onRequestStart">
            <kendo:dataSource-transport>
                <kendo:dataSource-transport-create url="${createUrl}" dataType="json" type="POST" contentType="application/json" />
                <kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json"/>
                <kendo:dataSource-transport-update url="${updateUrl}" dataType="json" type="POST" contentType="application/json" />
                <kendo:dataSource-transport-destroy url="${destroyUrl}" dataType="json" type="POST" contentType="application/json" />
                <kendo:dataSource-transport-parameterMap>
                	<script>
	                	function parameterMap(options,type) { 	                		
	                		return JSON.stringify(options);	                		
	                	}
                	</script>
                </kendo:dataSource-transport-parameterMap>
            </kendo:dataSource-transport>
            <kendo:dataSource-schema errors="errors">
                <kendo:dataSource-schema-model id="dept_Id">
                 <kendo:dataSource-schema-model-fields>
                        <kendo:dataSource-schema-model-field name="dept_Id" editable="false">
                        </kendo:dataSource-schema-model-field>
                    
                   
                        <kendo:dataSource-schema-model-field name="dept_Name" type="string"/>
                        	
                        
                        <kendo:dataSource-schema-model-field name="dept_Desc" type="string">
                        </kendo:dataSource-schema-model-field>
                         
                         <kendo:dataSource-schema-model-field name="dept_Status"   type="string"/>
                      
                   
                        
                    </kendo:dataSource-schema-model-fields>
                </kendo:dataSource-schema-model>
            </kendo:dataSource-schema>
        </kendo:dataSource>
    </kendo:grid>
    <div id="alertsBox" title="Alert"></div>   
    <script type="text/javascript">
    
    function onRequestStart(e){
		$('.k-grid-update').hide();
		$('.k-edit-buttons')
				.append(
						'<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
		$('.k-grid-cancel').hide();
}
 
    
    
function onEvent(e){
	
	 if (e.model.isNew()){
		 $(".k-grid-Activate").hide();
	}else{
		 $(".k-grid-Activate").hide();
		$('a[id="k-grid-Activate#=data.dept_Id#"]').hide();
	
    	
	}
		
	$('label[for="dept_Status"]').closest('.k-edit-label').hide();
	$('div[data-container-for="dept_Status"]').hide(); 
}
   	
	    $(document.body).keydown(function(e) {
	        if (e.altKey && e.keyCode == 87) {
	            $("#grid").data("kendoGrid").table.focus();
	        }
	    });
      
            //register custom validation rules
      var test1 = "";
	  var flag = "";
	  var name = "";	  
	  var res = [];
	  var editingName = "";
	  var flagUserId = "";
	  var res1 = [];
	  var enterdTrack = "";

	  function GroupName(data) {
		    
			$.each(data, function( index, value ) {
				if(index == "dept_Name" && value != "" && name != value)
				{
				        if ($.inArray(value, res) == -1) res.push(value);	
				}
				
			});		
			return data.dept_Name;
	  }


	  $("#grid").on("click", ".k-grid-Clear_Filter", function(){
	 	    //custom actions
	 	    $("form.k-filter-menu button[type='reset']").slice().trigger("click");
	 		var grid = $("#grid").data("kendoGrid");
	 		grid.dataSource.read();
	 		grid.refresh();
	 	});
	  
	  /*Spring Method level Security for Add Button  */
		$("#grid").on("click", ".k-grid-add", function() {
			 securityCheckForActions("./userManagement/department/createButton");
			/* var lang = getURLParameter("language");
		    if(lang == 'hin'){
				gridLangConversion();
				$(".k-window-title").text(jQuery.i18n.prop('addDept'));
		    }
		    else
		    { */
		    	$.ajax({
		    		type : "GET",
		    		url : '${departmentNameUrl}',
		    		dataType : "JSON",
		    		success : function(response) {
		    			$.each(response, function(index, value) {
		    				res1.push(value);
		    			});
		    		}
		    	});
		    	
		    	
		    	
		    	
		    	
		    	$(".k-window-title").text("Add New Department");
				$(".k-grid-update").text("Save");
		    //}
			 $('label[for=dept_Id]').parent().remove();
			 
			
			 $('.k-edit-field .k-input').first().focus();
			
			 if($("#grid").data("kendoGrid").dataSource.filter())
				{
		    		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
		    	    var grid = $("#grid").data("kendoGrid");
		    		grid.dataSource.read();
		    		grid.refresh();
		        }
		
		});

		/*Spring Method level Security for Edit Button  */
		function edit(e) {
			/* var lang = getURLParameter("language");
		    if(lang == 'hin'){
				gridLangConversion();
				$(".k-window-title").text(jQuery.i18n.prop('editDept'));
		    }
		    else{ */
		    
		   
		    	
		    var deptName = $('input[name="dept_Name"]').val();
			   $.each(res1, function(idx1, elem1) {
				   
				  
			    if(elem1 === deptName)
			    {
			     // alert("found-----");
			    	
			  
			     res1.splice(idx1, 1);
			    } 
			   });
		    	$(".k-window-title").text("Edit Department Details");
		    //}
			
			
			//Selecting Grid
			var gview = $("#grid").data("kendoGrid");
			//Getting selected item
			var selectedItem = gview.dataItem(gview.select());
			
			flagUserId = selectedItem.dept_Id;
			$(".k-grid-Activate"+flagUserId).hide();
			securityCheckForActions("./userManagement/department/updateButton");
			
			
	
						test1 == "EditUser";
						
						//accessing selected rows data 
						editingName = selectedItem.dept_Name;

						$.each(res, function(index, value) {
							if (editingName == value) {
								res.splice($.inArray(value, res), 1);
							}
						});
		}

	  (function ($, kendo) 
			   	{   	  
			        $.extend(true, kendo.ui.validator, 
			        {
			             rules: 
			             { // custom rules          	
			            	 DepartmentNameValidation: function (input, params) 
			                 {               	 
			                     //check for the name attribute 
			                     if (input.filter("[name='dept_Name']").length && input.val()) 
			                     {
			                    	 name = input.val();
			                    	$.each(res, function( index, value ) 
									{	
			              				if((name == value))
										{
											flag = input.val();								
			              				}  
			              			});  
			                         return /^[a-zA-Z]+[ ._a-zA-Z0-9._]*[a-zA-Z0-9]$/.test(input.val());
			                     }        
			                     return true;
			                 },		
			                 
			                 departmentNameUniqueness : function(input,params) {
									//check for the name attribute 
									if (input.filter("[name='dept_Name']").length && input.val()) {
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
							departmentUniqueness : function(input) {
								if (input.filter("[name='dept_Name']").length && input.val() && flag != "") {
									flag = "";
									return false;
								}
								return true;
						},  
			                
			             }, 
			             messages: 
			             { //custom rules messages
			            	 DepartmentNameValidation : "Department name field can not allow special symbols except(_ .)",
			            	 departmentUniqueness:"Department Name already exists, please try with some other name"
			             }
			        });
			        
			    })(jQuery, kendo);


	  function departmentDescription(container, options) 
		{
	        $('<textarea name="Description" data-text-field="dept_Desc" data-value-field="dept_Desc" data-bind="value:' + options.field + '" style="width: 148px; height: 40px;" />')
	             .appendTo(container);
	        $('<span class="k-invalid-msg" data-for="Department Description"></span>').appendTo(container);
		}

	
	  
	  
//-----------------------------------------------------
            function departmentEditor(container, options)
    	    {
    	    	var booleanData = [
    	    	                   {text : 'Active', value : "Active"},
    	    	                   {text: 'Deactive', value : "Deactive"}
    	    	                 ];
    	    	
    	    		  $('<input />')
    	    		  .attr('data-bind', 'value:dept_Status')
    	    		  .appendTo(container)
    	    		  .kendoDropDownList({
    	    		    dataSource: booleanData,
    	    		    dataTextField: 'text',
    	    		    dataValueField: 'value'
    	    		  });
    	    	
    	    	
    	    }
            function onRequestEnd(e) {
      	  	  
           		if (typeof e.response != 'undefined')
        		{
           			if (e.response.status == "FAIL") {
           				errorInfo = "";
           			//	errorInfo = e.response.result.invalid;
           				for (i = 0; i < e.response.result.length; i++) {
           				errorInfo += (i + 1) + ". "
           						+ e.response.result[i].defaultMessage;
           				}

           				if (e.type == "create") {
           					$("#alertsBox").html("");
               				$("#alertsBox").html("Error: Creating the Department record\n \n:-" + errorInfo);
               				$("#alertsBox").dialog({
               					modal : true,
               					buttons : {
               						"Close" : function() {
               							$(this).dialog("close");
               						}
               					}
               				});
           					//alert("Error: Creating the Department record\n\n" + errorInfo);
           				}
           		
           				if (e.type == "update") {
           					$("#alertsBox").html("");
               				$("#alertsBox").html("Error: Updating the Department record\n \n:-" + errorInfo);
               				$("#alertsBox").dialog({
               					modal : true,
               					buttons : {
               						"Close" : function() {
               							$(this).dialog("close");
               						}
               					}
               				});
               				
           					//alert("Error: Updating the Department record\n\n" + errorInfo);
           				}
           		
           				var grid = $("#grid").data("kendoGrid");
           				grid.dataSource.read();
           				grid.refresh();
           				return false;
           			}

           			else if (e.type == "update" && !e.response.Errors) {
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


            function found()
            {
            	
            	
            	$.ajax({
            		type : "GET",
            		url : '${departmentNameUrl}',
            		dataType : "JSON",
            		success : function(response) {
            			$.each(response, function(index, value) {
            				res1.push(value);
            			});
            		}
            	});
            	
            }

    		$("#grid").on("click", "#temPID", function(e) {
    			var button = $(this),        
	 			enable = button.text() == "Activate";
	 			var widget = $("#grid").data("kendoGrid");
	 			var dataItem = widget.dataItem($(e.currentTarget).closest("tr")); 
	 			   
    			
    					var result=securityCheckForActionsForStatus("./userManagement/department/deleteButton");   
 					   if(result=="success"){
    						 
    			 			   if(enable)
    			 				   {
    			 				     $.ajax({
    			 				    	 type : "POST",
    			 				    	 url : "./department/DepartmentStatus/"+dataItem.id +"/activate",
    			 				    	dataType:'text',
    			 				    	 success : function(response)
    			 				    	 {
    			 				    		 //alert(response);
    			 				    		$("#alertsBox").html("");
    			 							$("#alertsBox").html(response);
    			 							$("#alertsBox").dialog({
    			 								modal: true,
    			 								buttons: {
    			 									"Close": function() {
    			 										$( this ).dialog( "close" );
    			 									}
    			 								}
    			 								});
    			 				    		 button.text('Deactivate');
    			 				    		$('#grid').data('kendoGrid').dataSource.read(); 
    			 				    	 }
    			 				     });  
    			 				   }
    			 			   else
    			 				   {
    			 				     $.ajax({
    			 				    	 type : "POST",
    			 				    	 url : "./department/DepartmentStatus/"+dataItem.id +"/deactivate",
    			 				    	dataType:'text',
    			 				    	 success : function(response)
    			 				    	 {
    			 				    		 //alert(response);
    			 				    		 $("#alertsBox").html("");
											$("#alertsBox").html(response);
											$("#alertsBox").dialog({
												modal: true,
												buttons: {
													"Close": function() {
														$( this ).dialog( "close" );
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
    		
    		
    		
    		
    		(function($, kendo) {
				$.extend(	true,
								kendo.ui.validator,
								{	rules : { // custom rules
								       departmentNameValidator : function(input,params) 
	    								{
	    									 if (input.attr("name") == "dept_Name") {
	  						                   return $.trim(input.val()) !== "";
	  						                }
	  						                return true;
	    								},
	    								
	    								middleNamevalidation1 : function(input,
	    										params) {
	    									if (input.filter("[name='dept_Name']").length
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
										departmentNameValidator:"Department name is required",
										middleNamevalidation1:"Department Name allows Only characters and maximum 45 letters are allowed",
										middleNamevalidation2:"Department Description allows Only characters and maximum 225 letters are allowed",
										lastNameSpacesvalidation:"Department Description  required"
									}
								});
			})(jQuery, kendo);		
			
		
    </script>     
