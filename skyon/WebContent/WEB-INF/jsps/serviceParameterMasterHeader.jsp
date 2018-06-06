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

<script type="text/javascript"
	src="<c:url value='/resources/jquery-ui.min.js'/>"></script>
<script src="<c:url value='/resources/kendo/js/kendo.all.min.js' />"></script>
<script src="<c:url value='/resources/kendo/shared/js/console.js'/>"></script>
<script src="<c:url value='/resources/kendo/shared/js/prettify.js'/>"></script>
<script type="text/javascript" lang="JavaScript"
  src="<c:url value='/resources/jquery.i18n.properties-min-1.0.9.js'/>"></script>
 
<div id="langDiv"></div>
 
<div id="content">
	<div class="contentTop">
		<span class="pageTitle"><span class="icon-screen"></span><label id="labeldashboard">${ViewName}</label></span>
		<%-- <ul class="quickStats">
			<li><a href="" class="redImg"><img
					src="<c:url value='/resources/images/icons/quickstats/user.png'/>"
					alt="" /></a>
				<div class="floatR">
					<strong class="blue">4658</strong><span>users</span>
				</div></li>
		</ul> --%>
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
						<%-- <c:otherwise>
							<li><a href="${bc.value}">${bc.name}</a></li>
						</c:otherwise> --%>
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
	//$('.tipN').tipsy({gravity: 'n',fade: true, html:true});
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
			if(dataItem.value !="#"){
			RenderLinkInner(dataItem.value);
			}
		}

		$(document).ready(function() {
			$("#menu").kendoMenu({
				dataSource : [ {
					text : "Parameter Master Management",
					value : "#", 
					items : [ {
						text : "Service Parameter Master",
						value : "servicetasks"
					},{
						text : "Billing Parameter Master",
						value : "billingparameter"
					},{
						text : "Meter Parameter Master",
						value : "meterparameter"
					}]
				} ],
				select : onSelect
			});
		});
	</script>
	<div class="wrapper">
		<ul class="middleNavA">
			<li><a title="Update Service Parameter Master" href="#"
				onclick="RenderLinkInner('servicetasks')"><img alt=""
					src="<c:url value='/resources/images/icons/color/service.jpg'/>" height="50" width="60" ><span id="mNavUserRole">
						Service Parameter Master</span></a></li>
			<li><a title="Update Billing Parameter Master" href="#"
				onclick="RenderLinkInner('billingparameter')"><img alt=""
					src="<c:url value='/resources/images/icons/color/service.jpg'/>" height="50" width="60" ><span id="mNavUserRole">
						Billing Parameter Master</span></a></li>
			<li><a title="Update Meter Parameter Master" href="#"
				onclick="RenderLinkInner('meterparameter')"><img alt=""
					src="<c:url value='/resources/images/icons/color/service.jpg'/>" height="50" width="60" ><span id="mNavUserRole">
						Meter Parameter Master</span></a></li>
		</ul>
	</div>
	<div class="divider">
							<span></span>
						</div>
