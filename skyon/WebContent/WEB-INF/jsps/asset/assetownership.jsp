<%@include file="/common/taglibs.jsp"%>

<c:url value="/asset/getAllAssetsForAll" var="readAssetUrl" />

<c:url value="/asset/readassetforownersship" var="readAssetForOwnersShipUrl" />
<c:url value="/users/getPersonNamesList" var="personNameFilterUrl" />
<c:url value="/users/ownerShipNameFilterUrl" var="ownerShipNameFilterUrl" />
<c:url value="/users/maintainanceOwnerNameUrl" var="maintainanceOwnerNameUrl" />

<c:url value="/asset/ownership/read" var="readUrl" />
<c:url value="/asset/ownership/create" var="createUrl" />
<c:url value="/asset/ownership/update" var="updateUrl" />
<c:url value="/asset/ownership/delete" var="destroyUrl" />

<c:url value="/asset/getstaff" var="personUrl1" />
<c:url value="/asset/getstaff" var="personUrl2" />
<!-- 	Asset Category & Location Url	 -->
<c:url value="/asset/cat/read" var="transportReadUrlCat" />
<c:url value="/asset/loc/read" var="transportReadUrlLoc" />

<kendo:grid name="gridAssetOwnerShip" resizable="true" pageable="true"	sortable="true" scrollable="true" selectable="true"	edit="assetOwnerEvent" groupable="true" change="changeOwnerShip" remove="removeOwnership"> 
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10"></kendo:grid-pageable>
		<kendo:grid-filterable extra="false"><kendo:grid-filterable-operators><kendo:grid-filterable-operators-string eq="Is equal to" /></kendo:grid-filterable-operators></kendo:grid-filterable>
		<kendo:grid-editable mode="popup"/>
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Ownership"/>
			<kendo:grid-toolbarItem name="assetOwnerTemplatesDetailsExport" text="Export To Excel" />
			  <kendo:grid-toolbarItem name="assetOwnerPdfTemplatesDetailsExport" text="Export To PDF" /> 
			<kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilter() title='Clear Filter'><span class='k-icon k-i-funnel-clear'></span>Clear Filter</a>"/>
		</kendo:grid-toolbar>
		<kendo:grid-columns>
			<kendo:grid-column title="&nbsp;" field="radioBtn" editor="radioEditor" width="110px" hidden="true" />
			<kendo:grid-column title="Select Category" field="assetTreeCat" width="110px" editor="catEditor" hidden="true" />
			<kendo:grid-column title="Select Location" field="assetTreeLoc" width="110px" editor="locEditor" hidden="true" />
			<kendo:grid-column title="Asset&nbsp;Name&nbsp;*" field="assetId" editor="assetEditor" width="110px" hidden="true" />
			<kendo:grid-column title="Asset&nbsp;Name&nbsp;*" field="assetName" width="110px" ><kendo:grid-column-filterable><kendo:grid-column-filterable-ui><script>function assetNameFilter(element){element.kendoAutoComplete({	placeholder : "Enter Asset name",dataTextField : "assetName", dataValueField : "assetId", dataSource : {transport : {	read : "${readAssetUrl}"	}}});}</script></kendo:grid-column-filterable-ui></kendo:grid-column-filterable></kendo:grid-column>
			<kendo:grid-column title="Owner&nbsp;*" field="ownerShip"	editor="ownerShipEditor1" width="110px" hidden="true" />
			<kendo:grid-column title="Owner&nbsp;Ship&nbsp;Name *" field="ownerShipName" width="110px"><kendo:grid-column-filterable><kendo:grid-column-filterable-ui><script>function personNameFilter(element) {element.kendoAutoComplete({placeholder : "Enter OwnerShip name",dataSource : {	transport : {read : "${ownerShipNameFilterUrl}"}}});	}</script></kendo:grid-column-filterable-ui></kendo:grid-column-filterable></kendo:grid-column>
			<kendo:grid-column title="Maintainance&nbsp;Owner&nbsp;*" field="maintainanceOwner" editor="ownerShipEditor2" width="110px"	hidden="true" />
			<kendo:grid-column title="Maintainance&nbsp;Owner&nbsp;Name&nbsp;*" field="maintainanceOwnerName" width="110px" ><kendo:grid-column-filterable><kendo:grid-column-filterable-ui><script>function personNameFilter(element) {element.kendoAutoComplete({placeholder : "Enter Maintainance name",dataSource : {	transport : {read : "${maintainanceOwnerNameUrl}"}}});	}</script></kendo:grid-column-filterable-ui></kendo:grid-column-filterable></kendo:grid-column>
			<kendo:grid-column title="Ownership&nbsp;Start&nbsp;Date&nbsp;*" field="aoStartDate" width="100px" format="{0:dd/MM/yyyy}" >
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
			<kendo:grid-column title="Ownership&nbsp;End&nbsp;Date&nbsp;*" field="aoEndDate" width="100px" format="{0:dd/MM/yyyy}" >
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
				<kendo:dataSource-transport-destroy url="${destroyUrl}/"	dataType="json" type="GET" contentType="application/json" />
			</kendo:dataSource-transport>
			<kendo:dataSource-schema>
				<kendo:dataSource-schema-model id="aoId">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="aoId" type="number" />
						<kendo:dataSource-schema-model-field name="assetId" type="number"><kendo:dataSource-schema-model-field-validation required="true" /></kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="ownerShip" type="number" defaultValue=""><kendo:dataSource-schema-model-field-validation /></kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="maintainanceOwner" type="number" defaultValue=""><kendo:dataSource-schema-model-field-validation /></kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="aoStartDate" type="date" defaultValue=""><kendo:dataSource-schema-model-field-validation /></kendo:dataSource-schema-model-field>  
						<kendo:dataSource-schema-model-field name="aoEndDate" type="date" defaultValue=""><kendo:dataSource-schema-model-field-validation /></kendo:dataSource-schema-model-field>  
						<kendo:dataSource-schema-model-field name="createdBy"/>
						<kendo:dataSource-schema-model-field name="updatedBy"/>
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
</kendo:grid>
<script>
$("#gridAssetOwnerShip").on("click",".k-grid-assetOwnerTemplatesDetailsExport", function(e) {
	  window.open("./assetOwnerInvoiceTemplate/assetOwnerInvoiceTemplatesDetailsExport");
});	  

