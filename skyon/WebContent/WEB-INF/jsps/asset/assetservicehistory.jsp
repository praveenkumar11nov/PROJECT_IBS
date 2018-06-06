<%@include file="/common/taglibs.jsp"%>

<c:url value="/common/getPersonListBasedOnPersonType" var="personNamesAutoBasedOnPersonTypeUrl" />

<c:url value="/manpower/getStaffAndVendorName" var="getStaffAndVendorName" />
<c:url value="/manpower/getStaffOrVendorName" var="getStaffOrVendorName" />

<c:url value="/common/getAllChecks" var="allChecksUrl" />
<c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />
<c:url value="/asset/getAllAssetsForAll" var="readAssetUrl" />
<c:url value="/manpower/getMPerson" var="personUrl" />
<c:url value="/users/getPersonNamesList" var="personNameFilterUrl" />
<c:url value="/asset/servicehistory/read" var="readUrl" />
<c:url value="/asset/servicehistory/create" var="createUrl" />
<c:url value="/asset/servicehistory/update" var="updateUrl" />
<c:url value="/asset/servicehistory/delete" var="destroyUrl" />

<!--	Asset M C Url	-->
<c:url value="/asset/maintenancecost/read" var="readAssetMCUrl" />
<c:url value="/asset/maintenancecost/create" var="createAssetMCUrl" />
<c:url value="/asset/maintenancecost/update" var="updateAssetMCUrl" />
<c:url value="/asset/maintenancecost/delete" var="deleteAssetMCUrl" />
	
<!--	Asset M P Url	-->
<c:url value="/asset/maintenance/read" var="readAssetMPUrl" />
<c:url value="/asset/maintenance/create" var="createAssetMPUrl" />
<c:url value="/asset/maintenance/update" var="updateAssetMPUrl" />
<c:url value="/asset/maintenance/delete" var="deleteAssetMPUrl" />

<!-- 	Asset Category & Location Url	 -->
<c:url value="/asset/cat/read" var="transportReadUrlCat" />
<c:url value="/asset/loc/read" var="transportReadUrlLoc" />

