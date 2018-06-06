<%@include file="/common/taglibs.jsp"%>

<c:url value="/deposits/depositsCreate" var="createUrl" />
<c:url value="/deposits/depositsRead" var="readUrl" />
<c:url value="/deposits/depositsDestroy" var="destroyUrl" />
<c:url value="/deposits/depositsUpdate" var="updateUrl" />
<c:url value="/deposits/commonFilterForAccountNumbersUrl" var="commonFilterForAccountNumbersUrl" />
<c:url value="/deposits/commonFilterForPropertyNoUrl" var="commonFilterForPropertyNoUrl" />

<c:url value="/depositsDetails/depositsDetailsRead" var="depositsDetailsReadUrl" />
<c:url value="/depositsDetails/depositLineItemCreate" var="depositsDetailsCreateUrl" />
<c:url value="/depositsDetails/depositLineItemUpdate" var="depositsDetailsUpdateUrl" />
<c:url value="/depositsDetails/depositLineItemDestroy" var="depositsDetailsDestroy" />

<c:url value="/depositsDetails/depositRefundRead" var="refundReadUrl" />
<c:url value="/depositsDetails/depositRefundCreate" var="refundCreateUrl" />

<c:url value="/depositsDetails/depositsDetails/filter" var="commonFilterForPaymentSegmentsUrl" />
<c:url value="/deposits/getAllAccountNumbers" var="accountNumberAutocomplete" />
<c:url value="/deposits/getTransactionCodes" var="transactionCodeUrl" />

<c:url value="/depositsDetails/servicType" var="servicTypeUrl" />
<c:url value="/common/getAllChecks" var="allChecksUrl" />
<c:url value="/billingPayments/getAllBankNames" var="getAllBankNames" />
<c:url value="/deposits/getPersonListForFileter" var="personNamesFilterUrl" />

<kendo:grid name="depositGrid" change="onChangeDeposits" dataBound="dataBoundDeposit" pageable="true" resizable="true" edit="depositsEvent" detailTemplate="depositsTemplate" sortable="true" reorderable="true" selectable="true" scrollable="true" filterable="false" groupable="true">
    <kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" refresh="true" info="true" previousNext="true">
		<kendo:grid-pageable-messages itemsPerPage="Deposits per page" empty="No deposit to display" refresh="Refresh all the deposits" 
			display="{0} - {1} of {2} New Deposits" first="Go to the first page of deposits" last="Go to the last page of deposits" next="Go to the next page of deposits"
			previous="Go to the previous page of deposits"/>
	</kendo:grid-pageable>
	<kendo:grid-filterable extra="false">
		<kendo:grid-filterable-operators>
			<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains" />
				<kendo:grid-filterable-operators-date eq="Is equal to" gt="Is after" lt="Is before" gte="Is after or equals to" lte="Is before or equals to" neq="Is not equal to"/>
				<kendo:grid-filterable-operators-number eq="Is equal to" gt="Is greather than" gte="IS greather than and equal to" lt="Is less than" lte="Is less than and equal to" neq="Is not equal to"/>
		</kendo:grid-filterable-operators> 
	</kendo:grid-filterable>
	<kendo:grid-editable mode="popup"/>
		<kendo:grid-toolbar>
		      <kendo:grid-toolbarItem name="create" text="New Deposits Details" />
		      <kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterPayments()>Clear Filter</a>"/>
		    <%--   <kendo:grid-toolbarItem name="approveAdjustment" text="Approve Adjustment"/> --%>
		     <%--  <kendo:grid-toolbarItem name="postAdjustment" text="Post Adjustment"/> --%>
	    </kendo:grid-toolbar>
	<kendo:grid-columns>
	    
	    <kendo:grid-column title="deposits id" field="depositsId" width="10px" hidden="true" filterable="false" sortable="false" />
	    
	    <kendo:grid-column title="Account&nbsp;Number&nbsp;*" field="accountNo" width="140px" filterable="true">
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
	    
	    <kendo:grid-column title="Account&nbsp;Number&nbsp;*" field="accountId" hidden="true" width="140px" filterable="true" editor="accountNumberEditor"/>
	    
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
	    
	    <kendo:grid-column title="property&nbsp;Number" field="propertyNo"  width="135px" filterable="true">
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
	    
	    <kendo:grid-column title="Total&nbsp;Amount" field="totalAmount" width="130px" filterable="true"/>
	    
	    <kendo:grid-column title="Refund&nbsp;Amount" field="refundAmount" width="130px" filterable="true"/>
	    
	    <kendo:grid-column title="Balance&nbsp;Amount" field="balance" width="130px" filterable="true"/>
	 
	    <kendo:grid-column title="depositType" field="depositType" hidden="true" width="90px" filterable="true">
	    </kendo:grid-column>

		<%-- <kendo:grid-column title="&nbsp;" width="100px">
			<kendo:grid-column-command>
			    <kendo:grid-column-commandItem name="edit"/>
				<kendo:grid-column-commandItem name="destroy" />
			</kendo:grid-column-command>
		</kendo:grid-column> --%>
				
		  <%-- <kendo:grid-column title=""
				template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Active#=data.elBillId#'>#= data.status == 'Generated' ? 'Approved' : 'Posted' #</a>"
				width="90px" /> --%>
				
	   </kendo:grid-columns>

	<kendo:dataSource pageSize="20" requestEnd="onRequestEnd" requestStart="OnRequestSatrt">
		<kendo:dataSource-transport>
			<kendo:dataSource-transport-create url="${createUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json" />
			<kendo:dataSource-transport-update url="${updateUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-destroy url="${destroyUrl}" dataType="json" type="GET" contentType="application/json" />
		</kendo:dataSource-transport>
<%-- parse="parse" --%>
		<kendo:dataSource-schema parse="parseAccountId">
			<kendo:dataSource-schema-model id="depositsId">
				<kendo:dataSource-schema-model-fields>
					<kendo:dataSource-schema-model-field name="depositsId" type="number"/>
					
					<kendo:dataSource-schema-model-field name="accountId" type="number" defaultValue=""/>
					
					<kendo:dataSource-schema-model-field name="propertyNo" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="depositType" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="refundAmount" type="number">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="personName" type="string">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="balance" type="number">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="totalAmount" type="number" defaultValue="">
					         <kendo:dataSource-schema-model-field-validation min="0"/>
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="accountNo" type="string">
					</kendo:dataSource-schema-model-field>
				
					<kendo:dataSource-schema-model-field name="status" type="string">
					</kendo:dataSource-schema-model-field>
					
				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>

	</kendo:dataSource>

</kendo:grid>

