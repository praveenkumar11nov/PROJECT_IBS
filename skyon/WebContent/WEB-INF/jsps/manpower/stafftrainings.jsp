<%@include file="/common/taglibs.jsp"%>

	<c:url value="/manpower/train/create" var="createUrl" />
	<c:url value="/manpower/train/read" var="readUrl" />
	<c:url value="/manpower/train/update" var="updateUrl" />
	<c:url value="/manpower/train/destroy" var="destroyUrl" />
	<c:url value="/manpower/getPerson" var="personUrl" />
	<c:url value="/manpower/train/expDetails" var="transportNestedReadUrl" />

	<kendo:grid name="grid" pageable="true" detailTemplate="template" resizable="true" sortable="true" selectable="true" scrollable="true" filterable="true" groupable="true" change="onChange">

		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10"></kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
			<kendo:grid-filterable-operators>
				<kendo:grid-filterable-operators-string eq="Is equal to" />
			</kendo:grid-filterable-operators>

		</kendo:grid-filterable>
		<kendo:grid-columns>
			<kendo:grid-column title="Staff Name" field="pn_Name" width="100%">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script> 
							function personNameFilter(element) {
								element.kendoAutoComplete({
									dataSource: {
										transport: {
											read: "${personUrl}"
										}
									}
								});
					  		}
					  	</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>

			<kendo:grid-column title="Staff DoB" field="dob" hidden="true"/>
			<kendo:grid-column title="Staff Language" field="lang" hidden="true"/>
			<kendo:grid-column title="Staff Occupation" field="occu" hidden="true"/>
			<kendo:grid-column title="Staff Type" field="personType" hidden="true"/>
			<kendo:grid-column title="Staff Title" field="title" hidden="true"/>
			<kendo:grid-column title="Staff Fatthe Name" field="fatherName"	hidden="true"/>
			<kendo:grid-column title="Staff Designation" field="designation" hidden="true"/>
			<kendo:grid-column title="Staff Department" field="department"	hidden="true"/>
			<kendo:grid-column title="Staff Type" field="staffType"	hidden="true"/>
			<kendo:grid-column title="Staff Login Name" field="loginName" hidden="true"/>
			<kendo:grid-column title="Staff Status" field="userStatus"	hidden="true"/>
			<kendo:grid-column title="Staff Roles" field="roles" hidden="true"/>
			<kendo:grid-column title="Staff Groups" field="groups"	hidden="true"/>
			<kendo:grid-column title="Person Id" field="personId" width="100px"	hidden="true" />

		</kendo:grid-columns>
		<kendo:dataSource pageSize="20">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json" />
			</kendo:dataSource-transport>
			<kendo:dataSource-schema>
				<kendo:dataSource-schema-model id="personId">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="pn_Name"><kendo:dataSource-schema-model-field-validation required="true" /></kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="personId" type="number"	editable="false"/>
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
	</kendo:grid>
	<kendo:grid-detailTemplate id="template">
		<kendo:tabStrip name="tabStrip_#=personId#">
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
										<h2>#= pn_Name #</h2> 
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
			<kendo:tabStrip-item text="Staff Training" selected="true">
				<kendo:tabStrip-item-content>
					<kendo:grid name="grid_#=personId#" pageable="true" sortable="true" remove="removeTraining"
						scrollable="true" selectable="true" edit="trainingEvent">
						<kendo:grid-editable mode="popup"  />
						<kendo:grid-toolbar>
							<kendo:grid-toolbarItem name="create" text="Add Staff Training" />
						</kendo:grid-toolbar>
						<kendo:grid-columns>
							<kendo:grid-column title="Serial No. *" field="ptSlno" width="70px" format="{0:n0}" />
							<kendo:grid-column title="Training Name *" field="trainingName" width="100px" />
							<kendo:grid-column title="Recommended By" field="trainedBy"	width="120px" />
							<kendo:grid-column title="From Date *" field="fromDate" format="{0:dd/MM/yyyy}" filterable="false" width="100px" />
							<kendo:grid-column title="To Date *" field="toDate" format="{0:dd/MM/yyyy}" filterable="false" width="100px" />
							<kendo:grid-column title="Description" field="trainingDesc"	width="140px" editor="trainingDescEditor" />
							<kendo:grid-column title="Certification Achived *" editor="certiAchEditor" field="certificationAch" width="120px" />
							<%--  <kendo:grid-column title="Created By" field="createdBy" width="100px"/> --%>
							<%--  <kendo:grid-column title="Last Updated By" field="lastUpdatedBy" width="100px"/>
            --%>
							<%-- <kendo:grid-column title="Last Updated Date" field="lastUpdatedDate"
				template="#= kendo.toString(lastUpdatedDate, 'dd/MM/yyyy')#"
				filterable="false" width="122px" /> --%>
							<kendo:grid-column title="&nbsp;" width="350px">
								<kendo:grid-column-command>
									<kendo:grid-column-commandItem name="edit" click="edit" />
									<kendo:grid-column-commandItem name="destroy" />
									<kendo:grid-column-commandItem name="Upload" click="uploadCertificate" />
									<kendo:grid-column-commandItem name="Download" click="downloadFile" />
								</kendo:grid-column-command>
							</kendo:grid-column>
						</kendo:grid-columns>
						<kendo:dataSource pageSize="5" requestEnd="onRequestEnd" requestStart="onRequestStart">
							<kendo:dataSource-transport>
								<kendo:dataSource-transport-create	url="${createUrl}/#=personId#" dataType="json" type="GET"	contentType="application/json" />
								<kendo:dataSource-transport-read url="${transportNestedReadUrl}/#=personId#" dataType="json" type="POST" contentType="application/json" />
								<kendo:dataSource-transport-update url="${updateUrl}/#=personId#" dataType="json" type="GET" contentType="application/json" />
								<kendo:dataSource-transport-destroy url="${destroyUrl}/"	dataType="json" type="GET" contentType="application/json" />
							</kendo:dataSource-transport>
							<kendo:dataSource-schema>
								<kendo:dataSource-schema-model id="stId">
									<kendo:dataSource-schema-model-fields>
										<kendo:dataSource-schema-model-field name="stId" type="number"	editable="false" />
										<kendo:dataSource-schema-model-field name="pn_Name">
											<kendo:dataSource-schema-model-field-validation	required="true" />
										</kendo:dataSource-schema-model-field>
										<kendo:dataSource-schema-model-field name="ptSlno" type="number" defaultValue="">
											<kendo:dataSource-schema-model-field-validation	max="1000" min="1"  />
										</kendo:dataSource-schema-model-field>
										<kendo:dataSource-schema-model-field name="trainingName" type="string">
											<%-- <kendo:dataSource-schema-model-field-validation	required="true"  pattern="^.{1,45}$"/> --%>
										</kendo:dataSource-schema-model-field>
										<kendo:dataSource-schema-model-field name="trainedBy" type="string">
											<kendo:dataSource-schema-model-field-validation min="1"  pattern="^.{0,100}$"/>
										</kendo:dataSource-schema-model-field>
										<kendo:dataSource-schema-model-field name="fromDate" type="date" defaultValue="">
											<%-- <kendo:dataSource-schema-model-field-validation	required="true" /> --%>
										</kendo:dataSource-schema-model-field>
										<kendo:dataSource-schema-model-field name="toDate" type="date"	defaultValue="">
											<%-- <kendo:dataSource-schema-model-field-validation	required="true" /> --%>
										</kendo:dataSource-schema-model-field>
										<kendo:dataSource-schema-model-field name="trainingDesc" type="string"/>
										<kendo:dataSource-schema-model-field name="certificationAch" type="string"/>
										<kendo:dataSource-schema-model-field name="createdBy" type="string"/>
										<kendo:dataSource-schema-model-field name="lastUpdatedBy" type="string"/>
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
<div id="uploadDialog" title="Upload Training Certificate" style="display: none;">
	<kendo:upload  name="files" multiple="false" upload="uploadExtraData" success="onDocSuccess">
		<kendo:upload-async autoUpload="true" saveUrl="./manpower/training/upload" />
   </kendo:upload>
