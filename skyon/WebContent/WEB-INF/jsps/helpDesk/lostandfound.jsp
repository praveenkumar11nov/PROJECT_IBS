<%@include file="/common/taglibs.jsp"%>

	<c:url value="/lostandfound/create" var="createUrl" />
	<c:url value="/lostandfound/read" var="readUrl" />
	<c:url value="/lostandfound/update" var="updateUrl" />
	<c:url value="/lostandfound/delete" var="deleteUrl" />
	
	<c:url value="/jobobnotificationdetails/getNotificationMembers" var="notificationMembersCombo" />
	<c:url value="/parkingslotsdetails/getBlocks" var="getblocks" />
	<c:url value="/jobobnotificationdetails/getFlats" var="getFlat" />
	
	<c:url value="/lostandfound/upload/lostandfoundImage" var="lostandfoundImage" />
	
	<c:url value="/lostandfound/filter1" var="lostFilter" />
	<c:url value="/lostandfound/filter2" var="blockFilter" />
	<c:url value="/lostandfound/filter3" var="propertyFilter" />
	<c:url value="/lostandfound/filter4" var="personFilter" />
	<c:url value="/lostandfound/filter5" var="statusFilter" />
	
	
	
	
	
	
	
	<kendo:grid name="grid" pageable="true"  change="onChangeOwner" edit="lostandfoundEvent"  selectable="true" remove="lostandfoundremove" resizable="true" groupable="true"  sortable="true" filterable="true" scrollable="true" height="430px">
		<kendo:grid-editable  mode="popup" confirmation="Are You Sure? you want delete the Record?" />
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Lost And Found Details" />
			<kendo:grid-toolbarItem name="clear_filter" text="Clear Filter"/>
		</kendo:grid-toolbar>
			
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" ></kendo:grid-pageable>
		
	<kendo:grid-filterable extra="false">
			<kendo:grid-filterable-operators>
				<kendo:grid-filterable-operators-string eq="Is equal to"/>
				<kendo:grid-filterable-operators-date gt="Is after" lt="Is before"/>
			</kendo:grid-filterable-operators>
		</kendo:grid-filterable>
		
		<kendo:grid-columns>
			
			<kendo:grid-column title="Image" field="image" template ="<span onclick='clickToUploadImage()' title='Click to Upload Image' ><img src='./lostandfound/getlostandfoundimage/#=lostandfoundId#' id='myImages_#=lostandfoundId#' alt='Click to Upload Image' width='80px' height='80px'/></span>"
				filterable="false" width="94px" sortable="false">
			</kendo:grid-column>
				
			<kendo:grid-column title="Lost/Found" editor="lostandfoundDropDownEditor" field="lostandfoundtype" width="70px">
			
			
				
			
			<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function DesignationNameFilter(element) {
								element.kendoAutoComplete({
									dataSource : {
										transport : {
											read : "${lostFilter}"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
      	  		
			<kendo:grid-column title="Subject/Material" field="lostandfoundsubject" width="100px" filterable="fasle"/>
			
			
			<kendo:grid-column title="Block" field="block" editor="blockforEditor" width="50px">
			<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function DesignationNameFilter(element) {
								element.kendoAutoComplete({
									dataSource : {
										transport : {
											read : "${blockFilter}"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			
			</kendo:grid-column>
			<kendo:grid-column title="Property" field="propertyNo" editor="flatforEditor" width="70px">
			
				
			
			<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function DesignationNameFilter(element) {
								element.kendoAutoComplete({
									dataSource : {
										transport : {
											read : "${propertyFilter}"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="Person" field="personName" editor="MembersEditor" width="100px">
			
				
			
			<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function DesignationNameFilter(element) {
								element.kendoAutoComplete({
									dataSource : {
										transport : {
											read : "${personFilter}"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="Description/Location" field="lostandfoundcontent" editor="lostandfoundDescription"  width="100px" filterable="fasle"/>
			
			<kendo:grid-column title="Date" field="datelost" format= "{0:dd/MM/yyyy hh:mm tt}" editor="dateEditor" width="100px"/>
			
			<kendo:grid-column title="dummy" field="dummy" hidden="true"/>
			
			<kendo:grid-column title="Status" field="status" editor="statusEditor"  width="80px">
			
				
			
			<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function DesignationNameFilter(element) {
								element.kendoAutoComplete({
									optionLabel: "Select",
									dataSource : {                                         
										transport : {
											read : "${statusFilter}"
										}
									}
								});
							}
							
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title=" " width="110px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit"/>
					<kendo:grid-column-commandItem name="destroy"  text="Delete"/>
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
			
			<kendo:dataSource-schema parse="parse">
				<kendo:dataSource-schema-model id="lostandfoundId">
					<kendo:dataSource-schema-model-fields>
					
						<kendo:dataSource-schema-model-field name="lostandfoundtype" defaultValue="Lost"/>							
						<kendo:dataSource-schema-model-field name="lostandfoundsubject"/>							
						<kendo:dataSource-schema-model-field name="lostandfoundcontent"/>							
						<kendo:dataSource-schema-model-field name="propertyNo"/>							
						<kendo:dataSource-schema-model-field name="propertyId"/>							
						<kendo:dataSource-schema-model-field name="personName"/>							
						<kendo:dataSource-schema-model-field name="personId"/>							
						<kendo:dataSource-schema-model-field name="block"/>							
						<kendo:dataSource-schema-model-field name="blockId"/>							
						<kendo:dataSource-schema-model-field name="datelost" type="date"/>							
						<kendo:dataSource-schema-model-field name="status" defaultValue="UnClaimed"/>							
						
					</kendo:dataSource-schema-model-fields>
					
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
	</kendo:grid>

	<div id="alertsBox" title="Alert"></div>
	
	<div id="uploadDialog" title="Upload Image" style="display: none;">
		<kendo:upload complete="oncomplete" name="files" upload="onpersonImageUpload" multiple="false" success="onImageSuccess">
					<kendo:upload-async autoUpload="false" saveUrl="${lostandfoundImage}" />
		</kendo:upload>
	</div>
	
	<script> 	
	 
	 function onRequestStart(e){
			$('.k-grid-update').hide();
			$('.k-edit-buttons')
					.append(
							'<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
			$('.k-grid-cancel').hide();
	}
	 
	 
	
	var SelectedRowId = "";
	function onChangeOwner(arg) {
		 var gview = $("#grid").data("kendoGrid");
	 	 var selectedItem = gview.dataItem(gview.select());
	 	 SelectedRowId = selectedItem.lostandfoundId;
	 	 this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
	}
	
	$("#grid").on("click", ".k-grid-clear_filter", function(){
	    $("form.k-filter-menu button[type='reset']").slice().trigger("click");
	    var grid = $("#grid").data("kendoGrid");
		grid.dataSource.read();
		grid.refresh();
		filter=true;
	});	 
	
	function clickToUploadImage(){
		$('#uploadDialog').dialog({
			modal : true,
		});
		return false;
	}
	
	function parse (response) {   
	    $.each(response, function (idx, elem) {
           
            if (elem.lastUpdatedDate && typeof elem.lastUpdatedDate === "string") {
                elem.lastUpdatedDate = kendo.parseDate(elem.lastUpdatedDate, "yyyy-MM-ddTHH:mm:ss.fffZ");
            }
            if (elem.datelost && typeof elem.datelost === "string") {
                elem.jobDt = kendo.parseDate(elem.jobDt, "yyyy-MM-ddTHH:mm:ss.fffZ");
            }
        });
        return response;
	} 
	
	function dateEditor(container, options) {
	    $('<input name="' + options.field + '"/>')
	            .appendTo(container)
	            .kendoDateTimePicker({
	                format:"dd/MM/yyyy hh:mm tt",
	                timeFormat:"hh:mm tt"      	                
	    });
	}
	
	function blockforEditor(container, options) {
 		$('<input name="Block" data-text-field="block" id="blockforJob" data-value-field="blockId" data-bind="value:' + options.field + '" required="true"/>')
 				.appendTo(container).kendoComboBox({
 					 placeholder: "Select Blocks",
 		     		 filter:"startswith",	     	
 					 dataSource : {
 						transport : {
 							read : "${getblocks}"
 						}
 					}
 				});
 		 $('<span class="k-invalid-msg" data-for="Block"></span>').appendTo(container);
 	}	
	
	function flatforEditor(container, options) {
 		$('<input name="Property" data-text-field="propertyNo" id="flatforJob" data-value-field="propertyId" data-bind="value:' + options.field + '" required="true"/>')
 				.appendTo(container).kendoComboBox({
 					 placeholder: "Select Property",
 		     		 filter:"startswith",
 		     		 cascadeFrom: "blockforJob",		
 					 dataSource : {
 						transport : {
 							read : "${getFlat}"
 						}
 					}
 		});
 		$('<span class="k-invalid-msg" data-for="Property"></span>').appendTo(container);
 	} 	
 	
     
     function MembersEditor(container, options) {
   	    $('<input name="Person" data-text-field="personName" id="notificationMember" data-value-field="personId" data-bind="value:' + options.field + '" required="true"/>')
   	    	.appendTo(container).kendoComboBox({ 
   	    	cascadeFrom: "flatforJob",
   	    	dataSource : {
				transport : {
					read : "${notificationMembersCombo}"
				}
			},	  	               	                 	      
   	    });	 
   	 $('<span class="k-invalid-msg" data-for="Person"></span>').appendTo(container);
     }   
	
	
	 //register custom validation rules
    (function ($, kendo) {
        $.extend(true, kendo.ui.validator, {
             rules: { // custom rules            	 
            	 lostandfoundcontentvalidation: function (input, params) {
                     //check for the name attribute 
                     if (input.attr("name") == "datelost") {
                    	 return $.trim(input.val()) !== "";
                     }
                     return true;
                 },
                 lostandfoundsubjectvalidation: function (input, params) {
                     //check for the name attribute 
                     if (input.attr("name") == "lostandfoundsubject") {
                    	 return $.trim(input.val()) !== "";
                     }
                     return true;
                 },            
                 lostandfoundtypevalidation: function (input, params) {
                     //check for the name attribute 
                     if (input.attr("name") == "lostandfoundtype") {
                    	 return $.trim(input.val()) !== "";
                     }
                     return true;
                 }             
             },
           //custom rules messages
             messages: { 
            	 lostandfoundtypevalidation: "Enter Type",
            	 lostandfoundsubjectvalidation:"Enter Subject/Material ", 
            	 lostandfoundcontentvalidation:"Enter Date ",            	
             }
        });
    })(jQuery, kendo);
	     
	    function lostandfoundDescription(container, options) 
		{
	        $('<textarea data-text-field="lostandfoundcontent" data-value-field="lostandfoundcontent" data-bind="value:' + options.field + '" style="width: 148px; height: 40px;"/>')
	             .appendTo(container);
		}	
    
       function lostandfoundDropDownEditor (container, options) {
      	   var data = ["Lost" , "Found"];
      	   $(
      	     '<input data-text-field="" id="typeId"  data-value-field="" data-bind="value:' + options.field + '" />')
      	     .appendTo(container).kendoDropDownList({
      	      dataSource :data,  
      	      change:onchangeType
      	   });
       }   
       
       function statusEditor (container, options) {
      	   var data = ["UnClaimed" , "Claimed","Cannot Be Claimed"];
      	   $(
      	     '<input data-text-field="" id="status"  data-value-field="" data-bind="value:' + options.field + '" />')
      	     .appendTo(container).kendoDropDownList({
      	      dataSource :data,      	     
      	   });
       }       
       var typevalue;
       function onchangeType(){
    	   var value=$("#typeId").val(); 
    	   typevalue=value;
    	   //alert("Value="+value)
    	   if(value=="Found"){
    			$('label[for="block"]').parent().hide();
  			  	$("#blockforJob").parent().hide();
    			$('label[for="propertyNo"]').parent().hide();
  			  	$("#flatforJob").parent().hide();
    			$('label[for="personName"]').parent().hide();
  			  	$("#notificationMember").parent().hide();
    	   }else{
    		   	$('label[for="block"]').parent().show();
 			  	$("#blockforJob").parent().show();
   				$('label[for="propertyNo"]').parent().show();
 			  	$("#flatforJob").parent().show();
   				$('label[for="personName"]').parent().show();
 			  	$("#notificationMember").parent().show();
    	   }
       }
   		function lostandfoundremove(e){
   		 securityCheckForActions("./helpdeskmanagement/lostandfound/deleteButton");
   		}
   		function lostandfoundEvent(e) {
   			
   			if($("#grid").data("kendoGrid").dataSource.filter()){
   				$("form.k-filter-menu button[type='reset']").slice().trigger("click");
   				var grid = $("#grid").data("kendoGrid");
   				grid.dataSource.read();
   				grid.refresh();
   	    	}
   			$('label[for="image"]').parent().remove();
   			$('div[data-container-for="image"]').remove();
   			
   			$('label[for="dummy"]').hide();
   			$('div[data-container-for="dummy"]').hide();
   			
   			$('label[for="lostandfoundtype"]').after('<label style=color:red;>&nbsp;*</label>');
   			$('label[for="lostandfoundsubject"]').after('<label style=color:red;>&nbsp;*</label>');
   			$('label[for="datelost"]').after('<label style=color:red;>&nbsp;*</label>');
   			
   			$('label[for="block"]').after('<label style=color:red;>&nbsp;*</label>');
   			$('label[for="propertyNo"]').after('<label style=color:red;>&nbsp;*</label>');
   			$('label[for="personName"]').after('<label style=color:red;>&nbsp;*</label>');
   				
   			
   			if (e.model.isNew()){
   				 $('label[for="status"]').parent().hide();
	  			 $("#status").parent().hide();
	  			  	
   				 $(".k-window-title").text("Add Lost And Found Details");
   		     	 $(".k-grid-update").text("Create"); 
   		     	 securityCheckForActions("./helpdeskmanagement/lostandfound/createButton");
   		     	 
   		     	$(".k-grid-update").click(function () {
	   		     	if($("#typeId").val()=="Found"){
	   		     		$("#blockforJob").removeAttr("required");
	   		     		$("#flatforJob").removeAttr("required");
	   		     		$("#notificationMember").removeAttr("required");
	   	   			}else{
	   	   				$('#blockforJob').prop('required',true);
	   	   				$('#flatforJob').prop('required',true);
	   	   				$('#notificationMember').prop('required',true);
	   	   			}
   		     	});
   		     	   
   			 }else{   		
   				 securityCheckForActions("./helpdeskmanagement/lostandfound/updateButton");   				 
   				 $(".k-window-title").text("Edit Lost And Found Details");
   		      	 value=e.model.lostandfoundtype
   				 if(value=="Found"){
   	    			$('label[for="block"]').parent().hide();
   	  			  	$("#blockforJob").parent().hide();
   	    			$('label[for="propertyNo"]').parent().hide();
   	  			  	$("#flatforJob").parent().hide();
   	    			$('label[for="personName"]').parent().hide();
   	  			  	$("#notificationMember").parent().hide();
   	    	    }else{
   	    		   	$('label[for="block"]').parent().show();
   	 			  	$("#blockforJob").parent().show();
   	   				$('label[for="propertyNo"]').parent().show();
   	 			  	$("#flatforJob").parent().show();
   	   				$('label[for="personName"]').parent().show();
   	 			  	$("#notificationMember").parent().show();
   	    	    }
  				$(".k-grid-update").click(function () {
  					if(typevalue!=undefined)
  						value=typevalue;
	   		     	if(value=="Found"){
	   		     		$("#blockforJob").removeAttr("required");
	   		     		$("#flatforJob").removeAttr("required");
	   		     		$("#notificationMember").removeAttr("required");
	   	   			}else{
	   	   				$('#blockforJob').prop('required',true);
	   	   				$('#flatforJob').prop('required',true);
	   	   				$('#notificationMember').prop('required',true);
	   	   			}
  		     	});
   		          	     				       	  
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
    				$("#alertsBox").html("Record Updated Successfully");
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
    				$("#alertsBox").html("Record Updation Failed");
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
    				$("#alertsBox").html("Record Created SuccessFully");
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
 					$("#alertsBox").html("Lost/Found Record deleted successfully");
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
            
    </script>  
	
	<script type="text/javascript">
	function oncomplete() {
		$("#myImage").attr(
				"src",
				"<c:url value='/lostandfound/getlostandfoundimage/" + lostandfoundId
						+ "?timestamp=" + new Date().getTime() + "'/>");
		$("#myImages_"+lostandfoundId).attr(
				"src",
				"<c:url value='/lostandfound/getlostandfoundimage" + lostandfoundId
						+ "?timestamp=" + new Date().getTime() + "'/>");
	}
	
	function onpersonImageUpload(e) {
		var files = e.files;
		// Check the extension of each file and abort the upload if it is not .jpg
		$.each(files, function() {
			if (this.extension.toLowerCase() == ".png") {
				e.data = {
						lostandfoundId : SelectedRowId
					};
			}
			else if (this.extension.toLowerCase() == ".jpg") {
				
				e.data = {
						lostandfoundId : SelectedRowId
					};
			}
			else if (this.extension.toLowerCase() == ".jpeg") {
				
				e.data = {
						lostandfoundId : SelectedRowId
					};
			}
			else {
				alert("Only Images can be uploaded\nAcceptable formats are png, jpg and jpeg");
				e.preventDefault();
				return false;
			}
		});
    }
	function onImageSuccess(e)
	{
		alert("Uploaded Successfully");
		$(".k-upload-files.k-reset").find("li").remove();
		window.location.reload();
	}
		
	
	</script>

	<style>
		  .k-upload-button input {
                z-index: inherit;
                }
	</style>