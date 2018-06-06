<%@include file="/common/taglibs.jsp"%>	
			 
	
			

<%-- <c:url value="/bill/readAllBillData" var="readUr" /> --%>


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
             <a id="a1" style="display:none;"><input id='monthpicker' style='width:162px' /></a>
      			<a id="a2" style="display:none;" class="k-button k-button-icontext k-grid-searchBymonth" onclick="searchByMonth()"  href="#"  >
                <!-- <span class=" "></span> -->
                 Search By Month
                </a> 
      
      
      </div>
      
      
      </kendo:grid-toolbarTemplate>
        <kendo:grid-columns>
           <kendo:grid-column title="Property No. &nbsp; *" field="propertyno" width="180px">
         
           
           </kendo:grid-column>
             <kendo:grid-column title="Customer Name &nbsp; *" field="personName" width="180px">
        </kendo:grid-column>
        
          <kendo:grid-column title="Account No &nbsp; *" field="accountNo" width="180px">
        </kendo:grid-column>
        <kendo:grid-column title="Service Type &nbsp; *" filterable="false" field="serviceType" width="250px">
        </kendo:grid-column>
        
              <kendo:grid-column title="Billed Amount &nbsp; *" filterable="false" field="billAmount" width="250px">
        </kendo:grid-column>
        <kendo:grid-column title="Consumption &nbsp; *" filterable="false" field="consumption" width="250px">
        </kendo:grid-column>   
        
        <%--  <kendo:grid-column title="Jan-15"    field="jan" filterable="false" width="110px"/>
                
                <kendo:grid-column title="Feb-15"    field="feb" filterable="false" width="110px"/>
                
                <kendo:grid-column title="March-15"    field="march" filterable="false" width="110px"/>
        
        
                <kendo:grid-column title="April-15"    field="april" filterable="false" width="110px"/>
                
                <kendo:grid-column title="May-15"    field="may" filterable="false" width="110px"/>
                <kendo:grid-column title="June-15"    field="june" filterable="false" width="110px"/>
                <kendo:grid-column title="July-15"    field="july" filterable="false" width="110px"/>
                <kendo:grid-column title="August-15"    field="august" filterable="false" width="110px"/>
                <kendo:grid-column title="September-15"    field="september" filterable="false" width="110px"/>
                <kendo:grid-column title="October-15"    field="october" filterable="false" width="110px"/>
                <kendo:grid-column title="November-15"    field="november" filterable="false" width="110px"/>
                <kendo:grid-column title="December-15"    field="december" filterable="false" width="110px"/>
                 --%>
                <!-- 3rd report -->
                <kendo:grid-column title="Month" field="month" filterable="false" width="110px"/>
                <kendo:grid-column title="Energy Charge" field="head1" filterable="false" width="150px"/>
                <kendo:grid-column title="DG Charge" field="head2" filterable="false" width="150px"/>
                <kendo:grid-column title="Late Payment Charge" field="head3" filterable="false" width="150px"/>
                <kendo:grid-column title="Arrear" field="head4" filterable="false" width="150px"/>
                <kendo:grid-column title="Round Off" field="head5" filterable="false" width="150px"/>
				 
				<!-- 4th report --> 			 
   				<%-- <kendo:grid-column title="Meter Sr No"    field="meterSrNo" filterable="true" width="110px"/>
  				<kendo:grid-column title="Previous Billed Utility Unit" field="prevBillUnit" filterable="false" width="150px"/>
 				<kendo:grid-column title="Present Billed Utility Unit" field="presentBillUnit" filterable="false" width="150px"/>
 				<kendo:grid-column title="Previous Billed DG Unit" field="prevBilldgUnit" filterable="false" width="150px"/>
 				<kendo:grid-column title="Present Billed DG Unit" field="presentBilldgUnit" filterable="false" width="150px"/>
 				<kendo:grid-column title="Previous Billed  Date" field="prevBillDate" filterable="false" width="150px"/>
  				<kendo:grid-column title="Present Billed Date" field="presentBillDate" filterable="false" width="150px"/> --%>
  				
  				          
							
  				<!-- 5th report --> 
           	 	 <%-- <kendo:grid-column title="Month Billed Amount" field="monthBilledAmount" filterable="true" width="150px"/>	 
           	 	 <kendo:grid-column title="Collection Amount" field="collectionAmount" filterable="false" width="150px"/>	 
           	 	 <kendo:grid-column title="Interest" field="interest" filterable="false" width="150px"/>	 
           	 	 <kendo:grid-column title="Arrears" field="arrearsTax" filterable="false" width="150px"/>	 
           	 	 <kendo:grid-column title="Service Tax" field="vat" filterable="false" width="150px"/> --%>
           	 	 
           	 	 			
           	 	 	 
  				<!-- 6th report --> 
           	 	 <%-- <kendo:grid-column title="Tower" field="tower" filterable="true" width="110px"/>	 
           	 	 <kendo:grid-column title="Total Flats" field="totalFlats" filterable="true" width="110px"/>	 
           	 	 <kendo:grid-column title="Main Meter Reading" field="mainMeterReading" filterable="true" width="150px"/>	 
           	 	 <kendo:grid-column title="Others Meter Reading" field="otherMeterReading" filterable="true" width="140px"/>	 
           	 	 <kendo:grid-column title="Unit Loss" field="unitLoss" filterable="true" width="110px"/>	 
           	 	 <kendo:grid-column title="% Loss" field="loss" filterable="true" width="110px"/>	 
           	 	 	 --%>
  				<!-- 7th report --> 
           	 	 <%-- <kendo:grid-column title="Billed Amount" field="billedAmount" filterable="true" width="110px"/>	 
           	 	 <kendo:grid-column title="Collection Amount" field="collectionAmount" filterable="true" width="110px"/>	 
           	 	 <kendo:grid-column title="Collection Loss" field="collectionLoss" filterable="true" width="110px"/>	 
           	 	 <kendo:grid-column title="AT&C Loss" field="atcLoss" filterable="true" width="110px"/>	  --%>
           	 	 	 	
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
			url : "./bill/readAllBillData2/" + month+ "/" + "${data}",
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			success : function(result) {
				/* alert(result.size); */
				 var grid = $("#grid").getKendoGrid();
				var data = new kendo.data.DataSource();
				grid.dataSource.data(result);
				grid.refresh(); 
			}
		}); 
		 
		/*  if("${data}" == 45){
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
		} */
		 
		/*  if("${data}" == 47){
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
		} */
	/* 	 if("${data}" == 50){
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
		 } */
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
					  // defines when the calendar should return date0
					  depth: "year",
					  /* value:new Date(), */
					  // display month and year in the input
					  format: "MMMM yyyy"
					}); 
            	/* $("#menu1thirdlevel").remove();
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
            	$("#menu22thirdlevel").remove(); */
            	var grid = $("#grid").data("kendoGrid");
            	
            	
					if ("${data}" == 1)//primary contacts
						{
						$("#a1").show();
						$("#a2").show();
							/* ========== 3rd report ========== */
						    grid.hideColumn("month");
							grid.hideColumn("head1");
							grid.hideColumn("head2");
							grid.hideColumn("head3");
							grid.hideColumn("head4");
							grid.hideColumn("head5"); 

							/* ========== 4th report ========== */
							/* grid.hideColumn("meterSrNo");
							grid.hideColumn("prevBillUnit");
							grid.hideColumn("presentBillUnit");
							grid.hideColumn("prevBillDate");
							grid.hideColumn("presentBillDate");
							grid.hideColumn("prevBilldgUnit");
							grid.hideColumn("presentBilldgUnit"); */
							
							/* ========== 5th report ========== */
						/* 	grid.hideColumn("monthBilledAmount");
							grid.hideColumn("collectionAmount");
							grid.hideColumn("interest");
							grid.hideColumn("arrearsTax");
							grid.hideColumn("vat"); */
           	 	 
							/* ========== 6th report ========== */
							/* grid.hideColumn("tower");
							grid.hideColumn("totalFlats");
							grid.hideColumn("mainMeterReading");
							grid.hideColumn("otherMeterReading");
							grid.hideColumn("unitLoss");
							grid.hideColumn("loss"); */
           	 	  
							/* ========== 7th report ========== */
							/* grid.hideColumn("billedAmount");
							
							grid.hideColumn("collectionLoss");
							grid.hideColumn("atcLoss"); */
							
							
							/* document.getElementByID(a1)style.visibility = 'visible';
							document.getElementByID(a2)style.visibility = 'visible'; */
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
							grid.hideColumn("consumption");
							
						}
						if ("${data}" == 2)//primary contacts
						{
							$("#a1").show();
							$("#a2").show();
							grid.hideColumn("billAmount"); 
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

							/* ========== 3rd report ========== */
							grid.hideColumn("month");
							grid.hideColumn("head1");
							grid.hideColumn("head2");
							grid.hideColumn("head3");
							grid.hideColumn("head4");
							grid.hideColumn("head5");

							/* ========== 4th report ========== */
							/* grid.hideColumn("meterSrNo");
							grid.hideColumn("prevBillUnit");
							grid.hideColumn("presentBillUnit");
							grid.hideColumn("prevBillDate");
							grid.hideColumn("presentBillDate");
							grid.hideColumn("prevBilldgUnit");
							grid.hideColumn("presentBilldgUnit"); */
							
							/* ========== 5th report ========== */
							/* grid.hideColumn("monthBilledAmount");
							grid.hideColumn("collectionAmount");
							grid.hideColumn("interest");
							grid.hideColumn("arrearsTax");
							grid.hideColumn("vat"); */
           	 	 
							/* ========== 6th report ========== */
						/* 	grid.hideColumn("tower");
							grid.hideColumn("totalFlats");
							grid.hideColumn("mainMeterReading");
							grid.hideColumn("otherMeterReading");
							grid.hideColumn("unitLoss");
							grid.hideColumn("loss"); */
           	 	  
							/* ========== 7th report ========== */
							/* grid.hideColumn("billedAmount");
							grid.hideColumn("collectionAmount");
							grid.hideColumn("collectionLoss");
							grid.hideColumn("atcLoss"); */

						}
						if ("${data}" == 3)//primary contacts
						{
							$("#a1").show();
							$("#a2").show();
							/* grid.hideColumn("billAmount"); */
							
							grid.hideColumn("serviceType");
							grid.hideColumn("billAmount"); 
							grid.hideColumn("consumption");
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
							
							
							/* ========== 4th report ========== */
							/* grid.hideColumn("meterSrNo");
							grid.hideColumn("prevBillUnit");
							grid.hideColumn("presentBillUnit");
							grid.hideColumn("prevBillDate");
							grid.hideColumn("presentBillDate");
							grid.hideColumn("prevBilldgUnit");
							grid.hideColumn("presentBilldgUnit"); */
							/* ========== 5th report ========== */
							/* grid.hideColumn("monthBilledAmount");
							grid.hideColumn("collectionAmount");
							grid.hideColumn("interest");
							grid.hideColumn("arrearsTax");
							grid.hideColumn("vat"); */
           	
							/* ========== 6th report ========== */
							/* grid.hideColumn("tower");
							grid.hideColumn("totalFlats");
							grid.hideColumn("mainMeterReading");
							grid.hideColumn("otherMeterReading");
							grid.hideColumn("unitLoss");
							grid.hideColumn("loss"); */
           	 	  
							/* ========== 7th report ========== */
							/* grid.hideColumn("billedAmount");
							grid.hideColumn("collectionAmount");
							grid.hideColumn("collectionLoss");
							grid.hideColumn("atcLoss"); */

						}
						if ("${data}" == 4)//primary contacts
						{
							/* grid.hideColumn("billAmount"); */
							
							
							grid.hideColumn("serviceType");
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

						}
						if ("${data}" == 5)//primary contacts
						{
							/* grid.hideColumn("billAmount"); */
							
							
							grid.hideColumn("serviceType");
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
							grid.hideColumn("vat"); // deepak singh

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
							grid.hideColumn("prevBilldgUnit");
							grid.hideColumn("presentBilldgUnit");
					
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

						}
						if ("${data}" == 6)//primary contacts
						{
							/* grid.hideColumn("billAmount"); */
							
							
							grid.hideColumn("serviceType");
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

						}
						if ("${data}" == 7)//primary contacts
						{
							/* grid.hideColumn("billAmount"); */

							/* ========== 3rd report ========== */
							grid.hideColumn("serviceType");
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
							/* grid.hideColumn("tower");
							grid.hideColumn("totalFlats");
							grid.hideColumn("mainMeterReading");
							grid.hideColumn("otherMeterReading");
							grid.hideColumn("unitLoss");
							grid.hideColumn("loss"); */
           	 	  
						}

					})
					
					$("#grid").on(
							"click",
							".k-grid-ownerTemplatesDetailsExport",
							function(e) {
								var month = $('#monthpicker').val();
								 if("${data}" == 1 ||"${data}" == 2||"${data}" == 3 ){
									 
								if(month!=null && month!="" && month!="undefined"){
									
								window.open("./billAll/exportExcel/"+ month+ "/"
										+ "${data}");
								}
								else
									{
									alert("Select SearchByMonth then do Export to Excel");
									}
								}
							});
					 
				</script>     
 	