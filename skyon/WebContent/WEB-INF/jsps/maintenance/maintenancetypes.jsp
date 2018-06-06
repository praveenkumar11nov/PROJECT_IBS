<%@include file="/common/taglibs.jsp"%>

    <c:url value="/maintenancetypedetails/create" var="createUrl" />
	<c:url value="/maintenancetypedetails/read" var="readUrl" />
	<c:url value="/maintenancetypedetails/update" var="updateUrl" />	
	<c:url value="/maintenancetypedetails/destroy" var="destroyUrl" />
	<c:url value="/maintenancetypedetails/getmaintenanceTypeForFilter" var="maintenanceTypeFilterUrl" />
	<c:url value="/maintenancetypedetails/getCreatedByForFilter" var="CreatedByFilterUrl" />
	<c:url value="/maintenancetypedetails/getUpdatedByForFilter" var="UpdatedByFilterUrl" />
	
	
	<kendo:grid name="grid" pageable="true" remove="maintainanceRemove" sortable="true" resizable="true"  groupable="true" filterable="true" scrollable="true" height="430px" selectable="true">
		<kendo:grid-editable mode="popup" confirmation=" Are You Sure? You Want To Delete The Record?" />
		
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Maintenance Type Details" />
				<kendo:grid-toolbarItem text="Clear Filter" name="Clear_Filter"/>
		</kendo:grid-toolbar>
		
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" ></kendo:grid-pageable>
		
		<kendo:grid-filterable extra="false">
		 <kendo:grid-filterable-operators>
		  	<kendo:grid-filterable-operators-string eq="Is equal to"/>
		 </kendo:grid-filterable-operators>
			
		</kendo:grid-filterable>
		
		<kendo:grid-columns>
			<kendo:grid-column title="Maintenance Type" field="maintainanceType" width="130px">
			 <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function psSlotNoFilter(element) {
								element.kendoAutoComplete({
								dataSource : {
										transport : {
											read : "${maintenanceTypeFilterUrl}",
										}
									},
								});							
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	      	   
      	   </kendo:grid-column>	      	   
		 
			<kendo:grid-column title="Description" filterable="false" field="description" editor="maintenanceDescription" width="130px"/>
				   
			<kendo:grid-column title="Date & Time" field="mtDt"  editor="dateEditor" format="{0:dd/MM/yyyy hh:mm tt}" width="150px">			      	   
      	   		<kendo:grid-column-filterable ui="datetimepicker"></kendo:grid-column-filterable>
      	   	</kendo:grid-column>
      	   	
			<kendo:grid-column title="Created By" field="createdBy" width="130px">
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
      	   
			<kendo:grid-column title="Last Updated By" field="lastUpdatedBy" width="130px">
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
      	   
			<kendo:grid-column title="Last Updated Date" format= "{0:dd/MM/yyyy hh:mm tt}" field="lastUpdatedDate" width="150px">
				<kendo:grid-column-filterable ui="datetimepicker"></kendo:grid-column-filterable>
			</kendo:grid-column>
			<kendo:grid-column title="&nbsp;" width="200px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit" click="edit" />
					<kendo:grid-column-commandItem name="destroy"  text="Delete"/>
				</kendo:grid-column-command>
			</kendo:grid-column>				
		
		</kendo:grid-columns>
		<kendo:dataSource pageSize="20" requestEnd="onRequestEnd" requestStart="onRequestStart">
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
				<kendo:dataSource-schema-model id="mtId">
					<kendo:dataSource-schema-model-fields>
					
						<kendo:dataSource-schema-model-field name="maintainanceType" type="string" />
						<kendo:dataSource-schema-model-field name="description" type="string"/>
						<kendo:dataSource-schema-model-field name="mtDt" type="date"/>
						<kendo:dataSource-schema-model-field name="createdBy"/>
						<kendo:dataSource-schema-model-field name="lastUpdatedBy"/>
						<kendo:dataSource-schema-model-field name="lastUpdatedDate" type="date"/>
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
	</kendo:grid>
	<div id="alertsBox" title="Alert"></div>  	
	<script>  	
	
	
	$("#grid").on("click", ".k-grid-Clear_Filter", function(){
	    $("form.k-filter-menu button[type='reset']").slice().trigger("click");
		var grid = $("#grid").data("kendoGrid");
		grid.dataSource.read();
		grid.refresh();
	});
	
	function dateEditor(container, options) {
	    $('<input name="' + options.field + '"/>')
	            .appendTo(container)
	            .kendoDateTimePicker({
	                format:"dd/MM/yyyy hh:mm tt",
	                timeFormat:"hh:mm tt"         
	                
	            });
	}	
	
	var maintainancetype=[];
	function parse (response) {  
		maintainancetype=[];
	    $.each(response, function (idx, elem) {	    
	    	maintainancetype.push(elem.maintainanceType);	
            if (elem.mtDt && typeof elem.mtDt === "string") {
                elem.mtDt = kendo.parseDate(elem.mtDt, "yyyy-MM-ddTHH:mm:ss.fffZ");
            }
            if (elem.lastUpdatedDate && typeof elem.lastUpdatedDate === "string") {
                elem.lastUpdatedDate = kendo.parseDate(elem.lastUpdatedDate, "yyyy-MM-ddTHH:mm:ss.fffZ");
            }
        });
        return response;
	}  
	
	
	function maintenanceDescription(container, options) 
	{
        $('<textarea name="description" data-text-field="description" data-value-field="description" data-bind="value:' + options.field + '" style="width: 180px; height: 40px;"/>')
             .appendTo(container);
	}
		  
	 //register custom validation rules
    (function ($, kendo) {
        $.extend(true, kendo.ui.validator, {
             rules: { // custom rules
            	 maintainanceTypevalidation: function (input, params) {
                     //check for the name attribute 
                     if (input.attr("name") == "maintainanceType") {
                    	 return $.trim(input.val()) !== "";
                     }
                     return true;
                 },                
                 /* descriptionvalidation: function (input, params) {
                     //check for the name attribute 
                     if (input.attr("name") == "description") {
                    	 return $.trim(input.val()) !== "";
                     }
                     return true;
                 }, */  
                 descLengthValidation: function (input, params){
               if (input.attr("name") == "description") { 	 
		       	  if (input.filter("[name='description']").length && input.val() != "") {
		            	 return /^[\s\S]{1,500}$/.test(input.val());
		             }  
                 }
	             return true;
	                },
                 mtDtvalidation: function (input, params) {
                     //check for the name attribute 
                     if (input.attr("name") == "mtDt") {
                    	 return $.trim(input.val()) !== "";
                     }
                     return true;
                 },
                 maintainanceTypeUniquevalidation : function(input,params) {
                	 if (input.filter("[name='maintainanceType']").length && input.val()){
							var flag = true;
							var fieldValue = input.val();
							$.each(maintainancetype, function(idx1, elem1) {								
								if(elem1 == fieldValue.toUpperCase()){
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
            	 maintainanceTypevalidation: "Enter Maintenance Type",
            	 descriptionvalidation:"Enter Maintenance Type Description ",
            	 mtDtvalidation:"Enter Maintenance Type Date And Time", 
            	 maintainanceTypeUniquevalidation:"Maintainance Type Already Exist",  
            	 descLengthValidation:"Description field allows max 500 characters",
             }
        });
    })(jQuery, kendo);	 
	
	
	$("#grid").on("click", ".k-grid-add", function(e) {  
		
		securityCheckForActions("./maintainance/maintainancetype/addButton");
		
		if($("#grid").data("kendoGrid").dataSource.filter()){
    		//$("#grid").data("kendoGrid").dataSource.filter({});
    		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
 			var grid = $("#grid").data("kendoGrid");
 			grid.dataSource.read();
 			grid.refresh();
        } 
		
		 $(".k-window-title").text("Add Maintenance Type Details");
	 	 $(".k-grid-update").text("Create");	 	 
	 	 
	 	 $('label[for="maintainanceType"]').after('<label style=color:red;>&nbsp;*</label>');	 	 
	 	 $('label[for="mtDt"]').after('<label style=color:red;>&nbsp;*</label>');	
	 	 
		 $('label[for="createdBy"]').parent().remove();
	   	 $('label[for="lastUpdatedBy"]').parent().remove();
	     $('label[for="lastUpdatedDate"]').parent().remove();
	     $('input[name="createdBy"]').parent().remove();
	     $('input[name="lastUpdatedBy"]').parent().remove();		 
	     $('input[name="lastUpdatedDate"]').parent().remove(); 	
	     
	     $('input[name="maintainanceType"]').css('width', '180px');
	     
	     $('input[name="description"]').attr('maxlength', '200'); 
		 $('input[name="maintainanceType"]').attr('maxlength', '45'); 
		
	     
 	});

     function edit(e) {
    	 
    	 securityCheckForActions("./maintainance/maintainancetype/editButton");
    	 maintainancetype.splice(maintainancetype.indexOf($('input[name="maintainanceType"]').val()),1);
    	 if($("#grid").data("kendoGrid").dataSource.filter()){
     		//$("#grid").data("kendoGrid").dataSource.filter({});
     		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
  			var grid = $("#grid").data("kendoGrid");
  			grid.dataSource.read();
  			grid.refresh();
         } 
    
    	 $(".k-window-title").text("Add Maintenance Type Details");
	 	 $(".k-grid-update").text("Create");	
				 	 
	     $('input[name="maintainanceType"]').css('width', '180px');
	     
	     $('input[name="description"]').attr('maxlength', '200'); 
		 $('input[name="maintainanceType"]').attr('maxlength', '45'); 
		
	 	 $('label[for="maintainanceType"]').after('<label style=color:red;>&nbsp;*</label>');	 	 
	 	 $('label[for="mtDt"]').after('<label style=color:red;>&nbsp;*</label>');	 		 	
	 	  
		 $('label[for="createdBy"]').parent().remove();
	   	 $('label[for="lastUpdatedBy"]').parent().remove();
	     $('label[for="lastUpdatedDate"]').parent().remove();
	     $('input[name="createdBy"]').parent().remove();
	     $('input[name="lastUpdatedBy"]').parent().remove();		 
	     $('input[name="lastUpdatedDate"]').parent().remove();	     
   	 
     }  
     
     function maintainanceRemove(e){
    	 securityCheckForActions("./maintainance/maintainancetype/deleteButton");
     }
      
     function onRequestStart(e){
    	 $('.k-grid-update').hide();
         $('.k-edit-buttons')
                 .append(
                         '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
         $('.k-grid-cancel').hide();
         
 		/* if (e.type == "create"){
 			var gridStoreGoodsReturn = $("#grid").data("kendoGrid");
 			gridStoreGoodsReturn.cancelRow();		
 		} */	
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
   			$("#alertsBox").html("Record Updated Un-Successfully");
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
   		
   		if (e.type == "destroy" && !e.response.Errors) {
   			$("#alertsBox").html("");
   			$("#alertsBox").html("Record Deleted Successfully");
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
<style>
	td{
		vertical-align: top;
		padding: 5px;
	}
</style>
