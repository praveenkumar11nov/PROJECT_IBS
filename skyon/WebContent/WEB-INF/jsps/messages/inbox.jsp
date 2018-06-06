<%@include file="/common/taglibs.jsp"%>

<%@include file="messagesLink.jsp"%>

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
var first_array1 = new Array();
var checkedIds = {};
var inbox_viewDialog;
var inbox_forwardDialog;
var inbox_replyDialog;
var ccInboxRequired;
var cc1InboxRequired;
var replytoRequired;
var forwardtoRequired;
</script>

<div id="wrapper" class="wrapper">
    <div id="rightcolumn">
   <!--  <div class="inbox-header">
			<h1 class="pull-left">Inbox</h1>
		</div><br/><br/><br/> -->
			 <div id="selectDropDown" class="wrapper">		
        <kendo:dropDownList name="inboxDropDown" dataTextField="text"
						dataValueField="value" >
						<kendo:dataSource></kendo:dataSource>
					</kendo:dropDownList>
					</div>
					 <div class="wrapper" id="replyDiv" >
				<a href="#" id="replyButton" ><img title="Reply to sender" alt="" src="./resources/images/icons/usual/icon-arrowleft.png" width="20px"></a>
			</div> 
			<div class="wrapper" id="forwardDiv" >
				<a href="#" id="forwardButton"><img title="Forward this message" alt="" src="./resources/images/icons/usual/icon-arrowright.png" width="20px"></a>
			</div> 
					<div id="deleteButton" class="wrapper">
					<a href="#" id="showSelection1" onclick="deleteIds1()"><img title="Delete" alt="" src="./resources/images/deleteIcon.jpg" width="20px"></a>
	</div>	
	<div class="wrapper" id="moveDiv">
   <kendo:dropDownList name="moveToDropDown" dataTextField="text"
						dataValueField="value" optionLabel="MoveTo" >
						<kendo:dataSource></kendo:dataSource>
					</kendo:dropDownList> 
    </div> 
	<div class="wrapper" id="moreButton">
  <kendo:dropDownList name="moreDropDown" dataTextField="text"
						dataValueField="value" optionLabel="More">
						<kendo:dataSource></kendo:dataSource>
					</kendo:dropDownList>
    </div>
   <!--  div for reply popup -->
   	<div id="dialog-form" title="Reply to sender" style="width:6000px;height:6000px" hidden="true">
			<form>
				<fieldset>
					<label></label>
					<div class="">
						<h6>To :</h6>
						 <select
							id="replyTo" multiple="multiple">
						</select>
					<a onclick="inboxOpenReplyCc()" id="inboxReplyCcAnchor">CC</a></div>
					<div id="inboxReplyCcDiv" hidden="true">
						<a onclick="closePop()" id="raj">CC :</a>
						 <select
							id="cc" multiple="multiple">
						</select>
					</div>
					<div class="">
						<h6>Subject :</h6>
						<input type="text"
						name="composeSubject" id="subject"
						class="text ui-widget-content ui-corner-all"> 
					</div>
					<div class="">
						<h6>Messages :</h6>
						<textarea id="txtArea" rows="20" cols="0"
						name="composeTxtArea"></textarea>
					</div>
				</fieldset>
			</form>
		</div>
 	<div id="dialog-form-forward" title="Forward message" hidden="true">
			<form>
				<fieldset>
					<div class="">
						<h6>To :</h6>
						<label for="required">Required</label> <select
							id="forwardTo" multiple="multiple">
						</select>
					<a onclick="inboxOpenForwardCc()" id="inboxForwardCcAnchor">CC</a></div>
					<div id="inboxForwardCcDiv" hidden="true">
					<a onclick="closePop1()" id="raj1">CC :</a>
						 <select
							id="cc1" multiple="multiple">
						</select>
					</div>
					<div class="">
						<h6>Subject :</h6>
						<input type="text"
						name="composeSubject" id="subject1"
						class="text ui-widget-content ui-corner-all"> 
					</div>
					<div class="">
						<h6>Messages :</h6>
						<textarea id="txtArea1" rows="20" cols="0"
						name="composeTxtArea"></textarea>
					</div>
				</fieldset>
			</form>
		</div>
    
<div id="inboxGridView">
<div id="inboxViewDialog" title="View Message"> 
<div id="inbox-subjectField" class="inbox-subjectField"></div><br/>
<div id=inbox-fromUserField class="inbox-fromUserField"></div><br/>
Message: <textarea id="inbox-viewTxtArea" rows="15" cols="0" name="inbox-viewTxtArea" disabled="disabled" ></textarea>

