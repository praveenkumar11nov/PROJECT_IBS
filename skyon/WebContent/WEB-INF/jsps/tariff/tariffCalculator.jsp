<%@taglib prefix="kendo" uri="/WEB-INF/kendo.tld"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:url value="/tariff/readtariffname" var="readUrl" />

<c:url value="/tariff/readratename" var="productsUrl" />

<link
	href="<c:url value='/resources/kendo/css/web/kendo.common.min.css'/>"
	rel="stylesheet" />
<link href="<c:url value='/resources/kendo/css/web/kendo.rtl.min.css'/>"
	rel="stylesheet" />
<link
	href="<c:url value='/resources/kendo/css/web/kendo.bootstrap.min.css'/>"
	rel="stylesheet" />
<link
	href="<c:url value='/resources/kendo/css/dataviz/kendo.dataviz.min.css'/>"
	rel="stylesheet" />
<link
	href="<c:url value='/resources/kendo/css/dataviz/kendo.dataviz.default.min.css'/>"
	rel="stylesheet" />
<link
	href="<c:url value='/resources/kendo/shared/styles/examples-offline.css'/>"
	rel="stylesheet">
<script src="<c:url value='/resources/kendo/js/jquery.min.js' />"></script>
<%-- <script type="text/javascript"
	src="<c:url value='/resources/jquery.min.js'/>"></script> --%>
<script type="text/javascript"
	src="<c:url value='/resources/jquery-ui.min.js'/>"></script>
<script src="<c:url value='/resources/kendo/js/kendo.all.min.js' />"></script>
<script src="<c:url value='/resources/kendo/shared/js/console.js'/>"></script>
<script src="<c:url value='/resources/kendo/shared/js/prettify.js'/>"></script>
<script type="text/javascript"
	src="<c:url value='/resources/jquery-validate.js'/>"></script>
	<div style="padding-left: 500px" class="fluid">
	
	<div id="tariffCalc" class="widget grid8"   >
	
		<div class="whead">
	<h6>Tariff Calculator</h6>
			<div class="titleOpt" >
				<a href="#" data-toggle="dropdown" id=""><span class="icos-cog3"></span></a>
				<ul class="dropdown-menu pull-right">
								
				     <li>Tod Calculation&nbsp;&nbsp;<input type="checkbox" class="calculate" name="#checkbox1" id="#checkbox1" onchange="changeCheck()" ></li>
				      <!-- <li>Tax Calculation&nbsp;&nbsp;<input type="checkbox" class="calculate" name="#checkbox2" id="#checkbox1" onchange="changeCheck1()" ></li>
				       <li>Rebate Calculation&nbsp;&nbsp;<input type="checkbox" class="calculate" name="#checkbox3" id="#checkbox1" onchange="changeCheck2()" ></li> -->
				</ul>
			</div>
	</div>
	
	
	<div  id="normCal" style="padding-left: 80px" >
	<form style="margin-top: 2px;"  id="addform" method="post">
	
	<table id="table1">
	
	<tr>
		<td>Tariff Name</td>
	<td>	 <!-- <select id="tariffName" style=" margin:3px;width: 148px" onchange="gettariffId()"></select>  -->
	
	        <kendo:dropDownList name="tariffName" id="tariffName" style="margin:5px;width:79%;" onchange="gettariffId()"></kendo:dropDownList>   
		 
		
		
		</td>
	</tr>
	<tr>
		<td>Rate Name</td>
		<td>
		 <kendo:dropDownList name="rateName" id="rateName" style="margin:5px;width:79%;" cascadeFrom="tariffName" optionLabel="Select Rate..." onchange="getrateUom();opentext()"></kendo:dropDownList></td>
			
		</tr>
	
	
	<tr> 
	 <!-- <td<span class="replaceme">UOM </span></td> -->
	 <td><span class="replaceme">UOM </span></td>
	 <%-- <td><kendo:dropDownList name="rateUOM" id="rateUOM"  cascadeFrom="rateName" ></kendo:dropDownList></td>   --%>
		<td>
		
		 <kendo:numericTextBox name="numeric" id="unit" placeholder="Enter numeric value" min="0" decimals="4" step="0.0001" format="{0:n4}" value="" style="margin:3px;padding:0px;width:145px"></kendo:numericTextBox>
                        			
		</td>
						    
    </tr>
    <tr id="extra" style=" display: none">
    <td ><span class="replay">Unit</span></td>
		<td>
		 <kendo:numericTextBox name="numeric" id="extraAmount" placeholder="Enter numeric value" min="0"  value="" style="margin:3px;padding:0px;width:145px"></kendo:numericTextBox>
                        			
		</td>
						    
    </tr>

  
		<tr>
		
					<td>From-Date</td>
					<td> <kendo:datePicker format="yyyy/MM/dd  "  name="validFrom" id="validFrom" parse="parse()" value="${today}"  style="margin: 5px; width: 79%;  padding: 0px 0px;"
					change="startChange"
					 required="required" class="validate[required]">
					</kendo:datePicker><td>
					</td>
										    
		</tr>
		<tr>
					<td>To-Date</td>
					<td> <kendo:datePicker format="yyyy/MM/dd  " name="validTo" id="validTo" parse="parse()" value="${today}" style="margin:5px;width:79%; padding: 0px 0px;" 
					 change="endChange"					 
					 required="required" class="validate[required]">
					</kendo:datePicker></td>
					</tr>
                                                                        
		
		
		</table>	
		<div id="btton" style="padding-left: 80px">
		<table>
	<tr><td class="left" align="left" >
	<button type="submit"  id="calcu" class="k-button"  onclick="getAmount()"
						style="margin:10px;width:100px;padding-left: 10px" >Calc Tariff</button>
	<!--  <button class="submit" id="removeNode" >Calc Tariff</button> -->
	</td></tr>
	</table>
	</div>
	</form>
