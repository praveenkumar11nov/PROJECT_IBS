<%@include file="/common/taglibs.jsp"%>


<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<c:url value="/advanceBills/billRead" var="advanceBillReadUrl" />
<c:url value="/electricityBills/billCreate"
	var="electricityBillCreateUrl" />


<c:url value="/electricityBills/billLineItemRead"
	var="elBillLineItemReadUrl" />
<c:url value="/electricityBills/billLineItemCreate"
	var="elSubLedgerCreateUrl" />

<c:url value="/electricityBills/billParameterRead"
	var="elBillParameterReadUrl" />
<c:url value="/electricityBills/billParameterCreate"
	var="elBillParameterCreateUrl" />

<c:url value="/common/getAllChecks" var="allChecksUrl" />
<c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />

<!-- Filters Data url's -->

<c:url value="/bills/filter" var="commonFilterForBillUrl" />
<c:url value="/billLineItem/filter" var="commonFilterForBillLineItemUrl" />
<c:url value="//billParameter/filter"
	var="commonFilterForBillParameterUrl" />

<c:url
	value="/commonServiceTransactionMaster/getToTransactionMasterComboBoxUrl"
	var="toTransactionMasterComboBoxUrl" />
<c:url
	value="/commonServiceTransactionMaster/getToBillParaMeterMasteMasterComboBoxUrl"
	var="toBillParaMeterMasterComboBoxUrl" />
<c:url value="/openNewTickets/readTowerNames" var="towerNames" />

<c:url value="/openNewTickets/readPropertyNumbers" var="propertyNum" />

