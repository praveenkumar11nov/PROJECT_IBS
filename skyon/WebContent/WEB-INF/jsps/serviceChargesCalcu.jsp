<%@include file="/common/taglibs.jsp"%>


<script type="text/javascript"
	src="<c:url value='/resources/jquery-validate.js'/>"></script>
<script src="./resources/bootbox/bootbox.min.js"></script>	
<script src="resources/js/plugins/charts/highcharts.js"></script>
<script src="resources/js/plugins/charts/exporting.js"></script>

<c:url value="/prePaidPayment/getPropertyNamesUrl"
	var="getPropertyNamesUrl" />
<c:url value="/serviceChargeCalcu/getMeterNumberUrl" var="getMeterNumberUrl" />
<c:url value="/serviceChargeCalcu/getServiceNamesUrl" var="getServiceNamesUrl"></c:url>
<c:url value="/common/getAllChecks" var="allChecksUrl" />
 <c:url value="/serviceChargeCalcu/readUrl" var="readUrl"></c:url> 
 <c:url value="/bills/readTowerNames" var="towerNames" />
<%-- <c:url value="/prepaidCharges/updateUrl" var="updateUrl"></c:url>
<c:url value="/prepaidCharges/destroyUrl" var="destroyUrl"></c:url> --%>
<c:url value="/serviceChargeCalcu/filter" var="commonFilterForTransactionMasterUrl"></c:url>

