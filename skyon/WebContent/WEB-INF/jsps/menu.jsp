<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/javascript; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<title><spring:message code="project.title" text="default text" /></title>
<body>
<!-- <div id="alertsBox" title="Alert"></div> -->
	<!-- Top line begins -->
	<%-- <div id="top">
		<div class="wrapper">
			<a href="./home" title="" class="logo"><img
				src="<c:url value='/resources/images/ireo.png'/>" alt="" /></a>
			<div style="float: right; margin-left: 50px; margin-top: 10px;">
				<div style="display: inline-block; margin-bottom: -4px;"
					class="btn-group">
					<a href="#" data-toggle="dropdown" class="buttonM bDefault"><span
						class="icon-cog"></span><span>Language</span><span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="?language=en"><span class="icos-marker"></span>English</a></li>
						<li><a href="?language=hin"><span class="icos-marker"></span>Hindi</a></li>
					</ul>
				</div>
			</div>
			<!-- Right top nav -->
			<div class="topNav">

				<ul class="userNav">
					<li><a href="./logout"  class="logout" title="logout" id="logout"></a></li>
					<li class="showTabletP"><a href="#" title="" class="sidebar"></a></li>
				</ul>
				<a title="" class="iButton"></a> <a title="" class="iTop"></a>
				<div class="topSearch">
					<div class="topDropArrow"></div>
					<form action="">
						<input type="text" placeholder="search..." name="topSearch" /> <input
							type="submit" value="" />
					</form>
				</div>
			</div>

			<!-- Responsive nav -->
			<ul class="altMenu">
				<li><a href="#" onclick="changeMenu(this.id);" id="usermgm" title="" class="exp" id="current"><spring:message code="label.usermodule" text="System Security" /></a>
					<ul>
						<li><a href="#" onclick="RenderLink('dashboard',this.id);" id="db"><spring:message code="project.dashboard" text="default text" /></a></li>
						<li><a href="#" onclick="RenderLink('usermanagement',this.id);" id="um"><spring:message code="label.usermanagement" text="User Access Management" /></a></li>
						<li><a href="#" onclick="RenderLink('settings',this.id);" id="pam"><spring:message code="label.prodcutaccess" text="Product Access Management" /></a></li>
					</ul></li>
				 
			</ul>
		</div>
	</div> --%>
	<!-- Top line ends -->

