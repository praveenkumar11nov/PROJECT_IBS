<%@include file="/common/taglibs.jsp"%>

<c:url value="/myprofile/getUserLogDetails" var="readUrl" />	

	<div class="fluid">
		<div class="widget grid6">
			<div class="whead">

			<h6>Profile Details</h6>
			<!-- <div class="titleOpt">
			<a href="#" data-toggle="dropdown" id="create-user"><span
			class="icos-pencil"></span></a>

			</div> -->
			</div>
			<div class="body">
			<div class="gallery">
			<ul>
			<li style="width: 250px; height: 220px;"><a
			href="images/big.png" title="" class="lightbox"><img
			src="<c:url value='/ldap/getuserimage/'/>" alt="" width="250px;" height="220px;" /></a>
			<div class="actions" style="width: 250px; height: 220px; vertical-align: middle;">
			<a href="#" title="" class="edit" id="uploadDailogbtn"><img src="resources/images/icons/update.png" alt="" /></a>
			</div></li>

			</ul>

			</div>

			<div id="options">


			<table>
					
			<tr>
			<td>&nbsp;</td>
			</tr>
					
			<tr>
			<td>
			<h2 style="text-transform: capitalize;">${LoginName}</h2>
			</td>

			</tr>
			<tr>
			<td><b style="font-size: 15px;">${DesignationName},${DepartmentName}</b>
			</td>
			</tr>

			<tr>
			<td><b style="font-size: 15px;">${emailId}</b></td>
			</tr>
			<tr>
			<td align="left"><b style="font-size: 15px;">${contactcontent}</b></td>
			</tr>
			<tr>
			<td></td>

			</tr>
			<tr>
			<td>&nbsp;</td>
			</tr>
  	                                </table>
			<a class="buttonM bBlue" href="./changePassword"><span class="icol-user"></span><span>Change Password</span></a>
				</div>
			</div>

		</div>



		<div class="widget grid6">
			<div class="whead">

				<h6>Login History of ${LoginName}</h6>

			</div>

			<div class="whead"  >

				<kendo:grid name="grid" pageable="true" scrollable="true" resizable="true"
					height="448px" width="200px">
					

					<kendo:grid-columns>
						<kendo:grid-column title="Session Start" field="ulSessionStart" width="100px" />
						<kendo:grid-column title="Session End" field="ulSessionEnd" width="100px"/>
						<kendo:grid-column title="Logout Method" width="100px" field="logoutMethod" />
						<kendo:grid-column title="System IP" width="100px" field="systemIp" />
						<kendo:grid-column title="Duration (HH:MM:SS)" width="100px" field="duration" />
					</kendo:grid-columns>
					<kendo:dataSource pageSize="14">
						<kendo:dataSource-transport>

							<kendo:dataSource-transport-read url="${readUrl}" dataType="json"
								type="POST" contentType="application/json" />

							<kendo:dataSource-transport-parameterMap>
								<script>
									function parameterMap(options, type) {
										/* var msg=${msg};
										 alert(msg); */
										return JSON.stringify(options);
									}
								</script>
							</kendo:dataSource-transport-parameterMap>
						</kendo:dataSource-transport>
						<kendo:dataSource-schema>
							<kendo:dataSource-schema-model>
								<kendo:dataSource-schema-model-fields>
									<kendo:dataSource-schema-model-field name="ulSessionStart"/>
									<kendo:dataSource-schema-model-field name="systemIp" />
									<kendo:dataSource-schema-model-field name="duration" />
									
									<kendo:dataSource-schema-model-field name="ulSessionEnd"
										type="string">

										<kendo:dataSource-schema-model-field-validation
											required="true" />
									</kendo:dataSource-schema-model-field>
									<kendo:dataSource-schema-model-field name="logoutMethod"
										type="string">
										<kendo:dataSource-schema-model-field-validation
											required="true" />
									</kendo:dataSource-schema-model-field>
								</kendo:dataSource-schema-model-fields>
							</kendo:dataSource-schema-model>
						</kendo:dataSource-schema>
					</kendo:dataSource>
				</kendo:grid>

			</div>


		</div>
	</div>

	<div class="fluid">
		<div class="widget grid6">
			<div class="whead">
				<h6>Member of Roles</h6>
			</div>
			<div class="body">
				<div class="tagsinput" style="background: none; border: none;"
					id="tags_tagsinput" style="width: 100%;">
					<c:forEach var="roleName" items="${roles}"
						varStatus="loopCounter">
						<%-- <c:out value="count: ${loopCounter.count}"/> --%>
						<span class="tag"><span><c:out value="${roleName}" />&nbsp;&nbsp;</span><a
							href="#" title="Removing tag"></a></span>
					</c:forEach>
				</div>
			</div>
		</div>

		<div class="widget grid6">
			<div class="whead">
				<h6>Member of Groups</h6>
			</div>
			<div class="body">
				<div class="tagsinput" style="background: none; border: none;"
					id="tags_tagsinput" style="width: 100%;">
					<c:forEach var="groupName" items="${groups}"
						varStatus="loopCounter">
						<%-- <c:out value="count: ${loopCounter.count}"/> --%>
						<span class="tag"><span><c:out value="${groupName}" />&nbsp;&nbsp;</span><a
							href="#" title="Removing tag"></a></span>

					</c:forEach>
				</div>

			</div>
		</div>
	</div>




	<div id="uploadDialog" title="Upload Image" style="display: none;">
		<form id="form1" method="post" action="./image/upload"
			enctype="multipart/form-data">

			<!-- File input -->
			<input name="file" id="file" type="file" accept="image/x-png, image/gif,image/jpg, image/jpeg"/><br /> <br /> <input
				type="submit" id="checkImage"  value="Upload" class="k-button" />
		</form>
	</div>


	<div id="dialog-form" title="Edit Profile Details"
		style="display: none; ">
		<p class="validateTips">All form fields are required.</p>
		<form>
		<table>
		<tr>
		<td id="mobilediv">
		<input type="checkbox" id="checkboxmobile" onclick="showMobileText()"/>mobile&nbsp;&nbsp;&nbsp;&nbsp;</td>
		
		
		<td id="emaildiv">
		<input type="checkbox" id="checkboxemail" onclick="showEmailText()" />Email</td></tr></table><br>
			<fieldset id="fieldsetEmail" >
				<label for="email">Email</label> <input type="text" name="email" 
					id="email" value="${emailId}"
					class="text ui-widget-content ui-corner-all">
					</fieldset>
					
					<fieldset id="fieldsetMobile">
					 <label
					for="Phone">Phone</label> <input type="text" name="mobileno"
					id="mobileno" value="${mobileNo}"
					class="text ui-widget-content ui-corner-all">
			</fieldset>

		</form>

	</div>
	
		    <div class="widget grid6">
				<div class="whead">
					<h6>Select your theme</h6>
				</div>
   
	            <div id="themeChooser-container">
						<ul class="tc-theme-container" id="themeSelection" style="display: block;">
							<li class="tc-theme">
								<a data-value="default" onclick="changeGridThemes(this.id)" id="default"
								class="tc-link" href="#"><span
									style="background-color: rgb(239, 111, 28);" class="tc-color"></span><span
									style="background-color: #e24b17" class="tc-color"></span><span
									style="background-color: #5a4b43" class="tc-color"></span><span
									style="background-color: #ededed" class="tc-color"></span><span
									class="tc-theme-name">Default</span></a></li>
							<li class="tc-theme">
								<a data-value="blueopal" onclick="changeGridThemes(this.id)" id="blueopal"
								class="tc-link" href="#"><span
									style="background-color: #076186" class="tc-color"></span><span
									style="background-color: #7ed3f6" class="tc-color"></span><span
									style="background-color: #94c0d2" class="tc-color"></span><span
									style="background-color: #daecf4" class="tc-color"></span><span
									class="tc-theme-name">Blue Opal</span></a></li>
							<li class="tc-theme"><a data-value="bootstrap" onclick="changeGridThemes(this.id)" id="bootstrap"
								class="tc-link" href="#"><span
									style="background-color: #3276b1" class="tc-color"></span><span
									style="background-color: #67afe9" class="tc-color"></span><span
									style="background-color: #ebebeb" class="tc-color"></span><span
									style="background-color: #ffffff" class="tc-color"></span><span
									class="tc-theme-name">Bootstrap</span></a></li>
							<li class="tc-theme"><a data-value="silver" onclick="changeGridThemes(this.id)" id="silver"
								class="tc-link active" href="#"><span
									style="background-color: #298bc8" class="tc-color"></span><span
									style="background-color: #515967" class="tc-color"></span><span
									style="background-color: #bfc6d0" class="tc-color"></span><span
									style="background-color: #eaeaec" class="tc-color"></span><span
									class="tc-theme-name">Silver</span></a></li>
							<li class="tc-theme"><a data-value="uniform" onclick="changeGridThemes(this.id)" id="uniform"
								class="tc-link" href="#"><span
									style="background-color: #666666" class="tc-color"></span><span
									style="background-color: #cccccc" class="tc-color"></span><span
									style="background-color: #e7e7e7" class="tc-color"></span><span
									style="background-color: #ffffff" class="tc-color"></span><span
									class="tc-theme-name">Uniform</span></a></li>
							<li class="tc-theme"><a data-value="metro" class="tc-link" onclick="changeGridThemes(this.id)" id="metro"
								href="#"><span style="background-color: #8ebc00"
									class="tc-color"></span><span
									style="background-color: #787878" class="tc-color"></span><span
									style="background-color: #e5e5e5" class="tc-color"></span><span
									style="background-color: #ffffff" class="tc-color"></span><span
									class="tc-theme-name">Metro</span></a></li>
							<li class="tc-theme"><a data-value="black" class="tc-link" onclick="changeGridThemes(this.id)" id="black"
								href="#"><span style="background-color: #0167cc"
									class="tc-color"></span><span
									style="background-color: #4698e9" class="tc-color"></span><span
									style="background-color: #272727" class="tc-color"></span><span
									style="background-color: #000000" class="tc-color"></span><span
									class="tc-theme-name">Black</span></a></li> 
							<li class="tc-theme"><a data-value="metroblack" onclick="changeGridThemes(this.id)" id="metroblack"
								class="tc-link" href="#"><span
									style="background-color: #00aba9" class="tc-color"></span><span
									style="background-color: #0e0e0e" class="tc-color"></span><span
									style="background-color: #333333" class="tc-color"></span><span
									style="background-color: #565656" class="tc-color"></span><span
									class="tc-theme-name">Metro Black</span></a></li>
							<li class="tc-theme"><a data-value="highcontrast" onclick="changeGridThemes(this.id)" id="highcontrast"
								class="tc-link" href="#"><span
									style="background-color: #b11e9c" class="tc-color"></span><span
									style="background-color: #880275" class="tc-color"></span><span
									style="background-color: #664e62" class="tc-color"></span><span
									style="background-color: #1b141a" class="tc-color"></span><span
									class="tc-theme-name">High Contrast</span></a></li>
							<li class="tc-theme"><a data-value="moonlight" onclick="changeGridThemes(this.id)" id="moonlight"
								class="tc-link" href="#"><span
									style="background-color: #ee9f05" class="tc-color"></span><span
									style="background-color: #40444f" class="tc-color"></span><span
									style="background-color: #2f3640" class="tc-color"></span><span
									style="background-color: #212a33" class="tc-color"></span><span
									class="tc-theme-name">Moonlight</span></a></li>
							<li class="tc-theme"><a data-value="flat" class="tc-link" onclick="changeGridThemes(this.id)" id="flat"
								href="#"><span style="background-color: #363940"
									class="tc-color"></span><span
									style="background-color: #2eb3a6" class="tc-color"></span><span
									style="background-color: #10c4b2" class="tc-color"></span><span
									style="background-color: #ffffff" class="tc-color"></span><span
									class="tc-theme-name">Flat</span></a></li>
						</ul>
				</div>
			</div>


