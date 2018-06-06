<%@include file="/common/taglibs.jsp"%>

	<c:url value="/photo/create" var="createUrl" />
	<c:url value="/photo/read" var="readUrl" />
	<c:url value="/photo/update" var="updateUrl" />
	<c:url value="/photo/gallerydelete" var="deleteUrl" />
	<c:url value="/photo/photoGallery" var="readUrlPhotoGallery"/>
	<c:url value="/photo/deleteImage" var="deleteImage"/>
	
	<c:url value="/faq/Filter" var="faqFilter" />
	
	<kendo:grid name="grid" detailTemplate="newTicketOpenTemplate" pageable="true" edit="faqEvent" selectable="true" remove="faqremove" change="onChangeServicePoint" resizable="true" groupable="true"  sortable="true" filterable="true" scrollable="true" height="430px" >
		<kendo:grid-editable  mode="popup" confirmation="Are You Sure? you want delete the Record?" />
		<%-- <kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add FAQ Details" />
			<kendo:grid-toolbarItem name="clear_filter" text="Clear Filter"/>
			
		</kendo:grid-toolbar> --%>
		<kendo:grid-toolbarTemplate>
			
			<div class="toolbar">									
				<a class="k-button k-button-icontext k-grid-add" href="#">
		            <span class="k-icon k-add"></span>
		            Add Event Details
	        	</a>
				<a class='k-button' href='\\#' onclick="clearFilterNotification()"><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>
				
			</div>  	
				
    	</kendo:grid-toolbarTemplate>	
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" ></kendo:grid-pageable>
		
		<kendo:grid-filterable extra="false"/>
		
		<kendo:grid-columns>
			
			<%-- <kendo:grid-column title="FAQ Type" editor="faqDropDownEditor" field="faqtype" width="100px">
			
			
			
			<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function DesignationNameFilter(element) {
								element.kendoAutoComplete({
									dataSource : {
										transport : {
											read : "${faqFilter}"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column> --%>
      	  	<%-- <kendo:grid-column title="" field="peId" width="110px" hidden="true" /> --%>
			<kendo:grid-column title="Event &nbsp;Name" field="eventName" width="100px" filterable="false"/>
			
			<kendo:grid-column title="Event Description" field="eventDesc" editor="eventDescription"  width="100px" filterable="false"/>
			
			<kendo:grid-column title=" " width="80px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit"/>
					<kendo:grid-column-commandItem name="destroy"  text="Delete"/>
				</kendo:grid-column-command>
			</kendo:grid-column>
			<kendo:grid-column title=" " width="80px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="Upload Images" click="uploadDocument" className="k-grid-upload" />
				    <%-- <kendo:grid-column-commandItem name="View" click="downloadFile"/> --%>
				</kendo:grid-column-command>
			</kendo:grid-column>
			
		</kendo:grid-columns>
		<kendo:dataSource pageSize="20" requestEnd="onRequestEnd" requestStart="onRequestStart">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-create url="${createUrl}" dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-update url="${updateUrl}" dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-destroy url="${deleteUrl}" dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-parameterMap>
					<script>
						function parameterMap(options, type) {
							return JSON.stringify(options);
						}
					</script>
				</kendo:dataSource-transport-parameterMap>
			</kendo:dataSource-transport>
			
			<kendo:dataSource-schema>
				<kendo:dataSource-schema-model id="peId">
					<kendo:dataSource-schema-model-fields>
					
						<kendo:dataSource-schema-model-field name="peId" editable="false"/>	
						<kendo:dataSource-schema-model-field name="eventName"/>							
						<kendo:dataSource-schema-model-field name="eventDesc"/>							
						
					</kendo:dataSource-schema-model-fields>
					
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
	</kendo:grid>
	<kendo:grid-detailTemplate id="newTicketOpenTemplate" >
		<kendo:tabStrip name="tabStrip_#=peId#">
		<kendo:tabStrip-animation>
			</kendo:tabStrip-animation>
			<kendo:tabStrip-items>

				<kendo:tabStrip-item selected="true" text="Photo Gallery">
					<kendo:tabStrip-item-content>
						<div class='wethear' style="width: 50%;">
							<br />
							<kendo:grid name="subgrid_#=peId#"
								pageable="true" resizable="true" sortable="true"
								reorderable="true" selectable="true" scrollable="true" editable="true" change="changePhoto"  >
								<kendo:grid-pageable pageSize="10"></kendo:grid-pageable>
								<kendo:grid-filterable extra="false">
									<kendo:grid-filterable-operators>
										<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains"/>
				                        <kendo:grid-filterable-operators-date gt="Is after" lt="Is before"/>
									</kendo:grid-filterable-operators>
								</kendo:grid-filterable>
								<kendo:grid-editable mode="popup"
									confirmation="Are sure you want to delete this item ?" />								
								<kendo:grid-columns>
								
								< <kendo:grid-column title="pgId"  field="pgId"  width="140px" hidden="true"/> 
								 <kendo:grid-column title="&nbsp;" width="175px" >
							            	<kendo:grid-column-command>
							            	<kendo:grid-column-commandItem name="View Image" click="downloadImage1"/>
							            	</kendo:grid-column-command>
							     </kendo:grid-column>
								<%-- <kendo:grid-column title="Picture" field="image"  width="140px" template ="<img src='./photo/getImage/#=subPGId#'  width='80px' height='80px'/></span>"/>  --%>
           						 <%-- <kendo:grid-column title="Image" field="image" template ="<span onclick='clickToUploadImage()' title='Click to Upload Image' ></span>"	 width="94px" >
				</kendo:grid-column> --%>
           						 <kendo:grid-column title=" " width="140px">
           						 
				<kendo:grid-column-command>
				<kendo:grid-column-commandItem name="Edit Image"  click="uploadDocumentPhoto" className="k-grid-upload" />
					<%-- <kendo:grid-column-commandItem name="edit" /> --%>
					<kendo:grid-column-commandItem name="destroy"  text="Delete"/>
				</kendo:grid-column-command>
			</kendo:grid-column>
           						 
	
								</kendo:grid-columns>

								 <kendo:dataSource requestEnd="onRequestEndPhoto"  requestStart="onRequestStartPhoto" >
									<kendo:dataSource-transport>
										<kendo:dataSource-transport-read url="${readUrlPhotoGallery}/#=peId#" dataType="json" type="POST" contentType="application/json" />
										<%-- <kendo:dataSource-transport-create url="${postReplyCreateUrl}/#=ticketId#" dataType="json" type="GET" contentType="application/json" />
										<kendo:dataSource-transport-update url="${postReplyUpdateUrl}" dataType="json" type="GET" contentType="application/json" />
										<kendo:dataSource-transport-destroy	url="${postReplyDestroyUrl}" dataType="json" type="GET" contentType="application/json" /> --%>
										<kendo:dataSource-transport-destroy	url="${deleteImage}" dataType="json" type="POST" contentType="application/json" />
										 <kendo:dataSource-transport-parameterMap>
						                	<script type="text/javascript">
							                	function parameterMapkkk(options,type) 
							                	{
							                		return JSON.stringify(options);	                		
							                	}
						                	</script>
						                 </kendo:dataSource-transport-parameterMap>
									</kendo:dataSource-transport>
									
									<kendo:dataSource-schema >
										<kendo:dataSource-schema-model id="pgId">
											<kendo:dataSource-schema-model-fields>
												<kendo:dataSource-schema-model-field name="pgId" editable="false"/>
											</kendo:dataSource-schema-model-fields>
										</kendo:dataSource-schema-model>
									</kendo:dataSource-schema>
								</kendo:dataSource> 
							</kendo:grid>
						</div>
					</kendo:tabStrip-item-content>
				</kendo:tabStrip-item>
				</kendo:tabStrip-items>
				</kendo:tabStrip>
				</kendo:grid-detailTemplate>
				 

				

	<div id="alertsBox" title="Alert"></div>
	
	<%-- <div id="fileupload" style="display:none">
		<form method="post" action="<c:url value='./faq/uploadhanbook' />">        	
   			<kendo:upload multiple="false" name="file" error="onError" select="onSelectupload" template="<span class='k-progress'></span><div class='file-wrapper'> <span class='file-icon #=addExtensionClass(files[0].extension)#'></span><h6 class='file-heading file-name-heading'>Name: #=name#</h6><h6 class='file-heading file-size-heading'>Size: #=size# bytes</h6><button type='button' class='k-upload-action'></button></div>">
				<kendo:upload-async  autoUpload="true" saveUrl="./faq/uploadhanbook" removeUrl="${removeUrl}"/>
			</kendo:upload> 				
			<!-- <input type="submit" value="Submit" class="k-button" /> -->
		<!-- </form> -->
	</div> --%>
	
	<div id="uploadDialog" title="Upload Images" style="display: none;">
	<kendo:upload name="files" multiple="true" upload="uploadExtraData" success="onDocSuccess">
		<kendo:upload-async autoUpload="false" saveUrl="./photo/upload" />
	</kendo:upload>
</div> 
<div id="uploadDialogphoto" title="Upload Photo" style="display: none;">
	<kendo:upload name="files1" multiple="false" upload="uploadExtraDataPhoto" success="onDocSuccessPhoto">
		<kendo:upload-async autoUpload="false" saveUrl="./photoImageSingle/update" />
	</kendo:upload>
</div>

	
	<%-- <c:if test="${not empty XLERRORData}">
		<div id="XLERROR" title="Alert">
				<b>${XLERRORData}</b>
		</div>
	
		<script type="text/javascript">		
		$("#XLERROR").dialog({
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
	</c:if> --%>
	<script> 	
/* 	
	function onRequestStartChild(e){
		alert("ravi");
		
	} */
	/* function	 onRequestEndChild(e){
	alert("bhushan");
	 var gview = $("#subgrid").data("kendoGrid");
	 var selectedItem = gview.dataItem(gview.select());
	 subPGId = selectedItem.pgId;
	 alert(subPGId);
	} */
	
	
	
	var selectedItem = "";
    var SelectedRowId="";
	function onChangeServicePoint(arg) {
		var gview = $("#grid").data("kendoGrid");
		 selectedItem = gview.dataItem(gview.select());
		
		SelectedRowId = selectedItem.peId;
		
	}
 /*  var subPGId="";
	function onPhotoGallery(arg) {
		 var gview = $("#subgrid").data("kendoGrid");
		 var selectedItem = gview.dataItem(gview.select());
		 subPGId = selectedItem.pgId;
		 //alert(subPGId);
		 //SelectedServiceType = selectedItem.typeOfService;
	} */
  
	
	
	/* function oncomplete() {

		
		$("#myImage").attr("src","<c:url value='/photo/getImage/" + subPGId+"'/>");
		$("#myImages_"+subPGId).attr("src","<c:url value='/photo/getImage/" + subPGId+"'/>");
	}  */ 
	
	function onRequestStart(e){
		$('.k-grid-update').hide();
		$('.k-edit-buttons')
				.append(
						'<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
		$('.k-grid-cancel').hide();
}
	function onRequestStartPhoto(e){
		$('.k-grid-update').hide();
		$('.k-edit-buttons')
				.append(
						'<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
		$('.k-grid-cancel').hide();
}

	function clearFilterNotification()
	{
		  $("form.k-filter-menu button[type='reset']").slice().trigger("click");
		  var grid = $("#grid").data("kendoGrid");
		  grid.dataSource.read();
		  grid.refresh(); 
		  
		
	}
	function onError(){
		window.location.reload();
	}
	
	function onSelectupload(e){
		$.each(e.files, function (index, value) {			
	        var ok = value.extension == ".pdf" || value.extension == ".docx" || value.extension == ".doc";
	        if (!ok) {
	            e.preventDefault();
	            alert("Please upload XL (.pdf or .docx or .doc) files");
	        }
	    });
	}	
		
	 $(document).ready(function() {
         var window = $("#fileupload"),
             undo = $("#undo")
                     .bind("click", function() {
                         window.data("kendoWindow").open().center();
                         undo.hide();
                     });

         var onClose = function() {
             undo.show();
         }

         if (!window.data("kendoWindow")) {
             window.kendoWindow({
                 width: "600px",
                 title: "Import PDF File",
                 actions: [
                     "Pin",
                     "Minimize",
                     "Maximize",
                     "Close"
                 ],
                 close: onClose
             });
         }
     });	
	
	function addExtensionClass(extension) {
		switch (extension) {
		case '.jpg':
		case '.img':
		case '.png':
		case '.gif':
			return "img-file";
		case '.doc':
		case '.docx':
			return "doc-file";
		case '.xls':
		case '.xlsx':
			return "xls-file";
		case '.pdf':
			return "pdf-file";
		case '.zip':
		case '.rar':
			return "zip-file";
		default:
			return "default-file";
		}
	}
	
	$("#grid").on("click", ".k-grid-clear_filter", function(){
	    $("form.k-filter-menu button[type='reset']").slice().trigger("click");
	    var grid = $("#grid").data("kendoGrid");
		grid.dataSource.read();
		grid.refresh();
		filter=true;
	});	 	 
	
	
	
	 //register custom validation rules
    (function ($, kendo) {
        $.extend(true, kendo.ui.validator, {
             rules: { // custom rules            	 
            	 faqcontentvalidation: function (input, params) {
                     //check for the name attribute 
                     if (input.attr("name") == "faqcontent") {
                    	 return $.trim(input.val()) !== "";
                     }
                     return true;
                 },
                 faqsubjectvalidation: function (input, params) {
                     //check for the name attribute 
                     if (input.attr("name") == "faqsubject") {
                    	 return $.trim(input.val()) !== "";
                     }
                     return true;
                 },            
                 faqtypevalidation: function (input, params) {
                     //check for the name attribute 
                     if (input.attr("name") == "faqtype") {
                    	 return $.trim(input.val()) !== "";
                     }
                     return true;
                 }             
             },
           //custom rules messages
             messages: { 
            	 faqtypevalidation: "Enter FAQ Type",
            	 faqsubjectvalidation:"Enter FAQ Subject ", 
            	 faqcontentvalidation:"Enter FAQ Content ",            	
             }
        });
    })(jQuery, kendo);
	     
	    function eventDescription(container, options) 
		{
	        $('<textarea data-text-field="eventDesc" data-value-field="eventDesc" data-bind="value:' + options.field + '" style="width: 148px; height: 40px;"/>')
	             .appendTo(container);
		}	
    
       function faqDropDownEditor (container, options) {
      	   var data = ["General Questions" , "Membership","Terms Of Service","Licence Terms","Payment Rules","Other Questions"];
      	   $(
      	     '<input data-text-field="" data-value-field="" data-bind="value:' + options.field + '" />')
      	     .appendTo(container).kendoDropDownList({
      	      optionLabel :"Select",
      	      dataSource :data            	                 	      
      	   });
       }        
   		function faqremove(e){
   		 securityCheckForActions("./helpdeskmanagement/faq/deleteButton");
   		}
   		function faqEvent(e) {
   			
   			if($("#grid").data("kendoGrid").dataSource.filter()){
   				$("form.k-filter-menu button[type='reset']").slice().trigger("click");
   				var grid = $("#grid").data("kendoGrid");
   				grid.dataSource.read();
   				grid.refresh();
   	    	}
	 	
   			if (e.model.isNew()){
   				 $(".k-window-title").text("Add Event Details");
   		     	 $(".k-grid-update").text("Create"); 
   		     	// securityCheckForActions("./helpdeskmanagement/faq/createButton");
   		     	   
   			 }else{   		
   				 //securityCheckForActions("./helpdeskmanagement/faq/updateButton");   				 
   				 $(".k-window-title").text("Edit Event Details");
   		          	     				       	  
   			  }	
   		}   		
   			
   		
           
       function onRequestEnd(e) {
    	
    	
    	   if (typeof e.response != 'undefined')
			{
    		   if (e.response.status == "invalid") {
					errorInfo = "";
					errorInfo = e.response.result.invalid;
					for (var i = 0; i < e.response.result.length; i++) {
						errorInfo += (i + 1) + ". "
								+ e.response.result[i].defaultMessage;
					}
		
					$("#alertsBox").html("");
					$("#alertsBox").html(errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						draggable: false,
						resizable: false,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});		
					var grid = $("#grid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
					return false;
				}
    		    if (e.response.status == "FAIL") {
    				errorInfo = "";
    				for (var i = 0; i < e.response.result.length; i++) {
    					errorInfo += (i + 1) + ". "	+ e.response.result[i].defaultMessage+"<br>";
    				}
    				if (e.type == "create") {
    					$("#alertsBox").html("");
        				$("#alertsBox").html(errorInfo);
        				$("#alertsBox").dialog({
        					modal : true,
        					draggable: false,
        					resizable: false,
        					buttons : {
        						"Close" : function() {
        							$(this).dialog("close");
        						}
        					}
        				});
        				var grid = $("#grid").data("kendoGrid");
        				grid.dataSource.read();
        				grid.refresh();        				
    					
    				}

    				if (e.type == "update") {
    					$("#alertsBox").html("");
        				$("#alertsBox").html(errorInfo);
        				$("#alertsBox").dialog({
        					modal : true,
        					draggable: false,
        					resizable: false,
        					buttons : {
        						"Close" : function() {
        							$(this).dialog("close");
        						}
        					}
        				});
        				var grid = $("#grid").data("kendoGrid");
        				grid.dataSource.read();
        				grid.refresh(); 
    					
    				}

    				var grid = $("#grid").data("kendoGrid");
    				grid.dataSource.read();
    				grid.refresh();
    				return false;
    			}

    			if (e.type == "update" && !e.response.Errors) {
    				
    				$("#alertsBox").html("");
    				$("#alertsBox").html("Event Updated Successfully");
    				$("#alertsBox").dialog({
    					modal : true,
    					draggable: false,
    					resizable: false,
    					buttons : {
    						"Close" : function() {
    							$(this).dialog("close");
    						}
    					}
    				});    				
    				var grid = $("#grid").data("kendoGrid");
    				grid.dataSource.read();
    				grid.refresh();
    			}
    			
    			if (e.type == "update" && e.response.Errors) {
    				
    				$("#alertsBox").html("");
    				$("#alertsBox").html("Event Updation Failed");
    				$("#alertsBox").dialog({
    					modal : true,
    					draggable: false,
    					resizable: false,
    					buttons : {
    						"Close" : function() {
    							$(this).dialog("close");
    						}
    					}
    				});
    				var grid = $("#grid").data("kendoGrid");
    				grid.dataSource.read();
    				grid.refresh();
    			}

    			if (e.type == "create" && !e.response.Errors) {
    				
    				$("#alertsBox").html("");
    				$("#alertsBox").html("Event Created SuccessFully");
    				$("#alertsBox").dialog({
    					modal : true,
    					draggable: false,
    					resizable: false,
    					buttons : {
    						"Close" : function() {
    							$(this).dialog("close");
    						}
    					}
    				});
    				var grid = $("#grid").data("kendoGrid");
    				grid.dataSource.read();
    				grid.refresh();
    			}
    		 if(e.type == "destroy") 
				{
					$("#alertsBox").html("");
					$("#alertsBox").html("Event deleted successfully");
					$("#alertsBox").dialog
					({
						modal : true,
						buttons : 
						{
							"Close" : function() 
							{
								$(this).dialog("close");
							}
						}
					});
				}	
	
			}
		}  
       
       function onRequestEndPhoto(e) {
       	
    	  
    	   if (typeof e.response != 'undefined')
			{
    		   if (e.response.status == "invalid") {
					errorInfo = "";
					errorInfo = e.response.result.invalid;
					for (var i = 0; i < e.response.result.length; i++) {
						errorInfo += (i + 1) + ". "
								+ e.response.result[i].defaultMessage;
					}
		
					$("#alertsBox").html("");
					$("#alertsBox").html(errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						draggable: false,
						resizable: false,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});		
					var grid = $("#subgrid_"+SelectedRowId).data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
					return false;
				}
    		    if (e.response.status == "FAIL") {
    				errorInfo = "";
    				for (var i = 0; i < e.response.result.length; i++) {
    					errorInfo += (i + 1) + ". "	+ e.response.result[i].defaultMessage+"<br>";
    				}
    				if (e.type == "create") {
    					$("#alertsBox").html("");
        				$("#alertsBox").html(errorInfo);
        				$("#alertsBox").dialog({
        					modal : true,
        					draggable: false,
        					resizable: false,
        					buttons : {
        						"Close" : function() {
        							$(this).dialog("close");
        						}
        					}
        				});
        				var grid = $("#subgrid_"+SelectedRowId).data("kendoGrid");
        				grid.dataSource.read();
        				grid.refresh();        				
    					
    				}

    				if (e.type == "update") {
    					$("#alertsBox").html("");
        				$("#alertsBox").html(errorInfo);
        				$("#alertsBox").dialog({
        					modal : true,
        					draggable: false,
        					resizable: false,
        					buttons : {
        						"Close" : function() {
        							$(this).dialog("close");
        						}
        					}
        				});
        				var grid = $("#subgrid_"+SelectedRowId).data("kendoGrid");
        				grid.dataSource.read();
        				grid.refresh(); 
    					
    				}

    				var grid = $("#subgrid_"+SelectedRowId).data("kendoGrid");
    				grid.dataSource.read();
    				grid.refresh();
    				return false;
    			}

    			if (e.type == "update" && !e.response.Errors) {
    				
    				$("#alertsBox").html("");
    				$("#alertsBox").html("Photo Gallery Updated Successfully");
    				$("#alertsBox").dialog({
    					modal : true,
    					draggable: false,
    					resizable: false,
    					buttons : {
    						"Close" : function() {
    							$(this).dialog("close");
    						}
    					}
    				});    				
    				var grid = $("#subgrid_"+SelectedRowId).data("kendoGrid");
    				grid.dataSource.read();
    				grid.refresh();
    			}  			
    	
    		
    		 if(e.type == "destroy") 
				{
					$("#alertsBox").html("");
					$("#alertsBox").html("Photo Gallery Record deleted successfully");
					$("#alertsBox").dialog
					({
						modal : true,
						buttons : 
						{
							"Close" : function() 
							{
								$(this).dialog("close");
							}
						}
					});
				}	
	
			}
		}
       
  /*      function uploadDocument() {
    	 var gview = $("#grid").data("kendoGrid");
   		var selectedItem = gview.dataItem(gview.select());
   		//var peId = gview.dataItem(gview.select());
   		 if (selectedItem != null) {
   			peId = selectedItem.peId;
   		} 
   		
   		$('#uploadDialog').dialog({
   			modal : true,
   		});
   		
   	} */
   	var pgId=""; 
	 function changePhoto(e)
	{
		 
		 var gview = $("#subgrid_"+SelectedRowId).data("kendoGrid");
		 var selectedItem = gview.dataItem(gview.select());
		 pgId = selectedItem.pgId;
		
	}  
	
	function uploadDocument() {
		
		var gview = $("#grid").data("kendoGrid");
		var selectedItem = gview.dataItem(gview.select());
		if (selectedItem != null) {
			peId = selectedItem.peId;
		}
		var window = $("#uploadDialog");
		 window.kendoWindow({
	         width: "600px",
	         title: "Upload Images Data",
	         actions: [
	             "Pin",
	             "Minimize",
	             "Maximize",
	             "Close"
	         ],
	      
	     });
	 
		window.data("kendoWindow").open().center(); 
		
		
	}

	function uploadDocumentPhoto() {
		
	 var gview = $("#subgrid_"+SelectedRowId).data("kendoGrid");
	 	var selectedItem = gview.dataItem(gview.select());
		if (selectedItem != null) {
			pgId = selectedItem.pgId;
			
		}  
		var window1 = $("#uploadDialogphoto");
		 window1.kendoWindow({
	         width: "600px",
	         title: "Edit Image",
	         actions: [
	             "Pin",
	             "Minimize",
	             "Maximize",
	             "Close"
	         ],
	        
	     });
	 
		window1.data("kendoWindow").open().center(); 
		
		
	}
   	function uploadExtraData(e) {

		var files = e.files;
		// Check the extension of each file and abort the upload if it is not .jpg
		$.each(files,function() {
							if (this.extension.toLowerCase() == ".jpg" || this.extension.toLowerCase() == ".jpeg" || this.extension.toLowerCase() == ".png") {
								e.data = {
									peId : peId,
									type : this.extension
								};
							}

							else {
								alert("Invalid Document Type:\nAcceptable formats is pdf Only");
								e.preventDefault();
								return false;
							}
						});
	}
   	function uploadExtraDataPhoto(e) {

		var files = e.files;
		// Check the extension of each file and abort the upload if it is not .jpg
		$.each(files,function() {
							if (this.extension.toLowerCase() == ".jpg" || this.extension.toLowerCase() == ".jpeg" || this.extension.toLowerCase() == ".png") {
								e.data = {
									pgId : pgId,
									type : this.extension
								};
							}

							else {
								alert("Invalid Document Type:\nAcceptable formats is pdf Only");
								e.preventDefault();
								return false;
							}
						});
	}
   	
   	/* function onDocSuccess(e) {
		alert("Uploaded Successfully");
		$(".k-upload-files.k-reset").find("li").remove();
		$(".k-upload-status-total").remove();
		$('#grid').data('kendoGrid').dataSource.read();
	} */
	function onDocSuccess(e) {
		//alert("");
		alert("Uploaded Successfully");
		$(".k-upload-files.k-reset").find("li").remove();
		$(".k-upload-status-total").remove();
		$('#grid').data('kendoGrid').dataSource.read();
		window.location.reload();
	}
	function onDocSuccessPhoto(e) {
		//alert("");
		alert("Uploaded Successfully");
		$(".k-upload-files.k-reset").find("li").remove();
		$(".k-upload-status-total").remove();
		$('#grid').data('kendoGrid').dataSource.read();
		window.location.reload();
	}
	
 	function downloadFile()
    {
 		window.open("./photo/download/" +SelectedRowId);
    }
 	
 	function onImageSuccess(e)
	{
		alert("Uploaded Successfully");
		$(".k-upload-files.k-reset").find("li").remove();
		window.location.reload();
	}
 	
 	function onpersonImageUpload(e) {
		var files = e.files;
		// Check the extension of each file and abort the upload if it is not .jpg
		$.each(files, function() {
			if (this.extension.toLowerCase() == ".png") {
				e.data = {
						personId : SelectedRowId
					};
			}
			else if (this.extension.toLowerCase() == ".jpg") {
				
				e.data = {
						personId : SelectedRowId
					};
			}
			else if (this.extension.toLowerCase() == ".jpeg") {
				
				e.data = {
						personId : SelectedRowId
					};
			}
			else {
				alert("Only Images can be uploaded\nAcceptable formats are png, jpg and jpeg");
				e.preventDefault();
				return false;
			}
		});
    }
 	 /* var pgId=""; 
 	 function changePhoto(e)
 	{
 		 
 		 var gview = $("#subgrid_"+SelectedRowId).data("kendoGrid");
		 var selectedItem = gview.dataItem(gview.select());
		 pgId = selectedItem.pgId;
		
 	}   */
 	function downloadImage1()
	{
 		 var gview = $("#subgrid_"+SelectedRowId).data("kendoGrid");
 		var selectedItem = gview.dataItem(gview.select());
		window.open("./photo/getImage/"+selectedItem.pgId);
	}     
    </script>  
	
	<style>
		.file-icon {
		display: inline-block;
		float: left;
		width: 48px;
		height: 48px;
		margin-left: 10px;
		margin-top: 13.5px;
		}
		
		.img-file {
		background-image: url(./resources/kendo/web/upload/jpg.png)
	}
	
	.doc-file {
		background-image: url(./resources/kendo/web/upload/doc.png)
	}
	
	.pdf-file {
		background-image: url(./resources/kendo/web/upload/pdf.png)
	}
	
	.xls-file {
		background-image: url(./resources/kendo/web/upload/xls.png)
	}
	
	.zip-file {
		background-image: url(./resources/kendo/web/upload/zip.png)
	}
	
	.default-file {
		background-image: url(./resources/kendo/web/upload/default.png)
	}
	
	#example .file-heading {
		font-family: Arial;
		font-size: 1.1em;
		display: inline-block;
		float: left;
		width: 450px;
		margin: 0 0 0 20px;
		height: 25px;
		-ms-text-overflow: ellipsis;
		-o-text-overflow: ellipsis;
		text-overflow: ellipsis;
		overflow: hidden;
		white-space: nowrap;
	}
	
	#example .file-name-heading {
		font-weight: bold;
	}
	
	#example .file-size-heading {
		font-weight: normal;
		font-style: italic;
	}
	
	li.k-file .file-wrapper .k-upload-action {
		position: absolute;
		top: 0;
		right: 0;
	}
	
	li.k-file div.file-wrapper {
		position: relative;
		height: 75px;
	}
	
	.ui-dialog-osx {
	    -moz-border-radius: 0 0 8px 8px;
	    -webkit-border-radius: 0 0 8px 8px;
	    border-radius: 0 0 8px 8px; border-width: 0 8px 8px 8px;
	}
	
	.k-datepicker span {
	width: 68%
}
.k-window-titlebar {
	height: 25px;
} 

	.file-icon {
		display: inline-block;
		float: left;
		width: 48px;
		height: 48px;
		margin-left: 10px;
		margin-top: 13.5px;
		}
		
		.img-file {
		background-image: url(./resources/kendo/web/upload/jpg.png)
	}
	
	.doc-file {
		background-image: url(./resources/kendo/web/upload/doc.png)
	}
	
	.pdf-file {
		background-image: url(./resources/kendo/web/upload/pdf.png)
	}
	
	.xls-file {
		background-image: url(./resources/kendo/web/upload/xls.png)
	}
	
	.zip-file {
		background-image: url(./resources/kendo/web/upload/zip.png)
	}
	
	.default-file {
		background-image: url(./resources/kendo/web/upload/default.png)
	}
	
	#example .file-heading {
		font-family: Arial;
		font-size: 1.1em;
		display: inline-block;
		float: left;
		width: 450px;
		margin: 0 0 0 20px;
		height: 25px;
		-ms-text-overflow: ellipsis;
		-o-text-overflow: ellipsis;
		text-overflow: ellipsis;
		overflow: hidden;
		white-space: nowrap;
	}
	
	#example .file-name-heading {
		font-weight: bold;
	}
	
	#example .file-size-heading {
		font-weight: normal;
		font-style: italic;
	}
	
	li.k-file .file-wrapper .k-upload-action {
		position: absolute;
		top: 0;
		right: 0;
	}
	
	li.k-file div.file-wrapper {
		position: relative;
		height: 75px;
	}
	
	.ui-dialog-osx {
	    -moz-border-radius: 0 0 8px 8px;
	    -webkit-border-radius: 0 0 8px 8px;
	    border-radius: 0 0 8px 8px; border-width: 0 8px 8px 8px;
	}
	</style> 
	 <style>
.k-edit-form-container {
	/* width: 550px; */
	text-align: center;
	position: relative;
	/*  height: 600px; */
	/* border: 1px solid black; */
	/*  overflow: hidden;  */
}

.wrappers {
	display: inline;
	float: left;
	width: 250px;
	padding-top: 10px;
	position: relative;
}

.photo {
	width: 140px;
}

.details {
	width: 400px;
}

.title {
	display: block;
	font-size: 1.6em;
}

.description {
	display: block;
	padding-top: 1.6em;
}

.employeeID {
	font-family: "Segoe UI", "Helvetica Neue", Arial, sans-serif;
	font-size: 50px;
	font-weight: bold;
	color: #898989;
}

td.photo,.employeeID {
	text-align: center;
}

td {
	vertical-align: middle;
}

#employeesTable td {
	background: -moz-linear-gradient(top, rgba(0, 0, 0, 0.05) 0%,
		rgba(0, 0, 0, 0.15) 100%);
	background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, rgba(0, 0
		, 0, 0.05)), color-stop(100%, rgba(0, 0, 0, 0.15)));
	background: -webkit-linear-gradient(top, rgba(0, 0, 0, 0.05) 0%,
		rgba(0, 0, 0, 0.15) 100%);
	background: -o-linear-gradient(top, rgba(0, 0, 0, 0.05) 0%,
		rgba(0, 0, 0, 0.15) 100%);
	background: -ms-linear-gradient(top, rgba(0, 0, 0, 0.05) 0%,
		rgba(0, 0, 0, 0.15) 100%);
	background: linear-gradient(to bottom, rgba(0, 0, 0, 0.05) 0%,
		rgba(0, 0, 0, 0.15) 100%);
	padding: 20px;
}

