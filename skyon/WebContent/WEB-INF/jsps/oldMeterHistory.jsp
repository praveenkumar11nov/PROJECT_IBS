<%@include file="/common/taglibs.jsp"%>

<link	href="<c:url value='/resources/twitter-bootstrap-wizard/bootstrap/css/bootstrap.min.css'/>"	rel="stylesheet" />

<%--  <c:url value="/prePaidMeterGeneration/createUrl"  var="createUrl"></c:url> --%>
 <c:url value="/oldMeterHistoryDetails/readUrl"  var="readUrl"></c:url>
<%-- <c:url value="/prePaidMeterGeneration/updateUrl"  var="updateUrl"></c:url> 
<c:url value="/openNewTickets/readTowerNames" var="getAllBlockUrl" />

<c:url value="/openNewTickets/readPropertyNumbers" var="getAllPropertyUrl" />
<c:url value="/prepaidMeters/getPersonListBasedOnPropertyNumbers" var="personNamesAutoBasedOnPersonTypeUrl"></c:url> --%>
<script>
	function closePopup() {
	
		$("#ajax").hide();
	}
	function closePopup1() {
	
		$("#ajax1").hide();
	}
	
</script>
<c:if test="${not empty msg}">
	<div aria-hidden="false" role="basic" tabindex="-1" id="ajax"
		class="modal fade in" style="display: block;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button aria-hidden="true" data-dismiss="modal" class="close"
						type="button"></button>
					<h4 class="modal-title">Alert</h4>
				</div>
				<div class="modal-body">${msg}</div>
				<div class="modal-footer">
					<button data-dismiss="modal" class="btn default" type="button"
						onclick="closePopup()">Close</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
	</div>

