<%@include file="/common/taglibs.jsp"%>
<c:url value="/visitorwizard/uploadfile" var="uploadfile" />
<c:url value="/visitorwizard/create" var="createUrl" />
<c:url value="/visitorvisits/readcontactNo" var="readContact" />
<c:url value="/visitorvisits/readblocks" var="propertynameURL" />
<c:url value="/visitorvisits/readTowerNames" var="towerNames" />
<c:url value="/visitorparking/parkingslotNo" var="readSlotNo" />
<c:url value="/visitor/imageUpload" var="imageUploadUrl" />
<c:url value="/visitorwizard/getaccessCardNo" var="readAccessCardNo" />



<div class="wrapper">
	<div class="fluid">
		<div class="widget grid6">
			<div class="formRow">



				<div class="grid5">
					<input type="text" id="visitorId" name="visitorId" hidden="true" onkeyup="allowNumber(this)"
						placeholder="Enter Visitor Id"  />
				</div>

				<div class="grid6">
					<button id="VisitorBtn" onclick="btnClickToSearch()"
						class="k-button" 
						 >Search Visitor</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input
						type="button" id="VisitorBtnUpdate" onclick="btnUpdateClick()"
						class="k-button" value="Exit Visitor" /> 
						<div class="titleOpt">
					<button onclick="window.location.reload(true);" class="k-button"
						style="height: 34px;" >
						<b>Clear Page</b>
					</button>

				</div>
				</div>

			</div>

		</div>
	</div>
</div>



<!--------------------------------------------------------------------------------->
	<div class="wrapper">
	  <div class="fluid">
		<div class="widget grid6">
		  <div class="formRow">	
			<div class="grid5">
				<input type="text" id="visitorContactNo" name="visitorContactNo" hidden="true" onkeyup="allowNumber(this)" placeholder="Enter Contact No"  />
				<input type="text" id="visitorPassword" name="visitorPassword" hidden="true" onkeyup="allowNumber(this)" placeholder="Enter Visitor Pasword"  />
		    </div>
			<div class="grid6">
			   <button id="searchVisitorBtn" onclick="searchPreRegisteredVisitor()" class="k-button">Search Pre-Registered Visitor</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			      <!-- <input type="button" id="preRegVisitorBtnUpdate" onclick="findPreRegisteredVisitorClick()" class="k-button" value="Exit Visitor" /> --> 
			</div>
		  </div>
	    </div>
	 </div>
	</div>
<!----------------------------------------------------------------------------------->

<div id="divHidden" style="display: none;">
	<div class="grid6">
		<input type="text" id="vvId" name="vvId" />
	</div>
	<div class="grid6">
		<input type="text" id="vpId" name="vpId" />
	</div>

</div>

<!-- ****************************************************************************************** -->

<!-- ****************************************************************************************** -->
<div id="barcodedisplay" style="width: 300px; display: none;"></div>

