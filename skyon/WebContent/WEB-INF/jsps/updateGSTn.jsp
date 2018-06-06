<%@include file="/common/taglibs.jsp"%>

<c:url value="/camBills/readGstnTable" var="readUrl" />
<c:url value="/camBills/createAndUpdateGSTinNumber/1" var="createUrl" />
<c:url value="/camBills/createAndUpdateGSTinNumber/2" var="updateUrl" />

<c:url value="/camBills/getPropertyNumbers" var="getPropertyNamesUrl" />

<kendo:grid name="grid" pageable="true" resizable="true" sortable="true" reorderable="true" selectable="false" scrollable="true"
	edit="paymentAdjustmentEvent" filterable="false" groupable="true">
	<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10"
		input="true" numeric="true" refresh="true" info="true"
		previousNext="true">
		<kendo:grid-pageable-messages itemsPerPage="Status items per page"
			empty="No status item to display"
			refresh="Refresh all the status items"
			display="{0} - {1} of {2} New Status Items"
			first="Go to the first page of status items"
			last="Go to the last page of status items"
			next="Go to the next page of status items"
			previous="Go to the previous page of status items" />
	</kendo:grid-pageable>

	<kendo:grid-filterable extra="false">
		<kendo:grid-filterable-operators>
			<kendo:grid-filterable-operators-string eq="Is equal to"
				contains="Contains" />
			<kendo:grid-filterable-operators-date gt="Is after" lt="Is before" />
		</kendo:grid-filterable-operators>
	</kendo:grid-filterable>
	
	<kendo:grid-editable mode="popup"/>
		<kendo:grid-toolbar>
		      <kendo:grid-toolbarItem name="create" text="SetGstNumber" />
		      
	    </kendo:grid-toolbar> 
	
	<kendo:grid-columns>
		
		<kendo:grid-column title="RowId" field="rowid" width="100px" filterable="true" hidden="true" sortable="false" />
		
		<kendo:grid-column title="Account ID" field="accountid" width="100px" filterable="false" hidden="true" />
		
		<kendo:grid-column title="Property ID" field="propertyId" width="70px" filterable="false" hidden="true" />
		
		<kendo:grid-column title="Person ID" field="personid" width="100px" filterable="false" hidden="true" />
		
		<kendo:grid-column title="Account No" field="accountno" width="70px" filterable="false"  />
		
		<kendo:grid-column title="Property No" field="property_No" width="150px" filterable="true" editor="propertyEditor"/>
		
		<kendo:grid-column title="Person Name" field="personname" width="150px" filterable="true" />
		
		<kendo:grid-column title="GSTIN No" field="gstin" width="150px" filterable="false" />
		
		<kendo:grid-column title="&nbsp;" width="160px">
		<kendo:grid-column-command>
			<kendo:grid-column-commandItem name="edit" />
			<%-- <kendo:grid-column-commandItem name="destroy" /> --%>
		</kendo:grid-column-command>
	</kendo:grid-column>

	</kendo:grid-columns>

	<kendo:dataSource requestEnd="onRequestEnd" requestStart="onRequestStart">
	
	<kendo:dataSource-transport>
    		<kendo:dataSource-transport-create url="${createUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json" />
			 <kendo:dataSource-transport-update url="${updateUrl}" dataType="json" type="GET" contentType="application/json" />
	</kendo:dataSource-transport>

		<kendo:dataSource-schema>
			<kendo:dataSource-schema-model id="rowid">
				<kendo:dataSource-schema-model-fields>
					<kendo:dataSource-schema-model-field name="rowid" type="number" />
					
					<kendo:dataSource-schema-model-field name="accountid" type="string" />
					
					 <kendo:dataSource-schema-model-field name="propertyId" type="String" />
					
					<kendo:dataSource-schema-model-field name="personid" type="String" />
				    
					<kendo:dataSource-schema-model-field name="accountno" type="string" />
					
					<kendo:dataSource-schema-model-field name="property_No" type="String" />
					
					<kendo:dataSource-schema-model-field name="personname" type="String" />
					
					<kendo:dataSource-schema-model-field name="gstin" type="String" />
					
				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>

	</kendo:dataSource>

</kendo:grid>
<div id="alertsBox" title="Alert"></div>

<script type="text/javascript">
function propertyEditor(container, options) 
{
$('<input name="Property No" id="property_No" data-text-field="property_No" data-value-field="property_No" data-bind="value:' + options.field + '" required="true"/>')
.appendTo(container).kendoComboBox({
	autoBind : false,			
	dataSource : {
		transport : {		
			read :  "${getPropertyNamesUrl}"
		}
	},
	change : function (e) {
        if (this.value() && this.selectedIndex == -1) {                    
			alert("P doesn't exist!");
            $("#property_No").data("kendoComboBox").value("");
    	}

    }  
});

$('<span class="k-invalid-msg" data-for="Property No"></span>').appendTo(container);
}


function paymentAdjustmentEvent(e)
{

	$('div[data-container-for="rowid"]').hide();
	$('label[for="rowid"]').closest('.k-edit-label').hide();

	$('div[data-container-for="accountid"]').hide();
	$('label[for="accountid"]').closest('.k-edit-label').hide(); 
	
	$('div[data-container-for="propertyId"]').hide();
	$('label[for="propertyId"]').closest('.k-edit-label').hide();

	$('div[data-container-for="personid"]').hide();
	$('label[for="personid"]').closest('.k-edit-label').hide(); 
	
	$('div[data-container-for="accountno"]').hide();
	$('label[for="accountno"]').closest('.k-edit-label').hide();

	$('div[data-container-for="personname"]').hide();
	$('label[for="personname"]').closest('.k-edit-label').hide(); 
	/************************* Button Alerts *************************/
	if (e.model.isNew()) 
    {
		$(".k-window-title").text("Add New GSTN Details");
		$(".k-grid-update").text("Save");
    }
	else{
		$(".k-window-title").text("Edit GSTN Details");
	}
}

function onRequestStart(e){
	
	$('.k-grid-update').hide();
    $('.k-edit-buttons')
            .append(
                    '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
    $('.k-grid-cancel').hide();	
}

function onRequestEnd(e) { 
	
	if (typeof e.response != 'undefined') {	
			if(e.response.status == "AlreadyExists")
			{
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Property No Already Exists");
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
				
			} else if (e.type == "create") {
				$("#alertsBox").html("");
				$("#alertsBox").html(
					"Record Saved Successfully");
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
			
			} else if (e.type == "update") {
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Record Updated Successfully");
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
