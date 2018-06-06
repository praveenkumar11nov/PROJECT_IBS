<%@include file="/common/taglibs.jsp"%>

<!-- Create Read Update Delete URL's Of Service Master -->
<c:url value="/serviceMasters/ServiceMasterCreate" var="serviceMasterCreateUrl" />
<c:url value="/serviceMasters/ServiceMasterRead" var="serviceMasterReadUrl" />
<c:url value="/serviceMasters/ServiceMasterUpdate" var="serviceMasterUpdateUrl" />
<c:url value="/serviceMasters/ServiceMasterDestroy" var="serviceMasterDestroyUrl" />

<!-- Filter URL's Of Service Master -->
<c:url value="/serviceMasters/filter" var="commonFilterForServiceMasterUrl" />
<c:url value="/serviceMasters/getPersonListBasedOnAccountIDForFileter" var="personNamesFilterUrl" />

<!-- Create Read Update Delete URL's Of Service Master Parameter -->
<c:url value="/serviceMasters/parameterCreate" var="parameterCreateUrl" />
<c:url value="/serviceMasters/parameterRead" var="parameterReadUrl" />
<c:url value="/serviceMasters/parameterUpdate" var="parameterUpdateUrl" />
<c:url value="/serviceMasters/parameterDestroy" var="parameterDestroyUrl" />

<!-- Filter URL's Of Service Parameter -->
<c:url value="/serviceParameters/filter" var="commonFilterForServiceParameterUrl" />

<!-- Create Read Update Delete URL's Of Service Master Account-->
<c:url value="/serviceMasters/serviceAccountCreate" var="serviceAccountCreateUrl" />
<c:url value="/serviceMasters/serviceAccountReadUrl" var="serviceAccountReadUrl" />
<c:url value="/serviceMasters/serviceAccountUpdate" var="serviceAccountUpdateUrl" />
<c:url value="/serviceMasters/serviceAccountDestroy" var="serviceAccountDestroyUrl" />

<!-- Filter URL's Of Service Account -->
<c:url value="/serviceAccountss/filter" var="commonFilterForServiceAccountUrl" />

<c:url value="/common/getAllChecks" var="allChecksUrl" />
<c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />

<c:url value="/account/categories" var="categoriesReadUrlAccount" />
<c:url value="/serviceMasters/getPersonListBasedOnAccountNumbers" var="personNamesAutoBasedOnPersonTypeUrl" />
<c:url value="/serviceMasters/readAccountNumbers" var="readAccountNumbers" />
<c:url value="/serviceMasters/tariffList" var="tariffList" />
<c:url value="/serviceMasters/serviceParameterList" var="serviceParameterList" />
<c:url value="/serviceMasters/meterHistoryRead" var="meterHistoryReadUrl" />

