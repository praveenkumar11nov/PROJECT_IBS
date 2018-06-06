<%@include file="/common/taglibs.jsp"%>

<c:url value="/helpDesk/aboutus" var="readUrl" />
<c:url value="/helpDesk/AboutCreateUrl" var="AboutCreateUrl" />
 <c:url value="/helpDesk/AboutUpdateUrl" var="AboutUpdateUrl" />
<c:url value="/helpDesk/AboutDestroyUrl" var="AboutDestroy" />
<c:url value="/helpDesk/aboutImage" var="personImage" />
<%-- 
<c:url value="/FRChange/serviceTypeComboBoxUrl" var="serviceTypeComboBoxUrl" />
<c:url value="/FRChange/readAccountNumbers" var="readAccountNumbers" />

<c:url value="/FRChange/filter" var="commonFilterForFRChangeUrl" />
<c:url value="/FRChange/commonFilterForAccountNumbersUrl" var="commonFilterForAccountNumbersUrl" />
<c:url value="/FRChange/getPersonListForFileter" var="personNamesFilterUrl" />  --%>

<div id="sample">
<kendo:grid name="grid" remove="aboutUsChangeDeleteEvent" resizable="true" pageable="true" selectable="true" change="onChangeAboutUs" edit="AboutUsChangeEvent"  sortable="true" scrollable="true" groupable="true">
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10"></kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
			<kendo:grid-filterable-operators>
				<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains"/>
			</kendo:grid-filterable-operators>
		</kendo:grid-filterable>
		<kendo:grid-editable mode="popup" />
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Details" />
            <kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilterAboutUs()><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>"/>
		</kendo:grid-toolbar>	
	
            <kendo:grid-columns>
			
			<%--  <kendo:grid-column title="Image " field="image"	template="<span onclick='clickToUploadImage()' title='Click to Upload Image' ><img src='./person/getpersonimage/#=about_id#' id='myImages_#=about_id#' alt='Click to Upload Image' width='80px' height='80px'/></span>"
			filterable="false" width="94px" sortable="false">
		    </kendo:grid-column>  --%>
		     
			 <kendo:grid-column title="AboutId" field="about_id" width="110px" hidden="true"/>
			<kendo:grid-column title="Image" field="image" template ="<span onclick='clickToUploadImage()' title='Click to Upload Image' ><img src='./about/getpersonimage/#=about_id#' id='myImages_#=about_id#' alt='No Image to Display' width='80px' height='80px'/></span>" filterable="false" width="94px" sortable="false"/> 
			    		
	        <kendo:grid-column title="Person Name" field="about_name" filterable="true" width="130px">
	    	</kendo:grid-column>
	    	
	    	<kendo:grid-column title="Designation" field="about_designation" filterable="true"  width="120px" >
	    	</kendo:grid-column>
			
			<kendo:grid-column title="Description"  field="about_description" editor="messageEditor" filterable="false" width="110px" >
			</kendo:grid-column>
		   <kendo:grid-column title="&nbsp;" width="140px">
			  <kendo:grid-column-command>
				 <kendo:grid-column-commandItem name="edit" />
				 <kendo:grid-column-commandItem name="destroy" />
			  </kendo:grid-column-command>
		   </kendo:grid-column>

	</kendo:grid-columns>	

       <kendo:dataSource pageSize="20" requestEnd="onRequestEnd" requestStart="onRequestStart">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-create url="${AboutCreateUrl}" dataType="json" type="GET" contentType="application/json" />
				<kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json" />
				< <kendo:dataSource-transport-update url="${AboutUpdateUrl}" dataType="json" type="GET" contentType="application/json" />
				<kendo:dataSource-transport-destroy url="${AboutDestroy}" dataType="json" type="GET" contentType="application/json" /> 
			</kendo:dataSource-transport>
			
			<kendo:dataSource-schema>
				<kendo:dataSource-schema-model id="about_id">
					<kendo:dataSource-schema-model-fields>

						<kendo:dataSource-schema-model-field name="about_id" type="number" />
						
						<kendo:dataSource-schema-model-field name="about_name" type="string"/>
						
						<kendo:dataSource-schema-model-field name="about_designation" type="string"/>
						
						<kendo:dataSource-schema-model-field name="about_description" type="string"/>					
						
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
			
		</kendo:dataSource>
</kendo:grid>
</div>

<div id="uploadDialog" title="Upload Image" style="display: none;">
 <kendo:upload complete="oncomplete" name="files" upload="onpersonImageUpload" multiple="false" success="onImageSuccess">
				<kendo:upload-async autoUpload="true" saveUrl="${personImage}" />
	</kendo:upload>
	
</div> 

<div id="alertsBox" title="Alert"></div>

<%-- <div id="uploadVisitorImageDialog" title="Upload Image" hidden="false">	
	<kendo:upload name="visitorfile" upload="uploadExtraDataImage" complete="oncomplete" multiple="false"
				accept="image/png,image/jpeg,image/jpg" success="onImageSuccess">
		<kendo:upload-async autoUpload="true" saveUrl="./visitor/visitorImageUploaddoc" />
		</kendo:upload>
