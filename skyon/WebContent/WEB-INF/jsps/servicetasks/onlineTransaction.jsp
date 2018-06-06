<%@include file="/common/taglibs.jsp"%>

<script type="text/javascript"
	src="<c:url value='/resources/jquery-validate.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jsPDF/libs/FileSaver.js/FileSaver.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/js/jsPDF/libs/FileSaver.js/FileSaver.min.js' />"></script>
<script src="http://html2canvas.hertzen.com/build/html2canvas.js"></script>
<script src="resources/js/plugins/charts/highcharts.js"></script>
<script src="resources/js/plugins/charts/exporting.js"></script>
<script type="text/javascript"
	src="http://canvg.googlecode.com/svn/trunk/rgbcolor.js"></script>
<script type="text/javascript"
	src="http://canvg.googlecode.com/svn/trunk/canvg.js"></script>
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

	<c:url value="/onlineTransaction/billRead" var="readUrl" />
	
	<c:url value="/numberofbilled/getTypeOfService" var="getTypeOfService" />
	<c:url value="/common/getAllChecks" var="allChecksUrl" />
	<c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />
	<c:url value="/servicetasks/saveorupadteordelete" var="serviceSaveOrUpdateOrDelete" />
	 <c:url value="/filterForAccountNumber" var="commonFilterForAccountNumbersUrl" /> 
	 <c:url value="/filterForPropertyNumber" var="commonFilterForPropertyNumbersUrl" />	 
	 <c:url value="/filterForPaymentStatus" var="commonFilterForStatusUrl" />
	 
	 <c:url value="/filterForPayUMoneyId" var="commonFilterForPayUMoneyIDUrl" /> 
	 <c:url value="/filterForTransactionId" var="commonFilterForTransactionIdUrl" /> 
	
	<c:url value="/filterForServiceType" var="commonFilterForserviceTypeUrl" />
	
	
	<kendo:grid name="grid" edit="serviceParameterMasterEdit" pageable="true" scrollable="true" sortable="true" reorderable="true" groupable="true" selectable="true"
		resizable="true">
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10"
			input="true" numeric="true"></kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
			<kendo:grid-filterable-operators>
				<kendo:grid-filterable-operators-string eq="Is equal to" />
			</kendo:grid-filterable-operators>
		</kendo:grid-filterable>
		<kendo:grid-editable mode="popup"
			confirmation="Are You Sure to delete?" />
		<kendo:grid-toolbar>
		
		<%-- <kendo:grid-toolbarItem template="<label class="category-label">&nbsp;&nbsp;Select&nbsp;the&nbsp;Category&nbsp;to&nbsp;Add&nbsp;Owner&nbsp;:&nbsp;&nbsp;</label>" /> --%>
		<kendo:grid-toolbarItem template="<label>&nbsp;&nbsp;From Month&nbsp;:&nbsp;</label><input id='monthpicketfrom' style='width:162px' />" />
		
		<kendo:grid-toolbarItem template="<label>&nbsp;&nbsp;To Month&nbsp;&nbsp;:&nbsp;</label><input id='monthpickerto' style='width:162px' />" />
		<kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=searchByMonth() title='Search'>Search</a>" />
			
			<%-- <kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilter() title='Clear Filter'></a>"/> --%>
			 <kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilter()>Clear Filter</a>"/>
		</kendo:grid-toolbar>


		<kendo:grid-columns>
			 <kendo:grid-column title="onlineid" field="onlineid" width="1px" hidden="true"/> 
			 <kendo:grid-column title="Transaction&nbsp;Date" field="transactionDate" width="150px" format="{0:dd/MM/yyyy}"/>
			<kendo:grid-column title="Account&nbsp;No.&nbsp;*" field="accountNo" width="90px" filterable="true"> 
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
			<kendo:grid-column title="Person Name" field="personName" width="100px" filterable="true"/>			
			<kendo:grid-column title="Property No.&nbsp;*" field="propertyNo"	width="100px" filterable="true">
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function ledgerTypeFilter(element) {
							element.kendoAutoComplete({
										placeholder : "Enter Property Number",
										dataSource : {
											transport : {
												read : "${commonFilterForPropertyNumbersUrl}"
											}
										}
									});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
			</kendo:grid-column>
			<kendo:grid-column title="PayU Money Id&nbsp;*" field="payumoneyId" width="150px" filterable="true">
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function ledgerTypeFilter(element) {
							element.kendoAutoComplete({
										placeholder : "Enter PayU Money Id",
										dataSource : {
											transport : {
												read : "${commonFilterForPayUMoneyIDUrl}"
											}
										}
									});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
			</kendo:grid-column>
			<kendo:grid-column title="Transaction Id&nbsp;*" field="TransactionId" width="150px" filterable="true">
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function ledgerTypeFilter(element) {
							element.kendoAutoComplete({
										placeholder : "Enter Transaction Id",
										dataSource : {
											transport : {
												read : "${commonFilterForTransactionIdUrl}"
											}
										}
									});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
			</kendo:grid-column>
			<kendo:grid-column title="Transaction Amount" field="transactionAmount" width="140px" filterable="true"/>
			<kendo:grid-column title="Service Type&nbsp;*" field="serviceType" width="130px" filterable="true">
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function ledgerTypeFilter(element) {
							element.kendoAutoComplete({
										placeholder : "Enter Service Type",
										dataSource : {
											transport : {
												read : "${commonFilterForserviceTypeUrl}"
											}
										}
									});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
			</kendo:grid-column>
			<kendo:grid-column title="Payment Status&nbsp;*" field="paymentStatus" width="130px" filterable="true">
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function ledgerTypeFilter(element) {
							element.kendoAutoComplete({
										placeholder : "Enter Payment Status",
										dataSource : {
											transport : {
												read : "${commonFilterForStatusUrl}"
											}
										}
									});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
			</kendo:grid-column>		
		<%-- 	<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function ledgerTypeFilter(element) {
							element.kendoAutoComplete({
										placeholder : "Enter Property Number",
										dataSource : {
											transport : {
												read : "${commonFilterForTransactionAmountUrl}"
											}
										}
									});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable> --%>
			
			
			
			
			
			
			
		</kendo:grid-columns>	
		<kendo:dataSource requestStart="onRequestStartServiceMaster" requestEnd="onRequestEndServiceMaster">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json" />
			</kendo:dataSource-transport>
			<kendo:dataSource-schema>
			<kendo:dataSource-schema-model id="chequeBounceId">
			<kendo:dataSource-schema-model-fields>
			<%-- 	<kendo:dataSource-schema-model-field name="id" type="number"/> --%>
				<kendo:dataSource-schema-model-field name="personName" type="string"/>
				<kendo:dataSource-schema-model-field name="accountNo" type="string"/>
				<kendo:dataSource-schema-model-field name="propertyNo" type="string"/>
				<kendo:dataSource-schema-model-field name="transactionAmount" type="number" defaultValue="">
				<kendo:dataSource-schema-model-field-validation min="0"/>
				</kendo:dataSource-schema-model-field>
				<kendo:dataSource-schema-model-field name="transactionDate" type="date">
				</kendo:dataSource-schema-model-field>
				<kendo:dataSource-schema-model-field name="paymentStatus" type="string"/>
				<kendo:dataSource-schema-model-field name="payumoneyId" type="string"/>
				<kendo:dataSource-schema-model-field name="TransactionId" type="string"/>
				
			</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model> 
			</kendo:dataSource-schema> 
		</kendo:dataSource>
	</kendo:grid>
<div id="alertsBox" title="Alert"></div>
<script>
		function date1()
		{
			alert("hi");
		}

		function consolidateBill(){
			
			var month = $("#monthDatePicker").val();
			
			if(month == '' ){
				alert("Select month to consolidate Bill");
			}else{
			var result=securityCheckForActionsForStatus("./BillGeneration/ConsolidateBills/consolidateBillButton"); 
			  if(result=="success"){
			$.ajax({
				type : "GET",
				url : "./consolidate/consolidateBills?month="+month,
				dataType : "text",
				success : function(response) {
					alert("Consolidated");
					$('#grid').data('kendoGrid').dataSource
					.read();
			$('#grid').data('kendoGrid').refresh();
				}
			});
			  }
			}
		}
		
		$(document)
		.ready(function() {
			
			/* var todayDate = new Date();
			var picker = $("#monthDatePicker").kendoDatePicker({
						 format: "MM/yyyy"
					}).data("kendoDatePicker"),
		    dateView = picker.dateView;
			dateView.calendar.element.removeData("dateView");        
			picker.max(todayDate);
	      	picker.options.depth = dateView.options.depth = 'year';
	      	picker.options.start = dateView.options.start = 'year';
	       	picker.value(picker.value()); */
	       
	       /* 	$('#monthDatePicker').keyup(function() {
				$('#monthDatePicker').val("");
			}); */
	      /*  	$("#monthpicketfrom").kendoDatePicker({
				  // defines the start view
				  start: "year",
				  // defines when the calendar should return date
				  depth: "year",
				  value:new Date(),
				  // display month and year in the input
				  format: "MMMM yyyy"
				}); */
			/* $("#monthpickerto").kendoDatePicker({
				  // defines the start view
				  start: "year",
				  // defines when the calendar should return date
				  depth: "year",
				  value:new Date(),
				  // display month and year in the input
				  format: "MMMM yyyy"
				});
	       */
			var todayDate = new Date();
			var picker = $("#monthpickerto").kendoDatePicker({
				start: "year",
				depth: "year",
				  value:new Date(),
						 format: "MMMM yyyy"
					}).data("kendoDatePicker"),
		    dateView = picker.dateView;
			dateView.calendar.element.removeData("dateView");        
			picker.max(todayDate);
	      	picker.options.depth = dateView.options.depth = 'year';
	      	picker.options.start = dateView.options.start = 'year';
	       	picker.value(picker.value());
	       
	       	$('#monthpickerto').keyup(function() {
				$('#monthpickerto').val("");
			});
	       	var todayDate = new Date();
			var picker = $("#monthpicketfrom").kendoDatePicker({
				start: "year",
				depth: "year",
				  value:new Date(),
						 format: "MMMM yyyy"
					}).data("kendoDatePicker"),
		    dateView = picker.dateView;
			dateView.calendar.element.removeData("dateView");        
			picker.max(todayDate);
	      	picker.options.depth = dateView.options.depth = 'year';
	      	picker.options.start = dateView.options.start = 'year';
	       	picker.value(picker.value());
	       
	       	$('#monthpicketfrom').keyup(function() {
				$('#monthpicketfrom').val("");
			});
			
			/*  var data = [
	                     { text: "January", value: "1" },
	                     { text: "Febrauary", value: "2" },
	                     { text: "March", value: "3" },
	                     { text: "April", value: "4" },
	                     { text: "May", value: "5" },
	                     { text: "June", value: "6" },
	                     { text: "July", value: "7" },
	                     { text: "August", value: "8" },
	                     { text: "September", value: "9" },
	                     { text: "October", value: "10" },
	                     { text: "November", value: "11" },
	                     { text: "December", value: "12" },
	                 ];

	                 // create DropDownList from input HTML element
	                 $("#month").kendoDropDownList({
	                     dataTextField: "text",
	                     dataValueField: "value",
	                     dataSource: data
	                 }); */
		});
		
		
	function clearFilter() {
				//custom actions

				$("form.k-filter-menu button[type='reset']").slice()
						.trigger("click");
				var gridServiceMaster = $("#grid").data("kendoGrid");
				gridServiceMaster.dataSource.read();
				gridServiceMaster.refresh();
	}
	
/* 	var monthType="monthType";
	 $("#monthType").kendoDropDownList({
       autoBind: false,
       optionLabel : "Select",
       dataTextField: "text",
       dataValueField: "value",
       dataSource: {  
           transport:{
           	read:"${allChecksUrl}/"+monthType,
           }
       }
   }); */
	 
	
  
	function dropDownChecksEditor(container, options) {
		var res = (container.selector).split("=");
		var attribute = res[1].substring(0, res[1].length - 1);

		$(
				'<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '" name = "'+attribute+'" id = "'+attribute+'Id"/>')
				.appendTo(container).kendoDropDownList({
					optionLabel : {
						text : "Select",
						value : "",
					},
					defaultValue : false,
					sortable : true,
					dataSource : {
						transport : {
							read : "${allChecksUrl}/" + attribute,
						}
					}

				});
		$('<span class="k-invalid-msg" data-for="'+attribute+'"></span>')
				.appendTo(container);
	}
	
	 
	function onRequestStartServiceMaster(e)
	{
		var grid = $("#grid").data("kendoGrid");
		grid.cancelRow();
	}
	function onRequestEndServiceMaster(e) 
	{
		displayMessage(e, "grid", "Service Master");
	}
	function serviceParameterMasterEdit(e)
	{
		var sessionUserLoginName = '<%=session.getAttribute("userId")%>';
		
		$('label[for="elBillId"]').remove();
		$('div[data-container-for="elBillId"]').remove();


		
		
		$('.k-edit-label:nth-child(18n+1)').each(
				function(e) {
					$(this).nextAll(':lt(17)').addBack().wrapAll(
							'<div class="wrappers"/>');
				});
		
		$('a[id="temPID"]').remove();
	
		if (e.model.isNew()) 
	    {
			$(".k-window-title").text("Add New Store Record");
			$(".k-grid-update").text("Save");
	
	    }
		else
		{
			$(".k-window-title").text("Edit");
		}

	}
	
	function searchByMonth() {
		var month = $('#monthpicketfrom').val();
		var month1=$('#monthpickerto').val();
	   var splitmonth=month.split(" ");
	   var splitmonth1=month1.split(" ");
	   var months = [ "January", "February", "March", "April", "May", "June",   "July", "August", "September", "October", "November", "December" ];
		/* alert(months.indexOf(splitmonth[0])); */
	   if(splitmonth[1]>splitmonth1[1])
		   {
		     alert("To Month should be greater than From Month");
		     return false;
		   }
	   if(splitmonth[1]==splitmonth1[1])
  		{
  			if(months.indexOf(splitmonth[0])>months.indexOf(splitmonth1[0]))
  				{
  				alert("To Month should be greater than From Month");
  				return false;
  				
  				}
  		}
	   
	   
		$.ajax({
			type : "POST",
			url : "./onlineTransaction/byMonth/" + month+"/"+month1,
			dataType : "json",
			success : function(result) {
				var grid = $("#grid").getKendoGrid();
				var data = new kendo.data.DataSource();
				grid.dataSource.data(result);
				grid.refresh();
			}
		}); 
	}
</script>
