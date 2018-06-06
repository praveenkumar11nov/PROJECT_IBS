<%@taglib prefix="kendo" uri="/WEB-INF/kendo.tld"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
	
<c:url value="/gasTariff/read" var="transportReadUrl" />
<%-- <c:url value="/tariff/read" var="transportReadUrlLoc" /> --%>

<c:url value="/tariff/readall" var="transportReadUrlall" />

<div style="padding-left: 50px" class="fluid">

	<div class="widget grid8" >
		<div class="whead">

			<h6> Gas Tariff Master</h6>
			<div class="titleOpt" >
				<a href="#" data-toggle="dropdown" id=""><span class="icos-cog3"></span></a>
				<ul class="dropdown-menu pull-right">
					<li><a href="#" class="" id="expandAllNodes">Expand All</a></li>
					<li><a href="#" class="" id="collapseAllNodes">Collapse
							All</a></li>
					<li><a data-ajax="false" href="#" class="" id="addtariff" onclick="adddiv()">Add Tariff</a></li>
					<li><a data-ajax="false" href="#" class="" id="updatetariff" onclick="updatediv()" >Update Tariff</a></li>
					
					<li><a href="#" class="" id="removeNode">Delete Tariff</a></li>
				    <!--  <li>&nbsp;&nbsp;Show All Tariff&nbsp;&nbsp;<input type="checkbox" name="#checkbox1" id="#checkbox1" onchange="changeCheck()"></li> -->
				</ul>
			</div>
			<br>
			<!-- <input type="checkbox" name="#checkbox1" id="#checkbox1" onchange="changeCheck()">showall
			<button class="k-button" id="removeNode" >Delete Tariff</button> -->
			<!-- <button class="k-button" id="showall" onclick="getAllData()">Show All Tariff
								</button> -->
		</div>
		<br>
		<div id="current">
		<kendo:treeView name="treeview" dataTextField="gastariffName"
			select="onSelect" 
			template=" #: item.gastariffName # <br>#:item.gasStatus # <input type='hidden' id='syed' class='#: item.gastariffName ##: item.gasTariffId #' value='#: item.gasTariffId#'/>">
			
								
			<kendo:dataSource>
				<kendo:dataSource-transport>
					<kendo:dataSource-transport-read url="${transportReadUrl}"
						type="POST" contentType="application/json" />
					<kendo:dataSource-transport-parameterMap>
						<script>
							function parameterMap(options, type) {
								return JSON.stringify(options);
							}
						</script>
					</kendo:dataSource-transport-parameterMap>
				</kendo:dataSource-transport>
				<kendo:dataSource-schema>
					<kendo:dataSource-schema-hierarchical-model id="gasTariffId"
						hasChildren="hasChilds" />
				</kendo:dataSource-schema>
			</kendo:dataSource>
		</kendo:treeView>
		<table>
		<tr>
		<td>&nbsp; </td>
		<td>&nbsp; </td>
		</tr>
		<tr>
		<td>&nbsp; </td>
		<td>&nbsp; </td>
		</tr>
		<tr>
		<td>&nbsp; </td>
		<td>&nbsp; </td>
		</tr>
		</table>
		<table>
		<tr>
		<td>&nbsp; </td>
		<td>&nbsp; </td>
		</tr>
		<tr>
		<td>&nbsp; </td>
		<td>&nbsp; </td>
		</tr>
		</table>
</div>
<%-- <div id="all" >
		<kendo:treeView name="treeview1" dataTextField="tariffName"
			select="onSelect" 
			template=" #: item.tariffName #<br>#:item.tariffCode #<br>#:item.tariffDescription #  <br>#:item.status # <input type='hidden' id='syed' class='#: item.tariffName ##: item.wtTariffId#' value='#: item.wtTariffId#'/>"
			>
								
			<kendo:dataSource>
				<kendo:dataSource-transport>
					<kendo:dataSource-transport-read url="${transportReadUrlall}"
						type="POST" contentType="application/json" />
					<kendo:dataSource-transport-parameterMap>
						<script>
							function parameterMap(options, type) {
								return JSON.stringify(options);
							}
						</script>
					</kendo:dataSource-transport-parameterMap>
				</kendo:dataSource-transport>
				<kendo:dataSource-schema>
					<kendo:dataSource-schema-hierarchical-model id="wtTariffId"
						hasChildren="hasChilds" />
				</kendo:dataSource-schema>
			</kendo:dataSource>
		</kendo:treeView>
