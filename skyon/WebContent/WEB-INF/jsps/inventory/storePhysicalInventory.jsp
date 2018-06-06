<%@include file="/common/taglibs.jsp"%>
	
<!-- Urls for Store Physical Inventory  -->
	<c:url value="/storePhysicalInventory/read" var="readStorePhysicalInventoryUrl" />
	<c:url value="/storePhysicalInventory/modify" var="modifyStorePhysicalInventoryUrl" />
	
	<c:url value="/storePhysicalInventory/getStorePhysicalInventoryCategories" var="categoriesReadUrlStorePhysicalInventory" />
	<c:url value="/storePhysicalInventory/getStorePhysicalInventorySubCategories" var="subCategoriesReadUrlStorePhysicalInventory" />

	
	<!-- 	Asset Category & Location Url	 -->
	<c:url value="/storePhysicalInventory/category/read" var="categoriesReadTreeStorePhysicalInventory" />
	
	<!-- Urls for Store Physical Inventory Report -->
	<c:url value="/storePhysicalInventoryReport/read/" var="readStorePhysicalInventoryReportUrl" />
	<c:url value="/storePhysicalInventoryReport/modify" var="modifyStorePhysicalInventoryReportUrl" />
	
	<c:url value="/storePhysicalInventoryReport/getConditions" var="getConditionUrl" />
	
<!-- End of Urls  -->
			
