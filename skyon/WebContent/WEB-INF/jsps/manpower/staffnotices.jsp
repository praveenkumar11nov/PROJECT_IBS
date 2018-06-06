<%@include file="/common/taglibs.jsp"%>

	<c:url value="/manpower/notice/read" var="readUrl" />
	<c:url value="/manpower/notice/create" var="createUrl" />
	<c:url value="/manpower/notice/update" var="updateUrl" />
	<c:url value="/manpower/notice/destroy" var="destroyUrl" />

	<c:url value="/manpower/getPerson" var="personFilterUrl" />
	<c:url value="/manpower/getMPerson" var="personUrl" />

	<c:url value="/manpower/notice/noticeDetails"
		var="transportNestedReadUrl" />

	<kendo:grid name="gridNotices" pageable="true" detailTemplate="templateNotices" resizable="true" sortable="true" selectable="true" scrollable="true"
		filterable="true" groupable="true" change="onChangeNotice">

		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10"></kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
			<kendo:grid-filterable-operators>
				<kendo:grid-filterable-operators-string eq="Is equal to" />
			</kendo:grid-filterable-operators>

		</kendo:grid-filterable>

		<kendo:grid-columns>

			<kendo:grid-column title="Staff Name" field="personName"
				width="500px">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function personNameFilter(element) {
								element.kendoAutoComplete({
									dataSource : {
										transport : {
											read : "${personFilterUrl}"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>

			<kendo:grid-column title="DoB" field="dob" hidden="true"
				format="{0:dd/MM/yyyy}">
			</kendo:grid-column>
			<kendo:grid-column title="Language" field="lang" hidden="true">
			</kendo:grid-column>
			<kendo:grid-column title="Occupation" field="occu"
				hidden="true">
			</kendo:grid-column>

			<kendo:grid-column title="Type" field="personType"
				hidden="true">
			</kendo:grid-column>
			<kendo:grid-column title="Title" field="title"
				hidden="true">
			</kendo:grid-column>
			<kendo:grid-column title="Father Name" field="fatherName"
				hidden="true">
			</kendo:grid-column>
			<kendo:grid-column title="Designation" field="designation"
				hidden="true">
			</kendo:grid-column>
			<kendo:grid-column title="Department" field="department"
				hidden="true">
			</kendo:grid-column>
			<kendo:grid-column title="Type" field="staffType"
				hidden="true">
			</kendo:grid-column>
			<kendo:grid-column title="Name" field="loginName"
				hidden="true">
			</kendo:grid-column>
			<kendo:grid-column title="Status" field="userStatus"
				hidden="true">
			</kendo:grid-column>
			<kendo:grid-column title="Roles" field="roles"
				hidden="true">
			</kendo:grid-column>
			<kendo:grid-column title="Groups" field="groups"
				hidden="true">
			</kendo:grid-column>

			<kendo:grid-column title="Appreciation" field="appr" width="100px" />

			<kendo:grid-column title="Incident" field="incident" width="100px" />
			<kendo:grid-column title="Warning" field="warning" width="100px" />

			<kendo:grid-column title="Person Id" field="personId" width="100px"
				hidden="true" />

		</kendo:grid-columns>
		<kendo:dataSource pageSize="20">
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
				<kendo:dataSource-schema-model id="personId">
					<kendo:dataSource-schema-model-fields>

						<kendo:dataSource-schema-model-field name="personName">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>

						<kendo:dataSource-schema-model-field name="appr" type="string"
							editable="false">
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="incident" type="string"
							editable="false">
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="warning" type="string"
							editable="false">
						</kendo:dataSource-schema-model-field>

						<kendo:dataSource-schema-model-field name="personId" type="number"
							editable="false">

						</kendo:dataSource-schema-model-field>

					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
	</kendo:grid>

	<kendo:grid-detailTemplate id="templateNotices">

		<kendo:tabStrip name="tabStripNotices_#=personId#">
			<kendo:tabStrip-items>
			<kendo:tabStrip-item text="Staff Information">
					<kendo:tabStrip-item-content>
						<div class='employee-details'>
							<table>
								<tr>
									<td width="150px"><img src="<c:url value='/person/getpersonimage/#=personId#'/>" alt=""
										width="150px;" height="170px;" /></td>
									<td style="vertical-align: top;">
									 <br>
									<h2>#= personName #</h2> 
										<dl>
											<br>
											<table>
												<tr>
													<td><b>Category</b></td>
													<td>#= personType#</td>
													<td><b>Department</b></td>
													<td>#= department#</td>
												</tr>
												<tr>
													<td><b>Staff Type</b></td>
													<td>#= staffType#</td>
													<td><b>Designation</b></td>
													<td>#= designation#</td>
												</tr>
												<tr>
												<td><b>Languages Known</b></td>
													<td> #= lang #</td>
													<td><b>Status</b></td>
													<td>#= userStatus #</td>
												</tr>

												<tr>											
													<td><b>Login Name</b></td>
													<td>#=loginName#</td>
													<td><b>Roles</b></td>
													<td>#= roles #</td>
												</tr>										
											</table>	
										</dl> 
									</td>
								</tr>
							</table>
						</div>
					</kendo:tabStrip-item-content>
				</kendo:tabStrip-item>
			
				<kendo:tabStrip-item text="Staff Notices" selected="true">
					<kendo:tabStrip-item-content>
						<kendo:grid name="gridNotices_#=personId#"  sortable="true" pageable="true" edit="noticeEvent" remove="removeNotice"
							scrollable="true" selectable="true">
							<kendo:grid-editable mode="popup" />
							<kendo:grid-toolbar>
								<kendo:grid-toolbarItem name="create" text="Add Staff Notice" />
							</kendo:grid-toolbar>
							<kendo:grid-columns>
			
								<kendo:grid-column title="snId" field="snId" hidden="true"	width="0px"/>
								<kendo:grid-column title="Notice Type *" field="noticeType"	width="100px" editor="noticeTypeEditor" />
								<kendo:grid-column title="Description" field="description"	width="140px" editor="descriptionEditor" />
								<kendo:grid-column title="Notice Date *" field="snDate"		width="100px" format="{0:dd/MM/yyyy}" />
								<kendo:grid-column title="Notice Action *" field="snAction"	width="100px" />
								<kendo:grid-column title="Action Date *" field="snActionDate"	width="100px" format="{0:dd/MM/yyyy}" />

								<kendo:grid-column title="&nbsp;" width="350px">
									<kendo:grid-column-command>
										<kendo:grid-column-commandItem name="edit" />
										<kendo:grid-column-commandItem name="destroy" />
										<kendo:grid-column-commandItem name="Upload" click="uploadNotice" />
										<kendo:grid-column-commandItem name="Download"	click="downloadFile" />
									</kendo:grid-column-command>
								</kendo:grid-column>

							</kendo:grid-columns>
							<kendo:dataSource pageSize="20" requestEnd="onRequestEndNotices" requestStart="onRequestStartNotices">
								<kendo:dataSource-transport>
									<kendo:dataSource-transport-create	url="${createUrl}/#=personId#" dataType="json" type="GET" contentType="application/json" />
									<kendo:dataSource-transport-read url="${transportNestedReadUrl}/#=personId#" dataType="json" type="POST" contentType="application/json" />
									<kendo:dataSource-transport-update	url="${updateUrl}/#=personId#" dataType="json" type="GET" contentType="application/json" />
									<kendo:dataSource-transport-destroy url="${destroyUrl}/"	dataType="json" type="GET" contentType="application/json" />
								</kendo:dataSource-transport>
								<kendo:dataSource-schema>
									<kendo:dataSource-schema-model id="snId">
										<kendo:dataSource-schema-model-fields>

											<kendo:dataSource-schema-model-field name="snId" type="number"/>

											<kendo:dataSource-schema-model-field name="noticeType" type="string">
												<kendo:dataSource-schema-model-field-validation	required="true"  />
											</kendo:dataSource-schema-model-field>

											<kendo:dataSource-schema-model-field name="description"	type="string">
												<kendo:dataSource-schema-model-field-validation	required="true" />
											</kendo:dataSource-schema-model-field>

											<kendo:dataSource-schema-model-field name="snDate" type="date"  defaultValue="">
												<kendo:dataSource-schema-model-field-validation	 />
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="snAction" type="string">
												<kendo:dataSource-schema-model-field-validation	 pattern="^.{0,100}$"/>
											</kendo:dataSource-schema-model-field>

											<kendo:dataSource-schema-model-field name="snActionDate" type="date" defaultValue="">
												<kendo:dataSource-schema-model-field-validation	/>
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="createdBy" type="string">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="lastUpdateBy" type="string">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="lastUpdatedDate"	type="date" editable="false" />
												
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
<div id="uploadDialog" title="Upload Notice" style="display: none;">
	<kendo:upload name="files" multiple="false" upload="uploadExtraData" success="onDocSuccess">
		<kendo:upload-async autoUpload="true"
			saveUrl="./manpower/notice/upload" />
	</kendo:upload>
</div>
<div id="alertsBox" title="Alert"></div>
<script>
/* 
function parse (response) {   
    $.each(response, function (idx, elem) {
        if (elem.snDate && typeof elem.snDate === "string") {
            elem.snDate = kendo.parseDate(elem.snDate, "yyyy-MM-ddTHH:mm:ss.fffZ");
        }
        if (elem.snActionDate && typeof elem.snActionDate === "string") {
            elem.snActionDate = kendo.parseDate(elem.snActionDate, "yyyy-MM-ddTHH:mm:ss.fffZ");
        }
    });
    return response;
}  */ 


	var flagUserId = "";
	var snId = "";
	var SelectedRowId = "";
	function onChangeNotice(arg) {
		var gview = $("#gridNotices").data("kendoGrid");
		var selectedItem = gview.dataItem(gview.select());
		SelectedRowId = selectedItem.personId;
		this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
		// alert("Selected: " + SelectedRowId);
	}

	
	function noticeEvent(e)
	{
		$('label[for="personName"]').closest('.k-edit-label').remove();
		$('div[data-container-for="personName"]').remove();

		if (e.model.isNew()) 
	    {
			securityCheckForActions("./manpower/staffnotices/createButton");
			
			 
			/* var addUrl = "./manpower/staffnotices/createButton";
			var gridName = "#gridNotices_"+SelectedRowId;
			
			addAccess(addUrl,gridName);  */
			
			$(".k-window-title").text("Add New Staff Notice");
			$(".k-grid-update").text("Save");	
			$('label[for="snId"]').closest('.k-edit-label')
			.remove();
			$('div[data-container-for="snId"]').remove();
	    }
		else{
			securityCheckForActions("./manpower/staffnotices/updateButton");
			
			/* var editUrl = "./manpower/staffnotices/updateButton";
			var gridName = "#gridNotices_"+SelectedRowId;
			
			editAccess(editUrl,gridName);  */
			
			$(".k-window-title").text("Edit Staff Notice");
			$('label[for="snId"]').closest('.k-edit-label')
			.remove();
			$('div[data-container-for="snId"]').remove();
		}
		
	/* 	$(".k-grid-update").click(function() {
			
			var snDate = $('input[name="snDate"]').val();
			var snActionDate = $('input[name="snActionDate"]').val();
			
			if ((new Date(snDate).getTime() < new Date(snActionDate).getTime())) {
				alert("Notice date should not be less than Action Date");
				return false;
			}
	}); */
	}
	
	function removeNotice(){		
		securityCheckForActions("./manpower/staffnotices/deleteButton");
		var conf = confirm("Are u Sure want to delete this Notice Record?");
		if(!conf){
			$('#gridNotices_'+SelectedRowId).data().kendoGrid.dataSource.read({
				personId : SelectedRowId
			});
			 throw new Error('deletion aborted');
		}
		
	}
	
	/* $("#gridNotices_"+SelectedRowId).on("click", ".k-grid-delete", function() {
		var deleteUrl = "./manpower/staffnotices/deleteButton";
		var gridName = "#gridNotices_"+SelectedRowId;
		deleteAccess(deleteUrl,gridName);
	}); */
	
	
	function uploadExtraData(e) {
				
		var files = e.files;
		// Check the extension of each file and abort the upload if it is not .jpg
		$.each(files, function() {
			if (this.extension.toLowerCase() == ".pdf") {
				e.data = {snId : snId , type : this.extension};
			}
			else if (this.extension.toLowerCase() == ".doc") {
				e.data = {snId : snId , type : this.extension};
			}
			else if (this.extension.toLowerCase() == ".docx") {
				e.data = {snId : snId , type : this.extension};
			}
			else if (this.extension.toLowerCase() == ".csv") {
				e.data = {snId : snId , type : this.extension};
			}
			else if (this.extension.toLowerCase() == ".xlsx") {
				e.data = {snId : snId , type : this.extension };
			}
			else if (this.extension.toLowerCase() == ".xls") {
				e.data = {snId : snId , type : this.extension };
			}
			else {
				alert("Invalid Document Type:\nAcceptable formats are pdf, doc, docx, csv and xls");
				e.preventDefault();
				return false;
			}
		});
	}

	function descriptionEditor(container, options) {
		$(
				'<textarea  maxlength="300" data-text-field="description" data-value-field="description" data-bind="value:' + options.field + '" style="width: 148px; height: 40px;"/>')
				.appendTo(container);
	}
	
	
	function downloadFile() {
		
		securityCheckForActions("./manpower/staffnotices/updateButton");
		
		/* var editUrl = "./manpower/staffnotices/updateButton";
		var gridName = "#gridNotices_"+SelectedRowId;
		editAccess(editUrl,gridName);  */
		var gview = $("#gridNotices_" + SelectedRowId).data("kendoGrid");
		//Getting selected item
		var selectedItem = gview.dataItem(gview.select());
		window.open("./manpower/notice/download/" + selectedItem.snId);
		/* $.ajax({
			 type : "POST",
			 url :"./download/"+selectedItem.drId,
			 success : function(response)
			 {
				 alert(response);
			 }
			}); */
	}

/* 	function dataBound() {
		this.expandRow(this.tbody.find("tr.k-master-row").first());
	} */

	function personEditor(container, options) {
		$(

				'<input data-text-field="personName"  data-value-field="personId" data-bind="value:' + options.field + '"/>')
				.appendTo(container).kendoComboBox({

					dataTextField : "personName",
					dataValueField : "personId",
					optionLabel : {
						personName : "Select",
						personId : "",
					},
					defaultValue : false,
					sortable : true,
					dataSource : {
						transport : {
							read : "${personUrl}"
						}
					}
				});
	}

	function uploadNotice() {
		
		securityCheckForActions("./manpower/staffnotices/updateButton");
		
	/* 	var editUrl = "./manpower/staffnotices/updateButton";
		var gridName = "#gridNotices_"+SelectedRowId;
		editAccess(editUrl,gridName);  */
		var gview = $("#gridNotices_" + SelectedRowId).data("kendoGrid");

		var selectedItem = gview.dataItem(gview.select());
		if (selectedItem != null) {
			snId = selectedItem.snId;
		}
		$('#uploadDialog').dialog({
			modal : true,
		});
		return false;
	}

	

	function noticeTypeEditor(container, options) {

		var data = [ {
			text : "Warning",
			value : "Warning"
		}, {
			text : "Appreciation",
			value : "Appreciation"
		}, {
			text : "Incident",
			value : "Incident"
		} ];

		$(
				'<input name="noticeType" data-text-field="text" id="dept"  data-value-field="value" data-bind="value:' + options.field + '" required="required"/>')
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
		$('<span class="k-invalid-msg" data-for="noticeType"></span>').appendTo(container);
	}

	function onDocSuccess(e)
	{
		alert("Uploaded Successfully !!!");
		$(".k-upload-files.k-reset").find("li").remove();
		$(".k-upload-status-total").remove();
	}
	
	function onRequestStartNotices(e) {
		
		$('.k-grid-update').hide();
		$('.k-edit-buttons')
				.append(
						'<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
		$('.k-grid-cancel').hide();
		var res = (e.sender.options.transport.read.url).split("/");
		/* var sn = $('#gridNotices_' + res[res.length - 1]).data("kendoGrid");
		if(sn != null)
		{
			sn.cancelRow();
		} */
	}
	
	function onRequestEndNotices(e) {
		/* debugger; */

		
		if (typeof e.response != 'undefined') {
			if (e.response.status == "FAIL") {
				
				 errorInfo = "";

				for (var s = 0; s < e.response.result.length; s++) {
					errorInfo += (s + 1) + ". "
							+ e.response.result[s].defaultMessage + "\n";

				}

				if (e.type == "create") {
					//alert("Error: Creating the Staff Notice record\n\n" + errorInfo);

					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Creating the Staff record<br>" + errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					}); 

				}

				if (e.type == "update") {
					//alert("Error: Updating the Staff Notice record\n\n" + errorInfo);
					//alert("Invalid");
					
					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Updating the Staff record<br>" + errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
				} 

				  $('#gridNotices_' + SelectedRowId).data().kendoGrid.dataSource.read({
					personId : SelectedRowId
				});  
				 return false;
			}

			else if (e.response.status == "INVALID") {

				errorInfo = "";

				errorInfo = e.response.result.invalid;

				if (e.type == "create") {
					//alert("Error: Creating the USER record\n\n" + errorInfo);
					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Creating the Staff record<br>" + errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
				}
				if (e.type == "update") {
					//alert("Error: Creating the USER record\n\n" + errorInfo);
					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Updating the Staff record<br>" + errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
				}
				
				$('#gridNotices_'+SelectedRowId).data().kendoGrid.dataSource.read({
					personId : SelectedRowId
				});
				return false;
			}

			else if (e.response.status == "EXCEPTION") {

				errorInfo = "";

				errorInfo = e.response.result.exception;

				if (e.type == "create") {
					//alert("Error: Creating the USER record\n\n" + errorInfo);
					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Creating the Staff record<br>" + errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
				}

				if (e.type == "update") {
					//alert("Error: Creating the USER record\n\n" + errorInfo);
					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Updating the Staff record<br>" + errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
				}
				
				$('#gridNotices_' + SelectedRowId).data().kendoGrid.dataSource.read({
					personId : SelectedRowId
				});
				return false;
			}

			else if (e.type == "create") {

				$("#alertsBox").html("");
				$("#alertsBox")
						.html("Staff Notice Record created successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});

				var res = (e.sender.options.transport.read.url).split("/");
				var grid = $('#gridNotices_' + res[res.length - 1]).data("kendoGrid");
				if(grid != null)
				{
					grid.dataSource.read();
					grid.refresh();
					return false;
				}	
			}

			else if (e.type == "update") {
				
				//alert("User record updated successfully");
				$("#alertsBox").html("");
				$("#alertsBox")
						.html("Staff Notice Record updated successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});

				var res = (e.sender.options.transport.read.url).split("/");
				var grid = $('#gridNotices_' + res[res.length - 1]).data("kendoGrid");
				if(grid != null)
				{
					grid.dataSource.read();
					grid.refresh();
					return false;
				}	

			}
			else if (e.type == "destroy") {
				//alert("User record updated successfully");
				$("#alertsBox").html("");
				$("#alertsBox").html("Deleted successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				$('#gridNotices_' + SelectedRowId).data().kendoGrid.dataSource
				.read({
					personId : SelectedRowId
				});

			}

		}
	}
	var seasonFromAddress = "";
	(function($, kendo) {
		$
				.extend(
						true,
						kendo.ui.validator,
						{
							rules : { // custom rules          	
								snActionvalidation : function(input,
										params) {
									if (input.filter("[name='snAction']").length
											&& input.val()) {
										return /^[a-z\d\-_.\s]+$/i
												.test(input.val());
									}
									return true;
								},
								 addressSeasonTo1: function (input, params) 
					             {
				                     if (input.filter("[name = 'snActionDate']").length && input.val() != "") 
				                     {                          
				                         var flagDate = false;

				                         seasonFromAddress = $('input[name="snDate"]').val();
				                         if (seasonFromAddress != "") 
				                         {
				                                flagDate = true;
				                         }
				                         return flagDate;
				                     }
				                     return true;
				                 },
				                 addressSeasonTo2: function (input, params) 
					             {
				                     if (input.filter("[name = 'snActionDate']").length && input.val() != "") 
				                     {                          
				                         var selectedDate = input.val();
				                         var flagDate = false;

				                         seasonFromAddress = $('input[name="snDate"]').val();
				                         if ($.datepicker.parseDate('dd/mm/yy', selectedDate) > $.datepicker.parseDate('dd/mm/yy', seasonFromAddress)) 
				                         {
				                                flagDate = true;
				                         }
				                         return flagDate;
				                     }
				                     return true;
				                 },
				                 snDateEmpty : function(input, params){
				                      if (input.attr("name") == "snDate")
				                      {
				                       return $.trim(input.val()) !== "";
				                      }
				                      return true;
				                     },
				                  snActionDateEmpty : function(input, params){
					                      if (input.attr("name") == "snActionDate")
					                      {
					                       return $.trim(input.val()) !== "";
					                      }
					                      return true;
					              },
					              snActionEmpty : function(input, params){
				                      if (input.attr("name") == "snAction")
				                      {
				                       return $.trim(input.val()) !== "";
				                      }
				                      return true;
				              },
							},
							messages : {
								//custom rules messages
								snActionvalidation : "Notice Action should not contain any special characters",
								addressSeasonTo1:"Select Notice date first before selecting Action  date and change To date accordingly",
								addressSeasonTo2:"Action date should be after Notice date",
								snDateEmpty:"Notice Date is required",
								snActionDateEmpty:"Notice Action Date is required",
								snActionEmpty:"Notice Action is required"
}
						});

	})(jQuery, kendo);
	//End Of Validation
</script>


<style>
.k-upload-button input {
	z-index: 10000
}
</style>
