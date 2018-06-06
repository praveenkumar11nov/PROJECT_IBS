<%@taglib prefix="kendo" uri="/WEB-INF/kendo.tld"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script type="text/javascript"
	src="<c:url value='/resources/js/plugins/ui/jquery.tipsy.js'/>"></script>
<div id="langDiv"></div>
<script>


/* $('.tipN').tipsy({gravity: 'n',fade: true, html:true}); */

	jQuery(document)
			.ready(
					function() {
						
						 var lang = getURLParameter("language");
						//var script = document.createElement('script');
						if (lang == 'hin') {
							
							  $.ajax({
						        url: "./resources/kendo.hin.js",
						        dataType: "script",
						        async: false,           // <-- This is the key
						        success: function () {
						            // all good...
						        },
						        error: function () {
						            throw new Error("Could not load script " + script);
						        }
						    });
							loadBundles(lang);
							//if(lang == "hin"){loadjscssfile("./resources/kendo.hin.js", "js")}
							$("#labeldashboard").html(
									jQuery.i18n.prop('UserAccessManagement'));
							$("#labelhome")
									.html(jQuery.i18n.prop('label.home'));
							// $("#labelbcdashboard").html(jQuery.i18n.prop('UserManagement'));
							$("#mNavUsers").html(jQuery.i18n.prop('Users'));
							$("#mNavUserRole").html(
									jQuery.i18n.prop('UserRole'));
							$("#mNavUserGroup").html(
									jQuery.i18n.prop('UserGroup'));
							$("#mNavRoles").html(jQuery.i18n.prop('Roles'));
							$("#mNavGroups").html(jQuery.i18n.prop('Groups'));
							$("#mNavDesignation").html(
									jQuery.i18n.prop('Designation'));
							$("#mNavDepartment").html(
									jQuery.i18n.prop('Department'));
							//alert($("#labelbcdashboard").text().split(" ").join(""));
							$("#labelbcdashboard").html(
									jQuery.i18n.prop($("#labelbcdashboard")
											.text().split(" ").join("")));

						}
						 

					});

	function getURLParameter(name) {
		return decodeURIComponent((new RegExp('[?|&]' + name + '='
				+ '([^&;]+?)(&|#|;|$)').exec(location.search) || [ , "" ])[1]
				.replace(/\+/g, '%20'))
				|| null;
	}

	function loadBundles(lang) {
		jQuery.i18n.properties({
			name : 'welcome',
			path : 'resources/',
			mode : 'both',
			language : lang,
			callback : function() {
			}
		});
	}

	$("#langSelector").change(function() {
		document.location = this.value;
	});

	$("#cancelButton").click(function() {
		confirm(confirm_cancel);
	});
	function loadjscssfile(filename, filetype) {
		if ((filetype == "js")) { //if filename is a external JavaScript file
			// alert("Loading hindi for kendo ");
			var fileref = document.createElement('script')
			fileref.setAttribute("type", "text/javascript")
			fileref.setAttribute("src", filename);
			if (typeof fileref != "undefined") {
				$("#langDiv").html(fileref);
			}
		}
	}
	
	
	function gridLangConversion()
	{
		var lang = getURLParameter("language");
	    
	    if(lang == 'hin'){
	    	
	    	
	   
	    	
	    	/* Generic */
	     	$('.k-grid-Clear_Filter').html(jQuery.i18n.prop('clearFilter'));
		 	$('.k-grid-add').html(jQuery.i18n.prop('add'));
		 	$('#copySelectedToGrid2').html(jQuery.i18n.prop('assign'));
		 	$('#removeUser').html(jQuery.i18n.prop('unassign'));
		  	$('.k-grid-cancel').html(jQuery.i18n.prop('cancel'));
		   
		 	$('.k-grid-update').html(jQuery.i18n.prop('update'));  
		 	$('.k-grid-edit').html(jQuery.i18n.prop('update'));  
		 	$('li[aria-controls$="-1"]').html(jQuery.i18n.prop('address'));
		    $('li[aria-controls$="-2"]').html(jQuery.i18n.prop('contact'));
		    $('li[aria-controls$="-3"]').html(jQuery.i18n.prop('property'));
		    $('li[aria-controls$="-4"]').html(jQuery.i18n.prop('accessCard'));
		    $('li[aria-controls$="-5"]').html(jQuery.i18n.prop('accessCardPermission'));
		    $('li[aria-controls$="-6"]').html(jQuery.i18n.prop('documentName'));
		    $('li[aria-controls$="-7"]').html(jQuery.i18n.prop('medicalEmergency'));
		    $('li[aria-controls$="-8"]').html(jQuery.i18n.prop('typeOfArm'));
	    	
		 
		   /* Users */
		 $('th[data-field="personName"]').find('.k-link').html(jQuery.i18n.prop('personName'));
		 $('th[data-field="urLoginName"]').find('.k-link').html(jQuery.i18n.prop('urLoginName'));
		 $('th[data-field="staffType"]').find('.k-link').html(jQuery.i18n.prop('staffType'));
		 $('th[data-field="vendorName"]').find('.k-link').html(jQuery.i18n.prop('vendorName'));
		 $('th[data-field="dept_Name"]').find('.k-link').html(jQuery.i18n.prop('dept_Name'));
		 $('th[data-field="dn_Name"]').find('.k-link').html(jQuery.i18n.prop('dn_Name'));
		 $('th[data-field="roles"]').find('.k-link').html(jQuery.i18n.prop('roles'));
		 $('th[data-field="groups"]').find('.k-link').html(jQuery.i18n.prop('groups'));
		 $('th[data-field="status"]').find('.k-link').html(jQuery.i18n.prop('status'));
		 
		 $('label[for="dept_Name"]').html(jQuery.i18n.prop('dept_Name'));
		 $('label[for="dn_Name"]').html(jQuery.i18n.prop('dn_Name'));
			 
		 
		 
	    	/* Department */
		 $('th[data-field="dept_Desc"]').find('.k-link').html(jQuery.i18n.prop('departmentdesc'));
		 $('th[data-field="dept_Status"]').find('.k-link').html(jQuery.i18n.prop('status'));
		 $('label[for="dept_Desc"]').html(jQuery.i18n.prop('departmentdesc'));
		 $('label[for="dept_Status"]').html(jQuery.i18n.prop('status'));
		 
		 
		   /* Designation */
		 $('th[data-field="dn_Description"]').find('.k-link').html(jQuery.i18n.prop('designationdesc'));
		 $('th[data-field="dr_Status"]').find('.k-link').html(jQuery.i18n.prop('status'));
		 $('label[for="dn_Description"]').html(jQuery.i18n.prop('designationdesc'));
		 $('label[for="dr_Status"]').html(jQuery.i18n.prop('status'));
		 
		 
		  /* Groups */
		 $('th[data-field="gr_name"]').find('.k-link').html(jQuery.i18n.prop('groups'));
		 $('th[data-field="gr_description"]').find('.k-link').html(jQuery.i18n.prop('grdesc'));
		 $('th[data-field="created_by"]').find('.k-link').html(jQuery.i18n.prop('createdBy'));
		 $('th[data-field="last_Updated_by"]').find('.k-link').html(jQuery.i18n.prop('updatedBy'));
		 $('th[data-field="gr_status"]').find('.k-link').html(jQuery.i18n.prop('rlStatus'));
		 /* Form */
		 $('label[for="gr_name"]').html(jQuery.i18n.prop('groups'));
		 $('label[for="gr_description"]').html(jQuery.i18n.prop('grdesc'));
		 $('label[for="gr_status"]').html(jQuery.i18n.prop('status'));
		 
		 
		  /* Roles */
		  /* Grid */
		 $('th[data-field="rlName"]').find('.k-link').html(jQuery.i18n.prop('roles'));
		 $('th[data-field="rlDescription"]').find('.k-link').html(jQuery.i18n.prop('roledesc'));
		 $('th[data-field="createdBy"]').find('.k-link').html(jQuery.i18n.prop('createdBy'));
		 $('th[data-field="lastUpdatedBy"]').find('.k-link').html(jQuery.i18n.prop('updatedBy'));
		 $('th[data-field="rlStatus"]').find('.k-link').html(jQuery.i18n.prop('rlStatus'));
		 /* Form */
		 $('label[for="rlName"]').html(jQuery.i18n.prop('roles'));
		 $('label[for="rlDescription"]').html(jQuery.i18n.prop('roledesc'));
	    
		 
	      /* User Roles */
		 $('label[for="categories"]').html(jQuery.i18n.prop('roles'));
		 
		 
		   /* User Groups */
		 $('#groupCat').html(jQuery.i18n.prop('groups'));
		   
		   
		   /* Users Inner Grid */

		 $('th[data-field="addressLocation"]').find('.k-link').html(jQuery.i18n.prop('addressLocation'));
		 $('th[data-field="addressPrimary"]').find('.k-link').html(jQuery.i18n.prop('addressPrimary'));
		 $('th[data-field="address"]').find('.k-link').html(jQuery.i18n.prop('address'));
		 $('th[data-field="countryName"]').find('.k-link').html(jQuery.i18n.prop('countryName'));
		 $('th[data-field="stateName"]').find('.k-link').html(jQuery.i18n.prop('stateName'));
		 $('th[data-field="cityName"]').find('.k-link').html(jQuery.i18n.prop('cityName'));
		 $('th[data-field="pincode"]').find('.k-link').html(jQuery.i18n.prop('pincode'));
		 $('th[data-field="addressSeasonFrom"]').find('.k-link').html(jQuery.i18n.prop('addressSeasonFrom'));
		 $('th[data-field="addressSeasonTo"]').find('.k-link').html(jQuery.i18n.prop('addressSeasonTo'));
		 
		 $('th[data-field="contactLocation"]').find('.k-link').html(jQuery.i18n.prop('contactLocation'));
		 $('th[data-field="contactType"]').find('.k-link').html(jQuery.i18n.prop('contactType'));
		 $('th[data-field="contactContent"]').find('.k-link').html(jQuery.i18n.prop('contactContent'));
		 $('th[data-field="contactPreferredTime"]').find('.k-link').html(jQuery.i18n.prop('contactPreferredTime'));
		 $('th[data-field="contactSeasonFrom"]').find('.k-link').html(jQuery.i18n.prop('addressSeasonFrom'));
		 $('th[data-field="contactSeasonTo"]').find('.k-link').html(jQuery.i18n.prop('addressSeasonTo'));
		 
		 $('th[data-field="propertyId"]').find('.k-link').html(jQuery.i18n.prop('propertyId'));
		 $('th[data-field="propertyAquiredDate"]').find('.k-link').html(jQuery.i18n.prop('propertyAquiredDate'));
		 $('th[data-field="propertyRelingDate"]').find('.k-link').html(jQuery.i18n.prop('propertyRelingDate'));
		 $('th[data-field="visitorRegReq"]').find('.k-link').html(jQuery.i18n.prop('visitorRegReq'));
		 
		 $('th[data-field="acType"]').find('.k-link').html(jQuery.i18n.prop('acType'));
		 $('th[data-field="acNo"]').find('.k-link').html(jQuery.i18n.prop('acNo'));
		 $('th[data-field="acStartDate"]').find('.k-link').html(jQuery.i18n.prop('acStartDate'));
		 $('th[data-field="acEndDate"]').find('.k-link').html(jQuery.i18n.prop('acEndDate'));
		 $('th[data-field="arId"]').find('.k-link').html(jQuery.i18n.prop('arId'));
		 
		 $('th[data-field="acpStartDate"]').find('.k-link').html(jQuery.i18n.prop('acpStartDate'));
		 $('th[data-field="acpEndDate"]').find('.k-link').html(jQuery.i18n.prop('acpEndDate'));
		 
		 $('th[data-field="documentName"]').find('.k-link').html(jQuery.i18n.prop('documentName'));
		 $('th[data-field="documentNumber"]').find('.k-link').html(jQuery.i18n.prop('documentNumber'));
		 $('th[data-field="approved"]').find('.k-link').html(jQuery.i18n.prop('approved'));
		 
		 $('th[data-field="meCategory"]').find('.k-link').html(jQuery.i18n.prop('meCategory'));
		 $('th[data-field="disabilityType"]').find('.k-link').html(jQuery.i18n.prop('meCategory'));
		 $('th[data-field="description"]').find('.k-link').html(jQuery.i18n.prop('description'));
		 $('th[data-field="meHospitalName"]').find('.k-link').html(jQuery.i18n.prop('meHospitalName'));
		 $('th[data-field="meHospitalContact"]').find('.k-link').html(jQuery.i18n.prop('meHospitalContact'));
		 $('th[data-field="meHospitalAddress"]').find('.k-link').html(jQuery.i18n.prop('meHospitalAddress'));
		 
		 $('th[data-field="typeOfArm"]').find('.k-link').html(jQuery.i18n.prop('typeOfArm'));
		 $('th[data-field="armMake"]').find('.k-link').html(jQuery.i18n.prop('armMake'));
		 $('th[data-field="licenceNo"]').find('.k-link').html(jQuery.i18n.prop('licenceNo'));
		 $('th[data-field="licenceValidity"]').find('.k-link').html(jQuery.i18n.prop('licenceValidity'));
		 $('th[data-field="issuingAuthority"]').find('.k-link').html(jQuery.i18n.prop('issuingAuthority'));
		 $('th[data-field="noOfRounds"]').find('.k-link').html(jQuery.i18n.prop('issuingAuthority'));
	    }
	}
	