<!-- 
/resources/images/userLogin2.png -->

	<!-- Sidebar begins -->
	<div id="sidebar">
		<div class="mainNav">
			<div class="user">
				<a title="" class="leftUserDrop"><img src="<c:url value='/ldap/getuserimage/'/>"
					width="72" height="70" />
				</a><span>${userName}</span>
				<ul class="leftUser">
					<li><a href="#" onclick="RenderLinkInner('myprofile')" title="" class="sProfile">My profile</a></li>
					<li><a href="#" onclick="RenderLinkInner('messages')" title="" class="sMessages">Messages</a></li>
					<li><a href="#" title="" class="sSettings"
						onclick="RenderLinkInner('changePassword');" id="cp">ChangePassword</a></li>
					<li><a href="./logout" title="" class="sLogout">Logout</a></li>
					
				</ul>
			</div>

			<!-- Responsive nav -->
			<div class="altNav">
				<div class="userSearch">
					<form action="">
						<input type="text" placeholder="search..." name="userSearch" /> <input
							type="submit" value="" />
					</form>
				</div>

				<!-- User nav -->
				<ul class="userNav">
					<li><a href="#" title="" class="profile"></a></li>
					<li><a href="#" title="" class="messages"></a></li>
					<li><a href="#" title="" class="settings"></a></li>
					<li><a href="#" title="" class="logout"></a></li>
				</ul>
			</div>

			<!-- Main nav -->
			<ul class="nav">
				<li><a href="#" title="" onclick="changeMenu(this.id);"
					id="usermgm" class="active"><img
						src="<c:url value='/resources/images/icons/mainnav/dashboard.png'/>"
						alt="" /><span><spring:message code="label.usermodule" text="System Security" /></span></a></li>
				<li><a href="#" onclick="changeMenu(this.id);" id="customeroccupancymgm"
					class="notactive" title=""><img
						src="<c:url value='/resources/images/icons/mainnav/dashboard.png'/>"
						alt="" /><span>Customer Occupancy</span></a></li>		
				<li><a href="#" onclick="changeMenu(this.id);" id="test"
					class="notactive" title=""><img
						src="<c:url value='/resources/images/icons/mainnav/dashboard.png'/>"
						alt="" /><span>Mail Room</span></a></li>
						
						<li><a href="#" onclick="changeMenu(this.id);" id="procurementmgm"
					class="notactive" title=""><img
						src="<c:url value='/resources/images/icons/mainnav/dashboard.png'/>"
						alt="" /><span>Procurement Process</span></a></li>
				
				<li><a href="#" onclick="changeMenu(this.id);" id="manpowermgm"
					class="notactive" title=""><img
						src="<c:url value='/resources/images/icons/mainnav/dashboard.png'/>"
						alt="" /><span>Manpower</span></a></li>
				<li><a href="#" onclick="changeMenu(this.id);" id="assetmgm"
					class="notactive" title=""><img
						src="<c:url value='/resources/images/icons/mainnav/dashboard.png'/>"
						alt="" /><span>Asset</span></a></li>
				<li><a href="#" onclick="changeMenu(this.id);" id="visitormgm"
					class="notactive" title=""><img
						src="<c:url value='/resources/images/icons/mainnav/dashboard.png'/>"
						alt="" /><span>Visitor</span></a></li>	
				<li><a href="#" onclick="changeMenu(this.id);" id="timeAndAttendancemgm"
					class="notactive" title=""><img
						src="<c:url value='/resources/images/icons/mainnav/dashboard.png'/>"
						alt="" /><span>Time & Attendance</span></a></li>
				<li><a href="#" onclick="changeMenu(this.id);" id="parkingmgm"
					class="notactive" title=""><img
						src="<c:url value='/resources/images/icons/mainnav/dashboard.png'/>"
						alt="Parking Slot Creation And Assign,Vehicle Registration,Wrong Parking Details" /><span>Parking</span></a></li>			
									
				<li><a href="#" onclick="changeMenu(this.id);" id="FileDrawMgm"
					class="notactive" title=""><img
						src="<c:url value='/resources/images/icons/mainnav/tables.png'/>"
						alt="" /><span>File Drawing</span></a></li>		
			</ul>
			<button id="toggle" style="display: none;">Turn On</button>
			<br />
			<button id="showLeftPush" style="display: none;">Show/Hide
				Left Push Menu</button>
		</div>
		<script type="text/javascript"
			src="<c:url value='/resources/classie.js'/>"></script>
		<!-- Secondary nav -->
		<div class="secNav" id="cbp-spmenu-s1" style="display: none;height:100%;">
			<div class="secWrapper" id="usermgmmenu">
				<div class="secTop">
					<div class="balance">
						<div class="balInfo">
							Today:<br />
							<script  type="text/javascript">
						var monthArray = new Array("January", "February", "March", "April", "May","June", "July", "August", "September","October", "November", "December");
						var dayArray = new Array("Sun","Mon","Tue","Wed","Thu","Fri","Sat");
						var today = new Date();
						var todayMonth = today.getMonth();
						var todayDate = today.getDate();
						var todayYear = today.getFullYear();
						var todayDay = today.getDay();
						document.write("<b>"+monthArray[todayMonth] + " " + todayDate + ", "+dayArray[todayDay]+" " + todayYear+"<b>");
					</script>
						</div>
					</div>
					<a href="#" onclick="pinUnpin()" class="triangle-red"></a>
				</div>

				<!-- Tabs container -->
				<div id="tab-container" class="tab-container">
					<ul class="iconsLine ic3 etabs">
						<li><a href="#general" title=""><span
								class="icos-fullscreen"></span></a></li>
						<li><a href="#alt1" title=""><span class="icos-user"></span></a></li>
						<li><a href="#alt2" title=""><span class="icos-archive"></span></a></li>
					</ul>


					<div id="general">
						<div class="divider">
							<span></span>
						</div>
							<!-- onclick="RenderLink('dashboard',this.id,'subNav_syssecurity');"
								 onclick="RenderLink('usermanagement',this.id,'subNav_syssecurity');"
							 -->
						<div id="general">
							<ul class="subNav" id="subNav_syssecurity">
								<li><a href="dashboard" 
									id="db" title="" class="this"><span class="icos-fullscreen"></span>
									<spring:message code="project.dashboard" text="default text" /></a></li>
								<li><a href="usermanagement"  id="um"
									title="" class="notthis"><span class="icos-fullscreen"></span><spring:message code="label.usermanagement" text="User Access Management" /></a></li>
								<li><a href="#"
									onclick="RenderLink('settings',this.id,'subNav_syssecurity');" id="pam"
									class="notthis" title=""><span class="icos-images2"></span><spring:message code="label.prodcutaccess" text="Product Access Management" /></a></li>
			     			</ul>
						</div>

					</div>

					<div id="alt1">

						<div class="divider">
							<span></span>
						</div>
						<!-- Sidebar user list -->
						<ul class="userList">
							<li><a href="#" title=""> <img
									src="<c:url value='/resources/images/live/face1.png'/>" alt="" />
									<span class="contactName"> <strong>Eugene
											Kopyov <span>(5)</span>
									</strong> <i>web &amp; ui designer</i>
								</span> <span class="status_away"></span>
							</a></li>
						</ul>
					</div>


					<div id="alt2">

						<div class="divider">
							<span></span>
						</div>
						<!-- Sidebar datepicker -->
						<div class="sideWidget">
							<div class="inlinedate"></div>
						</div>

					</div>
				</div>
			</div>
			<div class="secWrapper" id="testmenu" style="display: none;">
				<div class="secTop">
					<div class="balance">
						<div class="balInfo">
							Today:<br />
							<script  type="text/javascript">
						document.write("<b>"+monthArray[todayMonth] + " " + todayDate + ", "+dayArray[todayDay]+" " + todayYear+"<b>");
					</script>
						</div>
						<!-- <div class="balAmount"><span class="balBars">5,10,15,20,18,16,14,20,15,16,12,10</span><span>$58,990</span></div> -->
					</div>
					<a href="#" onclick="pinUnpin()" class="triangle-red"></a>
				</div>
				<div id="tab-container" class="tab-container">
					<ul class="iconsLine ic3 etabs">
						<li></li>
						<li></li>
						<li></li>
					</ul>
				<div id="general">
					<ul class="subNav" id="subNav_mailroom">
						<li><a href="#" onclick="RenderLink('mailroomdashboard',this.id,'subNav_mailroom');" id="mailroomdashboard" title="" class="notthis"><span class="icos-fullscreen"></span>Dashboard</a></li>
						<li><a href="#" onclick="RenderLink('mailroom',this.id,'subNav_mailroom');" id="mailroomdetails" title="" class="notthis"><span class="icos-fullscreen"></span>Incoming Mails</a></li>
						<li><a href="#" onclick="RenderLink('mailroomDelivery',this.id,'subNav_mailroom');" id="mailroomdeliverylist" title="" class="notthis"><span class="icos-fullscreen"></span>Delivery List</a></li>
					</ul>
				</div>
				</div>
			</div>
			
			<!-- @@@@@@@@@@@@@@@@@@ -->
			
			<div class="secWrapper" id="procurementmenu" style="display: none;">
				<div class="secTop">
					<div class="balance">
						<div class="balInfo">
							Today:<br />
							<script  type="text/javascript">
						document.write("<b>"+monthArray[todayMonth] + " " + todayDate + ", "+dayArray[todayDay]+" " + todayYear+"<b>");
					</script>
						</div>
						<!-- <div class="balAmount"><span class="balBars">5,10,15,20,18,16,14,20,15,16,12,10</span><span>$58,990</span></div> -->
					</div>
					<a href="#" onclick="pinUnpin()" class="triangle-red"></a>
				</div>
				 <div id="tab-container" class="tab-container">
					<ul class="iconsLine ic3 etabs">
						<li></li>
						<li></li>
						<li></li>
					</ul>
				<div id="general">
					<ul class="subNav" id="subNav_procurement">
						<li><a href="#" onclick="RenderLink('procurementdashboard',this.id,'subNav_procurement');" id="procurementdashboard" title="" class="notthis"><span class="icos-fullscreen"></span>Dashboard</a></li>
						<li><a href="#" onclick="RenderLink('requisition',this.id,'subNav_procurement');" id="procurementlink" title="" class="notthis"><span class="icos-fullscreen"></span>Procurement Process</a></li>
						<!-- <li><a href="#" onclick="RenderLink('itemMaster',this.id,'subNav_asset');" id="assetloclink" title="" class="notthis"><span class="icos-fullscreen"></span>Item Master</a></li> -->
					</ul>
				</div>
				</div>
				</div>
			
			
			<!-- @@@@@@@@@@@@@@@ -->
			
			
			
			
			<div class="secWrapper" id="customeroccupancymenu" style="display: none;">
				<div class="secTop">
					<div class="balance">
						<div class="balInfo">
							Today:<br />
							<script  type="text/javascript">
						document.write("<b>"+monthArray[todayMonth] + " " + todayDate + ", "+dayArray[todayDay]+" " + todayYear+"<b>");
					</script>
						</div>
						<!-- <div class="balAmount"><span class="balBars">5,10,15,20,18,16,14,20,15,16,12,10</span><span>$58,990</span></div> -->
					</div>
					<a href="#" onclick="pinUnpin()" class="triangle-red"></a>
				</div>
				<div id="tab-container" class="tab-container">
					<ul class="iconsLine ic3 etabs">
						<li></li>
						<li></li>
						<li></li> 
					</ul>
				<div id="general">
					<ul class="subNav" id="subNav_com">
						<li><a href="comdashboard" onclick="RenderLink('comdashboard',this.id,'subNav_com');" id="comdashboard" title="" class="notthis"><span class="icos-fullscreen"></span>Dashboard</a></li>
						<li><a href="comproject" onclick="RenderLink('comproject',this.id,'subNav_com');" id="comproject" title="" class="notthis"><span class="icos-fullscreen"></span>Project</a></li>
						<li><a href="persons" onclick="RenderLink('persons',this.id,'subNav_com');" id="person" title="" class="notthis"><span class="icos-fullscreen"></span>Persons</a></li>
						<li><a href="comsettings" onclick="RenderLink('comsettings',this.id,'subNav_com');" id="property" title="" class="notthis"><span class="icos-fullscreen"></span>Settings</a></li>
						<!-- <li><a href="#" onclick="RenderLink('usersNew',this.id,'subNav_com');" id="usersNew" title="" class="notthis"><span class="icos-fullscreen"></span>Users New</a></li> -->
						<li><a href="comaccesscards" onclick="RenderLink('comaccesscards',this.id,'subNav_com');" id="accessCards" title="" class="notthis"><span class="icos-fullscreen"></span>Access Cards</a></li>
					</ul>
				</div>
				</div>
			</div>
			
			<div class="secWrapper" id="manpowermenu" style="display: none;">
				<div class="secTop">
					<div class="balance">
						<div class="balInfo">
							Today:<br />
							<script  type="text/javascript">
						document.write("<b>"+monthArray[todayMonth] + " " + todayDate + ", "+dayArray[todayDay]+" " + todayYear+"<b>");
					</script>
						</div>
						<!-- <div class="balAmount"><span class="balBars">5,10,15,20,18,16,14,20,15,16,12,10</span><span>$58,990</span></div> -->
					</div>
					<a href="#" onclick="pinUnpin()" class="triangle-red"></a>
				</div>
				 <div id="tab-container" class="tab-container">
					<ul class="iconsLine ic3 etabs">
						<li></li>
						<li></li>
						<li></li>
					</ul>
				<div id="general">
					<ul class="subNav" id="subNav_manpower">
						<li><a href="#" onclick="RenderLink('manpowerdashboard',this.id,'subNav_manpower');" id="manpowerdashboard" title="" class="notthis"><span class="icos-fullscreen"></span>Dashboard</a></li>
						<li><a href="#" onclick="RenderLink('manpowerindex',this.id,'subNav_manpower');" id="manpowerlink" title="" class="notthis"><span class="icos-fullscreen"></span>Staff Master</a></li>
						<li><a href="#" onclick="RenderLink('manpowerse',this.id,'subNav_manpower');" id="manpowerlink" title="" class="notthis"><span class="icos-fullscreen"></span>Staff Experience</a></li>
						<li><a href="#" onclick="RenderLink('manpowerst',this.id,'subNav_manpower');" id="manpowerlink" title="" class="notthis"><span class="icos-fullscreen"></span>Staff Training</a></li>
						<li><a href="#" onclick="RenderLink('manpowersn',this.id,'subNav_manpower');" id="manpowerlink" title="" class="notthis"><span class="icos-fullscreen"></span>Staff Notices</a></li>
					
					</ul>
				</div>
				</div>
			</div>
			
			<div class="secWrapper" id="assetmenu" style="display: none;">
				<div class="secTop">
					<div class="balance">
						<div class="balInfo">
							Today:<br />
							<script  type="text/javascript">
						document.write("<b>"+monthArray[todayMonth] + " " + todayDate + ", "+dayArray[todayDay]+" " + todayYear+"<b>");
					</script>
						</div>
						<!-- <div class="balAmount"><span class="balBars">5,10,15,20,18,16,14,20,15,16,12,10</span><span>$58,990</span></div> -->
					</div>
					<a href="#" onclick="pinUnpin()" class="triangle-red"></a>
				</div>
				 <div id="tab-container" class="tab-container">
					<ul class="iconsLine ic3 etabs">
						<li></li>
						<li></li>
						<li></li>
					</ul>
				<div id="general">
					<ul class="subNav" id="subNav_asset">
						<li><a href="#" onclick="RenderLink('assetdashboard',this.id,'subNav_asset');" id="assetdashboard" title="" class="notthis"><span class="icos-fullscreen"></span>Dashboard</a></li>
						<li><a href="#" onclick="RenderLink('assetindex',this.id,'subNav_asset');" id="assetlink" title="" class="notthis"><span class="icos-fullscreen"></span>Asset Category Management</a></li>
						<li><a href="#" onclick="RenderLink('assetlocindex',this.id,'subNav_asset');" id="assetloclink" title="" class="notthis"><span class="icos-fullscreen"></span>Asset Location Management</a></li>
					</ul>
				</div>
				</div>
				</div>
			
			<div class="secWrapper" id="visitormenu" style="display: none;">
				<div class="secTop">
					<div class="balance">
						<div class="balInfo">
							Today:<br />
							<script  type="text/javascript">
						document.write("<b>"+monthArray[todayMonth] + " " + todayDate + ", "+dayArray[todayDay]+" " + todayYear+"<b>");
					</script>
						</div>
						<!-- <div class="balAmount"><span class="balBars">5,10,15,20,18,16,14,20,15,16,12,10</span><span>$58,990</span></div> -->
					</div>
					<a href="#" onclick="pinUnpin()" class="triangle-red"></a>
				</div>
				 <div id="tab-container" class="tab-container">
					<ul class="iconsLine ic3 etabs">
						<li></li>
						<li></li>
						<li></li>
					</ul>
				<div id="general">
					<ul class="subNav" id="subNav_visitors">
						<li><a href="#" onclick="RenderLink('visitorsdashboard',this.id,'subNav_visitors');" id="visitorsdashboard" title="" class="notthis"><span class="icos-fullscreen"></span>Dashboard</a></li>
						<li><a href="#" onclick="RenderLink('visitorwizard',this.id,'subNav_visitors');" id="visitorwizardlink" title="" class="notthis"><span class="icos-fullscreen"></span>Visitor Wizard</a></li>
						<li><a href="#" onclick="RenderLink('visitordetails',this.id,'subNav_visitors');" id="visitordetailslink" title="" class="notthis"><span class="icos-fullscreen"></span>Visitors</a></li>
						<li><a href="#" onclick="RenderLink('storevisitor',this.id,'subNav_visitors');" id="storevisitorlink" title="" class="notthis"><span class="icos-fullscreen"></span>Visitor Master</a></li>
