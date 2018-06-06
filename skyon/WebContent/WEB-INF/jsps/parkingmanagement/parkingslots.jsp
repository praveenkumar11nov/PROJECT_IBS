<%@include file="/common/taglibs.jsp"%>
	
	

	<c:url value="/parkingslotsdetails/create" var="createUrl" />
	<c:url value="/parkingslotsdetails/read" var="readUrl" />
	<c:url value="/parkingslotsdetails/update" var="updateUrl" />
	<c:url value="/parkingslotsdetails/NestedReadUrl" var="transportNestedReadUrl" />
	<c:url value="/parkingslotsdetails/getBlocks" var="blockNameUrl" />
	<c:url value="/parkingslotsdetails/getBlocksForFilter" var="BlockNameFilterUrl" />
	<c:url value="/parkingslotsdetails/getSlotForFilter" var="SlotNameFilterUrl" />
	<c:url value="/parkingslotsdetails/getOwnershipForFilter" var="OwnerShipFilterUrl" />
	<c:url value="/parkingslotsdetails/getParkingMethodForFilter" var="ParkingMethodFilterUrl" />
	<c:url value="/parkingslotsdetails/checkExistance" var="checkExist" />
	
	<kendo:grid name="grid" pageable="true" edit="parkingSlotsEvent" selectable="true" resizable="true" groupable="true"  sortable="true" filterable="true" scrollable="true" height="430px" detailTemplate="template">
		<kendo:grid-editable  mode="popup" />
		<%-- <kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Parking Slots Master Details" />
			<kendo:grid-toolbarItem name="clear_filter" text="Clear Filter"/>
			
		</kendo:grid-toolbar> --%>
		
		<kendo:grid-toolbarTemplate>
			
			<div class="toolbar">									
				<a class="k-button k-button-icontext k-grid-add" href="#">
		            <span class="k-icon k-add"></span>
		            Add Parking Slots Master Details
	        	</a>
	        	<a class="k-button k-button-icontext k-grid-parkingMasterTemplatesDetailsExport" href="#">
                <span class=" "></span>
                 Export To Excel
                </a>
                
                <a class="k-button k-button-icontext k-grid-parkingMasterPdfTemplatesDetailsExport" href="#">
                <span class=" "></span>
                 Export To PDF
                </a>
				<a class='k-button' href='\\#' onclick="clearFilter()"><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>
				<a class='k-button' href='#' id="undo"><span class='k-icon k-i-funnel-clear'></span> Import</a>
			</div>  	
				
    	</kendo:grid-toolbarTemplate>	
		
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" ></kendo:grid-pageable>
		
		<kendo:grid-filterable extra="false">
		 <kendo:grid-filterable-operators>
		  	<kendo:grid-filterable-operators-string eq="Is equal to"/>	  	
		 </kendo:grid-filterable-operators>
			
		</kendo:grid-filterable>
		
		<kendo:grid-columns>
			<kendo:grid-column title="BlockId" field="blockId" width="100px" hidden="true"/>
			
			<kendo:grid-column title="Block Name" editor="blockDropDownEditor" field="block" width="100px">
			
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
			<kendo:grid-column title="Ownership" field="psOwnership" editor="ownershipDropDownEditor" width="100px">
			 <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function psOwnershipFilter(element) {
								element.kendoAutoComplete({
								dataSource : {
										transport : {
											read : "${OwnerShipFilterUrl}",
										}
									},
								});							
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	
      	   
      	   </kendo:grid-column>	
			<kendo:grid-column title="Parking Method" field="psParkingMethod" editor="parkingMethodDropDownEditor" width="100px">
				 <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function psParkingMethodFilter(element) {
								element.kendoAutoComplete({
								dataSource : {
										transport : {
											read : "${ParkingMethodFilterUrl}",
										}
									},
								});							
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	
      	   </kendo:grid-column>
      	   
      	   	<kendo:grid-column title="Slot No" field="psSlotNo" width="100px">
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

      	   <kendo:grid-column title="Active Date" field="psActiveDate" format="{0:dd/MM/yyyy hh:mm tt}" editor="dateEditor"  width="140px" >
      	   		<kendo:grid-column-filterable ui="datetimepicker"></kendo:grid-column-filterable>
	    	</kendo:grid-column>		

			<kendo:grid-column title="&nbsp;" width="100px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit"/>
				</kendo:grid-column-command>
			</kendo:grid-column>
			
			<kendo:grid-column title=""	template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Activate#=data.psId#'>#= data.status == true ? 'De-activate' : 'Activate' #</a>" width="100px" />
				
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
				<kendo:dataSource-schema-model id="psId">
					<kendo:dataSource-schema-model-fields>
					
						<kendo:dataSource-schema-model-field name="block"/>							
						<kendo:dataSource-schema-model-field name="blockId"/>							
						<kendo:dataSource-schema-model-field name="psSlotNo" type="string" />							
						<kendo:dataSource-schema-model-field name="psActiveDate" type="date" />																				
						<kendo:dataSource-schema-model-field name="psOwnership" type="string">
							<kendo:dataSource-schema-model-field-validation required="true"/>
						</kendo:dataSource-schema-model-field>						
						
						<kendo:dataSource-schema-model-field name="psParkingMethod" type="string">
							<kendo:dataSource-schema-model-field-validation required="true"/>
						</kendo:dataSource-schema-model-field>						
										 	
						<kendo:dataSource-schema-model-field name="status" defaultValue="false" type="boolean" />
					
					</kendo:dataSource-schema-model-fields>
					
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
	</kendo:grid>
	
	<kendo:grid-detailTemplate id="template">
		<kendo:grid name="grid_#=psId#" pageable="true" scrollable="false">
			<kendo:grid-columns>
				<kendo:grid-column title="Allocated To" field="owner" width="100px" />
				<kendo:grid-column title="Allocated Date" field="allotmentDateFrom" format= "{0:dd/MM/yyyy}"  width="100px" />
				<kendo:grid-column title="Expiry Date" field="allotmentDateTo" format= "{0:dd/MM/yyyy}" width="100px" />
				<kendo:grid-column title="Rent Rate" field="psRent" width="100px" />            
				<kendo:grid-column title="Allocation Status" field="status" width="100px" />            
			</kendo:grid-columns>		
			<kendo:dataSource pageSize="10">
				<kendo:dataSource-transport>
					<kendo:dataSource-transport-read url="${transportNestedReadUrl}/#=psId#" type="POST" contentType="application/json"/>
					<kendo:dataSource-transport-parameterMap>
		            	<script>
		             		function parameterMap(options) { 
		            			return JSON.stringify(options);
		             		}
		            	</script>
		            </kendo:dataSource-transport-parameterMap>
				</kendo:dataSource-transport>
				
				<kendo:dataSource-schema parse="parse2">
					<kendo:dataSource-schema-model id="psId">
						<kendo:dataSource-schema-model-fields>
						
							<kendo:dataSource-schema-model-field name="owner" type="string" >
								<kendo:dataSource-schema-model-field-validation required="true" />
							</kendo:dataSource-schema-model-field>							
							<kendo:dataSource-schema-model-field name="allotmentDateFrom" type="date" />	
							<kendo:dataSource-schema-model-field name="allotmentDateTo" type="date" />	
							<kendo:dataSource-schema-model-field name="psRent" type="String" />	
							<kendo:dataSource-schema-model-field name="status" type="String" />	
							
						
						</kendo:dataSource-schema-model-fields>
						
					</kendo:dataSource-schema-model>
				</kendo:dataSource-schema>
				
			</kendo:dataSource>                           
		</kendo:grid>            
	</kendo:grid-detailTemplate>  

	<div id="alertsBox" title="Alert"></div>
	<div id="dialogBox" title="Owner Details"></div>	
	
	<c:if test="${not empty XLERRORData}">
		<div id="XLERROR" title="Alert">
				<b>${XLERRORData}</b>
		</div>
	
		<script type="text/javascript">		
		$("#XLERROR").dialog({
			modal: true,
			draggable: false,
			resizable: false,
			buttons: {
				"Close": function() {
					$( this ).dialog( "close" );
				}			
			}
		}); 
		</script>
	</c:if>
	
	<div id="fileupload" style="display:none">
				<form method="post" action="<c:url value='./parkingslot/importXL' />">        	
	    			<kendo:upload multiple="false" name="files" select="onSelectupload"  template="<span class='k-progress'></span><div class='file-wrapper'> <span class='file-icon #=addExtensionClass(files[0].extension)#'></span><h6 class='file-heading file-name-heading'>Name: #=name#</h6><h6 class='file-heading file-size-heading'>Size: #=size# bytes</h6><button type='button' class='k-upload-action'></button></div>">
						<kendo:upload-async  autoUpload="true" saveUrl="${saveUrl}" removeUrl="${removeUrl}"/>
					</kendo:upload> 				
					<input type="submit" onclick="return checkValidtio()" value="Submit" class="k-button" />
				</form>
	</div>
	
	<script> 
	$("#grid").on("click",".k-grid-parkingMasterTemplatesDetailsExport", function(e) {
		  window.open("./parkingMasterTemplate/parkingMasterTemplatesDetailsExport");
	});	  

	$("#grid").on("click",".k-grid-parkingMasterPdfTemplatesDetailsExport", function(e) {
		  window.open("./parkingMasterPdfTemplate/parkingMasterPdfTemplatesDetailsExport");
	});	 
	
	function checkValidtio(){
		var value=$('input[name="files"]').val();
		if(value==""){
			alert("Please Select XL sheet");
			return false;
		}else{
			return true;
		}
	}
	 function clearFilter()
	 {
		 $("form.k-filter-menu button[type='reset']").slice().trigger("click");
		    var grid = $("#grid").data("kendoGrid");
		    grid.dataSource.read();
		    grid.refresh();
	 }
	function onSelectupload(e){
		$.each(e.files, function (index, value) {			
	        var ok = (value.extension == ".xlsx" || value.extension == ".xls") ;
	        if (!ok) {
	            e.preventDefault();
	            alert("Please upload XL (.xlsx) files");
	        }
	    });
	}
	function clearFilterSlots()
	{
		  $("form.k-filter-menu button[type='reset']").slice().trigger("click");
		  var grid = $("#grid").data("kendoGrid");
		  grid.dataSource.read();
		  grid.refresh();
	}
	
	 $(document).ready(function() {
         var window = $("#fileupload"),
             undo = $("#undo")
                     .bind("click", function() {
                         window.data("kendoWindow").open().center();
                         undo.hide();
                     });

         var onClose = function() {
             undo.show();
         }

         if (!window.data("kendoWindow")) {
             window.kendoWindow({
                 width: "600px",
                 title: "Import XL File",
                 actions: [
                     "Pin",
                     "Minimize",
                     "Maximize",
                     "Close"
                 ],
                 close: onClose
             });
         }
     });	
	
	function addExtensionClass(extension) {
		switch (extension) {
		case '.jpg':
		case '.img':
		case '.png':
		case '.gif':
			return "img-file";
		case '.doc':
		case '.docx':
			return "doc-file";
		case '.xls':
		case '.xlsx':
			return "xls-file";
		case '.pdf':
			return "pdf-file";
		case '.zip':
		case '.rar':
			return "zip-file";
		default:
			return "default-file";
		}
	}
	
	var slotNo=[];
	
	function parse (response) {   
		slotNo = [];
	    $.each(response, function (idx, elem) {
	    	 slotNo.push(elem.psSlotNo);	
	    	if(elem.psActiveDate!=null){
	    		if (elem.psActiveDate && typeof elem.psActiveDate === "string") {
	                elem.psActiveDate = kendo.parseDate(elem.psActiveDate, "yyyy-MM-ddTHH:mm:ss.fffZ");
	            }
	    	}
	    	 
            
        });	      
        return response;
	}  
	
	$("#grid").on("click", ".k-grid-clear_filter", function(){
	    $("form.k-filter-menu button[type='reset']").slice().trigger("click");
	    var grid = $("#grid").data("kendoGrid");
		grid.dataSource.read();
		grid.refresh();
		filter=true;
	});	 	 

	
	 //register custom validation rules
    (function ($, kendo) {
        $.extend(true, kendo.ui.validator, {
             rules: { // custom rules            	 
            	 psSlotNovalidation: function (input, params) {
                     //check for the name attribute 
                     if (input.attr("name") == "psSlotNo") {
                    	 return $.trim(input.val()) !== "";
                     }
                     return true;
                 },
                 psSlotNoUniquevalidation : function(input,params) {
                	 if (input.filter("[name='psSlotNo']").length && input.val()){
							var flag = true;
							var fieldValue = input.val();
							$.each(slotNo, function(idx1, elem1) {								
								if(elem1 == fieldValue.toUpperCase()){
									flag = false;
								}	
							});
							return flag;
						}
						return true;
					},			
                  psActiveDatevalidation: function (input, params) {
                     //check for the name attribute 
                     if (input.attr("name") == "psActiveDate") {
                    	 return $.trim(input.val()) !== "";
                     }
                     return true;
                 }             
             },
           //custom rules messages
             messages: { 
            	 psSlotNovalidation: "Enter Parking Slot Number",
            	 psActiveDatevalidation:"Enter Activation Date ", 
            	 psSlotNoUniquevalidation:"Parking Slot Already Exist",            	
             }
        });
    })(jQuery, kendo);
	 
	
		
	function parse2 (response) {   
	    $.each(response, function (idx, elem) {
            if (elem.allotmentDateFrom && typeof elem.allotmentDateFrom === "string") {
                elem.allotmentDateFrom = kendo.parseDate(elem.allotmentDateFrom, "yyyy-MM-ddTHH:mm:ss.fffZ");
            }
            if (elem.allotmentDateTo && typeof elem.allotmentDateTo === "string") {
                elem.allotmentDateTo = kendo.parseDate(elem.allotmentDateTo, "yyyy-MM-ddTHH:mm:ss.fffZ");
            }
        });
        return response;
	}  				
			
	function dateEditor(container, options) {
	    $('<input name="' + options.field + '"/>')
	            .appendTo(container)
	            .kendoDateTimePicker({
	                format:"dd/MM/yyyy hh:mm tt",
	                timeFormat:"hh:mm tt"         
	                
	    });
	}
		
	$("#grid").on("click", ".k-grid-Clear_Filter", function(){
	    $("form.k-filter-menu button[type='reset']").slice().trigger("click");
		var grid = $("#grid").data("kendoGrid");
		grid.dataSource.read();
		grid.refresh();
	});      

       function ownershipDropDownEditor(container, options) {
      	   var data = ["OWN" , "RENTED","VISITORS"];
      	   $(
      	     '<input data-text-field="" style="width:180px;" id="ownership" data-value-field="" data-bind="value:' + options.field + '" />')
      	     .appendTo(container).kendoDropDownList({
      	      optionLabel :"Select",
      	      change:onSelectOwnership,
      	      dataSource :data            	                 	      
      	   });
       }        
           
        function blockDropDownEditor(container, options) {
    		$('<input data-text-field="block" style="width:180px;" id="propertydrop" data-value-field="blockId" data-bind="value:' + options.field + '"/>') 
    				.appendTo(container).kendoComboBox({
    				placeholder : "Select Block Names",
    				filter:"startswith",  
    				autobind:true,
    				change:onChangeBlocks,
					template : '<table><tr>'
							+ '<td align="left"><span class="k-state-default"><b>#: data.block #</b></span><br>'
							+ '<span class="k-state-default">Allowed Slots:&nbsp;&nbsp;&nbsp;<i>#: data.allowedSlot #</i></span><br>'
							+ '<span class="k-state-default">No Of Properties:&nbsp;&nbsp;&nbsp;<i>#: data.noofproperty #</i></span><br></td></tr></table>',
    				dataSource : {
    						transport : {
    							read : "${blockNameUrl}",
    						}
    					},
    				});
    	}     
       
        var availableSlots=null;
    	var enteredSlotsforblocks=null;
    	var blockSelected=null; 
        function onChangeBlocks(){
        	var value=this.dataItem();     	
        	blockSelected=value.block;
        	 $.ajax({
 	 			type : "GET",
 	 			async: false,
 	 			url : './parkingslots/getnoofslots/'+value.blockId,
 	 			dataType : "JSON",
 	 			success : function(response) {		 				
 	 				enteredSlotsforblocks=response;
 	 			}
 	 		});
        	 $.ajax({
 	 			type : "GET",
 	 			async: false,
 	 			url : './parkingslots/availableSlots/'+value.blockId,
 	 			dataType : "JSON",
 	 			success : function(response) {		 				
 	 				availableSlots=response;
 	 			}
 	 		});
        	 
        	 if(enteredSlotsforblocks>=availableSlots){
    	    	 alert("No More Slots Can Create For This Block :"+blockSelected);
	       	 }
        }
        
        function parkingMethodDropDownEditor(container, options) {
      	   var data = ["FIXED" , "FLOATING"];
      	   $('<input data-text-field="" style="width:180px;" id="parkingmethod" data-value-field="" data-bind="value:' + options.field + '"/>')
	    	  .appendTo(container).kendoDropDownList({
	    	  optionLabel :"Select",				
			  dataSource :data,
			});
    	}
          
   		function onSelectOwnership(e){
   			var v=$("#ownership").val();   			
   			if(v=="VISITORS"){
   			 $('label[for="psParkingMethod"]').parent().hide();
			 $("#parkingmethod").parent().hide();
   			}else{
   			 $('label[for="psParkingMethod"]').parent().show();
			 $("#parkingmethod").parent().show();   		
   			}		
   		}
   		
   		function parkingSlotsEvent(e) {
   			
   			if($("#grid").data("kendoGrid").dataSource.filter()){
   				$("form.k-filter-menu button[type='reset']").slice().trigger("click");
   				var grid = $("#grid").data("kendoGrid");
   				grid.dataSource.read();
   				grid.refresh();
   	    	}
   			$('label[for=psId]').parent().remove();  
   	   	    $(".k-grid-Activate").hide();     	   
   	   	    $('.k-edit-field .k-input').first().focus();    	  
   	   	    $('input[name="psSlotNo"]').css('width', '180px');
   	   	    $('label[for="undefined"]').parent().remove(); 
   	   	    $('label[for="blockId"]').parent().remove(); 
   	   	    $('input[name="blockId"]').parent().hide(); 
	   	   	$(".k-edit-field").each(function () {
	    		   $(this).find("#temPID").parent().remove();
	        }); 
	   	   	
	   	    $('label[for="psSlotNo"]').after('<label style=color:red;>&nbsp;*</label>');	 	 
	 	    $('label[for="block"]').after('<label style=color:red;>&nbsp;*</label>');	
	 	    $('label[for="psOwnership"]').after('<label style=color:red;>&nbsp;*</label>');	
	 	    $('label[for="psParkingMethod"]').after('<label style=color:red;>&nbsp;*</label>');	
	 	    $('label[for="psActiveDate"]').after('<label style=color:red;>&nbsp;*</label>');	
	 	
   			if (e.model.isNew()){
   				 $(".k-window-title").text("Add Parking Slot Details");
   		     	 $(".k-grid-update").text("Create"); 
   		     	 securityCheckForActions("./parkingManagement/parkingslots/createButton");
   		     	
   		     	 $(".k-grid-update").click(function () {
   		     		 
   		     		 
   		      		 var ownership=$("#ownership").val();
   		      		 var property=$("#propertydrop").data("kendoComboBox");		      		  
   		      		 if(enteredSlotsforblocks!=null && availableSlots!=null){
   		      			 if(enteredSlotsforblocks>=availableSlots){
   		        	    	 alert("No More Slots Can Create For This Block :"+blockSelected);
   		        	    	 return false;
   		    	       	 }
   		      		 }    		 
   		      		 if (property.select() == -1) {
   		     	            alert("Please Select Proper Block");
   		     				return false;	
   		     		 }       		   	  		   	   
   		     		 if(ownership=="Select"){
   		     			 alert("Please Select Owership");      					
   		     			   return false;
   		     		 }if(ownership!="VISITORS"){
   		      			   if($("#parkingmethod").val()=="Select"){
   		      				   alert("Please Select Parking Method");
   		      				   return false;
   		      			   }        			          			   
   			      	 }        		   
   		      	});   		     	
   		     	   
   			 }else{   		
   				 securityCheckForActions("./parkingManagement/parkingslots/updateButton");   				 
   				 slotNo.splice(slotNo.indexOf(e.model.psSlotNo),1);
   				 $(".k-window-title").text("Edit Parking Slot Details");
   		       	 $('.k-edit-field .k-input').first().focus();
   		       	 var v=$("#ownership").val();
   	       		 $('input[name="slotallowed"]').attr('readonly', 'readonly');  
   	     		 if(v=="VISITORS"){
   	     			 	$('label[for="psParkingMethod"]').parent().hide();
   	  				 	$("#parkingmethod").parent().hide();     			
   	     		 }else{
   	     			 $('label[for="psParkingMethod"]').parent().show();
   	  			 	 $("#parkingmethod").parent().show();   		
   	     		 }
   	     			
   	     		 $(".k-grid-update").click(function () {	       		  
   	  	       		   var v=$("#ownership").val();
   	  	       		   var property=$("#propertydrop").data("kendoComboBox");
   	  		       	   if(enteredSlotsforblocks!=null && availableSlots!=null){
   	  		       			if(enteredSlotsforblocks>=availableSlots){
   	  		    	    	 	alert("No More Slots Can Create For This Block :"+blockSelected);
   	  		    	    	 	return false;
   	  			       	   	}
   	  		       	   }	       		   
   	          		   if (property.select() == -1) {
   	         	            alert("Please Select Proper Block");
   	         				return false;	
   	         		   }  
   	  	       		   if(v=="Select"){
   	  	       				alert("Please Select Owership");      					
   	  	       			    return false;
   	  	       		   }if(v!="VISITORS"){
   	  	       			   if($("#parkingmethod").val()=="Select"){
   	  	       				   alert("Please Select Parking Method");
   	  	       				   return false;
   	  	       			   }       			          			   
   	  	       		   }	       			
   	  	         });   	     				       	  
   			  }	
   			
   			  e.container.find(".k-grid-cancel").bind("click", function () {
		         var AccessCardGrid = $("#grid").data("kendoGrid");
		   		 parse(AccessCardGrid._data);
		     }); 
   		  }   		
      
           
         $("#grid").on("click", "#temPID", function(e) {
   			var button = $(this), enable = button.text() == "Activate";
   			var widget = $("#grid").data("kendoGrid");
   			var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));

   			var result=securityCheckForActionsForStatus("./parkingManagement/parkingslots/deleteButton");   
   		    if(result=="success"){
	   			if (enable) {
						$.ajax({
							type : "POST",
							url : "./parkingslots/ParkingSlotStatus/" + dataItem.id + "/activate",
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
					} else {
						$.ajax({
							type : "POST",
							url : "./parkingslots/ParkingSlotStatus/" + dataItem.id + "/deactivate",
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
    					errorInfo += (i + 1) + ". "	+ e.response.result[i].defaultMessage+"<br>";
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
    				$("#alertsBox").html("Record Created SuccessFully");
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
     
	<style>
		.file-icon {
		display: inline-block;
		float: left;
		width: 48px;
		height: 48px;
		margin-left: 10px;
		margin-top: 13.5px;
		}
		
		.img-file {
		background-image: url(./resources/kendo/web/upload/jpg.png)
	}
	
	.doc-file {
		background-image: url(./resources/kendo/web/upload/doc.png)
	}
	
	.pdf-file {
		background-image: url(./resources/kendo/web/upload/pdf.png)
	}
	
	.xls-file {
		background-image: url(./resources/kendo/web/upload/xls.png)
	}
	
	.zip-file {
		background-image: url(./resources/kendo/web/upload/zip.png)
	}
	
	.default-file {
		background-image: url(./resources/kendo/web/upload/default.png)
	}
	
	#example .file-heading {
		font-family: Arial;
		font-size: 1.1em;
		display: inline-block;
		float: left;
		width: 450px;
		margin: 0 0 0 20px;
		height: 25px;
		-ms-text-overflow: ellipsis;
		-o-text-overflow: ellipsis;
		text-overflow: ellipsis;
		overflow: hidden;
		white-space: nowrap;
	}
	
	#example .file-name-heading {
		font-weight: bold;
	}
	
	#example .file-size-heading {
		font-weight: normal;
		font-style: italic;
	}
	
	li.k-file .file-wrapper .k-upload-action {
		position: absolute;
		top: 0;
		right: 0;
	}
	
	li.k-file div.file-wrapper {
		position: relative;
		height: 75px;
	}
	
	.ui-dialog-osx {
	    -moz-border-radius: 0 0 8px 8px;
	    -webkit-border-radius: 0 0 8px 8px;
	    border-radius: 0 0 8px 8px; border-width: 0 8px 8px 8px;
	}
	</style>
<!-- </div> -->