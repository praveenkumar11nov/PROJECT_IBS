<%@include file="/common/taglibs.jsp"%>
<script src="resources/js/plugins/charts/highcharts.js"></script>
<script src="resources/js/plugins/charts/exporting.js"></script>


<div id="super" class="aspectwrapper">
<!-- <div id="container" style="min-width: 310px; height: 512px; margin: -5.3% auto 0px;"></div> -->
<div class="widget" style="padding-bottom: 3%; margin-top: 0%;">
<table>
	<tr><td style="padding-left: 30px;"><div style="width: 269px; height: 166px;">
		<div class="widget" id="super1" style="width: 269px; height: 166px;">
				 <div class="inHead">
                    <img alt="" src="./resources/images/images.png" height="50" width="50"
					style="vertical-align: middle;" /> <span
					style="font-size: 20px; padding-left: 15px;">Owners</span>
                    <span id="star1" hidden="true">
					<img alt="" src="./resources/images/star.png"  height="15" width="15"
					style="vertical-align: right;" />
                    </span>
                </div>

				<div>
					<div style="width: 100%;">
					
						<table>
						<tr><td style="padding-left: 20px;">No Of Owners</td><td style="padding-left: 100px;">${Owner.count}</td></tr>
						<tr><td style="padding-left: 20px;">Active Owners</td><td style="padding-left: 100px;">${Owner.activeCount}</td></tr>
						<tr><td style="padding-left: 20px;">In-Active Owners</td><td style="padding-left: 100px;">${Owner.inActiveCount}</td></tr>
						</table>				
						<span style="padding-left: 34%;"> <a id="owner" href="#" class="buttonM bBrown"><span class="icon-user-5"></span><span>View Report</span></a></span><br>
						<br>
					</div>
				</div>

		
		</div>
	</div></td>
	 <td style="padding-left: 30px;"><div style="width: 27%; height: 13%;">
		<div class="widget" id="super2" style="width: 269px; height: 166px; background-image: url(./resources/images/white.jpg);">

			<div class="inHead">
                    <img alt="" src="./resources/images/staff.png" height="50" width="50"
					style="vertical-align: middle;" /> <span
					style="font-size: 20px; padding-left: 15px;">Staff</span>
                     <span id="star2" hidden="true">
					<img alt="" src="./resources/images/star.png"  height="15" width="15"
					style="vertical-align: right;" />
                    </span>
                </div>

				<div>
					<div style="width: 100%;">
					<table>
						<tr><td style="padding-left: 20px;">No Of Staff </td><td style="padding-left: 120px;">${Staff.count}</td></tr>
						<tr><td style="padding-left: 20px;">Active Staff </td><td style="padding-left: 120px;">${Staff.activeCount}</td></tr>
						<tr><td style="padding-left: 20px;">In-Active Staff</td><td style="padding-left: 120px;">${Staff.inActiveCount}</td></tr>
						</table>
						<span
							style="padding-left: 30%;"> <a id="staff" href="#" class="buttonM bBrown"><span class="icon-user-5"></span><span>View Report</span></a></span><br>
						<br>
					</div>
				</div>

			
		</div>
	</div></td>
	<td style="padding-left: 30px;"><div style="width: 27%; height: 13%;">
		<div class="widget" id="super3" style="width: 269px; height: 166px;">
				 <div class="inHead">
                    <img alt="" src="./resources/images/vendorContracts.png" height="50" width="50"
					style="vertical-align: middle;" /> <span
					style="font-size: 20px; padding-left: 15px;">Vendors</span>
                   <span id="star6" hidden="true">
					<img alt="" src="./resources/images/star.png"  height="15" width="15"
					style="vertical-align: right;" />
                    </span> 
                </div>
				<div>
				<div style="width: 100%; text-align: justify;  text-justify: inter-word;" id="hello6">
						<table>
						<tr><td style="padding-left: 20px;">No Of Vendors</td><td style="padding-left: 100px;">${Vendor.count}</td></tr>
						<tr><td style="padding-left: 20px;">Active Vendors</td><td style="padding-left: 100px;">${Vendor.activeCount}</td></tr>
						<tr><td style="padding-left: 20px;">In-Active Vendors</td><td style="padding-left: 100px;">${Vendor.inActiveCount}</td></tr>
						</table>
						<span
							style="padding-left: 30%;"> <a id="vendor" href="#" class="buttonM bBrown"><span class="icon-user-5"></span><span>View Report</span></a></span><br>
						<br>
					</div>
				</div>

		
		</div>
	</div></td>
	
	<td style="padding-left: 30px;"><div id="hell9"
		style="width: 27%; height: 13%;">
		<div class="widget" id="super9" style="margin-top: -52.4%; width: 269px; height: 166px; background-image: url(./resources/images/white.jpg);">

			<div class="inHead">
                    <img alt="" src="./resources/images/help.png" height="50" width="50"
					style="vertical-align: middle;" /> <span
					style="font-size: 20px; padding-left: 15px;">Help Desk</span>
                     <span id="star9" hidden="true">
					<img alt="" src="./resources/images/star.png"  height="15" width="15"
					style="vertical-align: right;" />
                    </span>
                </div>

				<div>
					<div style="width: 100%;" >
					<table>
						<%-- <tr><td style="padding-left: 20px;">No Of Ticket Raised</td><td style="padding-left: 80px;">${helpDesk.count}</td></tr> --%>
						<br><tr><td style="padding-left: 20px;">Open Tickets</td><td style="padding-left: 80px;">${helpDesk.activeCount}</td></tr>
						<tr><td style="padding-left: 20px;">Re-Opened Ticket</td><td style="padding-left: 80px;">${helpDesk.inActiveCount}</td></tr>
						</table>
					    <span
							style="padding-left: 30%;"> <a id="helpdesk" href="#" class="buttonM bBrown"><span class="icon-user-5"></span><span>View Report</span></a></span><br>
						<br>
					</div>
			

		</div>
	</div>


