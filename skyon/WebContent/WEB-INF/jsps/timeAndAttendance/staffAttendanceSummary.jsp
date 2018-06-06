<%@include file="/common/taglibs.jsp"%>


<c:url value="/staffAttendanceSummary/read" var="readUrl" />
<c:url value="/viewAttendanceSummary/read" var="viewAttendanceSummary" />

<!--Filter URL  -->
<c:url value="/staffAttendanceSummary/getStaffNameList" var="getStaffNameListUrl" />
<c:url value="/staffAttendanceSummary/getDepartmentList" var="departmentFilterUrl" />
<c:url value="/staffAttendanceSummary/getDesignationList" var="designationFilterUrl" />
<c:url value="/staffAttendanceSummary/accessCardNoUrl" var="accessCardNoUrl" />


<kendo:grid name="attendanceGrid" pageable="true" resizable="true"
	sortable="true" reorderable="true" selectable="true" scrollable="true"
	filterable="true" groupable="true" detailTemplate="attendanceTemplate"
	change="onChangeAttendanceSummary">
	<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10"
		input="true" numeric="true"></kendo:grid-pageable>
	<kendo:grid-filterable extra="false">
		<kendo:grid-filterable-operators>
			<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains"/>
			<kendo:grid-filterable-operators-date gt="Is after" lt="Is before" />
			<kendo:grid-filterable-operators-number eq="Is equal to" gt="Is greather than" gte="IS greather than and equal to" lt="Is less than" lte="Is less than and equal to" neq="Is not equal to"/>
		</kendo:grid-filterable-operators>

	</kendo:grid-filterable>
	<kendo:grid-editable mode="popup" />
	<kendo:grid-toolbar>
		<kendo:grid-toolbarItem
			template="<input id='selectByMonth' style='width:162px'/>" />
		<kendo:grid-toolbarItem
			template="<a class='k-button' href='\\#' onclick=searchByMonth() title='Search'>Search</a>" />
		<kendo:grid-toolbarItem name="Clear_Filter" text="Clear Filter" />
	</kendo:grid-toolbar>

	<kendo:grid-columns>

		<kendo:grid-column title="Staff Name" field="staffName" width="50px"
			filterable="true">
			<%-- <kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function staffNameFilter(element) {
							element.kendoAutoComplete({
								dataSource : {
									transport : {
										read : "${getStaffNameListUrl}"
									}
								}
							});
						}
					</script>

				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable> --%>
		</kendo:grid-column>
		<kendo:grid-column title="Department" field="departmentName"
			width="80px">
			<%-- <kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function staffNameFilter(element) {
							element.kendoAutoComplete({
								dataSource : {
									transport : {
										read : "${departmentFilterUrl}"
									}
								}
							});
						}
					</script>

				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable> --%>
		</kendo:grid-column>


		<kendo:grid-column title="Designation" field="desigName" width="80px">
			<%-- <kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function staffNameFilter(element) {
							element.kendoAutoComplete({
								dataSource : {
									transport : {
										read : "${designationFilterUrl}"
									}
								}
							});
						}
					</script>

				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable> --%>
		</kendo:grid-column>

		<kendo:grid-column title="Access Card No" field="accessCardNo"
			width="80px" filterable="false">
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function staffNameFilter(element) {
							element.kendoAutoComplete({
								dataSource : {
									transport : {
										read : "${accessCardNoUrl}"
									}
								}
							});
						}
					</script>

				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>

		<kendo:grid-column title="" field="selectByMonth" hidden="true" />

	</kendo:grid-columns>

	<kendo:dataSource requestEnd="onRequestEnd"
		requestStart="onRequestStart">

		<kendo:dataSource-transport>
			<kendo:dataSource-transport-read url="${readUrl}" dataType="json"
				type="POST" contentType="application/json" />
			<kendo:dataSource-transport-parameterMap>
				<script>
					function parameterMap(options, type) {
						return JSON.stringify(options);
					}
				</script>
			</kendo:dataSource-transport-parameterMap>
		</kendo:dataSource-transport>

		<kendo:dataSource-schema>
			<kendo:dataSource-schema-model id="objectIdLo">
				<kendo:dataSource-schema-model-fields>

					<kendo:dataSource-schema-model-field name="objectIdLo"
						editable="false" />
					<kendo:dataSource-schema-model-field name="staffName" />
					<kendo:dataSource-schema-model-field name="departmentName" />
					<kendo:dataSource-schema-model-field name="desigName" />
					<kendo:dataSource-schema-model-field name="accessCardNo" type="number"/>
					<kendo:dataSource-schema-model-field name="selectByMonth" />

				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>

	</kendo:dataSource>

</kendo:grid>


