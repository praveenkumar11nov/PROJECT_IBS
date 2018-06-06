<%@include file="/common/taglibs.jsp"%>

<!-- Urls for Common controller  -->
<!-- Urls for Common controller  -->
<c:url value="/common/getAllChecks" var="allChecksUrl" />
<c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />

<c:url value="/visitordetails/create" var="createUrl" />
<c:url value="/visitordetails/read" var="readUrl" />
<c:url value="/visitordetails/update" var="updateUrl" />
<c:url value="/visitorwizard/getaccessCardNo" var="readAccessCardNo" />
<c:url value="/visitorvisits/readblocks" var="propertynameURL" />
<c:url value="/visitorvisits/readTowerNames" var="towerNamesUrl" />
<c:url value="/visitorparking/parkingslotNo" var="readpsSlotNoUrl" />

<c:url value="/storevisitor/contactNoForFilter" var="filtervisitorContactNo" />
<c:url value="/visitordetails/getAccessNoForFilter"  var="readAccessCardNoForFilter" />
<c:url value="/visitordetails/filtervmId" var="filtervmId" />
<c:url value="/storevisitor/nameForFilter" var="filtervisitorName" />
<c:url value="/storevisitor/addressForFilter" var="filtervisitoraddress" />
<c:url value="/property/read_propertyNameForFilter" var="getPropertyfilter" />
<c:url value="/visitordetails/filterVehicleNo" var="getVehicleNoToFilter" />
<c:url value="/parkingSlot/SlotNo" var="getParkingSlotNoForFilter" />
<c:url value="/visitor/imageUploaddoc" var="imageUploadUrl" />

<c:url value="/visitor/visitorDocViewUrl" var="visitorDocView"/>
<c:url value="/common/getAllChecks" var="genderUrl"/>
<c:url value="/common/getAllChecks" var="categoryUrl"/>

<kendo:grid name="grid" pageable="true" change="onChange" 
	edit="visitorEvent" selectable="true" groupable="true" resizable="true"
	sortable="true" filterable="true" scrollable="true" height="430px" dataBound="parentDataBound">
	<kendo:grid-editable mode="popup" />
	<kendo:grid-toolbar>
		<%-- <kendo:grid-toolbarItem name="create" text="Add Visitor Details" /> --%>
		<kendo:grid-toolbarItem text="Clear Filter" name="Clear_Filter" />
		<kendo:grid-toolbarItem
			template="<input id='visitorIdtext' name='visitorIdtext' 
                                                class='k-input' type='text' placeholder='Enter Visitor Id' onkeyup='allowNumber(this)'
                                                style='margin-left:4px;height:20px'/>" />
		<kendo:grid-toolbarItem name="search_visitor" text="Search Visitor" />
		<kendo:grid-toolbarItem name="visitor_out_time" text="Exit Visitor" />
		<kendo:grid-toolbarItem name="allrecord" text="Show All Visitor Record" />
		 <kendo:grid-toolbarItem name="visitorTemplatesDetailsExport" text="Export To Excel" /> 
            		  <kendo:grid-toolbarItem name="visitorPdfTemplatesDetailsExport" text="Export To PDF" /> 
		
	<%-- 	<kendo:grid-toolbarItem text="Pre-Requested Users" name="requestedUsers"/> --%>
			
		<kendo:grid-toolbarItem name="print_visitor_slip" text="Print"></kendo:grid-toolbarItem>


	</kendo:grid-toolbar>

	<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10"></kendo:grid-pageable>
	<kendo:grid-filterable extra="false">
		<kendo:grid-filterable-operators>
			<kendo:grid-filterable-operators-date gte="Visited from"
				lte="Visited Before" />
			<kendo:grid-filterable-operators-string eq="Is equal to"
				startswith="Starts with" />


		</kendo:grid-filterable-operators>
	</kendo:grid-filterable>





	<kendo:grid-columns>

		<kendo:grid-column title=""
			template="<input type='checkbox' name='ravi' onclick='selectSingleCheckBox(this.id)' id='singleselect_#=vvId#'/>"
			width="40px" />
		
		<kendo:grid-column title="Visitor Id &nbsp; *" field="vmId"
			width="120px" sortable="true" filterable="true">
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
				function ContactNoFilter(element) {
				element.kendoAutoComplete({
				dataSource : {
				transport : {
				read : "${filtervmId}"
				}
				}
				});
				}
				</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>

		</kendo:grid-column>

		<kendo:grid-column title="Image " field="image"
			template="<span onclick='clickToUploadImage()' title='Click to Upload Image' ><img src='./vistor/getvisitorimage/#=vmId#' id='myImages_#=vmId#' alt='Click to Upload Image' width='80px' height='80px'/></span>"
			filterable="false" width="94px" sortable="false">
		</kendo:grid-column>




		<kendo:grid-column title="Contact Number &nbsp; *" field="vmContactNo"
			width="120px">


			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script type="text/javascript">
							function ContactNoFilter(element) {
								element.kendoAutoComplete({
									dataSource : {
										transport : {
											read : "${filtervisitorContactNo}"
										}
									}
								});
							}
						</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>

		<kendo:grid-column title="Visitor Name &nbsp; *" field="vmName"
			filterable="true" width="150px">
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script type="text/javascript">
							function ContactNoFilter(element) {
								element.kendoAutoComplete({
									dataSource : {
										transport : {
											read : "${filtervisitorName}"
										}
									}
								});
							}
						</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>
		<kendo:grid-column title="Address &nbsp; *" field="vmFrom"
			width="100px" editor="visitorAddress">

			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script type="text/javascript">
							function ContactNoFilter(element) {
								element.kendoAutoComplete({
									dataSource : {
										transport : {
											read : "${filtervisitoraddress}"
										}
									}
								});
							}
						</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>


		<kendo:grid-column title="Gender &nbsp; *" field="gender"
			editor="GenderDropDown" width="100px">

			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script type="text/javascript"> 
					function nationalityFilter(element) {
					element.kendoDropDownList({
					optionLabel: "Select",
					dataSource : {
					transport : {
					read : "${filterDropDownUrl}/gender"
					}
					}
					});
					}
						  	</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>

		</kendo:grid-column>
		<kendo:grid-column title="Category &nbsp; *" field="category"
			editor="CategoryDropDown" width="100px">
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script type="text/javascript"> 
					function nationalityFilter(element) {
					element.kendoDropDownList({
					optionLabel: "Select",
					dataSource : {
					transport : {
					read : "${filterDropDownUrl}/category"
					}
					}
					});
					}
						  	</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>

		<kendo:grid-column title="Block Name &nbsp; *" field="blockName"
			editor="TowerNames" hidden="true" width="100px" />


		<kendo:grid-column title="Property No &nbsp; *"
			field="property_No" editor="propertyDropDownEditor" width="120px">

			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script type="text/javascript">
				function ContactNoFilter(element) {
				element.kendoAutoComplete({
				dataSource : {
				transport : {
				read : "${getPropertyfilter}"
				}
				}
				});
				}
				</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>

		<kendo:grid-column title="Access Card/Barcode" field="radioButtton"
			hidden="true" width="120px" editor="radioacNoEditor" />

		<kendo:grid-column title="Access Card No&nbsp; *" field="acNo"
			editor="AccessCard" width="120px">
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script type="text/javascript"> 
				function acNoFilter(element) {
				element.kendoAutoComplete({
				dataSource: {
				transport: {
				read: "${readAccessCardNoForFilter}"
				}
				}
				});
				}
			</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>

		<kendo:grid-column title="Purpose Of Visit &nbsp; *" field="vpurpose"
			filterable="false" width="150px" editor="purposeOfVisit" />

		<kendo:grid-column title="Visitor Status" field="vvstatus"
			 filterable="true" width="105px">

			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script type="text/javascript"> 
					function nationalityFilter(element) {
					element.kendoDropDownList({
					optionLabel: "Select",
					dataSource : {
					transport : {
					read : "${filterDropDownUrl}/vvstatus"
					}
					}
					});
					}
						  	</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>

        <kendo:grid-column title="Exit Reason" field="reason" filterable="true" width="150px"/>

		<kendo:grid-column title="Entry Date Time" field="vinDt"
			format="{0:dd/MM/yyyy HH:mm tt}" filterable="true" width="150px">
		</kendo:grid-column>
		
		<kendo:grid-column title="Exit Date Time" field="voutDt"
			format="{0:dd/MM/yyyy HH:mm tt}" filterable="true" width="150px">
		</kendo:grid-column>


		<kendo:grid-column title="Parking" field="radioBtn" hidden="true"
			width="120px" editor="radioEditor" />

		<kendo:grid-column title="Vehicle No&nbsp;*" filterable="false"
			field="vehicleNo" width="120px">

			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script type="text/javascript">
							function VehicleNoFilter(element) {
								element.kendoAutoComplete({
									dataSource : {
										transport : {
											read : "${getVehicleNoToFilter}"
										}
									}
								});
							}
						</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>


		<kendo:grid-column title="Parking Slot No&nbsp;*" field="psSlotNo"
			editor="ParkingSlotNo" filterable="true" width="120px">
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script type="text/javascript">
							function ContactNoFilter(element) {
								element
										.kendoAutoComplete({
											dataSource : {
												transport : {
													read : "${getParkingSlotNoForFilter}"
												}
											}
										});
							}
						</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>


		<kendo:grid-column title="Expected Hours" field="vpExpectedHours"
			filterable="true" width="120px" />


		<kendo:grid-column title="Parking-Status" field="vpStatus"
			 filterable="false" width="115px">

			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script type="text/javascript"> 
					function nationalityFilter(element) {
					element.kendoDropDownList({
					optionLabel: "Select",
					dataSource : {
					transport : {
					read : "${filterDropDownUrl}/vpStatus"
					}
					}
					});
					}
						  	</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>

		<kendo:grid-column title="&nbsp;" width="80px">
			<kendo:grid-column-command>
				<kendo:grid-column-commandItem name="edit" click="edit" />

			</kendo:grid-column-command>
		</kendo:grid-column>

		<kendo:grid-column title="&nbsp;" width="140px">
			<kendo:grid-column-command>

				<kendo:grid-column-commandItem name="uploadDocument"
					text="Upload Document">
					<kendo:grid-column-commandItem-click>
						<script type="text/javascript">
								function showDetails(e) {
									
									//securityCheckForActions("./visitormanagement/visitordetails/uploadButton");
									 var result=securityCheckForActionsForStatus("./visitormanagement/visitors/uploadButton");   
   									if(result=="success"){
									$('#uploadDialog').dialog({
										modal : true
									});
   									
									return false;
   									}
								}
							</script>
					</kendo:grid-column-commandItem-click>
				</kendo:grid-column-commandItem>

			</kendo:grid-column-command>
		</kendo:grid-column>
		<kendo:grid-column title="&nbsp;" width="140px">
			<kendo:grid-column-command>
				<kendo:grid-column-commandItem name="View Document"
					click="downloadFile" />

			</kendo:grid-column-command>
		</kendo:grid-column>
	</kendo:grid-columns>
	<kendo:dataSource pageSize="20" requestEnd="onRequestEnd">
		<kendo:dataSource-transport>
			<kendo:dataSource-transport-create url="${createUrl}" dataType="json"
				type="POST" contentType="application/json" />
			<kendo:dataSource-transport-read url="${readUrl}" dataType="json"
				type="POST" contentType="application/json" />
			<kendo:dataSource-transport-update url="${updateUrl}" dataType="json"
				type="POST" contentType="application/json" />

			<kendo:dataSource-transport-parameterMap>
				<script type="text/javascript">
						function parameterMap(options, type) {
							return JSON.stringify(options);
						}
					</script>
			</kendo:dataSource-transport-parameterMap>
		</kendo:dataSource-transport>
		<kendo:dataSource-schema parse="parse">
			<kendo:dataSource-schema-model id="vmId">
				<kendo:dataSource-schema-model-fields>
					<kendo:dataSource-schema-model-field name="vmId" type="string">

					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="vvId" editable="false">
					</kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="vmContactNo"
						type="string">
						<kendo:dataSource-schema-model-field-validation />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="vmName">
						<kendo:dataSource-schema-model-field-validation />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="vmFrom" />
					<kendo:dataSource-schema-model-field name="reason" />

					<kendo:dataSource-schema-model-field name="gender">
						<kendo:dataSource-schema-model-field-validation />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="category">
						<kendo:dataSource-schema-model-field-validation required="true" />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="blockName" type="string">
						<kendo:dataSource-schema-model-field-validation required="true" />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="property_No">
						<kendo:dataSource-schema-model-field-validation required="true" />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="acNo">
						<kendo:dataSource-schema-model-field-validation required="true" />
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="vpurpose" type="string">
						<kendo:dataSource-schema-model-field-validation />
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="vvstatus">
						<kendo:dataSource-schema-model-field-validation required="true" />
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="vinDt" type="date" />

					<kendo:dataSource-schema-model-field name="voutDt" type="date" />

					<kendo:dataSource-schema-model-field name="vehicleNo">
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="psSlotNo">
					</kendo:dataSource-schema-model-field>

					<kendo:dataSource-schema-model-field name="vpExpectedHours"
						type="number">
						<kendo:dataSource-schema-model-field-validation required="true" />
					</kendo:dataSource-schema-model-field>


					<kendo:dataSource-schema-model-field name="vpStatus">
						<kendo:dataSource-schema-model-field-validation required="true" />
					</kendo:dataSource-schema-model-field>
				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>
	</kendo:dataSource>
