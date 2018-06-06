<%@include file="/common/taglibs.jsp"%>
<c:url value="/transactionmaster/readTransactionMasterUrl" var="readTransactionMasterUrl"/>
<c:url value="/transactionmaster/createTransactionMasterUrl" var="createTransactionMasterUrl"/>
<c:url value="/transactionmaster/updateTransactionMasterUrl" var="updateTransactionMasterUrl"/>
<c:url value="/transactionmaster/readTransactionNamesForUniqueness" var="readTransactionNamesForUniqueness"/>
<c:url value="/transactionmaster/readDesignamtionNamesForUniqueness" var="readDesignamtionNamesForUniqueness"/>

<c:url value="/users/getDesignation" var="designationUrl" />
<c:url value="/users/getDepartment" var="departmentUrl" />

<c:url value="/transactionchild/readTransctinUrl1" var="readTransctinUrl1"/> 
<c:url value="/transactionchild/createTransctinUrl1" var="createTransctinUrl1"/>
<c:url value="/transactionchild/updateTransctionUrl1" var="updateTransctionUrl1"/>
<c:url value="/transactionchild/deleteTransctionUrl1" var="deleteTransctionUrl1"/>

<c:url value="/common/getAllProcessNames" var="allProcessNames" />
<%-- <c:url value="/transaction/readDesignationUrl" var="readALLDesignationUrl"/> --%>
<c:url value="/transction/readALLTransactionCodeUrl" var="readALLTransactionCodeUrl"/>
<c:url value="/transction/getTransactionNameFilter" var="getTransactionNameFilter"/>

<c:url value="/patternMaster/readProcessNameForUniqueness" var="readProcessNameForUniqueness"/>

<!-- process related url  -->
<c:url value="/transaction/readAllProcessUrl" var="readAllProcessUrl"/>
<c:url value="/transactionmaster/readProcessIdForUniqueness" var="readProcessIdForUniqueness"/>

<c:url value="/common/getAllChecks" var="allChecksUrl"/>

<c:url value="/commonss/getAllChecks" var="processTypeUrl"/>
<c:url value="/commonss/getAllTransactionNames" var="getAllTransactionNames"/>



<!-- <div id="content"> -->

		<!-- <div class="row">
			<div class="col-xs-12 col-sm-7 col-md-7 col-lg-4">
				<h1 class="page-title txt-color-blueDark"><i class="fa-fw fa fa-home"></i>Pattern Master</h1>
			</div>
		</div> -->
   <kendo:grid name="transactionGrid" pageable="true" resizable="true" sortable="true" change="onChangeItemTemplate" dataBound="parentDataBound"  reorderable="true" selectable="true" scrollable="true" filterable="true" groupable="true" detailTemplate="TransactionChild" edit="transactionGridEvent" >				
      <kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" ></kendo:grid-pageable>
	  <kendo:grid-filterable extra="false">
	    <kendo:grid-filterable-operators>
		 <kendo:grid-filterable-operators-string eq="Is equal to"/>
	    </kendo:grid-filterable-operators>
	  </kendo:grid-filterable>
				
	 
		<kendo:grid-editable mode="popup" confirmation="Are you sure you want to remove this item?" />
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add New Pattern" />
		<%-- 	<kendo:grid-toolbarItem text="Clear_Filter" /> --%>
				   <kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterSalaryTemplate()>Clear Filter</a>"/>
		</kendo:grid-toolbar>
	  
	   <kendo:grid-columns>
	 
