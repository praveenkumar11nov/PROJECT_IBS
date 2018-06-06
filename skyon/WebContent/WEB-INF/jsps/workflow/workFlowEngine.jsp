<%@include file="/common/taglibs.jsp"%>

<c:url value="/transactionManager/readTransactionManagerUrl" var="readTransactionManagerUrl" />
<c:url value="/transactionManager/createTransactionManagerUrl" var="createTransactionManagerUrl" />
<c:url value="/transactionManager/updateTransactionManagerUrl" var="updateTransactionManagerUrl" />
<c:url value="/transactionManager/destroyTransactionManagerUrl" var="destroyTransactionManagerUrl" />
<%-- <c:url value="/transactionManager/readTransIdForUniqueness" var="readTransIdForUniqueness" /> --%>

<c:url value="/transactionManager/readAllTransactionTypeUrl" var="readAllTransactionTypeUrl" />

<c:url value="/transactionManager/readAllProcessNamesUrl" var="readAllProcessNamesUrl" />
<c:url value="/transactionManager/readProcessIdForUniqueness" var="readProcessIdForUniqueness"/>
<c:url value="/transactionManager/readTransactionNamesForUniqueness" var="readTransactionNamesForUniqueness"/>
<c:url value="/commonss/getAllChecks" var="processTypeUrl"/>

<c:url value="/commonss/getAllTransactionNames" var="readTransactionNamesdropdown"/>
<c:url value="/patternMaster/readProcessNameForUniqueness" var="readProcessNameForUniqueness"/>

<!-- <div id="content"> -->
	<!-- <div class="row">
		<div class="col-xs-12 col-sm-7 col-md-7 col-lg-4">
			<h1 class="page-title txt-color-blueDark">
				<i class="fa-fw fa fa-home"></i>Work Flow Engine <span></span>
			</h1>
		</div>
	</div> -->
	<kendo:grid name="transactionManager" pageable="true" resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true" filterable="true" groupable="true" change="onchange" edit="transactionManagerGridEvent" dataBound="dataBound">
		<kendo:grid-pageable pageSizes="true" buttonCount="1" pageSize="5" input="true" numeric="true" refresh="true"></kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
			<kendo:grid-filterable-operators>
				<kendo:grid-filterable-operators-string eq="Is equal to" />
			</kendo:grid-filterable-operators>
		</kendo:grid-filterable>
	<kendo:grid-editable mode="popup"
			confirmation="Are you sure you want to remove this item?" />
	 <kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Choose Pattern" />
			<kendo:grid-toolbarItem text="Clear_Filter" />
		</kendo:grid-toolbar> 
		<kendo:grid-columns>
			<kendo:grid-column title="Process Name&nbsp;*" field="processName" width="75px" filterable="true" editor="processNameEditor"/><br/><br/>
	   		<%-- <kendo:grid-column title="Process Name&nbsp;*" field="processId" width="75px" hidden="true" /> --%>
		<kendo:grid-column title="PatternNames*" field="name" width="75px" filterable="false" />
		<kendo:grid-column title="PatternName*" field="tId" width="75px" filterable="false" hidden="true"  editor="transactionEditor" />
		<kendo:grid-column title="Code" field="transCode" width="35px" filterable="false" hidden="true"/>
		<kendo:grid-column title="Number of Levels" field="transLevel" width="55px" filterable="false"/>
		<kendo:grid-column title="Description" field="transDescription" width="45px" filterable="false"/>
	<%-- 	<kendo:grid-column title="OfficeId" field="officeId" width="45px" filterable="false" hidden="true"/> --%>
		<kendo:grid-column title="&nbsp;" width="50px">
			   <kendo:grid-column-command>
				   <kendo:grid-column-commandItem name="edit"/>
				  <%-- <kendo:grid-column-commandItem name="delete"/> --%>			
			   </kendo:grid-column-command>
			</kendo:grid-column>	
		</kendo:grid-columns>
			
		<kendo:dataSource pageSize="5" requestEnd="onTransactionManagerEnd" requestStart="OnRequestStart">
			<kendo:dataSource-transport>
				 <kendo:dataSource-transport-read url="${readTransactionManagerUrl}" dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-create url="${createTransactionManagerUrl}" dataType="json" type="GET" contentType="application/json" />
				<kendo:dataSource-transport-update url="${updateTransactionManagerUrl}" dataType="json" type="GET" contentType="application/json" />
				<kendo:dataSource-transport-destroy url="${destroyTransactionManagerUrl}" dataType="json" type="GET" contentType="application/json" />
			</kendo:dataSource-transport>

			<kendo:dataSource-schema >
				<kendo:dataSource-schema-model id="transManageId">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="transManageId" type="number" />
				 	<kendo:dataSource-schema-model-field name="tId" type="number" defaultValue="" /> 
						<kendo:dataSource-schema-model-field name="name" type="string"/>
						<kendo:dataSource-schema-model-field name="transCode" type="string"/>
						<kendo:dataSource-schema-model-field name="transLevel" type="number"/>
						<kendo:dataSource-schema-model-field name="transDescription" type="string"/>
						<kendo:dataSource-schema-model-field name="createdBy" type="string"/>
						<kendo:dataSource-schema-model-field name="createdDate" type="date"/>
						<kendo:dataSource-schema-model-field name="processName"  type="string" defaultValue=""/>
					<%-- 	<kendo:dataSource-schema-model-field name="processId" type="number" /> --%>
						<kendo:dataSource-schema-model-field name="officeId" type="number"/>
						</kendo:dataSource-schema-model-fields>
					
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>

	</kendo:grid>
		