<kendo:grid name="abbillGrid"  resizable="true"
	pageable="true" selectable="true" 
	edit="electricityBillEvent" sortable="true" scrollable="true"
	groupable="true">
	<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10"></kendo:grid-pageable>
	<kendo:grid-filterable extra="false">
		<kendo:grid-filterable-operators>
			<kendo:grid-filterable-operators-string eq="Is equal to" />
		</kendo:grid-filterable-operators>

	</kendo:grid-filterable>
	<kendo:grid-editable mode="popup"
		confirmation="Are you sure you want to remove this Bill Detail?" />
	<kendo:grid-toolbar>
		<%-- <kendo:grid-toolbarItem name="create" text="Add New Bill" /> --%>
		<kendo:grid-toolbarItem text="Clear_Filter" />
		<kendo:grid-toolbarItem name="generateBill" text="Generate Bill" />
		
	</kendo:grid-toolbar>
	<kendo:grid-columns>
		<kendo:grid-column title="Avg Id" field="avgId"
			width="100px" hidden="true" />

		<kendo:grid-column title="Account&nbsp;No" field="accountNo"
			filterable="true" width="90px">
		</kendo:grid-column>

		<kendo:grid-column title="Account&nbsp;No" field="accountId"
			filterable="true" width="100px" hidden="true">
		</kendo:grid-column>

		<kendo:grid-column title="Service&nbsp;Type" field="typeOfService"
			filterable="true" width="100px" editor="dropDownChecksEditor">
			<%-- <kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function typeOfServiceFilter(element) {
							element
									.kendoAutoComplete({
										placeholder : "Enter Service Type",
										dataType : 'JSON',
										dataSource : {
											transport : {
												read : "${commonFilterForServicePointUrl}/typeOfService"
											}
										}
									});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable> --%>
		</kendo:grid-column>

		

		

		<kendo:grid-column title="Average Month" field="abBillDate"
		format="{0:dd/MM/yyyy }"
			 width="100px">
		</kendo:grid-column>

		<kendo:grid-column title="Average&nbsp;Amount" field="avgAmount"
			format="{0:#.00}" width="90px" filterable="true">
		</kendo:grid-column>
		
		<kendo:grid-column title="Average&nbsp;Type" field="avgType"
			width="90px" filterable="true" editor="dropDownChecksEditor">
			<%-- <kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function postTypeFilter(element) {
							element
									.kendoAutoComplete({
										placeholder : "Enter Post Type",
										dataSource : {
											transport : {
												read : "${commonFilterForBillUrl}/avgType"
											}
										}
									});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable> --%>
		</kendo:grid-column>
          
		
		<kendo:grid-column title="Bill Status" field="status" width="80px">
			<%-- <kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function ledgerStatusFilter(element) {
							element
									.kendoDropDownList({
										optionLabel : "Select status",
										dataSource : {
											transport : {
												read : "${filterDropDownUrl}/ledgerStatus"
											}
										}
									});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable> --%>
		</kendo:grid-column>
		
           	<!-- url : "./electricityBills/approveBill/" + elBillId + "/"+ billStatus + "/" + totalBillAmount, -->
		        <kendo:grid-column title=""
				template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Active#=data.avgId#'>#= data.status == 'Generated' ? 'Approve' : 'Approved' #</a>"
				width="100px" /> 
	</kendo:grid-columns>

	<kendo:dataSource pageSize="20" requestEnd="onRequestEnd">
		<kendo:dataSource-transport>
			<%-- <kendo:dataSource-transport-create url="${electricityBillCreateUrl}"
				dataType="json" type="GET" contentType="application/json" /> --%>
			<kendo:dataSource-transport-read url="${advanceBillReadUrl}"
				dataType="json" type="POST" contentType="application/json" />
			<%-- <kendo:dataSource-transport-update url="${elratemasterUpdateUrl}"
					dataType="json" type="GET" contentType="application/json" />
			    <kendo:dataSource-transport-destroy url="${elratemasterDestroyUrl}"
					dataType="json" type="GET" contentType="application/json" /> --%>

		</kendo:dataSource-transport>

		<kendo:dataSource-schema >
			<kendo:dataSource-schema-model id="avgId">
				<kendo:dataSource-schema-model-fields>

					<kendo:dataSource-schema-model-field name="avgId" type="number">
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="typeOfService"
						type="string">
						<kendo:dataSource-schema-model-field-validation required="true" />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="accountNo" type="string">
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="accountId" type="number">
						
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="avgType" type="string">
						<kendo:dataSource-schema-model-field-validation required="true" />
					</kendo:dataSource-schema-model-field>
                    
                   
					<kendo:dataSource-schema-model-field name="noMonth" type="number">
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="units" type="number">
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="abBillDate" type="date">
						
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="abEndDate" type="date">
						
					</kendo:dataSource-schema-model-field>

					<%-- <kendo:dataSource-schema-model-field name="billMonth" type="date">
						<kendo:dataSource-schema-model-field-validation required="true" />
					</kendo:dataSource-schema-model-field> --%>

					<kendo:dataSource-schema-model-field name="avgAmount"
						type="number">
						
					</kendo:dataSource-schema-model-field>

					<%-- <kendo:dataSource-schema-model-field name="cbId" type="number">
						<kendo:dataSource-schema-model-field-validation required="true"
							min="1" />
					</kendo:dataSource-schema-model-field> --%>

					<kendo:dataSource-schema-model-field name="abBillNo" type="string">
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="createdBy">
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="status" editable="false"
						type="string" />
				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>

	</kendo:dataSource>
</kendo:grid>


<style scoped>
#vertical {
	height: 150px;
	width: 100%;
	margin: 0 auto;
	float: left;
}

#middle-pane {
	background-color: rgba(60, 70, 80, 0.10);
}

#bottom-pane {
	background-color: rgba(60, 70, 80, 0.15);
}

#left-pane,#center-pane,#right-pane {
	background-color: rgba(60, 70, 80, 0.05);
}

.pane-content {
	padding: 0 10px;
}
</style>
<div id="alertsBox" title="Alert"></div>
<div id="generateBillDialog" style="display: none;">
	<form id="addform" data-role="validator" novalidate="novalidate">