</div>
<div  id="TodCal" style="padding-left: 80px;display: none;" >
	<form style="margin-top: 2px;"  id="addTodForm" method="post">
	
	<table>
	
	<tr>
		<td>Tariff Name</td>
	<td>	 <!-- <select id="tariffName" style=" margin:3px;width: 148px" onchange="gettariffId()"></select>  -->
	
	        <kendo:dropDownList name="tariffNamet" id="tariffNamet" style="margin:5px;width:79%;" onchange="gettariffIdTOd()"></kendo:dropDownList>   
		 
		
		
		</td>
	</tr>
	<tr>
		<td>Rate Name</td>
		<td>
		 <kendo:dropDownList name="rateNamet" id="rateNamet" style="margin:5px;width:79%;" cascadeFrom="tariffNamet" optionLabel="Select Rate..."></kendo:dropDownList></td>
			
		</tr>
	
	<tr>
	 <td>22.00 Hrs to 06.00 Hrs</td><!--T1 units  -->
		<td>		
		 <kendo:numericTextBox name="numeric" id="t1unit" placeholder="Enter numeric value" min="0"  value="" style="margin:3px;padding:0px;width:145px"></kendo:numericTextBox>
        </td></tr>   			
		<tr>
	 <td>06.00 Hrs to 18.00 Hrs</td><!-- T2 units -->
		<td>		
		 <kendo:numericTextBox name="numeric" id="t2unit" placeholder="Enter numeric value" min="0"  value="" style="margin:3px;padding:0px;width:145px"></kendo:numericTextBox>
        </td></tr> 
          <tr>
	 <td>18.00 Hrs to 22.00 Hrs</td><!--T3 units  -->
		<td>		
		 <kendo:numericTextBox name="numeric" id="t3unit" placeholder="Enter numeric value" min="0"  value="" style="margin:3px;padding:0px;width:145px"></kendo:numericTextBox>
        </td></tr>
        <tr>
	<%--  <td>T4 Units </td>
		<td>		
		 <kendo:numericTextBox type="hidden" name="numeric" id="t4unit" placeholder="Enter numeric value" min="0"  value="" style="margin:3px;padding:0px;width:145px"></kendo:numericTextBox>
        </td></tr>
        <tr>
	 <td>T5 Units </td>
		<td>		
		 <kendo:numericTextBox name="numeric" id="t5unit" placeholder="Enter numeric value" min="0"  value="" style="margin:3px;padding:0px;width:145px"></kendo:numericTextBox>
        </td></tr> --%>
		<tr>
		
					<td>From-Date</td>
					<td> <kendo:datePicker format="yyyy/MM/dd  "  name="validFromt" id="validFromt" parse="parse()" value="${today}"  style="margin: 5px; width: 79%;  padding: 0px 0px;"
					change="startChangetod"
					 required="required" class="validate[required]">
					</kendo:datePicker><td>
					</td>
										    
		</tr>
		<tr>
					<td>To-Date</td>
					<td> <kendo:datePicker format="yyyy/MM/dd  " name="validTot" id="validTot" parse="parse()" value="${today}" style="margin:5px;width:79%; padding: 0px 0px;" 
					 change="endChangetod"					 
					 required="required" class="validate[required]">
					</kendo:datePicker></td>
					</tr>
                                                                        
		
		
		</table>	
		<div id="bttontod" style="padding-left: 80px">
		<table>
	<tr><td class="left" align="left" >
	<button type="submit"  id="calcutod" class="k-button"  onclick="gettodAmount()"
						style="margin:10px;width:100px;padding-left: 10px" >Calc Tariff</button>
	<!--  <button class="submit" id="removeNode" >Calc Tariff</button> -->
	</td></tr>
	</table>
	</div>
	</form>