</div></td></tr> 
	<%-- <div style="padding-left: 797px; width: 360px; height: 100px;">
		<div class="widget" id="super3" style="margin-top: -100px; width: 100%; height: 211%;">

			<div class="inHead">
                    <img alt="" src="./resources/images/owners.png" height="50" width="50"
					style="vertical-align: middle;" /> <span
					style="font-size: 20px; padding-left: 15px;">Owners</span>
                    
                </div><br>
				<div>
					<div style="width: 100%;">
						<span style="padding-left: 8%;">No Of Owners</span><span
							style="padding-left: 175px;">${Owner.count }</span><br> <span
							style="padding-left: 8%;">Active Owners</span><span
							style="padding-left: 173px;">${Owner.activeCount}</span><br> <span
							style="padding-left: 8%;">In-Active Owners</span><span
							style="padding-left: 161px;">${Owner.inActiveCount}</span><br> <br> <span
							style="padding-left: 137px;"> <a href="#" class="buttonM bBrown"><span class="icon-user-5"></span><span>View Report</span></a></span><br>
						<br>
					</div>
				</div>

			
		</div>
	</div> --%>

	<tr><td style="padding-left: 30px;"><div style="width: 27%; height: 13%;">
		<div class="widget" id="super4" style="width: 269px; height: 166px; background-image: url(./resources/images/white.jpg);">

			<div class="inHead">
                    <img alt="" src="./resources/images/visitors.png" height="50" width="50"
					style="vertical-align: middle;" /> <span
					style="font-size: 20px; padding-left: 15px;">Gate Keeper</span>
                      <span id="star4" hidden="true">
					<img  alt="" src="./resources/images/star.png"  height="15" width="15"
					style="vertical-align: right;" />
                    </span>
                </div>

				<div>
					<div style="width: 100%;">
					<table>
						<tr><td style="padding-left: 20px;">  </td><td style="padding-left: 109px;">  </td></tr>
						<tr><td style="padding-left: 20px;">Visitors IN'S</td><td style="padding-left: 109px;">${Visitor.visitorOut}</td></tr>
						<tr><td style="padding-left: 20px;"> </td><td style="padding-left: 109px;"> </td></tr><br>
						</table><br>
						<span
							style="padding-left: 30%;"> <a id="visitor" href="#" class="buttonM bBrown"><span class="icon-user-5"></span><span>View Report</span></a></span><br>
						<br>
					</div>
				

			</div>
		</div>
	</div></td>

	<td style="padding-left: 30px;"><div style="width: 27%; height: 13%;">
		<div class="widget" id="super5" style="width: 269px; height: 166px;">

			<div class="inHead">
                    <img alt="" src="./resources/images/icons/mainnav/Bill Generation.png" height="50" width="50"
					style="vertical-align: middle;" /> <span
					style="font-size: 20px; padding-left: 15px;">Billing</span>
                     <span id="star12" hidden="true">
					<img alt="" src="./resources/images/star.png"  height="15" width="15"
					style="vertical-align: right;" />
                    </span>
                </div>

				<div>
					<div style="width: 100%;">
					<table><br>
						<tr><td style="padding-left: 20px;"></td><td style="padding-left: 50px;"> </td></tr>
						<tr><td style="padding-left: 20px;">Number Of Reports</td><td style="padding-left: 97px;">5</td></tr>
						<tr><td style="padding-left: 20px;"></td><td style="padding-left: 50px;"></td></tr>
						</table><br>
						<span
							style="padding-left: 30%;"> <a id="billing" href="#" class="buttonM bBrown"><span class="icon-user-5"></span><span>View Report</span></a></span><br>
						<br>
					</div>
				

			</div>
		</div>
	</div></td>

	<td style="padding-left: 30px;"><div id="hell6"
		style="width: 27%; height: 13%;">
		<div class="widget" id="super6" style="width: 269px; height: 166px; background-image: url(./resources/images/white.jpg);">

			<div class="inHead">
                    <img alt="" src="./resources/images/inventory.png" height="50" width="50"
					style="vertical-align: middle;" /> <span
					style="font-size: 20px; padding-left: 15px;">Assets</span>
                     <span id="star8" hidden="true">
					<img alt="" src="./resources/images/star.png"  height="15" width="15"
					style="vertical-align: right;" />
                    </span>
                </div>

				<div id="hello6">
					<div style="width: 100%;">
					<table>
						<tr><td style="padding-left: 20px;">No Of Assets</td><td style="padding-left: 73px;">${assets.count}</td></tr>
						<tr><td style="padding-left: 20px;">No Of Active Stores</td><td style="padding-left: 73px;">${assets.activeCount}</td></tr>
						<tr><td style="padding-left: 20px;">No Of In-Active Stores</td><td style="padding-left: 73px;">${assets.inActiveCount}</td></tr>
						</table>
						<span
							style="padding-left: 30%;">
							 <a id="asset" href="#" class="buttonM bBrown"><span class="icon-user-5"></span><span>View Report</span></a></span><br>
						<br>
					</div>
			

		</div>
	</div>


