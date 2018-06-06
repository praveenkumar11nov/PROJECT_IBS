<%@include file="/common/taglibs.jsp"%>
<%@include file="messagesLink.jsp"%>

<c:url value="/messages/composeMessages" var="createUrl" />
<c:url value="/messages/readMessages" var="readUrl" />

<c:url value="/messages/sentMessages" var="readSentMailsUrl" />
<c:url value="/messages/deleteSentMessage" var="deleteSentMessageUrl" />

<c:url value="/messages/saveMessages" var="saveMessagesUrl" />

<c:url value="/messages/getUserName" var="getUserName" />
<c:url value="/messages/getSelectionValue" var="getSelectionValueUrl" />
<c:url value="/messages/getToNameList" var="getToNameListUrl" />
<script>
var id;
var viewDialog;
var forwardDialog;
var replyDialog;

var sentReplyRequired;
var sentCcRequired;
var sentForwardRequired;
var sentCc1Required;
</script>

				
					<div id="wrapper" class="wrapper">

    <div id="rightcolumn">
				
		 <div id="selectDropDown" class="wrapper">
				<kendo:dropDownList name="sentDropDown" value="1">
        <kendo:dataSource ></kendo:dataSource>
    </kendo:dropDownList>  
    </div>
    <div id="deleteButton" class="wrapper">
					<a href="#" id="showSelection3" onclick="deleteIds2()"><img title="Delete" alt="" src="./resources/images/deleteIcon.jpg" width="20px"></a>
					
	</div>	
    <div class="wrapper" id="moveDiv">
   <kendo:dropDownList name="moveToDropDown" dataTextField="text"
						dataValueField="value" >
						<kendo:dataSource></kendo:dataSource>
					</kendo:dropDownList> 
    </div> 
	
	<div class="wrapper" id="moreButton">
  <kendo:dropDownList name="moreDropDown" dataTextField="text"
						dataValueField="value">
						<kendo:dataSource></kendo:dataSource>
					</kendo:dropDownList>
    </div>
			 <!--  div for reply popup -->

<div id="dialog-form" title="Reply to sender" hidden="true">
			<form>
				<fieldset>
					<label></label>
					<div class="">
						<h6>To :</h6>
						 <select
							id="sent-replyTo" multiple="multiple">
						</select>
					<a onclick="sentReplyOpenCc()" id="sentReplyCcAnchor">CC</a></div>
					<div id="sentReplyCcDiv" hidden="true">
						<a onclick="ccClose()" id="acc">CC :</a>
						 <select
							id="sent-cc" multiple="multiple">
						</select>
					</div>
					<div class="">
						<h6>Subject :</h6>
						<input type="text"
						name="composeSubject" id="sent-subject"
						class="text ui-widget-content ui-corner-all"> 
					</div>
					<div class="">
						<h6>Messages :</h6>
						<textarea id="sent-txtArea" rows="20" cols="0"
						name="composeTxtArea"></textarea>
					</div>
				</fieldset>
			</form>
		</div>   
<div id="dialog-form-forward" title="Forward message" hidden="true">
			<form>
				<fieldset>
					<label></label>
					<div class="">
						<h6>To :</h6>
						 <select
							id="sent-forwardTo" multiple="multiple">
						</select>
					<a onclick="sentForwardOpenCc()" id="sentForwardCcAnchor">CC</a></div>
					<div id="sentForwardCcDiv" hidden="true">
						<a onclick="ccClose1()" id="acc2">CC :</a>
						 <select
							id="sent-cc1" multiple="multiple">
						</select>
					</div>
					<div class="">
						<h6>Subject :</h6>
						<input type="text"
						name="composeSubject" id="sent-subject1"
						class="text ui-widget-content ui-corner-all"> 
					</div>
					<div class="">
						<h6>Messages :</h6>
						<textarea id="sent-txtArea1" rows="20" cols="0"
						name="composeTxtArea"></textarea>
					</div>
				</fieldset>
			</form>
		</div>   