<kendo:grid-detailTemplate id="depositsTemplate">
		<kendo:tabStrip name="tabStrip_#=depositsId#">
		<kendo:tabStrip-animation>
			</kendo:tabStrip-animation>
			<kendo:tabStrip-items>
			
 			<kendo:tabStrip-item selected="true" text="Deposit Lines">
                <kendo:tabStrip-item-content>
                    <div class='wethear' style='width: 100%'>
						    <br/>
							<kendo:grid name="depositDetails_#=depositsId#" remove="depositCalcLineDeleteEvent" dataBound="dataBoundDepositLineItem" pageable="true" resizable="true" sortable="true" reorderable="true"
								selectable="true" scrollable="true" change="onChangeDepositLineItemEvent" edit="depositDetailsEvent" editable="true" >
								<kendo:grid-pageable pageSize="10"></kendo:grid-pageable>
								<kendo:grid-filterable extra="false">
			                    <kendo:grid-filterable-operators>
				                    <kendo:grid-filterable-operators-string eq="Is equal to" />
			                    </kendo:grid-filterable-operators>
		                        </kendo:grid-filterable>
								<kendo:grid-editable mode="popup"/>
						       <kendo:grid-toolbar >
						            <kendo:grid-toolbarItem name="create" text="Add Deposit Line Item" />
						        </kendo:grid-toolbar> 
        						<kendo:grid-columns>
        						    <kendo:grid-column title="ddId" field="ddId" hidden="true" width="100px">
									</kendo:grid-column> 
									
									<kendo:grid-column title="deposits id" field="depositsId" width="10px" hidden="true" filterable="false" sortable="false" />
									
									<kendo:grid-column title="Service&nbsp;Type&nbsp;*" field="typeOfServiceDeposit" filterable="false"  width="90px" editor="dropDownChecksEditor">
									</kendo:grid-column>
									
									<kendo:grid-column title="Transaction&nbsp;Name&nbsp;*" field="transactionName" filterable="false" width="130px">
						    		</kendo:grid-column>
						    		
									<kendo:grid-column title="Transaction&nbsp;Name&nbsp;*" field="transactionCode" filterable="false" hidden="true" width="130px" editor="transactionCodeEditor">
									</kendo:grid-column>
									
									<kendo:grid-column title="&nbsp;" field="loadEnhanced" editor="loadEnhancedEditor" hidden="true" width="140px" filterable="true">
									</kendo:grid-column>
									
									<kendo:grid-column title="Sanctioned&nbsp;Load" field="sancationedLoad" hidden="true" width="140px" filterable="true">
									</kendo:grid-column>
									
									<kendo:grid-column title="New&nbsp;Sanctioned&nbsp;Load" field="newSancationedLoad" hidden="true" width="140px" filterable="true">
									</kendo:grid-column>
									
									<kendo:grid-column title="Sanctioned&nbsp;Load&nbsp;Name" field="sancationedLoadName" hidden="true" width="140px" filterable="true">
									</kendo:grid-column>
									
									<kendo:grid-column title="Category&nbsp;Type" field="category" filterable="false"  width="90px">
									</kendo:grid-column>
									
									<kendo:grid-column title="Amount&nbsp;*" field="amount" format="{0:n2}" width="85px" filterable="false">
									</kendo:grid-column> 
									
									<kendo:grid-column title="Load&nbsp;Change" field="loadChangeUnits" format="{0:n2}" width="90px" filterable="false">
									</kendo:grid-column> 
									
									<kendo:grid-column title="Notes" field="notes" filterable="false"  width="170px">
									</kendo:grid-column>
									
									<kendo:grid-column title="Collection&nbsp;Type" field="collectionType" filterable="false"  width="110px">
									</kendo:grid-column>
									
	    							<kendo:grid-column title="Payment&nbsp;Mode&nbsp;*" field="paymentMode" width="115px" filterable="false" editor="dropDownChecksEditorPaymentMode">
								    </kendo:grid-column>
	    
	    							<kendo:grid-column title="Instrument&nbsp;Date&nbsp;*" field="instrumentDate" format="{0:dd/MM/yyyy}" width="120px" filterable="false">
	   							    </kendo:grid-column>
	    
	    							<kendo:grid-column title="Instrument&nbsp;Number&nbsp;*" field="instrumentNo" width="140px" filterable="false">
								    </kendo:grid-column>
	    
	   							    <kendo:grid-column title="Bank&nbsp;Name&nbsp;*" field="bankName" width="110px" filterable="false" editor="bankNames">
	   								</kendo:grid-column>
	   								
	   								<%-- <kendo:grid-column title="Refund&nbsp;Date" field="refundDate" width="110px" format="{0:dd/MM/yyyy}" filterable="false">
	   								</kendo:grid-column>
	   								
	   								<kendo:grid-column title="Refund&nbsp;Status" field="status" width="110px" filterable="false">
	   								</kendo:grid-column> --%>

								<%-- <kendo:grid-column title="&nbsp;" width="120px">
									<kendo:grid-column-command>
										<kendo:grid-column-commandItem name="Refund Amount"	click="refundAmountButtonClick" />
									</kendo:grid-column-command>
								</kendo:grid-column> --%>

								<kendo:grid-column title="&nbsp;" width="160px" >
							            <kendo:grid-column-command>
							            	<kendo:grid-column-commandItem name="edit"/>
							            	<kendo:grid-column-commandItem name="destroy"/>
							            </kendo:grid-column-command>
							        </kendo:grid-column>
							   						            
        						</kendo:grid-columns>
        						
        						  <kendo:dataSource requestEnd="calcLineOnRequestEnd" requestStart="calcLineOnRequestStart">
						            <kendo:dataSource-transport>
						            <kendo:dataSource-transport-read url="${depositsDetailsReadUrl}/#=depositsId#" dataType="json" type="POST" contentType="application/json"/>
						            <kendo:dataSource-transport-create url="${depositsDetailsCreateUrl}/#=depositsId#" dataType="json" type="GET" contentType="application/json" />
						            <kendo:dataSource-transport-update url="${depositsDetailsUpdateUrl}/#=depositsId#" dataType="json" type="GET" contentType="application/json" />
						            <kendo:dataSource-transport-destroy url="${depositsDetailsDestroy}" dataType="json" type="GET" contentType="application/json" />
						            </kendo:dataSource-transport>
						            
						            <kendo:dataSource-schema>
						                <kendo:dataSource-schema-model id="ddId">
						                    <kendo:dataSource-schema-model-fields>
						                    
						                    <kendo:dataSource-schema-model-field name="ddId" type="number">
											<kendo:dataSource-schema-model-field-validation  />
											</kendo:dataSource-schema-model-field>
											
											 <kendo:dataSource-schema-model-field name="depositsId" type="number">
											<kendo:dataSource-schema-model-field-validation  />
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="sancationedLoad" type="string">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="loadChangeUnits" type="number">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="notes" type="string">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="newSancationedLoad" type="number" defaultValue="">
												<kendo:dataSource-schema-model-field-validation min="0"/>
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="loadEnhanced" type="string">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="typeOfServiceDeposit" type="string">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="sancationedLoadName" type="string">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="transactionCode" type="string">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="category" type="string">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="transactionName" type="string">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="instrumentNo" type="string">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="instrumentDate" type="date">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="bankName" type="string">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="status" type="string">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="refundDate" type="date">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="paymentMode" type="string">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="amount" type="number" defaultValue="">
											   <kendo:dataSource-schema-model-field-validation min="0"/>
											</kendo:dataSource-schema-model-field>
						                    	
						                    </kendo:dataSource-schema-model-fields>
						                 </kendo:dataSource-schema-model>
						             </kendo:dataSource-schema>
						          </kendo:dataSource>
        				</kendo:grid>		
                    </div>
                </kendo:tabStrip-item-content>
            </kendo:tabStrip-item>
            
            <kendo:tabStrip-item selected="false" text="Refund Payments">
                <kendo:tabStrip-item-content>
                    <div class='wethear' style='width: 100%'>
						    <br/>
							<kendo:grid name="refundDetails_#=depositsId#" pageable="true" resizable="true" sortable="true" reorderable="true"
								selectable="true" scrollable="true" edit="depositRefundEvent" editable="true" >
								<kendo:grid-pageable pageSize="10"></kendo:grid-pageable>
								<kendo:grid-filterable extra="false">
			                    <kendo:grid-filterable-operators>
				                    <kendo:grid-filterable-operators-string eq="Is equal to" />
			                    </kendo:grid-filterable-operators>
		                        </kendo:grid-filterable>
								<kendo:grid-editable mode="popup"/>
						       <kendo:grid-toolbar >
						            <kendo:grid-toolbarItem name="create" text="Refund Amount" />
						        </kendo:grid-toolbar> 
        						<kendo:grid-columns>
        						    <kendo:grid-column title="refundId" field="refundId" hidden="true" width="100px">
									</kendo:grid-column> 
									
									<kendo:grid-column title="deposits id" field="depositsId" width="10px" hidden="true" filterable="false" sortable="false" />
									
									<kendo:grid-column title="Refund&nbsp;Date" field="refundDate" width="110px" format="{0:dd/MM/yyyy}" filterable="false">
	   								</kendo:grid-column>
									
									<kendo:grid-column title="&nbsp;" field="refundType" editor="refundTypeEditor" hidden="true" width="140px" filterable="true">
									</kendo:grid-column>
									
									<kendo:grid-column title="Sanctioned&nbsp;Load" field="sancationedLoadRefund" hidden="true" width="140px" filterable="true">
									</kendo:grid-column>
									
									<kendo:grid-column title="New&nbsp;Sanctioned&nbsp;Load" field="newSancationedLoadRefund" hidden="true" width="140px" filterable="true">
									</kendo:grid-column>
									
									<kendo:grid-column title="Sanctioned&nbsp;Load&nbsp;Name" field="sancationedLoadName" hidden="true" width="140px" filterable="true">
									</kendo:grid-column>
									
									<kendo:grid-column title="Refund&nbsp;Amount&nbsp;*" field="refundAmount" format="{0:n2}" width="90px" filterable="false">
									</kendo:grid-column> 
									
									<kendo:grid-column title="Load&nbsp;Change" field="loadChangeUnits" format="{0:n2}" width="90px" filterable="false">
									</kendo:grid-column> 
									
									<kendo:grid-column title="Notes" field="notes" filterable="false"  width="170px">
									</kendo:grid-column>
									
	    							<kendo:grid-column title="Payment&nbsp;Through&nbsp;*" field="paymentThrough" width="115px" filterable="false" editor="dropDownChecksEditorPaymentThrough">
								    </kendo:grid-column>
	    
	    							<kendo:grid-column title="Instrument&nbsp;Date&nbsp;*" field="instrumentDate" format="{0:dd/MM/yyyy}" width="110px" filterable="false">
	   							    </kendo:grid-column>
	    
	    							<kendo:grid-column title="Instrument&nbsp;Number&nbsp;*" field="instrumentNo" width="130px" filterable="false">
								    </kendo:grid-column>
	    
	   							    <kendo:grid-column title="Bank&nbsp;Name&nbsp;*" field="bankName" width="110px" filterable="false" editor="bankNames">
	   								</kendo:grid-column>
							   						            
        						</kendo:grid-columns>
        						
        						  <kendo:dataSource requestEnd="depositRefundOnRequestEnd" requestStart="depositRefundOnRequestStart">
						            <kendo:dataSource-transport>
						            <kendo:dataSource-transport-read url="${refundReadUrl}/#=depositsId#" dataType="json" type="POST" contentType="application/json"/>
						            <kendo:dataSource-transport-create url="${refundCreateUrl}/#=depositsId#" dataType="json" type="GET" contentType="application/json" />
						            </kendo:dataSource-transport>
						            
						            <kendo:dataSource-schema>
						                <kendo:dataSource-schema-model id="refundId">
						                    <kendo:dataSource-schema-model-fields>
						                    
						                    <kendo:dataSource-schema-model-field name="refundId" type="number">
											<kendo:dataSource-schema-model-field-validation  />
											</kendo:dataSource-schema-model-field>
											
											 <kendo:dataSource-schema-model-field name="depositsId" type="number">
											<kendo:dataSource-schema-model-field-validation  />
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="sancationedLoadRefund" type="string">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="loadChangeUnits" type="number">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="notes" type="string">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="newSancationedLoadRefund" type="number" defaultValue="" >
												<kendo:dataSource-schema-model-field-validation min="0"/>
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="refundType" type="string">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="sancationedLoadName" type="string">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="instrumentNo" type="string">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="instrumentDate" type="date">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="bankName" type="string">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="status" type="string">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="refundDate" type="date">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="paymentThrough" type="string">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="refundAmount" type="number" defaultValue="">
											   <kendo:dataSource-schema-model-field-validation min="0"/>
											</kendo:dataSource-schema-model-field>
						                    	
						                    </kendo:dataSource-schema-model-fields>
						                 </kendo:dataSource-schema-model>
						             </kendo:dataSource-schema>
						          </kendo:dataSource>
        				</kendo:grid>		
                    </div>
                </kendo:tabStrip-item-content>
            </kendo:tabStrip-item>
			</kendo:tabStrip-items>
		</kendo:tabStrip>
	</kendo:grid-detailTemplate>