<table>
<tr>
				<td>
					<table id="previousDiv" style="height: 205px;">
						<!--current Table  -->
						<tr>
						<tr>
							<td>Account Name</td>
							<td><input id="accountNo" name="accountNo"
								onchange="gettariffId()" required="required"
								validationMessage="Select Account No." />
						</tr>
                     <tr>
                     <td>&nbsp;</td>
                     </tr>
						<tr>
							<td>Services</td>
							<td><kendo:dropDownList name="serviceName" id="serviceName"
									cascadeFrom="accountNo" 
									required="required" validationMessage="Select Service Name"></kendo:dropDownList>
							</td>
						</tr>
						<tr>
                     <td>&nbsp;</td>
                     </tr>
						
						<tr>
						<tr>
							<td>Average Type</td>
							<td><kendo:dropDownList name="avgType" id="avgType"									
									required="required" validationMessage="Select Service Name"></kendo:dropDownList>
							</td>
						</tr>
						                     			
						 <tr>
                     <td>&nbsp;</td>
                     </tr>
                     <tr>
						<tr id="billstDate" >
							<td> Bill Start Date</td>
							<td><kendo:datePicker format="dd/MM/yyyy  "
									name="avgDate" id="avgDate" required="required" class="validate[required]" validationMessage="Start Date is Required" >
								</kendo:datePicker></td>
						</tr>
						<tr>
                     <td>&nbsp;</td>
                     </tr>
						<tr >
				<td class="left" align="center" colspan="4" >
				
					<button type="submit" id="generateAdvanceBill" class="k-button"
						style="padding-left: 10px" >Calculate Average</button>
				</td>
			</tr>
						<tr>
						<td>
						</tr>
					</table>

</td></tr>


</table>	
				
</form>
</div>


<script >



