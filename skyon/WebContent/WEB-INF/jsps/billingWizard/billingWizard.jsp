<%@include file="/common/taglibs.jsp"%>

<link	href="<c:url value='/resources/twitter-bootstrap-wizard/bootstrap/css/bootstrap.min.css'/>"	rel="stylesheet" />
<%-- <link	href="<c:url value='/resources/twitter-bootstrap-wizard/prettify.css'/>" rel="stylesheet" /> --%>

<script type="text/javascript"	src=" <c:url value='/resources/twitter-bootstrap-wizard/bootstrap/js/bootstrap.min.js'/>"></script>
<script type="text/javascript"	src=" <c:url value='/resources/twitter-bootstrap-wizard/jquery.bootstrap.wizard.js'/>"></script>
<script type="text/javascript"	src=" <c:url value='/resources/twitter-bootstrap-wizard/prettify.js'/>"></script>

<c:url value="/billingWizard/billingWizardDataRead" var="billingWizardDataUrl" />
<c:url value="/openNewTickets/readTowerNames" var="towerNames" />
<c:url value="/openNewTickets/readPropertyNumbers" var="propertyNum" />
<c:url value="/asset/getstaff1" var="personUrl1" />
<c:url value="/billingWizard/commonFilterForAccountNumbersUrl" var="commonFilterForAccountNumbersUrl" />
<c:url value="/billingWizard/commonFilterForPropertyNoUrl" var="commonFilterForPropertyNoUrl" />

<c:url value="/servicetasks/getServiceType" var="getServiceType"/>
<c:url value="/common/getAllChecks" var="allChecksUrl" />
<c:url value="/billingWizard/readServiceRouteNames" var="serviceRouteNamesUrl" />
<c:url value="/billingWizard/readServiceSubRouteNames" var="serviceRouteSubNamesUrl" />

<c:url value="/openNewTickets/getPersonListBasedOnPropertyNumbers" var="personNamesAutoBasedOnPersonTypeUrl" />
<c:url value="/billingWizard/readMeterNumbers" var="readMeterNumbers" />
<c:url value="/common/getAllChecks" var="allChecksUrl" />
<c:url value="/billingWizard/commonFilterForWizaradUrl" var="commonFilterForWizaradUrl" />
<c:url value="/serviceMasters/filter" var="commonFilterForServiceMasterUrl" />
<c:url value="/serviceMasters/tariffList" var="tariffList" />
<c:url value="/billingPayments/getPersonListForFileter" var="personNamesFilterUrl" />

