<%@include file="/common/taglibs.jsp"%>

<c:url value="/vendorRequestsUrl/readVendorRequestsUrl" var="readVendorRequestsUrl" />
<c:url value="/vendorRequestsUrl/createVendorRequestsUrl" var="createVendorRequestsUrl" />
<c:url value="/vendorRequestsUrl/updateVendorRequestsUrl" var="updateVendorRequestsUrl" />
<c:url value="/vendorRequestsUrl/destroyVendorRequestsUrl" var="destroyVendorRequestsUrl" />

<!-- URL FOR READING VENDOR NAMES -->
<c:url value="/vendorRequest/getVendorNamesForvendorRequests" var="vendorNamesAutoUrl" />
<!-- END FOR READING VENDOR NAMES -->

<!-- URL TO READ VENDOR CONTRACT -->
<c:url value="/vendorInvoice/readVendorContracts" var="readVendorContracts" />
<!-- END FOR VENDOR CONTRACT URL -->

<!-- URLS'S FOR STATUS UPDATION -->
<c:url value="/vendorRequestsUrl/statusUpdateForVendorRequests" var="statusUpdateForVendorRequests" />
<!-- END FOR STATUS UPADATION -->

<!-- URLS'S FOR FILTER -->
<c:url value="/vendorRequestsUrl/getVendorNamesFilter" var="getVendorNameFilter" />
<c:url value="/vendorRequestsUrl/getVendorContractFilter" var="getVendorContractFilter" />
<c:url value="/vendorRequestsUrl/getReqTypeFilterFilter" var="getReqTypeFilterFilter" />
<c:url value="/vendorRequestsUrl/getReqNoteFilter" var="getReqNoteFilter" />
<c:url value="/vendorRequestsUrl/getReplyNoteFilter" var="getReplyNoteFilter" />
<c:url value="/vendorRequestsUrl/getStatusFilter" var="getStatusFilter" />

