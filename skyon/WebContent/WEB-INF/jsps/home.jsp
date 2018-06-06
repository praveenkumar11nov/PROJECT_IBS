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

<link rel="IREO icon" href="<c:url value='/resources/favicon.ico' />" type="image/x-icon"/>

<%@include file="links.jsp"%>
 
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/component.css'/>" />
	
	<!-- Urls for Common controller  -->
	<c:url value="/common/getAllChecks" var="allChecksUrl" />
	<c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />
</head>

<body onload="onloadTest();">
	<div id="top">
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
						<li><a href="#" onclick="RenderLink('usermanagement',this.id);" id="um"><spring:message code="label.usermanagement" text="User Management" /></a></li>
						<li><a href="#" onclick="RenderLink('settings',this.id);" id="pam"><spring:message code="label.prodcutaccess" text="Product Access Management" /></a></li>
					</ul></li>
				 
			</ul>
		</div>
	</div>
	<!-- Top line ends -->

	<!-- Sidebar begins -->
	<div id="sidebar">
		<div class="mainNav">
			<div class="user">
				<a title="" class="leftUserDrop"><img src="<c:url value='/ldap/getuserimage/'/>"
					width="72" height="70" />
				</a><span>${userName}</span>
				<ul class="leftUser">
					<li><a href="#" onclick="RenderLinkInner('myprofile')" title="" class="sProfile">My profile</a></li>
					<li><a href="#" onclick="RenderLinkInner('inbox')" title="" class="sMessages">Messages</a></li>
					<li><a href="#" title="" class="sSettings"
						onclick="RenderLinkInner('changePassword');" id="cp">Change Password</a></li>
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
			
			<%-- <a id="scrollUp" href="#"><img src="<c:url value='/resources/images/arrow-up.png'/>" alt="Up" /></a>
			<a id="scrollDown" href="#"><img src="<c:url value='/resources/images/arrow-down.png'/>" alt="Down" /></a> --%>
			
			<!-- Main nav -->
			<ul class="nav" style="overflow:hidden;height: 500px;">
				<li><a href="#" title="" onclick="changeMenu(this.id);"
					id="usermgm" class="active"><img
						src="<c:url value='/resources/images/icons/mainnav/icon-locked.png'/>" height="21" width="22"
						alt="" /><span><spring:message code="label.usermodule" text="System Security" /></span></a></li>
				<li><a href="#" onclick="changeMenu(this.id);" id="customeroccupancymgm"
					class="notactive" title=""><img
						src="<c:url value='/resources/images/icons/mainnav/ui.png'/>"
						alt="" /><span>Customer Occupancy</span></a></li>
						
				
				<%-- <li><a href="#" onclick="changeMenu(this.id);" id="inventorymgm"
					class="notactive" title=""><img
						src="<c:url value='/resources/images/icons/mainnav/icon-listimg.png'/>" height="30" width="25"
						alt="" /><span>Inventory</span></a></li> --%>				
								
				<%-- <li><a href="#" onclick="changeMenu(this.id);" id="test"
					class="notactive" title=""><img
						src="<c:url value='/resources/images/icons/mainnav/icon-email.png'/>" height="21" width="22"
						alt="" /><span>Mail Room</span></a></li> --%>
						
						<%-- <li><a href="#" onclick="changeMenu(this.id);" id="vendormgm"
					class="notactive" title=""><img
						src="<c:url value='/resources/images/icons/mainnav/icon-suitcase.png'/>" height="21" width="22"
						alt="" /><span>Vendor</span></a></li> --%>
						
						<%-- <li><a href="#" onclick="changeMenu(this.id);" id="procurementmgm"
					class="notactive" title=""><img
						src="<c:url value='/resources/images/icons/mainnav/statistics.png'/>"
						alt="" /><span>Procurement Process</span></a></li> --%>
				
				<li><a href="#" onclick="changeMenu(this.id);" id="manpowermgm"
					class="notactive" title=""><img
						src="<c:url value='/resources/images/icons/mainnav/icon-user2.png'/>" height="21" width="22"
						alt="" /><span>Manpower</span></a></li>
				<%-- <li><a href="#" onclick="changeMenu(this.id);" id="assetmgm"
					class="notactive" title=""><img
						src="<c:url value='/resources/images/icons/mainnav/icon-money3.png'/>" height="21" width="22"
						alt="" /><span>Asset</span></a></li> --%>
				<li><a href="#" onclick="changeMenu(this.id);" id="visitormgm"
					class="notactive" title=""><img
						src="<c:url value='/resources/images/icons/mainnav/icon-users2.png'/>" height="21" width="22"
						alt="" /><span>Visitor</span></a></li>	
				<%-- <li><a href="#" onclick="changeMenu(this.id);" id="timeAndAttendancemgm"
					class="notactive" title=""><img
						src="<c:url value='/resources/images/icons/mainnav/icon-alarm.png'/>" height="21" width="22"
						alt="" /><span>Time & Attendance</span></a></li> --%>
				<%-- <li><a href="#" onclick="changeMenu(this.id);" id="concirgemgm"
					class="notactive" title=""><img
						src="<c:url value='/resources/images/icons/mainnav/dashboard.png'/>"
						alt="" /><span>Concierge</span></a></li> --%>
				<li><a href="#" onclick="changeMenu(this.id);" id="parkingmgm"
					class="notactive" title=""><img
						src="<c:url value='/resources/images/icons/mainnav/icon-car.png'/>" height="21" width="30"
						alt="Parking Slot Creation And Assign,Vehicle Registration,Wrong Parking Details" /><span>Parking</span></a></li>			
				<li><a href="#" onclick="changeMenu(this.id);" id="maintenancemgm"
					class="notactive" title=""><img
						src="<c:url value='/resources/images/icons/mainnav/icon-add.png'/>" height="21" width="30"
						alt="Maintenance" /><span>Maintenance</span></a></li>			
				<li><a href="#" onclick="changeMenu(this.id);" id="accessptmgm"
					class="notactive" title=""><img
						src="<c:url value='/resources/images/icons/mainnav/icon-tv.png'/>" height="21" width="30"
						alt="" /><span>Access&nbsp;Points</span></a></li>				
				
				<li><a href="#" onclick="changeMenu(this.id);" id="FileDrawMgm"
					class="notactive" title=""><img
						src="<c:url value='/resources/images/icons/mainnav/tables.png'/>"
						alt="" /><span>File Drawing</span></a></li>		
						
				<li><a href="#" onclick="changeMenu(this.id);" id="BillingMgm"
					class="notactive" title=""><img
						src="<c:url value='/resources/images/icons/mainnav/icon-pricetag.png'/>" height="21" width="30"
						alt="" /><span>Billing</span></a></li>
						
				<li><a href="#" onclick="changeMenu(this.id);" id="helpDeskmgm"
					class="notactive" title=""><img
						src="<c:url value='/resources/images/icons/mainnav/icon-pricetag.png'/>" height="21" width="30"
						alt="" /><span>Customer Care</span></a></li>
				
				<li><a href="#" onclick="changeMenu(this.id);" id="custonboardmgm"
					class="notactive" title=""><img
						src="<c:url value='/resources/images/icons/mainnav/icon-tv.png'/>" height="21" width="30"
						alt="" /><span>Customer Onboard</span></a></li>
								
				<li><a href="#" onclick="changeMenu(this.id);" id="serviceMgm"
					class="notactive" title=""><img
						src="<c:url value='/resources/images/icons/mainnav/tables.png'/>"
						alt="" /><span>Masters</span></a></li>

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

						<div id="general">
							<ul class="subNav" id="subNav_syssecurity">
								<li><a href="#" onclick="RenderLink('dashboard',this.id,'subNav_syssecurity');"
									id="db" title="" class="this"><span class="icos-fullscreen"></span>
									<spring:message code="project.dashboard" text="default text" /></a></li>
								<li><a href="#" onclick="RenderLink('usermanagement',this.id,'subNav_syssecurity');" id="um"
									title="" class="notthis"><span class="icos-fullscreen"></span><spring:message code="label.usermanagement" text="User Management" /></a></li>
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
						<%-- <ul class="userList">
							<li><a href="#" title=""> <img
									src="<c:url value='/resources/images/live/face1.png'/>" alt="" />
									<span class="contactName"> <strong>Eugene
											Kopyov <span>(5)</span>
									</strong> <i>web &amp; ui designer</i>
								</span> <span class="status_away"></span>
							</a></li>
						</ul> --%>
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
						<!-- <li><a href="#" onclick="RenderLink('mailroomdashboard',this.id,'subNav_mailroom');" id="mailroomdashboard" title="" class="notthis"><span class="icos-fullscreen"></span>Dashboard</a></li> -->
						<li><a href="#" onclick="RenderLink('mailroom',this.id,'subNav_mailroom');" id="mailroomdetails" title="" class="notthis"><span class="icos-fullscreen"></span>Incoming Mails</a></li>
						<!-- <li><a href="#" onclick="RenderLink('mailroomDelivery',this.id,'subNav_mailroom');" id="mailroomdeliverylist" title="" class="notthis"><span class="icos-fullscreen"></span>Delivery List</a></li> -->
					</ul>
				</div>
				</div>
			</div>			
			
			<div class="secWrapper" id="vendormenu" style="display: none;">
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
						<!-- <li><a href="#" onclick="RenderLink('vendordashboard',this.id,'subNav_procurement');" id="procurementdashboard" title="" class="notthis"><span class="icos-fullscreen"></span>Dashboard</a></li>
						<li><a href="#" onclick="RenderLink('vendor',this.id,'subNav_procurement');" id="procurementlink" title="" class="notthis"><span class="icos-fullscreen"></span>Vendors</a></li> -->
						<li><a href="#" onclick="RenderLink('vendorContracts',this.id,'subNav_procurement');" id="procurementlink" title="" class="notthis"><span class="icos-fullscreen"></span>Vendor Contracts</a></li>
						<!-- <li><a href="#" onclick="RenderLink('vendorInvoices',this.id,'subNav_procurement');" id="procurementlink" title="" class="notthis"><span class="icos-fullscreen"></span>Vendor Invoices</a></li>
						<li><a href="#" onclick="RenderLink('vendorIncidents',this.id,'subNav_procurement');" id="procurementlink" title="" class="notthis"><span class="icos-fullscreen"></span>Vendor Incidents</a></li>
						<li><a href="#" onclick="RenderLink('vendorLineItems',this.id,'subNav_procurement');" id="procurementlink" title="" class="notthis"><span class="icos-fullscreen"></span>Vendor LineItems</a></li>
						<li><a href="#" onclick="RenderLink('vendorPayments',this.id,'subNav_procurement');" id="procurementlink" title="" class="notthis"><span class="icos-fullscreen"></span>Vendor Payments</a></li>
						<li><a href="#" onclick="RenderLink('vendorRequests',this.id,'subNav_procurement');" id="procurementlink" title="" class="notthis"><span class="icos-fullscreen"></span>Vendor Requests</a></li> -->
						
					</ul>
				</div>
				</div>
				</div>			
			
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
						<li><a href="#" onclick="RenderLink('requisition',this.id,'subNav_procurement');" id="procurementlink" title="" class="notthis"><span class="icos-fullscreen"></span>Requisition</a></li>
						<!-- <li><a href="#" onclick="RenderLink('itemMaster',this.id,'subNav_procurement');" id="procurementlink" title="" class="notthis"><span class="icos-fullscreen"></span>Item Master</a></li>
						<li><a href="#" onclick="RenderLink('vendorPriceList',this.id,'subNav_procurement');" id="procurementlink" title="" class="notthis"><span class="icos-fullscreen"></span>Vendor PriceLists</a></li> -->
					</ul>
				</div>
				</div>
				</div>			
		
			<div class="secWrapper" id="filedrawmenu" style="display: none;">
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
					<ul class="subNav" id="subNav_filedrawing">
						<li><a href="#" onclick="RenderLink('filedrawingdashboard',this.id,'subNav_filedrawing');" id="filedrawingdashboard" title="" class="notthis"><span class="icos-fullscreen"></span>Dashboard</a></li>
						<!-- <li><a href="#" onclick="RenderLink('filerepository',this.id,'subNav_filedrawing');" id="filerepositorylink" title="" class="notthis"><span class="icos-fullscreen"></span>File Repository Tree</a></li> -->
						 <li><a href="#" onclick="RenderLink('filerepositorymaster',this.id,'subNav_procurement');" id="filerepositorylink" title="" class="notthis"><span class="icos-fullscreen"></span>File Repository</a></li>
						<!--<li><a href="#" onclick="RenderLink('vendorPriceList',this.id,'subNav_procurement');" id="procurementlink" title="" class="notthis"><span class="icos-fullscreen"></span>Vendor PriceLists</a></li> -->
					</ul>
				</div>
				</div>
				</div>
				
			<div class="secWrapper" id="billingMasterMenu" style="display: none;">
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
					<ul class="subNav" id="subNav_filedrawing">
						 <li><a href="#" onclick="RenderLink('accounts',this.id,'subNav_electricity');" id="electricity" title="" class="notthis"><span class="icos-fullscreen"></span>Accounts</a></li>
						 <li><a href="#" onclick="RenderLink('electricity',this.id,'subNav_electricity');" id="electricity" title="" class="notthis"><span class="icos-fullscreen"></span>Electricity</a></li>
						 <li><a href="#" onclick="RenderLink('servicePoints',this.id,'subNav_electricity');" id="servicePoints" title="" class="notthis"><span class="icos-fullscreen"></span>Service Points</a></li>
						 <li><a href="#" onclick="RenderLink('serviceMasters',this.id,'subNav_electricity');" id="servicePoints" title="" class="notthis"><span class="icos-fullscreen"></span>Masters</a></li>
						 <li><a href="#" onclick="RenderLink('tariffmaster',this.id,'subNav_electricity');" id="tariffmaster" title="" class="notthis"><span class="icos-fullscreen"></span>Tariff Management</a></li>
						 <li><a href="#" onclick="RenderLink('billingWizard',this.id,'subNav_electricity');" id="billingWizard" title="" class="notthis"><span class="icos-fullscreen"></span>Billing Wizard</a></li>
					</ul>
				</div>
				</div>
				</div>
				
			
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
						<li><a href="#" onclick="RenderLink('comdashboard',this.id,'subNav_com');" id="comdashboard" title="" class="notthis"><span class="icos-fullscreen"></span>Dashboard</a></li>
						<li><a href="#" onclick="RenderLink('comproject',this.id,'subNav_com');" id="comproject" title="" class="notthis"><span class="icos-fullscreen"></span>Project</a></li>
						<li><a href="#" onclick="RenderLink('persons',this.id,'subNav_com');" id="person" title="" class="notthis"><span class="icos-fullscreen"></span>Persons</a></li>
						<li><a href="#" onclick="RenderLink('comsettings',this.id,'subNav_com');" id="property" title="" class="notthis"><span class="icos-fullscreen"></span>Settings</a></li>
						<!-- <li><a href="#" onclick="RenderLink('usersNew',this.id,'subNav_com');" id="usersNew" title="" class="notthis"><span class="icos-fullscreen"></span>Users New</a></li> -->
						<li><a href="#" onclick="RenderLink('comaccesscards',this.id,'subNav_com');" id="accessCards" title="" class="notthis"><span class="icos-fullscreen"></span>Access Cards</a></li>
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
						<!-- <li><a href="#" onclick="RenderLink('manpowerdashboard',this.id,'subNav_manpower');" id="manpowerdashboard" title="" class="notthis"><span class="icos-fullscreen"></span>Dashboard</a></li> -->
						<li><a href="#" onclick="RenderLink('manpowerindex',this.id,'subNav_manpower');" id="manpowerlink" title="" class="notthis"><span class="icos-fullscreen"></span>Staff Master</a></li>
						<!-- <li><a href="#" onclick="RenderLink('manpowerse',this.id,'subNav_manpower');" id="manpowerlink" title="" class="notthis"><span class="icos-fullscreen"></span>Staff Experience</a></li>
						<li><a href="#" onclick="RenderLink('manpowerst',this.id,'subNav_manpower');" id="manpowerlink" title="" class="notthis"><span class="icos-fullscreen"></span>Staff Training</a></li>
						<li><a href="#" onclick="RenderLink('manpowersn',this.id,'subNav_manpower');" id="manpowerlink" title="" class="notthis"><span class="icos-fullscreen"></span>Staff Notices</a></li> -->
					
					</ul>
				</div>
				</div>
			</div>
			
			<div class="secWrapper" id="maintenancemenu" style="display: none;">
				<div class="secTop">
					<div class="balance">
						<div class="balInfo">
							Today:<br />
							<script  type="text/javascript">
						document.write("<b>"+monthArray[todayMonth] + " " + todayDate + ", "+dayArray[todayDay]+" " + todayYear+"<b>");
					</script>
						</div>
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
					<ul class="subNav" id="subNav_maintenance">
						<li><a href="#" onclick="RenderLink('jobcalender',this.id,'subNav_jobcalender');" id="jobcalender" title="" class="notthis"><span class="icos-fullscreen"></span>Manage Job Calender</a></li>
						<li><a href="#" onclick="RenderLink('jobtypes',this.id,'subNav_jobtypes');" id="jobtypes" title="" class="notthis"><span class="icos-fullscreen"></span>Manage Job Types</a></li>
						<li><a href="#" onclick="RenderLink('maintenancetypes',this.id,'subNav_maintenancetypes');" id="maintenancetypes" title="" class="notthis"><span class="icos-fullscreen"></span>Manage Maintenance Types</a></li>
						<li><a href="#" onclick="RenderLink('jobcards',this.id,'subNav_jobcards');" id="jobcards" title="" class="notthis"><span class="icos-fullscreen"></span>Manage Job Cards</a></li>
						<li><a href="#" onclick="RenderLink('toolmaster',this.id,'subNav_toolmaster');" id="toolmaster" title="" class="notthis"><span class="icos-fullscreen"></span>Manage Tool Master</a></li>
						<li><a href="#" onclick="RenderLink('maintainancedepartment',this.id,'subNav_maintainancedepartment');" id="maintainancedepartment" title="" class="notthis"><span class="icos-fullscreen"></span>Maintainance Department</a></li>
					</ul>
				</div>
				</div>
			</div>
			
			<div class="secWrapper" id="helpDeskmenu" style="display: none;">
				<div class="secTop">
					<div class="balance">
						<div class="balInfo">
							Today:<br />
							<script  type="text/javascript">
						document.write("<b>"+monthArray[todayMonth] + " " + todayDate + ", "+dayArray[todayDay]+" " + todayYear+"<b>");
					</script>
						</div>
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
					<ul class="subNav" id="subNav_helpDesk">
						<li><a href="#" onclick="RenderLink('openNewTickets',this.id,'subNav_openNewTickets');" id="openNewTickets" title="" class="notthis"><span class="icos-fullscreen"></span>Open New Ticket</a></li>
						<li><a href="#" onclick="RenderLink('departmentAccessSettings',this.id,'subNav_departmentAccessSettings');" id="departmentAccessSettings" title="" class="notthis"><span class="icos-fullscreen"></span>Department Access Settings</a></li>
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
								document.write("<b>" + monthArray[todayMonth]
										+ " " + todayDate + ", "
										+ dayArray[todayDay] + " " + todayYear
										+ "<b>");
							</script>
						</div>
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
							
							<li><a href="#"	onclick="RenderLink('assetmaster',this.id,'subNav_asset');" id="assetlink" title="" class="notthis"><span class="icos-fullscreen"></span>Asset Master</a></li>
							<!-- <li><a href="#"	onclick="RenderLink('assetownership',this.id,'subNav_asset');" id="assetlink" title="" class="notthis"><span class="icos-fullscreen"></span>Asset Ownership Management</a></li>
							<li><a href="#"	onclick="RenderLink('assetmaintainance',this.id,'subNav_asset');" id="assetlink" title="" class="notthis"><span class="icos-fullscreen"></span>Asset Maintainance</a></li>
							<li><a href="#"	onclick="RenderLink('assetservicehistory',this.id,'subNav_asset');" id="assetlink" title="" class="notthis"><span class="icos-fullscreen"></span>Asset Service History</a></li>
							<li><a href="#"	onclick="RenderLink('assetphysicalsurvey',this.id,'subNav_asset');" id="assetlink" title="" class="notthis"><span class="icos-fullscreen"></span>Asset Physical Survey</a></li>
							
							<li><a href="#"	onclick="RenderLink('assetcat',this.id,'subNav_asset');" id="assetlink" title="" class="notthis"><span class="icos-fullscreen"></span>Asset Category Management</a></li>
							<li><a href="#"	onclick="RenderLink('assetloc',this.id,'subNav_asset');" id="assetloclink" title="" class="notthis"><span	class="icos-fullscreen"></span>Asset Location Management</a></li> -->
						</ul>
					</div>
				</div>
			</div>


			<div class="secWrapper" id="inventorymenu" style="display: none;">
				<div class="secTop">
					<div class="balance">
						<div class="balInfo">
							Today:<br />
							<script  type="text/javascript">
								document.write("<b>" + monthArray[todayMonth]
										+ " " + todayDate + ", "
										+ dayArray[todayDay] + " " + todayYear
										+ "<b>");
							</script>
						</div>
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
						<ul class="subNav" id="subNav_inventory">
							
							<li><a href="#"
								onclick="RenderLink('indexStoreGoodsReceipt',this.id,'subNav_inventory');"
								id="assetlink" title="" class="notthis"><span
									class="icos-fullscreen"></span>Store Receipts</a></li>
							<!-- <li><a href="#"
								onclick="RenderLink('indexStoreIssue',this.id,'subNav_inventory');"
								id="assetlink" title="" class="notthis"><span
									class="icos-fullscreen"></span>Store Issues</a></li>
							<li><a href="#"
								onclick="RenderLink('indexStoreItemTransfer',this.id,'subNav_inventory');"
								id="assetlink" title="" class="notthis"><span
									class="icos-fullscreen"></span>Store Item Transfers</a></li>
							<li><a href="#"
								onclick="RenderLink('indexStoreGoodsReturns',this.id,'subNav_inventory');"
								id="assetlink" title="" class="notthis"><span
									class="icos-fullscreen"></span>Store Item Returns</a></li>
							<li><a href="#"
								onclick="RenderLink('indexStoreAdjustments',this.id,'subNav_inventory');"
								id="assetlink" title="" class="notthis"><span
									class="icos-fullscreen"></span>Store Adjustments</a></li>
							<li><a href="#"
								onclick="RenderLink('indexItemMaster',this.id,'subNav_inventory');"
								id="assetlink" title="" class="notthis"><span
									class="icos-fullscreen"></span>Item Master</a></li>		
							<li><a href="#"
								onclick="RenderLink('indexStoreItemLedger',this.id,'subNav_inventory');"
								id="assetlink" title="" class="notthis"><span
									class="icos-fullscreen"></span>Item Ledger</a></li>
							<li><a href="#"
								onclick="RenderLink('indexStoreMaster',this.id,'subNav_inventory');"
								id="assetlink" title="" class="notthis"><span
									class="icos-fullscreen"></span>Store Master</a></li> -->
																
						</ul>
					</div>
				</div>
			</div>

			<div class="secWrapper" id="accessptmenu" style="display: none;">
				<div class="secTop">
					<div class="balance">
						<div class="balInfo">
							Today:<br />
							<script  type="text/javascript">
								document.write("<b>" + monthArray[todayMonth]
										+ " " + todayDate + ", "
										+ dayArray[todayDay] + " " + todayYear
										+ "<b>");
							</script>
						</div>
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
							<li><a href="#"
								onclick="RenderLink('accessptdashboard',this.id,'subNav_asset');"
								id="assetdashboard" title="" class="notthis"><span
									class="icos-fullscreen"></span>Dashboard</a></li>
							<li><a href="#"
								onclick="RenderLink('accesspointindex',this.id,'subNav_asset');"
								id="assetlink" title="" class="notthis"><span
									class="icos-fullscreen"></span>Access Point Management</a></li>
						</ul>
					</div>
				</div>
			</div>
			
			<!--	Customer On board	 -->
			
			<div class="secWrapper" id="custonboardmenu" style="display: none;">
				<div class="secTop">
					<div class="balance">
						<div class="balInfo">
							Today:<br />
							<script  type="text/javascript">
								document.write("<b>" + monthArray[todayMonth]
										+ " " + todayDate + ", "
										+ dayArray[todayDay] + " " + todayYear
										+ "<b>");
							</script>
						</div>
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
						<ul class="subNav" id="subNav_custonboard">				
							<li><a href="#"
								onclick="RenderLink('custonboardindex',this.id,'subNav_custonboard');"
								id="custonboardlink" title="" class="notthis"><span
									class="icos-fullscreen"></span>Customer Onboard</a></li>
							<li><a href="#"
								onclick="RenderLink('servicetasks',this.id,'subNav_custonboard');"
								id="tasksdetails" title="" class="notthis"><span
									class="icos-fullscreen"></span>Test</a></li>
							
						</ul>
					</div>
				</div>
			</div>
			
			
			
			<!--		 -->
			


			<div class="secWrapper" id="visitormenu" style="display: none;">
				<div class="secTop">
					<div class="balance">
						<div class="balInfo">
							Today:<br />
							<script  type="text/javascript">
						document.write("<b>"+monthArray[todayMonth] + " " + todayDate + ", "+dayArray[todayDay]+" " + todayYear+"<b>");
					</script>
						</div>
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
						<!-- <li><a href="#" onclick="RenderLink('visitordetails',this.id,'subNav_visitors');" id="visitordetailslink" title="" class="notthis"><span class="icos-fullscreen"></span>Visitors</a></li>
						<li><a href="#" onclick="RenderLink('storevisitor',this.id,'subNav_visitors');" id="storevisitorlink" title="" class="notthis"><span class="icos-fullscreen"></span>Visitor Master</a></li>
						<li><a href="#" onclick="RenderLink('visitorvisits',this.id,'subNav_visitors');" id="visitorvisitslink" title="" class="notthis"><span class="icos-fullscreen"></span>Visitor Entry Details</a></li> -->
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
						<!-- <li><a href="#" onclick="RenderLink('PatrolTrackPoints',this.id,'subNav_timeAtt');" id="timeAttdashboard" title="" class="notthis"><span class="icos-fullscreen"></span>Patrol Track Points</a></li>
						<li><a href="#" onclick="RenderLink('patrolTrackStaff',this.id,'subNav_timeAtt');" id="timeAttdashboard" title="" class="notthis"><span class="icos-fullscreen"></span>Patrol Staff</a></li>
						<li><a href="#" onclick="RenderLink('patrolTrackAlert',this.id,'subNav_timeAtt');" id="timeAttdashboard" title="" class="notthis"><span class="icos-fullscreen"></span>Patrol Alerts</a></li>
						<li><a href="#" onclick="RenderLink('patrolSettings',this.id,'subNav_timeAtt');" id="timeAttdashboard" title="" class="notthis"><span class="icos-fullscreen"></span>Patrol Settings</a></li> -->
						<li><a href="#" onclick="RenderLink('staff',this.id,'subNav_timeAtt');" id="timeAttdashboard" title="" class="notthis"><span class="icos-fullscreen"></span>Staff</a></li>
						
					</ul>
				</div>
				</div>
			</div>
			<div class="secWrapper" id="concirge_menu" style="display: none;">
				<div class="secTop">
					<div class="balance">
						<div class="balInfo">
							Today:<br />
							<script  type="text/javascript">
						document.write("<b>"+monthArray[todayMonth] + " " + todayDate + ", "+dayArray[todayDay]+" " + todayYear+"<b>");
					</script>
						</div>
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
						<li><a href="#" onclick="RenderLink('conciergedashboard',this.id,'subNav_timeAtt');" id="dashboard" title="" class="notthis"><span class="icos-fullscreen"></span>Dashboard</a></li>
						<li><a href="#" onclick="RenderLink('serviceBooking',this.id,'subNav_timeAtt');" id="timeAttdashboard" title="" class="notthis"><span class="icos-fullscreen"></span>Service Booking</a></li>
						<!--<li><a href="#" onclick="RenderLink('patrolTrackStaff',this.id,'subNav_timeAtt');" id="timeAttdashboard" title="" class="notthis"><span class="icos-fullscreen"></span>Patrol Staff</a></li>
						<li><a href="#" onclick="RenderLink('patrolTrackAlert',this.id,'subNav_timeAtt');" id="timeAttdashboard" title="" class="notthis"><span class="icos-fullscreen"></span>Patrol Alerts</a></li>
						<li><a href="#" onclick="RenderLink('patrolSettings',this.id,'subNav_timeAtt');" id="timeAttdashboard" title="" class="notthis"><span class="icos-fullscreen"></span>Patrol Settings</a></li> 
						<li><a href="#" onclick="RenderLink('staff',this.id,'subNav_timeAtt');" id="timeAttdashboard" title="" class="notthis"><span class="icos-fullscreen"></span>Staff</a></li> -->
						
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
							<!-- <li><a href="#" onclick="RenderLink('parkingslotheader',this.id,'subNav_parking');" id="parkingslots_parking" title="" class="notthis"><span class="icos-fullscreen"></span>Parking Slots</a></li> -->
							<li><a href="#" onclick="RenderLink('vehicles',this.id,'subNav_parking');" id="vehicles_parking" title="" class="notthis"><span class="icos-fullscreen"></span>Vehicles</a></li>
							<!-- <li><a href="#" onclick="RenderLink('wrongparking',this.id,'subNav_parking');" id="wrongparking_parking" title="" class="notthis"><span class="icos-fullscreen"></span>Wrong Parking</a></li> -->
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
					</ul>
				</div>
				</div>
			</div>


		<div class="secWrapper" id="servicemenu" style="display: none;">
				<div class="secTop">
					<div class="balance">
						<div class="balInfo">
							Today:<br />
							<script  type="text/javascript">
						document.write("<b>"+monthArray[todayMonth] + " " + todayDate + ", "+dayArray[todayDay]+" " + todayYear+"<b>");
					</script>
						</div>
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
					<ul class="subNav" id="subNav_service">
						<li><a href="#" onclick="RenderLink('servicetasks',this.id,'subNav_service');" id="tasksdetails" title="" class="notthis"><span class="icos-fullscreen"></span>Service Parameter Master</a></li>
						<li><a href="#" onclick="RenderLink('billingparameter',this.id,'subNav_service');" id="billingdetails" title="" class="notthis"><span class="icos-fullscreen"></span>Billing Parameter Master</a></li>
						<li><a href="#" onclick="RenderLink('meterparameter',this.id,'subNav_service');" id="meterdetails" title="" class="notthis"><span class="icos-fullscreen"></span>Meter Parameter Master</a></li>
					</ul>
				</div>
				</div>
			</div>
		</div>
	</div>
	
	
	 
	<!-- Sidebar ends -->
