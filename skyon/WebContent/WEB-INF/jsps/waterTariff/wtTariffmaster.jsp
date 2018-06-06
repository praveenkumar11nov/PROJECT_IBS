<%@taglib prefix="kendo" uri="/WEB-INF/kendo.tld"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
	
<c:url value="/wtTariff/read" var="transportReadUrl" />
<%-- <c:url value="/tariff/read" var="transportReadUrlLoc" /> --%>

<c:url value="/tariff/readall" var="transportReadUrlall" />

<div style="padding-left: 50px" class="fluid">

	<div class="widget grid8" >
		<div class="whead">

			<h6> Tariff Master</h6>
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
		<kendo:treeView name="treeview" dataTextField="tariffName"
			select="onSelect" 
			template=" #: item.tariffName# <br>#:item.status # <input type='hidden' id='syed' class='#: item.tariffName ##: item.wtTariffId #' value='#: item.wtTariffId#'/>">
			
								
			<kendo:dataSource>
				<kendo:dataSource-transport>
					<kendo:dataSource-transport-read url="${transportReadUrl}" type="POST" contentType="application/json" />
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
						<td><input id="appendNodeText" name="tariffName" class="k-textbox" placeholder="Enter TariffName" 
						 required="required" onchange="getTariffName()" min="1" pattern=""/>
						<span id="msgId" style="display:none;color:red"></span>
                        			
						</td>					    
					</tr>				
				
					<tr>					
						<td style="vertical-align:middle;">Tariff Code*</td>
						<td><input id="tariffCode" name="tariffCode" placeholder="Enter TariffCode" class="k-textbox"  
						   required="required"
						 /></td>
					</tr>
					
					<tr>
					<td>From-Date*</td>
					<td> <kendo:datePicker format="yyyy/MM/dd  "  name="validFrom" id="validFrom" parse="parse()" value="${today}" 	change="startChange"
					 required="required" >
					</kendo:datePicker><td>
					</td>
					</tr>
										
					<tr>
					<td>To-Date*</td>
					<td> <kendo:datePicker format="yyyy/MM/dd  " name="validTo" id="validTo" parse="parse()" value="${today}"  change="endChange"  required="required" class="validate[required]">
					</kendo:datePicker></td>
					</tr>
					
					<tr>
					<td>Tariff Node Type*</td>
					<td><kendo:dropDownList name="tariffNodetype" id="tariffNodetype" dataTextField="name"
								dataValueField="value" 	required="required" optionLabel="Tariff Node Type">
								<kendo:dataSource data="${tariffNodetype}" ></kendo:dataSource>
							</kendo:dropDownList></td>
					<tr> 
						
					<tr >
						<td>Tariff Description*</td>
					<td style="vertical-align:middle;">	<textarea  id="tariffDescription"
							name="tariffDescription" class="k-textbox" 
							 required="required"></textarea></td>
					
					</tr>
					<tr>
						<td>Status*</td>
						<td><kendo:dropDownList name="status" id="status" dataTextField="name"
								dataValueField="value" 
								required="required"  optionLabel="Status">								
								<kendo:dataSource data="${status}" ></kendo:dataSource>
							</kendo:dropDownList></td>
					</tr>
						<tr>
						<td></td>
						<td>
						<input type="submit"  id="appendNodeToSelected" class="buttonS bLightBlue" value="Save Tariff"/>
						<div id='loader' style="display: none"> <img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;</div>
						
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
				<form style="margin-top: 6px;" id="updateform" method="post">
		
			
				<table style="height:350px;">
					<tr>
						<td>Tariff Name*</td>
						<td><input id="editNodeText" class="k-textbox" 
						class="validate[required]" required="required"/></td>
					</tr>
					
					<tr>
						<td>Tariff Code*</td>
						<td><input  id="tariffcodeu"
							name="tariffcodeu " class="k-textbox" 
							class="validate[required]" required="required"/></td>
					</tr>				
					<tr>
					<td>From-Date*</td>
					<td> <kendo:datePicker format="yyyy/MM/dd " name="validFromu" id="validFromu" value="${today}" 	change="startChangeu"
					class="validate[required]" required="required"></kendo:datePicker><td>
					</td>
					</tr>
					
					<tr>
					<td>To-Date*</td>
					<td> <kendo:datePicker format="yyyy/MM/dd " name="todateu" id="todateu" value="${today}" 
					change="endChangeu"	class="validate[required]" required="required"></kendo:datePicker></td>
					</tr>
					
					<tr>
					<td>Tariff Node Type*</td>
					<td><kendo:dropDownList name="tariffnodetypeu" id="tariffnodetypeu" dataTextField="name"
								dataValueField="value"  optionLabel="Tariff Node Type" class="validate[required]" required="required">
								<kendo:dataSource data="${tariffNodetype}" ></kendo:dataSource>
							</kendo:dropDownList></td>
					<tr>
						
					<tr >
						<td>Tariff Description*</td>
					<td style="vertical-align:middle;">	<textarea  id="tariffdescriptionu"
							name="tariffdescriptionu " class="k-textbox" class="validate[required]" required="required"></textarea></td>
					
					</tr>
					<tr>
						<tr>
						<td>Status*</td>
						<td><kendo:dropDownList name="statusu" id="statusu" dataTextField="name"
								dataValueField="value"  optionLabel="Status" class="validate[required]" required="required">
								<kendo:dataSource data="${status}"></kendo:dataSource>
							</kendo:dropDownList></td>
					</tr>
					<tr>				
						<td></td>
						<td>
							<!-- <button class="k-button" id="editNodeToSelected" style="margin:5px;width:79%;" >Update
								Tariff</button> -->
								<input type="submit"  id="editNodeToSelected" class="buttonS bLightBlue" value="Update Tariff"/>
								<div id='loader1' style="display: none"> <img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;</div>
								
						</td>
					
					</tr>					
				</table></form>
				
			</div>
		</div>
	