</div> 
<div id="alertsBox" title="Alert"></div> 
<script>

/* function parse (response) {   
    $.each(response, function (idx, elem) {
        if (elem.fromDate && typeof elem.fromDate === "string") {
            elem.fromDate = kendo.parseDate(elem.fromDate, "yyyy-MM-ddTHH:mm:ss.fffZ");
        }
        if (elem.toDate && typeof elem.toDate === "string") {
            elem.toDate = kendo.parseDate(elem.toDate, "yyyy-MM-ddTHH:mm:ss.fffZ");
        }
    });
    return response;
}   */

var SelectedRowId = "";
var stId = "";
var staffTraingName = new Array();
var staffExpSerialNo = new Array();
function onChange(arg) {
	 var gview = $("#grid").data("kendoGrid");
 	 var selectedItem = gview.dataItem(gview.select());
 	 SelectedRowId = selectedItem.personId;
 	 this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
     // alert("Selected: " + SelectedRowId);
}

function trainingEvent(e)
{
	
 	var trainingName = $('input[name="trainingName"]').val();
 	var ptSlno = $('input[name="ptSlno"]').val();
	if (e.model.isNew()) 
    {
		securityCheckForActions("./manpower/stafftraining/createButton");
		
		staffTraingName=[];
		$.ajax({
		     url:'./manpower/stafftrainingNameUnique/'+"StaffTraining/"+"trainingName/"+"personId/"+SelectedRowId,
		     dataType : "JSON",
		     async: false,
		     success: function (response) {
		    	 
		    	 for(var i = 0; i<response.length; i++) {
		    		 staffTraingName[i] = response[i];
				} 
		    }
		     
		});
		
		
		staffExpSerialNo=[];
		$.ajax({
		     url:'./manpower/stafftrainingNameUnique/'+"StaffTraining/"+"ptSlno/"+"personId/"+SelectedRowId,
		     dataType : "JSON",
		     async: false,
		     success: function (response) {
		    	 
		    	 for(var i = 0; i<response.length; i++) {
		    		 staffExpSerialNo[i] = response[i];
				} 
		    }
		     
		});
		
		
		/* var addUrl = "./manpower/stafftraining/createButton";
		var gridName = "#grid_"+SelectedRowId;
		
		addAccess(addUrl,gridName); */
		$(".k-window-title").text("Add New Staff Training");
		$(".k-grid-update").text("Save");	
    }
	else{
		securityCheckForActions("./manpower/stafftraining/updateButton");
		/* var editUrl = "./manpower/stafftraining/updateButton";
		var gridName = "#grid_"+SelectedRowId;
		
		editAccess(editUrl,gridName);
		  */
		  staffTraingName=[];
			$.ajax({
			     url:'./manpower/stafftrainingNameUnique/'+"StaffTraining/"+"trainingName/"+"personId/"+SelectedRowId,
			     dataType : "JSON",
			     async: false,
			     success: function (response) {
			     	 var j = 0;
			    	 
			    	 for(var i = 0; i<response.length; i++) {
			    		 if (response[i] != trainingName) {
			    			 staffTraingName[j] = response[i];
					       j++;
			    		 }
			    	 }
			    }
			     
			});
			
			staffExpSerialNo=[];
			$.ajax({
			     url:'./manpower/stafftrainingNameUnique/'+"StaffTraining/"+"ptSlno/"+"personId/"+SelectedRowId,
			     dataType : "JSON",
			     async: false,
			     success: function (response) {
			    	 var j = 0;
			    	 
			    	 for(var i = 0; i<response.length; i++) {
			    		 if (response[i] != ptSlno) {
			    		 staffExpSerialNo[j] = response[i];
					       j++;
					     
					} 
			    }
			   }
			     
			});
		 	
			
		  
		$(".k-window-title").text("Edit Staff Training");
	}
	
	//CLIENT SIDE VALIDATION FOR MULTI SELECT
	
	/* $(".k-grid-update").click(function() {
			var fromDate = $('input[name="fromDate"]').val();
			var toDate = $('input[name="toDate"]').val();

			if ((new Date(fromDate).getTime() < new Date(toDate).getTime())) {
				alert("To date should not be less than from date");
				return false;
			}
	}); */
	
}

