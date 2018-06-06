<%@include file="/common/taglibs.jsp"%>

	<c:url value="/jobtypedetails/create" var="createUrl" />
	<c:url value="/jobtypedetails/read" var="readUrl" />
	<c:url value="/jobtypedetails/update" var="updateUrl" />	
	<c:url value="/jobtypedetails/destroy" var="destroyUrl" />	
	
	<c:url value="/jobtypedetails/getJobTypeForFilter" var="JobTypeFilterUrl" />
	<c:url value="/jobtypedetails/getSlaForFilter" var="SLAFilterUrl" />	
	<c:url value="/jobtypedetails/getCreatedByForFilter" var="CreatedByFilterUrl" />
	<c:url value="/jobtypedetails/getUpdatedByForFilter" var="UpdatedByFilterUrl" />
	
	
	<kendo:grid name="grid" pageable="true" remove="jobtypeRemove" sortable="true" resizable="true"  groupable="true" filterable="true" scrollable="true" height="430px" selectable="true">
		<kendo:grid-editable mode="popup" confirmation="Are You Sure? You Want To Delete The Record?" />
		
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Job Type Details" />
				<kendo:grid-toolbarItem text="Clear Filter" name="Clear_Filter"/>
		</kendo:grid-toolbar>
		
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" ></kendo:grid-pageable>
		
		<kendo:grid-filterable extra="false">
		 <kendo:grid-filterable-operators>
		  	<kendo:grid-filterable-operators-string eq="Is equal to"/>
		  	<kendo:grid-filterable-operators-date gt="Is after" lt="Is before"/>
		  	
		 </kendo:grid-filterable-operators>
			
		</kendo:grid-filterable>
		
		<kendo:grid-columns>
			<kendo:grid-column title="Job Type" field="jtType" width="130px">
			 <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function psSlotNoFilter(element) {
								element.kendoAutoComplete({
								dataSource : {
										transport : {
											read : "${JobTypeFilterUrl}",
										}
									},
								});							
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	      	   
      	   </kendo:grid-column>	      	   
		 
			<kendo:grid-column title="Job Description" filterable="false" field="jtDescription" editor="jobtypeDescription" width="130px"/>				   
      	   
			<kendo:grid-column title="Job Type SLA" field="jtSla"  width="150px">
			 <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function psSlotNoFilter(element) {
								element.kendoAutoComplete({
								dataSource : {
										transport : {
											read : "${SLAFilterUrl}",
										}
									},
								});							
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	      	   
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
	    	
			<kendo:grid-column title=" " width="200px">
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
				<kendo:dataSource-schema-model id="jtId">
					<kendo:dataSource-schema-model-fields>					
						<kendo:dataSource-schema-model-field name="jtType" type="string"/>
						<kendo:dataSource-schema-model-field name="jtDescription" type="string"/>
						<kendo:dataSource-schema-model-field name="jtSla" type="string"/>
						<kendo:dataSource-schema-model-field name="createdBy" type="string"/>
						<kendo:dataSource-schema-model-field name="lastUpdatedBy" type="string"/>
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
	
	var jobtype=[];
	
	function parse (response) {
		jobtype=[];
	    $.each(response, function (idx, elem) {   
	    	jobtype.push(elem.jtType);	
            if (elem.lastUpdatedDate && typeof elem.lastUpdatedDate === "string") {
                elem.lastUpdatedDate = kendo.parseDate(elem.lastUpdatedDate, "yyyy-MM-ddTHH:mm:ss.fffZ");
            }          
        });	    
        return response;
	} 
	
	
	 //register custom validation rules
    (function ($, kendo) {
        $.extend(true, kendo.ui.validator, {
             rules: { // custom rules
                 jobtypevalidation: function (input, params) {
                     //check for the name attribute 
                     if (input.attr("name") == "jtType") {
                    	 return $.trim(input.val()) !== "";
                     }
                     return true;
                 },                
                 jobDescriptionvalidation: function (input, params) {
                     //check for the name attribute 
                     if (input.attr("name") == "jtDescription") {
                    	 return $.trim(input.val()) !== "";
                     }
                     return true;
                 },                 
                 jobSlavalidation: function (input, params) {
                     //check for the name attribute 
                     if (input.attr("name") == "jtSla") {
                    	 return $.trim(input.val()) !== "";
                     }
                     return true;
                 },
                 jobSlaPatternvalidation: function (input, params) {
                     //check for the name attribute 
                     if (input.attr("name") == "jtSla") {
                    	 return /^[0-9]+$/.test(input.val());
                     }
                     return true;
                 },
                 jtTypeUniquevalidation : function(input,params) {
                	 if (input.filter("[name='jtType']").length && input.val()){
							var flag = true;
							var fieldValue = input.val();
							$.each(jobtype, function(idx1, elem1) {								
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
            	 jobtypevalidation: "Enter Job Type",
            	 jobDescriptionvalidation:"Enter Job Description ",
            	 jobSlavalidation:"Enter Job SLA",
            	 jobSlaPatternvalidation:"Only Numbers Are Allowed",
            	 jtTypeUniquevalidation:"Job Type Already Exist"
            	 
            	 
             }
        });
    })(jQuery, kendo);
	 
    function jobtypeDescription(container, options) 
	{
        $('<textarea data-text-field="jtDescription" data-value-field="jtDescription" data-bind="value:' + options.field + '" style="width: 148px; height: 40px;"/>')
             .appendTo(container);
	}	
	
	$("#grid").on("click", ".k-grid-add", function(e) { 
		securityCheckForActions("./maintainance/jobtype/addButton");
		
	    $('input[name="jtSla"]').attr('maxlength', '3'); 
	    $('input[name="jtDescription"]').attr('maxlength', '500'); 
	    $('input[name="jtType"]').attr('maxlength', '50'); 
	     
		if($("#grid").data("kendoGrid").dataSource.filter()){
    		//$("#grid").data("kendoGrid").dataSource.filter({});
    		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
 			var grid = $("#grid").data("kendoGrid");
 			grid.dataSource.read();
 			grid.refresh();
        } 			
	 	 
		 $(".k-window-title").text("Add Job Type Details");
	 	 $(".k-grid-update").text("Create");	 
	 	 
	 	 $('label[for="jtType"]').after('<label style=color:red;>&nbsp;*</label>');	 	 
	 	 $('label[for="jtSla"]').after('<label style=color:red;>&nbsp;*</label>');	
	 	 
	 	 $('label[for="createdBy"]').parent().remove();
	   	 $('label[for="lastUpdatedBy"]').parent().remove();
	     $('label[for="lastUpdatedDate"]').parent().remove();
	     $('input[name="createdBy"]').parent().remove();
	     $('input[name="lastUpdatedBy"]').parent().remove();		 
	     $('input[name="lastUpdatedDate"]').parent().remove();	 
	     $('.k-edit-field .k-input').first().focus(); 
	     
 	});

     function edit(e) {
    	 securityCheckForActions("./maintainance/jobtype/editButton");
    	 $('label[for="createdBy"]').parent().remove();
	   	 $('label[for="lastUpdatedBy"]').parent().remove();
	     $('label[for="lastUpdatedDate"]').parent().remove();
	     $('input[name="createdBy"]').parent().remove(); 
	     $('input[name="lastUpdatedBy"]').parent().remove();		 
	     $('input[name="lastUpdatedDate"]').parent().remove(); 	  
	     $('input[name="jtSla"]').attr('maxlength', '3'); 
		 $('input[name="jtDescription"]').attr('maxlength', '500'); 
		 $('input[name="jtType"]').attr('maxlength', '50'); 
		  
    	 jobtype.splice(jobtype.indexOf($('input[name="jtType"]').val()),1);    	 
    	 if($("#grid").data("kendoGrid").dataSource.filter()){     		
     		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
  			var grid = $("#grid").data("kendoGrid");
  			grid.dataSource.read();
  			grid.refresh();
         } 
    	 
    	 $(".k-window-title").text("Edit Job Type Details");
    	 $('.k-edit-field .k-input').first().focus();     
    	 
    	 $('label[for="jtType"]').after('<label style=color:red;>&nbsp;*</label>');	 	 
	 	 $('label[for="jtSla"]').after('<label style=color:red;>&nbsp;*</label>');	 	 
	 	 
	     
     	$(".k-grid-cancel").bind("click", function () {
        	var AccessCardGrid = $("#grid").data("kendoGrid");
   			parse(AccessCardGrid._data);
    	}); 
   	 
     }  
     
     function jobtypeRemove(e){
    	 securityCheckForActions("./maintainance/jobtype/deleteButton");
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
   			$("#alertsBox").html("Record Not Updated");
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
