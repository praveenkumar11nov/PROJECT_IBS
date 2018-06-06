<%@include file="/common/taglibs.jsp"%>

<c:url value="/servicePoints/srread" var="readUrl" />
<c:url value="/servicePoints/srcreate" var="createUrl" />
<c:url value="/servicePoints/srupdate" var="updateUrl" />
<c:url value="/servicePoints/srdelete" var="destroyUrl" />

<c:url value="/asset/getstaff1" var="personUrl1" />

<!-- Create Read Update Delete URL's Of Service Point -->
<c:url value="/servicePoints/ServicePointCreate" var="servicePointCreateUrl" />
<c:url value="/servicePoints/ServicePointRead" var="servicePointReadUrl" />
<c:url value="/servicePoints/ServicePointUpdate" var="servicePointUpdateUrl" />
<c:url value="/servicePoints/ServicePointDestroy" var="servicePointDestroyUrl" />

<!-- Create Read Update Delete URL's Of Service Point Instruction -->
<c:url value="/servicePoints/instructionCreate" var="instructionCreateUrl" />
<c:url value="/servicePoints/instructionRead" var="instructionReadUrl" />
<c:url value="/servicePoints/instructionUpdate" var="instructionUpdateUrl" />
<c:url value="/servicePoints/instructionDestroy" var="instructionDestroyUrl" />

<!-- Create Read Update Delete URL's Of Service Point Equipment-->
<c:url value="/servicePoints/equipmentCreate" var="equipmentCreateUrl" />
<c:url value="/servicePoints/equipmentRead" var="equipmentReadUrl" />
<c:url value="/servicePoints/equipmentUpdate" var="equipmentUpdateUrl" />
<c:url value="/servicePoints/equipmentDestroy" var="equipmentDestroyUrl" />

<!-- Filter URL's Of Service Point Equipment -->
<c:url value="/equipments/filter" var="commonFilterForEquipmentUrl" />
<c:url value="/equipments/getEquipmentTypeList" var="getEquipmentTypeList" />

<!-- Filter URL's Of Service Point -->
<c:url value="/mailroom/readTowerNames" var="towerNames" />
<c:url value="/mailroom/readPropertyNumbers" var="propertyNum" />
<c:url value="/servicePoints/filter" var="commonFilterForServicePointUrl" />
<c:url value="/servicePoints/getBlockNamesList" var="getBlockNames" />
<c:url value="/servicePoints/getPropertyNumList" var="getPropertyNo" />

<c:url value="/common/getAllChecks" var="allChecksUrl" />
<c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />

<kendo:grid name="ServiceRoutGrid" resizable="true" pageable="true" edit="apEvent" change="onChangeServiceRouteList" detailTemplate="servicePointInnerTemplate"
	remove="removeAp" sortable="true" scrollable="true" groupable="true">
	<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10"></kendo:grid-pageable>
	<kendo:grid-filterable extra="false">
		<kendo:grid-filterable-operators>
			<kendo:grid-filterable-operators-string eq="Is equal to" />
		</kendo:grid-filterable-operators>

	</kendo:grid-filterable>
	<kendo:grid-editable mode="popup" />
	<kendo:grid-toolbar>
		<kendo:grid-toolbarItem name="create" text="Add Service Route" />
		<kendo:grid-toolbarItem text="ClearFilter" />
	</kendo:grid-toolbar>
	<kendo:grid-columns>
		<kendo:grid-column title="srId" field="srId" width="110px" hidden="true"/>
		<kendo:grid-column title="Route&nbsp;Name&nbsp;&nbsp;*"
			field="routeName" width="110px">
			<%-- 	<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function apCodeFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Code",
									dataSource : {
										transport : {
											read : "${commonFilterUrl}/apCode"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable> --%>
		</kendo:grid-column>
		<kendo:grid-column title="Sub&nbsp;Route&nbsp;Name&nbsp;*"
			field="subRouteName" width="150px">
			<%-- <kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function apCodeFilter(element) {
								element.kendoDropDownList({
									optionLabel: "Select Type",
									dataSource : {
										transport : {
											read : "${commonFilterUrl}/apType"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui> 
				</kendo:grid-column-filterable>--%>

		</kendo:grid-column>
		<kendo:grid-column title="Read&nbsp;Cycle&nbsp*"
			editor="acesspointTypeEditor" field="readCycle" width="120px">
			<%-- <kendo:grid-column-filterable>
			<kendo:grid-column-filterable-ui>
						<script>
							function apCodeFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Point Name",
									dataSource : {
										transport : {
											read : "${commonFilterUrl}/apName"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
					</kendo:grid-column-filterable> --%>
		</kendo:grid-column>
		<kendo:grid-column title="Staff&nbsp;Name&nbsp;*" field="personId"
			editor="ownerShipEditor1" hidden="true" width="130px">
		</kendo:grid-column>
		<kendo:grid-column title="Staff&nbsp;Name&nbsp;*" field="staffName"
			width="130px">
			<%-- <kendo:grid-column-filterable>
			<kendo:grid-column-filterable-ui>
						<script>
							function apCodeFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Point Location",
									dataSource : {
										transport : {
											read : "${commonFilterUrl}/apLocation"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable> --%>

		</kendo:grid-column>
		<kendo:grid-column title="Read&nbspDay&nbsp;*" field="readDay"
			format="{0:dd/MM/yyyy}" width="110px">
		</kendo:grid-column>

		<kendo:grid-column title="Bill&nbspWindow&nbspFrom&nbsp;Day&nbsp;*"
			field="billFrom" width="155px">
		</kendo:grid-column>
		<kendo:grid-column title="Bill&nbspWindow&nbspTo&nbsp;Day&nbsp;*"
			field="billTo" width="140px">
		</kendo:grid-column>
		<kendo:grid-column title="Estimation&nbspDay&nbsp;*"
			field="estimationDay" width="120px">
		</kendo:grid-column>
		<kendo:grid-column title="Route&nbspDescription&nbsp;*"
			field="routeDescription" width="140px" editor="descEditor" />

		<kendo:grid-column title="&nbsp;" width="160px">
			<kendo:grid-column-command>
				<kendo:grid-column-commandItem name="edit" />
				<kendo:grid-column-commandItem name="destroy" />
			</kendo:grid-column-command>
		</kendo:grid-column>
	</kendo:grid-columns>
	<kendo:dataSource pageSize="20" requestEnd="onRequestEnd">

		<kendo:dataSource-transport>
			<kendo:dataSource-transport-create url="${createUrl}" dataType="json"
				type="GET" contentType="application/json" />
			<kendo:dataSource-transport-read url="${readUrl}" dataType="json"
				type="POST" contentType="application/json" />
			<kendo:dataSource-transport-update url="${updateUrl}" dataType="json"
				type="GET" contentType="application/json" />
			<kendo:dataSource-transport-destroy url="${destroyUrl}/"
				dataType="json" type="GET" contentType="application/json" />
		</kendo:dataSource-transport>
		<kendo:dataSource-schema>
			<kendo:dataSource-schema-model id="srId">
				<kendo:dataSource-schema-model-fields>


					<kendo:dataSource-schema-model-field name="srId" type="number">
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="routeName">
						<kendo:dataSource-schema-model-field-validation required="true" />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="personId" type="number"
						defaultValue="">
						<kendo:dataSource-schema-model-field-validation />
					</kendo:dataSource-schema-model-field>


					<%-- <kendo:dataSource-schema-model-field name="staffName" type="string">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field> --%>

					<kendo:dataSource-schema-model-field name="subRouteName">
						<kendo:dataSource-schema-model-field-validation />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="routeDescription">
						<kendo:dataSource-schema-model-field-validation required="true" />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="readCycle">
						<kendo:dataSource-schema-model-field-validation />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="createdBy">
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="lastupdatedBy">
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="estimationDay"
						type="number">
						<kendo:dataSource-schema-model-field-validation required="true" />
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="billFrom" type="number">
						<kendo:dataSource-schema-model-field-validation required="true" />
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="billTo" type="number">
						<kendo:dataSource-schema-model-field-validation required="true" />
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="readDay" type="date">
						<kendo:dataSource-schema-model-field-validation required="true" />
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="routeDescription">
						<kendo:dataSource-schema-model-field-validation required="true" />
					</kendo:dataSource-schema-model-field>

					<%-- <kendo:dataSource-schema-model-field name="lastUpdatedDate" type="date">
						</kendo:dataSource-schema-model-field> --%>

				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>


		</kendo:dataSource-schema>

	</kendo:dataSource>
</kendo:grid>

<kendo:grid-detailTemplate id="servicePointInnerTemplate">
		<kendo:tabStrip name="tabStrip_#=srId#">
		<kendo:tabStrip-animation>
			</kendo:tabStrip-animation>
			<kendo:tabStrip-items>
			
 			<kendo:tabStrip-item selected="true" text="Service Point">
                <kendo:tabStrip-item-content>
                    <div class='wethear' style='width: 100%'>
						    <br/>
							<kendo:grid name="servicePointGrid_#=srId#" pageable="true"
								resizable="true" sortable="true" reorderable="true"
								selectable="true" scrollable="true" edit="servicePointEvent" editable="true" >
								<kendo:grid-pageable pageSize="10"></kendo:grid-pageable>
								<kendo:grid-filterable extra="false">
			                    <kendo:grid-filterable-operators>
				                    <kendo:grid-filterable-operators-string eq="Is equal to" />
			                    </kendo:grid-filterable-operators>
		                        </kendo:grid-filterable>
								<kendo:grid-editable mode="popup"  confirmation="Are sure you want to delete this item ?"/>
						       <kendo:grid-toolbar >
						            <kendo:grid-toolbarItem name="create" text="Add New Service Point" />
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
			
			<kendo:grid-column title="Created By" field="createdBy" width="100px">
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
			</kendo:grid-column>
			
			 <kendo:grid-column title="&nbsp;" width="160px" >
							            	<kendo:grid-column-command>
							            		<kendo:grid-column-commandItem name="edit"/>
							            		<kendo:grid-column-commandItem name="destroy"/>
							            	</kendo:grid-column-command>
							            </kendo:grid-column>
			
			<%-- <kendo:grid-column title=""
				template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Active#=data.servicePointId#'>#= data.status == 'Active' ? 'Inactivate' : 'Activate' #</a>"
				width="90px" /> --%>
		</kendo:grid-columns>
        						
        						  <kendo:dataSource requestEnd="servicepointOnRequestEnd" >
						           <kendo:dataSource-transport>
			<kendo:dataSource-transport-create url="${servicePointCreateUrl}/#=srId#"
					dataType="json" type="GET" contentType="application/json" />
				<kendo:dataSource-transport-read
					url="${servicePointReadUrl}/#=srId#" dataType="json"
					type="POST" contentType="application/json" />
				<kendo:dataSource-transport-update url="${servicePointUpdateUrl}/#=srId#"
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
                    </div>
                </kendo:tabStrip-item-content>
            </kendo:tabStrip-item>
            
            <kendo:tabStrip-item selected="false" text="Instructions">
                <kendo:tabStrip-item-content>
                    <div class='wethear' style='width: 86%'>
						    <br/>
							<kendo:grid name="spInstructionEvent_#=srId#" pageable="true"
								resizable="true" sortable="true" reorderable="true"
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
									
									<kendo:grid-column title="ServiceRouteID" field="srId" hidden="true" width="100px">
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
						            <kendo:dataSource-transport-read url="${instructionReadUrl}/#=srId#" dataType="json" type="POST" contentType="application/json"/>
						            <kendo:dataSource-transport-create url="${instructionCreateUrl}/#=srId#" dataType="json" type="GET" contentType="application/json" />
						            <kendo:dataSource-transport-update url="${instructionUpdateUrl}/#=srId#" dataType="json" type="GET" contentType="application/json" />
						            <kendo:dataSource-transport-destroy url="${instructionDestroyUrl}" dataType="json" type="GET" contentType="application/json" />
						            </kendo:dataSource-transport>
						            
						            <kendo:dataSource-schema>
						                <kendo:dataSource-schema-model id="spInstructionId">
						                    <kendo:dataSource-schema-model-fields>
						                    
						                    <kendo:dataSource-schema-model-field name="spInstructionId" type="number">
											<kendo:dataSource-schema-model-field-validation  />
											</kendo:dataSource-schema-model-field>
											
											 <kendo:dataSource-schema-model-field name="srId" type="number">
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
							<kendo:grid name="spEquipmentEvent_#=srId#" pageable="true"
								resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true" edit="spEquipmentEvent" editable="true">
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
						            <kendo:dataSource-transport-read url="${equipmentReadUrl}/#=srId#" dataType="json" type="POST" contentType="application/json"/>
						            <kendo:dataSource-transport-create url="${equipmentCreateUrl}/#=srId#" dataType="json" type="GET" contentType="application/json" />
						            <kendo:dataSource-transport-update url="${equipmentUpdateUrl}/#=srId#" dataType="json" type="GET" contentType="application/json" />
						            <kendo:dataSource-transport-destroy url="${equipmentDestroyUrl}" dataType="json" type="GET" contentType="application/json" />
						            </kendo:dataSource-transport>
						            
						            <kendo:dataSource-schema>
						                <kendo:dataSource-schema-model id="spEquipmentId">
						                    <kendo:dataSource-schema-model-fields>
						                    
						                    <kendo:dataSource-schema-model-field name="srId" type="number">
											<kendo:dataSource-schema-model-field-validation  />
											</kendo:dataSource-schema-model-field>
						                    
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


<script>


var SelectedRowId = "";

function onChangeServiceRouteList(arg) {
	 var gview = $("#ServiceRoutGrid").data("kendoGrid");
	 var selectedItem = gview.dataItem(gview.select());
	 SelectedRowId = selectedItem.srId;
	 this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
}



	var res = [];
	var flag = "";
	var apCode = "";
	var count = 0;
	var setApCode = "";
	function acesspointTypeEditor(container, options) {

		var data = [ {
			text : "Monthly",
			value : "Monthly"
		}, {
			text : "ODD Month",
			value : "ODD Month"
		}, {
			text : "Even Month",
			value : "Even Month"
		}, {
			text : "1st Quater",
			value : "1st Quater"
		}, {
			text : "2nd Quater",
			value : "2nd Quater"
		}, {
			text : "2nd Quater",
			value : "2nd Quater"
		}, {
			text : "3rd Quater",
			value : "3rd Quater"
		}, {
			text : "4th Quater",
			value : "4th Quater"
		} ];

		$(
				'<input name="Type" data-text-field="text" id="dept"  data-value-field="value" data-bind="value:' + options.field + '" required="required"/>')
				.appendTo(container).kendoDropDownList({

					dataTextField : "text",
					dataValueField : "value",
					optionLabel : {
						text : "Select",
						value : "",
					},
					defaultValue : false,
					sortable : true,
					dataSource : data
				});
		$('<span class="k-invalid-msg" data-for="apType"></span>').appendTo(
				container);
	}

	function ownerShipEditor1(container, options) {
		$(
				'<input id="staff" name="Staff Name" required="true" data-text-field="pn_Name"  data-value-field="personId" data-bind="value:' + options.field + '"/>')
				.appendTo(container)
				.kendoComboBox(
						{
							placeholder : "Select",
							template : ''
									+ '<span class="k-state-default"><b>#:data.pn_Name#</b></span><br>'
									+ '<span class="k-state-default"><i>#:data.designation#, #:data.department# </i> </span><br>',
							optionLabel : {
								pn_Name : "Select",
								personId : "",
							},
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
		$('<span class="k-invalid-msg" data-for="Staff Name"></span>')
				.appendTo(container);
	}
	function descEditor(container, options) {
		$(
				'<textarea name="Description" data-text-field="apDescription" data-value-field="apDescription" data-bind="value:' + options.field + '" style="width: 148px; height: 75px;" required="true"/>')
				.appendTo(container);
		$('<span class="k-invalid-msg" data-for="Description"></span>')
				.appendTo(container);
	}

	function apEvent(e) {
		$('div[data-container-for="staffName"]').remove();
		$('label[for="staffName"]').closest('.k-edit-label').remove();
		
		$('div[data-container-for="srId"]').remove();
		$('label[for="srId"]').closest('.k-edit-label').remove();
		if (e.model.isNew()) {

			$(".k-window-title").text("Add New Service Route");
			$(".k-grid-update").text("Save");
			
		//	$('div[data-container-for="elrmid"]').remove();
		//	$('label[for="elrmid"]').closest('.k-edit-label').remove();


		} 
		else {
			$(".k-window-title").text("Edit Access Service Route Details");
		}
		/* $.ajax({
			type : "GET",
			url : '${readUrl}',
			dataType : "JSON",
			success : function(response) {
				$.each(response, function(index, value) {
					res.push(value);
				});
			}
		}); */
	}

	function removeAp() {
		
		var conf = confirm("Are u Sure want to delete this Service Route?");
		if (!conf) {
			$('#ServiceRoutGrid').data().kendoGrid.dataSource.read();
			throw new Error('deletion aborted');
		}

	}
	//register custom validation rules
	/* (function($, kendo) {
		$.extend(true, kendo.ui.validator, {
			rules : { // custom rules          	
				apCodeValidation : function(input, params) {
					//check for the name attribute 
					if (input.filter("[name='apCode']").length && input.val()) {
						apCode = input.val();

						$.each(res, function(ind, val) {
							if (setApCode == apCode) {

								setApCode = "";
								return false;
							} else {

								if ((apCode == val)
										&& (apCode.length == val.length)) {
									flag = apCode;
									return false;
								}
							}
						});
					}
					return true;
				},

				apCodeUniqueness : function(input) {
					if (input.filter("[name='apCode']").length && input.val()
							&& flag != "") {
						flag = "";
						return false;
					}
					return true;
				},
				apCode_Empty : function(input, params) {
					if (input.attr("name") == "apCode") {
						return $.trim(input.val()) !== "";
					}
					return true;
				},
				apLocationEmpty : function(input, params) {
					if (input.attr("name") == "apLocation") {
						return $.trim(input.val()) !== "";
					}
					return true;
				},
			},
			messages : { //custom rules messages
				apCodeUniqueness : "Access Point Code Already Exists",
				apCodeValidation : "Invalid Access Point Code",
				apCode_Empty : "Access Point Code is required",
				apLocationEmpty : "Access Point Location is required"

			}
		});

	})(jQuery, kendo);

	$("#ServiceRoutGrid").on("click", ".k-grid-ClearFilter", function() {
		//custom actions

		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
		var grid = $("#ServiceRoutGrid").data("kendoGrid");
		grid.dataSource.read();
		grid.refresh();
	});
 */
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
					alert("Service Route Created Successfully");
				}

				if (r.type == "update") {
					alert("Service Route Updated Successfully");
				}

				$('#ServiceRoutGrid').data('kendoGrid').refresh();
				$('#ServiceRoutGrid').data('kendoGrid').dataSource.read();
				return false;
			}

			if (r.response.status == "CHILD") {

				alert("Can't Delete, Child record found");
				$('#ServiceRoutGrid').data('kendoGrid').refresh();
				$('#ServiceRoutGrid').data('kendoGrid').dataSource.read();
				return false;
			}

			else if (r.response.status == "INVALID") {

				errorInfo = "";

				errorInfo = r.response.result.invalid;

				if (r.type == "create") {

					alert("Service Route Created Successfully");
				}
				$('#ServiceRoutGrid').data('kendoGrid').refresh();
				$('#ServiceRoutGrid').data('kendoGrid').dataSource.read();
				return false;
			}

			else if (r.response.status == "EXCEPTION") {

				errorInfo = "";

				errorInfo = r.response.result.exception;

				if (r.type == "create") {

					alert("Service Route Created Successfully");

				}

				if (r.type == "update") {
					alert("Service Route Updated Successfully");
				}

				$('#ServiceRoutGrid').data('kendoGrid').refresh();
				$('#ServiceRoutGrid').data('kendoGrid').dataSource.read();
				return false;
			}

			else if (r.type == "create") {
				alert("Service Route Created Successfully");
			}

			else if (r.type == "update") {
				alert("Service Route  Updated Successfully");
			}

			else if (r.type == "destroy") {
				alert("Service Route Deleted Successfully");
			}

		}
	}
 
 
 function servicepointOnRequestEnd(e)
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
			
			/* var gridPets = $("#spEquipmentEvent_"+SelectedRowId).data("kendoGrid");
			gridPets.dataSource.read();
			gridPets.refresh();
			return false; */
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
			/* var gridPets = $("#spEquipmentEvent_"+SelectedRowId).data("kendoGrid");
			gridPets.dataSource.read();
			gridPets.refresh();
			return false; */
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
			/* var gridPets = $("#spEquipmentEvent_"+SelectedRowId).data("kendoGrid");
			gridPets.dataSource.read();
			gridPets.refresh(); */
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
 
	
	/*        Service Point Event            */
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
   var gridStoreIssue = $("#ServiceRoutGrid").data("kendoGrid");
   gridStoreIssue.dataSource.read();
   gridStoreIssue.refresh();
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
  
  function spInstructionEvent(e)
  {
  	 $('div[data-container-for="spInstructionId"]').remove();
  	 $('label[for="spInstructionId"]').closest('.k-edit-label').remove();  
  	 
  	 $('div[data-container-for="srId"]').remove();
  	 $('label[for="srId"]').closest('.k-edit-label').remove(); 
  	 
  	 $('div[data-container-for="srId"]').remove();
  	 $('label[for="srId"]').closest('.k-edit-label').remove();
  	 
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
 			var gridPets = $("#spInstructionEvent_"+SelectedRowId).data("kendoGrid");
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
 			var gridPets = $("#spInstructionEvent_"+SelectedRowId).data("kendoGrid");
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
 			var gridPets = $("#spInstructionEvent_"+SelectedRowId).data("kendoGrid");
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
  
  function spInstructionsEditor(container, options) 
	{
 $('<textarea data-text-field="instructions" name = "instructions" style="width:150px"/>')
      .appendTo(container);
 $('<span class="k-invalid-msg" data-for="Enter Instructions"></span>').appendTo(container);
	}

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
  
  function spEquipmentCommentsEditor(container, options) 
	{
     $('<textarea data-text-field="comments" name = "comments" style="width:150px"/>')
          .appendTo(container);
     $('<span class="k-invalid-msg" data-for="Enter Comments"></span>').appendTo(container);
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

  /************* Sub grid instruction status change code starts    *************/
  function onDataBoundInstructionStatus(e) 
  	{
  	    
  	  	var vgrid = $('#spInstructionEvent_'+SelectedRowId).data("kendoGrid");
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
		var gridParameter = $("#spInstructionEvent_"+SelectedRowId).data("kendoGrid");
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
				$('#spInstructionEvent_'+SelectedRowId).data('kendoGrid').dataSource.read();
			}
		});
	}
/************* Sub grid instruction status change code Ends    *************/

</script>