<%@include file="/common/taglibs.jsp"%>


<c:url value="/jobcardsdetails/create" var="createUrl" />
<c:url value="/jobcardsdetails/read" var="readUrl" />
<c:url value="/jobcardsdetails/update" var="updateUrl" />	
<c:url value="/jobcardsdetails/deleteJobCard" var="destroyUrl" />

<c:url value="/asset/readAssetAll" var="readAssetUrl" />

<c:url value="/jobcardsdetails/assertDropdown" var="assetdropdownUrl" />
<c:url value="/jobcardsdetails/getMPerson" var="getOwnername" />	
<c:url value="/jobcardsdetails/getgroupname" var="getgroupname" />	
<c:url value="/jobcardsdetails/getdepartmentname" var="getdepartmentname" />
<c:url value="/jobcardsdetails/getjtDescription" var="jtDescriptionUrl" />
<c:url value="/jobcardsdetails/getMaintainance" var="getMaintainance" />
<c:url value="/jobcardsdetails/getJobTypes" var="getJobTypes" />

<c:url value="/jobcardsdetails/getjobcardsForFilter" var="jobGeneratedbyFilterUrl" />
<c:url value="/jobcardsdetails/jobNameFilterUrl" var="jobNameFilterUrl" />
<c:url value="/jobcardsdetails/jobNoFilterUrl" var="jobNoFilterUrl" />
<c:url value="/jobcardsdetails/getCreatedByForJobCaardsFilter" var="CreatedByFilterUrl" />
<c:url value="/jobcardsdetails/getUpdatedByJobCaardsForFilter" var="UpdatedByFilterUrl" />	
<c:url value="/jobcardsdetails/jobTypeFilterUrl" var="jobTypeFilterUrl" />	
<c:url value="/jobcardsdetails/jobMaintainanceTypeFilterUrl" var="jobMaintainanceTypeFilterUrl" />	
<c:url value="/jobcardsdetails/jobExpectedSLAFilterUrl" var="jobExpectedSLAFilterUrl" />	
<c:url value="/jobcardsdetails/jobSLAFilterUrl" var="jobSLAFilterUrl" />	
<c:url value="/jobcardsdetails/jobStatusFilterUrl" var="jobStatusFilterUrl" />	
<c:url value="/jobcardsdetails/jobDepartmentFilterUrl" var="jobDepartmentFilterUrl" />	
<%-- <c:url value="/jobcardsdetails/jobGroupFilterUrl" var="jobGroupFilterUrl" />	 --%>
<c:url value="/jobcardsdetails/jobOwnersFilterUrl" var="jobOwnerFilterUrl" />	
<c:url value="/jobcardsdetails/jobPriorityFilterUrl" var="jobPriorityFilterUrl" />	

<c:url value="/asset/cat/read" var="transportReadUrlCat" />
<c:url value="/asset/loc/read" var="transportReadUrlLoc" />

<!-- Job Objective -->

<c:url value="/jobobjectivedetails/create" var="createJobObjectiveUrl" />
<c:url value="/jobobjectivedetails/update" var="updateJobObjectiveUrl" />
<c:url value="/jobobjectivedetails/read" var="readJobObjectiveUrl" />	
<c:url value="/jobobjectivedetails/destroy" var="deleteJobObjectiveUrl" />

<!-- Job Notification -->

<c:url value="/jobobnotificationdetails/create" var="createJobNotificationUrl" />
<c:url value="/jobobnotificationdetails/update" var="updateJobNotificationUrl" />
<c:url value="/jobobnotificationdetails/read" var="readJobNotificationUrl" />	
<c:url value="/jobobnotificationdetails/destroy" var="deleteJobNotificationUrl" />
<c:url value="/parkingslotsdetails/getBlocks" var="getblocks" />
<c:url value="/jobobnotificationdetails/getFlats" var="getFlat" />
<c:url value="/jobobnotificationdetails/getNotificationMembers" var="notificationMembersCombo" />

<!-- Job Notes -->

<c:url value="/jobobnotesdetails/create" var="createJobNotesUrl" />
<c:url value="/jobobnotesdetails/update" var="updateJobNotesUrl" />
<c:url value="/jobobnotesdetails/read" var="readJobNotesUrl" />	
<c:url value="/jobobnotesdetails/destroy" var="deleteJobNotesUrl" />

<!-- Job Team -->

<c:url value="/jobobteamdetails/getdepartmentname" var="getDepartment" />
<c:url value="/jobobteamdetails/jobTeamMembers" var="jobTeamMembersCombo" />
<c:url value="/jobobteamdetails/create" var="createJobTeamUrl" />
<c:url value="/jobobteamdetails/update" var="updateJobTeamUrl" />
<c:url value="/jobobteamdetails/read" var="readJobTeamUrl" />	
<c:url value="/jobobteamdetails/destroy" var="deleteJobTeamUrl" />

<!-- Job Tools -->

<c:url value="/jobobtoolsdetails/create" var="createJobToolsUrl" />
<c:url value="/jobobtoolsdetails/update" var="updateJobToolsUrl" />
<c:url value="/jobobtoolsdetails/read" var="readJobToolsUrl"/>	
<c:url value="/jobobtoolsdetails/destroy" var="deleteJobToolsUrl" />
<c:url value="/jobobtoolsdetails/getTools" var="gettoolname" />
<c:url value="/jobobtoolsdetails/getToolsQuantity" var="getQuantityofTools" />

<!-- Job Material -->

<c:url value="/jobobmaterialdetails/create" var="createJobMaterialUrl" />
<c:url value="/jobobmaterialdetails/update" var="updateJobMaterialUrl" />
<c:url value="/jobobmaterialdetails/read" var="readJobMaterialUrl" />	
<c:url value="/jobobmaterialdetails/destroy" var="deleteJobMaterialUrl" />
<c:url value="/jobobmaterialdetails/getMaterialName" var="getMaterialName" />
<c:url value="/jobobmaterialdetails/getStoreName" var="getStoreName" />
<c:url value="/jobobmaterialdetails/getMaterialUOM" var="getUOM" />
<c:url value="/jobobmaterialdetails/getQuantityforMaterials" var="getQuantityforMaterials" />

<!-- Job Labour Task -->

<c:url value="/joboblabourtaskdetails/create" var="createJobLabourTaskUrl" />
<c:url value="/joboblabourtaskdetails/update" var="updateJobLabourTaskUrl" />
<c:url value="/joboblabourtaskdetails/read" var="readJobLabourTaskUrl" />	
<c:url value="/joboblabourtaskdetails/destroy" var="deleteJobLabourTaskUrl" />

<!-- Job Documents -->
<c:url value="/jobdocumentsdetails/create" var="createJobDocumentsUrl" />
<c:url value="/jobdocumentsdetails/update" var="updateJobDocumentsUrl" />
<c:url value="/jobdocumentsdetails/read" var="readJobDocumentsUrl" />	
<c:url value="/jobdocumentsdetails/destroy" var="deleteJobDocumentsUrl" />
<c:url value="/jobdocuments/upload/async/save" var="saveUrl" />

	
<kendo:grid  name="grid" pageable="true" dataBound="jobCardsDatabound"   change="onChangeJobCards" edit="jobCardsEvent" sortable="true" resizable="true" detailTemplate="template"  groupable="true" filterable="true" scrollable="true" selectable="true" height="430px">
	<kendo:grid-editable mode="popup"  confirmation="Are You Sure? You Want To Delete The Record?" />
	
	<kendo:grid-toolbar>
		<kendo:grid-toolbarItem name="create" text="Create Job Card" />
		<kendo:grid-toolbarItem name="jobCardsTemplatesDetailsExport" text="Export To Excel" />
	    <kendo:grid-toolbarItem name="jobCardsPdfTemplatesDetailsExport" text="Export To PDF" /> 
		<kendo:grid-toolbarItem text="Clear Filter" name="Clear_Filter"/>		
		<kendo:grid-toolbarItem template="&nbsp;&nbsp;&nbsp;<span class='bgBlueColor' style='width:60px; height :20px'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;: OPEN"/>
		<kendo:grid-toolbarItem template="&nbsp;&nbsp;&nbsp;<span class='bgGreenColor' style='width:60px; height :20px'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;: ONJOB"/>
		<kendo:grid-toolbarItem template="&nbsp;&nbsp;&nbsp;<span class='bgYellowColor' style='width:60px; height :20px'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;: SUSPENDED"/>
		<kendo:grid-toolbarItem template="&nbsp;&nbsp;&nbsp;<span class='bgRedColor' style='width:60px; height :20px'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;: CLOSED"/>
	
	</kendo:grid-toolbar>
	
	<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" ></kendo:grid-pageable>
	
	<kendo:grid-filterable extra="false">
	 <kendo:grid-filterable-operators>
	  	<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains"/>
	  	<kendo:grid-filterable-operators-date gt="Is after" lt="Is before"/>
	  	
	 </kendo:grid-filterable-operators>
		
	</kendo:grid-filterable>
	
	<kendo:grid-columns>
			
		<kendo:grid-column title=""	template="<a href='\\\#' id='totalcost' class='k-button k-button-icontext btn-destroy k-grid-totlcost#=data.jcId#'>Total Cost</a>" width="95px"/>
		<%-- <kendo:grid-column title="" width="100px">
			<kendo:grid-column-command>
			<kendo:grid-column-commandItem name="TotalCost"  text="Total Cost"/>
			</kendo:grid-column-command>
		</kendo:grid-column>	 --%>
	 
	   <kendo:grid-column title="Job&nbsp;Number" field="jobNo" width="110px">
			 <kendo:grid-column-filterable>
    			<kendo:grid-column-filterable-ui>
   					<script> 
						function psSlotNoFilter(element) {
							element.kendoAutoComplete({
							dataSource : {
									transport : {
										read : "${jobNoFilterUrl}",
									}
								},
							});							
				  		}
				  	</script>		
    			</kendo:grid-column-filterable-ui>
    		</kendo:grid-column-filterable>	      	   
     	</kendo:grid-column>	     	   
	   
		<kendo:grid-column title="Job&nbsp;Name" field="jobName"  width="150px">
		 	<kendo:grid-column-filterable>
    			<kendo:grid-column-filterable-ui>
   					<script> 
						function psSlotNoFilter(element) {
							element.kendoAutoComplete({
							dataSource : {
									transport : {
										read : "${jobNameFilterUrl}",
									}
								},
							});							
				  		}
				  	</script>		
    			</kendo:grid-column-filterable-ui>
    		</kendo:grid-column-filterable>	      	   
       </kendo:grid-column>	      	 
      <%--  <kendo:grid-column title="Job&nbsp;Group" field="jobGroup"  width="150px">
       		<kendo:grid-column-filterable>
    			<kendo:grid-column-filterable-ui>
   					<script> 
						function psSlotNoFilter(element) {
							element.kendoAutoComplete({
							dataSource : {
									transport : {
										read : "${jobGroupFilterUrl}",
									}
								},
							});							
				  		}
				  	</script>		
    			</kendo:grid-column-filterable-ui>
    		</kendo:grid-column-filterable>	      	   
       </kendo:grid-column>	       --%>	 
       <kendo:grid-column title="Job&nbsp;Description" field="jobDescription" editor="jobdescriptionEditor" width="150px" hidden="true"/>
	   <kendo:grid-column title="Job&nbsp;Date" field="jobDt" format= "{0:dd/MM/yyyy hh:mm tt}"   width="130px">
	   		<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
	    					function fromDateFilter(element) {
								element.kendoDateTimePicker({
									format:"{0:dd/MM/yyyy hh:mm tt}",
					            	
				            	});
					  		}    					
					  	</script>			
	    			</kendo:grid-column-filterable-ui>
	    	</kendo:grid-column-filterable>
       </kendo:grid-column>
	   <kendo:grid-column title="Job&nbsp;Department" field="jobDepartment" editor="departmentEditor"  width="140px">
	   		<kendo:grid-column-filterable>
    			<kendo:grid-column-filterable-ui>
   					<script> 
						function psSlotNoFilter(element) {
							element.kendoAutoComplete({
							dataSource : {
									transport : {
										read : "${jobDepartmentFilterUrl}",
									}
								},
							});							
				  		}
				  	</script>		
    			</kendo:grid-column-filterable-ui>
    		</kendo:grid-column-filterable>	      	   
       </kendo:grid-column>	      	 
	   <kendo:grid-column title="Job&nbsp;Type" field="jobType" editor="jobTypeEditor" width="110px">
	  	 <kendo:grid-column-filterable>
    			<kendo:grid-column-filterable-ui>
   					<script> 
						function psSlotNoFilter(element) {
							element.kendoAutoComplete({
							dataSource : {
									transport : {
										read : "${jobTypeFilterUrl}",
									}
								},
							});							
				  		}
				  	</script>		
    			</kendo:grid-column-filterable-ui>
    		</kendo:grid-column-filterable>	      	   
       </kendo:grid-column>	      	 
	   <kendo:grid-column title="Job&nbsp;Expected&nbsp;SLA (Days)" field="jobExpectedSla"  width="162px" filterable="false" hidden="true">
	  		 <%-- <kendo:grid-column-filterable>
    			<kendo:grid-column-filterable-ui>
   					<script> 
						function psSlotNoFilter(element) {
							element.kendoAutoComplete({
							dataSource : {
									transport : {
										read : "${jobExpectedSLAFilterUrl}",
									}
								},
							});							
				  		}
				  	</script>		
    			</kendo:grid-column-filterable-ui>
    		</kendo:grid-column-filterable> --%>	      	   
       </kendo:grid-column>	     
	   
	   <kendo:grid-column title="Job&nbsp;SLA" field="jobSla" filterable="false"  width="150px" hidden="true">
	   		<%-- <kendo:grid-column-filterable>
    			<kendo:grid-column-filterable-ui>
   					<script> 
						function psSlotNoFilter(element) {
							element.kendoAutoComplete({
							dataSource : {
									transport : {
										read : "${jobSLAFilterUrl}",
									}
								},
							});							
				  		}
				  	</script>		
    			</kendo:grid-column-filterable-ui>
    		</kendo:grid-column-filterable>	  --%>     	   
       </kendo:grid-column>	      	 
	
	   <kendo:grid-column title="" field="" hidden="true" width="150px"/>
	   
	  <kendo:grid-column title="Asset&nbsp;Type"  field="radioBtn" editor="radioEditor" width="110px" hidden="true" />
      <kendo:grid-column title="Select&nbsp;Category" field="assetTreeCat" width="110px" editor="catEditor"	hidden="true" />
      <kendo:grid-column title="Select&nbsp;Location" field="assetTreeLoc" width="110px" editor="locEditor"	hidden="true" />
     	   
	   <kendo:grid-column title="Job&nbsp;Assests" filterable="false" field="jobAssets" editor="assetEditor"  width="150px" hidden="true"/>
	    <%-- <kendo:grid-column title="Job&nbsp;Owners" field="personId" editor="ownerEditor" width="150px" hidden="true"/> --%>
	   <kendo:grid-column title="Job&nbsp;Owners" field="pn_Name" editor="ownerEditor" width="105px">
	   		<kendo:grid-column-filterable>
    			<kendo:grid-column-filterable-ui>
   					<script> 
						function psSlotNoFilter(element) {
							element.kendoAutoComplete({
							dataSource : {
									transport : {
										read : "${jobOwnerFilterUrl}",
									}
								},
							});							
				  		}
				  	</script>		
    			</kendo:grid-column-filterable-ui>
    		</kendo:grid-column-filterable>	      	   
       </kendo:grid-column>	      	 
	 
	    <kendo:grid-column title="Maintenance&nbsp;Type" editor="jobMtEditor" field="jobMt" width="125px">
	    	<kendo:grid-column-filterable>
    			<kendo:grid-column-filterable-ui>
   					<script> 
						function psSlotNoFilter(element) {
							element.kendoAutoComplete({
							dataSource : {
									transport : {
										read : "${jobMaintainanceTypeFilterUrl}",
									}
								},
							});							
				  		}
				  	</script>		
    			</kendo:grid-column-filterable-ui>
    		</kendo:grid-column-filterable>	      	   
       </kendo:grid-column>	      	 
	
	   <kendo:grid-column title="Job&nbsp;Priority" field="jobPriority" editor="jobPriorityEditor"  width="100px">
	   		<kendo:grid-column-filterable>
    			<kendo:grid-column-filterable-ui>
   					<script> 
						function psSlotNoFilter(element) {
							element.kendoAutoComplete({
							dataSource : {
									transport : {
										read : "${jobPriorityFilterUrl}",
									}
								},
							});							
				  		}
				  	</script>		
    			</kendo:grid-column-filterable-ui>
    		</kendo:grid-column-filterable>	      	   
       </kendo:grid-column>	      	 
	 
	   <kendo:grid-column title="Job&nbsp;Start&nbsp;Date" field="jobStartDt" format= "{0:dd/MM/yyyy hh:mm tt}"  width="120px">
	   		<kendo:grid-column-filterable ui="datetimepicker"></kendo:grid-column-filterable>
       </kendo:grid-column>
       <kendo:grid-column title="Job&nbsp;End&nbsp;Date" field="jobEndDt" format= "{0:dd/MM/yyyy hh:mm tt}"  width="120px">
       		<kendo:grid-column-filterable ui="datetimepicker"></kendo:grid-column-filterable>
       </kendo:grid-column>
     	  
	   <kendo:grid-column title="Job&nbsp;CC" field="jobCcId"  width="150px" hidden="true"/>
	   <kendo:grid-column title="Job&nbsp;BMS" field="jobBmsId"  width="150px" hidden="true"/>		
	   <kendo:grid-column title="Job&nbsp;Arms" field="jobAmsId"  width="150px" hidden="true"/>		
	   <kendo:grid-column title="Job&nbsp;Generated&nbsp;By" field="jobGeneratedby"  width="120px" hidden="true">
		 <kendo:grid-column-filterable>
    			<kendo:grid-column-filterable-ui>
   					<script> 
						function psSlotNoFilter(element) {
							element.kendoAutoComplete({
							dataSource : {
									transport : {
										read : "${jobGeneratedbyFilterUrl}",
									}
								},
							});							
				  		}
				  	</script>		
    			</kendo:grid-column-filterable-ui>
    		</kendo:grid-column-filterable>	      	   
     	   </kendo:grid-column>	
		  <kendo:grid-column title="Expected&nbsp;To&nbsp;Complete" field="expDate" format= "{0:dd/MM/yyyy hh:mm tt}" editor="dateEditor"  width="148px">
	   		<kendo:grid-column-filterable ui="datetimepicker"></kendo:grid-column-filterable>
       	 </kendo:grid-column>     	   		
	
	   	 
	   	 <kendo:grid-column title="Created&nbsp;By" field="createdBy" width="130px" hidden="true">
		 <kendo:grid-column-filterable>
    			<kendo:grid-column-filterable-ui>
   					<script> 
						function psSlotNoFilter(element) {
							element.kendoAutoComplete({
							dataSource : {
									transport : {
										read : "${CreatedByFilterUrl}",
									}
								},
							});							
				  		}
				  	</script>		
    			</kendo:grid-column-filterable-ui>
    		</kendo:grid-column-filterable>	      	   
     	   </kendo:grid-column>	
     	   
		 <kendo:grid-column title="Last Updated By" field="lastUpdatedBy" width="130px" hidden="true">
		 <kendo:grid-column-filterable>
    			<kendo:grid-column-filterable-ui>
   					<script> 
						function psSlotNoFilter(element) {
							element.kendoAutoComplete({
							dataSource : {
									transport : {
										read : "${UpdatedByFilterUrl}",
									}
								},
							});							
				  		}
				  	</script>		
    			</kendo:grid-column-filterable-ui>
    		</kendo:grid-column-filterable>	      	   
     	   </kendo:grid-column>	
     	   
		<kendo:grid-column title="Last Updated Date" field="lastUpdatedDate" format= "{0:dd/MM/yyyy hh:mm tt}"  width="150px" hidden="true"/> 	
		<kendo:grid-column title="Job Status" field="status"  width="85px">
			<kendo:grid-column-filterable>
    			<kendo:grid-column-filterable-ui>
   					<script> 
						function psSlotNoFilter(element) {
							element.kendoAutoComplete({
							dataSource : {
									transport : {
										read : "${jobStatusFilterUrl}",
									}
								},
							});							
				  		}
				  	</script>		
    			</kendo:grid-column-filterable-ui>
    		</kendo:grid-column-filterable>	      	   
       </kendo:grid-column>	   
    	
		<kendo:grid-column title=" " width="160px">
			<kendo:grid-column-command>
				<kendo:grid-column-commandItem name="edit"/>			
				<kendo:grid-column-commandItem name="destroy"  text="Delete"/>
			</kendo:grid-column-command>			
		</kendo:grid-column>		
		<kendo:grid-column title=""	template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Activate#=data.jcId#'>#= data.status == 'INIT' ? 'OPEN' :  data.status == 'OPEN' ? 'ON JOB' : data.status == 'ON JOB' ?'CLOSE':'CLOSED' #</a>" width="170px">
		
		</kendo:grid-column>		 		
	
	</kendo:grid-columns>
	<kendo:dataSource pageSize="20" requestEnd="onRequestEnd" requestStart="onRequestStart" >
		<kendo:dataSource-transport>
			<kendo:dataSource-transport-create url="${createUrl}" dataType="json" type="POST" contentType="application/json" />
			<kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json" />
			<kendo:dataSource-transport-update url="${updateUrl}" dataType="json" type="POST" contentType="application/json" />
			<kendo:dataSource-transport-destroy url="${destroyUrl}" dataType="json" type="POST" contentType="application/json" />
			<kendo:dataSource-transport-parameterMap>
					<script>
						function parameterMap(options, type) {
							return JSON.stringify(options);
						}
					</script>
			</kendo:dataSource-transport-parameterMap>
		</kendo:dataSource-transport>
		
		<kendo:dataSource-schema parse="parse">
			<kendo:dataSource-schema-model id="jcId" >
				<kendo:dataSource-schema-model-fields>					
				   <kendo:dataSource-schema-model-field name="jcId"/>
				   <kendo:dataSource-schema-model-field name="jtDescription" type="string"/>
				   <kendo:dataSource-schema-model-field name="jtSla"/>
				   <kendo:dataSource-schema-model-field name="jobName"/>
				  <%--  <kendo:dataSource-schema-model-field  name="jobGroup"/> --%>
		      	   <kendo:dataSource-schema-model-field  name="jobDescription"/>
				   <kendo:dataSource-schema-model-field  name="jobDt" type="date"/>
				   <kendo:dataSource-schema-model-field  name="jobDepartment"/>
				   <kendo:dataSource-schema-model-field  name="jobExpectedSla"/>
				   <kendo:dataSource-schema-model-field  name="jobSla"/>		
				   <kendo:dataSource-schema-model-field  name="jobMt"/>					
				   <kendo:dataSource-schema-model-field  name="expDate" type="date" defaultValue=""/>					
				   <kendo:dataSource-schema-model-field  name="jobStartDt"  type="date" defaultValue=""/>
		      	   <kendo:dataSource-schema-model-field  name="jobEndDt"  type="date" defaultValue=""/>
				   <kendo:dataSource-schema-model-field  name="jobAssets"/>
				   <kendo:dataSource-schema-model-field  name="pn_Name"/>
				   <kendo:dataSource-schema-model-field  name="personId"/>
				   <kendo:dataSource-schema-model-field  name="jobCcId"/>
				   <kendo:dataSource-schema-model-field  name="jobBmsId"/>		
				   <kendo:dataSource-schema-model-field  name="jobAmsId"/>
				   <kendo:dataSource-schema-model-field  name="jobNo" type="string"/>
				   <kendo:dataSource-schema-model-field  name="jobType"/>
				   <kendo:dataSource-schema-model-field  name="jobPriority"/>		
				   <kendo:dataSource-schema-model-field name="status" defaultValue="INIT"  />
				   <kendo:dataSource-schema-model-field name="suspendStatus" defaultValue="RESUME"  />
				   <kendo:dataSource-schema-model-field name="jobGeneratedby" defaultValue="USER DEFINED"/>
				   <kendo:dataSource-schema-model-field name="createdBy"/>
				   <kendo:dataSource-schema-model-field name="lastUpdatedBy"/>
				   <kendo:dataSource-schema-model-field name="lastUpdatedDate" type="date"/>  
				  
				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>
	</kendo:dataSource>
