<%@include file="/common/taglibs.jsp"%>
	<c:url value="/numberofbilled/billRead" var="readUrl" />
	
	<c:url value="/numberofbilled/getTypeOfService" var="getTypeOfService" />
	<c:url value="/common/getAllChecks" var="allChecksUrl" />
	<c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />
	<c:url value="/servicetasks/saveorupadteordelete" var="serviceSaveOrUpdateOrDelete" />
	
	
	
	<kendo:grid name="grid" edit="serviceParameterMasterEdit" pageable="true" scrollable="true" sortable="true" reorderable="true" groupable="true" selectable="true"
		resizable="true">
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10"
			input="true" numeric="true"></kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
			<kendo:grid-filterable-operators>
				<kendo:grid-filterable-operators-string eq="Is equal to" />
			</kendo:grid-filterable-operators>
		</kendo:grid-filterable>
		<kendo:grid-editable mode="popup"
			confirmation="Are You Sure to delete?" />
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem template="<input id='monthDatePicker' type=date/>"/>
			<kendo:grid-toolbarItem template="<button class='k-button' onclick='consolidateBill()'>Consolidate Bills</button>"/>
			<kendo:grid-toolbarItem template="<a class='k-button' href='\\#' onclick=clearFilter() title='Clear Filter'><span class='k-icon k-i-funnel-clear'></span></a>"/>
		</kendo:grid-toolbar>


		<kendo:grid-columns>
			<kendo:grid-column title="Consolidate Bill Id" field="elBillId" width="1px"	hidden="true"/>
			<kendo:grid-column title="Service Type" field="typeOfService" width="100px" filterable="false"/>
			<kendo:grid-column title="No.&nbsp;Of&nbsp;Bills" field="noCustomer" width="170px" filterable="false"/>
			<kendo:grid-column title="No.&nbsp;Of&nbsp;Paid&nbsp;Bills" field="numberBilled"	width="200px" filterable="false" />
			<kendo:grid-column title="Payment&nbsp;Pending" field="numberPending" width="170px" filterable="false"/>
			<kendo:grid-column title="Consolidated" field="consolidated" width="170px" filterable="false"/>
			<kendo:grid-column title="Consolidation pending" field="consolidatePending" width="170px" filterable="false"/>
		</kendo:grid-columns>	
		<kendo:dataSource requestStart="onRequestStartServiceMaster" requestEnd="onRequestEndServiceMaster">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json" />
			</kendo:dataSource-transport>
		</kendo:dataSource>
	</kendo:grid>
<div id="alertsBox" title="Alert"></div>
<script>
		

		function consolidateBill(){
			
			var month = $("#monthDatePicker").val();
			
			if(month == '' ){
				alert("Select month to consolidate Bill");
			}else{
			var result=securityCheckForActionsForStatus("./BillGeneration/ConsolidateBills/consolidateBillButton"); 
			  if(result=="success"){
			$.ajax({
				type : "GET",
				url : "./consolidate/consolidateBills?month="+month,
				dataType : "text",
				success : function(response) {
					alert("Consolidated");
					$('#grid').data('kendoGrid').dataSource
					.read();
			$('#grid').data('kendoGrid').refresh();
				}
			});
			  }
			}
		}
		
		$(document)
		.ready(function() {
			
			var todayDate = new Date();
			var picker = $("#monthDatePicker").kendoDatePicker({
						 format: "MM/yyyy"
					}).data("kendoDatePicker"),
		    dateView = picker.dateView;
			dateView.calendar.element.removeData("dateView");        
			picker.max(todayDate);
	      	picker.options.depth = dateView.options.depth = 'year';
	      	picker.options.start = dateView.options.start = 'year';
	       	picker.value(picker.value());
	       
	       	$('#monthDatePicker').keyup(function() {
				$('#monthDatePicker').val("");
			});
	      
			
			/*  var data = [
	                     { text: "January", value: "1" },
	                     { text: "Febrauary", value: "2" },
	                     { text: "March", value: "3" },
	                     { text: "April", value: "4" },
	                     { text: "May", value: "5" },
	                     { text: "June", value: "6" },
	                     { text: "July", value: "7" },
	                     { text: "August", value: "8" },
	                     { text: "September", value: "9" },
	                     { text: "October", value: "10" },
	                     { text: "November", value: "11" },
	                     { text: "December", value: "12" },
	                 ];

	                 // create DropDownList from input HTML element
	                 $("#month").kendoDropDownList({
	                     dataTextField: "text",
	                     dataValueField: "value",
	                     dataSource: data
	                 }); */
		});
		
		
	function clearFilter() {
				//custom actions

				$("form.k-filter-menu button[type='reset']").slice()
						.trigger("click");
				var gridServiceMaster = $("#grid").data("kendoGrid");
				gridServiceMaster.dataSource.read();
				gridServiceMaster.refresh();
	}
	
/* 	var monthType="monthType";
	 $("#monthType").kendoDropDownList({
       autoBind: false,
       optionLabel : "Select",
       dataTextField: "text",
       dataValueField: "value",
       dataSource: {  
           transport:{
           	read:"${allChecksUrl}/"+monthType,
           }
       }
   }); */
	 
	
  
	function dropDownChecksEditor(container, options) {
		var res = (container.selector).split("=");
		var attribute = res[1].substring(0, res[1].length - 1);

		$(
				'<input data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '" name = "'+attribute+'" id = "'+attribute+'Id"/>')
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
		$('<span class="k-invalid-msg" data-for="'+attribute+'"></span>')
				.appendTo(container);
	}
	
	 
	function onRequestStartServiceMaster(e)
	{
		var grid = $("#grid").data("kendoGrid");
		grid.cancelRow();
	}
	function onRequestEndServiceMaster(e) 
	{
		displayMessage(e, "grid", "Service Master");
	}
	function serviceParameterMasterEdit(e)
	{
		var sessionUserLoginName = '<%=session.getAttribute("userId")%>';
		
		$('label[for="elBillId"]').remove();
		$('div[data-container-for="elBillId"]').remove();


		
		
		$('.k-edit-label:nth-child(18n+1)').each(
				function(e) {
					$(this).nextAll(':lt(17)').addBack().wrapAll(
							'<div class="wrappers"/>');
				});
		
		$('a[id="temPID"]').remove();
	
		if (e.model.isNew()) 
	    {
			$(".k-window-title").text("Add New Store Record");
			$(".k-grid-update").text("Save");
	
	    }
		else
		{
			$(".k-window-title").text("Edit");
		}

	}
</script>
