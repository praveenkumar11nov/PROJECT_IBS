 <%@include file="/common/taglibs.jsp"%>
<script type="text/javascript"  src=" <c:url value='/resources/js/plugins/forms/jquery.tagsinput.min.js'/>"></script>


<c:url value="/common/getAllChecks" var="allChecksUrl" />
	<c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />
	<c:url value="/common/getFilterAutoCompleteValues" var="filterAutoCompleteUrl" />

<c:url value="/filerepositorymaster/getfileRepositoryDocName" var="getDocNameUrl" />
<c:url value="/filerepositorymaster/read" var="readUrl"/>
<c:url value="/filerepositorymaster/create" var="createUrl" />
<c:url value="/fileRepositoryMaster/update" var="updateUrl" />
<c:url value="/filerepositorymaster/delete" var="destroyUrl" />
<c:url value="/filerepository/frgroup/readtree" var="readFrGroupTreeUrl" />
<c:url value="/filerepositorymaster/frGroupNameForFilter" var="fileRepoGroupNamefrSearchTag" />
<c:url value="/filerepositroy/getfilterValuesForcreatedBy" var="createdByFilterUrl" />
<c:url  value="/filerepositroy/getfilterValuesForLastUpdatedBy" var="lastUpdatedByUrl"/>
<c:url value="/filerepositroy/getfilterValuesDocType" var="docTypeUrl" />
<c:url value="/filerepositroy/getfilterfrId" var="frIdfilterUrl" />

