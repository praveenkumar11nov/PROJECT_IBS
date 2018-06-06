<%@include file="/common/taglibs.jsp"%>

<kendo:grid name="grid" resizable="true" pageable="true" 
	sortable="true" scrollable="true" groupable="true" >
	<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10"></kendo:grid-pageable>
	<kendo:grid-toolbar>

	   <kendo:grid-toolbarItem template="<input id='datepicker' style='width:162px' />" />
		<kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=searchByMonth() 
		                  title='Search'>Search</a>" />
		
		
	</kendo:grid-toolbar>
	
	<kendo:grid-editable mode="popup" confirmation="Are you sure you want to remove this Bill Detail?" />

	<kendo:grid-columns>
		<kendo:grid-column title="Meter_Number" field="meterNo" width="100px" />
		<kendo:grid-column title="Reading_Date" field="readingDate" width="100px" />
		<kendo:grid-column title="Created_Date" field="createdDate" width="100px" />
	</kendo:grid-columns>

	<kendo:dataSource>
	<%-- 
		<kendo:dataSource-transport>
			<kendo:dataSource-transport-read url="${readConsumptionBlankData}"
				dataType="json" type="POST" contentType="application/json" />
		</kendo:dataSource-transport>
 --%>
		<kendo:dataSource-schema >
			<kendo:dataSource-schema-model id="meterNo">
				<kendo:dataSource-schema-model-fields>

					<kendo:dataSource-schema-model-field name="meterNo" type="number" />

					<kendo:dataSource-schema-model-field name="readingDate" type="string" />

					<kendo:dataSource-schema-model-field name="createdDate" type="date"  />

				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>

	</kendo:dataSource>
</kendo:grid>

<script>
$(document).ready(function() {
$("#datepicker").kendoDatePicker({
	  // defines the start view
	  start: "date",
	  // defines when the calendar should return date
	  depth: "month",
	  value:new Date(),
	  // display month and year in the input
	  format: "dd/MM/yyyy"
	});
});


function searchByMonth() {
	
	var readingDate = $('#datepicker').val();
	//alert(readingDate);
	 $.ajax({
		type : "POST",
		url : "./readConsumptionBlankData",
		dataType : "json",
		data:{readingDate:readingDate},
		success : function(result) {
			var grid = $("#grid").getKendoGrid();
			var data = new kendo.data.DataSource();
			grid.dataSource.data(result);
			grid.refresh();
		}
	}); 
}

</script>