<div class="wrapper">
	<br>
	<div class="fluid">
		<div class="widget grid6">
			<!-- <div class="whead"> -->
			<div class="whead">
				<h6 style="font-size: 15px;">Visitor Record Form &nbsp;:</h6>
				<div class="titleOpt">
					<button onclick="window.location.reload(true);" class="k-button"
						style="height: 40px;">
						<b>Clear Page</b>
					</button>

				</div>

			</div>

			<div class="gallery">
			
			<div class="formRow" id="divImageShow">
					<div class="grid5">
						<label>Visitor Image</label>
					</div>
					<div class="grid5">
						<img id="myImage" src="" alt="" width="250px;" border="5" height="220px;" />
					</div>
				</div>
			
				<div class="formRow">
					<div class="grid5">
						<label>Contact Number &nbsp; *</label>
					</div>
					<div class="grid5">
						<input id="vmContactNo" type="text" name="vmContactNo" onkeyup="allowNumber(this)"
							required="required"  autofocus
							placeholder="Visitor Contact No." />
					</div>
				</div>
				<div class="formRow">
					<div class="grid5">
						<label>Visitor Name &nbsp; *</label>
					</div>
					<div class="grid5">
						<input id="vmName" type="text" name="vmName" required onkeyup="allowAlphabet(this)"
							 placeholder="Visitor Name" />
					</div>
				</div>
				<div class="formRow">
					<div class="grid5">
						<label>Address &nbsp; *</label>
					</div>
					<div class="grid5">
						<textarea id="vmFrom" placeholder="Address " name="textarea"
							 cols="" rows="4"></textarea>
					</div>
				</div>

				<div class="formRow">
					<div class="grid5">
						<label>Gender &nbsp; *</label>
					</div>
					<div class="grid5">
						<input id="gender" name="gender" />
					</div>
				</div>


				<div class="formRow">
					<div class="grid5">
						<label>Category &nbsp; *</label>
					</div>
					<div class="grid5">
						<input id="category" name="category" />
					</div>
				</div>
				<div class="formRow">
					<div class="grid5">
						<label>Block Name &nbsp; *</label>
					</div>
					<div class="grid5">
						<kendo:dropDownList name="blocks" dataTextField="blockName"
							dataValueField="blockName" optionLabel="Select Block"
							autoBind="false">
							<kendo:dataSource serverFiltering="true">
								<kendo:dataSource-transport>
									<kendo:dataSource-transport-read url="${towerNames}" type="GET"
										contentType="application/json" />
									<kendo:dataSource-transport-parameterMap>
 function(options){return JSON.stringify(options);}
    </kendo:dataSource-transport-parameterMap>
								</kendo:dataSource-transport>
							</kendo:dataSource>
						</kendo:dropDownList>
					</div>
				</div>





				<div class="formRow">
					<div class="grid5">
						<label>Property Number &nbsp; *</label>
					</div>
					<div class="grid5">
						<kendo:dropDownList name="property_No" cascadeFrom="blocks"
							dataTextField="property_No" dataValueField="property_No"
							optionLabel="Select Property No.">
							<kendo:dataSource>
								<kendo:dataSource-transport>
									<kendo:dataSource-transport-read url="${propertynameURL}"
										type="GET" contentType="application/json" />
									<kendo:dataSource-transport-parameterMap>
 function(options){return JSON.stringify(options);}
  </kendo:dataSource-transport-parameterMap>
								</kendo:dataSource-transport>
							</kendo:dataSource>
						</kendo:dropDownList>
					</div>
				</div>


				<div class="formRow">
					<div class="grid5">
						<label>Purpose &nbsp; *</label>
					</div>
					<div class="grid5">

						<textarea id="vpurpose" placeholder="purpose of visit"
							name="textarea" cols="" rows="2"></textarea>

					</div>
				</div>

				<div class="formRow ">
					<div class="grid5">
						<label>Entry Method</label>
					</div>
					<div class="grid5">

						<input id="accesscardradio" type="radio" name="accesscard_barcode"  value="acNorequired"
							>&nbsp;Access Card &nbsp; <input
							id="barcodeslipradio" type="radio" name="accesscard_barcode"  value="barcoderequired"
							 onchange="barcodefunction()">&nbsp;Barcode Slip
					</div>
				</div>




				<div class="formRow " id="barcodediv">
					<div class="grid5">
						<label>Bar Code Slip</label>
					</div>
					<div>
						<span id="barcodeslip"></span>

					</div>
				</div>


				<div class="formRow " id="accesscarddiv">
					<div class="grid5">
						<label>Access Card No.</label>
					</div>
					<div class="grid5">

						<kendo:autoComplete name="acId" dataTextField="acNo"
							dataValueField="acId" optionLabel="Select Access Card"
							autoBind="false">
							<kendo:dataSource serverFiltering="true">
								<kendo:dataSource-transport>
									<kendo:dataSource-transport-read url="${readAccessCardNo}"
										type="GET" contentType="application/json" />
									<kendo:dataSource-transport-parameterMap>
 function(options){return JSON.stringify(options);}
    </kendo:dataSource-transport-parameterMap>
								</kendo:dataSource-transport>
							</kendo:dataSource>
						</kendo:autoComplete>
						
					</div>
				</div>






				<div class="formRow ">



					<div class="grid5">
						<label>Parking  </label>
					</div>
					<div class="grid5">
						
						<input id="parkingreuiredradio" type="radio" name="parkingrequired"  value="required">&nbsp;Required &nbsp;
						<input id="parkingnotrequiredradio" type="radio" name="parkingrequired"   value="notrequired" >&nbsp; Not Required

					</div>

				</div>





				<div class="formRow" id="vehicleNodiv">
					<div class="grid5">
						<label>Vehicle No:</label>
					</div>
					<div class="grid5">

						<input id="vehicleNo" type="text" onkeyup="AllowAlphabetNum(this)"
							 name="vehicleNo" required />
					</div>
				</div>


				<div class="formRow" id="psSlotNodiv">
					<div class="grid5">
						<label>Parking Slot No:</label>
					</div>
					<div class="grid5">

						<kendo:dropDownList name="psSlotNo" dataTextField="psSlotNo"
							dataValueField="psSlotNo" optionLabel="Select-Parking-Slot">
							<kendo:dataSource>
								<kendo:dataSource-transport>
									<kendo:dataSource-transport-read url="${readSlotNo}" type="GET"
										contentType="application/json" />
									<kendo:dataSource-transport-parameterMap>
 function(options){return JSON.stringify(options);}
  </kendo:dataSource-transport-parameterMap>
								</kendo:dataSource-transport>
							</kendo:dataSource>
						</kendo:dropDownList>
						<input type="text" id="parkingrecodrHide"  style="display: none;" disabled="disabled">

					</div>
				</div>


				<div class="formRow" id="vpExpectedHoursdiv">
					<div class="grid5">
						<label>Expected Hours:</label>
					</div>
					<div class="grid5">
						<input id="vpExpectedHours" name="vpExpectedHours" maxlength="2" value="0" style="border: 1px solid #CCCCCC; height: 28px; width: auto;" />
					</div>
				</div>
				
				<div class="formRow" id="vpExpectedHoursdiv">
					<div class="grid5">
						<label></label>
					</div>
				</div>

			</div>
		</div>






		<!-- </form> -->


		<div class="widget grid6" style="height: 500px; " id="divImagePlugin" >
		  <p id="status" style="height: 22px; color: #c00; font-weight: bold;"></p>
			<div id="webcam" align="center">
			   <span>Your WebCam should be here!</span>
			</div>

			<p style="margin-left: 240px">
			   <a href="javascript:webcam.capture();void(0);" onclick="return Insertrecord()"  class="k-button">Add Record/Capture Image</a>
			</p>
			<input type="text" id="vmIdtext" name="vmIdtext" hidden="true" />
		</div>
	</div>