<li><a href="#" onclick="RenderLink('visitorvisits',this.id,'subNav_visitors');" id="visitorvisitslink" title="" class="notthis"><span class="icos-fullscreen"></span>Visitor Entry Details</a></li>
						<!-- <li><a href="#" onclick="RenderLink('visitorparking',this.id,'subNav_visitors');" id="visitorparkinglink" title="" class="notthis"><span class="icos-fullscreen"></span>Visitor Parking</a></li> -->
					</ul>
				</div>
				</div>
			</div>
			
			<div class="secWrapper" id="time_attendancemenu" style="display: none;">
				<div class="secTop">
					<div class="balance">
						<div class="balInfo">
							Today:<br />
							<script  type="text/javascript">
						document.write("<b>"+monthArray[todayMonth] + " " + todayDate + ", "+dayArray[todayDay]+" " + todayYear+"<b>");
					</script>
						</div>
						<!-- <div class="balAmount"><span class="balBars">5,10,15,20,18,16,14,20,15,16,12,10</span><span>$58,990</span></div> -->
					</div>
					<a href="#" onclick="pinUnpin()" class="triangle-red"></a>
				</div>
				 <div id="tab-container" class="tab-container">
					<ul class="iconsLine ic3 etabs">
						<li></li>
						<li></li>
						<li></li>
					</ul>
				<div id="general">
					<ul class="subNav" id="subNav_timeAtt">
						<li><a href="#" onclick="RenderLink('timeAttdashboard',this.id,'subNav_timeAtt');" id="timeAttdashboard" title="" class="notthis"><span class="icos-fullscreen"></span>Dashboard</a></li>
						<!-- <li><a href="#" onclick="RenderLink('time&AttendanceManagement',this.id,'subNav_timeAtt');" id="timeAttdashboard" title="" class="notthis"><span class="icos-fullscreen"></span>Time & Attendance</a></li> -->
						<li><a href="#" onclick="RenderLink('patrolTracks',this.id,'subNav_timeAtt');" id="timeAttdashboard" title="" class="notthis"><span class="icos-fullscreen"></span>Patrol Tracks</a></li>
						<li><a href="#" onclick="RenderLink('PatrolTrackPoints',this.id,'subNav_timeAtt');" id="timeAttdashboard" title="" class="notthis"><span class="icos-fullscreen"></span>PatrolTrack Points</a></li>
						<li><a href="#" onclick="RenderLink('patrolTrackStaff',this.id,'subNav_timeAtt');" id="timeAttdashboard" title="" class="notthis"><span class="icos-fullscreen"></span>Patrol Staff</a></li>
						<li><a href="#" onclick="RenderLink('patrolTrackAlert',this.id,'subNav_timeAtt');" id="timeAttdashboard" title="" class="notthis"><span class="icos-fullscreen"></span>Patrol Alerts</a></li>
						<li><a href="#" onclick="RenderLink('staff',this.id,'subNav_timeAtt');" id="timeAttdashboard" title="" class="notthis"><span class="icos-fullscreen"></span>Staff</a></li>
						
					</ul>
				</div>
				</div>
			</div>
			<div class="secWrapper" id="parkingmenu" style="display: none;">
				<div class="secTop">
					<div class="balance">
						<div class="balInfo">
							Today:<br />
							<script  type="text/javascript">
						document.write("<b>"+monthArray[todayMonth] + " " + todayDate + ", "+dayArray[todayDay]+" " + todayYear+"<b>");
					</script>
						</div>
						<!-- <div class="balAmount"><span class="balBars">5,10,15,20,18,16,14,20,15,16,12,10</span><span>$58,990</span></div> -->
					</div>
					<a href="#" onclick="pinUnpin()" class="triangle-red"></a>
				</div>
				 <div id="tab-container" class="tab-container">
					<ul class="iconsLine ic3 etabs">
						<li></li>
						<li></li>
						<li></li>
					</ul>
					<div id="general">
						<ul class="subNav" id="subNav_parking">
							<li><a href="#" onclick="RenderLink('parkingslotheader',this.id,'subNav_parking');" id="parkingslots_parking" title="" class="notthis"><span class="icos-fullscreen"></span>Parking Slots</a></li>
							<!-- <li><a href="#" onclick="RenderLink('parkingslotallotment',this.id,'subNav_parking');" id="parkingslots_parking" title="" class="notthis"><span class="icos-fullscreen"></span>Parking Slots Allotment</a></li>
						 -->	<li><a href="#" onclick="RenderLink('vehicles',this.id,'subNav_parking');" id="vehicles_parking" title="" class="notthis"><span class="icos-fullscreen"></span>Vehicles</a></li>
							<li><a href="#" onclick="RenderLink('wrongparking',this.id,'subNav_parking');" id="wrongparking_parking" title="" class="notthis"><span class="icos-fullscreen"></span>Wrong Parking</a></li>
						</ul>
					</div>
				</div>
			</div>
			
			<div class="secWrapper" id="documentrepomenu" style="display: none;">
				<div class="secTop">
					<div class="balance">
						<div class="balInfo">
							Today:<br />
							<script  type="text/javascript">
						document.write("<b>"+monthArray[todayMonth] + " " + todayDate + ", "+dayArray[todayDay]+" " + todayYear+"<b>");
					</script>
						</div>
						<!-- <div class="balAmount"><span class="balBars">5,10,15,20,18,16,14,20,15,16,12,10</span><span>$58,990</span></div> -->
					</div>
					<a href="#" onclick="pinUnpin()" class="triangle-red"></a>
				</div>
				 <div id="tab-container" class="tab-container">
					<ul class="iconsLine ic3 etabs">
						<li></li>
						<li></li>
						<li></li>
					</ul>
				<div id="general">
					<ul class="subNav" id="subNav_FileDrawMgm">
						<!-- <li><a href="#" onclick="RenderLink('documentrepository',this.id,'subNav_FileDrawMgm');" id="filedrawrepo" title="" class="notthis"><span class="icos-fullscreen"></span>Document Repository</a></li> -->
					</ul>
				</div>
				</div>
			</div>
			
			
		</div>
	</div>
	<!-- Sidebar ends -->
	<!-- <div id="alertsBox" title="Alert"></div> -->
	<!-- <div id="contentrender" class="cbp-spmenu-push"
		style="margin-left: -241px;"></div> -->
	<script type="text/javascript">
	var temp = 0;
	var menuLeft = document.getElementById( 'cbp-spmenu-s1' ),
			 
	showLeftPush = document.getElementById( 'showLeftPush' ),
	 
	body = document.getElementById('content');
          
 
	showLeftPush.onclick = function() {
   		temp = 1;
	 
		classie.toggle( this, 'active' );
	 
		//classie.toggle(body, 'cbp-spmenu-push-toright' );
		$(".secNav").show();
		$("#content").css("margin-left","326px");			 
	};
   