</div>
<div  id="TaxCal" style="padding-left: 80px;display: none;" >
	<form style="margin-top: 2px;"  id="calTaxForm" method="post">
	
	<table>
	
	
	
	
	<tr>
	 <td>Tax Amount</td>
		<td>		
		 <kendo:numericTextBox name="numeric" id="taxAmount" placeholder="Enter numeric value" min="0"  value="" style="margin:3px;padding:0px;width:145px"></kendo:numericTextBox>
        </td></tr>   			
		<tr>
	 <td>Tax Percentage</td><!-- T2 units -->
		<td>		
		 <kendo:numericTextBox name="numeric" id="taxPercentage" placeholder="Enter numeric value" min="0" max="100" value="" style="margin:3px;padding:0px;width:145px"></kendo:numericTextBox>
        </td></tr> 
          <tr>
          <tr><td class="left" align="left" colspan="2" >
	<button type="submit" id="taxAmountButton" class="k-button"  onclick="getTax()"
						style="margin:10px;width:100px;padding-left: 10px" >Calc Tax</button>
	<!--  <button class="submit" id="removeNode" >Calc Tariff</button> -->
	</td></tr>
  
        
        
        </table>
        
        </form>
        </div>
        
        <div id="RebateCal" style="padding-left: 80px;display: none;">
       <form style="margin-top: 2px;"  id="calRebateForm" method="post">
       <table>
        <tr>  
	 <td>Rebate Amount</td><!--T3 units  -->
		<td>		
		 <kendo:numericTextBox name="numeric" id="rebateAmount" placeholder="Enter numeric value" min="0"  value="" style="margin:3px;padding:0px;width:145px"></kendo:numericTextBox>
        </td></tr>
        <tr>
	 <td>Rebate Percentage</td><!-- T2 units -->
		<td>		
		 <kendo:numericTextBox name="numeric" id="rebatePercentage" placeholder="Enter numeric value" min="0"  value="" style="margin:3px;padding:0px;width:145px"></kendo:numericTextBox>
        </td></tr> 
          <tr>
          <tr><td class="left"  align="left" >
	<button type="submit"  id="calcRebate" class="k-button"  onclick="getRebate()"
						style="margin:10px;width:100px;padding-left: 10px" >Calc Rebate</button>
	<!--  <button class="submit" id="removeNode" >Calc Tariff</button> -->
	</td></tr>
		   
       
       
       
       
       </table>
       
       
       </form>
	</div>

	</div>
	</div>
	
	
	
	<div id="amountCalc"  class="dialog"   style="color: blue;size:100px;font-size:15px; display: none" title="Total Calucaltion" >
	
	</div>
	
	<div id="todamountCalc" class="dialog"  style="color: blue;size:100px;font-size:12px; display: none" title="Total Calucaltion" >
	
	</div>
	
   <div id="taxAmountpop" class="dialog"  style="color: blue;size:100px;font-size:12px; display: none" title="Total Calucaltion" >
	
	</div>
	
	
<script type="text/javascript">

$('input.calculate').on('change', function() {
    $('input.calculate').not(this).prop('checked', false);  
});

function changeCheck()
{
	
	var test = $('input:checked').length ? $(
     'input:checked').val() : '';
   
     if(test === 'on')
    	 {
    	 	$('#normCal').hide();
    	 	$('#TodCal').show();
    		$('#RebateCal').hide();
    		$('#TaxCal').hide();
    	 	
    	 }
     else{
    	 $('#normCal').show();
    	 $('#TodCal').hide();
 	 	$('#TaxCal').hide();
 	 	$('#RebateCal').hide();
    		
    	 	
    	 }
     } 