</div>

<div id="dialogBoxforBarcode" hidden="true" title="Visitor BarCode Slip"
	style="text-align: center;"></div>
<div id="imagePopUpDiv" style="display: none;">
	<br>
	<kendo:upload name="files"  multiple="false" upload="uploadExtraData"
		accept="application/pdf,image/png,image/jpeg,image/jpg">
		<kendo:upload-async autoUpload="true" saveUrl="${imageUploadUrl}" />
	</kendo:upload>
</div>
<div id="alertsBox" title="Alert"></div>
<div id="dialogBoxforContacts" title="Associated Contacts"></div>

<!---------------------- START FOR PRE-REGISTERED VISITOR SCRIPT ------------------------>
<script>

var check="";
function searchPreRegisteredVisitor()
{	 
	$("#savePreVisotorRecord").show();
	$("#category").hide();
	//$("#divImagePlugin").hide();	
	var visitorContactNo=$("#visitorContactNo").val();
	var visitorPassword=$("#visitorPassword").val();
	
	
	if($("#visitorContactNo").val()=="")
	{
		alert("Contact number required");
	}
	if($("#visitorPassword").val()=="")
	{
		alert("Visitor Password required");
	}
	else
	{
	    $.ajax
	    ({
		    type:"GET",
		    async : false,
		    data:
		    {
		    	visitorContactNo:visitorContactNo,
		    	visitorPassword : visitorPassword
		    },
		    url:"./visitorwizard/searchPreRegisteredVisitors",
            success:function(response)
            {
            	if(response == "")
                {
            		alert("No Pre-Registered Visitor Found");
            		return false;
            	}
            	for(var s = 0; s<response.length; s++)
		 		{
            		responseVal = response[s];
            		$('#vmName').val(responseVal.vmName);
            		$('#vmContactNo').val(responseVal.vmContactNo);
            		$('#vmFrom').val(responseVal.vmFrom);
            		$('#gender').val(responseVal.gender);
            		dropdownlistblockName.value(responseVal.blockName);
            		dropdownlistproperty.value(responseVal.property_No);
            		dropdownlistgender.search(responseVal.gender);
            		var parkingRequired = responseVal.parkingRequired;
            		if(parkingRequired === 'Yes')
            	    {
            			check="required";
            			$('input[id="parkingreuiredradio"]').prop('checked',true);
            			$("#psSlotNodiv").show();
                        $("#vehicleNodiv").show();
                    	$("#vpExpectedHoursdiv").show();
            	    }
            		else
            		{
            			$('input[id="parkingreuiredradio"]').prop('checked',false);
            		}
		 		}
            	 document.getElementById('vmName').readOnly =true;
                 document.getElementById('vmContactNo').readOnly =true;
                 document.getElementById('vmFrom').readOnly =false;
                 document.getElementById('vehicleNo').readOnly =false;
                     
                 dropdownlistgender.enable(true);
                 dropdownlistcategory.enable(true);
                 dropdownlistblockName.enable(false);
                 dropdownlistproperty.enable(false);
              }
	   });
	 }
	}
 
var visitorName = "";
var visitorContactNo = "";
var visitorFrom = "";
var visitorGender = "";
var visitorVehicleNo = "";
var propertyNo = "";
var psSlotno = "";
var vpurpose = "";

function savePreRegisterVisotorRecord()
{
	visitorName = $("#vmName").val();
	visitorContactNo = $("#vmContactNo").val();
	visitorFrom = $("#vmFrom").val();
	visitorGender = $("#gender").val();
	visitorVehicleNo = $("#vehicleNo").val();
	propertyNo = $("#property_No").val();
	psSlotno = $("#psSlotNo").val(); 
	vpurpose = $("#vpurpose").val();
	
	 $.ajax
	 ({
		  type : "POST",
		  url : "./preRegisteredVisitor/createPreRegVisitor",
		  dataType:'text',
		  data : 
		  {
			  visitorName : visitorName,
			  visitorContactNo : visitorContactNo,
			  visitorFrom : visitorFrom,
			  visitorGender : visitorGender,
			  visitorVehicleNo : visitorVehicleNo,
			  propertyNo : propertyNo,
			  psSlotno : psSlotno,
			  vpurpose : vpurpose,
	     },
	     success : function(response) 
	     {
	    	
	     }
	 });
}
</script>
<!----------------------  END FOR PRE-REGISTERED VISITOR SCRIPT ------------------------>

<!-- ########################script for image capturing using jquery##################################### -->
<script>

$("#vpExpectedHours").kendoNumericTextBox({
    min: 1
});

var vmId="";
var pos = 0;
var ctx = null;
var cam = null; 
var image = null;
var filter_on = false; 
var filter_id = 0;
var stralert="";
var radioValueParking ="";
var radioValue ="";

