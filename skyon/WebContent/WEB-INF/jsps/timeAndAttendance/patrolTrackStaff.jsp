<%@include file="/common/taglibs.jsp"%>

<c:url value="/patrolTrackStaff/read" var="readUrl" />
	<c:url value="/patrolTrackStaff/create" var="createUrl" />
	 <c:url value="/patrolTrackStaff/update" var="updateUrl" />  
	  <c:url value="/patrolTrackStaff/delete" var="destroyUrl" />
 
<c:url value="/PatrolTrackStaff/getAccessCardNo" var="getAccessCardNoUrl" />
<c:url value="/PatrolTrackPoints/getPatroltrackName" var="getPatroltrackNameUrl" />
<c:url value="/PatrolTrackStaff/getPatrolTrackNamesList" var="getPatroltrackNameListUrl" />
<c:url value="/PatrolTrackStaff/getAccessCardsNoList" var="getAccessCardNoListUrl" />
<c:url value="/PatrolTrackStaff/getStaffNames" var="getStaffNameUrl" />
<c:url value="/PatrolTrackStaff/getSupervisorNames" var="getSupervisorNameUrl" />

<c:url value="/PatrolTrackStaff/getStaffNameList" var="getStaffNameListUrl" />
<c:url value="/PatrolTrackStaff/getSupervisorNameList" var="getSupervisorNameListUrl" />


