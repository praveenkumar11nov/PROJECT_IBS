<%@include file="/common/taglibs.jsp"%>
	
<c:url value="/btTariff/read" var="transportbroadteleReadUrl" />
<%-- <c:url value="/tariff/read" var="transportReadUrlLoc" /> --%>

<c:url value="/tariff/readall" var="transportReadUrlall" />

<div style="padding-left: 50px" class="fluid">

	<div class="widget grid8" >
		<div class="whead">

			<h6>Broadband Telecommunications Tariff Master</h6>
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
		<kendo:treeView name="treeview" dataTextField="broadTeleTariffName"
			select="onSelect" 
			template=" #: item.broadTeleTariffName #  <br>#:item.status # <input type='hidden' id='syed' class='#: item.broadTeleTariffName ##: item.wtTariffId #' value='#: item.broadTeleTariffId#'/>">
			
								
			<kendo:dataSource>
				<kendo:dataSource-transport>
					<kendo:dataSource-transport-read url="${transportbroadteleReadUrl}"
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
					<kendo:dataSource-schema-hierarchical-model id="broadTeleTariffId"
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
				<form style="margin-top: 6px;" id="addform" method="post">
				<table>
					<tr>
						<td>Tariff Name*</td>
						<td><input id="appendNodeText" name="appendNodeText" class="k-textbox" placeholder="Enter TariffName" style="margin:5px;width:79%"
						class="validate[required]" required="required" onchange="getTariffName()"/>
						<span id="msgId" style="display:none;color:red"></span>
                        			
						</td>					    
					</tr>				
				
					<tr>					
						<td style="vertical-align:middle;">Tariff Code*</td>
						<td><input type="text" id="tariffCode" name="tariffCode" placeholder="Enter TariffCode" class="k-textbox"  style="margin:5px;width:79%;"
						 class="validate[required]"  required="required"
						 /></td>
					</tr>
					
					
					
					
					<tr>
					<td>From-Date*</td>
					<td> <kendo:datePicker format="yyyy/MM/dd  "  name="validFrom" id="validFrom" parse="parse()" value="${today}"  style="margin: 5px; width: 79%;  padding: 0px 5px;"
					change="startChange"
					 required="required" class="validate[required]">
					</kendo:datePicker><td>
					</td>
					</tr>
					
					<tr>
					<td>To-Date*</td>
					<td> <kendo:datePicker format="yyyy/MM/dd  " name="validTo" id="validTo" parse="parse()" value="${today}" style="margin:5px;width:79%; padding: 0px 5px;" 
					 change="endChange"					 
					 required="required" class="validate[required]">
					</kendo:datePicker></td>
					</tr>
					
					<tr>
					<td>Tariff Node Type*</td>
					<td><kendo:dropDownList name="tariffNodetype" id="tariffNodetype" dataTextField="name"
								dataValueField="value" style="margin:5px;width:79%;"
								required="required" class="validate[required]" optionLabel="Tariff Node Type"   >
								<kendo:dataSource data="${tariffNodetype}" ></kendo:dataSource>
							</kendo:dropDownList></td>
					<tr> 
						
					<tr >
						<td>Tariff Description*</td>
					<td style="vertical-align:middle;">	<textarea  id="tariffDescription"
							name="tariffDescription" class="k-textbox" style="margin:5px;width:79%;"
							 required="required" class="validate[required]"
							></textarea></td>
					
					</tr>
					<tr>
						<td>Status*</td>
						<td><kendo:dropDownList name="status" id="status" dataTextField="name"
								dataValueField="value" style="margin:5px;width:79%;"
								required="required" class="validate[required]" optionLabel="Status">
								
								<kendo:dataSource data="${status}" ></kendo:dataSource>
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

