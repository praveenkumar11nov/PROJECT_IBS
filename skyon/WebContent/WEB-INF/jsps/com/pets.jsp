<%@include file="/common/taglibs.jsp"%>
 
<c:url value="/pets/read" var="readPetsUrl" />
<c:url value="/pets/create" var="createPetsUrl" />
<c:url value="/pets/update" var="updatePetsUrl" />
<c:url value="/mailroom/readPropertyNames" var="readUrlId"/>
<c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />
<c:url value="/pets/petTypeChecks" var="petTypeUrl" />
<c:url value="/pets/petSizeChecks" var="petSizeUrl" />
<c:url value="/pets/petSexChecks" var="petSexUrl" />
<c:url value="/person/getPropertyNo" var="getPropertyNoUrl" />
<c:url value="/mailroom/readTowerNames" var="towerNames" />
<c:url value="/pets/getBlockNames" var="getBlockNames" />
<c:url value="/mailroom/readPropertyNumbers" var="propertyNum" />
<c:url value="/pets/getPropertyNumList" var="getPropertyNo" />
<c:url value="/pets/getPetNameList" var="petNameUrl"/>
<c:url value="/pets/getPetTypeList" var="petAllTypeUrl"/>
<c:url value="/pets/getPetSizeList" var="petAllSizeUrl"/>
<c:url value="/pets/getPetBreedList" var="petAllBreedUrl"/>
<c:url value="/pets/getPetColorList" var="petAllColorUrl"/>
<c:url value="/pets/getEmergencyContact" var="emergencyContactUrl"/>
<c:url value="/pets/getPetAgeList" var="petAllAgeUrl"/>
<c:url value="/pets/getPetSexList" var="petAllSexUrl"/>
<c:url value="/pets/getlastUpdatedBy" var="getlastUpdatedBy" />
<c:url value="/pets/getCreatedBy" var="getCreatedBy" />
<c:url value="/pets/getVeterianNameList" var="getAllVeterianNameUrl"/>
<c:url value="/kycComplaints/upload/async/save" var="saveUrl" />
<c:url value="/ownerDocument/read" var="OwnerPropertyDRReadUrl" />
<c:url value="/ownerDocument/create" var="OwnerDocumentRepoCreateUrl" />
<c:url value="/ownerDocument/update" var="OwnerDocumentRepoUpdateUrl" />
<c:url value="/family/ownerDocument/delete" var="OwnerDocumentRepoDeleteUrl" />
<c:url value="/documentdefiner/getAllDocument" var="getDocumentType" />
<c:url value="/documentDefiner/getDocumentFormat" var="getDocumentTypeFormat" />
<c:url value="/pets/getPersonListBasedOnPropertyNumbers" var="personNamesAutoBasedOnPersonTypeUrl" />
<c:url value="/pets/getPersonListForFileter" var="personNamesFilterUrl" />

<c:url value="/pets/filter" var="commonFilterForPetsUrl" />

<!-- <div class="wrapper"> -->
		