var dropdownlistgender = "";
var dropdownlistcategory="";
var dropdownlistproperty_No="";
var dropdownlistblockName="";
var dropdownlistproperty="";
var dropdownlistpsSlotNo="";

 $(document).ready(function() {
		// Handler for .ready() called.
		
		// $("#vpExpectedHours").kendoNumericTextBox();
		$("#savePreVisotorRecord").hide();	
		$("#psSlotNodiv").hide();
		$("#vehicleNodiv").hide();
		$("#vpExpectedHoursdiv").hide();
		$("#divImageShow").hide();
		$("#divHidden").hide();
		$("#accesscarddiv").hide();
		$("#barcodediv").hide();
		$("#imagePopUpDiv").hide();
		$("#parkingrecodrHide").hide();
		$("#mainImageCaptureDiv").hide();
		
	    var data = [
	        { text: "Select Gender", value: "" },
	        { text: "Male", value: "Male" },
	        { text: "Female", value: "Female" }
	    ];

	    // create DropDownList from input HTML element
	   $("#gender").kendoDropDownList({
	        dataTextField: "text",
	        dataValueField: "value",
	        dataSource: data,
	        index: 0
	    });

	   dropdownlistgender = $("#gender").data("kendoDropDownList");
	  

	var data=[

	{text:"Select Category",value:""},
	{text:"F&B",value:"F&B"},
	{text:"Services",value:"Services"},
	{text:"Courier",value:"Courier"},
	{text:"Relative/Friend",value:"Relative/Friend"},
	{text:"Others",value:"Others"}
	          
	          ];
	
	

	$("#category").kendoDropDownList({

		dataTextField:"text",
		dataValueField:"value",
		dataSource:data,
		index:0
	});
	   dropdownlistcategory = $("#category").data("kendoDropDownList");
	   dropdownlistblockName = $("#blocks").data("kendoDropDownList");
	   dropdownlistproperty  = $("#property_No").data("kendoDropDownList");
	   dropdownlistpsSlotNo = $("#psSlotNo").data("kendoDropDownList");
	   
	});

 /* -------------------Method to Search Visitor record Based on Visitor Id------------------ */
 
function btnClickToSearch1(x){
	
	if(x[7]=="N/A")
		{
		
	 $("#dialogBoxforBarcode").kendoBarcode({
	        value: x[4],
	        type: "code128",
	        width: 280,
	        height: 100
	    }); 
		 $('#dialogBoxforBarcode').dialog({
	         	width: 475,
	         	position: 'center',
				modal : true,
				 buttons: {
					 "Print with all Vistor  Information": function() {
						
						 var canvas=document.getElementsByTagName("canvas");
							var image=new Image();
							image.id="pic";
							image.src=canvas[0].toDataURL("image/png"); 
							///var divToPrint=document.getElementById("assetslip");
							var popupWin=window.open('','_blank','width=500 height=500');
							popupWin.document.open();	
							popupWin.document.write("<html><body onload=window.print()><div id=printpopup style=background-image:url(resources/images/b3.png)><table width=500px><th colspan=2 text-align:center>Visitor Information</th></tr><tr ><tr><td>Visitor Id </td><td>"+x[0]+"</td></tr><tr><td><label>Visitor Name : </label></td><td>"+x[1]+"<tr><td>Phone No. :</td><td>"+x[2]+"</td></tr><tr><td>Gender</td><td>"+x[3]+"</td></tr><tr><td>Property To Visit : </td><td>"+x[4]+"</td></tr><tr><td>Parking Slot</td><td>"+x[5]+"</td></tr><tr><td>Vehicle Number</td><td>"+x[6]+"</td></tr><tr><td>Access Card</td><td>"+x[7]+"</td><br><br><br></tr><tr><td>Bar Code Slip</td><td id='test'><image src="+image.src+" /></td></tr></table></div></html>");
							popupWin.document.close();
							 window.location.reload(true);
					  $( this ).dialog( "close" );
					 },
					 Cancel: function() {
						 window.location.reload(true);
					 $( this ).dialog( "close" );
					 }
				}
			}); 
		
		}
	else
		
		{
	
		var popupWin=window.open('','_blank','width=500 height=500');
		popupWin.document.open();	
		popupWin.document.write("<html><body onload=window.print()><div id=printpopup><table width=500px><th colspan=2 text-align:center>Visitor Information</th></tr><tr ><tr><td>Visitor Id </td><td>"+x[0]+"</td></tr><tr><td><label>Visitor Name : </label></td><td>"+x[1]+"<tr><td>Phone No. :</td><td>"+x[2]+"</td></tr><tr><td>Gender</td><td>"+x[3]+"</td></tr><tr><td>Property To Visit : </td><td>"+x[4]+"</td></tr><tr><td>Parking Slot</td><td>"+x[5]+"</td></tr><tr><td>Vehicle Number</td><td>"+x[6]+"</td></tr><tr><td>Access Card</td><td>"+x[7]+"</td></tr></table></div></html>");
		popupWin.document.close();
		
		 window.location.reload(true);
		
		}
		 
		 
	 
	 
 }
 
 
