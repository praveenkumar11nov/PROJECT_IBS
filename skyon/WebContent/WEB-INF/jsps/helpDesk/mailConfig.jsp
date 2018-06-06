<%@include file="/common/taglibs.jsp"%>

<!-----------------------urls   -------------------------------->
<c:url value="/mailConfig/read" var="readUrl" />
<c:url value="/mailConfig/add" var="createUrl" />
 <c:url value="/mailConfig/update" var="updateUrl" />
<c:url value="/mailConfig/delete" var="destroyUrl" /> 
<c:url value="/mailConfig/getService" var="serviceType"/>
<script>
function serviceEditor(container, options) 
{
$('<input data-text-field="value" id= "value" data-value-field="value" name="Pet type" data-bind="value:' + options.field + '" required="true"/>')
			.appendTo(container).kendoDropDownList({
				optionLabel : "Select",				
				select: onPrimaryOwnerSelect,
				dataSource : {
					transport : {
						read : "${serviceType}"
					}
				}
				
			});
	 $('<span class="k-invalid-msg" data-for="internalNoteTitle"></span>').appendTo(container); 
}
function onPrimaryOwnerSelect(e) 
{
	
	 var dataItem = this.dataItem(e.item.index());
	  var dataItem = this.dataItem(e.item.index());
        if(dataItem.value != "")
        {
			$.ajax({
				type : "POST",
				url : "./getService/"+dataItem.value,				
				dataType:"text",
				success : function(response)
				{
					
					var dropdownlist = $("#value").data("kendoDropDownList");
					if(response>0)
						{
						alert("You already added Mail Master for "+dataItem.value);
						dropdownlist.text("[Select]");
						dropdownlist.value(-1);
						return false;
						}
					/* if(response > 0)
					{
						window.alert("You will be CO-Owner for this property.");
						dropdownlist.search('No');
						dropdownlist.readonly();
						var primaryOwner = $('#propertyGrid_'+SelectedRowId).data().kendoGrid.dataSource
					      .data()[propertyselectedRowIndex];
						primaryOwner.set('primaryOwner', 'No');
					}
					else
					{
						dropdownlist.search('Yes');
						var primaryOwner = $('#propertyGrid_'+SelectedRowId).data().kendoGrid.dataSource
					      .data()[propertyselectedRowIndex];
						primaryOwner.set('primaryOwner', 'Yes');
						dropdownlist.enable();
					} */

				}
				});
        }    
     
}
function messageEditor(container, options) 
{
    $('<textarea name="mailMessage" data-text-field="mailMessage" data-value-field="mailMessage" data-bind="value:' + options.field + '" style="width: 240px; height: 66px;" placeholder=" "/>')
         .appendTo(container);
   /*  $('<span class="k-invalid-msg" data-for="ssss"></span>').appendTo(container);  */
}
</script>
<kendo:grid name="notiGrid" pageable="true" edit="customerCareEvent" resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true">
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true">
		<kendo:grid-pageable-messages itemsPerPage="Mail Master per page" empty="No Email Master to display" refresh="Refresh all the Mail Master" 
			display="{0} - {1} of {2} Services" first="Go to the first page of Mail Master" last="Go to the last page of Mail Master" next="Go to the Mail Master"
			previous="Go to the previous page of Mail Master"/>
					</kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
		 <kendo:grid-filterable-operators>
		  	<kendo:grid-filterable-operators-string eq="Is equal to"/>
		 </kendo:grid-filterable-operators>
		</kendo:grid-filterable>
		  <kendo:grid-editable mode="popup"
			confirmation="Are you sure you want to remove this Mail Master?" />
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Mail Master" />
		</kendo:grid-toolbar>
		<kendo:grid-toolbarTemplate>
			<div class="toolbar">
			<a class="k-button k-button-icontext k-grid-add" href="#">
		            <span class="k-icon k-add"></span>
		            Add Mail Master
	        	</a>
				 <a class='k-button' href='\\#' onclick="clearFilterNotification()"><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a> 
			</div>  	
    	</kendo:grid-toolbarTemplate>
    	<kendo:grid-columns>
    	<%-- <kendo:grid-column title="Mail Service" field="mailMasterId" width="120px"  filterable="false" hidden="true"/> --%>
		 <kendo:grid-column title="Mail Service" field="mailServiceType" width="120px"  filterable="false" editor="serviceEditor" /> 
		<kendo:grid-column title="Mail Subject" field="mailSubject" width="120px"  filterable="false" />
		<kendo:grid-column title="Mail Message" field="mailMessage" width="120px" filterable="false" editor="messageEditor"/>
		<kendo:grid-column title=" " width="110px"> 
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit"/>
					<kendo:grid-column-commandItem name="destroy"  text="Delete"/>
				</kendo:grid-column-command>
			</kendo:grid-column>
		</kendo:grid-columns>
				
			<kendo:dataSource pageSize="5" requestEnd="onRequestEnd" requestStart="onRequestStart">
			<kendo:dataSource-transport>
			<kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json"/>
			<kendo:dataSource-transport-create url="${createUrl}" dataType="json" type="POST" contentType="application/json"/>
			<kendo:dataSource-transport-update url="${updateUrl}" dataType="json" type="POST" contentType="application/json"/>
			<kendo:dataSource-transport-destroy url="${destroyUrl}" dataType="json" type="POST" contentType="application/json"/>
					<kendo:dataSource-transport-parameterMap>
					<script>
						function parameterMap(options, type) {
							return JSON.stringify(options);
						}
					</script>
				</kendo:dataSource-transport-parameterMap> 
			</kendo:dataSource-transport>
			 <kendo:dataSource-schema>
				<kendo:dataSource-schema-model id="mailMasterId">
					<kendo:dataSource-schema-model-fields>
					
						<kendo:dataSource-schema-model-field name="mailServiceType">
						<kendo:dataSource-schema-model-field-validation required="true"/>
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="mailSubject">
						<kendo:dataSource-schema-model-field-validation required="true"/>
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="mailMessage">
						<kendo:dataSource-schema-model-field-validation required="true"/>
						</kendo:dataSource-schema-model-field>
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
			</kendo:dataSource>				

