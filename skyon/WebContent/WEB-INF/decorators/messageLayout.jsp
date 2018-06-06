 <%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@ page contentType="text/html; charset=utf-8" language="java" %>
<title>Skyon</title>
<link rel="shortcut icon" href="resources/favicon.ico" type="image/icon" />
<!-- <link rel="icon" href="yourImage.gif" type="image/gif" /> --> 

<%@include file="/common/css.jsp"%>
<decorator:head/>
</head>
<body dir="${dir}" onload="onloadTest();">
	<jsp:include page="header.jsp" />
	<jsp:include page="leftMenu.jsp" />
	 <!-- Content begins -->
	<div id="content">
		<jsp:include page="messageBreadcrum.jsp" />
		<div style="padding-left:30px;">
			<decorator:body />
	 	</div>
	<jsp:include page="footer.jsp" />
	</div>
</body>
</html>