<kendo:grid name="gridStorePhysicalInventory" edit="storePhysicalInventoryEdit" pageable="true" resizable="true" detailTemplate="gridStorePhysicalInventoryReportTemplate" change="onChangeStorePhysicalInventory" dataBound="storePhysicalInventoryDataBound"
	sortable="true" reorderable="true" selectable="true" scrollable="true" groupable="true" filterable="true" navigatable="true">
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" refresh="true" info="true" previousNext="true">
			<kendo:grid-pageable-messages itemsPerPage="Inventories per page" empty="No Inventory to display" refresh="Refresh all the Inventories" 
			display="{0} - {1} of {2} Inventories" first="Go to the first page of Inventories" last="Go to the last page of Inventories" next="Go to the next page of Inventories"
			previous="Go to the previous page of Inventories"/>
		</kendo:grid-pageable> 
		<kendo:grid-editable mode="popup"/>
		<kendo:grid-filterable extra="false">
			<kendo:grid-filterable-operators>
				<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains"/>
			</kendo:grid-filterable-operators>
		</kendo:grid-filterable>
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Store Physical Inventory Survey" />
			<kendo:grid-toolbarItem name="storeInventoryTemplatesDetailsExport" text="Export To Excel" />
			  <kendo:grid-toolbarItem name="storeInventoryPdfTemplatesDetailsExport" text="Export To PDF" /> 
			<kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterStorePhysicalInventory()><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>"/>
			<kendo:grid-toolbarItem template="&nbsp;&nbsp;&nbsp;<span class='bgGreenColor' style='width:60px; height :20px'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;: Started"/>
			<kendo:grid-toolbarItem template="&nbsp;&nbsp;&nbsp;<span class='bgRedColor' style='width:60px; height :20px'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;: Suspended"/>
			<kendo:grid-toolbarItem template="&nbsp;&nbsp;&nbsp;<span class='bgGreenColor' style='width:60px; height :20px'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;: Continued"/>
			<kendo:grid-toolbarItem template="&nbsp;&nbsp;&nbsp;<span class='bgBlueColor' style='width:60px; height :20px'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;: Completed"/>
			
		</kendo:grid-toolbar>
		<kendo:grid-columns>
			<kendo:grid-column title="Survey&nbsp;Created&nbsp;Date&nbsp;Time" field="spiDt" format="{0:dd/MM/yyyy HH:mm}" width="140px" />
			<kendo:grid-column title="Survey&nbsp;Started&nbsp;Date&nbsp;Time" field="surveyDt" format="{0:dd/MM/yyyy HH:mm}" width="140px" />
			<kendo:grid-column title="Survey&nbsp;Name&nbsp;*" field="surveyName" width="90px" />
			<kendo:grid-column title="Survey&nbsp;Description&nbsp;" field="surveyDescription" width="100px" editor="textAreaEditor" filterable="false"/>
			<kendo:grid-column title="Survey&nbsp;Completed&nbsp;Date&nbsp;Time" field="surveyCompleteDt" format="{0:dd/MM/yyyy HH:mm}" width="160px" />
			<kendo:grid-column title="Category&nbsp;*" field="category" hidden="true" width="80px" editor="categoryEditor"/>
			<kendo:grid-column title="Category&nbsp;Ids" field="categoryIds" hidden="true" width="px"/>
			<kendo:grid-column title="Status" field="spiStatus"	width="80px" /> 		
			<kendo:grid-column title=""
				template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Activate#=data.spiId#'>#= data.spiStatus == 'Created' ? 'Start' : data.spiStatus == 'Started' ? 'Complete' : data.spiStatus == 'Completed' ? 'Complete' : 'Complete' #</a>&nbsp;<a href='\\\#' id='btn2_#=data.spiId#' class='k-button k-btn2' style='display:none' onClick=secondBtn(this.text)>#= data.spiStatus == 'Started' ? 'Suspend' : data.spiStatus == 'Suspended' ? 'Continue' : data.spiStatus == 'Continued' ? 'Suspend' : 'Continue' #</a>"
				width="200px" />
			<kendo:grid-column title="&nbsp;" width="80px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit" />
				</kendo:grid-column-command>
			</kendo:grid-column>
		</kendo:grid-columns>
		<kendo:dataSource requestStart="onRequestStartStorePhysicalInventory" requestEnd="onRequestEndStorePhysicalInventory">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-read url="${readStorePhysicalInventoryUrl}" dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-create url="${modifyStorePhysicalInventoryUrl}/create" dataType="json" type="GET" contentType="application/json" />
				<kendo:dataSource-transport-update url="${modifyStorePhysicalInventoryUrl}/update" dataType="json" type="GET" contentType="application/json" />
			</kendo:dataSource-transport>
			<kendo:dataSource-schema parse="storePhysicalInventoryParse">
				<kendo:dataSource-schema-model id="spiId">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="spiId" type="number"/>
						<kendo:dataSource-schema-model-field name="surveyName"><kendo:dataSource-schema-model-field-validation required="true" pattern="^.{0,45}$"/></kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="surveyDescription" />
						<kendo:dataSource-schema-model-field name="categoryIds" />
						<kendo:dataSource-schema-model-field name="spiDt" type="date"/>
						<kendo:dataSource-schema-model-field name="surveyDt" type="date"/>
						<kendo:dataSource-schema-model-field name="surveyCompleteDt" type="date"/>
						<kendo:dataSource-schema-model-field name="spiStatus" defaultValue="Created" type="string"/>
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
	</kendo:grid>
	
	<kendo:grid-detailTemplate id="gridStorePhysicalInventoryReportTemplate">
		<kendo:tabStrip name="tabStrip_#=spiId#">
			<kendo:tabStrip-items>
				<kendo:tabStrip-item text="Reports" selected="true">
                <kendo:tabStrip-item-content>
                    <div class="wethear">
                      <kendo:grid name="gridStorePhysicalInventoryReport_#=spiId#" edit="storePhysicalInventoryReportEdit" pageable="true" resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true" groupable="true" filterable="false" navigatable="true" dataBound="storePhysicalInventoryReportDataBound">
							<kendo:grid-pageable pageSize="10">
								<kendo:grid-pageable-messages itemsPerPage="Reports per page" empty="No Report to display" refresh="Refresh all the Reports" 
								display="{0} - {1} of {2} Reports" first="Go to the first page of Reports" last="Go to the last page of Reports" next="Go to the next page of Reports"
								previous="Go to the previous page of Reports"/>
							</kendo:grid-pageable> 
							<kendo:grid-editable mode="popup"/>
							<kendo:grid-columns>
								<kendo:grid-column title="Store&nbsp;Name" field="storeName" width="100px"/>
						        <kendo:grid-column title="Item&nbsp;Name" field="imName" width="100px"/>
						        <kendo:grid-column title="Unit&nbsp;of&nbsp;measuremant" field="uom" width="100px"/>
						        <kendo:grid-column title="Condition" field="spiCondition" editor="conditionEditor" width="80px"/>
									 <%-- <kendo:grid-column-values value="${spiCondition}"/> 
								</kendo:grid-column> --%>
								<kendo:grid-column title="Expected&nbsp;Balance" field="expectedBalance" width="100px"/>
								<kendo:grid-column title="Available&nbsp;Balance" field="availableBalance" editor="availableBalanceEditor" width="100px"/>
								<kendo:grid-column title="Quantity&nbsp;Unavailable" field="unavailableBalance" width="100px"/>
								<kendo:grid-column title="Notes" field="spiNotes" editor="textAreaEditor" width="100px" />
								<kendo:grid-column title="&nbsp;" width="60px">
									<kendo:grid-column-command>
										<kendo:grid-column-commandItem name="edit" />
									</kendo:grid-column-command>
								</kendo:grid-column>
					        </kendo:grid-columns>
							<kendo:dataSource requestEnd="onRequestEndStorePhysicalInventoryReport" requestStart="onRequestStartStorePhysicalInventoryReport">
								<kendo:dataSource-transport>
									<kendo:dataSource-transport-read url="${readStorePhysicalInventoryReportUrl}/#=spiId#" dataType="json" type="POST" contentType="application/json" />
									<kendo:dataSource-transport-update url="${modifyStorePhysicalInventoryReportUrl}/update" dataType="json" type="GET" contentType="application/json" />
								</kendo:dataSource-transport>
								<kendo:dataSource-schema>
									<kendo:dataSource-schema-model id="spirId">
										<kendo:dataSource-schema-model-fields>
											<kendo:dataSource-schema-model-field name="spirId" type="number"/>
											<kendo:dataSource-schema-model-field name="spiId" type="number"/> 
											<kendo:dataSource-schema-model-field name="storeName" type="string"/>
											<kendo:dataSource-schema-model-field name="imName" type="string"/>
											<kendo:dataSource-schema-model-field name="uom" type="string"/>
											<kendo:dataSource-schema-model-field name="expectedBalance" type="number"/>
											<kendo:dataSource-schema-model-field name="availableBalance" type="number"/>
											<kendo:dataSource-schema-model-field name="unavailableBalance" type="number"/>
											<kendo:dataSource-schema-model-field name="spiCondition" type="string" defaultValue="Under Review"/>
											<kendo:dataSource-schema-model-field name="spiNotes" type="string"/>
										</kendo:dataSource-schema-model-fields>
									</kendo:dataSource-schema-model>
								</kendo:dataSource-schema>
							</kendo:dataSource>
						</kendo:grid>
					 </div>
                </kendo:tabStrip-item-content>
            </kendo:tabStrip-item>
				
				
		    </kendo:tabStrip-items>
		</kendo:tabStrip>
    </kendo:grid-detailTemplate>
   
   <div id="alertsBox" title="Alert"></div>
    
