<%@include file="/common/taglibs.jsp"%>
<%@ page contentType="text/html; charset=utf-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authentication var="principal" property="principal" />
<fmt:setBundle basename="messages" />
<!-- Sidebar begins -->


<style>
.Color{
 background: #2b908f;
 color: #FFFFFF
}
</style>
<div id="sidebar">
    <div class="mainNav">
        <div class="user">
            <a title="" class="leftUserDrop"><img src="${ctx}/ldap/getuserimage/" width="70" height="70" /></a><span><strong >${userId}</strong></span>
            <ul class="leftUser">
                <li><a href="myprofile" title="" class="sProfile">My profile</a></li>
                <li><a href="inbox" title="" class="sMessages">Messages</a></li>
                <li><a href="changePassword" title="" class="sSettings">Change Password</a></li>
                <li><a href="logout" title="" class="sLogout">Logout</a></li>
            </ul>
            <!-- <font color="white">Search Menu</font> -->
            
            
            <br/>
            <input type="text" class="k-textbox" style="width:80px" id="search" placeholder="Search Menu..."/>
            <input type="text" class="k-textbox" style="display:none;" value="<%=session.getAttribute("menuId")%>" id="firstMenuId"/>
            <form id="getMenuId" action="./getMenu" method="POST">
            <input type="text" class="k-textbox" name="moduleName" style="display:none;" value="<%=session.getAttribute("moduleName")%>"  id="moduleName"/>
            <input type="text" class="k-textbox" name="menuName" style="display:none;"  id="menuName" value="<%=session.getServletContext().getAttribute("menuName")%>"/>
            </form>
        </div>
        
        <!-- Responsive nav -->
        <div class="altNav">
            <div class="userSearch">
                <form action="">
                    <input type="text" placeholder="search..." name="userSearch" />
                    <input type="submit" value="" />
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
       
			<c:if test="${empty navigation}" >
			<script>
				window.location.href="home";
			</script>
			</c:if>
			
			<ul class="nav" style="overflow:hidden;height: 500px;" onclick="closePop()">
				
				<c:forEach var="category" items="${navigation.keySet()}">
			       	<c:set var="count" value="1" scope="page" />
			       	<c:forEach var="widget" items="${navigation.get(category)}">			       		
			       		<c:if test="${widget.include()}">
				       		<li class="manu${count}"><a   href="#" onclick="changeDynamicMenu('${widget.text}','menu${count}',${count});" id="FileDrawMgm" class="notactive" title=""><img src="<c:url value='/resources/images/icons/mainnav/${widget.text}.png'/>" alt="" /><span>${widget.text}</span></a>
				       		</li>
				       		<c:set var="count" value="${count + 1}" scope="page"/>
			       		</c:if>
			       	</c:forEach>
        		</c:forEach>
			</ul>
			
			<button id="toggle" style="display:none;">Turn On</button>
			<br />
			<button id="showLeftPush" style="display:none;">Show/Hide
				Left Push Menu</button>
    </div>
    
    <script type="text/javascript"
			src="<c:url value='/resources/classie.js'/>"></script>
		<!-- Secondary nav -->
		<div class="secNav" id="dynamicInnerMenu" style="display: none;height:100%;">
		
			<div class="secWrapper" id="temp">
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
							
							<c:forEach var="category" items="${navigation.keySet()}">
								<c:set var="count" value="1" scope="page" />
						       	<c:forEach var="widget" items="${navigation.get(category)}">
						       		<c:if test="${widget.include()}">
						       			
						       			<ul class="subNav" id="menu${count}" style="display: none;height:100%;">
							       			<c:forEach var="example" items="${widget.items}">
							       				<c:if test="${example.include()}">
							       					<c:if test="${example.url == ''}">	
							       						<c:set var="className" value="${example.text}"></c:set>						       						
								       						<li><a class="${fn:replace(className, ' ','')}" href="#" onclick="changeThridLevelMenu('${example.text}','${widget.text}','${fn:replace(className, ' ','')}')"><img src="./resources/subNavIcon.png" alt="">${example.text}</a>
								       							
								       							<div id="leftThirdLevel${fn:replace(className, ' ','')}" style="display: none;">								       								
								       							</div>
								       						</li>								       						 	 						       						 
								    				</c:if>
							       					<c:if test="${example.url != ''}">
							       						<li><a onclick="secondLevelMenuId('${example.url}')" href="#"><span class="icos-fullscreen"></span><span id="mNavProject">${example.text}</span></a></li>
							       					</c:if>
							       					<%-- <c:url value='${example.url.replaceAll(".html", "")}' /> --%>
							       				</c:if>
							       			</c:forEach>
							       		</ul>
							       		
						       		</c:if>
						       		<c:set var="count" value="${count + 1}" scope="page"/>
						       	</c:forEach>
						       	
					        </c:forEach>
						</div>
		
						</div>

					<div id="alt1">
						<div class="divider">
							<span></span>
						</div>
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
		</div>