<script>

/*for kendo drop down list valion ignore  */
$("#addform").submit(function(e) {
		e.preventDefault();
	});
	
$("#updateform").submit(function(e) {
	e.preventDefault();
});
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


/*  */
$.validator.setDefaults({ ignore: '' });

/* validaion for update tariff */
 var updateValidator= $("#updateform").validate({
    rules: {
    	"editNodeText": {
            required: true,
            
        },  
    	
        "tariffcodeu": {
            required: true,
            //email: true
        },  
       
       "validFromu":{

    	   required:true,
       }, 
       "todateu":{
    	   required:true,
       }, 
		"tariffdescriptionu":{
			 required:true,	
		},
		"statusu":{
			 required:true,
		},
		"tariffnodetype":{
			 required:true,
		}
    },
    messages: {			
    	tariffcodeu :{
			required : "TariffCode is Required"
		},
		editNodeText:{
			required : "TariffName is Required"	,
			
		},
		validFromu:{
			required : "ValidFrom Date is to be selected"		
		},
		todateu:{
			required:"ValidTo Date is to be selected"
		},
		tariffdescriptionu:{
			 required : "Tariff Description is reqired"
		},
		status:{
			required	:"Status is required"
		},
		tariffnodetypeu:{
			required	:"Tariffnodetype is required"
		},
		
		},
		
		
		
    //perform an AJAX post to ajax.php
    /* submitHandler: function() {
        ( 
        $('form#updatefrom').serialize() , 
        function(data)            {
           
            $("#alertsBox").html("");
			$("#alertsBox").html(data);
			$("#alertsBox").dialog({
				modal: true,
				draggable: false,
				resizable: false,
				buttons: {
					"Close": function() {
						$( this ).dialog( "close" );
					}
				}
			}); 
            $('#myform')[0].reset();
        }, "text");
    } */
});	
	
	
 $("#editNodeToSelected").on("click", function() { 
	
		    if ($("#updateform").valid()) {
		        update();
		    }
	});
