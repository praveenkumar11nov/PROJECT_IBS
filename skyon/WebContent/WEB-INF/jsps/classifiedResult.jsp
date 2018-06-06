<%@include file="/common/taglibs.jsp"%>

<c:url value="/classifiedResult/readUrl"  var="readUrl"></c:url> 


<kendo:grid name="grid"  pageable="true" resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true" filterable="false" groupable="true">
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
	          <kendo:grid-toolbarItem name="ClassifiedTemplatesDetailsExport" text="Export To Excel" />
	    </kendo:grid-toolbar>
	<kendo:grid-columns>
	    
	    <kendo:grid-column title="PrePaidMeterId" field="prePaidId" width="70px" hidden="true" filterable="false" sortable="false" />
	    
	    <kendo:grid-column title="Tower&nbsp;*" field="blockId" width="70px" filterable="false" hidden="true"/>
	    
	     <kendo:grid-column title="Tower&nbsp;*" field="blocksName" width="70px" filterable="false"  hidden="true">
	     
	     </kendo:grid-column>
	    
	    <kendo:grid-column title="Property&nbsp;*" field="propertyId" width="70px" filterable="false" hidden="true"/>
	    
	    <kendo:grid-column title="Property&nbsp;No*" field="propertyName" width="70px" filterable="true">
	    <
	     </kendo:grid-column>
	     
	       <kendo:grid-column title="person&nbsp;*" field="personId" width="70px" filterable="false" hidden="true"/>
	     <kendo:grid-column title="Person Name" field="personName" width="70px" filterable="false" />
	     
	      <kendo:grid-column title="Mobile&nbsp;Number" field="mobile_No" width="70px" filterable="false">
		   
		    </kendo:grid-column>
	     
	       <kendo:grid-column title="Email&nbsp;ID" field="emailId" width="70px" filterable="false">
		   
		    </kendo:grid-column>
		    
		     <kendo:grid-column title="Information" field="information" width="300px" filterable="false">
		   
		    </kendo:grid-column>

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

	<kendo:dataSource pageSize="20" >
	 	<kendo:dataSource-transport>
		<kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json" />
		</kendo:dataSource-transport> 
 
		<kendo:dataSource-schema >
			<kendo:dataSource-schema-model id="prePaidId">
				<kendo:dataSource-schema-model-fields>
					<kendo:dataSource-schema-model-field name="prePaidId" type="number"/>
					
					 <kendo:dataSource-schema-model-field name="blockId" type="number"/> 
					
					<kendo:dataSource-schema-model-field name="blocksName" type="string"/>
					
					<kendo:dataSource-schema-model-field name="personId" type="number"/>
				<kendo:dataSource-schema-model-field name="propertyId" type="number"/> 
					
					<kendo:dataSource-schema-model-field name="propertyName" type="string"/>
					
					<kendo:dataSource-schema-model-field name="personName" type="string"/>
					
					<kendo:dataSource-schema-model-field name="mobile_No" type="string"/>
					
					<kendo:dataSource-schema-model-field name="emailId" type="string"/>
					
  					<kendo:dataSource-schema-model-field name="information" type="string"/>
  
				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>

	</kendo:dataSource>

</kendo:grid>

<div id="alertsBox" title="Alert"></div>
<%-- <div id="uploadTransactionFileDialog" style="display: none;">
	<form id="uploadBatchFileForm">
		<table>
			<tr>
				<td>Upload ConsumerData Batch File</td>
				<td><kendo:upload name="files" id="batchFile"></kendo:upload></td>
		</table>
	</form>
</div> --%>
<script>

function clearFilter() {
	//custom actions

	$("form.k-filter-menu button[type='reset']").slice()
			.trigger("click");
	var gridServiceMaster = $("#grid").data("kendoGrid");
	gridServiceMaster.dataSource.read();
	gridServiceMaster.refresh();
}

	
$("#grid").on("click",".k-grid-ClassifiedTemplatesDetailsExport", function(e) {
	
	
	  window.open("./ConsumerTemplate/ClassifiedTemplatesDetailsExport");
});	


	
</script>