function removeTraining(){
	securityCheckForActions("./manpower/stafftraining/deleteButton");
	
	var conf = confirm("Are u Sure want to delete this Training Record?");
	if(!conf){
		$('#grid_'+SelectedRowId).data().kendoGrid.dataSource.read({
			personId : SelectedRowId
		});
		 throw new Error('deletion aborted');
	}
	 
}


function trainingDescEditor(container, options) {
	$(
			'<textarea maxlength="300" data-text-field="trainingDesc" data-value-field="trainingDesc" data-bind="value:' + options.field + '" style="width: 148px; height: 40px;"/>')
			.appendTo(container);
}

function uploadExtraData(e)
{		
		var files = e.files;
        // Check the extension of each file and abort the upload if it is not .jpg
        $.each(files, function() {
        	if (this.extension.toLowerCase() == ".pdf") {
        		e.data = {stId: stId, type : this.extension};
			}
        	else if (this.extension.toLowerCase() == ".doc") {
        		e.data = {stId: stId, type : this.extension};
        	}
        	else if (this.extension.toLowerCase() == ".docx") {
        		e.data = {stId: stId, type : this.extension};
			}
        	else if (this.extension.toLowerCase() == ".csv") {
        		e.data = {stId: stId, type : this.extension};
			}
        	else if (this.extension.toLowerCase() == ".xls") {
        		e.data = {stId: stId, type : this.extension};
			}
        	else if (this.extension.toLowerCase() == ".xlsx") {
				e.data = {stId : stId , type : this.extension };
			}
        	else {
            	alert("Invalid Document Type:\nAcceptable formats are pdf, doc, docx, csv and xls");
				e.preventDefault();
				return false;
			}
        });	  
} 

