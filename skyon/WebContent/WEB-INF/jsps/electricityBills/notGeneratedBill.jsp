<%@include file="/common/taglibs.jsp"%>

<%-- <script src="http://html2canvas.hertzen.com/build/html2canvas.js"></script>
<script src="resources/js/plugins/charts/highcharts.js"></script>
<script src="resources/js/plugins/charts/exporting.js"></script>
<script type="text/javascript"
	src="http://canvg.googlecode.com/svn/trunk/rgbcolor.js"></script>
<script type="text/javascript"
	src="http://canvg.googlecode.com/svn/trunk/canvg.js"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jsPDF-master/jspdf.js' />"></script> --%>
<%-- <script type="text/javascript"
	src="<c:url value='/resources/js/jsPDF-master/libs/Deflate/adler32cs.js' />"></script> --%>
<%-- <script type="text/javascript"
	src="<c:url value='/resources/js/jsPDF-master/libs/FileSaver.js/FileSaver.js"' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jsPDF-master/libs/Blob.js/BlobBuilder.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jsPDF-master/jspdf.plugin.addimage.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jsPDF-master/jspdf.plugin.standard_fonts_metrics.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jsPDF-master/jspdf.plugin.split_text_to_size.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jsPDF-master/jspdf.plugin.from_html.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jsPDF/libs/sprintf.js' />"></script> --%>
<%-- <script type="text/javascript"
	src="<c:url value='/resources/js/jsPDF/tableExport.js' />"></script> --%>
<%-- <script type="text/javascript"
	src="<c:url value='/resources/js/jsPDF/jquery.base64.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jsPDF/html2canvas.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jsPDF/libs/sprintf.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jsPDF/libs/base64.js' />"></script> --%>
	
	<%-- <script type="text/javascript"
	src="<c:url value='/resources/js/jsPDF/libs/FileSaver.js/FileSaver.js' />"></script> --%>
<%-- 	<script type="text/javascript"
	src="<c:url value='/resources/js/jsPDF/libs/FileSaver.js/FileSaver.min.js' />"></script> --%>

<kendo:grid name="consolidatedGrid" resizable="true" selectable="multiple" change="onChange" filterable="true" pageable="true" sortable="true" scrollable="true" 	groupable="true">
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10"></kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
			<kendo:grid-filterable-operators>
				<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains"/>
			<kendo:grid-filterable-operators-date eq="Is equal to" gt="Is after" lt="Is before" gte="Is after or equals to" lte="Is before or equals to" neq="Is not equal to"/>
			</kendo:grid-filterable-operators>
		</kendo:grid-filterable>
		<kendo:grid-editable mode="popup" />
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="searchBills" text="Search All Bills" />
			<kendo:grid-toolbarItem name="electricityBillTemplatesDetailsExport" text="Export To Excel" />
			<kendo:grid-toolbarItem name="electricityBillPdfTemplatesDetailsExport" text="Export To PDF" />
			<kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilter() title='Clear Filter'><span class='k-icon k-i-funnel-clear'></span></a>"/>
		</kendo:grid-toolbar>
		<kendo:grid-columns>
		<kendo:grid-column title="Consolidate Bill Id" field="cbId" width="110px" hidden="true" filterable="true"/>
			<kendo:grid-column title="Account Id" field="accountId" width="110px" hidden="true" filterable="true"/>
			<kendo:grid-column title="Account No" field="accountNo" width="90px" filterable="true">
			</kendo:grid-column>
			<kendo:grid-column title="Property No" field="propertyNo" width="90px" filterable="true"/>
			<kendo:grid-column title="Person&nbsp;Name" field="personName"  width="100px" filterable="true">
	    	</kendo:grid-column>
	    	<kendo:grid-column title="Service Type" field="serviceType" width="70px" filterable="true"/>
			<kendo:grid-column title="Bill Month" field=" billMonth" width="90px"  filterable="false"/>
			<kendo:grid-column title="Bill Month" field=" billMonthSql" width="100px" hidden="true"/>
		</kendo:grid-columns>
		
		
		<kendo:dataSource pageSize="20">
			<%--<kendo:dataSource-transport>
				 <kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json" /> 
			</kendo:dataSource-transport>--%>
			<kendo:dataSource-schema >
				<kendo:dataSource-schema-model id="elBillId">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="cbId" type="number"/>
						<kendo:dataSource-schema-model-field name="accountId" type="number"/>
						<kendo:dataSource-schema-model-field name="accountNo"/>
						<kendo:dataSource-schema-model-field name="personName" type="string"/>
						<%-- <kendo:dataSource-schema-model-field name="billMonth" type="date"  /> --%>
						<kendo:dataSource-schema-model-field name="serviceType" type="string"/>
						<kendo:dataSource-schema-model-field name="billAmount" type="number"/>
						<kendo:dataSource-schema-model-field name="billMonthSql"/>
						
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
	</kendo:grid>
	
	<div id="billMasterTable"></div>
	<div id="billChildTable"></div>
	<div id="billAllChildTable"></div>
	<div id="billTable" style="color: black;width: 900px;"></div>
	
	
