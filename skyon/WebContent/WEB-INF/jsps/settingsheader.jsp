<%@taglib prefix="kendo" uri="/WEB-INF/kendo.tld"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<link href="<c:url value='/resources/kendo/css/web/kendo.common.min.css'/>" rel="stylesheet" />
<link href="<c:url value='/resources/kendo/css/web/kendo.rtl.min.css'/>" rel="stylesheet" />
<link href="<c:url value='/resources/kendo/css/web/kendo.bootstrap.min.css'/>" rel="stylesheet" />
<%-- <link href="<c:url value='/resources/kendo/css/dataviz/kendo.dataviz.min.css'/>" rel="stylesheet" />
<link href="<c:url value='/resources/kendo/css/dataviz/kendo.dataviz.default.min.css'/>" rel="stylesheet" /> --%>
<link href="<c:url value='/resources/kendo/shared/styles/examples-offline.css'/>" rel="stylesheet">
<script src="<c:url value='/resources/kendo/js/jquery.min.js' />"></script>
<script src="<c:url value='/resources/kendo/js/kendo.all.min.js' />"></script>
<script src="<c:url value='/resources/kendo/shared/js/console.js'/>"></script>
<%-- <script src="<c:url value='/resources/kendo/shared/js/prettify.js'/>"></script> --%>

<div id="content">
    <div class="contentTop">
        <span class="pageTitle"><span class="icon-screen"></span>${ViewName }</span>
        
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
							<li><a href="${home}">Home</a></li>
						</c:when>
						<c:when
							test="${status.index == fn:length(breadcrumb.tree)-1 && status.index!=0}">
							<li><a href="#">${bc.name}</a></li>
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
	      
<%-- 	<div class="wrapper">
	   <ul class="middleNavA">
				<li><a title="Manage Products" href="#" onclick="testInner('productDefinition')"><img alt="" src="<c:url value='/resources/images/icons/color/product2.png'/>"><span>Manage Product</span></a></li>
				
				<li><a title="Manage Products" href="#" onclick="testInner('moduleDefinition')"><img alt="" src="<c:url value='/resources/images/icons/color/module.png'/>"><span>Manage Module</span></a></li>
				<li><a title="Manage Products" href="#" onclick="testInner('formDefinition')"><img alt="" src="<c:url value='/resources/images/icons/color/forms.png'/>"><span>Manage Form</span></a></li>
				<li><a title="Manage Products" href="#" onclick="testInner('taskDefinition')"><img alt="" src="<c:url value='/resources/images/icons/color/user.png'/>"><span>Manage Task</span></a></li>
				
		</ul>	
	</div> --%>