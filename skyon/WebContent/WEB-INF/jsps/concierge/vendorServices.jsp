<%@include file="/common/taglibs.jsp"%>

<!-- vendorServices url -->
<c:url value="/vendorServices/read" var="readUrl" />
<c:url value="/vendorServices/create" var="createUrl" />
<c:url value="/vendorServices/update" var="updateUrl" />
<c:url value="/vendorServices/delete" var="destroyUrl" />
		
<c:url value="/vendorServices/getCsVendorNames" var="getVendorUrl" />
<c:url value="/vendorServices/getCsServices" var="getCsServiceUrl" />
<c:url value="/PatrolTrackPoints/getStatusList" var="getStatusListUrl" />
<c:url value="/vendorServices/getVendorNameList" var="getVendorNameListUrl" />
<c:url value="/vendorServices/getServiceNameList" var="getServiceNameListUrl" />
<c:url value="/vendorServices/getServiceNamesBasedOnVendorId" var="serviceNamesBasedOnVendorIdUrl" />


<!-- vendorServices charge url -->
<c:url value="/vendorServices/charge/read" var="serviceChargeReadUrl" />
<c:url value="/vendorServices/charge/create" var="serviceChargeCreateUrl" />
<c:url value="/vendorServices/charge/update" var="serviceChargeUpdateUrl" />
<c:url value="/vendorServices/charge/delete" var="serviceChargeDestroyUrl" />

<script>

var vendorEndDate_win;
var vendorServiceId;
var vendorServiceStartDate;
var serEndDate;
</script> <!-- padding: 2.177em 0; -->

<!-- for end date -->
<!-- <button type="button" id="open1">Open first Window</button> -->
  <div id="vendorServiceWin1">
  
   <div class="demo-section k-header" id="vendorServiceMainDiv" style="width: 210px;">
                <h4>Select Date</h4>
                <input type="date" id="datePicker" style="width: 200px;" placeholder="dd/mm/yyyy" required/><br/><br/></div> 
                <div>
                <input type="button" value="Save" onclick="vendorServiceGetEndDt()" style="width: 50px; height:20px; font: bold;font-size: unset;margin: auto;background:rgb; color: hsla();"/>
            </div>
</div>  