<kendo:grid name="grid" resizable="true" pageable="true" selectable="true" sortable="true" dataBound="billingWizardDataBound" scrollable="true" groupable="true">
	<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" refresh="true" info="true" previousNext="true">
		<kendo:grid-pageable-messages itemsPerPage="Items per page" empty="No Items to display" refresh="Refresh all the Items" display="{0} - {1} of {2} New Items" first="Go to the first page of Items"
			last="Go to the last page of Items"	next="Go to the next page of Items"	previous="Go to the previous page of Items" />
	</kendo:grid-pageable>
	<kendo:grid-filterable extra="false">
		<kendo:grid-filterable-operators>
			<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains" />
			<kendo:grid-filterable-operators-date gt="Is after" lt="Is before" />
		</kendo:grid-filterable-operators>

	</kendo:grid-filterable>
	<kendo:grid-editable mode="popup" />
	<kendo:grid-toolbar>
		<kendo:grid-toolbarItem name="billingWizardEvent" text="Add New Customer"/>
		<kendo:grid-toolbarItem
			template="<a class='k-button' href='\\#' onclick=clearFilterBillingWizard()><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>" />
	</kendo:grid-toolbar>

	<kendo:grid-columns>

		<kendo:grid-column title="wizardId" field="wizardId" width="110px" hidden="true" />

		<%-- <kendo:grid-column title="Account" field="accountNo" filterable="false" width="150px">
		</kendo:grid-column> --%>
		
		<kendo:grid-column title="Account&nbsp;Number" field="accountNo" filterable="true" width="130px">
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
		
		<kendo:grid-column title="Service&nbsp;Type" field="typeOfService" filterable="true" width="130px">
		<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function apCodeFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Servicetype",
									dataSource : {
										transport : {
											read : "${commonFilterForServiceMasterUrl}/typeOfService"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
		</kendo:grid-column>

		<kendo:grid-column title="Person&nbsp;Name" field="personName" filterable="true" width="150px">
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
		
		<kendo:grid-column title="Property&nbsp;Number" field="property_No" filterable="true" width="130px">
		<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function ledgerTypeFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Property Number",
									dataSource : {
										transport : {
											read : "${commonFilterForPropertyNoUrl}"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	    </kendo:grid-column>

		<kendo:grid-column title="Status" field="status" filterable="true" width="130px">
		<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function ledgerTypeFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Status",
									dataSource : {
										transport : {
											read : "${commonFilterForWizaradUrl}/status"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
		</kendo:grid-column>

		<%-- <kendo:grid-column title="Electricity&nbsp;Ledger" field="elLedgerid" filterable="false" width="130px">
		</kendo:grid-column>
		
		<kendo:grid-column title="Electricity&nbsp;Meter" field="meterSerialNo" filterable="false" width="150px">
		</kendo:grid-column>

		<kendo:grid-column title="Electricity&nbsp;Meter" field="elMeterId" filterable="false" width="130px">
		</kendo:grid-column>
		
		<kendo:grid-column title="Service&nbsp;Route" field="routePlan" filterable="false" width="150px">
		</kendo:grid-column>

		<kendo:grid-column title="Service&nbsp;Route" field="srId" filterable="false" width="130px">
		</kendo:grid-column>

		<kendo:grid-column title="Service&nbsp;Point" field="servicePointId" filterable="false" width="130px">
		</kendo:grid-column> --%>

		<kendo:grid-column title="&nbsp;" width="160px">
			<kendo:grid-column-command>
				<kendo:grid-column-commandItem name="Approve" click="approvedAccountClick"/>
			</kendo:grid-column-command>
		</kendo:grid-column>

		<%-- <kendo:grid-column title=""
			template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Active#=data.topicId#'>#= data.status == 'Active' ? 'Inactivate' : 'Activate' #</a>"
			width="80px" /> --%>
	</kendo:grid-columns>

	<kendo:dataSource pageSize="20">
		<kendo:dataSource-transport>
			<%-- <kendo:dataSource-transport-create url="${helpTopicCreateUrl}" dataType="json" type="GET" contentType="application/json" /> --%>
			<kendo:dataSource-transport-read url="${billingWizardDataUrl}" dataType="json" type="POST" contentType="application/json" />
			<%-- <kendo:dataSource-transport-update url="${helpTopicUpdateUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-destroy url="${helpTopicDestroyUrl}/" dataType="json" type="GET" contentType="application/json" /> --%>
		</kendo:dataSource-transport>

		<kendo:dataSource-schema>
			<kendo:dataSource-schema-model id="wizardId">
				<kendo:dataSource-schema-model-fields>

					<kendo:dataSource-schema-model-field name="wizardId" type="number">
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="accountNo" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="typeOfService" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="property_No" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="personName" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="accountId" type="number">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="status" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="spmName" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="spmId" type="number">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="elLedgerid" type="number">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="meterSerialNo" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="elMeterId" type="number">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="routePlan" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="srId" type="number">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="servicePointId" type="number">
					</kendo:dataSource-schema-model-field>

				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>

	</kendo:dataSource>
</kendo:grid>

<c:if test="${not empty error}">
	<script>
		$(document).ready(function() {
			var results = "${error}";
			alert(results);
		});
	</script>
	<c:remove var="results" scope="session" />
</c:if>

<div id="billingWizardPopUp">

<div class='container'>
	<div class="span12" style=" width: 780px;">
		<section id="wizard">
<form:form id="commentForm" method="POST" action="./billingWizardCreate" class="form-horizontal" commandName="billingWizardBean" modelAttribute="billingWizardBean" autocomplete="off">
  <div id="rootwizard" class="tabbable tabs-left">
					<div id="bar" class="progress progress-striped active">
					  <div class="bar"></div>
					</div>
					<div class="slimScrollDiv" style="position: relative; overflow: hidden; width: auto; /* height: 500px; */"><ul class="nav nav-tabs" style="overflow: hidden; width: auto; /* height: 500px; */">
						<li><a href="#tab1" data-toggle="tab" style="width: 168px;">Customer&nbsp;Information</a></li>
						<li><a href="#tab2" data-toggle="tab" style="width: 168px;">Account&nbsp;Information</a></li>
						<li><a href="#tab3" data-toggle="tab" style="width: 168px;">Service&nbsp;Parameter&nbsp;</a></li>
						<!-- <li><a href="#tab7" data-toggle="tab">Service&nbsp;Point&nbsp;</a></li> -->
						<li><a href="#tab5" data-toggle="tab" style="width: 164px;">Meter&nbsp;Information</a></li>
						<!-- <li><a href="#tab6" data-toggle="tab">Service&nbsp;Route&nbsp;</a></li> -->
						
					</ul></div>
					<div class="tab-content" >
					
				        <div class="tab-pane" id="tab1" style="width: min-height: 278px;">
							<h3 style="text-align:center;">Customer Information</h3>
							<br>
							<table>
								<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="blockName">Block&nbsp;Name&nbsp;*</label>
											<div class="controls">
												<%-- <form:input type="text" id="blockName" path="blockName"	class="required blockName"/> --%>
												<select id="blockName" name="blocks" class="required blockName" required="required" class="validate[required]" data-required-msg="Select Block"></select>
											</div>
										</div>
									</td>
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
									<td>
										<div class="control-group">
											<label class="control-label" for="serviceType">Service&nbsp;Type&nbsp;*</label>
											<div class="controls">
												<select id="serviceType" name="serviceType" data-required-msg="Select Service Type" onchange="parameterChange()" style="overflow:hidden;"></select>
											</div>
										</div>
									</td>
								</tr>
								<tr>
								<td>
										<div class="control-group">
											<label class="control-label" for="serviceMetered">Service&nbsp;Metered&nbsp;*</label>
											<div class="controls">
												<%-- <form:input type="text" id="serviceMetered" path="serviceMetered" class="required serviceMetered"/> --%>
												<select id="serviceMetered" name="serviceMetered" data-required-msg="Select Service Metered" style="overflow:hidden;" onchange="changeMeteredValue()" >
													<option></option>
													<option>No</option>
													<option>Yes</option>
												</select>
											</div>
										</div>
									</td>
								
									<td>
										<div id="toddiv" style="display: none" class="control-group">
											<label class="control-label" for="todApplicable">TOD&nbsp;Applicable&nbsp;*</label>
											<div class="controls">
												<kendo:dropDownList id="todApplicable" name="todApplicable" data-required-msg="Select TOD Applicable" onchange="electricityTariff()" style="overflow:hidden;"/>
													<!-- <option>Yes</option>
													<option>No</option>
												</select> -->
											</div>
										</div>
									</td>
								</tr>
								<tr>
								<td>
										<div class="control-group">
											<label class="control-label" for="elTariffID">Tariff&nbsp;Name&nbsp;*</label>
											<div class="controls">
												<%-- <form:input type="text" id="personId" path="personId" class="required personId"/> --%>
												<select id="elTariffID" name="elTariffID" data-required-msg="Select Tariff Name" ></select>
											</div>
										</div>
									</td>	
							</tr>
							</table>
						</div>
				        	
						<div class="tab-pane" id="tab2" style="width: min-height: 278px;">
							<h3 style="text-align:center;">Account Information</h3>
							<br>
							<table>
								<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="accountNo">Account&nbsp;Number&nbsp;*</label>
											<div class="controls"> <!--  readonly="true" -->
												<form:input type="text"  id="accountNo" cssStyle="width: 159px;" path="accountNo" class="control_acount_number" readonly="true"/>
											</div>
										</div>
									</td>
									<!-- <td class="control_account_type"><br/>
										<div class="control-group">
											<label class="control-label" for="accountType">Account&nbsp;Type&nbsp;</label>
											<div class="controls">
												<select id="accountType" name="accountType" data-required-msg="Select Type"></select>
											</div>
										</div>
									</td> -->
								</tr>
								<tr>
									<td>
										<div class="control-group">
											<div class="controls">
												<input type="button" style="height: 22px; border:1px solid; text-align:center; margin-left: 0px;  margin-top: 36px; width: 172px; color:#00f; background-color:#ddd;" id="autoGenerate" class="autoAccountGenerate" value="Create Account Number" onclick="generateAccountNumber()"/>
											</div>
										</div>
									</td>
								</tr>
							</table>
						</div>
						
						<div class="tab-pane" id="tab3" style="width: min-height: 350px;">
							<h3 style="text-align:center;">Service&nbsp;Parameter&nbsp;Details</h3>
							<br/>
									<table id="divleft"  style="width: 237px; margin-left: 76px;">
											<thead>
												<tr align="left">
													<th><b>Parameter</b>&nbsp;&nbsp;&nbsp;&nbsp;</th>
													<th><b>Value</b></th>
												</tr>
											</thead>
									</table>
								
									<table  class="table1" style="margin-top: -132px; height:500px; margin-left:-110px;">
										<tr>
											<td>
												<table id="list-order" style="margin-top:154px; margin-left: 165px; width: 293px;  height: 184px;">
													<tbody><tr></tr></tbody>
												</table>
											</td>
										</tr>
									</table>
						
								
									<table  id="divright" style="width: 247px; margin-left: 423px;margin-top: -385px;">
											<thead>
												<tr id="divtr" align="left">
													<th><b>Parameter</b>&nbsp;&nbsp;&nbsp;&nbsp;</th>
													<th><b>Value</b></th>
												</tr>
											</thead>
									</table>
								
									<table  class="table2" style="margin-top: -47px;margin-left: 274px;">
											<tr>
												<td>
													<table id="list-order-second" style="margin-top:70px; margin-left: 130px;width: 293px; height: 184px;">
														<tbody><tr></tr></tbody>
													</table>
												</td>
											</tr>
									</table>
						</div>
						<div class="tab-pane" id="tab5">
							<h3 style="text-align:center;">Meter&nbsp;Information</h3>
							<br>
							<table id="listMeterTable">
							<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="elMeterId">Meter&nbsp;Number&nbsp;*</label>
											<div class="controls">
											<select id="elMeterId" name="elMeterId" data-required-msg="Select Meter Number" style="overflow:hidden;"></select>
												<%-- <form:input type="text" id="elMeterId" data-required-msg="Select Meter Number" path="elMeterId"/> --%>
											</div>
										</div>
									</td>
									<td>
										<div class="control-group">
											<label class="control-label" for="fixedDate">Fixed&nbsp;Date&nbsp;*</label>
											<div class="controls">
												<form:input type="text" id="fixedDate" path="fixedDate" style="padding: 0px; margin: 0px; width: 76%; height: 26px;"/>
												<%-- <kendo:datePicker format="yyyy/MM/dd" name="fixedDate" style="padding: 0px; margin: 0px; width: 99%; height: 26px;" id="fixedDate" parse="parse()" value="${today}"
     											 required="required" class="validate[required]"/> --%>
											</div>
											
										</div>
									</td>
							</tr>
							<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="fixedBy" >Fixed&nbsp;By&nbsp;*</label>
											<div class="controls">
												<form:input type="text" id="fixedBy" cssStyle="width: 159px;" path="fixedBy"/>
											</div>
										</div>
									</td>
							</tr>
							</table>
						</div>
						
						<div class="tab-pane" id="tab6">
							<h3 style="text-align:center;">Service&nbsp;Route&nbsp;Plan</h3>
							<br>
							<table>
							<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="srId">Service&nbsp;Route&nbsp;Name&nbsp;*</label>
											<div class="controls">
												<form:input type="text" id="srId" path="srId"/>
											</div>
										</div>
									</td>
									
									<td>
										<div class="control-group">
											<label class="control-label" for="subRouteName">Sub&nbsp;Route&nbsp;Name&nbsp;*</label>
											<div class="controls">
												<form:input type="text" id="subRouteName" path="subRouteName"/>
											</div>
											<span id="commitplaceholder" style="display: none;"><img src="./resources/images/loadingimg.GIF" style="vertical-align:middle;margin-left: 50px"/> &nbsp;&nbsp;
					        <img src="./resources/images/loading.GIF" alt="loading" style="vertical-align:middle margin-left: 50px" height="20px" width="200px; "/></span>
										</div>
									</td>
							</tr>
							
							<tr>
									<%-- <td>
										<div class="control-group">
											<label class="control-label" for="readCycle">Read&nbsp;Cycle&nbsp;</label>
											<div class="controls">
												<form:input type="text" id="readCycle" path="readCycle" class="required readCycle"/>
											</div>
										</div>
									</td> --%>
									
									<td>
										<div class="control-group">
											<label class="control-label" for="personIdServiceRoute">Staff&nbsp;Name&nbsp;*</label>
											<div class="controls">
												<%-- <form:input type="text" id="personId" path="personId" class="required personId"/> --%>
												<select id="personIdServiceRoute" name="personIdServiceRoute" data-required-msg="Select Person"></select>
											</div>
										</div>
									</td>
							</tr>
							</table>
						</div>
						
						<div class="tab-pane" id="tab7">
							<h3 style="text-align:center;">Service&nbsp;Point&nbsp;</h3>
							<br>
							<table>
							<tr>
								   <td>
										<div class="control-group">
											<label class="control-label" for="serviceLocation">Service&nbsp;Point&nbsp;Location&nbsp;*</label>
											<div class="controls">
												<form:input type="text" id="serviceLocation" cssStyle="width: 159px;" path="serviceLocation"/>
											</div>
										</div>
									</td>
									<td>
										<div class="control-group">
											<label class="control-label" for="commissionDate" >Commission&nbsp;Date&nbsp;*</label>
											<div class="controls">
											<%-- <kendo:datePicker format="dd/MM/yyyy" name="commissionDate" id="commissionDate" parse="parse()" value="${today}"   style="padding: 0px; margin: 0px; width: 99%; height: 26px;"
     											 required="required" class="validate[required]"/> --%>
												<form:input type="text" id="commissionDate" style="padding: 0px; margin: 0px; width: 76%; height: 26px;" path="commissionDate"/>
											</div>
										</div>
									</td>
							</tr>
							<%-- <tr>
								<td>
										<div class="control-group">
											<label class="control-label" for="serviceMetered">Service&nbsp;Metered&nbsp;*</label>
											<div class="controls">
												<form:input type="text" id="serviceMetered" path="serviceMetered" class="required serviceMetered"/>
												<select id="serviceMetered" name="serviceMetered" data-required-msg="Select Service Metered" style="overflow:hidden;" onchange="changeMeteredValue()">
													<option>No</option>
													<option>Yes</option>
												</select>
											</div>
										</div>
									</td>
							</tr> --%>
							</table>
						</div>
						
						<ul class="pager wizard">
							<li class="previous"><a href="#">Previous</a></li>
							<li class="next"><a href="#">Next</a></li>
							<li class="next finish" style="display:none;"><a><input type="submit" value="Submit"></a></li>
							</ul>
					</div>					
				</div>
</form:form> 
		</section>
	</div>
</div>
</div>

<div id="alertsBox" title="Alert"></div>
<script>

function billingWizardDataBound(e) {
	var data = this.dataSource.view(),row;
	var grid = $("#grid").data("kendoGrid");
    for (var i = 0; i < data.length; i++) {
    	var currentUid = data[i].uid;
        row = this.tbody.children("tr[data-uid='" + data[i].uid + "']");
        
        var wizardStatus = data[i].status;
        if (wizardStatus == 'Approved') {
			var currenRow = grid.table.find("tr[data-uid='" + currentUid+ "']");
			
			var approveButton = $(currenRow).find(".k-grid-Approve");
			approveButton.hide();
		}
    }
}

function onSubmittion(){
	$('#commitplaceholder').hide();
	 alert("Data Submitted Successfully");
}
function generateAccountNumber(){
	 
		$.ajax({
			   url : "./billingWizard/autogenerateaccno",
			   type: "GET",
			   contentType: "application/json; charset=utf-8",
			   dataType:"text",
			   success : function(response)
			   {	
				   $(".control_acount_number").empty();
				  	 var resp=""+response+"";
				    // var accountNumber=resp.split(',');  
				     $('input[id="accountNo"]').val(response);
			   }

		});
	
}
var serviceMetered = "";
function changeMeteredValue(){
	serviceMetered=$("#serviceMetered").val();
	if(serviceMetered=='Yes'){
		 $("#listMeterTable").show();
	}else{
		 $("#listMeterTable").hide();
	}
	
}

$(document).ready(function() 		
		{
	/*  var data = [
                 { text: "Yes", value: "Yes" },
                 { text: "No", value: "No" },
                 
                
             ];
	  
	 $("#todApplicable").kendoDropDownList({
		 dataTextField: "text",
         dataValueField: "value",
         optionLabel : {
				text : "Select",
				value : "",
			},
			
			dataSource : data
}).data("kendoDropDownList");*/ 
	
		}); 
		
function electricityTariff(){
	var todtype=$("#todApplicable").val();	
	var comboBoxDataSource = new kendo.data.DataSource({
        transport: {
            read: {
                url     : "./serviceMasters/electricityTariffList/"+service+"/"+todtype,
                dataType: "json",
                type    : 'GET'
            }
        },
	           
	 });
	        
 $("#elTariffID").kendoComboBox({
    dataSource    : comboBoxDataSource,
    optionLabel : "Select",
    placeholder: "Select Tariff Name",
    dataTextField : "tariffName",
    dataValueField: "elTariffID",
});
 $("#elTariffID").data("kendoComboBox").value("");	
}

var accountNumber = "";
function accountNumberChange(){
	var personId=$("#personId").val();
	var propertyId=$("#propertyId").val();
	$.ajax({
		   url : "./billingWizard/accountNumber?personId="+personId+"&propertyId="+propertyId,
		   type: "GET",
		   contentType: "application/json; charset=utf-8",
		   dataType:"json",
		   success : function(response)
		   {	
			   
				 if(response.length==0){
					 $(".control_acount_number").empty();
					 $("#autoGenerate").show();
					 
				 }else{
					 alert("Already you have a account !");
					 //$(".control_acount_number").empty();
					 $("#autoGenerate").hide();
					 var resp=""+response+"";
					 for(var i=0;i<=response.length-1;i++){
				     		accountNumber=resp.split(',');  
				     		$('input[id="accountNo"]').val(accountNumber);
				     		
				     	 }
				 }
		     	
		   }

	});
	
}

var service ="";
function parameterChange(){
	
	service=$("#serviceType").val();
	
	$.ajax({
		   url : "./billingWizard/accountCheck/"+service+"/"+accountNumber,
		   type: "GET",
		   contentType: "application/json; charset=utf-8",
		   dataType:"json",
		   success : function(response)
		   {	   
			  if(response==false){
				  alert("Already account is there with this "+service+" service type");
	              $("#personId").data("kendoComboBox").value("");
	              $("#serviceType").data("kendoDropDownList").value("");
			  }
		   }
	}); 

	if(!$("#serviceType").val()){
			 $("#tab3").hide();
		  	} 
	
	if(service=="Electricity"){
		$("#toddiv").show();
	}else{
		$("#toddiv").hide();
	}
	$.ajax({
		   url : "./servicetasks/serviceParamName?serviceType="+service,
		   type: "GET",
		   contentType: "application/json; charset=utf-8",
		   dataType:"json",
		   success : function(response)
		   {	   
			  
			   $("#list-order > tbody > tr").empty();
			   $("#list-order-second > tbody > tr").empty();
			   if(response.length==0){
				   $("#divleft").hide();
				   $("#divright").hide();
				   $("#list-order").hide();
				   $("#list-order-second").hide();
				   	var rows="";
			     	 for(var i=0;i<=response.length-1;i++){
			     		 var resp=""+response+"";
						 var parameter=resp.split(',');
			     		if(i%2==0){
			     			rows = "<tr><td style='bottom: 17px; display: block; height: 10px;  text-indent: 17px; width:143px; id='parameterId'>"+parameter[i]+"</td><td></td><td><input name='parameterValue[" +i+ "]' id='validateParameterValue' onkeyup='javascript:validateFiled(this)' type='text' style='height: 20px; width:143px;'/></td></tr>";
			     			$('#list-order > tbody > tr').filter(":last").after(rows);
			     		}
			     		else{
			     			rows = "<tr><td style='bottom: 17px; display: block; height: 10px; text-indent: 17px; width:143px;'id='parameterId'>"+parameter[i]+"</td><td></td><td><input name='parameterValue[" +i+ "]' id='validateParameterValue' onkeyup='javascript:validateFiled(this)' type='text' style='height: 20px; width:143px;'/></td></tr>";
			     			$('#list-order-second > tbody > tr').filter(":last").after(rows);
							
			     		}
			     	 } 
				  
			   }else if(response.length==1){
					
				   $("#divleft").show();
				   $("#divright").hide();
				   $("#list-order").show();
				   $("#list-order-second").hide();
				  	 var rows="";
			     	 for(var i=0;i<=response.length-1;i++){
			     		 var resp=""+response+"";
						 var parameter=resp.split(',');
			     		if(i%2==0){
			     			rows = "<tr><td style='bottom: 17px; display: block; height: 10px;  text-indent: 17px; width:143px; id='parameterId'>"+parameter[i]+"</td><td></td><td><input name='parameterValue[" +i+ "]' id='validateParameterValue' onkeyup='javascript:validateFiled(this)' type='text' style='height: 20px; width:143px;'/></td></tr>";
			     			$('#list-order > tbody > tr').filter(":last").after(rows);
			     		}
			     		else{
			     			rows = "<tr><td style='bottom: 17px; display: block; height: 10px; text-indent: 17px; width:143px;'id='parameterId'>"+parameter[i]+"</td><td></td><td><input name='parameterValue[" +i+ "]' id='validateParameterValue' onkeyup='javascript:validateFiled(this)' type='text' style='height: 20px; width:143px;'/></td></tr>";
			     			$('#list-order-second > tbody > tr').filter(":last").after(rows);
							
			     		}
			     	 } 
				   
			   }else{
				   $("#divleft").show();
				   $("#divright").show();
				   $("#list-order").show();
				   $("#list-order-second").show();
			  	  
				 
				 var rows="";
		     	 for(var i=0;i<=response.length-1;i++){
		     		 var resp=""+response+"";
					 var parameter=resp.split(',');
		     		if(i%2==0){
		     			rows = "<tr><td style='bottom: 17px; display: block; height: 10px;  text-indent: 17px; width:143px; id='parameterId'>"+parameter[i]+"</td><td></td><td><input name='parameterValue[" +i+ "]' id='validateParameterValue' onkeyup='javascript:validateFiled(this)' type='text' style='height: 20px; width:143px;'/></td></tr>";
		     			$('#list-order > tbody > tr').filter(":last").after(rows);
		     		}
		     		else{
		     			rows = "<tr><td style='bottom: 17px; display: block; height: 10px; text-indent: 17px; width:143px;'id='parameterId'>"+parameter[i]+"</td><td></td><td><input name='parameterValue[" +i+ "]' id='validateParameterValue' onkeyup='javascript:validateFiled(this)' type='text' style='height: 20px; width:143px;'/></td></tr>";
		     			$('#list-order-second > tbody > tr').filter(":last").after(rows);
						
		     		}
		     	 } 
			   }
			  
		   }

	});
	
	if(service=="Electricity"){
		var todapplicable=$("#todApplicable").val();
		
		
		 var data = [
	                 { text: "Yes", value: "Yes" },
	                 { text: "No", value: "No" },
	                 
	                
	             ];
		  
		 $("#todApplicable").kendoDropDownList({
			 dataTextField: "text",
	         dataValueField: "value",
	         optionLabel : {
					text : "Select",
					value : "",
				},
				
				dataSource : data
	}).data("kendoDropDownList"); 
		
			
		
		
 		/* var comboBoxDataSource = new kendo.data.DataSource({
	            transport: {
	                read: {
	                    url     : "./serviceMasters/electricityTariffList/"+service,
	                    dataType: "json",
	                    type    : 'GET'
	                }
	            },
 		           
 		 });
 		        
         $("#elTariffID").kendoComboBox({
            dataSource    : comboBoxDataSource,
            optionLabel : "Select",
            placeholder: "Select Tariff Name",
            dataTextField : "tariffName",
            dataValueField: "elTariffID",
        });
         $("#elTariffID").data("kendoComboBox").value(""); */
         
         //serviceMetered = "Yes";
         
         $('select[name="serviceMetered"]').closest('span').closest('span').show();
   		$('label[for="serviceMetered"]').closest('label').show();
         
        $('input[name="elTariffID_input"]').closest('span').closest('span').show();
  		$('label[for="elTariffID"]').closest('label').show();
 	} 
	
	if(service=="Gas"){
 		var comboBoxDataSource = new kendo.data.DataSource({
	            transport: {
	                read: {
	                    url     : "./serviceMasters/gasTariffList/"+service,
	                    dataType: "json",
	                    type    : 'GET'
	                }
	            },
 		           
 		 });
 		        
         $("#elTariffID").kendoComboBox({
            dataSource    : comboBoxDataSource,
            optionLabel : "Select",
            placeholder: "Select Tariff Name",
            dataTextField : "tariffName",
            dataValueField: "elTariffID",
        });
         $("#elTariffID").data("kendoComboBox").value("");
         
        // serviceMetered = "Yes";
         
         $('select[name="serviceMetered"]').closest('span').closest('span').show();
  		$('label[for="serviceMetered"]').closest('label').show();
         
            $('div[data-container-for="todApplicable"]').hide();
	 		$('label[for="todApplicable"]').closest('.k-edit-label').hide();
	 		
	 		$('input[name="elTariffID_input"]').closest('span').closest('span').show();
	  		$('label[for="elTariffID"]').closest('label').show();
 	} 
 	
 	if(service=="Water"){
 		var comboBoxDataSource = new kendo.data.DataSource({
	            transport: {
	                read: {
	                    url     : "./serviceMasters/waterTariffList/"+service,
	                    dataType: "json",
	                    type    : 'GET'
	                }
	            },
 		           
 		 });
 		        
         $("#elTariffID").kendoComboBox({
            dataSource    : comboBoxDataSource,
            optionLabel : "Select",
            placeholder: "Select Tariff Name",
            dataTextField : "tariffName",
            dataValueField: "elTariffID",
        });
         $("#elTariffID").data("kendoComboBox").value("");
         
         //serviceMetered = "Yes";
         
         $('select[name="serviceMetered"]').closest('span').closest('span').show();
   		$('label[for="serviceMetered"]').closest('label').show();
         
         $('div[data-container-for="todApplicable"]').hide();
	 	$('label[for="todApplicable"]').closest('.k-edit-label').hide();
	 	
	 	$('input[name="elTariffID_input"]').closest('span').closest('span').show();
  		$('label[for="elTariffID"]').closest('label').show();
 	} 
 	
 	if(service=="Solid Waste"){
 		var comboBoxDataSource = new kendo.data.DataSource({
	            transport: {
	                read: {
	                    url     : "./serviceMasters/solidWasteTariffList/"+service,
	                    dataType: "json",
	                    type    : 'GET'
	                }
	            },
 		           
 		 });
 		        
         $("#elTariffID").kendoComboBox({
            dataSource    : comboBoxDataSource,
            optionLabel : "Select",
            placeholder: "Select Tariff Name",
            dataTextField : "tariffName",
            dataValueField: "elTariffID",
        });
         $("#elTariffID").data("kendoComboBox").value("");
         
         $("#listMeterTable").hide();
  		//serviceMetered = "No";
  		$('select[name="serviceMetered"]').closest('span').closest('span').hide();
  		$('label[for="serviceMetered"]').closest('label').hide();
         
         $('div[data-container-for="todApplicable"]').hide();
	 		$('label[for="todApplicable"]').closest('.k-edit-label').hide();
	 		
	 		$('input[name="elTariffID_input"]').closest('span').closest('span').show();
	  		$('label[for="elTariffID"]').closest('label').show();
 	} 
 	
 	if(service=="Others"){
 		var comboBoxDataSource = new kendo.data.DataSource({
	            transport: {
	                read: {
	                    url     : "./serviceMasters/othersTariffList/"+service,
	                    dataType: "json",
	                    type    : 'GET'
	                }
	            },
 		           
 		 });
 		        
         $("#elTariffID").kendoComboBox({
            dataSource    : comboBoxDataSource,
            optionLabel : "Select",
            placeholder: "Select Tariff Name",
            dataTextField : "tariffName",
            dataValueField: "elTariffID",
        });
         $("#elTariffID").data("kendoComboBox").value("");
         
        $("#listMeterTable").hide();
  		//serviceMetered = "No";
  		$('select[name="serviceMetered"]').closest('span').closest('span').hide();
  		$('label[for="serviceMetered"]').closest('label').hide();
         
         $('div[data-container-for="todApplicable"]').hide();
	 	 $('label[for="todApplicable"]').closest('.k-edit-label').hide();
	 	 
	 	$('input[name="elTariffID_input"]').closest('span').closest('span').show();
  		$('label[for="elTariffID"]').closest('label').show();
 	} 
 	
 	if(service=="Telephone Broadband"){
 		
		$("#listMeterTable").hide();
 	//	serviceMetered = "No";
 		$('select[name="serviceMetered"]').closest('span').closest('span').hide();
 		$('label[for="serviceMetered"]').closest('label').hide();
 		
 		$('div[data-container-for="todApplicable"]').hide();
 		$('label[for="todApplicable"]').closest('.k-edit-label').hide();
 		
 		$('input[name="elTariffID_input"]').closest('span').closest('span').hide();
 		$('label[for="elTariffID"]').closest('label').hide();
 	}
 	
 	if(service=="CAM"){
 		
 		$("#listMeterTable").hide();
 		
 		//serviceMetered = "No";
 		
 		$('div[data-container-for="todApplicable"]').hide();
 		$('label[for="todApplicable"]').closest('.k-edit-label').hide();
 		
 		$('input[name="elTariffID_input"]').closest('span').closest('span').hide();
 		$('label[for="elTariffID"]').closest('label').hide();
 		
 		$('select[name="serviceMetered"]').closest('span').closest('span').hide();
 		$('label[for="serviceMetered"]').closest('label').hide();
 	}
	
	meterType(service);
}
function meterType(service){
	
	 $("#elMeterId").kendoDropDownList({
	        //autoBind: false,
	        optionLabel : "Select",
	        dataTextField: "meterSerialNo",
	        dataValueField: "elMeterId",
	        dataSource: {  
	            transport:{
	            	read : {

						url : "./billingWizard/readMeterNumbers?service="+service,
					}
	            }
	        }
	    });
}

function validateFiled(validateParameterValue){
	validateParameterValue.value = validateParameterValue.value.replace(/[^0-9.\n\r]+/g, '');
	
}

function clearFilterBillingWizard()
{
   $("form.k-filter-menu button[type='reset']").slice().trigger("click");
   var gridBillingWizard = $("#grid").data("kendoGrid");
   gridBillingWizard.dataSource.read();
   gridBillingWizard.refresh();
}
 
 var wizardId="";
 function approvedAccountClick()
 {
 	var gridParameter = $("#grid").data("kendoGrid");
 	var selectedAddressItem = gridParameter.dataItem(gridParameter.select());
 	wizardId = selectedAddressItem.wizardId;
 	var result=securityCheckForActionsForStatus("./Accounts/ServiceOnBoard/approveButton"); 
	  if(result=="success"){  
 	$.ajax
 	({			
 		type : "POST",
 		url : "./billingWizard/approvedAccountNumber/"+wizardId,
 		dataType:"text",
 		success : function(response) 
 		{
 			$("#alertsBox").html("");
 			$("#alertsBox").html(response);
 			$("#alertsBox").dialog
 			({
 				modal : true,
 				buttons : 
 				{
 					"Close" : function() 
 					{
 						$(this).dialog("close");
 					}
 				}
 			});
 			$('#grid').data('kendoGrid').dataSource.read();
 		}
 	});
	  }
 }

var setApCode="";
$("#grid").on("click", ".k-grid-billingWizardEvent", function(e) {
	
	/* var div=$( "#billingWizardPopUp" );
	 div.dialog({
	    autoOpen: false,
	    height: 580,
	    width: 830,
	    title:"Enter Billing Information",
	    modal: true,
	    draggable: true,
	    resizable: true, 
	    
	  });
	 div.dialog( "open" ); 
	
		  }); */
		  var result=securityCheckForActionsForStatus("./Accounts/ServiceOnBoard/createButton"); 
		  if(result=="success"){   
		  var todcal=$( "#billingWizardPopUp" );
		  todcal.kendoWindow({
		      width: 835,
		      height: 'auto',
		      modal: true,
		      draggable: true,
		      position: { top: 100 },
		      title: "Account Information"
		  }).data("kendoWindow").center().open();

		   todcal.kendoWindow("open");
		  }
		  });


(function($) {
	$( "#billingWizardPopUp" ).hide();
	
	$("#blockName").kendoDropDownList({
        autoBind: false,
        optionLabel : "Select",
        dataTextField: "blockName",
        dataValueField: "blockName",
        dataSource: {  
            transport:{
                read: "${towerNames}"
            }
        }
    });
	
	/* var readCycle="readCycle";
	$("#readCycle").kendoDropDownList({
        autoBind: false,
        optionLabel : "Select",
        dataTextField: "text",
        dataValueField: "value",
        dataSource: {  
            transport:{
            	read:"${allChecksUrl}/"+readCycle,
            }
        }
    }); */
	
	var serviceType="serviceType";
	 $("#serviceType").kendoDropDownList({
        autoBind: false,
        optionLabel : "Select",
        dataTextField: "text",
        dataValueField: "value",
        dataSource: {  
            transport:{
            	read:"${allChecksUrl}/"+serviceType,
            }
        }
    });
	
	$("#propertyId").kendoDropDownList({
        autoBind: false,
        optionLabel : "Select",
        cascadeFrom: "blockName",
        dataTextField: "property_No",
        dataValueField: "propertyId",
        dataSource: {  
            transport:{
                read: "${propertyNum}"
            }
        }
    });
	
	$("#personId").kendoComboBox({
		autoBind : false,
		placeholder : "Select Person",
		cascadeFrom: "propertyId",
		dataTextField: "personName",
        dataValueField: "personId",
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
	               $("#person").data("kendoComboBox").value("");
	    }
	 } 
	});
	
	$("#elTariffID").kendoComboBox({
		autoBind : false,
		placeholder : "Select Tariff Name",
		dataTextField: "tariffName",
        dataValueField: "elTariffID",
		/* headerTemplate : '<div class="dropdown-header">'
			+ '<span class="k-widget k-header">Photo</span>'
			+ '<span class="k-widget k-header">Contact info</span>'
			+ '</div>',
		template : '<table><tr><td rowspan=2><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.personId#'/>" width=40 height=55 alt=\"No Image to Display\" /></span></td>'
			+ '<td align="left"><span class="k-state-default"><b>#: data.personName #</b></span><br>'
			+ '<span class="k-state-default"><i>#: data.personStyle #</i></span><br>'
			+ '<span class="k-state-default"><i>#: data.personType #</i></span></td></tr></table>', */
         dataSource: {  
             transport:{
                 read: "${tariffList}"
             }
         } 
	});
	
	/* $("#elMeterId").kendoDropDownList({
        //autoBind: false,
        optionLabel : "Select",
        dataTextField: "meterSerialNo",
        dataValueField: "elMeterId",
        dataSource: {  
            transport:{
            	read : {

					url : "./billingWizard/readMeterNumbers"
				}
            }
        }
    });  */
	
	$("#fixedDate").kendoDatePicker({
        format:"dd/MM/yyyy"
    });
	
	$("#commissionDate").kendoDatePicker({
        format:"dd/MM/yyyy"
      });
	
	$("#ledgerDate").kendoDatePicker();
	
	var serviceType="serviceType";
	$("#serviceType").kendoDropDownList({
		autoBind: false,
        optionLabel : "Select",
		dataTextField: "text",
        dataValueField: "value",
		dataSource : {
			transport : {
				read : "${allChecksUrl}/"+serviceType,
			}
		}
	}); 
	
	var serviceMeteredTest="serviceMetered";
	$("#serviceMetered").kendoDropDownList({
		autoBind: false,
        optionLabel : "Select",
		dataTextField: "text",
        dataValueField: "value",
        defaultValue:false,
		dataSource : {
			transport : {
				read : "${allChecksUrl}/"+serviceMeteredTest,
			}
		}
	}); 
	
	var todApplicable="todApplicable";
	$("#todApplicable").kendoDropDownList({
		autoBind: false,
        optionLabel : "Select",
		dataTextField: "text",
        dataValueField: "value",
		dataSource : {
			transport : {
				read : "${allChecksUrl}/"+todApplicable,
			}
		}
	}); 
	
	var accountType="accountType";
	$("#accountType").kendoDropDownList({
		autoBind: false,
        optionLabel : "Select",
		dataTextField: "text",
        dataValueField: "value",
		dataSource : {
			transport : {
				read : "${allChecksUrl}/"+accountType,
			}
		}
	});
	
	var ledgerType="ledgerType";
	$("#ledgerType").kendoDropDownList({
		autoBind: false,
        optionLabel : "Select",
		dataTextField: "text",
        dataValueField: "value",
		dataSource : {
			transport : {
				read : "${allChecksUrl}/"+ledgerType,
			}
		}
	});
	
	$("#srId").kendoDropDownList({
       // autoBind: false,
        optionLabel : "Select",
        dataTextField: "routeName",
        dataValueField: "srId",
        dataSource: {  
            transport:{
                read: "${serviceRouteNamesUrl}"
            }
        }
    });
	
//$(document).ready(function() {
		
		var serv = [ {
			text : "CAM",
			value : "CAM"
		}];
	
		$("#serviceType").kendoDropDownList({
        autoBind: false,
        optionLabel : "Select",
        dataTextField: "text",
      //  cascadeFrom: "srId",
        dataValueField: "value",
        dataSource: serv,
        
    });
	
	
	$("#subRouteName").kendoDropDownList({
        autoBind: false,
        optionLabel : "Select",
        dataTextField: "subRouteName",
        cascadeFrom: "srId",
        dataValueField: "srId",
        dataSource: {  
            transport:{
                read: "${serviceRouteSubNamesUrl}"
            }
        }
    });
	
	$("#personIdServiceRoute").kendoComboBox({
		autoBind : false,
		placeholder : "Select Person",
		dataTextField: "pn_Name",
        dataValueField: "personId",
		template : ''
				+ '<span class="k-state-default"><b>#:data.pn_Name#</b></span><br>'
				+ '<span class="k-state-default"><i>#:data.designation#, #:data.department# </i> </span><br>',
		
		dataSource : {
			transport : {
				read : "${personUrl1}"
			}
		},
		change : function(e) {
			if (this.value() && this.selectedIndex == -1) {
				alert("Staff doesn't exist!");
				$("#staff").data("kendoComboBox").value("");
			}
		}
	});
	
	var postType="postType";
	$("#postType").kendoDropDownList({
		autoBind: false,
        optionLabel : "Select",
		dataTextField: "text",
        dataValueField: "value",
		dataSource : {
			transport : {
				read : "${allChecksUrl}/"+postType,
			}
		}
	});
	
	$('#rootwizard').bootstrapWizard({
		onTabClick: function(tab, navigation, index) {
			alert('Please fill previous form');
			return false;
		}, 
		'tabClass' : 'nav nav-tabs',
		'onNext' : function(tab, navigation, index) {
			if(index==1) {
			    // Make sure we entered the name
			    if(!$('#blockName').val()) {
			     alert('Please select block name');
			     $('#blockName').focus();
			     return false;
			    }
			    
			    if(!$('#propertyId').val()) {
				     alert('Please select property number');
				     $('#propertyId').focus();
				     return false;
				    }
			    
			    if(!$('#personId').val()) {
				     alert('Please select person name');
				     $('#personId').focus();
				     return false;
				    }
			    
			    
			    if(!$('#serviceType').val()) {
				     alert('Please select service type');
				     $('#serviceType').focus();
				     return false;
				    }
			    
			    
			    
			    if(!$('#elTariffID').val()) {
			    	if($('#serviceType').val()=="CAM" || $('#serviceType').val()=="Telephone Broadband"){
			    		return true;
			    	}else{
			    		alert('Please select tariff name');
					     $('#elTariffID').focus();
					     return false;
			    	}
				}
			   var sMetered=$('#serviceMetered').val();			  
			   var sType=$("#serviceType").val();
			  
			   if(sType == "Electricity" || sType == "Water" || sType == "Gas"){
				  
			    if(!$('#serviceMetered').val()) {			    
				     alert('Please select service metered');
				     $('#serviceMetered').focus();
				     return false;
			    	
				    }
			   }
			   if(sType == "Electricity" ){					 
				    if(!$('#todApplicable').val()) {			    
					     alert('Please select Tod Applicable');
					     $('#todApplicable').focus();
					     return false;
				    	
					    }
				   }
			   
			   
			   
			   }
			
			if(index==2) {
			    // Make sure we entered the name
			    if(!$('#accountNo').val()) {
			     alert('Please enter account number');
			     $('#accountNo').focus();
			     return false;
			    }
			    
			    /* if(!$('#accountType').val()) {
				     alert('Please select account type');
				     $('#accountType').focus();
				     return false;
				    } */
			    
			   }
			
			/* if(index==4) {
			    // Make sure we entered the name
			    if(!$('#serviceMetered').val()) {
			     alert('Please select metered type');
			     $('#serviceMetered').focus();
			     return false;
			    }
			    if(!$('#commissionDate').val()) {
				     alert('Please enter commission date');
				     $('#commissionDate').focus();
				     return false;
				    }
				if(!$('#serviceLocation').val()) {
					     alert('Please enter service location');
					     $('#serviceLocation').focus();
					     return false;
					    }    
			   } */
			   
			   if(index==4) {
				    // Make sure we entered the name
				    if(serviceMetered=='Yes'){
				    	if(!$('#elMeterId').val()) {
		                       alert('Please select meter number');
		                       $('#elMeterId').focus();
		                       return false;
		   						}	
				    	if(!$('#fixedDate').val()) {
						     alert('Please eneter fixed date');
						     $('#fixedDate').focus();
						     return false;
						    }
						    if(!$('#fixedBy').val()) {
							     alert('Please enter fixed by name');
							     $('#fixedBy').focus();
							     return false;
							    }  
				    }
				    onSubmittion();
				   }
			   
			/* if(index==6) {
			    // Make sure we entered the name
			   if(!$('#srId').val()) {
				     alert('Please select route name');
				     $('#srId').focus();
				     return false;
				    }
			   if(!$('#subRouteName').val()) {
				     alert('Please select subroute name');
				     $('#subRouteName').focus();
				     return false;
				    }
			    if(!$('#personIdServiceRoute').val()) {
			     alert('Please select staff name');
			     $('#personIdServiceRoute').focus();
			     return false;
			    }
			    onSubmittion();
			    $('#commitplaceholder').show();
			   } */
			var $valid = $("#commentForm").valid();
			if (!$valid) {
				$validator.focusInvalid();
				return false;
			}
		},
		onTabShow: function(tab, navigation, index) {
			
		var $total = navigation.find('li').length;
		var $current = index+1;
		var $percent = ($current/$total) * 100;
		$('#rootwizard').find('.bar').css({width:$percent+'%'});
		if($current >= $total) {
			$('#rootwizard').find('.pager .next').hide();
			$('#rootwizard').find('.pager .finish').show();
			$('#rootwizard').find('.pager .finish').removeClass('disabled');
		} else {
			$('#rootwizard').find('.pager .next').show();
			$('#rootwizard').find('.pager .finish').hide();
		}
	}});	
	$('#rootwizard .finish').click(function() {
		$('#rootwizard').find("a[href*='#test']").trigger('click');
	});  
window.prettyPrint && prettyPrint();
})(jQuery);



</script>

<style>
.container {
	float: left;
	margin-left: 0px;
	min-height: 0px;
	width:150px;
}

.tabs-left>.nav-tabs .active>a {
	background: url("../images/backgrounds/top.jpg") repeat-x
}

.tabs-left>li>a {
	background: url("../images/backgrounds/top.jpg") repeat-x
}
.form-horizontal .controls {
    margin-left: 164px;
}


.tab-content > .active, .pill-content > .active {
    display: block;
   min-height: 330px;
}
.tab-pane{
border: 2px solid;
}
.table1.thread.th{
margin-left:100px;
}
.tab-content {
    overflow: visible;
}
.ui-dialog-content .ui-widget-content{
 	display: block;
    height: 561px;
    min-height: 0;
    width: 967px;
}
</style>