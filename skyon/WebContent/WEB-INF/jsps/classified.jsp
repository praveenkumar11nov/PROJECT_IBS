<%@include file="/common/taglibs.jsp"%>
<c:url value="/classified/createUrl"  var="createUrl"></c:url>
<c:url value="/classified/readUrl"  var="readUrl"></c:url>
<c:url value="/amrSetting/getAllBlockUrl" var="getAllBlockUrl" />
<c:url value="/classified/getAllMobileNumbers" var="getAllMobileNumbers"></c:url>
<c:url value="/classified/getEmailIdforFilter" var="getEmailIdforFilter"></c:url>
<c:url value="/amrSetting/getAllPropertyUrl" var="getAllPropertyUrl" />
<c:url value="/openNewTickets/getPersonListBasedOnPropertyNumbers" var="personNamesAutoBasedOnPersonTypeUrl"></c:url>


<kendo:grid name="grid"  pageable="true" resizable="true" change="onChangeBillsList" edit="prepaidMeterSettingEvent" sortable="true" reorderable="true" selectable="true" scrollable="true" filterable="false" groupable="true">
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
		      <kendo:grid-toolbarItem name="create" text="Add Classified" />
		      <kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilter()>Clear Filter</a>"/>
	         <%--  <kendo:grid-toolbarItem name="ConsumerTemplatesDetailsExport" text="Export To Excel" />
	          <kendo:grid-toolbarItem name="uploadConsumerDetails" text="Upload Batch File" /> --%>
		     <%--  <kendo:grid-toolbarItem name="activateAll" text="Activate All" />
		       <kendo:grid-toolbarItem name="ConsumerTemplatesDetailsExport" text="Export To Excel" />
		       <kendo:grid-toolbarItem name="amrPdfTemplatesDetailsExport" text="Export To PDF" />
		     <kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterAMRSettings()><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>"/> --%>
	    </kendo:grid-toolbar>
	<kendo:grid-columns>
	    <kendo:grid-column title="Display" field="select"
			template="<input type='checkbox' value='checked' onclick='fieldChange()'   id='select'/>"
			width="70px" >
		</kendo:grid-column>
	    <kendo:grid-column title="PrePaidMeterId" field="prePaidId" width="70px" hidden="true" filterable="false" sortable="false" />
	    
	    <kendo:grid-column title="Tower&nbsp;*" field="blockId" width="70px" filterable="false" hidden="true"/>
	    
	     <kendo:grid-column title="Tower&nbsp;*" field="blocksName" width="70px" filterable="false" editor="towerNameEditor" hidden="true">
	     
	     </kendo:grid-column>
	    
	    <kendo:grid-column title="Property&nbsp;*" field="propertyId" width="70px" filterable="false" hidden="true"/>
	    
	    <kendo:grid-column title="Property&nbsp;No*" field="propertyName" width="70px" filterable="true" editor="propertyEditor">
	   
	     </kendo:grid-column>
	     
	       <kendo:grid-column title="person&nbsp;*" field="personId" width="70px" filterable="false" hidden="true"/>
	     <kendo:grid-column title="Person Name" field="personName" width="70px" filterable="true" editor="PersonNames"/>
	     
	      <kendo:grid-column title="Mobile&nbsp;Number" field="mobile_No" width="70px" filterable="false" editor="mobileNumberEditor" >
		   
		    </kendo:grid-column>
	     
	       <kendo:grid-column title="Email&nbsp;ID" field="emailId" width="70px" filterable="false" editor="emailIdEditor">
		
		    </kendo:grid-column>
		    
		     <kendo:grid-column title="Information" field="information" width="70px" filterable="false" editor="informationEditor">
		   
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

	<kendo:dataSource pageSize="20" requestEnd="onRequestEnd" requestStart="onRequestStart">
		<kendo:dataSource-transport>
			 <kendo:dataSource-transport-create url="${createUrl}" dataType="json" type="GET" contentType="application/json" />
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
					
					<kendo:dataSource-schema-model-field name="mobile_No" type="string" >
					<kendo:dataSource-schema-model-field-validation max="10" />
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="emailId" type="string" nullable="false"/>
					
  					<kendo:dataSource-schema-model-field name="information" type="string" >
  					<kendo:dataSource-schema-model-field-validation max="5"/>
  					</kendo:dataSource-schema-model-field>
  
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

