<%@include file="/common/taglibs.jsp"%>

<c:url value="/camchargerSettings/camchargerSettingsCreate" var="createUrl" />
<c:url value="/camchargerSettings/camchargerSettingsRead" var="readUrl" />

<kendo:grid name="grid" remove="camchargerSettingsDeleteEvent" pageable="true" resizable="true" edit="camchargerSettingsEvent" sortable="true" reorderable="true" selectable="true" scrollable="true" filterable="false" groupable="true">
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
		      <kendo:grid-toolbarItem name="create" text="Add Details" />
	    </kendo:grid-toolbar>
	<kendo:grid-columns>
	    <kendo:grid-column title="camchargerId" field="camId" width="70px" hidden="true" filterable="false" sortable="false" />
	    <kendo:grid-column title="Charges&nbsp;Based&nbsp;On&nbsp;*" field="camChargesBasedOn" editor="dropDownChecksEditor" width="70px" filterable="false"/>
	    <kendo:grid-column title="Rate Per Sqft" field="rateForSqft" hidden="true" width="70px" filterable="false"/>
	    <%-- <kendo:grid-column title="" field="dummy1" width="100px" editor="andOrEditor" hidden="true"/> --%>
	    <kendo:grid-column title="Rate&nbsp;Per&nbsp;Apartment" field="rateForFlat" hidden="true" width="70px" filterable="false"/>
	</kendo:grid-columns>

	<kendo:dataSource pageSize="20" requestEnd="onRequestEnd">
		<kendo:dataSource-transport>
			<kendo:dataSource-transport-create url="${createUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json" />
		</kendo:dataSource-transport>

		<kendo:dataSource-schema>
			<kendo:dataSource-schema-model id="camId">
				<kendo:dataSource-schema-model-fields>
					<kendo:dataSource-schema-model-field name="camId" type="number"/>
					<kendo:dataSource-schema-model-field name="camChargesBasedOn" type="string"/>
					<kendo:dataSource-schema-model-field name="rateForFlat" type="number" defaultValue="">
					<kendo:dataSource-schema-model-field-validation min="0" />
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="rateForSqft" type="number" defaultValue="">
					<kendo:dataSource-schema-model-field-validation min="0" />
					</kendo:dataSource-schema-model-field>
				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>

	</kendo:dataSource>

</kendo:grid>
<div id="alertsBox" title="Alert"></div>
<script>

function dropDownChecksEditor(container, options) {
	
	var booleanData = [ {
	     text : 'Select',
		 value : ""
		    },{
			   text : 'Flat',
			   value : "Flat"
			  },{
			   text : 'PSF',
			   value : "PSF"
				},];
			  $('<input name="Cam Charges Based On" required="true"/>').attr('data-bind', 'value:camChargesBasedOn').appendTo(container).kendoDropDownList
			  ({
				  
				    defaultValue : false,
					//select : selectType,
					sortable : true,
				  
				 dataSource : booleanData,
				 dataTextField : 'text',
				 dataValueField : 'value',
			  });
			  $('<span class="k-invalid-msg" data-for="Cam Charges Based On"></span>').appendTo(container);
	   
}

function selectType(e){
	var dataItem = this.dataItem(e.item.index());
	if(dataItem.text=="Actual"){
		 $('div[data-container-for="rateForFlat"]').hide();
		 $('label[for="rateForFlat"]').closest('.k-edit-label').hide();
		 
		 $('div[data-container-for="rateForSqft"]').hide();
		 $('label[for="rateForSqft"]').closest('.k-edit-label').hide();
		 
		 $('div[data-container-for="dummy1"]').hide();
		 $('label[for="dummy1"]').closest('.k-edit-label').hide();
		 
	}else{
		$('div[data-container-for="rateForFlat"]').show();
		 $('label[for="rateForFlat"]').closest('.k-edit-label').show();
		 
		 $('div[data-container-for="rateForSqft"]').show();
		 $('label[for="rateForSqft"]').closest('.k-edit-label').show();
		
		 $('div[data-container-for="dummy1"]').show();
		 $('label[for="dummy1"]').closest('.k-edit-label').hide();
		 
	}
}

function camchargerSettingsDeleteEvent(){
	//securityCheckForActions("./Masters/MeterStatus/destroyButton");
	var conf = confirm("Are u sure want to delete this item?");
	 if(!conf){
	  $('#grid').data().kendoGrid.dataSource.read();
	   throw new Error('deletion aborted');
	 }
}

function andOrEditor(container, options) {
	  $(
	    '<input type="radio" name=' + options.field + ' value="AND" checked="true" /> AND &nbsp;&nbsp;&nbsp; <input type="radio" name=' + options.field + ' value="OR"/> OR &nbsp;&nbsp;&nbsp;<br>')
	    .appendTo(container);
	 }

var setApCode="";		
function camchargerSettingsEvent(e)
{
	/***************************  to remove the id from pop up  **********************/
	$('div[data-container-for="camId"]').remove();
	$('label[for="camId"]').closest('.k-edit-label').remove();
	
	
	 $('div[data-container-for="rateForFlat"]').hide();
	 $('label[for="rateForFlat"]').closest('.k-edit-label').hide();
	 
	 $('div[data-container-for="dummy1"]').hide();
	 $('label[for="dummy1"]').closest('.k-edit-label').hide();
	 
	 $('div[data-container-for="rateForSqft"]').hide();
	 $('label[for="rateForSqft"]').closest('.k-edit-label').hide();
		
	/************************* Button Alerts *************************/
	if (e.model.isNew()) 
    {
		securityCheckForActions("./CAM/CAMCharges/CreateButton");
		setApCode = $('input[name="camId"]').val();
		$(".k-window-title").text("Cam Charges Setting Details");
		$(".k-grid-update").text("Save");		
    }
	else{
		//securityCheckForActions("./Masters/MeterStatus/updateButton");
		$(".k-window-title").text("Edit Cam Charges Setting Details");
	}
}
		
	function onRequestEnd(e) {
		if (typeof e.response != 'undefined') {
			if (e.response.status == "FAIL") {
				errorInfo = "";
				for (var i = 0; i < e.response.result.length; i++) {
					errorInfo += (i + 1) + ". "
							+ e.response.result[i].defaultMessage + "<br>";
				}

				if (e.type == "create") {
					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Creating the meter status\n\n : "
									+ errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
				}
				var grid = $("#grid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			} 
			else if (e.type == "create") {
				$("#alertsBox").html("");
				$("#alertsBox").html("camCharges setting details created successfully.<br>Please change the cam rates in transaction master");
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
			} else if (e.type == "destroy") {
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Status name is deleted successfully");
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
						"Status name is updated successfully");
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