<kendo:grid name="gridPets" detailTemplate="personTemplate" edit="petEvent" change="onChange" pageable="true" resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true" filterable="true" groupable="true" >
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" ></kendo:grid-pageable>
			<kendo:grid-filterable extra="false">
		 		<kendo:grid-filterable-operators>
		  			<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains"/>
		 		</kendo:grid-filterable-operators>
			</kendo:grid-filterable>
		<kendo:grid-editable mode="popup" />
        <kendo:grid-toolbar >
            <kendo:grid-toolbarItem name="create" text="Add Pet" />
            		  <kendo:grid-toolbarItem name="petsTemplatesDetailsExport" text="Export To Excel" /> 
            		  <kendo:grid-toolbarItem name="petsPdfTemplatesDetailsExport" text="Export To PDF" /> 
            <kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterPets()><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>"/>
        </kendo:grid-toolbar>
       
        <kendo:grid-columns>
				
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
			
			<kendo:grid-column title="Property Number&nbsp;*" field="property_No" editor="PropertyNumbers" hidden="true" filterable="false" width="120px">
				<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function propertyNumberFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Property Number",
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
	    		
	    		<kendo:grid-column title="Property Number&nbsp;*" field="propertyNo"  filterable="false" width="120px">
				<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function propertyNumberFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Property Number",
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
	    		
	    	<kendo:grid-column title="Person&nbsp;Name&nbsp;*" field="personName"  width="120px" >
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
	    	
	    	<kendo:grid-column title="Person&nbsp;Name&nbsp;*" field="drGroupId" editor="PersonNames" filterable="false" width="0px" hidden="true" >
	    	</kendo:grid-column>
	    		
			<kendo:grid-column title="Pet Type&nbsp;*" field="petType" editor="petTypeEditor" width="100px">
			<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function petTypeFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Pet Type",
									dataSource : {
										transport : {
											read : "${commonFilterForPetsUrl}/petType"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="Pet Name&nbsp;*" field="petName" width="100px">
			<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function petNameFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Pet Name",
									dataSource : {
										transport : {
											read : "${commonFilterForPetsUrl}/petName"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="Breed" field="breedName" width="130px" hidden="true">
			<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function petBreedFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Pet Breed",
									dataSource : {
										transport : {
											read : "${commonFilterForPetsUrl}/breedName"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
						
			<kendo:grid-column title="Size" field="petSize" editor="petSizeEditor" width="100px" hidden="true">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function petSizeFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Pet Size",
									dataSource : {
										transport : {
											read : "${commonFilterForPetsUrl}/petSize"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>	
			
			<kendo:grid-column title="Color" field="petColor" width="100px" hidden="true">
			<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function petColorFilter(element) {
								element.kendoAutoComplete({                     
									placeholder : "Enter Pet Color",
									dataSource : {
										transport : {
											read : "${commonFilterForPetsUrl}/petColor"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="Age&nbsp;*" format="{0:n0}" field="petAge" width="100px" filterable="true" hidden="true">
			 <%-- <kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function petAgeFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Pet Age",
									dataSource : {
										transport : {
											read : "${petAllAgeUrl}"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>  --%>
			</kendo:grid-column>
			
			<kendo:grid-column title="Sex&nbsp;*" field="petSex" editor="petSexEditor" width="100px" hidden="true">
					<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function petSexFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Pet Sex",
									dataSource : {
										transport : {
											read : "${commonFilterForPetsUrl}/petSex"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="Last&nbsp;vaccination&nbsp;Date" field="dateOfVaccination"
				format="{0:dd/MM/yyyy}"  width="150px" hidden="true"/>	
			
			<kendo:grid-column title="Vaccinations&nbsp;Type" field="typesOfVaccination" filterable="true" width="130px" editor="typesOfVaccinationEditor">
			<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function petVeterianNameFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Vaccination Type",
									dataSource : {
										transport : {
											read : "${commonFilterForPetsUrl}/typesOfVaccination"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>	
			
			<kendo:grid-column title="Veterinarian&nbsp;Name" field="veterinarianName" width="150px" hidden="true">
			<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function petVeterianNameFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter Veterian Name",
									dataSource : {
										transport : {
											read : "${commonFilterForPetsUrl}/veterinarianName"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column> 
			
			<kendo:grid-column title="Veterinarian&nbsp;Address" field="veterinarianAddress" editor="veterinarianAddressEditor" filterable="false" width="150px" hidden="true">
			</kendo:grid-column>	
			
			<%-- <kendo:grid-column title="Emergency&nbsp;Contact<font color=red>&nbsp;*</font>" field="emergencyCareNumber" filterable="false" editor="telephoneEditor" width="150px" />  --%>
			
			<kendo:grid-column title="Emergency&nbsp;Contact&nbsp;*" field="emergencyCareNumber" filterable="false" width="140px">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script>
							function emergencyContactFilter(element) {
								element.kendoAutoComplete({                     
									placeholder : "Enter Mobile Number",
									dataSource : {
										transport : {
											read : "${emergencyContactUrl}"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			
			<kendo:grid-column title="Pet Status" field="petStatus" width="100px" >
					 <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function petStatusFilter(element) {
								element.kendoDropDownList({
									optionLabel: "Select",
									dataSource : {
										transport : {
											read : "${filterDropDownUrl}/petStatus"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	    	
			</kendo:grid-column> 
						
			<kendo:grid-column title="Created By" field="createdBy" width="150px" hidden="true">
			 <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function propertyCreatedByFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									placeholder : "Enter Created Name",
									dataSource: {
										transport: {
											read : "${commonFilterForPetsUrl}/createdBy"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>
			</kendo:grid-column>
			<kendo:grid-column title="Last Updated By" field="lastUpdatedBy" width="150px" hidden="true">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function propertyLastUpdatedFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									placeholder : "Enter Last Updated Name",
									dataSource: {
										transport: {
											read : "${commonFilterForPetsUrl}/lastUpdatedBy"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	  
			</kendo:grid-column>
			
			<kendo:grid-column title="&nbsp;" width="100px" >
            	<kendo:grid-column-command>
            		<kendo:grid-column-commandItem name="edit"/>
            	</kendo:grid-column-command>
            </kendo:grid-column>
            <kendo:grid-column title=""
				template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Active#=data.petId#'>#= data.petStatus == 'Active' ? 'In-active' : 'Active' #</a>"
				width="100px" />
        </kendo:grid-columns>
        <kendo:dataSource requestEnd="onRequestEndPets" requestStart="onRequestStart">
            <kendo:dataSource-transport>
				<kendo:dataSource-transport-read url="${readPetsUrl}" dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-create url="${createPetsUrl}" dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-update url="${updatePetsUrl}" dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-parameterMap>
					<script>
						function parameterMap(options, type) 
						{
							return JSON.stringify(options);
						}
					</script>
				</kendo:dataSource-transport-parameterMap>
			</kendo:dataSource-transport>
			
			<kendo:dataSource-schema >
				<kendo:dataSource-schema-model id="petId">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="petId"/>
						
						<kendo:dataSource-schema-model-field name="propertyId" type="number">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="property_No" type="number">
							<kendo:dataSource-schema-model-field-validation />
						</kendo:dataSource-schema-model-field>  
						
						<kendo:dataSource-schema-model-field name="emergencyCareNumber" type="string">
						</kendo:dataSource-schema-model-field>  
						
						<kendo:dataSource-schema-model-field name="drGroupId" type="number">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="personId" type="number">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="emergencyCareNumber" type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="personName" type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="petName" type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="petAge" type="number" defaultValue="">
							<kendo:dataSource-schema-model-field-validation min="0"/>
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="petType" type="string">
							 <kendo:dataSource-schema-model-field-validation />
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="breedName" type="string">
							<kendo:dataSource-schema-model-field-validation/>
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="dateOfVaccination" type="date"
							defaultValue="">
							<kendo:dataSource-schema-model-field-validation />
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="typesOfVaccination" type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="blockName" type="string">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="petStatus"
							editable="true" type="string" />
						
						<%--<kendo:dataSource-schema-model-field name="createdBy" type="string">
							 <kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>

						<kendo:dataSource-schema-model-field name="lastUpdatedBy" type="string">
							 <kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						
						 <kendo:dataSource-schema-model-field name="lastUpdatedDate"	type="string">
							 <kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field> --%>
						
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
	</kendo:grid>
	
	<kendo:grid-detailTemplate id="personTemplate">
		<kendo:tabStrip name="tabStrip_#=petId#">
			<kendo:tabStrip-items>
 			<kendo:tabStrip-item text="Documents" selected="true">
                <kendo:tabStrip-item-content>
                    <div class='wethear'>
						    <br/>
							<kendo:grid name="documentsUpload_#=petId#" remove="petDocumentDeleteEvent" pageable="true" edit="documentEvent" change="documentChangeEvent"
								resizable="true" sortable="true" reorderable="true"
								selectable="true" scrollable="true">
								<kendo:grid-pageable pageSize="10"></kendo:grid-pageable>
								<kendo:grid-editable mode="popup"  confirmation="Are sure you want to delete this item ?"/>
						       <kendo:grid-toolbar >
						            <kendo:grid-toolbarItem name="create" text="Add Document" />
						        </kendo:grid-toolbar> 
        						<kendo:grid-columns>
									     <kendo:grid-column title="Document Name&nbsp;*"  field="documentName" editor="documentNameEditor" width="100px"></kendo:grid-column>
									     <kendo:grid-column title="Document Format" field="documentFormat" editor="documentFormatEditor" width="100px"></kendo:grid-column>
									     <kendo:grid-column title="Document Number" field="documentNumber" width="100px"></kendo:grid-column>
									     <kendo:grid-column title="Document Description&nbsp;*" field="documentDescription" editor="documentDecriptionEditor" width="100px"></kendo:grid-column>
        								 <kendo:grid-column title="&nbsp;" width="175px" >
							            	<kendo:grid-column-command>
							            		<kendo:grid-column-commandItem name="edit"/>
							            		<kendo:grid-column-commandItem name="View" click="downloadFile"/>
							            		<kendo:grid-column-commandItem name="Upload" click="uploadNotice" />
							            		<kendo:grid-column-commandItem name="destroy" />
							            	</kendo:grid-column-command>
							            </kendo:grid-column>
        						</kendo:grid-columns>
        						 <kendo:dataSource requestEnd="DocumentOnRequestEnd" requestStart="onRequestStart1">
						            <kendo:dataSource-transport>
						                <kendo:dataSource-transport-read url="${OwnerPropertyDRReadUrl}/#=drGroupId#/Pet" dataType="json" type="POST" contentType="application/json"/>
						                <kendo:dataSource-transport-create url="${OwnerDocumentRepoCreateUrl}/#=drGroupId#/Pet" dataType="json" type="POST" contentType="application/json" />
						                <kendo:dataSource-transport-update url="${OwnerDocumentRepoUpdateUrl}/#=drGroupId#/Pet" dataType="json" type="POST" contentType="application/json" />
						                <kendo:dataSource-transport-destroy url="${OwnerDocumentRepoDeleteUrl}" dataType="json" type="POST" contentType="application/json" />
						               <kendo:dataSource-transport-parameterMap>
						                	<script>
							                	function parameterMap(options,type) 
							                	{
							                		return JSON.stringify(options);	                		
							                	}
						                	</script>
						                 </kendo:dataSource-transport-parameterMap>
						            </kendo:dataSource-transport>
						            <kendo:dataSource-schema>
						                <kendo:dataSource-schema-model id="drId">
						                    <kendo:dataSource-schema-model-fields>
							                    <kendo:dataSource-schema-model-field name="drId"  editable="false" />
						                    	<kendo:dataSource-schema-model-field name="documentFormat" type="string"></kendo:dataSource-schema-model-field>   
						                    	<kendo:dataSource-schema-model-field name="documentName" type="string"></kendo:dataSource-schema-model-field>
						                    	<kendo:dataSource-schema-model-field name="documentNumber" type="string"></kendo:dataSource-schema-model-field>
						                    	<kendo:dataSource-schema-model-field name="documentDescription" type="string">
						                    		<kendo:dataSource-schema-model-field-validation required="true"/>
						                    	</kendo:dataSource-schema-model-field>
						                    </kendo:dataSource-schema-model-fields>
						                 </kendo:dataSource-schema-model>
						             </kendo:dataSource-schema>
						          </kendo:dataSource>
        				</kendo:grid>		
                    </div>
                </kendo:tabStrip-item-content>
            </kendo:tabStrip-item>
            
            
			<kendo:tabStrip-item text="Pet Other Information">
					<kendo:tabStrip-item-content>
						<div class='employee-details'>
							<table>
								<tr>
									
									<td style="vertical-align: top;">
									 <br>
									<h2>Pet Name: #= petName #</h2> 
										<dl>
											<br>
											<table>
												<tr>
													<td><b>Breed</b></td>
													<td>#= breedName#</td>
													<td><b>Size</b></td>
													<td>#= petSize#</td>
												</tr>
												<tr>
													<td><b>Color</b></td>
													<td>#= petColor#</td>
													<td><b>Age</b></td>
													<td>#= petAge#</td>
												</tr>
												<tr>
												<td><b>Sex</b></td>
													<td> #= petSex #</td>
													<td><b>Last vaccination Date</b></td>
													<td>#= dateOfVaccination #</td>
												</tr>

												<tr>													
													<td><b>Veterinarian</b></td>
													<td>#=veterinarianName#</td>
													<td><b>Veterinarian Address</b></td>
													<td>#= veterinarianAddress #</td>
												</tr>
												
												<tr>													
													<td><b>Created By</b></td>
													<td>#=createdBy#</td>
													<td><b>Last Updated By</b></td>
													<td>#= lastUpdatedBy #</td>
												</tr>											
											</table>									
										</dl> 
									</td>
								</tr>
							</table>
						</div>
					</kendo:tabStrip-item-content>
				</kendo:tabStrip-item>
				
			</kendo:tabStrip-items>
		</kendo:tabStrip>
	</kendo:grid-detailTemplate>
 <div id="alertsBox" title="Alert"></div>	
	 <div id="uploadDialog" title="Upload File" style="display: none;">
	<kendo:upload  name="files" multiple="false" upload="uploadExtraDataAlongWithFile" select="filesSelectedtoUpload" success="onDocumentSuccess">
		<kendo:upload-async autoUpload="true" saveUrl="${saveUrl}" />
   </kendo:upload>
</div>
	
	
<!-- -------------------------------- Pets script ------------------------------------------------------------------ -->	
	<script>
	
	 
	  $("#gridPets").on("click",".k-grid-petsTemplatesDetailsExport", function(e) {
		  window.open("./petsTemplate/petsTemplatesDetailsExport");
	  });
	  
	  $("#gridPets").on("click",".k-grid-petsPdfTemplatesDetailsExport", function(e) {
		  window.open("./petsPdfTemplate/petsPdfTemplatesDetailsExport");
	  });
	  
	
	var SelectedRowId = "";
	function onChange(arg) {
		 var gview = $("#gridPets").data("kendoGrid");
	 	 var selectedItem = gview.dataItem(gview.select());
	 	 SelectedRowId = selectedItem.petId;
	 	 this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
	     // alert("Selected: " + SelectedRowId);
	}
	
	function clearFilterPets()
	{
	   $("form.k-filter-menu button[type='reset']").slice().trigger("click");
	   var gridStoreIssue = $("#gridPets").data("kendoGrid");
	   gridStoreIssue.dataSource.read();
	   gridStoreIssue.refresh();
	}
	
	function petEvent(e)
	{
		$(".k-edit-field").each(function () {
			$(this).find("#temPID").parent().remove();
	   	});
		
		$('#tags_typesOfVaccination').tagsInput({});	
		
		$(".k-edit-form-container").css({
			"width" : "700px"
		});
		$(".k-window").css({
			"top": "150px"
		});
		$('.k-edit-label:nth-child(20n+1)').each(
				function(e) {
					$(this).nextAll(':lt(19)').addBack().wrapAll(
							'<div class="wrappers"/>');
				});
		$(".k-grid-Active").hide();
		
		$('label[for=propertyId]').parent().hide();
		$('div[data-container-for="propertyId"]').hide();
		
		$('label[for=propertyNo]').parent().hide();
		$('div[data-container-for="propertyNo"]').hide();
		
		$('label[for=propertyName]').parent().hide();
		$('div[data-container-for="propertyName"]').hide();
		
		$('label[for=petStatus]').parent().hide();
		$('div[data-container-for="petStatus"]').hide();
		
		$('label[for=createdBy]').parent().hide();
		$('div[data-container-for="createdBy"]').hide();
		
		$('label[for=lastUpdatedBy]').parent().hide();
		$('div[data-container-for="lastUpdatedBy"]').hide();
		
		$('label[for="personName"]').parent().hide();  
		$('div[data-container-for="personName"]').hide();
		
		if (e.model.isNew()) 
	    {
			securityCheckForActions("./commanagement/pets/createButton");
			/* var addUrl = "./commanagement/pets/createButton";
			var gridName = "#gridPets";
			addAccess(addUrl, gridName); */
			
			$(".k-window-title").text("Add New Pet record");
			$(".k-grid-update").text("Save");
					
			
	    }
		else{
			securityCheckForActions("./commanagement/pets/updateButton");
			/* var editUrl = "./commanagement/pets/updateButton";
			var gridName = "#gridPets";
			editAccess(editUrl, gridName); */
			
			$(".k-window-title").text("Edit Pet Details");	
			
			}
		$(".k-grid-update").click(function () {
			e.model.set("typesOfVaccination", $('input[name="typesOfVaccination"]').val()); 
		  });
		
		checkValidation();
		
		}
	
	$("#gridPets").on("click", ".k-grid-Clear_Filter", function(){
	    //custom actions
		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
		var gridPets = $("#gridPets").data("kendoGrid");
		gridPets.dataSource.read();
		gridPets.refresh();
	});

/* 	function editPets(e) {
		
		$(".k-window-title").text("Edit Pet Details");		
				
		$(".k-edit-form-container").css({
			"width" : "700px"
		});
		$(".k-window").css({
			"top": "150px"
		});
		
		$('#tags_typesOfVaccination').tagsInput({});	
		
		$('.k-edit-label:nth-child(18n+1)').each(
				function(e) {
					$(this).nextAll(':lt(17)').addBack().wrapAll(
							'<div class="wrappers"/>');
				});

		$(".k-edit-field").each(function () {
			$(this).find("#temPID").parent().remove();
	   	});
		
		$('label[for=propertyId]').parent().hide();
		$('div[data-container-for="propertyId"]').hide();
		
		$('label[for=propertyNo]').parent().hide();
		$('div[data-container-for="propertyNo"]').hide();
		
		$('label[for=createdBy]').parent().hide();
		$('div[data-container-for="createdBy"]').hide();
		
		$('label[for=lastUpdatedBy]').parent().hide();
		$('div[data-container-for="lastUpdatedBy"]').hide();
		
		$('label[for=propertyName]').parent().hide();
		$('div[data-container-for="propertyName"]').hide();
		
		$('label[for=petStatus]').parent().hide();
		$('div[data-container-for="petStatus"]').hide();
		
		$(".k-grid-update").click(function () {
			e.model.set("typesOfVaccination", $('input[name="typesOfVaccination"]').val()); 
		  });
			
		checkValidation();
	} */
	
	
	 function onRequestStart(e){
		$('.k-grid-update').hide();
		$('.k-edit-buttons')
				.append(
						'<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
		$('.k-grid-cancel').hide();
}
	
	 function onRequestStart1(e){
			$('.k-grid-update').hide();
			$('.k-edit-buttons')
					.append(
							'<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
			$('.k-grid-cancel').hide();
	}
	
	
	//CLIENT SIDE VALIDATION FOR DROPDOWNS AND AUTOCOMPLETE

	function checkValidation()
	{
		
$(".k-grid-update").click(function () {	
	
		   /*  var blockname =$('select[data-text-field="blockName"] :selected').text();
	  		if(blockname ==="" || blockname =="Select") 
			{
				alert("Select Valid Block Name");
		 	    return false;	
		    }
		  		
	  	  	var one =$('select[data-text-field="property_No"] :selected').text();
	  		if(one==="" || one =="Select") 
			{
				alert("Select Valid Property Number");
		 	    return false;	
		    }  	 	 */
			  		
		});
	}
	
	var errorMsg = "";
	var flag = "";
	var loginName = "";
	var res = [];
	var flagUserId = "";
	var resContact = [];
	var mobileNumber = "";
	var emailId = "";
	
 	//register custom validation rules
	(function ($, kendo) 
		{   	  
	    $.extend(true, kendo.ui.validator, 
	    {
	         rules: 
	         { // custom rules          	

	        	 petNamevalidation: function (input, params) 
	             {         
	                 if (input.filter("[name='petName']").length && input.val() != "") 
	                 {
	                	 return /^[a-zA-Z ]{1,45}$/.test(input.val());
	                 }        
	                 return true;
	             },           
	             petColorvalidation: function (input, params) 
	             {         
	                 if (input.filter("[name='petColor']").length && input.val() != "") 
	                 {
	                	 return /^[a-zA-Z ]{1,45}$/.test(input.val());
	                 }        
	                 return true;
	             },
	             petBreedvalidation: function (input, params) 
	             {         
	                 if (input.filter("[name='breedName']").length && input.val() != "") 
	                 {
	                	 return /^[a-zA-Z ]{1,45}$/.test(input.val());
	                 }        
	                 return true;
	             },
	             vetrianNamevalidation: function (input, params) 
	             {         
	                 if (input.filter("[name='veterinarianName']").length && input.val() != "") 
	                 {
	                	 return /^[a-zA-Z ]{1,45}$/.test(input.val());
	                 }        
	                 return true;
	             },
	             emergencyContactValidation: function (input, params) 
	             {         
	            	 if (input.attr("name") == "emergencyCareNumber")
                     {
                      return $.trim(input.val()) !== "";
                     }
                     return true;
	             },
	             petAgeValidation: function (input, params) 
	             {         
	            	 if (input.attr("name") == "petAge")
                     {
                      return $.trim(input.val()) !== "";
                     }
                     return true;
	             },	
	             mobileValidation : function(input,
							params) {
						//check for the name attribute 
						if (input
								.filter("[name='emergencyCareNumber']").length
								&& input.val()) {
							mobileNumber = input.val();
							$
									.each(
											resContact,
											function(ind, val) {
												
												if ((mobileNumber == val)
														&& (mobileNumber.length == val.length)) {
													flag = mobileNumber;
												}
											});
							return /^(\d{10})$/
									.test(input.val());
						}
						return true;
					},
					petNameRequiredValidation : function(input, params){
                        if (input.attr("name") == "petName")
                        {
                         return $.trim(input.val()) !== "";
                        }
                        return true;
                       },		

			        mobileUniqueness : function(input) {
						if (input.filter("[name='emergencyCareNumber']").length	&& input.val() && flag != "") {
							flag = "";
							return false;
						}
						return true;
					}
	            },
	         messages: 
	         {
				//custom rules messages
				petNamevalidation: "Name can contain alphabets,spaces and max 45 characters",
				petColorvalidation: "Color can contain alphabets,spaces and max 45 characters",
				petBreedvalidation: "Breed Name can contain alphabets,spaces and max 45 characters",
				vetrianNamevalidation: "Vetrian Name can contain alphabets,spaces and max 45 characters",
				emergencyContactValidation:"Emergency contact is required",
				petAgeValidation:"Pet age is required",
				mobileValidation:"Invalid mobile number, please enter 10 digit number",
				petNameRequiredValidation:"Pet name is required",
				mobileUniqueness : "Mobile Number Already Exists"
			 }
	    });
	    
	})(jQuery, kendo);
	 //End Of Validation
	 
	 function typesOfVaccinationEditor(container, options) {
		$(
					'<input id="tags_typesOfVaccination" class="k-edit-field" name=' + options.field + ' data-bind="value:' + options.field + '" />')
					.appendTo(container);
	}
	 
	 function TowerNames(container, options) 
	 {
			$('<select data-text-field="blockName" name="blockNameYY" data-value-field="blockName" required ="true" validationmessage="Block Name is required" id="blockId" data-bind="value:' + options.field + '"/>')
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
			$('<span class="k-invalid-msg" data-for="blockNameYY"></span>').appendTo(container);
	 }
  
  function PropertyNumbers(container, options) 
	{
			$('<select data-text-field="property_No" name="property_NoYY" data-value-field="propertyId" required ="true" validationmessage="Property Number is required" id="property_No" data-bind="value:' + options.field + '"/>')
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
			$('<span class="k-invalid-msg" data-for="property_NoYY"></span>').appendTo(container);
	}
  
  function PersonNames(container, options) 
  {
  	  
  		$('<input name="Person name" id="hello1" data-text-field="personName" data-value-field="drGroupId" data-bind="value:' + options.field + '" required="true"/>')
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
  				+ '<span class="k-state-default"><i>#: data.persontStyle #</i></span><br>'
  				+ '<span class="k-state-default"><i>#: data.persontType #</i></span></td></tr></table>',
  	         dataSource: {  
  	             transport:{
  	                 read: "${personNamesAutoBasedOnPersonTypeUrl}"
  	             }
  	         }
  			 /* change : function (e) {
  			           if (this.value() && this.selectedIndex == -1) {                    
  			               alert("Person doesn't exist!");
  			               $("#person").data("kendoComboBox").value("");
  			    }
  			 }  */
  		});
  		$('<span class="k-invalid-msg" data-for="Person name"></span>').appendTo(container);
  }
  
	function petTypeEditor(container, options) 
	{
	     $('<input data-text-field="value" id = "typeId" data-value-field="name" name = "Pet type" data-bind="value:' + options.field + '" required="true"/>')
	                .appendTo(container)
	                .kendoDropDownList
	                ({					     
	                    autoBind: false,
	                    optionLabel: "Select",
	                    dataSource: {  
	                        transport:{
	                            read: "${petTypeUrl}"
	                        }
	                    }
	                });
	     $('<span class="k-invalid-msg" data-for="Pet type"></span>').appendTo(container);
	}
	
	function petSizeEditor(container, options) 
	{
	     $('<input data-text-field="value" id = "sizeId" data-value-field="petSize" name = "Pet size" data-bind="value:' + options.field + '"/>')
	                .appendTo(container)
	                .kendoDropDownList
	                ({					     
	                    autoBind: false,
	                    optionLabel: "Select",
	                    dataSource: {  
	                        transport:{
	                            read: "${petSizeUrl}"
	                        }
	                    }
	                });
	     $('<span class="k-invalid-msg" data-for="Pet size"></span>').appendTo(container);
	}
	
	function petSexEditor(container, options) 
	{
	     $('<input data-text-field="value" id = "petSexId" data-value-field="petSex" name = "Pet sex" data-bind="value:' + options.field + '" required="true"/>')
	                .appendTo(container)
	                .kendoDropDownList
	                ({					     
	                    autoBind: false,
	                    optionLabel: "Select",
	                    dataSource: {  
	                        transport:{
	                            read: "${petSexUrl}"
	                        }
	                    }
	                });
	     $('<span class="k-invalid-msg" data-for="Pet sex"></span>').appendTo(container);
	}
	
	function veterinarianAddressEditor(container, options) 
	{
        $('<textarea data-text-field="veterinarianAddress" name = "veterinarianAddress" style="width:150px"/>')
             .appendTo(container);
        $('<span class="k-invalid-msg" data-for="Veterinarian Addresss"></span>').appendTo(container);
	}
	
	$("#gridPets").on("click", "#temPID", function(e) {
		var button = $(this), enable = button.text() == "Active";
		var widget = $("#gridPets").data("kendoGrid");
		var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
		var result=securityCheckForActionsForStatus("./commanagement/pets/activateDeactivateButton"); 
		  if(result=="success"){  
					if (enable) 
					{
						$.ajax
						({
							type : "POST",
							url : "./pets/petStatus/" + dataItem.id + "/activate",
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
								$('#gridPets').data('kendoGrid').dataSource.read();
							}
						});
					} 
					else 
					{
						$.ajax
						({
							type : "POST",
							url : "./pets/petStatus/" + dataItem.id + "/deactivate",
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
								$('#gridPets').data('kendoGrid').dataSource.read();
							}
						});
					}
		         } 
			});
	
	function testValidation(regExp, input, msg){  
	     var OK = regExp.exec(input);  
	     if (!OK)  
		{
	    	 window.alert(msg);
	    	 return false;
		}
	}
	

  requestStart="onRequestStartPets";
  
  function onRequestStartPets(e){
	  
	  var gridPets= $("#gridPets").data("kendoGrid");
	  gridPets.cancelRow();
	} 
	
  onRequestEnd="onRequestEndPets";
	function onRequestEndPets(e) {
		
		var gridPets= $("#gridPets").data("kendoGrid");
		  gridPets.cancelRow();

		if (typeof e.response != 'undefined')
		{
			if (e.response.status == "READ_EXCEPTION") 
			{
				errorInfo = "";
				errorInfo = e.response.result.readException;

				$("#alertsBox").html("");
				$("#alertsBox").html(
						"Error: Loading Pets records<br>" + errorInfo);
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
			
			else if (e.response.status == "FAIL") 
			{
				errorInfo = "";

				for (var i = 0; i < e.response.result.length; i++) 
				{
					errorInfo += (i + 1) + ". "
							+ e.response.result[i].defaultMessage + "<br>";

				}

				if (e.type == "create") {
					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Creating the Pet record<br>" + errorInfo);
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
							"Error: Updating the Pet record<br>" + errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
				}

				var gridPets = $("#gridPets").data("kendoGrid");
				gridPets.dataSource.read();
				gridPets.refresh();
				return false;
			}
			
			else if (e.response.status == "SAVE_OR_UPDATE_EXCEPTION") {

				errorInfo = "";

				errorInfo = e.response.result.saveOrUpdateException;

				if (e.type == "create") {
					//alert("Error: Creating the USER record\n\n" + errorInfo);
					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Creating the Pet record<br>" + errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
				}
				
				if (e.type == "update") {
					//alert("Error: Creating the USER record\n\n" + errorInfo);
					$("#alertsBox").html("");
					$("#alertsBox").html(
							"Error: Updating the Pet record<br>" + errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
				}

				var gridPets = $("#gridPets").data("kendoGrid");
				gridPets.dataSource.read();
				gridPets.refresh();
				return false;
			}
			
			else if (e.response.status == "LOAD_EXCEPTION") 
			{
				if (e.type == "create") 
				{
					$('#gridPets').data().kendoGrid.dataSource.read();
					$("#alertsBox").html("");
					$("#alertsBox").html("Pet record created successfully");
					$('#gridPets').data().kendoGrid.dataSource.read();
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
					$('#gridPets').data().kendoGrid.dataSource.read();
					$("#alertsBox").html("");
					$("#alertsBox").html("Pet record updated successfully");
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
				var gridPets = $("#gridPets").data("kendoGrid");
				gridPets.dataSource.read();
				gridPets.refresh();
				return false;
			}
			
			else if (e.type == "create") 
			{
				$('#gridPets').data().kendoGrid.dataSource.read();
				$("#alertsBox").html("");
				$("#alertsBox").html("Pet record created successfully");
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
				$('#gridPets').data().kendoGrid.dataSource.read();
				$("#alertsBox").html("");
				$("#alertsBox").html("Pet record updated successfully");
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
	
<!-- -------------------------------- Document script ------------------------------------------------------------------ -->

<script type="text/javascript">
var documentTypeSelected = "";
function handleSelect(e)
{
	//alert("Changed>>"+SelectedRowId);
	var dataItem = this.dataItem(e.item.index());
  //alert("event :: select (" + dataItem.name + " : " + dataItem.value + ")" );
	documentTypeSelected = dataItem.name;
	$.ajax({
		type : "POST",
		url : "./documentDefiner/getDocumentFormat",
		data : {
			documentTypeSelected : dataItem.ddId,
			personType : "Pet",
			},
			success : function(response)
			{
				$("#docFormat_"+SelectedRowId).val(response);
			}
		
		});
}
var selectedRowIndex = "";
function documentChangeEvent(args)
{
	var grid = $("#documentsUpload_"+SelectedRowId).data("kendoGrid");
	var selectedRow = grid.select();
	selectedRowIndex = selectedRow.index();
}
 
function documentNameEditor(container, options)
  {	
	  $('<input name="Document Name" required="true" validationMessage="Document Name is Required" data-text-field="name" data-value-field="value" data-bind="value:' + options.field + '"/>')
		.appendTo(container).kendoDropDownList({	
			optionLabel : {
				name : "Select",
				value : "",
			},
			defaultValue : false,
			select: onDocumentSelect,
			sortable : true,
			dataSource : {
				transport : {
					read : "${getDocumentType}/Pet"
				}
			}
		});
	  //$('<span class="k-invalid-msg" data-for="Document Name"></span>').appendTo(container);

  }
function documentDecriptionEditor(container, options) 
{
	$('<textarea data-text-field="documentDescription" name="documentDescription" data-value-field="documentDescription" data-bind="value:' + options.field + '" style="width: 148px; height: 40px;"/>')
			.appendTo(container);
}
function onDocumentSelect(e) 
{
        var dataItem = this.dataItem(e.item.index());
        $.ajax({
			type : "POST",
			url : "./documentDefiner/getDocumentFormat",
			data : {
				documentTypeSelected : dataItem.ddId,
				personType : "Pet",
				},
				dataType:"text",
				success : function(response)
				{
					$("#docFormatId").val(response);
					var documentFormat = $("#documentsUpload_"+SelectedRowId).data().kendoGrid.dataSource
				      .data()[selectedRowIndex];
					documentFormat.set('documentFormat', response);
				}
			
			});

}    
  function documentFormatEditor(container, options)
  {	
	  $('<input type="text" readonly="true" class="k-input k-textbox" id="docFormatId" name="documentFormat" data-bind="value:' + options.field + '"/>')
		.appendTo(container);
  }
 var drId = "";
 var documentFormatToUpload = "";
  function uploadNotice()
	{
	    var gview = $("#documentsUpload_"+SelectedRowId).data("kendoGrid");
		//Getting selected item
		
		
		var selectedItem = gview.dataItem(gview.select());
		  if (selectedItem != null) 
		  {
			  drId = selectedItem.drId;
			  documentFormatToUpload = selectedItem.documentFormat;
		  } 
		$('#uploadDialog').dialog({
			modal : true,
		});
		return false;
	}

function uploadExtraData(e)
{
	  //alert(JSON.stringify(e));
	  //alert(SelectedRowId);
	  var docNumber = $("#docNumber_"+SelectedRowId).val();
	  e.data = { ddType: documentTypeSelected,documentNumber : docNumber,personId : SelectedRowId  };
} 
function uploadExtraDataAlongWithFile(e)
{	
	e.data = { drId:drId};
}
function downloadFile()
 {
	 var gview = $("#documentsUpload_"+SelectedRowId).data("kendoGrid");
		//Getting selected item
		var selectedItem = gview.dataItem(gview.select());
		window.open("./download/"+selectedItem.drId);
		/* $.ajax({
			 type : "POST",
			 url :"./download/"+selectedItem.drId,
			 success : function(response)
			 {
				 alert(response);
			 }
			}); */
 }

 var selectedType = "";
  
 function getUpload() 
 {
     return $("#files").data("kendoUpload");
 }
 
 function onSelect(e) 
 {
      
         var dataItem = this.dataItem(e.item.index());
         selectedType =  dataItem.value;
         if((selectedType != '') ||(selectedType != null))
         {
             $("#hiddenField").val(selectedType);
        	 getUpload().enable();
         }
       // alert("event :: select (" + dataItem.text + " : " + dataItem.value + ")" );
     
 }

 function onDocumentSuccess(e)
	{
		alert("Uploaded Successfully");
		$(".k-upload-files.k-reset").find("li").remove();
		
	}
function filesSelectedtoUpload(e)
  {
	  var files = e.files;
	  var requiredFormat = $("#docFormat_"+SelectedRowId).val();
	  requiredFormat = "."+documentFormatToUpload.toLowerCase();//requiredFormat.toLowerCase();
	 // alert(requiredFormat);
	 // alert(files.length);
	 if(requiredFormat == '')
		 {
			alert("Please select Document Type");
			return false;
		 }
		if( files.length  > 10 ) {
			alert("Maximum 10 files can be uploaded at a time.");
			e.preventDefault();
			return false;
		}
	 
		for(var fileCntr = 0; fileCntr < files.length; fileCntr ++) {
			if( files[fileCntr].size > 10485760 ) {
				alert("File size more than 10MB can not be uploaded.");
				e.preventDefault();
				return false;
			}
	 		
			if(documentFormatToUpload == "Image")
			{
			
				//alert(documentFormatToUpload+"----"+files[fileCntr].extension.toLowerCase());
				if( files[fileCntr].extension.toLowerCase() == '.png' || files[fileCntr].extension.toLowerCase() == '.jpg' || files[fileCntr].extension.toLowerCase() == '.jpeg' ) 
				{
					 
				}
				else
				{
					alert("Only Images can be uploaded\nAcceptable formats are png, jpg and jpeg");
					e.preventDefault();
					return false;
				}
			
			}
		else
			{
				if( files[fileCntr].extension.toLowerCase() != requiredFormat ) {
					alert("Please Upload "+documentFormatToUpload+" Format");
					e.preventDefault();
					return false;
				}	
			
			}
			
			/* if( files[fileCntr].extension.toLowerCase() != requiredFormat ) {
				alert("Please Upload "+documentFormatToUpload+" Format");
				e.preventDefault();
				return false;
			} */
		}

  }


function photoSelectedtoUpload(e)
  {
	  var files = e.files;
	
		if( files.length  > 10 ) {
			alert("Maximum 10 files can be uploaded at a time.");
			e.preventDefault();
			return false;
		}
	 
		for(var fileCntr = 0; fileCntr < files.length; fileCntr ++) {
			if( files[fileCntr].size > 10485760 ) {
				alert("File size more than 10MB can not be uploaded.");
				e.preventDefault();
				return false;
			}
	 
			/* if( files[fileCntr].extension.toLowerCase() != '.png' || files[fileCntr].extension.toLowerCase() != '.jpg') {
				alert("Executable files can not be uploaded.");
				e.preventDefault();
				return false;
			} */
		}

  }


 function ajaxFormSubmit()
 {
	 if((selectedType == '') ||(selectedType == null))
     {
	     alert("Select Document Type");
	     return false;
     }
	 else
		 {
			$.ajax({

				type : "POST",
				url : "/owner/document/upload",
				data :
					{
						selectedType : selectedType,
					},
				success : function(response)
				{
					alert(response);
				}
				

					
				});

		 }
 }

 function DocumentOnRequestEnd(e)
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
			$("#alertsBox").html("Document Record Created Successfully");
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
			$("#alertsBox").html("Document Record Updated successfully");
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
			$("#alertsBox").html("Document Record Deleted successfully");
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

 (function($, kendo) {
		$
				.extend(
						true,
						kendo.ui.validator,
						{
							rules : { // custom rules          	
								documentNumber: function (input, params) 
					             {
				                     if (input.filter("[name = 'documentNumber']").length && input.val() != "") 
				                     {                          
				                    	 return /^[a-zA-Z0-9 ]{0,35}$/
											.test(input.val());
				                     }
				                     return true;
				                 },
				                 dateOfVaccination : function(input, params) {
										if (input.filter("[name = 'dateOfVaccination']").length
												&& input.val()) {
											var selectedDate = input.val();
											var todaysDate = $.datepicker
													.formatDate('dd/mm/yy',
															new Date());
											var flagDate = false;

											if ($.datepicker.parseDate(
													'dd/mm/yy', selectedDate) < $.datepicker
													.parseDate('dd/mm/yy',
															todaysDate)) {
												flagDate = true;
											}
											return flagDate;
										}
										return true;
									},
									documentDescription: function (input, params) 
						             {
					                     if (input.filter("[name = 'documentDescription']").length && input.val() != "") 
					                     {                          
					                    	 return /^[a-zA-Z0-9 ]{0,50}$/
												.test(input.val());
					                     }
					                     return true;
					                 },
					                 documentDescription_blank :  function (input, params) {
					                     //check for the name attribute 
					                     if (input.attr("name") == "documentDescription") {
					                      return $.trim(input.val()) !== "";
					                     }
					                     return true;
					                 }             
								},
								messages : {
									//custom rules messages
									documentNumber:"Only alphanumeric is allowed",
									dateOfVaccination:"Date of Vaccination date must be past",
									documentDescription:"Only alphanumeric is allowed",
									documentDescription_blank : "Document Description is required"
								}
						});
	
	})(jQuery, kendo);
	//End Of Validation
	
	function petDocumentDeleteEvent(){
		securityCheckForActions("./commanagement/petsdocuments/deleteButton");
	}
	
	function documentEvent(e)
			{
				
				$('div[data-container-for="documentFormat"]').remove();
				$('label[for="documentFormat"]').closest('.k-edit-label').remove();
				if (e.model.isNew()) 
			    {
					selectedRowIndex = 0;
					securityCheckForActions("./commanagement/petsdocuments/createButton");
					$(".k-window-title").text("Add New Document Details");
					$(".k-grid-update").text("Save");
					$('.k-edit-field .k-input').first().focus();
			    }
				else
				{
					securityCheckForActions("./commanagement/petsdocuments/updateButton");
					$(".k-window-title").text("Update Document Details");
					$(".k-grid-update").text("Update");
					$('.k-edit-field .k-input').first().focus();
				}
				
				 $(".k-grid-cancel").click(function () 
						 {
							  var grid = $("#documentsUpload_"+SelectedRowId).data("kendoGrid");
							  grid.dataSource.read();
							  grid.refresh();
						 });
			}
		
</script>
<style type="text/css">
	.k-upload-button input {
                z-index: inherit;
                }
                
                
                
.wrappers {
	display: inline;
	float: left;
	width: 350px;
	padding-top: 10px;

	/* float:left;  */
	/* border: 1px solid red;
 */
	/* border: 1px solid green;
    overflow: hidden; */
}

div.tagsinput span.tag {
    background: none repeat scroll 0 0 grey;
    color: white;
    font-size: 12px;
    line-height: 20px;
    border: 0;

}

div.tagsinput input {
    background: none repeat scroll 0 0 rgba(0, 0, 0, 0);
    border: medium none;
    margin: 0;
    padding: 0;
    height: 28px;
}


div.tagsinput span.tag a {
    color: black;
    float: right;
    font-size: 11px;
    font-weight: bold;
}

div.tagsinput {
    background: none repeat scroll 0 0 #FDFDFD;
    border: 1px solid #DDDDDD;
    box-sizing: border-box;
    overflow-y: auto;
    padding: 0;
    width: 71%;
     border-radius: 6px;
}


.k-tabstrip{
	width: 1150px
}

</style>
