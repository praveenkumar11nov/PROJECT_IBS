<%@include file="/common/taglibs.jsp"%>
<script type="text/javascript"  src=" <c:url value='/resources/js/plugins/forms/jquery.tagsinput.min.js'/>"></script>

	<!--	Common Url		-->
<c:url value="/common/getAllChecks" var="allChecksUrl" />
<c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />

<c:url value="/asset/getAllAssetsForAll" var="readAssetUrl" />

<c:url value="/asset/readMaintainanceType" var="readMaintainanceTypeUrl" />
<c:url value="/asset/readJobType" var="readJobTypeUrl" />
<c:url value="/asset/readMaintDept" var="readMaintDeptUrl" />

<c:url value="/asset/maintenanceschedule/readjobcalender" var="readJobCalenderUrl" />


<c:url value="/asset/maintenanceschedule/read" var="readUrl" />
<c:url value="/asset/maintenanceschedule/create" var="createUrl" />
<c:url value="/asset/maintenanceschedule/update" var="updateUrl" />
<c:url value="/asset/maintenanceschedule/delete" var="destroyUrl" />
	
<!-- 	Asset Category & Location Url	 -->
<c:url value="/asset/cat/read" var="transportReadUrlCat" />
<c:url value="/asset/loc/read" var="transportReadUrlLoc" />