function btnClickToSearch(){
	 
	
	$("#divImagePlugin").hide();
	securityCheckForActions("./visitormanagement/visitorwizard/searchButton"); 
	var visitorID=$("#visitorId").val();
	if($("#visitorId").val()==""){
		alert("Please Enter the Visitor Id");
	}else{
	
	$.ajax({
	    type:"GET",
	    data:{
	    visitorId:visitorID	
	    },
	    url:"./visitorwizard/searchVisitorVisitsRecord",
                    success:function(response){
            
                    	if(response=="[object XMLDocument]"){
                    	alert("No Visitor Record is available");
                    	window.location.reload(true);
                    
                    	}
                    	else{
                    		var vmId=$("#visitorId").val();
                    	    
                    	 $("#myImage").attr("src","<c:url value='/vistor/getVisitorImageBasedOnSearch/" + vmId +"'/>");
                    	 $("#divImageShow").show();
                         $('#vmContactNo').val(response.visitor.vmContactNo);
                        $('#vmName').val(response.visitor.vmName);
                         $('#vmFrom').val(response.visitor.vmFrom);
                        
                         $('#property_No').val(response.property.property_No);
                         $('#vpurpose').val(response.vpurpose);
                         $("#vvId").val(response.vvId);
                         $("#vpId").val(response.vvstatus);
                        
                         document.getElementById('vmName').readOnly =true;
                         document.getElementById('vmContactNo').readOnly =true;
                         document.getElementById('vmFrom').readOnly =true;
                         document.getElementById('vehicleNo').readOnly =true;
                         document.getElementById('vpurpose').readOnly =true;
                         
                         
                             dropdownlistgender.search(response.visitor.gender);
                             dropdownlistcategory.search(response.category);
                             dropdownlistblockName.value(response.property.blocks.blockName);
                             dropdownlistproperty.value(response.property.property_No);
                             
                             dropdownlistgender.enable(false);
                             dropdownlistcategory.enable(false);
                             dropdownlistblockName.enable(false);
                             dropdownlistproperty.enable(false);
                             
                            
                            if(response.accessCards!=null){
                            	$('input[id="accesscardradio"]').prop('checked',true);
                            	$("#accesscarddiv").show();
                            	 $('#acId').val(response.accessCards.acNo);
                            	 document.getElementById('acId').readOnly =true;
                            }else{
                            	$('input[id="barcodeslipradio"]').prop('checked',true);
                            }
                            if(response.parkingSlots!=null){
                            	$('input[id="parkingreuiredradio"]').prop('checked',true);
                            	$("#parkingrecodrHide").val(response.parkingSlots.psSlotNo);
                            	$("#parkingrecodrHide").show();
                            	$("#psSlotNodiv").show();
                    			$("#vehicleNodiv").show();
                    			$("#vpExpectedHoursdiv").show();
                    			
                    			$("#psSlotNo").parent().hide();
                    			
                    			 dropdownlistpsSlotNo.search(response.parkingSlots.psSlotNo);
                    			 $("#vehicleNo").val(response.vehicleNo);
                                 //$("#vpExpectedHours").val(response.vpExpectedHours);
                                 
                    			 var numerictextbox = $("#vpExpectedHours").data("kendoNumericTextBox");
                                 
                    			// get the value of the numerictextbox.
                    			var value = numerictextbox.value();
                    			             
                    			// set the value of the numerictextbox.
                    			numerictextbox.value(response.vpExpectedHours);
                    			numerictextbox.enable(false);
                                 
                            }else{
                            	$('input[id="parkingnotrequiredradio"]').prop('checked',true);
                            }
                             
                    }
                    }
	});
	}
	}
  /* -------------------Method to Update Visitor record Based on Visitor Id------------------ */
  
  function btnUpdateClick(){
	  
	  
	 // securityCheckForActions("./visitormanagement/visitorwizard/exitButton");
	 
	 	 var vvId=$("#vvId").val();
		 var vpId=$("#vpId").val();
		 var visitorID=$("#visitorId").val();
		 
	   var result=securityCheckForActionsForStatus("./visitormanagement/visitorwizard/exitButton");   
	   if(result=="success"){
	  
				
			
				 if($("#visitorId").val()==""){
					 alert("please Enter the Visitor Id");
				 }
				 else
				 {
					 /* 	if(vpId=="OUT"){
					 		alert("Visitor Already Exited");
					 	}
					 	else
					 	{
					  */
						$.ajax({
			        	type:"POST",
			        	data:{
			      			vvId:vvId,
			      			vpId:vpId,
			      			visitorId:visitorID
			      	
			        	},
			       		 url:"./visitorwizardUpdatesearchRecord/updateSearchRecord",
			        	success:function(response){
			      	  			alert(response);
			      				window.location.reload(true);
			       			 }
						});
					 
				 } 
	 	}
  }
    
  /* -------------------Method to create Visitor Record------------------ */
  
  function Insertrecord(){

	  securityCheckForActions("./visitormanagement/visitorwizard/createButton");
      var block=$('#blocks').val();
      var property=$("#property_No").val();
      var vmContactNo = $("#vmContactNo").val();
      var vmName = $("#vmName").val();
      
      var vmFrom = $("#vmFrom").val();
      var property_No = $("#property_No").val();
      var gender=$("#gender").val();
      var category=$("#category").val();
      var vpurpose = $("#vpurpose").val();
      var vvstatus = $("#vvstatus").val();
      
    
      
            if(vmContactNo==""){
           	 stralert=stralert+"Contact Number is required \n";
            
            }
            if(vmName==""){
           	 stralert=stralert+"Visitor Name is Required \n";
            }
            if(vmFrom==""){
           	 
           	 stralert=stralert+" Visitor Address is required \n";
           	 
            }
            if(gender==""){
           	 stralert=stralert+"Gender is Required \n";	 
            }
            if(property_No==""){
           	 stralert=stralert+"Property Number is Required \n";	 
            }
            if(vpurpose==""){
           	 stralert=stralert+"Purpose of Visitor is required \n";
            }
            
            if(category==""){
             	 stralert=stralert+"Category is required \n";
           }
           
           
           if(check=="required"){
           	
           	
          	 var psSlotNo=$("#psSlotNo").val();
          	 //alert(psSlotNo);
          	 var vehicleNo=$("#vehicleNo").val();
          	 if(vehicleNo==""){
          		 stralert=stralert+"Vehicle Number of Visitor is required \n";
          	 }
          	 if(psSlotNo=="Select-Parking-Slot" || psSlotNo=="" )
          	 {
          		 stralert=stralert+"Parking Slot  is required \n";
          	 }
           }
            if(radioValue=="acNorequired"){
           	 var acId = $("#acId").val();
           	 if(acId==""){
           	 stralert=stralert+" Access Card Number  is required \n"; 
           	 }
            }
           
            if(stralert==""){
           	 
          
           	 
            }else{
           alert("Following Fields are required----- \n"+stralert);
           stralert="";
           	return false;
            }
            
           if ($("#vpExpectedHours").val() != "") {
       	       var vpExpectedHours = $("#vpExpectedHours").val();
                  var status = $("#status").val();
                  var psSlotNo = $("#psSlotNo").val();	       						
            } else {
   	           var vpExpectedHours = "";
                  var status = "";
                  var psSlotNo = "";	       						
            }
    
     if(stralert==""){  

									 $.ajax({
											  type : "POST",
											  url : "./visitorwizard/create",
											  dataType:'text',
											  data : {
											  vmContactNo : vmContactNo,
											  vmName : vmName,
											  acId : acId,
											  vmFrom : vmFrom,
											  property_No : property_No,
											  vpurpose : vpurpose,
											  vvstatus : vvstatus,
											 vpExpectedHours : vpExpectedHours,
											 status : status,
											 psSlotNo : psSlotNo,
											 gender:gender,
											 category:category,
											 vehicleNo:vehicleNo
										},
										success : function(response) {
											
											
										$("#vmIdtext").val(response);
										$("#mainImageCaptureDiv").show();
										var name=$('input[name="vmName"]').val();     							
		       							
										var phNumber=$('input[name="vmContactNo"]').val();
										var gender=$('input[name="gender"]').val();
										var propertyNo=$('input[name="property_No"]').val();
										var acId=$('input[name="acId"]').val();
										var vehicleNo="";
										if($('input[name="vehicleNo"]').val()==""){
											 vehicleNo="N/A";	
										}else{
										 vehicleNo=$('input[name="vehicleNo"]').val();
										}
										var psslotNo=$("#psSlotNo").val();
										var accessId="";
										var slotNo="";
										var barcodeImage="";
										if(acId==""){
								
										 accessId="N/A";
										 var blockvalue=$('#blocks').val().replace(/[\. ,:-]+/g, "");
										 var propNo=$('#property_No').val().replace(/[\. ,:-]+/g, "");
											 
										 barcodeImage=blockvalue+"/"+propNo+"/"+response;
										 
										}
										else{
											
										  accessId=$("#acId").val();
										  
										}
										if(psslotNo=="Select-Parking-Slot" || psslotNo==""){
							                 slotNo="N/A";
							 		    }else{
								             slotNo=$("#psSlotNo").val();	 
							            }
										var barCodeSlip=$('#blocks').val()+"/"+$('#property_No').val()+"/"+response;
										
										
									
										alert("Vistor's Information Added Successfully")
										
										
										$("#imagePopUpDiv").dialog({
											 resizable: false,
											 modal: true,
											 title: "Visitor Document Upload",
											 height: 400,
											 width: 500,
											 buttons: {
							                 "Print": function() {	
											 if(response.length>0){
												 //alert("test"+response);
															  var visitorlist="<table frame=box bgcolor=white width=450px><tr bgcolor=#0A7AC2>";
													     	  visitorlist=visitorlist+"<tr><td>Visitor Id </td><td>"+response+"</td></tr><tr><td>Visitor Name</td>"+"<td>"+name+"</td></tr>"+"<tr><td>Phone No.</td><td>"+$('input[name="vmContactNo"]').val()+"</td></tr><tr><td>Gender</td><td>"+$('#gender').val()+"</td></tr><tr><td>Property To Visit : </td><td>"+barCodeSlip+"</td></tr><tr><td>Parking Slot</td><td>"+slotNo+"</td></tr><tr><td>Vehicle Number</td><td>"+vehicleNo+"</td></tr><tr><td>Access Card</td><td>"+accessId+"</td></tr>";
													           $("#barcodedisplay").kendoBarcode({
													             value:barCodeSlip,
													             type: "code128"
													          });  
													      		if(acId==""){
														 					 visitorlist=visitorlist+"<tr><td>Bar Code Slip</td><td>"+"<div id=bcTarget1></div>";//+""+"</td></tr>";
														  					 visitorlist = visitorlist + "<script>$(function(){$('#bcTarget1').kendoBarcode({value:'"+barcodeImage+"',type: 'code128', width: '280',height: '100'})});</"+"script>"+"</td></tr></table>";
													            }else{
																			  visitorlist = visitorlist +"</table>";	
														        }	
													      		
													      		$('#dialogBoxforContacts').html("");
												         		$('#dialogBoxforContacts').html(visitorlist);
												         		$("#dialogBoxforContacts").dialog({
												         		resizable: false,
												         		modal: true,
												         		title: "Visitor Information",
												         		height:500,
												         		width: 500,
										         		        buttons: {
										         		        	"Print": function(code,target) {
										         		        		
										         		        		 var arr=[response,name,phNumber,gender,barCodeSlip,slotNo,vehicleNo,accessId];
											         		        		
											         		        		btnClickToSearch1(arr);
										         		        	
										         		        	/* 	  var Text="N/A";
											         		                var canvas = document.getElementsByTagName("canvas");
											         		        	    var image = new Image();
												         		        	image.id = "pic";
												         		        	
												         		        	if(acId==""){
												         		        	
												         		        	image.src = canvas[0].toDataURL("image/png");
												         		        	
											         		        		 }else{
											         		        		  image.src="";
											         		        		 } 
												         		             var divToPrint = document.getElementById('dialogBoxforContacts');
												         		             var popupWin = window.open('', '_blank', 'width=500,height=500');
												         		            
												         		             popupWin.document.write("<html><body onload=window.print()><div id=printpopup style=background-image:url(resources/images/b3.png) ><table width=500px height=450px><tr ><th colspan=2 text-align:center>Visitor Information</th></tr><tr ><tr><td>Visitor Id </td><td>"+response+"</td></tr><tr><td><label>Visitor Name : </label></td><td>"+name+"<tr><td>Phone No. :</td><td>"+phNumber+"</td></tr><tr><td>Gender</td><td>"+gender+"</td></tr><tr><td>Property To Visit : </td><td>"+barCodeSlip+"</td></tr><tr><td>Parking Slot</td><td>"+slotNo+"</td></tr><tr><td>Vehicle Number</td><td>"+vehicleNo+"</td></tr><tr><td>Access Card</td><td>"+accessId+"</td></tr><tr><td>Bar Code Slip</td><td><div id=bcTarget1><img src="+image.src+"alt="+Text+ "/></div> </td></tr></table></div></html");
												         		             popupWin.document.close();
												         		            
												         		             $(this).dialog('close');
												         		            window.location.reload(true); */
										         		        	},
										         		        	
										         		        	
										         		        	"Cancel": function() {
										         		        	
										         		        		$(this).dialog('close');
										         		        		 window.location.reload(true);
										         		          }
										         		          }
					         		                       		 }); 
					         		                             $(".k-upload-button span").text("Browse File..");	
													      		
													      		
											     }else{
											    	 
											     }
											 $(this).dialog('close');
							                  },
								               "Cancel": function() {
							                         $(this).dialog('close');
							                         window.location.reload(true);
								            }
							                        					  
								            }
							                 
						 		           });  
										
										
										
										vmId = response;
										 }
								});
     }     
	}
          
  $(function() {
	  var pos = 0, ctx = null, saveCB, image = [];  
	  var canvas = document.createElement("canvas"); 
	  canvas.setAttribute('width', 320);
	  canvas.setAttribute('height', 240); 
	  if (canvas.toDataURL) {
	  ctx = canvas.getContext("2d");  
	  image = ctx.getImageData(0, 0, 320, 240);
	  saveCB = function(data) { 
	  var col = data.split(";");
	  var img = image; 
	  for(var i = 0; i < 320; i++) {
	  var tmp = parseInt(col[i]); 
	  img.data[pos + 0] = (tmp >> 16) & 0xff;
	  img.data[pos + 1] = (tmp >> 8) & 0xff;
	  img.data[pos + 2] = tmp & 0xff;
	  img.data[pos + 3] = 0xff; pos+= 4;
	  } 
	   if (pos >= 4 * 320 * 240) { 
	 	  alert("Please Wait............");
	 	  if(vmId==""){
	 	  }else{
	  		 ctx.putImageData(img, 0, 0);
	 		 $.post("./visitor/visitorImageCapturedUpload/"+vmId, {type: "data", image: canvas.toDataURL ("image/png")});
	   pos = 0;
	   alert("Image Captured Successfully, Please Upload the document file");	       							
	   } 
	   }
	   };  
	   }  else {  
	 	 
	   saveCB = function(data) {
	   image.push(data);   
	   pos+= 4 * 320;     
	   if (pos >= 4 * 320 * 240) {
	   $.post("./visitor/visitorImageCapturedUpload/"+vmId, {type: "pixel", image: image.join('|')});
	   pos = 0; 
	   } };     
	   }        
	  $("#webcam").webcam({ 
	   width: 320,
	  height: 240,
	  mode: "callback", 
	  swffile: "<c:url value="/resources/jscam_canvas_only.swf" />",
	  onSave: saveCB,
	  onCapture: function () {
	  webcam.save();
	  }, 
	  debug: function (type, string) 
	  {
	  console.log(type + ": " + string);
	  }  
	  
	  });
	  
	 	

	 });  
	  

  $(document).on('change', 'input[name="accesscard_barcode"]:radio', function() {
		 radioValue = $('input[name=accesscard_barcode]:checked').val();
		  if(radioValue=='acNorequired') {
			
			  $("#accesscarddiv").show();
				
				if ($("#accesscarddiv").show() == "true") {
					var acId=$("#acId").val();
					if (acId.length == 0) {
						$("#alertsBox").html("");
	   					$("#alertsBox").html("Please select access card value");
	   					$("#alertsBox").dialog({
	   						modal: true,
	   						buttons: {
	   							"Close": function() {
	   								$( this ).dialog( "close" );
	   							}
	   						}
	   					});
						}
						}
			}
		  else {
			  
			 
			  $("#acId").data("kendoAutoComplete").value("");
				$("#accesscarddiv").hide();
				
		}
	});



