<%-- <%@include file="/common/taglibs.jsp"%> --%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="kendo" uri="/WEB-INF/lib/kendo-taglib-2013.3.1427.jar"%>
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


<br>
<br>
<button id="buttonExport" class="k-button">Export Bill</button>
<br>
<br>
<br>

<div id="myTab">
	<table id="tabs"
		style="width: 750px; background: white; border: 2px solid black; border-radius: 34px; padding: 21px 23px;">

		<tr>
			<td width="50%" colspan="2" style="padding: 0.5em; border: 1px solid #808080;" ><img id='eye'
				src='./resources/images/iREO Logo.jpg' height="100px" width="300px" /></td>

			<td style="padding: 0.5em; border: 1px solid #808080;" width="49%">Orchid Centre, DLF Golf Course Rd,<br> IILM
				Institute, Sector 53, <br>Gurgaon, Haryana<br> 0124 475
				4000
			</td>
		</tr>
		<tr>
			<td colspan="3"
				style="background: black; color: white; font-weight: bolder; padding: 0.5em; border: 1px solid #808080;">Customer
				Details</td>
		</tr>
		<tr>
			<td width="49%" style="padding-left: 25px;padding: 0.5em; border: 1px solid #808080;"> <b>
					<h4 id='name'>
						<c:out value="${person.firstName}" />
						<c:out value="${person.lastName}" />
					</h4>

			</b> <span id="addr"><c:out value="${address.address1}" /></span>
				<br> <span id="email"><c:out
						value="${email.contactContent}" /></span><br> <span id="mobile"><c:out
						value="${mobile.contactContent}" /></span><br></td>
			<td colspan="2"
				style="vertical-align: middle; border-left: 2px solid; padding: 0.5em; border: 1px solid #808080;">

				<table style="width: 100%;">
					<tr>
						<td style="padding: 0.5em; border: 1px solid #808080;" align="center"><b>Account Number</b></td>
						<td style="padding: 0.5em; border: 1px solid #808080;" id="accno"><c:out value="${account.accountNo}" /></td>
					</tr>
					<tr>
						<td style="padding: 0.5em; border: 1px solid #808080;" align="center"><b>Service Type</b></td>
						<td style="padding: 0.5em; border: 1px solid #808080;" id="prdFrom"><c:out value="${bill.typeOfService}" /></td>
					</tr>

				</table>
			</td>
		</tr>

		<tr style="background-color: black">
			<td style="padding: 0.5em; border: 1px solid #808080;" colspan="3"
				style="backgound: black; color: white; font-weight: bolder;">Parameters</td>
		</tr>

		<tr>

			<td style="padding: 0.5em; border: 1px solid #808080;" colspan="3">

				<table style="width: 100%; text-align: center;">
					<tr>
						<th style="padding: 0.5em; border: 1px solid #808080;">Service Parameters</th>
						<th style="padding: 0.5em; border: 1px solid #808080;">Meter Parameters</th>
						<th style="padding: 0.5em; border: 1px solid #808080;">Bill Parameters</th>

					</tr>
					<tr>
						<th style="padding: 0.5em; border: 1px solid #808080;">
							<table>
								<tr>
									<th style="padding: 0.5em; border: 1px solid #808080;">Name</th>
									<th style="padding: 0.5em; border: 1px solid #808080;">Value</th>
								</tr>
								<c:forEach var="sp" items="${serviceparameter}" varStatus="status">
									<tr>
										<td style="padding: 0.5em; border: 1px solid #808080;">${sp.name}</td>
										<td style="padding: 0.5em; border: 1px solid #808080;">${sp.value}</td>
									</tr>
								</c:forEach>
							</table>
						</th>
						<th style="padding: 0.5em; border: 1px solid #808080;">
							<table>
								<tr>
									<th style="padding: 0.5em; border: 1px solid #808080;">Name</th>
									<th style="padding: 0.5em; border: 1px solid #808080;">Value</th>
								</tr>
								<c:forEach var="mp" items="${meterparameter}" varStatus="status">
									<tr>
										<td style="padding: 0.5em; border: 1px solid #808080;">${mp.name}</td>
										<td style="padding: 0.5em; border: 1px solid #808080;">${mp.value}</td>
									</tr>
								</c:forEach>
							</table>
						</th>
						<th style="padding: 0.5em; border: 1px solid #808080;">
							<table>
								<tr>
									<th style="padding: 0.5em; border: 1px solid #808080;">Name</th>
									<th style="padding: 0.5em; border: 1px solid #808080;">Value</th>
								</tr>
								<c:forEach var="bp" items="${billparameter}" varStatus="status">
									<tr>
										<td style="padding: 0.5em; border: 1px solid #808080;">${bp.name}</td>
										<td style="padding: 0.5em; border: 1px solid #808080;">${bp.value}</td>
									</tr>
								</c:forEach>
							</table>
						</th>
					</tr>

				</table>
			</td>
		</tr>

		<tr>
		<tr style="background-color: black">
			<td colspan="3"
				style="backgound: black; color: white; font-weight: bolder;padding: 0.5em; border: 1px solid #808080;">Bill Segments</td>
		</tr>

		<tr>
			<td style="padding: 0.5em; border: 1px solid #808080;" colspan="3">
				<table style="width: 100%;">
					<tr>

						<td style="padding: 0.5em; border: 1px solid #808080;" width="40%">
							<div id="container" style="height: 200px; max-height: 200px; width: 350px; max-width: 350px"></div>
						</td>
						<td style="padding: 0.5em; border: 1px solid #808080;" width="60%">

							<table>
								<tr>
									<th style="padding: 0.5em; border: 1px solid #808080;" align="center">Transaction Code</th>
									<th style="padding: 0.5em; border: 1px solid #808080;" align="center">Amount</th>
								</tr>
								<c:forEach var="li" items="${lineitems}" varStatus="status">

									<tr>
										<td style="padding: 0.5em; border: 1px solid #808080;" align="center">${li.transactionCode}</td>

										<td style="padding: 0.5em; border: 1px solid #808080;" align="right">${li.balanceAmount}</td>

									</tr>

								</c:forEach>
								<tr>
									<th style="padding: 0.5em; border: 1px solid #808080;">TOTAL</th>
									<th style="padding: 0.5em; border: 1px solid #808080;" align="right"><c:out value="${bill.billAmount}" /><br><input type="hidden" id="h-elBillId" value="${bill.elBillId}"></th>
								</tr>

							</table>
						</td>
					</tr>
				</table>
				</td>
				</tr>
				
				<tr>
		<td style="padding: 0.5em; border: 1px solid #808080; height: 100px" colspan="3">
		<b>Message</b>
		</td>
		</tr>
				</table>
				
			
				
