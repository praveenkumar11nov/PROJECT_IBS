<%@include file="/common/taglibs.jsp"%>

<script src="./resources/twitter-bootstrap-wizard/bootstrap/js/bootstrap.min.js"></script>

<c:url value="/billingAndPaymentHelpDesk/helpDesk" var="readUrl"></c:url>

<%-- <c:url value="/billingAndPaymentHelpDesk/getRoles" var="rolesUrl" /> --%>

<kendo:grid name="helpDeskgrid" pageable="true" change="onChange" resizable="true" sortable="true" dataBound="billingPaymentBound" reorderable="true" selectable="true" scrollable="true"
		filterable="true" groupable="true" >
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" ></kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
		 <kendo:grid-filterable-operators>
		  	<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains"/>
		 </kendo:grid-filterable-operators>
			
		</kendo:grid-filterable>
		<kendo:grid-editable mode="popup" />
	<kendo:grid-toolbar>
		<kendo:grid-toolbarItem name="queryTemplatesDetailsExport" text="Export To Excel" /> 
		</kendo:grid-toolbar> 
		<kendo:grid-columns>
			
			<kendo:grid-column title="&nbsp;" width="90px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="update" text="edit" click="HelpTopics"/>
				</kendo:grid-column-command>
			</kendo:grid-column>
			
			<kendo:grid-column title="ID&nbsp;" field="id" width="130px" hidden="true" filterable="false">
			    
			</kendo:grid-column>
				
			<kendo:grid-column title="Property No &nbsp;" field="propertyNo" width="130px" filterable="false">
			    
			</kendo:grid-column>
			
				<kendo:grid-column title="Person Name &nbsp;" field="person_Name" width="130px" filterable="false">
			   
			</kendo:grid-column>
			
				<kendo:grid-column title="Query Topics &nbsp;" field="helpTopic" width="130px" filterable="false" >
			    
			</kendo:grid-column>
			<kendo:grid-column title="Other Query Topic&nbsp;" field="otherHelpTopic" width="130px" filterable="false" >
			    
			</kendo:grid-column>	
			
				<kendo:grid-column title="Issue Details&nbsp;" field="issue_Details" width="130px" filterable="false">
			    
			</kendo:grid-column>
			
			<kendo:grid-column title="Status &nbsp;" field="status" width="130px" filterable="false" >
			    
			</kendo:grid-column>
			
			<kendo:grid-column title="Remarks &nbsp;" field="remarks" width="130px" filterable="false" >
			    
			</kendo:grid-column> 
			
			<kendo:grid-column title="Created Date&nbsp;" field="createdDate" width="130px" filterable="false" format="{0:dd-MM-yyyy}">
			    
			</kendo:grid-column>
				
			<kendo:grid-column title="Resolved Date&nbsp;" field="reSolvedDate" width="130px" filterable="false" format="{0:dd-MM-yyyy}">
			    
			</kendo:grid-column>	
			<kendo:grid-column title="Mobile No" field="mobileNo" width="130px" filterable="false">
			    
			</kendo:grid-column>
			
			<kendo:grid-column title="Email ID&nbsp;" field="emailId" width="130px" filterable="false">
			    
			</kendo:grid-column>
			
		</kendo:grid-columns>
		<kendo:dataSource >
			<kendo:dataSource-transport>
				 <kendo:dataSource-transport-read url="${readUrl}" dataType="json"
					type="POST" contentType="application/json" /> 
				 <%-- <kendo:dataSource-transport-update url="${updateUrl}"
					dataType="json" type="POST" contentType="application/json" /> --%>
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
			<kendo:dataSource-schema>
				<kendo:dataSource-schema-model id="id" >
					<kendo:dataSource-schema-model-fields>
					<kendo:dataSource-schema-model-field name="id" type="number"/>
					    <kendo:dataSource-schema-model-field name="propertyNo" type="string"/>
					    <kendo:dataSource-schema-model-field name="person_Name" type="string"/>
					    <kendo:dataSource-schema-model-field name="helpTopic" type="string"/>
					    <kendo:dataSource-schema-model-field name="otherHelpTopic" type="string"/>
					    <kendo:dataSource-schema-model-field name="status" type="string"/>
					    <kendo:dataSource-schema-model-field name="remarks" type="string"/>
					    <kendo:dataSource-schema-model-field name="issue_Details" type="string"/>
					    <kendo:dataSource-schema-model-field name="createdDate" type="string"/>
					    <kendo:dataSource-schema-model-field name="reSolvedDate" type="string">
					    
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="mobileNo" type="string"/>
					    <kendo:dataSource-schema-model-field name="emailId" type="string"/>							
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
	</kendo:grid>
	<div id="alertsBox" title="Alert"></div>

	
	<div id="myModal"  style="display: none; width: 458px;" >
	
   		
   			
     		           
     		        	<form:form id="helpDeskFormid" action=""  modelAttribute="bHelpDesk"  role="form" method="post" style="line-height: 30px;">
						
					<%-- 	<div>
						Help Topics:<br/>
						<div style="padding-left: 75px;">
						<form:checkbox path="helpTopic"  value="wrong Bill Amt," cssClass="checkbox-inline" name="box1" id="checkbox1" />Wrong Bill Amount<br>
					    <form:checkbox path="helpTopic"   value="payment Not Reflecting," cssClass="checkbox-inline" id="checkbox2" />Payment Not Reflecting<br>	
                        <form:checkbox path="helpTopic"   value="bill Not Received," cssClass="checkbox-inline" id="checkbox3"/>Bill Not Received<br>
                        <form:checkbox path="helpTopic" value="online Payment Not Reflecting," cssClass="checkbox-inline" id="checkbox4"/>Online Payment Not Reflecting<br>
                        <form:checkbox path="helpTopic"  value="payment And Ledger Amount Not Matching" cssClass="checkbox-inline" id="checkbox5"/>Payment And Ledger Not Matching
                       
					    </div>
					    
					    
					    
					   <div class="checkbox-list" style="padding-left: 75px;">
					   <label  class="col-md-3 control-label"></label>
					   <form:checkbox path="helpTopic"  value="other" cssClass="checkbox-inline" id="radio1" />Other/Miscellaneous
					   <div hidden="true" id="checkBoxs" style=" padding-left: 10px;">
					   <form:select path="otherHelpTopic" id="topic">
					   <form:option value="" >select</form:option>
					   <form:option value="Tenant Not Getting Bills" id="v1">Tenant Not Getting Bills</form:option>
					   <form:option value="Change My EmailID">Change My EmailID</form:option>
					   </form:select>
					   </div>	
					   </div>
					
					
						
					    <div class="form-group" >
					    	<label class="col-md-3 control-label">Issue Details </label>
					    <div class="col-md-6" style="width: 200px; padding-left: 75px;">
							<form:textarea class="form-control" path="issue_Details" id="issueDetails" rows="3" />
						</div>
					    </div>
					      
					    <div class="form-group" >
						<div class="col-md-6">
					       <label class="col-md-3 control-label">Status</label>
						<form:input path="status" name="status" id=""></form:input>
						<form:select path="status" id="options" style="margin-left: -39px;">
						 <form:option value="">-- select an option --</form:option>
						 <form:option value="inProgress">InProgress</form:option>
						 <form:option value="closed">Closed</form:option>
						</form:select>
						</div>
					    </div> 
					     
					     <div class="form-group" >
						<label class="col-md-3 control-label">Remarks </label>
						<div class="col-md-6" style="width: 200px; padding-left: 75px;">
						<form:textarea path="remarks" name="remarks" id="Remarks" rows="3"/>
						</div>
					    </div>  
					      
					    <div class="form-group" >
						<label class="col-md-3 control-label">Resolved Date </label>
						<div class="col-md-3" style="padding-left: 75px;">
						<form:input path="reSolvedDate" name="reSolvedDate"  id="datepicker"/>
						</div>
					    </div> 
					
					    <div class="margin-top-10">
						<input class="btn green" type="submit" id="getvalues" value="Save">
						
					    </div>
					
					</div>
					 --%>
					 
					 	<table>
					 	<tr >
					 	<td>Query Topics:</td>
						<td>
						<form:checkbox path="helpTopic"  value="Payment Done to RWA but not available for Token Generation," cssClass="checkbox-inline" id="checkbox1" />Payment&nbsp;Done&nbsp;to&nbsp;RWA&nbsp;but&nbsp;not&nbsp;available&nbsp;for&nbsp;Token&nbsp;Generation<br>
					    <form:checkbox path="helpTopic"   value="Online Payment Made but not available for Token Generation," cssClass="checkbox-inline" id="checkbox2" />Online&nbsp;Payment&nbsp;Done&nbsp;but&nbsp;not&nbsp;available&nbsp;for&nbsp;Token&nbsp;Generation<br>	
                        <form:checkbox path="helpTopic"   value="Payment Not showing in the Payment History," cssClass="checkbox-inline" id="checkbox3"/>Payment&nbsp;Not&nbsp;showing&nbsp;in&nbsp;the&nbsp;Payment&nbsp;History
                       </td>
					    </tr>
					    
					   <tr>
					   <td></td>
					  <td ><form:checkbox path="otherHelpTopic"  value="other" cssClass="checkbox-inline" id="radio1" />Other/Miscellaneous</td>
					  </tr>
					  <tr hidden="true" id="checkBoxs">
					  <td></td>
					   <td  >
					   <form:select path="otherHelpTopic" id="topic">
					   <form:option value="" >select</form:option>
					   <form:option value="Change My MobileNo" id="v1">Change My MobileNo</form:option>
					   <form:option value="Change My EmailID" id="v2">Change My EmailID</form:option>
					   </form:select>
					   </td>	
					   </tr>
					
					
						
					    <tr >
					    	<td style="height: 35px;">Issue Details: </td>
					    <td >
							<form:textarea class="form-control" path="issue_Details" id="issueDetails" rows="2" />
						</td>
					    </tr>
					      
				 	    <tr >
						
					       <td style="height: 35px;">Status :</td>
					       <td>
						<form:input path="status" name="status" id=""></form:input>
						<form:select path="status" id="options" style="margin-left: -145px;">
						 <form:option value="">-- select an option --</form:option>
						 <form:option value="inProgress">InProgress</form:option>
						 <form:option value="closed">Closed</form:option>
						</form:select>
						</td>
					    </tr> 
					     
					     <tr >
						<td style="height: 35px;">Remarks :</td>
						<td>
						<form:textarea path="remarks" name="remarks" id="Remarks" rows="2"/>
						</td>
					    </tr>  
					      
					    <tr>
						<td>Resolved Date :</td>
						<td style="height: 50px;">
						<form:input path="reSolvedDate" name="reSolvedDate"  id="datepicker"/>
						</td>
					    </tr> 
					
					    <tr>
						<td><button type="submit" id="getvalues" value="Save">Save</button></td>
					    </tr> 
					
					</table>
					
				</form:form>
     
		
		
