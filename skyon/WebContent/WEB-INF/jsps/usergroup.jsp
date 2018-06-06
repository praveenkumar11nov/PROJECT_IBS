<%@include file="/common/taglibs.jsp"%>
 
 

	<style>
#panels {
	display: table-row;
}

#grid1,#grid2 {
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
	<c:url value="/usergroups/usr" var="readUrl1" />
	<c:url value="/usergroups/read" var="readUrl" />
	<c:url value="/usergroups/list" var="roles" />
	<c:url value="/usergrooups/getdata" var="getData" />
	
	
	<c:url value="/users/getLoginNames" var="loginNamesUrl" />

	<div id="panels">
	<br/>
		<kendo:grid name="users" pageable="true" height="300px"
			scrollable="true" selectable="true" filterable="true">
			<kendo:grid-sortable allowUnsort="false" mode="single" />
			
			<kendo:grid-toolbar>
			<kendo:grid-toolbarItem text="Clear_Filter"/>
		</kendo:grid-toolbar>
		
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" ></kendo:grid-pageable>
			<kendo:grid-filterable extra="false">
		 		<kendo:grid-filterable-operators>
		  			<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains"/>
				</kendo:grid-filterable-operators>			
		</kendo:grid-filterable>
			
			<kendo:grid-columns>
				<kendo:grid-column title="Available Users" field="urLoginName" width="100px">
					<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function propertyNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${loginNamesUrl}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
				
				</kendo:grid-column>
			</kendo:grid-columns>
			<kendo:dataSource pageSize="10">
				<kendo:dataSource-transport>
					<kendo:dataSource-transport-read url="${readUrl1}" dataType="json"
						type="POST" contentType="application/json" />
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

		<div id="commands">
			<div id="add">
				<button id="copySelectedToGrid2" class="k-button" onclick="addButton();">Assign &gt;</button>
			</div>
			<div id="remove">
				<button id="removeUser" class="k-button" onclick="deleteButton();">&lt; UnAssign</button>
			</div>
		</div>

		<label class="category-label" id="groupCat" for="categories">Select Group:</label>
		<kendo:dropDownList name="categories" optionLabel="--Select Group--"
			dataTextField="gr_name" dataValueField="gr_id"
			change="categoriesChange">
			<kendo:dataSource pageSize="20">
				<kendo:dataSource-transport>
					<kendo:dataSource-transport-read url="${roles}" />
				</kendo:dataSource-transport>
			</kendo:dataSource>
		</kendo:dropDownList>

		<kendo:grid name="grid" pageable="true" scrollable="true" sortable="true" selectable="true" height="300px" filterable="true">
		<kendo:grid-sortable allowUnsort="false" mode="single" />
		
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem text="Clear_Filter"/>
		</kendo:grid-toolbar>
		
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" ></kendo:grid-pageable>
			<kendo:grid-filterable extra="false">
		 		<kendo:grid-filterable-operators>
		  			<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains"/>
				</kendo:grid-filterable-operators>			
		</kendo:grid-filterable>
		
			<kendo:grid-columns>
				<kendo:grid-column title="Selected Users" field="urLoginName" width="100px" >
					<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function propertyNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${loginNamesUrl}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
				
				
				
				</kendo:grid-column>
				
				<%-- <kendo:grid-column  title="Users Names" field="urId" width="100px"/> --%>
			</kendo:grid-columns>
			<kendo:dataSource pageSize="10">
				<kendo:dataSource-transport>
					<kendo:dataSource-transport-read url="#" dataType="json"
						type="POST" contentType="application/json" />
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
		<br />
	</div>
	<div id="alertsBox" title="Alert"></div>
	<script>
		$(document).ready(function() {
		});
		

		$("#users").on("click", ".k-grid-Clear_Filter", function(){
		    //custom actions
		    $("form.k-filter-menu button[type='reset']").slice().trigger("click");
			var grid = $("#users").data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
		});	
		
		$("#grid").on("click", ".k-grid-Clear_Filter", function(){
		    //custom actions
		    $("form.k-filter-menu button[type='reset']").slice().trigger("click");
			var grid = $("#grid").data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
		});	

	

		//Spring Security for add button 
		function addButton(){
			
			var result=securityCheckForActionsForStatus("./userGroups/assignButton");   
			   if(result=="success")
			   {
				   var sourcegrid = $('#users').data('kendoGrid'); //SOURCE GRID
					/* var destinationgrid = $('#grid').data('kendoGrid'); // DESTINATION GRID */
					var groupId = $("#categories").val();
					if (groupId == "") {
						//alert("Select Group");
						$("#alertsBox").html("");
						$("#alertsBox").html("Select Group");
						$("#alertsBox").dialog({
							modal: true,
							buttons: {
								"Close": function() {
									$( this ).dialog( "close" );
								}
							}
							}); 
						return false;
					}
					var urid = "";
					sourcegrid.select().each(function() {
						var dataItem = sourcegrid.dataItem($(this));
						urid = JSON.stringify(dataItem.urId);
					});

					if (urid == "") {
						//alert("Select User to Assign Group");
						$("#alertsBox").html("");
						$("#alertsBox").html("Select Available Users to Assign Group");
						$("#alertsBox").dialog({
							modal: true,
							buttons: {
								"Close": function() {
									$( this ).dialog( "close" );
								}
							}
							}); 
					}
					
					
					 $.ajax({
						type : "POST",
						dataType : "text",
						url : "./usergroups/update/" + urid + "/" + groupId,
						success : function(response) {
							alert(response);
							var grid = $("#grid").data("kendoGrid");
							grid.dataSource.read();
							grid.refresh();
							grid.refresh();
						}
						});
					 
					   var btn = document.getElementById(copySelectedToGrid2);
			           btn.disabled = true;
			           btn.value = newText;
					 
					   $('#grid').data('kendoGrid').refresh();
					   $('#grid').data('kendoGrid').refresh();
					   $('#users').data('kendoGrid').refresh();
	               	   $('#grid').data('kendoGrid').refresh();
	               	   
	               	var btn = document.getElementById(copySelectedToGrid2);
			           btn.disabled = false;
			           btn.value = newText;
	               	   
					   var grid = $("#grid").data("kendoGrid");
		               grid.dataSource.page(1);
		               grid.dataSource.read(); 
			   }		
		}

		//Spring Security for delete Button 
		function deleteButton(){
			
			var result=securityCheckForActionsForStatus("./userGroups/unassignButton");   
			   if(result=="success")
			   {
				   var sourcegrid = $('#grid').data('kendoGrid'); //SOURCE GRID
					var groupId = $("#categories").val();
					if (groupId == "") {
						//alert("Select Group");
						$("#alertsBox").html("");
						$("#alertsBox").html("Select Group");
						$("#alertsBox").dialog({
							modal: true,
							buttons: {
								"Close": function() {
									$( this ).dialog( "close" );
								}
							}
							}); 
						return false;
					}
					var urid = "";
					sourcegrid.select().each(function() {
						var dataItem = sourcegrid.dataItem($(this));
						urid = JSON.stringify(dataItem.urId);
					});

					if ($('#grid').data('kendoGrid').dataSource.total() === 0) {
						//alert("Select User to remove");
						$("#alertsBox").html("");
						$("#alertsBox").html("No User Available");
						$("#alertsBox").dialog({
							modal: true,
							buttons: {
								"Close": function() {
									$( this ).dialog( "close" );
								}
							}
							}); 
					}
					else if(urid == ''){
						alert("Select user to remove");
					}
					
					 $.ajax({
							type : "POST",
							dataType : "text",
							url : "./usergroups/delete/" + urid + "/" + groupId,
							success : function(response) {
								alert(response);
								var grid = $("#grid").data("kendoGrid");
								grid.dataSource.read();
								grid.refresh();
								grid.refresh();
							}
							});
						 
						 var grid = $("#grid").data("kendoGrid");
			               grid.dataSource.page(1);
			               grid.dataSource.read();
			               
			               grid.dataSource.page(1);
			               grid.dataSource.read();
			               $('#grid').data('kendoGrid').refresh();
			   }
						
					
		}
	</script>

	<script type="text/javascript">
		function categoriesChange() {
			var groupName = this.text;
			if (groupName == "Default") {
				$("#removeUser").hide();
			}
			var value = this.value(), grid = $("#grid").data("kendoGrid");
			{
				var url = "";
				if (value == "") {
					
					url = "./usergroups/read";
					$("#grid").data("kendoGrid").dataSource.data([]);
					return false;
				} else {
					url = "./usergroups/" + value;
				}
				var dataSource = new kendo.data.DataSource({
					pageSize : 10,
					transport : {
						read : {
							type : "POST",
							dataType : "json",
							url : url,
						}
					}
				});
				dataSource.fetch(function() {
					var data = this.data();
					var kendoGrid;
					if (data.length == 1) {
						kendoGrid = $("#grid").data("kendoGrid");
					} else {
						kendoGrid = $("#grid").data("kendoGrid");
					}
					kendoGrid.setDataSource(dataSource);
					//kendoGrid.refresh();
				});
			}
			{
				grid.dataSource.filter({});
			}
		}	
		
		function categoryDropDownEditor(container, options) {
			$(
					'<input data-text-field="urLoginName" data-value-field="urId" data-bind="value:' + options.field + '"/>')
					.appendTo(container).kendoDropDownList({
						autoBind : false,
						dataSource : {
							transport : {
								read : "${getData}"
							}
						}
					});
		}
		
		
	/* 	jQuery(document).ready(function() {
			
			gridLangConversion();

					}); */
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
}
</style>
 