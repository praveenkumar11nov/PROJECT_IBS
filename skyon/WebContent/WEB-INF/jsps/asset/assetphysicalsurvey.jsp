<%@include file="/common/taglibs.jsp"%>

<c:url value="/asset/read" var="readAssetUrl" />
	
<!--	Common Url		-->
<c:url value="/common/getAllChecks" var="allChecksUrl" />
<c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />
<c:url value="/asset/physicalsurvey/read" var="readUrl" />
<c:url value="/asset/physicalsurvey/create" var="createUrl" />
<c:url value="/asset/physicalsurvey/update" var="updateUrl" />
<c:url value="/asset/physicalsurvey/delete" var="destroyUrl" />

<!-- 	Asset Category & Location Url	 -->
<c:url value="/asset/cat/read" var="transportReadUrlCat" />
<c:url value="/asset/loc/read" var="transportReadUrlLoc" />

<c:url value="/asset/physicalsurveyreport/read" var="readAssetPhySurUrl" />
<c:url value="/asset/physicalsurveyreport/create" var="createAssetPhySurUrl" />
<c:url value="/asset/physicalsurveyreport/update" var="updateAssetPhySurUrl" />

<kendo:grid name="gridAssetPhysicalSurvey" resizable="true" pageable="true" detailTemplate="assetPhySurveyTemplate"	sortable="true" scrollable="true" selectable="true" change="onChange" dataBound="dataBoundPS" edit="assetPhysicalSurveyEvent" groupable="true" remove="removePhysur">
	<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10"></kendo:grid-pageable>
	<kendo:grid-filterable extra="false"><kendo:grid-filterable-operators><kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains" /></kendo:grid-filterable-operators></kendo:grid-filterable>
		<kendo:grid-editable mode="popup" />
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Physical Survey Details"/>
			<kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilter() title='Clear Filter'><span class='k-icon k-i-funnel-clear'></span>Clear Filter</a>"/>
			<kendo:grid-toolbarItem template="&nbsp;&nbsp;&nbsp;<span class='bgGreenColor' style='width:60px; height :20px'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;: Started"/>
			<kendo:grid-toolbarItem template="&nbsp;&nbsp;&nbsp;<span class='bgRedColor' style='width:60px; height :20px'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;: Suspended"/>
			<kendo:grid-toolbarItem template="&nbsp;&nbsp;&nbsp;<span class='bgGreenColor' style='width:60px; height :20px'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;: Continued"/>
			<kendo:grid-toolbarItem template="&nbsp;&nbsp;&nbsp;<span class='bgBlueColor' style='width:60px; height :20px'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;: Completed"/>
			
		</kendo:grid-toolbar>
		<kendo:grid-columns>
			<kendo:grid-column title="Survey Created Date" field="apsmDate" format="{0:dd/MM/yyyy}" width="140px" >
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
						function fromDateFilter(element) {
							element.kendoDatePicker({
								format : "dd/MM/yyyy",

							});
						}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			<kendo:grid-column title="Survey Start Date" field="surveyDate" format="{0:dd/MM/yyyy}" width="120px">
				<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function fromDateFilter(element) {
							element.kendoDatePicker({
								format : "dd/MM/yyyy",

							});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
			</kendo:grid-column>
			<kendo:grid-column title="Survey Name *" field="surveyName" width="110px" />
			<kendo:grid-column title="Survey Description *" field="surveyDescription" width="130px" editor="assetPSDescEditor" filterable="false"/>	
			<kendo:grid-column title="Survey Completed Date" format="{0:dd/MM/yyyy}"	field="surveyCompleteDate"  width="160px" >
				<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function fromDateFilter(element) {
							element.kendoDatePicker({
								format : "dd/MM/yyyy",

							});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
			</kendo:grid-column>		
			<kendo:grid-column title="Category Id" field="assetCatId" width="110px" hidden="true"/>
			<kendo:grid-column title="Category *" field="category" hidden="true" width="100px" editor="catEditor"/>
			<kendo:grid-column title="Category *" field="assetCatHierarchy" hidden="true" width="110px" />		
			<kendo:grid-column title="Location Id" field="assetLocId" width="110px" hidden="true"/>
			<kendo:grid-column title="Location *" hidden="true" field="location" width="100px" editor="locEditor"/>
			<kendo:grid-column title="Location *" field="assetLocHierarchy" hidden="true" width="110px" />
			<kendo:grid-column title="Location Bunch" field="assetLocIds" width="110px" hidden="true"/>
			<kendo:grid-column title="Category Bunch" field="assetCatIds" width="110px" hidden="true"/>	
			<kendo:grid-column title="Status" filterable="false" field="physicalSurveyStatus"	width="110px" /> 		
			<kendo:grid-column title=""
				template="<a href='\\\#' onClick=firstBtn(this.text) id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Activate#=data.apsmId#'>#= data.physicalSurveyStatus == 'Created' ? 'Start' : data.physicalSurveyStatus == 'Started' ? 'Complete' : data.physicalSurveyStatus == 'Completed' ? 'Complete' : 'Complete' #</a>&nbsp;<a href='\\\#' id='btn2_#=data.apsmId#' class='k-button k-btn2' style='display:none' onClick=secondBtn(this.text)>#= data.physicalSurveyStatus == 'Started' ? 'Suspend' : data.physicalSurveyStatus == 'Suspended' ? 'Continue' : data.physicalSurveyStatus == 'Continued' ? 'Suspend' : 'Continue' #</a>"
				width="200px" />			
			<kendo:grid-column title="&nbsp;" width="200px">
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
				<kendo:dataSource-schema-model id="apsmId">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="apsmId" type="number"/>
						<kendo:dataSource-schema-model-field name="apsmDate" type="date"><kendo:dataSource-schema-model-field-validation required="true"/></kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="surveyDate" type="date" defaultValue=""><kendo:dataSource-schema-model-field-validation required="true" /></kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="surveyName"><kendo:dataSource-schema-model-field-validation required="true" pattern="^.{0,45}$"/></kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="surveyDescription" />
						<kendo:dataSource-schema-model-field name="surveyCompleteDate" defaultValue="" type="date"><kendo:dataSource-schema-model-field-validation required="true"/></kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="physicalSurveyStatus" type="string"/>
						<kendo:dataSource-schema-model-field name="createdBy"/>
						<kendo:dataSource-schema-model-field name="updatedBy"/>
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
	</kendo:grid>
	<kendo:grid-detailTemplate id="assetPhySurveyTemplate">
		<kendo:tabStrip name="tabStrip_#=apsmId#">
			<kendo:tabStrip-items>	
				<kendo:tabStrip-item text="Asset Physical Survey" selected="true">
					<kendo:tabStrip-item-content>
						<kendo:grid name="gridAssetPhySurvey_#=apsmId#" resizable="true" pageable="true" sortable="true" selectable="true" scrollable="true" edit="assetPhysicalSurveyReportEvent" groupable="true" dataBound="dataBoundPSR" >
							<kendo:grid-editable mode="inline" confirmation="Are you sure you want to remove this report?"/>					
							<kendo:grid-columns>
								<%-- <kendo:grid-column title="&nbsp;" field="radioBtn"	editor="radioEditor" width="110px" hidden="true" />
								<kendo:grid-column title="Select " field="assetTree" width="110px"	hidden="true" /> --%>
								<kendo:grid-column title="Asset Name" field="assetId"   width="100px" hidden="true"/>
								<kendo:grid-column title="Asset Name" field="assetName" width="100px" /> 
								<kendo:grid-column title="Category " field="assetCategoryTree" width="100px" /> 
								<kendo:grid-column title="Location " field="assetLocationTree" width="100px" /> 
								<kendo:grid-column title="Asset Condition" field="assetCondition" width="100px" editor="dropDownChecksEditor"/> 
								<kendo:grid-column title="Asset Notes" field="assetNotes" width="100px" />							
								<kendo:grid-column title="&nbsp;" width="160px">
									<kendo:grid-column-command>
										<kendo:grid-column-commandItem name="edit" />
									</kendo:grid-column-command>
								</kendo:grid-column>
							</kendo:grid-columns>
							<kendo:dataSource pageSize="5" requestEnd="onRequestEndChild" requestStart="onRequestStartChild">
								<kendo:dataSource-transport>
									<kendo:dataSource-transport-create	url="${createAssetPhySurUrl}/#=apsmId#" dataType="json" type="GET"	contentType="application/json" />
									<kendo:dataSource-transport-read url="${readAssetPhySurUrl}/#=apsmId#" dataType="json" type="POST" contentType="application/json" />
									<kendo:dataSource-transport-update url="${updateAssetPhySurUrl}/#=apsmId#" dataType="json" type="GET" contentType="application/json" />
									<kendo:dataSource-transport-destroy url="${deleteAssetSpareUrl}" dataType="json" type="GET" contentType="application/json" />
								</kendo:dataSource-transport>
								<kendo:dataSource-schema>
									<kendo:dataSource-schema-model id="apId">
										<kendo:dataSource-schema-model-fields>
											<kendo:dataSource-schema-model-field name="apId" type="number" editable="false"/>
											<kendo:dataSource-schema-model-field name="apsmId" type="number"><kendo:dataSource-schema-model-field-validation required="true" /></kendo:dataSource-schema-model-field>
											<kendo:dataSource-schema-model-field name="assetId" type="number"/>
											<kendo:dataSource-schema-model-field name="assetName" editable="false"/>
											<kendo:dataSource-schema-model-field name="assetCategoryTree" editable="false"/>
											<kendo:dataSource-schema-model-field name="assetLocationTree" editable="false"/>
											<kendo:dataSource-schema-model-field name="assetCondition" type="string"><kendo:dataSource-schema-model-field-validation required="true" /></kendo:dataSource-schema-model-field> 
											<kendo:dataSource-schema-model-field name="assetNotes" type="string"><kendo:dataSource-schema-model-field-validation required="true" pattern="^.{0,100}$"/></kendo:dataSource-schema-model-field>
											<kendo:dataSource-schema-model-field name="createdBy"/>
											<kendo:dataSource-schema-model-field name="lastUpdatedBy"/>
										</kendo:dataSource-schema-model-fields>
									</kendo:dataSource-schema-model>
								</kendo:dataSource-schema>
							</kendo:dataSource>
						</kendo:grid>
					</kendo:tabStrip-item-content>
				</kendo:tabStrip-item>
			</kendo:tabStrip-items>
		</kendo:tabStrip>
	</kendo:grid-detailTemplate>  
<script>

var apmsId = "";
var apStatus = "";
var catSelected = 0;
var locSelected = 0;
var apCatIds = "";
var apLocIds = "";
var apsmAddOrEdit = 0;
var checked, checkedValues = new Array();
var checked1, checkedValues1 = new Array();

function onChange(arg) {
	
	var gview = $("#gridAssetPhysicalSurvey").data("kendoGrid");
	var selectedItem = gview.dataItem(gview.select());
	apmsId = selectedItem.apsmId;
	apStatus = selectedItem.physicalSurveyStatus;
	apCatIds = selectedItem.assetCatIds;
	apLocIds = selectedItem.assetLocIds;
	this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
}

function clearFilter(){
	$("form.k-filter-menu button[type='reset']").slice().trigger("click");
	var gridAssetPhysicalSurvey = $("#gridAssetPhysicalSurvey").data("kendoGrid");
	gridAssetPhysicalSurvey.dataSource.read();
	gridAssetPhysicalSurvey.refresh();
}

function assetPhysicalSurveyEvent(e) {
		
/* 	
	
	 var treeview = $('div[ data-role="treeview1"]').kendoTreeView();
	 alert(treeview);
	 treeview.expand(".k-item");
	 */

/* 	$('input[value="2"]').remove();
	$('#btn2_'+apmsId).hide(); */
	
	$('label[for="assetCatId"]').closest('.k-edit-label').remove();
		$('div[data-container-for="assetCatId"]').remove();
		
		$('label[for="surveyCompleteDate"]').closest('.k-edit-label').remove();
		$('div[data-container-for="surveyCompleteDate"]').remove();
		
		$('label[for="surveyDate"]').closest('.k-edit-label').remove();
		$('div[data-container-for="surveyDate"]').remove();
		
		$('label[for="apsmDate"]').closest('.k-edit-label').remove();
		$('div[data-container-for="apsmDate"]').remove();

		$('label[for="assetCatIds"]').closest('.k-edit-label').remove();
		$('div[data-container-for="assetCatIds"]').remove();

		$('label[for="assetLocIds"]').closest('.k-edit-label').remove();
		$('div[data-container-for="assetLocIds"]').remove();

		$('label[for="assetLocId"]').closest('.k-edit-label').remove();
		$('div[data-container-for="assetLocId"]').remove();

		$('label[for="assetLocHierarchy"]').closest('.k-edit-label').remove();
		$('div[data-container-for="assetLocHierarchy"]').remove();

		$('label[for="assetCatHierarchy"]').closest('.k-edit-label').remove();
		$('div[data-container-for="assetCatHierarchy"]').remove();

		/* $('input[name="category"]').remove();
		$('input[name="location"]').remove(); */

		/* $('#dialogBoxforCatTree')
				.appendTo('div[data-container-for="category"]');
		$('#dialogBoxforLocTree')
				.appendTo('div[data-container-for="location"]'); */
		
		$('label[for="physicalSurveyStatus"]').closest('.k-edit-label').remove();
		$('div[data-container-for="physicalSurveyStatus"]').remove();
 
		/* $('a[id="temPID"]').hide();
		$('a[id^="btn2_"]').hide();  */
		
		$(".k-edit-field").each(function () {
			$(this).find("#temPID").parent().remove();
	   	});
		
		if (e.model.isNew()) {

			securityCheckForActions("./asset/physurvey/createButton");
			 $('input:checkbox').removeAttr('checked');
			apsmAddOrEdit = 1;
			$(".k-window-title").text("Add Physical Survey");
			$(".k-grid-update").text("Save");

		} else {
			
			securityCheckForActions("./asset/physurvey/updateButton");
			apsmAddOrEdit = 2;
			if (apCatIds != null) {
				var str = apCatIds.split(",");
				for (var i = 0; i < str.length; i++) {
					$('input[class="catCheck' + str[i] + '"]').prop("checked", true);
				}
			}
			if (apLocIds != null) {
				var str = apLocIds.split(",");
				for (var i = 0; i < str.length; i++) {
					$('input[class="locCheck' + str[i] + '"]').prop("checked", true);
				}
			}
			$(".k-window-title").text("Edit Physical Survey details");
		}

		$(".k-grid-update").click(function () 
		{
			checked = $("#locCheck:checked");
			checkedValues = checked.map(function(i) {
						return $(this).val()
			}).get();
			if (checked.length) {
				var str = checkedValues.join();
				e.model.set("assetLocIds", str);	
				locSelected = 1;
			}
			else{
				e.model.set("assetLocIds", "");
			}
			checked1 = $("#catCheck:checked");
			checkedValues1 = checked1.map(function(i) {
						return $(this).val()
			}).get();
			
			if (checked1.length) {
				var str1 = checkedValues1.join();
				e.model.set("assetCatIds", str1);	
				catSelected = 1;
			}else{
				e.model.set("assetCatIds", "");	
			}	
			
			if(checked1.length == 0 && checked.length == 0){
						alert("Please Select Either Location Or Category");
						return false;
			}
		});
		/* $(".k-grid-cancel").click(function() {
			$('#gridAssetPhysicalSurvey').data('kendoGrid').dataSource.read();
		}); */
	}

$("#gridAssetPhysicalSurvey").on("click", ".k-grid-add", function(e) { 
 	
	 /* if($("#gridAssetPhysicalSurvey").data("kendoGrid").dataSource.filter()){
			
  		//$("#grid").data("kendoGrid").dataSource.filter({});
  		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
			var grid = $("#gridAssetPhysicalSurvey").data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
      } */   
});

	function removePhysur(){
		securityCheckForActions("./asset/physurvey/deleteButton");
		var conf = confirm("Are u sure want to delete this Survey Record?");
		if(!conf){
			$('#gridAssetPhysicalSurvey').data('kendoGrid').dataSource.read();
			$('#gridAssetPhysicalSurvey').data('kendoGrid').refresh();
			 throw new Error('deletion aborted');
		}
	}

	function dataBoundPSR(e) {
		if (apStatus === 'Started' || apStatus === 'Continued') {
			$(".k-grid-delete", "#gridAssetPhySurvey_" + apmsId).show();
			$(".k-grid-edit", "#gridAssetPhySurvey_" + apmsId).show();
		} else {
			$(".k-grid-delete", "#gridAssetPhySurvey_" + apmsId).hide();
			$(".k-grid-edit", "#gridAssetPhySurvey_" + apmsId).hide();
		}
	}

	function dataBoundPS(e) {
		var data = this.dataSource.view(),
	       row;
	    for (var i = 0; i < data.length; i++) {
	        row = this.tbody.children("tr[data-uid='" + data[i].uid + "']");
	        
	        var status = data[i].physicalSurveyStatus;
	        var apmsId = data[i].apsmId;
	        if (status == 'Started' || status == 'Continued') {
				row.addClass("bgGreenColor");
				$('#btn2_' + apmsId).show();
				row.find(".k-grid-edit").hide();
		        row.find(".k-grid-delete").hide();
			} else if (status == 'Completed') {
				row.addClass("bgBlueColor");
				$('#btn2_' + apmsId).hide();
				$('.k-grid-Activate' + apmsId).hide();
				row.find(".k-grid-edit").hide();
		        row.find(".k-grid-delete").hide();
			} else if (status == 'Suspended') {
				row.addClass("bgRedColor");
				$('#btn2_' + apmsId).show();
				$('.k-grid-Activate' + apmsId).hide();
				row.find(".k-grid-edit").hide();
		        row.find(".k-grid-delete").hide();
			} else if (status == 'Created') {
				$('#btn2_' + apmsId).hide();
				row.find(".k-grid-edit").show();
		        row.find(".k-grid-delete").show();
			}
	    }
	}

	function assetPhysicalSurveyReportEvent(e) {

		$('div[data-container-for="assetId"]').remove();
		$('label[for="assetId"]').closest('.k-edit-label').remove();

		$('div[data-container-for="assetTree"]').hide();
		$('label[for="assetTree"]').closest('.k-edit-label').hide();

		if (e.model.isNew()) {

			$(".k-window-title").text("Add Physical Survey Report");
			$(".k-grid-update").text("Save");

		} else {
			securityCheckForActions("./asset/physurveyreport/updateButton");
			$(".k-window-title").text("Edit Physical Survey Report details");
		}
	}

	function dropDownChecksEditor(container, options) {
		var res = (container.selector).split("=");
		var attribute = res[1].substring(0, res[1].length - 1);

		$('<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '" name = "'+attribute+'" id = "'+attribute+'Id" required="true"/>')
				.appendTo(container).kendoDropDownList({
					optionLabel : {
						text : "Select",
						value : "",
					},
					defaultValue : false,
					sortable : true,
					dataSource : {
						transport : {
							read : "${allChecksUrl}/" + attribute,
						}
					}
				});
		$('<span class="k-invalid-msg" data-for="'+attribute+'"></span>')
				.appendTo(container);
	}

	var nodeid = "";
	var dropDownDataSource = "";
	function onCatSelect(e) {

		nodeid = $('.k-state-hover').find('#hiddenId').val();
		var nn = $('.k-state-hover').html();
		var spli = nn.split(" <input");
		$('#editNodeText').val(spli[0].trim());

		var kitems = $(e.node).add(
				$(e.node).parentsUntil('.k-treeview', '.k-item'));
		texts = $.map(kitems, function(kitem) {
			return $(kitem).find('>div span.k-in').text();
		});

		var catId = $('#gridAssetPhysicalSurvey').data().kendoGrid.dataSource
				.data()[0];
		catId.set('assetCatId', nodeid);
		catSelected = 1;
		/* 
		$.getJSON("asset/read", function (data) {
		   
		}); */
		
		$.getJSON("./asset/getAllAssetsOnCatId/" + nodeid, function(response) {

			var contactList = "";
			if (response.length > 0) {
				for (var s = 0, len = response.length; s < len; ++s) {
					var contact = response[s];
					contactList = contactList + contact.assetName + "<br>";
				}
			}

			$(".k-in").kendoTooltip({
				content : contactList,
				// show: onShow,
                // hide: onHide,
                 position: "top"
				/* show : function() {
					$(this.popup.wrapper).css({
						top : lastMouseY,
						left : lastMouseX
					});
				}, */
				//showOn : "click"
			});
		});
	}

	$(document).on("mousemove", function(e) {
		lastMouseX = e.pageX;
		lastMouseY = e.pageY;
	});

	function onLocSelect(e) {
		nodeid = $('.k-state-hover').find('#hiddenId').val();
		var nn = $('.k-state-hover').html();
		var spli = nn.split(" <input");
		$('#editNodeText').val(spli[0].trim());

		var kitems = $(e.node).add(
				$(e.node).parentsUntil('.k-treeview', '.k-item'));
		texts = $.map(kitems, function(kitem) {
			return $(kitem).find('>div span.k-in').text();
		});

		var locId = $('#gridAssetPhysicalSurvey').data().kendoGrid.dataSource
				.data()[0];
		locId.set('assetLocId', nodeid);
		locSelected = 1;
	}

	function assetEditor(container, options) {
		$(
				'<input name="Asset Name" data-text-field="assetName" data-value-field="assetId" id="assetId" data-bind="value:' + options.field + '" required="true"/>')
				.appendTo(container).kendoDropDownList({
					optionLabel : {
						assetName : "Select",
						assetId : "",
					},
					defaultValue : false,
					sortable : true,
					dataSource : {
						transport : {
							read : {
								url : "${readAssetUrl}",
								type : "POST"
							}
						}
					}
				});
		$('<span class="k-invalid-msg" data-for="Asset Name"></span>')
				.appendTo(container);
	}


	function firstBtn(a) {

		securityCheckForActions("./asset/physurvey/statusButton");
		var conf = confirm(a + " the asset survey?");
		if (conf) {
			if (a == 'Start') {
				a = "Started";
			}
			if (a == 'Complete') {
				a = "Completed";
			}
			if (a == "Suspend") {
				a = "Suspended";
			}
			if (a == "Create") {
				a = "Created";
			}
			if (a == "Continue") {
				a = "Continued";
			}
			$.ajax({
						type : "POST",
						url : "./asset/physicalsurvey/" + apmsId + "/" + a,
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

							if (response.status === 'DENIED') {
								alert(response.result);
							} else if (response.status === 'cant') {
								alert(response.result);
							} else {

								$('#gridAssetPhysicalSurvey').data('kendoGrid').dataSource
										.read();
							}
						}
					});
		}
	}

	function secondBtn(a) {

		securityCheckForActions("./asset/physurvey/statusButton");

		var conf = confirm(a + " the asset survey?");

		if (conf) {

			if (a == 'Start') {
				a = "Started";
			}
			if (a == 'Complete') {
				a = "Completed";
			}
			if (a == "Suspend") {
				a = "Suspended";
			}
			if (a == "Create") {
				a = "Created";
			}
			if (a == "Continue") {
				a = "Continued";
			}
			$.ajax({
						type : "POST",
						url : "./asset/physicalsurvey/" + apmsId + "/" + a,
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

							if (response.status === 'DENIED') {
								alert(response.result);
							} else {
								$('#gridAssetPhysicalSurvey').data('kendoGrid').dataSource
										.read();
							}
						}
					});
		}
	}

	function onCatExpand(e) {
		if (apsmAddOrEdit == 1) {
			$('input[id="catCheck"]').prop('checked', false);
		} else {
			if (apCatIds != null) {
				var catstr = apCatIds.split(",");
				for (var i = 0; i < catstr.length; i++) {
					$('input[class="catCheck' + catstr[i] + '"]').prop(
							"checked", true);
				}
			}
		}
	}

	function onLocExpand(e) {
		if (apsmAddOrEdit == 1) {
			$('input[id="locCheck"]').prop('checked', false);
		} else {
			if (apLocIds != null) {
				var locstr = apLocIds.split(",");
				for (var i = 0; i < locstr.length; i++) {
					$('input[class="locCheck' + locstr[i] + '"]').prop(
							"checked", true);
				}
			}
		}
	}

	function catEditor(container, options) {

		$(
				'<div id="catDivId" style="max-height: 200px; overflow: auto; "></div>')
				.appendTo(container)
				.kendoTreeView(
						{
							checkboxes : {
								checkChildren : true,
								template : " <input type='checkbox' class=catCheck#:item.assetcatId# id='catCheck' value=#: item.assetcatId#> "
							},
							dataTextField : "assetcatText",
							template : "#: item.assetcatText # <input type='hidden' id='hiddenId' class='#: item.assetcatText ##: item.assetcatId#' value='#: item.assetcatId#'/>",
							// select : onCatSelect,
							name : "treeview1",
							loadOnDemand : false,
							expand : onCatExpand,
							dataSource : {
								transport : {
									read : {
										url : "${transportReadUrlCat}",
										contentType : "application/json",
										type : "GET"
									}
								},
								schema : {
									model : {
										id : "assetcatId",
										hasChildren : "hasChilds"
									}
								}
							}
						});
	}

	function locEditor(container, options) {

		$('<div style="max-height: 200px; overflow: auto;"></div>')
				.appendTo(container)
				.kendoTreeView(
						{
							checkboxes : {
								checkChildren : true,
								template : " <input type='checkbox' class=locCheck#:item.assetlocId# id='locCheck' value=#: item.assetlocId#> "
							},
							dataTextField : "assetlocText",
							template : "#: item.assetlocText # <input type='hidden' id='hiddenId' class='#: item.assetlocText ##: item.assetlocId#' value='#: item.assetlocId#'/>",
							/* select : onLocSelect, */
							name : "treeview2",
							loadOnDemand : false,
							expand : onLocExpand,
							dataSource : {
								transport : {
									read : {
										url : "${transportReadUrlLoc}",
										contentType : "application/json",
										type : "GET"
									}
								},
								schema : {
									model : {
										id : "assetlocId",
										hasChildren : "hasChilds"
									}
								}
							}
						});
	}

	function assetPSDescEditor(container, options) {
		$(
				'<textarea maxlength="500" data-text-field="surveyDescription" data-value-field="surveyDescription" data-bind="value:' + options.field + '" style="width: 148px; height: 40px;"/>')
				.appendTo(container);
	}

	function onRequestEnd(r) {
		if (r.type == 'create') {
			alert("Added Successfully");
			$('#gridAssetPhysicalSurvey').data('kendoGrid').dataSource.read();
			$('#gridAssetPhysicalSurvey').data('kendoGrid').refresh();
		} else if (r.type == 'update') {
			alert("Updated Successfully");
			$('#gridAssetPhysicalSurvey').data('kendoGrid').dataSource.read();
			$('#gridAssetPhysicalSurvey').data('kendoGrid').refresh();
		} else if (r.type == 'destroy') {
			alert("Deleted Successfully");
		}
	}

	function onRequestStart(e) {
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();
        
		/* var gridAssetPhysicalSurvey = $('#gridAssetPhysicalSurvey').data(
				"kendoGrid");
		if (gridAssetPhysicalSurvey != null) {
			gridAssetPhysicalSurvey.cancelRow();
		} */
	}

	function onRequestEndChild(r) {
		if (r.type == 'create') {
			alert("Added Successfully");
			$('#gridAssetPhySurvey_' + apmsId).data('kendoGrid').dataSource
					.read();
			$('#gridAssetPhySurvey_' + apmsId).data('kendoGrid').refresh();
		} else if (r.type == 'update') {
			alert("Updated Successfully");
			$('#gridAssetPhySurvey_' + apmsId).data('kendoGrid').dataSource
					.read();
			$('#gridAssetPhySurvey_' + apmsId).data('kendoGrid').refresh();
		} else if (r.type == 'destroy') {
			alert("Deleted Successfully");
		}
	}

	function onRequestStartChild(e) {
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();
        
		/* var res = (e.sender.options.transport.read.url).split("/");
		var assetphy = $('#gridAssetPhySurvey_' + res[res.length - 1]).data(
				"kendoGrid");
		if (assetphy != null) {
			assetphy.cancelRow();
		} */
	}
</script>
<style>



.bgBlueColor{
background: #3883b5
}

.bgGreenColor{
background: #428f4c
}

.bgRedColor{
background:  tomato
}
</style>