<%@include file="/common/taglibs.jsp"%>

<c:url value="/meterStatus/meterStatusCreate" var="createUrl" />
<c:url value="/meterStatus/meterStatusRead" var="readUrl" />
<c:url value="/meterStatus/meterStatusDestroy" var="destroyUrl" />
<c:url value="/meterStatus/meterStatusUpdate" var="updateUrl" />

<kendo:grid name="grid" remove="meterStatusDeleteEvent" pageable="true" resizable="true" edit="meterStatusEvent" sortable="true" reorderable="true" selectable="true" scrollable="true" filterable="false" groupable="true">

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
		      <kendo:grid-toolbarItem name="create" text="Create Meter Status" />
	    </kendo:grid-toolbar>
	<kendo:grid-columns>
	    
	    <kendo:grid-column title="Status Id" field="meterStatusId" width="70px" hidden="true" filterable="false" sortable="false" />
	    
	    <kendo:grid-column title="Status&nbsp;Name&nbsp;*" field="meterStatus" width="70px" filterable="false">
	    </kendo:grid-column>

		<kendo:grid-column title="&nbsp;" width="160px">
			<kendo:grid-column-command>
			    <%-- <kendo:grid-column-commandItem name="edit"/> --%>
				<kendo:grid-column-commandItem name="destroy" />
			</kendo:grid-column-command>
		</kendo:grid-column>
	</kendo:grid-columns>

	<kendo:dataSource pageSize="20" requestEnd="onRequestEnd" requestStart="onRequestStart">
		<kendo:dataSource-transport>
			<kendo:dataSource-transport-create url="${createUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json" />
			<kendo:dataSource-transport-update url="${updateUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-destroy url="${destroyUrl}/" dataType="json" type="GET" contentType="application/json" />
		</kendo:dataSource-transport>

		<kendo:dataSource-schema parse="parseMeterStatus">
			<kendo:dataSource-schema-model id="meterStatusId">
				<kendo:dataSource-schema-model-fields>
					<kendo:dataSource-schema-model-field name="meterStatusId" type="number"/>
					
					<kendo:dataSource-schema-model-field name="meterStatus" type="string">
					</kendo:dataSource-schema-model-field>

				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>

	</kendo:dataSource>

</kendo:grid>
<div id="alertsBox" title="Alert"></div>
<script>

var statusNameArray = [];

function parseMeterStatus(response) {   
    var data = response; 
    statusNameArray = [];
	 for (var idx = 0, len = data.length; idx < len; idx ++) {
		var res1 = (data[idx].meterStatus);
		statusNameArray.push(res1);
	 }  
	 return response;
} 

function meterStatusDeleteEvent(){
	securityCheckForActions("./Masters/MeterStatus/destroyButton");
	var conf = confirm("Are u sure want to delete this status name?");
	 if(!conf){
	  $('#grid').data().kendoGrid.dataSource.read();
	   throw new Error('deletion aborted');
	 }
}


var setApCode="";		
function meterStatusEvent(e)
{
	/***************************  to remove the id from pop up  **********************/
	$('div[data-container-for="meterStatusId"]').remove();
	$('label[for="meterStatusId"]').closest('.k-edit-label').remove();
		
	/************************* Button Alerts *************************/
	if (e.model.isNew()) 
    {
		securityCheckForActions("./Masters/MeterStatus/createButton");
		setApCode = $('input[name="meterStatusId"]').val();
		$(".k-window-title").text("Create Status Name");
		$(".k-grid-update").text("Save");		
    }
	else{
		securityCheckForActions("./Masters/MeterStatus/updateButton");
		$(".k-window-title").text("Edit Status Name");
	}
}
	
	function onRequestStart(e){
		
			/* var gridStoreGoodsReturn = $("#grid").data("kendoGrid");
			gridStoreGoodsReturn.cancelRow(); */		
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();
		
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
				$("#alertsBox").html(
						"Status name is created successfully");
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
	
	//register custom validation rules
	(function ($, kendo) 
		{   	  
	    $.extend(true, kendo.ui.validator, 
	    {
	         rules: 
	         { // custom rules
	        	 
	         meterStatusRequired : function(input, params){
               if (input.attr("name") == "meterStatus")
               {
                return $.trim(input.val()) !== "";
               }
               return true;
              },
              meterStatusLengthValidation : function (input, params) 
	                    {         
	                        if (input.filter("[name='meterStatus']").length && input.val() != "") 
	                        {
	                       	 return /^[a-zA-Z ]{1,45}$/.test(input.val());
	                        }        
	                        return true;
	                    },
	             statusNameUniquevalidation : function(input, params){
									if (input.filter("[name='meterStatus']").length && input.val()) 
									{
										var flag = true;
										$.each(statusNameArray, function(idx1, elem1) {
											if(elem1.toLowerCase() == input.val().toLowerCase())
											{
												flag = false;
											}	
										});
										return flag;
								}
								return true;
							},
	         },
	         messages: 
	         {
				//custom rules messages
				meterStatusRequired : "Status name is required",
				meterStatusLengthValidation : "Status name can contain alphabets,spaces and max 45 characters",
				statusNameUniquevalidation : "Status name is already exist"
	     	 }
	    });
	    
	})(jQuery, kendo);
	  //End Of Validation
</script>
