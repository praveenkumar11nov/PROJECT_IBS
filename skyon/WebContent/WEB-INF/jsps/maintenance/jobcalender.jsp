<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@include file="/common/taglibs.jsp"%>

<c:url value="/index/read" var="readUrl" />
<c:url value="/index/create" var="createUrl" />
<c:url value="/index/update" var="updateUrl" />
<c:url value="/jobcalender/index/destroy" var="destroyUrl" />
<c:url value="./jobcardsdetails/getJobTypes" var="getJobTypes" />
<c:url value="./jobcardsdetails/getdepartmentname" var="getJobDepartments" />
<c:url value="./jobcardsdetails/getMPerson" var="getJobOwners" />
<c:url value="./jobcardsdetails/getMaintainance" var="getMaintainanceTypes" />
<c:url value="./asset/readAssetAll" var="getAssets" />
<c:url value="./jobcardsdetails/getPriority" var="JobPriority" />

<%
	Date todaysDate=new Date();
	
	String date1 = new SimpleDateFormat("dd/MM/yyyy").format(todaysDate);
	Date date = new SimpleDateFormat("dd/MM/yyyy").parse(date1);
	
	Date startTime = new SimpleDateFormat("dd/MM/yyyy hh:mm").parse(date1+" 12:00");
	
	ArrayList<HashMap<String, Object>> resources = new ArrayList<HashMap<String, Object>>();
	
	HashMap<String, Object> asset = new HashMap<String, Object>();
	asset.put("text", "Asset Maintainance");
	asset.put("value", 1);
	asset.put("color", "#f8a398");
	resources.add(asset);
	HashMap<String, Object> regular = new HashMap<String, Object>();
	regular.put("text", "Regular Maintainance");
	regular.put("value", 2);
	regular.put("color", "#51a0ed");
	resources.add(regular);	