<!-- 	<div id="contentrender" class="cbp-spmenu-push" style="margin-left: -241px;">
	  
	 </div> -->
	 
	 <!-- Sidebar ends -->
	<div id="contentrender" class="cbp-spmenu-push" style="margin-left: -241px;">
	
	  
     <div id="content">
  	  <div class="contentTop">
		<span class="pageTitle"><span class="icon-screen"></span><label id="labeldashboard"></label></span>
	  </div>
	  
	  <div class="breadLine">
		<div class="bc">
			<ul id="breadcrumbs" class="breadcrumbs">
				<li><a href="${home}"><label id="labelhome">Home</label></a></li>
			</ul>
		</div>
		<div class="breadLinks">
			<ul id="menu"></ul>
		</div>
	</div>	
	
	<%@include file="thirdlevelmenu.jsp" %>			
  	 
  	<div class="wrapper">	
	 	<div id="testContentRender">
	 	</div>
	 </div>
	 </div> 
	 </div>
	 
<script type="text/javascript">

	var temp = 0;
	var menuLeft = document.getElementById( 'cbp-spmenu-s1' ),
			 
	showLeftPush = document.getElementById( 'showLeftPush' ),
	 
	body = document.getElementById('contentrender');
          
 
	showLeftPush.onclick = function() {
   		temp = 1;	 
		classie.toggle( this, 'active' );	 
		$(".secNav").show();
		$("#contentrender").css("margin-left","0px");			 
	};
   
