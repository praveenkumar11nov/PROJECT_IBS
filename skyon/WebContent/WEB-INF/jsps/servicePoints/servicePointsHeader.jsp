<%@taglib prefix="kendo" uri="/WEB-INF/kendo.tld"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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

<%-- 	
 <link rel="stylesheet"
	href="<c:url value='/resources/js/intlTel/css/intlTelInput.css'/>"> 


<script src="<c:url value='/resources/js/intlTel/js/intlTelInput.js'/>"></script> --%>

<script type="text/javascript"
	src="<c:url value='/resources/jquery-ui.min.js'/>"></script>
<script src="<c:url value='/resources/kendo/js/kendo.all.min.js' />"></script>
<script src="<c:url value='/resources/kendo/shared/js/console.js'/>"></script>
<script src="<c:url value='/resources/kendo/shared/js/prettify.js'/>"></script>
<script type="text/javascript" lang="JavaScript"
	src="<c:url value='/resources/jquery.i18n.properties-min-1.0.9.js'/>"></script>

<script type="text/javascript"
	src="<c:url value='/resources/js/plugins/ui/jquery.tipsy.js'/>"></script>

<%--  <script src="<c:url value='/resources/kendo.hin.js'/>"></script> --%>
<div id="langDiv"></div>


<div id="content">
	<div class="contentTop">
		<span class="pageTitle"><span class="icon-screen"></span><label
			id="labeldashboard">${ViewName}</label></span>
	</div>

	<!-- Breadcrumbs line -->
	<div class="breadLine">
		<div class="bc">
			<ul id="breadcrumbs" class="breadcrumbs">
				<c:url var="home" value="./home" />
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
						<c:otherwise>
							<li><a href="${bc.value}">${bc.name}</a></li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</ul>
		</div>
		<div class="breadLinks">
			<ul id="menu"></ul>
		</div>
	</div>
</div>
	<script>
		function onSelect(e) {
			var item = $(e.item), menuElement = item.closest(".k-menu"), dataItem = this.options.dataSource, index = item
					.parentsUntil(menuElement, ".k-item").map(function() {
						return $(this).index();
					}).get().reverse();

			index.push(item.index());

			for (var i = -1, len = index.length; ++i < len;) {
				dataItem = dataItem[index[i]];
				dataItem = i < len - 1 ? dataItem.items : dataItem;
			}
			if (dataItem.value != "#") {
				RenderLinkInner(dataItem.value);
			}
		}

		$(document).ready(function() {
			$("#menu").kendoMenu({
				dataSource : [ {
					text : "Service Points",
					value : "#",
					items : [ {
						text : "Service Point",
						value : "servicePoint"
					} ]
				} ],
				select : onSelect
			});
		});
	</script>

	<div class="wrapper">
		<c:set var="viewName" value="${ViewName}" />
		<c:choose>
			<c:when test="${viewName == 'Dashboard'}">
				<ul class="middleNavA">
								
					<li><a title="Service Point" href="#"
						onclick="RenderLinkInner('servicePoint')"> <img alt=""
							src="<c:url value='/resources/images/billing/servicePoints/servicePoint.jpg'/>">
							<span id="mNavProject">Service Point</span></a></li>


				</ul>
			</c:when>

			<c:when test="${viewName == 'Service Points'}">
				<ul class="middleNavA">								
					<li><a title="Bills" href="#"
						onclick="RenderLinkInner('servicePoint')"> <img alt=""
							src="<c:url value='/resources/images/billing/servicePoints/servicePoint.jpg'/>">
							<span id="mNavProject">Service Point</span></a></li>
				</ul>
			</c:when>
			
			<c:when test="${viewName == 'Service Point'}">
				<ul class="middleNavA">								
					<li><a title="Bills" href="#"
						onclick="RenderLinkInner('servicePoint')"> <img alt=""
							src="<c:url value='/resources/images/billing/servicePoints/servicePoint.jpg'/>">
							<span id="mNavProject">Service Point</span></a></li>
				</ul>
			</c:when>			
		</c:choose>

	</div>
	<div class="divider">

		<span></span>
	</div>
	<br />