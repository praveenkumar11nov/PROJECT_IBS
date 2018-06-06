<%@include file="/common/taglibs.jsp"%>

<!-- Create Read Update Delete URL's Of Electricity Meters -->
<c:url value="/electrictyMeters/electricityMetertCreate" var="electricityMetertCreateUrl" />
<c:url value="/electrictyMeters/elMeterRead" var="elMeterReadUrl" />
<c:url value="/electrictyMeters/electricityMeterUpdate" var="electricityMeterUpdateUrl" />
<c:url value="/electrictyMeters/electricityMeterDestroy" var="electricityMeterDestroyUrl" />

<!-- Filter URL's Of Electricity Meter -->
<c:url value="/mailroom/readTowerNames" var="towerNames" />
<c:url value="/mailroom/readPropertyNumbers" var="propertyNum" />
<c:url value="/electrictyMeters/filter" var="commonFilterForMtersUrl" />

<!-- Create Read Update Delete URL's Of Meter Parameters -->
<c:url value="/electrictyMeters/meterParameterCreate" var="meterParameterCreateUrl" />
<c:url value="/electrictyMeters/meterParameterRead" var="meterParameterReadUrl" />
<c:url value="/electrictyMeters/meterParameterUpdate" var="meterParameterUpdateUrl" />
<c:url value="/electrictyMeters/meterParameterDestroy" var="meterParameterDestroyUrl" />
<c:url value="/electrictyMeters/getMeterParameterNamesList" var="getMeterParameterUrl" />

<!-- Filter URL's Of Meter Parameter -->
<c:url value="/meterParameter/filter" var="commonFilterForMeterParameterUrl" />

<!-- Create Read Update Delete URL's Of Electricity Location-->
<c:url value="/electrictyMeters/meterLocationCreate" var="meterLocationCreateUrl" />
<c:url value="/electrictyMeters/meterLocationsRead" var="meterLocationsReadUrl" />
<c:url value="/electrictyMeters/meterLocationUpdate" var="meterLocationUpdateUrl" />
<c:url value="/electrictyMeters/meterLocationDestroy" var="meterLocationDestroyUrl" />

<!-- Filter URL's Of Meter Location -->
<c:url value="/meterLocation/getServiceTypesUrl" var="getServiceTypesUrl" />
<c:url value="/meters/accountNumberAutocomplete" var="accountNumberAutocomplete" />
<c:url value="/common/relationshipIds/getFilterAutoCompleteValues" var="getFilterAutoCompleteValues" />
<c:url value="/meterLocation/filter" var="commonFilterForMeterLocationUrl" />
<c:url value="/common/getAllChecks" var="allChecksUrl" />
<c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />
<c:url value="/electrictyMeters/getPersonListForFileter" var="personNamesFilterUrl" />
<c:url value="/electrictyMeters/propertyNameFilterUrl" var="propertyNameFilterUrl" />

