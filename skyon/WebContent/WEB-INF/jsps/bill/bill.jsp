<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@taglib prefix="kendo"
	uri="/WEB-INF/lib/kendo-taglib-2013.3.1427.jar"%>
<script src="/resources/js/JSPDF/html2canvas.js"></script>
<script src="/resources/js/plugins/charts/highcharts.js"></script>
<script src="/resources/js/plugins/charts/exporting.js"></script>

<!-- <script type="text/javascript"
	src="http://canvg.googlecode.com/svn/trunk/rgbcolor.js"></script>
<script type="text/javascript"
	src="http://canvg.googlecode.com/svn/trunk/canvg.js"></script> -->

<script type="text/javascript"
	src="<c:url value='/resources/js/jsPDF-master/jspdf.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jsPDF-master/libs/Deflate/adler32cs.js' />"></script>
<script type="text/javascript"
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
	src="<c:url value='/resources/js/jsPDF/libs/sprintf.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jsPDF/tableExport.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jsPDF/jquery.base64.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jsPDF/html2canvas.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jsPDF/libs/sprintf.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jsPDF/libs/base64.js' />"></script>
<%--  <script type="text/javascript" src="<c:url value='/resources/js/jsPDF/jspdf.js' />"></script>    --%>

<c:url value="/bill/accountNumberAutocomplete" var="readGrid" />
<div id="clientsDb" style="width: 50%">
    <kendo:grid name="gridAccount" sortable="true" change="changeAccount" selectable="multiple">
    	<kendo:grid-pageable pageSizes="true" buttonCount="5">
    	</kendo:grid-pageable>
    	
    	
    	<kendo:grid-toolbarTemplate>
			<div style="vertical-align: middle;" class="toolbar">
				<!-- <b style='vertical-align: middle;'>Select All Accounts : </b><input onclick="checkedClick(this)" type='checkbox' style='vertical-align: middle;' id='checkBox'/>	
				 --><button class='k-button' onclick="showDetails()">Send mail to selected accounts</button>
			</div>  	
				
    	</kendo:grid-toolbarTemplate>
    	
        <kendo:grid-columns>
        	
        	<%-- <kendo:grid-column title=" " template="<input type='checkbox' id='SelectedCB'/>" width="20px" /> --%>
			
        	<kendo:grid-column title=" " field="image" template ="<span><img src='./person/getpersonimage/#=personId#' id='myImages_#=personId#' alt='Click to Upload Image' width='80px' height='80px'/></span>"	filterable="false" width="70px" sortable="false"/>
			<kendo:grid-column title="Customer Details" template="#=personName# <br> <b>#=accountNumber# </b> <br> <i>#=personType#</i>" field="accountNumber" width="140" />
			<kendo:grid-column title="&nbsp;" width="90px">
					<kendo:grid-column-command>
							<kendo:grid-column-commandItem name="View Bill" click="viewBill"/>
					</kendo:grid-column-command>
			</kendo:grid-column>     
			<kendo:grid-column title="&nbsp;" width="90px">
					<kendo:grid-column-command>
							<kendo:grid-column-commandItem name="Send Bill" click="sendIndividulaMail"/>
					</kendo:grid-column-command>
			</kendo:grid-column>        
        </kendo:grid-columns>
        <kendo:dataSource pageSize="10">
            <kendo:dataSource-transport>
                <kendo:dataSource-transport-read url="${readGrid}"/>
            </kendo:dataSource-transport>
        </kendo:dataSource>
    </kendo:grid>
</div>  


<div id="billTable" style="color: black">
</div>

<div id="detailedBillTable" style="color: black">
</div>

