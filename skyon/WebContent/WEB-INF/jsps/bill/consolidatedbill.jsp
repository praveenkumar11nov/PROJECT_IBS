<%@include file="/common/taglibs.jsp"%>



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
	
	<script type="text/javascript"
	src="<c:url value='/resources/js/jsPDF/libs/FileSaver.js/FileSaver.js' />"></script>
	<script type="text/javascript"
	src="<c:url value='/resources/js/jsPDF/libs/FileSaver.js/FileSaver.min.js' />"></script>


<c:url value="/consolidatedbill/read" var="readUrl" />
<c:url value="/consolidatedbill/getPersonListForFileter" var="personNamesFilterUrl" />
<c:url value="/bills/commonFilterForAccountNumbersUrl" var="commonFilterForAccountNumbersUrl" />

<kendo:grid name="consolidatedGrid" resizable="true" selectable="true" change="onChange" filterable="true" pageable="true" sortable="true" scrollable="true" 	groupable="true">
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10"></kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
			<kendo:grid-filterable-operators>
				<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains"/>
			<kendo:grid-filterable-operators-date eq="Is equal to" gt="Is after" lt="Is before" gte="Is after or equals to" lte="Is before or equals to" neq="Is not equal to"/>
			</kendo:grid-filterable-operators>
		</kendo:grid-filterable>
		<kendo:grid-editable mode="popup" />
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem template="<select id='month'></select>"/>
			<%-- <kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=sendMultipleMail() title='Send Mail'>Send Mail</a>"/> --%>
			<kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilter() title='Clear Filter'><span class='k-icon k-i-funnel-clear'></span></a>"/>
			<kendo:grid-toolbarItem name="downloadAllBills" text="Send All Mail" />
		</kendo:grid-toolbar>
		<kendo:grid-columns>
		<kendo:grid-column title="Consolidate Bill Id" field="cbId" width="110px" hidden="true" filterable="true"/>
			<kendo:grid-column title="Account Id" field="accountId" width="110px" hidden="true" filterable="true"/>
			<kendo:grid-column title="Account No" field="accountNo" width="90px" filterable="true">
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
	    	<kendo:grid-column title="Service Type" field="serviceType" width="70px" filterable="true"/>
			<kendo:grid-column title="Amount" field=" billAmount" width="70px" filterable="false"/>
			<kendo:grid-column title="Bill Month" field=" billMonth" width="90px"  filterable="false"/>
			<kendo:grid-column title="Bill Month" field=" billMonthSql" width="100px" hidden="true"/>
			<kendo:grid-column title="Mail Sent Status" field="mailSent_Status" width="100px" filterable="true" />
			<kendo:grid-column title="&nbsp;" width="160px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="Consolidated Bill" click="viewMasterBill" />
					<kendo:grid-column-commandItem name="Individual Bill" click="viewChildBill" />
				</kendo:grid-column-command>
			</kendo:grid-column>
				<%-- <kendo:grid-column title="&nbsp;" width="70px">
					<kendo:grid-column-command>
							<kendo:grid-column-commandItem name="Send Bills" click="sendIndividualMail"/>
					</kendo:grid-column-command>
			</kendo:grid-column>  --%>
			<kendo:grid-column title="&nbsp;" width="90px">
					<kendo:grid-column-command>
							<kendo:grid-column-commandItem name="Mail Individual Bill" click="sendAllIndividualMail"/>
					</kendo:grid-column-command>
			</kendo:grid-column> 
				<%-- <kendo:grid-column title="&nbsp;" width="200px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="Download Consolidated Bill"  click="downloadMaserCB"/>
					<kendo:grid-column-commandItem name="Download Individual Bills"  />
				</kendo:grid-column-command>
			</kendo:grid-column> --%>
		</kendo:grid-columns>
		
		
		<kendo:dataSource pageSize="20">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json" />
			</kendo:dataSource-transport>
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
	
	<div id="downloadAllBillsDiv" style="display: none;">
	<form id="downloadAllBillsForm" data-role="validator"
		novalidate="novalidate">
		<table style="height: 100px;">
			<tr>
				<td>Service Type</td>
				<td>
					
					<input id="serviceTypeDownload" name="serviceTypeDownload" />
				</td>
			</tr>
			<tr>
				<td>Bill Month</td>
				<td><kendo:datePicker format="MMMM yyyy"
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