<kendo:grid name="prepaidGrid"  pageable="true" resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true" filterable="false" groupable="true">

    <kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" refresh="true" info="true" previousNext="true">
		<kendo:grid-pageable-messages itemsPerPage="transaction codes per page" empty="No transaction code to display" refresh="Refresh all the transaction codes" 
			display="{0} - {1} of {2} New transaction codes" first="Go to the first page of transaction codes" last="Go to the last page of transaction codes" next="Go to the next page of transaction codes"
			previous="Go to the previous page of transaction codes"/>
	</kendo:grid-pageable>
	<kendo:grid-filterable extra="false">
		<kendo:grid-filterable-operators>
			<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains"/>
			<%-- <kendo:grid-filterable-operators-date gt="Is after" lt="Is before"/> --%>
		</kendo:grid-filterable-operators> 
	</kendo:grid-filterable>
	<kendo:grid-editable mode="popup"/>
		<kendo:grid-toolbar>
		      <kendo:grid-toolbarItem name="calculation" text="Calculate The Charges" />
		      <%-- <kendo:grid-toolbarItem name="transactionTemplatesDetailsExport" text="Generate Bill" /> --%>
 		     <%--   <kendo:grid-toolbarItem name="transactionTemplatesDetailsExport" text="Export To Excel" />
		            <kendo:grid-toolbarItem name="transactionPdfTemplatesDetailsExport" text="Export To PDF" /> --%>
		      <kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterTransactionMaster()>Clear Filter</a>"/>
		      <kendo:grid-toolbarItem name="PrePaid_CamBill_Xml_Generation" text="PrePaid_CamBill_Xml_Generation" />
	    </kendo:grid-toolbar>
	<kendo:grid-columns>
	    
	    <kendo:grid-column title="SCCID" field="sccId" width="70px" hidden="true" filterable="false" sortable="false" />
	    
	    <kendo:grid-column title="Customer&nbsp;Name&nbsp;*"  field="customer_Name" filterable="true" width="110px">
			<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function apCodeFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Servicetype",
									dataSource : {
										transport : {
											read : "${commonFilterForTransactionMasterUrl}/service_Name"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			
	   <kendo:grid-column title="Property&nbsp;No" field="property_No" width="90px"  sortable="false" />
	   <kendo:grid-column title="AREA" field="area" width="70px"  sortable="false" />
	   <kendo:grid-column title="Reading Date" field="reading_Date" width="100px" format="{0:dd/MM/yyyy}" sortable="false" />
	   <kendo:grid-column title="Service Name" field="service_Name" width="100px"  sortable="false" />
	   <kendo:grid-column title="Charge Name" field="charge_Name" width="100px"  sortable="false" />
	   <kendo:grid-column title="Rate" field="rate" width="70px" sortable="false" />
	   <kendo:grid-column title="Calcuation Basis" field="calculation_Basis"  width="120px" sortable="false" />
	   <kendo:grid-column title="Daily Units Consumed" field="daily_units_consumed" width="150px" sortable="false" />
	   <kendo:grid-column title="Daily Consumption Amount" field="daily_Consumption_amt" width="180px" sortable="false" />
	   <kendo:grid-column title="Daily Recharge Amount" field="daily_Rech_amt" width="155px" sortable="false" hidden="true"/>
	   <kendo:grid-column title="Daily Balance" field="daily_balance" width="100px" sortable="false" hidden="true"/>
			
			
	</kendo:grid-columns>

	    <kendo:dataSource pageSize="10" requestEnd="onRequestEnd">
	        <kendo:dataSource-transport>
			<kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json" />
		</kendo:dataSource-transport> 
<!--  data="data" total="total" groups="data"   -->
		<kendo:dataSource-schema >
			<kendo:dataSource-schema-model id="sccId">
				<kendo:dataSource-schema-model-fields>
					<kendo:dataSource-schema-model-field name="sccId" type="number"/>
					<kendo:dataSource-schema-model-field name="customer_Name" type="string">
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="property_No" type="string"/>
				    <kendo:dataSource-schema-model-field name="area" type="string"/>
					<kendo:dataSource-schema-model-field name="reading_Date" type="string"/>
					<kendo:dataSource-schema-model-field name="service_Name" type="string"/>
					<kendo:dataSource-schema-model-field name="charge_Name" type="string"/>
					<kendo:dataSource-schema-model-field name="rate" type="string"/>
					<kendo:dataSource-schema-model-field name="calculation_Basis" type="string"/>
					<kendo:dataSource-schema-model-field name="daily_units_consumed" type="number"/>
					<kendo:dataSource-schema-model-field name="daily_Consumption_amt" type="number"/>
					<kendo:dataSource-schema-model-field name="daily_Rech_amt" type="string"/>
					<kendo:dataSource-schema-model-field name="daily_balance" type="number"/>
					
					
				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>

	</kendo:dataSource>

</kendo:grid>
<%-- <div id="prepaidBillingDiv" style="display: none;">
	<form id="prepaidBillingForm" data-role="validator" novalidate="novalidate">
		<table style="height: 190px;">
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
				<td>Present Bill Date</td>
				<td><kendo:datePicker format="MMMM yyyy " start="year" depth="year" name="presentdateAmr" id="presentdateAmr" required="required"
						class="validate[required]" validationMessage="Date is Required">
					</kendo:datePicker>
				<td></td>
			</tr>

			<tr>
				<td class="left" align="center" colspan="4">
					<!-- <button type="button" id="verify" class="k-button"
						style="padding-left: 10px">Verify</button> -->
					<button type="submit" id="amrCalculate" class="k-button"
						style="padding-left: 10px">Generate Bill</button> <span
					id=placeholder style="display: none;"><img
						src="./resources/images/loadingimg.GIF"
						style="vertical-align: middle; margin-left: 50px" /> &nbsp;&nbsp;
						<img src="./resources/images/loading.GIF" alt="loading"
						style="vertical-align: middle margin-left: 50px" height="20px"
						width="200px; " /></span>
				</td>
			</tr>

		</table>
	</form>
</div> --%>
<div id="chrgesCalculationPopUp" style="display: none;">
	<form id="chrgesCalculationDatadivform" data-role="validator" novalidate="novalidate">
		<table style="height: 260px;">
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
				<td>Service Name</td>
				<td><input type="text" id="serviceId" name="serviceId"></td>
				<td></td>
			</tr>
             
			 <tr>
				<td>From Date</td>
				<td><input type="date"  name="fromDate" id="fromDate" required="required"></td>
				<td></td>
			</tr>

			<tr>
				<td>To Date</td>
				<td><input type="date" name="toDate" id="toDate" required="required"></td>
				<td></td>
			</tr> 


			<tr>
				<td class="left" align="center" colspan="4">
					<button type="submit" id="getData" class="k-button"
						style="padding-left: 10px">Calculate</button><span
					id=placeholder style="display: none;"><img
						src="./resources/images/loadingimg.GIF"
						style="vertical-align: middle; margin-left: 50px" /> &nbsp;&nbsp;
						<img src="./resources/images/loading.GIF" alt="loading"
						style="vertical-align: middle margin-left: 50px" height="20px"
						width="200px; " /></span>
			</tr>

		</table>
	</form>
</div>

<%--  ********************************************xmlGenerationPopup starts************************************************ --%>
<div id="xmlGenerationPopup" style="display: none;">
	<form id="chrgesCalculationDatadivform" data-role="validator" novalidate="novalidate">
		<table style="height: 260px;">
			 <tr>
				<td>From Date</td>
				<td><input type="date"  name="fromDateXML" id="fromDateXML" required="required" ></td>
				<td></td>
			</tr>

			<tr>
				<td>To Date</td>
				<td><input type="date" name="toDateXML" id="toDateXML" required="required" ></td>
				<td></td>
			</tr> 

			<tr>
				<td class="left" align="center" colspan="4">
					<button type="button" class="k-button"
						style="padding-left: 10px" onclick="return generateXmlFile();">Calculate</button><span
					id=placeholder style="display: none;"><img
						src="./resources/images/loadingimg.GIF"
						style="vertical-align: middle; margin-left: 50px" /> &nbsp;&nbsp;
						<img src="./resources/images/loading.GIF" alt="loading"
						style="vertical-align: middle margin-left: 50px" height="20px"
						width="200px; " /></span>
			</tr>

		</table>
	</form>
</div>
<%--  ********************************************ends************************************************ --%>


<div id="alertsBox" title="Alert"></div>

<script>

function onRequestEnd(e) {
	/* debugger; */

	if (typeof e.response != 'undefined') {
		
		if (e.response.status == "STATUS") {

			$("#alertsBox").html("");
			$("#alertsBox")
					.html(
							"Your Selected Service Status is Inactivated");
			$("#alertsBox").dialog({
				modal : true,
				buttons : {
					"Close" : function() {
						$(this).dialog("close");
					}
				}
			});
			$('#prepaidGrid').data('kendoGrid').refresh();
			$('#prepaidGrid').data('kendoGrid').dataSource.read();
			return false;
		}
		if (e.response.status == "MAXDATE") {

			$("#alertsBox").html("");
			$("#alertsBox")
					.html(
							"From Date Show Be Greater than Previous Bill Date");
			$("#alertsBox").dialog({
				modal : true,
				buttons : {
					"Close" : function() {
						$(this).dialog("close");
					}
				}
			});
			$('#prepaidGrid').data('kendoGrid').refresh();
			$('#prepaidGrid').data('kendoGrid').dataSource.read();
			return false;
		}
		if (e.response.status == "NOCONSUMPTION") {

			$("#alertsBox").html("");
			$("#alertsBox")
					.html(
							"Consumption History is Empty");
			$("#alertsBox").dialog({
				modal : true,
				buttons : {
					"Close" : function() {
						$(this).dialog("close");
					}
				}
			});
			$('#prepaidGrid').data('kendoGrid').refresh();
			$('#prepaidGrid').data('kendoGrid').dataSource.read();
			return false;
		}
		if (e.response.status == "MSG") {

			$("#alertsBox").html("");
			$("#alertsBox")
					.html(
							"Charges Calculation Done Successfully");
			$("#alertsBox").dialog({
				modal : true,
				buttons : {
					"Close" : function() {
						$(this).dialog("close");
					}
				}
			});
			$('#prepaidGrid').data('kendoGrid').refresh();
			$('#prepaidGrid').data('kendoGrid').dataSource.read();
			return false;
		}
		

	}

}


$("#serviceId").kendoComboBox({
	autoBind : false,
	optionLabel : "Select",
	dataTextField : "service_Name",
	dataValueField : "serviceId",
	dataSource : {
		transport : {
			read : "${getServiceNamesUrl}"
		}
	}
});
function clearFilterTransactionMaster()
{
   $("form.k-filter-menu button[type='reset']").slice().trigger("click");
   var gridStoreIssue = $("#prepaidGrid").data("kendoGrid");
   gridStoreIssue.dataSource.read();
   gridStoreIssue.refresh();
}

var calculatevalidator = $("#chrgesCalculationDatadivform").kendoValidator().data("kendoValidator");
$("#getData").on("click", function() {

if (calculatevalidator.validate()) {

generateBill();
}
});

$("#prepaidGrid").on("click", ".k-grid-calculation", function(e) {
	


	  var bbDialog=$( "#chrgesCalculationPopUp" );
	bbDialog.kendoWindow({
		width : "auto",
		height : "auto",
		modal : true,
		draggable : true,
		position : {
			top : 100
		},
	      title: "Calculate Charges"
	  }).data("kendoWindow").center().open();

	bbDialog.kendoWindow("open");
	    var dropdownlist2 = $("#blockNamePre").data("kendoDropDownList");
		dropdownlist2.value("");
		var dropdownlist1 = $("#propertyType").data("kendoDropDownList");
		dropdownlist1.value("");
		$("#serviceId").data("kendoComboBox").value("");
		/* var presentreading = $("#presentdateAmr");
		presentreading.val(""); */
		var EmptyArray = new Array();
		var ddlMulti = $('#propertyNameAmr').data('kendoMultiSelect');
		ddlMulti.value(EmptyArray);  
	  
	  });

//***********************************************************************************************
$("#prepaidGrid").on("click", ".k-grid-PrePaid_CamBill_Xml_Generation", function(e) {

	  var bbDialog=$( "#xmlGenerationPopup" );
	bbDialog.kendoWindow({
		width : "auto",
		height : "auto",
		modal : true,
		draggable : true,
		position : {
			top : 100
		},
	      title: "Calculate Charges"
	  }).data("kendoWindow").center().open();

	bbDialog.kendoWindow("open");
	  
	  });
//***********************************************************************************************

$("#chrgesCalculationDatadivform").submit(function(e) {
	e.preventDefault();
});

function generateBill() {
	$("#getData").hide();
	$("#placeholder").show();
	/* var propertyID = $("#propertyId").val();
	alert(propertyID); */
	var fromDate = $("#fromDate").val();
	//alert(fromDate);
	var toDate = $("#toDate").val();
	//var consumerID = $("#consumerId").val();
	//alert(consumerID);
	var serviceId = $("#serviceId").val();
	
	 var propertyId = $("input[name=propertyNameAmr]").data(
			"kendoMultiSelect").value();
	var blocId = $("#blockNamePre").val();
	var blockName = $("input[name=blockNamePre]").data("kendoDropDownList")
			.text(); 
	
//alert(propertyId);
/* 	if(propertyId == ""){
		alert("Plase Select Property Name");
		return false;
	} 
	if(serviceId == ""){
		alert("Plase Select Service Name");
		return false;
	}
*/
	if(fromDate == ""){
		alert("Plase Select From Date");
		return false;
	}	
	if(toDate == ""){
		alert("Plase Select To Date");
		return false;
	}
	
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
	// alert(accountNo);
	
	//alert(toDate);
alert("propertyId="+propertyId);
	$.ajax({
		url : "./serviceChargeCalcu/calculateCharges",
		type : 'GET',
		dataType : "text",
		data : {
			 blocId : blocId,
			blockName : blockName,
			propertyId : "" + propertyId + "", 
			fromDate : fromDate,
			toDate : toDate,
			serviceId : serviceId
		},
		//contentType : "application/json; charset=utf-8",
		success : function(response) {
			$("#getData").show();
			$("#placeholder").hide();
			alert(response);
			filterclose();
		
			$('#prepaidGrid').data('kendoGrid').refresh();
			$('#prepaidGrid').data('kendoGrid').dataSource.read();
		},

	});
}

function generateXmlFile() {
	$("#getData").hide();
	$("#placeholder").show();
	var fromDate = $("#fromDateXML").val();
	var toDate   = $("#toDateXML").val();
	if(fromDate==""){
		alert("Enter From Date");
		return false;
	}
	if(toDate==""){
		alert("Enter To Date");
		return false;
	}
				
	window.open("./getXmlFile/exportToXML?fromDate="+fromDate+"&toDate="+toDate);

	var bbDialog = $("#xmlGenerationPopup");
	bbDialog.kendoWindow({
		width : "auto",
		height : "auto",
		modal : true,
		draggable : true,
		position : {
			top : 100
		},
		title : "Post All Bill"
	}).data("kendoWindow").center().close();

	bbDialog.kendoWindow("close");
/* 	
	$.ajax({
		url : "./getXmlFile/exportToXML",
		type : 'GET',
		dataType : "text",
		data : {
			fromDate : fromDate,
			toDate : toDate,
		},
		success : function(response) {
			$("#getData").show();
			$("#placeholder").hide();
			alert(response);
			filterclose();
		
			$('#prepaidGrid').data('kendoGrid').refresh();
			$('#prepaidGrid').data('kendoGrid').dataSource.read();
		},

	}); */
}
 
function filterclose() {

	var bbDialog = $("#chrgesCalculationPopUp");

	bbDialog.kendoWindow({
		width : "auto",
		height : "auto",
		modal : true,
		draggable : true,
		position : {
			top : 100
		},
		title : "Approve Bills"
	}).data("kendoWindow").center().close();
	bbDialog.kendoWindow("close");
}




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

				$("#fromDate").kendoDatePicker({
					// defines the start view
					//start : "year",
					// defines when the calendar should return date
					//depth : "year",
					value : new Date(),
					// display month and year in the input
					format : "dd/MM/yyyy"
				});
				$("#toDate").kendoDatePicker({
					// defines the start view
					//start : "year",
					// defines when the calendar should return date
					//depth : "year",
					value : new Date(),
					// display month and year in the input
					format : "dd/MM/yyyy"
				});
				
				$("#fromDateXML").kendoDatePicker({
					// defines the start view
					//start : "year",
					// defines when the calendar should return date
					//depth : "year",
					value : new Date(),
					// display month and year in the input
					format : "dd/MM/yyyy"
				});
				
				$("#toDateXML").kendoDatePicker({
					// defines the start view
					//start : "year",
					// defines when the calendar should return date
					//depth : "year",
					value : new Date(),
					// display month and year in the input
					format : "dd/MM/yyyy"
				});
				
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
						url : "./prepaidBill/getPropertyNo?blockId=" + blockId,
					}
				}
			}
		});

	/* 	var datepicker = $("#presentdateAmr").data("kendoDatePicker");
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
       // alert("property number")
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
</script>
<style>
.k-widget k-window{
width: 420px;
}
</style>