<div id="alertsBox" title="Alert"></div>
<script>

var accountIdArray = [];

function parseAccountId(response) {   
     var data = response; 
     accountIdArray = [];
	 for (var idx = 0, len = data.length; idx < len; idx ++) {
		var res1 = (data[idx].accountId);
		accountIdArray.push(res1);
	 }  
	 return response;
} 

var adjustmentStatus="";

var SelectedRowId = "";
var SelectedAccountId = "";
var res="";
var balanceAmount = "";
function onChangeDeposits(arg) {
	 var gview = $("#depositGrid").data("kendoGrid");
	 var selectedItem = gview.dataItem(gview.select());
	 SelectedRowId = selectedItem.depositsId;
	 SelectedAccountId = selectedItem.accountId;
	 balanceAmount = selectedItem.balance;
	 this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
}

function depositCalcLineDeleteEvent(){
	securityCheckForActions("./Accounts/Deposits/DepositLines/destroyButton");
	var conf = confirm("Are u sure want to delete this line item details?");
	 if(!conf){
	   $("#depositDetails_"+SelectedRowId).data().kendoGrid.dataSource.read();
	   throw new Error('deletion aborted');
	 }
}

var selectedDepositLineId = "";
function onChangeDepositLineItemEvent(arg){
	var gview = $("#depositDetails_"+SelectedRowId).data("kendoGrid");
	var selectedItem = gview.dataItem(gview.select());
	selectedDepositLineId = selectedItem.ddId;
}