$("#toggle").click(function() {
    var button = $(this),        
        enable = button.text() == "Turn Off";
    
    if (enable) {
    	$(".secNav").hide();

	 	var menuLeft = document.getElementById( 'cbp-spmenu-s1' ),
 

		body = document.getElementById('content');
       

		classie.toggle( this, 'active' );
 
		//classie.toggle( body, 'cbp-spmenu-push-toright' );
		$("#content").css("margin-left","100px");
        
    } 
    else {


		$(".secNav").show();

			  var menuLeft = document.getElementById( 'cbp-spmenu-s1' ),
		 
		//showLeftPush = document.getElementById( 'showLeftPush' ),
		 
		body = document.getElementById('content');
               

		classie.toggle( this, 'active' );
		 
		//classie.toggle( body, 'cbp-spmenu-push-toright' );
		$("#content").css("margin-left","326px");
    }

    button.text(enable ? "Turn On" : "Turn Off");
});

var temp = 0;
var pinStatus = 0;

function onloadTest()
{
	
	//$("#contentrender").load("./dashboard");
	$.ajax({
	type : "POST",
		url : "./readPinStatus",
		dataType : 'text',
		success : function(response)
		{
			//alert(response);
			pinStatus = response;
			if(pinStatus== 1)
			{
				$("#showLeftPush").click();
			}
		}
	});	  
} 

