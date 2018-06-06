<%@include file="/common/taglibs.jsp"%>

	<c:url value="/visitorparking/read" var="readUrl" />
	
	<c:url value="/visitorparking/parkingslotNo" var="readSlotNo" />
<c:url value="storevisitor/readVisitorName" var="readVisitorName"/>
<c:url  value="/visitorparking/readcontactNo" var="readContact"/>
<c:url value="/visitorparking/updatevisitorparking" var="updateUrl" />
<c:url value="/visitorparking/createvisitorparking" var="createvisitorparkingUrl"/>

	<kendo:grid name="grid" pageable="true" resizable="true"
		sortable="true" filterable="true" scrollable="true" height="430px">
		<kendo:grid-editable mode="popup" />
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Visitor Parking Details" />
		</kendo:grid-toolbar>
		<kendo:grid-columns>
			<%-- <kendo:grid-column title="ID" field="vpId" />    --%>
			<kendo:grid-column title="Contact-No" field="vmContactNo" editor="VisitorContact"  filterable="true" width="100px" /> 
			<kendo:grid-column title="Visitor-Name" field="vmName" editor="VisitorAutoCompleteEditor"
				filterable="true" width="150px" />
			<kendo:grid-column title="Parking Slot No" field="psSlotNo"
				filterable="false" width="100px" editor="ParkingSlotNo" />
			<kendo:grid-column title="Expected Hours" field="vpExpectedHours"
				width="100px" />
			<kendo:grid-column title="Status" field="vpStatus" editor="ParkingStatus"  filterable="false"
				width="100px" />



			<kendo:grid-column title="&nbsp;" width="160px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit" />

				</kendo:grid-column-command>
			</kendo:grid-column>
		</kendo:grid-columns>
		<kendo:dataSource pageSize="20" requestEnd="onRequestEnd">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-create url="${createvisitorparkingUrl}"
					dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-read url="${readUrl}" dataType="json"
					type="POST" contentType="application/json" />
				<kendo:dataSource-transport-update url="${updateUrl}"
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
				<kendo:dataSource-schema-model id="vpId">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="vmName">
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="vmContactNo">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>

<kendo:dataSource-schema-model-field name="psSlotNo">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>

						<kendo:dataSource-schema-model-field name="vpExpectedHours"
							type="number">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>


						<kendo:dataSource-schema-model-field name="vpStatus" type="string">
							<kendo:dataSource-schema-model-field-validation required="true" />
						</kendo:dataSource-schema-model-field>




					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
	</kendo:grid>
 
<script>
	/*Spring Method level Security for Add Button  */

	//register custom validation rules
	
	$("#grid").on("click",".k-grid-add",function(){

$(".k-window-title").text("Add Visitor Parking Details");
$(".k-grid-update").text("Add/Create");
$(".k-grid-Activate").hide();
$('.k-edit-field .k-input').first().focus();

});
	
	
	(function($, kendo) {
		$
				.extend(
						true,
						kendo.ui.validator,
						{
							rules : { // custom rules
								Visitornamevalidation : function(input, params) {
									//check for the name attribute 
									if (input.filter("[name='vmName']").length
											&& input.val()) {
										return /^[A-Za-z]+[ a-zA-Z]*[ a-zA-Z]/
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
</script>

<script type="text/javascript">
	function ParkingStatus(container, options) {
		var booleanData = [ {

			text : 'select',
			value : ""

		}, {
			text : 'Required',
			value : "required"
		}, {
			text : 'Not Required',
			value : "notrequired"
		} ];

		$('<input />').attr('data-bind', 'value:vpStatus').appendTo(container)
				.kendoDropDownList({
					dataSource : booleanData,
					dataTextField : 'text',
					dataValueField : 'value'
				});

	}

	$("#grid").on("click", ".k-grid-add", function() {

		$('label[for=vinDt]').parent().remove();
		$('label[for=voutDt]').parent().remove();
		/* $('label[for=vmId]').parent().remove; */

	});

	function ParkingSlotNo(container, options) {
		$(
				'<input data-text-field="psSlotNo" data-value-field="psId" data-bind="value:' + options.field +  '"/>')
				.appendTo(container).kendoComboBox({
					optionLabel : "Select slot No",
					defaultValue : false,
					sortable : true,
					dataSource : {
						transport : {
							read : "${readSlotNo}"
						}
					}
				});
	}






	function VisitorContact(container, options) {
		   $(
		     '<input data-text-field="vmContactNo" data-value-field="vmContactNo" id="vmContactNo" data-bind="value:' + options.field +  '"/>')
		     .appendTo(container).kendoComboBox({
		      defaultValue : false,
		      sortable : true,
		      dataSource : {
		       transport : {
		        read : "${readContact}"
		       }
		      }
		     });
		  }





	
	function VisitorAutoCompleteEditor(container, options) {
		   $(
		     '<input data-text-field="vmName" data-value-field="vmId" data-bind="value:' + options.field +  '"/>')
		     .appendTo(container).kendoComboBox({
		      defaultValue : false,
		      sortable : true,
		      cascadeFrom: "vmContactNo",
		      dataSource : {
		       transport : {
		        read : "${readVisitorName}"
		       }
		      }
		     });
		  }
	function ParkingStatus(container, options) {
		var booleanData = [ {

			text : 'select',
			value : ""
			
		}, {
			text : 'Required',
			value : "Required"
		}, {
			text : 'Not Required',
			value : "NotRequired"
		} ];

		$('<input />').attr('data-bind', 'value:vpStatus').appendTo(
				container).kendoDropDownList({
			dataSource : booleanData,
			dataTextField : 'text',
			dataValueField : 'value'
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
   					alert("Error: Creating the Visitor Parking record\n\n" + errorInfo);
   				}
   		
   				if (e.type == "update") {
   					alert("Error: Updating the Visitor Parking record\n\n" + errorInfo);
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
		}  			
		
	}













	/* 
	function onRequestEnd(e) {
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
	} */
</script>

 
