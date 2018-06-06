<%@include file="/common/taglibs.jsp"%>
 <script type="text/javascript"
	src="<c:url value='/resources/jquery-validate.js'/>"></script>
<!--   <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script> -->
<c:url value="/bills/readTowerNames" var="towerNames" />		
<c:url value="/prePaidPayment/getPropertyNamesUrl"
	var="getPropertyNamesUrl" />
<c:url value="/prePaidPayment/getMeterNumberUrl" var="getMeterNumberUrl" />
<script type="text/javascript">
$(document).ready(function() {

	$("#blockNamePre").kendoDropDownList({
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

	
	var propertyTypeList = [ {
		text : "All",
		value : "All"
	}, {
		text : "Specific",
		value : "Specific"
	},

	];

	$("#propertyType").kendoDropDownList({
		dataTextField : "text",
		dataValueField : "value",
		optionLabel : {
			text : "Select",
			value : "",
		},
		dataSource : propertyTypeList
	}).data("kendoDropDownList");

	
});
</script>

<kendo:grid name="grid" pageable="true" resizable="true" sortable="true"
	reorderable="true" selectable="false" scrollable="true"
	filterable="false" groupable="true">
	<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10"
		input="true" numeric="true" refresh="true" info="true"
		previousNext="true">
		<kendo:grid-pageable-messages itemsPerPage="Status items per page"
			empty="No status item to display"
			refresh="Refresh all the status items"
			display="{0} - {1} of {2} New Status Items"
			first="Go to the first page of status items"
			last="Go to the last page of status items"
			next="Go to the next page of status items"
			previous="Go to the previous page of status items" />
	</kendo:grid-pageable>
	<kendo:grid-filterable extra="false">
		<kendo:grid-filterable-operators>
			<kendo:grid-filterable-operators-string eq="Is equal to"
				contains="Contains" />
			<kendo:grid-filterable-operators-date gt="Is after" lt="Is before" />
		</kendo:grid-filterable-operators>
	</kendo:grid-filterable>
	<kendo:grid-editable mode="popup" />
	<kendo:grid-toolbar>

		<kendo:grid-toolbarItem name="billdata" text="getConsumptionData" />
		<kendo:grid-toolbarItem name="dayWise" text="getAllConsumptionData"></kendo:grid-toolbarItem>
		<%-- <kendo:grid-toolbarItem name="getdata" text="getAlL"></kendo:grid-toolbarItem> --%>
	<%--   <kendo:grid-toolbarItem template="<label class='category-label'>&nbsp;&nbsp;From&nbsp;Date&nbsp;:&nbsp;&nbsp;</label><input id='fromMonthpicker' style='width:130px'/>"/>
		<kendo:grid-toolbarItem template="<label class='category-label'>&nbsp;&nbsp;To&nbsp;Date&nbsp;:&nbsp;&nbsp;</label><input id='toMonthpicker' style='width:130px'/>"/> --%>
		<%-- <kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=searchByMonth() title='Search' style='width:130px'>Search</a>"/> --%>
		<kendo:grid-toolbarItem name="exportConsumptionData" text="Export To Excel"></kendo:grid-toolbarItem>
		<kendo:grid-toolbarItem name="exportConsumptionDataPDF" text="Export To PDF"></kendo:grid-toolbarItem>
		<kendo:grid-toolbarItem name="getGenusConsumption" text="getConsumptionFromGenus"></kendo:grid-toolbarItem>
<kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilter()>Clear Filter</a>"/>		<%--  <kendo:grid-toolbarItem name="getData" text="getCosumData" /> --%>
		<%--  <kendo:grid-toolbarItem name="activateAll" text="Activate All" />
		       <kendo:grid-toolbarItem name="amrTemplatesDetailsExport" text="Export To Excel" />
		       <kendo:grid-toolbarItem name="amrPdfTemplatesDetailsExport" text="Export To PDF" />
		     <kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterAMRSettings()><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>"/> --%>
	</kendo:grid-toolbar>
	<kendo:grid-columns>
		<kendo:grid-column title="prePaid Id" field="prePaidId" width="100px"
			filterable="true" hidden="true" sortable="false" />
		<kendo:grid-column title="Meter Number" field="mtrSrNo" width="100px"
			filterable="true" sortable="false" />
		<kendo:grid-column title="Reading Date" field="readingDT"
			width="100px" filterable="false" sortable="false" />
		<kendo:grid-column title="Balance" field="balance" width="70px"
			filterable="true" sortable="false" />
		<kendo:grid-column title="Consumed Amount" field="daily_Cons_Amt"
			width="70px" filterable="false" sortable="false" />
		<kendo:grid-column title="Cum. Recharge Amount"
			field="cum_Recharge_Amount" width="150px" filterable="false"
			sortable="false" />
		<kendo:grid-column title="Cum. kWh" field="cum_Kwh_Main" width="80px"
			filterable="false" sortable="false" />
		<kendo:grid-column title="Cum.kWh(DG)" field="cum_Kwh_Dg"
			width="100px" filterable="false" sortable="false" />
		<kendo:grid-column title="Cum FixChgMain" field="cum_Fixed_Charg_Main"
			width="100px" filterable="false" sortable="false" />
		<kendo:grid-column title="Cum FixChgDg" field="cum_Fixed_Charg_Dg"
			width="100px" filterable="false" sortable="false" />

 
	</kendo:grid-columns>

	<kendo:dataSource pageSize="20">
		<kendo:dataSource-transport>
			<%-- <kendo:dataSource-transport-create url="${createUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json" />
			<kendo:dataSource-transport-update url="${updateUrl}" dataType="json" type="GET" contentType="application/json" /> --%>
			<%-- <kendo:dataSource-transport-destroy url="${destroyUrl}/" dataType="json" type="GET" contentType="application/json" /> --%>
		</kendo:dataSource-transport>

		<kendo:dataSource-schema>
			<kendo:dataSource-schema-model id="prePaidId">
				<kendo:dataSource-schema-model-fields>
					<kendo:dataSource-schema-model-field name="prePaidId" type="number" />
					<kendo:dataSource-schema-model-field name="mtrSrNo" type="string" />
					<kendo:dataSource-schema-model-field name="readingDT" type="String" />
					<kendo:dataSource-schema-model-field name="daily_Cons_Amt"
						type="string" />
					<kendo:dataSource-schema-model-field name="cum_Recharge_Amount"
						type="string" />
					<kendo:dataSource-schema-model-field name="cum_Kwh_Main"
						type="string" />
					<kendo:dataSource-schema-model-field name="cum_Kwh_Dg"
						type="string" />
					<kendo:dataSource-schema-model-field name="cum_Fixed_Charg_Main"
						type="string" />
					<kendo:dataSource-schema-model-field name="balance" type="string" />
					<kendo:dataSource-schema-model-field name="cum_Fixed_Charg_Dg"
						type="string" />
				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>

	</kendo:dataSource>

</kendo:grid>

<div id="indivisualInstantDatadiv" style="display: none;">
	<form id="indivisualInstantDatadivform">
		<table style="height: 190px;">

			<tr>
				<td>Property No</td>
				<td><input type="text" id="propertyId" name="propertyId"></td>
				<td></td>
			</tr>

			<tr>
				<td>Meter Number</td>
				<td><input type="text" id="consumerId" name="consumerId"></td>
				<td></td>
			</tr>

			 <tr>
				<td>From Date</td>
				<td><input type="date" name="fromDate" id="fromDate" required="required"></td>
				<td></td>
			</tr>

			<tr>
				<td>To Date</td>
				<td><input type="date" name="toDate" id="toDate" required="required"></td>
				<td></td>
			</tr> 


			<tr>
				<td class="left" align="center" colspan="4">
					<button type="submit" id="getInstantData" class="k-button"
						style="padding-left: 10px">Submit</button>
			</tr>

		</table>
	</form>
</div>

<div id="consumptionHtyDayWise" style="display: none;">
	<form id="consumptionHtyDayWiseform">
		<table style="height: 190px;">

			 <tr>
				<td>From Date</td>
				<td><input type="date" name="fromMonthpicker" id="fromMonthpicker" required="required"></td>
				<td></td>
			</tr>

			<tr>
				<td>To Date</td>
				<td><input type="date" name="toMonthpicker" id="toMonthpicker" required="required"></td>
				<td></td>
			</tr> 


			<tr>
				<td class="left" align="center" colspan="4">
					<button type="submit" id="datasubmit" class="k-button"
						style="padding-left: 10px">Search</button>
			</tr>

		</table>
	</form>
</div>
<div id="exportPdfDatadiv" style="display: none;">
	<form id="exportPdfDatadivform">
		<table style="height: 190px;">

			<tr>
				<td>Property No</td>
				<td><input type="text" id="property" name="propertyId"></td>
				<td></td>
			</tr>

			<tr>
				<td>Meter Number</td>
				<td><input type="text" id="consumerNumber" name="consumerId"></td>
				<td></td>
			</tr>

			 <tr>
				<td>Month</td>
				<td><input type="date" name="fromDate" id="fromMonth" required="required"></td>
				<td></td>
			</tr>

			<!-- <tr>
				<td>To Date</td>
				<td><input type="date" name="toDate" id="toDate" required="required"></td>
				<td></td>
			</tr>  -->


			<tr>
				<td class="left" align="center" colspan="4" style="padding-left: 20px;">
					<button type="submit" id="exportPdf" class="k-button"
						style="padding-left: 8px">Export To PDF</button>
			</tr>

		</table>
	</form>
</div>

<!-- 
<div id="getconsumptionDatadiv" style="display: none;">
	<form id="getconsumptionDatadivform">
		<table style="height: 190px;">

			<tr>
				<td>Property No</td>
				<td><input type="text" id="propertyId1" name="propertyId"></td>
				<td></td>
			</tr>

			<tr>
				<td>Meter Number</td>
				<td><input type="text" id="mtrNo" name="consumerId"></td>
				<td></td>
			</tr>

			 <tr>
				<td>From Date</td>
				<td><input type="date" name="fromDate" id="fromMonthpicker1" required="required"></td>
				<td></td>
			</tr>

			<tr>
				<td>To Date</td>
				<td><input type="date" name="toDate" id="toMonthpicker1" required="required"></td>
				<td></td>
			</tr> 


			<tr>
				<td class="left" align="center" colspan="4">
					<button type="submit" id="getGenusData" class="k-button"
						style="padding-left: 10px">Submit</button>
			</tr>

		</table>
	</form>
</div> -->
<div id="getconsumptionDatadiv" style="display: none;">
	<form id="getconsumptionDatadivform" data-role="validator" novalidate="novalidate">
		<table style="height: 263px;">
			<tr>
				<td>Block Name</td>
				<td><input id="blockNamePre" name="blockNamePre"
					required="required" validationMessage="Select Block Name"
					onchange="getPropertyNoAmr()" /> <!-- onchange="getPropertyNo()" --></td>
			</tr>
			<tr id="protypemultiselectAmr">
				<td>Property Type</td>
				<td><input id="propertyType" name="propertyType"
					required="required" onchange="selectallprop()"
					validationMessage="Select Type" /> <!-- onchange="getPropertyNo()" --></td>
			</tr>
			<tr id="propmultiselectAmr">
				<td id="propmultiselectAmr1">Property Number</td>
				<td id="propmultiselectAmr2"><kendo:multiSelect
						id="propertyNameAmr" name="propertyNameAmr" required="required"
						validationMessage="Select Property No"></kendo:multiSelect></td>
			</tr>

			<tr>
				<td>From Date</td>
				<td><input type="date" name="fromDate" id="fromMonthpicker1" required="required"></td>
				<td></td>
			</tr>

			<tr>
				<td>To Date</td>
				<td><input type="date" name="toDate" id="toMonthpicker1" required="required"></td>
				<td></td>
			</tr> 


			<tr>
				<td class="left" align="center" colspan="4">
					<!-- <button type="button" id="verify" class="k-button"
						style="padding-left: 10px">Verify</button> -->
					<button type="submit" id="getGenusData" class="k-button"
						style="padding-left: 10px">getGenusData</button> <span
					id=amrplaceholder style="display: none;"><img
						src="./resources/images/loadingimg.GIF"
						style="vertical-align: middle; margin-left: 50px" /> &nbsp;&nbsp;
						<img src="./resources/images/loading.GIF" alt="loading"
						style="vertical-align: middle margin-left: 50px" height="20px"
						width="200px; " /></span>
				</td>
			</tr>

		</table>
	</form>
</div>
<div id="alertsBox" title="Alert"></div>
<script>

$("#grid").on("click", ".k-grid-getGenusConsumption", function(e) {
	var bbDialog = $("#getconsumptionDatadiv");
	bbDialog.kendoWindow({
		width : "auto",
		height : "auto",
		modal : true,
		draggable : true,
		position : {
			top : 100
		},
		title : "Consumption History"
	}).data("kendoWindow").center().open();

	bbDialog.kendoWindow("open");
	var dropdownlist2 = $("#blockNamePre").data("kendoDropDownList");
	dropdownlist2.value("");
	var dropdownlist1 = $("#propertyType").data("kendoDropDownList");
	dropdownlist1.value("");

	var EmptyArray = new Array();
	var ddlMulti = $('#propertyNameAmr').data('kendoMultiSelect');
	ddlMulti.value(EmptyArray);  

});

function getPropertyNoAmr() {
	var blockId = $('#blockNamePre').val();
	var blockName = $("input[name=blockNamePre]").data("kendoDropDownList")
			.text();
	$('#propmultiselectAmr1').remove();
	$('#propmultiselectAmr2').remove();
	if (blockName == 'All Blocks') {
		$('#propmultiselectAmr').hide();
		$('#protypemultiselectAmr').hide();
		$('#propertyType').removeAttr('required');
		$('#propmultiselectAmr2').removeAttr('required');
	}

	$('#propmultiselectAmr')
			.append(
					"<td id='propmultiselectAmr1'>Property Name </td><td id='propmultiselectAmr2'><input id='propertyNameAmr' name='propertyNameAmr'/> </td>");
	$("#propertyNameAmr").kendoMultiSelect({
		autoBind : false,
		dataValueField : "propertyId",
		dataTextField : "property_No",
		dataSource : {
			transport : {
				read : {
					url : "./prepaidDataprops/getPropertyNos?blockId=" + blockId,
				}
			}
		}
	});

	/* var datepicker = $("#presentdateAmr").data("kendoDatePicker");
	datepicker.max(new Date()); */
	recur();
}

function recur() {
	var multiSelect = $("#propertyNameAmr").data("kendoMultiSelect");
	var selectedValues = "";
	var strComma = "";
	for (var i = 0; i < multiSelect.dataSource.data().length; i++) {
		var item = multiSelect.dataSource.data()[i];
		selectedValues += strComma + item.propertyId;
		strComma = ",";
	}
	multiSelect.value(selectedValues.split(","));
}

function selectallprop() {

	var mul = $("#propertyNameAmr").text();
	var propertyType = $("input[name=propertyType]").data(
			"kendoDropDownList").text();
	var multiSelect = $("#propertyNameAmr").data("kendoMultiSelect");

	if (propertyType == 'All') {

		//alert(multiSelect.dataSource.data().length);
		if (multiSelect.dataSource.data().length == 0) {
			var dropdownlist1 = $("#propertyType")
					.data("kendoDropDownList");
			dropdownlist1.value("");
			// $("#propmultiselectAmr").hide();

		} else {
			var selectedValues = "";
			var strComma = "";
			for (var i = 0; i < multiSelect.dataSource.data().length; i++) {
				var item = multiSelect.dataSource.data()[i];

				selectedValues += strComma + item.propertyId;
				//alert(selectedValues);
				strComma = ",";
			}
			multiSelect.value(selectedValues.split(","));
		}
	} else {
		//  var multiSelect = $("#propertyNameAmr").data("kendoMultiSelect");
		multiSelect.value(" ");
	}
}
	$("#propertyId").kendoComboBox({
		autoBind : false,
		optionLabel : "Select",
		dataTextField : "property_No",
		dataValueField : "propertyId",
		dataSource : {
			transport : {
				read : "${getPropertyNamesUrl}"
			}
		}
	});

	$("#consumerId").kendoComboBox({
		autoBind : false,
		cascadeFrom : "propertyId",
		dataTextField : "consumerId",
		dataValueField : "consumerId",
		dataSource : {
			transport : {
				read : "${getMeterNumberUrl}"
			}
		}
	});
	
	$("#property").kendoComboBox({
		autoBind : false,
		optionLabel : "Select",
		dataTextField : "property_No",
		dataValueField : "propertyId",
		dataSource : {
			transport : {
				read : "${getPropertyNamesUrl}"
			}
		}
	});

	$("#consumerNumber").kendoComboBox({
		autoBind : false,
		cascadeFrom : "property",
		dataTextField : "consumerId",
		dataValueField : "consumerId",
		dataSource : {
			transport : {
				read : "${getMeterNumberUrl}"
			}
		}
	});
	

	/* $("#propertyId1").kendoComboBox({
		autoBind : false,
		optionLabel : "Select",
		dataTextField : "property_No",
		dataValueField : "propertyId",
		dataSource : {
			transport : {
				read : "${getPropertyNamesUrl}"
			}
		}
	});

	$("#mtrNo").kendoComboBox({
		autoBind : false,
		cascadeFrom : "propertyId",
		dataTextField : "consumerId",
		dataValueField : "consumerId",
		dataSource : {
			transport : {
				read : "${getMeterNumberUrl}"
			}
		}
	}); */
	/* function propertyEditor(container, options) 
	{
	$('<input name="Property No" id="property_No" data-text-field="property_No" data-value-field="propertyId" data-bind="value:' + options.field + '" required="true"/>')
	.appendTo(container).kendoComboBox({
		autoBind : false,			
		dataSource : {
			transport : {		
				read :  "${getPropertyNamesUrl}"
			}
		},
		change : function (e) {
	        if (this.value() && this.selectedIndex == -1) {                    
				alert("P doesn't exist!");
	            $("#property_No").data("kendoComboBox").value("");
	    	}

	    }  
	}); 

	$('<span class="k-invalid-msg" data-for="Property No"></span>').appendTo(container);
	} */

	/* function meterNumberEditor(container, options) 
	{
	$('<input name="Consumer Id" id="consumerId" data-text-field="consumerId" data-value-field="consumerId" data-bind="value:' + options.field + '" required="true"/>')
	.appendTo(container).kendoComboBox({
		cascadeFrom : "property_No", 
		autoBind : false,			
		dataSource : {
			transport : {		
				read :  "${getMeterNumberUrl}"
			}
		},
		change : function (e) {
	        if (this.value() && this.selectedIndex == -1) {                    
				alert("P doesn't exist!");
	            $("#consumerId").data("kendoComboBox").value("");
	    	}

	    }  
	});

	$('<span class="k-invalid-msg" data-for="Consumer Id"></span>').appendTo(container);
	}
	 */

	$("#grid").on("click", ".k-grid-billdata", function(e) {
		var bbDialog = $("#indivisualInstantDatadiv");
		bbDialog.kendoWindow({
			width : "auto",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Consumption History"
		}).data("kendoWindow").center().open();

		bbDialog.kendoWindow("open");
		
		 $("#propertyId").data("kendoComboBox").value("");
			
			$("#consumerId").data("kendoComboBox").value("");

	});

	
	 $("#grid").on("click", ".k-grid-dayWise", function(e) {
			var bbDialog = $("#consumptionHtyDayWise");
			bbDialog.kendoWindow({
				width : "auto",
				height : "auto",
				modal : true,
				draggable : true,
				position : {
					top : 100
				},
				title : "Consumption History"
			}).data("kendoWindow").center().open();

			bbDialog.kendoWindow("open");

		});

	$("#grid").on("click", ".k-grid-getdata", function(e) {
		window.location.href ="./consumptionHty/prepaidbilldataDaily";
		
	}); 
	$("#indivisualInstantDatadivform").submit(function(e) {
		e.preventDefault();
	});
	$("#consumptionHtyDayWiseform").submit(function(e) {
		e.preventDefault();
	});
	$("#exportPdfDatadivform").submit(function(e) {
		e.preventDefault();
	});
	
	$("#getconsumptionDatadivform").submit(function(e) {
		e.preventDefault();
	});
$("#fromDate").kendoDatePicker({
		// defines the start view
		start : "year",
		// defines when the calendar should return date
		depth : "year",
		value : new Date(),
		// display month and year in the input
		format : "MMMM yyyy"
	});

	$("#toDate").kendoDatePicker({
		// defines the start view
		start : "year",
		// defines when the calendar should return date
		depth : "year",
		value : new Date(),
		// display month and year in the input
		format : "MMMM yyyy"
	});
$("#fromMonth").kendoDatePicker({
	// defines the start view
	start : "year",
	// defines when the calendar should return date
	depth : "year",
	value : new Date(),
	// display month and year in the input
	format : "MMMM yyyy"
  });
	$("#fromMonthpicker").kendoDatePicker({
		// defines the start view
		//start : "year",
		// defines when the calendar should return date
		//depth : "year",
		value : new Date(),
		// display month and year in the input
		format : "dd/MM/yyyy"
	});
	$("#toMonthpicker").kendoDatePicker({
		// defines the start view
		//start : "year",
		// defines when the calendar should return date
		//depth : "year",
		value : new Date(),
		// display month and year in the input
		format : "dd/MM/yyyy"
	});
	
	$("#fromMonthpicker1").kendoDatePicker({
		// defines the start view
		//start : "year",
		// defines when the calendar should return date
		//depth : "year",
		value : new Date(),
		// display month and year in the input
		format : "dd/MM/yyyy"
	});
	$("#toMonthpicker1").kendoDatePicker({
		// defines the start view
		//start : "year",
		// defines when the calendar should return date
		//depth : "year",
		value : new Date(),
		// display month and year in the input
		format : "dd/MM/yyyy"
	});
	function filterclose() {

		var todcal = $("#indivisualInstantDatadiv");

		todcal.kendoWindow({
			width : "auto",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Approve Bills"
		}).data("kendoWindow").center().close();
		todcal.kendoWindow("close");
	}
	function filterclose1() {

		var todcal = $("#consumptionHtyDayWise");

		todcal.kendoWindow({
			width : "auto",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Approve Bills"
		}).data("kendoWindow").center().close();
		todcal.kendoWindow("close");
	}
	function filterclose4() {

		var todcal = $("#getconsumptionDatadiv");

		todcal.kendoWindow({
			width : "auto",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Approve Bills"
		}).data("kendoWindow").center().close();
		todcal.kendoWindow("close");
	}
	function filterclose3() {

		var todcal = $("#exportPdfDatadiv");

		todcal.kendoWindow({
			width : "auto",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Approve Bills"
		}).data("kendoWindow").center().close();
		todcal.kendoWindow("close");
	}

	$("#getInstantData").click(function() {

		var propertyID = $("#propertyId").val();
		var fromDate = $("#fromDate").val();
		//alert(fromDate);
		var toDate = $("#toDate").val();
		var consumerID = $("#consumerId").val();
		if (propertyID == null || propertyID == "") {
			alert("Please Select Property No")
			return false;
		} else if (consumerID == null || consumerID == "") {
			alert("Please Select Consumer Id");
			return false;
		} 
		//alert("fromdate="+fromDate+"  todate="+toDate);
		var d1=new Date(fromDate);
		//alert(d1);
		var fDate=fromDate.split(" ").join("-");
		//alert(fDate);
		var tDate=toDate.split(" ").join("-");
		if (fDate > tDate) {
			alert("fromDate Should be Less then toDate");
			return false;
		}
		// alert(accountNo);
		
		//alert(toDate);

		$.ajax({
			url : "./consumptionHistory/readData",
			type : 'GET',
			dataType : "json",
			data : {
				
				consumerID : consumerID,
				fromDate : fromDate,
				toDate : toDate
			},
			contentType : "application/json; charset=utf-8",
			success : function(result) {

				filterclose();
				var grid = $("#grid").getKendoGrid();
				var data = new kendo.data.DataSource();
				grid.dataSource.data(result);
				grid.refresh();
			},

		});
	});
	

var amrcalculatevalidator = $("#getconsumptionDatadivform").kendoValidator().data(
	"kendoValidator");
$("#getGenusData").on("click", function() {

if (amrcalculatevalidator.validate()) {

	getGenusConsumpBill();
}
});
	function getGenusConsumpBill() {
 	    var fromDate = $('#fromMonthpicker1').val();
 	    //alert(fromDate);
 	    var toDate = $('#toMonthpicker1').val();
 	   //alert(toDate);
 	       $("#getGenusData").hide();
			$("#amrplaceholder").show();
			var propertyId = $("input[name=propertyNameAmr]").data(
					"kendoMultiSelect").value();
			var blocId = $("#blockNamePre").val();
			var blockName = $("input[name=blockNamePre]").data("kendoDropDownList")
					.text();
 	    var splitmonth=fromDate.split("/");
 		   var splitmonth1=toDate.split("/");
 		   if(splitmonth[2]>splitmonth1[2])
 			   {
 			   alert("To Date should be greater than From Date");
 			   return false;
 			   }
 		   if(splitmonth[2]==splitmonth1[2])
 			   {
 			   if(splitmonth[1]>splitmonth1[1])
 				   {
 				   alert("To Date should be greater than From Date");
 				   return false;
 				   }
 			   else if(splitmonth[1]==splitmonth1[1])
 				   {
 				   if(splitmonth[0]>splitmonth1[0])
 					   {
 					   alert("To Date should be greater than From Date");
 					   return false;
 					   }
 				   
 				   }
 			   }
 		   
 		    $.ajax({
 			type : "POST",
 			url : "./getGenusConsumption",
 			dataType : "json",
 			data : {
 				blocId : blocId,
				blockName : blockName,
				propertyId : "" + propertyId + "",
 				fromDate : fromDate,
 				toDate : toDate
 			},
 			success : function(result) {
 				//parse(result);
 				$("#getGenusData").show();
			$("#amrplaceholder").hide();
 				filterclose4();
 				var grid = $("#grid").getKendoGrid();
 				var data = new kendo.data.DataSource();
 				grid.dataSource.data(result);
 				grid.refresh();
 			}
 		}); 
	}
	 
	$("#datasubmit").click(function() {
 	    var fromDate = $('#fromMonthpicker').val();
 	    //alert(fromDate);
 	    var toDate = $('#toMonthpicker').val();
 	   //alert(toDate);
 	    var splitmonth=fromDate.split("/");
 		   var splitmonth1=toDate.split("/");
 		   if(splitmonth[2]>splitmonth1[2])
 			   {
 			   alert("To Date should be greater than From Date");
 			   return false;
 			   }
 		   if(splitmonth[2]==splitmonth1[2])
 			   {
 			   if(splitmonth[1]>splitmonth1[1])
 				   {
 				   alert("To Date should be greater than From Date");
 				   return false;
 				   }
 			   else if(splitmonth[1]==splitmonth1[1])
 				   {
 				   if(splitmonth[0]>splitmonth1[0])
 					   {
 					   alert("To Date should be greater than From Date");
 					   return false;
 					   }
 				   
 				   }
 			   }
 		   
 		    $.ajax({
 			type : "GET",
 			url : "./getconsumptionHistirySearchByMonth",
 			dataType : "json",
 			data : {
 				fromDate : fromDate,
 				toDate : toDate
 			},
 			success : function(result) {
 				//parse(result);
 				filterclose1();
 				var grid = $("#grid").getKendoGrid();
 				var data = new kendo.data.DataSource();
 				grid.dataSource.data(result);
 				grid.refresh();
 			}
 		}); 
	});
	 
	$("#exportPdf").click(function() {

		var propertyId = $("#property").val();
		//alert(propertyId);
		var fromDate = $("#fromMonth").val();
		//alert(fromDate);
		var consumerID = $("#consumerNumber").val();
		//alert(consumerID);
		if (propertyId == null || propertyId == "") {
			alert("Please Select Property No")
			return false;
		} else if (consumerID == null || consumerID == "") {
			alert("Please Select Consumer Id");
			return false;
		} 
		window.open("./consumptionHistory/exportPDFConsumptionData?fromDate="+fromDate+"&consumerID="+consumerID+"&propertyId="+propertyId);
		filterclose3();
		
	});
	 $("#grid").on("click",".k-grid-exportConsumptionData",function(e){
		 var fromDate = $('#fromMonthpicker').val();
	 	    //alert(fromDate);
	 	    var toDate = $('#toMonthpicker').val();
		 window.open("./consumptionHistory/exportConsumptionData?date1="+fromDate+"&date2="+toDate);
	 });
	 
	 $("#grid").on("click",".k-grid-exportConsumptionDataPDF",function(e){
			/* var fromDate1 = $("#fromDate").val();
			//alert(fromDate);
			var toDate1 = $("#toDate").val();
		 window.open("./consumptionHistory/exportPDFConsumptionData?date1="+fromDate1+"&date2="+toDate1); */
		 var bbDialog = $("#exportPdfDatadiv");
			bbDialog.kendoWindow({
				width : "auto",
				height : "auto",
				modal : true,
				draggable : true,
				position : {
					top : 100
				},
				title : "Consumption History"
			}).data("kendoWindow").center().open();

			bbDialog.kendoWindow("open");
			var propID=$("#property").data("kendoComboBox");
			propID.value("");
			$("#fromMonth").val();
			
			   $("#consumerNumber").data("kendoComboBox").value("");

	 });
	 function clearFilter() {
			//custom actions

			$("form.k-filter-menu button[type='reset']").slice()
					.trigger("click");
			var gridServiceMaster = $("#grid").data("kendoGrid");
			gridServiceMaster.dataSource.read();
			gridServiceMaster.refresh();
		}
</script>