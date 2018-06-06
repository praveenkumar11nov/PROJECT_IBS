<%-- <%@include file="/common/taglibs.jsp"%>

<c:url value="/customerpreorder/read" var="readUrl" />
<c:url value="/customerpreorder/create" var="createUrl" />


<kendo:grid name="itemGrid" pageable="true" resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true" edit="CustomerPreOrderEvent"  >
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true">
		<kendo:grid-pageable-messages itemsPerPage="Notification per page" empty="No Notification to display" refresh="Refresh all the Notification" 
			display="{0} - {1} of {2} Services" first="Go to the first page of Notification" last="Go to the last page of Notification" next="Go to the Notification"
			previous="Go to the previous page of Notification"/>
					</kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
		 <kendo:grid-filterable-operators>
		  	<kendo:grid-filterable-operators-string eq="Is equal to" />
		 </kendo:grid-filterable-operators>
			
		</kendo:grid-filterable>
		  <kendo:grid-editable mode="popup"
			confirmation="Are you sure you want to remove this Notification?" />
		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Notification" />
		</kendo:grid-toolbar>
		<kendo:grid-toolbarTemplate>
			<div class="toolbar">
			<a class="k-button k-button-icontext k-grid-add" href="#">
		            <span class="k-icon k-add"></span>
		            Add Item Master
	        	</a>
			 <a class='k-button' href='\\#' onclick="clearFilter()"><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>  
			</div>  	
    	</kendo:grid-toolbarTemplate>
    	<kendo:grid-columns>
    	<kendo:grid-column title="Item Name*" field="itemName"  width="120px" >
		</kendo:grid-column>
		
			<kendo:grid-column title="Item Quantity*" field="itemQuantity"  width="120px" >
		</kendo:grid-column>
		
			<kendo:grid-column title="Item Price*" field="itemPrice"  width="120px" >
		</kendo:grid-column>
		
			<kendo:grid-column title="Item TPrice*" field="itemTotalPrice"  width="120px" >
		</kendo:grid-column>
		
			<kendo:grid-column title="Item CusName*" field="cusName"  width="120px" >
		</kendo:grid-column>
			<kendo:grid-column title="Cus Num*" field="cusNum"  width="120px" >
		</kendo:grid-column>
		
		<kendo:grid-column title="Email*" field="Email"  width="120px" >
		</kendo:grid-column>
			
				<kendo:grid-column title="Email*" field="customerStatus"  width="120px" >
		</kendo:grid-column>
		
		<kendo:grid-column title="&nbsp;" width="220px">
				<kendo:grid-column-command>
					<kendo:grid-column-commandItem name="edit" />
					<kendo:grid-column-commandItem name="destroy" />
				</kendo:grid-column-command>
				</kendo:grid-column>
				
			
				
				</kendo:grid-columns>
				
			<kendo:dataSource pageSize="5" >
			<kendo:dataSource-transport>
 <kendo:dataSource-transport-read url="${readUrl}" dataType="json" type="POST" contentType="application/json"/>
			<kendo:dataSource-transport-create url="${createUrl}" dataType="json" type="GET" contentType="application/json"/>
<kendo:dataSource-transport-update url="${updateUrl}" dataType="json" type="GET" contentType="application/json" />
			<kendo:dataSource-transport-destroy url="${destroyUrl}" dataType="json" type="GET" contentType="application/json"/>  
				
			</kendo:dataSource-transport>
			
			 <kendo:dataSource-schema>
				<kendo:dataSource-schema-model id="cpid">
					<kendo:dataSource-schema-model-fields>
					<kendo:dataSource-schema-model-field name="cpid" type="number" />
						<kendo:dataSource-schema-model-field name="itemName" type="String"/>
						
						<kendo:dataSource-schema-model-field name="itemQuantity" type="String"/>
						<kendo:dataSource-schema-model-field name="itemPrice" type="String"/>
						<kendo:dataSource-schema-model-field name="itemTotalPrice" type="String"/>
							<kendo:dataSource-schema-model-field name="cusName" type="String"/>
								<kendo:dataSource-schema-model-field name="cusNum" type="String"/>
						
							<kendo:dataSource-schema-model-field name="Email" type="String"/>
							
							<kendo:dataSource-schema-model-field name="customerStatus" type="String"/>
						
							</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
			</kendo:dataSource>				

</kendo:grid>
<div id="alertsBox" title="Alert"></div>
<script>

function CustomerPreOrderEvent(e){
	
	 $('div[data-container-for="cpid"]').remove();
	 $('label[for="cpid"]').closest('.k-edit-label').remove()
	 
	if (e.model.isNew()) 
	{
		$(".k-window-title").text("Add Item Master Details");
		$(".k-grid-update").text("Save");		
	}
	else{
		$(".k-window-title").text("Edit Item Master Details");
	}
	
}
</script> --%>