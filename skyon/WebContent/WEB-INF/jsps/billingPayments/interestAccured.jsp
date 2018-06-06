<%@include file="/common/taglibs.jsp"%>	
<%-- <c:url value="/incomeTracker/readIncomeTrackerData" var="readUr" /> --%>
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
	        	
	        	<a class="k-button k-button-icontext k-grid-ownerTemplatesDetailsExport" href="#">
                <span class=" "></span>
                 Export To Excel
                </a>
      			<a><input id='monthpicker' style='width:162px' /></a>
      			<a class="k-button k-button-icontext k-grid-searchBymonth" onclick="searchByMonth()"  href="#">
                <span class=" "></span>
                 Search By Month
                </a>
      </div>
      
      
      </kendo:grid-toolbarTemplate>
        <kendo:grid-columns>
         <kendo:grid-column title="Block"    field="blocks" filterable="false" width="110px"/>
           <kendo:grid-column title="Property No. &nbsp; *" field="propertyNo" width="110px">
         
           
           </kendo:grid-column>
             <kendo:grid-column title="Customer  Name&nbsp; *" filterable="false" field="personName" width="130px">
        </kendo:grid-column>
        
          <kendo:grid-column title="Account No &nbsp; *" filterable="false" field="accountNo" width="130px">
        </kendo:grid-column>
        <kendo:grid-column title="Service Type"    field="typeOfService" filterable="false" width="110px"/>
             <%-- <kendo:grid-column title="Flat No &nbsp; *" filterable="false" field="flatNo" width="130px">
        </kendo:grid-column> --%>
        <kendo:grid-column title="Bill Date"    field="billDate" filterable="false" width="110px"/>
        <kendo:grid-column title="Bill Due Date"    field="billdueDate" filterable="false" width="110px"/>
        <kendo:grid-column title="Principal Amount"    field="billAmount" filterable="false" width="110px"/>
        <kendo:grid-column title="Arrears Amount"    field="arrearsAmount" filterable="false" width="110px"/>
        
        
                <%-- <kendo:grid-column title="Payment Gateway"    field="paymentgatway" filterable="false" width="110px"/> --%>
                
                <kendo:grid-column title="Total Due"    field="netAmount" filterable="false" width="110px"/>
                
                
                <!-- 3rd report -->
                
				 
				<!-- 4th report --> 			 
   				
  				
  				          
							
  				<!-- 5th report --> 
           	 	
           	 	 
           	 	 			
           	 	 	 
  				<!-- 6th report --> 
           	 		<kendo:grid-column title="Bill Amount"    field="totalbillamount" filterable="false" width="110px"/>                
                    <kendo:grid-column title="Payment Amount"    field="paymentAmount" filterable="false" width="110px"/>  
                 <kendo:grid-column title="Due Amount"    field="dueAmount" filterable="false" width="110px"/> 
           	 	 	
  				<!-- 7th report --> 
  				
  				<!--Staff Biometric Report --> 
  				
  				<kendo:grid-column title="Access Card No"    field="accessCardNo" filterable="false" width="110px"/>
           		<kendo:grid-column title="Staff Category" field="staffCategory" width="110px"/>
           		<kendo:grid-column title="Staff Name" field="staffName" width="110px"/>
           		<kendo:grid-column title="Attendance date" field="sasDateput" width="120px"/>
           		<kendo:grid-column title="Check In Time" field="timeIn" width="160px"/>
           		<kendo:grid-column title="Check Out Time" field="timeOut" width="160px"/>
           		<kendo:grid-column title="Department" field="departmentName" width="120px"/>
           		<kendo:grid-column title="Designation" field="desigName" width="120px"/>
           	 	 
           	 	 <!--Audit Report:Account Statement by Flat  -->	
           	 	 <%-- <kendo:grid-column title="Account No"  field="accountNo" filterable="false" width="110px"/> --%>
           		 <kendo:grid-column title="Reciept No" field="receiptNo" width="110px"/>
           	 	 <kendo:grid-column title="Posted Date"  field="receiptDate" filterable="false" width="110px"/>
           		 <kendo:grid-column title="Bill No" field="billNo" width="110px"/>
           		 <kendo:grid-column title="Service Type" field="serviceType" width="110px"/>
           		 <%-- <kendo:grid-column title="Payment Amount" field="paymentAmount" width="110px"/> --%>
           		 <kendo:grid-column title="Payment Mode" field="paymentMode" width="110px"/>
           		 <kendo:grid-column title="Status" field="status" width="110px"/>
           	 	 
           	 	 <!--Resident Receipt  -->
           	 	  <kendo:grid-column title="Block Name" field="blockName" width="110px"/>
           		  <kendo:grid-column title="Flat No" field="flatNo" width="110px"/>
           		  <kendo:grid-column title="Person Name" field="personNames" width="110px"/>
           		  <kendo:grid-column title="Instrument No" field="instructionNo" width="110px"/>
           		  <kendo:grid-column title="Instrument Date" field="instructionDate" width="110px"/>
           		  <kendo:grid-column title="Instrument Bank" field="instructionBank" width="110px"/>
				  <%-- <kendo:grid-column title="Payment Amount"    field="payAmount" filterable="false" width="110px"/>   --%>
							
           	 	  			
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
    <script type="text/javascript">
    var monthValue="";
    function searchByMonth() {
		var month = $('#monthpicker').val();
		monthValue= $('#monthpicker').val();
		/* alert(":::data::::"+"${data}");
		alert(":::month::::"+month); */
		
		 $.ajax({
			type : "POST",
			url : "./interestTracker/readIncomeTrackerData/" + month+ "/" + "${data}",
			dataType : "json",
			success : function(result) {
				 var grid = $("#grid").getKendoGrid();
				var data = new kendo.data.DataSource();
				grid.dataSource.data(result);
				grid.refresh(); 
			}
		}); 
		 
		 if("${data}" == 45){
		 $.ajax({
				type : "POST",
				url : "./staff/satffBiometricLog/" + month+ "/" + "${data}",
				dataType : "json",
				success : function(result) {
					 var grid = $("#grid").getKendoGrid();
					var data = new kendo.data.DataSource();
					grid.dataSource.data(result);
					grid.refresh(); 
				}
		}); 
		}
		 
		 if("${data}" == 47){
		 $.ajax({
				type : "POST",
				url : "./payment/accountStatementByFlat/" + month+ "/" + "${data}",
				dataType : "json",
				success : function(result) {
					 var grid = $("#grid").getKendoGrid();
					var data = new kendo.data.DataSource();
					grid.dataSource.data(result);
					grid.refresh(); 
				}
		}); 
		}
		 if("${data}" == 50){
		 $.ajax({
				type : "POST",
				url : "./resident/residentReceipt/" + month+ "/" + "${data}",
				dataType : "json",
				success : function(result) {
					 var grid = $("#grid").getKendoGrid();
					var data = new kendo.data.DataSource();
					grid.dataSource.data(result);
					grid.refresh(); 
				}
		}); 
		 }
	}
    
    
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

   

	 function clearFilterOwner()
	 {
	 	    //custom actions
	 	    $("form.k-filter-menu button[type='reset']").slice().trigger("click");
	 		var grid = $("#grid").data("kendoGrid");
	 		grid.dataSource.read();
	 		grid.refresh();
	 	}
	 
		    	
            $(document).ready(function(){
            	$("#monthpicker").kendoDatePicker({
					  // defines the start view
					  start: "year",
					  // defines when the calendar should return date
					  depth: "year",
					  value:new Date(),
					  // display month and year in the input
					  format: "MMMM yyyy"
					});
            	
            

            	
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
            	
            	
					if ("${data}" == 1)//primary contacts
						{
						
							grid.hideColumn("totalbillamount");
							grid.hideColumn("dueAmount");
							grid.hideColumn("paymentAmount");
							/* ========== 10th report ========== */
							
							grid.hideColumn("billDate");
							grid.hideColumn("billdueDate");							
							grid.hideColumn("accessCardNo");
							grid.hideColumn("staffCategory");
							grid.hideColumn("staffName");
							grid.hideColumn("sasDateput");
							grid.hideColumn("timeIn");
							grid.hideColumn("timeOut");
							grid.hideColumn("departmentName");
							grid.hideColumn("desigName");

							/* ========== 4th report ========== */
							
							
							/* ========== 5th report ========== */
							
							/* ========== 6th report ========== */
							
           	 	  
							/* ========== 7th report ========== */
							
							
							/*Staff BioLog And Account Statement By Flat  */
							
							grid.hideColumn("accessCardNo");
							grid.hideColumn("staffCategory");
							grid.hideColumn("staffName");
							grid.hideColumn("sasDateput");
							grid.hideColumn("timeIn");
							grid.hideColumn("timeOut");
							grid.hideColumn("departmentName");
							grid.hideColumn("desigName");
							
							grid.hideColumn("accountNo");
							grid.hideColumn("receiptNo");
							grid.hideColumn("receiptDate");
							grid.hideColumn("billNo");
							grid.hideColumn("serviceType");
							grid.hideColumn("status");
							grid.hideColumn("paymentAmount");
							grid.hideColumn("paymentMode");
							
							grid.hideColumn("blockName");
							grid.hideColumn("flatNo");
							grid.hideColumn("personName");
							grid.hideColumn("personNames");
							grid.hideColumn("instructionNo");
							grid.hideColumn("instructionDate");
							grid.hideColumn("instructionBank");
							grid.hideColumn("payAmount");
						}
						if ("${data}" == 2)//primary contacts
						{
							grid.hideColumn("accessCardNo");
							grid.hideColumn("totalbillamount");
							grid.hideColumn("dueAmount");
							grid.hideColumn("paymentAmount");
							
							grid.hideColumn("billDate");
							grid.hideColumn("billdueDate");
						

							/* ========== 3rd report ========== */
							grid.hideColumn("staffCategory");
							grid.hideColumn("staffName");
							grid.hideColumn("sasDateput");
							grid.hideColumn("timeIn");
							grid.hideColumn("timeOut");
							grid.hideColumn("departmentName");
							grid.hideColumn("desigName");

							/* ========== 4th report ========== */
							grid.hideColumn("meterSrNo");
							grid.hideColumn("prevBillUnit");
							grid.hideColumn("presentBillUnit");
							grid.hideColumn("prevBillDate");
							grid.hideColumn("presentBillDate");
							
							/* ========== 5th report ========== */
							grid.hideColumn("monthBilledAmount");
							grid.hideColumn("collectionAmount");
							grid.hideColumn("interest");
							grid.hideColumn("arrearsTax");
							grid.hideColumn("vat");
           	 	 
							/* ========== 6th report ========== */
							grid.hideColumn("tower");
							grid.hideColumn("totalFlats");
							grid.hideColumn("mainMeterReading");
							grid.hideColumn("otherMeterReading");
							grid.hideColumn("unitLoss");
							grid.hideColumn("loss");
           	 	  
							/* ========== 7th report ========== */
							grid.hideColumn("billedAmount");
							//grid.hideColumn("collectionAmount");
							grid.hideColumn("collectionLoss");
							grid.hideColumn("atcLoss");
							
							/*Staff BioLog And Account Statement By Flat  */
							
							grid.hideColumn("accessCardNo");
							grid.hideColumn("staffCategory");
							grid.hideColumn("staffName");
							grid.hideColumn("sasDateput");
							grid.hideColumn("timeIn");
							grid.hideColumn("timeOut");
							grid.hideColumn("departmentName");
							grid.hideColumn("desigName");
							
							
							grid.hideColumn("receiptNo");
							grid.hideColumn("receiptDate");
							grid.hideColumn("billNo");
							grid.hideColumn("serviceType");
							grid.hideColumn("paymentAmount");
							grid.hideColumn("paymentMode");
							grid.hideColumn("status");
							grid.hideColumn("blockName");
							grid.hideColumn("flatNo");
							
							grid.hideColumn("instructionNo");
							grid.hideColumn("instructionDate");
							grid.hideColumn("instructionBank");
							grid.hideColumn("payAmount");
							grid.hideColumn("personNames");


						}
						if ("${data}" == 3)//primary contacts
						{
							grid.hideColumn("totalbillamount");
							grid.hideColumn("dueAmount");
							grid.hideColumn("paymentAmount");
							grid.hideColumn("accessCardNo");
							grid.hideColumn("billDate");
							grid.hideColumn("billdueDate");
							
						

							
							/* ========== 4th report ========== */
							grid.hideColumn("staffCategory");
							grid.hideColumn("staffName");
							grid.hideColumn("sasDateput");
							grid.hideColumn("timeIn");
							grid.hideColumn("timeOut");
							grid.hideColumn("departmentName");
							grid.hideColumn("desigName");
							
							/* ========== 5th report ========== */
							grid.hideColumn("monthBilledAmount");
							grid.hideColumn("collectionAmount");
							grid.hideColumn("interest");
							grid.hideColumn("arrearsTax");
							grid.hideColumn("vat");
           	
							/* ========== 6th report ========== */
						
           	 	  
							/* ========== 7th report ========== */
							grid.hideColumn("billedAmount");
							//grid.hideColumn("collectionAmount");
							grid.hideColumn("collectionLoss");
							grid.hideColumn("atcLoss");
	
							/*Staff BioLog And Account Statement By Flat  */
							
							grid.hideColumn("accessCardNo");
							grid.hideColumn("staffCategory");
							grid.hideColumn("staffName");
							grid.hideColumn("sasDateput");
							grid.hideColumn("timeIn");
							grid.hideColumn("timeOut");
							grid.hideColumn("departmentName");
							grid.hideColumn("desigName");
							
							grid.hideColumn("accountNo");
							grid.hideColumn("receiptNo");
							grid.hideColumn("receiptDate");
							grid.hideColumn("billNo");
							grid.hideColumn("serviceType");
							grid.hideColumn("paymentAmount");
							grid.hideColumn("paymentMode");
							grid.hideColumn("status");	
							
							grid.hideColumn("blockName");
							grid.hideColumn("flatNo");
							grid.hideColumn("personName");
							grid.hideColumn("instructionNo");
							grid.hideColumn("instructionDate");
							grid.hideColumn("instructionBank");
							grid.hideColumn("payAmount");
							grid.hideColumn("personNames");

						}
						if ("${data}" == 4)//primary contacts
						{
							
							grid.hideColumn("totalbillamount");
							grid.hideColumn("dueAmount");
							grid.hideColumn("paymentAmount");
							grid.hideColumn("accessCardNo");
							grid.hideColumn("billDate");
							grid.hideColumn("billdueDate");

							/* ========== 3rd report ========== */
							grid.hideColumn("staffCategory");
							grid.hideColumn("staffName");
							grid.hideColumn("sasDateput");
							grid.hideColumn("timeIn");
							grid.hideColumn("timeOut");
							grid.hideColumn("departmentName");
							grid.hideColumn("desigName");

							/* ========== 5th report ========== */
							grid.hideColumn("monthBilledAmount");
							grid.hideColumn("collectionAmount");
							grid.hideColumn("interest");
							grid.hideColumn("arrearsTax");
							grid.hideColumn("vat");
           	 	 
							/* ========== 6th report ========== */
							grid.hideColumn("tower");
							grid.hideColumn("totalFlats");
							grid.hideColumn("mainMeterReading");
							grid.hideColumn("otherMeterReading");
							grid.hideColumn("unitLoss");
							grid.hideColumn("loss");
           	 	  
							/* ========== 7th report ========== */
							grid.hideColumn("billedAmount");
							//grid.hideColumn("collectionAmount");
							grid.hideColumn("collectionLoss");
							grid.hideColumn("atcLoss");

							/*Staff BioLog And Account Statement By Flat  */
							
							grid.hideColumn("accessCardNo");
							grid.hideColumn("staffCategory");
							grid.hideColumn("staffName");
							grid.hideColumn("sasDateput");
							grid.hideColumn("timeIn");
							grid.hideColumn("timeOut");
							grid.hideColumn("departmentName");
							grid.hideColumn("desigName");
							
							grid.hideColumn("accountNo");
							grid.hideColumn("receiptNo");
							grid.hideColumn("receiptDate");
							grid.hideColumn("billNo");
							grid.hideColumn("serviceType");
							grid.hideColumn("paymentAmount");
							grid.hideColumn("paymentMode");
							grid.hideColumn("status");
							
							grid.hideColumn("blockName");
							grid.hideColumn("flatNo");
							grid.hideColumn("personName");
							grid.hideColumn("instructionNo");
							grid.hideColumn("instructionDate");
							grid.hideColumn("instructionBank");
							grid.hideColumn("payAmount");
							grid.hideColumn("personNames");


						}
						if ("${data}" == 5)//primary contacts
						{
							grid.hideColumn("totalbillamount");
							grid.hideColumn("dueAmount");
							grid.hideColumn("paymentAmount");
							grid.hideColumn("accessCardNo");
							grid.hideColumn("billDate");
							grid.hideColumn("billdueDate");

							/* ========== 3rd report ========== */
							//grid.hideColumn("month");
						grid.hideColumn("staffCategory");
							grid.hideColumn("staffName");
							grid.hideColumn("sasDateput");
							grid.hideColumn("timeIn");
							grid.hideColumn("timeOut");
							grid.hideColumn("departmentName");
							grid.hideColumn("desigName");

							/* ========== 4th report ========== */
							grid.hideColumn("meterSrNo");
							grid.hideColumn("prevBillUnit");
							grid.hideColumn("presentBillUnit");
							grid.hideColumn("prevBillDate");
							grid.hideColumn("presentBillDate");
					
							/* ========== 6th report ========== */
							grid.hideColumn("tower");
							grid.hideColumn("totalFlats");
							grid.hideColumn("mainMeterReading");
							grid.hideColumn("otherMeterReading");
							grid.hideColumn("unitLoss");
							grid.hideColumn("loss");
           	 	  
							/* ========== 7th report ========== */
							grid.hideColumn("billedAmount");
							//grid.hideColumn("collectionAmount");
							grid.hideColumn("collectionLoss");
							grid.hideColumn("atcLoss");

							/*Staff BioLog And Account Statement By Flat  */
							
							grid.hideColumn("accessCardNo");
							grid.hideColumn("staffCategory");
							grid.hideColumn("staffName");
							grid.hideColumn("sasDateput");
							grid.hideColumn("timeIn");
							grid.hideColumn("timeOut");
							grid.hideColumn("departmentName");
							grid.hideColumn("desigName");
							
							grid.hideColumn("accountNo");
							grid.hideColumn("receiptNo");
							grid.hideColumn("receiptDate");
							grid.hideColumn("billNo");
							grid.hideColumn("serviceType");
							grid.hideColumn("paymentAmount");
							grid.hideColumn("paymentMode");
							grid.hideColumn("status");
							
							grid.hideColumn("blockName");
							grid.hideColumn("flatNo");
							grid.hideColumn("personName");
							grid.hideColumn("instructionNo");
							grid.hideColumn("instructionDate");
							grid.hideColumn("instructionBank");
							grid.hideColumn("payAmount");
							grid.hideColumn("personNames");


						}
						if ("${data}" == 6)//primary contacts
						{
							grid.hideColumn("billAmount");
							grid.hideColumn("netAmount");
							grid.hideColumn("arrearsAmount");
							grid.hideColumn("accessCardNo");
							grid.hideColumn("billDate");
							grid.hideColumn("billdueDate");

							/* ========== 3rd report ========== */
							grid.hideColumn("staffCategory");
							grid.hideColumn("staffName");
							grid.hideColumn("sasDateput");
							grid.hideColumn("timeIn");
							grid.hideColumn("timeOut");
							grid.hideColumn("departmentName");
							grid.hideColumn("desigName");
							
							
							/* ========== 4th report ========== */
							grid.hideColumn("meterSrNo");
							grid.hideColumn("prevBillUnit");
							grid.hideColumn("presentBillUnit");
							grid.hideColumn("prevBillDate");
							grid.hideColumn("presentBillDate");
							
							/* ========== 5th report ========== */
							grid.hideColumn("monthBilledAmount");
							//grid.hideColumn("collectionAmount");
							grid.hideColumn("interest");
							grid.hideColumn("arrearsTax");
							grid.hideColumn("vat");
           	 	 
							/* ========== 7th report ========== */
							grid.hideColumn("billedAmount");
							grid.hideColumn("collectionAmount");
							grid.hideColumn("collectionLoss");
							grid.hideColumn("atcLoss");

							/*Staff BioLog And Account Statement By Flat  */
							
							grid.hideColumn("accessCardNo");
							grid.hideColumn("staffCategory");
							grid.hideColumn("staffName");
							grid.hideColumn("sasDateput");
							grid.hideColumn("timeIn");
							grid.hideColumn("timeOut");
							grid.hideColumn("departmentName");
							grid.hideColumn("desigName");
							
							//grid.hideColumn("accountNo");
							grid.hideColumn("receiptNo");
							grid.hideColumn("receiptDate");
							grid.hideColumn("billNo");
							grid.hideColumn("serviceType");
							//grid.hideColumn("paymentAmount");
							grid.hideColumn("paymentMode");
							grid.hideColumn("status");
							
							grid.hideColumn("blockName");
							grid.hideColumn("flatNo");
							//grid.hideColumn("personName");
							grid.hideColumn("instructionNo");
							grid.hideColumn("instructionDate");
							grid.hideColumn("instructionBank");
							grid.hideColumn("payAmount");
							grid.hideColumn("personNames");


						}
						if ("${data}" == 7)//primary contacts
						{

							/* ========== 3rd report ========== */
						//grid.hideColumn("totalbillamount");
						//grid.hideColumn("dueAmount");
						//grid.hideColumn("paymentAmount");
						grid.hideColumn("accessCardNo");
						grid.hideColumn("billDate");
						grid.hideColumn("billdueDate");
						
						
						grid.hideColumn("totalbillamount");
						grid.hideColumn("paymentAmount");
						grid.hideColumn("dueAmount");

							/* ========== 1st report ========== */
							grid.hideColumn("name");
							grid.hideColumn("blockName");
							
							grid.hideColumn("march");
							grid.hideColumn("april");
							grid.hideColumn("may");
							grid.hideColumn("june");
							grid.hideColumn("july");
							grid.hideColumn("august");
							grid.hideColumn("september");
							grid.hideColumn("october");
							grid.hideColumn("november");
							grid.hideColumn("december");
							
							/* ========== 4th report ========== */
							grid.hideColumn("staffCategory");
							grid.hideColumn("staffName");
							grid.hideColumn("sasDateput");
							grid.hideColumn("timeIn");
							grid.hideColumn("timeOut");
							grid.hideColumn("departmentName");
							grid.hideColumn("desigName");
							
							/* ========== 5th report ========== */
							grid.hideColumn("monthBilledAmount");
							//grid.hideColumn("collectionAmount");
							grid.hideColumn("interest");
							grid.hideColumn("arrearsTax");
							grid.hideColumn("vat");
           	 	 
							/* ========== 6th report ========== */
						    grid.hideColumn("tower");
							/* grid.hideColumn("totalFlats");
							grid.hideColumn("mainMeterReading");
							grid.hideColumn("otherMeterReading");
							grid.hideColumn("unitLoss");
							grid.hideColumn("loss");  */
						 
							/*Staff BioLog And Account Statement By Flat  */
							
							grid.hideColumn("accessCardNo");
							grid.hideColumn("staffCategory");
							grid.hideColumn("staffName");
							grid.hideColumn("sasDateput");
							grid.hideColumn("timeIn");
							grid.hideColumn("timeOut");
							grid.hideColumn("departmentName");
							grid.hideColumn("desigName");
							
							//grid.hideColumn("accountNo");
							grid.hideColumn("receiptNo");
							grid.hideColumn("receiptDate");
							grid.hideColumn("billNo");
							grid.hideColumn("serviceType");
							//grid.hideColumn("paymentAmount");
							grid.hideColumn("paymentMode");
							grid.hideColumn("status");
							
							grid.hideColumn("blockName");
							grid.hideColumn("flatNo");
							//grid.hideColumn("personName");
							grid.hideColumn("instructionNo");
							grid.hideColumn("instructionDate");
							grid.hideColumn("instructionBank");
							grid.hideColumn("payAmount");
							grid.hideColumn("personNames");

           	 	  
						}
						
						if ("${data}" == 45)//Staff Biometric Logs
						{

							
							grid.hideColumn("blocks");
							grid.hideColumn("propertyNo");
							grid.hideColumn("personName");
							grid.hideColumn("accountNo");
							grid.hideColumn("typeOfService");
							grid.hideColumn("billAmount");
							grid.hideColumn("accessCardNo"); 
							grid.hideColumn("netAmount");
							grid.hideColumn("arrearsAmount");
							grid.hideColumn("billDate");
							grid.hideColumn("billdueDate");
							grid.hideColumn("blockName");
							grid.hideColumn("flatNo");
							grid.hideColumn("instructionNo");
							grid.hideColumn("instructionDate");
							grid.hideColumn("instructionBank");
							grid.hideColumn("payAmount");
							grid.hideColumn("personNames");
							grid.hideColumn("billNo");
							grid.hideColumn("serviceType");
							grid.hideColumn("status");
							grid.hideColumn("billDate");
							grid.hideColumn("billdueDate");
							grid.hideColumn("paymentAmount");
							grid.hideColumn("receiptNo");
							grid.hideColumn("receiptDate");
							grid.hideColumn("totalbillamount");
							grid.hideColumn("dueAmount");
							grid.hideColumn("paymentMode");

           	 	  
						}
						
						
						if ("${data}" == 10)//primary contacts
						{

							/* ========== 3rd report ========== */
						     grid.hideColumn("billAmount");
							grid.hideColumn("netAmount");
							grid.hideColumn("arrearsAmount");
							grid.hideColumn("accessCardNo");
						

							/* ========== 1st report ========== */
							
							/*Account Statement By Flat  */
						
							//grid.hideColumn("accountNo");
							grid.hideColumn("receiptNo");
							grid.hideColumn("receiptDate");
							grid.hideColumn("billNo");
							grid.hideColumn("serviceType");
							grid.hideColumn("paymentMode");
							grid.hideColumn("status");	
							
							
							
							grid.hideColumn("blockName");
							grid.hideColumn("flatNo");
							//grid.hideColumn("personName");
							grid.hideColumn("instructionNo");
							grid.hideColumn("instructionDate");
							grid.hideColumn("instructionBank");
							grid.hideColumn("payAmount");
							grid.hideColumn("personNames");

							
							/* ========== 4th report ========== */
							grid.hideColumn("staffCategory");
							grid.hideColumn("staffName");
							grid.hideColumn("sasDateput");
							grid.hideColumn("timeIn");
							grid.hideColumn("timeOut");
							grid.hideColumn("departmentName");
							grid.hideColumn("desigName");
							
							/* ========== 5th report ========== */
							
							/* ========== 6th report ========== */
						 grid.hideColumn("tower");
							/* grid.hideColumn("totalFlats");
							grid.hideColumn("mainMeterReading");
							grid.hideColumn("otherMeterReading");
							grid.hideColumn("unitLoss");
							grid.hideColumn("loss");  */
           	 	  
						}
						
						if ("${data}" == 47)//Account Statement By Flat
						{

							
							grid.hideColumn("blocks");
							grid.hideColumn("propertyNo");
							grid.hideColumn("personName");
							grid.hideColumn("typeOfService");
							grid.hideColumn("billAmount");

							grid.hideColumn("netAmount");
							grid.hideColumn("arrearsAmount");
							grid.hideColumn("totalbillamount");
							grid.hideColumn("dueAmount");
							

							/*Staff BioLog */
							
							grid.hideColumn("accessCardNo");
							grid.hideColumn("staffCategory");
							grid.hideColumn("staffName");
							grid.hideColumn("sasDateput");
							grid.hideColumn("timeIn");
							grid.hideColumn("timeOut");
							grid.hideColumn("departmentName");
							grid.hideColumn("desigName");
							
							grid.hideColumn("blockName");
							grid.hideColumn("flatNo");
							grid.hideColumn("personName");
							grid.hideColumn("instructionNo");
							grid.hideColumn("instructionDate");
							grid.hideColumn("instructionBank");
							
							grid.hideColumn("personNames");
							grid.hideColumn("billDate");
							grid.hideColumn("billdueDate");


						
						}
						
						if ("${data}" == 50)//Resident Receipt
						{

							
							grid.hideColumn("blocks");
							grid.hideColumn("propertyNo");
							grid.hideColumn("personName");
							grid.hideColumn("accountNo");
							grid.hideColumn("typeOfService");
							grid.hideColumn("billAmount");

							grid.hideColumn("netAmount");
							grid.hideColumn("arrearsAmount");
							grid.hideColumn("totalbillamount");
							grid.hideColumn("dueAmount");
							grid.hideColumn("paymentAmount");
							

							/*Staff BioLog */
							
							grid.hideColumn("accessCardNo");
							grid.hideColumn("staffCategory");
							grid.hideColumn("staffName");
							grid.hideColumn("sasDateput");
							grid.hideColumn("timeIn");
							grid.hideColumn("timeOut");
							grid.hideColumn("departmentName");
							grid.hideColumn("desigName");
						
							
							grid.hideColumn("billNo");
							grid.hideColumn("serviceType");
							grid.hideColumn("status");
							grid.hideColumn("billDate");
							grid.hideColumn("billdueDate");
						
						}
					})
                    if("${data}" == 2){
                    	
                    	$("#grid").on(
    							"click",
    							".k-grid-ownerTemplatesDetailsExport",
    							function(e) {
    								monthValue= $('#monthpicker').val();    								
    								window.open("./interestTracker/exportExcel/"
    										+ "${data}"+"/"+monthValue);
    							});
                    	
                    }
            
            if("${data}" == 6){
            	$("#grid").on(
						"click",
						".k-grid-ownerTemplatesDetailsExport",
						function(e) {
							monthValue= $('#monthpicker').val();
							window.open(".//interestTracker/exportExcel/"
									+ "${data}"+"/"+monthValue);
						});
            	
            }
            if("${data}" == 7){
            	$("#grid").on(
						"click",
						".k-grid-ownerTemplatesDetailsExport",
						function(e) {
							monthValue= $('#monthpicker').val();
							window.open("./interestTracker/exportExcel/"
									+ "${data}"+"/"+monthValue);
						});
            	
            }	
            
            if("${data}" == 10){
            	$("#grid").on(
						"click",
						".k-grid-ownerTemplatesDetailsExport",
						function(e) {
							monthValue= $('#monthpicker').val();
							window.open("./interestTracker/exportExcel/"
									+ "${data}"+"/"+monthValue);
						});
            	
            }	
            
            
           			 if("${data}" == 45){
           				$("#grid").on(
    							"click",
    							".k-grid-ownerTemplatesDetailsExport",
    							function(e) {
    								if(monthValue!=null && monthValue!="" && monthValue!="undefined"){
    								window.open("./staffBioLog/exportExcel/"+ "${data}"+"/"+monthValue);
    								}
    								else{
     									alert("Select SearchByMonth then do Export to Excel");
     								}
    							}); 
           			 }
           			 
           			 if("${data}" == 47){
            				$("#grid").on(
     							"click",
     							".k-grid-ownerTemplatesDetailsExport",
     							function(e) {
     								
     								if(monthValue!=null && monthValue!="" && monthValue!="undefined"){
     								window.open("./accountStatementByFlat/exportExcel/"+ "${data}"+"/"+monthValue);
     								}
     								else{
     									alert("Select SearchByMonth then do Export to Excel");
     								}
     							}); 
            			 }
           			 
           			 if("${data}" == 50){
         				$("#grid").on(
  							"click",
  							".k-grid-ownerTemplatesDetailsExport",
  							function(e) {
  								
  								if(monthValue!=null && monthValue!="" && monthValue!="undefined"){
  								window.open("./residentReceipt/exportExcel/"+ "${data}"+"/"+monthValue);
  								}
  								else{
  									alert("Select SearchByMonth then do Export to Excel");
  								}
  							}); 
         			 }
           		/* 	$("#grid").on(
							"click",
							".k-grid-ownerTemplatesDetailsExport",
							function(e) {
								window.open("./billAll/exportExcel/"
										+ "${data}");
							}); */
				</script>     
 	