</kendo:grid>

<div id="uploadVisitorImageDialog" title="Upload Image" hidden="true">
	<!-- <form id="form1" method="post" action="./visitor/visitorImageUploaddoc"
		enctype="multipart/form-data">
		<input type="text" id="testId" name="ravi" hidden="true" /> <input
			name="visitorfile" id="visitorfile"
			accept="image/*" type="file" /> <br /> <br />
		<input type="submit" id="checkImage" value="Upload"  class="k-button" />
	</form> -->
	
	
	<kendo:upload name="visitorfile" upload="uploadExtraDataImage" complete="oncomplete" multiple="false"
				accept="image/png,image/jpeg,image/jpg" success="onImageSuccess">
		<kendo:upload-async autoUpload="true" saveUrl="./visitor/visitorImageUploaddoc" />
		</kendo:upload>
</div>

<div id="uploadDialog" title="Upload Document" hidden="true">
		<kendo:upload name="files" upload="uploadExtraData" complete="oncomplete" multiple="false"
				accept="application/pdf,image/png,image/jpeg,image/jpg" success="onImageSuccess">
		<kendo:upload-async autoUpload="true" saveUrl="./visitor/imageUploaddoc" />
		</kendo:upload>
</div>

<div id="visitorSlip"  style="width: 350px; height: 100px;"></div>
<div id="alertsBox" title="Alert"></div>
 <div id="requestedUsersDetails"></div>	
 
  <div id="upadteExitReasonWindow" style="display: none;"><br/>	
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label for="Status">Reason for Exit</label>&nbsp;*&nbsp;&nbsp;
	      <textarea style="width: 150px; height: 46px;" name="exitReasonTextArea" id="exitReasonValue"></textarea>
	      <br/><br/>
          
          <br/><br/>
           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button name="update" class="k-button" id="exitReasonButton">Update Reason</button>
</div>
 
 

 <script>
 
 $("#grid").on("click",".k-grid-visitorTemplatesDetailsExport", function(e) {
	  window.open("./visitorTemplate/visitorTemplatesDetailsExport");
});