$("#addform").validate({
    rules: {
    	"appendNodeText": {
            required: true,
            
            data:{
            	appendNodeTex:function(){
            		return$("#tariffName").val();
            	}	
            },
            tariffnameCheck:true,
        },  
    	
        "tariffCode": {
            required: true,
            //email: true
        },  
       "appendNodeText":{
    	   required: true,
       },
       "validFrom":{
    	   required:true,
       }, 
       "validTo":{
    	   required:true,
       }, 
		"tariffDescription":{
			 required:true,	
		},
		"status":{
			 required:true,
		},
		"tariffNodetype":{
			 required:true,
		}
    },
    messages: {			
    	tariffCode :{
			required : "TariffCode is Required"
		},
		appendNodeText:{
			required : "TariffName is Required"	,
			regex:"Please Enter Tariff Name in Capital Letter",
			tariffnameCheck:   "TariffName is aleready there"
		},
		validFrom:{
			required : "ValidFrom Date is to be selected"		
		},
		validTo:{
			required:"ValidTo Date is to be selected"
		},
		tariffDescription:{
			 required : "Tariff Description is reqired"
		},
		status:{
			required	:"Status is required"
		},
		tariffNodetype:{
			required	:"Tariffnodetype is required"
		},
		
		},
		
		
		
		
    //perform an AJAX post to ajax.php
    submitHandler: function() {
        $.post('./tariff/create', 
        $('form#addform').serialize() , 
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
    }
});
 

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
		
			
				<table>
					<tr>
						<td>Tariff Name*</td>
						<td><input id="editNodeText" class="k-textbox" style="margin:5px;width:79%"
						class="validate[required]" required="required"
						/></td>
					</tr>
					
					<tr>
						<td>Tariff Code*</td>
						<td><input type="text" id="tariffcodeu"
							name="tariffcodeu " class="k-textbox" 
							class="validate[required]" required="required"
							style="margin:5px;width:79%" /></td>
					</tr>
					
					
					
					<tr>
					<td>From-Date*</td>
					<td> <kendo:datePicker format="yyyy/MM/dd " name="validFromu" id="validFromu" value="${today}" style="margin:5px;width:79%;padding: 0px 5px;"
					change="startChangeu"
					class="validate[required]" required="required"
					></kendo:datePicker><td>
					</td>
					</tr>
					
					<tr>
					<td>To-Date*</td>
					<td> <kendo:datePicker format="yyyy/MM/dd " name="todateu" id="todateu" value="${today}" style="margin:5px;width:79%;padding: 0px 5px;"
					change="endChangeu"
					class="validate[required]" required="required"
					></kendo:datePicker></td>
					</tr>
					
					<tr>
					<td>Tariff Node Type*</td>
					<td><kendo:dropDownList name="tariffnodetypeu" id="tariffnodetypeu" dataTextField="name"
								dataValueField="value" style="margin:5px;width:79%;" optionLabel="Tariff Node Type"
								class="validate[required]" required="required"
								>
								<kendo:dataSource data="${tariffNodetype}" ></kendo:dataSource>
							</kendo:dropDownList></td>
					<tr>
						
					<tr >
						<td>Tariff Description*</td>
					<td style="vertical-align:middle;">	<textarea  id="tariffdescriptionu"
							name="tariffdescriptionu " class="k-textbox" style="margin:5px;width:79%;"
							class="validate[required]" required="required"
							></textarea></td>
					
					</tr>
					<tr>
						<tr>
						<td>Status*</td>
						<td><kendo:dropDownList name="statusu" id="statusu" dataTextField="name"
								dataValueField="value" style="margin:5px;width:79%" optionLabel="Status"
								class="validate[required]" required="required"
								
								>
								<kendo:dataSource data="${status}"></kendo:dataSource>
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


/*  */
$.validator.setDefaults({ ignore: '' });

/* validaion for update tariff */
$("#updateform").validate({
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
    submitHandler: function() {
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
    }
});	
	


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
		url : "./btTariffMaster/getDetails/"+nodeid,
		success : function(response)
		{
		
			
			$('#editNodeText').val(response.broadTeleTariffName);
			$("#tariffcodeu").val(response.broadTeleTariffCode);
			$("#tariffdescriptionu").val(response.broadTeleTariffDescription);
			
			 var dropdownlist1 = $("#tariffnodetypeu").data("kendoDropDownList");
				
				 dropdownlist1.search(response.broadTeleTariffNodetype); 
				 
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
		 		var openadd=$('#taskperm');
		 		$( "#addtariff" )

				.click(function() {
					/*  $('#taskperm').is(':data(dialog)') 
					 $( "#taskperm" ).dialog( "open" );	
					

					 });


				$( "#taskperm" ).dialog({
					 autoOpen: false,
					 height: 500,
					 width: 500,
					 modal: true,
				 draggable: false,
				  resizable: false,*/ 
					 
				});	 
				
				
				   
				
					
				openadd.dialog({
				            autoOpen: false,
				            modal: true,
				            draggable: true,
				            width: 470,
				            height: 'auto',
				            resizable: false,
				            draggable: false,
				            bgiframe: true
				        });
				
				openadd.dialog('open');
		 	
		 	}
		 	
		 	function updatediv(){
		 		var divObj = $('#updateperm');
		 		$( "#updatetariff" )
				 
				 .click(function() {
					 
					 
					/*  $('#updateperm').is(':data(dialog)'); 
				 $( "#updateperm" ).dialog( "open" );
				 });

					 divObj.dialog('destroy');

			$( "#updateperm" ).dialog({
				 autoOpen: false,
				 height: 500,
				 width: 500,
				 modal: true,
				 draggable: false,
				  resizable: false, */
		
			
			});	
			 
			
			
				 
				 divObj.dialog({
			            autoOpen: false,
			            modal: true,
			            draggable: true,
			            width: 470,
			            height: 'auto',
			            resizable: false,
			            draggable: false,
			            bgiframe: true
			        });
			   
				// divObj.dialog('destroy');   
				 divObj.dialog('open');
			
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
															url : "./bttariff/delete",
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
										});

						$("#expandAllNodes").click(function() {
							treeview.expand(".k-item");
						});

						$("#collapseAllNodes").click(function() {
							treeview.collapse(".k-item");
						});

						var append = handleTextBox(function(e) {

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
									$
											.ajax({
												type : "POST",
												url : "./btTariff/create",
												data : {
													broadTeleTariffId : nodeid,
													broadTeleTariffName : nodename,
													broadTeleTreeHierarchy : treeHierarchy,
													status : status,
													broadTeleTariffDescription : tariffdescription,
													broadTeleTariffCode        :  tariffcode,
													broadTeleTariffNodetype: tariffnodetype,
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
													  broadTeleTariffId : response,
														broadTeleTariffName : nodename,
														broadTeleTreeHierarchy : treeHierarchy,
														status : status,
														broadTeleTariffDescription : tariffdescription,
														broadTeleTariffCode        :  tariffcode,
														broadTeleTariffNodetype: tariffnodetype,
														validFrom:       validFrom,
														validTo:  validTo 
														
													}, selectedNode);    

													 $('.' + nodename).val(
															response);   
											
								
													$("#tariffDescription").val(" ");
													$("#tariffCode").val(" ");
													$("#appendNodeText").val(" "); 
													
													
													//$('.'+nodename).removeClass(nodename).addClass(nodename+response);
													//$('.'+nodename).addClass(response.trim()); 

												}
											});

								} else {
									alert("Please enter the required details");
								}
							}
						});

						$("#appendNodeToSelected").click(append);
						$("#appendNodeText").keypress(append);

						var edit = handleTextBox(function(e) {

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
												url : "./btTariff/update",
												data : {

													broadTeleTariffId : nodeid,
													broadTeleTariffName : nodename,
													broadTeleTreeHierarchy : treeHierarchy,
													status : statusu,
													broadTeleTariffDescription: tariffdescriptionu,
													broadTeleTariffCode        :  tariffcodeu,
													broadTeleTariffNodetype: tariffnodetypeu,
													validFrom:       validFromu,
													validTo:  todateu
												},
												success : function(response) {
													alert("Updated Successfully");

													$('.k-state-selected')
															.text(nodename);
													$(".k-state-selected").append( "<br>"+tariffcodeu+"<br>"+tariffdescriptionu+"<br>"+statusu);											
											
													var divObj = $('#updateperm');
											 		
													 divObj.dialog({
												            autoOpen: false,
												            modal: true,
												            draggable: true,
												            width: 470,
												            height: 'auto',
												            resizable: false,
												            draggable: false,
												            bgiframe: true
												        });
												   
											
													 divObj.dialog('close');
															
															
															
															
															
												}
											});

								} else {
									alert("Please enter the required details");
								}
							}
						});

						$("#editNodeToSelected").click(edit);
						$("#editNodeText").keypress(edit);

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
<style scoped>
.configuration .k-textbox {
	width: 50px;
}

