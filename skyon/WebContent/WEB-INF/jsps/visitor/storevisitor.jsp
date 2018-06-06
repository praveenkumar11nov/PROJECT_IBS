<%@include file="/common/taglibs.jsp"%>

<c:url value="/common/getAllChecks" var="allChecksUrl" />
	<c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />



<c:url value="/storevisitor/create" var="createUrl" />
<c:url value="/storevisitor/read" var="readUrl" />
<c:url value="/storevisitor/update" var="updateUrl" />
<c:url value="/storevisitor/getContactNO_filter" var="filtercontactNo" />
<c:url value="/visitorvisits/readValues" var="readUrlForinnergrid" />
<c:url value="/storevisitor/nameForFilter" var="filtervisitorName" />
<c:url value="/storevisitor/addressForFilter" var="filtervisitoraddress" />
<c:url  value="/storevisitor/contactNoForFilter" var="filtervisitorContactNo"  />

 <kendo:grid name="grid" pageable="true" resizable="true" detailTemplate="template"  sortable="true" filterable="true" scrollable="true" height="430px" dataBound="visitorDataBound" selectable="true">
    	<kendo:grid-editable mode="popup"/>
        <kendo:grid-toolbar>
            <%-- <kendo:grid-toolbarItem name="create" text="Add Visitor Personal Details"/> --%>
             <kendo:grid-toolbarItem name="storeVisitorTemplatesDetailsExport" text="Export To Excel" /> 
            		  <kendo:grid-toolbarItem name="storeVisitorPdfTemplatesDetailsExport" text="Export To PDF" /> 
            <kendo:grid-toolbarItem text="Clear Filter" name="Clear_Filter"/>
        </kendo:grid-toolbar>
        
                <kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10"></kendo:grid-pageable>
        
         <kendo:grid-filterable extra="false">
		 <kendo:grid-filterable-operators>
		  	<kendo:grid-filterable-operators-string eq="Is equal to" startswith="Starts with"/>
		 </kendo:grid-filterable-operators>
	</kendo:grid-filterable> 

        
        <kendo:grid-columns>
      	   <%-- <kendo:grid-column title="ID" field="vmId" />    --%>
      	   
      	   <kendo:grid-column title="Image" field="image"
				template="<img src='./vistor/getvisitorimage/#=vmId#' id='myImages_#=vmId#' alt='No Image' width='80px' height='80px'/></span>"
				filterable="false" width="94px" sortable="false">
			</kendo:grid-column>
      	   
      	   <kendo:grid-column title="Contact-No" field="vmContactNo"   width="100px" >
      	   
      	   <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function ContactNoFilter(element) {
								element.kendoAutoComplete({
									dataSource: {
										transport: {
											read: "${filtervisitorContactNo}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	
      	    
      	   
      	   </kendo:grid-column>
            <kendo:grid-column title="Visitor-Name" field="vmName"   filterable="true"   width="150px">
            
             <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function ContactNoFilter(element) {
								element.kendoAutoComplete({
									dataSource: {
										transport: {
											read: "${filtervisitorName}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable>	
            </kendo:grid-column>
            
               <kendo:grid-column title="Gender" field="gender"
				editor="GenderDropDown" width="100px" >
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
            
            
            
            <kendo:grid-column title="Address" field="vmFrom"   width="100px" editor="VisitorAddress" filterable="false">
            <%-- <kendo:grid-column-filterable>
	    			<kendo:grid-column-filterable-ui>
    					<script> 
							function ContactNoFilter(element) {
								element.kendoAutoComplete({
									dataSource: {
										transport: {
											read: "${filtervisitoraddress}"
										}
									}
								});
					  		}
					  	</script>		
	    			</kendo:grid-column-filterable-ui>
	    		</kendo:grid-column-filterable> --%>
            
            </kendo:grid-column>
            
             <%-- <kendo:grid-column title="Created By" field="createdBy"   filterable="false" width="100px" />
             <kendo:grid-column title="Last Updated  By" field="lastUpdatedBy"   filterable="false" width="100px" /> --%>
             
             <kendo:grid-column title="Status" field="statusCheck"   width="100px" hidden="true" filterable="false" />
            <kendo:grid-column title="&nbsp;" width="160px" >
            	<kendo:grid-column-command>
            	 <kendo:grid-column-commandItem name="edit" click="edit"/> 
            		
            	</kendo:grid-column-command>
            </kendo:grid-column>
        </kendo:grid-columns>
        <kendo:dataSource pageSize="20" requestEnd="onRequestEnd">
            <kendo:dataSource-transport>
                <kendo:dataSource-transport-create url="${createUrl}" dataType="json" type="POST" contentType="application/json" />
                <kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="GET" contentType="application/json"/>
                 <kendo:dataSource-transport-update url="${updateUrl}" dataType="json" type="POST" contentType="application/json" />
             
                <kendo:dataSource-transport-parameterMap>
                	<script>
	                	function parameterMap(options,type) { 	                		
	                		return JSON.stringify(options);	                		
	                	}
                	</script>
                </kendo:dataSource-transport-parameterMap>
            </kendo:dataSource-transport>
            <kendo:dataSource-schema>
                <kendo:dataSource-schema-model id="vmId">
                    <kendo:dataSource-schema-model-fields>
                        <kendo:dataSource-schema-model-field name="vmId" editable="false" >
						</kendo:dataSource-schema-model-field> 
                        <kendo:dataSource-schema-model-field name="vmName" >
                        <kendo:dataSource-schema-model-field-validation required="true" />
                        </kendo:dataSource-schema-model-field>
                        <kendo:dataSource-schema-model-field name="vmFrom" >
                        <kendo:dataSource-schema-model-field-validation required="true" />
                        </kendo:dataSource-schema-model-field>
                        <kendo:dataSource-schema-model-field name="vmContactNo"  >
                        <!-- pattern="\d{10}" -->
                        <kendo:dataSource-schema-model-field-validation required="true"  />
                        </kendo:dataSource-schema-model-field>
                        <kendo:dataSource-schema-model-field name="statusCheck"/>
                        
                    <%--  <kendo:dataSource-schema-model-field name="drGroupId" type="number"  /> --%>
                        <%-- <kendo:dataSource-schema-model-field name="createdBy" type="string" editable="false" >
                        	<kendo:dataSource-schema-model-field-validation required="true" />
                        </kendo:dataSource-schema-model-field>
                        <kendo:dataSource-schema-model-field name="lastUpdatedBy" type="string" editable="false">
                        	<kendo:dataSource-schema-model-field-validation required="true" /> 
                        </kendo:dataSource-schema-model-field>--%>
                        
                    </kendo:dataSource-schema-model-fields>
                </kendo:dataSource-schema-model>
            </kendo:dataSource-schema>
        </kendo:dataSource>
    </kendo:grid>  
    
    <kendo:grid-detailTemplate id="template">
		<kendo:grid name="grid_#=vmId#" sortable="true" scrollable="true">
						
			<kendo:grid-columns>
			<kendo:grid-column title="Contact-No" field="vmContactNo" filterable="true" width="100px" /> 
            <kendo:grid-column title="Visitor-Name" field="vmName" width="150px"/>            
            <kendo:grid-column title="Block Name" field="blockName" hidden="true" width="100px" />            
            <kendo:grid-column title="Property Number" field="property_No" width="120px"/>   
            <kendo:grid-column title="Access-Card-No" field="acId"   width="120px" />  
            
            <kendo:grid-column title="Purpose-Of-Visit" field="vpurpose"   filterable="false" width="150px"  /> 
               <kendo:grid-column title="Visitor Status" field="vvstatus"  filterable="true" width="150px"  />
             <kendo:grid-column title="Entry-Time" field="vinDt"     filterable="false" width="150px"  /> 
             
           
           <kendo:grid-column title="Exit-Time" field="voutDt" filterable="false" width="150px"  />
           
            
			</kendo:grid-columns>
			
			<kendo:dataSource pageSize="5" requestEnd="onRequestEnd">
				<kendo:dataSource-transport>
					<kendo:dataSource-transport-read url="${readUrlForinnergrid}/#=vmId#" dataType="json" type="GET" contentType="application/json" />					
					<kendo:dataSource-transport-parameterMap>
						<script>
							function parameterMap(options, type) 
							{
								return JSON.stringify(options);
							}
						</script>
					</kendo:dataSource-transport-parameterMap>
				</kendo:dataSource-transport>
				<%-- <kendo:dataSource-schema>
					<kendo:dataSource-schema-model>
						<kendo:dataSource-schema-model-fields>

							<kendo:dataSource-schema-model-field name="urLoginName">
								<kendo:dataSource-schema-model-field-validation required="true" />
							</kendo:dataSource-schema-model-field>
							
						</kendo:dataSource-schema-model-fields>
					</kendo:dataSource-schema-model>
				</kendo:dataSource-schema> --%>
			</kendo:dataSource>
		</kendo:grid>
	</kendo:grid-detailTemplate>
    
    
    
    
    
  
    
      
 
 <script> 

 $("#grid").on("click",".k-grid-storeVisitorTemplatesDetailsExport", function(e) {
	  window.open("./storeVisitorTemplate/storeVisitorTemplatesDetailsExport");
 });
 
 $("#grid").on("click",".k-grid-storeVisitorPdfTemplatesDetailsExport", function(e) {
	  window.open("./storeVisitorPdfTemplate/storeVisitorPdfTemplatesDetailsExport");
 });
 

 function dataBound() {
		this.expandRow(this.tbody.find("tr.k-master-row").first());
	}
 
function visitorDataBound(e) 
{
	var data = this.dataSource.view(),row;
    var grid = $("#grid").data("kendoGrid");
    for (var i = 0; i < data.length; i++) {
    	var currentUid = data[i].uid;
        row = this.tbody.children("tr[data-uid='" + data[i].uid + "']");
        
        var statusCheck = data[i].statusCheck;
        if(statusCheck=="OUT"){
        	
        	var currenRow = grid.table.find("tr[data-uid='" + currentUid+ "']");
			var reOpenButton = $(currenRow).find('.k-grid-edit');
			reOpenButton.hide();
        }
    }
}

//register custom validation rules
 (function ($, kendo) {
     $.extend(true, kendo.ui.validator, {
          rules: { // custom rules
              Visitornamevalidation: function (input, params) {
                  //check for the name attribute 
                  if (input.filter("[name='vmName']").length && input.val()) {
                      return /^[A-Za-z ]*$/.test(input.val());
                  }
                  return true;
              }
          },
          messages: { //custom rules messages
         	 Visitornamevalidation: "Visitor Name should use only alphabets "
          }
     });
 })(jQuery, kendo);
 (function ($, kendo) {
     $.extend(true, kendo.ui.validator, {
          rules: { // custom rules
              VisitorContactvalidation: function (input, params) {
                  //check for the name attribute 
                  if (input.filter("[name='vmContactNo']").length && input.val()) {
                      return  /[0-9]{7,13}|\./.test(input.val());
                  }
                  return true;
              }
          },
          messages: { //custom rules messages
        	  VisitorContactvalidation: "Contact Number can allows numbers only and min 7 digits and max 13 digits"
          }
     });
 })(jQuery, kendo);





	function VisitorAddress(container, options) 
	{
     $('<textarea data-text-field="vmFrom" data-value-field="vmFrom" data-bind="value:' + options.field + '" style="width: 148px; height: 40px;"/>')
          .appendTo(container);
	}

	function GenderDropDown(container, options) {
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


 
/*Spring Method level Security for Add Button  */
$("#grid").on("click", ".k-grid-Clear_Filter", function(){
		    //custom actions
		    $("form.k-filter-menu button[type='reset']").slice().trigger("click");
			var grid = $("#grid").data("kendoGrid");
			grid.dataSource.read();
			grid.refresh();

		});

            
function edit(e){

	$('label[for="image"]').hide();
	$('div[data-container-for="image"]').hide();
	
	$('label[for=statusCheck]').parent().hide();
	$('div[data-container-for="statusCheck"]').hide();
	
	
	securityCheckForActions("./visitormanagement/visitormaster/updateButton");
	/* $.ajax({
			type : "POST",
			url : "./visitormanagement/storevisitor/updateButton",
			success : function(response) {
				if (response == "false") {
					$("#alertsBox").html("");
					$("#alertsBox").html("Access Denied");
					$("#alertsBox").dialog({
						modal: true,
						buttons: {
							"Close": function() {
								$( this ).dialog( "close" );
							}
						}
					}); 
					var grid = $("#grid").data("kendoGrid");
					grid.cancelRow();
					grid.close();
				}else if (response == "timeout") {
					$("#alertsBox").html("");
					$("#alertsBox").html("Session Timeout Please Login Again");
					$("#alertsBox").dialog({
						modal: true,
						buttons: {
							"Close": function() {
								$( this ).dialog( "close" );
							}
						}
					}); 
					window.location.href = "./logout";
					
				}
			}
		});         */

}
			

            $("#grid").on("click", ".k-grid-add", function() {
     	 	   $(".k-window-title").text("Add Visitor Personal Details");
     	 	   $(".k-grid-update").text("Add/Create");
     	 	   $(".k-grid-Activate").hide();
     	 	 $('.k-edit-field .k-input').first().focus();


     	 	  /* $('input[name="vmContactNo"]').on("change","",function(e){ 	

     	 		 var visitorContact="visitorContact="+$('input[name="vmContactNo"]').val();
   	 		  alert(vistorContact);
   	 		  $.ajax({
   	 			 type : "POST",
   	   			 url : "./storevisitor/visitorContactNo",
   	   			 data : visitorContact,
   	   			 dataType : 'text',
   	   			 success : function(response) {
       	   			 alert(response);
   	   				$('input[name="vmName"]').val(response);	  
   	   				$('input[name="vmName"]').change();   
   	   			    $('input[name="vmFrom"]').val(response);	  
	   				    $('input[name="vmFrom"]').change();   				
	   			     }				
   	   			
   	   					
   	 		  });



         	 	    }); */
     	    });





            

/* function onRequestEnd(e) {
    debugger;
    if (e.type == "update" && !e.response.Errors) {
        alert("Update record is successfull");
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

 */


function VisitorName(container, options) {
	   $(
	     '<input data-text-field="vmName" data-value-field="vmName" data-bind="value:' + options.field +  '"/>')
	     .appendTo(container).KendoAutoComplete({
	      defaultValue : false,
	      sortable : true,
	      cascadeFrom: "vmContactNo",
	      dataSource : {
	       transport : {
	        read : "${readUrl}"
	       }
	      }
	     });
	  }



 function onRequestEnd(e) {
 	  
		if (typeof e.response != 'undefined')
		{
			if (e.response.status == "FAIL") {
				errorInfo = "";
				errorInfo = e.response.result.invalid;
				for (i = 0; i < e.response.result.length; i++) {
				errorInfo += (i + 1) + ". "
						+ e.response.result[i].defaultMessage;
				}

				if (e.type == "create") {

					/* $("#alertsBox").html("");
    				$("#alertsBox").html("Error: Creating the Visitor record\n\n" + errorInfo);
    				$("#alertsBox").dialog({
    					modal : true,
    					buttons : {
    						"Close" : function() {
    							$(this).dialog("close");
    						}
    					}
    				}); */
					alert("Error: Creating the VisitorVisit record\n\n" + errorInfo);
				}
		
				if (e.type == "update") {
					/* $("#alertsBox").html("");
    				$("#alertsBox").html("Error: Updating the Visitor record\n\n" + errorInfo);
    				$("#alertsBox").dialog({
    					modal : true,
    					buttons : {
    						"Close" : function() {
    							$(this).dialog("close");
    						}
    					}
    				}); */
					alert("Error: Updating the VisitorVisit record\n\n" + errorInfo);
				}
		
				var grid = $("#grid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
				return false;
			}

			if (e.type == "update" && !e.response.Errors) {
				/* $("#alertsBox").html("");
				$("#alertsBox").html("Update record is successfull");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				}); */

				alert("Update record is successfull");
				var grid = $("#grid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			}
			
			if (e.type == "update" && e.response.Errors) {
				/* $("#alertsBox").html("");
				$("#alertsBox").html("Update record is Un-successfull");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				}); */
				alert("Update record is Un-successfull");
				var grid = $("#grid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			}

			if (e.type == "create" && !e.response.Errors) {
				/* $("#alertsBox").html("");
				$("#alertsBox").html("Create record is successfull");
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				}); */

				alert("Create record is successfull");
				var grid = $("#grid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			}
		}  			
		
	}
</script>
    