<%@include file="/common/taglibs.jsp"%>

<!-- Create Read Update Delete URL's Of Service Point -->
<c:url value="/servicePoints/ServicePointCreate" var="servicePointCreateUrl" />
<c:url value="/servicePoints/ServicePointRead" var="servicePointReadUrl" />
<c:url value="/servicePoints/ServicePointUpdate" var="servicePointUpdateUrl" />
<c:url value="/servicePoints/ServicePointDestroy" var="servicePointDestroyUrl" />

<!-- Filter URL's Of Service Point -->
<c:url value="/mailroom/readTowerNames" var="towerNames" />
<c:url value="/mailroom/readPropertyNumbers" var="propertyNum" />
<c:url value="/servicePoints/filter" var="commonFilterForServicePointUrl" />
<c:url value="/servicePoints/getBlockNamesList" var="getBlockNames" />
<c:url value="/servicePoints/getPropertyNumList" var="getPropertyNo" />

<!-- Create Read Update Delete URL's Of Service Point Instruction -->
<c:url value="/servicePoints/instructionCreate" var="instructionCreateUrl" />
<c:url value="/servicePoints/instructionRead" var="instructionReadUrl" />
<c:url value="/servicePoints/instructionUpdate" var="instructionUpdateUrl" />
<c:url value="/servicePoints/instructionDestroy" var="instructionDestroyUrl" />

<!-- Filter URL's Of Service Point Instruction -->
<c:url value="/instructions/filter" var="commonFilterForInstructionUrl" />

<!-- Create Read Update Delete URL's Of Service Point Equipment-->
<c:url value="/servicePoints/equipmentCreate" var="equipmentCreateUrl" />
<c:url value="/servicePoints/equipmentRead" var="equipmentReadUrl" />
<c:url value="/servicePoints/equipmentUpdate" var="equipmentUpdateUrl" />
<c:url value="/servicePoints/equipmentDestroy" var="equipmentDestroyUrl" />

<!-- Filter URL's Of Service Point Equipment -->
<c:url value="/equipments/filter" var="commonFilterForEquipmentUrl" />
<c:url value="/equipments/getEquipmentTypeList" var="getEquipmentTypeList" />