<kendo:grid name="gridAssetServiceHistory" resizable="true"	pageable="true" sortable="true" scrollable="true" selectable="true"	edit="assetServiceHistoryEvent" groupable="true" change="onChange" remove="removeService">  
	<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10"></kendo:grid-pageable>
	<kendo:grid-filterable extra="false"><kendo:grid-filterable-operators><kendo:grid-filterable-operators-string eq="Is equal to" /></kendo:grid-filterable-operators></kendo:grid-filterable>
	<kendo:grid-editable mode="popup"/>
	<kendo:grid-toolbar>
		<kendo:grid-toolbarItem name="create" text="Create New Service Record" />
		<kendo:grid-toolbarItem name="assetServiceTemplatesDetailsExport" text="Export To Excel" />
			  <kendo:grid-toolbarItem name="assetServicePdfTemplatesDetailsExport" text="Export To PDF" /> 
		<kendo:grid-toolbarItem text="ClearFilter" template="<a class='k-button' href='\\#' onclick=clearFilter() title='Clear Filter'><span class='k-icon k-i-funnel-clear'></span>Clear Filter</a>"/>
	</kendo:grid-toolbar>
	<kendo:grid-columns>
		<kendo:grid-column title="&nbsp;" field="radioBtn" editor="radioEditor" width="110px" hidden="true" />
		<kendo:grid-column title="Select Category" field="assetTreeCat" width="110px" editor="catEditor" hidden="true" />
		<kendo:grid-column title="Select Location" field="assetTreeLoc" width="110px" editor="locEditor" hidden="true" />
		<kendo:grid-column title="Asset Name *" field="assetId" editor="assetEditor" width="110px" hidden="true" />
		<kendo:grid-column title="Asset Name *" field="assetName" width="110px"  ><kendo:grid-column-filterable><kendo:grid-column-filterable-ui><script>function assetNameFilter(element){element.kendoAutoComplete({	placeholder : "Enter full name",dataTextField : "assetName", dataValueField : "assetId", dataSource : {transport : {	read : "${readAssetUrl}"	}}});}</script></kendo:grid-column-filterable-ui></kendo:grid-column-filterable></kendo:grid-column>
		<kendo:grid-column title="Service&nbsp;Date&nbsp;*" field="ashDate" width="110px" format="{0:dd/MM/yyyy}">
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>
						function fromDateFilter(element) {
							element.kendoDatePicker({
								format : "dd/MM/yyyy",

							});
						}
					</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>
		<kendo:grid-column title="Problem&nbsp;Description&nbsp;*" field="problemDesc" width="130px" editor="problemDescEditor" filterable="false"/>
		<kendo:grid-column title="Service&nbsp;Description&nbsp;*" field="serviceDesc" width="130px" editor="serviceDescEditor" filterable="false"/>
		<kendo:grid-column title="Service&nbsp;Type&nbsp;*" field="serviceType" width="110px" />
		<kendo:grid-column title="Serviced&nbsp;Under&nbsp;*" field="servicedUnder" width="100px" editor="dropDownChecksEditor"><kendo:grid-column-filterable><kendo:grid-column-filterable-ui><script>function servicedUnderFilter(element) {element.kendoDropDownList({optionLabel: "Select",dataSource : {transport : {	read : "${filterDropDownUrl}/servicedUnder"	}}});}</script></kendo:grid-column-filterable-ui></kendo:grid-column-filterable></kendo:grid-column>
		<kendo:grid-column title="Cost&nbsp;Incurred&nbsp;*" field="costIncurred" width="110px" format="{0:n0}"/>
		
		<kendo:grid-column title="Serviced By" field="radioServicedByBtn" editor="radioServicedByEditor" width="110px" hidden="true" />
		
		<kendo:grid-column title="Staff Name *" field="servicedBy" hidden="true" editor="staffEditor" />
		<kendo:grid-column title="Serviced&nbsp;By&nbsp;* " field="servicedByName" width="130px"><kendo:grid-column-filterable><kendo:grid-column-filterable-ui><script>function personNameFilter(element) {element.kendoAutoComplete({placeholder : "Enter full name",dataSource : {	transport : {read : "${personNameFilterUrl}/Staff"}}});	}</script></kendo:grid-column-filterable-ui></kendo:grid-column-filterable></kendo:grid-column>
		<kendo:grid-column title="&nbsp;" width="160px">
			<kendo:grid-column-command>
				<kendo:grid-column-commandItem name="edit" />
				<kendo:grid-column-commandItem name="destroy" />
			</kendo:grid-column-command>
		</kendo:grid-column>
	</kendo:grid-columns>
	<kendo:dataSource pageSize="20" requestEnd="onRequestEnd" requestStart="onRequestStart">
		<kendo:dataSource-transport>
			<kendo:dataSource-transport-create url="${createUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json" />
			<kendo:dataSource-transport-update url="${updateUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-destroy url="${destroyUrl}/" dataType="json" type="GET" contentType="application/json" />
		</kendo:dataSource-transport>
		<kendo:dataSource-schema>
			<kendo:dataSource-schema-model id="ashId">
				<kendo:dataSource-schema-model-fields>
					<kendo:dataSource-schema-model-field name="ashId" type="number" />
					<kendo:dataSource-schema-model-field name="assetId" type="number"><kendo:dataSource-schema-model-field-validation required="true" /></kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="ashDate" type="date"><kendo:dataSource-schema-model-field-validation required="true"/></kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="problemDesc" type="string"><kendo:dataSource-schema-model-field-validation required="true" /></kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="serviceDesc" type="string"><kendo:dataSource-schema-model-field-validation required="true" /></kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="serviceType" type="string"><kendo:dataSource-schema-model-field-validation required="true" pattern="^.{0,45}$"/>	</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="costIncurred" type="number" defaultValue="0"><kendo:dataSource-schema-model-field-validation max="10000000"  min="0" /></kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="servicedUnder" type="string"><kendo:dataSource-schema-model-field-validation /></kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="servicedBy" defaultValue="" type="number"><kendo:dataSource-schema-model-field-validation /></kendo:dataSource-schema-model-field>
					
					<kendo:dataSource-schema-model-field name="createdBy"/>
					<kendo:dataSource-schema-model-field name="updatedBy"/>
				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>
	</kendo:dataSource>
