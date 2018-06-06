<%@include file="/common/taglibs.jsp"%>	
			 
	
			

<c:url value="/incomeTracker/readIncomeTrackerData" var="readUr" />


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
            
      
      </div>
      
      
      </kendo:grid-toolbarTemplate>
        <kendo:grid-columns>
           <kendo:grid-column title="Property No. &nbsp; *" field="property_No" width="110px">
         
           
           </kendo:grid-column>
             <kendo:grid-column title="Customer  &nbsp; *" filterable="false" field="personName" width="130px">
        </kendo:grid-column>
        
          <kendo:grid-column title="Account No &nbsp; *" filterable="false" field="accountNo" width="130px">
        </kendo:grid-column>
        
             <%-- <kendo:grid-column title="Flat No &nbsp; *" filterable="false" field="flatNo" width="130px">
        </kendo:grid-column> --%>
        
        <kendo:grid-column title="Block"    field="blockName" filterable="false" width="110px"/>
                
                <kendo:grid-column title="Receipt No"    field="receiptNo" filterable="false" width="110px"/>
                
                <kendo:grid-column title="Trancation No"    field="instrumentNo" filterable="false" width="110px"/>
        
        
                <%-- <kendo:grid-column title="Payment Gateway"    field="paymentgatway" filterable="false" width="110px"/> --%>
                
                <kendo:grid-column title="Amount"    field="paymentAmount" filterable="false" width="110px"/>
                <kendo:grid-column title="Payment Mode"    field="paymentMode" filterable="false" width="110px"/>
                 <kendo:grid-column title="Payment Date"    field="instrumentDate" format="{0:dd/MM/yyyy }" filterable="false" width="110px"/>
                
                <!-- 3rd report -->
                
				 
				<!-- 4th report --> 			 
   				
  				
  				          
							
  				<!-- 5th report -->
  				 <kendo:grid-column title="Type Of Service" field="typeOfService" filterable="false" width="150px"/>	  
           	 	 <kendo:grid-column title="Bill Amount" field="billAmount" filterable="true" width="150px"/>	 
           	 	 <kendo:grid-column title="Arrears Amount" field="arrearsAmount" filterable="false" width="150px"/>           	 	
           	 	 <kendo:grid-column title="Total Amount" field="netAmount" filterable="false" width="150px"/>	 
           	 	
           	 	 
           	 	 			
           	 	 	 
  				<!-- 6th report --> 
           	 	  
           	 	 	
  				<!-- 7th report --> 
           	 	 <kendo:grid-column title="Billed Amount" field="billedAmount" filterable="true" width="110px"/>	 
           	 	<%--  <kendo:grid-column title="Collection Amount" field="collectionAmount" filterable="true" width="110px"/> --%>	 
           	 	  <kendo:grid-column title="Bank Name" field="bankName" filterable="false" width="150px"/>
           	 	 
           	 	 <!-- 8th report --> 
           	 	 <kendo:grid-column title="Refund  Amount" field="refundAmount" filterable="true" width="110px"/>	 
           	 	<%--  <kendo:grid-column title="Collection Amount" field="collectionAmount" filterable="true" width="110px"/> --%>	 
           	 	 <kendo:grid-column title="Total Amount" field="totalAmount" filterable="true" width="110px"/>	 
           	 	 
           	 		 	
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
                        <kendo:dataSource-schema-model-field name="instrumentDate" type="date"/>	
                        
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
						
						//paymentMap.put("refundAmount", (Double)values[7]);
						//paymentMap.put("totalAmount", (Double)values[4]); 
						
						grid.hideColumn("refundAmount");
						grid.hideColumn("totalAmount");
							/* ========== 3rd report ========== */
							
							grid.hideColumn("typeOfService");
							grid.hideColumn("billAmount");
							grid.hideColumn("arrearsAmount");
							grid.hideColumn("netAmount");

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
							
							grid.hideColumn("collectionLoss");
							grid.hideColumn("atcLoss");
							grid.hideColumn("bankName");
						}
						if ("${data}" == 2)//primary contacts
						{
							/* grid.hideColumn("jan");
							grid.hideColumn("feb");
							grid.hideColumn("march");
							grid.hideColumn("april");
							grid.hideColumn("may");
							grid.hideColumn("june");
							grid.hideColumn("july");
							grid.hideColumn("august");
							grid.hideColumn("september");
							grid.hideColumn("october");
							grid.hideColumn("november");
							grid.hideColumn("december"); */
							grid.hideColumn("bankName");
							grid.hideColumn("refundAmount");
							grid.hideColumn("totalAmount");
							
							/* ========== 3rd report ========== */
							grid.hideColumn("month");
							grid.hideColumn("head1");
							grid.hideColumn("head2");
							grid.hideColumn("head3");
							grid.hideColumn("head4");
							grid.hideColumn("head5");
							
							grid.hideColumn("typeOfService");
							grid.hideColumn("billAmount");
							grid.hideColumn("arrearsAmount");
							grid.hideColumn("netAmount");

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
							grid.hideColumn("bankName");
						}
						if ("${data}" == 3)//primary contacts
						{
							
							grid.hideColumn("refundAmount");
							grid.hideColumn("totalAmount");
							
							grid.hideColumn("typeOfService");
							grid.hideColumn("billAmount");
							grid.hideColumn("arrearsAmount");
							grid.hideColumn("netAmount");
							
							
							grid.hideColumn("jan");
							grid.hideColumn("feb");
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
							grid.hideColumn("bankName");
						}
						if ("${data}" == 4)//primary contacts
						{
							grid.hideColumn("refundAmount");
							grid.hideColumn("totalAmount");
							
							grid.hideColumn("jan");
							grid.hideColumn("feb");
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
							
							
							grid.hideColumn("typeOfService");
							grid.hideColumn("billAmount");
							grid.hideColumn("arrearsAmount");
							grid.hideColumn("netAmount");

							/* ========== 3rd report ========== */
							grid.hideColumn("month");
							grid.hideColumn("head1");
							grid.hideColumn("head2");
							grid.hideColumn("head3");
							grid.hideColumn("head4");
							grid.hideColumn("head5");

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
							grid.hideColumn("paymentAmount");
							//grid.hideColumn("collectionAmount");
							grid.hideColumn("netAmount");
							grid.hideColumn("typeOfServic");
							grid.hideColumn("arrearsAmount");
						}
						if ("${data}" == 5)//primary contacts
						{
							grid.hideColumn("refundAmount");
							grid.hideColumn("totalAmount");
							grid.hideColumn("receiptNo");
							grid.hideColumn("jan");
							grid.hideColumn("feb");
							grid.hideColumn("march");
							grid.hideColumn("april");
							grid.hideColumn("may");
							grid.hideColumn("june");
							grid.hideColumn("july");
							grid.hideColumn("august");
							
							grid.hideColumn("instrumentNo");
							grid.hideColumn("paymentAmount");
							grid.hideColumn("paymentMode");
							grid.hideColumn("december");

							/* ========== 3rd report ========== */
							//grid.hideColumn("month");
							grid.hideColumn("head1");
							grid.hideColumn("head2");
							grid.hideColumn("head3");
							grid.hideColumn("head4");
							grid.hideColumn("head5");

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
							grid.hideColumn("bankName");
						}
						if ("${data}" == 6)//primary contacts
						{
							grid.hideColumn("refundAmount");
							grid.hideColumn("totalAmount");
							
							grid.hideColumn("typeOfService");
							grid.hideColumn("billAmount");
							grid.hideColumn("arrearsAmount");
							grid.hideColumn("netAmount");
							
							grid.hideColumn("name");
							grid.hideColumn("blockName");
							grid.hideColumn("flatNo");
							grid.hideColumn("jan");
							grid.hideColumn("feb");
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

							/* ========== 3rd report ========== */
							grid.hideColumn("month");
							grid.hideColumn("head1");
							grid.hideColumn("head2");
							grid.hideColumn("head3");
							grid.hideColumn("head4");
							grid.hideColumn("head5");
							
							
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
							grid.hideColumn("bankName");
						}
						if ("${data}" == 7)//primary contacts
						{
							grid.hideColumn("refundAmount");
							grid.hideColumn("totalAmount");
							
							
							grid.hideColumn("typeOfService");
							grid.hideColumn("billAmount");
							grid.hideColumn("arrearsAmount");
							grid.hideColumn("netAmount");

							/* ========== 3rd report ========== */
							grid.hideColumn("month");
							grid.hideColumn("head1");
							grid.hideColumn("head2");
							grid.hideColumn("head3");
							grid.hideColumn("head4");
							grid.hideColumn("head5");

							/* ========== 1st report ========== */
							grid.hideColumn("name");
							grid.hideColumn("blockName");
							grid.hideColumn("flatNo");
							grid.hideColumn("jan");
							grid.hideColumn("feb");
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
           	 	 
							/* ========== 6th report ========== */
						 grid.hideColumn("tower");
							/* grid.hideColumn("totalFlats");
							grid.hideColumn("mainMeterReading");
							grid.hideColumn("otherMeterReading");
							grid.hideColumn("unitLoss");
							grid.hideColumn("loss");  */
				           grid.hideColumn("bankName");
						}
						if ("${data}" == 8)//primary contacts
						{
							
							grid.hideColumn("typeOfService");
							grid.hideColumn("billAmount");
							grid.hideColumn("arrearsAmount");
							grid.hideColumn("netAmount");

							/* ========== 3rd report ========== */
							grid.hideColumn("month");
							grid.hideColumn("billedAmount");
							grid.hideColumn("receiptNo");
							grid.hideColumn("paymentAmount");
							grid.hideColumn("head4");
							grid.hideColumn("head5");

							/* ========== 1st report ========== */
							grid.hideColumn("name");
							grid.hideColumn("blockName");
							grid.hideColumn("flatNo");
							grid.hideColumn("jan");
							grid.hideColumn("feb");
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
           	 	 
							/* ========== 6th report ========== */
						 grid.hideColumn("tower");
							/* grid.hideColumn("totalFlats");
							grid.hideColumn("mainMeterReading");
							grid.hideColumn("otherMeterReading");
							grid.hideColumn("unitLoss");
							grid.hideColumn("loss");  */
				           
						}
						if ("${data}" == 9)//primary contacts
						{
							grid.hideColumn("refundAmount");
							grid.hideColumn("totalAmount");
							grid.hideColumn("property_No");
							grid.hideColumn("accountNo");
							grid.hideColumn("billedAmount");
							
							
							
							grid.hideColumn("typeOfService");
							grid.hideColumn("billAmount");
							grid.hideColumn("arrearsAmount");
							grid.hideColumn("netAmount");

							/* ========== 3rd report ========== */
							
							//grid.hideColumn("head3");
							//grid.hideColumn("head4");
							//grid.hideColumn("head5");

							/* ========== 1st report ========== */
							grid.hideColumn("name");
							grid.hideColumn("blockName");
							grid.hideColumn("flatNo");
							grid.hideColumn("jan");
							grid.hideColumn("feb");
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
           	 	 
							/* ========== 6th report ========== */
						 grid.hideColumn("tower");
						 
           	 	  
						}

					})

					$("#grid").on(
							"click",
							".k-grid-ownerTemplatesDetailsExport",
							function(e) {
								window.open("./incomeTracker/exportExcel/"
										+ "${data}");
							});
				</script>     
 	