<div id="searchBilldiv" style="display: none;">
	<form id="searchBillForm" data-role="validator" novalidate="novalidate">
		<table style="height: 190px;">
			<tr>
				<td>Service Type</td>
				<td><input  id="serviceTypePost" name="serviceTypePost"
					
					validationMessage="Select Service Type"  />
				</td>
			</tr>
			 <tr id="fMonthPrint" style="display: none;">
				<td>From Month</td>
				<td><kendo:datePicker format="MMM yyyy  "
						name="fromdateprintBill" id="fromdateprintBill"  value="${month}" start="year"
						 depth="year">
					</kendo:datePicker>
				<td></td>
			</tr>

			<tr>
				<td> Month</td>
				<td><kendo:datePicker format="MMM yyyy  "
						name="presentdatepostBillLedger" id="presentdatepostBillLedger" required="required" value="${month}" start="year"
						class="validate[required]" validationMessage="Date is Required" depth="year">
					</kendo:datePicker>
				<td></td>
			</tr>
            
             
			<tr>
				<td class="left" align="center" colspan="4">                    
					<button type="submit" id="searchBills" class="k-button"
						style="padding-left: 10px">Search All Bills</button>
						<span id=commitplaceholderNewPost style="display: none;">
					<img
						src="./resources/images/loading2.gif"
						style="vertical-align: middle; margin-left: 50px" /> 
					</span>
				</td>
			</tr>

		</table>
	</form>
</div>

<div id="excelBilldiv" style="display: none;">
	<form id="excelBillForm" data-role="validator" novalidate="novalidate">
		<table style="height: 190px;">
			<tr>
				<td>Service Type</td>
				<td><input  id="serviceTypePostExcel" name="serviceTypePostExcel"
					
					validationMessage="Select Service Type"  />
				</td>
			</tr>
			 <tr id="fMonthPrint" style="display: none;">
				<td>From Month</td>
				<td><kendo:datePicker format="MMM yyyy  "
						name="fromdateprintBill" id="fromdateprintBill"  value="${month}" start="year"
						 depth="year">
					</kendo:datePicker>
				<td></td>
			</tr>

			<tr>
				<td> Month</td>
				<td><kendo:datePicker format="MMM yyyy  "
						name="presentdatepostBillExcel" id="presentdatepostBillExcel" required="required" value="${month}" start="year"
						class="validate[required]" validationMessage="Date is Required" depth="year">
					</kendo:datePicker>
				<td></td>
			</tr>
            
             
			<tr>
				<td class="left" align="center" colspan="4">                    
					<button type="submit" id="excelBills" class="k-button"
						style="padding-left: 10px">Submit</button>
						<span id=commitplaceholderNewPost style="display: none;">
					<img
						src="./resources/images/loading2.gif"
						style="vertical-align: middle; margin-left: 50px" /> 
					</span>
				</td>
			</tr>

		</table>
	</form>
