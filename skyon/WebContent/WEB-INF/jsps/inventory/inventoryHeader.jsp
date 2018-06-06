<%@taglib prefix="kendo" uri="/WEB-INF/kendo.tld"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link
	href="<c:url value='/resources/kendo/css/web/kendo.common.min.css'/>"
	rel="stylesheet" />
<link href="<c:url value='/resources/kendo/css/web/kendo.rtl.min.css'/>"
	rel="stylesheet" />
<link
	href="<c:url value='/resources/kendo/css/web/kendo.silver.min.css'/>"
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
	
	
 <link rel="stylesheet"
	href="<c:url value='/resources/js/intlTel/css/intlTelInput.css'/>"> 
<%-- <link rel="stylesheet"
	href="<c:url value='/resources/js/intlTel/css/demo.css'/>"> --%>
<%-- <link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/js/intlTelInput.css'/>" /> --%>

<!-- <script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script> -->
 <script src="<c:url value='/resources/js/intlTel/js/intlTelInput.js'/>"></script> 
	
<script type="text/javascript"
	src="<c:url value='/resources/jquery-ui.min.js'/>"></script>
<script src="<c:url value='/resources/kendo/js/kendo.all.min.js' />"></script>
<script src="<c:url value='/resources/kendo/shared/js/console.js'/>"></script>
<script src="<c:url value='/resources/kendo/shared/js/prettify.js'/>"></script>
<script src="<c:url value='/resources/jquery.i18n.properties-min-1.0.9.js'/>"></script>
  
	<script type="text/javascript" src="<c:url value='/resources/js/plugins/ui/jquery.tipsy.js'/>"></script>
  
 <%--  <script src="<c:url value='/resources/kendo.hin.js'/>"></script> --%>
<div id="langDiv"></div>
<style type="text/css">
.tipsy-inner {
    background-color: #808080;
    color: #000000;
    max-width: 200px;
    padding: 2px 10px;
    text-align: center;
}
			</style>
<script>

$("#langSelector").change(function() {
    document.location = this.value;
});

$("#cancelButton").click(function() {
    confirm(confirm_cancel);
});
function loadjscssfile(filename,filetype)
{
	      if ((filetype=="js")){ //if filename is a external JavaScript file
		     // alert("Loading hindi for kendo ");
		  var fileref=document.createElement('script');
		  fileref.setAttribute("type","text/javascript");
		  fileref.setAttribute("src", filename);
		  if (typeof fileref!="undefined"){
			  $("#langDiv").html(fileref);
			}
	      }
}
</script>  
<div id="content">
	<div class="contentTop">
		<span class="pageTitle"><span class="icon-screen"></span><label id="labeldashboard">${ViewName}</label></span>
		<%-- <ul class="quickStats">
			<li><a href="" class="redImg"><img
					src="<c:url value='/resources/images/icons/quickstats/user.png'/>"
					alt="" /></a>
				<div class="floatR">
					<strong class="blue">4658</strong><span>users</span>
				</div></li>
		</ul> --%>
	</div>