</div> --%>
		<div></div>
	</div>
	


	

	<div id="taskperm" class="dialog" style="display: none" title="Add Tariff" align="center">
		
				<br/>	
				<form style="margin-top: 6px;" id="addform" method="post" data-role="validator" novalidate="novalidate">
				<table style="height: 350px;">
					<tr>
						<td>Tariff Name*</td>
						<td><input id="appendNodeText" name="appendNodeText" class="k-textbox" placeholder="Enter TariffName" 
						 required="required" onchange="getTariffName()" validationMessage="Traiff Name Is Required"/>
						<span id="msgId" style="display:none;color:red"></span>
                        			
						</td>					    
					</tr>				
				
					<tr>					
						<td style="vertical-align:middle;">Tariff Code*</td>
						<td><input  id="gastariffCode" name="gastariffCode" placeholder="Enter TariffCode" class="k-textbox"  required="required" validationMessage="Traiff Code IS Required"/></td>
					</tr>
					
					
					
					
					<tr>
					<td>From-Date*</td>
					<td> <kendo:datePicker format="yyyy/MM/dd  "  name="validFrom" id="validFrom" parse="parse()" value="${today}" 	change="startChange"
					 required="required" class="validate[required]" validationMessage=" From Date Is Required">
					</kendo:datePicker><td>
					</td>
					</tr>
					
					<tr>
					<td>To-Date*</td>
					<td> <kendo:datePicker format="yyyy/MM/dd  " name="validTo" id="validTo" parse="parse()" value="${today}"  
					 change="endChange"	validationMessage=" TO-Date Is Required"	 required="required" >
					</kendo:datePicker></td>
					</tr>
					
					<tr>
					<td>Tariff Node Type*</td>
					<td><kendo:dropDownList name="gastariffNodetype" id="gastariffNodetype" dataTextField="name"
								dataValueField="value" validationMessage="Tariff Node Type Is Required"		required="required" optionLabel="Tariff Node Type"   >
								<kendo:dataSource data="${tariffNodetype}" ></kendo:dataSource>
							</kendo:dropDownList></td>
					<tr> 
						
					<tr >
						<td>Tariff Description*</td>
					<td style="vertical-align:middle;">	<textarea  id="gastariffDescription"
							name="gastariffDescription" class="k-textbox" 
							 required="required" validationMessage="Tariff Description  Is Required"></textarea></td>
					
					</tr>
					<tr>
						<td>Status*</td>
						<td><kendo:dropDownList name="gasStatus" id="gasStatus" dataTextField="name"
								dataValueField="value" required="required" validationMessage="Status  Is Required"  optionLabel="Status">								
								<kendo:dataSource data="${status}"> </kendo:dataSource>
							</kendo:dropDownList></td>
					</tr>
						<tr>
						<td></td>
						<td>
						<input type="submit"  id="appendNodeToSelected" class="buttonS bLightBlue" value="Save Tariff"
						style="margin:5px;width:79%;" />
							<!-- <button class="k-button" id="appendNodeToSelected" style="margin:5px;width:79%;">Save
								Tariff</button> -->
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
					
				</table>
<script type="text/javascript">

/*For opening and closing update div  */

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
        var startPicker = $("#validFrom").data("kendoDatePicker"),
            endDate = this.value();

        if (endDate) {
            endDate = new Date(endDate);
            endDate.setDate(endDate.getDate() - 1);
            startPicker.max(endDate);
        }
    }
 

/*  */
$.validator.setDefaults({ ignore: '' });