<div id="alertsBox" title="Alert"></div>

<!-- <canvas id="canvas" height="400px" width="300px"
	style="width: 300px; height: 400px;"></canvas> -->
	<!-- <canvas id="canvas" height="400px" width="300px" style="width: 300px; height: 400px;"></canvas> -->
	<script>

function downloadAllBillsAjax(){
	var selectedMonth =$("#presentdateDownload").val();
	var serviceType =$("#serviceTypeDownload").val();	
	
	if(serviceType==""){
		alert("Select service type");
		return false;
	}else if(selectedMonth==""){
		alert("Select bill month");
		return false;	
	}else{
		$('.k-button').hide();
		$.ajax({
			type : "GET",
			url : "./consolidate/sendAllBillsMonthWiseAndServiceWise",
			dataType : "text",							
			data:{
				selectedMonth : selectedMonth,
				serviceType : serviceType,
			},
			success : function(response) {
				var resMessage = "";
				$('.k-button').show();
				if(response=="success"){
					resMessage = "Mail sent successfully";
				}else{
					resMessage = "Oops! Sorry, an error has occured. Internal Server Error!";
				}
				
				$("#alertsBox").html("");
				$("#alertsBox").html(resMessage);
				$("#alertsBox").dialog
				({
					modal : true,
					buttons : 
					{
						"Close" : function() 
						{
							$(this).dialog("close");
						}
					}
				});
			}
		});
		closepayslip();
	}
}


