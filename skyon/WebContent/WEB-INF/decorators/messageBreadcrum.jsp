<%@include file="/common/taglibs.jsp"%>
    <div class="contentTop">
        <span class="pageTitle"><span class="icon-screen"></span>${ViewName}</span>
       
        <%-- <ul class="quickStats">
            <li>
                <a href="" class="blueImg"><img src="${ctx}/resources/images/icons/quickstats/plus.png" alt="" /></a>
                <div class="floatR"><strong class="blue">5489</strong><span>visits</span></div>
            </li>
            <li>
                <a href="" class="redImg"><img src="${ctx}/resources/images/icons/quickstats/user.png" alt="" /></a>
                <div class="floatR"><strong class="blue">4658</strong><span>users</span></div>
            </li>
            <li>
                <a href="" class="greenImg"><img src="${ctx}/resources/images/icons/quickstats/money.png" alt="" /></a>
                <div class="floatR"><strong class="blue">1289</strong><span>orders</span></div>
            </li>
        </ul> --%>
    </div>
    
    <!-- Breadcrumbs line -->
    <div class="breadLine">
        <div class="bc">
            <ul id="breadcrumbs" class="breadcrumbs">
                <!-- <li><a href="home">Home</a></li> -->
                
                <c:forEach var="bc" items="${breadcrumb.tree}" varStatus="status">
				<c:choose>
				<c:when test="${status.index==0}">
						<li><a href="home"
							>Home</a></li>
					</c:when>
					<c:when
						test="${status.index == fn:length(breadcrumb.tree)-1 && status.index!=0}">
						<li class="current"><a href="#" style="color: #666666">${bc.name} </a></li>
					</c:when>
				</c:choose>
			</c:forEach>		                
               
            </ul>
        </div> 
        
        <div class="breadLinks">
            <%-- <ul>
                <li><a href="#" title=""><i class="icos-list"></i><span>Orders</span> <strong>(+58)</strong></a></li>
                <li><a href="#" title=""><i class="icos-check"></i><span>Tasks</span> <strong>(+12)</strong></a></li>
                <li class="has">
                    <a title="">
                        <i class="icos-money3"></i>
                        <span>Invoices</span>
                        <span><img src="${ctx}/resources/images/elements/control/hasddArrow.png" alt="" /></span>
                    </a>
                    <ul>
                        <li><a href="#" title=""><span class="icos-add"></span>New invoice</a></li>
                        <li><a href="#" title=""><span class="icos-archive"></span>History</a></li>
                        <li><a href="#" title=""><span class="icos-printer"></span>Print invoices</a></li>
                    </ul>
                </li>
            </ul> --%>
        </div>
    </div>
     <!-- Main content -->
    <div class="wrapper" id="myMenuId">
        <%-- <ul class="middleNavR">
            <li><a href="#" title="Add an article" class="tipN"><img src="${ctx}/resources/images/icons/middlenav/create.png" alt="" /></a></li>
            <li><a href="#" title="Upload files" class="tipN"><img src="${ctx}/resources/images/icons/middlenav/upload.png" alt="" /></a></li>
            <li><a href="#" title="Add something" class="tipN"><img src="${ctx}/resources/images/icons/middlenav/add.png" alt="" /></a></li>
            <li><a href="#" title="Messages" class="tipN"><img src="${ctx}/resources/images/icons/middlenav/dialogs.png" alt="" /></a><strong>8</strong></li>
            <li><a href="#" title="Check statistics" class="tipN"><img src="${ctx}/resources/images/icons/middlenav/stats.png" alt="" /></a></li>
        </ul> --%>
        
       <%--  <ul class="middleNavR">
            <li><a class="tipN" href="#" original-title="Add an article"><img alt="" src="${ctx}/resources/images/icons/middlenav/create.png"></a></li>
            <li><a class="tipN" href="#" original-title="Upload files"><img alt="" src="${ctx}/resources/images/icons/middlenav/upload.png"></a></li>
            <li><a class="tipN" href="#" original-title="Add something"><img alt="" src="${ctx}/resources/images/icons/middlenav/add.png"></a></li>
            <li><a class="tipN" href="#" original-title="Messages"><img alt="" src="${ctx}/resources/images/icons/middlenav/dialogs.png"></a><strong>8</strong></li>
            <li><a class="tipN" href="#" original-title="Check statistics"><img alt="" src="${ctx}/resources/images/icons/middlenav/stats.png"></a></li>
        </ul> --%>
        
        
       <%--  <c:forEach var="category" items="${navigation.keySet()}">
				<c:set var="count" value="1" scope="page" />				
		       	<c:forEach var="widget" items="${navigation.get(category)}">
		       		<c:if test="${widget.include()}">	
		       		<ul id="thirdMenu${count}" class="middleNavA" style="display: none;height:100%;">			       					
			       		<c:forEach var="example" items="${widget.items}">
				       	   	<c:if test="${empty example.url}">	
					       		<c:forEach var="third" items="${example.thirdLevel}">
					       			<li><a  class="tipN" title="Manage Project" href="${third.url}">
											<img width="50" height="50" alt="" src="<c:url value='/resources/images/icons/comimages/help.png'/>">
										<span id="mNavProject">${third.text}</span></a></li>
					       		 		       		
					       		</c:forEach>
					       		 
					       	</c:if>
				       	    
				       				
				       	</c:forEach>
				       	</ul>
		       		</c:if>
		       		<c:set var="count" value="${count + 1}" scope="page"/>						       		
		       	</c:forEach>
						       	
	  </c:forEach> --%>
        <br/>
        <script>
				function changeDynamicMenu(param,id)
				{
					$("#moduleName").val(param);
					$("#dynamicInnerMenu").show();
					$('#general ul').not("#"+id).hide();
					if($('#'+id).css('display') == 'none')
					 {
						 $('#'+id).css('display','block');
					 }
					$.ajax({
						type:"POST",
						url : "./thirdlevelMenu/"+id,
						data :{
							"moduleName" : param
						},
						success : function(response)
						{
							//alert(response);
						}
					});
					
					
				}
				
				function changeThridLevelMenu(param)
				{
					//alert(param+"---"+$("#moduleName").val());
					
					$("#menuName").val(param);
					document.getElementById("getMenuId").submit();
					//window.location.href="./getMenu";
					
					 
				}
				
				function secondLevelMenuId(id)
				{
					//alert(id);
					<% session.getServletContext().setAttribute("menuName", null);%>
					
					window.location.href=id;
					//alert($(id).closest('ul').attr('id'));
					
					/* $('#myMenuId ul').not("#"+id).hide();
				 	alert($('#'+id).css('display'));
					if($('#'+id).css('display') == 'none')
					 {
						alert("In If");
							$("#menu2thirdlevel").css('display','block');
						 $('#'+id+"thirdlevel").css('display','block');
					 } */
				}
			
			</script>
        </div>
         
         