<!-- </div> -->
<div id="alertsBox" title="Alert"></div>
<script>
var rateType1="";
var processName="naren";
function onchange(arg){
	
	
	/*  var gview = $("#transactionManager").data("kendoGrid");
	 var selectedItem = gview.dataItem(gview.select());
	processName=selectedItem.processName; */
}


function transactionManagerGridEvent(e)
{
	
	  $(".k-window-title").text("Add pattern for Work flow Engine");
	  $(".k-grid-update").text("Save");
	 	
	   $('div[data-container-for="name"]').hide();
		$('label[for="name"]').closest('.k-edit-label').hide();   	
	
		
		$('div[data-container-for="transCode"]').hide();
		$('label[for="transCode"]').closest('.k-edit-label').hide();
	  
		$('div[data-container-for="transLevel"]').hide();
		$('label[for="transLevel"]').closest('.k-edit-label').hide();
		
		$('div[data-container-for="transDescription"]').hide();
		$('label[for="transDescription"]').closest('.k-edit-label').hide();
		
		$('div[data-container-for="officeId"]').hide();
		$('label[for="officeId"]').closest('.k-edit-label').hide();
		
	  if (e.model.isNew()) 
	  {
		  $(".k-window-title").text("Add  Details");
			$(".k-grid-update").text("Save");		
			
			$(".k-grid-cancel").click(function () {
				   var grid = $("#transactionManager").data("kendoGrid");
				   grid.dataSource.read();
				   grid.refresh();
				   });
			 
			res1 = [];
			 $.ajax
			 ({
			      type : "GET",
				  dataType:"text",
				  url : '${readTransactionNamesForUniqueness}',
				  dataType : "JSON",
				  success : function(response) 
				  {
					 for(var i = 0; i<response.length; i++) 
					 {
						 
					   res1[i] = response[i];	
					
				     }
			      }
			 });
			
			res2 = [];
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
					 res2[i] = response[i];	
				  }
			      }
			 });
			 res3 = [];
			 $.ajax
			 ({
			      type : "GET",
				  dataType:"text",
				  url : '${readProcessNameForUniqueness}',
				  dataType : "JSON",
				  success : function(response) 
				  {
					 for(var i = 0; i<response.length; i++) 
					 {
					   res3[i] = response[i];	
				     }
			      }
			 });
	  }
	  else
	  {
		
	
	   /*   $("#ttId").data("kendoComboBox").setDataSource({
               transport : {
                read : {
             	   url : '${readTransactionNamesdropdown}/'+processName,
                 dataType : "json",
                 type : 'GET'
                }
               },
               dataTextField : "name",
               dataValueField : "tId",
              }); */
            
		  
		 
		  
		  res3 = [];
		  res1 = [];
		res2 = [];
			$.ajax({
				type : "GET",
				dataType : "text",
				url : '${readProcessIdForUniqueness}',
				dataType : "JSON",
				success : function(response) {
					var j = 0;
					for (var i = 0; i < response.length; i++) {
						if (response[i] != proId) {
							res2[j] = response[i];
							j++;
						}
					}
				}
			});
			
		} 
		
	}

	/* function transactionEditor(container, options) {
	
		$(
				'<input data-text-field="transName" name="transName" id="tId" required="true" data-value-field="tId" data-bind="value:' + options.field + '"/>')
				.appendTo(container)
				.kendoComboBox(
						{
							//cascadeFrom : "processIds",
							placeholder : "Select Process",
							template : '<table><border><tr><td rowspan=2><span class="k-state-default"></span></td>'
									+ '<td padding="5px"><span class="k-state-default"><b>#: data.transName #</b></span><br></td></tr></border></table>',

							dataSource : {
								transport : {
									read : "${readAllTransactionTypeUrl}"
								}
							},
							change : function(e) {
								if (this.value() && this.selectedIndex == -1) {
									alert("Pattern doesn't exist!");
									$("#tId").data("kendoComboBox").value("");
									alert(processName);
								}
							},
						});
		$('<span class="k-invalid-msg" data-for="pattrenName"></span>')
				.appendTo(container);
	} */
	var   process="";
 /* function processNameEditor(container,options)
	 {
		 $('<input data-text-field="name" name="processName" data-value-field="value" data-bind="value:' + options.field + '"/>')
	     .appendTo(container)
	         .kendoDropDownList({
	        	 optionLabel: "Select Process Name",
	        	// select : onSelectCombooffice,
	         dataSource: {  
	             transport:{
	                 read: "${processTypeUrl}"
	             }
	         },
	         change : function(e) {
	        	 
	   
	             if (this.value() && this.selectedIndex == -1) {
	              alert("Process doesn't exist!");
	            $("#processName").data("kendoComboBox").value("");
	         
	             }
	             processName = this.dataItem().value;
	             //alert(process)
	              $("#tId").data("kendoComboBox").setDataSource({
	                  transport : {
	                   read : {
	                	   url : '${readTransactionNamesdropdown}/'+processName,
	                    dataType : "json",
	                    type : 'GET'
	                   }
	                  },
	                  dataTextField : "value",
	                  dataValueField : "value",
	                 });
	              
	     
	            },
	     });

		 $('<span class="k-invalid-msg" data-for="processName"></span>').appendTo(container);

	 }       
  */
	function processNameEditor(container, options) {
		var res = (container.selector).split("=");
		var attribute = res[1].substring(0,res[1].length-1);
		
		$('<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '"required ="true" name = "'+attribute+'" id = "'+attribute+'Id"/>')
				.appendTo(container).kendoDropDownList({
					optionLabel : {
						text : "Select Process Name",
						value : "",
					}, 
					defaultValue : false,
					sortable : true,
					dataSource : {
						transport : {
							read : "${processTypeUrl}/"+attribute,
						}
					},
			    change : function(e) {
			        
			             if (this.value() && this.selectedIndex == -1) {
			              alert("Process doesn't exist!");
			         	   $("#processName").data("kendoComboBox").value("");
			             }else{
			            	 processName = this.value();
			             }
			   }, 
			  
			     }); 

			
		 $('<span class="k-invalid-msg" data-for="'+attribute+'"></span>').appendTo(container);
	}
  
 /*  function transactionEditor(container, options){
		 $('<select multiple="multiple" name="TransactionMaster" data-text-field="name" style="width:162px;" data-value-field="tId" id="ttId" data-bind="value:' + options.field + '"/>')
		 .appendTo(container).kendoComboBox({
	
		   placeholder : "Select", 
		  });
		 } */
		 function transactionEditor(container, options) {
				
				$(
						'<input data-text-field="name" name="name" id="tId" required="true" data-value-field="tId" data-bind="value:' + options.field + '"/>')
						.appendTo(container)
						.kendoComboBox(
								{
									//cascadeFrom : "processIds",
									placeholder : "Select Process",
									template : '<table><border><tr><td rowspan=2><span class="k-state-default"></span></td>'
											+ '<td padding="5px"><span class="k-state-default"><b>#: data.name #</b></span><br></td></tr></border></table>',

									dataSource : {
										transport : {
											read : "./commonss/getAllTransactionNames/"+processName,
										}
									},
									change : function(e) {
										if (this.value() && this.selectedIndex == -1) {
											alert("Pattern doesn't exist!");
											$("#tId").data("kendoComboBox").value("");
										}
									},
								});
				$('<span class="k-invalid-msg" data-for="name"></span>')
						.appendTo(container);
			}
 function onSelectCombooffice(e) {

	  var processName = this.dataItem(e.item.index()).value;
	  alert(processName);
	  dropDownDataSource = new kendo.data.DataSource({
          transport: {
              read: {
               url : "./commonss/getAllTransactionNames/"+processName, 
                  dataType: "json",
                  type    : 'GET'
              }
          }
      });
	/*   
	   ("#tId").kendoDropDownList({
           dataSource    : dropDownDataSource,
           tId : "name",
           name: "tId",
           
       });  */
 }
	 
	 
	/*  function processNameEditor(container, options) {
		  $(
		    '<input name="Process Name" data-text-field="name" id="offcomboId" data-value-field="value" data-bind="value:' + options.field + '" required="required"/>')
		    .appendTo(container).kendoComboBox({
		     autoBind : false,
		     placeholder : "Select",
		     select : onSelectCombooffice,
		     dataSource : {
		      transport : {
		       read : "${processTypeUrl}"
		      }
		     },
		     change : function (e) {
		                if (this.value() && this.selectedIndex == -1) {                    
		                    alert("Peocess Name doesn't exist!");
		             }
		           },

		    });
		  
		  $('<span class="k-invalid-msg" data-for="Office"></span>').appendTo(
		    container);
		 }
	 
	 
	
	function onSelectCombooffice(e) {

		  var processName = this.dataItem(e.item.index()).value;
		  
		
		var  dropDownDataSource = new kendo.data.DataSource(
		    {
		    	
		     transport : {
		      read : {
		       url : "./commonss/getAllTransactionNames/"+processName,
		       dataType : "json",
		       type : 'GET'
		      }
		     }
		    });

		 
		 }
	 */
	 
	
	
/* function transactionEditor(container,options)
	{
//	alert(processName);
		  $('<input data-text-field="name" name="TransactionMaster" id="tId" required="true" data-value-field="tId" data-bind="value:' + options.field + '"/>').appendTo(container).kendoComboBox
			 ({
			   	 placeholder : "Select Componenet Names",
			     template : '<table><border><tr><td rowspan=2><span class="k-state-default"></span></td>'
					+ '<td padding="5px"><span class="k-state-default"><b>#: data.name #</b></span><br></td></tr></border></table>',
					filter:"startswith",
	     	
				   dataSource: {       
			           transport: {
			               read: "${readAllTransactionTypeUrl}"
			           }
			       }, 
			   });
			   $('<span class="k-invalid-msg" data-for="Component"></span>').appendTo(container);   
	  }   */

	/* function processNameEditor(container, options) {
		$(
				'<input data-text-field="processName" name="processName" id="processIds" required="true" data-value-field="processId" data-bind="value:' + options.field + '"/>')
				.appendTo(container)
				.kendoComboBox(
						{
							placeholder : "Select Process",
							template : '<table><border><tr><td rowspan=2><span class="k-state-default"></span></td>'
									+ '<td padding="5px"><span class="k-state-default"><b>#: data.processName #</b></span><br></td></tr></border></table>',

							dataSource : {
								transport : {
									read : "${readAllProcessNamesUrl}"
								}
							},
							change : function(e) {
								if (this.value() && this.selectedIndex == -1) {
									alert("Process doesn't exist!");
									$("#processId").data("kendoComboBox")
											.value("");

								}
							},
						}); 
		$('<span class="k-invalid-msg" data-for="processName"></span>')
				.appendTo(container);
	}*/

	$("#transactionManager").on("click", ".k-grid-Clear_Filter", function() {
		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
		var grid = $("#transactionManager").data("kendoGrid");
		grid.dataSource.read();
		grid.refresh();
	});

	var transLevel = 0;
	
	function dataBound(e) {
		var data = this.dataSource.view(), row;
		var grid = $("#transactionManager").data("kendoGrid");

		
		for (var i = 0; i < data.length; i++) {

			row = this.tbody.children("tr[data-uid='" + data[i].uid + "']");
			transLevel = data[i].transLevel;
		}

	}

	function OnRequestStart(e){
		    $('.k-grid-update').hide();
	        $('.k-edit-buttons')
	                .append(
	                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
	        $('.k-grid-cancel').hide();
	}
	function onTransactionManagerEnd(e) {
		if (typeof e.response != 'undefined') {
			if (e.response.status == "FAIL") {
				errorInfo = "";
				errorInfo = e.response.result.invalid;
				var i = 0;
				for (i = 0; i < e.response.result.length; i++) {
					errorInfo += (i + 1) + ". "
							+ e.response.result[i].defaultMessage + "\n";
				}
				if (e.type == "create") {
					alert("Error: Creating the Transaction Details\n\n"
							+ errorInfo);
				}
				if (e.type == "update") {
					alert("Error: Updating the Transaction Details\n\n"
							+ errorInfo);
				}
				var grid = $("#transactionManager").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
				return false;
			}
			if (e.type == "update" && !e.response.Errors) {
				e.sender.read();
				$("#alertsBox").html("Alert");
				$("#alertsBox").html("Process & Pattern Updated successfully");
				$("#alertsBox").dialog({
					modal : true,
					draggable : false,
					resizable : false,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				var grid = $("#transactionManager").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			}
			if (e.type == "create" && !e.response.Errors) {
				e.sender.read();
				$("#alertsBox").html("Alert");
				$("#alertsBox").html("Process & Pattern Created successfully");
				$("#alertsBox").dialog({
					modal : true,
					draggable : false,
					resizable : false,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
			}
			if (e.type == "destroy" && !e.response.Errors) {
				$("#alertsBox").html("Alert");
				$("#alertsBox").html("Process & Pattern Deleted successfully");
				$("#alertsBox").dialog({
					modal : true,
					draggable : false,
					resizable : false,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
				var grid = $("#transactionManager").data("kendoGrid");
				$(".k-grid-add", "#transactionManager").show();
				grid.dataSource.read();
				grid.refresh();
			}
		}
	}
	(function($, kendo) {
		$.extend(true, kendo.ui.validator, {
			rules : {
				transactionNameUniqueness : function(input, params) {

					if (input.attr("name") == "tId"
							|| input.attr("name") == "transName") {
						//alert(input.val()+" tnu");
						enterdService = input.val();
						if (res1 != null) {
							for (var i = 0; i < res1.length; i++) {

								if ((enterdService == res1[i])) {
									return false;
								}
							}
						}
					}
					return true;
				},
				processNameUniqueness : function(input,params) 
				   {
					//alert(input.attr("name"));
			    		 if (input.attr("name") == "processName")
				        {
				        	//alert(input.val()+"hello");
				          enterdService = input.val(); 
				          for(var i = 0; i<res3.length; i++) 
				          {
				        	  //alert(res2[i]+"hi");
				            if ((enterdService == res3[i])) 
				            {								            
				              return false;								          
				            }
				          }
				         }
				         return true;
				    }, 
			},
			messages : {
				transactionNameUniqueness : "pattren is already configured",
				processNameUniqueness : "process is already configured",
			}

		})
	})(jQuery, kendo);
</script>
<style>
#grid {
	font-size: 11px !important;
	font-weight: normal;
}


	.k-window-titlebar {
	height: 25px;
}

</style>
