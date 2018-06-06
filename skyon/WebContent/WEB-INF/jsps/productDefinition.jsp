<%@include file="productheadercontent.jsp"%>
<div class="wrapper">

	<br />
	<c:url value="/productDefinition/createProduct" var="createUrl" />
	<c:url value="/productDefinition/readProduct" var="readUrl" />
	<c:url value="/productDefinition/updateProduct" var="updateUrl" />
	<c:url value="/productDefinition/deleteProduct" var="destroyUrl" />

	<kendo:grid name="grid" pageable="true" sortable="true"
		filterable="true" scrollable="true" height="430px">
		<kendo:grid-editable mode="popup"
			confirmation="Are you sure you want to remove this item?" />
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Product" />
		</kendo:grid-toolbar>
		<kendo:grid-columns>
			<kendo:grid-column title="Product Id" field="pr_id" width="30px" />
			<kendo:grid-column title="Product Name" field="name" width="50px" />
			<kendo:grid-column title="Product Description" field="description"
				filterable="false" sortable="false" width="70px" />
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
				<kendo:dataSource-schema-model id="pr_id">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="pr_id" type="number"
							editable="false" />
						<kendo:dataSource-schema-model-field name="name" type="string">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="description"
							type="string">
						</kendo:dataSource-schema-model-field>
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>

	</kendo:grid>
	<script>
		//register custom validation rules
		//validation for product name
		(function($, kendo) {
			$
					.extend(
							true,
							kendo.ui.validator,
							{
								rules : { // custom rules
									productnamevalidation : function(input,
											params) {
										//check for the name attribute 
										if (input.filter("[name='name']").length
												&& input.val()) {
											return /^[a-zA-Z]+[a-zA-Z]*[_]{0,1}[a-zA-Z]*[^_]$/
													.test(input.val())
										}
										return true;
									}
								},
								messages : { //custom rules messages
									productnamevalidation : " Product Name allows characters,atmost one underscore(_) and should not contain numbers,special charecters(except underscore) and should not end with underscore(_)"
								}
							});
		})(jQuery, kendo);

		/*  //validation for product description
		   (function ($, kendo) {
		       $.extend(true, kendo.ui.validator, {
		            rules: { // custom rules
		                productdescriptionvalidation: function (input, params) {
		                    //check for the name attribute 
		                    if (input.filter("[name='description']").length && input.val()) {
		                        return /^[a-zA-Z]/.test(input.val());
		                    }
		                    return true;
		                }
		            },
		            messages: { //custom rules messages
		                productdescriptionvalidation: "Product Description should not contain any numeric value"
		            }
		       });
		   })(jQuery, kendo); */

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
