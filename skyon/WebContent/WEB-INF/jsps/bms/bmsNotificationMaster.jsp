<%@include file="/common/taglibs.jsp"%>

<c:url value="/bmsNotificationMaster/readUrl" var="readUrl" />


<c:url value="/bmsSettings/departmentFilterUrl" var="departmentFilterUrl" />
<c:url value="/bmsSettings/designationFilterUrl" var="designationFilterUrl" />
<c:url value="/bmsSettings/commonFilterUrl" var="commonFilterUrl" />

<div id="loading"></div>
<kendo:grid name="bmsNotificationGrid" pageable="true" 
		resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true" 
		filterable="true" groupable="true" >
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" refresh="true" info="true" previousNext="true">
			<kendo:grid-pageable-messages itemsPerPage="Notification per page" empty="No Notification to display" refresh="Refresh all the Notification" 
			display="{0} - {1} of {2} Notification" first="Go to the first page of Notification" last="Go to the last page of Notification" next="Go to the next page of Notification"
			previous="Go to the previous page of Notification"/>
		</kendo:grid-pageable> 
		
		<kendo:grid-editable mode="popup"/>
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterBMSNotification()><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>"/>
		</kendo:grid-toolbar>
			<kendo:grid-filterable extra="false">
		 <kendo:grid-filterable-operators>
		  	<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains"/>
		 </kendo:grid-filterable-operators>
		</kendo:grid-filterable>
		
		
<kendo:grid-columns>
	
				
		<kendo:grid-column title="TrendLog&nbsp;Name" field="bmsElements"  width="85px">
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function bmsElementsFilter(element) {
							element.kendoAutoComplete({
										placeholder : "Enter Description",
										dataType : 'JSON',
										dataSource : {
											transport : {
												read : "${commonFilterUrl}/bmsElements"
											}
										}
									});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>
		
				
				
			<kendo:grid-column title="Department" field="dept_Name" 
				
				width="70px">
				<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function departmentFilter(element) {
								element.kendoAutoComplete({
									optionLabel: "Select",
									dataSource : {
										transport : {
											read : "${departmentFilterUrl}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	    	
			</kendo:grid-column>
				
				
			<kendo:grid-column title="Designation" field="dn_Name" 
				width="70px">
				<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function designationFilter(element) {
								element.kendoAutoComplete({
									optionLabel: "Select",
									dataSource : {
										transport : {
											read : "${designationFilterUrl}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	    	
			</kendo:grid-column>
			<kendo:grid-column title="Persons" field="personNames" width="90px"/>
			<kendo:grid-column title="Notified&nbsp;Status&nbsp;" field="bmsStatus" filterable="true" width="70px">
				<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function statusFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Select",
									dataSource: {
										transport: {
											read: "${commonFilterUrl}/bmsStatus"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	    
				</kendo:grid-column>
			
			<kendo:grid-column title="Notification Date Time" field="notificationDate" width="80px" format= "{0:dd/MM/yyyy hh:mm tt}">
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
			
				<kendo:grid-column title="SMS&nbsp;Status&nbsp;" field="smsStatus" filterable="true" width="70px">
				<script>
						function bmsElementsFilter(element) {
							element.kendoAutoComplete({
										placeholder : "Enter Description",
										dataType : 'JSON',
										dataSource : {
											transport : {
												read : "${commonFilterUrl}/smsStatus"
											}
										}
									});
						}
					</script>   
				</kendo:grid-column>
				
				<kendo:grid-column title="Mail&nbsp;Status&nbsp;" field="mailStatus" filterable="true" width="70px">
			<script>
						function bmsElementsFilter(element) {
							element.kendoAutoComplete({
										placeholder : "Enter Description",
										dataType : 'JSON',
										dataSource : {
											transport : {
												read : "${commonFilterUrl}/mailStatus"
											}
										}
									});
						}
					</script>   
				</kendo:grid-column>

		</kendo:grid-columns>	


	<kendo:dataSource pageSize="20" requestStart="requestStart" requestEnd="requestEnd">
			<kendo:dataSource-transport>
				
				<kendo:dataSource-transport-read url="${readUrl}" dataType="json"
					type="POST" contentType="application/json" />
				<kendo:dataSource-transport-parameterMap>
					<script>
						function parameterMap(options, type) {
							return JSON.stringify(options);
						}
					</script>
				</kendo:dataSource-transport-parameterMap>
			</kendo:dataSource-transport>
			
			<kendo:dataSource-schema>
				<kendo:dataSource-schema-model id="bmsNotifyId">
					<kendo:dataSource-schema-model-fields>
								<kendo:dataSource-schema-model-field name="bmsNotifyId" type="number"/>
								<kendo:dataSource-schema-model-field name="bmsElements" type="string"/>
								<kendo:dataSource-schema-model-field name="bmsStatus" type="string"/>
								<kendo:dataSource-schema-model-field name="smsStatus" type="string"/>
						        <kendo:dataSource-schema-model-field name="mailStatus" type="string"/>
   								<kendo:dataSource-schema-model-field name="dept_Name" type="string"/>
   								<kendo:dataSource-schema-model-field name="dn_Name" type="string"/>
						        <kendo:dataSource-schema-model-field name="notificationDate" type="date"/>
						        <kendo:dataSource-schema-model-field name="personNames"/>
						        
							</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
			
			</kendo:dataSource>				
		
</kendo:grid>


<script type="text/javascript">

function clearFilterBMSNotification()
{
   $("form.k-filter-menu button[type='reset']").slice().trigger("click");
   var gridStoreIssue = $("#bmsNotificationGrid").data("kendoGrid");
   gridStoreIssue.dataSource.read();
   gridStoreIssue.refresh();
}


function requestStart()
{
	
	kendo.ui.progress($("#loading"), true);

	
}

function requestEnd()
{
	kendo.ui.progress($("#loading"), false);
	
}






</script>