</div>
<kendo:grid name="inboxGrid" height="430px" dataBound="inboxDataBound" pageable="true" sortable="true" filterable="true" groupable="true">
					<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" >
					<kendo:grid-pageable-messages itemsPerPage="Messages per page" empty="No Messages to display" refresh="Refresh all the Inbox Messages" 
			display="{0} - {1} of {2} Messages" first="Go to the first page of Inbox" last="Go to the last page of Inbox" next="Go to the Inbox"
			previous="Go to the previous page of Inbox"/>
					</kendo:grid-pageable>
						<kendo:grid-filterable extra="false">
						<kendo:grid-filterable-operators>
		  	<kendo:grid-filterable-operators-string eq="Is equal to"/>
		 </kendo:grid-filterable-operators>
		</kendo:grid-filterable>
						<kendo:grid-editable mode="popup"
							confirmation="Are you sure you want to move this message to trash?" />
						<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="Clear_Filter" text="Clear Filter"/>
						</kendo:grid-toolbar>
						<kendo:grid-columns>
							<kendo:grid-column title="" field=""
								template="<input type='checkbox' id='checkStatus1_#=data.msg_id#' class='checkbox1' />"
								width="10px" />
							 <kendo:grid-column title="Message Id" field="msg_id" width="70px"
								hidden="true" />
							<kendo:grid-column title="From : " field="fromUser" width="70px">
							<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function NameFilter(element) {
								element.kendoAutoComplete({
									dataSource: {
										transport: {
											read: "${getFromNameListUrl}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	 
	    		</kendo:grid-column>
							<kendo:grid-column title="Subject : " field="subject"
								width="130px" filterable="false" />
							<kendo:grid-column title="Message :" field="message" hidden="true"
								format="{0:A-Z}" width="70px" filterable="false"/>
								<kendo:grid-column hidden="true"/>
				<kendo:grid-column title="Date" field="lastUpdatedDate" format="{0:dd/MM/yyyy hh:mm tt}" width="50px" filterable="false">
				 <%-- <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
    					function fromDateFilter(element) {
							element.kendoDatePicker({
								/* format:"dd/MM/yyyy hh:mm tt", */
								 format:"dd/MM/yyyy", 
				            	
			            });
				  		}
    					
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	 --%>
	    		</kendo:grid-column>
							<kendo:grid-column title="&nbsp;" width="50px">
								<kendo:grid-column-command>
									<kendo:grid-column-commandItem name="viewDetails" text="View Details">
                        <kendo:grid-column-commandItem-click>
                            <script>
                            function showDetails(e) {
                            	
                                 var dataItem = this.dataItem($(e.currentTarget).closest("tr"));
                                 $('div.inbox-subjectField').text( "Subject: "+ dataItem.subject);
                                 $('div.inbox-fromUserField').text( "From: "+ dataItem.fromUser);
                                 id = dataItem.msg_id;
                                 if( dataItem.message == null )
                                	 $("#inbox-viewTxtArea").val("");
                                 else
                                 $("#inbox-viewTxtArea").val(dataItem.message);
                                 //$(".ui-icon-closethick").hide();
                                 var word=dataItem.subject.split(" ");
                                 if(word[0]=="Deny" )
                                	 {
                                	 $(".ui-dialog-buttonset").hide();
                                	 }
                                 else
                                	 {
                                	 $(".ui-dialog-buttonset").show();
                                	 $("#inboxReplyCcAnchor").show();
	 								 $("#inboxForwardCcAnchor").show();
                                	 }
                                  $("#inboxReplyCcDiv").hide();
	 								$("#inboxForwardCcDiv").hide();
	 								 
                                inbox_viewDialog.dialog( "open" );
                                 //$("#inbox_viewDialog").dialog(opt).dialog("open");
                                 $(".ui-icon-closethick").click(function(){
                                	 $.ajax({type:"POST",dataType:"text",url:"./messages/changeReadStatus/"+id }); 
                                	 first_array1 = [];
                                	 checkedIds = {};
    								 var grid = $("#inboxGrid").data("kendoGrid");
    									grid.cancelRow();
    									grid.dataSource.read();
    									grid.refresh();
    									$("#showSelection1").hide();
										$("#moveDiv").hide();
										$("#replyDiv").hide();
										$("#forwardDiv").hide();
										 var data1 = [
											            { text: "More", value: "more" },
								                        { text: "Mark all as read", value: "read" }
								                        
								                    ];
								                  // create DropDownList from input HTML element
								                    $("#moreDropDown").kendoDropDownList({
								                        dataTextField: "text",
								                        dataValueField: "value",
								                        dataSource: data1,
								                        index: 0,
								                        change: onChangeMore
								                       
								                    });       }); }
                            </script>
                        </kendo:grid-column-commandItem-click>
                   </kendo:grid-column-commandItem>
								</kendo:grid-column-command>
							</kendo:grid-column>
						</kendo:grid-columns>

						<kendo:dataSource pageSize="20">
							<kendo:dataSource-transport>
								<kendo:dataSource-transport-read url="${readInboxMailUrl}"
									dataType="json" type="POST" contentType="application/json" />
								<kendo:dataSource-transport-update url="${viewMailUrl}"
									dataType="json" type="POST" contentType="application/json" />
								<kendo:dataSource-transport-destroy url="${deleteInboxUrl}"
									dataType="json" type="POST" contentType="application/json" />
								<kendo:dataSource-transport-parameterMap>
									<script>
										function parameterMap(options, type) {
											return JSON.stringify(options);
										}
									</script>
								</kendo:dataSource-transport-parameterMap>
							</kendo:dataSource-transport>
							<kendo:dataSource-schema parse="parse">
								<kendo:dataSource-schema-model id="msg_id">
									<kendo:dataSource-schema-model-fields>
										<kendo:dataSource-schema-model-field name="msg_id"
											type="number" editable="false" />
										<kendo:dataSource-schema-model-field name="select"
											editable="false" />
										<kendo:dataSource-schema-model-field name="fromUser"
											editable="false" type="string">
											<kendo:dataSource-schema-model-field-validation
												required="true" />
										</kendo:dataSource-schema-model-field>
										<kendo:dataSource-schema-model-field name="subject"
											type="string">
										</kendo:dataSource-schema-model-field>
										<kendo:dataSource-schema-model-field name="message"
											editable="true" type="string">
										</kendo:dataSource-schema-model-field>
										<kendo:dataSource-schema-model-field name="read_status"
											editable="false" type="number" />
											<kendo:dataSource-schema-model-field name="ccField"
											editable="false" />
											<kendo:dataSource-schema-model-field name="lastUpdatedDate" type="date" defaultValue="">
						<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
									</kendo:dataSource-schema-model-fields>
								</kendo:dataSource-schema-model>
							</kendo:dataSource-schema>

						</kendo:dataSource>

					</kendo:grid>
					<div id="alertsBox" title="Alert"></div>
					</div>
					<style>
#showSelection {
	size: 10, width:5px, height:10px, border:2px
}
.unreadMsgColor {
	/* color: #777; */
	font-size: 13px;
	font-weight: bold;
}

.readMsgColor {
	/* background-color:#E8E8E8 ; */
	color: #777;
	font-size: 13px;
}
</style>
<script>
function inboxOpenReplyCc(){
	 
	 $("#inboxReplyCcDiv").show();
	 $("#inboxReplyCcAnchor").hide();
	 
}
function inboxOpenForwardCc(){
	 
	 $("#inboxForwardCcDiv").show();
	 $("#inboxForwardCcAnchor").hide();
	 
}


	function closePop() {
		$("#inboxReplyCcDiv").hide();
		$("#inboxReplyCcAnchor").show();

	}

	function closePop1() {
		 $("#inboxForwardCcDiv").hide();
		 $("#inboxForwardCcAnchor").show();
		
	}

	function parse(response) {
		$.each(response, function(idx, elem) {
			if (elem.lastUpdatedDate
					&& typeof elem.lastUpdatedDate === "string") {
				elem.lastUpdatedDate = kendo.parseDate(elem.lastUpdatedDate,
						"yyyy-MM-ddTHH:mm:ss.fffZ");
			}
		});
		return response;
	}

	var data = [ {
		text : "Select",
		value : "select"
	}, {
		text : "All",
		value : "all"
	}, {
		text : "None",
		value : "none"
	}, {
		text : "Read",
		value : "allRead"
	}, {
		text : "Unread",
		value : "allUnread"
	} ];
	var data1 = [ {
		text : "More",
		value : "more"
	}, {
		text : "Mark all as read",
		value : "read"
	}

	];
	var data2 = [ {
		text : "Move to",
		value : "moveto"
	}, {
		text : "Trash",
		value : "trash"
	}, ];
	function strip(html) {
		var tmp = document.createElement("DIV");
		tmp.innerHTML = html;
		return tmp.textContent || tmp.innerText;
	}
	$("#inboxGrid").on("click", ".k-grid-Clear_Filter", function() {
		//custom actions
		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
		first_array1 = [];
		checkedIds = {};
		var grid = $("#inboxGrid").data("kendoGrid");
		grid.dataSource.read();
		grid.refresh();
	});

	$(document).ready(function() {
		//location.reload();
		$("#showSelection1").hide();
		$("#inboxReplyCcDiv").hide();
		$("#inboxForwardCcDiv").hide();
		$("#inboxReplyCcAnchor").show();
		$("#inboxForwardCcAnchor").show();

		ccInboxRequired = $("#cc").kendoMultiSelect({
			/*  placeholder: "Select names...", */
			dataTextField : "loginnName",
			dataValueField : "loginnName",
			dataSource : {
				transport : {
					read : {
						url : "./messages/getAllLoginNames",
					}
				}
			}
		});
		cc1InboxRequired = $("#cc1").kendoMultiSelect({
			/*  placeholder: "Select names...", */
			dataTextField : "loginnName",
			dataValueField : "loginnName",
			dataSource : {
				transport : {
					read : {
						url : "./messages/getAllLoginNames",
					}
				}
			}

		});

		replytoRequired = $("#replyTo").kendoMultiSelect({
			/*  placeholder: "Select names...", */
			dataTextField : "loginnName",
			dataValueField : "loginnName",
			dataSource : {
				transport : {
					read : {
						url : "./messages/getAllLoginNames",
					}
				}
			}

		});
		forwardtoRequired = $("#forwardTo").kendoMultiSelect({
			/*  placeholder: "Select names...", */
			dataTextField : "loginnName",
			dataValueField : "loginnName",
			dataSource : {
				transport : {
					read : {
						url : "./messages/getAllLoginNames",
					}
				}
			}

		});
		// create DropDownList from input HTML element
		$("#inboxDropDown").kendoDropDownList({
			dataTextField : "text",
			dataValueField : "value",
			dataSource : data,
			index : 0,
			change : onChange
		});

		// create DropDownList from input HTML element
		$("#moreDropDown").kendoDropDownList({
			dataTextField : "text",
			dataValueField : "value",
			dataSource : data1,
			index : 0,
			change : onChangeMore

		});

		// create DropDownList from input HTML element
		$("#moveToDropDown").kendoDropDownList({
			dataTextField : "text",
			dataValueField : "value",
			dataSource : data2,
			index : 0,
			change : onChangeMove,
			enable : "false"

		});
	});
	inbox_viewDialog = $("#inboxViewDialog").dialog(
			{
				autoOpen : false,
				height : 500,
				width : 750,
				modal : true,
				buttons : {
					Reply : function() {
						$("#subject").val("");
						$("#txtArea").val("");
						var multiSelect = $("#replyTo")
								.data("kendoMultiSelect");
						var multiSelectCc = $("#cc").data("kendoMultiSelect");
						multiSelect.value("");
						multiSelectCc.value("");

						var entityGrid = $("#inboxGrid").data("kendoGrid");
						var data = entityGrid.dataSource.data();
						var totalNumber = data.length;
						var toNames;
						var sub;
						var j = 0;
						for (var i = 0; i < totalNumber; i++) {
							if (data[i].msg_id == id) {
								toNames = data[i].fromUser;
								sub = data[i].subject;

							}
						}
						// replytoRequired.val(toName);
						var toName = toNames.split(",");
						if (toName[0].indexOf('Me') != -1) {
							var toUser = toName[0].substring(3,
									(toName[0].length) - 1);
							multiSelect.value(toUser);
						} else {
							multiSelect.value(toName[0]);
						}

						/*  var toUser = toName.substring(3, (toName.length)-1);
							multiSelect.value(toUser); */
						$("#inboxReplyCcDiv").hide();
						$("#inboxForwardCcDiv").hide();
						$("#inboxReplyCcAnchor").show();
						$("#subject").val(sub);
						$("#inboxForwardCcAnchor").show();
						inbox_replyDialog.dialog("open");

					},
					Forward : function() {
						$("#subject1").val("");
						$("#txtArea1").val("");
						var multiSelect = $("#forwardTo").data(
								"kendoMultiSelect");
						var multiSelectCc = $("#cc1").data("kendoMultiSelect");
						multiSelect.value("");
						multiSelectCc.value("");
						var entityGrid = $("#inboxGrid").data("kendoGrid");
						var data = entityGrid.dataSource.data();
						var totalNumber = data.length;
						var msgs;
						var sub;
						var j = 0;
						for (var i = 0; i < totalNumber; i++) {
							if (data[i].msg_id == id) {
								msgs = data[i].message;
								sub = data[i].subject;

							}
						}

						$("#subject1").val(sub);
						$("#txtArea1").val(msgs);
						$("#inboxReplyCcDiv").hide();
						$("#inboxForwardCcDiv").hide();
						$("#inboxReplyCcAnchor").show();
						$("#inboxForwardCcAnchor").show();
						$("#dialog-form-forward").dialog("open");

					},
					Cancel : function() {
						$.ajax({
							type : "POST",
							dataType : "text",
							url : "./messages/changeReadStatus/" + id
						});
						first_array1 = [];
						checkedIds = {};
						var grid = $("#inboxGrid").data("kendoGrid");
						grid.cancelRow();
						grid.dataSource.read();
						grid.refresh();
						$(this).dialog("close");
						$("#showSelection1").hide();
						$("#moveDiv").hide();
						$("#replyDiv").hide();
						$("#forwardDiv").hide();
						var data1 = [ {
							text : "More",
							value : "more"
						}, {
							text : "Mark all as read",
							value : "read"
						}

						];
						// create DropDownList from input HTML element
						$("#moreDropDown").kendoDropDownList({
							dataTextField : "text",
							dataValueField : "value",
							dataSource : data1,
							index : 0,
							change : onChangeMore

						});
					}
				}
			});
	inbox_forwardDialog = $("#dialog-form-forward")
			.dialog(
					{
						autoOpen : false,
						height : 500,
						width : 750,
						modal : true,
						buttons : {
							"Send" : function() {
								var cc = cc1InboxRequired.val();
								var names = forwardtoRequired.val();
								var sub = $("#subject1").val();
								var msg = $("#txtArea1").val();
								if (msg == "")
									msg = "null";
								else {
									var fstStripMsg = strip(msg);
									var secondStripMsg = strip(fstStripMsg);
									msg = secondStripMsg;
								}
								if (cc == "")
									cc = "null";

								if (names == "" || names == null) {
									$("#alertsBox").html("");
									$("#alertsBox").html(
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
								}

								else {
									$
											.ajax({
												type : "POST",
												dataType : "text",
												/*  data: {
													 name:name,
													 cc:ccValue,
													 sub:sub,
													 msg:msg
												 }, */
												url : "./messages/replyToSender/"
														+ names
														+ "/"
														+ cc
														+ "/" + sub + "/" + msg,
												success : function(response) {
													if (response == "SUCCESS") {
														$("#alertsBox")
																.html("");
														$("#alertsBox")
																.html(
																		"Message  successfully forwarded to the user");
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
																		});

													}
													forwardtoRequired.val("");
													cc1InboxRequired.val("");
													$("#subject1").val("");
													$("#txtArea1").val("");
													$("#showSelection1").hide();
													$("#moveDiv").hide();
													$("#replyDiv").hide();
													$("#forwardDiv").hide();
													var data1 = [
															{
																text : "More",
																value : "more"
															},
															{
																text : "Mark all as read",
																value : "read"
															}

													];
													// create DropDownList from input HTML element
													$("#moreDropDown")
															.kendoDropDownList(
																	{
																		dataTextField : "text",
																		dataValueField : "value",
																		dataSource : data1,
																		index : 0,
																		change : onChangeMore

																	});
													first_array1 = [];
													checkedIds = {};
													var grid = $("#inboxGrid")
															.data("kendoGrid");
													grid.cancelRow();
													grid.dataSource.read();
													grid.refresh();
												}

											});

									$(this).dialog("close");
								}
							},
							"Save to draft" : function() {
								var cc = cc1InboxRequired.val();
								var names = forwardtoRequired.val();
								var sub = $("#subject1").val();
								var msg = $("#txtArea1").val();
								if (msg == "")
									msg = "null";
								else {
									var fstStripMsg = strip(msg);
									var secondStripMsg = strip(fstStripMsg);
									msg = secondStripMsg;
								}
								if (names == "" || names == null)
									names = "null";
								if (cc == "")
									cc = "null";
								if (sub == "")
									sub = "null";

								$
										.ajax({
											type : "POST",
											dataType : "text",
											/* data: {
											 name:toValues,
											 cc:ccValues,
											 sub:sub,
											 msg:msg
											}, */
											url : "./messages/saveToDraft/"
													+ names + "/" + cc + "/"
													+ sub + "/" + msg,
											success : function(response) {
												if (response == "DRAFTSUCCESS") {
													$("#alertsBox").html("");
													$("#alertsBox")
															.html(
																	"This message is successfully saved in draft");
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
												if (response == "DRAFTERROR") {
													$("#alertsBox").html("");
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
												forwardtoRequired.val("");
												cc1InboxRequired.val("");
												$("#subject1").val("");
												$("#txtArea1").val("");
												$("#showSelection1").hide();
												$("#moveDiv").hide();
												$("#replyDiv").hide();
												$("#forwardDiv").hide();
												var data1 = [ {
													text : "More",
													value : "more"
												}, {
													text : "Mark all as read",
													value : "read"
												}

												];
												// create DropDownList from input HTML element
												$("#moreDropDown")
														.kendoDropDownList(
																{
																	dataTextField : "text",
																	dataValueField : "value",
																	dataSource : data1,
																	index : 0,
																	change : onChangeMore

																});
												first_array1 = [];
												checkedIds = {};
												var grid = $("#inboxGrid")
														.data("kendoGrid");
												grid.cancelRow();
												grid.dataSource.read();
												grid.refresh();
											}
										});
								$(this).dialog("close");
							},
							Cancel : function() {
								forwardtoRequired.val("");
								cc1InboxRequired.val("");
								$("#subject1").val("");
								$("#txtArea1").val("");
								$(this).dialog("close");
							}
						}
					});
	inbox_replyDialog = $("#dialog-form")
			.dialog(
					{
						autoOpen : false,
						height : 500,
						width : 750,
						modal : true,
						buttons : {
							"Send" : function() {
								var cc = ccInboxRequired.val();
								var names = replytoRequired.val();
								var sub = $("#subject").val();
								alert("2-" + sub)
								var msg = $("#txtArea").val();
								if (msg == "")
									msg = "null";
								else {
									var fstStripMsg = strip(msg);
									var secondStripMsg = strip(fstStripMsg);
									msg = secondStripMsg;
								}
								if (cc == "")
									cc = "null";

								if (names == "" || names == null) {
									$("#alertsBox").html("");
									$("#alertsBox").html(
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
								}

								else {
									$
											.ajax({
												type : "POST",
												dataType : "text",
												/* data: {
												 name:name,
												 cc:ccValue,
												 sub:sub,
												 msg:secondStripMsg
												}, */
												url : "./messages/replyToSender/"
														+ names
														+ "/"
														+ cc
														+ "/" + sub + "/" + msg,
												success : function(response) {
													if (response == "SUCCESS") {
														$("#alertsBox")
																.html("");
														$("#alertsBox")
																.html(
																		"Message  successfully sent");
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
																		});

													}
													replytoRequired.val("");
													ccInboxRequired.val("");
													$("#subject").val("");
													$("#txtArea").val("");

													$("#showSelection1").hide();
													$("#moveDiv").hide();
													$("#replyDiv").hide();
													$("#forwardDiv").hide();
													var data1 = [
															{
																text : "More",
																value : "more"
															},
															{
																text : "Mark all as read",
																value : "read"
															}

													];
													// create DropDownList from input HTML element
													$("#moreDropDown")
															.kendoDropDownList(
																	{
																		dataTextField : "text",
																		dataValueField : "value",
																		dataSource : data1,
																		index : 0,
																		change : onChangeMore

																	});
													first_array1 = [];
													checkedIds = {};
													var grid = $("#inboxGrid")
															.data("kendoGrid");
													grid.cancelRow();
													grid.dataSource.read();
													grid.refresh();
												}

											});

									$(this).dialog("close");
								}
							},
							"Save to draft" : function() {
								var cc = ccInboxRequired.val();
								var names = replytoRequired.val();
								var sub = $("#subject").val();
								var msg = $("#txtArea").val();
								if (msg == "")
									msg = "null";
								else {
									var fstStripMsg = strip(msg);
									msg = fstStripMsg;
									var secondStripMsg = strip(fstStripMsg);
									msg = secondStripMsg;
								}
								if (names == "" || names == null)
									names = "null";
								if (cc == "")
									cc = "null";
								if (sub == "")
									sub = "null";

								$
										.ajax({
											type : "POST",
											dataType : "text",
											/*  data: {
												 name:toValues,
												 cc:ccValues,
												 sub:sub,
												 msg:secondStripMsg
											 }, */
											url : "./messages/saveToDraft/"
													+ names + "/" + cc + "/"
													+ sub + "/" + msg,
											success : function(response) {
												if (response == "DRAFTSUCCESS") {
													$("#alertsBox").html("");
													$("#alertsBox")
															.html(
																	"This message is successfully saved in draft");
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
												if (response == "DRAFTERROR") {
													$("#alertsBox").html("");
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
												replytoRequired.val("");
												ccInboxRequired.val("");
												$("#subject").val("");
												$("#txtArea").val("");
												$("#showSelection1").hide();
												$("#moveDiv").hide();
												$("#replyDiv").hide();
												$("#forwardDiv").hide();
												var data1 = [ {
													text : "More",
													value : "more"
												}, {
													text : "Mark all as read",
													value : "read"
												}

												];
												// create DropDownList from input HTML element
												$("#moreDropDown")
														.kendoDropDownList(
																{
																	dataTextField : "text",
																	dataValueField : "value",
																	dataSource : data1,
																	index : 0,
																	change : onChangeMore

																});
												first_array1 = [];
												checkedIds = {};
												var grid = $("#inboxGrid")
														.data("kendoGrid");
												grid.cancelRow();
												grid.dataSource.read();
												grid.refresh();
											}
										});
								$(this).dialog("close");
							},
							Cancel : function() {
								replytoRequired.val("");
								ccInboxRequired.val("");
								$("#subject").val("");
								$("#txtArea").val("");

								$(this).dialog("close");
							}
						},
					});

	$("#replyButton").button().click(function() {
		var multiSelect = $("#replyTo").data("kendoMultiSelect");
		var multiSelectCc = $("#cc").data("kendoMultiSelect");
		multiSelect.value("");
		multiSelectCc.value("");
		$("#subject").val("");
		$("#txtArea").val("");
		var entityGrid = $("#inboxGrid").data("kendoGrid");
		var data = entityGrid.dataSource.data();
		var totalNumber = data.length;
		var toNames;
		var j = 0;
		for (var i = 0; i < totalNumber; i++) {
			if (data[i].msg_id == first_array1) {
				toNames = data[i].fromUser;

			}
		}

		var toName = toNames.split(",");
		if (toName[0].indexOf('Me') != -1) {
			var toUser = toName[0].substring(3, (toName[0].length) - 1);
			multiSelect.value(toUser);
		} else {
			multiSelect.value(toName[0]);
		}
		$("#inboxReplyCcDiv").hide();
		$("#inboxForwardCcDiv").hide();
		$("#inboxReplyCcAnchor").show();
		$("#inboxForwardCcAnchor").show();
		inbox_replyDialog.dialog("open");

	});

	$("#forwardButton").button().click(function() {
		var multiSelect = $("#forwardTo").data("kendoMultiSelect");
		var multiSelectCc = $("#cc1").data("kendoMultiSelect");
		multiSelect.value("");
		multiSelectCc.value("");
		$("#subject1").val("");
		$("#txtArea1").val("");
		var entityGrid = $("#inboxGrid").data("kendoGrid");
		var data = entityGrid.dataSource.data();
		var totalNumber = data.length;
		var msgs;
		var sub;
		var j = 0;
		for (var i = 0; i < totalNumber; i++) {
			if (data[i].msg_id == first_array1) {
				msgs = data[i].message;
				sub = data[i].subject;

			}
		}

		$("#subject1").val(sub);
		if (msgs == null) {
			msgs = "";
		}
		$("#txtArea1").val(msgs);
		// $("#txtArea1").text(msgs);
		$("#inboxReplyCcDiv").hide();
		$("#inboxForwardCcDiv").hide();
		$("#inboxReplyCcAnchor").show();
		$("#inboxForwardCcAnchor").show();
		inbox_forwardDialog.dialog("open");

	});

	function inboxDataBound(e) {
		var grid = $("#inboxGrid").data("kendoGrid");
		var gridData = grid.dataSource.view();
		$("#showSelection1").hide();
		$("#inboxReplyCcDiv").hide();
		$("#inboxForwardCcDiv").hide();
		$("#inboxReplyCcAnchor").show();
		$("#inboxForwardCcAnchor").show();

		var totalNumber = gridData.length;
		if (totalNumber == 0) {
			$("#selectDropDown").hide();
			$("#moveDiv").hide();
			$("#moreButton").hide();

		}

		for (var i = 0; i < gridData.length; i++) {
			var currentUid = gridData[i].uid;
			if (gridData[i].read_status == 0) {
				var currentRow = grid.table.find("tr[data-uid='" + currentUid
						+ "']");
				$(currentRow).addClass("unreadMsgColor");
			}
			if (gridData[i].read_status == 1) {
				var currentRows = grid.table.find("tr[data-uid='" + currentUid
						+ "']");
				$(currentRows).addClass("readMsgColor");
			}
		}

	}

	//$(".moveDropDown").show();

	$("#moveDiv").hide();
	$("#replyDiv").hide();
	$("#forwardDiv").hide();

	//onchange function for movetoDropdown
	function onChangeMove() {
		var moveValues = $("#moveToDropDown").val();
		if (moveValues == "moveto") {

		}
		if (moveValues == "trash") {

			var agree = false;
			agree = confirm("Are you really want to send this msg to trash..?");
			$.ajax({
				type : "POST",
				dataType : "text",
				// beforeSend: function() {  
				/* alert("Are you really want to send this " + count + "  messages to trash..?"); */
				// agree = confirm("Are you really want to send this msg to trash..?");
				//alert("agree " + agree);
				//}, 

				url : "./messages/deleteMessage/" + first_array1 + "/" + agree,
				success : function(response) {
					if (response == "SUCCESS") {
						alert("Messages are successfully moved to trash.");
						first_array1 = [];
						checkedIds = {};
						var grid = $("#inboxGrid").data("kendoGrid");
						grid.cancelRow();
						grid.dataSource.read();
						grid.refresh();
						//location.reload(false);
						//window.location.reload(true);
						//document.location.reload(true)

					}

					if (response == "FAIL") {

						first_array1 = [];
						checkedIds = {};
						var grid = $("#inboxGrid").data("kendoGrid");
						grid.cancelRow();
						grid.dataSource.read();
						grid.refresh();

					}

				}
			});
			$("#showSelection1").hide();
			$("#moveDiv").hide();
			$("#replyDiv").hide();
			$("#forwardDiv").hide();

			first_array1 = [];
			checkedIds = {};
			var grid = $("#inboxGrid").data("kendoGrid");
			grid.cancelRow();
			grid.dataSource.read();
			grid.refresh();

			var data1 = [ {
				text : "More",
				value : "more"
			}, {
				text : "Mark all as read",
				value : "read"
			}

			];
			// create DropDownList from input HTML element
			$("#moreDropDown").kendoDropDownList({
				dataTextField : "text",
				dataValueField : "value",
				dataSource : data1,
				index : 0,
				change : onChangeMore

			});

		}

	}

	var a = new Array();
	//onchange function for moreDropdown
	function onChangeMore() {

		var moreValues = $("#moreDropDown").val();

		if (moreValues == "more") {

		}
		if (moreValues == "read") {

			var entityGrid = $("#inboxGrid").data("kendoGrid");
			var data = entityGrid.dataSource.data();
			for (var i = 0; i < data.length; i++) {
				if (data[i].read_status == 0) {

					$.ajax({
						type : "POST",
						dataType : "text",
						url : "./messages/updateReadStatusAsOne/"
								+ data[i].msg_id
					});
					first_array1 = [];
					checkedIds = {};
					var grid = $("#inboxGrid").data("kendoGrid");
					grid.cancelRow();
					grid.dataSource.read();
					grid.refresh();
					$("#showSelection1").hide();
					$("#moveDiv").hide();
					$("#replyDiv").hide();
					$("#forwardDiv").hide();
					var data1 = [ {
						text : "More",
						value : "more"
					}, {
						text : "Mark all as read",
						value : "read"
					}

					];
					// create DropDownList from input HTML element
					$("#moreDropDown").kendoDropDownList({
						dataTextField : "text",
						dataValueField : "value",
						dataSource : data1,
						index : 0,
						change : onChangeMore

					});

				}
			}

		}
		if (moreValues == "markRead") {

			for (var i = 0; i < first_array1.length; i++) {
				$.ajax({
					type : "POST",
					dataType : "text",
					url : "./messages/updateReadStatusAsOne/" + first_array1[i]
				});
			}
			first_array1 = [];
			checkedIds = {};
			var grid = $("#inboxGrid").data("kendoGrid");
			grid.cancelRow();
			grid.dataSource.read();
			grid.refresh();
			$("#showSelection1").hide();
			$("#moveDiv").hide();
			$("#replyDiv").hide();
			$("#forwardDiv").hide();
			var data1 = [ {
				text : "More",
				value : "more"
			}, {
				text : "Mark all as read",
				value : "read"
			}

			];
			// create DropDownList from input HTML element
			$("#moreDropDown").kendoDropDownList({
				dataTextField : "text",
				dataValueField : "value",
				dataSource : data1,
				index : 0,
				change : onChangeMore

			});
		}
		if (moreValues == "markUnread") {

			for (var i = 0; i < first_array1.length; i++) {
				$.ajax({
					type : "POST",
					dataType : "text",
					url : "./messages/updateReadStatusAsZero/"
							+ first_array1[i]
				});
			}
			first_array1 = [];
			checkedIds = {};
			var grid = $("#inboxGrid").data("kendoGrid");
			grid.cancelRow();
			grid.dataSource.read();
			grid.refresh();
			$("#showSelection1").hide();
			$("#moveDiv").hide();
			$("#replyDiv").hide();
			$("#forwardDiv").hide();
			var data1 = [ {
				text : "More",
				value : "more"
			}, {
				text : "Mark all as read",
				value : "read"
			}

			];
			// create DropDownList from input HTML element
			$("#moreDropDown").kendoDropDownList({
				dataTextField : "text",
				dataValueField : "value",
				dataSource : data1,
				index : 0,
				change : onChangeMore

			});
		}
	}

	//onchange function for dropdown
	function onChange() {

		$("#showSelection1").hide();
		$("#moveDiv").hide();
		/* var inboxGrid = $("#inboxGrid").data("kendoGrid");
		inboxGrid.cancelRow();
		inboxGrid.dataSource.read();
		inboxGrid.refresh(); */

		var values = $("#inboxDropDown").val();
		if (values == "select") {
			$("#moveDiv").hide();
			$("#replyDiv").hide();
			$("#forwardDiv").hide();
			var data1 = [ {
				text : "More",
				value : "more"
			}, {
				text : "Mark all as read",
				value : "read"
			}

			];
			// create DropDownList from input HTML element
			$("#moreDropDown").kendoDropDownList({
				dataTextField : "text",
				dataValueField : "value",
				dataSource : data1,
				index : 0,
				change : onChangeMore

			});
			first_array1 = [];
			$('input').prop('checked', false);
			$("#showSelection1").hide();
			$("#moveDiv").hide();

			if (first_array1.length == 1) {
				$("#replyDiv").show();
				$("#forwardDiv").show();
				$("#showSelection1").show();
				$("#moveDiv").show();

			} else if (first_array1.length > 1) {
				$("#replyDiv").hide();
				$("#forwardDiv").hide();

			}

		}
		if (values == "all") {
			$("#replyDiv").hide();
			$("#forwardDiv").hide();
			if (first_array1.length == 1) {
				$("#replyDiv").show();
				$("#forwardDiv").show();
				$("#showSelection1").show();
				$("#moveDiv").show();

			} else if (first_array1.length > 1) {
				$("#replyDiv").hide();
				$("#forwardDiv").hide();

			}
			var data1 = [ {
				text : "More",
				value : "more"
			}, {
				text : "Mark as read",
				value : "markRead"
			}, {
				text : "Mark as unread",
				value : "markUnread"
			}

			];
			// create DropDownList from input HTML element
			$("#moreDropDown").kendoDropDownList({
				dataTextField : "text",
				dataValueField : "value",
				dataSource : data1,
				index : 0,
				change : onChangeMore

			});
			var entityGrid = $("#inboxGrid").data("kendoGrid");
			var data = entityGrid.dataSource.data();
			var totalNumber = data.length;
			first_array1 = [];
			// $('input').attr('checked' , true);
			//$('input').prop('checked' , true);
			// $('input').attr('checked','checked');
			for (var i = 0; i < totalNumber; i++) {
				var currentDataItem = data[i];

				if ($.inArray(data[i].msg_id, first_array1) < 0) {

					first_array1[i] = data[i].msg_id;
					first_array1[i].checked = true;
					$('#checkStatus1_' + first_array1[i]).prop('checked', true);
				} else {
					$('#checkStatus1_' + data[i].msg_id).prop('checked', true);
				}
				// $('#checkStatus1_'+currentDataItem.msg_id).prop('checked' , true);
				// $('input').prop('checked' , true);
			}

			$("#showSelection1").show();
			$("#moveDiv").show();

			if (first_array1.length == 1) {
				$("#replyDiv").show();
				$("#forwardDiv").show();
				$("#showSelection1").show();
				$("#moveDiv").show();

			} else if (first_array1.length > 1) {
				$("#replyDiv").hide();
				$("#forwardDiv").hide();

			}

		}

		if (values == "none") {
			$("#replyDiv").hide();
			$("#forwardDiv").hide();
			var data1 = [ {
				text : "More",
				value : "more"
			}, {
				text : "Mark all as read",
				value : "read"
			}

			];
			// create DropDownList from input HTML element
			$("#moreDropDown").kendoDropDownList({
				dataTextField : "text",
				dataValueField : "value",
				dataSource : data1,
				index : 0,
				change : onChangeMore

			});
			var entityGrid = $("#inboxGrid").data("kendoGrid");
			var data = entityGrid.dataSource.data();
			var totalNumber = data.length;
			first_array1 = [];
			for (var i = 0; i < totalNumber; i++) {
				var currentDataItem = data[i];
				$('input').prop('checked', false);
				var removeItem = data[i].msg_id;
				first_array1 = jQuery.grep(first_array1, function(value) {
					return value != removeItem;
				});

			}

			$("#showSelection1").hide();
			$("#moveDiv").hide();

			if (first_array1.length == 1) {
				$("#replyDiv").show();
				$("#forwardDiv").show();
				$("#showSelection1").show();
				$("#moveDiv").show();

			} else if (first_array1.length > 1) {
				$("#replyDiv").hide();
				$("#forwardDiv").hide();

			}
		}
		if (values == "allRead") {
			$("#replyDiv").hide();
			$("#forwardDiv").hide();
			var data1 = [ {
				text : "More",
				value : "more"
			}, {
				text : "Mark as unread",
				value : "markUnread"
			}

			];
			// create DropDownList from input HTML element
			$("#moreDropDown").kendoDropDownList({
				dataTextField : "text",
				dataValueField : "value",
				dataSource : data1,
				index : 0,
				change : onChangeMore

			});

			var entityGrid = $("#inboxGrid").data("kendoGrid");
			var data = entityGrid.dataSource.data();
			var totalNumber = data.length;
			first_array1 = [];
			var j = 0;
			for (var i = 0; i < totalNumber; i++) {
				var currentDataItem = data[i];
				var rowId = data[i].msg_id;
				if (data[i].read_status == "1") {

					if ($.inArray(data[i].msg_id, first_array1) < 0) {
						first_array1[j] = data[i].msg_id;
						j++;
						$('#checkStatus1_' + data[i].msg_id).prop('checked',
								true);

					} else {
						$('#checkStatus1_' + data[i].msg_id).prop('checked',
								true);
					}

				} else {
					$('#checkStatus1_' + data[i].msg_id).prop('checked', false);
				}

			}
			$("#showSelection1").show();
			$("#moveDiv").show();

			if (first_array1.length == 1) {
				$("#replyDiv").show();
				$("#forwardDiv").show();
				$("#showSelection1").show();
				$("#moveDiv").show();

			} else if (first_array1.length > 1) {
				$("#replyDiv").hide();
				$("#forwardDiv").hide();

			}
		}
		if (values == "allUnread") {
			$("#replyDiv").hide();
			$("#forwardDiv").hide();
			var data1 = [ {
				text : "More",
				value : "more"
			}, {
				text : "Mark as read",
				value : "markRead"
			}

			];
			// create DropDownList from input HTML element
			$("#moreDropDown").kendoDropDownList({
				dataTextField : "text",
				dataValueField : "value",
				dataSource : data1,
				index : 0,
				change : onChangeMore

			});
			var entityGrid = $("#inboxGrid").data("kendoGrid");
			var data = entityGrid.dataSource.data();
			var totalNumber = data.length;
			first_array1 = [];
			var j = 0;
			for (var i = 0; i < totalNumber; i++) {
				var currentDataItem = data[i];
				var rowId = data[i].msg_id;
				if (data[i].read_status == "0") {

					if ($.inArray(data[i].msg_id, first_array1) < 0) {
						first_array1[j] = data[i].msg_id;
						j++;
						$('#checkStatus1_' + data[i].msg_id).prop('checked',
								true);

					} else {
						$('#checkStatus1_' + data[i].msg_id).prop('checked',
								true);
					}
				} else {
					$('#checkStatus1_' + data[i].msg_id).prop('checked', false);
				}

			}
			$("#showSelection1").show();
			$("#moveDiv").show();
			if (first_array1.length == 1) {
				$("#replyDiv").show();
				$("#forwardDiv").show();
				$("#showSelection1").show();
				$("#moveDiv").show();

			} else if (first_array1.length > 1) {
				$("#replyDiv").hide();
				$("#forwardDiv").hide();

			}
		}
	}

	//onclick function for checkbox
	$("#inboxGrid")
			.on(
					"click",
					"[class^=checkbox1]",
					function(e) {
						$("#replyDiv").hide();
						// first_array1 = [];

						var data2 = [ {
							text : "Move to",
							value : "moveto"
						}, {
							text : "Trash",
							value : "trash"
						}, ];
						// create DropDownList from input HTML element
						$("#moveToDropDown").kendoDropDownList({
							dataTextField : "text",
							dataValueField : "value",
							dataSource : data2,
							index : 0,
							change : onChangeMove

						});

						var checked = this.checked, row = $(this).closest("tr"), grid = $(
								"#inboxGrid").data("kendoGrid"), dataItem = grid
								.dataItem(row);
						checkedIds[dataItem.id] = checked;
						if (checked) {

							if (first_array1.length == 0) {
								first_array1[first_array1.length] = dataItem.msg_id;

							} else {

								if ($.inArray(dataItem.msg_id, first_array1) < 0) {
									first_array1[first_array1.length] = dataItem.msg_id;

								}

							}
							var data1 = [ {
								text : "More",
								value : "more"
							}, {
								text : "Mark as read",
								value : "markRead"
							}, {
								text : "Mark as unread",
								value : "markUnread"
							}

							];
							// create DropDownList from input HTML element
							$("#moreDropDown").kendoDropDownList({
								dataTextField : "text",
								dataValueField : "value",
								dataSource : data1,
								index : 0,
								change : onChangeMore

							});
							//enable delete button 
							$("#showSelection1").show();
							$("#moveDiv").show();
							//-select the row
							row.addClass("k-state-selected");
						} else {
							//alert(">>.fst array lengthbefore uncheck " + first_array1.length);
							for (var i = 0; i < first_array1.length; i++) {
								$('#checkStatus1_' + first_array1[i]).attr(
										'checked', true);

								if (first_array1[i] == dataItem.msg_id) {

									$('#checkStatus1_' + dataItem.msg_id).attr(
											'checked', false);
									var removeItem = dataItem.msg_id;
									first_array1 = jQuery.grep(first_array1,
											function(value) {
												return value != removeItem;
											});
									//alert(">>.fst array length after uncheck " + first_array1.length);
									//row.removeClass("k-state-selected");

								} else {

									$('#checkStatus1_' + first_array1[i]).attr(
											'checked', true);

								}

							}

							//hide delete button
							if (first_array1.length == 0) {

								$("#replyDiv").hide();
								$("#forwardDiv").hide();
								$("#showSelection1").hide();
								$("#moveDiv").hide();

								var data1 = [ {
									text : "More",
									value : "more"
								}, {
									text : "Mark all as read",
									value : "read"
								}

								];
								// create DropDownList from input HTML element
								$("#moreDropDown").kendoDropDownList({
									dataTextField : "text",
									dataValueField : "value",
									dataSource : data1,
									index : 0,
									change : onChangeMore

								});

								var grid = $("#inboxGrid").data("kendoGrid");
								/* grid.cancelRow();
								grid.dataSource.read();
								grid.refresh(); */

							} else if (first_array1.length > 0) {

								$("#forwardDiv").hide();
								$("#replyDiv").hide();

								$("#showSelection1").show();
								$("#moveDiv").show();

								var data1 = [ {
									text : "More",
									value : "more"
								}, {
									text : "Mark as read",
									value : "markRead"
								}, {
									text : "Mark as unread",
									value : "markUnread"
								}

								];
								// create DropDownList from input HTML element
								$("#moreDropDown").kendoDropDownList({
									dataTextField : "text",
									dataValueField : "value",
									dataSource : data1,
									index : 0,
									change : onChangeMore

								});

								var grid = $("#inboxGrid").data("kendoGrid");
								/* grid.cancelRow();
								grid.dataSource.read();
								grid.refresh(); */

							}
						}
						if (first_array1.length == 1) {
							$("#replyDiv").show();
							$("#forwardDiv").show();
							$("#showSelection1").show();
							$("#moveDiv").show();

						} else if (first_array1.length > 1) {
							$("#replyDiv").hide();
							$("#forwardDiv").hide();

						}
					});

	function deleteIds1() {

		var agree = false;
		agree = confirm("Are you really want to delete this msg..?");
		$.ajax({
			type : "POST",
			dataType : "text",
			// beforeSend: function() {  
			/* alert("Are you really want to send this " + count + "  messages to trash..?"); */
			// agree = confirm("Are you really want to send this msg to trash..?");
			//alert("agree " + agree);
			//}, 

			url : "./messages/deleteMessage/" + first_array1 + "/" + agree,
			success : function(response) {
				if (response == "SUCCESS") {
					alert("Messages are successfully moved to trash.");
					first_array1 = [];
					checkedIds = {};
					var grid = $("#inboxGrid").data("kendoGrid");
					grid.cancelRow();
					grid.dataSource.read();
					grid.refresh();
					//location.reload(false);
					//window.location.reload(true);
					//document.location.reload(true)	

				}

				if (response == "FAIL") {

					first_array1 = [];
					checkedIds = {};
					var grid = $("#inboxGrid").data("kendoGrid");
					grid.cancelRow();
					grid.dataSource.read();
					grid.refresh();

				}

			}
		});
		$("#showSelection1").hide();
		$("#moveDiv").hide();
		$("#replyDiv").hide();
		$("#forwardDiv").hide();

		first_array1 = [];
		checkedIds = {};
		var grid = $("#inboxGrid").data("kendoGrid");
		grid.cancelRow();
		grid.dataSource.read();
		grid.refresh();

		var data1 = [ {
			text : "More",
			value : "more"
		}, {
			text : "Mark all as read",
			value : "read"
		}

		];
		// create DropDownList from input HTML element
		$("#moreDropDown").kendoDropDownList({
			dataTextField : "text",
			dataValueField : "value",
			dataSource : data1,
			index : 0,
			change : onChangeMore

		});

	}
</script>
</div>
</div>

<style>
/* body {
background-color: #444;
margin: 0;
}  */   
#wrapper {
width: 1300px;
margin: 0 auto;
} 
 #rightcolumn {
border: 1px solid white;
float: left;
min-height: 450px;
color: white;
}
#rightcolumn {
width: 1079px;
background-color: light gray; 
}
body, td, input, textarea, select {
font-family: arial,sans-serif;
}
body, td, input, textarea, select {
font-family: arial,sans-serif;
}
}
#deleteButton {
width: 150px;
background-color: light gray; 
}
#selectDropDown {
width: 50px;
background-color: light gray;
}
#moreButton {
width: 300px;
background-color: light gray;

