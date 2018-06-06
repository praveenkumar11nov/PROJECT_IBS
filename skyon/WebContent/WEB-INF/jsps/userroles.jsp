<%@include file="/common/taglibs.jsp"%>
 
	<br /> <br />
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
	<c:url value="/userroles/usr" var="readUrl1" />
	<%-- <c:url value="/userroles/read" var="readUrl" /> --%>
	<c:url value="/userroles/list" var="roles" />
	<c:url value="/userroles/{rlId}" var="test" />
	<c:url value="/users/getLoginNames" var="loginNamesUrl" />

	<div id="panels">
		<br />
		<kendo:grid name="users" selectable="true" height="300px" pageable="true" sortable="true" filterable="true">
			<kendo:grid-sortable allowUnsort="false" mode="single" />
			
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem text=" Clear_Filter "/>
		</kendo:grid-toolbar>
		
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" ></kendo:grid-pageable>
			<kendo:grid-filterable extra="false">
		 		<kendo:grid-filterable-operators>
		  			<kendo:grid-filterable-operators-string eq="Is equal to"/>
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
		
		<label class="category-label" for="categories">Select Role:</label>
		<kendo:dropDownList name="categories" optionLabel="--Select Role--" dataTextField="rlName" dataValueField="rlId" change="categoriesChange">
			<kendo:dataSource>
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
		  			<kendo:grid-filterable-operators-string eq="Is equal to"/>
				</kendo:grid-filterable-operators>			
		</kendo:grid-filterable>
			
			<kendo:grid-columns>
				<%-- <kendo:grid-column  title="Users Names" field="user" template="#=user.urLoginName#"  width="100px"> --%>
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
			
			var result=securityCheckForActionsForStatus("./userRoles/assignButton");   
			   if(result=="success")
			   {
				   var sourcegrid = $('#users').data('kendoGrid'); //SOURCE GRID
					//var destinationgrid = $('#grid').data('kendoGrid'); // DESTINATION GRID
					var roleId = $("#categories").val();
					
					if (roleId == "") {
						//alert("Select Roles");
						$("#alertsBox").html("");
						$("#alertsBox").html("Select Roles");
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
						//alert("Select User to Assign Role");
						$("#alertsBox").html("");
						$("#alertsBox").html("Select User to Assign Role");
						$("#alertsBox").dialog({
							modal: true,
							buttons: {
								"Close": function() {
									$( this ).dialog( "close" );
								}
							}
							}); 
					}$.ajax({
							type : "POST",
							dataType : "text",
							url : "./userroles/update/" + urid + "/" + roleId,
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
					
						var grid = $("#grid").data("kendoGrid");
						grid.dataSource.read();
						grid.refresh();
						
						var grid = $("#grid").data("kendoGrid");
						grid.dataSource.read();
						grid.refresh();
						
						var btn = document.getElementById(copySelectedToGrid2);
			            btn.disabled = false;
			            btn.value = newText;
					
					 	   $('#grid').data('kendoGrid').refresh();
					 	   $('#grid').data('kendoGrid').refresh();
						   $('#users').data('kendoGrid').refresh();
		               	   $('#grid').data('kendoGrid').refresh();
						 
						   var grid = $("#grid").data("kendoGrid");
			               grid.dataSource.page(1);
			               grid.dataSource.read();			
				   
			   }
		}

		//Spring Security for delete Button 
		function deleteButton(){
			var result=securityCheckForActionsForStatus("./userRoles/unassignButton");   
			   if(result=="success")
			   {
				   var sourcegrid = $('#grid').data('kendoGrid'); //SOURCE GRID
					var roleId = $("#categories").val();
					if (roleId == "") {
						//alert("Select Role");
						$("#alertsBox").html("");
						$("#alertsBox").html("Select Role");
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
							url : "./userroles/delete/" + urid + "/" + roleId,
							success : function(response) {
								alert(response);
								var grid = $("#grid").data("kendoGrid");
								grid.dataSource.read();
								grid.refresh();
								grid.refresh();
							}
							});
						 
						   var btn1 = document.getElementById(removeUser);
			               btn1.disabled = true;
			               
			               var grid = $("#grid").data("kendoGrid");
							grid.dataSource.read();
							grid.refresh();
							grid.refresh();
					 
						   var grid = $("#grid").data("kendoGrid");
			               grid.dataSource.page(1);
			               grid.dataSource.read();
			               
			               var btn1 = document.getElementById(removeUser);
			               btn1.disabled = false;
			               
			               $('#grid').data('kendoGrid').refresh();
					 	   $('#grid').data('kendoGrid').refresh();
						   $('#users').data('kendoGrid').refresh();
		               	   $('#grid').data('kendoGrid').refresh();
			               					               
			               grid.dataSource.page(1);
			               grid.dataSource.read();
			               $('#grid').data('kendoGrid').refresh(); 
			   }
		}

	</script>

	<script type="text/javascript">
		function categoriesChange() {
			
			var value = this.value(), grid = $("#grid").data("kendoGrid");
			{
				var url = "";
	
				if (value != "") {				
					url = "./userroles/" + value;
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
					// Replace the grids data source with our new populated data source
					kendoGrid.setDataSource(dataSource);
					//kendoGrid.refresh();
				});
			}// else
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
</style>
 
