<%@include file="/common/taglibs.jsp"%>

<c:url value="/parkingslotsallocationdetails/create" var="createUrl" />
	<c:url value="/parkingslotsallocationdetails/read" var="readUrl" />
	<c:url value="/parkingslotsallocationdetails/update" var="updateUrl" />
	<c:url value="/parkingslotsallocationdetails/destroy" var="destroyUrl" />
	<c:url value="/parkingslotsallocationdetails/getParkingSlots" var="parkingSlotUrl" />
	<c:url value="/parkingslotsallocationdetails/getPropertyNames" var="propertyUrl" />
	<c:url value="/parkingslotsallocationdetails/getOwnerNames" var="getOwnername" />
	<c:url value="/parkingslotsallocationdetails/getBlocksinAllocation" var="blockNameUrl" />
	<c:url value="/parkingslotsallocationdetails/getBlocksForFilterAllocation" var="BlockNameFilterUrl" />
	<c:url value="/parkingslotsallocationdetails/getSlotForFilterAllocation" var="SlotNameFilterUrl" />
	<c:url value="/parkingslotsallocationdetails/getPropertyForFilter" var="PropertyFilterUrl" />
	<c:url value="/parkingslotsallocationdetails/getRentForFilter" var="RentFilterUrl" />
	
	
	<kendo:grid name="grid" pageable="true" resizable="true" groupable="true" sortable="true"  filterable="true" scrollable="true" height="430px" selectable="true">
		<kendo:grid-editable mode="popup" confirmation="You Want To Delete The Record?"/>
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Parking Slots Allocation Details" />
			<kendo:grid-toolbarItem name="parkingAllocationTemplatesDetailsExport" text="Export To Excel" /> 
            		  <kendo:grid-toolbarItem name="parkingAllocationPdfTemplatesDetailsExport" text="Export To PDF" /> 
			<kendo:grid-toolbarItem text="Clear Filter" name="Clear_Filter"/>
		</kendo:grid-toolbar>
		
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" ></kendo:grid-pageable>
		
		<kendo:grid-filterable extra="false">
		 <kendo:grid-filterable-operators>
		  	<kendo:grid-filterable-operators-string eq="Is equal to"/>
		  	<kendo:grid-filterable-operators-date lte="Alloted Before" gte="Alloted After"/>
		 </kendo:grid-filterable-operators>
			
		</kendo:grid-filterable>
		
		<kendo:grid-columns>
			<kendo:grid-column title="Block Name" field="block"  width="120px" editor="blockNameDropDownEditor">
			 <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockFilter(element) {								
								element.kendoAutoComplete({
									dataSource: {
										transport: {
											read: "${BlockNameFilterUrl}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	      	   
      	   </kendo:grid-column>	
      	   		
			<kendo:grid-column title="Parking Slots" field="parkingSlotsNo" width="130px" editor="parkingSlotEditor">
			 <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function psSlotNoFilter(element) {
								element.kendoAutoComplete({
								dataSource : {
										transport : {
											read : "${SlotNameFilterUrl}",
										}
									},
								});							
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	      	   
      	   </kendo:grid-column>	
      	   
			<kendo:grid-column title="Property Number" field="property" width="130px" editor="propertyDropDownEditor">
			 <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function psOwnershipFilter(element) {
								element.kendoAutoComplete({
								dataSource : {
										transport : {
											read : "${PropertyFilterUrl}",
										}
									},
								});							
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	      	   
      	   </kendo:grid-column>	
      	   
			<kendo:grid-column title="No Of Slots Allowed" field="slotallowed" hidden="true" width="100px" />
			
			<kendo:grid-column title="Allotment Date From" field="allotmentDateFrom" width="135px" format= "{0:dd/MM/yyyy}" >
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
	    					function fromDateFilter(element) {
								element.kendoDatePicker({
									format:"dd/MM/yyyy",
					            	
				            	});
					  		}    					
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	
	    		</kendo:grid-column>
			<kendo:grid-column title="Allotment Date To" field="allotmentDateTo" width="130px" format= "{0:dd/MM/yyyy}" >
		<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
    					function toDateFilter(element) {
							element.kendoDatePicker({
								format:"dd/MM/yyyy",
				            	
			            });
				  		}
    					
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	
	    		</kendo:grid-column>
			<kendo:grid-column title="Rent Rate" field="psRent" width="90px" >
			 <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function psOwnershipFilter(element) {
								element.kendoAutoComplete({
								dataSource : {
										transport : {
											read : "${RentFilterUrl}",
										}
									},
								});							
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	      	   
      	   </kendo:grid-column>	
      	   
			<kendo:grid-column title="Rent Last Revised" field="psRentLastRevised" width="135px" format= "{0:dd/MM/yyyy}" />
			<kendo:grid-column title="Rent Last Raised" field="psRentLastRaised" width="130px" format= "{0:dd/MM/yyyy}"/>
				
			<kendo:grid-column title=""	template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Activate#=data.psId#'>#= data.status == true ? 'De-Allocate' : 'Allocate' #</a>" width="100px" />
	
		<kendo:grid-column title="&nbsp;" width="100px">
			<kendo:grid-column-command>

			<kendo:grid-column-commandItem name="destroy"  text="Delete"/>

			</kendo:grid-column-command>
		</kendo:grid-column>			  
				
		</kendo:grid-columns>
		<kendo:dataSource pageSize="20" requestEnd="onRequestEnd" requestStart="OnRequestStart">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-create url="${createUrl}" dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-update url="${updateUrl}" dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-destroy url="${destroyUrl}" dataType="json" type="POST" contentType="application/json" />
				
				<kendo:dataSource-transport-parameterMap>
					<script>
						function parameterMap(options, type) {
							return JSON.stringify(options);
						}
					</script>
				</kendo:dataSource-transport-parameterMap>
			</kendo:dataSource-transport>
			
			<kendo:dataSource-schema parse="parse">
				<kendo:dataSource-schema-model id="psaId">
					<kendo:dataSource-schema-model-fields>
					
						<kendo:dataSource-schema-model-field name="property"/>
						
						<kendo:dataSource-schema-model-field name="slotallowed"/>
						
						<kendo:dataSource-schema-model-field name="block"/>	
						
						<kendo:dataSource-schema-model-field name="parkingSlotsNo"/>						
												
						<kendo:dataSource-schema-model-field name="allotmentDateFrom" type="date">
							<kendo:dataSource-schema-model-field-validation required="true"/>
						</kendo:dataSource-schema-model-field>									
						
						<kendo:dataSource-schema-model-field name="allotmentDateTo" type="date" defaultValue=""/>
						
						<kendo:dataSource-schema-model-field name="psRent" type="string"/>
						
						<kendo:dataSource-schema-model-field name="psRentLastRevised" type="date" defaultValue=""/>
						
						<kendo:dataSource-schema-model-field name="psRentLastRaised" type="date"/>
							
						<kendo:dataSource-schema-model-field name="status" editable="false" defaultValue="false" type="boolean" />
												
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
	</kendo:grid>	
		
	<div id="alertsBox" title="Alert"></div>	
	<div id="dialogBox" title="Property Details"></div>	
	<script type="text/javascript">	
	
	$("#grid").on("click",".k-grid-parkingAllocationTemplatesDetailsExport", function(e) {
		  window.open("./parkingAllocationTemplate/parkingAllocationTemplatesDetailsExport");
	  });
	  
	  $("#grid").on("click",".k-grid-parkingAllocationPdfTemplatesDetailsExport", function(e) {
		  window.open("./parkingAllocationPdfTemplate/parkingAllocationPdfTemplatesDetailsExport");
	  });
	  
	 
	function parse (response) {   
	    $.each(response, function (idx, elem) {
	    	if(elem==null){
	    		alert(idx+" null");
	    	}
            if (elem.allotmentDateFrom && typeof elem.allotmentDateFrom === "string") {
                elem.allotmentDateFrom = kendo.parseDate(elem.allotmentDateFrom, "yyyy-MM-ddTHH:mm:ss.fffZ");
            }
            if (elem.allotmentDateTo && typeof elem.allotmentDateTo === "string") {
                elem.allotmentDateTo = kendo.parseDate(elem.allotmentDateTo, "yyyy-MM-ddTHH:mm:ss.fffZ");
            }
            if (elem.psRentLastRevised && typeof elem.psRentLastRevised === "string") {
                elem.psRentLastRevised = kendo.parseDate(elem.psRentLastRevised, "yyyy-MM-ddTHH:mm:ss.fffZ");
            }
            if (elem.psRentLastRaised && typeof elem.psRentLastRaised === "string") {
                elem.psRentLastRaised = kendo.parseDate(elem.psRentLastRaised, "yyyy-MM-ddTHH:mm:ss.fffZ");
            }
        });
        return response;
	}  
		
	//register custom validation rules
    (function ($, kendo) {
        $.extend(true, kendo.ui.validator, {
             rules: { // custom rules
            	 psRent: function (input, params) {
                     //check for the name attribute 
                     if (input.filter("[name='psRent']").length && input.val()) {
                         return /^[0-9]+$/.test(input.val());
                     }
                     return true;
                 }
             },
             messages: { //custom rules messages
            	 psRent: "Only Digits Are Allowed"
             }
        });
    })(jQuery, kendo);

	
	$("#grid").on("click", ".k-grid-Clear_Filter", function(){
	    $("form.k-filter-menu button[type='reset']").slice().trigger("click");
		var grid = $("#grid").data("kendoGrid");
		grid.dataSource.read();
		grid.refresh();
	});		
	 
	function blockNameDropDownEditor(container, options) {
		$('<input data-text-field="block" id="blockdrop" data-value-field="blockId" data-bind="value:' + options.field + '"/>') 
				.appendTo(container).kendoComboBox({
				placeholder : "Select Blocks",
				filter:"startswith",
				change:onchangeBlocks,
				dataSource : {
						transport : {
							read : "${blockNameUrl}",
						}
					},
				});
	}	
	function onchangeBlocks(){
		var blockId=$("#blockdrop").data("kendoComboBox").value();
		$.ajax({
			type : "POST",
			url : "./parkingslotsallocationdetails/getParkingSlotsSize/"+blockId,
			success : function(response) {
				if(response=="0"){
					alert("There Is No Slots Available for This:"+$("#blockdrop").data("kendoComboBox").text()+"\n\n May Be All Slots Allotted or No Slots Created for this Block");
				} 
			}
		});		
	}  
	function parkingSlotEditor(container, options) {
		$('<input data-text-field="parkingSlotsNo" id="slot" data-value-field="psId" data-bind="value:' + options.field + '"/>') 
				.appendTo(container).kendoComboBox({
				placeholder : "Select Parking Slots",
				filter:"startswith",
				cascadeFrom: "blockdrop",				
				template : '<table><tr><td style="padding: 0px;"><span class="k-state-default"><b>#: data.parkingSlotsNo #</b></span><br></td></tr>'
						+'<tr><td style="padding: 0px;font-size: 10px;"><span class="k-state-default"><i>#: data.parkingMethod #</i></span></td></tr></table>',
				dataSource : {
						transport : {
							read : "${parkingSlotUrl}",
						}
					},
				change: onParkingSlot
				});
	}		
	
	function onParkingSlot(e){		
		var val= $("#slot").val();	
		$.ajax({
			type : "POST",
			url : "./parkingslotsAllocation/getOwnership/"+val,
			success : function(response) {
				 if(response=="RENTED"){
					 $('label[for="psRentLastRevised"]').parent().show();
					 $('label[for="psRent"]').parent().show();
					 $('input[name="psRent"]').parent().show();
					 $('div[data-container-for="psRentLastRevised"]').show();					 
				 }else{
					 $('label[for="psRentLastRevised"]').parent().hide();
					 $('label[for="psRent"]').parent().hide();
					 $('input[name="psRent"]').parent().hide();
					 $('div[data-container-for="psRentLastRevised"]').hide();					 
				 }
			}
		});
	 } 
	
	 	
 	function propertyDropDownEditor(container, options) {
		$('<input data-text-field="property" id="propertydrop" data-value-field="propertyId" data-bind="value:' + options.field + '" />')
				.appendTo(container).kendoComboBox({
					placeholder:"Select Property",					
					filter:"startswith",   
					change:onChangeproperty,
	    			dataSource : {
						transport : {
							read : "${propertyUrl}"
						}
					},							
				});						
	}	 	
 	
 	function onChangeproperty(e){ 	
 		
 		var value = this.dataItem();
 		var ownertype=[];
 		var ownerResident=[];
 		var person=[];
 		var persontype=[];
 		
 		ownertype=value.ownertype.split(":");
 		person=value.person.split(":");
 		ownerResident=value.ownerResident.split(":"); 			
 		persontype=value.persontype.split(":"); 			
 		
 		if(person.length>1){
 			contactList ="<table><tr><td style='padding:10px;vertical-align:'top';>NAME</td><td style='padding:10px;vertical-align: 'top';>PRIMARY OWNER</td><td style='padding:10px;vertical-align: 'top';>RESIDENT</td><td style='padding:10px;vertical-align: 'top';>OWNER/TENANT</td></tr>";
 	 		for(var i=0;i<person.length;i++){
 	 			contactList+="<tr><td style='padding:10px;vertical-align:'top';>"+person[i]+"</td><td align='center' style='padding:10px;vertical-align: 'top';>"+ownertype[i]+"</td><td align='center' style='padding:10px;vertical-align: 'top';>"+ownerResident[i]+"</td><td align='center' style='padding:10px;vertical-align: 'top';>"+persontype[i]+"</td></tr>"
 	 		}
 	 		contactList+="</table>"; 	 		
 	 		$("#dialogBox").html("");
			$("#dialogBox").html(contactList);
			$("#dialogBox").dialog({
				modal : true,
				draggable: false,
				resizable: true,
				zIndex:100000,
				width:400,					
				buttons : {
					"Close" : function() {
						$(this).dialog("close");
					}
				}
			});				
 					
 		}else{
 			var h="No Owner OR Tenants Found";
 			$("#dialogBox").html("");
			$("#dialogBox").html(h);
			$("#dialogBox").dialog({
				modal : true,
				draggable: false,
				resizable: true,
				zIndex:100000,
				width:400,					
				buttons : {
					"Close" : function() {
						$(this).dialog("close");
					}
				}
		});		
 		} 		
		
    	var firstItem = $('#grid').data().kendoGrid.dataSource.data()[0];
    	var vehReg="vegReg="+firstItem.get('property');
 		  $.ajax({
 			 type : "POST",
   			 url : "./parkingSlots/getNumberParkingSlots",
   			 data : vehReg,
   			 dataType : 'text',
   			 success : function(response) {
   				$('input[name="slotallowed"]').val(response);	  
   				$('input[name="slotallowed"]').change(); 
   				$('input[name="slotallowed"]').attr('readonly', 'readonly');   				
   			 } 		  	  		  
 	       });
    }
 	
 	function checkExist(psId){
   		var result=null;
			$.ajax({
	 			type : "GET",
	 			async: false,
	 			url : './parkingslotsAllocation/checkfixed/'+psId,		 			
	 			success : function(response) {		 				
	 				result=response;
	 			}
	 		});
			return result;
		}
 	
 	$("#grid").on("click", ".k-grid-add", function() {
 		securityCheckForActions("./parkingManagement/parkingslotsAllocation/createButton");
 		
 		if($("#grid").data("kendoGrid").dataSource.filter()){
    		//$("#grid").data("kendoGrid").dataSource.filter({});
    		 $("form.k-filter-menu button[type='reset']").slice().trigger("click");
    			var grid = $("#grid").data("kendoGrid");
    			grid.dataSource.read();
    			grid.refresh();
        }
 		$('input[name="allotmentDateFrom"]').kendoDatePicker().data("kendoDatePicker").min(new Date());
 	    $('input[name="allotmentDateTo"]').kendoDatePicker().data("kendoDatePicker");
 		 $('label[for="parkingSlotsNo"]').after('<label style=color:red;>&nbsp;*</label>'); 	     	
 	     $('label[for="block"]').after('<label style=color:red;>&nbsp;*</label>');	
 	     $('label[for="property"]').after('<label style=color:red;>&nbsp;*</label>');	
 	     $('label[for="allotmentDateFrom"]').after('<label style=color:red;>&nbsp;*</label>');	
 	    
		 $(".k-window-title").text("Add Parking Slot Allocation Details");
		 $('.k-edit-field .k-input').first().focus();  
		 $(".k-grid-update").text("Create");
		 
		 $(".k-edit-field").each(function () {
	   		   $(this).find("#temPID").parent().remove();
	     }); 		 
		
		 $('label[for="psRentLastRaised"]').parent().remove();
		 $('label[for="undefined"]').parent().remove();
		 $('div[data-container-for="psRentLastRaised"]').remove();			 
		 
		 $('label[for="psRent"]').parent().hide();
		 $('input[name="psRent"]').parent().hide();
		 
		 $('div[data-container-for="psRentLastRevised"]').hide();			 
		 $('label[for="psRentLastRevised"]').parent().hide();
		
		 $(".k-grid-update").click(function () {			 
		      var firstItem = $('#grid').data().kendoGrid.dataSource.data()[0];		   
		   	  var slot = $("#slot").data("kendoComboBox");
	          var propertydrop=$("#propertydrop").data("kendoComboBox");
	 		  var blockdrop=$("#blockdrop").data("kendoComboBox");
	 		   
	 		  
      		  if(checkExist(slot.value())=="Exists"){
      			  alert("Slot Type is Fixed,And Already Assigned");
      			  return false;
      		  }
      		  
	 		   if (blockdrop.select() == -1) {
	            alert("Please Select Proper Block");
				return false;	
		  	   }
	 		   if (slot.select() == -1) {
		            alert("Please Select Proper Slot");
					return false;	
			   }  		  
	 		   if(propertydrop.select()==-1){
	 			   alert("Please Select The Property");
	 			   return false;
			   }if(firstItem.get('allotmentDateTo')!="" && firstItem.get('allotmentDateTo')!=null ){
				   if(firstItem.get('allotmentDateTo')<=firstItem.get('allotmentDateFrom')){
	  			   alert("To Date Should Be Greater than From Date");
	  			   return false; 				   
				   }
			   }if(firstItem.get('slotallowed')==0){
				   alert("No More Slot Allowed For this Property");
				   return false;
			   } 		   
				
	        });		  
	 }); 
 	
 	 $("#grid").on("click", ".k-grid-delete", function() {
 		securityCheckForActions("./parkingManagement/parkingslotsallocation/deleteButton");
 	}); 	
 	 
 	$("#grid").on("click", "#temPID", function(e) {
			var button = $(this), enable = button.text() == "Allocate";
			var widget = $("#grid").data("kendoGrid");
			var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
			var selectedItem = widget.dataItem(widget.select());
			
			var result=securityCheckForActionsForStatus("./parkingManagement/parkingslotsAllocation/statusButton");   
		    if(result=="success"){
		    	if (enable) {
					$.ajax({
						type : "POST",
						url : "./parkingslotsAllocation/ParkingSlotAllocationStatus/" +selectedItem.psId+"/"+ dataItem.id + "/allocate",
						dataType : 'text',
						success : function(response) {
							$("#alertsBox").html("");
							$("#alertsBox").html(response);
							$("#alertsBox").dialog({
								modal : true,
								draggable: false,
								resizable: false,
								buttons : {
									"Close" : function() {
										$(this).dialog("close");
									}
								}
							});
							button.text('DeAllocate');
							$('#grid').data('kendoGrid').dataSource.read();
						}
					});
				} else {
					$.ajax({
						type : "POST",
						url : "./parkingslotsAllocation/ParkingSlotAllocationStatus/" +selectedItem.psId+"/"+ dataItem.id + "/deallocate",
						dataType : 'text',
						success : function(response) {
							$("#alertsBox").html("");
							$("#alertsBox").html(response);
							$("#alertsBox").dialog({
								modal : true,
								draggable: false,
								resizable: false,
								buttons : {
									"Close" : function() {
										$(this).dialog("close");
									}
								}
							});
							button.text('Allocate');
							$('#grid').data('kendoGrid').dataSource.read();
						}
					});
				}
		    }			
	});	
		
	function OnRequestStart(e){
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();
	} 
	function onRequestEnd(e) {	
			
		 if (typeof e.response != 'undefined')
		 {
						 
				if (e.response.status == "FAIL") {
					errorInfo = "";
					for (var i = 0; i < e.response.result.length; i++) {
						errorInfo += (i + 1) + ". "
								+ e.response.result[i].defaultMessage;
				     }
		
					if (e.type == "create") {
						$("#alertsBox").html("");
						$("#alertsBox").html(errorInfo);
						$("#alertsBox").dialog({
							modal : true,
							draggable: false,
							resizable: false,
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
					return false;
				}
				if (e.response.status == "invalid") {
					errorInfo = "";
					errorInfo = e.response.result.invalid;
					for (var i = 0; i < e.response.result.length; i++) {
						errorInfo += (i + 1) + ". "
								+ e.response.result[i].defaultMessage;
					}
		
					if (e.type == "create") {
						$("#alertsBox").html("");
						$("#alertsBox").html(errorInfo);
						$("#alertsBox").dialog({
							modal : true,
							draggable: false,
							resizable: false,
							buttons : {
								"Close" : function() {
									$(this).dialog("close");
								}
							}
						});
						
					}	
					
					if (e.type == "destroy") {
						$("#alertsBox").html("");
						$("#alertsBox").html(errorInfo);
						$("#alertsBox").dialog({
							modal : true,
							draggable: false,
							resizable: false,
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
					return false;
				}
				if (e.type == "create" && !e.response.Errors) {
					$("#alertsBox").html("");
					$("#alertsBox").html("Record Created Successfully");
					$("#alertsBox").dialog({
						modal : true,
						draggable: false,
						resizable: false,
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
				if (e.type == "destroy" && !e.response.Errors) {
					$("#alertsBox").html("");
					$("#alertsBox").html("Record Deleted Succesfully");
					$("#alertsBox").dialog({
						modal : true,
						draggable: false,
						resizable: false,
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
	
<!-- </div>
</div> -->
<style type="text/css">
  .k-datepicker span {
	width: 52%;
}
.k-picker-wrap .k-icon {
    margin-left: -2px;
    margin-top: 5px;
}
.k-i-calendar {
    background-position: -33px -175px;
}
.k-datepicker{
background: white;
} 
</style>

<style>

	td{
		vertical-align: top;
		padding: 5px;
	}
	div.ui-dialog {position:fixed;overflow:"auto";}   
</style>