<div class="wrapper">
	<br> <br>


	<kendo:grid name="grid" pageable="true"   resizable="true" change="onChange" selectable="true" groupable="true"
		sortable="true" filterable="true" scrollable="true" height="430px" edit="onEvent">
		
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" ></kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
		 <kendo:grid-filterable-operators>
		  	<%-- <kendo:grid-filterable-operators-string eq="Is equal to"/> --%>
		 </kendo:grid-filterable-operators>
			
		</kendo:grid-filterable>
		
		
		
		<kendo:grid-editable mode="popup" confirmation="Are you sure to delete the record" />
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create"
				text="Add Documents" />
				<kendo:grid-toolbarItem text="Clear Filter" name="Clear_Filter" />
		</kendo:grid-toolbar>
		<kendo:grid-columns>
			<kendo:grid-column title="Document Category  *" field="fileRepositoryTree" editor="frGroupEditor" hidden="true" 
				 filterable="true" width="100px" />
				 	
				 <kendo:grid-column title="File Repository Group" field="frGroupId" hidden="true"
				 filterable="true" width="100px" />
				 <kendo:grid-column title="Document Id " field="frId"  width="150px" sortable="true" filterable="true" >
				 
				 <kendo:grid-column-filterable>
						<kendo:grid-column-filterable-ui>
							<script>
								function docNameFilter(element) {
									element.kendoAutoComplete({
										placeholder : "Enter Document Id",
										dataSource : {
											transport : {
												read : "${frIdfilterUrl}"
											}
										}
									});
								}
							</script>
						</kendo:grid-column-filterable-ui>
					</kendo:grid-column-filterable>
				 </kendo:grid-column>
				 
				 <kendo:grid-column title="Group Name &nbsp; *" field="frGroupName" width="200px" > 
				 <kendo:grid-column-filterable>
						<kendo:grid-column-filterable-ui>
							<script>
								function docNameFilter(element) {
									element.kendoAutoComplete({
										placeholder : "Enter Fr_Group Name",
										dataSource : {
											transport : {
												read : "${filterAutoCompleteUrl}/FileRepositoryTree/frGroupName"
											}
										}
									});
								}
							</script>
						</kendo:grid-column-filterable-ui>
					</kendo:grid-column-filterable>
				</kendo:grid-column>
				 
				 
				 
				 
				 
				 
				 <kendo:grid-column title="Document Name *" field="docName"
				  width="150px" >
				<kendo:grid-column-filterable>
						<kendo:grid-column-filterable-ui>
							<script>
								function docNameFilter(element) {
									element.kendoAutoComplete({
										placeholder : "Enter document name",
										dataSource : {
											transport : {
												read : "${filterAutoCompleteUrl}/FileRepository/docName"
											}
										}
									});
								}
							</script>
						</kendo:grid-column-filterable-ui>
					</kendo:grid-column-filterable>
				</kendo:grid-column>
				 
				  <kendo:grid-column title="Description &nbsp;*" field="docDescription" editor="DocumentDescription"
				 filterable="false" width="150px" />
				 
			<kendo:grid-column title="Doc Type" field="docType" 
				 filterable="true" width="150px" >
				 
				  <kendo:grid-column-filterable>
		    			<kendo:grid-column-filterable-ui>
	    				<script type="text/javascript"> 
					function nationalityFilter(element) {
					element.kendoAutoComplete({
						placeholder : "Enter Doc Type",
					optionLabel: "Select",
					dataSource : {
					transport : {
					read : "${docTypeUrl}"
					}
					}
					});
					}
						  	</script>		
		    			</kendo:grid-column-filterable-ui>
		    		</kendo:grid-column-filterable>	 
				 
				 
				 </kendo:grid-column>
				 
			<kendo:grid-column title="Search Tag *" field="frSearchTag" editor="searchTagEditor"
				filterable="true" width="120px">
				<kendo:grid-column-filterable>
						<kendo:grid-column-filterable-ui>
							<script>
								function docNameFilter(element) {
									element.kendoAutoComplete({
										placeholder : "Enter Search Tag Name",
										dataSource : {
											transport : {
												read : "${filterAutoCompleteUrl}/FileRepository/frSearchTag"
											}
										}
									});
								}
							</script>
						</kendo:grid-column-filterable-ui>
					</kendo:grid-column-filterable>
				</kendo:grid-column>
				
			<kendo:grid-column title="Status" field="status"
				width="100px" >
					<kendo:grid-column-values value="${status}"/> 
				 </kendo:grid-column>
              <kendo:grid-column title="Created By" field="createdby"
				 filterable="true" width="100px">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function ContactNoFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter created by",
									dataSource : {
										transport : {
											read : "${createdByFilterUrl}"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable> 
				 </kendo:grid-column>
				 
				 
               <kendo:grid-column title="Updated By" field="lastupdatedBy"
				 filterable="true" width="100px" >
				  <kendo:grid-column-filterable>
						<kendo:grid-column-filterable-ui>
							<script>
								function docNameFilter(element) {
									element.kendoAutoComplete({
										placeholder : "Enter updated by",
										dataSource : {
											transport : {
												read : "${lastUpdatedByUrl}"
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
				<kendo:grid-column-commandItem name="edit" click="edit"/>
				</kendo:grid-column-command>
				</kendo:grid-column>
				<kendo:grid-column title="&nbsp;" width="100px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="destroy"/>
				</kendo:grid-column-command>
			</kendo:grid-column>
				<kendo:grid-column title="&nbsp;" width="160px">
				<kendo:grid-column-command>
				<kendo:grid-column-commandItem name="uploadDoc" text="Upload Document" click="uploadDocumentclick">
				<kendo:grid-column-commandItem-click>
		                	<script>
				function showDetails(e) {
					//securityCheckForActions("./filerepositorymanagement/filerepositorymaster/uploadDocumentButton");
					 var result=securityCheckForActionsForStatus("./filerepositorymanagement/filerepositorymaster/uploadDocumentButton");   
					   if(result=="success"){
							$('#uploadDialog').dialog({
							modal : true,
								});
							return false;
					   }
							}
				</script>
				</kendo:grid-column-commandItem-click>
				</kendo:grid-column-commandItem>
				</kendo:grid-column-command>
			     </kendo:grid-column>
			     
			<kendo:grid-column title="&nbsp;" width="160px">
				<kendo:grid-column-command>
				<kendo:grid-column-commandItem name="View Document" click="downloadFile"/>
				</kendo:grid-column-command>
				</kendo:grid-column>
			<kendo:grid-column title=""  template="<a href='\\\#' id='temPID'  class='k-button k-button-icontext btn-destroy k-grid-Approve#=data.frId#'>#= data.status == 'Active' ? 'Reject' : 'Approve' #</a>" width="100px" />	
		</kendo:grid-columns>
		<kendo:dataSource pageSize="20" requestEnd="onRequestEnd" requestStart="onRequestStart">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-create url="${createUrl}"
					dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-read url="${readUrl}" dataType="json"
					type="POST" contentType="application/json" />
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
			<kendo:dataSource-schema>
				<kendo:dataSource-schema-model id="frId" >
					<kendo:dataSource-schema-model-fields>
					<kendo:dataSource-schema-model-field name="frId" type="string">
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="frGroupName" editable="false">
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="frGroupId" type="number">
						</kendo:dataSource-schema-model-field>
						
						<kendo:dataSource-schema-model-field name="docName" type="string"/>
						<kendo:dataSource-schema-model-field name="docDescription"/>
						
						 <kendo:dataSource-schema-model-field name="docType"/> 

						<kendo:dataSource-schema-model-field name="frSearchTag" />

						<kendo:dataSource-schema-model-field name="status"
							type="string">
							<kendo:dataSource-schema-model-field-validation  />
						</kendo:dataSource-schema-model-field>
                     
                     <kendo:dataSource-schema-model-field name="createdby" type="string">
							<kendo:dataSource-schema-model-field-validation  />
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="lastupdatedBy" type="string">
							<kendo:dataSource-schema-model-field-validation />
						</kendo:dataSource-schema-model-field>



					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
	</kendo:grid>
</div>


<div id="uploadDialog" title="Upload Document" hidden="true">
	<kendo:upload name="files" upload="uploadExtraData"  accept="image/png,application/pdf,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,image/jpeg,image/gif" multiple="false" success="onImageSuccess">
		<kendo:upload-async autoUpload="true"
			saveUrl="./filerepositorymaster/imageUploaddoc" />
	</kendo:upload>

</div>

<div id="alertsBox" title="Alert"></div>






<script type="text/javascript">

var nodeid = "";
var dropDownDataSource = "";
var flagUserId = "";
var res = [];
var res1 = [];
var flag = "";
var docNameid = [];
var groupId=0;
var frId="";

	
	function onEvent(e){
		$('#frSearchTag').tagsInput({});
		if (e.model.isNew()) {
			$(".k-grid-update").click(function () 
			{		
			if(groupId==0){
				alert("please Select Any Node .............");
				return false;
			}else{
				e.model.set("frGroupId", groupId);
			}
			});
		}
			else{
				e.model.set("frGroupId", selectedfrGroupId);
			}
		

		$(".k-grid-update").click(function () {
			
			if($('input[name=frSearchTag]').val() == ''){
				alert("Search Tag is required");
				return false;
			}
		    e.model.set("frSearchTag", $('input[name="frSearchTag"]').val());  
		 });
	}

function searchTagEditor(container, options){
	$('<input id="frSearchTag" class="k-edit-field" name=' + options.field + ' data-bind="value:' + options.field + '" required="true" style="width:65p%;" />')
	.appendTo(container);
}


(function($, kendo) {
	$.extend(	true,
					kendo.ui.validator,
					{	rules : { // custom rules
						DocumentNameValidator : function(input,params) 
							{
								 if (input.attr("name") == "docName") {
					                   return $.trim(input.val()) !== "";
					                }
					                return true;
							},
							
							docNameUniqueness : function(input,params) {
									//check for the name attribute 
									if (input.filter("[name='docName']").length && input.val()) {
										enterdTrack = input.val().toUpperCase();	
										$.each(res1,function(ind, val) {
													if ((enterdTrack == val.toUpperCase()) && (enterdTrack.length == val.length)) {
													flag = enterdTrack;
													return false;
											}
										});
									}
									return true;
							},
							docUniqueness : function(input) {
								if (input.filter("[name='docName']").length && input.val() && flag != "") {
									flag = "";
									return false;
								}
								return true;
						},  
							
						},
						messages : { 
							DocumentNameValidator:"Document Name Required",
							docUniqueness:"Document Name already exists, please try with some other name",
						}
					});
})(jQuery, kendo);	



(function ($, kendo) 
	   	{   	  
	        $.extend(true, kendo.ui.validator, 
	        {
	             rules: 
	             {           	
	            	 DocumentNameValidation: function (input, params) 
	                 {               	 
	                     if (input.filter("[name='docName']").length && input.val()) 
	                     {
	                    	 name = input.val();
	                    	$.each(res, function( index, value ) 
							{	
	              				if((name == value))
								{
									flag = input.val();								
	              				}  
	              			});  
	                         return /^[a-zA-Z]+[ ._a-zA-Z0-9._]*[a-zA-Z0-9]$/.test(input.val());
	                     }        
	                     return true;
	                 },	
	                 	
	                 
	             }, 
	             messages: 
	             {
	            	 DocumentNameValidation : "Document name field can not allow special symbols except(_ .)",
	            	// DocumentSearchTagValidation:"Document search Tag field can not allow special symbols except(_)",
	             }
	        });
	        
	    })(jQuery, kendo);





function onCatSelect(e) {

	
	nodeid = $('.k-state-hover').find('#hiddenId').val();
	var nn = $('.k-state-hover').html();
	var spli = nn.split(" <input");
	$('#editNodeText').val(spli[0].trim());
	var kitems = $(e.node).add(
			$(e.node).parentsUntil('.k-treeview', '.k-item'));
	texts = $.map(kitems, function(kitem) {
		return $(kitem).find('>div span.k-in').text();
	});
	groupId = nodeid;	
/* 	nodeid = $('.k-state-hover').find('#hiddenId').val();
	var catId = $('#grid').data().kendoGrid.dataSource.data()[0];
			catId.set('frGroupId', nodeid); */

}



function DocumentDescription(container, options) {
	$(
			'<textarea name="Document Description" data-text-field="docDescription" data-value-field="docDescription" data-bind="value:' + options.field + '" style="width: 148px; height: 40px;" required="true"/>')
			.appendTo(container);
	$('<span class="k-invalid-msg" data-for="Purpose Of Visit"></span>').appendTo(container);
}

$("#grid").on("click", ".k-grid-delete", function() {
	
	securityCheckForActions("./filerepositorymanagement/filerepositorymaster/deleteButton");
	
});
/*
function destroyclick(e){
	
	securityCheckForActions("./filerepositorymanagement/filerepositorymaster/deleteButton");
	
	 $.ajax({
		type : "POST",
		url : "./filerepositorymanagement/filerepositorymaster/deleteButton",
		dataType : 'text',
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
					grid.cancelChanges();		
						
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
	}); 

}*/

function uploadDocumentclick(){
	
	securityCheckForActions("./filerepositorymanagement/filerepositorymaster/uploadDocumentButton");
	//alert("in ypload");
	/* $.ajax({
		type : "POST",
		url : "./filerepositorymanagement/filerepositorymaster/uploadDocumentButton",
		success : function(response) {
			if (response == "false") {
				//alert("Access Denied");
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
	}); */

}

$("#grid").on("click",".k-grid-add",function(){
	
	securityCheckForActions("./filerepositorymanagement/filerepositorymaster/createButton");
	
	$.ajax({
		type : "GET",
		url : '${getDocNameUrl}',
		dataType : "JSON",
		success : function(response) {
			$.each(response, function(index, value) {
				res1.push(value);
			});
		}
	});
	
	
	

$(".k-window-title").text("Add Documents");
$(".k-grid-update").text("Create");

$('.k-edit-field .k-input').first().focus();

$('label[for="createdby"]').hide();
$('div[data-container-for="createdby"]').hide();

$('label[for="lastupdatedBy"]').hide();
$('div[data-container-for="lastupdatedBy"]').hide();

$('label[for="frId"]').hide();
$('div[data-container-for="frId"]').hide();


$('label[for="frGroupId"]').hide();
$('div[data-container-for="frGroupId"]').hide();

$('label[for="frGroupName"]').hide();
$('div[data-container-for="frGroupName"]').hide();

$('label[for="status"]').hide();
$('div[data-container-for="status"]').hide();

 $('label[for="docType"]').hide();
$('div[data-container-for="docType"]').hide(); 


$(".k-grid-Approve").hide();


/* $.ajax({
	type : "POST",
	url : "./filerepositorymanagement/filerepositorymaster/createButton",
	success : function(response) {
		if (response == "false") {
			//alert("Access Denied");
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
});

 */




});
function edit(){
	 
	securityCheckForActions("./filerepositorymanagement/filerepositorymaster/updateButton");
	$('[name="docName"]').attr("disabled", true);
	
	$('label[for="createdby"]').hide();
	$('div[data-container-for="createdby"]').hide();

	$('label[for="lastupdatedBy"]').hide();
	$('div[data-container-for="lastupdatedBy"]').hide();
	
	$('label[for="frGroupId"]').hide();
	$('div[data-container-for="frGroupId"]').hide();
	
	$('label[for="status"]').hide();
	$('div[data-container-for="status"]').hide();
	
	$('label[for="frId"]').hide();
	$('div[data-container-for="frId"]').hide();
	
	var gview = $("#grid").data("kendoGrid");
	var selectedItem = gview.dataItem(gview.select()); 
	
 	$('label[for="docType"]').hide();
	$('div[data-container-for="docType"]').hide(); 
	
	flagUserId = selectedItem.frId;
	$(".k-grid-Approve"+flagUserId).hide();
	
	
	
	/* $.ajax({
		type : "POST",
		url : "./filerepositorymanagement/filerepositorymaster/updateButton",
		success : function(response) {
			if (response == "false") {
				//alert("Access Denied");
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
	}); */

	
	
	
}
var SelectedRowId = "";
var docType = "";
var selectedfrGroupId="";
function onChange(arg) {

	var gview = $("#grid").data("kendoGrid");
	var selectedItem = gview.dataItem(gview.select());
	SelectedRowId = selectedItem.frId;
	selectedfrGroupId=selectedItem.frGroupId;
	docType = selectedItem.docType;

}
function downloadFile()
{
	//securityCheckForActions("./filerepositorymanagement/filerepositorymaster/downloadDocumentButton");
	var result=securityCheckForActionsForStatus("./filerepositorymanagement/filerepositorymaster/downloadDocumentButton");
 	
	 
	
	 if(result=="success"){				 
		 window.open("./filerepositorymaster/uploaded_document/download/"+SelectedRowId);
	 }
	

}

function uploadExtraData(e) {
	 var files=e.files;
		
     $.each(files,function(){
                    if(this.extension ===".png"){
                 	   
                                 e.data = {
                                		 frId : SelectedRowId
                 	                      };   
                    }
                    else if(this.extension ===".pdf"){
                 	   e.data = {
                 			  frId : SelectedRowId
                                };
                    }
                    else if(this.extension ===".jpg"){
                 	   e.data = {
                 			  frId : SelectedRowId
                 	            };
                    }
                    else if(this.extension ===".jpeg"){
                 	   e.data = {
                 			  frId : SelectedRowId
                                };
                       
                    }
                    else if(this.extension ===".gif"){
                  	   e.data = {
                  			  frId : SelectedRowId
                                 };
                        
                     }
                    else if(this.extension ===".xlsx"){
                  	   e.data = {
                  			 frId : SelectedRowId
                                 };
                        
                     }
                    else if(this.extension ===".pdf"){
                  	   e.data = {
                  			 frId : SelectedRowId
                                };
                        
                     }
                    
                    else{
                 	   alert("Only Images(png,jpeg,jpg,gif)/Pdf/Xlsx  files can be uploaded");
                 	   e.preventDefault();
                 	   return false;
                    }
                    
});
	
	
}

function onImageSuccess(e) {
	
	  $("#alertsBox").html("");
		$("#alertsBox").html("Uploaded Successfully");
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
	 $(".k-upload-status").remove();
   $(".k-upload-files.k-reset").find("li").remove();
   $(".k-upload-status k-upload-status-total").text("");
	
}



function frGroupEditor(container, options) {

	$('<div style="max-height: 100px; overflow: auto; "></div>')
			.appendTo(container).kendoTreeView({

			dataTextField : "frGroupName",
			template : " #: item.frGroupName # <input type='hidden' id='hiddenId' class='#: item.frGroupName ##: item.frGroupId#' value='#: item.frGroupId#'/>",
			select:onCatSelect,
			name : "treeview",
			dataSource : {
				 transport: {
                     read: {
                         url: "${readFrGroupTreeUrl}",
                         contentType: "application/json",
                         type : "GET"
                     }
                 },
                 schema: {
                     model: {
                         id: "frGroupId",
                         hasChildren: "hasChilds"
                     }
                 }
				}
			});
} 


function DocumentTypeEditor(container, options) {
	var booleanData = [ {
		text : 'Document Type',
		value : ""
	}, {
		text : 'PDF',
		value : "PDF"
	},{
		text : 'XLS',
		value : "XLS"
	},
	{
		text : 'DOC',
		value : "DOC"
	}, {
		text : 'JPEG',
		value : "JPEG"
	},{
		text : 'PNG',
		value : "PNG"
	}

	];

	$('<input/>').attr('data-bind', 'value:docType').appendTo(container)
			.kendoDropDownList({

				dataSource : booleanData,
				dataTextField : 'text',
				dataValueField : 'value'
			});

}

/* #####################################################onRequest End method########################################### */

  	function onRequestStart(e){
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();
	}
  function onRequestEnd(e) {
    	  	  
       		if (typeof e.response != 'undefined')
    		{
       			if (e.response.status == "FAIL") {
       				errorInfo = "";
       				errorInfo = e.response.result.invalid;
       				for (var r = 0; r < e.response.result.length; r++) {
       				errorInfo += (r + 1) + ". "
       						+ e.response.result[r].defaultMessage;
       				}

       				if (e.type == "create") {
       					$("#alertsBox").html("");
						$("#alertsBox").html("Error: Creating the File Repository record\n \n:-" + errorInfo);
						$("#alertsBox").dialog({
							modal: true,
							buttons: {
								"Close": function() {
									$( this ).dialog( "close" );
								}
							}
							});
       				}
       		
       				if (e.type == "update") {

       					$("#alertsBox").html("");
						$("#alertsBox").html("Error: Updating the File Repository\n \n:-" + errorInfo);
						$("#alertsBox").dialog({
							modal: true,
							buttons: {
								"Close": function() {
									$( this ).dialog( "close" );
								}
							}
							});
       				}
       		
       				var grid = $("#grid").data("kendoGrid");
       				grid.dataSource.read();
       				grid.refresh();
       				return false;
       			}

       			if (e.type == "update" && !e.response.Errors) {
       				$("#alertsBox").html("");
       				$("#alertsBox").html("Update record is successfull");
       				$("#alertsBox").dialog({
       					modal : true,
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
       			
       			if (e.type == "update" && e.response.Errors) {
       				$("#alertsBox").html("");
       				$("#alertsBox").html("Update record is Un-successfull");
       				$("#alertsBox").dialog({
       					modal : true,
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

       			if (e.type == "create" && !e.response.Errors) {
       				$("#alertsBox").html("");
       				$("#alertsBox").html("Create record is successfull");
       				$("#alertsBox").dialog({
       					modal : true,
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
       			
       			
       			 if (e.type == "destroy") {
       				 
       				 if (e.response.status == "AciveItemMasterDestroyError") {
           				 
    		  			$("#alertsBox").html("");
    		  			$("#alertsBox").html("Active Records Cannot be Deleted");
    		  			$("#alertsBox").dialog({
    		  				modal : true,
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
       			  else{ 
					$("#alertsBox").html("");
					$("#alertsBox").html("File Repository record deleted successfully");
							$("#alertsBox").dialog({
								modal : true,
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
       		  }
       			
       			
    		}  			
    		
    	}
 

/* ####################################status############################################## */
 $("#grid").on("click", "#temPID", function(e) {
	 
	 
	 
			var button = $(this),        
		   	enable = button.text() == "Approve";
		   	var widget = $("#grid").data("kendoGrid");
		  	var dataItem = widget.dataItem($(e.currentTarget).closest("tr")); 
		   
		  	var result=securityCheckForActionsForStatus("./filerepositorymaster/filerepositorymaster/statusButton");
		  	 if(result=="success"){
						   if(enable)
							   {
							     $.ajax({
							    	 type : "POST",
							    	 url : "./filerepositorymaster/DocumentStatus/"+dataItem.id +"/approved",
							    	 dataType:'text',
							    	 success : function(response)
							    	 {
							    		 //alert(response);
							    		 $("#alertsBox").html("");
											$("#alertsBox").html(response);
											$("#alertsBox").dialog({
												modal: true,
												buttons: {
													"Close": function() {
														$( this ).dialog( "close" );
													}
												}
												}); 
							    		 button.text('Reject');
							    		$('#grid').data('kendoGrid').dataSource.read(); 
							    	 }
							     });  
							   }
						   else
							   {
							     $.ajax({
							    	 type : "POST",
							    	 url : "./filerepositorymaster/DocumentStatus/"+dataItem.id +"/rejected",
							    	 dataType:'text',
							    	 success : function(response)
							    	 {
							    		 //alert(response);
							    		 $("#alertsBox").html("");
											$("#alertsBox").html(response);
											$("#alertsBox").dialog({
												modal: true,
												buttons: {
													"Close": function() {
														$( this ).dialog( "close" );
													}
												}
												}); 
							    		 button.text('Approve');
							    		 $('#grid').data('kendoGrid').dataSource.read(); 
							    	 }
							     });   
							   }
						   }
		  	 /* }
			   }); */
		});		
		
 
 
 $("#grid").on("click", ".k-grid-Clear_Filter", function(){
	    //custom actions
	    $("form.k-filter-menu button[type='reset']").slice().trigger("click");
		var grid = $("#grid").data("kendoGrid");
		grid.dataSource.read();
		grid.refresh();
	});
 

</script>
<style>
.k-upload-button input {
	z-index: 100000
}

div.tagsinput span.tag {
    background: none repeat scroll 0 0 grey;
    color: white;
    font-size: 12px;
    line-height: 20px;
    border: 0;

}

div.tagsinput input {
    background: none repeat scroll 0 0 rgba(0, 0, 0, 0);
    border: medium none;
    margin: 0;
    padding: 0;
    height: 28px;
}


div.tagsinput span.tag a {
    color: black;
    float: right;
    font-size: 11px;
    font-weight: bold;
}

div.tagsinput {
    background: none repeat scroll 0 0 #FDFDFD;
    border: 1px solid #DDDDDD;
    box-sizing: border-box;
    overflow-y: auto;
    padding: 0;
    width: 65%;
     border-radius: 6px;
}


</style>