<%@include file="/common/taglibs.jsp"%>


<c:url value="/asset/location/read" var="transportReadUrl" />

<div style="padding-left: 100px" class="fluid"> 

<div class="widget grid6">
<div class="whead">

			<h6>Asset Location Hierarchy</h6>
			<div class="titleOpt">
				<a href="#" data-toggle="dropdown"><span class="icos-cog3"></span></a>
				<ul class="dropdown-menu pull-right">
					<li><a href="#" class="" id="expandAllNodes">Expand All</a></li>
					<li><a href="#" class="" id="collapseAllNodes">Collapse
							All</a></li>
				</ul>
			</div>
		</div>
		<br>
	 <kendo:treeView name="treeview" dataTextField="assetlocText" select="onSelect" template=" #: item.assetlocText # <input type='hidden' id='syed' class='#: item.assetlocText ##: item.assetlocId#' value='#: item.assetlocId#'/><span id='hideit_#: item.assetlocId#' style='display:none'><a href='\\#' class='icos-add' onclick='addLocation()'></a><a href='\\#' class='icos-pencil' onClick='editLocation()' ></a><a href='\\#' class='icos-trash' onClick='deleteLocation()' ></a><a href='\\#'  onClick='viewAssets()' title='View Associated Assets' > <img id='eye' src='./resources/images/eye.png' alt='view associated Assets'/></a><a href='\\#'  onClick='viewAllAssets()' title='View All Associated Assets' > <img id='eye' src='./resources/images/eye.png' alt='view all associated Assets'/></a></span>">
     <kendo:dataSource>
         <kendo:dataSource-transport>
             <kendo:dataSource-transport-read url="${transportReadUrl}" type="POST"  contentType="application/json"/>  
             <kendo:dataSource-transport-parameterMap>
             	<script>
              	function parameterMap(options,type) {
              		return JSON.stringify(options);
              	}
             	</script>
             </kendo:dataSource-transport-parameterMap>         
         </kendo:dataSource-transport>
         <kendo:dataSource-schema>
             <kendo:dataSource-schema-hierarchical-model id="assetlocId" hasChildren="hasChilds" />
         </kendo:dataSource-schema>
     </kendo:dataSource>
 </kendo:treeView>
 <br>
</div>

<div id="taskperm" class="widget grid4" style="text-align: center;display: none;">
	<div class="whead" id="whead" title="Assets" style="text-align: center;">
	</div>
</div>

	
</div>

<div id="window1" style="display: none;" title="Add/Update Asset Location">

 <input id="hiddenid" type="hidden"/>
	<table style="width:100%; text-align: right;">
		<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
		<tr><td>Location Name *</td><td align="center"> <input id="locationName" class="k-textbox" required="required" maxlength="50"/> </td></tr>
		<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
		<tr><td>Location Type *</td><td align="center">  
			 <input id="locationType" style="width: 140px; height: 28px; margin-top: 2px; margin-bottom: 3px;" maxlength="20"/>
		 </td></tr>
		<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
		<tr><td style="vertical-align: middle;">Address *</td><td align="center" style="vertical-align: middle;"> <textarea maxlength="500" id="locationAddress"  style="width: 130px; height: 40px"></textarea> </td></tr>
		<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
		<tr><td>Contact Details *</td><td align="center"> <input id="locationContactDetails" class="k-textbox" required="required" maxlength="50"/> </td></tr>
		<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
		<tr><td>Location Geo Code *</td><td align="center"> <input id="locationGeoCode" class="k-textbox" required="required" maxlength="20"/> </td></tr>
		<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
		<tr style="border-top: 1px solid #D7D7D7"><td>&nbsp;</td><td>&nbsp;</td></tr>
		<tr><td></td><td align="center"><button class="k-button" onClick = "submitForm()"><span class="k-icon k-i-pencil"></span>Add/Update</button> </td></tr>
		<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
	</table>

</div>

<div id="gridLocIndi" style="max-height: 500px; max-width: 1000px; width: 1000px; height:450px; overflow: auto; "></div>
<div id="gridLoc" style="max-height: 500px; max-width: 1000px; width: 1000px; height:450px; overflow: auto; "></div>