/* $.validator.addMethod(
	      "regex",
	      function(value, element, regexp) {
	          var check = false;
	          var re = new RegExp(regexp);
	          return this.optional(element) || re.test(value);
	      },""
	); */
    
    $("#addform").submit(function(e) {
		e.preventDefault();
	});   
  

	 $("#addform").kendoValidator({
         messages: {
             // defines a message for the 'custom' validation rule
             custom: "Please enter valid value for my custom rule",

             // overrides the built-in message for the required rule
             required: "My custom required message",
                 minlen:1
             // overrides the built-in message for the email rule
             // with a custom function that returns the actual message
             
         },
         rules: {
        	 customRule1: function(input){
                 // all of the input must have a value
                 return $.trim(input.val()) !== "";
               },
         }
    });

    function getMessage(input) {
      return input.data("message");
    }
    var addvalidator = $("#addform").kendoValidator().data("kendoValidator");
    $("#appendNodeToSelected").on("click", function() {
	    if (addvalidator.validate()) {
	        
	       addTraiff();
	    }
	});
	
	function addTraiff(){
		var treeview = $("#treeview").data("kendoTreeView");
		var selectedNode = treeview.select();

		var nodename = $("#appendNodeText").val();

		if (selectedNode.length == 0) {
			alert("Select the Node");
			selectedNode = null;
		} else {

			if (nodename != "") {

				var status = $("input[name=gasStatus]").val();
				var tariffdescription = $("#gastariffDescription").val();
						
				var tariffcode=$("#gastariffCode").val();
				
				var tariffnodetype = $("input[name=gastariffNodetype]").val();

				var validFrom=$("#validFrom").val();
				var validTo=$("#validTo").val();

				var treeHierarchy = texts.join('>');
				$
						.ajax({
							type : "POST",
							url : "./gasTariff/create",
							data : {
								gasTariffId : nodeid,
								gastariffName : nodename,
								gastreeHierarchy : treeHierarchy,
								gasStatus : status,
								gastariffDescription : tariffdescription,
								gastariffCode        :  tariffcode,
								gastariffNodetype: tariffnodetype,
								validFrom:       validFrom,
								validTo:  validTo 
								 },
							
							success : function(response) {
								alert("Added Successfully !!!");	
								
								adddivClose();

							  treeview.append({
								  gasTariffId : response,
								  gastariffName : nodename,
								  gastreeHierarchy : treeHierarchy,
								  gasStatus : status,
								  gastariffDescription : tariffdescription,
								  gastariffCode        :  tariffcode,
								  gastariffNodetype: tariffnodetype,
									validFrom:       validFrom,
									validTo:  validTo 
									
								}, selectedNode);    

								 $('.' + nodename).val(
										response);   
						
			
								$("#gastariffDescription").val(" ");
								$("#gastariffCode").val(" ");
								$("#appendNodeText").val(" "); 
								
								
								//$('.'+nodename).removeClass(nodename).addClass(nodename+response);
								//$('.'+nodename).addClass(response.trim()); 

							}
						});

			} else {
				alert("Please enter the required details");
			}
		}	
	}

	function adddivClose(){
		var divObj = $('#taskperm');		 
		 divObj.kendoWindow({
	            autoOpen: false,
	            modal: true,
	            draggable: true,
	            width: 350,
	            height: 'auto',
	            resizable: false,
	            draggable: false,
	            bgiframe: true,
	            title : "Add Tariff"
	        }).data("kendoWindow").center().close();;
	
	        divObj.kendoWindow("close");
				
	} 
function  getTariffName(){
	
	var  tariffName = $('#appendNodeText').val();
	  
	$.ajax({
		
		url : "./tariffMaster/gettariffname/"+tariffName,
		

        type: "POST",

		success : function(response)
		{
			
			
			if(response== 'Namefound'){
			$("#msgId").text("Already Exists");
			$("#msgId").css("display", "block");
			}  else{
				
				$("#msgId").css("display", "none");	
				 
			} 
		}

		});
  
	 
	 ;}

