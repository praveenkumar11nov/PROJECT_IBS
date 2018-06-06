<%@include file="/common/taglibs.jsp"%>

<c:url value="/patrolSettings/create" var="createUrl" />
	<c:url value="/patrolSettings/read" var="readUrl" />
	<c:url value="/patrolSettings/update" var="updateUrl" />
	<c:url value="/patrolSettings/delete" var="destroyUrl" />
	
<c:url value="/patrolSettings/getrolenames" var="getRoleName" />
<c:url value="/PatrolTrackPoints/getStatusList" var="getStatusListUrl" />
<c:url value="/patrolSettings/getPatrolRole" var="patrolRoleUrl" />
<c:url value="/patrolSettings/getrolenamesForFilter" var="getRoleNamesForFilter" />

<kendo:grid name="roleGrid" pageable="true"
		resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true"
		filterable="true" groupable="false" >
		
		 <kendo:grid-editable mode="popup" confirmation="Are you sure you want to remove this Patrol Role?" />/>
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Patrol Role" />
			<kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterPatrolSettings()><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>"/>
		</kendo:grid-toolbar>
			<kendo:grid-filterable extra="false">
		 <kendo:grid-filterable-operators>
		  	<kendo:grid-filterable-operators-string eq="Is equal to"/>
		 </kendo:grid-filterable-operators>
		</kendo:grid-filterable>
		<kendo:grid-columns>
		<%-- <kendo:grid-column title="setting Id" field="psId" width="70px" hidden="false"/> --%>
		<%-- <kendo:grid-column title="Role Name" field="rlId" width="70px"
				editor="roleDropDownEditor" template="#=roleDisplay(data)#"
				filterable="false" sortable="false" /> --%>
				<kendo:grid-column title="Role Name" field="rlId" width="70px" editor="roleDropDownEditor" >
					<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function statusFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Select",
									dataSource: {
										transport: {
											read: "${getRoleNamesForFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	
				</kendo:grid-column>
				<kendo:grid-column title="Status" field="status" filterable="true" width="100px" >
				<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function statusFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Select",
									dataSource: {
										transport: {
											read: "${getStatusListUrl}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	    
				</kendo:grid-column>
				<kendo:grid-column title=""
				template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Activate#=data.ptpId#'>#= data.status == 'Active' ? 'De-activate' : 'Activate' #</a>"
				width="100px" />
				<%-- <kendo:grid-column title="&nbsp;" width="160px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit" click="edit" />
					<kendo:grid-column-commandItem name="destroy" />
				</kendo:grid-column-command>
			</kendo:grid-column> --%>
		</kendo:grid-columns>
		
		<kendo:dataSource pageSize="20" requestEnd="onRequestEnd" requestStart="onRequestStart">
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
				<kendo:dataSource-schema-model id="psId">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="psId" type="number"
							editable="false" />
							<kendo:dataSource-schema-model-field name="rlId">
							<kendo:dataSource-schema-model-field-validation required="true" />
							</kendo:dataSource-schema-model-field>
							<kendo:dataSource-schema-model-field name="status" type="string">
						</kendo:dataSource-schema-model-field>
							</kendo:dataSource-schema-model-fields>
							</kendo:dataSource-schema-model>
							</kendo:dataSource-schema>
			
			</kendo:dataSource>
		
		</kendo:grid>
		<div id="alertsBox" title="Alert"></div>
		<script>
		var res1 = new Array();
		var selectedRlId;
		$("#roleGrid").on("click", ".k-grid-add", function() {
			
			
			res1 = [];
			$.ajax({
				type : "GET",
				dataType:"text",
				url : '${patrolRoleUrl}',
				dataType : "JSON",
				success : function(response) {
					 for(var i = 0; i<response.length; i++) {
							res1[i] = response[i];
						
						} 
				}
			});
			
			$(".k-window-title").text("Add Patrol Role");
			$(".k-grid-update").text("Save");
			$('[name="psId"]').attr("disabled", true);
			$('[name="psId"]').hide();
			 $('label[for=psId]').parent().remove();
			 
			 $('label[for="status"]').closest('.k-edit-label').hide();
				$('div[data-container-for="status"]').hide(); 
			 
			 $(".k-edit-field").each(function () {
		         $(this).find("#temPID").parent().remove();
		      });
			 $(".k-grid-update").click(function () {
			 for(var i = 0; i<res1.length; i++) {
					if ( res1[i] == selectedRlId ) {
						alert("This 'Role' is already exist.Please try with different one");
						return false;
				
				}
		    } 
			 });
			 
			 securityCheckForActions("./timeandattendence/patrolRoleSettings/createButton");
			
		});
		$("#roleGrid").on("click", ".k-grid-delete", function() {
			
			 securityCheckForActions("./timeandattendence/patrolRoleSettings/deleteButton");
			
		});
		 $("#roleGrid").on("click", "#temPID", function(e) {
			 
	   			var button = $(this), enable = button.text() == "Activate";
	   			var widget = $("#roleGrid").data("kendoGrid");
	   			var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
	   			result=securityCheckForActionsForStatus("./timeandattendence/patrolRoleSettings/statusButton");   
	 		   if(result=="success"){
	   						if (enable) {
	   							$.ajax({
	   								type : "POST",
	   								dataType:"text",
	   								url : "./timeAndAttendanceManagement/patrolRoleSettingStatus/" + dataItem.id + "/activate",
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
	   									$('#roleGrid').data('kendoGrid').dataSource.read();
	   								}
	   							});
	   						} else {
	   							$.ajax({
	   								type : "POST",
	   								dataType:"text",
	   								url : "./timeAndAttendanceManagement/patrolRoleSettingStatus/" + dataItem.id + "/deactivate",
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
	   									$('#roleGrid').data('kendoGrid').dataSource.read();
	   								}
	   							});
	   						}
	   					}
	   		});	
		
		 function roleDropDownEditor(container, options) {
			$(
					'<input name="Role Name" data-text-field="rlName"  data-value-field="rlId" data-bind="value:' + options.field + '" required="true"/>')
					.appendTo(container).kendoDropDownList({
						// placeholder:"Select Role",
						optionLabel : "Select",
						defaultValue : false,
						sortable : true,
						dataSource : {
							transport : {
								read : "${getRoleName}"
							}
						},
						change : function (e) {
				            selectedRlId = this.value();
				            for(var i = 0; i<res1.length; i++) {
								if ( res1[i] == selectedRlId ) {
									alert("This 'Role' is already exist.Please try with different one");
									return false;
							
							}
					    } 
						}
					});
			$('<span class="k-invalid-msg" data-for="Role Name"></span>').appendTo(container); 
			
		}
		 
		 
	   function clearFilterPatrolSettings(){
		   $("form.k-filter-menu button[type='reset']").slice().trigger("click");
		   var gridStoreIssue = $("#roleGrid").data("kendoGrid");
		   gridStoreIssue.dataSource.read();
		   gridStoreIssue.refresh();
		}
		 
		function onRequestStart(e){
			
				var gridStoreGoodsReturn = $("#roleGrid").data("kendoGrid");
				gridStoreGoodsReturn.cancelRow();		
			
		}
		function onRequestEnd(e) {
			if (typeof e.response != 'undefined')
			{
				if (e.response.status == "FAIL") 
				{
					errorInfo = "";
					for (i = 0; i < e.response.result.length; i++) {
						errorInfo += (i + 1) + ". "
								+ e.response.result[i].defaultMessage + "<br>";
					}

					if (e.type == "create") {
						$("#alertsBox").html("");
 		 					$("#alertsBox").html("Error: Creating the Patrol Role\n\n : " + errorInfo);
 							$("#alertsBox").dialog({
 								modal : true,
 								buttons : {
 									"Close" : function() {
 										$(this).dialog("close");
 									}
 								}
 							});
					}
					var grid = $("#roleGrid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
				}
				else if (e.response.status == "EXISTROLE") 
				{
					errorInfo = "";
					errorInfo = e.response.result.existrole;

					if (e.type == "create") {
						$("#alertsBox").html("");
 		 					$("#alertsBox").html("Error: Creating the Patrol Role\n\n : " + errorInfo);
 							$("#alertsBox").dialog({
 								modal : true,
 								buttons : {
 									"Close" : function() {
 										$(this).dialog("close");
 									}
 								}
 							});
					}
					var grid = $("#roleGrid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
				}
				/* else if (e.response.status == "INVALIDROLE") 
				{
					errorInfo = "";
					errorInfo = e.response.result.invalidrole;

					if (e.type == "create") {
						$("#alertsBox").html("");
 		 					$("#alertsBox").html("Error: Creating the Patrol Role\n\n : " + errorInfo);
 							$("#alertsBox").dialog({
 								modal : true,
 								buttons : {
 									"Close" : function() {
 										$(this).dialog("close");
 									}
 								}
 							});
					}
					var grid = $("#roleGrid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
				} */
				
				 else if (e.type == "create") {
					 $("#alertsBox").html("");
	 					$("#alertsBox").html("Patrol Role is created successfully");
						$("#alertsBox").dialog({
							modal : true,
							buttons : {
								"Close" : function() {
									$(this).dialog("close");
								}
							}
						});
						
						var grid = $("#roleGrid").data("kendoGrid");
						grid.dataSource.read();
						grid.refresh();
				 }
				 else if (e.type == "destroy") {
					 $("#alertsBox").html("");
	 					$("#alertsBox").html("Patrol Role is deleted successfully");
						$("#alertsBox").dialog({
							modal : true,
							buttons : {
								"Close" : function() {
									$(this).dialog("close");
								}
							}
						});
						
						var grid = $("#roleGrid").data("kendoGrid");
						grid.dataSource.read();
						grid.refresh();
				 }
				 else if (e.type == "update") {
					 $("#alertsBox").html("");
	 					$("#alertsBox").html("Patrol Role is updated successfully");
						$("#alertsBox").dialog({
							modal : true,
							buttons : {
								"Close" : function() {
									$(this).dialog("close");
								}
							}
						});
						
						var grid = $("#roleGrid").data("kendoGrid");
						grid.dataSource.read();
						grid.refresh();
				 }
				
			}
			
		}
		</script>
