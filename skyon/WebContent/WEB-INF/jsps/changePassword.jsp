<%@include file="/common/taglibs.jsp"%>
 
<br/>
<br/>
<script type="text/javascript" src="<c:url value='/resources/jquery-validate.js'/>"></script>
<div id="alertsBox" title="Alert"></div> 

<c:if test="${not empty status}">

		<div id="forgotPassAlert" title="Alert">
				<b>${status}</b>
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

<c:remove var = "status" scope = "session" />
	
	
<script type="text/javascript">

$(document).ready(function(){
	 $('#oldPassword').focus();
    $('#oldPassword').bind("cut copy paste",function(e) {
        e.preventDefault();
    });
    $('#newPassword').bind("cut copy paste",function(e) {
        e.preventDefault();
    });
    $('#confirmPassword').bind("cut copy paste",function(e) {
        e.preventDefault();
    });
    
    
    if("${countValue}"=="first")
    	{
    	 $('.manu1').hide();
    	 $('.manu2').hide();
    	 $('.manu3').hide();
    	 $('.manu4').hide();
    	 $('.manu5').hide();
    	 $('.manu6').hide();
    	 $('.manu7').hide();
    	 $('.manu8').hide();
    	 $('.manu9').hide();
    	 $('.manu10').hide();
    	 $('.manu11').hide();
    	 $('.manu12').hide();
    	 $('.manu13').hide();
    	 $('.manu14').hide();
    	 $('.manu15').hide();
    	 $('.manu16').hide();
    	 $('.manu17').hide();
    	 $('.manu18').hide();
     	
    	}
  });  

	function focusOnFields(e) {
		keyPressed = e.keyCode;
		if (keyPressed == '13' || e == '13') {
			$("#myform").validate();
		}

	}
 
	function showMessage() {
		$(".messageArea").show();
	}
	function hideMessage() {
		$(".messageArea").hide();
	}
	
	$("#myform").validate({
        rules: {
            "oldPassword": {
                required: true,
            },  
            "newPassword": {
				required: true,
				minlength: 5
			},
			"confirmPassword": {
				required: true,
				minlength: 5,
				equalTo: "#newPassword"
			},
			
        },
        messages: {			
			oldPassword :{
				required : "Old Password is Required"
			},
			newPassword :{
				required : "New Password is Required"
			},
			confirmPassword :{
				required : "Confirm Password is Required"
			},			
		},
        //perform an AJAX post to ajax.php
     submitHandler: function() {
            $.post('./updatePassword', 
            $('form#myform').serialize() , 
            function(data){
            	alert(data);
            	
                $("#alertsBox").html("");
				$("#alertsBox").html(data);
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
			  $('#myform')[0].reset();        
              
            }, "text");
        } 
    });
	 
</script>
	<div style="width: 500px;">

		<form action="<c:url value="./updatePassword"/>" autocomplete="off" style="margin-top: -60px;" id="myform" method="post">

			<div class="formRow">
				<label>Enter Your Old Password:</label> <input type="password"
					name="oldPassword" id="oldPassword" placeholder="Old Password"
					class="validate[required]"  onkeyup="focusOnFields(event)"
					onkeypress="hideMessage();" required="required" /> <br/> <label>Enter
					Your New Password:</label> <input type="password" name="newPassword"
					id="newPassword" placeholder=" New Password" required="required"
					onkeyup="focusOnFields(event)" onkeypress="showMessage();" /><span class="messageArea"
					style="display: none; color: green; margin: 3px;">New
					Password Should be Different From Old Password</span><br/> <label>Confirm
					New Password:</label> <input type="password" id="confirmPassword"
					name="confirmPassword" placeholder="Confirm Password" required="required"
					onkeyup="focusOnFields(event)" />

				<div class="logControl">
					<input type="submit"  class="buttonS bLightBlue" value="Change Password" />
				</div>

			</div>
		</form>
	</div>	