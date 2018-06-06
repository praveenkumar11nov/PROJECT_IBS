<%@include file="/common/taglibs.jsp"%>
    
    <c:url value="/project/create" var="createUrl" />
	<c:url value="/project/read_projectData" var="readUrl" />
	<c:url value="/project/update_project" var="updateUrl" />
	<c:url value="/project/delete_project" var="destroyUrl" />
 	<c:url value="/address/getCountry" var="countryUrl" />
 	<c:url value="/address/getState" var="stateUrl" />   
  	<c:url value="/address/getProjectLocation" var="projectLocationUrl" />
  	
  	<!-- Blocks Urls -->
  	
    <c:url value="/project/readblock" var="BlocksReadUrl" /> 
    <c:url value="/project/createblock" var="BlockCreateUrl" /> 
    <c:url value="/project/updateblock" var="BlockUpdateUrl" /> 
    <c:url value="/project/deleteblock" var="BlockDeleteUrl" /> 
    
     <c:url value="/project/readallprojectnames" var="readAllProjectNames" />  
     <c:url value="/project/readAllPinCodes" var="readAllPincodesFromProject"/>
     
     
      <kendo:grid name="grid" pageable="true"  detailTemplate="blocksTemplate" sortable="true" filterable="true" resizable="true"  groupable="true" scrollable="true" edit="gridEvent" change="onChange" selectable="true">
      	<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10"></kendo:grid-pageable>	
      	<kendo:grid-filterable extra="false">
			<kendo:grid-filterable-operators>
				<kendo:grid-filterable-operators-string eq="Is equal to"/>
				<kendo:grid-filterable-operators-number eq="Is equal to" gt="Is greather than" gte="IS greather than and equal to" lt="Is less than" lte="Is less than and equal to" neq="Is not equal to"/>
			</kendo:grid-filterable-operators>
		</kendo:grid-filterable>
    	<kendo:grid-editable mode="popup" confirmation="Are you sure to delete that record???"/>
        <kendo:grid-toolbar>
            <kendo:grid-toolbarItem name="create" text="Add Project"/>
            <kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilter()><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>"/>
        </kendo:grid-toolbar>
        <kendo:grid-columns>
           <%--  <kendo:grid-column title="Project Id" field="projectId" width="120px"/> --%>
           <kendo:grid-column title="Project Country" field="projectCountry" hidden="true" editor="projectCountryEditor" filterable="false"  width="175px"/>
            <kendo:grid-column title="Project Country" field="countryName"  filterable="true" width="175px">
            	<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function projectCountryNameFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Country Name",
									dataTextField: "countryName",
                                    dataValueField: "countryId",
									dataSource : {
										transport : {
											read : "${countryUrl}"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
            </kendo:grid-column>
            <kendo:grid-column title="Project State" field="projectState" hidden="true" filterable="false" editor="projectStateEditor" width="175px"/>
            <kendo:grid-column title="Project State" field="stateName"  filterable="false" width="175px">
            	<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function projectStateNameFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter State Name",
									dataTextField: "stateName",
                                    dataValueField: "stateId",
									dataSource : {
										transport : {
											read : "${stateUrl}"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
            </kendo:grid-column>
            <kendo:grid-column title="Project Location" field="projectLocation" hidden="true" filterable="false" editor="projectLocationEditor" width="175px"/>
             <kendo:grid-column title="Project Location" field="projectLocationName"  filterable="false" width="175px">
             	<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function projectLocationNameFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Location Name",
									dataTextField: "projectLocationName",
                                    dataValueField: "projectLocationId",
									dataSource : {
										transport : {
											read : "${projectLocationUrl}"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
             </kendo:grid-column>
            <kendo:grid-column title="Project Name" field="projectName" template="#=ProjectName(data)#"  width="170px">
            	<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function projectNameFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Project Name",
									dataSource : {
										transport : {
											read : "${readAllProjectNames}"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
            </kendo:grid-column>
            <kendo:grid-column title="No Of Block/Towers" field="no_OF_TOWERS"  format="{0:n0}"  width="150px"></kendo:grid-column>
            <%-- <kendo:grid-column title="Villa/Flats/Units" field="no_OF_PROPERTIES" format=""  width="175px" /> --%>
            <kendo:grid-column title="Project Address" field="projectAddress" editor="projectAddressEditor" filterable="false" width="180px" />
            <kendo:grid-column title="Project Pincode" field="project_PINCODE" format="" width="170px">
            	<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function projectLocationNameFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Pincode",
									dataSource : {
										transport : {
											read : "${readAllPincodesFromProject}"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
            </kendo:grid-column>
            
           <%--  <kendo:grid-column title="Created By" field="createdBy" format="" width="130px" />
            <kendo:grid-column title="Last Updated By" field="lastUpdatedBy" format="" width="170px"/> --%>
            
            <kendo:grid-column title="&nbsp;" width="200px" >
            	<kendo:grid-column-command>
            		<kendo:grid-column-commandItem name="edit"/>
            		<kendo:grid-column-commandItem name="destroy" />
            	</kendo:grid-column-command>
            </kendo:grid-column>
        </kendo:grid-columns>
        <kendo:dataSource pageSize="10" requestEnd="onRequestEnd" requestStart="onProjectReqStart">
            <kendo:dataSource-transport>
            
            <kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="GET" contentType="application/json"/>
            <kendo:dataSource-transport-create url="${createUrl}" dataType="json" type="POST" contentType="application/json" />
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
            <kendo:dataSource-schema parse="projectParse">
                <kendo:dataSource-schema-model id="projectId">
                    <kendo:dataSource-schema-model-fields>
                    <%-- <kendo:dataSource-schema-model-field name="projectId" editable="false">
						</kendo:dataSource-schema-model-field> --%>
                        <kendo:dataSource-schema-model-field name="projectName" type="string" >
                        	<kendo:dataSource-schema-model-field-validation  />
                        </kendo:dataSource-schema-model-field>
                        <kendo:dataSource-schema-model-field name="no_OF_TOWERS" type="number" >
                        	<kendo:dataSource-schema-model-field-validation  min="1" />
                        </kendo:dataSource-schema-model-field>
                          <%-- <kendo:dataSource-schema-model-field name="no_OF_PROPERTIES" type="number">
                        	<kendo:dataSource-schema-model-field-validation required="true" min="1" />
                        </kendo:dataSource-schema-model-field> --%>
                        <kendo:dataSource-schema-model-field name="projectAddress" type="string">
                        	<kendo:dataSource-schema-model-field-validation   />
                        </kendo:dataSource-schema-model-field>
                        <kendo:dataSource-schema-model-field name="project_PINCODE" type="string">
                        	<kendo:dataSource-schema-model-field-validation  min="1" />
                        </kendo:dataSource-schema-model-field>
                        <kendo:dataSource-schema-model-field name="projectCountry" type="number">
                        	<kendo:dataSource-schema-model-field-validation />
                        </kendo:dataSource-schema-model-field>
                         <kendo:dataSource-schema-model-field name="projectState" type="string">
                        	<kendo:dataSource-schema-model-field-validation   />
                        </kendo:dataSource-schema-model-field>
                        <kendo:dataSource-schema-model-field name="projectLocation" >
                        	<kendo:dataSource-schema-model-field-validation   />
                        </kendo:dataSource-schema-model-field>
                    </kendo:dataSource-schema-model-fields> 
                </kendo:dataSource-schema-model>
            </kendo:dataSource-schema>
        </kendo:dataSource>
    </kendo:grid>    
    
    
    <kendo:grid-detailTemplate id="blocksTemplate">
    <br/>
          <!-- <a class="k-button k-button-icontext" id="importFile_#=projectId#" onclick="importFileClick()" ><span class="k-icon k-add"></span>Import From Excel</a> -->
         
     <br/>
   <!--    <input type='file'   name='files' id='files' onclick="importFileClick()"> -->
    	<kendo:grid name="gridBlocks_#=projectId#" edit="gridBlocksEvent"  pageable="true" resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true">
								<kendo:grid-pageable pageSize="10"></kendo:grid-pageable>
								<kendo:grid-editable mode="popup" confirmation="Are you sure you want to remove this item?" />
						    <kendo:grid-toolbar>
		<kendo:grid-toolbarItem name="create" text="Add Blocks" />
		<%-- <kendo:grid-toolbarItem name="files" text="Select Files" click="uploadAsset" /> --%>
		<%-- <kendo:grid-toolbarItem	template="<input type='file' name='files' id='files' />" /> --%>
						            <%-- <kendo:grid-toolbarItem text="Clear Filter" name="Clear_Filter" /> --%>
						          <%--   <kendo:grid-toolbarItem text="Import" name="Import_File"></kendo:grid-toolbarItem> --%>
						 	<kendo:grid-toolbarItem	template="<input type='file' name='files' id='files' onclick='importFileClick()' />" /> 		            
						        </kendo:grid-toolbar>
        						<kendo:grid-columns>
									     <kendo:grid-column title="Tower/Block Name"  width="100px" field="blockName"></kendo:grid-column>
									     <kendo:grid-column title="No of properties" format="{0:n0}" field="numOfProperties" filterable="true" width="100px" />
									     <kendo:grid-column title="No of parking slots" format="{0:n0}" field="numOfParkingSlots" filterable="true" width="100px" />
        								 <kendo:grid-column title="&nbsp;" width="172px" >
 							            	<kendo:grid-column-command>
							            		<kendo:grid-column-commandItem name="edit"/>
							            		<kendo:grid-column-commandItem name="destroy" />
							            	</kendo:grid-column-command>
							            </kendo:grid-column>
        						</kendo:grid-columns>
        						 <kendo:dataSource requestEnd="blocksRequestEnd" requestStart="onBlocksRequestStart">
						            <kendo:dataSource-transport>
						                <kendo:dataSource-transport-read  url="${BlocksReadUrl}/#=projectId#" dataType="json"  type="GET" contentType="application/json"/>
						                <kendo:dataSource-transport-create url="${BlockCreateUrl}/#=projectId#" dataType="json" type="POST"  contentType="application/json" />
						                <kendo:dataSource-transport-update url="${BlockUpdateUrl}/#=projectId#" dataType="json" type="POST" contentType="application/json" />
						                <kendo:dataSource-transport-destroy url="${BlockDeleteUrl}" dataType="json" type="POST" contentType="application/json" />
						               <kendo:dataSource-transport-parameterMap>
						                	<script>
							                	function parameterMap(options,type) 
							                	{
							                		return JSON.stringify(options);	                		
							                	}
						                	</script>
						                 </kendo:dataSource-transport-parameterMap>
						            </kendo:dataSource-transport>
						            <kendo:dataSource-schema parse="blocksParse">
						                <kendo:dataSource-schema-model id="blockId">
						                    <kendo:dataSource-schema-model-fields>
							                    <kendo:dataSource-schema-model-field name="blockId" type="number" />
							                     <kendo:dataSource-schema-model-field name="projectId" type="number" />
						                    	<%-- <kendo:dataSource-schema-model-field name="personId" type="number"></kendo:dataSource-schema-model-field>   --%> 
						                    	<kendo:dataSource-schema-model-field name="blockName" type="string">
						                    		<kendo:dataSource-schema-model-field-validation required = "true"/>
						                    	</kendo:dataSource-schema-model-field>
						                    	<kendo:dataSource-schema-model-field name="numOfProperties" type="number" >
						                    			<kendo:dataSource-schema-model-field-validation required = "true" min="1"/>
						                    	</kendo:dataSource-schema-model-field>
						                    	<kendo:dataSource-schema-model-field name="numOfParkingSlots" type="number">
						                    			<kendo:dataSource-schema-model-field-validation required = "true" min="1"/>
						                    	</kendo:dataSource-schema-model-field>
						                    </kendo:dataSource-schema-model-fields>
						                 </kendo:dataSource-schema-model>
						             </kendo:dataSource-schema>
						          </kendo:dataSource>
        				</kendo:grid>
    
    </kendo:grid-detailTemplate>
    <div id="alertsBox" title="Alert"></div>
    <div id="dialogBoxforFamilyMembers" title="Associated Family Members">
</div>
    <div id="uploadDialog" title="Upload Document" hidden="true"></div>



<script>

function importFileClick()
{	
//	alert("Funt() for import");
	/* $('#uploadDialog').dialog({
		modal : true,
	}); */
	
	$("#files").kendoUpload({
        multiple: true,
        select:selectFileType,
        success : onDocSuccess,
        error : errorS,
        
        async: {
        	
            saveUrl:"./blocks/upload/"+SelectedRowId,
            autoUpload: true
        }   
    });
	function onDocSuccess(e){
     /*   	alert("File Imported Successfully");
       	window.location.reload();
       	 */
   	 if (e.response.status == "cannotImport") {
			errorInfo = "";
			errorInfo = e.response.result.cannotImport;
			for (var i = 0; i < e.response.result.length; i++) {
				errorInfo += (i + 1) + ". "
						+ e.response.result[i].defaultMessage;
			}

			$("#alertsBox").html("");
			$("#alertsBox").html(errorInfo);
			$("#alertsBox").dialog({
				modal : true,
				draggable: false,
				resizable: false,
				buttons : {
					"Close" : function() {
						$(this).dialog("close");
					 	window.location.reload();
					}
				}
			});	
			
		  

		}
       	
    }
    function errorS(e){
     
    	var errorInfo="Uploaded file format is not proper(Acceptable formats are .xlsx & .xls) OR it does not contain valid data";
		$("#alertsBox").html("");
		$("#alertsBox").html(errorInfo);
		$("#alertsBox").dialog({
			modal : true,
			draggable: false,
			resizable: false,
			buttons : {
				"Close" : function() {
					$(this).dialog("close");
				 	window.location.reload();
				}
			}
		});	
      	
    }
    
    function selectFileType(e){
    	
    	var files = e.files;
    	// Check the extension of each file and abort the upload if it is not .jpg,.jpeg and .png
    	$.each(files, function() {
    		if (this.extension === ".xlsx") {
    			
    		}
    		else if (this.extension === ".xls") {
    			
    		}
    		
    		
    		else {
    			
    			var errorInfo="Only Excel files with extention (.xlsx & .xls) can be uploaded";
    			$("#alertsBox").html("");
    			$("#alertsBox").html(errorInfo);
    			$("#alertsBox").dialog({
    				modal : true,
    				draggable: false,
    				resizable: false,
    				buttons : {
    					"Close" : function() {
    						$(this).dialog("close");
    					 	window.location.reload();
    					}
    				}
    			});	
    			
    		}
    	});

    }
   
}

var SelectedRowId = "";
function onChange(arg) 
{
	 var gview = $("#grid").data("kendoGrid");
 	 var selectedItem = gview.dataItem(gview.select());
 	 SelectedRowId = selectedItem.projectId;
 	 projectName =  selectedItem.projectName;
 	 NoOfBlocks = selectedItem.no_OF_TOWERS;
 	// alert(SelectedRowId);
 	this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
     //alert("Selected: " + SelectedRowId);
	//alert(NoOfBlocks);
	
	
	
	
	

	/* var gridBlocks = $("#gridBlocks_"+SelectedRowId).data("kendoGrid");
	//var grid = $("#SearchWindowGrid").data("kendoGrid");
	gridBlocks.dataSource.read();
    var count = gridBlocks.dataSource.total();
    alert(count); */
}
/* $(document).ready(function () 
	    {
	
	        $("#files_").kendoUpload({
	            multiple: true,
	            success : onDocSuccess,
	            error : errorS,
	            async: {
	            	
	                saveUrl:"./blocks/upload/"+SelectedRowId,
	                autoUpload: true
	            }
	       
	        });
	       
	    }); */  
	        
	    /* function onDocSuccess(){
	       	alert("File Imported Successfully");
	       	window.location.reload();
	    }
	    function errorS(){
	      	alert("File Importing Failed");
	      	window.location.reload();
	    } */ 
	    
	     /* $("#gridBlocks_"+SelectedRowId).on("click", "k-grid-files", function() 
	    		 {
	    				$('#uploadDialog').dialog({
	    					modal : true,
	    				}); 
	    		 }); */
    </script>
    <script type="text/javascript">
   

    /* var SelectedRowId = "";*/
    var NoOfBlocks = "";
    var projectName = "";

    var test1 = "";
	  var flag = "";
	  var name = "";
	  var res = [];
	  var editingName = ""; 

	
	//Clear Filter_Button_Action Function
	$("#gridBlocks_").on("click", ".k-grid-Clear_Filter", function()
	{
	   	//custom actions
		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
		var grid = $("#bankStatementGrid").data("kendoGrid");
		grid.dataSource.read();
		grid.refresh();
	});
	
	//Clear Filter Before Adding
	$("#gridBlocks_").on("click", ".k-grid-add", function() 
	{
		if($("#bankStatementGrid").data("kendoGrid").dataSource.filter())
		{
    	   $("form.k-filter-menu button[type='reset']").slice().trigger("click");
    	   var grid = $("#bankStatementGrid").data("kendoGrid");
    	   grid.dataSource.read();
    	   grid.refresh();
        }
	});
	function clearFilter()
	{
		  $("form.k-filter-menu button[type='reset']").slice().trigger("click");
		  var gridDocumentDefiner = $("#grid").data("kendoGrid");
		  gridDocumentDefiner.dataSource.read();
		  gridDocumentDefiner.refresh();
	}
		
	function ProjectName(data) 
	  {
			$.each(data, function( index, value ) 
			{
				if(index == "projectName" && value != "" && name != value)
				{
				        if ($.inArray(value, res) == -1) res.push(value);	
				}
				
			});		
			return data.projectName;
	  }	
		var eventStatus = "";
		var projectNames = [];
		function gridEvent(e)
		{
			//$(".k-select").hide();
			eventStatus = e.model.isNew();
			//alert(eventStatus);
			$('div[data-container-for="countryName"]').remove();
			$('label[for="countryName"]').closest('.k-edit-label').remove();
			$('div[data-container-for="stateName"]').remove();
			$('label[for="stateName"]').closest('.k-edit-label').remove();
			$('div[data-container-for="projectLocationName"]').remove();
			$('label[for="projectLocationName"]').closest('.k-edit-label').remove();
			if(e.model.isNew())
				{
					$(".k-window-title").text("Add New Project");
			  		$(".k-grid-update").text("Save");
				}
			else
				{
					$(".k-window-title").text("Edit Project Details");
			  		$(".k-grid-update").text("Update"); 
			  		projectNames.splice(projectNames.indexOf(e.model.projectName),1);
			  		//alert(projectNames);
				}
			
		}
		
		 function projectParse (response) 
		  {   
			    var data = response; //<--data might be in response.data!!!
			    projectNames = [];
			     
				 for (var idx = 0, len = data.length; idx < len; idx ++)
				 {
					 projectNames.push(data[idx].projectName);
				 }
				// alert(projectNames);
				 return response;
		 }
		 
		 var blocksCount = 0;
		 function blocksParse(response)
		 {
			 var data = response;
			 blocksCount = data.length;
			 return response;
		 }
		 
		function projectAddressEditor(container, options) 
		{
			$('<textarea data-text-field="address1" name="address1" data-value-field="address1" data-bind="value:' + options.field + '" style="width: 148px; height: 40px;"/>')
					.appendTo(container);
		}

		function projectCountryEditor(container, options) 
		{
			$('<input data-text-field="countryName" name="countryName" required="true" validationmessage="Project Country is required" data-value-field="countryId" id="countryId" data-bind="value:' + options.field + '"/>')
					.appendTo(container).kendoDropDownList({
						autoBind : false,
						optionLabel : "Select",
						dataSource : {
							transport : {
								read : "${countryUrl}"
							}
						}
					});
			$('<span class="k-invalid-msg" data-for="countryName"></span>').appendTo(container);
		}

		
		function projectStateEditor(container, options)
		 {
			$('<input data-text-field="stateName" name="stateName" required="true" validationmessage="Project State is required" id="stateId" data-value-field="stateId" data-bind="value:' + options.field + '"/>')
					.appendTo(container).kendoDropDownList({
						cascadeFrom : "countryId",
						optionLabel : "Select",
						autoBind : false,
						dataSource : {
							transport : {
								read : "${stateUrl}"
							}
						}
					});
			$('<span class="k-invalid-msg" data-for="stateName"></span>').appendTo(container);
		}

		function projectLocationEditor(container, options) 
		{
			$('<input data-text-field="projectLocationName" name="projectLocationName" required="true" validationmessage="Project Location is required"  data-value-field="projectLocationId" data-bind="value:' + options.field + '"/>')
					.appendTo(container).kendoComboBox({
						cascadeFrom : "stateId",
						autoBind : false,
						optionLabel : "Select",
						dataSource : {
							transport : {
								read : "${projectLocationUrl}"
							}
						}
					});
			$('<span class="k-invalid-msg" data-for="projectLocationName"></span>').appendTo(container);
		}


	  (function ($, kendo) 
			   	{   	  
			        $.extend(true, kendo.ui.validator, 
			        {
			             rules: 
			             { // custom rules          	
			                 ProjectNameValidation: function (input, params) 
			                 {               	 
			                     //check for the name attribute 
			                     if (input.filter("[name='projectName']").length && input.val()) 
			                     {
			                         return /^[A-Z]+[a-z A-Z 0-9]*[_]{0,1}[a-z A-Z 0-9]*[^_]$/.test(input.val());
			                     }        
			                     return true;
			                 },
			                 projectName: function (input, params) 
			                 {
			                     //check for the name attribute 
			                     if (input.attr("name") == "projectName") 
				                 {
			                      	return $.trim(input.val()) !== "";
			                     }
			                     return true;
			                 },
			                 no_OF_TOWERS: function (input, params) 
			                 {
			                     //check for the name attribute 
			                     if (input.attr("name") == "no_OF_TOWERS") 
				                 {
			                      	return $.trim(input.val()) !== "";
			                     }
			                     return true;
			                 },
			                 address1: function (input, params) 
			                 {
			                     //check for the name attribute 
			                     if (input.attr("name") == "address1") 
				                 {
			                      	return $.trim(input.val()) !== "";
			                     }
			                     return true;
			                 },
			                 project_PINCODE: function (input, params) 
			                 {
			                     //check for the name attribute 
			                     if (input.attr("name") == "project_PINCODE") 
				                 {
			                      	return $.trim(input.val()) !== "";
			                     }
			                     return true;
			                 },   
			                 pincode: function (input, params) 
				             {
			                    	 if (input.filter("[name='project_PINCODE']").length && input.val()) {
											return /^[0-9]{6,6}$/
													.test(input.val());
										}
										return true;
			                 },

			                 projectLocation: function (input, params) 
				             {
			                    	 if (input.filter("[name='projectLocation']").length && input.val()) {
											return /^[a-zA-Z0-9 ]{1,40}$/
													.test(input.val());
										}
										return true;
			                 },
			                 
			                 ProjectNameUniqueness : function(input, params){
									if (input.filter("[name='projectName']").length && input.val()) 
									{
										var flag = true;
										$.each(projectNames, function(idx1, elem1) {
											//alert(elem1+"----"+input.val());
											if(elem1.toLowerCase() == input.val().toLowerCase())
											{
												flag = false;
											}	
										});
										return flag;
									}
									return true;
								}
			             },
			             messages: 
			             { //custom rules messages
			            	 ProjectNameValidation: "Project Name should start with a Capital letter, can contain alphanumeric characters and should not contain any special charecters except atmost one underscore (_) but should not end with it and minimum length is 2",
			            	 pincode : "Pincode must be 6 digit number",
			            	 projectLocation : "Only alphanumeric allowed",
			            	 projectName : "Project Name is required",
			            	 no_OF_TOWERS : "No Of Blocks/Towers is required",
			            	 address1 : "Address is required",
			            	 project_PINCODE : "Pincode is required",
			            	 ProjectNameUniqueness : "Project Name already exists"
			             }
			        });
			        
			    })(jQuery, kendo);

	  function onRequestEnd(e)
	   {
		   if (typeof e.response != 'undefined')
				{
			if (e.response.status == "FAIL") {
				errorInfo = "";
				errorInfo = e.response.result.invalid;
				for (i = 0; i < e.response.result.length; i++) {
					errorInfo += (i + 1) + ". "
							+ e.response.result[i].defaultMessage;
				}

				if (e.type == "create") {
					alert("Error: Creating the Project\n\n" + errorInfo);
				}

				if (e.type == "update") {
					alert("Error: Updating the Project\n\n" + errorInfo);
				}

				var grid = $("#grid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
				return false;
			}


			else  if (e.response.status == "PROJECT_NAME_EXISTS") 
			{
				errorInfo = "";
				errorInfo = e.response.result.projectNameAlreadyExists;

				if (e.type == "create") {
					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Creating Project<br>" +errorInfo);
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
					$("#alertsBox").html(
							"Error: Updating Project<br>" + errorInfo);
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

			else if (e.type == "update") {
				//alert("Update record is successfull");
				
				$("#alertsBox").html("");
				$("#alertsBox").html("Project details updated is successfully");
				$("#alertsBox").dialog({
					modal : true,
					dragable : false,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				$("#alertsBox").draggable({disabled:false});
				var grid = $("#grid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			}

			else if (e.type == "create") {
				//alert("Create record is successfull");
				$("#alertsBox").html("");
				$("#alertsBox").html("Project created successfully");
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
			else if (e.type == "destroy") 
			{
				if (e.response.status == "CHILD_FOUND_EXCEPTION") 
				{
					errorInfo = "";
					errorInfo = e.response.result.childFoundException;

					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Delete Project<br>" + errorInfo);
					$("#alertsBox").dialog
					({
						modal : true,
						buttons : 
						{
							"Close" : function() 
							{
								$(this).dialog("close");
							}
						}
					});
					var grid = $("#grid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
					return false;
				}
				else
				{
				$("#alertsBox").html("");
				$("#alertsBox").html("Project deleted successfully");
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
		}


	  function blocksRequestEnd(e)
	   {
		   if (typeof e.response != 'undefined')
				{
			if (e.response.status == "FAIL") 
			{
				errorInfo = "";
				errorInfo = e.response.result.invalid;
				for (i = 0; i < e.response.result.length; i++) {
					errorInfo += (i + 1) + ". "
							+ e.response.result[i].defaultMessage;
				}

				if (e.type == "create") {
					alert("Error: Creating the Project\n\n" + errorInfo);
				}

				if (e.type == "update") {
					alert("Error: Updating the Project\n\n" + errorInfo);
				}

				var grid = $("#grid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
				return false;
			}

			if (e.type == "update" && !e.response.Errors) 
			{
				//alert("Update record is successfull");
				$("#alertsBox").html("");
				$("#alertsBox").html("Block details updated is successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				var grid =  $("#gridBlocks_"+SelectedRowId).data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			}

			if (e.type == "create") 
			{
				//alert("Create record is successfull");
				$("#alertsBox").html("");
				$("#alertsBox").html("Block created successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});

				var grid =  $("#gridBlocks_"+SelectedRowId).data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			}
			if (e.type == "destroy") 
			{
				if (e.response.status == "CHILD_FOUND_EXCEPTION") 
				{
					errorInfo = "";
					errorInfo = e.response.result.childFoundException;

					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Delete Project<br>" + errorInfo);
					$("#alertsBox").dialog
					({
						modal : true,
						buttons : 
						{
							"Close" : function() 
							{
								$(this).dialog("close");
							}
						}
					});
					var grid = $("#grid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
					return false;
				}
				else
				{
					$("#alertsBox").html("");
					$("#alertsBox").html("Block deleted successfully");
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
	
					var grid = $("#gridBlocks_"+SelectedRowId).data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
				}
			}

				}
		}

		
		function gridBlocksEvent(e)
		{
		 	if(e.model.isNew())
			{
		 		$(".k-window-title").text("Add New Block");
		  		$(".k-grid-update").text("Save");
		  		
		  		if(blocksCount == NoOfBlocks)
			    {
			        var grid = $("#gridBlocks_"+SelectedRowId).data("kendoGrid");
					grid.cancelRow();
					$("#alertsBox").html("");
					$("#alertsBox").html("Only "+NoOfBlocks+" block(s) is allowed to add in this project");
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
					return false;
			    }
			}
			else
			{
				$(".k-window-title").text("Edit Block Details");
		  		$(".k-grid-update").text("Update");
			}
		}
		
		
		

		function onProjectReqStart(e)
		{
			
			$('.k-grid-update').hide();
			$('.k-edit-buttons')
					.append(
							'<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
			$('.k-grid-cancel').hide();
			/* 
			 var gridStoreMaster = $("#grid").data("kendoGrid");
			 gridStoreMaster.cancelRow(); */
		}

		function onBlocksRequestStart(e)
		{
			
			
			
			$('.k-grid-update').hide();
			$('.k-edit-buttons')
					.append(
							'<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
			$('.k-grid-cancel').hide();
			//alert("Start");
			 /* var gridBlocks = $("gridBlocks_"+SelectedRowId).data("kendoGrid");
			 if(gridBlocks != null)
  			 {
				
				 gridBlocks.cancelRow();
  			 } */ 
		}

</script>
    
    <style>
    .k-edit-label:after { content:" *"; }
</style>
    
    
  <!-- </div>
 
 </div> -->