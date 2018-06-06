<%@include file="/common/taglibs.jsp"%>

<c:url value="/bankRemittance/readbankRemittanceUrl" var="readbankRemittanceUrl" />
<c:url value="/bankRemittance/createbankRemittanceUrl" var="createbankRemittanceUrl" />
<c:url value="/bankRemittance/updatebankRemittanceUrl" var="updatebankRemittanceUrl" />
<c:url value="/bankRemittance/destroybankRemittanceUrl" var="destroybankRemittanceUrl" />

<!-- URL'S FOR FILTER -->
<c:url value="/bankRemittance/commonFilterForBankRemittance" var="commonFilterForBankRemittance" />
<c:url value="/bankRemittance/commonFilterForBankRemittanceAcccountNo" var="commonFilterForBankRemittanceAcccountNo" />
<c:url value="/bankRemittance/commonFilterForBankRemittanceCredit" var="commonFilterBankRemittanceForCredit" />

<c:url value="/bankRemittance/upload" var="Upload" />


<kendo:grid remove="bankRemittanceDeleteEvent" name="bankRemittanceGrid" pageable="true" resizable="true" filterable="true" sortable="true" reorderable="true" selectable="true" scrollable="true" groupable="true" edit="bankStatementEvent">	
		<kendo:grid-editable mode="popup"/>
				
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Bank Remittance"/>
			<kendo:grid-toolbarItem text="Clear Filter" name="Clear_Filter"/>
			<kendo:grid-toolbarItem template="<input type='file' name='files' id='files' />" />
		</kendo:grid-toolbar>
		
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" ></kendo:grid-pageable>
			<kendo:grid-filterable extra="false">
		 		<kendo:grid-filterable-operators>
		  			<kendo:grid-filterable-operators-string eq="Is equal to"/>
		  		    <kendo:grid-filterable-operators-date gt="Is after" lt="Is before"/>
				</kendo:grid-filterable-operators>			
		</kendo:grid-filterable>
		
		<kendo:grid-columns>
		    <kendo:grid-column title="Bank RemittanceId&nbsp;*" field="brId" width="80px" hidden="true"/>
		    		    
		    <kendo:grid-column title="Bank Name&nbsp;*" field="bankName" width="80px">
		    <kendo:grid-column-filterable >
				<kendo:grid-column-filterable-ui >
				   <script>
					 function ledgerStatusFilter(element){
						element.kendoAutoComplete({
							placeholder : "Enter Bank Name",
							dataSource : {
						   	   transport :{
								 read : "${commonFilterForBankRemittance}/bankName"
							   }
							}
						 });
					   }
				   </script>
				 </kendo:grid-column-filterable-ui>
			   </kendo:grid-column-filterable>
			</kendo:grid-column>
		    		    
		    <kendo:grid-column title="Account Number&nbsp;*" field="accountNo" width="80px">
		    <kendo:grid-column-filterable >
				<kendo:grid-column-filterable-ui >
				   <script>
					 function ledgerStatusFilter(element){
						element.kendoAutoComplete({
							placeholder : "Enter Account Number",
							dataSource : {
						   	   transport :{
								 read : "${commonFilterForBankRemittanceAcccountNo}"
							   }
							}
						 });
					   }
				   </script>
				 </kendo:grid-column-filterable-ui>
			   </kendo:grid-column-filterable>
			</kendo:grid-column>
		    
		    <kendo:grid-column title="Transaction Date&nbsp;*" field="txDate" width="80px" format="{0: dd/MM/yyyy}"/>
		     
		    <kendo:grid-column title="Description&nbsp;*" field="description" editor="descriptionEditor" width="100px">
		    <kendo:grid-column-filterable >
				<kendo:grid-column-filterable-ui >
				   <script>
					 function ledgerStatusFilter(element){
						element.kendoAutoComplete({
							placeholder : "Enter Description",
							dataSource : {
						   	   transport :{
								 read : "${commonFilterForBankRemittance}/description"
							   }
							}
						 });
					   }
				   </script>
				 </kendo:grid-column-filterable-ui>
			   </kendo:grid-column-filterable>
			</kendo:grid-column>
		    
		    <kendo:grid-column title="Credit&nbsp;*" field="credit" width="80px">
			</kendo:grid-column>
		    	    
		    <kendo:grid-column title="Status&nbsp;*" field="status" width="80px">
		    <kendo:grid-column-filterable >
				<kendo:grid-column-filterable-ui >
				   <script>
					 function ledgerStatusFilter(element){
						element.kendoAutoComplete({
							placeholder : "Enter Status",
							dataSource : {
						   	   transport :{
								 read : "${commonFilterForBankRemittance}/status"
							   }
							}
						 });
					   }
				   </script>
				 </kendo:grid-column-filterable-ui>
			   </kendo:grid-column-filterable>
			</kendo:grid-column>
				
		    <kendo:grid-column title="Created By&nbsp;*" field="createdBy" width="0px" hidden="true"/>
		    <kendo:grid-column title="Last UpdatedBy&nbsp;*" field="lastUpdatedBy" width="0px" hidden="true"/>
		    	    
		    <kendo:grid-column title="&nbsp;" width="135px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit" text="Edit"/>
					<kendo:grid-column-commandItem name="destroy"/> 
				</kendo:grid-column-command>
			</kendo:grid-column>
		</kendo:grid-columns>
		
		<kendo:dataSource requestEnd="onBankRemittanceRequestEnd" requestStart="onBankRemittanceRequestStart">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-read url="${readbankRemittanceUrl}" dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-create url="${createbankRemittanceUrl}" dataType="json" type="GET" contentType="application/json" />	
				<kendo:dataSource-transport-update url="${updatebankRemittanceUrl}" dataType="json" type="GET" contentType="application/json" />	
				<kendo:dataSource-transport-destroy url="${destroybankRemittanceUrl}/" dataType="json" type="GET" contentType="application/json" />				
			</kendo:dataSource-transport>
			
			<kendo:dataSource-schema>
				<kendo:dataSource-schema-model id="brId">
					<kendo:dataSource-schema-model-fields>
						<%-- <kendo:dataSource-schema-model-field name="bsId" type="number"/> --%>
						<kendo:dataSource-schema-model-field name="bankName" type="string"/>
						
						<kendo:dataSource-schema-model-field name="accountNo" type="number">
							<kendo:dataSource-schema-model-field-validation min="1" />
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="txDate" type="date"/>						
						<kendo:dataSource-schema-model-field name="description" type="string"/>
						
						<kendo:dataSource-schema-model-field name="credit" type="number">
							<kendo:dataSource-schema-model-field-validation min="1" />
						</kendo:dataSource-schema-model-field>													
						
						<kendo:dataSource-schema-model-field name="status" type="string" defaultValue="Created"/>						 
						<kendo:dataSource-schema-model-field name="createdBy" type="string"/>
						<kendo:dataSource-schema-model-field name="lastUpdatedBy" type="string"/>					
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
	</kendo:grid>
	
	<div id="alertsBox" title="Alert"></div>
	
	
	<!-- *****************START FOR EXCEL FILE IMPORT FUNCTION******************* -->
	<script>
	
	function bankRemittanceDeleteEvent(){
		securityCheckForActions("./Collections/BankRemittance/destroyButton");
		var conf = confirm("Are u sure want to delete this item?");
		 if(!conf){
		   $("#bankRemittanceGrid").data().kendoGrid.dataSource.read();
		   throw new Error('deletion aborted');
		 }
	}
	
	  $(document).ready(function () 
      {
		 var result=securityCheckForActionsForStatus("./Collections/BankRemittance/uploadButton"); 
		 if(result=="success"){ 
         $("#files").kendoUpload
         ({
            multiple: true,
            success : onDocSuccess,
            error : errorS,
            async: 
            {
                saveUrl:"./bankRemittance/upload",
                autoUpload: true
            }
        });
		 }
     });	  
	 function onDocSuccess(){
      	alert("File Imported Successfully");
      	window.location.reload();
     }
     function errorS(){
      	alert("File Importing Failed");
      	window.location.reload();
     }
   </script>
   <!-- *****************END FOR EXCEL FILE IMPORT FUNCTION******************* -->
	
	
	<script>
	
	//Clear Filter_Button_Action Function
	$("#bankRemittanceGrid").on("click", ".k-grid-Clear_Filter", function()
	{
	   	//custom actions
		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
		var grid = $("#bankRemittanceGrid").data("kendoGrid");
		grid.dataSource.read();
		grid.refresh();
	});
	
	//Clear Filter Before Adding
	$("#bankRemittanceGrid").on("click", ".k-grid-add", function() 
	{
		if($("#bankRemittanceGrid").data("kendoGrid").dataSource.filter())
		{
    		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
    	    var grid = $("#bankRemittanceGrid").data("kendoGrid");
    		grid.dataSource.read();
    		grid.refresh();
        }
	});
	
	//TextArea Editor
	function descriptionEditor(container, options) 
	{
	    $('<textarea name="Description" data-text-field="description" data-value-field="description" data-bind="value:' + options.field + '" style="width: 148px; height: 46px;" required="true"/>').appendTo(container);
	    $('<span class="k-invalid-msg" data-for="Description"></span>').appendTo(container); 
	}
	
	//Add & Edit Event
	function bankStatementEvent(e)
	{
		$('label[for=brId]').parent().hide();
		$('div[data-container-for="brId"]').hide();
		
		$('label[for=createdBy]').parent().hide();
		$('div[data-container-for="createdBy"]').hide();

		$('label[for=lastUpdatedBy]').parent().hide();
		$('div[data-container-for="lastUpdatedBy"]').hide();
		
		$('label[for=status]').parent().hide();
		$('div[data-container-for="status"]').hide();
		
		if(e.model.isNew()){
			securityCheckForActions("./Collections/BankRemittance/createButton");
			$(".k-window-title").text("Add New Bank Remittance");
			$(".k-grid-update").text("Save");
		}
		else{
			securityCheckForActions("./Collections/BankRemittance/editButton");
			$(".k-window-title").text("Edit Bank Remittance");
			$(".k-grid-update").text("Update");
		}
	}
	
	//Request End Function
	function onBankRemittanceRequestEnd(e)
	{
		if (typeof e.response != 'undefined')
		{
			if (e.response.status == "FAIL") 
			{
				errorInfo = "";
				errorInfo = e.response.result.invalid;
				for (i = 0; i < e.response.result.length; i++) 
				{
					errorInfo += (i + 1) + ". "+ e.response.result[i].defaultMessage + "\n";
				}
				if (e.type == "create") 
				{
					alert("Error: Creating the Bank Remittance Details\n\n"
							+ errorInfo);
				}
				if (e.type == "update") 
				{
					alert("Error: Updating the Bank Remittance Details\n\n" +errorInfo);
				}
				var grid = $("#bankRemittanceGrid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
				return false;
			}
		}
		if (e.type == "update" && !e.response.Errors)
		{
			$("#alertsBox").html("Alert");
			$("#alertsBox").html("Bank Remittance Updated successfully");
			$("#alertsBox").dialog
			({
			   modal : true,
			   buttons : {
				"Close" : function(){
					$(this).dialog("close");
				 }
			   }
			});
			e.sender.read();
		}
		if (e.type == "create" && !e.response.Errors)
		{
			$("#alertsBox").html("Alert");
			$("#alertsBox").html("Bank Remittance Added successfully");
			$("#alertsBox").dialog
			({
			   modal : true,
			   buttons : {
				"Close" : function(){
					$(this).dialog("close");
				 }
			   }
			});
			e.sender.read();
		}
		if (e.type == "destroy" && !e.response.Errors)
		{
			$("#alertsBox").html("Alert");
			$("#alertsBox").html("Bank Remittance Deleted successfully");
			$("#alertsBox").dialog
			({
			   modal : true,
			   buttons : {
				"Close" : function(){
					$(this).dialog("close");
				 }
			   }
			});
			e.sender.read();
		}
	}
	
	//Request Start Function
	function onBankRemittanceRequestStart(e)
	{
	    
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();	
        
		/* var gridStoreGoodsReturn = $("#bankRemittanceGrid").data("kendoGrid");
		gridStoreGoodsReturn.cancelRow();	 */	
			
	}
	
	
	//Field Validator's
	 var res = [];              
   	(function($, kendo) {
   		$
   				.extend(
   						true,
   						kendo.ui.validator,
   						{
   							rules :
   							{ // custom rules
   								bankNameNullValidator: function (input, params)
							        {
					                     if (input.attr("name") == "bankName") {
					                      return $.trim(input.val()) !== "";
					                     }
					                     return true;
					                },
					                accNoNullValidator: function (input, params)
							        {
					                     if (input.attr("name") == "accountNo") {
					                      return $.trim(input.val()) !== "";
					                     }
					                     return true;
					                },
					                txDateNullValidator: function (input, params)
							        {
					                     if (input.attr("name") == "txDate") {
					                      return $.trim(input.val()) !== "";
					                     }
					                     return true;
					                },
					                creditNullValidator: function (input, params)
							        {
					                     if (input.attr("name") == "credit") {
					                      return $.trim(input.val()) !== "";
					                     }
					                     return true;
					                },		                
	   								bankNameRegExpValidator : function(input,params) 
	   								{
	   									//check for the name attribute 
	   									if (input.filter("[name='bankName']").length&& input.val()) 
	   									{
	   										return /^[a-zA-Z]+[._a-zA-Z0-9]*[a-zA-Z0-9]$/.test(input.val());
	   									}
	   									 return true;
	   								},	
   							   },
   							messages : { 
   								bankNameNullValidator : "Bank Name Required",
   								accNoNullValidator : "Account Number Required",
   								txDateNullValidator : "Date Required",
   								creditNullValidator : "Credit Value Required",
   								bankNameRegExpValidator: "Bank Name can not allow special symbols & numbers",   									
   							}
   						});
   	})(jQuery, kendo);
	</script>
	
	<style>
 .k-upload-empty {
	width: 170px;
	height: 35px;
	margin-left: 268px;
	margin-top: -36px;
}
.k-upload-button {
	width: 130px;
	}
</style>
	