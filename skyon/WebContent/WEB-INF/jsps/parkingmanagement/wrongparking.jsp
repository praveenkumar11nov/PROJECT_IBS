<%@include file="/common/taglibs.jsp"%>

	<c:url value="/wrongparking/create" var="createUrl" />
	<c:url value="/wrongparking/read" var="readUrl" />
	<c:url value="/wrongparking/update" var="updateUrl" />
	<c:url value="/wrongparking/getOwnerNames" var="getOwnername" />
	<c:url value="/wrongparking/getSlotNumbers" var="getSlotNumber" />
	<c:url value="/wrongparking/getRegNumbers" var="getRegNumber" />
	<c:url value="/wrongparking/destroy" var="destroyUrl" />	
	<c:url value="/wrongparking/getRegNameForFilterWrongParking" var="RegNameFilterUrl" />
	<c:url value="/wrongparking/getSlotForFilterWrongParking" var="SlotNameFilterUrl" />
	<c:url value="/wrongparking/getActionForFilter" var="ActionFilterUrl" />
	<c:url value="/wrongparking/getNoticeeForFilter" var="NoticeFilterUrl" />
	<c:url value="/wrongparking/getTagNameForFilterWrongParking" var="TagNameFilterUrl" />
	<c:url value="/wrongparking/getCreatedByForFilterWrongParking" var="CreatedByFilterUrl" />
	<c:url value="/wrongparking/getUpdatedByForFilterWrongParking" var="UpdatedByFilterUrl" />
	
	<kendo:grid name="grid" pageable="true" resizable="true" groupable="true" sortable="true" filterable="true" selectable="true"
		scrollable="true" height="430px">
		<kendo:grid-editable mode="popup" />
		
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Wrong Parking Details" />
				<kendo:grid-toolbarItem text="Clear Filter" name="Clear_Filter"/>			
		</kendo:grid-toolbar>
		
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" ></kendo:grid-pageable>
		
		<kendo:grid-filterable extra="false">
		 <kendo:grid-filterable-operators>
		  	<kendo:grid-filterable-operators-string eq="Is equal to"/>
		  	<kendo:grid-filterable-operators-date lte="Wrong Parked Before" gte="Wrong Parked After"/>		
		 </kendo:grid-filterable-operators>
			
		</kendo:grid-filterable>		
		
		<kendo:grid-columns>
		
			<kendo:grid-column title="VehicleId" field="vehicleId" hidden="true" />
			<kendo:grid-column title="Vehicle Reg No" field="vehicleNo" editor="regEditor" width="130px">
			 <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function psSlotNoFilter(element) {
								element.kendoAutoComplete({
								dataSource : {
										transport : {
											read : "${RegNameFilterUrl}",
										}
									},
								});							
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	 
	    	</kendo:grid-column>
	    	
			<kendo:grid-column title="Vehicle Tag No" field="vhTagNoWrong" width="130px">
				 <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function psSlotNoFilter(element) {
								element.kendoAutoComplete({
								dataSource : {
										transport : {
											read : "${TagNameFilterUrl}",
										}
									},
								});							
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	      	   
      	   </kendo:grid-column>	     	   
			
			<kendo:grid-column title="Parked Slot" field="psSlotNo" width="100px" editor="parkingSlotEditor">
			 <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function psSlotNoFilter(element) {
								element.kendoAutoComplete({
								dataSource : {
										transport : {
											read : "${SlotNameFilterUrl}",
										}
									},
								});							
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	      	   
      	   </kendo:grid-column>	 
			<kendo:grid-column title="Wrong Parking Date" field="wpDt" format= "{0:dd/MM/yyyy hh:mm tt}" width="145px">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
	    					function fromDateFilter(element) {
								element.kendoDateTimePicker({
									format:"{0:dd/MM/yyyy hh:mm tt}",
					            	
				            	});
					  		}    					
					  	</script>			
	    			</kendo:grid-column-filterable-ui>
	    	</kendo:grid-column-filterable>	    	</kendo:grid-column>
			<kendo:grid-column title="Action Taken" field="actionTaken" editor="actionEditor" width="150px">
			 <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function psSlotNoFilter(element) {
								element.kendoAutoComplete({
								dataSource : {
										transport : {
											read : "${ActionFilterUrl}",
										}
									},
								});							
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	      	   
      	   </kendo:grid-column>	
      	   
			<kendo:grid-column title="Notice Generated" field="status" width="120px">
			 <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function psSlotNoFilter(element) {
								element.kendoAutoComplete({
								dataSource : {
										transport : {
											read : "${NoticeFilterUrl}",
										}
									},
								});							
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	      	   
      	   </kendo:grid-column>	
      	   
			<kendo:grid-column title="Created By" field="createdBy" width="90px">
			 <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function psSlotNoFilter(element) {
								element.kendoAutoComplete({
								dataSource : {
										transport : {
											read : "${CreatedByFilterUrl}",
										}
									},
								});							
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	      	   
      	   </kendo:grid-column>	
      	   
			<kendo:grid-column title="Last Updated By" field="lastUpdatedBy" width="120px">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function psSlotNoFilter(element) {
								element.kendoAutoComplete({
								dataSource : {
										transport : {
											read : "${UpdatedByFilterUrl}",
										}
									},
								});							
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	      	   
      	   </kendo:grid-column>	

			<kendo:grid-column title="Last Updated Date" field="lastUpdatedDate" width="140px" format= "{0:dd/MM/yyyy hh:mm tt}" >
				<kendo:grid-column-filterable ui="datetimepicker"></kendo:grid-column-filterable>
	    	</kendo:grid-column>
	    		
			<kendo:grid-column title="&nbsp;" width="80px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit" click="edit" />
				</kendo:grid-column-command>
			</kendo:grid-column>			
			
			
		<kendo:grid-column title=""	template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Allocate#=data.wpId#'>#= data.status == 'YES' ? 'Notice Generated' : 'Generate Notice' #</a>" width="100px" />
	
		</kendo:grid-columns>
		<kendo:dataSource pageSize="20" requestEnd="onRequestEnd" requestStart="OnRequestStart">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-create url="${createUrl}" dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-update url="${updateUrl}" dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-destroy url="${destroyUrl}" dataType="json" type="POST" contentType="application/json" />
				
				<kendo:dataSource-transport-parameterMap>
					<script>
						function parameterMap(options, type) {
							return JSON.stringify(options);
						}
					</script>
				</kendo:dataSource-transport-parameterMap>
			</kendo:dataSource-transport>
			
			<kendo:dataSource-schema parse="parse">
				<kendo:dataSource-schema-model id="wpId">
					<kendo:dataSource-schema-model-fields>					
							
						<kendo:dataSource-schema-model-field name="psSlotNo"/>
						<kendo:dataSource-schema-model-field name="wpDt" type="date"/>										
						<kendo:dataSource-schema-model-field name="vehicleNo"/>												
						<kendo:dataSource-schema-model-field name="actionTaken" type="string"/>
						<kendo:dataSource-schema-model-field name="vhTagNoWrong" />
						<kendo:dataSource-schema-model-field name="createdBy" type="string"/>
						<kendo:dataSource-schema-model-field name="lastUpdatedBy"/>
						<kendo:dataSource-schema-model-field name="lastUpdatedDate" type="date"/>
					
						<kendo:dataSource-schema-model-field name="status" defaultValue="NO" type="string" />
						
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
	</kendo:grid>
	<!-- <div id="dialogBoxforFamilyMembers" title="Owner Details">
	</div> -->
	<div id="alertsBox" title="Alert"></div>
	<div id="dialogBox" title="Owner Details"></div>	
	<script>   
    
     function parse (response) {   
 	    $.each(response, function (idx, elem) {
             if (elem.wpDt && typeof elem.wpDt === "string") {
                 elem.wpDt = kendo.parseDate(elem.wpDt, "yyyy-MM-ddTHH:mm:ss.fffZ");
             }            
             if (elem.lastUpdatedDate && typeof elem.lastUpdatedDate === "string") {
                 elem.lastUpdatedDate = kendo.parseDate(elem.lastUpdatedDate, "yyyy-MM-ddTHH:mm:ss.fffZ");
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
	
    function dateLastEditor(container, options) {
	    $('<input name="' + options.field + '"/>')
	            .appendTo(container)
	            .kendoDateTimePicker({
	                format:"dd/MM/yyyy hh:mm tt",
	                timeFormat:"hh:mm tt"      	                
	    });
	}		
			
	$("#grid").on("click", ".k-grid-Clear_Filter", function(){
	    $("form.k-filter-menu button[type='reset']").slice().trigger("click");
		var grid = $("#grid").data("kendoGrid");
		grid.dataSource.read();
		grid.refresh();
	});
			
	function actionEditor(container, options) {
     	   var data = ["No Action Taken" , "Vehicle Towed","Informed"];
     	   $(
     	     '<input data-text-field="" style="width:180px;" id="ownership" data-value-field="" data-bind="value:' + options.field + '" />')
     	     .appendTo(container).kendoDropDownList({
     	      optionLabel :"Select",
     	      dataSource :data            	                 	      
     	   });
    }  


 	function regEditor(container, options) {
 		 $('<input data-text-field="vehicleNo" style="width:180px;" id="regNumber" data-value-field="vehicleNo" data-bind="value:' + options.field + '"/>')
     		.appendTo(container).kendoComboBox({
     			placeholder:"Registration Number",
     			filter:"startswith",
     			change:registrationChange,
				dataSource : {
					transport : {
						read : "${getRegNumber}"
					}
				},
				
				change : function (e) {
		            if (this.value() && this.selectedIndex == -1) {                    
		                alert("Vehicle Registration No doesn't exist!");
		                $("#regNumber").data("kendoComboBox").value("");
		        	}	
				}
		});
 	}		
 	
 	
 	function registrationChange(){ 	
 		var value = this.dataItem();		
		var allottedSlot=value.allottedSlot;
		var contact=[];
		contact=value.contact.split(":");
		var mobile=contact[0];
		var email=contact[1];		
		var contactList ="<table><tr><td><img src='./person/getpersonimage/"+value.personId+"' width='80px' height='80px'/></td>"+
		"<td style='padding:10px;vertical-align: top;'><span>NAME</span></br><span>Contact Number</span></br><span>Email Address</span></br><span>Allotted Slot</span></td>"+
		"<td style='padding:10px;vertical-align: top;''><span>"+value.firstName+" "+value.lastName+"</span></br><span>"+mobile+"</span></br><span>"+email+"</span></br><span>"+allottedSlot+"</span></td></tr></table>";
		
		 $("#dialogBox").html("");
			$("#dialogBox").html(contactList);
			$("#dialogBox").dialog({
				modal : true,
				draggable: false,
				resizable: true,
				zIndex:100000,
				width:450,					
				buttons : {
					"Close" : function() {
						$(this).dialog("close");
					}
				}
		});		
			
 		var vehReg="vegReg="+$("#regNumber").data("kendoComboBox").value();
 		 $.ajax({
			 type : "POST",
 			 url : "./wrongParking/vehicleTagNo",
 			 data : vehReg,
 			 dataType : 'text',
 			 success : function(response) {
 				if(!(response=="")){
 					var v=response; 					
 				 	var value=v.split("="); 				 	
 				 	
 					$('input[name="vhTagNoWrong"]').val(value[0]);	  
	   				$('input[name="vhTagNoWrong"]').change(); 
	   				$('input[name="vhTagNoWrong"]').attr('readonly', 'readonly');					
			   		var comboBoxDataSource = new kendo.data.DataSource({
	 		            transport: {
	 		                read: {
	 		                    url     : "./vehicledetails/getwrongSlots/"+$("#regNumber").data("kendoComboBox").value(),		 		                    dataType: "json",
	 		                    type    : 'GET'
	 		                }
	 		            },
			 		           
			 		 });
			 		        
			         $("#slotdrop").kendoComboBox({
			            dataSource    : comboBoxDataSource,
			            dataTextField : "psSlotNo",
			            dataValueField: "psSlotNo",
			        });
			         $("#slotdrop").data("kendoComboBox").value("");
			             
 				}else{ 					
					$('input[name="vhTagNoWrong"]').val("");	  
	   				$('input[name="vhTagNoWrong"]').change(); 
	   				$('input[name="vhTagNoWrong"]').attr('readonly', 'readonly');	   				
   				    var comboBoxDataSource = new kendo.data.DataSource({
	 		            transport: {
	 		                read: {
	 		                    url     : "${getSlotNumber}",  // url to remote data source 
	 		                    dataType: "json",
	 		                    type    : 'GET'
	 		                }
	 		            },
 		           
 			 		}); 		        
		        	$("#slotdrop").kendoComboBox({
		          	  dataSource    : comboBoxDataSource,
		          	  dataTextField : "psSlotNo",
		          	  dataValueField: "psSlotNo",
		       		});
		            $("#slotdrop").data("kendoComboBox").value("");
 				}
 			 } 					
		  });	 	 
 	}
 	
 	function parkingSlotEditor(container, options) {
		  $('<input data-text-field="psSlotNo" style="width:180px;"  id="slotdrop"  data-value-field="psSlotNo" data-bind="value:' + options.field + '"/>')
		     		.appendTo(container).kendoComboBox({
		     			filter:"startswith",
		     			placeholder:"Select Parking Slots",
		     			autoBind: false,
		     			dataSource : {
							transport : {	
								read : "${getSlotNumber}"
							}
						},
						change : function (e) {
				            if (this.value() && this.selectedIndex == -1) {                    
				                alert("Parked Slot doesn't exist!");
				                $("#slotdrop").data("kendoComboBox").value("");
				        	}	
						}
		   });
	}		
		  
	
    $("#grid").on("click", ".k-grid-add", function(e) {
    	
    	securityCheckForActions("./parkingManagement/wrongparking/createButton");
    	
    	if($("#grid").data("kendoGrid").dataSource.filter()){
    		//$("#grid").data("kendoGrid").dataSource.filter({});
    		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
 			var grid = $("#grid").data("kendoGrid");
 			grid.dataSource.read();
 			grid.refresh();
        }
    	
        
	     var wpDt = $('input[name="wpDt"]').data("kendoDatePicker");
	     var today = new Date();
	     wpDt.max(today);
	     
    	
	   $(".k-window-title").text("Add Wrong Parking Details");
 	   $('.k-edit-field .k-input').first().focus(); 
 	   $(".k-grid-update").text("Create");	 	   
 	   $('label[for=vehicleId]').parent().remove();  
 	   $('label[for=status]').parent().remove();  
 	   $('input[name="status"]').hide();
 	   $('label[for="createdBy"]').parent().remove();
	   $('label[for="lastUpdatedBy"]').parent().remove();
	   $('label[for="lastUpdatedDate"]').parent().remove();
	   
	   $('input[name="vehicleId"]').parent().remove();		   
	   $('input[name="createdBy"]').parent().remove();
	   $('input[name="lastUpdatedBy"]').parent().remove();		 
	   $('input[name="lastUpdatedDate"]').parent().remove();
	   $('input[name="vhTagNoWrong"]').css('width', '180px');	  
	   $('label[for="undefined"]').parent().remove();
	   $('div[data-container-for="lastUpdatedDate"]').remove();		   
	    
	   $('label[for="psSlotNo"]').after('<label style=color:red;>&nbsp;*</label>'); 	     	
	   $('label[for="wpDt"]').after('<label style=color:red;>&nbsp;*</label>');	
	   $('label[for="vehicleNo"]').after('<label style=color:red;>&nbsp;*</label>');	
	   $('label[for="actionTaken"]').after('<label style=color:red;>&nbsp;*</label>');	
	   $('label[for="vhTagNoWrong"]').after('<label style=color:red;>&nbsp;*</label>');
  	 
	   $(".k-grid-update").click(function () {
		   var slotdrop=$("#slotdrop").data("kendoComboBox");	
		   var regdrop=$("#regNumber").data("kendoComboBox");
		   var ownership=$("#ownership").val();
		   
          
		   if (regdrop.select() == -1) {
	            alert("Please Select Proper Registration Number");
				return false;	
		   }if (slotdrop.select() == -1) {
	            alert("Please Select Proper Slot");
				return false;	
		   }if (ownership=="Select") {
	            alert("Please Select Proper Action");
				return false;	
		   }				  
		   
	   });
 	   $(".k-edit-field").each(function () {
  		  	 $(this).find("#temPID").parent().remove();
  	   });
 	  
 	  $('.k-edit-field .k-input').first().focus();   
 	 	
 	  
   });
   
    function edit(e) {
    	
    	securityCheckForActions("./parkingManagement/wrongparking/updateButton");
    	
    	if($("#grid").data("kendoGrid").dataSource.filter()){
    		//$("#grid").data("kendoGrid").dataSource.filter({});
    		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
 			var grid = $("#grid").data("kendoGrid");
 			grid.dataSource.read();
 			grid.refresh();
        }
    	
    	var wpDt = $('input[name="wpDt"]').data("kendoDatePicker");
	    var today = new Date();
	    wpDt.max(today);
    	
	  $(".k-window-title").text("Edit Wrong Parking Details"); 
  	  $('.k-edit-field .k-input').first().focus(); 
  	  $('label[for="createdBy"]').parent().remove();
  	  $('label[for="lastUpdatedBy"]').parent().remove();
  	  $('label[for="lastUpdatedDate"]').parent().remove();
  	  $('label[for="vehicleId"]').parent().remove();
  	  
  	  $('input[name="vehicleId"]').parent().remove();
  	  $('input[name="createdBy"]').parent().remove();
  	  $('input[name="lastUpdatedBy"]').parent().remove();		 
  	  $('input[name="lastUpdatedDate"]').parent().remove();
  	  $('input[name="vhTagNoWrong"]').css('width', '180px');	  
	  $('label[for="undefined"]').parent().remove();
	  $('div[data-container-for="lastUpdatedDate"]').remove();	
	  
	  $('label[for="psSlotNo"]').after('<label style=color:red;>&nbsp;*</label>'); 	     	
	   $('label[for="wpDt"]').after('<label style=color:red;>&nbsp;*</label>');	
	   $('label[for="vehicleNo"]').after('<label style=color:red;>&nbsp;*</label>');	
	   $('label[for="actionTaken"]').after('<label style=color:red;>&nbsp;*</label>');	
	   $('label[for="vhTagNoWrong"]').after('<label style=color:red;>&nbsp;*</label>');
 	 
	
  	  $(".k-grid-update").click(function () {
  		   var slotdrop=$("#slotdrop").data("kendoComboBox");	
  			$('input[name="vehicleNo"]').val(regdrop.val());
		   var regdrop=$("#regNumber").data("kendoComboBox");
		   var ownership=$("#ownership").val();
		   var allted=$('input[name="vhTagNoWrong"]').val();	
		   if (regdrop.select() == -1) {
	            alert("Please Select Proper Registration Number");
				return false;	
		   }if (slotdrop.select() == -1) {
	            alert("Please Select Proper Slot");
				return false;	
		   }if (ownership=="Select") {
	            alert("Please Select Proper Action");
				return false;	
		   }if (slotdrop.value()==allted) {
	            alert("Vehicle is Allowed in this Slot to Park");
				return false;	
		   }			  
  	  });
  	  $(".k-edit-field").each(function () {			
   	  $('label[for=status]').parent().remove();  
  	 	  $('input[name="status"]').hide();	   	 	   
  		  $(this).find("#temPID").parent().remove();
 	  });	  
	    
  	}      
     
     function OnRequestStart(e){
    	    $('.k-grid-update').hide();
	        $('.k-edit-buttons')
	                .append(
	                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
	        $('.k-grid-cancel').hide();
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
   				errorInfo += (i + 1) + ". "+ e.response.result[i].defaultMessage+"\n";
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
   				$("#alertsBox").html("Record Created Successfully");
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
		}  			
		
	}
   	 
   	$("#grid").on("click", "#temPID", function(e) {
			var button = $(this), enable = button.text() == "Generate Notice";
			var widget = $("#grid").data("kendoGrid");
			var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
			
			var result=securityCheckForActionsForStatus("./parkingManagement/WrongParking/deleteButton");   
		    if(result=="success"){
		    	if (enable) {
					$.ajax({
						type : "POST",
						url : "./WrongParking/wrongParkingStatus/" + dataItem.id + "/generateNotice",
						dataType : 'text',
						success : function(response) {
							$("#alertsBox").html("");
							$("#alertsBox").html(response);
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
							button.text('Notice Gennerated');
							$('#grid').data('kendoGrid').dataSource.read();
						}
					});
				} else {
					$.ajax({
						type : "POST",
						url : "./WrongParking/wrongParkingStatus/" + dataItem.id + "/noticeGenerated",
						dataType : 'text',
						success : function(response) {
							$("#alertsBox").html("");
							$("#alertsBox").html(response);
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
							button.text('Generate Notice');
							$('#grid').data('kendoGrid').dataSource.read();
						}
					});
				}
			}		  		
	});
   	
   	
	
    (function ($, kendo) {
         $.extend(true, kendo.ui.validator, {
              rules: { // custom rules           	 
             	 
                 
                  wpDtValidation: function (input, params) {
                      //check for the name attribute 
                      if (input.attr("name") == "wpDt") {
                     	 return $.trim(input.val()) !== "";
                      }
                      return true;
                  }, 
                  
                  
                  
              },
            //custom rules messages
              messages: { 
             	 
             	 wpDtValidation:"Wrong Parking Date is required", 
             	 
              }
         });
     })(jQuery, kendo);
   
    </script> 
<style>
	div.ui-dialog {position:fixed;overflow:"auto";} 
</style>
    
