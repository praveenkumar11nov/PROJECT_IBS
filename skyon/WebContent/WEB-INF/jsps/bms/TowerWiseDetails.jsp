<%@include file="/common/taglibs.jsp"%>

<script src="resources/js/plugins/charts/highcharts.js"></script>
<script src="resources/js/plugins/charts/exporting.js"></script>

<div id="tabs-container" style="width: 100%;">
    <ul class="tabs-menu">
        <li class="current"><a href="#tab-1">Tower Wise Consumption</a></li>
    </ul>
    <div class="tab" style="width:100%">
        <div id="tab-1" class="tab-content" style="width: 100%;">
       <div>
        <div id="container" style="width: 97%;"></div>
        
             <div class="widget" style="width:97%;">
             
            <table cellpadding="0" cellspacing="0" width="100%" class="tDark">
                <thead>
                    <tr>
                        <td>Sr No</td>
                        <td>Tower</td>
                        <td>Consumption (kWh)</td>
                    </tr>
                </thead>
                <tbody>
                
                	<c:forEach items="${tower}" var="list"> 
                	<tr>
                        <td><center><c:out value="${list.srNo}" /></center> </td>
                        <td><center><c:out value="${list.tower}" /></center> </td>
                        <td><center><c:out value="${list.consumption}" /></center> </td>
                    </tr>
                </c:forEach>
               
                   
                </tbody>
            </table>
        </div>
  </div>  
   <!--  div 1st -->   
      <!--  <div style="float: right;">
       <div id="container2" style="height: 400px"></div>
        
             <div class="widget">
             
            <table cellpadding="0" cellspacing="0" width="100%" class="tDark">
                <thead>
                    <tr>
                        <td>Sr No</td>
                        <td>Year</td>
                        <td>Consumption (kWh)</td>
                    </tr>
                </thead>
                <tbody>
                
                	<tr>
                        <td><center>NAren</center> </td>
                        <td><center>Shine</center> </td>
                        <td><center>Macha</center> </td>
                    </tr>
               
                   
                </tbody>
            </table>
        </div>
  </div>   -->
  </div> <!--  div 2nd -->   
    </div>
</div>

<script>

$(document).ready(function () {
	$.ajax({
		url : "./energyDashboard/towerWisedetails",
		type : "GET",
		dataType : "JSON",
		success : function(response) {
	        $('#container').highcharts({
	            chart: {
	                plotBackgroundColor: null,
	                plotBorderWidth: null,
	                plotShadow: false,
	                type: 'pie'
	            },
	            title: {
	                text: 'Towerwise Consumption'
	            },
	            tooltip: {
	                pointFormat: '{series.name}: <b>{point.y}kWh</b>'
	            },
	            plotOptions: {
	                pie: {
	                    allowPointSelect: false,
	                    cursor: 'pointer',
	                    dataLabels: {
	                        enabled: false
	                    },
	                    showInLegend: true
	                }
	            },
	            series: [{
	                name: "Consumption",
	                colorByPoint: true,
	                data: response
	            }]
	        });
		}
	   });
});
   /*  $(document).ready(function () {

        // Build the chart
          
 });          */

/* $(function () {
    $('#container2').highcharts({
        chart: {
            type: 'pie',
            options3d: {
                enabled: true,
                alpha: 45,
                beta: 0
            }
        },
        title: {
            text: 'Browser market shares at a specific website, 2014'
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: false,
                cursor: 'pointer',
                depth: 35,
                dataLabels: {
                    enabled: true,
                    format: '{point.name}'
                }
            },
            showInLegend: true
        },
        series: [{
            type: 'pie',
            name: 'Browser share',
            data: [
                ['Firefox',   45.0],
                ['IE',       26.8],
                ['Safari',    8.5],
                ['Opera',     6.2],
                ['Others',   0.7]
            ]
        }]
    });
}); */
</script>

<style>


.tabs-menu {
    height: 30px;
    float: right;
    clear: both;
}

.tabs-menu li {
    height: 30px;
    line-height: 30px;
    float: left;
    margin-right: 10px;
    background-color: #ccc;
    border-top: 1px solid #d4d4d1;
    border-right: 1px solid #d4d4d1;
    border-left: 1px solid #d4d4d1;
}

.tabs-menu li.current {
    position: relative;
    background-color: #fff;
    border-bottom: 1px solid #fff;
    z-index: 5;
}

.tabs-menu li a {
    padding: 10px;
    /* text-transform: uppercase; */
    color: #fff;
    text-decoration: none; 
}

.tabs-menu .current a {
    color: #2e7da3;
}

.tab {
    border: 1px solid #d4d4d1;
    background-color: #fff;
    float: left;
    margin-bottom: 20px;
    width: auto;
}

.tab-content {
    width: 660px;
    padding: 20px;
    display: none;
}

#tab-1 {
 display: block;   
}

</style>