function changeMenu(param)
{     
	
	$.ajax({
		type : "POST",
		url : "./viewAuthentication",
		data : {
			 accessUrl : param
		},
		success : function(response)
		{
			 if(response == "timeout")
			  {
				  alert("Session Timed out Login Again");       
			      window.location.href = "./logout";
			  }
			 
		}
	});	
	  var sidebar = $('#sidebar');
	  	sidebar.delegate('a.notactive','click',function(){
	      sidebar.find('.active').toggleClass('active notactive');
	      $(this).toggleClass('active notactive');
	});
	if((temp == 0) && pinStatus == 0)
	{
     $(".secNav").show();
     $(".triangle-red").toggleClass("triangle-blue");
					 temp = 1;
    }
	 else if(temp ==1 && pinStatus == 0)
	 {	 	  
	    $(".secNav").hide();
		temp = 0;
   	 }

	 else if(temp == 1 && pinStatus==1)
	 {
	       if(pinStatus==0){
        	 $("#toggle").click();
		   }
		   $(".secNav").show();
		   temp = 1;
	 }

	if(param == "usermgm")
	{
		$("#testmenu").hide();
		$("#usermgmmenu").show();
		$("#manpowermenu").hide();
		$("#assetmenu").hide();
		$("#visitormenu").hide();
		$("#time_attendancemenu").hide();
		$("#parkingmenu").hide();
		$("#customeroccupancymenu").hide();
		$("#documentrepomenu").hide();
		$("#procurementmenu").hide();
	}
	else if(param == "test")
	{
		$("#testmenu").show();
		$("#usermgmmenu").hide();
		$("#manpowermenu").hide();
		$("#assetrmenu").hide();
		$("#visitormenu").hide();
		$("#time_attendancemenu").hide();
		$("#parkingmenu").hide();
		$("#customeroccupancymenu").hide();
		$("#documentrepomenu").hide();
		$("#procurementmenu").hide();
	}
	else if(param == "manpowermgm")
	{
		$("#manpowermenu").show();
		$("#assetmenu").hide();
		$("#testmenu").hide();
		$("#usermgmmenu").hide();
		$("#visitormenu").hide();
		$("#time_attendancemenu").hide();
		$("#parkingmenu").hide();
		$("#customeroccupancymenu").hide();
		$("#documentrepomenu").hide();
		$("#procurementmenu").hide();
	}
	else if(param == "visitormgm")
	{
		$("#visitormenu").show();
		$("#manpowermenu").hide();
		$("#assetmenu").hide();
		$("#testmenu").hide();
		$("#usermgmmenu").hide();
		$("#time_attendancemenu").hide();
		$("#parkingmenu").hide();
		$("#customeroccupancymenu").hide();
		$("#documentrepomenu").hide();
		$("#procurementmenu").hide();
	}	
	else if(param == "timeAndAttendancemgm")
	{
		$("#time_attendancemenu").show();
		$("#visitormenu").hide();
		$("#manpowermenu").hide();
		$("#assetmenu").hide();
		$("#testmenu").hide();
		$("#usermgmmenu").hide();
		$("#parkingmenu").hide();
		$("#customeroccupancymenu").hide();
		$("#documentrepomenu").hide();
		$("#procurementmenu").hide();
	}
	else if(param == "parkingmgm")
	{
		
		$("#parkingmenu").show();
		$("#time_attendancemenu").hide();
		$("#menu").hide();
		$("#manpowermenu").hide();
		$("#assetmenu").hide();
		$("#testmenu").hide();
		$("#usermgmmenu").hide();
		$("#customeroccupancymenu").hide();
		$("#documentrepomenu").hide();
		$("#procurementmenu").hide();
	}
	else if(param == "customeroccupancymgm")
	{
		$("#customeroccupancymenu").show();
		$("#parkingmenu").hide();
		$("#time_attendancemenu").hide();
		$("#visitormenu").hide();
		$("#manpowermenu").hide();
		$("#assetmenu").hide();
		$("#testmenu").hide();
		$("#usermgmmenu").hide();
		$("#documentrepomenu").hide();
		$("#procurementmenu").hide();
	}
	else if(param == "assetmgm")
	{
		$("#customeroccupancymenu").hide();
		$("#parkingmenu").hide();
		$("#time_attendancemenu").hide();
		$("#visitormenu").hide();
		$("#manpowermenu").hide();
		$("#assetmenu").show();
		$("#testmenu").hide();
		$("#usermgmmenu").hide();
		$("#documentrepomenu").hide();
		$("#procurementmenu").hide();
	}
	else if(param == "procurementmgm")
	{
		$("#customeroccupancymenu").hide();
		$("#parkingmenu").hide();
		$("#time_attendancemenu").hide();
		$("#visitormenu").hide();
		$("#manpowermenu").hide();
		$("#assetmenu").hide();
		$("#procurementmenu").show();
		$("#testmenu").hide();
		$("#usermgmmenu").hide();
		$("#documentrepomenu").hide();
	}

	else if(param == "FileDrawMgm")
	{
		$("#documentrepomenu").show();
		$("#customeroccupancymenu").hide();
		$("#parkingmenu").hide();
		$("#time_attendancemenu").hide();
		$("#visitormenu").hide();
		$("#manpowermenu").hide();
		$("#assetmenu").hide();
		$("#testmenu").hide();
		$("#usermgmmenu").hide();
		$("#procurementmenu").hide();
	}		
}

