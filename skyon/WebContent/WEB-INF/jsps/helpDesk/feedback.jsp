<%@include file="/common/taglibs.jsp"%>		
<%-- 
<c:url value="/helpDesk/readAllHelpDeskData" var="readUr" /> --%>
<c:url value="/helpDesk/feedbackData" var="readUr" />



<div id="loading" ></div>
	<kendo:grid name="grid" pageable="true"
		filterable="true" groupable="true" selectable="true" scrollable="true" reorderable="true" resizable="true" navigatable="true" >
	  <kendo:grid-pageable pageSizes="true" buttonCount="5"  pageSize="20" input="true" numeric="true" refresh="true" ></kendo:grid-pageable>
			<kendo:grid-filterable extra="false">
		
				<kendo:grid-filterable-operators>
					<kendo:grid-filterable-operators-string eq="Is equal to" />
				</kendo:grid-filterable-operators>
		
			</kendo:grid-filterable>



			<kendo:grid-editable mode="popup" confirmation="Are you sure you want to remove this item?"/>
			<kendo:grid-toolbarTemplate>
				<div class="toolbar">
		
					<a class='k-button' href='\\#' onclick="clearFilterOwner()"><span
						class='k-icon k-i-funnel-clear'></span> Clear Filter</a> <a
						class="k-button k-button-icontext k-grid-ownerTemplatesDetailsExport"
						href="#"> <span class=" "></span> Export To Excel
					</a>
				</div>
			</kendo:grid-toolbarTemplate>

	<kendo:grid-columns>
		<kendo:grid-column title="Apartment No " field="apartmentNo"
			width="100px">
		</kendo:grid-column>
		<kendo:grid-column title="Customer Name " 
			field="customerName" width="120px">
		</kendo:grid-column>
		<kendo:grid-column title="Ticket No" field="ticketNo" width="80px">
		<kendo:grid-column-filterable>
		    			<kendo:grid-column-filterable-ui>
	    					<script> 
								function statusFilter(element) {
									element.kendoAutoComplete({
										dataType: 'JSON',
										dataSource: {
											transport: {
												read: "${filter1}"
											}
										}
									});
						  		}
						  	</script>		
		    			</kendo:grid-column-filterable-ui>
		    	</kendo:grid-column-filterable>
		</kendo:grid-column>

		<kendo:grid-column title="Ticket Description " filterable="false"
			field="ticketDescription" width="120px">
		</kendo:grid-column>
		<kendo:grid-column title="Professionalism " filterable="false" field="professionalism"
			width="110px">
		</kendo:grid-column>
		<kendo:grid-column title="Timeliness" field="timeliness"
			filterable="false" width="80px" />
		<kendo:grid-column title="Completion Speed" field="completionSpeed"
			filterable="false" width="130px" />
		<kendo:grid-column title="Cleanliness" field="cleanliness"
			filterable="false" width="90px" />
		<kendo:grid-column title="Communication" field="communication"
			filterable="false" width="110px" />
		<kendo:grid-column title="Quality" field="quality"
			filterable="false" width="60px" />
		<%--  <kendo:grid-column title="Serviced By"    field="june" filterable="false" width="110px"/> --%>
		<kendo:grid-column title="Additional Comments" field="additionalComments"
			filterable="false" width="120px" />
		<kendo:grid-column title="Contacted by mgt" field="contactedbymgtt"
			filterable="false" width="110px" />
		<%-- <kendo:grid-column title="Category Owner" field="categoryOwner"
			filterable="false" width="380px" />
			<kendo:grid-column title="Open Tickets" field="openCount"
			filterable="false" width="280px" />
			<kendo:grid-column title="Closed Tickets" field="closedCount"
			filterable="false" width="280px" /> --%>

		<!-- 3rd report -->
		<%-- <kendo:grid-column title="Month" field="month" filterable="false" width="110px"/>
                <kendo:grid-column title="Energy Charge" field="head1" filterable="false" width="150px"/>
                <kendo:grid-column title="DG Charge" field="head2" filterable="false" width="150px"/>
                <kendo:grid-column title="Late Payment Charge" field="head3" filterable="false" width="150px"/>
                <kendo:grid-column title="Arrear" field="head4" filterable="false" width="150px"/>
                <kendo:grid-column title="Round Off" field="head5" filterable="false" width="150px"/> --%>

		<!-- 4th report -->
		<%-- <kendo:grid-column title="Meter Sr No"    field="meterSrNo" filterable="true" width="110px"/>
  				<kendo:grid-column title="Previous Billed Unit" field="prevBillUnit" filterable="false" width="150px"/>
 				<kendo:grid-column title="Present Billed Unit" field="presentBillUnit" filterable="false" width="150px"/>
 				<kendo:grid-column title="Previous Billed Date" field="prevBillDate" filterable="false" width="150px"/>
  				<kendo:grid-column title="Present Billed Date" field="presentBillDate" filterable="false" width="150px"/> --%>



		<!-- 5th report -->
		<%-- <kendo:grid-column title="Month Billed Amount" field="monthBilledAmount" filterable="true" width="150px"/>	 
           	 	 <kendo:grid-column title="Collection Amount" field="collectionAmount" filterable="false" width="150px"/>	 
           	 	 <kendo:grid-column title="Interest" field="interest" filterable="false" width="150px"/>	 
           	 	 <kendo:grid-column title="Arrears" field="arrearsTax" filterable="false" width="150px"/>	 
           	 	 <kendo:grid-column title="Service Tax" field="vat" filterable="false" width="150px"/> --%>



		<!-- 6th report -->
		<%--  <kendo:grid-column title="Tower" field="tower" filterable="true" width="110px"/>	 
           	 	 <kendo:grid-column title="Total Flats" field="totalFlats" filterable="true" width="110px"/>	 
           	 	 <kendo:grid-column title="Main Meter Reading" field="mainMeterReading" filterable="true" width="150px"/>	 
           	 	 <kendo:grid-column title="Others Meter Reading" field="otherMeterReading" filterable="true" width="140px"/>	 
           	 	 <kendo:grid-column title="Unit Loss" field="unitLoss" filterable="true" width="110px"/>	 
           	 	 <kendo:grid-column title="% Loss" field="loss" filterable="true" width="110px"/> --%>

		<!-- 7th report -->
		<%--  <kendo:grid-column title="Billed Amount" field="billedAmount" filterable="true" width="110px"/>	 
           	 	 <kendo:grid-column title="Collection Amount" field="collectionAmount" filterable="true" width="110px"/> 	 
           	 	 <kendo:grid-column title="Collection Loss" field="collectionLoss" filterable="true" width="110px"/>	 
           	 	 <kendo:grid-column title="AT&C Loss" field="atcLoss" filterable="true" width="110px"/>	  --%>

		<!-- 8th report -->
		<%-- <kendo:grid-column title="Escalated&nbsp;Date" field="assignDate"
			format="{0:dd/MM/yyyy hh:mm tt}" width="140px" />
		<kendo:grid-column title="Escalated&nbsp;To" field="userName"
			width="120px" />
		<kendo:grid-column title="Escalated&nbsp;Comments"
			field="assignComments" filterable="true" width="150px" />
		<kendo:grid-column title="SLA&nbsp;Level&nbsp;(in&nbsp;days)"
			field="levelOFSLA" filterable="false" width="120px" />
		<kendo:grid-column title="Person&nbsp;Name" field="personName"
			width="120px" filterable="false" />
		<kendo:grid-column title="Priority" field="priorityLevel"
			width="100px" filterable="true" />
		<kendo:grid-column title="Issue&nbsp;Subject" field="issueSubject"
			width="120px" filterable="true" />
		<kendo:grid-column title="Issue&nbsp;Details" field="issueDetails"
			width="140px" filterable="true" /> --%>


	</kendo:grid-columns>
	<kendo:dataSource requestStart="requestStart" requestEnd="requestEnd">
		<kendo:dataSource-transport>

			<kendo:dataSource-transport-read url="${readUr}"
				dataType="json" type="POST" contentType="application/json" />

		</kendo:dataSource-transport>
		<kendo:dataSource-schema parse="parse">
			<kendo:dataSource-schema-model id="dept_Id">
				<kendo:dataSource-schema-model-fields>
					<kendo:dataSource-schema-model-field name="dept_Id"
						editable="false">
					</kendo:dataSource-schema-model-field>


					<kendo:dataSource-schema-model-field name="dept_Name" type="string" />


					<kendo:dataSource-schema-model-field name="dept_Desc" type="string">
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="dept_Status"
						type="string" />
					<kendo:dataSource-schema-model-field name="jan" type="number"
						defaultValue="0" />



				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>
	</kendo:dataSource>