</kendo:grid>
	
<kendo:grid-detailTemplate id="template">
	<kendo:tabStrip name="tabStrip_#=jcId#">
		<kendo:tabStrip-items>
			<kendo:tabStrip-item text="Job Objective" selected="true" >
				<kendo:tabStrip-item-content>
					<div class="jobObjectivetabStrip">
						<kendo:grid  name="jobObjectivegrid_#=jcId#" remove="jobobjectiveRemove" dataBound="jobObjectivedataBound" edit="jobObjectiveEvent" pageable="true" resizable="true" sortable="true" reorderable="true" 	selectable="true" scrollable="true" >
							
							<kendo:grid-pageable pageSize="10"/>
							<kendo:grid-editable  mode="popup" confirmation="Are You Sure? You Want To Delete The Record?"/>
							<kendo:grid-toolbar>
							<kendo:grid-toolbarItem name="create" text="Add Job Objective"/>								
							</kendo:grid-toolbar>
						
							<kendo:grid-columns>
								<kendo:grid-column title="Job Card Id" field="jcoId" width="100px" hidden="true"/>
								<kendo:grid-column title="Job Card Objective&nbsp;*" field="jcObjective" editor="jobObjectiveEditor" width="100px" />
								<kendo:grid-column title="Job Card Achived " field="jcObjectiveAch" width="100px" />								
							    
								<kendo:grid-column title=" " width="80px">
									<kendo:grid-column-command>
										<kendo:grid-column-commandItem name="edit"/>
										<kendo:grid-column-commandItem name="destroy"  text="Delete"/>	
									</kendo:grid-column-command>
								</kendo:grid-column>					
												
							
								<kendo:grid-column title="" width="60px" />								     
								 
							 </kendo:grid-columns>
							<kendo:dataSource requestEnd="onRequestEndJobObjective" requestStart="onRequestStartJobObjective">						
								<kendo:dataSource-transport>
									<kendo:dataSource-transport-read url="${readJobObjectiveUrl}/#=jcId#" dataType="json" type="POST" contentType="application/json" />
									<kendo:dataSource-transport-create url="${createJobObjectiveUrl}/#=jcId#" dataType="json" type="POST" contentType="application/json" />
									<kendo:dataSource-transport-update url="${updateJobObjectiveUrl}/#=jcId#" dataType="json" type="POST" contentType="application/json" />
									<kendo:dataSource-transport-destroy	url="${deleteJobObjectiveUrl}/#=jcId#" dataType="json" type="POST" contentType="application/json" />
									<kendo:dataSource-transport-parameterMap>
									<script>
										function parameterMap(options, type) {
											return JSON.stringify(options);
										}
									</script>
									</kendo:dataSource-transport-parameterMap>
								</kendo:dataSource-transport>
								<kendo:dataSource-schema>
									<kendo:dataSource-schema-model id="jcoId">
										<kendo:dataSource-schema-model-fields>
											<kendo:dataSource-schema-model-field name="jcObjective" type="string"/>
											<kendo:dataSource-schema-model-field name="jcObjectiveAch" defaultValue="NO"/>
						
										</kendo:dataSource-schema-model-fields>
									</kendo:dataSource-schema-model>
								</kendo:dataSource-schema>
						   </kendo:dataSource>
						</kendo:grid>
					</div>
				</kendo:tabStrip-item-content>
			</kendo:tabStrip-item> 
			
			<kendo:tabStrip-item text="Job Notification">
				<kendo:tabStrip-item-content>
					<div class="jobNotificationtabStrip">
						<kendo:grid name="jobNotificationgrid_#=jcId#" remove="jobnotificationRemove" dataBound="jobNotificationdataBound" edit="jobNotificationEvent" pageable="true" 	resizable="true" sortable="true" reorderable="true"	selectable="true" scrollable="true">
							<kendo:grid-pageable pageSize="10"/>
							<kendo:grid-editable  mode="popup" confirmation="Are You Sure? You Want To Delete The Record?" />							
							<kendo:grid-toolbar>
							<kendo:grid-toolbarItem name="create" text="Add Job Notification"/>								
							</kendo:grid-toolbar>						
							<kendo:grid-columns>
								<kendo:grid-column title="Job Notification Type&nbsp;*" editor="jobNotificationTypeEditor" field="notificationType" width="100px" />
								<kendo:grid-column title="Notify To&nbsp;*" editor="jobNotifyEditor" field="notify" width="100px" />
								<kendo:grid-column title="Blocks&nbsp;*" editor="blockforEditor" field="blockforJob" width="150px" />
								<kendo:grid-column title="Flat&nbsp;*" editor="flatforEditor" field="flatforJob" width="150px" />
								<kendo:grid-column title="Notification Members&nbsp;*" editor="jobNotificationMembersEditor" field="notificationMembers" width="150px"/>
								<kendo:grid-column title="Notification&nbsp;*" editor="jobNotificationEditor"  field="notification" width="100px"/>								
								<kendo:grid-column title="Job Notification Date" format= "{0:dd/MM/yyyy hh:mm tt}" field="jnDt" width="100px" />
								<kendo:grid-column title="Notified"  field="status" width="100px" />
								
								<kendo:grid-column title=" " width="80px">
									<kendo:grid-column-command>
										<kendo:grid-column-commandItem name="edit"/>
										<kendo:grid-column-commandItem name="destroy"  text="Delete"/>	
									</kendo:grid-column-command>
								</kendo:grid-column>		
								
																
								<kendo:grid-column title="" width="60px" />
																	
							</kendo:grid-columns>
							<kendo:dataSource requestEnd="onRequestEndJobNotification" requestStart="onRequestStartJobNotification">
								<kendo:dataSource-transport>
									<kendo:dataSource-transport-read url="${readJobNotificationUrl}/#=jcId#" dataType="json" type="POST" contentType="application/json" />
									<kendo:dataSource-transport-create url="${createJobNotificationUrl}/#=jcId#" dataType="json" type="POST" contentType="application/json" />
									<kendo:dataSource-transport-update url="${updateJobNotificationUrl}/#=jcId#" dataType="json" type="POST" contentType="application/json" />
									<kendo:dataSource-transport-destroy	url="${deleteJobNotificationUrl}" dataType="json" type="POST" contentType="application/json" />
									<kendo:dataSource-transport-parameterMap>
									<script>
										function parameterMap(options, type) {
											return JSON.stringify(options);
										}
									</script>
									</kendo:dataSource-transport-parameterMap>
								</kendo:dataSource-transport>
								<kendo:dataSource-schema parse="parseJobNotification">
									<kendo:dataSource-schema-model id="jnId" >
										<kendo:dataSource-schema-model-fields>
										    <kendo:dataSource-schema-model-field name="jnId" type="number"/>
											<kendo:dataSource-schema-model-field name="notificationType" type="string"/>
											<kendo:dataSource-schema-model-field name="notification"	type="string"/>	
											<kendo:dataSource-schema-model-field name="blockforJob"/>	
											<kendo:dataSource-schema-model-field name="flatforJob"/>	
											<kendo:dataSource-schema-model-field name="notificationMembers"/>
											<kendo:dataSource-schema-model-field name="notify" type="string"/>	
											<kendo:dataSource-schema-model-field name="jnDt" type="date"/>												
											<kendo:dataSource-schema-model-field name="status" type="string" defaultValue="NO"/>												
										</kendo:dataSource-schema-model-fields>
									</kendo:dataSource-schema-model>
								</kendo:dataSource-schema>
						   </kendo:dataSource>
						</kendo:grid>
					</div>
				</kendo:tabStrip-item-content>
			</kendo:tabStrip-item>
			
			<kendo:tabStrip-item text="Job Notes">
				<kendo:tabStrip-item-content>
					<div class="jobNotestabStrip">
						<kendo:grid name="jobNotesgrid_#=jcId#" remove="jobnotesRemove" pageable="true" edit="jobNotesEvent"	resizable="true" sortable="true" reorderable="true"	selectable="true" scrollable="true">
							
							<kendo:grid-pageable pageSize="10"/>
							<kendo:grid-editable  mode="popup" confirmation="Are You Sure? You Want To Delete The Record?"/>
							<kendo:grid-toolbar>
								<kendo:grid-toolbarItem name="create" text="Add Job Notes Details" />								
							</kendo:grid-toolbar>
							<kendo:grid-columns>
								<kendo:grid-column title="Job Notes Id" field="jcnId" width="100px" hidden="true"/>
								<kendo:grid-column title="Job Notes&nbsp;*" editor="jobNotesEditor" field="notes" width="100px" />
								<kendo:grid-column title="Job Notes Date&nbsp;*" format= "{0:dd/MM/yyyy hh:mm tt}" field="jcnDt" width="100px" />									
								<kendo:grid-column title=" " width="80px">
									<kendo:grid-column-command>
										<kendo:grid-column-commandItem name="edit"/>
										<kendo:grid-column-commandItem name="destroy"  text="Delete"/>
									</kendo:grid-column-command>
								</kendo:grid-column>									
								
							</kendo:grid-columns>
							<kendo:dataSource requestEnd="onRequestEndJobNotes" requestStart="onRequestStartJobNotes">
								<kendo:dataSource-transport>
									<kendo:dataSource-transport-read url="${readJobNotesUrl}/#=jcId#" dataType="json" type="POST" contentType="application/json" />
									<kendo:dataSource-transport-create url="${createJobNotesUrl}/#=jcId#" dataType="json" type="POST" contentType="application/json" />
									<kendo:dataSource-transport-update url="${updateJobNotesUrl}/#=jcId#" dataType="json" type="POST" contentType="application/json" />
									<kendo:dataSource-transport-destroy	url="${deleteJobNotesUrl}" dataType="json" type="POST" contentType="application/json" />
									<kendo:dataSource-transport-parameterMap>
									<script>
										function parameterMap(options, type) {
											return JSON.stringify(options);
										}
									</script>
									</kendo:dataSource-transport-parameterMap>
								</kendo:dataSource-transport>
								<kendo:dataSource-schema parse="parseJobNotes">
									<kendo:dataSource-schema-model id="jcnId">
										<kendo:dataSource-schema-model-fields>
											<kendo:dataSource-schema-model-field name="notes" type="string"/>
											<kendo:dataSource-schema-model-field name="jcnDt" type="date"/>	
										</kendo:dataSource-schema-model-fields>
									</kendo:dataSource-schema-model>
								</kendo:dataSource-schema>
						   </kendo:dataSource>
						</kendo:grid>
					</div>
				</kendo:tabStrip-item-content>
			</kendo:tabStrip-item>
			
			<kendo:tabStrip-item text="Job Team">
				<kendo:tabStrip-item-content>
					<div class="jobTeamtabStrip">
						<kendo:grid name="jobTeamgrid_#=jcId#" pageable="true" remove="jobteamRemove" edit="jobTeamEvent" resizable="true" sortable="true" reorderable="true"	selectable="true" scrollable="true">
							
							<kendo:grid-pageable pageSize="10"/>
							<kendo:grid-editable  mode="popup" confirmation="Are You Sure? You Want To Delete The Record?"/>
							<kendo:grid-toolbar>
								<kendo:grid-toolbarItem name="create" text="Add Job Team Details" />								
							</kendo:grid-toolbar>
							<kendo:grid-columns>
								<kendo:grid-column title="Job Department&nbsp;*" editor="jTeamDepartmentEditor" field="departmentName" width="100px" />
								<kendo:grid-column title="Job Staff&nbsp;*" editor="jTeamStaffEditor" field="userName" width="100px" />
								<kendo:grid-column title="Job Start Date" format= "{0:dd/MM/yyyy}" field="jctStartDt" width="100px" />									
								<kendo:grid-column title="Job End Date" format= "{0:dd/MM/yyyy}" field="jctEndDt" width="100px" />	
								<kendo:grid-column title="Job Hours" field="jctHours" width="100px" />	
								<kendo:grid-column title="Job Work Time&nbsp;*" field="jctWorktime" editor="jcWorkTimeEditor" width="100px" />	
								<kendo:grid-column title="Created By" field="createdBy" width="100px" />	
								<kendo:grid-column title="Last Updated By" field="lastUpdatedBy" width="100px" />	
								<kendo:grid-column title="Last Updated Date" format= "{0:dd/MM/yyyy hh:mm tt}" field="lastUpdatedDate" width="100px" />										
								<kendo:grid-column title=" " width="80px">
									<kendo:grid-column-command>
										<kendo:grid-column-commandItem name="edit"/>
										<kendo:grid-column-commandItem name="destroy"  text="Delete"/>	
									</kendo:grid-column-command>
								</kendo:grid-column>							
								
							</kendo:grid-columns>
							<kendo:dataSource  requestEnd="onRequestEndJobTeam" requestStart="onRequestStartJobTeam">
								<kendo:dataSource-transport>
									<kendo:dataSource-transport-read url="${readJobTeamUrl}/#=jcId#" dataType="json" type="POST" contentType="application/json" />
									<kendo:dataSource-transport-create url="${createJobTeamUrl}/#=jcId#" dataType="json" type="POST" contentType="application/json" />
									<kendo:dataSource-transport-update url="${updateJobTeamUrl}/#=jcId#" dataType="json" type="POST" contentType="application/json" />
									<kendo:dataSource-transport-destroy	url="${deleteJobTeamUrl}" dataType="json" type="POST" contentType="application/json" />
									<kendo:dataSource-transport-parameterMap>
									<script>
										function parameterMap(options, type) {
											return JSON.stringify(options);
										}
									</script>
									</kendo:dataSource-transport-parameterMap>
								</kendo:dataSource-transport>
								<kendo:dataSource-schema parse="parseJobTeam">
									<kendo:dataSource-schema-model id="jctId">
										<kendo:dataSource-schema-model-fields>
											<kendo:dataSource-schema-model-field name="jctdepartment"/>
											<kendo:dataSource-schema-model-field name="jctStaffId"/>
											<kendo:dataSource-schema-model-field name="jctStartDt" type="date"/>
											<kendo:dataSource-schema-model-field name="jctEndDt" type="date" defaultValue=""/>
											<kendo:dataSource-schema-model-field name="jctHours" type="number"/>
											<kendo:dataSource-schema-model-field name="jctWorktime" type="string"/>
											<kendo:dataSource-schema-model-field name="createdBy"/>
											<kendo:dataSource-schema-model-field name="lastUpdatedBy" type="string"/>
											<kendo:dataSource-schema-model-field name="lastUpdatedDate" type="date"/>																								
										</kendo:dataSource-schema-model-fields>
									</kendo:dataSource-schema-model>
								</kendo:dataSource-schema>
						   </kendo:dataSource>
						</kendo:grid>
					</div>
				</kendo:tabStrip-item-content>
			</kendo:tabStrip-item>
			
				<kendo:tabStrip-item text="Job Tools">
				<kendo:tabStrip-item-content>
					<div class="jobToolstabStrip">
						<kendo:grid name="jobToolsgrid_#=jcId#" pageable="true" remove="jobtoolsRemove" edit="jobToolsEvent"	resizable="true" sortable="true" reorderable="true"	selectable="true" scrollable="true">
							
							<kendo:grid-pageable pageSize="10"/>
							<kendo:grid-editable  mode="popup" confirmation="Are You Sure? You Want To Delete The Record?" />
							<kendo:grid-toolbar>
								<kendo:grid-toolbarItem name="create" text="Add Job Tools Details" />								
							</kendo:grid-toolbar>
							<kendo:grid-columns>
								<kendo:grid-column title="Job Tools Id" field="jctoolId" width="100px" hidden="true"/>								
								<kendo:grid-column title="Tool Name&nbsp;*" field="toolMaster" editor="toolMasterEditor" width="100px" />									
								<kendo:grid-column title="Quantity&nbsp;*" field="quantity" width="100px" />	
								<kendo:grid-column title="Created By" field="createdBy" width="100px" />	
								<kendo:grid-column title="Last Updated By" field="lastUpdatedBy" width="100px" />	
								<kendo:grid-column title="Last Updated Date" format= "{0:dd/MM/yyyy hh:mm tt}" field="lastUpdatedDate" width="100px" />	
								<kendo:grid-column title=" " width="80px">
									<kendo:grid-column-command>
										<kendo:grid-column-commandItem name="edit"/>
										<kendo:grid-column-commandItem name="destroy"  text="Delete"/>		
									</kendo:grid-column-command>
								</kendo:grid-column>							
																
							</kendo:grid-columns>
							<kendo:dataSource requestEnd="onRequestEndJobTools" requestStart="onRequestStartJobTools">
								<kendo:dataSource-transport>
									<kendo:dataSource-transport-read url="${readJobToolsUrl}/#=jcId#" dataType="json" type="POST" contentType="application/json" />
									<kendo:dataSource-transport-create url="${createJobToolsUrl}/#=jcId#" dataType="json" type="POST" contentType="application/json" />
									<kendo:dataSource-transport-update url="${updateJobToolsUrl}/#=jcId#" dataType="json" type="POST" contentType="application/json" />
									<kendo:dataSource-transport-destroy	url="${deleteJobToolsUrl}" dataType="json" type="POST" contentType="application/json" />
									<kendo:dataSource-transport-parameterMap>
									<script>
										function parameterMap(options, type) {
											return JSON.stringify(options);
										}
									</script>
									</kendo:dataSource-transport-parameterMap>
								</kendo:dataSource-transport>
								<kendo:dataSource-schema parse="parseJobTools">
									<kendo:dataSource-schema-model id="jctoolId">
										<kendo:dataSource-schema-model-fields>							
											<kendo:dataSource-schema-model-field name="toolMaster"/>
											<kendo:dataSource-schema-model-field name="toolMasterId"/>
											<kendo:dataSource-schema-model-field name="quantity" type="string"/>
											<kendo:dataSource-schema-model-field name="createdBy"/>
											<kendo:dataSource-schema-model-field name="lastUpdatedBy" type="string"/>	
											<kendo:dataSource-schema-model-field name="lastUpdatedDate" type="date"/>																							
										</kendo:dataSource-schema-model-fields>
									</kendo:dataSource-schema-model>
								</kendo:dataSource-schema>
						   </kendo:dataSource>
						</kendo:grid>
					</div>
				</kendo:tabStrip-item-content>
			</kendo:tabStrip-item>
			
			<kendo:tabStrip-item text="Job Material">
				<kendo:tabStrip-item-content>
					<div class="jobMaterialtabStrip">
						<kendo:grid name="jobMaterialgrid_#=jcId#" pageable="true" remove="jobmaterialRemove" dataBound="jobMaterialsdataBound" edit="jobMaterialEvent" resizable="true" sortable="true" reorderable="true"	selectable="true" scrollable="true">
							
							<kendo:grid-pageable pageSize="10"/>
							<kendo:grid-editable  mode="popup" confirmation="Are You Sure? You Want To Delete The Record?" />
							<kendo:grid-toolbar>
								<kendo:grid-toolbarItem name="create" text="Issue Job Materials" />								
							</kendo:grid-toolbar>
							<kendo:grid-columns>
								<kendo:grid-column title=" " width="80px"/>
								<kendo:grid-column title="Material Type&nbsp;*" editor="jcmTypeEditor" field="jcmType" width="100px" />									
								<kendo:grid-column title="Store Name&nbsp;*" editor="storeMasterEditor" field="storeMaster" width="100px" />	
								<kendo:grid-column title="Material Name&nbsp;*" editor="itemMasterEditor" field="jcmitemName" width="100px" />	
								<kendo:grid-column title="Unit Of Issue&nbsp;*" hidden="true" editor="imUomEditor" field="jcmimUom" width="100px" />	
								<kendo:grid-column title="Available Quantity" field="imQuantity" width="50px" hidden="true"/>
								<kendo:grid-column title="Material Description" hidden="true" editor="materialDescriptionEditor" field="jcmMaterial" width="100px" />		
								<kendo:grid-column title="Material Quantity&nbsp;*" field="jcmQuantinty" width="50px" />									
								<kendo:grid-column title="Returned Quantity" field="returnedQuantinty" width="50px" />									
								<kendo:grid-column title="Part Number&nbsp;*" field="jcmPartno" width="80px" />									
								<kendo:grid-column title="Rate&nbsp;*" field="jcmrate" width="50px" />								
								<kendo:grid-column title="Created By" hidden="true" field="createdBy" width="100px" />	
								<kendo:grid-column title="Last Updated By" hidden="true" field="lastUpdatedBy" width="100px" />	
								<kendo:grid-column title="Last Updated Date" hidden="true" format= "{0:dd/MM/yyyy hh:mm tt}" field="lastUpdatedDate" width="100px" />										
								<kendo:grid-column title="Material Status" field="status" width="100px" />										
								<kendo:grid-column title=" " width="80px">
									<kendo:grid-column-command>
										<kendo:grid-column-commandItem name="edit"/>
										<kendo:grid-column-commandItem name="destroy"  text="Delete"/>	
									</kendo:grid-column-command>
								</kendo:grid-column>					
								
							</kendo:grid-columns>
							<kendo:dataSource requestEnd="onRequestEndJobMaterial" requestStart="onRequestStartJobMaterial">
								<kendo:dataSource-transport>
									<kendo:dataSource-transport-read url="${readJobMaterialUrl}/#=jcId#" dataType="json" type="POST" contentType="application/json" />
									<kendo:dataSource-transport-create url="${createJobMaterialUrl}/#=jcId#" dataType="json" type="POST" contentType="application/json" />
									<kendo:dataSource-transport-update url="${updateJobMaterialUrl}/#=jcId#" dataType="json" type="POST" contentType="application/json" />
									<kendo:dataSource-transport-destroy	url="${deleteJobMaterialUrl}" dataType="json" type="POST" contentType="application/json" />
									<kendo:dataSource-transport-parameterMap>
									<script>
										function parameterMap(options, type) {
											return JSON.stringify(options);
										}
									</script>
									</kendo:dataSource-transport-parameterMap>
								</kendo:dataSource-transport>
								<kendo:dataSource-schema parse="parseJobMaterial">
									<kendo:dataSource-schema-model id="jcmId">
										<kendo:dataSource-schema-model-fields>											
											<kendo:dataSource-schema-model-field name="jcmType"/>
											<kendo:dataSource-schema-model-field name="jcmTypeId"/>
											<kendo:dataSource-schema-model-field name="storeMaster"/>
											<kendo:dataSource-schema-model-field name="jcmitemName"/>
											<kendo:dataSource-schema-model-field name="jcmimUom"/>
											<kendo:dataSource-schema-model-field name="imQuantity" type="string"/>
											<kendo:dataSource-schema-model-field name="jcmQuantinty" type="string"/>
											<kendo:dataSource-schema-model-field name="returnedQuantinty" type="string"/>
											<kendo:dataSource-schema-model-field name="jcmMaterial" type="string"/>	
											<kendo:dataSource-schema-model-field name="jcmPartno" type="string"/>
											<kendo:dataSource-schema-model-field name="jcmrate" type="string"/>																						
											<kendo:dataSource-schema-model-field name="createdBy" type="string"/>
											<kendo:dataSource-schema-model-field name="lastUpdatedBy" type="string"/>	
											<kendo:dataSource-schema-model-field name="lastUpdatedDate" type="date"/>																							
											<kendo:dataSource-schema-model-field name="status" type="string" defaultValue="Issue"/>																							
										</kendo:dataSource-schema-model-fields>
									</kendo:dataSource-schema-model>
								</kendo:dataSource-schema>
						   </kendo:dataSource>
						</kendo:grid>
					</div>
				</kendo:tabStrip-item-content>
			</kendo:tabStrip-item>
			
			<kendo:tabStrip-item text="Labour Task">
				<kendo:tabStrip-item-content>
					<div class="jobLabourTasktabStrip">
						<kendo:grid name="jobLabourTaskgrid_#=jcId#" pageable="true" remove="joblabourtaskRemove"  edit="jobLabourTaskEvent" resizable="true" sortable="true" reorderable="true"	selectable="true" scrollable="true">
							
							<kendo:grid-pageable pageSize="10"/>
							<kendo:grid-editable  mode="popup" confirmation="Are You Sure? You Want To Delete The Record?" />
							<kendo:grid-toolbar>
								<kendo:grid-toolbarItem name="create" text="Add Labour Task" />								
							</kendo:grid-toolbar>
							<kendo:grid-columns>
								<kendo:grid-column title="Labour Type&nbsp;*" field="jclType" width="100px" />									
								<kendo:grid-column title="Labour Description" filterable="false" editor="jobLabourDescriptionEditor" field="jclLabourdesc" width="200px" />	
								<kendo:grid-column title="Job Hours" field="jclHours" width="100px" />	
								<kendo:grid-column title="Labour Billable&nbsp;*" editor="jclBillableEditor" field="jclBillable" width="100px" />
								<kendo:grid-column title="Labour Rate&nbsp;*" field="jclRate" width="100px" />									
								<kendo:grid-column title="Created By" field="createdBy" width="100px" />	
								<kendo:grid-column title="Last Updated By" field="lastUpdatedBy" width="100px" />	
								<kendo:grid-column title="Last Updated Date" format= "{0:dd/MM/yyyy hh:mm tt}" field="lastUpdatedDate" width="100px" />										
								<kendo:grid-column title="" width="80px">
								<kendo:grid-column-command>
										<kendo:grid-column-commandItem name="edit"/>
										<kendo:grid-column-commandItem name="destroy"  text="Delete"/>	
									</kendo:grid-column-command>
								</kendo:grid-column>						
								
							</kendo:grid-columns>
							<kendo:dataSource requestEnd="onRequestEndLabourTask" requestStart="onRequestStartLabourTask">
								<kendo:dataSource-transport>
									<kendo:dataSource-transport-read url="${readJobLabourTaskUrl}/#=jcId#" dataType="json" type="POST" contentType="application/json" />
									<kendo:dataSource-transport-create url="${createJobLabourTaskUrl}/#=jcId#" dataType="json" type="POST" contentType="application/json" />
									<kendo:dataSource-transport-update url="${updateJobLabourTaskUrl}/#=jcId#" dataType="json" type="POST" contentType="application/json" />
									<kendo:dataSource-transport-destroy	url="${deleteJobLabourTaskUrl}" dataType="json" type="POST" contentType="application/json" />
									<kendo:dataSource-transport-parameterMap>
									<script>
										function parameterMap(options, type) {
											return JSON.stringify(options);
										}
									</script>
									</kendo:dataSource-transport-parameterMap>
								</kendo:dataSource-transport>
								<kendo:dataSource-schema parse="parseLabourTask">
									<kendo:dataSource-schema-model id="jclId">
										<kendo:dataSource-schema-model-fields>											
											<kendo:dataSource-schema-model-field name="jclType" type="string"/>
											<kendo:dataSource-schema-model-field name="jclLabourdesc" type="string"/>
											<kendo:dataSource-schema-model-field name="jclHours" type="number"/>
											<kendo:dataSource-schema-model-field name="jclBillable" type="string"/>
											<kendo:dataSource-schema-model-field name="jclRate" type="string"/>												
											<kendo:dataSource-schema-model-field name="createdBy" type="string"/>
											<kendo:dataSource-schema-model-field name="lastUpdatedBy" type="string"/>	
											<kendo:dataSource-schema-model-field name="lastUpdatedDate" type="date"/>																							
										</kendo:dataSource-schema-model-fields>
									</kendo:dataSource-schema-model>
								</kendo:dataSource-schema>
						   </kendo:dataSource>
						</kendo:grid>
					</div>
				</kendo:tabStrip-item-content>
			</kendo:tabStrip-item>		
		
			<kendo:tabStrip-item text="Documents Of Work">
				<kendo:tabStrip-item-content>
					<div class="jobDocumentstabStrip">
						<kendo:grid name="jobDocumentsgrid_#=jcId#" remove="jobdocumentsRemove" edit="jobdocumentEvent" dataBound="jobDocumentsDatabound" pageable="true" resizable="true" sortable="true" reorderable="true"	selectable="true" scrollable="true">
							
							<kendo:grid-pageable pageSize="10"></kendo:grid-pageable>
								<kendo:grid-editable mode="popup"  confirmation="Are sure you want to delete this item ?"/>
						       <kendo:grid-toolbar >
						            <kendo:grid-toolbarItem name="create" text="Add Document" />
						        </kendo:grid-toolbar> 
        						<kendo:grid-columns>
       								 <kendo:grid-column title="&nbsp;" width="150px" >
						             	<!-- File Upload Button Purpose -->
						             </kendo:grid-column>
								     <kendo:grid-column title="Document Name&nbsp;*"  field="documentName" width="100px"></kendo:grid-column>
								     <kendo:grid-column title="Document Type&nbsp;*" editor="documentTypeEditor" field="documentType"  width="100px"></kendo:grid-column>
								     <kendo:grid-column title="Document Description" field="documentDescription" width="160px"></kendo:grid-column>
								 	 <kendo:grid-column title="&nbsp;" width="175px" >
						            	<kendo:grid-column-command>
						            		<kendo:grid-column-commandItem name="edit"/>
						            		<kendo:grid-column-commandItem name="View" click="downloadFile"/>
						            		<kendo:grid-column-commandItem name="destroy" />
						            	</kendo:grid-column-command>
						            </kendo:grid-column>		
        								
        						</kendo:grid-columns>
        						 <kendo:dataSource requestEnd="onRequestJobDocuments" requestStart="onRequestStartJobDocuments">
						            <kendo:dataSource-transport>
						                <kendo:dataSource-transport-read url="${readJobDocumentsUrl}/#=jcId#" dataType="json" type="POST" contentType="application/json"/>
						                <kendo:dataSource-transport-create url="${createJobDocumentsUrl}/#=jcId#" dataType="json" type="POST" contentType="application/json" />
						                <kendo:dataSource-transport-update url="${updateJobDocumentsUrl}/#=jcId#" dataType="json" type="POST" contentType="application/json" />
						                <kendo:dataSource-transport-destroy url="${deleteJobDocumentsUrl}" dataType="json" type="POST" contentType="application/json" />
						               <kendo:dataSource-transport-parameterMap>
						                	<script type="text/javascript">
							                	function parameterMap(options,type) 
							                	{
							                		return JSON.stringify(options);	                		
							                	}
						                	</script>
						                 </kendo:dataSource-transport-parameterMap>
						            </kendo:dataSource-transport>
						            <kendo:dataSource-schema>
						                <kendo:dataSource-schema-model id="jobDocId">
						                    <kendo:dataSource-schema-model-fields>
						                    	<kendo:dataSource-schema-model-field name="jobDocId"  editable="false" />
						                    	<kendo:dataSource-schema-model-field name="documentType" type="string"/>   
						                    	<kendo:dataSource-schema-model-field name="documentName" type="string"/>
						                 		<kendo:dataSource-schema-model-field name="documentDescription" type="string"/>
						                    </kendo:dataSource-schema-model-fields>
						                 </kendo:dataSource-schema-model>
						             </kendo:dataSource-schema>
						          </kendo:dataSource>
        				</kendo:grid>       						
					</div>
				</kendo:tabStrip-item-content>
		   </kendo:tabStrip-item>			
			<kendo:tabStrip-item text="">
				<kendo:tabStrip-item-content>
					<div class="jobDocumentstabStrip">
						<kendo:grid name="jobMaterialReturngrid_#=jcId#" pageable="true" edit="jobMaterialReturnsEvent" resizable="true" sortable="true" reorderable="true"	selectable="true" scrollable="true">
							
							<kendo:grid-pageable pageSize="10"/>
							<kendo:grid-editable  mode="popup" confirmation="Are You Sure? You Want To Delete The Record?" />
							<kendo:grid-toolbar>
								<kendo:grid-toolbarItem name="create" text="Return Job Materials" />								
							</kendo:grid-toolbar>
							<kendo:grid-columns>
								<kendo:grid-column title=" " width="80px"/>
								<kendo:grid-column title="Material Type" field="jcmType" width="100px" />									
								<kendo:grid-column title="Store Name"  field="storeMaster" width="100px" />	
								<kendo:grid-column title="Material Name" field="jcmitemName" width="100px" />	
								<kendo:grid-column title="Unit Of Issue" hidden="true"  field="jcmimUom" width="100px" />	
								<kendo:grid-column title="Material Quantity" field="jcmQuantinty" width="50px" />									
								<kendo:grid-column title="Quantity To Return" field="returnedQuantinty" width="50px" />									
								<kendo:grid-column title="Material Status" field="status" width="100px" />										
								<kendo:grid-column title=" " width="80px">
									<kendo:grid-column-command>
										<kendo:grid-column-commandItem name="edit"/>
									</kendo:grid-column-command>
								</kendo:grid-column>							
								
							</kendo:grid-columns>
							<kendo:dataSource requestEnd="onRequestEndJobMaterial">
								<kendo:dataSource-transport>
									<kendo:dataSource-transport-read url="${readJobMaterialUrl}/#=jcId#" dataType="json" type="POST" contentType="application/json" />
									<kendo:dataSource-transport-update url="${updateJobMaterialUrl}/#=jcId#" dataType="json" type="POST" contentType="application/json" />
									<kendo:dataSource-transport-parameterMap>
									<script>
										function parameterMap(options, type) {
											return JSON.stringify(options);
										}
									</script>
									</kendo:dataSource-transport-parameterMap>
								</kendo:dataSource-transport>
								<kendo:dataSource-schema>
									<kendo:dataSource-schema-model id="jcmId">
										<kendo:dataSource-schema-model-fields>											
											<kendo:dataSource-schema-model-field name="jcmType"/>
											<kendo:dataSource-schema-model-field name="jcmTypeId"/>
											<kendo:dataSource-schema-model-field name="storeMaster"/>
											<kendo:dataSource-schema-model-field name="jcmitemName"/>
											<kendo:dataSource-schema-model-field name="jcmimUom"/>											
											<kendo:dataSource-schema-model-field name="jcmQuantinty" type="string"/>
											<kendo:dataSource-schema-model-field name="returnedQuantinty" type="string"/>
											<kendo:dataSource-schema-model-field name="jcmMaterial" type="string"/>																																	
											<kendo:dataSource-schema-model-field name="status" type="string" defaultValue="Issue"/>																							
										</kendo:dataSource-schema-model-fields>
									</kendo:dataSource-schema-model>
								</kendo:dataSource-schema>
						   </kendo:dataSource>
						</kendo:grid>					
					</div>
				</kendo:tabStrip-item-content>
		   </kendo:tabStrip-item> 
		   
		   <kendo:tabStrip-item text="JobCard Oher Details">
				<kendo:tabStrip-item-content>
				<div class="jobCardOthertabStrip">
					<br>
					<h6>Job Name: #=jobName#</h6>
					<br>
					<table>
						<tr>
							<td><b>Job Expected SLA (Days) : </b></td>
							<td>#=jobExpectedSla#</td>
							<td><b>Job SLA :</b></td>
							<td>#=jobSla#</td>
						</tr>
						<tr>
							<td><b>Job Assests : </b></td>
							<td>#=jobAssets#</td>
							<td><b>Job Generated By : </b></td>
							<td>#=jobGeneratedby#</td>
						</tr>
					</table>
					<br>
					<br>
					</div>
				</kendo:tabStrip-item-content>
			</kendo:tabStrip-item>
		   		
		</kendo:tabStrip-items>
	</kendo:tabStrip>
