<%@include file="/common/taglibs.jsp"%>

<c:url value="/toolmasterdetails/create" var="createUrl" />
<c:url value="/toolmasterdetails/read" var="readUrl" />
<c:url value="/toolmasterdetails/update" var="updateUrl" />	
<c:url value="/toolmasterdetails/destroy" var="destroyUrl" />
<c:url value="/toolmasterdetails/getToolNameForFilter" var="ToolNameFilterUrl" />
<c:url value="/toolmasterdetails/getToolQuantityForFilter" var="ToolQuantityFilterUrl" />
<c:url value="/toolmasterdetails/getCreatedByForFilter" var="CreatedByFilterUrl" />
<c:url value="/toolmasterdetails/getUpdatedByForFilter" var="UpdatedByFilterUrl" />
	
	
<kendo:grid name="grid" pageable="true" remove="toolmasterRemove" sortable="true" resizable="true"  groupable="true" filterable="true" scrollable="true" height="430px" selectable="true">
	<kendo:grid-editable mode="popup" confirmation="Are You Sure? You Want To Delete The Record?" />
	
	<kendo:grid-toolbar>
		<kendo:grid-toolbarItem name="create" text="Add Tool Master Details" />
	    <kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterToolMaster()><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>"/>
	</kendo:grid-toolbar>
	
	<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" ></kendo:grid-pageable>
	
	<kendo:grid-filterable extra="false">
	 <kendo:grid-filterable-operators>
	  	<kendo:grid-filterable-operators-string eq="Is equal to"/>
	  	<kendo:grid-filterable-operators-date lte="Created Before" gte="Created After"/>
	 </kendo:grid-filterable-operators>
		
	</kendo:grid-filterable>
	
	<kendo:grid-columns>
		<kendo:grid-column title="Tool Name" field="tmName" width="130px">
		 <kendo:grid-column-filterable>
    			<kendo:grid-column-filterable-ui>
   					<script> 
						function psSlotNoFilter(element) {
							element.kendoAutoComplete({
							dataSource : {
									transport : {
										read : "${ToolNameFilterUrl}",
									}
								},
							});							
				  		}
				  	</script>		
    			</kendo:grid-column-filterable-ui>
    		</kendo:grid-column-filterable>	      	   
     	   </kendo:grid-column>	      	   
	 
		<kendo:grid-column title="Tool Description" field="description" filterable="false" editor="toolmasterDescription" width="130px"/>
		<kendo:grid-column title="Tool Quantity" field="tmQuantity"  width="150px">
		 <kendo:grid-column-filterable>
    			<kendo:grid-column-filterable-ui>
   					<script> 
						function psSlotNoFilter(element) {
							element.kendoAutoComplete({
							dataSource : {
									transport : {
										read : "${ToolQuantityFilterUrl}",
									}
								},
							});							
				  		}
				  	</script>		
    			</kendo:grid-column-filterable-ui>
    		</kendo:grid-column-filterable>	      	   
     	   </kendo:grid-column>	
     	   
		<kendo:grid-column title="Created By" field="createdBy" hidden="true" width="130px">
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
     	   
		<kendo:grid-column title="Last Updated Date" field="lastUpdatedDate" width="150px" format= "{0:dd/MM/yyyy}">

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
			<kendo:dataSource-schema-model id="tmId">
				<kendo:dataSource-schema-model-fields>					
					<kendo:dataSource-schema-model-field name="tmName" type="string"/>
					<kendo:dataSource-schema-model-field name="description" type="string"/>
					<kendo:dataSource-schema-model-field name="tmQuantity" type="string"/>
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

	function clearFilterToolMaster()
	{
	   $("form.k-filter-menu button[type='reset']").slice().trigger("click");
	   var gridStoreIssue = $("#grid").data("kendoGrid");
	   gridStoreIssue.dataSource.read();
	   gridStoreIssue.refresh();
	}

	var toolname=[];
	function parse (response) { 
		toolname=[];
	    $.each(response, function (idx, elem) {
	    	toolname.push(elem.tmName);	
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
	           	tmNamevalidation: function (input, params) {
	                   //check for the name attribute 
	                   if (input.attr("name") == "tmName") {
	                  	 return $.trim(input.val()) !== "";
	                   }
	                   return true;
	               },                           
	               tmQuantityvalidation: function (input, params) {
	                   //check for the name attribute 
	                   if (input.attr("name") == "tmQuantity") {
	                  	 return $.trim(input.val()) !== "";
	                   }
	                   return true;
	               },    
	               tmQuantityPatternvalidation: function (input, params) {
	                   //check for the name attribute                   
	                   if (input.filter("[name='tmQuantity']").length && input.val()) {
	                       return /^[0-9]+$/.test(input.val());
	                   }
	                   return true;
	               }, 
	               tmNameUniquevalidation : function(input,params) {
	                	 if (input.filter("[name='tmName']").length && input.val()){
								var flag = true;
								var fieldValue = input.val();
								$.each(toolname, function(idx1, elem1) {								
									if(elem1 == fieldValue.toUpperCase()){
										flag = false;
									}	
								});
								return flag;
							}
							return true;
					},
					toolNameLengthValidation : function (input, params){         
	                        if (input.filter("[name='tmName']").length && input.val() != "") 
	                        {
	                        return /^[\s\S]{1,45}$/.test(input.val());
	                        }        
	                        return true;
	                    },
                    toolDescLengthValidation : function (input, params){ 
                    	  if (input.attr("name") == "description") { 
	                        if (input.filter("[name='description']").length && input.val() != "") 
	                        {
	                        return /^[\s\S]{1,500}$/.test(input.val());
	                        } 
                         }
	                        return true;
	                    },
	           },
	         //custom rules messages
	           messages: { 
	           	tmNamevalidation: "Tool Name is required",
	           	tmQuantityvalidation:"Quantity is required", 
	           	tmQuantityPatternvalidation:"Only Numbers Are Allowed",
	           	tmNameUniquevalidation:"Tool Name is Already Exists",
	           	toolNameLengthValidation : "Tool Name allows max 45 characters",
				toolDescLengthValidation : "Tool Description allows max 500 characters", 

	           }
	      });
 	})(jQuery, kendo);
 
	function toolmasterDescription(container, options){
	      $('<textarea name="description" data-text-field="description" data-value-field="description" data-bind="value:' + options.field + '" style="width: 148px; height: 40px;"/>')
	           .appendTo(container);     
	}

	$("#grid").on("click", ".k-grid-Clear_Filter", function(){
	    $("form.k-filter-menu button[type='reset']").slice().trigger("click");
		var grid = $("#grid").data("kendoGrid");
		grid.dataSource.read();
		grid.refresh();
	});

	$("#grid").on("click", ".k-grid-add", function(e) {     		
		
		 securityCheckForActions("./maintainance/toolmaster/addButton");
		
		 if($("#grid").data("kendoGrid").dataSource.filter()){
	    		//$("#grid").data("kendoGrid").dataSource.filter({});
	    		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
	 			var grid = $("#grid").data("kendoGrid");
	 			grid.dataSource.read();
	 			grid.refresh();
	     } 
		 
		 $(".k-window-title").text("Add Tool Master Details");
	 	 $(".k-grid-update").text("Create");	 
	 	 $('.k-edit-field .k-input').first().focus();      	 
	 	 $('label[for="createdBy"]').parent().remove();
	   	 $('label[for="lastUpdatedBy"]').parent().remove();
	     $('label[for="lastUpdatedDate"]').parent().remove();
	     $('input[name="createdBy"]').parent().remove();
	     $('input[name="lastUpdatedBy"]').parent().remove();		 
	     $('div[data-container-for="lastUpdatedDate"]').hide(); 
	     
	     $('label[for="tmQuantity"]').after('<span style=color:red;>&nbsp;*</span>');    
	     $('label[for="tmName"]').after('<span style=color:red;>&nbsp;*</span>');   	 
	});

    function edit(e) {
    	 securityCheckForActions("./maintainance/toolmaster/editButton");
    	 
    	 if($("#grid").data("kendoGrid").dataSource.filter()){
     		//$("#grid").data("kendoGrid").dataSource.filter({});
     		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
  			var grid = $("#grid").data("kendoGrid");
  			grid.dataSource.read();
  			grid.refresh();
         } 
    	 
	   	 $(".k-window-title").text("Edit Tool Master Details");
	   	 $('.k-edit-field .k-input').first().focus();      	 
	 	 $('label[for="createdBy"]').parent().remove();
	   	 $('label[for="lastUpdatedBy"]').parent().remove();
	     $('label[for="lastUpdatedDate"]').parent().remove();
	     $('input[name="createdBy"]').parent().remove();
	     $('input[name="lastUpdatedBy"]').parent().remove();		 
	     $('div[data-container-for="lastUpdatedDate"]').hide();    
	     toolname.splice(toolname.indexOf($('input[name="tmName"]').val()),1);
	     
	     $(".k-grid-cancel").bind("click", function () {
	        	var AccessCardGrid = $("#grid").data("kendoGrid");
	   			parse(AccessCardGrid._data);
	     }); 
  	 
    }  
        
   function toolmasterRemove(e){
   		securityCheckForActions("./maintainance/toolmaster/deleteButton");
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

<style>
	td{
		vertical-align: top;
		padding: 5px;
	}
</style>