function fieldChange() {

	var checkBoxVal = $("#select").val();
/* 	if(checkBoxVal!= ""){
		$("#select").hide();
	} */

	$.ajax({
		url : "./updateClassifiedData",
		type : 'GET',
		dataType : "text",
		data : {
			prePaidId : prePaidId,
			checkBoxVal : checkBoxVal

		},

		contentType : "application/json; charset=utf-8",
		success : function(result) {
		/* 
				$("#select").hide(); */
			
			//alert("Data successfully Updated");
			//$("#select").hide();
			/*  var grid = $("#classifiedGrid").data("kendoGrid");
			grid.refresh();  */
		},

	});

}

function clearFilter() {
	//custom actions

	$("form.k-filter-menu button[type='reset']").slice()
			.trigger("click");
	var gridServiceMaster = $("#grid").data("kendoGrid");
	gridServiceMaster.dataSource.read();
	gridServiceMaster.refresh();
}



var blockId=0;
var blocksName=" ";
var propertyId=0;
var propertyName="";
var prePaidId="";
function onChangeBillsList(arg) {
	var gview = $("#grid").data("kendoGrid");
	var selectedItem = gview.dataItem(gview.select());
	blockId= selectedItem.blockId;

	blocksName = selectedItem.blocksName;
	
	propertyId= selectedItem.propertyId;
	propertyName = selectedItem.propertyName;
	prePaidId = selectedItem.prePaidId;
	
	
	this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
	
}


var setApCode="";
var flagColumnNameCode="";
function prepaidMeterSettingEvent(e)
{
	/***************************  to remove the id from pop up  **********************/
    $('a[id="temPID"]').remove();
    flagColumnNameCode = true;
	$('div[data-container-for="prePaidId"]').remove();
	$('label[for="prePaidId"]').closest('.k-edit-label').remove();
	
	$('div[data-container-for="blockId"]').remove();
	$('label[for="blockId"]').closest('.k-edit-label').remove();
	
	$('div[data-container-for="propertyId"]').remove();
	$('label[for="propertyId"]').closest('.k-edit-label').remove();
	
	
	$('div[data-container-for="personId"]').remove();
	$('label[for="personId"]').closest('.k-edit-label').remove();
	
	$('div[data-container-for="select"]').remove();
	$('label[for="select"]').closest('.k-edit-label').remove();
	/************************* Button Alerts *************************/
	if (e.model.isNew()) 
    {
		//securityCheckForActions("./Masters/AMRSettings/create");
		flagColumnNameCode = false;
		/* setApCode = $('input[name="prePaidId"]').val();
		alert(setApCode);
		var a=$('input[name="personName"]').val();
		alert(a); */
		$(".k-window-title").text("Classified");
		$(".k-grid-update").text("Save");		
    }
	else{
		
		//securityCheckForActions("./Masters/AMRSettings/update");
		$(".k-window-title").text("Edit prePaid Meter");
	}
}
	
	function onRequestStart(e){
		$('.k-grid-update').hide();
        $('.k-edit-buttons').append( '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
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
							"Error: Creating the Classified status\n\n : "
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
						"Classified created successfully");
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
						"PrePaid Meter deleted successfully");
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
				$("#alertsBox").html("PrePaid Meter updated successfully");
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
	
	function informationEditor(container, options){
		$('<textarea data-text-field="information" name="information"  style="width:150px;height:60px" data-value-field="information" data-bind="value:' + options.field + '" required="true"/>').appendTo(container);
		$('<span class="k-invalid-msg" data-for="Information"></span>').appendTo(container);
	}


	function towerNameEditor(container, options) 
   	{
		$('<input name="Tower Name" id="blockId" data-text-field="blocksName" data-value-field="blockId" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoComboBox({
			autoBind : false,			
			dataSource : {
				transport : {		
					read :  "${getAllBlockUrl}"
				}
			},
			change : function (e) {
	            if (this.value() && this.selectedIndex == -1) {                    
					alert("Block doesn't exist!");
	                $("#blockId").data("kendoComboBox").value("");
	        	}
	    
		    }  
		});
		
		$('<span class="k-invalid-msg" data-for="Tower Name"></span>').appendTo(container);
   	}