</div></td>
<td style="padding-left: 30px;"><div style="width: 27%; height: 13%;">
		<div class="widget" id="super15" style="width: 269px; height: 166px;">
				 <div class="inHead">
                    <img alt="" src="./resources/images/admin.png" height="50" width="50"
					style="vertical-align: middle;" /> <span
					style="font-size: 20px; padding-left: 15px;">Admin Files</span>
                     <span id="star7" hidden="true" >
					<img alt="" src="./resources/images/star.png"  height="15" width="15"
					style="vertical-align: right;" />
                    </span>
                </div>

				<div>
					<div style="width: 100%;">
					<table><br>
						<tr><td style="padding-left: 20px;">Flat Specific Document</td><td style="padding-left: 76px;">${adminFiles.count}</td></tr>
						<%-- <tr><td style="padding-left: 20px;">Active Documents</td><td style="padding-left: 76px;">${adminFiles.activeCount}</td></tr>
						<tr><td style="padding-left: 20px;">In-Active Documents</td><td style="padding-left: 76px;"> ${adminFiles.inActiveCount}</td></tr> --%>
						</table><br>
						<span
							style="padding-left: 30%;"> <a id="adminfile"  href="#" class="buttonM bBrown"><span class="icon-user-5"></span><span>View Report</span></a></span><br>
						<br>
					</div>
				</div>

		
		</div>
	</div></td></tr>
	
	<!--billin report  -->
	<tr><td style="padding-left: 30px;"><div style="width: 27%; height: 13%;">
		<div class="widget" id="super4" style="width: 269px; height: 166px; background-image: url(./resources/images/white.jpg);">

			<div class="inHead">
                    <img alt="" src="./resources/images/visitors.png" height="50" width="50"
					style="vertical-align: middle;" /> <span
					style="font-size: 20px; padding-left: 15px;">Income Tracker</span>
                      <span id="star10" hidden="true">
					<img  alt="" src="./resources/images/star.png"  height="15" width="15"
					style="vertical-align: right;" />
                    </span>
                </div>

				<div>
					<div style="width: 100%;">
					<table>
						<tr><td style="padding-left: 20px;">  </td><td style="padding-left: 109px;">  </td></tr>
						<tr><td style="padding-left: 20px;">No Of Reports</td><td style="padding-left: 109px;">13</td></tr>
						<tr><td style="padding-left: 20px;"> </td><td style="padding-left: 109px;"> </td></tr><br>
						</table><br>
						<span
							style="padding-left: 30%;"> <a id="itracker" href="#" class="buttonM bBrown"><span class="icon-user-5"></span><span>View Report</span></a></span><br>
						<br>
					</div>
				

			</div>
		</div>
	</div></td> 

