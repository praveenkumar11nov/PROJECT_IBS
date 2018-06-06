<%@include file="/common/taglibs.jsp"%>
<c:url value="/itemmaster/createitemmaster" var="createUrl" />
<c:url value="/itemmaster/readitemmaster" var="readUrl" />
<c:url value="/itemmaster/updateitemmaster" var="updateUrl" />
<c:url value="/itemmaster/deleteitemmaster" var="destroyUrl" />

<c:url value="/itemmaster/storenamesDropDown" var="helloReadStoreNames" />
<c:url value="/common/getAllChecks" var="allUomUrl" />

<c:url value="/item/upload/itemImage" var="itemImage" />

<kendo:grid name="itemGrid" pageable="true" resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true"  change="onItemVendor" edit="ItemMasterGridEvent"  >
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true">
		<kendo:grid-pageable-messages itemsPerPage="Notification per page" empty="No Notification to display" refresh="Refresh all the Notification" 
			display="{0} - {1} of {2} Services" first="Go to the first page of Notification" last="Go to the last page of Notification" next="Go to the Notification"
			previous="Go to the previous page of Notification"/>
					</kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
		 <kendo:grid-filterable-operators>
		  	<kendo:grid-filterable-operators-string eq="Is equal to" />
		 </kendo:grid-filterable-operators>
			
		</kendo:grid-filterable>
		  <kendo:grid-editable mode="popup"
			confirmation="Are you sure you want to remove this Notification?" />
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Notification" />
		</kendo:grid-toolbar>
		<kendo:grid-toolbarTemplate>
			<div class="toolbar">
			<a class="k-button k-button-icontext k-grid-add" href="#">
		            <span class="k-icon k-add"></span>
		            Add Item Master
	        	</a>
			 <a class='k-button' href='\\#' onclick="clearFilter()"><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>  
			</div>  	
    	</kendo:grid-toolbarTemplate>
    	<kendo:grid-columns>
    	<kendo:grid-column title="Item Name*" field="itemName"  width="120px" >
		</kendo:grid-column>
		
			<kendo:grid-column title="Item Category*" field="category"  width="120px" editor="categoryEditor" >
		</kendo:grid-column>
		
		<kendo:grid-column title="Item Price*" field="price"  width="120px" >
		</kendo:grid-column>
		
		<kendo:grid-column title="Unit of Measure*" field="unitOfMeasure"  width="120px" filterable="true" editor="uomDropDownEditor">
		</kendo:grid-column>
		
			<kendo:grid-column title="Item Discount*" field="discount"  width="120px" >
		</kendo:grid-column>
		
		 <kendo:grid-column title="Image" field="itemImage"
				template="<span onclick='clickToUploadImage()' title='Click to Upload Image' ><img src='./item/getItemimage/#=gid#' id='myImages_#=gid#' alt='No Image to Display' width='80px' height='80px'/></span>"
				filterable="false" width="94px" sortable="false" />

		
		<kendo:grid-column title="Item Review*" field="itemReview"  width="120px" editor="reviewEditor">
		</kendo:grid-column>
		
		<kendo:grid-column title="Item Description*" field="description"  width="120px" >
		</kendo:grid-column>
		
		<kendo:grid-column title="Store Name*" field="sId" hidden="true"  width="120px" editor="storenamesEditor" >
		</kendo:grid-column>
		
		 <kendo:grid-column title="Store Name" field="storeName" width="100px">
									</kendo:grid-column> 
									
		
		
			<kendo:grid-column title="&nbsp;" width="220px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit" />
					<kendo:grid-column-commandItem name="destroy" />
				</kendo:grid-column-command>
				</kendo:grid-column>
				
			
				
				</kendo:grid-columns>
				
			<kendo:dataSource pageSize="5" requestEnd="onItemRequestEnd" requestStart="onRequestStart">
			<kendo:dataSource-transport>
 <kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json"/>
			<kendo:dataSource-transport-create url="${createUrl}" dataType="json" type="GET" contentType="application/json"/>
