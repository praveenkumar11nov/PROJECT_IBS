<%@include file="/common/taglibs.jsp"%>


<c:url value="/resources/kendo/shared/icons/sports/mails.png"
	var="baseball" />
<c:url value="/resources/kendo/shared/icons/sports/sent.png" var="golf" />
<c:url value="/resources/kendo/shared/icons/sports/draft.png"
	var="swimming" />
<c:url value="/resources/kendo/shared/icons/sports/trash.png"
	var="snowboarding" />

<c:url value="/messages/composeMessages" var="createUrl" />
<c:url value="/messages/readMessages" var="readUrl" />

<c:url value="/messages/inboxMessages" var="readInboxMailUrl" />
<c:url value="/messages/updateReadStatus" var="viewMailUrl" />
<c:url value="/messages/deleteInboxMessage" var="deleteInboxUrl" />

<c:url value="/messages/saveMessages" var="saveMessagesUrl" />

<c:url value="/messages/getUserName" var="getUserName" />
<c:url value="/messages/getSelectionValue" var="getSelectionValueUrl" />

<c:url value="/messages/getFromNameList" var="getFromNameListUrl" />

<script>
var id;
var inbox_composeDialog;
var toRequired;
var ccRequired;
</script>

<div id="wrapper" class="wrapper">
	<!-- left div -->
	<div id="leftcolumn">
		<!-- <div class="wrapper">
		<ul id="msgLink">
		<li>
			<input type="button" id="compose" value="COMPOSE"
				onclick="composeMeth()"></li>
				</ul>
		</div> -->
		<div class="navContainer">
			<ul id="msgLink">
			<li>
			<input type="button" id="compose" value="COMPOSE"
				onclick="composeMeth()"></li>
				<li class="navText"><a href="./inbox" id="inboxId" title=""
					class="notthis"><img title="Forward this message" alt="" src="./resources/images/icons/usual/icon-inbox.png" width="20px"><font
						color="black">Inbox</font></a></li>
				<li class="navText"><a href="./sent" id="sentId" title=""
					class="notthis"><img title="Forward this message" alt="" src="./resources/kendo/shared/icons/sports/sent.png" width="20px"><font
						color="black">Sent</font></a></li>
				<li class="navText"><a href="./drafts" id="draftId" title=""
					class="notthis"><img title="Forward this message" alt="" src="./resources/kendo/shared/icons/sports/draft.png" width="20px"><font
						color="black">Drafts</font></a></li>
				<li class="navText"><a href="./trash" id="trashId" title=""
					class="notthis"><img title="Forward this message" alt="" src="./resources/kendo/shared/icons/sports/trash.png" width="20px"><font
						color="black">Trash</font></a></li>
			</ul>
		</div>
		<div id="inbox-compose-dialog" title="New Message" hidden="true">
			<form>
				<fieldset>
					<label></label>
					<div class="">
						<h6>To :</h6>
						 <select
							id="inbox-composeTo" multiple="multiple">
						</select>
						<a onclick="openCc()" id="ccAnchor">CC</a></div>
					<div id="ccDiv" hidden="true">
						<a onclick="closePopNew()" id="raj">CC :</a>
						 <select
							id="inbox-composeCc" multiple="multiple">
							<!-- data-placeholder="Select" -->
						</select>
						
						
					</div>
					<!-- <input type="text" name="email" id="email" value="" class="text ui-widget-content ui-corner-all"> -->
					<div class="">
						<h6>Subject :</h6>
						<input type="text"
						name="composeSubject" id="composeSubject"
						class="text ui-widget-content ui-corner-all"> 
					</div>
					<div class="">
						<h6>Messages :</h6>
						<textarea id="composeTxtArea" rows="20" cols="0"
						name="composeTxtArea"></textarea>
					</div>
				</fieldset>
			</form>
		</div>
	</div>
	<div id="alertsBox" title="Alert"></div>
</div>

<style>
#showSelection {
	size: 10, width:5px, height:10px, border:2px
}
.unreadMsgColor {
	 font-size:15px; 
	 font-weight:bold;
}
.readMsgColor {
	/* background-color:#E8E8E8 ; */
	 font-size:15px;
}
</style>
<script>

function closePopNew()
{

	$("#ccDiv").hide();
	$("#ccAnchor").show();
	
 
}


 function openCc(){
	 
	 $("#ccDiv").show();
	 $("#ccAnchor").hide();
	 
 }
	$(document).ready(function() {
		
		// create MultiSelect from select HTML element
		/*  var required = $("#required").kendoMultiSelect().data("kendoMultiSelect"); */
		 toRequired = $("#inbox-composeTo").kendoMultiSelect({
			dataTextField : "loginnName",
			dataValueField : "loginnName",
			dataSource : {
				transport : {
					read : {
						url : "./messages/getAllLoginNames",
					}
				}
			}

		}).data("kendoMultiSelect");
		
		 ccRequired = $("#inbox-composeCc").kendoMultiSelect({
			dataTextField : "loginnName",
			dataValueField : "loginnName",
			dataSource : {
				transport : {
					read : {
						url : "./messages/getAllLoginNames",
					}
				}
			}

		}).data("kendoMultiSelect");
		
	});

	function strip(html) {
		var tmp = document.createElement("DIV");
		tmp.innerHTML = html;
		return tmp.textContent || tmp.innerText;
	}
