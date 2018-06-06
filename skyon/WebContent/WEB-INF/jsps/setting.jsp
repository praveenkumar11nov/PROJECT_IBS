 <%@include file="/common/taglibs.jsp"%>


<c:url value="/setting/ajaxtree" var="transportReadUrl" />

<div style="padding-left: 100px" class="fluid">




	<div class="widget grid6" style="max-height: 500px; overflow: scroll;">
		<div class="whead">

			<h6>Product Hierarchy</h6>
			<div class="titleOpt">
				<a href="#" data-toggle="dropdown"><span class="icos-cog3"></span></a>
				<ul class="dropdown-menu pull-right">
					<li><a href="#" class="" id="expandAllNodes">Expand All</a></li>
					<li><a href="#" class="" id="collapseAllNodes">Collapse
							All</a></li>
				</ul>
			</div>
		</div>
		<div>	
			<br>		
			 <div class="demo-section k-header">
                <div id="treeview"></div>
                <br>
            </div>
            
		</div>
	</div>

	<div id="emptyDiv" class="widget grid2"></div>

	<div id="taskperm" class="widget grid4">
		<div class="whead">
			<h6>Permission</h6>
			<div class="titleOpt"></div>
		</div>
		<div id="hidden" style="height: 120px"></div>
		<div id="MultiSelect" align="center">
			<div>
				<br>
				<ul>
					<li style="padding-left: 20px; padding-right: 20px"><kendo:multiSelect
							name="treeItems" dataTextField="text" dataValueField="id"
							size="10px" select="onselects">
							<kendo:dataSource data="${items}"></kendo:dataSource>
						</kendo:multiSelect><br></li>
					<li style="text-align: left; margin-left: 20px">
						<button class="k-button" id="editRoles">&nbsp;&nbsp;Assign Roles
							&nbsp;&nbsp;</button> <br>
					</li>
				</ul>
				<br>
			</div>
		</div>		
			<div align="center" style="border-top:  1px solid black"><br>
				<button class="k-button" onClick="flushAll()"> Commit all your changes !!!</button>
				<span id=commitplaceholder style="display: none;"><img src='./resources/images/commiting.GIF' alt='Commiting......'/></span>
			<br><br>
			</div>
	</div>
</div>


<script>

$( document ).ready(function() {
	$('#MultiSelect').hide();
	$('#hidden').show();

	
$('#treeview').html('<img src="./resources/images/loadingimg.GIF" style="vertical-align:middle"/> &nbsp;&nbsp; <img src="./resources/images/loading.GIF" alt="loading" height= style="vertical-align:middle" height=25px/>');
 var tree = "";
		$.ajax({
		    type: "POST",
		     url: "${transportReadUrl}",
		     contentType: "application/json; charset=utf-8",
		     dataType: "json",
		     success: function (data) {  	 
		    	 
		       tree =   $("#treeview").kendoTreeView({
		             dataSource: data,
		             select : testtree
		         }).data("kendoTreeView"), handleTextBox = function(
							callback) {
						return function(e) {
							if (e.type != "keypress"
									|| kendo.keys.ENTER == e.keyCode) {
								callback(e);
							}
						};
				};
		       tree.collapse(".k-item");
		       
		       var roles = handleTextBox(function(e) {
					var selectedNode = tree.select();
					var multiSelect = $("#treeItems").data(
							"kendoMultiSelect");
					var role = "(" + multiSelect.value() + ")";
					if (multiSelect.value() != "") {
						var ne = texts.join(',');
						var str = ne.split(",");
						var strspli = str[str.length - 1].split("=");
						if (strspli[0] == "Tasks " || strspli[0] == "Forms " || strspli[0] == "Module ") {
							$body = $("#content");
					        $body.addClass("loading");							
							$.ajax({
								type : "POST",
								url : "./ldaptree/update",
								dataType: "text",
								data : {
									values : ne,
									mselect : role
								},
								success : function(response) {

									if(strspli[0] == "Tasks "){
									
									tree.append({
										text : "Roles = " + response
									}, selectedNode);

									$('#treeview_tv_active').find(
											'.k-group').find('.k-top')
											.remove();
									$('#treeview_tv_active').find(
											'.k-group').find('.k-mid')
											.remove();
									}
									// $('.k-group .k-group .k-group .k-item .k-group .k-group .k-item:first-child .k-top').remove();

									alert("Updated Successfully");

								},
								complete:function()
							       {
							        $body.removeClass("loading");
							       }
							});
						} else {
							alert("Invalid Selection");
						}
					} else {
						alert("Please enter the Roles");
					}
				});
				$("#editRoles").click(roles);  
		     }
		 });
		
		$("#expandAllNodes").click(function() {
			tree.expand(".k-item");
		});

		$("#collapseAllNodes").click(function() {
			tree.collapse(".k-item");
		});

	});

	function onselects(e) {
		var dataItem = this.dataSource.view()[e.item.index()];
	}
	
	function flushAll()
	{
		
		$('.k-button').hide();
		$('.k-multiselect').hide();
		$('#commitplaceholder').show();
		$body = $("body");
        $body.addClass("loading");
		$.ajax({
			type : "POST",
			url : "./ldaptree/flush",
			dataType: "text",
			success : function(response) {
				$('.k-button').show();

				$('#commitplaceholder').hide();
				alert(response);
				window.location.href="./home";

			},
			complete:function()
		       {
		        $body.removeClass("loading");
		       }
		});
	}

	var texts = "";
	function testtree(e) {
		$('#MultiSelect').hide();
		$('#hidden').show();

		var kitems = $(e.node).add(
				$(e.node).parentsUntil('.k-treeview', '.k-item'));
		texts = $.map(kitems, function(kitem) {
			return $(kitem).find('>div span.k-in').text();
		});

		var ntext = texts.join(',');
		var len = ntext.split(",");

		if (len.length >= 2) {
	
			$('#MultiSelect').show();
			$('#hidden').hide();

			var ne = texts.join(',');

			var multiSelect = $("#treeItems").data("kendoMultiSelect");
			
			if(len[2]=='Forms = Manage Product Access'){
				
				 multiSelect.enable(false);
			}
			else{
				multiSelect.enable();
			}
			
			multiSelect.dataSource.filter({}); //clear applied filter before setting value

			$.ajax({
				type : "POST",
				url : "./ldaptree/taskroles",
				data : {
					values : ne

				},
				success : function(response) {

					var res = [];
					$.each(response, function(index, value) {

						if (value.text == null) {

						} else {
							res.push(value.id);
						}
					});
					multiSelect.value(res);
				}
			});
		}
	}
	
   /*  $(window).scroll(function(){     
        if ($(window).scrollTop() > 48){
 	    $("#taskperm").css({"top": ($(window).scrollTop()) -48 + "px"});
 	}
  }); */
    
</script>
<style scoped>
.configuration .k-textbox {
	width: 10px
}

.demo-section {
	margin: 0 auto;
	border: 0px solid #CDCDCD;
	box-shadow: 0 1px 0 #FFFFFF;
	text-shadow: 0 1px #FFFFFF;
	background-image: none,
		linear-gradient(to bottom, #FFFFFF 0px, #E6E6E6 100%);
	background-position: 50% 50%;
	font-weight: normal;
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
	height: 100px;
	float: none;
}

#emptyDiv {
	width: 1px;
}
</style>