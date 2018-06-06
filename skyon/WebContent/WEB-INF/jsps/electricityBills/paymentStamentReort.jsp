<%@include file="/common/taglibs.jsp"%>	
			 
	
			

<c:url value="/bill/readAllBillData" var="readUr" />


<div id="loading" ></div>
	<kendo:grid name="grid" pageable="true"
		filterable="true" groupable="true" selectable="true" scrollable="true" reorderable="true" resizable="true" >
		<kendo:grid-pageable pageSizes="true" buttonCount="5"  pageSize="20" input="true" numeric="true" refresh="true" ></kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
		 <kendo:grid-filterable-operators>
		  	<kendo:grid-filterable-operators-string eq="Is equal to"/>
		 </kendo:grid-filterable-operators>
			
		</kendo:grid-filterable>


    
    	 <kendo:grid-editable mode="popup" confirmation="Are you sure you want to remove this item?"/>
    	 
       <kendo:grid-toolbarTemplate>
      
         <div class="toolbar">
	        	<a class='k-button' href='\\#' onclick="clearFilterOwner()"><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>
	        	
	        	<a class="k-button k-button-icontext k-grid-generateReport" href="#">
                <span class=" "></span>
                Generate Report
                </a>
	        	<a class="k-button k-button-icontext k-grid-ownerTemplatesDetailsExport" href="#">
                <span class=" "></span>
                 Export To Excel
                </a>
                
                <a class="k-button k-button-icontext k-grid-ownerTemplatesDetailsPdfExport" href="#">
                <span class=" "></span>
                 Export To Pdf
                </a>
      </div>
      
      
      </kendo:grid-toolbarTemplate>
	<kendo:grid-columns>
		<kendo:grid-column title="Property No. &nbsp; *" field="property_No"
			width="110px">
		</kendo:grid-column>
		<kendo:grid-column title="Customer Name &nbsp; *" filterable="false"
			field="personName" width="130px">
		</kendo:grid-column>
		<kendo:grid-column title="Account No &nbsp; *" filterable="false"
			field="accountNo" width="130px">
		</kendo:grid-column>
		<kendo:grid-column title="Service Type &nbsp; *" filterable="false"
			field="ledgerType" width="130px">
		</kendo:grid-column>
		<kendo:grid-column title="Post Type &nbsp; *" filterable="false"
			field="postType" width="130px">
		</kendo:grid-column>
		<kendo:grid-column title="Month" field="ledgerDate" filterable="false"
			width="110px" />
		<kendo:grid-column title="Amount"
			field="amount" filterable="true" width="150px" />
		<kendo:grid-column title="Balance" field="balance"
			filterable="false" width="150px" />


	</kendo:grid-columns>
	<kendo:dataSource  requestStart="requestStart" requestEnd="requestEnd">
            <kendo:dataSource-transport>
            
                <kendo:dataSource-transport-read url="${readUr}/${data}" dataType="json" type="POST" contentType="application/json"/>
             
            </kendo:dataSource-transport>
            <kendo:dataSource-schema parse="parse">
                <kendo:dataSource-schema-model id="dept_Id">
                 <kendo:dataSource-schema-model-fields>
                        <kendo:dataSource-schema-model-field name="dept_Id" editable="false">
                        </kendo:dataSource-schema-model-field>
                        <kendo:dataSource-schema-model-field name="dept_Name" type="string"/>
                        	
                        
                        <kendo:dataSource-schema-model-field name="dept_Desc" type="string">
                        </kendo:dataSource-schema-model-field>
                         
                         <kendo:dataSource-schema-model-field name="dept_Status"   type="string"/>
                         <kendo:dataSource-schema-model-field name="jan" type="number" defaultValue="0"/>
                      
                   
                        
                    </kendo:dataSource-schema-model-fields>
                </kendo:dataSource-schema-model>
            </kendo:dataSource-schema>
        </kendo:dataSource>
    </kendo:grid>
    <div id="alertsBox" title="Alert"></div>

