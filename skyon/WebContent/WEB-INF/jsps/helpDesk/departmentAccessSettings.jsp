<%@include file="/common/taglibs.jsp"%>

<c:url value="/HelpDeskSettings/departmentAccessSettingsCreate" var="createUrl" />
<c:url value="/HelpDeskSettings/departmentAccessSettingsRead" var="readUrl" />
<c:url value="/HelpDeskSettings/departmentAccessSettingsDestroy" var="destroyUrl" />
<c:url value="/HelpDeskSettings/getRolenames" var="getRoleName" />

<kendo:grid name="grid" remove="deptAccessSettingsDeleteEvent" pageable="true" resizable="true" edit="deprtAccessRoleEvent" sortable="true" reorderable="true" selectable="true" scrollable="true" filterable="false" groupable="false">

	<kendo:grid-editable mode="popup"/>
		<kendo:grid-toolbar>
		      <kendo:grid-toolbarItem name="create" text="Add Dept Access Role" />
	    </kendo:grid-toolbar>
	<kendo:grid-columns>
	    
	    <kendo:grid-column title="Setting Id" field="settingId" width="70px" hidden="true" filterable="false" sortable="false" />
	    
	    <kendo:grid-column title="Role Name&nbsp;*" field="rlName" width="70px" filterable="false"/>
	
		<kendo:grid-column title="Role Name&nbsp;*" field="rlId" width="70px" editor="roleDropDownEditor" hidden="true" filterable="false"/>

		<kendo:grid-column title="&nbsp;" width="160px">
			<kendo:grid-column-command>
				<kendo:grid-column-commandItem name="destroy" />
			</kendo:grid-column-command>
		</kendo:grid-column>
	</kendo:grid-columns>

	<kendo:dataSource pageSize="20" requestEnd="onRequestEnd" requestStart="onRequestStart">
		<kendo:dataSource-transport>
			<kendo:dataSource-transport-create url="${createUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json" />
			<kendo:dataSource-transport-destroy url="${destroyUrl}/" dataType="json" type="GET" contentType="application/json" />
		</kendo:dataSource-transport>

		<kendo:dataSource-schema>
			<kendo:dataSource-schema-model id="settingId">
				<kendo:dataSource-schema-model-fields>
					<kendo:dataSource-schema-model-field name="settingId" type="number"/>
					
					<kendo:dataSource-schema-model-field name="rlId">
					       <kendo:dataSource-schema-model-field-validation required="true" />
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="rlName" type="string"/>

				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>

	</kendo:dataSource>

</kendo:grid>
<div id="alertsBox" title="Alert"></div>
<script>
function onRequestStart(e){
	$('.k-grid-update').hide();
	$('.k-edit-buttons')
			.append(
					'<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
	$('.k-grid-cancel').hide();
}

function deptAccessSettingsDeleteEvent(){
	securityCheckForActions("./CustomerCare/DepartmentAccessSettings/deleteButton");
	var conf = confirm("Are u sure want to delete this Access Role?");
	 if(!conf){
	  $('#grid').data().kendoGrid.dataSource.read();
	   throw new Error('deletion aborted');
	 }
}


var setApCode="";		
function deprtAccessRoleEvent(e)
{
	/***************************  to remove the id from pop up  **********************/
	$('div[data-container-for="settingId"]').remove();
	$('label[for="settingId"]').closest('.k-edit-label').remove();
	
	$('div[data-container-for="rlName"]').remove();
	$('label[for="rlName"]').closest('.k-edit-label').remove();
		
	/************************* Button Alerts *************************/
	if (e.model.isNew()) 
    {
		securityCheckForActions("./CustomerCare/DepartmentAccessSettings/createButton");
		setApCode = $('input[name="settingId"]').val();
		$(".k-window-title").text("Add Department Access Role Details");
		$(".k-grid-update").text("Save");		
    }
	else{
		//securityCheckForActions("./CustomerCare/HelpTopic/editButton");
		$(".k-window-title").text("Edit Department Access Role Details");
	}
}
		
		 function roleDropDownEditor(container, options) {
			$(
					'<input name="rlName" data-text-field="rlName" required="true" validationmessage="Role name is required"  data-value-field="rlId" data-bind="value:' + options.field + '"/>')
					.appendTo(container).kendoDropDownList({
						// placeholder:"Select Role",
						optionLabel : "Select",
						defaultValue : false,
						sortable : true,
						dataSource : {
							transport : {
								read : "${getRoleName}"
							}
						}
					});
			$('<span class="k-invalid-msg" data-for="Role Name"></span>').appendTo(container); 
			
		}
		function onRequestEnd(e) {
			if (typeof e.response != 'undefined')
			{
				if (e.response.status == "FAIL") 
				{
					errorInfo = "";
					for (var i = 0; i < e.response.result.length; i++) {
						errorInfo += (i + 1) + ". "
								+ e.response.result[i].defaultMessage + "<br>";
					}

					if (e.type == "create") {
						$("#alertsBox").html("");
 		 					$("#alertsBox").html("Error: Creating the Dept Access Role\n\n : " + errorInfo);
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
				else if (e.response.status == "ExitRole") 
				{
							$("#alertsBox").html("");
 		 					$("#alertsBox").html("This role is already there");
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
				
				 else if (e.type == "create") {
					 $("#alertsBox").html("");
	 					$("#alertsBox").html("Department access role is created successfully");
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
				 else if (e.type == "destroy") {
					 $("#alertsBox").html("");
	 					$("#alertsBox").html("Department access role is deleted successfully");
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
				 else if (e.type == "update") {
					 $("#alertsBox").html("");
	 					$("#alertsBox").html("Department access role is updated successfully");
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
