<%@include file="/common/taglibs.jsp"%>

<!-- Create Read Update Delete URL's Of Open Ticket -->
<c:url value="/openNewTickets/openTicketCreate" var="openNewTicketCreateUrl" />
<c:url value="/openNewTickets/openTicketsRead" var="openNewTicketReadUrl" />
<c:url value="/openNewTickets/openTicketUpdate" var="openNewTicketUpdateUrl" />
<c:url value="/openNewTickets/openTicketDestroy" var="openNewTicketDestroyUrl" />
<c:url value="/respondTickets/respondTicketsRead" var="respondTicketsReadUrl" />

<!-- Editor URL's Of Open New Ticket -->
<c:url value="/openNewTickets/readTowerNames" var="towerNames" />
<c:url value="/openNewTickets/readPropertyNumbers" var="propertyNum" />
<c:url value="/openNewTickets/filter" var="commonFilterForTicketOpenUrl" />
<c:url value="/openNewTickets/getBlockNamesList" var="getBlockNames" />
<c:url value="/openNewTickets/getPersonListBasedOnPropertyNumbers" var="personNamesAutoBasedOnPersonTypeUrl" />
<c:url value="/users/getDepartment" var="departmentUrl" />

<c:url value="/helpDesk/getPostInternalNote" var="postInternalNote" />
<c:url value="/openTickets/getPersonListForFileter" var="personNamesFilterUrl" />
<c:url value="/openNewTickets/getHelpTopicData" var="getHelpTopicData" />

<!-- Create Read Update Delete URL's Of Post Reply-->
<c:url value="/respondTickets/postReplyCreate" var="postReplyCreateUrl" />
<c:url value="/respondTickets/postReplyRead" var="postReplyReadUrl" />
<c:url value="/respondTickets/postReplyUpdate" var="postReplyUpdateUrl" />
<c:url value="/respondTickets/postReplyDestroy" var="postReplyDestroyUrl" />
<c:url value="/respondTickets/postReplyFilter" var="commonFilterForPostReplyUrl" />

<!-- Create Read Update Delete URL's Of Post Internal Note -->
<c:url value="/respondTickets/postInternalNoteCreate" var="postInternalNoteCreateUrl" />
<c:url value="/respondTickets/postInternalNoteRead" var="postInternalNoteReadUrl" />
<c:url value="/respondTickets/postInternalNoteUpdate" var="postInternalNoteUpdateUrl" />
<c:url value="/respondTickets/postInternalNoteDestroy" var="postInternalNoteDestroyUrl" />
<c:url value="/respondTickets/internalNoteFilter" var="commonFilterForInternalNoteUrl" />

<!-- Create Read Update Delete URL's Of Department Transfer -->
<c:url value="/respondTickets/departmentTransferCreate" var="departmentTransferCreateUrl" />
<c:url value="/respondTickets/departmentTransferRead" var="departmentTransferReadUrl" />
<c:url value="/respondTickets/departmentTransferUpdate" var="departmentTransferUpdateUrl" />
<c:url value="/respondTickets/departmentTransferDestroy" var="departmentTransferDestroyUrl" />
<c:url value="/respondTickets/departmentTransferFilter" var="commonFilterForDepartmentTransferUrl" />

<!-- Create Read Update Delete URL's Of Ticket Assign -->
<c:url value="/respondTickets/ticketAssignCreate" var="ticketAssignCreateUrl" />
<c:url value="/respondTickets/ticketAssignRead" var="ticketAssignReadUrl" />
<c:url value="/respondTickets/ticketAssignUpdate" var="ticketAssignUpdateUrl" />
<c:url value="/respondTickets/ticketAssignDestroy" var="ticketAssignDestroyUrl" />
<c:url value="/respondTickets/readUserLogins" var="userLoginsUrl" />
<c:url value="/respondTickets/ticketAssignFilter" var="commonFilterForTicketAssignUrl" />