.demo-section {
	width: 200px;
	margin: 0 auto;
}

#get {
	margin-top: 25px;
}
</style>











<style scoped>
.configuration .k-textbox {
	width: 10px
}

.demo-section {
	margin: 0 auto;
	border: 0px solid #CDCDCD;
	box-shadow: 0 1px 0 #FFFFFF;
	position: relative;
	text-shadow: 0 1px #FFFFFF;
	background-image: none,
		linear-gradient(to bottom, #FFFFFF 0px, #E6E6E6 100%);
	background-position: 50% 50%;
	font-weight: normal;
	width: 95%
}

 #taskperm {
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
#updateperm {
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
 .k-validFrom 
        {
            width: 200px;
            height:90px;
        }
.k-validTo 
        {
            width: 200px;
            height:90px;
        }
        
   .k-validFromu 
        {
            width: 200px;
            height:90px;
            
        }
        .k-todateu 
        {
            width: 200px;
            height:90px;
        }
             

#dropperam{
height: 100px;
 }

#emptyDiv {
	width: 1px;
}
.test{vertical-align: middle;}

.ui-dialog input[type="text"], .ui-dialog input[type="password"], .ui-dialog textarea {
    background: none repeat scroll 0 0 white;
    border: 1px solid #DDDDDD;
    box-shadow: 0 0 0 2px #F4F4F4;
    box-sizing: border-box;
    color: #656565;
    display: block;
    font-family: Arial,Helvetica,sans-serif;
    font-size: 11px;
    margin: 10px auto;
    padding: 0 5px;
    width: 100%;
}



</style>