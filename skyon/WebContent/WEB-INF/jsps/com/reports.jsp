


<%@include file="/common/taglibs.jsp"%>	
			 
	
<c:url value="/reports/read" var="readUrl" />
 <div id="name">
	<span>${reportName}</span>
	</div>
 

	<kendo:grid name="grid" pageable="true"
		resizable="true" sortable="true" reorderable="true" selectable="true" scrollable="true"
		filterable="true" groupable="true">
		<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="20" input="true" numeric="true" refresh="true" ></kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
		 <kendo:grid-filterable-operators>
		  	<kendo:grid-filterable-operators-string eq="Is equal to"/>
		 </kendo:grid-filterable-operators>
			
		</kendo:grid-filterable>


    
    	<kendo:grid-editable mode="popup" confirmation="Are you sure you want to remove this item?"/>
        <kendo:grid-toolbar>
          
             <kendo:grid-toolbarItem text="Clear Filter" name="Clear_Filter"/>
        </kendo:grid-toolbar>
        <kendo:grid-columns>
           <kendo:grid-column title="Serial Number &nbsp; *" field="slNo" width="40px">
           </kendo:grid-column>
             <kendo:grid-column title="Reports Name &nbsp; *" field="report" width="120px">
           </kendo:grid-column>
          
     
									<kendo:grid-column title="&nbsp;" width="100px">
										<kendo:grid-column-command>
											<kendo:grid-column-commandItem name="View Report" click="contactInfo"/>
												<kendo:grid-column-commandItem name="Export To Excel" click="contactInfo1"/>
										</kendo:grid-column-command>
									</kendo:grid-column>
        
        </kendo:grid-columns>
        <kendo:dataSource>
            <kendo:dataSource-transport>
           
                <kendo:dataSource-transport-read url="${readUrl}/${unqId}" dataType="json" type="POST" contentType="application/json"/>
             
                <kendo:dataSource-transport-parameterMap>
                	<script>
	                	function parameterMap(options,type) { 	                		
	                		return JSON.stringify(options);	                		
	                	}
                	</script>
                </kendo:dataSource-transport-parameterMap>
            </kendo:dataSource-transport>
            <kendo:dataSource-schema >
                <kendo:dataSource-schema-model id="slNO">
                 <kendo:dataSource-schema-model-fields>
                        <kendo:dataSource-schema-model-field name="slNo" editable="false">
                        </kendo:dataSource-schema-model-field>
                    
                   
              
                        
                        <kendo:dataSource-schema-model-field name="report" type="string">
                        </kendo:dataSource-schema-model-field>
                         
                      
                      
                   
                        
                    </kendo:dataSource-schema-model-fields>
                </kendo:dataSource-schema-model>
            </kendo:dataSource-schema>
        </kendo:dataSource>
    </kendo:grid>
    <div id="alertsBox" title="Alert"></div>   
    <script type="text/javascript">
    function contactInfo()
    {
    	var slNo="";
		var gaddressview = $("#grid").data("kendoGrid");
		var selectedAddressItem = gaddressview.dataItem(gaddressview.select());
		slNo = selectedAddressItem.slNo;
		//alert(slNo);
		if("${unqId}"==1)
	{
			
		if(slNo==7)
			{
			window.location.href="./comfamily";
			}
		
		else if(slNo==11)
			{
			var a=10;
			window.location.href="./getAll?jspId="+a;
			}
		
		else if(slNo==13)
		{
		var a=12;
		window.location.href="./getAll?jspId="+a;
		}
		
		else if(slNo==9)
		{
		var a=1;
		window.location.href="./getAll?jspId="+a;
		}
		else if(slNo==16)
		{
	
		window.location.href="./comowners";
		}
		
		else if(slNo==18)
		{
	
			var a=19;
			window.location.href="./getAll?jspId="+a;
		}
		else if(slNo==19)
		{
	
			var a=30;
			window.location.href="./getAll?jspId="+a;
		}
		
		else{
		
		window.location.href="./getAll?jspId="+slNo;
		}
	}
		
	  if("${unqId}"==11)
	   {
	   if(slNo==2)
	
	      {
		   var a=46;
		   window.location.href="./getAll?jspId="+a;
	
	      }
	   if(slNo==1)
	   {
	   	var a=47;
		window.location.href="./getAllStaffLog?jspId="+a;
	   }
	   if(slNo==3)
	   {
	   	var a=48;
		window.location.href="./getAllOutstandingReports?jspId="+a;
	   } 
	   if(slNo==4)
	   {
	   	var a=111;
		window.location.href="./getCAMreports?jspId="+a;
	   }
	   }
   if("${unqId}"==2)
   {
	   if(slNo==1)
	   {
	   var a=20;
		window.location.href="./getAll?jspId="+a;
	   }
	 
   }
   if("${unqId}"==3)
   {
  
   if(slNo==1)
	   {
	   var a=21;
		window.location.href="./getAll?jspId="+a;
	   }
   if(slNo==2)
   {
   var a=51;
	window.location.href="./getAll?jspId="+a;
   }
   
   }

   if("${unqId}"==4)
   {
  
   if(slNo==1)
   {
   var a=22;
	window.location.href="./getAll?jspId="+a;
   }
   if(slNo==2)
   {
   var a=23;
	window.location.href="./getAll?jspId="+a;
   }
   
   }

   if("${unqId}"==5)
   {
  
     if(slNo==1)
   {
   var a=24;
	window.location.href="./getAll?jspId="+a;
   }
   
   if(slNo==2)
   {
   var a=25;
	window.location.href="./getAll?jspId="+a;
   }
   
   if(slNo==3)
   {
   var a=26;
	window.location.href="./getAll?jspId="+a;
   }
   
    if(slNo==4)
   {
   var a=27;
	window.location.href="./getAll?jspId="+a;
   }
   if(slNo==5)
   {
   var a=28;
	window.location.href="./getAll?jspId="+a;
   }
   if(slNo==6)
   {
   var a=40;
	window.location.href="./getAll?jspId="+a;
   }
   if(slNo==7)
   {
   var a=45;
	window.location.href="./getAllStaffLog?jspId="+a;
   }
   
   }

   if("${unqId}"==6)
   {
	   

    if(slNo==1)
   {
   var a=29;
	window.location.href="./getAll?jspId="+a;
   }
    
    if(slNo==2)
    {
    var a=42;
 	window.location.href="./getAll?jspId="+a;
    }
   }
   
   //*********************billing part*************************************** 
    if("${unqId}"==8)
   {
	   

    if(slNo==1)
   {

        window.location.href="./getAllBillingRepots?jspId="+slNo;
   }
    if(slNo==2)
    {
         
         window.location.href="./getAllBillingRepots?jspId="+slNo;
    }
     
    if(slNo==3)
    {
         
         window.location.href="./getAllBillingRepots?jspId="+slNo;
    }
    if(slNo==4)
    {
         
         window.location.href="./getAllBillingRepots?jspId="+slNo;
    }
    if(slNo==5)
    {
         
         window.location.href="./getAllBillingRepots?jspId="+slNo;
    }
    if(slNo==6)
    {
         
         window.location.href="./getAllBillingRepots?jspId="+slNo;
    }
    if(slNo==7)
    {
         
         window.location.href="./getAllBillingRepots?jspId="+slNo;
    }
     
   }
    
    
    /* :::::::::::::::::::::::::::Helep Desk Report:::::::::::::::::::::::::::::::  */
     
   
    if("${unqId}"==9)
   	 
    {
     
       if(slNo==1)
    {
     	  
     	 

       window.location.href="./getAllhelpDeskReport?jspId="+slNo;
    }
     if(slNo==2)
     {
          
    	 window.location.href="./getAllhelpDeskReport?jspId="+slNo;
     }
      
     if(slNo==3)
     {
          
    	 window.location.href="./getAllhelpDeskReport?jspId="+slNo;
     }
     if(slNo==4)
     {
          
    	 window.location.href="./getAllhelpDeskReport?jspId="+slNo;
     }
     if(slNo==5)
     {
          
    	 window.location.href="./getAllhelpDeskReport?jspId="+slNo;
     }
     if(slNo==6)
     {
          
    	 window.location.href="./getAllhelpDeskReport?jspId="+slNo;
     }
     if(slNo==7)
     {
          
    	 window.location.href="./getAllhelpDeskReport?jspId="+slNo;
     }
      
     if(slNo==8)
     {
          
    	 window.location.href="./getAllhelpDeskReport?jspId="+slNo;
     }
     if(slNo==9)
     {
          
    	 window.location.href="./getAllhelpDeskReport?jspId="+slNo;
     }
     if(slNo==10)
     {
          
    	 window.location.href="./getAllhelpDeskReport?jspId="+slNo;
     }
    }
    /*::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
  
   /*::::::::::::::::::::::::::::::::::Income Tracker Report::::::::::::::::::::::::::::::  */

   if("${unqId}"==10)
   	
    {
     
       if(slNo==1)
    {
     	  
     	 

       window.location.href="./getIncomeTrackerReport?jspId="+slNo;
    }
     if(slNo==2)
     {
          
    	 window.location.href="./getIncomeTrackerReport?jspId="+slNo;
     }
      
     if(slNo==3)
     {
          
    	 window.location.href="./getIncomeTrackerReport?jspId="+slNo;
     }
     if(slNo==4)
     {
         var a=44;
    	 window.location.href="./getAll?jspId="+a;
     }
     if(slNo==5)
     {
          
    	 window.location.href="./getIncomeTrackerReport?jspId="+slNo;
     }
     if(slNo==6)
     {
          
    	 window.location.href="./getIncomeTrackerReport?jspId="+slNo;
     }
     if(slNo==7)
     {
          
    	 window.location.href="./getIncomeTrackerReport?jspId="+slNo;
     }
      
     if(slNo==8)
     {
          
    	 window.location.href="./getIncomeTrackerReport?jspId="+slNo;
     }
     if(slNo==9)
     {
          
    	 window.location.href="./getIncomeTrackerReport?jspId="+slNo;
     }
     
     if(slNo==10)
     {
          
    	 window.location.href="./getIncomeTrackerReport?jspId="+slNo;
     }

     if(slNo==12)
	   {
	   	var a=50;
		window.location.href="./getAllStaffLog?jspId="+a;
	   }
   
     if(slNo==11)
     {
          
    	 window.location.href="./getIncomeTrackerReport?jspId="+slNo;
     }
     if(slNo==13)
     {
          
    	 window.location.href="./paymentStatmentReport";
     }
    
    
    }
   
    }	
	/*::::::::::::::::::::: ::::::::::::::::::::::::::::::::::::::::::::::: */	
   
					function contactInfo1() {

						var slNo = "";
						var gaddressview = $("#grid").data("kendoGrid");
						var selectedAddressItem = gaddressview
								.dataItem(gaddressview.select());
						slNo = selectedAddressItem.slNo;
						//alert("sl="+slNo);
						if ("${unqId}" == 1) {

							if (slNo == 7) {
								window
										.open("./familyTemplate/familyTemplatesDetailsExport");

							} else if (slNo == 11) {
								var a = 10;
								window.open("./comAll/exportExcel/" + a);
							} else if (slNo == 9) {
								var a = 1;
								window.open("./comAll/exportExcel/" + a);
							} else if (slNo == 13) {
								var a = 12;
								window.open("./comAll/exportExcel/" + a);
							} else if (slNo == 16) {

								window
										.open("./ownerPdfTemplate/ownerPdfTemplatesDetailsExport");
							}

							else if (slNo == 18) {
								var a = 19;
								window.open("./comAll/exportExcel/" + a);
							}

							else if (slNo == 19) {
								var a = 30;
								window.open("./comAll/exportExcel/" + a);
							} else {
								window.open("./comAll/exportExcel/" + slNo);

							}
						}

						if ("${unqId}" == 2) {

							if (slNo == 1)

							{
								var a = 20;
								window.open("./comAll/exportExcel/" + a);

							}

						}
						if ("${unqId}" == 3) {

							if (slNo == 1)

							{
								var a = 21;
								window.open("./comAll/exportExcel/" + a);

							}
							if (slNo == 2)

							{
								var a = 51;
								window.open("./comAll/exportExcel/" + a);

							}
						}

						if ("${unqId}" == 4) {

							if (slNo == 1)

							{
								var a = 22;
								window.open("./comAll/exportExcel/" + a);

							}
							if (slNo == 2)

							{
								var a = 23;
								window.open("./comAll/exportExcel/" + a);

							}

						}

						if ("${unqId}" == 5) {

							if (slNo == 4)

							{
								var a = 27;
								window.open("./comAll/exportExcel/" + a);

							}

							if (slNo == 1)

							{
								var a = 24;
								window.open("./comAll/exportExcel/" + a);

							}

							if (slNo == 2)

							{
								var a = 25;
								window.open("./comAll/exportExcel/" + a);

							}

							if (slNo == 3)

							{
								var a = 26;
								window.open("./comAll/exportExcel/" + a);

							}

							if (slNo == 5)

							{
								var a = 28;
								window.open("./comAll/exportExcel/" + a);

							}
							if (slNo == 6)

							{
								var a = 40;
								window.open("./comAll/exportExcel/" + a);

							}

							if (slNo == 7)

							{
								alert("Click on View Report then do Export to Excel");

							}
						}

						if ("${unqId}" == 6) {

							if (slNo == 1)

							{
								var a = 29;
								window.open("./comAll/exportExcel/" + a);

							}

							if (slNo == 2)

							{
								var a = 42;
								window.open("./comAll/exportExcel/" + a);

							}
						}

						//**********Ticket Part Excel Report*************

						if ("${unqId}" == 9) {

							if (slNo == 1)

							{

								window.open("./helpDeskReport/exportExcel/"
										+ slNo);

							}
							if (slNo == 2)

							{

								window.open("./helpDeskReport/exportExcel/"
										+ slNo);

							}
							if (slNo == 3)

							{

								window.open("./helpDeskReport/exportExcel/"
										+ slNo);

							}
							if (slNo == 4)

							{

								window.open("./helpDeskReport/exportExcel/"
										+ slNo);

							}
							if (slNo == 5)

							{

								window.open("./helpDeskReport/exportExcel/"
										+ slNo);

							}
							if (slNo == 6)

							{

								window.open("./helpDeskReport/exportExcel/"
										+ slNo);

							}
							if (slNo == 7)

							{

								window.open("./helpDeskReport/exportExcel/"
										+ slNo);

							}
							if (slNo == 8)

							{

								window.open("./helpDeskReport/exportExcel/"
										+ slNo);

							}

							if (slNo == 9)

							{

								window.open("./helpDeskReport/exportExcel/"
										+ slNo);

							}
							if (slNo == 10)

							{

								window.open("./helpDeskReport/exportExcel/"
										+ slNo);

							}

						}

						if ("${unqId}" == 8) {
							var month = "";
							if (slNo == 1)
							{
								alert("Click on View Report then do Export to Excel");
								/* window.open("./billAll/exportExcel/"+slNo);	 */
							}
							if (slNo == 2)
							{
								alert("Click on View Report then do Export to Excel");
							}
							if (slNo == 3)
							{
								alert("Click on View Report then do Export to Excel");
							}
							if (slNo == 4)
							{
								/* alert("Click on View Report then do Export to Excel"); */
								window.open("./billAll/exportExcel2/" + slNo);
							}
							if (slNo == 5)
							{
								/*  alert("Click on View Report then do Export to Excel"); */
								window.open("./billAll/exportExcel2/" + slNo);
							}
							if (slNo == 6)
							{
								window.open("./billAll/exportExcel/" + slNo);
							}
							if (slNo == 7)
							{
								window.open("./billAll/exportExcel/" + slNo);
							}
						}
						if ("${unqId}" == 10) {
							if (slNo == 1){
								window.open("./incomeTracker/exportExcel/"+ slNo);
							}
							if (slNo == 3){
								window.open("./incomeTracker/exportExcel/"+ slNo);
							}
							if (slNo == 5){
								window.open("./incomeTracker/exportExcel/"+ slNo);
							}
							if (slNo == 8){
								window.open("./incomeTracker/exportExcel/"+ slNo);
							}
							if (slNo == 9) {
								window.open("./incomeTracker/exportExcel/"+ slNo);
							}
							if (slNo == 4)
							{
								var a = 44;
								window.open("./comAll/exportExcel/" + a);
							}
							if (slNo == 12)
							{
								alert("Click on View Report then do Export to Excel");
							}
							if (slNo == 2)
							{
								alert("Click on View Report then do Export to Excel");
							}
							if (slNo == 6)
							{
								alert("Click on View Report then do Export to Excel");
							}
							if (slNo == 7)
							{
								alert("Click on View Report then do Export to Excel");
							}
							if (slNo == 10)
							{
								alert("Click on View Report then do Export to Excel");
							}
							if (slNo == 11)
							{
								alert("Click on View Report then do Export to Excel");
							}
							if (slNo == 13)
							{
								alert("Click on View Report then do Export to Excel");
							}
						}
						if ("${unqId}" == 11){
							if (slNo == 2)
							{
								var a = 46;
								window.open("./comAll/exportExcel/" + a);
							}
							if (slNo == 4)
							{
								var a = 46;
								window.open("./ExportCamReportToExcel");
							}
							if (slNo == 1)
							{
								alert("Click on View Report then do Export to Excel");
							}
						}

					}
					$(document).ready(function() {
						/* alert("${unqId}"); */

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

					});
				</script>
    