<%-- 	   		<kendo:grid-column title="Process Name&nbsp;*" field="processId" width="75px" hidden="true" />
 --%>	   	
 
			<kendo:grid-column title="Pattern Name&nbsp;*" field="name" width="75px" filterable="true">
			   <kendo:grid-column-filterable>
		    			<kendo:grid-column-filterable-ui>
	    					<script> 
								function statusFilter(element) {
									element.kendoAutoComplete({
										dataType: 'JSON',
										dataSource: {
											transport: {
												read: "${getTransactionNameFilter}"
											}
										}
									});
						  		}
						  	</script>		
		    			</kendo:grid-column-filterable-ui>
		    	</kendo:grid-column-filterable>
			</kendo:grid-column>	  	
        <kendo:grid-column title="Process Name&nbsp;*" field="processName" width="75px" filterable="true" editor="processNameEditor"/>
        
			
			<kendo:grid-column title="Pattern Code&nbsp;*" field="code"  filterable="false" width="50px" hidden="true">
			    <kendo:grid-column-filterable>
		    			<kendo:grid-column-filterable-ui>
	    					<script> 
								function statusFilter(element) {
									element.kendoAutoComplete({
										dataType: 'JSON',
										dataSource: {
											transport: {
												read: "${getTransactionCodeFilter}"
											}
										}
									});
						  		}
						  	</script>		
		    			</kendo:grid-column-filterable-ui>
		    	</kendo:grid-column-filterable>
			</kendo:grid-column>
			<kendo:grid-column title="Level&nbsp;*" field="level" width="35px" filterable="true">
		     </kendo:grid-column>
		     
		    <kendo:grid-column title="Pattern Description&nbsp;*" field="description" width="75px" filterable="true" editor="transactionDescriptionFilter">
		       <kendo:grid-column-filterable>
		    			<kendo:grid-column-filterable-ui>
	    					<script> 
								function statusFilter(element) {
									element.kendoAutoComplete({
										dataType: 'JSON',
										dataSource: {
											transport: {
												read: "${getTransactionDescFilter}"
											}
										}
									});
						  		}
						  	</script>		
		    			</kendo:grid-column-filterable-ui>
		    	</kendo:grid-column-filterable>
		    </kendo:grid-column>
		     
	   <kendo:grid-column title="Pattern Status&nbsp;*" field="transactionStatus" width="75px" filterable="true"/>
		     
		 <%--  <kendo:grid-column title="&nbsp;" width="50px">
			   <kendo:grid-column-command>
				  <kendo:grid-column-commandItem name="edit"/>			
			   </kendo:grid-column-command>
			</kendo:grid-column>  --%>
			
		 <kendo:grid-column title="" template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Activate#=data.tId#'>#= data.transactionStatus == 'Active' ? 'De-activate' : 'Activate' #</a>"
				width="50px" >						
				</kendo:grid-column> 
				
				<%--  <kendo:grid-column title="Store&nbspStatus&nbsp;*" field="patternStatus"  filterable="true" width="130px" /> --%>
		<%-- 	<kendo:grid-column title=""
				template="<a href='\\\#' id='temPID' class='k-button k-button-icontext btn-destroy k-grid-Activate#=data.tId#'>#= data.patternStatus == 'Active' ? 'De-activate' : 'Activate' #</a>"
				width="100px" /> --%>
					
	   </kendo:grid-columns>
	   
	   <kendo:dataSource pageSize="20" requestEnd="ontransactionRequestEnd" requestStart="onTransactionRequestStart">
	   <kendo:dataSource-transport>
			<kendo:dataSource-transport-read url="${readTransactionMasterUrl}" dataType="json" type="POST" contentType="application/json" />
			<kendo:dataSource-transport-create url="${createTransactionMasterUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-update url="${updateTransactionMasterUrl}" dataType="json" type="GET" contentType="application/json" />
		 </kendo:dataSource-transport>
		 <kendo:dataSource-schema>
			<kendo:dataSource-schema-model id="tId">
			  <kendo:dataSource-schema-model-fields>
			    <kendo:dataSource-schema-model-field name="tId" type="number"/> 
			    <kendo:dataSource-schema-model-field name="name" type="string"/>
			    <kendo:dataSource-schema-model-field name="code" type="string"/>
			    
			    <kendo:dataSource-schema-model-field name="level" type="number"  >
			    <kendo:dataSource-schema-model-field-validation min="0"/>
			   </kendo:dataSource-schema-model-field>
			    <kendo:dataSource-schema-model-field name="description" type="string"/>
	  <kendo:dataSource-schema-model-field name="transactionStatus" type="string" defaultValue="Inactive"/>
			    <kendo:dataSource-schema-model-field name="processName"/>
	<%-- 			    	 <kendo:dataSource-schema-model-field name="patternStatus" type="string" defaultValue="Inactive"/>
		    <kendo:dataSource-schema-model-field name="processId" type="number" defaultValue=""/>  
 --%>			  </kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		 </kendo:dataSource-schema>
		</kendo:dataSource>
	  </kendo:grid>	
	  
	  
	  
	  <kendo:grid-detailTemplate id="TransactionChild">
		<kendo:tabStrip name="tabStrip_#=tId#">
		
			<kendo:tabStrip-items>
			
 			<kendo:tabStrip-item selected="true" text="Pattern Details">
                <kendo:tabStrip-item-content>
                <kendo:grid name="transactionChild_#=tId#" pageable="true" resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true" filterable="true" groupable="true" edit="transactionChieldGridEvent" dataBound="dataBound">				
      <kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" ></kendo:grid-pageable>
	  <kendo:grid-filterable extra="false">
	    <kendo:grid-filterable-operators>
		 <kendo:grid-filterable-operators-string eq="Is equal to"/>
	    </kendo:grid-filterable-operators>
	  </kendo:grid-filterable>
				
	 <kendo:grid-editable mode="popup" confirmation="Are you sure you want to remove this item?" />
	   <kendo:grid-toolbar>
		  <kendo:grid-toolbarItem name="create" text="Add Pattern Details" />
		  <kendo:grid-toolbarItem text="Clear_Filter"/>
	   </kendo:grid-toolbar>
	   <kendo:grid-columns>
			<kendo:grid-column title="Level Number&nbsp;*" field="lNum" width="75px" filterable="true">
			  
			</kendo:grid-column>
			 <%-- <kendo:grid-column title="Code*" field="tId" width="75px" editor="transactionEditor" filterable="true" hidden="true"/> 
			   <kendo:grid-column title="CodeName*" field="code" width="75px"  filterable="true"  />   --%>
			                                                                                                            
			
	 	
		   
		    
		     <kendo:grid-column title="Department;*" field="dept_Id" width="75px" editor="transactionDepartment" hidden="true"/>
		   <%--  <kendo:grid-column title="Designation;*" field="dept_Name" width="75px"/>   --%>
		    
		      <kendo:grid-column title="Designation;*" field="dnId" width="75px" editor="transactionChild" hidden="true"/>
		    <kendo:grid-column title="Designation;*" field="dn_Name" width="75px"/>  
		    
		  <%--   <kendo:grid-column title="Department *" field="dept_Name"	editor="departmentEditor" width="100px"/>
		    <kendo:grid-column title="Designation *" field="dn_Name" editor="designationEditor" width="100px"/> --%>
		    
		    <kendo:grid-column title="&nbsp;" width="50px">
			   <kendo:grid-column-command>
				  <kendo:grid-column-commandItem name="edit"/>			
			   </kendo:grid-column-command>
			</kendo:grid-column>	
		    </kendo:grid-columns>
	    					
	 
	   <kendo:dataSource pageSize="20" requestEnd="onTransactionChieldRequestEnd" requestStart="onTransactionChildRequestStart">
		 <kendo:dataSource-transport>
			<kendo:dataSource-transport-read url="${readTransctinUrl1}/#=tId#" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-create url="${createTransctinUrl1}/#=tId#" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-update url="${updateTransctionUrl1}/#=tId#" dataType="json" type="GET" contentType="application/json" />
			<%-- <kendo:dataSource-transport-destroy url="${deleteTransctionUrl1}/#=tId#" dataType="json" type="GET" contentType="application/json" /> --%>
		 </kendo:dataSource-transport>

		 <kendo:dataSource-schema>
			<kendo:dataSource-schema-model id="id">
			  <kendo:dataSource-schema-model-fields>
			    <kendo:dataSource-schema-model-field name="id" type="number"/>
			   
			    <kendo:dataSource-schema-model-field name="lNum" type="number" >
			    <kendo:dataSource-schema-model-field-validation min="1"/>
			   </kendo:dataSource-schema-model-field>
			   
			  <kendo:dataSource-schema-model-field name="dnId" type="number"/> 
			  	  <kendo:dataSource-schema-model-field name="dept_Id" type="number" defaultValue=""/> 
			    <kendo:dataSource-schema-model-field name="tId" type="number" />
			    <kendo:dataSource-schema-model-field name="dn_Name"  />
			  <%--   <kendo:dataSource-schema-model-field name="dept_Name">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="dn_Name">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field> --%>
			  
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
	 <!--  </div> -->
	  
	  <div id="alertsBox" title="Alert"></div>
	  <script>
	  var rateType1="";
	
	    
	 /* 	function processNameEditor(container,options)
		 {
			 $('<input data-text-field="name" name="Process Name" required="true" data-value-field="value" data-bind="value:' + options.field + '"/>')
		     .appendTo(container)
		         .kendoDropDownList({
		        	 optionLabel: "Select Process Name",
		        	// select : componentMasterData,
		         dataSource: {  
		             transport:{
		                 read: "${processTypeUrl}"
		             }
		         },
		         
		         placeholder :"Select",
		         change : function(e) {
		             if (this.value() && this.selectedIndex == -1) {
		              alert("Process doesn't exist!");
		              $("#processName").data("kendoComboBox").value("");
		             }
		            },
		     });
				
			 $('<span class="k-invalid-msg" data-for="Process Name"></span>').appendTo(container);
		
		 }  */
	 	
	 	function processNameEditor(container, options) {
			var res = (container.selector).split("=");
			var attribute = res[1].substring(0,res[1].length-1);
			
			$('<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '"required ="true" name = "'+attribute+'" id = "'+attribute+'Id"/>')
					.appendTo(container).kendoDropDownList({
					 optionLabel : {
							text : "Select",
							value : "",
						}, 
						defaultValue : false,
						sortable : true,
						dataSource : {
							transport : {
								read : "${processTypeUrl}/"+attribute,
							}
						}
					});
			 $('<span class="k-invalid-msg" data-for="'+attribute+'"></span>').appendTo(container);
		}
	 	function clearFilterSalaryTemplate() {
			$("form.k-filter-menu button[type='reset']").slice()
					.trigger("click");
			var gridStoreIssue = $("#transactionGrid").data("kendoGrid");
			gridStoreIssue.dataSource.read();
			gridStoreIssue.refresh();
		}

	    function componentMasterData(){
	    	//alert(value);
	    //	var processName = $("input[name=processName]").value();
	  //  var p1=	$('input.val(processName));	
	    	
	    //	alert("----"+p1.value );
	    	
         
	    }
	  var SelectedRowId = "";
	  var level ="";
	  var tId="";
	  function onChangeItemTemplate(arg) {
		  
	  	 var gview = $("#transactionGrid").data("kendoGrid");
	  	 var selectedItem = gview.dataItem(gview.select());
	  	 SelectedRowId = selectedItem.tId;
	  	 level = selectedItem.level;
	  	 tId=selectedItem.tId;
	  	 //alert(tId);
	  	 //alert(tId);
	  	if(level == 0){
			$(".k-grid-add", "#transactionChild_" + SelectedRowId).hide();
		}
	  	 this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
	  	 
			
	  	
	  }
	  /* function spinnerEditor(container, options){
			$('<input data-bind="value:' + options.field + '"/>')
		    .appendTo(container)
		    .kendoNumericTextBox({
		         spinners : false,
		    }).attr('readonly',true); 	
	  } */
	  function parentDataBound(e){
		 // alert("databound");
		  var data = this.dataSource.view(), row;
			var grid = $("#transactionGrid").data("kendoGrid");
			for (var i = 0; i < data.length; i++) {
				row = this.tbody.children("tr[data-uid='" + data[i].uid + "']");
				var pettyStatus = data[i].pettyStatus;
				var currentUid = data[i].uid;
				
			}
			
	  }
	  function dataBound(e) {
			var data = this.dataSource.view(), row;
			var grid = $("#transactionGrid").data("kendoGrid");
			for (var i = 0; i < data.length; i++) {

				row = this.tbody.children("tr[data-uid='" + data[i].uid + "']");
				var lNum = data[i].lNum;
				
				if(level == lNum ){
					$(".k-grid-add", "#transactionChild_" + SelectedRowId)
					.hide();
				}
			}
			if(level==0){
				$(".k-grid-add", "#transactionChild_" + SelectedRowId)
				.hide();
			}
	  }
	  
	
	   

		$("#transactionGrid").on("click", ".k-grid-Clear_Filter", function()
				{
					 $("form.k-filter-menu button[type='reset']").slice().trigger("click");
					 var grid = $("#transactionGrid").data("kendoGrid");
					 grid.dataSource.read();
					 grid.refresh();
				});

				  function transactionChild(container,options)
				  {
					  $('<input data-text-field="dnName" name="Designation" id="dnIds" required="true" data-value-field="dnId" data-bind="value:' + options.field + '"/>').appendTo(container).kendoComboBox
						 ({
							 cascadeFrom : "dept_Id",
						   	 placeholder : "Select Designation",
						     template : '<table><border><tr><td rowspan=2><span class="k-state-default"></span></td>'
								+ '<td padding="5px"><span class="k-state-default"><b>#: data.dnName #</b></span><br></td></tr></border></table>',
								
							    dataSource: {       
						           transport: {
						              // read: "${readALLDesignationUrl}+"
						              read:"./transaction/readDesignationUrl/"+tId,
						           }
						       },
						   });
						   $('<span class="k-invalid-msg" data-for="Designation"></span>').appendTo(container);   
				  }
				  function transactionDepartment(container,options)
				  {
					  $('<input data-text-field="dept_Name" name="Department" id="dept_Id" required="true" data-value-field="dept_Id" data-bind="value:' + options.field + '"/>').appendTo(container).kendoComboBox
						 ({
						   	 placeholder : "Select Department",
						     template : '<table><border><tr><td rowspan=2><span class="k-state-default"></span></td>'
								+ '<td padding="5px"><span class="k-state-default"><b>#: data.dept_Name #</b></span><br></td></tr></border></table>',
								
							    dataSource: {       
						           transport: {
						              // read: "${readALLDesignationUrl}+"
						              read:"./transaction/readDepartmentUrl/"+tId,
						           }
						       },
						   });
						   $('<span class="k-invalid-msg" data-for="Designation"></span>').appendTo(container);   
				  }
				  
				  
				   function transactionEditor(container,options)
				  {
					  $('<input data-text-field="code" name="Name" id="tId" required="true" data-value-field="tId" data-bind="value:' + options.field + '"/>').appendTo(container).kendoComboBox
						 ({
						   	 placeholder : "Select Code",
						     template : '<table><border><tr><td rowspan=2><span class="k-state-default"></span></td>'
								+ '<td padding="5px"><span class="k-state-default"><b>#: data.code #</b></span><br></td></tr></border></table>',
								
							    dataSource: {       
						           transport: {
						               read: "${readALLTransactionCodeUrl}"
						           }
						       },
						   });
						   $('<span class="k-invalid-msg" data-for="transaction"></span>').appendTo(container);   
				  } 
				  
				  var res1 = new Array();
				  
				  function transactionChieldGridEvent(e)
				  {
					  $(".k-window-title").text("Add Transaction Details");
					  $(".k-grid-update").text("Save");
						
					  $('a[id="temPID"]').remove();		 
					  
					 $('label[for=dn_Name]').parent().hide();
					  $('div[data-container-for="dn_Name"]').hide();
					  
					   $('label[for=code]').parent().hide(); 
					  $('div[data-container-for="code"]').hide();

					   $('label[for=lNum]').parent().hide(); 
					  $('div[data-container-for="lNum"]').hide();
					  
					  $(".k-grid-cancel").click(function () {
						   var grid = $("#transactionGrid").data("kendoGrid");
						   grid.dataSource.read();
						   grid.refresh();
						  }); 
					  
					/*   
					  if (e.model.isNew()) 
					  {
						 //alert( $('input[name="dnName"]').val());
						 //securityCheckForActions("./userManagement/designation/createButton");
						 res3 = [];
						 $.ajax
						 ({
						      type : "GET",
							  dataType:"text",
							 // url : "./read/designationUniqueNess/"+tId,
							  dataType : "JSON",
							  success : function(response) 
							  {  
							  alert(response);
								 for(var i = 0; i<response.length; i++) 
								 { 
								   res3[i] = response[i];	
							     }
						      }
						 });
					  }
					  else
						  {
						  res4 = [];
							 $.ajax
							 ({
							      type : "GET",
								  dataType:"text",
								  url : "./read/designationUniqueNess/"+tId,
								  dataType : "JSON",
								  success : function(response) 
								  {  
									 
									 for(var i = 0; i<response.length; i++) 
									 { 
									   res4[i] = response[i];	
								     }
							      }
							 });
						  }
					   */
					  
				 }
				  
				  function onTransactionChieldRequestEnd(e) 
				  { 
				    if (typeof e.response != 'undefined') {
				     if (e.response.status == "FAIL") {
				      errorInfo = "";
				      for (var i = 0; i < e.response.result.length; i++) {
				       errorInfo += (i + 1) + ". "
				         + e.response.result[i].defaultMessage + "<br>";
				      }
				        if (e.type == "create") {
				       $("#alertsBox").html("");
				       $("#alertsBox").html(
				         "Error: Creating the process details \n\n : "
				           + errorInfo);
				       $("#alertsBox").dialog({
				        modal : true,
				        buttons : {
				         "Close" : function() { 
				 
				          $(this).dialog("close");
				         
				         }
				        }
				       });
				      }
				      /* var grid = $("#transactionChild_"+SelectedRowId).data("kendoGrid");
				      grid.dataSource.read(); */
				      
				      var grid = $("#transactionGrid").data("kendoGrid");
						//grid.refresh()
				      
				      
				      grid.refresh();
				     } 
				     else if (e.type == "create") {
				      $("#alertsBox").html("");
				      $("#alertsBox").html(
				        "Designation Added successfully");
				      $("#alertsBox").dialog({
				       modal : true,
				       buttons : {
				        "Close" : function() {
				         $(this).dialog("close");
				       
				        }
				       }
				      });
				      var grid = $("#transactionGrid").data("kendoGrid");
				      /* var grid = $("#transactionChild_"+SelectedRowId).data("kendoGrid");
				     grid.dataSource.read(); */
				      grid.refresh(); 
				     
				      } else if (e.type == "destroy") {
				      $("#alertsBox").html("");
				      $("#alertsBox").html(
				        "Pattern details deleted successfully");
				      $("#alertsBox").dialog({
				       modal : true,
				       buttons : {
				        "Close" : function() {
				         $(this).dialog("close");
				        }
				       }
				      });

				      var grid = $("#transactionChild_"+SelectedRowId).data("kendoGrid"); 
				      grid.dataSource.read();
				      grid.refresh();
				     } else if (e.type == "update") {
				      $("#alertsBox").html("");
				      $("#alertsBox").html(
				        "Designation updated successfully");
				      $("#alertsBox").dialog({
				       modal : true,
				       buttons : {
				        "Close" : function() {
				         $(this).dialog("close");
				        }
				       }
				      });

				     /*  var grid = $("#transactionChild_"+SelectedRowId).data("kendoGrid");
				       grid.dataSource.read();
				      grid.refresh(); */ 
				      
				      var grid = $("#transactionGrid").data("kendoGrid");
						grid.refresh()     
				      
				     }

				    }

				   }
				 
				  
				  function onTransactionChildRequestStart(e)
				  {
					    $('.k-grid-update').hide();
				        $('.k-edit-buttons')
				                .append(
				                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
				        $('.k-grid-cancel').hide();
					  
					  /*  var grid= $("#transactionChild_").data("kendoGrid"); 
					       grid.cancelRow(); */
					  
					  
				  }	
				  
				  
				  ////////////////Transaction master starts here/////////////////////////
			  
				  
	  
	 
	  function transactionGridEvent(e)
	  {
		  $(".k-window-title").text("Add Pattern Details");
		  $(".k-grid-update").text("Save");
		  $('label[for=transactionStatus]').parent().hide();
		  $('div[data-container-for="transactionStatus"]').hide(); 
		  $('label[for=status]').parent().hide();
		  $('div[data-container-for="status"]').hide();
		  $('a[id="temPID"]').remove();	
		  $('label[for=code]').parent().hide();
		  $('div[data-container-for="code"]').hide();
		  
		 /*  $('label[for=name]').parent().hide();
		  $('div[data-container-for="name"]').hide(); */
		  
		 /*  $('input[name="processName"]').attr('readonly', 'readonly'); */
		  
		/*  $('label[for=processId]').parent().hide();
		  $('div[data-container-for="processId"]').hide(); */
		  
		 /*  $('input[name="processId"]').val(SelectedPersonStyleOwner);
			$('input[name="processId"]').attr('readonly', 'readonly'); */
			//alert(SelectedPersonStyleOwner);
			
			
			
			/* $(".k-grid-cancel").click(function () {
		   var grid = $("#transactionGrid").data("kendoGrid");
		   grid.dataSource.read();
		   grid.refresh();

		   //alert("hi");
		  }); */
		  
		  if (e.model.isNew()) 
		  {
			  
			 //securityCheckForActions("./userManagement/designation/createButton");
			 res1 = [];
			 $.ajax
			 ({
			      type : "GET",
				  dataType:"text",
				  url : './commonss/getAllTransactionUniqueness',
				  dataType : "JSON",
				  success : function(response) 
				  {
					 for(var i = 0; i<response.length; i++) 
					 {
						// alert(res1[i]);
					   res1[i] = response[i];	
				     }
			      }
			 });
		
			 /* res2 = [];
			 $.ajax
			 ({
			      type : "GET",
				  dataType:"text",
				  url : '${readProcessIdForUniqueness}',
				  dataType : "JSON",
				  success : function(response) 
				  {
					 for(var i = 0; i<response.length; i++) 
					 {
						 //alert(response[i]);
					   res2[i] = response[i];	
				     }
			      }
			 });  */
		  }
		  else
		  {
			  //securityCheckForActions("./userManagement/designation/updateButton");  
		  }
	  }
	  function ontransactionRequestEnd(e)
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
						alert("Error: Creating the Transaction Details\n\n" + errorInfo);
					}
					if (e.type == "update") 
					{
						alert("Error: Updating the Transaction Details\n\n" + errorInfo);
					}
					var grid = $("#transactionGrid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
					return false;
				}			
				if (e.type == "update" && !e.response.Errors) 
				{	
					e.sender.read();
					$("#alertsBox").html("Alert");
					$("#alertsBox").html("Pattern Updated successfully");
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
					var grid = $("#transactionGrid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
				}
				if (e.type == "create" && !e.response.Errors)
				{
					e.sender.read();
					$("#alertsBox").html("Alert");
					$("#alertsBox").html("Pattern Created successfully");
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
					var grid = $("#transactionGrid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
				}
				if(e.type == "destroy" && !e.response.Errors)
				{
					$("#alertsBox").html("Alert");
					$("#alertsBox").html("Pattern Deleted successfully");
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
					var grid = $("#transactionGrid").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
				} 
			 } 
	  }
	  function onTransactionRequestStart(e)
	  {
		    $('.k-grid-update').hide();
	        $('.k-edit-buttons')
	                .append(
	                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
	        $('.k-grid-cancel').hide();
	        
		  /* var grid= $("#transactionGrid").data("kendoGrid");
		  //grid.cancelRow();
		  grid.refresh(); */
	  }	
	  
	  $("#transactionGrid").on("click", "#temPID", function(e) 
			  {
				 var button = $(this), enable = button.text() == "Activate";
				 var widget = $("#transactionGrid").data("kendoGrid");
				 var dataItem = widget.dataItem($(e.currentTarget).closest("tr"));
				 /* securityCheckForActionsForStatus("./userManagement/department/statusButton"); */
				 var result="success";			
			     if(result=="success")
			     {
					 if (enable)
					 {
						$.ajax
						({
							type : "POST",
							url : "./transaction/transactionStatus/" + dataItem.id + "/activate",
							dataType : 'text',
							success : function(response) 
							{
								$("#alertsBox").html("");
								$("#alertsBox").html(response);
						  	    $("#alertsBox").dialog
								({
									modal : true,
									buttons : {
									   "Close" : function() {
								          $(this).dialog("close");
										}
									}
								});
								button.text('Deactivate');
								$('#transactionGrid').data('kendoGrid').dataSource.read();
						grid.refresh();
							}
						});
					 }
					 else 
					 {
					      $.ajax
						  ({
							   type : "POST",
							   url : "./transaction/transactionStatus/" + dataItem.id + "/deactivate",
							   dataType : 'text',
							   success : function(response) 
							   {
								   $("#alertsBox").html("");
								   $("#alertsBox").html(response);
								   $("#alertsBox").dialog({
									   modal : true,
									   buttons : 
									   {
										   "Close" : function() {
												$(this).dialog("close");
											 }
											}
									   });
									  button.text('Activate');
									  $('#transactionGrid').data('kendoGrid').dataSource.read();
							grid.refresh();
									 }
									});
					 }	
			     }
			   });
	  function departmentEditor(container, options) {
			$(
					'<input name="dept_Name" data-text-field="dept_Name" id="dept_Id" data-value-field="dept_Name" data-bind="value:' + options.field + '" required="required"/>')
					.appendTo(container).kendoDropDownList({
						optionLabel : "Select",
						dataSource : {
							transport : {
								read : "${departmentUrl}"
							}
						}
						
					});
			 $('<span class="k-invalid-msg" data-for="dept_Name"></span>').appendTo(container); 
		}

		function designationEditor(container, options) {
			$(
					'<input name="dn_Name" data-text-field="dn_Name" data-value-field="dn_Name" data-bind="value:' + options.field + '"/>')
					.appendTo(container).kendoDropDownList({
						optionLabel : "Select",
						cascadeFrom : "dept_Id",
						dataSource : {
							transport : {

								read : "${designationUrl}"

							}
						}

					});
			$('<span class="k-invalid-msg" data-for="dn_Name"></span>').appendTo(container);
		}

	  function transactionDescriptionFilter(container,options)
	  {
		  $('<textarea name="Pattern Description" data-text-field="description" data-value-field="description" data-bind="value:' + options.field + '" style="width: 161px; height: 46px;" required="true"/>').appendTo(container);
		  $('<span class="k-invalid-msg" data-for="Pattern Description"></span>').appendTo(container); 
	  }
	  
	  
	//Validator Function
		 (function($, kendo) 
		  {
			 $.extend(true,kendo.ui.validator,
			 {
				rules : { 
					
					levelValidator : function(input,params) 
  				  { 
  					  if (input.attr("name") == "level") 
  					  {
						     return $.trim(input.val()) !== "";
						  }
						  return true;
  		           }, 
			  		      transactionNamevalidation : function(input, params) 
			  		      { 
							 if (input.filter("[name='name']").length && input.val()) 
							 {
								return /^[a-zA-Z]+[ ._a-zA-Z0-9._]*[a-zA-Z0-9]$/.test(input.val());
							 }
							 return true;
						  },
						  transactionDescription : function(input,params) 
						  { 
							 if (input.filter("[name='description']").length&& input.val()) 
							 {
							     return /^[a-zA-Z]+[._a-zA-Z0-9]*[a-zA-Z0-9]$/.test(input.val());
							 }
							 return true;
						  },
						  transactionNullValidator : function(input,params) 
	    				  { 
	    					  if (input.attr("name") == "name") 
	    					  {
	  						     return $.trim(input.val()) !== "";
	  						  }
	  						  return true;
	    		           }, 
	    		           transactionNameUniqueness : function(input,params) 
						   {
						        //check for the name attribute 
						        if (input.filter("[name='name']").length && input.val()) 
						        {
						          enterdService = input.val().toUpperCase(); 
						          for(var i = 0; i<res1.length; i++) 
						          {
						        	  //alert("======"+res1[i]);
						            if ((enterdService == res1[i].toUpperCase()) && (enterdService.length == res1[i].length) ) 
						            {								            
						              return false;								          
						            }
						          }
						         }
						         return true;
						    },
						    
						  /*   designationName : function(input,params) 
							   {       
						    	 alert($('input[name="dnName"]').val());
							        if ($('input[name="dnName"]').val().length && input.val()) 
							        	
							      		{
							              alert("inside");
							       
							        
							          for(var i = 0; i<res3.length; i++) 
							          {
							        	
							            if((enterdService == res3[i].toUpperCase()) && (enterdService.length == res3[i].length)) 
							            {
							            	
							              return false;								          
							            }
							          }
							         }
							         return true; 
							    }, */
						    
				/* 	processNameUniqueness : function(input,params) 
							   {
						    		
						    		 if (input.attr("name") == "processId" ||input.attr("name") == "processName")
							        {
							        	//alert(input.val()+"hello");
							          enterdService = input.val(); 
							          for(var i = 0; i<res1.length; i++) 
							          {
							        	  //alert(res2[i]+"hi");
							            if ((enterdService == res1[i])) 
							            {								            
							              return false;								          
							            }
							          }
							         }
							         return true;
							    },   */
						  },
						  messages : 
						  {
							 
							  levelValidator : "Level Should be atleast 1",
							  transactionNamevalidation : "Pattern name field can not allow special symbols except(_ .)",
							  transactionDescription:"pattern Description field can not allow special symbols except(_ .)",
							  transactionNullValidator:" Pattern Name Required",						
							  transactionNameUniqueness:"Pattern name already exist",
							  processNameUniqueness: "Process name already Configured", 
							  
						  }
					 });
			})
	        (jQuery, kendo);
	  
	  
	  </script>
	   <style type="text/css">

.k-datepicker span {
	width: 70%
}
.k-datepicker{
background: white;
}
#grid {
	font-size: 11px !important;
	font-weight: normal;
}


	.k-window-titlebar {
	height: 25px;
}
</style>