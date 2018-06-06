<%@include file="/common/taglibs.jsp"%>	 
	
<c:url value="/common/getAllChecks" var="allChecksUrl" />
<c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />

<c:url value="/groups/create" var="createUrl" />
<c:url value="/groups/update" var="updateUrl" />
<c:url value="/groups/delete" var="destroyUrl" />
<c:url value="/groups/read" var="readUrl" />
<c:url value="/groups/readGroupName" var="readGroupNameUrl" />

<c:url value="/groups/getGroupDescription" var="getGroupDescription" />


<kendo:grid name="grid" pageable="true"
		resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true"
		filterable="true" groupable="true" edit="onEvent" dataBound="found">
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" ></kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
		 <kendo:grid-filterable-operators>
		  	<kendo:grid-filterable-operators-string eq="Is equal to"/>
		 </kendo:grid-filterable-operators>
			
		</kendo:grid-filterable>


	
		<kendo:grid-editable mode="popup"
			confirmation="Are you sure to delete that record???" />
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Group" />
			<kendo:grid-toolbarItem text="Clear Filter" name="Clear_Filter"/>
		</kendo:grid-toolbar>
		<kendo:grid-columns>
			<%-- <kendo:grid-column title="Group Id" field="gr_id" width="90px" />  --%>
			
			<kendo:grid-column title="Group Name&nbsp;*" field="gr_name"
				 width="110px" >
				
				 <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function GroupNameFilter(element) {
								element.kendoAutoComplete({
									dataSource: {
										transport: {
											read: "${readGroupNameUrl}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	    
				
				</kendo:grid-column>
			<kendo:grid-column title="Group Description&nbsp;*" field="gr_description" filterable="true" editor="GroupDescription" width="150px">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getGroupDescription}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
			</kendo:grid-column>
			
				
			<kendo:grid-column title="Group Status"  field="gr_status" filterable="true" width="100px">
			
			<kendo:grid-column-filterable>
		    			<kendo:grid-column-filterable-ui>
	    				<script> 
					function nationalityFilter(element) {
					element.kendoDropDownList({
					optionLabel: "Select",
					dataSource : {
					transport : {
					read : "${filterDropDownUrl}/gr_status"
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
					<kendo:grid-column-commandItem name=" edit" text="Edit" click="edit" />
					
				</kendo:grid-column-command>
			</kendo:grid-column>
			<kendo:grid-column title=""  template="<a href='\\\#' id='temPID'  class='k-button k-button-icontext btn-destroy k-grid-Activate#=data.gr_id#'>#= data.gr_status == 'Active' ? 'De-activate' : 'Activate' #</a>" width="100px" />
		</kendo:grid-columns>
		


		<kendo:dataSource  requestEnd="onRequestEnd" requestStart="onRequestStart" >
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
							/* var msg=${msg};
							 alert(msg); */
							return JSON.stringify(options);
						}
					</script>
				</kendo:dataSource-transport-parameterMap>
			</kendo:dataSource-transport>
			<kendo:dataSource-schema parse="parseGroups">
				<kendo:dataSource-schema-model id="gr_id">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="gr_id" editable="false">
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="gr_name" type="string" />

						<kendo:dataSource-schema-model-field name="gr_description"
							type="string"/>

					
						
						<kendo:dataSource-schema-model-field name="gr_status"  type="string"/>
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
	 
	  function found()
      {
      	
		  $.ajax({
	    		type : "GET",
	    		url : '${readGroupNameUrl}',
	    		dataType : "JSON",
	    		success : function(response) {
	    			$.each(response, function(index, value) {
	    				res1.push(value);
	    			});
	    		}
	    	});
      	
      }
	 
	
	
	function parseGroups (response) {   
	    var data = response; //<--data might be in response.data!!!
	    gArray = [];
	    for (var idx = 0, len = data.length; idx < len; idx ++) {
			gArray.push(data[idx].gr_name);
	    }
		return response;
	}
	
	
	var gArray = [];
function onEvent(e){
	
	$('label[for=gr_status]').hide();		
	$('div[data-container-for="gr_status"]').hide();	
	if (e.model.isNew()){
		$(".k-grid-Activate").hide();
	}else{
		$(this).find("#temPID").parent().remove();
		var gr_name = e.model.gr_name;
		
		 $.each(gArray, function(idx1, elem1) {
				if(elem1 == gr_name)
				{
					gArray.splice(idx1, 1);
				}	
			});	
		$('a[id="k-grid-Activate#=data.gr_id#"]').hide();
	}
	  $('label[for="gr_status"]').closest('.k-edit-label').hide();
  	$('div[data-container-for="gr_status"]').hide(); 
}



	function GroupDescription(container, options) 
	{
        $('<textarea name="Group Description" data-text-field="gr_description" data-value-field="gr_description" data-bind="value:' + options.field + '" style="width: 148px; height: 40px;" />')
             .appendTo(container);
        $('<span class="k-invalid-msg" data-for="Group Description"></span>').appendTo(container);
	}


	

	

	$("#grid").on("click", ".k-grid-Clear_Filter", function(){
	    //custom actions
	    $("form.k-filter-menu button[type='reset']").slice().trigger("click");
		var grid = $("#grid").data("kendoGrid");
		grid.dataSource.read();
		grid.refresh();
	});

	
		/*       group name  */
		var test1 = "";
		var flag = "";
		var name = "";
		var Group_Name = "";
		var res = [];
		var editingName = "";
		var flagUserId = "";
		var res1 = [];
		var enterdTrack = "";
		
		

		/*Spring Method level Security for Add Button  */
		$("#grid").on("click", ".k-grid-add", function() {
			 
			/* var lang = getURLParameter("language");
		    if(lang == 'hin'){
				gridLangConversion();
				$(".k-window-title").text(jQuery.i18n.prop('addGroup'));
		    }
		    else
		    { */
		    	$.ajax({
		    		type : "GET",
		    		url : '${readGroupNameUrl}',
		    		dataType : "JSON",
		    		success : function(response) {
		    			$.each(response, function(index, value) {
		    				res1.push(value);
		    			});
		    		}
		    	});
		    	
		    	
		    	
		    	$(".k-window-title").text("Add New Group");
				$(".k-grid-update").text("Save");
		    //}
			
			 $('label[for=gr_id]').parent().remove();
			 $('label[for=created_by]').parent().remove();
			 $('label[for=last_Updated_by]').parent().remove();
			 
		

		    	
			   $('.k-edit-field .k-input').first().focus();
				
			//$(".k-grid-Activate").hide();
			
			securityCheckForActions("./userManagement/groups/createButton");
			
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
			
			
			
			var groupName = $('input[name="gr_name"]').val();
			   $.each(res1, function(idx1, elem1) {
			    if(elem1 == groupName)
			    {
			     res1.splice(idx1, 1);
			    } 
			   });
			
		
		    	$(".k-window-title").text("Edit Group Details");
			
			//Selecting Grid
			var gview = $("#grid").data("kendoGrid");
			//Getting selected item
			var selectedItem = gview.dataItem(gview.select());
			//accessing selected rows data 
						  
			flagUserId = selectedItem.gr_id;
			$(".k-grid-Activate"+flagUserId).hide();
			securityCheckForActions("./userManagement/groups/updateButton");
			
			
						test1 == "EditUser";

						editingName = selectedItem.urLoginName;

						$.each(res, function(index, value) {
							if (editingName == value) {
								res.splice($.inArray(value, res), 1);
							}
						});


		}

		(function($, kendo) {
			$
					.extend(
							true,
							kendo.ui.validator,
							{
								rules : { // custom rules          	
									GroupNameValidation : function(input,
											params) {
										//check for the name attribute 
										if (input.filter("[name='gr_name']").length
												&& input.val()) {
											name = input.val();
											$.each(res, function(index, value) {
												if ((name == value)) {
													flag = input.val();
												}
											});
											return /^[a-zA-Z]+[ ._a-zA-Z0-9._]*[a-zA-Z0-9]$/
													.test(input.val());
										}
										return true;
									},
									checkValidation : function(input, params){
    									if (input.filter("[name='gr_name']").length && input.val()) 
    									{
    										var flag = true;
    										$.each(gArray, function(idx1, elem1) {
    											if(elem1 == input.val())
    											{
    												flag = false;
    											}	
    										});
    										return flag;
    									}
    									return true;
    								},
									groupNameUniqueness : function(input,params) {
										//check for the name attribute 
										if (input.filter("[name='gr_name']").length && input.val()) {
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
								groupUniqueness : function(input) {
									if (input.filter("[name='gr_name']").length && input.val() && flag != "") {
										flag = "";
										return false;
									}
									return true;
							},  
								},
								messages : { 

									GroupNameValidation: "Group name field can not allow special symbols except(_ .)",
									groupUniqueness : "Group Name Already Exists",
								}
							});

		})(jQuery, kendo);

		$("#grid").on("click", "#temPID", function(e) {
			var button = $(this),        
		   	enable = button.text() == "Activate";
		   	var widget = $("#grid").data("kendoGrid");
		  	var dataItem = widget.dataItem($(e.currentTarget).closest("tr")); 
		   
			
						var result=securityCheckForActionsForStatus("./userManagement/groups/deleteButton");   
					   if(result=="success"){
						   if(enable)
							   {
							     $.ajax({
							    	 type : "POST",
							    	 url : "./groups/GroupStatus/"+dataItem.id +"/activate",
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
							    	 url : "./groups/GroupStatus/"+dataItem.id +"/deactivate",
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
		
		//-------------------------------------------------------------------------
		
		function onRequestEnd(e) {
    	  	  
       		if (typeof e.response != 'undefined')
    		{
       			if (e.response.status == "FAIL") {
       				errorInfo = "";
       				//errorInfo = e.response.result.invalid;
       				for (var r = 0; r < e.response.result.length; r++) {
       				errorInfo += (r + 1) + ". "
       						+ e.response.result[r].defaultMessage;
       				}

       				if (e.type == "create") {
       					$("#alertsBox").html("");
						$("#alertsBox").html("Error: Creating the Group record\n \n:-" + errorInfo);
						$("#alertsBox").dialog({
							modal: true,
							buttons: {
								"Close": function() {
									$( this ).dialog( "close" );
								}
							}
							});

           				
       					//alert("Error: Creating the Group record\n\n" + errorInfo);
       				}
       		
       				if (e.type == "update") {

       					$("#alertsBox").html("");
						$("#alertsBox").html("Error: Updating the Group record\n \n:-" + errorInfo);
						$("#alertsBox").dialog({
							modal: true,
							buttons: {
								"Close": function() {
									$( this ).dialog( "close" );
								}
							}
							});

           				
       					//alert("Error: Updating the Group record\n\n" + errorInfo);
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


		

		
		
		
		
		
		
		
		

		function Groupdropdown(container, options) {
			var booleanData = [ {
				text : '--select--',
				value : "--select--"
			}, {
				text : 'Active',
				value : "Active"
			}, {
				text : 'Inactive',
				value : "Inactive"
			} ];

			$('<input />').attr('data-bind', 'value:gr_status').appendTo(
					container).kendoDropDownList({
				dataSource : booleanData,
				dataTextField : 'text',
				dataValueField : 'value'
			});

		}
		
		
					
					
					
					
					(function($, kendo) {
						$.extend(	true,
										kendo.ui.validator,
										{	rules : { // custom rules
										       groupNameValidator : function(input,params) 
			    								{
			    									 if (input.attr("name") == "gr_name") {
			  						                   return $.trim(input.val()) !== "";
			  						                }
			  						                return true;
			    								},
			    								
			    								
			    								middleNamevalidation1 : function(input,
			    										params) {
			    									if (input.filter("[name='gr_name']").length
			    											&& input.val()) {
			    										return /^[a-zA-Z ]{0,45}$/
			    												.test(input.val());
			    									}
			    									return true;
			    								},
			    								
			    								middleNamevalidation2 : function(input,
			    										params) {
			    									if (input.filter("[name='Group Description']").length
			    											&& input.val()) {
			    										return /^[a-zA-Z ]{0,225}$/
			    												.test(input.val());
			    									}
			    									return true;
			    								},
			    								
			    								lastNameSpacesvalidation : function(input,
			    										params) {
			    									if (input.attr("name") == "Group Description") {
				  						                   return $.trim(input.val()) !== "";
				  						                }
				  						                return true;
			    								},
			    	    						
											},
											messages : { 
												groupNameValidator:"Group Name Required",
												middleNamevalidation1:"Group Name allows Only characters and maximum 45 letters are allowed",
												middleNamevalidation2:"Group Description allows Only characters and maximum 225 letters are allowed",
												lastNameSpacesvalidation:"Group Description Required"
											}
										});
					})(jQuery, kendo);		
					
					
					
					
					
	</script>


<!-- </div>
</div> -->