$("#toggle").click(function() {
    var button = $(this),        
        enable = button.text() == "Turn Off";
    
    if (enable) {
    	$(".secNav").hide();

	 	var menuLeft = document.getElementById( 'cbp-spmenu-s1' ),
		body = document.getElementById('contentrender');
	 	classie.toggle( this, 'active' ); 
		$("#contentrender").css("margin-left","0px");
        
    } 
    else {

		$(".secNav").show();
		var menuLeft = document.getElementById( 'cbp-spmenu-s1' ),	
		body = document.getElementById('contentrender');
		classie.toggle( this, 'active' );
		$("#contentrender").css("margin-left","0px");
    }
    button.text(enable ? "Turn On" : "Turn Off");
});

var temp = 0;
var pinStatus = 0;
 
		
		

function onloadTest()
{
	
	//document.location.href = String( document.location.href ).replace( /#/, "" );
	var pageName = getUrlVars()["page"];
	var pageNameForMenu = "";
	var thirdLevelMenuId = "";
	if(typeof pageName == 'undefined')
	{
		//$("#contentrender").load("./dashboard");
		pageName = "./dashboard";
	}
	else
	{
		//$("#contentrender").load("./"+pageName);
		pageNameForMenu = pageName.replace( /#/, "" );
		pageName = "./"+pageName;
	}
	//alert( $('ul > li > a[onclick="RenderLinkInner(\''+pageNameForMenu+'\')"]').closest('ul').attr('id'));
	//alert($('ul > li > a[onclick="RenderLinkInner(\''+pageNameForMenu+'\')"]').closest('li').text());
	var labelText = $('ul > li > a[onclick="RenderLinkInner(\''+pageNameForMenu+'\')"]').closest('li').text();
	$("#labeldashboard").html("");
	$("#labeldashboard").html(labelText);
	thirdLevelMenuId = $('ul > li > a[onclick="RenderLinkInner(\''+pageNameForMenu+'\')"]').closest('ul').attr('id');
	//alert(thirdLevelMenuId);
	if(typeof thirdLevelMenuId != 'undefined')
		{
			$('#wrapper ul').not("#"+thirdLevelMenuId).hide();
			 if($('#'+thirdLevelMenuId).css('display') == 'none')
			 {
				 $('#'+thirdLevelMenuId).css('display','block');
			 }
		}
	else
		{
			pageNameForMenu = pageNameForMenu.toLowerCase().replace(/\b[a-z]/g, function(letter) {
			    return letter.toUpperCase();
			});
			$('#wrapper ul').not("#"+pageNameForMenu+"InnerMenu").hide();
			 if($("#"+pageNameForMenu+"InnerMenu").css('display') == 'none')
			 {
				 $("#"+pageNameForMenu+"InnerMenu").css('display','block');
			 }
			 
			 $("#labeldashboard").html("");
			 $("#labeldashboard").html(pageNameForMenu);
		}
	 
	 $.ajax({
         type: "GET",
         url: pageName,
         success: function(response) {
             $("#testContentRender").html( response );
         	//$("#content").remove();
         	//$("#contentrender").append(response);
         },
	        complete:function()
			{
				$body.removeClass("loading");
			},
			error: function(jqXHR, textStatus, errorThrown) {
			    //alert(jqXHR.status);
			    //alert(jqXHR.responseText);
			    //alert(errorThrown);
			    if(jqXHR.status)
				    {
			    		$("#testContentRender").html( jqXHR.responseText );
			    		//$("#content").remove();
		         		//$("#contentrender").append(jqXHR.responseText);
				    }
			}
     });
	$.ajax({
	type : "POST",
		url : "./readPinStatus",
		dataType : 'text',
		success : function(response)
		{
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
	 
	  var $bc = $('<ul class="breadcrumbs"></ul>');
	   var labelModuleText = $("#"+param).text(); 
	   var $li = $('<li></li>').append("<a>"+labelModuleText+"</a>"); 
	   $bc.prepend($li);
       $('.bc').html( $bc.prepend('<li><a href="home">Home</a></li>') );
    //$(".bc > ul > li > a > span").remove();
	  
	  
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
		$("#concirge_menu").hide();
		$("#parkingmenu").hide();
		$("#customeroccupancymenu").hide();
		$("#documentrepomenu").hide();
		$("#procurementmenu").hide();
		$("#accessptmenu").hide();
		$("#maintenancemenu").hide();
		$("#inventorymenu").hide();
		$("#filedrawmenu").hide();
		$("#vendormenu").hide();
		$("#billingMasterMenu").hide();
		$("#helpDeskmenu").hide();
		$("#servicemenu").hide();
		$("#custonboardmenu").hide();
	}
	else if(param == "test")
	{
		$("#testmenu").show();
		$("#usermgmmenu").hide();
		$("#manpowermenu").hide();
		$("#assetrmenu").hide();
		$("#visitormenu").hide();
		$("#time_attendancemenu").hide();
		$("#concirge_menu").hide();
		$("#parkingmenu").hide();
		$("#customeroccupancymenu").hide();
		$("#documentrepomenu").hide();
		$("#procurementmenu").hide();
		$("#accessptmenu").hide();
		$("#maintenancemenu").hide();
		$("#inventorymenu").hide();
		$("#filedrawmenu").hide();
		$("#vendormenu").hide();
		$("#billingMasterMenu").hide();
		$("#helpDeskmenu").hide();
		$("#servicemenu").hide();
		$("#custonboardmenu").hide();
	}
	else if(param == "manpowermgm")
	{
		$("#manpowermenu").show();
		$("#assetmenu").hide();
		$("#testmenu").hide();
		$("#usermgmmenu").hide();
		$("#visitormenu").hide();
		$("#time_attendancemenu").hide();
		$("#concirge_menu").hide();
		$("#parkingmenu").hide();
		$("#customeroccupancymenu").hide();
		$("#documentrepomenu").hide();
		$("#procurementmenu").hide();
		$("#accessptmenu").hide();
		$("#maintenancemenu").hide();
		$("#inventorymenu").hide();
		$("#filedrawmenu").hide();
		$("#vendormenu").hide();
		$("#billingMasterMenu").hide();
		$("#helpDeskmenu").hide();
		$("#servicemenu").hide();
		$("#custonboardmenu").hide();
	}
	else if(param == "visitormgm")
	{
		$("#visitormenu").show();
		$("#manpowermenu").hide();
		$("#assetmenu").hide();
		$("#testmenu").hide();
		$("#usermgmmenu").hide();
		$("#time_attendancemenu").hide();
		$("#concirge_menu").hide();
		$("#parkingmenu").hide();
		$("#customeroccupancymenu").hide();
		$("#documentrepomenu").hide();
		$("#procurementmenu").hide();
		$("#accessptmenu").hide();
		$("#maintenancemenu").hide();
		$("#inventorymenu").hide();
		$("#filedrawmenu").hide();
		$("#vendormenu").hide();
		$("#billingMasterMenu").hide();
		$("#helpDeskmenu").hide();
		$("#servicemenu").hide();
		$("#custonboardmenu").hide();
	}	
	else if(param == "timeAndAttendancemgm")
	{
		$("#time_attendancemenu").show();
		$("#concirge_menu").hide();
		$("#visitormenu").hide();
		$("#manpowermenu").hide();
		$("#assetmenu").hide();
		$("#testmenu").hide();
		$("#usermgmmenu").hide();
		$("#parkingmenu").hide();
		$("#customeroccupancymenu").hide();
		$("#documentrepomenu").hide();
		$("#procurementmenu").hide();
		$("#accessptmenu").hide();
		$("#maintenancemenu").hide();
		$("#inventorymenu").hide();
		$("#filedrawmenu").hide();
		$("#vendormenu").hide();
		$("#billingMasterMenu").hide();
		$("#helpDeskmenu").hide();
		$("#servicemenu").hide();
		$("#custonboardmenu").hide();
	}
	else if(param == "concirgemgm")
	{
		$("#concirge_menu").show();
		$("#time_attendancemenu").hide();
		$("#visitormenu").hide();
		$("#manpowermenu").hide();
		$("#assetmenu").hide();
		$("#testmenu").hide();
		$("#usermgmmenu").hide();
		$("#parkingmenu").hide();
		$("#customeroccupancymenu").hide();
		$("#documentrepomenu").hide();
		$("#procurementmenu").hide();
		$("#accessptmenu").hide();
		$("#maintenancemenu").hide();
		$("#inventorymenu").hide();
		$("#filedrawmenu").hide();
		$("#vendormenu").hide();
		$("#billingMasterMenu").hide();
		$("#helpDeskmenu").hide();
		$("#servicemenu").hide();
		$("#custonboardmenu").hide();
	}
	else if(param == "parkingmgm")
	{
		
		$("#parkingmenu").show();
		$("#time_attendancemenu").hide();
		$("#concirge_menu").hide();
		$("#menu").hide();
		$("#manpowermenu").hide();
		$("#assetmenu").hide();
		$("#visitormenu").hide();
		$("#testmenu").hide();
		$("#usermgmmenu").hide();
		$("#customeroccupancymenu").hide();
		$("#documentrepomenu").hide();
		$("#procurementmenu").hide();
		$("#accessptmenu").hide();
		$("#maintenancemenu").hide();
		$("#inventorymenu").hide();
		$("#filedrawmenu").hide();
		$("#vendormenu").hide();
		$("#billingMasterMenu").hide();
		$("#visitormenu").hide();
		$("#helpDeskmenu").hide();
		$("#servicemenu").hide();
		$("#custonboardmenu").hide();
	}
	else if(param == "customeroccupancymgm")
	{
		$("#customeroccupancymenu").show();
		$("#parkingmenu").hide();
		$("#time_attendancemenu").hide();
		$("#concirge_menu").hide();
		$("#visitormenu").hide();
		$("#manpowermenu").hide();
		$("#assetmenu").hide();
		$("#testmenu").hide();
		$("#usermgmmenu").hide();
		$("#documentrepomenu").hide();
		$("#procurementmenu").hide();
		$("#accessptmenu").hide();
		$("#maintenancemenu").hide();
		$("#inventorymenu").hide();
		$("#filedrawmenu").hide();
		$("#vendormenu").hide();
		$("#billingMasterMenu").hide();
		$("#helpDeskmenu").hide();
		$("#servicemenu").hide();
		$("#custonboardmenu").hide();
	}
	else if(param == "inventorymgm")
	{
		$("#customeroccupancymenu").hide();
		$("#parkingmenu").hide();
		$("#time_attendancemenu").hide();
		$("#concirge_menu").hide();
		$("#visitormenu").hide();
		$("#manpowermenu").hide();
		$("#assetmenu").hide();
		$("#testmenu").hide();
		$("#usermgmmenu").hide();
		$("#documentrepomenu").hide();
		$("#procurementmenu").hide();
		$("#accessptmenu").hide();
		$("#maintenancemenu").hide();
		$("#inventorymenu").show();
		$("#filedrawmenu").hide();
		$("#vendormenu").hide();
		$("#billingMasterMenu").hide();
		$("#helpDeskmenu").hide();
		$("#servicemenu").hide();
		$("#custonboardmenu").hide();
	}
	else if(param == "assetmgm")
	{
		$("#customeroccupancymenu").hide();
		$("#parkingmenu").hide();
		$("#time_attendancemenu").hide();
		$("#concirge_menu").hide();
		$("#visitormenu").hide();
		$("#manpowermenu").hide();
		$("#assetmenu").show();
		$("#testmenu").hide();
		$("#usermgmmenu").hide();
		$("#documentrepomenu").hide();
		$("#procurementmenu").hide();
		$("#accessptmenu").hide();
		$("#maintenancemenu").hide();
		$("#inventorymenu").hide();
		$("#filedrawmenu").hide();
		$("#vendormenu").hide();
		$("#billingMasterMenu").hide();
		$("#helpDeskmenu").hide();
		$("#servicemenu").hide();
		$("#custonboardmenu").hide();
	}
	else if(param == "accessptmgm")
	{
		$("#customeroccupancymenu").hide();
		$("#parkingmenu").hide();
		$("#time_attendancemenu").hide();
		$("#concirge_menu").hide();
		$("#visitormenu").hide();
		$("#manpowermenu").hide();
		$("#assetmenu").hide();
		$("#testmenu").hide();
		$("#usermgmmenu").hide();
		$("#documentrepomenu").hide();
		$("#procurementmenu").hide();
		$("#accessptmenu").show();
		$("#maintenancemenu").hide();
		$("#inventorymenu").hide();
		$("#filedrawmenu").hide();
		$("#vendormenu").hide();
		$("#billingMasterMenu").hide();
		$("#helpDeskmenu").hide();
		$("#servicemenu").hide();
		$("#custonboardmenu").hide();
	}
	else if(param == "procurementmgm")
	{
		$("#customeroccupancymenu").hide();
		$("#parkingmenu").hide();
		$("#time_attendancemenu").hide();
		$("#concirge_menu").hide();
		$("#visitormenu").hide();
		$("#manpowermenu").hide();
		$("#assetmenu").hide();
		$("#procurementmenu").show();
		$("#testmenu").hide();
		$("#usermgmmenu").hide();
		$("#documentrepomenu").hide();
		$("#accessptmenu").hide();
		$("#maintenancemenu").hide();
		$("#inventorymenu").hide();
		$("#filedrawmenu").hide();
		$("#vendormenu").hide();
		$("#billingMasterMenu").hide();
		$("#helpDeskmenu").hide();
		$("#servicemenu").hide();
		$("#custonboardmenu").hide();
	}
	else if(param == "vendormgm")
	{
		$("#customeroccupancymenu").hide();
		$("#parkingmenu").hide();
		$("#time_attendancemenu").hide();
		$("#concirge_menu").hide();
		$("#visitormenu").hide();
		$("#manpowermenu").hide();
		$("#assetmenu").hide();
		$("#procurementmenu").hide();
		$("#testmenu").hide();
		$("#usermgmmenu").hide();
		$("#documentrepomenu").hide();
		$("#accessptmenu").hide();
		$("#maintenancemenu").hide();
		$("#inventorymenu").hide();
		$("#filedrawmenu").hide();
		$("#vendormenu").show();
		$("#billingMasterMenu").hide();
		$("#helpDeskmenu").hide();
		$("#servicemenu").hide();
		$("#custonboardmenu").hide();
	}

	else if(param == "FileDrawMgm")
	{
		$("#filedrawmenu").show();
		$("#documentrepomenu").hide();
		$("#customeroccupancymenu").hide();
		$("#parkingmenu").hide();
		$("#time_attendancemenu").hide();
		$("#concirge_menu").hide();
		$("#visitormenu").hide();
		$("#manpowermenu").hide();
		$("#assetmenu").hide();
		$("#testmenu").hide();
		$("#usermgmmenu").hide();
		$("#procurementmenu").hide();
		$("#accessptmenu").hide();
		$("#maintenancemenu").hide();
		$("#inventorymenu").hide();
		$("#vendormenu").hide();
		$("#billingMasterMenu").hide();
		$("#helpDeskmenu").hide();
		$("#servicemenu").hide();
		$("#custonboardmenu").hide();
		
	}		
	
	else if(param == "BillingMgm")
	{
		$("#billingMasterMenu").show();
		$("#filedrawmenu").hide();
		$("#documentrepomenu").hide();
		$("#customeroccupancymenu").hide();
		$("#parkingmenu").hide();
		$("#time_attendancemenu").hide();
		$("#concirge_menu").hide();
		$("#visitormenu").hide();
		$("#manpowermenu").hide();
		$("#assetmenu").hide();
		$("#testmenu").hide();
		$("#usermgmmenu").hide();
		$("#procurementmenu").hide();
		$("#accessptmenu").hide();
		$("#maintenancemenu").hide();
		$("#inventorymenu").hide();
		$("#vendormenu").hide();
		$("#helpDeskmenu").hide();
		$("#servicemenu").hide();
		$("#custonboardmenu").hide();
		
	}

	else if(param == "maintenancemgm")
	{
		$("#maintenancemenu").show();
		$("#documentrepomenu").hide();
		$("#customeroccupancymenu").hide();
		$("#parkingmenu").hide();
		$("#time_attendancemenu").hide();
		$("#concirge_menu").hide();
		$("#visitormenu").hide();
		$("#manpowermenu").hide();
		$("#assetmenu").hide();
		$("#testmenu").hide();
		$("#usermgmmenu").hide();
		$("#procurementmenu").hide();
		$("#accessptmenu").hide();
		$("#inventorymenu").hide();
		$("#filedrawmenu").hide();
		$("#vendormenu").hide();
		$("#billingMasterMenu").hide();
		$("#helpDeskmenu").hide();
		$("#servicemenu").hide();
		$("#custonboardmenu").hide();
	}
	else if(param == "helpDeskmgm")
	{
		$("#helpDeskmenu").show();
		$("#maintenancemenu").hide();
		$("#documentrepomenu").hide();
		$("#customeroccupancymenu").hide();
		$("#parkingmenu").hide();
		$("#time_attendancemenu").hide();
		$("#concirge_menu").hide();
		$("#visitormenu").hide();
		$("#manpowermenu").hide();
		$("#assetmenu").hide();
		$("#testmenu").hide();
		$("#usermgmmenu").hide();
		$("#procurementmenu").hide();
		$("#accessptmenu").hide();
		$("#inventorymenu").hide();
		$("#filedrawmenu").hide();
		$("#vendormenu").hide();
		$("#billingMasterMenu").hide();
		$("#servicemenu").hide();
		$("#custonboardmenu").hide();
	}
	else if(param == "custonboardmgm")
	{
		$("#custonboardmenu").show();
		$("#helpDeskmenu").hide();
		$("#maintenancemenu").hide();
		$("#documentrepomenu").hide();
		$("#customeroccupancymenu").hide();
		$("#parkingmenu").hide();
		$("#time_attendancemenu").hide();
		$("#concirge_menu").hide();
		$("#visitormenu").hide();
		$("#manpowermenu").hide();
		$("#assetmenu").hide();
		$("#testmenu").hide();
		$("#usermgmmenu").hide();
		$("#procurementmenu").hide();
		$("#accessptmenu").hide();
		$("#inventorymenu").hide();
		$("#filedrawmenu").hide();
		$("#vendormenu").hide();
		$("#billingMasterMenu").hide();
		$("#servicemenu").hide();
	}
	else if(param == "serviceMgm")
	 {
	  	$("#servicemenu").show();
	  	$("#helpDeskmenu").hide();
		$("#maintenancemenu").hide();
		$("#documentrepomenu").hide();
		$("#customeroccupancymenu").hide();
		$("#parkingmenu").hide();
		$("#time_attendancemenu").hide();
		$("#concirge_menu").hide();
		$("#visitormenu").hide();
		$("#manpowermenu").hide();
		$("#assetmenu").hide();
		$("#testmenu").hide();
		$("#usermgmmenu").hide();
		$("#procurementmenu").hide();
		$("#accessptmenu").hide();
		$("#inventorymenu").hide();
		$("#filedrawmenu").hide();
		$("#vendormenu").hide();
		$("#billingMasterMenu").hide();
		$("#custonboardmenu").hide();
	 }
	
}

function getUrlVars()
{
    var vars = [], hash;
    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    for(var i = 0; i < hashes.length; i++)
    {
        hash = hashes[i].split('=');
        vars.push(hash[0]);
        vars[hash[0]] = hash[1];
    }
    return vars;
}




function RenderLink(param,id,subNavId)
{
	 var subNav = $('#'+subNavId);
	 subNav.delegate('a.notthis','click',function(){
	 subNav.find('.this').toggleClass('this notthis');
     $(this).toggleClass('this notthis');
	});
	 
	$("#contentrender").css("margin-left","-241px");
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
				if(response == "success")
				  {
					var loadUrl = "./"+param;
					

    	 			var loc = window.location.href,
    	 		    index = loc.indexOf('#');
    	 			if (index > 0) 
        	 		{
    	 				history.pushState({},"bfm","home?page="+param);
    	 			}

    	 			//var projectName = "${pageContext.request.contextPath}";

					// Getting URL var by its nam
					$body = $("body");
    	 			$body.addClass("loading");

					 $.ajax({
				            type: "GET",
				            url: loadUrl,
				            success: function(response) {
				                $("#testContentRender").html( response );
				               
				            	//$("#content").remove();
				            	//$("#contentrender").append(response);
				            },
					        complete:function()
							{
								$body.removeClass("loading");
								 //alert("---------"+"${ViewName }");
							},
							error: function(jqXHR, textStatus, errorThrown) {
							    //alert(jqXHR.status);
							   // alert(textStatus);
							    //alert(errorThrown);
							    if(jqXHR.status)
				    			{
			    					//$("#content").remove();
		         					//$("#contentrender").append(jqXHR.responseText);
							    	$("#testContentRender").html( jqXHR.responseText );
				    			}
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
					  $("#testContentRender").load(response);
				  }
			}
		});	
}
function RenderLinkInner(param)
{
	var labelText = $('ul > li > a[onclick="RenderLinkInner(\''+param+'\')"]').closest('li').text();
	$("#labeldashboard").html("");
	$("#labeldashboard").html(labelText);
	
	var $bc = $('<ul class="breadcrumbs"></ul>');
	   var labelModuleText = $("#"+param).text(); 
	   var $li = $('<li></li>').append("<a>"+labelText+"</a>"); 
	   $bc.prepend($li);
    $('.bc').html( $bc.prepend('<li><a href="home">Home</a></li>') );
	if(param == 'myprofile' || param == 'inbox' || param == 'changePassword')
	{	
		$(".middleNavA").hide();
	}
	$.ajax({
		type : "POST",
		url : "./viewAuthentication",
		data : {
			 accessUrl : param
		},
		success : function(response)
		{
		   	  if(response == "success")
			  {
				 var loadUrl = "./"+param;

 	 			var loc = window.location.href,
	 		    index = loc.indexOf('#');
	 			if (index > 0) 
    	 		{
	 				history.pushState({},"bfm","home?page="+param);
	 			}
	 			$body = $("body");
	 			 $body.addClass("loading");
					 $.ajax({
				            type: "GET",
				            url: loadUrl,
				            success: function(response) {
				                $("#testContentRender").html( response );
				            	//$("#content").remove();
				            	//$("#contentrender").append(response);
				            },
					        complete:function()
							{
								$body.removeClass("loading");
							},
							error: function(jqXHR, textStatus, errorThrown) 
							{
							    //alert(jqXHR.status);
							    //alert(textStatus);
							    //alert(errorThrown);
							    if(jqXHR.status)
				    			{
							    	$("#testContentRender").html( jqXHR.responseText );
			    					//$("#content").remove();
		         					//$("#contentrender").append(jqXHR.responseText);
				    			}
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
				  $("#testContentRender").load(response);
			  }
		}
	});
}

function pinUnpin()
{   
  	if(pinStatus == 0){
	    
       $("#showLeftPush").click();
	   pinStatus = 1;
    }
	else if (pinStatus == 1 && temp == 1)
	{
	 $("#showLeftPush").click();
     $(".secNav").hide();
     $("#contentrender").css("margin-left","-241px");
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

$(document).ready(function() {
    var tooltip = $("#agglomerations").kendoTooltip({
        filter: "a",
        width: 120,
        position: "top"
    }).data("kendoTooltip");

    tooltip.show($("#logout"));

    $("#agglomerations").find("a").click(false);
});

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
					
					$('#uploadDialog').dialog('close');
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

function deleteAccess(deleteUrl,gridName)
{
	var grid = $(gridName).data("kendoGrid");
	      $.ajax({
	        type : "POST",
	       url : deleteUrl,
	        dataType : 'text',
	        success : function(response) {
	         if (response == "false") {	         
			     grid.cancelChanges();
		          $("#alertsBox").html("");
		          $("#alertsBox").html("Access Denied");
		          $("#alertsBox").dialog({
			           modal : true,
			           buttons : {
			            "Close" : function() {
			             	$(this).dialog("close");
			             }
			           }
	          	  });          
	          
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
	           var grid = $(gridName).data("kendoGrid");
	           grid.cancelChanges();
	          window.location.href = "./logout";             
	          }
	        }
	       });
		
}

</script>

<script type="text/javascript">
	
function dropDownChecksEditor(container, options) {
	var res = (container.selector).split("=");
	var attribute = res[1].substring(0,res[1].length-1);
	
	$("<select data-bind='value:" + attribute + "'/>")
			.appendTo(container).kendoDropDownList({
				dataTextField : "text",
				dataValueField : "value",
				dataSource : {
					transport : {
						read : "${allChecksUrl}/"+attribute,
					}
				}
			});
}

function comboBoxChecksEditor(container, options) 
{
	var res = (container.selector).split("=");
	var attribute = res[1].substring(0,res[1].length-1);
	
	$("<select data-bind='value:" + attribute + "'/>")
	        .appendTo(container).kendoComboBox({
	        		dataTextField : "text",
					dataValueField : "value",
					placeholder: "Enter or Select",
                    dataSource: {  
                        transport:{
                            read: "${allChecksUrl}/"+attribute,
                        }
                    }
                });
}

function mulitiSelectEditor(container, options) 
{
	var res = (container.selector).split("=");
	var attribute = res[1].substring(0,res[1].length-1);
	
	$("<select multiple='multiple' data-bind='value : "+attribute+"' style='width:150px; float:right'/> ")
			.appendTo(container).kendoMultiSelect({
				dataTextField : "text",
				dataValueField : "value",
				placeholder : "Select",
				dataSource : {
					transport : {
						read : "${allChecksUrl}/"+attribute,
					}
				}
			});
	
}

function textAreaEditor(container, options) 
{
	var res = (container.selector).split("=");
	var attribute = res[1].substring(0,res[1].length-1);
	
    $('<textarea data-text-field="'+attribute+'" data-value-field="'+attribute+'" data-bind="value:' + options.field + '" style="width: 148px; height: 40px;"/>')
         .appendTo(container);
}

function displayMessage(e, gridName, model)
{
	if (typeof e.response != 'undefined')
	{
		if (e.response.status == "EXCEPTION") 
		{
			errorInfo = "";

			if((e.response.result.length) > 1)
			{
				for (i = 0; i < e.response.result.length; i++) 
				{
					errorInfo += (i + 1) + ". " + e.response.result[i]+ "<br>";
				}
			}
			else
			{
				for (i = 0; i < e.response.result.length; i++) 
				{
					errorInfo += e.response.result[i];
				} 
			}	
			
			displayFailureMessage(e, gridName, model, errorInfo);

		}
		
		else if (e.response.status == "FAIL") 
		{
			errorInfo = "";

			if((e.response.result.length) > 1)
			{
				for (i = 0; i < e.response.result.length; i++) 
				{
					errorInfo += (i + 1) + ". " + e.response.result[i].defaultMessage+ "<br>";
				}
			}
			else
			{
				for (i = 0; i < e.response.result.length; i++) 
				{
					errorInfo += e.response.result[i].defaultMessage;
				} 
			}	

			displayFailureMessage(e, gridName, model, errorInfo);
		}
		
		else if (e.type == "create") 
		{
			$("#alertsBox").html("");
			$("#alertsBox").html(model+" record created successfully");
			$("#alertsBox").dialog
			({
				modal : true,
				buttons : 
				{
					"Close" : function() 
					{
						$(this).dialog("close");
					}
				}
			});
			var grid = $("#"+gridName).data("kendoGrid");
			if(grid != null)
			{
				grid.dataSource.read();
				grid.refresh();
				return false;
			}	
		}

		else if (e.type == "update") 
		{
			$("#alertsBox").html("");
			$("#alertsBox").html(model+" record updated successfully");
			$("#alertsBox").dialog
			({
				modal : true,
				buttons : 
				{
					"Close" : function() 
					{
						$(this).dialog("close");
					}
				}
			});
			var grid = $("#"+gridName).data("kendoGrid");
			if(grid != null)
			{
				grid.dataSource.read();
				grid.refresh();
				return false;
			}	
		}
		
		else if (e.type == "destroy") 

		{
			$("#alertsBox").html("");
			$("#alertsBox").html(model+" record deleted successfully");
			$("#alertsBox").dialog
			({
				modal : true,
				buttons : 
				{
					"Close" : function() 
					{
						$(this).dialog("close");
					}
				}
			});
			var grid = $("#"+gridName).data("kendoGrid");
			if(grid != null)
			{
				grid.dataSource.read();
				grid.refresh();
				return false;
			}	
		} 
	}
}

function displayFailureMessage(e, gridName, model, errorInfo)
{
	if (e.type == "create") 
	{
		$("#alertsBox").html("");
		$("#alertsBox").html(
				"Error: Creating the "+model+" record<br>" + errorInfo);
		$("#alertsBox").dialog({
			modal : true,
			buttons : {
				"Close" : function() {
					$(this).dialog("close");
				}
			}
		});
		var grid = $("#"+gridName).data("kendoGrid");
		if(grid != null)
		{
			grid.dataSource.read();
			grid.refresh();
			return false;
		}	
	}

	else if (e.type == "update") 
	{
		$("#alertsBox").html("");
		$("#alertsBox").html(
				"Error: Updating the "+model+" record<br>" + errorInfo);
		$("#alertsBox").dialog({
			modal : true,
			buttons : {
				"Close" : function() {
					$(this).dialog("close");
				}
			}
		});
		var grid = $("#"+gridName).data("kendoGrid");
		if(grid != null)
		{
			grid.dataSource.read();
			grid.refresh();
			return false;
		}	
	}
	
	else if (e.type == "destroy") 
	{
		$("#alertsBox").html("");
		$("#alertsBox").html("Error: Deleting the "+model+" record<br>" + errorInfo);
		$("#alertsBox").dialog
		({
			modal : true,
			buttons : 
			{
				"Close" : function() 
				{
					$(this).dialog("close");
				}
			}
		});
		var grid = $("#"+gridName).data("kendoGrid");
		if(grid != null)
		{
			grid.dataSource.read();
			grid.refresh();
			return false;
		}	
	} 
	
	else
	{
		$("#alertsBox").html("");
		$("#alertsBox").html(
				"Error: Loading "+model+" details<br>" + errorInfo);
		$("#alertsBox").dialog
		({
			modal : true,
			buttons : 
			{
				"Close" : function() 
				{
					$(this).dialog("close");
				}
			}
		});
	}	
}

</script>

<script>
			/* var step = 200;
			var scrolling = false;
			
			// Wire up events for the 'scrollUp' link:
			$("#scrollUp").bind("click", function(event) {
			    event.preventDefault();
			    // Animates the scrollTop property by the specified
			    // step.
			    $(".nav").animate({
			        scrollTop: "-=" + step + "px"
			    });
			}).bind("mouseover", function(event) {
			    scrolling = true;
			    scrollContent("up");
			}).bind("mouseout", function(event) {
			    scrolling = false;
			});


			$("#scrollDown").bind("click", function(event) {
			    event.preventDefault();
			    $(".nav").animate({
			        scrollTop: "+=" + step + "px"
			    });
			}).bind("mouseover", function(event) {
			    scrolling = true;
			    scrollContent("down");
			}).bind("mouseout", function(event) {
			    scrolling = false;
			});

			function scrollContent(direction) {
			    var amount = (direction === "up" ? "-=1px" : "+=1px");
			    $(".nav").animate({
			        scrollTop: amount
			    }, 1, function() {
			        if (scrolling) {
			            scrollContent(direction);
			        }
			    });
			} */
			
			$('#general a').on('click', function() 
					{
					 var selectedFromSecondMenu = $(this).text();
					 $("#labeldashboard").html("");
					 $("#labeldashboard").html($(this).text());
					 selectedFromSecondMenu = selectedFromSecondMenu.split(" ").join("");
					 selectedFromSecondMenu = selectedFromSecondMenu+"InnerMenu";
					 $('#wrapper ul').not("#"+selectedFromSecondMenu).hide();
					 if($('#'+selectedFromSecondMenu).css('display') == 'none')
					 {
						 $('#'+selectedFromSecondMenu).css('display','block');
					 }
					 $("#selectedUsecaseMenu").val($(this).text());
					  var $this = $(this),
					      $bc = $('<ul class="breadcrumbs"></ul>');

					  $this.parents('li').each(function(n, li) {
					      var $a = $(li).children('a').clone();
					      var $li = $('<li></li>').append($a); 
					      $bc.prepend($li);
					  });
					    $('.bc').html( $bc.prepend('<li><a href="home">Home</a></li>') );
					    $(".bc > ul > li > a > span").remove();
					    return false;
					}); 
			</script>

<%-- <script type="text/javascript"	src="<c:url value='/resources/jquery.min.js'/>"></script> --%>



			
<div class="modal"><!-- Place at bottom of page --></div>
<style type="text/css">
/* Start by setting display:none to make this hidden.
   Then we position it in relation to the viewport window
   with position:fixed. Width, height, top and left speak
   speak for themselves. Background we set to 80% white with
   our animation centered, and no-repeating */
.modal {
    display:    none;
    position:   fixed;
    z-index:    1000;
    top:        0;
    left:       0;
    height:     100%;
    width:      100%;
    background: rgba( 255, 255, 255, .8 ) 
                url('./resources/pageload.gif') 
                50% 50% 
                no-repeat;
}

/* When the body has the loading class, we turn
   the scrollbar off with overflow:hidden */
body.loading {
    overflow: hidden;   
}

/* Anytime the body has the loading class, our
   modal element will be visible */
body.loading .modal {
    display: block;
}
</style>
</body>
</html>