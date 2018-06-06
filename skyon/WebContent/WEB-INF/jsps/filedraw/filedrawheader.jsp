<%@taglib prefix="kendo" uri="/WEB-INF/kendo.tld"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
<script src="<c:url value='/resources/kendo/js/jquery.min.js' />"></script>
<%-- <script type="text/javascript"
	src="<c:url value='/resources/jquery.min.js'/>"></script> --%>
<script type="text/javascript"
	src="<c:url value='/resources/jquery-ui.min.js'/>"></script>
<script src="<c:url value='/resources/kendo/js/kendo.all.min.js' />"></script>
<script src="<c:url value='/resources/kendo/shared/js/console.js'/>"></script>
<script src="<c:url value='/resources/kendo/shared/js/prettify.js'/>"></script>
<script type="text/javascript" 
  src="<c:url value='/resources/jquery.i18n.properties-min-1.0.9.js'/>"></script>
 <%--  <script src="<c:url value='/resources/kendo.hin.js'/>"></script> --%>
<div id="langDiv"></div>
<script>
jQuery(document).ready(function() {
    var lang = getURLParameter("language");
    //alert(lang);
    loadBundles(lang);
    //if(lang == "hin"){loadjscssfile("./resources/kendo.hin.js", "js")}
    $("#labeldashboard").html(jQuery.i18n.prop('${ViewName}'));
    $("#labelhome").html(jQuery.i18n.prop('label.home'));
   // $("#labelbcdashboard").html(jQuery.i18n.prop('UserManagement'));
    $("#mNavGeneral").html(jQuery.i18n.prop('General'));
    $("#mNavPerson").html(jQuery.i18n.prop('Person'));
    //alert($("#labelbcdashboard").text().split(" ").join(""));
    $("#labelbcdashboard").html(jQuery.i18n.prop($("#labelbcdashboard").text().split(" ").join("")));
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
});
function loadjscssfile(filename,filetype)
{
	      if ((filetype=="js")){ //if filename is a external JavaScript file
		     // alert("Loading hindi for kendo ");
		  var fileref=document.createElement('script')
		  fileref.setAttribute("type","text/javascript")
		  fileref.setAttribute("src", filename);
		  if (typeof fileref!="undefined"){
			  $("#langDiv").html(fileref);
			}
	      }
}
</script>  
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
					<img alt="" src="<c:url value='/resources/images/icons/comimages/project.png'/>">
						<span id="mNavProject">Project</span></a></li>
			<li><a title="Manage Property" href="#" onclick="RenderLinkInner('comproperty')">
					<img alt=""src="<c:url value='/resources/images/icons/comimages/property.png'/>">
					<span id="mNavProperty">Property</span>
			</a></li>
		 </ul>
 		</c:when>
 		<c:when test="${viewName == 'Persons' || viewName == 'Owners'}">
 			<ul class="middleNavA">
				<li><a title="Manage Owners" href="#" onclick="RenderLinkInner('comowners')">
						<img alt="" src="<c:url value='/resources/images/icons/comimages/owner.png'/>">
							<span id="mNavOwners">Owners</span></a></li>
				<li><a title="Manage Tenants" href="#" onclick="RenderLinkInner('comtenants')">
						<img alt=""src="<c:url value='/resources/images/icons/color/order-149.png'/>">
						<span id="mNavTenants">Tenants</span>
				</a></li>
				<li><a title="Manage Family" href="#" onclick="RenderLinkInner('comfamily')">
						<img alt=""src="<c:url value='/resources/images/icons/comimages/family.png'/>">
						<span id="mNavfamily">Family</span>
				</a></li>
				<li><a title="Manage Domestic Helps" href="#" onclick="RenderLinkInner('comdomestichelp')">
						<img alt=""src="<c:url value='/resources/images/icons/comimages/help.png'/>">
						<span id="mNavdomestichelp">Domestic Helps</span>
				</a></li>
				<li><a title="Manage Pets" href="#" onclick="RenderLinkInner('compets')">
						<img alt=""src="<c:url value='/resources/images/icons/comimages/pet.png'/>">
						<span id="mNavpets">Pets</span>
				</a></li>
		   </ul>
 		</c:when>
 		
 		<c:when test="${viewName == 'Settings' || viewName == 'Document Definer'}">
 			<ul class="middleNavA">
				<li><a title="Manage Document Definer" href="#" onclick="RenderLinkInner('comdocumentdefiner')">
						<img alt="" src="<c:url value='/resources/images/icons/comimages/document.png'/>">
							<span id="mNavDocumentDefiner">Document Definer</span></a></li>
				<li><a title="Manage Document Repository" href="#" onclick="RenderLinkInner('comdocumentrepository')">
						<img alt=""src="<c:url value='/resources/images/icons/comimages/repository.png'/>">
						<span id="mNavDocumentRepo">Document Repository</span>
				</a></li>
		   </ul>
 		</c:when>
  		 
	</c:choose>	
		
		
	
	<%-- <c:if test="${viewName == 'Persons' || viewName == 'Dashboard'}">
		
	 </c:if>
	 
	 <c:if test="${viewName == 'Project'} || ${viewName == 'Property'}">
	 	
	 </c:if> --%>
	 
	</div>
	<div class="divider">
	 
							<span></span>
						</div>
						<br/>