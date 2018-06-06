<%@include file="/common/taglibs.jsp"%>

<c:url value="/accountStatementDetail" var="readUrl" />

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
<%-- 	<kendo:grid-toolbar>
		<kendo:grid-toolbarItem name="exportDataPDF" text="Export To PDF"></kendo:grid-toolbarItem>	
	</kendo:grid-toolbar> --%>
	<kendo:grid-columns>
		<kendo:grid-column title="&nbsp;" width="110px">
			<kendo:grid-column-command >
				<kendo:grid-column-commandItem name="Get PDF" click="exportToPdf" />
			</kendo:grid-column-command>
		</kendo:grid-column>
		
		<kendo:grid-column title="prePaid Id" field="prePaidId" width="100px" filterable="true" hidden="true" sortable="false" />
		
		<kendo:grid-column title="Meter Number" field="meterNo" width="100px" filterable="true"  />
		
		<kendo:grid-column title="Property No" field="propertyNo" width="70px" filterable="true"  />
		
		<kendo:grid-column title="Reading Date" field="readingDateTime" width="100px" filterable="false"  />
		
		<kendo:grid-column title="Consumtion" field="consumption" width="70px" filterable="false"  />
		
		<kendo:grid-column title="Total Recharge Amount" field="totalRechargeAmount" width="150px" filterable="false" />
		
		<kendo:grid-column title="Balance" field="balance" width="100px" filterable="false" />
		
		<kendo:grid-column title="EBUnits" field="ebUnits" width="80px" filterable="false" />
		
		<kendo:grid-column title="DGUnits" field="dgUnits" width="100px" filterable="false"/>
		
		<kendo:grid-column title="Cum FixChgDg" field="cumFixedChargeDG" width="100px" filterable="false" />
		
		

 
	</kendo:grid-columns>

	<kendo:dataSource pageSize="20">
<kendo:dataSource-transport>
    <kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json" />
    
		</kendo:dataSource-transport>

		<kendo:dataSource-schema>
			<kendo:dataSource-schema-model id="prePaidId">
				<kendo:dataSource-schema-model-fields>
					<kendo:dataSource-schema-model-field name="prePaidId" type="number" />
					
					<kendo:dataSource-schema-model-field name="meterNo" type="string" />
					
					 <kendo:dataSource-schema-model-field name="propertyNo" type="String" />
					
					<kendo:dataSource-schema-model-field name="readingDateTime" type="String" />
				    
					<kendo:dataSource-schema-model-field name="consumption" type="string" />
					
					<kendo:dataSource-schema-model-field name="totalRechargeAmount" type="number" />
					
					<kendo:dataSource-schema-model-field name="balance" type="number" />
					
					<kendo:dataSource-schema-model-field name="ebUnits" type="number" />
					
					<kendo:dataSource-schema-model-field name="dgUnits"	type="number" />
					
					<kendo:dataSource-schema-model-field name="cumFixedChargeDG" type="number" />
				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>

	</kendo:dataSource>

</kendo:grid>

<div id="exportPdfDatadiv" style="display: none;">
	<form id="exportPdfDatadivform" data-role="validator" novalidate="novalidate">
	
		<table style="height: 263px;">
			<tr>
				<td>From Date</td>
				<td><input type="date" name="fromDateCam" id="fromMonthpicker" required="required"></td>
				<td></td>
			</tr>

			<tr>
				<td>To Date</td>
				<td><input type="date" name="toDateCam" id="toMonthpicker" required="required"></td>
				<td></td>
			</tr> 


			<tr>
				<td class="left" align="center" colspan="4" style="padding-left: 20px;">
					<button type="submit" id="exportPdf" class="k-button"
						style="padding-left: 8px">Export To PDF</button>
			</tr>
		</table>
	</form>
</div>


<script>

$("#exportPdfDatadivform").submit(function(e) {
	e.preventDefault();
});

$("#grid").on("click",".k-grid-exportDataPDF",function(e){

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

$("#fromMonthpicker").kendoDatePicker({
	value : new Date(),
	format : "dd/MM/yyyy"
});

$("#toMonthpicker").kendoDatePicker({
	value : new Date(),
	format : "dd/MM/yyyy"
});

function exportToPdf()
{
	var gridParameter = $("#grid").data("kendoGrid");
	var selectedAddressItem = gridParameter.dataItem(gridParameter.select());
	var prePaidId = selectedAddressItem.prePaidId;
	var meterNo = selectedAddressItem.meterNo;
	var propertyNo = selectedAddressItem.propertyNo;
	var readingDateTime = selectedAddressItem.readingDateTime;
	var consumption = selectedAddressItem.consumption;
	var totalRechargeAmount = selectedAddressItem.totalRechargeAmount;
	var balance = selectedAddressItem.balance;
	var ebUnits = selectedAddressItem.ebUnits;
	var dgUnits = selectedAddressItem.dgUnits;
	var cumFixedChargeDG = selectedAddressItem.cumFixedChargeDG;	
	
	/* String url = "./accountStatement/getPdfReport?prePaidId="+prePaidId+"&meterNo="+meterNo+"&propertyNo="+propertyNo+
			"&readingDateTime="+readingDateTime+"&consumption="+consumption+"&totalRechargeAmount="+totalRechargeAmount+
			"&balance="+balance+"&ebUnits="+ebUnits+"&dgUnits="+dgUnits+"&cumFixedChargeDG="+cumFixedChargeDG ; 
	alert(url);   */
	window.open("./accountStatement/getPdfReport?prePaidId="+prePaidId+"&meterNo="+meterNo+"&propertyNo="+propertyNo+"&readingDateTime="+readingDateTime+"&consumption="+consumption+"&totalRechargeAmount="+totalRechargeAmount+"&balance="+balance+"&ebUnits="+ebUnits+"&dgUnits="+dgUnits+"&cumFixedChargeDG="+cumFixedChargeDG);   

}
</script>