</script>
				<br>
			
		</form>
	</div>
	
	<!-- <div id="emptyDiv" class="widget grid2"></div> -->
	
  <div id="updateperm" title="Update Tariff" align="center" style="display: none"> 
		<br/>	
				<form style="margin-top: 6px;" id="updateform" method="post" data-role="validator" novalidate="novalidate">		
				<table style="height: 350px;">
					<tr>
						<td>Tariff Name*</td>
						<td><input id="editNodeText" class="k-textbox" 
						 required="required" validationMessage="Tariff Name  Is Required"
						/></td>
					</tr>
					
					<tr>
						<td>Tariff Code*</td>
						<td><input  id="tariffcodeu"
							name="tariffcodeu " class="k-textbox"  required="required" validationMessage="Tariff Code  Is Required"/></td>
					</tr>					
					<tr>
					<td>From-Date*</td>
					<td> <kendo:datePicker format="yyyy/MM/dd " name="validFromu" id="validFromu" value="${today}" 
					change="startChangeu"	 required="required" validationMessage="From-Date  Is Required"></kendo:datePicker><td>
					</td>
					</tr>
					
					<tr>
					<td>To-Date*</td>
					<td> <kendo:datePicker format="yyyy/MM/dd " name="todateu" id="todateu" value="${today}" 
					change="endChangeu"	 required="required"  validationMessage="To-Date  Is Required"></kendo:datePicker></td>
					</tr>					
					<tr>
					<td>Tariff Node Type*</td>
					<td><kendo:dropDownList name="tariffnodetypeu" id="tariffnodetypeu" dataTextField="name"
								dataValueField="value"  optionLabel="Tariff Node Type"	required="required" validationMessage="Tariff Node Type  Is Required">
								<kendo:dataSource data="${tariffNodetype}"></kendo:dataSource>
							</kendo:dropDownList></td>
					<tr>
						
					<tr >
						<td>Tariff Description*</td>
					<td style="vertical-align:middle;">	<textarea  id="tariffdescriptionu"
							name="tariffdescriptionu " class="k-textbox" required="required" validationMessage="Tariff Description  Is Required"></textarea></td>
					</tr>
					<tr>
						<tr>
						<td>Status*</td>
						<td><kendo:dropDownList name="statusu" id="statusu" dataTextField="name"
								dataValueField="value"  optionLabel="Status" required="required" validationMessage="Status  Is Required">
								<kendo:dataSource data="${status}" ></kendo:dataSource>
							</kendo:dropDownList></td>
					</tr>
					<tr>				
						<td></td>
						<td>
							<!-- <button class="k-button" id="editNodeToSelected" style="margin:5px;width:79%;" >Update
								Tariff</button> -->
								<input type="submit"  id="editNodeToSelected" class="buttonS bLightBlue" value="Update Tariff"
						style="margin:5px;width:79%;" />
						</td>
					
					</tr>
					
					
				</table>
</form>
				<br>
			</div>
		</div>
	


<script>

/*for kendo drop down list valion ignore  */