function RenderLink(param,id,subNavId)
{
	var subNav = $('#'+subNavId);
	 subNav.delegate('a.notthis','click',function(){
	 subNav.find('.this').toggleClass('this notthis');
     $(this).toggleClass('this notthis');
	});
	$("#content").css("margin-left","100px");
	if((temp == 0) && pinStatus == 0)
	{
       $(".secNav").show();
       $(".triangle-red").toggleClass("triangle-blue");
					 temp = 1;
     }
	 else if(temp ==1 && pinStatus == 0)
	 {
		 $(".secNav").hide();
		 temp = 0;
     }
	 else if(temp == 1 && pinStatus==1)
	 {
		 $("#showLeftPush").click();
	       if(pinStatus==0){
         	  $("#toggle").click();
		   }
		   $(".secNav").show();
		   temp = 1;
	 }

	$.ajax({
			type : "POST",
			url : "./viewAuthentication",
			data : {
				 accessUrl : param
			},
			success : function(response)
			{
				//alert(response);
				if(response == "success")
				  {
					 var loadUrl = "./"+param;
					// $("#contentrender").load(loadUrl);
					$body = $("body");
    	 			$body.addClass("loading");

					 $.ajax({
				            type: "GET",
				            url: loadUrl,
				            success: function(response) {
				                $("#contentrender").html( response );
				            },
					        complete:function()
							{
								//alert("Ajax call complete");
								$body.removeClass("loading");
							}
				        });
						
				  }	
				  else if(response == "timeout")
				  {
					  alert("Session Timed out Login Again");       
				     window.location.href = "./logout";
				  }	
				  else if(response == "accessDenied")
				  {
					  response = "./"+response;
					  $("#contentrender").load(response);
				  }/* else{
					  alert(response);
				  } */
			}
		});	
}
function RenderLinkInner(param)
{
	$.ajax({
		type : "POST",
		url : "./viewAuthentication",
		data : {
			 accessUrl : param
		},
		success : function(response)
		{
				 //alert(response);       
			if(response == "success")
			  {
				 var loadUrl = "./"+param;
				 //$("#contentrender").load(loadUrl);

				 $body = $("body");
 	 			$body.addClass("loading");

					 $.ajax({
				            type: "GET",
				            url: loadUrl,
				            success: function(response) {
				                $("#contentrender").html( response );
				            },
					        complete:function()
							{
								//alert("Ajax call complete");
								$body.removeClass("loading");
							}
				        });
					
			  }	
			  else if(response == "timeout")
			  {
				  alert("Session Timed out Login Again");       
			      window.location.href = "./logout";
			  }	
			  else if(response == "accessDenied")
			  {
				  response = "./"+response;
				  $("#contentrender").load(response);
			  }/* else{
				  alert(response);
			  } */
		}
	});
}

