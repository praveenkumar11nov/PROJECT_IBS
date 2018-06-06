<%@include file="/common/taglibs.jsp"%>

<c:url value="/unmeteredPoints/create" var="createUrl" />
<c:url value="/unmeteredPoints/update" var="updateUrl" />
<c:url value="/unmeteredPoints/destroy" var="destroyUrl" />
<c:url value="/unmeteredPoints/read" var="readUrl" />

<c:url value="/unmeteredPoints/childReadUrl" var="childReadUrl" />
<c:url value="/unmeteredPoints/childCreateUrl" var="childCreateUrl" />
<c:url value="/unmeteredPoints/childUpdateUrl" var="childUpdateUrl" />

<c:url value="/unmeteredPoints/filter" var="commonFilterForUnmeteredParameterMasterUrl" />
<c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />
<c:url value="/common/getAllChecks" var="allChecksUrl" />

<kendo:grid name="grid" remove="unmeteredPointsDeleteEvent" pageable="true" resizable="true" detailTemplate="unMeteredPointsTemplate" change="onChangeUser" edit="unMeteredEdit" sortable="true" reorderable="true" selectable="true" scrollable="true"	filterable="true" groupable="true">
<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="5" input="true" numeric="true" refresh="true" info="true" previousNext="true">
		<kendo:grid-pageable-messages itemsPerPage="Items per page" empty="No items to display" refresh="Refresh all the items" 
			display="{0} - {1} of {2} New items" first="Go to the first page of items" last="Go to the last page of items" next="Go to the next page of items"
			previous="Go to the previous page of items"/>