</script>
<div id="content">
	<div class="contentTop">
		<span class="pageTitle"><span class="icon-screen"></span><label
			id="labeldashboard">${ViewName}</label></span>
		<%-- <ul class="quickStats">
			<li><a href="" class="redImg"><img
					src="<c:url value='/resources/images/icons/quickstats/user.png'/>"
					alt="" /></a>
				<div class="floatR">
					<strong class="blue">4658</strong><span>users</span>
				</div></li>
		</ul> --%>
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
	</div>
	<script>
		function onSelect(e) {
			var item = $(e.item), menuElement = item.closest(".k-menu"), dataItem = this.options.dataSource, index = item
					.parentsUntil(menuElement, ".k-item").map(function() {
						return $(this).index();
					}).get().reverse();

			index.push(item.index());

			for (var i = -1, len = index.length; ++i < len;) {
				dataItem = dataItem[index[i]];
				dataItem = i < len - 1 ? dataItem.items : dataItem;
			}
			if (dataItem.value != "#") {
				RenderLinkInner(dataItem.value);
			}
		}

		$(document).ready(function() {
			$("#menu").kendoMenu({
				dataSource : [ {
					text : "User Management",
					value : "#",
					items : [ {
						text : "Users",
						value : "users",
					}, {
						text : "User Roles",
						value : "userroles"
					}, {
						text : "User Groups",
						value : "usergroups"
					}, {
						text : "Roles",
						value : "role"
					}, {
						text : "Groups",
						value : "groups"
					}, {
						text : "Designations",
						value : "designation"
					}, {
						text : "Departments",
						value : "department"
					} ]
				} ],
				select : onSelect
			});
		});
	</script>
	<div class="wrapper">
		<ul class="middleNavA">
			<li><a title="Manage System access to the Users"
				href="#" onclick="RenderLinkInner('users')"><img alt=""
					src="<c:url value='/resources/images/icons/color/user.png'/>"
					height="50" width="50"><span id="mNavUsers"> Users</span></a></li>
					
			<li><a title="Assign Users to the Role" href="#"
				onclick="RenderLinkInner('userroles')"><img alt=""
					src="<c:url value='/resources/images/icons/color/order-149.png'/>"
					height="50" width="50"><span id="mNavUserRole"> User
						Roles</span></a></li>

			<li><a title="Assign Users to the Grroup" href="#"
				onclick="RenderLinkInner('usergroups')"><img alt=""
					src="<c:url value='/resources/images/icons/color/my-account.png'/>"
					height="50" width="50"><span id="mNavUserGroup"> User
						Groups</span></a></li>

			<li><a title="Manage Roles" href="#"
				onclick="RenderLinkInner('role')"><img alt=""
					src="<c:url value='/resources/images/icons/color/administrative-docs.png'/>"
					height="50" width="50"><span id="mNavRoles"> Roles</span></a></li>


			<li><a title="Manage Groups" href="#"
				onclick="RenderLinkInner('groups')"><img alt=""
					src="<c:url value='/resources/images/icons/color/attibutes.png'/>"
					height="50" width="50"><span id="mNavGroups"> Groups</span></a></li>



			<li><a title="Manage Designations" href="#"
				onclick="RenderLinkInner('designation')"><img alt=""
					src="<c:url value='/resources/images/icons/color/cost.png'/>"
					height="50" width="50"><span id="mNavDesignation">
						Designations</span></a></li>

			<li><a title="Manage Department" href="#"
				onclick="RenderLinkInner('department')"><img alt=""
					src="<c:url value='/resources/images/icons/color/suppliers.png'/>"
					height="50" width="50"><span id="mNavDepartment">
						Departments</span></a></li>
		</ul>
	</div>
	<div class="divider">
		<span></span>
	</div>