</div>

<div id="pdfBilldiv" style="display: none;">
	<form id="pdfBillForm" data-role="validator" novalidate="novalidate">
		<table style="height: 190px;">
			<tr>
				<td>Service Type</td>
				<td><input  id="serviceTypePostPdf" name="serviceTypePostPdf"
					
					validationMessage="Select Service Type"  />
				</td>
			</tr>
			 <tr id="fMonthPrint" style="display: none;">
				<td>From Month</td>
				<td><kendo:datePicker format="MMM yyyy  "
						name="fromdateprintBill" id="fromdateprintBill"  value="${month}" start="year"
						 depth="year">
					</kendo:datePicker>
				<td></td>
			</tr>

			<tr>
				<td> Month</td>
				<td><kendo:datePicker format="MMM yyyy  "
						name="presentdatepostBillPdf" id="presentdatepostBillPdf" required="required" value="${month}" start="year"
						class="validate[required]" validationMessage="Date is Required" depth="year">
					</kendo:datePicker>
				<td></td>
			</tr>
            
             
			<tr>
				<td class="left" align="center" colspan="4">                    
					<button type="submit" id="pdfBills" class="k-button"
						style="padding-left: 10px">Submit</button>
						<span id=commitplaceholderNewPost style="display: none;">
					<img
						src="./resources/images/loading2.gif"
						style="vertical-align: middle; margin-left: 50px" /> 
					</span>
				</td>
			</tr>

		</table>
	</form>
</div>

<div id="alertsBox" title="Alert"></div>

<script>

$("#consolidatedGrid").on("click",".k-grid-electricityBillTemplatesDetailsExport", function(e) {
	  var bbDialog = $("#excelBilldiv");
		bbDialog.kendoWindow({
			width : "auto",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Select Month"
		}).data("kendoWindow").center().open();
		var dropdownlist1 = $("#serviceTypePostExcel").data("kendoDropDownList");
		dropdownlist1.value(""); 
		$("#presentdatepostBillExcel").val("");
		bbDialog.kendoWindow("open");
});	
$("#consolidatedGrid").on("click",".k-grid-electricityBillPdfTemplatesDetailsExport", function(e) {
	 var bbDialog = $("#pdfBilldiv");
		bbDialog.kendoWindow({
			width : "auto",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Select Month"
		}).data("kendoWindow").center().open();
		var dropdownlist1 = $("#serviceTypePostPdf").data("kendoDropDownList");
		dropdownlist1.value(""); 
		$("#presentdatepostBillPdf").val("");
		bbDialog.kendoWindow("open");
});	

var postbillvalidator = $("#searchBillForm").kendoValidator().data("kendoValidator");
$("#searchBills").on("click", function() {

	if (postbillvalidator.validate()) {

		searchBillMetod();
	}
});
var excelvalidator = $("#excelBillForm").kendoValidator().data("kendoValidator");
$("#excelBills").on("click", function() {

	if (excelvalidator.validate()) {

		excelBillMetod();
	}
});
var pdfvalidator = $("#pdfBillForm").kendoValidator().data("kendoValidator");
$("#pdfBills").on("click", function() {

	if (pdfvalidator.validate()) {

		pdfBillMetod();
	}
});
$("#searchBillForm").submit(function(e) {
	e.preventDefault();
});
$("#excelBillForm").submit(function(e) {
	e.preventDefault();
});
$("#pdfBillForm").submit(function(e) {
	e.preventDefault();
});
function excelBillMetod()
{
	
	var month=$("#presentdatepostBillExcel").val();
	var service=$("#serviceTypePostExcel").val();
	  window.open("./electricityBillTemplate/notGeneratedBillTemplatesDetailsExport?month="+month+"&service="+service); 
	  closeExcel();
}
function pdfBillMetod()
{
	
	var month=$("#presentdatepostBillPdf").val();
	var service=$("#serviceTypePostPdf").val();
	window.open("./electricityBillPdfTemplate/notGeneratedBillPdfTemplatesDetailsExport?month="+month+"&service="+service);
	  closePDF();
}