function propertyEditor(container, options) 
	{
	$('<input name="Propery Name" id="property_No" data-text-field="propertyName" data-value-field="propertyId" data-bind="value:' + options.field + '" required="true"/>')
	.appendTo(container).kendoComboBox({
		cascadeFrom : "blockId", 
		autoBind : false,			
		dataSource : {
			transport : {		
				read :  "${getAllPropertyUrl}"
			}
		},
		change : function (e) {
            if (this.value() && this.selectedIndex == -1) {                    
				alert("P doesn't exist!");
                $("#property_No").data("kendoComboBox").value("");
        	}
    
	    }  
	});
	
	$('<span class="k-invalid-msg" data-for="Propery Name"></span>').appendTo(container);
	}
	
function PersonNames(container, options) 
{
		$('<input name="personNameEE" id="hello1" data-text-field="personName" data-value-field="personId" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoComboBox({
			autoBind : false,
			placeholder : "Select Person",
			cascadeFrom: "property_No",
			headerTemplate : '<div class="dropdown-header">'
				+ '<span class="k-widget k-header">Photo</span>'
				+ '<span class="k-widget k-header">Contact info</span>'
				+ '</div>',
			template : '<table><tr><td rowspan=2><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.personId#'/>" width=40 height=55 alt=\"No Image to Display\" /></span></td>'
				+ '<td align="left"><span class="k-state-default"><b>#: data.personName #</b></span><br>'
				+ '<span class="k-state-default"><i>#: data.personStyle #</i></span><br>'
				+ '<span class="k-state-default"><i>#: data.personType #</i></span></td></tr></table>',
	         dataSource: {  
	             transport:{
	                 read: "${personNamesAutoBasedOnPersonTypeUrl}"
	             }
	         },
	         change : function (e) {
		            if (this.value() && this.selectedIndex == -1) {                    
						alert("Person doesn't exist!");
		                $("#hello1").data("kendoComboBox").value("");
		        	}
			    }
		});
		$('<span class="k-invalid-msg" data-for="personNameEE"></span>').appendTo(container);
}

 function mobileNumberEditor(container, options) 
{
$('<input name="Mobile Number" id="mobile_No" data-text-field="mobile_No" data-value-field="mobile_No" data-bind="value:' + options.field + '" required="true"/>')
.appendTo(container).kendoComboBox({
	cascadeFrom : "hello1", 
	autoBind : false,			
	dataSource : {
		transport : {		
			read :  "${getAllMobileNumbers}"
		}
	},
	 change : function (e) {
        if (this.value().length>11) {                    
			alert("Please Enter 10 Digit Mobile Number!");
			return false;
          //  $("#mobile_No").data("kendoComboBox").value("");
    	}

    }   
});

$('<span class="k-invalid-msg" data-for="Mobile Number"></span>').appendTo(container);
}	
 
 function emailIdEditor(container, options) 
 {
 $('<input name="Email Id" id="emailId" data-text-field="emailId" data-value-field="emailId" data-bind="value:' + options.field + '" required="true"/>')
 .appendTo(container).kendoComboBox({
 	cascadeFrom : "hello1", 
 	autoBind : false,			
 	dataSource : {
 		transport : {		
 			read :  "${getEmailIdforFilter}"
 		}
 	},
 	change : function (e) {
         if (this.value().length && this.value() != "") {                    
 			alert("EmailId can't allow special symbols except(_@.)!");
           return /^[a-zA-Z0-9_.@ ]*$/.test(this.value());
     	}

     }   
 });

 $('<span class="k-invalid-msg" data-for="Email Id"></span>').appendTo(container);
 }		
$("#grid").on("click",".k-grid-ConsumerTemplatesDetailsExport", function(e) {
	
	
	  window.open("./ConsumerTemplate/ConsumerTemplatesDetailsExport");
});

(function ($, kendo) {
    $.extend(true, kendo.ui.validator, {
        rules: { // custom rules
        	informationvalidation: function (input, params) {
					if (input.filter("[name='information']").length
							&& input.val() != "") {
						return /^[\s\S]{1,500}$/.test(input.val());
					}
					return true;
				},
				  
        },
      
        messages: { //custom rules messages
        	informationvalidation:"Information Allows only 500 characters",
        	
            }
        
    });
})(jQuery, kendo);

	
</script>