<kendo:grid name="grid" remove="meterDeleteEvent" resizable="true" pageable="true" selectable="true" change="onChangeMeter" edit="electricityMeterEvent" detailTemplate="electricityMetersTemplate" sortable="true" scrollable="true" 
		groupable="true">
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="5"></kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
			<kendo:grid-filterable-operators>
				<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains" />
			</kendo:grid-filterable-operators>

		</kendo:grid-filterable>
		<kendo:grid-editable mode="popup"/>
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add New Meter" />
			 <kendo:grid-toolbarItem name="electricityTemplatesDetailsExport" text="Export To Excel" />
			 	 <kendo:grid-toolbarItem name="electricityPdfTemplatesDetailsExport" text="Export To PDF" />
			<kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterMeter()><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>"/>
		</kendo:grid-toolbar>
		<kendo:grid-columns>
			
			<kendo:grid-column title="MeterID" field="elMeterId" width="110px" hidden="true"/>
			
			<kendo:grid-column title="Account&nbsp;No&nbsp;*" field="accountNo" filterable="true" width="110px">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function typeOfServiceFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Service Type",                                            
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getFilterAutoCompleteValues}/ElectricityMetersEntity/account/accountNo"
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
	    	
	    	<kendo:grid-column title="Property&nbsp;Number*"  field="property_No" filterable="true" width="110px">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function typeOfServiceFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Service Type",                                            
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${propertyNameFilterUrl}"
										}
									}
								});    
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="Service&nbsp;Type *"  field="typeOfServiceForMeters" filterable="true" width="110px" editor="dropDownChecksEditorForServiceType">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function typeOfServiceFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Service Type",                                            
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${commonFilterForMtersUrl}/typeOfServiceForMeters"
										}
									}
								});    
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="Meter&nbsp;Serial&nbsp;No&nbsp;*" field="meterSerialNo" filterable="true" width="115px">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function typeOfServiceFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Meter Serial No",                                            
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${commonFilterForMtersUrl}/meterSerialNo"
										}
									}
								});    
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
			</kendo:grid-column>
						
			<kendo:grid-column title="Meter&nbsp;Type *"  field="meterType" filterable="true" width="110px" editor="dropDownChecksEditorMeterType">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function typeOfServiceFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Meter Type",                                            
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${commonFilterForMtersUrl}/meterType"
										}
									}
								});    
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
			</kendo:grid-column>

			<kendo:grid-column title="Meter&nbsp;Ownership&nbsp;*"  field="meterOwnerShip" filterable="true" width="125px" editor="dropDownChecksEditor">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function typeOfServiceFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Meter Ownership",                                            
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${commonFilterForMtersUrl}/meterOwnerShip"
										}
									}
								});    
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="Current&nbsp;Sp&nbsp;No&nbsp;*" field="servicePointId" hidden="true" filterable="true" width="115px">
			</kendo:grid-column>
			
			<kendo:grid-column title="Meter&nbsp;Account&nbsp;No&nbsp;*" field="accountId" hidden="true" filterable="true" width="130px">
			</kendo:grid-column>
												
			<kendo:grid-column title="Current&nbsp;Status" field="meterStatus" width="90px" editor="dropDownChecksEditor">
				<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function typeOfServiceFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Meter Status",                                            
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${commonFilterForMtersUrl}/meterStatus"
										}
									}
								});    
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    	</kendo:grid-column-filterable>
			</kendo:grid-column> 
			
			 <kendo:grid-column title="&nbsp;" width="160px" >
							            	<kendo:grid-column-command>
							            		<kendo:grid-column-commandItem name="edit"/>
							            		<kendo:grid-column-commandItem name="destroy"/>
							            	</kendo:grid-column-command>
							            </kendo:grid-column>
		</kendo:grid-columns>
		
		<kendo:dataSource pageSize="20" requestEnd="onRequestEnd" requestStart="onRequestStart">
			<kendo:dataSource-transport>
			<kendo:dataSource-transport-create url="${electricityMetertCreateUrl}"
					dataType="json" type="GET" contentType="application/json" />
				<kendo:dataSource-transport-read
					url="${elMeterReadUrl}" dataType="json"
					type="POST" contentType="application/json" />
				<kendo:dataSource-transport-update url="${electricityMeterUpdateUrl}"
					dataType="json" type="GET" contentType="application/json" />
			    <kendo:dataSource-transport-destroy url="${electricityMeterDestroyUrl}"
					dataType="json" type="GET" contentType="application/json" />
			
			</kendo:dataSource-transport>
			
			<kendo:dataSource-schema parse="parseElectricityMeter">
				<kendo:dataSource-schema-model id="elMeterId">
					<kendo:dataSource-schema-model-fields>

						<kendo:dataSource-schema-model-field name="elMeterId" type="number">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="personName" type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="accountNo" type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="property_No" type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="meterStatus" type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="typeOfServiceForMeters" type="string">
							<kendo:dataSource-schema-model-field-validation required="true"/>
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="meterSerialNo" type="string">
						</kendo:dataSource-schema-model-field>

						<kendo:dataSource-schema-model-field name="meterType" type="string" defaultValue="Analog Meter">
						</kendo:dataSource-schema-model-field>

						<kendo:dataSource-schema-model-field name="meterOwnerShip" type="string">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="servicePointId" type="number">
							<kendo:dataSource-schema-model-field-validation />
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="accountId" type="number">
							<kendo:dataSource-schema-model-field-validation />
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
	
	<kendo:grid-detailTemplate id="electricityMetersTemplate">
		<kendo:tabStrip name="tabStrip_#=elMeterId#">
			<kendo:tabStrip-items>
			
 			<kendo:tabStrip-item selected="true" text="Meter Parameters">
                <kendo:tabStrip-item-content>
                    <div class='wethear'>
						    <br/>
							<kendo:grid name="elMeterParametersEvent_#=elMeterId#" remove="meterParameterDeleteEvent" pageable="true" dataBound="onDataBoundParameters"
								resizable="true" sortable="true" reorderable="true"
								selectable="true" scrollable="true" edit="elMeterParametersEvent" editable="true" >
								<kendo:grid-pageable pageSize="10"></kendo:grid-pageable>
								<kendo:grid-editable mode="popup"/>
						       <kendo:grid-toolbar >
						            <kendo:grid-toolbarItem name="create" text="Add New Parameter" />
						        </kendo:grid-toolbar> 
        						<kendo:grid-columns>
        						    <kendo:grid-column title="MeterParameterId" field="elMeterParameterId" hidden="true" width="100px">
									</kendo:grid-column> 
									
									<kendo:grid-column title="MeterId" field="elMeterId" hidden="true" width="100px">
									</kendo:grid-column>
									
									<kendo:grid-column title="Sequence&nbsp;*" field="elMeterParameterSequence" format="{0:n0}" width="125px">
									</kendo:grid-column>
	    		                    
	    		                    <kendo:grid-column title="Parameter&nbsp;Name&nbsp;*"  field="mpmName" filterable="false" width="110px">
			                        </kendo:grid-column>
			
			                        <kendo:grid-column title="Parameter&nbsp;Name&nbsp;*" field="mpmId" filterable="false" hidden="true" width="110px" editor="meterParameterEditor">
			                        </kendo:grid-column>
																		
									<kendo:grid-column title="Master&nbsp;ParameterId&nbsp;*" field="elMasterParameterId" hidden="true" format="{0:n0}" width="130px" filterable="true">
									</kendo:grid-column> 
									
									<kendo:grid-column title="Parameter&nbsp;DataType&nbsp;*" field="elMasterParameterDataType" hidden="true" width="140px" filterable="true" editor="dropDownChecksEditor">
									<kendo:grid-column-filterable>
	    			                <kendo:grid-column-filterable-ui>
    					            <script> 
							             function typeOfServiceFilter(element) {
								                   element.kendoAutoComplete({
									               placeholder : "Enter Datatype",                                            
									               dataType: 'JSON',
									               dataSource: {
										           transport: {
											       read: "${commonFilterForMeterParameterUrl}/elMasterParameterDataType"
										                       }
									                          }
								                             });    
					  		                                }
					  	            </script>		
	    			                </kendo:grid-column-filterable-ui>
	    		                    </kendo:grid-column-filterable>
									</kendo:grid-column>
									
									<kendo:grid-column title="Parameter&nbsp;Value&nbsp;*" field="elMeterParameterValue" width="140px" filterable="true">
									<kendo:grid-column-filterable>
	    			                <kendo:grid-column-filterable-ui>
    					            <script> 
							             function typeOfServiceFilter(element) {
								                   element.kendoAutoComplete({
									               placeholder : "Enter value",                                            
									               dataType: 'JSON',
									               dataSource: {
										           transport: {
											       read: "${commonFilterForMeterParameterUrl}/elMeterParameterValue"
										                       }
									                          }
								                             });    
					  		                                }
					  	            </script>		
	    			                </kendo:grid-column-filterable-ui>
	    		                    </kendo:grid-column-filterable>
									</kendo:grid-column>
									
									<kendo:grid-column title="Notes" field="notes" width="100px" editor="elMeterParameterNotesEditor" filterable="true">
									<kendo:grid-column-filterable>
	    			                <kendo:grid-column-filterable-ui>
    					            <script> 
							             function typeOfServiceFilter(element) {
								                   element.kendoAutoComplete({
									               placeholder : "Enter Notes",                                            
									               dataType: 'JSON',
									               dataSource: {
										           transport: {
											       read: "${commonFilterForMeterParameterUrl}/notes"
										                       }
									                          }
								                             });    
					  		                                }
					  	            </script>		
	    			                </kendo:grid-column-filterable-ui>
	    		                    </kendo:grid-column-filterable>
									</kendo:grid-column> 
									
									<kendo:grid-column title="Status&nbsp;*" field="status" width="80px" >
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
									
			                    	<%-- <kendo:grid-column title="Created By" field="createdBy" width="90px" filterable="true">
			                    	<kendo:grid-column-filterable>
	    			                <kendo:grid-column-filterable-ui>
    					            <script> 
							             function typeOfServiceFilter(element) {
								                   element.kendoAutoComplete({
									               placeholder : "Enter Created Name",                                            
									               dataType: 'JSON',
									               dataSource: {
										           transport: {
											       read: "${commonFilterForMeterParameterUrl}/createdBy"
										                       }
									                          }
								                             });    
					  		                                }
					  	            </script>		
	    			                </kendo:grid-column-filterable-ui>
	    		                    </kendo:grid-column-filterable>
			                        </kendo:grid-column> --%>
																		     
        								 <kendo:grid-column title="&nbsp;" width="130px" >
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
        						
        						  <kendo:dataSource requestEnd="elMeterParameterOnRequestEnd" requestStart="elMeterParameterOnRequesStart">
						            <kendo:dataSource-transport>
						            <kendo:dataSource-transport-read url="${meterParameterReadUrl}/#=elMeterId#" dataType="json" type="POST" contentType="application/json"/>
						            <kendo:dataSource-transport-create url="${meterParameterCreateUrl}/#=elMeterId#" dataType="json" type="GET" contentType="application/json" />
						            <kendo:dataSource-transport-update url="${meterParameterUpdateUrl}/#=elMeterId#" dataType="json" type="GET" contentType="application/json" />
						            <kendo:dataSource-transport-destroy url="${meterParameterDestroyUrl}" dataType="json" type="GET" contentType="application/json" />
						            </kendo:dataSource-transport>
						            
						            <kendo:dataSource-schema>
						                <kendo:dataSource-schema-model id="elMeterParameterId">
						                    <kendo:dataSource-schema-model-fields>
						                    
						                    <kendo:dataSource-schema-model-field name="elMeterParameterId" type="number">
											<kendo:dataSource-schema-model-field-validation  />
											</kendo:dataSource-schema-model-field>
											
											 <kendo:dataSource-schema-model-field name="elMeterId" type="number">
											<kendo:dataSource-schema-model-field-validation  />
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="mpmId" type="number">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="mpmName" type="string">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="elMasterParameterId" type="number">
											<kendo:dataSource-schema-model-field-validation min="1"/>
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="elMasterParameterDataType" type="string">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="elMeterParameterValue" type="string">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="notes" type="string">
											<kendo:dataSource-schema-model-field-validation required="true"/>
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="elMeterParameterSequence" type="number" defaultValue="">
											<kendo:dataSource-schema-model-field-validation min="1"/>
											</kendo:dataSource-schema-model-field>
																
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
                         
            <kendo:tabStrip-item selected="false" text="Meter Locations">
                <kendo:tabStrip-item-content>
                    <div class='wethear'>
						    <br/>
							<kendo:grid name="elMeterLocationsEvent_#=elMeterId#" remove="meterLocationDeleteEvent" pageable="true"
								resizable="true" sortable="true" reorderable="true"
								selectable="true" scrollable="true" edit="elMeterLocationsEvent" change="onChangeMeterLocationEvent" editable="true" dataBound="meterLocationDataBound">
								<kendo:grid-pageable pageSize="10"></kendo:grid-pageable>
								<kendo:grid-editable mode="popup"/>
						       <kendo:grid-toolbar >
						            <kendo:grid-toolbarItem name="create" text="Add New Location" />
						        </kendo:grid-toolbar> 
        						<kendo:grid-columns>
        							<kendo:grid-column title="LocationId" field="elMeterLocationId" hidden="true" width="70px" />	
        							
        							<kendo:grid-column title="MeterId" field="elMeterId" hidden="true" width="100px">
									</kendo:grid-column>

									<kendo:grid-column title="Account&nbsp;Number&nbsp;*" field="accountNo" width="130px" filterable="true">
									</kendo:grid-column>

									<kendo:grid-column title="Account&nbsp;Number&nbsp;*" field="accountId" hidden="true" width="140px" filterable="true" editor="accountNumberAutocomplete">
	    							</kendo:grid-column>
																	
									<%-- <kendo:grid-column title="Service&nbsp;Type&nbsp;*" field="servicePointId" width="100px" filterable="true">
									<kendo:grid-column-filterable>
	    			                <kendo:grid-column-filterable-ui>
    					            <script> 
							             function typeOfServiceFilter(element) {
								                   element.kendoAutoComplete({
									               placeholder : "Enter Instruction",                                            
									               dataType: 'JSON',
									               dataSource: {
										           transport: {
											       read: "${commonFilterForMeterLocationUrl}/createdBy"
										                       }
									                          }
								                             });    
					  		                                }
					  	            </script>		
	    			                </kendo:grid-column-filterable-ui>
	    		                    </kendo:grid-column-filterable>
									<kendo:grid-column-values value="${servicePoints}"/>
									</kendo:grid-column>  --%>
									
									<%-- <kendo:grid-column title="Service&nbsp;Type&nbsp;*" field="typeOfService" width="130px" filterable="false">
									<kendo:grid-column-filterable>
	    			                <kendo:grid-column-filterable-ui>
    					            <script> 
							             function typeOfServiceFilter(element) {
								                   element.kendoAutoComplete({
									               placeholder : "Enter Servicetype",                                            
									               dataType: 'JSON',
									               dataSource: {
										           transport: {
											       read: "${commonFilterForMeterLocationUrl}/typeOfService"
										                       }
									                          }
								                             });    
					  		                                }
					  	            </script>		
	    			                </kendo:grid-column-filterable-ui>
	    		                    </kendo:grid-column-filterable>
									</kendo:grid-column>
									<kendo:grid-column title="Service&nbsp;Type&nbsp;*" field="servicePointId" editor="serviceTypeEditor" filterable="false" width="0px" hidden="true">
            						</kendo:grid-column> --%>									
									
									<kendo:grid-column title="Meter&nbsp;Fixed&nbsp;Date&nbsp;*" field="meterFixedDate" format="{0:dd/MM/yyyy}" width="100px">
									</kendo:grid-column>
									
									<kendo:grid-column title="Meter Fixed By" field="meterFixedBy" width="130px" filterable="true">
									<kendo:grid-column-filterable>
	    			                <kendo:grid-column-filterable-ui>
    					            <script> 
							             function typeOfServiceFilter(element) {
								                   element.kendoAutoComplete({
									               placeholder : "Enter Fixed by",                                            
									               dataType: 'JSON',
									               dataSource: {
										           transport: {
											       read: "${commonFilterForMeterLocationUrl}/meterFixedBy"
										                       }
									                          }
								                             });    
					  		                                }
					  	            </script>		
	    			                </kendo:grid-column-filterable-ui>
	    		                    </kendo:grid-column-filterable>
									</kendo:grid-column>
									
									<kendo:grid-column title="Intial&nbsp;Reading&nbsp*" field="intialReading" format="{0:0.00}" width="110px" filterable="true">
								    </kendo:grid-column>
			                        
			                        <kendo:grid-column title="Final&nbsp;Reading&nbsp*" field="finalReading" format="{0:0.00}" width="110px" filterable="true">
			                        </kendo:grid-column>
			                        
			                        
			                        <kendo:grid-column title="DG&nbsp;Initial&nbsp;Reading&nbsp*" field="dgIntitalReading" format="{0:0.00}" width="110px" filterable="true">
			                        </kendo:grid-column>
			                        
			                        <kendo:grid-column title="DG&nbsp;Final&nbsp;Reading&nbsp*" field="dgFinalReading" format="{0:0.00}" width="110px" filterable="true">
			                        </kendo:grid-column>
			                        
			                        <kendo:grid-column title="Release&nbsp;Date" field="meterReleaseDate" format="{0:dd/MM/yyyy}" width="100px">
									</kendo:grid-column>
									
									<kendo:grid-column title="Current&nbsp;Status&nbsp;*" field="locationStatus" hidden="true" width="100px" filterable="true" editor="dropDownChecksEditor">
									<kendo:grid-column-filterable>
	    			                <kendo:grid-column-filterable-ui>
    					            <script> 
							             function typeOfServiceFilter(element) {
								                   element.kendoAutoComplete({
									               placeholder : "Enter Status",                                            
									               dataType: 'JSON',
									               dataSource: {
										           transport: {
											       read: "${commonFilterForMeterLocationUrl}/locationStatus"
										                       }
									                          }
								                             });    
					  		                                }
					  	            </script>		
	    			                </kendo:grid-column-filterable-ui>
	    		                    </kendo:grid-column-filterable>
									</kendo:grid-column> 
									
			                    	<%-- <kendo:grid-column title="Created By" field="createdBy" width="120px" filterable="true">
			                    	<kendo:grid-column-filterable>
	    			                <kendo:grid-column-filterable-ui>
    					            <script> 
							             function typeOfServiceFilter(element) {
								                   element.kendoAutoComplete({
									               placeholder : "Enter Created Name",                                            
									               dataType: 'JSON',
									               dataSource: {
										           transport: {
											       read: "${commonFilterForMeterLocationUrl}/createdBy"
										                       }
									                          }
								                             });    
					  		                                }
					  	            </script>		
	    			                </kendo:grid-column-filterable-ui>
	    		                    </kendo:grid-column-filterable>
			                        </kendo:grid-column> --%>


								<kendo:grid-column title="&nbsp;" width="160px">
									<kendo:grid-column-command>
										<kendo:grid-column-commandItem name="edit" />
										<kendo:grid-column-commandItem name="destroy" />
									</kendo:grid-column-command>
								</kendo:grid-column>

								<kendo:grid-column title="&nbsp;" width="120px">
									<kendo:grid-column-command>
										<kendo:grid-column-commandItem name="Release Meter"
											click="releaseMeterStatusClick" />
									</kendo:grid-column-command>
								</kendo:grid-column>

							</kendo:grid-columns>
        						
        						  <kendo:dataSource requestEnd="elMeterLocationOnRequestEnd" requestStart="elMeterLocationOnRequestStart">
						            <kendo:dataSource-transport>
						            <kendo:dataSource-transport-read url="${meterLocationsReadUrl}/#=elMeterId#" dataType="json" type="POST" contentType="application/json"/>
						            <kendo:dataSource-transport-create url="${meterLocationCreateUrl}/#=elMeterId#" dataType="json" type="GET" contentType="application/json" />
						            <kendo:dataSource-transport-update url="${meterLocationUpdateUrl}/#=elMeterId#" dataType="json" type="GET" contentType="application/json" />
						            <kendo:dataSource-transport-destroy url="${meterLocationDestroyUrl}" dataType="json" type="GET" contentType="application/json" />
						            </kendo:dataSource-transport>
						            
						            <kendo:dataSource-schema >
						                <kendo:dataSource-schema-model id="elMeterLocationId">
						                    <kendo:dataSource-schema-model-fields>
						                    
						                    <kendo:dataSource-schema-model-field name="elMeterLocationId" type="number">
											<kendo:dataSource-schema-model-field-validation  />
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="elMeterId" type="number">
											<kendo:dataSource-schema-model-field-validation  />
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="dgIntitalReading" type="number">
												<kendo:dataSource-schema-model-field-validation  />
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="dgFinalReading" type="number">
												<kendo:dataSource-schema-model-field-validation  />
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="accountId" type="number" defaultValue="">
											</kendo:dataSource-schema-model-field>

											<kendo:dataSource-schema-model-field name="meterFixedDate" type="date">
											<kendo:dataSource-schema-model-field-validation required="true"/>
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="servicePointId" type="number">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="accountNo" type="string">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="typeOfService" type="string">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="installDate" type="date">
											<kendo:dataSource-schema-model-field-validation required="true"/>
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="meterReleaseDate" type="date">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="meterFixedBy" type="string">
											<kendo:dataSource-schema-model-field-validation />
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="intialReading" type="number">
											<kendo:dataSource-schema-model-field-validation required="true" min="0"/>
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="finalReading" type="number">
											<kendo:dataSource-schema-model-field-validation required="true" min="0"/>
											</kendo:dataSource-schema-model-field>
																
											<kendo:dataSource-schema-model-field name="createdBy">
											</kendo:dataSource-schema-model-field>
						
											<kendo:dataSource-schema-model-field name="locationStatus" type="string">
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
	
