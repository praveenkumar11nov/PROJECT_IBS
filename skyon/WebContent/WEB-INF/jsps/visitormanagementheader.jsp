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

	<script type="text/javascript"
	src="<c:url value='/resources/js/plugins/ui/jquery.tipsy.js'/>"></script>
<script>
	jQuery(document).ready(
			function() {
				var lang = getURLParameter("language");
				//alert(lang);
				loadBundles(lang);
				//if(lang == "hin"){loadjscssfile("./resources/kendo.hin.js", "js")}
				$("#mNavVisitorWizard").html(jQuery.i18n.prop('Visitor Wizard'));
				$("#mNavUserRole").html(jQuery.i18n.prop('Visitors Entry Details'));
				$("#labeldashboard").html(
						jQuery.i18n.prop('Visitor Master'));
				$("#labelhome").html(jQuery.i18n.prop('label.home'));
				// $("#labelbcdashboard").html(jQuery.i18n.prop('UserManagement'));
			    $("#mNavUsers").html(jQuery.i18n.prop('Visitors')); 
				$("#mNavVisitorRecord").html(
						jQuery.i18n.prop("Visitor Master"));
				
				//$("#mNavUserGroup").html(jQuery.i18n.prop('Visitor Parking'));
				
				
				/* $("#mNavTest").html(jQuery.i18n.prop('Visitor Parking Test')); */
				//alert($("#labelbcdashboard").text().split(" ").join(""));
				$("#labelbcdashboard").html(
						jQuery.i18n.prop($("#labelbcdashboard").text().split(
								" ").join("")));
			});

	function getURLParameter(name) {
		return decodeURIComponent((new RegExp('[?|&]' + name + '='
				+ '([^&;]+?)(&|#|;|$)').exec(location.search) || [ , "" ])[1]
				.replace(/\+/g, '%20'))
				|| null;
	}

	function loadBundles(lang) {
		jQuery.i18n.properties({
			name : 'welcome',
			path : 'resources/',
			mode : 'both',
			language : lang,
			callback : function() {
			}
		});
	}

	$("#langSelector").change(function() {
		document.location = this.value;
	});

	$("#cancelButton").click(function() {
		confirm(confirm_cancel);
	});
	function loadjscssfile(filename, filetype) {
		if ((filetype == "js")) { //if filename is a external JavaScript file
			// alert("Loading hindi for kendo ");
			var fileref = document.createElement('script')
			fileref.setAttribute("type", "text/javascript")
			fileref.setAttribute("src", filename);
			if (typeof fileref != "undefined") {
				$("#langDiv").html(fileref);
			}
		}
	}
</script>
<!-- <br>
<br> -->
<div id="content">
	<div class="contentTop">
		<span class="pageTitle"><span class="icon-screen"></span><label
			id="labeldashboard">${ViewName}</label></span>
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
			if (dataItem.value != "#") {
				RenderLinkInner(dataItem.value);
			}
		}

		$(document).ready(function() {
			$("#menu").kendoMenu({
				dataSource : [ {
					text : "Visitor Management",
					value : "#",
					items : [

{
	text : "Visitor Wizard",
	value : "visitorwizard"
},
{
	text : "Visitors",
	value : "visitordetails"
},

					         
					{

						text : "Visitor Master",
						value : "storevisitor",
					},

					{
						text : "Visitor Entry Details",
						value : "visitorvisits"
					}/* , {
						text : "Visitor Parking",
						value : "visitorparking"
					}
					 */
					
					]
				} ],
				select : onSelect
			});
		});
	</script>


	<div class="wrapper">
		<ul class="middleNavA">

			<li><a title="Enter Visitor Record " href="#"
				onclick="RenderLinkInner('visitorwizard')"><img alt=""
					src="<c:url value='/resources/images/icons/color/Person-Male-Light-icon.png'/>"
					height="50" width="50"><span id="mNavVisitorWizard">
						Visitor Wizard</span></a></li>



			<li><a
				title="Enter and View the Visitor  Record  based on  Property Number And Parking Slots"
				href="#" onclick="RenderLinkInner('visitordetails')"><img alt=""
					src="<c:url value='/resources/images/icons/color/people.png'/>"
					height="50" width="50"><span id="mNavUsers"> Visitors</span></a></li>


			<li><a
				title="View and update Visitor Master Record like(Visitor Name,Address,Contact Number)"
				href="#" onclick="RenderLinkInner('storevisitor')"><img alt=""
					src="<c:url value='/resources/images/icons/color/staff-notice.png'/>"
					height="50" width="50"><span id="mNavVisitorRecord">
						Visitor Master</span></a></li>


			<li><a title="View and update Visitor Visit record" href="#"
				onclick="RenderLinkInner('visitorvisits')"><img alt=""
					src="<c:url value='/resources/images/icons/color/Attendence.png'/>"
					height="50" width="50"><span id="mNavUserRole">
						Visitor Entry Details</span></a></li>

			<%-- <li><a title="Visitor Parking" href="#"
				onclick="RenderLinkInner('visitorparking')"><img alt=""
					src="<c:url value='/resources/images/icons/color/my-account.png'/>"><span
					id="mNavUserGroup"> Visitor Parking</span></a></li> --%>





			

		</ul>
	</div>
	<div class="divider">
		<span></span>
	</div>
	
	