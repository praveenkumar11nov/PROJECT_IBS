<%@include file="/common/taglibs.jsp"%>
<!-- <script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script> -->
<!-- <script src="//code.jquery.com/jquery-1.8.3.min.js"></script> -->
<c:url value="/batchUpdate/batchCreate" var="createUrl" />
<c:url value="/batchUpdate/batchRead" var="readUrl" />
<c:url value="/batchUpdate/batchDestroy" var="destroyUrl" />
<c:url value="/batchUpdate/batchUpdate" var="updateUrl" /> 

<c:url value="/bills/readTowerNames" var="towerNames" />
<c:url value="batchUpdate/presentStatus" var="presentStatus" />
<c:url value="/grid/excel-export/save" var="saveUrl" />

  <kendo:grid name="batchUpdateGrid" dataBound="batchUpdateDataBound" change="onChangeBatchUpdate" edit="batchUpdateEvent" selectable="true" resizable="true" groupable="true" pageable="true" sortable="true" scrollable="true" navigatable="true" editable="true">
  <%--  <kendo:grid-toolbar>
        	<kendo:grid-toolbarItem name="excel"></kendo:grid-toolbarItem>
   </kendo:grid-toolbar>
    
   <kendo:grid-excel fileName="BatchBills.xlsx" filterable="true" proxyURL="${saveUrl}"/> --%>
   
   <kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" refresh="true" info="true" previousNext="true">
	<kendo:grid-pageable-messages itemsPerPage="Items per page" empty="No tem to display" refresh="Refresh all the  items" 
			display="{0} - {1} of {2} New Items" first="Go to the first page of items" last="Go to the last page of items" next="Go to the next page of items"
			previous="Go to the previous page of items"/>
	</kendo:grid-pageable>
	<%-- <kendo:grid-editable mode="popup"
		confirmation="Are you sure you want to remove this Bill Detail?" /> --%>
        <kendo:grid-toolbar>
           <%--  <kendo:grid-toolbarItem name="create"/> --%>
           <kendo:grid-toolbarItem name="createBatch" text="Create Batch" />
            <kendo:grid-toolbarItem name="save" text="Bill All"/>
            <kendo:grid-toolbarItem name="exportToExcel" text="Export To Excel" />
            <kendo:grid-toolbarItem name="cancel"/>
            
        </kendo:grid-toolbar>
        <kendo:grid-columns>
        <kendo:grid-column title="&nbsp;" width="128px">
			<kendo:grid-column-command>				
				<kendo:grid-column-commandItem name="destroy" />
			</kendo:grid-column-command>
		</kendo:grid-column>
            <kendo:grid-column title="Status Id" field="batchUpdateId" width="70px" hidden="true" filterable="false" sortable="false" />
            <kendo:grid-column title="Account Number" field="accountNo" width="105px"  />
            <kendo:grid-column title="Service Type" field="serviceType" width="80px"/>
            <kendo:grid-column title="Present Bill Date" field="presentBillDate" format="{0:dd/MM/yyyy}" width="105px" />
            <kendo:grid-column title="Present Status" field="presentStatus" width="100px"/>
            <kendo:grid-column title="Present Reading" field="presentReading" format="{0:#.00}" editor="presentReadingEditor" width="105px" />
            <kendo:grid-column title="DG Present Reading" field="dgPresentReading" editor="dgPresentReadingEditor" format="{0:#.00}" width="125px"/>
             
             <kendo:grid-column title="Previous Reading" field="previousReading" width="110px" />
            <kendo:grid-column title="DG Previous Reading" field="dgPreviousReading" width="130px" />
            <kendo:grid-column title="Previous Bill Date" field="previousBillDate" format="{0:dd/MM/yyyy}" width="110px" />
            <kendo:grid-column title="Previous Status" field="previousStatus" width="100px" />
           
            <%-- <kendo:grid-column title="Units" field="units" width="80px" />
            <kendo:grid-column title="Average Units" field="averageUnits" width="100px" />
            <kendo:grid-column title="DG Units" field="dgUnits" width="120px" /> --%>
            <kendo:grid-column title="Meter Constant" field="meterConstant" width="100px" />
            <kendo:grid-column title="DG Meter Constant" field="dgMeterConstant" width="120px" />
            <kendo:grid-column title="PF Reading" field="pfReading" editor="pfReadingEditor" format="{0:#.0000}" width="80px"/>
            <kendo:grid-column title="Recorded Demand" editor="recordedDemandEditor" field="recordedDemand" format="{0:#.00}" width="120px" />
            <kendo:grid-column title="TOD1" field="tod1" format="{0:#.00}" editor="tod1Editor" width="50px" />
            <kendo:grid-column title="TOD2" field="tod2" format="{0:#.00}" editor="tod2Editor" width="50px" />
            <kendo:grid-column title="TOD3" field="tod3" format="{0:#.00}" editor="tod3Editor" width="50px" />
            <kendo:grid-column title="PresentbilldateTo Export" field="presentbilldateToExport" width="0px" hidden="true" />
            <kendo:grid-column title="BlockIdToE xport" field="blockIdToExport" width="0px" hidden="true" />
            
            
        </kendo:grid-columns>
        <kendo:dataSource pageSize="50" batch="true" requestEnd="onRequestEndBatchUpdate">
            <kendo:dataSource-transport>
                <kendo:dataSource-transport-create url="${createUrl}" dataType="json" type="POST" contentType="application/json" />
                <kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json" />
                <kendo:dataSource-transport-update url="${updateUrl}" dataType="json" type="POST" contentType="application/json" />
                <kendo:dataSource-transport-destroy url="${destroyUrl}" dataType="json" type="POST" contentType="application/json" />
                <kendo:dataSource-transport-parameterMap>
                	<script>
	                	function parameterMap(options,type) { 
	                		if(type==="read"){
	                			return JSON.stringify(options);
	                		} else {
	                			return JSON.stringify(options.models);
	                		}
	                	}
                	</script>
                </kendo:dataSource-transport-parameterMap>
            </kendo:dataSource-transport>
            <kendo:dataSource-schema>
                <kendo:dataSource-schema-model id="batchUpdateId">
                    <kendo:dataSource-schema-model-fields>
                        <kendo:dataSource-schema-model-field name="accountNo" type="string" editable="false"/>
                        <kendo:dataSource-schema-model-field name="serviceType" type="string" editable="false"/>
                        <kendo:dataSource-schema-model-field name="presentBillDate" type="date" editable="false"/>
                        <kendo:dataSource-schema-model-field name="presentStatus" type="string"/>
                        <kendo:dataSource-schema-model-field name="presentReading" type="number">
                       	    <kendo:dataSource-schema-model-field-validation min="0" />
                        </kendo:dataSource-schema-model-field>
                        <kendo:dataSource-schema-model-field name="pfReading" type="number">
                        	<kendo:dataSource-schema-model-field-validation min="0" max="1"/>
                        </kendo:dataSource-schema-model-field>
                        <kendo:dataSource-schema-model-field name="recordedDemand" type="number">
                       	    <kendo:dataSource-schema-model-field-validation min="0" />
                        </kendo:dataSource-schema-model-field>
                        <kendo:dataSource-schema-model-field name="previousBillDate" type="date" editable="false"/>
                        <kendo:dataSource-schema-model-field name="previousStatus" type="string" editable="false"/>
                        <kendo:dataSource-schema-model-field name="previousReading" type="number" editable="false"/>
                       <%--  <kendo:dataSource-schema-model-field name="units" type="number"/>
                        <kendo:dataSource-schema-model-field name="averageUnits" type="number" /> --%>
                        <kendo:dataSource-schema-model-field name="meterConstant" type="number" editable="false"/>
                        <kendo:dataSource-schema-model-field name="tod1" type="number" />
                        <kendo:dataSource-schema-model-field name="tod2" type="number"/>
                        <kendo:dataSource-schema-model-field name="tod3" type="number"/>
                        <kendo:dataSource-schema-model-field name="dgMeterConstant" type="number" editable="false"/>  
                        <kendo:dataSource-schema-model-field name="dgPreviousReading" type="number" editable="false"/>
                        <kendo:dataSource-schema-model-field name="dgPresentReading" type="number" />
                        <kendo:dataSource-schema-model-field name="blockNameToExport" type="string"/>
                        <kendo:dataSource-schema-model-field name="presentbilldateToExport" type="date"/>
                        <kendo:dataSource-schema-model-field name="blockIdToExport" type="number"/>
                       <%--  <kendo:dataSource-schema-model-field name="dgUnits" type="number" /> --%>
                    </kendo:dataSource-schema-model-fields>
                </kendo:dataSource-schema-model>
            </kendo:dataSource-schema>
        </kendo:dataSource>
    </kendo:grid>
    
  <div id="alertsBox" title="Alert"></div>
 <div id="dialogTest" title="Please wait" style="display: none;">