function refundAmountButtonClick() {
	/* var result=securityCheckForActionsForStatus("./CustomerCare/OpenNewTicket/re-OpenTicketStatusButton");	  
	if(result=="success"){ */
	$.ajax({
		type : "POST",
		url : "./deposits/refundAmountButtonClick/"+ selectedDepositLineId,
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
			$('#depositGrid').data('kendoGrid').dataSource.read();
		}
	});
	/* } */

}

function loadEnhancedEditor(container, options) {
	$('<input type="radio" name=' + options.field + ' value="Fixed Amount" checked="true" /> Fixed Amount <br> <input type="radio" name=' + options.field + ' value="Amount On Enhanced Load"/> Amount&nbsp;On&nbsp;Enhanced&nbsp;Load <br>')
		.appendTo(container);
}

var sancationedLoadName = "";
var sancationedLoad;


function refundTypeEditor(container, options) {
	$('<input type="radio" name=' + options.field + ' value="Refund Fixed Amount" checked="true" /> Refund Fixed Amount <br> <input type="radio" name=' + options.field + ' value="Refund Amount On Enhanced Load"/> Refund&nbspAmount&nbsp;On&nbsp;Enhanced&nbsp;Load <br>')
		.appendTo(container);
}

$(document).on('change','input[name="refundType"]:radio',function() {

	var radioValue = $('input[name=refundType]:checked').val();
	if (radioValue == 'Refund Fixed Amount') {
		
		$('div[data-container-for="refundAmount"]').show();
		$('label[for="refundAmount"]').closest('.k-edit-label').show();
		
		$('div[data-container-for="sancationedLoadRefund"]').hide();
		$('label[for="sancationedLoadRefund"]').closest('.k-edit-label').hide();
		
		$('div[data-container-for="newSancationedLoadRefund"]').hide();
		$('label[for="newSancationedLoadRefund"]').closest('.k-edit-label').hide();

	} else if(radioValue == 'Refund Amount On Enhanced Load'){
		
		$.ajax({
			type : "POST",
			url : "./deposits/getSanctionedLoadDetails/"+ SelectedAccountId,
			async: false,
			dataType : "JSON",
			success : function(response) {
				sancationedLoad=response.serviceParameterValue;
				sancationedLoadName=response.spmName;
				$('input[name="sancationedLoadRefund"]').val(sancationedLoad);
				
				$('label[for="sancationedLoadRefund"]').text(response.spmName);
			    $('label[for="newSancationedLoadRefund"]').text("New "+response.spmName);
			}
		});
		
		$('div[data-container-for="refundAmount"]').hide();
		$('label[for="refundAmount"]').closest('.k-edit-label').hide();
		
		$('div[data-container-for="sancationedLoadRefund"]').show();
		$('label[for="sancationedLoadRefund"]').closest('.k-edit-label').show();
		
		$('div[data-container-for="newSancationedLoadRefund"]').show();
		$('label[for="newSancationedLoadRefund"]').closest('.k-edit-label').show();
	}
});

var depositTypeStatus="";
function dataBoundDeposit(e) {

	var data = this.dataSource.view(), row;
	var grid = $("#depositGrid").data("kendoGrid");
	for (var i = 0; i < data.length; i++) {

		row = this.tbody.children("tr[data-uid='" + data[i].uid + "']");
		depositTypeStatus = data[i].depositType;
		var currentUid = data[i].uid;
		if(depositTypeStatus=="New"){
			var currenRow = grid.table.find("tr[data-uid='" + currentUid + "']");
			var editButton = $(currenRow).find(".k-grid-edit");
			editButton.hide();
		}

	}
}

var collectionTypeStatus="";
function dataBoundDepositLineItem(e) {

	var data = this.dataSource.view(), row;
	var grid = $("#depositDetails_"+SelectedRowId).data("kendoGrid");
	for (var i = 0; i < data.length; i++) {

		row = this.tbody.children("tr[data-uid='" + data[i].uid + "']");
		collectionTypeStatus = data[i].collectionType;
		var refundStatus = data[i].status;
		var currentUid = data[i].uid;
		
		if(refundStatus=="Refund" || balanceAmount<=0){
			var currenRow = grid.table.find("tr[data-uid='" + currentUid + "']");
			var editButton = $(currenRow).find(".k-grid-edit");
			editButton.hide();
			
			var refundAmountButton = $(currenRow).find(".k-grid-RefundAmount");
			refundAmountButton.hide();
			
			var deleteButton = $(currenRow).find(".k-grid-delete");
			deleteButton.hide();
		}
		
		if(collectionTypeStatus=="Counter"){
			var currenRow = grid.table.find("tr[data-uid='" + currentUid + "']");
			var editButton = $(currenRow).find(".k-grid-edit");
			editButton.hide();
		}

	}
}

