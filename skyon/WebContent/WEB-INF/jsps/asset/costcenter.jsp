<%@include file="/common/taglibs.jsp"%>

<c:url value="/asset/getAllAssetsForAll" var="readAssetUrl" />

<c:url value="/asset/costcenter/read" var="readUrl" />
<c:url value="/asset/costcenter/create" var="createUrl" />
<c:url value="/asset/costcenter/update" var="updateUrl" />
<c:url value="/asset/costcenter/delete" var="destroyUrl" />
<c:url value="/asset/costcenter/filter" var="commonFilterForCostCenterUrl" />

<!-- 	Asset Category & Location Url	 -->
<c:url value="/asset/cat/read" var="transportReadUrlCat" />
<c:url value="/asset/loc/read" var="transportReadUrlLoc" />

<kendo:grid name="gridCC" resizable="true" pageable="true"	sortable="true" scrollable="true" selectable="true"	edit="CCEvent" groupable="true" change="changeCC" remove="removeCC"> 
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10"></kendo:grid-pageable>
		<kendo:grid-filterable extra="false"><kendo:grid-filterable-operators><kendo:grid-filterable-operators-string eq="Is equal to" /></kendo:grid-filterable-operators></kendo:grid-filterable>
		<kendo:grid-editable mode="popup"/>
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Cost Center"/>
			<kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilter() title='Clear Filter'><span class='k-icon k-i-funnel-clear'></span>Clear Filter</a>"/>
		</kendo:grid-toolbar>
		<kendo:grid-columns>
			<kendo:grid-column title="Cost Center*" field="name" width="100px" >
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function apCodeFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Cost Center",
									dataSource : {
										transport : {
											read : "${commonFilterForCostCenterUrl}/name"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			<kendo:grid-column title="Cost Center Description" field="description" editor="costCenterDescEditor"  width="100px" filterable="false"/>
			<kendo:grid-column title="&nbsp;" width="160px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit" />
					<kendo:grid-column-commandItem name="destroy" />
				</kendo:grid-column-command>
			</kendo:grid-column>
		</kendo:grid-columns>
		<kendo:dataSource pageSize="20" requestEnd="onRequestEnd" requestStart="onRequestStart">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-create url="${createUrl}" dataType="json" type="GET" contentType="application/json" />
				<kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-update url="${updateUrl}" dataType="json" type="GET" contentType="application/json" />
				<kendo:dataSource-transport-destroy url="${destroyUrl}/"	dataType="json" type="GET" contentType="application/json" />
			</kendo:dataSource-transport>
			<kendo:dataSource-schema>
				<kendo:dataSource-schema-model id="ccId">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="ccId" type="number" />
						<kendo:dataSource-schema-model-field name="name"><kendo:dataSource-schema-model-field-validation required="true" /></kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="description"/>
						<kendo:dataSource-schema-model-field name="createdBy"/>
						<kendo:dataSource-schema-model-field name="updatedBy"/>
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
</kendo:grid>
<script>
    
    var nodeid = "";
    var dropDownDataSource = "";
    
    function clearFilter(){
		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
		var gridCC = $("#gridCC").data("kendoGrid");
		gridCC.dataSource.read();
		gridCC.refresh();
	}
   
    function changeCC(arg) {
		var gview = $("#gridCC").data("kendoGrid");
		var selectedItem = gview.dataItem(gview.select());
		aoId = selectedItem.aoId;
		assetId = selectedItem.assetId;
		this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
	}
    
    

	function CCEvent(e) {

		$('div[data-container-for="assetTreeLoc"]').hide();
		$('label[for="assetTreeLoc"]').closest('.k-edit-label').hide();
		$('div[data-container-for="assetTreeCat"]').hide();
		$('label[for="assetTreeCat"]').closest('.k-edit-label').hide();
		
		$('div[data-container-for="assetName"]').remove();
		$('label[for="assetName"]').closest('.k-edit-label').remove();
		/* $('div[data-container-for="assetTree"]').hide();
		$('label[for="assetTree"]').closest('.k-edit-label').hide(); */
		if (e.model.isNew()) {
			$(".k-window-title").text("Add Cost Center");
			$(".k-grid-update").text("Save");		
		} else {
			$(".k-window-title").text("Edit Cost Center Information");
		}
	}
	
	
	$("#gridCC").on("click", ".k-grid-add", function(e) { 
	 	
		 if($("#gridCC").data("kendoGrid").dataSource.filter()){
				
	   		//$("#grid").data("kendoGrid").dataSource.filter({});
	   		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
				var grid = $("#gridCC").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
	       }   
	});
	
	function removeCC(e){
		var conf = confirm("Are u sure want to delete this Cost Center Record?");
		if(!conf){
			$('#gridCC').data('kendoGrid').dataSource.read();
			$('#gridCC').data('kendoGrid').refresh();
			 throw new Error('deletion aborted');
		}
	}

	 
			function onRequestEnd(a) {
				if (a.type == 'create') {
					alert("Added Successfully");
					$('#gridCC').data('kendoGrid').dataSource.read();
					$('#gridCC').data('kendoGrid').refresh();
				} else if (a.type == 'update') {
					alert("Updated Successfully");
					$('#gridCC').data('kendoGrid').dataSource.read();
					$('#gridCC').data('kendoGrid').refresh();
				} else if(a.type == 'destroy'){
					alert("Deleted Successfully");
				}
			}
			
			function onRequestStart(e) {
				$('.k-grid-update').hide();
		        $('.k-edit-buttons')
		                .append(
		                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
		        $('.k-grid-cancel').hide();
		        
				/* var gridCC = $('#gridCC').data("kendoGrid");
				if(gridCC != null)
				{
					gridCC.cancelRow();
				} */
			} 
			
			function costCenterDescEditor(container, options) {
				$(
						'<textarea maxlength="500" data-text-field="desciption" data-value-field="description" data-bind="value:' + options.field + '" style="width: 148px; height: 40px;"/>')
						.appendTo(container);
			}
	 
</script>

<style>
.k-datepicker span{
	width: 53%
}

.k-header{
	background: white
}
</style>