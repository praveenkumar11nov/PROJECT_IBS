<%@taglib prefix="kendo" uri="/WEB-INF/kendo.tld"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<link
	href="<c:url value='/resources/kendo/css/web/kendo.common.min.css'/>"
	rel="stylesheet" />
<link href="<c:url value='/resources/kendo/css/web/kendo.rtl.min.css'/>"
	rel="stylesheet" />
<link
	href="<c:url value='/resources/kendo/css/web/kendo.bootstrap.min.css'/>"
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
<script type="text/javascript" src="<c:url value='/resources/kendo/js/jquery.min.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/jquery-ui.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/kendo/js/kendo.custom.min.js' />"></script>
<script type="text/javascript" src="<c:url value='/resources/kendo/shared/js/console.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/kendo/shared/js/prettify.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resources/jquery-barcode.js'/>"></script>

<script type="text/javascript" 
  src="<c:url value='/resources/jquery.i18n.properties-min-1.0.9.js'/>"></script>  

  
<script type="text/javascript">
/* jQuery(document).ready(function() {
    var lang = getURLParameter("language");
    loadBundles(lang);
    $("#labelhome").html(jQuery.i18n.prop('label.home'));
    $("#labeldashboard").html(jQuery.i18n.prop('${ViewName}'));  
});

function getURLParameter(name) {
    return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null;
}

function loadBundles(lang) {
    jQuery.i18n.properties({
        name:'welcome',
        path:'resources/',
        mode:'both',
        language: lang,
        callback: function() {}
    });
}

$("#langSelector").change(function() {
    document.location = this.value;
});

$("#cancelButton").click(function() {
    confirm(confirm_cancel);
}); */
</script>  

<div id="content">
	<div class="contentTop">
		<span class="pageTitle"><span class="icon-screen"></span><label id="labeldashboard">${ViewName}</label></span>
	</div>

	<!-- Breadcrumbs line -->
	<div class="breadLine">
		<div class="bc">
			<ul id="breadcrumbs" class="breadcrumbs">
				<c:url var="home" value="/home" />
				<c:url value="/images/icons/breadsHome.png" var="homeimage" />
				<c:forEach var="bc" items="${breadcrumb.tree}" varStatus="status">
					<c:choose>
						<c:when test="${status.index==0}">
							<li><a href="${home}"><label id="labelhome">Home</label></a></li>
						</c:when>
						<c:when
							test="${status.index == fn:length(breadcrumb.tree)-1 && status.index!=0}">
							<li><a href="#"><label id="labelbcdashboard">${bc.name}</label></a></li>
						</c:when>
					</c:choose>
				</c:forEach>
			</ul>
		</div>
		<div class="breadLinks"></div>
	</div>