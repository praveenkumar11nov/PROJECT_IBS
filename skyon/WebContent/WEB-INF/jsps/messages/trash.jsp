<%@include file="/common/taglibs.jsp"%>
<%@include file="messagesLink.jsp"%>

<c:url value="/messages/trashMessages" var="readTrashMailsUrl" />
<c:url value="/messages/viewTrashMail" var="viewTrashMailUrl" />

<c:url value="/messages/saveMessages" var="saveMessagesUrl" />

<c:url value="/messages/getUserName" var="getUserName" />
<c:url value="/messages/getSelectionValue" var="getSelectionValueUrl" />

<script>
var id;
var trash_viewDialog;
var trash_replyDialog;
</script>

<div id="wrapper" class="wrapper">

    <div id="rightcolumn">
      <div id="selectDropDown" class="wrapper">		
        <kendo:dropDownList name="trashDropDown" dataTextField="text"
						dataValueField="value" >
						<kendo:dataSource></kendo:dataSource>
					</kendo:dropDownList>
					</div>
					<div id="deleteButton" class="wrapper">
					<a href="#" id="showSelection" onclick="deleteIds()"><img title="Delete forever" alt="" src="./resources/images/deleteIcon.jpg" width="20px"></a>
       
					
	</div>	
    <div class="wrapper" id="moreButton">
  <kendo:dropDownList name="moreDropDown" dataTextField="text"
						dataValueField="value">
						<kendo:dataSource></kendo:dataSource>
					</kendo:dropDownList>
    </div>
    <div id="gridDiv">
    