</div> 

<script>

$("#helpDeskFormid").submit(function(e) {
	e.preventDefault();
});

var urId = "";

function onChange(arg) 
{
	 $("#checkbox1").attr("checked", false);
	 $("#checkbox2").attr("checked", false);
	 $("#checkbox3").attr("checked", false);
	 $("#radio1").attr("checked", false);
	 if($("#radio1").attr("checked", false)){
		 $("#checkBoxs").hide();
	 }
	 //$("#v1").val("");
	 //$("#v2").val("");
	
	 var gview = $("#helpDeskgrid").data("kendoGrid");
	 var selectedItem = gview.dataItem(gview.select());
	 
	 urId = selectedItem.id;
	// alert(urId);
	 var hp=selectedItem.helpTopic;


	var array=hp.split(",");
	//alert(array);
	var i;
	
	for (i = 0; i < array.length-1; i++) {
	
		if(array[i].trim() == "Payment Not showing in the Payment History"){
	    	 $("#checkbox3").attr("checked", true);
		}
		
		//alert(array[i].trim());
		if(array[i].trim() == "Payment Done to RWA but not available for Token Generation"){
			$("#checkbox1").attr("checked", true);	
		} 
		
		if(array[i].trim() == "Online Payment Done but not available for Token Generation"){
    	 $("#checkbox2").attr("checked",  true);	
		}
		
	
  
	}
	
	var details=selectedItem.issue_Details;

	if(details!=null){
	  $("#issueDetails").val(details);
	}
	var otherTopics=selectedItem.otherHelpTopic;
	
	if(otherTopics == "Change My MobileNo"){
	$("#radio1").attr("checked", "checked");
	
	
	 if(($("#radio1").is(":checked")) )
     {
		$("#topic").val(otherTopics);
     	$("#checkBoxs").show();
     	
     }else{
     	$("#checkBoxs").hide();
     	}
	}
	
	 if(otherTopics == "Change My EmailID"){
		$("#radio1").attr("checked", "checked");
		
		
		 if(($("#radio1").is(":checked")) )
	     {
			 $("#topic").val(otherTopics);
	     	$("#checkBoxs").show();
	     }else{
	     	$("#checkBoxs").hide();
	     	}
		} 
}

