<%@include file="/common/taglibs.jsp"%>
	<c:url value="/servicetasks/read" var="readUrl" />
	<c:url value="/common/getAllChecks" var="allChecksUrl" />
	<c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />
	<c:url value="/servicetasks/saveorupadteordelete" var="serviceSaveOrUpdateOrDelete" />
	<c:url value="/servicetasks/getAllServiceType" var="getAllServiceType" />
	<c:url value="/servicetasks/getServiceType" var="getServiceType" />
	<c:url value="/servicetasks/getspmName" var="getspmName" />
	<c:url value="/servicetasks/getspmDataType" var="getspmDataType" />
	<c:url value="/servicetasks/getsmpDescription" var="getsmpDescription" />
	<c:url value="/servicetasks/getspmStatus" var="getspmStatus" /> 
	<c:url value="/servicetasks/getspmSequence" var="getspmSequence" />
	<c:url value="/servicetasks/whomCreated" var="whomCreated" />
	<c:url value="/servicetasks/whomLastUpadted" var="whomLastUpadted" />
	<c:url value="/servicetasks/getlastupdateDate" var="getlastupdateDate" />
	<kendo:grid name="grid" edit="serviceParameterMasterEdit" pageable="true" scrollable="true"
		filterable="true" sortable="true" reorderable="true" groupable="true" selectable="true"
		resizable="true">
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10"
			input="true" numeric="true"></kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
			<kendo:grid-filterable-operators>
				<kendo:grid-filterable-operators-string eq="Is equal to" />
			</kendo:grid-filterable-operators>
		</kendo:grid-filterable>
		<kendo:grid-editable mode="popup"
			confirmation="Are You Sure to delete?" />
		<kendo:grid-toolbar>

			<kendo:grid-toolbarItem name="create"
				text="Enter Service Parameter Master" />
			<kendo:grid-toolbarItem name="Clear_Filter" text="Clear Filter" />
		</kendo:grid-toolbar>


		<kendo:grid-columns>
			<kendo:grid-column title="SPM Id" field="spmId" width="1px"
				hidden="true">

			</kendo:grid-column>
			<kendo:grid-column title="Service Type&nbsp" field="serviceType"
				editor="dropDownChecksEditor" width="100px">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function propertyNumberFilter(element) {
								element.kendoAutoComplete({
									dataType : 'JSON',
									dataSource : {
										transport : {
											read : "${getServiceType}"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			<kendo:grid-column title="Service Master Sequence" field="spmSequence"
				width="170px">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function propertyNumberFilter(element) {
								element.kendoAutoComplete({
									dataType : 'JSON',
									dataSource : {
										transport : {
											read : "${getspmSequence}"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			<kendo:grid-column title="Service Master Data_Type" field="spmdataType"
				editor="dropDownChecksEditor" width="200px">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function propertyNumberFilter(element) {
								element.kendoAutoComplete({
									dataType : 'JSON',
									dataSource : {
										transport : {
											read : "${getspmDataType}"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			<kendo:grid-column title="Service Master Name" field="spmName" width="170px">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function propertyNumberFilter(element) {
								element.kendoAutoComplete({
									dataType : 'JSON',
									dataSource : {
										transport : {
											read : "${getspmName}"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="Service Master Description" field="spmDescription"
				width="170px" editor="serviceDescriptionEditor">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function propertyNumberFilter(element) {
								element.kendoAutoComplete({
									dataType : 'JSON',
									dataSource : {
										transport : {
											read : "${getsmpDescription}"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
		 <kendo:grid-column title="Service Master Status" field="status" 
				editor="dropDownChecksEditor" width="90px">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function propertyNumberFilter(element) {
								element.kendoAutoComplete({
									dataType : 'JSON',
									dataSource : {
										transport : {
											read : "${getspmStatus}"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="Created By" field="createdBy" width="170px">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function propertyNumberFilter(element) {
								element.kendoAutoComplete({
									dataType : 'JSON',
									dataSource : {
										transport : {
											read : "${whomCreated}"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			<kendo:grid-column title="Last Updated By" field="lastupdatedBy"
				width="160px">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function propertyNumberFilter(element) {
								element.kendoAutoComplete({
									dataType : 'JSON',
									dataSource : {
										transport : {
											read : "${whomLastUpadted}"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			<kendo:grid-column title="&nbsp;" width="80px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit" />
				</kendo:grid-column-command>
			</kendo:grid-column>
			<kendo:grid-column title="&nbsp;" width="120px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="destroy"/>				
				</kendo:grid-column-command>
			</kendo:grid-column>
			<kendo:grid-column title=""
    template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Active#=data.spmId#'>#= data.status == 'Active' ? 'In-active' : 'Active' #</a>"
    width="90px" />
		</kendo:grid-columns>
		<kendo:dataSource requestStart="onRequestStartServiceMaster" requestEnd="onRequestEndServiceMaster">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-read url="${readUrl}" dataType="json"
					type="POST" contentType="application/json" />
				<kendo:dataSource-transport-create url="${serviceSaveOrUpdateOrDelete}/create"
					dataType="json" type="GET" contentType="application/json" />
				 <kendo:dataSource-transport-update url="${serviceSaveOrUpdateOrDelete}/update"
					dataType="json" type="GET" contentType="application/json" />
				<kendo:dataSource-transport-destroy url="${serviceSaveOrUpdateOrDelete}/delete"
					dataType="json" type="GET" contentType="application/json" /> 
			</kendo:dataSource-transport>
			<kendo:dataSource-schema>
				<kendo:dataSource-schema-model id="spmId">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="spmId" type="number" />
						<kendo:dataSource-schema-model-field name="spmSequence"
							type="number">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="serviceType"
							type="string" >
							<kendo:dataSource-schema-model-field-validation required="true" />
							</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="status"  type="string"/>
						
						<kendo:dataSource-schema-model-field name="spmName" type="string">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="spmdataType"
							type="string" >
							<kendo:dataSource-schema-model-field-validation required="true" />
							</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="createdBy"
							type="string">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="lastUpdatedBy"
							type="string">
							<kendo:dataSource-schema-model-field-validation required="true" />
								</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="spmDescription" type="string">
								<kendo:dataSource-schema-model-field-validation required="true" />
							</kendo:dataSource-schema-model-field>

					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>

	</kendo:grid>

<div id="alertsBox" title="Alert"></div>
<script>
	
	$("#grid").on("click", ".k-grid-Clear_Filter",
			function() {
				//custom actions

				$("form.k-filter-menu button[type='reset']").slice()
						.trigger("click");
				var gridServiceMaster = $("#grid").data("kendoGrid");
				gridServiceMaster.dataSource.read();
				gridServiceMaster.refresh();
	});
	
	 
	
   
	function serviceDescriptionEditor(container, options) {
		$(
				'<textarea data-text-field="spmDescription" data-value-field="spmDescription" data-bind="value:' + options.field + '" style="width: 148px; height: 40px;"/>')
				.appendTo(container);
	}
	
	
	function dropDownChecksEditor(container, options) {
		var res = (container.selector).split("=");
		var attribute = res[1].substring(0, res[1].length - 1);

		$(
				'<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '" name = "'+attribute+'" id = "'+attribute+'Id"/>')
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
	
	 
	function onRequestStartServiceMaster(e)
	{
		var grid = $("#grid").data("kendoGrid");
		grid.cancelRow();
	}
	function onRequestEndServiceMaster(e) 
	{
		displayMessage(e, "grid", "Service Master");
	}
	function serviceParameterMasterEdit(e)
	{
		var sessionUserLoginName = '<%=session.getAttribute("userId")%>';
		
		$('label[for="spmId"]').remove();
		$('div[data-container-for="spmId"]').remove();
		$('label[for="createdBy"]').remove();
		$('div[data-container-for="createdBy"]').remove();
		$('label[for="lastupdatedBy"]').remove();
		$('div[data-container-for="lastupdatedBy"]').remove();
		/* $('label[for="status"]').remove();
		$('div[data-container-for="status"]').remove(); */
		$('.k-edit-label:nth-child(18n+1)').each(
				function(e) {
					$(this).nextAll(':lt(17)').addBack().wrapAll(
							'<div class="wrappers"/>');
				});
		
		$('a[id="temPID"]').remove();
	
		if (e.model.isNew()) 
	    {
			$(".k-window-title").text("Add New Store Record");
			$(".k-grid-update").text("Save");
			/* $('input[name="createdBy"]').val(sessionUserLoginName).change(); */
	    }
		else
		{
			$(".k-window-title").text("Edit Service Details");
		}

	}
	 $("#grid").on("click", "#temPID", function(e) {
		  var button = $(this), enable = button.text() == "Active";
		  var widget = $("#grid").data("kendoGrid");
		  var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
		     if (enable) 
		     {
		      $.ajax
		      ({
		       type : "POST",
		       url : "./serviceParameterMaster/serviceMaster/" + dataItem.id + "/activate",
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
		        button.text('In-active');
		        $('#grid').data('kendoGrid').dataSource.read();
		       }
		      });
		     } 
		     else 
		     {
		      $.ajax
		      ({
		       type : "POST",
		       url : "./serviceParameterMaster/serviceMaster/" + dataItem.id + "/deactivate",
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
		        button.text('Active');
		        $('#grid').data('kendoGrid').dataSource.read();
		       }
		      });
		     }
		   });
</script>