/* 
	$("ul").on('click', 'a', function() { 
		//Here this will point to the li element being clicked
		//$(this.id).css("color", "#FF3333");
		// alert();
		*/

	inbox_composeDialog = $("#inbox-compose-dialog")
	.dialog(
	{
		autoOpen : false,
		height : 500,
		width : 750,
		modal : true,
		buttons : {
		"Send" : function() {
			var cc = ccRequired.value();
			var names = toRequired.value();
			var sub = $("#composeSubject").val();
			var msg = $("#composeTxtArea").val();
			if( msg == "" )
				msg = "null";
			else{
			var fstStripMsg = strip(msg);
			var secondStripMsg = strip(fstStripMsg);
			msg = secondStripMsg;
			}
			if( cc == "" )
			cc = "null";
			if( names == "" || names == null ) {
				$("#alertsBox").html("");
				$("#alertsBox")
						.html(
								"Error: To address is required");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
			} else if (sub == "") {
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Error: Subject is required");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
			} else {
				
				$.ajax({
							type : "POST",
							dataType:"text",
							url : "./messages/composeNewMsg/"+names+"/"+cc+"/"+sub+"/"+msg,
							/*  data : {
									sub : sub,
									msg : secondStripMsg
								}, */ 
							success : function(response) {
								if (response == "SUCCESS") {
									$("#alertsBox")
											.html("");
									$("#alertsBox")
											.html(
													"Message  successfully sent ");
									$("#alertsBox")
											.dialog(
													{
														modal : true,
														buttons : {
															"Close" : function() {
																$(
																		this)
																		.dialog(
																				"close");
															}
														}
													});}
								if (response == "ERROR") {
									$("#alertsBox")
											.html("");
									$("#alertsBox")
											.html(
													"Error: While sending this message");
									$("#alertsBox")
											.dialog(
													{
														modal : true,
														buttons : {
															"Close" : function() {
																$(
																		this)
																		.dialog(
																				"close");
															}
														}
													});}
								toRequired.value("");
								ccRequired.value("");
								$("#composeSubject").val("");
								$("#composeTxtArea").val("");
								//$(".k-editable-area").text("");

								/*  var grid = $("#inboxGrid").data("kendoGrid");
								grid.cancelRow();
								grid.dataSource.read();
								grid.refresh();  */
							}

						});

				$(this).dialog("close");
			}
		},
		"Save to draft" : function() {
			var cc = ccRequired.value();
			var names = toRequired.value();
			var sub = $("#composeSubject").val();
			var msg = $("#composeTxtArea").val();
			if( msg == "" )
				msg = "null";
			else{
			var fstStripMsg = strip(msg);
			var secondStripMsg = strip(fstStripMsg);
			msg = secondStripMsg;}
			if( names == "" || names == null )
				names = "null";
			if( cc == "" )
				cc = "null";
			if( sub == "" )
				sub = "null";
			
			$.ajax({
						type : "POST",
						dataType:"text",
						/* data : {
							name : toValues,
							cc : ccValues,
							sub : sub,
							msg : secondStripMsg
						}, */
						url : "./messages/saveToDraft/"+names+"/"+cc+"/"+sub+"/"+msg,
						success : function(response) {
							if (response == "DRAFTSUCCESS") {
								$("#alertsBox")
										.html("");
								$("#alertsBox")
										.html(
												"This message is successfully saved in draft");
								$("#alertsBox")
										.dialog(
												{
													modal : true,
													buttons : {
														"Close" : function() {
															$(this).dialog("close");
														}
													}
												});
								/* var grid = $("#inboxGrid").data("kendoGrid");
								grid.cancelRow();
								grid.dataSource.read();
								grid.refresh(); */
								}
							if (response == "DRAFTERROR") {
								$("#alertsBox")
										.html("");
								$("#alertsBox")
										.html(
												"Error: While saving this message in draft");
								$("#alertsBox")
										.dialog(
												{
													modal : true,
													buttons : {
														"Close" : function() {
															$(
																	this)
																	.dialog(
																			"close");
														}
													}
												});

							}

							/* var grid = $("#inboxGrid").data("kendoGrid");
							grid.cancelRow();
							grid.dataSource.read();
							grid.refresh(); */
						}
					});
			toRequired.value("");
			ccRequired.value("");
			$("#composeSubject").val("");
			$("#composeTxtArea").val("");
			$(this).dialog("close");
		},
		Cancel : function() {
			toRequired.value("");
			ccRequired.value("");
			$("#composeSubject").val("");
			$("#composeTxtArea").val("");
			$(this).dialog("close");
		}
	},

	});
	function composeMeth() {
		toRequired.value("");
		ccRequired.value("");
		$("#composeSubject").val("");
		$("#composeTxtArea").val("");

	inbox_composeDialog.dialog("open");

}							
</script>
	
<style>
.wrapper {
	display: inline;
}
#compose{ 
FONT-FAMILY: Verdana, Arial, Helvetica, sans-serif;
	BACKGROUND-COLOR:gray;
	border-style:solid;
	FONT-WEIGHT: bold; 
    color: buttonhighlight;
	border-width:1px;
	font-size:12px;
	color:#FFFFFF;
	height:20px;"



}
 #wrapper {
     width: 1300px;
     margin: 0 auto;
} 
#leftcolumn {
    border: 1px solid white;
    float: left;
    min-height: 450px;
    color: white;
}
#leftcolumn {
     width: 100px;
     background-color: light gray;
}
#ccAnchor{
   /*  text-decoration: underline; */
    border-bottom:1px solid blue;
}
#raj{
   /*  text-decoration: underline; */
    border-bottom:1px solid blue;
}


</style>