<c:url value="/common/getAllChecks" var="allChecksUrl" />
<c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />
<c:url value="/common/relationshipIds/getFilterAutoCompleteValues" var="getFilterAutoCompleteValues" />
<c:url value="/common/relationshipIds/getFilterAutoCompleteValuesForUsers" var="getFilterAutoCompleteValuesForUsers" />

 
<kendo:grid name="grid" resizable="true" pageable="true" selectable="true" change="onChangeRespondTicketList" detailTemplate="newTicketOpenTemplate" edit="respondTicketEvent" sortable="true"  dataBound="statusDataBound" scrollable="true" 
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
		<kendo:grid-editable mode="popup"
			confirmation="Are you sure you want to remove this ticket details?" />
		<kendo:grid-toolbar>
			<%-- <kendo:grid-toolbarItem name="create" text="Add New Ticket" /> --%>
			<kendo:grid-toolbarItem template="<label class='category-label'>&nbsp;&nbsp;From&nbsp;Date&nbsp;:&nbsp;&nbsp;</label><input id='fromMonthpicker' style='width:130px'/>"/>
			<kendo:grid-toolbarItem template="<label class='category-label'>&nbsp;&nbsp;To&nbsp;Date&nbsp;:&nbsp;&nbsp;</label><input id='toMonthpicker' style='width:130px'/>"/>
			<kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=searchByMonth() title='Search' style='width:130px'>Search</a>"/>
			<kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterRespondTicket()><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>"/>
			<kendo:grid-toolbarItem template="&nbsp;&nbsp;&nbsp;<span class='bgBlueColor' style='width:60px; height :20px'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;: Open"/>
			<kendo:grid-toolbarItem template="&nbsp;&nbsp;&nbsp;<span class='bgRedColor' style='width:60px; height :20px'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;: Re-Open"/>
			<kendo:grid-toolbarItem template="&nbsp;&nbsp;&nbsp;<span class='bgGreenColor' style='width:60px; height :20px'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;: Closed"/>
			<kendo:grid-toolbarItem template="&nbsp;&nbsp;&nbsp;<span class='bgYellowColor' style='width:60px; height :20px'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;: Assigned"/>
		</kendo:grid-toolbar>
		<kendo:grid-columns>
			
			<kendo:grid-column title="Ticket Id" field="ticketId" width="110px" hidden="true"/>
			
			<kendo:grid-column title="Created&nbsp;Date" field="ticketCreatedDate" format="{0:dd/MM/yyyy hh:mm tt}" width="140px">
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
			
			<kendo:grid-column title="Ticket&nbsp;Type&nbsp;" field="typeOfTicket" width="100px" filterable="true">
			</kendo:grid-column>
			
			<kendo:grid-column title="Block&nbsp;Name" field="blockName" editor="TowerNames"  width="120px" >
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Block Name",
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
			
			<kendo:grid-column title="Property&nbsp;Number" field="propertyId" editor="PropertyNumbers" hidden="true" filterable="false" width="150px">
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
	    		
	    		<kendo:grid-column title="Property&nbsp;Number" field="property_No" filterable="false" width="130px">
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
	    	
	    	<kendo:grid-column title="Person&nbsp;Name" field="personId" editor="PersonNames" filterable="false" width="0px" hidden="true" >
	    	</kendo:grid-column>
	    				
			<kendo:grid-column title="Department" field="dept_Name" filterable="false" width="100px">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function departmentFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Departement Name",
									dataSource : {
										transport : {
											read : "${getFilterAutoCompleteValues}/OpenNewTicketEntity/departmentObj/dept_Name"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	    		</kendo:grid-column>
	    		
	    		<kendo:grid-column title="Department" field="dept_Id" filterable="false" hidden="true" width="130px" editor="departmentEditor">
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
			
			<kendo:grid-column title="Help&nbsp;Topic&nbsp;*"  field="topicName" filterable="false" width="110px">
			<%-- <kendo:grid-column-values value="${helpTopics}" /> --%>
			<kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function ledgerStatusFilter(element) {
								element
										.kendoAutoComplete({
											placeholder : "Enter Help Topic",
											dataSource : {
												transport : {
													read: "${getFilterAutoCompleteValues}/OpenNewTicketEntity/helpTopicObj/topicName"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>				
			</kendo:grid-column>
			
			<kendo:grid-column title="Help&nbsp;Topic&nbsp;*" field="topicId" filterable="false" hidden="true" width="110px" editor="helpTopicEditor">
			</kendo:grid-column>

			<kendo:grid-column title="Issue&nbsp;Subject"  field="issueSubject" width="120px" editor="ticketIssueSubjectEditor" filterable="true">
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
						
			<kendo:grid-column title="Issue&nbsp;Details" field="issueDetails" width="140px" editor="ticketIssueDetailsEditor" filterable="true">
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
			
			<kendo:grid-column title="Dept&nbsp;Acceptance&nbsp;Status" field="deptAcceptanceStatus" width="155px" >
			<kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function ledgerStatusFilter(element) {
								element
										.kendoAutoComplete({
											placeholder : "Enter Status",
											dataSource : {
												transport : {
													read : "${commonFilterForTicketOpenUrl}/deptAcceptanceStatus"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="Dept&nbsp;Accepted&nbsp;Date" field="deptAcceptedDate" format="{0:dd/MM/yyyy hh:mm tt}" width="150px">
			</kendo:grid-column>
			
			<kendo:grid-column title="Last&nbsp;Response&nbsp;Date" field="lastResonse" format="{0:dd/MM/yyyy hh:mm tt}" width="150px">
			</kendo:grid-column>	
			
			<kendo:grid-column title="Ticket&nbsp;Update&nbsp;Date" field="ticketUpdateDate" format="{0:dd/MM/yyyy hh:mm tt}" width="150px">
			</kendo:grid-column>	
						
			<kendo:grid-column title="Closed&nbsp;Date" field="ticketClosedDate" format="{0:dd/MM/yyyy hh:mm tt}" width="140px">
			</kendo:grid-column>
			
			<kendo:grid-column title="Reopen&nbsp;Date" field="ticketReopenDate" format="{0:dd/MM/yyyy hh:mm tt}" width="140px">
			</kendo:grid-column>
			
			<kendo:grid-column title="Assign&nbsp;Date" field="ticketAssignedDate" format="{0:dd/MM/yyyy hh:mm tt}" width="140px">
			</kendo:grid-column>		
						
			<kendo:grid-column title="Created By" field="createdBy" width="100px">
			 <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function propertyCreatedByFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									placeholder : "Enter Created Name",
									dataSource: {
										transport: {
											read: "${commonFilterForTicketOpenUrl}/createdBy"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column>
                <kendo:grid-column-command>
                   <kendo:grid-column-commandItem name="viewDetails" text="View Details">
                        <kendo:grid-column-commandItem-click>
                            <script>
                            function showDetails(e) {
                                var detailsTemplate = kendo.template($("#template").html());

                                e.preventDefault();

                                var dataItem = this.dataItem($(e.currentTarget).closest("tr"));
                                var wnd = $("#details").data("kendoWindow");

                                wnd.content(detailsTemplate(dataItem));
                                wnd.center().open();
                            }
                            </script>
                        </kendo:grid-column-commandItem-click>
                   </kendo:grid-column-commandItem>
                </kendo:grid-column-command>
            </kendo:grid-column>
			
			<kendo:grid-column title="&nbsp;" width="160px">
				<kendo:grid-column-command >
					 <kendo:grid-column-commandItem name="Accept" click="departementAcceptanceClick" />
					 <kendo:grid-column-commandItem name="Reject" click="departementRejectanceClick" />
				</kendo:grid-column-command>
		    </kendo:grid-column>
		    
		    <kendo:grid-column title="&nbsp;" width="90px">
				<kendo:grid-column-command >
					 <kendo:grid-column-commandItem name="View" click="displayConversation" />
				</kendo:grid-column-command>
		    </kendo:grid-column>		
			
		</kendo:grid-columns>
		
		<kendo:dataSource pageSize="20" requestEnd="onRequestEnd"  requestStart="closeWindow">
			<kendo:dataSource-transport>
			<kendo:dataSource-transport-create url="${openNewTicketCreateUrl}"
					dataType="json" type="GET" contentType="application/json" />
				<kendo:dataSource-transport-read
					url="${respondTicketsReadUrl}" dataType="json"
					type="POST" contentType="application/json" />
				<kendo:dataSource-transport-update url="${openNewTicketUpdateUrl}"
					dataType="json" type="GET" contentType="application/json" />
			    <kendo:dataSource-transport-destroy url="${openNewTicketDestroyUrl}"
					dataType="json" type="GET" contentType="application/json" />
			
			</kendo:dataSource-transport>
			
			<kendo:dataSource-schema parse="parse">
				<kendo:dataSource-schema-model id="ticketId">
					<kendo:dataSource-schema-model-fields>

						<kendo:dataSource-schema-model-field name="ticketId" type="number">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="ticketNumber" type="string">
							<kendo:dataSource-schema-model-field-validation />
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="blockName" type="string">
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
						
						<kendo:dataSource-schema-model-field name="deptAcceptanceStatus" type="string">
						</kendo:dataSource-schema-model-field>						
						
						<kendo:dataSource-schema-model-field name="priorityLevel" type="string">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="topicId" type="number">
						    <kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="issueSubject" type="string">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="issueDetails" type="string">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="ticketStatus" type="string" editable="true">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="lastResonse" type="date">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="ticketCreatedDate" type="date">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="deptAcceptedDate" type="date">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="ticketClosedDate" type="date">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="ticketReopenDate" type="date">
						</kendo:dataSource-schema-model-field>   
						
						<kendo:dataSource-schema-model-field name="ticketAssignedDate" type="date">
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
	
    <kendo:grid-detailTemplate id="newTicketOpenTemplate">
		<kendo:tabStrip name="tabStrip_#=ticketId#">
		<kendo:tabStrip-animation>
			</kendo:tabStrip-animation>
			<kendo:tabStrip-items>

				<kendo:tabStrip-item selected="true" text="Post Reply">
					<kendo:tabStrip-item-content>
						<div class='wethear' style="width: 1150px;">
							<br />
							<kendo:grid name="newTicketPostReplyTemplate_#=ticketId#" remove="postReplyDeleteEvent"
								pageable="true" resizable="true" sortable="true"
								reorderable="true" selectable="true" scrollable="true"
								edit="newTicketPostReplyEvent" editable="true" filterable="false">
								<kendo:grid-pageable pageSize="10"></kendo:grid-pageable>
								<kendo:grid-editable mode="popup" />
								<kendo:grid-toolbar>
									<kendo:grid-toolbarItem name="create" text="Post Reply"/>
								</kendo:grid-toolbar>
								<kendo:grid-columns>
									<kendo:grid-column title="postReplyId" field="postReplyId"
										hidden="true" width="100px">
									</kendo:grid-column>

									<kendo:grid-column title="Response&nbsp;*" field="response" width="200px" filterable="false" editor="ticketResponseEditor">
									<kendo:grid-column-filterable>
											<kendo:grid-column-filterable-ui>
												<script>
													function apCodeFilter(element) {
														element.kendoAutoComplete({
																placeholder : "Enter Response",
																dataSource : {
																transport : {
																read : "${commonFilterForPostReplyUrl}/response"
															}
														 }
													   });
													}
												</script>
											</kendo:grid-column-filterable-ui>
										</kendo:grid-column-filterable>
									</kendo:grid-column>

									<kendo:grid-column title="Response&nbsp;Created&nbsp;Date" field="responseDate" format="{0:dd/MM/yyyy hh:mm tt}" width="130px" filterable="true">
									</kendo:grid-column>

									<kendo:grid-column title="Created By" field="createdBy"	width="90px" filterable="true">
										<kendo:grid-column-filterable>
											<kendo:grid-column-filterable-ui>
												<script>
													function apCodeFilter(element) {
														element.kendoAutoComplete({
																placeholder : "Enter Created By",
																dataSource : {
																transport : {
																read : "${commonFilterForPostReplyUrl}/createdBy"
															}
														 }
													   });
													}
												</script>
											</kendo:grid-column-filterable-ui>
										</kendo:grid-column-filterable>
									</kendo:grid-column>

									<kendo:grid-column title="Last&nbsp;Updated&nbsp;Date"
										field="lastUpdatedDT" format="{0:dd/MM/yyyy hh:mm tt}"
										width="120px" filterable="true">
									</kendo:grid-column>

									<kendo:grid-column title="&nbsp;" width="80px">
										<kendo:grid-column-command>
											<kendo:grid-column-commandItem name="Close Ticket"
												click="ticketStatusCloseClick" />
										</kendo:grid-column-command>
									</kendo:grid-column>

									<kendo:grid-column title="&nbsp;" width="140px">
										<kendo:grid-column-command>
											<kendo:grid-column-commandItem name="edit" />
											<kendo:grid-column-commandItem name="destroy" />
										</kendo:grid-column-command>
									</kendo:grid-column>
								</kendo:grid-columns>

								<kendo:dataSource requestEnd="postReplyOnRequestEnd" requestStart="postReplyOnRequestStart">
									<kendo:dataSource-transport>
										<kendo:dataSource-transport-read url="${postReplyReadUrl}/#=ticketId#" dataType="json" type="POST" contentType="application/json" />
										<kendo:dataSource-transport-create url="${postReplyCreateUrl}/#=ticketId#" dataType="json" type="GET" contentType="application/json" />
										<kendo:dataSource-transport-update url="${postReplyUpdateUrl}" dataType="json" type="GET" contentType="application/json" />
										<kendo:dataSource-transport-destroy	url="${postReplyDestroyUrl}/" dataType="json" type="GET" contentType="application/json" />
									</kendo:dataSource-transport>

									<kendo:dataSource-schema parse="parseSubGrid">
										<kendo:dataSource-schema-model id="postReplyId">
											<kendo:dataSource-schema-model-fields>

												<kendo:dataSource-schema-model-field name="postReplyId"
													type="number">
													<kendo:dataSource-schema-model-field-validation />
												</kendo:dataSource-schema-model-field>

												<kendo:dataSource-schema-model-field name="response"
													type="string">
												</kendo:dataSource-schema-model-field>

												<kendo:dataSource-schema-model-field name="responseDate" type="date">
												</kendo:dataSource-schema-model-field>

												<kendo:dataSource-schema-model-field name="ticketStatus"
													type="string">
												</kendo:dataSource-schema-model-field>

												<kendo:dataSource-schema-model-field name="createdBy"
													type="string">
												</kendo:dataSource-schema-model-field>

												<kendo:dataSource-schema-model-field name="lastUpdatedDT" type="date">
												</kendo:dataSource-schema-model-field>

											</kendo:dataSource-schema-model-fields>
										</kendo:dataSource-schema-model>
									</kendo:dataSource-schema>
								</kendo:dataSource>
							</kendo:grid>
						</div>
					</kendo:tabStrip-item-content>
				</kendo:tabStrip-item>

				<kendo:tabStrip-item selected="false" text="Post Internal Note">
					<kendo:tabStrip-item-content>
						<div class='wethear' style="width: 50%;">
							<br />
							<kendo:grid name="newTicketPostInternalNoteTemplate_#=ticketId#" remove="postInternalNoteDeleteEvent"
								pageable="true" resizable="true" sortable="true"
								reorderable="true" selectable="true" scrollable="true" filterable="false"
								edit="internalNoteEvent" editable="true">
								<kendo:grid-pageable pageSize="10"></kendo:grid-pageable>
								<kendo:grid-editable mode="popup" />
								<kendo:grid-toolbar>
									<kendo:grid-toolbarItem name="create" text="Post Internal Note" />
								</kendo:grid-toolbar>commonFilterForInternalNoteUrl
								<kendo:grid-columns>
									<kendo:grid-column title="internalNoteID" field="internalNoteID" hidden="true" width="100px">
									</kendo:grid-column>

									<kendo:grid-column title="Internal&nbsp;Note&nbsp;Title&nbsp;*" field="internalNoteTitle" width="140px" editor="internalNoteTitleEditor">
									<kendo:grid-column-filterable>
											<kendo:grid-column-filterable-ui>
												<script>
													function apCodeFilter(element) {
														element.kendoAutoComplete({
																placeholder : "Enter Note Subject",
																dataSource : {
																transport : {
																read : "${commonFilterForInternalNoteUrl}/internalNoteTitle"
															}
														 }
													   });
													}
												</script>
											</kendo:grid-column-filterable-ui>
										</kendo:grid-column-filterable>
									</kendo:grid-column>
									<kendo:grid-column title="Internal&nbsp;Note&nbsp;Title&nbsp;*" field="internalNoteID" width="140px" hidden="true" editor="internalNoteTitleEditor"/>
									<kendo:grid-column title="Internal&nbsp;Note&nbsp;Details&nbsp;*" field="internalNoteDetails" width="200px"	editor="internalNoteDetailsEditor">
									<kendo:grid-column-filterable>
											<kendo:grid-column-filterable-ui>
												<script>
													function apCodeFilter(element) {
														element.kendoAutoComplete({
																placeholder : "Enter Note Details",
																dataSource : {
																transport : {
																read : "${commonFilterForInternalNoteUrl}/internalNoteDetails"
															}
														 }
													   });
													}
												</script>
											</kendo:grid-column-filterable-ui>
										</kendo:grid-column-filterable>
									</kendo:grid-column>

									<kendo:grid-column title="Note&nbsp;Created&nbsp;Date" field="internalNoteCreatedDate" format="{0:dd/MM/yyyy hh:mm tt}" width="110px" filterable="true">
									</kendo:grid-column>

									<kendo:grid-column title="Created By" field="createdBy" width="90px" filterable="true">
										<kendo:grid-column-filterable>
											<kendo:grid-column-filterable-ui>
												<script>
													function apCodeFilter(element) {
														element.kendoAutoComplete({
																placeholder : "Enter Created By",
																dataSource : {
																transport : {
																read : "${commonFilterForInternalNoteUrl}/createdBy"
															}
														 }
													   });
													}
												</script>
											</kendo:grid-column-filterable-ui>
										</kendo:grid-column-filterable>
									</kendo:grid-column>

									<kendo:grid-column title="Last&nbsp;Updated&nbsp;Date" field="lastUpdatedDT" format="{0:dd/MM/yyyy hh:mm tt}" width="120px" filterable="true">
									</kendo:grid-column>

									<kendo:grid-column title="&nbsp;" width="80px">
										<kendo:grid-column-command>
											<kendo:grid-column-commandItem name="Close Ticket"	click="ticketStatusUpdateOnPostInternalNote" />
										</kendo:grid-column-command>
									</kendo:grid-column>

									<kendo:grid-column title="&nbsp;" width="140px">
										<kendo:grid-column-command>
											<kendo:grid-column-commandItem name="edit" />
											<kendo:grid-column-commandItem name="destroy" />
										</kendo:grid-column-command>
									</kendo:grid-column>
								</kendo:grid-columns>

								<kendo:dataSource requestEnd="internalNoteOnRequestEnd" requestStart="internalNoteOnRequestStart">
									<kendo:dataSource-transport>
										<kendo:dataSource-transport-read url="${postInternalNoteReadUrl}/#=ticketId#" dataType="json" type="POST" contentType="application/json" />
										<kendo:dataSource-transport-create url="${postInternalNoteCreateUrl}/#=ticketId#" dataType="json" type="GET" contentType="application/json" />
										<kendo:dataSource-transport-update url="${postInternalNoteUpdateUrl}" dataType="json" type="GET" contentType="application/json" />
										<kendo:dataSource-transport-destroy url="${postInternalNoteDestroyUrl}/" dataType="json" type="GET" contentType="application/json" />
									</kendo:dataSource-transport>

									<kendo:dataSource-schema parse="parseInternalNoteGrid">
										<kendo:dataSource-schema-model id="postReplyId">
											<kendo:dataSource-schema-model-fields>

												<kendo:dataSource-schema-model-field name="postReplyId"
													type="number">
													<kendo:dataSource-schema-model-field-validation />
												</kendo:dataSource-schema-model-field>

												<kendo:dataSource-schema-model-field name="response"
													type="string">
												</kendo:dataSource-schema-model-field>

												<kendo:dataSource-schema-model-field name="responseDate" type="date">
												</kendo:dataSource-schema-model-field>

												<kendo:dataSource-schema-model-field name="ticketStatus"
													type="string">
												</kendo:dataSource-schema-model-field>

												<kendo:dataSource-schema-model-field name="createdBy"
													type="string">
												</kendo:dataSource-schema-model-field>

												<kendo:dataSource-schema-model-field name="lastUpdatedDT" type="date">
												</kendo:dataSource-schema-model-field>

											</kendo:dataSource-schema-model-fields>
										</kendo:dataSource-schema-model>
									</kendo:dataSource-schema>
								</kendo:dataSource>
							</kendo:grid>
						</div>
					</kendo:tabStrip-item-content>
				</kendo:tabStrip-item>

				<kendo:tabStrip-item selected="false" text="Department Transfer">
					<kendo:tabStrip-item-content>
						<div class='wethear' style="width: 42%;">
							<br />
							<kendo:grid name="newTicketDeptTransferTemplate_#=ticketId#" pageable="true" resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true"
								edit="deptTransferEvent" editable="true" filterable="false">
								<kendo:grid-pageable pageSize="10"></kendo:grid-pageable>
								<kendo:grid-editable mode="popup" />
								<kendo:grid-toolbar>
									<kendo:grid-toolbarItem name="create" text="Transfer Ticket" />
								</kendo:grid-toolbar>
								<kendo:grid-columns>
									<kendo:grid-column title="deptTransId" field="deptTransId"	hidden="true" width="100px">
									</kendo:grid-column>

								<kendo:grid-column title="Department&nbsp;*" field="dept_Name"
									filterable="false" width="150px">
									<kendo:grid-column-filterable>
										<kendo:grid-column-filterable-ui>
											<script type="text/javascript">
												function departmentFilter(
														element) {
													element
															.kendoAutoComplete({
																placeholder : "Enter Departement Name",
																dataSource : {
																	transport : {
																		read : "${getFilterAutoCompleteValues}/OpenNewTicketEntity/departmentObj/dept_Name"
																	}
																}
															});
												}
											</script>
										</kendo:grid-column-filterable-ui>
									</kendo:grid-column-filterable>
								</kendo:grid-column>

								<kendo:grid-column title="Department&nbsp;*" field="dept_Id" filterable="false" hidden="true" width="130px" editor="departmentEditor">
			                    </kendo:grid-column>
			                    
			                    <kendo:grid-column title="Help&nbsp;Topic&nbsp;*"  field="topicName" filterable="false" width="110px">
								<kendo:grid-column-filterable >
								<kendo:grid-column-filterable-ui >
									<script>
										function ledgerStatusFilter(element) {
												element
												.kendoAutoComplete({
													placeholder : "Enter Help Topic",
													dataSource : {
													transport : {
													read: "${getFilterAutoCompleteValues}/OpenNewTicketEntity/helpTopicObj/topicName"
														}
													}
												});
											}
									</script>
									</kendo:grid-column-filterable-ui>
									</kendo:grid-column-filterable>				
									</kendo:grid-column>
			
									<kendo:grid-column title="Help&nbsp;Topic&nbsp;*" field="topicId" filterable="false" hidden="true" width="110px" editor="helpTopicEditor">
									</kendo:grid-column>

									<kendo:grid-column title="Comments&nbsp;*" field="comments"	width="200px" editor="commentsEditor">
									<kendo:grid-column-filterable>
											<kendo:grid-column-filterable-ui>
												<script>
													function apCodeFilter(element) {
														element.kendoAutoComplete({
																placeholder : "Enter Comments",
																dataSource : {
																transport : {
																read : "${commonFilterForDepartmentTransferUrl}/comments"
															}
														 }
													   });
													}
												</script>
											</kendo:grid-column-filterable-ui>
										</kendo:grid-column-filterable>
									</kendo:grid-column>

									<kendo:grid-column title="Transfer&nbsp;Date" field="transferDate" format="{0:dd/MM/yyyy hh:mm tt}"	width="130px" filterable="true">
									</kendo:grid-column>

									<kendo:grid-column title="Created By" field="createdBy"	width="120px" filterable="true">
										<kendo:grid-column-filterable>
											<kendo:grid-column-filterable-ui>
												<script>
													function apCodeFilter(element) {
														element.kendoAutoComplete({
																placeholder : "Enter Created By",
																dataSource : {
																transport : {
																read : "${commonFilterForDepartmentTransferUrl}/createdBy"
															}
														 }
													   });
													}
												</script>
											</kendo:grid-column-filterable-ui>
										</kendo:grid-column-filterable>
									</kendo:grid-column>

									<kendo:grid-column title="Last&nbsp;Updated&nbsp;Date" field="lastUpdatedDT" format="{0:dd/MM/yyyy hh:mm tt}" width="120px" filterable="true">
									</kendo:grid-column>

									<kendo:grid-column title="&nbsp;" width="70px">
										<kendo:grid-column-command>
											<kendo:grid-column-commandItem name="edit" />
											<%-- <kendo:grid-column-commandItem name="destroy" /> --%>
										</kendo:grid-column-command>
									</kendo:grid-column>
								</kendo:grid-columns>

								<kendo:dataSource requestEnd="departementTransferOnRequestEnd" requestStart="departmentTransferOnRequestStart">
									<kendo:dataSource-transport>
										<kendo:dataSource-transport-read url="${departmentTransferReadUrl}/#=ticketId#"	dataType="json" type="POST" contentType="application/json" />
										<kendo:dataSource-transport-create	url="${departmentTransferCreateUrl}/#=ticketId#" dataType="json" type="GET" contentType="application/json" />
										<kendo:dataSource-transport-update	url="${departmentTransferUpdateUrl}" dataType="json" type="GET" contentType="application/json" />
										<kendo:dataSource-transport-destroy	url="${departmentTransferDestroyUrl}/" dataType="json" type="GET" contentType="application/json" />
									</kendo:dataSource-transport>

									<kendo:dataSource-schema parse="departementTransferGridParse">
										<kendo:dataSource-schema-model id="deptTransId">
											<kendo:dataSource-schema-model-fields>

												<kendo:dataSource-schema-model-field name="deptTransId"	type="number">
												</kendo:dataSource-schema-model-field>

												<kendo:dataSource-schema-model-field name="comments" type="string">
												</kendo:dataSource-schema-model-field>

												<kendo:dataSource-schema-model-field name="dept_Id"	type="number">
												</kendo:dataSource-schema-model-field>

												<kendo:dataSource-schema-model-field name="dept_Name" type="string">
												</kendo:dataSource-schema-model-field>
												
												<kendo:dataSource-schema-model-field name="topicId"	type="number">
												</kendo:dataSource-schema-model-field>
												
												<kendo:dataSource-schema-model-field name="topicName" type="string">
												</kendo:dataSource-schema-model-field>

												<kendo:dataSource-schema-model-field name="transferDate" type="date">
												</kendo:dataSource-schema-model-field>

												<kendo:dataSource-schema-model-field name="createdBy" type="string">
												</kendo:dataSource-schema-model-field>

												<kendo:dataSource-schema-model-field name="lastUpdatedDT" type="date">
												</kendo:dataSource-schema-model-field>

											</kendo:dataSource-schema-model-fields>
										</kendo:dataSource-schema-model>
									</kendo:dataSource-schema>
								</kendo:dataSource>
							</kendo:grid>
						</div>
					</kendo:tabStrip-item-content>
				</kendo:tabStrip-item>

				<kendo:tabStrip-item selected="false" text="Assign ticket">
					<kendo:tabStrip-item-content>
						<div class='wethear' style="width: 42%;">
							<br />
							<kendo:grid name="newTicketAssignTemplate_#=ticketId#"
								pageable="true" resizable="true" sortable="true"
								reorderable="true" selectable="true" scrollable="true"
								edit="ticketAssignEvent" editable="true" remove="assignTicketDeleteEvent" filterable="false">
								<kendo:grid-pageable pageSize="10"></kendo:grid-pageable>
								<kendo:grid-editable mode="popup" />
								<kendo:grid-toolbar>
									<kendo:grid-toolbarItem name="create" text="Assign Ticket" />
								</kendo:grid-toolbar>
								<kendo:grid-columns>
									<kendo:grid-column title="assignId" field="assignId" hidden="true" width="100px">
									</kendo:grid-column>

								<kendo:grid-column title="Assignee&nbsp;*" field="userName"
									width="120px">
									<kendo:grid-column-filterable>
										<kendo:grid-column-filterable-ui>
											<script>
												function propertyNumberFilter(element) {
													element.kendoAutoComplete({
																placeholder : "Enter User Name",
																dataType : 'JSON',
																dataSource : {
																	transport : {
																		read : "${getFilterAutoCompleteValuesForUsers}/TicketAssignEntity/usersObj"
																	}
																}
															});
												}
											</script>
										</kendo:grid-column-filterable-ui>
									</kendo:grid-column-filterable>
								</kendo:grid-column>

								<kendo:grid-column title="Assignee&nbsp;*" field="urId"	width="120px" hidden="true" editor="usersEditor">
								</kendo:grid-column>

									<kendo:grid-column title="Assign&nbsp;Comments&nbsp;*" field="assignComments" width="140px" filterable="false"	editor="assignCommentsEditor">
									<kendo:grid-column-filterable>
											<kendo:grid-column-filterable-ui>
												<script>
													function apCodeFilter(element) {
														element.kendoAutoComplete({
																placeholder : "Enter Code",
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

									<kendo:grid-column title="Assign&nbsp;Date"
										field="assignDate" format="{0:dd/MM/yyyy hh:mm tt}"
										width="130px" filterable="true">
									</kendo:grid-column>

									<kendo:grid-column title="Created By" field="createdBy"
										width="120px" filterable="true">
										<kendo:grid-column-filterable>
											<kendo:grid-column-filterable-ui>
												<script>
													function apCodeFilter(element) {
														element.kendoAutoComplete({
																placeholder : "Enter Code",
																dataSource : {
																transport : {
																read : "${commonFilterForTicketAssignUrl}/createdBy"
															}
														 }
													   });
													}
												</script>
											</kendo:grid-column-filterable-ui>
										</kendo:grid-column-filterable>
									</kendo:grid-column>

									<kendo:grid-column title="Last&nbsp;Updated&nbsp;Date"
										field="lastUpdatedDT" format="{0:dd/MM/yyyy hh:mm tt}"
										width="120px" filterable="true">
									</kendo:grid-column>

									<%-- <kendo:grid-column title="&nbsp;" width="110px">
										<kendo:grid-column-command >
											<kendo:grid-column-commandItem name="Close Ticket" click="ticketStatusUpdateOnPostInternalNote" />
										</kendo:grid-column-command>
								    </kendo:grid-column> --%>

									<kendo:grid-column title="&nbsp;" width="120px">
										<kendo:grid-column-command>
											<kendo:grid-column-commandItem name="edit" />
											<kendo:grid-column-commandItem name="destroy" />
										</kendo:grid-column-command>
									</kendo:grid-column>
								</kendo:grid-columns>

								<kendo:dataSource requestEnd="ticketAssignOnRequestEnd" requestStart="assignTicketOnRequestStart">
									<kendo:dataSource-transport>
										<kendo:dataSource-transport-read
											url="${ticketAssignReadUrl}/#=ticketId#" dataType="json"
											type="POST" contentType="application/json" />
										<kendo:dataSource-transport-create
											url="${ticketAssignCreateUrl}/#=ticketId#" dataType="json"
											type="GET" contentType="application/json" />
										<kendo:dataSource-transport-update
											url="${ticketAssignUpdateUrl}" dataType="json" type="GET"
											contentType="application/json" />
										<kendo:dataSource-transport-destroy
											url="${ticketAssignDestroyUrl}/" dataType="json" type="GET"
											contentType="application/json" />
									</kendo:dataSource-transport>

									<kendo:dataSource-schema parse="ticketAssignGridParse">
										<kendo:dataSource-schema-model id="assignId">
											<kendo:dataSource-schema-model-fields>

												<kendo:dataSource-schema-model-field name="assignId"
													type="number">
													<kendo:dataSource-schema-model-field-validation />
												</kendo:dataSource-schema-model-field>

												<kendo:dataSource-schema-model-field name="assignComments"
													type="string">
												</kendo:dataSource-schema-model-field>

												<kendo:dataSource-schema-model-field name="urId">
												</kendo:dataSource-schema-model-field>
												
												<kendo:dataSource-schema-model-field name="personId"
													type="number">
												</kendo:dataSource-schema-model-field>

												<kendo:dataSource-schema-model-field name="userName"
													type="string">
												</kendo:dataSource-schema-model-field>
												
												<kendo:dataSource-schema-model-field name="urLoginName"
													type="string">
												</kendo:dataSource-schema-model-field>

												<kendo:dataSource-schema-model-field name="assignDate" type="date">
												</kendo:dataSource-schema-model-field>

												<kendo:dataSource-schema-model-field name="createdBy"
													type="string">
												</kendo:dataSource-schema-model-field>

												<kendo:dataSource-schema-model-field name="lastUpdatedDT" type="date">
												</kendo:dataSource-schema-model-field>

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
<div id="viewConverstionDiv">

<table style="width: 1160px;" id="ticketsTable"></table>
<div id="subGridResponseTable"></div>	
<!-- <table id="subGridResponseTable" style="border:1px solid #696969;"></table> -->
		
</div>		

<script>

$( document ).ready(function() {	
	var todayDate = new Date();
	var picker = $("#fromMonthpicker").kendoDatePicker({
		start: "month",
		depth: "month",
		  value:new Date(),
				 format: "dd/MM/yyyy"
			}).data("kendoDatePicker"),
    dateView = picker.dateView;
	dateView.calendar.element.removeData("dateView");        
	picker.max(todayDate);
  	picker.options.depth = dateView.options.depth = 'month';
  	picker.options.start = dateView.options.start = 'month';
   	picker.value(picker.value());
   
   	$('#fromMonthpicker').keyup(function() {
		$('#fromMonthpicker').val("");
	});
   	var todayDate = new Date();
	var picker = $("#toMonthpicker").kendoDatePicker({
		start: "month",
		depth: "month",
		  value:new Date(),
				 format: "dd/MM/yyyy"
			}).data("kendoDatePicker"),
    dateView = picker.dateView;
	dateView.calendar.element.removeData("dateView");        
	picker.max(todayDate);
  	picker.options.depth = dateView.options.depth = 'month';
  	picker.options.start = dateView.options.start = 'month';
   	picker.value(picker.value());
   
   	$('#toMonthpicker').keyup(function() {
		$('#toMonthpicker').val("");
	});
});

function searchByMonth() {
    var fromDate = $('#fromMonthpicker').val();
    var toDate = $('#toMonthpicker').val();
    //alert(fromDate);
    var splitmonth=fromDate.split("/");
	   var splitmonth1=toDate.split("/");
	   if(splitmonth[2]>splitmonth1[2])
		   {
		   alert("To Date should be greater than From Date");
		   return false;
		   }
	   if(splitmonth[2]==splitmonth1[2])
		   {
		   if(splitmonth[1]>splitmonth1[1])
			   {
			   alert("To Date should be greater than From Date");
			   return false;
			   }
		   else if(splitmonth[1]==splitmonth1[1])
			   {
			   if(splitmonth[0]>splitmonth1[0])
				   {
				   alert("To Date should be greater than From Date");
				   return false;
				   }
			   
			   }
		   }
	    $.ajax({
		type : "GET",
		url : "./helpDesk/getOpenTicketSearchByMonth",
		dataType : "json",
		data : {
			fromDate : fromDate,
			toDate : toDate
		},
		success : function(result) {
			parse(result);
			var grid = $("#grid").getKendoGrid();
			var data = new kendo.data.DataSource();
			grid.dataSource.data(result);
			grid.refresh();
		}
	}); 
}

function clearFilterRespondTicket()
{
   $("form.k-filter-menu button[type='reset']").slice().trigger("click");
   var gridStoreIssue = $("#grid").data("kendoGrid");
   gridStoreIssue.dataSource.read();
   gridStoreIssue.refresh();
}

function closeWindow(e){
	
	$('.k-grid-update').hide();
	$('.k-edit-buttons')
			.append(
					'<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
	$('.k-grid-cancel').hide();
	/* var gridPerson = $("#grid").data("kendoGrid");
	if(gridPerson != null)
	{
		gridPerson.cancelRow();
	} */
}

//for parsing timestamp data fields

function parse (response) {	
	var ss="";
    $.each(response, function (idx, elem) {   
    	   if(elem.lastResonse=== null){
    		   elem.lastResonse = "";
    	   }else{
    		   elem.lastResonse = kendo.parseDate(new Date(elem.lastResonse),'dd/MM/yyyy HH:mm');
    	   } 
    
           if(elem.deptAcceptedDate=== null){
               elem.deptAcceptedDate = "";
            }else{
               elem.deptAcceptedDate = kendo.parseDate(new Date(elem.deptAcceptedDate),'dd/MM/yyyy HH:mm');
            } 
    	   
    	   if(elem.ticketCreatedDate=== null){
    		   elem.ticketCreatedDate = ""; 
    	   }else{
    		   elem.ticketCreatedDate = kendo.parseDate(new Date(elem.ticketCreatedDate),'dd/MM/yyyy HH:mm');
    	   }
            
    	   if(elem.ticketClosedDate=== null){
    		   elem.ticketClosedDate = ""; 
    	   }else{
    		   elem.ticketClosedDate = kendo.parseDate(new Date(elem.ticketClosedDate),'dd/MM/yyyy HH:mm');
    	   }
            
    	   if(elem.ticketReopenDate=== null){
    		   elem.ticketReopenDate = ""; 
    	   }else{
    		   elem.ticketReopenDate = kendo.parseDate(new Date(elem.ticketReopenDate),'dd/MM/yyyy HH:mm');
    	   }
    	   
    	   if(elem.ticketAssignedDate=== null){
    		   elem.ticketAssignedDate = "";
    	   }else{
    		   elem.ticketAssignedDate = kendo.parseDate(new Date(elem.ticketAssignedDate),'dd/MM/yyyy HH:mm');
    	   }  
    	   
    	   if(elem.responseDate=== null){
    		   elem.responseDate = ""; 
    	   }else{
    		   elem.responseDate = kendo.parseDate(new Date(elem.responseDate),'dd/MM/yyyy HH:mm');
    	   }
            
    	   if(elem.ticketUpdateDate=== null){
    		   elem.ticketUpdateDate = ""; 
    	   }else{
    		   elem.ticketUpdateDate = kendo.parseDate(new Date(elem.ticketUpdateDate),'dd/MM/yyyy HH:mm');
    	   }
    	   
    	   if (elem.deptAcceptanceStatus=="" || elem.deptAcceptanceStatus==null) {
    	          ss=true;
    	         }
            
       });
       if(ss==true){
	      /* alert("Some tickets are assigned to you.So please check and respond it."); */
	     }
       return response; 
}  

function parseSubGrid (response) {
    $.each(response, function (idx, elem) {   
    	if(elem!=null && elem!=""){   	   
    	   if(elem.responseDate== null){
    		   elem.responseDate = ""; 
    	   }else{
    		   elem.responseDate = kendo.parseDate(new Date(elem.responseDate),'dd/MM/yyyy HH:mm');
    	   }
    	   
    	   if(elem.lastUpdatedDT== null){
    		   elem.lastUpdatedDT = ""; 
    	   }else{
    		   elem.lastUpdatedDT = kendo.parseDate(new Date(elem.lastUpdatedDT),'dd/MM/yyyy HH:mm');
    	   }
    	}
            
       });
       return response; 
}

function parseInternalNoteGrid (response) {
    $.each(response, function (idx, elem) {  
    	   if(elem!=null && elem!=""){
    		   if(elem.internalNoteCreatedDate== null || elem.internalNoteCreatedDate==""){
        		   elem.internalNoteCreatedDate = ""; 
        	   }else{
        		   elem.internalNoteCreatedDate = kendo.parseDate(new Date(elem.internalNoteCreatedDate),'dd/MM/yyyy HH:mm');
        	   }
        	   
        	   if(elem.lastUpdatedDT== null){
        		   elem.lastUpdatedDT = ""; 
        	   }else{
        		   elem.lastUpdatedDT = kendo.parseDate(new Date(elem.lastUpdatedDT),'dd/MM/yyyy HH:mm');
        	   }   
    	   }   	   
            
       });
       return response; 
}

function departementTransferGridParse (response) {
    $.each(response, function (idx, elem) {  
    	   if(elem!=null && elem!=""){
    		   if(elem.transferDate== null || elem.transferDate==""){
        		   elem.transferDate = ""; 
        	   }else{
        		   elem.transferDate = kendo.parseDate(new Date(elem.transferDate),'dd/MM/yyyy HH:mm');
        	   }
        	   
        	   if(elem.lastUpdatedDT== null){
        		   elem.lastUpdatedDT = ""; 
        	   }else{
        		   elem.lastUpdatedDT = kendo.parseDate(new Date(elem.lastUpdatedDT),'dd/MM/yyyy HH:mm');
        	   }   
    	   }   	   
            
       });
       return response; 
}

function ticketAssignGridParse (response) {
    $.each(response, function (idx, elem) {  
    	   if(elem!=null && elem!=""){
    		   if(elem.assignDate== null || elem.assignDate==""){
        		   elem.assignDate = ""; 
        	   }else{
        		   elem.assignDate = kendo.parseDate(new Date(elem.assignDate),'dd/MM/yyyy HH:mm');
        	   }
        	   
        	   if(elem.lastUpdatedDT== null){
        		   elem.lastUpdatedDT = ""; 
        	   }else{
        		   elem.lastUpdatedDT = kendo.parseDate(new Date(elem.lastUpdatedDT),'dd/MM/yyyy HH:mm');
        	   }   
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

// Onclick Functions

var SelectedRowId = "";
var ticketStatus = "";
var SelectedRowIdDeptId = "";
var SelectedRowIdDeptAcceptanceStatus = "";

function onChangeRespondTicketList(arg) {
	 var gview = $("#grid").data("kendoGrid");
	 var selectedItem = gview.dataItem(gview.select());
	 SelectedRowId = selectedItem.ticketId;
	 SelectedRowIdDeptId = selectedItem.dept_Id;
	 ticketStatus = selectedItem.ticketStatus;
	 SelectedRowIdDeptAcceptanceStatus = selectedItem.deptAcceptanceStatus;
	 this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
    
}
var date="";
var responseReply="";
var createdByReply= "";

var internalNoteCreatedDate="";
var internalNoteDetails = "";
var createdByInternalNote = "";
var internalNoteSubject = "";

var deptTransferCreatedBy="";
var deptTransferDate="";
var deptTransferComments="";
var deptTransferPrevdept="";
var deptTransferCurrentObj="";

var createdByAssign="";
var commentsAssign="";
var dateAssign="";
var urLoginNameAssign="";
var createdByAssign="";

var test = ""; 
function displayConversation(){
	$("#subGridResponseTable").empty();
    var todcal=$( "#viewConverstionDiv" );
	  todcal.kendoWindow({
	      width: 950,
	      height: 500,
	      modal: true,
	      draggable: true,
	      position: { top: 100 },
	      title: "Ticket Conversation"
	  }).data("kendoWindow").center().open();

	   todcal.kendoWindow("open");
	
	securityCheckForActions("./CustomerCare/respondTicket/viewConversationButton");
	
	$.ajax
	({			
		type : "POST",
		url : "./openNewTickets/displayConversation/"+SelectedRowId,
		async: false,
		dataType : "JSON",
		success : function(response) 
		{	  
			test = '<table id="test" style="border:1px solid #696969;"></table>';
			 $.each(response, function (idx, elem) {	
			    	$.each(elem, function (idx1, elem1) {
			    		$.each(elem1, function (idx2, elem2) {
				    	   if(idx1 == "TicketPostReplyEntity"){
				    	   if(idx2 == "createdBy"){
					    			createdByReply = elem2;
					    		}	
				    		if(idx2 == "response"){
					    		responseReply = elem2;
					    	}	
				    		
				    		if(idx2 == "responseDate"){
				    			date=elem2;
				    			test=test+'<br><table style="width: 918px;border:1px solid #696969;"><tr><th style="background-color:DarkKhaki ;border:1px solid #696969;"><span style="float: left; padding: 13px; display: inline-block;font-size:100%;">'+dateFun(date)+'</span><span style="float: right; white-space: no-wrap; display: inline-block"><span class="textra" style="vertical-align: middle;"></span><span style="float: right; padding: 13px; float: right; white-space: no-wrap; display: inline-block; vertical-align: middle;font-size:100%;">'+createdByReply+'</span></span></th></tr><tr><th style="height: 40px;border:1px solid #696969;border:1px solid #696969;"><div style="padding: 13px; vertical-align: left; float: left; display: inline-block;">'+responseReply+'</div></th></tr></table>';
				    		}
				    		}
				    		
				    		if(idx1 == "TicketPostInternalNoteEntity"){
				    		if(idx2 == "createdBy"){
					    			createdByInternalNote = elem2;
					    		}	
				    		if(idx2 == "internalNoteDetails"){
				    			internalNoteDetails = elem2;
				    		}
				    		
				    		if(idx2 == "internalNoteTitle"){
				    			internalNoteSubject = elem2;
				    		}
				    		if(idx2 == "internalNoteCreatedDate"){
					    		internalNoteCreatedDate=elem2;	
					    		test=test+'<br><table style="width: 918px;border:1px solid #696969;"><tr><th style="background-color:DarkKhaki ;border:1px solid #696969;"><span style="float: left; padding: 13px; display: inline-block;font-size:100%;">'+dateFun(internalNoteCreatedDate)+'</span><span style="padding: 13px; display: inline-block; padding-left: 1em;font-size:100%;">'+internalNoteSubject+'</span><span style="float: right; white-space: no-wrap; display: inline-block"><span class="textra" style="vertical-align: middle;"></span><span style="float: right; padding: 13px; float: right; white-space: no-wrap; display: inline-block; vertical-align: middle;font-size:100%;">'+createdByInternalNote+'</span></span></th></tr><tr><th style="height: 40px;border:1px solid #696969;"><div style="padding: 13px; vertical-align: left; float: left; display: inline-block;">'+internalNoteDetails+'</div></th></tr></table>';
					    	}	
				    		
				    		}
				    		
				    		if(idx1 == "TicketDeptTransferEntity"){
				    		if(idx2 == "comments"){
					    		deptTransferComments = elem2;
					    	}	
				    		
				    		if(idx2 == "createdBy"){
				    			deptTransferCreatedBy = elem2;
				    		}
				    		
				    		if(idx2 == "prevDepartmentObj"){
				    	    	deptTransferPrevdept = elem2;
				    		}
				    		
				    		if(idx2 == "departmentObj"){
				    			deptTransferCurrentObj = elem2;
				    		}	
				    			
				    		if(idx2 == "transferDate"){
					    		deptTransferDate = elem2;
					    		test=test+'<br><table style="width: 918px;border:1px solid #696969;"><tr><th style="background-color:DarkKhaki ;border:1px solid #696969;"><span style="float: left; padding: 13px; display: inline-block;font-size:100%;">'+dateFun(deptTransferDate)+'</span><span style="padding: 13px; display: inline-block; padding-left: 1em;font-size:100%;"> Ticket transfered from '+deptTransferPrevdept.dept_Name+' to '+deptTransferCurrentObj.dept_Name+'</span><span style="float: right; white-space: no-wrap; display: inline-block"><span class="textra" style="vertical-align: middle;"></span><span style="float: right; padding: 13px; float: right; white-space: no-wrap; display: inline-block; vertical-align: middle;font-size:100%;">'+deptTransferCreatedBy+'</span></span></th></tr><tr><th style="height: 40px;border:1px solid #696969;"><div style="padding: 13px; vertical-align: left; float: left; display: inline-block;">'+deptTransferComments+'</div></th></tr></table>';
					    	}	
				    		
				    		}
				    		
				    		if(idx1 == "TicketAssignEntity"){
				    			   if(idx2 == "createdBy"){
					    			if(elem2==null || elem2==""){
					    				createdByAssign="";
					    			}else {
					    			createdByAssign = elem2;
					    			}
				    			   }
					    			if(idx2 == "assignDate"){
						    			dateAssign=elem2;
						    			}
						    		if(idx2 == "assignComments"){
						    			commentsAssign = elem2;
						    		}	
						    		if(idx2 == "urLoginName"){
						    			urLoginNameAssign = elem2;	
						    			test=test+'<br><table style="width: 918px;border:1px solid #696969;"><tr><th style="background-color:DarkKhaki ;"><span style="float: left; padding: 13px; display: inline-block;font-size:100%;">'+dateFun(dateAssign)+'</span><span style="padding: 13px; display: inline-block; padding-left: 1em;font-size:100%;"> Ticket assigned to '+urLoginNameAssign+'</span><span style="float: right; white-space: no-wrap; display: inline-block"><span class="textra" style="vertical-align: middle;"></span><span style="float: right; padding: 13px; float: right; white-space: no-wrap; display: inline-block; vertical-align: middle;font-size:100%;">'+createdByAssign+'</span></span></th></tr><tr><th style="height: 40px;border:1px solid #696969;"><div style="padding: 13px; vertical-align: left; float: left; display: inline-block;">'+commentsAssign+'</div></th></tr></table>';
						    		}
					    		}
				    		
				    	});			    		
			    	});			    	 
		        });
			 $('#subGridResponseTable').html(test); 
		     } 
	     });

} 

function dateFun (date) {
      return kendo.toString(new Date(date), 'dd/MM/yyyy h:mm tt'); 
  }

function statusDataBound(e) {
	var data = this.dataSource.view(),row;
	var grid = $("#grid").data("kendoGrid");
	
    for (var i = 0; i < data.length; i++) {
    	var currentUid = data[i].uid; 
        row = this.tbody.children("tr[data-uid='" + data[i].uid + "']");
        
        var ticketStatus = data[i].ticketStatus;
        var ticketId = data[i].ticketId;
        var ticketId = data[i].ticketId;
        var deptAcceptanceStatus = data[i].deptAcceptanceStatus;
        
        if(deptAcceptanceStatus != null){
        	var currenRow = grid.table.find("tr[data-uid='" + currentUid+ "']");
			var acceptButton = $(currenRow).find(".k-grid-Accept");
			acceptButton.hide();
			
			var rejectButton = $(currenRow).find(".k-grid-Reject");
			rejectButton.hide();
        }
        
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
 
 // Editors Code Starts
 
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
 
 function helpTopicEditor(container, options) {
		$(
				'<input name="topicName" data-text-field="topicName" id="topicId" data-value-field="topicId" required="true" validationmessage="Help Topic is required" data-bind="value:' + options.field + '"/>')
				.appendTo(container).kendoDropDownList({
					optionLabel : "Select",
					cascadeFrom: "dept_Id",
	                autoBind: false,
					dataSource : {
						transport : {
							read : "${getHelpTopicData}"
						}
					}
					
				});
		 $('<span class="k-invalid-msg" data-for="topicName"></span>').appendTo(container); 
	}

function dropDownChecksEditor(container, options) {
		var res = (container.selector).split("=");
		var attribute = res[1].substring(0,res[1].length-1);
		
		$('<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '"required ="true" name = "'+attribute+'" id = "'+attribute+'Id"/>')
				.appendTo(container).kendoDropDownList({
					optionLabel : {
						text : "Select",
						value : "",
					},
					defaultValue : false,
					sortable : true,
					dataSource : {
						transport : {
							read : "${allChecksUrl}/"+attribute,
						}
					}
				});
		 $('<span class="k-invalid-msg" data-for="'+attribute+'"></span>').appendTo(container);
	}
 
  function ticketIssueSubjectEditor(container, options) 
	{
     $('<textarea data-text-field="issueSubject" required="true" validationmessage="Issue Subject is required" name = "issueSubject" style="width:200px;height:35px"/>')
          .appendTo(container);
     $('<span class="k-invalid-msg" data-for="Enter issue subject"></span>').appendTo(container);
	}
  
  function ticketIssueDetailsEditor(container, options) 
	{
   $('<textarea data-text-field="issueDetails" required="true" validationmessage="Issue Details is required" name = "issueDetails" style="width:200px;height:60px"/>')
        .appendTo(container);
   $('<span class="k-invalid-msg" data-for="Enter issue details"></span>').appendTo(container);
	}
  
  function ticketResponseEditor(container, options) 
	{
 $('<textarea data-text-field="response" name = "response" style="width:150px;height:80px"/>')
      .appendTo(container);
 $('<span class="k-invalid-msg" data-for="Enter response details"></span>').appendTo(container);
	}
  
  function internalNoteTitleEditor(container, options) 
	{
/* $('<textarea data-text-field="internalNoteTitle" name = "internalNoteTitle" style="width:200px;height:35px"/>')
    .appendTo(container);
$('<span class="k-invalid-msg" data-for="Enter internal title"></span>').appendTo(container); */
 $('<input data-text-field="value" id = "typeId" data-value-field="internalNoteTitle" name = "Pet type" data-bind="value:' + options.field + '" required="true"/>')
				.appendTo(container).kendoDropDownList({
					optionLabel : "Select",
					dataSource : {
						transport : {
							read : "${postInternalNote}"
						}
					}
					
				});
		 $('<span class="k-invalid-msg" data-for="internalNoteTitle"></span>').appendTo(container); 
	  /* $(
				'<input name="internalNoteTitle" data-text-field="internalNoteTitle" id="internalNoteID" data-value-field="internalNoteID" validationmessage="Departement name is required" data-bind="value:' + options.field + '" required="required"/>')
				.appendTo(container).kendoDropDownList({
					optionLabel : "Select",
					dataSource : {
						transport : {
							read : "${postInternalNote}"
						}
					}
					
				});
		 $('<span class="k-invalid-msg" data-for="internalNoteTitle"></span>').appendTo(container);  */
	
	} 
  
  function internalNoteDetailsEditor(container, options) 
	{
$('<textarea data-text-field="internalNoteDetails" name = "internalNoteDetails" style="width:200px;height:80px"/>')
    .appendTo(container);
$('<span class="k-invalid-msg" data-for="Enter note details"></span>').appendTo(container);
	}
  
  function commentsEditor(container, options) 
	{
$('<textarea data-text-field="comments" name = "comments" style="width:150px;height:80px"/>')
  .appendTo(container);
$('<span class="k-invalid-msg" data-for="Enter comment details"></span>').appendTo(container);
	}
  
  function assignCommentsEditor(container, options) 
	{
$('<textarea data-text-field="assignComments" name = "assignComments" style="width:150px;height:80px"/>')
.appendTo(container);
$('<span class="k-invalid-msg" data-for="Enter comment details"></span>').appendTo(container);
	}
  
  
  function TowerNames(container, options) 
	 {
			$('<select data-text-field="blockName" name="blockNameEE" required ="true" validationmessage="Block name is required" data-value-field="blockName"  id="blockId" data-bind="value:' + options.field + '"/>')
	             .appendTo(container)
	             .kendoDropDownList
	             ({
	            	 placeholder: "Select BlockName",
	                 autoBind: false,
	                 optionLabel : "Select",
	                 dataSource: {  
	                     transport:{
	                         read: "${towerNames}"
	                     }
	                 }
	             });
			$('<span class="k-invalid-msg" data-for="blockNameEE"></span>').appendTo(container);
	 }
  
  function PropertyNumbers(container, options) 
	{
			$('<select data-text-field="property_No" name="property_NoEE" data-value-field="propertyId" required ="true" validationmessage="Property number is required" id="property_No" data-bind="value:' + options.field + '"/>')
	             .appendTo(container).kendoDropDownList
	             ({
	            	 placeholder: "Select PropertyNo",
	            	 cascadeFrom: "blockId",
	            	 optionLabel : "Select",
	                 autoBind: false,
	                 dataSource: {  
	                     transport:{
	                         read: "${propertyNum}"
	                     }
	                 }
	             });
			$('<span class="k-invalid-msg" data-for="property_NoEE"></span>').appendTo(container);
	}
  
  function PersonNames(container, options) 
  {
	  
		$('<input name="Person name" id="hello1" data-text-field="personName" data-value-field="personId" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoComboBox({
			autoBind : false,
			placeholder : "Select Person",
			cascadeFrom: "property_No",
			headerTemplate : '<div class="dropdown-header">'
				+ '<span class="k-widget k-header">Photo</span>'
				+ '<span class="k-widget k-header">Contact info</span>'
				+ '</div>',
			template : '<table><tr><td rowspan=2><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.personId#'/>" width=40 height=55 alt=\"No Image to Display\" /></span></td>'
				+ '<td align="left"><span class="k-state-default"><b>#: data.personName #</b></span><br>'
				+ '<span class="k-state-default"><i>#: data.persontStyle #</i></span><br>'
				+ '<span class="k-state-default"><i>#: data.personType #</i></span></td></tr></table>',
	         dataSource: {  
	             transport:{
	                 read: "${personNamesAutoBasedOnPersonTypeUrl}"
	             }
	         }			
		});
		$('<span class="k-invalid-msg" data-for="Person name"></span>').appendTo(container);
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
				               alert("User doesn't exist!");
				               $("#urId").data("kendoComboBox").value("");
				    }
				 }						
					
				});
		 $('<span class="k-invalid-msg" data-for="userName"></span>').appendTo(container); 
	}
  
  function dateEditor(container, options) {
	    $('<input name="' + options.field + '"/>')
	            .appendTo(container)
	            .kendoDateTimePicker({
	                format:"dd/MM/yyyy hh:mm tt",
	                timeFormat:"hh:mm tt"         
	                
	            });
	}	
  
  // Editors Code Ends
  
  //onclick functions
  
  function departementAcceptanceClick()
{
	var ticketId="";
	var gridParameter = $("#grid").data("kendoGrid");
	var selectedAddressItem = gridParameter.dataItem(gridParameter.select());
	ticketId = selectedAddressItem.ticketId;
	
	securityCheckForActions("./CustomerCare/respondTicket/deptAcceptStatusButton");
	
	var retVal = confirm("Are you sure you want to accept this ticket?");
	   if( retVal == true ){
			$.ajax
			({			
				type : "POST",
				url : "./openNewTickets/departementTicketAcceptanceStatusAsAccept/"+ticketId,
				dataType:"text",
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
	      
		  return true;
	   }else{
		  return false;
	   }
}
  
  function departementRejectanceClick()
  {
  	var ticketId="";
  	var gridParameter = $("#grid").data("kendoGrid");
  	var selectedAddressItem = gridParameter.dataItem(gridParameter.select());
  	ticketId = selectedAddressItem.ticketId;
  	
  	securityCheckForActions("./CustomerCare/respondTicket/deptRejectStatusButton");
  	
  	var retVal = confirm("Are you sure you want to reject this ticket?");
	if( retVal == true ){  	
		
	               $.ajax({
						type : "POST",
						url : "./openNewTickets/departementTicketAcceptanceStatusAsReject/"+ticketId,
						dataType:"text",
						success : function(response) {
							$("#alertsBox").html("");
							$("#alertsBox").html(response);
							$("#alertsBox").dialog({
								modal : true,
								buttons : {
									"Close" : function() {
										$(this).dialog("close");
									}
								}
							});
							$('#grid').data('kendoGrid').dataSource.read();
						}
					});
			return true;
		} else {
			return false;
		}
	}


	var postReplyId = "";
	function ticketStatusCloseClick() {
		var gridParameter = $("#newTicketPostReplyTemplate_" + SelectedRowId).data("kendoGrid");
		var selectedAddressItem = gridParameter.dataItem(gridParameter.select());
		postReplyId = selectedAddressItem.postReplyId;
		var result=securityCheckForActionsForStatus("./CustomerCare/RespondTicket/PostReply/closedTicketButton");	  
		  if(result=="success"){
		/* securityCheckForActions("./CustomerCare/RespondTicket/PostReply/closedTicketButton"); */
		
		$.ajax({
			type : "POST",
			url : "./respondTickets/ticketStatusUpdateOnPostInternalNote/"+ SelectedRowId,
			dataType:"text",
			success : function(response) {
				$("#alertsBox").html("");
				$("#alertsBox").html(response);
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				$('#grid').data('kendoGrid').dataSource.read();
			}
		});
	 }
	}


	var internalNoteID = "";
	function ticketStatusUpdateOnPostInternalNote() {
		var gridParameter = $("#newTicketPostInternalNoteTemplate_" + SelectedRowId).data("kendoGrid");
		var selectedAddressItem = gridParameter.dataItem(gridParameter.select());
		internalNoteID = selectedAddressItem.internalNoteID;
		var result=securityCheckForActionsForStatus("./CustomerCare/RespondTicket/PostInternalNote/closedTicketButton");	  
		  if(result=="success"){
		/* securityCheckForActions("./CustomerCare/RespondTicket/PostInternalNote/closedTicketButton"); */
		
		$.ajax({
			type : "POST",
			url : "./respondTickets/ticketStatusUpdateOnPostInternalNote/"+ SelectedRowId,
			dataType:"text",
			success : function(response) {
				$("#alertsBox").html("");
				$("#alertsBox").html(response);
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				$('#grid').data('kendoGrid').dataSource.read();
			}
		});
	  }
	}

	var setApCode = "";
	function respondTicketEvent(e) {
		/***************************  to remove the id from pop up  **********************/
		$('div[data-container-for="ticketId"]').remove();
		$('label[for="ticketId"]').closest('.k-edit-label').remove();

		$(".k-edit-field").each(function() {
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
		
		$('label[for="deptAcceptedDate"]').parent().hide();
		$('div[data-container-for="deptAcceptedDate"]').hide();

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
		
		$('label[for="topicName"]').parent().hide();
		$('div[data-container-for="topicName"]').hide();

		/************************* Button Alerts *************************/
		if (e.model.isNew()) {
			setApCode = $('input[name="ticketId"]').val();
			$(".k-window-title").text("Add New Ticket Details");
			$(".k-grid-update").text("Save");
		} else {			
			//securityCheckForActions("./CustomerCare/RespondTicket/editButton");
			$(".k-window-title").text("Edit Ticket Details");
		}
	}

	$("#grid").on("click", ".k-grid-Clear_Filter", function() {
		//custom actions
		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
		var grid = $("#grid").data("kendoGrid");
		grid.dataSource.read();
		grid.refresh();
	});
	
	function newTicketPostReplyEvent(e) {
		if(ticketStatus == "Closed"){
			var grid = $("#newTicketPostReplyTemplate_" + SelectedRowId).data("kendoGrid");
			grid.cancelChanges();
			alert("You can't add response,because ticket already closed");
		}else{
		$('div[data-container-for="postReplyId"]').remove();
		$('label[for="postReplyId"]').closest('.k-edit-label').remove();

		$('div[data-container-for="ticketId"]').remove();
		$('label[for="ticketId"]').closest('.k-edit-label').remove();

		$('div[data-container-for="ticketStatus"]').remove();
		$('label[for="ticketStatus"]').closest('.k-edit-label').remove();

		$('div[data-container-for="responseDate"]').remove();
		$('label[for="responseDate"]').closest('.k-edit-label').remove();

		$('div[data-container-for="createdBy"]').remove();
		$('label[for="createdBy"]').closest('.k-edit-label').remove();

		$('div[data-container-for="lastUpdatedBy"]').remove();
		$('label[for="lastUpdatedBy"]').closest('.k-edit-label').remove();

		$('div[data-container-for="lastUpdatedDT"]').remove();
		$('label[for="lastUpdatedDT"]').closest('.k-edit-label').remove();

		if (e.model.isNew()) {
			securityCheckForActions("./CustomerCare/RespondTicket/PostReply/createButton");
			$(".k-window-title").text("Add Post Reply");
			$(".k-grid-update").text("Save");

		} else {
		    securityCheckForActions("./CustomerCare/RespondTicket/PostReply/editButton");
			setApCode = $('input[name="postReplyId"]').val();
			$(".k-window-title").text("Edit Post Reply Details");
		}
		}
	}

	function internalNoteEvent(e) {
		if(ticketStatus == "Closed"){
			var grid = $("#newTicketPostInternalNoteTemplate_" + SelectedRowId).data("kendoGrid");
			grid.cancelChanges();
			alert("You can't add internal note,because ticket already closed");
		}else{
		$('div[data-container-for="internalNoteID"]').remove();
		$('label[for="internalNoteID"]').closest('.k-edit-label').remove();

		$('div[data-container-for="ticketId"]').remove();
		$('label[for="ticketId"]').closest('.k-edit-label').remove();

		$('div[data-container-for="ticketStatus"]').remove();
		$('label[for="ticketStatus"]').closest('.k-edit-label').remove();

		$('div[data-container-for="internalNoteCreatedDate"]').remove();
		$('label[for="internalNoteCreatedDate"]').closest('.k-edit-label').remove();

		$('div[data-container-for="createdBy"]').remove();
		$('label[for="createdBy"]').closest('.k-edit-label').remove();

		$('div[data-container-for="lastUpdatedBy"]').remove();
		$('label[for="lastUpdatedBy"]').closest('.k-edit-label').remove();

		$('div[data-container-for="lastUpdatedDT"]').remove();
		$('label[for="lastUpdatedDT"]').closest('.k-edit-label').remove();

		if (e.model.isNew()) {
			securityCheckForActions("./CustomerCare/RespondTicket/PostInternalNote/createButton");
			$(".k-window-title").text("Add Post Internal Note");
			$(".k-grid-update").text("Save");

		} else {
			securityCheckForActions("./CustomerCare/RespondTicket/PostInternalNote/editButton");
			setApCode = $('input[name="internalNoteID"]').val();
			$(".k-window-title").text("Edit Post Internal Note Details");
		}
		}
	}

	function deptTransferEvent(e) {
		
		if(ticketStatus == "Closed"){
			var grid = $("#newTicketDeptTransferTemplate_" + SelectedRowId).data("kendoGrid");
			grid.cancelChanges();
			alert("You can't transfer,because ticket already closed");
		} else {
		$('div[data-container-for="deptTransId"]').remove();
		$('label[for="deptTransId"]').closest('.k-edit-label').remove();

		$('div[data-container-for="ticketId"]').remove();
		$('label[for="ticketId"]').closest('.k-edit-label').remove();

		$('div[data-container-for="transferDate"]').remove();
		$('label[for="transferDate"]').closest('.k-edit-label').remove();

		$('label[for="dept_Name"]').parent().hide();
		$('div[data-container-for="dept_Name"]').hide();
		
		$('label[for="topicName"]').parent().remove();
		$('div[data-container-for="topicName"]').remove();

		$('div[data-container-for="createdBy"]').remove();
		$('label[for="createdBy"]').closest('.k-edit-label').remove();

		$('div[data-container-for="lastUpdatedBy"]').remove();
		$('label[for="lastUpdatedBy"]').closest('.k-edit-label').remove();

		$('div[data-container-for="lastUpdatedDT"]').remove();
		$('label[for="lastUpdatedDT"]').closest('.k-edit-label').remove();

		if (e.model.isNew()) {
			securityCheckForActions("./CustomerCare/RespondTicket/DepartmentTransfer/createButton");
			$(".k-window-title").text("Transfer ticket to diff department");
			$(".k-grid-update").text("Save");

		} else {
			securityCheckForActions("./CustomerCare/RespondTicket/DepartmentTransfer/editButton");
			setApCode = $('input[name="deptTransId"]').val();
			$(".k-window-title").text("Edit transfer details");
		}
		}
	}

	function ticketAssignEvent(e) {
		if (SelectedRowIdDeptAcceptanceStatus == null || SelectedRowIdDeptAcceptanceStatus == "Rejected") {
			var grid = $("#newTicketAssignTemplate_" + SelectedRowId).data("kendoGrid");
			grid.cancelChanges();
			alert("You can't assign,because department not accepted this ticket");
		} else if(ticketStatus == "Closed"){
			var grid = $("#newTicketAssignTemplate_" + SelectedRowId).data("kendoGrid");
			grid.cancelChanges();
			alert("You can't assign,because ticket already closed");
		} else {
			$('div[data-container-for="assignId"]').remove();
			$('label[for="assignId"]').closest('.k-edit-label').remove();

			$('div[data-container-for="ticketId"]').remove();
			$('label[for="ticketId"]').closest('.k-edit-label').remove();

			$('div[data-container-for="assignDate"]').remove();
			$('label[for="assignDate"]').closest('.k-edit-label').remove();

			$('label[for="userName"]').parent().hide();
			$('div[data-container-for="userName"]').hide();

			$('div[data-container-for="createdBy"]').remove();
			$('label[for="createdBy"]').closest('.k-edit-label').remove();

			$('div[data-container-for="lastUpdatedBy"]').remove();
			$('label[for="lastUpdatedBy"]').closest('.k-edit-label').remove();

			$('div[data-container-for="lastUpdatedDT"]').remove();
			$('label[for="lastUpdatedDT"]').closest('.k-edit-label').remove();

			if (e.model.isNew()) {
				securityCheckForActions("./CustomerCare/RespondTicket/AssignTicket/createButton");
				$(".k-window-title").text("Assign ticket to user");
				$(".k-grid-update").text("Save");

			} else {
				securityCheckForActions("./CustomerCare/RespondTicket/AssignTicket/editButton");
				setApCode = $('input[name="assignId"]').val();
				$(".k-window-title").text("Edit assign details");
			}
		}
	}
	
    function postReplyDeleteEvent(){
    	securityCheckForActions("./CustomerCare/RespondTicket/PostReply/deleteButton");
    	var conf = confirm("Are you sure want to delete this Post Reply?");
   	    if(!conf){
   	    $('#newTicketPostReplyTemplate_'+SelectedRowId).data().kendoGrid.dataSource.read();
   	    throw new Error('deletion aborted');
   	     } 
	}
	
	function postInternalNoteDeleteEvent(){
		securityCheckForActions("./CustomerCare/RespondTicket/PostInternalNote/deleteButton");
		var conf = confirm("Are you sure want to delete this Internal Note?");
   	    if(!conf){
   	    $('#newTicketPostInternalNoteTemplate_'+SelectedRowId).data().kendoGrid.dataSource.read();
   	    throw new Error('deletion aborted');
   	     }
	}
	
	/* function departmentTransferDeleteEvent(){
		securityCheckForActions("./CustomerCare/RespondTicket/TransferedTickets/deleteButton");
		var conf = confirm("Are you sure want to delete this Transfer Ticket?");
   	    if(!conf){
   	    $('#newTicketDeptTransferTemplate_'+SelectedRowId).data().kendoGrid.dataSource.read();
   	    throw new Error('deletion aborted');
   	     }
	}*/
	
	function assignTicketDeleteEvent(){
		securityCheckForActions("./CustomerCare/RespondTicket/EscalatedTickets/deleteButton");
		var conf = confirm("Are you sure want to delete this Assign Ticket?");
   	    if(!conf){
   	    $('#newTicketAssignTemplate_'+SelectedRowId).data().kendoGrid.dataSource.read();
   	    throw new Error('deletion aborted');
   	     }
	} 

	function onRequestEnd(r) {
		if (typeof r.response != 'undefined') {
			if (r.response.status == "FAIL") {
				errorInfo = "";
				for (var s = 0; s < r.response.result.length; s++) {
					errorInfo += (s + 1) + ". "+ r.response.result[s].defaultMessage + "<br>";
				}

				if (r.type == "create") {
					$("#alertsBox").html("");
					$("#alertsBox").html("Error: Creating the New Ticket Details<br>"+ errorInfo);
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
					$("#alertsBox").html("Error: Updating the New Ticket Details<br>"+ errorInfo);
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
				$("#alertsBox").html("Can't Delete New Ticket Details, Child Record Found");
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
					$("#alertsBox").html("Error: Creating the New Ticket Details<br>"+ errorInfo);
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
					$("#alertsBox").html("Error: Creating the New Ticket Details<br>"+ errorInfo);
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
					$("#alertsBox").html("Error: Updating the New Ticket Details<br>"+ errorInfo);
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

			else if (r.type == "create") {
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
				$("#alertsBox").html("Ticket Details delete successfully");
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

	/************************************* for inner rate slab request *********************************/
	
	function postReplyOnRequestStart(e)
	{
		
		$('.k-grid-update').hide();
		$('.k-edit-buttons')
				.append(
						'<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
		$('.k-grid-cancel').hide();
		/* 
		var gridPostReply= $("#newTicketPostReplyTemplate_" + SelectedRowId).data("kendoGrid");
		gridPostReply.cancelRow(); */
	}

	function postReplyOnRequestEnd(e) {
		if (typeof e.response != 'undefined') {
			if (e.response.status == "FAIL") {
				errorInfo = "";
				for (var k = 0; k < e.response.result.length; k++) {
					errorInfo += (k + 1) + ". "+ e.response.result[k].defaultMessage + "<br>";
				}
				if (e.type == "create") {
					$("#alertsBox").html("");
					$("#alertsBox").html("Error: Creating Post Reply<br>" + errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
				}

				else if (e.type == "update") {
					$("#alertsBox").html("");
					$("#alertsBox").html("Error: Updating the Post Reply<br>" + errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
				}
				return false;
			}

			else if (e.type == "create") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Post Reply Created Successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				var grid = $("#newTicketPostReplyTemplate_" + SelectedRowId).data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			}

			else if (e.type == "update") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Post Reply updated successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				var grid = $("#newTicketPostReplyTemplate_" + SelectedRowId).data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			}

			else if (e.type == "destroy") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Post Reply deleted successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
			}

		}
	}

	function internalNoteOnRequestStart(e)
	{
		
		$('.k-grid-update').hide();
		$('.k-edit-buttons')
				.append(
						'<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
		$('.k-grid-cancel').hide();
		
		
		/* var gridInternalNote= $("#newTicketPostInternalNoteTemplate_" + SelectedRowId).data("kendoGrid");
		gridInternalNote.cancelRow();
 */	}
	
	function internalNoteOnRequestEnd(e) {
		if (typeof e.response != 'undefined') {
			if (e.response.status == "FAIL") {
				errorInfo = "";
				for (var k = 0; k < e.response.result.length; k++) {
					errorInfo += (k + 1) + ". "	+ e.response.result[k].defaultMessage + "<br>";
				}
				if (e.type == "create") {
					$("#alertsBox").html("");
					$("#alertsBox").html("Error: Creating Internal Note<br>" + errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
				}

				else if (e.type == "update") {
					$("#alertsBox").html("");
					$("#alertsBox").html("Error: Updating the Internal Note<br>"+ errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
				}
				return false;
			}

			else if (e.type == "create") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Internal Note Created Successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				var grid = $("#newTicketPostInternalNoteTemplate_" + SelectedRowId).data("kendoGrid");
				grid.dataSource.read();
			}

			else if (e.type == "update") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Internal Note updated successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				var grid = $("#newTicketPostInternalNoteTemplate_" + SelectedRowId).data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			}

			else if (e.type == "destroy") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Internal Note deleted successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
			}

		}
	}
	
	function departmentTransferOnRequestStart(e){
		
		/* $('.k-grid-update').hide();
		$('.k-edit-buttons')
				.append(
						'<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
		$('.k-grid-cancel').hide(); */
		
		 var gridDepartmentTransfer = $("#newTicketDeptTransferTemplate_" + SelectedRowId).data("kendoGrid");
		gridDepartmentTransfer.cancelRow();
	}

	function departementTransferOnRequestEnd(e) {
		if (typeof e.response != 'undefined') {
			if (e.response.status == "FAIL") {
				errorInfo = "";
				for (var k = 0; k < e.response.result.length; k++) {
					errorInfo += (k + 1) + ". "	+ e.response.result[k].defaultMessage + "<br>";
				}

				if (e.type == "create") {
					$("#alertsBox").html("");
					$("#alertsBox").html("Error: Assigning Permission to AccessCard<br>"+ errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});

				}

				else if (e.type == "update") {
					$("#alertsBox").html("");
					$("#alertsBox").html("Error: Updating the Permission to AccessCard<br>"+ errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
				}
				return false;
			}

			else if (e.type == "create") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Department Transfered Successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				//var grid = $("#newTicketDeptTransferTemplate_" + SelectedRowId).data("kendoGrid");
				var grid = $('#grid').data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			}

			else if (e.type == "update") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Transfer details updated successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				var grid = $("#newTicketDeptTransferTemplate_" + SelectedRowId).data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			}

			else if (e.type == "destroy") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Transfer details deleted successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
			}

		}
	}
	
	function assignTicketOnRequestStart(e){
		
	
		/* $('.k-grid-update').hide();
		$('.k-edit-buttons')
				.append(
						'<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
		$('.k-grid-cancel').hide(); */
		
		var gridAssignTicket = $("#newTicketAssignTemplate_" + SelectedRowId).data("kendoGrid");
		gridAssignTicket.cancelRow();
 	}

	function ticketAssignOnRequestEnd(e) {
		if (typeof e.response != 'undefined') {
			if (e.response.status == "FAIL") {
				errorInfo = "";
				for (var k = 0; k < e.response.result.length; k++) {
					errorInfo += (k + 1) + ". "+ e.response.result[k].defaultMessage + "<br>";
				}

				if (e.type == "create") {
					$("#alertsBox").html("");
					$("#alertsBox").html("Error: Assing ticket to user<br>" + errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
				}				
				
				else if (e.type == "update") {
					$("#alertsBox").html("");
					$("#alertsBox").html("Error: Updating the Assing ticket<br>"+ errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
				}

				$('#gridAccessCardPermission_' + SelectedRowId).data().kendoGrid.dataSource.read({
					personId : SelectedAccessCardId
						});
				return false;
			}
			
			else if (e.response.status == "ClosedTicketDestroyError") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Closed status tickets details only can delete,cannot be delete open,assign and re-open tickets");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				var grid = $("#newTicketAssignTemplate_" + SelectedRowId).data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			return false;
		}

			else if (e.type == "create") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Ticket Assigned Successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});

				var grid = $('#grid').data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			}

			else if (e.type == "update") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Assign details updated successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				var grid = $("#newTicketAssignTemplate_" + SelectedRowId).data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			}

			else if (e.type == "destroy") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Assign details deleted successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
			}

		}
	}

	//register custom validation rules
	(function($, kendo) {
		$
				.extend(
						true,
						kendo.ui.validator,
						{

							rules : { // custom rules

								departmentValidation: function (input, params)
						          {
						                 if ((input.attr("name") == "dept_Id") && (input.val() == 0)) 
						                 {
						                 return false;
						                 }
						                 return true;
						             },
						             postReplyRequiredValidation : function(input, params){
						                 if (input.attr("name") == "response")
						                 {
						                  return $.trim(input.val()) !== "";
						                 }
						                 return true;
						                },
						                postReplyLengthValidation: function (input, params)
						                {
						          	  if (input.filter("[name='response']").length && input.val() != "") 
						                {
						               	 return /^[\s\S]{1,500}$/.test(input.val());
						                }        
						                return true;
						                   },
						                   internalNoteTitleRequiredValidation : function(input, params){
								                 if (input.attr("name") == "internalNoteTitle")
								                 {
								                  return $.trim(input.val()) !== "";
								                 }
								                 return true;
								                },
								                internalNoteTitleLengthValidation: function (input, params)
								                {
								          	  if (input.filter("[name='internalNoteTitle']").length && input.val() != "") 
								                {
								               	 return /^[\s\S]{1,500}$/.test(input.val());
								                }        
								                return true;
								                   },
								                internalNoteDetailsRequiredValidation : function(input, params){
									                 if (input.attr("name") == "internalNoteDetails")
									                 {
									                  return $.trim(input.val()) !== "";
									                 }
									                 return true;
									                },
									               internalNoteDetailsLengthValidation: function (input, params)
									                {
									          	  if (input.filter("[name='internalNoteDetails']").length && input.val() != "") 
									                {
									               	 return /^[\s\S]{1,500}$/.test(input.val());
									                }        
									                return true;
									                   },
									                   deptCommentsRequiredValidation : function(input, params){
											                 if (input.attr("name") == "comments")
											                 {
											                  return $.trim(input.val()) !== "";
											                 }
											                 return true;
											                },
											                deptCommentsLengthValidation: function (input, params)
											                {
											          	  if (input.filter("[name='comments']").length && input.val() != "") 
											                {
											               	 return /^[\s\S]{1,500}$/.test(input.val());
											                }        
											                return true;
											                   },
											                   assignCommentsRequiredValidation : function(input, params){
													                 if (input.attr("name") == "assignComments")
													                 {
													                  return $.trim(input.val()) !== "";
													                 }
													                 return true;
													                },
													                assignCommentsLengthValidation: function (input, params)
													                {
													          	  if (input.filter("[name='assignComments']").length && input.val() != "") 
													                {
													               	 return /^[\s\S]{1,500}$/.test(input.val());
													                }        
													                return true;
													                   },

							},
							messages : {
								//custom rules messages
								departmentValidation : "Department name is required",
								postReplyRequiredValidation : "Response Details required",
								postReplyLengthValidation : "Response field allows max 500 characters",
								internalNoteTitleRequiredValidation : "Note title is required",
								internalNoteTitleLengthValidation : "Note title field allows max 500 characters",
								internalNoteDetailsRequiredValidation : "Note details required",
								internalNoteDetailsLengthValidation : "Note details field allows max 500 characters",
								deptCommentsRequiredValidation : "Comment details required",
								deptCommentsLengthValidation : "Comments field allows max 500 characters",
								assignCommentsRequiredValidation : "Comment details required",
								assignCommentsLengthValidation : "Assign comments field allows max 500 characters",
							}
						});

	})(jQuery, kendo);
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

/* table,th,td
{
border:1px solid #696969;
} */

.bgYellowColor{
background: #FFA500
}
</style>
