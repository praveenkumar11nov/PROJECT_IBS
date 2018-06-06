<%@include file="/common/taglibs.jsp"%>


<c:url value="/asset/category/read" var="transportReadUrl" />

<div style="padding-left: 100px" class="fluid">

<div class="widget grid6">
	<div class="whead">
			<h6>Asset Category Hierarchy</h6>
			<div class="titleOpt">
				<a href="#" data-toggle="dropdown"><span class="icos-cog3"></span></a>
				<ul class="dropdown-menu pull-right">
					<li><a href="#" class="" id="expandAllNodes">Expand All</a></li>
					<li><a href="#" class="" id="collapseAllNodes">Collapse	All</a></li>
				</ul>
			</div>
	</div>	
	<br>
	<kendo:treeView name="treeview" dataTextField="assetcatText" select="onSelect" template=" #: item.assetcatText # <input type='hidden' id='syed' class='#: item.assetcatText ##: item.assetcatId#' value='#: item.assetcatId#'/><span id='hideit_#: item.assetcatId#' style='display:none'><a href='\\#' class='icos-add' onclick='addCategory()'></a><a href='\\#' class='icos-pencil' onClick='editCategory()' ></a><a href='\\#' class='icos-trash' onClick='deleteCategory()' ></a><a href='\\#'  onClick='viewAssets()' title='View Associated Assets' > <img id='eye' src='./resources/images/eye.png' alt='view associated Assets'/></a><a href='\\#'  onClick='viewAllAssets()' title='View All Associated Assets' ><img id='eye' src='./resources/images/eye.png' alt='view All associated Assets'/></a></span>">
     <kendo:dataSource >
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
             <kendo:dataSource-schema-hierarchical-model id="assetcatId" hasChildren="hasChilds" />
         </kendo:dataSource-schema>
     </kendo:dataSource>
 </kendo:treeView>
 <br>
</div>

<div id="emptyDiv" class="widget grid4"></div>

<div id="taskperm" class="widget grid4" style="text-align: center;display: none;">
	<div class="whead" id="whead" title="Assets" style="text-align: center;">
	</div>
	<div class="whead" id="whead2" title="All Assets" style="text-align: center;">
	</div>
</div>
</div>

<div id="window1" style="display: none;" title="Add/Update Asset Category">
 <input id="hiddenid" type="hidden"/>
	<table style="width:100%; text-align: right;">
		<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
		<tr><td> Category *</td><td align="center"> <input id="categoryName" class="k-textbox" required="required"/> </td></tr>
		<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
		<tr style="border-top: 1px solid #D7D7D7"><td>&nbsp;</td><td>&nbsp;</td></tr>
		<tr><td></td><td align="center"><button class="k-button" onClick = "submitForm()"><span class="k-icon k-i-pencil"></span>Add/Update</button> </td></tr>
		<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
	</table>
</div>

<div id="gridCatIndi" style="max-height: 500px; max-width: 1000px; width: 1000px; height:450px; overflow: auto; "></div>
<div id="gridCat" style="max-height: 500px; max-width: 1000px; width: 1000px; height:450px; overflow: auto; "></div>

<script>
	var texts = "";
	var nodeid = "";	
	var treeview ="";
	var addOrEdit = "0";
	var catWindow = "" ;
