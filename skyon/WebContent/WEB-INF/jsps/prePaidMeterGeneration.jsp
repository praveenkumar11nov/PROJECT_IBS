<%@include file="/common/taglibs.jsp"%>

<link	href="<c:url value='/resources/twitter-bootstrap-wizard/bootstrap/css/bootstrap.min.css'/>"	rel="stylesheet" />

<%--  <c:url value="/prePaidMeterGeneration/createUrl"  var="createUrl"></c:url> --%>
 <c:url value="/prePaidMeterGeneration/readUrl"  var="readUrl"></c:url>
<c:url value="/prePaidMeterGeneration/updateUrl"  var="updateUrl"></c:url> 
<c:url value="/openNewTickets/readTowerNames" var="getAllBlockUrl" />

<c:url value="/openNewTickets/readPropertyNumbers" var="getAllPropertyUrl" />
<c:url value="/prepaidMeters/getPersonListBasedOnPropertyNumbers" var="personNamesAutoBasedOnPersonTypeUrl"></c:url>
<script>
	function closePopup() {
	
		$("#ajax").hide();
	}
	function closePopup1() {
	
		$("#ajax1").hide();
	}
	
</script>
<c:if test="${not empty msg}">
	<div aria-hidden="false" role="basic" tabindex="-1" id="ajax"
		class="modal fade in" style="display: block;">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button aria-hidden="true" data-dismiss="modal" class="close"
						type="button"></button>
					<h4 class="modal-title">Alert</h4>
				</div>
				<div class="modal-body">${msg}</div>
				<div class="modal-footer">
					<button data-dismiss="modal" class="btn default" type="button"
						onclick="closePopup()">Close</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
	</div>

