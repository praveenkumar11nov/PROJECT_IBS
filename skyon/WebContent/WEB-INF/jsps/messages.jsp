<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="kendo" uri="/WEB-INF/kendo.tld"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link
	href="<c:url value='/resources/kendo/css/web/kendo.common.min.css'/>"
	rel="stylesheet" />
<link href="<c:url value='/resources/kendo/css/web/kendo.rtl.min.css'/>"
	rel="stylesheet" />
<link
	href="<c:url value='/resources/kendo/css/web/kendo.silver.min.css'/>"
	rel="stylesheet" />
<link
	href="<c:url value='/resources/kendo/css/dataviz/kendo.dataviz.min.css'/>"
	rel="stylesheet" />
<link
	href="<c:url value='/resources/kendo/css/dataviz/kendo.dataviz.default.min.css'/>"
	rel="stylesheet" />
<link
	href="<c:url value='/resources/kendo/shared/styles/examples-offline.css'/>"
	rel="stylesheet">
<script src="<c:url value='/resources/kendo/js/jquery.min.js' />"></script>
<%-- <script type="text/javascript"
	src="<c:url value='/resources/jquery.min.js'/>"></script> --%>
<script type="text/javascript"
	src="<c:url value='/resources/jquery-ui.min.js'/>"></script>
<script src="<c:url value='/resources/kendo/js/kendo.all.min.js' />"></script>
<script src="<c:url value='/resources/kendo/shared/js/console.js'/>"></script>
<script src="<c:url value='/resources/kendo/shared/js/prettify.js'/>"></script>

<div id="wrapper" class="wrapper">
   <div id="leftcolumn">
        Left
		<!-- <li><a href="#" onclick="RenderLink('compose',this.id,'subNav_timeAtt');" id="timeAttdashboard" title="" class="notthis"><span class="icos-fullscreen"></span>Compose</a></li> -->
    </div>
    <div id="rightcolumn">
        Right
        <kendo:grid name="trackGrid">
        </kendo:grid>
    </div>
</div>

<style>
/* body {
    background-color: #444;
    margin: 0;
}  */   
 #wrapper {
     width: 1300px;
     margin: 0 auto;
} 
#leftcolumn, #rightcolumn {
    border: 1px solid white;
    float: left;
    min-height: 450px;
    color: white;
}
#leftcolumn {
     width: 250px;
     background-color: #111;
}
#rightcolumn {
     width: 1000px;
     background-color: #777;
}



</style>