<!--  General Ledger-->
<td style="padding-left: 30px;"><div id="hell7"
		style="width: 27%; height: 13%;">
		<div class="widget" id="super15" style="width: 269px; height: 166px;">

			<div class="inHead">
                    <img alt="" src="./resources/images/icons/mainnav/Bill Generation.png" height="50" width="50"
					style="vertical-align: middle;" /> <span
					style="font-size: 20px; padding-left: 15px;">General Ledger</span>
                     <span id="star11" hidden="true">
					<img alt="" src="./resources/images/star.png"  height="15" width="15"
					style="vertical-align: right;" />
                    </span>
                </div>

				<div id="hello6">
					<div style="width: 100%;">
					<table>
						<tr><td style="padding-left: 20px;">  </td><td style="padding-left: 109px;">  </td></tr>
						<tr><td style="padding-left: 20px;">No Of Reports</td><td style="padding-left: 109px;">2</td></tr>
						<tr><td style="padding-left: 20px;"> </td><td style="padding-left: 109px;"> </td></tr><br>
						</table><br>
						<span
							style="padding-left: 30%;"> <a id="generalLedger" href="#" class="buttonM bBrown"><span class="icon-user-5"></span><span>View Report</span></a></span><br>
						<br>
					</div>
			

		</div>
	</div>


</div></td>
	
	
<%-- 	
	<!--  Online Transaction-->
<td style="padding-left: 30px;"><div id="hell7"
		style="width: 27%; height: 13%;">
		<div class="widget" id="super15" style="width: 269px; height: 166px;">

			<div class="inHead">
                    <img alt="" src="./resources/images/icons/mainnav/Bill Generation.png" height="50" width="50"
					style="vertical-align: middle;" /> <span
					style="font-size: 20px; padding-left: -30px;">Online Transaction</span>
                     <span id="star12" hidden="true">
					<img alt="" src="./resources/images/star.png"  height="15" width="15"
					style="vertical-align: right;" />
                    </span>
                </div>

				<div id="hello7">
					<div style="width: 100%;">
					<table>
						<tr><td style="padding-left: 10px; margin-bottom: 50px;">No Of Transactions</td><td style="padding-left: 73px;">${transaction.count}</td></tr>
						<tr><td style="padding-left: 10px;">Success Transactions</td><td style="padding-left: 73px;">${transaction.activeCount}</td></tr>
						<tr><td style="padding-left: 10px;">Failure Transactions</td><td style="padding-left: 73px;">${transaction.inActiveCount}</td></tr>
						<tr><td style="padding-left: 20px;"> </td><td style="padding-left: 109px;"> </td></tr><br>
						</table><br>
						<span
							style="padding-left: 5%;"> <a id="paytmId"  href="paytmOnlineTransaction" class="buttonM bBrown"><span class="icon-user-5"></span><span>Paytm</span></a></span>
				 <span style="padding-left: 5%;"> <a id="payuId" href="payUOnlineTransaction" class="buttonM bBrown"><span class="icon-user-5"></span><span>PayU</span></a></span><br>
						
						<br>
					</div>
			

		</div>
	</div>