function depositDetailsEvent(e)
{
	 $(".k-window").css({"top" : "100px"});
	 
	 $('input[name="sancationedLoad"]').prop("readonly",true);
	
	 $('div[data-container-for="ddId"]').remove();
	 $('label[for="ddId"]').closest('.k-edit-label').remove();  
	 
	 $('div[data-container-for="loadChangeUnits"]').remove();
	 $('label[for="loadChangeUnits"]').closest('.k-edit-label').remove();
	 
	 $('div[data-container-for="notes"]').remove();
	 $('label[for="notes"]').closest('.k-edit-label').remove();
	 
	 $('div[data-container-for="category"]').remove();
	 $('label[for="category"]').closest('.k-edit-label').remove(); 
	 
	 $('div[data-container-for="newSancationedLoad"]').hide();
	 $('label[for="newSancationedLoad"]').closest('.k-edit-label').hide();
	 
	 $('div[data-container-for="sancationedLoad"]').hide();
	 $('label[for="sancationedLoad"]').closest('.k-edit-label').hide();
	 
	 $('div[data-container-for="loadEnhanced"]').hide();
	 $('label[for="loadEnhanced"]').closest('.k-edit-label').hide();
	 
	 $('div[data-container-for="sancationedLoadName"]').hide();
	 $('label[for="sancationedLoadName"]').closest('.k-edit-label').hide();
	 
	 $('div[data-container-for="depositsId"]').remove();
	 $('label[for="depositsId"]').closest('.k-edit-label').remove(); 
	 
	 $('div[data-container-for="transactionName"]').remove();
	 $('label[for="transactionName"]').closest('.k-edit-label').remove();     
	 
	 $('div[data-container-for="collectionType"]').remove();
	 $('label[for="collectionType"]').closest('.k-edit-label').remove();
	 
	 $('div[data-container-for="instrumentNo"]').hide();
	 $('label[for="instrumentNo"]').closest('.k-edit-label').hide();
	 
	 $('div[data-container-for="refundDate"]').hide();
	 $('label[for="refundDate"]').closest('.k-edit-label').hide();
	 
	 $('div[data-container-for="status"]').hide();
	 $('label[for="status"]').closest('.k-edit-label').hide();
		
	 $('div[data-container-for="instrumentDate"]').hide();
	 $('label[for="instrumentDate"]').closest('.k-edit-label').hide();
	 
	 $('div[data-container-for="depositsId"]').hide();
	 $('label[for="depositsId"]').closest('.k-edit-label').hide();
		
	 $('div[data-container-for="bankName"]').hide();
	 $('label[for="bankName"]').closest('.k-edit-label').hide();
	 
		if (e.model.isNew()) 
	    {
			securityCheckForActions("./Accounts/Deposits/DepositLines/createButton");
			$(".k-window-title").text("Add New Calc Line Details");
			$(".k-grid-update").text("Save");
	    }
		else
		{
			securityCheckForActions("./Accounts/Deposits/DepositLines/updateButton");
			$(".k-window-title").text("Edit Calc Line Details");
		}
		
		$(".k-grid-update").click(function() {
			e.model.set("sancationedLoad", sancationedLoad);
			e.model.set("sancationedLoadName", sancationedLoadName);
		  });
		
		
		
		$(document).on('change','input[name="loadEnhanced"]:radio',function() {

			var radioValue = $('input[name=loadEnhanced]:checked').val();
			if (radioValue == 'Fixed Amount') {
				
				$('div[data-container-for="amount"]').show();
				$('label[for="amount"]').closest('.k-edit-label').show();
				
				$('div[data-container-for="sancationedLoad"]').hide();
				$('label[for="sancationedLoad"]').closest('.k-edit-label').hide();
				
				$('div[data-container-for="newSancationedLoad"]').hide();
				$('label[for="newSancationedLoad"]').closest('.k-edit-label').hide();

			} else if(radioValue == 'Amount On Enhanced Load'){
				
				$.ajax({
					type : "POST",
					url : "./deposits/getSanctionedLoadDetails/"+ SelectedAccountId,
					async: false,
					dataType : "JSON",
					success : function(response) {
						sancationedLoad=response.serviceParameterValue;
						sancationedLoadName=response.spmName;
						$('input[name="sancationedLoad"]').val(sancationedLoad);
						
						$('label[for="sancationedLoad"]').text(response.spmName);
					    $('label[for="newSancationedLoad"]').text("New "+response.spmName);
					}
				});
				
				$('div[data-container-for="amount"]').hide();
				$('label[for="amount"]').closest('.k-edit-label').hide();
				
				$('div[data-container-for="sancationedLoad"]').show();
				$('label[for="sancationedLoad"]').closest('.k-edit-label').show();
				
				$('div[data-container-for="newSancationedLoad"]').show();
				$('label[for="newSancationedLoad"]').closest('.k-edit-label').show();
				
				var newSactionedLoadMin = $('input[name="newSancationedLoad"]').kendoNumericTextBox().data("kendoNumericTextBox");
				newSactionedLoadMin.min(parseFloat(sancationedLoad)+1);
			}
		});
	}
	
function depositRefundEvent(e)
{
	 $(".k-window").css({"top" : "100px"});
	 
	 $('input[name="sancationedLoadRefund"]').prop("readonly",true);
	
	 $('div[data-container-for="refundId"]').remove();
	 $('label[for="refundId"]').closest('.k-edit-label').remove();  
	 
	 $('div[data-container-for="loadChangeUnits"]').remove();
	 $('label[for="loadChangeUnits"]').closest('.k-edit-label').remove();
	 
	 $('div[data-container-for="notes"]').remove();
	 $('label[for="notes"]').closest('.k-edit-label').remove();
	 
	 $('div[data-container-for="category"]').remove();
	 $('label[for="category"]').closest('.k-edit-label').remove(); 
	 
	 $('div[data-container-for="newSancationedLoadRefund"]').hide();
	 $('label[for="newSancationedLoadRefund"]').closest('.k-edit-label').hide();
	 
	 $('div[data-container-for="sancationedLoadRefund"]').hide();
	 $('label[for="sancationedLoadRefund"]').closest('.k-edit-label').hide();
	 
	 $('div[data-container-for="loadEnhanced"]').hide();
	 $('label[for="loadEnhanced"]').closest('.k-edit-label').hide();
	 
	 $('div[data-container-for="sancationedLoadName"]').hide();
	 $('label[for="sancationedLoadName"]').closest('.k-edit-label').hide();
	 
	 $('div[data-container-for="depositsId"]').remove();
	 $('label[for="depositsId"]').closest('.k-edit-label').remove(); 
	 
	 $('div[data-container-for="transactionName"]').remove();
	 $('label[for="transactionName"]').closest('.k-edit-label').remove();     
	 
	 $('div[data-container-for="collectionType"]').remove();
	 $('label[for="collectionType"]').closest('.k-edit-label').remove();
	 
	 $('div[data-container-for="instrumentNo"]').hide();
	 $('label[for="instrumentNo"]').closest('.k-edit-label').hide();
	 
	 $('div[data-container-for="refundDate"]').hide();
	 $('label[for="refundDate"]').closest('.k-edit-label').hide();
	 
	 $('div[data-container-for="status"]').hide();
	 $('label[for="status"]').closest('.k-edit-label').hide();
		
	 $('div[data-container-for="instrumentDate"]').hide();
	 $('label[for="instrumentDate"]').closest('.k-edit-label').hide();
	 
	 $('div[data-container-for="depositsId"]').hide();
	 $('label[for="depositsId"]').closest('.k-edit-label').hide();
		
	 $('div[data-container-for="bankName"]').hide();
	 $('label[for="bankName"]').closest('.k-edit-label').hide();
	 
		if (e.model.isNew()) 
	    {
			securityCheckForActions("./Accounts/Deposits/RefundPayments/createButton");
			$(".k-window-title").text("Add New Refund Details");
			$(".k-grid-update").text("Save");
	    }
		else
		{
			//securityCheckForActions("./Accounts/Deposits/DepositLines/updateButton");
			$(".k-window-title").text("Edit Calc Line Details");
		}
		
		$(".k-grid-update").click(function() {
			e.model.set("sancationedLoadRefund", sancationedLoad);
			e.model.set("sancationedLoadName", sancationedLoadName);
		  });
		
		$(document).on('change','input[name="refundType"]:radio',function() {

			var radioValue = $('input[name=refundType]:checked').val();
			if (radioValue == 'Refund Fixed Amount') {
				
				$('div[data-container-for="refundAmount"]').show();
				$('label[for="refundAmount"]').closest('.k-edit-label').show();
				
				$('div[data-container-for="sancationedLoadRefund"]').hide();
				$('label[for="sancationedLoadRefund"]').closest('.k-edit-label').hide();
				
				$('div[data-container-for="newSancationedLoadRefund"]').hide();
				$('label[for="newSancationedLoadRefund"]').closest('.k-edit-label').hide();
				
				var refundAmountMax = $('input[name="refundAmount"]').kendoNumericTextBox().data("kendoNumericTextBox");
				refundAmountMax.max(parseFloat(balanceAmount));

			} else if(radioValue == 'Refund Amount On Enhanced Load'){
				
				$.ajax({
					type : "POST",
					url : "./deposits/getSanctionedLoadDetails/"+ SelectedAccountId,
					async: false,
					dataType : "JSON",
					success : function(response) {
						sancationedLoad=response.serviceParameterValue;
						sancationedLoadName=response.spmName;
						$('input[name="sancationedLoadRefund"]').val(sancationedLoad);
						
						$('label[for="sancationedLoadRefund"]').text(response.spmName);
					    $('label[for="newSancationedLoadRefund"]').text("New "+response.spmName);
					}
				});
				
				$('div[data-container-for="refundAmount"]').hide();
				$('label[for="refundAmount"]').closest('.k-edit-label').hide();
				
				$('div[data-container-for="sancationedLoadRefund"]').show();
				$('label[for="sancationedLoadRefund"]').closest('.k-edit-label').show();
				
				$('div[data-container-for="newSancationedLoadRefund"]').show();
				$('label[for="newSancationedLoadRefund"]').closest('.k-edit-label').show();
				
				var newSactionedLoadMax = $('input[name="newSancationedLoadRefund"]').kendoNumericTextBox().data("kendoNumericTextBox");
				newSactionedLoadMax.max(parseFloat(sancationedLoad)-1);
			}
		});
		
		var refundAmountMax = $('input[name="refundAmount"]').kendoNumericTextBox().data("kendoNumericTextBox");
		refundAmountMax.max(parseFloat(balanceAmount));
	}
	