</kendo:grid-detailTemplate>  


	
	<div id="alertsBox" title="Alert"></div>  	
	<div id="dialogBox" title="Job Details"></div>
	<script>
	$("#grid").on("click",".k-grid-jobCardsTemplatesDetailsExport", function(e) {
		  window.open("./jobCardsTemplate/jobCardsTemplatesDetailsExport");
	});	  

	$("#grid").on("click",".k-grid-jobCardsPdfTemplatesDetailsExport", function(e) {
		  window.open("./jobCardsPdfTemplate/jobCardsPdfTemplatesDetailsExport");
	});
	
	var toolname="";
	var toolQuantityBasedOnToolName=0;
	
	$("#grid").on("click", ".k-grid-Clear_Filter", function(){
	    $("form.k-filter-menu button[type='reset']").slice().trigger("click");
		var grid = $("#grid").data("kendoGrid");
		grid.dataSource.read();
		grid.refresh();
	});
	
	$("#grid").on("click", "#totalcost", function(e) {		
		
		var result=securityCheckForActionsForStatus("./maintainance/jobcards/totalcostButton");
		
		if(result=="success"){
			var widget = $("#grid").data("kendoGrid");
			var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));		
			var labourSize;
			var teamSize;
			var jcTools;
			var jobMaterials;
			var totalTime;
			var jobLabourRate;
			 $.ajax({
		 			type : "POST",
		 			async: false,
		 			url : './jobcards/getAllDatailsOfJob/'+dataItem.id,
		 			dataType : "JSON",
		 			success : function(data) {		 				
		 				$.each(data, function(index, element) {
		 		            if(index=="teamSize"){
		 		            	teamSize=element;	 		            	
		 		            }else if(index=="jcTools"){
		 		            	jcTools=element;	 		            	
		 		            }else if(index=="labourSize"){
		 		            	labourSize=element;	 		           
		 		            }else if(index=="jobMaterials"){
		 		            	jobMaterials=element;	 		            	
		 		            }else if(index=="totalTime"){
		 		            	totalTime=element;	 		            	
		 		            }else if(index=="jobLabourRate"){
		 		            	jobLabourRate=element;	 		            	
		 		            }else{
		 		            	alert("No Details to Display");
		 		            }
		 		        });
		 				
		 			}
		 	});
			 var contactList ="<table  align='left'><tr>"+		
			 "<tr><td><span><b>Labour Size</b></span></td><td>"+labourSize+"</td></tr>"+
			 "<tr><td><span><b>Team Size</b></span></td><td>"+teamSize+"</td></tr>"+
			 "<tr><td><span><b>Tools Used</b></span></td><td>"+jcTools+"</td></tr>"+
			 "<tr><td><span><b>Material Used</b></span></td><td>"+jobMaterials+"</td>"+
			 "<tr><td><span><b>Total Work Time</b></span></td><td>"+totalTime+"</td>"+
			 "<tr><td><span><b>Total Rate</b></span></td><td>"+jobLabourRate+"</td>"+
			 "</tr></table>";		 
			
			  $("#dialogBox").html("");
				$("#dialogBox").html(contactList);
				$("#dialogBox").dialog({
					modal : true,
					draggable: false,
					resizable: true,
					width:450,	            
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				}); 	
		}		
			 
	});
	 
	
	
	function parse (response) {   
	    $.each(response, function (idx, elem) {
            if (elem.jobStartDt && typeof elem.jobStartDt === "string") {
                elem.jobStartDt = kendo.parseDate(elem.jobStartDt, "yyyy-MM-ddTHH:mm:ss.fffZ");
            }
            if (elem.jobEndDt && typeof elem.jobEndDt === "string") {
                elem.jobEndDt = kendo.parseDate(elem.jobEndDt, "yyyy-MM-ddTHH:mm:ss.fffZ");
            }
            if (elem.lastUpdatedDate && typeof elem.lastUpdatedDate === "string") {
                elem.lastUpdatedDate = kendo.parseDate(elem.lastUpdatedDate, "yyyy-MM-ddTHH:mm:ss.fffZ");
            }
            if (elem.jobDt && typeof elem.jobDt === "string") {
                elem.jobDt = kendo.parseDate(elem.jobDt, "yyyy-MM-ddTHH:mm:ss.fffZ");
            }
            if (elem.expDate && typeof elem.expDate === "string") {
                elem.expDate = kendo.parseDate(elem.expDate, "yyyy-MM-ddTHH:mm:ss.fffZ");
            }
            
           var res1 = [];
			$.each(response[idx].jobAssets, function(idx, elem) {						
				res1.push(elem.assetName);				
			});
			res1.join(",");
			response[idx].jobAssets = res1.sort().toString();		
        });
        return response;
	} 
	
	
	
   (function ($, kendo) {
        $.extend(true, kendo.ui.validator, {
             rules: { // custom rules           	 
            	 
                 jobNameValidation: function (input, params) {
                     //check for the name attribute 
                     if (input.attr("name") == "jobName") {
                    	 return $.trim(input.val()) !== "";
                     }
                     return true;
                 },	  
                 /* jobGroupvalidation: function (input, params) {
                     //check for the name attribute 
                     if (input.attr("name") == "jobGroup") {
                    	 return $.trim(input.val()) !== "";
                     }
                     return true;
                 }, */
                 jobDtValidation: function (input, params) {
                     //check for the name attribute 
                     if (input.attr("name") == "jobDt") {
                    	 return $.trim(input.val()) !== "";
                     }
                     return true;
                 },
                 jobExpectedSlaValidation: function (input, params) {
                     //check for the name attribute 
                     if (input.attr("name") == "jobExpectedSla") {
                    	 return $.trim(input.val()) !== "";
                     }
                     return true;
                 },	   
                 jobSlaPatternValidation: function (input, params) {
                	 if (input.filter("[name='jobSla']").length && input.val()) {
                         return /^[0-9]+$/.test(input.val());
                     }
                     return true;
                 },	    
                 jobExpectedSlaPatternValidation: function (input, params) {
                	 if (input.filter("[name='jobExpectedSla']").length && input.val()) {
                         return /^[0-9]+$/.test(input.val());
                     }
                     return true;
                 },
                 
                 
             },
           //custom rules messages
             messages: { 
            	 
            	 jobDtValidation:"Job Created Date is required", 
            	 jobExpectedSlaValidation:"Job Expected SLA is required",  
            	 jobNameValidation:"Job Name is required",           		
            	 jobExpectedSlaPatternValidation:"Only Numbers Are Allowed",
            	 jobSlaPatternValidation:"Only Numbers Are Allowed",
            	/*  jobGroupvalidation:"Job Group is required" */
             }
        });
    })(jQuery, kendo);

	
	
	function departmentEditor(container, options) {
		$('<input name="department" id="dept" data-text-field="departmentName" data-value-field="departmentId" data-bind="value:' + options.field + '" required="true"/>')
				.appendTo(container).kendoComboBox({
					 placeholder: "Select Department",
					 autoBind : false,	
		     		 filter:"startswith",	     	
					dataSource : {
						transport : {
							read : "${getdepartmentname}"
						}
					}
				});
		 $('<span class="k-invalid-msg" data-for="department"></span>').appendTo(container);
	}
	
	function jobMtEditor(container, options) {
		$('<input name="Maintainance Type" id="dept1" data-text-field="jobMt" style="width:180px;" data-value-field="jobMtId" data-bind="value:' + options.field + '" required="true"/>')
				.appendTo(container).kendoComboBox({
					 placeholder: "Select",
		     		 filter:"startswith",	     	
					 dataSource : {
						transport : {
							read : "${getMaintainance}"
						}
					}
				});
		 $('<span class="k-invalid-msg" data-for="Maintainance Type"></span>').appendTo(container);
	}	 
	
	function ownerEditor(container, options) {
		$('<input name="Owner" data-text-field="pn_Name" id="owner" style="width:180px;border-color:red;" data-value-field="personId" data-bind="value:' + options.field + '" required="true"/>')
				.appendTo(container).kendoComboBox({
					 placeholder: "Select Owner",
					 cascadeFrom:"dept",
					 autoBind : false,	
		     		 filter:"startswith",
		     		  template : '<table><tr><td rowspan=2><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.personId#'/>" width=40 height=55  /></span></td>'
		  			+ '<td><span class="k-state-default"><b>#: data.pn_Name #</b></span><br></td>'+
		  			'<tr><td ><span class="k-state-default">Person Type:<b>#: data.personType #</b></span><br></td></tr></table>',
					dataSource : {
						transport : {
							read : "${getOwnername}"
						}
					}
				});
		 $('<span class="k-invalid-msg" data-for="Owner"></span>').appendTo(container);
	}
	
	 function jobTypeEditor(container, options) {
     	   $(
     	     '<input name="Job Type" data-text-field="jobType"  data-value-field="jobTypeId" data-bind="value:' + options.field + '" required="true"/>')
     	     .appendTo(container).kendoComboBox({ 
     	    	change:onChangeJobType,
     	    	dataSource : {
					transport : {
						read : "${getJobTypes}"
					}
				}         	                 	      
     	   });
     	  $('<span class="k-invalid-msg" data-for="Job Type"></span>').appendTo(container);
      }  
	 
	 function onChangeJobType (){
		 var value=this.dataItem();
		 $('input[name="jobExpectedSla"]').val(value.jobSLA);	  
		 $('input[name="jobExpectedSla"]').change(); 
		 $('input[name="jobExpectedSla"]').attr('readonly', 'readonly');  
	 }
	 
	 function jobPriorityEditor(container, options) {
   	   var data = ["LOW","MEDIUM","HIGH","STATUTORY"];
   	   $(
   	     '<input name="Job Priority" data-text-field="" style="width:180px;" data-value-field="" data-bind="value:' + options.field + '" required="true" />Days')
   	     .appendTo(container).kendoComboBox({   	    
   	      dataSource :data            	                 	      
   	   });
	   	$('<span class="k-invalid-msg" data-for="Job Priority"></span>').appendTo(container);
     }
	 
	 
	function assetEditor(container, options) {
		var model = options.model;
		model.jobAssets = model.jobAssetsDummy;		
		$(
				'<select multiple="multiple" name="Job Assets" data-text-field="assetName" style="width:180px;" data-value-field="assetId" id="asset" data-bind="value:' + options.field + '" required="true"/>')
				.appendTo(container).kendoMultiSelect({
					optionLabel : {
						assetName : "Select",
						assetId : "",
					},	
				 	dataSource : {
						transport : {
							read : {
								url : "${readAssetUrl}",
								type : "POST"
							}
						}
					}

		 });
		 $('<span class="k-invalid-msg" data-for="Job Assets"></span>').appendTo(container);
	 } 
	
	 function radioEditor(container, options) {
			$(
					'<input type="radio" name=' + options.field + ' value="cat" /> Category &nbsp;&nbsp;&nbsp; <input type="radio" name=' + options.field + ' value="loc"/> Location &nbsp;&nbsp;&nbsp; <input type="radio" name=' + options.field + ' value="all" /> All <br>')
					.appendTo(container);
		}
	 $(document)
		.on(
				'change',
				'input[name="radioBtn"]:radio',
				function() {

					var radioValue = $('input[name=radioBtn]:checked')
							.val();

					if (radioValue == 'cat') {

						$('div[data-container-for="assetTreeCat"]').show();
						$('label[for="assetTreeCat"]').closest('.k-edit-label').show();
						$('div[data-container-for="assetTreeLoc"]').hide();
						$('label[for="assetTreeLoc"]').closest('.k-edit-label').hide();

					} else if(radioValue == 'loc'){
						
						$('div[data-container-for="assetTreeLoc"]').show();
						$('label[for="assetTreeLoc"]').closest('.k-edit-label').show();
						$('div[data-container-for="assetTreeCat"]').hide();
						$('label[for="assetTreeCat"]').closest('.k-edit-label').hide();
					}
					else if(radioValue == 'all'){
					
						$('div[data-container-for="assetTreeLoc"]').hide();
						$('label[for="assetTreeLoc"]').closest('.k-edit-label').hide();
						$('div[data-container-for="assetTreeCat"]').hide();
						$('label[for="assetTreeCat"]').closest('.k-edit-label').hide();						
						
						$("#asset").data("kendoMultiSelect").setDataSource({
							transport : {
								read : {
									url : "./asset/getAllAssetsForAll",
									dataType : "json",
									type : 'GET'
								}
							},dataTextField : "assetName",
							dataValueField : "assetId", 
						});						
					}
				});
	 
	 function catEditor(container, options) {

			$('<div style="max-height: 100px; overflow: auto; "></div>')
					.appendTo(container).kendoTreeView({

					dataTextField : "assetcatText",
					template : " #: item.assetcatText # <input type='hidden' id='hiddenId' class='#: item.assetcatText ##: item.assetcatId#' value='#: item.assetcatId#'/>",
					select : onCatSelect,
					name : "treeview",
					dataSource : {
						 transport: {
	                         read: {
	                             url: "${transportReadUrlCat}",
	                             contentType: "application/json",
	                             type : "GET"
	                         }
	                     },
	                     schema: {
	                         model: {
	                             id: "assetcatId",
	                             hasChildren: "hasChilds"
	                         }
	                     }
						}
					});
		} 
		
		 
		 function locEditor(container, options) {

				$('<div style="max-height: 100px; overflow: auto; "></div>')
						.appendTo(container).kendoTreeView({

						dataTextField : "assetlocText",
						template : " #: item.assetlocText # <input type='hidden' id='hiddenId' class='#: item.assetlocText ##: item.assetlocId#' value='#: item.assetlocId#'/>",
						select : onLocSelect,
						name : "treeview2",
						dataSource : {
							 transport: {
		                         read: {
		                             url: "${transportReadUrlLoc}",
		                             contentType: "application/json",
		                             type : "GET"
		                         }
		                     },
		                     schema: {
		                         model: {
		                             id: "assetlocId",
		                             hasChildren: "hasChilds"
		                         }
		                     }
							}
						});
			} 

		var nodeid = "";
	    var dropDownDataSource = "";
	    function onCatSelect(e) {

	    	
			nodeid = $('.k-state-hover').find('#hiddenId').val();
			var nn = $('.k-state-hover').html();
			var spli = nn.split(" <input");
			$('#editNodeText').val(spli[0].trim());

			var kitems = $(e.node).add(
					$(e.node).parentsUntil('.k-treeview', '.k-item'));
			texts = $.map(kitems, function(kitem) {
				return $(kitem).find('>div span.k-in').text();
			});			
		
			$("#asset").data("kendoMultiSelect").setDataSource({
				 transport: {
			            read: {
			                url     : "./asset/getAllAssetsOnCatId/"+nodeid, // url to remote data source 
			                dataType: "json",
			                type    : 'GET'
			            }
			        },dataTextField : "assetName",
					dataValueField : "assetId", 
			});
			
		}
	  
		function onLocSelect(e) {

			nodeid = $('.k-state-hover').find('#hiddenId').val();
			var nn = $('.k-state-hover').html();
			var spli = nn.split(" <input");
			$('#editNodeText').val(spli[0].trim());

			var kitems = $(e.node).add(
					$(e.node).parentsUntil('.k-treeview', '.k-item'));
			texts = $.map(kitems, function(kitem) {
				return $(kitem).find('>div span.k-in').text();
			});			
			
			$("#asset").data("kendoMultiSelect").setDataSource({
				transport : {
					read : {
						url : "./asset/getAllAssetsOnLocId/" + nodeid, // url to remote data source 
						dataType : "json",
						type : 'GET'
					}
				},
				dataTextField : "assetName",
				dataValueField : "assetId", 

			});			
		}
	    
	function jobdescriptionEditor(container, options) 
	{
        $('<textarea data-text-field="jobDescription" data-value-field="jobDescription" data-bind="value:' + options.field + '" style="width: 148px; height: 58px;"/>')
             .appendTo(container);        
	}
	
	function dateEditor(container, options) {
	    $('<input name="' + options.field + '"/>')
	            .appendTo(container)
	            .kendoDateTimePicker({
	                format:"dd/MM/yyyy hh:mm tt",
	                timeFormat:"hh:mm tt"      	                
	    });
	}	
	
	function jobLabourDescriptionEditor(container, options) 
	{
        $('<textarea data-text-field="jclLabourdesc" data-value-field="jclLabourdesc" data-bind="value:' + options.field + '" style="width: 148px; height: 50px;"/>')
             .appendTo(container);        
	}		
	
	function jobCardsEvent(e) {	
			if($("#grid").data("kendoGrid").dataSource.filter()){
	    		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
	 			var grid = $("#grid").data("kendoGrid");
	 			grid.dataSource.read();
	 			grid.refresh();
	        }
			
			$(".k-edit-form-container").css({
				"width" : "700px"
			});
			$(".k-window").css({
				"top": "150px"
			});
			
			$('.k-edit-label:nth-child(20n+1)').each(function(s) {
				$(this).nextAll(':lt(19)').addBack().wrapAll('<div class="wrappers"/>');
			});
			
			$(".k-edit-field").each(function () {
	 		  	 $(this).find("#temPID").parent().remove();
	 	    }); 		
			$(".k-edit-field").each(function () {
	 		  	 $(this).find("#temsuspend").parent().remove();
	 	    }); 					
		 	
			 $('label[for="createdBy"]').parent().remove();
		   	 $('label[for="lastUpdatedBy"]').parent().remove();
		     $('label[for="lastUpdatedDate"]').parent().remove();
		     $('label[for="jobGeneratedby"]').parent().remove();
		   	 $('label[for="status"]').parent().remove();
		   	 $('label[for="suspendStatus"]').parent().remove();
		     $('label[for="jobStartDt"]').parent().remove();
		     $('label[for="jobEndDt"]').parent().remove();
		   	 $('label[for="jobCcId"]').parent().remove();
		     $('label[for="jobBmsId"]').parent().remove();
		     $('label[for="jobAmsId"]').parent().remove();
		     $('label[for="jobMtId"]').parent().remove();
		     $('label[for="jobSla"]').parent().remove();		    	    
		     $('label[for="undefined"]').parent().remove();		  		    
		     $('label[for=""]').parent().hide();		  		    
		     $('label[for="jobNo"]').parent().remove();		  		    

		     $('input[name="createdBy"]').parent().remove();
		     $('input[name="lastUpdatedBy"]').parent().remove();
		     $('input[name="jobNo"]').parent().remove();		     
		  	 $('input[name="jobStartDt"]').parent().remove(); 
		     $('input[name="jobEndDt"]').parent().remove(); 
		     $('input[name="status"]').parent().remove(); 
		     $('input[name="suspendStatus"]').parent().remove(); 
		     $('input[name="jobMtId"]').parent().remove(); 
		     $('input[name="jobSla"]').parent().remove(); 
		     $('input[name="jobExpectedSla"]').after('<label>&nbsp;Days</label>');
		     $('label[for="jobNo"]').after('<label style=color:red;>&nbsp;*</label>');
		     $('label[for="jobName"]').after('<label style=color:red;>&nbsp;*</label>');
		     /* $('label[for="jobGroup"]').after('<label style=color:red;>&nbsp;*</label>'); */
		     $('label[for="jobDepartment"]').after('<label style=color:red;>&nbsp;*</label>');
		     $('label[for="jobExpectedSla"]').after('<label style=color:red;>&nbsp;*</label>');
		     $('label[for="jobAssets"]').after('<label style=color:red;>&nbsp;*</label>');
		     $('label[for="jobOwnerId"]').after('<label style=color:red;>&nbsp;*</label>');
		     $('label[for="jobType"]').after('<label style=color:red;>&nbsp;*</label>');
		     $('label[for="jobPriority"]').after('<label style=color:red;>&nbsp;*</label>');
		     $('label[for="jobMt"]').after('<label style=color:red;>&nbsp;*</label>');
		     $('input[name="jobExpectedSla"]').attr('readonly', 'readonly'); 
		     $('div[data-container-for="jobStartDt"]').remove();
			 $('div[data-container-for="jobEndDt"]').remove();
			 $('div[data-container-for="lastUpdatedDate"]').remove();   			  
				     
		     $('input[name="jobGeneratedby"]').parent().remove();  		 
			 $('input[name="jobCcId"]').parent().remove();		 
		     $('input[name="jobBmsId"]').parent().remove();  
		     $('input[name="jobAmsId"]').parent().remove(); 		
		     $('input[name="jobAmsId"]').parent().remove(); 
		     
		     var jobDt = $('input[name="jobDt"]').data("kendoDatePicker");
		     var today = new Date();
		     jobDt.min(today);
		     
		     var myDatePicker = $('input[name="expDate"]').data("kendoDateTimePicker");
		     var today = new Date();
		     myDatePicker.min(today);
			    
  	    	if (e.model.isNew()){  	
  	    		securityCheckForActions("./maintainance/jobcards/addButton");
  	    		
  	    		
  			     
  	    		$(".k-edit-field").each(function () {
  		 		  	 $(this).find(".k-grid-totlcost").parent().remove();
  		 	    }); 
	  	    	 $(".k-window-title").text("Add Job Card Details");
	  		 	 $(".k-grid-update").text("Create");   	
	  		 	$(".k-grid-update").click(function (){
	  		 		var firstItem = $('#grid').data().kendoGrid.dataSource.data()[0];
	  		 		if(firstItem.get("expDate")!=null && firstItem.get("expDate")!=""){
	  		 			if(firstItem.get("expDate")<firstItem.get("jobDt")){
		  		 			alert("Expected To Complte is less than Job Date");
		  		 			return false;
		  		 		}	
	  		 		}
	  		 		
	  		 	});
  		 	} 
  		
	  		else{	
	  			securityCheckForActions("./maintainance/jobcards/editButton");
	  			if(e.model.status=="CLOSED"){
	  				var grid = $("#grid").data("kendoGrid");
	  				grid.cancelRow();
	  				alert("Job is Already Closed,You Cannot Edit");
	  				grid.dataSource.read();
					grid.refresh();
	  			}
	  			$(".k-edit-field").each(function () {
		 		  	 $(this).find(".k-grid-totlcost"+e.model.jcId).parent().remove();
		 	    }); 
		  		$(".k-window-title").text("Edit Job Card Details");
			 	$(".k-grid-update").text("Update");	
			 	$(".k-grid-update").click(function (){		 		  	
	  		 		if(e.model.expDate!=null && e.model.expDate!=""){
	  		 			if(e.model.expDate<e.model.jobDt){
		  		 			alert("Expected Date Should not be Greater than Job Created Date");
		  		 			return false;
		  		 		}	
	  		 		}
	  		 		
	  		 	});
  		 	} 
	 	    e.container.find(".k-grid-cancel").bind("click", function () {
	 	    	var grid = $("#grid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
		    }); 
	 	    
	 	   $(".k-window-action.k-link").click(function(e){
	 		    var grid = $("#grid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
	 	   });
	 	   
 	}	
	
	function jobCardsRemove(e){
		securityCheckForActions("./maintainance/jobcards/deleteButton");		
	}
  	
	$("#grid").on("click", "#temPID", function(e) {
		
		var result=securityCheckForActionsForStatus("./maintainance/jobcards/jobstatusButton");
		
		if(result=="success"){
			var widget = $("#grid").data("kendoGrid");
			var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));			
			$.ajax({
					type : "POST",
					url : "./jobcardsdetails/jobcardStatus/" + dataItem.jcId + "/"+dataItem.status,
					async:false,
					dataType:"text",
					success : function(response) {
						
   						 $("#alertsBox").html("");
   						 $("#alertsBox").html(response);
   						 $("#alertsBox").dialog({
   								modal : true,
   								draggable: false,
   								resizable: false,
   								buttons : {
   									"Close" : function() {
   										$(this).dialog("close");
   									}
   								}
   						 });
   						 $('#grid').data('kendoGrid').dataSource.read();
    					 					
					}
			});
		}
		
 	}); 	
	
	
	 function jobCardsDatabound(e) 
		{
		    
		  	var vgrid = $("#grid").data("kendoGrid");
		  	var items = vgrid.dataSource.data();
		 	var i = 0;		       
		 	 this.tbody.find("tr td:last-child").each(function (e){
		  	  	var item = items[i];	
		  	 
		  	  	if(item!=undefined){
					if(item.status!="CLOSED"){
		  	  		
		  	  		$(".k-grid-totlcost"+item.jcId).hide();
		  	  	
			  	  	if(item.suspendStatus == 'RESUME')
				   	{
				   		$("<button id='test' class='k-button k-button-icontext' onclick='jobCardsSuspendStatusClick()'>SUSPEND</button>").appendTo(this);
				   	}
				   	else
				   	{
				   		$("<button id='test' class='k-button k-button-icontext' onclick='jobCardsSuspendStatusClick()'>RESUME</button>").appendTo(this);
				   	}					   	
			  	 }
		  	  	 i++;
		  	  	}
		  	  	
			   
		   	}); 	 	
		 	
		 	
		 	var data = this.dataSource.view(),row;
		    for (var i = 0; i < data.length; i++) {
		        row = this.tbody.children("tr[data-uid='" + data[i].uid + "']");		        
		        var status = data[i].status;	
		        var suspendstatus = data[i].suspendStatus;	
		        if(suspendstatus == 'SUSPEND'){
		        	row.addClass("bgYellowColor");	
		        }else{
		        	if (status == 'CLOSED') {
						row.addClass("bgRedColor");					
					}if (status == 'OPEN') {
						row.addClass("bgBlueColor");					
					}if (status == 'ON JOB') {
						row.addClass("bgGreenColor");					
					} 
				}
		        
		    }
		} 
	 
	 function jobCardsSuspendStatusClick(){
		 
		var result=securityCheckForActionsForStatus("./maintainance/jobcards/suspendstatusButton");
			
		if(result=="success"){
			var widget = $("#grid").data("kendoGrid");
			var dataItem = widget.dataItem(widget.select());		
	    	
			$.ajax({
					type : "POST",
					url : "./jobcardsdetails/jobcardsuspendStatus/" + dataItem.jcId + "/"+dataItem.suspendStatus,
					dataType : 'text',
					success : function(response) {   									
						$("#alertsBox").html("");
						$("#alertsBox").html(response);
						$("#alertsBox").dialog({
							modal : true,
							draggable: false,
							resizable: false,
							buttons : {
								"Close" : function() {
									$(this).dialog("close");
								}
							}
						});
						$('#grid').data('kendoGrid').dataSource.read();
					}
			});
		}
		 
		 	
	 }
	 
	function onRequestStart(e){
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();
        
		/* if (e.type == "create"){
			var gridStoreGoodsReturn = $("#grid").data("kendoGrid");
			gridStoreGoodsReturn.cancelRow();		
		} */	
		
	 }
	
	function onRequestStartJobObjective(e){
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();
	}
	
	function onRequestStartJobNotification(e){
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();
	}
	
	function onRequestStartJobNotes(e){
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();
	}
	
	function onRequestStartJobTeam(e){
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();
	}
	function onRequestStartJobTools(e){
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();
	}
	function onRequestStartJobMaterial(e){
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();
	}
	
	function onRequestStartLabourTask(e){
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();
	}
	
	function onRequestStartJobDocuments(e){
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();
	}
	 function onRequestEnd(e) {
	 	   
		   	if (typeof e.response != 'undefined')
			{
		   		if (e.response.status == "invalid") {
					errorInfo = "";
					errorInfo = e.response.result.invalid;
					for (var i = 0; i < e.response.result.length; i++) {
						errorInfo += (i + 1) + ". "
								+ e.response.result[i].defaultMessage;
					}

					$("#alertsBox").html("");
					$("#alertsBox").html(errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						draggable: false,
						resizable: false,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});		
					var grid = $("#grid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
					return false;
				}
		   		if (e.response.status == "FAIL") {
		   			errorInfo = "";
		   			for (var i = 0; i < e.response.result.length; i++) {
		   			errorInfo += (i + 1) + ". "	+ e.response.result[i].defaultMessage+"\n";
		   			}

		   			if (e.type == "create") {
		   				
		   				$("#alertsBox").html("");
		       			$("#alertsBox").html(errorInfo);
		       			$("#alertsBox").dialog({
		       				modal : true,
		       				draggable: false,
		    				resizable: false,
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
		   	
		   			if (e.type == "update") {
		   				$("#alertsBox").html("");
		       			$("#alertsBox").html(errorInfo);
		       			$("#alertsBox").dialog({
		       				modal : true,
		       				draggable: false,
		    				resizable: false,
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
		   	
		   			var grid = $("#grid").data("kendoGrid");
		   			grid.dataSource.read();
		   			grid.refresh();
		   			return false;
		   		}

		   		if (e.type == "update" && !e.response.Errors) {
		   			$("#alertsBox").html("");
		   			$("#alertsBox").html("Record Updated Successfully");
		   			$("#alertsBox").dialog({
		   				modal : true,
		   				draggable: false,
						resizable: false,
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
		   		
		   		if (e.type == "update" && e.response.Errors) {
		   			$("#alertsBox").html("");
		   			$("#alertsBox").html("Record Updated Un-successfully");
		   			$("#alertsBox").dialog({
		   				modal : true,
		   				draggable: false,
						resizable: false,
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

		   		if (e.type == "create" && !e.response.Errors) {
		   			$("#alertsBox").html("");
		   			$("#alertsBox").html("Record Created Successfully");
		   			$("#alertsBox").dialog({
		   				modal : true,
		   				draggable: false,
						resizable: false,
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
		   		
		   		if (e.type == "destroy" && !e.response.Errors) {
		   			$("#alertsBox").html("");
		   			$("#alertsBox").html("Record Deleted Successfully");
		   			$("#alertsBox").dialog({
		   				modal : true,
		   				draggable: false,
						resizable: false,
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
	 
	 /* Tab Strips */
	 
   	var SelectedRowId = "";
   	var jobcardstatus="";
	function onChangeJobCards(arg) {
		
		 var gview = $("#grid").data("kendoGrid");
	 	 var selectedItem = gview.dataItem(gview.select());
	 	 SelectedRowId = selectedItem.jcId;	
	 	 jobcardstatus =selectedItem.status;
	 	 this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));  	 
	     $(".k-master-row.k-state-selected").show();
	}	
	
	
	 
 	function jobObjectiveEditor (container, options) {
        $('<textarea name="Job Objective" data-text-field="jcObjective"  data-value-field="jcObjective" data-bind="value:' + options.field + '" style="width: 148px; height: 50px;"/>')
             .appendTo(container);
        
        $('<span class="k-invalid-msg" data-for="Job Objective"></span>').appendTo(container);
 	}
	 
    function jobObjectiveEvent(e) {			
    	 $('label[for="jcoId"]').parent().remove();
		 $('input[name="jcoId"]').parent().remove();
		 $('label[for="undefined"]').parent().remove();			 		 		 
  		 $('label[for="jcObjectiveAch"]').parent().remove();
		 $('input[name="jcObjectiveAch"]').parent().remove();
		 
		
		 
		 $('.k-edit-field .k-input').first().focus();
  		
  		 if (e.model.isNew()){  	
  			securityCheckForActions("./jobobjective/createbutton");
  	    	 $(".k-window-title").text("Add Job Objective Details");
  		 	 $(".k-grid-update").text("Create");  		 
	    }
	  	else{
	  		securityCheckForActions("./jobobjective/editbutton");
	  		$(".k-window-title").text("Edit Job Objective Details");
	  		$('.k-edit-field .k-input').first().focus();
	  		$(".k-grid-update").text("Update");
	  	}	
	}
    
    function jobobjectiveRemove(e){
 	   securityCheckForActions("./jobobjective/deletebutton");
    }
    
    function jobObjectivedataBound(e) 
	{
	    
	  	var vgrid = $("#jobObjectivegrid_"+SelectedRowId).data("kendoGrid");
	  	var items = vgrid.dataSource.data();
	 	var i = 0;
	 	this.tbody.find("tr td:last-child").each(function (e) 
	   	{
	  	  	var item = items[i];	  	  	
		   	if(item.jcObjectiveAch == 'YES')
		   	{
		   		$("<button id='test' class='k-button k-button-icontext' onclick='jobObjectiveStatusClick()'>Objective Achieved</button>").appendTo(this);
		   	}
		   	else
		   	{
		   		$("<button id='test' class='k-button k-button-icontext' onclick='jobObjectiveStatusClick()'>Achieve Objective</button>").appendTo(this);
		   	}	
		   	i++;
	   	});
	}   
	
    function jobObjectiveStatusClick(){
		 var widget = $("#jobObjectivegrid_"+SelectedRowId).data("kendoGrid");
		 var dataItem = widget.dataItem(widget.select());		
	    	
		 $.ajax({
				type : "POST",
				url : "./jobobjectivedetails/jobObjectiveStatus/" + dataItem.id + "/"+dataItem.jcObjectiveAch,
				dataType : 'text',
				success : function(response) {   									
					$("#alertsBox").html("");
					$("#alertsBox").html(response);
					$("#alertsBox").dialog({
						modal : true,
						draggable: false,
						resizable: false,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});
					$("#jobObjectivegrid_"+SelectedRowId).data('kendoGrid').dataSource.read();
				}
		});
	 }    
    
    function onRequestEndJobObjective(e) {
	 	var resultOperation="";
	   	if (typeof e.response != 'undefined'){
	   	
	   		
	   		if (e.response.status == "FAIL") {
	   			errorInfo = "";
	   			for (var i = 0; i < e.response.result.length; i++) {
	   				errorInfo += (i + 1) + ". "	+ e.response.result[i].defaultMessage+"\n";
	   			}		   			
		   		if (e.type == "create") {			   			
		   			resultOperation=errorInfo;	   			     			
		   		}	   	
		   		if (e.type == "update") {
		   			resultOperation=errorInfo;   			
		   		}
		   		$("#alertsBox").html("");
	   			$("#alertsBox").html(resultOperation);
	   			$("#alertsBox").dialog({
	   				modal : true,
	   				draggable: false,
					resizable: false,
	   				buttons : {
	   					"Close" : function() {
	   						$(this).dialog("close");
	   					}
	   				}
	   			});	   	
	   			
	   			var grid = $("#jobObjectivegrid_"+SelectedRowId).data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
				
	   			return false;
	   		}
	   		if (e.type == "update" && !e.response.Errors) {
	   			resultOperation="Record Updated Successfully";	   		
	   		}
	   		if (e.type == "create" && !e.response.Errors) {
	   			resultOperation="Record Created Successfully";	   			
	   		}		   		
	   		if (e.type == "destroy" && !e.response.Errors) {
	   			
	   		if (e.response.status == "cannotDelete") {
				errorInfo = "";
				errorInfo = e.response.result.cannotDelete;
				$("#alertsBox").html("");
				$("#alertsBox").html(errorInfo);
				$("#alertsBox").dialog({
					modal : true,
					draggable: false,
					resizable: false,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});		
				var grid = $("#jobObjectivegrid_"+SelectedRowId).data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			}
	   		else
	   		{
	   			resultOperation="Record Deleted Successfully";	   			
	   		}
	   		}
	   		if(resultOperation!=""){
	   			$("#alertsBox").html("");
	   			$("#alertsBox").html(resultOperation);
	   			$("#alertsBox").dialog({
	   				modal : true,
	   				draggable: false,
					resizable: false,
	   				buttons : {
	   					"Close" : function() {
	   						$(this).dialog("close");
	   					}
	   				}
	   			});	 
	   			
	   			var grid = $("#jobObjectivegrid_"+SelectedRowId).data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
	   		}
	   		
		}		
  	}   
    
    /* Job Notifications */
    
     (function ($, kendo) {
        $.extend(true, kendo.ui.validator, {
             rules: {            	
            	 jnDtValidation: function (input, params) {
                     //check for the name attribute 
                     if (input.attr("name") == "jnDt") {
                    	 return $.trim(input.val()) !== "";
                     }
                     return true;
            	 }
              },		               
             //custom rules messages
             messages: {             	
            	 jnDtValidation: "Job Notification Date is required",
            	
             }
        });
    })(jQuery, kendo);
    
    function parseJobNotification(response) {   
	    $.each(response, function (idx, elem) {
            if (elem.jnDt && typeof elem.jnDt === "string") {
                elem.jnDt = kendo.parseDate(elem.jnDt, "yyyy-MM-ddTHH:mm:ss.fffZ");
            }            
        });
        return response;
	} 
    
    function jobNotificationEditor (container, options) {
        $('<textarea name="notification" data-text-field="notification" data-value-field="notification"  data-bind="value:' + options.field + '" style="width: 148px; height: 50px;"/>')
             .appendTo(container);
        $('<span class="k-invalid-msg" data-for="notification"></span>').appendTo(container);
 	}
    
     function jobNotificationTypeEditor(container, options) {
   	   var data = ["ALERT","MESSAGE"];
   	   $(
   	     '<input name="Notification Type" data-text-field="" id="notificationType" data-value-field="" data-bind="value:' + options.field + '" required="true" />')
   	     .appendTo(container).kendoComboBox({   	    
   	      dataSource :data,
   	      change : function (e) {
	          if (this.value() && this.selectedIndex == -1) {                    
	               alert("Job Notification Type doesn't exist");
	               $("#notificationType").data("kendoComboBox").value("");
	       }
	  	},
   	   });
	   	$('<span class="k-invalid-msg" data-for="Notification Type"></span>').appendTo(container);
     }      
    
     function jobNotifyEditor(container, options) {
     	   /*  var data = ["OWNER","OWNER GROUP"];
     	   $(
     	     '<input name="Job Notify To" data-text-field="" id="notifyTo" data-value-field="" data-bind="value:' + options.field + '" required="true" />')
     	     .appendTo(container).kendoComboBox({   	    
     	      dataSource :data,
     	      autoBind:false,
     	      select:onChangeNotify,
     	      change : function (e) {
   	          if (this.value() && this.selectedIndex == -1) {                    
   	               alert("Notify To doesn't exist");
   	               $("#notifyTo").data("kendoComboBox").value("");
   	       }
   	  	},
     	   });
  	   	  $('<span class="k-invalid-msg" data-for="Job Notify To"></span>').appendTo(container);  */
  	   	
    		var booleanData = [ {
    		     text : 'Select',
    			 value : ""
    			    },{
    				   text : 'OWNER',
    				   value : "OWNER"
    				  },{
    				   text : 'OWNER GROUP',
    				   value : "OWNER GROUP"
    				  },];
    				  $('<input name="Job Notify To" required="true"/>').attr('data-bind', 'value:notify').appendTo(container).kendoDropDownList
    				  ({
    					    autoBind : false,
    					    defaultValue : false,
    						select : onChangeNotify,
    						sortable : true,
    					  
    					 dataSource : booleanData,
    					 dataTextField : 'text',
    					 dataValueField : 'value',
    				  });
    				  $('<span class="k-invalid-msg" data-for="Job Notify To"></span>').appendTo(container);
    		
     }      
   
    var notifyEditorText="";
    function onChangeNotify(e){
    	
     	/* var notify=$("#notify").data("kendoComboBox").value(); */
     	/* var notify= $("#notify").data("kendoComboBox").value(""); */
     	var dataItem = this.dataItem(e.item.index());
     	//getMembersforNotification(notify);
     	
     	
     	
     	
     	if(dataItem.text=="OWNER"){ 
     		 notifyEditorText=dataItem.text;
     		 $('div[data-container-for="blockforJob"]').show();	
 		     $('div[data-container-for="flatforJob"]').show();	
 		     $('label[for="blockforJob"]').parent().show();
 		     $('div[data-container-for="notificationMembers"]').show();	
 		     $('label[for="notificationMembers"]').parent().show();
		     $('label[for="flatforJob"]').parent().show();
     	}else{
     		 notifyEditorText=dataItem.text;
     		 $('div[data-container-for="blockforJob"]').show();	
     		 $('div[data-container-for="flatforJob"]').hide();
     		 $('div[data-container-for="notificationMembers"]').hide();
     		 $('label[for="blockforJob"]').parent().show();
     		 $('label[for="flatforJob"]').parent().hide();
     		 $('label[for="notificationMembers"]').parent().hide();
     	}
     } 
     
     function blockforEditor(container, options) {
 		$('<input name="Block" data-text-field="block" id="blockforJob" data-value-field="blockId" data-bind="value:' + options.field + '" />')
 				.appendTo(container).kendoComboBox({
 					 placeholder: "Select Blocks",
 		     		 filter:"startswith",	     	
 					 dataSource : {
 						transport : {
 							read : "${getblocks}"
 						}
 					},
 				     change : function (e) {
 				          if (this.value() && this.selectedIndex == -1) {                    
 				               alert("Block doesn't exist");
 				               $("#blockforJob").data("kendoComboBox").value("");
 				       }
 				  	},
 				});
 		 $('<span class="k-invalid-msg" data-for="Block"></span>').appendTo(container);
 	}	
 	
 	function flatforEditor(container, options) {
 		$('<input name="Block" data-text-field="propertyNo" id="flatforJob" data-value-field="propertyId" data-bind="value:' + options.field + '" />')
 				.appendTo(container).kendoComboBox({
 					 placeholder: "Select Flats",
 		     		 filter:"startswith",
 		     		 cascadeFrom: "blockforJob",		
 					 dataSource : {
 						transport : {
 							read : "${getFlat}"
 						}
 					},
 				    change : function (e) {
				          if (this.value() && this.selectedIndex == -1) {                    
				               alert("Flat doesn't exist");
				               $("#flatforJob").data("kendoComboBox").value("");
				       }
				  	},
 		});
 	} 	
 	
     
     function jobNotificationMembersEditor(container, options) {
   	    $('<input name="Notification Memeber" data-text-field="personName" id="notificationMember" data-value-field="personId" data-bind="value:' + options.field + '" />')
   	    	.appendTo(container).kendoComboBox({ 
   	    	cascadeFrom: "flatforJob",
   	    	dataSource : {
				transport : {
					read : "${notificationMembersCombo}"
				}
			},
			 change : function (e) {
		          if (this.value() && this.selectedIndex == -1) {                    
		               alert("Job Notification Members doesn't exist");
		               $("#notificationMember").data("kendoComboBox").value("");
		       }
		  	},
   	    });	   
     }    
     
   
    function jobNotificationEvent(e){
	    
    	
    	 $('.k-edit-field .k-input').first().focus();
    	 $('label[for="jnDt"]').parent().remove();
 		 $('input[name="jnDt"]').parent().remove();
 		 $('label[for="status"]').parent().hide();
		 $('input[name="status"]').parent().remove();
		 
  		 if (e.model.isNew()){  	
  			securityCheckForActions("./jobnotification/createbutton");
  	    	 $(".k-window-title").text("Add Job Notification Details");
  		 	 $(".k-grid-update").text("Create"); 
  		 	 $('div[data-container-for="blockforJob"]').hide();	
 		     $('div[data-container-for="flatforJob"]').hide();	
 		     $('label[for="blockforJob"]').parent().hide(); 		    	
 		     $('label[for="flatforJob"]').parent().hide();
     		 $('label[for="notificationMembers"]').parent().hide();
     		 $('div[data-container-for="notificationMembers"]').hide();
     		 
     		 
     		 
      		$(".k-grid-update").click(function () 
      				{
      			
      			      if(notifyEditorText=="OWNER"){
      			    	  
      			    	 var cards = $("#blockforJob").data("kendoComboBox");
       					 var supvisors = $("#flatforJob").data("kendoComboBox");
       					 var track = $("#notificationMember").data("kendoComboBox");
      			    	  
      		 			
      		 			if(cards.select() == -1){
    							
      						 alert("Please Select Block");
      						 return false;	
      					 }
      		 			else if(supvisors.select() == -1){
    						
     						 alert("Please Select Flat");
     						 return false;	
     					 }
      		 			else if(track.select() == -1){
    						
     						 alert("Please Select Notification Member");
     						 return false;	
     					 }
      		 			
      		 			
      		 			
      			      }
      			      
      			      else if(notifyEditorText=="OWNER GROUP"){
    			    	  
     			    	 var cards = $("#blockforJob").data("kendoComboBox");
      					
     		 			if(cards.select() == -1){
    							
     						 alert("Please Select Block");
     						 return false;	
     					 }
     		 			
     			      }
      			      
      				}); 

     		
	    }
	  	else{

	  		securityCheckForActions("./jobnotification/editbutton");
	  		$(".k-window-title").text("Edit Job Notification Details");
	  		$('.k-edit-field .k-input').first().focus();
	  		$(".k-grid-update").text("Update");
	  		

	  		
			
	  		 var gview= $('#jobNotificationgrid_' + SelectedRowId).data("kendoGrid");
		     var selectedItem = gview.dataItem(gview.select());
			  selectedType = selectedItem.notify;
			 
		    if(selectedType=="OWNER"){
		    	 /* $('div[data-container-for="blockforJob"]').show();	
	 		     $('div[data-container-for="flatforJob"]').show();	
	 		     $('div[data-container-for="notificationMembers"]').show();	
	 		     $('label[for="blockforJob"]').parent().show();
	 		     $('label[for="notificationMembers"]').parent().show();
			     $('label[for="flatforJob"]').parent().show(); */
		    	 $('div[data-container-for="blockforJob"]').show();	
	 		     $('div[data-container-for="flatforJob"]').show();	
	 		     $('label[for="blockforJob"]').parent().show();
	 		     $('div[data-container-for="notificationMembers"]').show();	
	 		     $('label[for="notificationMembers"]').parent().show();
			     $('label[for="flatforJob"]').parent().show();
			     
			
			     
			     
		    }else{
		    	 /* $('div[data-container-for="blockforJob"]').show();	
	     		 $('div[data-container-for="flatforJob"]').hide();
	     		 $('div[data-container-for="notificationMembers"]').hide();
	     		 $('label[for="blockforJob"]').parent().show();
	     		 $('label[for="flatforJob"]').parent().hide();
	     		 $('label[for="notificationMembers"]').parent().hide(); */
		    	 $('div[data-container-for="blockforJob"]').show();	
	     		 $('div[data-container-for="flatforJob"]').hide();
	     		 $('div[data-container-for="notificationMembers"]').hide();
	     		 $('label[for="blockforJob"]').parent().show();
	     		 $('label[for="flatforJob"]').parent().hide();
	     		 $('label[for="notificationMembers"]').parent().hide();
	
		    }	
		    
		    
			$(".k-grid-update").click(function () 
      				{
      			
      			      if(notifyEditorText=="OWNER"){
      			    	  
      			    	 var cards = $("#blockforJob").data("kendoComboBox");
       					 var supvisors = $("#flatforJob").data("kendoComboBox");
       					 var track = $("#notificationMember").data("kendoComboBox");
      			    	  
      		 			
      		 			if(cards.select() == -1){
    							
      						 alert("Please Select Block");
      						 return false;	
      					 }
      		 			else if(supvisors.select() == -1){
    						
     						 alert("Please Select Flat");
     						 return false;	
     					 }
      		 			else if(track.select() == -1){
    						
     						 alert("Please Select Notification Member");
     						 return false;	
     					 }
      		 			
      		 			
      		 			
      			      }
      			      
      			      else if(notifyEditorText=="OWNER GROUP"){
    			    	  
     			    	 var cards = $("#blockforJob").data("kendoComboBox");
      					
     		 			if(cards.select() == -1){
    							
     						 alert("Please Select Block");
     						 return false;	
     					 }
     		 			
     			      }
      			      
      				}); 
		    
	  	}	
  	
  	   e.container.find(".k-grid-cancel").bind("click", function () {
	    	var grid = $("#jobNotificationgrid_"+SelectedRowId).data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
	    }); 
  		 
 
    }
    
    function jobnotificationRemove(e){
    	   securityCheckForActions("./jobnotification/deletebutton");
     }
    
    function jobNotificationdataBound(e) 
	{
	    
	  	var vgrid = $("#jobNotificationgrid_"+SelectedRowId).data("kendoGrid");
	  	var items = vgrid.dataSource.data();
	 	var i = 0;
	 	this.tbody.find("tr td:last-child").each(function (e) 
	   	{
	  	  	var item = items[i];	  	  	
		   	if(item.status == 'NO')
		   	{
		   		$("<button id='test' class='k-button k-button-icontext' onclick='jobNotificationStatusClick()'>Send Notification</button>").appendTo(this);
		   	}
		   	else
		   	{
		   		$("<button id='test' class='k-button k-button-icontext' onclick='jobNotificationStatusClick()'>Notified</button>").appendTo(this);
		   	}	
		   	i++;
	   	});
	}
    
    function jobNotificationStatusClick(){     	
    	
		var widget = $("#jobNotificationgrid_"+SelectedRowId).data("kendoGrid");
		var dataItem = widget.dataItem(widget.select());		
    	
		$.ajax({
				type : "POST",
				url : "./jobNotificationdetails/jobNotificationStatus/" + dataItem.jnId + "/"+dataItem.status,
				dataType : 'text',
				success : function(response) {   									
					$("#alertsBox").html("");
					$("#alertsBox").html(response);
					$("#alertsBox").dialog({
						modal : true,
						draggable: false,
						resizable: false,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							}
						}
					});	
					var grid = $("#jobNotificationgrid_"+SelectedRowId).data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
				}
				
		});
    }
    
    function onRequestEndJobNotification(e) {
	 	var resultOperation="";
	   	if (typeof e.response != 'undefined'){	   					
	   		
	   		if (e.response.status == "FAIL") {
	   			errorInfo = "";
	   			for (var i = 0; i < e.response.result.length; i++) {
	   				errorInfo += (i + 1) + ". "	+ e.response.result[i].defaultMessage+"\n";
	   			}		   			
		   		if (e.type == "create") {			   			
		   			resultOperation=errorInfo;	   			     			
		   		}	   	
		   		if (e.type == "update") {
		   			resultOperation=errorInfo;   			
		   		}
		   		$("#alertsBox").html("");
	   			$("#alertsBox").html(resultOperation);
	   			$("#alertsBox").dialog({
	   				modal : true,
	   				draggable: false,
					resizable: false,
	   				buttons : {
	   					"Close" : function() {
	   						$(this).dialog("close");
	   					}
	   				}
	   			});	   	
	   			
	   			var grid = $("#jobNotificationgrid_"+SelectedRowId).data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
				
	   			return false;
	   		}
	   		if (e.type == "update" && !e.response.Errors) {
	   			resultOperation="Record Updated Successfully";	   		
	   		}
	   		if (e.type == "create" && !e.response.Errors) {
	   			resultOperation="Record Created Successfully";	   			
	   		}		   		
	   		if (e.type == "destroy" && !e.response.Errors) {
	   			resultOperation="Record Deleted Successfully";	   			
	   		}	
	   		if(resultOperation!=""){
	   			$("#alertsBox").html("");
	   			$("#alertsBox").html(resultOperation);
	   			$("#alertsBox").dialog({
	   				modal : true,
	   				draggable: false,
					resizable: false,
	   				buttons : {
	   					"Close" : function() {
	   						$(this).dialog("close");
	   					}
	   				}
	   			});	 
	   			
	   			var grid = $("#jobNotificationgrid_"+SelectedRowId).data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
	   		}
	   		
		}		
  	}
    
    /* Job Notes */
    
    (function ($, kendo) {
        $.extend(true, kendo.ui.validator, {
             rules: {            	
            	 jcnDtValidation: function (input, params) {
                     //check for the name attribute 
                     if (input.attr("name") == "jcnDt") {
                    	 return $.trim(input.val()) !== "";
                     }
                     return true;
            	 },
            	 JobNotesNullValidation: function (input, params) {
                     //check for the name attribute 
                     if (input.attr("name") == "Job Notes") {
                    	 return $.trim(input.val()) !== "";
                     }
                     return true;
                 },
                 
            	 NotificationNullValidation: function (input, params) {
                     //check for the name attribute 
                     if (input.attr("name") == "notification") {
                    	 return $.trim(input.val()) !== "";
                     }
                     return true;
                 },
                 
                 descLengthValidation: function (input, params){
                     if (input.attr("name") == "notification") { 	 
      		       	  if (input.filter("[name='notification']").length && input.val() != "") {
      		            	 return /^[\s\S]{1,500}$/.test(input.val());
      		             }  
                       }
      	             return true;
      	         },
              },		               
             //custom rules messages
             messages: {             	
            	 jcnDtValidation: "Job Notes Date is required",	 
            	 JobNotesNullValidation:"Job Notes is Required",
            	 NotificationNullValidation:"Notification is Required",
            	 descLengthValidation:"Notification field allows max 500 characters",

             }
        });
    })(jQuery, kendo);
    
    function parseJobNotes(response) {   
	    $.each(response, function (idx, elem) {
            if (elem.jcnDt && typeof elem.jcnDt === "string") {
                elem.jcnDt = kendo.parseDate(elem.jcnDt, "yyyy-MM-ddTHH:mm:ss.fffZ");
            }            
        });
        return response;
	} 
    
    function jobNotesEditor (container, options) {
        $('<textarea name="Job Notes" data-text-field="notes" data-value-field="notes"  data-bind="value:' + options.field + '" style="width: 148px; height: 50px;"/>')
             .appendTo(container);
        $('<span class="k-invalid-msg" data-for="Job Notes"></span>').appendTo(container);
 	}    
    
    function jobNotesEvent(e){
   	 	 $('label[for="jcnId"]').parent().remove();
		 $('input[name="jcnId"]').parent().remove();
	    
 		 $('.k-edit-field .k-input').first().focus();
 	    
 		 if (e.model.isNew()){  	
 			securityCheckForActions("./jobnotes/createbutton");
 	    	 $(".k-window-title").text("Add Job Notes Details");
 		 	 $(".k-grid-update").text("Create");		
	    }
	  	else{
	  		securityCheckForActions("./jobnotes/editbutton");
	  		$(".k-window-title").text("Edit Job Notes Details");
	  		$('.k-edit-field .k-input').first().focus();
	  		$(".k-grid-update").text("Update");
	  	}	
   }
    
    function jobnotesRemove(e){
   	   securityCheckForActions("./jobnotes/deletebutton");
    }
    
    function onRequestEndJobNotes(e) {
	 	var resultOperation="";
	   	if (typeof e.response != 'undefined'){	   					
	   		
	   		if (e.response.status == "FAIL") {
	   			errorInfo = "";
	   			for (var i = 0; i < e.response.result.length; i++) {
	   				errorInfo += (i + 1) + ". "	+ e.response.result[i].defaultMessage+"\n";
	   			}		   			
		   		if (e.type == "create") {			   			
		   			resultOperation=errorInfo;	   			     			
		   		}	   	
		   		if (e.type == "update") {
		   			resultOperation=errorInfo;   			
		   		}
		   		$("#alertsBox").html("");
	   			$("#alertsBox").html(resultOperation);
	   			$("#alertsBox").dialog({
	   				modal : true,
	   				draggable: false,
					resizable: false,
	   				buttons : {
	   					"Close" : function() {
	   						$(this).dialog("close");
	   					}
	   				}
	   			});	   	
	   			
	   			var grid = $("#jobNotesgrid_"+SelectedRowId).data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
				
	   			return false;
	   		}
	   		if (e.type == "update" && !e.response.Errors) {
	   			resultOperation="Record Updated Successfully";	   		
	   		}
	   		if (e.type == "create" && !e.response.Errors) {
	   			resultOperation="Record Created Successfully";	   			
	   		}		   		
	   		if (e.type == "destroy" && !e.response.Errors) {
	   			resultOperation="Record Deleted Successfully";	   			
	   		}	
	   		if(resultOperation!=""){
	   			$("#alertsBox").html("");
	   			$("#alertsBox").html(resultOperation);
	   			$("#alertsBox").dialog({
	   				modal : true,
	   				draggable: false,
					resizable: false,
	   				buttons : {
	   					"Close" : function() {
	   						$(this).dialog("close");
	   					}
	   				}
	   			});	 
	   			
	   			var grid = $("#jobNotesgrid_"+SelectedRowId).data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
	   		}
	   		
		}		
  	}
    
    /* -------------------------------Job Team---------------------------------- */    
    
     function parseJobTeam (response) {   
	    $.each(response, function (idx, elem) {           
            if (elem.lastUpdatedDate && typeof elem.lastUpdatedDate === "string") {
                elem.lastUpdatedDate = kendo.parseDate(elem.lastUpdatedDate, "yyyy-MM-ddTHH:mm:ss.fffZ");
            }            
        });
        return response;
	}    
	
   (function ($, kendo) {
        $.extend(true, kendo.ui.validator, {
             rules: { // custom rules             	 
                 jctHoursValidation: function (input, params) {
                     //check for the name attribute 
                     if (input.attr("name") == "jctHours") {
                    	 return $.trim(input.val()) !== "";
                     }
                     return true;
                 },	
                 JobObjectiveNullValidation: function (input, params) {
                     //check for the name attribute 
                     if (input.attr("name") == "Job Objective") {
                    	 return $.trim(input.val()) !== "";
                     }
                     return true;
                 },	
                 
                 
              
                 jctHoursPatternValidation: function (input, params) {
                	 if (input.filter("[name='jctHours']").length && input.val()) {
                         return /^[0-9]+$/.test(input.val());
                     }
                     return true;
                 },
                 startDtNullValidation: function (input, params) {
                     //check for the name attribute 
                     if (input.attr("name") == "jctStartDt") {
                    	 return $.trim(input.val()) !== "";
                     }
                     return true;
                 },
                 endDtNullValidation: function (input, params) {
                     //check for the name attribute 
                     if (input.attr("name") == "jctEndDt") {
                    	 return $.trim(input.val()) !== "";
                     }
                     return true;
                 },
                 jobStartDateValidation : function(input,params) {
                 	
 					if (input
 							.filter("[name = 'jctStartDt']").length && input.val()) {
 						var selectedDate = input.val();
 						var todaysDate = $.datepicker
 								.formatDate('dd/mm/yy',
 										new Date());
 						var flagDate = true;

 						if ($.datepicker.parseDate('dd/mm/yy',
 								selectedDate) < $.datepicker
 								.parseDate('dd/mm/yy',
 										todaysDate)) {
 							flagDate = false;
 						}
 						return flagDate;
 					}
 					return true;
 				},
 				
                jobEndDateValidation : function(input,params) {
                	
                	var jbstdt=$('input[name="jctStartDt"]').val();
                	
					if (input
							.filter("[name = 'jctEndDt']").length && input.val()) {
						var selectedDate = input.val();
						var flagDate = true;

						if ($.datepicker.parseDate('dd/mm/yy',
												selectedDate) < $.datepicker
												.parseDate('dd/mm/yy',
														jbstdt)) {
							flagDate = false;
						}
						return flagDate;
					}
					return true;
				},
                                       
             },
           //custom rules messages
             messages: {             	 
            	 jctHoursValidation:"Hours is required",            	
            	 jctHoursPatternValidation:"Only Numbers Are Allowed",
            	 startDtNullValidation:"Job Start Date Required",
            	 endDtNullValidation:"Job End Date Required",
            	 jobStartDateValidation :"Job Start date can't be less than Today Date",
            	 jobEndDateValidation:"Job End date can't be less than job Start date",
            	 JobObjectiveNullValidation:"Job Objective is Required",
            }
        });
    })(jQuery, kendo); 
   
   function jcWorkTimeEditor(container, options) {
   	   var data = ["OFFICE TIME","OVER TIME"];
   	   $(
   	     '<input name="Work Time" id="worktime" data-text-field="" data-value-field="" data-bind="value:' + options.field + '" required="true" />Days')
   	     .appendTo(container).kendoComboBox({   	    
   	      dataSource :data,
   		change : function (e) {
	        if (this.value() && this.selectedIndex == -1) {                    
	             alert("Work Time doesn't exist");
	             $("#worktime").data("kendoComboBox").value("");
	     }
		},
   	   });
	   	$('<span class="k-invalid-msg" data-for="Work Time"></span>').appendTo(container);
     } 
   
   function jTeamDepartmentEditor(container, options) {
		$('<input name="Department" required="true"  data-text-field="departmentName" id="departmentJTeam" data-value-field="departmentId" data-bind="value:' + options.field + '" />')
				.appendTo(container).kendoComboBox({
					 placeholder: "Select Department",
					 autoBind : false,		     				
					 dataSource : {
						transport : {
							read : "${getDepartment}"
						}
					},
					change : function (e) {
				        if (this.value() && this.selectedIndex == -1) {                    
				             alert("Department doesn't exist");
				             $("#departmentJTeam").data("kendoComboBox").value("");
				     }
					},
		});
		
		$('<span class="k-invalid-msg" data-for="Department"></span>').appendTo(container);
	}	
   
   function jTeamStaffEditor(container, options) {
  	    $(
  	     '<input name="Staff Name" required="true"  data-text-field="userName" id="jobTeamMembers" data-value-field="userId" data-bind="value:' + options.field + '"/>')
  	     .appendTo(container).kendoComboBox({ 
  	    	cascadeFrom: "departmentJTeam",
  	    	 placeholder: "Select Staff",
  	    	autoBind : false,	     	
  	    	dataSource : {
				transport : {
					read : "${jobTeamMembersCombo}"
				}
			},
			change : function (e) {
		        if (this.value() && this.selectedIndex == -1) {                    
		             alert("Staff doesn't exist");
		             $("#jobTeamMembers").data("kendoComboBox").value("");
		     }
			},
  	    });	  
  	    
  	  $('<span class="k-invalid-msg" data-for="Staff Name"></span>').appendTo(container);
    } 
  
   
   function onRequestEndJobTeam(e) {
	 	var resultOperation="";
	   	if (typeof e.response != 'undefined'){	   					
	   		
	   		if (e.response.status == "FAIL") {
	   			errorInfo = "";
	   			for (var i = 0; i < e.response.result.length; i++) {
	   				errorInfo += (i + 1) + ". "	+ e.response.result[i].defaultMessage+"\n";
	   			}		   			
		   		if (e.type == "create") {			   			
		   			resultOperation=errorInfo;	   			     			
		   		}	   	
		   		if (e.type == "update") {
		   			resultOperation=errorInfo;   			
		   		}
		   		$("#alertsBox").html("");
	   			$("#alertsBox").html(resultOperation);
	   			$("#alertsBox").dialog({
	   				modal : true,
	   				draggable: false,
					resizable: false,
	   				buttons : {
	   					"Close" : function() {
	   						$(this).dialog("close");
	   					}
	   				}
	   			});	   	
	   			
	   			var grid = $("#jobTeamgrid_"+SelectedRowId).data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
				
	   			return false;
	   		}
	   		if (e.type == "update" && !e.response.Errors) {
	   			resultOperation="Record Updated Successfully";	   		
	   		}
	   		if (e.type == "create" && !e.response.Errors) {
	   			resultOperation="Record Created Successfully";	   			
	   		}		   		
	   		if (e.type == "destroy" && !e.response.Errors) {
	   			resultOperation="Record Deleted Successfully";	   			
	   		}	
	   		if(resultOperation!=""){
	   			$("#alertsBox").html("");
	   			$("#alertsBox").html(resultOperation);
	   			$("#alertsBox").dialog({
	   				modal : true,
	   				draggable: false,
					resizable: false,
	   				buttons : {
	   					"Close" : function() {
	   						$(this).dialog("close");
	   					}
	   				}
	   			});	 
	   			
	   			var grid = $("#jobTeamgrid_"+SelectedRowId).data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
	   		}
	   		
		}		
	} 
    
    function jobTeamEvent(e){
   	 	 $('label[for="createdBy"]').parent().remove();
	   	 $('label[for="lastUpdatedBy"]').parent().remove();
	     $('label[for="lastUpdatedDate"]').parent().remove();
	     
	     $('input[name="createdBy"]').parent().remove();
	     $('input[name="lastUpdatedBy"]').parent().remove();
	     $('div[data-container-for="lastUpdatedDate"]').hide();   
	     
	     
 		 $('.k-edit-field .k-input').first().focus();
 	    
 		 if (e.model.isNew()){  	
 			securityCheckForActions("./jobteam/createbutton");
 	    	 $(".k-window-title").text("Add Job Team Details");
 		 	 $(".k-grid-update").text("Create");  		 
	    }
	  	else{
	  		securityCheckForActions("./jobteam/editbutton");
	  		$(".k-window-title").text("Edit Job Team Details");
	  		$('.k-edit-field .k-input').first().focus();
	  		$(".k-grid-update").text("Update");
	  	}	
   }
    
    function jobteamRemove(e){
  	   securityCheckForActions("./jobteam/deletebutton");
     }
    /* -------------------------------Job Tools---------------------------------- */
    
    function parseJobTools (response) {   
	    $.each(response, function (idx, elem) {            
            if (elem.lastUpdatedDate && typeof elem.lastUpdatedDate === "string") {
                elem.lastUpdatedDate = kendo.parseDate(elem.lastUpdatedDate, "yyyy-MM-ddTHH:mm:ss.fffZ");
            }           
        });
        return response;
	} 
    
     (function ($, kendo) {
        $.extend(true, kendo.ui.validator, {
             rules: {            	
            	 quantityValidation: function (input, params) {
                     //check for the name attribute 
                     if (input.attr("name") == "quantity") {
                    	 return $.trim(input.val()) !== "";
                     }
                     return true;
            	 },
            	 quantityPatternvalidation: function (input, params) {
                     //check for the name attribute                   
                     if (input.filter("[name='quantity']").length && input.val()) {
                         return /^[0-9]+$/.test(input.val());
                     }
                     return true;
                 },    
              },		               
             //custom rules messages
             messages: {             	
            	 quantityValidation: "Quantity is required",
            	 quantityPatternvalidation:"Only Numbers Are Allowed"
             }
        });
    })(jQuery, kendo);
    
    function toolMasterEditor(container, options) {
		$('<input name="Tool Name" data-text-field="toolMaster" id="tools" data-value-field="toolMasterId" data-bind="value:' + options.field + '" required="true"/>')
				.appendTo(container).kendoComboBox({
					 placeholder: "Select Tools",
		     		 filter:"startswith",
		     		 autoBind:false,
		     		 change:onChangeTools,
					 dataSource : {
						transport : {
							read : "${gettoolname}"
						}
					}
				});
		 $('<span class="k-invalid-msg" data-for="Tool Name"></span>').appendTo(container);
	}  
   
    
	function onChangeTools(){
		 var value = this.dataItem();
		 toolname=value.toolMaster;
		 
		 $.ajax({
	 			type : "GET",
	 			async: false,
	 			url : "./toolMaster/getQuantityBasedOnName/"+toolname,
	 			dataType : "JSON",
	 			success : function(response) {		 				
	 				toolQuantityBasedOnToolName=response;
	 				
	 			}
	 	});
	}
	
    function jobToolsEvent(e){
   	 	 $('label[for="jctoolId"]').parent().remove();
		 $('input[name="jctoolId"]').parent().remove();	
		 $('label[for="createdBy"]').parent().remove();
	   	 $('label[for="lastUpdatedBy"]').parent().remove();
	     $('label[for="lastUpdatedDate"]').parent().remove();
	     
	     $('input[name="createdBy"]').parent().remove();
	     $('input[name="lastUpdatedBy"]').parent().remove();
	     $('div[data-container-for="lastUpdatedDate"]').hide();   
	     
	     $('.k-edit-field .k-input').first().focus();
	     
	     $(".k-grid-update").click(function () {
				
			 	var toolQuan = $('input[name="quantity"]').val();

			   if(toolQuan > toolQuantityBasedOnToolName){
				   alert("Quantity Should be less than or equal to "+toolQuantityBasedOnToolName);
				     return false;

			   }
				
			}); 
	     
 	    
 		 if (e.model.isNew()){  
 			securityCheckForActions("./jobtools/createbutton");
 	    	 $(".k-window-title").text("Add Job Tools Details");
 		 	 $(".k-grid-update").text("Create");  	 		 	
	    }
	  	else{
	  		securityCheckForActions("./jobtools/editbutton");
	  		$(".k-window-title").text("Edit Job Tools Details");
	  		 $('.k-edit-field .k-input').first().focus(); 
	  		$(".k-grid-update").text("Update");  		
	  	}
 		 
		    e.container.find(".k-grid-cancel").bind("click", function () {
	   		var grid = $("#jobToolsgrid_"+SelectedRowId).data("kendoGrid");

			grid.dataSource.read();
			grid.refresh();
	    }); 
   }
    
    function jobtoolsRemove(e){
 	   securityCheckForActions("./jobtools/deletebutton");
    }
    
    function onRequestEndJobTools(e) {
	 	var resultOperation="";
	   	if (typeof e.response != 'undefined'){	   					
	   		
	   		if (e.response.status == "FAIL") {
	   			errorInfo = "";
	   			for (var i = 0; i < e.response.result.length; i++) {
	   				errorInfo += (i + 1) + ". "	+ e.response.result[i].defaultMessage+"\n";
	   			}		   			
		   		if (e.type == "create") {			   			
		   			resultOperation=errorInfo;	   			     			
		   		}	   	
		   		if (e.type == "update") {
		   			resultOperation=errorInfo;   			
		   		}
		   		$("#alertsBox").html("");
	   			$("#alertsBox").html(resultOperation);
	   			$("#alertsBox").dialog({
	   				modal : true,
	   				draggable: false,
					resizable: false,
	   				buttons : {
	   					"Close" : function() {
	   						$(this).dialog("close");
	   					}
	   				}
	   			});	   	
	   			
	   			var grid = $("#jobToolsgrid_"+SelectedRowId).data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
				
	   			return false;
	   		}
	   		if (e.type == "update" && !e.response.Errors) {
	   			resultOperation="Record Updated Successfully";	   		
	   		}
	   		if (e.type == "create" && !e.response.Errors) {
	   			resultOperation="Record Created Successfully";	  
	   			
	   		}		   		
	   		if (e.type == "destroy" && !e.response.Errors) {
	   			resultOperation="Record Deleted Successfully";	   			
	   		}	
	   		if(resultOperation!=""){
	   			$("#alertsBox").html("");
	   			$("#alertsBox").html(resultOperation);
	   			$("#alertsBox").dialog({
	   				modal : true,
	   				draggable: false,
					resizable: false,
	   				buttons : {
	   					"Close" : function() {
	   						$(this).dialog("close");
	   					}
	   				}
	   			});	 
	   			
	   			var grid = $("#jobToolsgrid_"+SelectedRowId).data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
	   		}
	   		
		}		
  	}
    
   
    
   /* ---------------- Job Materials---------------------------- */
   function parseJobMaterial (response) {   
	    $.each(response, function (idx, elem) {           
            if (elem.lastUpdatedDate && typeof elem.lastUpdatedDate === "string") {
                elem.lastUpdatedDate = kendo.parseDate(elem.lastUpdatedDate, "yyyy-MM-ddTHH:mm:ss.fffZ");
            }            
        });
        return response;
	}
   
   (function ($, kendo) {
       $.extend(true, kendo.ui.validator, {
            rules: {            	
            	jcmPartnoValidation: function (input, params) {
                    //check for the name attribute 
                    if (input.attr("name") == "jcmPartno") {
                   	 return $.trim(input.val()) !== "";
                    }
                    return true;
           	 	},
           		jcQuantityValidation: function (input, params) {
                 //check for the name attribute 
                 if (input.attr("name") == "jcmQuantinty") {
                	 return $.trim(input.val()) !== "";
                 }
                 return true;
        	 	},
        	 	rateValidation: function (input, params) {
                 //check for the name attribute 
                 if (input.attr("name") == "jcmrate") {
                	 return $.trim(input.val()) !== "";
                 }
                 return true;
        	 	},           	 	 
             },		               
            //custom rules messages
            messages: {             	
            	jcmPartnoValidation: "Part Number is required",
            	jcQuantityValidation: "Quantity is required",
            	rateValidation: "Rate is required",           	 	
            }
       });
   })(jQuery, kendo);
   
   function jcmTypeEditor(container, options) {
   	   var data = ["ASSET","ASSET SPARE","CONSUMABLE"];
   	   $(
   	     '<input name="Material Type" id="materialtype" data-text-field="" style="width:180px;" data-value-field="" data-bind="value:' + options.field + '" required="true" />Days')
   	     .appendTo(container).kendoComboBox({ 
   	      placeholder: "Select Material Type",
   	      dataSource :data,
   	      change : function (e) {
	          if (this.value() && this.selectedIndex == -1) {                    
	               alert("Material Type doesn't exist");
	               $("#materialtype").data("kendoComboBox").value("");

	       }
	  	},
   	   });
	   	$('<span class="k-invalid-msg" data-for="Material Type"></span>').appendTo(container);
     }   
   
	function storeMasterEditor(container, options) {
		$('<input name="Store Name" required="true" style="width:180px;"  data-text-field="storeMaster" id="storeMasterCombo" data-value-field="storeMasterId" data-bind="value:' + options.field + '"/>')
				.appendTo(container).kendoComboBox({
					 placeholder: "Select Store Name",									 
					 dataSource : {
						transport : {
							read : "${getStoreName}"
						}
					},
					  change : function (e) {
				          if (this.value() && this.selectedIndex == -1) {                    
				               alert("Store doesn't exist");
				               $("#storeMasterCombo").data("kendoComboBox").value("");
				       }
				  	},
				});	
		$('<span class="k-invalid-msg" data-for="Store Name"></span>').appendTo(container);
	}	
   
	function itemMasterEditor(container, options) {
		$('<input name="Item Name" required="true" style="width:180px;"  data-text-field="jcmitemName" id="itemMasterCombo" data-value-field="jcmitemId" data-bind="value:' + options.field + '"/>')
				.appendTo(container).kendoComboBox({
					 placeholder: "Select Material",
					 cascadeFrom: "storeMasterCombo",
					 select:onChangeItemName,
					 headerTemplate : '<div class="dropdown-header">'
							+ '<span class="k-widget k-header">Photo</span>'
							+ '<span class="k-widget k-header">Contact info</span>'
							+ '</div>',
						template : '<table><tr>'
							+ '<td align="left"><span class="k-state-default"><b>#: data.jcmitemName #</b></span><br>'
							+ '<span class="k-state-default"><b>Type:&nbsp;</b>&nbsp;&nbsp;<i>#: data.imType #</i></span><br>'
							+ '</td></tr></table>',
					 dataSource : {
						transport : {
							read : "${getMaterialName}"
						}
					},
				
					 change : function (e) {
				          if (this.value() && this.selectedIndex == -1) {                    
				               alert("Item doesn't exist");
				               $("#itemMasterCombo").data("kendoComboBox").value("");
				       }
				  	},
					
				});	
		$('<span class="k-invalid-msg" data-for="Item Name"></span>').appendTo(container);
	}
	
	
	var store;
	function onChangeItemName(e){
		 var value = this.dataItem(e.item.index());
		 store=value.storeMasterId;
	}
	
	function imUomEditor(container, options) {
		$('<input name="UOM" required="true" style="width:180px;"  id="imUOM" data-text-field="jcmimUom" data-value-field="jcmimUomId" data-bind="value:' + options.field + '"/>')
				.appendTo(container).kendoComboBox({
					 placeholder: "Select Unit",
					 cascadeFrom: "itemMasterCombo",
					 select:onChangeUOM,
		     		 dataSource : {
						transport : {
							read : "${getUOM}"
						}
					},
					 change : function (e) {
				          if (this.value() && this.selectedIndex == -1) {                    
				               alert("UOM doesn't exist");
				               $("#imUOM").data("kendoComboBox").value("");
				       }
				  	},
					
		});
		$('<span class="k-invalid-msg" data-for="UOM"></span>').appendTo(container);
	}
	
   function onChangeUOM(e){
	   
	    
	     var value = this.dataItem(e.item.index());
		 var jcmitemId=value.jcmitemId;
		 var jcmimUomId=value.jcmimUomId;
		 var result="";
		 
		 $.ajax({
	 			type : "GET",
	 			async: false,
	 			url : '${getQuantityforMaterials}/'+jcmitemId+"/"+jcmimUomId+"/"+store,
	 			dataType : "JSON",
	 			success : function(response) {		 				
	 				result=response;
	 			}
	 	});
		 
		 $('input[name="imQuantity"]').val(result);	  
		 $('input[name="imQuantity"]').change(); 
		 $('input[name="imQuantity"]').attr('readonly', 'readonly'); 
   }
   
   function materialDescriptionEditor(container, options){
       $('<textarea data-text-field="jcmMaterial" style="width:180px;" data-value-field="jcmMaterial" data-bind="value:' + options.field + '" style="width: 148px; height: 50px;"/>')
            .appendTo(container);        
   }
   
   function jobMaterialEvent(e){
   	 	 $('label[for="createdBy"]').parent().remove();
	   	 $('label[for="lastUpdatedBy"]').parent().remove();
	     $('label[for="lastUpdatedDate"]').parent().remove();
	     $('label[for="status"]').parent().remove();
	     $('label[for="returnedQuantinty"]').parent().remove();
	     $('input[name="jcmPartno"]').css('width', '180px');
	     $('input[name="imQuantity"]').css('width', '180px');
	     $('input[name="jcmQuantinty"]').css('width', '180px');
	     $('input[name="jcmrate"]').css('width', '180px');
	     
	     $('input[name="returnedQuantinty"]').parent().remove();
	     $('input[name="createdBy"]').parent().remove();
	     $('input[name="lastUpdatedBy"]').parent().remove();
	     $('div[data-container-for="lastUpdatedDate"]').hide();   
	     $('div[data-container-for="status"]').hide();   
	     $('input[name="imQuantity"]').attr('readonly', 'readonly');	     
 		 $('.k-edit-field .k-input').first().focus();
 	    
 		 if (e.model.isNew()){  
 			securityCheckForActions("./jobmaterial/createbutton");
 	    	 $(".k-window-title").text("Add Job Material Details");
 		 	 $(".k-grid-update").text("Create");  
 		 	 $(".k-grid-update").click(function () {
 		 		 var bal=$('input[name="imQuantity"]').val();
 		 		 var entered=$('input[name="jcmQuantinty"]').val(); 		 		
 		 		 if(parseInt(entered)>parseInt(bal)){
 		 			 alert("Only "+bal+" Are Available");
 		 			 return false;
 		 		 }
 		 	 });
	    }
	  	else{
	  		securityCheckForActions("./jobmaterial/editbutton");
	    	$(".k-window-title").text("Add Job Material Details");
	  		$('.k-edit-field .k-input').first().focus();
	  		$(".k-grid-update").text("Update");
	  		 $(".k-grid-update").click(function () {
 		 		 var bal=$('input[name="imQuantity"]').val();
 		 		 var entered=$('input[name="jcmQuantinty"]').val();
 		 		 if(parseInt(entered)>parseInt(bal)){
 		 			 alert("Only "+bal+" Are Available");
 		 			 return false;
 		 		 }
 		 	 });
	  	}	
   } 
   
   function jobmaterialRemove(e){
	   securityCheckForActions("./jobmaterial/deletebutton");
   }
   
   function jobMaterialsdataBound(e) 
	{
	    
	  	var vgrid = $("#jobMaterialgrid_"+SelectedRowId).data("kendoGrid");
	  	var items = vgrid.dataSource.data();
	 	var i = 0;
	 	var temp = 1;
	 	this.tbody.find("tr td:first-child").each(function (e) 
	   	{
	  	  	var item = items[i];	  	  	
		   	if(item.status == 'Issued')
		   	{
		   		$("<button id='testMaterials' class='k-button k-button-icontext' onclick='jobMaterialStatusClick("+temp+")'>Return</button>").appendTo(this);
		   		//$('<a class="k-button k-button-icontext k-grid-edit" href="\#"><span class="k-icon k-edit"></span>Edit</a>').appendTo(this);
		   	}		   	
		   	i++;
		   	temp++;
	   	});
	}
   function jobMaterialStatusClick(index){	   
	   var grid = $("#jobMaterialReturngrid_"+SelectedRowId).data("kendoGrid");
	   grid.editRow($("#jobMaterialReturngrid_"+SelectedRowId+" tr:eq("+index+")"));	  
   }
   
   function onRequestEndJobMaterial(e) {
	 	var resultOperation="";
	   	if (typeof e.response != 'undefined'){	   					
	   		
	   		if (e.response.status == "FAIL") {
	   			errorInfo = "";
	   			for (var i = 0; i < e.response.result.length; i++) {
	   				errorInfo += (i + 1) + ". "	+ e.response.result[i].defaultMessage+"\n";
	   			}		   			
		   		if (e.type == "create") {			   			
		   			resultOperation=errorInfo;	   			     			
		   		}	   	
		   		if (e.type == "update") {
		   			resultOperation=errorInfo;   			
		   		}
		   		$("#alertsBox").html("");
	   			$("#alertsBox").html(resultOperation);
	   			$("#alertsBox").dialog({
	   				modal : true,
	   				draggable: false,
					resizable: false,
	   				buttons : {
	   					"Close" : function() {
	   						$(this).dialog("close");
	   					}
	   				}
	   			});	   	
	   			
	   			var grid = $("#jobMaterialgrid_"+SelectedRowId).data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
				
	   			return false;
	   		}
	   		if (e.type == "update" && !e.response.Errors) {
	   			resultOperation="Record Updated Successfully";	   		
	   		}
	   		if (e.type == "create" && !e.response.Errors) {
	   			resultOperation="Record Created Successfully";	
	   			var grid = $("#jobMaterialReturngrid_"+SelectedRowId).data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
	   		}		   		
	   		if (e.type == "destroy" && !e.response.Errors) {
	   			resultOperation="Record Deleted Successfully";	   			
	   		}	
	   		if(resultOperation!=""){
	   			$("#alertsBox").html("");
	   			$("#alertsBox").html(resultOperation);
	   			$("#alertsBox").dialog({
	   				modal : true,
	   				draggable: false,
					resizable: false,
	   				buttons : {
	   					"Close" : function() {
	   						$(this).dialog("close");
	   					}
	   				}
	   			});	 
	   			
	   			var grid = $("#jobMaterialgrid_"+SelectedRowId).data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
	   		}
	   		
		}		
	}
   
   /* ---------------- Job Labour Task---------------------------- */
   
    function parseLabourTask (response) {   
	    $.each(response, function (idx, elem) {           
            if (elem.lastUpdatedDate && typeof elem.lastUpdatedDate === "string") {
                elem.lastUpdatedDate = kendo.parseDate(elem.lastUpdatedDate, "yyyy-MM-ddTHH:mm:ss.fffZ");
            }            
        });
        return response;
	}	
	
   (function ($, kendo) {
        $.extend(true, kendo.ui.validator, {
             rules: { // custom rules            	 
            	 jclTypeValidation: function (input, params) {
                     //check for the name attribute 
                     if (input.attr("name") == "jclType") {
                    	 return $.trim(input.val()) !== "";
                     }
                     return true;
                 },	
                 jclHoursValidation: function (input, params) {
                     //check for the name attribute 
                     if (input.attr("name") == "jclHours") {
                    	 return $.trim(input.val()) !== "";
                     }
                     return true;
                 },	 
                 jclHoursPatternValidation: function (input, params) {
                	 if (input.filter("[name='jclHours']").length && input.val()) {
                         return /^[0-9]+$/.test(input.val());
                     }
                     return true;
                 },	                 
                 jclRatevalidation: function (input, params) {
                     //check for the name attribute 
                     if (input.attr("name") == "jclRate") {
                    	 return $.trim(input.val()) !== "";
                     }
                     return true;
                 },  
                 jclRatePatternValidation: function (input, params) {
                	 if (input.filter("[name='jclRate']").length && input.val()) {
                		 return /^[0-9]+$/.test(input.val());
                     }
                     return true;
                 },	                             
             },
           //custom rules messages
             messages: { 
            	 jclTypeValidation: "Labour Type is required",
            	 jclHoursValidation:"Hours is required", 
            	 jclRatevalidation:"Labour Rate is required",             	 
            	 jclRatePatternValidation:"Only Numbers Are Allowed",
            	 jclHoursPatternValidation:"Only Numbers Are Allowed",	
            }
        });
    })(jQuery, kendo);    
   
   function jclBillableEditor(container, options) {
   	   var data = ["YES","NO"];
   	   $(
   	     '<input name="Labour Billable" data-text-field="" id="labourBill" data-value-field="" data-bind="value:' + options.field + '" required="true" />')
   	     .appendTo(container).kendoComboBox({   	    
   	      dataSource :data,
   	       change : function (e) {
	          if (this.value() && this.selectedIndex == -1) {                    
	               alert("Select Proper Billable Type");
	               $("#labourBill").data("kendoComboBox").value("");
	       }
	  	},
   	   });
	   	$('<span class="k-invalid-msg" data-for="Labour Billable"></span>').appendTo(container);
     } 
    
  function jobLabourTaskEvent(e){
	  
		 $('label[for="createdBy"]').parent().remove();
	   	 $('label[for="lastUpdatedBy"]').parent().remove();
	     $('label[for="lastUpdatedDate"]').parent().remove();
	     $('input[name="jclRate"]').after('<label>&nbsp;/ Hour</label>');
	     $('input[name="createdBy"]').parent().remove();
	     $('input[name="lastUpdatedBy"]').parent().remove();
	     $('div[data-container-for="lastUpdatedDate"]').hide();   	     
	     
		 $('.k-edit-field .k-input').first().focus();
	    
		 if (e.model.isNew()){  	
			 securityCheckForActions("./labourtask/createbutton");
	    	 $(".k-window-title").text("Add Labour Task Details");
		 	 $(".k-grid-update").text("Create");  		 
	    }
	  	else{
	  		securityCheckForActions("./labourtask/editbutton");
	  		$(".k-window-title").text("Edit Labour Task Details");
	  		$('.k-edit-field .k-input').first().focus();
	  		$(".k-grid-update").text("Update");
	  	}	
  }
  function joblabourtaskRemove(e){
	  securityCheckForActions("./labourtask/deletebutton");
  }
  
  function onRequestEndLabourTask(e) {
	 	var resultOperation="";
	   	if (typeof e.response != 'undefined'){	   					
	   		
	   		if (e.response.status == "FAIL") {
	   			errorInfo = "";
	   			for (var i = 0; i < e.response.result.length; i++) {
	   				errorInfo += (i + 1) + ". "	+ e.response.result[i].defaultMessage+"\n";
	   			}		   			
		   		if (e.type == "create") {			   			
		   			resultOperation=errorInfo;	   			     			
		   		}	   	
		   		if (e.type == "update") {
		   			resultOperation=errorInfo;   			
		   		}
		   		$("#alertsBox").html("");
	   			$("#alertsBox").html(resultOperation);
	   			$("#alertsBox").dialog({
	   				modal : true,
	   				draggable: false,
					resizable: false,
	   				buttons : {
	   					"Close" : function() {
	   						$(this).dialog("close");
	   					}
	   				}
	   			});	   	
	   			
	   			var grid = $("#jobLabourTaskgrid_"+SelectedRowId).data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
				
	   			return false;
	   		}
	   		if (e.type == "update" && !e.response.Errors) {
	   			resultOperation="Record Updated Successfully";	   		
	   		}
	   		if (e.type == "create" && !e.response.Errors) {
	   			resultOperation="Record Created Successfully";	   			
	   		}		   		
	   		if (e.type == "destroy" && !e.response.Errors) {
	   			resultOperation="Record Deleted Successfully";	   			
	   		}	
	   		if(resultOperation!=""){
	   			$("#alertsBox").html("");
	   			$("#alertsBox").html(resultOperation);
	   			$("#alertsBox").dialog({
	   				modal : true,
	   				draggable: false,
					resizable: false,
	   				buttons : {
	   					"Close" : function() {
	   						$(this).dialog("close");
	   					}
	   				}
	   			});	 
	   			
	   			var grid = $("#jobLabourTaskgrid_"+SelectedRowId).data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
	   		}
	   		
		}		
	} 
	
  	/*-------------------------------- Document Uploading---------------------- */	
  	
  	 (function ($, kendo) {
        $.extend(true, kendo.ui.validator, {
             rules: { // custom rules            	 
            	 documentNameValidation: function (input, params) {
                     //check for the name attribute 
                     if (input.attr("name") == "documentName") {
                    	 return $.trim(input.val()) !== "";
                     }
                     return true;
                 },	                                        
             },
           //custom rules messages
             messages: { 
            	 documentNameValidation: "Document Name is required",            	
            }
        });
    })(jQuery, kendo);    
  	
	function documentTypeEditor(container, options) {
   	   var data = ["Image","PDF"];
   	   $(
   	     '<input name="Document Type" data-text-field="" id="notificationType" data-value-field="" data-bind="value:' + options.field + '" required="true" />')
   	     .appendTo(container).kendoComboBox({   	    
   	      dataSource :data,
   	      change : function (e) {
	          if (this.value() && this.selectedIndex == -1) {                    
	               alert("DocType doesn't exist");
	               $("#notificationType").data("kendoComboBox").value("");
	       }
	  	}
   	   });
   	   
   	    
	   	$('<span class="k-invalid-msg" data-for="Document Type"></span>').appendTo(container);
     }   
  	
	function jobDocumentsDatabound(e) {
	    this.tbody.find("tr td:first-child").each(function (e){
	        $("<input type='file' name='files'/>").appendTo(this).kendoUpload({
	            async: {
	                saveUrl: "${saveUrl}",
	                removeUrl: "remove",
	                autoUpload: false,
	            },
	        	multiple : false,
	            upload : uploadExtraDataAlongWithFile, 
	            select : filesSelectedtoUpload,
	            success: clearList
	       	 });
	    });
	}
  	
	var selectedRowIndex = "";
	function jobdocumentChangeEvent(args)
	{
		var grid = $("#jobDocumentsgrid_"+SelectedRowId).data("kendoGrid");
		var selectedRow = grid.select();
		selectedRowIndex = selectedRow.index();
	}
  	
	function jobdocumentEvent(e){
	
		$('label[for="undefined"]').closest('.k-edit-label').remove();
		if (e.model.isNew()) 
	    {
			securityCheckForActions("./jobdocuments/createbutton");
			selectedRowIndex = 0;
			$(".k-window-title").text("Add New Document Details");
			$(".k-grid-update").text("Save");
			$('.k-edit-field .k-input').first().focus();
	    }
		else
		{
			securityCheckForActions("./jobdocuments/editbutton");
			$(".k-window-title").text("Update Document Details");
			$(".k-grid-update").text("Update");
			$('.k-edit-field .k-input').first().focus();
		}
	}
	
	function jobdocumentsRemove(e){
 		securityCheckForActions("./jobdocuments/deletebutton");
 	}
	
	var jobDocId = "";
	var  documentTypeToUpload="";
	function filesSelectedtoUpload(e){
			var gview = $("#jobDocumentsgrid_"+SelectedRowId).data("kendoGrid");
			var selectedItem = gview.dataItem(gview.select());
			if (selectedItem != null) {
			  jobDocId = selectedItem.jobDocId;
			  documentTypeToUpload = selectedItem.documentType;			 
			} 
		 	var files = e.files;
		  	var requiredFormat = $("#docFormat_"+SelectedRowId).val();
		  	requiredFormat = "."+documentTypeToUpload.toLowerCase();//requiredFormat.toLowerCase();
		 	if(requiredFormat == '') {
				alert("Please select Document Type");
				return false;
			}
			if( files.length  > 10 ) {
				alert("Maximum 10 files can be uploaded at a time.");
				e.preventDefault();
				return false;
			}		 
			for(var fileCntr = 0; fileCntr < files.length; fileCntr ++) {
				if( files[fileCntr].size > 10485760 ){
					alert("File size more than 10MB can not be uploaded.");
					e.preventDefault();
					return false;
				}
				
				if(documentTypeToUpload == "Image"){
					if( files[fileCntr].extension.toLowerCase() == '.png' || files[fileCntr].extension.toLowerCase() == '.jpg' || files[fileCntr].extension.toLowerCase() == '.jpeg' ){
						 
					}
					else{
						alert("Only Images can be uploaded\nAcceptable formats are png, jpg and jpeg");
						e.preventDefault();
						return false;
					}				
				}
				else{
					if( files[fileCntr].extension.toLowerCase() != requiredFormat ) {
						alert("Please Upload "+documentTypeToUpload+" Format");
						e.preventDefault();
						return false;
					}				
				}				
			}
	}
	function downloadFile(){
		var gview = $("#jobDocumentsgrid_"+SelectedRowId).data("kendoGrid");
		var selectedItem = gview.dataItem(gview.select());
		window.open("./downloadJobDocument/"+selectedItem.jobDocId);		
	}
	function uploadExtraDataAlongWithFile(e){			
		e.data = { jobDocId:jobDocId};
	}
	function clearList(){
		
		    $("#alertsBox").html("");
			$("#alertsBox").html("Uploaded Successfully");
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
		 $(".k-upload-status").remove();
	     $(".k-upload-files.k-reset").find("li").remove();
	     $(".k-upload-status k-upload-status-total").text("");

    }
	 function onRequestJobDocuments(e) {
		 	var resultOperation="";
		   	if (typeof e.response != 'undefined'){	   					
		   		
		   		if (e.response.status == "FAIL") {
		   			errorInfo = "";
		   			for (var i = 0; i < e.response.result.length; i++) {
		   				errorInfo += (i + 1) + ". "	+ e.response.result[i].defaultMessage+"\n";
		   			}		   			
			   		if (e.type == "create") {			   			
			   			resultOperation=errorInfo;	   			     			
			   		}	   	
			   		if (e.type == "update") {
			   			resultOperation=errorInfo;   			
			   		}
			   		$("#alertsBox").html("");
		   			$("#alertsBox").html(resultOperation);
		   			$("#alertsBox").dialog({
		   				modal : true,
		   				draggable: false,
						resizable: false,
		   				buttons : {
		   					"Close" : function() {
		   						$(this).dialog("close");
		   					}
		   				}
		   			});	   	
		   			
		   			var grid = $("#jobDocumentsgrid_"+SelectedRowId).data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
					
		   			return false;
		   		}
		   		if (e.type == "update" && !e.response.Errors) {
		   			resultOperation="Record Updated Successfully";	   		
		   		}
		   		if (e.type == "create" && !e.response.Errors) {
		   			resultOperation="Record Created Successfully";	   			
		   		}		   		
		   		if (e.type == "destroy" && !e.response.Errors) {
		   			resultOperation="Record Deleted Successfully";	   			
		   		}	
		   		if(resultOperation!=""){
		   			$("#alertsBox").html("");
		   			$("#alertsBox").html(resultOperation);
		   			$("#alertsBox").dialog({
		   				modal : true,
		   				draggable: false,
						resizable: false,
		   				buttons : {
		   					"Close" : function() {
		   						$(this).dialog("close");
		   					}
		   				}
		   			});	 
		   			
		   			var grid = $("#jobDocumentsgrid_"+SelectedRowId).data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
		   		}
		   		
			}		
		}
	 	
	 
	 	function jobMaterialReturnsEvent(e){
	 		$(".k-window-title").text("Return Job Materials");
	 		 $('input[name="jcmQuantinty"]').css('width', '180px');
	 		 $('input[name="jcmType"]').css('width', '180px');
	 		 $('input[name="jcmitemName"]').css('width', '180px');
	 		 $('input[name="storeMaster"]').css('width', '180px');
	 		 $('input[name="jcmimUom"]').css('width', '180px');
		     $('div[data-container-for="status"]').hide();   
		     $('label[for="status"]').parent().remove();
		     
	 		 $('input[name="jcmQuantinty"]').attr('readonly', 'readonly');
	 		 $('input[name="jcmType"]').attr('readonly', 'readonly');
	 		 $('input[name="storeMaster"]').attr('readonly', 'readonly');
	 		 $('input[name="jcmitemName"]').attr('readonly', 'readonly');
	 		 $('input[name="jcmimUom"]').attr('readonly', 'readonly');
	 		 e.model.set("newStatus", "Returned" );
		     var jcmquantity=$('input[name="jcmQuantinty"]').val();
		     
			   /*   if (!(e.model.isNew())){		    	
					
					if(jobcardstatus!="CLOSED"){
						$("#alertsBox").html("");
			   			$("#alertsBox").html("Job is Not Yet Closed,You cannot Return Materials");
			   			$("#alertsBox").dialog({
			   				modal : true,
			   				draggable: false,
							resizable: false,
			   				buttons : {
			   					"Close" : function() {
			   						$(this).dialog("close");
			   					}
			   				}
			   			});	 					
						var grid = $("#jobMaterialReturngrid_"+SelectedRowId).data("kendoGrid");
						grid.cancelRow();
						
					}		    	
				 } */
		     
		     $(".k-grid-update").click(function () {
 		 		 var entered=$('input[name="returnedQuantinty"]').val();
 		 		 var uom=$('input[name="jcmimUom"]').val();
		    	 if(parseInt(entered)>parseInt(jcmquantity)){
 		 			 alert("Only "+jcmquantity+" ("+uom +") Materials You can Return");
 		 			 return false;
 		 		 }
 		 	 });

	 	}
	 	
	
    </script> 
<!-- </div> -->

<style>	
	.bgGreenColor{
	background: #99E699
	}	
	.bgYellowColor{
	background: #FFFF70
	}	
	.bgBlueColor{
	background: #82CDFF
	}	
	.bgRedColor{
	background: #FF9999
	}
	.jobObjectivetabStrip{		
		width: 1000px;	
	}
	.jobDocumentstabStrip{		
		width: 1200px;	
	}
	.jobCardOthertabStrip{		
		width: 1150px;	
	}
	.jobNotificationtabStrip{
		width: 2200px;	
	}
	.jobNotestabStrip{
		width: 1000px;	
	}
	.jobToolstabStrip{
		width: 1500px;	
	}
	.jobTeamtabStrip{
		width: 2000px;	
	}
	.jobMaterialtabStrip{
		width: 2000px;	
	}
	.jobLabourTasktabStrip{
		width: 2000px;	
	} 
	td{
		vertical-align: top;
		padding: 5px;
	}
	.k-edit-form-container {	
		text-align: left;
		position: relative;		
	}
	div.ui-dialog {position:fixed;overflow:"auto";} 

	.wrappers {
		display: inline;
		float: left;
		width: 350px;	
	}
</style>