</kendo:grid>
<div id="alertsBox" title="Alert"></div>


<script>
/*======================================= clear filter===============================================  */
function clearFilterNotification()
{
	  $("form.k-filter-menu button[type='reset']").slice().trigger("click");
	  var grid = $("#notiGrid").data("kendoGrid");
	  grid.dataSource.read();
	  grid.refresh(); 
	  
	
}


$(document).on('change','input[name="radioBtn"]:radio',function() {
	
	var radioValue = $('input[name=radioBtn]:checked').val();
	if (radioValue == 'block') {
		$('div[data-container-for="blockName"]').hide();
		$('label[for="blockName"]').closest('.k-edit-label').hide();
		$('div[data-container-for="propertyNo"]').hide();
		$('label[for="propertyNo"]').closest('.k-edit-label').hide();
		$('div[data-container-for="radioBtn2"]').show();
		$('label[for="radioBtn2"]').closest('.k-edit-label').show();
	}
	if (radioValue == 'property') {
		$('div[data-container-for="radioBtn2"]').hide();
		$('label[for="radioBtn2"]').closest('.k-edit-label').hide();
		$('div[data-container-for="blockName"]').hide();
		$('label[for="blockName"]').closest('.k-edit-label').hide();
		$('div[data-container-for="propertyNo"]').show();
		$('label[for="propertyNo"]').closest('.k-edit-label').show();
	}
	
});
$(document).on('change','input[name="radioBtn2"]:radio',function() {
	var radioValue = $('input[name=radioBtn2]:checked').val();
	if (radioValue == 'all') {
		$('div[data-container-for="blockName"]').hide();
		$('label[for="blockName"]').closest('.k-edit-label').hide();
		$('div[data-container-for="propertyNo"]').hide();
		$('label[for="propertyNo"]').closest('.k-edit-label').hide();
		$('div[data-container-for="radioBtn2"]').show();
		$('label[for="radioBtn2"]').closest('.k-edit-label').show();
	}
	if (radioValue == 'select') {
		$('div[data-container-for="propertyNo"]').hide();
		$('label[for="propertyNo"]').closest('.k-edit-label').hide();
		$('div[data-container-for="blockName"]').show();
		$('label[for="blockName"]').closest('.k-edit-label').show();
		$('div[data-container-for="radioBtn2"]').show();
		$('label[for="radioBtn2"]').closest('.k-edit-label').show();
	}
	
});
function edit(){
	
	$(".k-window-title").text("Add Mail Master");
	$(".k-grid-update").text("Save");
}

