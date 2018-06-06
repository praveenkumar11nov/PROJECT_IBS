<%@include file="/common/taglibs.jsp"%>

<c:url value="/amrData/amrDataCreate" var="createUrl" />
<c:url value="/amrData/amrDataRead" var="readUrl" />
<c:url value="/amrData/amrDataDestroy" var="destroyUrl" />
<c:url value="/amrData/amrDataUpdate" var="updateUrl" />
<c:url value="/bills/readTowerNames" var="towerNames" />

<c:url value="/amrSetting/blockNameFilterUrl" var="blockNameFilterUrl" />
<c:url value="/amrSetting/propertyNameFilterUrl" var="propertyNameFilterUrl" />
<c:url value="/amrSetting/accountNumberFilterUrl" var="accountNumberFilterUrl" />
<c:url value="/amrSetting/statusFilterUrl" var="statusFilterUrl" />
<c:url value="/amrSetting/personNameFilterUrl" var="personNameFilterUrl" />

<kendo:grid name="grid" remove="amrDataDeleteEvent" pageable="true" resizable="true" edit="amrDataEvent" sortable="true" reorderable="true" selectable="true" scrollable="true" filterable="false" groupable="true" height="421px">
    <kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" refresh="true" info="true" previousNext="true">
	<kendo:grid-pageable-messages itemsPerPage="Status items per page" empty="No status item to display" refresh="Refresh all the status items" 
			display="{0} - {1} of {2} New Status Items" first="Go to the first page of status items" last="Go to the last page of status items" next="Go to the next page of status items"
			previous="Go to the previous page of status items"/>
	</kendo:grid-pageable>
	<kendo:grid-filterable extra="false">
		<kendo:grid-filterable-operators>
			<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains"/>
			<kendo:grid-filterable-operators-date gt="Is after" lt="Is before"/>
		</kendo:grid-filterable-operators> 
	</kendo:grid-filterable>
	<kendo:grid-editable mode="popup"/>
		<kendo:grid-toolbar>
		 <kendo:grid-toolbarItem name="dataTemplatesDetailsExport" text="Export To Excel" />
		 <kendo:grid-toolbarItem name="dataPdfTemplatesDetailsExport" text="Export To PDF" />
		     <%--  <kendo:grid-toolbarItem name="create" text="Create AMR For Property" />
		      <kendo:grid-toolbarItem name="activateAll" text="Activate All" /> --%>
		    <%--   <kendo:grid-toolbarItem
			template="<input id='monthpicker' style='width:162px' />" />
		<kendo:grid-toolbarItem
			template="<a class='k-button' href='\\#' onclick=searchByMonth() title='Search'>Search</a>" /> --%>
			<kendo:grid-toolbarItem name="amrBilling" text="Get AMR Data" />
		     <kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterAMRSettings()><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>"/>
	    
	    </kendo:grid-toolbar>
	<kendo:grid-columns>
	    
	    <kendo:grid-column title="AMR Id" field="id" width="70px" hidden="true" filterable="false" sortable="false" />
	    
	    <kendo:grid-column title="Tower&nbsp;*" field="blockId" width="70px" filterable="false" hidden="true"/>
	    
	     <kendo:grid-column title="Tower&nbsp;*" field="blocksName" width="70px" filterable="true" editor="towerNameEditor">
	     <kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function storeNameFilter(element) 
						   	{
								element.kendoAutoComplete({
									dataSource : {
										transport : {		
											read :  "${blockNameFilterUrl}"
										}
									} 
								});
						   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	     </kendo:grid-column>
	    
	    <kendo:grid-column title="Property&nbsp;*" field="propertyId" width="70px" filterable="false" hidden="true"/>
	    
	    <kendo:grid-column title="Person&nbsp;Name" field="personName" width="70px" filterable="true">
		   <kendo:grid-column-filterable>
						<kendo:grid-column-filterable-ui>
							<script type="text/javascript">
								function storeNameFilter(element) 
							   	{
									element.kendoAutoComplete({
										dataSource : {
											transport : {		
												read :  "${personNameFilterUrl}"
											}
										} 
									});
							   	}
							</script>
						</kendo:grid-column-filterable-ui>
					</kendo:grid-column-filterable>
		    </kendo:grid-column>
	    
	    <kendo:grid-column title="Property&nbsp;*" field="propertyName" width="70px" filterable="false" editor="propertyEditor">
	    <kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function storeNameFilter(element) 
						   	{
								element.kendoAutoComplete({
									dataSource : {
										transport : {		
											read :  "${propertyNameFilterUrl}"
										}
									} 
								});
						   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	     </kendo:grid-column>
	     
	    <kendo:grid-column title="Account&nbsp;Number&nbsp;*" field="accountNumber" width="70px" filterable="false">
		   <kendo:grid-column-filterable>
						<kendo:grid-column-filterable-ui>
							<script type="text/javascript">
								function storeNameFilter(element) 
							   	{
									element.kendoAutoComplete({
										dataSource : {
											transport : {		
												read :  "${accountNumberFilterUrl}"
											}
										} 
									});
							   	}
							</script>
						</kendo:grid-column-filterable-ui>
					</kendo:grid-column-filterable>
		    </kendo:grid-column>
		    
		    <kendo:grid-column title="Meter&nbsp;Number&nbsp;*" field="meterNumber" width="70px" filterable="false">
		   <kendo:grid-column-filterable>
						<kendo:grid-column-filterable-ui>
							<script type="text/javascript">
								function storeNameFilter(element) 
							   	{
									element.kendoAutoComplete({
										dataSource : {
											transport : {		
												read :  "${meterNumberFilterUrl}"
											}
										} 
									});
							   	}
							</script>
						</kendo:grid-column-filterable-ui>
					</kendo:grid-column-filterable>
		    </kendo:grid-column>
		    
 		<kendo:grid-column title="Date" field="readingDate" width="70px" format="{0:dd/MM/yyyy hh:mm tt}" filterable="true" sortable="false" >
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function validFromFilter(element) {
							element.kendoDateTimePicker({
							});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
	     </kendo:grid-column>
	    
	    <kendo:grid-column title="Present&nbsp;Reading" field="presentReading" width="70px" filterable="true">
	    <kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function storeNameFilter(element) 
						   	{
								element.kendoAutoComplete({
									dataSource : {
										transport : {		
											read :  "${presentReadingFilterUrl}"
										}
									} 
								});
						   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	    </kendo:grid-column>
	    	    <kendo:grid-column title="Present&nbsp;DG&nbsp;Reading" field="presentDGReading" width="70px" filterable="true">
	    <kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function storeNameFilter(element) 
						   	{
								element.kendoAutoComplete({
									dataSource : {
										transport : {		
											read :  "${presentDGReadingFilterUrl}"
										}
									} 
								});
						   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	    </kendo:grid-column>
		
	</kendo:grid-columns>

<kendo:dataSource pageSize="20" requestEnd="onRequestEnd" requestStart="onRequestStart">
		<kendo:dataSource-transport>
			<kendo:dataSource-transport-create url="${createUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json" />
			<kendo:dataSource-transport-update url="${updateUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-destroy url="${destroyUrl}/" dataType="json" type="GET" contentType="application/json" />
		</kendo:dataSource-transport>

		<kendo:dataSource-schema parse="parse">
			<kendo:dataSource-schema-model id="id">
				<kendo:dataSource-schema-model-fields>
					<kendo:dataSource-schema-model-field name="id" type="number"/>
					
					<kendo:dataSource-schema-model-field name="blockId" type="number"/>
					
					<kendo:dataSource-schema-model-field name="readingDate" type="date"/>
					
					<kendo:dataSource-schema-model-field name="blocksName" type="string"/>
					
					<kendo:dataSource-schema-model-field name="accountNumber" type="string"/>
					
					<kendo:dataSource-schema-model-field name="propertyId" type="number"/>
					
					<kendo:dataSource-schema-model-field name="propertyName" type="string"/>
					
					<kendo:dataSource-schema-model-field name="personName" type="string"/>
					
					<kendo:dataSource-schema-model-field name="meterNumber" type="string"/>
					
  					<kendo:dataSource-schema-model-field name="presentReading" type="string"/>
                    <kendo:dataSource-schema-model-field name="presentDGReading" type="number"/>
				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>

	</kendo:dataSource>

</kendo:grid>

<div id="alertsBox" title="Alert"></div>

<div id="amrBillingDiv" style="display: none;">
	<form id="amrBillingForm" data-role="validator" novalidate="novalidate">
		<table style="height: 120px;">
			<!-- <tr>
				<td>Block Name</td>
				<td><input id="blockNameAmr" name="blockNameAmr"
					required="required" 
					validationMessage="Select Block Name" /> onchange="getPropertyNo()"</td>
			</tr> -->		

			<tr>
				<td>Present Bill Date</td>
				<td><kendo:datePicker format="dd/MM/yyyy"
						name="presentdateAmr" id="presentdateAmr" required="required"
						class="validate[required]" validationMessage="Date is Required">
					</kendo:datePicker>
				<td></td>
			</tr>

			<tr>
				<td class="left" align="center" colspan="4">                     
					<button type="submit" id="amrCalculate" class="k-button"
						style="padding-left: 10px">Submit</button>
					<span id=amrplaceholder style="display: none;"><img
						src="./resources/images/loadingimg.GIF"
						style="vertical-align: middle; margin-left: 50px" /> &nbsp;&nbsp;
						<img src="./resources/images/loading.GIF" alt="loading"
						style="vertical-align: middle margin-left: 50px" height="20px"
						width="200px; " /></span>
				</td>
				<!-- <td class="left" align="center" colspan="">                     
					<button type="submit" id="amrDump" class="k-button"
						style="padding-left: 10px">Save</button>
					<span id=amrDumpplaceholder style="display: none;"><img
						src="./resources/images/loadingimg.GIF"
						style="vertical-align: middle; margin-left: 50px" /> &nbsp;&nbsp;
						<img src="./resources/images/loading.GIF" alt="loading"
						style="vertical-align: middle margin-left: 50px" height="20px"
						width="200px; " /></span>
				</td> -->
			</tr>
			<tr>
				
			</tr>

		</table>
	</form>
</div>

<script>
var month="";
$("#amrBillingForm").submit(function(e) {
	e.preventDefault();
});

var amrcalculatevalidator = $("#amrBillingForm").kendoValidator().data("kendoValidator");
$("#amrCalculate").on("click", function() {

if (amrcalculatevalidator.validate()) {

	searchByMonth();
}
});

$("#amrDump").on("click", function() {

	if (amrcalculatevalidator.validate()) {

		dumpByMonth();
	}
	});


$(document).ready(function() {
	
	$("#blockNameAmr").kendoDropDownList({
		autoBind : false,
		optionLabel : "Select",
		dataTextField : "blockName",

		dataValueField : "blockId",
		dataSource : {
			transport : {
				read : "${towerNames}"
			}
		}
	});

	
	
	$("#monthpicker").kendoDatePicker({
	
		  value:new Date(),
		  // display month and year in the input
		  format: "dd/MM/yyyy"
		});
});
function searchByMonth(){
	$("#amrCalculate").hide();
	$("#amrplaceholder").show();
	 month=$("#presentdateAmr").val();
	//var blockId = $("#blockNameAmr").val();
	//var blockName = $("input[name=blockNameAmr]").data("kendoDropDownList").text();
	var presentdate = $("#presentdateAmr").val();
	
	$.ajax({
		type : "POST",
	//	url : "./amrDate/searcchByDate" ,
	    url: "./amrDate/searcchByDateNew",
		dataType : "json",
		data : {

			 month :  month,
			// blockId:blockId,
		},
		success : function(result) {
			$("#amrCalculate").show();
			$("#amrplaceholder").hide();
			//alert("Data has been Generated");
			closeAmr();
			 var grid = $("#grid").getKendoGrid();
			var data = new kendo.data.DataSource(); 
			grid.dataSource.data(result);
			grid.refresh(); 
		}
	});
}
function dumpByMonth(){
	$("#amrDump").hide();
	$("#amrDumpplaceholder").show();
	 month=$("#presentdateAmr").val();	
	var presentdate = $("#presentdateAmr").val();
	
	$.ajax({
		type : "POST",
	    url: "./amrDate/dumpByDateNew",
		dataType : "json",
		data : {

			 month :  month,
		
		},
		success : function(result) {
			$("#amrDump").show();
			$("#amrDumpplaceholder").hide();
			alert("Data has been Saved Successfully");
			closeAmr();
			 var grid = $("#grid").getKendoGrid();
			var data = new kendo.data.DataSource(); 
			grid.dataSource.data(result);
			grid.refresh(); 
		}
	});
}

function closeAmr() {
	var bbDialog = $("#amrBillingDiv");
	bbDialog.kendoWindow({
		width : "auto",
		height : "auto",
		modal : true,
		draggable : true,
		position : {
			top : 100
		},
		title : "Generate  Amr Data"
	}).data("kendoWindow").center().close();

	bbDialog.kendoWindow("close");
}
$("#grid").on("click",".k-grid-dataTemplatesDetailsExport", function(e) {
	
	if(month.length==0){
		var date = new Date();
		var day = date.getDate();
		var monthIndex = date.getMonth()+1;
		var year = date.getFullYear();
		month=day+"/"+monthIndex+"/"+year;
		
	}
	 // window.open("./amrDataTemplate/amrDataTemplatesDetailsExport?month="+month);
	 
	window.open("./amrDataTemplate/amrDataTemplatesDetailsExportNew?month="+month);
});	 

$("#grid").on("click",".k-grid-dataPdfTemplatesDetailsExport", function(e) {
	if(month.length==0){
		var date = new Date();
		var day = date.getDate();
		var monthIndex = date.getMonth()+1;
		var year = date.getFullYear();
		month=day+"/"+monthIndex+"/"+year;
		
	}
	  window.open("./amrDataPdfTemplate/amrDataPdfTemplatesDetailsExportNew?month="+month);
});	 


	function parse(response) {
		$.each(response, function(idx, elem) {
			if (elem.readingDate === null) {
				elem.readingDate = "";
			} else {
				elem.readingDate = kendo.parseDate(new Date(elem.readingDate),'dd/MM/yyyy HH:mm');
			}
		});
		return response;
	}
var statusNameArray = [];

function clearFilterAMRSettings()
{
   $("form.k-filter-menu button[type='reset']").slice().trigger("click");
   var gridStoreIssue = $("#grid").data("kendoGrid");
   gridStoreIssue.dataSource.read();
   gridStoreIssue.refresh();
}

function amrDataDeleteEvent(){
	var conf = confirm("Are u sure want to delete this status name?");
	 if(!conf){
	  $('#grid').data().kendoGrid.dataSource.read();
	   throw new Error('deletion aborted');
	 }
}


var setApCode="";		
function amrDataEvent(e)
{
	/***************************  to remove the id from pop up  **********************/

	$('div[data-container-for="id"]').remove();
	$('label[for="id"]').closest('.k-edit-label').remove();
	
	$('div[data-container-for="blockId"]').remove();
	$('label[for="blockId"]').closest('.k-edit-label').remove();
	
	$('div[data-container-for="propertyId"]').remove();
	$('label[for="propertyId"]').closest('.k-edit-label').remove();
	
	$('div[data-container-for="presentReading"]').remove();
	$('label[for="presentReading"]').closest('.k-edit-label').remove();
	
	$('div[data-container-for="personName"]').remove();
	$('label[for="personName"]').closest('.k-edit-label').remove();
	
	/************************* Button Alerts *************************/
	if (e.model.isNew()) 
    {
		setApCode = $('input[name="id"]').val();
		$(".k-window-title").text("Create Status Name");
		$(".k-grid-update").text("Save");		
    }
	else{
		$(".k-window-title").text("Edit Status Name");
	}
}
	
	function onRequestStart(e){
		$('.k-grid-update').hide();
        $('.k-edit-buttons').append( '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();
		
	}
	function onRequestEnd(e) {
		if (typeof e.response != 'undefined') {
			if (e.response.status == "FAIL") {
				errorInfo = "";
				for (var i = 0; i < e.response.result.length; i++) {
					errorInfo += (i + 1) + ". "
							+ e.response.result[i].defaultMessage + "<br>";
				}

				if (e.type == "create") {
					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Creating the meter status\n\n : "
									+ errorInfo);
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
			else if (e.type == "create") {
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Status name is created successfully");
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
			} else if (e.type == "destroy") {
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Status name is deleted successfully");
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
			} else if (e.type == "update") {
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Status name is updated successfully");
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

		function towerNameEditor(container, options) 
	   	{
			$('<input name="Tower Name" id="blockId" data-text-field="blocksName" data-value-field="blockId" data-bind="value:' + options.field + '" required="true"/>')
			.appendTo(container).kendoComboBox({
				autoBind : false,			
				dataSource : {
					transport : {		
						read :  "${getAllBlockUrl}"
					}
				},
				change : function (e) {
		            if (this.value() && this.selectedIndex == -1) {                    
						alert("Block doesn't exist!");
		                $("#blockId").data("kendoComboBox").value("");
		        	}
		    
			    }  
			});
			
			$('<span class="k-invalid-msg" data-for="Tower Name"></span>').appendTo(container);
	   	}
	
	function propertyEditor(container, options) 
   	{
		$('<input name="Propery Name" id="blockId" data-text-field="propertyName" data-value-field="propertyId" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoComboBox({
			cascadeFrom : "blockId", 
			autoBind : false,			
			dataSource : {
				transport : {		
					read :  "${getAllPropertyUrl}"
				}
			},
			change : function (e) {
	            if (this.value() && this.selectedIndex == -1) {                    
					alert("P doesn't exist!");
	                $("#blockId").data("kendoComboBox").value("");
	        	}
	    
		    }  
		});
		
		$('<span class="k-invalid-msg" data-for="Propery Name"></span>').appendTo(container);
   	}
	$("#grid").on("click", ".k-grid-amrBilling", function(e) {
		var bbDialog = $("#amrBillingDiv");
		bbDialog.kendoWindow({
			width : "auto",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Get AMR Data"
		}).data("kendoWindow").center().open();

		bbDialog.kendoWindow("open");

		/* var dropdownlist2 = $("#blockNameAmr").data("kendoDropDownList");
		dropdownlist2.value(""); */

		var presentreading = $("#presentdateAmr");
		presentreading.val("");
		
	});
</script>
