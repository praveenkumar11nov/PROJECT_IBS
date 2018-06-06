<%@include file="/common/taglibs.jsp"%>
<link	href="<c:url value='/resources/twitter-bootstrap-wizard/bootstrap/css/bootstrap.min.css'/>"	rel="stylesheet" />
<c:url value="/prepaidRecharge/tokenUpdatereadUrl" var="readUrl"></c:url>
<c:url value="/prepaidRecharge/tokenUpdateupdateUrl" var="updateUrl"></c:url>


<kendo:grid name="grid" pageable="false" resizable="true"
	change="onChangeTokenList" edit="prepaidRechargeTokenEvent"
	sortable="false" reorderable="true" selectable="false"
	scrollable="true" filterable="false" groupable="false" dataBound="dataBound">
	<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10"
		input="true" numeric="true" refresh="true" info="true"
		previousNext="true">
		<kendo:grid-pageable-messages itemsPerPage="Status items per page"
			empty="No status item to display"
			refresh="Refresh all the status items"
			display="{0} - {1} of {2} New Status Items"
			first="Go to the first page of status items"
			last="Go to the last page of status items"
			next="Go to the next page of status items"
			previous="Go to the previous page of status items" />
	</kendo:grid-pageable>
	<kendo:grid-filterable extra="false">
		<kendo:grid-filterable-operators>
			<kendo:grid-filterable-operators-string eq="Is equal to"
				contains="Contains" />
			<kendo:grid-filterable-operators-date gt="Is after" lt="Is before" />
		</kendo:grid-filterable-operators>
	</kendo:grid-filterable>
	<kendo:grid-editable mode="popup" />
	<kendo:grid-toolbar>
		<kendo:grid-toolbarItem
			template="<a class='k-button' href='\\#' onclick=clearFilter()>Clear Filter</a>" />
		<%-- <kendo:grid-toolbarItem name="ConsumerTemplatesDetailsExport"
			text="Export To Excel" /> --%>
		
	</kendo:grid-toolbar>
	<kendo:grid-columns>
           <kendo:grid-column title="&nbsp;" width="120px">

			<kendo:grid-column-command>
				<kendo:grid-column-commandItem name="edit" />
			</kendo:grid-column-command>
		</kendo:grid-column>

		<kendo:grid-column title="PrePaidRechId" field="prePaidId"
			width="70px" hidden="true" filterable="false" sortable="false" />

		<kendo:grid-column title="Consumer Id&nbsp;" field="meterSerialNo"
			width="110px" filterable="true" />

		<kendo:grid-column title="Customer&nbsp;Name&nbsp;"
			field="personName" width="130px" filterable="true">

		</kendo:grid-column>

		<kendo:grid-column title="Property&nbsp;No" field="propertyName"
			width="120px" filterable="true">
		</kendo:grid-column>
		<kendo:grid-column title="Token&nbsp;No" field="tokenNo" width="200px" />
		<kendo:grid-column title="Recharge&nbsp;Amount" field="rechargeAmount"
			width="140" filterable="true"/>
		<kendo:grid-column title="Recharged&nbsp;Date" field="billDate"
			format="{0:dd/MM/yyyy}" width="140px" />
		<kendo:grid-column title="Status" field="parentStatus" width="100" filterable="true"/>
		<kendo:grid-column title="Transaction&nbsp;Date" field="txnDate"
			format="{0:dd/MM/yyyy}" width="140px" />
		<kendo:grid-column title="Transaction&nbsp; Id" field="txnId"
			width="140px" />
		<kendo:grid-column title="Mode Of Payment" field="paymentMode"
			width="135px" />
		
	</kendo:grid-columns>

	<kendo:dataSource pageSize="20" requestEnd="onRequestEnd"
		requestStart="onRequestStart">
		<kendo:dataSource-transport>
			<kendo:dataSource-transport-read url="${readUrl}" dataType="json"
				type="POST" contentType="application/json" />
			 <kendo:dataSource-transport-update url="${updateUrl}" dataType="json"
				type="GET" contentType="application/json" />  
		</kendo:dataSource-transport>

		<kendo:dataSource-schema>
			<kendo:dataSource-schema-model id="prePaidId">
				<kendo:dataSource-schema-model-fields>
					<kendo:dataSource-schema-model-field name="prePaidId" type="number" />

					<kendo:dataSource-schema-model-field name="propertyName"
						type="string" />

					<kendo:dataSource-schema-model-field name="personName"
						type="string" />

					<kendo:dataSource-schema-model-field name="meterSerialNo"
						type="string" />
					<kendo:dataSource-schema-model-field name="paymentMode"
						type="string" />
					<kendo:dataSource-schema-model-field name="rechargeAmount"
						type="string" />
					<kendo:dataSource-schema-model-field name="tokenNo" type="string" />
					<kendo:dataSource-schema-model-field name="parentStatus"
						type="string" />
					<kendo:dataSource-schema-model-field name="txnDate" type="string" />
					<kendo:dataSource-schema-model-field name="txnId" type="string" />
					<kendo:dataSource-schema-model-field name="billDate" type="date" />
				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>

	</kendo:dataSource>
