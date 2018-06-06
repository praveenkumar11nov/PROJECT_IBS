<%@include file="/common/taglibs.jsp"%>
 
	<c:url value="/role/create" var="createUrl" />
	<c:url value="/role/read" var="readUrl" />
	<c:url value="/role/update" var="updateUrl" />
	<c:url value="/role/destroy" var="destroyUrl" />
	<c:url value="/role/getUserIdBasedOnRoleId" var="getUserId" />
	
	<c:url value="/role/getRoleNamesFilter" var="getRoleNames" />
	<c:url value="/role/getRoleDescriptionFilter" var="getRoleDescription" />
	<c:url value="/role/getRoleCreatedByFilter" var="getRoleCreatedBy" />
	<c:url value="/role/getRoleUpdatedByFilter" var="getRoleUpdatedBy" />
	<c:url value="/role/getRoleStatusFilter" var="getRoleStatus" />
	
	<c:url value="/role/getRoleDescriptionFilter" var="getRoleDescription" />
	
	

			
		<kendo:grid name="grid" detailTemplate="template"  pageable="true" 
		resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true"
		filterable="true" groupable="true" edit="onEvent" dataBound="found">
				
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" ></kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
		 <kendo:grid-filterable-operators>
		  	<kendo:grid-filterable-operators-string eq="Is equal to"/>
		 </kendo:grid-filterable-operators>
		 </kendo:grid-filterable>
				
		<kendo:grid-editable mode="popup"
			confirmation="Are you sure you want to remove this item?" />
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Role" />
			<kendo:grid-toolbarItem text="Clear_Filter"/>
		</kendo:grid-toolbar>
		<kendo:grid-columns>
			<%-- <kendo:grid-column title="Role Id" field="rlId" width="100px" hidden="true"/> --%>
			<kendo:grid-column title="Role Name&nbsp;*" field="rlName" width="100px" filterable="true">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getRoleNames}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
	    		</kendo:grid-column>
			
			
			<kendo:grid-column title="Role Description&nbsp;*" field="rlDescription" editor="roleDescription" filterable="false" width="100px">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getRoleDescription}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="Created By" field="createdBy" width="100px" filterable="true">
				<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getRoleCreatedBy}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
			
			
			</kendo:grid-column>
			
			<kendo:grid-column title="Updated By" field="lastUpdatedBy" filterable="true" width="100px">				
				<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getRoleUpdatedBy}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			
			<%--  <kendo:grid-column title="Updated Date" field="lastUpdatedDate" width="100px" format="{0:yyyy-MM-dd}"/>  --%>
			<kendo:grid-column title="Role Status" field="rlStatus" filterable="true" width="100px">
				<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
    					function statusFilter(element) {
							element.kendoDropDownList({
								optionLabel: "Select",
								dataSource : {
									transport : {
										read : "${getRoleStatus}"
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
					<kendo:grid-column-commandItem name="edit" click="edit" />
					<%-- <kendo:grid-column-commandItem name="destroy" /> --%>
				</kendo:grid-column-command>
			</kendo:grid-column>
			<kendo:grid-column title=""
				template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Activate#=data.rlId#'>#= data.rlStatus == 'Active' ? 'De-activate' : 'Activate' #</a>"
				width="100px" />
		</kendo:grid-columns>
		<kendo:dataSource pageSize="10" requestEnd="onRequestEnd" requestStart="onRequestStart">
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
							return JSON.stringify(options);
						}
					</script>
				</kendo:dataSource-transport-parameterMap>
			</kendo:dataSource-transport>
			<kendo:dataSource-schema>
				<kendo:dataSource-schema-model id="rlId">
					<kendo:dataSource-schema-model-fields>

						<kendo:dataSource-schema-model-field name="rlName" type="string">
							<%-- <kendo:dataSource-schema-model-field-validation required="true"/> --%>
						</kendo:dataSource-schema-model-field>

						<kendo:dataSource-schema-model-field name="rlDescription" type="string">
					<%-- 		<kendo:dataSource-schema-model-field-validation required="true" /> --%>
						</kendo:dataSource-schema-model-field>

						<kendo:dataSource-schema-model-field name="createdBy"
							type="string" defaultValue="Admin">
						</kendo:dataSource-schema-model-field>

						<kendo:dataSource-schema-model-field name="lastUpdatedBy"
							type="string" defaultValue="Admin">
						</kendo:dataSource-schema-model-field>

						<%--  <kendo:dataSource-schema-model-field name="lastUpdatedDate"   type="dateTime" editable="false">
                        </kendo:dataSource-schema-model-field>  --%>

						<kendo:dataSource-schema-model-field name="rlStatus"
							defaultValue="Inactive" type="string" />
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
	</kendo:grid>
	
	
	
	<kendo:grid-detailTemplate id="template">
		<kendo:grid name="grid_#=rlId#" sortable="true" scrollable="true">
						
			<kendo:grid-columns>
				<kendo:grid-column title="Active Users" field="urLoginName" width="100px" />
			</kendo:grid-columns>
			
			<kendo:dataSource pageSize="5" requestEnd="onRequestEnd" requestStart="onRequestStart">
				<kendo:dataSource-transport>
					<kendo:dataSource-transport-read url="${getUserId}/#=rlId#" dataType="json" type="GET" contentType="application/json" />					
					<kendo:dataSource-transport-parameterMap>
						<script>
							function parameterMap(options, type) 
							{
								return JSON.stringify(options);
							}
						</script>
					</kendo:dataSource-transport-parameterMap>
				</kendo:dataSource-transport>
				<kendo:dataSource-schema>
					<kendo:dataSource-schema-model>
						<kendo:dataSource-schema-model-fields>

							<kendo:dataSource-schema-model-field name="urLoginName">
								<kendo:dataSource-schema-model-field-validation required="true" />
							</kendo:dataSource-schema-model-field>
							
						</kendo:dataSource-schema-model-fields>
					</kendo:dataSource-schema-model>
				</kendo:dataSource-schema>
			</kendo:dataSource>
		</kendo:grid>
	</kendo:grid-detailTemplate>
	
	
	<div id="alertsBox" title="Alert"></div>
	<script>
	
	function found()
    {
    	
    	
	   	
    	$.ajax({
    		type : "GET",
    		url : '${getRoleNames}',
    		dataType : "JSON",
    		success : function(response) {
    			$.each(response, function(index, value) {
    				res1.push(value);
    			});
    		}
    	}); 	    
    	
    }

	
	var rlNameArray = [];
	
	function onEvent(e){
		
		 if (e.model.isNew()){
			 
			 securityCheckForActions("./userManagement/roles/createButton");
			 
			 $(".k-grid-Activate").hide(); 
		 }else{		
			 securityCheckForActions("./userManagement/roles/updateButton");
			 $('a[id="k-grid-Activate#=data.rlId#"]').hide();
			 $(this).find("#temPID").parent().remove();
			 
		 }
		
		$('label[for=rlStatus]').hide();		
		$('div[data-container-for="rlStatus"]').hide();
	}
	
	function onRequestStart(e){
	$('.k-grid-update').hide();
	$('.k-edit-buttons')
			.append(
					'<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
	$('.k-grid-cancel').hide();
}
	
	function dataBound() {
		this.expandRow(this.tbody.find("tr.k-master-row").first());
	}
	
		var errorMsg = "";
		var flag = "";
		var loginName = "";
		var res = [];
		var flagUserId = "";
		var editingName = "";
		var test1 = "";
		var res1 = [];
		var enterdTrack = "";
		/*Spring Method level Security for Add Button  */
		$("#grid").on("click", ".k-grid-add", function() 
		{	
			securityCheckForActions("./userManagement/roles/createButton");
			
			if($("#grid").data("kendoGrid").dataSource.filter())
			{
	    		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
	    	    var grid = $("#grid").data("kendoGrid");
	    		grid.dataSource.read();
	    		grid.refresh();
	        }
			
		    	
	    	$.ajax({
	    		type : "GET",
	    		url : '${getRoleNames}',
	    		dataType : "JSON",
	    		success : function(response) {
	    			$.each(response, function(index, value) {
	    				res1.push(value);
	    			});
	    		}
	    	}); 	    	
		    	
		    	
	    	$(".k-window-title").text("Add New Role");
	    	$(".k-grid-update").text("Save");
	    	
		    	  
		    $('label[for=rlId]').parent().remove();  
     	    // $(".k-grid-Activate").hide();
     	    $('.k-edit-field .k-input').first().focus();
			
			$('label[for=rlId]').hide();
			$('div[data-container-for="rlId"]').hide();
			
			/* $('label[for=rlId]').hide();		
			$('div[data-container-for="rlId"]').hide(); */
			
			$('label[for=createdBy]').hide();		
			$('div[data-container-for="createdBy"]').hide();
			
			$('label[for=lastUpdatedBy]').hide();		
			$('div[data-container-for="lastUpdatedBy"]').hide();		
		});
		
		function onRequestStart(e){
			$('.k-grid-update').hide();
			$('.k-edit-buttons')
					.append(
							'<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
			$('.k-grid-cancel').hide();
	}
		/*Spring Method level Security for Edit Button  */
		function edit(e) {
			
			securityCheckForActions("./userManagement/roles/updateButton");

	       	
		    	
		    var rlName = $('input[name="rlName"]').val();
			$.each(res1, function(idx1, elem1) {
			    if(elem1 == rlName)
			    {
			     res1.splice(idx1, 1);
			    } 
			   });	 	    	
		    	
		    	
		    $(".k-window-title").text("Edit Role Details");
		    
			$('label[for=rlId]').hide();		
			$('div[data-container-for="rlId"]').hide();
			
			$('label[for=createdBy]').hide();		
			$('div[data-container-for="createdBy"]').hide();
			
			$('label[for=lastUpdatedBy]').hide();		
			$('div[data-container-for="lastUpdatedBy"]').hide();
			
			$('label[for=rlStatus]').hide();		
			$('div[data-container-for="rlStatus"]').hide();	
			

			//Selecting Grid
			var gview = $("#grid").data("kendoGrid");
			//Getting selected item
			var selectedItem = gview.dataItem(gview.select());

			flagUserId = selectedItem.rlId;
			$(".k-grid-Activate" + flagUserId).hide();

			test1 == "EditUser";

			
			$(".k-link").click(function () 
			{
				 $('input[type="checkbox"]').parent().show();	
				 var grid = $("#grid").data("kendoGrid");
				 grid.dataSource.read();
				 grid.refresh();
			});

		}

		
		function rolesEditor(container, options) {
			var booleanData = [ {
				text : 'Active',
				value : "Active"
			}, {
				text : 'Deactive',
				value : "Deactive"
			} ];

			$('<input />').attr('data-bind', 'value:rlStatus').appendTo(
					container).kendoDropDownList({
				dataSource : booleanData,
				dataTextField : 'text',
				dataValueField : 'value'
			});

		}
	</script>
	
	<script>
		//register custom validation rules
		(function($, kendo) {
			$
					.extend(
							true,
							kendo.ui.validator,
							{
								rules : { // custom rules
									rolenamevalidation : function(input, params) {
										//check for the name attribute 
										if (input.filter("[name='rlName']").length
												&& input.val()) {
											//return /^[A-Za-z]+[a-zA-Z_]+$/.test(input.val());
											/* return /^[a-zA-Z]+[a-zA-Z]*[_]{0,1}[a-zA-Z]*[^_]$/ */

											return /^[a-zA-Z]+[ ._a-zA-Z0-9._]*[a-zA-Z0-9]$/
													.test(input.val());
										}
										return true;
									},
									
							        roleNameValidator : function(input,params) 
    								{
    									//check for the name attribute 
    									 if (input.attr("name") == "rlName") {
  						                   return $.trim(input.val()) !== "";
  						                }
  						                return true;
    								},
    								
    								
    								lastNameSpacesvalidation : function(input,params) 
     								{
    							
     									//check for the name attribute 
     									 if (input.attr("name") == "Role Description") {
   						                   return $.trim(input.val()) !== "";
   						                }
   						                return true;
     								},
    								roleNameUniqueness : function(input,params) {
    									//check for the name attribute 
    									if (input.filter("[name='rlName']").length && input.val()) {
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
    							roleUniqueness : function(input) {
    								if (input.filter("[name='rlName']").length && input.val() && flag != "") {
    									flag = "";
    									return false;
    								}
    								return true;
    						},  
    						
    						middleNamevalidation1 : function(input,
									params) {
								if (input.filter("[name='rlName']").length
										&& input.val()) {
									return /^[a-zA-Z ]{0,45}$/
											.test(input.val());
								}
								return true;
							},
							
							middleNamevalidation2 : function(input,
									params) {
								if (input.filter("[name='Role Description']").length
										&& input.val()) {
									return /^[a-zA-Z ]{0,225}$/
											.test(input.val());
								}
								return true;
							},
							
							
    						
    								/* rlNameUniquevalidation : function(input, params){
    									if (input.filter("[name='rlName']").length && input.val()) 
    									{
    										var flag = true;
    										$.each(rlNameArray, function(idx1, elem1) {
    											if(elem1 == input.val())
    											{
    												flag = false;
    											}	
    										});
    										return flag;
    									}
    									return true;
    								} */
								},
								messages : { //custom rules messages
									rolenamevalidation : "Role name field can not allow special symbols except(_ .)",
									//RoleDescription:"Role Description field can not allow special symbols except(_ .)",
									roleNameValidator:"Role Name Required",
									//rlNameUniquevalidation:"Role name already exists"
									roleUniqueness:"Role Name already exists, please try with some other name ",
									middleNamevalidation1:"Role Name allows Only characters and maximum 45 letters are allowed",
									middleNamevalidation2:"Role Description allows Only characters and maximum 225 letters are allowed",
									lastNameSpacesvalidation:"Role Description Required"
								//rolenamevalidation : " Role Name allows characters,atmost one underscore(_) and should not contain numbers,special charecters(except underscore) and should not end with underscore(_)"
								}
							});

		})(jQuery, kendo);


		function roleDescription(container, options) 
		{
	        $('<textarea name="Role Description" data-text-field="rlDescription" data-value-field="rlDescription" data-bind="value:' + options.field +  '" style="width: 148px; height: 40px;"/>')
	             .appendTo(container);
		}
		
		$("#grid").on("click", "#temPID", function(e) {
			var button = $(this), enable = button.text() == "Activate";
			var widget = $("#grid").data("kendoGrid");
			var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));

			var result=securityCheckForActionsForStatus("./userManagement/roles/deleteButton");			
			if(result=="success"){
				if (enable) {
					$.ajax({
						type : "POST",
						url : "./role/RoleStatus/" + dataItem.id + "/activate",
						dataType : 'text',
						success : function(response) {
							//alert(response);
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
						url : "./role/RoleStatus/" + dataItem.id + "/deactivate",
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
			 }			
		});			
	
		
		$("#grid").on("click", ".k-grid-Clear_Filter", function(){
		    //custom actions
		    $("form.k-filter-menu button[type='reset']").slice().trigger("click");
			var grid = $("#grid").data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
		});
		
		function onRequestEnd(e) {
			if (typeof e.response != 'undefined')
			{
			if (e.response.status == "FAIL") {
				errorInfo = "";
				//errorInfo = e.response.result.invalid;
				for (i = 0; i < e.response.result.length; i++) {
					errorInfo += (i + 1) + ". "
							+ e.response.result[i].defaultMessage;
				}

				if (e.type == "create") {
					alert("Error: Creating the Role record\n\n" + errorInfo);
				}

				if (e.type == "update") {
					alert("Error: Updating the Role record\n\n" + errorInfo);
				}

				var grid = $("#grid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
				return false;
			}

			if (e.type == "update" && !e.response.Errors) {
				//alert("Update record is successfull");
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

			if (e.type == "create" && !e.response.Errors) {
				//alert("Create record is successfull");
				$("#alertsBox").html("");
				$("#alertsBox").html("Role Created successfully");
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
		
		function parseRoles (response) {   
		    var data = response; //<--data might be in response.data!!!
		    rlNameArray = [];
		    for (var idx = 0, len = data.length; idx < len; idx ++) {
				rlNameArray.push(data[idx].rlName);
		    }
			return response;
		}
	</script>
<!-- </div>
</div> -->