function certiAchEditor(container, options) {

	var data = [ {
		text : "Yes",
		value : "Yes"
	}, {
		text : "No",
		value : "No"
	}];

	$(
			'<input name="Certification Acheived" data-text-field="text"  data-value-field="value" data-bind="value:' + options.field + '" required="required"/>')
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
	$('<span class="k-invalid-msg" data-for="certificationAch"></span>').appendTo(container);
}


function uploadCertificate()
{
	
	securityCheckForActions("./manpower/stafftraining/updateButton");
	
	/* var editUrl = "./manpower/stafftraining/updateButton";
	var gridName = "#grid_"+SelectedRowId;
	editAccess(editUrl,gridName); 
	 */
	var gview = $("#grid_"+SelectedRowId).data("kendoGrid");
	var selectedItem = gview.dataItem(gview.select());
	  if (selectedItem != null) {
	   stId = selectedItem.stId;
	  }
	$('#uploadDialog').dialog({
		modal : true,
	});
	return false;
}

function downloadFile()
{
	
	securityCheckForActions("./manpower/stafftraining/updateButton");
	
	/* var editUrl = "./manpower/stafftraining/updateButton";
	var gridName = "#grid_"+SelectedRowId;
	editAccess(editUrl,gridName);  */
	
	 var gview = $("#grid_"+SelectedRowId).data("kendoGrid");
		//Getting selected item
		var selectedItem = gview.dataItem(gview.select());
		window.open("./manpower/train/download/"+selectedItem.stId);
		/* $.ajax({
			 type : "POST",
			 url :"./download/"+selectedItem.drId,
			 success : function(response)
			 {
				 alert(response);
			 }
			}); */
}