<div id="generateReleaseMeterDialog" style="display: none;">
<form id="meterRelease">
<div id="previousDiv" align="center">
<table  style="height: 80px">
	<tr>
		<td>Release Date *</td>
		<td>
		<kendo:datePicker format="dd/MM/yyyy"
									name="releaseDate" id="releaseDate">
								</kendo:datePicker>
		</td>	
		</tr>
		
		<tr>
		<td>Final Reading *</td>
		<td>
		<kendo:numericTextBox name="finalReadingMeter" min="0"
									id="finalReadingMeter"></kendo:numericTextBox>
		</td>
		</tr>
		
		<tr>
		<td>DG&nbsp;Final&nbsp;Reading</td>
		<td>
		<kendo:numericTextBox name="dgFinalReadingMeter" min="0"
									id="dgFinalReadingMeter"></kendo:numericTextBox>
		</td>
		</tr>
</table>

</div>

<div align="center">
<table>
<tr><td class="left" align="center" >
	<button type="submit"  id="calcu" class="k-button" onclick="releaseMeter()">Release</button>
	</td></tr>

</table>
</div>
</form>
</div>	
	
	
<div id="alertsBox" title="Alert"></div>

<script>

$("#grid").on("click",".k-grid-electricityTemplatesDetailsExport", function(e) {
	  window.open("./electricityMeterTemplate/electricityMeterTemplatesDetailsExport");
});	 
$("#grid").on("click",".k-grid-electricityPdfTemplatesDetailsExport", function(e) {
	  window.open("./electricityMeterPdfTemplate/electricityMeterPdfTemplatesDetailsExport");
});	 



	var SelectedRowId = "";
	var type = "";
	var meterStatus = "";
	function onChangeMeter(arg) {
		var gview = $("#grid").data("kendoGrid");
		var selectedItem = gview.dataItem(gview.select());
		SelectedRowId = selectedItem.elMeterId;
		type = selectedItem.typeOfServiceForMeters;
		meterStatus = selectedItem.meterStatus;
		this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
	}
	
	var selectedMeterLocationId = "";
	function onChangeMeterLocationEvent(arg){
		var gview = $("#elMeterLocationsEvent_"+SelectedRowId).data("kendoGrid");
		var selectedItem = gview.dataItem(gview.select());
		selectedMeterLocationId = selectedItem.elMeterLocationId;
	}

	function meterDeleteEvent() {
		securityCheckForActions("./Masters/Meters/destroyButton");
		var conf = confirm("Are u sure want to delete this meter details?");
		if (!conf) {
			$("#grid").data().kendoGrid.dataSource.read();
			throw new Error('deletion aborted');
		}
	}

	var meterNumberArray = [];

	function parseElectricityMeter(response) {
		var data = response;
		meterNumberArray = [];
		for (var idx = 0, len = data.length; idx < len; idx++) {

			var res4 = (data[idx].meterSerialNo);
			meterNumberArray.push(res4);
		}
		return response;
	}
	
	function meterLocationDataBound(e){
		var data = this.dataSource.view(),row;
		var grid = $("#elMeterLocationsEvent_"+SelectedRowId).data("kendoGrid");
	    for (var i = 0; i < data.length; i++) {
	    	var currentUid = data[i].uid;
	        row = this.tbody.children("tr[data-uid='" + data[i].uid + "']");
	        
	        var releaseDate = data[i].meterReleaseDate;
	     	if (releaseDate != null) {
				var currenRow = grid.table.find("tr[data-uid='" + currentUid+ "']");
				
				var releaseButton = $(currenRow).find(".k-grid-ReleaseMeter");
				releaseButton.hide();
			} 
	    }
	}

	function releaseMeterStatusClick() {
		var finalReadingMeter = $("#finalReadingMeter").data("kendoNumericTextBox");
		finalReadingMeter.value("");
		
		
		var dgFinalReadingMeter = $("#dgFinalReadingMeter").data("kendoNumericTextBox");
		dgFinalReadingMeter.value("");
       
		$("#releaseDate").val("");
		
		 
		var releaseDate = $('input[name="releaseDate"]').kendoDatePicker().data("kendoDatePicker");
		var todaysDate = new Date();
		releaseDate.max(todaysDate);

		
		var todcal = $("#generateReleaseMeterDialog");
		todcal.kendoWindow({
			width : '350',
			height : 'auto',
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Release Meter Information"
		}).data("kendoWindow").center().open();

		todcal.kendoWindow("open");
		
		/* var result=securityCheckForActionsForStatus("./CustomerCare/OpenNewTicket/re-OpenTicketStatusButton");	  
		if(result=="success"){ */
		/* $.ajax({
			type : "POST",
			url : "./meters/releaseMeterStatusClick/"+ selectedMeterLocationId,
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
				$('#grid').data('kendoGrid').dataSource.read();
			}
		}); */
		/* } */

	}
	
	function releaseMeter(){
	    var releaseDate = $("#releaseDate").val();
	    var finalReadingMeter = $("#finalReadingMeter").val();
	    var dgFinalReadingMeter = $("#dgFinalReadingMeter").val();	    
	    
		   if(releaseDate==""){
			     alert( "Please Enter Release Date");
			     return false;
		   }
	    
		   if(finalReadingMeter==""){
			     alert( "Please Enter Final Reading");
			     return false;
		   }
		   
	   // var result=securityCheckForActionsForStatus("./Master/Meters/releaseMeterButton");	    
		//if(result=="success"){
			//alert(selectedMeterLocationId);
		$.ajax
		({
			type : "POST",
			url : "./meters/releaseMeterStatusClick",
			dataType:"text",
			data : {
				selectedMeterLocationId : selectedMeterLocationId, 
				releaseDate : releaseDate,
				finalReadingMeter : finalReadingMeter,
				dgFinalReadingMeter : dgFinalReadingMeter
				 },
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
				close();
			}
		  });
		//}
	}

	function close(){
		
		var todcal=$( "#generateReleaseMeterDialog" );
		todcal.kendoWindow({
			width : '350',
			height : 'auto',
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : "Bill Information"
		 }).data("kendoWindow").center().close();
		
		todcal.kendoWindow("close");
	}

	$("#meterRelease").submit(function(e){
	    e.preventDefault();
	});

	// Editors Code Starts
	
	function accountNumberAutocomplete(container, options) {
	  $('<input name="accountNumberEE" id="accountId" data-text-field="accountNo" required="true" validationmessage="Account number is required" data-value-field="accountId" data-bind="value:' + options.field + '"/>')
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
				+ '<span class="k-state-default"><i>#: data.personType #</i></span></td></tr></table>',
			dataSource: {
				transport: {
					read: "${accountNumberAutocomplete}"
				}
			},
			   change : function (e) {
			          if (this.value() && this.selectedIndex == -1) {                    
			               alert("Account No does n't exist");
			               $("#accountId").data("kendoComboBox").value("");
			       }
			  	},
		});
	  $('<span class="k-invalid-msg" data-for="accountNumberEE"></span>').appendTo(container);
	}

	function dropDownChecksEditorForServiceType(container, options) {
		var res = (container.selector).split("=");
		var attribute = res[1].substring(0, res[1].length - 1);

		$(
				'<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '" name = "typeOfServiceForMeters" id = "typeOfServiceForMeters"/>')
				.appendTo(container).kendoDropDownList({
					optionLabel : {
						text : "Select",
						value : "",
					},
					defaultValue : false,
					sortable : true,
					change : onChangeDataType,
					dataSource : {
						transport : {
							read : "${allChecksUrl}/" + attribute,
						}
					}
				});
		$('<span class="k-invalid-msg" data-for="'+attribute+'"></span>')
				.appendTo(container);
	}

	function dropDownChecksEditorMeterType(container, options) {

		var data = [ {
			text : "Analog Meter",
			value : "Electricity"
		}, {
			text : "Electronic Meter",
			value : "Electricity"
		}, {
			text : "Trivector Meter",
			value : "Electricity"
		}, {
			text : "DG Meter",
			value : "Electricity"
		}, {
			text : "Analog Meter",
			value : "Water"
		}, {
			text : "AMR Meter",
			value : "Water"
		}, {
			text : "Analog Meter",
			value : "Gas"
		}, {
			text : "Digital Meter",
			value : "Gas"
		} ];

		$(
				'<input name="Meter Type" data-text-field="text" id="meterType" data-value-field="text" data-bind="value:' + options.field + '" required="true"/>')
				.appendTo(container).kendoDropDownList({

					dataTextField : "text",
					dataValueField : "text",
					cascadeFrom : "typeOfServiceForMeters",
					/* optionLabel : {
					text :  "Select",
					value : "",
					}, */
					defaultValue : true,
					sortable : true,
					dataSource : data
				});
		$('<span class="k-invalid-msg" data-for="Meter Type"></span>')
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
					change : onChangeDataType,
					dataSource : {
						transport : {
							read : "${allChecksUrl}/" + attribute,
						}
					}
				});
		$('<span class="k-invalid-msg" data-for="'+attribute+'"></span>')
				.appendTo(container);
	}
	var dataType = "";
	function onChangeDataType() {
		var value = this.dataItem();
		dataType = value.text;

	}

	function spEquipmentCommentsEditor(container, options) {
		$(
				'<textarea data-text-field="comments" name = "comments" style="width:150px"/>')
				.appendTo(container);
		$('<span class="k-invalid-msg" data-for="Enter Comments"></span>')
				.appendTo(container);
	}

	function elMeterParameterNotesEditor(container, options) {
		$(
				'<textarea data-text-field="notes" name = "notes" style="width:150px"/>')
				.appendTo(container);
		$('<span class="k-invalid-msg" data-for="Enter Notes"></span>')
				.appendTo(container);
	}

	function TowerNames(container, options) {
		$(
				'<select data-text-field="blockName" name="blockNameEE" required ="true" validationmessage="Block Name is required" data-value-field="blockName"  id="blockId" data-bind="value:' + options.field + '"/>')
				.appendTo(container).kendoDropDownList({
					placeholder : "Select BlockName",
					autoBind : false,
					optionLabel : "Select",
					dataSource : {
						transport : {
							read : "${towerNames}"
						}
					}
				});
		$('<span class="k-invalid-msg" data-for="blockNameEE"></span>')
				.appendTo(container);
	}

	function PropertyNumbers(container, options) {
		$(
				'<select data-text-field="property_No" name="property_NoEE" data-value-field="propertyId" required ="true" validationmessage="Property Number is required" id="property_No" data-bind="value:' + options.field + '"/>')
				.appendTo(container).kendoDropDownList({
					placeholder : "Select PropertyNo",
					cascadeFrom : "blockId",
					optionLabel : "Select",
					autoBind : false,
					dataSource : {
						transport : {
							read : "${propertyNum}"
						}
					}
				});
		$('<span class="k-invalid-msg" data-for="property_NoEE"></span>')
				.appendTo(container);
	}

	function equipmentTypeAutocomplete(container, options) {
		$(
				'<input name="equipmentTypeEE" id="equipmentType" required="true" validationmessage="Equipment Type is required" data-text-field="value" data-value-field="name" data-bind="value:' + options.field + '"/>')
				.appendTo(container).kendoAutoComplete({
					dataType : 'JSON',
					dataSource : {
						transport : {
							read : "${getEquipmentTypeList}"
						}
					}
				});
		$('<span class="k-invalid-msg" data-for="equipmentTypeEE"></span>')
				.appendTo(container);
	}

	function serviceTypeEditor(container, options) {
		$(
				'<input name="typeOfService" id="servicePointId" data-text-field="typeOfService" required ="true" validationmessage="Service type is required" data-value-field="servicePointId" data-bind="value:' + options.field + '" />')
				.appendTo(container).kendoDropDownList({
					placeholder : "Select Servicetype",
					autoBind : false,
					optionLabel : "Select",
					dataSource : {
						transport : {
							read : "${getServiceTypesUrl}"
						}
					}
				});

		$('<span class="k-invalid-msg" data-for="typeOfServiceEE"></span>')
				.appendTo(container);
	}

	function dateEditor(container, options) {
		$('<input name="' + options.field + '"/>').appendTo(container)
				.kendoDateTimePicker({
					format : "dd/MM/yyyy hh:mm tt",
					timeFormat : "hh:mm tt"

				});
	}

	// Editors Code Ends

	// Onclick functions

	function meterParameterDeleteEvent() {
		securityCheckForActions("./Masters/Meters/MeterParameters/destroyButton");
		var conf = confirm("Are u sure want to delete this parameter details?");
		if (!conf) {
			$("#elMeterParametersEvent_" + SelectedRowId).data().kendoGrid.dataSource
					.read();
			throw new Error('deletion aborted');
		}
	}

	function meterLocationDeleteEvent() {
		securityCheckForActions("./Masters/Meters/MeterLocation/destroyButton");
		var conf = confirm("Are u sure want to delete this location details?");
		if (!conf) {
			$("#elMeterLocationsEvent_" + SelectedRowId).data().kendoGrid.dataSource
					.read();
			throw new Error('deletion aborted');
		}
	}

	function meterParameterEditor(container, options) {
		$(
				'<input name="mpmName" id="parameterId" data-text-field="mpmName" id="dept_Id" data-value-field="mpmId" validationmessage="Parameter name is required" data-bind="value:' + options.field + '" required="required"/>')
				.appendTo(container).kendoDropDownList({
					placeholder : "Select Parameter Name",
					autoBind : false,
					optionLabel : "Select",
					dataSource : {
						transport : {
							read : "${getMeterParameterUrl}/" + type+"/"+SelectedRowId
						}
					}
				});

		$('<span class="k-invalid-msg" data-for="mpmName"></span>').appendTo(
				container);
	}

	/**************************/
	var vgrid = "";
	function onDataBoundParameters(e) {
		vgrid = $('#elMeterParametersEvent_' + SelectedRowId).data("kendoGrid");
		var items = vgrid.dataSource.data();
		var i = 0;
		this.tbody
				.find("tr td:last-child")
				.each(
						function(e) {
							var item = items[i];
							if (item.status == 'Active') {
								$(
										"<button id='test' class='k-button k-button-icontext' onclick='parameterStatusClick()'>Inactivate</button>")
										.appendTo(this);
							} else {
								$(
										"<button id='test' class='k-button k-button-icontext' onclick='parameterStatusClick()'>Activate</button>")
										.appendTo(this);
							}
							i++;
						});
	}

	function parameterStatusClick() {
		var elMeterParameterId = "";
		var gridAccount = $("#elMeterParametersEvent_" + SelectedRowId).data(
				"kendoGrid");
		var selectedAddressItem = gridAccount.dataItem(gridAccount.select());
		elMeterParameterId = selectedAddressItem.elMeterParameterId;
		var result = securityCheckForActionsForStatus("./Masters/Meters/MeterParameters/activeInactiveButton");
		if (result == "success") {
			$.ajax({
				type : "POST",
				url : "./meterParameter/parameterStatusUpdateFromInnerGrid/"
						+ elMeterParameterId,
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
					$('#elMeterParametersEvent_' + SelectedRowId).data(
							'kendoGrid').dataSource.read();
				}
			});
		}
	}

	/****************************/

	// Grid Click Function Code Starts
	$("#grid")
			.on(
					"click",
					"#temPID",
					function(e) {
						var button = $(this), enable = button.text() == "Activate";
						var widget = $("#grid").data("kendoGrid");
						var dataItem = widget.dataItem($(e.currentTarget)
								.closest("tr"));
						var result = securityCheckForActionsForStatus("./Masters/Meters/activeInactiveButton");
						if (result == "success") {
							if (enable) {
								$.ajax({
									type : "POST",
									url : "./electrictyMeters/metersStatus/"
											+ dataItem.id + "/activate",
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
										button.text('Inactivate');
										$('#grid').data('kendoGrid').dataSource
												.read();
									}
								});
							} else {
								$.ajax({
									type : "POST",
									url : "./electrictyMeters/metersStatus/"
											+ dataItem.id + "/deactivate",
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
										button.text('Activate');
										$('#grid').data('kendoGrid').dataSource
												.read();
									}
								});
							}
						}
					});

	var setApCode = "";
	var flagMeterNumber = "";
	function electricityMeterEvent(e) {
		/***************************  to remove the id from pop up  **********************/
		$('div[data-container-for="elMeterId"]').remove();
		$('label[for="elMeterId"]').closest('.k-edit-label').remove();

		$(".k-edit-field").each(function() {
			$(this).find("#temPID").parent().remove();
		});
		
		$('label[for=personName]').parent().hide();
		$('div[data-container-for="personName"]').hide();

		$('label[for=servicePointId]').parent().hide();
		$('div[data-container-for="servicePointId"]').hide();

		$('label[for=accountId]').parent().hide();
		$('div[data-container-for="accountId"]').hide();

		$('label[for=accountNo]').parent().hide();
		$('div[data-container-for="accountNo"]').hide();

		$('label[for="status"]').parent().hide();
		$('div[data-container-for="status"]').hide();

		$('label[for="createdBy"]').parent().hide();
		$('div[data-container-for="createdBy"]').hide();
		
		$('label[for="property_No"]').parent().hide();
		$('div[data-container-for="property_No"]').hide();

		/************************* Button Alerts *************************/
		if (e.model.isNew()) {
			securityCheckForActions("./Masters/Meters/createButton");	
			
			$('label[for=meterStatus]').parent().remove();
			$('div[data-container-for="meterStatus"]').remove();
			
			$(".k-window-title").text("Add New Meter Details");
			$(".k-grid-update").text("Save");
			flagMeterNumber = true;
		} else {
			securityCheckForActions("./Masters/Meters/updateButton");
			flagMeterNumber = false;
			$('label[for=meterSerialNo]').parent().hide();
			$('div[data-container-for="meterSerialNo"]').hide();
			setApCode = $('input[name="elMeterId"]').val();
			$(".k-window-title").text("Edit Meter Details");
		}
	}

	function clearFilterMeter() {
		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
		var gridStoreIssue = $("#grid").data("kendoGrid");
		gridStoreIssue.dataSource.read();
		gridStoreIssue.refresh();
	}

	/********************** to hide the child table id ***************************/
	function elMeterLocationsEvent(e) {
		$('div[data-container-for="elMeterLocationId"]').remove();
		$('label[for="elMeterLocationId"]').closest('.k-edit-label').remove();

		$('div[data-container-for="typeOfService"]').remove();
		$('label[for="typeOfService"]').closest('.k-edit-label').remove();

		$('div[data-container-for="elMeterId"]').remove();
		$('label[for="elMeterId"]').closest('.k-edit-label').remove();
		
		$('div[data-container-for="meterReleaseDate"]').remove();
		$('label[for="meterReleaseDate"]').closest('.k-edit-label').remove();
		
		$('div[data-container-for="locationStatus"]').remove();
		$('label[for="locationStatus"]').closest('.k-edit-label').remove();
		
		$('div[data-container-for="accountNo"]').remove();
		$('label[for="accountNo"]').closest('.k-edit-label').remove();

		$('div[data-container-for="createdBy"]').remove();
		$('label[for="createdBy"]').closest('.k-edit-label').remove();

		if (e.model.isNew()) {
			securityCheckForActions("./Masters/Meters/MeterLocation/createButton");
			if(meterStatus=="In Stock"){
				$(".k-window-title").text("Add New Location");
				$(".k-grid-update").text("Save");
			}else{
				var grid = $("#elMeterLocationsEvent_" + SelectedRowId).data("kendoGrid");
				grid.cancelChanges();
				alert("You can't change meter,because meter status is "+meterStatus);
			}

		} else {
			securityCheckForActions("./Masters/Meters/MeterLocation/updateButton");
			setApCode = $('input[name="elMeterLocationId"]').val();
			$(".k-window-title").text("Edit Location Details");
		}
	}

	function elMeterParametersEvent(e) {
		$('div[data-container-for="elMeterParameterId"]').remove();
		$('label[for="elMeterParameterId"]').closest('.k-edit-label').remove();

		$('div[data-container-for="elMeterId"]').remove();
		$('label[for="elMeterId"]').closest('.k-edit-label').remove();

		$('div[data-container-for="elMasterParameterId"]').remove();
		$('label[for="elMasterParameterId"]').closest('.k-edit-label').remove();

		$('div[data-container-for="elMasterParameterDataType"]').remove();
		$('label[for="elMasterParameterDataType"]').closest('.k-edit-label')
				.remove();

		$('div[data-container-for="status"]').remove();
		$('label[for="status"]').closest('.k-edit-label').remove();

		$('div[data-container-for="mpmName"]').remove();
		$('label[for="mpmName"]').closest('.k-edit-label').remove();

		$('div[data-container-for="createdBy"]').remove();
		$('label[for="createdBy"]').closest('.k-edit-label').remove();

		if (e.model.isNew()) {
			securityCheckForActions("./Masters/Meters/MeterParameters/createButton");
			$(".k-window-title").text("Add New Meter Parameter");
			$(".k-grid-update").text("Save");

		} else {
			securityCheckForActions("./Masters/Meters/MeterParameters/updateButton");
			setApCode = $('input[name="elMeterParameterId"]').val();
			$('label[for="status"]').parent().hide();
			$('div[data-container-for="status"]').hide();
			$(".k-window-title").text("Edit Meter Parameter Details");
		}
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
	
	function elMeterParameterOnRequesStart(e){
		
				
	$('.k-grid-update').hide();
    $('.k-edit-buttons')
            .append(
                    '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
    $('.k-grid-cancel').hide();
    }
	
	function elMeterLocationOnRequestStart(e){
		
				
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
							"Error: Creating the electricity meter details<br>"
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
							"Error: Updating the electricity meter details<br>"
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

			if (r.response.status == "AciveMeterDestroyError") {
				$("#alertsBox").html("");
				$("#alertsBox").html("In service meter details cannot be deleted");
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
			if (r.response.status == "CHILD") {

				$("#alertsBox").html("");
				$("#alertsBox")
						.html(
								"Can't delete electricity meter details, child record found");
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
							"Error: Creating the electricity meter details<br>"
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
							"Error: Creating the electricity meter details<br>"
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
							"Error: Updating the electricity meter details<br>"
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
						"Electricity meter details created successfully");
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
						"Electricity meter details updated successfully");
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

			else if (r.type == "destroy") {
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Electricity meter details deleted successfully");
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

	function elMeterLocationOnRequestEnd(e) {
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
				$("#alertsBox").html("Location Record Created Successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				var gridPets = $("#elMeterLocationsEvent_" + SelectedRowId)
						.data("kendoGrid");
				gridPets.dataSource.read();
				gridPets.refresh();
				return false;
			}

			else if (e.type == "update") {
				$("#alertsBox").html("");
				$("#alertsBox").html("Location Record updated successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				var gridPets = $("#elMeterLocationsEvent_" + SelectedRowId)
						.data("kendoGrid");
				gridPets.dataSource.read();
				gridPets.refresh();
				return false;
			}

			else if (e.type == "destroy") {
				
				 if (e.response.status == "CHILD") {
       				 
 		  			$("#alertsBox").html("");
 		  			$("#alertsBox").html("Child Exist. Record Cannot be Deleted");
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
    			else{ 
				$("#alertsBox").html("");
				$("#alertsBox").html("Location Record deleted successfully");
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

	}

	function elMeterParameterOnRequestEnd(e) {
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
				$("#alertsBox").html(
						"Meter Parameter Record Created Successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				var gridPets = $("#elMeterParametersEvent_" + SelectedRowId)
						.data("kendoGrid");
				gridPets.dataSource.read();
				gridPets.refresh();
				return false;
			}

			else if (e.type == "update") {
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Meter Parameter Record updated successfully");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				var gridPets = $("#elMeterParametersEvent_" + SelectedRowId)
						.data("kendoGrid");
				gridPets.dataSource.read();
				gridPets.refresh();
				return false;
			}

			else if (e.response.status == "AciveParameterDestroyError") {
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Active parameter details cannot be deleted");
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

			else if (e.type == "destroy") {
				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Meter Parameter Record delete successfully");
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
	// Grid Click Function Code Starts

	//register custom validation rules

	(function($, kendo) {
		$
				.extend(
						true,
						kendo.ui.validator,
						{
							rules : { // custom rules
								/* elMeterParameterValueFloatValidation: function (input, params) 
								{         
								    if (input.filter("[name='elMeterParameterValue']").length && input.val() != "") 
								    {
								   	 return /^[0-9.]{1,45}$/.test(input.val());
								    }        
								    return true;
								},
								elMeterParameterValueStringValidation: function (input, params) 
								{         
								    if (input.filter("[name='elMeterParameterValue']").length && input.val() != "" && dataType=="String") 
								    {
								   	 return /^[a-zA-Z0-9. ]{1,45}$/.test(input.val());
								    }        
								    return true;
								}, */
								elMeterParameterValueRequired : function(input,
										params) {
									if (input.attr("name") == "elMeterParameterValue") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},
								/* elMasterParameterDataTypeRequired : function(input, params){
								    if (input.attr("name") == "elMasterParameterDataType")
								    {
								     return $.trim(input.val()) !== "";
								    }
								    return true;
								   }, */
								elMeterParameterSequenceRequired : function(
										input, params) {
									if (input.attr("name") == "elMeterParameterSequence") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},
								meterTypeRequired : function(input, params) {
									if (input.attr("name") == "meterType") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},
								meterOwnerShipRequired : function(input, params) {
									if (input.attr("name") == "meterOwnerShip") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},
								locationStatusRequired : function(input, params) {
									if (input.attr("name") == "meterStatus") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},
								fixedByvalidation : function(input, params) {
									if (input.filter("[name='meterFixedBy']").length
											&& input.val() != "") {
										return /^[a-zA-Z ]{1,45}$/.test(input
												.val());
									}
									return true;
								},
								meterSerialNoUniquevalidation : function(input,
										params) {
									if (flagMeterNumber) {
										if (input
												.filter("[name='meterSerialNo']").length
												&& input.val()) {
											var flag = true;
											$.each(meterNumberArray, function(
													idx1, elem1) {
												if (elem1 == input.val()) {
													flag = false;
												}
											});
											return flag;
										}
									}
									return true;
								},
								serviceTypeRequired : function(input, params) {
									if (input.attr("name") == "typeOfServiceForMeters") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},
								meterSerialNoRequired : function(input, params) {
									if (input.attr("name") == "meterSerialNo") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},
								meterSerialNumberLengthValidation : function(
										input, params) {
									if (input.filter("[name='meterSerialNo']").length
											&& input.val() != "") {
										return /^[\s\S]{1,45}$/.test(input
												.val());
									}
									return true;
								},
								meterParameterLengthValidation : function(
										input, params) {
									if (input
											.filter("[name='elMeterParameterValue']").length
											&& input.val() != "") {
										return /^[\s\S]{1,45}$/.test(input
												.val());
									}
									return true;
								},
								sequenceLengthValidation : function(input,
										params) {
									if (input
											.filter("[name='elMeterParameterSequence']").length
											&& input.val() != "") {
										return /^[0-9]{1,2}$/.test(input.val());
									}
									return true;
								},
								notesLengthValidation : function(input, params) {
									if (input.filter("[name='notes']").length
											&& input.val() != "") {
										return /^[\s\S]{1,500}$/.test(input
												.val());
									}
									return true;
								},
								initialReadingLengthValidation : function(
										input, params) {
									if (input.filter("[name='intialReading']").length
											&& input.val() != "") {
										return /^[0-9]{1,13}$/
												.test(input.val());
									}
									return true;
								},
								finalReadingLengthValidation : function(input,
										params) {
									if (input.filter("[name='finalReading']").length
											&& input.val() != "") {
										return /^[0-9]{1,13}$/
												.test(input.val());
									}
									return true;
								},
							},
							messages : {
								//custom rules messages
								/* elMeterParameterValueFloatValidation:"Allows only numbers",
								elMeterParameterValueStringValidation:"Allows only alphanumeric characters", */
								elMeterParameterValueRequired : "Parameter value is required",
								/* elMasterParameterDataTypeRequired:"Parameter datatype is required", */
								elMeterParameterSequenceRequired : "Parameter sequence is required",
								meterTypeRequired : "Meter type is required",
								meterOwnerShipRequired : "Meter ownership is required",
								locationStatusRequired : "Current status is required",
								fixedByvalidation : "Fixed by not allow special characters and numbers",
								meterSerialNoUniquevalidation : "Meter number already exists",
								serviceTypeRequired : "Service type is required",
								meterSerialNoRequired : "Meter serial no is required",
								meterSerialNumberLengthValidation : "Meter serial no allows max 45 characters",
								meterParameterLengthValidation : "Parameter value max length 45",
								sequenceLengthValidation : "Sequence max length 2 digit number only",
								notesLengthValidation : "Notes field allows max 500 characters",
								initialReadingLengthValidation : "Intial reading max length 13 digit number only",
								finalReadingLengthValidation : "Final reading max length 13 digit number only"
							}
						});

	})(jQuery, kendo);
	//End Of Validation
</script>
