<%@include file="/common/taglibs.jsp"%>

<c:url value="/FRChange/FRChangeCreateUrl" var="FRChangeCreateUrl" />
<c:url value="/FRChange/FRChangeReadUrl" var="FRChangeReadUrl" />
<c:url value="/FRChange/FRChangeUpdateUrl" var="FRChangeUpdateUrl" />
<c:url value="/FRChange/FRChangeDestroyUrl" var="FRChangeDestroy" />

<c:url value="/FRChange/serviceTypeComboBoxUrl" var="serviceTypeComboBoxUrl" />
<c:url value="/FRChange/readAccountNumbers" var="readAccountNumbers" />

<c:url value="/FRChange/filter" var="commonFilterForFRChangeUrl" />
<c:url value="/FRChange/commonFilterForAccountNumbersUrl" var="commonFilterForAccountNumbersUrl" />
<c:url value="/FRChange/getPersonListForFileter" var="personNamesFilterUrl" />

<div id="sample">
<kendo:grid name="grid" remove="frChangeDeleteEvent" resizable="true" pageable="true" selectable="true" change="onChangeFRChange" edit="FRChangeEvent"  sortable="true" scrollable="true" groupable="true">
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10"></kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
			<kendo:grid-filterable-operators>
				<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains"/>
			</kendo:grid-filterable-operators>
		</kendo:grid-filterable>
		<kendo:grid-editable mode="popup" />
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Details" />
            <kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterFRChane()><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>"/>
		</kendo:grid-toolbar>	
	
            <kendo:grid-columns>
			
			<kendo:grid-column title="FRChangeId" field="frId" width="110px" hidden="true"/>
			
			<kendo:grid-column title="Account Number&nbsp;*" field="accountId" editor="AccountNumbers" hidden="true" filterable="true" width="0px">
	    	</kendo:grid-column>
	    		
	        <kendo:grid-column title="Account Number&nbsp;*" field="accountNo" filterable="true" width="130px">
	        <kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function ledgerTypeFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Account Number",
									dataSource : {
										transport : {
											read : "${commonFilterForAccountNumbersUrl}"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	    	</kendo:grid-column>
	    	
	    	<kendo:grid-column title="Person&nbsp;Name" field="personName"  width="120px" filterable="false">
	    	<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function personNameFilter(element) 
						   	{
								element.kendoAutoComplete({
									autoBind : false,
									dataTextField : "personName",
									dataValueField : "personName", 
									placeholder : "Enter name",
									headerTemplate : '<div class="dropdown-header">'
										+ '<span class="k-widget k-header">Photo</span>'
										+ '<span class="k-widget k-header">Contact info</span>'
										+ '</div>',
									template : '<table><tr><td rowspan=2><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.personId#'/>" width=40 height=55 alt=\"No Image to Display\" /></span></td>'
										+ '<td align="left"><span class="k-state-default"><b>#: data.personName #</b></span><br>'
										+ '<span class="k-state-default"><i>#: data.personStyle #</i></span><br>'
										+ '<span class="k-state-default"><i>#: data.personType #</i></span></td></tr></table>',
									dataSource : {
										transport : {		
											read :  "${personNamesFilterUrl}"
										}
									} 
								});
						   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	    	</kendo:grid-column>
			
			<kendo:grid-column title="Service&nbsp;Type&nbsp;*"  field="typeOfService" filterable="true" width="110px" editor="dropDownChecksEditor">
			<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function apCodeFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Servicetype",
									dataSource : {
										transport : {
											read : "${commonFilterForFRChangeUrl}/typeOfService"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>

			
			
			<kendo:grid-column title="Bill&nbsp;Date&nbsp;*" field="billDate" format="{0:dd/MM/yyyy}" width="100px"/>
			
		   <kendo:grid-column title="Present Reading Utility*" field="presentReading"  width="120px" filterable="false"/>
		   
		    <kendo:grid-column title="Present Reading DG*" field="presentReadingDg"  width="120px" filterable="false"/>
			
		   <kendo:grid-column title="Final Reading Utility*" field="finalReading"  width="120px"/>
		   
		   <kendo:grid-column title="Final Reading DG*" field="finalReadingDg"  width="120px"/>
		   
		   
		   
		   <kendo:grid-column title="Description" field="description" editor="frChangeDesEditor" width="140px">
		   <kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function apCodeFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Description",
									dataSource : {
										transport : {
											read : "${commonFilterForFRChangeUrl}/description"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
		   </kendo:grid-column>

		   <kendo:grid-column title="&nbsp;" width="140px">
			  <kendo:grid-column-command>
				 <kendo:grid-column-commandItem name="edit" />
				 <kendo:grid-column-commandItem name="destroy" />
			  </kendo:grid-column-command>
		   </kendo:grid-column>

	</kendo:grid-columns>	

       <kendo:dataSource pageSize="20" requestEnd="onRequestEnd" requestStart="onRequestStart">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-create url="${FRChangeCreateUrl}" dataType="json" type="GET" contentType="application/json" />
				<kendo:dataSource-transport-read url="${FRChangeReadUrl}" dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-update url="${FRChangeUpdateUrl}" dataType="json" type="GET" contentType="application/json" />
				<kendo:dataSource-transport-destroy url="${FRChangeDestroy}" dataType="json" type="GET" contentType="application/json" />
			</kendo:dataSource-transport>
			
			<kendo:dataSource-schema>
				<kendo:dataSource-schema-model id="frId">
					<kendo:dataSource-schema-model-fields>

						<kendo:dataSource-schema-model-field name="frId" type="number"/>
						
						<kendo:dataSource-schema-model-field name="personName" type="string"/>
						
						<kendo:dataSource-schema-model-field name="accountId" type="number" defaultValue=""/>
						
						<kendo:dataSource-schema-model-field name="accountNo" type="string"/>
						
						<kendo:dataSource-schema-model-field name="billDate" type="date"/>
						
						<kendo:dataSource-schema-model-field name="typeOfService" type="string">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="presentReading">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="presentReadingDg">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="finalReadingDg" type="number" defaultValue="">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="description" type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="finalReading" type="number" defaultValue=""/>
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
			
		</kendo:dataSource>
</kendo:grid>
</div>
<div id="alertsBox" title="Alert"></div>

<script>

function frChangeDesEditor(container, options) 
{
$('<textarea data-text-field="description" name = "description" style="width:150px;height:60px"/>')
    .appendTo(container);
$('<span class="k-invalid-msg" data-for="Enter Description"></span>').appendTo(container);
}

function frChangeDeleteEvent(e){
	securityCheckForActions("./Masters/FRChange/destroyButton");
	var conf = confirm("Are you sure want to delete this item?");
	    if(!conf){
	    $('#grid').data().kendoGrid.dataSource.read();
	    throw new Error('deletion aborted');
	     }
}

var SelectedRowId = "";
var SelectedServiceType = "";

function onChangeFRChange(arg) {
	 var gview = $("#grid").data("kendoGrid");
	 var selectedItem = gview.dataItem(gview.select());
	 SelectedRowId = selectedItem.frId;
	 SelectedServiceType = selectedItem.typeOfService;
}

function clearFilterFRChane()
{
   $("form.k-filter-menu button[type='reset']").slice().trigger("click");
   var gridStoreIssue = $("#grid").data("kendoGrid");
   gridStoreIssue.dataSource.read();
   gridStoreIssue.refresh();
}

var billDate = "";
var elBillParameterValue = "";
var dgpresentReading = "";

function FRChangeEvent(e)
{
	/***************************  to remove the id from pop up  **********************/
	$('div[data-container-for="frId"]').remove();
	$('label[for="frId"]').closest(
	'.k-edit-label').remove();
	
	$(".k-edit-field").each(function () {
		$(this).find("#temPID").parent().remove();  
   	});
 	
	var billDate1 = $('input[name="billDate"]').kendoDatePicker({
		format : "dd/MM/yyyy"
	}).data("kendoDatePicker");
	billDate1.readonly();
	
	$('input[name="presentReading"]').prop("readonly", true);
	
	$('input[name="presentReadingDg"]').prop("readonly", true);
	
	$('label[for=todApplicable]').parent().hide();
	$('div[data-container-for="todApplicable"]').hide();
	
	$('label[for=accountNo]').parent().hide();
	$('div[data-container-for="accountNo"]').hide();
		
	$('label[for="status"]').parent().hide();  
	$('div[data-container-for="status"]').hide();
	
	$('label[for="personName"]').parent().hide();  
	$('div[data-container-for="personName"]').hide();
	
	$('label[for="createdBy"]').parent().hide();  
	$('div[data-container-for="createdBy"]').hide();
	
	 $(".k-grid-cancel").click(function () {
		 var grid = $("#grid").data("kendoGrid");
		 grid.dataSource.read();
		 grid.refresh();
	 });
	
	
	/************************* Button Alerts *************************/
	if (e.model.isNew()) 
    {
		securityCheckForActions("./Masters/FRChange/createButton");
		$(".k-window-title").text("Add Final Reading Change Details");
		$(".k-grid-update").text("Save");		
    }
	else{
		securityCheckForActions("./Masters/FRChange/editButton");
		$(".k-window-title").text("Edit Final Reading Change Details");
		}
	
	$(".k-grid-update").click(function() {
		var st = billDate;
		var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
		var dt = new Date(st.replace(pattern,'$3-$2-$1'));
		e.model.set("billDate",dt);
		e.model.set("presentReading", elBillParameterValue);
		e.model.set("presentReadingDg", dgpresentReading);
		
	  });

	}

 function AccountNumbers(container, options) 
  {
		$('<input name="Account" id="account" data-text-field="accountNo" validationmessage="Account number is required" data-value-field="accountId" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoComboBox({
		 autoBind : false,
		 placeholder: "Enter Account Number",
		 headerTemplate : '<div class="dropdown-header">'
				+ '<span class="k-widget k-header">Photo</span>'
				+ '<span class="k-widget k-header">Contact info</span>'
				+ '</div>',
			template : '<table><tr><td rowspan=2><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.personId#'/>" width=40 height=55 alt=\"No Image to Display\" /></span></td>'
				+ '<td align="left"><span class="k-state-default"><b>#: data.personName #</b></span><br>'
				+ '<span class="k-state-default"><i>#: data.accountNo #</i></span><br>'
				+ '<span class="k-state-default"><i>#: data.personType #</i></span></td></tr></table>',
		 dataSource : {
		  transport : {  
		   read :  "${readAccountNumbers}"
		  }
		 },
		 change : function (e) {
		           if (this.value() && this.selectedIndex == -1) {                    
		               alert("Account doesn't exist!");
		               $("#account").data("kendoComboBox").value("");
		        }
		    } 
		});
		
		$('<span class="k-invalid-msg" data-for="Account"></span>').appendTo(container);
  }
  
  
	  
	function dropDownChecksEditor(container, options) {
		$('<input name="Service Type" id="serviceSGRE" data-text-field="typeOfService" required="true" validationmessage="Service type is required" data-value-field="typeOfService" data-bind="value:' + options.field + '" />')
				.appendTo(container)
				.kendoComboBox({
							cascadeFrom : "account",
							autoBind : false,
							placeholder : "Select ServiceType",
							template : '<table><tr>'
									+ '<td align="left"><span class="k-state-default"><b>#: data.typeOfService #</b></span><br>'
									+ '</td></tr></table>',
							select : serviceTypeFunction,
							dataSource : {
								transport : {
									read : "${serviceTypeComboBoxUrl}"
								}
							},
							change : function(e) {
								
								if (this.value() && this.selectedIndex == -1) {
									alert("Service type doesn't exist!");
									$("#serviceSGRE").data("kendoComboBox").value("");
								}
							}

						});

		$('<span class="k-invalid-msg" data-for="Service Type"></span>').appendTo(container);
	}

	var selectedAccountId = "";
	var selectedServiceType = "";

	function serviceTypeFunction(e) {
		var dataItem = this.dataItem(e.item.index());
		selectedAccountId = dataItem.accountId;
		selectedServiceType = dataItem.typeOfService;
		$.ajax({
			type : "GET",
			url : "./frChange/getBillDateAndPreReading",
			async: false,
			dataType : "JSON",
			data : {
				accountId : selectedAccountId,
				serviceType : selectedServiceType,
			    },
			success : function(response) {
				billDate = response.billDate;
				elBillParameterValue = response.elBillParameterValue;
				dgpresentReading=response.dgpresentReading;
				$('input[name="billDate"]').val(billDate);
				$('input[name="presentReading"]').val(elBillParameterValue);
				$('input[name="presentReadingDg"]').val(dgpresentReading);
				
				}
			});
	}

	function onRequestStart(e){
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();
	}
	
	function onRequestEnd(e) {

		if (typeof e.response != 'undefined') {
			if (e.response.status == "FAIL") {
				errorInfo = "";
				errorInfo = e.response.result.invalid;
				var i = 0;
				for (i = 0; i < e.response.result.length; i++) {
					errorInfo += (i + 1) + ". "
							+ e.response.result[i].defaultMessage + "\n";
				}
				if (e.type == "create") {
					alert("Error: Creating the FRChange Details\n\n"
							+ errorInfo);
				}
				if (e.type == "update") {
					alert("Error: Updating the FRChange Details\n\n"
							+ errorInfo);
				}
				var grid = $("#grid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
				return false;
			}
			if (e.type == "update" && !e.response.Errors) {
				e.sender.read();
				$("#alertsBox").html("Alert");
				$("#alertsBox").html("Final reading details updated successfully");
				$("#alertsBox").dialog({
					modal : true,
					draggable : false,
					resizable : false,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
			}
			if (e.type == "create" && !e.response.Errors) {
				e.sender.read();
				$("#alertsBox").html("Alert");
				$("#alertsBox").html("Final reading details created successfully");
				$("#alertsBox").dialog({
					modal : true,
					draggable : false,
					resizable : false,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
			}
			if (e.type == "destroy" && !e.response.Errors) {
				$("#alertsBox").html("Alert");
				$("#alertsBox").html("Final reading details deleted successfully");
				$("#alertsBox").dialog({
					modal : true,
					draggable : false,
					resizable : false,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				var grid = $("#grid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			}
		}
	}
	
	//register custom validation rules

	(function ($, kendo) 
		{   	  
	    $.extend(true, kendo.ui.validator, 
	    {
	         rules: 
	         { // custom rules

	        	 finalReadingRequired : function(input, params){
								      if (input.attr("name") == "finalReading")
								          {
								           return $.trim(input.val()) !== "";
								          }
								       return true;
								      },
								      descriptionLengthValidation: function (input, params)
							             {
								      if (input.filter("[name='description']").length && input.val() != "") 
							             {
							            	 return /^[\s\S]{1,500}$/.test(input.val());
							             }        
							             return true;
							                }
							},
							messages : {
								//custom rules messages
								finalReadingRequired : "Final reading is required", 
								descriptionLengthValidation : "Description field allows max 500 characters"
								
							}
						});

	})(jQuery, kendo);
	//End Of Validation
	
</script>
<style>
.k-datepicker span {
	width: 52%
}

.k-datepicker {
	background: white;
}
</style>