function closeExcel() {
	var todcal = $("#excelBilldiv");
	todcal.kendoWindow({
		width : "auto",
		height : "auto",
		modal : true,
		draggable : true,
		position : {
			top : 100
		},
		title : "Search All Bills"
	}).data("kendoWindow").center().close();
	todcal.kendoWindow("close");
}
function closePDF() {
	var bbDialog = $("#pdfBilldiv");
	bbDialog.kendoWindow({
		width : "auto",
		height : "auto",
		modal : true,
		draggable : true,
		position : {
			top : 100
		},
		title : "Search All Bills"
	}).data("kendoWindow").center().close();

	bbDialog.kendoWindow("close"); 
	  
	
	/* $("#pdfBillDiv").submit(function() {
		  $(this).closest(".ui-dialog-content").dialog("close");
		}); */
	// $("#kendoWindow").data("tWindow").close();
}
function searchBillMetod()
{
	
var serviceTypePost = $("#serviceTypePost").val();
var presentdatePost = $("#presentdatepostBillLedger").val();
$('#commitplaceholderNewPost').show();
$('#searchBills').hide();

$.ajax({		
	
	type : "POST",
	url : "./noGeneratedBill/searchBillsMonthWiseAndServiceWise",
	dataType : "json",
	data : {
		serviceTypePost : serviceTypePost,
		presentdatePost : presentdatePost,
	},
	success : function(response) {
		
		$('#commitplaceholderNewPost').hide();
     	$('#searchBills').show(); 
     	 searchClose()
		var grid = $("#consolidatedGrid").getKendoGrid();
		var data = new kendo.data.DataSource();
		grid.dataSource.data(response);
		grid.refresh();
		
	}
	
});
}
function searchClose() {

	var todcal = $("#searchBilldiv");
	todcal.kendoWindow({
		width : "auto",
		height : "auto",
		modal : true,
		draggable : true,
		position : {
			top : 100
		},
		title : "Search All Bills"
	}).data("kendoWindow").center().close();
	todcal.kendoWindow("close");
}
$("#consolidatedGrid")
.on(
		"click",
		".k-grid-searchBills",
		function(e) {
			
			var bbDialog = $("#searchBilldiv");
			bbDialog.kendoWindow({
				width : "auto",
				height : "auto",
				modal : true,
				draggable : true,
				position : {
					top : 100
				},
				title : "Search All Bills"
			}).data("kendoWindow").center().open();
			var dropdownlist1 = $("#serviceTypePost").data("kendoDropDownList");
			dropdownlist1.value(""); 
			$("#presentdatepostBillLedger").val("");
			bbDialog.kendoWindow("open");
		});




	var accountId = "";
	var billMonth = "";
	var billMonthSql = "";
	var cbId= "";
	var serviceType="";
	function onChange(arg) {
		var gview = $("#consolidatedGrid").data("kendoGrid");
		var selectedItem = gview.dataItem(gview.select());
		accountId = selectedItem.accountId;
		billMonth = selectedItem.billMonth;
		billMonthSql = selectedItem.billMonthSql;
		cbId=selectedItem.cbId;
		serviceType=selectedItem.serviceType;
		
		this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
	}
	
	
	$("#consolidatedGrid").on(
			"click",
			".k-grid-downloadAllBills",
			function(e) {
				var bbDialog = $("#downloadAllBillsDiv");
				bbDialog.kendoWindow({
					width : 300,
					height : "auto",
					modal : true,
					draggable : true,
					position : {
						top : 100
					},
					title : "Send All Mail"
				}).data("kendoWindow").center().open();

				bbDialog.kendoWindow("open");

				var dropdownlist1 = $("#serviceTypeDownload").data(
						"kendoDropDownList");
				dropdownlist1.value("");
				var presentreading = $("#presentdateDownload");
				presentreading.val("");

			});
	
	
	$(document)
	.ready(function() {
		
		
		var serviceListDownload = [ {
			text : "Electricity",
			value : "Electricity"
		}, {
			text : "Water",
			value : "Water"
		}, {
			text : "Gas",
			value : "Gas"
		}, {
			text : "CAM",
			value : "CAM"
		}, {
			text : "Telephone Broadband",
			value : "Telephone Broadband"
		}, {
			text : "Solid Waste",
			value : "Solid Waste"
		}, {
			text : "Others",
			value : "Others"
		},

		];
		$("#serviceTypePostPdf").kendoDropDownList({
			dataTextField : "text",
			dataValueField : "value",
			optionLabel : {
				text : "Select",
				value : "",
			},
			dataSource : serviceListDownload
		}).data("kendoDropDownList");
		$("#serviceTypePostExcel").kendoDropDownList({
			dataTextField : "text",
			dataValueField : "value",
			optionLabel : {
				text : "Select",
				value : "",
			},
			dataSource : serviceListDownload
		}).data("kendoDropDownList");
		$("#serviceTypePost").kendoDropDownList({
			dataTextField : "text",
			dataValueField : "value",
			optionLabel : {
				text : "Select",
				value : "",
			},
			dataSource : serviceListDownload
		}).data("kendoDropDownList");
		$("#serviceTypeDownload").kendoDropDownList({
			dataTextField : "text",
			dataValueField : "value",
			optionLabel : {
				text : "Select",
				value : "",
			},
			dataSource : serviceListDownload
		}).data("kendoDropDownList");
		
		
		
		$( "#billMasterTable" ).hide();
		 var data = [
                     { text: "January", value: "January" },
                     { text: "February", value: "February" },
                     { text: "March", value: "March" },
                     { text: "April", value: "April" },
                     { text: "May", value: "May" },
                     { text: "June", value: "June" },
                     { text: "July", value: "July" },
                     { text: "August", value: "August" },
                     { text: "September", value: "September" },
                     { text: "October", value: "October" },
                     { text: "November", value: "November" },
                     { text: "December", value: "December" },
                 ];

                 $("#month").kendoDropDownList({
                     dataTextField: "text",
                     dataValueField: "value",
                     optionLabel : {
							text : "Select",
							value : "",
						},
                     dataSource: data
                 });
                 
                 
                 $("#month").change(function () {
                	    applyFilter("billMonth", $(this).val());
                	});
                 
                 
                 function applyFilter(filterField, filterValue) {
                	    var gridData = $("#consolidatedGrid").data("kendoGrid");
                	    var currFilterObj = gridData.dataSource.filter();
                	    var currentFilters = currFilterObj ? currFilterObj.filters : [];
                	    if (currentFilters && currentFilters.length > 0) {
                	        for (var i = 0; i < currentFilters.length; i++) {
                	            if (currentFilters[i].field == filterField) {
                	                currentFilters.splice(i, 1);
                	                break;
                	            }
                	        }
                	    }
                	    if (filterValue != "0") {
                	        currentFilters.push({
                	            field: filterField,
                	            operator: "contains",
                	            value: filterValue
                	        });
                	    }
                	    gridData.dataSource.filter({
                	        logic: "and",
                	        filters: currentFilters
                	    });

                	}           				
	});
	

	
	function clearFilter() {
    	    var gridData = $("#consolidatedGrid").data("kendoGrid");
    	    gridData.dataSource.filter({});
    }
	</script>
	<style>
	table {
	width: 100%;
	/* margin-bottom: 1em; */
}
td {
	vertical-align: top;
}

/* th {
	font-weight: bold;
}

th,td {
	padding: 0.5em;
	border: 1px solid #ccc;
	vertical-align: top;
} */


#hovertd:hover {
    background: #eee!important;
}

#hovertd {
text-align : center;
   padding: 15px 20px;
    border-right: 1px solid #d5d5d5;
    background: #d4d4d4;
    border-bottom: 1px solid #d5d5d5;
 }

	</style>