<kendo:grid-detailTemplate id="attendanceTemplate">
	<kendo:tabStrip name="tabStrip_#=objectIdLo#">
		<kendo:tabStrip-animation></kendo:tabStrip-animation>
		<kendo:tabStrip-items>

			<kendo:tabStrip-item selected="true" text="Attendance History">
				<kendo:tabStrip-item-content>
					<kendo:grid name="calcLines_#=objectIdLo#" pageable="true"
						resizable="true" sortable="true" reorderable="true"
						dataBound="avgAttendanceTimeDataBound" selectable="true"
						scrollable="true">
						<kendo:grid-pageable pageSize="10"></kendo:grid-pageable>
						<kendo:grid-filterable extra="false">
							<kendo:grid-filterable-operators>
								<kendo:grid-filterable-operators-string eq="Is equal to" />
							</kendo:grid-filterable-operators>
						</kendo:grid-filterable>

						<kendo:grid-editable mode="popup" />

						<kendo:grid-columns>


							<kendo:grid-column title="Attendance Date" field="sasDateput"
								format="{0:dd/MM/yyyy}" filterable="true" width="70px" />
							<kendo:grid-column title="Check In Time " field="timeIn"
								 width="70px" filterable="false">
								
							</kendo:grid-column>
							<kendo:grid-column title="Check Out Time" field="timeOut"
								 width="70px" filterable="false"
								footerTemplate="<span style='margin-left: 0px;'><i>Avg Time Spent[/day](hrs) </i></span>">
								
							</kendo:grid-column>

							<kendo:grid-column title="Time Spent:(hrs)" field="timeSpent"
								footerTemplate="<span id='totlcst'><span id='sumTotal_#=objectIdLo#'></span></span>"
								width="70px" filterable="false">

							</kendo:grid-column>
							<kendo:grid-column title="Time Out Status"
								field="timeOutSuccessfull" width="100px" filterable="false" hidden="true"/>


						</kendo:grid-columns>

						<kendo:dataSource pageSize="20">

							<kendo:dataSource-aggregate>
								<kendo:dataSource-aggregateItem aggregate="average"
									field="timeSpent" />

							</kendo:dataSource-aggregate>

							<kendo:dataSource-transport>
								<kendo:dataSource-transport-read
									url="${viewAttendanceSummary}/#=objectIdLo#/#=selectByMonth#"
									dataType="json" type="POST" contentType="application/json" />


								<kendo:dataSource-transport-parameterMap>
									<script>
										function parameterMap(options, type) {
											return JSON.stringify(options);
										}
									</script>
								</kendo:dataSource-transport-parameterMap>

							</kendo:dataSource-transport>
							<kendo:dataSource-schema>
								<kendo:dataSource-schema-model id="objectIdLo">
									<kendo:dataSource-schema-model-fields>
										<kendo:dataSource-schema-model-field name="objectIdLo"
											type="number" />
										<kendo:dataSource-schema-model-field name="timeOut"
											 />
										<kendo:dataSource-schema-model-field name="timeSpent" />
										<kendo:dataSource-schema-model-field name="timeIn"/>
										<kendo:dataSource-schema-model-field name="sasDateput"
											type="date" />


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

<div id="loading"></div>
<script>

   
	var SelectedRowId = "";
	var selectMonth = "";
	function onChangeAttendanceSummary(arg) {
		var gview = $("#attendanceGrid").data("kendoGrid");
		var selectedItem = gview.dataItem(gview.select());
		SelectedRowId = selectedItem.objectIdLo;
		this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
		$(".k-master-row.k-state-selected").show();
		selectMonth = $('#selectByMonth').val();

	}

	function avgAttendanceTimeDataBound(e) {

		var data = this.dataSource.view();
		var sum = 0;
		for (var i = 0; i < data.length; i++) {
			row = this.tbody.children("tr[data-uid='" + data[i].uid + "']");
			sum = sum + data[i].timeSpent;
		}
		var length=data.length;
		var avg=(sum/length);
		$('#sumTotal_' + SelectedRowId).text(Math.round(avg));
		$('#totlcst').show();

	}

	function onRequestEnd(e) {

		kendo.ui.progress($("#loading"), false);

	}

	function onRequestStart(e) {

		kendo.ui.progress($("#loading"), true);

	}

	function searchByMonth() {
		var month = $('#selectByMonth').val();
		
		$.ajax({
			type : "POST",
			url : "./attendanceHistory/searchByMonth/" + month,
			dataType : "json",
			success : function(result) {
				var grid = $("#attendanceGrid").data("kendoGrid");
				var data = new kendo.data.DataSource();
				grid.dataSource.data(result);
				grid.refresh();
			}
		});
	}

	$(document).ready(function() {
		$("#selectByMonth").kendoDatePicker({

			// defines the start view
			start : "year",
			// defines when the calendar should return date
			depth : "year",
			value : new Date(),
			// display month and year in the input
			format : "MMMM yyyy"
		});
	});

	$("#attendanceGrid").on("click", ".k-grid-Clear_Filter", function() {
		//custom actions
		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
		var grid = $("#attendanceGrid").data("kendoGrid");
		grid.dataSource.read();
		grid.refresh();
	});
</script>

<style>
.k-state-active,.k-state-active:hover,.k-active-filter,.k-tabstrip .k-state-active
	{
	background-color: #fff;
	border-color: #b7b7b7;
	color: #2e2e2e;
}
</style>

