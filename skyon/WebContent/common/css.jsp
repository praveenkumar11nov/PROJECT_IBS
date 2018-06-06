
<link href="${ctx}/resources/css/styles.css" rel="stylesheet" type="text/css" />
<!--[if IE]> <link href="${ctx}/resources/css/ie.css" rel="stylesheet" type="text/css"> <![endif]-->

<script type="text/javascript" src="${ctx}/resources/jquery.min.js"></script>
	
<script type="text/javascript" src="${ctx}/resources/jquery-ui.min.js"></script>

<script type="text/javascript" src="${ctx}/resources/js/plugins/charts/excanvas.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/plugins/charts/jquery.flot.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/plugins/charts/jquery.flot.orderBars.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/plugins/charts/jquery.flot.pie.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/plugins/charts/jquery.flot.resize.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/plugins/charts/jquery.sparkline.min.js"></script>

<script type="text/javascript" src="${ctx}/resources/js/plugins/tables/jquery.dataTables.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/plugins/tables/jquery.sortable.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/plugins/tables/jquery.resizable.js"></script>

<script type="text/javascript" src="${ctx}/resources/js/plugins/forms/jquery.autosize.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/plugins/forms/jquery.uniform.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/plugins/forms/jquery.inputlimiter.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/plugins/forms/jquery.tagsinput.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/plugins/forms/jquery.maskedinput.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/plugins/forms/jquery.autotab.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/plugins/forms/jquery.select2.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/plugins/forms/jquery.dualListBox.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/plugins/forms/jquery.cleditor.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/plugins/forms/jquery.ibutton.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/plugins/forms/jquery.validationEngine-en.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/plugins/forms/jquery.validationEngine.js"></script>

<script type="text/javascript" src="${ctx}/resources/js/plugins/uploader/plupload.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/plugins/uploader/plupload.html4.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/plugins/uploader/plupload.html5.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/plugins/uploader/jquery.plupload.queue.js"></script>

<script type="text/javascript" src="${ctx}/resources/js/plugins/wizards/jquery.form.wizard.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/plugins/wizards/jquery.validate.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/plugins/wizards/jquery.form.js"></script>
<%-- <script type="text/javascript" src="${ctx}/resources/js/plugins/ui/jquery.tipsy.js"></script> --%>

<!-- *********************************************************************************************************************** -->

<!-- ****************************************************************************************************** -->
<script type="text/javascript" src="${ctx}/resources/js/plugins/ui/jquery.collapsible.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/plugins/ui/jquery.breadcrumbs.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/plugins/ui/jquery.tipsy.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/plugins/ui/jquery.progress.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/plugins/ui/jquery.timeentry.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/plugins/ui/jquery.colorpicker.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/plugins/ui/jquery.jgrowl.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/plugins/ui/jquery.fancybox.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/plugins/ui/jquery.fileTree.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/plugins/ui/jquery.sourcerer.js"></script>

<%-- <script type="text/javascript" src="${ctx}/resources/js/plugins/others/jquery.fullcalendar.js"></script> --%>
<script type="text/javascript" src="${ctx}/resources/js/plugins/others/jquery.elfinder.js"></script>

<script type="text/javascript" src="${ctx}/resources/js/plugins/forms/jquery.mousewheel.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/plugins/ui/jquery.easytabs.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/files/bootstrap.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/files/functions.js"></script>

 <script type="text/javascript" src="${ctx}/resources/jquery.slimscroll.js"></script>
 
 <link type="text/css" rel="stylesheet" href="${ctx}/resources/dashboard/lib/jquery.stepy.css" />
  <script type="text/javascript" src="${ctx}/resources/dashboard/demo/js/jquery.validate.min.js"></script>
  <script type="text/javascript" src="${ctx}/resources/dashboard/lib/jquery.stepy.js"></script>


<%
	String str ="";
	if(session.getAttribute("themeName") == null)
	{
		str ="kendo.default.min.css";
	}
	else
	{
		str ="kendo."+session.getAttribute("themeName")+".min.css";
	}
	
  %>
<link href="<c:url value='/resources/kendo/css/web/kendo.common.min.css'/>" rel="stylesheet" />
<link href="<c:url value='/resources/kendo/css/web/kendo.rtl.min.css'/>" rel="stylesheet" />
<link href="${ctx}/resources/kendo/css/web/<%=str%>" rel="stylesheet" />
<link href="<c:url value='/resources/kendo/css/dataviz/kendo.dataviz.min.css'/>" rel="stylesheet" />
<link href="<c:url value='/resources/kendo/css/dataviz/kendo.dataviz.default.min.css'/>" rel="stylesheet" />
<link href="<c:url value='/resources/kendo/shared/styles/examples-offline.css'/>" rel="stylesheet">
<%-- <script src="<c:url value='/resources/kendo/js/jquery.min.js' />"></script> --%>
<%-- <script type="text/javascript" src="<c:url value='/resources/jquery.min.js'/>"></script> --%>
<%-- <script type="text/javascript" src="<c:url value='/resources/jquery-ui.min.js'/>"></script> --%>
<script src="<c:url value='/resources/kendo/js/kendo.all.min.js' />"></script>
<script src="<c:url value='/resources/kendo/shared/js/console.js'/>"></script>
<script src="<c:url value='/resources/kendo/shared/js/prettify.js'/>"></script>

<script src="<c:url value='/resources/kendo/js/kendo.timezones.min.js' />"></script>