/* function approveBill() {
	vavgIdlId = "";
	var gridParameter = $("#abbillGrid").data("kendoGrid");
	var selectedAddressItem = gridParameter
			.dataItem(gridParameter.select());
	avgId = selectedAddressItem.avgId;
	$.ajax({
		type : "POST",
		url : "./advanceBills/approveBill/" + avgId + "/"+ billStatus + "/" + totalBillAmount,
		dataType : "text",
		success : function(response) {
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
			$('#abbillGrid').data('kendoGrid').dataSource.read();
		}
	});
} */

	
	$("#addform").submit(function(e) {
		e.preventDefault();
	});

	var validator = $("#addform").kendoValidator().data("kendoValidator");
	$.validator.addMethod("maxlength", function (value, element, len) {
		   return value == "" || value.length <= len;
		});
	
	$("#generateAdvanceBill").on("click", function() {
	    if (validator.validate()) {
	        // If the form is valid, the Validator will return true
	       generateBill();
	    }
	});
	
	
	 $("#addform").kendoValidator({
         messages: {
             // defines a message for the 'custom' validation rule
             custom: "Please enter valid value for my custom rule",

             // overrides the built-in message for the required rule
             required: "My custom required message",
                 minlen:1
             // overrides the built-in message for the email rule
             // with a custom function that returns the actual message
             
         },
         rules: {
        	 customRule1: function(input){
                 // all of the input must have a value
                 return $.trim(input.val()) !== "";
               },
         }
    });

    function getMessage(input) {
      return input.data("message");
    }
	
	
	function gettariffId() {

		var accountId = $("#accountNo").val();

		$("#serviceName").kendoDropDownList({

			optionLabel : "Select Services ...",
			dataTextField : "typeOfService",
			dataValueField : "serviceMasterId",

			dataSource : {

				transport : {
					read : "./bill/getServiceName?accountId=" + accountId,

				}
			}
		}).data("kendoDropDownList");
	}
	jQuery.fn.ForceNumericOnly = function() {
		return this
				.each(function() {
					$(this)
							.keydown(
									function(e) {
										var key = e.charCode || e.keyCode || 0;
										return (key == 8 || key == 9
												|| key == 13 || key == 46
												|| key == 110 || key == 190
												|| (key >= 35 && key <= 40)
												|| (key >= 48 && key <= 57) || (key >= 96 && key <= 105));
									});
				});
	};

	$("#presentReading").ForceNumericOnly();	

	
	
	
	
	

	$(document)
			.ready(
					function() {

						var autocomplete = $("#accountNo")
						.kendoComboBox(
								{
									filter : "startswith",
									autoBind : false,
									dataTextField : "accountNumber",
									dataValueField : "accountId",
									placeholder : "Select accountno...",

									headerTemplate : '<div class="dropdown-header">'
											+ '<span class="k-widget k-header">Photo</span>'
											+ '<span class="k-widget k-header">Contact info</span>'
											+ '</div>',
									template : '<table><tr><td rowspan=2><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.personId#'/>" width=40 height=55 alt=\"No Image to Display\" /></span></td>'
											+ '<td align="left"><span class="k-state-default"><b>#: data.personName #</b></span><br>'
											+ '<span class="k-state-default"><i>#: data.accountNumber #</i></span><br>'
											+ '<span class="k-state-default"><i>#: data.personType #</i></span></td></tr></table>',
									dataSource : {
										transport : {
											read : {

												url : "./bill/accountNumberAutocomplete"
											}
										}
									},
									height : 370,
								}).data("kendoComboBox");	
						
						
						
						
						
						var data = [ {
							text : "Last 1 Year Consumption",
							value : "Last 1 Year Consumption"
						}, {
							text : "Similar Apartment Type",
							value : "Similar Apartment Type"
						}, {
							text : "Previous 3 Year Consumption",
							value : "Previous 3 Year Consumption"
						},

						];

						$("#avgType").kendoDropDownList({
							dataTextField : "text",
							dataValueField : "value",
							optionLabel : {
								text : "Select",
								value : "",
							},

							dataSource : data
						}).data("kendoDropDownList");
						$("#avgType").kendoDropDownList({
							dataTextField : "text",
							dataValueField : "value",
							optionLabel : {
								text : "Select",
								value : "",
							},

							dataSource : data
						}).data("kendoDropDownList");

						

					});

	
	

	function generateBill() {
		 var accountNo = $("input[name=accountNo]").data("kendoComboBox").text();
			var accountId = $("#accountNo").val();
			var serviceID = $("#serviceName").val();
			var serviceName = $("input[name=serviceName]").data("kendoDropDownList").text();
			var avgType = $("input[name=avgType]").data("kendoDropDownList").text();
			var avgDate=$("#avgDate").val();
		$.ajax({

			url : "./avgUnit/calculateAverageUnit",
			type : "GET",
			data : {
				accountId:accountId,
				accountNo:accountNo,
				serviceID:serviceID,
				serviceName:serviceName,
				avgType:avgType,
				avgDate:avgDate
				
			},

			success : function(response) {
				alert("Your bill has been calculated");
				$('#abbillGrid').data('kendoGrid').refresh();
				$('#abbillGrid').data('kendoGrid').dataSource.read();
				close();
				
			}
		});

	}
	
	var SelectedRowId = "";
	var billStatus = "";
	var billServiceType = "";
	var totalBillAmount = "";
	
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

	$("#abbillGrid").on(
			"click",
			"#temPID",
			function(e) {
				var button = $(this), 
				enable = button.text() == "Approve";
				var widget = $("#abbillGrid").data("kendoGrid");
				var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
				var result=securityCheckForActionsForStatus("./BillGeneration/AdvanceBilling/approveButton"); 
				if(result=="success"){ 
				if (enable) {
					$.ajax({
						type : "POST",
						//url : "./electricityBills/elBillStatus/" + dataItem.id+ "/activate",
						url : "./advanceBills/approveBill/" + dataItem.id+ "/activate",
						dataType : "text",
						success : function(response) {
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
							button.text('In-active');
							$('#abbillGrid').data('kendoGrid').dataSource.read();
						}
					});
				} else
				{
                   alert("Already approved.");
				}
				}
			});

	var setApCode = "";
	function electricityBillEvent(e) {
		/***************************  to remove the id from pop up  **********************/
		$('div[data-container-for="avgId"]').remove();
		$('label[for="avgId"]').closest('.k-edit-label').remove();

		$(".k-edit-field").each(function() {
			$(this).find("#temPID").parent().remove();
		});

		$('label[for="status"]').parent().hide();
		$('div[data-container-for="status"]').hide();

		$('label[for="createdBy"]').parent().hide();
		$('div[data-container-for="createdBy"]').hide();

		/************************* Button Alerts *************************/
		if (e.model.isNew()) {

			

		} else {

			
		}
	}

	$("#abbillGrid").on("click", ".k-grid-Clear_Filter", function() {
		//custom actions
		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
		var gridPets = $("#abbillGrid").data("kendoGrid");
		gridPets.dataSource.read();
		gridPets.refresh();
	});

	

	/***************************  Custom message validation  **********************/

	/* (function($, kendo) {
		$
				.extend(
						true,
						kendo.ui.validator,
						{
							rules : {
								descriptionValidation : function(input, params) {

									if (input.attr("name") == "rateDescription") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},
								rateofunit : function(input, params) {

									if (input.attr("name") == "rateUnit") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},

								rateType : function(input, params) {
									if (input.attr("name") == "rateType") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},

								rateUOM : function(input, params) {
									if (input.attr("name") == "rateUOM") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},

								addressSeasonFrom : function(input, params) {
									if (input.filter("[name = 'validFrom']").length
											&& input.val() != "") {
										var selectedDate = input.val();
										var todaysDate = $.datepicker
												.formatDate('dd/mm/yy',
														new Date());
										var flagDate = false;

										if ($.datepicker.parseDate('dd/mm/yy',
												selectedDate) >= $.datepicker
												.parseDate('dd/mm/yy',
														todaysDate)) {
											seasonFromAddress = selectedDate;
											flagDate = true;
										}
										return flagDate;
									}
									return true;
								},
								addressSeasonTo1 : function(input, params) {
									if (input.filter("[name = 'validTo']").length
											&& input.val() != "") {
										var flagDate = false;

										if (seasonFromAddress != "") {
											flagDate = true;
										}
										return flagDate;
									}
									return true;
								},
								addressSeasonTo2 : function(input, params) {
									if (input.filter("[name = 'validTo']").length
											&& input.val() != "") {
										var selectedDate = input.val();
										var flagDate = false;

										if ($.datepicker.parseDate('dd/mm/yy',
												selectedDate) > $.datepicker
												.parseDate('dd/mm/yy',
														seasonFromAddress)) {
											flagDate = true;
										}
										return flagDate;
									}
									return true;
								},

								rateUOMValidation : function(input, params) {
									if (input.filter("[name='rateUOM']").length
											&& input.val() != "") {
										return /^[a-zA-Z0-9 ]{1,45}$/
												.test(input.val());
									}
									return true;
								},

							},
							messages : {
								descriptionValidation : "Description should not be empty",
								rateofunit : "Rate per unit should not be empty",
								rateType : "Rate Type should not be empty",
								rateUOM : "UOM should not be empty",
								validatinForNumbers : "Only numbers are allowed,follwed by two decimal points",
								addressSeasonFrom : "From Date must be selected in the future",
								addressSeasonTo1 : "Select From date first before selecting To date and change To date accordingly",
								rateUOMValidation : "Rate UOM can contain alphabets and spaces but cannot allow numbers and other special characters and maximum 45 characters are allowed",
								addressSeasonTo2 : "To date should be after From date"
							}
						});
	})(jQuery, kendo);
 */
	function onRequestEnd(r) {
		if (typeof r.response != 'undefined') {
			if (r.response.status == "FAIL") {

				errorInfo = "";

				for (var s = 0; s < r.response.result.length; s++) {
					errorInfo += (s + 1) + ". "
							+ r.response.result[s].defaultMessage + "<br>";

				}

				if (r.type == "create") {
					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Creating the Bill Details<br>" + errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});

				}

				if (r.type == "update") {
					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Updating the Bill Details<br>" + errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
				}

				$('#abbillGrid').data('kendoGrid').refresh();
				$('#abbillGrid').data('kendoGrid').dataSource.read();
				return false;
			}

			if (r.response.status == "CHILD") {

				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Can't Delete Bill Details, Child Record Found");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				$('#abbillGrid').data('kendoGrid').refresh();
				$('#abbillGrid').data('kendoGrid').dataSource.read();
				return false;
			}

			else if (r.response.status == "INVALID") {

				errorInfo = "";

				errorInfo = r.response.result.invalid;

				if (r.type == "create") {
					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Creating the Bill Details<br>" + errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
				}
				$('#abbillGrid').data('kendoGrid').refresh();
				$('#abbillGrid').data('kendoGrid').dataSource.read();
				return false;
			}

			else if (r.response.status == "EXCEPTION") {

				errorInfo = "";

				errorInfo = r.response.result.exception;

				if (r.type == "create") {

					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Creating the Bill Details<br>" + errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
				}

				if (r.type == "update") {
					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Updating the Bill Details<br>" + errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
				}

				$('#abbillGrid').data('kendoGrid').refresh();
				$('#abbillGrid').data('kendoGrid').dataSource.read();
				return false;
			}

			else if (r.type == "create") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Bill Details created successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});

			}

			else if (r.type == "update") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Bill Details updated successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
			}
		}
	}

	/************************************* for inner rate slab request *********************************/

	
	
	/* ----------------------------------------------------------------- tariff code drop down -------------------------------- */
	
	/*---------------------- To open advance bill pop-up------------------------------------- */
	$("#abbillGrid").on(
			"click",
			".k-grid-generateBill",
			function(e) {
				var result=securityCheckForActionsForStatus("./BillGeneration/AdvanceBilling/createButton"); 
				  if(result=="success"){ 
				var todcal = $("#generateBillDialog");
				todcal.kendoWindow({
					width : "auto",
					height : "auto",
					modal : true,
					draggable : true,
					position : {
						top : 100
					},
					title : "Generate Bill"
				}).data("kendoWindow").center().open();

				todcal.kendoWindow("open");
				  }
				
				  var combobox = $('#accountNo').data("kendoComboBox");
				  if (combobox != null) {

						combobox.value("");
						
					} 
				  var dropdownlist = $("#serviceName").data("kendoDropDownList");
					dropdownlist.value("");
					$('#units').val("");
					
				
				  /* 

				var dropdownlist1 = $("#presentStaus")
						.data("kendoDropDownList");
				dropdownlist1.value("");
				
				var presentreading = $("#presentReading");
				presentreading.val("");
				
				$("#presentbilldate").val("");
				$("#previousbillDate").val("");
				$("#previousStaus").val("");
				$('#presentTod1').val("");
				$('#presentTod2').val("");
				$('#presentTod3').val("");
				var combobox = $('#accountNo').data("kendoComboBox");
				

				if (combobox != null) {

					combobox.value("");
				} */

			});
	
	function close(){
		var todcal = $("#generateBillDialog");
		todcal.kendoWindow({
			width : "auto",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Generate Bill"
		}).data("kendoWindow").center().close();

		todcal.kendoWindow("close");
		  }
	
</script>
<style>
td {
	border: 0 none;
	font-size: 100%;
	margin: 0;
	outline: 0 none;
	padding: 0;
	vertical-align: middle;
}

div[id^='elRateSlab_']>div>table td:nth-child(4) {
	text-align: right;
}

span.k-tooltip {
    border-width: 0;
    display: flex;
    padding: 2px 5px 1px 6px;
    position: fixed;
}

tr[class='k-footer-template'] {
	text-align: right;
}
</style>