<script>
	var electricityAmt = 0.0;
	var waterAmt = 0.0;
	var gasAmt = 0.0;
	var swAmt = 0.0;
	var teleAmt = 0.0;
	var commonAmt = 0.0;
	var accid = "";
	var personId = "";
	var accountNumber ="";

	function checkedClick(checkbox){
		 if (checkbox.checked)
		    {
			 $('input[id^=SelectedCB]').prop("checked","checked");
		    }else{    	
		    	$('input[id^=SelectedCB]').prop("checked",false);
		    }	
	}
	
	
	function showDetails() {

		var accountIds = "";
		var personIds = "";
		var grid = $("#gridAccount").data("kendoGrid");
		grid.select().each(function() {
			var dataItem = grid.dataItem($(this));
			accountIds += dataItem.accountId + ",";
			personIds += dataItem.personId + ",";
		});

		
		$.ajax({
			type : "POST",
			url : "./bill/exportData?accountIds="+accountIds+"&personIds="+personIds,
			data : {
				action : "multiple"
			},
			dataType : "text",
			success : function(response) {
				
			}
		});
	}


	function changeAccount(e) {

		var gview = $("#gridAccount").data("kendoGrid");
		var selectedItem = gview.dataItem(gview.select());
		accid = selectedItem.accountId;
		personId = selectedItem.personId;
		accountNumber = selectedItem.accountNumber;
	}

	function viewBill() {
		$('#accno').text(accountNumber);

		$.ajax({
					type : "POST",
					url : "bill/ajaxtable",
					data : {
						accountId : accid,
						personId : personId
					},
					dataType : "text",
					success : function(response) {

						$("#billTable").html(response);

						var eleamt = parseInt($("#eleamt").text());
						var gasamt = parseInt($("#gasamt").text());
						var swamt = parseInt($("#swamt").text());
						var wateramt = parseInt($("#wateramt").text());

						var chart = new Highcharts.Chart(
								{
									colors : [ "#7cb5ec", "#f7a35c", "#90ee7e",
											"#7798BF", "#aaeeee", "#ff0066",
											"#eeaaee", "#55BF3B", "#DF5353",
											"#7798BF", "#aaeeee" ],
									chart : {
										plotBackgroundColor : null,
										plotBorderWidth : 1,//null,
										plotShadow : false,
										renderTo : 'syed',
										zoomType : 'x'
									},
									credits : {
										enabled : false
									},
									title : {
										text : 'Amount Shares',
										align : 'center',
										verticalAlign : 'middle',
										y : -165
									},
									tooltip : {
										pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
									},
									plotOptions : {
										pie : {
											allowPointSelect : true,
											cursor : 'pointer',
											dataLabels : {
												enabled : false
											},
											showInLegend : true
										}
									},
									series : [ {
										type : 'pie',
										name : 'Amount share',
										data : [ [ 'Electricity', eleamt ],
												[ 'Water', wateramt ],
												[ 'Solid Waste', swamt ],
												[ 'Gas', gasamt ],
												[ 'Telephone', 0.0 ],
												[ 'Common', 0.0 ] ]
									} ],
									navigation : {
										buttonOptions : {
											enabled : false
										}
									}
								});

						var wnd2 = $("#billTable").kendoWindow(
								{
									visible : false,
									resizable : false,
									modal : true,
									actions : [ "Custom", "Minimize",
											"Maximize", "Close" ],
									title : "Bill"
								}).data("kendoWindow");

						wnd2.center().open();

					}
				});
	}

	function sendIndividulaMail() {
		$.ajax({
			type : "GET",
			url : "./bill/exportData?accountId="+accid,
			data : {
				action : "single",
				personId : personId
			},
			dataType : "text",
			success : function(response) {
				alert("Mail Sent!!! Please check");
			}
		});
	}

	function detailedBill(typeOfService) {
		
		$.ajax({
			type : "GET",
			url : "./bill/getAjaxDetailedBill?accountId="+accid+ "&typeOfService=" + typeOfService,
			dataType : "text",
			success : function(response) {
				$('#detailedBillTable').html(response);
				var wnd3 = $("#detailedBillTable").kendoWindow(
						{
							visible : false,
							resizable : false,
							modal : true,
							actions : [ "Custom", "Minimize",
									"Maximize", "Close" ],
							title : "Bill"
						}).data("kendoWindow");

				wnd3.center().open();

			}
		});
		

		/* window.open("./detailedbill?accid=" + accid + "&personId=" + personId
				+ "&typeOfService=" + typeOfService, "_blank"); */

	}

</script>

<style>


td {
	vertical-align: top;
} 


</style>