function changeCheck1()
{
	
	var test = $('input:checked').length ? $(
     'input:checked').val() : '';
   
     if(test === 'on')
    	 {
    	 	$('#normCal').hide();
    	 	$('#TodCal').hide();
    		$('#TaxCal').show();
    		$('#RebateCal').hide();
    	 	
    	 }
     else{
    	 $('#normCal').show();
    		$('#TodCal').hide();
    	 	$('#TaxCal').hide();
    	 	$('#RebateCal').hide();
    	 	
    	 }
     } 

function changeCheck2()
{
	
	var test = $('input:checked').length ? $(
     'input:checked').val() : '';
   
     if(test === 'on')
    	 {
    	 	$('#normCal').hide();
    	 	$('#TodCal').hide();
    		$('#TaxCal').hide();
    		
    		$('#RebateCal').show();
    	 	
    	 }
     else{
    	 $('#normCal').show();
    	 	$('#TodCal').hide();
    	 	$('#TaxCal').hide();
    	 	$('#RebateCal').hide();
    	 	
    	 }
     } 

function startChange() {
    var endPicker = $("#validTo").data("kendoDatePicker"),
        startDate = this.value();

    if (startDate) {
        startDate = new Date(startDate);
        startDate.setDate(startDate.getDate() + 1);
        endPicker.min(startDate);
    }
}

function endChange() {
    var startPicker = $("#validFromt").data("kendoDatePicker"),
        endDate = this.value();

    if (endDate) {
        endDate = new Date(endDate);
        endDate.setDate(endDate.getDate() - 1);
        startPicker.max(endDate);
    }
}
function startChangetod() {
    var endPicker = $("#validTot").data("kendoDatePicker"),
        startDate = this.value();

    if (startDate) {
        startDate = new Date(startDate);
        startDate.setDate(startDate.getDate() + 1);
        endPicker.min(startDate);
    }
}

function endChangetod() {
    var startPicker = $("#validFrom").data("kendoDatePicker"),
        endDate = this.value();

    if (endDate) {
        endDate = new Date(endDate);
        endDate.setDate(endDate.getDate() - 1);
        startPicker.max(endDate);
    }
}
function gettariffId(){
	 var elTariffID =$("#tariffName").val();
	
	 
	

		   $("#rateName").kendoDropDownList({
		
        autoBind: false,
        cascadeFrom: "tariffName",
        optionLabel: "Select Rates...",
        dataTextField: "rateName",
        dataValueField: "elTariffID",
        dataSource: {
        	 
            transport: {
                read: "./tariff/readratename?elTariffID="+elTariffID,
                	
                	
            }
        }
    }).data("kendoDropDownList"); 
		 
}
function opentext(){
	 var ratename= $("input[name=rateName]").data("kendoDropDownList").text();
	 //var extraAmount=$("#extraAmount").val();
	 if(ratename=="DC" | ratename=="Supply Voltage Rebate" | ratename=="PF Penalty"){
		// alert("extra");
		// $("#extra").css({"display":"show"});
		$("#extra").show();
		if(ratename=="DC"){
			 $('.replay').html("Demand Charge");	
		}
		
		 
	 }else{
		// alert("extra hide")
			$("#extra").hide();	 
			$("#extraAmount").val("0");
	 }
	 
}
function getrateUom(){
	//alert("rateUom");
	 var elTariffID =$("#rateName").val();
	 var ratename= $("input[name=rateName]").data("kendoDropDownList").text();
	 //alert(elTariffID);
	 //alert( ratename);
	 /* var uom = $("#rateUOM").kendoDropDownList({
			
		    autoBind: false,
	        cascadeFrom: "rateName",
	       
	        dataTextField: "rateUOM",
	        dataValueField: "elrmid",
	        optionLabel: "Select Rate UOM...",
	        dataSource: {
	                     
	            transport: {
	            	
	                read: "./tariff/readrateuom?ratename="+ratename+"&elTariffID="+elTariffID,
	                
	            }
	        }
	    }).data("kendoDropDownList");  */
	 $.ajax({
			
			url : "./tariff/readrateuom?ratename="+ratename+"&elTariffID="+elTariffID,
	        type: "GET",
	        

			success : function(response)
			{
				 //  alert("response"+response);
				   
				   $('.replaceme').html(response);
	    	     		}

			});
	    
	    
	    
	    
	    
	    
}
 
