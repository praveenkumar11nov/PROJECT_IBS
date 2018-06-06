<%@include file="/common/taglibs.jsp"%>


<c:url value="/vehicledetails/create" var="createUrl" />
<c:url value="/vehicledetails/read" var="readUrl" />
<c:url value="/vehicledetails/update" var="updateUrl" />
<c:url value="/vehicledetails/getOwnerNames" var="getOwnername" />
<c:url value="/vehicledetails/getTenantNames" var="getTenantname" />
<c:url value="/vehicledetails/getSlotNumbers" var="getSlotNumber" />
<c:url value="/vehicledetails/getVehiclesMake" var="getVehicleMake" />
<c:url value="/vehicledetails/getVehicleModel" var="getVehicleModel" />
<c:url value="/vehicledetails/getProperty" var="propertyUrl" />
<c:url value="/vehicledetails/getRegNameForFilter" var="RegNameFilterUrl" />
<c:url value="/vehicledetails/getSlotForFiltervehicle" var="SlotNameFilterUrl" />
<c:url value="/vehicledetails/getPropertyForFiltervehicle" var="PropertyFilterUrl" />
<c:url value="/vehicledetails/getVehicleMakeForFilter" var="VehicleMakeFilterUrl" />
<c:url value="/vehicledetails/getVehicleModeleForFilter" var="getVehicleModelForFilter" />
<c:url value="/vehicledetails/getTagNameForFilter" var="TagNameFilterUrl" />
<c:url value="/vehicledetails/getCreatedByForFilter" var="CreatedByFilterUrl" />
<c:url value="/vehicledetails/getUpdatedByForFilter" var="UpdatedByFilterUrl" />
<c:url value="/vehicledetails/getReg" var="checkRegExist" />
<c:url value="/vehicledetails/getTag" var="checkTagExist" />
<c:url value="/vehicledetails/getMake" var="checkMakeExist" />
<c:url value="/vehicledetails/getModel" var="checkModelExist" />
	
	<kendo:grid name="grid" pageable="true" sortable="true" resizable="true" selectable="true" groupable="true" filterable="true" scrollable="true" height="430px">
		<kendo:grid-editable mode="popup" />
		
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Vehicles Details" />
				<kendo:grid-toolbarItem text="Clear Filter" name="Clear_Filter"/>
		</kendo:grid-toolbar>
		
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" ></kendo:grid-pageable>
		
		<kendo:grid-filterable extra="false">
		 <kendo:grid-filterable-operators>
		  	<kendo:grid-filterable-operators-string eq="Is equal to"/>
		  	<kendo:grid-filterable-operators-date lte="Registered Before" gte="Registered After"/>		
		 </kendo:grid-filterable-operators>
			
		</kendo:grid-filterable>
		
		<kendo:grid-columns>
			<kendo:grid-column title="Registration No" field="vhRegistrationNo" width="130px">
			 <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function psSlotNoFilter(element) {
								element.kendoAutoComplete({
								dataSource : {
										transport : {
											read : "${RegNameFilterUrl}",
										}
									},
								});							
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	      	   
      	   </kendo:grid-column>	
      	   
			<kendo:grid-column title="Property Number" field="property" editor="propertyDropDownEditor" hidden="true" width="100px"/>		
      	   
			<kendo:grid-column title="Owner/Tenant" field="owner" editor="ownerEditor" width="130px">
				 <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function psSlotNoFilter(element) {
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
      	   
			<kendo:grid-column title="Slot Type" field="slotType" hidden="true" editor="slotTypeEditor" width="100px"/>			
      	   
			<kendo:grid-column title="Slot No" field="validSlotsNo" editor="parkingSlotEditor" width="150px">
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
      	   
			<kendo:grid-column title="Vehicle Make" field="vhMake" width="120px" editor="vehicleMakeEditor">
			 <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function psSlotNoFilter(element) {
								element.kendoAutoComplete({
								dataSource : {
										transport : {
											read : "${VehicleMakeFilterUrl}",
										}
									},
								});							
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	      	   
      	   </kendo:grid-column>	
      	   
      	   <kendo:grid-column title=" " field="vhMakeOther" width="130px" hidden="true"/>
      	   
			<kendo:grid-column title="Vehicle Model" field="vhModel" width="110px" editor="vehicleModelEditor">
			 <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function psSlotNoFilter(element) {
								element.kendoAutoComplete({
								dataSource : {
										transport : {
											read : "${getVehicleModelForFilter}",
										}
									},
								});							
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	      	   
      	   </kendo:grid-column>	
      	   
      	   <kendo:grid-column title=" " field="vhModelOther" width="130px"  hidden="true"/>
      	   
			<kendo:grid-column title="Vehicle Tag No" field="vhTagNo" width="130px">
			 <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function psSlotNoFilter(element) {
								element.kendoAutoComplete({
								dataSource : {
										transport : {
											read : "${TagNameFilterUrl}",
										}
									},
								});							
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	      	   
      	   </kendo:grid-column>	
      	   
			<kendo:grid-column title="Start Date" field="vhStartDate" width="100px" format= "{0:dd/MM/yyyy}" >
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
			<kendo:grid-column title="End Date" field="vhEndDate" width="100px" format= "{0:dd/MM/yyyy}" >
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
			<kendo:grid-column title="Created By" field="createdBy" width="100px">
			 <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function psSlotNoFilter(element) {
								element.kendoAutoComplete({
								dataSource : {
										transport : {
											read : "${CreatedByFilterUrl}",
										}
									},
								});							
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	      	   
      	   </kendo:grid-column>	
      	   
			<kendo:grid-column title="Last Updated By" field="lastUpdatedBy" width="130px" hidden="true">
			 <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function psSlotNoFilter(element) {
								element.kendoAutoComplete({
								dataSource : {
										transport : {
											read : "${UpdatedByFilterUrl}",
										}
									},
								});							
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	      	   
      	   </kendo:grid-column>	
      	   
			<kendo:grid-column title="Last Updated Date" field="lastUpdatedDate" width="140px" format= "{0:dd/MM/yyyy hh:mm tt}">
			 	<kendo:grid-column-filterable ui="datetimepicker"></kendo:grid-column-filterable>   	   
      	   </kendo:grid-column>	
			<kendo:grid-column title="&nbsp;" width="80px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit" click="edit" />
				</kendo:grid-column-command>
			</kendo:grid-column>
			
			<kendo:grid-column title=""	template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Activate#=data.vhId#'>#= data.status == true ? 'De-activate' : 'Activate' #</a>" width="100px" />
				
		</kendo:grid-columns>
		<kendo:dataSource pageSize="20" requestEnd="onRequestEnd" requestStart="OnRequestStart">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-create url="${createUrl}" dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-update url="${updateUrl}" dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-parameterMap>
					<script>
						function parameterMap(options, type) {
							return JSON.stringify(options);
						}
					</script>
				</kendo:dataSource-transport-parameterMap>
			</kendo:dataSource-transport>
			
			<kendo:dataSource-schema parse="parse">
				<kendo:dataSource-schema-model id="vhId">
					<kendo:dataSource-schema-model-fields>
					
						<kendo:dataSource-schema-model-field name="owner"/>
						<kendo:dataSource-schema-model-field name="Idowner"/>						
						<kendo:dataSource-schema-model-field name="property"/>
						<kendo:dataSource-schema-model-field name="propertyId"/>					
						<kendo:dataSource-schema-model-field name="vhRegistrationNo" type="string"/>											
						<kendo:dataSource-schema-model-field name="vhMake"/> 					
						<kendo:dataSource-schema-model-field name="vhModel" />	
						<kendo:dataSource-schema-model-field name="vhMakeOther"/> 					
						<kendo:dataSource-schema-model-field name="vhModelOther" />						
						<kendo:dataSource-schema-model-field name="vhTagNo" type="string"/>												
						<kendo:dataSource-schema-model-field name="vhStartDate" type="date"/>						
						<kendo:dataSource-schema-model-field name="vhEndDate" type="date" defaultValue="" />
						<kendo:dataSource-schema-model-field name="slotType"/>						
						<kendo:dataSource-schema-model-field name="validSlotsNo"/>
						<kendo:dataSource-schema-model-field name="createdBy"/>
						<kendo:dataSource-schema-model-field name="lastUpdatedBy"/>
						<kendo:dataSource-schema-model-field name="lastUpdatedDate" type="date"/>
												 
						<kendo:dataSource-schema-model-field name="status" editable="false" defaultValue="false" type="boolean" />
						 	
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
	</kendo:grid>
	<div id="alertsBox" title="Alert"></div>  	
	<script> 
	
	var resRegistartion = [];	
	var resTagNumber = [];	
	var rescarMake = [];	
	var rescarModel = [];
	
	function parse (response) {   
		resRegistartion = [];	
		resTagNumber = [];	
		rescarMake = [];	
		rescarModel = [];
		
	    $.each(response, function (idx, elem) {
            if (elem.vhEndDate && typeof elem.vhEndDate === "string") {
                elem.vhEndDate = kendo.parseDate(elem.vhEndDate, "yyyy-MM-ddTHH:mm:ss.fffZ");
            }
            if (elem.vhStartDate && typeof elem.vhStartDate === "string") {
                elem.vhStartDate = kendo.parseDate(elem.vhStartDate, "yyyy-MM-ddTHH:mm:ss.fffZ");
            }
            if (elem.lastUpdatedDate && typeof elem.lastUpdatedDate === "string") {
                elem.lastUpdatedDate = kendo.parseDate(elem.lastUpdatedDate, "yyyy-MM-ddTHH:mm:ss.fffZ");
            }
           	resRegistartion.push(elem.vhRegistrationNo);				
            resTagNumber.push(elem.vhTagNo);				
            rescarMake.push(elem.vhMake);	            
            rescarModel.push(elem.vhModel);				
			
        });
        return response;
	} 
	
	  //register custom validation rules
    (function ($, kendo) {
        $.extend(true, kendo.ui.validator, {
             rules: { // custom rules     
            	 vhRegistrationvalidation: function (input, params) {
                     //check for the name attribute 
                     if (input.attr("name") == "vhRegistrationNo") {
                    	 return $.trim(input.val()) !== "";
                     }
                     return true;
                 },
                 vhStartDate: function (input, params) {
                     //check for the name attribute 
                     if (input.attr("name") == "vhStartDate") {
                    	 return $.trim(input.val()) !== "";
                     }
                     return true;
                 },
                 vhTagNoValidation: function (input, params) {
                     //check for the name attribute 
                     if (input.attr("name") == "vhTagNo") {
                    	 return $.trim(input.val()) !== "";
                     }
                     return true;
                 },   
            	 vhRegistrationNoUniquevalidation : function(input,params) {						
					if (input.filter("[name='vhRegistrationNo']").length && input.val()) {
						var flag = true;
						var fieldValue = input.val();
						$.each(resRegistartion, function(idx1, elem1) {								
							if(elem1 == fieldValue.toUpperCase()){
								flag = false;
							}	
						});
						return flag;
					}
					return true;
											
				},				
				vhTagNoUniquevalidation : function(input,params) {						
					if (input.filter("[name='vhTagNo']").length && input.val()) {
						var flag = true;
						var fieldValue = input.val();
						$.each(resTagNumber, function(idx1, elem1) {								
							if(elem1 == fieldValue.toUpperCase()){
								flag = false;
							}	
						});
						return flag;
					}
					return true;						
				},				
				vhcarmakeUniquevalidation : function(input,params) {												
					if (input.filter("[name='vhMakeOther']").length && input.val()) {
						var flag = true;
						var fieldValue = input.val();
						$.each(rescarMake, function(idx1, elem1) {	
							
							if(elem1!=null && elem1.toUpperCase() == fieldValue.toUpperCase()){
								flag = false;
							}	
						});
						return flag;
					}
					return true;						
				},				
				vhcarmodelUniquevalidation : function(input,params) {				
					if (input.filter("[name='vhModelOther']").length && input.val()) {
						var flag = true;
						var fieldValue = input.val();
						$.each(rescarModel, function(idx1, elem1) {								
							if(elem1.toUpperCase() == fieldValue.toUpperCase()){
								flag = false;
							}	
						});
						return flag;
					}
					return true;					
				},			
             },
           //custom rules messages
             messages: { 
            	 vhRegistrationvalidation:"Enter Vehicle Registration Number",
            	 vhStartDate:"Enter Vehicle Start Date",
            	 vhRegistrationNoUniquevalidation:"Vehicle Registration Already Exists",            	 
            	 vhTagNoUniquevalidation:"Vehicle Tag Number Already Exists",            	 
            	 vhcarmakeUniquevalidation:"Vehicle Make Already Exists",            	
            	 vhcarmodelUniquevalidation:"Vehicle Model Already Exists",           		
           		 vhTagNoValidation:"Enter Vahicle Tag Number"
             }
           });
      })(jQuery, kendo);
	 
	$("#grid").on("click", ".k-grid-Clear_Filter", function(){		
	    $("form.k-filter-menu button[type='reset']").slice().trigger("click");
		var grid = $("#grid").data("kendoGrid");
		grid.dataSource.read();
		grid.refresh();
	});
	
	var flagUserId = "";

	function propertyDropDownEditor(container, options) {
		$('<input data-text-field="property" id="propertydrop" data-value-field="propertyId" data-bind="value:' + options.field + '" />')
				.appendTo(container).kendoComboBox({
					placeholder:"Select Property",
					change:onDataBound,
					filter:"startswith",            	  
					dataSource : {
						transport : {
							read : "${propertyUrl}"
						}
					},							
				});						
	}
	var result;
	function onDataBound() {
		 var value = this.dataItem();
		 var regNo=$('input[name="vhRegistrationNo"]').val();
		 result=value.block+value.floor+value.property;
		 var text=result+regNo.toUpperCase();
		 $('input[name="vhTagNo"]').val(text.trim());	  
		 $('input[name="vhTagNo"]').change(); 
		 $('input[name="vhTagNo"]').attr('readonly', 'readonly');
	}	
	
	 function ownerEditor(container, options) {
	     $('<input data-text-field="owner" id="ownerdrop" data-value-field="ownerId" data-bind="value:' + options.field + '"/>')
          .appendTo(container)
          .kendoComboBox({
      		   placeholder: "Select Owner",
      		   cascadeFrom: "propertydrop",				
      		   filter:"startswith",
      		   template : '<table><tr><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.personId#'/>" width=40 height=55  /></span>'
			+ '<td padding="5px"><span class="k-state-default"><b>#: data.owner #</b></span><br></td></tr>'
			+'<tr><td style="padding: 5px;font-size: 10px;"><b>#: data.persontType #</b></td></tr></table>',
		   dataSource: {       
	            transport: {
	                read: "${getOwnername}"
	            }
			
	        },
	    });
	 } 
	 
	function slotTypeEditor(container, options) {
    	   var data = ["FIXED" , "FLOATING"];
    	   $('<input data-text-field="" id="slottypedrop" data-value-field="" data-bind="value:' + options.field + '"/>')
	    	  .appendTo(container).kendoDropDownList({
	    	  optionLabel :"Select",				
			  dataSource :data,
			  change:onSelectslotType,
			});
  	}
	
	function onSelectslotType(e){
			var v=$("#slottypedrop").val();
			
			if(v=="FLOATING"){
			 $('label[for="validSlotsNo"]').parent().hide();
		 	 $("#slotdrop").parent().hide();
			}else{
			 $('label[for="validSlotsNo"]').parent().show();
		     $("#slotdrop").parent().show();   		
			}
	
		}
	 
	function parkingSlotEditor(container, options) {
		  $('<input data-text-field="validSlotsNo" id="slotdrop"  data-value-field="validSlotsNo" data-bind="value:' + options.field + '"/>')
		     		.appendTo(container).kendoComboBox({
		     			cascadeFrom: "propertydrop", 
		     			autoBind: false,
		     			dataSource : {
							transport : {	
								read : "${getSlotNumber}"
							}
						}	
			});
	}		
 
   function vehicleMakeEditor(container, options) {
		  $('<input data-text-field="carName" id="vhmakedrop" data-value-field="carId" data-bind="value:' + options.field + '"/>')
		     		.appendTo(container).kendoComboBox({
		     		palceholder:"Select Car",
		     		change:onmakeChange,
		     		filter:"startswith",
		     		dataSource : {
						transport : {		
							read : "${getVehicleMake}"
						}
					}							
			});
	}	  
   
   function onmakeChange(e){
		var v= this.dataItem();
		if(v.carName=="Others"){
			   $('label[for="vhMakeOther"]').parent().show();			    
			   $('div[data-container-for="vhMakeOther"]').show();	
		}else{
			  $('label[for="vhMakeOther"]').parent().hide();			   
			  $('div[data-container-for="vhMakeOther"]').hide();	
			 			
		}
	
	}     
  
   function vehicleModelEditor(container, options) {
		  $('<input data-text-field="modelName" id="vhmodeldrop" data-value-field="modelId" data-bind="value:' + options.field + '"/>')
     		.appendTo(container).kendoComboBox({
				cascadeFrom: "vhmakedrop", 							
		        autoBind: false,
		        change:onmodelChange,
		        palceholder:"Select Model",
	     		filter:"startswith",
	     		dataSource : {
					transport : {
						read : "${getVehicleModel}"						
					}
				}
			});
   }	
   
   function onmodelChange(e){
		var v= this.dataItem();
		if(v.modelName=="Others"){
			   $('label[for="vhModelOther"]').parent().show();			   
			   $('div[data-container-for="vhModelOther"]').show();	
		}else{
			  $('label[for="vhModelOther"]').parent().hide();			  	
			  $('div[data-container-for="vhModelOther"]').hide();			
		}
	
	}     

    $("#grid").on("click", ".k-grid-add", function(e) {   
    	
    	securityCheckForActions("./parkingManagement/vehicles/createButton");
    	
    	if($("#grid").data("kendoGrid").dataSource.filter()){
    		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
 			var grid = $("#grid").data("kendoGrid");
 			grid.dataSource.read();
 			grid.refresh();
        } 
    	
    	$('input[name="vhRegistrationNo"]').on("change","",function(e){
    		
   			if(result!="" && result!=null){
	   			 var regNo=$('input[name="vhRegistrationNo"]').val();
	   			 var text=result+regNo.toUpperCase();
	   			 $('input[name="vhTagNo"]').val(text.trim());	  
	   			 $('input[name="vhTagNo"]').change();
	   			 $('input[name="vhTagNo"]').attr('readonly', 'readonly');
   			}
   		});      
		  
	   $('.k-edit-field .k-input').first().focus();  	       	
	   $(".k-edit-field").each(function () {
   		   $(this).find("#temPID").parent().remove();
       }); 
	   $('input[name="vhStartDate"]').kendoDatePicker().data("kendoDatePicker").min(new Date());
	    $('input[name="vhEndDate"]').kendoDatePicker().data("kendoDatePicker");
	   $('label[for="vhMakeOther"]').parent().hide();
	   $('label[for="vhModelOther"]').parent().hide();
	   
	   $('div[data-container-for="vhMakeOther"]').hide();	
	   $('div[data-container-for="vhModelOther"]').hide();	
	
       var firstItem = $('#grid').data().kendoGrid.dataSource.data()[0];   		
 	   $(".k-window-title").text("Add Vehicles Details");
 	   $(".k-grid-update").text("Create");
 	   $(".k-grid-Activate").hide();
 	   $('label[for="createdBy"]').parent().remove();
	   $('label[for="lastUpdatedBy"]').parent().remove();
	   $('label[for="lastUpdatedDate"]').parent().remove();
	   $('input[name="createdBy"]').parent().remove();
	   $('input[name="lastUpdatedBy"]').parent().remove();	
	   $('input[name="lastUpdatedDate"]').parent().remove();
	   $('label[for="undefined"]').parent().remove();
	   $('div[data-container-for="lastUpdatedDate"]').remove();	
	   $('input[name="vhTagNo"]').attr('readonly', 'readonly');	   
	   
	   $('label[for="property"]').after('<label style=color:red;>&nbsp;*</label>'); 	     	
	   $('label[for="vhMake"]').after('<label style=color:red;>&nbsp;*</label>');	
	   $('label[for="vhModel"]').after('<label style=color:red;>&nbsp;*</label>');	
	   $('label[for="vhMakeOther"]').after('<label style=color:red;>&nbsp;*</label>');	
	   $('label[for="vhModelOther"]').after('<label style=color:red;>&nbsp;*</label>');	
	   $('label[for="vhTagNo"]').after('<label style=color:red;>&nbsp;*</label>');	
	   $('label[for="validSlotsNo"]').after('<label style=color:red;>&nbsp;*</label>');	
	   $('label[for="slotType"]').after('<label style=color:red;>&nbsp;*</label>');	
	   $('label[for="vhStartDate"]').after('<label style=color:red;>&nbsp;*</label>');	
	   $('label[for="owner"]').after('<label style=color:red;>&nbsp;*</label>');	
	   $('label[for="vhRegistrationNo"]').after('<label style=color:red;>&nbsp;*</label>');		  
	  	
	   $(".k-grid-update").click(function () {
			
		   var ownerdrop=$("#ownerdrop").data("kendoComboBox");
		   var slotdrop=$("#slotdrop").data("kendoComboBox");
		   var vhmakedrop=$("#vhmakedrop").data("kendoComboBox");
		   var vhmodeldrop=$("#vhmodeldrop").data("kendoComboBox");
		   var slottypedrop=$("#slottypedrop").val();
		   var propertydrop=$("#propertydrop").data("kendoComboBox");
			 
		   if (propertydrop.select() == -1) {
	            alert("Please Select Proper Property");
				return false;	
		   }
		   if (ownerdrop.select() == -1) {
	            alert("Please Select Proper Owner");
				return false;	
		   }
		   if(slottypedrop=="Select"){
		   		alert("Please Select Slot Type");
		   		return false;	
		   }
		   if(slottypedrop=="FIXED"){
			   if (slotdrop.select() == -1) {
		            alert("Please Select Proper Slot");
					return false;	
			   }
		   }			   
		   if (vhmakedrop.select() == -1) {
	            alert("Please Select Proper Vehicle");
				return false;	
		   }
		   if (vhmodeldrop.select() == -1) {
	            alert("Please Select Proper Vehicle Model");
				return false;	
		   }		
		   if(firstItem.get('vhEndDate')!="" && firstItem.get('vhEndDate')!=null){
 			   if(firstItem.get('vhEndDate')<=firstItem.get('vhStartDate')){
	  			   alert("End Date Should Be Greater than Start Date");
	  			   return false; 				   
 			   }
 		   } 	
	   });	 	
 	   
 	});

     function edit(e) {
    	 securityCheckForActions("./parkingManagement/vehicles/updateButton");
    	 
    	 if($("#grid").data("kendoGrid").dataSource.filter()){
     		$("form.k-filter-menu button[type='resRegistartionet']").slice().trigger("click");
 			var grid = $("#grid").data("kendoGrid");
 			grid.dataSource.read();
 			grid.refresh();
         }
    	 var reg=$('input[name="vhRegistrationNo"]').val();
    	 var tag=$('input[name="vhTagNo"]').val();
    	 var carmk=$('input[name="vhMakeOther"]').val();
    	 var carmd=$('input[name="vhModelOther"]').val();
    	 resRegistartion.splice(resRegistartion.indexOf(reg),1);    	 
		 resTagNumber.splice(resTagNumber.indexOf(tag),1);
		 rescarMake.splice(rescarMake.indexOf(carmk),1);
		 rescarModel.splice(rescarModel.indexOf(carmd),1);
		 
		 $('input[name="vhStartDate"]').kendoDatePicker().data("kendoDatePicker").min(new Date());
		    $('input[name="vhEndDate"]').kendoDatePicker().data("kendoDatePicker");
    	 $('label[for="property"]').after('<label style=color:red;>&nbsp;*</label>'); 	     	
  	     $('label[for="vhMake"]').after('<label style=color:red;>&nbsp;*</label>');	
  	     $('label[for="vhModel"]').after('<label style=color:red;>&nbsp;*</label>');	
  	     $('label[for="vhMakeOther"]').after('<label style=color:red;>&nbsp;*</label>');	
  	     $('label[for="vhModelOther"]').after('<label style=color:red;>&nbsp;*</label>');	
  	     $('label[for="vhTagNo"]').after('<label style=color:red;>&nbsp;*</label>');	
  	     $('label[for="validSlotsNo"]').after('<label style=color:red;>&nbsp;*</label>');	
  	     $('label[for="slotType"]').after('<label style=color:red;>&nbsp;*</label>');	
  	     $('label[for="vhStartDate"]').after('<label style=color:red;>&nbsp;*</label>');	
  	     $('label[for="owner"]').after('<label style=color:red;>&nbsp;*</label>');	
  	     $('label[for="vhRegistrationNo"]').after('<label style=color:red;>&nbsp;*</label>');	
    	
    	 $('input[name="vhRegistrationNo"]').on("change","",function(e){     		
    			if(result!="" && result!=null){
 	   			 var regNo=$('input[name="vhRegistrationNo"]').val();
 	   			 var text=result+regNo.toUpperCase();
 	   			 $('input[name="vhTagNo"]').val(text.trim());	  
 	   			 $('input[name="vhTagNo"]').change(); 
 	   		 	 $('input[name="vhTagNo"]').attr('readonly', 'readonly');
    			}
    	 });
    	 
		  var widget = $("#grid").data("kendoGrid");
	      var firstItem = widget.dataItem($(e.currentTarget).closest("tr"));
		  
	      $('input[name="allottedSlot"]').val("Slot Not Allotted");	  
		  $('input[name="allottedSlot"]').change(); 
		  $('input[name="vhTagNo"]').attr('readonly', 'readonly');
		  $('label[for="vhMakeOther"]').parent().hide();
		  $('label[for="vhModelOther"]').parent().hide();
		   
		  $('div[data-container-for="vhMakeOther"]').hide();	
		  $('div[data-container-for="vhModelOther"]').hide();	
	    	  	
	   	  $(".k-window-title").text("Edit Vehicles Details"); 
	   	  $('label[for="createdBy"]').parent().remove();
		  $('label[for="lastUpdatedBy"]').parent().remove();
		  $('label[for="lastUpdatedDate"]').parent().remove();
		  $('input[name="createdBy"]').parent().remove();
		  $('input[name="lastUpdatedBy"]').parent().remove();		 
		  $('input[name="lastUpdatedDate"]').parent().remove();
		  $('label[for="undefined"]').parent().remove();
		  $('div[data-container-for="lastUpdatedDate"]').remove();				
		 
		  var v=firstItem.get('slotType');
			 
		  if(v=="FLOATING"){
			 $('label[for="validSlotsNo"]').parent().hide();
			 $("#slotdrop").parent().hide();
		  }else{
			 $('label[for="validSlotsNo"]').parent().show();
			 $("#slotdrop").parent().show();   		
		  }
		  $(".k-grid-update").click(function () {
			   var ownerdrop=$("#ownerdrop").data("kendoComboBox");
			   var slotdrop=$("#slotdrop").data("kendoComboBox");
			   var vhmakedrop=$("#vhmakedrop").data("kendoComboBox");
			   var vhmodeldrop=$("#vhmodeldrop").data("kendoComboBox");
			   var slottypedrop=$("#slottypedrop").val();
			   
			   var propertydrop=$("#propertydrop").data("kendoComboBox");
				 
			   if (propertydrop.select() == -1) {
		            alert("Please Select Proper Property");
					return false;	
			   }
			   if (ownerdrop.select() == -1) {
		            alert("Please Select Proper Owner");
					return false;	
			   }
			   if(slottypedrop=="Select"){
			   		alert("Please Select Slot Type");
			   		return false;	
			   }
			   if(slottypedrop=="FIXED"){
				   if (slotdrop.select() == -1) {
			            alert("Please Select Proper Slot");
						return false;	
				   }
			   }			   
			   if (vhmakedrop.select() == -1) {
		            alert("Please Select Proper Vehicle");
					return false;	
			   }
			   if (vhmodeldrop.select() == -1) {
		            alert("Please Select Proper Vehicle Model");
					return false;	
			   }
			   if(firstItem.get('vhStartDate')==null){
		  			alert("Start Date Should Be Selected");
		  			return false; 				   
				  
			   } 
			   if(firstItem.get('vhEndDate')!="" && firstItem.get('vhEndDate')!=null){
				   if(firstItem.get('vhEndDate')<=firstItem.get('vhStartDate')){
		  			   alert("End Date Should Be Greater than Start Date");
		  			   return false; 				   
				   }
			   } 	
		   });
		  
	   	  $(".k-edit-field").each(function () {
	  		   $(this).find("#temPID").parent().remove();
	  	  });   
	   	  
   	    e.container.find(".k-grid-cancel").bind("click", function () {
         var AccessCardGrid = $("#grid").data("kendoGrid");
   		 parse(AccessCardGrid._data);
       }); 
	  
     } 
     
     $("#grid").on("click", "#temPID", function(e) {
		var button = $(this), enable = button.text() == "Activate";
		var widget = $("#grid").data("kendoGrid");
		var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
		
		var result=securityCheckForActionsForStatus("./parkingManagement/vehicles/deleteButton");   
	    if(result=="success"){
	    	if (enable) {
				$.ajax({
					type : "POST",
					url : "./vehicles/vehicleStatus/" + dataItem.id + "/activate",
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
						button.text('Deactivate');
						$('#grid').data('kendoGrid').dataSource.read();
					}
				});
			} 
			else {
				$.ajax({
					type : "POST",
					url : "./vehicles/vehicleStatus/" + dataItem.id + "/deactivate",
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
						button.text('Activate');
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
   		if (e.response.status == "invalid") {
			errorInfo = "";
			errorInfo = e.response.result.invalid;
			for (var i = 0; i < e.response.result.length; i++) {
				errorInfo += (i + 1) + ". "
						+ e.response.result[i].defaultMessage;
			}

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
			var grid = $("#grid").data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
			return false;
		}
   		if (e.response.status == "FAIL") {
   			errorInfo = "";
   			for (var i = 0; i < e.response.result.length; i++) {
   			errorInfo += (i + 1) + ". "	+ e.response.result[i].defaultMessage+"\n";
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
       			var grid = $("#grid").data("kendoGrid");
       			grid.dataSource.read();
       			grid.refresh();
       			
   			}
   	
   			if (e.type == "update") {
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
       			var grid = $("#grid").data("kendoGrid");
       			grid.dataSource.read();
       			grid.refresh();
   			}
   	
   			var grid = $("#grid").data("kendoGrid");
   			grid.dataSource.read();
   			grid.refresh();
   			return false;
   		}

   		if (e.type == "update" && !e.response.Errors) {
   			$("#alertsBox").html("");
   			$("#alertsBox").html("Record Updated Successfully");
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
   		
   		if (e.type == "update" && e.response.Errors) {
   			$("#alertsBox").html("");
   			$("#alertsBox").html("Record Updation Failed");
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
</style>