<kendo:dataSource-transport-update url="${updateUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-destroy url="${destroyUrl}" dataType="json" type="GET" contentType="application/json"/>  
				
			</kendo:dataSource-transport>
			
			 <kendo:dataSource-schema>
				<kendo:dataSource-schema-model id="gid">
					<kendo:dataSource-schema-model-fields>
					<kendo:dataSource-schema-model-field name="gid" type="number" />
						<kendo:dataSource-schema-model-field name="itemName" type="String"/>
						<kendo:dataSource-schema-model-field name="category"/>
							<kendo:dataSource-schema-model-field name="price"/>
								<kendo:dataSource-schema-model-field name="unitOfMeasure" type="String"/>
									<kendo:dataSource-schema-model-field name="discount"/>
									<%-- 	<kendo:dataSource-schema-model-field name="image"/> --%>
											<kendo:dataSource-schema-model-field name="itemReview"/>
												<kendo:dataSource-schema-model-field name="description"/>
													<kendo:dataSource-schema-model-field name="sId" type="number" />
														<kendo:dataSource-schema-model-field name="storeName"  />
						</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
			</kendo:dataSource>				

</kendo:grid>
<div id="alertsBox" title="Alert"></div>
<div id="uploadDialog" title="Upload Image" style="display: none;">
	<kendo:upload complete="oncomplete" name="files" upload="onitemImageUpload" multiple="false" success="onImageSuccess">
				<kendo:upload-async autoUpload="true" saveUrl="${itemImage}" />
	</kendo:upload>
</div>

