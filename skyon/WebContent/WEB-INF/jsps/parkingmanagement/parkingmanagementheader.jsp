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
	src="<c:url value='/resources/jquery.min.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/jquery-ui.min.js'/>"></script>
<script src="<c:url value='/resources/kendo/js/kendo.all.min.js' />"></script>
<script src="<c:url value='/resources/kendo/shared/js/console.js'/>"></script>
<script src="<c:url value='/resources/kendo/shared/js/prettify.js'/>"></script>
<script type="text/javascript"  src="<c:url value='/resources/jquery.i18n.properties-min-1.0.9.js'/>"></script>
  <script src="<c:url value='/resources/kendo.hin.js'/>"></script>
<div id="langDiv"></div>
<script type="text/javascript" src="<c:url value='/resources/js/plugins/ui/jquery.tipsy.js'/>"></script>
<script>

/* $('.tipN').tipsy({ gravity: 'n',fade: true, html:true});
$('.tipN').click(function(){
	$('.tipN').tipsy().hide();
}); */

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
		<ul class="quickStats">
			<li><a href="" class="redImg"><img
					src="<c:url value='/resources/images/icons/quickstats/user.png'/>"
					alt="" /></a>
				<div class="floatR">
					<strong class="blue">4658</strong><span>users</span>
				</div></li>
		</ul>
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
					text : "Parking Management",
					value : "#",
					items : [ {
						text : "Manage Parking Slots",
						value : "parkingslotheader",
					},{
						text : "Manage Vehicles",
						value : "vehicles"
					},{
						text : "Manage Wrong Parking",
						value : "wrongparking"
					} ]
				} ],
				select : onSelect
			});
		});
	</script>
	<div class="wrapper">
		<ul class="middleNavA">
			<li><a class="tipN" title="Managing Parking Slots Matser,Parking Slot Allocation" href="#"
				onclick="RenderLinkInner('parkingslotheader')"><img alt="" width="50px" height="50px"
					src="<c:url value='/resources/images/icons/color/logo_blue_white.png'/>"><span id="mNavUsers">
						Parking Slots</span></a></li>	
						
						
						
			<li><a class="tipN" title="Manage Vehicles Registration With Owner and Slots" href="#"
				onclick="RenderLinkInner('vehicles')"><img alt="" width="70px" height="50px"
					src="<c:url value='/resources/images/icons/color/car.png'/>"><span id="mNavRoles">
						Vehicles</span></a></li>	
						
			<li><a class="tipN" title="Manage Wrong Parking Details With Vehicle Details" href="#"
				onclick="RenderLinkInner('wrongparking')"><img alt="" width="50px" height="50px"
					src="<c:url value='/resources/images/icons/color/Wrongparking.png'/>"><span id="mNavGroups">
						Wrong Parking</span></a></li>	

			
		</ul>
	</div>
	<div class="divider">
							<span></span>
						</div>