function startChangeu() {
        var endPicker = $("#todateu").data("kendoDatePicker"),
            startDate = this.value();

        if (startDate) {
            startDate = new Date(startDate);
            startDate.setDate(startDate.getDate() + 1);
            endPicker.min(startDate);
        }
    }

    function endChangeu() {
        var startPicker = $("#validFromu").data("kendoDatePicker"),
            endDate = this.value();

        if (endDate) {
            endDate = new Date(endDate);
            endDate.setDate(endDate.getDate() - 1);
            startPicker.max(endDate);
        }
    }
    var validator = $("#updateform").kendoValidator().data("kendoValidator");
    $("#updateform").submit(function(e) {
    	e.preventDefault();
    });   


     $("#updateform").kendoValidator({
         messages: {
             // defines a message for the 'custom' validation rule
             custom: "Please enter valid value for my custom rule",

             // overrides the built-in message for the required rule
             required: "My custom required message",
                 minlen:1
             // overrides the built-in message for the email rule
             // with a custom function that returns the actual message
             
         },
         rules: {
        	 customRule1: function(input){
                 // all of the input must have a value
                 return $.trim(input.val()) !== "";
               },
         }
    });

     var updateValidator = $("#updateform").kendoValidator().data("kendoValidator");
     $("#editNodeToSelected").on("click", function() {
    	    if (updateValidator.validate()) {
    	        
    	    	updateform();
    	    }
    	});
    		 	
    		 		
     
     function updateform(){	
    	 var treeview = $("#treeview").data("kendoTreeView");
    	 var selectedNode = treeview.select();

			var nodename = $("#editNodeText").val();

			if (selectedNode.length == 0) {
				alert("Select the Node");
				selectedNode = null;
			} else {
				if (nodename != "") {

					var tariffnodetypeu = $("input[name=tariffnodetypeu]").val();
					var statusu = $("input[name=statusu]").val();
					var tariffdescriptionu = $(
							"#tariffdescriptionu").val();
					// var tariffdescription=$('#tariffdescription').val();
					var tariffcodeu=$("#tariffcodeu").val();
					var validFromu=$("#validFromu").val();
					
					var todateu=$("#todateu").val();
					
					var treeHierarchy = texts.join('>');
					$
							.ajax({
								type : "POST",
								url : "./gasTariff/update",
								data : {

									gasTariffId : nodeid,
									gastariffName : nodename,
									gastreeHierarchy : treeHierarchy,
									gasStatus : statusu,
									gastariffDescription : tariffdescriptionu,
									gastariffCode        :  tariffcodeu,
									gastariffNodetype: tariffnodetypeu,
									validFrom:       validFromu,
									validTo:  todateu
								},
								success : function(response) {
									alert("Updated Successfully");

									$('.k-state-selected')
											.text(nodename);
									$(".k-state-selected").append( "<br>"+statusu);											
							
									var divObj = $('#updateperm');
							 		
									 divObj.kendoWindow({
								            autoOpen: false,
								            modal: true,
								            draggable: true,
								            width: 470,
								            height: 'auto',
								            resizable: false,
								            draggable: false,
								            bgiframe: true,
								            title : "Update Tariff"
								        }).data("kendoWindow").center().close();;
									 divObj.kendoWindow('close');
								
								}
							});

				} else {
					alert("Please enter the required details");
				}
			}

     }
/*  */
$.validator.setDefaults({ ignore: '' });