<style>
.tc-color {
    border-color: rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(0, 0, 0, 0.1) rgba(255, 255, 255, 0.1);
    border-style: solid;
    border-width: 1px;
    display: inline-block;
    height: 23px;
    width: 23px;
}
.tc-theme .tc-link {
    border: 1px solid #FFFFFF;
    color: #4F4F4F;
    padding: 10px;
}

.k-theme-chooser, .tc-theme, .tc-color, .tc-link, .tc-theme-name {
    display: inline-block;
    text-decoration: none;
}
.tc-theme-name {
    display: block;
    padding: 0;
    width: auto;
}
</style>


	<script type="text/javascript">
	
	$('#checkImage').click(function(){
		
		var filename=$('#file').val();
		
		
		
	
		if(filename.lastIndexOf("png")==filename.length-3){
			//alert("png");
			return true;
		}
		if(filename.lastIndexOf("jpg")==filename.length-3){
			//alert("jpg");
			return true;
		}
		if(filename.lastIndexOf("gif")==filename.length-3){
			//alert("jpg");
			return true;
		}
		if(filename.lastIndexOf("jpeg")==filename.length-4){
			//alert("jpeg");
			return true;
		}else{
			alert("please select file");
			return false;
		}
		
	});
	
	
	$('INPUT[type="file"]').change(function () {
	    var ext = this.value.match(/\.(.+)$/)[1];
	    switch (ext) {
	        case 'jpg':
	        case 'jpeg':
	        case 'png':
	        case 'gif':
	            $('#uploadButton').attr('disabled', false);
	            break;
	        default:
	            alert('Only Files with Extension (.jpg/.png/.gif/.jpeg) Are Allowed');
	            this.value = '';
	    }
	});


	