<script>
$("#gridStorePhysicalInventory").on("click",".k-grid-storeInventoryTemplatesDetailsExport", function(e) {
	  window.open("./storeInventoryTemplate/storeInventoryTemplatesDetailsExport");
});	  

$("#gridStorePhysicalInventory").on("click",".k-grid-storeInventoryPdfTemplatesDetailsExport", function(e) {
	  window.open("./storeInventoryPdfTemplate/storeInventoryPdfTemplatesDetailsExport");
});
//-- ------------------------------------------ Store Physical Inventory Script  ------------------------------------------ -

var SelectedRowIdStorePhysicalInventory = 0;
var spiId = 0;
var spiStatus = "";
var categoryIds = "";

$("#gridStorePhysicalInventory").on("click", ".k-grid-add", function(e) { 
	
	securityCheckForActions("./inventory/storePhysicalInventory/createButton");
 	
	/*  if($("#gridStorePhysicalInventory").data("kendoGrid").dataSource.filter()){
			
   		//$("#grid").data("kendoGrid").dataSource.filter({});
   		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
			var grid = $("#gridStorePhysicalInventory").data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
       }  */  
});

function clearFilterStorePhysicalInventory()
{
	  $("form.k-filter-menu button[type='reset']").slice().trigger("click");
	  var gridStorePhysicalInventory = $("#gridStorePhysicalInventory").data("kendoGrid");
	  gridStorePhysicalInventory.dataSource.read();
	  gridStorePhysicalInventory.refresh();
}