</div></td>
 --%>


<td style="padding-left: 30px;"><div id="hell7"
		style="width: 27%; height: 13%;">
<div class="widget" id="super15" style="width: 269px; height: 166px; background-image: url(./resources/images/white.jpg);">
			<div class="inHead">
                    <img alt="" src="./resources/images/icons/mainnav/Bill Generation.png" height="50" width="50"
					style="vertical-align: middle;" /> <span
					style="font-size: 20px; padding-left: -30px;">Online Transaction</span>
                     <span id="star12" hidden="true">
					<img alt="" src="./resources/images/star.png"  height="15" width="15"
					style="vertical-align: right;" />
                    </span>
                </div>

				<div id="txnId">
					<div style="width: 100%;">
					<table>
						<tr><td style="padding-left: 20px;">No Of Transactions</td><td style="padding-left: 63px;">${transaction.count}</td></tr>
						<tr><td style="padding-left: 20px;">Success Transactions</td><td style="padding-left: 63px;">${transaction.activeCount}</td></tr>
						<tr><td style="padding-left: 20px;">Failure Transactions</td><td style="padding-left: 63px;">${transaction.inActiveCount}</td></tr>
						</table>
						<span
							style="padding-left: 10%;"> <a id="paytmId"  href="paytmOnlineTransaction" class="buttonM bBrown"><span class="icon-user-5"></span><span>Paytm</span></a></span>
				 <span style="padding-left: 10%;"> <a id="payuId" href="payUOnlineTransaction" class="buttonM bBrown"><span class="icon-user-5"></span><span>PayU</span></a></span>
					</div>
			

		</div>
	</div>

</div></td>
</tr>
	
	


	<!--:::::::::::::::::::::::::::::::::: Billing report end:::::::::::::::::::::::::::::  -->
	
	
	<%-- <div style="padding-left: 34%; width: 27%; height: 13%;">
		<div class="widget" id="super8" style="margin-top: -100px; width: 82%; height: 211%; background-image: url(./resources/images/white.jpg);">

			<div class="inHead">
                    <img alt="" src="./resources/images/inventory.png" height="50" width="50"
					style="vertical-align: middle;" /> <span
					style="font-size: 20px; padding-left: 15px;">Asset & Inventary</span>
                    
                </div><br>

				<div>
					<div style="width: 100%;">
						<span style="padding-left: 8%;">No Of Assets</span><span
							style="padding-left: 175px;">${assets.count}</span><br> <span
							style="padding-left: 8%;">No Of Active Stores</span><span
							style="padding-left: 140px;">${assets.activeCount}</span><br> <span
							style="padding-left: 8%;">No Of In-Active Stores</span><span
							style="padding-left: 126px;">${assets.inActiveCount}</span><br> <br> <span
							style="padding-left: 137px;">
							 <a href="#" class="buttonM bBrown"><span class="icon-user-5"></span><span>View Report</span></a></span><br>
						<br>
					</div>
				

			</div>
		</div>
	</div> --%>
	</table>	
