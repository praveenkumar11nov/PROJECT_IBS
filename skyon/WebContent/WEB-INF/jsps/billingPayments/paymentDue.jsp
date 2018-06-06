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
             <kendo:grid-column title="Customer  &nbsp; *" filterable="false" field="personName" width="130px">
        </kendo:grid-column>
        
          <kendo:grid-column title="Account No &nbsp; *" filterable="false" field="accountNo" width="130px">
        </kendo:grid-column>
        <kendo:grid-column title="Service Type"    field="typeOfService" filterable="false" width="110px"/>
         <kendo:grid-column title="Due Amount"    field="dueAmount" filterable="false" width="110px"/>  
        
        
                <%-- <kendo:grid-column title="Payment Gateway"    field="paymentgatway" filterable="false" width="110px"/> --%>
                
                
                
                
                <!-- 3rd report -->
                
				 
				<!-- 4th report --> 			 
   				
  				
  				          
							
  				<!-- 5th report --> 
           	 	
           	 	 
           	 	 			
           	 	 	 
  				<!-- 6th report --> 
           	 		
                 
           	 	 	
  				<!-- 7th report --> 
  				
  				<!--Staff Biometric Report --> 
  				
  				
           	 	 
           	 	 	 	
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
    function searchByMonth() {
		var month = $('#monthpicker').val();
		//alert(":::data::::"+"${data}");
		//alert(":::month::::"+month);
		if("${data}"==11){
		 $.ajax({
			type : "POST",
			url : "./paymentDue/readIncomeTrackerData/" + month+ "/" + "${data}",
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
    var monthValue="";
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
            	
            	
            	$(document).ready(function(){
            		monthValue= $('#monthpicker').val();
               
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
						
						
							/* ========== 10th report ========== */
							
							/* grid.hideColumn("billDate");
							grid.hideColumn("billdueDate");
							
							grid.hideColumn("staffCategory");
							grid.hideColumn("staffName");
							grid.hideColumn("sasDateput");
							grid.hideColumn("timeIn");
							grid.hideColumn("timeOut");
							grid.hideColumn("departmentName");
							grid.hideColumn("desigName"); */

							/* ========== 4th report ========== */
							
							
							/* ========== 5th report ========== */
							
							/* ========== 6th report ========== */
							
           	 	  
							/* ========== 7th report ========== */
							
							
						}
						if ("${data}" == 2)//primary contacts
						{
							
						
						

							/* ========== 3rd report ========== */
							

							/* ========== 4th report ========== */
							
							
							/* ========== 5th report ========== */
							
           	 	 
							/* ========== 6th report ========== */
							
							/* ========== 7th report ========== */
							

						}
						if ("${data}" == 3)//primary contacts
						{
							
							
						

							
							/* ========== 4th report ========== */
							
							
							/* ========== 5th report ========== */
							
           	
							/* ========== 6th report ========== */
						
           	 	  
							/* ========== 7th report ========== */
							

						}
						if ("${data}" == 4)//primary contacts
						{
							
							

							/* ========== 3rd report ========== */
							

							/* ========== 5th report ========== */
							
           	 	 
							/* ========== 6th report ========== */
							
           	 	  
							/* ========== 7th report ========== */
							

						}
						if ("${data}" == 5)//primary contacts
						{
							

							/* ========== 3rd report ========== */
							//grid.hideColumn("month");
						

							/* ========== 4th report ========== */
							
					
							/* ========== 6th report ========== */
							
           	 	  
							/* ========== 7th report ========== */
							

						}
						if ("${data}" == 6)//primary contacts
						{
							

							/* ========== 3rd report ========== */
							
							
							
							/* ========== 4th report ========== */
							
							/* ========== 5th report ========== */
							
           	 	 
							/* ========== 7th report ========== */
							

						}
						if ("${data}" == 7)//primary contacts
						{

						}
						
						if ("${data}" == 45)//Staff Biometric Logs
						{

							
							
           	 	  
						}
						
						
						if ("${data}" == 10)//primary contacts
						{

						
           	 	  
						}
						
					})

					
            
            
           			 if("${data}" == 11){
           				$("#grid").on(
    							"click",
    							".k-grid-ownerTemplatesDetailsExport",
    							function(e) {
    								monthValue= $('#monthpicker').val();	
    								window.open("./paymentDue/exportExcel/"+ "${data}"+"/"+monthValue);
    							}); 
           			 }
				</script>     
 	