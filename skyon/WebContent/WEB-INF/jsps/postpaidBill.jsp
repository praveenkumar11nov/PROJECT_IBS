
<%@include file="/common/taglibs.jsp"%>

<script type="text/javascript"
	src="<c:url value='/resources/jquery-validate.js'/>"></script>

<script src="./resources/bootbox/bootbox.min.js"></script>
<link	href="<c:url value='/resources/twitter-bootstrap-wizard/bootstrap/css/bootstrap.min.css'/>"	rel="stylesheet" />


 <c:url value="/bills/readTowerNames" var="towerNames" />
 <c:url value="/postpaidBill/postPaidELBillReadUrl" var="prePaidELBillReadUrl"></c:url>
 <c:url value="/prpaidBill/prePaiddestroyUrl" var="prePaiddestroyUrl"></c:url>
 
 <c:url value="/prePaidBill/getPropertyNamesUrl"
	var="getPropertyNamesUrl" />
 <c:url value="/prePaidPayment/getMeterNumberUrl" var="getMeterNumberUrl" />
 
<kendo:grid name="prepaidGrid" resizable="true" change="prepaidDataChange"
	pageable="true" selectable="true"  sortable="true" scrollable="true"
	groupable="true" >
	<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10"></kendo:grid-pageable>
	<kendo:grid-filterable extra="false">
		<kendo:grid-filterable-operators>
			<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains"/>
		</kendo:grid-filterable-operators>

	</kendo:grid-filterable>
	<kendo:grid-editable mode="popup"
		confirmation="Are you sure you want to remove this Bill Detail?" />
	<kendo:grid-toolbar>
		<%--<kendo:grid-toolbarItem name="PrepaidBillGenerate" text="Generate Bill" />		
		 <kendo:grid-toolbarItem template="<input id='monthpicker' style='width:162px' />" />
		<kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=searchByMonth() title='Search'>Search</a>" /> 
		<kendo:grid-toolbarItem text="Clear_Filter" />--%>
		<%-- 	<kendo:grid-toolbarItem name="PrepaidPrintBill" text="Print Bill" />	
			<kendo:grid-toolbarItem name="downloadAllBills" text="Send All Mail" /> 
			<kendo:grid-toolbarItem name="ManualBilling" text="Generate Manual Bill" ></kendo:grid-toolbarItem>
			
			<kendo:grid-toolbarItem name="exportingPrepaidBillDatasToExcel" text="Export To Excel" ></kendo:grid-toolbarItem> --%>
			<kendo:grid-toolbarItem name="uploadingExcelFileDataToGenBill" text="Upload Excel File" ></kendo:grid-toolbarItem>
			
	</kendo:grid-toolbar>

	<kendo:grid-columns>
		<kendo:grid-column title="Bill ID" field="preBillId"
			width="100px" hidden="true" />

		 <kendo:grid-column title="&nbsp;" width="80px">
			<kendo:grid-column-command>
				<kendo:grid-column-commandItem name="View Bill" click="exportBill" />
			</kendo:grid-column-command>
		</kendo:grid-column> 

		<kendo:grid-column title="Meter&nbsp;No" field="mtrNo" filterable="true" width="85px">
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function ledgerTypeFilter(element) {
							element.kendoAutoComplete({
										placeholder : "Enter Account Number",
										dataSource : {
											transport : {
												read : "${commonFilterForAccountNumbersUrl}"
											}
										}
									});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>

		<%-- <kendo:grid-column title="Account&nbsp;No" field="accountId"
			editor="accountNumberEditor" filterable="true" width="100px"
			hidden="true" /> --%>

		<kendo:grid-column title="Person&nbsp;Name" field="personName"
			width="115px" filterable="true">
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function personNameFilter(element) {
							element
									.kendoAutoComplete({
										autoBind : false,
										dataTextField : "personName",
										dataValueField : "personName",
										placeholder : "Enter name",
										headerTemplate : '<div class="dropdown-header">'
												+ '<span class="k-widget k-header">Photo</span>'
												+ '<span class="k-widget k-header">Contact info</span>'
												+ '</div>',
										template : '<table><tr><td rowspan=2><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.personId#'/>" width=40 height=55 alt=\"No Image to Display\" /></span></td>'
												+ '<td align="left"><span class="k-state-default"><b>#: data.personName #</b></span><br>'
												+ '<span class="k-state-default"><i>#: data.personStyle #</i></span><br>'
												+ '<span class="k-state-default"><i>#: data.personType #</i></span></td></tr></table>',
										dataSource : {
											transport : {
												read : "${personNamesFilterUrl}"
											}
										}
									});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>

		<kendo:grid-column title="Property&nbsp;No" field="propertyNo"
			filterable="true" width="90px" />

		<kendo:grid-column title="Consumption&nbsp;Amount" field="netAmount"
			format="{0:#.00}" width="125px" filterable="true" />
		<kendo:grid-column title="Gross&nbsp;Consumption&nbsp;Amount" field="grossAmount"
			format="{0:#.00}" width="125px" filterable="true" />	

		<kendo:grid-column title="Bill&nbsp;No" field="billNo" width="70px"
			filterable="true" >
			<%-- <kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function postTypeFilter(element) {
							element
									.kendoAutoComplete({
										placeholder : "Enter Bill Number",
										dataSource : {
											transport : {
												read : "${commonFilterForBillUrl}/billNo"
											}
										}
									});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable> --%>
		</kendo:grid-column>
        <kendo:grid-column title="Bill&nbsp;Month" field="billMonth" format="{0:MMM,yyyy}" width="80px" >
		</kendo:grid-column>
		<kendo:grid-column title="Period&nbsp;From" field="fromDate"
			format="{0:dd/MM/yyyy}" width="100px" hidden="true" ></kendo:grid-column>

		<kendo:grid-column title="Period&nbsp;to" field="toDate"
			format="{0:dd/MM/yyyy}" width="100px" hidden="true" />
		<kendo:grid-column title="Previous Balance" field="previousBal" width="105px">
			
		</kendo:grid-column>
        
		<kendo:grid-column title="Closing Balance" field="closingBal"
			width="100px">
			
		</kendo:grid-column>
         <kendo:grid-column title="Mail Send Status" field="mailStatus" width="100px">
		</kendo:grid-column>
		

		<kendo:grid-column title="&nbsp;" width="100px">
			<kendo:grid-column-command>
			<kendo:grid-column-commandItem name="destroy" />
			</kendo:grid-column-command>
		</kendo:grid-column>
		


	</kendo:grid-columns>

	<kendo:dataSource pageSize="20"  requestEnd="onRequestEnd" requestStart="onRequestStart">
		<kendo:dataSource-transport >
			
			<kendo:dataSource-transport-read url="${prePaidELBillReadUrl}"
				dataType="json" type="POST" contentType="application/json" />
		   <kendo:dataSource-transport-destroy url="${prePaiddestroyUrl}" dataType="json" type="GET" contentType="application/json" /> 
		</kendo:dataSource-transport>

		<kendo:dataSource-schema >
			<kendo:dataSource-schema-model id="preBillId">
				<kendo:dataSource-schema-model-fields>

					<kendo:dataSource-schema-model-field name="preBillId" type="number" />

					<kendo:dataSource-schema-model-field name="mtrNo"
						type="string" />

					<kendo:dataSource-schema-model-field name="personName"
						type="string" />

					
					<kendo:dataSource-schema-model-field name="propertyNo"
						type="string" />

				
					<kendo:dataSource-schema-model-field name="fromDate" type="string" />
					<kendo:dataSource-schema-model-field name="toDate" type="string" />

					<kendo:dataSource-schema-model-field name="billMonth" type="string" />

					<kendo:dataSource-schema-model-field name="netAmount" type="number" />

					<kendo:dataSource-schema-model-field name="billNo" type="string" />

					<kendo:dataSource-schema-model-field name="previousBal" type="number" />

					<kendo:dataSource-schema-model-field name="closingBal"
						type="number" />
					<kendo:dataSource-schema-model-field name="grossAmount"
						type="number" />	
                    <kendo:dataSource-schema-model-field name="mailStatus" type="string" />
				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>

	</kendo:dataSource>
</kendo:grid>
<div id="prepaidBillingDiv" style="display: none;">
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
<!-- ------------------------------------upload excel file pop win field--------------- -->

<div id="uploadExcelFile" style="display: none;">
<form:form action="./uploadExcelFileToGenBill" modelAttribute="khataChangeEntity"
				enctype="multipart/form-data" method="POST" id="khataChangeEntityId"
				role="form" class="form-horizontal form-validate-jquery">
				<fieldset class="content-group">
				

					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Upload File <font color="red">*</font></label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input
									type="file" name="xlsheet" id="xlsheetId" class="file-styled"
									accept=".xls,.xlsx">
								<!--  //onchange="readURL1(this)" need to write error handling code...-->

							</div>
						</div>

						<div class="col-md-6" style="padding-top: 27px;">

							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type="submit" class="btn btn-primary" id="addOption">
								Submit<i class="icon-arrow-right14 position-right"></i>
								<!-- onclick="return finalSubmit(0) -->
							</button>

						</div>

					</div>

				</fieldset>
			</form:form>

</div>


<!-- ----------------------------------------------------------------------------------------------------------------------------------->

<div id="printBillingDiv" style="display: none;">
	<form id="printBillingForm" data-role="validator"
		novalidate="novalidate">
		<table style="height: 190px;">


			<tr>
				<td>Print Bill</td>
				<td><input id="typePrint" name="typePrint" required="required"
					validationMessage="Select Service Type" onchange="billTOPrint()" />
				</td>
			</tr>
			
			  <tr id="accPrint" style="display: none;"> 
	             <td>Property No</td>
				<td><input id="propertyId" name="propertyId"
					validationMessage="Select propertyId." /></td>
			 </tr> 
         

			 <!-- <tr>
				<td>Service Type</td>
				<td><input id="serviceTypePrint" name="serviceTypePrint"
					validationMessage="Select Service Type" /> onchange="getPropertyNo()"</td>
			</tr>  -->
			
			
			<%-- <tr id="fMonthPrint" style="display: none;">
				<td>From Month</td>
				<td><kendo:datePicker format="MMM yyyy  "
						name="fromdateprintBill" id="fromdateprintBill" value="${month}"
						start="year" depth="year">
					</kendo:datePicker>
				<td></td>
			</tr>  --%>

			<tr>
				<td>Month</td>
				<td><kendo:datePicker format="MMM yyyy  "
						name="presentdateprintBill" id="presentdateprintBill"
						required="required" value="${month}" start="year"
						class="validate[required]" validationMessage="Date is Required"
						depth="year">
					</kendo:datePicker>
				<td></td>
			</tr>


			<tr>
				<td class="left" align="center" colspan="4">
					<button type="submit" id="printBill" class="k-button"
						style="padding-left: 10px">Print Bill</button> <span
					id=printplaceholder style="display: none;"><img
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

<!-- ---------------------------------------------------------------------------------------------------------->

<div id="downloadAllBillsDiv" style="display: none;">
	<form id="downloadAllBillsForm" data-role="validator"
		novalidate="novalidate">
		<table style="height: 190px;">
			<tr id="blockNameCamid">
				<td>Block Name</td>
				<td><input id="blockNameCam"  name="blockNameCam"
					required="required" 
					validationMessage="Select Block Name" onchange="getPropertyNoCam()" /> <!-- onchange="getPropertyNo()" --></td>
			</tr>
			<tr id="propertyTypeCamid">
				<td>Property Type</td>
				<td><input id="propertyTypeCam"  name="propertyTypeCam"
					required="required" onchange="selectallpropCam()"
					validationMessage="Select Type" /> <!-- onchange="getPropertyNo()" --></td>
			</tr>
			<tr id="propmultiselectCam">
				<td id="propmultiselectCam1" >Property Number</td>
				<td id="propmultiselectCam2"><kendo:multiSelect
						id="propertyNameCam" style="display: none;" name="propertyNameCam" required="required"
						validationMessage="Select Property No"></kendo:multiSelect></td>
			</tr>
			<tr>
				<td>Bill Month</td>
				<td><kendo:datePicker format="MMM yyyy"
						name="presentdateDownload" value="${month}" start="year"
						depth="year" id="presentdateDownload">
					</kendo:datePicker>
				<td></td>
			</tr>
			<tr>
				<td class="left" align="center" colspan="4">
					<button type="button" id="downlodbutton" onclick="return downloadAllBillsAjax()" class="k-button"
						style="padding-left: 10px">Send Mail</button>
				</td>
			</tr>

		</table>
	</form>
</div>


 <div id="meterWizardPopUp" hidden="true" style="overflow: hidden; height: 328px;">

<div class='container'>
	<div class="span12" style=" width: 400px;">
		<section id="wizard">
<form:form id="commentForm"  action="" class="form-horizontal" commandName="prepaidBillDetails" modelAttribute="prepaidBillDetails" autocomplete="off">
  <div id="rootwizard">
					<div class="tab-content" style="overflow: hidden;width: 780px;">
							<table>
									<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="propertyId">Property&nbsp;Number&nbsp;*</label>
											<div class="controls">
												<select id="propertyId1" name="propertyId" data-required-msg="Select Property" required="required"></select>
											</div>
										</div>
									</td>
								<!-- </tr>
								<tr> -->
									<td>
										<div class="control-group">
											<label class="control-label" for="meterNo">Meter&nbsp;Number&nbsp;*</label>
											<div class="controls">
												<%-- <form:input type="text" id="personId" path="personId" class="required personId"/> --%>
												<select id="meterNum" name="consumerId" data-required-msg="Select Meter Number" required="required"></select>
											</div>
										</div>
									</td>
									</tr>
								<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="previousBal">Previous&nbsp;Balance&nbsp;*</label>
											<div class="controls">
												<form:input type="text" id="previousBal"   path="previousBal" cssStyle="width: 159px;" required="required"/>
											</div>
										</div>
									</td>
								<!-- </tr>
								<tr> -->
									<td>
										<div class="control-group">
											<label class="control-label" for="rechargedAmt">Recharged&nbsp;Amount&nbsp;*</label>
											<div class="controls">
												<form:input type="text" id="rechargedAmt"   path="rechargedAmt" cssStyle="width: 159px;" required="required"/>
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="closingBal">Closing&nbsp;Balance&nbsp;*</label>
											<div class="controls">
												<form:input type="text" id="closingBal"   path="closingBal" cssStyle="width: 159px;" required="required"/>
											</div>
										</div>
									</td>
								<!-- </tr>
								<tr> -->
									<td>
										<div class="control-group">
											<label class="control-label" for="ebReading">EB&nbsp;Reading&nbsp;</label>
											<div class="controls">
												<form:input type="number" id="ebReading" path="ebReading" cssStyle="width: 175px; height: 28px;" required="required"/>
											</div>
										</div>
									</td>
								</tr>
								
								<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="dgReading">DG&nbsp;Reading&nbsp;</label>
											<div class="controls">
												<form:input type="number" id="dgReading" path="dgReading" cssStyle="width: 175px; height: 28px;" required="required"/>
											</div>
										</div>
									</td>
								<!-- </tr>
								<tr> -->
									<td>
										<div class="control-group">
											<label class="control-label" for="camCharges">CAM&nbsp;Charges&nbsp;</label>
											<div class="controls">
												<form:input type="number" id="camCharges" path="camCharges" cssStyle="width: 175px; height: 28px;" required="required"/>
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="fromDate">From&nbsp;Date&nbsp;</label>
											<div class="controls">
												<input type="text" id="fromDate" name="fromDate" cssStyle="width: 176px;" required="required">
											</div>
										</div>
									</td>
								<!-- </tr>	
								<tr> -->
									<td>
										<div class="control-group">
											<label class="control-label" for="toDate">To&nbsp;Date&nbsp;</label>
											<div class="controls">
												<input type="text" id="toDate" name="toDate" cssStyle="width: 176px;" required="required">
											</div>
										</div>
									</td>
								</tr>		
							</table>
						 
						    <ul class="pager wizard">
							<li><a style="background-color: rgb(236, 236, 236);"><input type="button" value="Generate Bill" id="saveMeterData"><span
					id=billplaceholder style="display: none;"><img
						src="./resources/images/loadingimg.GIF"
						style="vertical-align: middle; margin-left: 50px" /> &nbsp;&nbsp;
						<img src="./resources/images/loading.GIF" alt="loading"
						style="vertical-align: middle margin-left: 50px" height="20px"
						width="200px; " /></span></a></li>
							<li><a style="background-color: rgb(236, 236, 236);"><input type="button" value="Close" onclick="return closePop()"></a></li>
							</ul>
					
					</div>					
				</div>
</form:form> 
		</section>
	</div>
</div>
</div> 
<div id="alertsBox" title="Alert"></div>


<script>
var SelectedRowId = "";
var billNo = "";

function prepaidDataChange(arg) {
	var gview = $("#prepaidGrid").data("kendoGrid");
	var selectedItem = gview.dataItem(gview.select());
	SelectedRowId = selectedItem.preBillId;
	billNo = selectedItem.billNo;
	
	this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
	$(".k-master-row.k-state-selected").show();
}
$("#prepaidGrid").on("click",".k-grid-PrepaidBillGenerate",function(e){
	   // window.open("./bill/getbilltablePDF");
		 var bbDialog = $("#prepaidBillingDiv");
		bbDialog.kendoWindow({
			width : "auto",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Generate  Bill"
		}).data("kendoWindow").center().open();

		bbDialog.kendoWindow("open");

		var dropdownlist2 = $("#blockNamePre").data("kendoDropDownList");
		dropdownlist2.value("");
		var dropdownlist1 = $("#propertyType").data("kendoDropDownList");
		dropdownlist1.value("");

		var presentreading = $("#presentdateAmr");
		presentreading.val("");
		var EmptyArray = new Array();
		var ddlMulti = $('#propertyNameAmr').data('kendoMultiSelect');
		ddlMulti.value(EmptyArray);  
	});
	 function openAmrBill() {
			
		}
		
	 function generateAmrBill() {
			$("#amrCalculate").hide();
			$("#amrplaceholder").show();
			var propertyId = $("input[name=propertyNameAmr]").data("kendoMultiSelect").value();
			var blocId = $("#blockNamePre").val();
			var blockName = $("input[name=blockNamePre]").data("kendoDropDownList").text();
			var presentdate = $("#presentdateAmr").val();
			//alert(presentdate);
			//alert(propertyId);
			
			$.ajax({
				//url : "./bill/generateAmrBill",
				url : "./prepaid/generateBillNew",
				type : "GET",
				dataType : "text",
				data : {
					blocId : blocId,
					blockName : blockName,
					propertyId : "" + propertyId + "",
					presentdate : presentdate,
				},
				success : function(response) {
					$("#amrCalculate").show();
					$("#amrplaceholder").hide();
					alert(response);
					closeAmr();
					$('#prepaidGrid').data('kendoGrid').refresh();
					$('#prepaidGrid').data('kendoGrid').dataSource.read();
					/* var combobox = $('#generateDropDown').data("kendoComboBox");
					if (combobox != null) {
						combobox.value("");
					} */

				}
			});
		}

		function closeAmr() {
			var bbDialog = $("#prepaidBillingDiv");
			bbDialog.kendoWindow({
				width : "auto",
				height : "auto",
				modal : true,
				draggable : true,
				position : {
					top : 100
				},
				title : "Generate  Bill"
			}).data("kendoWindow").center().close();

			bbDialog.kendoWindow("close");
		}
	 var amrcalculatevalidator = $("#prepaidBillingForm").kendoValidator().data(
		"kendoValidator");
	$("#amrCalculate").on("click", function() {

	if (amrcalculatevalidator.validate()) {

		generateAmrBill();
	}
	});
	 $("#prepaidBillingForm").submit(function(e) {
			e.preventDefault();
		});
	 
	 $(document)
		.ready(
				function() {

					/* 
					$("#monthpicker").kendoDatePicker({
						// defines the start view
						start : "year",
						// defines when the calendar should return date
						depth : "year",
						value : new Date(),
						// display month and year in the input
						format : "MMMM yyyy"
					});
	 */
					

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
							url : "./prepaidDataprop/getPropertyNo?blockId=" + blockId,
						}
					}
				}
			});

			var datepicker = $("#presentdateAmr").data("kendoDatePicker");
			datepicker.max(new Date());
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
	 
	 function exportBill() {
			
				window.open("./postpaidBill/getbilltablePDF/" + SelectedRowId);

		}
	 