</div>


	                  <c:forEach var="category" items="${navigation.keySet()}">
			       	<c:forEach var="widget" items="${navigation.get(category)}">			       		
			       		<c:if test="${widget.include()}">
				       	<script type="text/javascript">
			 	
				          if("${widget.text}"=="Asset")
				        	  {
				        
				        	  document.getElementById('asset').href = "./comReports?unqId=3";
				        	  $("#star8").show();
				        	  }
				          
				          if("${widget.text}"=="Manpower Resource")
			        	  {
				        	  document.getElementById('staff').href = "./comReports?unqId=5";
			        	  $("#star2").show();
			        	  }
			          
				          if("${widget.text}"=="Parking")
			        	  {
				               document.getElementById('parking').href = "./parkingslots";
			        	  $("#star5").show();
			        	  }
			          
				          if("${widget.text}"=="Visitor")
			        	  {
				        	  document.getElementById('visitor').href = "./comReports?unqId=6";
			        	  $("#star4").show();
			        	  }
			          
				          if("${widget.text}"=="Vendor")
			        	  {
				        	  document.getElementById('vendor').href = "./comReports?unqId=4";
			        	  $("#star6").show();
			        	  }
			          
				          if("${widget.text}"=="Customer Occupancy")
			        	  {
				        	 
				        	  document.getElementById('owner').href = "./comReports?unqId=1";
			        	  $("#star1").show();
			        	  
			        	  
			        	  }
				         
				          
				          if("${widget.text}"=="Customer Care")
			        	  { 
				        	
				        	  document.getElementById('helpdesk').href = "./comReports?unqId=9";
			        	
			        	      $("#star9").show();
			        	  
			        	  }
				          
				          if("${widget.text}"=="System Security")
			        	  { 
				        	
				        	  document.getElementById('adminfile').href = "./comReports?unqId=2";
			        	
			        	      $("#star7").show();
			        	  
			        	  }
			          
				          if("${widget.text}"=="Bill Generation")
			        	  { 
				        	
				        	  document.getElementById('billing').href = "./comReports?unqId=8";
			        	
			        	      $("#star12").show();
			        	  
			        	  }
				          if("${widget.text}"=="Cash Management")
			        	  { 
				        	
				        	  document.getElementById('itracker').href = "./comReports?unqId=10";
			        	
			        	      $("#star10").show();
			        	  
			        	  }
				          if("${widget.text}"=="Accounts")
			        	  { 
				        	
				        	  document.getElementById('generalLedger').href = "./comReports?unqId=11";
			        	
			        	      $("#star11").show();
			        	  
			        	  }
				          
				          
				       	
				       	</script>
				       		
			       		</c:if>
			       	</c:forEach>
        		</c:forEach>
</div>
<div id="alertsBox" title="Alert"></div>  
<script>
$(document).ready(function() {
	
	   $.ajax({
		url : "./dashBoard/ChartDetails",
		type : "GET",
		dataType : "JSON",
		success : function(response) {
			$('#container').highcharts({
		        chart: {
		            type: 'column'
		        },
		        title: {
		            text: 'Monthly Average Property Occupation'
		        },
		        subtitle: {
		            text: ''
		        },
		        xAxis: {
		            categories: [
		                'Jan',
		                'Feb',
		                'Mar',
		                'Apr',
		                'May',
		                'Jun',
		                'Jul',
		                'Aug',
		                'Sep',
		                'Oct',
		                'Nov',
		                'Dec'
		            ],
		            crosshair: true
		        },
		        yAxis: {
		            min: 0,
		            title: {
		                text: 'No of Customers'
		            }
		        },
		        tooltip: {
		            headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
		            pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' +
		                '<td style="padding:0"><b>{point.y:.1f}</b></td></tr>',
		            footerFormat: '</table>',
		            shared: true,
		            useHTML: true
		        },
		        plotOptions: {
		            column: {
		                pointPadding: 0.2,
		                borderWidth: 0
		            }
		        },
		        series: response
		    });
			
		}
	   });
});

$("#owner").click(function () { 
	   
	var x=$('#owner').attr('href');
	if(x=="#")
		{
		$("#alertsBox").html("");
			$("#alertsBox").html("Access Denied");
			$("#alertsBox").dialog({
				modal : true,
				buttons : {
					"Close" : function() {
						$(this).dialog("close");
					}
				}
			});
		
		return false;
		}

});