</c:if>
<kendo:grid name="grid"  pageable="false" resizable="true" change="onChangeBillsList" edit="prepaidMeterSettingEvent" sortable="false" reorderable="true" selectable="false" scrollable="true" filterable="false" groupable="false">
    <kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" refresh="true" info="true" previousNext="true">
	<kendo:grid-pageable-messages itemsPerPage="Status items per page" empty="No status item to display" refresh="Refresh all the status items" 
			display="{0} - {1} of {2} New Status Items" first="Go to the first page of status items" last="Go to the last page of status items" next="Go to the next page of status items"
			previous="Go to the previous page of status items"/>
	</kendo:grid-pageable>
	<kendo:grid-filterable extra="false">
		<kendo:grid-filterable-operators>
			<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains"/>
			<kendo:grid-filterable-operators-date gt="Is after" lt="Is before"/>
		</kendo:grid-filterable-operators> 
	</kendo:grid-filterable>
	<kendo:grid-editable mode="popup"/>
		<kendo:grid-toolbar>
		      <kendo:grid-toolbarItem name="meterGrid" text="Create Pre-Paid Meters" />
		      <kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilter()>Clear Filter</a>"/>
	          <kendo:grid-toolbarItem name="ConsumerTemplatesDetailsExport" text="Export To Excel" />
	        <%--   <kendo:grid-toolbarItem name="uploadConsumerDetails" text="Upload Batch File" /> --%>
		     <%--  <kendo:grid-toolbarItem name="activateAll" text="Activate All" />
		       <kendo:grid-toolbarItem name="ConsumerTemplatesDetailsExport" text="Export To Excel" />
		       <kendo:grid-toolbarItem name="amrPdfTemplatesDetailsExport" text="Export To PDF" />
		     <kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterAMRSettings()><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>"/> --%>
	    </kendo:grid-toolbar>
	<kendo:grid-columns>
	    
	    <kendo:grid-column title="PrePaidMeterId" field="prePaidId" width="70px" hidden="true" filterable="false" sortable="false" />
	    
	    <kendo:grid-column title="Tower&nbsp;*" field="blockId" width="70px" filterable="false" hidden="true"/>
	    
	     <kendo:grid-column title="Tower&nbsp;*" field="blocksName" width="70px" filterable="true">
	     <%-- <kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function storeNameFilter(element) 
						   	{
								element.kendoAutoComplete({
									dataSource : {
										transport : {		
											read :  "${blockNameFilterUrl}"
										}
									} 
								});
						   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable> --%>
	     </kendo:grid-column>
	     <kendo:grid-column title="Consumer&nbsp;Id*" field="consumerId" width="100px" filterable="true"></kendo:grid-column>
	    <kendo:grid-column title="Property&nbsp;*" field="propertyId" width="70px" filterable="false" hidden="true"/>
	    
	    <kendo:grid-column title="Property&nbsp;No*" field="propertyName" width="100px" filterable="true" >
	   <%--  <kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function storeNameFilter(element) 
						   	{
								element.kendoAutoComplete({
									dataSource : {
										transport : {		
											read :  "${propertyNameFilterUrl}"
										}
									} 
								});
						   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable> --%>
	     </kendo:grid-column>
	     
	       <kendo:grid-column title="person&nbsp;*" field="personId" width="70px" filterable="false" hidden="true"/>
	     <kendo:grid-column title="Person Name" field="personName" width="100px" filterable="true" />
	     
	      <kendo:grid-column title="Meter&nbsp;Number" field="meterNumber"  filterable="true" 
			width="120px" >
		  <%--  <kendo:grid-column-filterable>
						<kendo:grid-column-filterable-ui>
							<script type="text/javascript">
								function storeNameFilter(element) 
							   	{
									element.kendoAutoComplete({
										dataSource : {
											transport : {		
												read :  "${meterNumberFilterUrl}"
											}
										} 
									});
							   	}
							</script>
						</kendo:grid-column-filterable-ui>
					</kendo:grid-column-filterable> --%>
		    </kendo:grid-column>
	        <kendo:grid-column title="EB&nbsp;Reading" field="initialReading"  filterable="true" 
			width="100px" ></kendo:grid-column>
			<kendo:grid-column title="DG&nbsp;Reading" field="dgReading"  filterable="true" 
			width="100px" ></kendo:grid-column>
			<kendo:grid-column title="Balance&nbsp;" field="initialBalnce"  filterable="true" 
			width="100px" ></kendo:grid-column>
			<kendo:grid-column title="Service&nbsp;Start&nbsp;Date" field="readingDate" format="{0:dd/MM/yyyy}" filterable="true" 
			width="120px" ></kendo:grid-column>
	   

	<%--     <kendo:grid-column title="Status" field="status" width="70px" filterable="true">
	    <kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function storeNameFilter(element) 
						   	{
								element.kendoAutoComplete({
									dataSource : {
										transport : {		
											read :  "${statusFilterUrl}"
										}
									} 
								});
						   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	    </kendo:grid-column> --%>

<%--         <kendo:grid-column title=""
				template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Active#=data.amrId#'>#= data.status == 'Active' ? 'Inactivate' : 'Activate' #</a>"
				width="80px" /> --%>
		<kendo:grid-column title="&nbsp;" width="160px">
		 
			<kendo:grid-column-command>
			    <kendo:grid-column-commandItem name="edit" />
				<kendo:grid-column-commandItem name="Save Old Meter Data" click="meterHistoryData"/> 
			</kendo:grid-column-command>
		</kendo:grid-column>
		
	</kendo:grid-columns>

	<kendo:dataSource pageSize="20" requestEnd="onRequestEnd" requestStart="onRequestStart">
		<kendo:dataSource-transport>
			<%--  <kendo:dataSource-transport-create url="${createUrl}" dataType="json" type="GET" contentType="application/json" /> --%>
		<kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json" />
		<kendo:dataSource-transport-update url="${updateUrl}" dataType="json" type="GET" contentType="application/json" />  
			<%-- <kendo:dataSource-transport-destroy url="${destroyUrl}/" dataType="json" type="GET" contentType="application/json" /> --%>
		</kendo:dataSource-transport> 

		<kendo:dataSource-schema >
			<kendo:dataSource-schema-model id="prePaidId">
				<kendo:dataSource-schema-model-fields>
					<kendo:dataSource-schema-model-field name="prePaidId" type="number"/>
					
					 <kendo:dataSource-schema-model-field name="blockId" type="number"/> 
					
					<kendo:dataSource-schema-model-field name="blocksName" type="string" />
					<kendo:dataSource-schema-model-field name="consumerId" type="string" />
					<kendo:dataSource-schema-model-field name="personId" type="number"/>
				    <kendo:dataSource-schema-model-field name="propertyId" type="number"/> 
					
					<kendo:dataSource-schema-model-field name="propertyName" type="string" />
					
					<kendo:dataSource-schema-model-field name="personName" type="string" />
					
					<kendo:dataSource-schema-model-field name="meterNumber" type="string"/>
					
  					<kendo:dataSource-schema-model-field name="status" type="string"/>
  					<kendo:dataSource-schema-model-field name="initialReading" type="number" >
  					</kendo:dataSource-schema-model-field>
  					<kendo:dataSource-schema-model-field name="readingDate" type="date"/>
  					<kendo:dataSource-schema-model-field name="dgReading" type="number" defaultValue="0"/>
  					<kendo:dataSource-schema-model-field name="initialBalnce" type="number" defaultValue="0"/>
  
				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>

	</kendo:dataSource>

</kendo:grid>
<div id="meterWizardPopUp" hidden="true" style="overflow: hidden; height: 500px;">

<div class='container'>
	<div class="span12" style=" width: 500px;">
		<section id="wizard">
<form:form id="commentForm"  action="" class="form-horizontal" commandName="paidMeters" modelAttribute="paidMeters" autocomplete="off">
  <div id="rootwizard">
					<div class="tab-content" >
							<table>
								<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="blockName">Block&nbsp;Name&nbsp;*</label>
											<div class="controls">
												<%-- <form:input type="text" id="blockName" path="blockName"	class="required blockName"/> --%>
												<select id="blockName" name="blockName" class="required blockName" required="required" class="validate[required]" data-required-msg="Select Block"></select>
											</div>
										</div>
									</td>
									</tr>
									<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="propertyId">Property&nbsp;Number&nbsp;*</label>
											<div class="controls">
												<%-- <form:input type="text" id="propertyId" path="propertyId" class="required propertyId"/> --%>
												<select id="propertyId" name="propertyId" data-required-msg="Select Property"></select>
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="personId">Person&nbsp;Name&nbsp;*</label>
											<div class="controls">
												<%-- <form:input type="text" id="personId" path="personId" class="required personId"/> --%>
												<select id="personId" name="personId" data-required-msg="Select Person" onchange="accountNumberChange()"></select>
											</div>
										</div>
									</td>
									</tr>
								<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="consumerId">Consumer&nbsp;Id&nbsp;*</label>
											<div class="controls">
												<form:input type="text" id="consumerId"  path="consumerId" cssStyle="width: 159px;" onchange="checkmtr()"/>
											</div>
										</div>
									</td>
								</tr>	
								<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="serviceType">Meter&nbsp;Number&nbsp;*</label>
											<div class="controls">
												<form:input type="text" id="meterNumber"   path="meterNumber" cssStyle="width: 159px;"/>
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="initailReading">EB&nbsp;Reading&nbsp;</label>
											<div class="controls">
												<form:input type="number" id="initailReading" path="initailReading" cssStyle="width: 175px; height: 28px;"/>
											</div>
										</div>
									</td>
								</tr>
								
								<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="dgReading">DG&nbsp;Reading&nbsp;</label>
											<div class="controls">
												<form:input type="number" id="dgReading" path="dgReading" cssStyle="width: 175px; height: 28px;"/>
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="balance">Balance&nbsp;</label>
											<div class="controls">
												<form:input type="number" id="balance" path="balance" cssStyle="width: 175px; height: 28px;"/>
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="readingDate">Service&nbsp;Start&nbsp;Date&nbsp;*</label>
											<div class="controls">
												<form:input type="text" id="readingDate" path="readingDate" cssStyle="width: 176px;" />
											</div>
										</div>
									</td>
								</tr>		
							</table>
						 
						    <ul class="pager wizard">
							<li><a style="background-color: rgb(236, 236, 236);"><input type="button" value="Submit" id="saveMeterData"></a></li>
							<li><a style="background-color: rgb(236, 236, 236);"><input type="button" value="Close" onclick="return closePop()"></a></li>
							</ul>
					
					</div>					
				</div>
</form:form> 
		</section>
	</div>
</div>
</div>

<div id="alertsBox" title="Alert"></div>

<div id="alertsBox" title="Alert"></div>
<div id="uploadTransactionFileDialog" style="display: none;">
	<form id="uploadBatchFileForm">
		<table>
			<tr>
				<td>Upload ConsumerData Batch File</td>
				<td><kendo:upload name="files" id="batchFile"></kendo:upload></td>
		</table>
	</form>
</div>
<script>
$("#readingDate").kendoDatePicker({
    format:"dd/MM/yyyy"
});
function checkmtr(){
	//alert(val);
	var consumerId=$("#consumerId").val();
	if(consumerId==''){
		   alert("consumerId is Required");
		   return false;
	   }
	
	//alert($("#personId").val());
	  $.ajax({
		   url : "./meterGeneration/checkCANumbAvailability/"+consumerId,
		   type: "GET",
		   dataType:"text",
		   success : function(response)
		   {	//alert(response);
			  if(response == 0){
			         alert("ConsumerId Already Assigned for Other Property");
					 $("#consumerId").val("");
				 return false;
			  }else{
				  return true;
			  }
		   }
		 

	}); 
}
function meterHistoryData(){
	
	
		var gview = $("#grid").data("kendoGrid");
		var selectedItem = gview.dataItem(gview.select());
		
		
		var propertyName = selectedItem.propertyName;
		//alert(propertyName);
		var personName = selectedItem.personName;
		//alert(personName);
		var serviceStartDate = selectedItem.readingDate;
		
		//alert(serviceStartDate);
		if(serviceStartDate==null){
			alert("Please Provide ServiceStartDate");
			return false;
		}
		var meterNumber = selectedItem.meterNumber;
		//alert(meterNumber);
		var ebReading = selectedItem.initialReading;
		//alert(ebReading);
		var dgReading = selectedItem.dgReading;
		//alert(dgReading);
		var balance = selectedItem.initialBalnce;
	    var	consumerId =selectedItem.consumerId;
		//alert(balance);
		$.ajax({
			type:"POST",
			url:"./saveMeterHistoryDetails",
			dataType:"text",
			data :{
				propertyName:propertyName,
				personName:personName,
				serviceStartDate:serviceStartDate,
				meterNumber:meterNumber,
				ebReading:ebReading,
				dgReading:dgReading,
				balance:balance,
				consumerId:consumerId
				
			},
			success: function(response){
				alert(response);
	var grid = $("#grid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			}
		});
	}
	$("#saveMeterData")
			.on(
					"click",
					function(e) {
						var personId = $("#personId").val();

						var propertyId = $("#propertyId").val();

						var blockName = $("#blockName").val();

						var meterNumber = $("#meterNumber").val();
						var initialReading = $("#initailReading").val();
						var readingDate = $("#readingDate").val();
						var dgReading = $("#dgReading").val();
						var balance = $("#balance").val();
						var consumerId=$("#consumerId").val();
						//alert(readingDate);
						if (blockName == null) {
							alert("Please Select Block Name");
							return false;
						} else if (propertyId == null) {
							alert("Property Number Required");
							return false;
						} else if (personId == null) {
							alert("Please Select Person Name");
							return false;
						} else if (initialReading == null
								|| initialReading == "") {
							alert("initial Reading is Required");
							return false;
						} else if (readingDate == "") {
							alert("Service Start Date is Required");
							return false;
						} else if (meterNumber == "") {
							alert("Meter Number Required");
							return false;
						} else if(consumerId == ""){
							alert("ConsumerId is Required");
							return false;
						}else if (meterNumber != "") {
						
							// alert("hhh");
							$
									.ajax({
										url : "./meterGeneration/meterNumberAvailability123/"
												+ meterNumber,
										type : "GET",
										dataType : "text",
										async : false,
										success : function(response) {
											if (response == "Meter Already Assigned") {
												alert(response);
												$("#meterNumber").val("");
												return false;
											} else {
												$
														.ajax({
															url : "./meterGenerationCreate?personId="
																+ personId
																+ "&propertyId="
																+ propertyId
																+ "&blockName="
																+ blockName
																+ "&meterNumber="
																+ meterNumber
																+ "&initialReading="
																+ initialReading
																+ "&readingDate="
																+ readingDate
																+ "&dgReading="
																+ dgReading
																+ "&balance="
																+ balance
																+ "&consumerId="
																+consumerId,
															type : "GET",
															contentType : "application/json; charset=utf-8",
															dataType : "json",
															async : false,
															success : function(
																	response) {

																// alert(resonse);
																meterClose();
																$("#alertsBox")
																		.html(
																				"");
																$("#alertsBox")
																		.html(
																				"Meter Created Successfully");
																$("#alertsBox")
																		.dialog(
																				{
																					modal : true,
																					buttons : {
																						"Close" : function() {
																							$(
																									this)
																									.dialog(
																											"close");
																						}
																					}
																				});
																var grid = $(
																		"#grid")
																		.data(
																				"kendoGrid");
																grid.dataSource
																		.read();
																grid.refresh();
															}

														});
											}
										}

									});

						}

					});

	function closePop() {
		$("#personId").data("kendoComboBox").value("");
		$("#propertyId").data("kendoDropDownList").value("");
		$("#blockName").data("kendoDropDownList").value("");

		$("#meterNumber").val("");
		meterClose();
	}
	function meterClose() {

		var todcal = $("#meterWizardPopUp");
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
	$("#grid").on("click", ".k-grid-meterGrid", function(e) {

		//var result=securityCheckForActionsForStatus("./Accounts/ServiceOnBoard/createButton"); 
		$("#personId").data("kendoComboBox").value("");
		$("#propertyId").data("kendoDropDownList").value("");
		$("#blockName").data("kendoDropDownList").value("");
		$("#meterNumber").val("");
		var todcal = $("#meterWizardPopUp");
		todcal.kendoWindow({
			width : 445,
			height : 'auto',
			modal : true,
			draggable : true,
			position : {
				top : 90
			},
			title : "Create prePaid Meter"
		}).data("kendoWindow").center().open();

		todcal.kendoWindow("open");

	});

	function accountNumberChange() {
		var personId = $("#personId").val();
		var propertyId = $("#propertyId").val();
		$.ajax({
			url : "./meterGeneration/accountNumber?personId=" + personId
					+ "&propertyId=" + propertyId,
			type : "GET",
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(response) {

				if (response.length == 0) {
					/*  $(".control_acount_number").empty();
					 $("#autoGenerate").show();
					 */
				} else {
					alert("Already Meter Assigned for these Property !");
					$("#personId").data("kendoComboBox").value("");
					return false;
					//$(".control_acount_number").empty();
					/*  $("#autoGenerate").hide();
					 var resp=""+response+"";
					 for(var i=0;i<=response.length-1;i++){
					 		accountNumber=resp.split(',');  
					 		$('input[id="accountNo"]').val(accountNumber);
					 		
					 	 } */
				}

			}

		});

	}
	function clearFilter() {
		//custom actions

		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
		var gridServiceMaster = $("#grid").data("kendoGrid");
		gridServiceMaster.dataSource.read();
		gridServiceMaster.refresh();
	}

	var blockId = 0;
	var blocksName = " ";
	var propertyId = 0;
	var propertyName = "";
	var personName = "";
	var meterNumber = "";
	var prepaidId = 0;
	var consumerId="";
	function onChangeBillsList(arg) {
		var gview = $("#grid").data("kendoGrid");
		var selectedItem = gview.dataItem(gview.select());
		blockId = selectedItem.blockId;

		blocksName = selectedItem.blocksName;

		propertyId = selectedItem.propertyId;
		propertyName = selectedItem.propertyName;
		personName = selectedItem.personName;
		prepaidId = selectedItem.prePaidId;
		meterNumber = selectedItem.meterNumber;
		consumerId  = selectedItem.consumerId;
		this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));

	}

	var setApCode = "";
	var flagColumnNameCode = "";
	var resContact = [];
	var meterNum = "";
	var consumId="";
	var resContact1 = [];
	function prepaidMeterSettingEvent(e) {
		/***************************  to remove the id from pop up  **********************/
		$.ajax({
			type : "GET",
			url : './meterGeneration/meterNumberAvailability/' + prepaidId,
			dataType : "JSON",
			success : function(response) {
				$.each(response, function(index, value) {
					resContact.push(value);
				});
			}
		});
		
		 $.ajax({
			   url : "./meterGeneration/checkCANumbers/"+prepaidId,
			   type: "GET",
			   dataType:"JSON",
			   success : function(response)
			   {	$.each(response, function(index, value) {
					resContact1.push(value);
				});
			   }

		});
		$('a[id="temPID"]').remove();
		flagColumnNameCode = true;
		$('div[data-container-for="prePaidId"]').remove();
		$('label[for="prePaidId"]').closest('.k-edit-label').remove();

		$('div[data-container-for="blockId"]').remove();
		$('label[for="blockId"]').closest('.k-edit-label').remove();

		$('div[data-container-for="propertyId"]').remove();
		$('label[for="propertyId"]').closest('.k-edit-label').remove();

		$('div[data-container-for="status"]').remove();
		$('label[for="status"]').closest('.k-edit-label').remove();

		$('div[data-container-for="personId"]').remove();
		$('label[for="personId"]').closest('.k-edit-label').remove();

		$('input[name="blocksName"]').attr('readonly', 'readonly');
		$('input[name="propertyName"]').attr('readonly', 'readonly');
		$('input[name="personName"]').attr('readonly', 'readonly');
		/************************* Button Alerts *************************/
		if (e.model.isNew()) {
			//securityCheckForActions("./Masters/AMRSettings/create");
			/* 	flagColumnNameCode = false;
				
				setApCode = $('input[name="prePaidId"]').val();
				$(".k-window-title").text("Create prePaid Meter");
				$(".k-grid-update").text("Save");	 */
		} else {

			//securityCheckForActions("./Masters/AMRSettings/update");
			meterNum = meterNumber;
			consumId = consumerId;
			$(".k-window-title").text("Edit prePaid Meter");
			$('.k-edit-field .k-input').first().focus();
			/* $(".k-grid-update").click(function () 
					  {
				 //var meterNumber=$("#meterNumber").val();
					//alert($("#meterNumber").val());
					 $.ajax({
						   url : "./updateUrl",
						   type: "GET",
						   dataType:"text",
						   success : function(response)
						   {	alert(response);
									// alert("Already Meter Assigned for these Property !");
									 //$("#meterNumber").val("");
									 return false;
						     	
						   }

					});
				  });; */
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
				$("#alertsBox").html(" Meter updated successfully");
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

	/**	function towerNameEditor(container, options) 
	 {
	 $('<input name="Tower Name" id="blockId" data-text-field="blocksName" data-value-field="blockId" data-bind="value:' + options.field + '" required="true"/>')
	 .appendTo(container).kendoComboBox({
	 autoBind : false,			
	 dataSource : {
	 transport : {		
	 read :  "${getAllBlockUrl}"
	 }
	 },
	 change : function (e) {
	 if (this.value() && this.selectedIndex == -1) {                    
	 alert("Block doesn't exist!");
	 $("#blockId").data("kendoComboBox").value("");
	 }
	
	 }  
	 });
	
	 $('<span class="k-invalid-msg" data-for="Tower Name"></span>').appendTo(container);
	 }

	 function propertyEditor(container, options) 
	 {
	 $('<input name="Propery Name" id="property_No" data-text-field="propertyName" data-value-field="propertyId" data-bind="value:' + options.field + '" required="true"/>')
	 .appendTo(container).kendoComboBox({
	 cascadeFrom : "blockId", 
	 autoBind : false,			
	 dataSource : {
	 transport : {		
	 read :  "${getAllPropertyUrl}"
	 }
	 },
	 change : function (e) {
	 if (this.value() && this.selectedIndex == -1) {                    
	 alert("P doesn't exist!");
	 $("#property_No").data("kendoComboBox").value("");
	 }
	
	 }  
	 });
	
	 $('<span class="k-invalid-msg" data-for="Propery Name"></span>').appendTo(container);
	 }
	
	 function PersonNames(container, options) 
	 {
	 $('<input name="personNameEE" id="hello1" data-text-field="personName" data-value-field="personId" data-bind="value:' + options.field + '"/>')
	 .appendTo(container).kendoComboBox({
	 autoBind : false,
	 placeholder : "Select Person",
	 cascadeFrom: "property_No",
	 headerTemplate : '<div class="dropdown-header">'
	 + '<span class="k-widget k-header">Photo</span>'
	 + '<span class="k-widget k-header">Contact info</span>'
	 + '</div>',
	 template : '<table><tr><td rowspan=2><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.personId#'/>" width=40 height=55 alt=\"No Image to Display\" /></span></td>'
	 + '<td align="left"><span class="k-state-default"><b>#: data.personName #</b></span><br>'
	 + '<span class="k-state-default"><i>#: data.personStyle #</i></span><br>'
	 + '<span class="k-state-default"><i>#: data.personType #</i></span></td></tr></table>',
	 dataSource: {  
	 transport:{
	 read: "${personNamesAutoBasedOnPersonTypeUrl}"
	 }
	 },
	 change : function (e) {
	 if (this.value() && this.selectedIndex == -1) {                    
	 alert("Person doesn't exist!");
	 $("#hello1").data("kendoComboBox").value("");
	 }
	 }
	 });
	 $('<span class="k-invalid-msg" data-for="personNameEE"></span>').appendTo(container);
	 }

	
	 */

	$("#grid")
			.on(
					"click",
					".k-grid-ConsumerTemplatesDetailsExport",
					function(e) {

						window
								.open("./ConsumerTemplate/ConsumerTemplatesDetailsExport");
					});

	$("#grid")
			.on(
					"click",
					".k-grid-uploadConsumerDetails",
					function(e) {

						var result = securityCheckForActionsForStatus("./BillGeneration/GenerateBills/uploadBatchFile");
						if (result == "success") {
							var xlsDialog = $("#uploadTransactionFileDialog");
							xlsDialog.kendoWindow({
								width : "400",
								height : "150",
								modal : true,
								draggable : true,
								position : {
									top : 80
								},
								title : "Upload Batch Bills"
							}).data("kendoWindow").center().open();
							xlsDialog.kendoWindow("open");
						}
					});

	$("#batchFile").kendoUpload({
		multiple : true,
		upload : onUploadXLS,
		success : onDocSuccessBatch,
		error : errorRegardingUploadExcel,
		async : {
			saveUrl : "./prepaidConsumerData/uploadPrePaidConsumerData",
			autoUpload : true
		}
	});

	function errorRegardingUploadExcel(e) {
		var windowOwner = $("#uploadTransactionFileDialog").data("kendoWindow");
		windowOwner.close();

		var errorInfo = "Uploaded file does not contain valid data";

		alert(errorInfo);
		/* 	$("#alertsBox").html("");
			$("#alertsBox").html(errorInfo);
			$("#alertsBox").dialog({
				modal : true,
				draggable: false,
				resizable: false,
				buttons : {
					"Close" : function() {
						$(this).dialog("close");
					 	window.location.reload();
					}
				}
			}); */

	}

	function onUploadXLS(e) {
		var files = e.files;
		$
				.each(
						files,
						function() {
							if (this.extension.toLowerCase() != ".xls") {
								alert(" Invalid Document Type:\n Acceptable formats is .xls Only");
								e.preventDefault();
								return false;
							}
						});
	}

	function onDocSuccessBatch() {
		alert("File Imported Successfully");
		window.location.reload();
	}

	(function($) {
		$("#billingWizardPopUp").hide();

		$("#blockName").kendoDropDownList({
			autoBind : false,
			optionLabel : "Select",
			dataTextField : "blockName",
			dataValueField : "blockName",
			dataSource : {
				transport : {
					read : "${getAllBlockUrl}"
				}
			}
		});

		$("#propertyId").kendoDropDownList({
			autoBind : false,
			optionLabel : "Select",
			cascadeFrom : "blockName",
			dataTextField : "property_No",
			dataValueField : "propertyId",
			dataSource : {
				transport : {
					read : "${getAllPropertyUrl}"
				}
			}
		});

		$("#personId")
				.kendoComboBox(
						{
							autoBind : false,
							placeholder : "Select Person",
							cascadeFrom : "propertyId",
							dataTextField : "personName",
							dataValueField : "personId",
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
									read : "${personNamesAutoBasedOnPersonTypeUrl}"
								}
							},
							change : function(e) {
								if (this.value() && this.selectedIndex == -1) {
									alert("Person doesn't exist!");
									$("#person").data("kendoComboBox")
											.value("");
								}
							}
						});

		window.prettyPrint && prettyPrint();
	})(jQuery);
	var flag = "";
	var flag1 = "";
	(function($, kendo) {
		$
				.extend(
						true,
						kendo.ui.validator,
						{
							rules : { // custom rules          	

								contactContentValidation : function(input,
										params) {
									//check for the name attribute 
									if (input.filter("[name='meterNumber']").length
											&& input.val()) {
										contactContents = input.val();
										$
												.each(
														resContact,
														function(ind, val) {

															if (meterNum == contactContents) {

																meterNum = "";
																return false;
															} else {

																if ((contactContents == val)
																		&& (contactContents.length == val.length)) {
																	flag = contactContents;
																	return false;
																}
															}
														});
									}
									return true;
								},

								contactContentUniqueness : function(input) {
									if (input.filter("[name='meterNumber']").length
											&& input.val() && flag != "") {
										flag = "";
										return false;
									}
									return true;
								},
							
							consumNumberValidation : function(input,
									params) {
								//check for the name attribute 
								if (input
										.filter("[name='consumerId']").length
										&& input.val()) {
									contactContents = input.val();
									$
											.each(
													resContact1,
													function(ind, val) {

														if (consumId == contactContents) {
															
															consumId = "";
															return false;
														} else {
                                                                 
															if ((contactContents == val)
																	&& (contactContents.length == val.length)) {
																flag1 = contactContents;
																return false;
															}
														}
													});
								}
								return true;
							},

							ConsumNumbertUniqueness : function(input) {
								if (input
										.filter("[name='consumerId']").length
										&& input.val() && flag1 != "") {
									flag1 = "";
									return false;
								}
								return true;
							} ,
							consumerNumberValidation : function(input, params) {
								if (input.attr("name") == "consumerId") {
									return $.trim(input.val()) !== "";
								}
								return true;

							}
							},
							messages : {
								//custom rules messages
								contactContentUniqueness : "Meter Number already exists",
								consumerNumberValidation : "Consumer Id is required",
								ConsumNumbertUniqueness : "Consumer ID already exists",
							}
						});

	})(jQuery, kendo);
</script>

 <style>
.container {
	float: left;
	margin-left: 0px;
	min-height: 0px;
	width:150px;
}


.form-horizontal .controls {
    margin-left: 164px;
}


.tab-content > .active, .pill-content > .active {
    display: block;
   min-height: 330px;
}


.ui-dialog-content .ui-widget-content{
 	display: block;
    height: 561px;
    min-height: 0;
    width: 967px;
}

.pager {
    list-style: outside none none;
    margin: 0;
    }
</style> 