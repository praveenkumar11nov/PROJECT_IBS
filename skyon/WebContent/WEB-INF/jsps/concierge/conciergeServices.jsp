<%@include file="/common/taglibs.jsp"%>

<c:url value="/csService/read" var="readUrl" />
<c:url value="/csService/create" var="createUrl" />
<c:url value="/csService/update" var="updateUrl" />
<c:url value="/csService/delete" var="deleteServiceUrl" />

<c:url value="/csService/getGroupNames" var="getGroupNameUrl" />
<c:url value="/csService/getServiceNames" var="serviceNamesUrl" />

<c:url value="/PatrolTrackPoints/getStatusList" var="getStatusListUrl" />
<c:url value="/csService/getServiceNameList" var="getServiceNameListUrl" />
<c:url value="/csService/getGroupNameList" var="getGroupNameListUrl" />

<script>

var endDate_win;
var serviceId;
var startDate;
var serEndDate;
</script> <!-- padding: 2.177em 0; -->

<!-- for end date -->
<!-- <button type="button" id="open1">Open first Window</button> -->
<div>
  <div id="win1">
  
   <div class="demo-section k-header" id="mainDiv" style="width: 210px;">
                <h4>Select Date</h4>
                <input type="date" id="datePicker" style="width: 200px;"  placeholder="dd/mm/yyyy" required/><br/><br/></div> 
                <div>
                <input type="button" value="Save" onclick="getEndDt()" style="width: 50px; height:20px; font: bold;font-size: unset;margin: auto;background:rgb; color: hsla();"/>
            </div>
</div>  

<!-- for end date -->
<div>

