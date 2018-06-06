<%@include file="/common/taglibs.jsp"%>

<script src="./resources/js/plugins/charts/highcharts.js"></script>
<script src="./resources/js/plugins/charts/exporting.js"></script>


<div id="tabs-container" style="width: 100%;">
    <ul class="tabs-menu">
        <li class="current"><a href="#tab-1">Year Wise Consumption</a></li>
        <li><a href="#tab-2">Month Wise Consumption</a></li>
    </ul>
    <div class="tab" style="width:100%">
        <div id="tab-1" class="tab-content" style="width: 100%;">
        <div id="container1" style="width: 97%;"></div>
        
        
             <div class="widget" style="width:97%;">
            <table cellpadding="0" cellspacing="0" width="100%" class="tDark">
                <thead>
                    <tr>
                        <td>Sr No</td>
                        <td>Year</td>
                        <td>Consumption (kWh)</td>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${year}" var="list"> 
                	<tr>
                        <td><center><c:out value="${list.srNo}" /></center> </td>
                        <td><center><c:out value="${list.year}" /></center> </td>
                        <td><center><c:out value="${list.consumption}" /></center> </td>
                    </tr>
                </c:forEach>
                   
                </tbody>
            </table>
        </div>
  </div>
        <div id="tab-2" class="tab-content" style="width: 100%; ">
         <div id="columnChart" style="width: 82%; padding-left: 70px;"></div>
             <div class="widget" style="width: 97%;">
            <table cellpadding="0" cellspacing="0" width="100%" class="tDark">
                <thead>
                    <tr>
                        <td>Sr No</td>
                        <td>Month</td>
                        <td>Consumption (kWh)</td>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${month}" var="list"> 
                	<tr>
                        <td><center><c:out value="${list.srNo}" /></center> </td>
                        <td align="center"><a href='#' onclick="getTowerWiseDetails('${list.month}')"><c:out value="${list.month}" /></a></td>
                        <td><center><c:out value="${list.consumption}" /></center> </td>
                    </tr>
                    
																
                </c:forEach>
                   
                </tbody>
            </table>
        </div>
        
        </div>
    </div>
</div>

<script>
function getTowerWiseDetails(month){
	window.location.href="./getTowerWiseDetails?id="+month;
}

$(document).ready(function() {
    $(".tabs-menu a").click(function(event) {
        event.preventDefault();
        $(this).parent().addClass("current");
        $(this).parent().siblings().removeClass("current");
        var tab = $(this).attr("href");
        $(".tab-content").not(tab).css("display", "none");
        $(tab).fadeIn();
    });
    
     $.ajax({
	url : "./energyDashboard/yearWisedetails",
	type : "GET",
	dataType : "JSON",
	success : function(response) {
		//alert(response[0][0]);
		$('#container1').highcharts({
            chart: {
                type: 'column',
            },
            title: {
                text: 'Consumption (kWh)'
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                type: 'category',
                labels: {
                    //rotation: -45,
                    style: {
                        fontSize: '13px',
                        fontFamily: 'Verdana, sans-serif'
                    }
                }
            },
            plotOptions: {
                series: {
                    pointWidth: 100,
                    groupPadding: 0
                }
            },
            yAxis: {
                min: 0,
                title: {
                    text: ''
                }
            },
            legend: {
                enabled: false
            },
            tooltip: {
                pointFormat: 'Consumption in {point.name}: <b>{point.y} kWh</b>'
            },
            series: [{
                name: 'Population',
                data: response,
                dataLabels: {
                    enabled: true,
                    //rotation: -90,
                    color: '#FFFFFF',
                    align: 'center',
                    format: '{point.y:.1f}', // one decimal
                    y: 10, // 10 pixels down from the top
                    style: {
                        fontSize: '13px',
                        fontFamily: 'Verdana, sans-serif'
                    }
                }
            }]
        });
	}
});  
     $.ajax({
	url : "./energyDashboard/monthWisedetails",
	type : "GET",
	dataType : "JSON",
	success : function(response) {
		//alert(response[0][0]);
		$('#columnChart').highcharts({
            chart: {
                type: 'column',
            },
            title: {
                text: 'Monthly Consumption (kWh)'
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                type: 'category',
                labels: {
                    //rotation: -45,
                    style: {
                        fontSize: '13px',
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
            legend: {
                enabled: false
            },
            tooltip: {
                pointFormat: 'Consumption in {point.name}: <b>{point.y} kWh</b>'
            },
            series: [{
                name: 'Population',
                data: response,
                dataLabels: {
                    enabled: true,
                    //rotation: -90,
                    color: '#FFFFFF',
                    align: 'center',
                    format: '{point.y:.1f}', // one decimal
                    y: 10, // 10 pixels down from the top
                    style: {
                        fontSize: '13px',
                        fontFamily: 'Verdana, sans-serif'
                    }
                }
            }]
        });
	}
});  
    
   
});

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