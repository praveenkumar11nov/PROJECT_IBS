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
<!-- <script>
jQuery(document).ready(function() {
    var lang = getURLParameter("language");
    //alert(lang);
    loadBundles(lang);
    //if(lang == "hin"){loadjscssfile("./resources/kendo.hin.js", "js")}
    //$("#labeldashboard").html(jQuery.i18n.prop($("#labeldashboard").text().split(" ").join("")));
     $("#labeldashboard").html(jQuery.i18n.prop('${ViewName}'));
    $("#labelhome").html(jQuery.i18n.prop('label.home'));
    $("#mNavPt").html(jQuery.i18n.prop('Patrol Tracks'));
    $("#mNavPtp").html(jQuery.i18n.prop('PatrolTrack Points'));
     $("#mNavPtpS").html(jQuery.i18n.prop('Patrol Staff'));
    $("#mNavPtA").html(jQuery.i18n.prop('Patrol Alerts'));
    $("#mNavPs").html(jQuery.i18n.prop('Patrol Settings'));
    /* $("#mNavAtt").html(jQuery.i18n.prop('StaffAttendanceSummary'));
    $("#mNavGateout").html(jQuery.i18n.prop('StaffGateoutAlert')); */
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
</script>   -->
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
					text : "Time And Attendance",
					value : "#",
					items : [ {
						text : "Manage Patrol Tracks",
						value : "patrolTracks",
					},{
						text : "Manage Patrol Track Point",
						value : "PatrolTrackPoints"
					},{
						text : "Manage Patrol Staff",
						value : "patrolTrackStaff"
					},{
						text : "Manage Patrol Alerts",
						value : "patrolTrackAlert"
					} ,{
						text : "Patrol Settings",
						value : "patrolSettings"
					},/*{
						text : "Staff Attendance GateOut Alert",
						value : "staffAttendanceGateOutAlert"
					} */ ]
				} ],
				select : onSelect
			});
		});
	</script>
	<div class="wrapper">
		<ul class="middleNavA">
			<li><a title="Manage Patrol Tracks" href="#"
				onclick="RenderLinkInner('patrolTracks')"><img alt=""
					src="<c:url value='/resources/images/icons/color/track2.png'/>" height="50" width="50"><span id="mNavPt">
						Patrol Tracks</span></a></li>
			<li><a title="Manage Patrol Points" href="#"
				onclick="RenderLinkInner('PatrolTrackPoints')"><img alt=""
					src="<c:url value='/resources/images/icons/color/point.png'/>" height="50" width="50"><span id="mNavPtp">
						Patrol Track Point</span></a></li>
						
			<li><a title="Manage Patrol Staff" href="#"
				onclick="RenderLinkInner('patrolTrackStaff')"><img alt=""
					src="<c:url value='/resources/images/icons/color/Police-Officer-icon.png'/>" height="50" width="50"><span id="mNavPtpS">
						Patrol Staff</span></a></li>	
						
			<li><a title="Manage Patrol Alert" href="#"
				onclick="RenderLinkInner('patrolTrackAlert')"><img alt=""
					src="<c:url value='/resources/images/icons/color/alert-icon.png'/>" height="50" width="50"><span id="mNavPtA">
						Patrol Alerts</span></a></li>	
						
						<li><a title="Manage Patrol Settings" href="#"
				onclick="RenderLinkInner('patrolSettings')"><img alt=""
					src="<c:url value='/resources/images/icons/color/settings.png'/>" height="50" width="50"><span id="mNavPs">
						Patrol Settings</span></a></li>
						
						<%-- <li><a title="Manage Staff Attendance Summary" href="#"
				onclick="RenderLinkInner('staffAttendanceSummary')"><img alt=""
					src="<c:url value='/resources/images/icons/color/administrative-docs.png'/>"><span id="mNavAtt">
						Staff Attendance Summary</span></a></li>	
						
						<li><a title="Manage Staff Attendance Gateout Alert" href="#"
				onclick="RenderLinkInner('staffAttendanceGateOutAlert')"><img alt=""
					src="<c:url value='/resources/images/icons/color/administrative-docs.png'/>"><span id="mNavGateout">
						Staff Attendance Gateout Alert</span></a></li>	 --%>
						

			
		</ul>
	</div>
	<div class="divider">
							<span></span>
						</div>