/* 	 ----------------------------------------------------------------------------------------- */
$("#prepaidGrid").on("click", ".k-grid-ManualBilling", function(e) {

		//var result=securityCheckForActionsForStatus("./Accounts/ServiceOnBoard/createButton"); 
		/* $("#propertyId").data("kendoDropDownList").value("");
		$("#meterNumber").val(""); */
		var todcal = $("#meterWizardPopUp");
		todcal.kendoWindow({
			width : 'auto',
			height : 'auto',
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Generate Manual Bill"
		}).data("kendoWindow").center().open();

		todcal.kendoWindow("open");

	});

  $("#saveMeterData").on("click",function(){
	$("#billplaceholder").show();
	$("#saveMeterData").hide();
	var propertyId = $("#propertyId1").val(); 
	  //  alert(propertyId);
		var meternumber = $("#meterNum").val();
		//alert(meternumber);
		var ebReading = $("#ebReading").val();
		//alert(ebReading);
		var dgReading = $("#dgReading").val();
		//alert(dgReading);
		var previousBal = $("#previousBal").val();
		//alert(previousBal);
		var closingBal = $("#closingBal").val();
		//alert(closingBal);
		var rechargeamt = $("#rechargedAmt").val();
		//alert(rechargeamt);
		var camCharges = $("#camCharges").val();
		//alert(camCharges);
		var fromDate = $("#fromDate").val();
		//alert(fromDate);
		var toDate = $("#toDate").val();
		//alert(toDate);
		if(propertyId==null){
			alert("Please select Property Number!");
			return false;
		}else if(meternumber==null){
			alert("Please select Meter Number!");
			return false;
		}else if(ebReading==""){
			alert("Please select EB Reading!");
			return false;
		}else if(dgReading==""){
			alert("Please select EB Reading!");
			return false;
		}else if(previousBal==""){
			alert("Please select EB Reading!");
			return false;
		}else if(closingBal==""){
			alert("Please select EB Reading!");
			return false;
		}else if(rechargeamt==""){
			alert("Please select EB Reading!");
			return false;
		}else if(camCharges==""){
			alert("Please select EB Reading!");
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
		   /* 
alert("propertyId="+propertyId+",meternumber="+meternumber+",ebReading="+ebReading+",dgReading="+dgReading+
		",previousBal="+previousBal+",closingBal="+closingBal+",rechargeamt="+rechargeamt+",rechargeamt="+rechargeamt+
		",camCharges="+camCharges+",fromDate="+fromDate+",toDate="+toDate);
 */

		$.ajax({
			url : "./prepaid/generateManualBillNew",
			type : "GET",
			dataType : "text",
			data : {
				propertyId:propertyId,
				meternumber:meternumber,
				ebReading:ebReading,
				dgReading:dgReading,
				previousBal:previousBal,
				closingBal:closingBal,
				rechargeamt:rechargeamt,
				camCharges:camCharges,
				fromDate:fromDate,
				toDate:toDate
				
			},
			success : function(response) {
				$("#billplaceholder").hide();
				$("#saveMeterData").show();
				closebill();
				alert(response);
				
				$('#prepaidGrid').data('kendoGrid').refresh();
				$('#prepaidGrid').data('kendoGrid').dataSource.read();
			}
		});
	});
  function closePop(){
	  closebill();
  }
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

	/* 	 ----------------------------------------------------------------------------------------- */

	function onRequestEnd(e) {

		if (typeof e.response != 'undefined') {
			// alert(e.response);
			if (e.response.status == "FAIL") {
				errorInfo = "";
				for (var i = 0; i < e.response.result.length; i++) {
					errorInfo += (i + 1) + ". "
							+ e.response.result[i].defaultMessage + "<br>";
				}

				if (e.type == "destroy") {
					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Delete the Bill\n\n : " + errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
				}
				var grid = $("#prepaidGrid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			} else if (e.type == "destroy") {

				$("#alertsBox").html("");
				$("#alertsBox").html("Bill deleted successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});

				var grid = $("#prepaidGrid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			}

		}

	}
	function onRequestStart(e) {
		$('.k-grid-update').hide();
		$('.k-edit-buttons')
				.append(
						'<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
		$('.k-grid-cancel').hide();

	}

	/* ---------------------------------------------------------------------------------------------- */

	$("#prepaidGrid").on("click", ".k-grid-PrepaidPrintBill", function(e) {
		var bbDialog = $("#printBillingDiv");
		bbDialog.kendoWindow({
			width : "auto",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Print  Bill"
		}).data("kendoWindow").center().open();

		bbDialog.kendoWindow("open");

		var dropdownlist2 = $("#blockNameAmr").data("kendoDropDownList");
		dropdownlist2.value("");
		var dropdownlist1 = $("#propertyType").data("kendoDropDownList");
		dropdownlist1.value("");

		var presentreading = $("#presentdateAmr");
		presentreading.val("");
		var EmptyArray = new Array();
		var ddlMulti = $('#propertyNameAmr').data('kendoMultiSelect');
		ddlMulti.value(EmptyArray);
	});

	var printbillvalidator = $("#printBillingForm").kendoValidator().data(
			"kendoValidator");
	$("#printBill").on("click", function() {

		if (printbillvalidator.validate()) {

			printAllBills();
		}
	});

	function printAllBills() {
		$("#printBill").hide();
		$("#printplaceholder").show();

		/* var serviceType = $("input[name=serviceTypePrint]").data(
				"kendoDropDownList").text(); */

		var presentdate = $("#presentdateprintBill").val();
		var propertyNo = $("#propertyId").val();

		var fromMonth = $("#fromdateprintBill").val();
		var typetoPrint = $("#typePrint").val();
		if (typetoPrint == 'All') {
			window.open("./bill/printAllBill/prepaidBillGeneration" + "/"
					+ presentdate);

		} else {

			window.open("./bill/propertyNoWise/prepaidBillGeneration" + "/"
					+ propertyNo + "/" + presentdate);

		}
	}

	/* //upload excel file
	function uploadExcelFile() {

		$.ajax({
			
			url : "./uploadExcelFileToGenBill",
			type : "GET",
			dataType : "text",
			data : {
				
			},
			success : function(response) {
				alert(response);
			}
		});
			
		
	} */
	
	
	function billTOPrint() {
		var typePrint = $("#typePrint").val();
		if (typePrint != 'All') {
			$("#accPrint").show();
			//$("#fMonthPrint").show();
		} else {
			$("#accPrint").hide();
			//$("#fMonthPrint").hide();
		}
	}
	

	$(document).ready(function() {
		
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
		$("#propertyId1").kendoComboBox({
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

		$("#meterNum").kendoComboBox({
			autoBind : false,
			cascadeFrom : "propertyId1",
			dataTextField : "consumerId",
			dataValueField : "consumerId",
			dataSource : {
				transport : {
					read : "${getMeterNumberUrl}"
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

		$("#typePrint").kendoDropDownList({
			dataTextField : "text",
			dataValueField : "value",
			optionLabel : {
				text : "Select",
				value : "",
			},
			dataSource : propertyTypeList
		}).data("kendoDropDownList");

		$("#blockNameCam").kendoDropDownList({
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

		$("#propertyTypeCam").kendoDropDownList({
			dataTextField : "text",
			dataValueField : "value",
			optionLabel : {
				text : "Select",
				value : "",
			},
			dataSource : propertyTypeList
		}).data("kendoDropDownList");
	});

	/*===============================================================================================================*/

	$("#prepaidGrid").on("click", ".k-grid-downloadAllBills", function(e) {
		var dropdownlist1 = $("#blockNameCam").data("kendoDropDownList");
		dropdownlist1.value("");
		var dropdownlist3 = $("#propertyTypeCam").data("kendoDropDownList");
		dropdownlist3.value("");

		var EmptyArray = new Array();
		var ddlMulti = $('#propertyNameCam').data('kendoMultiSelect');
		ddlMulti.value(EmptyArray);
		var bbDialog = $("#downloadAllBillsDiv");
		bbDialog.kendoWindow({
			width : "auto",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Send All Mail"
		}).data("kendoWindow").center().open();

		bbDialog.kendoWindow("open");

		var presentreading = $("#presentdateDownload");
		presentreading.val("");

	});

	var presentreading = $("#presentdateDownload");
	presentreading.val("");

	function downloadAllBillsAjax() {

		//alert(" ");
		var selectedMonth = $("#presentdateDownload").val();
		alert(selectedMonth);
		var blockSendId = $("#blockNameCam").val();
		alert(blockSendId);
		var propertyId = $("input[name=propertyNameCam]").data(
				"kendoMultiSelect").value();
		alert(propertyId);
		if (selectedMonth == "") {
			alert("Select bill month");
			return false;
		} else {
			$('.k-button').hide();
			$
					.ajax({
						type : "GET",
						url : "./prepaidBillGeneration/sendAllBillsMonthWise",
						dataType : "text",
						data : {
							selectedMonth : selectedMonth,
							propertyId : "" + propertyId + "",
							blockSendId : blockSendId

						},
						success : function(response) {
							var resMessage = "";
							$('.k-button').show();
							//alert(response);
							if (response == "success") {
								resMessage = "Mail sent successfully";
							} else {
								resMessage = "Oops! Sorry, an error has occured. Internal Server Error!";
							}

							$("#alertsBox").html("");
							$("#alertsBox").html(resMessage);
							$("#alertsBox").dialog({
								modal : true,
								buttons : {
									"Close" : function() {
										$(this).dialog("close");
									}
								}
							});
							var grid = $("#prepaidGrid").data("kendoGrid");
							grid.dataSource.read();
							grid.refresh();
						}
					});
			closepayslip();
		}
	}

	function closepayslip() {
		var btDialog = $("#downloadAllBillsDiv");
		btDialog.kendoWindow({
			width : "300",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Send All Payslips"
		}).data("kendoWindow").center().close();

		btDialog.kendoWindow("close");

	}
	
	function closebill() {
		var btDialog = $("#meterWizardPopUp");
		btDialog.kendoWindow({
			width : "300",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Send All Payslips"
		}).data("kendoWindow").center().close();

		btDialog.kendoWindow("close");

	}

	/* ------------------------------------------------Export to excel-------------------------------------------------------- */
	
	
	 $("#prepaidGrid").on("click",".k-grid-exportingPrepaidBillDatasToExcel",function(e){
		   //var fromDate = $('#fromMonthpicker').val();
	 	    //alert(fromDate);
	 	    //var toDate = $('#toMonthpicker').val();
		 window.open("./prepaidBillGeneration/exportPrepaidBillDatasToExcel");
	 });
	
	
	/* --------------------------------------------Upload Excel File -----------------------------------------------------*/
	$("#prepaidGrid").on("click", ".k-grid-uploadingExcelFileDataToGenBill", function(e) {
		var bbDialog = $("#uploadExcelFile");
		bbDialog.kendoWindow({
			width : "500px",
			height : "170px",
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Upload Excel File"
		}).data("kendoWindow").center().open();

		bbDialog.kendoWindow("open");

		
	});
	
	
	
	
	
	/* --------------------send mail--------------------------------- */
	function getPropertyNoCam() {
		var blockId = $('#blockNameCam').val();
		var blockName = $("input[name=blockNameCam]").data("kendoDropDownList")
				.text();
		$('#propmultiselectCam1').remove();
		$('#propmultiselectCam2').remove();

		$('#propmultiselectCam')
				.append(
						"<td id='propmultiselectCam1'>Property Name </td><td id='propmultiselectCam2'><input id='propertyNameCam' name='propertyNameCam'/> </td>");
		$("#propertyNameCam")
				.kendoMultiSelect(
						{
							autoBind : false,
							dataValueField : "propertyId",
							dataTextField : "property_No",
							dataSource : {
								transport : {
									read : {
										url : "./prepaidBillingModule/getPropertyNo?blockId="
												+ blockId,
									}
								}
							}
						});

		//	var datepicker = $("#presentdateCam").data("kendoDatePicker");
		//datepicker.max(new Date());
		recurCam();
	}
	function selectallpropCam() {

		var mul = $("#propertyNameCam").text();
		var propertyType = $("input[name=propertyTypeCam]").data(
				"kendoDropDownList").text();
		var multiSelect = $("#propertyNameCam").data("kendoMultiSelect");

		if (propertyType == 'All') {
			if (multiSelect.dataSource.data().length == 0) {
				var dropdownlist1 = $("#propertyTypeCam").data(
						"kendoDropDownList");
				dropdownlist1.value("");

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
			//multiSelect.value(" ");
		}
	}
	function recurCam() {
		var multiSelect = $("#propertyNameCam").data("kendoMultiSelect");
		var selectedValues = "";
		var strComma = "";
		for (var i = 0; i < multiSelect.dataSource.data().length; i++) {
			var item = multiSelect.dataSource.data()[i];
			selectedValues += strComma + item.propertyId;
			strComma = ",";
		}
		multiSelect.value(selectedValues.split(","));
	}
	
	$(document).ready(function()
			{
		if("${msg}"== null || "${msg}"==""){}else{
		alert("${msg}");}
	});
	
	
</script>
<style>
.container {
    margin-right: -156px;
    margin-left: -47px;
    
}
</style>
