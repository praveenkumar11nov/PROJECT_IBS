<%@ include file="/common/taglibs.jsp"%>
<%@page import="java.util.List"%>
<%--  <%@page contentType="charset=UTF-8" %> --%>

 <!-- Top line begins -->
<div id="top">
    <div class="wrapper">
     
       <%-- <a href="#" title="" class="logo"><img src="${ctx}/resources/images/logo1.png" alt="" /></a><h2 

align="left">Skyon</h2> --%> 
     
        <a href="#" title="" class="logo"><span style="color:#C1C1C1;font-size:20px;cursor: default;">Skyon</span></a> 
         
        <div class="topNav">
            <ul class="userNav">            	
            	<li id="not"><a href="#" title="notification" class="settings"></a></li><span class="badge" id="notiMsgSpan1"  style="margin-top: -29px; vertical-align: top;"></span>
            	<li id="notify"><a href="#" title="task" class="screen" ></a></li><span class="badge" id="notiMsgSpan"  style="margin-top: -29px; vertical-align: top;"></span> 
            	
                    <!--  <a href="#" class="tips"></a> -->            	
            	                            
                <li><a href="./logout" title="logout" class="logout" ></a></li>               
            </ul>
            <br><br>
            <div style="display:none;" id="notification">
            	 <div  style='background-color:#dddddd;' class="alert-box notice">	             	
	             </div>
	             
            </div>
            
        </div>
        
        <!-- Responsive nav -->
        <ul class="altMenu">
            <li><a href="index.html" title="">Dashboard</a></li>
            <li><a href="ui.html" title="" class="exp" id="current">UI elements</a>
                <ul>
                    <li><a href="ui.html">General elements</a></li>
                    <li><a href="ui_icons.html">Icons</a></li>
                    <li><a href="ui_buttons.html">Button sets</a></li>
                    <li><a href="ui_grid.html" class="active">Grid</a></li>
                    <li><a href="ui_custom.html">Custom elements</a></li>
                </ul>
            </li>
            <li><a href="forms.html" title="" class="exp">Forms stuff</a>
                <ul>
                    <li><a href="forms.html">Inputs &amp; elements</a></li>
                    <li><a href="form_validation.html">Validation</a></li>
                    <li><a href="form_editor.html">File uploads &amp; editor</a></li>
                    <li><a href="form_wizards.html">Form wizards</a></li>
                </ul>
            </li>
            <li><a href="messages.html" title="">Messages</a></li>
            <li><a href="statistics.html" title="">Statistics</a></li>
            <li><a href="tables.html" title="" class="exp">Tables</a>
                <ul>
                    <li><a href="tables.html">Standard tables</a></li>
                    <li><a href="tables_dynamic.html">Dynamic tables</a></li>
                    <li><a href="tables_control.html">Tables with control</a></li>
                    <li><a href="tables_sortable.html">Sortable &amp; resizable</a></li>
                </ul>
            </li>
            <li><a href="other_calendar.html" title="" class="exp">Other pages</a>
                <ul>
                    <li><a href="other_calendar.html">Calendar</a></li>
                    <li><a href="other_gallery.html">Images gallery</a></li>
                    <li><a href="other_file_manager.html">File manager</a></li>
                    <li><a href="other_404.html">Sample error page</a></li>
                    <li><a href="other_typography.html">Typography</a></li>
                </ul>
            </li>
        </ul>
    </div>   
  
</div>
<!-- Top line ends -->
<script>
var note=0;
var messageId = new Array();
var notificationlength=0;
var notificationContent="";
var newDiv="";
$( document ).ready(function() {
	
	notificationContent="<ul>";
	$.ajax({
		type : "POST",
		url : "./inbox/unread",
		async: false,
		dataType : "JSON",
		success : function(response) 
		{
           	notificationContent +="<label class='messageHeader'>You Have "+response.length+" New Notifications</label>";
			for (var i = 0, len = response.length; i < len; ++i) {
                var results = response[i];                
            	messageId[i]=results.msg_id;
            	var url;
            	if( results.notificationtype=="Job Cards"){
            		url="./jobcarddetails/openjobcards?id="+results.msg_id;
            	}else{
            		url="./inbox";
            	}
            	notificationContent +="<li class='messageContent'><p><a href="+url+">" + results.subject +"</a><p></li>";
			}
			
            newDiv = $("<span style='font-size:15px;color:#FF0000 '>" + response.length +"</span>");
            notificationlength=response.length;	
            
            notificationContent+="<label class='messageFooter'>See All Notifications</label><a href='./inbox'>>>>></a></ul>";
		}	
	});
	if(notificationlength>0){
		$(".tips").html(newDiv).show();		
	    $("#notiMsgSpan1").html(newDiv);
		$(".alert-box.notice").html(notificationContent);	
	}else{
		$("#not").show();
	}
    
});

$(document).ajaxError( function(event, request, settings, exception) {
    if(String.prototype.indexOf.call(request.responseText, "j_username") != -1) {
        window.location.reload(document.URL);
    }
});

$('.tips').toggle(function() {
	$("#notification").show();
	
}, function() {
	$(".tips").hide();
});

