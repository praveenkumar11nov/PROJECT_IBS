<%@include file="/common/taglibs.jsp"%>


<script type="text/javascript" src="<c:url value='/resources/jquery-validate.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jsPDF/libs/FileSaver.js/FileSaver.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jsPDF/libs/FileSaver.js/FileSaver.min.js' />"></script>
<script src="http://html2canvas.hertzen.com/build/html2canvas.js"></script>

<script src="resources/js/plugins/charts/highcharts.js"></script>
<script src="resources/js/plugins/charts/exporting.js"></script>

<script type="text/javascript" src="http://canvg.googlecode.com/svn/trunk/rgbcolor.js"></script>
<script type="text/javascript" src="http://canvg.googlecode.com/svn/trunk/canvg.js"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jsPDF-master/jspdf.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jsPDF-master/libs/Deflate/adler32cs.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jsPDF-master/libs/FileSaver.js/FileSaver.js"' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jsPDF-master/libs/Blob.js/BlobBuilder.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jsPDF-master/jspdf.plugin.addimage.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jsPDF-master/jspdf.plugin.standard_fonts_metrics.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jsPDF-master/jspdf.plugin.split_text_to_size.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jsPDF-master/jspdf.plugin.from_html.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jsPDF/libs/sprintf.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jsPDF/tableExport.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jsPDF/jquery.base64.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jsPDF/html2canvas.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jsPDF/libs/sprintf.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/js/jsPDF/libs/base64.js' />"></script>

<c:url value="/prepaidPaymentsHistory/readUrl" var="readUrl"></c:url>
<c:url value="/paymentHistory/filter" var="commonFilterForBillUrl" />
<div id="dvLoadingbody" class="loadingimg" hidden="true" ></div>