%>
	 
    <div class="widget grid12">
    
    <div id="team-schedule">
	 <div id="people">
	 	<label id="assetLabel">Asset<br>Maintainance</label><input checked type="checkbox" id="asset" value="1">
	    <label id="regularLabel">Regular Maintainance</label><input checked type="checkbox"  id="regular" value="2">        
    </div>
    </div>
    
    <kendo:grid-detailTemplate id="customEditor">
	 <div class="k-edit-form-container">
	 	 <div class="k-edit-label">
        	<label for="jobNumber">Job&nbsp;Number</label>
        	<label style="color:red;">*</label>
      	</div>
	    <div data-container-for="jobNumber" class="k-edit-field">
	      	<input type="text" class="k-input k-textbox" name="jobNumber" data-bind="value:jobNumber">
	    </div>
	    
	 	 <div class="k-edit-label">
        	<label for="title">Job Title</label>
        	<label style="color:red;">*</label>
      	</div>
	    <div data-container-for="title" class="k-edit-field">
	      	<input type="text" class="k-input k-textbox" required="required"  name="Title" data-bind="value:title">
	    </div>
	    
	 	<!--  <div class="k-edit-label">
        	<label for="jobGroup">Job&nbsp;Group</label>
        	<label style="color:red;">*</label>
      	</div> 
	    <div data-container-for="jobGroup" class="k-edit-field">
	      	<input type="text" class="k-input k-textbox" required="required" name="Group" data-bind="value:jobGroup">
	    </div>-->
	    
	 	 <div class="k-edit-label">
        	<label for="description">Description</label>
      	</div>
	    <div data-container-for="description" class="k-edit-field">
	      	<textarea cols="5" rows="3" style="width:150px;" data-bind="value:description" data-value-field="description" data-text-field="description">
	      	</textarea>
	    </div>
	    
	    <div class="k-edit-label">
        	<label for="jobTypes">Job&nbsp;Type</label>
        	<label style="color:red;">*</label>
      	</div>
	    <div data-container-for="jobTypes" class="k-edit-field">
		    <kendo:dropDownList name="jobTypes" id="jobTypes" dataTextField="jobType" dataValueField="jobTypeId" change="onChangeJobType" optionLabel="Select Job Type">
		    	 <kendo:dataSource>
	                <kendo:dataSource-transport>
	                   <kendo:dataSource-transport-read url="${getJobTypes}" type="GET" contentType="application/json"/>
	                </kendo:dataSource-transport>           
            	</kendo:dataSource>		             
		   </kendo:dropDownList>
	    </div>
	    
	    <div class="k-edit-label">
        	<label for="expectedDays">Expected&nbsp;SLA</label>
        	<label style="color:red;">*</label>
      	</div>
	    <div data-container-for="expectedDays" class="k-edit-field">
	      	<input type="text" class="k-input k-textbox" required="required" name="Expected Days" data-bind="value:expectedDays">
	    </div>
	    
	     <div class="k-edit-label">
        	<label for="departmentName">Job&nbsp;Department</label>
        	<label style="color:red;">*</label>
      	</div>
	    <div data-container-for="departmentName" class="k-edit-field">
		    <kendo:dropDownList name="departmentName" id="dept" dataTextField="departmentName" dataValueField="departmentId" optionLabel="Select Departments">
		    	 <kendo:dataSource>
	                <kendo:dataSource-transport>
	                   <kendo:dataSource-transport-read url="${getJobDepartments}" type="GET" contentType="application/json"/>
	                </kendo:dataSource-transport>           
            	</kendo:dataSource>		             
		   </kendo:dropDownList>
	    </div>
	   
	     <div class="k-edit-label">
        	<label for="jobOwner">Job&nbsp;Owner</label>
        	<label style="color:red;">*</label>
      	</div>
	    <div data-container-for="pn_Name" class="k-edit-field">
		    <kendo:dropDownList name="pn_Name" id="person" cascadeFrom="dept" autoBind="false" dataTextField="pn_Name" dataValueField="personId"  optionLabel="Select Owners">
		    	 <kendo:dataSource>
	                <kendo:dataSource-transport>
	                   <kendo:dataSource-transport-read url="${getJobOwners}" type="GET" contentType="application/json"/>
	                </kendo:dataSource-transport>           
            	</kendo:dataSource>		             
		   </kendo:dropDownList>
	    </div>
	    
	     <div class="k-edit-label">
        	<label for="jobPriority">Job&nbsp;Priority</label>
        	<label style="color:red;">*</label>
      	</div>      	
	    <div data-container-for="jobPriority" class="k-edit-field">
		    <kendo:dropDownList name="jobPriority" id="priorityVal" dataTextField="priority" dataValueField="priority" optionLabel="Select">		
		        <kendo:dataSource>
	                <kendo:dataSource-transport>
	                   <kendo:dataSource-transport-read url="${JobPriority}" type="GET" contentType="application/json"/>
	                </kendo:dataSource-transport>           
            	</kendo:dataSource>	    	             
		   </kendo:dropDownList>
	    </div>
	    
	     <div class="k-edit-label">
        	<label for="maintainanceTypes">Maintainance&nbsp;Type&nbsp;</label>
        	<label style="color:red;">*</label>
      	</div>
	    <div data-container-for="maintainanceTypes" class="k-edit-field">
		    <kendo:dropDownList name="maintainanceTypes"  id="maintainanceTypes" dataTextField="jobMt" dataValueField="jobMtId"  optionLabel="Select">
		    	 <kendo:dataSource>
	                <kendo:dataSource-transport>
	                   <kendo:dataSource-transport-read url="${getMaintainanceTypes}" type="GET" contentType="application/json"/>
	                </kendo:dataSource-transport>           
            	</kendo:dataSource>		             
		   </kendo:dropDownList>
	    </div>	    
	  
	     <div class="k-edit-label">
        	<label for="assetName">Job&nbsp;Assets&nbsp;</label>
        	<label style="color:red;">*</label>
      	</div>
	    <div data-container-for="assetName" class="k-edit-field">
	    	 <kendo:multiSelect name="assetName" id="assets" dataTextField="assetName" dataValueField="assetId" filter="startswith">
	            <kendo:dataSource>
	                <kendo:dataSource-transport>
	                   <kendo:dataSource-transport-read url="${getAssets}" type="POST" contentType="application/json"/>
	                 </kendo:dataSource-transport>
	            	</kendo:dataSource>
        	</kendo:multiSelect>
	    </div>
	    
	     <div class="k-edit-label">
        	<label for="scheduleType">Schedule&nbsp;Type&nbsp;</label>
      	</div>
	    <div data-container-for="scheduleType" class="k-edit-field">
	      	<input type="text" class="k-input k-textbox" name="scheduleType" data-bind="value:scheduleType">
	    </div>
	    
	     <div class="k-edit-label">
        	<label for="isAllDay">Is All Day</label>
      	</div>
	    <div data-container-for="isAllDay" class="k-edit-field">
	      	<input type="checkbox" value="true" data-bind="checked:isAllDay" name="IsAllDay" data-bind="value:isAllDay">
	    </div>
      
      	<div class="k-edit-label">
    	    	<label for="recurrenceRule">Repeat</label>
				
		</div>
   		<div class="k-edit-field" data-container-for="recurrenceRule">
   	    	<div data-bind="value: recurrenceRule" name="recurrenceRule" data-role="recurrenceeditor"></div>
   		</div>   		
   		
	 </div>
 	</kendo:grid-detailTemplate>
    
	<kendo:scheduler name="scheduler" remove="jobcalenderRemove"  edit="kendoschedulerEvent" height="670" date="<%= date %>" startTime="<%= startTime %>">
   		<kendo:scheduler-editable>
        	<kendo:scheduler-editable-template>
	           $("#customEditor").html()	           
        	</kendo:scheduler-editable-template>
    	</kendo:scheduler-editable>
    	
    	<kendo:scheduler-views>
    		<kendo:scheduler-view type="day" selected="true"/>    		
    		<kendo:scheduler-view type="week" />
    		<kendo:scheduler-view type="month"  />
    		<kendo:scheduler-view type="agenda" />
    	</kendo:scheduler-views>   
    	<kendo:scheduler-resources>
    		<kendo:scheduler-resource field="scheduleType" title="ScheduleType">
    			<kendo:dataSource data="<%= resources %>"/>
    		</kendo:scheduler-resource>
    	</kendo:scheduler-resources> 	
        <kendo:dataSource batch="true" requestEnd="onRequestEnd" requestStart="OnRequestStart">
        
        	<kendo:dataSource-filter>
           		<kendo:dataSource-filterItem logic="or">
           			<kendo:dataSource-filterItem field="scheduleType" operator="eq" value="1" />
           			<kendo:dataSource-filterItem field="scheduleType" operator="eq" value="2" />
           		</kendo:dataSource-filterItem>
           	</kendo:dataSource-filter>         
             <kendo:dataSource-schema parse="parse">
                <kendo:dataSource-schema-model id="jobCalenderId">
                     <kendo:dataSource-schema-model-fields>
                         <kendo:dataSource-schema-model-field name="jobCalenderId" type="number" />
                         <kendo:dataSource-schema-model-field name="title" defaultValue="No title" type="string" />
                         <kendo:dataSource-schema-model-field name="description" type="string" />
                         <kendo:dataSource-schema-model-field name="isAllDay" type="boolean" />
                         <kendo:dataSource-schema-model-field name="recurrenceRule" type="string" nullable="true"/>
                         <kendo:dataSource-schema-model-field name="recurrenceId" type="number" nullable="true" />
                         <kendo:dataSource-schema-model-field name="recurrenceException" type="string" nullable="true" />
                         <kendo:dataSource-schema-model-field name="scheduleType" type="string" defaultValue="2" />                         
                         <kendo:dataSource-schema-model-field name="start" type="date" />
                         <kendo:dataSource-schema-model-field name="end" type="date" />
                         <kendo:dataSource-schema-model-field name="jobNumber" type="string" />
                         <%-- <kendo:dataSource-schema-model-field name="jobGroup" /> --%>
                         <kendo:dataSource-schema-model-field name="jobOwner" />
                         <kendo:dataSource-schema-model-field name="expectedDays" />
                         <kendo:dataSource-schema-model-field name="jobPriority" />
                         <kendo:dataSource-schema-model-field name="assetName" />
                         <kendo:dataSource-schema-model-field name="maintainanceTypes" />
                         <kendo:dataSource-schema-model-field name="departmentName" />
                         <kendo:dataSource-schema-model-field name="jobTypes" />
                         <kendo:dataSource-schema-model-field name="pn_Name" />
                    </kendo:dataSource-schema-model-fields>
                </kendo:dataSource-schema-model>
            </kendo:dataSource-schema>
            <kendo:dataSource-transport>
                <kendo:dataSource-transport-create url="${createUrl}" dataType="json" type="POST" contentType="application/json" />
                <kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json" />
                <kendo:dataSource-transport-update url="${updateUrl}" dataType="json" type="POST" contentType="application/json" />
                <kendo:dataSource-transport-destroy url="${destroyUrl}" dataType="json" type="POST" contentType="application/json" />
                <kendo:dataSource-transport-parameterMap>
                	<script>
	                	function parameterMap(options, type) { 
	                		if(type==="read"){
	                			return JSON.stringify(options);
	                		} else {
	                			return JSON.stringify(options.models);
	                		}
	                	}
                	</script>
                </kendo:dataSource-transport-parameterMap>              
            </kendo:dataSource-transport>
        </kendo:dataSource>
    </kendo:scheduler>    
  </div>    
   <div id="alertsBox" title="Alert"></div> 
    <script>    
    var maintainanceTypedataSource = new kendo.data.DataSource({
        transport: {
            read: function(options) {
                $.ajax({
                    url: "./jobcardsdetails/getMaintainance",
                    dataType: 'json',

                    success: function(result) {
                        options.success(result);
                    }
                });
            }
        }
    });    
    
    function onChangeJobType(){
    	
    	 var value=this.dataItem();    	
		 $('input[name="Expected Days"]').val(value.jobSLA);	  
		 $('input[name="Expected Days"]').change(); 
		 $('input[name="Expected Days"]').attr('readonly', 'readonly'); 
    }
    function parse (response) {   
	    $.each(response, function (idx, elem) {
	    	  if (elem.start && typeof elem.start === "string") {
                elem.start = kendo.parseDate(elem.start, "yyyy-MM-ddTHH:mm:ss.fffZ");
            }
            if (elem.end && typeof elem.end === "string") {
                elem.end = kendo.parseDate(elem.end, "yyyy-MM-ddTHH:mm:ss.fffZ");
            }            
        });
        return response;
	}  
	   
    	function jobcalenderRemove(e){
    		securityCheckForActions("./maintainance/jobcalender/deleteButton");
    	}
   
	
    	function kendoschedulerEvent(e){
    		
    		var result=securityCheckForActionsForStatus("./maintainance/jobcalender/addeditButton");  		
    		
    		
    		if(result=="success"){
    			 $(".k-scheduler-cancel").click(function () {    		   	    	
    				var scheduler = $("#scheduler").data("kendoScheduler");
    	   			scheduler.dataSource.read();
    	   			scheduler.refresh();  
    		    }); 
    			
    			$(".k-textbox ").css({
    				"box-shadow":"0 0 0px black"				
    			});   	    		
   	    		  
   	    		$('label[for="timezone"]').parent().remove();		  		    
   	    		$('label[for="jobNumber"]').parent().remove();		
   	    	 	$('input[name="jobNumber"]').parent().remove();
   	    		$('div[data-container-for="timezone"]').hide();
   	    		$('label[for="scheduleType"]').parent().remove();		  		    
   	    		$('div[data-container-for="scheduleType"]').hide();
   	    		/* $('input[name="title"]').css({
   	    			"width":"150px"
   	    		}); */ 
   	    	 
   	    		 $(".k-multiselect-wrap").css({
   					"width" : "150px"
   				});
   	    		$(".k-multiselect").css({
   					"width" : "150px"
   				}); 
   	    		
   	    	 $(".k-scheduler-update").click(function () {	
   	    		 
	   	    	var jobtypes=$("#jobTypes").val();
	   	    	var dept=$("#dept").val();
	   		    var person=$("#person").val();
	   		    var priority=$("#priorityVal").val();
	   		    var maintainanceTypes=$("#maintainanceTypes").val();
	   		    var assets=$("#assets").val();   		    	
	   		    if (jobtypes==null || jobtypes=="") {
   		            alert("Please Select Job Type");
   					return false;	
   			  	} 
   	    		if (dept==null || dept=="") {
   		            alert("Please Select Job Department");
   					return false;	
   			  	} 
   	    		if (person==null || person=="") {
   		            alert("Please Select Job Owner");
   					return false;	
   			  	} 
   	    		if (priority==null || priority=="" || priority=="Select") {
   		            alert("Please Select Job Priority");
   					return false;	
   			  	} 
   	    		if (maintainanceTypes==null || maintainanceTypes=="") {
   		            alert("Please Select Maintainance Types");
   					return false;	
   			  	} 
   	    		if (assets==null) {
   		            alert("Please Select Job Assets");
   					return false;	
   			  	} 
   	    	 });
   	    		
    		}			
    	}
    
	     $("#people :checkbox").change(function(e) {
	        var checked = $.map($("#people :checked"), function(checkbox) {
	            return parseInt($(checkbox).val());
	     });
	
	        var filter = {
	            logic: "or",
	            filters: $.map(checked, function(value) {
	                return {
	                    operator: "eq",
	                    field: "scheduleType",
	                    value: value
	                };
	            })
	        };
	
	        var scheduler = $("#scheduler").data("kendoScheduler");
	
	        scheduler.dataSource.filter(filter);
	        
	        
	    });	     
	     
	     function OnRequestStart(e){
	    	
	    	 $('.k-scheduler-update').hide();
		        $('.k-edit-buttons')
		                .append(
		                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
		        $('.k-scheduler-cancel').hide();
	          
	     }
	     function onRequestEnd(e) {
	   	   
	    	   	if (typeof e.response != 'undefined')
	    		{
	    	   		if (e.response.status == "invalid") {
	    	   			/* var scheduler = $("#scheduler").data("kendoScheduler");
	    	   			scheduler.dataSource.read();
	    	   			scheduler.refresh(); */
	    	   			
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
	    				var scheduler = $("#scheduler").data("kendoScheduler");
	    	   			scheduler.dataSource.read();
	    	   			scheduler.refresh();  
	    				return false;
	    			}
	    	   		if (e.response.status == "FAIL") {
	    	   			/* var scheduler = $("#scheduler").data("kendoScheduler");
	    	   			scheduler.dataSource.read();
	    	   			scheduler.refresh(); */
	    	   			
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
	    	       			
	    	   			}
	    	   	
	    	   			if (e.type == "update") {
	    	   				/* var scheduler = $("#scheduler").data("kendoScheduler");
		    	   			scheduler.dataSource.read();
		    	   			scheduler.refresh(); */
		    	   			
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
	    	   			}
	    	   	
	    	   			
	    	   			return false;
	    	   		}

	    	   		if (e.type == "update" && !e.response.Errors) {	 
	    	   			var scheduler = $("#scheduler").data("kendoScheduler");
	    	   			scheduler.dataSource.read();
	    	   			scheduler.refresh(); 	    	   		 	
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
	    	   			
	    	   		}
	    	   		
	    	   		if (e.type == "update" && e.response.Errors) {
	    	   			/* var scheduler = $("#scheduler").data("kendoScheduler");
	    	   			scheduler.dataSource.read();
	    	   			scheduler.refresh();
	    	   			 */
	    	   			$("#alertsBox").html("");
	    	   			$("#alertsBox").html("Record Not Updated");
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
	    	   			
	    	   		}

	    	   		if (e.type == "create" && !e.response.Errors) {
	    	   			var scheduler = $("#scheduler").data("kendoScheduler");
	    	   			scheduler.dataSource.read();
	    	   			scheduler.refresh();  
	    	   			
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
	    	   			
	    	   			
	    	   			
	    	   			
	    	   		}
	    	   		if (e.type == "destroy" && !e.response.Errors) {
	    	   			/* var scheduler = $("#scheduler").data("kendoScheduler");
	    	   			scheduler.dataSource.read();
	    	   			scheduler.refresh();
	    	   		 	 */
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
	    	   			var scheduler = $("#scheduler").data("kendoScheduler");
	    	   			scheduler.dataSource.read();
	    	   			scheduler.refresh();
	    	   		}	
	    		}
	     }     
	     
	  
	</script>	


<style scoped>

#team-schedule {
    background: url(<c:url value="/resources/team-schedule.png"/>) transparent no-repeat; 
    height: 115px;
    position: relative;
}
#people {
    background: url(<c:url value="/resources/scheduler-people.png" />) no-repeat; 
    width: 345px;
    height: 115px;
    position: absolute;
    right: 147px;
    
} 
#asset {
    position: absolute;
    left: 7px;
    top: 83px;
}
#assetLabel {
    position: absolute;
    left: 45px;
    top: 45px;
}
#regular {
   position: absolute;
    left: 206px;
    top: 83px;
}
#regularLabel {
    position: absolute;
    left: 238px;
    top: 46px;
}
</style>