/* border-radius: 2px;
cursor: default;
font-size: 11px;
font-weight: bold;
height: 27px;
line-height: 27px;
margin-right: 20px;
min-width: 54px;
outline: 0 none;
padding: 0 8px;
text-align: center;
white-space: nowrap; */
}
.wrapper{
display:inline;

}

.ui-dialog .ui-dialog-buttonpane button {
background:gray;
border-radius: 0px;
box-shadow: 0 1px 2px 0 #66B2D2 inset; 
color: #FFFFFF;
display: inline-block;
font-size: 11px;
font-weight: bold;
line-height: 14px;
margin: 0 5px;
padding: 7px 16px;
text-shadow: 0 -1px #6F6F6F; 
}
.ui-dialog .ui-dialog-buttonpane .ui-dialog-buttonset {
margin: 0 4px;
text-align: right;
}
.compose{
background:gray;

}
#replyAndForward{
color: #999999;


}
.inbox-subjectField{
font-weight: inherit;
font-size:26px;
color: #222222;
}
.inbox-fromUserField{
font-weight: inherit;
font-size:20px;
color: #222222;
}
#inbox-viewTxtArea{
font-weight: normal;
font-size:16px;
color: #222222;
-moz-tab-size:4;
tab-width:4;
}
#replyAndForward span {
/* background-color: #E8E8E8; */
display: inline;
cursor: pointer;
text-decoration: underline;
}
.amt {
color: #1155CC;
}
.ams {
cursor: pointer;
text-decoration: underline;
}

 .inbox-header h1 {
    color: #666;
    margin: 0 0 10px;
     font-size: 43px;
}
.pull-left {
    float: left !important;
}
.pull-left {
    float: left;
}
h1, h2, h3, h4, h5, h6 {
    font-family: 'Open Sans',sans-serif;
    font-weight: 300 !important;
}
h1, .h1 {
   
}
h1, h2, h3 {
    margin-bottom: 10px;
    margin-top: 20px;
}
h1, h2, h3, h4, h5, h6, .h1, .h2, .h3, .h4, .h5, .h6 {
    font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
    font-weight: 500;
    line-height: 1.1;
}
h1 {
    font-size: 2em;
    margin: 0.67em 0;}
    
 #inboxReplyCcAnchor{
   /*  text-decoration: underline; */
    border-bottom:1px solid blue;
}
 #inboxForwardCcAnchor{
   /*  text-decoration: underline; */
    border-bottom:1px solid blue;
}
#raj{
   /*  text-decoration: underline; */
    border-bottom:1px solid blue;
}
#raj1{
   /*  text-decoration: underline; */
    border-bottom:1px solid blue;
}

</style>
            