<script>
	var texts = "";
	var nodeid = "";	
	var treeview ="";
	var addOrEdit = "0";

	var nn = "";
	function onSelect(e) {
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
		
		
		
	}

	function viewAssets(){
		 $("#gridLocIndi").kendoGrid({
            dataSource: {
                type: "json",
                transport: {
                    read:  "./asset/assetsonlocselect/"+nodeid,
                },
                pageSize: 10
            },
            groupable: true,
            sortable: true,
            pageable: {
                refresh: true,
                pageSizes: true,
                buttonCount: 5
            },
            columns: [{
                field: "assetName",
                title: "Asset Name",
               
            },
            {
                field: "assetType",
                title: "Asset Type",
               
            },
            {
                field: "assetCondition",
                title: "Asset Condition",
               
            },
            {
                field: "assetTag",
                title: "Asset Tag",
               
            },
            {
                field: "assetStatus",
                title: "Asset Status",
            
            }]
        });
		
		 catWindow = $("#gridLocIndi").kendoWindow(
					{
						visible : false,
						resizable : false,
						modal : true,
						actions : ["Minimize",
								"Maximize", "Close" ],
						title : "Assets"
					}).data("kendoWindow");

			catWindow.center().open();
		
		
		/*  $('#whead').dialog({
	         	width: 750,
	         	position: 'center',
					modal : true,
				}); 
		 $.ajax({
				type : "GET",
				url : "./asset/assetsonlocselect/"+nodeid,
				success : function(
						response) {
					 $('#whead').html("");
					var assetlist = "<table class='tDefault' cellspacing='0' cellpadding='0' width='100%'>"
						+"<thead><tr><td>Asset Name</td>"
							+"<td>Asset Type</td>"
							+"<td>Asset Condtion</td>"
							+"<td>Asset Tag</td>"
							+"<td>Asset Status</td></tr><thead>";
					 for ( var s = 0, len = response.length; s < len; ++s) {
		                 	var assets = response[s];
		                 	assetlist += 	"<tbody><tr><td>"+assets.assetName +"</td>"
		                 						+"<td>"+assets.assetType +"</td>"
		                 						+"<td>"+assets.assetCondition +"</td>"
		                 						+"<td>"+assets.assetTag +"</td>"
		                 						+"<td>"+assets.assetStatus +"</td></tr><tbody>";
		             }
					 assetlist+="</table>";
					 $('#whead').append(assetlist);
				}
			}); */
	}
	
	function viewAllAssets(){

		 $("#gridLoc").kendoGrid({
            dataSource: {
                type: "json",
                transport: {
                    read: "./asset/allassetsonlocselect/"+nodeid,
                },
                pageSize: 10
            },
            groupable: true,
            sortable: true,
            pageable: {
                refresh: true,
                pageSizes: true,
                buttonCount: 5
            },
            columns: [{
                field: "assetName",
                title: "Asset Name",
               
            },
            {
                field: "assetType",
                title: "Asset Type",
               
            },
            {
                field: "assetCondition",
                title: "Asset Condition",
               
            },
            {
                field: "assetTag",
                title: "Asset Tag",
               
            },
            {
                field: "assetStatus",
                title: "Asset Status",
            
            }]
        });
		
		 catWindow = $("#gridLoc").kendoWindow(
					{
						visible : false,
						resizable : false,
						modal : true,
						actions : ["Minimize",
								"Maximize", "Close" ],
						title : "Assets"
					}).data("kendoWindow");

			catWindow.center().open();
		
		/* 
		 $('#whead').dialog({
	         	width: 750,
	         	position: 'center',
					modal : true,
				}); 
		 $.ajax({
				type : "GET",
				url : "./asset/allassetsonlocselect/"+nodeid,
				success : function(
						response) {
					 $('#whead').html("");
					var assetlist = "<table class='tDefault' cellspacing='0' cellpadding='0' width='100%'>"
						+"<thead><tr><td>Asset Name</td>"
							+"<td>Asset Type</td>"
							+"<td>Asset Condtion</td>"
							+"<td>Asset Tag</td>"
							+"<td>Asset Status</td></tr><thead>";
					 for ( var s = 0, len = response.length; s < len; ++s) {
		                 	var assets = response[s];
		                 	assetlist += 	"<tbody><tr><td>"+assets.assetName +"</td>"
		                 						+"<td>"+assets.assetType +"</td>"
		                 						+"<td>"+assets.assetCondition +"</td>"
		                 						+"<td>"+assets.assetTag +"</td>"
		                 						+"<td>"+assets.assetStatus +"</td></tr><tbody>";
		             }
					 assetlist+="</table>";
					 $('#whead').append(assetlist);
				}
			}); */
	}
	
	
	function addLocation()
	{
		securityCheckForActions("./asset/loc/createButton");
		$('#locationName').val("");
		$('#locationType').val("");
		$('#locationAddress').val("");
		$('#locationContactDetails').val("");
		$('#locationGeoCode').val("");
		
		$("#locationType").kendoComboBox({
            /*  dataTextField: "locationType",
             dataValueField: "locationType", */
             placeholder: "Select Location Type...",
             dataSource: {
                 transport: {
                     read: {
                         url: "./asset/loc/getlocationtype",
                         contentType: "application/json",
                         type : "GET"
                     }
                 }
             }
         });
			
		addOrEdit = "0";

		 $('#window1').dialog({
         	width: 475,
         	position: 'center',
				modal : true,
			}); 
		 
		//wnd1.center().open();
	}
		
	function editLocation()
	{
	
		securityCheckForActions("./asset/loc/updateButton");
		/* $('input[type="text"]').css({"height" : "15200"}); */
			 
		addOrEdit = "1";
		$.ajax({
			type : "POST",
			url : "./asset/loc/getdetailonlocid",
			data : {
				assetlocId : nodeid
			},
			success : function(response) {
			
				$('#locationName').val(response.assetlocText);
				$('#locationType').val(response.locationType);
				$('#locationAddress').val(response.locationAddress);
				$('#locationContactDetails').val(response.contactDetails);;
				$('#locationGeoCode').val(response.geoCode);
				
				$("#locationType").kendoComboBox({
		            /*  dataTextField: "locationType",
		             dataValueField: "locationType", */
		             placeholder: "Select Location Type...",
		             dataSource: {
		                 transport: {
		                     read: {
		                         url: "./asset/loc/getlocationtype",
		                         contentType: "application/json",
		                         type : "GET"
		                     }
		                 }
		             }
		         });
				
				
				 $('#window1').dialog({
			         	width: 475,
			         	position: 'center',
							modal : true,
						}); 
				 $( "#cncl" ).click(function() {
						$( "#window1" ).dialog( "open" );	
				}); 
				 
				 $('#locationName').val(response.assetlocText);
					$('#locationType').val(response.locationType);
					$('#locationAddress').val(response.locationAddress);
					$('#locationContactDetails').val(response.contactDetails);;
					$('#locationGeoCode').val(response.geoCode);
			}
		});
		
		var spli = nn.split(" <input");
		// wnd1.center().open();
		$('#editNodeText').val(spli[0].trim());
		
		
		
		 //wnd1.center().open();
	}
	
	function deleteLocation()
	{
		securityCheckForActions("./asset/loc/deleteButton");
		var cof = confirm("Are U Sure?");
		
		if(cof){
		var selectedNode = treeview.select();
		if (selectedNode.length == 0) {
			alert("Select the Node to Delete");
			selectedNode = null;
		} else {
			if(nodeid>2){
			$.ajax({
				type : "POST",
				url : "./asset/loc/delete",
				data : {
					assetlocId : nodeid
				},
				dataType: "text",
				success : function(response) {
					alert(response);
					if (response == 'Deleted Successfully!!!') {
						treeview.remove(selectedNode);
					}
				}
			});
			}
			else{
				alert("Root Location can't be deleted");
			}
		}
		}
	}
	
	function submitForm(){
		treeview.expand(".k-item");
		
		var action ="";
		if(addOrEdit == "0"){
			action = "add";
		}else{
			action = "update";
		}
		
		var errorStr ="Plz select the following - \n";
		var flag = 0;
		var selectedNode = treeview.select();
		var locationName =  $('#locationName').val().trim();
		var locationType = $('#locationType').val().trim();
		var locationAddress = $('#locationAddress').val().trim();
		var locationContactDetails = $('#locationContactDetails').val().trim();
		var locationGeoCode = $('#locationGeoCode').val().trim();
		var treeHierarchy = texts.join('>');
		
		if(locationName == ''){
			flag =1;
			errorStr+=" > Location is required \n";
		}else{
			var re = /^[_A-Za-z0-9.'-, ]{0,40}$/;
			if (!re.test(locationName)){
				flag =1;
				errorStr+=" > Invalid Location Name \n";
			}
		}
		
		if(locationType == ''){
			flag =1;
			errorStr+=" > Location Type is required \n";
		}else{
			var re = /^[_A-Za-z0-9.'-, ]{0,40}$/;
			if (!re.test(locationType)){
				flag =1;
				errorStr+=" > Invalid Location Type \n";
			}
		}
		
		if(locationAddress == ''){
			flag =1;
			errorStr+=" > Location Address is required \n";
		}
		
		if(locationContactDetails == ''){
			flag =1;
			errorStr+=" > Location Contact Details is required \n";
		}
		
		if(locationGeoCode == ''){
			flag =1;
			errorStr+=" > Location Geo Code is required \n";
		}else{
			var re = /^[_A-Za-z0-9.'-]{0,40}$/;
			if (!re.test(locationGeoCode)){
				flag =1;
				errorStr+=" > Invalid Location GeoCode \n";
			}
		}
		
		if(flag == 1){
			alert(errorStr);
			return false;
		}
		
		$.ajax({
			type : "POST",
			url : "./asset/loc/"+action,
			dataType: "text",
			data : {
				assetlocId : nodeid,
				nodename : locationName,
				treeHierarchy : treeHierarchy,
				locationType : locationType,
				locationAddress : locationAddress,
				locationContactDetails : locationContactDetails,
				locationGeoCode : locationGeoCode
			},
			success : function(response) {
				$('#window1').dialog('close');
			 	if(action == "add"){
				 	alert("Added Successfully !!!");
					treeview.append({
						assetlocText: locationName,
						assetlocId: response
					}, selectedNode);
					$('.'+locationName).val(response);						
			 	}
				 else{
					alert(response);
					$('.k-state-selected').text(locationName);
					$('#hideit_'+nodeid).show();	
				
					$(".k-state-selected").append("<span id='hideit_'"+nodeid+"' style='display: inline'><a class='icos-add' onclick='addLocation()' href='#'></a><a class='icos-pencil' onclick='editLocation()' href='#'></a><a class='icos-trash' onclick='deleteLocation()' href='#'></a><a href='\\#'  onClick='viewAssets()' title='View Associated Assets' > <img id='eye' src='./resources/images/eye.png' alt='view associated Assets'/></a><a href='\\#'  onClick='viewAllAssets()' title='View All Associated Assets' > <img id='eye' src='./resources/images/eye.png' alt='view all associated Assets'/></a></span>");
				//$('.'+nodename).removeClass(nodename).addClass(nodename+response);
				//$('.'+nodename).addClass(response.trim()); 
				}
			}
		});
	}
	function cancelForm(){
		alert("close");
		$('#window1').dialog('close');
	}
	
	$(document).ready(function() {				
		treeview = $("#treeview").data("kendoTreeView"), handleTextBox = function(callback) {
			return function(e) {
				if (e.type != "keypress" || kendo.keys.ENTER == e.keyCode) {
									callback(e);
				}
			};
	};
						
	$("#removeNode").click(function() {
		var selectedNode = treeview.select();
		if (selectedNode.length == 0) {
			alert("Select the Node to Delete");
			selectedNode = null;
		} else {
			$.ajax({
				type : "POST",
				url : "./asset/loc/delete",
				data : {
					assetlocId : nodeid
				},
				success : function(response) {
					alert(response);
					if (response == 'Deleted Successfully!!!') {
						treeview.remove(selectedNode);
					}
				}
			});
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
			var treeHierarchy = texts.join('>');
			$.ajax({
				type : "POST",
				url : "./asset/loc/add",
				data : {
					assetlocId : nodeid,
					nodename : nodename,
					treeHierarchy : treeHierarchy
				},
				success : function(response) {alert("Added Successfully !!!");
						treeview.append({
						assetlocText: nodename,
						assetlocId: response
					}, selectedNode);
					$('.'+nodename).val(response);	
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
	// passing a falsy value as the second append() parameter
	// will append the new node to the root group
	if (selectedNode.length == 0) {
		alert("Select the Node");
		selectedNode = null;
	} else {
		if (nodename != "") {
			/* 	var ne = texts.join(',');
			var str = ne.split(",");
			var strspli = str[str.length - 1].split("="); */

			var treeHierarchy = texts.join('>');
			$.ajax({
				type : "POST",
				url : "./asset/loc/update",
				data : {
					assetlocId : nodeid,
					assetlocText : nodename,
					treeHierarchy : treeHierarchy
				},
				success : function(response) {
					alert("Updated Successfully");
					$('.k-state-selected').text(nodename);
						/* treeview.dataSource.read();
						$('#treeview').data('kendoTreeView').refresh(); */
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
	
	
</script>
<style scoped>
.configuration .k-textbox {
	width: 50px;
}

.demo-section {
	width: 200px;
	margin: 0 auto;
}

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

#emptyDiv {
	width: 1px;
}


element.style {
    width: 100%;
}
input[type="text"]{
height: auto;
}

img[id='eye'] {
    border: 0 none;
    margin-left: 14px;
    vertical-align: middle;
    height: 20px;
    width: 20px
}

td{
	vertical-align: middle;
}
</style>