function update(){
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

		    $('#editNodeToSelected').hide();
		 	$('#loader1').show();
			
			$
					.ajax({
						type : "POST",
						url : "./wtTariff/update",
						data : {

							wtTariffId : nodeid,
							tariffName : nodename,
							treeHierarchy : treeHierarchy,
							status : statusu,
							tariffDescription : tariffdescriptionu,
							tariffCode        :  tariffcodeu,
							tariffNodetype: tariffnodetypeu,
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
						     
						     $('#editNodeToSelected').show();
							 $('#loader1').hide();
								
						}
					});

		} else {
			alert("Please enter the required details");
		}
	}
}

	var texts = "";
	var nodeid = "";
	var nn = "";
	function onSelect(e){ 


		nodeid = $('.k-state-hover').find('#syed').val();
		
		/* 
			$('span[id^=hideit_]').hide();
			nodeid = $('.k-state-hover').find('#syed').val();
			
			nn = $('.k-state-hover').html();
			var kitems = $(e.node).add(
					$(e.node).parentsUntil('.k-treeview',
							'.k-item'));
			texts = $.map(kitems, function(kitem) {
				return $(kitem).find('>div span.k-in').text();
			});	
			$('#hideit_'+nodeid).show();
		 */
		
		
	$.ajax({
		type : "GET",
		url : "./wtTariffMaster/getDetails/"+nodeid,
		success : function(response)
		{
		
			
			$('#editNodeText').val(response.tariffName);
			$("#tariffcodeu").val(response.tariffCode);
			$("#tariffdescriptionu").val(response.tariffDescription);
			
			 var dropdownlist1 = $("#tariffnodetypeu").data("kendoDropDownList");
				
				 dropdownlist1.search(response.tariffNodetype); 
				 
				 var drop = $("#statusu").data("kendoDropDownList");
					
				 drop.search(response.status);  
				 
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
		 		var result=securityCheckForActionsForStatus("./Tariff/Water/TariffMaster/createTariffMaster"); 
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
				/* openadd.dialog({
				            autoOpen: false,
				            modal: true,
				            draggable: true,
				            width: 350,
				            height: 'auto',
				            resizable: false,
				            draggable: false,
				            bgiframe: true
				        });
				
				openadd.dialog('open');  */
				
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
		
			}
					
				} 
		 	}
		 	
		 	function updatediv(){
		 		var result=securityCheckForActionsForStatus("./Tariff/Water/TariffMaster/updateTariffMaster"); 
		 		var treeview = $("#treeview").data("kendoTreeView");
		 		if(result=="success"){ 
		 			var selectedNode = treeview.select();
					if(selectedNode.length == 0){
						alert("Select Tariff Node To Update Data")
						
					}else{
						var divObj = $('#updateperm');
				 		$( "#updatetariff" )
						 
						 .click(function() {
						
					
					});	
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
						
						
						
						/*  var openadd=$( "#addtariff" )

						.click(function() {

							 $( "#taskperm" ).dialog( "open" );	
							

							 });


						$( "#taskperm" ).dialog({
							 autoOpen: false,
							 height: 500,
							 width: 500,
							 modal: true,
						 draggable: false,
						  resizable: false, 
							 
						});	
							
						
						
						
						$( "#updatetariff" )
						 
						 .click(function() {
						 $( "#updateperm" ).dialog( "open" );
						 });



					$( "#updateperm" ).dialog({
						 autoOpen: false,
						 height: 500,
						 width: 500,
						 modal: true,
						 draggable: false,
						  resizable: false,
					});	
						 
						  */

					
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
												  
												
												var selectedNode = treeview
														.select();

												if (selectedNode.length == 0) {
													alert("Select the Node to Delete");
													selectedNode = null;
												} else {
													  if(cof){
													$
															.ajax({
																type : "POST",
																url : "./wttariff/delete",
																dataType:"text",
																data : {
																	wtTariffId : nodeid
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

						

						 $("#addform").kendoValidator({
					         messages: {
					             // defines a message for the 'custom' validation rule
					             custom: "Please enter valid value for my custom rule",

					             
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
							        
							       addTariff();
							    }
							});
                        
						function addTariff(){
							var selectedNode = treeview.select();

							var nodename = $("#appendNodeText").val();

							if (selectedNode.length == 0) {
								alert("Select the Node");
								selectedNode = null;
							} else {

								if (nodename != "") {

									var status = $("input[name=status]").val();
									var tariffdescription = $("#tariffDescription").val();
											
									var tariffcode=$("#tariffCode").val();
									
									var tariffnodetype = $("input[name=tariffNodetype]").val();

									var validFrom=$("#validFrom").val();
									var validTo=$("#validTo").val();

									var treeHierarchy = texts.join('>');
									

								    $('#appendNodeToSelected').hide();
								 	$('#loader').show();
									
									$
											.ajax({
												type : "POST",
												url : "./wtTariff/create",
												data : {
													wtTariffId : nodeid,
													tariffName : nodename,
													treeHierarchy : treeHierarchy,
													status : status,
													tariffDescription : tariffdescription,
													tariffCode        :  tariffcode,
													tariffNodetype: tariffnodetype,
													validFrom:       validFrom,
													validTo:  validTo 
													 },
												
												success : function(response) {
													alert("Added Successfully !!!");
													//alert(response);
													
														/* $('.k-state-selected')
															.text(nodename);
														treeview.append( "<br>"+tariffcode+"<br>"+tariffdescription+"<br>"+status);	 */										
 
												  treeview.append({
													  wtTariffId : response,
														tariffName : nodename,
														treeHierarchy : treeHierarchy,
														status : status,
														tariffDescription : tariffdescription,
														tariffCode        :  tariffcode,
														tariffNodetype: tariffnodetype,
														validFrom:       validFrom,
														validTo:  validTo 
														
													}, selectedNode);    

													 $('.' + nodename).val(
															response);   
											
								
													$("#tariffDescription").val(" ");
													$("#tariffCode").val(" ");
													$("#appendNodeText").val(" "); 
													

												    $('#appendNodeToSelected').show();
												 	$('#loader').hide();
													
													
													//$('.'+nodename).removeClass(nodename).addClass(nodename+response);
													//$('.'+nodename).addClass(response.trim()); 

												}
											});

								} else {
									alert("Please enter the required details");
								}
							}
						}
						
	/* 					var edit = handleTextBox(function(e) {

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
												url : "./wtTariff/update",
												data : {

													wtTariffId : nodeid,
													tariffName : nodename,
													treeHierarchy : treeHierarchy,
													status : statusu,
													tariffDescription : tariffdescriptionu,
													tariffCode        :  tariffcodeu,
													tariffNodetype: tariffnodetypeu,
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
						});
*/
						//$("#editNodeToSelected").click(edit);
						//$("#editNodeText").keypress(edit);

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