function dropDownChecksEditorPaymentMode(container, options) {
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
				select : selectModeType,
				dataSource : {
					transport : {
						read : "${allChecksUrl}/"+attribute,
					}
				}
			});
	 $('<span class="k-invalid-msg" data-for="'+attribute+'"></span>').appendTo(container);
}

var selectedPaymentMode="";
function selectModeType(e){
	
	var dataItem = this.dataItem(e.item.index());
	selectedPaymentMode=dataItem.text;
	if(selectedPaymentMode!="Cash"){
		$('label[for="instrumentDate"]').text(dataItem.text+" Date *");
	    $('label[for="instrumentNo"]').text(dataItem.text+" Number *");
	    
	    $('div[data-container-for="instrumentNo"]').show();
		$('label[for="instrumentNo"]').closest('.k-edit-label').show();
		
		$('div[data-container-for="instrumentDate"]').show();
		$('label[for="instrumentDate"]').closest('.k-edit-label').show();
		
		$('div[data-container-for="bankName"]').show();
		$('label[for="bankName"]').closest('.k-edit-label').show();
	}else{
		$('div[data-container-for="instrumentNo"]').hide();
		$('label[for="instrumentNo"]').closest('.k-edit-label').hide();
		
		$('div[data-container-for="instrumentDate"]').hide();
		$('label[for="instrumentDate"]').closest('.k-edit-label').hide();
		
		$('div[data-container-for="bankName"]').hide();
		$('label[for="bankName"]').closest('.k-edit-label').hide();
	}
    
}

function dropDownChecksEditorPaymentThrough(container, options) {
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
				select : selectPayTroughType,
				dataSource : {
					transport : {
						read : "${allChecksUrl}/"+attribute,
					}
				}
			});
	 $('<span class="k-invalid-msg" data-for="'+attribute+'"></span>').appendTo(container);
}

var selectedPaymentThrough="";
function selectPayTroughType(e){
	
	var dataItem = this.dataItem(e.item.index());
	selectedPaymentThrough=dataItem.text;
	if(selectedPaymentThrough!="By Adjustment"){
		$('label[for="instrumentDate"]').text(dataItem.text+" Date *");
	    $('label[for="instrumentNo"]').text(dataItem.text+" Number *");
	    
	    $('div[data-container-for="instrumentNo"]').show();
		$('label[for="instrumentNo"]').closest('.k-edit-label').show();
		
		$('div[data-container-for="instrumentDate"]').show();
		$('label[for="instrumentDate"]').closest('.k-edit-label').show();
		
		$('div[data-container-for="bankName"]').show();
		$('label[for="bankName"]').closest('.k-edit-label').show();
	}else{
		$('div[data-container-for="instrumentNo"]').hide();
		$('label[for="instrumentNo"]').closest('.k-edit-label').hide();
		
		$('div[data-container-for="instrumentDate"]').hide();
		$('label[for="instrumentDate"]').closest('.k-edit-label').hide();
		
		$('div[data-container-for="bankName"]').hide();
		$('label[for="bankName"]').closest('.k-edit-label').hide();
	}
    
}

function bankNames(container, options) 
{
		$('<input name="bankName" id="bankName" data-text-field="bankName" data-value-field="bankName" data-bind="value:' + options.field + '"/>')
		.appendTo(container).kendoComboBox({
		 autoBind : false,
		 placeholder : "Select Bank",
		 dataSource : {
		  transport : {  
		   read :  "${getAllBankNames}"
		  }
		 }
		});
		
		$('<span class="k-invalid-msg" data-for="bankName"></span>').appendTo(container);
}

function paymentStatusUpdate()
{
	var paymentCollectionId="";
	var gridParameter = $("#depositGrid").data("kendoGrid");
	var selectedAddressItem = gridParameter.dataItem(gridParameter.select());
	paymentCollectionId = selectedAddressItem.paymentCollectionId;
	$.ajax
	({			
		type : "POST",
		url : "./billingPayments/paymentStatusUpdate/"+paymentCollectionId,
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
			$('#depositGrid').data('kendoGrid').dataSource.read();
		}
	});
}

function paymentSegmentParse(response) {
    $.each(response, function (idx, elem) {   
    	   if(elem.postedLedgerDate== null){
    		   elem.postedLedgerDate = "";
    	   }else{
    		   elem.postedLedgerDate = kendo.parseDate(new Date(elem.postedLedgerDate),'dd/MM/yyyy HH:mm');
    	   }
       });
       return response;
}