function billingPaymentBound(e) 
{	/* Coupan Generated */
	/* var data = this.dataSource.view(), row;
	var grid = $("#helpDeskgrid").data("kendoGrid");
	for (var i = 0; i < data.length; i++) {
		row = this.tbody.children("tr[data-uid='" + data[i].uid + "']");
		var parentStatus = data[i].helpTopic;
		alert(parentStatus);
		//var parentStatus = data[i].parentStatus;
		var currentUid = data[i].uid;
		var array=parentStatus.split(",");
		//alert(array);
		var i;
		for (i = 0; i < array.length-1; i++) {
			//alert(array[i].trim());
			if(array[i].trim() == "Payment Done to RWA but not available for Token Generation"){
				$("#checkbox1").attr("checked", "checked");	
			}
			if(array[i].trim() == "Online Payment Done but not available for Token Generation"){
	    	 $("#checkbox2").attr("checked", "checked");	
			}
			if(array[i].trim() == "Payment Not showing in the Payment History"){
	    	 $("#checkbox3").attr("checked", "checked");
		}
	  
		}
		var details=data[i].issue_Details;

		if(details!=null){
		  $("#issueDetails").val(details);
		}
		var otherTopics=data[i].otherHelpTopic;
		
		if(otherTopics == "Change My MobileNo"){
		$("#radio1").attr("checked", "checked");
		
		
		 if(($("#radio1").is(":checked")) )
	     {
			$("#topic").val(otherTopics);
	     	$("#checkBoxs").show();
	     	
	     }else{
	     	$("#checkBoxs").hide();
	     	}
		}
		
		 if(otherTopics == "Change My EmailID"){
			$("#radio1").attr("checked", "checked");
			
			
			 if(($("#radio1").is(":checked")) )
		     {
				 $("#topic").val(otherTopics);
		     	$("#checkBoxs").show();
		     }else{
		     	$("#checkBoxs").hide();
		     	}
			}
		 if(parentStatus=="Token Generated"){
			var currenRow = grid.table.find("tr[data-uid='" + currentUid +"']");
			var approveButton =$(currenRow).find(".k-grid-edit");
				approveButton.hide();
		} 
	} */
}

 function HelpTopics(){
	 
	 
	 $(".k-edit-field").hide();
	// $("#options").val("");
	// $("#datepicker").val("");
		 var todcal = $("#myModal");
			
			todcal.kendoWindow({
				width : "auto",
				height : "auto",
				modal : true,
				draggable : true,
				position : {
					top : 100
				},
				title : "Issue Details"
			}).data("kendoWindow").center().open();

			todcal.kendoWindow("open");
 }
 
 
 $(document).ready(function(){
	 
	 //$( "#datepicker" ).datepicker();
	 $("#datepicker").kendoDatePicker({
			
		});
		
		document.getElementById("radio1").onclick = function()
	    {
	        if(this.checked == true)
	        {
	        	$("#checkBoxs").show();
	        }else{
	        	$("#topic").val("");
	        	$("#checkBoxs").hide();
	        	}
	       
	        
	    }; 
		
		
	});
 
 var string=""; 
 $('#getvalues').click(function () {
 	
       if($("#checkbox1:checked").val()){
 	var b1=$("#checkbox1:checked").val();
 	string=b1;
 	//alert(string);
       }
       
       if($("#checkbox2:checked").val()){
 	var b2=$("#checkbox2:checked").val();
 	   string=string+b2;
 	   //alert(string);
       }
       if($("#checkbox3:checked").val()){
 	var b3=$("#checkbox3:checked").val();
 	string=string+b3;
 	//alert(string);
       }
     /*   if($("#checkbox4:checked").val()){
 	var b4=$("#checkbox4:checked").val();
 	string=string+b4;
 	//alert(string);
       }
       if($("#checkbox5:checked").val()){
 	var b5=$("#checkbox5:checked").val();
 	  string=string+b5;
 	  //alert(string);
       }
        */
       
       var details=$("#issueDetails").val();
       
       var otherTopics=$("#topic").val();
      // alert(otherTopics);
       
       var status=$("#options").val();
      
       var remarks=$("#Remarks").val();
   
       var responseDate=$("#datepicker").val();
  
      
       $.ajax({
 			url : "./saveHelpDeskData/"+urId,
 			type : 'GET',
 			dataType : "json",
 			data : {
 				string : string,
 				details: details,
 				otherTopics: otherTopics,
 				status:status,
 				remarks:remarks,
 				responseDate:responseDate,
 			},
 			async : false,
 			contentType : "application/json; charset=utf-8",
 			success : function(result) {

 				deskclose();
				/* var grid = $("#helpDeskgrid").getKendoGrid();
				var data = new kendo.data.DataSource();
				
				grid.refresh(); */
 				window.location.reload();
 			},

 		});

 });
 
 function deskclose() {

		var todcal = $("#myModal");

		todcal.kendoWindow({
			width : "auto",
			height : "auto",
			modal : true,
			draggable : true,
			position : {
				top : 100
			},
			title : ""
		}).data("kendoWindow").center().close();
		todcal.kendoWindow("close");
	}
 
 $("#helpDeskgrid").on("click",".k-grid-queryTemplatesDetailsExport", function(e) {
	  window.open("./QueryTemplate/QueryTemplatesDetailsExport");
});
</script>