<c:url value="/PatrolTrackStaff/getFromDateList" var="getFromDateListUrl" />
<c:url value="/PatrolTrackStaff/getToDateList" var="getToDateListUrl" />
<c:url value="/PatrolTrackPoints/getStatusList" var="getStatusListUrl" />

		<kendo:grid name="staffGrid" pageable="true"
		resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true"
		filterable="true" groupable="true" >
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" ></kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
		 <kendo:grid-filterable-operators>
		  	<kendo:grid-filterable-operators-string eq="Is equal to"/>
		 </kendo:grid-filterable-operators>
			
		</kendo:grid-filterable>
		  <kendo:grid-editable mode="popup"
			confirmation="Are you sure you want to remove this Patrol Staff?" />
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Patrol Staff" />
			<kendo:grid-toolbarItem name="Clear_Filter" text="Clear Filter"/>
		</kendo:grid-toolbar>

		<kendo:grid-columns>
		<kendo:grid-column title="Patrol Staff Name *" field="firstName" editor="staffNameDropDownEditor" width="130px">
				<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function staffNameFilter(element) {
								element.kendoAutoComplete({
									dataSource: {
										transport: {
											read: "${getStaffNameListUrl}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	    
		</kendo:grid-column>
		<kendo:grid-column hidden="true"/>
		<kendo:grid-column title="Access Card Number *" field="acNo" 
				editor="accessCardsDropDownEditor" 
				width="150px">
				<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function patrolTrackFilter(element) {
								element.kendoAutoComplete({
									dataSource: {
										transport: {
											read: "${getAccessCardNoListUrl}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	    
				</kendo:grid-column>
				<%-- <kendo:grid-column hidden="true"/> --%>
			 <kendo:grid-column title="Patrol Track Name *" field="ptName" 
				editor="patroltrackDropDownEditor" 
				width="130px">
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
				<kendo:grid-column title="Supervisor Name *" field="supervisorName" editor="supervisorNameEditor" width="130px">
				<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function supervisorFilter(element) {
								element.kendoAutoComplete({
									dataSource: {
										transport: {
											read: "${getSupervisorNameListUrl}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	    
		</kendo:grid-column>
		<kendo:grid-column hidden="true"/>
				<kendo:grid-column title="From Date *" field="fromDate" format="{0:dd/MM/yyyy}" 
				width="130px" filterable="true">
				 <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
    					function fromDateFilter(element) {
							element.kendoDatePicker({
								format:"dd/MM/yyyy",
				            	
			            });
				  		}
    					
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	
	    		</kendo:grid-column>
				<kendo:grid-column hidden="true"/>
			<kendo:grid-column title="To Date *" field="toDate" format="{0:dd/MM/yyyy}"
			 width="130px" filterable="true">
			 <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
    					function toDateFilter(element) {
							element.kendoDatePicker({
								format:"dd/MM/yyyy",
				            	
			            });
				  		}
    					
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	
			 </kendo:grid-column>
			<kendo:grid-column title="Status" field="status" filterable="false"
				width="130px" >
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
				template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Activate#=data.ptsId#'>#= data.status == 'Active' ? 'De-activate' : 'Activate' #</a>"
				width="100px" />
		</kendo:grid-columns>
		<kendo:dataSource requestEnd="onRequestEndStaff" requestStart="onRequestStart">
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
			 <kendo:dataSource-schema parse="parse">
				<kendo:dataSource-schema-model id="ptsId">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="ptsId" editable="false" />
						<kendo:dataSource-schema-model-field name="supervisorId" type="number" />
						<kendo:dataSource-schema-model-field name="ptName" type="string">
						 <kendo:dataSource-schema-model-field-validation />
						 </kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="firstName">
						<kendo:dataSource-schema-model-field-validation />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="acNo">
						<kendo:dataSource-schema-model-field-validation />
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
						<kendo:dataSource-schema-model-field name="status" type="string" >
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
	
	<div id="alertsBox" title="Alert"></div>	
	
	<script>
	
	function parse (response) {   
	    $.each(response, function (idx, elem) {
            if (elem.fromDate && typeof elem.fromDate === "string") {
                elem.fromDate = kendo.parseDate(elem.fromDate, "yyyy-MM-ddTHH:mm:ss.fffZ");
            }
            if (elem.toDate && typeof elem.toDate === "string") {
                elem.toDate = kendo.parseDate(elem.toDate, "yyyy-MM-ddTHH:mm:ss.fffZ");
            }
        });
        return response;
	}  
	
	
	  var flagPtsId = "";
	
	$("#staffGrid").on("click", ".k-grid-Clear_Filter", function(){
	    //custom actions
	    $("form.k-filter-menu button[type='reset']").slice().trigger("click");
		var grid = $("#staffGrid").data("kendoGrid");
		grid.dataSource.read();
		grid.refresh();
	});
	
		$("#staffGrid").on("click", ".k-grid-add", function() {
			
		/* 	if($("#staffGrid").data("kendoGrid").dataSource.filter()){
		          //$("#grid").data("kendoGrid").dataSource.filter({});
		          $("form.k-filter-menu button[type='reset']").slice().trigger("click");
		        var grid = $("#staffGrid").data("kendoGrid");
		        grid.dataSource.read();
		        grid.refresh();
		            } */
			
			$(".k-window-title").text("Add Patrol Staff");
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
				 var cards = $("#card").data("kendoComboBox");
				 var supvisors = $("#superID").data("kendoComboBox");

				 var staffs = $("#personId").data("kendoComboBox");
				 var track = $("#tracks").data("kendoDropDownList");
				 
				
				 if( staffs.select() == -1 && cards.select() == -1 && supvisors.select() == -1 && track.select() == 0 ){
					
					 alert("Please properly select all mandatory fields");
					 return false;	
				 }
				 else if( staffs.select() == -1 && cards.select() == -1 && supvisors.select() == -1 ){
						
					 alert("Please Select Proper Staff Name , Access Card And Supervisor Name");
					 return false;	
				 }
				 else if( staffs.select() == -1 && cards.select() == -1 && track.select() == 0 ){
						
					 alert("Please Select Proper Staff Name , Access Card And Patrol Track");
					 return false;	
				 }
				 else if( cards.select() == -1 && supvisors.select() == -1 && track.select() == 0 ){
						
					 alert("Please Select Proper Access Card , Supervisor Name And Patrol Track");
					 return false;	
				 }
				 else if( staffs.select() == -1 && cards.select() == -1 ){
						
					 alert("Please Select Proper Staff Name And Access Card");
					 return false;	
				 }
				 else if( staffs.select() == -1 && supvisors.select() == -1){
						
					 alert("Please Select Proper Staff Name And Supervisor Name");
					 return false;	
				 }
				 else  if( staffs.select() == -1 && track.select() == 0 ){
						
					 alert("Please Select Proper Staff Name And Patrol Track");
					 return false;	
				 }
				 else if( cards.select() == -1 && supvisors.select() == -1 ){
						
					 alert("Please Select Proper Access Card , Supervisor Name");
					 return false;	
				 }
				 else if( cards.select() == -1 && track.select() == 0 ){
						
					 alert("Please Select Proper Access Card And Patrol Track");
					 return false;	
				 }
				 else if(supvisors.select() == -1 && track.select() == 0 ){
						
					 alert("Please Select Proper Supervisor Name And Patrol Track");
					 return false;	
				 }
				 
				 else if (staffs.select() == -1) {
	     	            alert("Please Select Proper Staff Name");
	     				return false;	
	     		   	   }
				 else if (cards.select() == -1) {
     	            alert("Please Select Proper Access Card Number");
     				return false;	
     		   	   }
				 else if (supvisors.select() == -1) {
	     	            alert("Please Select Proper Supervisor Name");
	     				return false;	
	     		   	   }
				 else if (track.select() == 0) {
	     	            alert("Please Select Proper Patrol Track");
	     				return false;	
	     		   	   }
			});
			
			   
			 securityCheckForActions("./timeandattendence/patrolTrackStaff/createButton");  
		});
		function edit(e) {
			$(".k-edit-field").each(function () {
			         $(this).find("#temPID").parent().remove();
			      });
			//for security
			securityCheckForActions("./timeandattendence/patrolTrackStaff/updateButton");
	       					/* if($("#staffGrid").data("kendoGrid").dataSource.filter()){
	       			          //$("#grid").data("kendoGrid").dataSource.filter({});
	       			          $("form.k-filter-menu button[type='reset']").slice().trigger("click");
	       			        var grid = $("#staffGrid").data("kendoGrid");
	       			        grid.dataSource.read();
	       			        grid.refresh();
	       			            } */
	       				
	       				$(".k-window-title").text("Edit Patrol Staff");
	       				$(".k-grid-Activate").hide();
	       				$('[name="ptName"]').attr("disabled", true);
	       				$('[name="supervisorName"]').attr("disabled", true);			

	       				//$('[name="status"]').hide();
	       				//$('label[for=status]').parent().remove();
	       				
	       				$(".k-edit-field").each(function () {
	       			         $(this).find("#temPID").parent().remove();
	       			      });
	       				
	       				$('label[for="status"]').closest('.k-edit-label').hide();
	       				$('div[data-container-for="status"]').hide(); 
	       				//Selecting Grid
	       				var gview = $("#staffGrid").data("kendoGrid");
	       				  //Getting selected item
	       				var selectedItem = gview.dataItem(gview.select());
	       				  //accessing selected rows data 
	       				flagPtsId  = selectedItem.ptsId;
	       				  
	       				$(".k-grid-Activate" + flagPtsId).hide();
	       				
	       				$(".k-grid-update").click(function () {
	       					if((selectedItem.toDate)!=""){
	       			 			   if(selectedItem.toDate<selectedItem.fromDate){
	       				  			   alert("Error : 'To Date' should always be greater than or equal to 'From Date'");
	       				  			   return false; 				   
	       			 			   }
	       			 		   } 
	       					
	       					 var cards = $("#card").data("kendoComboBox");
	       					 var supvisors = $("#superID").data("kendoComboBox");
	       					 var track = $("#tracks").data("kendoDropDownList");
	       					 
	       					if( track.select() == 0 ){
	       						 alert("Error: This 'Patrol Track' is deactivated..Please activate Patrol Track befor update");
	       		     				return false;
	       					 }
	       					if( cards.select() == -1 ){
	       						
	       					 alert("Error: This 'Staff Role' is deactivated..Please activate 'Patrol Role' befor update");
    		     				return false;
	       						
	       					}
	       					 
	       					 else if( cards.select() == -1 && supvisors.select() == -1 && track.select() == 0 ){
	       							
	       						 alert("Please Select Proper Access Card , Supervisor Name And Patrol Track");
	       						 return false;	
	       					 }
	       					 
	       					 else if( cards.select() == -1 && supvisors.select() == -1 ){
	       							
	       						 alert("Please Select Proper Access Card , Supervisor Name");
	       						 return false;	
	       					 }
	       					 else if( cards.select() == -1 && track.select() == 0 ){
	       							
	       						 alert("Please Select Proper Access Card And Patrol Track");
	       						 return false;	
	       					 }
	       					 else if(supvisors.select() == -1 && track.select() == 0 ){
	       							
	       						 alert("Please Select Proper Supervisor Name And Patrol Track");
	       						 return false;	
	       					 }
	       					 
	       					 else if (cards.select() == -1) {
	       	     	            alert("Please Select Proper Access Card Number");
	       	     				return false;	
	       	     		   	   }
	       					 else if (supvisors.select() == 0) {
	       		     	            alert("Please Select Proper Supervisor Name");
	       		     				return false;	
	       		     		   	   }
	       					 else if (track.select() == 0) {
	       		     	            alert("Please Select Proper Patrol Track");
	       		     				return false;	
	       		     		   	   }
	       			});
	       					
		}
		
		$("#staffGrid").on("click", ".k-grid-delete", function() {
			
			securityCheckForActions("./timeandattendence/patrolTrackStaff/deleteButton");
			
		});
	 	
		
		$("#staffGrid").on("click", "#temPID", function(e) {
			 
   			var button = $(this), enable = button.text() == "Activate";
   			var widget = $("#staffGrid").data("kendoGrid");
   			var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));

   			result=securityCheckForActionsForStatus("./timeandattendence/patroltarckpoints/statusButton");   
	 		   if(result=="success"){
   						if (enable) {
   							$.ajax({
   								type : "POST",
   								dataType:"text",
   								url : "./timeAndAttendanceManagement/patroltrackStaffStatus/" + dataItem.id + "/activate",
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
   									$('#staffGrid').data('kendoGrid').dataSource.read();
   								}
   							});
   						} else {
   							$.ajax({
   								type : "POST",
   								dataType:"text",
   								url : "./timeAndAttendanceManagement/patroltrackStaffStatus/" + dataItem.id + "/deactivate",
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
   									$('#staffGrid').data('kendoGrid').dataSource.read();
   								}
   							});
   						}
	 		   }
   		});	
		
		/* 
		function dateEditor(container, options) {
		    $('<input data-text-field="' + options.field + '" data-value-field="' + options.field + '" data-bind="value:' + 
		    		options.field + '" data-format="' + ["dd/MM/yyyy"] + '" required='true'/>')
		    		
		            .appendTo(container)
		            .kendoDatePicker({
		            	placeholder : "Please select to time"
		            });
		}
		 */
		 function supervisorNameEditor(container, options)
		 {
			 if (options.model.supervisorName == '') {
			
			 $('<input data-text-field="supervisorName" data-value-field="supervisorId" id="superID" data-bind="value:' + options.field + '"/>')
             .appendTo(container).kendoComboBox({
            	 placeholder:"Select Supervisor Name",
            	//optionLabel : "Select",
            	autoBind:false,
                 dataSource: {  
                     transport:{
                         read: "${getSupervisorNameUrl}"
                     }
                 },
             	change : function(e) {
					if (this.value() && this.selectedIndex == -1) {
						alert("Supervisor Name does not exists");
						$("#superID").data("kendoComboBox")
								.value("");
					}
				},
             });
			 
			 }
			 else{
			 
				 $('<input data-text-field="supervisorName" data-value-field="supervisorId" id="superID" data-bind="value:' + options.field + '"/>')
	             .appendTo(container).kendoComboBox({
	            	 placeholder:"Select Supervisor Name",
	            	//optionLabel : "Select",
	            	autoBind:false,
	                 dataSource: {  
	                     transport:{
	                         read: "${getSupervisorNameUrl}"
	                     }
	                 },
	             	change : function(e) {
						if (this.value() && this.selectedIndex == -1) {
							alert("Supervisor Name does not exists");
							$("#superID").data("kendoComboBox")
									.value("");
						}
					},
	             });
				 
				 
			 }
			 
			 /* $('<span class="k-invalid-msg" data-for="This Field"></span>').appendTo(container); */
		 }
		 
		/*  function onChangeNames(){
			 
			 var firstItem = $('#staffGrid').data().kendoGrid.dataSource.data()[0];
			var supervisorName =  firstItem.get('supervisorName');
			var supervisorId = firstItem.get('supervisorId');
			if ( supervisorId == null) {
				
				$.ajax({
					 
					type : "POST",
					dataType:"text",
					data: {
						 supervisorId:firstItem.get('supervisorId'),
						 supervisorName:supervisorName
					 },
	       			url : "./patrolTrackStaff/checkSupervisorName1",
	       			success : function(response) {
	       				if( response == "NAMENOTEXISTS"){
	       					alert("Error :\n1. Invalid Supervisor Name : Please select it from drop down");
	       					return false;
	       					
	       				}
	       				
	       			}
					 
				});
			}
			else{
			$.ajax({
				 
				type : "POST",
				data: {
					 supervisorId:firstItem.get('supervisorId'),
					 supervisorName:supervisorName
				 },
       			url : "./patrolTrackStaff/checkSupervisorName",
       			success : function(response) {
       				if( response == "NOTEXISTS"){
       					alert("Error :\n1. Invalid Supervisor Name : Please select it from drop down");
       					return false;
       					
       				}
       				
       			}
				 
			});
		 }
		
		 } */

		 function staffNameDropDownEditor(container, options) 
		 {
			 if (options.model.firstName == '') {
			 
				$('<input data-text-field="firstName" data-value-field="personId" id="personId" data-bind="value:' + options.field + '"/>')
		             .appendTo(container).kendoComboBox({
		            	 placeholder:"Select Staff Name",
		            	//optionLabel : "Select",
		            	autoBind:false,
		                 dataSource: {  
		                     transport:{
		                         read: "${getStaffNameUrl}"
		                     }
		                 },
		             	change : function(e) {
							if (this.value() && this.selectedIndex == -1) {
								alert("Staff Name does not exists");
								$("#personId").data("kendoComboBox")
										.value("");
							}
						},
		             });
				/* $('<span class="k-invalid-msg" data-for="This field"></span>').appendTo(container); */
			 }
			 else{
				 $('<input disabled data-text-field="firstName" data-value-field="personId" id="personId" data-bind="value:' + options.field + '"/>')
	             .appendTo(container).kendoComboBox({
	            	 placeholder:"Select Staff Name",
	            	//optionLabel : "Select",
	            	autoBind:false,
	                 dataSource: {  
	                     transport:{
	                         read: "${getStaffNameUrl}"
	                     }
	                 }
	             });
			/* $('<span class="k-invalid-msg" data-for="This field"></span>').appendTo(container); */
				 
				 
			 }
		 }
		
		 /* function accessCardsDropDownEditor(container, options) 
			{
					$('<input id="card" data-text-field="acNo" data-value-field="acNo" data-bind="value:' + options.field + '"/>')
			             .appendTo(container)
			             .kendoComboBox({
			            	 placeholder:"Select access card",
		            	//optionLabel : "Select",
			            	 cascadeFrom: "personId",
			            	 autoBind: false,
			                 dataSource: {  
			                     transport:{
			                         read: "${getAccessCardNoUrl}"
			                     }
			                 },
			             	change : function(e) {
								if (this.value() && this.selectedIndex == -1) {
									alert("Account No does not exists");
									$("#card").data("kendoComboBox")
											.value("");
								}
							},
			             });
				 $('<span class="k-invalid-msg" data-for="This field"></span>').appendTo(container); 
			} */
		 
		 
		 
		 function accessCardsDropDownEditor(container, options) 
			{
			$('<input name="Access Card" id="card" data-text-field="accessCardNo" data-value-field="acNo" data-bind="value:' + options.field + '" required="true"/>')
			.appendTo(container).kendoComboBox({
				cascadeFrom: "personId",
				autoBind : false,
				placeholder : "Select Card",
				headerTemplate : '<div class="dropdown-header">'
					+ '<span class="k-widget k-header">Photo</span>'
					+ '<span class="k-widget k-header">Contact info</span>'
					+ '</div>',
				template : '<table><tr>'
						+ '<td align="left"><span class="k-state-default"><b>Card No:#: data.accessCardNo #</b></span><br>'
						+ '</td></tr></table>',
				dataSource : {
					transport : {		
						read :  "${getAccessCardNoUrl}"
					}
				},
				change : function (e) {
		            if (this.value() && this.selectedIndex == -1) {                    
						alert("Access Card doesn't exist!");
		                $("#card").data("kendoComboBox").value("");
		        	}
		            
			    } 
			});
			
			$('<span class="k-invalid-msg" data-for="Access Card"></span>').appendTo(container);
			}
			 
		function patroltrackDropDownEditor(container, options) {
			if (options.model.ptName == '') {
			$('<input id="tracks" data-text-field="ptName"  data-value-field="ptName" data-bind="value:' + options.field + '"/>')
					 .appendTo(container)
			             .kendoDropDownList({
		            	//placeholder:"Select patrolTrack",
		           			optionLabel : "Select",
						 autoBind: false,
						dataSource : {
							transport : {
								read : "${getPatroltrackNameUrl}"
							}
						}
					}); 
			}
			else{
				
				$('<input id="tracks" data-text-field="ptName" disabled data-value-field="ptName" data-bind="value:' + options.field + '"/>')
						 .appendTo(container)
				             .kendoDropDownList({
			            	//placeholder:"Select patrolTrack",
			           			optionLabel : "Select",
							 autoBind: false,
							dataSource : {
								transport : {
									read : "${getPatroltrackNameUrl}"
								}
							}
						}); 
			
			}
			 $('<span class="k-invalid-msg" data-for="This field"></span>').appendTo(container);
		}
		//custom validation
		var requiredStartDate = "";
		(function($, kendo) {
			$
					.extend(
							true,
							kendo.ui.validator,
							{
								rules : { // custom rules  
									 startDateMsg: function (input, params) 
						             {
					                     if (input.filter("[name = 'fromDate']").length && input.val() != "") 
					                     {                          
					                         var selectedDate = input.val();
					                         requiredStartDate = selectedDate;

					                        
					                     }
					                     return true;
					                 },
					                 endDateMsg: function (input, params) 
						             {
					                     if (input.filter("[name = 'toDate']").length && input.val() != "") 
					                     {     
					                         var selectedDate = input.val();
					                         var flagDate = false;

					                         if ($.datepicker.parseDate('dd/mm/yy', selectedDate) >= $.datepicker.parseDate('dd/mm/yy', requiredStartDate)) 
					                         {
					                                flagDate = true;
					                         }
					                         return flagDate;
					                     }
					                     return true;
					                 } ,
					                 /* trackNameValidation : function(input) {
											if ((input.filter("[name='ptName']")) && (input.val() == "Select")) {
												
												return false;
											}
											return true;
										}, */
										  fromDateValidation : function(input) {
												if ((input.filter("[name='fromDate']")).length && (input.val() == "")) {
													
													return false;
												}
												return true;
											}, 
											 toDateValidation : function(input) {
													if ((input.filter("[name='toDate']")).length && (input.val() == "")) {
														
														return false;
													}
													return true;
												}, 
								},
								messages : {
									//custom rules messages
									startDateMsg:"",
									fromDateValidation : "This field is required",
									toDateValidation : "This field is required", 
									/* trackNameValidation : "This field is required", */
									endDateMsg:"To date should be greater than or equal to From date"
								}
							});

		})(jQuery, kendo);
		//End Of Validation
		 
		function onRequestStart(e){
			$('.k-grid-update').hide();
	        $('.k-edit-buttons')
	                .append(
	                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
	        $('.k-grid-cancel').hide();
	        
		 /* if (e.type == "create"){
			var gridStoreGoodsReturn = $("#staffGrid").data("kendoGrid");
			gridStoreGoodsReturn.cancelRow();		
		} */ 	
	   }
	
			function onRequestEndStaff(e) {
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
									$("#alertsBox").html("Error: Creating the Staff record\n\n : " + errorInfo);
									$("#alertsBox").dialog({
										modal : true,
										buttons : {
											"Close" : function() {
												$(this).dialog("close");
											}
										}
									});
							}
							var grid = $("#staffGrid").data("kendoGrid");
							grid.dataSource.read();
							grid.refresh();
						}
					 else if (e.response.status == "INVALID") {

							errorInfo = "";

							errorInfo = e.response.result.invalid;

							if (e.type == "create") {
								$("#alertsBox").html("");
								$("#alertsBox").html("Error: Creating the Staff record\n\n : " + errorInfo);
								$("#alertsBox").dialog({
									modal : true,
									buttons : {
										"Close" : function() {
											$(this).dialog("close");
										}
									}
								});
							
							}
							
							var grid = $("#staffGrid").data("kendoGrid");
							grid.dataSource.read();
							grid.refresh();
							return false;
						}
						else if (e.response.status == "INVALIDDATES") {

							errorInfo = "";

							errorInfo = e.response.result.invalidDate;

							if (e.type == "create") {
								$("#alertsBox").html("");
								$("#alertsBox").html("Error: Creating the Staff record\n\n : " + errorInfo);
								$("#alertsBox").dialog({
									modal : true,
									buttons : {
										"Close" : function() {
											$(this).dialog("close");
										}
									}
								});
								
							}
							
							var grid = $("#staffGrid").data("kendoGrid");
							grid.dataSource.read();
							grid.refresh();
							return false;
						}
						else if (e.response.status == "INVALIDDATE") {
							errorInfo = "";

							errorInfo = e.response.result.invalidDate;

							if (e.type == "update") {
								$("#alertsBox").html("");
								$("#alertsBox").html("Error: Updating the Staff record\n\n : " + errorInfo);
								$("#alertsBox").dialog({
									modal : true,
									buttons : {
										"Close" : function() {
											$(this).dialog("close");
										}
									}
								});
								
							}
							
							var grid = $("#staffGrid").data("kendoGrid");
							grid.dataSource.read();
							grid.refresh();
							return false;
						}
						else if (e.response.status == "INVALIDPTNAME") {
							errorInfo = "";

							errorInfo = e.response.result.invalidPtName;

							if (e.type == "update") {
								$("#alertsBox").html("");
								$("#alertsBox").html("Error: Updating the Staff record\n\n : " + errorInfo);
								$("#alertsBox").dialog({
									modal : true,
									buttons : {
										"Close" : function() {
											$(this).dialog("close");
										}
									}
								});
								
							}
							
							var grid = $("#staffGrid").data("kendoGrid");
							grid.dataSource.read();
							grid.refresh();
							return false;
						}
						
						else if (e.response.status == "INVALIDSUPERVISORNAME") {
							errorInfo = "";

							errorInfo = e.response.result.invalidSupervisorName;

							if (e.type == "update") {
								$("#alertsBox").html("");
								$("#alertsBox").html("Error: Updating the Staff record\n\n : " + errorInfo);
								$("#alertsBox").dialog({
									modal : true,
									buttons : {
										"Close" : function() {
											$(this).dialog("close");
										}
									}
								});
								
							}
							
							var grid = $("#staffGrid").data("kendoGrid");
							grid.dataSource.read();
							grid.refresh();
							return false;
						}
						else if (e.response.status == "INVALIDCARDNAME") {
							errorInfo = "";

							errorInfo = e.response.result.invalidCardName;

							if (e.type == "update") {
								$("#alertsBox").html("");
								$("#alertsBox").html("Error: Updating the Staff record\n\n : " + errorInfo);
								$("#alertsBox").dialog({
									modal : true,
									buttons : {
										"Close" : function() {
											$(this).dialog("close");
										}
									}
								});
								
							}
							
							var grid = $("#staffGrid").data("kendoGrid");
							grid.dataSource.read();
							grid.refresh();
							return false;
						}
					 else if (e.type == "create") {
						 
						 $("#alertsBox").html("");
							$("#alertsBox").html("Staff record created successfully");
							$("#alertsBox").dialog({
								modal : true,
								buttons : {
									"Close" : function() {
										$(this).dialog("close");
									}
								}
							});
							
						//alert("Staff record created successfully");
							var grid = $("#staffGrid").data("kendoGrid");
							grid.dataSource.read();
							grid.refresh();
					 }

					  else if (e.type == "update") {
						 $("#alertsBox").html("");
							$("#alertsBox").html("Staff record updated successfully");
							$("#alertsBox").dialog({
								modal : true,
								buttons : {
									"Close" : function() {
										$(this).dialog("close");
									}
								}
							});
							
							var grid = $("#staffGrid").data("kendoGrid");
							grid.dataSource.read();
							grid.refresh();
					 } 
					 else if (e.response.status == "DELETESTAFFERROR") {
							errorInfo = "";
							errorInfo = e.response.result.deletestafferror;
							if (e.type == "destroy") {
								$("#alertsBox").html("");
								$("#alertsBox").html("Error: Deleting The Patrol Staff Record\n\n : " + errorInfo);
										$("#alertsBox").dialog({
											modal : true,
											buttons : {
												"Close" : function() {
													$(this).dialog("close");
												}
											}
										});
								
							}
							var grid = $("#staffGrid").data("kendoGrid");
							grid.dataSource.read();
							grid.refresh();
						}
					 
					 else if (e.type == "destroy") {
						 $("#alertsBox").html("");
							$("#alertsBox").html("Staff record deleted successfully");
									$("#alertsBox").dialog({
										modal : true,
										buttons : {
											"Close" : function() {
												$(this).dialog("close");
											}
										}
									});
							
							var grid = $("#staffGrid").data("kendoGrid");
							grid.dataSource.read();
							grid.refresh();
					 }
				}
		}

			
		</script>
