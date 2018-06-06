<%@include file="dashboardheader.jsp"%>
<br />
<br />

<c:url value="/patrolTracks/readPatrolTracks" var="readUrl" />
	<c:url value="/patrolTracks/createPatrolTrack" var="createUrl" />
	<c:url value="/patrolTracks/updatePatrolTrack" var="updateUrl" />
	<c:url value="/patrolTracks/deletePatrolTracks" var="destroyUrl" />



<style>
            ptValidTimeFrom {
                height: 52px;
                width: 221px;
                margin: 30px auto;
                padding: 91px 0 0 188px;
                background: url('../../WebContent/WEB-INF/jsps/img/images.png') transparent no-repeat 0 0;
            }
        </style>

<div class="wrapper">

<kendo:grid name="grid" pageable="true" columnMenu="true" height="430px"
		resizable="true" sortable="true" selectable="true" scrollable="true"
		filterable="true">
		<kendo:grid-editable mode="popup"
			confirmation="Are you sure you want to remove this PatrolTrack?" />
			<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add PatrolTrack" />
		</kendo:grid-toolbar>
		
		<kendo:grid-columns>
			<kendo:grid-column title="PatrolTrack Id" field="ptId" filterable="true" width="130px" />
			<kendo:grid-column title="PatrolTrack Name" field="ptName" filterable="true"
				width="130px" />
			<kendo:grid-column title="Description" field="description"
				filterable="true" width="130px" />
			<kendo:grid-column title="ValidTimeFrom " field="validTimeFrom" editor="fromTimeEditor"
				width="130px" filterable="false" />
			<kendo:grid-column title="ValidTimeTo" field="validTimeTo" format="{0:HH:mm}" editor="toTimeEditor"
				width="130px" filterable="false" />
			<kendo:grid-column title="AdminAlertMobileNo" field="adminAlertMobileNo" filterable="false" width="130px"/>
			<kendo:grid-column title="AdminAlertEmailId" field="adminAlertEmailId"
				filterable="false" width="130px" />
			<kendo:grid-column title="Status" field="status"
				filterable="false" width="130px" />
			<kendo:grid-column title="Created By" field="createdBy"
				filterable="false" width="130px" />
			<kendo:grid-column title="LastUpdated By" field="lastUpdatedBy"
				filterable="false" width="130px" />
			<kendo:grid-column title="LastUpdated Date" field="lastUpdatedDt" template="#= kendo.toString(lastUpdatedDt, 'dd/MM/yyyy')#"
				filterable="true" width="130px" />

			<kendo:grid-column title="&nbsp;" width="100px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit"/>
					<kendo:grid-column-commandItem name="destroy" />
				</kendo:grid-column-command>
			</kendo:grid-column>
		</kendo:grid-columns>
		<kendo:dataSource pageSize="10" serverFiltering="true" requestStart="onRequestStart">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-read url="${readUrl}" dataType="json"
					type="POST" contentType="application/json" />
				<kendo:dataSource-transport-create url="${createUrl}"
					dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-update url="${updateUrl}"
					dataType="json" type="POST" contentType="application/json" />
					<kendo:dataSource-transport-destroy url="${destroyUrl}"
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
				<kendo:dataSource-schema-model id="ptId">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="ptId" editable="false" />
						<kendo:dataSource-schema-model-field name="ptName" type="string" />
						<kendo:dataSource-schema-model-field name="description" type="string"  />
						<kendo:dataSource-schema-model-field name="validTimeFrom" >
						<kendo:dataSource-schema-model-field-validation/>
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="validTimeTo" />
						<kendo:dataSource-schema-model-field name="adminAlertMobileNo" type="tel" >
						 <kendo:dataSource-schema-model-field-validation required="true"
								pattern="\d{10}" /> 
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="adminAlertEmailId" type="email">
							 <kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="status">
							 <kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="createdBy">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="lastUpdatedBy">
							 <kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="lastUpdatedDt" type="date" editable="false"/>
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
		
		</kendo:grid>
		
		<script>
		
		$("#grid").on("click", ".k-grid-add", function() {
			$(".k-window-title").text("Add");
			$(".k-grid-update").text("Save");
			
		});
		function fromTimeEditor(container, options) {
		    $('<input data-text-field="' + options.field + '" id="timepicker" data-value-field="' + options.field + '" data-bind="value:' + 
		    		options.field + '" data-format="' + ["HH:mm"] + '"/>')
		            .appendTo(container)
		            .kendoTimePicker({});
		}
		
		function onRequestStart(e){
			if (e.type == "create"){
				var gridStoreGoodsReturn = $("#grid").data("kendoGrid");
				gridStoreGoodsReturn.cancelRow();		
			}	
		}
		
		function toTimeEditor(container, options) {
		    $('<input data-text-field="' + options.field + '" data-value-field="' + options.field + '" data-bind="value:' + options.field + '" data-format="' + options.format + '"/>')
		            .appendTo(container)
		            .kendoTimePicker({});
		}
		</script>
		
</div>
