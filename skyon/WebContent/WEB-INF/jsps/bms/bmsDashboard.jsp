<%@include file="/common/taglibs.jsp"%>

<script src="./resources/js/plugins/charts/highcharts.js"></script>
<script src="./resources/js/plugins/charts/exporting.js"></script>

<!-- Sewerage Treatment Plant -->
  <div class="widget grid4 chartWrapper" style="width: 400px;height: 214px;">
                <div class="whead" style="text-align:center;color: black;"><h6>Sewerage Treatment Plant</h6></div>
                <span style='font-size: 13px; margin-left: 173px;color: #333131;font-family:Arnoldboecklin, fantasy;'>Treated Tank Level
	                <c:choose>
					<c:when test="${treatWaterTankLevel=='HIGH'}">
						
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img alt="Nothing to show" src="./resources/images/red_light.png" style="vertical-align: middle; height: 20px; width: 20px;"/>&nbsp;&nbsp;&nbsp;<font color="red"><b><label>${treatWaterTankLevel}</label></b></font>
					
					</c:when>
					<c:otherwise>
					
					<c:if test="${treatWaterTankLevel=='LOW'}">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img alt="Nothing to show" src="./resources/images/green_light.png" style="vertical-align: middle; height: 20px; width: 20px;"/>&nbsp;&nbsp;&nbsp;<font color="green"><b><label>${treatWaterTankLevel}</label></b></font>
					</c:if>
					</c:otherwise>
				   </c:choose>
			   </span>
				  
				  
			   <span style='font-size: 13px; margin-left: 173px;color: #333131;font-family:Arnoldboecklin, fantasy;'>Soft Tank Level 
				<c:choose>
				  <c:when test="${softWaterTankLevel=='HIGH'}">
				
				  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img alt="Nothing to show" src="./resources/images/red_light.png" style="vertical-align: middle; height: 20px; width: 20px;"/>&nbsp;&nbsp;&nbsp;<font color="red"><b><label>${softWaterTankLevel}</label></b></font>
				  </c:when>
				  <c:otherwise>
				  <c:if test="${softWaterTankLevel=='LOW'}">
				  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img alt="Nothing to show" src="./resources/images/green_light.png" style="vertical-align: middle; height: 20px; width: 20px;"/>&nbsp;&nbsp;&nbsp;<font color="green"><b><label>${softWaterTankLevel}</label></b></font> 
				  </c:if>
				  </c:otherwise>
			   </c:choose>
		      </span>
			
			 <span style='font-size: 13px; margin-left: 173px;color: #333131;font-family:Arnoldboecklin, fantasy;'>Water PH Monitoring    <c:choose>
				<c:when test="${softWaterPHMonitoring=='HIGH'}">
				
			    &nbsp;&nbsp;&nbsp;&nbsp;<img alt="Nothing to show" src="./resources/images/red_light.png" style="vertical-align: middle; height: 20px; width: 20px;"/>&nbsp;&nbsp;&nbsp;<font color="red"><b><label>${softWaterPHMonitoring}</label></b></font> 
				</c:when>
				<c:otherwise>
				<c:if test="${softWaterPHMonitoring=='LOW'}">
				&nbsp;&nbsp;&nbsp;&nbsp;<img alt="Nothing to show" src="./resources/images/green_light.png" style="vertical-align: middle; height: 20px; width: 20px;"/>&nbsp;&nbsp;&nbsp;<font color="green"><b><label>${softWaterPHMonitoring}</label></b></font>
				</c:if>
				</c:otherwise>
			</c:choose></span> 
			
			
			<span style='font-size: 13px; margin-left: 173px;color: #333131;font-family:Arnoldboecklin, fantasy;'>Water Chlorine Monitor   <c:choose>
				<c:when test="${softWaterChlorineMonitoring=='HIGH'}">
				<img alt="Nothing to show" src="./resources/images/red_light.png" style="vertical-align: middle; height: 20px; width: 20px;"/>&nbsp;&nbsp;&nbsp;<font color="red"><b><label>${softWaterChlorineMonitoring}</label></b></font> 
				</c:when>
				<c:otherwise>
				<c:if test="${softWaterChlorineMonitoring=='LOW'}">
					<img alt="Nothing to show" src="./resources/images/green_light.png" style="vertical-align: middle; height: 20px; width: 20px;"/>&nbsp;&nbsp;&nbsp;<font color="green"><b><label>${softWaterChlorineMonitoring}</label></b></font> 
				</c:if>
				</c:otherwise>
			</c:choose></span> 
			
			
			
                  <div class="body" style="height: 176px;width: 170px;margin-top: -107px;margin-left: -3px;"><div id="sewerageContainer"
			style="height: 100%;width: 100%; margin: 0 auto; border: 0px solid black;"></div></div>
                
	                
			
  </div>
  
  
  
  
  
  
  
  
  <!-- Fight Fighting System -->
   <div class="widget grid4 chartWrapper" style="width: 400px;height: 214px;margin-left: 408px;margin-top: -216px;">
                <div class="whead" style="text-align:center;color: black;"><h6 align="center" style="color: black;">Fight Fighting System</h6></div>
                
                 <span style='font-size: 13px; margin-left: 183px;color: #333131;font-family:Arnoldboecklin, fantasy;'>Hydrant Pump Status 
	                <c:choose>
					<c:when test="${hydrantPumpStatus=='OFF'}">
						
				&nbsp;&nbsp;<img alt="Nothing to show" src="./resources/images/green_light2.png" style="vertical-align: middle; height: 20px; width: 20px;"/>&nbsp;&nbsp;&nbsp;<font color="red"><b><label>${hydrantPumpStatus}</label></b></font>
					
					</c:when>
					<c:otherwise>
					<c:if test="${hydrantPumpStatus=='ON'}">	
					&nbsp;&nbsp;<img alt="Nothing to show" src="./resources/images/green_light.png" style="vertical-align: middle; height: 20px; width: 20px;"/>&nbsp;&nbsp;&nbsp;<font color="green"><b><label>${hydrantPumpStatus}</label></b></font>
					</c:if>
					</c:otherwise>
				   </c:choose>
				  </span>
			
			<span style='font-size: 13px; margin-left: 183px;color: #333131;font-family:Arnoldboecklin, fantasy;'>Sprinkler Pump Status  <c:choose>
				<c:when test="${sprinklerPumpStatus=='OFF'}">
				
					<img alt="Nothing to show" src="./resources/images/green_light2.png" style="vertical-align: middle; height: 20px; width: 20px;"/>&nbsp;&nbsp;&nbsp;<font color="red"><b><label>${sprinklerPumpStatus}</label></b></font>
				</c:when>
				<c:otherwise>
				<c:if test="${sprinklerPumpStatus=='ON'}">
					<img alt="Nothing to show" src="./resources/images/green_light.png" style="vertical-align: middle; height: 20px; width: 20px;"/>&nbsp;&nbsp;&nbsp;<font color="green"><b><label>${sprinklerPumpStatus}</label></b></font>
				</c:if>
				</c:otherwise>
			</c:choose></span>
			
			
			
			<span style='font-size: 13px; margin-left: 183px;color: #333131;font-family:Arnoldboecklin, fantasy;'>Jockey Pump Status  <c:choose>
				<c:when test="${jockeyPumpStatus=='OFF'}">
				
					&nbsp;&nbsp;&nbsp;&nbsp;<img alt="Nothing to show" src="./resources/images/green_light2.png" style="vertical-align: middle; height: 20px; width: 20px;"/>&nbsp;&nbsp;&nbsp;<font color="red"><b><label>${jockeyPumpStatus}</label></b></font> 
				</c:when>
				<c:otherwise>
				<c:if test="${jockeyPumpStatus=='ON'}">
					&nbsp;&nbsp;&nbsp;&nbsp;<img alt="Nothing to show" src="./resources/images/green_light.png" style="vertical-align: middle; height: 20px; width: 20px;"/>&nbsp;&nbsp;&nbsp;<font color="green"><b><label>${jockeyPumpStatus}</label></b></font>
				</c:if>
				</c:otherwise>
			</c:choose></span>
			
			
			<span style='font-size: 13px; margin-left: 183px;color: #333131;font-family:Arnoldboecklin, fantasy;'>Diesel Pump Status <c:choose>
				<c:when test="${dieselPumpStatus=='OFF'}">
				
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img alt="Nothing to show" src="./resources/images/green_light2.png" style="vertical-align: middle; height: 20px; width: 20px;"/>&nbsp;&nbsp;&nbsp;<font color="red"><b><label>${dieselPumpStatus}</label></b></font>
				</c:when>
				<c:otherwise>
				<c:if test="${dieselPumpStatus=='ON'}">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img alt="Nothing to show" src="./resources/images/green_light.png" style="vertical-align: middle; height: 20px; width: 20px;"/>&nbsp;&nbsp;&nbsp;<font color="green"><b><label>${dieselPumpStatus}</label></b></font>
				</c:if>
				</c:otherwise>
			</c:choose></span>
                
                
                <div class="body" style="height: 176px;width: 180px;margin-top: -107px;margin-left: -3px;"><div id="firecontainer"
			style="height: 100%;width: 100%; margin: 0 auto; border: 0px solid black;"></div></div>