<kendo:grid name="grid" change="onChangePaymentSegmet" dataBound="prepaidDataBound" pageable="true" resizable="true"  sortable="true" reorderable="true" selectable="false" scrollable="true" filterable="false" groupable="true">

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
	<kendo:grid-editable mode="popup"/>
		<kendo:grid-toolbar>
		      <kendo:grid-toolbarItem template="<label class='category-label'>&nbsp;&nbsp;From&nbsp;Date&nbsp;:&nbsp;&nbsp;</label><input id='fromMonthpicker' style='width:110px'/>"/>
			  <kendo:grid-toolbarItem template="<label class='category-label'>&nbsp;&nbsp;To&nbsp;Date&nbsp;:&nbsp;&nbsp;</label><input id='toMonthpicker' style='width:110px'/>"/>
			  <kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=searchByMonth() title='Search' style='width:90px'>Search</a>"/>
		      <kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterPayments()>Clear Filter</a>"/>
		      <kendo:grid-toolbarItem name="exportPaymentHistory" text="Export To Excel"></kendo:grid-toolbarItem>
		      <kendo:grid-toolbarItem name="generateXMLForTally" text="Generate XML for Tally"></kendo:grid-toolbarItem>
		      <kendo:grid-toolbarItem name="postAllToTally" text="Post All To Tally"></kendo:grid-toolbarItem>
	    </kendo:grid-toolbar>
	<kendo:grid-columns>
	    
	    <kendo:grid-column title="paymentCollectionId" field="paymentCollectionId" width="10px" hidden="true" filterable="false" sortable="false" />
	      <kendo:grid-column title="Consumer&nbsp;Id" field="consumerId" width="100px" filterable="true" >
	    </kendo:grid-column>
	     <kendo:grid-column title="Property&nbsp;No&nbsp;*" field="property_No" filterable="true"  width="90px">
		</kendo:grid-column>
	    <kendo:grid-column title="Person&nbsp;Name" field="personName"  width="100px" filterable="false">
	    	<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function personNameFilter(element) 
						   	{
								element.kendoAutoComplete({
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
											read :  "${personNamesFilterUrl}"
										}
									} 
								});
						   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	    	</kendo:grid-column>
		    <kendo:grid-column title="Meter&nbsp;Number" field="meterNo" width="100px" filterable="true" >
	    </kendo:grid-column>
	     <kendo:grid-column title="Receipt&nbsp;No&nbsp;*" field="receiptNo" width="95px" filterable="true">
	    <kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function ledgerStatusFilter(element) {
								element.kendoAutoComplete({
											placeholder : "Enter Receipt Number",
											dataSource : {
												transport : {
													read : "${commonFilterForBillUrl}/receiptNo"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	    </kendo:grid-column>
			<kendo:grid-column title="Receipt&nbsp;Date&nbsp;*" field="receiptDate" format="{0:dd/MM/yyyy}" width="105px">
	    </kendo:grid-column>
	    
	    <kendo:grid-column title="Amount&nbsp;*" field="rechargedAmount" filterable="true" width="120px">
		</kendo:grid-column>
	   
	    <kendo:grid-column title="Payment&nbsp;Mode&nbsp;*" field="paymentMode" width="115px" filterable="true">
	    <kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function ledgerStatusFilter(element) {
								element.kendoAutoComplete({
											placeholder : "Enter Payment Mode",
											dataSource : {
												transport : {
													read : "${commonFilterForBillUrl}/paymentMode"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	    </kendo:grid-column>
	    
	    <kendo:grid-column title="Instrument&nbsp;Date&nbsp;*" field="instrumentDate" format="{0:dd/MM/yyyy}" width="120px" >
	    </kendo:grid-column>
	   
	   <%--  <kendo:grid-column title="Instrument&nbsp;Number&nbsp;*" field="instrumentNo" width="140px" filterable="true" hidden="true">
	    <kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function ledgerStatusFilter(element) {
								element.kendoAutoComplete({
											placeholder : "Enter Instrument Number",
											dataSource : {
												transport : {
													read : "${commonFilterForPaymentCollectionUrl}/instrumentNo"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	    </kendo:grid-column> --%>
	    <kendo:grid-column title="Status" field="status" width="80px" filterable="true">
	    <kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function ledgerStatusFilter(element) {
								element.kendoAutoComplete({
											placeholder : "Enter Status",
											dataSource : {
												transport : {
													read : "${commonFilterForBillUrl}/status"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	    </kendo:grid-column>
	    <kendo:grid-column title="Bank&nbsp;Name&nbsp;*" field="bankName" width="110px" filterable="true" hidden="true">
	    <kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function ledgerStatusFilter(element) {
								element.kendoAutoComplete({
											placeholder : "Enter Bank Name",
											dataSource : {
												transport : {
													read : "${commonFilterForPaymentCollectionUrl}/bankName"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	    </kendo:grid-column>
	    <kendo:grid-column title="Tally Status" field="tallyStatus" width="80px" filterable="true">
	    	<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
					function ledgerStatusFilter(element) {
						element
								.kendoDropDownList({
									optionLabel : "Select status",
									dataSource : {
										transport : {
											read : "${commonFilterForBillUrl}/tallyStatus"
										}
									}
								});
					}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
	    </kendo:grid-column>
		    
		<kendo:grid-column title="&nbsp;" width="110px">
				<kendo:grid-column-command >
					 <kendo:grid-column-commandItem name="Print Receipt" click="paymentReceipt" />
				</kendo:grid-column-command>
		</kendo:grid-column>  
		<kendo:grid-column title="&nbsp;" width="110px">
				<kendo:grid-column-command >
					 <kendo:grid-column-commandItem name="Post To Tally" click="postToTally" />
				</kendo:grid-column-command>
		</kendo:grid-column>    
		
	</kendo:grid-columns>

	<kendo:dataSource pageSize="20">
		<kendo:dataSource-transport>
			<kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json" />
		</kendo:dataSource-transport>

		<kendo:dataSource-schema parse="parse">
			<kendo:dataSource-schema-model id="paymentCollectionId">
				<kendo:dataSource-schema-model-fields>
					<kendo:dataSource-schema-model-field name="paymentCollectionId" type="number"/>
					
					<kendo:dataSource-schema-model-field name="receiptDate" type="date">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="personName" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="property_No" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="receiptNo" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="typeOfService" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="paymentMode" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="bankName" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="instrumentDate" type="date">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="instrumentNo" type="string">
					</kendo:dataSource-schema-model-field>
					
					
					<kendo:dataSource-schema-model-field name="rechargedAmount" type="number">
					</kendo:dataSource-schema-model-field>
					
					
					<kendo:dataSource-schema-model-field name="status" type="string">
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="tallyStatus" type="string">
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="meterNo" type="string"></kendo:dataSource-schema-model-field>

				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>

	</kendo:dataSource>

</kendo:grid>
<div id="PaymentHistorydiv" style="display: none;">
	<form id="PaymentHistorydivform">
		<table style="height: 190px;">

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
					<button type="submit" id="datasubmit" class="k-button"
						style="padding-left: 10px">Export To Excel</button>
			</tr>

		</table>
	</form>
</div>
<div id=generateXMLDiv style="display: none;">
	<form id="generateXMLDivform">
		<table style="height: 110px;">
             <tr>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			 <tr>
				<td>Month</td>
				<td><input type="date" name="fromMonth" id="fromMonth" required="required"></td>
				<td></td>
			</tr>

			<tr>
				<td class="left" align="center" colspan="4">
					<button type="submit" id="exportXml" class="k-button"
						style="padding-left: 10px">Export To XML</button>
			</tr>

		</table>
	</form>
</div>
<div id=postAllToTallydiv style="display: none;">
	<form id="postAllToTallydivform">
		<table style="height: 110px;">
             <tr>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			 <tr>
				<td>Month</td>
				<td><input type="date" name="monthPicker" id="monthPicker" required="required"></td>
				<td></td>
			</tr>

			<tr>
				<td class="left" align="center" colspan="4">
					<button type="submit" id="postPaymentToTally" class="k-button"
						style="padding-left: 10px">Post All Payments</button>
			</tr>

		</table>
	</form>
</div>
<!-- <div id="paymentCalcLinesPopUp"></div>	 -->
<div id="billTable" style="color: black"></div>
<div id="alertsBox" title="Alert"></div>
<!-- <a href="#" onclick="javascript:CallPrint('billTable')">Print</a> -->
<script>
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

$( document ).ready(function() {	
	
	var todayDate = new Date();
	var picker = $("#fromMonthpicker").kendoDatePicker({
		start: "month",
		depth: "month",
		  value:new Date(),
				 format: "dd/MM/yyyy"
			}).data("kendoDatePicker"),
    dateView = picker.dateView;
	dateView.calendar.element.removeData("dateView");        
	picker.max(todayDate);
  	picker.options.depth = dateView.options.depth = 'month';
  	picker.options.start = dateView.options.start = 'month';
   	picker.value(picker.value());
   
   	$('#fromMonthpicker').keyup(function() {
		$('#fromMonthpicker').val("");
	});
   	var todayDate = new Date();
	var picker = $("#toMonthpicker").kendoDatePicker({
		start: "month",
		depth: "month",
		  value:new Date(),
				 format: "dd/MM/yyyy"
			}).data("kendoDatePicker"),
    dateView = picker.dateView;
	dateView.calendar.element.removeData("dateView");        
	picker.max(todayDate);
  	picker.options.depth = dateView.options.depth = 'month';
  	picker.options.start = dateView.options.start = 'month';
   	picker.value(picker.value());
   
   	$('#toMonthpicker').keyup(function() {
		$('#toMonthpicker').val("");
	});
});

function searchByMonth() {
    var fromDate = $('#fromMonthpicker').val();
    var toDate = $('#toMonthpicker').val();
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
		url : "./prepaidPaymentsHistory/searchprepaidPaymentsHistoryByDate",
		dataType : "json",
		data : {
			fromDate : fromDate,
			toDate : toDate
		},
		success : function(result) {
			parse(result);
			var grid = $("#grid").getKendoGrid();
			var data = new kendo.data.DataSource();
			grid.dataSource.data(result);
			grid.refresh();
		}
	}); 
}


var SelectedRowId = "";

function onChangePaymentSegmet(arg) {
	 var gview = $("#grid").data("kendoGrid");
	 var selectedItem = gview.dataItem(gview.select());
	 SelectedRowId = selectedItem.paymentCollectionId;
	 this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
    
}

function prepaidDataBound(){
	
	var data = this.dataSource.view(),row;
	var grid = $("#grid").data("kendoGrid");
	
    for (var i = 0; i < data.length; i++) {
    	var currentUid = data[i].uid;
        row = this.tbody.children("tr[data-uid='" + data[i].uid + "']");
        
        var tallyStatus = data[i].tallyStatus;
        if (tallyStatus=='Posted') {
        	var currenRow = grid.table.find("tr[data-uid='" + currentUid+ "']");alert("currenRow="+currenRow);
			var postTotally = $(currenRow).find(".k-grid-PostToTally");
			postTotally.hide();
        } 
    }
}

var printFlag = "";
function paymentReceipt(){	
	$("#billTable").empty();
	var paymentCollectionId = "";
	var paymentStatus = "";
	var gridParameter = $("#grid").data("kendoGrid");
	var selectedAddressItem = gridParameter.dataItem(gridParameter.select());
	paymentCollectionId = selectedAddressItem.paymentCollectionId;
	paymentStatus = selectedAddressItem.status;
	
	
		$.ajax
		({
			type : "POST",
			url : "./collections/prepaidPaymentsHistory/getprepaidPaymentsHistoryDetails",
			data : {
				paymentCollectionId : paymentCollectionId
			},
			dataType:"text",
			success : function(response) 
			{
				$("#billTable").html(response);
			
				var wnd2 = $("#billTable").kendoWindow({
				    visible:false,
				    resizable: false,
				    modal: true,
				    actions: ["Custom","Close"],
	                title: "Payment Receipt"
				}).data("kendoWindow");
				
				wnd2.center().open();
				
				var kendoWindow = $("#billTable").data("kendoWindow");
				kendoWindow.wrapper.find(".k-i-custom").addClass('icon-printer').addClass('fa').removeClass('k-i-custom').removeClass('k-icon');
				$('.fa').html('');
				$("#billTable").data("kendoWindow").wrapper.find(".icon-printer").click(function(e){
					 var prtContent = document.getElementById('billTable');
			            var WinPrint = window.open('', '', 'letf=0,top=0,width=400,height=400,toolbar=0,scrollbars=0,status=0');
			            WinPrint.document.write(prtContent.innerHTML);
			            WinPrint.document.close();
			            WinPrint.focus();
			            WinPrint.print();
			            WinPrint.close();
				});  
				
			}
		});
	  }

function postToTally(){
	var gridParameter = $("#grid").data("kendoGrid");
	var selectedAddressItem = gridParameter.dataItem(gridParameter.select());
	paymentCollectionId = selectedAddressItem.paymentCollectionId;

	$('tr[aria-selected="true"]').find('td:nth-child(30)').html("");
	$('tr[aria-selected="true"]').find('td:nth-child(30)').html("<img src='./resources/images/151.gif' width='100px' height='25px' />");
	
	$.ajax
	({
		type:"POST",
	    url:"./prepaidPaymentHistory/postCollectionDataTally",
	    dataType:"text",
	    data:{
	    	paymentCollectionId:paymentCollectionId
	    },
	    success : function(response){
	    	  alert(response);
	    	  window.location.reload();
	    }
	});
}
/* ------------------------EXPORT TO XML Start----------------------------------------------------------------- */
   $("#fromMonth").kendoDatePicker({
	// defines the start view
	start : "year",
	// defines when the calendar should return date
	depth : "year",
	value : new Date(),
	// display month and year in the input
	format : "MMM yyyy"
});
   $("#monthPicker").kendoDatePicker({
		// defines the start view
		start : "year",
		// defines when the calendar should return date
		depth : "year",
		value : new Date(),
		// display month and year in the input
		format : "MMM yyyy"
	});

 $("#grid").on("click",".k-grid-generateXMLForTally",function(e){
	 
	 var bbDialog = $("#generateXMLDiv");
		bbDialog.kendoWindow({
			width : "auto",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 80
			},
			title : "Export To XML"
		}).data("kendoWindow").center().open();

		bbDialog.kendoWindow("open");
		$("#fromMonth").val("");
 });
 
 $("#exportXml").click(function(){
	 
	 var fromDate=$("#fromMonth").val();
	 window.open("./prepaidPaymentsHistory/exportToXml?fromDate="+fromDate);
	 filterclose1();
	/*  $.ajax({
		type:"POST",
	    url:"./prepaidPaymentsHistory/exportToXml?fromDate="+fromDate,
	    dataType:"json",
	    success: function(response){
	    	filterclose1();
	    	var grid = $("#grid").getKendoGrid();
			var data = new kendo.data.DataSource();
			grid.dataSource.data(result);
			grid.refresh();
	    },
	 }); */
	 
 });
 function filterclose1() {

		var todcal = $("#generateXMLDiv");

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
 function filtertallyclose() {

		var todcal = $("#postAllToTallydiv");

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
 
 
 $("#grid").on("click",".k-grid-postAllToTally", function(e){
	 var bbDialog = $("#postAllToTallydiv");
		bbDialog.kendoWindow({
			width : "auto",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 80
			},
			title : "Post All Payments"
		}).data("kendoWindow").center().open();

		bbDialog.kendoWindow("open");
		$("#monthPicker").val("");
 });
 $("#postPaymentToTally").click(function(){
	 var fromDate=$("#monthPicker").val();
	 $.ajax({
		 type:"POST",
		 url:"./prepaidPaymentHtry/postAllPaymentsToTally",
		 dataType:"text",
		 data:{
			 fromDate:fromDate
		 },
		 success: function(response){
			 filtertallyclose();
			 alert(response);
	    	  window.location.reload();
		 }
	 });
 });
 /* ---------------------------END------------------------------------------------------- */
//for parsing timestamp data fields
function parse (response) {
    $.each(response, function (idx, elem) {   
    	  
            if(elem.receiptDate=== null){
                elem.receiptDate = " ";
             }else{
                elem.receiptDate = kendo.parseDate(new Date(elem.receiptDate),'dd/MM/yyyy');
             }
            
            if(elem.instrumentDate=== null){
                elem.instrumentDate = " ";
             }else{
                elem.instrumentDate = kendo.parseDate(new Date(elem.instrumentDate),'dd/MM/yyyy');
             }
       });
               
       return response;
}
$("#datasubmit").click(function() {
	    var fromDate = $('#fromDate').val();
	    //alert(fromDate);
	    var toDate = $('#toDate').val();
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
		   window.open("./prepaidPaymentsHistory/exportprepaidPaymentsHistoryData?fromDate="+fromDate+"&toDate="+toDate);
		   filterclose();
});

$("#grid").on("click",".k-grid-exportPaymentHistory",function(e){
	var bbDialog = $("#PaymentHistorydiv");
	bbDialog.kendoWindow({
		width : "auto",
		height : "auto",
		modal : true,
		draggable : true,
		position : {
			top : 100
		},
		title : "Payment History"
	}).data("kendoWindow").center().open();

	bbDialog.kendoWindow("open");
	
});
$("#PaymentHistorydivform").submit(function(e) {
	e.preventDefault();
});
$("#generateXMLDivform").submit(function(e) {
	e.preventDefault();
});
$("#postAllToTallydivform").submit(function(e) {
	e.preventDefault();
});
function filterclose() {

	var todcal = $("#PaymentHistorydiv");

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
function clearFilterPayments() {
	//custom actions

	$("form.k-filter-menu button[type='reset']").slice()
			.trigger("click");
	var gridServiceMaster = $("#grid").data("kendoGrid");
	gridServiceMaster.dataSource.read();
	gridServiceMaster.refresh();
}

</script>

<style type="text/css">
.wrappers {
	display: inline;
	float: left;
	width: 350px;
	padding-top: 10px;

	/* float:left;  */
	/* border: 1px solid red;
 */
	/* border: 1px solid green;
    overflow: hidden; */
}
.tftable {font-size:12px;color:#333333;width:100%;border-width: 1px;border-color: #729ea5;border-collapse: collapse;}
.tftable th {font-size:12px;background-color:#acc8cc;border-width: 1px;padding: 8px;border-style: solid;border-color: #729ea5;text-align:left;}
.tftable tr {background-color:#d4e3e5;}
.tftable td {font-size:12px;border-width: 1px;padding: 8px;border-style: solid;border-color: #729ea5;}
.tftable tr:hover {background-color:#ffffff;}

.loadingimg {
	    background: #FFF url(./resources/images/712.GIF) no-repeat center center;
        position: absolute;
        top: 0%;
        left: 0%;
        width: 100%;
        height: 100%;
        z-index:1001;
        -moz-opacity: 0.8;
        opacity:.80;
        filter: alpha(opacity=80);
       }
</style>