<!-- <p>This is the default dialog which is useful for displaying information. The dialog window can be moved, resized and closed with the 'x' icon.</p> -->
               <span id=commitplaceholder><img
						src="./resources/images/loadingimg.GIF"
						style="vertical-align: middle;" /> &nbsp;&nbsp;
						<img src="./resources/images/loading.GIF" alt="loading"
						style="vertical-align: middle margin-left: 50px" height="20px"
						width="200px; " /></span>
</div>
   <div id="bulkBillingDiv" style="display: none;">
	<form id="bulkBillingForm" data-role="validator" novalidate="novalidate">
		<table style="height:160px;">
			<tr>
				<td>Block Name*</td>
				<td><input id="blockName" name="blockName" required="required" onchange="getPropertyNo()" validationMessage="Block Name is Required" />
				</td>
			</tr>
			<tr>
				<td>Service Type*</td>
				<td><input id="serviceTypeList" name="serviceTypeList" required="required" onchange="chanegeUomType();chanegeDate()"  validationMessage="Service Type Is required" />
				</td>
			</tr>
			 <%-- format="MMMM yyyy" --%>
			 <tr>
							<td>Present Bill Date*</td>
							<td><kendo:datePicker  format="dd/MM/yyyy"
									name="presentbilldateForm" id="presentbilldateForm" required="required"  validationMessage="Date is Required">
								</kendo:datePicker>
							<td></td>
						</tr>
			
			<tr>
				<td class="left" align="center" colspan="4">
					<button type="submit" id="bulkCalculate" class="k-button" style="padding-left: 10px" >Submit</button>
					
					<span id=commitplaceholderNew style="display: none;">
					<img
						src="./resources/images/loading2.gif"
						style="vertical-align: middle; margin-left: 50px" /> 
					</span>
				</td>
			</tr>

		</table>
	</form>
