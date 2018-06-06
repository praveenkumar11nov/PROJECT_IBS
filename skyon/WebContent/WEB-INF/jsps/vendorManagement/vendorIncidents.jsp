<%@include file="/common/taglibs.jsp"%>

	<c:url value="/vendorIncidents/readvendorIncidentsUrl" var="readvendorIncidentsUrl" />
	<c:url value="/vendorIncidents/createvendorIncidentsUrl" var="createvendorIncidentsUrl" />
	<c:url value="/vendorIncidents/updatevendorIncidentsUrl" var="updatevendorIncidentsUrl" />	
	
	<%-- <c:url value="/vendorInvoice/readVendorContracts" var="readVendorContracts" /> --%>
	<c:url value="/vendorIncidents/readVendorContractsForVendorIncidents" var="readVendorContracts" />
	
	<c:url value="/vendorIncidents/updateVendorIncidentStatus" var="updateVendorIncidentStatus" />
	
	<!-- URLS'S FOR FILTER -->	
	<c:url value="/vendorIncidents/getVendorContractsFilter" var="getVendorContractsFilter"/>
	<c:url value="/vendorIncidents/getVendorIncidentDescriptionFilter" var="getVendorIncidentDescriptionFilter"/>
	<c:url value="/vendorIncidents/getVendorIncidentExpectedSlaFilter" var="getVendorIncidentExpectedSlaFilter"/>
	<c:url value="/vendorIncidents/getVendorIncidentSlaReachedFilter" var="getVendorIncidentSlaReachedFilter"/>
	<c:url value="/vendorIncidents/getVendorIncidentSlaCommentsFilter" var="getVendorIncidentSlaCommentsFilter"/>
	<c:url value="/vendorIncidents/getVendorIncidentSlaStatusFilter" var="getVendorIncidentSlaStatusFilter"/>
	<!-- END FOR URL'S FILTER -->

	<kendo:grid name="vendorIncidentsGrid" pageable="true" resizable="true" filterable="true" sortable="true" reorderable="true" selectable="true" change="onChangeVendorIncidents" 
	scrollable="true" groupable="true" edit="addVendorIncidentsEvent">	
		<kendo:grid-editable mode="popup"/>
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Vendor Incidents"/>
			<kendo:grid-toolbarItem text="Clear Filter" name="Clear_Filter"/>
		</kendo:grid-toolbar>
		
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" ></kendo:grid-pageable>
			<kendo:grid-filterable extra="false">
		 		<kendo:grid-filterable-operators>
		  			<kendo:grid-filterable-operators-string eq="Is equal to"/>
		  			<kendo:grid-filterable-operators-date lte="Date Before" gte="Date After"/>
				</kendo:grid-filterable-operators>			
		</kendo:grid-filterable>
		
		<kendo:grid-columns>
		    <kendo:grid-column title="Vendor IncidentsId&nbsp;*" field="vcSlaId" width="80px" hidden="true"/>
		    
		    <kendo:grid-column title="Vendors Contract&nbsp;*" field="contractName" width="80px">
		    <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getVendorContractsFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	 </kendo:grid-column-filterable>
		    </kendo:grid-column>
		    
		    <kendo:grid-column title="Vendor Contract&nbsp;*" field="vcId" width="80px" editor="readVendorContractsDetails" hidden="true"/>
		    <kendo:grid-column title="Vendor Incident Date&nbsp;*" field="incidentDt" width="100px" format="{0:dd/MM/yyyy}"/>
		    
		    
		    <kendo:grid-column title="Vendor Incident Description&nbsp;*" field="incidentDescription" editor="IncidentDescription" width="130px">		    
		      <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getVendorIncidentDescriptionFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	 </kendo:grid-column-filterable>		    
		    </kendo:grid-column>
		    
		    <kendo:grid-column title="Expected SLA&nbsp;*" field="expectedSla" width="80px">
		      <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getVendorIncidentExpectedSlaFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	 </kendo:grid-column-filterable>		    
		    </kendo:grid-column>
		    
		    <kendo:grid-column title="SLA Reached&nbsp;*" field="slaReached" format="{0:n0}" width="80px">
		     <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getVendorIncidentSlaReachedFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	 </kendo:grid-column-filterable>
		    </kendo:grid-column>
		    
		    <kendo:grid-column title="SLA Comments&nbsp;*" field="slaComments" editor="SlaComments" width="80px">
		      <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getVendorIncidentSlaCommentsFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	 </kendo:grid-column-filterable>		    
		    </kendo:grid-column>
		    
		    
		    <kendo:grid-column title="SLA Status&nbsp;*" field="slaStatus" width="80px">	
		       <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getVendorIncidentSlaStatusFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	   </kendo:grid-column-filterable>		    
		    </kendo:grid-column>
		    	    
		    <kendo:grid-column title="&nbsp;" width="135px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit" text="Edit"/>
					<kendo:grid-column-commandItem name="Update Status" click="updateStatusForIncidents"/>
				</kendo:grid-column-command>
			</kendo:grid-column>
			
			
			<%-- <kendo:grid-column title=""
				template="<select id='products' style='width: 250p'><option>Select</option><option>Reached</option><option>Not REached</option><option>Messed</option><option>Forced Reach</option></select>"
				width="100px" /> --%>
		</kendo:grid-columns>
		
		<kendo:dataSource requestEnd="onRequestEnd" requestStart="onRequestStart">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-read url="${readvendorIncidentsUrl}" dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-create url="${createvendorIncidentsUrl}" dataType="json" type="GET" contentType="application/json" />
				<kendo:dataSource-transport-update url="${updatevendorIncidentsUrl}" dataType="json" type="GET" contentType="application/json" />		
			</kendo:dataSource-transport>
			
			<kendo:dataSource-schema>
				<kendo:dataSource-schema-model id="vcSlaId">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="vcSlaId" type="number"/>
						<kendo:dataSource-schema-model-field name="vcId" type="number" defaultValue=""/>
						<kendo:dataSource-schema-model-field name="incidentDt" type="date"/>
						<kendo:dataSource-schema-model-field name="incidentDescription" type="string"/>
						
						<kendo:dataSource-schema-model-field name="expectedSla" type="number" defaultValue="">
							<kendo:dataSource-schema-model-field-validation  min="0"/>
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="slaReached" type="number">
							<kendo:dataSource-schema-model-field-validation  min="1"/>
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="slaComments" type="string"/>
						<kendo:dataSource-schema-model-field name="slaStatus" type="string" defaultValue="Created"/> 					
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
	</kendo:grid>

	<div id="alertsBox" title="Alert"></div>

	<div id="upadteStatusWindow" style="display: none;"><br/>
	    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label for="Status">Status</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	      <select id="selectStatus">
                <option>Select</option>
                <option>Reached</option>
                <option>Not Reached</option>
                <option>Missed</option>
                <option>Force Reached</option>
          </select>
          
          <br/><br/><br/>
           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button name="update" class="k-button" id="updateButton">Update Status</button>
           
	</div>

    <script>
    var statVal = "";
    var statusType = "";
    function onChangeVendorIncidents(e)
    {
    	var gview = $("#vendorIncidentsGrid").data("kendoGrid");
		var selectedItem = gview.dataItem(gview.select());
		statVal = selectedItem.slaStatus;
    }
    
    
    /*** CLEAR FILTER OPTION ***/
	 $("#vendorIncidentsGrid").on("click", ".k-grid-Clear_Filter", function()
	 {		
		   $("form.k-filter-menu button[type='reset']").slice().trigger("click");
		   var grid = $("#vendorIncidentsGrid").data("kendoGrid");
		   grid.dataSource.read();
		   grid.refresh();
	 });
	 /*** END FOR CLEAR FILTER OPTION ***/
    
    /*****  FUNCTION FOR UPDATING STATUS  ******/
    function updateStatusForIncidents(e)
    {
    	var result=securityCheckForActionsForStatus("./vendorManagement/vendorIncidents/activateButton");
 		if(result=="success")
	 	{ 
		    $("#selectStatus").val(statVal);

 			var SelectedItemMasterRowId = "";
 	    	var gview = $("#vendorIncidentsGrid").data("kendoGrid");
 	    	var selectedItem = gview.dataItem(gview.select());
 	    	SelectedItemMasterRowId = selectedItem.vcSlaId;
 			$("#selectStatus").kendoDropDownList({
 				 select: onSelect,
 			});
 			var dropdownlist = $("#selectStatus").data("kendoDropDownList");
 			
 	    	dropdownlist.value(); 
 	    	
 	    	//var statusType = "";
 			/* function onSelect(e) 
 			{
 	             var dataItem = this.dataItem(e.item.index());
 	             statusType = dataItem.value;
 	        } */
 			$('#upadteStatusWindow').kendoWindow
 		    ({
 			      width:320,
 			      height:120,
 			      title:"Update Status",
 			       modal : true
 		    }).data("kendoWindow").center().open();
 					
 			$('#updateButton').click(function()
 			{  
 			
 				if(statusType == "" || statusType=="Select" ){
				alert("Select Status"); 
				return false;
			     }
 			   	$.ajax
 			   	({
 				    type : "POST",
 				    dataType : "text",
 				   	url :"${updateVendorIncidentStatus}/"+ SelectedItemMasterRowId+"/"+statusType,
 				   	datatype:"text",
 				   	success : function(response)
 				   	{
 				   		$("#alertsBox").html("");
 						$("#alertsBox").html(response);
 						$("#upadteStatusWindow").data("kendoWindow").close();
 						$("#alertsBox").dialog({
 							modal : true,
 							buttons : {
 								"Close" : function() {
 									$(this).dialog("close");
 								}
 							}
 						}); 
 						var grid = $("#vendorIncidentsGrid").data("kendoGrid");
 				   		grid.dataSource.read();
 						grid.refresh();
 				   	}
 				});
 			});
	 	} 
 		
		function onSelect(e) 
		{
	       var dataItem = this.dataItem(e.item.index());
	       statusType = dataItem.value;
	    }
    }
    /******  END FOR UPDATING STATUS FUNCTION  ******/
    
    $(".k-i-close").click(function () 
	{
    	$("#upadteStatusWindow").data("kendoWindow").close();
	});
    
	 /************** ON REQUEST END FUNCTION  **********************/
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
					alert("Error: Creating the Vendor Incidents Details\n\n" + errorInfo);
				}
				if (e.type == "update") 
				{
					alert("Error: Updating the Vendor Incidents Details\n\n" + errorInfo);
				}
				var grid = $("#vendorIncidentsGrid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
				return false;
			}
		}
		if (e.type == "update" && !e.response.Errors) 
		{	
			e.sender.read();
			//alert("Vendor Incidents UPDATED successfully");
			$("#alertsBox").html("");
			$("#alertsBox").html("Vendor Incidents UPDATED successfully");
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
			//alert("Vendor Incidents Added Successfully");
			$("#alertsBox").html("");
			$("#alertsBox").html("Vendor Incidents Added Successfully");
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
			//alert("Vendor Incidents Details Deleted Successfully");
			$("#alertsBox").html("");
			$("#alertsBox").html("Vendor Incidents Deleted Successfully");
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
			var grid = $("#vendorIncidentsGrid").data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
		}
	 }	
	 function onRequestStart(e)
	{    
		 $('.k-grid-update').hide();
	        $('.k-edit-buttons')
	                .append(
	                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
	     $('.k-grid-cancel').hide();	
	 }
	 /*********  END FOR ONREQUEST END  ********************/
    
	 $("#vendorIncidentsGrid").on("click", ".k-grid-add", function() 
	 {
	    if($("#vendorIncidentsGrid").data("kendoGrid").dataSource.filter())
	    {
		    $("form.k-filter-menu button[type='reset']").slice().trigger("click");
		 	var grid = $("#vendorIncidentsGrid").data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
		}
	});
    
    /******* EDITOR FUNCTION FOR ADD/CREATE RECORD ********/
	 function addVendorIncidentsEvent(e)
	 {
		 
		 $('a[id="temPID"]').remove();
		 
		$('input[name="expectedSla"]').prop("readonly",true);
		 
		 $('label[for=vendorInvoiceDetails]').parent().remove();
		 $('div[data-container-for="vendorInvoiceDetails"]').remove();
		 
		 $('label[for=vcSlaId]').parent().hide();
		 $('div[data-container-for="vcSlaId"]').hide();
		 
		 $('label[for=slaStatus]').parent().hide();
		 $('div[data-container-for="slaStatus"]').hide();
		 
		 $('label[for=contractName]').parent().hide();
		 $('div[data-container-for="contractName"]').hide();
		 
		 $(".k-grid-cancel").click(function () 
		 {
			  var grid = $("#vendorIncidentsGrid").data("kendoGrid");
			  grid.dataSource.read();
			  grid.refresh();
		 });		 
		 /* $(".k-link").click(function () 
		 {
		     var grid = $("#vendorIncidentsGrid").data("kendoGrid");
			 grid.dataSource.read();
			 grid.refresh();
		     grid.refresh();
		 }); */
		 
		 /* $(".k-grid-update").click(function () {
				
				var incidentDt = $('input[name="incidentDt"]').val();
                var todaysDate = $.datepicker.formatDate('dd/mm/yy', new Date());
               
				if(incidentDt!=null || incidentDt!=""){
					if($.datepicker.parseDate('dd/mm/yy', incidentDt) > $.datepicker.parseDate('dd/mm/yy', todaysDate) || $.datepicker.parseDate('dd/mm/yy', incidentDt) < $.datepicker.parseDate('dd/mm/yy', todaysDate)){

				     alert( "Incident Date Cannot Be Future OR Past date" );
				     return false;
				   }
				}
				}); */
		 
		 
		 if (e.model.isNew()) 
		 {
			 securityCheckForActions("./vendorManagement/vendorIncidents/createButton"); 
			 $(".k-window-title").text("Add Vendor Incidents");
			 $(".k-grid-update").text("Save");
			 
		 }
		 else
		 {
			 securityCheckForActions("./vendorManagement/vendorIncidents/updateButton"); 
			 $(".k-window-title").text("Edit Vendor Incidents");
			 $(".k-grid-update").text("Update");
			 
			 
			 if(statVal!="Created")
		     {
				 $("#alertsBox").html("Alert");
				 $("#alertsBox").html("Incident\t"+statVal+"\tCannot Be Edited");
				 $("#alertsBox").dialog
				 ({
				     modal : true,
					   buttons : {
						 "Close" : function(){
						    $(this).dialog("close");
					      }
					   }
				   });	
				   var grid = $("#vendorIncidentsGrid").data("kendoGrid");
				   grid.cancelRow();
				   grid.refresh();
				   return false;
				return false;
			 }			 
			 $.ajax({
					type : "POST",
					dataType : "text",
					url : "./vendorManagement/vendorIncidents/updateButton",
					success : function(response) {
						if (response == "false") {
							$("#alertsBox").html("");
							$("#alertsBox").html("Access Denied");
							$("#alertsBox").dialog({
								modal: true,
								buttons: {
									"Close": function() {
										$( this ).dialog( "close" );
									}
								}
							}); 
							var grid = $("#vendorInvoicesGrid").data("kendoGrid");
							grid.cancelRow();
							grid.close();
						}else if (response == "timeout") {
							$("#alertsBox").html("");
							$("#alertsBox").html("Session Timeout Please Login Again");
							$("#alertsBox").dialog({
								modal: true,
								buttons: {
									"Close": function() {
										$( this ).dialog( "close" );
									}
								}
							}); 
							var grid = $("#vendorInvoicesGrid").data("kendoGrid");
							grid.cancelRow();
							grid.close();
		
						}
					}
				}); 
		 }
	 }
	 /*******END OF EDITOR FUNCTION FOR ADD/CREATE RECORD ********/
	 
	 /******  TEXT AREA FOR VENDOR INCIDENT  ******/
	 function IncidentDescription(container, options) 
	 {
	      $('<textarea name="Vendor Incident Description" data-text-field="incidentDescription" data-value-field="incidentDescription" data-bind="value:' + options.field + '" style="width: 148px; height: 46px;" required="true"/>')
	      .appendTo(container);
	      $('<span class="k-invalid-msg" data-for="Vendor Incident Description"></span>').appendTo(container); 
	 }
	 /******   END FOR VENDOR INCIDENT *******/
    
	 /******  TEXT AREA FOR SLA COMMENTS DESCRIPTION  ******/
	 function SlaComments(container, options) 
	 {
	      $('<textarea name="Sla Comments" data-text-field="slaComments" data-value-field="slaComments" data-bind="value:' + options.field + '" style="width: 148px; height: 46px;" required="true"/>')
	      .appendTo(container);
	      $('<span class="k-invalid-msg" data-for="Sla Comments"></span>').appendTo(container); 
	 }
	 /******   END FOR SLA COMMENTS *******/
	 
	 var vcId = 0;
	 function readVendorContractsDetails(container,options)
	 {
	 	  $('<input data-text-field="contractName" name="Vendor Contracts" id="test" required="true" data-value-field="vcId" data-bind="value:' + options.field + '"/>')
	      .appendTo(container).kendoComboBox({
		   	 placeholder : "Select Contract",
			    template : '<table><border><tr><td rowspan=2><span class="k-state-default"></span></td>'
				+ '<td padding="5px"><span class="k-state-default"><b>#: data.vendorInvoiceDetails #</b></span><br></td></tr>'
				+'<tr><td rowspan=2 style="padding: 5px;font-size: 10px;"><b>#: data.firstName #</b></td></tr></border></table>', 
			    dataSource: {       
		           transport: {
		               read: "${readVendorContracts}"
		           }
		       },
		       change : function (e) {
		    	   vcId = this.value();
		    	   $.ajax
		    	   ({
						type : "POST",
						url : './vendorIncidents/getSLAForSelectedContract/'+vcId, 
						success: function(response)
						{
						   	//alert("-------"+response);						   
						   	$('input[name="expectedSla"]').val(response);
						}
		    	   });
			       if (this.value() && this.selectedIndex == -1) {                    
			                alert("Contract doesn't exist!");
			                $("#vendorIncidentsGrid").data("kendoComboBox").value("");
			   }
			   }
		   });
			 $('<span class="k-invalid-msg" data-for="Vendor Contracts"></span>').appendTo(container);
		}
	 
	 
	 /****************************   VALIDATOR FUNCTION *************************************/var res = [];
 	(function($, kendo) {
		$
				.extend(
						true,
						kendo.ui.validator,
						{
							rules :
							{ // custom rules
								incidentDtValidator : function (input, params)
							     {
					                     if (input.attr("name") == "incidentDt") {
					                      return $.trim(input.val()) !== "";
					                     }
					                     return true;
					             },
					             expectedSlaValidator : function (input, params)
							     {
					                     if (input.attr("name") == "expectedSla") {
					                      return $.trim(input.val()) !== "";
					                     }
					                     return true;
					             },
					             slaReachedValidator : function (input, params)
							     {
					                     if (input.attr("name") == "slaReached") {
					                      return $.trim(input.val()) !== "";
					                     }
					                     return true;
					             },
					             
					              
					        incidentDtPreFutvalidation: function (input, params) {
				                     if (input.filter("[name = 'incidentDt']").length && input.val() != "") {                          
				                         var incidentDt = input.val();
				                         var todaysDate = $.datepicker.formatDate('dd/mm/yy', new Date());
				                         var flagDate = true;

				                         if ($.datepicker.parseDate('dd/mm/yy', incidentDt) > $.datepicker.parseDate('dd/mm/yy', todaysDate) || $.datepicker.parseDate('dd/mm/yy', incidentDt) < $.datepicker.parseDate('dd/mm/yy', todaysDate)) {
					                      	flagDate = false;
				                         }
				                         return flagDate;
				                     }
				                 return true;
				                 }, 
						        },
							messages : { 
								incidentDtValidator : "Incident Date Cannot Be Empty",
								expectedSlaValidator : "Expected SLA Required",
								slaReachedValidator : "SLA Reached Value Required",
								incidentDtPreFutvalidation : "Incident Date Cannot Be Future OR Past date",

							}
						});
	})(jQuery, kendo);
	 
	 
	 
	 
	/*****************************  END FOR VALIDTAOR FUNCTION ***********************************/
	 
   </script>







<!-- </div> -->