</div>











                
	<!-- Basement Ventilation System -->                
 <div class="widget grid4 chartWrapper" style="width: 400px;height: 214px;margin-left: 818px;margin-top: -216px;">
                <div class="whead" style="text-align:center;color: black;"><h6 align="center" style="color: black;">Basement Ventilation System</h6></div>
                
                
                <div class="body" style="height: 155px;width: 395px;margin-top: -17px;margin-left: -3px;">
                <div id="ventillationContainer" style="height: 100%;width: 100%; margin: 0 auto; border: 0px solid black;"></div>
                &nbsp;<span style='font-size: 13px; margin-left: 130px;color: #333131;font-family:Arnoldboecklin, fantasy;'>Fan Status<c:choose>
				<c:when test="${fanStatus=='OFF'}">
				
				<img alt="Nothing to show" src="./resources/images/green_light2.png" style="vertical-align: middle; height: 15px; width: 15px;"/>&nbsp;&nbsp;&nbsp;<font color="red"><b><label>${fanStatus}</label></b></font>
				</c:when>
				<c:otherwise>
				<c:if test="${fanStatus=='ON'}">
					<img alt="Nothing to show" src="./resources/images/green_light.png" style="vertical-align: middle; height: 15px; width: 15px;"/>&nbsp;&nbsp;&nbsp;<font color="green"><b><label>${fanStatus}</label></b></font>
				</c:if>
				</c:otherwise>
				</c:choose></span>
                
                </div>
 </div>
                
  
  
  
 
  
  <!-- Water Distribution System -->
  <div class="widget grid4 chartWrapper" style="width: 400px;height: 214px;margin-top: 7px;">
                <div class="whead" style="text-align:center;color: black;"><h6 align="center" style="color: black;">Water Distribution System</h6></div>
                
                
                
                
                <div class="body" style="height: 155px;width: 395px;margin-top: -17px;margin-left: -3px"><div id="waterDistribution" style="height: 100%;width: 100%; margin: 0 auto; border: 0px solid black;"></div>
                <span style='font-size: 13px; margin-left: 90px;color: #333131;font-family:Arnoldboecklin, fantasy;'>Hydro Pneumatical Pump Status<c:choose>
				<c:when test="${hydroPneumaticPumpStatus=='OFF'}">
				
					<img alt="Nothing to show" src="./resources/images/green_light2.png" style="vertical-align: middle; height: 15px; width: 15px;"/>&nbsp;&nbsp;&nbsp;<font color="red"><b><label>${hydroPneumaticPumpStatus}</label></b></font>
				</c:when>
				<c:otherwise>
				<c:if test="${hydroPneumaticPumpStatus=='ON'}">
					<img alt="Nothing to show" src="./resources/images/green_light.png" style="vertical-align: middle; height: 15px; width: 15px;"/>&nbsp;&nbsp;&nbsp;<font color="green"><b><label>${hydroPneumaticPumpStatus}</label></b></font>
				</c:if>
				</c:otherwise>
			</c:choose> </span>
                </div>
