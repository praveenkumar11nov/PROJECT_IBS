
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
<title>IREO - Login</title>

<%@include file="links.jsp"%>

</head>

<body>

	<!-- Top line begins -->
	<div id="top">
		<div class="wrapper">
			<a href="#" title="" class="logo"><img
				src="<c:url value='/resources/images/ireo.png'/>" alt="" /></a>

			<!-- Right top nav -->
			<!-- <div class="topNav">
            <ul class="userNav">
                <li><a href="#" title="" class="screen"></a></li>
                <li><a href="#" title="" class="settings"></a></li>
                <li><a href="#" title="" class="logout"></a></li>
            </ul>
        </div> -->
		</div>
	</div>
	<!-- Top line ends -->


	<!-- Login wrapper begins -->
	<div class="loginWrapper">

		<!-- New user form -->
		<form action="./login" id="login" method="POST">
			<c:if test="${msg eq 'invalid'}">
				<span class="red"><spring:message code="label.invalidUser"></spring:message></span>
			</c:if>
			<div class="loginPic">
				<a href="#" title=""><img
					src="<c:url value='/resources/images/userLogin2.png'/>" alt="" /></a>
				<div class="loginActions">
					<div>
						<a href="#" title="" class="logback flip"></a>
					</div>
					<div>
						<a href="#" title="Forgot password?" class="logright"></a>
					</div>
				</div>
			</div>
			<%-- <form:input path="userid" placeholder="Your username" cssClass="loginUsername"/>
        <form:password path="password" placeholder="Password" cssClass="loginPassword"/>   --%>
			<input type="text" name="userid" placeholder="Your username"
				class="loginUsername" style="width: 236px" /> <input
				type="password" name="password" placeholder="Password"
				class="loginPassword" style="width: 236px" />

			<div class="logControl">
				<div class="memory">
					<input type="checkbox" checked="checked" class="check"
						id="remember2" /><label for="remember2">Remember me</label>
				</div>
				<input type="submit" name="submit" value="Login"
					class="buttonM bBlue" />
			</div>
		</form>

	</div>
	<!-- Login wrapper ends -->

</body>
</html>