$(document).on('change', 'input[name="parkingrequired"]:radio', function() {
	 radioValueParking = $('input[name=parkingrequired]:checked').val();
		  if(radioValueParking=='required') {
			
			  check="required";
			  	$("#psSlotNodiv").show();
				$("#vpExpectedHoursdiv").show();
				$("#vehicleNodiv").show();
				
				if ($("#vehicleNodiv").show() == "true") {
					var vehicleNo=$("#vehicleNo").val();
					if (vehicleNo.length == 0) {
						$("#alertsBox").html("");
	   					$("#alertsBox").html("Please Enter the Vehicle Number");
	   					$("#alertsBox").dialog({
	   						modal: true,
	   						buttons: {
	   							"Close": function() {
	   								$( this ).dialog( "close" );
	   							}
	   						}
	   					});
						}
						}
				
				if ($("#psSlotNodiv").show() == "true") {
					var slotNo=$("#psSlotNo").val();
					if (slotNo.length == 0) {
						$("#alertsBox").html("");
	   					$("#alertsBox").html("Select parking Slot No.");
	   					$("#alertsBox").dialog({
	   						modal: true,
	   						buttons: {
	   							"Close": function() {
	   								$( this ).dialog( "close" );
	   							}
	   						}
	   					});
					}
				}
				
			}
		  else {
			  check="No";
			 
			  $('input[name="vehicleNo"]').val("");
			  $("#psSlotNo").data("kendoDropDownList").value("");

			  $("#psSlotNodiv").hide();
				$("#vpExpectedHoursdiv").hide();
				$("#vehicleNodiv").hide();
				
		}
	});





