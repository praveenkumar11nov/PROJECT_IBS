<%@include file="/common/taglibs.jsp"%>

  <c:url value="/accesscards/read" var="AccessCardsReadUrl" />
  <c:url value="/accesscards/cu?action=create" var="AccessCardsCreateUrl" />
  <c:url value="/accesscards/cu?action=update" var="AccessCardsUpdateUrl" />
  <c:url value="/accesscards/delete" var="AccessCardsDeleteUrl"></c:url>
  <c:url value="/accesscards/getPersonNames" var="readUserNames" />
  <c:url value="/comowner/status/readstatus" var="statusReadUrl" />
  <c:url value="/comowner/acccards/readAccessCardTypes" var="accessCardTypeUrl" />
  
  <!-- Access card permission Grid Access Urls -->
	<c:url value="/comowner/accesscardspermisions/read" var="AccessCardsPermissionsReadUrl" />
	<c:url value="/comowner/accesscardspermisions/create" var="AccessCardsPermissionCreateUrl" />
	<c:url value="/comowner/accesscardspermisions/update" var="AccessCardsPermissionUpdateUrl" />
	<c:url value="/comowner/accesscardspermisions/delete" var="AccessCardsPermissionDeleteUrl" />
	<c:url value="/comowner/getAccessCardsBasedOnPersonId" var="getAccessCardsBasedOnPersonId" />
	<c:url value="/comowner/accessrepositoryread/read" var="readAccessRepositoryUrl" />
	<c:url value="/accessCards/getAllAccessCards/forFilter" var="readAllAccessCards"></c:url>
	
	
	 <%-- <c:forEach var="category" items="${navigation.keySet()}">
	       	<h1>${category}</h1>
	       	<ul>
	       	<c:forEach var="widget" items="${navigation.get(category)}">
	       		<c:if test="${widget.include()}">
		       		<li>
		       			<h2>${widget.text}</h2>
		       			<ul>
		       			<c:forEach var="example" items="${widget.items}">
		       				<c:if test="${example.include()}">
		       					<li><a href="<c:url value='${example.url.replaceAll(".html", "")}'/>">${example.text}</a></li>
		       				</c:if>
		       			</c:forEach>
		       			</ul>
		       		</li>
	       		</c:if>
	       	</c:forEach>
	       	</ul>
        </c:forEach> --%>
        
  	<kendo:grid name="AccessCardGrid" edit="accessCardEvent" pageable="true" detailTemplate="accesscardTemplate" change="onChange"  resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true" filterable="true" groupable="true" >
							<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" ></kendo:grid-pageable>
								<kendo:grid-filterable extra="false">
							 		<kendo:grid-filterable-operators>
							  			<kendo:grid-filterable-operators-string eq="Is equal to"/>
							 		</kendo:grid-filterable-operators>
								</kendo:grid-filterable>
								<kendo:grid-editable mode="popup" confirmation="Are you sure you want to delete?"/>
						        <kendo:grid-toolbar >
						            <kendo:grid-toolbarItem name="create" text="Add Card" />
						            <kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilter()><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>"/>
						       	<%-- <kendo:grid-toolbarItem	template="<input type='file' name='files' id='files' />" /> --%>
						       	<kendo:grid-toolbarItem name="uploadXMLFile" text="Import Excel" />
						       	
						       	
						        </kendo:grid-toolbar>
        						<kendo:grid-columns>
									     <kendo:grid-column title="Access Type" editor="accessCardTypeEditor" field="acType">
									     	<kendo:grid-column-filterable>
												<kendo:grid-column-filterable-ui>
													<script>
														function cartTypeFilter(element) {
															element.kendoAutoComplete({
																placeholder : "Enter Card Type",
																dataTextField: "name",
																dataSource : {
																	transport : {
																		read : "${accessCardTypeUrl}"
																	}
																}
															});
														}
													</script>
												</kendo:grid-column-filterable-ui>
											</kendo:grid-column-filterable>
									     </kendo:grid-column>
									     <kendo:grid-column title="Access Card Number"  field="acNo">
									     <kendo:grid-column-filterable>
											<kendo:grid-column-filterable-ui>
												<script>
													function personNameFilter(element) {
														element.kendoAutoComplete({
															placeholder : "Enter Card Number",
															dataSource : {
																transport : {
																	read : "${readAllAccessCards}"
																}
															}
														});
													}
												</script>
											</kendo:grid-column-filterable-ui>
										</kendo:grid-column-filterable>
									     </kendo:grid-column>
									     <kendo:grid-column title="Status"  field="status" >
									     	<kendo:grid-column-filterable>
												<kendo:grid-column-filterable-ui>
													<script>
														function cartTypeFilter(element) {
															element.kendoAutoComplete({
																placeholder : "Enter Status",
																dataTextField: "name",
																dataSource : {
																	transport : {
																		read : "${statusReadUrl}"
																	}
																}
															});
														}
													</script>
												</kendo:grid-column-filterable-ui>
											</kendo:grid-column-filterable>
									     
									     
									     </kendo:grid-column>
        								 <kendo:grid-column title="&nbsp;" width="250px" >
							            	<kendo:grid-column-command>
							            		<kendo:grid-column-commandItem name="edit"/>
							            		<kendo:grid-column-commandItem name="destroy"/>
							            		<kendo:grid-column-commandItem name="View Details" click="getCardAssignedStatus" />
							            	</kendo:grid-column-command>
							            </kendo:grid-column>
							            <kendo:grid-column title=""
				template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Activate#=data.urId#'>#= data.status == 'Active' ? 'De-activate' : 'Activate' #</a>"
				width="100px" />
        						</kendo:grid-columns>
        						 <kendo:dataSource requestEnd="AccessCardRequestEnd" requestStart="onRequestStart">
						            <kendo:dataSource-transport>
						                <kendo:dataSource-transport-read url="${AccessCardsReadUrl}" dataType="json" type="POST" contentType="application/json"/>
						                <kendo:dataSource-transport-create url="${AccessCardsCreateUrl}" dataType="json" type="GET" contentType="application/json" />
						                <kendo:dataSource-transport-update url="${AccessCardsUpdateUrl}" dataType="json" type="GET" contentType="application/json" />
						                <kendo:dataSource-transport-destroy url="${AccessCardsDeleteUrl}" dataType="json" type="GET" contentType="application/json" />
						               <%-- <kendo:dataSource-transport-parameterMap>
						                	<script>
							                	function parameterMap(options,type) 
							                	{
							                		return JSON.stringify(options);	                		
							                	}
						                	</script>
						                 </kendo:dataSource-transport-parameterMap> --%>
						            </kendo:dataSource-transport>
						            <kendo:dataSource-schema parse="accessCardsParse">
						                <kendo:dataSource-schema-model id="acId">
						                    <kendo:dataSource-schema-model-fields>
							                    <kendo:dataSource-schema-model-field name="acId" editable="false" type="number"/>
						                    	<%-- <kendo:dataSource-schema-model-field name="personId" type="number"></kendo:dataSource-schema-model-field> --%>   
						                    	<kendo:dataSource-schema-model-field name="acType" type="string"></kendo:dataSource-schema-model-field>
						                    	<kendo:dataSource-schema-model-field name="acNo" type="string">
						                    		<kendo:dataSource-schema-model-field-validation required = "true"/>
						                    	</kendo:dataSource-schema-model-field>
						                    	<kendo:dataSource-schema-model-field name="lastUpdatedDt" type="dateTime"></kendo:dataSource-schema-model-field>
						                    	<kendo:dataSource-schema-model-field name="status" editable="true" defaultValue="Active" type="string"></kendo:dataSource-schema-model-field>
						                    </kendo:dataSource-schema-model-fields>
						                 </kendo:dataSource-schema-model>
						             </kendo:dataSource-schema>
						          </kendo:dataSource>
        				</kendo:grid>	
        				
        				<kendo:grid-detailTemplate id="accesscardTemplate">
							<kendo:grid name="gridAccessCardPermission_#=acId#"  pageable="true"   edit="accessCardPermissionEvent"
								resizable="true" sortable="true" reorderable="true"
								selectable="true" scrollable="true">
								<kendo:grid-pageable pageSize="10"></kendo:grid-pageable>
								<kendo:grid-editable mode="popup" confirmation="Are you sure you want to remove this item?" />
						        <kendo:grid-toolbar >
						            <kendo:grid-toolbarItem name="create" text="Assign Access Points" />
						        </kendo:grid-toolbar>
        						<kendo:grid-columns>
        								<%-- <kendo:grid-column title="&nbsp;" width="100px" >
							             	<kendo:grid-column-command>
							            		<kendo:grid-column-commandItem name="edit"/>
							            	</kendo:grid-column-command>
							             </kendo:grid-column> --%>
									     <%-- <kendo:grid-column title="Access Permission Id" field="acpId" filterable="false" width="120px"/> --%>
									     <%-- <kendo:grid-column title="Access Card Id="acId" ></kendo:grid-column> --%>
									     
									    <%--  <kendo:grid-column title="Person Name" editor="PersonNames" field="personId"></kendo:grid-column> --%>
									     <%-- <kendo:grid-column title="Access Type" field="acpId"></kendo:grid-column> --%>
									    <%--  <kendo:grid-column title="Access Card"  field="acId"></kendo:grid-column> --%>
									     <kendo:grid-column title="Access Point" editor="accessRepoEditor" hidden="true" width="0px" field="arId"></kendo:grid-column>
									      <kendo:grid-column title="Access Repository"  field="acPointName"></kendo:grid-column>
									     <kendo:grid-column title="Start Date" field="acpStartDate" format= "{0:dd/MM/yyyy}"   filterable="true" width="100px" />
									     <kendo:grid-column title="End Date" field="acpEndDate" format= "{0:dd/MM/yyyy}"   filterable="true" width="100px" />
									     <kendo:grid-column title="Status"  field="status" editor="statusEditor"></kendo:grid-column>
									    
        								 <kendo:grid-column title="&nbsp;" width="172px" >
							            	<kendo:grid-column-command>
							            		<kendo:grid-column-commandItem name="edit"/>
							            		<kendo:grid-column-commandItem name="destroy" />
							            	</kendo:grid-column-command>
							            </kendo:grid-column>
        						</kendo:grid-columns>
        						 <kendo:dataSource requestEnd="AccessCardsPermissionRequestEnd" requestStart="onRequestStart1">
						            <kendo:dataSource-transport>
						                <kendo:dataSource-transport-read  url="${AccessCardsPermissionsReadUrl}/#=acId#" dataType="json"  type="POST" contentType="application/json"/>
						                <kendo:dataSource-transport-create url="${AccessCardsPermissionCreateUrl}" dataType="json" type="GET"  contentType="application/json" />
						                <kendo:dataSource-transport-update url="${AccessCardsPermissionUpdateUrl}"  dataType="json" type="GET" contentType="application/json" />
						                <kendo:dataSource-transport-destroy url="${AccessCardsPermissionDeleteUrl}" dataType="json" type="GET" contentType="application/json" />
						               <%-- <kendo:dataSource-transport-parameterMap>
						                	<script>
							                	function parameterMap(options,type) 
							                	{
							                		if(type=="read")
							                			{
							                				return JSON.stringify(options);
							                			}
							                		else
							                			{
							                			return jQuery.param(options);//options;
							                					
							                			}
							                			
							                		
							                	}
						                	</script>
						                 </kendo:dataSource-transport-parameterMap> --%>
						            </kendo:dataSource-transport>
						            <kendo:dataSource-schema parse="accessCardPemissionParse">
						                <kendo:dataSource-schema-model id="acpId">
						                    <kendo:dataSource-schema-model-fields>
							                    <kendo:dataSource-schema-model-field name="acpId" type="number" />
						                    	<%-- <kendo:dataSource-schema-model-field name="personId" type="number"></kendo:dataSource-schema-model-field>   --%> 
						                    	<kendo:dataSource-schema-model-field name="arId" type="number">
						                    		<kendo:dataSource-schema-model-field-validation required = "true"/>
						                    	</kendo:dataSource-schema-model-field>
						                    	<kendo:dataSource-schema-model-field name="acpStartDate" type="date" defaultValue="">
						                    			<kendo:dataSource-schema-model-field-validation required = "true"/>
						                    	</kendo:dataSource-schema-model-field>
						                    	<kendo:dataSource-schema-model-field name="acpEndDate" type="date" defaultValue="">
						                    			<kendo:dataSource-schema-model-field-validation required = "true"/>
						                    	</kendo:dataSource-schema-model-field>
						                    	<kendo:dataSource-schema-model-field name="status" type="string">
						                    		<kendo:dataSource-schema-model-field-validation required = "true"/>
						                    	</kendo:dataSource-schema-model-field>
						                    	
						                    	<kendo:dataSource-schema-model-field name="acId" type="number">
						                    	</kendo:dataSource-schema-model-field>
						                    	<kendo:dataSource-schema-model-field name="arName" type="string">
						                    		<kendo:dataSource-schema-model-field-validation required = "true"/>
						                    	</kendo:dataSource-schema-model-field>
						                    </kendo:dataSource-schema-model-fields>
						                 </kendo:dataSource-schema-model>
						             </kendo:dataSource-schema>
						          </kendo:dataSource>
        				</kendo:grid>	          
					  </kendo:grid-detailTemplate>  
        				<div id="alertsBox" title="Alert"></div>
        				
        				<div id="uploadDialog" title="Upload Document" hidden="true"></div>




