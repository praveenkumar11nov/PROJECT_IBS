<%@include file="/common/taglibs.jsp"%>

<!-- Create Read Update Delete URL's Of Open Ticket -->
<c:url value="/escalatedTickets/escalatedTicketRead" var="escalatedTicketReadUrl" />

<c:url value="/openNewTickets/filter" var="commonFilterForTicketOpenUrl" />
<c:url value="/respondTickets/ticketAssignDestroy" var="ticketAssignDestroyUrl" />
<c:url value="/common/getAllChecks" var="allChecksUrl" />
<c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />
<c:url value="/common/relationshipIds/getFilterAutoCompleteValuesForUsers" var="getFilterAutoCompleteValuesForUsers" />
<c:url value="/common/relationshipIds/getFilterAutoCompleteValues" var="getFilterAutoCompleteValues" />
<c:url value="/respondTickets/ticketAssignFilter" var="commonFilterForTicketAssignUrl" />
<c:url value="/helpTopics/helpTopicFilter" var="commonFilterForHelpTopicOpenUrl" />

 
<kendo:grid name="grid" remove="escalatedTicketsDeleteEvent" resizable="true" pageable="true" selectable="true" change="onChangeOpenTicketList" edit="openNewTicketEvent" dataBound="statusDataBound" sortable="true" scrollable="true" 
		groupable="true">
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" refresh="true" info="true" previousNext="true">
			<kendo:grid-pageable-messages itemsPerPage="Tickets per page" empty="No Tickets to display" refresh="Refresh all the Tickets" 
			display="{0} - {1} of {2} New Tickets" first="Go to the first page of Tickets" last="Go to the last page of Tickets" next="Go to the next page of Tickets"
			previous="Go to the previous page of Tickets"/>
		</kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
			<kendo:grid-filterable-operators>
				<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains"/>
				<kendo:grid-filterable-operators-date gt="Is after" lt="Is before"/>
			</kendo:grid-filterable-operators>
		</kendo:grid-filterable>
		<kendo:grid-editable mode="popup" />
		<kendo:grid-toolbar>
		<kendo:grid-toolbarItem name="escalatedTicketTemplatesDetailsExport" text="Export To Excel" />
	    <kendo:grid-toolbarItem name="escalatedTicketPdfTemplatesDetailsExport" text="Export To PDF" /> 
			<kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterEscalatedTickets()><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>"/>
			<kendo:grid-toolbarItem template="&nbsp;&nbsp;&nbsp;<span class='bgBlueColor' style='width:60px; height :20px'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;: Open"/>
			<kendo:grid-toolbarItem template="&nbsp;&nbsp;&nbsp;<span class='bgRedColor' style='width:60px; height :20px'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;: Re-Open"/>
			<kendo:grid-toolbarItem template="&nbsp;&nbsp;&nbsp;<span class='bgGreenColor' style='width:60px; height :20px'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;: Closed"/>
			<kendo:grid-toolbarItem template="&nbsp;&nbsp;&nbsp;<span class='bgYellowColor' style='width:60px; height :20px'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;: Assigned"/>
		</kendo:grid-toolbar>
		
		<kendo:grid-columns>
			
			<kendo:grid-column title="assignId" field="assignId" width="110px" hidden="true"/>
			
			<kendo:grid-column title="Escalated&nbsp;Date" field="assignDate" format="{0:dd/MM/yyyy hh:mm tt}" width="140px">
			</kendo:grid-column>
			
	    	<kendo:grid-column title="From&nbsp;Department" field="prevDeptId" filterable="false" hidden="true" width="130px" editor="departmentEditor">
			</kendo:grid-column>
			
			<kendo:grid-column title="Escalated&nbsp;To" field="userName" width="120px">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function propertyNumberFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter User Name",
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getFilterAutoCompleteValuesForUsers}/TicketAssignEntity/usersObj"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
			</kendo:grid-column>

			<kendo:grid-column title="Escalated&nbsp;To&nbsp;*" field="urId"	width="120px" hidden="true" editor="usersEditor">
			</kendo:grid-column>
						
			<kendo:grid-column title="Escalated&nbsp;Comments" field="assignComments" filterable="true" width="150px">
			<kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function ledgerStatusFilter(element) {
								element.kendoAutoComplete({
											placeholder : "Select Assign Comments",
											dataSource : {
												transport : {
													read : "${commonFilterForTicketAssignUrl}/assignComments"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="SLA&nbsp;Level&nbsp;(in&nbsp;days)" field="levelOFSLA" filterable="false" width="120px">

			</kendo:grid-column>
			
			<kendo:grid-column title="Ticket&nbsp;Number" field="ticketNumber" filterable="true" width="120px" >
			<kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function ledgerStatusFilter(element) {
								element.kendoAutoComplete({
											placeholder : "Select ticket number",
											dataSource : {
												transport : {
													read : "${commonFilterForTicketOpenUrl}/ticketNumber"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="Property&nbsp;No" field="property_No" filterable="false" width="130px">
				<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function propertyNumberFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Property Number",
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getFilterAutoCompleteValues}/OpenNewTicketEntity/propertyObj/property_No"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
	    		</kendo:grid-column>
				    		
	    	<kendo:grid-column title="Person&nbsp;Name" field="personName"  width="120px" filterable="false">
			<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function personNameFilter(element) 
						   	{
								element.kendoAutoComplete({
									autoBind : false,
									dataTextField : "personName",
									dataValueField : "personName", 
									placeholder : "Enter name",
									headerTemplate : '<div class="dropdown-header">'
										+ '<span class="k-widget k-header">Photo</span>'
										+ '<span class="k-widget k-header">Contact info</span>'
										+ '</div>',
									template : '<table><tr><td rowspan=2><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.personId#'/>" width=40 height=55 alt=\"No Image to Display\" /></span></td>'
										+ '<td align="left"><span class="k-state-default"><b>#: data.personName #</b></span><br>'
										+ '<span class="k-state-default"><i>#: data.personStyle #</i></span><br>'
										+ '<span class="k-state-default"><i>#: data.personType #</i></span></td></tr></table>',
									dataSource : {
										transport : {		
											read :  "${personNamesFilterUrl}"
										}
									} 
								});
						   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	    	</kendo:grid-column>
	    	
	        <kendo:grid-column title="Person Name" field="personId" filterable="false" width="0px" hidden="true" >
	    	</kendo:grid-column>     				
				
			<kendo:grid-column title="Priority" field="priorityLevel" width="100px" filterable="true" editor="dropDownChecksEditor">
			<kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function ledgerStatusFilter(element) {
								element
										.kendoAutoComplete({
											placeholder : "Enter Prority",
											dataSource : {
												transport : {
													read : "${commonFilterForTicketOpenUrl}/priorityLevel"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="Issue&nbsp;Subject"  field="issueSubject" width="120px" filterable="true">
			<kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function ledgerStatusFilter(element) {
								element
										.kendoAutoComplete({
											placeholder : "Enter Issue Subject",
											dataSource : {
												transport : {
													read : "${commonFilterForTicketOpenUrl}/issueSubject"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
						
			<kendo:grid-column title="Issue&nbsp;Details" field="issueDetails" width="140px" filterable="true">
			<kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function ledgerStatusFilter(element) {
								element
										.kendoAutoComplete({
											placeholder : "Enter Issue Details",
											dataSource : {
												transport : {
													read : "${commonFilterForTicketOpenUrl}/issueDetails"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>		
						
			<kendo:grid-column title="Ticket&nbsp;Status" field="ticketStatus" width="105px" >
			<kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function ledgerStatusFilter(element) {
								element
										.kendoAutoComplete({
											placeholder : "Enter Status",
											dataSource : {
												transport : {
													read : "${commonFilterForTicketOpenUrl}/ticketStatus"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="Ticket&nbsp;Created&nbsp;Date" field="ticketCreatedDate" format="{0:dd/MM/yyyy hh:mm tt}" width="140px">
			</kendo:grid-column> 
						
			 <kendo:grid-column title="&nbsp;" width="100px" >
							            	<kendo:grid-column-command>
							            		<%-- <kendo:grid-column-commandItem name="edit"/> --%>
							            		<kendo:grid-column-commandItem name="destroy"/>
							            	</kendo:grid-column-command>
							            </kendo:grid-column>

		</kendo:grid-columns>
		
		<kendo:dataSource pageSize="20" requestEnd="onRequestEnd"  requestStart="closeWindow">
			<kendo:dataSource-transport>
			<kendo:dataSource-transport-create url="${openNewTicketCreateUrl}"
					dataType="json" type="GET" contentType="application/json" />
				<kendo:dataSource-transport-read
					url="${escalatedTicketReadUrl}" dataType="json"
					type="POST" contentType="application/json" />
				<kendo:dataSource-transport-update url="${openNewTicketUpdateUrl}"
					dataType="json" type="GET" contentType="application/json" />
			    <kendo:dataSource-transport-destroy url="${ticketAssignDestroyUrl}/"
					dataType="json" type="GET" contentType="application/json" />
			
			</kendo:dataSource-transport>
			
			<kendo:dataSource-schema parse="parse">
				<kendo:dataSource-schema-model id="deptTransId">
					<kendo:dataSource-schema-model-fields>

						<kendo:dataSource-schema-model-field name="deptTransId" type="number">
						</kendo:dataSource-schema-model-field>deptTransId
						
						<kendo:dataSource-schema-model-field name="ticketNumber" type="string">
							<kendo:dataSource-schema-model-field-validation />
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="blockName" type="string">
							<kendo:dataSource-schema-model-field-validation />
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="levelOFSLA" type="string">
							<kendo:dataSource-schema-model-field-validation />
						</kendo:dataSource-schema-model-field>

						<kendo:dataSource-schema-model-field name="propertyId" type="number">
							<kendo:dataSource-schema-model-field-validation />
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="personId" type="number">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="personName" type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="dept_Id" type="number">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="dept_Name" type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="pevDept_Name" type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="urId" type="number">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="urLoginName" type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="deptId" type="number">
						</kendo:dataSource-schema-model-field>				
						
						<kendo:dataSource-schema-model-field name="priorityLevel" type="string">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="helpTopic" type="string">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="issueSubject" type="string">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="assignComments" type="string">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="issueDetails" type="string">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="ticketStatus" type="string" editable="true">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="lastResonse">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="ticketCreatedDate" type="date">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="ticketClosedDate">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="assignDate" type="date">
						</kendo:dataSource-schema-model-field>   
						
						<kendo:dataSource-schema-model-field name="ticketAssignedDate">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="ticketUpdateDate" type="date">
						</kendo:dataSource-schema-model-field> 
						
						<kendo:dataSource-schema-model-field name="createdBy" type="string">
						</kendo:dataSource-schema-model-field>
						
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
			
		</kendo:dataSource>
	</kendo:grid> 
	
<div id="alertsBox" title="Alert"></div>

<script>
$("#grid").on("click",".k-grid-escalatedTicketTemplatesDetailsExport", function(e) {
	  window.open("./escalatedTicketTemplate/escalatedTicketTemplatesDetailsExport");
});	  

$("#grid").on("click",".k-grid-escalatedTicketPdfTemplatesDetailsExport", function(e) {
	  window.open("./escalatedTicketPdfTemplate/escalatedTicketPdfTemplatesDetailsExport");
});
function clearFilterEscalatedTickets()
{
   $("form.k-filter-menu button[type='reset']").slice().trigger("click");
   var gridStoreIssue = $("#grid").data("kendoGrid");
   gridStoreIssue.dataSource.read();
   gridStoreIssue.refresh();
}

$(document).ready(function() 
{
	  $("#ticketStatusValue").kendoDropDownList({
	       dataTextField: "text",
	       dataValueField: "value",
	       index: 0,
	       select: onSelect,
		});
});

var ticketStatus="";
function onSelect(e) 
{
     var dataItem = this.dataItem(e.item.index());
     ticketStatus = dataItem.value;
     
     $.ajax({
    	   
    	   url : "./openNewTickets/openTicketsReadBasedOnStatus/"+ticketStatus,
    	         type: "GET",
    	         success : function(response)
    	   {
    	      $("#grid").empty();
    	    $('#grid').append(response);
    	   }
     });

}

function closeWindow(e){
	
	$('.k-grid-update').hide();
	$('.k-edit-buttons')
			.append(
					'<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
	$('.k-grid-cancel').hide();
	/* 
	var gridPerson = $("#grid").data("kendoGrid");
	if(gridPerson != null)
	{
		gridPerson.cancelRow();
	}
 */}

//for parsing timestamp 
function parse (response) {   
    $.each(response, function (idx, elem) {   
    	   if(elem.assignDate=== null){
    		   elem.assignDate = "";
    	   }else{
    		   elem.assignDate = kendo.parseDate(new Date(elem.assignDate),'dd/MM/yyyy HH:mm');
    	   } 
    	   
    	   if(elem.ticketCreatedDate=== null){
    		   elem.ticketCreatedDate = "";
    	   }else{
    		   elem.ticketCreatedDate = kendo.parseDate(new Date(elem.ticketCreatedDate),'dd/MM/yyyy HH:mm');
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

function departmentEditor(container, options) {
	$(
			'<input name="dept_Name" data-text-field="dept_Name" id="dept_Id" data-value-field="dept_Id" validationmessage="Departement name is required" data-bind="value:' + options.field + '" required="required"/>')
			.appendTo(container).kendoDropDownList({
				optionLabel : "Select",
				dataSource : {
					transport : {
						read : "${departmentUrl}"
					}
				}
				
			});
	 $('<span class="k-invalid-msg" data-for="dept_Name"></span>').appendTo(container); 
}

function departmentEditor(container, options) {
	$(
			'<input name="deptName" data-text-field="deptName" id="prevDeptId" data-value-field="prevDeptId" validationmessage="Departement name is required" data-bind="value:' + options.field + '" required="required"/>')
			.appendTo(container).kendoDropDownList({
				optionLabel : "Select",
				dataSource : {
					transport : {
						read : "${departmentUrl}"
					}
				}
				
			});
	 $('<span class="k-invalid-msg" data-for="dept_Name"></span>').appendTo(container); 
}

function usersEditor(container, options) {
	$(
			'<input name="userName" data-text-field="userName" id="urId" data-value-field="urId" validationmessage="User name is required" data-bind="value:' + options.field + '" required="required"/>')
			.appendTo(container).kendoComboBox({
				autoBind : false,
				placeholder : "Select User",
				headerTemplate : '<div class="dropdown-header">'
					+ '<span class="k-widget k-header">Photo</span>'
					+ '<span class="k-widget k-header">Contact info</span>'
					+ '</div>',
				template : '<table><tr><td rowspan=2><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.personId#'/>" width=40 height=55 alt=\"No Image to Display\" /></span></td>'
					+ '<td align="left"><span class="k-state-default"><b>#: data.userName #</b></span><br>'
					+ '<span class="k-state-default"><i>#: data.urLoginName #</i></span><br>'
					+ '<span class="k-state-default"><i>#: data.designation #</i></span></td></tr></table>',
				dataSource : {
					transport : {
						read : "${userLoginsUrl}/"+SelectedRowIdDeptId,
					}
				},
				change : function (e) {
			           if (this.value() && this.selectedIndex == -1) {                    
			               alert("Person doesn't exist!");
			               $("#urId").data("kendoComboBox").value("");
			    }
			 }						
				
			});
	 $('<span class="k-invalid-msg" data-for="userName"></span>').appendTo(container); 
}

// Onclick Functions

var SelectedRowId = "";

function onChangeOpenTicketList(arg) {
	 var gview = $("#grid").data("kendoGrid");
	 var selectedItem = gview.dataItem(gview.select());
	 SelectedRowId = selectedItem.ticketId;
	 this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));    
}

function statusDataBound(e) {
	var data = this.dataSource.view(),row;
    for (var i = 0; i < data.length; i++) {
        row = this.tbody.children("tr[data-uid='" + data[i].uid + "']");
        
        var ticketStatus = data[i].ticketStatus;
        var ticketId = data[i].ticketId;
        if (ticketStatus == 'Closed') {
			row.addClass("bgGreenColor");
			$('.k-grid-Activate' + ticketId).show();
		}  if (ticketStatus == 'Open') {
			row.addClass("bgBlueColor");
			$('.k-grid-Activate' + ticketId).show();
		}  if (ticketStatus == 'Re-Open') {
			row.addClass("bgRedColor");
			$('.k-grid-Activate' + ticketId).show();
		} if (ticketStatus == 'Assigned') {
			row.addClass("bgYellowColor");
			$('.k-grid-Activate' + ticketId).show();
		}
    }
}
 
  function ticketReopenStatusClick()
	{
	   $.ajax
		({			
			type : "POST",
			url : "./openNewTickets/ticketStatusUpdateFromInnerGrid/"+SelectedRowId,
			success : function(response) 
			{
				$("#alertsBox").html("");
				$("#alertsBox").html(response);
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
				$('#grid').data('kendoGrid').dataSource.read();
			}
		});
	}
  
	var setApCode = "";
function openNewTicketEvent(e)
{
	/***************************  to remove the id from pop up  **********************/
	$('div[data-container-for="ticketId"]').remove();
	$('label[for="ticketId"]').closest(
	'.k-edit-label').remove();
	
	$(".k-edit-field").each(function () {
		$(this).find("#temPID").parent().remove();  
   	});
	
	$('label[for=property_No]').parent().hide();
	$('div[data-container-for="property_No"]').hide();
	
	$('label[for=ticketNumber]').parent().hide();
	$('div[data-container-for="ticketNumber"]').hide();
	
	$('label[for="ticketStatus"]').parent().hide();  
	$('div[data-container-for="ticketStatus"]').hide();
	
	$('label[for="lastResonse"]').parent().hide();  
	$('div[data-container-for="lastResonse"]').hide();
	
	$('label[for="ticketCreatedDate"]').parent().hide();  
	$('div[data-container-for="ticketCreatedDate"]').hide();
	
	$('label[for="ticketClosedDate"]').parent().hide();  
	$('div[data-container-for="ticketClosedDate"]').hide();
	
	$('label[for="ticketReopenDate"]').parent().hide();  
	$('div[data-container-for="ticketReopenDate"]').hide();
	
	$('label[for="ticketAssignedDate"]').parent().hide();  
	$('div[data-container-for="ticketAssignedDate"]').hide();
	
	$('label[for="ticketUpdateDate"]').parent().hide();  
	$('div[data-container-for="ticketUpdateDate"]').hide();
	
	$('label[for="createdBy"]').parent().hide();  
	$('div[data-container-for="createdBy"]').hide();
	
	$('label[for="personName"]').parent().hide();  
	$('div[data-container-for="personName"]').hide();
	
	$('label[for="deptAcceptanceStatus"]').parent().hide();  
	$('div[data-container-for="deptAcceptanceStatus"]').hide();
	
	$('label[for="dept_Name"]').parent().hide();  
	$('div[data-container-for="dept_Name"]').hide();
	
	$('label[for="urLoginName"]').parent().hide();  
	$('div[data-container-for="urLoginName"]').hide();
	
	/************************* Button Alerts *************************/
	if (e.model.isNew()) 
    {
		setApCode = $('input[name="ticketId"]').val();
		$(".k-window-title").text("Add New Ticket Details");
		$(".k-grid-update").text("Save");		
    }
	else{
		$(".k-window-title").text("Edit Ticket Details");
	}
}

function escalatedTicketsDeleteEvent(){
	securityCheckForActions("./CustomerCare/EscalatedTickets/deleteButton");
	var conf = confirm("Are you sure want to delete this Ticket Details?");
	    if(!conf){
	    $('#grid').data().kendoGrid.dataSource.read();
	    throw new Error('deletion aborted');
	     }
}

$("#grid").on("click", ".k-grid-Clear_Filter", function(){
    //custom actions
	$("form.k-filter-menu button[type='reset']").slice().trigger("click");
	var grid = $("#grid").data("kendoGrid");
	grid.dataSource.read();
	grid.refresh();
});

function onRequestEnd(r) {
	if (typeof r.response != 'undefined') {
		if (r.response.status == "FAIL") {
			errorInfo = "";
			for (var s = 0; s < r.response.result.length; s++) {
				errorInfo += (s + 1) + ". "+ r.response.result[s].defaultMessage + "<br>";
			}

			if (r.type == "create") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Error: Creating the New Ticket Details<br>" + errorInfo);
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});

			}

			if (r.type == "update") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Error: Updating the New Ticket Details<br>" + errorInfo);
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
			}

			$('#grid').data('kendoGrid').refresh();
			$('#grid').data('kendoGrid').dataSource.read();
			return false;
		}
		
		if (r.response.status == "CHILD") {			
				$("#alertsBox").html("");
				$("#alertsBox").html("Can't Delete Transfered Ticket Details, Child Record Found");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				$('#grid').data('kendoGrid').refresh();
				$('#grid').data('kendoGrid').dataSource.read();
			return false;
		}
		
		if (r.response.status == "ClosedTicketDestroyError") {
			$("#alertsBox").html("");
			$("#alertsBox").html("Closed status tickets only can delete,cannot be delete open,assign and re-open tickets");
			$("#alertsBox").dialog({
				modal : true,
				buttons : {
					"Close" : function() {
						$(this).dialog("close");
					}
				}
			});
			$('#grid').data('kendoGrid').refresh();
			$('#grid').data('kendoGrid').dataSource.read();
		return false;
	}

		else if (r.response.status == "INVALID") {
			errorInfo = "";
			errorInfo = r.response.result.invalid;
			if (r.type == "create") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Error: Creating the Transfered Ticket Details<br>" + errorInfo);
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
			}
			$('#grid').data('kendoGrid').refresh();
			$('#grid').data('kendoGrid').dataSource.read();
			return false;
		}

		else if (r.response.status == "EXCEPTION") {
			errorInfo = "";
			errorInfo = r.response.result.exception;
			if (r.type == "create") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Error: Creating the New Ticket Details<br>" + errorInfo);
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
			}

			if (r.type == "update") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Error: Updating the New Ticket Details<br>" + errorInfo);
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
			}

			$('#grid').data('kendoGrid').refresh();
			$('#grid').data('kendoGrid').dataSource.read();
			return false;
		}

		else if (r.type == "create")
		{
			$("#alertsBox").html("");
			$("#alertsBox").html("New Ticket Details created successfully");
			$("#alertsBox").dialog({
				modal : true,
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

		else if (r.type == "update") {
			$("#alertsBox").html("");
			$("#alertsBox").html("Ticket Details updated successfully");
			$("#alertsBox").dialog({
				modal : true,
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
		
		else if (r.type == "destroy") {
			$("#alertsBox").html("");
			$("#alertsBox").html("Transfer ticket details deleted successfully");
			$("#alertsBox").dialog({
				modal : true,
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
</script>
<style>

.bgGreenColor{
background: #428f4c
}

.bgBlueColor{
background: #3883b5
}

.bgRedColor{
background: #FF8484
}

.bgYellowColor{
background: #FFA500
}
</style>