$(document).ready(function() 
		{
	    
	//$('#table1 .extra').hide();
	//$('#table1 tr:has(td.extra)').hide()
	// $('extra').hide();
	
	 var tariffName = $("#tariffName").kendoDropDownList({
		
        dataTextField: "tariffName",
        dataValueField: "elTariffID",
        optionLabel: "Select Tariff...",
        dataSource: {
                     
            transport: {
            	
                read: "./tariff/readtariffname",
                
            }
        }
    }).data("kendoDropDownList"); 
	
	/*  $( "#taxAmountpop" ).dialog({
		 autoOpen: false,
		 height: 100,
		 width: 400,
		 modal: true,
	});
	$( "#taxAmountButton" ).click(function() {
		$( "#taxAmountpop" ).dialog( "open" );	
		   }); 	 
	

	



	
	 $( "#amountCalc" ).dialog({
		 autoOpen: false,
		 height: 500,
		 width: 500,
		 modal: true,
	  
	 
		 
	});
	$( "#calcu" ).click(function() {
		$( "#amountCalc" ).dialog( "open" );	
		   });  */

	/*---------------------------For Tod calculation-------------------------------------------------------------------------------------------*/
		
	/*  $( "#todamountCalc" ).dialog({
		 autoOpen: false,
		 height: 500,
		 width: 500,
		 modal: true,
	     
	 
		 
	});
	$( "#calcutod" ).click(function() {
		$( "#todamountCalc" ).dialog( "open" );	
		   });  */
	
	
	 var tariffName = $("#tariffNamet").kendoDropDownList({
			
	        dataTextField: "tariffName",
	        dataValueField: "elTariffID",
	        optionLabel: "Select Tariff...",
	        dataSource: {
	                     
	            transport: {
	            	
	                read: "./tariff/readtariffnametod",
	                
	            }
	        }
	    }).data("kendoDropDownList"); 
		
/*end  */		
		
				
		});
		
		/*end of document.ready  */
/*for previnting default submit of form  */
$("#addform").submit(function(e){
    e.preventDefault();
});

		

$("#addTodForm").submit(function(e){
    e.preventDefault();
});

