<%@include file="/common/taglibs.jsp"%>

	<!-- Urls for Common controller  -->
	<c:url value="/common/getAllChecks" var="allChecksUrl" />
	<c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />
	<c:url value="/common/getFilterAutoCompleteValues" var="filterAutoCompleteUrl" />
	<c:url value="/account/getPersonListForFileter" var="personNamesFilterUrl" />
	
	<!-- Urls for Account  -->
	<c:url value="/account/readPerson" var="readAccountPersonUrl" />
	
	<c:url value="/account/read" var="readAccountUrl" />
	<c:url value="/account/modify" var="modifyAccountUrl" />
	
	<c:url value="/account/categories" var="categoriesReadUrlAccount" />
	<c:url value="/account/getAccountPersonListBasedOnPersonType" var="accountPersonNamesAutoBasedOnPersonTypeUrl" />
	
	<!-- ---------------------------------------------- Grid ------------------------------------- -->
	
	<kendo:grid name="gridPersonAccount" edit="accountPersonEdit" detailTemplate="accountTemplate" change="onChangeAccount" pageable="true" resizable="true" 
	sortable="true" reorderable="true" selectable="true" scrollable="true" groupable="true" filterable="true" navigatable="true">
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10" input="true" numeric="true" refresh="true" info="true" previousNext="true">
			<kendo:grid-pageable-messages itemsPerPage="Persons per page" empty="No Person to display" refresh="Refresh all the Persons" 
			display="{0} - {1} of {2} Persons" first="Go to the first page of Persons" last="Go to the last page of Persons" next="Go to the next page of Persons"
			previous="Go to the previous page of Persons"/>
		</kendo:grid-pageable> 
		<kendo:grid-filterable extra="false">
			<kendo:grid-filterable-operators>
				<kendo:grid-filterable-operators-string eq="Is equal to" contains="Contains"/>
			</kendo:grid-filterable-operators>
		</kendo:grid-filterable>
		<kendo:grid-editable mode="popup"/>