<div id="printBillingDiv" style="display: none;">
	<form id="printBillingForm" data-role="validator" novalidate="novalidate">
		<table style="height: 190px;">
		
		
		   
		  <tr>
				<td>Account Name</td>
				<td><input id="accountNoprint" name="accountNoprint" 	
					validationMessage="Select Account No." />
			</tr>
		  
			
			 <tr >
				<td>From Month</td>
				<td><kendo:datePicker format="MMM yyyy  "
						name="fromdateprintBill" id="fromdateprintBill"  value="${month}" start="year"
						 depth="year">
					</kendo:datePicker>
				<td></td>
			</tr>

			<tr>
				<td> Month</td>
				<td><kendo:datePicker format="MMM yyyy  "
						name="presentdateprintBill" id="presentdateprintBill" required="required" value="${month}" start="year"
						class="validate[required]" validationMessage="Date is Required" depth="year">
					</kendo:datePicker>
				<td></td>
			</tr>
            
             
			<tr>
				<td class="left" align="center" colspan="4">                    
					<button type="submit" id="printBill" class="k-button"
						style="padding-left: 10px">Submit</button>
					<span id=printplaceholder style="display: none;"><img
						src="./resources/images/loadingimg.GIF"
						style="vertical-align: middle; margin-left: 50px" /> &nbsp;&nbsp;
						<img src="./resources/images/loading.GIF" alt="loading"
						style="vertical-align: middle margin-left: 50px" height="20px"
						width="200px; " /></span>
				</td>
			</tr>

		</table>
	</form>