</c:if>
<kendo:grid name="grid"  pageable="false" resizable="true"  sortable="false" reorderable="true" selectable="false" scrollable="true" filterable="false" groupable="false">
    <kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" refresh="true" info="true" previousNext="true">
	<kendo:grid-pageable-messages itemsPerPage="Status items per page" empty="No status item to display" refresh="Refresh all the status items" 
			display="{0} - {1} of {2} New Status Items" first="Go to the first page of status items" last="Go to the last page of status items" next="Go to the next page of status items"
			previous="Go to the previous page of status items"/>
	</kendo:grid-pageable>
	<kendo:grid-filterable extra="false">
		<kendo:grid-filterable-operators>
			<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains"/>
			<kendo:grid-filterable-operators-date gt="Is after" lt="Is before"/>
		</kendo:grid-filterable-operators> 
	</kendo:grid-filterable>
	<kendo:grid-editable mode="popup"/>
		<kendo:grid-toolbar>
		      <kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilter()>Clear Filter</a>"/>
	          <%-- <kendo:grid-toolbarItem name="ConsumerTemplatesDetailsExport" text="Export To Excel" /> --%>
	       
	    </kendo:grid-toolbar>
	<kendo:grid-columns>
	    
	    <kendo:grid-column title="PrePaidMeterId" field="meterHtryId" width="70px" hidden="true" filterable="false" sortable="false" />
	    
	     <kendo:grid-column title="Consumer ID" field="consumerId" width="70px" filterable="true" />
	    <kendo:grid-column title="Property&nbsp;No*" field="propertyName" width="70px" filterable="true" >
	   <%--  <kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function storeNameFilter(element) 
						   	{
								element.kendoAutoComplete({
									dataSource : {
										transport : {		
											read :  "${propertyNameFilterUrl}"
										}
									} 
								});
						   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable> --%>
	     </kendo:grid-column>
	     
	     <kendo:grid-column title="Person Name" field="personName" width="70px" filterable="true" />
	     
	      <kendo:grid-column title="Meter&nbsp;Number" field="meterNumber"  filterable="true" 
			width="70px" >
		  <%--  <kendo:grid-column-filterable>
						<kendo:grid-column-filterable-ui>
							<script type="text/javascript">
								function storeNameFilter(element) 
							   	{
									element.kendoAutoComplete({
										dataSource : {
											transport : {		
												read :  "${meterNumberFilterUrl}"
											}
										} 
									});
							   	}
							</script>
						</kendo:grid-column-filterable-ui>
					</kendo:grid-column-filterable> --%>
		    </kendo:grid-column>
	        <kendo:grid-column title="EB&nbsp;Reading" field="initialReading"  filterable="true" 
			width="70px" ></kendo:grid-column>
			<kendo:grid-column title="DG&nbsp;Reading" field="dgReading"  filterable="true" 
			width="70px" ></kendo:grid-column>
			<kendo:grid-column title="Balance&nbsp;" field="initialBalnce"  filterable="true" 
			width="70px" ></kendo:grid-column>
			<kendo:grid-column title="Service&nbsp;Start&nbsp;Date" field="readingDate" format="{0:dd/MM/yyyy}" filterable="true" 
			width="100px" ></kendo:grid-column>
	        <kendo:grid-column title="Service&nbsp;End&nbsp;Date" field="serviceEndDate" format="{0:dd/MM/yyyy}" filterable="true" 
			width="100px" ></kendo:grid-column>

	<%--     <kendo:grid-column title="Status" field="status" width="70px" filterable="true">
	    <kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function storeNameFilter(element) 
						   	{
								element.kendoAutoComplete({
									dataSource : {
										transport : {		
											read :  "${statusFilterUrl}"
										}
									} 
								});
						   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	    </kendo:grid-column> --%>

<%--         <kendo:grid-column title=""
				template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Active#=data.amrId#'>#= data.status == 'Active' ? 'Inactivate' : 'Activate' #</a>"
				width="80px" /> --%>
		
		
	</kendo:grid-columns>

	<kendo:dataSource >
		<kendo:dataSource-transport>
		<kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json" />
		</kendo:dataSource-transport> 

		<kendo:dataSource-schema >
			<kendo:dataSource-schema-model id="meterHtryId">
				<kendo:dataSource-schema-model-fields>
					<kendo:dataSource-schema-model-field name="meterHtryId" type="number"/>
					
					<kendo:dataSource-schema-model-field name="propertyName" type="string" />
					
					<kendo:dataSource-schema-model-field name="personName" type="string" />
					<kendo:dataSource-schema-model-field name="consumerId" type="string"/>
					<kendo:dataSource-schema-model-field name="meterNumber" type="string"/>
					
  					<kendo:dataSource-schema-model-field name="status" type="string"/>
  					<kendo:dataSource-schema-model-field name="initialReading" type="number" defaultValue="0">
  					</kendo:dataSource-schema-model-field>
  					<kendo:dataSource-schema-model-field name="readingDate" type="string"/>
  					<kendo:dataSource-schema-model-field name="dgReading" type="number" defaultValue="0"/>
  					<kendo:dataSource-schema-model-field name="initialBalnce" type="number" defaultValue="0"/>
  					<kendo:dataSource-schema-model-field name="serviceEndDate" type="string"></kendo:dataSource-schema-model-field>
  
				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>

	</kendo:dataSource>

</kendo:grid>

<script>
function clearFilter() {
	//custom actions

	$("form.k-filter-menu button[type='reset']").slice()
			.trigger("click");
	var gridServiceMaster = $("#grid").data("kendoGrid");
	gridServiceMaster.dataSource.read();
	gridServiceMaster.refresh();
}
</script>

 <style>
.container {
	float: left;
	margin-left: 0px;
	min-height: 0px;
	width:150px;
}


.form-horizontal .controls {
    margin-left: 164px;
}


.tab-content > .active, .pill-content > .active {
    display: block;
   min-height: 330px;
}


.ui-dialog-content .ui-widget-content{
 	display: block;
    height: 561px;
    min-height: 0;
    width: 967px;
}

.pager {
    list-style: outside none none;
    margin: 0;
    }
</style> 