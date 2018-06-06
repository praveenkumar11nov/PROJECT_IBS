<%@include file="/common/taglibs.jsp"%>

<!-- Create Read Update Delete URL's Of Help Topic -->
<c:url value="/helpTopics/helpTopicCreate" var="helpTopicCreateUrl" />
<c:url value="/helpTopics/helpTopicRead" var="helpTopicReadUrl" />
<c:url value="/helpTopics/helpTopicUpdate" var="helpTopicUpdateUrl" />
<c:url value="/helpTopics/helpTopicDestroy" var="helpTopicDestroyUrl" />

<!-- Help Topic Drop down Editors URL's -->
<c:url value="/helpTopics/readUserLogins" var="userLoginsUrl" />
<c:url value="/helpTopics/readNotifiedUserLogins" var="notifiedUserLoginsUrl" />
<c:url value="/users/getDepartment" var="departmentUrl" />
<c:url value="/helpTopics/helpTopicFilter" var="commonFilterForTicketOpenUrl" />

<!-- Help Topic Filter URL's -->
<c:url value="/common/getAllChecks" var="allChecksUrl" />
<c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />
<c:url value="/common/relationshipIds/getFilterAutoCompleteValuesForUsers" var="getFilterAutoCompleteValuesForUsers" />
<c:url value="/common/relationshipIds/getFilterAutoCompleteValues" var="getFilterAutoCompleteValues" />
 