</div>
	<!-- Breadcrumbs line -->
	<div class="breadLine">
		<div class="bc">
			<ul id="breadcrumbs" class="breadcrumbs">
				<c:url var="home" value="./home" />
				<c:url value="/images/icons/breadsHome.png" var="homeimage" />
				<c:forEach var="bc" items="${breadcrumb.tree}" varStatus="status">
					<c:choose>
						<c:when test="${status.index==0}">
							<li><a href="${home}"><label id="labelhome">Home</label></a></li>
						</c:when>
						<c:when
							test="${status.index == fn:length(breadcrumb.tree)-1 && status.index!=0}">
							<li><a href="#"><label id="labelbcdashboard">${bc.name}</label></a></li>
						</c:when>
						<%-- <c:otherwise>
							<li><a href="${bc.value}">${bc.name}</a></li>
						</c:otherwise> --%>
					</c:choose>
				</c:forEach>
			</ul>
		</div>
		<div class="breadLinks">
			<ul id="menu"></ul>
		</div>
	</div>
	<script>
	
	/* $('.tipN').tipsy({gravity: 'n',fade: true, html:true}); */
	
		function onSelect(e) {
			var item = $(e.item), menuElement = item.closest(".k-menu"), dataItem = this.options.dataSource, index = item
					.parentsUntil(menuElement, ".k-item").map(function() {
						return $(this).index();
					}).get().reverse();

			index.push(item.index());

			for (var j = -1, len = index.length; ++j < len;) {
				dataItem = dataItem[index[j]];
				dataItem = j < len - 1 ? dataItem.items : dataItem;
			}
			if(dataItem.value !="#"){
			RenderLinkInner(dataItem.value);
			}
		}

		$(document).ready(function() {
			$("#menu").kendoMenu({
				dataSource : [ {
					text : "Store Receipts",
					value : "#",
					items : [ {
						text : "Store Receipts",
						value : "indexStoreGoodsReceipt",
					},
					{
						text : "Store Issues",
						value : "indexStoreIssue",
					},
					{
						text : "Store Item Transfers",
						value : "indexStoreItemTransfer",
					},
					{
						text : "Store Item Returns",
						value : "indexStoreGoodsReturns",
					},
					{
						text : "Store Adjustments",
						value : "indexStoreAdjustments",
					},
					{
						text : "Item Ledger",
						value : "indexStoreItemLedger",
					},
					{
						text : "Store Master",
						value : "indexStoreMaster",
					}]
				} ],
				select : onSelect
			});
		});
	</script>
	<div class="wrapper">
		<ul class="middleNavA">
			<li><a class="tipN" title="Manage Store Receipts" href="#"
				onclick="RenderLinkInner('indexStoreGoodsReceipt')"><img alt=""
					src="<c:url value='/resources/images/icons/color/user.png'/>" width="50px" height="50px"><span id="">
						Store Receipts</span></a></li>
			<li><a class="tipN" title="Manage Store Issues" href="#"
				onclick="RenderLinkInner('indexStoreIssue')"><img alt=""
					src="<c:url value='/resources/images/icons/color/user.png'/>" width="50px" height="50px"><span id="">
						Store Issues</span></a></li>
			<li><a class="tipN" title="Manage Store Item Transfers" href="#"
				onclick="RenderLinkInner('indexStoreItemTransfer')"><img alt=""
					src="<c:url value='/resources/images/icons/color/user.png'/>" width="50px" height="50px"><span id="">
						Store Item Transfers</span></a></li>
			<li><a class="tipN" title="Manage Store Item Returns" href="#"
				onclick="RenderLinkInner('indexStoreGoodsReturns')"><img alt=""
					src="<c:url value='/resources/images/icons/color/user.png'/>" width="50px" height="50px"><span id="">
						Store Item Returns</span></a></li>
			<li><a class="tipN" title="Manage Store Adjustments" href="#"
				onclick="RenderLinkInner('indexStoreAdjustments')"><img alt=""
					src="<c:url value='/resources/images/icons/color/user.png'/>" width="50px" height="50px"><span id="">
						Store Adjustments</span></a></li>
			<li><a class="tipN" title="Manage Item Ledger" href="#"
				onclick="RenderLinkInner('indexStoreItemLedger')"><img alt=""
					src="<c:url value='/resources/images/icons/color/user.png'/>" width="50px" height="50px"><span id="">
						Item Ledger</span></a></li>
			<li><a class="tipN" title="Manage Store Master" href="#"
				onclick="RenderLinkInner('indexStoreMaster')"><img alt=""
					src="<c:url value='/resources/images/icons/color/user.png'/>" width="50px" height="50px"><span id="">
						Store Master</span></a></li>

		</ul>
	</div>
	<div class="divider">
							<span></span>
						</div>
						