function onDocSuccess(e)
{
	alert("Uploaded Successfully !!!");
	$(".k-upload-files.k-reset").find("li").remove();
	$(".k-upload-status-total").remove();
}


	
	var flagUserId = "";


	function edit(e) {
		$(".k-window-title").text("Edit Staff Details");
	}

	$("#grid").on("click", ".k-grid-add", function() {
		$(".k-grid-update").text("Save");
	});
	
	

	function onRequestStart(e) {
		
		
		$('.k-grid-update').hide();
		$('.k-edit-buttons')
				.append(
						'<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
		$('.k-grid-cancel').hide();
		var res = (e.sender.options.transport.read.url).split("/");
		/* var st = $('#grid_' + res[res.length - 1]).data("kendoGrid");
		if(st != null)
		{
			st.cancelRow();
		} */
	}
	
	function onRequestEnd(e) {
		/* debugger; */

		
	if (typeof e.response != 'undefined')
	{
		if (e.response.status == "FAIL") 
		{

			errorInfo = "";

			for (var s = 0; s < e.response.result.length; s++) {
				errorInfo += (s + 1) + ". "
						+ e.response.result[s].defaultMessage + "<br>";

			}

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
				$('#grid_' + SelectedRowId).data().kendoGrid.dataSource
				.read({
					personId : SelectedRowId
				});

			}

			if (e.type == "update") {
				//alert("Error: Updating the USER record\n\n" + errorInfo);
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
				$('#grid_' + SelectedRowId).data().kendoGrid.dataSource
				.read({
					personId : SelectedRowId
				});
			}

			
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
				$('#grid_' + SelectedRowId).data().kendoGrid.dataSource
				.read({
					personId : SelectedRowId
				});
			}
			
			
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
					$('#grid_' + SelectedRowId).data().kendoGrid.dataSource
					.read({
						personId : SelectedRowId
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
					$('#grid_' + SelectedRowId).data().kendoGrid.dataSource
					.read({
						personId : SelectedRowId
					});
				}

			
			return false;
		}

		else if (e.type == "create") {

			$("#alertsBox").html("");
			$("#alertsBox").html("Staff Training Record created successfully");
			$("#alertsBox").dialog({
				modal : true,
				buttons : {
					"Close" : function() {
						$(this).dialog("close");
					}
				}
			});
			var res = (e.sender.options.transport.read.url).split("/");
			var grid = $('#grid_' + res[res.length - 1]).data("kendoGrid");
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
			$("#alertsBox").html("Staff Training Record updated successfully");
			$("#alertsBox").dialog({
				modal : true,
				buttons : {
					"Close" : function() {
						$(this).dialog("close");
					}
				}
			});
			var res = (e.sender.options.transport.read.url).split("/");
			var grid = $('#grid_' + res[res.length - 1]).data("kendoGrid");
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
			$('#grid_' + SelectedRowId).data().kendoGrid.dataSource
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
				                 addressSeasonTo11: function (input, params) 
					             {
				                     if (input.filter("[name = 'toDate']").length && input.val() != "") 
				                     {                          
				                         var flagDate = false;

				                         seasonFromAddress = $('input[name="fromDate"]').val();
				                         
				                         if (seasonFromAddress != "") 
				                         {
				                                flagDate = true;
				                         }
				                         return flagDate;
				                     }
				                     return true;
				                 },
				                 addressSeasonTo22: function (input, params) 
					             {
				                     if (input.filter("[name = 'toDate']").length && input.val() != "") 
				                     {                          
				                         var selectedDate = input.val();
				                         var flagDate = false;

				                         seasonFromAddress = $('input[name="fromDate"]').val();
				                         
				                         if ($.datepicker.parseDate('dd/mm/yy', selectedDate) > $.datepicker.parseDate('dd/mm/yy', seasonFromAddress)) 
				                         {
				                                flagDate = true;
				                         }
				                         return flagDate;
				                     }
				                     return true;
				                 },
				              
								trainedByvalidation : function(input,
										params) {
									if (input.filter("[name='trainedBy']").length
											&& input.val()) {
										return /^[a-z\d\-_.\s]+$/i
												.test(input.val());
									}
									return true;
								},
								
								fromDateNullvalidation : function(input, params){
				                      if (input.attr("name") == "fromDate")
				                      {
				                       return $.trim(input.val()) !== "";
				                      }
				                      return true;
				                     },
			                     toDateNullvalidation : function(input, params){
				                      if (input.attr("name") == "toDate")
				                      {
				                       return $.trim(input.val()) !== "";
				                      }
				                      return true;
				                     },
							
								trainingNameNullvalidation : function(input, params){
				                      if (input.attr("name") == "trainingName")
				                      {
				                       return $.trim(input.val()) !== "";
				                      }
				                      return true;
				                     },
								trainingNamevalidation : function(input,
										params) {
									if (input.filter("[name='trainingName']").length
											&& input.val()) {
										return /^[a-z\d\-_.\s]+$/i
												.test(input.val());
									}
									return true;
								},
								
								certificationAchValidation : function(input,
										params) {
									if (input.filter("[name='certificationAch']").length
											&& input.val()) {
										return /^[a-z\d\-_.\s]+$/i
												.test(input.val());
									}
									return true;
								},
								ptSlnoEmpty : function(input, params){
				                      if (input.attr("name") == "ptSlno")
				                      {
				                       return $.trim(input.val()) !== "";
				                      }
				                      return true;
				                     },
				                     
				                     TrainingNameUniqueness : function(input,params){
						        	    if (input.filter("[name='trainingName']").length && input.val()) {
									         var enterdService = input.val().toUpperCase(); 
										          for(var i = 0; i<staffTraingName.length; i++) 
										          {
										            if ((enterdService == staffTraingName[i].toUpperCase()) && (enterdService.length == staffTraingName[i].length) ) 
										            {								            
										              return false;								          
										            }
										          }
									         }
								    return true;
							    },
							     SerialNoUniqueness : function(input,params){
								        if (input.filter("[name='ptSlno']").length && input.val()) {
								         var enterdService = input.val(); 
								         
								     
									          for(var i = 0; i<staffExpSerialNo.length; i++) 
									          {
									            if ((enterdService == staffExpSerialNo[i])) 
									            {	
									              return false;								          
									            }
									          }
								         }
							    return true;
							    },
							},
							messages : {
								//custom rules messages
								addressSeasonTo11:"Select From date first before selecting To date and change To date accordingly",
								addressSeasonTo22:"To date should be more than From date",
								trainedByvalidation : "Trained By should not contain any special characters",
								certificationAchValidation : "Description should not contain any special characters",
								ptSlnoEmpty : "Serial Number is required",
								fromDateNullvalidation:"From Date is required",
								toDateNullvalidation:"To Date is required",
								trainingNameNullvalidation: "Training Name is required",
								trainingNamevalidation:"Training Name should not contain any special characters",
								TrainingNameUniqueness:"Training Name Already Exists",
								SerialNoUniqueness:"Serial No Already Exists",
								
							}
						});

	})(jQuery, kendo);
	//End Of Validation
	
	
</script>


<style type="text/css">
#details-container {
	padding: 10px;
}

#details-container h2 {
	margin: 0;
}

#details-container em {
	color: #8c8c8c;
}

#details-container dt {
	margin: 0;
	display: inline;
}

.k-upload-button input { z-index: 10000 }

</style>