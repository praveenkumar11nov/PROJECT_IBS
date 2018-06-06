
<%@include file="/common/taglibs.jsp"%>

<c:url value="/filerepository/readtree" var="transportReadUrl" />

<div style="padding-left: 100px" class="fluid">

	<div class="widget grid6">
		<div class="whead">

			<h6>Document Categories</h6>
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
		<kendo:treeView name="treeview" dataTextField="frGroupName"
			spriteCssClass="folder" select="onSelect"
			template="<img src='resources/images/folder.png' height='20px' style='vertical-align:middle' /> &nbsp;&nbsp;#: item.frGroupName #  <input type='hidden' id='ravi' class='#: item.frGroupName ##: item.frGroupId#' value='#: item.frGroupId#'/><br> <span id='hideit_#: item.frGroupId#' style='display:none'> <a href='\\#' onClick='return createDocument()' class='icos-add'  ></a><a href='\\#' onClick='return updateDocument()' class='icos-pencil'></a><a href='\\#' onClick='return deleteDocument()' class='icos-trash'></a></span>&nbsp;<span id='viewDocumentId_#:item.frGroupId#' style='color:blue; display:none;'><a href='\\#'    onClick='return viewDocument();' ><img src='resources/images/eye.png' height='20px' /></a></span>">

			<kendo:dataSource requestStart="OnRequestStart">
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
					<kendo:dataSource-schema-hierarchical-model id="frGroupId"
						hasChildren="hasChilds" />
				</kendo:dataSource-schema>
			</kendo:dataSource>
		</kendo:treeView>
		<br>
	</div>


	<div id="emptyDiv" class="widget grid2"></div>

	<div id="uploadDialog"></div>

	<div id="treeViewDiv" hidden="true" style="width: 40%;">

		<table style="width: 100%; text-align: right;">
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td style="vertical-align: middle;"><b>Group Name * &nbsp;</b></td>
				<td style="vertical-align: middle;" align="center"><input
					id="frGroupNameId" name="frGroupNameId" onkeyup="Validate(this)"
					style="height: 30px; width: 200px;" placeholder="Enter Group Name"
					class="k-textbox" required="required" /></td>
			</tr>

			<tr>
				<td style="vertical-align: middle;"><b>Description * &nbsp;</b></td>
				<td style="vertical-align: middle;" align="center"><textarea
						id="frDescriptionId" onkeyup="ValidateDescription(this)"
						placeholder="Description Of Group" name="textarea" cols=""
						style="height: 50px; width: 200px;"></textarea></td>
			</tr>
		</table>
	</div>


	<div id="alertsBox" title="Alert"></div>

