<%@include file="/common/taglibs.jsp"%>
     <div class="contentTop">
        <span class="pageTitle"><span class="icon-screen"></span>${ViewName}</span>       
       
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
        </div>
    </div>
     <!-- Main content -->
    <div class="wrapper" id="myMenuId">
      
        <br/>
        <script>
        function changeDynamicMenu(param,id){	
			
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
				
        function secondLevelMenuId(id)
		{
			//alert(id);
			<% session.getServletContext().setAttribute("menuName", null);%>					
			window.location.href=id;
			
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
			                temp="<a style='color: #2b6893;' href="+results.url+"><img src='./resources/thirdmenuicon.png'/>"+results.text+"</a>";
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
			
		</script>
        </div>
         
         