</div>


  <script type="text/javascript">
  function chanegeDate(){	
	 var blockid=$("#blockName").val(); 
	 var blockName = $("input[name=blockName]").data("kendoDropDownList").text();
	 var serviceTypeList=$("input[name=serviceTypeList]").data("kendoDropDownList").text();
	 var todayDate = new Date();			
		$('#presentbilldateForm').data("kendoDatePicker").max(todayDate);
	  $.ajax({
			type : "GET",
			url : "./batchUpdate/getBillDate",
			dataType : "json",
			data : {
				blockid : blockid,
				blockName  : blockName ,
				serviceTypeList:serviceTypeList,
			
			},
			success : function(response) {
				
				
				if(response.date == null){
					
				}else{
					
					var date=response.date; 
				    var     newvalid = date.split("-");
				    var month="";
				    var year="";
				    var date="";
					for(var i=0;i<=newvalid.length;i++){
						 month=(parseFloat(newvalid[1])-1);
						 date=(parseFloat(newvalid[2])+1);
						year=newvalid[0];
					}	
					//alert(" month::::"+ month+"date"+date+"year"+year);
					var toDate = new Date(year, month, date);
					
					$('#presentbilldateForm').data("kendoDatePicker").min(toDate);
					/* var datepicker = $("#presentbilldateForm").data("kendoDatePicker");			
					datepicker.min(new Date(year, month, date));	 */	
				}
				
			}

		});
	  
  }
  $("#bulkBillingForm").submit(function(e) {
		e.preventDefault();
	});
  
  
  $("#bulkBillingForm").kendoValidator({
      messages: {
          // defines a message for the 'custom' validation rule
          custom: "Please enter valid value for my custom rule",

          // overrides the built-in message for the required rule
          required: "My custom required message",
              minlen:1
          // overrides the built-in message for the email rule
          // with a custom function that returns the actual message
          
      },
      rules: {
     	 customRule1: function(input){
              // all of the input must have a value
              return $.trim(input.val()) !== "";
            },
      }
 });

 function getMessage(input) {
   return input.data("message");
 }
 var addvalidator = $("#bulkBillingForm").kendoValidator().data("kendoValidator");
 $("#bulkCalculate").on("click", function() {
	    if (addvalidator.validate()) {
	    	generateBulkBill();
	    }
	});
	$(document).ready(function() {
		/*------------------------------  Block Name --------------------------*/
				$("#blockName").kendoDropDownList({
					autoBind : false,
					optionLabel : "Select",
					dataTextField : "blockName",

					dataValueField : "blockId",
					dataSource : {
						transport : {
							read : "${towerNames}"
						}
					}
				});
	  /*------------------------------ Service Type --------------------------*/
				var serviceList = [
			    {
					text : "Electricity",
					value : "Electricity"
				},
				{
					text : "Water",
					value : "Water"
				}, 
				{
					text : "Gas",
					value : "Gas"
				},{
					text : "Solid Waste",
					value : "Solid Waste"
				},
				{
					text : "Others",
					value : "Others"
				},
				];

				$("#serviceTypeList").kendoDropDownList({
					dataTextField : "text",
					dataValueField : "value",
					optionLabel : {
						text : "Select",
						value : "",
					},
					dataSource : serviceList
				}).data("kendoDropDownList");
	});
  var SelectedRowId="";
  function batchUpdateEvent(e)
  {
  	$('div[data-container-for="batchUpdateId"]').remove();
  	$('label[for="batchUpdateId"]').closest('.k-edit-label').remove();
  	if (e.model.isNew()) 
      {
  		securityCheckForActions("./BillGeneration/GenerateBatchBill/billAll");
  		
  		if(tod1== 'NA'){
			$("#tod1").attr('readonly',true);
			$("#tod2").attr('readonly',true);
			$("#tod3").attr('readonly',true);
		}else
		{
			$("#tod1").attr('readonly',false);
			$("#tod2").attr('readonly',false);;
			$("#tod3").attr('readonly',false);
		}
  		
  		if(presentReading== 'NA'){
			$("#presentReading").attr('readonly',true);
		}else
		{
			$("#presentReading").attr('readonly',false);
		}
  		
  		
  		if(dgPresentReading== 'NA'){
			$("#dgPresentReading").attr('readonly',true);
		}else
		{
			$("#dgPresentReading").attr('readonly',false);
		}
  		
  		if(pfReading== 'NA'){
			$("#pfReading").attr('readonly',true);
		}else
		{
			$("#pfReading").attr('readonly',false);
		}

  		if(recordedDemand== 'NA'){
			$("#recordedDemand").attr('readonly',true);
		}else
		{
			$("#recordedDemand").attr('readonly',false);
		}
  		 $("#presentReading").change(
   				function() {
   					
   					
   				 	var presentreading=$("#presentReading").val();
   	  				if(presentreading<=previousReading)
   	  		    	{
   	  		    	alert("Present reading should be greater than previous reading .");
   	  				$('#presentReading').val("");
   	  		    	}
   				 
   				});
  		$("#dgPresentReading").change(
  				function() {
  				
  					var dgPresentReading=$("#dgPresentReading").val();
  	  				if(dgPresentReading<=dgPreviousReading)
  	  		    	{
  	  		    	alert("Present reading should be greater than previous reading .");
  	  				$('#presentReading').val("");
  	  		    	}
  				});
  		
  		$(".k-window-title").text("Create Batch Update");
  		$(".k-grid-update").text("Save");		
      }
  	else
  	{
  		$(".k-window-title").text("Edit Batch Update");
  	}
  }
  var dgPresentReading="";
  var tod1="";
  var tod2="";
  var tod3="";
  var pfReading="";
  var recordedDemand="";
  var presentReading="";
  var presentStatus="";
  var previousReading="";
  var presentbilldateToExport="";
  var blockIdToExport="";
  var serviceType="";
  function onChangeBatchUpdate(arg) {
		var gview = $("#batchUpdateGrid").data("kendoGrid");
		var selectedItem = gview.dataItem(gview.select());
		SelectedRowId = selectedItem.batchUpdateId;
		dgPresentReading = selectedItem.dgPresentReading;
		tod1 = selectedItem.tod1;
		tod2 = selectedItem.tod2;
		tod3 = selectedItem.tod3;
		pfReading = selectedItem.pfReading;
		recordedDemand = selectedItem.recordedDemand;
		presentReading = selectedItem.presentReading;
		presentStatus = selectedItem.presentStatus;
		previousReading = selectedItem.previousReading;
		
		this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
		$(".k-master-row.k-state-selected").show();
	}
  
	$("#batchUpdateGrid").on("click", ".k-grid-createBatch", function(e) {
		var result = securityCheckForActionsForStatus("./BillGeneration/GenerateBatchBill/createBatch");
		if (result == "success") {
			var bbDialog = $("#bulkBillingDiv");
			bbDialog.kendoWindow({
				width : "auto",
				height : "auto",
				modal : true,
				draggable : true,
				position : {
					top : 100
				},
				title : "Create Batch"
			}).data("kendoWindow").center().open();

			bbDialog.kendoWindow("open");
			var dropdownlist1 = $("#serviceTypeList").data("kendoDropDownList");
			dropdownlist1.value(""); 
			var dropdownlist2 = $("#blockName").data("kendoDropDownList");
			dropdownlist2.value("");
		}
	});
	
	
	function getPropertyNo() {
		var blockId = $('#blockName').val();
		$("#propertyName").kendoMultiSelect({
			autoBind : false,
			dataValueField : "propertyId",
			dataTextField : "property_No",
			dataSource : {
				transport : {
					read : {
						url : "./bill/getPropertyNo?blockId=" + blockId,
					}
				}
			}
		});

	}
	
	function chanegeUomType() {
		var serviceName = $("input[name=serviceTypeList]").data("kendoDropDownList").text();
		if (serviceName != 'Solid Waste') {
			$('.changeUom').html('Present Reading');
		} else {
			$('.changeUom').html('Amount(per month)');
		}
	}
	
	function bulkBillClose() {

		var todcal = $("#bulkBillingDiv");
		var dropdownlist1 = $("#serviceTypeList").data("kendoDropDownList");
		dropdownlist1.value("");
		var dropdownlist2 = $("#blockName").data("kendoDropDownList");
		dropdownlist2.value("");

		todcal.kendoWindow({
			width : "auto",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Generate Bill"
		}).data("kendoWindow").center().close();
		todcal.kendoWindow("close");
	}
	
	function generateBulkBill() {
		var blockName = $("input[name=blockName]").data("kendoDropDownList").text();
		var blocId = $("#blockName").val();
		var presentbilldate = $("#presentbilldateForm").val();
		var serviceTypeList = $("input[name=serviceTypeList]").data("kendoDropDownList").text();
		
		$('#commitplaceholderNew').show();
        $('#bulkCalculate').hide();
			$.ajax({
				url : "./batchUpdate/createBatch",
				type : "GET",
				data : {
					blocId : blocId,
					blockName : blockName,
					serviceTypeList : serviceTypeList,
					presentbilldate : presentbilldate,
				},
				success : function(response) {
					alert("Your batch has been created");
					
					$('#commitplaceholderNew').hide();
	            	$('#bulkCalculate').show();
					bulkBillClose();
					var grid = $("#batchUpdateGrid").getKendoGrid();
					var data = new kendo.data.DataSource();
					grid.dataSource.data(response);
					grid.refresh();
					/* $('#batchUpdateGrid').data('kendoGrid').refresh();
					$('#batchUpdateGrid').data('kendoGrid').dataSource.read(); */
				}
			});
	}
	
	/* function presentStatusEditor(container, options) {
		$(
				'<select data-text-field="presentStatus" name="presentStatus" data-value-field="presentStatus" required ="true" validationmessage="Service type is required" id="presentStatus" data-bind="value:' + options.field + '"/>')
				.appendTo(container).kendoDropDownList({
					optionLabel : "Select",
					autoBind : true,
					dataSource : {
						transport : {
							read : "${presentStatus}"
						}
					}
				});
		$('<span class="k-invalid-msg" data-for="presentStatus"></span>').appendTo(container);
	} */
	
	function onRequestEndBatchUpdate(r) {
		
		if (typeof r.response != 'undefined') {
			if (r.response.status == "FAIL") {

				errorInfo = "";

				for (var s = 0; s < r.response.result.length; s++) {
					errorInfo += (s + 1) + ". "+ r.response.result[s].defaultMessage + "<br>";

				}

				if (r.type == "create") {

					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Bill generated for all accounts<br>" + errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
					$('#batchUpdateGrid').data('kendoGrid').refresh();
					$('#batchUpdateGrid').data('kendoGrid').dataSource.read();
					return false;
				}

				if (r.type == "update") {
					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Bill generated for all accounts<br>" + errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
				}

				$('#batchUpdateGrid').data('kendoGrid').refresh();
				$('#batchUpdateGrid').data('kendoGrid').dataSource.read();
				return false;
			}

			if (r.type == "update") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Bill generated for all accounts");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$('#dialog').dialog('close');
							$(this).dialog("close");
						}
					}
				});
				$('#batchUpdateGrid').data('kendoGrid').refresh();
				$('#batchUpdateGrid').data('kendoGrid').dataSource.read();
			}
			
			else if (r.type == "create") {
				$('#dialogTest').dialog('close');
				$("#alertsBox").html("");
				$("#alertsBox").html("Bill generated for all accounts");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
							
						}
					}
				});
				$('#batchUpdateGrid').data('kendoGrid').refresh();
				$('#batchUpdateGrid').data('kendoGrid').dataSource.read();
			}
		}
	}

	$("#batchUpdateGrid").on("click",".k-grid-save-changes",function(e) {
		if(serviceType.length ==0)
		{
		alert("No records found");
		}else{
			$("#dialogTest").dialog({
				modal : true,
				buttons : {
					"Close" : function() {
						$(this).dialog("close");
					}
				}
			});
		}
	});
	
			(function ($, kendo) 
				{   	  
			    $.extend(true, kendo.ui.validator, 
			    {
			        
			rules : { 
				presentBillDate: function (input, params)
		        {
		               if ((input.attr("name") == "presentBillDate") && (input.val() == 0)) 
		               {
		               return false;
		               }
		               return true;
		           },
				},
		messages : {
			        presentBillDate : "Present Bill Date is required",
					}
				});
		})(jQuery, kendo);
	
	function tod1Editor(container, options){
		  $('<input id="tod1" data-bind="value:' + options.field + '"/>')
	        .appendTo(container)
	        .kendoNumericTextBox({
	         spinners : false,
	         min : 0,
	        });
		 }
	
	function tod2Editor(container, options){
		  $('<input id="tod2" data-bind="value:' + options.field + '"/>')
	        .appendTo(container)
	        .kendoNumericTextBox({
	         spinners : false,
	         min : 0,
	        });
		 }
	
	function tod3Editor(container, options){
		  $('<input id="tod3" data-bind="value:' + options.field + '"/>')
	        .appendTo(container)
	        .kendoNumericTextBox({
	         spinners : false,
	         min : 0,
	         change : onChange(),
	        });
		 }
	
	function onChange(){
		$("#tod1").val();
		$("#tod2").val();
	    $("#tod3").val();
	    var totalTodValue = tod1+tod2+tod3;
	    var units = presentReading - previousReading;
	    if(totalTodValue<units)
	    	{
	    	alert("Tod Slabs can not be less than Unit");
			$('#tod3').val("");
	    	}
	    
	    if(totalTodValue>units)
	    	{
	    	alert("Tod Slabs can not be More than Unit");
			$('#tod3').val("");
	    	}
	}
	function presentReadingEditor(container, options){
		  $('<input id="presentReading" data-bind="value:' + options.field + '"/>')
	        .appendTo(container)
	        .kendoNumericTextBox({
	         spinners : false,
	         min : 0,
	       /*   onchange : onChangePresentReading(), */
	        });
		 }
	
