<%@include file="/common/taglibs.jsp"%>


<kendo:grid name="grid" pageable="true" resizable="true"  sortable="true" reorderable="true" selectable="false" scrollable="true" filterable="false" groupable="true">

    <kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" refresh="true" info="true" previousNext="true">
		<kendo:grid-pageable-messages itemsPerPage="Payments per page" empty="No payment to display" refresh="Refresh all the payments" 
			display="{0} - {1} of {2} New payments" first="Go to the first page of payments" last="Go to the last page of payments" next="Go to the next page of payments"
			previous="Go to the previous page of payments"/>
	</kendo:grid-pageable>
	<kendo:grid-filterable extra="false">
		<kendo:grid-filterable-operators>
			<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains"/>
			<kendo:grid-filterable-operators-date gt="Is after" lt="Is before"/>
		</kendo:grid-filterable-operators> 
	</kendo:grid-filterable>

	<kendo:grid-toolbar>
		      <kendo:grid-toolbarItem template="<label class='category-label'>&nbsp;&nbsp;Select&nbsp;Date&nbsp;:&nbsp;&nbsp;</label><input id='fromMonthpicker' style='width:110px'/>"/>
			  <kendo:grid-toolbarItem template="<a class='k-button'  href='\\#' onclick=searchByMonth() title='Search' style='width:90px'>Search</a>"/>
		      <kendo:grid-toolbarItem template="<a class='k-button'>Clear Filter</a>"/>
		      
	    </kendo:grid-toolbar>
	<kendo:grid-columns>
	    
	    <kendo:grid-column title="PaymentId" field="id" width="10px" hidden="true" filterable="false" sortable="false" />
	    
	    <kendo:grid-column title="Merchant&nbsp;Id" field="merchantId" width="100px" filterable="true" />
	  
	    <kendo:grid-column title="Payumoney&nbsp;Id&nbsp;*" field="payumoney_id" filterable="true"  width="90px"/>
		
		<kendo:grid-column title="Transaction&nbsp;Id" field="transaction_id" width="100px" filterable="true" />
	  
	    <kendo:grid-column title="Service&nbsp;Type&nbsp;*" field="service_type" width="95px" filterable="true"/>
	    
	    <kendo:grid-column title="cbid;*" field="cbid" width="100px"/>
	  
	    <kendo:grid-column title="Created&nbsp;Date&nbsp;*" field="created_date" format="{0:dd/MM/yyyy}" width="120px" />
	    
	    <kendo:grid-column title="Transaction&nbsp;Amount&nbsp;*" field="tx_amount" width="115px" filterable="true"/>
	    
	    <kendo:grid-column title="Payment&nbsp;Status*" field="payment_status" filterable="true" width="120px"/>
	   
	   
	</kendo:grid-columns>

	<kendo:dataSource >

		<kendo:dataSource-schema >
			<kendo:dataSource-schema-model id="id">
				<kendo:dataSource-schema-model-fields>
					<kendo:dataSource-schema-model-field name="id" />
					
					<kendo:dataSource-schema-model-field name="merchantId" />
					
					<kendo:dataSource-schema-model-field name="payumoney_id"/>
					
					<kendo:dataSource-schema-model-field name="transaction_id" />
					
					<kendo:dataSource-schema-model-field name="service_type" />
					
					<kendo:dataSource-schema-model-field name="cbid" />
					
				  <kendo:dataSource-schema-model-field name="created_date"/>
				
					<kendo:dataSource-schema-model-field name="tx_amount" />
					
					<kendo:dataSource-schema-model-field name="payment_status" />
				
				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>

	</kendo:dataSource>

</kendo:grid>

<script>

	
	
$(document).ready(function() {

    $("#fromMonthpicker").kendoDatePicker({
        start: "month",
        depth: "month",
        format: "dd/MM/yyyy",
        dateInput: true
    });
});



function searchByMonth() {
    var fromDate = $('#fromMonthpicker').val();
	  $.ajax({
		type : "GET",
		url : "./onlinePayment/onlinePaymentFailureReadUrl",
		dataType : "json",
		data : {
			fromDate : fromDate
		},
		success : function(result) {
		//parse(result);
			var grid = $("#grid").getKendoGrid();
			var data = new kendo.data.DataSource();
			grid.dataSource.data(result); 
	       grid.refresh();
		}
	}); 
}



</script>