</div>
<script type="text/javascript">
	var texts = "";
	var nodeid = "";
	var naam = "";
	var addOrEdit = "0";

	function Validate(txt) {
		txt.value = txt.value.replace(/[^a-zA-Z0-9]+[^a-zA-Z 0-9_\n\r]+/g, '');
	}

	function ValidateDescription(txt) {
		txt.value = txt.value.replace(/[^a-zA-Z]+[^a-zA-Z 0-9_.\n\r]+/g, '');
	}

	function onSelect(e) {
		$('span[id^=hideit_]').hide();
		$('span[id^=viewDocumentId_]').hide();
		nodeid = $('.k-state-hover').find('#ravi').val();

		var nn = $('.k-state-hover').html();
		var spli = nn.split(" <input");
		$('#editNodeText').val(spli[0].trim());

		var kitems = $(e.node).add(
				$(e.node).parentsUntil('.k-treeview', '.k-item'));
		texts = $.map(kitems, function(kitem) {
			return $(kitem).find('>div span.k-in').text();
		});
		$('#hideit_' + nodeid).show();
		$('#viewDocumentId_' + nodeid).show();
	}

	$("#expandAllNodes").click(function() {
		treeview.expand(".k-item");
	});

	$("#collapseAllNodes").click(function() {
		treeview.collapse(".k-item");
	});

	/* -------------------------------------ceating the file node------------------------------------------- */
	/* ------------------------------------------------------------------------------------------------------*/

	/* -------------------------------------updating the file node------------------------------------------- */
	/* ------------------------------------------------------------------------------------------------------*/

	var frlist = "";
	var icon = "";
	function viewDocument(e) {

		securityCheckForActions("./filerepositorymanagement/filerepository/viewDocumentButton");

		$
				.ajax({
					type : "POST",
					url : "./filerepositroy/tree/viewdocument",
					dataType : "JSON",
					data : {
						frGroupId : nodeid,
					},
					success : function(response) {
						var documentlist = "<table frame=box bgcolor=white width=450px><tr bgcolor=#0A7AC2>";
						documentlist = documentlist
								+ "<th><font color=white><b>Document Name/Type</b></font></th><th><font color=white><b>View Document</b></font></th></tr>";
						if (response.length > 0) {
							for (var s = 0, len = response.length; s < len; s++) {
								frlist = response[s];
								if (frlist.docType == "pdf") {
									icon = "resources/images/pdficon.png";
								} else if (frlist.docType == "png") {
									icon = "resources/images/pngicon.png";
								} else if (frlist.docType == "jpg") {
									icon = "resources/images/jpgicon.png";
								} else if (frlist.docType == "jpeg") {
									icon = "resources/images/jpegicon.png";
								} else if (frlist.docType == "xlsx") {
									icon = "resources/images/xlsxicon.png";
								} else if( frlist.docType == "null") {
									icon = "resources/images/jpgicon.png";
								}
								if( frlist.docImage == "null" ){
									documentlist += "<tr><td>&nbsp;&nbsp;No Record To Display </td><td></td><td></td></tr>";
								}
								else{
								documentlist += "<tr><td align=center><img src="+icon+ " height='28px' /><b>"
										+ frlist.docName
										+ "</b></td><td align=center>"
										+ "<a href='./downloaddocument/"+frlist.frId+"' target='_blank'><font color=green><b>View</b></font></a><br></td></tr>";
								}
								}
						} else {
							documentlist += "<tr><td>&nbsp;&nbsp;No Record To Display </td><td></td><td></td></tr>";
						}
						documentlist = documentlist + "</table>";

						$('#uploadDialog').html("");
						$('#uploadDialog').html(documentlist);
						$('#uploadDialog').dialog({
							width : 475,
							modal : true,
							buttons : {
								"Close" : function() {
									$(this).dialog('close');
								}
							}
						});
					}

				});
		return false;

	}

	var treeview = "";
	$(document).ready(
			function() {
				treeview = $("#treeview").data("kendoTreeView"),
						handleTextBox = function(callback) {
							return function(e) {
								if (e.type != "keypress"
										|| kendo.keys.ENTER == e.keyCode) {
									callback(e);
								}
							}
						};
			});

	
	function OnRequestStart(e){
		    $('.k-grid-update').hide();
	        $('.k-edit-buttons')
	                .append(
	                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
	        $('.k-grid-cancel').hide();
	}
	function createDocument(e) {

		securityCheckForActions("./filerepositorymanagement/filerepository/createButton");
		treeview.expand(".k-item");

		$('input[name="frGroupNameId"]').prop('readonly', false);
		var selectedNode = treeview.select();
		$('#treeViewDiv').show();
		$('#treeViewDiv')
				.dialog(
						{
							width : 350,
							title : "Add Document Category",
							modal : true,
							align:"top",
							buttons : {
								"Add" : function() {
									var errorStr = "Please select the following - \n";
									var flag = 0;
									var frGroupName = $('#frGroupNameId').val();
									var frgroupdescription = $(
											'#frDescriptionId').val();
									var treeHierarchy = texts.join('>');
									if (frGroupName == '') {
										flag = 1;
										errorStr += " > File Repository Group Name  is required \n";
									}

									if (frgroupdescription == '') {
										flag = 1;
										errorStr += " >File Repository Group description is required \n";
									}

									if (flag == 1) {
										alert(errorStr);
										return false;
									}
									$
											.ajax({
												type : "POST",
												dataType : 'text',
												data : {
													frGroupId : nodeid,
													nodename : frGroupName,
													treeHierarchy : treeHierarchy,
													nodedescription : frgroupdescription
												},
												url : "./filerepository/create/node",
												success : function(response) {

													if (response == 'File group Already Exist !!!') {
														alert(response);

													} else {
														alert("File Repository Added Successfully !!!");
														$("#frDescriptionId")
																.val("");
														$("#frGroupNameId")
																.val("");
														treeview
																.append(
																		{
																			frGroupName : frGroupName,
																			frGroupId : response
																		},
																		selectedNode);
														$('.' + frGroupName)
																.val(response);

													}

												}
											});
									$(this).dialog('close');

								},
								"Close" : function() {
									$("#frDescriptionId").val("");
									$("#frGroupNameId").val("");
									$(this).dialog('close');
								}
							}
						});

	}

	function updateDocument() {

		securityCheckForActions("./filerepositorymanagement/filerepository/updateButton");

		$('input[name="frGroupNameId"]').prop('readonly', true);
		var selectedNode = treeview.select();
		$.ajax({
			type : "GET",
			url : "./filerepository/getDetails/" + nodeid,
			success : function(response) {
				$("#frDescriptionId").val(response.frGroupDescription);
				$("#frGroupNameId").val(response.frGroupName);
			}

		});

		$('#treeViewDiv').show();
		$('#treeViewDiv')
				.dialog(
						{
							width : 350,
							title : "Update Document Category",
							modal : true,
							buttons : {
								"Update" : function() {
									var flag = 0;
									var frGroupName = $('#frGroupNameId').val();
									var frgroupdescription = $(
											'#frDescriptionId').val();
									// var arr=texts.split(" ");
									var treeHierarchy = texts.join('>');
									if (frGroupName == '') {
										flag = 1;
										errorStr += " > File Repository Group Name  is required \n";
									}

									if (frgroupdescription == '') {
										flag = 1;
										errorStr += " >File Repository Group description is required \n";
									}

									if (flag == 1) {
										alert(errorStr);
										return false;
									}
									$
											.ajax({
												type : "POST",
												dataType : 'text',
												data : {
													frGroupId : nodeid,
													nodename : frGroupName,
													treeHierarchy : treeHierarchy,
													nodedescription : frgroupdescription
												},
												url : "./filerepository/update/node",
												success : function(response) {

													if (response == 'File group Already Exist !!!') {
														alert(response);
													} else {
														alert(response);
														//var tree = $("#treeview").data("kendoTreeView");
														//tree.dataSource.read();
													}
													$("#frDescriptionId").val(
															"");
													$("#frGroupNameId").val("");
												}
											});
									$(this).dialog('close');
								},
								"Close" : function() {
									$("#frDescriptionId").val("");
									$("#frGroupNameId").val("");
									$(this).dialog('close');

								}
							}
						});

	}

	function deleteDocument() {

		securityCheckForActions("./filerepositorymanagement/filerepository/deleteButton");

		var selectedNode = treeview.select();
		if (confirm("Are you sure to delete the node?")) {
			if (selectedNode.length == 0) {
				alert("Select the Node to Delete");
				selectedNode = null;
			} else {
				$.ajax({
					type : "POST",
					url : "./filerepository/delete/node",
					dataType : 'text',
					data : {
						frGroupId : nodeid
					},
					success : function(response) {
						alert(response);
						if (response == 'Deleted Sucessfully !!!') {
							treeview.remove(selectedNode);
						}

					}
				});
			}
		}
		return false;
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

#treeview .k-sprite {
	background-image: url("../resources/images/icons/folder.png");
}

.folder {
	background-position: 0 -16px;
}

/* ###########################treeview image################################# */
#treeview .k-sprite {
	background-image: url("../resources/images/b3.png");
}

.rootfolder {
	background-position: 0 0;
}

.folder {
	background-position: 0 -16px;
}

.pdf {
	background-position: 0 -32px;
}

.html {
	background-position: 0 -48px;
}

.image {
	background-position: 0 -64px;
}
/* ###########################treeview image################################# */
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

#taskperm { /
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
</style>

<style>
	div.ui-dialog {position:fixed;overflow:"auto";} 
</style>