$('#not').toggle(function() {
	
	if(notificationlength<=0){
		$(".alert-box.notice").html("You Dont Have new Notifications <a href='./inbox'>>>>></a>");
		$("#notification").show();
		
		
	}else{
		$(".tips").html(newDiv).show();		
	    $("#notiMsgSpan1").html(newDiv);
		$(".alert-box.notice").html(notificationContent);	
		$("#notification").show();
	}
}, function() {	
	$("#notification").hide();	
});








/* ===================================================  Start ======================================================================= */
var manPowerId = new Array();
var desigId = new Array();

var notificationlength1=0;
var notificationContent1="";
var newDiv1="";
$( document ).ready(function() {
	
	notificationContent1="<ul>";
	
	$.ajax({
		type : "POST",
		url : "./manpower/notification/unread",
		async: false,
		dataType : "JSON",
		success : function(response) 
		{
           	notificationContent1 +="<label class='messageHeader' style='font-size:12px;color:#F00000 '>You Have "+response.length+" New Notification(s) </label>";
			for (var i = 0, len = response.length; i < len; ++i) {
                var results = response[i];                
                manPowerId[i]=results.manPowerId;
                desigId[i]=results.desigId;
            	var url;
            	notificationContent1+="<li class='messageContent' style='font-size:12px;color:#000000' onclick=manpowerUpdateStatus("+i+")><u>ManPower Approval Task</u><p><a href='./userApproval' style='font-size:12px;color:#606060'>Requested By:&nbsp;"  +results.requestedBy+"<br>Requested On:&nbsp;"+results.requestedDate+"<p></a></li>";
			
                newDiv1 = $("<span style='font-size:15px;color:#FF0000 '>"+response.length+"</span>");
                notificationlength1=response.length;	
            
            
		}	
			  	
		}
	});
	if(notificationlength1>0){
		$("#notiMsgSpan").html(newDiv1);
		$(".alert-box.notice").html(notificationContent1);	
	}else{
		$("#notify").show();
		
	}
    
});





/* $('.tips').toggle(function() {
	$("#notification").show();
}, function() {
	$(".tips").hide();
}); */

$('#notify').toggle(function() {
	if(notificationlength1<=0){
		$(".alert-box.notice").html("You don't Have new Notifications");
		$("#notification").show();
		
		
	}else{
		$(".tips").html(newDiv1).show();		
	    $("#notiMsgSpan").html(newDiv1);
		$(".alert-box.notice").html(notificationContent1);	
		$("#notification").show();
		
	}
}, function() {	
	$("#notification").hide();	
});


function manpowerUpdateStatus(i)
{
	/* model.addAttribute("reqIds12",reqId[i]); */
	$.ajax({
		type : "POST",
		url : "./manPowerNotification/updateStatus/"+manPowerId[i]+ "/" + desigId[i],
		async: false,
		dataType : "text",
		success:function(response){
	  			
				window.location.reload(true);
			 }
});
	
} 

/* ===================================================  End  ======================================================================= */



function securityCheckForActions(url)
{
	$.ajax({
		type : "POST",
		url : url,
		success : function(response) 
		{
		},			
		error: function(jqXHR, textStatus, errorThrown) {
		   
		    if(jqXHR.status=="403")
		    {
		    	window.location.href = "./accessDenied";
		    }
		}
	});

}
function securityCheckForActionsForStatus(url)
{
	var result="";
	$.ajax({
		type : "POST",
		url : url,
		async: false,
		datatype:'text',
		success : function(response) 	{			
			result = "success";			
		},			
		error: function(jqXHR, textStatus, errorThrown) {	 
		    
		    if(jqXHR.status=="403")
		    {
		    	result = "false";	
		    	window.location.href = "./accessDenied";
		    }
		}		
	});	
	return result;
	
}

</script>

<style>
	
	 
	.messageHeader{
		border-bottom: solid 1px white;	
	}
	.messageContent{
		
		border-bottom: solid 1px white;		
		color:blue;
		font-size: 15px;	
		font-weight: bold;
	}
	.tips{			
		margin:12px;
		top:-8px;
		position:relative;
		font-family:Tahoma,Geneva,Arial,sans-serif;
		font-size: 15px;	
		background : -moz-linear-gradient(center top , #da8854 0%, #c35a32 100%) repeat scroll 0 0 rgba(0, 0, 0, 

0);
	    border-radius: 2px;
	    color: white;	    
	    height: 16px;
    	line-height: 17px;
    	padding: 0 5px;
   		white-space: nowrap;
   		display:none;
	}
   .alert-box {
		/* color:#555; */ 
		border-radius:10px;
		font-family:Tahoma,Geneva,Arial,sans-serif;	
		color:black;	
		padding:10px 36px;
		margin:10px;
		max-width:180px;
		box-shadow: 0px 4px 5px #AAAAAA;	
		display:block;	
		max-height: 150px;
		overflow: auto;
		overflow-style:panner;
		background-color: transparent;
		
			
	}
	.alert-box span {
		display:block;		
		text-transform:uppercase;
		text-color:#403C3C;
		
	}
	.notice {
    /* background:#403C3C url('./resources/notification.png') no-repeat 10px 50%;*/	
	 border:1px solid green;
	 text-color:#403C3C;
	 
	}

</style>
