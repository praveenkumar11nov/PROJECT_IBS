<%@include file="/common/taglibs.jsp"%>


	<c:url value="/PatrolTrackPoints/read" var="readUrl" />
	<c:url value="/PatrolTrackPoints/create" var="createUrl" />
	<c:url value="/PatrolTrackPoints/update" var="updateUrl" />
		<c:url value="/PatrolTrackPoints/delete" var="destroyUrl" />
	 	
		<c:url value="/PatrolTrackPoints/getRepositoryData" var="getRepositoryDataUrl" />
		<c:url value="/PatrolTrackPoints/getPatroltrackName" var="getPatroltrackNameUrl" />
		
		<c:url value="/PatrolTrackPoints/getPatrolTrackNamesList" var="getPatroltrackNameListUrl" />
		<c:url value="/PatrolTrackPoints/getAccessRepositoryNamesList" var="getRepositoryDataListUrl" />
		<c:url value="/PatrolTrackPoints/getSequenceList" var="getSequenceListUrl" />
		<c:url value="/PatrolTrackPoints/getTimeIntervalList" var="getTimeIntervalListUrl" />
		<c:url value="/PatrolTrackPoints/getStatusList" var="getStatusListUrl" />
		
	<kendo:grid name="pointGrid" pageable="true"
		resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true"
		filterable="true" groupable="true" >
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" ></kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
		 <kendo:grid-filterable-operators>
		  	<kendo:grid-filterable-operators-string eq="Is equal to"/>
		 </kendo:grid-filterable-operators>
			
		</kendo:grid-filterable>
		
		  <kendo:grid-editable mode="popup"
			confirmation="Are you sure you want to remove this Patrol Track Point?" />
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Patrol Track Point" />
			<kendo:grid-toolbarItem name="Clear_Filter" text="Clear Filter"/>
		</kendo:grid-toolbar>

		<kendo:grid-columns>
		
			<%--  <kendo:grid-column title="PatrolTrack Name" field="patrolTracks" editor="patroltrackDropDownEditor"  template="#=patrolTracks.ptName#"
				filterable="false" width="130px" > --%>
				<kendo:grid-column title="Patrol Track Name *" field="ptName" 
				editor="patroltrackDropDownEditor" 
				width="120px">
				<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function patrolTrackFilter(element) {
								element.kendoAutoComplete({
									dataSource: {
										transport: {
											read: "${getPatroltrackNameListUrl}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	    
				</kendo:grid-column>
				<kendo:grid-column hidden="true"/>
			<kendo:grid-column title="Access Points *" field="arName" 
				editor="repositoryDropDownEditor" 
				width="140px">
			 <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function accessRepositoryFilter(element) {
								element.kendoAutoComplete({
									dataSource: {
										transport: {
											read: "${getRepositoryDataListUrl}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	  
			</kendo:grid-column>
			<kendo:grid-column hidden="true"/>
			<kendo:grid-column title="Sequence *" field="ptpSequence" width="100px" filterable="true" >
			<%-- <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function sequenceFilter(element) {
								element.kendoAutoComplete({
									dataSource: {
										transport: {
											read: "${getSequenceListUrl}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	 --%>    
			</kendo:grid-column>
			<kendo:grid-column hidden="true"/>
			<kendo:grid-column title="Time Interval *" field="ptpVisitInterval" format="{0:HH:mm}" editor="intervalTimeEditor" width="100px" >
				<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function timeIntevalFilter(element) {
								element.kendoAutoComplete({
									dataSource: {
										transport: {
											read: "${getTimeIntervalListUrl}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	    
				</kendo:grid-column>
			<kendo:grid-column title="Status" field="status" filterable="false"
				width="100px" >
				<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function statusFilter(element) {
								element.kendoAutoComplete({
									dataSource: {
										transport: {
											read: "${getStatusListUrl}"
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
					<kendo:grid-column-commandItem name="destroy" />
				</kendo:grid-column-command>
			</kendo:grid-column>
			<kendo:grid-column title=""
				template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Activate#=data.ptpId#'>#= data.status == 'Active' ? 'De-activate' : 'Activate' #</a>"
				width="100px" />
		</kendo:grid-columns>
		<kendo:dataSource requestEnd="onRequestEnd" requestStart="onRequestStart">
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
			 <kendo:dataSource-schema>
				<kendo:dataSource-schema-model id="ptpId">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="ptpId" editable="false" />
						<kendo:dataSource-schema-model-field name="ptName" editable="true">
						 <kendo:dataSource-schema-model-field-validation  />
						 </kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="arName">
						<kendo:dataSource-schema-model-field-validation  />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="ptpSequence" type="number">
						<kendo:dataSource-schema-model-field-validation min="1" />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="ptpVisitInterval">
						<kendo:dataSource-schema-model-field-validation />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="status" type="string">
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
<div id="alertsBox" title="Alert"></div>
	<script>
	var flag = "";
	  var intervalTime = "";
	  var des = "";
	  var res = [];
	  var flagUserId = "";
	  
	$("#pointGrid").on("click", ".k-grid-Clear_Filter", function(){
	    //custom actions
	    $("form.k-filter-menu button[type='reset']").slice().trigger("click");
		var grid = $("#pointGrid").data("kendoGrid");
		grid.dataSource.read();
		grid.refresh();
	});
	
		$("#pointGrid").on("click", ".k-grid-add", function() {
			
			if($("#pointGrid").data("kendoGrid").dataSource.filter()){
		          //$("#grid").data("kendoGrid").dataSource.filter({});
		           //alert("Clearing Filter");
		          $("form.k-filter-menu button[type='reset']").slice().trigger("click");
		        var grid = $("#pointGrid").data("kendoGrid");
		        grid.dataSource.read();
		        grid.refresh();
		            }
			   
			$(".k-window-title").text("Add Patrol Track Point");
			$(".k-grid-update").text("Save");
			$(".k-grid-Activate").hide();
			$('[name="status"]').attr("disabled", true);
			 $('.k-edit-field .k-input').first().focus();
			 $('label[for="status"]').closest('.k-edit-label').hide();
				$('div[data-container-for="status"]').hide(); 
			 
			 $(".k-edit-field").each(function () {
		         $(this).find("#temPID").parent().remove();
		      });
			
			 
			 $(".k-grid-update").click(function () {
				 var firstItem = $('#pointGrid').data().kendoGrid.dataSource.data()[0];
				 var ptTracks = $("#ptTracks").data("kendoDropDownList");
				 var accessPoint = $("#accessPoints").data("kendoDropDownList");
				 var seq = firstItem.get('ptpSequence');
				 var timePicker = $("#timePicker").data("kendoTimePicker");
				 if( ptTracks.select() == 0 ){
					 alert("Please Select Proper Patrol Track");
	     				return false;
				 }
				 if( accessPoint.select() == 0 ){
					 alert("Please Select Proper Access Point");
	     				return false;
				 }
				 if( seq == 0 || seq == null){
					 alert("Please enter sequence");
	     				return false;
				 }
				 if( timePicker.value() == null ){
					 alert("Please Select Proper Interval Time");
	     				return false;
				 }
				 
			});
			 
			 securityCheckForActions("./timeandattendence/patroltarckpoints/createButton");    

		});
		
		function edit(e) {
			$(".k-edit-field").each(function () {
			         $(this).find("#temPID").parent().remove();
			      });
			
			securityCheckForActions("./timeandattendence/patroltrackpoints/updateButton");
	       					/* if($("#pointGrid").data("kendoGrid").dataSource.filter()){
	       			          //$("#grid").data("kendoGrid").dataSource.filter({});
	       			          $("form.k-filter-menu button[type='reset']").slice().trigger("click");
	       			        var grid = $("#pointGrid").data("kendoGrid");
	       			        grid.dataSource.read();
	       			        grid.refresh();
	       			            } */
	       				
	       				$(".k-window-title").text("Edit Patrol Track Point");
	       				$('[name="ptName"]').attr("disabled", true);
	       				$(".k-grid-Activate").hide();
	       				$('label[for="status"]').closest('.k-edit-label').hide();
	       				$('div[data-container-for="status"]').hide(); 
	       				var gview = $("#pointGrid").data("kendoGrid");
	 					var selectedItem = gview.dataItem(gview.select());
	       				$(".k-grid-update").click(function () {
	       					
	       					 var ptTracks = $("#ptTracks").data("kendoDropDownList");
	       					 var accessPoint = $("#accessPoints").data("kendoDropDownList");
	       					 var seq = selectedItem.ptpSequence;
	       					 var timePicker = $("#timePicker").data("kendoTimePicker");
	       					 if( ptTracks.select() == 0 ){
	       						 alert("Error: This 'Patrol Track' is deactivated..Please activate Patrol Track befor update");
	       		     				return false;
	       					 }
	       					 if( accessPoint.select() == 0 ){
	       						 alert("Please Select Proper Access Point");
	       		     				return false;
	       					 }
	       					 if( seq == null ){
	       						 alert("Please enter sequence");
	       		     				return false;
	       					 }
	       					 if( timePicker.value() == null ){
	       						 alert("Please Select Proper Interval Time");
	       		     				return false;
	       					 }
	       				});
	       				
	       				 
	       				 var gview = $("#pointGrid").data("kendoGrid");
	       				  //Getting selected item
	       				  var selectedItem = gview.dataItem(gview.select());
	       				  //accessing selected rows data 
	       				 flagUserId  = selectedItem.ptpId;
	       				  
	       				  $(".k-grid-Activate" + flagUserId).hide();
	       					
	       		
		}
		
		$("#pointGrid").on("click", ".k-grid-delete", function() {
			securityCheckForActions("./timeandattendence/patroltarckpoints/deleteButton");
		});
		
		 $("#pointGrid").on("click", "#temPID", function(e) {
			 
	   			var button = $(this), enable = button.text() == "Activate";
	   			var widget = $("#pointGrid").data("kendoGrid");
	   			var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
	   			result=securityCheckForActionsForStatus("./timeandattendence/patroltarckpoints/statusButton");   
	 		   if(result=="success"){
	   						if (enable) {
	   							$.ajax({
	   								type : "POST",
	   								dataType:"text",
	   								url : "./timeAndAttendanceManagement/patroltarckPointStatus/" + dataItem.id + "/activate",
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
	   									$('#pointGrid').data('kendoGrid').dataSource.read();
	   								}
	   							});
	   						} else {
	   							$.ajax({
	   								type : "POST",
	   								dataType:"text",
	   								url : "./timeAndAttendanceManagement/patroltarckPointStatus/" + dataItem.id + "/deactivate",
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
	   									$('#pointGrid').data('kendoGrid').dataSource.read();
	   								}
	   							});
	   						}
	   					}
	   		});	
		 
		function intervalTimeEditor(container, options) {
			 
		    $('<input id="timePicker" data-text-field="' + options.field + '" data-value-field="' + options.field + '" data-bind="value:' + 
		    		options.field + '" data-format="' + ["HH:mm"] + '"/>')
		            .appendTo(container)
		            .kendoTimePicker({
		            	 placeholder:"Select "
		            });
		   /*  $('<span class="k-invalid-msg" data-for="Interval Time"></span>').appendTo(container); */
		}
		
		function patroltrackDropDownEditor(container, options) {
			
			
			if (options.model.ptName == '') {
				$(
						'<input id="ptTracks" data-text-field="ptName" data-value-field="ptName" data-bind="value:' + options.field + '"/>')
						.appendTo(container).kendoDropDownList({
							optionLabel : "Select",
							dataSource : {
								transport : {

									read : "${getPatroltrackNameUrl}"

								}
							}

						});
				/* $('<span class="k-invalid-msg" data-for="TrackName"></span>').appendTo(container); */
			}
			else
				{
				$(
						'<input id="ptTracks" data-text-field="ptName" disabled data-value-field="ptName" data-bind="value:' + options.field + '"/>')
						.appendTo(container).kendoDropDownList({
							optionLabel : "Select",
							dataSource : {
								transport : {

									read : "${getPatroltrackNameUrl}"

								}
							}

						});
				/* $('<span class="k-invalid-msg" data-for="TrackName"></span>').appendTo(container); */
				
				}
			
			
			
			
		}
		function repositoryDropDownEditor(container, options) {
			$(
					'<input id="accessPoints" data-text-field="arName"  data-value-field="arName" data-bind="value:' + options.field + '"/>')
					.appendTo(container)
					 .kendoDropDownList({
						 optionLabel : "Select",
						dataSource : {
							transport : {
								read : "${getRepositoryDataUrl}"
							}
						}
					});
			/*  $('<span class="k-invalid-msg" data-for="AccesPoint"></span>').appendTo(container); */ 
		}

			function onRequestStart(e){
				$('.k-grid-update').hide();
		        $('.k-edit-buttons')
		                .append(
		                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
		        $('.k-grid-cancel').hide();
		        
				/* if (e.type == "create"){
					var gridStoreGoodsReturn = $("#pointGrid").data("kendoGrid");
					gridStoreGoodsReturn.cancelRow();		
				} */	
			}
			function onRequestEnd(e) {
				if (typeof e.response != 'undefined')
			{
					 if (e.response.status == "FAIL") 
						{

							errorInfo = "";
							for (i = 0; i < e.response.result.length; i++) {
								errorInfo += (i + 1) + ". "
										+ e.response.result[i].defaultMessage + "<br>";

							}

							if (e.type == "create") {
								$("#alertsBox").html("");
		 		 					$("#alertsBox").html("Error: " + errorInfo);
		 							$("#alertsBox").dialog({
		 								modal : true,
		 								buttons : {
		 									"Close" : function() {
		 										$(this).dialog("close");
		 									}
		 								}
		 							});

							}
							var grid = $("#pointGrid").data("kendoGrid");
							grid.dataSource.read();
							grid.refresh();
						}
					 if (e.response.status == "FAIL") 
						{
							errorInfo = "";
							for (i = 0; i < e.response.result.length; i++) {
								errorInfo += (i + 1) + ". "
										+ e.response.result[i].defaultMessage + "<br>";

							}

							if (e.type == "update") {
								$("#alertsBox").html("");
		 		 					$("#alertsBox").html("Error: " + errorInfo);
		 							$("#alertsBox").dialog({
		 								modal : true,
		 								buttons : {
		 									"Close" : function() {
		 										$(this).dialog("close");
		 									}
		 								}
		 							});

							}
							var grid = $("#pointGrid").data("kendoGrid");
							grid.dataSource.read();
							grid.refresh();
						}
					 else if (e.response.status == "INVALID") {

							errorInfo = "";
							errorInfo = e.response.result.invalid;

							if (e.type == "create") {
								$("#alertsBox").html("");
	 		 					$("#alertsBox").html("Error: Creating the Patrol Track Point record\n\n : " + errorInfo);
	 							$("#alertsBox").dialog({
	 								modal : true,
	 								buttons : {
	 									"Close" : function() {
	 										$(this).dialog("close");
	 									}
	 								}
	 							});
								
							}
							
							var grid = $("#pointGrid").data("kendoGrid");
							grid.dataSource.read();
							grid.refresh();
							return false;
						}
					 else if (e.response.status == "UPDATEINVALID") {

							errorInfo = "";
							errorInfo = e.response.result.updateinvalid;

							if (e.type == "update") {
								$("#alertsBox").html("");
	 		 					$("#alertsBox").html("Error: Creating the Patrol Track Point record\n\n : " + errorInfo);
	 							$("#alertsBox").dialog({
	 								modal : true,
	 								buttons : {
	 									"Close" : function() {
	 										$(this).dialog("close");
	 									}
	 								}
	 							});
								
							}
							
							var grid = $("#pointGrid").data("kendoGrid");
							grid.dataSource.read();
							grid.refresh();
							return false;
						}
						else if (e.response.status == "WRONGTIMEINTERVAL") {

							errorInfo = "";

							errorInfo = e.response.result.wrongTimeInterval;

							if (e.type == "create") {
								$("#alertsBox").html("");
	 		 					$("#alertsBox").html("Error: " + errorInfo);
	 							$("#alertsBox").dialog({
	 								modal : true,
	 								buttons : {
	 									"Close" : function() {
	 										$(this).dialog("close");
	 									}
	 								}
	 							});
								
							}
							
							var grid = $("#pointGrid").data("kendoGrid");
							grid.dataSource.read();
							grid.refresh();
							return false;
						}
						else if (e.response.status == "ZEROTIMEINTERVAL") {

							errorInfo = "";

							errorInfo = e.response.result.zeroTimeInterval;

							if (e.type == "create") {
								$("#alertsBox").html("");
	 		 					$("#alertsBox").html("Error: " + errorInfo);
	 							$("#alertsBox").dialog({
	 								modal : true,
	 								buttons : {
	 									"Close" : function() {
	 										$(this).dialog("close");
	 									}
	 								}
	 							});
								
							}
							
							var grid = $("#pointGrid").data("kendoGrid");
							grid.dataSource.read();
							grid.refresh();
							return false;
						}
						else if (e.response.status == "ZEROTIME") {

							errorInfo = "";

							errorInfo = e.response.result.zeroTime;

							if (e.type == "update") {
								$("#alertsBox").html("");
	 		 					$("#alertsBox").html("Error: " + errorInfo);
	 							$("#alertsBox").dialog({
	 								modal : true,
	 								buttons : {
	 									"Close" : function() {
	 										$(this).dialog("close");
	 									}
	 								}
	 							});
								
							}
							
							var grid = $("#pointGrid").data("kendoGrid");
							grid.dataSource.read();
							grid.refresh();
							return false;
						}
					 
						 else if (e.response.status == "DELETEPOINTERROR") {
								errorInfo = "";
								errorInfo = e.response.result.deletepointerror;
								if (e.type == "destroy") {
									$("#alertsBox").html("");
									$("#alertsBox").html("Error: Deleting The Patrol Track Point Record\n\n : " + errorInfo);
											$("#alertsBox").dialog({
												modal : true,
												buttons : {
													"Close" : function() {
														$(this).dialog("close");
													}
												}
											});
									
								}
								var grid = $("#pointGrid").data("kendoGrid");
								grid.dataSource.read();
								grid.refresh();
							}
					 else if (e.type == "create") {
						 $("#alertsBox").html("");
		 					$("#alertsBox").html("Patrol Track Point created successfully");
							$("#alertsBox").dialog({
								modal : true,
								buttons : {
									"Close" : function() {
										$(this).dialog("close");
									}
								}
							});
							
							var grid = $("#pointGrid").data("kendoGrid");
							grid.dataSource.read();
							grid.refresh();
					 }

					 else if (e.type == "update") {
						 $("#alertsBox").html("");
		 					$("#alertsBox").html("Patrol Track Point updated successfully");
							$("#alertsBox").dialog({
								modal : true,
								buttons : {
									"Close" : function() {
										$(this).dialog("close");
									}
								}
							});
							
							var grid = $("#pointGrid").data("kendoGrid");
							grid.dataSource.read();
							grid.refresh();
					 }
					 
					 else if (e.type == "destroy") {
						 $("#alertsBox").html("");
		 					$("#alertsBox").html("Patrol Track Point deleted successfully");
							$("#alertsBox").dialog({
								modal : true,
								buttons : {
									"Close" : function() {
										$(this).dialog("close");
									}
								}
							});
							
							var grid = $("#pointGrid").data("kendoGrid");
							grid.dataSource.read();
							grid.refresh();
					 }
		}

			}
		
	 	//custom validation
		(function($, kendo) {
			$
					.extend(
							true,
							kendo.ui.validator,
							{
								rules : { // custom rules   
									sequence : function(input,
											params) {
										if (input.filter("[name='ptpSequence']").length
												&& input.val()) {
											if(input.val().length > 2){
												alert("Sequence must contain only 2 digit numbers.");
												
											}
											return true;
										}
										return true;
									},
											 /* sequenceValidation : function(input) {
													if ((input.filter("[name='ptpSequence']")) && (input.val() == "")) {
														
														return false;
													}
													return true;
												},
												 trackNameValidation : function(input) {
														if ((input.filter("[name='ptName']")) && (input.val() == "Select")) {
															
															return false;
														}
														return true;
													},
													accessPointValidation : function(input) {
															if ((input.filter("[name='arName']")) && (input.val() == "Select")) {
																
																return false;
															}
															return true;
														},  */
														
								},
								/* messages : { //custom rules messages
									
									sequenceValidation : "This field cannot be empty",
									trackNameValidation : "This field cannot be empty",
									accessPointValidation : "This field cannot be empty" 
								} */
							});

		})(jQuery, kendo);
			
	</script>
