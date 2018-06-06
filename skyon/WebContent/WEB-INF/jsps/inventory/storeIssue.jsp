<%@include file="/common/taglibs.jsp"%>

<script src="resources/js/plugins//jQuery.print.js"></script>

<!-- Urls for Store Issue  -->
	<c:url value="/storeIssue/read" var="readStoreIssueUrl" />
	<c:url value="/storeIssue/modify" var="modifyStoreIssueUrl" />
    <c:url value="/storeIssue/getJobNosSIFilterUrl" var="jobNosSIFilterUrl"/>
	<c:url value="/storeIssue/getJobCardsSIComboBoxUrl" var="jobCardsSIComboBoxUrl"/>
	<c:url value="/storeIssue/getStoreNamesSIFilterUrl" var="storeNamesSIFilterUrl" />
	<c:url value="/storeIssue/getStoreMasterSIComboBoxUrl" var="storeMasterSIComboBoxUrl" />
	<c:url value="/storeIssue/getContractNamesSIFilterUrl" var="contractNamesSIFilterUrl" />
	<c:url value="/storeIssue/getVendorContractSIComboBoxUrl" var="vendorContractSIComboBoxUrl" />
	<c:url value="/storeIssue/getImNamesSIFilterUrl" var="imNamesSIFilterUrl"/>
	<c:url value="/storeIssue/getItemsSIComboBoxUrl" var="itemsSIComboBoxUrl"/>
	<c:url value="/storeIssue/getUomSIFilterUrl" var="uomSIFilterUrl"/>
	<c:url value="/storeIssue/getUnitOfMeasurementSIComboBoxUrl" var="unitOfMeasurementSIComboBoxUrl" />
	
	<!-- ------------------------------------ Grid -------------------------------------- -->
	
	<kendo:grid name="gridStoreIssue"   edit="storeIssueEdit" pageable="true" resizable="true" change="onChangeStoreIssue"
	sortable="true" reorderable="true" selectable="true" scrollable="true" groupable="true" filterable="true" navigatable="true">
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" refresh="true" info="true" previousNext="true">
			<kendo:grid-pageable-messages itemsPerPage="Store Issues per page" empty="No Store Issue to display" refresh="Refresh all the Store Issues" 
			display="{0} - {1} of {2} Store Issues" first="Go to the first page of Store Issues" last="Go to the last page of Store Issues" next="Go to the next page of Store Issues"
			previous="Go to the previous page of Store Issues"/>
		</kendo:grid-pageable> 
		<kendo:grid-filterable extra="false">
			<kendo:grid-filterable-operators>
				<kendo:grid-filterable-operators-string eq="Is equal to"/>
				<kendo:grid-filterable-operators-date gt="Is after" lt="Is before"/>
			</kendo:grid-filterable-operators>
		</kendo:grid-filterable>
		<kendo:grid-editable mode="popup"/>
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Store Issue" />
			<kendo:grid-toolbarItem name="storeIssueTemplatesDetailsExport" text="Export To Excel" />
	        <kendo:grid-toolbarItem name="storeIssuePdfTemplatesDetailsExport" text="Export To PDF" /> 
			<kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterStoreIssue()><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>"/>
		</kendo:grid-toolbar>
		<kendo:grid-columns>
			 <kendo:grid-column title="Job&nbsp;No&nbsp;*" field="jobNo" width="80px">
					<kendo:grid-column-filterable>
						<kendo:grid-column-filterable-ui>
							<script type="text/javascript">
								function jobNoFilter(element) 
							   	{
									element.kendoComboBox({
										autoBind : true,
										dataTextField : "jobNo",
										dataValueField : "jobNo", 
										placeholder : "Enter job No",
										headerTemplate : '<div class="dropdown-header">'
											+ '<span class="k-widget k-header">Photo</span>'
											+ '<span class="k-widget k-header">Contact info</span>'
											+ '</div>',
										template : '<table><tr>'
											+ '<td align="left"><span class="k-state-default"><b>#: data.jobNo #</b></span><br>'
											+ '<span class="k-state-default"><b>Name:&nbsp;</b>&nbsp;&nbsp;<i>#: data.jobName #</i></span><br>'
											+ '<span class="k-state-default"><b>Group:&nbsp;</b>&nbsp;<i>#: data.jobGroup #</i></span></td></tr></table>',
										dataSource : {
											transport : {		
												read :  "${jobNosSIFilterUrl}"
											}
										} 
									});
							   	}
							</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>	
			<kendo:grid-column title="Job&nbsp;No&nbsp;*" field="jcId" editor="jobCardsSIEditor" width="0px" hidden="true"/>
			
			<kendo:grid-column title="Store&nbsp;Name&nbsp;*" field="storeName" width="100px">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function storeNameFilter(element) 
						   	{
								element.kendoComboBox({
									autoBind : true,
									dataTextField : "storeName",
									dataValueField : "storeName", 
									placeholder : "Enter to store name",
									headerTemplate : '<div class="dropdown-header">'
										+ '<span class="k-widget k-header">Photo</span>'
										+ '<span class="k-widget k-header">Contact info</span>'
										+ '</div>',
									template : '<table><tr>'
										+ '<td align="left"><span class="k-state-default"><b>#: data.storeName #</b></span><br>'
										+ '<span class="k-state-default"><i>#: data.storeLocation #</i></span></td></tr></table>',
									dataSource : {
										transport : {		
											read :  "${storeNamesSIFilterUrl}"
										}
									} 
								});
						   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			<kendo:grid-column title="Store&nbsp;Name&nbsp;*" field="storeId" editor="storeMasterSIEditor" width="0px" hidden="true">
            </kendo:grid-column>	
			
	        <kendo:grid-column title="Item&nbsp;Name&nbsp;*" field="imName" width="100px">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
								function imNameFilter(element) 
							   	{
									element.kendoComboBox({
										autoBind : true,
										dataTextField : "imName",
										dataValueField : "imName", 
										placeholder : "Enter item name",
										headerTemplate : '<div class="dropdown-header">'
											+ '<span class="k-widget k-header">Photo</span>'
											+ '<span class="k-widget k-header">Contact info</span>'
											+ '</div>',
										template : '<table><tr>'
											+ '<td align="left"><span class="k-state-default"><b>#: data.imName #</b></span><br>'
											+ '<span class="k-state-default"><b>Type:&nbsp;</b>&nbsp;&nbsp;<i>#: data.imType #</i></span><br>'
											+ '<span class="k-state-default"><b>Group:&nbsp;</b>&nbsp;<i>#: data.imGroup #</i></span></td></tr></table>',
										dataSource : {
											transport : {		
												read :  "${imNamesSIFilterUrl}"
											}
										} 
									});
							   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>	
			<kendo:grid-column title="Item&nbsp;Name&nbsp;*" field="imId" editor="itemsSIEditor" width="0px" hidden="true"/>
			<kendo:grid-column title="Unit&nbsp;of&nbsp;Issue&nbsp;*" field="uom" width="100px">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
								function uomFilter(element) 
							   	{
									element.kendoComboBox({
										autoBind : true,
										dataTextField : "uom",
										dataValueField : "uom", 
										placeholder : "Enter uom",
										headerTemplate : '<div class="dropdown-header">'
											+ '<span class="k-widget k-header">Photo</span>'
											+ '<span class="k-widget k-header">Contact info</span>'
											+ '</div>',
										template : '<table><tr>'
											+ '<td align="left"><span class="k-state-default"><b>#: data.uom #</b></span><br>'
											+ '<span class="k-state-default">Code: <i>#: data.code #</i></span><br>'
											+ '<span class="k-state-default">Base UOM: <i>#: data.baseUom #</i></span><br>'
											+ '<span class="k-state-default"><b>Item: </b><i>#: data.imName #</i></span></td></tr></table>',
										dataSource : {
											transport : {		
												read :  "${uomSIFilterUrl}"
											}
										} 
									});
							   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>	
	        <kendo:grid-column title="Unit&nbsp;of&nbsp;Measurement&nbsp;*" field="uomId" editor="unitOfMeasurementSIEditor" width="0px" hidden="true"/>
	        
	        <kendo:grid-column title="Vendor&nbsp;Contract&nbsp;Name&nbsp;*" field="contractName" width="170px">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function storeNameFilter(element) 
						   	{
								element.kendoComboBox({
									autoBind : true,
									dataTextField : "contractName",
									dataValueField : "contractName", 
									placeholder : "Enter contract name",
									headerTemplate : '<div class="dropdown-header">'
										+ '<span class="k-widget k-header">Photo</span>'
										+ '<span class="k-widget k-header">Contact info</span>'
										+ '</div>',
									template : '<table><tr>'
										+ '<td align="left"><span class="k-state-default"><b>#: data.contractName #</b></span><br>'
										+ '<span class="k-state-default"><i>#: data.contractNo #</i></span></td></tr></table>',
									dataSource : {
										transport : {		
											read :  "${contractNamesSIFilterUrl}"
										}
									} 
								});
						   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>	
			<kendo:grid-column title="Vendor&nbsp;Contract&nbsp;Name&nbsp;*" field="vcId" editor="vendorContractSIEditor" width="0px" hidden="true">
            </kendo:grid-column>
            
	        <kendo:grid-column title="Issue&nbsp;Quantity&nbsp;*" field="imQuantity" editor="itemQuantitySIEditor" width="120px"/>
	        <kendo:grid-column title="Issue&nbsp;Date&nbsp;*" field="striDate" format="{0:dd/MM/yyyy}" width="0px" hidden="true"/>
            <kendo:grid-column title="Issue&nbsp;Time&nbsp;*" field="striTime" format="{0:HH:mm}" editor="timeEditorStoreIssue" width="0px" hidden="true"/>
			<kendo:grid-column title="Issued&nbsp;Date&nbsp;Time" field="striDt" format="{0:dd/MM/yyyy HH:mm}" editor="dateTimeEditorStoreIssue" width="130px" filterable="true">
				<kendo:grid-column-filterable ui="datetimepicker"></kendo:grid-column-filterable>
			</kendo:grid-column>
			<kendo:grid-column title="Reason&nbsp;for&nbsp;Issue" field="reasonForIssue" editor="textAreaEditor" width="120px" filterable="false"/>
	        <kendo:grid-column title="Ledger&nbsp;Update&nbsp;Date&nbsp;Time" format="{0:dd/MM/yyyy HH:mm}" field="ledgerUpdateDt" width="180px" filterable="true">
				<kendo:grid-column-filterable ui="datetimepicker"></kendo:grid-column-filterable>
			</kendo:grid-column>
			<kendo:grid-column title="Barcode&nbsp;Generator"   template="<a href='\\\#' class='k-button' id='printSGRBtn'  >Print Gate Pass</a>" width="140px" />
        </kendo:grid-columns>
		<kendo:dataSource requestStart="onRequestStartStoreIssue" requestEnd="onRequestEndStoreIssue">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-read url="${readStoreIssueUrl}" dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-create url="${modifyStoreIssueUrl}/create" dataType="json" type="GET" contentType="application/json" />
			</kendo:dataSource-transport>
			<kendo:dataSource-schema parse="storeIssueParse">
				<kendo:dataSource-schema-model id="striId">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="striId" type="number"/> 
						<kendo:dataSource-schema-model-field name="jobNo"/>
						<kendo:dataSource-schema-model-field name="jcId"/>
						<kendo:dataSource-schema-model-field name="storeId"/>
						<kendo:dataSource-schema-model-field name="storeName"/>
						<kendo:dataSource-schema-model-field name="vcId"/>
						<kendo:dataSource-schema-model-field name="contractName"/>
						<kendo:dataSource-schema-model-field name="imName"/>
						<kendo:dataSource-schema-model-field name="imId"/>
						<kendo:dataSource-schema-model-field name="uom"/>
						<kendo:dataSource-schema-model-field name="uomId"/>
						<kendo:dataSource-schema-model-field name="imQuantity" type="number"/>
						<kendo:dataSource-schema-model-field name="striDate" type="date">
							<kendo:dataSource-schema-model-field-validation required="true"/>
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="striTime" defaultValue="00:00"/>
						<kendo:dataSource-schema-model-field name="striDt" type="date"/>
						<kendo:dataSource-schema-model-field name="reasonForIssue" type="string"/>
						<kendo:dataSource-schema-model-field name="ledgerUpdateDt" type="date"/>
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
	</kendo:grid>
	
	<div id="alertsBox" title="Alert"></div>
	<div id="printMain" style="display:none">
	
		<div id="dialogBoxforBarcodeSGR" >
		</div>
		<br>
		
		<button id='print' class="myButton">Print</button>
		
	</div>
	
	<script type="text/javascript">
	$("#gridStoreIssue").on("click",".k-grid-storeIssueTemplatesDetailsExport", function(e) {
		  window.open("./storeIssueTemplate/storeIssueTemplatesDetailsExport");
	});	  

	$("#gridStoreIssue").on("click",".k-grid-storeIssuePdfTemplatesDetailsExport", function(e) {
		  window.open("./storeIssuePdfTemplate/storeIssuePdfTemplatesDetailsExport");
	});
	var storeIdSI = 0;
	var maximumQuantitySI = 0;
	var imIdSI = 0;
	var uomIdSI = 0;
	var uomSI = "";
	var storeName = "";
	var imName = "";
	var sessionUserLoginName = "";
	
	function onChangeStoreIssue(arg) {
		
		var gview = $("#gridStoreIssue").data("kendoGrid");
		var selectedItem = gview.dataItem(gview.select());
		storeName = selectedItem.storeName;
		imName = selectedItem.imName;
		this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
		
		sessionUserLoginName = '<%=session.getAttribute("userId")%>';
	}
	function clearFilterStoreIssue()
	{
		  $("form.k-filter-menu button[type='reset']").slice().trigger("click");
		  var gridStoreIssue = $("#gridStoreIssue").data("kendoGrid");
		  gridStoreIssue.dataSource.read();
		  gridStoreIssue.refresh();
	}
	
	 $("#gridStoreIssue").on("click", ".k-grid-add", function(e) { 
		 
		securityCheckForActions("./inventory/storeIssue/createButton");
			
		 if($("#gridStoreIssue").data("kendoGrid").dataSource.filter()){
				
	    		//$("#grid").data("kendoGrid").dataSource.filter({});
	    		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
	 			var grid = $("#gridStoreIssue").data("kendoGrid");
	 			grid.dataSource.read();
	 			grid.refresh();
	        }   
	 });
	
	function storeIssueEdit(e)
	{
		$('a[id=printSGRBtn]').remove();
		$('label[for="undefined"]').closest('.k-edit-label').remove();
		$('div[data-container-for="jobNo"]').remove();
		$('label[for="jobNo"]').closest('.k-edit-label').remove();
		$('div[data-container-for="storeName"]').remove();
		$('label[for="storeName"]').closest('.k-edit-label').remove();
		$('div[data-container-for="contractName"]').remove();
		$('label[for="contractName"]').closest('.k-edit-label').remove();
		$('div[data-container-for="imName"]').remove();
		$('label[for="imName"]').closest('.k-edit-label').remove();
		$('div[data-container-for="uom"]').remove();
		$('label[for="uom"]').closest('.k-edit-label').remove();
		$('label[for="striDt"]').parent().remove();
		$('div[data-container-for="striDt"]').hide();
		$('label[for="ledgerUpdateDt"]').parent().remove();
		$('div[data-container-for="ledgerUpdateDt"]').hide();
		
		
		if (e.model.isNew()) 
	    {
			
			$.ajax
			({
				type : "POST",
				url : "./common/checkIsStoreItemLedgerHasRecords/StoreIssue",
				success : function(response) 
				{
					if(response != "success")
					{
						$("#alertsBox").html("");
						$("#alertsBox").html(response);
						$("#alertsBox").dialog
						({
							modal : true,
							buttons : 
							{
								"Close" : function() 
								{
									$(this).dialog("close");
								}
							}
						});
						var gridStoreIssue = $("#gridStoreIssue").data("kendoGrid");
						gridStoreIssue.cancelRow();
					}	
				}
			});
			
			$(".k-window-title").text("Issue Store Item");
			$(".k-grid-update").text("Save");
			
			$('label[for="vcId"]').parent().hide();
			$('div[data-container-for="vcId"]').hide();
			$('label[for="imQuantity"]').parent().hide();
			$('div[data-container-for="imQuantity"]').hide();
	    }
		else
		{
			securityCheckForActions("./inventory/storeIssue/updateButton");
			
			$(".k-window-title").text("Edit Issued Store Item Details");
		}
		
		$('.k-edit-field .k-input').first().focus();
		
		var grid = this;
		e.container.on("keydown", function(e) {        
	        if (e.keyCode == kendo.keys.ENTER) {
	          $(document.activeElement).blur();
	          grid.saveRow();
	        }
	      });
		
		//CLIENT SIDE VALIDATION FOR MULTI SELECT
 		$(".k-grid-update").click(function () 
		{
 			var itemQuantity = $("#imQuantitySI").val();
 			
 			 if(itemQuantity == 0)
	       	 {
	       		 alert("Quantity cannot be zero - if you cannot see the quantity text box, please once again select the vendor contract name dropdown properly");
	       		 return false;
	       	 }
 			
 			 if((maximumQuantitySI > 0) && (itemQuantity > maximumQuantitySI))
	       	 {
	       		 alert("Maximum "+maximumQuantitySI+" "+uomSI+" is available in the store to issue with respect to this contract");
	       		 //return getMessage(input);
	       		 return false;
	       	 }	
 			 
 			 
 			var firstItem = $('#gridStoreIssue').data().kendoGrid.dataSource
 		      .data()[0];
 		    firstItem.set('vcId', $("#contractSI").val());
		}); 

	}
	
	//register custom validation rules
	(function($, kendo) {$.extend(true,
						kendo.ui.validator,
						{
							rules : { // custom rules
								imQuantitySizeValidation: function (input, params) 
					             {               	
					                 if (input.filter("[name='imQuantity']").length && input.val()) 
					                 {
					                	 if(input.val() > 999999999.99)
					                	 {
					                		 return false;
					                	 }	 
					                 }        
					                 return true;
					             },
								striTimeValidation: function (input, params) 
					             {               	
					                 if (input.filter("[name='Time']").length && input.val() ) 
					                 {
					                	var TimeReg = /^([01]?[0-9]|2[0-3]):[0-5][0-9]$/;
					                	if(!TimeReg.test(input.val()))
					                	{
					                		 return false;
					                	}
					                 }        
					                 return true;
					             }
							},
							messages : {
								imQuantitySizeValidation:"Maximum 999999999.99 is allowed to enter",
								striTimeValidation:"Invalid Time ( valid eg: 'HH:mm' - 14:25)"
								
							}
						});

	})(jQuery, kendo);
	//End Of Validation
	
	function storeIssueParse (response) {   
	    $.each(response, function (idx, elem) {

	    	if(elem.ledgerUpdateDt == null)
	    	{
	    		elem.ledgerUpdateDt = "";
	    	}	
	    	else
	    	{	
            	elem.ledgerUpdateDt = kendo.parseDate(new Date(elem.ledgerUpdateDt),'dd/MM/yyyy HH:mm');
	    	}
	    	
            	elem.striDt = kendo.parseDate(new Date(elem.striDt),'dd/MM/yyyy HH:mm');
            
        });
        return response;
	}
	
	
		
	function dateTimeEditorStoreIssue(container, options) {
	    $('<input data-text-field="' + options.field + '" data-value-field="' + options.field + '" data-bind="value:' + options.field + '" data-format="' + options.format + '"/>')
	            .appendTo(container)
	            .kendoDateTimePicker({});
	}
	
	function timeEditorStoreIssue(container, options) 
	{
		    $('<input name="Time" data-text-field="' + options.field + '" data-value-field="' + options.field + '" data-bind="value:' + options.field + '" data-format="' + options.format + '"  required="true"/>')
		            .appendTo(container)
		            .kendoTimePicker({});
		    
		    $('<span class="k-invalid-msg" data-for="Time"></span>').appendTo(container);
	}
	
	var requstedQuantity;
	
	function jobCardsSIEditor(container, options) 
   	{
		$('<input name="Job Card" id="jobCardsSI" data-text-field="jobNo" data-value-field="jcId" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoComboBox({
			autoBind : false,
			placeholder : "Select job card",
			headerTemplate : '<div class="dropdown-header">'
				+ '<span class="k-widget k-header">Photo</span>'
				+ '<span class="k-widget k-header">Contact info</span>'
				+ '</div>',
			template : '<table><tr>'
				+ '<td align="left"><span class="k-state-default"><b>#: data.jobNo #</b></span><br>'
				+ '<span class="k-state-default"><b>Job&nbsp;Name:&nbsp;&nbsp;</b>&nbsp;<i>#: data.jobName #</i></span></td></tr></table>',
			dataSource : {
				transport : {		
					read :  "${jobCardsSIComboBoxUrl}"
				}
			},
			change : function (e) {
	            if (this.value() && this.selectedIndex == -1) {                    
					alert("Job card doesn't exist!");
	                $("#jobCardsSI").data("kendoComboBox").value("");
	        	}
	            var combobox = $("#storeSI").data("kendoComboBox");
				combobox.value("");
				var combobox = $("#itemsSI").data("kendoComboBox");
				combobox.value("");
				var combobox = $("#uomIdSI").data("kendoComboBox");
				combobox.value("");
				var combobox = $("#contractSI").data("kendoComboBox");
				combobox.value("");				
				alert("Requested Quantity for this Job Card :"+this.dataItem().jobQuantity+" "+this.dataItem().uom);
				var numerictextbox = $("#imQuantitySI").data("kendoNumericTextBox");
				numerictextbox.value(0);
				maximumQuantitySI = 0;				
	            $('label[for="vcId"]').parent().hide();
				$('div[data-container-for="vcId"]').hide();
				$('label[for="imQuantity"]').parent().hide();
				$('div[data-container-for="imQuantity"]').hide();
		    } 
		});
		
		$('<span class="k-invalid-msg" data-for="Job Card"></span>').appendTo(container);
   	}
	
	function storeMasterSIEditor(container, options) 
   	{
		$('<input name="Store" id="storeSI" data-text-field="storeName" data-value-field="storeId" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoComboBox({
			cascadeFrom : "jobCardsSI",
			autoBind : false,
			placeholder : "Select store",
			headerTemplate : '<div class="dropdown-header">'
				+ '<span class="k-widget k-header">Photo</span>'
				+ '<span class="k-widget k-header">Contact info</span>'
				+ '</div>',
			template : '<table><tr>'
				+ '<td align="left"><span class="k-state-default"><b>#: data.storeName #</b></span><br>'
				+ '<span class="k-state-default"><b>Location:&nbsp;</b>&nbsp;<i>#: data.storeLocation #</i></span></td></tr></table>',
			dataSource : {
				transport : {		
					read :  "${storeMasterSIComboBoxUrl}"
				}
			},
			change : function (e) {
	            if (this.value() && this.selectedIndex == -1) {                    
					alert("Store doesn't exist!");
	                $("#storeSI").data("kendoComboBox").value("");
	        	}
	            
	            storeIdSI = this.value();
	            
				var combobox = $("#itemsSI").data("kendoComboBox");
				combobox.value("");
				var combobox = $("#uomIdSI").data("kendoComboBox");
				combobox.value("");
				var combobox = $("#contractSI").data("kendoComboBox");
				combobox.value("");
				var numerictextbox = $("#imQuantitySI").data("kendoNumericTextBox");
				numerictextbox.value(0);
				maximumQuantitySI = 0;
				
	            $('label[for="vcId"]').parent().hide();
				$('div[data-container-for="vcId"]').hide();
				$('label[for="imQuantity"]').parent().hide();
				$('div[data-container-for="imQuantity"]').hide();
		    } 
		});
		
		$('<span class="k-invalid-msg" data-for="Store"></span>').appendTo(container);
   	}
	
	function itemsSIEditor(container, options) 
   	{
		$('<input name="Item" id="itemsSI" data-text-field="imName" data-value-field="imId" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoComboBox({
			cascadeFrom : "storeSI",
			autoBind : false,
			placeholder : "Select item",
			headerTemplate : '<div class="dropdown-header">'
				+ '<span class="k-widget k-header">Photo</span>'
				+ '<span class="k-widget k-header">Contact info</span>'
				+ '</div>',
			template : '<table><tr>'
				+ '<td align="left"><span class="k-state-default"><b>#: data.imName #</b></span><br>'
				+ '<span class="k-state-default"><b>Type:&nbsp;</b>&nbsp;&nbsp;<i>#: data.imType #</i></span></td></tr></table>',
			dataSource : {
				transport : {		
					read :  "${itemsSIComboBoxUrl}"
				}
			},
			change : function (e) {
	            if (this.value() && this.selectedIndex == -1) {                    
					alert("Item doesn't exist!");
	                $("#itemsSI").data("kendoComboBox").value("");
	        	}
	            
				imIdSI = this.value();
	            
				var combobox = $("#uomIdSI").data("kendoComboBox");
				combobox.value("");
				var combobox = $("#contractSI").data("kendoComboBox");
				combobox.value("");
				var numerictextbox = $("#imQuantitySI").data("kendoNumericTextBox");
				numerictextbox.value(0);
				maximumQuantitySI = 0;
				
	            $('label[for="vcId"]').parent().hide();
				$('div[data-container-for="vcId"]').hide();
				$('label[for="imQuantity"]').parent().hide();
				$('div[data-container-for="imQuantity"]').hide();
		    } 
		});
		
		$('<span class="k-invalid-msg" data-for="Item"></span>').appendTo(container);
   	}
	
	function unitOfMeasurementSIEditor(container, options) 
   	{
		$('<input name="UOM" id="uomIdSI" data-text-field="uom" data-value-field="uomId" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoComboBox({
			cascadeFrom : "itemsSI",
			autoBind : false,
			placeholder : "Select uom",
			headerTemplate : '<div class="dropdown-header">'
				+ '<span class="k-widget k-header">Photo</span>'
				+ '<span class="k-widget k-header">Contact info</span>'
				+ '</div>',
			template : '<table><tr>'
				+ '<td align="left"><span class="k-state-default"><b>#: data.uom #</b></span><br>'
				+ '<span class="k-state-default">Code: <i>#: data.code #</i></span><br>'
				+ '<span class="k-state-default">Base UOM: <i>#: data.baseUom #</i></span><br>'
				+ '<span class="k-state-default">Conversion: <i>#: data.uomConversion #</i></span></td></tr></table>',
			dataSource : {
				transport : {		
					read :  "${unitOfMeasurementSIComboBoxUrl}"
				}
			},
			change : function (e) {
	            if (this.value() && this.selectedIndex == -1) {                    
					alert("Unit of measurement doesn't exist!");
	                $("#uomIdSI").data("kendoComboBox").value("");
	        	}
	            
	            uomIdSI = this.value();
	            var data=this.dataItem();
	           	uomSI = data.uom;
	           	codeSI = data.code;
	            
	           	var combobox = $("#contractSI").data("kendoComboBox");
				combobox.value("");
	            var numerictextbox = $("#imQuantitySI").data("kendoNumericTextBox");
				numerictextbox.value(0);
				maximumQuantitySI = 0;
				
				var data=this.dataItem();
	            
	            var result = 0;
	           	 $.ajax({
	           	     type : "GET",
	           	  	 async: false,
	           	  	 data : {
	           	  	    storeId : storeIdSI,
	           	  		imId : imIdSI,
	           	  		uomId : uomIdSI
						},
	           	     url : './common/getQuantityBasedOnStoreItemAndUom',
	           	     dataType : "JSON",
	           	     success : function(response) {       
	           	     	result=response;
	           	     }
	           	});
	           	 
	           	if(result != -1)
	           	{	
	           		$('label[for="vcId"]').parent().show();
					$('div[data-container-for="vcId"]').show();
					$('label[for="imQuantity"]').parent().hide();
					$('div[data-container-for="imQuantity"]').hide();
				}
	           	else
	           	{
					alert("Item with respect to selected store and item is empty in the ledger");
	           		$('label[for="vcId"]').parent().hide();
					$('div[data-container-for="vcId"]').hide();
					$('label[for="imQuantity"]').parent().hide();
					$('div[data-container-for="imQuantity"]').hide();
	           	}
		    } 
		});
		
		$('<span class="k-invalid-msg" data-for="UOM"></span>').appendTo(container);
   	}
	
	function itemQuantitySIEditor(container, options) 
   	{
		$('<input name="Issue quantity" id="imQuantitySI" data-text-field="imQuantity" data-value-field="imQuantity" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoNumericTextBox({
			min : 0
		});
		
		$('<span class="k-invalid-msg" data-for="Issue quantity"></span>').appendTo(container);
	}
	
	function vendorContractSIEditor(container, options) 
   	{
		$('<input name="Contract" id="contractSI" data-text-field="contractName" data-value-field="vcId" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoComboBox({
			autoBind : false,
			placeholder : "Select contract",
			headerTemplate : '<div class="dropdown-header">'
				+ '<span class="k-widget k-header">Photo</span>'
				+ '<span class="k-widget k-header">Contact info</span>'
				+ '</div>',
			template : '<table><tr>'
				+ '<td align="left"><span class="k-state-default"><b>#: data.contractName #</b></span><br>'
				+ '<span class="k-state-default"><b>Contract&nbsp;No:&nbsp;</b><i>#: data.contractNo #</i></span></td></tr></table>',
			dataSource : {
				transport: {
					read: {
						url: "${vendorContractSIComboBoxUrl}",
						dataType: "json",
						type: "POST",
					},
					parameterMap: function (data, type) {
							var values = {};
							values["storeId"] = storeIdSI;
							values["imId"] = imIdSI;
							values["uomId"] = uomIdSI;
							return values;
					}
				}	
			},
			change : function (e) {
	            if (this.value() && this.selectedIndex == -1) {                    
					alert("Contract doesn't exist!");
	                $("#contractSI").data("kendoComboBox").value("");
	        	}
				
				var data=this.dataItem();
	            
	            var result = 0;
	           	 $.ajax({
	           	     type : "GET",
	           	  	async: false,
	           	  	 data : {
	           	  	    storeId : storeIdSI,
	           	  		vcId : this.value(),
	           	  		imId : imIdSI,
	           	  		uomId : uomIdSI,
						},
	           	     url : './common/getQuantityBasedOnContractStoreAndItem',
	           	     dataType : "JSON",
	           	     success : function(response) {       
	           	     	result=response;
	           	     }
	           	});
	           	 
	           	maximumQuantitySI = result;	           	
	           	/* var numerictextbox = $("#itemQuantity").data("kendoNumericTextBox");
				numerictextbox.value(result); */
				
	           	$('label[for="imQuantity"]').parent().show();
				$('div[data-container-for="imQuantity"]').show();
				
				if(maximumQuantitySI == 0)
				{
					alert("Balance is empty because selected contract items may be transferred or adjusted with respect to selected store and item");
					 $("#itemQuantitySI").kendoNumericTextBox({
					    max: 0
					});
				}	
				
				$('label[for="imQuantity"]').text("Issue Quantity *\n(Balance: "+maximumQuantitySI+" "+uomSI+")");
		    } 
		});
		
		$('<span class="k-invalid-msg" data-for="Contract"></span>').appendTo(container);
   	}
	
	function onRequestStartStoreIssue(e)
	{
	
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();
        
		/* var gridStoreIssue = $("#gridStoreIssue").data("kendoGrid");
		gridStoreIssue.cancelRow(); */
	}
	
	function onRequestEndStoreIssue(e) 
	{
		if (typeof e.response != 'undefined') {
		 if(e.response.status=="invalid"){
			errorInfo = "";
			errorInfo = e.response.result.invalid;
			for (var i = 0; i < e.response.result.length; i++) {
				errorInfo += (i + 1) + ". "	+ e.response.result[i].defaultMessage;
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
			var grid = $("#gridStoreIssue").data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
			return false;
		}
		}
		displayMessage(e, "gridStoreIssue", "Store Issue"); 
	}	
	
	$("#gridStoreIssue").on("click", "#printSGRBtn", function(e) {
		var result=securityCheckForActionsForStatus("./inventory/storeIssue/deleteButton");
		
		if(result=="success"){
			var widget = $("#gridStoreIssue").data("kendoGrid");
			var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
			
			var data="<table><tbody><tr><th colspan='2' class='thclass'><h1>Store Details</h1></th></tr>";
			data=data+"<tr><td class='tdclass'>Job Number</td><td class='tdclass'>"+dataItem.jobNo+"</td></tr><tr><td class='tdclass'>Store Name</td><td class='tdclass'>"+dataItem.storeName+"</td></tr><tr><td class='tdclass'>Item Name</td>"+"<td class='tdclass'>"+dataItem.imName+"</td></tr>"+"<tr><td class='tdclass'>UOM</td><td class='tdclass'>"+dataItem.uom+"</td></tr><tr><td class='tdclass'>Quantity</td><td class='tdclass'>"+dataItem.imQuantity+"</td></tr><tr><td class='tdclass'>Vendor Contract</td><td class='tdclass'>"+dataItem.contractName+"</td></tr><tr><td class='tdclass'>Issue Date</td><td class='tdclass'>"+dataItem.striDate+"</td></tr><tr><td class='tdclass'>Reason</td><td class='tdclass'>"+dataItem.reasonForIssue+"</td></tr></tbody></table>";
	       
			$("#dialogBoxforBarcodeSGR").html(data);
			
		 	$("#print").on("click",function(){
				 var prtContent = document.getElementById('dialogBoxforBarcodeSGR');
	             var WinPrint = window.open('', '', 'letf=0,top=0,width=800,height=400,toolbar=0,scrollbars=0,status=0');
	             WinPrint.document.write(prtContent.innerHTML);
	             WinPrint.document.close();
	             WinPrint.focus();
	             WinPrint.print();
	             WinPrint.close();
			}); 
			
	         var undo = $("#printMain").kendoWindow({
				visible : false,
				resizable : false,
				modal : true,
				actions : ["Minimize",
						"Maximize", "Close"],
				title : "Gate Pass"
			}).data("kendoWindow");
	         undo.open().center();
		}
		
	});
	
	</script>
<!-- ------------------------------------------ Style  ------------------------------------------ -->

	<style>
	.flat-table {
		  display: block;
		  font-family: sans-serif;
		  -webkit-font-smoothing: antialiased;
		  font-size: 115%;
		  overflow: auto;
		  width: auto;
	  
		  
	}
	.thclass {
		background-color: rgb(112, 196, 105);
		color: white;
		font-weight: normal;
		padding: 20px 30px;
		text-align: center;
	}
	.tdclass {
		background-color: rgb(238, 238, 238);
		color: rgb(111, 111, 111);
		padding: 20px 30px;
	}
	.myButton {
		background-color:white;
		border:1px solid #18ab29;
		
		cursor:pointer;
		color:red;
		font-family:arial;
		font-size:17px;
		padding:8px 15px;
		text-decoration:none;
		text-shadow:0px 1px 0px red;
	}
	
	td {
    	vertical-align: middle;
	}
	</style>
