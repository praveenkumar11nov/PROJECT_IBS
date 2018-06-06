<%@include file="/common/taglibs.jsp"%>


<c:url value="/patrolTracks/patrolTrackPoint/read" var="readPointUrl" />
<c:url value="/patrolTracks/patrolTrackStaff/read" var="readStaffUrl" />
<c:url value="/patrolTracks/patrolTrackAlert/read" var="readAlertUrl" />

<c:url value="/patrolTracks/readPatrolTracks" var="readUrl" />
	<c:url value="/patrolTracks/createPatrolTrack" var="createUrl" />
	<c:url value="/patrolTracks/updatePatrolTrack" var="updateUrl" />
	<c:url value="/patrolTracks/deletePatrolTracks" var="destroyUrl" />
 	<c:url value="/patrolTracks/getCreatedByValue" var="createdByUrl" />
	
	<c:url value="/patrolTracks/getPatrolTrackNames" var="patroltrackNamesUrl" />
	<c:url value="/PatrolTrackPoints/getPatrolTrackNamesList" var="getPatroltrackNameListUrl" />
		<c:url value="/PatrolTrackPoints/getAccessRepositoryNamesList" var="getRepositoryDataListUrl" />
		
		<c:url value="/patrolTracks/getStatusList" var="statusUrl" />
	
	<kendo:grid name="trackGrid" pageable="true" detailTemplate="template"
		resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true"
		filterable="true" groupable="true" >
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" ></kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
		 <kendo:grid-filterable-operators>
		  	<kendo:grid-filterable-operators-string eq="Is equal to"/>
		 </kendo:grid-filterable-operators>
			
		</kendo:grid-filterable>
		
		 <kendo:grid-editable mode="popup"
			confirmation="Are you sure you want to remove this Patrol Track Alert?" />
			<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Patrol Track" />
			<kendo:grid-toolbarItem name="Clear_Filter" text="Clear Filter"/>
		</kendo:grid-toolbar>
		
		<kendo:grid-columns>
			<%-- <kendo:grid-column title="PatrolTrack Name" field="ptName" template="#=pkNameDisplay(data)#" 
			filterable="true" width="130px" /> --%>
			
			
			<kendo:grid-column title="Patrol Track Name *" field="ptName"
				 width="130px">
				<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function loginNameFilter(element) {
								element.kendoAutoComplete({
									dataSource: {
										transport: {
											read: "${patroltrackNamesUrl}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	        
			</kendo:grid-column>
				<kendo:grid-column  hidden="true"></kendo:grid-column>
			
			<kendo:grid-column title="Valid Time From *" field="validTimeFrom" format="{0:HH:mm}" editor="fromTimeEditor"
				width="130px" >
				<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function fromTimeFilter(element) {
								element.kendoTimePicker({
					            	name:(""),
					                Value:("")
					            	
				            });
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	 
				</kendo:grid-column>
				<kendo:grid-column hidden="true"></kendo:grid-column>
			<kendo:grid-column title="Valid Time To *" field="validTimeTo" format="{0:HH:mm}" editor="toTimeEditor"
				width="130px" >
				<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function toTimeFilter(element) {
								element.kendoTimePicker({
					            	name:(""),
					                Value:("$now")
					            	
				            });
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	 
				</kendo:grid-column>
				<kendo:grid-column title="Description *" field="description" editor="descriptionEditor"
				filterable="false" width="130px" format="{0:[A-Z]}"/>
			<kendo:grid-column title="Status" field="status" width="130px">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function statusFilter(element) {
								element.kendoAutoComplete({
									dataSource: {
										transport: {
											read: "${statusUrl}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	 
			</kendo:grid-column>
			
			<kendo:grid-column title="&nbsp;" width="100px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit" click="edit"/>
				</kendo:grid-column-command>
			</kendo:grid-column>
			<kendo:grid-column title="&nbsp;" width="100px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="destroy"/>
				</kendo:grid-column-command>
			</kendo:grid-column>
			<kendo:grid-column title=""
				template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Activate#=data.ptId#'>#= data.status == 'Active' ? 'De-activate' : 'Activate' #</a>"
				width="100px" />
		</kendo:grid-columns>
		<kendo:dataSource requestEnd="onRequestEnd" requestStart="OnRequestStart">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-read url="${readUrl}" dataType="json"
					type="POST" contentType="application/json" />
				<kendo:dataSource-transport-create url="${createUrl}"
					dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-update url="${updateUrl}"
					dataType="json" type="POST" contentType="application/json" />
					<kendo:dataSource-transport-destroy url="${destroyUrl}"
					dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-parameterMap>
					<script>
						function parameterMap(options, type) {
							return JSON.stringify(options);
						}
					</script>
				</kendo:dataSource-transport-parameterMap>
			</kendo:dataSource-transport>
			<kendo:dataSource-schema parse="parseTrackName">
				<kendo:dataSource-schema-model id="ptId">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="ptId" editable="false" />
						<kendo:dataSource-schema-model-field name="ptName" type="string" >
						 <kendo:dataSource-schema-model-field-validation/>
						 </kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="description" type="string"  >
						<kendo:dataSource-schema-model-field-validation required="true"/>
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="validTimeFrom" >
						<kendo:dataSource-schema-model-field-validation/>
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="validTimeTo" />
						<kendo:dataSource-schema-model-field name="adminAlertMobileNo" type="tel" >
						 <kendo:dataSource-schema-model-field-validation required="true"
								pattern="\d{10}" /> 
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="adminAlertEmailId" type="email">
							 <kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="status" type="string" >
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="createdBy" type="string">
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="lastUpdatedBy" type="string">
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="lastUpdatedDt" type="date"/>
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
		
		</kendo:grid>
		
		<kendo:grid-detailTemplate id="template">
		<kendo:tabStrip name="tabStrip_#=ptId#">
		<kendo:tabStrip-animation>
				<!-- <tabStrip-animation-open effects="fadeIn" /> -->
			</kendo:tabStrip-animation>
			<kendo:tabStrip-items>
				<kendo:tabStrip-item text="PatrolTrack Point" selected="true">
				<kendo:tabStrip-item-content>
				
				<kendo:grid name="grid2_#=ptId#" resizable="true"
							sortable="true" scrollable="true" groupable="false">
							
							<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" ></kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
		 <kendo:grid-filterable-operators>
		  	<kendo:grid-filterable-operators-string eq="Is equal to"/>
		 </kendo:grid-filterable-operators>
		</kendo:grid-filterable>
		
		<kendo:grid-editable mode="popup"
			confirmation="Are you sure you want to remove this PatrolTrack?" />
		<kendo:grid-toolbar>
		</kendo:grid-toolbar>
		<kendo:grid-columns>
		<kendo:grid-column title="PatrolTrack Name" field="ptName" width="100px" filterable="false"  >
				</kendo:grid-column>
			<kendo:grid-column title="AccessRepository Name" field="arName"	width="100px" filterable="false" >
			</kendo:grid-column>
			<kendo:grid-column title="Sequence " field="ptpSequence"
				width="100px" filterable="false" />
			<kendo:grid-column title="Interval" field="ptpVisitInterval" format="{0:HH:mm}" 
				width="100px" filterable="false" />
			<kendo:grid-column title="Status" field="status" filterable="false"
				width="100px" />
		</kendo:grid-columns>
		<kendo:dataSource >
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-read url="${readPointUrl}/#=ptId#" dataType="json"
					type="POST" contentType="application/json" />
				<kendo:dataSource-transport-create url="${createPointUrl}/#=ptId#"
					dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-update url="${updatePointUrl}/#=ptId#"
					dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-destroy url="${destroyPointUrl}/#=ptId#"
					dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-parameterMap>
					<script>
						function parameterMap(options, type) {
							return JSON.stringify(options);
						}
					</script>
				</kendo:dataSource-transport-parameterMap>
			</kendo:dataSource-transport>
			 <kendo:dataSource-schema>
				<kendo:dataSource-schema-model id="ptpId">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="ptpId" editable="false" />
						<kendo:dataSource-schema-model-field name="ptName" type="string">
						 <kendo:dataSource-schema-model-field-validation required="true" />
						 </kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="arName" type="string">
						<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="ptpSequence" type="number">
						<kendo:dataSource-schema-model-field-validation required="true"/>
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="ptpVisitInterval" >
						<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="status" type="String" defaultValue="Active">
							 <kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="createdBy" type="string">
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="lastUpdatedBy" type="string">
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="lastUpdatedDt" type="date"/>
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
			</kendo:grid>
				</kendo:tabStrip-item-content>
				</kendo:tabStrip-item>
				
			 	<kendo:tabStrip-item text="PatrolTrack Staff" selected="false">
				<kendo:tabStrip-item-content>
				
				<kendo:grid name="grid3_#=ptId#" resizable="false"
							sortable="true" scrollable="true" groupable="false">
							
							 <kendo:grid-editable mode="popup"
			confirmation="Are you sure you want to remove this PatrolTrack Staff?" />

		<kendo:grid-columns>
		<kendo:grid-column title="Staff Name" field="firstName" width="100px">
		</kendo:grid-column>
		<kendo:grid-column title="AccessCard Number" field="acNo" width="100px">
				</kendo:grid-column>
			 <kendo:grid-column title="PatrolTrack Name" field="ptName" width="100px">
				</kendo:grid-column>
				<kendo:grid-column title="Supervisor Name" field="supervisorName" width="130px"/>
				<kendo:grid-column title="From Date *" field="fromDate" format="{0:dd/MM/yyyy}" 
				width="130px" filterable="true">
				</kendo:grid-column>
				<kendo:grid-column hidden="true"/>
			<kendo:grid-column title="To Date *" field="toDate" format="{0:dd/MM/yyyy}"
			 width="130px" filterable="true">
			 </kendo:grid-column>
			<kendo:grid-column title="Status" field="status" filterable="false"
				width="100px" /> 
		</kendo:grid-columns>
		<kendo:dataSource>
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-read url="${readStaffUrl}/#=ptId#" dataType="json"
					type="POST" contentType="application/json" />
				<kendo:dataSource-transport-create url="${createStaffUrl}/#=ptId#"
					dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-update url="${updateStaffUrl}/#=ptId#"
					dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-destroy url="${destroyStaffUrl}/#=ptId#"
					dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-parameterMap>
					<script>
						function parameterMap(options, type) {
							return JSON.stringify(options);
						}
					</script>
				</kendo:dataSource-transport-parameterMap>
			</kendo:dataSource-transport>
			 <kendo:dataSource-schema parse="parse">
				<kendo:dataSource-schema-model id="ptsId">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="ptsId" editable="false" />
						<kendo:dataSource-schema-model-field name="ptName" type="string">
						 <kendo:dataSource-schema-model-field-validation required="true" />
						 </kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="firstName">
						<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="acNo">
						<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="supervisorName">
						<kendo:dataSource-schema-model-field-validation />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="fromDate" type="date" defaultValue="">
						<kendo:dataSource-schema-model-field-validation />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="toDate" type="date" defaultValue=""> 
						<kendo:dataSource-schema-model-field-validation />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="status" type="String">
						<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="createdBy" type="string">
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="lastUpdatedBy" type="string">
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="lastUpdatedDt" type="date"/>
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
							
			</kendo:grid>
				</kendo:tabStrip-item-content>
				</kendo:tabStrip-item>
				
				<kendo:tabStrip-item text="PatrolTrack Alert">
				<kendo:tabStrip-item-content>
				
				<kendo:grid name="grid4_#=ptId#" resizable="false"
							sortable="true" scrollable="true" groupable="false">
							
							<kendo:grid-columns>
		 <kendo:grid-column title="PatrolTrack Name" field="ptName" width="100px">
				</kendo:grid-column>
		<kendo:grid-column title="Staff Name" field="firstName" width="100px">
		</kendo:grid-column>
			 <kendo:grid-column title="Message" field="message" filterable="false" width="100px" >
			</kendo:grid-column> 
				<kendo:grid-column title="Alert Date " field="ptaDt" format="{0:dd/MM/yyyy}"  editor="dateEditor" 
				width="100px" filterable="false" />
		</kendo:grid-columns>
		<kendo:dataSource>
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-read url="${readAlertUrl}/#=ptId#" dataType="json"
					type="POST" contentType="application/json" />
				<kendo:dataSource-transport-create url="${createAlertUrl}/#=ptId#"
					dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-update url="${updateAlertUrl}/#=ptId#"
					dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-destroy url="${destroyAlertUrl}/#=ptId#"
					dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-parameterMap>
					<script>
						function parameterMap(options, type) {
							return JSON.stringify(options);
						}
					</script>
				</kendo:dataSource-transport-parameterMap>
			</kendo:dataSource-transport>
			 <kendo:dataSource-schema>
				<kendo:dataSource-schema-model id="ptaId">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="ptaId" editable="false" />
						<kendo:dataSource-schema-model-field name="ptName" type="string">
						 <kendo:dataSource-schema-model-field-validation required="true" />
						 </kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="firstName">
						<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="message">
						<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="ptaDt" type="date">
						<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="ptaMobileNo" type="tel" >
						 <kendo:dataSource-schema-model-field-validation required="true"
								pattern="\d{10}" /> 
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="ptaEmail" type="email">
							 <kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
							
			</kendo:grid>
				</kendo:tabStrip-item-content>
				</kendo:tabStrip-item>
				
				</kendo:tabStrip-items>
		</kendo:tabStrip>
		</kendo:grid-detailTemplate>
		
		<div id="alertsBox" title="Alert"></div>	
		
		<style>
textarea.k-textbox{white-space: normal;}
		</style>
		<script>
		var trackArray = [];
		var enterdTrack = "";
		var existingTrack = "";
		//var res1 = [];
		var res1 = new Array();
		var res2 = [];
		var flag = "";
		function parse (response) {   
		    $.each(response, function (idx, elem) {
	            if (elem.ptsValidFrom && typeof elem.ptsValidFrom === "string") {
	                elem.ptsValidFrom = kendo.parseDate(elem.ptsValidFrom, "yyyy-MM-ddTHH:mm:ss.fffZ");
	            }
	            if (elem.ptsValidTo && typeof elem.ptsValidTo === "string") {
	                elem.ptsValidTo = kendo.parseDate(elem.ptsValidTo, "yyyy-MM-ddTHH:mm:ss.fffZ");
	            }
	        });
	        return response;
		}  
		function parseTrackName (response) {   
		    var data = response; //<--data might be in response.data!!!
		    trackArray = [];
			 for (var idx = 0, len = data.length; idx < len; idx ++) {
				var res = [];
				res = (data[idx].ptName);
				trackArray.push(res);
			 }
			// alert(accountNoArray);
			 return response;
		}
		function dataBound() {
			this.expandRow(this.tbody.find("tr.k-master-row").first());
		}
		
		function dateEditor(container, options) {
		    $('<input data-text-field="' + options.field + '" data-value-field="' + options.field + '" data-bind="value:' + 
		    		options.field + '" data-format="' + ["dd/MM/yyyy"] + '"/>')
		            .appendTo(container)
		            .kendoDatePicker({
		            	placeholder : "Please select to time"
		            });
		}
		
		$("#trackGrid").on("click", ".k-grid-Clear_Filter", function(){
		    //custom actions
		    $("form.k-filter-menu button[type='reset']").slice().trigger("click");
			var grid = $("#trackGrid").data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
		});
		
		
		var test1 = "";
		  var patroltrackName = "";
		  var des = "";
		  var res = [];
		  var editingName = "";
		  
		  var flagUserId = "";
		  
		  /* function pkNameDisplay(data) {
				$.each(data, function( index, value ) {
					if(index == "ptName" && value != "" && patroltrackName != value)
					{
					        if ($.inArray(value, res) == -1) res.push(value);	
					}
					
				});	
				
				return data.ptName;
		} */
		  
		$("#trackGrid").on("click", ".k-grid-add", function() {
			res1 = [];
			$.ajax({
				type : "GET",
				dataType:"text",
				url : '${patroltrackNamesUrl}',
				dataType : "JSON",
				success : function(response) {
					 for(var i = 0; i<response.length; i++) {
							res1[i] = response[i];
						
						} 
				}
			});
			
			if($("#trackGrid").data("kendoGrid").dataSource.filter()){
		          //$("#grid").data("kendoGrid").dataSource.filter({});
		          $("form.k-filter-menu button[type='reset']").slice().trigger("click");
		        var grid = $("#trackGrid").data("kendoGrid");
		        grid.dataSource.read();
		        grid.refresh();
		            }
			$('label[for="status"]').closest('.k-edit-label').hide();
			$('div[data-container-for="status"]').hide(); 
			$(".k-window-title").text("Add Patrol Track");
			/* $(".k-edit-label:first").hide(); */
			$(".k-grid-update").text("Save");
		//	$(".k-grid-Activate").remove();
		 $(".k-edit-field").each(function () {
	   		   $(this).find("#temPID").parent().remove();
	     });
		 $(".k-grid-update").click(function () {
			 
			 var firstItem = $('#trackGrid').data().kendoGrid.dataSource.data()[0];
			 var fromTime = firstItem.get('validTimeFrom');
			 var toTime = firstItem.get('validTimeTo');
			 var des = firstItem.get('description');
			 var track = firstItem.get('ptName');
			 if( track == ""){
				 alert("Please enter patrol Track name");
				 return false;
			 }
			 if( fromTime == "" && toTime == "" )
				 {
				 alert("Please select From Time and To Time");
				 return false;
				 
				 }
			 if( fromTime != "" && toTime != "" )
				 {
			 if( fromTime == toTime ){
				 alert("'From Time' should not be equal to 'To Time'.");
				 return false;
				 
			 }
			
			 else{
			 var res = fromTime.match(/^(0?[1-9]|1[012])(:[0-5]\d) [APap][mM]$/);
			 var res2 = toTime.match(/^(0?[1-9]|1[012])(:[0-5]\d) [APap][mM]$/);
			 if( !res && !res2){
				 alert("Please select proper From Time and proper To time");
				 return false;
			 }
			 else if(!res){
				 alert("Please Select Proper From Time");
				 return false;
			 }
			
			 else if( !res2 ){
				 alert("Please Select Proper To Time");
				 return false;
			 }
			 }
				 }
			 if( fromTime != "" || toTime != "" )
			 {
		
		 var res = fromTime.match(/^(0?[1-9]|1[012])(:[0-5]\d) [APap][mM]$/);
		 var res2 = toTime.match(/^(0?[1-9]|1[012])(:[0-5]\d) [APap][mM]$/);
		 if( !res && !res2){
			 alert("Please select proper From Time and proper To Time");
			 return false;
		 }
		 else if(!res){
			 alert("Please Select Proper From Time");
			 return false;
		 }
		
		 else if( !res2 ){
			 alert("Please Select Proper To Time");
			 return false;
		 }
		 }
			 if( des == ""){
				 alert("Please fill the description field");
				 return false;
			 }
			 
			});
		
			$('[name="status"]').attr("disabled", true);
			setTimeout(function () {
			    $(".k-edit-field .k-input").first().focus();
			}, 1000);
			 $(".k-edit-field").each(function () {
		         $(this).find("#temPID").parent().remove();
		      });
			 
			 $(".k-i-close").click(function () {
				//confirm("hiiiiiiiiiii");
				/*  var grid = $("#trackGrid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh(); */
				  
				 
				 
			 });
			
			 securityCheckForActions("./timeandattendence/patroltarcks/createButton");
		});
		
		function edit(e) {	
			 /* uniqunesss for track name
			  var accountNo = $('input[name="accountNo"]').val();
			   $.each(accountNoArray, function(idx1, elem1) {
			    if(elem1 == accountNo)
			    {
			     accountNoArray.splice(idx1, 1);
			    } 
			   }); */
			$(".k-edit-field").each(function () {
		         $(this).find("#temPID").parent().remove();
		      });
			
			   securityCheckForActions("./timeandattendence/patroltracks/updateButton");
			   
		 					/* if($("#trackGrid").data("kendoGrid").dataSource.filter()){
		 				          //$("#grid").data("kendoGrid").dataSource.filter({});
		 				          $("form.k-filter-menu button[type='reset']").slice().trigger("click");
		 				        var grid = $("#trackGrid").data("kendoGrid");
		 				        grid.dataSource.read();
		 				        grid.refresh();
		 				            } */
		 					$(".k-window-title").text("Edit Patrol Track");
		 					$('[name="ptName"]').attr("disabled", true);
		 					$('label[for="status"]').closest('.k-edit-label').hide();
		 					$('div[data-container-for="status"]').hide(); 
		 					
		 					//var RE = "/[0-9]{1,2}:[0-9]{1,2}?[ ](A|P)M $/";
		 				    test1 =="EditPatrolTrack";
		 					//Selecting Grid
		 					var gview = $("#trackGrid").data("kendoGrid");
		 					  //Getting selected item
		 					var selectedItem = gview.dataItem(gview.select());
		 					  //accessing selected rows data 
		 					flagUserId  = selectedItem.ptId;
		 					  
		 					$(".k-grid-Activate" + flagUserId).hide();
		 					
		 					 $(".k-grid-update").click(function () {
		 						
		 						 /* var firstItem = $('#trackGrid').data().kendoGrid.dataSource.data()[0]; */
		 						 var fromTime = selectedItem.validTimeFrom;
		 						 var toTime = selectedItem.validTimeTo;
		 						 if( fromTime == "" && toTime == "" )
		 							 {
		 							 alert("Please select From Time and To time");
		 							 return false;
		 							 
		 							 }
		 						 if( fromTime != "" && toTime != "" )
		 							 {
		 						 if( fromTime == toTime ){
		 							 alert("'From Time' should not be equal to 'To Time'.");
		 							 return false;
		 							 
		 						 }
		 						
		 						 else{
		 						 var res = fromTime.match(/^(0?[1-9]|1[012])(:[0-5]\d) [APap][mM]$/);
		 						 var res2 = toTime.match(/^(0?[1-9]|1[012])(:[0-5]\d) [APap][mM]$/);
		 						 if( !res && !res2){
		 							 alert("Please select proper From Time and proper To time");
		 							 return false;
		 						 }
		 						 else if(!res){
		 							 alert("Please Select Proper From Time");
		 							 return false;
		 						 }
		 						
		 						 else if( !res2 ){
		 							 alert("Please Select Proper To Time");
		 							 return false;
		 						 }
		 						 }
		 							 }
		 						 if( fromTime != "" || toTime != "" )
		 						 {
		 					
		 					 var res = fromTime.match(/^(0?[1-9]|1[012])(:[0-5]\d) [APap][mM]$/);
		 					 var res2 = toTime.match(/^(0?[1-9]|1[012])(:[0-5]\d) [APap][mM]$/);
		 					 if( !res && !res2){
		 						 alert("Please select proper From Time and proper To time");
		 						 return false;
		 					 }
		 					 else if(!res){
		 						 alert("Please Select Proper From Time");
		 						 return false;
		 					 }
		 					
		 					 else if( !res2 ){
		 						 alert("Please Select Proper To Time");
		 						 return false;
		 					 }
		 					 }
		 					});		 					 
		 			
		} 
		
		$("#trackGrid").on("click", ".k-grid-delete", function() {
			
			 securityCheckForActions("./timeandattendence/patroltarcks/deleteButton");
		});
		
		 $("#trackGrid").on("click", "#temPID", function(e) {
			 
	   			var button = $(this), enable = button.text() == "Activate";
	   			var widget = $("#trackGrid").data("kendoGrid");
	   			var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));

	   			result=securityCheckForActionsForStatus("./timeandattendence/patroltarcks/statusButton");   
	 		   if(result=="success"){
	   						if (enable) {
	   							$.ajax({
	   								type : "POST",
	   								dataType:"text",
	   								url : "./timeAndAttendanceManagement/patroltrackStatus/" + dataItem.id + "/activate",
	   								dataType : 'text',
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
	   									button.text('Deactivate');
	   									$('#trackGrid').data('kendoGrid').dataSource.read();
	   								}
	   							});
	   						} else {
	   							$.ajax({
	   								type : "POST",
	   								dataType:"text",
	   								url : "./timeAndAttendanceManagement/patroltrackStatus/" + dataItem.id + "/deactivate",
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
	   									$('#trackGrid').data('kendoGrid').dataSource.read();
	   								}
	   							});
	   						}
	 		   }
	   		});	
		 
			
			//register custom validation rules
			(function ($, kendo) 
				{   	  
			    $.extend(true, kendo.ui.validator, 
			    {
			         rules: 
			         { // custom rules          	
			        	 patrolTrackNamevalidation: function (input, params) 
			             {               	 
			                 //check for the name attribute 
			                 if (input.filter("[name='ptName']").length && input.val()) 
			                 {
			                	 patroltrackName = input.val();
			                	$.each(res, function( index, value ) 
								{	
			          				if((patroltrackName == value))
									{
										flag = input.val();								
			          				}  
			          			});  
			                	return /^[a-zA-Z]+[ _\-a-zA-Z0-9_\-]*[a-zA-Z0-9]$/.test(input.val());
			                	/* return /^[a-zA-Z]*[ a-zA-Z][_]{0,1}[a-zA-Z]*[^_]$/.test(input.val()) */
			                 }        
			                 return true;
			             },
					        
			             patrolTrackNameUniqueness : function(input,params) {
								//check for the name attribute 
								if (input.filter("[name='ptName']").length && input.val()) {
									enterdTrack = input.val().toUpperCase();
									for(var i = 0; i<res1.length; i++) {
										if ((enterdTrack == res1[i].toUpperCase()) && (enterdTrack.length == res1[i].length) ) {
											//flag = enterdService;
											return false;
									
									}
									}
									/* $.each(res1,function(ind, val) {
												if ((enterdTrack == val.toUpperCase()) && (enterdTrack.length == val.length)) {
												flag = enterdTrack;
												return false;
										}
									}); */
								}
								return true;
						},
						ptNameUniqueness : function(input) {
								if (input.filter("[name='ptName']").length && input.val() && flag != "") {
									flag = "";
									return false;
								}
								return true;
						},  
			         /*   patrolTrackNameUniqueness: function(input, params) 
				  		{   
			        	   
			        		if(input.filter("[name='ptName']").length && input.val()) 
					  		{
			        			var flag = true;
			        			$.each(trackArray, function(idx1, elem1) {
									if(elem1 == input.val())
									{
										flag = false;
									}	
								});
								return flag;
							}
							return true;
						}
			        	 */		
								
			         }, 
			         messages: 
			         {
						//custom rules messages
			        	 //patrolTrackNamevalidation: " PatrolTrack Name allows characters,atmost one underscore(_) and one number and should not allow special charecters(except underscore) and should not end with underscore(_)",
			        	ptNameUniqueness: " PatrolTrack Name already exists, please try with some other name ",
			        	 patrolTrackNameUniqueness: " PatrolTrack Name already exists, please try with some other name ",
			        	patrolTrackNamevalidation: " PatrolTrack Name can not allow special symbols except underscore(_) and hyphen(-) ",
			         }
			    });
			    
			})(jQuery, kendo);
			  //End Of Validation
			  
			 function descriptionEditor(container, options) 
	{
        $('<textarea data-text-field="description" data-value-field="description" data-bind="value:' + options.field + '" style="width: 148px; height: 46px;" placeholder=" " required validationmessage="This field is Required"/>')
             .appendTo(container);
       /*  $('<span class="k-invalid-msg" data-for="ssss"></span>').appendTo(container);  */
	}
			
			  
		function fromTimeEditor(container, options) {
		    $('<input id="fromTimePicker" data-text-field="' + options.field + '" data-value-field="' + options.field + '" data-bind="value:' + 
		    		options.field + '" data-format="' + ["hh:mm:tt"] + '"/>')
		            .appendTo(container)
		            .kendoTimePicker({
			            	 name:("validTimeFrom"),
			            	value:"${now}" 
			            	
		            });
		   /*  $('<span class="k-invalid-msg" data-for="From Time"></span>').appendTo(container);  */
		} 
		

		function toTimeEditor(container, options) {
		    $('<input name="To Time" data-text-field="' + options.field + '" id="toTimePicker" data-value-field="' + options.field + '" data-bind="value:' + 
		    		options.field + '" data-format="' + ["hh:mm:tt"] + '"/>')
		            .appendTo(container)
		            .kendoTimePicker({
		            	optionLabel : "Select Status",
		            	 name:("validTimeFrom"),
		            	 value:"${now}" 
		            });
		  /*   $('<span class="k-invalid-msg" data-for="To Time"></span>').appendTo(container); */
		} 
		function statusDropDownEditor(container, options) {
			var data = ["Active" , "inactive"];
			$(
					'<input data-text-field="" data-value-field="" data-bind="value:' + options.field + '"/>')
					.appendTo(container).kendoDropDownList({
						optionLabel : "Select Status",
						defaultValue : false,
						sortable : true,
						dataSource :data
						
					});
		} 
		
		function OnRequestStart(e){
			$('.k-grid-update').hide();
	        $('.k-edit-buttons')
	                .append(
	                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
	        $('.k-grid-cancel').hide();
		}
	 	function onRequestEnd(e) {
			if (typeof e.response != 'undefined'){
				if (e.response.status == "EXCEPTION") {
					errorInfo = "";
					errorInfo = e.response.result.exception;
					if (e.type == "destroy") {
						$("#alertsBox").html("");
							$("#alertsBox").html("Error: Deleting the PatrolTrack record\n\n : " + errorInfo);
									$("#alertsBox").dialog({
										modal : true,
										buttons : {
											"Close" : function() {
												$(this).dialog("close");
											}
										}
									});
					}
					var grid = $("#trackGrid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
				}
				
				
				else if (e.response.status == "FAIL") {
						errorInfo = "";

						for (i = 0; i < e.response.result.length; i++) {
							errorInfo += (i + 1) + ". "
									+ e.response.result[i].defaultMessage + "<br>";

						}
					if (e.type == "create") {
						$("#alertsBox").html("");
						$("#alertsBox").html("Error: Creating the PatrolTrack record\n\n" + errorInfo);
								$("#alertsBox").dialog({
									modal : true,
									buttons : {
										"Close" : function() {
											$(this).dialog("close");
										}
									}
								});
						
					}
					var grid = $("#trackGrid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
					//return false;
				}
				 else if (e.response.status == "FAILUPDATE") {
						errorInfo = "";

						for (i = 0; i < e.response.result.length; i++) {
							errorInfo += (i + 1) + ". "
									+ e.response.result[i].defaultMessage + "<br>";

						}
					if (e.type == "update") {
						$("#alertsBox").html("");
						$("#alertsBox").html("Error: Updating the PatrolTrack record\n\n" + errorInfo);
								$("#alertsBox").dialog({
									modal : true,
									buttons : {
										"Close" : function() {
											$(this).dialog("close");
										}
									}
								});
						
					}
					var grid = $("#trackGrid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
				}
				else if (e.response.status == "WRONGFORMAT") {
					errorInfo = "";
					errorInfo = e.response.result.wrongFormate;
					if (e.type == "create") {
						$("#alertsBox").html("");
						$("#alertsBox").html("Error: Creating the PatrolTrack record\n\n : " + errorInfo);
								$("#alertsBox").dialog({
									modal : true,
									buttons : {
										"Close" : function() {
											$(this).dialog("close");
										}
									}
								});
						
					}
					var grid = $("#trackGrid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
				}
				else if (e.response.status == "WRONGTOTIME") {
					errorInfo = "";
					errorInfo = e.response.result.wrongToTime;
					if (e.type == "create") {
						$("#alertsBox").html("");
						$("#alertsBox").html("Error: Creating the PatrolTrack record\n\n : " + errorInfo);
								$("#alertsBox").dialog({
									modal : true,
									buttons : {
										"Close" : function() {
											$(this).dialog("close");
										}
									}
								});
						
					}
					var grid = $("#trackGrid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
				}
				else if (e.response.status == "WRONGFROMTIME") {
					errorInfo = "";
					errorInfo = e.response.result.wrongFromTime;
					if (e.type == "create") {
						$("#alertsBox").html("");
						$("#alertsBox").html("Error: Creating the PatrolTrack record\n\n : " + errorInfo);
								$("#alertsBox").dialog({
									modal : true,
									buttons : {
										"Close" : function() {
											$(this).dialog("close");
										}
									}
								});
						
					}
					var grid = $("#trackGrid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
				}
				else if (e.response.status == "DUPLICATE") {
					errorInfo = "";
					errorInfo = e.response.result.duplicate;
					if (e.type == "create") {
						$("#alertsBox").html("");
						$("#alertsBox").html("Error: Creating the PatrolTrack record\n\n : " + errorInfo);
								$("#alertsBox").dialog({
									modal : true,
									buttons : {
										"Close" : function() {
											$(this).dialog("close");
										}
									}
								});
					}
					var grid = $("#trackGrid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
				}
				else if (e.response.status == "SAMETIME") {
					errorInfo = "";
					errorInfo = e.response.result.sameTime;
					if (e.type == "create") {
						$("#alertsBox").html("");
						$("#alertsBox").html("Error: Creating The PatrolTrack record\n\n : " + errorInfo);
								$("#alertsBox").dialog({
									modal : true,
									buttons : {
										"Close" : function() {
											$(this).dialog("close");
										}
									}
								});
					}
					var grid = $("#trackGrid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
				}
				
				else if (e.response.status == "SAVEEXCEPTION") {
					errorInfo = "";
					errorInfo = e.response.result.saveException;
					if (e.type == "create") {
						$("#alertsBox").html("");
						$("#alertsBox").html("Error: Creating the PatrolTrack record\n\n : " + errorInfo);
								$("#alertsBox").dialog({
									modal : true,
									buttons : {
										"Close" : function() {
											$(this).dialog("close");
										}
									}
								});
						
					}
					var grid = $("#trackGrid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
				}
				else if (e.response.status == "UPDATEEXCEPTION") {
					errorInfo = "";
					errorInfo = e.response.result.updateException;
					if (e.type == "update") {
						$("#alertsBox").html("");
						$("#alertsBox").html("Error: Updating the PatrolTrack record\n\n : " + errorInfo);
								$("#alertsBox").dialog({
									modal : true,
									buttons : {
										"Close" : function() {
											$(this).dialog("close");
										}
									}
								});
						
					}
					var grid = $("#trackGrid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
				}
				else if (e.response.status == "INVALIDFORMAT") {
					errorInfo = "";
					errorInfo = e.response.result.wrongFormate;
					if (e.type == "update") {
						$("#alertsBox").html("");
						$("#alertsBox").html("Error: Updating the PatrolTrack record\n\n : " + errorInfo);
								$("#alertsBox").dialog({
									modal : true,
									buttons : {
										"Close" : function() {
											$(this).dialog("close");
										}
									}
								});
						
					}
					var grid = $("#trackGrid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
				}
				else if (e.response.status == "FROMTIME") {
					errorInfo = "";
					errorInfo = e.response.result.wrongFromTime;
					if (e.type == "update") {
						$("#alertsBox").html("");
						$("#alertsBox").html("Error: Updating the PatrolTrack record\n\n : " + errorInfo);
								$("#alertsBox").dialog({
									modal : true,
									buttons : {
										"Close" : function() {
											$(this).dialog("close");
										}
									}
								});
						
					}
					var grid = $("#trackGrid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
				}
				else if (e.response.status == "SAMETIMEE") {
					errorInfo = "";
					errorInfo = e.response.result.sameTime;
					if (e.type == "update") {
						$("#alertsBox").html("");
						$("#alertsBox").html("Error: Updating The PatrolTrack record\n\n : " + errorInfo);
								$("#alertsBox").dialog({
									modal : true,
									buttons : {
										"Close" : function() {
											$(this).dialog("close");
										}
									}
								});
					}
					var grid = $("#trackGrid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
				}
				else if (e.response.status == "TOTIME") {
					errorInfo = "";
					errorInfo = e.response.result.wrongToTime;
					if (e.type == "update") {
						$("#alertsBox").html("");
						$("#alertsBox").html("Error: Updating the PatrolTrack record\n\n : " + errorInfo);
								$("#alertsBox").dialog({
									modal : true,
									buttons : {
										"Close" : function() {
											$(this).dialog("close");
										}
									}
								});
						
					}
					var grid = $("#trackGrid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
				}
						
				else if (e.response.status == "DELETEERROR") {
					errorInfo = "";
					errorInfo = e.response.result.deleteerror;
					if (e.type == "destroy") {
						$("#alertsBox").html("");
						$("#alertsBox").html("Error: Deleting the PatrolTrack record\n\n : " + errorInfo);
								$("#alertsBox").dialog({
									modal : true,
									buttons : {
										"Close" : function() {
											$(this).dialog("close");
										}
									}
								});
						
					}
					var grid = $("#trackGrid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
				}
				
				else if (e.type == "destroy") {
					$("#alertsBox").html("");
					$("#alertsBox").html("PatrolTrack record deleted successfully");
							$("#alertsBox").dialog({
								modal : true,
								buttons : {
									"Close" : function() {
										$(this).dialog("close");
									}
								}
							});
					var grid = $("#trackGrid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
			   }
				
			   else if (e.type == "create") {
				   $("#alertsBox").html("");
					$("#alertsBox").html("PatrolTrack record created successfully");
							$("#alertsBox").dialog({
								modal : true,
								buttons : {
									"Close" : function() {
										$(this).dialog("close");
									}
								}
							});
					
					var grid = $("#trackGrid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
			  }
			  

			  else if (e.type == "update") {
				  $("#alertsBox").html("");
					$("#alertsBox").html("PatrolTrack record updated successfully");
							$("#alertsBox").dialog({
								modal : true,
								buttons : {
									"Close" : function() {
										$(this).dialog("close");
									}
								}
							});
					$(".k-grid-Activate" + flagUserId).show();
					
					var grid = $("#trackGrid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
			 }
			}
		} 
		
		/* //custom validation
		(function($, kendo) {
			$
					.extend(
							true,
							kendo.ui.validator,
							{
								rules : { // custom rules   
									fromTimeValidation: function (input, params) 
						             {
					                     if (input.filter("[name = 'validTimeFrom']").length && input.val() != "") 
					                     {                          
					                         var selectedDate = input.val();
					                         //var todaysDate = $.datepicker.formatDate('dd/mm/yy', new Date());
					                         var flagDate = false;

					                         if ($.timepicker.parseTime('[0-9]{1,2}:[0-9]{1,2}?[ ](A|P)M(?!^[A-Za-z][0-9]$)', selectedDate) )
					                         {
					                        	 //propertyAquiredDateFrom = selectedDate;
					                                flagDate = true;
					                         }
					                         return flagDate;
					                     }
					                     return true;
					                 },
											
								},
								messages : { //custom rules messages
									fromTimeValidation : "This field cannot be left",
									toTimeValidation : "This field cannot be left" 
								}
							});

		})(jQuery, kendo);
		  */
</script>
