<%@include file="/common/taglibs.jsp"%>

<c:url value="/ledgers/ledgerRead" var="electricityLedgerReadUrl" />
<c:url value="/ledgers/ledgerCreate" var="electricityLedgerCreateUrl" />
<c:url value="/ledgers/subLedgerRead" var="elSubLedgerReadUrl" />
<c:url value="/ledgers/subLedgerCreate" var="elSubLedgerCreateUrl" />

<c:url value="/common/getAllChecks" var="allChecksUrl" />
<c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />
<c:url value="/person/getAllAttributeValues" var="filterAutoCompleteUrl" />  
<c:url value="/ledger/commonFilterForAccountNumbersUrl" var="commonFilterForAccountNumbersUrl" />
<c:url value="/ledger/commonFilterForPropertyNumbersUrl" var="commonFilterForPropertyNumbersUrl" />
<c:url value="/ledgers/getPersonListForFileter" var="personNamesFilterUrl" />

<!-- Filters Data url's -->	
<c:url value="/ledger/filter" var="commonFilterForLedgerUrl" />
<c:url value="/subLedger/filter" var="commonFilterForSubLedgerUrl" />

<!-- <div id="cap-view" class="demo-section k-content">
	<h4>Search Ledger Data Based On</h4><br>
    	<select id="searchCriteria" name="searchCriteria" style="overflow:hidden;"></select>
    	<select id="propertyId" name="propertyId"></select> 
    </div> -->

 <kendo:grid name="grid" resizable="true" pageable="true" selectable="true" change="onChangeLedger" edit="electricityLedgerEvent" detailTemplate="elRateSlabTemplate" sortable="true" scrollable="true" 
		groupable="true">
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10"></kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
			<kendo:grid-filterable-operators>
				<kendo:grid-filterable-operators-string eq="Is equal to" neq="Is not equal to" doesnotcontain="Does not contain" contains="Contains" startswith="Starts with"/>
				<kendo:grid-filterable-operators-date eq="Is equal to" neq="Is not equal to" gte="Is after or equal to" lte="Is before or equal to" gt="Is after" lt="Is before"/>
			</kendo:grid-filterable-operators>

		</kendo:grid-filterable>
		<kendo:grid-editable mode="popup"
			confirmation="Are you sure you want to remove this Ledger Detail?" />
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem template="<label class='category-label'>&nbsp;&nbsp;From&nbsp;Date&nbsp;:&nbsp;&nbsp;</label><input id='fromMonthpicker' style='width:130px'/>"/>
			<kendo:grid-toolbarItem template="<label class='category-label'>&nbsp;&nbsp;To&nbsp;Date&nbsp;:&nbsp;&nbsp;</label><input id='toMonthpicker' style='width:130px'/>"/>
			<kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=searchByMonth() title='Search' style='width:130px'>Search</a>"/>
			<kendo:grid-toolbarItem template="<a class='k-button' href='\\#' style='width:100px' onclick=clearFilterLedger()></span> Clear Filter</a>"/>
		</kendo:grid-toolbar>
		<kendo:grid-columns>
			
			<kendo:grid-column title="Electricty Ledger Id" field="elLedgerid" width="110px" hidden="true"/>
			
			<kendo:grid-column title="TS" format="{0:n0}" field="transactionSequence" width="40px" filterable="true">
			</kendo:grid-column>
			
			<kendo:grid-column title="Account&nbsp;No" field="accountNo" filterable="true" width="85px">
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
			
	    	<kendo:grid-column title="Person&nbsp;Name" field="personName"  width="100px" filterable="false">
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
	    	
	    	<kendo:grid-column title="Property&nbsp;No" field="property_No" filterable="true" width="85px">
	    	<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function postTypeFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Property No.",
									dataSource : {
										transport : {
											read : "${commonFilterForPropertyNumbersUrl}"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	    	</kendo:grid-column>
	    	
	    	<kendo:grid-column title="Post&nbsp;Type" field="postType" width="75px" filterable="true" editor="dropDownChecksEditor">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function postTypeFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Post Type",
									dataSource : {
										transport : {
											read : "${commonFilterForLedgerUrl}/postType"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="Transaction&nbsp;Name" field="transactionName" width="125px" filterable="true">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function ledgerPeriodFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Transaction Code",                                            
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${commonFilterForLedgerUrl}/transactionCode"
										}
									}
								});    
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="Amount" field="amount" format="{0:#.00}" width="70px" filterable="true">
			</kendo:grid-column>
	    	
	    	<kendo:grid-column title="Balance&nbsp;" field="balance" format="{0:#.00}" width="70px" filterable="true">
			</kendo:grid-column>
			
			<kendo:grid-column title="Ledger&nbsp;Type" field="ledgerType" width="110px" filterable="true">
		    	<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function ledgerTypeFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Ledger Type",
									dataSource : {
										transport : {
											read : "${commonFilterForLedgerUrl}/ledgerType"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>

			<kendo:grid-column title="Ledger&nbsp;Period" field="ledgerPeriod" filterable="true" width="100px" hidden="true">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function ledgerPeriodFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Period",                                            
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${commonFilterForLedgerUrl}/ledgerPeriod"
										}
									}
								});    
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="Ledger&nbsp;Month" field="ledgerDate" format="{0:MMM,yyyy}" filterable="false" width="100px">
			</kendo:grid-column>
			
			<kendo:grid-column title="Posted&nbsp;Date" field="postedBillDate" format="{0:dd/MM/yyyy hh:mm tt}" filterable="false" width="135px">
			</kendo:grid-column>
			
			<kendo:grid-column title="Post&nbsp;Reference" field="postReference" width="110px" filterable="true">
			<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function postReferenceFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Post Reference",
									dataSource : {
										transport : {
											read : "${commonFilterForLedgerUrl}/postReference"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="Bank Name" field="bankName" width="110px" filterable="false" hidden="true"/>
			<kendo:grid-column title="Instrument No." field="instrumentNo" width="110px" filterable="false" hidden="true"/>
			<%-- <kendo:grid-column title="Instrument Date" field="instrumentDate" width="110px" filterable="true"/> --%>
			
			<kendo:grid-column title="Remarks" field="remarks" width="110px" filterable="true">
			<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function postReferenceFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Remarks",
									dataSource : {
										transport : {
											read : "${commonFilterForLedgerUrl}/remarks"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<%-- <kendo:grid-column title="Posted&nbsp;To&nbsp;Bill&nbsp*" field="postedToBill" width="110px" editor="dropDownChecksEditor" filterable="true">
			<kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function postedToBillFilter(element) {
								element
										.kendoDropDownList({
											optionLabel : "Select",
											dataSource : {
												transport : {
													read : "${filterDropDownUrl}/postedToBill"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="Posted&nbsp;To&nbsp;Gl&nbsp*" field="postedToGl" width="110px" editor="dropDownChecksEditor" filterable="true">
			<kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function postedToGlFilter(element) {
								element
										.kendoDropDownList({
											optionLabel : "Select",
											dataSource : {
												transport : {
													read : "${filterDropDownUrl}/postedToGl"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column> --%>
			
			<%-- <kendo:grid-column title="Posted&nbsp;Gl&nbsp;Date" field="postedGlDate" format="{0:dd/MM/yyyy}" width="120px">
			</kendo:grid-column> --%>	
			
			<%-- <kendo:grid-column title="Debit&nbsp;Amount" field="debitAmount" format="{0:#.00}" width="110px" filterable="true">
			</kendo:grid-column> --%>
			
			<%-- <kendo:grid-column title="Ledger Status" field="status" width="100px" >
				<kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
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
				</kendo:grid-column-filterable>
			</kendo:grid-column>  --%>
			
			<%-- <kendo:grid-column title="Created By" field="createdBy" width="150px">
			 <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function propertyCreatedByFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									placeholder : "Enter Created Name",
									dataSource: {
										transport: {
											read: "${commonFilterForLedgerUrl}/createdBy"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
			</kendo:grid-column> --%>
			
			<%-- <kendo:grid-column title=""
				template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Active#=data.elLedgerid#'>#= data.status == 'Active' ? 'In-active' : 'Active' #</a>"
				width="100px" /> --%>
		</kendo:grid-columns>
		
		<kendo:dataSource pageSize="20" serverPaging="true" serverSorting="false" serverFiltering="true" serverGrouping="true" requestEnd="onRequestEnd">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-create url="${electricityLedgerCreateUrl}" dataType="json" type="GET" contentType="application/json" />
				<kendo:dataSource-transport-read url="${electricityLedgerReadUrl}" dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-parameterMap>
                	function(options){return JSON.stringify(options);}
           		</kendo:dataSource-transport-parameterMap>
			</kendo:dataSource-transport>
			<kendo:dataSource-schema data="data" groups="data" total="total">
				<kendo:dataSource-schema-model id="elLedgerid">
					<kendo:dataSource-schema-model-fields>

						<kendo:dataSource-schema-model-field name="elLedgerid" type="number">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="property_No" type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="accountNo" type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="transactionName" type="string">
						</kendo:dataSource-schema-model-field>

						<kendo:dataSource-schema-model-field name="accountId" type="number">
							<kendo:dataSource-schema-model-field-validation min="1" />
						</kendo:dataSource-schema-model-field>

						<kendo:dataSource-schema-model-field name="ledgerPeriod" type="string">
						</kendo:dataSource-schema-model-field> 

						<kendo:dataSource-schema-model-field name="ledgerDate" type="date">
						</kendo:dataSource-schema-model-field>

						<kendo:dataSource-schema-model-field name="postType" type="string">
						</kendo:dataSource-schema-model-field>

						<kendo:dataSource-schema-model-field name="postReference" type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="transactionCode" type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="personName" type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="amount" type="number">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="balance" type="number">
							<kendo:dataSource-schema-model-field-validation  required="true" min="1"/>							
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="postedToBill" type="string">						
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="postedBillDate" type="string">						
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="postedToGl" type="string">						
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="postedGlDate" type="date">						
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="transactionSequence" type="number" defaultValue="">
							<kendo:dataSource-schema-model-field-validation  required="true" min="1" />							
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="ledgerType" type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="createdBy">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="status"
							editable="false" type="string" />
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
			
		</kendo:dataSource>
	</kendo:grid> 
	
	<kendo:grid-detailTemplate id="elRateSlabTemplate">
		<kendo:tabStrip name="tabStrip_#=elLedgerid#">
			<kendo:tabStrip-items>
			
 			<kendo:tabStrip-item selected="true" text="Sub Ledger">
                <kendo:tabStrip-item-content>
                    <div class='wethear' style="width: 75%;">
							<br />
							<kendo:grid name="subLedgerEvent_#=elLedgerid#" pageable="true"
								resizable="true" sortable="true" reorderable="true"
								selectable="true" scrollable="true" edit="subLedgerEvent" filterable="true" dataBound="dataBoundMC">
								<kendo:grid-pageable pageSize="15"></kendo:grid-pageable>
								<kendo:grid-filterable extra="false">
			                    <kendo:grid-filterable-operators>
				                    <kendo:grid-filterable-operators-string eq="Is equal to" />
			                    </kendo:grid-filterable-operators>
		                        </kendo:grid-filterable>
								<kendo:grid-editable mode="popup"  confirmation="Are sure you want to delete this item ?"/>
						       <kendo:grid-toolbar >
						            <%-- <kendo:grid-toolbarItem name="create" text="Add New Sub Ledger" /> --%>
						        </kendo:grid-toolbar> 
        						<kendo:grid-columns>
        							<kendo:grid-column title="Sub&nbsp;Ledger Id" field="elSubLedgerid" hidden="true" width="70px">
        							</kendo:grid-column>
        							
									<%-- <kendo:grid-column title="Ledger&nbsp;Id" field="electricityLedgerEntity.elLedgerid" width="100px" /> --%>
									
									<kendo:grid-column title="Transaction&nbsp;Name" field="transactionName" footerTemplate="<span style='margin-left: 270px;' id='totlcst'><b>Total : </b></span>" filterable="false" width="150px">
	    		                    </kendo:grid-column>
									
									<%-- <kendo:grid-column title="Transaction&nbsp;Code" field="transactionId" width="100px" filterable="false" hidden="true">
									</kendo:grid-column> --%>
									
									<kendo:grid-column title="Amount" field="amount" footerTemplate="<span id='totlcst'><span id='sumTotal_#=elLedgerid#'></span> Rs</span>" width="100px" filterable="false">
									</kendo:grid-column>
									 
									<kendo:grid-column title="Balance&nbsp;Amount" field="balanceAmount" width="100px" filterable="false">
									</kendo:grid-column>
									 
									<%-- <kendo:grid-column title="sub&nbsp;Ledger&nbsp;Status" field="status" width="100px" filterable="true">
									<kendo:grid-column-filterable >
					                <kendo:grid-column-filterable-ui >
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
				                    </kendo:grid-column-filterable>
									</kendo:grid-column> 
									
			                    	<kendo:grid-column title="Created By" field="createdBy" width="150px" filterable="true">
			                    	<kendo:grid-column-filterable>
	    			                <kendo:grid-column-filterable-ui>
    					            <script> 
							        function propertyCreatedByFilter(element) {
								    element.kendoAutoComplete({
									    dataType: 'JSON',
									    placeholder : "Enter Created Name",
									    dataSource: {
										transport: {
										read: "${commonFilterForSubLedgerUrl}/createdBy"
										          }
									           }
								            });
					  		              }
					  	            </script>		
	    			                </kendo:grid-column-filterable-ui>
	    		                    </kendo:grid-column-filterable>
			                        </kendo:grid-column>
			 						
																		     
        								 <kendo:grid-column title="&nbsp;" width="175px" >
							            	<kendo:grid-column-command>
							            		<kendo:grid-column-commandItem name="edit"/>
							            		<kendo:grid-column-commandItem name="view" />
							            		<kendo:grid-column-commandItem name="Upload"/>
							            		<kendo:grid-column-commandItem name="destroy"/>
							            	</kendo:grid-column-command>
							            </kendo:grid-column>
							            
							         <kendo:grid-column title="&nbsp;" width="110px">
										<kendo:grid-column-command >
											<kendo:grid-column-commandItem name="Change_Status" click="subLedgerStatusClick" />
										</kendo:grid-column-command>
								        </kendo:grid-column> --%>
							            
        						</kendo:grid-columns>
        						
        						  <kendo:dataSource requestEnd="elRateSlabOnRequestEnd" >
						            <kendo:dataSource-transport>
						            <kendo:dataSource-transport-read url="${elSubLedgerReadUrl}/#=elLedgerid#" dataType="json" type="POST" contentType="application/json"/>
						            <kendo:dataSource-transport-create url="${elSubLedgerCreateUrl}/#=elLedgerid#" dataType="json" type="GET" contentType="application/json" />
						            <%-- <kendo:dataSource-transport-update url="${elRateSlabUpdateUrl}/#=elrmid#" dataType="json" type="POST" contentType="application/json" />
						            <kendo:dataSource-transport-destroy url="${elRateSlabDeleteUrl}" dataType="json" type="POST" contentType="application/json" /> --%>
						            </kendo:dataSource-transport>
						            
						            <kendo:dataSource-schema>
						                <kendo:dataSource-schema-model id="elSubLedgerid">
						                    <kendo:dataSource-schema-model-fields>
						                    
						                    <kendo:dataSource-schema-model-field name="elSubLedgerid" type="number">
											<kendo:dataSource-schema-model-field-validation  />
											</kendo:dataSource-schema-model-field>
						                    
											<kendo:dataSource-schema-model-field name="transactionId" type="number">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="transactionName" type="string">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="amount" type="number">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="balanceAmount" type="number">
											</kendo:dataSource-schema-model-field>
																
											<kendo:dataSource-schema-model-field name="createdBy">
											</kendo:dataSource-schema-model-field>
						
											<kendo:dataSource-schema-model-field name="status"
											editable="false" type="string" />
						                    	
						                    </kendo:dataSource-schema-model-fields>
						                 </kendo:dataSource-schema-model>
						             </kendo:dataSource-schema>
						          </kendo:dataSource>
        				</kendo:grid>		
                    </div>
                </kendo:tabStrip-item-content>
            </kendo:tabStrip-item>
            
          <%--   <kendo:tabStrip-item text="Ledger Information">
                <kendo:tabStrip-item-content>
                    <div class='wethear' style="width: 75%;">
		
                    </div>
                </kendo:tabStrip-item-content>
            </kendo:tabStrip-item> --%>
            
			</kendo:tabStrip-items>
		</kendo:tabStrip>
	</kendo:grid-detailTemplate>
	
	
<div id="alertsBox" title="Alert"></div>


<script>

$( document ).ready(function() {	
	var todayDate = new Date();
	var picker = $("#fromMonthpicker").kendoDatePicker({
		start: "month",
		depth: "month",
		  value:new Date(),
				 format: "dd/MM/yyyy"
			}).data("kendoDatePicker"),
    dateView = picker.dateView;
	dateView.calendar.element.removeData("dateView");        
	picker.max(todayDate);
  	picker.options.depth = dateView.options.depth = 'month';
  	picker.options.start = dateView.options.start = 'month';
   	picker.value(picker.value());
   
   	$('#fromMonthpicker').keyup(function() {
		$('#fromMonthpicker').val("");
	});
   	var todayDate = new Date();
	var picker = $("#toMonthpicker").kendoDatePicker({
		start: "month",
		depth: "month",
		  value:new Date(),
				 format: "dd/MM/yyyy"
			}).data("kendoDatePicker"),
    dateView = picker.dateView;
	dateView.calendar.element.removeData("dateView");        
	picker.max(todayDate);
  	picker.options.depth = dateView.options.depth = 'month';
  	picker.options.start = dateView.options.start = 'month';
   	picker.value(picker.value());
   
   	$('#toMonthpicker').keyup(function() {
		$('#toMonthpicker').val("");
	});
});

function searchByMonth() {
    var fromDate = $('#fromMonthpicker').val();
    var toDate = $('#toMonthpicker').val();
    //alert(fromDate);
    var splitmonth=fromDate.split("/");
	   var splitmonth1=toDate.split("/");
	   if(splitmonth[2]>splitmonth1[2])
		   {
		   alert("To Date should be greater than From Date");
		   return false;
		   }
	   if(splitmonth[2]==splitmonth1[2])
		   {
		   if(splitmonth[1]>splitmonth1[1])
			   {
			   alert("To Date should be greater than From Date");
			   return false;
			   }
		   else if(splitmonth[1]==splitmonth1[1])
			   {
			   if(splitmonth[0]>splitmonth1[0])
				   {
				   alert("To Date should be greater than From Date");
				   return false;
				   }
			   
			   }
		   }
	    $.ajax({
		type : "GET",
		url : "./Ledger/searchLedgerDataByMonth",
		dataType : "json",
		data : {
			fromDate : fromDate,
			toDate : toDate
		},
		success : function(result) {
			parse(result);
			var grid = $("#grid").getKendoGrid();
			var data = new kendo.data.DataSource();
			grid.dataSource.data(result);
			grid.refresh();
		}
	}); 
}

var SelectedRowId = "";

function onChangeLedger(arg) {
	 var gview = $("#grid").data("kendoGrid");
	 var selectedItem = gview.dataItem(gview.select());
	 SelectedRowId = selectedItem.elLedgerid;
	 this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));  	 
	 $(".k-master-row.k-state-selected").show();
    
}

function dataBoundMC(e) {
	/* var grid = $("#subLedgerEvent_"+SelectedRowId).data("kendoGrid");
	var selectedItem = grid.dataItem(grid.tbody.find(">tr:last"));
	var balanceAmount = selectedItem.balanceAmount;
	
	$('#sumTotal_' + SelectedRowId).text(balanceAmount);
	$('#totlcst').show(); */
	
	var data = this.dataSource.view();
	var sum = 0;
	for (var i = 0; i < data.length; i++) {
		row = this.tbody.children("tr[data-uid='" + data[i].uid + "']");
		sum = sum + data[i].amount;
	}
	$('#sumTotal_' + SelectedRowId).text(Math.round(sum));
	$('#totlcst').show();
}

//for parsing timestamp data fields
function parse (response) {
    $.each(response, function (idx, elem) {   
    	   if(elem.postedBillDate=== null){
    		   elem.postedBillDate = "";
    	   }else{
    		   elem.postedBillDate = kendo.parseDate(new Date(elem.postedBillDate),'dd/MM/yyyy HH:mm');
    	   }
    	   
    	   if(elem.ledgerDate=== null){
    		   elem.ledgerDate = "";
    	   }else{
    		   elem.ledgerDate = kendo.parseDate(new Date(elem.ledgerDate),'MMMM,yyyy');
    	   }
       });
       
       return response;
}

//Onclick functions

function clearFilterLedger()
{
   $("form.k-filter-menu button[type='reset']").slice().trigger("click");
   var gridStoreIssue = $("#grid").data("kendoGrid");
   gridStoreIssue.dataSource.read();
   gridStoreIssue.refresh();
}

function subLedgerStatusClick()
	{
	    /* var gview = $("#grid").data("kendoGrid");
	 	var selectedItem = gview.dataItem(gview.select());
	 	SelectedRowId = selectedItem.elLedgerid; */
		var elSubLedgerid="";
		var gridParameter = $("#subLedgerEvent_"+SelectedRowId).data("kendoGrid");
		var selectedAddressItem = gridParameter.dataItem(gridParameter.select());
		elSubLedgerid = selectedAddressItem.elSubLedgerid;
		$.ajax
		({			
			type : "POST",
			url : "./ledgers/subLedgerStatusUpdateFromInnerGrid/"+elSubLedgerid,
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
				$('#subLedgerEvent_'+SelectedRowId).data('kendoGrid').dataSource.read();
			}
		});
	}
	
 
 function dropDownChecksEditor(container, options) {
		var res = (container.selector).split("=");
		var attribute = res[1].substring(0,res[1].length-1);
		
		$('<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '" name = "'+attribute+'" id = "'+attribute+'Id"/>')
				.appendTo(container).kendoDropDownList({
					optionLabel : {
						text : "Select",
						value : "",
					},
					defaultValue : false,
					sortable : true,
					dataSource : {
						transport : {
							read : "${allChecksUrl}/"+attribute,
						}
					}
				});
		 $('<span class="k-invalid-msg" data-for="'+attribute+'"></span>').appendTo(container);
	}
 
	$("#grid").on("click", "#temPID", function(e) {
		var button = $(this), enable = button.text() == "Active";
		var widget = $("#grid").data("kendoGrid");
		var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));

					if (enable) 
					{
						$.ajax
						({
							type : "POST",
							url : "./ledgers/ledgerStatus/" + dataItem.id + "/activate",
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
								button.text('In-active');
								$('#grid').data('kendoGrid').dataSource.read();
							}
						});
					} 
					else 
					{
						$.ajax
						({
							type : "POST",
							url : "./ledgers/ledgerStatus/" + dataItem.id + "/deactivate",
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
								button.text('Active');
								$('#grid').data('kendoGrid').dataSource.read();
							}
						});
					}
			});
	
	var setApCode = "";
function electricityLedgerEvent(e)
{
	/***************************  to remove the id from pop up  **********************/
	$('div[data-container-for="elLedgerid"]').remove();
	$('label[for="elLedgerid"]').closest(
	'.k-edit-label').remove();
	
	$(".k-edit-field").each(function () {
		$(this).find("#temPID").parent().remove();  
   	});
	
	$('label[for="status"]').parent().hide();  
	$('div[data-container-for="status"]').hide();
	
	$('label[for="createdBy"]').parent().hide();  
	$('div[data-container-for="createdBy"]').hide();
	
	/************************* Button Alerts *************************/
	if (e.model.isNew()) 
    {
		
		$(".k-window-title").text("Add New Ledger");
		$(".k-grid-update").text("Save");
		
    }
	else{
		
		setApCode = $('input[name="elLedgerid"]').val();
		$(".k-window-title").text("Edit Rate Master Details");
	}
}

$("#grid").on("click", ".k-grid-Clear_Filter", function(){
    //custom actions
	$("form.k-filter-menu button[type='reset']").slice().trigger("click");
	var gridPets = $("#grid").data("kendoGrid");
	gridPets.dataSource.read();
	gridPets.refresh();
});


/********************** to hide the child table id ***************************/
function subLedgerEvent(e)
{
	 $('div[data-container-for="elSubLedgerid"]').remove();
	 $('label[for="elSubLedgerid"]').closest('.k-edit-label').remove();  
	 
	 $('div[data-container-for="elLedgerid"]').remove();
	 $('label[for="elLedgerid"]').closest('.k-edit-label').remove(); 
	 
	 
	 $('div[data-container-for="status"]').remove();
	 $('label[for="status"]').closest('.k-edit-label').remove(); 
	 
	 
	 $('div[data-container-for="createdBy"]').remove();
	 $('label[for="createdBy"]').closest('.k-edit-label').remove(); 
	 
		if (e.model.isNew()) 
	    {
			
			$(".k-window-title").text("Add New Sub Ledger");
			$(".k-grid-update").text("Save");
			
			
	    }
		else{
			
			setApCode = $('input[name="elSubLedgerid"]').val();
			$(".k-window-title").text("Edit Sub Ledger Details");
		}
	}



/***************************  Custom message validation  **********************/

(function ($, kendo) {
    $.extend(true, kendo.ui.validator, {
         rules: { 
          descriptionValidation: function (input, params) {
                
                 if (input.attr("name") == "rateDescription") {
                  return $.trim(input.val()) !== "";
                 }
                 return true;
             },                
             rateofunit: function (input, params) {
               
                 if (input.attr("name") == "rateUnit") {
                  return $.trim(input.val()) !== "";
                 }
                 return true;
             },  
             
             rateType: function (input, params) {
                 if (input.attr("name") == "rateType") {
                  return $.trim(input.val()) !== "";
                 }
                 return true;
             },  

             rateUOM: function (input, params) {
                 if (input.attr("name") == "rateUOM") {
                  return $.trim(input.val()) !== "";
                 }
                 return true;
             },
             
              addressSeasonFrom: function (input, params) 
             {
                 if (input.filter("[name = 'validFrom']").length && input.val() != "") 
                 {                          
                     var selectedDate = input.val();
                     var todaysDate = $.datepicker.formatDate('dd/mm/yy', new Date());
                     var flagDate = false;

                     if ($.datepicker.parseDate('dd/mm/yy', selectedDate) >= $.datepicker.parseDate('dd/mm/yy', todaysDate)) 
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
                 if (input.filter("[name = 'validTo']").length && input.val() != "") 
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
                 if (input.filter("[name = 'validTo']").length && input.val() != "") 
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
             
             rateUOMValidation: function (input, params) 
             {         
                 if (input.filter("[name='rateUOM']").length && input.val() != "") 
                 {
                	 return /^[a-zA-Z0-9 ]{1,45}$/.test(input.val());
                 }        
                 return true;
             },
             
         },
       //custom rules messages
         messages: { 
          descriptionValidation: "Description should not be empty",
          rateofunit:"Rate per unit should not be empty",
          rateType:"Rate Type should not be empty",  
          rateUOM:"UOM should not be empty",
          validatinForNumbers:"Only numbers are allowed,follwed by two decimal points",
          addressSeasonFrom:"From Date must be selected in the future",
		  addressSeasonTo1:"Select From date first before selecting To date and change To date accordingly",
		  rateUOMValidation:"Rate UOM can contain alphabets and spaces but cannot allow numbers and other special characters and maximum 45 characters are allowed",
		  addressSeasonTo2:"To date should be after From date"
         }
    });
})(jQuery, kendo); 


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
				//alert("Error: Creating the USER record\n\n" + errorInfo);

				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Error: Creating the Ledger Details<br>" + errorInfo);
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
				//alert("Error: Updating the USER record\n\n" + errorInfo);
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Error: Updating the Ledger Details<br>" + errorInfo);
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
			}

			$('#grid').data('kendoGrid').refresh();
			$('#grid').data('kendoGrid').dataSource.read();
			return false;
		}
		
		if (r.response.status == "CHILD") {

			
				$("#alertsBox").html("");
				$("#alertsBox")
						.html("Can't Delete Ledger Details, Child Record Found");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				$('#grid').data('kendoGrid').refresh();
				$('#grid').data('kendoGrid').dataSource.read();
			return false;
		}

		else if (r.response.status == "INVALID") {

			errorInfo = "";

			errorInfo = r.response.result.invalid;

			if (r.type == "create") {
				//alert("Error: Creating the USER record\n\n" + errorInfo);
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Error: Creating the Ledger Details<br>" + errorInfo);
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
			}
			$('#grid').data('kendoGrid').refresh();
			$('#grid').data('kendoGrid').dataSource.read();
			return false;
		}

		else if (r.response.status == "EXCEPTION") {

			errorInfo = "";

			errorInfo = r.response.result.exception;

			if (r.type == "create") {
				
				//alert("Error: Creating the USER record\n\n" + errorInfo);
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Error: Creating the Ledger Details<br>" + errorInfo);
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
				//alert("Error: Creating the USER record\n\n" + errorInfo);
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Error: Updating the Ledger Details<br>" + errorInfo);
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
			}

			$('#grid').data('kendoGrid').refresh();
			$('#grid').data('kendoGrid').dataSource.read();
			return false;
		}

		else if (r.type == "create") {
			
			/*  $('#grid_' + SelectedRowId).data().kendoGrid.dataSource
			.read({
				personId : SelectedRowId
			});  */
			
			$("#alertsBox").html("");
			$("#alertsBox")
					.html("Ledger Details created successfully");
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
			
			
			//alert("User record updated successfully");
			$("#alertsBox").html("");
			$("#alertsBox")
					.html("Ledger Details updated successfully");
			$("#alertsBox").dialog({
				modal : true,
				buttons : {
					"Close" : function() {
						$(this).dialog("close");
					}
				}
			});
			 /*  $('#grid_'+ SelectedRowId).data().kendoGrid.dataSource
				.read({
					personId : SelectedRowId
				});   */

		}
		

	}
}


/************************************* for inner rate slab request *********************************/

function elRateSlabOnRequestEnd(e)
 {
	  if (typeof e.response != 'undefined')
		{
			//alert("Response is Undefined");

			if (e.response.status == "FAIL") 
			{
				errorInfo = "";

				for (var k = 0; k < e.response.result.length; k++) 
				{
					errorInfo += (k + 1) + ". "
							+ e.response.result[k].defaultMessage + "<br>";

				}

				if (e.type == "create") {
					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Assigning Permission to AccessCard<br>" + errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});

				}

				else if (e.type == "update") {
					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Updating the Permission to AccessCard<br>" + errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
				}

				$('#gridAccessCardPermission_'+SelectedRowId).data().kendoGrid.dataSource.read({personId:SelectedAccessCardId});
				/* var grid = $("#propertyGrid_"+SelectedRowId).data("kendoGrid");
				grid.dataSource.read();
				grid.refresh(); */
				return false;
			}
		

	  else if (e.type == "create") 
		{
			$("#alertsBox").html("");
			$("#alertsBox").html("Sub Ledger Record Created Successfully");
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
		}

		else if (e.type == "update") 
		{
			$("#alertsBox").html("");
			$("#alertsBox").html("Sub Ledger Record updated successfully");
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
		}

		else if (e.type == "destroy") 
		{
			$("#alertsBox").html("");
			$("#alertsBox").html("Sub Ledger Record delete successfully");
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
		}	
			
		}

 }


</script>