.employeesDropDownWrap {
	display: block;
	margin: 0 auto;
}

#emplooyeesDropDown-list .k-item {
	overflow: hidden;
}

#emplooyeesDropDown-list img {
	-moz-box-shadow: 0 0 2px rgba(0, 0, 0, .4);
	-webkit-box-shadow: 0 0 2px rgba(0, 0, 0, .4);
	box-shadow: 0 0 2px rgba(0, 0, 0, .4);
	float: left;
	margin: 5px 20px 5px 0;
}

#emplooyeesDropDown-list h3 {
	margin: 30px 0 10px 0;
	font-size: 2em;
}

#emplooyeesDropDown-list p {
	margin: 0;
}

.k-upload-button input {
	z-index: 10000
}

img {
    border-radius: 8px;
    opacity: 1.0;   
}

img:hover {
    opacity: 0.80;
}

.k-edit-form-container {
    text-align: left;
}

.k-tabstrip{
	width: 1150px
}

#hovertd:hover {
    background: #eee!important;
}

#hovertd {
	text-align : center;
   	padding: 15px 20px;
    border-right: 1px solid #d5d5d5;
    background: #d4d4d4;
    border-bottom: 1px solid #d5d5d5;
    width: 50%
 }

.k-dropdown-wrap {
    z-index: 1;
}

.k-invalid-msg {
    position: relative;
    z-index: 2;
}


.k-animation-container { 
	width: auto !important; 
}
#reqId-list{
	width: 300px
}
	</style>