$("#grid").on("click",".k-grid-visitorPdfTemplatesDetailsExport", function(e) {
	  window.open("./visitorPdfTemplate/visitorPdfTemplatesDetailsExport");
});

   var tableData = "";
   $("#grid").on("click", ".k-grid-requestedUsers", function()
   {
	  var todcal=$( "#requestedUsersDetails" );
	  todcal.kendoWindow
	  ({
		  width: 740,
		  height: 'auto',
		  modal: true,
		  draggable: true,
		  position: { top: 180,left:425 },
		  title: "Pre-Requested Users"
	  }).data("kendoWindow").open();
		todcal.kendoWindow("open");
		
	  $.ajax
	  ({			
		type : "POST",
		url : "./preRequestedUsers/read",
		async: false,
		dataType : "JSON",
		success : function(response) 
		{	
		   tableData="<table class='requestedUsertable' border='2' id='tableData'><tr><th>Visitor Name</th><th>Visitor ContactNo</th><th>Visitor From</th><th>Gender</th><th>Visitor Password</th></tr>";
		   for(var s = 0; s<response.length; s++)
	 	   {
			  responseVal = response[s];
			  tableData+='<tr><td>'+responseVal.vmName+'</td><td>'+responseVal.vmContactNo+'</td><td>'+responseVal.vmFrom+'</td><td>'+responseVal.gender+'</td><td>'+responseVal.visitorPassword+'</td></tr>';  			  
	 	   }
		   tableData+="</table><br><p style='margin-left: 157px;color: rgba(155, 57, 57, 0.97);font-size: 50px;'></p>"; 
		   $('#requestedUsersDetails').html(tableData);
		}
	  });	  
   });   
</script>

<!-- #######################script starts here############################### -->

<script>

var globalid=[];
var vmId="";
var SelectedRowId = "";
var visitorname = "";
var ContactNo="";
var address="";
var property="";
var block="";
var acNo="";
var vehicleNo="";
var psSlotNo="";
var gender="";
var vvId="";
var vvstatus="";
var barcode="N/A";

/* -----------------------------------------Method to hide/show exit and print button -------------------- */

function selectSingleCheckBox(fieldId)
{
	
	var temp = fieldId.split("_");
	if($("#"+fieldId).prop('checked') == true)
		{
  		globalid.push(temp[1]);
   		$('.k-grid-visitor_out_time').show();
		$('.k-grid-print_visitor_slip').hide();
		//alert(globalid);
		}
	else if($("#"+fieldId).prop('checked') == false)
	{
		globalid.splice($.inArray(temp[1], globalid),1);
		if(globalid.length>0){
		
		}else{
				$('.k-grid-visitor_out_time').hide();
				$('.k-grid-print_visitor_slip').hide();
		}
	}
	
} 

/* -----------------------------------------Method to exit Visitor -------------------- */
var exitReasonValue = "";
$("#grid").on("click", ".k-grid-visitor_out_time", function()
{		
	/*******************************************************************************************************************************/
	if(vvstatus=="OUT")
	{
		alert(" Visitor Already Exit");		
		var len=globalid.length;
		globalid=[];
		var grid = $("#grid").data("kendoGrid");
		grid.dataSource.read();
		grid.refresh();
		$('.k-grid-visitor_out_time').hide();
		$('.k-grid-print_visitor_slip').hide();
	}
	else
	{
		$('#upadteExitReasonWindow').kendoWindow
	    ({
			width:320,
			height:200,
			title:"Update Status",
			modal : true
		}).data("kendoWindow").center().open();
					
	    $('#exitReasonButton').click(function()
	    {
	    	exitReasonValue = $("#exitReasonValue").val();
	    	if(exitReasonValue == "")
			{
			   alert("Select Exit Reason");  
			   return false;
			}
	    	else
	        {
	    	   $.ajax
			   ({
				   type : "POST",
				   dataType:'text',
				   url : "./visitordetails/updateVisitorStatus",
				   data :
				   {
					  id1 : globalid.toString(),
					  reason : exitReasonValue,
				   }, 
				   success : function(response)
				   {						
					  alert(response);
					  $("#upadteExitReasonWindow").data("kendoWindow").close();
					  window.location.reload(true);
				   }
				});	
	    	}
	    });
	}    
	/*******************************************************************************************************************************/
	
	
	var result=securityCheckForActionsForStatus("./visitormanagement/visitors/updateButton");
	 /* if(result=="success"){
	if(vvstatus=="OUT"){
		alert(" Visitor Already Exit");
		
		var len=globalid.length;
		globalid=[];
		var grid = $("#grid").data("kendoGrid");
		grid.dataSource.read();
		grid.refresh();
		$('.k-grid-visitor_out_time').hide();
		$('.k-grid-print_visitor_slip').hide();
	}else{
	
			 	$.ajax
				({
					type : "POST",
					dataType:'text',
					url : "./visitordetails/updateVisitorStatus",
					data :
					{
						id1 : globalid.toString(),						
					}, 
					success : function(response){						
						alert(response);
						window.location.reload(true);
					}
				});  
				
			   //
		}
	 } */
	
	
}); 
		   
/* ----------------------------------------parse function-------------------- */		   
		   
function parse (response) 
{   
    $.each(response, function (idx, elem) {
           if (elem.vinDt && typeof elem.vinDt === "string") {
               elem.vinDt = kendo.parseDate(elem.vinDt, "dd/MM/yyyy HH:mm");
           }
           if (elem.voutDt && typeof elem.voutDt === "string") {
               elem.voutDt = kendo.parseDate(elem.voutDt, "dd/MM/yyyy HH:mm");
           }
       });
  
       return response;
} 
		   
/* -----------------------------------------barcode printing code finished-------------------- */		   

function dateEditor(container, options) {
    $('<input name="' + options.field + '"/>')
            .appendTo(container)
            .kendoDateTimePicker({
                format:"dd/MM/yyyy hh:mm tt",
                timeFormat:"hh:mm tt"         
                
            });
}	