</div>

<canvas id="canvas" height="400px" width="300px" style="width: 300px; height: 400px;"></canvas>
<script>
	var electricityAmt = 0.0;
	var waterAmt = 0.0;
	var gasAmt = 0.0;
	var internetAmt = 0.0;
	var telephoneAmt = 0.0;
	var otherAmt = 0.0;

	$(document)
			.ready(
					function() {

						

						alert("Click to continue");
						var chart = new Highcharts.Chart({
							chart : {
								renderTo : 'container',
								type : 'column'
							},
							credits : {
								enabled : false
							},
							title : {
								text : ' ',
								align : 'center',
								verticalAlign : 'middle',
								y : -165
							},
							xAxis : {
								categories : [ 'Mar', 'Apr', 'May', 'Jun',
										'Jul', 'Aug' ]
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
								data : [ 2, 5, 10, 20, 30, 100.4 ],
								name : 'Amount Graph',
							} ]
						});

						$('#buttonExport').click(
								function() {
									
									var elBillId = $('#h-elBillId').val();
									alert($('#h-elBillId').val());
									
									$.ajax({
							            type: "GET",
							            url: "./bill/maildetailedbill?elBillId="+elBillId, 
							            dataType: "text",
							            success: function(data){
							                alert("Success: "+data.success);
							            } 
							       }); 
									
									var svg = canvg(document.getElementById('canvas'),
											getSVG(), {});

									function getSVG() {
										var chart = $('#container').highcharts();
										var svg = chart.getSVG();
										return svg;
									}

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
											$('#canvas').hide();
										}
									});
								});
					});
</script>

<style>
body {
	color: black;
}

td {
	vertical-align: top;
	font-weight: normal;
}

/* Housekeeping */
html {
	font: 0.75em/1.5 sans-serif;
	color: #000000;
	background-color: #fff;
	padding: 1em;
}

/* Tables */
table {
	width: 100%;
	margin-bottom: 1em;
}

th {
	font-weight: bold;
}
/* 
th,td {
	padding: 0.5em;
	border: 1px solid #808080;
} */

/* Table sizing */
.t10 {
	width: 10%
}

.t20 {
	width: 20%
}

.t25 {
	width: 25%
}

.t30 {
	width: 30%
}

.t33 {
	width: 33.333%
}

.t40 {
	width: 40%
}

.t50 {
	width: 50%
}

.t60 {
	width: 60%
}

.t66 {
	width: 66.666%
}

.t70 {
	width: 70%
}

.t75 {
	width: 75%
}

.t80 {
	width: 80%
}

.t90 {
	width: 90%
}

.canvas {
	height: 400px;
	width: 300px;
}

.k-animation-container {
	top: 292px;
	left: 140px;
}
</style>