</div>

  
    <script type="text/javascript">
    
    function requestStart()
    {
    	
    	kendo.ui.progress($("#loading"), true);

    	
    }
    
    function requestEnd()
    {
    	kendo.ui.progress($("#loading"), false);
    	
    }
    
    function parse (response) {
        $.each(response, function (idx, elem) {   
        	   if(elem.vStd=== null){
        		   elem.vStd = "";
        	   }else{
        		   elem.vStd = kendo.parseDate(new Date(elem.vStd),'dd/MM/yyyy');
        	   }
        	   
        	   if(elem.vEnd=== null){
        		   elem.vEnd = "";
        	   }else{
        		   elem.vEnd = kendo.parseDate(new Date(elem.vEnd),'dd/MM/yyyy');
        	   }
        	   if(elem.visitorInDate=== null){
        		   elem.visitorInDate = "";
        	   }else{
        		   elem.visitorInDate = kendo.parseDate(new Date(elem.visitorInDate),'dd/MM/yyyy');
        	   }
           });
           
           return response;
    }
    $("#printBillingForm").submit(function(e) {
		e.preventDefault();
	});
    var printbillvalidator = $("#printBillingForm").kendoValidator().data("kendoValidator");
	$("#printBill").on("click", function() {

		if (printbillvalidator.validate()) {

			printAllBills();
		}
	});
	var accountNo="";
	var presentdate="";
	var fromMonth="";
	function printAllBills() {
		$("#printBill").hide();
		$("#printplaceholder").show();
	
		 presentdate = $("#presentdateprintBill").val();     
	      accountNo=$("#accountNoprint").val();
	     
	      fromMonth=$("#fromdateprintBill").val();	
	   var typetoPrint=  $("#typePrint").val();  
	     
		$.ajax({
			//url : "./bill/generateAmrBill",
			url : "./bill/generateAllLedgerData",
			type : "POST",
			dataType : "json",
			data : {
				
				presentdate : presentdate,
				accountNo : accountNo,
				fromMonth : fromMonth,
			},
			success : function(response) {
				$("#printBill").show();
				$("#printplaceholder").hide();
				
				 var grid = $("#grid").getKendoGrid();
					var data = new kendo.data.DataSource();
					grid.dataSource.data(response);
					grid.refresh(); 
					//closeAmr();			

			}
		});
	}

	 function clearFilterOwner()
	 {
	 	    //custom actions
	 	    $("form.k-filter-menu button[type='reset']").slice().trigger("click");
	 		var grid = $("#grid").data("kendoGrid");
	 		grid.dataSource.read();
	 		grid.refresh();
	 	}
	 
		    	
            $(document).ready(function(){
            	$("#menu1thirdlevel").remove();
            	$("#menu2thirdlevel").remove();
            	$("#menu3thirdlevel").remove();
            	$("#menu4thirdlevel").remove();
            	$("#menu5thirdlevel").remove();
            	$("#menu6thirdlevel").remove();
            	$("#menu7thirdlevel").remove();
            	$("#menu8thirdlevel").remove();
            	$("#menu9thirdlevel").remove();
            	$("#menu10thirdlevel").remove();
            	$("#menu12thirdlevel").remove();
            	$("#menu13thirdlevel").remove();
            	$("#menu14thirdlevel").remove();
            	$("#menu15thirdlevel").remove();
            	$("#menu16thirdlevel").remove();
            	$("#menu17thirdlevel").remove();
            	$("#menu18thirdlevel").remove();
            	$("#menu19thirdlevel").remove();
            	$("#menu20thirdlevel").remove();
            	$("#menu21thirdlevel").remove();
            	$("#menu22thirdlevel").remove();
            	var grid = $("#grid").data("kendoGrid");
            	
            	
            	$("#accountNoprint")
				.kendoComboBox(
						{
							filter : "startswith",
							autoBind : false,
							dataTextField : "accountNumber",
							dataValueField : "accountId",
							placeholder : "Select accountno...",
							headerTemplate : '<div class="dropdown-header">'
									+ '<span class="k-widget k-header">Photo</span>'
									+ '<span class="k-widget k-header">Contact info</span>'
									+ '</div>',
							template : '<table><tr><td rowspan=2><span class="k-state-default"><img src=\"<c:url value='/person/getpersonimage/#=data.personId#'/>" width=40 height=55 alt=\"No Image to Display\" /></span></td>'
									+ '<td align="left"><span class="k-state-default"><b>#: data.personName #</b></span><br>'
									+ '<span class="k-state-default"><i>#: data.accountNumber #</i></span><br>'
									+ '<span class="k-state-default"><i>#: data.personType #</i></span></td></tr></table>',
							dataSource : {
								transport : {
									read : {

										url : "./bill/accountNumberAutocomplete"
									}
								}
							},
							height : 370,
						}).data("kendoComboBox");
				
					})

					$("#grid").on(
							"click",
							".k-grid-ownerTemplatesDetailsExport",
							function(e) {
								
								
								if(presentdate == "" || presentdate == null || accountNo == "" || accountNo == null){
									
									alert("Select Month And Account No");
								}else{
									window.open("./bill/exportAccontWisePayment/" +accountNo+ "/" + presentdate + "/" + fromMonth);
								}
								
								
								
								
							});
            
            $("#grid").on(
					"click",
					".k-grid-generateReport",
					function(e) {
						
							var bbDialog = $("#printBillingDiv");
							bbDialog.kendoWindow({
								width : "auto",
								height : "auto",
								modal : true,
								draggable : true,
								position : {
									top : 100
								},
								title : "Account Wise Report"
							}).data("kendoWindow").center().open();

							bbDialog.kendoWindow("open");

							/* var dropdownlist2 = $("#blockNameAmr").data("kendoDropDownList");
							dropdownlist2.value("");
							var dropdownlist1 = $("#propertyType").data("kendoDropDownList");
							dropdownlist1.value("");
							
							var presentreading = $("#presentdateAmr");
							presentreading.val("");
							var EmptyArray = new Array();
							var ddlMulti = $('#propertyNameAmr').data('kendoMultiSelect');
							ddlMulti.value(EmptyArray); */
						
					});
            
            $("#grid").on(
					"click",
					".k-grid-ownerTemplatesDetailsPdfExport",
					function(e) {
						
						
						if(presentdate == "" || presentdate == null || accountNo == "" || accountNo == null){
							
							alert("Select Month And Account No");
						}else{
							
							  window.open("./payment/paymentReport/" +accountNo+ "/" + presentdate + "/" + fromMonth);
						}
						
						
						
						
					});
				</script>     
 	