</div>
  
  <!--Lift Elevator System  -->
  <div class="widget grid4 chartWrapper" style="width: 400px;height: 214px;margin-left: 408px;margin-top: -218px;">
                <div class="whead" style="text-align:center;color: black;"><h6 align="center" style="color: black;">Lift Elevator System</h6></div>
                
                <span style='font-size: 13px; margin-left: 188px;color: #333131;font-family:Arnoldboecklin, fantasy;'>
				Elevator Status   <c:choose>
				<c:when test="${liftElevatorStatus=='RUNNING'}">
				
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img alt="Nothing to show" src="./resources/images/green_light2.png" style="vertical-align: middle; height: 17px; width: 17px;"/><font color="red"><b><label>${liftElevatorStatus}</label></b></font>
				</c:when>
				<c:otherwise>
				<c:if test="${liftElevatorStatus=='STAND BY'}">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img alt="Nothing to show" src="./resources/images/green_light.png" style="vertical-align: middle; height: 17px; width: 17px;"/><font color="green"><b><label>${liftElevatorStatus}</label></b></font>
				</c:if>
				</c:otherwise>
			</c:choose></span>
                
               <span style='font-size: 13px; margin-left: 188px;color: #333131;font-family:Arnoldboecklin, fantasy;'>Lift Elevator Alarm<c:choose>
				<c:when test="${liftElevatorFaultAlarm=='OFF'}">
				
				<img alt="Nothing to show" src="./resources/images/green_light2.png" style="vertical-align: middle; height: 17px; width: 17px;"/>&nbsp;&nbsp;&nbsp;<font color="red"><b><label>${liftElevatorFaultAlarm}</label></b></font>
				</c:when>
				<c:otherwise>
				<c:if test="${liftElevatorFaultAlarm=='ON'}">
					<img alt="Nothing to show" src="./resources/images/green_light.png" style="vertical-align: middle; height: 17px; width: 17px;"/>&nbsp;&nbsp;&nbsp;<font color="green"><b><label>${liftElevatorFaultAlarm}</label></b></font> 
				</c:if>
				</c:otherwise>
			</c:choose></span> 
                
                
                
                <div class="body" style="height: 173px;width: 180px;margin-top: -62px;margin-left: -3px;"><div id="liftContainer" style="height: 100%;width: 100%; margin: 0 auto; border: 0px solid black;"></div></div>
                </div>
 