</div>
<!-- Sidebar ends -->

<script type="text/javascript">	


  function closePop()
  {
	 
	  $(".leftUser").hide();
	  $("#notification").hide();
	  
	  //$("#notiMsgSpan").hide();
	  
	  
	  
  }




	var temp = 0;
	var menuLeft = document.getElementById( 'cbp-spmenu-s1' ),
			 
	showLeftPush = document.getElementById( 'showLeftPush' ),
	 
	body = document.getElementById('content');
          
 
	showLeftPush.onclick = function() {
   		temp = 1;	 
		classie.toggle( this, 'active' );	 
		$(".secNav").show();	
		$("#content").css("margin-left","327px");
	};
   
$("#toggle").click(function() {
    var button = $(this),        
        enable = button.text() == "Turn Off";
    
    if (enable) {
    	$(".secNav").hide();

	 	var menuLeft = document.getElementById( 'cbp-spmenu-s1' ),
		body = document.getElementById('content');
	 	classie.toggle( this, 'active' ); 
		$("#content").css("margin-left","0px");
        
    } 
    else {

		$(".secNav").show();
		var menuLeft = document.getElementById( 'cbp-spmenu-s1' ),	
		body = document.getElementById('content');
		classie.toggle( this, 'active' );
		$("#content").css("margin-left","0px");
    }
    button.text(enable ? "Turn On" : "Turn Off");
});

var temp = 0;
var pinStatus = 0;
 
		
		

function onloadTest()
{
	//$("#showLeftPush").click();
	var pageName = getUrlVars()["param"];
	var id = $("#firstMenuId").val();
	
	$('#general ul').not("#"+id).hide();
	if($('#'+id).css('display') == 'none')
	 {
		 $('#'+id).css('display','block');
	 }
	
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
				changeMenu(pageName);
			}
		}
	});	  
	
	if($("#menuName").val()=="null")
		{
			$('#myMenuId ul.middleNavA').not("#"+id).hide();
			if($('#'+id+"thirdlevel").css('display') == 'none')
			{
				$('#'+id+"thirdlevel").css('display','block');
			}
		}
	else
		{
			var menuId = $("#menuName").val();
			$('#myMenuId ul.middleNavA').not("#"+menuId).hide();
			if($('#'+menuId).css('display') == 'none')
			{
				$('#'+id).css('display','block');
			}
		
		}
	$("#themeSelection").show();
} 

function changeMenu(param)
{     
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



$(document).ready(function () {
	 
    
	$("#search").on("keyup", function () {
	if (this.value.length > 0) {   
	  $("ul[class=nav]>li").hide().filter(function () {
	    return $(this).text().toLowerCase().indexOf($("#search").val().toLowerCase()) != -1;
	  }).show(); 
	}  
	else { 
	  $("li").show();
	}
	}); 

	});

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
	      $.ajax({
	        type : "POST",
	       url : deleteUrl,
	        dataType : 'text',
	        success : function(response) {
	         if (response == "false") {
	          var grid = $(gridName).data("kendoGrid");
	          grid.cancelRow();
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
	          return false;
	          var grid = $(gridName).data("kendoGrid");
	          grid.cancelChanges();
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

$('span').click(function(){
    var modName = $(this).text();
    var d = new Date();
    d.setTime(d.getTime() + (2*24*60*60*1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = "moduleName" + "=" + modName + "; " + expires;
});

</script>