function pinUnpin()
{
   
  //alert(pinStatus+"==="+temp);
   if(pinStatus == 0){
	    
       $("#showLeftPush").click();
	   pinStatus = 1;
    }
	else if (pinStatus == 1 && temp == 1)
	{
	 $("#showLeftPush").click();
     $(".secNav").hide();
     $("#content").css("margin-left","100px");
					 temp = 0;
					 pinStatus = 0;
	}
	else
	{
      $("#showLeftPush").click();
	  pinStatus=0;
	}      
   $.ajax({
		type :"POST",
		url : "./userpinstatus",
		data : {
			pinStatus : pinStatus
			},
	   success : function(response)
	   {

	   }
	  });
}

//tooltip for logout

/* $(document).ready(function() {
    var tooltip = $("#agglomerations").kendoTooltip({
        filter: "a",
        width: 120,
        position: "top"
    }).data("kendoTooltip");

    tooltip.show($("#logout"));

    $("#agglomerations").find("a").click(false);
}); */

function addAccess(addUrl,gridName){
   $.ajax({
			type : "POST",
			url : addUrl,
			success : function(response) {
				if (response == "false") {
					$("#alertsBox").html("");
					$("#alertsBox").html("Access Denied");
					$("#alertsBox").dialog({
						modal: true,
						buttons: {
							"Close": function() {
								$( this ).dialog( "close" );
							}
						}
					}); 
					
					var grid = $(gridName).data("kendoGrid");
					grid.cancelRow();
					grid.close();
					
					
				}else if (response == "timeout") {
					$("#alertsBox").html("");
					$("#alertsBox").html("Session Timeout Please Login Again");
					$("#alertsBox").dialog({
						modal: true,
						buttons: {
							"Close": function() {
								$( this ).dialog( "close" );
							}
						}
					}); 
					window.location.href = "./logout";
					
				}
			}
		});   
}

function editAccess(editUrl,gridName){
	
	  $.ajax({
			type : "POST",
			url : editUrl,
			success : function(response) {
				if (response == "false") {
					$("#alertsBox").html("");
					$("#alertsBox").html("Access Denied");
					$("#alertsBox").dialog({
						modal: true,
						buttons: {
							"Close": function() {
								$( this ).dialog( "close" );
							}
						}
					}); 
					var grid = $(gridName).data("kendoGrid");
					grid.cancelRow();
					grid.close();
				}else if (response == "timeout") {
					$("#alertsBox").html("");
					$("#alertsBox").html("Session Timeout Please Login Again");
					$("#alertsBox").dialog({
						modal: true,
						buttons: {
							"Close": function() {
								$( this ).dialog( "close" );
							}
						}
					}); 
					window.location.href = "./logout";
				}
			}
		});
	
	
}



</script>
</body>
</html>