<!-- DG Set -->
  <div class="widget grid4 chartWrapper" style="width: 400px;height: 214px;margin-left: 818px;margin-top: -218px;">
                <div class="whead" style="text-align:center;color: black;"><h6 align="center" style="color: black;">DG Set</h6></div>
            
            
            <span style='font-size: 13px; margin-left: 187px;color: #333131;font-family:Arnoldboecklin, fantasy;'>DG Set Status <c:choose>
				<c:when test="${DGSetStatus=='OFF'}">
				
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img alt="Nothing to show" src="./resources/images/green_light2.png" style="vertical-align: middle; height: 20px; width: 20px;"/>&nbsp;&nbsp;&nbsp;<font color="red"><b><label>${DGSetStatus}</label></b></font>
				</c:when>
				<c:otherwise>
				<c:if test="${DGSetStatus=='ON'}">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img alt="Nothing to show" src="./resources/images/green_light.png" style="vertical-align: middle; height: 20px; width: 20px;"/>&nbsp;&nbsp;&nbsp;<font color="green"><b><label>${DGSetStatus}</label></b></font>
				</c:if>
				</c:otherwise>
			</c:choose> </span>
            
            
            <span style='font-size: 13px; margin-left: 187px;color: #333131;font-family:Arnoldboecklin, fantasy;'>DG Set Alarm <c:choose>
				<c:when test="${DGSetTripAlarm=='OFF'}">
				
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img alt="Nothing to show" src="./resources/images/green_light2.png" style="vertical-align: middle; height: 20px; width: 20px;"/>&nbsp;&nbsp;&nbsp;<font color="red"><b><label>${DGSetTripAlarm}</label></b></font>
				</c:when>
				<c:otherwise>
				<c:if test="${DGSetTripAlarm=='ON'}">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img alt="Nothing to show" src="./resources/images/green_light.png" style="vertical-align: middle; height: 20px; width: 20px;"/>&nbsp;&nbsp;&nbsp;<font color="green"><b><label>${DGSetTripAlarm}</label></b></font> 
				</c:if>
				</c:otherwise>
			</c:choose></span>
			
			<span style='font-size: 13px; margin-left: 187px;color: #333131;font-family:Arnoldboecklin, fantasy;'>DG Battery Status    <c:choose>
				<c:when test="${DGBatteryStatus=='OFF'}">
				
					<img alt="Nothing to show" src="./resources/images/green_light2.png" style="vertical-align: middle; height: 20px; width: 20px;"/>&nbsp;&nbsp;&nbsp;<font color="red"><b><label>${DGBatteryStatus}</label></b></font>
				</c:when>
				<c:otherwise>
				<c:if test="${DGBatteryStatus=='ON'}">
					<img alt="Nothing to show" src="./resources/images/green_light.png" style="vertical-align: middle; height: 20px; width: 20px;"/>&nbsp;&nbsp;&nbsp;<font color="green"><b><label>${DGBatteryStatus}</label></b></font>
				</c:if>
				</c:otherwise>
			</c:choose></span>
			
          <div class="body" style="height: 176px;width: 180px;margin-top: -85px;margin-left: -3px;"><div id="dgSet" style="height: 100%;width: 100%; margin: 0 auto; border: 0px solid black;"></div></div>
