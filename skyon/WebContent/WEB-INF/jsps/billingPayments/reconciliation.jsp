<%@include file="/common/taglibs.jsp"%>

<!-- Create Read Update Delete URL's Of Reconciliation -->
<c:url value="/reconciliation/reconciliationRead" var="reconciliationReadUrl" />
 
<kendo:grid name="grid" resizable="true" pageable="true" selectable="true" sortable="true" scrollable="true" groupable="true">
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" refresh="true" info="true" previousNext="true">
			<kendo:grid-pageable-messages itemsPerPage="Reconciliations per page" empty="No reconciliations to display" refresh="Refresh all the Reconciliations" 
			display="{0} - {1} of {2} New Reconciliations" first="Go to the first page of Reconciliations" last="Go to the last page of Reconciliations" next="Go to the next page of Reconciliations"
			previous="Go to the previous page of Reconciliations"/>
		</kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
			<kendo:grid-filterable-operators>
				<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains"/>
				<kendo:grid-filterable-operators-date eq="Is equal to" gt="Is after" lt="Is before"/>
			</kendo:grid-filterable-operators>

		</kendo:grid-filterable>
		<kendo:grid-editable mode="popup" />
		<kendo:grid-toolbar>
			<%-- <kendo:grid-toolbarItem name="create" text="Add New Reconciliation" /> --%>
			<kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterHelpTopic()><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>"/>
		</kendo:grid-toolbar>
		
		<kendo:grid-columns>
			
			<kendo:grid-column title="reconciliationId" field="reconciliationId" width="110px" hidden="true"/>
			
			<kendo:grid-column title="Reconciliation&nbsp;Date" field="reconciliationDate" format="{0:dd/MM/yyyy}" width="140px" filterable="true">
			</kendo:grid-column>
			
			<kendo:grid-column title="Total&nbsp;Receipts" field="totalReceipts" filterable="true" width="130px">
			</kendo:grid-column>
			
			<kendo:grid-column title="Collection&nbsp;Amount" field="collectionAmount" width="150px" filterable="true">
			</kendo:grid-column>
	    		
	    	<kendo:grid-column title="Remittance&nbsp;Amount" field="remittanceAmount" width="130px" filterable="true">
			</kendo:grid-column>
			
			<kendo:grid-column title="Difference&nbsp;Amount" field="diffAmount" filterable="true" width="150px">
    		</kendo:grid-column>
			
		</kendo:grid-columns>
		
		<kendo:dataSource pageSize="20" requestEnd="onRequestEnd">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-read url="${reconciliationReadUrl}" dataType="json" type="POST" contentType="application/json" />
			</kendo:dataSource-transport>
			
			<kendo:dataSource-schema>
				<kendo:dataSource-schema-model id="reconciliationId">
					<kendo:dataSource-schema-model-fields>

						<kendo:dataSource-schema-model-field name="reconciliationId" type="number">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="reconciliationDate" type="date">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="totalReceipts" type="number">
						</kendo:dataSource-schema-model-field>

						<kendo:dataSource-schema-model-field name="collectionAmount" type="number">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="remittanceAmount" type="number">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="diffAmount" type="number">
						</kendo:dataSource-schema-model-field>
						
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
			
		</kendo:dataSource>
	</kendo:grid> 
	
<div id="alertsBox" title="Alert"></div>

<script>

function clearFilterHelpTopic()
{
   $("form.k-filter-menu button[type='reset']").slice().trigger("click");
   var gridStoreIssue = $("#helpTopicGrid").data("kendoGrid");
   gridStoreIssue.dataSource.read();
   gridStoreIssue.refresh();
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

</script>