function onChangeStorePhysicalInventory(arg) 
{
	 var gview = $("#gridStorePhysicalInventory").data("kendoGrid");
 	 var selectedItem = gview.dataItem(gview.select());
 	 SelectedRowIdStorePhysicalInventory = selectedItem.spiId;
 	 spiId = selectedItem.spiId;
 	 spiStatus = selectedItem.spiStatus;
 	 categoryIds = selectedItem.categoryIds;
 	 this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
     //alert("Selected: " + SelectedRowIdStorePhysicalInventory);
}

function storePhysicalInventoryEdit(e)
{
	$('div[data-container-for="categoryIds"]').remove();
	$('label[for="categoryIds"]').closest('.k-edit-label').remove();
	$('div[data-container-for="spiDt"]').remove();
	$('label[for="spiDt"]').closest('.k-edit-label').remove();
	$('div[data-container-for="surveyDt"]').remove();
	$('label[for="surveyDt"]').closest('.k-edit-label').remove();
	$('div[data-container-for="surveyCompleteDt"]').remove();
	$('label[for="surveyCompleteDt"]').closest('.k-edit-label').remove();
	$('label[for="spiStatus"]').closest('.k-edit-label').remove();
	$('div[data-container-for="spiStatus"]').remove();
	$(".k-edit-field").each(function () {
		$(this).find("#temPID").parent().remove();
   	});
	
	if (e.model.isNew()) 
    {
		$.ajax
		({
			type : "POST",
			url : "./common/checkIsStoreItemLedgerHasRecords/StorePhysicalInventory",
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
					var gridStorePhysicalInventory = $("#gridStorePhysicalInventory").data("kendoGrid");
					gridStorePhysicalInventory.cancelRow();
				}	
			}
		});
		
		$(".k-window-title").text("Add New Physical Inventory Details");
		$(".k-grid-update").text("Save");
    }
	else
	{
		securityCheckForActions("./inventory/storePhysicalInventory/updateButton");
		
		$(".k-window-title").text("Edit Physical Inventory Details");
		
		if (categoryIds != null) {
			var str = categoryIds.split(",");
			for (var i = 0; i < str.length; i++) {
				//alert('catCheck' + str[i] );
				$('input[class="catCheck' + str[i] + '"]').prop("checked", true);
			}
		}
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
		checked1 = $("#catCheck:checked");
		checkedValues1 = checked1.map(function(i) {
			return $(this).val();
		}).get();
		
		if(checked1.length==0){
			alert("Please Select Category");
			return false;
		}
		if (checked1.length) {
			var str1 = checkedValues1.join();
			var firstItem = $('#gridStorePhysicalInventory').data().kendoGrid.dataSource.data()[0];
		    firstItem.set('categoryIds', str1);	
		}
			
	});

}

function storePhysicalInventoryDataBound(e) {
	var data = this.dataSource.view(),
       row;
    for (var i = 0; i < data.length; i++) {
        row = this.tbody.children("tr[data-uid='" + data[i].uid + "']");
        
        var status = data[i].spiStatus;
        var spiId = data[i].spiId;
        if (status == 'Started' || status == 'Continued') {
			row.addClass("bgGreenColor");
			$('#btn2_' + spiId).show();
			row.find(".k-grid-edit").hide();
	        row.find(".k-grid-delete").hide();
		} else if (status == 'Completed') {
			row.addClass("bgBlueColor");
			$('#btn2_' + spiId).hide();
			$('.k-grid-Activate' + spiId).hide();
			row.find(".k-grid-edit").hide();
	        row.find(".k-grid-delete").hide();
		} else if (status == 'Suspended') {
			row.addClass("bgRedColor");
			$('#btn2_' + spiId).show();
			$('.k-grid-Activate' + spiId).hide();
			row.find(".k-grid-edit").hide();
	        row.find(".k-grid-delete").hide();
		} else if (status == 'Created') {
			$('#btn2_' + spiId).hide();
			row.find(".k-grid-edit").show();
	        row.find(".k-grid-delete").show();
		}
    }
}