function dropDownChecksEditor(container, options) {
	var res = (container.selector).split("=");
	var attribute = res[1].substring(0,res[1].length-1);
	
	$('<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '" name = "typeOfServiceDeposit" id = "typeOfServiceDeposit"/>')
			.appendTo(container).kendoDropDownList({
				optionLabel : {
					text : "Select",
					value : "",
				},
				defaultValue : false,
				sortable : true,
				change : selectedServiceType,
				dataSource : {
					transport : {
						read : "${allChecksUrl}/"+attribute,
					}
				}
			});
	 $('<span class="k-invalid-msg" data-for="'+attribute+'"></span>').appendTo(container);
}

var selectedService="";
function selectedServiceType(e){
	var dataItem = this.dataItem(e.item);
	  selectedService=dataItem.text;
	  var comboBoxDataSource = new kendo.data.DataSource({
          transport: {
              read: {
                  url     : "./deposits/getTransactionCodes/"+selectedService,
                  dataType: "json",
                  type    : 'GET'
              }
          },
	           
	 });
	        
   $("#transactionCode").kendoDropDownList({
      dataSource    : comboBoxDataSource,
      optionLabel : "Select",
      placeholder: "Select Transaction",
      dataTextField : "transactionName",
      dataValueField: "transactionCode",
      select : selectedTransaction,
  });
   $("#transactionCode").data("kendoDropDownList").value("");
}

function transactionCodeEditor(container, options) {
	$('<input name="transactionCode" data-text-field="transactionName" id="transactionCode" data-value-field="transactionCode" validationmessage="Transaction code is required" data-bind="value:' + options.field + '" required="required"/>')
			.appendTo(container).kendoDropDownList({
				autoBind: false,
				placeholder: "Select Transaction Code",
           	 	/* cascadeFrom: "typeOfService",  */
           	 	optionLabel : "Select",
           	 	select : selectedTransaction,
				dataSource : {
					transport : {
						read : "${transactionCodeUrl}",
					}
				}
				
			});
	 $('<span class="k-invalid-msg" data-for="transactionCode"></span>').appendTo(container); 
}

function selectedTransaction(e){
	var dataItem = this.dataItem(e.item.index());
	if(dataItem.transactionCode=="EL_LOAD_ENHANCED"){
		 $('div[data-container-for="loadEnhanced"]').show();
		 $('label[for="loadEnhanced"]').closest('.k-edit-label').show();
	}else{
		 $('div[data-container-for="loadEnhanced"]').hide();
		 $('label[for="loadEnhanced"]').closest('.k-edit-label').hide();	
		 
		 $('div[data-container-for="amount"]').show();
		 $('label[for="amount"]').closest('.k-edit-label').show();
	} 
}

function clearFilterPayments()
{
   $("form.k-filter-menu button[type='reset']").slice().trigger("click");
   var gridStoreIssue = $("#depositGrid").data("kendoGrid");
   gridStoreIssue.dataSource.read();
   gridStoreIssue.refresh();
}

function paymetsDeleteEvent(){
	var conf = confirm("Are u sure want to delete this payment details?");
	 if(!conf){
	  $('#depositGrid').data().kendoGrid.dataSource.read();
	   throw new Error('deletion aborted');
	 }
}

function accountNumberEditor(container, options) {
	  $('<input name="accountId" id="accountId" data-text-field="accountNo" data-value-field="accountId" data-bind="value:' + options.field + '"/>')
	     .appendTo(container)
	     .kendoComboBox({
			dataType: 'JSON',
			placeholder: "Enter Account Number",
			headerTemplate : '<div class="dropdown-header">'
				+ '<span class="k-widget k-header">Photo</span>'
				+ '<span class="k-widget k-header">Contact info</span>'
				+ '</div>',
			template : '<table><tr><td rowspan=2><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.personId#'/>" width=40 height=55 alt=\"No Image to Display\" /></span></td>'
				+ '<td align="left"><span class="k-state-default"><b>#: data.personName #</b></span><br>'
				+ '<span class="k-state-default"><i>#: data.accountNo #</i></span><br>'
				+ '<span class="k-state-default"><i>#: data.propertyNo #</i></span><br>'
				+ '<span class="k-state-default"><i>#: data.personType #</i></span></td></tr></table>',
			dataSource: {
				transport: {
					read: "${accountNumberAutocomplete}"
				}
			},
			change : function (e) {
	            if (this.value() && this.selectedIndex == -1) {                    
					alert("Account doesn't exist!");
	                $("#accountId").data("kendoComboBox").value("");
	        	}
		    }
		});
	  $('<span class="k-invalid-msg" data-for="accountId"></span>').appendTo(container);
	}

$("#depositGrid").on("click", "#temPID", function(e) {
	  
	  var button = $(this), enable = button.text() == "Approved";
	  var widget = $("#depositGrid").data("kendoGrid");
	  var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
						if (enable) 
						{
							$.ajax
							({
								type : "POST",
								url : "./deposits/setDepositsStatus/" +dataItem.id+ "/activate",
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
									button.text('Posted');
									$('#depositGrid').data('kendoGrid').dataSource.read();
								}
							});
						} 
						else 
						{
							$.ajax
							({
								type : "POST",
								url : "./deposits/setDepositsStatus/" + dataItem.id + "/deactivate",
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
									button.text('Posted');
									$('#depositGrid').data('kendoGrid').dataSource.read();
								}
							});
						}
 });

