<%@include file="/common/taglibs.jsp"%>	
			 
			
			<c:url value="/common/getAllChecks" var="allChecksUrl" />
	<c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />
			
			

<c:url value="/ownerAudit/read1" var="readUrl1" />

<c:url value="/ownerAudit/nameFilter" var="nameFilter" />
<c:url value="/ownerAudit/updatedFilter" var="updatedFilter" />
<c:url value="/ownerAudit/previousFilter" var="previousFilter" />
<c:url value="/ownerAudit/currentFilter" var="currentFilter" />
<c:url value="/ownerAudit/updatedBy" var="updatedFilter1" />



	<kendo:grid name="grid" pageable="true"
		resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true"
		filterable="true" groupable="true">
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" ></kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
		 <kendo:grid-filterable-operators>
		  	<kendo:grid-filterable-operators-string eq="Is equal to"/>
		 </kendo:grid-filterable-operators>
			
		</kendo:grid-filterable>


    
     <kendo:grid-toolbar>
            
             <kendo:grid-toolbarItem text="Clear Filter" name="Clear_Filter"/>
        </kendo:grid-toolbar>
        
        <kendo:grid-columns>
           <kendo:grid-column title="Owner Name &nbsp; *" field="ownerName" width="170px">
           
          <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function DepartmentNameFilter(element) {
								element.kendoAutoComplete({
									dataSource: {
										transport: {
											read: "${nameFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	    
           
           </kendo:grid-column>
             <kendo:grid-column title="Updated Fields &nbsp; *"  field="updated_field" width="160px">
             <<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${updatedFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable> 
			</kendo:grid-column>
              
             
             
                 <kendo:grid-column title="Current Values &nbsp; *" filterable="false" field="owner_current" width="160px">
                  <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function DepartmentNameFilter(element) {
								element.kendoAutoComplete({
									dataSource: {
										transport: {
											read: "${currentFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	    
                 
                 
                 </kendo:grid-column>
                 <kendo:grid-column title="Previous Values &nbsp; *" filterable="false" field="owner_previous" width="160px">
                 
                  <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function DepartmentNameFilter(element) {
								element.kendoAutoComplete({
									dataSource: {
										transport: {
											read: "${previousFilter}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	    
                 
                 </kendo:grid-column>
             <kendo:grid-column title="Updated Date &nbsp; *"  field="lastUpdatedDate" width="160px" /> 
               <kendo:grid-column title="Updated By &nbsp; *"  field="lastUpdatedBy" width="160px">
               
                  <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function DepartmentNameFilter(element) {
								element.kendoAutoComplete({
									dataSource: {
										transport: {
											read: "${updatedFilter1}"
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
               
                <kendo:dataSource-transport-read url="${readUrl1}" dataType="json" type="GET" contentType="application/json"/>
              
            </kendo:dataSource-transport>
            <kendo:dataSource-schema parse="parse">
                <kendo:dataSource-schema-model id="id">
                 <kendo:dataSource-schema-model-fields >
                        <kendo:dataSource-schema-model-field name="id" editable="false">
                        </kendo:dataSource-schema-model-field>
                    
                   
                        <kendo:dataSource-schema-model-field name="ownerName" type="string"/>
                        	
                        
                        <kendo:dataSource-schema-model-field name="updated_field" type="string">
                        </kendo:dataSource-schema-model-field>
                         
                         <kendo:dataSource-schema-model-field name="owner_previous"   type="string"/>
                      
                            <kendo:dataSource-schema-model-field name="owner_current"   type="string"/>
                            
                               <kendo:dataSource-schema-model-field name="lastUpdatedDate"   type="date"/> 
                        
                         <kendo:dataSource-schema-model-field name="lastUpdatedBy"   type="string"/>
                    </kendo:dataSource-schema-model-fields>
                </kendo:dataSource-schema-model>
            </kendo:dataSource-schema>
        </kendo:dataSource>
    </kendo:grid>
    <div id="alertsBox" title="Alert"></div>   
    <script>
    $("#grid").on("click", ".k-grid-Clear_Filter", function(){
 	    //custom actions
 	    $("form.k-filter-menu button[type='reset']").slice().trigger("click");
 		var grid = $("#grid").data("kendoGrid");
 		grid.dataSource.read();
 		grid.refresh();
 	});
    
    
    function parse (response) {
        $.each(response, function (idx, elem) {   
        	   if(elem.lastUpdatedDate=== null){
        		   elem.lastUpdatedDate = "";
        	   }else{
        		   elem.lastUpdatedDate = kendo.parseDate(new Date(elem.lastUpdatedDate),'dd/MM/yyyy HH:mm');
        	   }
           });
           
           return response;
    }
</script>
 
