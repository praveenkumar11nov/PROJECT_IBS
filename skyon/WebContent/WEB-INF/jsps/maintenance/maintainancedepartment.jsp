<%@include file="/common/taglibs.jsp"%>


<style>
#panels {
	display: table-row;
}

#users,#departments {
	width: 400px;
	display: table-cell;
}

#commands {
	text-align: center;
	width: 50px;
	vertical-align: middle;
	display: table-cell;
}

#commands div {
	padding: 5px;
}

#commands div a {
	width: 35px;
	text-align: center;
}
</style>
	
<c:url value="/department/list" var="departments" />	
<c:url value="/users/getLoginNames" var="loginNamesUrl" />	
<c:url value="/userroles/usr" var="readAllUsers" />
<c:url value="/getmaintenanceusers" var="readAssignedUsersUrl" />
<c:url value="/jobcardsdetails/getJobTypes" var="jobtypes" />


<%
       String headerTemplate = "<div class='dropdown-header'>" +
               "<span class='k-widget k-header'>Photo</span>" +
               "<span class='k-widget k-header'>Contact info</span>" +
           "</div>";

       String template ="<span class='k-state-default'><h5>#: data.dept_Name #</h5><p>#: data.dept_Status #</p></span>";
%>

<div style="margin:30px;">
   
    <div style="padding-bottom:20px;float:center;">
	    <label class="category-label" for="categories">Select Department</label>
		<kendo:comboBox id="deptCombo" name="departmentsCombo" style="width:200px" filter="startswith" headerTemplate="<%=headerTemplate%>" template="<%=template%>" dataTextField="dept_Name" dataValueField="dept_Id" change="departmentsComboChange">
				<kendo:dataSource>
					<kendo:dataSource-transport>
						<kendo:dataSource-transport-read url="${departments}" />
					</kendo:dataSource-transport>
				</kendo:dataSource>
		</kendo:comboBox>
	</div>
	
	<div style="padding-bottom:20px;float:center;">
	    <label class="category-label" for="categories">Select Job Type&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
		<kendo:comboBox id="jobType" name="jobType" style="width:200px" filter="startswith"  dataTextField="jobType" dataValueField="jobType">
				<kendo:dataSource>
					<kendo:dataSource-transport>
						<kendo:dataSource-transport-read url="${jobtypes}" />
					</kendo:dataSource-transport>
				</kendo:dataSource>
		</kendo:comboBox>		
	</div>
	
	<div id="panels">	
		<div>
			<kendo:grid name="users" selectable="true" height="300px" pageable="true" sortable="true" filterable="true">
			
				<kendo:grid-pageable  buttonCount="5" pageSize="10" numeric="true" refresh="true"></kendo:grid-pageable>
				
				<kendo:grid-filterable extra="false">
					<kendo:grid-filterable-operators>
						<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains"/>
					</kendo:grid-filterable-operators>
		       </kendo:grid-filterable>	
		       		
				<kendo:grid-columns>
						<kendo:grid-column title="Users" field="urLoginName" width="100px"/>							
				</kendo:grid-columns>		
			</kendo:grid>
		</div>
		
		<div id="commands">
			<div id="add">
					<button id="assignButton" class="k-button" onclick="assignButton();">Assign</button>
			</div>
			<div id="remove">
					<button id="removeButton" class="k-button" onclick="removeButton();">Un Assign</button>		
			</div>
		</div>
		
	
	
	
		<div>		
			<kendo:grid name="departments" pageable="true" scrollable="true" sortable="true" selectable="true" height="300px" filterable="true">
				
			<kendo:grid-pageable  buttonCount="3" pageSize="10" numeric="true" refresh="true"></kendo:grid-pageable>
				<kendo:grid-filterable extra="false">
			 		<kendo:grid-filterable-operators>
			  			<kendo:grid-filterable-operators-string eq="Is equal to"/>
					</kendo:grid-filterable-operators>			
			</kendo:grid-filterable>
				
				<kendo:grid-columns>
					<kendo:grid-column title="Assigned Users" field="urLoginName" width="150px" filterable="false" />	
					<kendo:grid-column title=" From Depaertment" field="department" width="150px" filterable="false"/>					
					<kendo:grid-column title="Assigned For Job Type" field="jobtype" width="200px" filterable="false"/>					
				</kendo:grid-columns>
				<kendo:dataSource pageSize="10">
					<kendo:dataSource-transport>
						<kendo:dataSource-transport-read url="${readAssignedUsersUrl}" dataType="json" type="POST" contentType="application/json" />
						<kendo:dataSource-transport-parameterMap>
							<script>
								function parameterMap(options, type) {
									return JSON.stringify(options);
								}
							</script>
						</kendo:dataSource-transport-parameterMap>
					</kendo:dataSource-transport>
				</kendo:dataSource>
			</kendo:grid>
		</div>
		
	</div>
</div>

 

<div id="alertsBox" title="Alert"></div>