<kendo:grid name="grid" remove="serviceMasterDeleteEvent" resizable="true" pageable="true" selectable="true" change="onChangeServiceMaster" edit="serviceMasterEvent" detailTemplate="serviceMasterParameterTemplate" sortable="true" scrollable="true" 
		groupable="true" dataBound="serviceMasterDataBound">
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10"></kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
			<kendo:grid-filterable-operators>
				<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains"/>
				<kendo:grid-filterable-operators-date gt="Is after" lt="Is before"/>
			</kendo:grid-filterable-operators>

		</kendo:grid-filterable>
		<kendo:grid-editable mode="popup" />
		<kendo:grid-toolbar>
			<%-- <kendo:grid-toolbarItem name="create" text="Add Service Master" /> --%>
			<kendo:grid-toolbarItem name="generateBill" text="Calculate Average" />
			<kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterServiceMasters()><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>"/>
		</kendo:grid-toolbar>		
		<kendo:grid-columns>
			
			<kendo:grid-column title="ServiceMasterId" field="serviceMasterId" width="110px" hidden="true"/>
			
			<kendo:grid-column title="Service&nbsp;Type&nbsp;*"  field="typeOfService" filterable="true" width="100px" editor="dropDownChecksEditor">
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
			
			<kendo:grid-column title="Tariff&nbsp;Master&nbsp;*" field="tariffName" width="90px" filterable="false">
            </kendo:grid-column>
			
			<kendo:grid-column title="Tariff&nbsp;Master&nbsp;*" field="elTariffID" width="100px" hidden="true" editor="tariffEditor" filterable="false">
                 <%-- <kendo:grid-column-values value="${TariffMaster}"/> --%>
            <kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function apCodeFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Code",
									dataSource : {
										transport : {
											read : "${commonFilterForServiceMasterUrl}/tariffName"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
            </kendo:grid-column>
            
            <kendo:grid-column title="TOD"  field="todApplicable" filterable="true" width="50px" editor="dropDownChecksTODEditor">
			<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function apCodeFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Tod",
									dataSource : {
										transport : {
											read : "${commonFilterForServiceMasterUrl}/todApplicable"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="Person&nbsp;Name&nbsp;*" field="personName"  width="110px" >
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
	    	
	    	 <kendo:grid-column title="Person&nbsp;Name&nbsp;*" field="personId" editor="PersonNames" filterable="false" width="0px" hidden="true" >
	    	</kendo:grid-column>
	    	
	    	<kendo:grid-column title="Property&nbsp;No" field="property_No" filterable="true" width="85px">
	      	</kendo:grid-column>
			
			<kendo:grid-column title="Account Number&nbsp;*" field="accountId" editor="AccountNumbers" hidden="true" filterable="true" width="0px">
	    	</kendo:grid-column>
	    		
	        <kendo:grid-column title="Account No&nbsp;*" field="accountNo" filterable="true" width="95px">
	        <kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function accountNumberFilter(element) 
						   	{
								element.kendoAutoComplete({
									autoBind : false,
									dataTextField : "accountNo",
									dataValueField : "accountNo", 
									placeholder : "Enter account",
									headerTemplate : '<div class="dropdown-header">'
										+ '<span class="k-widget k-header">Accout Type</span>'
										+ '<span class="k-widget k-header">Account Number</span>'
										+ '</div>',
									template : '<table><tr><td align="left"><span class="k-state-default"><b>#: data.accountType #</b></span><br>'
										+ '<span class="k-state-default"><i>#: data.accountNo #</i></span></td></tr></table>',
									dataSource : {
										transport : {		
											read :  "${readAccountNumbers}"
										}
									} 
								});
						   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
	    	</kendo:grid-column>

			
			<%-- <kendo:grid-column title="Name" field="personName" width="80px">
			</kendo:grid-column>
			
			<kendo:grid-column title="personId" field="personId" editor="personEditorAccount" width="0px" hidden="true">
            </kendo:grid-column> --%>
						
			<kendo:grid-column title="Service&nbsp;Start&nbsp;Date&nbsp;*" field="serviceStartDate" format="{0:dd/MM/yyyy}" width="130px">
			</kendo:grid-column>
			
			<kendo:grid-column title="Service&nbsp;End&nbsp;Date&nbsp;*" field="serviceEndDate" format="{0:dd/MM/yyyy}" width="130px">
			</kendo:grid-column>	
								
			<kendo:grid-column title="Status" field="status" width="60px" >
				<kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function ledgerStatusFilter(element) {
								element
										.kendoDropDownList({
											optionLabel : "Select status",
											dataSource : {
												transport : {
													read : "${filterDropDownUrl}/servicePointsStatus"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column> 
			
			<%-- <kendo:grid-column title="Created&nbsp;By" field="createdBy" width="100px">
			<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function apCodeFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Created Name",
									dataSource : {
										transport : {
											read : "${commonFilterForServiceMasterUrl}/createdBy"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column> --%>
			
			 <kendo:grid-column title="&nbsp;" width="160px" >
							            	<kendo:grid-column-command>
							            		<kendo:grid-column-commandItem name="edit"/>
							            		<kendo:grid-column-commandItem name="destroy"/>
							            	</kendo:grid-column-command>
							            </kendo:grid-column>
							            
	        <kendo:grid-column title="&nbsp;" width="100px">
				<kendo:grid-column-command >
					 <kendo:grid-column-commandItem name="ServiceEnd" click="ServiceEndDateClick" />
				</kendo:grid-column-command>
		    </kendo:grid-column>						            
			
			<kendo:grid-column title=""
				template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Active#=data.serviceMasterId#'>#= data.status == 'Active' ? 'Inactivate' : 'Activate' #</a>"
				width="80px" />
				
		</kendo:grid-columns>
		
		<kendo:dataSource pageSize="20" requestEnd="onRequestEnd" requestStart="onRequestStart">
			<kendo:dataSource-transport>
			<kendo:dataSource-transport-create url="${serviceMasterCreateUrl}" dataType="json" type="GET" contentType="application/json" />
				<kendo:dataSource-transport-read url="${serviceMasterReadUrl}" dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-update url="${serviceMasterUpdateUrl}" dataType="json" type="GET" contentType="application/json" />
			    <kendo:dataSource-transport-destroy url="${serviceMasterDestroyUrl}" dataType="json" type="GET" contentType="application/json" />			
			</kendo:dataSource-transport>
			
			<kendo:dataSource-schema>
				<kendo:dataSource-schema-model id="serviceMasterId">
					<kendo:dataSource-schema-model-fields>

						<kendo:dataSource-schema-model-field name="serviceMasterId" type="number">
						</kendo:dataSource-schema-model-field>	
						
						<kendo:dataSource-schema-model-field name="accountId">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="personId">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="tariffName" type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="todApplicable" type="string" defaultValue="No">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="accountNo" type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="personName" type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="elTariffID" type="number" defaultValue="">
						</kendo:dataSource-schema-model-field>					
						
						<kendo:dataSource-schema-model-field name="serviceStartDate" type="date">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="serviceEndDate" type="date">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="typeOfService" type="string">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="createdBy">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="status"
							editable="true" type="string" />
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
			
		</kendo:dataSource>
	</kendo:grid> 
	
	<kendo:grid-detailTemplate id="serviceMasterParameterTemplate">
		<kendo:tabStrip name="tabStripg_#=serviceMasterId#">
		<kendo:tabStrip-animation>
			</kendo:tabStrip-animation>
			<kendo:tabStrip-items>
			
 			<kendo:tabStrip-item selected="true" text="Service Parameters">
                <kendo:tabStrip-item-content>
                    <div class='wethear' style="width: 91%">
						    <br/>
							<kendo:grid name="serviceMasterParameterEvent_#=serviceMasterId#" remove="serviceParameterDeleteEvent" pageable="true"
								resizable="true" sortable="true" reorderable="true" dataBound="onDataBoundParameterStatus"
								selectable="true" scrollable="true" edit="serviceMasterParameterEvent" editable="true" >
								<kendo:grid-pageable pageSize="10"></kendo:grid-pageable>
								<kendo:grid-filterable extra="false">
			                    <kendo:grid-filterable-operators>
				                    <kendo:grid-filterable-operators-string eq="Is equal to" />
			                    </kendo:grid-filterable-operators>
		                        </kendo:grid-filterable>
								<kendo:grid-editable mode="popup" />
						       <kendo:grid-toolbar >
						            <kendo:grid-toolbarItem name="create" text="Add New Parameter" />
						        </kendo:grid-toolbar> 
        						<kendo:grid-columns>
        						    <kendo:grid-column title="serviceParameterId" field="sertviceParameterId" hidden="true" width="100px">
									</kendo:grid-column> 
									
									<kendo:grid-column title="serviceMasterId" field="serviceMasterId" hidden="true" width="100px">
									</kendo:grid-column>
									
									<kendo:grid-column title="Parameter&nbsp;Sequence&nbsp;*" field="serviceParameterSequence" format="{0:n0}" filterable="false" width="140px">
									</kendo:grid-column>
									
									<kendo:grid-column title="Parameter&nbsp;Name&nbsp;*" field="spmName" width="120px" filterable="false">
									</kendo:grid-column>
									
									<kendo:grid-column title="Parameter&nbsp;Name&nbsp;*" field="spmId" editor="parameterNameEditor" hidden="true" width="120px">
									</kendo:grid-column>
									
									<kendo:grid-column title="Parameter&nbsp;Datatype&nbsp;*" field="serviceParameterDataType" width="130px" filterable="false" editor="dropDownChecksEditor">
									</kendo:grid-column> 
									
									<kendo:grid-column title="Parameter&nbsp;Value&nbsp;*" field="serviceParameterValue" filterable="false" width="120px">
									</kendo:grid-column> 
									
									<kendo:grid-column title="Status" field="status" width="100px" filterable="false" >
									</kendo:grid-column> 		 						
																		     
        								 <kendo:grid-column title="&nbsp;" width="140px" >
							            	<kendo:grid-column-command>
							            		<kendo:grid-column-commandItem name="edit"/>
							            		<kendo:grid-column-commandItem name="destroy"/>
							            	</kendo:grid-column-command>
							            </kendo:grid-column>
							            
							             <kendo:grid-column title="&nbsp;" width="75px" >
							             	<!-- Status updation purpose -->
							             </kendo:grid-column>
							            
							            <%-- <kendo:grid-column title="&nbsp;" width="110px">
										<kendo:grid-column-command >
											<kendo:grid-column-commandItem name="Change_Status" click="parameterStatusClick" />
										</kendo:grid-column-command>
								        </kendo:grid-column> --%>
							            
        						</kendo:grid-columns>
        						
        						  <kendo:dataSource requestEnd="serviceParameterOnRequestEnd" requestStart="serviceParameterOnRequestStart">
						            <kendo:dataSource-transport>
						            <kendo:dataSource-transport-read url="${parameterReadUrl}/#=serviceMasterId#" dataType="json" type="POST" contentType="application/json"/>
						            <kendo:dataSource-transport-create url="${parameterCreateUrl}/#=serviceMasterId#" dataType="json" type="GET" contentType="application/json" />
						            <kendo:dataSource-transport-update url="${parameterUpdateUrl}" dataType="json" type="GET" contentType="application/json" />
						            <kendo:dataSource-transport-destroy url="${parameterDestroyUrl}" dataType="json" type="GET" contentType="application/json" />
						            </kendo:dataSource-transport>
						            
						            <kendo:dataSource-schema>
						                <kendo:dataSource-schema-model id="serviceParameterId">
						                    <kendo:dataSource-schema-model-fields>
						                    
						                    <kendo:dataSource-schema-model-field name="serviceParameterId" type="number">
											<kendo:dataSource-schema-model-field-validation  />
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="serviceMasterId" type="number">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="spmName" type="string">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="serviceParameterSequence" type="number" defaultValue="">
											<kendo:dataSource-schema-model-field-validation min="1" />
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="spmId" defaultValue="0">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="serviceParameterDataType" type="string">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="serviceParameterValue" type="string">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="status" editable="true" type="string" />
																
											<kendo:dataSource-schema-model-field name="createdBy">
											</kendo:dataSource-schema-model-field>
						                    	
						                    </kendo:dataSource-schema-model-fields>
						                 </kendo:dataSource-schema-model>
						             </kendo:dataSource-schema>
						          </kendo:dataSource>
        				</kendo:grid>		
                    </div>
                </kendo:tabStrip-item-content>
            </kendo:tabStrip-item>
            
            <kendo:tabStrip-item selected="false" text="Service Account">
                <kendo:tabStrip-item-content>
                    <div class='wethear'>
						    <br/>
							<kendo:grid name="serviceAccoutEvent_#=serviceMasterId#" remove="serviceAccountDeleteEvent" pageable="true"
								resizable="true" sortable="true" reorderable="true" dataBound="onDataBoundServiceAccountStatus"
								selectable="true" scrollable="true" edit="serviceAccoutEvent" editable="true">
								<kendo:grid-pageable pageSize="10"></kendo:grid-pageable>
								<kendo:grid-filterable extra="false">
			                    <kendo:grid-filterable-operators>
				                    <kendo:grid-filterable-operators-string eq="Is equal to" />
			                    </kendo:grid-filterable-operators>
		                        </kendo:grid-filterable>
								<kendo:grid-editable mode="popup"/>
						       <kendo:grid-toolbar >
						            <kendo:grid-toolbarItem name="create" text="Add New Account" />
						        </kendo:grid-toolbar> 
        						<kendo:grid-columns>
        							<kendo:grid-column title="serviceAccoutId" field="serviceAccoutId" hidden="true" width="70px" />
        							
        							<kendo:grid-column title="serviceMasterId" field="serviceMasterId" hidden="true" width="70px" />
        							
        							<kendo:grid-column title="Account&nbsp;Number&nbsp;*" field="accountId" width="100px" filterable="false">
									<kendo:grid-column-values value="${accounts}"/>
									</kendo:grid-column>	
        																
									<kendo:grid-column title="Service&nbsp;Type&nbsp;*"  field="typeOfService" filterable="false" width="100px" editor="dropDownChecksForServiceAccountEditor">
			                        </kendo:grid-column> 
									
									<%-- <kendo:grid-column title="Service&nbsp;Reference&nbsp;Id&nbsp;*" field="serviceRefId" format="{0:n0}" width="145px">
									</kendo:grid-column> --%> 
									
									<kendo:grid-column title="Service&nbsp;Ledger&nbsp;*"  field="serviceLedger" filterable="false" width="110px" editor="dropDownChecksForServiceAccountEditor">
			                        </kendo:grid-column> 
			                        
									<kendo:grid-column title="Ledger&nbsp;Start&nbsp;Date&nbsp;*" field="ledgerStartDate" format="{0:dd/MM/yyyy}" filterable="false" width="110px"/>
									
									<kendo:grid-column title="Ledger&nbsp;End&nbsp;Date&nbsp;*" field="ledgerEndDate" format="{0:dd/MM/yyyy}" filterable="false" width="110px"/>
									
									<kendo:grid-column title="Status" field="status" width="90px" filterable="false">
									</kendo:grid-column> 			 						
																		     
        								 <kendo:grid-column title="&nbsp;" width="140px" >
							            	<kendo:grid-column-command>
							            		<kendo:grid-column-commandItem name="edit"/>
							            		<kendo:grid-column-commandItem name="destroy"/>
							            	</kendo:grid-column-command>
							            </kendo:grid-column>
							            
							            <kendo:grid-column title="&nbsp;" width="90px">
				                           <kendo:grid-column-command >
					                           <kendo:grid-column-commandItem name="LedgerEnd" click="LedgerEndDateClick" />
				                           </kendo:grid-column-command>
		                                </kendo:grid-column>
		                                
		                                <kendo:grid-column title="&nbsp;" width="75px" >
							             	<!-- Status updation purpose -->
							             </kendo:grid-column>
							            
							            <%-- <kendo:grid-column title="&nbsp;" width="110px">
										<kendo:grid-column-command >
											<kendo:grid-column-commandItem name="Change_Status" click="serviceAccountStatusClick" />
										</kendo:grid-column-command>
								        </kendo:grid-column> --%>
							            
        						</kendo:grid-columns>
        						
        						  <kendo:dataSource requestEnd="serviceAccountOnRequestEnd" requestStart="serviceAccountOnRequestStart">
						            <kendo:dataSource-transport>
						            <kendo:dataSource-transport-read url="${serviceAccountReadUrl}/#=serviceMasterId#" dataType="json" type="POST" contentType="application/json"/>
						            <kendo:dataSource-transport-create url="${serviceAccountCreateUrl}/#=serviceMasterId#" dataType="json" type="GET" contentType="application/json" />
						            <kendo:dataSource-transport-update url="${serviceAccountUpdateUrl}" dataType="json" type="GET" contentType="application/json" />
						            <kendo:dataSource-transport-destroy url="${serviceAccountDestroyUrl}" dataType="json" type="GET" contentType="application/json" />
						            </kendo:dataSource-transport>
						            
						            <kendo:dataSource-schema >
						                <kendo:dataSource-schema-model id="serviceAccoutId">
						                    <kendo:dataSource-schema-model-fields>
						                    
						                    <kendo:dataSource-schema-model-field name="serviceAccoutId" type="number">
											<kendo:dataSource-schema-model-field-validation  />
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="serviceMasterId" type="number">
											<kendo:dataSource-schema-model-field-validation  />
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="accountId" defaultValue="0">
											</kendo:dataSource-schema-model-field>

											<kendo:dataSource-schema-model-field name="typeOfService" type="string">
											<kendo:dataSource-schema-model-field-validation required="true"/>
											</kendo:dataSource-schema-model-field>
											
											<%-- <kendo:dataSource-schema-model-field name="serviceRefId" type="number">
											<kendo:dataSource-schema-model-field-validation required="true" min="1"/>
											</kendo:dataSource-schema-model-field> --%>
											
											<kendo:dataSource-schema-model-field name="ledgerStartDate" type="date">
											<kendo:dataSource-schema-model-field-validation required="true"/>
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="ledgerEndDate" type="date">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="serviceLedger" type="string">
											<kendo:dataSource-schema-model-field-validation required="true"/>
											</kendo:dataSource-schema-model-field>
																
											<kendo:dataSource-schema-model-field name="createdBy">
											</kendo:dataSource-schema-model-field>
						
											<kendo:dataSource-schema-model-field name="status"
											editable="true" type="string" />
						                    	
						                    </kendo:dataSource-schema-model-fields>
						                 </kendo:dataSource-schema-model>
						             </kendo:dataSource-schema>
						          </kendo:dataSource>
        				</kendo:grid>		
                    </div>
                </kendo:tabStrip-item-content>
            </kendo:tabStrip-item>
            
            <kendo:tabStrip-item selected="false" text="Meter History">
                <kendo:tabStrip-item-content>
                    <div class='wethear' style="width: 75%;">
						    <br/>
							<kendo:grid name="meterHistoryEvent_#=serviceMasterId#" pageable="true"
								resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true" edit="meterHistoryEvent" editable="true">
								<kendo:grid-pageable pageSize="10"></kendo:grid-pageable>
								<kendo:grid-filterable extra="false">
			                    <kendo:grid-filterable-operators>
				                    <kendo:grid-filterable-operators-string eq="Is equal to" />
			                    </kendo:grid-filterable-operators>
		                        </kendo:grid-filterable>
								<kendo:grid-editable mode="popup"/>
						       <%-- <kendo:grid-toolbar >
						            <kendo:grid-toolbarItem name="create" text="Add New Account" />
						        </kendo:grid-toolbar>  --%>
        						<kendo:grid-columns>
        							<kendo:grid-column title="meterHistoryId" field="meterHistoryId" hidden="true" width="70px" />
        							
        							<kendo:grid-column title="serviceMasterId" field="serviceMasterId" hidden="true" width="70px" />	
        																
									<kendo:grid-column title="Meter&nbsp;Serial&nbsp;No"  field="meterSerialNo" filterable="false" width="100px">
			                        </kendo:grid-column> 
									
									<kendo:grid-column title="Intial&nbsp;Reading" field="intialReading" width="100px" filterable="false">
									</kendo:grid-column> 
									
									<kendo:grid-column title="Final&nbsp;Reading" field="finalReading" width="100px" filterable="false">
									</kendo:grid-column> 
			                        
									<kendo:grid-column title="Meter&nbsp;Fixed&nbsp;Date" field="meterFixedDate" format="{0:dd/MM/yyyy}" filterable="false" width="110px"/>
									
									<kendo:grid-column title="Meter&nbsp;Release&nbsp;Date" field="meterReleaseDate" format="{0:dd/MM/yyyy}" filterable="false" width="110px"/>
																            
        						</kendo:grid-columns>
        						
        						  <kendo:dataSource requestEnd="serviceAccountOnRequestEnd" >
						            <kendo:dataSource-transport>
						            <kendo:dataSource-transport-read url="${meterHistoryReadUrl}/#=serviceMasterId#" dataType="json" type="POST" contentType="application/json"/>
						            </kendo:dataSource-transport>
						            
						            <kendo:dataSource-schema >
						                <kendo:dataSource-schema-model id="meterHistoryId">
						                    <kendo:dataSource-schema-model-fields>
						                    
						                    <kendo:dataSource-schema-model-field name="meterHistoryId" type="number">
											<kendo:dataSource-schema-model-field-validation  />
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="serviceMasterId" type="number">
											<kendo:dataSource-schema-model-field-validation  />
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="meterSerialNo" type="string">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="meterFixedDate" type="date">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="meterReleaseDate" type="date">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="intialReading" type="number">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="finalReading" type="number">
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

<div id="generateBillDialog" style="display: none;">
	<form id="addform" data-role="validator" novalidate="novalidate">
		<table>
			<tr>
				<td>
					<table id="previousDiv" style="height: 205px;">
						
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
									cascadeFrom="accountNo" required="required"
									validationMessage="Select Service Name"></kendo:dropDownList>
							</td>
						</tr>
						<tr>
                     <td>&nbsp;</td>
                     </tr>						
						<tr>						
						<tr>
							<td>Average Type</td>
							<td><kendo:dropDownList name="avgType" id="avgType"
									required="required" validationMessage="Select Average Type"></kendo:dropDownList>
							</td>
						</tr>						                     			
						 <tr>
                     <td>&nbsp;</td>
                     </tr>
                     <tr>						
					<tr id="billstDate">
							<td>Date</td>
							<td><kendo:datePicker format="dd/MM/yyyy  " name="avgDate"
									id="avgDate" required="required" class="validate[required]"
									validationMessage="Start Date is Required">
								</kendo:datePicker></td>
						</tr>
						<tr>
                     <td>&nbsp;</td>
                     </tr>
						<tr>
				<td class="left" align="center" colspan="4">
				
					<button type="submit" id="generateAdvanceBill" class="k-button"
									style="padding-left: 10px">Calculate Average</button>
				</td>
			</tr>
		</table>

</td>
</tr>
</table>				
</form>
</div>

<script>	



var selectedService ="";

function serviceMasterDeleteEvent(){
	securityCheckForActions("./Services/ServiceMaster/destroyButton");
	var conf = confirm("Are u sure want to delete this service details?");
	 if(!conf){
	  $("#grid").data().kendoGrid.dataSource.read();
	   throw new Error('deletion aborted');
	 }
}

// Onclick Functions

var SelectedRowId = "";
var SelectedServiceType = "";
var serviceSttDt = "";
var acId="";
function onChangeServiceMaster(arg) {
	 var gview = $("#grid").data("kendoGrid");
	 var selectedItem = gview.dataItem(gview.select());
	 SelectedRowId = selectedItem.serviceMasterId;
	 SelectedServiceType = selectedItem.typeOfService;
	 serviceSttDt = selectedItem.serviceStartDate;
	 acId = selectedItem.accountId;

	 this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
    //alert("Selected: " + SelectedRowId);   
}

function serviceMasterDataBound(e) 
{
	var data = this.dataSource.view(),row;
    var grid = $("#grid").data("kendoGrid");
    for (var i = 0; i < data.length; i++) {
    	var currentUid = data[i].uid;
        row = this.tbody.children("tr[data-uid='" + data[i].uid + "']");
        
        var serviceStartDt = data[i].serviceStartDate;
        var serviceEndDate = data[i].serviceEndDate;

        if(serviceStartDt==null){
        	
        	var currenRow = grid.table.find("tr[data-uid='" + currentUid+ "']");
			var reOpenButton = $(currenRow).find('.k-grid-ServiceEnd');
			reOpenButton.hide();
        }
        
 		if(serviceEndDate!= null){
        	
        	var serViceMasterId=data[i].serviceMasterId;
        	var currenRow = grid.table.find("tr[data-uid='" + currentUid+ "']");
			var reOpenButton = $(currenRow).find('.k-grid-ServiceEnd');
			reOpenButton.hide();
			var reOpenButton1 = $(currenRow).find('.k-grid-edit');
			reOpenButton1.hide();
			//.k-grid-Active#=data.serviceMasterId#
			var reOpenButton2 = $(currenRow).find(".k-grid-Active" + serViceMasterId+ "");
			reOpenButton2.hide();
			
        }
    }
}

function serviceParameterDeleteEvent(){
	securityCheckForActions("./Services/ServiceMaster/ServiceParameter/destroyButton");
	var conf = confirm("Are u sure want to delete this parameter details?");
	 if(!conf){
	  $("#serviceMasterParameterEvent_"+SelectedRowId).data().kendoGrid.dataSource.read();
	   throw new Error('deletion aborted');
	 }
}

function serviceAccountDeleteEvent(){
	securityCheckForActions("./Services/ServiceMaster/ServiceAccount/destroyButton");
	var conf = confirm("Are u sure want to delete this account details?");
	 if(!conf){
	  $("#serviceAccoutEvent_"+SelectedRowId).data().kendoGrid.dataSource.read();
	   throw new Error('deletion aborted');
	 }
}
 
function ServiceEndDateClick()
{
	var serviceMasterId="";
	var gridParameter = $("#grid").data("kendoGrid");
	var selectedAddressItem = gridParameter.dataItem(gridParameter.select());
	serviceMasterId = selectedAddressItem.serviceMasterId;
	var result=securityCheckForActionsForStatus("./Services/ServiceMaster/serviceEndDateButton"); 
	if(result=="success"){  
	$.ajax
	({			
		type : "POST",
		url : "./serviceMasters/serviceEndDateUpdate/"+serviceMasterId,
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

function LedgerEndDateClick()
{
	var serviceAccoutId="";
	var gridParameter = $("#serviceAccoutEvent_"+SelectedRowId).data("kendoGrid");
	var selectedAddressItem = gridParameter.dataItem(gridParameter.select());
	serviceAccoutId = selectedAddressItem.serviceAccoutId;
	var result=securityCheckForActionsForStatus("./Services/ServiceMaster/ServiceAccount/ledgerEndButton");	  
	if(result=="success"){ 
	$.ajax
	({			
		type : "POST",
		url : "./serviceMasters/ledgerEndDateUpdate/"+serviceAccoutId,
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
			$('#serviceAccoutEvent_'+SelectedRowId).data('kendoGrid').dataSource.read();
		}
	});
	}
}

/************* Sub grid parameter status change code starts    *************/
function onDataBoundParameterStatus(e) 
	{
	  	var vgrid = $('#serviceMasterParameterEvent_'+SelectedRowId).data("kendoGrid");
	  	var items = vgrid.dataSource.data();
	 	var i = 0;
	 	this.tbody.find("tr td:last-child").each(function (e) 
	   	{
	  	  	var item = items[i];
		   	if(item.status == 'Active')
		   	{
		   		$("<button id='parameterStatus' class='k-button k-button-icontext' onclick='parameterStatusClick()'>Inactivate</button>").appendTo(this);
		   	}
		   	else
		   	{
		   		$("<button id='parameterStatus' class='k-button k-button-icontext' onclick='parameterStatusClick()'>Activate</button>").appendTo(this);
		   	}	
		   	i++;
	   	});
	}
 
function parameterStatusClick()
{
	var serviceParameterId="";
	var gridParameter = $("#serviceMasterParameterEvent_"+SelectedRowId).data("kendoGrid");
	var selectedAddressItem = gridParameter.dataItem(gridParameter.select());
	serviceParameterId = selectedAddressItem.serviceParameterId;
	var result=securityCheckForActionsForStatus("./Services/ServiceMaster/ServiceParameter/activeInactiveButton");	  
	if(result=="success"){ 
	$.ajax
	({			
		type : "POST",
		url : "./serviceMasters/parameterUpdateFromInnerGrid/"+serviceParameterId,
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
			$('#serviceMasterParameterEvent_'+SelectedRowId).data('kendoGrid').dataSource.read();
		}
	});
	}
}
  /************* Sub grid parameter status change code Ends    *************/
  
    /************* Sub grid service Account status change code starts    *************/
 
  function onDataBoundServiceAccountStatus(e) 
	{
	    
	  	var vgrid = $('#serviceAccoutEvent_'+SelectedRowId).data("kendoGrid");
	  	var items = vgrid.dataSource.data();
	 	var i = 0;
	 	this.tbody.find("tr td:last-child").each(function (e) 
	   	{
	  	  	var item = items[i];
		   	if(item.status == 'Active')
		   	{
		   		$("<button id='serviceAccountStatus' class='k-button k-button-icontext' onclick='serviceAccountStatusClick()'>Inactivate</button>").appendTo(this);
		   	}
		   	else
		   	{
		   		$("<button id='serviceAccountStatus' class='k-button k-button-icontext' onclick='serviceAccountStatusClick()'>Activate</button>").appendTo(this);
		   	}	
		   	i++;
	   	});
	}
  
  function serviceAccountStatusClick()
	{
		var serviceAccoutId="";
		var gridParameter = $("#serviceAccoutEvent_"+SelectedRowId).data("kendoGrid");
		var selectedAddressItem = gridParameter.dataItem(gridParameter.select());
		serviceAccoutId = selectedAddressItem.serviceAccoutId;
		var result=securityCheckForActionsForStatus("./Services/ServiceMaster/ServiceAccount/activeInactiveButton");	  
		if(result=="success"){ 
		$.ajax
		({			
			type : "POST",
			url : "./serviceMasters/serviceAccountUpdateFromInnerGrid/"+serviceAccoutId,
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
				$('#serviceAccoutEvent_'+SelectedRowId).data('kendoGrid').dataSource.read();
			}
		});
		}
	}
  /************* Sub grid service Account status change code ends    *************/

 // Editors Code Starts
 
 function dropDownChecksTODEditor(container, options) {
		var res = (container.selector).split("=");
		var attribute = res[1].substring(0,res[1].length-1);
		
		$('<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '" name = "'+attribute+'" id = "'+attribute+'Id"/>')
				.appendTo(container).kendoDropDownList({
					/* optionLabel : {
						text : "Select",
						value : "",
					}, */
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
					change : selectServiceType,
					dataSource : {
						transport : {
							read : "${allChecksUrl}/"+attribute,
						}
					}
				});
		 $('<span class="k-invalid-msg" data-for="'+attribute+'"></span>').appendTo(container);
	}
	
function dropDownChecksForServiceAccountEditor(container, options) {
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
  
  
  function selectServiceType(e){
	  
	  var dataItem = this.dataItem(e.item);
	  selectedService=dataItem.text;
	 	if(selectedService=="Electricity"){
	 		var comboBoxDataSource = new kendo.data.DataSource({
		            transport: {
		                read: {
		                    url     : "./serviceMasters/electricityTariffList/"+selectedService,
		                    dataType: "json",
		                    type    : 'GET'
		                }
		            },
	 		           
	 		 });
	 		        
	         $("#elTariffID").kendoComboBox({
	            dataSource    : comboBoxDataSource,
	            /* optionLabel : "Select", */
	            placeholder: "Select Tariff",
	            dataTextField : "tariffName",
	            dataValueField: "elTariffID",
	        });
	         $("#elTariffID").data("kendoComboBox").value("");
	         
	        $('div[data-container-for="todApplicable"]').show();
	 		$('label[for="todApplicable"]').closest('.k-edit-label').show();
	 	} 
	 	
	 	if(selectedService=="Gas"){
	 		var comboBoxDataSource = new kendo.data.DataSource({
		            transport: {
		                read: {
		                    url     : "./serviceMasters/gasTariffList/"+selectedService,
		                    dataType: "json",
		                    type    : 'GET'
		                }
		            },
	 		           
	 		 });
	 		        
	         $("#elTariffID").kendoComboBox({
	            dataSource    : comboBoxDataSource,
	            /* optionLabel : "Select", */
	            placeholder: "Select Tariff",
	            dataTextField : "tariffName",
	            dataValueField: "elTariffID",
	        });
	         $("#elTariffID").data("kendoComboBox").value("");
	         
	            $('div[data-container-for="todApplicable"]').hide();
		 		$('label[for="todApplicable"]').closest('.k-edit-label').hide();
	 	} 
	 	
	 	if(selectedService=="Water"){
	 		var comboBoxDataSource = new kendo.data.DataSource({
		            transport: {
		                read: {
		                    url     : "./serviceMasters/waterTariffList/"+selectedService,
		                    dataType: "json",
		                    type    : 'GET'
		                }
		            },
	 		           
	 		 });
	 		        
	         $("#elTariffID").kendoComboBox({
	            dataSource    : comboBoxDataSource,
	            /* optionLabel : "Select", */
	            placeholder: "Select Tariff",
	            dataTextField : "tariffName",
	            dataValueField: "elTariffID",
	        });
	         $("#elTariffID").data("kendoComboBox").value("");
	         
	         $('div[data-container-for="todApplicable"]').hide();
		 		$('label[for="todApplicable"]').closest('.k-edit-label').hide();
	 	} 
	 	
	 	if(selectedService=="Solid Waste"){
	 		var comboBoxDataSource = new kendo.data.DataSource({
		            transport: {
		                read: {
		                    url     : "./serviceMasters/solidWasteTariffList/"+selectedService,
		                    dataType: "json",
		                    type    : 'GET'
		                }
		            },
	 		           
	 		 });
	 		        
	         $("#elTariffID").kendoComboBox({
	            dataSource    : comboBoxDataSource,
	            /* optionLabel : "Select", */
	            placeholder: "Select Tariff",
	            dataTextField : "tariffName",
	            dataValueField: "elTariffID",
	        });
	         $("#elTariffID").data("kendoComboBox").value("");
	         
	         $('div[data-container-for="todApplicable"]').hide();
		 		$('label[for="todApplicable"]').closest('.k-edit-label').hide();
	 	} 
	 	
	 	if(selectedService=="Others"){
	 		var comboBoxDataSource = new kendo.data.DataSource({
		            transport: {
		                read: {
		                    url     : "./serviceMasters/othersTariffList/"+selectedService,
		                    dataType: "json",
		                    type    : 'GET'
		                }
		            },
	 		           
	 		 });
	 		        
	         $("#elTariffID").kendoComboBox({
	            dataSource    : comboBoxDataSource,
	            /* optionLabel : "Select", */
	            placeholder: "Select Tariff",
	            dataTextField : "tariffName",
	            dataValueField: "elTariffID",
	        });
	         $("#elTariffID").data("kendoComboBox").value("");
	         
	         $('div[data-container-for="todApplicable"]').hide();
		 		$('label[for="todApplicable"]').closest('.k-edit-label').hide();
	 	} 
	 	
	 	if(selectedService=="Telephone Broadband"){
	 		var comboBoxDataSource = new kendo.data.DataSource({
		            transport: {
		                read: {
		                    url     : "./serviceMasters/broadBandTariffList/"+selectedService,
		                    dataType: "json",
		                    type    : 'GET'
		                }
		            },
	 		           
	 		 });
	 		        
	         $("#elTariffID").kendoComboBox({
	            dataSource    : comboBoxDataSource,
	            /* optionLabel : "Select", */
	            placeholder: "Select Tariff",
	            dataTextField : "tariffName",
	            dataValueField: "elTariffID",
	        });
	         $("#elTariffID").data("kendoComboBox").value("");
	         
	         $('div[data-container-for="todApplicable"]').hide();
		 		$('label[for="todApplicable"]').closest('.k-edit-label').hide();
	 	}
	 	
	 	if(selectedService=="CAM"){
	 		$('div[data-container-for="todApplicable"]').remove();
	 		$('label[for="todApplicable"]').closest('.k-edit-label').remove();
	 		
	 		$('div[data-container-for="elTariffID"]').remove();
	 		$('label[for="elTariffID"]').closest('.k-edit-label').remove();
	 	}
  }
  
  function parameterNameEditor(container, options){
	  $('<select data-text-field="spmName" name="spmName"  data-value-field="spmId" required="true" validationmessage="Parameter name is required" id="spmId" data-bind="value:' + options.field + '"/>')
      .appendTo(container)
      .kendoDropDownList
      ({
          autoBind: false,
          optionLabel : "Select", 
          defaultValue : false,
		  sortable : true,
          dataSource: {  
              transport:{
                  read: "${serviceParameterList}/"+SelectedServiceType,
              } 
          }
      });
	$('<span class="k-invalid-msg" data-for="spmName"></span>').appendTo(container);
  }
  
  function tariffEditor(container, options){
	  $('<select data-text-field="tariffName" name="tariffName"  data-value-field="elTariffID" required="true" validationmessage="Tariff name is required" id="elTariffID" data-bind="value:' + options.field + '"/>')
      .appendTo(container)
      .kendoComboBox
      ({
     	 placeholder: "Select Tariff",
          autoBind: false,
          /* optionLabel : "Select", */
          dataSource: {  
              transport:{
                  read: "${tariffList}",
              } 
          }
      });
	$('<span class="k-invalid-msg" data-for="tariffName"></span>').appendTo(container);
  }
 
  function spEquipmentCommentsEditor(container, options) 
	{
     $('<textarea data-text-field="comments" name = "comments" style="width:150px"/>')
          .appendTo(container);
     $('<span class="k-invalid-msg" data-for="Enter Comments"></span>').appendTo(container);
	}
  
  function spInstructionsEditor(container, options) 
	{
   $('<textarea data-text-field="instructions" name = "instructions" style="width:150px"/>')
        .appendTo(container);
   $('<span class="k-invalid-msg" data-for="Enter Instructions"></span>').appendTo(container);
	}
  
  function PersonNames(container, options) 
  {
		$('<input name="Person" id="personId" data-text-field="personName" validationmessage="Person name is required" data-value-field="personId" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoComboBox({
			autoBind : false,
			placeholder : "Select Person",
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
		
		$('<span class="k-invalid-msg" data-for="Person"></span>').appendTo(container);
  }
  
  function AccountNumbers(container, options) 
  {
		$('<input name="Account" id="account" data-text-field="accountNo" validationmessage="Account number is required" data-value-field="accountId" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoComboBox({
		 cascadeFrom : "personId",
		 autoBind : false,
		 placeholder : "Select account",
		 headerTemplate : '<div class="dropdown-header">'
				+ '<span class="k-widget k-header">Accout Type</span>'
				+ '<span class="k-widget k-header">Account Number</span>'
				+ '</div>',
			template : '<table><tr><td align="left"><span class="k-state-default"><b>#: data.accountType #</b></span><br>'
				+ '<span class="k-state-default"><i>#: data.accountNo #</i></span></td></tr></table>',
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
  
  function equipmentTypeAutocomplete(container, options) {
	  $('<input name="equipmentTypeEE" id="equipmentType" required="true" validationmessage="Equipment Type is required" data-text-field="value" data-value-field="name" data-bind="value:' + options.field + '"/>')
	     .appendTo(container)
	     .kendoAutoComplete({
			dataType: 'JSON',
			dataSource: {
				transport: {
					read: "${getEquipmentTypeList}"
				}
			}
		});
	  $('<span class="k-invalid-msg" data-for="equipmentTypeEE"></span>').appendTo(container);
	}	
  
  // Editors Code Ends
  
  // Grid Click Function Code Starts
 
	$("#grid").on("click", "#temPID", function(e) {
		var button = $(this), enable = button.text() == "Activate";
		var widget = $("#grid").data("kendoGrid");
		var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
		 var result=securityCheckForActionsForStatus("./Services/ServiceMaster/activeInactiveButton"); 
		  if(result=="success"){  
					if (enable) 
					{
						$.ajax
						({
							type : "POST",
							url : "./serviceMasters/serviceMasterStatus/" + dataItem.id + "/activate",
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
								button.text('Inactivate');
								$('#grid').data('kendoGrid').dataSource.read();
							}
						});
					} 
					else 
					{
						$.ajax
						({
							type : "POST",
							url : "./serviceMasters/serviceMasterStatus/" + dataItem.id + "/deactivate",
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
								button.text('Activate');
								$('#grid').data('kendoGrid').dataSource.read();
							}
						});
					}
		        }
			});
	
	var setApCode = "";
	var acNoId="";
	var serDt;
	var resDate;
function serviceMasterEvent(e)
{

	
	/***************************  to remove the id from pop up  **********************/
	$('div[data-container-for="serviceMasterId"]').remove();
	$('label[for="serviceMasterId"]').closest(
	'.k-edit-label').remove();
	
	$(".k-edit-field").each(function () {
		$(this).find("#temPID").parent().remove();  
   	});
	
	$('label[for=tariffName]').parent().hide();
	$('div[data-container-for="tariffName"]').hide();
	
	$('label[for=property_No]').parent().hide();
	$('div[data-container-for="property_No"]').hide();
	
	$('label[for=todApplicable]').parent().hide();
	$('div[data-container-for="todApplicable"]').hide();
	
	$('label[for=accountNo]').parent().hide();
	$('div[data-container-for="accountNo"]').hide();
	
	$('label[for=serviceEndDate]').parent().hide();
	$('div[data-container-for="serviceEndDate"]').hide();
	
	$('label[for="status"]').parent().hide();  
	$('div[data-container-for="status"]').hide();
	
	$('label[for="createdBy"]').parent().hide();  
	$('div[data-container-for="createdBy"]').hide();
	
	$('label[for="personName"]').parent().hide();  
	$('div[data-container-for="personName"]').hide();
	
	e.container.find(".k-grid-cancel").bind("click", function () {
	    	var grid = $("#grid").data("kendoGrid");
		grid.dataSource.read();
		grid.refresh();
    }); 
	
	
	/************************* Button Alerts *************************/
	if (e.model.isNew()) 
    {
		securityCheckForActions("./Services/ServiceMaster/createButton");
		$(".k-window-title").text("Add New Service Master Details");
		$(".k-grid-update").text("Save");		
    }
	else{
		 
		    var s = $('input[name="serviceStartDate"]').val();

			securityCheckForActions("./Services/ServiceMaster/updateButton");
			
			
			var gview = $("#grid").data("kendoGrid");
			var selectedItem = gview.dataItem(gview.select());
			selectedService = selectedItem.typeOfService;
			acNoId=selectedItem.accountId;
			serDt=selectedItem.serviceStartDate;

			if(selectedService=="Electricity"){
				$('div[data-container-for="todApplicable"]').show();
				$('label[for="todApplicable"]').closest('.k-edit-label').show();
			}
			
			if(selectedService=="CAM" || selectedService=="Telephone Broadband"){
				$('div[data-container-for="todApplicable"]').remove();
		 		$('label[for="todApplicable"]').closest('.k-edit-label').remove();
		 		
		 		$('div[data-container-for="elTariffID"]').remove();
		 		$('label[for="elTariffID"]').closest('.k-edit-label').remove();
			}

			setApCode = $('input[name="servicePointId"]').val();
			$(".k-window-title").text("Edit Service Master Details");
			
			
			
          	     $(".k-grid-update").click(function (){
  				
          	    var sdt = $('input[name="serviceStartDate"]').val();
          		var service = $("input[name=typeOfService]").data("kendoDropDownList").text();
          		var acNoId = $("#account").val();

	  
     			$.ajax({
				     url:'./billingparameter/getMeterFixedDate/'+"ElectricityMeterLocationEntity/"+"meterFixedDate/"+"electricityMetersEntity.typeOfServiceForMeters/"+service+"/account.accountId/"+acNoId,
				     dataType:"JSON",
				     async: false,
				    
				     success: function (response) {
				    	
    			             resDate=response;

				    }
				     
				});
     			
     			
     			var parts = resDate.split("-");
                var day = parts[2] && parseInt( parts[2], 10 );
                var month = parts[1] && parseInt( parts[1], 10 );
                var year = parts[0] && parseInt( parts[0], 10 );
               
                
                var ndate;
                ndate=new Date(year,month-1,day);
           
  		 			 if(new Date(ndate)>new Date(sdt)){
	  		 			alert("Start Date cannot be less than Meter Fixed Date");
	  		 			return false;
	  		 		}	 
  		 		
  		 	});   
               
		}

	}

	function clearFilterServiceMasters() {
		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
		var gridStoreIssue = $("#grid").data("kendoGrid");
		gridStoreIssue.dataSource.read();
		gridStoreIssue.refresh();
	}
	
	/********************** to hide the child table id ***************************/
	
	function meterHistoryEvent(e) {
		$('div[data-container-for="serviceAccoutId"]').remove();
		$('label[for="serviceAccoutId"]').closest('.k-edit-label').remove();

		$('div[data-container-for="serviceMasterId"]').remove();
		$('label[for="serviceMasterId"]').closest('.k-edit-label').remove();

		$('div[data-container-for="status"]').remove();
		$('label[for="status"]').closest('.k-edit-label').remove();

		$('div[data-container-for="accountId"]').remove();
		$('label[for="accountId"]').closest('.k-edit-label').remove();

		$('div[data-container-for="ledgerEndDate"]').remove();
		$('label[for="ledgerEndDate"]').closest('.k-edit-label').remove();

		$('div[data-container-for="createdBy"]').remove();
		$('label[for="createdBy"]').closest('.k-edit-label').remove();

		if (e.model.isNew()) {
			securityCheckForActions("./Services/ServiceMaster/ServiceAccount/createButton");
			$(".k-window-title").text("Add New Account");
			$(".k-grid-update").text("Save");

		} else {
			securityCheckForActions("./Services/ServiceMaster/ServiceAccount/updateButton");
			setApCode = $('input[name="serviceAccoutId"]').val();
			$(".k-window-title").text("Edit Account Details");
		}
	}
	
	function serviceAccoutEvent(e) {
		$('div[data-container-for="serviceAccoutId"]').remove();
		$('label[for="serviceAccoutId"]').closest('.k-edit-label').remove();

		$('div[data-container-for="serviceMasterId"]').remove();
		$('label[for="serviceMasterId"]').closest('.k-edit-label').remove();

		$('div[data-container-for="status"]').remove();
		$('label[for="status"]').closest('.k-edit-label').remove();

		$('div[data-container-for="accountId"]').remove();
		$('label[for="accountId"]').closest('.k-edit-label').remove();

		$('div[data-container-for="ledgerEndDate"]').remove();
		$('label[for="ledgerEndDate"]').closest('.k-edit-label').remove();

		$('div[data-container-for="createdBy"]').remove();
		$('label[for="createdBy"]').closest('.k-edit-label').remove();

		if (e.model.isNew()) {
			securityCheckForActions("./Services/ServiceMaster/ServiceAccount/createButton");
			$(".k-window-title").text("Add New Account");
			$(".k-grid-update").text("Save");

		} else {
			securityCheckForActions("./Services/ServiceMaster/ServiceAccount/updateButton");
			setApCode = $('input[name="serviceAccoutId"]').val();
			$(".k-window-title").text("Edit Account Details");
		}
	}

	function serviceMasterParameterEvent(e) {
		$('div[data-container-for="sertviceParameterId"]').remove();
		$('label[for="sertviceParameterId"]').closest('.k-edit-label').remove();

		$('div[data-container-for="serviceMasterId"]').remove();
		$('label[for="serviceMasterId"]').closest('.k-edit-label').remove();
		
		$('div[data-container-for="spmName"]').remove();
		$('label[for="spmName"]').closest('.k-edit-label').remove();
		
		$('div[data-container-for="serviceParameterDataType"]').remove();
		$('label[for="serviceParameterDataType"]').closest('.k-edit-label').remove();

		$('div[data-container-for="status"]').remove();
		$('label[for="status"]').closest('.k-edit-label').remove();

		$('div[data-container-for="createdBy"]').remove();
		$('label[for="createdBy"]').closest('.k-edit-label').remove();

		if (e.model.isNew()) {
			/* $('div[data-container-for="serviceParameterDataType"]').remove();
			 $('label[for="serviceParameterDataType"]').closest('.k-edit-label').remove();  */
			securityCheckForActions("./Services/ServiceMaster/ServiceParameter/createButton");
			$(".k-window-title").text("Add New Parameter");
			$(".k-grid-update").text("Save");

		} else {
			securityCheckForActions("./Services/ServiceMaster/ServiceParameter/updateButton");
			setApCode = $('input[name="sertviceParameterId"]').val();
			$('label[for="status"]').parent().hide();
			$('div[data-container-for="status"]').hide();
			$(".k-window-title").text("Edit Parameter Details");
		}

		//CLIENT SIDE VALIDATION FOR MULTI SELECT
		$(".k-grid-update").click(function() {
			var spmId = $("select[name=spmId] :selected").val();

			/* if ((spmId == null) || (spmId == 0)) {
				alert("Parameter name is not selected");
				return false;
			} */
		});

	}

	function onRequestStart(e){
		
			/* var gridStoreGoodsReturn = $("#grid").data("kendoGrid");
			gridStoreGoodsReturn.cancelRow(); */
			
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();
		
	}
	
	function serviceParameterOnRequestStart(e){
	
	$('.k-grid-update').hide();
    $('.k-edit-buttons')
            .append(
                    '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
    $('.k-grid-cancel').hide();
	
   }
	
	function serviceAccountOnRequestStart(e){
		
		$('.k-grid-update').hide();
	    $('.k-edit-buttons')
	            .append(
	                    '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
	    $('.k-grid-cancel').hide();
		
	 }
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
							"Error: Creating the Service Master Details<br>"
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

				if (r.type == "update") {
					//alert("Error: Updating the USER record\n\n" + errorInfo);
					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Updating the Service Master Details<br>"
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

				$('#grid').data('kendoGrid').refresh();
				$('#grid').data('kendoGrid').dataSource.read();
				return false;
			}

			if (r.response.status == "CHILD") {

				$("#alertsBox").html("");
				$("#alertsBox")
						.html(
								"Can't delete service master details, child record found");
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

			if (r.response.status == "AciveServiceMasterDestroyError") {
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Active service master details cannot be deleted");
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
							"Error: Creating the Service Master Details<br>"
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
							"Error: Creating the Service Master Details<br>"
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

				if (r.type == "update") {
					//alert("Error: Creating the USER record\n\n" + errorInfo);
					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Updating the Service Master Details<br>"
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

				$('#grid').data('kendoGrid').refresh();
				$('#grid').data('kendoGrid').dataSource.read();
				return false;
			}

			else if (r.type == "create") {
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Service master details created successfully");
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

			else if (r.type == "update") {

				//alert("User record updated successfully");
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Service master details updated successfully");
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

			} else if (r.type == "destroy") {
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Service master details deleted successfully");
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

	/************************************* for inner rate slab request *********************************/

	function serviceAccountOnRequestEnd(e) {
		if (typeof e.response != 'undefined') {
			//alert("Response is Undefined");

			if (e.response.status == "FAIL") {
				errorInfo = "";

				for (var k = 0; k < e.response.result.length; k++) {
					errorInfo += (k + 1) + ". "
							+ e.response.result[k].defaultMessage + "<br>";

				}

				if (e.type == "create") {
					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Assigning Permission to AccessCard<br>"
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

				else if (e.type == "update") {
					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Updating the Permission to AccessCard<br>"
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

				$('#serviceAccoutEvent_' + SelectedRowId).data().kendoGrid.dataSource
						.read({
							personId : SelectedAccessCardId
						});
				/* var grid = $("#propertyGrid_"+SelectedRowId).data("kendoGrid");
				grid.dataSource.read();
				grid.refresh(); */
				return false;
			}

			else if (e.type == "create") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Account Record Created Successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				var gridPets = $("#serviceAccoutEvent_" + SelectedRowId).data(
						"kendoGrid");
				gridPets.dataSource.read();
				gridPets.refresh();
			}

			else if (e.type == "update") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Account Record updated successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				var gridPets = $("#serviceAccoutEvent_" + SelectedRowId).data(
						"kendoGrid");
				gridPets.dataSource.read();
				gridPets.refresh();
			}

			else if (e.response.status == "AciveServiceAccountDestroyError") {
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Active service account details cannot be deleted");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				var gridPets = $("#serviceAccoutEvent_" + SelectedRowId).data(
						"kendoGrid");
				gridPets.dataSource.read();
				gridPets.refresh();
				return false;
			}

			else if (e.type == "destroy") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Account Record delete successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				var gridPets = $("#serviceAccoutEvent_" + SelectedRowId).data(
						"kendoGrid");
				gridPets.dataSource.read();
				gridPets.refresh();
			}

		}

	}

	function serviceParameterOnRequestEnd(e) {
		if (typeof e.response != 'undefined') {
			//alert("Response is Undefined");

			if (e.response.status == "FAIL") {
				errorInfo = "";

				for (var k = 0; k < e.response.result.length; k++) {
					errorInfo += (k + 1) + ". "
							+ e.response.result[k].defaultMessage + "<br>";

				}

				if (e.type == "create") {
					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Assigning Permission to AccessCard<br>"
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

				else if (e.type == "update") {
					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Updating the Permission to AccessCard<br>"
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

				$('#gridAccessCardPermission_' + SelectedRowId).data().kendoGrid.dataSource
						.read({
							personId : SelectedAccessCardId
						});
				/* var grid = $("#propertyGrid_"+SelectedRowId).data("kendoGrid");
				grid.dataSource.read();
				grid.refresh(); */
				return false;
			}

			else if (e.type == "create") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Parameter Record Created Successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				var gridPets = $(
						"#serviceMasterParameterEvent_" + SelectedRowId).data(
						"kendoGrid");
				gridPets.dataSource.read();
				gridPets.refresh();
			}

			else if (e.type == "update") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Parameter Record updated successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				var gridPets = $(
						"#serviceMasterParameterEvent_" + SelectedRowId).data(
						"kendoGrid");
				gridPets.dataSource.read();
				gridPets.refresh();
			}

			else if (e.response.status == "AciveParameterDestroyError") {
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Active service parameter details cannot be deleted");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				var gridPets = $(
						"#serviceMasterParameterEvent_" + SelectedRowId).data(
						"kendoGrid");
				gridPets.dataSource.read();
				gridPets.refresh();
				return false;
			}

			else if (e.type == "destroy") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Parameter Record delete successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				var gridPets = $(
						"#serviceMasterParameterEvent_" + SelectedRowId).data(
						"kendoGrid");
				gridPets.dataSource.read();
				gridPets.refresh();

			}
		}
	}

	//register custom validation rules
	(function($, kendo) {
		$
				.extend(
						true,
						kendo.ui.validator,
						{
							rules : { // custom rules

								serviceStartDateValidation : function(input,
										params) {
									if (input
											.filter("[name = 'serviceStartDate']").length
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
								ledgerStartDateValidation : function(input,
										params) {
									if (input
											.filter("[name = 'ledgerStartDate']").length
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
								serviceParameterSequenceRequired : function(
										input, params) {
									if (input.attr("name") == "serviceParameterSequence") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},
								serviceParameterValueRequired : function(input,
										params) {
									if (input.attr("name") == "serviceParameterValue") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},
								serviceParameterValueValidation : function(
										input, params) {
									if (input
											.filter("[name='serviceParameterValue']").length
											&& input.val() != "") {
										return /^[a-zA-Z0-9.]{1,45}$/
												.test(input.val());
									}
									return true;
								},
								serviceParameterDataTypeRequired : function(
										input, params) {
									if (input.attr("name") == "serviceParameterDataType") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},
								typeOfServiceRequired : function(input, params) {
									if (input.attr("name") == "typeOfService") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},
								serviceLedgerRequired : function(input, params) {
									if (input.attr("name") == "serviceLedger") {
										return $.trim(input.val()) !== "";
									}
									return true;
								}
							},
							messages : {
								//custom rules messages
								serviceStartDateValidation : "Service start date must be past",
								ledgerStartDateValidation : "Ledger date must be past",
								serviceParameterSequenceRequired : "Parameter sequence is required",
								serviceParameterValueRequired : "Parameter value is required",
								serviceParameterValueValidation : "Allows only alphanumeric characters",
								serviceParameterDataTypeRequired : "Data type is required",
								typeOfServiceRequired : "Service type is required",
								serviceLedgerRequired : "Ledger type is required"
							}
						});

	})(jQuery, kendo);
	
/*  */
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
		 alert("Your Average has been calculated");
		 $('#grid').data('kendoGrid').refresh();
		$('#grid').data('kendoGrid').dataSource.read(); 
		close();
		
	}
});

}

$("#grid").on(
		"click",
		".k-grid-generateBill",
		function(e) {
			
			var todcal = $("#generateBillDialog");
			todcal.kendoWindow({
				width : "auto",
				height : "auto",
				modal : true,
				draggable : true,
				position : {
					top : 100
				},
				title : "Calculate Average"
			}).data("kendoWindow").center().open();

			todcal.kendoWindow("open");
			  
			
			  var combobox = $('#accountNo').data("kendoComboBox");
			  if (combobox != null) {

					combobox.value("");
					
				} 
			  var dropdownlist = $("#serviceName").data("kendoDropDownList");
				dropdownlist.value("");
				$('#units').val("");
				
			
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

	//End Of Validation
</script>
<style>
span.k-tooltip {
    border-width: 1px;
    display: list-item;
    padding: 2px 5px 1px 6px;
    position: absolute;
}

</style>