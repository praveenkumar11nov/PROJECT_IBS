<%@include file="/common/taglibs.jsp"%>


<!-- Service Booking url -->
<c:url value="/serviceBooking/read" var="readUrl" />
<c:url value="/serviceBooking/create" var="createUrl" />
<c:url value="/serviceBooking/update" var="updateUrl" />
<c:url value="/serviceBooking/delete" var="destroyUrl" />

<c:url value="/conciergeVendors/commentRate/getOwnerNames" var="ownerNamesUrl" />
<c:url value="/serviceBooking/getVendorService" var="getVendorServiceUrl" />
<c:url value="/serviceBooking/getVendors" var="getVendorsUrl" />
<c:url value="/serviceBooking/getVendorRateType" var="getVendorRateTypeUrl" />
<c:url value="/serviceBooking/getVendorRate" var="getVendorRateUrl" />



<kendo:grid name="serviceBookingGrid" pageable="true" edit="serviceBookingEvent"
		resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true" groupable="true" >
	  <kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" >
		<kendo:grid-pageable-messages itemsPerPage="Services per page" empty="No Services to display" refresh="Refresh all the Services" 
			display="{0} - {1} of {2} Services" first="Go to the first page of Services" last="Go to the last page of Services" next="Go to the Services"
			previous="Go to the previous page of Services"/>
		</kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
		 <kendo:grid-filterable-operators>
		  	<kendo:grid-filterable-operators-string  contains="Contains" eq="Is equal to"/>
		    <kendo:grid-filterable-operators-date lte="Created Before" gte="Created After"/>		
		  	
		 </kendo:grid-filterable-operators>
		</kendo:grid-filterable>
		 <kendo:grid-editable mode="popup"
			confirmation="Are you sure you want to remove this Services?" />
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Book New Service" />
		</kendo:grid-toolbar>
		<kendo:grid-toolbarTemplate>
			<div class="toolbar">
			<a class="k-button k-button-icontext k-grid-add" href="#">
		            <span class="k-icon k-add"></span>
		            Book New Service
	        	</a>
				 <a class='k-button' href='\\#' onclick="clearFilterForBookedService()"><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a> 
			</div>  	
    	</kendo:grid-toolbarTemplate>
		<kendo:grid-columns>
		 <%--  <kendo:grid-column title="Belongs to" field="ownerNames1" hidden="true"  width="100px"/>  --%>
		  <kendo:grid-column title="Owner Name *" field="ownerId" hidden="true" width="120px" />
				 <kendo:grid-column title="Owner/Tenant Name *" field="ownerNames" editor="ownerNamesEditor" width="150px" >
		<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function ownerNameFilter(element) {
								element.kendoAutoComplete({
									dataSource: {
										transport: {
											read: "./serviceBooking/getListForFiltering/"+"OwnerNames"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	   
		</kendo:grid-column> 
		<kendo:grid-column title="Services*" field="vendorServices" editor="serviceEditor" width="120px">
		<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
    					function vendorServicesFilter(element) {
    						element.kendoAutoComplete({
								dataSource: {
									transport: {
										read: "./serviceBooking/getListForFiltering/"+"Services"
									}
								}
							});
				  		}
    					
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	
		</kendo:grid-column>
		<kendo:grid-column title="Concierge Vendors*" field="vendors" editor="vendorsEditor" width="140px">
		<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
    					function vendorServicesFilter(element) {
    						element.kendoAutoComplete({
								dataSource: {
									transport: {
										read: "./serviceBooking/getListForFiltering/"+"Vendors"
									}
								}
							});
				  		}
    					
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	
		</kendo:grid-column>
		<kendo:grid-column title="Vendor Rate Type*" field="rateType" editor="rateTypeEditor" width="140px">
		<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
    					function vendorServicesFilter(element) {
    						element.kendoAutoComplete({
								dataSource: {
									transport: {
										read: "./serviceBooking/getListForFiltering/"+"RateType"
									}
								}
							});
				  		}
    					
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	
		</kendo:grid-column>
		<kendo:grid-column title="Vendor Rate*" field="rate" width="100px">
		</kendo:grid-column>
		<kendo:grid-column title="Booked By" field="bookedBy" filterable="true" width="120px">
		<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function personNameFilter(element) {
								element.kendoAutoComplete({
									dataSource: {
										transport: {
											read: "./serviceBooking/getListForFiltering/"+"BookedBy"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	   
		</kendo:grid-column>
		<kendo:grid-column title="Booking Date *" field="bookingDate" width="120px" format="{0:dd/MM/yyyy}">
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
		<kendo:grid-column title="Comments" field="sbComments" editor="commentsEditor" width="120px" filterable="false"/>
		<kendo:grid-column title="Service Delivered *" field="serviceDelivered" width="130px">
		 <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
    					function serviceDeliverFilter(element) {
							element.kendoAutoComplete({
								placeholder : "Enter Status",
								dataSource: {
									transport: {
										read: "./serviceBooking/getListForFiltering/"+"Status"
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
				</kendo:grid-column-command>
			</kendo:grid-column>
			<kendo:grid-column title=""
				template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Activate#=data.sbId#'>#= data.serviceDelivered == 'No' ? 'Make It As Delivered' : 'Make It As Hold' #</a>"
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
				<kendo:dataSource-schema-model id="sbId">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="sbId" type="number" />
						<kendo:dataSource-schema-model-field name="vscId" type="number">
						 <kendo:dataSource-schema-model-field-validation  />
						 </kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="propertyId" type="number">
						<kendo:dataSource-schema-model-field-validation  />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="ownerId"/>
						<%-- <kendo:dataSource-schema-model-field name="ownerNames">
						<kendo:dataSource-schema-model-field-validation  />
						</kendo:dataSource-schema-model-field> --%>
						<kendo:dataSource-schema-model-field name="bookedBy">
						<kendo:dataSource-schema-model-field-validation  />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="sbComments">
						<kendo:dataSource-schema-model-field-validation  />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="rate">
						<kendo:dataSource-schema-model-field-validation />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="bookingDate" type="date" defaultValue="">
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="serviceDelivered">
						<kendo:dataSource-schema-model-field-validation />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="status" defaultValue="InActive" editable="false">
							 <kendo:dataSource-schema-model-field-validation required="true" />
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
	  
	  <div id="alertsBox" title="Alert"></div>
	  
	  <script>
	  
	  function clearFilterForBookedService()
	  {
	  	  $("form.k-filter-menu button[type='reset']").slice().trigger("click");
	  	  var grid = $("#serviceBookingGrid").data("kendoGrid");
	  	  grid.dataSource.read();
	  	  grid.refresh();
	  }
	  
	  function parse (response) {   
		    $.each(response, function (idx, elem) {
		        if (elem.bookingDate && typeof elem.bookingDate === "string") {
		            elem.bookingDate = kendo.parseDate(elem.bookingDate, "yyyy-MM-ddTHH:mm:ss.fffZ");
		        }        
		       
		    });
		    return response;
		}  
	  /* $("#serviceBookingGrid").on("click", ".k-grid-add", function() {
		
		  if($("#serviceBookingGrid").data("kendoGrid").dataSource.filter()){
	  	        $("form.k-filter-menu button[type='reset']").slice().trigger("click");
	  	      var grid = $("#serviceBookingGrid").data("kendoGrid");
	  	      grid.dataSource.read();
	  	      grid.refresh();
	  	          }
		}); */
	  
	  function serviceBookingEvent(e)
	  {
		   var myDatePicker = $('input[name="bookingDate"]').data("kendoDatePicker");
		  var today = new Date();
		  myDatePicker.min(today);
		  
		  $('[name="serviceName"]').attr("disabled", true);
		  $('[name="rate"]').attr("disabled", true);
		  
		  $(".k-edit-field").each(function () {
		         $(this).find("#temPID").parent().remove();
		      });
		  
		  
		e.container.find(".k-grid-cancel").bind("click", function () {
 	    	var grid = $("#serviceBookingGrid").data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
	    }); 
		   
	  	if (e.model.isNew()) 
	      {
	  		securityCheckForActions("./conciergeManagement/serviceBooking/createButton");
	  		
	  		$('label[for="ownerId"]').hide();
			$('div[data-container-for="ownerId"]').hide();
			$(".k-window-title").text("Book New Service");
			$(".k-grid-update").text("Save");
			$(".k-grid-Activate").hide();
			$('[name="status"]').attr("disabled", true);
			$('.k-edit-field .k-input').first().focus();
			$('[name="status"]').attr("disabled", true);
			
			$('label[for="serviceDelivered"]').hide();
			$('div[data-container-for="serviceDelivered"]').hide();
			
	  		if($("#serviceBookingGrid").data("kendoGrid").dataSource.filter()){
		        $("form.k-filter-menu button[type='reset']").slice().trigger("click");
		      var grid = $("#serviceBookingGrid").data("kendoGrid");
		      grid.dataSource.read();
		      grid.refresh();
		          }
			
			$(".k-grid-update").click(function () {
				 var firstItem = $('#serviceBookingGrid').data().kendoGrid.dataSource.data()[0];
				var comments = firstItem.get('sbComments'); 
				var bookedByPerson = firstItem.get('bookedBy');
				 var csRateType = $("#vscId").data("kendoComboBox");
				 var ownersList = $("#ownerNamesList").data("kendoComboBox");
					if( ownersList.select() == -1 ){
						alert("Error: Please select proper 'Owner Name' from drop down");
						return false;
					}
				 else if( csRateType.select() == -1 ){
					 alert("Error : Please select 'Vendor Rate Type' from dropdown");
					 return false;
					 
				 }
					if( bookedByPerson != "" && bookedByPerson != null ){
						if( bookedByPerson.length >45 ){
							 alert("'Booked By Name' allow only 255 characters");
							return false;
						}	
						}
					if( comments != "" && comments != null ){
					if( comments.length >255 ){
						 alert("'Comment' allow only 255 characters");
						return false;
					}	
					}
					
			 });
	      }
	  	else{
	  		
	  		 $('label[for="ownerId"]').hide();
	 		$('div[data-container-for="ownerId"]').hide();
	 	  	$('label[for="serviceDelivered"]').hide();
	 		$('div[data-container-for="serviceDelivered"]').hide();
	 		
	 		$(".k-grid-Activate").hide();
			$('[name="status"]').attr("disabled", true);
			$('.k-edit-field .k-input').first().focus();
			$('[name="status"]').attr("disabled", true);
	 		
	 	  		$('label[for="serviceDelivered"]').hide();
	 			$('div[data-container-for="serviceDelivered"]').hide();
	 			
	  		securityCheckForActions("./conciergeManagement/serviceBooking/updateButton");
	  		/* if($("#serviceBookingGrid").data("kendoGrid").dataSource.filter()){
		        $("form.k-filter-menu button[type='reset']").slice().trigger("click");
		      var grid = $("#serviceBookingGrid").data("kendoGrid");
		      grid.dataSource.read();
		      grid.refresh();
		          } */
			
	  	var gview = $("#serviceBookingGrid").data("kendoGrid");
			var selectedItem = gview.dataItem(gview.select());
			
			var deliveryStatus = selectedItem.serviceDelivered;
			if( deliveryStatus == "Yes" ){
				var grid = $("#serviceBookingGrid").data("kendoGrid");
				grid.cancelRow();
				$("#alertsBox").html("");
					$("#alertsBox").html("Sorry.. You cannot edit delivered service");
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
			}
			else{
	  	
	  	$(".k-edit-field").each(function () {
	         $(this).find("#temPID").parent().remove();
	      });
		$(".k-window-title").text("Edit Booked Service");
		$(".k-grid-Activate").hide();
		$('[name="status"]').attr("disabled", true);
		$('.k-edit-field .k-input').first().focus();
		
	  	}
			$(".k-grid-update").click(function () {
				 var ownersList = $("#ownerNamesList").data("kendoComboBox");
				 var comments = selectedItem.sbComments; 
				 var bookedByPerson = selectedItem.bookedBy;
					if( ownersList.select() == -1 ){
						alert("Error: Please select proper 'Owner Name' from drop down");
						return false;
					}
					if( bookedByPerson != "" && bookedByPerson != null ){
						if( bookedByPerson.length >45 ){
							 alert("'Booked By Name' allow only 255 characters");
							return false;
						}	
						}
					if( comments != "" && comments != null ){
						if( comments.length > 255 ){
							 alert("'Comment' allow only 255 characters");
							return false;
						}	
						}
					
			 });
	  	 
	  	}
	  }
	  
	  $("#serviceBookingGrid").on("click", ".k-grid-delete", function() {
		  
		  securityCheckForActions("./conciergeManagement/serviceBooking/deleteButton");
			
		});
	  
	  $("#serviceBookingGrid").on("click", "#temPID", function(e) {
			 
 			var button = $(this), enable = button.text() == "Make It As Delivered";
 			var widget = $("#serviceBookingGrid").data("kendoGrid");
 			var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
 			
 			var result=securityCheckForActionsForStatus("./conciergeManagement/serviceBooking/statusButton");   
	 		   if(result=="success"){
 						if (enable) {
 							$.ajax({
 								type : "POST",
 								dataType:"text",
 								url : "./conciergeManagement/serviceBookingStatus/" + dataItem.id + "/Yes",
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
 									button.text('Make It As Hold');
 									$('#serviceBookingGrid').data('kendoGrid').dataSource.read();
 								}
 							});
 						} else {
 							$.ajax({
 								type : "POST",
 								dataType:"text",
 								url : "./conciergeManagement/serviceBookingStatus/" + dataItem.id + "/No",
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
 									button.text('Make It As Delivered');
 									$('#serviceBookingGrid').data('kendoGrid').dataSource.read();
 								}
 							});
 						}
 					}
 		});	
	  function commentsEditor(container, options) 
	  {
	      $('<textarea data-text-field="sbComments" data-value-field="sbComments" data-bind="value:' + options.field + '" style="width: 148px; height: 46px;"/>')
	           .appendTo(container);
	  }
	  function ownerNamesEditor(container,options)
		 {
			 $('<input name="Owner Name" id="ownerNamesList" data-text-field="ownerNames" data-value-field="ownerId" data-bind="value:' + options.field + '" required="true" />')
		     .appendTo(container)
		         .kendoComboBox({	
		        	/*  optionLabel : "Select", */
		        	 filter: "startswith",
		        	 //autoBind: true,
						placeholder:"Select",
						 defaultValue : false,
							sortable : true,
							template : '<table><tr>'
								+ '<td align="left"><span class="k-state-default"><b>#: data.ownerNames #</b></span><br>'
								+ '<span class="k-state-default"><i>#:blockName #</i></span><br>'
								+'<span class="k-state-default"><i>#:propertyNumber #</i></span></td></tr></table>',
							
		         dataSource: {  
		             transport:{
		                 read: "${ownerNamesUrl}"
		             }
		         },
		         placeholder :"Select",
		         select:onChange
		     });
			 /* $('<span class="k-invalid-msg" data-for="Status"></span>').appendTo(container); */

		 }
	  function onChange(e){
		    var dataItem = this.dataItem(e.item.index());
		    var firstItem = $('#serviceBookingGrid').data().kendoGrid.dataSource.data()[0];
		    firstItem.set("bookedBy", dataItem.ownerNames);
		    
		   }
	 
	  function serviceEditor(container, options){
		  if (options.model.vendorServices == null) {
			  
				$(
						'<select name="Service" id="cssId" data-text-field="vendorServices"  data-value-field="vendorServices" data-bind="value:' + options.field + '" required="true"  />')
				.appendTo(container).kendoDropDownList({	
					optionLabel : "Select",
					defaultValue : false,
					sortable : true,
					 width: "300px",
					dataSource: {  
			            transport:{
			                read: "${getVendorServiceUrl}"
			            }
					}
				});
		  }
		  else{
			  $(
					  '<select name="Service" id="cssId" data-text-field="vendorServices" disabled data-value-field="vendorServices" data-bind="value:' + options.field + '" required="true" />')
				.appendTo(container).kendoDropDownList({	
					optionLabel : "Select",
					defaultValue : false,
					sortable : true,
					 width: "300px",
					dataSource: {  
			            transport:{
			                read: "${getVendorServiceUrl}"
			            }
					}
				});
		  }
		  $('<span class="k-invalid-msg" data-for="Vendor Service"></span>').appendTo(container);
				
			}
	function vendorsEditor(container, options){
		
		$('<select name="Vendor Name" id="csvId" data-text-field="vendors" data-value-field="vendors" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoDropDownList({	
			 optionLabel : "Select", 
			/* placeholder:"Select", */
			autoBind:false,
			cascadeFrom : "cssId",
			defaultValue : false,
			sortable : true,
			 width: "300px",
			 select:onSelect,
			dataSource: {  
	            transport:{
	                read: "${getVendorsUrl}"
	            }
			}
		});
		$('<span class="k-invalid-msg" data-for="Vendor Name"></span>').appendTo(container);
		
	}
	function onSelect(e){
		
		var dataItem = this.dataItem(e.item.index());
		var serviceName = dataItem.vendorServices;
		var csvId = dataItem.csvId;
		if( csvId != null ){
		
		var comboBoxDataSource = new kendo.data.DataSource({
            transport: {
                read: {
                    url     : "./serviceBooking/getVendorRateType", // url to remote data source 
                    dataType: "json",
                    type    : 'GET',
                    data:{
                    	serviceName : serviceName,
                    	csvId : csvId
                        }    
                }
            },
            schema   : {
                data: function(result) 
                {
        			return result;
                }
            }
        });
		
		$("#vscId").kendoComboBox({
			  
	         dataSource    : comboBoxDataSource,
	         filter: "startswith",
	         defaultValue : false,
				sortable : true,
	         dataTextField : "rateType",
	         dataValueField: "vscId",
	         template : '<table><tr>'
					+ '<td align="left"><span class="k-state-default"><b>#: rateType #</b></span><br>'
					+ '<span class="k-state-default"><i>#:data.collectionMethod#</i></span><br>'
					+'</td></tr></table>',
	         select:onSelectRateType
	     });
		
		}
		
	}
	
	function rateTypeEditor(container, options){
		$('<select name="Vendor Rate Type" id="vscId" data-text-field="rateType" data-value-field="vscId" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoComboBox({	
			/* optionLabel : "Select", */
			placeholder:"Select",
			defaultValue : false,
			sortable : true,
			 filter: "startswith",
			 width: "300px",
			 dataSource: null
		});
		 $('<span class="k-invalid-msg" data-for="Vendor Rate Type"></span>').appendTo(container); 
		
	}
	
	function onSelectRateType(e){
		var dataItem = this.dataItem(e.item.index());
		var vscId = dataItem.vscId;
		
		$.ajax({
			type : "GET",
			dataType:"text",
			url : '${getVendorRateUrl}/'+vscId,
			dataType : "JSON",
			success : function(response) {
				    var firstItem = $('#serviceBookingGrid').data().kendoGrid.dataSource.data()[0];
				    firstItem.set("rate", response);
				/* $.each(response, function(index, value) {
					res1.push(value);
				}); */
			}
		});
	}

//custom validation
var requiredStartDate = "";
(function($, kendo) {
	$
			.extend(
					true,
					kendo.ui.validator,
					{
						rules : { // custom rules  
									bookingDateMsg : function(input) {
											if ((input.filter("[name='bookingDate']")).length && (input.val() == "")) {
												
												return false;
											}
											return true;
										}, 
						},
						messages : {
							bookingDateMsg:"Booking Date is required"
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
	  function onRequestEnd(e){
			if (typeof e.response != 'undefined')
			{
				 if (e.type == "create") {
					 
					 $("#alertsBox").html("");
						$("#alertsBox").html("Record created successfully ");
						$("#alertsBox").dialog({
							modal : true,
							buttons : {
								"Close" : function() {
									$(this).dialog("close");
								}
							}
						});
						
					//alert("Staff record created successfully");
						var grid = $("#serviceBookingGrid").data("kendoGrid");
						grid.dataSource.read();
						grid.refresh();
				 }
				else if (e.type == "update") {
					 
					 $("#alertsBox").html("");
						$("#alertsBox").html("Service updated successfully");
						$("#alertsBox").dialog({
							modal : true,
							buttons : {
								"Close" : function() {
									$(this).dialog("close");
								}
							}
						});
						
					//alert("Staff record created successfully");
						var grid = $("#serviceBookingGrid").data("kendoGrid");
						grid.dataSource.read();
						grid.refresh();
				 }
				 else if (e.response.status == "EXCEPTION") {
						errorInfo = "";
						errorInfo = e.response.result.exception;
						if (e.type == "destroy") {
							$("#alertsBox").html("");
								$("#alertsBox").html("Error: Deleting record\n\n : " + errorInfo);
										$("#alertsBox").dialog({
											modal : true,
											buttons : {
												"Close" : function() {
													$(this).dialog("close");
												}
											}
										});
						}
						var grid = $("#serviceBookingGrid").data("kendoGrid");
						grid.dataSource.read();
						grid.refresh();
					}
				else if (e.type == "destroy") {
					 
					 $("#alertsBox").html("");
						$("#alertsBox").html("Service deleted successfully");
						$("#alertsBox").dialog({
							modal : true,
							buttons : {
								"Close" : function() {
									$(this).dialog("close");
								}
							}
						});
						
					//alert("Staff record created successfully");
						var grid = $("#serviceBookingGrid").data("kendoGrid");
						grid.dataSource.read();
						grid.refresh();
				 }
			}
	  }
	  </script>
	  <style>
	  /* .k-datepicker span{
   			width: 50%;
		}   */ 
.k-header{
background:white;
}
	 */ /*  .k-dropdown .k-input {
    display: block;
    overflow: hidden;
    text-overflow: ellipsis;
    width: 2000px;
} */
	  </style>