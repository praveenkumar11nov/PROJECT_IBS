
<%@include file="/common/taglibs.jsp"%>

  <c:url value="/preRegisteredUsers/readPreRegisteredUsersUrl" var="readPreRegisteredUsersUrl" />
  
  <c:url value="/preRegisteredUsers/getPreRegVisitorNameForFilter" var="getPreRegVisitorNameForFilter" />
  <c:url value="/preRegisteredUsers/getPreRegVisitorContactNoForFilter" var="getPreRegVisitorContactNoForFilter" />
  <c:url value="/preRegisteredUsers/getPreRegVisitorBlockNameForFilter" var="getPreRegVisitorBlockNameForFilter" />
  <c:url value="/preRegisteredUsers/getPreRegVisitorPropertyNoForFilter" var="getPreRegVisitorPropertyNoForFilter" />
  <c:url value="/preRegisteredUsers/getPreRegVisitorGenderForFilter" var="getPreRegVisitorGenderForFilter" />
  <c:url value="/preRegisteredUsers/getPreRegVisitorPasswordForFilter" var="getPreRegVisitorPasswordForFilter" />
  <c:url value="/preRegisteredUsers/getPreRegVisitorNoOfVisitorsForFilter" var="getPreRegVisitorNoOfVisitorsForFilter" />
  <c:url value="/preRegisteredUsers/getPreRegVisitorCreatedByForFilter" var="getPreRegVisitorCreatedByForFilter" />
  <c:url value="/preRegisteredUsers/getPreRegVisitorStatusForFilter" var="getPreRegVisitorStatusForFilter" />
  
  
  <kendo:grid name="preRegisteredVisitorGrid" pageable="true" resizable="true" filterable="true" sortable="true" reorderable="true" selectable="true" scrollable="true" groupable="true">	
		<%-- <kendo:grid-editable mode="popup"/> --%>
		<kendo:grid-toolbar>
		<kendo:grid-toolbarItem name="preRegisteredVisitorTemplatesDetailsExport" text="Export To Excel" />
			  <kendo:grid-toolbarItem name="preRegisteredVisitorPdfTemplatesDetailsExport" text="Export To PDF" /> 
			<kendo:grid-toolbarItem text="Clear Filter" name="Clear_Filter"/>
		</kendo:grid-toolbar>
		
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" ></kendo:grid-pageable>
			<kendo:grid-filterable extra="false">
		 		<kendo:grid-filterable-operators>
		 		
		  			<kendo:grid-filterable-operators-string eq="Is equal to"/>
		  			<%-- <kendo:grid-filterable-operators-date lte="Date Before" gte="Date After"/> --%>
				</kendo:grid-filterable-operators>			
		</kendo:grid-filterable>
		
		<kendo:grid-columns>
		
		    <%-- <kendo:grid-column title="Image" field="image" template ="<span title='Click to Upload Image' ><img src='./users/visitor/getVisitorimage/#=viId#' id='myImages_#=viId#' width='80px' height='80px'/></span>"
				filterable="false" width="94px" sortable="false">
			</kendo:grid-column> --%>
			
		    <kendo:grid-column title="Visitor Id&nbsp;*" field="viId" width="80px" hidden="true">
		      <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getPreRegVisitorIdValuesForFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	  </kendo:grid-column-filterable>
	       </kendo:grid-column>		
	           
		    <kendo:grid-column title="Visitor Name&nbsp;*" field="visitorName" width="80px">
		       <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getPreRegVisitorNameForFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	   </kendo:grid-column-filterable>
	    	 </kendo:grid-column>
		    
		    <kendo:grid-column title="Visitor ContactNo&nbsp;*" field="visitorContactNo" width="100px">
		       <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getPreRegVisitorContactNoForFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	   </kendo:grid-column-filterable>
	    	 </kendo:grid-column>
	    	 
		    <kendo:grid-column title="Block Name&nbsp;*" field="blockName" width="100px">
		      <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getPreRegVisitorBlockNameForFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	   </kendo:grid-column-filterable>
	    	 </kendo:grid-column>
		    
		    <kendo:grid-column title="Property Name&nbsp;*" field="property_No" width="100px">
		       <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getPreRegVisitorPropertyNoForFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	   </kendo:grid-column-filterable>
	    	 </kendo:grid-column>
	    	 
		  <%--   <kendo:grid-column title="Gender&nbsp;*" field="gender" width="100px">
		       <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getPreRegVisitorGenderForFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	   </kendo:grid-column-filterable>
	    	 </kendo:grid-column> --%>
	    	 
		    <kendo:grid-column title="Visitor Password&nbsp;*" field="visitorPassword" width="100px">
		       <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getPreRegVisitorPasswordForFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	   </kendo:grid-column-filterable>
	    	 </kendo:grid-column>
	    	 
		    <kendo:grid-column title="Number Of Visitors&nbsp;*" field="noOfVisitors" width="100px">
		      <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getPreRegVisitorNoOfVisitorsForFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	   </kendo:grid-column-filterable>
	    	 </kendo:grid-column>
		    
		    <kendo:grid-column title="Pre-Registered By&nbsp;*" field="createdBy" width="100px">
		       <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getPreRegVisitorCreatedByForFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	   </kendo:grid-column-filterable>
	    	 </kendo:grid-column>
		    
		    <kendo:grid-column title="Status&nbsp;*" field="status" width="100px">
		       <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getPreRegVisitorStatusForFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	   </kendo:grid-column-filterable>
	    	 </kendo:grid-column>
		</kendo:grid-columns>
		
		<kendo:dataSource>
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-read url="${readPreRegisteredUsersUrl}" dataType="json" type="POST" contentType="application/json" />		
			</kendo:dataSource-transport>
			
			<kendo:dataSource-schema>
				<kendo:dataSource-schema-model id="viId">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="viId" type="number"/>
						<kendo:dataSource-schema-model-field name="visitorName"/>
						<kendo:dataSource-schema-model-field name="visitorContactNo"/>
						<kendo:dataSource-schema-model-field name="blockName"/>						
						<kendo:dataSource-schema-model-field name="property_No"/>
						<kendo:dataSource-schema-model-field name="visitorPassword"/> 
						<kendo:dataSource-schema-model-field name="gender"/> 					
						<kendo:dataSource-schema-model-field name="createdBy"/> 					
						<kendo:dataSource-schema-model-field name="noOfVisitors"/> 					
						<kendo:dataSource-schema-model-field name="status"/> 					
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
	</kendo:grid>
	<script>
	$("#preRegisteredVisitorGrid").on("click",".k-grid-preRegisteredVisitorTemplatesDetailsExport", function(e) {
		  window.open("./preRegisteredVisitorTemplate/preRegisteredVisitorTemplatesDetailsExport");
	});	  

	$("#preRegisteredVisitorGrid").on("click",".k-grid-preRegisteredVisitorPdfTemplatesDetailsExport", function(e) {
		  window.open("./preRegisteredVisitorPdfTemplate/preRegisteredVisitorPdfTemplatesDetailsExport");
	});

	</script>