var setApCode="";	
var flagAccountId="";
function depositsEvent(e)
{
	
	/***************************  to remove the id from pop up  **********************/
	$('div[data-container-for="depositsId"]').remove();
	$('label[for="depositsId"]').closest('.k-edit-label').remove();
	
	$(".k-edit-field").each(function () {
		$(this).find("#temPID").parent().remove();  
   	});
   	
   	$('div[data-container-for="personName"]').remove();
	$('label[for="personName"]').closest('.k-edit-label').remove();
   	
   	$('div[data-container-for="refundAmount"]').hide();
	$('label[for="refundAmount"]').closest('.k-edit-label').hide();
	
	$('div[data-container-for="balance"]').hide();
	$('label[for="balance"]').closest('.k-edit-label').hide();
	
   	$('div[data-container-for="accountNo"]').hide();
	$('label[for="accountNo"]').closest('.k-edit-label').hide();
	
	$('div[data-container-for="propertyNo"]').hide();
	$('label[for="propertyNo"]').closest('.k-edit-label').hide();
	
	$('div[data-container-for="depositType"]').hide();
	$('label[for="depositType"]').closest('.k-edit-label').hide();
	
	$('div[data-container-for="totalAmount"]').hide();
	$('label[for="totalAmount"]').closest('.k-edit-label').hide();

	$('div[data-container-for="status"]').remove();
	$('label[for="status"]').closest('.k-edit-label').remove();
	
	/************************* Button Alerts *************************/
	if (e.model.isNew()) 
    {
		flagAccountId = true;
		securityCheckForActions("./Accounts/Deposits/createButton");
		$(".k-window-title").text("Add New Deposits Details");
		$(".k-grid-update").text("Save");	
    }
	else
	{
		$(".k-window-title").text("Edit Deposits Details");
	}
}
/* 
$("#depositGrid").on("click", ".k-grid-approveAdjustment", function(e) {

	$.ajax
	({
		type : "POST",
		url : "./paymentAdjustments/approveAdjustment",
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
			$('#depositGrid').data('kendoGrid').dataSource.read();
		}
	});
}); */
/* 
$("#depositGrid").on("click", ".k-grid-postAdjustment", function(e) {

	$.ajax
	({
		type : "POST",
		url : "./paymentAdjustments/postAdjustmentLedger",
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
			$('#depositGrid').data('kendoGrid').dataSource.read();
		}
	});
}); */
	

	function OnRequestSatrt(e){
		$('.k-grid-update').hide();
	    $('.k-edit-buttons')
	            .append(
	                    '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
	    $('.k-grid-cancel').hide();
    }
    function calcLineOnRequestStart(e){
    	$('.k-grid-update').hide();
	    $('.k-edit-buttons')
	            .append(
	                    '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
	    $('.k-grid-cancel').hide();	
    }
    function depositRefundOnRequestStart(e){
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
							"Error: Creating the adjustment details \n\n : "
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
				var grid = $("#depositGrid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			} 
			else if (e.type == "create") {
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Deposit details is created successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});

				var grid = $("#depositGrid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			} else if (e.type == "destroy") {
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Deposit details is deleted successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});

				var grid = $("#depositGrid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			} else if (e.type == "update") {
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Deposit details is updated successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});

				var grid = $("#depositGrid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			}
		}
	}
	function calcLineOnRequestEnd(e)
	 {
		  if (typeof e.response != 'undefined')
			{
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

					$('#calcLines_'+SelectedRowId).data().kendoGrid.dataSource.read({personId:SelectedAccessCardId});
					return false;
				}
			

		  else if (e.type == "create") 
			{
				$("#alertsBox").html("");
				$("#alertsBox").html("Deposit line item created successfully");
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
				var gridPets = $("#depositGrid").data("kendoGrid");
				gridPets.dataSource.read();
				gridPets.refresh();
				return false;
			}

			else if (e.type == "update") 
			{
				$("#alertsBox").html("");
				$("#alertsBox").html("Deposit line item updated successfully");
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
				var gridPets = $("#depositGrid").data("kendoGrid");
				gridPets.dataSource.read();
				gridPets.refresh();
				return false;
			}
				
			else if (e.type == "destroy") 
			{
				$("#alertsBox").html("");
				$("#alertsBox").html("Deposit line item deleted successfully");
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
	
	function depositRefundOnRequestEnd(e)
	 {
		  if (typeof e.response != 'undefined')
			{
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

					$('#calcLines_'+SelectedRowId).data().kendoGrid.dataSource.read({personId:SelectedAccessCardId});
					return false;
				}
			

		  else if (e.type == "create") 
			{
				$("#alertsBox").html("");
				$("#alertsBox").html("Refund details created successfully");
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
				var gridPets = $("#depositGrid").data("kendoGrid");
				gridPets.dataSource.read();
				gridPets.refresh();
				return false;
			}

			else if (e.type == "update") 
			{
				$("#alertsBox").html("");
				$("#alertsBox").html("Deposit line item updated successfully");
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
				var gridPets = $("#depositGrid").data("kendoGrid");
				gridPets.dataSource.read();
				gridPets.refresh();
				return false;
			}
				
			else if (e.type == "destroy") 
			{
				$("#alertsBox").html("");
				$("#alertsBox").html("Deposit line item deleted successfully");
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
	
	//register custom validation rules

	var paymentModeFlag = true;
	var sancationedLoadRefund="";
	
	(function ($, kendo) 
		{   	  
	    $.extend(true, kendo.ui.validator, 
	    {
	         rules: 
	         { // custom rules
	        	 
								typeOfServiceRequired : function(input, params) {
									if (input.attr("name") == "typeOfServiceDeposit") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},
								/* amountRequired : function(input, params){
								      if (input.attr("name") == "amount")
								          {
								           return $.trim(input.val()) !== "";
								          }
								       return true;
								      }, */
								paymentModeRequired : function(input, params) {
									if (input.attr("name") == "paymentMode") {
										if (input.val() == "Cash") {
											paymentModeFlag = false;
										}else{
											paymentModeFlag = true;
										}
										return $.trim(input.val()) !== "";
									}
									return true;
								},

								instrumentDateRequired : function(input, params) {
									if (paymentModeFlag) {
										if (input.attr("name") == "instrumentDate") {
											return $.trim(input.val()) !== "";
										}
									}
									return true;
								},
								instrumentNoRequired : function(input, params) {
									if (paymentModeFlag) {
										if (input.attr("name") == "instrumentNo") {
											return $.trim(input.val()) !== "";
										}
									}
									return true;
								},
								bankNameRequired : function(input, params) {
									if (paymentModeFlag) {
										if (input.attr("name") == "bankName") {
											return $.trim(input.val()) !== "";
										}
									}
									return true;
								},
								instrumentDateValidation : function(input,
										params) {
									if (input
											.filter("[name = 'instrumentDate']").length
											&& input.val()) {
										var selectedDate = input.val();
										var todaysDate = $.datepicker
												.formatDate('dd/mm/yy',
														new Date());
										var flagDate = false;

										if ($.datepicker.parseDate('dd/mm/yy',
												selectedDate) <= $.datepicker
												.parseDate('dd/mm/yy',
														todaysDate)) {
											flagDate = true;
										}
										return flagDate;
									}
									return true;
								},
								accountNoRequired : function(input, params) {
									if (input.attr("name") == "accountId") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},
								accountIdUniquevalidation : function(input,
										params) {
									if (input.filter("[name='accountId']").length
											&& input.val()) {
										var flag = true;
										$.each(accountIdArray, function(idx1,
												elem1) {
											if (elem1 == input.val()) {
												flag = false;
											}
										});
										return flag;
									}
									return true;
								},
								/* refundAmountRequired : function(input, params) {
									if (refundAmountFlag) {
									if (input.attr("name") == "refundAmount") {
										return $.trim(input.val()) !== "";
										}
									}
									return true;
								}, */
								paymentThroughRequired : function(input, params) {
									if (input.attr("name") == "paymentThrough") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},
								/* sancationedLoadRefundRequired : function(input, params) {
									if (input.attr("name") == "sancationedLoadRefund") {
										sancationedLoadRefund=input.val();
										alert(sancationedLoadRefund);
									}
									return true;
								},
								newSancationedLoadRefundRequired : function(input, params) {
									if (input.attr("name") == "newSancationedLoadRefund") {
										if(sancationedLoadRefund<=input.val()){
											return false;
										}else{
											return true;
										}
									}
									return true;
								}, */
								instrumentNoLengthValidation : function(input, params) {
									if (paymentModeFlag) {
										if (input.filter("[name='instrumentNo']").length && input.val() != "") 
							              {
							             	 return /^[0-9]{6,15}$/.test(input.val());
							              }        
									}
									return true;
								},
							},
							messages : {
								//custom rules messages
								typeOfServiceRequired : "Service type is required",
								/* amountRequired : "Amount is required", */
								paymentModeRequired : "Payment mode is required",
								instrumentDateRequired : "Instrument date is required",
								instrumentNoRequired : "Instrument number is required",
								bankNameRequired : "Bank name is required",
								instrumentDateValidation : "Instrument date must be past date",
								accountNoRequired : "Account number is required",
								accountIdUniquevalidation : "This account number already exists",
								/* refundAmountRequired : "Refund amount is required" */
								paymentThroughRequired : "Payment through is required",
								/* sancationedLoadRefundRequired : "",
								newSancationedLoadRefundRequired : "New sanction load must be less than the sanctioned load" */
								instrumentNoLengthValidation : "Instrument number allows only numbers min 6 and max 15"
							}
						});

	})(jQuery, kendo);
	//End Of Validation
</script>