<div id="generateBTBillDialog" style="display: none;">
	<form id="uploadForm">
		<table>
			<tr>
				<td>Upload Excel</td>
				<td><kendo:upload name="filesNew" id="files"></kendo:upload></td>
		</table>
	</form>
</div>



<script>

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


    $(document).ready(function () 
    {
    	$("#files").kendoUpload({
            multiple: true,
            success : onDocSuccess,
            error : errorS,
            async: {
                saveUrl:"./accesscards/upload",
                autoUpload: true
            }
        });
    }); 
        
    
    

    
    function onDocSuccess(){
       	alert("File Imported Successfully");
       	window.location.reload();
    }
    function errorS(){
      	alert("File Importing Failed:Empty Cells Found or Duplicate Records Found");
      	window.location.reload();
    }
    </script>
        				<script type="text/javascript">
        				function PersonNames(container, options) 
        				{
        			        $('<textarea data-text-field="firstName" data-value-field="personId" data-bind="value:' + options.field + '"/>')
        			             .appendTo(container)
        				        .kendoAutoComplete
        			             ({
        			                 autoBind: false,
        			                 dataSource: 
        			                 {                
        			                     transport: 
        			                     {
        			                         read: "${readUserNames}"
        			                     }
        			                 }
        			             });
        			     }  
        				$("#AccessCardGrid").on("click", ".k-grid-Import", function() 
        					    {
        							 $('#uploadDialog').dialog({
        								modal : true,
        							}); 
        					    });
        						
        				function statusEditor(container,options)
        				 {
        					 $('<input name="Status"  data-text-field="name"  data-value-field="value"  data-bind="value:' + options.field + '" required="true"/>')
        				     .appendTo(container)
        				         .kendoDropDownList({
        				        	 optionLabel: "Select Status",  		
        				         dataSource: {  
        				             transport:{
        				                 read: "${statusReadUrl}"
        				             }
        				         },
        				         placeholder :"Select",
        				     });
        					 $('<span class="k-invalid-msg" data-for="Status"></span>').appendTo(container);

        				 }
        				function clearFilter()
        				{
        					  $("form.k-filter-menu button[type='reset']").slice().trigger("click");
        					  var gridDocumentDefiner = $("#AccessCardGrid").data("kendoGrid");
        					  gridDocumentDefiner.dataSource.read();
        					  gridDocumentDefiner.refresh();
        				}

        				//register custom validation rules
        				(function($, kendo) {
        					$
        							.extend(
        									true,
        									kendo.ui.validator,
        									{
        										rules : { // custom rules          	
        											 
        							                 acNo : function(input,params)
        							                 {
        							                	 if (input.filter("[name='acNo']").length && input.val()) 
            							                	{
        														//return /^[a-zA-Z0-9#/]{0,15}$/.test(input.val());
     														     //return /^-[0-9]{5}[ ]{1}[0-9]{11}-[0-9]{1}$/.test(input.val());
        													}
        													return true;

        								             },
        								             AccessCardNumberUniqueness : function(input, params){
        													if (input.filter("[name='acNo']").length && input.val()) 
        													{
        														var flag = true;
        														$.each(accessCardNumbers, function(idx1, elem1) {
        															if(elem1 == input.val())
        															{
        																flag = false;
        															}	
        														});
        														return flag;
        													}
        													return true;
        												},
        												
        												AccessPoint_blank : function(input, params){
        														if (input.attr("name") == "AccessRepository")
        													{
        														return $.trim(input.val()) !== "";
        													}
        													return true;
        												},
        												AccessPointUniqueness : function(input, params){
        													if (input.filter("[name='AccessRepository']").length && input.val()) 
        													{
        														var flag = true;
        														$.each(accessPointsId, function(idx1, elem1) {
        															//alert(elem1+"----"+input.val());
        															if(elem1 == input.val())
        															{
        																flag = false;
        															}	
        														});
        														return flag;
        													}
        													return true;
        												}
        										},
        										messages : {
        											//custom rules messages
        											acNo :"Valid format is -12345 12345678901-1",
        											AccessCardNumberUniqueness : "Access Card Number Already Exists",
        											AccessPointUniqueness : "Access Point is already assigned",
        											AccessPoint_blank : "Access Point is required"
        										}
        									});
        				
        				})(jQuery, kendo);
        				//End Of Validation
        				/* function format(mask, number) 
        				{
						   var s = ''+number, r = '';
						   for (var im=0, is = 0; im<mask.length && is<s.length; im++) {
						     r += mask[im]=='X' ? s.charAt(is++) : mask.charAt(im);
						   }
						   return r;
						} */
        				function accessCardTypeEditor(container,options)
						 {
							 $('<input data-text-field="name" name="Card Type" required="true" data-value-field="value" data-bind="value:' + options.field + '"/>')
						     .appendTo(container)
						         .kendoDropDownList({
						        	 optionLabel: "Select CardType",		
						         dataSource: {  
						             transport:{
						                 read: "${accessCardTypeUrl}"
						             }
						         },
						         placeholder :"Select",
						     });
				
							 $('<span class="k-invalid-msg" data-for="Card Type"></span>').appendTo(container);
				
						 }

						function AccessCardRequestEnd(e)
						  {
							  if (typeof e.response != 'undefined')
								{

								  if (e.response.status == "CHILD_FOUND_EXCEPTION") 
									{
										errorInfo = "";
										errorInfo = e.response.result.childFoundException;

										$("#alertsBox").html("");
										$("#alertsBox").html(
												"Error: Delete Access Card<br>" + errorInfo);
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
										$('#AccessCardGrid').data('kendoGrid').dataSource.read();
										return false;
									}

								  	else  if (e.response.status == "CARD_NUM_EXISTS") 
									{
										errorInfo = "";
										errorInfo = e.response.result.accessCardNumberExists;

										$("#alertsBox").html("");
										$("#alertsBox").html(
												"Error: AccessCard Creating<br>" + errorInfo);
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
										var grid = $("#AccessCardGrid").data("kendoGrid");
										grid.dataSource.read();
										grid.refresh();
										return false;
									}

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
													"Error: Assigning the AccessCard<br>" + errorInfo);
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
													"Error: Updating the AccessCard<br>" + errorInfo);
											$("#alertsBox").dialog({
												modal : true,
												buttons : {
													"Close" : function() {
														$(this).dialog("close");
													}
												}
											});
										}

										//$('#gridAccessCard_'+SelectedRowId).data().kendoGrid.dataSource.read();
										/* var grid = $("#propertyGrid_"+SelectedRowId).data("kendoGrid");
										grid.dataSource.read();
										grid.refresh(); */
										return false;
									}
								
							  else if (e.type == "create") 
								{
									$("#alertsBox").html("");
									$("#alertsBox").html("AccessCard added successfully");
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
									//$('#accessCardAssigned_'+SelectedRowId).data().kendoDropDownList.dataSource.read();
								}

								else if (e.type == "update") 
								{
									$("#alertsBox").html("");
									$("#alertsBox").html("AccessCard updated successfully");
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
									//$('#accessCardAssigned_'+SelectedRowId).data().kendoDropDownList.dataSource.read();
								}

								else if (e.type == "destroy") 
								{
									$("#alertsBox").html("");
									$("#alertsBox").html("AccessCard deleted successfully");
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
									//$('#accessCardAssigned_'+SelectedRowId).data().kendoDropDownList.dataSource.read();
								}	
							
								}
						  }

						function accessRepoEditor(container, options)
						  {	
							  $('<select name="AccessRepository" data-text-field="name" data-value-field="value" data-bind="value:' + options.field + '"/>')
								.appendTo(container).kendoDropDownList({	
									optionLabel : {
										name : "Select",
										value : "",
									},
									defaultValue : false,
									sortable : true,
									dataSource : {
										transport : {
											read : "${readAccessRepositoryUrl}"
										}
									}

								});

							  $('<span class="k-invalid-msg" data-for="AccessRepository"></span>').appendTo(container);

						  }
						var SelectedRowId = "";
						function onChange(arg) {
							 var gview = $("#AccessCardGrid").data("kendoGrid");
						 	 var selectedItem = gview.dataItem(gview.select());
						 	 SelectedRowId = selectedItem.acId;
						 	 this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
						     // alert("Selected: " + SelectedRowId);
						}
						var accessPointsId = [];
						function accessCardPermissionEvent(e)
						{
							  //alert(JSON.stringify(e.model));
							  $('div[data-container-for="acPointName"]').remove();
  							  $('label[for="acPointName"]').closest('.k-edit-label').remove();
							  if (e.model.isNew()) 
							    {
								  $(".k-window-title").text("Add New Acess Card Permission");
							        //set the default value for StateID
							        //alert("Coming to new Script>>"+SelectedAccessCardId);
							        /* if((SelectedAccessCardId == "")||(SelectedAccessCardId == ' '))
								    {
								        var grid = $("#gridAccessCardPermission_"+SelectedRowId).data("kendoGrid");
										grid.cancelRow();
										$("#alertsBox").html("");
										$("#alertsBox").html("Please Select Access Card");
										$("#alertsBox").dialog({
											modal : true,
											buttons : {
												"Close" : function() {
													$(this).dialog("close");
												}
											}
										});
										return false;
								    } */
							        $(".k-window-title").text("Add New Acess Card Permission");
							        e.model.set("acId", SelectedRowId );
							        /* else
								    {
							        	$(".k-window-title").text("Edit Acess Card Permission Details");
							        	e.model.set("acId", SelectedAccessCardId );
								    } */
							    }
							  else
							  {
								  $(".k-window-title").text("Edit Acess Card Permission Details");
								  accessPointsId.splice(accessPointsId.indexOf(e.model.arId),1);
							  }
							  
							  e.container.find(".k-grid-cancel").bind("click", function () {
							        //your code here
							   var gridAccessCardPermission = $("#gridAccessCardPermission_"+SelectedRowId).data("kendoGrid");
							   gridAccessCardPermission.cancelRow();
							   accessCardPemissionParse(gridAccessCardPermission._data);
							     }); 
						
						}
						
                           $("#AccessCardGrid").on("click",".k-grid-uploadXMLFile",function(e) {
							
							//var result = securityCheckForActionsForStatus("./BillGeneration/GenerateBills/uploadXMLFile");
						
								var xmlDialog = $("#generateBTBillDialog");
								xmlDialog.kendoWindow({
									width : "400",
									height : "150",
									modal : true,
									draggable : true,
									position : {
										top : 80
									},
									title : "Upload Excel "
								}).data("kendoWindow").center().open();
								xmlDialog.kendoWindow("open");
							
					});
						
						function accessCardPemissionParse(response)
						{
							//alert("JSON>>"+JSON.stringify(response));
							var data = response; //<--data might be in response.data!!!
							accessPointsId = [];
						     
							 for (var idx = 0, len = data.length; idx < len; idx ++)
							 {
								 accessPointsId.push(data[idx].arId);
							 }
							 //alert(accessPointsId);
							 return response;
							
						}

						function AccessCardsPermissionRequestEnd(e)
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

										$('#gridAccessCardPermission_'+SelectedRowId).data().kendoGrid.dataSource.read();
										/* var grid = $("#propertyGrid_"+SelectedRowId).data("kendoGrid");
										grid.dataSource.read();
										grid.refresh(); */
										return false;
									}
									else  if (e.response.status == "ACP_ALREADY_ASSIGNED") 
									{
										errorInfo = "";
										errorInfo = e.response.result.accessPointAlreadyAssigned;

										$("#alertsBox").html("");
										$("#alertsBox").html(
												"Error: Assigning AccessPoint<br>" + errorInfo);
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
										var grid = $('#gridAccessCardPermission_'+SelectedRowId).data("kendoGrid");
										grid.dataSource.read();
										grid.refresh();
										return false;
									}

							  else if (e.type == "create") 
								{
									$("#alertsBox").html("");
									$("#alertsBox").html("Assigned Permission Successfully");
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
									$('#gridAccessCardPermission_'+SelectedRowId).data().kendoGrid.dataSource.read();
								}

								else if (e.type == "update") 
								{
									$("#alertsBox").html("");
									$("#alertsBox").html("Assigning Permission updated successfully");
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
									$('#gridAccessCardPermission_'+SelectedRowId).data().kendoGrid.dataSource.read();
								}
								else if (e.type == "destroy") 
								{
									$("#alertsBox").html("");
									$("#alertsBox").html("Access Point Unassigned Successfully");
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
									$('#gridAccessCardPermission_'+SelectedRowId).data().kendoGrid.dataSource.read();
								}
								}

						  }
						var accessCardNumbers = [];
						function accessCardEvent(e)
						{
							$(".k-edit-field").each(function () {
								$(this).find("#temPID").parent().remove();
						   	});
							$('div[data-container-for="status"]').remove();
							$('label[for="status"]').closest('.k-edit-label').remove();
							$('label[for="undefined"]').closest('.k-edit-label').remove();
							
							$(".k-edit-label").parent().width(500);
							//$(".k-edit-form-container").parent().width(600).data("kendoWindow").center();
							 if (e.model.isNew()) 
							  {
							  	$(".k-window-title").text("Add New Access Card");
							  	$(".k-grid-update").text("Save");
							  }
							  else
							  {
								  $(".k-window-title").text("Edit Access Card Details");
								  $(".k-grid-update").text("Update");  
								  accessCardNumbers.splice(accessCardNumbers.indexOf(e.model.acNo),1);
								 // alert(accessCardNumbers);
							  }	
							 
							 e.container.find(".k-grid-cancel").bind("click", function () {
							        //your code here
							   var AccessCardGrid = $("#AccessCardGrid").data("kendoGrid");
							   accessCardsParse(AccessCardGrid._data);
							     }); 
						}
						
						 function accessCardsParse (response) 
						  {   
							    var data = response; //<--data might be in response.data!!!
							    accessCardNumbers = [];
							     
								 for (var idx = 0, len = data.length; idx < len; idx ++)
								 {
									 accessCardNumbers.push(data[idx].acNo);
								 }
								 //alert(accessCardNumbers);
								 return response;
						 }

						$("#AccessCardGrid").on("click", "#temPID", function(e) {
							var button = $(this), enable = button.text() == "Activate";
							var widget = $("#AccessCardGrid").data("kendoGrid");
							var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
							if (enable) {
								
								$.ajax({
									type : "POST",
									url : "./accessCards/CardStatus/" + dataItem.acId + "/activate",
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
										$('#AccessCardGrid').data('kendoGrid').dataSource.read();
									}
								});
							} else {
								$.ajax({
									type : "POST",
									url : "./accessCards/CardStatus/" + dataItem.acId + "/deactivate",
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
										button.text('Activate');
										$('#AccessCardGrid').data('kendoGrid').dataSource.read();
									}
								});
							}
						});
						
						
						
						
						
						function getCardAssignedStatus()
						{
							var gaddressview = $("#AccessCardGrid").data("kendoGrid");
							var selectedAddressItem = gaddressview.dataItem(gaddressview.select());
							//alert(selectedAddressItem.acId);
							$.ajax({
								type : "POST",
								url : "./accessCards/getCardAssignedDetails",
								//dataType : "json",
								data : {
									acId : selectedAddressItem.acId,
								},
								success : function(response) 
								{
									//alert(JSON.stringify(response));
									
									
							 		 var detailsList = "";
							 		if(response.length>0)
							 		{
							 			detailsList = "<table frame=box bgcolor=white width=450px><tr bgcolor=#0A7AC2>";
										detailsList = detailsList +"<th><font color=white>Person Name</font></th><th><font color=white>Person Type</font></th><th><font color=white>Contact Content</font></th></tr>";
								 		 	for ( var s = 0, len = response.length; s < len; ++s) 
								 		 	{
								         		var contact = response[s];
								         		detailsList += "<tr><td align=center>"+contact.personName+ "</td><td align=center>"+contact.personType+"</td><td align=center>"+contact.contactContent+"</td></tr>";
								     		}
								 		 	detailsList = detailsList +"</table>";
							 		}
							 		else
							 		{
							 			detailsList += "Card not assigned to any person";
							 		}
							 		
									 
									 //$("#contactDialogContent").text(contactList);
									 $('#dialogBoxforAcDetails').html("");
									 $('#dialogBoxforAcDetails').html(detailsList);
									 $('#dialogBoxforAcDetails').dialog({
							             	width: 475,
							             	position: 'center',
											modal : true,
										}); 
								}
								
							});
							
							
						}
        				</script>
        				<div id="dialogBoxforAcDetails" title="Card Assigned To"></div>
        				<style>
    .k-edit-label:after { content:" *"; }
</style>
  <!-- </div>
  </div> -->