function storePhysicalInventoryParse (response) {   
    $.each(response, function (idx, elem) {
        	elem.spiDt = kendo.parseDate(new Date(elem.spiDt),'dd/MM/yyyy HH:mm');
        	if(elem.surveyDt != null)
        	{
        		elem.surveyDt = kendo.parseDate(new Date(elem.surveyDt),'dd/MM/yyyy HH:mm');	
        	}
        	if(elem.surveyCompleteDt != null)
        	{
        		elem.surveyCompleteDt = kendo.parseDate(new Date(elem.surveyCompleteDt),'dd/MM/yyyy HH:mm');	
        	}
    });
    return response;
}

$("#gridStorePhysicalInventory").on("click", "#temPID",	function(e) {
			var button = $(this), enable = button.text() == "Started";
			var text = "";
			if(button.text() == "Start")
			{
				text = "Started";
			}
			else if(button.text() == "Suspend")
			{
				text = "Suspended";
			}
			else if(button.text() == "Continue")
			{
				text = "Continued";
			}
			else
			{
				text = "Completed";
			}	
			$.ajax({
						type : "POST",
						async : false,
						url : "./storePhysicalInventory/spiStatus/" + spiId + "/"
								+ text,
						success : function(response) {
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
						}
					});
			
			var grid = $("#gridStorePhysicalInventory").data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
		});
		
function secondBtn(a) {
	var text = "";
	if(a == "Start")
	{
		text = "Started";
	}
	else if(a == "Suspend")
	{
		text = "Suspended";
	}
	else if(a == "Continue")
	{
		text = "Continued";
	}
	else
	{
		text = "Completed";
	}	
	
	$.ajax({
		type : "POST",
		async : false,
		url : "./storePhysicalInventory/spiStatus/" + spiId + "/" + text,
		success : function(response) {
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
		}
	});
	
	var grid = $("#gridStorePhysicalInventory").data("kendoGrid");
	grid.dataSource.read();
	grid.refresh();
}

function categoryEditor(container, options) {

	$('<div required="required" name="Category" id="categoryId" style="max-height: 200px; overflow: auto; "></div>').appendTo(container).kendoTreeView(	{
						checkboxes: {
							checkChildren: true,
							template : " <input type='checkbox' class=catCheck#:item.storeCategoryId# id='catCheck' value=#: item.storeCategoryId#> "
						}, 
						dataTextField : "storeCategoryName",
						name : "treeview",
						loadOnDemand : false,
						dataSource : {
							transport : {
								read : {
									url : "${categoriesReadTreeStorePhysicalInventory}",
									contentType : "application/json",
									type : "GET"
								}
							},
							schema : {
								model : {
									id : "storeCategoryId",
									hasChildren : "hasChilds"
								}
							}
						}
					});
	$('<span class="k-invalid-msg" data-for="Category"></span>').appendTo(container);
}


