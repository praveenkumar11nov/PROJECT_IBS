<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="kendo" uri="http://www.kendoui.com/jsp/tags"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<title>IREO - Login</title>
<%@include file="links.jsp"%>

<style>
.next, .prev {
    position: absolute;
    cursor: pointer;
    top: 70px;
}

.next {
    right: -40px;
}

.prev {
    left: -40px;
} 
</style>
</head>

<div id="alertsBox" title="Alert"></div> 

<script type="text/javascript">

function checkCookie(){
	$("#userid").focus(); 
	var cookieEnabled=(navigator.cookieEnabled)? true : false;
	if(!(cookieEnabled)){		
		$("#alertsBox").html("");
			$("#alertsBox").html("Please Enable Cookie in Your Browser");
			$("#alertsBox").dialog({
				modal: true,
				draggable: false,
				resizable: false,
				buttons: {
					"Close": function() {
						$( this ).dialog( "close" );
					}
				}
			}); 
		return false;
	}
}

function afterPopup(){
	$( "#forgotPassword" ).dialog( "close" );
}
function displaypassword(){
  $( "#forgotPassword" ).dialog({
	  modal: true,
	  draggable: false,
	  resizable: false,
	  close: function() {
		  $('#forgot')[0].reset();
	  }
  });
}	
function getUserId(param){
	
	$.ajax({  
	     type : "Post",   
	     url : "./getUserId", 
	     dataType:"text",
	     data:"imageName="+param,
	     success : function(response) {
	    	    if(!(response.length==32)){
	    	    	$('#userid').val(response);	    	    
	    	    }   	    
	      }     
	    }); 
}
</script>

<body onload="checkCookie();">

	<c:if test="${not empty msg}">
		<div id="forgotPassAlert" title="Alert">
				<b>${msg}</b>
		</div>
	
		<script type="text/javascript">		
		$("#forgotPassAlert").dialog({
			modal: true,
			draggable: false,
			resizable: false,
			buttons: {
				"Close": function() {
					$( this ).dialog( "close" );
				}			
			}
		}); 
		</script>
	
	</c:if>

	<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION.message}">
		
		<div id="errorMessage" title="Alert">
			<b>${SPRING_SECURITY_LAST_EXCEPTION.message}</b>
		</div>
		
		<script type="text/javascript">

		$("#errorMessage").dialog({
			modal: true,
			draggable: false,
			resizable: false,
			draggable: true,
			buttons: {
				"Close": function() {
					$( this ).dialog( "close" );
				}
			}
		}); 
		</script>		
	</c:if>	
	<c:remove var = "SPRING_SECURITY_LAST_EXCEPTION" scope = "session" />
	<!-- Top line begins -->
	<div id="top">
		<div class="wrapper">
			<table style="width: 100%"><tr><td width="50%"><%-- <a href="#" title="" class="logo"> <img	src="<c:url value='/resources/images/ireo.png'/>" alt="" /> </a> --%>  <a href="#" title="" class="logo"><span style="color:#C1C1C1;font-size:20px;cursor: default;">Skyon</span></a> </td><td style="vertical-align: middle;" width="50%" align="right"><span style="color:#C1C1C1;font-size:15px;">${DeployVersion}</span></td></tr></table>
			
		</div>
	</div>
	<!-- Top line ends -->


	<!-- Login wrapper begins -->
	<div class="loginWrapper" >

		<!-- New user form -->
		<form action="<c:url value='j_spring_security_check' />" id="login"	method="POST">				
			
			<div class="loginPic">
				<a title="" id="first"  onclick="getUserId(this.id);">
					 <img width="100px" height="100px" src="<c:url value='/cookie/getuserimage/' />" alt="" />
				</a>
			</div>
			
			<input type="text" id="userid" name='j_username' placeholder="Your User Name" class="loginUsername" style="width: 200px" maxlength="25" required oninvalid="this.setCustomValidity('Please Enter UserName')" onchange="this.setCustomValidity('')"/>
			
			<input id="password" type="password" name='j_password'	placeholder="Password" class="loginPassword" style="width: 200px" maxlength="25" required oninvalid="this.setCustomValidity('Please Enter Password')" onchange="this.setCustomValidity('')"  />

			<div class="logControl">
				 <div class="memory">
					<a onclick="displaypassword();">Forgot Password?</a>
				</div> 	
				<input type="submit" name="submit" value="Login" class="buttonM bBlue" onsubmit="checkCookie();"/>
			</div>
		</form>
		
		<div id="forgotPassword" title="Forgot Password?" style="display: none;">
					
			<form action="./recoverPassword" id="forgot" method="POST">
					Enter Your Login Name: <br>
					<input type="text" name="urLoginName" style="margin:0px;" placeholder="Your Name" required maxlength="25" oninvalid="this.setCustomValidity('Please Enter User Login Name')" onchange="this.setCustomValidity('')"  /> 
					Enter Your Email Address: <br>
					<input type="email" name="emailId"	placeholder="Your Email" required maxlength="25" oninvalid="this.setCustomValidity('Please Enter An Valid Email Address')" onchange="this.setCustomValidity('')" /> 
					Enter Your Mobile Number: <br>
					<input type="tel" name="mobileNo" placeholder="Your Mobile Number (10 digits)" required pattern="\d{10}" maxlength="10" oninvalid="this.setCustomValidity('Please Enter 10 Digit Mobile Number')" onchange="this.setCustomValidity('')"  />
					<div class="logControl">
						<input id="buttonS bLightBlue" type="submit" class="buttonS bLightBlue" value="Recover Password" />
					</div>				
			</form>			
	   </div>
	</div>
</body>
</html>