$("#notiGrid").on("click", ".k-grid-add", function() {	
	
	$(".k-window-title").text("Add Mail Master");
	$(".k-grid-update").text("Save");
	
	$('div[data-container-for="radioBtn2"]').hide();
	$('label[for="radioBtn2"]').closest('.k-edit-label').hide();
	
	$('div[data-container-for="blockName"]').hide();
	$('label[for="blockName"]').closest('.k-edit-label').hide();
	$('div[data-container-for="propertyNo"]').hide();
	$('label[for="propertyNo"]').closest('.k-edit-label').hide();
	
	
});



function customerCareEvent(e)
{
	 
	if (e.model.isNew()){
		
		securityCheckForActions("./customerCare/Notification/createButton");	 
		$(".k-window-title").text("Add Mail Master Details");
	     	 $(".k-grid-update").text("Save"); 
	     	 
	     	   
		 }else{  
			 
			 $(".k-grid-update").click(function(){
				
			
			 }); 
			
			 securityCheckForActions("./customerCare/Notification/updateButton");   				 
			 $(".k-window-title").text("Edit Mail Master Details");
			 $(".k-grid-update").text("update");
			 
			 $('div[data-container-for="blockName"]').hide();
				$('label[for="blockName"]').closest('.k-edit-label').hide();
				$('div[data-container-for="propertyNo"]').hide();
				$('label[for="propertyNo"]').closest('.k-edit-label').hide();
	          	     				       	  
		  }
	
	
	}  
(function($, kendo) {
	$
			.extend(
					true,
					kendo.ui.validator,
					{
						rules : { // custom rules  
							
						subjectvalidation: function (input, params) 
				             {               	 
				                // check for the name attribute 
				                 if (input.filter("[name='subject']").length && input.val()) 
				                 {
				                	 sub = input.val();
				                	$.each(res, function( index, value ) 
									{	
				          				if((sub == value))
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
				             messagevalidation: function (input, params) 
				             {               	 
				                // check for the name attribute 
				                 if (input.filter("[name='message']").length && input.val()) 
				                 {
				                	 msgName = input.val();
				                	$.each(res, function( index, value ) 
									{	
				          				if((msgName == value))
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
				             
				             lastNameSpacesvalidation : function(input,
										params) {
									 if (input.attr("name") == "Description") {
						                   return $.trim(input.val()) !== "";
						                }
						                return true;

								},
								lastNameSpacesvalidation1 : function(input,
										params) {
									 if (input.attr("name") == "Description1") {
  						                   return $.trim(input.val()) !== "";
  						                }
  						                return true;

								},
								
				             
						},
						messages : {
							messagevalidation: " Message can not allow special symbols except underscore(_) ",
							subjectvalidation: " Subject can not allow special symbols except underscore(_) ",
							lastNameSpacesvalidation: "Subject is Required",
							lastNameSpacesvalidation1: "Message is Required",
						}
					});

})(jQuery, kendo);

 function onRequestStart(e){
	 
	 
	
	
	 
	 $('.k-grid-update').hide();
		$('.k-edit-buttons')
				.append(
						'<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
		$('.k-grid-cancel').hide();
		
	
	
} 
function onRequestEnd(e){
	
	
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
				var grid = $("#notiGrid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			}
		 else if (e.type == "create") {
			 $("#alertsBox").html("Mail Master created successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				var grid = $("#notiGrid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
		 } 
		 else if (e.type == "destroy") {
			 $("#alertsBox").html("");
				$("#alertsBox").html("Mail Master deleted successfully");
						$("#alertsBox").dialog({
							modal : true,
							buttons : {
								"Close" : function() {
									$(this).dialog("close");
								}
							}
						});
				
						var grid = $("#notiGrid").data("kendoGrid");
						grid.dataSource.read();
						grid.refresh();
		 }
		 else if (e.type == "update") {
			 $("#alertsBox").html("");
				$("#alertsBox").html("Mail Master updated successfully");
						$("#alertsBox").dialog({
							modal : true,
							buttons : {
								"Close" : function() {
									$(this).dialog("close");
								}
							}
						});
				
						var grid = $("#notiGrid").data("kendoGrid");
						grid.dataSource.read();
						grid.refresh();
		 }
	}
	
}
function startDateEditor(container, options) {
    $('<input name="' + options.field + '"/>')
            .appendTo(container)
            .kendoDateTimePicker({
                format:"dd/MM/yyyy hh:mm tt",
                timeFormat:"hh:mm tt"         
                
    });
}
function endDateEditor(container, options) {
    $('<input name="' + options.field + '"/>')
            .appendTo(container)
            .kendoDateTimePicker({
                format:"dd/MM/yyyy hh:mm tt",
                timeFormat:"hh:mm tt"         
                
    });



}
</script>