$("#asset").click(function () { 
	   
	var x=$('#asset').attr('href');
	if(x=="#")
		{
		$("#alertsBox").html("");
		$("#alertsBox").html("Access Denied");
		$("#alertsBox").dialog({
			modal : true,
			buttons : {
				"Close" : function() {
					$(this).dialog("close");
				}
			}
		});
		return false;
		}

});
$("#vendor").click(function () { 
	   
	var x=$('#vendor').attr('href');
	if(x=="#")
		{
		$("#alertsBox").html("");
		$("#alertsBox").html("Access Denied");
		$("#alertsBox").dialog({
			modal : true,
			buttons : {
				"Close" : function() {
					$(this).dialog("close");
				}
			}
		});
		return false;
		}

});
$("#visitor").click(function () { 
	   
	var x=$('#visitor').attr('href');
	if(x=="#")
		{
		$("#alertsBox").html("");
		$("#alertsBox").html("Access Denied");
		$("#alertsBox").dialog({
			modal : true,
			buttons : {
				"Close" : function() {
					$(this).dialog("close");
				}
			}
		});
		return false;
		}

});
$("#parking").click(function () { 
	   
	var x=$('#parking').attr('href');
	
	if(x=="#")
		{
		$("#alertsBox").html("");
		$("#alertsBox").html("Access Denied");
		$("#alertsBox").dialog({
			modal : true,
			buttons : {
				"Close" : function() {
					$(this).dialog("close");
				}
			}
		});
		return false;
		}

});
$("#staff").click(function () { 
	   
	var x=$('#staff').attr('href');
	if(x=="#")
		{
		$("#alertsBox").html("");
		$("#alertsBox").html("Access Denied");
		$("#alertsBox").dialog({
			modal : true,
			buttons : {
				"Close" : function() {
					$(this).dialog("close");
				}
			}
		});
		return false;
		}

});
$("#helpdesk").click(function () { 
	   
	var x=$('#helpdesk').attr('href');
	if(x=="#")
		{
		$("#alertsBox").html("");
		$("#alertsBox").html("Access Denied");
		$("#alertsBox").dialog({
			modal : true,
			buttons : {
				"Close" : function() {
					$(this).dialog("close");
				}
			}
		});
		return false;
		}

});
$("#adminfile").click(function () { 
	   
	var x=$('#adminfile').attr('href');
	if(x=="#")
		{
		$("#alertsBox").html("");
		$("#alertsBox").html("Access Denied");
		$("#alertsBox").dialog({
			modal : true,
			buttons : {
				"Close" : function() {
					$(this).dialog("close");
				}
			}
		});
		return false;
		}

});




/* $("#super1").on("mouseover", function(){
	 $("#super1").fadeIn();
}); */
	/* $('#hello6 ').click(function(e) {
	 var block = $(e.target);
	 var color = $.Color(block.css('backgroundColor'));
	 block.animate({backgroundColor: color.lightness('+=0.4')}, 300, function() {
	 block.animate({backgroundColor: color}, 300);
	 });
	 }); */
	/* (function animate() {
	 var block = $('#super');
	 block.animate({backgroundColor: $.Color(block.css('backgroundColor')).hue('+=179')}, 3000, animate);
	 })(); */
	
	 
	/*  function clickPay(param)
	 { alert(param);
		
		  $.ajax({
				url : "./onlineTransactionNew/"+param+"",
				type : "GET",
				dataType : "JSON",
				success : function(response) {
					alert(response);					
				}
			   });
		 
	 } */