$("#gridAssetOwnerShip").on("click",".k-grid-assetOwnerPdfTemplatesDetailsExport", function(e) {
	  window.open("./assetOwnerInvoicePdfTemplate/assetOwnerPdfTemplatesDetailsExport");
});

    
    var nodeid = "";
    var dropDownDataSource = "";
    
    function clearFilter(){
		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
		var gridAssetOwnerShip = $("#gridAssetOwnerShip").data("kendoGrid");
		gridAssetOwnerShip.dataSource.read();
		gridAssetOwnerShip.refresh();
	}
    var aoId ="0";
    var assetId ="0";
    function changeOwnerShip(arg) {
		var gview = $("#gridAssetOwnerShip").data("kendoGrid");
		var selectedItem = gview.dataItem(gview.select());
		aoId = selectedItem.aoId;
		assetId = selectedItem.assetId;
		this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
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
		dropDownDataSource = new kendo.data.DataSource({
		        transport: {
		            read: {
		                url     : "./asset/getAllAssetsOnCatId/"+nodeid + "?from=ownership&assetId="+assetId, // url to remote data source 
		                dataType: "json",
		                type    : 'GET'
		            }
		        }
		    
		    });
		 $("#assetId").kendoDropDownList({
		        dataSource    : dropDownDataSource,
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
					url : "./asset/getAllAssetsOnLocId/" + nodeid + "?from=ownership&assetId="+assetId, // url to remote data source 
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

	function assetOwnerEvent(e) {

		$('div[data-container-for="assetTreeLoc"]').hide();
		$('label[for="assetTreeLoc"]').closest('.k-edit-label').hide();
		$('div[data-container-for="assetTreeCat"]').hide();
		$('label[for="assetTreeCat"]').closest('.k-edit-label').hide();
		
		$('div[data-container-for="assetName"]').remove();
		$('label[for="assetName"]').closest('.k-edit-label').remove();
		/* $('div[data-container-for="assetTree"]').hide();
		$('label[for="assetTree"]').closest('.k-edit-label').hide(); */
		$('div[data-container-for="ownerShipName"]').remove();
		$('label[for="ownerShipName"]').closest('.k-edit-label').remove();
		$('div[data-container-for="maintainanceOwnerName"]').remove();
		$('label[for="maintainanceOwnerName"]').closest('.k-edit-label').remove();
		
		var aoEndDate = $('input[name="aoEndDate"]').kendoDatePicker().data("kendoDatePicker");
		aoEndDate.min(e.model.get("aoStartDate"));
		var aoStartDate = $('input[name="aoStartDate"]').kendoDatePicker().data("kendoDatePicker");
		aoStartDate.max(e.model.get("aoEndDate"));
		if (e.model.isNew()) {
			securityCheckForActions("./asset/ownership/createButton");
			assetId = "0";
			$(".k-window-title").text("Assign Asset Ownership");
			$(".k-grid-update").text("Save");		
		} else {
			securityCheckForActions("./asset/ownership/updateButton");
			assetId= e.model.get("assetId");
			$(".k-window-title").text("Edit Ownership information");
		}
		
		$('input[name="aoStartDate"]').change(function() {
			//$('input[name="aoEndDate"]').val("");
			aoEndDate.min($('input[name="aoStartDate"]').val());
		});
		$('input[name="aoEndDate"]').change(function() {
			//$('input[name="aoStartDate"]').val("");
			aoStartDate.max($('input[name="aoEndDate"]').val());
		});
		
		$('input[name="aoStartDate"]').keyup(function() {
			$('input[name="aoStartDate"]').val("");
		});
		
		$('input[name="aoEndDate"]').keyup(function() {
			$('input[name="aoEndDate"]').val("");
		});
	}
	
	
	$("#gridAssetOwnerShip").on("click", ".k-grid-add", function(e) { 
	 	
		 if($("#gridAssetOwnerShip").data("kendoGrid").dataSource.filter()){
				
	   		//$("#grid").data("kendoGrid").dataSource.filter({});
	   		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
				var grid = $("#gridAssetOwnerShip").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
	       }   
	});
	
	function removeOwnership(e){
		securityCheckForActions("./asset/ownership/deleteButton");
		var conf = confirm("Are u sure want to delete this Ownership Record?");
		if(!conf){
			$('#gridAssetOwnerShip').data('kendoGrid').dataSource.read();
			$('#gridAssetOwnerShip').data('kendoGrid').refresh();
			 throw new Error('deletion aborted');
		}
	}

	function assetEditor(container, options) {
		if (options.model.assetId == 0) {
			assetId = "0";
		}
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
						url : "${readAssetForOwnersShipUrl}/"+assetId,
						type : "POST"
					}
				}
			}
		});
		$('<span class="k-invalid-msg" data-for="Asset Name"></span>').appendTo(container);
	}

	function ownerShipEditor1(container, options) {
		$('<input id="staff" name="Staff Name" required="true" data-text-field="pn_Name"  data-value-field="personId" data-bind="value:' + options.field + '"/>')
				.appendTo(container).kendoComboBox({
					placeholder: "Select",
					template : ''
						+ '<span class="k-state-default"><b>#:data.pn_Name#</b></span><br>'
						+ '<span class="k-state-default"><i>#:data.designation#, #:data.department# </i> </span><br>',
					optionLabel : {
						pn_Name : "Select",
						personId : "",
					},
					dataSource : {
						transport : {
							read : "${personUrl1}"
						}
					},
					change : function (e) {
			            if (this.value() && this.selectedIndex == -1) {                    
							alert("Staff doesn't exist!");
			                $("#staff").data("kendoComboBox").value("");
			        	}
				    } 
				});
		$('<span class="k-invalid-msg" data-for="Staff Name"></span>').appendTo(container);
	}
	
	function ownerShipEditor2(container, options) {
		$('<input id="miantstaff" name="Maint Owner" required="true" data-text-field="pn_Name"  data-value-field="personId" data-bind="value:' + options.field + '"/>')
				.appendTo(container).kendoComboBox({
					placeholder: "Select",
					template : ''
						+ '<span class="k-state-default"><b>#:data.pn_Name#</b></span><br>'
						+ '<span class="k-state-default"><i>#:data.designation#, #:data.department# </i> </span><br>',
					optionLabel : {
						pn_Name : "Select",
						personId : "",
					},
					dataSource : {
						transport : {
							read : "${personUrl2}"
						}
					},
					change : function (e) {
			            if (this.value() && this.selectedIndex == -1) {                    
							alert("Staff doesn't exist!");
			                $("#miantstaff").data("kendoComboBox").value("");
			        	}
				    } 
				});
		$('<span class="k-invalid-msg" data-for="Maint Owner"></span>').appendTo(container);
	}

	 function radioEditor(container, options) {$(
					'<input type="radio" name=' + options.field + ' value="cat" /> Category &nbsp;&nbsp;&nbsp; <input type="radio" name=' + options.field + ' value="loc"/> Location &nbsp;&nbsp;&nbsp; <input type="radio" name=' + options.field + ' value="all" checked="true" /> All <br>')
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
									url : "./asset/getAllAssetsForAll?from=ownership&assetId="+assetId,
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
	    
	 function catEditor(container, options) {

			$('<div style="max-height: 100px; overflow: auto; "></div>')
					.appendTo(container).kendoTreeView({

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
		 
			function onRequestEnd(a) {
				if (a.type == 'create') {
					alert("Added Successfully");
					$('#gridAssetOwnerShip').data('kendoGrid').dataSource.read();
					$('#gridAssetOwnerShip').data('kendoGrid').refresh();
				} else if (a.type == 'update') {
					alert("Updated Successfully");
					$('#gridAssetOwnerShip').data('kendoGrid').dataSource.read();
					$('#gridAssetOwnerShip').data('kendoGrid').refresh();
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
		        
				/* var gridAssetOwnerShip = $('#gridAssetOwnerShip').data("kendoGrid");
				if(gridAssetOwnerShip != null)
				{
					gridAssetOwnerShip.cancelRow();
				} */
			} 
			
			
			(function ($, kendo) { $.extend(true, kendo.ui.validator,  {
				         rules: { 
				        	 aoStartDateValidation : function(input, params){
				                      if (input.attr("name") == "aoStartDate"){
				                       return $.trim(input.val()) !== "";
				                      }
				                      return true;
				               },
				               aoEndDateValidation : function(input, params){
				                      if (input.attr("name") == "aoEndDate"){
				                       return $.trim(input.val()) !== "";
				                      }
				                      return true;
				               },
				         },
				         messages: 
				         {
				        	 aoStartDateValidation: "Start Date is Required",
				        	 aoEndDateValidation: "End Date is required"
				     	 }
				    });
				    
				})(jQuery, kendo);
			
	 
</script>

<style>
.k-datepicker span{
	width: 53%
}

.k-header{
	background: white
}
</style>