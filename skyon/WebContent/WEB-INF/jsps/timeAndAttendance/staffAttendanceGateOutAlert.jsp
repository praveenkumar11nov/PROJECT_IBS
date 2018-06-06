<%@include file="/common/taglibs.jsp"%>

	<c:url value="/staffAttendanceGateOutAlert/read" var="readUrl" />
	<c:url value="/staffAttendanceGateOutAlert/create" var="createUrl" />
	<c:url value="/staffAttendanceGateOutAlert/update" var="updateUrl" />
	<c:url value="/staffAttendanceGateOutAlert/delete" var="destroyUrl" />
	
	<c:url value="/staffAttendanceGateOutAlert/getStaffNames" var="getGateOutStaffNameList" />

		<kendo:grid name="grid" pageable="true"
		resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true"
		filterable="true" groupable="true" >
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" ></kendo:grid-pageable>
		 <kendo:grid-filterable extra="false">
		 <kendo:grid-filterable-operators>
		  	<kendo:grid-filterable-operators-string eq="Is equal to"/>
		 </kendo:grid-filterable-operators>
			
		</kendo:grid-filterable> 
		 <kendo:grid-editable mode="popup"
			confirmation="Are you sure you want to remove this GateOut Alert?" />
		 <kendo:grid-toolbar>
			<%-- <kendo:grid-toolbarItem name="create" text="Add GateOut Alert" /> --%>
			<kendo:grid-toolbarItem text="Clear_Filter"/>
		</kendo:grid-toolbar>

		<kendo:grid-columns>
		<kendo:grid-column title="Alert Date " field="sagaDt" format= "{0:dd/MM/yyyy}"
				width="100px" filterable="true" />
		<kendo:grid-column title="Staff Name" field="firstName" editor="staffNameDropDownEditor" width="100px">
				<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function staffNameFilter(element) {
								element.kendoAutoComplete({
									dataSource: {
										transport: {
											read: "${getGateOutStaffNameList}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	    
		</kendo:grid-column>
		
			
			 <kendo:grid-column title="Message" field="message" filterable="false" width="100px" sortable="false" >
			</kendo:grid-column> 
			<%-- <kendo:grid-column title="Mobile Number" field="sagaMobileNo"  width="130px" filterable="false" />
			<kendo:grid-column title="Email Id" field="sagaEmail" filterable="false" width="130px" /> --%>
		</kendo:grid-columns>
		<kendo:dataSource requestEnd="onRequestEnd">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-read url="${readUrl}" dataType="json"
					type="POST" contentType="application/json" />
				<kendo:dataSource-transport-create url="${createUrl}"
					dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-update url="${updateUrl}"
					dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-destroy url="${destroyUrl}"
					dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-parameterMap>
					<script>
						function parameterMap(options, type) {
							return JSON.stringify(options);
						}
					</script>
				</kendo:dataSource-transport-parameterMap>
			</kendo:dataSource-transport>
			 <kendo:dataSource-schema>
				<kendo:dataSource-schema-model id="sagaId">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="sagaId" editable="false" />
						<kendo:dataSource-schema-model-field name="firstName">
						<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="message">
						<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="sagaDt" type="date">
						<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="sagaMobileNo" type="tel" >
						 <kendo:dataSource-schema-model-field-validation required="true"
								pattern="\d{10}" /> 
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="sagaEmail" type="email">
							 <kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>

	</kendo:grid>
	
	<script>
	
	$("#grid").on("click", ".k-grid-Clear_Filter", function(){
	    //custom actions
	    $("form.k-filter-menu button[type='reset']").slice().trigger("click");
		var grid = $("#grid").data("kendoGrid");
		grid.dataSource.read();
		grid.refresh();
	});
	
		$("#grid").on("click", ".k-grid-add", function() {
			$(".k-window-title").text("Add GateOut Alert");
			$(".k-grid-update").text("Save");

		});
		
		function edit(e) {
			$(".k-window-title").text("Edit GateOut Alert");
		}
		
		function dateEditor(container, options) {
		    $('<input data-text-field="' + options.field + '" data-value-field="' + options.field + '" data-bind="value:' + 
		    		options.field + '" data-format="' + ["dd/MM/yyyy"] + '"/>')
		            .appendTo(container)
		            .kendoDatePicker({
		            	placeholder : "Please select to time"
		            });
		}
		
		 function staffNameDropDownEditor(container, options) {
				$('<input data-text-field="firstName" data-value-field="personId" id="personId" data-bind="value:' + options.field + '"/>')
		             .appendTo(container)
			             .kendoComboBox({
		            	placeholder:"Select Staff Name",
		            	
		                 dataSource: {  
		                     transport:{
		                         read: "${getStaffNameUrl}"
		                     }
		                 }
		             });
		 }
		 
		 
		function onRequestEnd(e) {
				if (typeof e.response != 'undefined'){
					
					  if (e.type == "create") {

							$("#alertsBox").html("");
							$("#alertsBox").html("Staff record created successfully");
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