<%-- 		<kendo:grid-toolbar>
			<kendo:grid-toolbarItem name="create" text="Add Account" />
			<kendo:grid-toolbarItem text="Clear_Filter"/>
		</kendo:grid-toolbar> --%>
		<kendo:grid-toolbarTemplate>
			<div class="toolbar">
    			<label class="category-label" for="categoriesAccount">&nbsp;&nbsp;Select&nbsp;the&nbsp;Person&nbsp;Type&nbsp;to&nbsp;Add&nbsp;Account&nbsp;for&nbsp;a&nbsp;new&nbsp;Person:&nbsp;&nbsp;</label>
	    		<%-- <kendo:dropDownList name="categoriesAccount" optionLabel="Select" dataTextField="personType" 
	    				dataValueField="personType" autoBind="false" change="categoriesChangeAccount">
	    			<kendo:dataSource>
		    			<kendo:dataSource-transport>            	
			                <kendo:dataSource-transport-read url="${categoriesReadUrlAccount}" />                
			            </kendo:dataSource-transport>
	    			</kendo:dataSource>    			   			    			
	    		</kendo:dropDownList> --%>

				<!-- <a class="k-button k-button-icontext k-grid-add" href="#">
		            <span class="k-icon k-add"></span>
		            Add Account
	        	</a> -->
	        	
	        	<a class='k-button' href='\\#' onclick="clearFilterAccount()"><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>
			</div>  	
    	</kendo:grid-toolbarTemplate>
		<kendo:grid-columns>
			
			<kendo:grid-column title="Image" field="image" template ="<img src='./person/getpersonimage/#=personId#' style='border-radius:6px;' id='myImages_#=personId#' alt='No Image to Display' width='80px' height='80px'/>"
				filterable="false" width="80px" sortable="false"/>
			
			<kendo:grid-column title="Person&nbsp;Type" field="personType" width="80px">
			<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function personTypeAccountFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter person type",
									dataSource : {
										transport : {
											read : "${filterDropDownUrl}/personType"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
			<kendo:grid-column title="Category" field="personStyle" width="80px">
			   	<kendo:grid-column-values value="${personStyle}"/>
			</kendo:grid-column>
			<kendo:grid-column title="Name" field="personName" width="80px">
				<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function personNameFilter(element) 
						   	{
								element.kendoComboBox({
									autoBind : false,
									dataTextField : "personName",
									dataValueField : "personName", 
									placeholder : "Enter name",
									headerTemplate : '<div class="dropdown-header">'
										+ '<span class="k-widget k-header">Photo</span>'
										+ '<span class="k-widget k-header">Contact info</span>'
										+ '</div>',
									template : '<table><tr><td rowspan=2><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.personId#'/>" width=40 height=55 alt=\"No Image to Display\" /></span></td>'
										+ '<td align="left"><span class="k-state-default"><b>#: data.personName #</b></span><br>'
										+ '<span class="k-state-default"><i>#: data.personStyle #</i></span><br>'
										+ '<span class="k-state-default"><i>#: data.personType #</i></span></td></tr></table>',
									dataSource : {
										transport : {		
											read :  "${personNamesFilterUrl}"
										}
									} 
								});
						   	}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>	
			<kendo:grid-column title="personId" field="personId" editor="personEditorAccount" width="0px" hidden="true">
            </kendo:grid-column>
            
            <kendo:grid-column title="Account&nbsp;Numbers&nbsp;" field="allAccountNos" width="100px">
           		<kendo:grid-column-filterable>
					<kendo:grid-column-filterable-ui>
						<script type="text/javascript">
							function allAccountNosFilter(element) {
								element.kendoAutoComplete({
									placeholder : "Enter account number",
									dataSource : {
										transport : {
											read : "${filterAutoCompleteUrl}/Account/accountNo"
										}
									}
								});
							}
						</script>
					</kendo:grid-column-filterable-ui>
				</kendo:grid-column-filterable>
			</kendo:grid-column>
            <kendo:grid-column title="No&nbsp;of&nbsp;Accounts&nbsp;" field="noOfAccounts" width="80px"/>
            <kendo:grid-column title="Account&nbsp;Number&nbsp;*" field="accountNo" width="0px" hidden="true"/>
			<kendo:grid-column title="Account&nbsp;Type&nbsp;*" field="accountType" width="0px" hidden="true">
				<kendo:grid-column-values value="${accountType}"/>
			</kendo:grid-column>
			
        </kendo:grid-columns>
		<kendo:dataSource requestStart="onRequestStartPersonAccount" requestEnd="onRequestEndPersonAccount">
			<kendo:dataSource-transport>
				<kendo:dataSource-transport-read url="${readAccountPersonUrl}" dataType="json" type="POST" contentType="application/json" />
				<kendo:dataSource-transport-create url="${modifyAccountUrl}/create" dataType="json" type="GET" contentType="application/json" />
			</kendo:dataSource-transport>
			<%-- <kendo:dataSource-sort>
            	<kendo:dataSource-sortItem field="accountId" dir="desc"></kendo:dataSource-sortItem>
         	</kendo:dataSource-sort> --%>
			<kendo:dataSource-schema parse="parsePersonAccount">
				<kendo:dataSource-schema-model id="accountId">
					<kendo:dataSource-schema-model-fields>
						<kendo:dataSource-schema-model-field name="accountId" type="number"/> 
						<kendo:dataSource-schema-model-field name="personType" type="string"/>
						<kendo:dataSource-schema-model-field name="personStyle" type="string"/>
						<kendo:dataSource-schema-model-field name="personName" type="string"/>
						<kendo:dataSource-schema-model-field name="personId" /> 
						<kendo:dataSource-schema-model-field name="allAccountNos" type="string"/>
						<kendo:dataSource-schema-model-field name="noOfAccounts" type="number"/>
						<kendo:dataSource-schema-model-field name="accountStatusAllCheck" type="string"/>
						<kendo:dataSource-schema-model-field name="accountNo" type="string">
							<kendo:dataSource-schema-model-field-validation required="true"/>
						</kendo:dataSource-schema-model-field>
						<kendo:dataSource-schema-model-field name="accountType" defaultValue="Services"/>
						<kendo:dataSource-schema-model-field name="accountStatus" defaultValue="Inactive" type="string"/>
					</kendo:dataSource-schema-model-fields>
				</kendo:dataSource-schema-model>
			</kendo:dataSource-schema>
		</kendo:dataSource>
	</kendo:grid>
	
	<kendo:grid-detailTemplate id="accountTemplate">
		<kendo:tabStrip name="tabStrip_#=personId#">
			<kendo:tabStrip-items>
			<kendo:tabStrip-item text="Accounts" selected="true">
                <kendo:tabStrip-item-content>
                    <div class="wethear">
                       <kendo:grid name="gridAccount_#=personId#" pageable="true" resizable="true" edit="accountEdit" dataBound="dataBoundAccount" sortable="true" reorderable="true" selectable="true" scrollable="true" >
						<kendo:grid-pageable pageSize="5"  >
								<kendo:grid-pageable-messages itemsPerPage="Accounts per page" empty="No Account to display" refresh="Refresh all the Accounts" 
									display="{0} - {1} of {2} Accounts" first="Go to the first page of Accounts" last="Go to the last page of Accounts" next="Go to the next page of Accounts"
									previous="Go to the previous page of Accounts"/>
						</kendo:grid-pageable>
						<kendo:grid-editable mode="popup"/>
					        <%-- <kendo:grid-toolbar >
					            <kendo:grid-toolbarItem name="create"  text="Add another Account" />				       
					        </kendo:grid-toolbar> --%>
					        <kendo:grid-columns>			
					       		<kendo:grid-column title="Account&nbsp;Number&nbsp;*" field="accountNo" filterable="false" width="100px" />
								<kendo:grid-column title="Account&nbsp;Type&nbsp;*" field="accountType" filterable="false" width="100px" >
									<kendo:grid-column-values value="${accountType}"/>
								</kendo:grid-column>
								<kendo:grid-column title="Status" field="accountStatus" filterable="false" width="60px"/>

								<kendo:grid-column title="&nbsp;" width="60px" >
							             	<!-- Status updation purpose -->
							    </kendo:grid-column>
							    
					        </kendo:grid-columns>
					        <kendo:dataSource requestStart="onRequestStartPersonAccount" requestEnd="onRequestEndPersonAccount">
					            <kendo:dataSource-transport>
					                <kendo:dataSource-transport-read url="${readAccountUrl}/#=personId#" dataType="json" type="POST" contentType="application/json"/>
					                <kendo:dataSource-transport-create url="${modifyAccountUrl}/create/#=personId#" dataType="json" type="GET" contentType="application/json" />
					            </kendo:dataSource-transport>
					            <kendo:dataSource-schema>
					            	<kendo:dataSource-schema-model id="accountId">
										<kendo:dataSource-schema-model-fields>
											<kendo:dataSource-schema-model-field name="accountId" type="number"/> 
											<kendo:dataSource-schema-model-field name="personId" /> 
											<kendo:dataSource-schema-model-field name="accountNo" type="string">
												<kendo:dataSource-schema-model-field-validation required="true"/>
											</kendo:dataSource-schema-model-field>
											<kendo:dataSource-schema-model-field name="accountType" defaultValue="Sample1"/>
											<kendo:dataSource-schema-model-field name="accountStatus" defaultValue="Inactive" type="string"/>
										</kendo:dataSource-schema-model-fields>
									</kendo:dataSource-schema-model>
					            </kendo:dataSource-schema>
					        </kendo:dataSource>
					 </kendo:grid> 
					 </div>
                </kendo:tabStrip-item-content>
            </kendo:tabStrip-item>

			</kendo:tabStrip-items>
		</kendo:tabStrip>
	</kendo:grid-detailTemplate>
	
	<div id="alertsBox" title="Alert"></div>

<!-- ------------------------------------------ Account Person Script  ------------------------------------------ -->
	
	<script type="text/javascript">
	var SelectedPersonTypeAccount = "Select";
	var accountNoArray = [];
	
	$( document ).ready(function() {
		$('.k-grid-add').hide();
	});
	
  	$(".k-grid-toolbar").delegate(".k-grid-add", "click", function(e) {
	    e.preventDefault();
	    grid.addRow();
	}); 

	 function categoriesChangeAccount() {	
		 var value = this.value(),
		 	 grid = $("#gridPersonAccount").data("kendoGrid");

		 if (value != "Select") {
	    	 $('.k-grid-add').show();
	     } else {
	    	$('.k-grid-add').hide();
	     }
		 SelectedPersonTypeAccount = value;
	}
	 
	 function clearFilterAccount()
		{
			  $("form.k-filter-menu button[type='reset']").slice().trigger("click");
			  var gridPersonAccount = $("#gridPersonAccount").data("kendoGrid");
			  gridPersonAccount.dataSource.read();
			  gridPersonAccount.refresh();
		}
	
	var SelectedRowId = "";
	function onChangeAccount(arg) {
		 var gview = $("#gridPersonAccount").data("kendoGrid");
	 	 var selectedItem = gview.dataItem(gview.select());
	 	 SelectedRowId = selectedItem.personId;
	 	// this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
	     //alert("Selected: " + SelectedRowId);
	     
	}
	
	function accountPersonEdit(e)
	{
		$('div[data-container-for="image"]').remove();
		$('label[for="image"]').closest('.k-edit-label').remove();
		
		$('div[data-container-for="personType"]').remove();
		$('label[for="personType"]').closest('.k-edit-label').remove();
		$('div[data-container-for="personStyle"]').remove();
		$('label[for="personStyle"]').closest('.k-edit-label').remove();
		
		$('div[data-container-for="allAccountNos"]').remove();
		$('label[for="allAccountNos"]').closest('.k-edit-label').remove();
		$('div[data-container-for="noOfAccounts"]').remove();
		$('label[for="noOfAccounts"]').closest('.k-edit-label').remove();
		
		$(".k-edit-field").each(function () {
			$(this).find("#temPID").parent().remove();
	   	});
		
		$('div[data-container-for="accountStatus"]').remove();
		$('label[for="accountStatus"]').closest('.k-edit-label').remove();
		
		$('.k-edit-field .k-input').first().focus();
		
		var grid = this;
		e.container.on("keydown", function(e) {        
	        if (e.keyCode == kendo.keys.ENTER) {
	          $(document.activeElement).blur();
	          grid.saveRow();
	        }
	      });
		
		if (e.model.isNew()) 
	    {
			if(SelectedPersonTypeAccount == "Owner")
			{
				$(".k-window-title").text("Add New Account Record for Owner");
				$('label[for="personId"]').text("Owner *");
				
			}
			else
			{
				$(".k-window-title").text("Add New Account Record for Tenant");
				$('label[for="personId"]').text("Tenant *");
			}	
			
/* 			accountNoArray = [];
			
			$.ajax({
				type : "GET",
				url : "${filterAutoCompleteUrl}/Account/accountNo",
				dataType : "JSON",
				success : function(response) {
					$.each(response, function(index, value) {
						accountNoArray.push(value);
					});
				}
			}); */
			
			$('div[data-container-for="personName"]').remove();
			$('label[for="personName"]').closest('.k-edit-label').remove();
			
			$(".k-grid-update").text("Save");
	    }
		else
		{
			/* var accountNo = $('input[name="accountNo"]').val();
			
			$.each(accountNoArray, function(idx1, elem1) {
				if(elem1 == accountNo)
				{
					accountNoArray.splice(idx1, 1);
				}	
			});
			
			$('div[data-container-for="personId"]').remove();
			$('label[for="personId"]').closest('.k-edit-label').remove();
			
			$('[name="personName"]').attr("disabled", true);
			
			$(".k-window-title").text("Edit Account Details"); */
		}
		
		//CLIENT SIDE VALIDATION FOR MULTI SELECT
 		$(".k-grid-update").click(function () 
		{
 			
		}); 
	}
	
	function parsePersonAccount (response) {   
	    var data = response; //<--data might be in response.data!!!
	    accountNoArray = [];
		 for (var idx = 0, len = data.length; idx < len; idx ++) {
			var res = [];
			res = (data[idx].personType).split(',');
			data[idx].personType = res.sort().toString();
			
			var res1 = [];
			res1 = (data[idx].allAccountNos).split(',');
			data[idx].allAccountNos = res1.sort().toString();
			
			var res4 =(data[idx].allAccountNos).split(',');
			$.each(res4, function(idx1, elem1) 
			{
				accountNoArray.push(elem1);
			});
			//accountNoArray.push(data[idx].allAccountNos);
		 }
		 
		 //alert(accountNoArray.length+" ----- "+accountNoArray);
		 return response;
	}
	
	function personEditorAccount(container, options) 
   	{
		if (options.model.personId == '')
		{
			if(SelectedPersonTypeAccount == "Owner")
			{
				$('<input name="Owner" id="personId" data-text-field="personName" data-value-field="personId" data-bind="value:' + options.field + '" required="true"/>')
				.appendTo(container).kendoComboBox({
					autoBind : false,
					placeholder : "Select Owner",
					headerTemplate : '<div class="dropdown-header">'
						+ '<span class="k-widget k-header">Photo</span>'
						+ '<span class="k-widget k-header">Contact info</span>'
						+ '</div>',
					template : '<table><tr><td rowspan=2><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.personId#'/>" width=40 height=55 alt=\"No Image to Display\" /></span></td>'
						+ '<td align="left"><span class="k-state-default"><b>#: data.personName #</b></span><br>'
						+ '<span class="k-state-default"><i>#: data.personStyle #</i></span><br>'
						+ '<span class="k-state-default"><i>#: data.personType #</i></span></td></tr></table>',
					dataSource : {
						transport : {		
							read :  "${accountPersonNamesAutoBasedOnPersonTypeUrl}/Owner"
						}
					},
					change : function (e) {
			            if (this.value() && this.selectedIndex == -1) {                    
							alert("Owner doesn't exist or already has an account!");
			                $("#personId").data("kendoComboBox").value("");
			        	}
				    } 
				});
				
				$('<span class="k-invalid-msg" data-for="Owner"></span>').appendTo(container); 
			}
			
			else
			{
				$('<input name="Tenant" id="personId" data-text-field="personName" data-value-field="personId" data-bind="value:' + options.field + '" required="true"/>')
				.appendTo(container).kendoComboBox({
					autoBind : false,
					placeholder : "Select Tenant",
					headerTemplate : '<div class="dropdown-header">'
						+ '<span class="k-widget k-header">Photo</span>'
						+ '<span class="k-widget k-header">Contact info</span>'
						+ '</div>',
					template : '<table><tr><td rowspan=2><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.personId#'/>" width=40 height=55 alt=\"No Image to Display\" /></span></td>'
						+ '<td align="center"><span class="k-state-default"><b>#: data.personName #</b></span><br>'
						+ '<span class="k-state-default"><i>#: data.personStyle #</i></span><br>'
						+ '<span class="k-state-default"><i>#: data.personType #</i></span></td></tr></table>',
					dataSource : {
						transport : {		
							read :  "${accountPersonNamesAutoBasedOnPersonTypeUrl}/Tenant"
						}
					},
					change : function (e) {
			            if (this.value() && this.selectedIndex == -1) {                    
							alert("Tenant doesn't exist or already has an account!");
			                $("#personId").data("kendoComboBox").value("");
			        	}
				    } 
				});
				
				$('<span class="k-invalid-msg" data-for="Owner"></span>').appendTo(container); 
			}	
			
		}
/* 		else
		{
			 $('<input type="text" disabled="disabled" class="k-input k-textbox" id ="personId" name=' + options.field + ' data-bind="value:' + options.field + '" required="true"/>')
		      .appendTo(container);
		}	 */
	   	 
   	}
	
	/* function personEditorAccountAuto(e) 
	{
		var dataItem = this.dataItem(e.item.index());
		var personId = dataItem.personId;

		var firstItem = $('#gridPersonAccount').data().kendoGrid.dataSource.data()[0];
		firstItem.set('personId', 1234);
		alert(personId +" --------- "+JSON.stringify(firstItem));
		
	} */
		
	
	/* function dataBoundPersonAccount(e) 
	{
		var grid = $("#gridPersonAccount").data("kendoGrid");
	    var gridData = grid._data;
	    
	    var i = 0;
		
	 	this.tbody.find("tr td:last-child").each(function (e) 
	   	{
	 		var status = gridData[i].accountStatusAllCheck;
	  	  	
	 		if(status == 'deactiveAll')
		   	{
	 			var quote_str =  "'activateAll'";
		   		$('<button id="test1" class="k-button k-button-icontext" onclick="accountAllStatusClick('+quote_str+')">Activate All</button>').appendTo(this);
		   		
		   	}
	 		else if(status == 'activeAll')
	 		{
	 			var quote_str =  "'deactivateAll'";
		   		$('<button id="test2" class="k-button k-button-icontext" onclick="accountAllStatusClick('+quote_str+')">De-activate All</button>').appendTo(this);
		   	}	
	 		else
	 		{
	 			var activateAll =  "'activateAll'";
		   		$('<button id="test1" class="k-button k-button-icontext" onclick="accountAllStatusClick('+activateAll+')">Activate All</button>').appendTo(this);
		   		var deactivateAll =  "'deactivateAll'";
		   		$('<button id="test2" class="k-button k-button-icontext" onclick="accountAllStatusClick('+deactivateAll+')">De-activate All</button>').appendTo(this);
	 		}	
		   	i++;
	   	});
	} */
	
	function accountAllStatusClick(operation)
	{
		var personId="";
		var gridAccount = $("#gridPersonAccount").data("kendoGrid");
		var selectedAddressItem = gridAccount.dataItem(gridAccount.select());
		personId = selectedAddressItem.personId;
		
		$.ajax
		({
			type : "POST",
			url : "./account/accountStatus/" + personId + "/"+operation,
			dataType:"text",
			success : function(response) 
			{
				$("#alertsBox").html("");
				$("#alertsBox").html(response);
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
				$('#gridPersonAccount').data('kendoGrid').dataSource.read();
			}
		});
	}

	function onRequestStartPersonAccount(e)
	{
		var gridPersonAccount = $("#gridPersonAccount").data("kendoGrid");
		gridPersonAccount.cancelRow();
	}
	
	function onRequestEndPersonAccount(e) 
	{
		displayMessage(e, "gridPersonAccount", "Account");
	}	
	

// ------------------------------------------ Account Script  ------------------------------------------ 

	function accountEdit(e)
	{
		
		$('div[data-container-for="accountStatus"]').remove();
		$('label[for="accountStatus"]').closest('.k-edit-label').remove();
		
		$('.k-edit-field .k-input').first().focus();
		
		var grid = this;
		e.container.on("keydown", function(e) {        
	        if (e.keyCode == kendo.keys.ENTER) {
	          $(document.activeElement).blur();
	          grid.saveRow();
	        }
	      });
		
		if (e.model.isNew()) 
	    {
			
			$(".k-window-title").text("Add New Account for the existing Person");
			
			accountNoArray = [];
			
			$.ajax({
				type : "GET",
				url : "${filterAutoCompleteUrl}/Account/accountNo",
				dataType : "JSON",
				success : function(response) {
					$.each(response, function(index, value) {
						accountNoArray.push(value);
					});
				}
			});
			
			$(".k-grid-update").text("Save");
	    }
		else
		{
			/* var accountNo = $('input[name="accountNo"]').val();
			
			$.each(accountNoArray, function(idx1, elem1) {
				if(elem1 == accountNo)
				{
					accountNoArray.splice(idx1, 1);
				}	
			});
			
			$('div[data-container-for="personId"]').remove();
			$('label[for="personId"]').closest('.k-edit-label').remove();
			
			$('[name="personName"]').attr("disabled", true);
			
			$(".k-window-title").text("Edit Account Details"); */
		}
		
		//CLIENT SIDE VALIDATION FOR MULTI SELECT
 		$(".k-grid-update").click(function () 
		{
 			
		}); 
	}
	
/* 	function parseAccount (response) {  
		
		var grid = $("#gridAccount_"+SelectedRowId).data("kendoGrid");
	    var data = response; //<--data might be in response.data!!!
		 for (var idx = 0, len = data.length; idx < len; idx ++) {
			//res = (data[idx].personType).split(',');
			
			
	        var rowSelector = ">tr:nth-child(" + (idx + 1) + ")";
	        var row = grid.tbody.find(rowSelector);
	        
	        var astatus = data[idx].accountStatus;
	        alert(astatus);
	        alert(JSON.stringify(row));
			
			alert(JSON.stringify(data[idx]));
			//data[idx].personType = res.sort().toString();
			
		 }
		 return response;
	} 
	
	function accountDB(e) {
		
		var grid = $("#gridAccount_"+SelectedRowId).data("kendoGrid");
	    var gridData = grid._data;
	    for (var i = 0; i < gridData.length; i++) {
	        var status = gridData[i].accountStatus;
	        var rowSelector = ">tr:nth-child(" + (i + 1) + ")";
	        var row = grid.tbody.find(rowSelector).find('td[role="gridcell"]');
	       alert(status+"--------"+JSON.stringify(row));
	    }
	}*/
	
	function dataBoundAccount(e) 
	{
	    
	  	var vgrid = $('#gridAccount_'+SelectedRowId).data("kendoGrid");
	  	var items = vgrid.dataSource.data();
	 	var i = 0;
	 	this.tbody.find("tr td:last-child").each(function (e) 
	   	{
	  	  	var item = items[i];
		   	if(item.accountStatus == 'Active')
		   	{
		   		$("<button id='test' class='k-button k-button-icontext' onclick='accountStatusClick()'>Inactivate</button>").appendTo(this);
		   	}
		   	else
		   	{
		   		$("<button id='test' class='k-button k-button-icontext' onclick='accountStatusClick()'>Activate</button>").appendTo(this);
		   	}	
		   	i++;
	   	});
	}
	
	function accountStatusClick()
	{
		var accountId="";
		var gridAccount = $("#gridAccount_"+SelectedRowId).data("kendoGrid");
		var selectedAddressItem = gridAccount.dataItem(gridAccount.select());
		accountId = selectedAddressItem.accountId;
		var result=securityCheckForActionsForStatus("./Accounts/Account/activeInactiveButton");	  
		if(result=="success"){ 
		$.ajax
		({
			type : "POST",
			url : "./account/accountStatusUpdateFromInnerGrid/"+accountId,
			dataType:"text",
			success : function(response) 
			{
				$("#alertsBox").html("");
				$("#alertsBox").html(response);
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
				$('#gridAccount_'+SelectedRowId).data('kendoGrid').dataSource.read();
			}
		});
		}
	}
	
/* 	function onRequestStartAccount(e)
	{
		var gridPersonAccount = $("#gridAccount_"+SelectedRowId).data("kendoGrid");
		if(gridPersonAccount != null)
		{
			gridPersonAccount.cancelRow();
		}
	}
	
	function onRequestEndAccount(e) 
	{
		displayMessage(e, "gridAccount_"+SelectedRowId, "Account");
	}	 */

// ------------------------------------------ Common Script  ------------------------------------------ 

		//register custom validation rules
	(function($, kendo) {$.extend(true,
						kendo.ui.validator,
						{
							rules : { // custom rules          	         	
								accountNovalidation : function(input, params){
									if (input.filter("[name='accountNo']").length && input.val()) 
									{
										return /^[a-zA-Z0-9]{1,50}$/.test(input.val());
									}
									return true;
								},
								accountNoUniquevalidation : function(input, params){
									if (input.filter("[name='accountNo']").length && input.val()) 
									{
										var flag = true;
										$.each(accountNoArray, function(idx1, elem1) {
											if(elem1 == input.val())
											{
												flag = false;
											}	
										});
										return flag;
									}
									return true;
								}
							},
							messages : {
								//custom rules messages
								accountNovalidation : "Account number can contain alphanumeric characters but cannot allow other special characters and maximum 50 characters are allowed",
								accountNoUniquevalidation : "Account number already exists"
							}
						});

	})(jQuery, kendo);
	//End Of Validation

</script>
<!-- ------------------------------------------ Style  ------------------------------------------ -->

	<style>
	td {
    	vertical-align: middle;
	}
	</style>