function closepayslip(){
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
	
	var accountId = "";
	var billMonth = "";
	var billMonthSql= "";
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
		alert("onChange--------->accountId="+accountId+"&billMonthSql="+billMonthSql+"&cbId="+cbId+"&serviceType="+serviceType);
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

                 // create DropDownList from input HTML element
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
//alert(filterField+"::"+filterValue);
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
	
	function sendIndividualMail() {
		
	/* 	window.location.href="./consolidate/exportConsolidatedBill?action=single""+cbId; */
	var result=securityCheckForActionsForStatus("./BillGeneration/ViewBills/sendBillButton"); 
	if(result=="success")
	{  
		$.ajax({
			type : "GET",
			url : "./consolidate/sendConsolidatedBill?accountId="+accountId+"&billMonthSql="+billMonthSql+"&cbId="+cbId,
			data : {
				action : "single"
			},
			dataType : "text",
			success : function(response) {
				alert("Mail Sent Successfully.");
				
			}
		});
     }
	}
	
	function sendAllIndividualMail(){

	var result=securityCheckForActionsForStatus("./BillGeneration/ViewBills/sendAllBillsButton"); 
		  if(result=="success"){  
			
		$.ajax({
			type : "GET",
			url : "./consolidate/sendAllConsolidatedBill?accountId="+accountId+"&billMonthSql="+billMonthSql+"&cbId="+cbId+"&serviceType="+serviceType,
			data : {
				action : "single"
			},
			dataType : "text",
			success : function(response) {
				alert("Mail Sent Successfully.");
				window.location.reload();
			}
		});
		  }
	}
	
	function sendMultipleMail() {
var  VersionIdArray=[];
var cbIds="";
		
		/* var entityGrid = $("#consolidatedGrid").data("kendoGrid");
		var selectedItem = entityGrid.dataItem($(this));
		//accessing selected rows data 
		alert(selectedItem.accountId);
		var data = entityGrid.dataSource.data();
		var totalNumber = data.length;

		for(var i = 0; i<totalNumber; i++) {
		    var currentDataItem = data[i];
		  VersionIdArray[i] = currentDataItem.accountId;
		  alert(VersionIdArray[i]);
		} */
		
		var accountIds = "";
		var grid = $("#consolidatedGrid").data("kendoGrid");
		/* var dataItem = grid.dataItem($(this));
		var accId=dataItem.accountId;
		alert(accId);
 */		grid.select().each(function() {
			var dataItem = grid.dataItem($(this));
			accountIds += dataItem.accountId + ",";
			cbIds += dataItem.cbId + ",";
			
			
		});
		
		var result=securityCheckForActionsForStatus("./BillGeneration/ViewBills/sendMailButton"); 
		  if(result=="success"){  
		$.ajax({
			type : "POST",
			url : "./consolidate/sendConsolidatedBill?accountIds="+accountIds+"&billMonthSql="+billMonthSql+"&cbIds="+cbIds,
			data : {
				action : "multiple"
			},
			dataType : "text",
			success : function(response) {
				alert("Mail Sent Successfully");
			}
		});
		  }
	}
	
	
	function clearFilter() {
    	    var gridData = $("#consolidatedGrid").data("kendoGrid");
    	    gridData.dataSource.filter({});
    }
	
	function  downloadMaserCB(){
		
		$.ajax({
			type : "GET",
			url : './conslidated/getMaster?accountId='+accountId+"&cbId="+cbId,
			dataType : "text",
			success : function(response) {
				
				$('#billMasterTable').html(response);
				
				
				/* var svg = canvg(document.getElementById('canvas'),
						getSVG(), {});

				function getSVG() {
					var chart = $('#container').highcharts();
					var svg = chart.getSVG();
					return svg;
				}
 */
				var canvas1 = document.getElementById("canvas");
				var url = canvas1.toDataURL();
				var image1 = new Image();
				image1.src = url;
				/* image1.width = "300";
				image1.height = "320"; */
				$('#container').html("");
				$('#container').html(
						'<img style="vertical-align:middle" height="80%" width="100%" src="'
								+ url + '"/>');
				$('#canvas').remove();
				
				html2canvas([ document.getElementById('tabs') ], {
					onrendered : function(canvas) {
						var doc = new jsPDF();
						doc.addImage(canvas.toDataURL("image/jpeg"),
								"jpeg", 10, 10, 190, 250);
						window.open(doc.output("datauristring"),'_blank');

						doc.save('test_detailedbill.pdf');
						$('#billAllChildTable').hide();
					}
				});
				
				
				
				
			}
		});
		
	}
	
	
	function viewMasterBill(){
		var result=securityCheckForActionsForStatus("./BillGeneration/ViewBills/consolidateBillButton"); 
		  if(result=="success"){ 
		$.ajax({
			type : "GET",
			url : './conslidated/getMaster?accountId='+accountId+"&billMonthSql="+billMonthSql+"&cbId="+cbId,
			dataType : "text",
			success : function(response) {
				
				$('#billMasterTable').html(response);
				
			
				var eleamt =parseInt($('#eleamt').text());
				var gasamt = parseInt($('#gasamt').text());
				var wateramt = parseInt($('#wateramt').text());
				var swamt = parseInt($('#swamt').text());
				var teleamt = parseInt($('#internetamt').text());
				var otamt = parseInt($('#commonamt').text());
				var camamt = parseInt($('#camamt').text());
			
				var chart = new Highcharts.Chart({
					colors : [ "#7cb5ec", "#f7a35c", "#90ee7e", "#7798BF", "#aaeeee",
							"#ff0066","#FFFF33", "#eeaaee", "#55BF3B", "#DF5353", "#7798BF",
							"#aaeeee"],
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
								[ 'Water',wateramt],
								[ 'Solid Waste', swamt ],
								[ 'Gas', gasamt ],
								[ 'Telephone', teleamt ],
								[ 'Others', otamt ],
								[ 'CAM', camamt ] ]
					} ],
					navigation : {
						buttonOptions : {
							enabled : false
						}
					}
				});
				
				
				var wnd2 = $("#billMasterTable").kendoWindow({
				    visible:false,
				    resizable: false,
				    modal: true,
				    actions: ["Custom", "Minimize", "Maximize", "Close"],
	                title: "Bill"
				}).data("kendoWindow");
				
				wnd2.center().open();
				
				var kendoWindow = $("#billMasterTable").data("kendoWindow");
				kendoWindow.wrapper.find(".k-i-custom").addClass('icon-printer').addClass('fa').removeClass('k-icon').removeClass('k-i-custom');
				$('.fa').html('');
				$("#billMasterTable").data("kendoWindow").wrapper.find(".icon-printer").click(function(e){
						var prtContent = document.getElementById('billMasterTable');
			            var WinPrint = window.open('', '', 'letf=0,top=0,width=400,height=400,toolbar=0,scrollbars=0,status=0');
			            WinPrint.document.write(prtContent.innerHTML);
			            WinPrint.document.close();
			            WinPrint.focus();
			            WinPrint.print();
			            WinPrint.close();
				}); 
				
				$("#billMasterTable").data("kendoWindow").wrapper.find(".k-i-seek-s").click(function(e){
	                 
	                 var svg = canvg(document.getElementById('canvas'),
								getSVG(), {});

						function getSVG() {
							var chart = $('#syed').highcharts();
							var svg = chart.getSVG();
							return svg;
						}
						
						var canvas1 = document.getElementById("canvas");
						var url = canvas1.toDataURL();
						var image1 = new Image();
						image1.src = url;
						/* image1.width = "300";
						image1.height = "320"; */
						$('#syed').html("");
						$('#syed').html(
								'<img style="vertical-align:middle" height="80%" width="100%" src="'
										+ url + '"/>');
						$('#canvas').remove();
						
						html2canvas([ document.getElementById('tabs') ], {
							onrendered : function(canvas) {
								var doc = new jsPDF();
								doc.addImage(canvas.toDataURL("image/jpeg"),
										"jpeg", 10, 10, 190, 250);
								//window.open(doc.output("datauristring"),'_blank');

								doc.save(" Bill - "+accountId+".pdf");
								wnd2.close();
								$('#canvas').hide();
							}
						});    
	                 e.preventDefault();
	             });
			}
		});
		  }
	}
	
	
	function viewChildBill(){
		var result=securityCheckForActionsForStatus("./BillGeneration/ViewBills/individualBillButton"); 
		  if(result=="success"){  
		$.ajax({
			type : "GET",
			url : './conslidated/getChildCount?accountId='+accountId+"&billMonthSql="+billMonthSql+"&cbId="+cbId,
			dataType : "json",
			success : function(response) {
				var htmlCode = '<table style="width:100%">';
				for(var i=1;i<= response.length;i++){
					var obj = response[i-1];
					
					var billMonth = obj.billMonth;
					var billMonthFormat = $.datepicker.formatDate('MM, yy', new Date(billMonth));
					if(i%2==0){
						htmlCode+="<td width='50%' id='hovertd'><h1><a href='#' onclick='openBillDetail("+obj.elBillId+")'>"+obj.typeOfService+"</a></h1><br> <b>Amount </b>:"+obj.billAmount+" <del>&#2352;</del> <br><b>Month</b> "+billMonthFormat+"</td></tr>";
					}else{
						htmlCode+="<tr id='divId"+i+"'><td id='hovertd' width='50%'><h1><a href='#' onclick='openBillDetail("+obj.elBillId+")'>"+obj.typeOfService+"</a></h1><br> <b>Amount </b>:"+obj.billAmount+"  <del>&#2352;</del><br><b>Month</b> "+billMonthFormat+"</td>";
					}
				}
				htmlCode+="</table>";
				
				$('#billChildTable').html(htmlCode);
				var wnd3 = $("#billChildTable").kendoWindow({
				    visible:false,
				    resizable: false,
				    modal: true,
				    actions: ["Custom", "Minimize", "Maximize", "Close"],
	                title: "Bill"
				}).data("kendoWindow");
				
				wnd3.center().open();
				
				var kendoWindow = $("#billChildTable").data("kendoWindow");
				kendoWindow.wrapper.find(".k-i-custom").addClass('icon-printer').addClass('fa').removeClass('k-icon').removeClass('k-i-custom');
				$('.fa').html('');
				$("#billChildTable").data("kendoWindow").wrapper.find(".icon-printer").click(function(e){
						var prtContent = document.getElementById('billChildTable');
			            var WinPrint = window.open('', '', 'letf=0,top=0,width=400,height=400,toolbar=0,scrollbars=0,status=0');
			            WinPrint.document.write(prtContent.innerHTML);
			            WinPrint.document.close();
			            WinPrint.focus();
			            WinPrint.print();
			            WinPrint.close();
				});
				
				$("#billChildTable").data("kendoWindow").wrapper.find(".k-i-seek-s").click(function(e){
					//alert("click");
					$.ajax({
						type : "GET",
						url : './conslidated/getAllChild?accountId='+accountId+"&billMonthSql="+billMonthSql+"&cbId="+cbId,
						dataType : "text",
						success : function(response) {
							
							var res = response.split("---"); 
							
							$('#billAllChildTable').html(res[0]);

					
							//alert("ll");
							var doc = new jsPDF();
							var i=0;
						 	for(i=0;i<=res[1];i++){ 
								
						 		// alert(i);
						 		alert("Continue...");
							html2canvas([ document.getElementById('tabs'+i) ], {
								onrendered : function(canvas) {
									//window.open(doc.output("datauristring"),'_blank');
									//alert(doc.internal.pageSize.height);
									
									//pageHeight= doc.internal.pageSize.height;

									// Before adding new content
								//	y = 250;// Height position of new content
									////if (y <= pageHeight)
										
								/* 		function partA() {
  						
 									 		window.setTimeout(partB,1000);
										}

										function partB() { */
											//alert("??");
											doc.addImage(canvas.toDataURL("image/jpeg"),
													"jpeg", 10, 10, 190, 250);
										//}
									//{
									
									
									//  y = 0; // Restart height position
									//}
									
								
									doc.addPage();
									/* doc.addImage(canvas.toDataURL("image/jpeg"),
											"jpeg", 10, 10, 190, 250);
									 */
									
									

									
								}
							});  
							
							 } 
						 	
						 	doc.save(" Bill - "+accountId+".pdf");
							
							
							
							wnd3.close();
							$('#billAllChildTable').hide();
		                		 e.preventDefault();
							
							
							
							
						}
					});
				});
			}
		});
		  }
		
	}
	
	var flag = 0;
	var months=[];
	var values=[];
	
	    var janCredit = "";
	    var febCredit = "";
	    var marCredit = "";
	    var aprCredit = "";
	    var mayCredit = "";
	    var junCredit = "";
	    var julCredit = "";
	    var augCredit = "";
	    var septCredit = "";
	    var octCredit = "";
	    var novCredit = "";
	    var decCredit = "";
	   
	    var janMonth = "";
	    var febMonth = "";
	    var marMonth = "";
	    var aprMonth = "";
	    var mayMonth = "";
	    var junMonth = "";
	    var julMonth = "";
	    var augMonth = "";
	    var septMonth = "";
	    var octMonth = "";
	    var novMonth = "";
	    var decMonth = "";
	
	function openBillDetail(elBillId){
		
/* 	 $.ajax({
			type : "GET",
			dataType : "text",
			url : "./bill/getbilltable",
			data : {
				elBillId : elBillId
			},
			success : function(response) {
				$('#billChildTable').html(response);
				var wnd3 = $("#billChildTable").kendoWindow({
				    visible:false,
				    resizable: false,
				    modal: true,
				    actions: ["Minimize", "Maximize", "Close"],
	                title: "Bill"
				}).data("kendoWindow");
				
				wnd3.center().open();
			}
		});  */
		window.open("./bill/getbilltablePDF/" + elBillId);
		 
		/* $.ajax({
			type : "POST",
			url : "./bill/getbilltable",
			 data : {
				 elBillId : elBillId
			}, 
			dataType : "json",
	        async : false,
			success : function(response) {
				 $.map( response, function( value, key ) {
					if(key!='string')
						{
			              if(key == 'Jan'){
			            	  janCredit = value;
			            	  janMonth=key;
			              }
			              if(key == 'Feb'){
			            	  febCredit = value;
			            	  febMonth=key;
			              }
			              if(key == 'Mar'){
			            	  marCredit = value;
			            	  marMonth=key;
			              }
			              if(key == 'Apr'){
			            	  aprCredit = value;
			            	  aprMonth=key;
			              }
			              if(key == 'May'){
			            	  mayCredit = value;
			            	  mayMonth=key;
			              }
			              if(key == 'Jun'){
			            	  junCredit = value;
			            	  julMonth=key;
			              }
			              if(key == 'Jul'){
			            	  julCredit = value;
			            	  julMonth=key;
			              }
			              if(key == 'Aug'){
			            	  augCredit = value;
			            	  augMonth=key;
			              }
			              if(key == 'Sept'){
			            	  septCredit = value;
			            	  septMonth = key;
			              }
			              if(key == 'Oct'){
			            	  octCredit = value;
			            	  octMonth=key;
			              }
			              if(key == 'Nov'){
			            	  novCredit = value;
			            	  novMonth = key;
			              }
			              if(key == 'Dec'){
			            	  decCredit = value;
			            	  decMonth = key;
			              }	
						}
					});
				
				$("#billTable").html(response.string);

				 var chart = new Highcharts.Chart({
					chart : {
						renderTo : 'container',
						type : 'column',
						width:560,
						hight:200,
					},
					credits : {
						enabled : false
					},
					title : {
						text : 'Last six month bills amount',
						align : 'center',
						verticalAlign : 'middle',
						y : -165
					},
					 yAxis: [{
				            min: 0,
				            title: {
				                text: 'Amount in rupees'
				            }
				        }],
					xAxis : {
						 categories: [janMonth,febMonth,marMonth,aprMonth,mayMonth,junMonth,julMonth,augMonth,septMonth,octMonth,novMonth,decMonth],
						  title: {
				                text: 'Months'
				            }
					},

					plotOptions : {
						series : {
							cursor : 'pointer',
							dataLabels : {
								enabled : false
							},
							showInLegend : true
						}
					},

					series : [ {
						data : [janCredit, febCredit, marCredit,aprCredit, mayCredit, junCredit,julCredit, augCredit, septCredit,octCredit,novCredit,decCredit],
						name:"Months"
					} ]
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
					
					var kendoWindow = $("#billTable").data("kendoWindow");
					kendoWindow.wrapper.find(".k-i-custom").addClass('icon-printer').addClass('fa').removeClass('k-icon').removeClass('k-i-custom');
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
					
					
					 canvg(document.getElementById('canvas'), chart.getSVG());
			          var canvas = document.getElementById("canvas");
			          var img = canvas.toDataURL("image/png");
			          img = img.replace('data:image/png;base64,', '');
			          
			          $('#chart').attr('src', img); 
			          $('#chart').hide; 
			          $('#canvas').hide; 
			           var data = "bin_data=" + img;
			              $.ajax({
			                type: "POST",
			                url : "./bill/storeImage",
			                data: data,
			                success: function(data){
			                  alert('success');
			                }
			              });
					 
					 // URL to Highcharts export server
				       var exportUrl = 'http://export.highcharts.com/';

				       // POST parameter for Highcharts export server
				       var object = {
				           options: JSON.stringify(options),
				           type: 'image/png',
				           async: true
				       };

				       // Ajax request
				       $.ajax({
				           type: 'post',
				           url: exportUrl,
				           data: object,
				           success: function (data) {
				               // Update "src" attribute with received image URL
				               $('#chart').attr('src', exportUrl + data);
				           }
				       });
					
					
				}
			});  */
}
//window.open("./detailedbill?accid="+accid+"&typeOfService="+typeOfService,"_blank");

			
		
	
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