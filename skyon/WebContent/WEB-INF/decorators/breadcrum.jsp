<%@include file="/common/taglibs.jsp"%>
    <div class="contentTop">
        <span class="pageTitle"><span class="icon-screen"></span>${ViewName}</span>       
       <c:if test="${ViewName == 'Dashboard'}">
        <ul class="quickStats">
           <li>
                <a href="" class="greenImg"><img src="./resources/images/user.png" alt="" /></a>
                <div class="floatR"><strong class="blue">${User.activeCount}</strong><span>Active Staff</span></div>
            </li>
            <li>
                <a href="" class="redImg"><img src="./resources/images/user.png" alt="" /></a>
                <div class="floatR"><strong class="blue">${User.inActiveCount}</strong><span>In-Active Staff</span></div>
            </li>
            
        </ul>
       </c:if>
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
					<c:when test="${status.index==1}">
						<li><a href="#" style="cursor: default;"
							>${bc.name} </a></li>
					</c:when>
					<c:when	test="${status.index == fn:length(breadcrumb.tree)-1 && status.index!=0}">
						<li class="current"><a href="#" style="color: #666666;cursor: default;">${bc.name} </a></li>
					</c:when>
					
					
				</c:choose>
			</c:forEach>		                
               
            </ul>
        </div>      
       
        
        <div class="breadLinks">            
        </div>
    </div>
     <!-- Main content -->
    <div class="wrapper" id="myMenuId">        
        <br/>
   </div>      
        <% if((session.getServletContext().getAttribute("menuName") == null && session.getAttribute("thirdLevelMenu")==null)  ){ %>
        <c:forEach var="category" items="${navigation.keySet()}">
				<c:set var="count" value="1" scope="page" />				
		       	<c:forEach var="widget" items="${navigation.get(category)}">
		       		<c:if test="${widget.include()}">	
		       		<ul class="middleNavA" id="menu${count}thirdlevel" style="display: none;height:100%;">			       					
			       		<c:forEach var="example" items="${widget.items}">
				       	   	 			       		        		 			       		
					       		 <c:if test="${not empty example.url}" >
					       			<li id="menuLi"><a  id="tipN" title="${example.text}" href="${example.url}">
											<img width="50" height="50" alt="" src="<c:url value='/resources/images/modules/${widget.text}/${example.text}.png'/>" >
										<span id="mNavProject">${example.text}</span></a></li>
					      		</c:if>
					      		 <c:if test="${empty example.url}" >
					       			<li id="menuLi"><a  id="tipN" title="${example.text}" href="#" onclick="afterChageMenu('${example.text}')">
											<img width="50" height="50" alt="" src="<c:url value='/resources/images/modules/${widget.text}/${example.text}.png'/>">
										<span id="mNavProject">${example.text}</span></a></li>
					      		</c:if>
					      		
				       				
				       	</c:forEach>
				       	</ul>
		       		</c:if>	
		       		<c:set var="count" value="${count + 1}" scope="page"/>						       		
		       	</c:forEach>
						       	
	  </c:forEach>
	  <%} else {%>
	  <c:forEach var="category" items="${thirdLevelMenu.keySet()}" >
				<c:set var="count" value="1" scope="page" />				
		       	<c:forEach var="widget" items="${thirdLevelMenu.get(category)}">
		       		<c:if test="${widget.include()}">	
		       		<ul class="middleNavA" id="${fn:replace(widget.text, ' ','')}">			       					
			       		<c:forEach var="example" items="${widget.items}">				       	   	 			       		        		 			       		
					       		
					       			<li id="menuLi"><a  id="tipN" title="${example.text}" href="${example.url}">
											<img width="50" height="50" alt="" src="<c:url value='/resources/images/modules/${widget.text}/${example.text}.png'/>">
										<span id="mNavProject">${example.text}</span></a></li>
					      
				       				
				       	</c:forEach>
				       	</ul>
		       		</c:if>	
		       		<c:set var="count" value="${count + 1}" scope="page"/>						       		
		       	</c:forEach>
						       	
	  </c:forEach>
	  
	  <%} %>
    	<br/>
    	<br/>
    <script>     			
    		var status="";	
    		var result="";
			    /* function changeThirdLevelMenuItems(){		
			    	
			    	changeThridLevelMenu();
			    } */
				function changeDynamicMenu(param,id,count){
			    	
			    	$('li[class^=manu]').css("background","#5f5f5f");
			    	$('.manu'+count).css("background","#464651");

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
							
							
						}
					});
					
					
				}
				
				function changeThridLevelMenu(param,param2,param3)
				{
					var usercase="";	
					if(param3==status && result=="true"){						
						$("#leftThirdLevel"+param3).hide();	
						result="false";
					}
					else{
						$.ajax({
							type:"POST",
							url : "./leftthirdlevelMenu",
							async: false,
							dataType : "JSON",
							data :{
								"moduleName" : param2,
								"usecaseName":param,
							},
							success : function(response)
							{							
								usercase="<li>";
								var temp;
								for (var i = 0, len = response.length; i < len; ++i) {
					                var results = response[i];
					                temp="<a style='color: #2b6893;' href="+results.url+"><img src='./resources/thirdmenuicon.png'/><span id='mNavProject'>"+results.text+"</span></a>";
					                usercase+=temp;
					            }
								
								usercase +="</li>";
								status=param3;
								result="true";
							}
						});					
						
		   				$("#leftThirdLevel"+param3).html(usercase);
		    			$("#leftThirdLevel"+param3).show();
					}				
					 
				}
				
				function afterChageMenu(param){					
					$("#menuName").val(param);
					document.getElementById("getMenuId").submit();
				}
				
				function secondLevelMenuId(id)
				{
					<% session.getServletContext().setAttribute("menuName", null);%>					
				window.location.href=id;
					//window.location.reload();
					

				}

				$('li[id=menuLi]').click(function(){
					var modName = $(this).find('a').find('span').text();
				    var d = new Date();
				    d.setTime(d.getTime() + (2*24*60*60*1000));
				    var expires = "expires="+d.toUTCString();
				    document.cookie = "moduleName" + "=" + modName + "; " + expires;
				});
				$( document ).ready(function() {		
					
					var name = "moduleName" + "=";
				    var ca = document.cookie.split(';');
				    for(var i=0; i<ca.length; i++) {
				        var c = ca[i];
				        while (c.charAt(0)==' ') c = c.substring(1);
				        if (c.indexOf(name) != -1) {
				        	var modname = c.substring(name.length, c.length);
				        	$("span[id=mNavProject]:contains('"+modname+"')").parent().addClass("activeCss");
				        	$('.activeCss').css("background","#D0D0CF");
				        }
				    }
				});
			</script>
<style>
	.greenImg img {
 		padding: 9px 10px;
}
.redImg {
    background: none repeat scroll 0% 0% #A52C2C;
}
.greenImg {
    background: none repeat scroll 0% 0% #4CA237;
}
.quickStats .blue {
    color: #2A8F3D;
}
.quickStats li span {
    display: block;
    color: #000;
    font-size: 11px;
}
			</style>
			