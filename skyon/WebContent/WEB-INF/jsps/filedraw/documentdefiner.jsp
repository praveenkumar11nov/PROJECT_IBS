<%@include file="/common/taglibs.jsp"%>

	<c:url value="/documentdefiner/read" var="readDdUrl" />
	<c:url value="/documentdefiner/cu?action=create" var="createDdUrl" />
	<c:url value="/documentdefiner/cu?action=update" var="updateDdUrl" />
	
	<c:url value="/documentdefiner/getddtype" var="readDdTypeUrl" />
	<c:url value="/documentdefiner/getddformat" var="readDdFormatUrl" />
	<c:url value="/person/getYesNo" var="readOptional" />
 	<kendo:grid name="grid" pageable="true" resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true" filterable="true" groupable="true" >
 	<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" ></kendo:grid-pageable>
								<kendo:grid-filterable extra="false">
							 		<kendo:grid-filterable-operators>
							  			<kendo:grid-filterable-operators-string eq="Is equal to"/>
							 		</kendo:grid-filterable-operators>
								</kendo:grid-filterable>
								<kendo:grid-editable mode="popup" />
						        <kendo:grid-toolbar >
						            <kendo:grid-toolbarItem name="create" text="Add DD" />
						            <kendo:grid-toolbarItem text="Clear_Filter"/>
						        </kendo:grid-toolbar>
        						<kendo:grid-columns>
        								<kendo:grid-column title="&nbsp;" width="100px" >
							             	<kendo:grid-column-command>
							            		<kendo:grid-column-commandItem name="edit"/>
							            	</kendo:grid-column-command>
							             </kendo:grid-column>
									     <%-- <kendo:grid-column title="Owner Property ID" field="ddId" filterable="false" width="120px"/> --%>
									     <%-- <kendo:grid-column title="Owner Id" field="owner" ></kendo:grid-column> --%>
									     <kendo:grid-column title="DD Type" editor="ddTypeEditor"  field="ddType"></kendo:grid-column>
									     <kendo:grid-column title="Document Format" editor="ddFormatEditor" field="ddFormat"></kendo:grid-column>
        								 <kendo:grid-column title="Document Name" field="ddName"></kendo:grid-column>
        								 <kendo:grid-column title="Document Description" field="ddDescription"></kendo:grid-column>
        								 <kendo:grid-column title="Optional" editor="optionalEditor" field="ddOptional"></kendo:grid-column>
        								 <kendo:grid-column title="Start Date" format= "{0:dd/MM/yyyy}" field="ddStartDate"></kendo:grid-column>
        								 <kendo:grid-column title="End Date" format= "{0:dd/MM/yyyy}" field="ddEndDate"></kendo:grid-column>
        								 <kendo:grid-column title="RV Complaince" field="ddRvComplaince"></kendo:grid-column>
        								 <kendo:grid-column title="Status" field="status"></kendo:grid-column>
        								 
        								 <kendo:grid-column title="&nbsp;" width="100px" >
							            	<kendo:grid-column-command>
							            		<kendo:grid-column-commandItem name="edit"/>
							            	</kendo:grid-column-command>
							            </kendo:grid-column>
        						</kendo:grid-columns>
        						 <kendo:dataSource >
						            <kendo:dataSource-transport>
						                <kendo:dataSource-transport-read url="${readDdUrl}" dataType="json" type="POST" contentType="application/json"/>
						                <kendo:dataSource-transport-create url="${createDdUrl}" dataType="json" type="POST" contentType="application/json" />
						                <kendo:dataSource-transport-update url="${updateDdUrl}" dataType="json" type="POST" contentType="application/json" />
						               <kendo:dataSource-transport-parameterMap>
						                	<script>
							                	function parameterMap(options,type) 
							                	{
							                		return JSON.stringify(options);	                		
							                	}
						                	</script>
						                 </kendo:dataSource-transport-parameterMap>
						            </kendo:dataSource-transport>
						            <kendo:dataSource-schema>
						                <kendo:dataSource-schema-model id="ddId">
						                    <kendo:dataSource-schema-model-fields>
							                    <kendo:dataSource-schema-model-field name="ddId" editable="false" />
							                    <kendo:dataSource-schema-model-field name="ddType" >
							                        	<kendo:dataSource-schema-model-field-validation required = "true"/>
							                     </kendo:dataSource-schema-model-field>
						                    	<kendo:dataSource-schema-model-field name="ddName" type="string">
						                    		<kendo:dataSource-schema-model-field-validation required = "true"/>
						                    	</kendo:dataSource-schema-model-field>   
						                    	<kendo:dataSource-schema-model-field name="ddFormat" type="string">
						                    			<kendo:dataSource-schema-model-field-validation required = "true"/>
						                    	</kendo:dataSource-schema-model-field>
						                    	<kendo:dataSource-schema-model-field name="ddDescription" type="string">
						                    			<kendo:dataSource-schema-model-field-validation required = "true"/>
						                    	</kendo:dataSource-schema-model-field>
						                    	<kendo:dataSource-schema-model-field name="ddOptional" type="string">
						                    			<kendo:dataSource-schema-model-field-validation required = "true"/>
						                    	</kendo:dataSource-schema-model-field>  
						                    	<kendo:dataSource-schema-model-field name="ddStartDate" type="date">
						                    			<kendo:dataSource-schema-model-field-validation required = "true"/>
						                    	</kendo:dataSource-schema-model-field>
						                    	
						                    	<kendo:dataSource-schema-model-field name="ddEndDate" type="date">
						                    			<kendo:dataSource-schema-model-field-validation required = "true"/>
						                    	</kendo:dataSource-schema-model-field>
						                    	<kendo:dataSource-schema-model-field name="ddRvComplaince" type="string">
						                    			<kendo:dataSource-schema-model-field-validation required = "true"/>
						                    	</kendo:dataSource-schema-model-field>
						                    	<kendo:dataSource-schema-model-field name="status" type="string" defaultValue="Inactive" editable="false"></kendo:dataSource-schema-model-field>      
						                    </kendo:dataSource-schema-model-fields>
						                 </kendo:dataSource-schema-model>
						             </kendo:dataSource-schema>
						          </kendo:dataSource>
 
 
 </kendo:grid>
<script>
function ddTypeEditor(container, options) {
	$(
			'<input data-text-field="name" data-value-field="value" data-bind="value:' + options.field + '"/>')
			.appendTo(container).kendoDropDownList({	
				optionLabel : {
					name : "Select",
					value : "",
				},
				defaultValue : false,
				sortable : true,
				dataSource : {
					transport : {
						read : "${readDdTypeUrl}"
					}
				}

			});
}

function optionalEditor(container, options) {
	$(
			'<input data-text-field="name" data-value-field="value" data-bind="value:' + options.field + '"/>')
			.appendTo(container).kendoDropDownList({	
				optionLabel : {
					name : "Select",
					value : "",
				},
				defaultValue : false,
				sortable : true,
				dataSource : {
					transport : {
						read : "${readOptional}"
					}
				}

			});
}

function ddFormatEditor(container, options) {
	$(
			'<input data-text-field="name" data-value-field="value" data-bind="value:' + options.field + '"/>')
			.appendTo(container).kendoDropDownList({	
				optionLabel : {
					name : "Select",
					value : "",
				},
				defaultValue : false,
				sortable : true,
				dataSource : {
					transport : {
						read : "${readDdFormatUrl}"
					}
				}

			});
}
</script>
 