/*.............end of function................  */
/* function opencalc(){

	 var amountcalc=$( "#amountCalc" );
	
	
	 amountcalc.dialog({
		 autoOpen: false,
		 height: 500,
		 width: 500,
		 modal: true,
		 
	});
	
	
		 amountcalc.dialog( "open" );	
	
	
} 
function opencalctod(){
  
	var todcal=$( "#todamountCalc" );
		
	todcal.dialog({
		 autoOpen: false,
		 height: 500,
		 width: 500,
		 modal: true,
	 
	  resizable: false, 
		 
	});
	
	 $( "#calcutod" ).click(function() {
		 todcal.dialog( "open" );	
		   }); 
	
}  */





 function  getAmount(){

	var tariffName = $("input[name=tariffName]").data("kendoDropDownList").text();
    var  eltariffId=$("#rateName").val();
	var rateName = $("input[name=rateName]").data("kendoDropDownList").text();
	var unit=$("#unit").val();
	var validFrom=$("#validFrom").val();
	var validTo=$("#validTo").val();
	var extraAmount=$("#extraAmount").val();
	 var amountcalc=$( "#amountCalc" );
	if(unit>0 ){
			
	$.ajax({
		
		url : "./tariffCalc/getAmount",
        type: "GET",
        data : {
        	 rateName : rateName, 
        	 tariffName : tariffName,
        	 rateName : rateName,
        	 unit : unit ,        	
        	 validFrom:validFrom,
        	 validTo:validTo, 
        	 extraAmount:extraAmount,
			 },

		success : function(response)
		{
			      $("#amountCalc").empty();
		    	  $('#amountCalc').append("<table><tr><td colspan =5 >"+response+"</td></tr></table>");
    	     		}

		});
	
	amountcalc.dialog({
		 autoOpen: false,
		 height: 500,
		 width: 500,
		 modal: true,
		 
	});
	
	amountcalc.dialog( "open" );
	}else{
		alert("Please enter all fields");
	}
     
	
		
		
	 	
		  
	
	 
	 ;}



 function gettariffIdTOd(){
	 var elTariffID =$("#tariffNamet").val();
	
	 
	

		   $("#rateNamet").kendoDropDownList({
		
        autoBind: false,
        cascadeFrom: "tariffNamet",
        optionLabel: "Select Rates...",
        dataTextField: "rateName",
        dataValueField: "elTariffID",
        dataSource: {
        	 
            transport: {
                read: "./tariff/readratenametod?elTariffID="+elTariffID,
                	
                	
            }
        }
    }).data("kendoDropDownList"); 
		 
}

 function  gettodAmount(){

		var tariffName = $("input[name=tariffNamet]").data("kendoDropDownList").text();
		var rateName = $("input[name=rateNamet]").data("kendoDropDownList").text();
		var t1unit=$("#t1unit").val();
		var t2unit=$("#t2unit").val();
		var t3unit=$("#t3unit").val();
		var t4unit=$("#t4unit").val();
		var t5unit=$("#t5unit").val();
		var validFrom=$("#validFromt").val();
		var validTo=$("#validTot").val(); 
				
		$.ajax({
			
			url : "./tariffCalc/gettodAmount",
	        type: "GET",
	        data : {
	        	rateName : rateName, 
	        	tariffName : tariffName,
	        	rateName : rateName,
	        	t1unit : t1unit ,        	
	        	t2unit:t2unit,
	        	t3unit:t3unit,
	        	t4unit:t4unit,
	        	t5unit:t5unit,
	        	validFrom:validFrom,
	        	validTo:validTo, 
				 },

			success : function(response)
			{
			  
			   $("#todamountCalc").empty();
				$('#todamountCalc').append("<table><tr><td colspan =5 > "+response+"</td></tr>  </table>");

			}
			});
		
		var todcal=$( "#todamountCalc" );
		
		todcal.dialog({
			 autoOpen: false,
			 height: 500,
			 width: 500,
			 modal: true,
		 
		  resizable: false, 
			 
		});
		
		
			 todcal.dialog( "open" );	
			  
		
		
		 
		 ;}

 function  getRebate(){

		
		
		var rebateAmount=$("#rebateAmount").val();
		var rebatePercentage=$("#rebatePercentage").val();
		
				
				
		$.ajax({
			
			url : "./tariffCalc/getrebateAmount",
	        type: "GET",
	        data : {
	        	rebateAmount : rebateAmount, 
	        	rebatePercentage : rebatePercentage,
	        	
	        	
				 },

			success : function(response)
			{
			  
			   $("#todamountCalc").empty();
				$('#todamountCalc').append("<table><tr><td colspan =5 > "+response+"</td></tr>  </table>"); 

			}
			});
		

		 
		 ;}

 function  getTax(){

		
		
		var taxAmount=$("#taxAmount").val();
		var taxPercentage=$("#taxPercentage").val();
		
				
				
		$.ajax({
			
			url : "./tariffCalc/getTaxdAmount",
	        type: "GET",
	        data : {
	        	taxAmount : taxAmount, 
	        	taxPercentage : taxPercentage,
	        	
	        	
				 },

			success : function(response)
			{
			  
			    $("#taxAmountpop").empty();
				$('#taxAmountpop').append("<table><tr><td colspan =5 > "+response+"</td></tr>  </table>"); 

			}
			});
		

		 
		 ;}

	 
	 
</script>

		
	<style>
#tariffCalc {
	/* font-size: 8pt;
	overflow: auto;
	min-height: 100px;
	height: auto !important;
	height: 100px; */
	font-size: 8pt;
	overflow: auto;
	min-height: 100px;
	height: auto !important;
	position: relative;
	height: 100px;
	float: none;
}
#amountCalc {
	/* font-size: 8pt;
	overflow: auto;
	min-height: 100px;
	height: auto !important;
	height: 100px; */
	font-size: 8pt;
	overflow: auto;
	min-height: 100px;
	height: auto !important;
	position: relative;
	height: 100px;
	float: none;
}
#todamountCalc{
	/* font-size: 8pt;
	overflow: auto;
	min-height: 100px;
	height: auto !important;
	height: 100px; */
	font-size: 8pt;
	overflow: auto;
	min-height: 100px;
	
	position: relative;
	height: 100px;
	float: none;
	
}
td.right {text-align: left;}
</style>
	
	
	
	
	
	