<!-- for end date -->

		
		<kendo:grid name="vendorSerGrid" pageable="true" edit="vendorServiceEvent" change="onChangeService"
		resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true" detailTemplate="csVendorCharges" groupable="true" filterable="true" >
		
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" >
		<kendo:grid-pageable-messages itemsPerPage="Vendor Services per page" empty="No Vendor Services to display" refresh="Refresh all the Vendor Services" 
			display="{0} - {1} of {2} Vendor Services" first="Go to the first page of Vendor Services" last="Go to the last page of Services" next="Go to the Vendor Services"
			previous="Go to the previous page of Vendor Services"/>
		</kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
		 <kendo:grid-filterable-operators>
		  	<kendo:grid-filterable-operators-string eq="Is equal to"/>
		 </kendo:grid-filterable-operators>
			
		</kendo:grid-filterable>
		
		  <kendo:grid-editable mode="popup"
			confirmation="Are you sure you want to remove this Concierge Vendor Service?" />
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Concierge Vendor Service" />
		</kendo:grid-toolbar>
		<kendo:grid-toolbarTemplate>
			<div class="toolbar">
			<a class="k-button k-button-icontext k-grid-add" href="#">
		            <span class="k-icon k-add"></span>
		            Add Concierge Vendor Service
	        	</a>
				 <a class='k-button' href='\\#' onclick="clearFilterConciergeVendorServices()"><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a> 
			</div>  	
    	</kendo:grid-toolbarTemplate>
		<kendo:grid-columns>
		<kendo:grid-column title="Concierge Vendors *" field="conciergeVendors" editor="vendorEditor" width="120px">
		<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function vendorNameFilter(element) {
								element.kendoAutoComplete({
									dataSource: {
										transport: {
											read: "${getVendorNameListUrl}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
		</kendo:grid-column>
		<kendo:grid-column title="Concierge Services *" field="conciergeService" editor="serviceEditor" width="120px" >
		<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function serviceNameFilter(element) {
								element.kendoAutoComplete({
									dataSource: {
										transport: {
											read: "${getServiceNameListUrl}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
		</kendo:grid-column>
		<kendo:grid-column title="Service Start Date *" field="startDate" format="{0:dd/MM/yyyy}" 
				width="130px" filterable="true">
		 <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
    					function startDateFilter(element) {
							element.kendoDatePicker({
								format:"dd/MM/yyyy",
				            	
			            });
				  		}
    					
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	
		</kendo:grid-column>
		<kendo:grid-column title="Service End Date *" field="endDate" width="120px" format="{0:dd/MM/yyyy}">
		 <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
    					function endDateFilter(element) {
							element.kendoDatePicker({
								format:"dd/MM/yyyy",
				            	
			            });
				  		}
    					
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	
		</kendo:grid-column>
		<kendo:grid-column title="Service Status*" field="status" width="120px">
		<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function statusFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Status",
									dataSource: {
										transport: {
											read: "${getStatusListUrl}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
		</kendo:grid-column>
		<kendo:grid-column title="&nbsp;" width="160px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit"/>
					<kendo:grid-column-commandItem name="destroy"/>
					<kendo:grid-column-commandItem name="conciergeVendorServiceEndDatee" text="Add/Edit Service End Date">
				 <kendo:grid-column-commandItem-click>
				<script>
				function showDetails(e){
					result=securityCheckForActionsForStatus("./conciergeManagement/vendorService/addOrEditEndDate");   
					   if(result=="success"){
					 $("#vendorServiceMainDiv input").val("");
					var dataItem = this.dataItem($(e.currentTarget).closest("tr"));
					vendorServiceId = dataItem.vsId;
					vendorServiceStartDate = dataItem.startDate;
					 serEndDate = dataItem.endDate;
					var formatedEndDate = $.datepicker.formatDate("dd/mm/yy",serEndDate);
					$("#vendorServiceMainDiv input").val(formatedEndDate);
					vendorEndDate_win.dialog( "open" );
					   }
					
					
				}
				</script>
				</kendo:grid-column-commandItem-click> 
				</kendo:grid-column-commandItem>
				</kendo:grid-column-command>
			</kendo:grid-column>
			<kendo:grid-column title=""
				template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Activate#=data.ptsId#'>#= data.status == 'Active' ? 'De-activate' : 'Activate' #</a>"
				width="100px" />
				</kendo:grid-columns>
				<kendo:dataSource pageSize="5" requestEnd="onRequestEnd" requestStart="OnRequestStart">
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
			
			 <kendo:dataSource-schema parse="parse">
				<kendo:dataSource-schema-model id="vsId">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="vsId" type="number" />
						<kendo:dataSource-schema-model-field name="csvId"  type="number">
						 <kendo:dataSource-schema-model-field-validation  />
						 </kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="cssId" type="number">
						<kendo:dataSource-schema-model-field-validation  />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="conciergeVendors">
						<kendo:dataSource-schema-model-field-validation  />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="conciergeService">
						<kendo:dataSource-schema-model-field-validation  />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="startDate" type="date" defaultValue=" ">
						<kendo:dataSource-schema-model-field-validation required="true"/>
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="endDate" type="date" defaultValue="" >
						<kendo:dataSource-schema-model-field-validation/>
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="status" type="string">
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="createdBy" type="string">
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="lastUpdatedBy" type="string">
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="lastUpdatedDt" type="date"/>
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
			</kendo:dataSource>

</kendo:grid>

<kendo:grid-detailTemplate id="csVendorCharges">
<kendo:tabStrip name="tabStrip_#=vsId#">
<kendo:tabStrip-animation>
<!-- <tabStrip-animation-open effects="fadeIn" /> -->
</kendo:tabStrip-animation>
<kendo:tabStrip-items>
<kendo:tabStrip-item text="Vendor Service Charges" selected="true">
<kendo:tabStrip-item-content>
<kendo:grid name="gridServiceCharges_#=vsId#" remove="serviceChargeRemove" edit="serviceChargeEvent" pageable="true" resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true" filterable="false">
	<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" ></kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
		 <kendo:grid-filterable-operators>
		  	<kendo:grid-filterable-operators-string eq="Is equal to"/>
		 </kendo:grid-filterable-operators>
			
		</kendo:grid-filterable>		
		<kendo:grid-editable mode="popup" confirmation="Are you sure you want to remove this item?" />
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Vendor Service Charges"  />
		</kendo:grid-toolbar>
		<kendo:grid-columns>
		<kendo:grid-column title="Collection Method *" field="collectionMethod" editor="collectionEditor" width="130px" filterable="false"/>
		<kendo:grid-column title="Vendor Rate Type *" field="vendorRateType" editor="vendorRateTypeEditor" width="130px" filterable="false"/>
		<kendo:grid-column title="Vendor Pay Method *" field="vrtPaymethod" editor="vendorPayMethodEditor" width="150px" filterable="false"/>
		<kendo:grid-column title="Vendor Rate *" field="vendorRate" width="130px" filterable="false"/>
		<kendo:grid-column title="Vendor Rate Type Note" field="vendorRateTypeNote" editor="vendorRateTypeNoteEditor" width="160px" filterable="false" />
		<kendo:grid-column title="Service Rate Type *" field="serviceRateType" editor="serviceRateTypeEditor" width="130px" filterable="false"/>
		<kendo:grid-column title="Service Pay Method *" field="srtPaymethod" editor="servicePayMethodEditor" width="150px" filterable="false"/>
		<kendo:grid-column title="Service Rate*" field="serviceRate" width="130px" filterable="false"/>
		<kendo:grid-column title="Service Rate Type Note" field="serviceRateTypeNote" editor="serviceRateTypeNoteEditor" width="160px" filterable="false"/>
		<kendo:grid-column title="&nbsp;" width="160px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit"/>
					<kendo:grid-column-commandItem name="destroy"/>
				</kendo:grid-column-command>
			</kendo:grid-column>
		</kendo:grid-columns>	
		<kendo:dataSource pageSize="5" requestEnd="onRequestEndCharge" requestStart="onRequestStartCharge">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-read url="${serviceChargeReadUrl}/#=vsId#" dataType="json"
					type="POST" contentType="application/json" />
				<kendo:dataSource-transport-create url="${serviceChargeCreateUrl}/#=vsId#"
					dataType="json" type="GET" contentType="application/json" />
				<kendo:dataSource-transport-update url="${serviceChargeUpdateUrl}/#=vsId#"
					dataType="json" type="GET" contentType="application/json" />
				<kendo:dataSource-transport-destroy url="${serviceChargeDestroyUrl}/"
					dataType="json" type="GET" contentType="application/json" />
				 <%-- <kendo:dataSource-transport-parameterMap>
					<script>
						function parameterMap(options, type) {
							return JSON.stringify(options);
						}
					</script>
				</kendo:dataSource-transport-parameterMap>  --%>
			</kendo:dataSource-transport>
			 <kendo:dataSource-schema>
				<kendo:dataSource-schema-model id="vscId">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="vscId" type="number" />
						<kendo:dataSource-schema-model-field name="vsId" type="number">
						 <kendo:dataSource-schema-model-field-validation required="true" />
						 </kendo:dataSource-schema-model-field>
						 <kendo:dataSource-schema-model-field name="collectionMethod">
						<kendo:dataSource-schema-model-field-validation/>
						</kendo:dataSource-schema-model-field>
						 	<kendo:dataSource-schema-model-field name="vendorRateType">
						<kendo:dataSource-schema-model-field-validation/>
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="vrtPaymethod">
						<kendo:dataSource-schema-model-field-validation />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="vendorRateTypeNote">
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="vendorRate" type="number">
						<kendo:dataSource-schema-model-field-validation required="true" min="0"/>
						</kendo:dataSource-schema-model-field>
						 <kendo:dataSource-schema-model-field name="serviceRateType">
						<kendo:dataSource-schema-model-field-validation />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="srtPaymethod">
						<kendo:dataSource-schema-model-field-validation  />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="serviceRateTypeNote">
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="serviceRate" type="number">
						<kendo:dataSource-schema-model-field-validation required="true" min="0"/>
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="createdBy">
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="lastUpdatedBy">
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="lastUpdatedDt" type="date"/>
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
			</kendo:dataSource>				
</kendo:grid>
</kendo:tabStrip-item-content>
</kendo:tabStrip-item>
			
</kendo:tabStrip-items>
</kendo:tabStrip>
</kendo:grid-detailTemplate>

<div id="alertsBox" title="Alert"></div>

<!--  ----------------------------------------------------------- vendorServices Script ---------------------------------------------  -->

<script type="text/javascript">

function parse (response) {   
    $.each(response, function (idx, elem) {
        if (elem.startDate && typeof elem.startDate === "string") {
            elem.startDate = kendo.parseDate(elem.startDate, "yyyy-MM-ddTHH:mm:ss.fffZ");
        }
        if (elem.endDate && typeof elem.endDate === "string") {
            elem.endDate = kendo.parseDate(elem.endDate, "yyyy-MM-ddTHH:mm:ss.fffZ");
        }
    });
    return response;
}  


//for end date
var vendorServiceEndDate = "";
vendorEndDate_win  = $( "#vendorServiceWin1" ).dialog({
autoOpen: false,
height: 250,
width: 300,
modal: true,
title:"Add Concierge Vendor Service End Date",

}); 

$("input[type=date]").kendoDatePicker({
   open:function(e)
       {                
           window.setTimeout(function(){ $(".k-calendar-container").parent(".k-animation-container").css("zIndex", "11000"); }, 1);
       },
change:onChange,
format:"dd/MM/yyyy"
 });
function onChange(e){
	
	vendorServiceEndDate = this.value();
	 
}

function vendorServiceGetEndDt(){
	securityCheckForActions("./conciergeManagement/vendorService/addOrEditEndDate");
	
	 if(vendorServiceEndDate == null || vendorServiceEndDate == "")
	   {
		 $("#alertsBox").html("");
			$("#alertsBox").html("Please Select proper 'Service End Date'");
			$("#alertsBox").dialog({
				modal : true,
				buttons : {
					"Close" : function() {
						$(this).dialog("close");
					}
				}
			});
		 return false;
		 
	 }
	 else{
		 if( vendorServiceEndDate < vendorServiceStartDate ){
			 $("#alertsBox").html("");
				$("#alertsBox").html("Error: 'End Date' should be greater than or equal to 'Start Date''");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
			 return false;
		} 
		else{
	 $.ajax({
			type : "POST",
			dataType:"text",
			url : "./vendorServices/updateCsVendorServiceEndDt/"+vendorServiceEndDate+"/"+vendorServiceId,
			
			success : function(response) {
				if (response == "Success") {
					$("#alertsBox").html("");
					$("#alertsBox").html("Service 'End Date' saved successfully");
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
					vendorEndDate_win.dialog( "close" );
				}
				else if (response == "false") {
					$("#alertsBox").html("");
					$("#alertsBox").html("Error: While saving service 'End Date'");
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
				}
				 var grid = $("#vendorSerGrid").data("kendoGrid");
			      grid.dataSource.read();
			      grid.refresh();
			}
	 });
		}
	 } 
}

//for end date




var SelectedRowId = "";
function onChangeService(arg) {
	 var gview = $("#vendorSerGrid").data("kendoGrid");
 	 var selectedItem = gview.dataItem(gview.select());
 	 SelectedRowId = selectedItem.vsId;
 	 this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
     //alert("Selected: " + SelectedRowId);
}
var servName = "";
var flag = "";
var res = [];
var res1 = [];
var res2 = [];
var enterdService = "";
var existingService = "";

function clearFilterConciergeVendorServices()
{
	  $("form.k-filter-menu button[type='reset']").slice().trigger("click");
	  var grid = $("#vendorSerGrid").data("kendoGrid");
	  grid.dataSource.read();
	  grid.refresh();
}
$("#vendorSerGrid").on("click", ".k-grid-add", function() {
	
	setTimeout(function () {
	    $(".k-edit-field .k-input").first().focus();
	}, 1000);
	/* if($("#vendorSerGrid").data("kendoGrid").dataSource.filter()){
        $("form.k-filter-menu button[type='reset']").slice().trigger("click");
      var grid = $("#vendorSerGrid").data("kendoGrid");
      grid.dataSource.read();
      grid.refresh();
          } */
});
function vendorServiceEvent(e)
{
	 var myDatePicker = $('input[name="startDate"]').data("kendoDatePicker");
	  var today = new Date();
	  myDatePicker.min(today);
	  
	if (e.model.isNew()) 
    {
		securityCheckForActions("./conciergeManagement/vendorService/createButton");
		
		$(".k-window-title").text("Add Concierge Vendor Services");
	    $(".k-grid-update").text("Save");
		
		$('label[for="status"]').closest('.k-edit-label').hide();
			$('div[data-container-for="status"]').hide(); 
		$(".k-window-title").text("Add Concierge Vendor Service");
		$(".k-grid-update").text("Save");
		$(".k-grid-Activate").hide();
		$('[name="status"]').attr("disabled", true);
		$('.k-edit-field .k-input').first().focus();
		$(".k-edit-field").each(function () {
	        $(this).find("#temPID").parent().remove();
	     });
		
		$('label[for="endDate"]').parent().remove();
		$('div[data-container-for="endDate"]').remove();
		
		$(".k-grid-update").click(function () {
			var vendors = $("#csVendor").data("kendoComboBox");
			 var services = $("#csServices").data("kendoDropDownList");
			 if( vendors.select() == -1 && services.select() == 0 ){
				 alert("Error:Please select proper 'Vendor' and 'Service' from dropdown");
				/*  $("#alertsBox").html("");
		 	     $("#alertsBox").html("Error:Please select proper 'Vendor' and 'Service' from dropdown");
		 	     $("#alertsBox").dialog({
		 	      modal : true,
		 	      buttons : {
		 	       "Close" : function() {
		 	        $(this).dialog("close");
		 	       }
		 	      }
		 	     }); */
		 	     return false;
			 }
			 else if( vendors.select() == -1 ){
				 alert("Error: Please select proper 'Vendor' from dropdown");
				 return false;
				 
			 }
			 else if( services.select() == 0 ){
				 alert("Error: Please select proper 'Service' from dropdown");
				 return false;
			 }
			 
		});
		
    }
	else{
		
		securityCheckForActions("./conciergeManagement/vendorService/updateButton");
		/* if($("#vendorSerGrid").data("kendoGrid").dataSource.filter()){
	        $("form.k-filter-menu button[type='reset']").slice().trigger("click");
	      var grid = $("#vendorSerGrid").data("kendoGrid");
	      grid.dataSource.read();
	      grid.refresh();
	          } */
		$(".k-window-title").text("Edit Concierge Vendor Services");
	    $(".k-grid-update").text("Update");
				
		$(".k-grid-Activate").hide();
		$('[name="status"]').attr("disabled", true);
		$('.k-edit-field .k-input').first().focus();
		$(".k-edit-field").each(function () {
	        $(this).find("#temPID").parent().remove();
	     });
		$('label[for="status"]').closest('.k-edit-label').hide();
			$('div[data-container-for="status"]').hide(); 
		$('label[for="endDate"]').parent().hide();
		$('div[data-container-for="endDate"]').hide();
		
		$(".k-grid-update").click(function () {
			 var vendors = $("#csVendor").data("kendoComboBox");
			 var services = $("#csServices").data("kendoDropDownList");
			 if( vendors.select() == -1 && services.select() == 0 ){
				 alert("Error:Please select proper 'Vendor' and 'Service' from dropdown");
		 	     return false;
			 }
			 else if( vendors.select() == -1 ){
				 alert("Error: Please select proper 'Vendor' from dropdown");
				 return false;
				 
			 }
			 else if( services.select() == 0 ){
				 alert("Error: Please select proper 'Service' from dropdown");
				 return false;
			 }
			 
		});
		
	}
}

$("#vendorSerGrid").on("click", ".k-grid-delete", function(e) {
	
	securityCheckForActions("./conciergeManagement/vendorService/deleteButton");
	
	var widget = $("#vendorSerGrid").data("kendoGrid");
	var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
	if( dataItem.status == 'Active' ){
		 var grid = $("#vendorSerGrid").data("kendoGrid");
 	     grid.cancelRow();
 	     $("#alertsBox").html("");
 	     $("#alertsBox").html("Error: You cannot delete 'Active' Vendor Service");
 	     $("#alertsBox").dialog({
 	      modal : true,
 	      buttons : {
 	       "Close" : function() {
 	        $(this).dialog("close");
 	       }
 	      }
 	     });
 	     var grid = $("#vendorSerGrid").data("kendoGrid");
 	     grid.cancelChanges();
		
	}
	else{
	}
});
$("#vendorSerGrid").on("click", "#temPID", function(e) {
	 
		var button = $(this), enable = button.text() == "Activate";
		var widget = $("#vendorSerGrid").data("kendoGrid");
		var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
		
		result=securityCheckForActionsForStatus("./conciergeManagement/vendorService/statusButton");   
		   if(result=="success"){
		if (enable) {
				$.ajax({
					type : "POST",
					dataType:"text",
					url : "./conciergeManagement/vendorServiceStatus/" + dataItem.id + "/activate",
					dataType : 'text',
					success : function(response) {
						$("#alertsBox").html("");
						$("#alertsBox").html(response);
						$("#alertsBox").dialog({
							modal : true,
							buttons : {
								"Close" : function() {
									$(this).dialog("close");
								}
							}
						});
						button.text('Deactivate');
						$('#vendorSerGrid').data('kendoGrid').dataSource.read();
					}
				});
			} else {
				$.ajax({
					type : "POST",
					dataType:"text",
					url : "./conciergeManagement/vendorServiceStatus/" + dataItem.id + "/deactivate",
					success : function(response) {
						$("#alertsBox").html("");
						$("#alertsBox").html(response);
						$("#alertsBox").dialog({
							modal : true,
							buttons : {
								"Close" : function() {
									$(this).dialog("close");
								}
							}
						});
						button.text('Activate');
						$('#vendorSerGrid').data('kendoGrid').dataSource.read();
					}
				});
			}
		}
		
});
function vendorEditor(container, options){
	 if (options.model.conciergeVendors == '') {
	$('<select id="csVendor" data-text-field="conciergeVendors" data-value-field="csvId" data-bind="value:' + options.field + '" />')
	.appendTo(container).kendoComboBox({	
		placeholder:"Select",
		defaultValue : false,
		sortable : true,
		dataSource: {  
            transport:{
                read: "${getVendorUrl}"
            }
        }

	});
	 }
	 else
		 {
		 $('<select id="csVendor" data-text-field="conciergeVendors" disabled data-value-field="csvId" data-bind="value:' + options.field + '" />')
			.appendTo(container).kendoComboBox({	
				placeholder:"Select",
				defaultValue : false,
				sortable : true,
				dataSource: {  
		            transport:{
		                read: "${getVendorUrl}"
		            }
		        }

			});
		 
		 }
/*  $('<span class="k-invalid-msg" data-for="This field"></span>').appendTo(container); */
	
}
function serviceEditor(container, options){
	if (options.model.conciergeService == '') {
		
		$('<select id="csServices" data-text-field="conciergeService" data-value-field="conciergeService" data-bind="value:' + options.field + '" />')
		.appendTo(container).kendoDropDownList({	
			optionLabel : "Select",
			defaultValue : false,
			sortable : true,
			dataSource: {  
	            transport:{
	                read: "${getCsServiceUrl}"
	            }
			}
		});
		
	}
	 else
	 {
	$('<select id="csServices" data-text-field="conciergeService" disabled data-value-field="conciergeService" data-bind="value:' + options.field + '" />')
	.appendTo(container).kendoDropDownList({	
		optionLabel : "Select",
		defaultValue : false,
		sortable : true,
		dataSource: {  
            transport:{
                read: "${getCsServiceUrl}"
            }
		}
	});
	 }
	}
/*  $('<span class="k-invalid-msg" data-for="This field"></span>').appendTo(container); */
	//custom validation
var requiredStartDate = "";
(function($, kendo) {
	$
			.extend(
					true,
					kendo.ui.validator,
					{
						rules : { // custom rules  
							   serviceNameUniqueness : function(input,params) {
									//check for the name attribute 
									if (input.filter("[name='conciergeService']").length && input.val()) {
										enterdService = input.val().toUpperCase();	
										$.each(res1,function(ind, val) {
													if ((enterdService == val.toUpperCase()) && (enterdService.length == val.length)) {
													flag = enterdService;
													return false;
											}
										});
									}
									return true;
							},
						serNameUniqueness : function(input) {
									if (input.filter("[name='conciergeService']").length && input.val() && flag != "") {
										flag = "";
										return false;
									}
									return true;
							},
						startDateMsg: function (input, params) 
				             {
			                     if (input.filter("[name = 'startDate']").length && input.val() != "") 
			                     {                          
			                         var selectedDate = input.val();
			                         requiredStartDate = selectedDate;

			                        
			                     }
			                     return true;
			                 },
			                 endDateMsg: function (input, params) 
				             {
			                     if (input.filter("[name = 'endDate']").length && input.val() != "") 
			                     {     
			                         var selectedDate = input.val();
			                         var flagDate = false;

			                         if ($.datepicker.parseDate('dd/mm/yy', selectedDate) >= $.datepicker.parseDate('dd/mm/yy', requiredStartDate)) 
			                         {
			                                flagDate = true;
			                         }
			                         return flagDate;
			                     }
			                     return true;
			                 } ,
						},
						messages : {
							serNameUniqueness: " Service Name already exists, please try with some other name ",
				        	serviceNameUniqueness: " Service Name already exists, please try with some other name ",
							endDateMsg:"Start date should be greater than or equal to End date",
						}
					});

})(jQuery, kendo);
//End Of Validation

function OnRequestStart(e){
	$('.k-grid-update').hide();
    $('.k-edit-buttons')
            .append(
                    '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
    $('.k-grid-cancel').hide();
}
function onRequestStartCharge(e){
	$('.k-grid-update').hide();
    $('.k-edit-buttons')
            .append(
                    '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
    $('.k-grid-cancel').hide();
}
function onRequestEnd(e){
	if (typeof e.response != 'undefined')
	{
		 if (e.response.status == "FAIL") 
			{
				errorInfo = "";
				for (i = 0; i < e.response.result.length; i++) {
					errorInfo += (i + 1) + ". "
							+ e.response.result[i].defaultMessage + "<br>";

				}

				if (e.type == "create") {
					 $("#alertsBox").html("");
						$("#alertsBox").html("Error: Creating the Vendor Service\n\n : " + errorInfo);
						$("#alertsBox").dialog({
							modal : true,
							buttons : {
								"Close" : function() {
									$(this).dialog("close");
								}
							}
						});
				}
				if (e.type == "update") {
					 $("#alertsBox").html("");
						$("#alertsBox").html("Error: Updating the Vendor Service\n\n : " + errorInfo);
						$("#alertsBox").dialog({
							modal : true,
							buttons : {
								"Close" : function() {
									$(this).dialog("close");
								}
							}
						});
				}
				var grid = $("#vendorSerGrid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			}
		
		 else if (e.response.status == "CREATEEXIST") {
				errorInfo = "";
				errorInfo = e.response.result.createExists;
				if (e.type == "create") {
					$("#alertsBox").html("");
						$("#alertsBox").html("Error: Creating the Vendor Service\n\n : " + errorInfo);
								$("#alertsBox").dialog({
									modal : true,
									buttons : {
										"Close" : function() {
											$(this).dialog("close");
										}
									}
								});
				}
				var grid = $("#vendorSerGrid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			}
	else if (e.type == "create") {
			 
			 $("#alertsBox").html("");
				$("#alertsBox").html("Vendor Service created successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				
			//alert("Staff record created successfully");
				var grid = $("#vendorSerGrid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
		 }
		else if (e.type == "update") {
			 
			 $("#alertsBox").html("");
				$("#alertsBox").html("Vendor Service updated successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				
			//alert("Staff record created successfully");
				var grid = $("#vendorSerGrid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
		 }
		else if (e.response.status == "EXCEPTION") {
				errorInfo = "";
				errorInfo = e.response.result.exception;
				if (e.type == "destroy") {
					$("#alertsBox").html("");
						$("#alertsBox").html("Error: Deleting the Vendor Service\n\n : " + errorInfo);
								$("#alertsBox").dialog({
									modal : true,
									buttons : {
										"Close" : function() {
											$(this).dialog("close");
										}
									}
								});
				}
				var grid = $("#vendorSerGrid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			}
		 else if (e.type == "destroy") {
			 $("#alertsBox").html("");
				$("#alertsBox").html("Vendor Service deleted successfully");
						$("#alertsBox").dialog({
							modal : true,
							buttons : {
								"Close" : function() {
									$(this).dialog("close");
								}
							}
						});
				
				var grid = $("#vendorSerGrid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
		 }
	}
}
</script>
<!--  -----------------------------------------------------------VendorServices charge script---------------------------------------------  -->
<script type="text/javascript">


 function serviceChargeEvent(e)
{
	if (e.model.isNew()) 
    {
		securityCheckForActions("./conciergeManagement/vendorService/serviceCharge/createButton");
		setTimeout(function () {
		    $(".k-edit-field .k-input").first().focus();
		}, 1000);
		$(".k-window-title").text("Add Vendor Service Charges");
	    $(".k-grid-update").text("Save");
	    
		$('.k-edit-field .k-input').first().focus();
		$(".k-grid-update").text("Save");
		$(".k-grid-update").click(function () {
			var firstItem = $('#gridServiceCharges_'+SelectedRowId).data().kendoGrid.dataSource.data()[0];
			var vRateNote = firstItem.get('vendorRateTypeNote'); 
			var sRateNote = firstItem.get('serviceRateTypeNote'); 
			  if( vRateNote != "" && vRateNote != null ){
			  if( vRateNote.length > 255 ){
					 alert("'Vendor Rate Type Note' allow only 255 characters");
					 return false;
				 }
				}
			  if( sRateNote != "" && sRateNote != null ){
				  if( sRateNote.length > 255 ){
						 alert("'Service Rate Type Note' allow only 255 characters");
						 return false;
					 }
			}
		});
		
    }
	else
	{
		securityCheckForActions("./conciergeManagement/vendorService/serviceCharge/updateButton");
		
		$(".k-window-title").text("Edit Vendor Service Charges");
	    $(".k-grid-update").text("Update");

		
		$('.k-edit-field .k-input').first().focus();
		$(".k-grid-update").text("Update");
		var gview = $('#gridServiceCharges_'+SelectedRowId).data("kendoGrid");
		  //Getting selected item
		var selectedItem = gview.dataItem(gview.select());
		  
		$(".k-grid-update").click(function () {
			var vRateNote = selectedItem.vendorRateTypeNote;
			  var sRateNote = selectedItem.serviceRateTypeNote; 
			  if( vRateNote != "" && vRateNote != null ){
				  if( vRateNote.length > 255 ){
						 alert("'Vendor Rate Type Note' allow only 255 characters");
						 return false;
					 }
					}
			  if( sRateNote != "" && sRateNote != null ){
			  if( sRateNote.length > 255 ){
					 alert("'Service Rate Type Note' allow only 255 characters");
					 return false;
				 }
		}
		});
		
	}
}
 /* $("#gridServiceCharges_"+SelectedRowId).on("click", ".k-grid-delete", function(e) {
		
	 securityCheckForActions("./conciergeManagement/vendorService/serviceCharge/deleteButton");
	 
 }); */
function serviceChargeRemove(){
	 securityCheckForActions("./conciergeManagement/vendorService/serviceCharge/deleteButton");
	 /*  var result = confirm("Are you sure you want to delete this record ?");
	 if(!result){
		 $('#gridServiceCharges_'+SelectedRowId).data().kendoGrid.dataSource.read();
		 throw new Error("error");
	 } */
	  
}
function collectionEditor(container, options){
	var data = [
		         { text: "Concierge", value: "Concierge" },
              	{ text: "Vendor",value:"Vendor"},
          ];
	 if (options.model.collectionMethod == '') {
		$('<select name="Collection Method" id="collectionMethodId" data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '" required="true" />')
		.appendTo(container).kendoDropDownList({	
			optionLabel : "Select",
			 dataTextField: "text",
		      dataValueField: "value",
		       dataSource: data
		});
	 }
	 else{
		 $('<select name="Collection Method" disabled id="collectionMethodId" data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '" required="true" />')
			.appendTo(container).kendoDropDownList({	
				optionLabel : "Select",
				 dataTextField: "text",
			      dataValueField: "value",
			       dataSource: data
			}); 
		 
	 }
		 $('<span class="k-invalid-msg" data-for="Collection Method"></span>').appendTo(container);
}

function vendorRateTypeNoteEditor(container, options) 
{
    $('<textarea data-text-field="vendorRateTypeNote" data-value-field="vendorRateTypeNote" data-bind="value:' + options.field + '" style="width: 148px; height: 46px;"/>')
         .appendTo(container);
}
function serviceRateTypeNoteEditor(container, options) 
{
    $('<textarea data-text-field="serviceRateTypeNote" data-value-field="serviceRateTypeNote" data-bind="value:' + options.field + '" style="width: 148px; height: 46px;"/>')
         .appendTo(container);
}

 function serviceRateTypeEditor(container, options){
	 var data = [
		         { text: "Per Transaction", value: "Per Transaction" },
               	{ text: "Per Day",value:"Per Day"},
               	{ text: "Per Hour", value: "Per Hour" },
               	{ text: "Per Paget",value:"Per Page"},
               	{ text: "Actuals",value:"Actuals"},
               	{ text: "Percentage",value:"Percentage"}
               
           ];
		$('<select name="Rate Type" data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoDropDownList({	
			optionLabel : "Select",
			 dataTextField: "text",
		      dataValueField: "value",
		       dataSource: data
		});
		$('<span class="k-invalid-msg" data-for="Rate Type"></span>').appendTo(container);
		}
 function servicePayMethodEditor(container, options){
	 var data = [
		         { text: "CASH", value: "CASH" },
               	{ text: "CHEQUE",value:"CHEQUE"},
               	{ text: "DD", value: "DD" },
               	{ text: "WIRETRANSFER",value:"WIRETRANSFER"},
               
           ];
		$('<select name="Pay Method" data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '" required="true" />')
		.appendTo(container).kendoDropDownList({	
			optionLabel : "Select",
			 dataTextField: "text",
		      dataValueField: "value",
		       dataSource: data
		});
		$('<span class="k-invalid-msg" data-for="Pay Method"></span>').appendTo(container);
		}
 function vendorRateTypeEditor(container, options){
	 var data = [
		         { text: "Per Transaction", value: "Per Transaction" },
               	{ text: "Per Day",value:"Per Day"},
               	{ text: "Per Hour", value: "Per Hour" },
               	{ text: "Per Paget",value:"Per Page"},
               	{ text: "Actuals",value:"Actuals"},
               	{ text: "Percentage",value:"Percentage"}
               
           ];
	 if (options.model.vendorRateType == '') {
		 $('<select name="Vendor Rate Type" data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '" required="true"/>')
			.appendTo(container).kendoDropDownList({	
				optionLabel : "Select",
				 dataTextField: "text",
			      dataValueField: "value",
			       dataSource: data
			});
		 
	 }
	 else{
		$('<select name="Vendor Rate Type" data-text-field="text" disabled data-value-field="value" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoDropDownList({	
			optionLabel : "Select",
			 dataTextField: "text",
		      dataValueField: "value",
		       dataSource: data
		});
	 }
		$('<span class="k-invalid-msg" data-for="Vendor Rate Type"></span>').appendTo(container);
		}
 function vendorPayMethodEditor(container, options){
	 var data = [
		         { text: "CASH", value: "CASH" },
               	{ text: "CHEQUE",value:"CHEQUE"},
               	{ text: "DD", value: "DD" },
               	{ text: "WIRETRANSFER",value:"WIRETRANSFER"},
               
           ];
		$('<select name="Vendor Pay Method" data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '" required="true" />')
		.appendTo(container).kendoDropDownList({	
			optionLabel : "Select",
			 dataTextField: "text",
		      dataValueField: "value",
		       dataSource: data
		});
		$('<span class="k-invalid-msg" data-for="Vendor Pay Method"></span>').appendTo(container);
		}
 
 (function($, kendo) {
		$
				.extend(
						true,
						kendo.ui.validator,
						{
							rules : { // custom rules  
								
								vendorRate : function(input,
										params) {
									if (input.filter("[name='vendorRate']").length
											&& input.val()) {
										if(input.val().length > 20){
											alert("'Vendor Rate' must contain only 20 digit numbers.");
											
										}
										return true;
									}
									return true;
								},
								serviceRate : function(input,
										params) {
									if (input.filter("[name='vendorRate']").length
											&& input.val()) {
										if(input.val().length > 20){
											alert("'Service Rate' must contain only 20 digit numbers.");
											
										}
										return true;
									}
									return true;
								},
							},
							messages : {
								startDateValidation : "Start Date is required",
								
							}
						});

	})(jQuery, kendo);
	
 function onRequestEndCharge(e){
	 if (typeof e.response != 'undefined')
		{
		 if (e.response.status == "VENDORRATEDUPLICATE") {
				errorInfo = "";
				errorInfo = e.response.result.vendorRateDuplicate;
				if (e.type == "create") {
					$("#alertsBox").html("");
						$("#alertsBox").html("Error: Creating Service Charge\n\n : " + errorInfo);
								$("#alertsBox").dialog({
									modal : true,
									buttons : {
										"Close" : function() {
											$(this).dialog("close");
										}
									}
								});
				}
				$('#gridServiceCharges_'+SelectedRowId).data().kendoGrid.dataSource.read();
			}
		 else if (e.response.status == "RATEDUPLICATE") {
				errorInfo = "";
				errorInfo = e.response.result.vendorRateDuplicate;
				if (e.type == "update") {
					$("#alertsBox").html("");
						$("#alertsBox").html("Error: Creating Service Charge\n\n : " + errorInfo);
								$("#alertsBox").dialog({
									modal : true,
									buttons : {
										"Close" : function() {
											$(this).dialog("close");
										}
									}
								});
				}
				$('#gridServiceCharges_'+SelectedRowId).data().kendoGrid.dataSource.read();
			}
		 else if (e.response.status == "EXCEPTION") {
				errorInfo = "";
				errorInfo = e.response.result.exception;
				if (e.type == "destroy") {
					$("#alertsBox").html("");
					$("#alertsBox").html("Error: " + errorInfo);
							$("#alertsBox").dialog({
								modal : true,
								buttons : {
									"Close" : function() {
										$(this).dialog("close");
									}
								}
							});
					
				}
				$('#gridServiceCharges_'+SelectedRowId).data().kendoGrid.dataSource.read();
			}
		 else if (e.type == "create") {
			 
			 $("#alertsBox").html("");
				$("#alertsBox").html("Service Charge is created successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				
				$('#gridServiceCharges_'+SelectedRowId).data().kendoGrid.dataSource.read();
		 }
		 else if (e.type == "update") {
			 
			 $("#alertsBox").html("");
				$("#alertsBox").html("Service Charge is updated successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				
				$('#gridServiceCharges_'+SelectedRowId).data().kendoGrid.dataSource.read();
		 }

		 else if (e.type == "destroy") {
			 
			 $("#alertsBox").html("");
				$("#alertsBox").html("Service Charge is deleted successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				
				$('#gridServiceCharges_'+SelectedRowId).data().kendoGrid.dataSource.read();
		 }
		 
		 
		}
}

</script>
<style>

.ui-dialog input[type="text"], .ui-dialog input[type="password"], .ui-dialog textarea {
    background: none repeat scroll 0 0 white;
    border: 1px solid #ddd;
    box-shadow: 0 0 0 2px #f4f4f4;
    box-sizing: border-box;
    color: #656565;
    display: block;
    font-family: Arial,Helvetica,sans-serif;
    font-size: 17px;
    margin: 10px;
    padding: -4px 8px;
</style>
		
		
