<%@include file="/common/taglibs.jsp"%>

<!-- Urls for Common controller  -->
	<c:url value="/common/getAllChecks" var="allChecksUrl" />
	<c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />
	<c:url value="/common/getFilterAutoCompleteValues" var="filterAutoCompleteUrl" />
	<c:url value="/common/getPersonNamesFilterList" var="personNamesFilterUrl" />
	<c:url value="/common/getPersonListBasedOnPersonType" var="personNamesAutoBasedOnPersonTypeUrl" />
	
	<!-- Urls for Store Goods Return  -->
	<c:url value="/storeGoodsReturns/read" var="readStoreGoodsReturnUrl" />
	<c:url value="/storeGoodsReturns/modify" var="modifyStoreGoodsReturnUrl" />

	<c:url value="/storeGoodsReturns/getJobNosSGREFilterUrl" var="jobNosSGREFilterUrl"/>
	<c:url value="/storeGoodsReturns/getJobCardsSGREComboBoxUrl" var="jobCardsSGREComboBoxUrl"/>	
	<c:url value="/storeGoodsReturns/getSGRStoreNamesFilterUrl" var="storeNamesSGRFilterUrl" />
	<c:url value="/storeGoodsReturns/getSGRStoreMasterComboBoxUrl" var="storeMasterSGRComboBoxUrl" />
	<c:url value="/storeGoodsReturns/getImNamesSGReturnFilterUrl" var="imNamesSGReturnFilterUrl"/>
	<c:url value="/storeGoodsReturns/getItemsSGRComboBoxUrl" var="itemsSGRComboBoxUrl"/>
	<c:url value="/storeGoodsReturns/getUomSGRFilterUrl" var="uomSGRFilterUrl"/>
	<c:url value="/storeGoodsReturns/getUnitOfMeasurementSGRComboBoxUrl" var="unitOfMeasurementSGRComboBoxUrl" />
	
	<!-- ------------------------------------ Grid -------------------------------------- -->
	
	<kendo:grid name="gridStoreGoodsReturn" edit="storeGoodsReturnEdit" pageable="true" resizable="true" dataBound="storeGoodsReturnDataBound" change="onChangeStoreGoodsReturn"
	sortable="true" reorderable="true" selectable="true" scrollable="true" groupable="true" filterable="true" navigatable="true">
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" refresh="true" info="true" previousNext="true">
			<kendo:grid-pageable-messages itemsPerPage="Store Goods Returns per page" empty="No Store Goods Return to display" refresh="Refresh all the Store Goods Returns" 
			display="{0} - {1} of {2} Store Goods Returns" first="Go to the first page of Store Goods Returns" last="Go to the last page of Store Goods Returns" next="Go to the next page of Store Goods Returns"
			previous="Go to the previous page of Store Goods Returns"/>
		</kendo:grid-pageable> 
		<kendo:grid-filterable extra="false">
			<kendo:grid-filterable-operators>
				<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains"/>
			</kendo:grid-filterable-operators>
		</kendo:grid-filterable>
		<kendo:grid-editable mode="popup" confirmation="Are you sure you want to delete this Store Goods Return record"/>
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Store Goods Return" />
			<kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterStoreGoodsReturn()><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>"/>
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
												read :  "${jobNosSGREFilterUrl}"
											}
										} 
									});
							   	}
							</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>	
			<kendo:grid-column title="Job&nbsp;No&nbsp;*" field="jcId" editor="jobCardsSGREEditor" width="0px" hidden="true"/>
			
			<kendo:grid-column title="Store&nbsp;Name&nbsp;*" field="storeName" width="130px">
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
										+ '<td align="left"><span class="k-state-default">Store&nbsp;Name:</b>&nbsp;<b>#: data.storeName #</b></span><br>'
										+ '<span class="k-state-default"><b>Store&nbsp;Location:</b>&nbsp;<i>#: data.storeLocation #</i></span></td></tr></table>',
									dataSource : {
										transport : {		
											read :  "${storeNamesSGRFilterUrl}"
										}
									} 
								});
						   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			<kendo:grid-column title="Store&nbsp;Name&nbsp;*" field="storeId" editor="storeMasterSGREditor" width="0px" hidden="true">
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
												read :  "${imNamesSGReturnFilterUrl}"
											}
										} 
									});
							   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>	
			<kendo:grid-column title="Item&nbsp;Name&nbsp;*" field="imId" editor="itemsSGREditor" width="0px" hidden="true"/>
			<kendo:grid-column title="Unit&nbsp;of&nbsp;Return&nbsp;*" field="uom" width="150px">
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
											+ '<span class="k-state-default">Base UOM: <i>#: data.baseUom #</i></span></td></tr></table>',
										dataSource : {
											transport : {		
												read :  "${uomSGRFilterUrl}"
											}
										} 
									});
							   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>	
	        <kendo:grid-column title="Unit&nbsp;of&nbsp;Measurement&nbsp;*" field="uomId" editor="unitOfMeasurementSGREditor" width="0px" hidden="true"/>
            
	        <kendo:grid-column title="Quantity&nbsp;*" field="itemReturnQuantity" editor="itemReturnQuantityEditor" width="80px"/>
	        <kendo:grid-column title="Returned&nbsp;By&nbsp;*" field="returnedByStaffName" width="120px">
			<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function personNameFilter(element) 
						   	{
								element.kendoComboBox({
									autoBind : true,
									dataTextField : "personName",
									dataValueField : "personName", 
									placeholder : "Enter name",
									headerTemplate : '<div class="dropdown-header">'
										+ '<span class="k-widget k-header">Photo</span>'
										+ '<span class="k-widget k-header">Contact info</span>'
										+ '</div>',
									template : '<table><tr><td rowspan=2><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.personId#'/>" width=40 height=55 alt=\"No Image to Display\" /></span></td>'
										+ '<td align="left"><span class="k-state-default"><b>#: data.personName #</b></span><br>'
										+ '<span class="k-state-default"><i>#: data.personStyle #</i></span><br>'
										+ '<span class="k-state-default"><i>#: data.personType #</i></span></td></tr></table>',
									dataSource : {
										transport : {		
											read :  "${personNamesFilterUrl}/StoreGoodsReturns/returnedByStaff"
										}
									} 
								});
						   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>	
			<kendo:grid-column title="Returned&nbsp;By&nbsp;*" field="returnedByStaffId" editor="personEditorStoreGoodsReturnsReturnedByStaff" width="0px" hidden="true"/>
			
				        <kendo:grid-column title="Returned&nbsp;To&nbsp;*" field="returnedToVendorName" width="120px">
			<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function personNameFilter(element) 
						   	{
								element.kendoComboBox({
									autoBind : true,
									dataTextField : "personName",
									dataValueField : "personName", 
									placeholder : "Enter name",
									headerTemplate : '<div class="dropdown-header">'
										+ '<span class="k-widget k-header">Photo</span>'
										+ '<span class="k-widget k-header">Contact info</span>'
										+ '</div>',
									template : '<table><tr><td rowspan=2><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.personId#'/>" width=40 height=55 alt=\"No Image to Display\" /></span></td>'
										+ '<td align="left"><span class="k-state-default"><b>#: data.personName #</b></span><br>'
										+ '<span class="k-state-default"><i>#: data.personStyle #</i></span><br>'
										+ '<span class="k-state-default"><i>#: data.personType #</i></span></td></tr></table>',
									dataSource : {
										transport : {		
											read :  "${personNamesFilterUrl}/StoreGoodsReturns/returnedToVendor"
										}
									} 
								});
						   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>	
			<kendo:grid-column title="Returned&nbsp;To&nbsp;*" field="returnedToVendorId" editor="personEditorStoreGoodsReturnsReturnedToVendor" width="0px" hidden="true"/>
			<kendo:grid-column title="Returned&nbsp;Date&nbsp;*" field="dateOfReturn" format="{0:dd/MM/yyyy}" width="110px"/>
			<kendo:grid-column title="Reason&nbsp;for&nbsp;Return" field="reasonForReturn" editor="textAreaEditor" width="140px" filterable="false"/>
			<%-- <kendo:grid-column title="Barcode&nbsp;Generator"  template="<a href='\\\#' class='k-button' id='printSGRBtn' onClick = 'generateBarcodeSGR()'>Print Gate Pass</a>" width="140px" /> --%>
			<kendo:grid-column title="&nbsp;" width="80px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit" />
				</kendo:grid-column-command>
			</kendo:grid-column>
        </kendo:grid-columns>
		<kendo:dataSource requestStart="onRequestStartStoreGoodsReturn" requestEnd="onRequestEndStoreGoodsReturn">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-read url="${readStoreGoodsReturnUrl}" dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-create url="${modifyStoreGoodsReturnUrl}/create" dataType="json" type="GET" contentType="application/json" />
				<kendo:dataSource-transport-update url="${modifyStoreGoodsReturnUrl}/update" dataType="json" type="GET" contentType="application/json" />
			</kendo:dataSource-transport>
			<kendo:dataSource-schema parse="StoreGoodsReturnParse">
				<kendo:dataSource-schema-model id="sgrId">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="sgrId" type="number"/> 
						<kendo:dataSource-schema-model-field name="jcId"/>
						<kendo:dataSource-schema-model-field name="jobNo"/>
						<kendo:dataSource-schema-model-field name="storeId"/>
						<kendo:dataSource-schema-model-field name="storeName"/>
						<kendo:dataSource-schema-model-field name="imName"/>
						<kendo:dataSource-schema-model-field name="imId"/>
						<kendo:dataSource-schema-model-field name="uom"/>
						<kendo:dataSource-schema-model-field name="uomId"/>
						<kendo:dataSource-schema-model-field name="returnedByStaffName"/>
						<kendo:dataSource-schema-model-field name="returnedByStaffId"/>
						<kendo:dataSource-schema-model-field name="returnedToVendorName"/>
						<kendo:dataSource-schema-model-field name="returnedToVendorId"/>
						<kendo:dataSource-schema-model-field name="itemReturnQuantity" type="number"/>
						<kendo:dataSource-schema-model-field name="dateOfReturn" type="date"/>
						<kendo:dataSource-schema-model-field name="reasonForReturn" type="string"/>
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
	</kendo:grid>
	
	<div id="alertsBox" title="Alert"></div>
	<div id="dialogBoxforBarcodeSGR" hidden="true" title="Gate Pass" style="text-align: center;"></div>
	
	<script type="text/javascript">
	
	var sgrId=0;
	var imId = 0;
	var storeIdSGRE = 0;
	var imIdSGRE = 0;
	var uomIdSGRE = 0;
	var maximumReturnQuantity = 0;
	var uom = "";
	var storeName = "";
	var imName = "";
	var sessionUserLoginName = "";
	
	function onChangeStoreGoodsReturn(arg) {
		
		var gview = $("#gridStoreGoodsReturn").data("kendoGrid");
		var selectedItem = gview.dataItem(gview.select());
		sgrId = selectedItem.sgrId;
		imId = selectedItem.imId;
		storeName = selectedItem.storeName;
		imName = selectedItem.imName;
		this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
		
		sessionUserLoginName = '<%=session.getAttribute("userId")%>';
	}
	
	function clearFilterStoreGoodsReturn()
	{
		  $("form.k-filter-menu button[type='reset']").slice().trigger("click");
		  var gridStoreGoodsReturn = $("#gridStoreGoodsReturn").data("kendoGrid");
		  gridStoreGoodsReturn.dataSource.read();
		  gridStoreGoodsReturn.refresh();
	}
	
	$("#gridStoreGoodsReturn").on("click", ".k-grid-add", function(e) 
	{ 
		securityCheckForActions("./inventory/storeGoodsReturns/createButton");
	    	
		if($("#gridStoreGoodsReturn").data("kendoGrid").dataSource.filter())
		 {
	    		//$("#grid").data("kendoGrid").dataSource.filter({});
	    		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
	 			var grid = $("#gridStoreGoodsReturn").data("kendoGrid");
	 			grid.dataSource.read();
	 			grid.refresh();
	     }   
	 });
	
	function storeGoodsReturnEdit(e)
	{
		$('a[id=printSGRBtn]').remove();
		$('label[for="undefined"]').closest('.k-edit-label').remove();
		$('div[data-container-for="jobNo"]').remove();
		$('label[for="jobNo"]').closest('.k-edit-label').remove();
		$('div[data-container-for="storeName"]').remove();
		$('label[for="storeName"]').closest('.k-edit-label').remove();
		$('div[data-container-for="imName"]').remove();
		$('label[for="imName"]').closest('.k-edit-label').remove();
		$('div[data-container-for="uom"]').remove();
		$('label[for="uom"]').closest('.k-edit-label').remove();
		$('div[data-container-for="returnedByStaffName"]').remove();
		$('label[for="returnedByStaffName"]').closest('.k-edit-label').remove();
		$('div[data-container-for="returnedToVendorName"]').remove();
		$('label[for="returnedToVendorName"]').closest('.k-edit-label').remove();
		
		if (e.model.isNew()) 
	    {
			$.ajax
			({
				type : "POST",
				url : "./common/checkIsStoreItemLedgerHasRecords/StoreGoodsReturn",
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
						var gridStoreGoodsReturn = $("#gridStoreGoodsReturn").data("kendoGrid");
						gridStoreGoodsReturn.cancelRow();
					}	
				}
			});
			
			$('label[for="itemReturnQuantity"]').parent().hide();
			$('div[data-container-for="itemReturnQuantity"]').hide();
			
			$(".k-window-title").text("New Store Goods Item Return");
			$(".k-grid-update").text("Save");
	    }
		else
		{
			securityCheckForActions("./inventory/storeGoodsReturns/updateButton");
			  
			$('label[for="jcId"]').parent().remove();
			$('div[data-container-for="jcId"]').remove();
			$('label[for="storeId"]').parent().remove();
			$('div[data-container-for="storeId"]').remove();
			$('label[for="imId"]').parent().remove();
			$('div[data-container-for="imId"]').remove();
			$('label[for="uomId"]').parent().remove();
			$('div[data-container-for="uomId"]').remove();
			$('label[for="itemReturnQuantity"]').parent().remove();
			$('div[data-container-for="itemReturnQuantity"]').remove();
			
			$(".k-window-title").text("Edit Store Goods Item Return Details");	

		}
		
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
 			var itemQuantity = $("#itemReturnQuantity").val();
 			
 			 if(itemQuantity == 0)
	       	 {
	       		 alert("Quantity cannot be zero");
	       		 return false;
	       	 }
 			
  			 if((maximumReturnQuantity > 0) && (itemQuantity > maximumReturnQuantity))
	       	 {
	       		 alert("Maximum "+maximumReturnQuantity+" "+uom+" can be returned to the store with respect to this contract");
	       		 //return getMessage(input);
	       		 return false;
	       	 }	 
		}); 
		
 		 
	}
	
	function storeGoodsReturnDataBound(e) 
	{
		/* var data = this.dataSource.view(),row;
	    for (var i = 0; i < data.length; i++) {
	        row = this.tbody.children("tr[data-uid='" + data[i].uid + "']");
	        
	        var status = data[i].sgrStatus;
	        var sgrId = data[i].sgrId;
	        if (status == 'Inactive') {
				
				$('#sgrStatusId_' + sgrId).show();
				//row.find(".k-grid-edit").hide();
		        row.find(".k-grid-delete").show();
			} else {
				$('#sgrStatusId_' + sgrId).hide();
				//row.find(".k-grid-edit").hide();
		        row.find(".k-grid-delete").hide();
			}
	    } */
	}
	
	function sgrStatusClick(text)
	{
		$.ajax
		({
			type : "POST",
			url : "./storeGoodsReturns/sgrStatus/" + sgrId,
			success : function(response) 
			{
				$("#alertsBox").html("");
				$("#alertsBox").html(response);
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				$('#gridStoreGoodsReturn').data('kendoGrid').dataSource.read();
			}
		});
	}
	
	
	function StoreGoodsReturnParse (response) {   
	    /* $.each(response, function (idx, elem) {

        }); */
        return response;
	}

	function generateBarcodeSGR(){
		
		var result=securityCheckForActionsForStatus("./inventory/storeGoodsReturns/deleteButton");
		
		if(result=="success"){
			 $("#dialogBoxforBarcodeSGR").kendoBarcode({
			        value: storeName.concat("-"+imName+"-").concat(sessionUserLoginName),
			        type: "code128",
			        width: 450,
			        height: 100
			    }); 
				 $('#dialogBoxforBarcodeSGR').dialog({
			         	width: 475,
			         	position: 'center',
						modal : true,
						 buttons: {
							 "Print": function() {						 
								 $.print("#dialogBoxforBarcodeSGR");					 
							  	 $( this ).dialog( "close" );
							 },
							 Cancel: function() {
							 $( this ).dialog( "close" );
							 }
							 }
					});  
		}		
	   
	} 
	
	function jobCardsSGREEditor(container, options) 
   	{
		$('<input name="Job Card" id="jobCardsSGRE" data-text-field="jobNo" data-value-field="jcId" data-bind="value:' + options.field + '" required="true"/>')
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
					read :  "${jobCardsSGREComboBoxUrl}"
				}
			},
			change : function (e) {
	            if (this.value() && this.selectedIndex == -1) {                    
					alert("Job card doesn't exist!");
	                $("#jobCardsSGRE").data("kendoComboBox").value("");
	        	}
	            
				var combobox = $("#storeSGRE").data("kendoComboBox");
				combobox.value("");
				var combobox = $("#itemsSGRE").data("kendoComboBox");
				combobox.value("");
				var combobox = $("#uomSGRE").data("kendoComboBox");
				combobox.value("");
				var numerictextbox = $("#itemReturnQuantity").data("kendoNumericTextBox");
				numerictextbox.value(0);
				maximumReturnQuantity = 0;
				
				$('label[for="itemReturnQuantity"]').parent().hide();
				$('div[data-container-for="itemReturnQuantity"]').hide();
		    } 
		});
		
		$('<span class="k-invalid-msg" data-for="Job Card"></span>').appendTo(container);
   	}
	
	function storeMasterSGREditor(container, options) 
   	{
		$('<input name="Store" id="storeSGRE" data-text-field="storeName" data-value-field="storeId" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoComboBox({
			autoBind : false,
			cascadeFrom : "jobCardsSGRE",
			placeholder : "Select to store",
			headerTemplate : '<div class="dropdown-header">'
				+ '<span class="k-widget k-header">Photo</span>'
				+ '<span class="k-widget k-header">Contact info</span>'
				+ '</div>',
			template : '<table><tr>'
				+ '<td align="left"><span class="k-state-default"><b>#: data.storeName #</b></span><br>'
				+ '<span class="k-state-default"><i>#: data.storeLocation #</i></span></td></tr></table>',
			dataSource : {
				transport : {		
					read :  "${storeMasterSGRComboBoxUrl}"
				}
			},
			change : function (e) {
	            if (this.value() && this.selectedIndex == -1) {                    
					alert("Store doesn't exist!");
	                $("#storeSGRE").data("kendoComboBox").value("");
	        	}
	            
	            storeIdSGRE = this.value();

				var combobox = $("#itemsSGRE").data("kendoComboBox");
				combobox.value("");
				var combobox = $("#uomSGRE").data("kendoComboBox");
				combobox.value("");
				var numerictextbox = $("#itemReturnQuantity").data("kendoNumericTextBox");
				numerictextbox.value(0);
				maximumReturnQuantity = 0;
				
				$('label[for="itemReturnQuantity"]').parent().hide();
				$('div[data-container-for="itemReturnQuantity"]').hide();
		    } 
		});
		
		$('<span class="k-invalid-msg" data-for="Store"></span>').appendTo(container);
   	}
	
	function itemsSGREditor(container, options) 
   	{
		$('<input name="Store item" id="itemsSGRE" data-text-field="imName" data-value-field="imId" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoComboBox({
			cascadeFrom : "storeSGRE",
			autoBind : false,
			placeholder : "Select item",
			headerTemplate : '<div class="dropdown-header">'
				+ '<span class="k-widget k-header">Photo</span>'
				+ '<span class="k-widget k-header">Contact info</span>'
				+ '</div>',
			template : '<table><tr>'
					+ '<td align="left"><span class="k-state-default"><b>#: data.imName #</b></span><br>'
					+ '<span class="k-state-default"><b>Type:&nbsp;</b>&nbsp;&nbsp;<i>#: data.imType #</i></span><br>'
					+ '<span class="k-state-default"><b>Group:&nbsp;</b>&nbsp;&nbsp;<i>#: data.imGroup #</i></span></td></tr></table>',
			dataSource : {
				transport : {		
					read :  "${itemsSGRComboBoxUrl}"
				}
			},
			change : function (e) {
	            if (this.value() && this.selectedIndex == -1) {                    
					alert("Store item doesn't exist!");
	                $("#itemsSGRE").data("kendoComboBox").value("");
	        	}
	            
	           	imIdSGRE = this.value();
	            
				var combobox = $("#uomSGRE").data("kendoComboBox");
				combobox.value("");
				var numerictextbox = $("#itemReturnQuantity").data("kendoNumericTextBox");
				numerictextbox.value(0);
				maximumReturnQuantity = 0;
				
				$('label[for="itemReturnQuantity"]').parent().hide();
				$('div[data-container-for="itemReturnQuantity"]').hide();
		    } 
		});
		
		$('<span class="k-invalid-msg" data-for="Store item"></span>').appendTo(container);
   	}

	function unitOfMeasurementSGREditor(container, options) 
   	{
		$('<input name="Item UOM" id="uomSGRE" data-text-field="uom" data-value-field="uomId" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoComboBox({
			cascadeFrom : "itemsSGRE",
			autoBind : false,
			placeholder : "Select uom",
			headerTemplate : '<div class="dropdown-header">'
				+ '<span class="k-widget k-header">Photo</span>'
				+ '<span class="k-widget k-header">Contact info</span>'
				+ '</div>',
			template : '<table><tr>'
				+ '<td align="left"><span class="k-state-default"><b>#: data.uom #</b></span><br>'
				+ '<span class="k-state-default">Code: <i>#: data.code #</i></span><br>'
				+ '<span class="k-state-default">Base UOM: <i>#: data.baseUom #</i></span></td></tr></table>',
			dataSource : {
				transport : {		
					read :  "${unitOfMeasurementSGRComboBoxUrl}"
				}
			},
			change : function (e) {
	            if (this.value() && this.selectedIndex == -1) {                    
					a,lert("Unit of measurement doesn't exist!");
	                $("#uomSGRE").data("kendoComboBox").value("");
	        	}     
	            
	            uomIdSGRE = this.value();
	            var data=this.dataItem();
	           	uom = data.uom;
	           	code = data.code;
	            
	            var numerictextbox = $("#itemReturnQuantity").data("kendoNumericTextBox");
				numerictextbox.value(0);
				
				var data=this.dataItem();
	            
	            var result = 0;
	           	 $.ajax({
	           	     type : "GET",
	           	  	 async: false,
	           	  	 data : {
	           	  	    storeId : storeIdSGRE,
	           	  		imId : imIdSGRE,
	           	  		uomId : uomIdSGRE
						},
	           	     url : './common/getQuantityBasedOnStoreItemAndUom',
	           	     dataType : "JSON",
	           	     success : function(response) {       
	           	     	result=response;
	           	     }
	           	});
	           	 
	           	if(result != -1)
	           	{	
					$('label[for="itemReturnQuantity"]').parent().show();
					$('div[data-container-for="itemReturnQuantity"]').show();
					
					maximumReturnQuantity = result;
					
					$('label[for="itemReturnQuantity"]').text("Item Quantity *\n(Return at max: "+maximumReturnQuantity+" "+uom+")");
				}
	           	else
	           	{
					alert("Item with respect to selected store and item is empty in the ledger");
					$('label[for="itemReturnQuantity"]').parent().hide();
					$('div[data-container-for="itemReturnQuantity"]').hide();
	           	}
		    } 
		});
		
		$('<span class="k-invalid-msg" data-for="Item UOM"></span>').appendTo(container);
   	}

	function itemReturnQuantityEditor(container, options) 
   	{
		$('<input name="Item return quantity" id="itemReturnQuantity" data-text-field="itemReturnQuantity" data-value-field="itemReturnQuantity" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoNumericTextBox({
			min : 0
		});
		
		$('<span class="k-invalid-msg" data-for="Item return quantity"></span>').appendTo(container);
	}
	
	function personEditorStoreGoodsReturnsReturnedByStaff(container, options) 
   	{
		$('<input name="Returned by" id="returnedStaff" data-text-field="personName" data-value-field="id" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoComboBox({
			autoBind : false,
			placeholder : "Select staff",
			headerTemplate : '<div class="dropdown-header">'
				+ '<span class="k-widget k-header">Photo</span>'
				+ '<span class="k-widget k-header">Contact info</span>'
				+ '</div>',
			template : '<table><tr><td rowspan=2><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.personId#'/>" width=40 height=55 alt=\"No Image to Display\" /></span></td>'
				+ '<td align="left"><span class="k-state-default"><b>#: data.personName #</b></span><br>'
				+ '<span class="k-state-default"><i>#: data.personStyle #</i></span><br>'
				+ '<span class="k-state-default"><i>#: data.personType #</i></span></td></tr></table>',
			dataSource : {
				transport : {		
					read :  "${personNamesAutoBasedOnPersonTypeUrl}/Staff"
				}
			},
			change : function (e) {
	            if (this.value() && this.selectedIndex == -1) {                    
					alert("Staff doesn't exist!");
	                $("#returnedStaff").data("kendoComboBox").value("");
	        	}
		    } 
		});
		
		$('<span class="k-invalid-msg" data-for="Returned by"></span>').appendTo(container);
   	}
	
	function personEditorStoreGoodsReturnsReturnedToVendor(container, options) 
   	{
		$('<input name="Returned to" id="returnedVendor" data-text-field="personName" data-value-field="id" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoComboBox({
			autoBind : false,
			placeholder : "Select vendor",
			headerTemplate : '<div class="dropdown-header">'
				+ '<span class="k-widget k-header">Photo</span>'
				+ '<span class="k-widget k-header">Contact info</span>'
				+ '</div>',
			template : '<table><tr><td rowspan=2><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.personId#'/>" width=40 height=55 alt=\"No Image to Display\" /></span></td>'
				+ '<td align="left"><span class="k-state-default"><b>#: data.personName #</b></span><br>'
				+ '<span class="k-state-default"><i>#: data.personStyle #</i></span><br>'
				+ '<span class="k-state-default"><i>#: data.personType #</i></span></td></tr></table>',
			dataSource : {
				transport : {		
					read :  "${personNamesAutoBasedOnPersonTypeUrl}/Vendor"
				}
			},
			change : function (e) {
	            if (this.value() && this.selectedIndex == -1) {                    
					alert("Vendor doesn't exist!");
	                $("#returnedVendor").data("kendoComboBox").value("");
	        	}
		    } 
		});
		
		$('<span class="k-invalid-msg" data-for="Returned to"></span>').appendTo(container);
   	}
	
	//register custom validation rules
	(function($, kendo) {$.extend(true,
						kendo.ui.validator,
						{
							rules : { // custom rules
								
							},
							messages : {
								
							}
						});

	})(jQuery, kendo);
	//End Of Validation
	
	function onRequestStartStoreGoodsReturn(e)
	{
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();
        
		/* var gridStoreGoodsReturn = $("#gridStoreGoodsReturn").data("kendoGrid");
		gridStoreGoodsReturn.cancelRow(); */
	}
	
	function onRequestEndStoreGoodsReturn(e) 
	{
		displayMessage(e, "gridStoreGoodsReturn", "Store Goods Return");
	}	
	
	</script>
<!-- ------------------------------------------ Style  ------------------------------------------ -->

	<style>
	td {
    	vertical-align: middle;
	}
	</style>