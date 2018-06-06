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
<script type="text/javascript"
	src="<c:url value='/resources/jquery-ui.min.js'/>"></script>
<script src="<c:url value='/resources/kendo/js/kendo.all.min.js' />"></script>
<script src="<c:url value='/resources/kendo/shared/js/console.js'/>"></script>
<script src="<c:url value='/resources/kendo/shared/js/prettify.js'/>"></script>
<script type="text/javascript" language="JavaScript"
  src="<c:url value='/resources/jquery.i18n.properties-min-1.0.9.js'/>"></script>
 <div id="langDiv"></div>
<script>
jQuery(document).ready(function() {
    var lang = getURLParameter("language");
   loadBundles(lang);
    $("#labeldashboard").html(jQuery.i18n.prop('${ViewName}'));
    $("#labelhome").html(jQuery.i18n.prop('label.home'));
    $("#mNavUsers").html(jQuery.i18n.prop('ParkingSlots'));
    $("#mNavRoles").html(jQuery.i18n.prop('Vehicles'));
    $("#mNavGroups").html(jQuery.i18n.prop('WrongParking'));
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
	      if ((filetype=="js")){ 
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
					text : "WorkFlow Management",
					value : "#",
					items : [ {
						text : "Manage Process",
						value : "manageprocess",
					},{
						text : "Manage Tasks",
						value : "managetasks"
					}, {
						text : "Mange Process Integration",
						value : "processintegration"
					},{
						text : "Batch Activity & Routing",
						value : "batchactivity"
					} ]
				} ],
				select : onSelect
			});
		});
	</script>
	<div class="wrapper">
		<ul class="middleNavA">
			<li><a title="Manage Process" href="#"
				onclick="RenderLinkInner('manageprocess')"><img alt="" width="50px" height="50px"
					src="<c:url value='/resources/images/icons/color/parking.jpg'/>">
					<span id="mNavUsers">Manage Process</span></a></li>
					
			<li><a title="Manage Tasks" href="#"
				onclick="RenderLinkInner('managetasks')"><img alt="" width="50px" height="50px"
					src="<c:url value='/resources/images/icons/color/vehicles.jpg'/>"><span id="mNavRoles">
						Mange Process Integration</span></a></li>	
						
			<li><a title="Manage Process Integration" href="#"
				onclick="RenderLinkInner('processintegration')"><img alt="" width="50px" height="50px"
					src="<c:url value='/resources/images/icons/color/wrongparking.jpg'/>"><span id="mNavGroups">
						Mange Process Integration</span></a></li>	
			
			<li><a title="Batch Activity & Routing" href="#"
				onclick="RenderLinkInner('batchactivity')"><img alt="" width="50px" height="50px"
					src="<c:url value='/resources/images/icons/color/wrongparking.jpg'/>"><span id="mNavGroups">
						Batch Activity & Routingn</span></a></li>	
			
		</ul>
	</div>
	<div class="divider">
							<span></span>
						</div>