<c:url value="/common/getAllChecks" var="allChecksUrl" />
<c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />

 
<kendo:grid name="grid" resizable="true" pageable="true" selectable="true" change="onChangeServicePoint" edit="servicePointEvent" detailTemplate="servicePointInstructionTemplate" sortable="true" scrollable="true" 
		groupable="true">
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10"></kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
			<kendo:grid-filterable-operators>
				<kendo:grid-filterable-operators-string eq="Is equal to" />
			</kendo:grid-filterable-operators>

		</kendo:grid-filterable>
		<kendo:grid-editable mode="popup"
			confirmation="Are you sure you want to remove this Service Point Detail?" />
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Service Point" />
			<kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterServicePoints()><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>"/>
		</kendo:grid-toolbar>
		<kendo:grid-columns>
			
			<kendo:grid-column title="ServicePointId" field="servicePointId" width="110px" hidden="true"/>
			
				<kendo:grid-column title="Block&nbsp;Name&nbsp;*" field="blockName" editor="TowerNames"  width="120px" >
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Block Name",
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getBlockNames}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
	    	</kendo:grid-column>
			
			<kendo:grid-column title="Property Number&nbsp;*" field="propertyId" editor="PropertyNumbers" hidden="true" filterable="false" width="150px">
				<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function propertyNumberFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getPropertyNo}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
	    		</kendo:grid-column>
	    		
	    		<kendo:grid-column title="Property Number&nbsp;*" field="property_No" filterable="false" width="130px">
				<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function propertyNumberFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getPropertyNo}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
	    		</kendo:grid-column>
			
			<%-- <kendo:grid-column title="Block&nbsp;Name&nbsp;*" field="propertyObj.blocks.blockName" width="100px" filterable="true">
			</kendo:grid-column>
			
			<kendo:grid-column title="Property&nbsp;No&nbsp;*" field="propertyObj.property_No" width="100px" filterable="true">
			</kendo:grid-column> --%>
			
			<kendo:grid-column title="Service&nbsp;Type *"  field="typeOfService" filterable="true" width="110px" editor="dropDownChecksEditor">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function typeOfServiceFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Service Type",                                            
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${commonFilterForServicePointUrl}/typeOfService"
										}
									}
								});    
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="Service&nbsp;Point&nbsp;Name&nbsp;*"  field="servicePointName" filterable="true" width="150px">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function typeOfServiceFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Service Type",                                            
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${commonFilterForServicePointUrl}/servicePointName"
										}
									}
								});    
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
			</kendo:grid-column>

			<kendo:grid-column title="Service&nbsp;Metered&nbsp;*"  field="serviceMetered" filterable="true" width="120px" editor="dropDownChecksEditor">
			<kendo:grid-column-filterable >
					<kendo:grid-column-filterable-ui >
						<script>
							function ledgerStatusFilter(element) {
								element
										.kendoDropDownList({
											optionLabel : "Select Metered",
											dataSource : {
												transport : {
													read : "${filterDropDownUrl}/serviceMetered"
												}
											}
										});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable> 
			</kendo:grid-column>
						
			<kendo:grid-column title="Commission&nbsp;Date&nbsp;*" field="commissionDate" format="{0:dd/MM/yyyy}" width="140px">
			</kendo:grid-column>
			
			<kendo:grid-column title="Decommission&nbsp;Date&nbsp;*" field="deCommissionDate" format="{0:dd/MM/yyyy}" width="150px">
			</kendo:grid-column>	
			
			<kendo:grid-column title="Service&nbsp;Point&nbsp;Location&nbsp;*"  field="serviceLocation" filterable="true" width="155px"> 
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function serviceLocationFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Service Location",                                            
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${commonFilterForServicePointUrl}/serviceLocation"
										}
									}
								});    
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
			</kendo:grid-column>	
						
			<kendo:grid-column title="Status" field="status" width="90px" >
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
			
			<%-- <kendo:grid-column title="Created By" field="createdBy" width="100px">
			 <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function propertyCreatedByFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									placeholder : "Enter Created Name",
									dataSource: {
										transport: {
											read: "${commonFilterForServicePointUrl}/createdBy"
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
			
			<kendo:grid-column title=""
				template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Active#=data.servicePointId#'>#= data.status == 'Active' ? 'Inactivate' : 'Activate' #</a>"
				width="90px" />
		</kendo:grid-columns>
		
		<kendo:dataSource pageSize="20" requestEnd="onRequestEnd">
			<kendo:dataSource-transport>
			<kendo:dataSource-transport-create url="${servicePointCreateUrl}"
					dataType="json" type="GET" contentType="application/json" />
				<kendo:dataSource-transport-read
					url="${servicePointReadUrl}" dataType="json"
					type="POST" contentType="application/json" />
				<kendo:dataSource-transport-update url="${servicePointUpdateUrl}"
					dataType="json" type="GET" contentType="application/json" />
			    <kendo:dataSource-transport-destroy url="${servicePointDestroyUrl}"
					dataType="json" type="GET" contentType="application/json" />
			
			</kendo:dataSource-transport>
			
			<kendo:dataSource-schema>
				<kendo:dataSource-schema-model id="servicePointId">
					<kendo:dataSource-schema-model-fields>

						<kendo:dataSource-schema-model-field name="servicePointId" type="number">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="blockName" type="string">
							<kendo:dataSource-schema-model-field-validation />
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="servicePointName" type="string">
							<kendo:dataSource-schema-model-field-validation required="true"/>
						</kendo:dataSource-schema-model-field>

						<kendo:dataSource-schema-model-field name="propertyId" type="number">
							<kendo:dataSource-schema-model-field-validation />
						</kendo:dataSource-schema-model-field>

						<kendo:dataSource-schema-model-field name="typeOfService" type="string">
							<kendo:dataSource-schema-model-field-validation required="true"/>
						</kendo:dataSource-schema-model-field>

						<kendo:dataSource-schema-model-field name="serviceMetered" type="string">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						
						
						<kendo:dataSource-schema-model-field name="commissionDate" type="date">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="deCommissionDate" type="date">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="serviceLocation" type="string">
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
	
	<kendo:grid-detailTemplate id="servicePointInstructionTemplate">
		<kendo:tabStrip name="tabStripg_#=servicePointId#">
		<kendo:tabStrip-animation>
			</kendo:tabStrip-animation>
			<kendo:tabStrip-items>
			
 			<kendo:tabStrip-item selected="true" text="Instructions">
                <kendo:tabStrip-item-content>
                    <div class='wethear' style='width: 86%'>
						    <br/>
							<kendo:grid name="spInstructionEventj_#=servicePointId#" pageable="true"
								resizable="true" sortable="true" reorderable="true" dataBound="onDataBoundInstructionStatus"
								selectable="true" scrollable="true" edit="spInstructionEvent" editable="true" >
								<kendo:grid-pageable pageSize="10"></kendo:grid-pageable>
								<kendo:grid-filterable extra="false">
			                    <kendo:grid-filterable-operators>
				                    <kendo:grid-filterable-operators-string eq="Is equal to" />
			                    </kendo:grid-filterable-operators>
		                        </kendo:grid-filterable>
								<kendo:grid-editable mode="popup"  confirmation="Are sure you want to delete this item ?"/>
						       <kendo:grid-toolbar >
						            <kendo:grid-toolbarItem name="create" text="Add New Instruction" />
						        </kendo:grid-toolbar> 
        						<kendo:grid-columns>
        						    <kendo:grid-column title="spInstructionId" field="spInstructionId" hidden="true" width="100px">
									</kendo:grid-column> 
									
									<kendo:grid-column title="servicePointId" field="servicePointId" hidden="true" width="100px">
									</kendo:grid-column>
									
									<kendo:grid-column title="Instruction&nbsp;Date&nbsp;*" field="instructionDate" format="{0:dd/MM/yyyy}" width="100px" filterable="true"/>
									
									<kendo:grid-column title="Alert&nbsp;*" field="alert" width="80px" editor="dropDownChecksEditor" filterable="true">
									<kendo:grid-column-filterable >
					                <kendo:grid-column-filterable-ui >
						            <script>
							              function ledgerStatusFilter(element) {
								                element
										        .kendoDropDownList({
											     optionLabel : "Select Alert",
											     dataSource : {
												 transport : {
													read : "${filterDropDownUrl}/alert"
												             }
											               }
										               });
							                         }
						             </script>
					                 </kendo:grid-column-filterable-ui>
				                     </kendo:grid-column-filterable>
									</kendo:grid-column> 
									
									<kendo:grid-column title="Instructions" field="instructions" width="150px" editor="spInstructionsEditor" filterable="true">
									<kendo:grid-column-filterable>
	    			                <kendo:grid-column-filterable-ui>
    					            <script> 
							             function typeOfServiceFilter(element) {
								                   element.kendoAutoComplete({
									               placeholder : "Enter Instruction",                                            
									               dataType: 'JSON',
									               dataSource: {
										           transport: {
											       read: "${commonFilterForInstructionUrl}/instructions"
										                       }
									                          }
								                             });    
					  		                                }
					  	            </script>		
	    			                </kendo:grid-column-filterable-ui>
	    		                    </kendo:grid-column-filterable>
									</kendo:grid-column> 
									
									<kendo:grid-column title="Status" field="status" width="80px" >
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
									
			                    	<%-- <kendo:grid-column title="Created&nbsp;By" field="createdBy" width="90px" filterable="true">
			                    	<kendo:grid-column-filterable>
	    			                <kendo:grid-column-filterable-ui>
    					            <script> 
							             function typeOfServiceFilter(element) {
								                   element.kendoAutoComplete({
									               placeholder : "Enter Created Name",                                            
									               dataType: 'JSON',
									               dataSource: {
										           transport: {
											       read: "${commonFilterForInstructionUrl}/createdBy"
										                       }
									                          }
								                             });    
					  		                                }
					  	            </script>		
	    			                </kendo:grid-column-filterable-ui>
	    		                    </kendo:grid-column-filterable>
			                        </kendo:grid-column> --%>
			 						
																		     
        								 <kendo:grid-column title="&nbsp;" width="110px" >
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
											<kendo:grid-column-commandItem name="Change_Status" click="instructionStatusClick" />
										</kendo:grid-column-command>
								        </kendo:grid-column> --%>
							            
        						</kendo:grid-columns>
        						
        						  <kendo:dataSource requestEnd="spInstructionOnRequestEnd" >
						            <kendo:dataSource-transport>
						            <kendo:dataSource-transport-read url="${instructionReadUrl}/#=servicePointId#" dataType="json" type="POST" contentType="application/json"/>
						            <kendo:dataSource-transport-create url="${instructionCreateUrl}/#=servicePointId#" dataType="json" type="GET" contentType="application/json" />
						            <kendo:dataSource-transport-update url="${instructionUpdateUrl}/#=servicePointId#" dataType="json" type="GET" contentType="application/json" />
						            <kendo:dataSource-transport-destroy url="${instructionDestroyUrl}" dataType="json" type="GET" contentType="application/json" />
						            </kendo:dataSource-transport>
						            
						            <kendo:dataSource-schema>
						                <kendo:dataSource-schema-model id="spInstructionId">
						                    <kendo:dataSource-schema-model-fields>
						                    
						                    <kendo:dataSource-schema-model-field name="spInstructionId" type="number">
											<kendo:dataSource-schema-model-field-validation  />
											</kendo:dataSource-schema-model-field>
											
											 <kendo:dataSource-schema-model-field name="servicePointId" type="number">
											<kendo:dataSource-schema-model-field-validation  />
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="instructionDate" type="date">
											<kendo:dataSource-schema-model-field-validation required="true"/>
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="alert" type="string">
											<kendo:dataSource-schema-model-field-validation required="true"/>
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="instructions" type="string">
											<kendo:dataSource-schema-model-field-validation required="true"/>
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
            
            <kendo:tabStrip-item selected="false" text="Equipments">
                <kendo:tabStrip-item-content>
                    <div class='wethear' style="width: 87%">
						    <br/>
							<kendo:grid name="spEquipmentEvent_#=servicePointId#" pageable="true"
								resizable="true" sortable="true" reorderable="true" dataBound="onDataBoundEquipmentStatus"
								selectable="true" scrollable="true" edit="spEquipmentEvent" editable="true">
								<kendo:grid-pageable pageSize="10"></kendo:grid-pageable>
								<kendo:grid-filterable extra="false">
			                    <kendo:grid-filterable-operators>
				                    <kendo:grid-filterable-operators-string eq="Is equal to" />
			                    </kendo:grid-filterable-operators>
		                        </kendo:grid-filterable>
								<kendo:grid-editable mode="popup"  confirmation="Are sure you want to delete this item ?"/>
						       <kendo:grid-toolbar >
						            <kendo:grid-toolbarItem name="create" text="Add New Equipment" />
						        </kendo:grid-toolbar> 
        						<kendo:grid-columns>
        							<kendo:grid-column title="EquipmentId" field="spEquipmentId" hidden="true" width="70px" />									
									<kendo:grid-column title="Equipment&nbsp;Type&nbsp;*" field="equipmentType" width="105px" editor="equipmentTypeAutocomplete">
									<kendo:grid-column-filterable>
	    			                <kendo:grid-column-filterable-ui>
    					            <script> 
							             function typeOfServiceFilter(element) {
								                   element.kendoAutoComplete({
									               placeholder : "Enter Equipment type",                                            
									               dataType: 'JSON',
									               dataSource: {
										           transport: {
											       read: "${commonFilterForEquipmentUrl}/equipmentType"
										                       }
									                          }
								                             });    
					  		                                }
					  	            </script>		
	    			                </kendo:grid-column-filterable-ui>
	    		                    </kendo:grid-column-filterable>
									</kendo:grid-column> 
									<kendo:grid-column title="Equipment&nbsp;Count&nbsp;*" field="equipmentCount" format="{0:n0}" width="120px" /> 
									<kendo:grid-column title="Install&nbsp;Date&nbsp;*" field="installDate" format="{0:dd/MM/yyyy}" width="100px" filterable="true"/>
									<kendo:grid-column title="Removal&nbsp;Date" field="removalDate" format="{0:dd/MM/yyyy}"  width="100px" filterable="true"/>
									<kendo:grid-column title="Comments" field="comments" width="130px" editor="spEquipmentCommentsEditor" filterable="true">
									<kendo:grid-column-filterable>
	    			                <kendo:grid-column-filterable-ui>
    					            <script> 
							             function typeOfServiceFilter(element) {
								                   element.kendoAutoComplete({
									               placeholder : "Enter Comments",                                            
									               dataType: 'JSON',
									               dataSource: {
										           transport: {
											       read: "${commonFilterForEquipmentUrl}/comments"
										                       }
									                          }
								                             });    
					  		                                }
					  	            </script>		
	    			                </kendo:grid-column-filterable-ui>
	    		                    </kendo:grid-column-filterable>
									</kendo:grid-column> 
									<kendo:grid-column title="Status" field="status" width="80px" filterable="true">
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
									
			                    	<%-- <kendo:grid-column title="Created&nbsp;By" field="createdBy" width="90px" filterable="true">
			                    	<kendo:grid-column-filterable>
	    			                <kendo:grid-column-filterable-ui>
    					            <script> 
							             function typeOfServiceFilter(element) {
								                   element.kendoAutoComplete({
									               placeholder : "Enter Created Name",                                            
									               dataType: 'JSON',
									               dataSource: {
										           transport: {
											       read: "${commonFilterForEquipmentUrl}/createdBy"
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
											<kendo:grid-column-commandItem name="Change_Status" click="equipmentStatusClick" />
										</kendo:grid-column-command>
								        </kendo:grid-column> --%>
							            
        						</kendo:grid-columns>
        						
        						  <kendo:dataSource requestEnd="spEquipmentOnRequestEnd" >
						            <kendo:dataSource-transport>
						            <kendo:dataSource-transport-read url="${equipmentReadUrl}/#=servicePointId#" dataType="json" type="POST" contentType="application/json"/>
						            <kendo:dataSource-transport-create url="${equipmentCreateUrl}/#=servicePointId#" dataType="json" type="GET" contentType="application/json" />
						            <kendo:dataSource-transport-update url="${equipmentUpdateUrl}/#=servicePointId#" dataType="json" type="GET" contentType="application/json" />
						            <kendo:dataSource-transport-destroy url="${equipmentDestroyUrl}" dataType="json" type="GET" contentType="application/json" />
						            </kendo:dataSource-transport>
						            
						            <kendo:dataSource-schema>
						                <kendo:dataSource-schema-model id="spEquipmentId">
						                    <kendo:dataSource-schema-model-fields>
						                    
						                    <kendo:dataSource-schema-model-field name="spEquipmentId" type="number">
											<kendo:dataSource-schema-model-field-validation  />
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="installDate" type="date">
											<kendo:dataSource-schema-model-field-validation required="true"/>
											</kendo:dataSource-schema-model-field>

											<kendo:dataSource-schema-model-field name="removalDate" type="date">
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="comments" type="string">
											<kendo:dataSource-schema-model-field-validation />
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="equipmentType" type="string">
											<kendo:dataSource-schema-model-field-validation />
											</kendo:dataSource-schema-model-field>
											
											<kendo:dataSource-schema-model-field name="equipmentCount" type="number">
											<kendo:dataSource-schema-model-field-validation required="true" min="1"/>
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
            
			</kendo:tabStrip-items>
		</kendo:tabStrip>
	</kendo:grid-detailTemplate> 
	
	
<div id="alertsBox" title="Alert"></div>

<script>

//for parsing timestamp 


function parse (response) {   
    $.each(response, function (idx, elem) { 
    	
    	 if(elem.instructionDate=== null){
  		   elem.instructionDate = "";
  	   }else{
  		   elem.instructionDate = kendo.parseDate(new Date(elem.instructionDate),'dd/MM/yyyy HH:mm');
  	   } 
    	 if(elem.installDate=== null){
    		   elem.installDate = "";
    	   }else{
    		   elem.installDate = kendo.parseDate(new Date(elem.installDate),'dd/MM/yyyy HH:mm');
    	   } 
    	 if(elem.removalDate=== null){
    		   elem.removalDate = "";
    	   }else{
    		   elem.removalDate = kendo.parseDate(new Date(elem.removalDate),'dd/MM/yyyy HH:mm');
    	   } 
       });
       return response;
}

function dateEditor(container, options) {
    $('<input name="' + options.field + '" required="true" />')
            .appendTo(container)
            .kendoDateTimePicker({
                format:"dd/MM/yyyy hh:mm tt",
                timeFormat:"hh:mm tt"      	                
    });
}

function instructionDateEditor(container, options) {
    $('<input name="' + options.field + '" required="true" />')
            .appendTo(container)
            .kendoDateTimePicker({
                format:"dd/MM/yyyy hh:mm tt",
                timeFormat:"hh:mm tt"      	                
    });
}

function dateEditorRemovalDate(container, options) {
    $('<input name="' + options.field + '"/>')
            .appendTo(container)
            .kendoDateTimePicker({
                format:"dd/MM/yyyy hh:mm tt",
                timeFormat:"hh:mm tt"      	                
    });
}

// Onclick Functions

var SelectedRowId = "";

function onChangeServicePoint(arg) {
	 var gview = $("#grid").data("kendoGrid");
	 var selectedItem = gview.dataItem(gview.select());
	 SelectedRowId = selectedItem.servicePointId;
	 //this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
    //alert("Selected: " + SelectedRowId);
    
}

/************* Sub grid instruction status change code starts    *************/
function onDataBoundInstructionStatus(e) 
	{
	    
	  	var vgrid = $('#spInstructionEventj_'+SelectedRowId).data("kendoGrid");
	  	var items = vgrid.dataSource.data();
	 	var i = 0;
	 	this.tbody.find("tr td:last-child").each(function (e) 
	   	{
	  	  	var item = items[i];
		   	if(item.status == 'Active')
		   	{
		   		$("<button id='instructionStatus' class='k-button k-button-icontext' onclick='instructionStatusClick()'>Inactivate</button>").appendTo(this);
		   	}
		   	else
		   	{
		   		$("<button id='instructionStatus' class='k-button k-button-icontext' onclick='instructionStatusClick()'>Activate</button>").appendTo(this);
		   	}	
		   	i++;
	   	});
	}
 
  function instructionStatusClick()
	{
		var spInstructionId="";
		var gridParameter = $("#spInstructionEventj_"+SelectedRowId).data("kendoGrid");
		var selectedAddressItem = gridParameter.dataItem(gridParameter.select());
		spInstructionId = selectedAddressItem.spInstructionId;
		$.ajax
		({			
			type : "POST",
			url : "./servicePoints/instructionUpdateFromInnerGrid/"+spInstructionId,
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
				$('#spInstructionEventj_'+SelectedRowId).data('kendoGrid').dataSource.read();
			}
		});
	}
  /************* Sub grid instruction status change code Ends    *************/
  
  /************* Sub grid equipment status change code starts    *************/
  
  function onDataBoundEquipmentStatus(e) 
	{
	    
	  	var vgrid = $('#spEquipmentEvent_'+SelectedRowId).data("kendoGrid");
	  	var items = vgrid.dataSource.data();
	 	var i = 0;
	 	this.tbody.find("tr td:last-child").each(function (e) 
	   	{
	  	  	var item = items[i];
		   	if(item.status == 'Active')
		   	{
		   		$("<button id='equipmentStatus' class='k-button k-button-icontext' onclick='equipmentStatusClick()'>Inactivate</button>").appendTo(this);
		   	}
		   	else
		   	{
		   		$("<button id='equipmentStatus' class='k-button k-button-icontext' onclick='equipmentStatusClick()'>Activate</button>").appendTo(this);
		   	}	
		   	i++;
	   	});
	}
  
  function equipmentStatusClick()
	{
		var spEquipmentId="";
		var gridParameter = $("#spEquipmentEvent_"+SelectedRowId).data("kendoGrid");
		var selectedAddressItem = gridParameter.dataItem(gridParameter.select());
		spEquipmentId = selectedAddressItem.spEquipmentId;
		$.ajax
		({			
			type : "POST",
			url : "./servicePoints/equipmentUpdateFromInnerGrid/"+spEquipmentId,
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
				$('#spEquipmentEvent_'+SelectedRowId).data('kendoGrid').dataSource.read();
			}
		});
	}

  /************* Sub grid equipment status change code ends    *************/

 // Editors Code Starts

function dropDownChecksEditor(container, options) {
		var res = (container.selector).split("=");
		var attribute = res[1].substring(0,res[1].length-1);
		
		$('<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '"required ="true" name = "'+attribute+'" id = "'+attribute+'Id"/>')
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
  
  
  function TowerNames(container, options) 
	 {
			$('<select data-text-field="blockName" name="blockNameEE" required ="true" validationmessage="Block Name is required" data-value-field="blockName"  id="blockId" data-bind="value:' + options.field + '"/>')
	             .appendTo(container)
	             .kendoDropDownList
	             ({
	            	 placeholder: "Select BlockName",
	                 autoBind: false,
	                 optionLabel : "Select",
	                 dataSource: {  
	                     transport:{
	                         read: "${towerNames}"
	                     }
	                 }
	             });
			$('<span class="k-invalid-msg" data-for="blockNameEE"></span>').appendTo(container);
	 }
  
  function PropertyNumbers(container, options) 
	{
			$('<select data-text-field="property_No" name="property_NoEE" data-value-field="propertyId" required ="true" validationmessage="Property Number is required" id="property_No" data-bind="value:' + options.field + '"/>')
	             .appendTo(container).kendoDropDownList
	             ({
	            	 placeholder: "Select PropertyNo",
	            	 cascadeFrom: "blockId",
	            	 optionLabel : "Select",
	                 autoBind: false,
	                 dataSource: {  
	                     transport:{
	                         read: "${propertyNum}"
	                     }
	                 }
	             });
			$('<span class="k-invalid-msg" data-for="property_NoEE"></span>').appendTo(container);
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
					if (enable) 
					{
						$.ajax
						({
							type : "POST",
							url : "./servicePoints/servicePointStatus/" + dataItem.id + "/activate",
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
							url : "./servicePoints/servicePointStatus/" + dataItem.id + "/deactivate",
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
			});
	
	var setApCode = "";
function servicePointEvent(e)
{
	/***************************  to remove the id from pop up  **********************/
	$('div[data-container-for="servicePointId"]').remove();
	$('label[for="servicePointId"]').closest(
	'.k-edit-label').remove();
	
	$(".k-edit-field").each(function () {
		$(this).find("#temPID").parent().remove();  
   	});
	
	$('label[for=property_No]').parent().hide();
	$('div[data-container-for="property_No"]').hide();
	
	$('label[for="status"]').parent().hide();  
	$('div[data-container-for="status"]').hide();
	
	$('label[for="createdBy"]').parent().hide();  
	$('div[data-container-for="createdBy"]').hide();
	
	/************************* Button Alerts *************************/
	if (e.model.isNew()) 
    {
		
		$(".k-window-title").text("Add New Service Point Details");
		$(".k-grid-update").text("Save");		
    }
	else{
		
		setApCode = $('input[name="servicePointId"]').val();
		$(".k-window-title").text("Edit Service Point Details");
	}
}

function clearFilterServicePoints()
{
   $("form.k-filter-menu button[type='reset']").slice().trigger("click");
   var gridStoreIssue = $("#grid").data("kendoGrid");
   gridStoreIssue.dataSource.read();
   gridStoreIssue.refresh();
}


/********************** to hide the child table id ***************************/
function spEquipmentEvent(e)
{
	 $('div[data-container-for="spInstructionId"]').remove();
	 $('label[for="spInstructionId"]').closest('.k-edit-label').remove();  
	 
	 $('div[data-container-for="spEquipmentId"]').remove();
	 $('label[for="spEquipmentId"]').closest('.k-edit-label').remove(); 
	 
	 $('div[data-container-for="test1"]').remove();
	 $('label[for="spEquipmentId"]').closest('.k-edit-label').remove(); 	 
	 
	 $('div[data-container-for="status"]').remove();
	 $('label[for="status"]').closest('.k-edit-label').remove(); 
	 
	 
	 $('div[data-container-for="createdBy"]').remove();
	 $('label[for="createdBy"]').closest('.k-edit-label').remove(); 
	 
		if (e.model.isNew()) 
	    {
			
			$(".k-window-title").text("Add New Equipment");
			$(".k-grid-update").text("Save");
			
	    }
		else{
			
			setApCode = $('input[name="spEquipmentId"]').val();
			$(".k-window-title").text("Edit Equipment Details");
		}
	}
	
function spInstructionEvent(e)
{
	 $('div[data-container-for="spInstructionId"]').remove();
	 $('label[for="spInstructionId"]').closest('.k-edit-label').remove();  
	 
	 $('div[data-container-for="servicePointId"]').remove();
	 $('label[for="servicePointId"]').closest('.k-edit-label').remove(); 
	 
	 $('div[data-container-for="dummy"]').remove();
	 $('label[for="dummy"]').closest('.k-edit-label').remove();
	 
	 $('div[data-container-for="status"]').remove();
	 $('label[for="status"]').closest('.k-edit-label').remove(); 
	 
	 
	 $('div[data-container-for="createdBy"]').remove();
	 $('label[for="createdBy"]').closest('.k-edit-label').remove(); 
	 
		if (e.model.isNew()) 
	    {
			
			$(".k-window-title").text("Add New Instruction");
			$(".k-grid-update").text("Save");
			
			
	    }
		else{
			
			setApCode = $('input[name="spInstructionId"]').val();
			$('label[for="status"]').parent().hide();  
			$('div[data-container-for="status"]').hide();
			$(".k-window-title").text("Edit Instruction Details");
		}
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
						"Error: Creating the Service Point Details<br>" + errorInfo);
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
						"Error: Updating the Service Point Details<br>" + errorInfo);
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
						.html("Can't Delete Service Point Details, Child Record Found");
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
		
		if (r.response.status == "AciveServicePointDestroyError") {
			$("#alertsBox").html("");
			$("#alertsBox").html("Active service point details cannot be deleted");
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
						"Error: Creating the Service Point Details<br>" + errorInfo);
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
						"Error: Creating the Service Point Details<br>" + errorInfo);
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
						"Error: Updating the Service Point Details<br>" + errorInfo);
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

		else if (r.type == "create")
		{
			$("#alertsBox").html("");
			$("#alertsBox").html("Service point details created successfully");
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
			$("#alertsBox")
					.html("Service point details updated successfully");
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
			$("#alertsBox").html("Service point details deleted successfully");
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

function spEquipmentOnRequestEnd(e)
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
			$("#alertsBox").html("Equipment Record Created Successfully");
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
			
			var gridPets = $("#spEquipmentEvent_"+SelectedRowId).data("kendoGrid");
			gridPets.dataSource.read();
			gridPets.refresh();
			return false;
		}

		else if (e.type == "update") 
		{
			$("#alertsBox").html("");
			$("#alertsBox").html("Equipment Record updated successfully");
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
			var gridPets = $("#spEquipmentEvent_"+SelectedRowId).data("kendoGrid");
			gridPets.dataSource.read();
			gridPets.refresh();
			return false;
		}
			
		else if (e.response.status == "AciveEquipmentDestroyError") {
			$("#alertsBox").html("");
			$("#alertsBox").html("Active equipment details cannot be deleted");
			$("#alertsBox").dialog({
				modal : true,
				buttons : {
					"Close" : function() {
						$(this).dialog("close");
					}
				}
			});
			var gridPets = $("#spEquipmentEvent_"+SelectedRowId).data("kendoGrid");
			gridPets.dataSource.read();
			gridPets.refresh();
		return false;
	}	

		else if (e.type == "destroy") 
		{
			$("#alertsBox").html("");
			$("#alertsBox").html("Equipment Record delete successfully");
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
 
 function spInstructionOnRequestEnd(e)
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
			$("#alertsBox").html("Instruction Record Created Successfully");
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
			var gridPets = $("#spInstructionEventj_"+SelectedRowId).data("kendoGrid");
			gridPets.dataSource.read();
			gridPets.refresh();
			return false;
		}

		else if (e.type == "update") 
		{
			$("#alertsBox").html("");
			$("#alertsBox").html("Instruction Record updated successfully");
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
			var gridPets = $("#spInstructionEventj_"+SelectedRowId).data("kendoGrid");
			gridPets.dataSource.read();
			gridPets.refresh();
			return false;
		}
			
		else if (e.response.status == "AciveInstructionDestroyError") {
			$("#alertsBox").html("");
			$("#alertsBox").html("Active instruction details cannot be deleted");
			$("#alertsBox").dialog({
				modal : true,
				buttons : {
					"Close" : function() {
						$(this).dialog("close");
					}
				}
			});
			var gridPets = $("#spInstructionEventj_"+SelectedRowId).data("kendoGrid");
			gridPets.dataSource.read();
			gridPets.refresh();
		return false;
	}	

		else if (e.type == "destroy") 
		{
			$("#alertsBox").html("");
			$("#alertsBox").html("Instruction Record deleted successfully");
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
	(function ($, kendo) 
		{   	  
	    $.extend(true, kendo.ui.validator, 
	    {
	        
	rules : { // custom rules

								commissionDateValidation : function(input,params) {
									if (input.filter("[name = 'commissionDate']").length && input.val()) {
										var selectedDate = input.val();
										var todaysDate = $.datepicker.formatDate('dd/mm/yy',new Date());
										var flagDate = false;

										if ($.datepicker.parseDate('dd/mm/yy',selectedDate) < $.datepicker.parseDate('dd/mm/yy',todaysDate)) {
											flagDate = true;
										}
										return flagDate;
									}
									return true;
								},
								deCommissionDateValidation : function(input,params) {
									if (input.filter("[name = 'deCommissionDate']").length && input.val()) {
										var selectedDate = input.val();
										var todaysDate = $.datepicker.formatDate('dd/mm/yy',new Date());
										var flagDate = false;

										if ($.datepicker.parseDate('dd/mm/yy',selectedDate) >= $.datepicker.parseDate('dd/mm/yy',todaysDate)) {
											flagDate = true;
										}
										return flagDate;
									}
									return true;
								},
								
								installDateBlankValidation: function (input, params) {
				                     //check for the name attribute 
				                     if (input.attr("name") == "installDate") {
				                    	 return $.trim(input.val()) !== "";
				                     }
				                     return true;
				                 },
				                 
				                 instructionDateValidation : function(input,params) {
										if (input.filter("[name = 'instructionDate']").length && input.val()) {
											var selectedDate = input.val();
											var todaysDate = $.datepicker.formatDate('dd/mm/yy',new Date());
											var flagDate = false;

											if ($.datepicker.parseDate('dd/mm/yy',selectedDate) <= $.datepicker.parseDate('dd/mm/yy',todaysDate)) {
												flagDate = true;
											}
											return flagDate;
										}
										return true;
									},

								installDateValidation : function(input,params) {
									if (input.filter("[name = 'installDate']").length && input.val()) {
										var selectedDate = input.val();
										var todaysDate = $.datepicker.formatDate('dd/mm/yy',new Date());
										var flagDate = false;

										if ($.datepicker.parseDate('dd/mm/yy',selectedDate) <= $.datepicker.parseDate('dd/mm/yy',todaysDate)) {
											flagDate = true;
										}
										return flagDate;
									}
									return true;
								},

								removalDateValidation : function(input,params) {
									if (input
											.filter("[name = 'removalDate']").length && input.val()) {
										var selectedDate = input.val();
										var todaysDate = $.datepicker.formatDate('dd/mm/yy',new Date());
										var flagDate = false;

										if ($.datepicker.parseDate('dd/mm/yy',selectedDate) >= $.datepicker.parseDate('dd/mm/yy',todaysDate)) {
											flagDate = true;
										}
										return flagDate;
									}
									return true;
								}

							},
							messages : {
								//custom rules messages
								commissionDateValidation : "Commission date must be past date",
								deCommissionDateValidation : "Decommission date must be future date",
								instructionDateValidation : "Instruction date must be past",
								installDateBlankValidation : "Install date can't be blank",
								installDateValidation : "Install date must be past date",
								removalDateValidation : "Removal date must be future date"
							}
						});

	})(jQuery, kendo);
	//End Of Validation

	
</script>
