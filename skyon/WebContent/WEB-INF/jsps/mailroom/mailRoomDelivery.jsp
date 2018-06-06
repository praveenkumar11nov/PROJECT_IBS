<%@include file="/common/taglibs.jsp"%>

	<style>
	.bgGreenColor{
background: #FF8484
}

.whiteColor{
background: #82CDFF
}
	</style>

	<c:url value="/mailroomDelivery/read" var="readUrl"/>
	<c:url value="/mailroomDelivery/update" var="updateUrl" />
	<c:url value="/mailroom/getBlockNames" var="getBlockNames" />
	<c:url value="/mailroom/getConsignmentNo" var="getConsignmentNo" />
	<c:url value="/mailroom/getPropertyNo" var="getPropertyNo" />
	<c:url value="/mailroom/addTo" var="addTo" />
	<c:url value="/mailroom/addFrom" var="addFrom" />
	<c:url value="/mailroom/getStatus" var="getStatus" />
	<c:url value="/mailroom/getCreatedBy" var="getCreatedBy" />
	<c:url value="/mailroom/getlastUpdatedBy" var="getlastUpdatedBy" />
	<c:url value="/mailroom/getDeliveredByName" var="getDeliveredByName" />
	<c:url value="/mailroom/getReceivedByName" var="getReceivedByName" />
	<c:url value="/mailroom/getReasons" var="getReasons" />
	<c:url value="/mailroom/getRedirectedAddress" var="getRedirectedAddress" />
		<c:url value="/mailroom/mailboxDate" var="mailboxDate" />
	
	
	<c:url value="/mailroom/getBlockNames" var="getBlockNames" />
            
	<kendo:grid name="grid" pageable="true"	filterable="true" sortable="true" reorderable="true" selectable="true" change="onChange"
	scrollable="true" groupable="true" resizable="true">
		<kendo:grid-editable mode="popup" confirmation="Are you sure you want to remove this item?" />
				
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="Clear_Filter" text="Clear Filter"/>
			<kendo:grid-toolbarItem text="Select" template=" <select id='statusValues'><option value=0>Select Status</option> <option value=1>Delivered</option><option value=2>Redirected</option><option value=3>Returned</option></select>"/>
			<kendo:grid-toolbarItem template="<input id='datepicker' style='margin-left:5px'/>"/>
			<kendo:grid-toolbarItem template="<textarea id='redirectedAddress' placeholder='Enter redirected address' style='width: 156px; height: 27px;margin-bottom: -7px;'></textarea>"/>
			<kendo:grid-toolbarItem text="SelectReason" template=" <select id='reasonsValue'><option value=0>Select Reason</option> <option value=1>Door Locked</option><option value=2>Not Availble</option><option value=3>Busy</option></select>"/>
			<kendo:grid-toolbarItem template="<input id='deliveredbytext' class='k-input' type='text' placeholder=' Enter Delivery boy info' style='margin-left:4px;height:20px'/>"/>
			<kendo:grid-toolbarItem template="<input id='receivedbytext' class='k-input' type='text' placeholder=' Received By Info' style='margin-left:4px;height:20px'/>"/>
			<kendo:grid-toolbarItem text="Update Status"/>
		</kendo:grid-toolbar>
		
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" ></kendo:grid-pageable>
			<kendo:grid-filterable extra="false">
		 		<kendo:grid-filterable-operators>
		  			<kendo:grid-filterable-operators-string eq="Is equal to"/>
		  			<kendo:grid-filterable-operators-date lte="Date Before" gte="Date After"/>
				</kendo:grid-filterable-operators>			
		</kendo:grid-filterable>
		
		<kendo:grid-columns>
		 <kendo:grid-column title="" headerTemplate="<input type='checkbox' class='headerCheckBox' onclick='selectAllCheckBox(this.id)' id='selectAllChkbx'/>" template="<input type='checkbox' onclick='selectSingleCheckBox(this.id)' id='singleselect_#=mlrId#' class='checkbox_#=status#'/>" width="40px" />
		 
			<kendo:grid-column title="MR_Id" field="mlrId" width="70px" hidden="true"/>	
			<kendo:grid-column title="Property Id" field="propertyId" width="65px" hidden="true"/>
			
			<kendo:grid-column title="Block Name" field="blockName" width="113px">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getBlockNames}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
	    		</kendo:grid-column>
			
			<kendo:grid-column title="Property Number" field="property_No"  width="140px">	
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function propertyNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getPropertyNo}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	 
	    	</kendo:grid-column>
			
			<kendo:grid-column title="Consignment Number" field="consignmentNo" width="160px">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function propertyNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getConsignmentNo}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	 
	    	</kendo:grid-column>
			
			<kendo:grid-column title="AddressedTo" field="addressedTo"  width="115px">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function propertyNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${addTo}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	 
	    	</kendo:grid-column>		
			
			
			<kendo:grid-column title="Addressed From" field="addressedFrom" width="132px">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function propertyNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${addFrom}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	 
	    	</kendo:grid-column>			
			
			<kendo:grid-column title="MailBox Received Date" field="mailboxDt" format="{0: dd/MM/yy hh:mm tt}" width="150px">
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
      </kendo:grid-column-filterable>
	    	</kendo:grid-column>		
			
			<kendo:grid-column title="Mail Delivered Date" field="mailNotifiedDt" format="{0: dd/MM/yy hh:mm tt}" filterable="true" width="145px">
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
      </kendo:grid-column-filterable>
			</kendo:grid-column>
			
			
			
			<kendo:grid-column title="Mail Redirected Date" field="mailRedirectedDt" format="{0: dd/MM/yy hh:mm tt}"  width="145px" >
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
      </kendo:grid-column-filterable>
			</kendo:grid-column>
			
			
			<kendo:grid-column title="Mail Returned Date" field="mailReturnedDt" format="{0: dd/MM/yy hh:mm tt}" width="145px" >
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
      </kendo:grid-column-filterable>
			</kendo:grid-column>						
			
			<kendo:grid-column title="Status" field="status" editor="statusTypes" width="105px">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function propertyNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getStatus}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	 
	    	</kendo:grid-column>			
				
			<kendo:grid-column title="Delivered By" field="note" width="135px">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function propertyNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getDeliveredByName}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	 
	    	</kendo:grid-column>
			
			<kendo:grid-column title="Received By" field="receivedBy" width="165px">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function propertyNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getReceivedByName}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	 
	    	</kendo:grid-column>
			
			
			<kendo:grid-column title="Redirected Address" field="mailRedirectedStatus" width="165px">		
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function propertyNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getRedirectedAddress}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	 
	    	</kendo:grid-column>	
			
			<kendo:grid-column title="Tower No." field="towerNo" width="92px" hidden="true"/>						 
			<kendo:grid-column title="DrGroup_Id" field="drGroupId" width="70px" hidden="true"/>
			
			<%-- <kendo:grid-column title="Returned Address" field="mailReturnedStatus" width="132px"/> --%>
			<kendo:grid-column title="Reasons" field="reasons" width="132px">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function propertyNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getReasons}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	 
	    	</kendo:grid-column>
			</kendo:grid-columns>
		
		<kendo:dataSource pageSize="10" requestEnd="onRequestEnd">
			<kendo:dataSource-transport>
			<kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-update url="${updateUrl}" dataType="json" type="POST" contentType="application/json" />				
				 <kendo:dataSource-transport-parameterMap>
					<script>
						function parameterMap(options, type) 
						{
							return JSON.stringify(options);
						}
					</script>
				</kendo:dataSource-transport-parameterMap>
			</kendo:dataSource-transport>
			<kendo:dataSource-schema parse="parse">
				<kendo:dataSource-schema-model id="mlrId">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="mlrId">
						</kendo:dataSource-schema-model-field>

						<kendo:dataSource-schema-model-field name="mailboxDt" type="date">
							<kendo:dataSource-schema-model-field-validation />
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="propertyId" type="number">
							<kendo:dataSource-schema-model-field-validation/>
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="mailNotifiedDt" type="date"/>
						
						<kendo:dataSource-schema-model-field name="mailRedirectedDt" type="date"/>
						
						<kendo:dataSource-schema-model-field name="mailReturnedDt" type="date"/>


						<kendo:dataSource-schema-model-field name="property_No" type="string">
							<kendo:dataSource-schema-model-field-validation />
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="addressedTo" type="string">
							<kendo:dataSource-schema-model-field-validation/>
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="addressedFrom" type="string">
							<kendo:dataSource-schema-model-field-validation/>
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="towerName" type="string">
							<kendo:dataSource-schema-model-field-validation/>
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="towerNo" type="string">
							<kendo:dataSource-schema-model-field-validation />
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="status" type="string" defaultValue="Mail_Received">
							<kendo:dataSource-schema-model-field-validation/>
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="mailRedirectedStatus" type="string">
							<kendo:dataSource-schema-model-field-validation />
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="drGroupId" type="number"/>
						
						<kendo:dataSource-schema-model-field name="reasons" type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="note" type="string">
							<kendo:dataSource-schema-model-field-validation required="true"/>
						</kendo:dataSource-schema-model-field> 

						<kendo:dataSource-schema-model-field name="receivedBy" type="string"/>
						
						<kendo:dataSource-schema-model-field name="drGroupId" type="number">
						</kendo:dataSource-schema-model-field>

					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
	</kendo:grid>
	<div id="alertsBox" title="Alert"></div>
	<script>
	
	
	/*****************************FOR SINGLE SELECT CHECKBOX CLICK****************************************/
	 //var globalIdVal = [];
	 var globalSelectedStatusVal = "";
	 var globalIdVal = [];
	 var globalSelectedStatus = "";
	 var globaldateVal = "";
	 var globalTextVal = "";
	 var globalDeliveryBoyInfo = "";
	 var globalReceivedByInfo = "";
	 var selectedStatus = "";
	 var newSelectedStatus = "";
	 
	 var mailstatus = "";
   	 var allIds = "";
   	 var SelectedDt = "";
   	 var convertedDate = "";
   	 var mailboxDts = [];
	 function onChange(args)
	 {
	 	 var gview = $("#grid").data("kendoGrid");
		 var selectedItem = gview.dataItem(gview.select());
		 mailstatus = selectedItem.status;
		 SelectedDt = selectedItem.mailboxDt;
		 //var date = new Date(SelectedDt);
		 //convertedDate = ("0" + date.getDate()).slice(-2)+'/'+("0" + (date.getMonth()+1)).slice(-2) + '/' +  date.getFullYear().toString().substr(2,2);
	 }
	 function selectSingleCheckBox(fieldId)
	 {  
		 securityCheckForActions("./mailroomManagement/mailroomDelivery/selectSingleCheckBox");
		 
		    if(mailstatus == 'Mail_Received')
			{
				alert("Consignment Not Sent From MailRoom");
				$("#"+fieldId).prop('checked',false);
				return false;
			}
	    	if(mailstatus == 'Delivered')
			{
				alert("Consignment Already Delivered");
				$("#"+fieldId).prop('checked',false);
				return false;
			}
	    	if(mailstatus == 'Redirected')
			{
				alert("Consignment Already Redirected");
				$("#"+fieldId).prop('checked',false);
				return false;
			}
	    	if(mailstatus == 'Returned')
			{
				alert("Consignment Retuned & stored in mailroom");
				$("#"+fieldId).prop('checked',false);
				return false;
			}
		 
	     	var temp = fieldId.split("_");
	    	if($("#"+fieldId).prop('checked') == true)
		    {
	    		//alert("Adding to Array>>"+temp[1]);
	    		globalIdVal.push(temp[1]);
	    		var date = SelectedDt;
				  date.setHours(0,0,0,0);
				  //alert(date);
				  mailboxDts.push(date);
	    		//mailboxDts.push(SelectedDt);
		    }		    
	    	else if($("#"+fieldId).prop('checked') == false)
		    {
	    		//alert("Remove From Array>>"+temp[1]);
	    		globalIdVal.splice($.inArray(temp[1], globalIdVal),1);
		    }
	    	if(globalIdVal.length > 0)
		    {
	    		$("#statusValues").kendoDropDownList({
			            dataTextField: "text",
			            dataValueField: "value",
			            index: 0,
			    });	
	    		
	    		/**** For Refreshing the dropsownList ****/
				var dropdownlist = $("#statusValues").data("kendoDropDownList");
		    	dropdownlist.value(0);
		    	/**** Refreshing Over ****/
	    		
	    		$(document).on('change', 'select[id="statusValues"]', function() 
			    {
	    			globalSelectedStatusVal = $('#statusValues option:selected').eq(0).text();
	    		    if(globalSelectedStatusVal == 'Delivered')
		    	    {
		    			$('.k-picker-wrap').show();
						$('.k-grid-UpdateStatus').show();
						$('#deliveredbytext').show();
						$('#receivedbytext').show();
						$('#reasonsValue').hide();
						$('#redirectedAddress').hide();
		    		}
	    		    else if(globalSelectedStatusVal == 'Redirected')
					{
					 	$('#redirectedAddress').show();
					 	$('.k-grid-UpdateStatus').show();
					 	$('.k-picker-wrap').show();
					  	$('#deliveredbytext').show();
					  	$('#receivedbytext').show();
						$('#reasonsValue').hide();
					}
	    		    else if(globalSelectedStatusVal == 'Returned')
					{
						/* $("#reasonsValue").kendoDropDownList({
						     dataTextField: "text",
						     dataValueField: "value",
						     index: 0,
						 }); */
						 $("#reasonsValue").parent().show();
						 $('.k-grid-UpdateStatus').show();
						 $('.k-picker-wrap').show();
						 $('#deliveredbytext').show();
						 $('#redirectedAddress').hide();
						 $('#receivedbytext').hide();
					}
	    		    else if(globalSelectedStatusVal == 'Select Status')
					{
						$('.k-picker-wrap').hide();
						$('.k-grid-UpdateStatus').hide();
						$('#deliveredbytext').hide();
						$('#receivedbytext').hide();
						$('#redirectedAddress').hide();
						$("#reasonsValue").hide();
					}
			    });
	    		
		    }
	    	if(globalIdVal.length < 1)
		    {
	    		$("#statusValues").parent().hide();
	    		$('input').attr('checked' , false);
			 	$("#statusValues").parent().hide();	
				$('#reasonsValue').parent().hide();		
				$('.k-picker-wrap').hide();
				$('.k-grid-UpdateStatus').hide();
				$('#redirectedAddress').hide();
				$('#deliveredbytext').hide();
				$('#receivedbytext').hide();
		    }
	    }
		$("#grid").on("click", ".k-grid-UpdateStatus", function()
        {
			securityCheckForActions("./mailroomManagement/mailroomDelivery/updateButton");
		     globaldateVal = $("#datepicker").val();
		     dropDownVal = $("#statusValues").val();
		     globalDeliveryBoyInfo = $("#deliveredbytext").val();
	  	     globalReceivedByInfo = $('#receivedbytext').val();
	  	     
	  	     /******* FOR DELIVRED UPDATE BUTTON CLICK  *******/
	  	     if(dropDownVal == 1)
	  	     {
		  		   var reason = "";
		    	   var redirectAddress = "";
		  		   newSelectedStatus = "Delivered";
		  		   if(globaldateVal == '') {
				       alert("Select Date");	
				   }
				   else if(globalDeliveryBoyInfo == ''){
					   alert("Select Delivered By Name");	
				   }
				   else if(globalReceivedByInfo == ''){
					   alert("Enter Received By Name");	
				   }
				   else
				   {
				       $.ajax({
							   	type : "POST",
							   	dataType : "text",
							    url : "./mailroomDelivery/updateSingleMailRoomDelivery",
							    data : 
							    { 				
							    	id1 : globalIdVal.toString(),
							    	selectedStatus : newSelectedStatus,
							    	selectedDate : globaldateVal,
							    	deliveryBoyName : globalDeliveryBoyInfo,
							    	receivedByName : globalReceivedByInfo,
							    	reason : reason,
							    	redirectAddress : redirectAddress
							    }, 
							    success : function(response) 
							    {
							    	alert(response);
							        window.location.reload();
							    } 
						    });					        	
				    	    //RenderLinkInner('mailroomDelivery');
			       }   
	  	      }
	  	     /********  END FOR DELIVERED STATUS BUTTON CLICK **********/
	  	     
	  	     /********  FOR REDIRECTED STATUS BUTTON CLICK  **********/
	  	     if(dropDownVal == 2)
		  	 {
		  	      var reason = "";
		  	      globalDeliveryBoyInfo = $("#deliveredbytext").val();
			      var redirectAddress = $('#redirectedAddress').val();
			      newSelectedStatus = "Redirected";
			    	  
			      if(globaldateVal == '')
			      {
			          alert("Select Date");	
			      }
			      else if(redirectAddress == '')
			      {
			          alert("Enter Redirected Addresss");	
			      }
			      else if(globalDeliveryBoyInfo == '')
				  {
				      alert("Select Delivered By Name");	
				  }
			      else if(globalReceivedByInfo == '')
				  {
				      alert("Enter Received By Name");	
				  }
			      else
		          {
		    	  		$.ajax({
		      				type : "POST",
		      				dataType:"text",
		      				url : "./mailroomDelivery/updateSingleMailRoomDelivery",
		      				data : { 				
			      				id1 : globalIdVal.toString(),
			      				selectedStatus : newSelectedStatus,
						    	selectedDate : globaldateVal,
						    	deliveryBoyName : globalDeliveryBoyInfo,
						    	receivedByName : globalReceivedByInfo,
						    	reason : reason,
						    	redirectAddress : redirectAddress
		      			    }, 
		      			 success : function(response)
		      			 {
		      				alert(response);
					        window.location.reload();
		      			 } 
		      		  });
		        	}
		  	  }	  	     
	  	     /********  END FOR REDIRECTED STATUS BUTTON CLICK *******/
	  	     
	  	     
	  	     /********  FOR RETURNED STATUS BUTTON CLICK  **********/
	  	     if(dropDownVal == 3)
		  	 {
		  	     globalDeliveryBoyInfo = $("#deliveredbytext").val();
			     var reason = $('#reasonsValue option:selected').eq(0).text();
			     var redirectAddress = "";
			     newSelectedStatus = "Returned";
			    	  
			     if(globaldateVal == '')
			     {
			         alert("Select Date");	
			     }
			     else if(reason == 'Select Reason')
			     {
			      	 alert("Select Reason For Returned");	
			     }
			     else if(globalDeliveryBoyInfo == '')
				 {
				     alert("Select Delivered By Name");	
				 }					        	
			     else
			     {
			      	 $.ajax({
				      		  type : "POST",
				     		  dataType:"text",
				      		  url : "./mailroomDelivery/updateSingleMailRoomDelivery",
				      		  data : { 				
				      		  	 id1 : globalIdVal.toString(),
				      	 		 selectedStatus : newSelectedStatus,
				      			 selectedDate : globaldateVal,
							     deliveryBoyName : globalDeliveryBoyInfo,
							     receivedByName : globalReceivedByInfo,
							     reason : reason,
							     redirectAddress : redirectAddress
				      		  }, 
				      	      success : function(response) 
				      	      {
				      			 alert(response);
				      			 window.location.reload();
				      		  } 
				      		});
			         }
		  	 }	  	     
	  	     /********  END FOR RETURNED STATUS BUTTON CLICK *******/
     });	
	/***************************END FOR SINGLE SELECT CHECKBOX*************************************/
	
	
	
	
      ////////////////////////////////////////--------SELECT ALL CHECKBOX--------/////////////////////////////////////////////////////////	   	
	  function selectAllCheckBox(fieldId)
	  {
		  securityCheckForActions("./mailroomManagement/mailroomDelivery/selectAllCheckBox");
		
	      if($("#"+fieldId).prop('checked') == true)
	      {
	    	  var uomDetailsGrid = $('#grid').data("kendoGrid");
			  var data = uomDetailsGrid.dataSource.data();			
			  $.each(data, function( index, val ) 
			  {
				  if(val.status == "Out_For_Delivery")
			      {
					  globalIdVal.push(val.mlrId);
					  var date = val.mailboxDt;
					  date.setHours(0,0,0,0);
					  mailboxDts.push(date);
					  $("#singleselect_"+val.mlrId).prop('checked',true);
			      }
		      });	
			  if(globalIdVal.length > 0)
			  {				  
				  $("#statusValues").kendoDropDownList({
			            dataTextField: "text",
			            dataValueField: "value",
			            index: 0,
			      });	
		    	  
				  /**** For Refreshing the dropsownList ****/
				  var dropdownlist = $("#statusValues").data("kendoDropDownList");
		    	  dropdownlist.value(0);
		    	  /**** Refreshing Over ****/
		    	  
		    	  
				  $(document).on('change', 'select[id="statusValues"]', function() 
			      {
				    	globalSelectedStatusVal = $('#statusValues option:selected').eq(0).text();
				    	if(globalSelectedStatusVal == 'Delivered')
					    {
					    	$('.k-picker-wrap').show();
							$('.k-grid-UpdateStatus').show();
							$('#deliveredbytext').show();
							$('#receivedbytext').show();
							$('#reasonsValue').hide();
							$('#redirectedAddress').hide();
					    }
				    	else if(globalSelectedStatusVal == 'Redirected')
						{
						 	$('#redirectedAddress').show();
						 	$('.k-grid-UpdateStatus').show();
						 	$('.k-picker-wrap').show();
						  	$('#deliveredbytext').show();
						  	$('#receivedbytext').show();
							$('#reasonsValue').hide();
						}
				    	else if(globalSelectedStatusVal == 'Returned')
						{
							/* $("#reasonsValue").kendoDropDownList({
							     dataTextField: "text",
							     dataValueField: "value",
							     index: 0,
						    }); */
						    $('#reasonsValue').parent().show();
							$('.k-grid-UpdateStatus').show();
							$('.k-picker-wrap').show();
							$('#deliveredbytext').show();
							$('#redirectedAddress').hide();
							$('#receivedbytext').hide();
						}
				    	else if(globalSelectedStatusVal == 'Select Status')
						{
						    $('.k-picker-wrap').hide();
							$('.k-grid-UpdateStatus').hide();
							$('#deliveredbytext').hide();
							$('#receivedbytext').hide();
							$('#redirectedAddress').hide();
							$("#reasonsValue").hide();
						}
				  });
			  }
			  else
			  {
				  alert("No Consingments to deliver");	
				  $("#"+fieldId).prop('checked',false);				  
			  }
	    	}
	    	else
	    	{
	    		//alert("Unchecked");
	    		$('input').attr('checked' , false);
			 	$("#statusValues").parent().hide();	
				$('#reasonsValue').parent().hide();		
				$('.k-picker-wrap').hide();
				$('.k-grid-UpdateStatus').hide();
				$('#redirectedAddress').hide();
				$('#deliveredbytext').hide();
				$('#receivedbytext').hide();
				
				var grid = $("#grid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
				
	    		for(var i = 0;i<globalIdVal.length;i++)
	    		{
	    			$("#singleselect_"+globalIdVal[i]).prop('checked',false);
	    		}
	    		globalIdVal = [];
	    	}
	    }
        ////////////////////////////////////////--------END FOR SELECT ALL CHECKBOX--------/////////////////////////////////////////////////////////

	function parse (response) 
	{   
	             $.each(response, function (idx, elem) {
	             if (elem.mailboxDt && typeof elem.mailboxDt === "string") {
	                 elem.mailboxDt = kendo.parseDate(elem.mailboxDt, "yyyy-MM-ddTHH:mm:ss.fffZ");
	             }
	             if (elem.mailNotifiedDt && typeof elem.mailNotifiedDt === "string") {
	                 elem.mailNotifiedDt = kendo.parseDate(elem.mailNotifiedDt, "yyyy-MM-ddTHH:mm:ss.fffZ");
	             }
	             if (elem.mailRedirectedDt && typeof elem.mailRedirectedDt === "string") {
	                 elem.mailRedirectedDt = kendo.parseDate(elem.mailRedirectedDt, "yyyy-MM-ddTHH:mm:ss.fffZ");
	             }
	             if (elem.mailReturnedDt && typeof elem.mailReturnedDt === "string") {
	                 elem.mailReturnedDt = kendo.parseDate(elem.mailReturnedDt, "yyyy-MM-ddTHH:mm:ss.fffZ");
	             }
	         });
	         return response;
	 }
	 var count = 0;
	$(document).ready(function() 
    {		
		$("#reasonsValue").kendoDropDownList({
		     dataTextField: "text",
		     dataValueField: "value",
		     index: 0,
	    });
		
		$("#datepicker").kendoDateTimePicker({
			format: "dd/MM/yyyy hh:mm tt",
			change: onDateChange
        });
		function onDateChange()
		{
			var datetimepicker = $("#datepicker").data("kendoDateTimePicker");
			dateVal = datetimepicker.value();
			var futureDtCount = 0;
			$.each(mailboxDts,function(id,value)
		    {
				if((new Date(dateVal).getTime())>new Date().getTime())
				{	
					if(futureDtCount == 0)
					{
					   alert("No Future Date Selection Allowed");
					   /* $('#reasonsValue').parent().hide();
					   futureDtCount++;	 */
					}
					$("#singleselect_"+globalIdVal[id]).prop('checked',true);
					   globalIdVal.splice($.inArray(globalIdVal[id], globalIdVal),1);
					   
					   //alert(globalIdVal);
	  					$("#statusValues").parent().hide();
			    		$('input').attr('checked' , false);
					 	$("#statusValues").parent().hide();	
						//$('#reasonsValue').parent().hide();		
						$('.k-picker-wrap').hide();
						$('.k-grid-UpdateStatus').hide();
						$('#redirectedAddress').hide();
						$('#deliveredbytext').hide();
						$('#receivedbytext').hide();
					var grid = $("#grid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh(); 
				}
				if( (new Date(dateVal).getTime()) > (new Date(value).getTime()) || (new Date(dateVal).getTime()) == (new Date(value).getTime()))
				{
					//alert("Eligible");
				}
				else
				{				       
				    if(count == 0)
				    {
				      	alert("Some Consignements Cannot Be Selected Due To Invalid Selection Of Date");				        	
				      	count++;				      	
				    }	
					var grid = $("#grid").data("kendoGrid");
				 	var rowSelector = $("#singleselect_"+globalIdVal[id]).closest('td').closest('tr');//">tr:nth-child(" + (i + 1) + ")";
				  	var row = grid.tbody.find(rowSelector);
				 	row.addClass("bgGreenColor"); 
					$("#singleselect_"+globalIdVal[id]).prop('checked',false);
					globalIdVal.splice($.inArray(globalIdVal[id], globalIdVal),1);
					if(globalIdVal.length == 0)
				    { 	
			    		$("#statusValues").parent().hide();
			    		$('input').attr('checked' , false);
					 	$("#statusValues").parent().hide();	
						$('#reasonsValue').parent().hide();		
						$('.k-picker-wrap').hide();
						$('.k-grid-UpdateStatus').hide();
						$('#redirectedAddress').hide();
						$('#deliveredbytext').hide();
						$('#receivedbytext').hide();
				    }
				}
				
			});
			//alert(dateVal);
		}
		$("#datepicker").prop('disabled', true);
		
		$("#statusValues").hide();	
		$('#reasonsValue').parent().hide();		
		$('.k-picker-wrap').hide();
		$('.k-grid-UpdateStatus').hide();
		$('#redirectedAddress').hide();
		$('#deliveredbytext').hide();
		$('#receivedbytext').hide();
    });
	
	$("#grid").on("click", ".k-grid-Clear_Filter", function()
	{
	    $("form.k-filter-menu button[type='reset']").slice().trigger("click");
		var grid = $("#grid").data("kendoGrid");
		grid.dataSource.read();
		grid.refresh();
	});
	
	function onRequestEnd(e) 
	{
		if (typeof e.response != 'undefined')
		{
		if (e.response.status == "FAIL")
		{
			errorInfo = "";			
			errorInfo = e.response.result.invalid;			
			for (i = 0; i < e.response.result.length; i++) 
			{
				errorInfo += (i + 1) + ". "	+ e.response.result[i].defaultMessage + "\n";
			}
			if (e.type == "create") 
			{
				alert("Error: Creating the MailRoom Details\n\n" + errorInfo);
			}
			if (e.type == "update") 
			{
				alert("Error: Updating the MailRoom Details\n\n" + errorInfo);
			}			
			var grid = $("#grid").data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
			return false;
		}}
		if (e.type == "update" && !e.response.Errors) 
		{
			alert("Status Updated Successfully");
			e.sender.read();
		}
		if (e.type == "create" && !e.response.Errors)
		{
			alert("Consignment details ADDED successfully");
			e.sender.read();
		}		
	}
	
	function statusTypes(container, options)
	{
		var booleanData = [ {
			text : 'Delivered',
			value : "Delivered"
		}, {
			text : 'Redirected',
			value : "Redirected"
		},{
			text : 'Returned',
			value : "Returned"
		}];

		$('<input />').attr('data-bind', 'value:status').appendTo(container).kendoComboBox
		({
			dataSource : booleanData,
			dataTextField : 'text',
			dataValueField : 'value',
			//change: onChangeEdit
		});
	}	
	
	var res = [];
	(function($, kendo) {
		$
				.extend(
						true,
						kendo.ui.validator,
						{
							rules : { 
								NoteFieldValidation : function(input,
										params) {
									//check for the name attribute 
									if (input.filter("[name='note']").length
											&& input.val()) {
										return /^[a-zA-Z]+[._a-zA-Z0-9]*[a-zA-Z0-9]$/.test(input.val());
									}
									return true;
								}
								},
							messages : { 
								NoteFieldValidation: "Note Field can not allow special symbols & numbers"
							}
						});
	})(jQuery, kendo);
	
	
	/***********************************/
	$("#grid").kendoValidator({
        rules:
        {
            custom: function (input, params) 
            {
                if (input.is("[name=mailboxDt]") || input.is("[name=mailNotifiedDt]")) 
                {                	
                    var container = $(input).closest("tr");
                    var start = container.find("input[name=mailboxDt]").data("kendoDatePicker").value();
                    var end = container.find("input[name=mailNotifiedDt]").data("kendoDatePicker").value();
                    if (start < end) 
                    {
                        return false;
                    }
                }
                return true;
            }
        },
        messages: 
        {
            custom: function (input) 
            {
                return "Start Date must be greater than End Date!";
            }
        }
    });
	</script>