</div> --%>


<script>


function onImageSuccess(e) {
	alert("Uploaded Successfully");
	$(".k-upload-files.k-reset").find("li").remove();
	window.location.reload(true);
	
}
 
/* 
function frChangeDesEditor(container, options) 
{
$('<textarea data-text-field="description" name = "description" style="width:150px;height:60px"/>')
    .appendTo(container);
$('<span class="k-invalid-msg" data-for="Enter Description"></span>').appendTo(container);
} */

function messageEditor(container, options) 
{
    $('<textarea name="Description1" data-text-field="message" data-value-field="message" data-bind="value:' + options.field + '" style="width: 240px; height: 66px;" placeholder=" "/>')
         .appendTo(container);
   /*  $('<span class="k-invalid-msg" data-for="ssss"></span>').appendTo(container);  */
}
function aboutUsChangeDeleteEvent(e){
	
	var conf = confirm("Are you sure want to delete this Contact Details?");
	    if(!conf){
	    $('#grid').data().kendoGrid.dataSource.read();
	    throw new Error('deletion aborted');
	     }
}

var SelectedRowId = "";
var SelectedServiceType = "";

function onChangeAboutUs(arg) {
	 var gview = $("#grid").data("kendoGrid");
	 var selectedItem = gview.dataItem(gview.select());
	 SelectedRowId = selectedItem.about_id;
	 SelectedServiceType = selectedItem.typeOfService;
}

function clearFilterAboutUs()
{
   $("form.k-filter-menu button[type='reset']").slice().trigger("click");
   var gridStoreIssue = $("#grid").data("kendoGrid");
   gridStoreIssue.dataSource.read();
   gridStoreIssue.refresh();
}
function oncomplete() {

	
	$("#myImage").attr("src","<c:url value='/about/getpersonimage/" + SelectedRowId+"'/>");
	$("#myImages_"+SelectedRowId).attr("src","<c:url value='/about/getpersonimage/" + SelectedRowId+"'/>");
} 

function onpersonImageUpload(e) {

	
	var files = e.files;

	// Check the extension of each file and abort the upload if it is not .jpg
	$.each(files, function() {
		if (this.extension.toLowerCase() == ".png") {
			e.data = {
					about_id : SelectedRowId
				};
		}
		else if (this.extension.toLowerCase() == ".jpg") {
			 
			
			e.data = {
					about_id : SelectedRowId
				};
		}
		else if (this.extension.toLowerCase() == ".jpeg") {
			
			e.data = {
					about_id : SelectedRowId
				};
		}
		else {
			alert("Only Images can be uploaded\nAcceptable formats are png, jpg and jpeg");
			e.preventDefault();
			return false;
		}
	});
} 
var billDate = "";
var elBillParameterValue = "";