</script>
<style>
 .inHead {
    background: -moz-linear-gradient(center top , #C2C0C0 0%, #E8E8E8 77%) repeat scroll 0px 0px transparent;
    border-color: #B5B5B7;
    border-radius: 20px 20px 0px 0px;
    border-style: solid;
    border-width: 1px;
} 
#super1 {
background: #ffffff;
border-left: 1px solid #B5B5B7;
    border-right: 1px solid #B5B5B7;
    border-top: 1px solid #B5B5B7;
    border-bottom: 1px solid #B5B5B7;
   border-radius: 20px 20px 20px 20px;
}
#super2 {
background: #ffffff;
border-left: 1px solid #B5B5B7;
    border-right: 1px solid #B5B5B7;
    border-top: 1px solid #B5B5B7;
    border-bottom: 1px solid #B5B5B7;
   border-radius: 20px 20px 20px 20px;
}
#super3 {
background: #ffffff;
border-left: 1px solid #B5B5B7;
    border-right: 1px solid #B5B5B7;
    border-top: 1px solid #B5B5B7;
    border-bottom: 1px solid #B5B5B7;
   border-radius: 20px 20px 20px 20px;
}
#super4 {
background: #ffffff;
border-left: 1px solid #B5B5B7;
    border-right: 1px solid #B5B5B7;
    border-top: 1px solid #B5B5B7;
    border-bottom: 1px solid #B5B5B7;
   border-radius: 20px 20px 20px 20px;
}
#super5 {
background: #ffffff;
border-left: 1px solid #B5B5B7;
    border-right: 1px solid #B5B5B7;
    border-top: 1px solid #B5B5B7;
    border-bottom: 1px solid #B5B5B7;
   border-radius: 20px 20px 20px 20px;
}
#super5 {
background: #ffffff;
border-left: 1px solid #B5B5B7;
    border-right: 1px solid #B5B5B7;
    border-top: 1px solid #B5B5B7;
    border-bottom: 1px solid #B5B5B7;
   border-radius: 20px 20px 20px 20px;
}
#super6 {
background: #ffffff;
border-left: 1px solid #B5B5B7;
    border-right: 1px solid #B5B5B7;
    border-top: 1px solid #B5B5B7;
    border-bottom: 1px solid #B5B5B7;
   border-radius: 20px 20px 20px 20px;
}
#super7 {
background: #ffffff;
border-left: 1px solid #B5B5B7;
    border-right: 1px solid #B5B5B7;
    border-top: 1px solid #B5B5B7;
    border-bottom: 1px solid #B5B5B7;
   border-radius: 20px 20px 20px 20px;
}
#super15 {
background: #ffffff;
border-left: 1px solid #B5B5B7;
    border-right: 1px solid #B5B5B7;
    border-top: 1px solid #B5B5B7;
    border-bottom: 1px solid #B5B5B7;
   border-radius: 20px 20px 20px 20px;
}
#super8 {
background: #ffffff;
border-left: 1px solid #B5B5B7;
    border-right: 1px solid #B5B5B7;
    border-top: 1px solid #B5B5B7;
    border-bottom: 1px solid #B5B5B7;
   border-radius: 20px 20px 20px 20px;
}
#super9 {
background: #ffffff;
border-left: 1px solid #B5B5B7;
    border-right: 1px solid #B5B5B7;
    border-top: 1px solid #B5B5B7;
    border-bottom: 1px solid #B5B5B7;
   border-radius: 20px 20px 20px 20px;
}

 .buttonM {
    padding: 6px 13px;
     border-radius: 20px 20px 20px 20px;
} 
body {
    color: #000000;
}
.bDefault, .dualBtn, .searchLine button {
    border: 1px solid #181515;
    box-shadow: 0px 1px 2px #FFF inset;
    color: #000;
    text-shadow: none;
    background: -moz-linear-gradient(center top , #F8F8F8 0%, #E8E8E8 100%) repeat scroll 0% 0% transparent;
}
#grandArch {
    padding-left: 1px !important;
}
#content {
    padding-bottom: 2px;
    position: relative;
    margin-left: 100px;
    padding-top: 49px;
}
b {
    font-size: 13px;
    color: #000;
    font-family: Arial,Helvetica,sans-serif;
}
#star1 {
   
    float: right;
    margin-right: 3%;
    margin-top: 6%;
}
#star2 {
   
    float: right;
    margin-right: 3%;
    margin-top: 6%;
}
#star3 {
  
    float: right;
    margin-right: 3%;
    margin-top: 6%;
}
#star4 {
  
    float: right;
    margin-right: 3%;
    margin-top: 6%;
}
#star5 {
    
    float: right;
    margin-right: 3%;
    margin-top: 6%;
}
#star6 {
 
    float: right;
    margin-right: 3%;
    margin-top: 6%;
}
#star7 {
  
    float: right;
    margin-right: 3%;
    margin-top: 6%;
}
#star8 {
 
    float: right;
    margin-right: 3%;
    margin-top: 6%;
}
#star9 {
   
    float: right;
    margin-right: 3%;
    margin-top: 6%;
}
#star10 {
   
    float: right;
    margin-right: 3%;
    margin-top: 6%;
}
#star11 {
   
    float: right;
    margin-right: 3%;
    margin-top: 6%;
}
#star12 {
   
    float: right;
    margin-right: 3%;
    margin-top: 6%;
}
td{
font-size:96% !important;
}
.widget {
    background: none repeat scroll 0% 0% #FFF;
    border: 1px solid #FFF;
    border-radius: 3px;
    box-shadow: 0px 2px 2px -2px #FFF;
    margin-top: 35px;
    position: relative;
}
</style>