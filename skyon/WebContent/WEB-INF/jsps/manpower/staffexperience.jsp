<%@include file="/common/taglibs.jsp"%>

	<c:url value="/manpower/experience/expDetails"	var="transportNestedReadUrl" />
	<c:url value="/manpower/experience/read" var="readUrl" />
	<c:url value="/manpower/experience/create" var="createUrl" />
	<c:url value="/manpower/experience/update" var="updateUrl" />
	<c:url value="/manpower/experience/destroy" var="destroyUrl" />
	<c:url value="/manpower/getPerson" var="personUrl" />
	
	<kendo:grid name="gridExp" pageable="true" detailTemplate="templateExp" resizable="true" sortable="true" selectable="true" scrollable="true" filterable="true" groupable="true" change="onChanges">
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10"></kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
			<kendo:grid-filterable-operators>
				<kendo:grid-filterable-operators-string eq="Is equal to" />
			</kendo:grid-filterable-operators>
		</kendo:grid-filterable>
		<kendo:grid-columns>
			<kendo:grid-column title="Staff Name" field="pn_Name" width="75%">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function personNameFilter(element) {
								element.kendoAutoComplete({
									dataSource : {
										transport : {
											read : "${personUrl}"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>

			<kendo:grid-column title="Total Experience"	field="total_exp" width="25%" filterable="false" />
			<kendo:grid-column title="Person Id" field="personId" width="100px" hidden="true" />
			<kendo:grid-column title="Staff DoB" field="dob" hidden="true" format="{0:dd/MM/yyyy}"/>
			<kendo:grid-column title="Staff Language" field="lang" hidden="true"/>
			<kendo:grid-column title="Staff Occupation" field="occu" hidden="true"/>
			<kendo:grid-column title="Staff Type" field="personType" hidden="true"/>
			<kendo:grid-column title="Staff Title" field="title" hidden="true"/>
			<kendo:grid-column title="Staff Father Name" field="fatherName"	hidden="true"/>
			<kendo:grid-column title="Staff Designation" field="designation" hidden="true"/>
			<kendo:grid-column title="Staff Department" field="department"	hidden="true"/>
			<kendo:grid-column title="Staff Type" field="staffType"	hidden="true"/>
			<kendo:grid-column title="Staff Login Name" field="loginName" hidden="true"/>
			<kendo:grid-column title="Staff Status" field="userStatus" hidden="true"/>
			<kendo:grid-column title="Staff Roles" field="roles" hidden="true"/>
			<kendo:grid-column title="Staff Groups" field="groups"	hidden="true"/>
		</kendo:grid-columns>
		<kendo:dataSource pageSize="20">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-read url="${readUrl}" dataType="json"	type="POST" contentType="application/json" />
			</kendo:dataSource-transport>
			<kendo:dataSource-schema>
				<kendo:dataSource-schema-model id="personId">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="pn_Name"><kendo:dataSource-schema-model-field-validation required="true" /></kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="total_exp"	type="string" editable="false"/>
						<kendo:dataSource-schema-model-field name="personId" type="number" editable="false"/>
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
	</kendo:grid>

	<kendo:grid-detailTemplate id="templateExp">
		<kendo:tabStrip name="tabStripExp_#=personId#">
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
				<kendo:tabStrip-item text="Staff Experience" selected="true">
					<kendo:tabStrip-item-content>

						<kendo:grid  name="gridExp_#=personId#" resizable="true" pageable="true" sortable="true"  scrollable="true" edit="experienceEvent" groupable="true" remove="deleteSe">
							<kendo:grid-editable mode="popup" confirmDelete="true"/>
							<kendo:grid-toolbar>
								<kendo:grid-toolbarItem name="create" text="Add Staff Experience" />
							</kendo:grid-toolbar>
							<kendo:grid-columns>
								<kendo:grid-column title="Serial No. *" field="pwSlno" width="100px" format="{0:n0}" />
								<kendo:grid-column title="Previous Company *" field="company" width="130px" />
								<kendo:grid-column title="Designation *" field="designation" width="100px" />
								<kendo:grid-column title="From Date *" field="startDate" width="100px" format="{0:dd/MM/yyyy}" />
								<kendo:grid-column title="To Date *" field="endDate" width="100px" format="{0:dd/MM/yyyy}" />
								<kendo:grid-column title="Work Description" field="workDesc" width="140px" editor="workDescEditor"/>
								<kendo:grid-column title="&nbsp;" width="160px">
									<kendo:grid-column-command>
										<kendo:grid-column-commandItem name="edit" />
										<kendo:grid-column-commandItem name="destroy" />
									</kendo:grid-column-command>
								</kendo:grid-column>
							</kendo:grid-columns>
							<kendo:dataSource pageSize="5" requestEnd="onRequestEnd" requestStart="onRequestStart">
								<kendo:dataSource-transport>
									<kendo:dataSource-transport-create url="${createUrl}/#=personId#" dataType="json" type="GET" contentType="application/json" />
									<kendo:dataSource-transport-read url="${transportNestedReadUrl}/#=personId#" dataType="json" type="POST" contentType="application/json" />
									<kendo:dataSource-transport-update url="${updateUrl}/#=personId#" dataType="json" type="GET" contentType="application/json" />
									<kendo:dataSource-transport-destroy  url="${destroyUrl}/" dataType="json" type="GET" contentType="application/json" />
								</kendo:dataSource-transport>
								<kendo:dataSource-schema >
									<kendo:dataSource-schema-model id="seId">
										<kendo:dataSource-schema-model-fields>
											<kendo:dataSource-schema-model-field name="seId" type="number" editable="false"/>
											<kendo:dataSource-schema-model-field name="pwSlno"	type="number"><kendo:dataSource-schema-model-field-validation max="1000"  min="1" /></kendo:dataSource-schema-model-field>
											<kendo:dataSource-schema-model-field name="company"	type="string"><kendo:dataSource-schema-model-field-validation	required="true"  pattern="^.{0,90}$"/></kendo:dataSource-schema-model-field>
											<kendo:dataSource-schema-model-field name="designation"	type="string"><kendo:dataSource-schema-model-field-validation required="true"  pattern="^.{0,90}$"/></kendo:dataSource-schema-model-field>
											<kendo:dataSource-schema-model-field name="startDate" type="date" defaultValue=""><kendo:dataSource-schema-model-field-validation /></kendo:dataSource-schema-model-field>
											<kendo:dataSource-schema-model-field name="endDate"	type="date" defaultValue=""><kendo:dataSource-schema-model-field-validation /></kendo:dataSource-schema-model-field>
											<kendo:dataSource-schema-model-field name="workDesc" type="string"/>
											<kendo:dataSource-schema-model-field name="createdBy" type="string"/>
											<kendo:dataSource-schema-model-field name="lastUpdatedBy"	type="string"/>
											<kendo:dataSource-schema-model-field name="lastUpdateDate"	type="date" editable="false" />
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
 <div id="alertsBox" title="Alert"></div>
<script>

/* function parse (response) {   
    $.each(response, function (idx, elem) {
        if (elem.startDate && typeof elem.startDate === "string") {
            elem.startDate = kendo.parseDate(elem.startDate, "yyyy-MM-ddTHH:mm:ss.fffZ");
        }
        if (elem.endDate && typeof elem.endDate === "string") {
            elem.endDate = kendo.parseDate(elem.endDate, "yyyy-MM-ddTHH:mm:ss.fffZ");
        }
    });
    return response;
}   */

var staffExpSerialNo = new Array();

var SelectedRowId = "";
function onChanges(arg) {
	 var gview = $("#gridExp").data("kendoGrid");
	 var selectedItem = gview.dataItem(gview.select());
	 SelectedRowId = selectedItem.personId;
	 this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
    // alert("Selected: " + SelectedRowId);
}


function experienceEvent(e)
{
	var pwSlno = $('input[name="pwSlno"]').val();
	
	if (e.model.isNew()) 
    {
		securityCheckForActions("./manpower/staffexperience/createButton");
		
		staffExpSerialNo=[];
		$.ajax({
		     url:'./manpower/stafftrainingNameUnique/'+"StaffExperience/"+"pwSlno/"+"personId/"+SelectedRowId,
		     dataType : "JSON",
		     async: false,
		     success: function (response) {
		    	 
		    	 for(var i = 0; i<response.length; i++) {
		    		 staffExpSerialNo[i] = response[i];
				} 
		    }
		     
		});
	 	
		
		/*  var addUrl = "./manpower/staffexperience/createButton";
		var gridName = "#gridExp_"+SelectedRowId;
		
		addAccess(addUrl,gridName);   */
		
		$(".k-window-title").text("Add New Staff Experience");
		$(".k-grid-update").text("Save");	
    }
	else{
		
		securityCheckForActions("./manpower/staffexperience/updateButton");
		
		 /* var editUrl = "./manpower/staffexperience/updateButton";
		var gridName = "#gridExp_"+SelectedRowId;
		
		editAccess(editUrl,gridName);   */
		staffExpSerialNo=[];
		$.ajax({
		     url:'./manpower/stafftrainingNameUnique/'+"StaffExperience/"+"pwSlno/"+"personId/"+SelectedRowId,
		     dataType : "JSON",
		     async: false,
		     success: function (response) {
		    	 var j = 0;
		    	 
		    	 for(var i = 0; i<response.length; i++) {
		    		 if (response[i] != pwSlno) {
		    		 staffExpSerialNo[j] = response[i];
				       j++;
				     
				} 
		    }
		   }
		     
		});
	 	
	 
		
		
		$(".k-window-title").text("Edit Staff Experience");
	}

	} 
	
	function deleteSe(e){
		securityCheckForActions("./manpower/staffexperience/deleteButton");
		var conf = confirm("Are u Sure want to delete this Experience Record?");
		if(!conf){
			$('#gridExp_'+SelectedRowId).data().kendoGrid.dataSource.read({
				personId : SelectedRowId
			});
			 throw new Error('deletion aborted');
		}
		 
	}

	function personEditor(container, options) {

		var personName = "";
		var gview = $("#gridExp").data("kendoGrid");
		var selectedItem = gview.dataItem(gview.select());
		if (selectedItem != null) {
			personName = selectedItem.pn_Name;
		}

		$('<input data-text-field="pn_Name"  data-value-field="pn_Id" data-bind="value:' + options.field + '"/>')
				.appendTo(container).kendoComboBox({

					optionLabel : {
						pn_Name : "Select Person",

					},
					defaultValue : false,
					sortable : true,
					dataSource : {
						transport : {
							read : "${personUrl}?personSelected=" + personName
						}
					}
				});
	}

	function workDescEditor(container, options) {
		$(
				'<textarea maxlength="300" data-text-field="workDesc" data-value-field="workDesc" data-bind="value:' + options.field + '" style="width: 148px; height: 40px;"/>')
				.appendTo(container);
	}
	
 	function onRequestStart(e) {

 		$('.k-grid-update').hide();
		$('.k-edit-buttons')
				.append(
						'<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
		$('.k-grid-cancel').hide();
 		
		var res = (e.sender.options.transport.read.url).split("/");
		/* var se = $('#gridExp_' + res[res.length - 1]).data("kendoGrid");
		if(se != null)
		{
			se.cancelRow();
		} */
	} 

 	function onRequestEnd(e) {
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

				  $('#gridExp_' + SelectedRowId).data().kendoGrid.dataSource.read({
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
				
				$('#gridExp_'+SelectedRowId).data().kendoGrid.dataSource.read({
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
				
				$('#gridExp_' + SelectedRowId).data().kendoGrid.dataSource.read({
					personId : SelectedRowId
				});
				return false;
			}

			else if (e.type == "create") {

				$("#alertsBox").html("");
				$("#alertsBox")
						.html("Staff Experience Record created successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});

				var res = (e.sender.options.transport.read.url).split("/");
				var grid = $('#gridExp_' + res[res.length - 1]).data("kendoGrid");
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
						.html("Staff Experience Record updated successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});

				var res = (e.sender.options.transport.read.url).split("/");
				var grid = $('#gridExp_' + res[res.length - 1]).data("kendoGrid");
				if(grid != null){
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
				$('#gridExp_' + SelectedRowId).data().kendoGrid.dataSource
				.read({
					personId : SelectedRowId
				});
			}
		}
	}
	var seasonFromContact = "";
	var seasonToContact = "";

	(function($, kendo) {
		$
				.extend(
						true,
						kendo.ui.validator,
						{
							rules : { // custom rules 
								
								addressSeasonFrom: function (input, params) 
					             {
				                     if (input.filter("[name = 'startDate']").length && input.val() != "") 
				                     {                          
				                         var selectedDate = input.val();
				                         var todaysDate = $.datepicker.formatDate('dd/mm/yy', new Date());
				                         var flagDate = false;

				                         if ($.datepicker.parseDate('dd/mm/yy', selectedDate) < $.datepicker.parseDate('dd/mm/yy', todaysDate)) 
				                         {
				                        	 	seasonFromAddress = selectedDate;
				                                flagDate = true;
				                         }
				                         return flagDate;
				                     }
				                     return true;
				                 },
				                 addressSeasonTo1: function (input, params) 
					             {
				                     if (input.filter("[name = 'endDate']").length && input.val() != "") 
				                     {                          
				                         var flagDate = false;

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
				                     if (input.filter("[name = 'endDate']").length && input.val() != "") 
				                     {                          
				                         var selectedDate = input.val();
				                         var flagDate = false;

				                         if ($.datepicker.parseDate('dd/mm/yy', selectedDate) > $.datepicker.parseDate('dd/mm/yy', seasonFromAddress)) 
				                         {
				                                flagDate = true;
				                         }
				                         return flagDate;
				                     }
				                     return true;
				                 },
				                 addressSeasonTo3: function (input, params) 
					             {
				                     if (input.filter("[name = 'startDate']").length && input.val() != "") 
				                     {                          
				                    	 var todaysDate = $.datepicker.formatDate('dd/mm/yy', new Date());
				                         var flagDate = false;

				                         if ($.datepicker.parseDate('dd/mm/yy', todaysDate) > $.datepicker.parseDate('dd/mm/yy', seasonFromAddress)) 
				                         {
				                                flagDate = true;
				                         }
				                         return flagDate;
				                     }
				                     return true;
				                 },
								companyNamevalidation : function(input, params) {
									if (input.filter("[name='company']").length
											&& input.val()) {
										return /^[a-z\d\-_.\s]+$/i.test(input
												.val());
									}
									return true;
								},
								designationNamevalidation : function(input,
										params) {
									if (input.filter("[name='designation']").length
											&& input.val()) {
										return /^[a-z\d\-_.\s]+$/i.test(input
												.val());
									}
									return true;
								},
								workDescValidation : function(input, params) {
									if (input.filter("[name='workDesc']").length
											&& input.val()) {
										return /^[a-z\d\-_.\s]+$/i.test(input
												.val());
									}
									return true;
								},
								startDate : function(input, params) {
									if (input.filter("[name = 'startDate']").length
											&& input.val()) {
										var selectedDate = input.val();
										var todaysDate = $.datepicker
												.formatDate('dd/mm/yy',
														new Date());
										var flagDate = false;

										if ($.datepicker.parseDate('dd/mm/yy',
												selectedDate) < $.datepicker
												.parseDate('dd/mm/yy',
														todaysDate)) {
											flagDate = true;
										}
										return flagDate;
									}
									return true;
								},
								endDate : function(input, params) {
									if (input.filter("[name = 'endDate']").length
											&& input.val()) {
										var selectedDate = input.val();
										var todaysDate = $.datepicker
												.formatDate('dd/mm/yy',
														new Date());
										var flagDate = false;

										if ($.datepicker.parseDate('dd/mm/yy',
												selectedDate) < $.datepicker
												.parseDate('dd/mm/yy',
														todaysDate)) {
											flagDate = true;
										}
										return flagDate;
									}
									return true;
								},
								pwSlnoEmpty : function(input, params){
				                      if (input.attr("name") == "pwSlno")
				                      {
				                       return $.trim(input.val()) !== "";
				                      }
				                      return true;
				                 },
				                startDateEmpty : function(input, params){
					                      if (input.attr("name") == "startDate")
					                      {
					                       return $.trim(input.val()) !== "";
					                      }
					                      return true;
					               },
					            endDateEmpty : function(input, params){
						                      if (input.attr("name") == "endDate")
						                      {
						                       return $.trim(input.val()) !== "";
						                      }
						                      return true;
						         },
						         
						         SerialNoUniqueness : function(input,params){
								        if (input.filter("[name='pwSlno']").length && input.val()) {
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
								addressSeasonFrom:"From Date must be selected in the Past",
								addressSeasonTo1:"Select From date first before selecting To date and change To date accordingly",
								addressSeasonTo2:"To date should be more than From date",
								addressSeasonTo3:"To date must be selected in the Past",
								companyNamevalidation : "Company name should not contain any special characters",
								designationNamevalidation : "Designation should not contain any special characters",
								workDescValidation : "Description should not contain any special characters",
								startDate : "From date must be selected from the past",
								endDate : "To date must be selected from the past",
								pwSlnoEmpty : "Serial Number is required",
								startDateEmpty : "From Date is required",
								endDateEmpty : "End Date is required",
								SerialNoUniqueness:"Serial No Already Exists",
							}
						});

	})(jQuery, kendo);
	//End Of Validation
</script>
<style type="text/css">
.red {
	color: red;
}
</style>
