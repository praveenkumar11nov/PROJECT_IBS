<%@include file="/common/taglibs.jsp"%>

<!-----------------------urls   -------------------------------->
<c:url value="/customerCareNotification/read" var="readUrl" />
<c:url value="/customerCareNotification/add" var="createUrl" />
<c:url value="/customerCareNotification/update" var="updateUrl" />
<c:url value="/customerCareNotification/delete" var="destroyUrl" />

<c:url value="/customerCareNotification/getBlockNames" var="getBlockNamesUrl" />
<c:url value="/customerCareNotification/getProperties" var="getPropertyNamesUrl" />

<kendo:grid name="notiGrid" pageable="true" edit="customerCareEvent" resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true">
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true">
		<kendo:grid-pageable-messages itemsPerPage="Notification per page" empty="No Notification to display" refresh="Refresh all the Notification" 
			display="{0} - {1} of {2} Services" first="Go to the first page of Notification" last="Go to the last page of Notification" next="Go to the Notification"
			previous="Go to the previous page of Notification"/>
					</kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
		 <kendo:grid-filterable-operators>
		  	<kendo:grid-filterable-operators-string eq="Is equal to"/>
		 </kendo:grid-filterable-operators>
			
		</kendo:grid-filterable>
		  <kendo:grid-editable mode="popup"
			confirmation="Are you sure you want to remove this Notification?" />
		<kendo:grid-toolbar>
		
			<kendo:grid-toolbarItem name="create" text="Add Notification" />
		</kendo:grid-toolbar>
		<kendo:grid-toolbarTemplate>
			<div class="toolbar">
			<a class="k-button k-button-icontext k-grid-add" href="#">
		            <span class="k-icon k-add"></span>
		            Add Notification
	        	</a>
				 <a class='k-button' href='\\#' onclick="clearFilterNotification()"><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a> 
			</div>  	
    	</kendo:grid-toolbarTemplate>
    	<kendo:grid-columns>
    	<kendo:grid-column title="&nbsp;" field="radioBtn" editor="radioEditor" width="110px" hidden="true" />
    	<kendo:grid-column title="&nbsp;" field="radioBtn2" editor="radioEditor2" width="110px" hidden="true" />
		<kendo:grid-column title="Block Names *" field="blockName" width="120px" editor="blockEditor" filterable="false">
		</kendo:grid-column>
		<kendo:grid-column title="property Numbers *" field="propertyNo" width="120px" editor="proprtyEditor" filterable="false">
		</kendo:grid-column>
		<kendo:grid-column hidden="true"/>
		<kendo:grid-column title="Subject*" field="subject" width="120px" editor="subjectEditor" filterable="false"/>
		<kendo:grid-column title="Message*" field="message" width="120px" editor="messageEditor" filterable="false">
		</kendo:grid-column>
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
			<%-- <kendo:dataSource-transport>
				<kendo:dataSource-transport-read url="${readUrl}" dataType="json"
					type="POST" contentType="application/json" />
				<kendo:dataSource-transport-create url="${createUrl}"
					dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-update url="${updateUrl}"
					dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-destroy url="${deleteServiceUrl}"
					dataType="json" type="POST" contentType="application/json" />
				 <kendo:dataSource-transport-parameterMap>
					<script>
						function parameterMap(options, type) {
							return JSON.stringify(options);
						}
					</script>
				</kendo:dataSource-transport-parameterMap> 
			</kendo:dataSource-transport> --%>
			 <kendo:dataSource-schema>
				<kendo:dataSource-schema-model id="cnId">
					<kendo:dataSource-schema-model-fields>
					<kendo:dataSource-schema-model-field name="cnId" type="number" />
						<kendo:dataSource-schema-model-field name="blockId" />
						<kendo:dataSource-schema-model-field name="propertyId">
						<kendo:dataSource-schema-model-field-validation required="true"/>
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="viewStatus" type="number">
						<kendo:dataSource-schema-model-field-validation  />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="subject">
						<kendo:dataSource-schema-model-field-validation required="true"/>
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="message">
						<kendo:dataSource-schema-model-field-validation required="true"/>
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="createdBy" >
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="lastUpdatedBy">
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="lastUpdatedDt" type="date"/>
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
function subjectEditor(container, options) 
{
    $('<textarea name="Description" data-text-field="subject" data-value-field="sebject" data-bind="value:' + options.field + '" style="width: 240px; height: 30px;" placeholder=" "/>')
         .appendTo(container);
   /*  $('<span class="k-invalid-msg" data-for="ssss"></span>').appendTo(container);  */
}