/* validaion for update tariff */


	var texts = "";
	var nodeid = "";
	var nn = "";
	function onSelect(e){ 


		nodeid = $('.k-state-hover').find('#syed').val();
		
	
		
		
	$.ajax({
		type : "GET",
		url : "./gasTariffMaster/getDetails/"+nodeid,
		success : function(response)
		{
		
			
			$('#editNodeText').val(response.gastariffName);
			$("#tariffcodeu").val(response.gastariffCode);
			$("#tariffdescriptionu").val(response.gastariffDescription);
			
			 var dropdownlist1 = $("#tariffnodetypeu").data("kendoDropDownList");
				
				 dropdownlist1.search(response.gastariffNodetype); 
				 
				 var drop = $("#statusu").data("kendoDropDownList");
					
				 drop.search(response.gasStatus);  
				 
			var olddatefrom=	 $('#validFromu').val(response.validFrom).val();			
			var newvalidf=olddatefrom.split("-").join("/")+" ";
			$('#validFromu').val(newvalidf);
			
			var olddateto= $('#todateu').val(response.validTo).val();
				var newvalidto=olddateto.split("-").join("/")+" ";
								
				$('#todateu').val(newvalidto);
				
			
		}

		});
		
	
	
	var nn = $('.k-state-hover').html();
	var spli = nn.split(" <input");


	var kitems = $(e.node).add(
			$(e.node).parentsUntil('.k-treeview', '.k-item'));
	texts = $.map(kitems, function(kitem) {
		return $(kitem).find('>div span.k-in').text();
	});
	}
	
		
	
		 	function changeCheck()
			{
			//	alert("click");
				var test = $('input:checked').length ? $(
		         'input:checked').val() : '';
		        // alert(test);
		         if(test === 'on')
		        	 {
		        	 	$('#current').hide();
		        	 	$('#all').show();
		        	 	
		        	 }
		         else{
		        	 $('#current').show();
		        	 	$('#all').hide();
		        	 	
		        	 }
		         } 
		 
		 	function adddiv(){
		 		var result=securityCheckForActionsForStatus("./Tariff/Gas/TariffMaster/createTariffMaster"); 
		 		var treeview = $("#treeview").data("kendoTreeView");
		 		if(result=="success"){ 
					var selectedNode = treeview
					.select();

			if (selectedNode.length == 0) {
				alert("Select Tariff Node to Add Data");
				selectedNode = null;
			}else{
				 var openadd=$('#taskperm');
		 		$( "#addtariff" )

				.click(function() {
					
				});	 
		 		 var openadd=$('#taskperm');
			 		
					openadd.kendoWindow({
					            autoOpen: false,
					            modal: true,
					            draggable: true,
					            width: 350,
					            height: 'auto',
					            resizable: false,
					            draggable: false,
					            bgiframe: true,
					            title : "Add Tariff"
					        }).data("kendoWindow").center().open();
					
					        openadd.kendoWindow("open");
			}}}
		 	
		 	function updatediv(){
		 		var result=securityCheckForActionsForStatus("./Tariff/Gas/TariffMaster/updateTariffMaster"); 
		 		var treeview = $("#treeview").data("kendoTreeView");
		 		if(result=="success"){ 
		 			var selectedNode = treeview.select();
					if(selectedNode.length == 0){
						alert("Select Tariff Node To Update Data")
						
					}else{
						var divObj = $('#updateperm');
				 		
				 		divObj.kendoWindow({
				            autoOpen: false,
				            modal: true,
				            draggable: true,
				            width: 470,
				            height: 'auto',
				            resizable: false,
				            draggable: false,
				            bgiframe: true,
				            title : "Update Tariff"
				        }).data("kendoWindow").center().open();;
					 divObj.kendoWindow('open');
					}
				
				}
		 	}
		 	
	$(document)
			.ready(
					function() {
                   
							 var $all = $("#all").hide(),
					        $current = $("#current");

					    $("#checkbox1").click(function() {
					       $all[this.checked ? "show" : "hide"]();
					       $current[this.checked ? "hide" : "show"]();
					    });
					    
					
					        
						var treeview = $("#treeview").data("kendoTreeView"), handleTextBox = function(
								callback) {
							return function(e) {
								if (e.type != "keypress"
										|| kendo.keys.ENTER == e.keyCode) {
									callback(e);
								}
							};
						};

						$("#removeNode")
								.click(
										function() {
											
									 		var result=securityCheckForActionsForStatus("./Tariff/Water/TariffMaster/deleteTariffMaster"); 
											if(result=="success"){ 
												var cof = confirm("Are you Sure to Delete Tariff");
												var selectedNode = treeview.select();

												if (selectedNode.length == 0) {
													alert("Select the Node to Delete");
													selectedNode = null;
												} else {
													  if(cof){
													$
															.ajax({
																type : "POST",
																url : "./gastariff/delete",
																dataType:"text",
																data : {
																	gasTariffId : nodeid
																},
																success : function(
																		response) {
																	alert(response);
																	if (response == 'Deleted Successfully!!!') {
																		treeview
																				.remove(selectedNode);
																	}
																}
															});

												}
												}
											}
										});

						$("#expandAllNodes").click(function() {
							treeview.expand(".k-item");
						});

						$("#collapseAllNodes").click(function() {
							treeview.collapse(".k-item");
						});

					});

	 
  function parse (response) 
 {   
              $.each(response, function (idx, elem) {
              if (elem.validTo && typeof elem.validTo === "string") {
                  elem.validTo = kendo.parseDate(elem.validTo, "yyyy-MM-ddTHH:mm:ss.fffZ");
              }
              if (elem.validFrom && typeof elem.validFrom === "string") {
                  elem.validFrom = kendo.parseDate(elem.validFrom, "yyyy-MM-ddTHH:mm:ss.fffZ");
              }
              
          });
          return response;
  }  
</script>
<style>
td {
	border: 0 none;
	font-size: 100%;
	margin: 0;
	outline: 0 none;
	padding: 0;
	vertical-align: middle;
}

div[id^='elRateSlab_']>div>table td:nth-child(4) {
	text-align: right;
}

span.k-tooltip {
   border-width: 2px;
    display: list-item;
    padding: -1px 5px 8px 25px;
    position: absolute;
    margin-top: -7px;
}

tr[class='k-footer-template'] {
	text-align: right;
}
</style>