/* 	function onChangePresentReading(){
		$("#presentReading").val();
	    if(presentReading<=previousReading)
	    	{
	    	alert("Present reading should be greater than previous reading.");
			$('#presentReading').val("");
	    	}
	} */
	
	function dgPresentReadingEditor(container, options){
		  $('<input id="dgPresentReading" data-bind="value:' + options.field + '"/>')
	        .appendTo(container)
	        .kendoNumericTextBox({
	         spinners : false,
	         min : 0,
	        });
		 }
	
	function pfReadingEditor(container, options){
		  $('<input id="pfReading" data-bind="value:' + options.field + '"/>')
	        .appendTo(container)
	        .kendoNumericTextBox({
	         spinners : false,
	         min : 0,
	         max:1,
	        });
		 }
	
	function recordedDemandEditor(container, options){
		  $('<input id="recordedDemand" data-bind="value:' + options.field + '"/>')
	        .appendTo(container)
	        .kendoNumericTextBox({
	         spinners : false,
	         min : 0,
	        });
		 }
	
	function batchUpdateDataBound(e) {
		var data = this.dataSource.view();
		var grid = $("#batchUpdateGrid").data("kendoGrid");
		for (var i = 0; i < data.length; i++) {
			var currentUid = data[i].uid;
			row = this.tbody.children("tr[data-uid='" + data[i].uid + "']");
			 serviceType = data[i].serviceType;
			 presentbilldateToExport = data[i].presentBillDate;
			 blockIdToExport = data[i].blockIdToExport;
		}
	}
		
	$("#batchUpdateGrid").on("click",".k-grid-exportToExcel",function(e) {
		var result = securityCheckForActionsForStatus("./BillGeneration/GenerateBatchBill/exportToExcel");
	
		if (result == "success") {
			if(serviceType.length ==0)
				{
				alert("No records found");
				}else{
					var result = replaceAll('/','-',presentbilldateToExport);
					window.open("./batchUpdate/exportToExcel/"+ serviceType + "/"+result+"/"+blockIdToExport);
				}
		}
		});
	
	function replaceAll(find, replace, str) {
		  return str.replace(new RegExp(find, 'g'), replace);
		}

</script>  

<!-- <style>

.k-grid-content{
	height : 75px !important
}
span.k-tooltip {
    border-width: 0;
    display: list-item;
    margin-top: -7px;
    padding: 0 0 3px 1px;
    position: absolute;
}
</style> -->