function messageEditor(container, options) 
{
    $('<textarea name="Description1" data-text-field="message" data-value-field="message" data-bind="value:' + options.field + '" style="width: 240px; height: 66px;" placeholder=" "/>')
         .appendTo(container);
   /*  $('<span class="k-invalid-msg" data-for="ssss"></span>').appendTo(container);  */
}
function proprtyEditor( container, options ){
	
	 $(
				'<select name="multiple" multiple="multiple" data-text-field="propertyNo" id="propertyId" data-value-field="propertyId" data-bind="value:' + options.field + '" />')
				.appendTo(container)
					.kendoMultiSelect({
						/*  optionLabel : "Select",  */
						  placeholder:"Select", 
						 cascadeFrom : "blockId",
						 dataSource: {  
				             transport:{
				                 read: "${getPropertyNamesUrl}"
				             }
				         },
				 });
				  /* $('<span class="k-invalid-msg" data-for="This field"></span>').appendTo(container);  */
	
}
function blockEditor( container, options ){
	
	 $(
				'<select multiple="multiple" data-text-field="blockName" id="blockId" data-value-field="blockId" data-bind="value:' + options.field + '" />')
				.appendTo(container)
					.kendoMultiSelect({
						/*  optionLabel : "Select",  */
						  placeholder:"Select", 
						  dataSource: {  
					             transport:{
					                 read: "${getBlockNamesUrl}"
					             }
					         },
				 });
				  /* $('<span class="k-invalid-msg" data-for="This field"></span>').appendTo(container);  */
	
}
function radioEditor(container, options) {$(
		'<input type="radio" name=' + options.field + ' value="block" /> Blocks &nbsp;&nbsp;&nbsp; <input type="radio" name=' + options.field + ' value="property"/> Property &nbsp;&nbsp;&nbsp;  <br>')
		.appendTo(container);
}
function radioEditor2(container, options) {$(
		'<input type="radio" name=' + options.field + ' id="all" value="all" /> All &nbsp;&nbsp;&nbsp; <input type="radio" name=' + options.field + ' value="select"/> Select Blocks &nbsp;&nbsp;&nbsp;  <br>')
		.appendTo(container);
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
	
	$(".k-window-title").text("Add Notification");
	$(".k-grid-update").text("Save");
}

$("#notiGrid").on("click", ".k-grid-add", function() {	
	
	$(".k-window-title").text("Add Notification");
	$(".k-grid-update").text("Save");
	
	$('div[data-container-for="radioBtn2"]').hide();
	$('label[for="radioBtn2"]').closest('.k-edit-label').hide();
	
	$('div[data-container-for="blockName"]').hide();
	$('label[for="blockName"]').closest('.k-edit-label').hide();
	$('div[data-container-for="propertyNo"]').hide();
	$('label[for="propertyNo"]').closest('.k-edit-label').hide();
	
	
});

/* $("#notiGrid").on("click", ".k-grid-edit", function() {	
	
	$(".k-window-title").text("Edit Notification");
	$(".k-grid-update").text("Save");
	
	$('div[data-container-for="radioBtn2"]').hide();
	$('label[for="radioBtn2"]').closest('.k-edit-label').hide();
	
	$('div[data-container-for="blockName"]').hide();
	$('label[for="blockName"]').closest('.k-edit-label').hide();
	$('div[data-container-for="propertyNo"]').hide();
	$('label[for="propertyNo"]').closest('.k-edit-label').hide();
	
	
});
 */


function customerCareEvent(e)
{
	 
	if (e.model.isNew()){
		
		securityCheckForActions("./customerCare/Notification/createButton");	 
		$(".k-window-title").text("Add Notification Details");
	     	 $(".k-grid-update").text("Save"); 
	     	 
	     	   
		 }else{  
			 
			 $(".k-grid-update").click(function(){
				 var radioValue = $('input[name=radioBtn]:checked').val();
				if (radioValue == 'property')
					{
					var propertyNo=$("#propertyId").val();
					 if(!propertyNo)
						 {
						 alert("please select Property Number");
						 return false;
						 }
					
					}
				 var radioValue = $('input[name=radioBtn2]:checked').val();
				 if (radioValue == 'all') {
						
					}
					else if(radioValue == 'select')
						{
						var blockId=$("#blockId").val();
						 if(!blockId)
							 {
							 alert("please select Block Name");
							 return false;
							 }
						}
					else
						{
						alert("Please select Block");
						return false;
						}
			 }); 
			
			 securityCheckForActions("./customerCare/Notification/updateButton");   				 
			 $(".k-window-title").text("Edit Notification Details");
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
			 $("#alertsBox").html("Notification created successfully");
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
				$("#alertsBox").html("Notification record deleted successfully");
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
				$("#alertsBox").html("Notification record updated successfully");
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

</script>