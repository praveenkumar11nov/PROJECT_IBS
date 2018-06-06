<%@include file="productheadercontent.jsp"%>
<div class="wrapper">
	<br />
	<c:url value="/formDefinition/createForm" var="createUrl" />
	<c:url value="/formDefinition/readForm" var="readUrl" />
	<c:url value="/formDefinition/updateForm" var="updateUrl" />
	<c:url value="/formDefinition/deleteForm" var="destroyUrl" />
	<c:url value="/productDefinition/getproductdata" var="getProductData" />
	<c:url value="/moduleDefinition/getmoduledata" var="getModuleData" />

	<kendo:grid name="grid" pageable="true" sortable="true"
		filterable="true" scrollable="true" height="430px">
		<kendo:grid-editable mode="popup"
			confirmation="Are you sure you want to remove this item?" />
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Form" />
		</kendo:grid-toolbar>

		<kendo:grid-columns>
			<kendo:grid-column title="Form Id" field="fm_id" width="70px" />
			<kendo:grid-column title="Product Name" field="product" width="70px"
				editor="productDropDownEditor" template="#=product.name#"
				filterable="false" sortable="false">
				<%-- <kendo:grid-column-values value="${products}"/> --%>
			</kendo:grid-column>
			<kendo:grid-column title="Module Name" field="module" width="70px"
				editor="moduleDropDownEditor" template="#=module.md_name#"
				filterable="false" sortable="false">
				<%-- <kendo:grid-column-values value="${modules}"/> --%>
			</kendo:grid-column>
			<kendo:grid-column title="Form Name" field="fm_name" width="70px"/>
			<kendo:grid-column title="Form Description" field="fm_description"
				width="70px" filterable="false" sortable="false" />
			<kendo:grid-column title="&nbsp;" width="160px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit" />
					<kendo:grid-column-commandItem name="destroy" />
				</kendo:grid-column-command>
			</kendo:grid-column>
		</kendo:grid-columns>
		<kendo:dataSource pageSize="20" requestEnd="onRequestEnd">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-create url="${createUrl}"
					dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-read url="${readUrl}" dataType="json"
					type="POST" contentType="application/json" />
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
				<kendo:dataSource-schema-model id="fm_id">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="fm_id" type="number"
							editable="false" />
						<kendo:dataSource-schema-model-field name="product">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="module">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="fm_name" type="string">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="fm_description"
							type="string">
						</kendo:dataSource-schema-model-field>
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>

	</kendo:grid>

	<script>
		//register custom validation rules
		//validation for form name
		(function($, kendo) {
			$
					.extend(
							true,
							kendo.ui.validator,
							{
								rules : { // custom rules
									formnamevalidation : function(input, params) {
										//check for the name attribute 
										if (input.filter("[name='fm_name']").length
												&& input.val()) {
											return /^[a-zA-Z]+[a-zA-Z]*[_]{0,1}[a-zA-Z]*[^_]$/
													.test(input.val())
										}
										return true;
									}
								},
								messages : { //custom rules messages
									formnamevalidation : " Module Name allows characters,atmost one underscore(_) and should not contain numbers,special charecters(except underscore) and should not end with underscore(_)"
								}
							});
		})(jQuery, kendo);

		/* //validation for form description
		(function ($, kendo) {
		    $.extend(true, kendo.ui.validator, {
		         rules: { // custom rules
		             formdescriptionvalidation: function (input, params) {
		                 //check for the name attribute 
		                 if (input.filter("[name='fm_description']").length && input.val()) {
		                	 return /^[a-zA-Z]+[a-zA-Z]*[_]{0,1}[a-zA-Z]*[^_]$/.test(input.val())
		                 }
		                 return true;
		             }
		         },
		         messages: { //custom rules messages
		        	 productnamevalidation: " Module Name allows characters,atmost one underscore(_) and should not contain numbers,special charecters(except underscore) and should not end with underscore(_)"
		         }
		    });
		})(jQuery, kendo);
		 */
		function productDropDownEditor(container, options) {
			$(
					'<input data-text-field="name" id="product" data-value-field="pr_id" data-bind="value:' + options.field + '"/>')
					.appendTo(container).kendoDropDownList({
						autoBind : false,
						dataSource : {
							type : "json",
							transport : {
								read : "${getProductData}"
							}
						}
					});
		}

		function moduleDropDownEditor(container, options) {
			$(
					'<input data-text-field="md_name" data-value-field="md_id" data-bind="value:' + options.field + '"/>')
					.appendTo(container).kendoDropDownList({
						cascadeFrom : "product",
						autoBind : false,
						dataSource : {
							type : "json",
							transport : {
								read : "${getModuleData}"
							}
						}
					});
		}

		function onRequestEnd(e) {
			/*  debugger; */
			if (e.type == "update" && !e.response.Errors) {
				alert("Record Is Updated Successfully..");
				var grid = $("#grid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			}

			if (e.type == "create" && !e.response.Errors) {
				alert("Record Is Created Successfully..");
				var grid = $("#grid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			}

		}
	</script>
</div>