<kendo:grid name="gridAssetMaintenanceSchedule" resizable="true" pageable="true" sortable="true" scrollable="true" selectable="true" edit="assetMaintSchEvent" groupable="true" detailTemplate="amsTemplate" change="onAmsChange" dataBound="dataBoundAms" remove="removeMaintSch">
	<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10"></kendo:grid-pageable>
	<kendo:grid-filterable extra="false">
		<kendo:grid-filterable-operators>
			<kendo:grid-filterable-operators-string contains="Is Contains"  eq="Is equal to" />
		</kendo:grid-filterable-operators>
	</kendo:grid-filterable>
	<kendo:grid-editable mode="popup"/>
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Create New Schedule"/>
			<kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilter() title='Clear Filter'><span class='k-icon k-i-funnel-clear'></span>Clear Filter</a>"/>
		</kendo:grid-toolbar>
		<kendo:grid-columns>
			<kendo:grid-column title="&nbsp;" field="radioBtn" editor="radioEditor" width="110px" hidden="true" />
			<kendo:grid-column title="Select Category" field="assetTreeCat" width="110px" editor="catEditor" hidden="true" />
			<kendo:grid-column title="Select Location" field="assetTreeLoc" width="110px" editor="locEditor" hidden="true" />
			<kendo:grid-column title="Asset Name *" field="assetId" editor="assetEditor" width="130px" hidden="true" />
			<kendo:grid-column title="Asset Name" field="assetName" width="130px" ><kendo:grid-column-filterable><kendo:grid-column-filterable-ui><script>function assetNameFilter(element){element.kendoAutoComplete({	placeholder : "Enter full name",dataTextField : "assetName", dataValueField : "assetId", dataSource : {transport : {	read : "${readAssetUrl}"	}}});}</script></kendo:grid-column-filterable-ui></kendo:grid-column-filterable></kendo:grid-column>
			<kendo:grid-column title="Task Name *" field="taskName" width="110px" hidden="true"/>
			<kendo:grid-column title="Task Location" field="taskLocation" width="110px" hidden="true"/>
			<%-- <kendo:grid-column title="Task Group *" field="taskGroup" width="110px" /> --%>
			
			<kendo:grid-column title="Maintenance Type *" field="mtId" editor="maintenanceTypeEditor" width="120px" hidden="true"/>
			<kendo:grid-column title="Maintenence Type" field="maintainanceType" width="120px" ><kendo:grid-column-filterable><kendo:grid-column-filterable-ui><script>function assetNameFilter(element){element.kendoAutoComplete({	placeholder : "Enter full name",dataTextField : "maintainanceType", dataValueField : "mtId", dataSource : {transport : {	read : "${readMaintainanceTypeUrl}"	}}});}</script></kendo:grid-column-filterable-ui></kendo:grid-column-filterable></kendo:grid-column>
			
			<kendo:grid-column title="Job Type *" field="jtId" editor="jobTypeEditor" width="110px" hidden="true" />
			<kendo:grid-column title="Job Type" field="jtType" width="110px" ><kendo:grid-column-filterable><kendo:grid-column-filterable-ui><script>function assetNameFilter(element){element.kendoAutoComplete({	placeholder : "Enter full name",dataTextField : "jtType", dataValueField : "jtId", dataSource : {transport : {	read : "${readJobTypeUrl}"	}}});}</script></kendo:grid-column-filterable-ui></kendo:grid-column-filterable></kendo:grid-column>
					
			<kendo:grid-column title="Department Name *" field="mtDpIt" editor="maintDeptEditor" width="130px" hidden="true"/>
			<kendo:grid-column title="Department Name" field="mtDpName" width="130px" ><kendo:grid-column-filterable><kendo:grid-column-filterable-ui><script>function assetNameFilter(element){element.kendoAutoComplete({	placeholder : "Enter full name",dataTextField : "mtDpName", dataValueField : "mtDpIt", dataSource : {transport : {	read : "${readMaintDeptUrl}"	}}});}</script></kendo:grid-column-filterable-ui></kendo:grid-column-filterable></kendo:grid-column>
			
			<kendo:grid-column title="Task Description" field="taskDescription" width="120px" editor="taskDescriptionEditor" filterable="false" hidden="true"/>
			<kendo:grid-column title="Tools Required *" field="toolsRequired" width="120px" editor="toolsRequiredEditor" hidden="true"/>
			<kendo:grid-column title="Expected Result" field="expectedResult" width="120px" editor="exptResultEditor" hidden="true"/>
			<kendo:grid-column title="Task Best Time *" field="taskBestTime" width="110px" editor="dropDownChecksEditor"><kendo:grid-column-filterable><kendo:grid-column-filterable-ui><script>function taskBestTimeFilter(element) {element.kendoDropDownList({optionLabel: "Select",dataSource : {transport : {	read : "${filterDropDownUrl}/taskBestTime"	}}});}</script></kendo:grid-column-filterable-ui></kendo:grid-column-filterable></kendo:grid-column>
			<kendo:grid-column title="Expected Task Duration(Hrs)" field="expectedTaskDuration" width="180px" format="{0:n0}" hidden="true"/>
			<kendo:grid-column title="Task Frequency *" field="taskFrequency" width="110px" editor="dropDownChecksEditor"><kendo:grid-column-filterable><kendo:grid-column-filterable-ui><script>function taskFrequencyFilter(element) {element.kendoDropDownList({optionLabel: "Select",dataSource : {transport : {	read : "${filterDropDownUrl}/taskFrequency"	}}});}</script></kendo:grid-column-filterable-ui></kendo:grid-column-filterable></kendo:grid-column>
			<kendo:grid-column title="Task Start Date *" field="taskStartDate"  format="{0:dd/MM/yyyy}"  filterable="true" width="110px" >
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
			<kendo:grid-column title="Task End Date *" field="taskEndDate"  format="{0:dd/MM/yyyy}" filterable="true" width="110px" >
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
			<kendo:grid-column title="Task Last Executed" field="taskLastExecuted" format="{0:dd/MM/yyyy}" width="130px" hidden="true"/>
			
			<kendo:grid-column title="Time From *" field="timeFrom" width="90px" editor="timeFromEditor" format="{0:HH:mm}"/>
			<kendo:grid-column title="Time To *" field="timeTo" width="80px"  editor="timeToEditor" format="{0:HH:mm}"/>
			<kendo:grid-column title=" " field="support" width="0px"/>
			
			<kendo:grid-column title="Status" field="amsStatus" width="80px" ><kendo:grid-column-filterable><kendo:grid-column-filterable-ui><script>function amsStatusFilter(element) {element.kendoDropDownList({optionLabel: "Select",dataSource : {transport : {	read : "${filterDropDownUrl}/amsStatus"	}}});}</script></kendo:grid-column-filterable-ui></kendo:grid-column-filterable></kendo:grid-column>
				
			<%-- <kendo:grid-column title=" "  template="<a href='\\\#' class='k-button' id='jobCal' onClick = 'createJobCalender(#=data.amsId#)'>Create Job Calender</a>" 	width="150px" />
			 --%>
			<kendo:grid-column  title="" template="<a href='\\\#' id='btn1_#=data.amsId#' class='k-button k-button-icontext btn-destroy k-grid-Activate#=data.amsId#' onClick=amsStatusBtn(this.text)>Approve</a>&nbsp;<a href='\\\#' id='btn2_#=data.amsId#' class='k-button k-btn2' onClick=amsStatusBtn(this.text)>Reject</a>"
				width="160px" />	
			
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
				<kendo:dataSource-schema-model id="amsId">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="amsId" type="number"/>
						<kendo:dataSource-schema-model-field name="assetId" type="number"><kendo:dataSource-schema-model-field-validation required="true" /></kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="mtId" type="number"><kendo:dataSource-schema-model-field-validation required="true" /></kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="taskName"><kendo:dataSource-schema-model-field-validation pattern="^.{0,45}$"/></kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="taskLocation" ><kendo:dataSource-schema-model-field-validation pattern="^.{0,45}$" /></kendo:dataSource-schema-model-field>
					<%-- 	<kendo:dataSource-schema-model-field name="taskGroup" ><kendo:dataSource-schema-model-field-validation pattern="^.{0,45}$" required="true"/></kendo:dataSource-schema-model-field>
						 --%><kendo:dataSource-schema-model-field name="taskType" ><kendo:dataSource-schema-model-field-validation required="true" /></kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="taskDescription"/>
						<kendo:dataSource-schema-model-field name="toolsRequired" />
						<kendo:dataSource-schema-model-field name="expectedResult" ><kendo:dataSource-schema-model-field-validation pattern="^.{0,45}$"/></kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="taskBestTime" />
						<kendo:dataSource-schema-model-field name="expectedTaskDuration" type="number"><kendo:dataSource-schema-model-field-validation min="1" max="24"/></kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="taskFrequency"/>
						<kendo:dataSource-schema-model-field name="taskStartDate" type="date" defaultValue=""><kendo:dataSource-schema-model-field-validation  /></kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="taskEndDate" type="date" defaultValue=""><kendo:dataSource-schema-model-field-validation  /></kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="taskLastExecuted" type="date"/>
						<kendo:dataSource-schema-model-field name="amsStatus"/>
						<kendo:dataSource-schema-model-field name="taskOverdueByDays" type="number"/>
						<kendo:dataSource-schema-model-field name="createdBy" />
						<kendo:dataSource-schema-model-field name="lastUpdatedBy" />					
						<kendo:dataSource-schema-model-field name="timeFrom" />
						<kendo:dataSource-schema-model-field name="timeTo" />				
						<kendo:dataSource-schema-model-field name="jtId" type="number"/>			
						<kendo:dataSource-schema-model-field name="mtDpIt" type="number"/>		
						<%-- <kendo:dataSource-schema-model-field name="tsDate"/>
						<kendo:dataSource-schema-model-field name="teDate"/> --%>
						
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
	</kendo:grid>
	<div id="alertsBox" title="Alert"></div>
	
	
	<kendo:grid-detailTemplate id="amsTemplate">
		<kendo:tabStrip name="tabStrip_#=amsId#">
			<kendo:tabStrip-items>
				<kendo:tabStrip-item text="Job Calender" selected="true">
					<kendo:tabStrip-item-content>
						<kendo:grid name="grid_#=amsId#" resizable="true" pageable="true" sortable="true" selectable="true" scrollable="true" 	groupable="true">
							<kendo:grid-columns>
								<kendo:grid-column title="Title" field="title" width="150px"  />
								<kendo:grid-column title="Job Number" field="jobNumber" width="100px"  />
								<kendo:grid-column title="Maintainence Type" field="jobGroup" width="100px" hidden="true" />
								<kendo:grid-column title="Starts On" field="start" width="100px"  format="{0:dd/MM/yyyy}"/>
								<kendo:grid-column title="Ends On" field="end" width="100px"  format="{0:dd/MM/yyyy}" />
							</kendo:grid-columns>
							<kendo:dataSource pageSize="5" >
								<kendo:dataSource-transport>
									<kendo:dataSource-transport-read url="${readJobCalenderUrl}/#=amsId#" dataType="json" type="POST" contentType="application/json" />
								</kendo:dataSource-transport>
								<kendo:dataSource-schema>
									<kendo:dataSource-schema-model id="jobCalenderId">
										<kendo:dataSource-schema-model-fields>
											<kendo:dataSource-schema-model-field name="jobCalenderId" />	
											<kendo:dataSource-schema-model-field name="title" />	
											<kendo:dataSource-schema-model-field name="jobNumber" />	
											<kendo:dataSource-schema-model-field name="jobGroup" />
											<kendo:dataSource-schema-model-field name="start" type="date"/>
											<kendo:dataSource-schema-model-field name="end" type="date"/>
											<kendo:dataSource-schema-model-field name="lastUpdatedBy"/>										
											<kendo:dataSource-schema-model-field name="lastUpdatedDate" type="date"/>								
										</kendo:dataSource-schema-model-fields>
									</kendo:dataSource-schema-model>
								</kendo:dataSource-schema>
							</kendo:dataSource>
						</kendo:grid>
					</kendo:tabStrip-item-content>
				</kendo:tabStrip-item>
				
				<kendo:tabStrip-item text="Asset Details">
				<kendo:tabStrip-item-content>
					<br>
					<h5>Asset Name: #=assetName#</h5>
					<br>
					<table>
						<tr>
							<td><b>Task Name : </b></td>
							<td>#=taskName#</td>
							<td><b>Task Location :</b></td>
							<td>#=taskLocation#</td>
						</tr>
						<tr>
							<td><b>Task Description : </b></td>
							<td>#=taskDescription#</td>
							<td><b>Tools Required :</b></td>
							<td>#=toolsRequired#</td>
						</tr>
						<tr>
							<td><b>Expected Result : </b></td>
							<td>#=expectedResult#</td>
							<td><b>Expected Task Duration(Hrs) :</b></td>
							<td>#=expectedTaskDuration#</td>
						</tr>
					</table>
					<br>
					<br>
				</kendo:tabStrip-item-content>
			</kendo:tabStrip-item>
				
			</kendo:tabStrip-items>
		</kendo:tabStrip>
	</kendo:grid-detailTemplate>
	
	