</kendo:grid>

<script>

$("#gridAssetServiceHistory").on("click",".k-grid-assetServiceTemplatesDetailsExport", function(e) {
	  window.open("./assetServiceTemplate/assetServiceTemplatesDetailsExport");
});	  

$("#gridAssetServiceHistory").on("click",".k-grid-assetServicePdfTemplatesDetailsExport", function(e) {
	  window.open("./assetServicePdfTemplate/assetServicePdfTemplatesDetailsExport");
});


    
    var nodeid = "";
    var dropDownDataSource = "";
    
    var assetId= 0;
    function onChange(arg) {
		var gview = $("#gridAssetServiceHistory").data("kendoGrid");
		var selectedItem = gview.dataItem(gview.select());
		assetId = selectedItem.assetId;
		this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
	}
	
    
    function clearFilter(){
		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
		var gridAssetServiceHistory = $("#gridAssetServiceHistory").data("kendoGrid");
		gridAssetServiceHistory.dataSource.read();
		gridAssetServiceHistory.refresh();
	}
    
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
		
		comboBoxDataSource = new kendo.data.DataSource({
		        transport: {
		            read: {
		                url     : "./asset/getAllAssetsOnCatId/"+nodeid, // url to remote data source 
		                dataType: "json",
		                type    : 'GET'
		            }
		        }
		    
		    });
		 $("#assetId").kendoDropDownList({
		        dataSource    : comboBoxDataSource,
		        dataTextField : "assetName",
		        dataValueField: "assetId",
		        optionLabel : {
					assetName : "Select",
					assetId : "",
				},
		    });
	}
  
	function onLocSelect(e) {
		nodeid = $('.k-state-hover').find('#hiddenId').val();
		var nn = $('.k-state-hover').html();
		var spli = nn.split(" <input");
		$('#editNodeText').val(spli[0].trim());

		var kitems = $(e.node).add(
				$(e.node).parentsUntil('.k-treeview', '.k-item'));
		texts = $.map(kitems, function(kitem) {
			return $(kitem).find('>div span.k-in').text();
		});

		dropDownDataSource = new kendo.data.DataSource({
			transport : {
				read : {
					url : "./asset/getAllAssetsOnLocId/" + nodeid, // url to remote data source 
					dataType : "json",
					type : 'GET'
				}
			}
		});
		$("#assetId").kendoDropDownList({
			dataSource : dropDownDataSource,
			dataTextField : "assetName",
			dataValueField : "assetId",
			optionLabel : {
				assetName : "Select",
				assetId : "",
			},
		});
	}
     
    function assetServiceHistoryEvent(e) { 	
    	
    /* 	$('label[for="costIncurred"]').closest('.k-edit-label').hide();
		$('div[data-container-for="costIncurred"]').hide(); */
    	$('div[data-container-for="assetTreeLoc"]').hide();
		$('label[for="assetTreeLoc"]').closest('.k-edit-label').hide();
		$('div[data-container-for="assetTreeCat"]').hide();
		$('label[for="assetTreeCat"]').closest('.k-edit-label').hide();
		/* $('div[data-container-for="servicedBy"]').hide();
		$('label[for="servicedBy"]').closest('.k-edit-label').hide(); */
		
    	$('div[data-container-for="assetName"]').remove();
		$('label[for="assetName"]').closest('.k-edit-label').remove();
		/* $('div[data-container-for="assetTree"]').hide();
		$('label[for="assetTree"]').closest('.k-edit-label').hide(); */
	
		$('div[data-container-for="servicedByName"]').remove();
		$('label[for="servicedByName"]').closest('.k-edit-label').remove();
		
		if (e.model.isNew()) {
			securityCheckForActions("./asset/service/createButton");
			$(".k-window-title").text("Add New Asset Service History");
			$(".k-grid-update").text("Save");
			
		} else {
			securityCheckForActions("./asset/service/updateButton");
			if(e.model.get("servicedUnder") == 'Paid Service'){
				/* $('label[for="costIncurred"]').closest('.k-edit-label').show();
				$('div[data-container-for="costIncurred"]').show(); */
				$('input[name="costIncurred"]').prop("required",true);
				
			}else{
				$('input[name="costIncurred"]').prop("required",false);
				/* $('label[for="costIncurred"]').closest('.k-edit-label').hide();
				$('div[data-container-for="costIncurred"]').hide(); */
			}
			$(".k-window-title").text("Edit Service Information");
		}
	}
    
    
    $("#gridAssetServiceHistory").on("click", ".k-grid-add", function(e) { 
	 	
		 if($("#gridAssetServiceHistory").data("kendoGrid").dataSource.filter()){
				
	   		//$("#grid").data("kendoGrid").dataSource.filter({});
	   		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
				var grid = $("#gridAssetServiceHistory").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
	       }   
	});
    
    function removeService(e){
    	securityCheckForActions("./asset/service/deleteButton");
    	var conf = confirm("Are you sure want to delete this Service Record?");
		if(!conf){
			$('#gridAssetServiceHistory').data('kendoGrid').dataSource.read();
			$('#gridAssetServiceHistory').data('kendoGrid').refresh();
			 throw new Error('deletion aborted');
		}
    }
    function assetEditor(container, options) {
		$('<input name="Asset Name" data-text-field="assetName" data-value-field="assetId" id="assetId" data-bind="value:' + options.field + '" required="true"/>')
				.appendTo(container).kendoDropDownList({
					optionLabel : {
						assetName : "Select",
						assetId : "",
					},
					defaultValue : false,
					sortable : true,
					dataSource : {
						transport : {
							read : {
								url : "${readAssetUrl}",
								type : "GET"
							}
						}
					}
				});
		$('<span class="k-invalid-msg" data-for="Asset Name"></span>')
				.appendTo(container);
	}
   
    function radioEditor(container, options) {
		$('<input type="radio" name=' + options.field + ' value="cat" /> Category &nbsp;&nbsp;&nbsp; <input type="radio" name=' + options.field + ' value="loc"/> Location &nbsp;&nbsp;&nbsp; <input type="radio" name=' + options.field + ' value="all" checked="true"/> All <br>')
				.appendTo(container);
	}
      

	$(document).on('change','input[name="radioBtn"]:radio',function() {

					var radioValue = $('input[name=radioBtn]:checked').val();
					if (radioValue == 'cat') {
						$('div[data-container-for="assetTreeCat"]').show();
						$('label[for="assetTreeCat"]').closest('.k-edit-label').show();
						$('div[data-container-for="assetTreeLoc"]').hide();
						$('label[for="assetTreeLoc"]').closest('.k-edit-label').hide();

					} else if(radioValue == 'loc'){						
						$('div[data-container-for="assetTreeLoc"]').show();
						$('label[for="assetTreeLoc"]').closest('.k-edit-label').show();
						$('div[data-container-for="assetTreeCat"]').hide();
						$('label[for="assetTreeCat"]').closest('.k-edit-label').hide();
					}
					else if(radioValue == 'all'){					
						$('div[data-container-for="assetTreeLoc"]').hide();
						$('label[for="assetTreeLoc"]').closest('.k-edit-label').hide();
						$('div[data-container-for="assetTreeCat"]').hide();
						$('label[for="assetTreeCat"]').closest('.k-edit-label').hide();
						dropDownDataSource = new kendo.data.DataSource({
							transport : {
								read : {
									url : "./asset/getAllAssetsForAll",
									dataType : "json",
									type : 'GET'
								}
							}
						});
						$("#assetId").kendoDropDownList({
							dataSource : dropDownDataSource,
							dataTextField : "assetName",
							dataValueField : "assetId",
							optionLabel : {
								assetName : "Select",
								assetId : "",
							},
						});
					}
				});
	 
	 
	function radioServicedByEditor(container, options) {
		$('<input type="radio" name=' + options.field + ' value="staff" onchange="radioSbChange()"/> Staff &nbsp;&nbsp;&nbsp; <input type="radio" name=' + options.field + ' value="vendor" onchange="radioSbChange()"/> Vendor <br>')
					.appendTo(container);
	}
	 
	function radioSbChange(){
		$('div[data-container-for="servicedBy"]').show();
		$('label[for="servicedBy"]').closest('.k-edit-label').show();
		var type="";
		var radioServicedByBtn = $('input[name=radioServicedByBtn]:checked').val();
		if (radioServicedByBtn == 'staff') {
				$('label[for="servicedBy"]').text("Staff Name *");
				type="Staff";
		} else {
				$('label[for="servicedBy"]').text("Vendor Name *");
				type="Vendor";
		}
		dropDownDataSource = new kendo.data.DataSource({
				transport : {		
					read :  "${getStaffOrVendorName}/"+type
				}
		});
		$("#person1").kendoComboBox({
			template: '<span class="k-state-default"><b>#: data.personName #</b></span><br>' +
				'<span class="k-state-default"><i>#: data.personType #</i></span><br>' +
				'<span class="k-state-default">Mob.&nbsp;:#: data.contactContent #</span>',
			dataSource : dropDownDataSource,
			dataTextField : "personName",
			dataValueField : "personId",
			optionLabel : {
				personName : "Select",
				personId : "",
			},
		});
			
	} 

	function staffEditor(container, options) {
			$('<input name="Received by" id="person1" data-text-field="personName" data-value-field="personId" data-bind="value:' + options.field + '" required="true"/>')
			.appendTo(container).kendoComboBox({
				autoBind : false,
				optionLabel : {
					personName : "Select",
					personId : "",
				},
         		template: 		'<span class="k-state-default"><b>#: data.personName #</b></span><br>' +
                   				'<span class="k-state-default"><i>#: data.personType #</i></span><br>' +
                   				'<span class="k-state-default">Mob.&nbsp;:#: data.contactContent #</span>',
				dataSource : {
					transport : {		
						read :  "${getStaffAndVendorName}"
					}
				},
				change : function (e) {
		            if (this.value() && this.selectedIndex == -1) {                    
						alert("Person doesn't exist!");
		                $("#person1").data("kendoComboBox").value("");
		        	}
			    } 
			});
			
			$('<span class="k-invalid-msg" data-for="Received by"></span>').appendTo(container);
	   	}
    
	 function problemDescEditor(container, options) {
			$('<textarea maxlength="500" name="Problem Description" required="true" data-text-field="problemDesc" data-value-field="problemDesc" data-bind="value:' + options.field + '" style="width: 148px; height: 40px;"/>')
					.appendTo(container);
			$('<span class="k-invalid-msg" data-for="Problem Description"></span>')
			.appendTo(container);
		}
	
	 function serviceDescEditor(container, options) {
			$('<textarea maxlength="500" name="Service Description" required="true" data-text-field="serviceDesc" data-value-field="serviceDesc" data-bind="value:' + options.field + '" style="width: 148px; height: 40px;"/>')
					.appendTo(container);
			$('<span class="k-invalid-msg" data-for="Service Description"></span>')
			.appendTo(container);
		}
	
	 function dropDownChecksEditor(container, options) {
			var res = (container.selector).split("=");
			var attribute = res[1].substring(0, res[1].length - 1);
			$('<select required="true" data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '" name = "'+attribute+'" id = "'+attribute+'Id"/>')
					.appendTo(container).kendoDropDownList({
						optionLabel : {
							text : "Select",
							value : "",
						},
						defaultValue : false,
						sortable : true,
						dataSource : {
							transport : {
								read : "${allChecksUrl}/" + attribute,
							}
						}
					});
			$('<span class="k-invalid-msg" data-for="'+attribute+'"></span>').appendTo(container);
		}
	 
	 function catEditor(container, options) {
			$('<div style="max-height: 100px; overflow: auto; "></div>').appendTo(container).kendoTreeView({
					dataTextField : "assetcatText",
					template : " #: item.assetcatText # <input type='hidden' id='hiddenId' class='#: item.assetcatText ##: item.assetcatId#' value='#: item.assetcatId#'/>",
					select : onCatSelect,
					name : "treeview",
					dataSource : {
						 transport: {
	                         read: {
	                             url: "${transportReadUrlCat}",
	                             contentType: "application/json",
	                             type : "GET"
	                         }
	                     },
	                     schema: {
	                         model: {
	                             id: "assetcatId",
	                             hasChildren: "hasChilds"
	                         }
	                     }
						}
					});
		} 
		
		 
		 function locEditor(container, options) {
				$('<div style="max-height: 100px; overflow: auto; "></div>')
						.appendTo(container).kendoTreeView({

						dataTextField : "assetlocText",
						template : " #: item.assetlocText # <input type='hidden' id='hiddenId' class='#: item.assetlocText ##: item.assetlocId#' value='#: item.assetlocId#'/>",
						select : onLocSelect,
						name : "treeview2",
						dataSource : {
							 transport: {
		                         read: {
		                             url: "${transportReadUrlLoc}",
		                             contentType: "application/json",
		                             type : "GET"
		                         }
		                     },
		                     schema: {
		                         model: {
		                             id: "assetlocId",
		                             hasChildren: "hasChilds"
		                         }
		                     }
							}
					});
			} 
		 
		 $(document).on('change', 'select[id="servicedUnderId"]', function() {

				var selectedText = $('#servicedUnderId option:selected').eq(0).text();

				if (selectedText == 'Paid Service') {

					/* $('label[for="costIncurred"]').closest('.k-edit-label').show();
					$('div[data-container-for="costIncurred"]').show(); */
					$('input[name="costIncurred"]').prop("required",true);
				} else {
					$('input[name="costIncurred"]').prop("required",false);
				/* 	$('label[for="costIncurred"]').closest('.k-edit-label').hide();
					$('div[data-container-for="costIncurred"]').hide(); */
				 }

			});
		 
		 function onRequestEnd(a) {
				if (a.type == 'create') {
					alert("Added Successfully");
					$('#gridAssetServiceHistory').data('kendoGrid').dataSource.read();
					$('#gridAssetServiceHistory').data('kendoGrid').refresh();
				} else if (a.type == 'update') {
					alert("Updated Successfully");
					$('#gridAssetServiceHistory').data('kendoGrid').dataSource.read();
					$('#gridAssetServiceHistory').data('kendoGrid').refresh();
				} else if(a.type == 'destroy'){
					alert("Deleted Successfully");
				}
			} 
		 
		 function onRequestStart(e) {
			    $('.k-grid-update').hide();
		        $('.k-edit-buttons')
		                .append(
		                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
		        $('.k-grid-cancel').hide();
		        
				/* var gridAssetServiceHistory = $('#gridAssetServiceHistory').data("kendoGrid");
				if(gridAssetServiceHistory != null)
				{
					gridAssetServiceHistory.cancelRow();
				} */
			} 
		 
		 (function ($, kendo) 
					{   	  
				    $.extend(true, kendo.ui.validator, 
				    {
				         rules: 
				         { // custom rules
				        	 assetSTValidation : function(input, params) {
									if (input.filter("[name='serviceType']").length
											&& input.val()) {
										return /^[a-z\d\-_.\s]+$/i.test(input.val());
									}
									return true;
							},
				         },
				         messages: 
				         {
				        	 assetSTValidation: "Invalid Asset Service Type : Special Characters not allowed"			
				     	 }
				    });
				    
				})(jQuery, kendo);
    </script>