function onRequestStartStorePhysicalInventory(e)
{
	$('.k-grid-update').hide();
    $('.k-edit-buttons')
            .append(
                    '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
    $('.k-grid-cancel').hide();
    
	/* var gridStorePhysicalInventory = $("#gridStorePhysicalInventory").data("kendoGrid");
	gridStorePhysicalInventory.cancelRow(); */
}

function onRequestEndStorePhysicalInventory(e) 
{
	displayMessage(e, "gridStorePhysicalInventory", "Store Physical Inventory");
}	

//-- ------------------------------------------ Store Physical Inventory Report Script  ------------------------------------------ -

var flag = "";
var maxBalance = 0;
function storePhysicalInventoryReportEdit(e)
{
	$('div[data-container-for="unavailableBalance"]').remove();
	$('label[for="unavailableBalance"]').closest('.k-edit-label').remove();
	$('div[data-container-for="storeName"]').remove();
	$('label[for="storeName"]').closest('.k-edit-label').remove();
	$('div[data-container-for="imName"]').remove();
	$('label[for="imName"]').closest('.k-edit-label').remove();
	$('div[data-container-for="uom"]').remove();
	$('label[for="uom"]').closest('.k-edit-label').remove();
	$('div[data-container-for="expectedBalance"]').remove();
	$('label[for="expectedBalance"]').closest('.k-edit-label').remove();
	
	if (e.model.isNew()) 
    {
		/* $(".k-window-title").text("Add New Physical Inventory Details");
		$(".k-grid-update").text("Save"); */
    }
	else
	{
		flag = e.model.spiCondition;
		maxBalance = e.model.expectedBalance;
		
		if((flag == "Available") || (flag == "Under Review"))
		{
			$('div[data-container-for="availableBalance"]').hide();
			$('label[for="availableBalance"]').closest('.k-edit-label').hide();
		}	
		else
		{
			$('div[data-container-for="availableBalance"]').show();
			$('label[for="availableBalance"]').closest('.k-edit-label').show();
		}	
		$(".k-window-title").text("Edit Physical Inventory Report");
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
		var numerictextbox = $("#availableBalanceId").data("kendoNumericTextBox");
		//numerictextbox.value(0);
		
		if(flag == "Under Review")
		{
			alert("Please change the condition");
			return false;
		}
		
		if((flag != "Available") && (numerictextbox.value() == 0))
		{
			alert("Available balance is required");
			return false;
		}	
		if(numerictextbox.value() == maxBalance)
		{
			alert("Please change the condition to 'Available'");
			return false;
		}	
		if(numerictextbox.value() > maxBalance)
		{
			alert("Available balance cannot excceed expected balance");
			return false;
		}	
	});

}

function storePhysicalInventoryReportDataBound(e) {
	var data = this.dataSource.view(),
       row;
    for (var i = 0; i < data.length; i++) {
        row = this.tbody.children("tr[data-uid='" + data[i].uid + "']");
        var status = spiStatus;
        
        if (status == 'Started' || status == 'Continued') {
			row.find(".k-grid-edit").show();
		} else{
			row.find(".k-grid-edit").hide();
		}
    }
}

function conditionEditor(container, options) 
	{
	$('<input name="Condition" id="conditionId" data-text-field="spiCondition" data-value-field="spiCondition" data-bind="value:' + options.field + '" required="true"/>')
	.appendTo(container).kendoDropDownList({
		autoBind : false,
		dataSource : {
			transport : {		
				read :  "${getConditionUrl}"
			}
		},
		change : function (e) {
			flag = this.value();
			var numerictextbox = $("#availableBalanceId").data("kendoNumericTextBox");
			numerictextbox.value(0);
	       if((flag != "Available") && (flag != "Under Review"))
	       {
	    	   	$('label[for="availableBalance"]').parent().show();
				$('div[data-container-for="availableBalance"]').show();
	       }
	       else
	       {
	    	   	$('div[data-container-for="availableBalance"]').hide();
	    		$('label[for="availableBalance"]').closest('.k-edit-label').hide();
	       }
	       
	    }	 
	});
	
	$('<span class="k-invalid-msg" data-for="Condition"></span>').appendTo(container);
}

function availableBalanceEditor(container, options) 
	{
	$('<input id="availableBalanceId" data-text-field="availableBalance" data-value-field="availableBalance" data-bind="value:' + options.field + '"/>')
	.appendTo(container).kendoNumericTextBox({
		min : 0
	});
}

function onRequestStartStorePhysicalInventoryReport(e)
{
	$('.k-grid-update').hide();
    $('.k-edit-buttons')
            .append(
                    '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
    $('.k-grid-cancel').hide();
    
	/* var grid = $('#gridStorePhysicalInventoryReport_' + SelectedRowIdStorePhysicalInventory).data("kendoGrid");
	if(grid != null)
	{
		grid.cancelRow();
	} */
}

function onRequestEndStorePhysicalInventoryReport(e) 
{
	displayMessage(e, "gridStorePhysicalInventoryReport_"+ SelectedRowIdStorePhysicalInventory, "Store Physical Inventory Report");
}

</script>
<style>

.bgGreenColor{
background: #B4C594
}

.bgBlueColor{
background: #82CDFF
}

.bgRedColor{
background: #FF8484
}
</style>