function showMobileText(){

if(checkboxmobile.checked)
{

	$("#emaildiv").hide();
	//$("#email").val("");
	$("#fieldsetMobile").show();




	
	}
else{
	$("#fieldsetMobile").hide();
	//$("#email").val("");
	$("#emaildiv").show();
}
	
}

function showEmailText(){

if(checkboxemail.checked){
	$("#fieldsetEmail").show();
	$("#mobilediv").hide();
	//$("#mobileno").val("");	
}
else{
	$("#fieldsetEmail").hide();
	$("#mobilediv").show();
	//$("#mobileno").val("");
}

	
}

$(document).ready(function(){

$("#fieldsetEmail").hide();
$("#fieldsetMobile").hide();




});



	
		
		$(".gallery ul li").hover(function() {
			$(this).children(".actions").show("fade", 200);
		}, function() {
			$(this).children(".actions").hide("fade", 200);
		});

		$(function() {
			var mobileno = $("#mobileno"), email = $("#email"), allFields = $(
					[]).add(mobileno).add(email), tips = $(".validateTips");
			function updateTips(t) {
				tips.text(t).addClass("ui-state-highlight");
				setTimeout(function() {
					tips.removeClass("ui-state-highlight", 1500);
				}, 500);
			}
			function checkLength(o, n, min, max) {
				if (o.val().length > max || o.val().length < min) {
					o.addClass("ui-state-error");
					updateTips("Length of " + n + " must be between " + min
							+ " and " + max + ".");
					return false;
				} else {
					return true;
				}
			}

			function checkLengthEmail(o, n, min, max) {
				if (o.val().length > max || o.val().length < min) {
					o.addClass("ui-state-error");
					updateTips("Length of " + n + " must be between " + min
							+ " and " + max + "." +"and must use the format like :- 'abc@gmail.com'");
					return false;
				} else {
					return true;
				}
			}
			function checkRegexp(o, regexp, n) {
				if (!(regexp.test(o.val()))) {
					o.addClass("ui-state-error");
					updateTips(n);
					return false;
				} else {
					return true;
				}
			} 
			$("#dialog-form")
					.dialog(
							{
								autoOpen : false,
								height : 300,
								width : 350,
								modal : true,
								buttons : {
									"Save" : function() {
										 var bValid = true;
										var bValidemail=true;
										allFields.removeClass("ui-state-error");

										   bValidemail = bValidemail
												&& checkLengthEmail(email, "email",
														6, 80);   
 
										
										// From jquery.validate.js (by joern), contributed by Scott Gonzalez: http://projects.scottsplayground.com/email_address_validation/
										   bValidemail = bValidemail
												&& checkRegexp(
														email,
														/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i,
														"eg. name@domain.com");  

										   if(checkboxmobile.checked){
  
										if (bValid) {
											bValid = bValid
											&& checkRegexp(mobileno,
													/^\d{1,45}$/g,
													"Phone may consist of only numbers between 0-9.");
                                        alert("in update profile");
											var mail="";
										// var mail = $("#email").val(); 
											var ph = $("#mobileno").val()
										}
										}

										   if(checkboxemail.checked){
											   bValidemail = bValidemail
												&& checkRegexp(
														email,
														/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i,
														"eg. name@domain.com");  
										if(bValidemail){
											var mail = $("#email").val(); 
											var ph = "";

											}
										}
										
													$.ajax({
														type : "POST",
														url : "./myprofile/update",
														data : {
														email : mail,
															mobileno : ph
														},
														success : function(
																response) {
															alert("Saved Successfully");
															RenderLinkInner('myprofile');

														}
													});

											$(this).dialog("close");
										},

										
									
									Cancel : function() {
										$(this).dialog("close");
									},
								},
								
								close : function() {
									allFields.val("").removeClass(
											"ui-state-error");
								}
							});

			$("#create-user").button().click(function() {
				 $("#email").val('${emailId}'); 
				$("#mobileno").val('${mobileNo}');
				$("#dialog-form").dialog("open");
			});
		});
	</script>
	
	<form id="themeForm" action="./changeGridTheme" >
		<input type="text" id="theme" name="theme" hidden="true"/>
	</form>


	<script>
	
		$('#uploadDailogbtn').click(function() {

			$('#uploadDialog').dialog({
				modal : true,
			});
			return false;
		});


		function changeGridThemes(themeName)
		{
			$("#theme").val(themeName);
			$("#themeForm").submit();
		}
	</script>



	<style>
	
	.fluid [class*="grid"] {
    box-sizing: border-box;
    display: block;
    float: left;
    margin-left: 0%;
    position: relative;
   
}
	
div#users-contain {
	width: 350px;
	margin: 20px 0;
}

div#users-contain table {
	margin: 1em 0;
	border-collapse: collapse;
	width: 100%;
}

div#users-contain table td,div#users-contain table th {
	border: 1px solid #eee;
	padding: .6em 10px;
	text-align: left;
}

.ui-dialog .ui-state-error {
	padding: .3em;
}

.validateTips {
	border: 1px solid transparent;
	padding: 0.3em;
}

div.tagsinput span.tag {
	background: grey;
	border: 1px solid black;
	color: white;
	display: block;
	float: left;
	font-size: 11px;
	line-height: 23px;
	margin: 5px;
	padding: 0 8px;
}
</style>