<script>
/*IMAGE UPLOAD SCRIPT  */


 function onRequestStart(e){
			$('.k-grid-update').hide();
			$('.k-edit-buttons')
					.append(
							'<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
			$('.k-grid-cancel').hide();
	}
	 
	 

function oncomplete() {
	$("#myImage").attr(
			"src",
			"<c:url value='/users/getUserimage/" + SelectedRowId
					+ "?timestamp=" + new Date().getTime() + "'/>");
	$("#myImages_" + SelectedRowId).attr(
			"src",
			"<c:url value='/users/getUserimage/" + SelectedRowId
					+ "?timestamp=" + new Date().getTime() + "'/>");
}
function reviewEditor(container, options) {
	   var data = ["NewlyAdded" , "Featured","DealOfTheDay"];
	   $(
	     '<input data-text-field="" style="width:180px;" id="ownership" data-value-field="" data-bind="value:' + options.field + '" />')
	     .appendTo(container).kendoDropDownList({
	      optionLabel :"Select",
	    
	      dataSource :data            	                 	      
	   });
}      
function clickToUploadImage() {
	$('#uploadDialog').dialog({
		modal : true,
	});
	return false;
}
function onitemImageUpload(e) {
	var files = e.files;
	// Check the extension of each file and abort the upload if it is not .jpg
	$.each(files, function() {
		if (this.extension.toLowerCase() == ".png") {
			e.data = {
					gid : SelectedRowId
				};
		}
		else if (this.extension.toLowerCase() == ".jpg") {
			
			e.data = {
					gid : SelectedRowId
				};
		}
		else if (this.extension.toLowerCase() == ".jpeg") {
			
			e.data = {
					gid : SelectedRowId
				};
		}
		else {
			alert("Only Images can be uploaded\nAcceptable formats are png, jpg and jpeg");
			e.preventDefault();
			return false;
		}
	});
}
function onImageSuccess(e) {
	alert("Uploaded Successfully");
	$(".k-upload-files.k-reset").find("li").remove();
	window.location.reload();
}

function ItemMasterGridEvent(e){
	$('div[data-container-for="gid"]').remove();
	$('label[for="gid"]').closest('.k-edit-label').remove();
	
	 $('div[data-container-for="storeName"]').remove();
	 $('label[for="storeName"]').closest('.k-edit-label').remove();
	 
	 $('div[data-container-for="itemImage"]').hide();
	 $('label[for="itemImage"]').closest('.k-edit-label').hide();
	 
	 
	 
if (e.model.isNew()) 
{
	$(".k-window-title").text("Add Item Master Details");
	$(".k-grid-update").text("Save");		
}
else{
	$(".k-window-title").text("Edit Item Master Details");
}
}

function onItemRequestEnd(e) {
	if (typeof e.response != 'undefined') {
		if (e.response.status == "FAIL") {
			errorInfo = "";
			errorInfo = e.response.result.invalid;
			var i = 0;
			for (i = 0; i < e.response.result.length; i++) {
				errorInfo += (i + 1) + ". "
						+ e.response.result[i].defaultMessage + "\n";
			}
			if (e.type == "create") {
				alert("Error: Creating the Store Master Details\n\n"
						+ errorInfo);
			}
			if (e.type == "update") {
				alert("Error: Updating the Store Master Details\n\n"
						+ errorInfo);
			}
			var grid = $("#itemGrid").data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
			return false;
		}
		if (e.type == "update" && !e.response.Errors) {
			e.sender.read();
			$("#alertsBox").html("Alert");
			$("#alertsBox").html("Item Master Updated successfully");
			$("#alertsBox").dialog({
				modal : true,
				draggable : false,
				resizable : false,
				buttons : {
					"Close" : function() {
						$(this).dialog("close");
					}
				}
			});
		}
		if (e.type == "create" && !e.response.Errors) {
			e.sender.read();
			$("#alertsBox").html("Alert");
			$("#alertsBox").html("Item Master Created successfully");
			$("#alertsBox").dialog({
				modal : true,
				draggable : false,
				resizable : false,
				buttons : {
					"Close" : function() {
						$(this).dialog("close");
					}
				}
			});
		}
		if (e.type == "destroy" && !e.response.Errors) {
			$("#alertsBox").html("Alert");
			$("#alertsBox").html("Item Master Deleted successfully");
			$("#alertsBox").dialog({
				modal : true,
				draggable : false,
				resizable : false,
				buttons : {
					"Close" : function() {
						$(this).dialog("close");
					}
				}
			});
			var grid = $("#itemGrid").data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
		}
	}
}

var SelectedRowId = "";

function onItemVendor(arg) {
	 var gview = $("#itemGrid").data("kendoGrid");
 	 var selectedItem = gview.dataItem(gview.select());
 	 SelectedRowId = selectedItem.gid;
 	
     
}
function clearFilter()
{
   $("form.k-filter-menu button[type='reset']").slice().trigger("click");
   var gridStoreIssue = $("#itemGrid").data("kendoGrid");
   gridStoreIssue.dataSource.read();
   gridStoreIssue.refresh();
}


function uomDropDownEditor(container, options) {
	   var data = ["litres" , "grams","kg","nos"];
	   $(
	     '<input data-text-field="" style="width:180px;" id="ownership" data-value-field="" data-bind="value:' + options.field + '" />')
	     .appendTo(container).kendoDropDownList({
	      optionLabel :"Select",
	    
	      dataSource :data            	                 	      
	   });
}        


function categoryEditor(container, options) {
	   var data = ["Fruits" , "Vegetables","Grocery","HealthCare" , "CleaningAgents","PetZone","Fish And Meat","Stationary","Others"];
	   $(
	     '<input data-text-field="" style="width:180px;" id="ownership" data-value-field="" data-bind="value:' + options.field + '" />')
	     .appendTo(container).kendoDropDownList({
	      optionLabel :"Select",
	    
	      dataSource :data            	                 	      
	   });
}        

function storenamesEditor(container,options)
{
	  $('<input data-text-field="storeName" name="StoreMasterEntity" id="sId" required="true" data-value-field="sId" data-bind="value:' + options.field + '"/>').appendTo(container).kendoComboBox
		 ({
		   	 placeholder : "Select Componenet Names",
		     template : '<table><border><tr><td rowspan=2><span class="k-state-default"></span></td>'
				+ '<td padding="5px"><span class="k-state-default"><b>#: data.storeName #</b></span><br></td></tr></border></table>',
				filter:"startswith",
     	
			    dataSource: {       
		           transport: {
		               read: "${helloReadStoreNames}"
		           }
		       },
		   });
		   $('<span class="k-invalid-msg" data-for="Component"></span>').appendTo(container);   
  } 

</script>
<style>
.k-upload-button input {
 z-index: 10000
}
</style>