</kendo:grid-pageable>		
      <kendo:grid-filterable extra="false">
	    <kendo:grid-filterable-operators>
		 <kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains"/>
	    </kendo:grid-filterable-operators>
	  </kendo:grid-filterable>	
	<kendo:grid-editable mode="popup"/>
	
	<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Create Unassessed Points" />
		    <kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterItemMaster()>Clear Filter</a>"/>
	</kendo:grid-toolbar>
	<kendo:grid-columns>
			<kendo:grid-column title="InsId" field="id" hidden="true" width="120px"/>
			<kendo:grid-column title="Name *" field="insName"  width="120px">
			<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function apCodeFilter(element) {
								element.kendoAutoComplete({
									dataSource : {
										transport : {
											read : "${commonFilterForUnmeteredParameterMasterUrl}/insName"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			<kendo:grid-column title="Location *" field="insLocation" width="120px">
			<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function apCodeFilter(element) {
								element.kendoAutoComplete({
									dataSource : {
										transport : {
											read : "${commonFilterForUnmeteredParameterMasterUrl}/insLocation"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			<kendo:grid-column title="Metered" field="meteredStatus" width="100px" editor="dropDownChecksEditor">
			<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function apCodeFilter(element) {
								element.kendoAutoComplete({
									dataSource : {
										transport : {
											read : "${commonFilterForUnmeteredParameterMasterUrl}/meteredStatus"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			<kendo:grid-column title="Meter Constant *" field="meteredConstant" width="100px"/>
			
			<kendo:grid-column title="Status" field="status" width="100px" filterable="true" >
			 <kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function ledgerStatusFilter(element) {
								element
										.kendoDropDownList({
											optionLabel : "Select status",
											dataSource : {
												transport : {
													read : "${filterDropDownUrl}/servicePointsStatus"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="&nbsp;" width="175px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit"/>
					<kendo:grid-column-commandItem name="destroy" />
					
				</kendo:grid-column-command>
			</kendo:grid-column>
			
	<<kendo:grid-column title=""
				template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Activate#=data.id#'>#= data.status =='Active' ? 'Deactivate' : 'Activate' #</a>"
				width="160px" />	 
	</kendo:grid-columns>
	
	<kendo:dataSource pageSize="20" requestEnd="onRequestEnd" requestStart="onRequestStart">
			<kendo:dataSource-transport>
				  <kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="GET" contentType="application/json" />
				  <kendo:dataSource-transport-create url="${createUrl}" dataType="json" type="GET" contentType="application/json" />
				  <kendo:dataSource-transport-update url="${updateUrl}" dataType="json" type="GET" contentType="application/json" />
				  <kendo:dataSource-transport-destroy url="${destroyUrl}" dataType="json" type="GET" contentType="application/json" />
			</kendo:dataSource-transport>
			
			<kendo:dataSource-schema parse="parseInsName">
				<kendo:dataSource-schema-model id="id">
					<kendo:dataSource-schema-model-fields>
					    <kendo:dataSource-schema-model-field name="id" type="number"/>							
						<kendo:dataSource-schema-model-field name="insName" type="string"/>							
						<kendo:dataSource-schema-model-field name="insLocation" type="string"/>							
						<kendo:dataSource-schema-model-field name="meteredStatus" type="string"/>							
						<kendo:dataSource-schema-model-field name="meteredConstant" type="number" defaultValue="">
							<kendo:dataSource-schema-model-field-validation min="0" />
						</kendo:dataSource-schema-model-field>
					    <kendo:dataSource-schema-model-field name="status" type="string"/>							
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
	</kendo:grid>
	
	
	
		
	<!--------------------------------------------UnMetered Child------------------------------------------------------------------------->
	<kendo:grid-detailTemplate id="unMeteredPointsTemplate">
		  <kendo:tabStrip name="tabStrip_#=id#"> 
		<kendo:tabStrip-animation></kendo:tabStrip-animation>
	<kendo:tabStrip-items >

	<kendo:tabStrip-item selected="true" text="Unassessed Details">
	<kendo:tabStrip-item-content>	 
		<kendo:grid name="unmeteredPoints_#=id#" pageable="true" resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true" edit="unmeteredPointsEvent" editable="true"  >
		<kendo:grid-pageable pageSize="10"></kendo:grid-pageable>
					<kendo:grid-filterable extra="false">
						<kendo:grid-filterable-operators>
							<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains"/>
						</kendo:grid-filterable-operators>
					</kendo:grid-filterable>

			<kendo:grid-editable mode="popup" confirmation="Are you sure you want to remove this item?"  />
			<kendo:grid-toolbar>
				<kendo:grid-toolbarItem name="create" text="Add Metered Details"/>
			</kendo:grid-toolbar>
		<kendo:grid-columns>	                        
	        <kendo:grid-column title="InstallId" field="id" hidden="true"/>		
		    <kendo:grid-column title="UnMeterChildId" field="otId" hidden="true"/>
			<kendo:grid-column title="Month"  field="oTMonth" width="90px" format="{0:dd/MM/yyyy}" filterable="false"/>
			<kendo:grid-column title="PreviousReading *" field="previousReading"  width="120px" filterable="false"/>
			<kendo:grid-column title="PresentReading *" field="presentReading" width="120px" filterable="false"/>
			<kendo:grid-column title="MeteredConstant *" field="meterConstant" width="90px" filterable="false"/>
			<kendo:grid-column title="Capacity(Kw)*" field="capacity" width="80px" filterable="false" hidden="true" />
			<kendo:grid-column title="Hour*" field="hour" width="80px" filterable="false" hidden="true"/>
			<kendo:grid-column title="Phase*" field="phase" width="80px" editor="phaseEditor" filterable="false" hidden="true"/>
			<kendo:grid-column title="Units *" field="units" width="80px" filterable="false"/>		
			<kendo:grid-column title="&nbsp;" width="100px">
					<kendo:grid-column-command>
						<kendo:grid-column-commandItem name="edit" />
						
 					</kendo:grid-column-command>
		</kendo:grid-column>
		</kendo:grid-columns>
		
		<kendo:dataSource requestEnd="unmeterdPointOnRequestEnd" requestStart="unmeterdPointRequestStart">
		  <kendo:dataSource-transport>
		     <kendo:dataSource-transport-read url="${childReadUrl}/#=id#" dataType="json" type="GET" contentType="application/json"/>
		    <kendo:dataSource-transport-create url="${childCreateUrl}/#=id#" dataType="json" type="GET" contentType="application/json"/>
		    <kendo:dataSource-transport-update url="${childUpdateUrl}/#=id#" dataType="json" type="GET" contentType="application/json"/> 
		    
		  </kendo:dataSource-transport>
	    <kendo:dataSource-schema parse="parseUnmeteredPoints" >
		  <kendo:dataSource-schema-model id="otId">
			<kendo:dataSource-schema-model-fields>
			    <kendo:dataSource-schema-model-field name="otId" type="number"/>							
			    <kendo:dataSource-schema-model-field name="id" type="number"/>							
	    		<kendo:dataSource-schema-model-field name="oTMonth" type="date"/>
				<kendo:dataSource-schema-model-field name="previousReading" type="number"  defaultValue="0">
				</kendo:dataSource-schema-model-field>
				<kendo:dataSource-schema-model-field name="presentReading" type="number" defaultValue="0">
				</kendo:dataSource-schema-model-field>	
				<kendo:dataSource-schema-model-field name="meterConstant" type="number" defaultValue="0">
				</kendo:dataSource-schema-model-field>
			    <kendo:dataSource-schema-model-field name="units" type="number" defaultValue="0">
			    </kendo:dataSource-schema-model-field>
			    <kendo:dataSource-schema-model-field name="capacity" type="number" defaultValue="">
			    </kendo:dataSource-schema-model-field>
			    <kendo:dataSource-schema-model-field name="hour" type="number" defaultValue="">
			    </kendo:dataSource-schema-model-field>
			    <kendo:dataSource-schema-model-field name="phase" type="number" defaultValue="">
			    </kendo:dataSource-schema-model-field>
			    </kendo:dataSource-schema-model-fields>
			    </kendo:dataSource-schema-model>
			</kendo:dataSource-schema>     
		</kendo:dataSource>
		</kendo:grid>
		</kendo:tabStrip-item-content>
	</kendo:tabStrip-item>	
	</kendo:tabStrip-items>
	</kendo:tabStrip>
	</kendo:grid-detailTemplate>
	
	<div id="alertsBox" title="Alert"></div>
		<!--------------------------------------------UnMetered Child ------------------------------------------------------------------------->
		
	
	<script>
	var SelectedRowId = "";
	var previous="";
	var units="";
	function unmeteredPointsDeleteEvent(){
		securityCheckForActions("./BillGeneration/UnAssedPoints/destroyButton");
		var conf = confirm("Are u sure want to delete this unmetered point?");
		 if(!conf){
		   $("#grid").data().kendoGrid.dataSource.read();
		   throw new Error('deletion aborted');
		 }
	}
	
	var insNameArray = [];

	 function parseInsName(response) {   
	     var data = response; 
	     insNameArray = [];
		 for (var idx = 0, len = data.length; idx < len; idx ++) {
			var res1 = (data[idx].insName);
			insNameArray.push(res1);
		 }  
		 return response;
	} 
	
	var SelectedRowId = "";
	var meteredStatus = "";
	var meteredFlag = false;
	function onChangeUser(arg) {
		 var gview = $("#grid").data("kendoGrid");
		 var selectedItem = gview.dataItem(gview.select());
		 SelectedRowId = selectedItem.id;
		 meteredStatus = selectedItem.meteredStatus;
		 if(meteredStatus=="Yes"){
			 meteredFlag = true;
		 }else{
			 meteredFlag = false;
		 }
		 this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
	}
	
	var previousReadingArray = [];
	var presentReadingArray = [];
	var idArray=[];

	 function parseUnmeteredPoints(response) {

		 var data = response; 
		 previousReadingArray = [];
		 presentReadingArray = [];
		 for (var idx = 0, len = data.length; idx < len; idx ++) {
			var res1 = (data[idx].previousReading);
			var res2 = (data[idx].presentReading);
			var res3 = (data[idx].id);
			previousReadingArray.push(res1);
			presentReadingArray.push(res2);
			idArray.push(res3);
		 }  
		   var gview = $("#grid").data("kendoGrid");
		   var selectedItem = gview.dataItem(gview.select());
		   selectedType = selectedItem.meteredStatus;
		   
		   
		   
    	if(selectedType=="Yes")
    	{
    			$.ajax
    					({
    						
    						type : "GET",
    						url : "./unmetered/getunits",
    						  autoBind: false,
    						data : {
    							presentReading  : res2,
    							previousReading : res1,
    							             id : res3,
    							typeOfservice:selectedType,
    						},
    						   success : function(response) {
    							previous=response[1];
    							
    							
    							if (response[0] == 'Present') {
    								alert("Present Reading Should be Greater than Previous Reading");
    								$("#presentReading").val("");
    							} else {
    								
    							   
    								response[0];
    							}

    						}
    					});
    		} 
    		
    	
			return response;
	} 
	 
	 function dropDownChecksEditor(container, options) {
			var res = (container.selector).split("=");
			var attribute = res[1].substring(0,res[1].length-1);
			
			$('<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '" name = "'+attribute+'" id = "'+attribute+'Id"/>')
					.appendTo(container).kendoDropDownList({
						optionLabel : {
							text : "Select",
							value : "",
						},
						defaultValue : false,
						sortable : true,
						select : selectType,
						dataSource : {
							transport : {
								read : "${allChecksUrl}/"+attribute,
							}
						}
					});
			 $('<span class="k-invalid-msg"  data-for="'+attribute+'"></span>').appendTo(container);
		}

	/* function meteredEditor(container, options)
	{
		  
		var booleanData = [ {
		     text : 'Select',
			 value : ""
			    },{
				   text : 'Yes',
				   value : "Yes"
				  },{
				   text : 'No',
				   value : "No"
					},];
				  $('<input name="Metered Status"/>').attr('data-bind', 'value:meteredStatus').appendTo(container).kendoDropDownList
				  ({
					  
					  defaultValue : false,
						select : selectType,
						sortable : true,
					  
					  
					 dataSource : booleanData,
					 dataTextField : 'text',
					 dataValueField : 'value',
				  });
				  $('<span class="k-invalid-msg" data-for="Metered Status"></span>').appendTo(container);
		   
	} */
	
	function selectType(e){
		var dataItem = this.dataItem(e.item.index());
		if(dataItem.text=="Yes"){
			 $('div[data-container-for="meteredConstant"]').show();
			 $('label[for="meteredConstant"]').closest('.k-edit-label').show();
			 
			
			 
		}else{
			
			 $('div[data-container-for="meteredConstant"]').hide();
			 $('label[for="meteredConstant"]').closest('.k-edit-label').hide();
			
			
			 
		}
	}
	
	var flagInsName = "";
	function unMeteredEdit(e){
		$(".k-edit-field").each(function () {
			$(this).find("#temPID").parent().remove();  
	   	});
		
		
		$('label[for=id]').parent().remove();
		$('div[data-container-for="id"]').hide();
		
		$('label[for=meteredConstant]').parent().hide();
		$('div[data-container-for="meteredConstant"]').hide();
		
		$('label[for=status]').parent().remove();
		$('div[data-container-for="status"]').hide();
		
		if (e.model.isNew()){
			flagInsName = true;
			securityCheckForActions("./BillGeneration/UnAssedPoints/createButton");
			$(".k-window-title").text("Add UnAssed Point Details");
		    $(".k-grid-update").text("Save"); 
		
		}else{
			flagInsName = false;
			securityCheckForActions("./BillGeneration/UnAssedPoints/editButton");
			$(".k-window-title").text("Edit UnAssed Point  Details");
		    $(".k-grid-update").text("Update"); 
		}
	}
	
	
	function clearFilterItemMaster()
	{
	   $("form.k-filter-menu button[type='reset']").slice().trigger("click");
	   var gridStoreIssue = $("#grid").data("kendoGrid");
	   gridStoreIssue.dataSource.read();
	   gridStoreIssue.refresh();
	}
	
	function onRequestStart(e){
		if (e.type == "create"){
			var gridStoreGoodsReturn = $("#grid").data("kendoGrid");
			gridStoreGoodsReturn.cancelRow();		
		}	
	}
	
	function onRequestEnd(r) {
		if (typeof r.response != 'undefined') {
	  		if (r.response.status == "FAIL") {

	  			errorInfo = "";

	  			for (var s = 0; s < r.response.result.length; s++) {
	  				errorInfo += (s + 1) + ". "
	  						+ r.response.result[s].defaultMessage + "<br>";

	  			}

	  			if (r.type == "create") {

	  				$("#alertsBox").html("");
	  				$("#alertsBox").html(
	  						"Error: Creating the Unmetered  Details<br>" + errorInfo);
	  				$("#alertsBox").dialog({
	  					modal : true,
	  					buttons : {
	  						"Close" : function() {
	  							$(this).dialog("close");
	  						}
	  					}
	  				});

	  			}

	  			if (r.type == "update") {
	  				$("#alertsBox").html("");
	  				$("#alertsBox").html(
	  						"Error: Updating the Unmetered  Details<br>" + errorInfo);
	  				$("#alertsBox").dialog({
	  					modal : true,
	  					buttons : {
	  						"Close" : function() {
	  							$(this).dialog("close");
	  						}
	  					}
	  				});
	  			}

	  			$('#grid').data('kendoGrid').refresh();
	  			$('#grid').data('kendoGrid').dataSource.read();
	  			return false;
	  		}
	  		
	  		if (r.response.status == "CHILD") {

	  			
	  				$("#alertsBox").html("");
	  				$("#alertsBox")
	  						.html("Can't delete Unmetered  details, child record found");
	  				$("#alertsBox").dialog({
	  					modal : true,
	  					buttons : {
	  						"Close" : function() {
	  							$(this).dialog("close");
	  						}
	  					}
	  				});
	  				$('#grid').data('kendoGrid').refresh();
	  				$('#grid').data('kendoGrid').dataSource.read();
	  			return false;
	  		}
	  		
	  		if (r.response.status == "AciveParameterDestroyError") {
	  			$("#alertsBox").html("");
	  			$("#alertsBox").html("Active UnAssed point  details cannot be deleted");
	  			$("#alertsBox").dialog({
	  				modal : true,
	  				buttons : {
	  					"Close" : function() {
	  						$(this).dialog("close");
	  					}
	  				}
	  			});
	  			$('#grid').data('kendoGrid').refresh();
	  			$('#grid').data('kendoGrid').dataSource.read();
	  		return false;
	  	}

	  		else if (r.response.status == "INVALID") {

	  			errorInfo = "";

	  			errorInfo = r.response.result.invalid;

	  			if (r.type == "create") {
	  				$("#alertsBox").html("");
	  				$("#alertsBox").html(
	  						"Error: Creating the Unmetered  Details<br>" + errorInfo);
	  				$("#alertsBox").dialog({
	  					modal : true,
	  					buttons : {
	  						"Close" : function() {
	  							$(this).dialog("close");
	  						}
	  					}
	  				});
	  			}
	  			$('#grid').data('kendoGrid').refresh();
	  			$('#grid').data('kendoGrid').dataSource.read();
	  			return false;
	  		}

	  		else if (r.response.status == "EXCEPTION") {

	  			errorInfo = "";

	  			errorInfo = r.response.result.exception;

	  			if (r.type == "create") {
	  				
	  				$("#alertsBox").html("");
	  				$("#alertsBox").html(
	  						"Error: Creating the Unmetered  Details<br>" + errorInfo);
	  				$("#alertsBox").dialog({
	  					modal : true,
	  					buttons : {
	  						"Close" : function() {
	  							$(this).dialog("close");
	  						}
	  					}
	  				});
	  			}

	  			if (r.type == "update") {
	  				$("#alertsBox").html("");
	  				$("#alertsBox").html(
	  						"Error: Updating the Unmetered  Details<br>" + errorInfo);
	  				$("#alertsBox").dialog({
	  					modal : true,
	  					buttons : {
	  						"Close" : function() {
	  							$(this).dialog("close");
	  						}
	  					}
	  				});
	  			}

	  			$('#grid').data('kendoGrid').refresh();
	  			$('#grid').data('kendoGrid').dataSource.read();
	  			return false;
	  		}

	  		else if (r.type == "create")
	  		{
	  			$("#alertsBox").html("");
	  			$("#alertsBox").html("UnAssed Point  Details Created successfully");
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

	  		else if (r.type == "update") {
	  			
	  			$("#alertsBox").html("");
	  			$("#alertsBox")
	  					.html("UnAssed Point Details Updated successfully");
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
	  		else if (r.type == "destroy") {
	  			$("#alertsBox").html("");
	  			$("#alertsBox").html("UnAssed Point Details Deleted successfully");
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
	
	
	function unmeterdPointOnRequestEnd(e) {
	
		
		if (typeof e.response != 'undefined')
		{

			if (e.response.status == "FAIL") 
			{
				errorInfo = "";

				for (var k = 0; k < e.response.result.length; k++) 
				{
					errorInfo += (k + 1) + ". "
							+ e.response.result[k].defaultMessage + "<br>";

				}

				if (e.type == "create") {
					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Creating Child Details<br>" + errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});

				}

				else if (e.type == "update") {
					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Updating child Details<br>" + errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
				}

				 var grid = $("#unmeteredPoints_"+SelectedRowId).data("kendoGrid");
				grid.dataSource.read();
				grid.refresh(); 
				return false;
			}
		

	  else if (e.type == "create") 
		{
			$("#alertsBox").html("");
			$("#alertsBox").html("Record Created Successfully");
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
			var gview = $("#unmeteredPoints_"+SelectedRowId).data("kendoGrid");
			gview.dataSource.read();
			gview.refresh();
			return false;
		}

		else if (e.type == "update") 
		{
			$("#alertsBox").html("");
			$("#alertsBox").html("Record Updated Successfully");
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
			var gview = $("#unmeteredPoints_"+SelectedRowId).data("kendoGrid");
			gview.dataSource.read();
			gview.refresh();
			return false;
		}
		else if (e.type == "destroy") 
		{
			$("#alertsBox").html("");
			$("#alertsBox").html("Record deleted successfully");
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
		}	
			
		}
		
	} 
	
	function unmeterdPointRequestStart(e)
	{

	}
	
	var previousReading=0;
	var unit=0;
	function unmeteredPointsEvent(e)
	{
		if(meteredStatus=="Yes"){
			$.ajax
			({			
				type : "POST",
				url : "./unAssetPoints/getPreviousReading/"+SelectedRowId,
				async: false,
				dataType : "JSON",
				success : function(response) 
				{	  
					previousReading = response;
					$('input[name="previousReading"]').data("kendoNumericTextBox").value(response);
					if(response==0){
						$('input[name="previousReading"]').attr('readonly',false); 	
					}else{
						$('input[name="previousReading"]').attr('readonly',true); 	
					}
					  
				} 
			});
		}
		 $('div[data-container-for="otId"]').remove();
		 $('label[for="otId"]').closest('.k-edit-label').remove();  
		 
		 $('div[data-container-for="id"]').remove();
		 $('label[for="id"]').closest('.k-edit-label').remove(); 
		 
		   var gview = $("#grid").data("kendoGrid");
		   var selectedItem = gview.dataItem(gview.select());
		   selectedType = selectedItem.meteredStatus;
		  
		 if (e.model.isNew()){
			 securityCheckForActions("./BillGeneration/UnAssedDetails/createButton");
			 if(selectedType=="Yes"){
				    $('div[data-container-for="oTMonth"]').show();
				    $('label[for="oTMonth"]').closest('.k-edit-label').show();  
				    
				    $('div[data-container-for="previousReading"]').show();
				    $('label[for="previousReading"]').closest('.k-edit-label').show();
				    
				    $('div[data-container-for="presentReading"]').show();
				    $('label[for="presentReading"]').closest('.k-edit-label').show();
				    
				    $('div[data-container-for="meterConstant"]').show();
				    $('label[for="meterConstant"]').closest('.k-edit-label').show();
				    
				    $('div[data-container-for="units"]').show();
				    $('label[for="units"]').closest('.k-edit-label').show();
				    
				    $('div[data-container-for="capacity"]').hide();
				    $('label[for="capacity"]').closest('.k-edit-label').hide();
				    
				    $('div[data-container-for="hour"]').hide();
				    $('label[for="hour"]').closest('.k-edit-label').hide();
				    
				    $('div[data-container-for="phase"]').hide();
				    $('label[for="phase"]').closest('.k-edit-label').hide();
				   }
				    
				   else
					   {
				    $('div[data-container-for="previousReading"]').hide();
				    $('label[for="previousReading"]').closest('.k-edit-label').hide();
				    
				    $('div[data-container-for="presentReading"]').hide();
				    $('label[for="presentReading"]').closest('.k-edit-label').hide();
				    
				    $('div[data-container-for="meterConstant"]').hide();
				    $('label[for="meterConstant"]').closest('.k-edit-label').hide();
				    
				    $('div[data-container-for="capacity"]').show();
				    $('label[for="capacity"]').closest('.k-edit-label').show();
				    
				    $('div[data-container-for="hour"]').show();
				    $('label[for="hour"]').closest('.k-edit-label').show();
				    
				    $('div[data-container-for="phase"]').show();
				    $('label[for="phase"]').closest('.k-edit-label').show();
				  
				    $('div[data-container-for="previousReading"]').remove();
				    
				    $(".k-grid-update").click(function() {
				    	unit = units;
						e.model.set("units",units);
					});
					   }
			 
				$(".k-window-title").text("Add Details");
			    $(".k-grid-update").text("Save");
			   
			  
			}else{
				securityCheckForActions("./BillGeneration/UnAssedDetails/editButton");
				
				 if(selectedType=="Yes"){
				    $('div[data-container-for="oTMonth"]').show();
				    $('label[for="oTMonth"]').closest('.k-edit-label').show();  
					   
				    $('div[data-container-for="previousReading"]').show();
				    $('label[for="previousReading"]').closest('.k-edit-label').show();
				    
				    $('div[data-container-for="presentReading"]').show();
				    $('label[for="presentReading"]').closest('.k-edit-label').show();
				    
				    $('div[data-container-for="meterConstant"]').show();
				    $('label[for="meterConstant"]').closest('.k-edit-label').show();
				    
				    $('div[data-container-for="units"]').show();
				    $('label[for="units"]').closest('.k-edit-label').show();
				    
				    $('div[data-container-for="capacity"]').hide();
				    $('label[for="capacity"]').closest('.k-edit-label').hide();
				    
				    $('div[data-container-for="hour"]').hide();
				    $('label[for="hour"]').closest('.k-edit-label').hide();
				    
				    $('div[data-container-for="phase"]').hide();
				    $('label[for="phase"]').closest('.k-edit-label').hide();
				   }
				    
				   else
					   {
				    $('div[data-container-for="previousReading"]').hide();
				    $('label[for="previousReading"]').closest('.k-edit-label').hide();
				    
				    $('div[data-container-for="presentReading"]').hide();
				    $('label[for="presentReading"]').closest('.k-edit-label').hide();
				    
				    $('div[data-container-for="meterConstant"]').hide();
				    $('label[for="meterConstant"]').closest('.k-edit-label').hide();
				    
						   }
				
				$(".k-window-title").text("Edit Details");
			    $(".k-grid-update").text("Update"); 
			}
		 
		 $('input[name="previousReading"]').change(function() {
						var preRead = $('input[name="presentReading"]').val();
						var prevRead = $('input[name="previousReading"]').val();
						unit = preRead - prevRead;
						$('input[name="units"]').val(unit);
						$('input[name="units"]').prop('readonly',true);
					});
			
			$('input[name="presentReading"]').change(function() {
						var preRead = $('input[name="presentReading"]').val();
						var prevRead = $('input[name="previousReading"]').val();
						 unit = preRead - prevRead;
						$('input[name="units"]').val(unit);
						$('input[name="units"]').prop('readonly',true);
					});
		 
		 $(".k-grid-update").click(function() {
				
				 if(selectedType=="Yes"){
				 	var presentRead = $('input[name="presentReading"]').val();
				 	var previousRead = $('input[name="previousReading"]').val();
			 	   if(presentRead < previousRead){
							alert("Present Reading Should be Greater than Previous Reading");
							 return false;

				    } 
				 }
				 
				e.model.set("previousReading", previousReading);
				e.model.set("units", unit);


			  });
		
		}
		
	
	/* function getReadings()
	{
		
		 var gview = $("#grid").data("kendoGrid");
		 var selectedItem = gview.dataItem(gview.select());
		 selectedType = selectedItem.meteredStatus;
  	if(selectedType=="Yes")
  	{
		
		 var data = this.dataSource.view(), row;
		  var grid = $("#unmeteredPoints_"+SelectedRowId).data("kendoGrid");
		  var i=0;
		  for (i = 0; i < data.length; i++) {

		   row = this.tbody.children("tr[data-uid='" + data[i].uid + "']");
		   
				    data[i].previousReading=previous;
			
				   
		    }
	}
	
  	
	} */
	
	  function spinnerEditor(container, options){
		  $('<input id="previousReading" data-bind="value:' + options.field + '"/>')
	        .appendTo(container)
	        .kendoNumericTextBox({
	         spinners : false
	        });
		  if (previous != null || previous!=0 ||previous!="") {
	        	 $('input[name="previousReading"]').attr('readonly',true);
		
	        	 
	            }
		
		 }
	
	$("#grid").on("click", "#temPID", function(e){
		 var button = $(this), enable = button.text() == "Activate";
		 var widget = $("#grid").data("kendoGrid");
		 var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
		 var result=securityCheckForActionsForStatus("./BillGeneration/UnAssedPoints/activeInactiveButton"); 
		 if(result=="success"){ 
			 if (enable)
			 {
				$.ajax
				({
					type : "POST",
					url : "./unmeterd/unmeteredStatus/" + dataItem.id + "/activate",
					dataType : 'text',
					success : function(response) 
					{
						$("#alertsBox").html("");
						$("#alertsBox").html(response);
						$("#alertsBox").dialog
						({
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
			  }
			  else 
			  {
				  $.ajax
				  ({
					   type : "POST",
					   url : "./unmeterd/unmeteredStatus/" + dataItem.id + "/deactivate",
					   dataType : 'text',
					   success : function(response) 
					   {
							$("#alertsBox").html("");
							$("#alertsBox").html(response);
							$("#alertsBox").dialog({
								modal : true,
								buttons : 
								{
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

	 	  //Validator Function
	 (function($, kendo) 
	  {
		 $.extend(true,kendo.ui.validator,
		 {
			rules : { 
		  		        insNamevalidation : function(input, params) 
		  		      { 
						 if (input.filter("[name='insName']").length && input.val()) 
						 {
							 return /^[a-zA-Z]+[ ._a-zA-Z._]*[a-zA-Z]$/.test(input.val());
						 }
						 return true;
					  },  
					  
					  insNameNullValidator : function(input,params) 
						{
							
							 if (input.attr("name") == "insName") {
			                   return $.trim(input.val()) !== "";
			                }
			                return true;
						},
		             
					  
   		             insLocationNullValidator : function(input,params) 
	    				  { 
	    					  if (input.attr("name") == "insLocation") 
	    					  {
	  						     return $.trim(input.val()) !== "";
	  						  }
	  						  return true;
	    		           }, 
	    		      meteredNullValidator : function(input,params) 
		    				  { 
	    		    	  			if(meteredFlag){
	    		    	  				if (input.attr("name") == "meteredConstant") 
	  		    					  		{
	  		  						     return $.trim(input.val()) !== "";
	  		  						 		 }
		    				  			}
		  						  return true;
		    		           }, 
		    		  insNameUniquevalidation : function(input, params){
									if(flagInsName){
										if (input.filter("[name='insName']").length && input.val()) 
										{
											var flag = true;
											$.each(insNameArray, function(idx1, elem1) {
												if(elem1.toLowerCase() == input.val().toLowerCase())
												{
													flag = false;
												}	
											});
											return flag;
										}
									}
									return true;
								},
					meteredStatusValidator : function(input,params) 
			    				  { 
			    					  if (input.attr("name") == "meteredStatus") 
			    					  {
			    						if(input.val()=="Yes"){
			    							  meteredFlag = true;
			                          	}else{
			                          		meteredFlag = false;
			                          	}
			  						     return $.trim(input.val()) !== "";
			  						  }
			  						  return true;
			    		           },    
			    	 oTMonthRequiredValidation : function(input,params) 
				    				  { 
			    		    	  		if (input.attr("name") == "oTMonth"){
			  		  						     return $.trim(input.val()) !== "";
				    				  	     }
				  						  return true;
				    		           },
				     unitsRequiredValidation : function(input,params) 
					    				  { 
				    		    	  		if (input.attr("name") == "units"){
				  		  						     return $.trim(input.val()) !== "";
					    				  	     }
					  						  return true;
					    		           },
					  presentReadingRequiredValidation : function(input,params) 
						    				  { 
					    		    	  			if(meteredFlag){
					    		    	  				if (input.attr("name") == "presentReading") 
					  		    					  		{
					  		  						     return $.trim(input.val()) !== "";
					  		  						 		 }
						    				  			}
						  						  return true;
						    		           }, 
					  meterConstantRequiredValidation : function(input,params) 
							    				  { 
						    		    	  			if(meteredFlag){
						    		    	  				if (input.attr("name") == "meterConstant") 
						  		    					  		{
						  		  						     return $.trim(input.val()) !== "";
						  		  						 		 }
							    				  			}
							  						  return true;
							    		           },   
			  },
					  
					  messages : 
					  {
						  insNamevalidation : " Name field cannot allow special symbols except(_ .)",
						  insNameNullValidator:"Name is required",
						  insLocationNullValidator:"Location is required",
						  meteredNullValidator:"Meter constant is required",
						  insNameUniquevalidation : "Assed point name already exits",
						  meteredStatusValidator : "Metered is required",
						  oTMonthRequiredValidation : "Month is required",
						  unitsRequiredValidation : "Units is required",
						  presentReadingRequiredValidation : "Present reading is required",
						  meterConstantRequiredValidation : "Meter constant is required"

					  }
				 });
		})(jQuery, kendo); 
	
	
	 function phaseEditor(container, options){
		 var data = [ {
				text : "Single Phase",
				value : "1"
			}, {
				text : "Three Phase",
				value : "3"
			} ];

			$(
					'<input name="phase" id="phase" data-text-field="text" id="dept"  data-value-field="value" data-bind="value:' + options.field + '" />')
					.appendTo(container).kendoDropDownList({

						dataTextField : "text",
						dataValueField : "value",
						optionLabel : {
							text : "Select",
							value : "",
						},
						defaultValue : false,
						sortable : true,
						dataSource : data,
						change:function() {	           
							onChangePhase();
				         }
					});
			$('<span class="k-invalid-msg" data-for="phase"></span>').appendTo(
					container);
		 }
	
function onChangePhase(){
	var capacity= $("input[name=capacity]").val();
	var phase = $("input[name=phase]").data("kendoDropDownList").text();
	var  hour= $("input[name=hour]").val();
	
	
	$.ajax({
		type : "GET",
		url : "./UnAssedPoint/getUnMeteredReading",
		
		dataType : "json",	
		data : {

			capacity : capacity,
			phase : phase,
			hour:hour,
		},
		success : function(response) {
			$("input[name=units]").data("kendoNumericTextBox").value(response);
			unit = response;
			units=response;
		}
	}); 
}

	</script>
	