<script>
				
	function departmentsComboChange() {
		
		 var value = this.dataItem().dept_Id;		
		 var dataSource = new kendo.data.DataSource({
				pageSize : 10,
				transport : {
					read : {
						type : "GET",						
						url :"./users/listusers/"+value,
					}
				}
		});
		dataSource.fetch(function() {			
				$("#users").data("kendoGrid").setDataSource(dataSource);				
		});			
		
	}
	
	function assignButton(){
		
		var result=securityCheckForActionsForStatus("./maintainance/department/assignButton");
		
		if(result=="success"){			
			
			var usersgrid = $('#users').data('kendoGrid'); 
			var departmentCombo = $("#deptCombo").data("kendoComboBox");	 
			var jobType = $("#jobType").data("kendoComboBox");	 
			var deptId=$("#deptCombo").data("kendoComboBox").value();
			
			var urid=null;
			usersgrid.select().each(function() {
				var dataItem = usersgrid.dataItem($(this));
				urid = JSON.stringify(dataItem.urId);
			});	
			
			if(departmentCombo.select() == -1) {
				$("#alertsBox").html("");
				$("#alertsBox").html("Please Select Proper Department");
				$("#alertsBox").dialog({
					modal: true,
					buttons: {
						"Close": function() {
							$( this ).dialog( "close" );
						}
					}
				}); 
			}else if(jobType.select() == -1) {
				$("#alertsBox").html("");
				$("#alertsBox").html("Please Select Job Type");
				$("#alertsBox").dialog({
					modal: true,
					buttons: {
						"Close": function() {
							$( this ).dialog( "close" );
						}
					}
				}); 
			}
			else if (urid == "" || urid==null) {			
				$("#alertsBox").html("");
				$("#alertsBox").html("Please Select Available Users");
				$("#alertsBox").dialog({
					modal: true,
					buttons: {
						"Close": function() {
							$( this ).dialog( "close" );
						}
					}
				}); 
			}else {
				var result=null;
				$.ajax({
					type : "POST",
					async: false,
					url : "./maintainance/updateDepartment/" + urid + "/" + deptId +"/"+jobType.value(),
					dataType:'text',
					success : function(response) {							
						if(response=="success"){
							result="User Assigned Successfully";
						}else if(response=="exists"){
							result="User Already Exists/Assigned";
						}else{
							result="Unable To Assign User";
						}					
					}
				});
				$("#alertsBox").html("");
				$("#alertsBox").html(result);
				$("#alertsBox").dialog({
					modal: true,
					buttons: {
						"Close": function() {
							$( this ).dialog( "close" );
						}
					}
				}); 
			}
			
			var grid = $("#departments").data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
		}
		
	}
	
	function removeButton(){		
		
		var result=securityCheckForActionsForStatus("./maintainance/department/unassignButton");
		
		if(result=="success"){
			var usersgrid = $('#users').data('kendoGrid'); 
			var departmentCombo = $("#deptCombo").data("kendoComboBox");	 
			var deptId=$("#deptCombo").data("kendoComboBox").value();
			
			var urid=null;
			usersgrid.select().each(function() {
				var dataItem = usersgrid.dataItem($(this));
				urid = JSON.stringify(dataItem.urId);
			});	
			
			 if(departmentCombo.select() == -1) {
				$("#alertsBox").html("");
				$("#alertsBox").html("Please Select Proper Department");
				$("#alertsBox").dialog({
					modal: true,
					buttons: {
						"Close": function() {
							$( this ).dialog( "close" );
						}
					}
				}); 
			}else if (urid == "" || urid==null) {			
				$("#alertsBox").html("");
				$("#alertsBox").html("Please Select Available Users");
				$("#alertsBox").dialog({
					modal: true,
					buttons: {
						"Close": function() {
							$( this ).dialog( "close" );
						}
					}
				}); 
			}else{
				var result=null;
				$.ajax({
					type : "POST",
					async: false,
					url : "./maintainance/removeDepartment/" + urid + "/" + deptId,
					dataType:'text',
					success : function(response) {					
						if(response=="success"){
							result="User Un Assigned Successfully";
						}else{
							result="Not able to Un-Assign User OR User Already Un-Assigned";
						}					
					}
				});
				$("#alertsBox").html("");
				$("#alertsBox").html(result);
				$("#alertsBox").dialog({
					modal: true,
					buttons: {
						"Close": function() {
							$( this ).dialog( "close" );
						}
					}
				}); 
			}
			
			var grid = $("#departments").data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
		}
		 
	}
	
	$( document ).ready(function() {
		$("#deptCombo").data("kendoComboBox").value("");
	});

		
</script>
<style scoped="scoped">
#grid .k-toolbar {
	min-height: 27px;
	padding: -2.78em;
}

.category-label {
	vertical-align: middle;
	padding-right: .5em;
}

#category {
	vertical-align: middle;
}

.toolbar {
	float: right;
}

.k-button {
	height: 27px;
	text-align: center;
	width: 92px;
}


</style>
 