</kendo:grid>
<div id="alertsBox" title="Alert"></div>
<script type="text/javascript">
function clearFilter() {
	//custom actions

	$("form.k-filter-menu button[type='reset']").slice().trigger("click");
	var gridServiceMaster = $("#grid").data("kendoGrid");
	gridServiceMaster.dataSource.read();
	gridServiceMaster.refresh();
}

var txnDate ="";
var tokenNo = "";
var parentStatus = "";
var billDate = null;
var personName = "";
var meterNumber = "";
var prepaidId = 0;
function onChangeTokenList(arg) {
	var gview = $("#grid").data("kendoGrid");
	var selectedItem = gview.dataItem(gview.select());
	txnDate = selectedItem.txnDate;

	tokenNo = selectedItem.tokenNo;

	parentStatus = selectedItem.parentStatus;
	billDate = selectedItem.billDate;
	personName = selectedItem.personName;
	prepaidId = selectedItem.prePaidId;
	meterNumber = selectedItem.meterSerialNo;
	this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));

}

function dataBound(e) 
{	/* Coupan Generated */
	var data = this.dataSource.view(), row;
	var grid = $("#grid").data("kendoGrid");
	for (var i = 0; i < data.length; i++) {
		row = this.tbody.children("tr[data-uid='" + data[i].uid + "']");
		var tokenNo = data[i].tokenNo;
		//alert(tokenNo);
		var currentUid = data[i].uid;
		 if(tokenNo!=null){
			var currenRow = grid.table.find("tr[data-uid='" + currentUid +"']");
			var approveButton =$(currenRow).find(".k-grid-edit");
				approveButton.hide();
		}
	}
}

function prepaidRechargeTokenEvent(e) {
	/***************************  to remove the id from pop up  **********************/
	
	$('a[id="temPID"]').remove();
	//flagColumnNameCode = true;
	$('div[data-container-for="prePaidId"]').remove();
	$('label[for="prePaidId"]').closest('.k-edit-label').remove();
	$('div[data-container-for="parentStatus"]').remove();
	$('label[for="parentStatus"]').closest('.k-edit-label').remove();
	 $('input[name="meterSerialNo"]').attr('readonly', 'readonly');
	$('input[name="propertyName"]').attr('readonly', 'readonly');
	$('input[name="personName"]').attr('readonly', 'readonly');
	 $('input[name="paymentMode"]').attr('readonly', 'readonly');
	$('input[name="rechargeAmount"]').attr('readonly', 'readonly');
	//$('input[name="parentStatus"]').attr('readonly', 'readonly');
	$('input[name="txnDate"]').attr('readonly', 'readonly');
	$('input[name="txnId"]').attr('readonly', 'readonly'); 
	
	/************************* Button Alerts *************************/
	if (e.model.isNew()) {
		
	} else {

	
		
		$(".k-window-title").text("Edit prePaid Meter");
		$('.k-edit-field .k-input').first().focus();
		 $(".k-grid-update").click(function () 
		  {
			 var tokenNumber=$('input[name="tokenNo"]').val();
			 if(tokenNumber == ""){
					alert("Token Number is Required");
					return false;
				}
			 var date=$('input[name="billDate"]').val();
				if(date == ""){
					alert("Recharged Date  is Required");
					return false;
				}
		});; 
	}
}

function onRequestStart(e) {
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
			for (var i = 0; i < e.response.result.length; i++) {
				errorInfo += (i + 1) + ". "
						+ e.response.result[i].defaultMessage + "<br>";
			}

			if (e.type == "create") {
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Error: Creating the meter status\n\n : "
								+ errorInfo);
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
			}
			var grid = $("#grid").data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
		} else if (e.type == "create") {
			$("#alertsBox").html("");
			$("#alertsBox").html(" Meter created successfully");
			$("#alertsBox").dialog({
				modal : true,
				buttons : {
					"Close" : function() {
						$(this).dialog("close");
					}
				}
			});

			var grid = $("#grid").data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
		} else if (e.type == "destroy") {
			$("#alertsBox").html("");
			$("#alertsBox").html("PrePaid Meter deleted successfully");
			$("#alertsBox").dialog({
				modal : true,
				buttons : {
					"Close" : function() {
						$(this).dialog("close");
					}
				}
			});

			var grid = $("#grid").data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
		} else if (e.type == "update") {
			$("#alertsBox").html("");
			$("#alertsBox").html(" Token Number updated successfully");
			$("#alertsBox").dialog({
				modal : true,
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
</script>