<kendo:grid name="vendorRequestsGrid" pageable="true" resizable="true" filterable="true" sortable="true" reorderable="true" selectable="true" 
	scrollable="true" groupable="true" edit="vendorRequestsGridEvent" change="onChange">	
		<kendo:grid-editable mode="popup"/>
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Vendor Requests"/>
			<kendo:grid-toolbarItem text="Clear Filter" name="Clear_Filter"/>
		</kendo:grid-toolbar>
		
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" ></kendo:grid-pageable>
			<kendo:grid-filterable extra="false">
		 		<kendo:grid-filterable-operators>
		  			<kendo:grid-filterable-operators-string eq="Is equal to"/>
				</kendo:grid-filterable-operators>			
		</kendo:grid-filterable>
		
		<kendo:grid-columns>
		    <kendo:grid-column title="Vendor ContractId" field="vrId" width="0px" hidden="true"/>	
		    
		    <kendo:grid-column title="Vendors&nbsp;*" field="vendorName" width="75px">
		    <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getVendorNameFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	 </kendo:grid-column-filterable>
		    </kendo:grid-column>
		    
		    <kendo:grid-column title="Vendors&nbsp;*" field="vendorId" editor="readVendorTypes" width="0px" hidden="true"/>
		    
		    <kendo:grid-column title="Vendor Contract&nbsp;" field="vendorInvoiceDetails" width="80px">
		    <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getVendorContractFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	 </kendo:grid-column-filterable>
		    </kendo:grid-column>
		    
		    <kendo:grid-column title="Vendor Contract&nbsp;" field="vcId" width="40px" hidden="true" editor="ReadVendorContracts"/>
		    
		    <kendo:grid-column title="Request Type&nbsp;*" field="requestType" editor="requestTypeEditor" width="60px">
		    <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getReqTypeFilterFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	 </kendo:grid-column-filterable>
		    </kendo:grid-column>
		    
		    <kendo:grid-column title="Request Note&nbsp;*" field="requestNote" editor="requestNoteEditor" width="60px">
		    <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getReqNoteFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	 </kendo:grid-column-filterable>
		    </kendo:grid-column>
		    
		    <kendo:grid-column title="Reply Note&nbsp;*" field="replyNote" editor="replyNoteEditor" width="60px">
		    <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getReplyNoteFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	 </kendo:grid-column-filterable>
		    </kendo:grid-column>
		    
		    <kendo:grid-column title="Status" field="status" width="60px">
		    <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getStatusFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	 </kendo:grid-column-filterable>
		    </kendo:grid-column>
		    
		    <kendo:grid-column title="Created By" field="createdBy" width="0px" hidden="true"/>
		    <kendo:grid-column title="Last Updated By" field="lastUpdatedBy" width="0px" hidden="true"/>
		    
		    
			<kendo:grid-column title="&nbsp;" width="85px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit" text="Edit"/>
					<kendo:grid-column-commandItem name="Update Status" click="updateVendorRequestClick"/>
				</kendo:grid-column-command>
			</kendo:grid-column>
			
			<%-- <kendo:grid-column title=""
				template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Activate#=data.vcId#'>#= data.status == 'Approved' ? 'Reject' : 'Approve' #</a>"
				width="100px" /> --%>
			
		</kendo:grid-columns>		
		<kendo:dataSource requestEnd="onVendorRequestEnd" requestStart="onVendorRequestStart">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-read url="${readVendorRequestsUrl}" dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-create url="${createVendorRequestsUrl}" dataType="json" type="GET" contentType="application/json" />
				<kendo:dataSource-transport-update url="${updateVendorRequestsUrl}" dataType="json" type="GET" contentType="application/json" />
				<kendo:dataSource-transport-destroy url="${destroyVendorRequestsUrl}" dataType="json" type="GET" contentType="application/json" />			
			</kendo:dataSource-transport>
			
			<kendo:dataSource-schema>
				<kendo:dataSource-schema-model id="vrId">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="vrId" type="number"/>
						<kendo:dataSource-schema-model-field name="vendorId"/>
						<kendo:dataSource-schema-model-field name="vcId"/>	
						<kendo:dataSource-schema-model-field name="VendorInvoiceDetails"/>						
						<kendo:dataSource-schema-model-field name="requestType" type="string"/>
						<kendo:dataSource-schema-model-field name="requestNote" type="string"/>
						<kendo:dataSource-schema-model-field name="replyNote" type="string"/>
						<kendo:dataSource-schema-model-field name="status" type="string" defaultValue="Created"/>
						<kendo:dataSource-schema-model-field name="createdBy" type="string"/>
						<kendo:dataSource-schema-model-field name="lastUpdatedBy" type="string"/>						
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
	</kendo:grid>
	
	<div id="alertsBox" title="Alert"></div>
	
	<div id="upadteVendorStatusWindow" style="display: none;"><br/>
	
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label for="Status">Reply Note</label>&nbsp;&nbsp;&nbsp;*
	      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea style="width: 150px; height:-46px;" name="replyNoteTextArea" id="replyNoteValue"></textarea>
	      <br/><br/>
	      
	    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label for="Status">Status</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*
	      <select id="selectVendorRequestStatus" style='width:150px;'>
                <option>Select</option>
                <option>Open</option>
                <option>Answered</option>
                <option>Closed</option>
          </select>
          
          <br/><br/><br/>
           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button type="submit" class="k-button" id="updateVendorRequestButton" onclick="updateVendorStatus()">Update Status</button>
           <div id='loader' style="display: none">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;</div>
           
	</div>
	
	
	<script>
	 $("#vendorRequestsGrid").on("click", ".k-grid-Clear_Filter", function()
     {		
	   	    $("form.k-filter-menu button[type='reset']").slice().trigger("click");
	   		var grid = $("#vendorRequestsGrid").data("kendoGrid");
	   		grid.dataSource.read();
	   		grid.refresh();
	 });
	 
	 $("#vendorRequestsGrid").on("click", ".k-grid-add", function() 
	 {
	    if($("#vendorRequestsGrid").data("kendoGrid").dataSource.filter())
		{
		    $("form.k-filter-menu button[type='reset']").slice().trigger("click");
		 	var grid = $("#vendorRequestsGrid").data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
	    }
	 });
	 
	 var reply="";
	 var vendorReqStatus="";
	 var SelectedItemMasterRowId = "";

	 function onChange(e)
	    {
	    	var gview = $("#vendorRequestsGrid").data("kendoGrid");
			var selectedItem = gview.dataItem(gview.select());
			reply = selectedItem.replyNote;
			vendorReqStatus = selectedItem.status;
		    SelectedItemMasterRowId = selectedItem.vrId;

	    }
	
	function vendorRequestsGridEvent(e)
	{
		$('.k-edit-field .k-input').first().focus();	
		
		
		$('label[for=vrId]').parent().hide();
		$('div[data-container-for="vrId"]').hide();
		
		$('label[for=vendorName]').parent().hide();
		$('div[data-container-for="vendorName"]').hide();
		
		$('label[for=replyNote]').parent().remove();
		$('div[data-container-for="replyNote"]').remove();
		
		$('label[for=vendorName]').parent().hide();
		$('div[data-container-for="vendorName"]').hide();
		
		$('label[for=status]').parent().hide();
		$('div[data-container-for="status"]').hide();
			
		$('label[for=createdBy]').parent().hide();
		$('div[data-container-for="createdBy"]').hide();
			
		$('label[for=lastUpdatedBy]').parent().hide();
		$('div[data-container-for="lastUpdatedBy"]').hide();
		
		$('label[for=vendorName]').parent().hide();
		$('div[data-container-for="vendorName"]').hide();
		
		$('label[for=vendorInvoiceDetails]').parent().hide();
		$('div[data-container-for="vendorInvoiceDetails"]').hide();
		
		$(".k-grid-cancel").click(function () 
		{
		    var grid = $("#vendorRequestsGrid").data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
		});	
		
		$(".k-link").click(function () 
		{
		    var grid = $("#vendorRequestsGrid").data("kendoGrid");
		    grid.dataSource.read();
			grid.refresh();
			grid.refresh();
	    });		
		if (e.model.isNew()) 
		 {		 
			securityCheckForActions("./vendorManagement/vendorRequests/createButton");
			$(".k-window-title").text("Add Vendor Requests");
			$(".k-grid-update").text("Save");
		 }
		 else
		 {
			securityCheckForActions("./vendorManagement/vendorRequests/updateButton");	 
			$(".k-window-title").text("Edit Vendor Requests");
			$(".k-grid-update").text("Update");
		 }
	}
	
	/******  TEXTAREA EDITOR FOR REQUEST-NOTE  *****/
	function requestNoteEditor(container, options) 
	{
	      $('<textarea name="Request Note" data-text-field="requestNote" data-value-field="requestNote" data-bind="value:' + options.field + '" style="width: 148px; height: 46px;" required="true"/>')
	      .appendTo(container);
	      $('<span class="k-invalid-msg" data-for="Request Note"></span>').appendTo(container); 
	}
	/******  END FOR REQUEST-NOTE TEXTAREA  ******/
	
	/******  TEXTAREA EDITOR FOR REPLY-NOTE  *****/
	function replyNoteEditor(container, options) 
	{
	      $('<textarea name="Reply Note" data-text-field="replyNote" data-value-field="replyNote" data-bind="value:' + options.field + '" style="width: 148px; height: 46px;" required="true"/>')
	      .appendTo(container);
	      $('<span class="k-invalid-msg" data-for="Reply Note"></span>').appendTo(container); 
	}
	/******  END FOR REPLY-NOTE TEXTAREA  ******/
	
	/****  DROPDOWN FOR REQUEST TYPE  *****/
	function requestTypeEditor(container, options)
	{
		var booleanData = [ {
				text : '--Select--',
				value : ""
			}, {
				text : 'Alert',
				value : "Alert"
			}, {
				text : 'Reply',
				value : "Reply"
			},{
				text : 'Request',
				value : "Request"
			}];
			$('<input name="Request Type" required="true"/>').attr('data-bind','value:requestType').appendTo(container).kendoDropDownList({
				dataSource : booleanData,
				dataTextField : 'text',
				dataValueField : 'value',
			}); 
			$('<span class="k-invalid-msg" data-for="Request Type"></span>').appendTo(container); 
	}
	/*****  END FOR DROPDOWN  ****/
	
	 /*******  FOR READING VENDORS NAMES  ***********/
	 function readVendorTypes(container, options) 
	 {
			$('<input name="Vendor" id="vendorId" data-text-field="vendorName" data-value-field="vendorId" data-bind="value:' + options.field + '" required="true"/>')
			.appendTo(container).kendoComboBox({
				autoBind : false,
				placeholder : "Select vendor",
				headerTemplate : '<div class="dropdown-header">'
					+ '<span class="k-widget k-header">Photo</span>'
					+ '<span class="k-widget k-header">Contact info</span>'
					+ '</div>',
				template : '<table><tr><td rowspan=2><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.personId#'/>" width=40 height=55 alt=\"No Image to Display\" /></span></td>'
					+ '<td align="left"><span class="k-state-default"><b>#: data.vendorName #</b></span><br>'
					+ '<span class="k-state-default"><i>#: data.personType #</i></span></td></tr></table>',
				dataSource : {
					transport : {		
						read :  "${vendorNamesAutoUrl}"
					}
				},
				change : function (e) {
		            if (this.value() && this.selectedIndex == -1) {                    
		                alert("Vendor doesn't exist!");
		                $("#vendorId").data("kendoComboBox").value("");
		        	}
			    } 
			});
			
			$('<span class="k-invalid-msg" data-for="Vendor"></span>').appendTo(container);
	 }
	 /*******  END FOR READING VENDORS NAMES ********/
	
	 
	 /******  FOR READING VENDOR CONTRACTS TEMPLATE  ******/
	 function ReadVendorContracts(container,options)
	 {
		 $('<input data-text-field="vendorInvoiceDetails" name="Vendor Contracts" id="test" data-value-field="vcId" data-bind="value:' + options.field + '"/>')
         .appendTo(container).kendoComboBox({
        	 /* optionLabel : "Select", */
        	 cascadeFrom : "vendorId",
        	 placeholder : "Select Contract",
     		    template : '<table><border><tr><td rowspan=2><span class="k-state-default"></span></td>'
			+ '<td padding="5px"><span class="k-state-default"><b>#: data.vendorInvoiceDetails #</b></span><br></td></tr>'
			+'<tr><td rowspan=2 style="padding: 5px;font-size: 10px;"><b>#: data.contractName #</b></td></tr></border></table>', 
		   dataSource: {       
	            transport: {
	                read: "${readVendorContracts}"
	            }
	        },
	         change : function (e) {
		        if (this.value() && this.selectedIndex == -1) {                    
		                alert("Contract doesn't exist!");
		                return false;
		    } 
	        }
	    });
		//$('<span class="k-invalid-msg" data-for="Vendor Contracts"></span>').appendTo(container);
	 }	 
	 /****** END FOR READING VENDOR CONTRACTS TEMPLATE  *****/
	 
	 /************** ON REQUEST END FUNCTION  **********************/
	  function onVendorRequestEnd(e) 
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
					alert("Error: Creating the Vendor Requests Details\n\n" + errorInfo);
				}
				if (e.type == "update") 
				{
					alert("Error: Updating the Vendor Requests Details\n\n" + errorInfo);
				}
				var grid = $("#vendorRequestsGrid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
				return false;
			}
		}
		if (e.type == "update" && !e.response.Errors) 
		{	
			e.sender.read();
			//alert("Vendor Requests UPDATED successfully");
			$("#alertsBox").html("");
			$("#alertsBox").html("Vendor Requests UPDATED successfully");
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
		}
		if (e.type == "create" && !e.response.Errors)
		{
			e.sender.read();
			//alert("Vendor Requests Added Successfully");
			$("#alertsBox").html("");
			$("#alertsBox").html("Vendor Requests Added Successfully");
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
		}
		if(e.type == "destroy" && !e.response.Errors)
		{
			alert("Vendor Requests Deleted Successfully");
			var grid = $("#vendorRequestsGrid").data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
		}
      }	
	  function onVendorRequestStart(e)
	  {
		    $('.k-grid-update').hide();
	        $('.k-edit-buttons')
	                .append(
	                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
	        $('.k-grid-cancel').hide();	
	        
		  /*  var grid= $("#vendorRequestsGrid").data("kendoGrid");
		   grid.cancelRow(); */
	  }
      /*********  END FOR ONREQUEST END  ********************/
      
      
      /*****  FOR UPADTE STATUS WINDOW  ******/
      var statusType = "";
     
	  function updateVendorRequestClick(e)
	  {
			  $('#upadteVendorStatusWindow').kendoWindow
			  ({
				  width:320,
				  height:250,
				  title:"Update Status",
				  
				  modal : true
			  }).data("kendoWindow").center().open();
				
		     
		      $("#replyNoteValue").val(reply);
		      $("#selectVendorRequestStatus").val(vendorReqStatus);
		      
			 
	    }
	  
	  
	  
		function updateVendorStatus() 
		      {
			   
			  
			 var replyValue = $("#replyNoteValue").val();
			 var statusType = $("#selectVendorRequestStatus").val();

			  if(replyValue == "")
			  {
				alert("Select Reply Note");  
				return false;
			  }
			  else if(statusType == "" || statusType=="Select" )
			  {
				alert("Select Status"); 
				return false;
			  }
			  
			  $('#updateVendorRequestButton').hide();
			  $('#loader').show();
			  
			  var result=securityCheckForActionsForStatus("./vendorManagement/vendorRequests/updateStatusButton");
		 	  if(result=="success")
			  {  
			  $.ajax
			  ({
				  type : "POST",
				  url :"${statusUpdateForVendorRequests}/"+ SelectedItemMasterRowId+"/"+statusType+"/"+replyValue,
				  dataType : "text",
				  success : function(response)
				  {
					 
					  
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
						updateStatusClose();
						$('#loader').hide();
						$('#updateVendorRequestButton').show();
						$('#vendorRequestsGrid').data('kendoGrid').refresh();
						$('#vendorRequestsGrid').data('kendoGrid').dataSource.read();
					
				   }
				});
			  }
		      }
			
	
		function updateStatusClose() {

			var todcal = $("#upadteVendorStatusWindow");
		
			var presentreading = $("#replyNoteTextArea");
			presentreading.val("");
			
			todcal.kendoWindow({
				width : "auto",
				height : "auto",
				modal : true,
				draggable : true,
				position : {
					top : 100
				},
				title : "Update Status"
			}).data("kendoWindow").center().close();

			todcal.kendoWindow("close");

		}
    /******  END FOR UPDATING STATUS FUNCTION  ******/
      
      
      /***** END FOR UPADTE STATUS WINDOW  *****/
      
	 
	</script>
	
	
	
	
	
	
	
	