function AboutUsChangeEvent(e)
{
	/***************************  to remove the id from pop up  **********************/
	$('div[data-container-for="about_id"]').remove();
	$('label[for="about_id"]').closest('.k-edit-label').remove();
	/* 
	$(".k-edit-field").each(function () {
		$(this).find("#temPID").parent().remove();  
   	});
 	
	var billDate1 = $('input[name="billDate"]').kendoDatePicker({
		format : "dd/MM/yyyy"
	}).data("kendoDatePicker");
	billDate1.readonly(); */
	
	/* $('input[name="presentReading"]').prop("readonly", true);
	
	$('label[for=todApplicable]').parent().hide();
	$('div[data-container-for="todApplicable"]').hide(); */
		
 	 $('label[for=image]').parent().hide();
	$('div[data-container-for="image"]').hide();
	
	$('label[for=about_id]').parent().hide();
	$('div[data-container-for="about_id"]').hide();
	
	/* $('label[for=accountNo]').parent().hide();
	$('div[data-container-for="accountNo"]').hide();
		
	$('label[for="status"]').parent().hide();  
	$('div[data-container-for="status"]').hide();
	
	$('label[for="personName"]').parent().hide();  
	$('div[data-container-for="personName"]').hide();
	
	$('label[for="createdBy"]').parent().hide();  
	$('div[data-container-for="createdBy"]').hide(); */
	
	 $(".k-grid-cancel").click(function () {
		 var grid = $("#grid").data("kendoGrid");
		 grid.dataSource.read();
		 grid.refresh();
	 });
	
	
	/************************* Button Alerts *************************/
	if (e.model.isNew()) 
    {
		
		$(".k-window-title").text("Add Contact Person Details");
		$(".k-grid-update").text("Save");		
    }
	else{
		
		$(".k-window-title").text("Edit Contact Person Details");
		}
	
	/* $(".k-grid-update").click(function() {
		var st = billDate;
		var pattern = /(\d{2})\/(\d{2})\/(\d{4})/;
		var dt = new Date(st.replace(pattern,'$3-$2-$1'));
		 e.model.set("billDate",dt);
		e.model.set("presentReading", elBillParameterValue);
	  }); */

	}

 /* function AccountNumbers(container, options) 
  {
		$('<input name="Account" id="account" data-text-field="accountNo" validationmessage="Account number is required" data-value-field="accountId" data-bind="value:' + options.field + '" required="true"/>')
		.appendTo(container).kendoComboBox({
		 autoBind : false,
		 placeholder: "Enter Account Number",
		 headerTemplate : '<div class="dropdown-header">'
				+ '<span class="k-widget k-header">Photo</span>'
				+ '<span class="k-widget k-header">Contact info</span>'
				+ '</div>',
			template : '<table><tr><td rowspan=2><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.about_id#'/>" width=40 height=55 alt=\"No Image to Display\" /></span></td>'
				+ '<td align="left"><span class="k-state-default"><b>#: data.personName #</b></span><br>'
				+ '<span class="k-state-default"><i>#: data.accountNo #</i></span><br>'
				+ '<span class="k-state-default"><i>#: data.personType #</i></span></td></tr></table>',
		 dataSource : {
		  transport : {  
		   read :  "${readAccountNumbers}"
		  }
		 },
		 change : function (e) {
		           if (this.value() && this.selectedIndex == -1) {                    
		               alert("Account doesn't exist!");
		               $("#account").data("kendoComboBox").value("");
		        }
		    } 
		});
		
		$('<span class="k-invalid-msg" data-for="Account"></span>').appendTo(container);
  }
  
  
	  
	function dropDownChecksEditor(container, options) {
		$('<input name="Service Type" id="serviceSGRE" data-text-field="typeOfService" required="true" validationmessage="Service type is required" data-value-field="typeOfService" data-bind="value:' + options.field + '" />')
				.appendTo(container)
				.kendoComboBox({
							cascadeFrom : "account",
							autoBind : false,
							placeholder : "Select ServiceType",
							template : '<table><tr>'
									+ '<td align="left"><span class="k-state-default"><b>#: data.typeOfService #</b></span><br>'
									+ '</td></tr></table>',
							select : serviceTypeFunction,
							dataSource : {
								transport : {
									read : "${serviceTypeComboBoxUrl}"
								}
							},
							change : function(e) {
								
								if (this.value() && this.selectedIndex == -1) {
									alert("Service type doesn't exist!");
									$("#serviceSGRE").data("kendoComboBox").value("");
								}
							}

						});

		$('<span class="k-invalid-msg" data-for="Service Type"></span>').appendTo(container);
	} */

	var selectedAccountId = "";
	var selectedServiceType = "";

	/* function serviceTypeFunction(e) {
		var dataItem = this.dataItem(e.item.index());
		selectedAccountId = dataItem.accountId;
		selectedServiceType = dataItem.typeOfService;
		$.ajax({
			type : "GET",
			url : "./frChange/getBillDateAndPreReading",
			async: false,
			dataType : "JSON",
			data : {
				accountId : selectedAccountId,
				serviceType : selectedServiceType,
			    },
			success : function(response) {
				billDate = response.billDate;
				elBillParameterValue = response.elBillParameterValue;
				$('input[name="billDate"]').val(billDate);
				$('input[name="presentReading"]').val(elBillParameterValue);
				}
			});
	}
 */
	function onRequestStart(e){
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();
	}
/* ------------------ Method to open the popup -------------------- */
	
	function clickToUploadImage(){
		$('#uploadDialog').dialog({
			modal : true,
		});
		$(".k-upload-button span").text("Browse File..");
		return false;
			}
	
	function onRequestEnd(e) {

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
					alert("Error: Creating the Contact Details\n\n"
							+ errorInfo);
				}
				if (e.type == "update") {
					alert("Error: Updating the Contact Details\n\n"
							+ errorInfo);
				}
				var grid = $("#grid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
				return false;
			}
			if (e.type == "update" && !e.response.Errors) {
				e.sender.read();
				$("#alertsBox").html("Alert");
				$("#alertsBox").html("Contact details updated successfully");
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
			if (e.type == "create" && !e.response.Errors) {
				e.sender.read();
				$("#alertsBox").html("Alert");
				$("#alertsBox").html("Contact details created successfully");
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
				$("#alertsBox").html("Contact details deleted successfully");
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
				var grid = $("#grid").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
			}
		}
	}
	
	//register custom validation rules

	(function ($, kendo) 
		{   	  
	    $.extend(true, kendo.ui.validator, 
	    {
	         rules: 
	         { // custom rules

	        	 aboutNameSpacesvalidation : function(input,
							params) {
						if (input.filter("[name='about_name']").length
								&& input.val()) {
							if(input.val().trim() == "")
							{
								return false;
							}	
						}
						return true;
					},
					aboutdesignationSpacesvalidation : function(input,
							params) {
						if (input.filter("[name='about_designation']").length
								&& input.val()) {
							if(input.val().trim() == "")
							{
								return false;
							}	
						}
						return true;
					}
							},
							messages : {
								//custom rules messages
								aboutNameSpacesvalidation : "person name cannot contain only spaces", 
								aboutdesignationSpacesvalidation : "designation cannot contain only spaces"
								
							}
						});

	})(jQuery, kendo);
	//End Of Validation
	
</script>
<style>
.k-datepicker span {
	width: 52%
}

.k-datepicker {
	background: white;
}
/* .k-upload-button input {
	z-index: 100000
} */

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
</style>