</div>
                              
                
  <script>
  
  /*  Sewerage Plant*/
  $(document).ready(function () {
	$.ajax({
		url : "./bmsDashboard/sewerageTreatedWaterPlant",
		type : "GET",
		dataType : "JSON",
		success : function(response) {
		
			    $('#sewerageContainer').highcharts({
			    	colors : [ "#FF6347" ],  
			    	chart: {
				            	backgroundColor : {
											linearGradient : {
												x1 : 0,
												y1 : 0,
												x2 : 1,
												y2 : 1
											},
											stops : [ [ 0, '#B4FFB4' ], [ 1, '#B4FFB4' ] ]
										},
				            type: 'column'
				        },
			        title: {
			        	text : 'Sewerage Day Wise',
			        	style : {
							color : '#FF6347',
							fontFamily:'Arnoldboecklin, fantasy',
							fontSize : '12px'
						}
			        },
			        xAxis: {
			        	    
			        	   lineWidth: 0,
			        	   minorGridLineWidth: 0,
			        	   lineColor: 'transparent',
			        	          
			        	   labels: {
			        	       enabled: false
			        	   },
			        	   minorTickLength: 0,
			        	   tickLength: 0
			        	},
			        yAxis: {
			            min: 0,
			            title: {
			                text: ''
			            }
			        },
			        tooltip: {
			        	pointFormat:'<b>{point.y}</b>',
	        		    shared: true,
	                    valueSuffix: ' cm'
			        },
			          legend: {
				            enabled: false
				        },
			        exporting: { enabled: false },
			        credits : {
							enabled : false
						},
						colors: ['#8FBC8F', '#2F7ED8', '#AAAAAA',' 	#DC143C'],
						plotOptions: {
						    series: {
						    colorByPoint: true
						    }
						},  
				         
			        series: [{
			        	data: response
			        	
			        }]
			    });
			}

		});
	});
	
	/* Fight fighting system */
		
	$(document).ready(function () {
	$.ajax({
		url : "./bmsDashboard/fightFightingSystem",
		type : "GET",
		dataType : "JSON",
		success : function(response) {
		
	        // Build the chart
	        $('#firecontainer').highcharts({
	            chart: {
	                	backgroundColor : {
							linearGradient : {
								x1 : 0,
								y1 : 0,
								x2 : 1,
								y2 : 1
							},
							stops : [ [ 0, '#B5E6FF' ], [ 1, '#B5E6FF' ] ]
						},
	                plotBackgroundColor: null,
	                plotBorderWidth: null,
	                plotShadow: false
	            },
	            title : {
					text : 'Pump Level Day Wise',
					style : {
						color : '#FF6347',
						fontFamily:'Arnoldboecklin, fantasy',
						fontSize : '12px'
					}
				},
	           tooltip: {
                pointFormat: '<b>{point.y}</b>'
	            }, 
	            credits : {
					enabled : false
				},
				exporting: { enabled: false },
	            plotOptions: {
	                pie: {
	                    allowPointSelect: true,
	                    cursor: 'pointer',
	                    size: 110,
	                    dataLabels: {
	                        enabled: false
	                    },
	                }
	            },
	             series: [{
		            type: 'pie',
					fontSize : '11px',                 
		            data: response
		        }]
	        });
	    }

	});
 
});
	
	/* Basement ventilation system  */
  
	$.ajax({
		url : "./bmsDashboard/ventillationContainer",
		type : "GET",
		dataType : "JSON",
		success : function(response) {
			
			var day1 = response[0];
			var first = response[1];
			
			var day2 = response[2];
			var second = response[3];
			
			var day3 = response[4];
			var third = response[5];
			
			var day4 = response[6];
			var fourth = response[7];
			
			var day5 = response[8];
			var fifth = response[9];
			
			var day6 = response[10];
			var sixth = response[11];
			
			var day7 = response[12];
			var seventh = response[13];
		
	 $(function () {
		    $('#ventillationContainer').highcharts({
		       colors : [ "#FF6347" ],
			chart : {

							backgroundColor : {
								linearGradient : {
									x1 : 0,
									y1 : 0,
									x2 : 1,
									y2 : 1
								},
								stops : [ [ 0, '#f2ff67' ], [ 1, '#f2ff67' ] ]
							},
							style : {
								fontFamily :'Arnoldboecklin, fantasy'
							},
							plotBorderColor : '#606063',
						},
						title : {
							text : 'Ventillation Level Day Wise Status',
							style : {
								color : '#307D7E',
								fontFamily:'Arnoldboecklin, fantasy',
								fontSize : '12px'
							}
						},
		            
		       
		            xAxis: {
			            type: '',
			            categories: [day1, day2,day3, day4,day5,day6,day7],
			            labels: {
			                rotation: 0,
			                style: {
			                    fontSize: '8px',
			                    fontFamily:'Arnoldboecklin, fantasy',
			                }
			            }
			        },
		        yAxis: {
		            title: {
						fontFamily:'Arnoldboecklin, fantasy',
		                text: 'Temperature(°C)',
						fontSize : '5px'
		            },
		            plotLines: [{
		                value: 0,
		                width: 1,
		                color: '#808080'
		            }]
		        },
		        tooltip: {
		            valueSuffix: '°C'
		        },
		        credits : {
					enabled : false
				},
				exporting: { enabled: false },
		        legend: {
		            layout: 'vertical',
		            align: 'center',
		            borderWidth: 0,
		            noColumns: 0
		        },
		        series: [{
		        	name: 'Temperature Level',
		        	data: [first,second, third, fourth, fifth, sixth, seventh],
		        	fontFamily:'Arnoldboecklin, fantasy',
		        	fontSize : '8px'
		        }]
		    });
		});
		}
	});
	 
	/* Lift elevator system */
	
	 $(document).ready(function () {
		$.ajax({
		url : "./bmsDashboard/liftElevatorSystem",
		type : "GET",
		dataType : "JSON",
		success : function(response) {

	    $('#liftContainer').highcharts({
	       colors : [ "#00BFFF" ],

	        chart: {
	            	backgroundColor : {
								linearGradient : {
									x1 : 0,
									y1 : 0,
									x2 : 1,
									y2 : 1
								},
								stops : [ [ 0, '#DEEAA0' ], [ 1, '#DEEAA0' ] ]
							},
	            type: 'column'
	        },
	        title : {
				text : 'Lift Day Wise Status',
				style : {
					color : '#307D7E',
					fontFamily:'Arnoldboecklin, fantasy',
					fontSize : '12px'
				}
			},
	            xAxis: {
	            type: '',
	            labels: {
	                rotation: 0,
	                style: {
	                    fontSize: '9px',
	                    fontFamily: 'Verdana, sans-serif'
	                }
	            }
	        },
	        yAxis: {
	            min: 0,
	            title: {
	                text: ''
	            }
	        },
	    	colors: ['#FF6633', '#669999'],
			plotOptions: {
			    series: {
			    colorByPoint: true
			    }
			},
	        legend: {
	            enabled: false
	        },
	        exporting: { enabled: false },
	        tooltip: {
	        	pointFormat: '<b>{point.y:.1f} %</b>'
	        },
	        credits : {
				enabled : false
			},
	        series: [{
	            
	            data: response,
	            enableMouseTracking: true
	        }]
	    });
	}
});
});
		


  
	/*DG Set  */

	    $(document).ready(function () {

	    	$.ajax({
	    		url : "./bmsDashboard/dgSet",
	    		type : "GET",
	    		dataType : "JSON",
	    		success : function(response) {
	        $('#dgSet').highcharts({
	            chart: {
	                	backgroundColor : {
							linearGradient : {
								x1 : 0,
								y1 : 0,
								x2 : 1,
								y2 : 1
							},
							stops : [ [ 0, '#FFD2FF' ], [ 1, '#FFD2FF' ] ]
						},
	                plotBackgroundColor: null,
	                plotBorderWidth: null,
	                plotShadow: false
	            },
	            title : {
					text : 'DG Set Day Wise',
					style : {
						color : '#307D7E',
						fontFamily:'Arnoldboecklin, fantasy',
						fontSize : '12px'
					}
				},
	            tooltip: {
	                pointFormat: '<b>{point.y}</b>'
	            },
	            exporting: { enabled: false },
	            credits : {
					enabled : false
				},
	            plotOptions: {
	                pie: {
	                    allowPointSelect: true,
	                    cursor: 'pointer',
	                    size: 110,
	                    dataLabels: {
	                        enabled: false
	                    },
	                }
	            },
	             series: [{
		            type: 'pie',
					fontSize : '11px',
					name:'Pump status',
		            data:response
		         
		        }]
	        });
	    }

	});
	
	});

	
	
	
	/*water Distribution System */
	
	$.ajax({
		url : "./bmsDashboard/waterDistribution",
		type : "GET",
		dataType : "JSON",
		success : function(response) {
			
			var day1 = response[0];
			var first = response[1];
			
			var day2 = response[2];
			var second = response[3];
			
			var day3 = response[4];
			var third = response[5];
			
			var day4 = response[6];
			var fourth = response[7];
			
			var day5 = response[8];
			var fifth = response[9];
			
			var day6 = response[10];
			var sixth = response[11];
			
			var day7 = response[12];
			var seventh = response[13];

	$(function () {
	    $('#waterDistribution').highcharts({
	       colors : [ "#4b8df8" ],
		chart : {

						backgroundColor : {
							linearGradient : {
								x1 : 0,
								y1 : 0,
								x2 : 1,
								y2 : 1
							},
							stops : [ [ 0, '#FFAEA3' ], [ 1, '#FFAEA3' ] ]
						},
						style : {
							fontFamily :'Arnoldboecklin, fantasy'
						},
						plotBorderColor : '#606063',
					},
					title : {
						text : 'Water Distribution Day Wise Status',
						style : {
							color : '#F0F8FF',
							fontFamily:'Arnoldboecklin, fantasy',
							fontSize : '12px'
						}
					},
	       
				    xAxis: {
			            type: '',
			            categories: [day1, day2,day3, day4,day5,day6,day7],
			            labels: {
			                rotation: 0,
			                style: {
			                    fontSize: '9px',
			                    fontFamily:'Arnoldboecklin, fantasy'
			                }
			            }
			        },
	        yAxis: {
	            title: {
	                text: 'Meters (mtrs)',
					fontSize : '9px',
					fontFamily:'Arnoldboecklin, fantasy'

	            },
	            plotLines: [{
	                value: 0,
	                width: 1,
	                color: '#808080'
	            }]
	        },
	        credits : {
				enabled : false
			},
	        tooltip: {
	            valueSuffix: 'mtrs'
	        },
	        exporting: { enabled: false },
	        legend: {
	            layout: 'vertical',
	            align: 'center',
	            borderWidth: 0,
	            noColumns: 0
	        },
	        series: [{
	            name: 'Hydro Pneumatic Pump Status',
	            data: [first, second, third, fourth, fifth, sixth, seventh],
	            fontFamily: 'Verdana, sans-serif'
	        }]
	    });
	});
	
		}
	});
	
  </script>
  
  
  
  
  
  <style>
  .chart, .bars, .updating, .pie {
    height: 121px;
    margin: 0px 0px 0px;
    z-index: 90;
    width: 62%;
}
 
 .widget .body {
    padding: 18px 4px;
}


.tile {
	width: 170px !important
}
.highcharts-background{
	width: 1272px !important;
	border: 0px !important

	
}

svg{
	width: auto !important;
	border: 0px;
}

h4 {
	margin-top: 0px !important;
	margin-bottom: 10px
}

.tile.double-down i {
	margin-top: 10px;
}
.tile.double-down{
	width: 250px !important;
}

.tile .tile-body > i {
	margin-top: 0px;
	display: block;
	font-size: 56px;
	line-height: 56px;
	text-align: center;
}
  </style>