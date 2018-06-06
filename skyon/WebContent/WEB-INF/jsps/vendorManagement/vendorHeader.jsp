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
	src="<c:url value='/resources/js/plugins/ui/jquery.tipsy.js'/>"></script> --%>
<%-- <script type="text/javascript"
	src="<c:url value='/resources/jquery.min.js'/>"></script> --%>
<script type="text/javascript"
	src="<c:url value='/resources/jquery-ui.min.js'/>"></script>
<script src="<c:url value='/resources/kendo/js/kendo.all.min.js' />"></script>
<script src="<c:url value='/resources/kendo/shared/js/console.js'/>"></script>
<script src="<c:url value='/resources/kendo/shared/js/prettify.js'/>"></script>
<%-- <script type="text/javascript" language="JavaScript"
  src="<c:url value='/resources/jquery.i18n.properties-min-1.0.9.js'/>"></script> --%>
 <%--  <script src="<c:url value='/resources/kendo.hin.js'/>"></script> --%>
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
	<script>
	/* $('.tipN').tipsy({gravity: 'n',fade: true, html:true}); */
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
					text : "Vendor Management",
					value : "#",
					items : [ {
						text : "Vendor",
						value : "vendor"
					},{
						text : "Vendor Contracts",
						value : "vendorContracts"
					},{
						text : "Vendor Invoices",
						value : "vendorInvoices"
					},{
						text : "Vendor Payments",
						value : "vendorPayments"
					},{
						text : "Vendor Requests",
						value : "vendorRequests"
					},{
						text : "Vendor Inidents",
						value : "vendorIncidents"
					}]
				} ],
				select : onSelect
			});
		});
	</script>
	<div class="wrapper">
		<ul class="middleNavA">
			<%-- <li><a title="Enter Vendor Details" href="#"
				onclick="RenderLinkInner('vendors')"><img alt=""
					src="<c:url value='/resources/images/icons/color/order.png'/>"><span id="mNavUsers">
						Vendors</span></a></li> --%>
						
			<li><a title="Enter Vendor Contracts Details" href="#"
				onclick="RenderLinkInner('vendorContracts')"><img alt=""
					src="<c:url value='/resources/images/icons/color/business-contact.png'/>"><span id="mNavUserRole">
						Vendor Contracts</span></a></li>
						
			<li><a title="Enter Vendor Invoices" href="#"
				onclick="RenderLinkInner('vendorInvoices')"><img alt=""
					src="<c:url value='/resources/images/icons/color/business-contact.png'/>"><span id="mNavUserRole">
						Vendor Invoices</span></a></li>
						
			<li><a title="Enter Vendor Incidents" href="#"
				onclick="RenderLinkInner('vendorIncidents')"><img alt=""
					src="<c:url value='/resources/images/icons/color/business-contact.png'/>"><span id="mNavUserRole">
						Vendor Incidents</span></a></li>
		  <%--  <li><a title="Enter Vendor LineItems" href="#"
				onclick="RenderLinkInner('vendorLineItems')"><img alt=""
					src="<c:url value='/resources/images/icons/color/business-contact.png'/>"><span id="mNavUserRole">
						Vendor LineItems</span></a></li> --%>
						
			<li><a title="Enter Vendor Payments" href="#"
				onclick="RenderLinkInner('vendorPayments')"><img alt=""
					src="<c:url value='/resources/images/icons/color/business-contact.png'/>"><span id="mNavUserRole">
						Vendor Payments</span></a></li>
						
			<li><a title="Enter Vendor Requests" href="#"
				onclick="RenderLinkInner('vendorRequests')"><img alt=""
					src="<c:url value='/resources/images/icons/color/business-contact.png'/>"><span id="mNavUserRole">
						Vendor Requests</span></a></li>
		</ul>
	</div>
	</div>
	<div class="divider">
							<span></span>
						</div>