function uploadExtraData(e) {
	var files=e.files;
	$.each(files,function(){
                               if(this.extension ===".png"){
                                            e.data = {
                            	vmId : vmId
                            	}; 
                                            
                               }
                               else if(this.extension ===".jpg"){
                            	   e.data = {
                            	            vmId : vmId
                            	     };
                               }
                               else if(this.extension ===".jpeg"){
                            	   e.data = {
                            	             vmId : vmId
                                                        };
                               }
                               else if(this.extension ===".pdf"){
                            	   e.data = {
                            	             vmId : vmId
                                                        };
                               }
                               else{
                            	   alert("Only images/pdf files can be uploaded");
                            	   e.preventDefault();
                            	   return false;
                               }
	});
	 
	
}

function allowNumber(txt){
	txt.value = txt.value.replace(/[^0-9\n\r]+/g, '');
} 
function allowAlphabet(txt){
	txt.value=txt.value.replace(/[^a-zA-Z \n\r]+/g, '');
}
function AllowAlphabetNum(txt){
	txt.value = txt.value.replace(/[^a-zA-Z 0-9\n\r]+/g, '');
	}

</script>


<style>
.formRow {
	border-bottom: 1px solid #DDDDDD;
	border-top: 1px solid #FFFFFF;
	padding: 10px 16px;
}

.k-upload-button input {
	z-index: 100000
}

.k-button:-moz-any(input) {
	padding-bottom: 0.5em;
	padding-top: 0.5em;
}
</style>
<style type="text/css">
#webcam,#canvas {
	width: 320px;
	border: 20px solid #333;
	background: #eee;
	-webkit-border-radius: 20px;
	-moz-border-radius: 20px;
	border-radius: 20px;
}

#webcam {
	position: relative;
	margin-top: 50px;
	margin-left: 100px;
	margin-bottom: 50px;
	position: relative;
}

#webcam>span {
	z-index: 2;
	position: absolute;
	color: #eee;
	font-size: 10px;
	bottom: -16px;
	left: 152px;
}

#canvas {
	border: 20px solid #ccc;
	background: #eee;
}
#test{
padding-left: 220px;
}
</style>

<script type="text/javascript"
	src="<c:url value="/resources/jquery.webcam.js" />"></script>
<script type="text/javascript"
	src="<c:url value='/resources/jquery-ui.min.js'/>"></script>