/* -----------------------------------------Onchange Method-------------------- */
 
    var inOut;
    var vvstatus;
    var vvIdNew;
 
	function onChange(arg) {
				
		var gview = $("#grid").data("kendoGrid");
		var selectedItem = gview.dataItem(gview.select());

		SelectedRowId = selectedItem.vmId;
		visitorname = selectedItem.vmName;
		vvIdNew=selectedItem.vvId;
		ContactNo=selectedItem.vmContactNo;
		address=selectedItem.vmFrom;
		property=selectedItem.property_No;
		block=selectedItem.blockName;
		inOut=selectedItem.vpurpose;
	//	alert(SelectedRowId);
		//psSlotNo=selectedItem.psSlotNo;
	 vvstatus=selectedItem.vvstatus;
	//	alert(vvstatus);
		if(selectedItem.acNo==null)
			{
			acNo="N/A";
			}
		else
			{
			acNo=selectedItem.acNo;
		
			}
		
		
		if(selectedItem.vehicleNo==null){
			  vehicleNo="N/A";	  
		}
		else
		{
			  vehicleNo=selectedItem.vehicleNo;
			  $('input[id="myRadioPsSlot"]').prop('checked',true);
		}
		
		if(selectedItem.psSlotNo== null||selectedItem.psSlotNo=="undefined")
		{
			psSlotNo="N/A";
			//alert("psSlotNum="+psSlotNo);
		}
		else
		{
			psSlotNo=selectedItem.psSlotNo;
		}
		gender=selectedItem.gender;
		vvId=selectedItem.vvId;
		vmId=selectedItem.vmId;
		vvstatus=selectedItem.vvstatus;
		$("#testId").val(selectedItem.vmId);
		$('#testId').hide();
		$('input[name="vehicleNo"]').prop('required',false);	
	}
	
	/* ------------------ Method to print barcode and slip-------------------- */
	
	$("#grid").on("click" , ".k-grid-print_visitor_slip",function(){ 
		
		if(globalid.length>1 || globalid.length<1 ){
	    	alert("Please Select 1 Record To Print Details");
	    }
		else{
			
			    $("#visitorSlip").kendoBarcode({
			            value:block+"/"+property+"/"+vmId,
			            type: "code128"
			    });   
			           
			    var Text="N/A";
			    var canvas=document.getElementsByTagName("canvas");
				var image=new Image();
				image.id="pic";
				image.src=canvas[0].toDataURL("image/png");
				
			
				var divToPrint=document.getElementById("visitorSlip");
				
				
				var popupWin=window.open('','_blank','width=500 height=500');
				popupWin.document.write("<html><body onload=window.print()><div id=printpopup style=background-image:url(resources/images/b3.png) ><table width=500px height=450px><tr ><th colspan=2 text-align:center>Visitor Information</th></tr><tr ><tr><td>Visitor Id </td><td>"+SelectedRowId+"</td></tr><tr><td><label>Visitor Name : </label></td><td>"+visitorname+"<tr><td>Phone No. :</td><td>"+ContactNo+"</td></tr><tr><td>Gender</td><td>"+gender+"</td></tr><tr><td>Property To Visit : </td><td>"+block+" / "+property+"</td></tr><tr><td>Parking Slot</td><td>"+psSlotNo+"</td></tr><tr><td>Vehicle Number</td><td>"+vehicleNo+"</td></tr><tr><td>Access Card</td><td>"+acNo+"</td></tr><tr><td>Bar Code Slip</td><td><div id=visitorSlip><img src="+image.src+"alt="+Text+ "/></div> </td></tr></table></div></html");
				 			
				$("#visitorSlip").hide();
				//popupWin.document.open();	
				popupWin.document.close();
		    
			    
		}
		$('input:checked').val("");

		
		
  
    globalid=[];
    var grid = $("#grid").data("kendoGrid");
	grid.dataSource.read();
	grid.refresh();	
	
		}); 
		

	/* ------------------ Method to check type of document to be uploaded -------------------- */
	
	function uploadExtraData(e) {
		
		
                    var files=e.files;
		            $.each(files,function(){
                                   if(this.extension ===".png"){
                                                e.data = {
                                                vmId : SelectedRowId
                                				};  
                                   }
                                   
                                   else if(this.extension ===".pdf"){
                                	   e.data = {
                                		 vmId : SelectedRowId
                                	   };
                                   }
                                   
                                   else if(this.extension ===".jpg"){
                                	   e.data = {
                                	        vmId : SelectedRowId
                                	     };
                                   }
                                   
                                   else if(this.extension ===".jpeg"){
                                	   e.data = {
                                		    vmId : SelectedRowId
                                       };
                                   }
                                   
                                   else{
                                	   alert("Only Images(png,jpeg,jpg)/pdf files can be uploaded");
                                	   e.preventDefault();
                                	   return false;
                                   }
                                   
					});
	}

	
	function uploadExtraDataImage(e) {
		
        var files=e.files;
        $.each(files,function(){
                       if(this.extension ===".png"){
                                    e.data = {
                                    vmId : SelectedRowId
                    				};  
                       }
                       
                      else if(this.extension ===".jpg"){
                    	   e.data = {
                    	        vmId : SelectedRowId
                    	     };
                       }
                       
                       else if(this.extension ===".jpeg"){
                    	   e.data = {
                    		    vmId : SelectedRowId
                           };
                       }
                       
                       else{
                    	   alert("Only Images png,jpeg,jpg files can be uploaded");
                    	   e.preventDefault();
                    	   return false;
                       }
                       
		});
}

	/* ------------------ Method to show image after uploading -------------------- */
	
	function oncomplete() {
		
		$("#myImage").attr(
				"src",
				"<c:url value='/vistor/getvisitorimage/" + SelectedRowId+ "?timestamp=" + new Date().getTime() + "'/>");
		$("#myImages_" + SelectedRowId).attr(
				"src","<c:url value='/vistor/getvisitorimage/" + SelectedRowId+ "?timestamp=" + new Date().getTime() + "'/>");
	}

	/* ------------------ Method to show alert after uploading image successfully   -------------------- */
	
	function onImageSuccess(e) {
		alert("Uploaded Successfully");
		$(".k-upload-files.k-reset").find("li").remove();
		window.location.reload(true);
		
	}

	/* ------------------ Method to open the popup -------------------- */
	
	function clickToUploadImage() {
		 var result=securityCheckForActionsForStatus("./visitormanagement/visitordetails/uploadButton");   
		   if(result=="success"){
		
		$('#uploadVisitorImageDialog').dialog({
			model : true
		});
		$(".k-upload-button span").text("Browse File..");
			return false;
		   }
	}

	/* ------------------ Method to download uploaded document -------------------- */
	
	function downloadFile()
	{
		 var result=securityCheckForActionsForStatus("./visitormanagement/visitors/viewButton");
		 if(result=="success")
		 {	        
	        var gview = $("#grid").data("kendoGrid");
			var selectedItem = gview.dataItem(gview.select());
			var vmId = selectedItem.vmId;
	        $.ajax({
				    dataType : "text",
				   	url :"${visitorDocView}/"+ vmId,
				   	datatype:"text",
				   	success : function(response)
				   	{
				   		if(response == "pass")
				   	    {
				   			var gview = $("#grid").data("kendoGrid");
							var selectedItem = gview.dataItem(gview.select());
					        window.open("./visitordetails/document/download/"+selectedItem.vmId);
				   		}
				   		else if(response == "fail")
				   		{
				   			alert("No Documents Uploaded");	
				   			var grid = $("#grid").data("kendoGrid");
							grid.dataSource.read();
							grid.refresh();
				   			return false;
				   		}
				   	}
			   	});	        
		 }
    	}
	
	/* ------------------ Method to check Visitor Image type -------------------- */
	
	$('#checkImage').click(function(){
		
			var filename=$('#visitorfile').val();
			if(filename.lastIndexOf("png")==filename.length-3)
			{
				return true;
			}
			if(filename.lastIndexOf("jpg")==filename.length-3)
			{
				return true;
			}
			if(filename.lastIndexOf("jpeg")==filename.length-3)
			{
				return true;
			}
			else
			{
				if(filename.length=="")
				{
					alert("please select the file...");
					return false;
				}
				else
				{
				alert("Only Files with Extension (.jpg/.png/.jpeg) Are Allowed");
				$('#visitorfile').val("");
				return false;
				}
			}
		
	});
	
	/* $('INPUT[type="visitorfile"]').change(function () {
	    var ext = this.value.match(/\.(.+)$/)[1];
	    switch (ext) {
	        case 'jpg':
	        case 'jpeg':
	        case 'png':
	            $('#uploadButton').attr('disabled', false);
	            break;
	        default:
	            alert('Only Files with Extension (.jpg/.png/.jpeg) Are Allowed');
	            this.value = '';
	    }
	}); */
	
	
	
	/* ------------------ Method to clear the grid-------------------- */
	
	$("#grid").on("click", ".k-grid-Clear_Filter", function() {

		window.location.reload(true);
		
	});

	/* ------------------ Method to hide and display textbox and other and security  while updating the record-------------------- */
	
	function edit(e) {
		
		securityCheckForActions("./visitormanagement/visitors/updateButton");
		
		$('[name="vmContactNo"]').attr("disabled", true);
		$('[name="vmName"]').attr("disabled", true);
		
		$('label[for="image"]').hide();
		$('div[data-container-for="image"]').hide();
		
		$('label[for="voutDt"]').hide();
		$('div[data-container-for="voutDt"]').hide();
		
		$('label[for="vinDt"]').hide();
		$('div[data-container-for="vinDt"]').hide();
		
		$('label[for="vpStatus"]').hide();
		$('div[data-container-for="vpStatus"]').hide();
		
		$('label[for="vvstatus"]').closest('.k-edit-label').remove();
		$('div[data-container-for="vvstatus"]').remove();
		
		$('label[for="vmId"]').closest('.k-edit-label').remove();
		$('div[data-container-for="vmId"]').remove();
		
		$('label[for="vvId"]').closest('.k-edit-label').remove();
		$('div[data-container-for="vvId"]').remove();
		
		$('input[name="ravi"]').remove();
		
		$('label[for="barCodeslip"]').hide();
		$('div[data-container-for="barCodeslip"]').hide();
		
		$('label[for="reason"]').hide();
		$('div[data-container-for="reason"]').hide();
	   	
		
	}

	/* ------------------ Method to display dropDown for Category-------------------- */

	/* function CategoryDropDown(container, options) {
		var booleanData = [ {
			text : 'Select Category',
			value : ""
		}, {
			text : 'F&B',
			value : "F&B"
		}, {
			text : 'Services',
			value : "Services"
		}, {
			text : 'Relative/Friend',
			value : "Relative/Friend"
		}, {

			text : 'Courier',
			value : 'Courier'
		},

		{
			text : 'Others',
			value : "Others"
		}

		];

		$('<select/>').attr('data-bind', 'value:category').appendTo(container)
				.kendoDropDownList({

					dataSource : booleanData,
					dataTextField : 'text',
					dataValueField : 'value'
				});

	} */

	function CategoryDropDown(container, options) {
		var res = (container.selector).split("=");
		var attribute = res[1].substring(0,res[1].length-1);
		
		$('<input data-text-field="text" id="category" data-value-field="value" data-bind="value:' + options.field + '"required ="true" name = "'+attribute+'" id = "'+attribute+'Id"/>')
				.appendTo(container).kendoDropDownList({
				 optionLabel : {
						text : "Select",
						value : "",
					}, 
					defaultValue : false,
					sortable : true,
					dataSource : {
						transport : {
							read : "${categoryUrl}/"+attribute,
						}
					}
				});
		 $('<span class="k-invalid-msg" data-for="'+attribute+'"></span>').appendTo(container);
	}
	
	function parentDataBound(e) {
		  var data = this.dataSource.view(), row;
		 
		 var grid = $("#grid").data("kendoGrid");
		 for (var i = 0; i < data.length; i++) {
		  row = this.tbody.children("tr[data-uid='" + data[i].uid + "']");
	
		  var currentUid = data[i].uid;
		
		 // alert(data[i].vvstatus);
		    if(data[i].vvstatus == "OUT"){
		     var currenRow = grid.table.find("tr[data-uid='" + currentUid
		      + "']");
		    var approveButton =$(currenRow).find(".k-grid-edit");
		     approveButton.hide();
		   		    }
		 } 
		}

	/* ------------------ Method to  display dropDown for Gender-------------------- */
	
	/* function GenderDropDown(container, options) {
		

			var booleanData = [ {
				text : 'Select Gender',
				value : ""
			}, {
				text : 'Male',
				value : "Male"
			}, {
				text : 'Female',
				value : "Female"
			} ];
	
			$('<select />').attr('data-bind', 'value:gender').appendTo(container)
					.kendoDropDownList({
						dataSource : booleanData,
						dataTextField : 'text',
						dataValueField : 'value'
	
			});

	}
	 */
	
	function GenderDropDown(container, options) {
		var res = (container.selector).split("=");
		var attribute = res[1].substring(0,res[1].length-1);
		
		$('<input data-text-field="text" id="gender" data-value-field="value" data-bind="value:' + options.field + '"required ="true" name = "'+attribute+'" id = "'+attribute+'Id"/>')
				.appendTo(container).kendoDropDownList({
				 optionLabel : {
						text : "Select",
						value : "",
					}, 
					defaultValue : false,
					sortable : true,
					dataSource : {
						transport : {
							read : "${genderUrl}/"+attribute,
						}
					}
				});
		 $('<span class="k-invalid-msg" data-for="'+attribute+'"></span>').appendTo(container);
	}
	

	/* ------------------ Method to check aplhabet pattern for visitor name-------------------- */

	(function($, kendo) {
		$
				.extend(
						true,
						kendo.ui.validator,
						{
							rules : { // custom rules
								Visitornamevalidation : function(input, params) {
									if (input.filter("[name='vmName']").length && input.val()) {
										
										return /^[a-zA-Z-,]+(\s{0,1}[a-zA-Z, ])*$/
												.test(input.val());
									}
									return true;
								}
							},
							messages : { //custom rules messages
								Visitornamevalidation : "Visitor Name should use only alphabets "
							}
						});
	})(jQuery, kendo);

	/* ------------------ Method to check aplhabet pattern for Vehicle Number-------------------- */
	
	(function($, kendo) {
		$.extend(true, kendo.ui.validator, {
			rules : { // custom rules
				VehicleNoValidation : function(input, params) {
					if (input.filter("[name='vehicleNo']").length
							&& input.val()) {
						return /^[A-Za-z0-9 ]+$/.test(input.val());
					}
					return true;
				}
			},
			messages : { //custom rules messages
				VehicleNoValidation : "Vehicle Number should be alphaNumeric"
			}
		});
	})(jQuery, kendo);

	/* ------------------ Method to check aplhabet pattern for Contact Number-------------------- */
	
	(function($, kendo) {
		$.extend(true, kendo.ui.validator, {
			rules : { // custom rules
				VisitorContactvalidation : function(input, params) {
					if (input.filter("[name='vmContactNo']").length
							&& input.val()) {
						return /[0-9]{7,13}|\./.test(input.val());
					}
					return true;
				}
			},
			messages : { //custom rules messages
				VisitorContactvalidation : "Contact Number can allows numbers only and min 7 digits and max 13 digits"
			}
		});
	})(jQuery, kendo);

	/* ------------------ Method to show script message for purpose of visit-------------------- */
	
	function purposeOfVisit(container, options) {
		$(
				'<textarea name="Purpose Of Visit" id="vpurpose" data-text-field="vpurpose" data-value-field="vpurpose" data-bind="value:' + options.field + '" style="width: 148px; height: 40px;" required="true"/>')
				.appendTo(container);
		$('<span class="k-invalid-msg" data-for="Purpose Of Visit"></span>').appendTo(container);
	}

	/* ------------------ Method to show script message for address of visitor-------------------- */
	
	function visitorAddress(container, options) {
		$(
				'<textarea name="Address" id="vmFrom" data-text-field="vmFrom" data-value-field="vmFrom" data-bind="value:' + options.field + '" style="width: 148px; height: 40px;" required="true" />')
				.appendTo(container);
		$('<span class="k-invalid-msg" data-for="Address"></span>').appendTo(container);
	}
	
	
	/* ------------------ Method to hide and display textbox and other and security  while creating the record-------------------- */
	
	$("#grid").on("click", ".k-grid-add", function() {
		securityCheckForActions("./visitormanagement/visitors/createButton");
				
				$('input[id="myRadioPsSlot"]').prop('checked',false);
				$('input[id="myRadioacNo"]').prop('checked',false);
				
			 	$('label[for="image"]').hide();
				$('div[data-container-for="image"]').hide();
				$('label[for=vinDt]').hide();		
				$('label[for=voutDt]').hide(); 
				$('label[for="vpStatus"]').closest('.k-edit-label').remove();
				$('div[data-container-for="vpStatus"]').remove();
		
				$('input[name="ravi"]').hide();
				$('label[for="vpExpectedHours"]').hide();
				$('div[data-container-for="vpExpectedHours"]').hide();
			
				$('label[for="vehicleNo"]').hide();
				$('div[data-container-for="vehicleNo"]').hide();
		
				$('label[for="psSlotNo"]').hide();
				$('div[data-container-for="psSlotNo"]').hide();
		
				$('label[for="voutDt"]').hide();
				$('div[data-container-for="voutDt"]').hide();
				
				$('label[for="vinDt"]').hide();
				$('div[data-container-for="vinDt"]').hide();
				
				$('label[for="vpStatus"]').hide();
				$('div[data-container-for="vpStatus"]').hide();
				
				$('label[for="vvstatus"]').closest('.k-edit-label').remove();
				$('div[data-container-for="vvstatus"]').remove();
		
				$('label[for="vmId"]').closest('.k-edit-label').remove();
				$('div[data-container-for="vmId"]').remove();
				
				$('label[for="vvId"]').closest('.k-edit-label').remove();
				$('div[data-container-for="vvId"]').remove();
				
				$(".k-window-title").text("Add Visitor Visits Details");
				$(".k-grid-update").text("Add/Create");
		
				$('.k-edit-field .k-input').first().focus();
				
				$('.k-grid-visitor_out_time').hide();
				$('.k-grid-print_visitor_slip').hide();
				
				$('label[for="acNo"]').hide();
				$('div[data-container-for="acNo"]').hide();

	});

	/*========== IN/OUT BUTTON================== */





	/* --------------------Method to get access card value-------------------- */

	 function AccessCard(container, options) {
		
		 $('<input id="acNoId"  data-text-field="acNo" data-value-field="acNo"   data-bind="value:' + options.field + '"/>')
					.appendTo(container).kendoAutoComplete({
						optionLabel : "Select Access Card",
						autoBind : false,
						dataSource : {
							transport : {
								read : "${readAccessCardNo}"
							}
						}
					});
		
	}
	
	 /* --------------------Method to get Tower Name value-------------------- */
	
	function TowerNames(container, options) {

		$('<select id="blockNameBox" data-text-field="blockName" data-value-field="blockName"   data-bind="value:' + options.field + '"/>')
				.appendTo(container).kendoComboBox({
					optionLabel : "Select Block",
					autoBind : false,
					defaultValue : false,
					sortable : true,
					change : function (e) {
			            if (this.value() && this.selectedIndex == -1) {                    
			                alert("Block Name doesn't exist!");
			                $("#blockNameBox").data("kendoComboBox").value("");
			        	}
				    } ,
					dataSource : {
						transport : {
							read : "${towerNamesUrl}"
						}
					}
				});
		
	}

	/* --------------------Method to get Proeprty Name value-------------------- */
	 
	function propertyDropDownEditor(container, options) {
		$('<select id="propertybox" data-text-field="property_No" data-value-field="property_No"  data-bind="value:' + options.field + '"/>')
				.appendTo(container).kendoComboBox({
					optionLabel : "Select Property",
					cascadeFrom : "blockNameBox",
					defaultValue : false,
					sortable : true,
					change : function (e) {
			            if (this.value() && this.selectedIndex == -1) {                    
			                alert("Property Number doesn't exist!");
			                $("#propertybox").data("kendoComboBox").value("");
			        	}
				    } ,
					dataSource : {
						transport : {
							read : "${propertynameURL}"
						}
					}

				});
	}
	
	/* --------------------Method to get parking slot value-------------------- */
	
	function ParkingSlotNo(container, options) {
		$('<select id="slotId" data-text-field="psSlotNo" data-value-field="psSlotNo" data-bind="value:' + options.field +  '"/>')
				.appendTo(container).kendoComboBox({
					optionLabel : "Select-Slot-No",
					defaultValue : false,
					sortable : true,
					 change : function (e) {
			            if (this.value() && this.selectedIndex == -1) {                    
			                alert("Parking Slot doesn't exist!");
			                $("#slotId").data("kendoComboBox").value("");
			        	}
				    } , 
					dataSource : {
						transport : {
							read : "${readpsSlotNoUrl}"
						}
					}
				});
	}

	/* ----------------Onrequest method------------------- */

	function onRequestEnd(e) {

		if (typeof e.response != 'undefined') {
			if (e.response.status == "FAIL") {
				errorInfo = "";
				errorInfo = e.response.result.invalid;
				for (i = 0; i < e.response.result.length; i++) {
					errorInfo += (i + 1) + ". "
							+ e.response.result[i].defaultMessage;
				}

				if (e.type == "create") {
					alert("Error: Creating the Visitor Parking record\n\n"
							+ errorInfo);
				}

				if (e.type == "update") {
					alert("Error: Updating the Visitor Parking record\n\n"
							+ errorInfo);
				}

				var grid = $("#grid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
				return false;
			}

			if (e.type == "update" && !e.response.Errors) {
				
				alert("Update record is successfull");
				var grid = $("#grid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			}

			if (e.type == "update" && e.response.Errors) {
			
				alert("Update record is Un-successfull");
				var grid = $("#grid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			}

			if (e.type == "create" && !e.response.Errors) {
				alert("Create record is successfull");
				var grid = $("#grid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			}
		}

	}

	
	/* --------------------Method date editor-------------------- */

	function dateEditor(container, options) {
		    $('<input name="' + options.field + '"/>')
		            .appendTo(container)
		            .kendoDateTimePicker({
		                format:"dd/MM/yyyy",
		            });
		} 
	
	/* -------------------document.ready method-------------------- */
	
	 $(document).ready(function() 
			    {
			$('.k-grid-visitor_out_time').hide();
			$('.k-grid-print_visitor_slip').hide();
			
			$("#visitorIdtext").keypress(function (e) {
			     if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
			        alert("Enter Numbers only..");
			               return false;
			    }
			   });
			    });
		
	 
	 function visitorNameEditor(container, options) {
			$("<input />")
					.attr("name", options.field)
					.appendTo(container)
					.kendoComboBox(
							{
								dataTextField : "vmContactNo",
								dataSource : {
									transport : {
										read : {
											url : "./visitordetails/readVisitorPersonalDetails/"
													+ value,
											dataType : "jsonp"
										}
									}
								}
							});
		}
	
	 /* --------------------Method to search visitor-------------------- */
	
	$("#grid").on("click",".k-grid-search_visitor",function(){
		
		var Id=$("#visitorIdtext").val();
		if($("#visitorIdtext").val()==""){
			alert("please Enter Visitor Id");
		}else{
		
		var visitorSearchRecord = new kendo.data.DataSource({
			transport : {
				read : {
					url : "./visitordetails/readVisitorRecordBasedOnSearch/"+Id, // url to remote data source 
					dataType : "json",
					type : 'GET'
				}
			}
		});
		
		$('#grid').data('kendoGrid').setDataSource(visitorSearchRecord);
		
		}
		
		 $(".k-grid-cancel").click(function () {
			 var grid = $("#grid").data("kendoGrid");
			 grid.dataSource.read();
			 grid.refresh();
		 });
		
	});
	
	/* --------------------Method tofind All record------------------- */
	
	$("#grid").on("click", ".k-grid-allrecord", function() {
		$('.k-grid-visitor_out_time').hide();
		 var visitorRecord = new kendo.data.DataSource({
			transport : {
				read : {
					url : "./visitorAllRecorddetails/read", // url to remote data source 
					dataType : "json",
					type : 'GET'
				
				}
			}
		});
		//$('#grid').data('kendoGrid').setDataSource(visitorRecord);
		   $('#grid').data("kendoGrid").setDataSource(visitorRecord);
		   var grid = $("#grid").data("kendoGrid");
		   grid.dataSource.pageSize(10);
		   $('#grid').data("kendoGrid").refresh(); 
	
		
	});
	
	
	
	

	function radioEditor(container, options) {

		$(
				'<input id="myRadioPsSlot" type="radio" name='+options.field+' value="required"/>&nbsp; Required &nbsp; <input type="radio" name='+options.field+' value="notrequired "  id="myRadio" />&nbsp; Not Required<br>')
				.appendTo(container);

	}
	
/* 	--------------------visitor event method to generate alert message----------------------- */

	
 function visitorEvent(e)
	{

		if(e.model.get("psSlotNo")!=null)
		{
		 $('input[id="myRadioPsSlot"]').prop('checked',true);
		
		}else{
			$('input[id="myRadio"]').prop('checked',true);
			$('div[data-container-for="vehicleNo"]').hide();
			$('label[for="vehicleNo"]').hide();
			$('div[data-container-for="vpExpectedHours"]').hide();
			$('label[for="vpExpectedHours"]').hide();
			$('div[data-container-for="psSlotNo"]').hide();
			$('label[for="psSlotNo"]').hide();
		}
		
		if(e.model.get("acNo")!=null){
			$('input[id="myRadioacNo"]').prop('checked',true);
		}else{
			$('input[id="myRadiobarcode"]').prop('checked',true);
			$('label[for="acNo"]').hide();
			$('div[data-container-for="acNo"]').hide();
		}
		
		/*  if (e.model.isNew()) 
	    {

	    }
		else
		{

		} */
		 
		$('.k-edit-field .k-input').first().focus();
		
		var grid = this;
		e.container.on("keydown", function(e) {        
	        if (e.keyCode == kendo.keys.ENTER) {
	          $(document.activeElement).blur();
	          grid.saveRow();
	        }
	      });
		
		$(".k-grid-cancel").click(function(){
			var grid = $("#grid").data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
		});
		
 		 $(".k-grid-update").click(function () 
		{
 			
 		//	alert("------------------"+vmId);
 			
 			var vmname=$('input[name="vmName"]').val();
 			var phNumber=$('input[name="vmContactNo"]').val();
 			var inOut=$('input[name="vvstatus"]').val();
 			var  vpurpose=$("#vpurpose").val();
 			var  vmfrom=$("#vmFrom").val();
 			var  gender=$("#gender").val();
 			var  category=$("#category").val();
 			var  block=$("#blockNameBox").val();
 			var  property=$("#propertybox").val();
 			var  accesscardno=$("#myRadioacNo").val();
 			var  acnoid=$("#acNoId").val();
 			var reason=$('input[name="reason"]').val();
 			var  psslot=$("#myRadioPsSlot").val();
 			var vehicleno=$('input[name="vehicleNo"]').val();
 			var  slotid=$("#slotId").val();
 			var hours=$('input[name="vpExpectedHours"]').val();
 			var vpstatus=$('input[name="vpStatus"]').val();
 			var vinDt=$('input[name="vinDt"]').val();
 			
 			//var gender=$('input[name="gender"]').val();
 			
 			
 			//var inOut=$('input[name="vvstatus"]').val();
 		/* 	alert(vvstatus);
 			alert("vvId----"+vvIdNew);
 			alert(phNumber+"--"+block+""+vmfrom+""+gender+""+category+""+block+"propert"+property+"hhhhh"+hours+""+acnoid+""+vvstatus); */
 		/* 	$.ajax({
 				type:"GET",
 				url:"./visitorDetail/updating",
 				dataType:"json",
 				data:{
 					vmname:vmname,
 					phone:phNumber,
 					status:vvstatus,
 					vpurpose:vpurpose,
 					vmfrom:vmfrom,
 					gender:gender,
 					category:category,
 					block:block,
 					property:property,
 					accesscardno:accesscardno,
 					acnoid:acnoid,
 					reason:reason,
 					psslot:psslot,
 					vehicleno:vehicleno,
 					slotid:slotid,
 					hours:hours,
 					vmId:SelectedRowId,
 					vvIdNew:vvIdNew,
 					vpstatus:vpstatus,
 					vinDt:vinDt,
 				},
 				success:function(response)
 				{
 					alert("resp-----"+response);
 					
 				}
 				
 			}); */
 			
 			
 			/*
 				 var visitorRecord = new kendo.data.DataSource({
 					transport : {
 						read : {
 							url : "./visitordetails/update", // url to remote data source 
 							dataType : "json",
 							type : 'POST'
 						
 						}
 					}
 				});
 			
 				//$('#grid').data('kendoGrid').setDataSource(visitorRecord);
 				   $('#grid').data("kendoGrid").setDataSource(visitorRecord);
 				   var grid = $("#grid").data("kendoGrid");
 				
 				   $('#grid').data("kendoGrid").refresh();  */
 		
 			var stralert="";
 			var category =$("select[data-bind='value:category'] :selected").val();
 			var visitorgender=$("select[data-bind='value:gender'] :selected").val();
 			var radioCheck = $('input[name=radioBtn]:checked').val();
 			var radiocheckAcNo=$('input[name=radioButtton]:checked').val();
			var blockNameBox=  $("#blockNameBox").val();
 			var propertyBox = $("select[data-bind='value:property_No'] :selected").val();
 			//var vehicleNo=$("#vehicleNo").val();
 			//alert(vehicleNo);
 		/*  if((visitorgender==null) || (visitorgender==""))
 			{
 				stralert=stralert+"Visitor Gender is required \n";
 			} 
 			 if((category==null) || (category=="")){
 				stralert=stralert+"Visitor Category is required \n";
 			}  */
 			if((blockNameBox==null) || (blockNameBox=="")){
 				stralert=stralert+"Block Name is required \n";
 			}
 			if((propertyBox==null) || (propertyBox=="")){
 				stralert=stralert+"Property Number is required \n";
 			}
 			
 			if(radiocheckAcNo=="accesscardrequired"){
 				 if((e.model.get("acNo") == null) || (e.model.get("acNo")== "")){
 				stralert=stralert+"Access card is required \n";
 				 }
 			}
 			if(radioCheck == "required")
 			{
 				var psSlotNo = $("select[data-bind='value:psSlotNo'] :selected").val();
 			 if((e.model.get("vehicleNo") == null) || (e.model.get("vehicleNo")== ""))
 				{
 					stralert=stralert+"Vehicle Number is required \n";
 				} 
 				if((psSlotNo == null) || (psSlotNo == ""))
 				{
 					stralert=stralert+"Parking Slot is required \n";
 				}
 			}	
 			if(stralert==""){
 			}
 			else{
 				  alert("Following Fields are required----- \n"+stralert);
 				  return false;
 			}
		}); 
	
	}

 /* 	--------------------visitor method for radio button----------------------- */

	$(document).on('change', 'input[name="radioBtn"]:radio', function() {

		var radioValue = $('input[name=radioBtn]:checked').val();
		  if(radioValue=='required') {
			
				$('div[data-container-for="vpExpectedHours"]').show();
				$('label[for="vpExpectedHours"]').show();
				$('div[data-container-for="psSlotNo"]').show();
				$('label[for="psSlotNo"]').show();
				$('div[data-container-for="vehicleNo"]').show();
				$('label[for="vehicleNo"]').show();
				$('input[name="vehicleNo"]').prop('required',true);
				$('input[id="slotId"]').prop('required',true);
			}
		  else {
			  $('input[name="vehicleNo"]').val("");
			  $('input[name="vehicleNo"]').prop('required', false);
			  $("#slotId").data("kendoComboBox").value("");
			  var firstItem = $('#grid').data().kendoGrid.dataSource.data()[0];
				firstItem.set('psSlotNo','');
			
			$('div[data-container-for="vehicleNo"]').hide();
			$('label[for="vehicleNo"]').hide();
			$('div[data-container-for="vpExpectedHours"]').hide();
			$('label[for="vpExpectedHours"]').hide();
			$('div[data-container-for="psSlotNo"]').hide();
			$('label[for="psSlotNo"]').hide();
		}
	});
	
	
	
	function radioacNoEditor(container, options) {

		$(
				'<input id="myRadioacNo" type="radio" name='+options.field+' value="accesscardrequired"/>&nbsp;Access Card &nbsp; <input type="radio" name='+options.field+'  value="barcoderequired" id="myRadiobarcode" />&nbsp; Barcode<br>')
				.appendTo(container);
	

	}
	
	

	$(document).on('change', 'input[name="radioButtton"]:radio', function() {

		var radioValue = $('input[name=radioButtton]:checked').val();
		  if(radioValue=='accesscardrequired') {
			
			  $('label[for="acNo"]').show();
				$('div[data-container-for="acNo"]').show();
				
				
			}
		  else {
			  $("#acNoId").data("kendoAutoComplete").value("");
			  var firstItem = $('#grid').data().kendoGrid.dataSource
				.data()[0];
		firstItem.set('acNo','');
		
		$('label[for="acNo"]').hide();
		$('div[data-container-for="acNo"]').hide();
		}
	});
	
	
	
	
	
	
	
	
	
	
	
	(function ($, kendo) {
        $.extend(true, kendo.ui.validator, {
             rules: { // custom rules
            	 
            	 Gendervalidation: function (input, params) {
                     //check for the name attribute 
                     if (input.attr("name") == "gender") {
                      return $.trim(input.val()) !== "";
                     }
                     return true;
                 },                  
                 
                 Categoryvalidation: function (input, params) {
                     //check for the name attribute 
                     if (input.attr("name") == "category") {
                      return $.trim(input.val()) !== "";
                     }
                     return true;
                 },                  
            	 
            	 
            	 vmContactNovalidation: function (input, params) {
                     //check for the name attribute 
                     if (input.attr("name") == "vmContactNo") {
                      return $.trim(input.val()) !== "";
                     }
                     return true;
                 },                             
                 vmNamevalidation: function (input, params) {
                     //check for the name attribute 
                     if (input.attr("name") == "vmName") {
                      return $.trim(input.val()) !== "";
                     }
                     return true;
                 },            
             
             vmFromvalidation: function (input, params) {
                 //check for the name attribute 
                 if (input.attr("name") == "vmFrom") {
                  return $.trim(input.val()) !== "";
                 }
                 return true;
             
             }
             },
           //custom rules messages
             messages: { 
            	 vmContactNovalidation: "Enter valid Contact Number",
              vmNamevalidation:"Enter Visitor Name ", 
              vmFromvalidation:"Enter Visitor Address",
            	  Gendervalidation:"Select Gender",
            	  Categoryvalidation:"Select Category"
             }
        });
    })(jQuery, kendo);
	
	function allowNumber(txt){
		txt.value = txt.value.replace(/[^0-9\n\r]+/g, '');
	} 
</script>

<!-- -------------------------------------------- STYLE ---------------------------- -->

<style>


.k-edit-form-container {
	/* width: 550px; */
	text-align: center;
	position: relative;
	/*  height: 600px; */
	/* border: 1px solid black; */
	/*  overflow: hidden;  */
}

.wrappers {
	display: inline;
	float: left;
	width: 350px;
	padding-top: 10px;
	position: relative;

	/* float:left;  */
	/* border: 1px solid red;
 */
	/* border: 1px solid green;
    overflow: hidden; */
}

  				.photo {
                    width: 140px;                    
                }
                .details {
                    width: 400px;
                }                
                .title {
                    display: block;
                    font-size: 1.6em; 
                }
                .description {
                    display: block;
                    padding-top: 1.6em;
                }
                .employeeID {
                    font-family: "Segoe UI", "Helvetica Neue", Arial, sans-serif;
                    font-size: 50px;
                    font-weight: bold;
                    color: #898989;
                }
                td.photo, .employeeID {
                    text-align: center;
                }
                td {
    				vertical-align: middle;
				}
                #employeesTable td {
                    background: -moz-linear-gradient(top,  rgba(0,0,0,0.05) 0%, rgba(0,0,0,0.15) 100%);
                    background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,rgba(0,0,0,0.05)), color-stop(100%,rgba(0,0,0,0.15)));
                    background: -webkit-linear-gradient(top,  rgba(0,0,0,0.05) 0%,rgba(0,0,0,0.15) 100%);
                    background: -o-linear-gradient(top,  rgba(0,0,0,0.05) 0%,rgba(0,0,0,0.15) 100%);
                    background: -ms-linear-gradient(top,  rgba(0,0,0,0.05) 0%,rgba(0,0,0,0.15) 100%);
                    background: linear-gradient(to bottom,  rgba(0,0,0,0.05) 0%,rgba(0,0,0,0.15) 100%);
                    padding: 20px;
                }
                
                .employeesDropDownWrap
                {
                    display:block;
                    margin:0 auto;
                }
                
                #emplooyeesDropDown-list .k-item {
                    overflow: hidden; 
                }
                
                #emplooyeesDropDown-list img {
                    -moz-box-shadow: 0 0 2px rgba(0,0,0,.4);
                    -webkit-box-shadow: 0 0 2px rgba(0,0,0,.4);
                    box-shadow: 0 0 2px rgba(0,0,0,.4);
                    float: left;
                    margin: 5px 20px 5px 0;
                }
                
           
                #emplooyeesDropDown-list h3 {
                    margin: 30px 0 10px 0;
                    font-size: 2em;
                }
                
                #emplooyeesDropDown-list p {
                    margin: 0;
                }
                .k-upload-button input {
                z-index: inherit;
                }
                .k-edit-form-container {
    text-align: left;
}
.k-tabstrip{
	width: 1150px
}
.file-icon {
		display: inline-block;
		float: left;
		width: 48px;
		height: 48px;
		margin-left: 10px;
		margin-top: 13.5px;
		}
		
		.img-file {
		background-image: url(./resources/kendo/web/upload/jpg.png)
	}
	
	.doc-file {
		background-image: url(./resources/kendo/web/upload/doc.png)
	}
	
	.pdf-file {
		background-image: url(./resources/kendo/web/upload/pdf.png)
	}
	
	.xls-file {
		background-image: url(./resources/kendo/web/upload/xls.png)
	}
	
	.zip-file {
		background-image: url(./resources/kendo/web/upload/zip.png)
	}
	
	.default-file {
		background-image: url(./resources/kendo/web/upload/default.png)
	}
	
	#example .file-heading {
		font-family: Arial;
		font-size: 1.1em;
		display: inline-block;
		float: left;
		width: 450px;
		margin: 0 0 0 20px;
		height: 25px;
		-ms-text-overflow: ellipsis;
		-o-text-overflow: ellipsis;
		text-overflow: ellipsis;
		overflow: hidden;
		white-space: nowrap;
	}
	
	#example .file-name-heading {
		font-weight: bold;
	}
	
	#example .file-size-heading {
		font-weight: normal;
		font-style: italic;
	}
	
	li.k-file .file-wrapper .k-upload-action {
		position: absolute;
		top: 0;
		right: 0;
	}
	
	li.k-file div.file-wrapper {
		position: relative;
		height: 75px;
	}
	
	.ui-dialog-osx {
	    -moz-border-radius: 0 0 8px 8px;
	    -webkit-border-radius: 0 0 8px 8px;
	    border-radius: 0 0 8px 8px; border-width: 0 8px 8px 8px;
	}


.k-upload-button input {
	z-index: 100000
}
.requestedUsertable {font-size:40px;color:#333333;width:100%;border-width: 1px;}
.requestedUsertable th {font-size:12px;background-color:#acc8cc;border-width: 1px;padding: 8px;border-style: solid;border-color: #729ea5;text-align:left;}
.requestedUsertable tr {background-color:#d4h3e5;}
.requestedUsertable td {font-size:12px;border-width: 1px;padding: 8px;border-style: solid;border-color: #729ea5;}
.requestedUsertable tr:hover {background-color:#ffffff;}
/* .requestedUsertable {font-size:12px;color:#333333;width:100%;border-width: 1px;border-color: #729ea5;border-collapse: collapse;}
.requestedUsertable th {font-size:12px;background-color:#acc8cc;border-width: 1px;padding: 8px;border-style: solid;border-color: #729ea5;text-align:left;}
.requestedUsertable tr {background-color:#d4e3e5;}
.requestedUsertable td {font-size:12px;border-width: 1px;padding: 8px;border-style: solid;border-color: #729ea5;}
.requestedUsertable tr:hover {background-color:#ffffff;} */
</style>