<kendo:grid name="helpTopicGrid" change="onChangeHelpTopicGrid" detailTemplate="helpTopicTemplate" remove="helpTopicDeleteEvent" resizable="true" pageable="true" selectable="true" edit="helpTopicEvent" sortable="true" scrollable="true" 
		groupable="true">
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" refresh="true" info="true" previousNext="true">
			<kendo:grid-pageable-messages itemsPerPage="Topics per page" empty="No Topics to display" refresh="Refresh all the Topics" 
			display="{0} - {1} of {2} New Topics" first="Go to the first page of Topics" last="Go to the last page of Topics" next="Go to the next page of Topics"
			previous="Go to the previous page of Topics"/>
		</kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
			<kendo:grid-filterable-operators>
				<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains"/>
				<kendo:grid-filterable-operators-date gt="Is after" lt="Is before"/>
			</kendo:grid-filterable-operators>

		</kendo:grid-filterable>
		<kendo:grid-editable mode="popup" />
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add New Help Topic" />
			<kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterHelpTopic()><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>"/>
		</kendo:grid-toolbar>
		
		<kendo:grid-columns>
			
			<kendo:grid-column title="topicId" field="topicId" width="110px" hidden="true"/>
			
			<kendo:grid-column title="Created&nbsp;Date" field="createdDate" format="{0:dd/MM/yyyy hh:mm tt}" width="140px" filterable="true">
			</kendo:grid-column>
			
			<kendo:grid-column title="Topic&nbsp;Name&nbsp;*" field="topicName" width="150px">
			<kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function ledgerStatusFilter(element) {
								element
										.kendoAutoComplete({
											placeholder : "Enter Topic Name",
											dataSource : {
												transport : {
													read : "${commonFilterForTicketOpenUrl}/topicName"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
	    		
	    	<kendo:grid-column title="Topic&nbsp;Description&nbsp;*" field="topicDesc" width="130px" editor="helpTopicDesEditor" filterable="true">
	    	<kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function ledgerStatusFilter(element) {
								element
										.kendoAutoComplete({
											placeholder : "Enter Topic Name",
											dataSource : {
												transport : {
													read : "${commonFilterForTicketOpenUrl}/topicDesc"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="Department&nbsp;*" field="dept_Name" filterable="false" width="150px">
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
			
			<kendo:grid-column title="Department&nbsp;*" field="dept_Id" filterable="false" hidden="true" width="130px" editor="departmentEditor">
			</kendo:grid-column>
			
			<kendo:grid-column title="Normal&nbsp;SLA&nbsp;(in&nbsp;days)&nbsp;*" field="normalSLA" format="{0:n0}" width="150px">
	 		</kendo:grid-column>
			
			<kendo:grid-column title="Level&nbsp;1&nbsp;Escalation&nbsp;SLA&nbsp;*" field="level1SLA" hidden="true" format="{0:n0}" width="157px">
			</kendo:grid-column>
			
			<kendo:grid-column title="Level&nbsp;1&nbsp;Escalation&nbsp;User&nbsp;*" field="userNameLevel1" hidden="true" width="163px" filterable="true">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function propertyNumberFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter User Name",
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getFilterAutoCompleteValuesForUsers}/HelpTopicEntity/usersObj1"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
			</kendo:grid-column>

			<kendo:grid-column title="Level&nbsp;1&nbsp;Escalation&nbsp;User&nbsp;*" field="level1User"	width="120px" hidden="true" editor="usersLevel1Editor">
			</kendo:grid-column>
			
			<kendo:grid-column title="Level&nbsp;1&nbsp;Notified&nbsp;Users" field="level1NotifiedUsers" editor="level1NotifiedUsersEditor" width="120px" hidden="true">
			</kendo:grid-column>
		
			<kendo:grid-column title="Level&nbsp;1&nbsp;Notified&nbsp;Users" field="level1NotifiedUserNames" hidden="true" width="120px">
			</kendo:grid-column>
			
			<kendo:grid-column title="Level&nbsp;2&nbsp;Escalation&nbsp;SLA&nbsp;*" field="level2SLA" hidden="true" format="{0:n0}" width="157px">
			</kendo:grid-column>
			
			<kendo:grid-column title="Level&nbsp;2&nbsp;Escalation&nbsp;User&nbsp;*" field="userNameLevel2" hidden="true" width="163px" filterable="true">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function propertyNumberFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter User Name",
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getFilterAutoCompleteValuesForUsers}/HelpTopicEntity/usersObj2"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
			</kendo:grid-column>

			<kendo:grid-column title="Level&nbsp;2&nbsp;Escalation&nbsp;User&nbsp;*" field="level2User"	width="120px" hidden="true" editor="usersLevel2Editor">
			</kendo:grid-column>
			
			<kendo:grid-column title="Level&nbsp;2&nbsp;Notified&nbsp;Users" field="level2NotifiedUsers" editor="level2NotifiedUsersEditor" width="120px" hidden="true">
			</kendo:grid-column>
		
			<kendo:grid-column title="Level&nbsp;2&nbsp;Notified&nbsp;Users" field="level2NotifiedUserNames" hidden="true" width="120px">
			</kendo:grid-column>
			
			<kendo:grid-column title="Level&nbsp;3&nbsp;Escalation&nbsp;SLA&nbsp;*" field="level3SLA" hidden="true" format="{0:n0}" width="157px">
			</kendo:grid-column>
			
			<kendo:grid-column title="Level&nbsp;3&nbsp;Escalation&nbsp;User&nbsp;*" field="userNameLevel3" hidden="true" width="163px">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function propertyNumberFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter User Name",
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getFilterAutoCompleteValuesForUsers}/HelpTopicEntity/usersObj3"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
			</kendo:grid-column>

			<kendo:grid-column title="Level&nbsp;3&nbsp;Escalation&nbsp;User&nbsp;*" field="level3User"	width="120px" hidden="true" editor="usersLevel3Editor">
			</kendo:grid-column>
			
			<kendo:grid-column title="Level&nbsp;3&nbsp;Notified&nbsp;Users" field="level3NotifiedUsers" editor="level3NotifiedUsersEditor" width="120px" hidden="true">
			</kendo:grid-column>
		
			<kendo:grid-column title="Level&nbsp;3&nbsp;Notified&nbsp;Users" field="level3NotifiedUserNames" hidden="true" width="120px">
			</kendo:grid-column>
	    	
	        <kendo:grid-column title="Topic&nbsp;Status" field="status" width="90px">
	        <kendo:grid-column-filterable >
					                <kendo:grid-column-filterable-ui >
						            <script>
							              function ledgerStatusFilter(element) {
								                element
										        .kendoDropDownList({
											     optionLabel : "Select status",
											     dataSource : {
												 transport : {
													read : "${filterDropDownUrl}/servicePointsStatus"
												             }
											               }
										               });
							                         }
						             </script>
					                 </kendo:grid-column-filterable-ui>
				                     </kendo:grid-column-filterable>
	    	</kendo:grid-column>     				
			
			<kendo:grid-column title="Topic&nbsp;Created&nbsp;By"  field="createdBy" width="120px">
			<kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function ledgerStatusFilter(element) {
								element
										.kendoAutoComplete({
											placeholder : "Enter Created By",
											dataSource : {
												transport : {
													read : "${commonFilterForTicketOpenUrl}/createdBy"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>	
			
			 <kendo:grid-column title="&nbsp;" width="160px" >
							            	<kendo:grid-column-command>
							            		<kendo:grid-column-commandItem name="edit"/>
							            		<kendo:grid-column-commandItem name="destroy"/>
							            	</kendo:grid-column-command>
							            </kendo:grid-column>
			
			<kendo:grid-column title=""
				template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Active#=data.topicId#'>#= data.status == 'Active' ? 'Inactivate' : 'Activate' #</a>"
				width="80px" />
		</kendo:grid-columns>
		
		<kendo:dataSource pageSize="20" requestEnd="onRequestEnd" requestStart="helpTopicOnRequestStart">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-create url="${helpTopicCreateUrl}" dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-read url="${helpTopicReadUrl}" dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-update url="${helpTopicUpdateUrl}" dataType="json" type="POST" contentType="application/json" />
			    <kendo:dataSource-transport-destroy url="${helpTopicDestroyUrl}/" dataType="json" type="POST" contentType="application/json" />
			    <kendo:dataSource-transport-parameterMap>
					<script type="text/javascript">
						function parameterMap(options,type) {
							return JSON.stringify(options);
						}
					</script>
				</kendo:dataSource-transport-parameterMap>
			</kendo:dataSource-transport>
			
			<kendo:dataSource-schema parse="parse">
				<kendo:dataSource-schema-model id="topicId">
					<kendo:dataSource-schema-model-fields>

						<kendo:dataSource-schema-model-field name="topicId" type="number">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="topicName" type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="topicDesc" type="string">
							<kendo:dataSource-schema-model-field-validation required="true"/>
						</kendo:dataSource-schema-model-field>

						<kendo:dataSource-schema-model-field name="createdDate" type="date">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="normalSLA" type="number" defaultValue="">
						      <kendo:dataSource-schema-model-field-validation min="0"/>
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="dept_Id">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="dept_Name" type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="level1User">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="userNameLevel1" type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="level1SLA" type="number" defaultValue="">
						      <kendo:dataSource-schema-model-field-validation min="1"/>
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="level2User">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="userNameLevel2" type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="level2SLA" type="number" defaultValue="">
						<kendo:dataSource-schema-model-field-validation min="1"/>
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="level3User">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="userNameLevel3" type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="level3SLA" type="number" defaultValue="">
						<kendo:dataSource-schema-model-field-validation min="1"/>
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="status" type="string" editable="true">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="level1NotifiedUsers">
							<kendo:dataSource-schema-model-field-validation max="500"/>
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="level2NotifiedUsers">
							<kendo:dataSource-schema-model-field-validation max="500"/>
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="level3NotifiedUsers">
							<kendo:dataSource-schema-model-field-validation max="500"/>
						</kendo:dataSource-schema-model-field>
						
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
			
		</kendo:dataSource>
	</kendo:grid> 
	<kendo:grid-detailTemplate id="helpTopicTemplate">
		<kendo:tabStrip name="tabStrip_#=topicId#">
			<kendo:tabStrip-animation>
			</kendo:tabStrip-animation>
			<kendo:tabStrip-items>
				<kendo:tabStrip-item text="Escalation Details" selected="true">
                <kendo:tabStrip-item-content>
                     <div class='payment-details' style='width: 1000px;'>
							<table>
								<tr><td>
										<dl>
											<table>											 
												<tr >	
													<td><b>Level&nbsp;1&nbsp;Escalation&nbsp;SLA&nbsp;</b></td>
													<td>#= level1SLA# days</td>
												</tr >
												<tr >
													<td><b>Level&nbsp;1&nbsp;Escalation&nbsp;User&nbsp;</b></td>
													<td>#= userNameLevel1#</td>
												</tr >
											   <tr >
											        <td><b>Level&nbsp;1&nbsp;Notified&nbsp;Users&nbsp;</b></td>
													<td>#= level1NotifiedUserNames#</td>
												</tr >
											</table>	
																			
										</dl> 
									</td>
									
									<td>
										<dl>
											<table>											 
												<tr >	
													<td><b>Level&nbsp;2&nbsp;Escalation&nbsp;SLA&nbsp;</b></td>
													<td>#= level2SLA# days</td>
												</tr >
												<tr >
													<td><b>Level&nbsp;2&nbsp;Escalation&nbsp;User&nbsp;</b></td>
													<td>#= userNameLevel2#</td>
												</tr >
											   <tr >
											        <td><b>Level&nbsp;2&nbsp;Notified&nbsp;Users&nbsp;</b></td>
													<td>#= level2NotifiedUserNames#</td>
												</tr >
											</table>	
																			
										</dl> 
									</td>
									
									<td>
										<dl>
											<table>											 
												<tr >	
													<td><b>Level&nbsp;3&nbsp;Escalation&nbsp;SLA&nbsp;</b></td>
													<td>#= level3SLA# days</td>
												</tr >
												<tr >
													<td><b>Level&nbsp;3&nbsp;Escalation&nbsp;User&nbsp;</b></td>
													<td>#= userNameLevel3#</td>
												</tr >
											   <tr >
											        <td><b>Level&nbsp;3&nbsp;Notified&nbsp;Users&nbsp;</b></td>
													<td>#= level3NotifiedUserNames#</td>
												</tr >
											</table>	
																			
										</dl> 
									</td>
								</tr>
							</table>
				</div>
                </kendo:tabStrip-item-content>
            </kendo:tabStrip-item>
			</kendo:tabStrip-items>
			</kendo:tabStrip>
	</kendo:grid-detailTemplate>		
	
<div id="alertsBox" title="Alert"></div>

<script>

function onChangeHelpTopicGrid(arg) {
	 var gview = $("#helpTopicGrid").data("kendoGrid");
	 var selectedItem = gview.dataItem(gview.select());
	 SelectedRowId = selectedItem.paymentCollectionId;
	 this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
   
}

function clearFilterHelpTopic()
{
   $("form.k-filter-menu button[type='reset']").slice().trigger("click");
   var gridStoreIssue = $("#helpTopicGrid").data("kendoGrid");
   gridStoreIssue.dataSource.read();
   gridStoreIssue.refresh();
}

$("#helpTopicGrid").on("click", ".k-grid-add", function() 
		{
			if($("#helpTopicGrid").data("kendoGrid").dataSource.filter())
			{
	    		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
	    	    var grid = $("#helpTopicGrid").data("kendoGrid");
	    		grid.dataSource.read();
	    		grid.refresh();
	        }
		});

function closeWindow(e){
	var gridPerson = $("#helpTopicGrid").data("kendoGrid");
	if(gridPerson != null)
	{
		gridPerson.cancelRow();
	}
}

var helpTopicNameArray = [];
//for parsing timestamp 
function parse (response) {   
    $.each(response, function (idx, elem) {   
    	   if(elem.createdDate=== null){
    		   elem.createdDate = "";
    	   }else{
    		   elem.createdDate = kendo.parseDate(new Date(elem.createdDate),'dd/MM/yyyy HH:mm');
    	   }
       });
    
    var data = response; 
    helpTopicNameArray = [];
	 for (var idx = 0, len = data.length; idx < len; idx ++) {
		var res1 = (data[idx].topicName);
		helpTopicNameArray.push(res1);
	 } 
	 
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

function helpTopicDesEditor(container, options) 
{
$('<textarea data-text-field="topicDesc" name = "topicDesc" style="width:150px;height:60px"/>')
    .appendTo(container);
$('<span class="k-invalid-msg" data-for="Enter help topic desc"></span>').appendTo(container);
}

function departmentEditor(container, options) {
	$(
			'<input name="dept_Name" data-text-field="dept_Name" id="dept_Id" data-value-field="dept_Id" validationmessage="Departement name is required" data-bind="value:' + options.field + '" required="required"/>')
			.appendTo(container).kendoComboBox({
				autoBind : false,
				placeholder : "Select Department",				
				dataSource : {
					transport : {
						read : "${departmentUrl}"
					}
				},
				change : function (e) {
		            if (this.value() && this.selectedIndex == -1) {                    
						alert("Department doesn't exist!");
		                $("#dept_Id").data("kendoComboBox").value("");
		        	}
			    } 
				
			});
	 $('<span class="k-invalid-msg" data-for="dept_Name"></span>').appendTo(container); 
}

function usersLevel1Editor(container, options) {
	$(
			'<input name="userNameLevel1" data-text-field="userName" id="level1User" data-value-field="urId" required="true" validationmessage="Level 1 user name is required" data-bind="value:' + options.field + '" />')
			.appendTo(container).kendoComboBox({
				autoBind : false,
				placeholder : "Select User",
				/* cascadeFrom: "dept_Id", */
				headerTemplate : '<div class="dropdown-header">'
					+ '<span class="k-widget k-header">Photo</span>'
					+ '<span class="k-widget k-header">Contact info</span>'
					+ '</div>',
				template : '<table><tr><td rowspan=2><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.personId#'/>" width=40 height=55 alt=\"No Image to Display\" /></span></td>'
					+ '<td align="left"><span class="k-state-default"><b>#: data.userName #</b></span><br>'
					+ '<span class="k-state-default"><i>Dept : #: data.dept_Name #</i></span><br>'
					+ '<span class="k-state-default"><i>Desg : #: data.designation #</i></span></td></tr></table>',
				dataSource : {
					transport : {
						read : "${userLoginsUrl}",
					}
				},
				change : function (e) {
			           if (this.value() && this.selectedIndex == -1) {                    
			               alert("User doesn't exist!");
			               $("#level1User").data("kendoComboBox").value("");
			    }
			 }						
				
			});
	 $('<span class="k-invalid-msg" data-for="userNameLevel1"></span>').appendTo(container); 
}

function usersLevel2Editor(container, options) {
	$(
			'<input name="userNameLevel2" data-text-field="userName" id="level2User" data-value-field="urId" validationmessage="Level 2 user name is required" data-bind="value:' + options.field + '" required="true"/>')
			.appendTo(container).kendoComboBox({
				autoBind : false,
				placeholder : "Select User",
				/* cascadeFrom: "dept_Id", */
				headerTemplate : '<div class="dropdown-header">'
					+ '<span class="k-widget k-header">Photo</span>'
					+ '<span class="k-widget k-header">Contact info</span>'
					+ '</div>',
				template : '<table><tr><td rowspan=2><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.personId#'/>" width=40 height=55 alt=\"No Image to Display\" /></span></td>'
					+ '<td align="left"><span class="k-state-default"><b>#: data.userName #</b></span><br>'
					+ '<span class="k-state-default"><i>Dept : #: data.dept_Name #</i></span><br>'
					+ '<span class="k-state-default"><i>Desg : #: data.designation #</i></span></td></tr></table>',
				dataSource : {
					transport : {
						read : "${userLoginsUrl}",
					}
				},
				change : function (e) {
			           if (this.value() && this.selectedIndex == -1) {                    
			               alert("User doesn't exist!");
			               $("#level2User").data("kendoComboBox").value("");
			    }
			 }						
				
			});
	 $('<span class="k-invalid-msg" data-for="userNameLevel2"></span>').appendTo(container); 
}

function usersLevel3Editor(container, options) {
	$(
			'<input name="userNameLevel3" data-text-field="userName" id="level3User" data-value-field="urId" validationmessage="Level 3 user name is required" data-bind="value:' + options.field + '" required="true"/>')
			.appendTo(container).kendoComboBox({
				autoBind : false,
				placeholder : "Select User",
				/* cascadeFrom: "dept_Id", */
				headerTemplate : '<div class="dropdown-header">'
					+ '<span class="k-widget k-header">Photo</span>'
					+ '<span class="k-widget k-header">Contact info</span>'
					+ '</div>',
				template : '<table><tr><td rowspan=2><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.personId#'/>" width=40 height=55 alt=\"No Image to Display\" /></span></td>'
					+ '<td align="left"><span class="k-state-default"><b>#: data.userName #</b></span><br>'
					+ '<span class="k-state-default"><i>Dept : #: data.dept_Name #</i></span><br>'
					+ '<span class="k-state-default"><i>Desg : #: data.designation #</i></span></td></tr></table>',
				dataSource : {
					transport : {
						read : "${userLoginsUrl}",
					}
				},
				change : function (e) {
			           if (this.value() && this.selectedIndex == -1) {                    
			               alert("User doesn't exist!");
			               $("#level3User").data("kendoComboBox").value("");
			    }
			 }						
				
			});
	 $('<span class="k-invalid-msg" data-for="userNameLevel3"></span>').appendTo(container); 
} 

function level1NotifiedUsersEditor(container, options) {
	var model = options.model;
	model.level1NotifiedUsers = model.level1NotifiedUsersDummy;
	
	$("<select multiple='multiple' style='width:150px;' id = 'level1NotifiedUsers'" + "data-bind='value : level1NotifiedUsers'/>")
			.appendTo(container).kendoMultiSelect({
				autoBind: false,
				groupable: false,
				placeholder : "Select User",
				dataTextField : "notifiedUser",
				dataValueField : "urId", 
				dataSource : {
					transport : {
						read : "${notifiedUserLoginsUrl}",
					}
				}			
			});
} 

function level2NotifiedUsersEditor(container, options) {
	var model = options.model;
	model.level2NotifiedUsers = model.level2NotifiedUsersDummy;
	
	$("<select multiple='multiple' style='width:150px;' id = 'level2NotifiedUsers'" + "data-bind='value : level2NotifiedUsers'/>")
			.appendTo(container).kendoMultiSelect({
				autoBind: false,
				groupable: false,
				placeholder : "Select User",
				dataTextField : "notifiedUser",
				dataValueField : "urId", 
				dataSource : {
					transport : {
						read : "${notifiedUserLoginsUrl}",
					}
				}			
			});
} 

function level3NotifiedUsersEditor(container, options) {
	var model = options.model;
	model.level3NotifiedUsers = model.level3NotifiedUsersDummy;
	
	$("<select multiple='multiple' style='width:150px;' id = 'level3NotifiedUsers'" + "data-bind='value : level3NotifiedUsers'/>")
			.appendTo(container).kendoMultiSelect({
				autoBind: false,
				groupable: false,
				placeholder : "Select User",
				dataTextField : "notifiedUser",
				dataValueField : "urId", 
				dataSource : {
					transport : {
						read : "${notifiedUserLoginsUrl}",
					}
				}			
			});
} 

// Onclick Functions

var SelectedRowId = ""; 
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
				$('#helpTopicGrid').data('kendoGrid').dataSource.read();
			}
		});
	}
  
  $("#helpTopicGrid").on("click", "#temPID", function(e) {
	  
	  var button = $(this), enable = button.text() == "Activate";
	  var widget = $("#helpTopicGrid").data("kendoGrid");
	  var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
	  var result=securityCheckForActionsForStatus("./CustomerCare/HelpTopic/activateInactivateButton"); 
	  if(result=="success"){     
		  
						if (enable) 
						{
							$.ajax
							({
								type : "POST",
								url : "./helpTopics/helpTopicStatus/" +dataItem.id+ "/activate",
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
									button.text('Inactivate');
									$('#helpTopicGrid').data('kendoGrid').dataSource.read();
								}
							});
						} 
						else 
						{
							$.ajax
							({
								type : "POST",
								url : "./helpTopics/helpTopicStatus/" + dataItem.id + "/deactivate",
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
									button.text('Activate');
									$('#helpTopicGrid').data('kendoGrid').dataSource.read();
								}
							});
						}
	        }  
   });
  
	var setApCode = "";
	var flagHelpTopicName = true;
function helpTopicEvent(e)
{
	/***************************  to remove the id from pop up  **********************/
	$('div[data-container-for="topicId"]').remove();
	$('label[for="topicId"]').closest('.k-edit-label').remove();
	
	/* $(".k-edit-form-container").css({
		"width" : "450px"
	}); */
	
	$(".k-window").css({
		"top" : "100px"
		});
	
	$(".k-edit-field").each(function () {
		$(this).find("#temPID").parent().remove();  
   	});
	
	$('label[for=level1NotifiedUserNames]').parent().hide();
	$('div[data-container-for="level1NotifiedUserNames"]').hide();
	
	$('label[for=level2NotifiedUserNames]').parent().hide();
	$('div[data-container-for="level2NotifiedUserNames"]').hide();
	
	$('label[for=level3NotifiedUserNames]').parent().hide();
	$('div[data-container-for="level3NotifiedUserNames"]').hide();
	
	$('label[for=createdDate]').parent().hide();
	$('div[data-container-for="createdDate"]').hide();
	
	$('label[for=status]').parent().hide();
	$('div[data-container-for="status"]').hide();
	
	$('label[for=userNameLevel1]').parent().hide();
	$('div[data-container-for="userNameLevel1"]').hide();
	
	$('label[for=userNameLevel2]').parent().hide();
	$('div[data-container-for="userNameLevel2"]').hide();
	
	$('label[for=dept_Name]').parent().hide();
	$('div[data-container-for="dept_Name"]').hide();
	
	$('label[for=userNameLevel3]').parent().hide();
	$('div[data-container-for="userNameLevel3"]').hide();
	
	$('label[for="createdBy"]').parent().hide();  
	$('div[data-container-for="createdBy"]').hide();
	
	/************************* Button Alerts *************************/
	if (e.model.isNew()) 
    {
		flagHelpTopicName = true;
	    securityCheckForActions("./CustomerCare/HelpTopic/createButton");
		setApCode = $('input[name="topicId"]').val();
		$(".k-window-title").text("Add New Help Topic Details");
		$(".k-grid-update").text("Save");		
    }
	else{
		flagHelpTopicName = false;
		$('input[name="topicName"]').prop("readonly",true);
		securityCheckForActions("./CustomerCare/HelpTopic/editButton");
		$(".k-window-title").text("Edit Help Topic Details");
	}
}

function helpTopicDeleteEvent(){
	securityCheckForActions("./CustomerCare/HelpTopic/deleteButton");
	var conf = confirm("Are you sure want to delete this Help Topic?");
	    if(!conf){
	    $('#helpTopicGrid').data().kendoGrid.dataSource.read();
	    throw new Error('deletion aborted');
	     }
}

$("#grid").on("click", ".k-grid-Clear_Filter", function(){
    //custom actions
	$("form.k-filter-menu button[type='reset']").slice().trigger("click");
	var grid = $("#helpTopicGrid").data("kendoGrid");
	grid.dataSource.read();
	grid.refresh();
});

function helpTopicOnRequestStart(r){
	
	
	$('.k-grid-update').hide();
	$('.k-edit-buttons')
			.append(
					'<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
	$('.k-grid-cancel').hide();
	/* 
	var helpTopicGrid = $("#helpTopicGrid").data("kendoGrid");
	helpTopicGrid.cancelRow(); */
}

function onRequestEnd(r) {
	var helpTopicData = $('#helpTopicGrid').data("kendoGrid");
	if (typeof r.response != 'undefined') {
		
		if (r.response.status == "FAIL") {

			errorInfo = "";

			for (var s = 0; s < r.response.result.length; s++) {
				errorInfo += (s + 1) + ". "
						+ r.response.result[s].defaultMessage + "<br>";

			}

			if (r.type == "create") {
				//alert("Error: Creating the USER record\n\n" + errorInfo);

				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Error: Creating the Service Master Details<br>" + errorInfo);
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
				//alert("Error: Updating the USER record\n\n" + errorInfo);
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Error: Updating the Service Master Details<br>" + errorInfo);
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
			$("#alertsBox").html("Can't Delete Help Topic Details, Child Record Found");
			$("#alertsBox").dialog({
				modal : true,
				buttons : {
					"Close" : function() {
						$(this).dialog("close");
					}
				}
			});
			$('#helpTopicGrid').data('kendoGrid').refresh();
			$('#helpTopicGrid').data('kendoGrid').dataSource.read();
		return false;
	}
	
	if (r.response.status == "AciveHelpTopicDestroyError") {
		$("#alertsBox").html("");
		$("#alertsBox").html("Active help topic details cannot be deleted");
		$("#alertsBox").dialog({
			modal : true,
			buttons : {
				"Close" : function() {
					$(this).dialog("close");
				}
			}
		});
		$('#helpTopicGrid').data('kendoGrid').refresh();
		$('#helpTopicGrid').data('kendoGrid').dataSource.read();
	return false;
}
		
		else if (r.response.status == "INVALID") {

			errorInfo = "";

			errorInfo = r.response.result.invalid;

			if (r.type == "create") {
				//alert("Error: Creating the USER record\n\n" + errorInfo);
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Error: Creating the Service Master Details<br>" + errorInfo);
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
				
				//alert("Error: Creating the USER record\n\n" + errorInfo);
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Error: Creating the Service Master Details<br>" + errorInfo);
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
				//alert("Error: Creating the USER record\n\n" + errorInfo);
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Error: Updating the Service Master Details<br>" + errorInfo);
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
			$("#alertsBox").html("New Help Topic Details created successfully");
			$("#alertsBox").dialog({
				modal : true,
				buttons : {
					"Close" : function() {
						$(this).dialog("close");
					}
				}
			});
			
			 var grid = $("#helpTopicGrid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh(); 

		}

		else if (r.type == "update") {
			$("#alertsBox").html("");
			$("#alertsBox").html("Help Topic Details updated successfully");
			$("#alertsBox").dialog({
				modal : true,
				buttons : {
					"Close" : function() {
						$(this).dialog("close");
					}
				}
			});
			 var grid = $("#helpTopicGrid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();     

		}
		
		else if (r.type == "destroy") {
			$("#alertsBox").html("");
			$("#alertsBox").html("Help Topic Details delete successfully");
			$("#alertsBox").dialog({
				modal : true,
				buttons : {
					"Close" : function() {
						$(this).dialog("close");
					}
				}
			});
			helpTopicData.dataSource.read();
			helpTopicData.refresh();
		}
	}
}

//register custom validation rules
var normalSLAValue = 0;
var level1SLAValue = 0;
var level2SLAValue = 0;
(function ($, kendo) 
	{   	  
    $.extend(true, kendo.ui.validator, 
    {
         rules: 
         { // custom rules
        	 
       topicNameRequiredValidation : function(input, params){
           if (input.attr("name") == "topicName")
           {
            return $.trim(input.val()) !== "";
           }
           return true;
          },
          topicNameLengthValidation : function (input, params) 
          {         
              if (input.filter("[name='topicName']").length && input.val() != "") 
              {
             	 return /^[a-zA-Z ]{1,45}$/.test(input.val());
              }        
              return true;
          }, 
          normalSLARequiredValidation : function(input, params){
        	     normalSLAValue = input.val();
                 if (input.attr("name") == "normalSLA")
                 {
                  return $.trim(input.val()) !== "";
                 }
                 return true;
                },
          normalSLALengthValidation: function (input, params) 
	             {         
	                 if (input.filter("[name='normalSLA']").length && input.val() != "") 
	                 {
	                	 return /^[0-9]{1,2}$/.test(input.val());
	                 }        
	                 return true;
	             },

	      level1SLARequiredValidation : function(input, params){
	    	         level1SLAValue = input.val(); 
	                 if (input.attr("name") == "level1SLA")
	                 {
	                  return $.trim(input.val()) !== "";
	                 }
	                 return true;
	                },

	      level1SLALengthValidation: function (input, params) 
		             {         
		                 if (input.filter("[name='level1SLA']").length && input.val() != "") 
		                 {
		                	 return /^[0-9]{1,2}$/.test(input.val());
		                 }        
		                 return true;
		             },  
		  /* level1SLAValueValidation: function (input, params) 
		             {         
		                 if (input.filter("[name='level1SLA']").length && input.val()) 
		                 {
		                	 var currentValue=input.val();
		                	 var flagDate = false;
		                	 if(currentValue>=normalSLAValue){
		                		 flagDate=true;
		                	 }
		                	 return flagDate;
		                 }        
		                 return true;
		             },  */            
		  level2SLARequiredValidation : function(input, params){
			             level2SLAValue = input.val(); 
		                 if (input.attr("name") == "level2SLA")
		                 {
		                  return $.trim(input.val()) !== "";
		                 }
		                 return true;
		                },
		  level2SLALengthValidation: function (input, params) 
			             {         
			                 if (input.filter("[name='level2SLA']").length && input.val() != "") 
			                 {
			                	 return /^[0-9]{1,2}$/.test(input.val());
			                 }        
			                 return true;
			             },   
		  /* level2SLAValueValidation: function (input, params) 
			             {         
			                 if (input.filter("[name='level2SLA']").length && input.val()) 
			                 {
			                	 var currentValue=input.val();
			                	 var flagDate = false;
			                	 if(currentValue>=normalSLAValue && currentValue>=level1SLAValue){
			                		 flagDate=true;
			                	 }
			                	 return flagDate;
			                 }        
			                 return true;
			             },   */            
	      level3SLARequiredValidation : function(input, params){
			                 if (input.attr("name") == "level3SLA")
			                 {
			                  return $.trim(input.val()) !== "";
			                 }
			                 return true;
			                },
		  level3SLALengthValidation: function (input, params) 
				             {         
				                 if (input.filter("[name='level3SLA']").length && input.val() != "") 
				                 {
				                	 return /^[0-9]{1,2}$/.test(input.val());
				                 }        
				                 return true;
				             },  
		 /* level3SLAValueValidation: function (input, params) 
				             {         
				                 if (input.filter("[name='level3SLA']").length && input.val()) 
				                 {
				                	 var currentValue=input.val();
				                	 var flagDate = false;
				                	 if(currentValue>=normalSLAValue && currentValue>=level1SLAValue && currentValue>=level2SLAValue){
				                		 flagDate=true;
				                	 }
				                	 return flagDate;
				                 }        
				                 return true;
				             },	  */            
		  topicDescRequiredValidation : function(input, params){
              if (input.attr("name") == "topicDesc")
              {
               return $.trim(input.val()) !== "";
              }
              return true;
             },
             topicDescLengthValidation: function (input, params)
             {
       	  if (input.filter("[name='topicDesc']").length && input.val() != "") 
             {
            	 return /^[\s\S]{1,500}$/.test(input.val());
             }        
             return true;
                },
             topicNameUniquevalidation : function(input, params){
					if(flagHelpTopicName){
						if (input.filter("[name='topicName']").length && input.val()) 
						{
							var flag = true;
							$.each(helpTopicNameArray, function(idx1, elem1) {
								if(elem1.toLowerCase() == input.val().toLowerCase())
								{
									flag = false;
								}	
							});
							return flag;
						}
					}
					return true;
				},
         },
         messages: 
         {
			//custom rules messages
			topicNameRequiredValidation : "Topic name is required",
			topicNameLengthValidation : "Name can contain alphabets,spaces and max 45 characters",
			normalSLARequiredValidation : "Normal SLA is required",
			normalSLALengthValidation : "Normal SLA max 2 digit number",
			level1SLARequiredValidation : "Level 1 SLA is required",
		    level1SLALengthValidation : "Level 1 SLA max 2 digit number",
		    //level1SLAValueValidation : "Level 1 SLA must be greater than of normal SLA",
		    level2SLARequiredValidation : "Level 2 SLA is required",
		    level2SLALengthValidation : "Level 2 SLA max 2 digit number",
		   // level2SLAValueValidation : "Level 2 SLA must be greater than of normal SLA and level 1 SLA",
			level3SLARequiredValidation : "Level 3 SLA is required",
			level3SLALengthValidation : "Level 3 SLA max 2 digit number",
			//level3SLAValueValidation : "Level 3 SLA must be greater than of normal SLA,level 1 SLA and level 2 SLA",
			topicDescRequiredValidation: "Description is required",
			topicDescLengthValidation : "Description field allows max 500 characters",
			topicNameUniquevalidation : "This help topic is already exist"
     	 }
    });
    
})(jQuery, kendo);
  //End Of Validation
  
</script>
<style>

.bgGreenColor{
background: #99E699
}

.bgBlueColor{
background: #82CDFF
}

.bgRedColor{
background: #FF8484
}
</style>
