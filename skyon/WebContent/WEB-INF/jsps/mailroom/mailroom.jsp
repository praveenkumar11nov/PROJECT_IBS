<%@include file="/common/taglibs.jsp"%>

	<c:url value="/mailroom/read" var="readUrl" />
	<c:url value="/mailroom/create" var="createUrl" />
	<c:url value="/mailroom/update" var="updateUrl" />
	<c:url value="/mailroom/destroy" var="destroyUrl" />
	<c:url value="/mailroom/readTowerNames" var="towerNames" />
	<c:url value="/mailroom/readPropertyNumbers" var="propertyNum" />
	<c:url value="/mailroom/test" var="test" />
	<c:url value="/mailroom/getDetailsBasedOnStatus" var="statusValues" />	
	
	<c:url value="/mailroom/getBlockNames" var="getBlockNames" />
	<c:url value="/mailroom/getPropertyNo" var="getPropertyNo" />
	<c:url value="/mailroom/addTo" var="addTo" />
	<c:url value="/mailroom/addFrom" var="addFrom" />
	<c:url value="/mailroom/getStatus" var="getStatus" />
	<c:url value="/mailroom/getCreatedBy" var="getCreatedBy" />
	<c:url value="/mailroom/getlastUpdatedBy" var="getlastUpdatedBy" />
	<c:url value="/mailroom/getConsignmentNo" var="getConsignmentNo" />	
	
	<c:url value="readConsignmentAvailabilty" var="consignmentAvailable" />
	
  		
	<kendo:grid name="grid" pageable="true"	filterable="true" sortable="true" reorderable="true"  selectable="true" change="onChange"
	scrollable="true" groupable="true" resizable="true">
	<kendo:grid-filterable extra="false">
		 		<kendo:grid-filterable-operators>
		  			<kendo:grid-filterable-operators-string eq="Is equal to"/>
		  			<kendo:grid-filterable-operators-date lte="Mail Received Before" gte="Mail Received After"/>
				</kendo:grid-filterable-operators>			
		</kendo:grid-filterable>	
		<kendo:grid-editable mode="popup" confirmation="Are You Sure,you want to delete?" />
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Enter MailRoom Consignments" />
			<kendo:grid-toolbarItem name="Clear_Filter" text="Clear Filter"/>			
			<kendo:grid-toolbarItem name="singleOutForDelivery" text="Send To Delivery"/>
			<kendo:grid-toolbarItem name="allForDelivery" text="Send To Delivery"/>
			<kendo:grid-toolbarItem name="printSentMail" text="Print Sent Mail"/>
		</kendo:grid-toolbar>
		
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" ></kendo:grid-pageable>
		
		<kendo:grid-columns>		
		    <kendo:grid-column title="" headerTemplate="<input type='checkbox' class='headerCheckBox' onclick='selectAllCheckBox(this.id)' id='selectAllChkbx'/>" template="<input type='checkbox' onclick='selectSingleCheckBox(this.id)' id='singleselect_#=mlrId#' class='checkbox_#=status#'/>" width="30px" />		
		    <kendo:grid-column title="MR_Id" field="mlrId" width="70px" hidden="true"/>			
			<kendo:grid-column title="Block Name&nbsp;*" field="blockName" editor="TowerNames"  width="100px" >
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function blockNameFilter(element) {
								element.kendoAutoComplete({
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
			
			<kendo:grid-column title="Property Id" field="propertyId" width="65px" hidden="true"/>
			
			<kendo:grid-column title="Property Number&nbsp;*" field="property_No" editor="PropertyNumbers"  width="130px">
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
						
			<kendo:grid-column title="Addressed To&nbsp;*" field="addressedTo" editor="PersonNames"  width="110px">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function addressedTo(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${addTo}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	    
	    	</kendo:grid-column>			
			
			<kendo:grid-column title="Addressed From&nbsp;*" field="addressedFrom" editor="FromAddress" width="125px">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function addressedFrom(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${addFrom}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	    
	    	</kendo:grid-column>
			
			<kendo:grid-column title="Consignment Number Available&nbsp;*" field="test" editor="consignmentNoDetails" width="160px" hidden="true"/>
			
			<kendo:grid-column title="Consignment Number" field="consignmentNo" width="150px">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function ConsignmentNo(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getConsignmentNo}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	 
	    		</kendo:grid-column>
			
			
			<kendo:grid-column title="Mail Received Date&nbsp;*" field="mailboxDt" format="{0: dd/MM/yy hh:mm tt}" filterable="true" width="130px" />	
					
			<kendo:grid-column title="Status" field="status" editor="statusTypes" width="120px">
			<kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function statusFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getStatus}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	    
	    	</kendo:grid-column>
			
			<kendo:grid-column title="DrGroup_Id" field="drGroupId" width="70px" hidden="true"/>
			
			<kendo:grid-column title="Created By" field="createdBy" width="100px">
			 <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function createdByFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getCreatedBy}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	    
	    	</kendo:grid-column>			 
			 
		    <kendo:grid-column title="Last Updated By" field="lastUpdatedBy" width="125px" >
		   <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function propertyNameFilter(element) {
								element.kendoAutoComplete({
									dataType: 'JSON',
									dataSource: {
										transport: {
											read: "${getlastUpdatedBy}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	   
	    	</kendo:grid-column>
		    
		    
			
			<kendo:grid-column title="&nbsp;" width="160px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit" click="Edit" />
					<kendo:grid-column-commandItem name="destroy"/> 
				</kendo:grid-column-command>
			</kendo:grid-column>
			
		</kendo:grid-columns>
		
		<kendo:dataSource requestEnd="onRequestEnd" requestStart="onRequestStart">
			<kendo:dataSource-transport>
			<kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-create url="${createUrl}" dataType="json" type="POST" contentType="application/json" />
			<kendo:dataSource-transport-update url="${updateUrl}" dataType="json" type="POST" contentType="application/json" />
			<kendo:dataSource-transport-destroy url="${destroyUrl}" dataType="json" type="POST" contentType="application/json" />
		<%-- <kendo:dataSource-transport-update url="${updateUrl}" dataType="json" type="POST" contentType="application/json" /> --%>
				 <kendo:dataSource-transport-parameterMap>
					<script>
						function parameterMap(options, type) 
						{
							return JSON.stringify(options);
						}
					</script>
				</kendo:dataSource-transport-parameterMap>
			</kendo:dataSource-transport>
			
			<kendo:dataSource-schema parse="parse">
				<kendo:dataSource-schema-model id="mlrId">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="mlrId">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="blockName" type="string"/>						
						<kendo:dataSource-schema-model-field name="property_No" type="string"/>						
						<kendo:dataSource-schema-model-field name="propertyId" type="number"/>												
						<kendo:dataSource-schema-model-field name="addressedTo" type="string"/>						
						<kendo:dataSource-schema-model-field name="addressedFrom" type="string"/>						
						<kendo:dataSource-schema-model-field name="drGroupId" type="number"/>						
						 <kendo:dataSource-schema-model-field name="mailboxDt" type="date"/>						
						<kendo:dataSource-schema-model-field name="status" type="string" defaultValue="Mail_Received" editable="true"/>
						<kendo:dataSource-schema-model-field name="createdBy"/>						
						<kendo:dataSource-schema-model-field name="lastUpdatedBy"/>						
						<kendo:dataSource-schema-model-field name="consignmentNo"/>
						
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
	</kendo:grid>
	
	<div id="printDetails"></div>		
	<div id="alertsBox" title="Alert"></div>
	
	<style>
	  .requestedUsertable {font-size:40px;color:#333333;width:100%;border-width: 1px;}
      .requestedUsertable th {font-size:12px;background-color:#acc8cc;border-width: 1px;padding: 8px;border-style: solid;border-color: #729ea5;text-align:left;}
      .requestedUsertable tr {background-color:#d4h3e5;}
      .requestedUsertable td {font-size:12px;border-width: 1px;padding: 8px;border-style: solid;border-color: #729ea5;}
      .requestedUsertable tr:hover {background-color:#ffffff;}
	</style>
	
   <script>
	   var tableData = "";
	   $("#grid").on("click", ".k-grid-printSentMail", function()
	   {
		  var date = new Date();
		  var mnth = ("0" + (date.getMonth()+1)).slice(-2);
	      var day = ("0" + date.getDate()).slice(-2);
		  var finalDate = [ day, mnth, date.getFullYear()].join("-");
		  var todcal=$( "#printDetails");
		  todcal.kendoWindow
		  ({			 
			  width: 1054,
			  height: 'auto',
			  modal: true,
			  draggable: true,
			  position: { top: 145,left:251 },
			  title: "Mail Sent Details   - ("+ finalDate+")",
			  actions: ["print","Minimize", "Maximize", "Close"],
		  }).data("kendoWindow").open();
			todcal.kendoWindow("open");
			
			var st = $("#printDetails").data("kendoWindow");
			var vv = st.wrapper.find(".k-i-print");
			vv.click(function (e) 
		    {
				var prtContent = document.getElementById('printDetails');
                var WinPrint = window.open('', '', 'letf=0,top=0,width=400,height=400,toolbar=0,scrollbars=0,status=0');
                WinPrint.document.write(prtContent.innerHTML);
                WinPrint.document.close();
                WinPrint.focus();
                WinPrint.print();
                WinPrint.close();
			});
		  var out = "Out_For_Delivery";
		  var delivered = "Delivered";
		  var redirected = "Redirected";
		  var returned = "Returned";
			
		  $.ajax
		  ({			
			type : "POST",
			url : "./mailroom/readSentMails",
			async: false,
			dataType : "JSON",
			success : function(response) 
			{	
			   tableData="<table class='requestedUsertable' border='2' id='tableData'><tr><th>Sl.No</th><th>Block Name</th><th>Property_No</th><th>Addressed_To</th><th>Addressed_From</th><th>Consignment No</th><th>Mail Status</th></tr>";
			   for(var s = 0; s<response.length; s++)
		 	   {
				  responseVal = response[s];
				  tableData+='<tr><td>'+(s+1)+'</td><td>'+responseVal.blockName+'</td><td>'+responseVal.property_No+'</td><td>'+responseVal.addressedTo+'</td><td>'+responseVal.addressedFrom+'</td><td>'+responseVal.consignmentNo+'</td><td><img src="./resources/out.png" alt="aizaz">'+out+'</img><br><img src="./resources/out.png" alt="aizaz">'+delivered+'</img><br><img src="./resources/out.png" alt="aizaz">'+redirected+'</img><br><img src="./resources/out.png" alt="aizaz">'+returned+'</img><br></td></tr>';  			  
		 	   }
			   tableData+="</table><br><p style='margin-left: 157px;color: rgba(155, 57, 57, 0.97);font-size: 50px;'></p>"; 
			   $('#printDetails').html(tableData);
			}
		  });	  
	   }); 	   
   </script>
	
	
	<script>
		  function parse (response) 
		  {   
		      $.each(response, function (idx, elem) {
		             if (elem.mailboxDt && typeof elem.mailboxDt === "string") {
		                 elem.mailboxDt = kendo.parseDate(elem.mailboxDt, "yyyy-MM-ddTHH:mm:ss.fffZ");
		             }
		         });
		         return response;
		  }
	
	    $(document).ready(function() 
		{	
			$('.k-grid-singleOutForDelivery').hide();
			$('.k-grid-allForDelivery').hide();
		});
		var mailstatus = "";
		var allIds = "";
		function onChange(args)
		{
			var gview = $("#grid").data("kendoGrid");
		 	var selectedItem = gview.dataItem(gview.select());
		 	mailstatus = selectedItem.status;
		 	allIds = selectedItem.mlrId;
	    }
		var globalid = [];
	    function selectSingleCheckBox(fieldId)
	    {
	    	securityCheckForActions("./mailroomManagement/mailroom/selectSingleCheckBox");
	    	if(mailstatus == 'Out_For_Delivery')
			{
				//alert("Consignment Already Sent To Delivery");
				$("#alertsBox").html("Alert");
				$("#alertsBox").html("Consignment Already Sent To Delivery");
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
				
				$("#"+fieldId).prop('checked',false);
				return false;
			}
	    	if(mailstatus == 'Delivered')
			{
				//alert("Consignment Already Delivered");
				$("#alertsBox").html("Alert");
				$("#alertsBox").html("Consignment Already Delivered");
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
				
				$("#"+fieldId).prop('checked',false);
				return false;
			}
	    	if(mailstatus == 'Redirected')
			{
				//alert("Consignment Already Redirected");
				
				$("#alertsBox").html("Alert");
				$("#alertsBox").html("Consignment Already Redirected");
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
				
				$("#"+fieldId).prop('checked',false);
				return false;
			}
	    	var temp = fieldId.split("_");
	    	if($("#"+fieldId).prop('checked') == true)
		    {
	    		globalid.push(temp[1]);
		    }		    
	    	else if($("#"+fieldId).prop('checked') == false)
		    {
	    		globalid.splice($.inArray(temp[1], globalid),1);
		    }
	    	if(globalid.length > 0)
		    {
	    		$('.k-grid-singleOutForDelivery').show();
				$('.k-grid-allForDelivery').hide();	
		    }
	    	if(globalid.length < 1)
		    {
	    		$('.k-grid-singleOutForDelivery').hide();
				$('.k-grid-allForDelivery').hide();	
		    }
	    }	   
		  $("#grid").on("click", ".k-grid-singleOutForDelivery", function()
		  {	    	  
				$.ajax
				({
					type : "POST",
					dataType : "text",
					url : "./mailroom/updateSingleOutForDelivery",
					data :
					{
						id1 : globalid.toString(),						
					}, 
					success : function(response)
					{						
					    alert(response);
						window.location.reload();
					}
				}); 
		   });
	   /****************************** END FOR SINGLE CHECKBOX  *****************************/
		
	  
	  	  
		/******************** FOR SELECT ALL CHECKBOX************************/
		
	    var globalSelectAllId = [];
	    function selectAllCheckBox(fieldId)
	    {
	    	securityCheckForActions("./mailroomManagement/mailroom/selectAllCheckBox");
	    	
	    	if($("#"+fieldId).prop('checked') == true)
	    	{
	    		var uomDetailsGrid = $('#grid').data("kendoGrid");
				var data = uomDetailsGrid.dataSource.data();			
				$.each(data, function( index, value ) 
				{
					if(value.status == "Mail_Received" || value.status == "Returned")
					{
						globalid.push(value.mlrId);
						$("#singleselect_"+value.mlrId).prop('checked',true);
					}
				});	
				if(globalid.length > 0)
				{
					$('.k-grid-singleOutForDelivery').show();
				}
				else
				{
					//alert("No Consingments to deliver");
					$("#alertsBox").html("Alert");
					$("#alertsBox").html("No Consignments to deliver");
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
					$("#"+fieldId).prop('checked',false);					
				}
	    	}
	    	else
	    	{
	    		//alert("Unchecked");
	    		for(var i = 0;i<globalid.length;i++)
	    		{
	    			$("#singleselect_"+globalid[i]).prop('checked',false);
	    		}
	    		$('.k-grid-singleOutForDelivery').hide();
	    		globalid = [];
	    	}
	    }
		$("#grid").on("click", ".k-grid-allForDelivery", function()
	    {	
			$.ajax
			({
				type : "POST",
				url : "./mailroom/updateAllOutForDelivery",
				data : 
				{
						id1 : ids,
				}, 
				success : function(response){
				 	alert(response);
					window.location.reload();
				 } 
			});
			//RenderLinkInner('mailroom');	    
	    });
	   /*********** END Of  Select All CheckBox******************/
	
		$("#grid").on("click", ".k-grid-Clear_Filter", function()
		{		
		    $("form.k-filter-menu button[type='reset']").slice().trigger("click");
			var grid = $("#grid").data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();
		});
	
		function onRequestEnd(e) 
		{
			if (typeof e.response != 'undefined')
			{
				if (e.response.status == "FAIL")
				{
					errorInfo = "";			
					errorInfo = e.response.result.invalid;		
					var i = 0;
					for (i = 0; i < e.response.result.length; i++) 
					{
						errorInfo += (i + 1) + ". "	+ e.response.result[i].defaultMessage + "\n";
					}
					if (e.type == "create") 
					{
						alert("Error: Creating the MailRoom Details\n\n" + errorInfo);
					}
					if (e.type == "update") 
					{
						alert("Error: Updating the MailRoom Details\n\n" + errorInfo);
					}
					var grid = $("#grid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
					return false;
				}
				if(e.response.status == "OutForDeliveryError")
			    {
					alert("Consignment Sent For Delivery!Cannot Be Deleted");
					var grid = $("#grid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
					return false;
			    }
				if(e.response.status == "ReturnedError")
				{
					alert("Returned Consignment Cannot Be Deleted");
					var grid = $("#grid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
					return false;
				}
			}
			if (e.type == "update" && !e.response.Errors) 
			{	
				e.sender.read();
				/* window.location.reload();
				alert("Consignment details UPDATED successfully"); */
				
				$("#alertsBox").html("");
				$("#alertsBox").html("Consignment details UPDATED successfully");
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
			if (e.type == "create" && !e.response.Errors)
			{
				e.sender.read();
				/* alert("Consignment details ADDED successfully");
				window.location.reload(); */
				
				$("#alertsBox").html("");
				$("#alertsBox").html("Consignment details ADDED successfully");
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
			if(e.type == "destroy" && !e.response.Errors)
			{
				/* alert("Consignment Details Deleted Successfully");
				var grid = $("#grid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh(); */
				
				$("#alertsBox").html("");
				$("#alertsBox").html("Consignment Details Deleted Successfully");
				$("#alertsBox").dialog({
					modal : true,
					draggable: false,
					resizable: false,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
							window.location.reload();
						}
					}
				});
				
				
				
			}
		}
	
		function onRequestStart(e)
		{
			    $('.k-grid-update').hide();
		        $('.k-edit-buttons')
		                .append(
		                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
		        $('.k-grid-cancel').hide();
		        
		    /*  var grid= $("#grid").data("kendoGrid");
		     grid.cancelRow(); */
		}
		
		function consignmentNoDetails(container, options)
		{
			var booleanData = [ {
				text : 'Select',
				value : "select"
			}, {
				text : 'Yes',
				value : "Yes"
			}, {
				text : 'No',
				value : "No"
			}];
			$('<select id="check" name="ConsignmentNo" class="consignmentInput"  required="true"/>').attr('data-bind', 'value:test').appendTo(container).kendoDropDownList 
			({
				dataSource : booleanData,
				dataTextField : 'text',
				dataValueField : 'value',
			});
			$('<span class="k-invalid-msg" data-for="ConsignmentNo"></span>').appendTo(container);  
		}
	
		$(document).on('change', 'select[class="consignmentInput"]', function() 
	    {
			var value = $('.consignmentInput option:selected').eq(0).text();
			
			if(value == 'Yes')
			{
				$('label[for=consignmentNo]').parent().hide();
				$('div[data-container-for="consignmentNo"]').show();
				$('input[name="consignmentNo"]').prop('required',true);
			}
			else if(value == 'No')
			{
				$('label[for=consignmentNo]').parent().hide();
				$('div[data-container-for="consignmentNo"]').hide();			
				$('input[name="consignmentNo"]').prop('required',false);
			}
		});
		 function TowerNames(container, options) 
		 {
				$('<select data-text-field="blockName" data-value-field="blockId"  id="blockId" data-bind="value:' + options.field + '" />')
		             .appendTo(container)
		             .kendoComboBox
		             ({
		            	 placeholder: "Select Block Name",
		                 autoBind: false,
		                 dataSource: {  
		                     transport:{
		                         read: "${towerNames}"
		                     }
		                 }
		             });
		 }

		function PropertyNumbers(container, options) 
		{
				$('<input data-text-field="property_No" data-value-field="propertyId" id="propertyNo" data-bind="value:' + options.field + '"/>')
		             .appendTo(container).kendoComboBox
		             ({
		            	 placeholder: "Select PropertyNo",
		            	 cascadeFrom: "blockId",
		                 autoBind: false,
		                 dataSource: {  
		                     transport:{
		                         read: "${propertyNum}"
		                     }
		                 }
		             });
				$('<span class="k-invalid-msg" data-for="Address To"></span>').appendTo(container);
				
		}
		
		function PersonNames(container, options) 
		{
	        $('<textarea name="Address To" data-text-field="addressedTo" data-value-field="addressedTo" data-bind="value:' + options.field + '" style="width: 148px; height: 46px;" required="true"/>')
	             .appendTo(container);
	    	$('<span class="k-invalid-msg" data-for="Address To"></span>').appendTo(container); 
		} 
		     
	    function FromAddress(container, options) 
		{
		    $('<textarea name="Addressed From" data-text-field="addressedTo" data-value-field="addressedTo" data-bind="value:' + options.field + '" style="width: 148px; height: 46px;" required="true"/>')
		    .appendTo(container);
		    $('<span class="k-invalid-msg" data-for="Addressed From"></span>').appendTo(container);
		}      
		
		function statusTypes(container, options)
		{
			var booleanData = [ {
				text : '--select--',
				value : "--select--"
			}, {
				text : 'Mail_Received',
				value : "Mail_Received"
			}, {
				text : 'Delivered',
				value : "Delivered"
			}, {
				text : 'Redirected',
				value : "Redirected"
			}];
	
			$('<input />').attr('data-bind', 'value:status').appendTo(
					container).kendoComboBox({
				dataSource : booleanData,
				dataTextField : 'text',
				dataValueField : 'value'
			});
		}
	     
		/*Spring Method level Security for Add Button  */
		$("#grid").on("click", ".k-grid-add", function() 
		{	
			securityCheckForActions("./mailroomManagement/mailroom/createButton");
			
			if($("#grid").data("kendoGrid").dataSource.filter())
			{
	    		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
	    	    var grid = $("#grid").data("kendoGrid");
	    		grid.dataSource.read();
	    		grid.refresh();
	        }
			
			$(".k-window-title").text("Add Consignement Details");
			$(".k-grid-update").text("Save");	
			
			$(".k-nav-today").hide();
			
			$('label[for=consignmentNo]').parent().hide();
			$('div[data-container-for="consignmentNo"]').hide();
			
			$('label[for=mlrId]').parent().hide();
			$('div[data-container-for="mlrId"]').hide();
			
			$('label[for=lastUpdatedDt]').parent().remove();
			$('div[data-container-for="lastUpdatedDt"]').hide();	
			
			$('label[for=drGroupId]').parent().hide();
			$('div[data-container-for="drGroupId"]').hide();
			
			$('label[for=propertyId]').parent().hide();
			$('div[data-container-for="propertyId"]').hide();
					
			$('label[for=propertyName]').parent().hide();
			$('div[data-container-for="propertyName"]').hide();
			
			$('label[for=createdBy]').parent().hide();
			$('div[data-container-for="createdBy"]').hide();
			
			$('label[for=lastUpdatedBy]').parent().hide();
			$('div[data-container-for="lastUpdatedBy"]').hide();	
			
			$('label[for=status]').parent().hide();
			$('div[data-container-for="status"]').hide();	
			
			$('input[type="checkbox"]').parent().hide();
			 
			 $(".k-grid-update").click(function () 
	         {
		  		var one = $("#blockId").data("kendoComboBox");
		        var two=$("#propertyNo").data("kendoComboBox");	      
				var check = $("#check").val();
				
				if(check == 'select')
				{
					alert("Select Consignment Number Availability");
					return false;	
				}			
		  		if(one.select() == -1) 
				{
					alert("Select Valid Block Name");
			 	    return false;	
			    }
		  		if(two.select() == -1) 
				{
			        alert("Select Valid Property Number");
			     	return false;	
			 	}
	         }); 
			 
			 /************  CANCEL BUTTON ***************/
			 $(".k-grid-cancel").click(function () 
	         {
				 $('input[type="checkbox"]').parent().show();
				 var grid = $("#grid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
	         });		 
			 /***********  END CANCEL  ************/
			 
			 /* $(".k-link").click(function () 
			 {
				 $('input[type="checkbox"]').parent().show();	
				 var grid = $("#grid").data("kendoGrid");
					grid.dataSource.read();
					grid.dataSource.read();
					grid.refresh();
					grid.refresh();
					grid.refresh();
			 }); */
		});	
		
		function Edit(e) 
		{
			securityCheckForActions("./mailroomManagement/mailroom/updateButton");
			
			var widget = $("#grid").data("kendoGrid");
			var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
		    var st = dataItem.status;
			if(st=="Out_For_Delivery")
			{
				 $('input[type="checkbox"]').parent().show();
				 var grid = $("#grid").data("kendoGrid");
				 grid.dataSource.read();
			  	 grid.refresh();
				 alert("Mail Sent For Delivery");
				 return false;				 
			}
			else if(st=="Redirected")
			{
				 $('input[type="checkbox"]').parent().show();
				 var grid = $("#grid").data("kendoGrid");
				 grid.dataSource.read();
				 grid.refresh();
				 alert("Mail Already Redirected");
				 return false;
			}
			else if(st=="Delivered")
			{
				 $('input[type="checkbox"]').parent().show();
			     var grid = $("#grid").data("kendoGrid");
				 grid.dataSource.read();
				 grid.refresh();
				 alert("Mail Already Delivered");
				 return false;
			}
			else if(st=="Returned")
			{
				 $('input[type="checkbox"]').parent().show();
				 var grid = $("#grid").data("kendoGrid");
				 grid.dataSource.read();
			 	 grid.refresh();
				 alert("Mail Already Returned And Stored In Mailroom");
				 return false;
			}		
			
	  	    $(".k-window-title").text("Update Consignment Details");
	  	
	  	  	$(".k-nav-today").hide();
	  	    
	  		$('label[for=test]').parent().hide();
			$('div[data-container-for="test"]').hide();
		
	     	$('label[for=mlrId]').parent().hide();
			$('div[data-container-for="mlrId"]').hide();
			
			$('label[for=drGroupId]').parent().hide();
			$('div[data-container-for="drGroupId"]').hide();
			
			$('label[for=createdBy]').parent().hide();
			$('div[data-container-for="createdBy"]').hide();
			
			$('label[for=lastUpdatedBy]').parent().hide();
			$('div[data-container-for="lastUpdatedBy"]').hide();
			
			$('label[for=propertyId]').parent().hide();
			$('div[data-container-for="propertyId"]').hide();	
			
			$('label[for=status]').parent().hide();
			$('div[data-container-for="status"]').hide();
			
			$('[name="consignmentNo"]').attr("readonly", true);
			
			$('input[type="checkbox"]').parent().hide();
			
			$(".k-grid-update").click(function () 
		    {
				var one = $("#blockId").data("kendoComboBox");
				var two=$("#propertyNo").data("kendoComboBox");
		  		if(one.select() == -1) 
				{
					alert("Select Valid Block Name");
			 	    return false;	
			    }
				if(two.select() == -1) 
				{
			        alert("Select Valid Property Number");
			     	return false;	
			 	}
	        }); 
			$(".k-grid-cancel").click(function () 
			{
			    $('input[type="checkbox"]').parent().show();	
			    var grid = $("#grid").data("kendoGrid");
				grid.dataSource.read();
				grid.dataSource.read();
				grid.refresh();
				grid.refresh();
				grid.refresh();
			});
			 $(".k-link").click(function () 
			 {
				 $('input[type="checkbox"]').parent().show();	
				 var grid = $("#grid").data("kendoGrid");
					grid.dataSource.read();
					grid.dataSource.read();
					grid.refresh();
					grid.refresh();
					grid.refresh();
			 });
	    }  	
		$("#grid").on("click", ".k-grid-delete", function() 
		{
			securityCheckForActions("./mailroomManagement/mailroom/deleteButton");
		});
	
	/*************************************************************************************************************/
	var res = [];
    	(function($, kendo) {
    		$
    				.extend(
    						true,
    						kendo.ui.validator,
    						{
    							rules :
    							{ // custom rules
					                 DeliveryAtValidator : function (input, params)
								     {
						                     if (input.attr("name") == "mailboxDt") {
						                      return $.trim(input.val()) !== "";
						                     }
						                     return true;
						             },
						             dob: function (input, params) 
						             {
					                     if (input.filter("[name = 'mailboxDt']").length && input.val()) 
					                     {    
					                    	 var temp = input.val().substring(0,9);
					                         var selectedDate = temp;
					                         var todaysDate = $.datepicker.formatDate('dd/mm/yy', new Date());
					                         var flagDate = false;
												
					                         if ($.datepicker.parseDate('dd/mm/yy', selectedDate.trim()) <= $.datepicker.parseDate('dd/mm/yy', todaysDate)) 
					                         {
					                                flagDate = true;
					                         }
					                         return flagDate;
					                     }
					                     return true;
					                 }
							        },
    							messages : { 
    								DeliveryAtValidator : "Mail Received Date Cannot Be Empty",
    								dob:"Mail Received Date Cannot Be Future Date"
    							}
    						});
    	})(jQuery, kendo);
		
	</script>
<!-- </div> -->