<script>

	var amsId = "";
	var tsDate = "";
	function onAmsChange(arg) {
		var gview = $("#gridAssetMaintenanceSchedule").data("kendoGrid");
		var selectedItem = gview.dataItem(gview.select());
		amsId = selectedItem.amsId;
		this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
	}


	function clearFilter(){
		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
		var gridAssetMaintenanceSchedule = $("#gridAssetMaintenanceSchedule").data("kendoGrid");
		gridAssetMaintenanceSchedule.dataSource.read();
		gridAssetMaintenanceSchedule.refresh();
	}

	function assetMaintSchEvent(e) {
		
		$('input[name=taskLocation]').prop("readonly","true");

		$('#tags_taskType').tagsInput({});
		$('#tags_toolsRequired').tagsInput({});
		$('div[data-container-for="assetTreeLoc"]').hide();
		$('label[for="assetTreeLoc"]').closest('.k-edit-label').hide();
		$('div[data-container-for="assetTreeCat"]').hide();
		$('label[for="support"]').closest('.k-edit-label').hide();
		$('div[data-container-for="support"]').hide();
		$('label[for="assetTreeCat"]').closest('.k-edit-label').hide();
		$('div[data-container-for="assetName"]').remove();
		$('label[for="assetName"]').closest('.k-edit-label').remove();
		$('div[data-container-for="maintainanceType"]').remove();
		$('label[for="maintainanceType"]').closest('.k-edit-label').remove();
		$('label[for="taskLastExecuted"]').closest('.k-edit-label').hide();
		$('div[data-container-for="taskLastExecuted"]').hide();
		$('div[data-container-for="amsStatus"]').remove();
		$('label[for="amsStatus"]').closest('.k-edit-label').remove();
		$('a[id^=btn1_]').remove();
		$('a[id^=btn2_]').remove();
		
		$('div[data-container-for="jtType"]').remove();
		$('label[for="jtType"]').closest('.k-edit-label').remove();
		
		$('div[data-container-for="mtDpName"]').remove();
		$('label[for="mtDpName"]').closest('.k-edit-label').remove();
		
	/* 	$(".k-edit-field").each(function () {
			$(this).find("#jobCal").parent().remove();
	   	}); */
		$(".k-edit-form-container").css({"width" : "650px"});
		$(".k-window").css({"top" : "150px"});
		$('.k-edit-label:nth-child(22n+1)').each(
			function(e) {$(this).nextAll(':lt(21)').addBack().wrapAll(
							'<div class="wrappers"/>');
		});
		/* $(".k-window").css({"position" : "fixed"});	 */
		if (e.model.isNew()) {
			securityCheckForActions("./asset/maintsch/createButton");
			$(".k-window-title").text("Add Maintenance Schedule");
			$(".k-grid-update").text("Save");
			
		} else {
			securityCheckForActions("./asset/maintsch/updateButton");
			$(".k-window-title").text("Edit  Maintenance Schedule information");
		}

		$(".k-grid-update").click(function () {
			/* alert($('input[name="taskStartDate"]').val());
			e.model.set("tsDate", $('input[name="taskStartDate"]').val());	 */
			e.model.set("taskLocation", $('input[name="taskLocation"]').val());
			e.model.set("taskType", $('input[name="taskType"]').val());		
			e.model.set("toolsRequired", $('input[name="toolsRequired"]').val());		
		});
		
		var timeFrom = $('input[name="timeFrom"]').kendoTimePicker().data("kendoTimePicker");
		var timeTo = $('input[name="timeTo"]').kendoTimePicker().data("kendoTimePicker");
		$('input[name="timeFrom"]').change(function() {
			
			timeTo.min($('input[name="timeFrom"]').val());
		});
		
		$('input[name="timeTo"]').change(function() {
			
			timeFrom.min($('input[name="timeTo"]').val());
		});
		
		
		var taskStartDate = $('input[name="taskStartDate"]').kendoDatePicker().data("kendoDatePicker");
		var taskEndDate = $('input[name="taskEndDate"]').kendoDatePicker().data("kendoDatePicker");
		
		$('input[name="taskStartDate"]').change(function() {
			
			taskEndDate.min($('input[name="taskStartDate"]').val());
		});
		
		$('input[name="taskEndDate"]').change(function() {
			taskStartDate.min($('input[name="taskEndDate"]').val());
		});
		
		$('input[name="taskStartDate"]').keyup(function() {
			$('input[name="taskStartDate"]').val("");
		});
		
		$('input[name="taskEndDate"]').keyup(function() {
			$('input[name="taskEndDate"]').val("");
		});
		
		$('input[name="timeFrom"]').keyup(function() {
			$('input[name="timeFrom"]').val("");
		});
		
		$('input[name="timeTo"]').keyup(function() {
			$('input[name="timeTo"]').val("");
		});
		
		/*	Date Validations*/
		/* var taskEndDate = $('input[name="taskEndDate"]').kendoDateTimePicker().data("kendoDatePicker");
		taskEndDate.min(e.model.get("taskStartDate"));
		var taskStartDate = $('input[name="taskStartDate"]').kendoDateTimePicker().data("kendoDatePicker");
		taskStartDate.max(e.model.get("taskEndDate"));
		$('input[name="taskStartDate"]').change(function() {
			taskEndDate.min($('input[name="taskStartDate"]').val());
		});
		$('input[name="taskEndDate"]').change(function() {
			taskStartDate.max($('input[name="taskEndDate"]').val());
		}); */
	}
	
	$("#gridAssetMaintenanceSchedule").on("click", ".k-grid-add", function(e) { 
	 	
		 if($("#gridAssetMaintenanceSchedule").data("kendoGrid").dataSource.filter()){
				
	   		//$("#grid").data("kendoGrid").dataSource.filter({});
	   		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
				var grid = $("#gridAssetMaintenanceSchedule").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
	       }   
	});
	
	function removeMaintSch(e){
		securityCheckForActions("./asset/maintsch/deleteButton");
		var conf = confirm("Are u sure want to delete this Maintenance Record?");
		if(!conf){
			$('#gridAssetMaintenanceSchedule').data('kendoGrid').dataSource.read();
			$('#gridAssetMaintenanceSchedule').data('kendoGrid').refresh();
			 throw new Error('deletion aborted');
		}
	}

	function createJobCalender(amsIdJC){
		$.ajax({
			type : "GET",
			url : "./asset/maintenanceschedule/ceatejobcalender/" + amsIdJC,
			success : function(response) {
				//alert("Back");
			}
		});
	}
	
	function assetEditor(container, options) {
		$('<input name="Asset Name" data-text-field="assetName" data-value-field="assetId" id="assetId" data-bind="value:' + options.field + '" required="true"/>')
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
								type : "GET"
							}
						}
					},
					select : function (e) {
						var dataItem = this.dataItem(e.item.index());
						$('input[name=taskLocation]').val(dataItem.assetLocation);
				    } 

				});
		$('<span class="k-invalid-msg" data-for="Asset Name"></span>')
				.appendTo(container);
	}
	
	function maintenanceTypeEditor(container, options) {
		$('<input name="Maintainence Type" data-text-field="maintainanceType" data-value-field="mtId" id="mtId" data-bind="value:' + options.field + '" required="true"/>')
				.appendTo(container).kendoDropDownList({
					optionLabel : {
						maintainanceType : "Select",
						mtId : "",
					},
					defaultValue : false,
					sortable : true,
					dataSource : {
						transport : {
							read : {
								url : "${readMaintainanceTypeUrl}",
								type : "GET"
							}
						}
					}
				});
		$('<span class="k-invalid-msg" data-for="Maintainence Type"></span>')
				.appendTo(container);
	}
	
	function maintDeptEditor(container, options) {
		$('<input name="Department Name" data-text-field="mtDpName" data-value-field="mtDpIt" id="mtDpIt" data-bind="value:' + options.field + '" required="true"/>')
				.appendTo(container).kendoDropDownList({
					optionLabel : {
						mtDpName : "Select",
						mtDpIt : "",
					},
					defaultValue : false,
					sortable : true,
					dataSource : {
						transport : {
							read : {
								url : "${readMaintDeptUrl}",
								type : "GET"
							}
						}
					}
				});
		$('<span class="k-invalid-msg" data-for="Department Name"></span>')
				.appendTo(container);
	}
	
	function jobTypeEditor(container, options) {
		$('<input name="Job Type" data-text-field="jtType" data-value-field="jtId" id="jtId" data-bind="value:' + options.field + '" required="true"/>')
				.appendTo(container).kendoDropDownList({
					optionLabel : {
						jtType : "Select",
						jtId : "",
					},
					defaultValue : false,
					sortable : true,
					dataSource : {
						transport : {
							read : {
								url : "${readJobTypeUrl}",
								type : "GET"
							}
						}
					}
				});
		$('<span class="k-invalid-msg" data-for="Job Type"></span>')
				.appendTo(container);
	}
	
	
	
	function dropDownChecksEditor(container, options) {
		var res = (container.selector).split("=");
		var attribute = res[1].substring(0, res[1].length - 1);

		$(
				'<select data-text-field="text" name="'+attribute+'" data-value-field="value" data-bind="value:' + options.field + '" name = "'+attribute+'" id = "'+attribute+'Id" required="true"/>')
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
		$('<span class="k-invalid-msg" data-for="'+attribute+'" ></span>')
		.appendTo(container);
	}

	 
	function toolsRequiredEditor(container, options) {
		$('<input id="tags_toolsRequired" class="k-edit-field" name=' + options.field + ' data-bind="value:' + options.field + '" required="true" style="width:120px" />')
					.appendTo(container);
	}
	 
	function taskDescriptionEditor(container, options) {
		$('<textarea maxlength="500" data-text-field="taskDescription" data-value-field="taskDescription" data-bind="value:' + options.field + '" style="width: 148px; height: 40px;"/>')
					.appendTo(container);
	}
	 
	function exptResultEditor(container, options) {
		$('<textarea maxlength="500" data-text-field="expectedResult" data-value-field="expectedResult" data-bind="value:' + options.field + '" style="width: 148px; height: 40px;"/>')
					.appendTo(container);
	}
	
	
	function radioEditor(container, options) {
		$('<input type="radio" name=' + options.field + ' value="cat" /> Category &nbsp;&nbsp;<input type="radio" name=' + options.field + ' value="loc"/> Location &nbsp;&nbsp;<input type="radio" name=' + options.field + ' value="all" checked="true"/> All <br>')
					.appendTo(container);
	}
	
	function dataBoundAms(e) {
		
		var grid = $("#gridAssetMaintenanceSchedule").data("kendoGrid");
		var data = this.dataSource.view();
		for (var i = 0; i < data.length; i++) {
			var amsStatus = data[i].amsStatus;
			var currentUid = data[i].uid;
		    var amsId = data[i].amsId;		
			if (amsStatus === 'Approved') {
				$('#btn1_' + amsId).hide();
				$('#btn2_' + amsId).hide();
				 var currenRow = grid.table.find("tr[data-uid='" + currentUid + "']");
		         var editButton = $(currenRow).find(".k-grid-edit");
		         editButton.hide();
		         var currenRow = grid.table.find("tr[data-uid='" + currentUid + "']");
		         var deleteButton = $(currenRow).find(".k-grid-delete");
		         deleteButton.hide();
			} else if (amsStatus === 'Rejected') {
				$('#btn1_' + amsId).hide();
				$('#btn2_' + amsId).hide();
				 var currenRow = grid.table.find("tr[data-uid='" + currentUid + "']");
		         var editButton = $(currenRow).find(".k-grid-edit");
		         editButton.hide();
		         var currenRow = grid.table.find("tr[data-uid='" + currentUid + "']");
		         var deleteButton = $(currenRow).find(".k-grid-delete");
		         deleteButton.hide();
			}			
		}	
	}
	

	function amsStatusBtn(a) {

		securityCheckForActions("./asset/maintsch/approverejectButton");

		var conf = confirm(a + " the maintain schedule?");

		if (conf) {
			if (a == 'Approve')
				a = 'Approved';
			else
				a = 'Rejected';
			$
					.ajax({
						type : "POST",
						url : "./asset/assetmaintenanceschedule/updatestatus/"
								+ amsId + "/" + a,
						dataType:"text",
						success : function(response) {
							
							/* $("#alertsBox").html("");
							$("#alertsBox").html("Asset Schedule is"+response);
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
								$('#gridAssetMaintenanceSchedule').data(
										'kendoGrid').dataSource.read();
							} */
							
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
							$('#gridAssetMaintenanceSchedule').data(
							'kendoGrid').dataSource.read();
						}
					});
		}
	}

	$(document).on('change', 'input[name="radioBtn"]:radio', function() {

		var radioValue = $('input[name=radioBtn]:checked').val();

		if (radioValue == 'cat') {
			$('div[data-container-for="assetTreeCat"]').show();
			$('label[for="assetTreeCat"]').closest('.k-edit-label').show();
			$('div[data-container-for="assetTreeLoc"]').hide();
			$('label[for="assetTreeLoc"]').closest('.k-edit-label').hide();
		} else if (radioValue == 'loc') {
			$('div[data-container-for="assetTreeLoc"]').show();
			$('label[for="assetTreeLoc"]').closest('.k-edit-label').show();
			$('div[data-container-for="assetTreeCat"]').hide();
			$('label[for="assetTreeCat"]').closest('.k-edit-label').hide();
		} else if (radioValue == 'all') {
			$('div[data-container-for="assetTreeLoc"]').hide();
			$('label[for="assetTreeLoc"]').closest('.k-edit-label').hide();
			$('div[data-container-for="assetTreeCat"]').hide();
			$('label[for="assetTreeCat"]').closest('.k-edit-label').hide();
			dropDownDataSource = new kendo.data.DataSource({
				transport : {
					read : {
						url : "./asset/getAllAssetsForAll",
						dataType : "json",
						type : 'GET'
					}
				}
			});
			$("#assetId").kendoDropDownList({
				dataSource : dropDownDataSource,
				dataTextField : "assetName",
				dataValueField : "assetId",
				optionLabel : {
					assetName : "Select",
					assetId : "",
				},
			});
		}
	});

	function catEditor(container, options) {

		$('<div style="max-height: 100px; overflow: auto; "></div>')
				.appendTo(container)
				.kendoTreeView(
						{

							dataTextField : "assetcatText",
							template : " #: item.assetcatText # <input type='hidden' id='hiddenId' class='#: item.assetcatText ##: item.assetcatId#' value='#: item.assetcatId#'/>",
							select : onCatSelect,
							name : "treeview",
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

		$('<div style="max-height: 100px; overflow: auto; "></div>')
				.appendTo(container)
				.kendoTreeView(
						{

							dataTextField : "assetlocText",
							template : " #: item.assetlocText # <input type='hidden' id='hiddenId' class='#: item.assetlocText ##: item.assetlocId#' value='#: item.assetlocId#'/>",
							select : onLocSelect,
							name : "treeview2",
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

		dropDownDataSource = new kendo.data.DataSource({
			transport : {
				read : {
					url : "./asset/getAllAssetsOnCatId/" + nodeid, // url to remote data source 
					dataType : "json",
					type : 'GET'
				}
			}

		});
		$("#assetId").kendoDropDownList({
			dataSource : dropDownDataSource,
			dataTextField : "assetName",
			dataValueField : "assetId",
			optionLabel : {
				assetName : "Select",
				assetId : "",
			},
		});
	}

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

		dropDownDataSource = new kendo.data.DataSource({
			transport : {
				read : {
					url : "./asset/getAllAssetsOnLocId/" + nodeid, // url to remote data source 
					dataType : "json",
					type : 'GET'
				}
			}

		});
		$("#assetId").kendoDropDownList({
			dataSource : dropDownDataSource,
			dataTextField : "assetName",
			dataValueField : "assetId",
			optionLabel : {
				assetName : "Select",
				assetId : "",
			},
		});
	}

	function dateEditor(container, options) {
		$('<input required="true" name="' + options.field + '"/>').appendTo(
				container).kendoDateTimePicker({
			format : "dd/MM/yyyy hh:mm tt",
			timeFormat : "hh:mm tt"

		});
		$('<span class="k-invalid-msg" data-for="'+options.field+'" ></span>')
				.appendTo(container);
	}

	function onRequestEnd(a) {
		if (a.type == 'create') {
			alert("Added Successfully");
			$('#gridAssetMaintenanceSchedule').data('kendoGrid').dataSource
					.read();
			$('#gridAssetMaintenanceSchedule').data('kendoGrid').refresh();
		} else if (a.type == 'update') {
			alert("Updated Successfully");
			$('#gridAssetMaintenanceSchedule').data('kendoGrid').dataSource
					.read();
			$('#gridAssetMaintenanceSchedule').data('kendoGrid').refresh();
		} else if (a.type == 'destroy') {
			alert("Deleted Successfully");
		}
	}

	function onRequestStart(e) {
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();
        
		/* var gridAssetMaintenanceSchedule = $('#gridAssetMaintenanceSchedule')
				.data("kendoGrid");
		if (gridAssetMaintenanceSchedule != null) {
			gridAssetMaintenanceSchedule.cancelRow();
		} */
	}

	function timeFromEditor(container, options) {
		$(
				'<input required="true" name="Time From" data-text-field="' + options.field + '" id="timepicker" data-value-field="' + options.field + '" data-bind="value:' + 
							    		options.field + '" data-format="' + ["HH:mm:ss"] + '"/>')
				.appendTo(container).kendoTimePicker({
					name : (options.field),
					Value : ("$now")

				});
		$('<span class="k-invalid-msg" data-for="Time From" ></span>')
				.appendTo(container);
	}
	
	function timeToEditor(container, options) {
		$(
				'<input required="true" name="Time To" data-text-field="' + options.field + '" id="timepicker" data-value-field="' + options.field + '" data-bind="value:' + 
							    		options.field + '" data-format="' + ["HH:mm:ss"] + '"/>')
				.appendTo(container).kendoTimePicker({
					name : (options.field),
					Value : ("$now")

				});
		$('<span class="k-invalid-msg" data-for="Time To" ></span>')
				.appendTo(container);
	}
	
	(function($, kendo) {
		$
				.extend(
						true,
						kendo.ui.validator,
						{
							rules : { // custom rules
								
				                 maintDateFromEmpty : function(input, params){
				                      if (input.attr("name") == "taskStartDate")
				                      {
				                       return $.trim(input.val()) !== "";
				                      }
				                      return true;
				                 },
				                 maintDateToEmpty : function(input, params){
				                      if (input.attr("name") == "taskEndDate")
				                      {
				                       return $.trim(input.val()) !== "";
				                      }
				                      return true;
				                 }
				                 ,
				                 taskNameEmpty : function(input, params){
				                      if (input.attr("name") == "taskName")
				                      {
				                       return $.trim(input.val()) !== "";
				                      }
				                      return true;
				                 }
				              
							},
							messages : {
								maintDateFromEmpty : "Start Date is Required",
								maintDateToEmpty : "End Date is Required",
								taskNameEmpty : "Task Name is Required"
							}
						});
	})(jQuery, kendo);
	
</script>
<style>
.k-edit-form-container {
	/* width: 550px; */
	text-align: center;
	position: relative;
}
.wrappers {
	display: inline;
	float: left;
	width: 320px;
	padding-top: 10px;
}
.k-edit-form-container {
    text-align: left;
}
.k-window{
z-index: 10px;
}

.k-datepicker span{
	width: 65%
}


.k-timepicker span{
	width: 65%
}

div.tagsinput span.tag {
    background: none repeat scroll 0 0 grey;
    color: white;
    font-size: 12px;
    line-height: 20px;
    border: 0;

}

div.tagsinput input {
    background: none repeat scroll 0 0 rgba(0, 0, 0, 0);
    border: medium none;
    margin: 0;
    padding: 0;
    height: 28px;
}


div.tagsinput span.tag a {
    color: black;
    float: right;
    font-size: 11px;
    font-weight: bold;
}

div.tagsinput {
    background: none repeat scroll 0 0 #FDFDFD;
    border: 1px solid #DDDDDD;
    box-sizing: border-box;
    overflow-y: auto;
    padding: 0;
    width: 78%;
     border-radius: 6px;
}

.k-tabstrip{
	width: 1150px
}

.k-header{
	background: white
}

</style>