/* 	var wnd1 = $("#window1").kendoWindow({
		title : "Asset Category",
	    visible:false,
	    resizable: true,
	    width : "350px",
	    modal:true
	}).data("kendoWindow");
	var nn = ""; */
	
	
	function onSelect(e) {
		$('span[id^=hideit_]').hide();
		nodeid = $('.k-state-hover').find('#syed').val();
		var nn = $('.k-state-hover').html();
		var spli = nn.split(" <input");
	
		//$('#editNodeText').val(spli[0].trim());

		 var kitems = $(e.node).add(
				$(e.node).parentsUntil('.k-treeview',
						'.k-item'));
		texts = $.map(kitems, function(kitem) {
			return $(kitem).find('>div span.k-in').text();
		}); 
		$('#hideit_'+nodeid).show();
		
	}

	function addCategory()
	{
		securityCheckForActions("./asset/cat/createButton");
		addOrEdit = "0";
/* 
		$('#window1').html("");
		 $('#window1').html(contactList); */
		 $('#window1').dialog({
            	width: 475,
            	position: 'center',
				modal : true,
			}); 
		 $('#categoryName').val("");
		//wnd1.center().open();
	}
	
	
	$("#expandAllNodes").click(function() {
		treeview.expand(".k-item");
	});

	$("#collapseAllNodes").click(function() {
		treeview.collapse(".k-item");
	});
	function editCategory(){
		securityCheckForActions("./asset/cat/updateButton");
		addOrEdit = "1";
		 $('#window1').dialog({
         	width: 475,
         	position: 'center',
				modal : true,
			}); 
		 
			$.ajax({
				type : "POST",
				url : "./asset/cat/getdetailoncatid",
				data : {
					assetcatId : nodeid
				},
				success : function(response) {
					$('#categoryName').val(response.assetcatText);
				}
			});
			
	}
	
	
	function deleteCategory()
	{
		securityCheckForActions("./asset/cat/deleteButton");
		var cof = confirm("Are U Sure?");
		
		if(cof){
		var selectedNode = treeview
		.select();
		if (selectedNode.length == 0) {
		alert("Select the Node to Delete");
		selectedNode = null;
		} else {
			
			if(nodeid>2){
			
		$.ajax({
				type : "POST",
				url : "./asset/cat/delete",
				data : {
					assetcatId : nodeid
				},
				dataType: "text",
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
			else{
				alert("Parent category can't be deleted");
			}
		}
		}
		
	}
	
	function viewAssets(){
		
		
		 $("#gridCatIndi").kendoGrid({
             dataSource: {
                 type: "json",
                 transport: {
                     read: "./asset/assetsoncatselect/"+nodeid,
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
		
		 catWindow = $("#gridCatIndi").kendoWindow(
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
				url : "./asset/assetsoncatselect/"+nodeid,
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
		
		
		
		
		 $("#gridCat").kendoGrid({
             dataSource: {
                 type: "json",
                 transport: {
                     read: "./asset/allassetsoncatselect/"+nodeid,
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
		
		 catWindow = $("#gridCat").kendoWindow(
					{
						visible : false,
						resizable : false,
						modal : true,
						actions : ["Minimize",
								"Maximize", "Close" ],
						title : "Assets"
					}).data("kendoWindow");

			catWindow.center().open();
		
		
		/*  $('#grid').dialog({
	         	width: 750,
	         	position: 'center',
					modal : true,
				}); 
		 $.ajax({
				type : "GET",
				url : "./asset/allassetsoncatselect/"+nodeid,
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
	
	function submitForm(){
		
		treeview.expand(".k-item");
		
		var action ="";
		if(addOrEdit == "0"){
			action = "add";
		}else{
			action = "update";
		}
		
		/* var errorStr ="Please select the following - \n\n"; */
		var errorStr ="";
		var flag = 0;
		var selectedNode = treeview.select();
		var categoryName =  $('#categoryName').val().trim();		
		var treeHierarchy = texts.join('>');
		
		if(categoryName == ''){
			flag =1;
			errorStr+="Category is required \n";
		}else{
			var re = /^[a-zA-Z0-9 ]{0,45}$/;
			if (!re.test(categoryName)){
				flag =1;
				errorStr+="Category does not allow special characters except spaces \n";
			}
		}
		
		if(flag == 1){
			alert(errorStr);
			return false;
		}
		
		$.ajax({
			type : "POST",
			url : "./asset/cat/"+action,
			dataType: "text",
			data : {
				assetcatId : nodeid,
				nodename : categoryName,
				treeHierarchy : treeHierarchy,
			},
			success : function(response) {
			$('#window1').dialog('close');
			 if(action == "add"){
				 	alert("Added Successfully !!!");
					treeview.append({
						assetcatText: categoryName,
						assetcatId: response
				}, selectedNode);
				$('.'+categoryName).val(response);	
				$("#categoryName").val("");
				
			 }else{
				alert(response);
				$('.k-state-selected').text(categoryName);
				$('#hideit_'+nodeid).show();	
			
				$(".k-state-selected").append("<span id='hideit_'"+nodeid+"' style='display: inline'><a class='icos-add' onclick='addCategory()' href='#'></a><a class='icos-pencil' onclick='editCategory()' href='#'></a><a class='icos-trash' onclick='deleteCategory()' href='#'></a><a href='\\#'  onClick='viewAssets()' title='View Associated Assets' > <img id='eye' src='./resources/images/eye.png' alt='view associated Assets'/></a><a href='\\#'  onClick='viewAllAssets()' title='View All Associated Assets' ><img id='eye' src='./resources/images/eye.png' alt='view All associated Assets'/></a></span>");
			
			}
		}
		});
	}
	function cancelForm(){
		$('#window1').dialog('close');
	}
	
	$(document).ready(function() {
		 treeview = $("#treeview").data("kendoTreeView"), handleTextBox = function(callback) {
		return function(e) {
		if (e.type != "keypress"|| kendo.keys.ENTER == e.keyCode) {
		callback(e);
		}
		}
		};
	});
	/* var append = handleTextBox(function(e) {
		var selectedNode = treeview.select();
		var nodename = $("#categoryName").val();
								
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
		}); */
	
</script>
<style scoped>
.configuration .k-textbox {
	width: 50px;
}

.demo-section {
	width: 200px;
	margin: 0 auto;
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

#emptyDiv {
	width: 1px;
}
 img[id=eye] {
    border: 0 none;
    margin-left: 14px;
    vertical-align: middle;
    height: 20px;
    width: 20px
}
</style>