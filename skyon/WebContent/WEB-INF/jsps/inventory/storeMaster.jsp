<%@include file="/common/taglibs.jsp"%>

	<!-- Urls for Common controller  -->
	<c:url value="/common/getFilterAutoCompleteValues" var="filterAutoCompleteUrl" />
	<c:url value="/common/getPersonListBasedOnPersonType" var="personNamesAutoBasedOnPersonTypeUrl" />
	
	<!-- Urls for Store Master  -->
	<c:url value="/storeMaster/read" var="readStoreMasterUrl" />
	<c:url value="/storeMaster/modify" var="modifyStoreMasterUrl" />
	
	<c:url value="/storeMaster/getStoreNamesForFilterList" var="storeNameFilterUrl" />
	<c:url value="/storeMaster/getstoreInchargeStaffNamesFilterList" var="storeInchargeStaffNameFilterUrl" />
	<c:url value="/storeMaster/storeLocationFilterUrl" var="storeLocationFilterUrl" />
	
	
	<!-- ------------------------------------ Grid -------------------------------------- -->
	
	<kendo:grid name="gridStoreMaster" edit="storeMasterEdit" pageable="true" resizable="true" dataBound="storeMasterDataBound" change="onChangeStoreMaster"
	sortable="true" reorderable="true" selectable="true" scrollable="true" groupable="true" filterable="true" navigatable="true">
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" refresh="true" info="true" previousNext="true">
			<kendo:grid-pageable-messages itemsPerPage="Stores per page" empty="No Store to display" refresh="Refresh all the Stores" 
			display="{0} - {1} of {2} Stores" first="Go to the first page of Stores" last="Go to the last page of Stores" next="Go to the next page of Stores"
			previous="Go to the previous page of Stores"/>
		</kendo:grid-pageable> 
		<kendo:grid-filterable extra="false">
			<kendo:grid-filterable-operators>
				<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains"/>
			</kendo:grid-filterable-operators>
		</kendo:grid-filterable>
		<kendo:grid-editable mode="popup"/>
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Store" />
			<kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterStoreMaster()><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>"/>
		</kendo:grid-toolbar>
		<kendo:grid-columns>
			<kendo:grid-column title="Store&nbsp;Name" field="storeName" width="140px" >
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function storeNameFilter(element) {
								element.kendoComboBox({
									placeholder : "Enter store name",
									dataSource : {
										transport : {
											read : "${storeNameFilterUrl}"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			<kendo:grid-column title="Store&nbsp;Incharge" field="storeInchargeStaffName" width="80px">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function personNameFilter(element) 
						   	{
								element.kendoComboBox({
									autoBind : false,
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
											read :  "${storeInchargeStaffNameFilterUrl}"
										}
									} 
								});
						   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>	
			<kendo:grid-column title="Store&nbsp;Incharge" field="storeInchargeStaffId" editor="personEditorStoreMaster" width="0px" hidden="true">
            </kendo:grid-column>
			<kendo:grid-column title="Store&nbsp;Location" field="storeLocation" editor="textAreaEditor" width="180px" >
			<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function storeNameFilter(element) {
								element.kendoComboBox({
									placeholder : "Enter store Location",
									dataSource : {
										transport : {
											read : "${storeLocationFilterUrl}"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			<kendo:grid-column title="Status" field="storeStatus" width="60px">
				 <kendo:grid-column-values value="${status}"/>
			</kendo:grid-column>
			<kendo:grid-column title="&nbsp;" width="60px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit" />
				</kendo:grid-column-command>
			</kendo:grid-column>
		<kendo:grid-column title=""
				template="<a href='\\\#' id='storeStatusId_#=storeId#' onClick = 'storeStatusClick(this.text)' class='k-button k-button-icontext btn-destroy k-grid-Activate#=data.storeId#'>#= data.storeStatus == 'Active' ? 'De-activate' : 'Activate' #</a>"
				width="60px" />
        </kendo:grid-columns>
		<kendo:dataSource requestStart="onRequestStartStoreMaster" requestEnd="onRequestEndStoreMaster">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-read url="${readStoreMasterUrl}" dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-create url="${modifyStoreMasterUrl}/create" dataType="json" type="GET" contentType="application/json" />
				<kendo:dataSource-transport-update url="${modifyStoreMasterUrl}/update" dataType="json" type="GET" contentType="application/json" />
			</kendo:dataSource-transport>
			<kendo:dataSource-schema>
				<kendo:dataSource-schema-model id="storeId">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="storeId" type="number"/> 
						<kendo:dataSource-schema-model-field name="storeName" type="string"/>
						<kendo:dataSource-schema-model-field name="storeInchargeStaffName" type="string"/>
						<kendo:dataSource-schema-model-field name="storeInchargeStaffId" /> 
						<kendo:dataSource-schema-model-field name="storeLocation" type="string"/>
						<kendo:dataSource-schema-model-field name="storeStatus" defaultValue="Inactive" type="string"/>
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
	</kendo:grid>
	
	<div id="alertsBox" title="Alert"></div>
	
	<script type="text/javascript">

	var storeName = "";
	var storeStatus = "";
	var storeId = 0;
	
	function onChangeStoreMaster(arg) {
		
		var gview = $("#gridStoreMaster").data("kendoGrid");
		var selectedItem = gview.dataItem(gview.select());
		storeId = selectedItem.storeId;
		storeStatus = selectedItem.storeStatus;
		storeName = selectedItem.storeName;
		this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
	}
	
	function clearFilterStoreMaster()
	{
		  $("form.k-filter-menu button[type='reset']").slice().trigger("click");
		  var gridStoreMaster = $("#gridStoreMaster").data("kendoGrid");
		  gridStoreMaster.dataSource.read();
		  gridStoreMaster.refresh();
	}
	
	 $("#gridStoreMaster").on("click", ".k-grid-add", function(e) { 
		 
		 securityCheckForActions("./inventory/storeMaster/createButton");
			
	     	
		 if($("#gridStoreMaster").data("kendoGrid").dataSource.filter()){
				
	    		//$("#grid").data("kendoGrid").dataSource.filter({});
	    		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
	 			var grid = $("#gridStoreMaster").data("kendoGrid");
	 			grid.dataSource.read();
	 			grid.refresh();
	        }   
	 });
	
	function storeMasterEdit(e)
	{
		$('label[for="storeName"]').text("Store Name *");
		$('label[for="storeInchargeStaffId"]').text("Store Incharge *");
		
		$('div[data-container-for="storeInchargeStaffName"]').remove();
		$('label[for="storeInchargeStaffName"]').closest('.k-edit-label').remove();
		
		$('div[data-container-for="storeStatus"]').remove();
		$('label[for="storeStatus"]').closest('.k-edit-label').remove();
		
		e.container.find(".k-grid-cancel").bind("click", function () {
 	    	var grid = $("#gridStoreMaster").data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
	    }); 
		
		if (e.model.isNew()) 
	    {
			$(".k-window-title").text("Add New Store Record");
			$(".k-grid-update").text("Save");
	    }
		else
		{
			securityCheckForActions("./inventory/storeMaster/updateButton");
				
			if(e.model.storeStatus == "Active")
			{
				$('div[data-container-for="storeName"]').remove();
				$('label[for="storeName"]').closest('.k-edit-label').remove();
			}	
			
			$(".k-window-title").text("Edit Store Details of "+storeName);
		}
		
		$('a[id^=storeStatusId_]').parent().remove();
		
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
 			var specialInstructions = $('textarea[data-bind="value:storeLocation"]').val();
 			if(specialInstructions.length > 225)
		    {
		    	alert("Maximum 225 characters are allowed to enter as Store Location");
		    	return false;
		    }  
		}); 
	}
	
	function storeMasterDataBound(e) 
	{
		var data = this.dataSource.view(),row;
	    var grid = $("#gridStoreMaster").data("kendoGrid");
	    for (var i = 0; i < data.length; i++) {
	    	var currentUid = data[i].uid;
	        row = this.tbody.children("tr[data-uid='" + data[i].uid + "']");
	        
	        var storeStatus = data[i].storeStatus;
	        var storeId = data[i].storeId;
	        if (storeStatus == 'Inactive')
	        {
				$('#storeStatusId_' + storeId).text("Activate");
			} 
	        else
	        {
				$('#storeStatusId_' + storeId).text("De-Activate");
			}
	        
	        if(storeStatus=="Active"){
	        	
	        	var currenRow = grid.table.find("tr[data-uid='" + currentUid+ "']");
				var reOpenButton = $(currenRow).find('#storeStatusId_' + storeId);
				reOpenButton.hide();
	        }
	    }
	}
	
	//register custom validation rules
	(function($, kendo) {$.extend(true,
						kendo.ui.validator,
						{
							rules : { // custom rules          	         	
								storeNamevalidation : function(input, params){
									if (input.filter("[name='storeName']").length && input.val()) 
									{
										return /^[a-zA-Z ]{1,45}$/.test(input.val());
									}
									return true;
								},
								storeNameSpacesvalidation : function(input, params){ 
									if (input.filter("[name='storeName']").length && input.val()) 
									{
										if(input.val().trim() == "")
										{
											return false;
										}	
									}
									return true;
								},
								storeName_blank : function(input, params){
									if (input.attr("name") == "storeName") 
									{
										return $.trim(input.val()) !== "";
									}
									return true;
								   }
							},
							messages : {
								//custom rules messages
								storeNamevalidation : "Store name can contain alphabets and spaces but cannot allow other special characters and maximum 45 characters are allowed",
								storeNameSpacesvalidation : "Store name cannot contain only spaces",
								storeName_blank : "Store Name is required"
							}
						});

	})(jQuery, kendo);
	//End Of Validation
	
	function personEditorStoreMaster(container, options)  
   	{
		$('<input name="Incharge Staff" id="person3" data-text-field="personName" data-value-field="id" data-bind="value:' + options.field + '" required="true"/>')
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
	                $("#person3").data("kendoComboBox").value("");
	        	}
		    } 
		});
		
		$('<span class="k-invalid-msg" data-for="Incharge Staff"></span>').appendTo(container);
   	}
	
	function storeStatusClick(text)
	{
		
		 $('tr[aria-selected="true"]').find('td:nth-child(10)').html("");
		 $('tr[aria-selected="true"]').find('td:nth-child(10)').html("<img src='./resources/images/progressbar.gif' width='100px' height='25px' />"); 
		 
		var result=securityCheckForActionsForStatus("./inventory/storeMaster/deleteButton");
		
		if(result=="success"){
			if (text == "Activate") 
			{
				$.ajax
				({
					type : "POST",
					url : "./storeMaster/storeStatus/" + storeId + "/activate",
					success : function(response) 
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
						$('#gridStoreMaster').data('kendoGrid').dataSource.read();
					}
				});
			} 
			else 
			{
				$.ajax
				({
					type : "POST",
					url : "./storeMaster/storeStatus/" + storeId + "/deactivate",
					success : function(response) 
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
						$('#gridStoreMaster').data('kendoGrid').dataSource.read();
					}
				});
			}
		}
		
	}
	
	function onRequestStartStoreMaster(e)
	{
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();
        
		/* var gridStoreMaster = $("#gridStoreMaster").data("kendoGrid");
		gridStoreMaster.cancelRow(); */
	}
	
	function onRequestEndStoreMaster(e) 
	{
		displayMessage(e, "gridStoreMaster", "Store Master");
	}	
	
	</script>
<!-- ------------------------------------------ Style  ------------------------------------------ -->

	<style>
	td {
    	vertical-align: middle;
	}
	</style>
