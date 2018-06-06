<%@include file="/common/taglibs.jsp"%>

	<c:url value="/accesspoint/read" var="readUrl" />
	<c:url value="/accesspoint/create" var="createUrl" />
	<c:url value="/accesspoint/update" var="updateUrl" />
	<c:url value="/accesspoint/destroy" var="destroyUrl" />
	<c:url value="/accesspoint/getAllApcode" var="apCodesUrl" />
	
	<c:url value="/accesspoint/filter" var="commonFilterUrl" />
	
	<c:url value="/accesspoint/actype" var="readType" />
	<c:url value="/accesspoint/location" var="readLocation" />

	<kendo:grid name="grid" resizable="true" pageable="true" edit="apEvent" remove="removeAp"
		sortable="true" scrollable="true" 
		groupable="true" dataBound="accessDataBound">
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10"></kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
			<kendo:grid-filterable-operators>
				<kendo:grid-filterable-operators-string eq="Is equal to" />
			</kendo:grid-filterable-operators>

		</kendo:grid-filterable>
		<kendo:grid-editable mode="popup" />
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Access Point" />
			<kendo:grid-toolbarItem text="ClearFilter" />
		</kendo:grid-toolbar>
		<kendo:grid-columns>
			
			<kendo:grid-column title="Access&nbsp;Point&nbsp;Code&nbsp;*" field="apCode" width="110px">
			<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function apCodeFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Code",
									dataSource : {
										transport : {
											read : "${commonFilterUrl}/apCode"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
				</kendo:grid-column>
			<kendo:grid-column title="Access&nbsp;Point&nbsp;Type&nbsp;*" field="apType" editor="acesspointTypeEditor" width="110px" >
			 <kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function projectNameFilter(element) {
							element.kendoAutoComplete({
								placeholder : "Enter Access Type",
								dataSource : {
									transport : {
										read : "${readType}"
									}
								}
							});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
			
			</kendo:grid-column>
			<kendo:grid-column title="Access&nbsp;Point&nbsp;Name&nbsp;*" field="apName" width="120px">
			<kendo:grid-column-filterable>
			<kendo:grid-column-filterable-ui>
						<script>
							function apCodeFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Point Name",
									dataSource : {
										transport : {
											read : "${commonFilterUrl}/apName"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
					</kendo:grid-column-filterable>
			</kendo:grid-column>
			<kendo:grid-column title="Access&nbsp;Point&nbsp;Location&nbsp;*" field="apLocation" width="130px">
			 <kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function projectNameFilter(element) {
							element.kendoAutoComplete({
								placeholder : "Enter Access Location",
								dataSource : {
									transport : {
										read : "${readLocation}"
									}
								}
							});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
			
			</kendo:grid-column>
			<kendo:grid-column title="Description.&nbsp;*" field="apDescription" width="110px" editor="descEditor" filterable="false"/>

			<kendo:grid-column title="&nbsp;" width="160px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit" click = "edit" text="Edit"/>
					<kendo:grid-column-commandItem name="destroy" />
				</kendo:grid-column-command>
			</kendo:grid-column>
		</kendo:grid-columns>
		<kendo:dataSource pageSize="20" requestEnd="onRequestEnd" requestStart="onRequestStart">

			<kendo:dataSource-transport>
				<kendo:dataSource-transport-create url="${createUrl}"
					dataType="json" type="GET" contentType="application/json" />
				<kendo:dataSource-transport-read
					url="${readUrl}" dataType="json"
					type="POST" contentType="application/json" />
				<kendo:dataSource-transport-update url="${updateUrl}"
					dataType="json" type="GET" contentType="application/json" />
				<kendo:dataSource-transport-destroy url="${destroyUrl}/"
					dataType="json" type="GET" contentType="application/json" />
			</kendo:dataSource-transport>
			<kendo:dataSource-schema>
				<kendo:dataSource-schema-model id="apId">
					<kendo:dataSource-schema-model-fields>


						<kendo:dataSource-schema-model-field name="apId" type="number">
						</kendo:dataSource-schema-model-field>

						<kendo:dataSource-schema-model-field name="apType">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>

						<kendo:dataSource-schema-model-field name="apName">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>

						<kendo:dataSource-schema-model-field name="apLocation">
							<kendo:dataSource-schema-model-field-validation />
						</kendo:dataSource-schema-model-field>

						<kendo:dataSource-schema-model-field name="apDescription">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>

						<kendo:dataSource-schema-model-field name="apCode">
							<kendo:dataSource-schema-model-field-validation/>
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="createdBy">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="updatedBy">
						</kendo:dataSource-schema-model-field>
						
						<%-- <kendo:dataSource-schema-model-field name="lastUpdatedDate" type="date">
						</kendo:dataSource-schema-model-field> --%>

					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>


			</kendo:dataSource-schema>

		</kendo:dataSource>
	</kendo:grid>

<script>
var res = [];
var flag = "";
var apCode = "";
var count = 0;
var setApCode = "";



function accessDataBound()
{
 
 $.ajax({
		type : "GET",
		url : '${apCodesUrl}',
		dataType : "JSON",
		success : function(response) {
			$.each(response, function(index, value) {
				res.push(value);
			});
		}
	});
	
	}

function acesspointTypeEditor(container, options) {

	var data = [ {
		text : "Tower Level",
		value : "Tower Level"
	}, {
		text : "Floor Level",
		value : "Floor Level"
	}, {
		text : "Flat Level",
		value : "Flat Level"
	}, {
		text : "Common Point",
		value : "Common Point"
	} ];

	$(
			'<input name="Type" data-text-field="text" id="dept"  data-value-field="value" data-bind="value:' + options.field + '" required="required"/>')
			.appendTo(container).kendoDropDownList({

				dataTextField : "text",
				dataValueField : "value",
				optionLabel : {
					text :  "Select",
					value : "",
				},
				defaultValue : false,
				sortable : true,
				dataSource : data
			});
	$('<span class="k-invalid-msg" data-for="apType"></span>').appendTo(container);
}



function edit(e) {
	/* var lang = getURLParameter("language");
    if(lang == 'hin'){
		gridLangConversion();
		$(".k-window-title").text(jQuery.i18n.prop('editDept'));
    }
    else{ */
    
   
    	
    var deptName = $('input[name="apCode"]').val();
	   $.each(res, function(idx1, elem1) {
		  
	
		  
	    if(elem1 === deptName)
	    {
	    
	  
	     res.splice(idx1, 1);
	    } 
	   });
    	$(".k-window-title").text("Edit Access Point Details");
    //}
	

}


function onRequestStart(e){
	$('.k-grid-update').hide();
	$('.k-edit-buttons')
			.append(
					'<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
	$('.k-grid-cancel').hide();
}

function descEditor(container, options) {
	$(
			'<textarea name="Description" data-text-field="apDescription" data-value-field="apDescription" data-bind="value:' + options.field + '" style="width: 148px; height: 75px;" required="true"/>')
			.appendTo(container);
	$('<span class="k-invalid-msg" data-for="Description"></span>').appendTo(container); 
}

function apEvent(e)
{
	if (e.model.isNew()) 
    {
		securityCheckForActions("./commanagement/accesscardspoints/createButton");
		
		$(".k-window-title").text("Add New Access Points");
		$(".k-grid-update").text("Save");
		
		
    }
	else{
		
		
	  
		
		securityCheckForActions("./commanagement/accesscardspoints/updateButton");
		
		setApCode = $('input[name="apCode"]').val();
		$(".k-window-title").text("Edit Access Points Details");
	}
	
}


function removeAp(){
	securityCheckForActions("./commanagement/accesscardspoints/deleteButton");
	var conf = confirm("Are u Sure want to delete this Access Point?");
	if(!conf){
		$('#grid').data().kendoGrid.dataSource.read();
		 throw new Error('deletion aborted');
	}
	 
}
//register custom validation rules
(function($, kendo) {
	$
			.extend(
					true,
					kendo.ui.validator,
					{
						rules : { // custom rules          	
							apCodeValidation : function(input,
									params) {
								//check for the name attribute 
								
	if (input.filter("[name='apCode']").length && input.val()) {
						apCode = input.val();

						$.each(res, function(ind, val) {
							if (setApCode == apCode) {

								setApCode = "";
								return false;
							} else {

								if ((apCode == val)
										&& (apCode.length == val.length)) {
									flag = apCode;
									return false;
								}
							}
						});
					}
					return true;
				},
				
				
				
				
				 departmentNameUniqueness : function(input,params) {
						//check for the name attribute 
						if (input.filter("[name='apCode']").length && input.val()) {
							enterdTrack = input.val().toUpperCase();	
							$.each(res,function(ind, val) {
										if ((enterdTrack == val.toUpperCase()) && (enterdTrack.length == val.length)) {
										flag = enterdTrack;
										return false;
								}
							});
						}
						return true;
				},
				
				
				

				apCodeUniqueness : function(input) {

					if (input.filter("[name='apCode']").length && input.val()
							&& flag != "") {
						flag = "";
						return false;
					}
					return true;
				},
				apCode_Empty : function(input, params) {
					if (input.attr("name") == "apCode") {
						return $.trim(input.val()) !== "";
					}
					return true;
				},
				apLocationEmpty : function(input, params) {
					if (input.attr("name") == "apLocation") {
						return $.trim(input.val()) !== "";
					}
					return true;
				},
			},
			messages : { //custom rules messages
				apCodeUniqueness : "Access Point Code Already Exists",
				apCodeValidation : "Invalid Access Point Code",
				apCode_Empty : "Access Point Code is required",
				apLocationEmpty : "Access Point Location is required"

			}
		});

	})(jQuery, kendo);

	$("#grid").on("click", ".k-grid-ClearFilter", function() {
		//custom actions

		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
		var grid = $("#grid").data("kendoGrid");
		grid.dataSource.read();
		grid.refresh();
	});

	function onRequestEnd(r) {
		/* debugger; */

		if (typeof r.response != 'undefined') {
			if (r.response.status == "FAIL") {

				errorInfo = "";

				for (var s = 0; s < r.response.result.length; s++) {
					errorInfo += (s + 1) + ". "
							+ r.response.result[s].defaultMessage + "<br>";

				}

				if (r.type == "create") {
					alert("Access Point Created Successfully");
				}

				if (r.type == "update") {
					alert("Access Point Updated Successfully");
				}

				$('#grid').data('kendoGrid').refresh();
				$('#grid').data('kendoGrid').dataSource.read();
				return false;
			}

			if (r.response.status == "CHILD") {

				alert("Can't Delete, Child record found");
				$('#grid').data('kendoGrid').refresh();
				$('#grid').data('kendoGrid').dataSource.read();
				return false;
			}

			else if (r.response.status == "INVALID") {

				errorInfo = "";

				errorInfo = r.response.result.invalid;

				if (r.type == "create") {

					alert("Access Point Created Successfully");
				}
				$('#grid').data('kendoGrid').refresh();
				$('#grid').data('kendoGrid').dataSource.read();
				return false;
			}

			else if (r.response.status == "EXCEPTION") {

				errorInfo = "";

				errorInfo = r.response.result.exception;

				if (r.type == "create") {

					alert("Access Point Created Successfully");

				}

				if (r.type == "update") {
					alert("Access Point Updated Successfully");
				}

				$('#grid').data('kendoGrid').refresh();
				$('#grid').data('kendoGrid').dataSource.read();
				return false;
			}

			else if (r.type == "create") {
				alert("Access Point Created Successfully");
			}

			else if (r.type == "update") {
				alert("Access Point Updated Successfully");
			}

			else if (r.type == "destroy") {
				alert("Access Point Deleted Successfully");
			}

		}
	}
</script>