<div id="sentGridDiv">
<div id="sent-view-dialog" title="View Message"> 
<!-- <div id="replyAndForward" align="center">Click here to <span id="viewReply" tabindex="0" role="link" class="ams amt">Reply </span>or <span id="viewForward" tabindex="0" role="link" class="ams amt" >Forward </span></div><br/> -->
<div id="sentSubjectField" class="sentSubjectField"></div><br/>
<div id="sentToUserField" class="sentToUserField"></div><br/>
Message: <textarea id="sent-viewTxtArea" rows="15" cols="0" name="sent-viewTxtArea" disabled="disabled" ></textarea>
</div>	


					<kendo:grid name="sentGrid" height="430px" dataBound="sentDataBound" pageable="true" sortable="true" filterable="true" groupable="true">
					<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" ></kendo:grid-pageable>
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
							<kendo:grid-column title="Message Id" field="msg_id" width="70px"
								hidden="true" />
								<kendo:grid-column title="" field=""
								template="<input type='checkbox' id='checkStatus2_#=data.msg_id#' class='checkbox2' />"
								width="10px" />
							<kendo:grid-column title="To : " field="toUser" width="70px" >
							<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function NameFilter(element) {
								element.kendoAutoComplete({
									dataSource: {
										transport: {
											read: "${getToNameListUrl}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	
	    		</kendo:grid-column>
							<kendo:grid-column title="Subject : " field="subject"
								width="130px" filterable="false"/>
							<kendo:grid-column title="Message :" field="message" hidden="true"
								width="70px" filterable="false"/>
								<kendo:grid-column title="Date" field="lastUpdatedDate" format="{0:dd/MM/yyyy hh:mm tt}" width="50px" filterable="false"/>
							<kendo:grid-column title="&nbsp;" width="50px">
								<kendo:grid-column-command>
									<kendo:grid-column-commandItem name="sent-viewDetails" text="View Details">
                        <kendo:grid-column-commandItem-click>
                            <script>
                            function sentShowDetails(e) {
                            	
                                 var dataItem = this.dataItem($(e.currentTarget).closest("tr"));
                                 $('div.sentSubjectField').text( "Subject: "+ dataItem.subject);
                                 $('div.sentToUserField').text( "To: "+ dataItem.toUser);
                                 id = dataItem.msg_id;
                                 if( dataItem.message == null )
                                	 $("#sent-viewTxtArea").val("");
                                 else
                                 $("#sent-viewTxtArea").val(dataItem.message);
                                 $("#sentReplyCcDiv").hide();
                				 $("#sentReplyCcAnchor").show();
                				 $("#sentForwardCcDiv").hide();
                				 $("#sentForwardCcAnchor").show();
                                 //$(".ui-icon-closethick").hide();
                                // $( "#sent-view-dialog" ).dialog( "open" );
                                viewDialog.dialog("open");
                                 
                                 
                                 $(".ui-icon-closethick").click(function(){
                                	 $.ajax({type:"POST",dataType:"text",url:"./messages/changeReadStatus/"+id }); 
                                	 first_array2 = [];
                        			 checkedIds = {};
                                	 var grid = $("#sentGrid").data("kendoGrid");
    									grid.cancelRow();
    									grid.dataSource.read();
    									grid.refresh();
    									
    									$("#showSelection3").hide();
										$("#moveDiv").hide();
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
								                       
								                       
								                    }); 
                                 });
                            }
                            </script>
                        </kendo:grid-column-commandItem-click>
                   </kendo:grid-column-commandItem>
								</kendo:grid-column-command>
							</kendo:grid-column>
						</kendo:grid-columns>

						<kendo:dataSource pageSize="20">
							<kendo:dataSource-transport>
								<kendo:dataSource-transport-read url="${readSentMailsUrl}"
									dataType="json" type="POST" contentType="application/json" />
								<kendo:dataSource-transport-update url="${viewMailUrl}"
									dataType="json" type="POST" contentType="application/json" />
								<kendo:dataSource-transport-destroy
									url="${deleteSentMessageUrl}" dataType="json" type="POST"
									contentType="application/json" />
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
											type="number"  editable="false" />
										<kendo:dataSource-schema-model-field name="toUser"  editable="false"
											type="string">
											<kendo:dataSource-schema-model-field-validation
												required="true" />
										</kendo:dataSource-schema-model-field>
										<kendo:dataSource-schema-model-field name="subject"
											type="string">
										</kendo:dataSource-schema-model-field>
										<kendo:dataSource-schema-model-field name="message"  editable="false" 
											type="string" >
										</kendo:dataSource-schema-model-field>
										<kendo:dataSource-schema-model-field name="read_status"
											editable="false" type="number" />
											<kendo:dataSource-schema-model-field name="lastUpdatedDate" type="date" defaultValue="">
						<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
									</kendo:dataSource-schema-model-fields>
								</kendo:dataSource-schema-model>
							</kendo:dataSource-schema>

						</kendo:dataSource>

					</kendo:grid>
					<div id="alertsBox" title="Alert"></div>


			<script>
			
			function ccClose()
			{
				 $("#sentReplyCcDiv").hide();
				 $("#sentReplyCcAnchor").show();
				
			}
			
			function ccClose1()
			{

				 $("#sentForwardCcDiv").hide();
				 $("#sentForwardCcAnchor").show();
				 
			}
			
			
			function sentReplyOpenCc(){
				 
				 $("#sentReplyCcDiv").show();
				 $("#sentReplyCcAnchor").hide();
				 
			 }
			function sentForwardOpenCc(){
				 
				 $("#sentForwardCcDiv").show();
				 $("#sentForwardCcAnchor").hide();
				 
			 }
			function parse (response) {   
			    $.each(response, function (idx, elem) {
			        if (elem.lastUpdatedDate && typeof elem.lastUpdatedDate === "string") {
			            elem.lastUpdatedDate = kendo.parseDate(elem.lastUpdatedDate, "yyyy-MM-ddTHH:mm:ss.fffZ");
			        }
			    });
			    return response;
			}  

			 function strip(html)
			 {
			 var tmp = document.createElement("DIV");
			 tmp.innerHTML = html;
			 return tmp.textContent || tmp.innerText;
			 }
			
			
			$("#sentGrid").on("click", ".k-grid-Clear_Filter", function(){
			    //custom actions
			    $("form.k-filter-menu button[type='reset']").slice().trigger("click");
			    first_array2 = [];
				 checkedIds = {};
			    var grid = $("#sentGrid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			});
			
			viewDialog = $( "#sent-view-dialog" ).dialog({
				 autoOpen: false,
				 height: 500,
				 width: 750,
				 modal: true,
				 buttons:{
					 Reply: function() {
						 var multiSelect = $("#sent-replyTo").data("kendoMultiSelect");
						 var multiSelectCc = $("#sent-cc").data("kendoMultiSelect");
						 $("#sent-subject").val("");
						 $("#sent-txtArea").val("");
						 multiSelect.value("");
						 multiSelectCc.value("");
						 
						 multiSelect.value("");
						 var entityGrid = $("#sentGrid").data("kendoGrid");       
		            	    var data = entityGrid.dataSource.data();
		            	    var totalNumber = data.length;
		            	    var toNames;
		            	    var j = 0;
		            	    for(var i = 0; i<totalNumber; i++) {
		            	       if( data[i].msg_id == id ){
		            	    	   toNames = data[i].toUser;
		            	    	   
		            	       }
		            	    }
		            	    var toName = toNames.split(",");
		            	    var toUser = toName[0].substring(3, (toName[0].length)-1);
		        	      	multiSelect.value(toUser);
							 $("#sentReplyCcDiv").hide();
							 $("#sentReplyCcAnchor").show();
							 $("#sentForwardCcDiv").hide();
							 $("#sentForwardCcAnchor").show();
							 replyDialog.dialog( "open" );
							
					 },
					 Forward: function() {
						 $("#sent-subject1").val("");
						 $("#sent-txtArea1").val("");
						 var multiSelect = $("#sent-forwardTo").data("kendoMultiSelect");
						 var multiSelectCc = $("#sent-cc1").data("kendoMultiSelect");
						 multiSelect.value("");
						 multiSelectCc.value("");
						 
						 var entityGrid = $("#sentGrid").data("kendoGrid");       
		            	    var data = entityGrid.dataSource.data();
		            	    var totalNumber = data.length;
		            	    var msgs;
		            	    var sub;
		            	    var j = 0;
		            	    msgs = "";
		            	    for(var i = 0; i<totalNumber; i++) {
		            	       if( data[i].msg_id == id ){
		            	    	   msgs = data[i].message;
		            	    	   sub = data[i].subject;
		            	    	   
		            	       }
		            	    }
					 $("#sent-subject1").val(sub);
					 $("#sent-txtArea1").val(msgs);
					 $("#sentReplyCcDiv").hide();
					 $("#sentReplyCcAnchor").show();
					 $("#sentForwardCcDiv").hide();
					 $("#sentForwardCcAnchor").show();
					 forwardDialog.dialog( "open" );
					 
					 },
					 Cancel: function() {
						 $.ajax({type:"POST",dataType:"text",url:"./messages/changeReadStatus/"+id }); 
						 first_array2 = [];
						 checkedIds = {};
						 var grid = $("#sentGrid").data("kendoGrid");
							grid.cancelRow();
							grid.dataSource.read();
							grid.refresh();
						 $( this ).dialog( "close" );
						 $("#showSelection3").hide();
							$("#moveDiv").hide();
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
					                       
					                       
					                    }); 
						 } 
				 }
			}); 
			forwardDialog = $( "#dialog-form-forward" ).dialog({
				 autoOpen: false,
				 height: 500,
				 width: 750,
				 modal: true,
				 buttons: {
					 "Forward": function() {
						 
						 var cc = sentCc1Required.val();
						 var names = sentForwardRequired.val();
						var sub = $("#sent-subject1").val();
						var msg = $("#sent-txtArea1").val();
						if( msg == "" )
							msg = "null";
						else{
						var fstStripMsg = strip(msg);
						var secondStripMsg = strip(fstStripMsg);
						msg = secondStripMsg;
						}
						if( cc == "" )
							cc = "null";
						if( names == "" || names == null ){
							$("#alertsBox").html("");
	       					$("#alertsBox").html("Error: To address is required");
							$("#alertsBox").dialog({
								modal : true,
								buttons : {
									"Close" : function() {
										$(this).dialog("close");
									}
								}
							});
						} 
						else if( sub == "" ){
							$("#alertsBox").html("");
	       					$("#alertsBox").html("Error: Subject is required");
							$("#alertsBox").dialog({
								modal : true,
								buttons : {
									"Close" : function() {
										$(this).dialog("close");
									}
								}
							});
						}
						
						else{
							  $.ajax({
									type : "POST",
									dataType:"text",
									 /* data: {
										 name:name,
										 cc:ccValue,
										 sub:sub,
										 msg:msg
									 }, */
									 url : "./messages/replyToSender/"+names+"/"+cc+"/"+sub+"/"+msg,
									success : function(response) {
										if( response == "SUCCESS"){
											$("#alertsBox").html("");
					       					$("#alertsBox").html("Message is successfully forwarded to the user");
											$("#alertsBox").dialog({
												modal : true,
												buttons : {
													"Close" : function() {
														$(this).dialog("close");
													}
												}
											});
											
										}
										if( response == "ERROR"){
											$("#alertsBox").html("");
					       					$("#alertsBox").html("Error: While sending this message");
											$("#alertsBox").dialog({
												modal : true,
												buttons : {
													"Close" : function() {
														$(this).dialog("close");
													}
												}
											});
											
										}
										$("#showSelection3").hide();
										$("#moveDiv").hide();
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
								                       
								                       
								                    }); 
								                    first_array2 = [];
								       			 checkedIds = {};
										var grid = $("#sentGrid").data("kendoGrid");
										grid.cancelRow();
										grid.dataSource.read();
										grid.refresh();
									}

								});   
							
						 $( this ).dialog( "close" );
						}
					 },
					 "Save to draft": function() {
						 var cc = sentCc1Required.val();
						 var names = sentForwardRequired.val();
						var sub = $("#sent-subject1").val();
						var msg = $("#sent-txtArea1").val();
						if( msg == "" )
							msg = "null";
						else{
						 var fstStripMsg = strip(msg);
						var secondStripMsg = strip(fstStripMsg); 
						msg = secondStripMsg; 
						}
						if( names == "" || names == null )
							names = "null";
						if( cc == "" )
							cc = "null";
						if( sub == "" )
							sub = "null";
							$.ajax({
								type : "POST",
								dataType:"text",
								 /* data: {
									 name:toValues,
									 cc:ccValues,
									 sub:sub,
									 msg:msg
								 }, */
								 url : "./messages/saveToDraft/"+names+"/"+cc+"/"+sub+"/"+msg,
								success : function(response) {
									if( response == "DRAFTSUCCESS"){
										$("#alertsBox").html("");
				       					$("#alertsBox").html("This message is successfully saved in draft");
										$("#alertsBox").dialog({
											modal : true,
											buttons : {
												"Close" : function() {
													$(this).dialog("close");
												}
											}
										});
										
									}
									if( response == "DRAFTERROR"){
										$("#alertsBox").html("");
				       					$("#alertsBox").html("Error: While saving this message in draft");
										$("#alertsBox").dialog({
											modal : true,
											buttons : {
												"Close" : function() {
													$(this).dialog("close");
												}
											}
										});
										
									}
									$("#showSelection3").hide();
									$("#moveDiv").hide();
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
							                       
							                       
							                    }); 
							                    first_array2 = [];
							       			 checkedIds = {};
									var grid = $("#sentGrid").data("kendoGrid");
									grid.cancelRow();
									grid.dataSource.read();
									grid.refresh();
								}
							});  
							 $( this ).dialog( "close" );
						 },
					 Cancel: function() {
					 $( this ).dialog( "close" );
					 }
					 }
			});
			replyDialog = $( "#dialog-form" ).dialog({
				 autoOpen: false,
				 height: 500,
				 width: 750,
				 modal: true,
				 buttons: {
					 "Send": function() {
						
						 var cc = sentCcRequired.val();
						 var names = sentReplyRequired.val();
						var sub = $("#sent-subject").val();
						var msg = $("#sent-txtArea").val();
						if( msg == "" )
							msg = "null";
						else{
						var fstStripMsg = strip(msg);
						var secondStripMsg = strip(fstStripMsg);
						msg = secondStripMsg;
						}
						if( cc == "" )
							cc = "null";
						
						if( names == "" || names == null ){
							$("#alertsBox").html("");
	       					$("#alertsBox").html("Error: To address is required");
							$("#alertsBox").dialog({
								modal : true,
								buttons : {
									"Close" : function() {
										$(this).dialog("close");
									}
								}
							});
						}
						else if( sub == "" ){
							$("#alertsBox").html("");
	       					$("#alertsBox").html("Error: Subject is required");
							$("#alertsBox").dialog({
								modal : true,
								buttons : {
									"Close" : function() {
										$(this).dialog("close");
									}
								}
							});
						}
						
						else{
							  $.ajax({
									type : "POST",
									dataType:"text",
									 /* data: {
										 name:name,
										 cc:ccValue,
										 sub:sub,
										 msg:secondStripMsg
									 }, */
									 url : "./messages/replyToSender/"+names+"/"+cc+"/"+sub+"/"+msg,
									success : function(response) {
										if( response == "SUCCESS"){
											$("#alertsBox").html("");
					       					$("#alertsBox").html("Message is successfully send to the user");
											$("#alertsBox").dialog({
												modal : true,
												buttons : {
													"Close" : function() {
														$(this).dialog("close");
													}
												}
											});
											
										}
										if( response == "ERROR"){
											$("#alertsBox").html("");
					       					$("#alertsBox").html("Error: While sending this message");
											$("#alertsBox").dialog({
												modal : true,
												buttons : {
													"Close" : function() {
														$(this).dialog("close");
													}
												}
											});
											
										}
										$("#showSelection3").hide();
										$("#moveDiv").hide();
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
								                       
								                       
								                    });
								                    first_array2 = [];
								       			 checkedIds = {};
										var grid = $("#sentGrid").data("kendoGrid");
										grid.cancelRow();
										grid.dataSource.read();
										grid.refresh();
									}

								});   
							
						 $( this ).dialog( "close" );
						}
					 },
					 "Save to draft": function() {
						 var cc = sentCcRequired.val();
						 var names = sentReplyRequired.val();
						var sub = $("#sent-subject").val();
						var msg = $("#sent-txtArea").val();
						if( msg == "" )
							msg = "null";
						else{
						var fstStripMsg = strip(msg);
						var secondStripMsg = strip(fstStripMsg);
						msg = secondStripMsg;
						}
						if( names == "" || names == null )
							names = "null";
						if( cc == "" )
							cc = "null";
						if( sub == "" )
							sub = "null";
						
							$.ajax({
								type : "POST",
								dataType:"text",
								 /* data: {
									 name:toValues,
									 cc:ccValues,
									 sub:sub,
									 msg:secondStripMsg
								 }, */
								 url : "./messages/saveToDraft/"+names+"/"+cc+"/"+sub+"/"+msg,
								success : function(response) {
									if( response == "DRAFTSUCCESS"){
										$("#alertsBox").html("");
				       					$("#alertsBox").html("This message is successfully saved in draft");
										$("#alertsBox").dialog({
											modal : true,
											buttons : {
												"Close" : function() {
													$(this).dialog("close");
												}
											}
										});
										
									}
									if( response == "DRAFTERROR"){
										$("#alertsBox").html("");
				       					$("#alertsBox").html("Error: While saving this message in draft");
										$("#alertsBox").dialog({
											modal : true,
											buttons : {
												"Close" : function() {
													$(this).dialog("close");
												}
											}
										});
										
									}
									$("#showSelection3").hide();
									$("#moveDiv").hide();
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
							                       
							                       
							                    }); 
							                    first_array2 = [];
							       			 checkedIds = {};
									var grid = $("#sentGrid").data("kendoGrid");
									grid.cancelRow();
									grid.dataSource.read();
									grid.refresh();
								}
							});  
							 $( this ).dialog( "close" );
						 },
					 Cancel: function() {
					 $( this ).dialog( "close" );
					 }
					 },
			 });
			
			function sentDataBound(e) {
				$("#showSelection3").hide();
				$("#sentReplyCcDiv").hide();
				 $("#sentReplyCcAnchor").show();
				 $("#sentForwardCcDiv").hide();
				 $("#sentForwardCcAnchor").show();
				  var grid = $("#sentGrid").data("kendoGrid");
	      		    var gridData = grid.dataSource.view();
	      		    
	      		  var totalNumber = gridData.length;
				    if( totalNumber == 0 ){
				    	$("#selectDropDown").hide();
				    	$("#moveDiv").hide();
				    	$("#moreButton").hide();
				   	 
				    } 
      				for(var i = 0; i < gridData.length;i++){
      					var currentUid = gridData[i].uid;
      					if (gridData[i].read_status == 0) {
      						var currentRow = grid.table.find("tr[data-uid='" + currentUid + "']");
      			            $(currentRow).addClass("unreadMsgColor");
      			        }
      					if (gridData[i].read_status == 1) {
      						var currentRows = grid.table.find("tr[data-uid='" + currentUid + "']");
      			            $(currentRows).addClass("readMsgColor");
      			        }
      				} 
				     }
			
			
			var first_array2 = new Array();
			var checkedIds = {};
			
			 $(document).ready(function () {
				 
				  $("#showSelection3").hide();
				  $("#sentReplyCcDiv").hide();
					 $("#sentReplyCcAnchor").show();
					 $("#sentForwardCcDiv").hide();
					 $("#sentForwardCcAnchor").show();
				  sentCcRequired = $("#sent-cc").kendoMultiSelect({
		               /*  placeholder: "Select names...", */
		                dataTextField: "loginnName",
		                dataValueField: "loginnName",
		                dataSource: {
		                    transport: {
		                        read: {
		                            url: "./messages/getAllLoginNames",
		                        }
		                    }
		                }
		               
		            });
					
					sentCc1Required = $("#sent-cc1").kendoMultiSelect({
			               /*  placeholder: "Select names...", */
			                dataTextField: "loginnName",
			                dataValueField: "loginnName",
			                dataSource: {
			                    transport: {
			                        read: {
			                            url: "./messages/getAllLoginNames",
			                        }
			                    }
			                }
			               
			            });
					sentReplyRequired = $("#sent-replyTo").kendoMultiSelect({
			               /*  placeholder: "Select names...", */
			                dataTextField: "loginnName",
			                dataValueField: "loginnName",
			                dataSource: {
			                    transport: {
			                        read: {
			                            url: "./messages/getAllLoginNames",
			                        }
			                    }
			                }
			               
			            });
					sentForwardRequired = $("#sent-forwardTo").kendoMultiSelect({
			               /*  placeholder: "Select names...", */
			                dataTextField: "loginnName",
			                dataValueField: "loginnName",
			                dataSource: {
			                    transport: {
			                        read: {
			                            url: "./messages/getAllLoginNames",
			                        }
			                    }
			                }
			               
			            });
					var data = [
					            { text: "Select", value: "select" },
		                        { text: "All", value: "all" },
		                        { text: "None", value: "none" },
		                        { text: "Read", value: "allRead" },
		                        { text: "Unread", value: "allUnread" }
		                    ];
		                  // create DropDownList from input HTML element
		                    $("#sentDropDown").kendoDropDownList({
		                        dataTextField: "text",
		                        dataValueField: "value",
		                        dataSource: data,
		                        index: 0,
		                        change:onChange
		                       
		                    }); 
	                 
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
	                       
	                       
	                    }); 
	                    var data2 = [
						            { text: "Move to", value: "moveto" },
			                        { text: "Trash", value: "trash" },
			                    ];
			                  // create DropDownList from input HTML element
			                    $("#moveToDropDown").kendoDropDownList({
			                        dataTextField: "text",
			                        dataValueField: "value",
			                        dataSource: data2,
			                        index: 0,
			                        change:onChangeMove,
			                        enable : "false"
			                       
			                    }); 
			 }); 
	 //$(".moveDropDown").show();
	 
	  $("#moveDiv").hide();
	 
	//onchange function for movetoDropdown
	 function onChangeMove() {
		 var moveValues = $("#moveToDropDown").val();
		 if( moveValues == "moveto" ){
       		
       	}
		 if( moveValues == "trash" ){
			 var agree=false;
			 agree = confirm("Are you really want to send this msg to trash..?");
			$.ajax({
				type : "POST",
				dataType:"text",
				url : "./messages/deleteMessage/"+first_array2+"/"+agree,
				success : function(response) {
					if( response == "SUCCESS" ){
						$("#alertsBox").html("");
       					$("#alertsBox").html("Messages are successfully moved to trash." );
						$("#alertsBox").dialog({
							modal : true,
							buttons : {
								"Close" : function() {
									$(this).dialog("close");
								}
							}
						});
						first_array2 = [];
						 checkedIds = {};
						var grid = $("#sentGrid").data("kendoGrid");
						grid.cancelRow();
						grid.dataSource.read();
						grid.refresh();
					}
					
					if( response == "FAIL" ){
						
						first_array2 = [];
						 checkedIds = {};
						var grid = $("#sentGrid").data("kendoGrid");
						grid.cancelRow();
						grid.dataSource.read();
						grid.refresh();
						
					}
					
				}
			});
			$("#showSelection3").hide();
			$("#moveDiv").hide();
			first_array2 = [];
			 checkedIds = {};
			var grid = $("#sentGrid").data("kendoGrid");
			grid.cancelRow();
			grid.dataSource.read();
			grid.refresh();
   		
   	}
		 /* if( moveValues == "inbox" ){
			 var status = "INBOX";
			 var agree=false;
			 agree = confirm("Are you really want to send this msg to inbox..?");
			$.ajax({
				type : "POST",
				url : "./messages/deleteMessage/"+first_array2+"/"+agree+"/"+status,
				success : function(response) {
					if( response == "SUCCESS" ){
						alert( "Messages are successfully moved to inbox." );
						checkedIds = {};
						var grid = $("#sentGrid").data("kendoGrid");
						grid.cancelRow();
						grid.dataSource.read();
						grid.refresh();
					}
					
					if( response == "FAIL" ){
						
						checkedIds = {};
						var grid = $("#sentGrid").data("kendoGrid");
						grid.cancelRow();
						grid.dataSource.read();
						grid.refresh();
						
					}
					
				}
			});
			$("#showSelection3").hide();
			$("#moveDiv").hide();
			var grid = $("#sentGrid").data("kendoGrid");
			grid.cancelRow();
			grid.dataSource.read();
			grid.refresh();
	       		
	       	} */
		
	}
	
	//onchange function for moreDropdown
	 function onChangeMore() {
		
		 var moreValues = $("#moreDropDown").val();
		 if( moreValues == "more" ){
   		
   	}
		 if( moreValues == "read" ){
			
			 var entityGrid = $("#sentGrid").data("kendoGrid");       
       	    var data = entityGrid.dataSource.data();
 				for(var i = 0; i < data.length;i++){
 					if( data[i].read_status == 0){
 						
 						$.ajax({type:"POST",dataType:"text",url:"./messages/updateReadStatusAsOne/"+data[i].msg_id }); 
 						
 					}
 				}
 				first_array2 = [];
				 checkedIds = {};
 				var grid = $("#sentGrid").data("kendoGrid");
				grid.cancelRow();
				grid.dataSource.read();
				grid.refresh();
				$("#showSelection3").hide();
				$("#moveDiv").hide();
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
		                       
		                       
		                    }); 
				
       	}
		 if( moreValues == "markRead" ){
			 
			 for(var i = 0; i<first_array2.length; i++){
				 $.ajax({type:"POST",dataType:"text",url:"./messages/updateReadStatusAsOne/"+first_array2[i]});
           	}
			 first_array2 = [];
			 checkedIds = {};
			 var grid = $("#sentGrid").data("kendoGrid");
				grid.cancelRow();
				grid.dataSource.read();
				grid.refresh();
				$("#showSelection3").hide();
				$("#moveDiv").hide();
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
		                       
		                       
		                    }); 
	}
	if( moreValues == "markUnread" ){
			 
			 for(var i = 0; i<first_array2.length; i++){
				 $.ajax({type:"POST",dataType:"text",url:"./messages/updateReadStatusAsZero/"+first_array2[i]});
           	}
			 first_array2 = [];
			 checkedIds = {};
			 var grid = $("#sentGrid").data("kendoGrid");
				grid.cancelRow();
				grid.dataSource.read();
				grid.refresh();
				$("#showSelection3").hide();
				$("#moveDiv").hide();
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
		                       
		                       
		                    }); 
	}
	}
				//onchange function for dropdown
				 function onChange() {
					
	            	 $("#showSelection3").hide();
	            	var values = $("#sentDropDown").val();
	            	if( values == "select" ){
	            		$("#moveDiv").hide();
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
				                       
				                       
				                    }); 
	            		  first_array2 = [];
	            		  $('input').prop('checked' , false);
	            		  $("#showSelection3").hide();
	            		  $("#moveDiv").hide();
		    				if( first_array2.length == 1 ){
								$("#replyDiv").show();
								$("#forwardDiv").show();
								$("#showSelection3").show();
								$("#moveDiv").show();
								
							}
							else if( first_array2.length > 1 ){
								$("#replyDiv").hide();
								$("#forwardDiv").hide();
								
							}
	            		
	            	}
	            	if( values == "all" ){
	            		
	            		if( first_array2.length == 1 ){
							$("#showSelection3").show();
							$("#moveDiv").show();
							
						}
						else if(first_array2.length > 1){
							$("#replyDiv").hide();
							$("#forwardDiv").hide();
							
						}
	            		var data1 = [
										{ text: "More", value: "more" },
							            { text: "Mark as read", value: "markRead" },
				                        { text: "Mark as unread", value: "markUnread" }
				                        
				                    ];
				                  // create DropDownList from input HTML element
				                    $("#moreDropDown").kendoDropDownList({
				                        dataTextField: "text",
				                        dataValueField: "value",
				                        dataSource: data1,
				                        index: 0,
				                        change: onChangeMore
				                       
				                       
				                    }); 
	            		var entityGrid = $("#sentGrid").data("kendoGrid");       
	            	    var data = entityGrid.dataSource.data();
	            	    var totalNumber = data.length;
	            	    first_array2 = [];
	            	     for(var i = 0; i<totalNumber; i++) {
	            	        var currentDataItem = data[i];
	            	       
	            	        if($.inArray( data[i].msg_id, first_array2) < 0 ){
	            	        	
	            	        	first_array2[i] = data[i].msg_id;
	            	        	 $('#checkStatus2_'+first_array2[i]).prop('checked' , true);
	            	        }
	            	        else{
	            	        	 $('#checkStatus2_'+data[i].msg_id).prop('checked' , true);
	            	        }
	            	       // $('#checkStatus2_'+currentDataItem.msg_id).prop('checked' , true);
	            	       // $('input').prop('checked' , true);
	            	    } 
	            	    

		            	    $("#showSelection3").show();
		    				$("#moveDiv").show();
		    				
		    				if( first_array2.length == 1 ){
								$("#showSelection3").show();
								$("#moveDiv").show();
								
							}
		    				else if(first_array2.length > 1){
								
							}
	            	    
	            	}
	            	
	            	if( values == "none" ){
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
				                       
				                       
				                    }); 
	            		
	            		var entityGrid = $("#sentGrid").data("kendoGrid");       
	            	    var data = entityGrid.dataSource.data();
	            	    var totalNumber = data.length;
	            	    first_array2 = [];
	            	    for(var i = 0; i<totalNumber; i++) {
	            	        var currentDataItem = data[i];
	            	        $('input').prop('checked' , false);
	            	        var removeItem = data[i].msg_id;
						 	first_array2 = jQuery.grep(first_array2, function(value) {
	  						 return value != removeItem;
	 						});  
						 	
	            	    }
	            	    
	            	    $("#showSelection3").hide();
	            	}
	            	if( values == "allRead" ){
	            		var data1 = [
										{ text: "More", value: "more" },
							            { text: "Mark as unread", value: "markUnread" }
				                        
				                    ];
				                  // create DropDownList from input HTML element
				                    $("#moreDropDown").kendoDropDownList({
				                        dataTextField: "text",
				                        dataValueField: "value",
				                        dataSource: data1,
				                        index: 0,
				                        change: onChangeMore
				                       
				                       
				                    }); 
	            		var entityGrid = $("#sentGrid").data("kendoGrid");       
	            	    var data = entityGrid.dataSource.data();
	            	    var totalNumber = data.length;
	            	    first_array2 = [];
	            	    var j = 0;
	            	    for(var i = 0; i<totalNumber; i++) {
	            	        var currentDataItem = data[i];
	            	        var rowId = data[i].msg_id;
	            	        if(data[i].read_status == "1")
	                        {
	                       
	                        if($.inArray( data[i].msg_id, first_array2) < 0 ){
	            	        	first_array2[j] = data[i].msg_id;
	            	        	j++;
	            	        	 $('#checkStatus2_'+data[i].msg_id).prop('checked' , true);
	            	        	
	            	        }
	            	        else{
	            	        	 $('#checkStatus2_'+data[i].msg_id).prop('checked' , true);
	            	        }
	                        
	                        }
	            	        else{
	            	        	$('#checkStatus2_'+data[i].msg_id).prop('checked' , false);
	            	        }
	            	       
	            	    }
	            	    $("#showSelection3").show();
	            	}
	            	if( values == "allUnread" ){
	            		var data1 = [
										{ text: "More", value: "more" },
				                        { text: "Mark as read", value: "markRead" }
				                        
				                    ];
				                  // create DropDownList from input HTML element
				                    $("#moreDropDown").kendoDropDownList({
				                        dataTextField: "text",
				                        dataValueField: "value",
				                        dataSource: data1,
				                        index: 0,
				                        change: onChangeMore
				                       
				                       
				                    }); 
	            		var entityGrid = $("#sentGrid").data("kendoGrid");       
	            	    var data = entityGrid.dataSource.data();
	            	    var totalNumber = data.length;
	            	    first_array2 = [];
	            	    var j = 0;
	            	    for(var i = 0; i<totalNumber; i++) {
	            	        var currentDataItem = data[i];
	            	        var rowId = data[i].msg_id;
	            	        if(data[i].read_status == "0")
	                        {
		                        
		                        if($.inArray( data[i].msg_id, first_array2) < 0 ){
		            	        	first_array2[j] = data[i].msg_id;
		            	        	j++;
		            	        	 $('#checkStatus2_'+data[i].msg_id).prop('checked' , true);
		            	        	 
		            	        }
		            	        else{
		            	        	 $('#checkStatus2_'+data[i].msg_id).prop('checked' , true);
		            	        }
	                        }
	            	        else{
	            	        	$('#checkStatus2_'+data[i].msg_id).prop('checked' , false);
	            	        }
	            	       
	            	    }
	            	    $("#showSelection3").show();
	            	}
	            }
				
				//onclick function for checkbox
				$("#sentGrid").on("click", "[class^=checkbox]", function (e)
						{
							var data2 = [
						            { text: "Move to", value: "moveto" },
			                        { text: "Trash", value: "trash" },
			                    ];
			                  // create DropDownList from input HTML element
			                    $("#moveToDropDown").kendoDropDownList({
			                        dataTextField: "text",
			                        dataValueField: "value",
			                        dataSource: data2,
			                        index: 0,
			                        change:onChangeMove
			                       
			                    }); 
							var checked = this.checked,
							row = $(this).closest("tr"),
							grid = $("#sentGrid").data("kendoGrid"),
							dataItem = grid.dataItem(row);
							checkedIds[dataItem.id] = checked;
							if (checked) {
							
							if(first_array2.length == 0){
								first_array2[first_array2.length] = dataItem.msg_id ;
								
							}
							else{
								
								if($.inArray(dataItem.msg_id, first_array2) < 0 ){
									first_array2[first_array2.length] = dataItem.msg_id;
									
								}
								
							}
							var data1 = [
											{ text: "More", value: "more" },
								            { text: "Mark as read", value: "markRead" },
					                        { text: "Mark as unread", value: "markUnread" }
					                        
					                    ];
					                  // create DropDownList from input HTML element
					                    $("#moreDropDown").kendoDropDownList({
					                        dataTextField: "text",
					                        dataValueField: "value",
					                        dataSource: data1,
					                        index: 0,
					                        change: onChangeMore
					                       
					                       
					                    }); 
							//enable delete button 
							$("#showSelection3").show();
							$("#moveDiv").show();
							//-select the row
							row.addClass("k-state-selected");
						} else {
							
							for(var i = 0; i<first_array2.length; i++){
								 $('#checkStatus2_'+first_array2[i]).attr('checked' , true);
								 
								if(first_array2[i] == dataItem.msg_id){
									
									 $('#checkStatus2_'+dataItem.msg_id).attr('checked' , false);
			    					 	var removeItem = dataItem.msg_id;
									 	first_array2 = jQuery.grep(first_array2, function(value) {
			      						 return value != removeItem;
			     						});  
			     				
									//row.removeClass("k-state-selected");
									
								}
								else{
									
									$('#checkStatus2_'+first_array2[i]).attr('checked' , true);
									
								}
								
							}
							
							//hide delete button
							if( first_array2.length == 0 ){
								$("#showSelection3").hide();
								$("#showSelection4").hide();
								
								var grid = $("#sentGrid").data("kendoGrid");
								/* grid.cancelRow();
								grid.dataSource.read();
								grid.refresh(); */
								
							}
							else if( first_array2.length > 0 ){
								$("#showSelection3").show();
								$("#showSelection4").show();
								
								var grid = $("#sentGrid").data("kendoGrid");
								/* grid.cancelRow();
								grid.dataSource.read();
								grid.refresh(); */
								
							}
							
							
						}
						});
				
			//onclick function for checkbox
			$("#sentGrid").on("click", "[class^=checkbox2]", function (e)
					{
				 var data2 = [
					            { text: "Move to", value: "moveto" },
		                        { text: "Trash", value: "trash" },
		                    ];
		                  // create DropDownList from input HTML element
		                    $("#moveToDropDown").kendoDropDownList({
		                        dataTextField: "text",
		                        dataValueField: "value",
		                        dataSource: data2,
		                        index: 0,
		                        change:onChangeMove
		                       
		                    }); 
						var checked = this.checked,
						row = $(this).closest("tr"),
						grid = $("#sentGrid").data("kendoGrid"),
						dataItem = grid.dataItem(row);
						checkedIds[dataItem.id] = checked;
						if (checked) {
						
						if(first_array2.length == 0){
							first_array2[first_array2.length] = dataItem.msg_id ;
							
						}
						else{
							
							if($.inArray(dataItem.msg_id, first_array2) < 0 ){
								first_array2[first_array2.length] = dataItem.msg_id;
								
							}
							
						}
						
						//enable delete button 
						$("#showSelection3").show();
						//-select the row
						row.addClass("k-state-selected");
					} else {
						
						for(var i = 0; i<first_array2.length; i++){
							 $('#checkStatus2_'+first_array2[i]).attr('checked' , true);
							 
							if(first_array2[i] == dataItem.msg_id){
								
								 $('#checkStatus2_'+dataItem.msg_id).attr('checked' , false);
		    					 	var removeItem = dataItem.msg_id;
								 	first_array2 = jQuery.grep(first_array2, function(value) {
		      						 return value != removeItem;
		     						});  
		     				
								//row.removeClass("k-state-selected");
								
							}
							else{
								
								$('#checkStatus2_'+first_array2[i]).attr('checked' , true);
								
							}
							
						}
						
						//hide delete button
						if( first_array2.length == 0 ){

							$("#showSelection3").hide();
							$("#moveDiv").hide();
							
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
					                       
					                       
					                    }); 
							
							var grid = $("#sentGrid").data("kendoGrid");
							
						}
						else if( first_array2.length > 0 ){

							$("#showSelection3").show();
							$("#moveDiv").show();
							
							var data1 = [
											{ text: "More", value: "more" },
								            { text: "Mark as read", value: "markRead" },
					                        { text: "Mark as unread", value: "markUnread" }
					                        
					                    ];
					                  // create DropDownList from input HTML element
					                    $("#moreDropDown").kendoDropDownList({
					                        dataTextField: "text",
					                        dataValueField: "value",
					                        dataSource: data1,
					                        index: 0,
					                        change: onChangeMore
					                       
					                       
					                    }); 
							
							var grid = $("#sentGrid").data("kendoGrid");
							
						}
					}
						if( first_array2.length == 1 ){
							$("#showSelection3").show();
							$("#moveDiv").show();
							
						}
						else if(first_array2.length > 1){
							
						}
					});
			
			 function deleteIds2()
				{
					
					var agree=false;
					 agree = confirm("Are you really want to delete this msg..?");
					$.ajax({
						type : "POST",
						dataType:"text",
						url : "./messages/deleteMessage/"+first_array2+"/"+agree,
						success : function(response) {
							if( response == "SUCCESS" ){
								$("#alertsBox").html("");
		       					$("#alertsBox").html("Messages are successfully moved to trash."  );
								$("#alertsBox").dialog({
									modal : true,
									buttons : {
										"Close" : function() {
											$(this).dialog("close");
										}
									}
								});
								first_array2 = [];
								 checkedIds = {};
								
							}
							
							if( response == "FAIL" ){
								$("#alertsBox").html("");
		       					$("#alertsBox").html("Error: While deleting message"  );
								$("#alertsBox").dialog({
									modal : true,
									buttons : {
										"Close" : function() {
											$(this).dialog("close");
										}
									}
								});
								first_array2 = [];
								 checkedIds = {};
								
							}
							var grid = $("#sentGrid").data("kendoGrid");
							grid.cancelRow();
							grid.dataSource.read();
							grid.refresh();
							
						}
					});
					$("#showSelection3").hide();
					$("#moveDiv").hide();
					first_array2 = [];
					 checkedIds = {};
					 
					var grid = $("#sentGrid").data("kendoGrid");
					grid.cancelRow();
					grid.dataSource.read();
					grid.refresh();
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
			                       
			                       
			                    }); 
					
					
				}
			</script>
    </div>
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
.sentSubjectField{
font-weight: inherit;
font-size:26px;
color: #222222;
}
.sentToUserField{
font-weight: inherit;
font-size:20px;
color: #222222;
}
#sent-viewTxtArea{
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
</style>

<style>
#showSelection {
	size: 10, width:5px, height:10px, border:2px
}
.unreadMsgColor {
	/*  color: #777;  */
	 font-size: 13px; 
	font-weight: bold;
}

.readMsgColor {
	/* background-color:#E8E8E8 ; */
	color: #777;
	font-size: 13px;
}
#sentReplyCcAnchor{
   /*  text-decoration: underline; */
    border-bottom:1px solid blue;
}
#sentForwardCcAnchor{
   /*  text-decoration: underline; */
    border-bottom:1px solid blue;
}

#acc{
   /*  text-decoration: underline; */
    border-bottom:1px solid blue;
}
#acc2{
   /*  text-decoration: underline; */
    border-bottom:1px solid blue;
}
</style>

    
    
    