<kendo:grid name="csGrid" pageable="true" edit="ConciergeServiceEvent"
		resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true"
		filterable="false" groupable="true" dataBound="csServiceDataBound">
		
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true">
		<kendo:grid-pageable-messages itemsPerPage="Services per page" empty="No Services to display" refresh="Refresh all the Services" 
			display="{0} - {1} of {2} Services" first="Go to the first page of Services" last="Go to the last page of Services" next="Go to the Services"
			previous="Go to the previous page of Services"/>
					</kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
		 <kendo:grid-filterable-operators>
		  	<kendo:grid-filterable-operators-string eq="Is equal to"/>
		 </kendo:grid-filterable-operators>
			
		</kendo:grid-filterable>
		
		  <kendo:grid-editable mode="popup"
			confirmation="Are you sure you want to remove this Concierge Service?" />
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Concierge Service" />
		</kendo:grid-toolbar>
		<kendo:grid-toolbarTemplate>
			<div class="toolbar">
			<a class="k-button k-button-icontext k-grid-add" href="#">
		            <span class="k-icon k-add"></span>
		            Add Concierge Service
	        	</a>
				 <a class='k-button' href='\\#' onclick="clearFilterConciergeServices()"><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a> 
			</div>  	
    	</kendo:grid-toolbarTemplate>
		<kendo:grid-columns>
		<kendo:grid-column title="Service Name *" field="serviceName" width="120px">
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
		 <kendo:grid-column hidden="true"></kendo:grid-column> 
		<kendo:grid-column title="Service Group Name*" field="serviceGroupName" editor="csGroupNameEditor" width="120px">
		<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function groupNameFilter(element) {
								element.kendoAutoComplete({
									dataSource: {
										transport: {
											read: "${getGroupNameListUrl}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
		</kendo:grid-column>
		<kendo:grid-column hidden="true"/>
		<kendo:grid-column title="Service Start Date *" field="serviceStartDate" width="120px" format="{0:dd/MM/yyyy}" >
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
		<kendo:grid-column title="Service End Date *" field="serviceEndDate" width="120px" format="{0:dd/MM/yyyy}">
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
		<kendo:grid-column title="Service Description*" field="serviceDescription"  editor="descriptionEditor"
				filterable="false"  width="120px" />
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
					<kendo:grid-column-commandItem name="edit" click="edit"/>
						<kendo:grid-column-commandItem name="destroy"/>
						<kendo:grid-column-commandItem name="serviceEndDatee" text="Add/Edit Service End Date">
				 <kendo:grid-column-commandItem-click>
				<script>
				function showDetails(e){
					result=securityCheckForActionsForStatus("./conciergeManagement/conciergeServices/addOrEditEndDate");   
					   if(result=="success"){
						   $("#mainDiv input").val("");
					var dataItem = this.dataItem($(e.currentTarget).closest("tr"));
					serviceId = dataItem.cssId;
					startDate = dataItem.serviceStartDate;
					 serEndDate = dataItem.serviceEndDate;
					var formatedEndDate = $.datepicker.formatDate("dd/mm/yy",serEndDate);
					$("#mainDiv input").val(formatedEndDate);
					endDate_win.dialog( "open" );
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
				<kendo:dataSource pageSize="5" requestEnd="onRequestEndService" requestStart="onRequestStartService">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-read url="${readUrl}" dataType="json"
					type="POST" contentType="application/json" />
				<kendo:dataSource-transport-create url="${createUrl}"
					dataType="json" type="GET" contentType="application/json" />
				<kendo:dataSource-transport-update url="${updateUrl}"
					dataType="json" type="GET" contentType="application/json" />
				<kendo:dataSource-transport-destroy url="${deleteServiceUrl}"
					dataType="json" type="GET" contentType="application/json" />
				<%-- <kendo:dataSource-transport-parameterMap>
					<script>
						function parameterMap(options, type) {
							return JSON.stringify(options);
						}
					</script>
				</kendo:dataSource-transport-parameterMap> --%>
			</kendo:dataSource-transport>
			
			 <kendo:dataSource-schema>
				<kendo:dataSource-schema-model id="cssId">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="cssId" type="number" />
						<kendo:dataSource-schema-model-field name="serviceName">
						<kendo:dataSource-schema-model-field-validation max="50" min="1"/>
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="serviceGroupName">
						<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="serviceStartDate" type="date" defaultValue="">
						<kendo:dataSource-schema-model-field-validation />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="serviceEndDate" type="date" defaultValue="">
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="serviceDescription" type="string" >
							 <kendo:dataSource-schema-model-field-validation required="true" />
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
</div></div>
<div id="alertsBox" title="Alert"></div>

<script>
//for end date
var endDate = ""; 
  endDate_win  = $( "#win1" ).dialog({
autoOpen: false,
height: 250,
width: 300,
modal: true,
title:"Add Service End Date"

 }); 
  function ConciergeServiceEvent(e)
  {
	  
  var myDatePicker = $('input[name="serviceStartDate"]').data("kendoDatePicker");
  var today = new Date();
  myDatePicker.min(today);
  
  }
  $('input[name="serviceName"]').attr('maxlength', '255');
  
 function csServiceDataBound(e){
/*  $("#mainDiv input").val("");
	 $("input[type=date]").kendoDatePicker({
	     open:function(e)
	         {                
	             window.setTimeout(function(){ $(".k-calendar-container").parent(".k-animation-container").css("zIndex", "11000"); }, 1);
	         },
	 change:onChange,
	 format:"MM/dd/yyyy"
	   });  */
 }
 
   $("input[type=date]").kendoDatePicker({
     open:function(e)
         {                
             window.setTimeout(function(){ $(".k-calendar-container").parent(".k-animation-container").css("zIndex", "11000"); }, 1);
         },
 			change:onChange,
 			format:"dd/MM/yyyy"
   });  
 function onChange(e){
	 
	  endDate = this.value();
 }
 
 function getEndDt(){
	 
	 securityCheckForActions("./conciergeManagement/conciergeServices/addOrEditEndDate");
	 
	 	
	  if(endDate == null || endDate == "")
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
			if( endDate < startDate ){
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
			url : "./csService/updateServiceEndDt/"+endDate+"/"+serviceId,
			
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
					endDate_win.dialog( "close" );
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
				 var grid = $("#csGrid").data("kendoGrid");
			      grid.dataSource.read();
			      grid.refresh();
			}
	 });
			}
	 }
	
 }
  
//for end date

var servName = "";
var flag = "";
var res = [];
//var res1 = [];
var res1 = new Array();
//var res1 = {};
var res2 = [];
var enterdService = "";
var existingService = "";

/*======================================= clear filter===============================================  */
  function clearFilterConciergeServices()
 {
	  $("form.k-filter-menu button[type='reset']").slice().trigger("click");
	  var grid = $("#csGrid").data("kendoGrid");
	  grid.dataSource.read();
	  grid.refresh();
}
 


$("#csGrid").on("click", ".k-grid-Clear_Filter", function(){
    //custom actions
    $("form.k-filter-menu button[type='reset']").slice().trigger("click");
	var grid = $("#csGrid").data("kendoGrid");
	grid.dataSource.read();
	grid.refresh();
});
$("#csGrid").on("click", ".k-grid-add", function() {
	
	res1 = [];
	setTimeout(function () {
	    $(".k-edit-field .k-input").first().focus();
	}, 1000);
	
	if($("#csGrid").data("kendoGrid").dataSource.filter()){
        $("form.k-filter-menu button[type='reset']").slice().trigger("click");
      var grid = $("#csGrid").data("kendoGrid");
      grid.dataSource.read();
      grid.refresh();
          }
	$(".k-grid-update").click(function () {
		 var serviceGroupName = "";
		 var firstItem = $('#csGrid').data().kendoGrid.dataSource.data()[0];
		  serviceGroupName = firstItem.get('serviceGroupName'); 
		  var serviceDescription = firstItem.get('serviceDescription'); 
		 // var serviceGroupName = $("#csGropName").data("kendoComboBox");
		 /* if( serviceGroupName == "" ){
			 alert("Error: Service Grop Name Is Required");
			 
		 } */
		 if( serviceGroupName != "" ){
			 var result = serviceGroupName.match(/^[a-zA-Z]+[ _a-zA-Z0-9_]*[a-zA-Z0-9]$/);
			 if( !result ){
				 alert("Error: Service Group Name can not allow special symbols except underscore(_) ");
				 return false;
			 }
		 }
		 if( serviceDescription.length > 255 ){
			 alert("'Service Description' allow only 255 characters");
			 return false;
		 }
		 
	});
	
	securityCheckForActions("./conciergeManagement/conciergeServices/createButton");
	
	 $.ajax({
		type : "GET",
		dataType:"text",
		url : '${serviceNamesUrl}',
		dataType : "JSON",
		success : function(response) {
			/* $.each(response, function(index, value) {
				res1 = res1+value+','; */
				//res1.push(value);
			 for(var i = 0; i<response.length; i++) {
				res1[i] = response[i];
			
			} 
			//});
			
		}
	}); 
	
	
	$('label[for="serviceEndDate"]').parent().remove();
	$('div[data-container-for="serviceEndDate"]').remove();
	
	$('label[for="status"]').closest('.k-edit-label').hide();
		$('div[data-container-for="status"]').hide(); 
	
	$(".k-grid-Activate").hide();
	$('[name="status"]').attr("disabled", true);
	$(".k-window-title").text("Add Concierge Service");
	$(".k-grid-update").text("Save");
	$(".k-edit-field").each(function () {
        $(this).find("#temPID").parent().remove();
     });

	
});

function edit(e) {
	
	securityCheckForActions("./conciergeManagement/conciergeServices/updateButton");
	/* if($("#csGrid").data("kendoGrid").dataSource.filter()){
        //$("#grid").data("kendoGrid").dataSource.filter({});
         //alert("Clearing Filter");
        $("form.k-filter-menu button[type='reset']").slice().trigger("click");
      var grid = $("#csGrid").data("kendoGrid");
      grid.dataSource.read();
      grid.refresh();
          } */
	
	$('label[for="serviceEndDate"]').parent().hide();
	$('div[data-container-for="serviceEndDate"]').hide();
	$('label[for="status"]').closest('.k-edit-label').hide();
		$('div[data-container-for="status"]').hide(); 
	$(".k-window-title").text("Edit Concierge Service");
	$('[name="serviceName"]').attr("readonly", true);
	$(".k-edit-field").each(function () {
        $(this).find("#temPID").parent().remove();
     });
	 var gview = $("#csGrid").data("kendoGrid");
	  //Getting selected item
	var selectedItem = gview.dataItem(gview.select());
  $(".k-grid-update").click(function () {
		 var csGroupName = "";
		  csGroupName = selectedItem.serviceGroupName; 
		  var serviceDescription = selectedItem.serviceDescription; 
		 // var serviceGroupName = $("#csGropName").data("kendoComboBox");
		  if( csGroupName != "" ){
			 var result1 = csGroupName.match(/^[a-zA-Z]+[ _a-zA-Z0-9_]*[a-zA-Z0-9]$/);
			 if( !result1 ){
				 alert("Error: Service Group Name can not allow special symbols except underscore(_) ");
				 return false;
			 }
		 }
		  if( serviceDescription.length > 255 ){
				 alert("'Service Description' allow only 255 characters");
				 return false;
			 }
	}); 

}
$("#csGrid").on("click", ".k-grid-delete", function(e) {
	
	securityCheckForActions("./conciergeManagement/conciergeServices/deleteButton");
	
	var widget = $("#csGrid").data("kendoGrid");
	var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
	if( dataItem.status == 'Active' ){
		 var grid = $("#csGrid").data("kendoGrid");
 	     grid.cancelRow();
 	     $("#alertsBox").html("");
 	     $("#alertsBox").html("Error: You cannot delete 'Active' Concierge Service");
 	     $("#alertsBox").dialog({
 	      modal : true,
 	      buttons : {
 	       "Close" : function() {
 	        $(this).dialog("close");
 	       }
 	      }
 	     });
 	     var grid = $("#csGrid").data("kendoGrid");
 	     grid.cancelChanges();
		
	}
	else{
	}
	
});
function csGroupNameEditor( container, options ){
	 
	 $(
	'<input name="Service Group Name" data-text-field="serviceGroupName" id="csGropName" data-value-field="serviceGroupName" data-bind="value:' + options.field + '" required="true"/>')
	.appendTo(container)
		.kendoComboBox({
			 placeholder:"Select",
         dataSource: {  
             transport:{
                 read: "${getGroupNameUrl}"
             }
         }
	 });
	   /* $('<span class="k-invalid-msg" data-for="This field"></span>').appendTo(container);  */
}

function descriptionEditor(container, options) 
{
    $('<textarea data-text-field="serviceDescription" data-value-field="serviceDescription" data-bind="value:' + options.field + '" style="width: 148px; height: 46px;" placeholder=" " required validationmessage="Description is Required"/>')
         .appendTo(container);
   /*  $('<span class="k-invalid-msg" data-for="ssss"></span>').appendTo(container);  */
}
$("#csGrid").on("click", "#temPID", function(e) {
	
		var button = $(this), enable = button.text() == "Activate";
		var widget = $("#csGrid").data("kendoGrid");
		var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
		
		//var dataItem = this.dataItem($(e.currentTarget).closest("tr"));
		if(  button.text() == "Activate" ){
		 	serEndDate = dataItem.serviceEndDate;
			var dt = $('input[name="serviceStartDate"]').val();
			if(new Date > serEndDate){
				
				$("#alertsBox").html("");
				$("#alertsBox").html("This service date is expired.You cannot activate");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				
				$('#csGrid').data('kendoGrid').dataSource.read();
				/* alert("This service date is expired.You cannot activate"); */
				return false;
			}
		}
		result=securityCheckForActionsForStatus("./conciergeManagement/conciergeServices/statusButton");   
		   if(result=="success"){
		if (enable) {
				$.ajax({
					type : "POST",
					dataType:"text",
					url : "./conciergeManagement/csServiceStatus/" + dataItem.id + "/activate",
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
						$('#csGrid').data('kendoGrid').dataSource.read();
					}
				});
			} else {
				$.ajax({
					type : "POST",
					dataType:"text",
					url : "./conciergeManagement/csServiceStatus/" + dataItem.id + "/deactivate",
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
						$('#csGrid').data('kendoGrid').dataSource.read();
					}
				});
			}
		}
		
});
//custom validation
var requiredStartDate = "";
(function($, kendo) {
	$
			.extend(
					true,
					kendo.ui.validator,
					{
						rules : { // custom rules  
							
							serviceNamevalidation: function (input, params) 
				             {               	 
				                // check for the name attribute 
				                 if (input.filter("[name='serviceName']").length && input.val()) 
				                 {
				                	 servName = input.val();
				                	$.each(res, function( index, value ) 
									{	
				          				if((servName == value))
										{
											flag = input.val();								
				          				}  
				          			}); 
				                	//return /^[a-zA-Z]{1,10}$/.test(input.val());
				                	return /^[a-zA-Z]+[ _a-zA-Z0-9_]*[a-zA-Z0-9]$/.test(input.val());
				                	/* return /^[a-zA-Z]*[ a-zA-Z][_]{0,1}[a-zA-Z]*[^_]$/.test(input.val()) */
				                 }        
				                 return true;
				             },
				              serviceNameLengthValidation: function (input, params) 
				             {               	 
				                // check for the name attribute 
				                if ( (input.filter("[name='serviceName']").length) && (input.val() == servName)) 
				                 {
				                	if(input.val().length > 50){
				                		return false;
				                	}
				                // return false;
				                 }
				                return true;
				             }, 
				             serviceNameUniqueness : function(input,params) {
									//check for the name attribute 
									if (input.filter("[name='serviceName']").length && input.val()) {
										enterdService = input.val().toUpperCase();	
										for(var i = 0; i<res1.length; i++) {
											if ((enterdService == res1[i].toUpperCase()) && (enterdService.length == res1[i].length) ) {
												//flag = enterdService;
												return false;
										
										}
										}
										/* $.each(res1,function(ind, val) {
													if ((enterdService == val.toUpperCase()) && (enterdService.length == val.length)) {
													flag = enterdService;
													return false;
											}
										}); */
									}
									return true;
							},
						serNameUniqueness : function(input) {
									if (input.filter("[name='serviceName']").length && input.val() && flag != "") {
										flag = "";
										return false;
									}
									return true;
							},  
							 startDateMsg: function (input, params) 
				             {
			                     if (input.filter("[name = 'serviceStartDate']").length && input.val() != "") 
			                     {                          
			                         var selectedDate = input.val();
			                         requiredStartDate = selectedDate;

			                        
			                     }
			                     return true;
			                 },
			                 /* endDateMsg: function (input, params) 
				             {
			                     if (input.filter("[name = 'serviceEndDate']").length && input.val() != "") 
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
			                 } , */
			                 startDateValidation : function(input) {
									if ((input.filter("[name='serviceStartDate']")).length && (input.val() == "")) {
										
										return false;
									}
									return true;
								}, 
								csServiceNameValidation : function(input) {
								           if (input.attr("name") == "serviceName")
								           {
								            return $.trim(input.val()) !== "";
								           }
								           return true;
								          },
									/* if (input.filter("[name='serviceName']").length && (input.val() == "")) {
										return false;
									}
									return true; 
							},*/
								/* endDateValidation : function(input) {
										if ((input.filter("[name='serviceEndDate']")).length && (input.val() == "")) {
											
											return false;
										}
										return true;
									}, */
						},
						messages : {
							startDateValidation : "Start Date is required",
							serviceNameLengthValidation : "Service Name allow max 50 characters only",
							csServiceNameValidation : "Service Name is required",
							/* endDateValidation : "This field is required",  */
							serNameUniqueness: " Service Name already exists, please try with some other name ",
				        	serviceNameUniqueness: " Service Name already exists, please try with some other name ",
							/* endDateMsg:"Start date should be greater than or equal to End date", */
							serviceNamevalidation: " Service Name can not allow special symbols except underscore(_) ",
						}
					});

})(jQuery, kendo);
//End Of Validation
function onRequestStartService(e){
	
	$('.k-grid-update').hide();
    $('.k-edit-buttons')
            .append(
                    '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
    $('.k-grid-cancel').hide();
    
	/* var grid = $("#csGrid").data("kendoGrid");
	grid.cancelRow(); */
}
function onRequestEndService(e){
		if (typeof e.response != 'undefined')
	{
			 if (e.response.status == "CREATEFAIL") 
				{

					errorInfo = "";
					for (i = 0; i < e.response.result.length; i++) {
						errorInfo += (i + 1) + ". "
								+ e.response.result[i].defaultMessage + "<br>";

					}

					if (e.type == "create") {
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
					var grid = $("#csGrid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
				}
			 else if (e.response.status == "UPDATEFAIL") 
				{

					errorInfo = "";
					for (i = 0; i < e.response.result.length; i++) {
						errorInfo += (i + 1) + ". "
								+ e.response.result[i].defaultMessage + "<br>";

					}

					if (e.type == "update") {
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
					var grid = $("#csGrid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
				}
			 else if (e.type == "create") {
				 $("#alertsBox").html("");
					$("#alertsBox").html("Service record is created successfully");
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
					
					var grid = $("#csGrid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
			 } 
			 else if (e.type == "update") {
				 $("#alertsBox").html("");
					$("#alertsBox").html("Service record is updated successfully");
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
					
					var grid = $("#csGrid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
			 } 
			 else if (e.response.status == "EXCEPTION") {
					errorInfo = "";
					errorInfo = e.response.result.exception;
					if (e.type == "destroy") {
						$("#alertsBox").html("");
							$("#alertsBox").html("Error: Deleting the Service record\n\n : " + errorInfo);
									$("#alertsBox").dialog({
										modal : true,
										buttons : {
											"Close" : function() {
												$(this).dialog("close");
											}
										}
									});
					}
					var grid = $("#csGrid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
				}
			 else if (e.type == "destroy") {
				 $("#alertsBox").html("");
					$("#alertsBox").html("Service record deleted successfully");
							$("#alertsBox").dialog({
								modal : true,
								buttons : {
									"Close" : function() {
										$(this).dialog("close");
									}
								}
							});
					
							var grid = $("#csGrid").data("kendoGrid");
							grid.dataSource.read();
							grid.refresh();
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
 <style>
/*  ui-widget ui-widget-content ui-corner-all ui-draggable ui-resizable */
	div ui-widget {
	
	  position:fixed;overflow:"auto";  
	
	} 
</style>