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
	href="<c:url value='/resources/kendo/shared/styles/examples-offline.css'/>"
	rel="stylesheet">

<script src="<c:url value='/resources/kendo/js/jquery.min.js' />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/jquery-ui.min.js'/>"></script>
<script src="<c:url value='/resources/kendo/js/kendo.all.min.js' />"></script>
<script src="<c:url value='/resources/kendo/shared/js/console.js'/>"></script>
<script src="<c:url value='/resources/kendo/shared/js/prettify.js'/>"></script>

<div id="langDiv"></div>
 
<div id="content">
	<div class="contentTop">
		<span class="pageTitle"><span class="icon-screen"></span><label id="labeldashboard">${ViewName}</label></span>		
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
			if(dataItem.value !="#"){
			RenderLinkInner(dataItem.value);
			}
		}

		$(document).ready(function() {
			$("#menu").kendoMenu({
				dataSource : [ {
					text : "Customer Occupancy",
					value : "#",
					items : [ {
						text : "General",
						value : "comgeneral",
					},{
						text : "Person",
						value : "persons"
					} ]
				} ],
				select : onSelect
			});
		});
	</script>
	
	<div class="wrapper">
	<c:set var="viewName" value="${ViewName}" />
	<c:choose>
  		<c:when test="${viewName == 'Person' || viewName == 'Dashboard'}">
   		 <ul class="middleNavA">
			<li><a title="General" href="#" onclick="RenderLinkInner('comgeneral')">
					<img alt="" src="<c:url value='/resources/images/icons/color/user.png'/>">
						<span id="mNavGeneral">General</span>
			</a></li>
			<li><a title="Manage Persons" href="#" onclick="RenderLinkInner('person')">
					<img alt=""src="<c:url value='/resources/images/icons/comimages/persons.png'/>">
						<span id="mNavPerson">Persons</span>
			</a></li>
		</ul>
  		</c:when>
  		<c:when test="${viewName == 'Project' || viewName == 'Property'}">
   		<ul class="middleNavA">
			<li><a title="Manage Project" href="#" onclick="RenderLinkInner('comproject')">
					<img width="50" height="50" alt="" src="<c:url value='/resources/images/icons/comimages/project.png'/>">
						<span id="mNavProject">Project</span></a></li>
			<li><a title="Manage Property" href="#" onclick="RenderLinkInner('comproperty')">
					<img width="50" height="50" alt=""src="<c:url value='/resources/images/icons/comimages/property.png'/>">
					<span id="mNavProperty">Property</span>
			</a></li>
		 </ul>
 		</c:when>
 		<c:when test="${viewName == 'Persons' || viewName == 'Owners' || viewName == 'Family' || viewName == 'Tenant' 
 		|| viewName == 'Domestic' || viewName == 'Vendors' || viewName =='Pets'}">
 			<ul class="middleNavA">
				<li><a title="Manage Owners" href="#" onclick="RenderLinkInner('comowners')">
						<img width="50" height="50" alt="" src="<c:url value='/resources/images/icons/comimages/Owner2.png'/>">
							<span id="mNavOwners">Owners</span></a></li>
				<li><a title="Manage Tenants" href="#" onclick="RenderLinkInner('comtenants')">
						<img width="50" height="50" alt=""src="<c:url value='/resources/images/icons/color/tenent2.png'/>">
						<span id="mNavTenants">Tenants</span>
				</a></li>
				<li><a title="Manage Family" href="#" onclick="RenderLinkInner('comfamily')">
						<img width="50" height="50" alt=""src="<c:url value='/resources/images/icons/comimages/family-icon.png'/>">
						<span id="mNavfamily">Family</span>
				</a></li>
				<li><a title="Manage Domestic Helps" href="#" onclick="RenderLinkInner('comdomestichelp')">
						<img width="50" height="50" alt=""src="<c:url value='/resources/images/icons/comimages/cheff.png'/>">
						<span id="mNavdomestichelp">Domestic Helps</span>
				</a></li>
				<li><a title="Manage Pets" href="#" onclick="RenderLinkInner('compets')">
						<img width="50" height="50" alt=""src="<c:url value='/resources/images/icons/comimages/Cat-Brown-icon.png'/>">
						<span id="mNavpets">Pets</span>
				</a></li>
		   </ul>
 		</c:when>
 		
 		<c:when test="${viewName == 'Settings' || viewName == 'Document Definer'}">
 			<ul class="middleNavA">
				<li><a title="Manage Document Definer" href="#" onclick="RenderLinkInner('documentdefiner')">
						<img width="50" height="50" alt="" src="<c:url value='/resources/images/icons/comimages/document.png'/>">
							<span id="mNavDocumentDefiner">Document Definer</span></a></li>
				<li><a title="Manage Document Repository" href="#" onclick="RenderLinkInner('documentrepository')">
						<img width="50" height="50" alt=""src="<c:url value='/resources/images/icons/comimages/repository.png'/>">
						<span id="mNavDocumentRepo">Document Repository</span>
				</a></li>
		   </ul>
 		</c:when>
 		
 		<c:when test="${viewName == 'Access Cards'}">
 			<ul class="middleNavA">
				<li><a title="Manage Access Cards" href="#" onclick="RenderLinkInner('comaccesscards')">
						<img width="60" height="50" alt="" src="<c:url value='/resources/images/icons/comimages/add_card.png'/>">
							<span id="mNavDocumentDefiner">Access Cards</span></a></li>
		   </ul>
 		</c:when>
  		 
	</c:choose>		 
	</div>
	<div class="divider">
	 
							<span></span>
						</div>
						<br/>