<div id="view-dialog" title="View Message"> 
<form>
<fieldset>
<div id="subjectField" class="subjectField"></div><br/>
<!-- <div id="replyAndForward" align="center">Click here to <span id="viewReply" tabindex="0" role="link" class="ams amt">Reply </span>or <span id="viewForward" tabindex="0" role="link" class="ams amt" >Forward </span></div><br/> -->
Message: <textarea id="viewTxtArea" rows="15" cols="0" name="viewTxtArea" disabled="disabled" ></textarea>
</fieldset>
</form>
</div>
     <kendo:grid name="trashGrid" height="430px" dataBound="trashDataBound" pageable="true" sortable="true">
     <kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" >
     <kendo:grid-pageable-messages itemsPerPage="Messages per page" empty="No Messages to display" refresh="Refresh all the Trash Messages" 
			display="{0} - {1} of {2} Messages" first="Go to the first page of Trash" last="Go to the last page of Trash" next="Go to the Trash"
			previous="Go to the previous page of Trash"/>
     </kendo:grid-pageable>
						<kendo:grid-editable mode="popup"
							confirmation="Are you sure you want to delete this message?" />
						<kendo:grid-toolbar>
						</kendo:grid-toolbar>
						<kendo:grid-columns>
							<kendo:grid-column title="Message Id" field="msg_id" width="70px"
								hidden="true" />
								<kendo:grid-column title="" field=""
								template="<input type='checkbox' id='checkStatus4_#=data.msg_id#' class='checkbox4' />"
								width="10px" />
							<kendo:grid-column title="Subject : " field="subject"
								width="70px" />
							<kendo:grid-column title="Message :" field="message"
								width="70px" />
								<kendo:grid-column title="Date" field="lastUpdatedDate" format="{0:dd/MM/yyyy hh:mm tt}" width="50px" filterable="false"/>
							<kendo:grid-column title="&nbsp;" width="50px">
								<<kendo:grid-column-command>
									<kendo:grid-column-commandItem name="viewDetails" text="View Details">
                        <kendo:grid-column-commandItem-click>
                            <script>
                            function showDetails(e) {
                            	
                                 var dataItem = this.dataItem($(e.currentTarget).closest("tr"));
                                // $('div.subjectField').text( "Subject: "+ dataItem.subject);
                                 id = dataItem.msg_id;
                                // $("#viewTxtArea").text(dataItem.message);
                                 var sub = dataItem.subject;
                                 if( sub == "(no subject)")
                                	 $('div.subjectField').text( "Subject: ");
                                 else
                                	 $('div.subjectField').text( "Subject: "+ dataItem.subject);
                                 var msg = dataItem.message;
            						 if( dataItem.message == "(no message)" )
                                  	 $("#viewTxtArea").val("");
                                   else
                              	 $("#viewTxtArea").val(msg);
            						 
                                 //$(".ui-icon-closethick").hide();
                                 trash_viewDialog.dialog( "open" );
                                 
                                 $(".ui-icon-closethick").click(function(){
                                	 $.ajax({type:"POST",dataType:"text",url:"./messages/changeReadStatus/"+id }); 
                                	 first_array = [];
                        			 checkedIds = {};
                                	 var grid = $("#trashGrid").data("kendoGrid");
    									grid.cancelRow();
    									grid.dataSource.read();
    									grid.refresh();
    									
    									$("#showSelection").hide();
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
								<kendo:dataSource-transport-read url="${readTrashMailsUrl}"
									dataType="json" type="POST" contentType="application/json" />
								<kendo:dataSource-transport-update url="${viewTrashMailUrl}"
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
											type="number"  editable="false" />
										<kendo:dataSource-schema-model-field name="subject"
											type="string" editable="false">
										</kendo:dataSource-schema-model-field>
										<kendo:dataSource-schema-model-field name="message"
											type="string" editable="false">
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
					</div>
					<div id="alertsBox" title="Alert"></div>
					
  
    <script>
    function strip(html)
	 {
	 var tmp = document.createElement("DIV");
	 tmp.innerHTML = html;
	 return tmp.textContent || tmp.innerText;
	 }
    function parse (response) {   
        $.each(response, function (idx, elem) {
            if (elem.lastUpdatedDate && typeof elem.lastUpdatedDate === "string") {
                elem.lastUpdatedDate = kendo.parseDate(elem.lastUpdatedDate, "yyyy-MM-ddTHH:mm:ss.fffZ");
            }
        });
        return response;
    }  
    trash_viewDialog = $( "#view-dialog" ).dialog({
		 autoOpen: false,
		 height: 500,
		 width: 750,
		 modal: true,
		 buttons:{
			 
			 Cancel: function() {
				 $.ajax({type:"POST",dataType:"text",url:"./messages/changeReadStatus/"+id }); 
				 first_array = [];
				 checkedIds = {};
				 var grid = $("#trashGrid").data("kendoGrid");
					grid.cancelRow();
					grid.dataSource.read();
					grid.refresh();
				 $( this ).dialog( "close" );
				 $("#showSelection").hide();
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
		
    function trashDataBound(e) {
    	
    	//$( "#view-dialog" ).dialog( "close" );
    	
		$("#showSelection").hide();

		  var grid = $("#trashGrid").data("kendoGrid");
  		    var gridData = grid.dataSource.view();
  		    
  		  var totalNumber = gridData.length;
		    if( totalNumber == 0 ){
		    	$("#selectDropDown").hide();
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
    var first_array = new Array();
			var checkedIds = {};
			$(document).ready(function () {
				$("#showSelection").hide();
				 first_array = [];
    			 checkedIds = {};
			
				var data = [
				            { text: "Select", value: "select" },
	                        { text: "All", value: "all" },
	                        { text: "None", value: "none" },
	                        { text: "Read", value: "allRead" },
	                        { text: "Unread", value: "allUnread" }
	                    ];
	                  // create DropDownList from input HTML element
	                    $("#trashDropDown").kendoDropDownList({
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
	                 
	                });
			//onchange function for moreDropdown
			 function onChangeMore() {
				
				 var moreValues = $("#moreDropDown").val();
				 if( moreValues == "more" ){
           		
           	}
				 if( moreValues == "read" ){
					 var entityGrid = $("#trashGrid").data("kendoGrid");       
	            	    var data = entityGrid.dataSource.data();
	      				for(var i = 0; i < data.length;i++){
	      					if( data[i].read_status == 0){
	      						
	      						
	      						$.ajax({type:"POST",dataType:"text",url:"./messages/updateReadStatusAsOne/"+data[i].msg_id }); 
	      						 first_array = [];
	      						 checkedIds = {};
	      						var grid = $("#trashGrid").data("kendoGrid");
									grid.cancelRow();
									grid.dataSource.read();
									grid.refresh();
									$("#showSelection").hide();
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
					 
	            	}
				 if( moreValues == "markRead" ){
				
					 for(var i = 0; i<first_array.length; i++){
						 $.ajax({type:"POST",dataType:"text",url:"./messages/updateReadStatusAsOne/"+first_array[i]});
		            	}
					 first_array = [];
					 checkedIds = {};
					 var grid = $("#trashGrid").data("kendoGrid");
						grid.cancelRow();
						grid.dataSource.read();
						grid.refresh();
						$("#showSelection").hide();
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
				
					 for(var i = 0; i<first_array.length; i++){
						 $.ajax({type:"POST",dataType:"text",url:"./messages/updateReadStatusAsZero/"+first_array[i]});
		            	}
					 first_array = [];
					 checkedIds = {};
					 var grid = $("#trashGrid").data("kendoGrid");
						grid.cancelRow();
						grid.dataSource.read();
						grid.refresh();
						$("#showSelection").hide();
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
				
           	 $("#showSelection").hide();
           	
           	var values = $("#trashDropDown").val();
           	if( values == "select" ){
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
        		  first_array = [];
        		  $('input').prop('checked' , false);
        		  $("#showSelection").hide();
    				
    				if( first_array.length == 1 ){
						
						$("#showSelection").show();						
					}
					else if( first_array.length > 1 ){
						
					}
        		
        	}
           	if( values == "all" ){
        		
				if( first_array.length == 1 ){
					
					$("#showSelection").show();
					
				}
				else if(first_array.length > 1){
					
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
        		var entityGrid = $("#trashGrid").data("kendoGrid");       
        	    var data = entityGrid.dataSource.data();
        	    var totalNumber = data.length;
        	    first_array = [];
        	     for(var i = 0; i<totalNumber; i++) {
        	        var currentDataItem = data[i];
        	       
        	        if($.inArray( data[i].msg_id, first_array) < 0 ){
        	        	
        	        	first_array[i] = data[i].msg_id;
        	        	first_array[i].checked = true;
        	        	 $('#checkStatus4_'+first_array[i]).prop('checked' , true);
        	        }
        	        else{
        	        	 $('#checkStatus4_'+data[i].msg_id).prop('checked' , true);
        	        }
        	       // $('#checkStatus1_'+currentDataItem.msg_id).prop('checked' , true);
        	       // $('input').prop('checked' , true);
        	    } 
        	    
        	    $("#showSelection").show();
				
				if( first_array.length == 1 ){
					$("#showSelection").show();
					
				}
				else if(first_array.length > 1){
					
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
        		var entityGrid = $("#trashGrid").data("kendoGrid");       
        	    var data = entityGrid.dataSource.data();
        	    var totalNumber = data.length;
        	    first_array = [];
        	    for(var i = 0; i<totalNumber; i++) {
        	        var currentDataItem = data[i];
        	        $('input').prop('checked' , false);
        	        var removeItem = data[i].msg_id;
				 	first_array = jQuery.grep(first_array, function(value) {
						 return value != removeItem;
						});  
				 	
        	    }
        	    
        	    $("#showSelection").hide();
				
				if( first_array.length == 1 ){
					
					$("#showSelection").show();
					
				}
				else if(first_array.length > 1){
					
				}
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
        		
        		var entityGrid = $("#trashGrid").data("kendoGrid");       
        	    var data = entityGrid.dataSource.data();
        	    var totalNumber = data.length;
        	    first_array = [];
        	    var j = 0;
        	    for(var i = 0; i<totalNumber; i++) {
        	        var currentDataItem = data[i];
        	        var rowId = data[i].msg_id;
        	        if(data[i].read_status == "1")
                    {
                   
                    if($.inArray( data[i].msg_id, first_array) < 0 ){
        	        	first_array[j] = data[i].msg_id;
        	        	j++;
        	        	 $('#checkStatus4_'+data[i].msg_id).prop('checked' , true);
        	        	
        	        }
        	        else{
        	        	 $('#checkStatus4_'+data[i].msg_id).prop('checked' , true);
        	        }
                    
                    }
        	        else{
        	        	$('#checkStatus4_'+data[i].msg_id).prop('checked' , false);
        	        }
        	       
        	    }
        	    $("#showSelection").show();
				$("#moveDiv").show();
				
				if( first_array.length == 1 ){
					
					$("#showSelection").show();
					
				}
				else if(first_array.length > 1){
					
				}
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
        		var entityGrid = $("#trashGrid").data("kendoGrid");       
        	    var data = entityGrid.dataSource.data();
        	    var totalNumber = data.length;
        	    first_array = [];
        	    var j = 0;
        	    for(var i = 0; i<totalNumber; i++) {
        	        var currentDataItem = data[i];
        	        var rowId = data[i].msg_id;
        	        if(data[i].read_status == "0")
                    {
                        
                        if($.inArray( data[i].msg_id, first_array) < 0 ){
            	        	first_array[j] = data[i].msg_id;
            	        	j++;
            	        	 $('#checkStatus4_'+data[i].msg_id).prop('checked' , true);
            	        	 
            	        }
            	        else{
            	        	 $('#checkStatu4_'+data[i].msg_id).prop('checked' , true);
            	        }
                    }
        	        else{
        	        	$('#checkStatus4_'+data[i].msg_id).prop('checked' , false);
        	        }
        	       
        	    }
        	    $("#showSelection").show();
				if( first_array.length == 1 ){
					
					$("#showSelection").show();
					
				}
				else if(first_array.length > 1){
					
				}
        	}
			}
			//onclick function for checkbox
				$("#trashGrid").on("click", "[class^=checkbox4]", function (e)
						{
					
							var checked = this.checked,
							row = $(this).closest("tr"),
							grid = $("#trashGrid").data("kendoGrid"),
							dataItem = grid.dataItem(row);
							checkedIds[dataItem.id] = checked;
							if (checked) {
							
							if(first_array.length == 0){
								first_array[first_array.length] = dataItem.msg_id ;
								
							}
							else{
								
								if($.inArray(dataItem.msg_id, first_array) < 0 ){
									first_array[first_array.length] = dataItem.msg_id;
									
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
							$("#showSelection").show();
							//-select the row
							row.addClass("k-state-selected");
						} else {
							
							for(var i = 0; i<first_array.length; i++){
								 $('#checkStatus4_'+first_array[i]).attr('checked' , true);
								 
								if(first_array[i] == dataItem.msg_id){
									
									 $('#checkStatus4_'+dataItem.msg_id).attr('checked' , false);
			    					 	var removeItem = dataItem.msg_id;
									 	first_array = jQuery.grep(first_array, function(value) {
			      						 return value != removeItem;
			     						});  
			     				
									//row.removeClass("k-state-selected");
									
								}
								else{
									
									$('#checkStatus4_'+first_array[i]).attr('checked' , true);
									
								}
								
							}
							
							//hide delete button
							if( first_array.length == 0 ){
								
								$("#showSelection").hide();
								
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
								
								var grid = $("#trashGrid").data("kendoGrid");
								/* grid.cancelRow();
								grid.dataSource.read();
								grid.refresh(); */
								
							}
							else if( first_array.length > 0 ){
								
								$("#showSelection").show();
								
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
								
								var grid = $("#trashGrid").data("kendoGrid");
								/* grid.cancelRow();
								grid.dataSource.read();
								grid.refresh(); */
								
							}
						}
							if( first_array.length == 1 ){
								
								$("#showSelection").show();
								
							}
							else if(first_array.length > 1){
								
							}
						});
				
				 function deleteIds()
					{
						
						var agree=false;
						 agree = confirm("Are you sure you want to delete this message?");
						$.ajax({
							type : "POST",
							dataType:"text",
							url : "./messages/deleteTrashMessages/"+first_array+"/"+agree,
							success : function(response) {
								if( response == "SUCCESS" ){
									$("#alertsBox").html("");
			       					$("#alertsBox").html("Messages are successfully deleted"  );
									$("#alertsBox").dialog({
										modal : true,
										buttons : {
											"Close" : function() {
												$(this).dialog("close");
											}
										}
									});
									 first_array = [];
									 checkedIds = {};
									var grid = $("#trashGrid").data("kendoGrid");
									grid.cancelRow();
									grid.dataSource.read();
									grid.refresh();
									
								}
								
								if( response == "FAIL" ){
									
									 first_array = [];
									 checkedIds = {};
									var grid = $("#trashGrid").data("kendoGrid");
									grid.cancelRow();
									grid.dataSource.read();
									grid.refresh();
									
								}
								
							}
						});
						 first_array = [];
						 checkedIds = {};
						$("#showSelection").hide();
						var grid = $("#trashGrid").data("kendoGrid");
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
				// $('.ui-dialog-content').dialog('close');
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
.subjectField{
font-weight: inherit;
font-size:26px;
color: #222222;
}
.fromUserField{
font-weight: inherit;
font-size:20px;
color: #222222;
}
#viewTxtArea{
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
</style>