</kendo:grid>
    
    <div id="alertsBox" title="Alert"></div>
<script type="text/javascript">
					function requestStart() {

						kendo.ui.progress($("#loading"), true);

					}

					function requestEnd() {
						kendo.ui.progress($("#loading"), false);

					}

					function parse(response) {
						$.each(response, function(idx, elem) {
							if (elem.vStd === null) {
								elem.vStd = "";
							} else {
								elem.vStd = kendo.parseDate(
										new Date(elem.vStd), 'dd/MM/yyyy');
							}

							if (elem.vEnd === null) {
								elem.vEnd = "";
							} else {
								elem.vEnd = kendo.parseDate(
										new Date(elem.vEnd), 'dd/MM/yyyy');
							}
							if (elem.visitorInDate === null) {
								elem.visitorInDate = "";
							} else {
								elem.visitorInDate = kendo.parseDate(new Date(
										elem.visitorInDate), 'dd/MM/yyyy');
							}
						});

						return response;
					}

					function clearFilterOwner() {
						//custom actions
						$("form.k-filter-menu button[type='reset']").slice()
								.trigger("click");
						var grid = $("#grid").data("kendoGrid");
						grid.dataSource.read();
						grid.refresh();
					}

					$(document).ready(function() {

						$("#menu1thirdlevel").remove();
						$("#menu2thirdlevel").remove();
						$("#menu3thirdlevel").remove();
						$("#menu4thirdlevel").remove();
						$("#menu5thirdlevel").remove();
						$("#menu6thirdlevel").remove();
						$("#menu7thirdlevel").remove();
						$("#menu8thirdlevel").remove();
						$("#menu9thirdlevel").remove();
						$("#menu10thirdlevel").remove();
						$("#menu12thirdlevel").remove();
						$("#menu13thirdlevel").remove();
						$("#menu14thirdlevel").remove();
						$("#menu15thirdlevel").remove();
						$("#menu16thirdlevel").remove();
						$("#menu17thirdlevel").remove();
						$("#menu18thirdlevel").remove();
						$("#menu19thirdlevel").remove();
						$("#menu20thirdlevel").remove();
						$("#menu21thirdlevel").remove();
						$("#menu22thirdlevel").remove();
						var grid = $("#grid").data("kendoGrid");

						if ("${data}" == 1)//primary contacts
						{
							/* ========== 3rd report ========== */
							grid.hideColumn("onBeHalfOf");

							grid.hideColumn("head1");
							grid.hideColumn("head2");
							grid.hideColumn("head3");
							grid.hideColumn("head4");
							grid.hideColumn("head5");

							/* ========== 4th report ========== */
							grid.hideColumn("meterSrNo");
							grid.hideColumn("prevBillUnit");
							grid.hideColumn("presentBillUnit");
							grid.hideColumn("prevBillDate");
							grid.hideColumn("presentBillDate");

							/* ========== 5th report ========== */
							grid.hideColumn("monthBilledAmount");
							grid.hideColumn("collectionAmount");
							grid.hideColumn("interest");
							grid.hideColumn("arrearsTax");
							grid.hideColumn("vat");

							/* ========== 6th report ========== */
							grid.hideColumn("tower");
							grid.hideColumn("totalFlats");
							grid.hideColumn("mainMeterReading");
							grid.hideColumn("otherMeterReading");
							grid.hideColumn("unitLoss");
							grid.hideColumn("loss");

							/* ========== 7th report ========== */
							grid.hideColumn("billedAmount");

							grid.hideColumn("collectionLoss");
							grid.hideColumn("atcLoss");

							/* ========== 8th report ========== */
							grid.hideColumn("assignDate");
							grid.hideColumn("userName");
							grid.hideColumn("assignComments");
							grid.hideColumn("levelOFSLA");
							grid.hideColumn("personName");
							grid.hideColumn("priorityLevel");
							grid.hideColumn("issueSubject");
							grid.hideColumn("issueDetails");
							
							/* ========== 9th report ========== */
							grid.hideColumn("categoryOwner");
							grid.hideColumn("openCount");
							grid.hideColumn("closedCount");

						}
						if ("${data}" == 2)//primary contacts
						{
							grid.hideColumn("onBeHalfOf");
							/* grid.hideColumn("jan");
							grid.hideColumn("feb");
							grid.hideColumn("march");
							grid.hideColumn("april");
							grid.hideColumn("may");
							grid.hideColumn("june");
							grid.hideColumn("july");
							grid.hideColumn("august");
							grid.hideColumn("september");
							grid.hideColumn("october");
							grid.hideColumn("november");
							grid.hideColumn("december"); */

							/* ========== 3rd report ========== */
							grid.hideColumn("month");
							grid.hideColumn("head1");
							grid.hideColumn("head2");
							grid.hideColumn("head3");
							grid.hideColumn("head4");
							grid.hideColumn("head5");

							/* ========== 4th report ========== */
							grid.hideColumn("meterSrNo");
							grid.hideColumn("prevBillUnit");
							grid.hideColumn("presentBillUnit");
							grid.hideColumn("prevBillDate");
							grid.hideColumn("presentBillDate");

							/* ========== 5th report ========== */
							grid.hideColumn("monthBilledAmount");
							grid.hideColumn("collectionAmount");
							grid.hideColumn("interest");
							grid.hideColumn("arrearsTax");
							grid.hideColumn("vat");

							/* ========== 6th report ========== */
							grid.hideColumn("tower");
							grid.hideColumn("totalFlats");
							grid.hideColumn("mainMeterReading");
							grid.hideColumn("otherMeterReading");
							grid.hideColumn("unitLoss");
							grid.hideColumn("loss");

							/* ========== 7th report ========== */
							grid.hideColumn("billedAmount");
							//grid.hideColumn("collectionAmount");
							grid.hideColumn("collectionLoss");
							grid.hideColumn("atcLoss");
							/* ========== 8th report ========== */
							grid.hideColumn("assignDate");
							grid.hideColumn("userName");
							grid.hideColumn("assignComments");
							grid.hideColumn("levelOFSLA");
							grid.hideColumn("personName");
							grid.hideColumn("priorityLevel");
							grid.hideColumn("issueSubject");
							grid.hideColumn("issueDetails");
							/* ========== 9th report ========== */
							grid.hideColumn("categoryOwner");
							grid.hideColumn("openCount");
							grid.hideColumn("closedCount");

						}
						if ("${data}" == 3)//primary contacts
						{
							grid.hideColumn("onBeHalfOf");

							grid.hideColumn("jan");
							grid.hideColumn("feb");
							grid.hideColumn("march");
							grid.hideColumn("april");
							grid.hideColumn("may");
							grid.hideColumn("june");
							grid.hideColumn("july");
							grid.hideColumn("august");
							grid.hideColumn("september");
							grid.hideColumn("october");
							grid.hideColumn("november");
							grid.hideColumn("december");

							/* ========== 4th report ========== */
							grid.hideColumn("meterSrNo");
							grid.hideColumn("prevBillUnit");
							grid.hideColumn("presentBillUnit");
							grid.hideColumn("prevBillDate");
							grid.hideColumn("presentBillDate");

							/* ========== 5th report ========== */
							grid.hideColumn("monthBilledAmount");
							grid.hideColumn("collectionAmount");
							grid.hideColumn("interest");
							grid.hideColumn("arrearsTax");
							grid.hideColumn("vat");

							/* ========== 6th report ========== */
							grid.hideColumn("tower");
							grid.hideColumn("totalFlats");
							grid.hideColumn("mainMeterReading");
							grid.hideColumn("otherMeterReading");
							grid.hideColumn("unitLoss");
							grid.hideColumn("loss");

							/* ========== 7th report ========== */
							grid.hideColumn("billedAmount");
							//grid.hideColumn("collectionAmount");
							grid.hideColumn("collectionLoss");
							grid.hideColumn("atcLoss");

							/* ========== 8th report ========== */
							grid.hideColumn("assignDate");
							grid.hideColumn("userName");
							grid.hideColumn("assignComments");
							grid.hideColumn("levelOFSLA");
							grid.hideColumn("personName");
							grid.hideColumn("priorityLevel");
							grid.hideColumn("issueSubject");
							grid.hideColumn("issueDetails");
							/* ========== 9th report ========== */
							grid.hideColumn("categoryOwner");
							grid.hideColumn("openCount");
							grid.hideColumn("closedCount");

						}
						if ("${data}" == 4)//primary contacts
						{
							grid.hideColumn("jan");
							grid.hideColumn("feb");
							grid.hideColumn("march");
							grid.hideColumn("april");
							grid.hideColumn("may");
							grid.hideColumn("june");
							grid.hideColumn("july");
							grid.hideColumn("august");
							grid.hideColumn("september");
							grid.hideColumn("october");
							grid.hideColumn("november");
							grid.hideColumn("december");

							/* ========== 3rd report ========== */
							grid.hideColumn("month");
							grid.hideColumn("head1");
							grid.hideColumn("head2");
							grid.hideColumn("head3");
							grid.hideColumn("head4");
							grid.hideColumn("head5");

							/* ========== 5th report ========== */
							grid.hideColumn("monthBilledAmount");
							grid.hideColumn("collectionAmount");
							grid.hideColumn("interest");
							grid.hideColumn("arrearsTax");
							grid.hideColumn("vat");

							/* ========== 6th report ========== */
							grid.hideColumn("tower");
							grid.hideColumn("totalFlats");
							grid.hideColumn("mainMeterReading");
							grid.hideColumn("otherMeterReading");
							grid.hideColumn("unitLoss");
							grid.hideColumn("loss");

							/* ========== 7th report ========== */
							grid.hideColumn("billedAmount");
							//grid.hideColumn("collectionAmount");
							grid.hideColumn("collectionLoss");
							grid.hideColumn("atcLoss");

							/* ========== 8th report ========== */
							grid.hideColumn("assignDate");
							grid.hideColumn("userName");
							grid.hideColumn("assignComments");
							grid.hideColumn("levelOFSLA");
							grid.hideColumn("personName");
							grid.hideColumn("priorityLevel");
							grid.hideColumn("issueSubject");
							grid.hideColumn("issueDetails");
							/* ========== 9th report ========== */
							grid.hideColumn("categoryOwner");
							grid.hideColumn("openCount");
							grid.hideColumn("closedCount");

						}
						if ("${data}" == 5)//primary contacts
						{
							grid.hideColumn("jan");
							grid.hideColumn("feb");
							grid.hideColumn("march");
							grid.hideColumn("april");
							grid.hideColumn("may");
							grid.hideColumn("june");
							grid.hideColumn("july");
							grid.hideColumn("august");
							grid.hideColumn("september");
							grid.hideColumn("october");
							grid.hideColumn("november");
							grid.hideColumn("december");

							/* ========== 3rd report ========== */
							//grid.hideColumn("month");
							grid.hideColumn("head1");
							grid.hideColumn("head2");
							grid.hideColumn("head3");
							grid.hideColumn("head4");
							grid.hideColumn("head5");

							/* ========== 4th report ========== */
							grid.hideColumn("meterSrNo");
							grid.hideColumn("prevBillUnit");
							grid.hideColumn("presentBillUnit");
							grid.hideColumn("prevBillDate");
							grid.hideColumn("presentBillDate");

							/* ========== 6th report ========== */
							grid.hideColumn("tower");
							grid.hideColumn("totalFlats");
							grid.hideColumn("mainMeterReading");
							grid.hideColumn("otherMeterReading");
							grid.hideColumn("unitLoss");
							grid.hideColumn("loss");

							/* ========== 7th report ========== */
							grid.hideColumn("billedAmount");
							//grid.hideColumn("collectionAmount");
							grid.hideColumn("collectionLoss");
							grid.hideColumn("atcLoss");

							/* ========== 8th report ========== */
							grid.hideColumn("assignDate");
							grid.hideColumn("userName");
							grid.hideColumn("assignComments");
							grid.hideColumn("levelOFSLA");
							grid.hideColumn("personName");
							grid.hideColumn("priorityLevel");
							grid.hideColumn("issueSubject");
							grid.hideColumn("issueDetails");
							/* ========== 9th report ========== */
							grid.hideColumn("categoryOwner");
							grid.hideColumn("openCount");
							grid.hideColumn("closedCount");

						}
						if ("${data}" == 6)//primary contacts
						{
							grid.hideColumn("onBeHalfOf");

							grid.hideColumn("name");
							grid.hideColumn("blockName");
							grid.hideColumn("flatNo");
							grid.hideColumn("jan");
							grid.hideColumn("feb");
							grid.hideColumn("march");
							grid.hideColumn("april");
							grid.hideColumn("may");
							grid.hideColumn("june");
							grid.hideColumn("july");
							grid.hideColumn("august");
							grid.hideColumn("september");
							grid.hideColumn("october");
							grid.hideColumn("november");
							grid.hideColumn("december");

							/* ========== 3rd report ========== */
							grid.hideColumn("month");
							grid.hideColumn("head1");
							grid.hideColumn("head2");
							grid.hideColumn("head3");
							grid.hideColumn("head4");
							grid.hideColumn("head5");

							/* ========== 4th report ========== */
							grid.hideColumn("meterSrNo");
							grid.hideColumn("prevBillUnit");
							grid.hideColumn("presentBillUnit");
							grid.hideColumn("prevBillDate");
							grid.hideColumn("presentBillDate");

							/* ========== 5th report ========== */
							grid.hideColumn("monthBilledAmount");
							//grid.hideColumn("collectionAmount");
							grid.hideColumn("interest");
							grid.hideColumn("arrearsTax");
							grid.hideColumn("vat");

							/* ========== 7th report ========== */
							grid.hideColumn("billedAmount");
							grid.hideColumn("collectionAmount");
							grid.hideColumn("collectionLoss");
							grid.hideColumn("atcLoss");

							/* ========== 8th report ========== */
							grid.hideColumn("assignDate");
							grid.hideColumn("userName");
							grid.hideColumn("assignComments");
							grid.hideColumn("levelOFSLA");
							grid.hideColumn("personName");
							grid.hideColumn("priorityLevel");
							grid.hideColumn("issueSubject");
							grid.hideColumn("issueDetails");
							/* ========== 9th report ========== */
							grid.hideColumn("categoryOwner");
							grid.hideColumn("openCount");
							grid.hideColumn("closedCount");

						}
						if ("${data}" == 7)//primary contacts
						{
							grid.hideColumn("onBeHalfOf");

							/* ========== 3rd report ========== */
							grid.hideColumn("month");
							grid.hideColumn("head1");
							grid.hideColumn("head2");
							grid.hideColumn("head3");
							grid.hideColumn("head4");
							grid.hideColumn("head5");

							/* ========== 1st report ========== */
							grid.hideColumn("name");
							grid.hideColumn("blockName");
							grid.hideColumn("flatNo");
							grid.hideColumn("jan");
							grid.hideColumn("feb");
							grid.hideColumn("march");
							grid.hideColumn("april");
							grid.hideColumn("may");
							grid.hideColumn("june");
							grid.hideColumn("july");
							grid.hideColumn("august");
							grid.hideColumn("september");
							grid.hideColumn("october");
							grid.hideColumn("november");
							grid.hideColumn("december");

							/* ========== 4th report ========== */
							grid.hideColumn("meterSrNo");
							grid.hideColumn("prevBillUnit");
							grid.hideColumn("presentBillUnit");
							grid.hideColumn("prevBillDate");
							grid.hideColumn("presentBillDate");

							/* ========== 5th report ========== */
							grid.hideColumn("monthBilledAmount");
							//grid.hideColumn("collectionAmount");
							grid.hideColumn("interest");
							grid.hideColumn("arrearsTax");
							grid.hideColumn("vat");

							/* ========== 6th report ========== */
							/* grid.hideColumn("tower");
							grid.hideColumn("totalFlats");
							grid.hideColumn("mainMeterReading");
							grid.hideColumn("otherMeterReading");
							grid.hideColumn("unitLoss");
							grid.hideColumn("loss"); */

							/* ========== 8th report ========== */
							grid.hideColumn("assignDate");
							grid.hideColumn("userName");
							grid.hideColumn("assignComments");
							grid.hideColumn("levelOFSLA");
							grid.hideColumn("personName");
							grid.hideColumn("priorityLevel");
							grid.hideColumn("issueSubject");
							grid.hideColumn("issueDetails");
							/* ========== 9th report ========== */
							grid.hideColumn("categoryOwner");
							grid.hideColumn("openCount");
							grid.hideColumn("closedCount");

						}
						if ("${data}" == 8)//Escalated Reports
						{
							grid.hideColumn("onBeHalfOf");
							grid.hideColumn("propertyNo");
							grid.hideColumn("createdBy");
							grid.hideColumn("dayIssue");
							grid.hideColumn("lastUpdatedDT");
							grid.hideColumn("drivenBy");
							grid.hideColumn("category");
							grid.hideColumn("subcategory");
							grid.hideColumn("lastnotes");
							/* ========== 9th report ========== */
							grid.hideColumn("categoryOwner");
							grid.hideColumn("openCount");
							grid.hideColumn("closedCount");
						}
						
						if ("${data}" == 9)//Ticket Summary
						{
							grid.hideColumn("onBeHalfOf");
							grid.hideColumn("propertyNo");
							grid.hideColumn("createdBy");
							grid.hideColumn("dayIssue");
							grid.hideColumn("lastUpdatedDT");
							grid.hideColumn("drivenBy");
							grid.hideColumn("ticketCreatedDate");
							grid.hideColumn("subcategory");
							grid.hideColumn("lastnotes");
							grid.hideColumn("ticketNumber");
							grid.hideColumn("subcategory");
							grid.hideColumn("ticketStatus");
							
							grid.hideColumn("assignDate");
							grid.hideColumn("userName");
							grid.hideColumn("assignComments");
							grid.hideColumn("levelOFSLA");
							grid.hideColumn("personName");
							grid.hideColumn("priorityLevel");
							grid.hideColumn("issueSubject");
							grid.hideColumn("issueDetails");
							
							
						}

					})

					$("#grid").on(
							"click",
							".k-grid-ownerTemplatesDetailsExport",
							function(e) {
								window.open("./helpDesk/feedbackDataExport");
							});
				</script>
