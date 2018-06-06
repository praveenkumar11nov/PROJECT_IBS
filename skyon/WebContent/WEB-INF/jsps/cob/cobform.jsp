<%@include file="/common/taglibs.jsp"%>

<link	href="<c:url value='/resources/twitter-bootstrap-wizard/bootstrap/css/bootstrap.min.css'/>"	rel="stylesheet" />
<link	href="<c:url value='/resources/twitter-bootstrap-wizard/prettify.css'/>" rel="stylesheet" />

<script type="text/javascript"	src=" <c:url value='/resources/twitter-bootstrap-wizard/bootstrap/js/bootstrap.min.js'/>"></script>
<script type="text/javascript"	src=" <c:url value='/resources/twitter-bootstrap-wizard/jquery.bootstrap.wizard.js'/>"></script>
<script type="text/javascript"	src=" <c:url value='/resources/twitter-bootstrap-wizard/prettify.js'/>"></script>


<script>
$(document).ready(function() {
	
	$(".tab-content").hide();
		$("#size").kendoDropDownList();
		$("#maritalStatus").kendoDropDownList();
		$("#workNature").kendoDropDownList();
		$("#bloodGroup").kendoDropDownList();
		$("#occupation").kendoDropDownList();
		$("#dob").kendoDatePicker();
		$("#nationality").kendoDropDownList();
		$("#slotTypeField").kendoDropDownList();
		$("#petSexField").kendoDropDownList();
		$("#categoryNameField").kendoDropDownList();
		$("#armType").kendoDropDownList();
		$("#petType").kendoDropDownList();
		
		
		$("#accessCardNumberField").kendoDropDownList({
            filter: "startswith",
            dataTextField: "acNo",
            dataValueField: "acId",
            dataSource: {
                transport: {
                    read: {
                        url: "./accesscards/getAllAccessCards",
                    }
                }
            }
        });
		
		$("#country").kendoDropDownList({
			autoBind : false,
			optionLabel : "Select",
			dataTextField: "countryName",
            dataValueField: "countryId",
			dataSource : {
				transport : {
					read : "./address/getCountry"
				}
			}
		});	
		
		$("#state").kendoDropDownList({
			autoBind : false,
			optionLabel : "Select",
			dataTextField: "stateName",
            dataValueField: "stateId",
            cascadeFrom: "country",
			dataSource : {
				transport : {
					read : "./address/getState"
				}
			}
		});	
		
		$("#city").kendoDropDownList({
			autoBind : false,
			optionLabel : "Select",
			dataTextField: "cityName",
            dataValueField: "cityId",
            cascadeFrom: "state",
			dataSource : {
				transport : {
					read : "./address/getCity"
				}
			}
		});	
		
		
		$("#vehicleMake").kendoComboBox({
			autoBind : false,
			placeholder: "Select",
			dataTextField: "carName",
            dataValueField: "carId",
			dataSource : {
				transport : {
					read : "./vehicledetails/getVehiclesMake"
				}
			}
		});	
		
		
		

		$('#rootwizard').bootstrapWizard({onTabShow: function(tab, navigation, index) {
			 var $total = navigation.find('li').length;
			 var $current = index+1;
			 var $percent = ($current/$total) * 100;
			 $('#rootwizard').find('.bar').css({width:$percent+'%'});
		}});
		window.prettyPrint && prettyPrint()
	});
	
	function propChange(e){
		$(".tab-content").show();
		var dataItem = this.dataItem(e.item.index());
        $.ajax({
           url : "./cob/read/family/"+dataItem.propertyId,  
           type : 'POST',
              dataType: "json",
              contentType: "application/json; charset=utf-8",
              success: function (result){
                var grid = $("#cobFamilyGrid").getKendoGrid();
                   var data = new kendo.data.DataSource();
                   grid.dataSource.data(result);
                   grid.refresh();  
              },
          });
        
        $.ajax({
            url : "./cob/read/dom/"+dataItem.propertyId,  
            type : 'POST',
               dataType: "json",
               contentType: "application/json; charset=utf-8",
               success: function (result){
                 var grid = $("#cobDomGrid").getKendoGrid();
                    var data = new kendo.data.DataSource();
                    grid.dataSource.data(result);
                    grid.refresh();  
               },
           });
        
        dropDownDataSource = new kendo.data.DataSource({
			transport : {
				read : {
					url : "./cob/getSlotNumbers?propertyId="+dataItem.propertyId,
					dataType : "json",
					type : 'GET'
				}
			}

		});
		$("#slotNo").kendoDropDownList({
			dataSource : dropDownDataSource,
			optionLabel : "Select",
			dataTextField : "psSlotNo",
			dataValueField : "psId",
			optionLabel : {
				psSlotNo : "Select",
				psId : "",
			},
		});
	}
	
	function familyCobEvent(e){
		
		$('label[for="fullName"]').remove();
		$('div[data-container-for="fullName"]')
				.remove();
		if (e.model.isNew()){
			$(".k-window-title").text("Add Family");
		}else{
		}
	}
	
	
	function relationEditor(container, options) {
		var data = [ {
			text : "Wife",
			value : "Wife"
		}, {
			text : "Cousin",
			value : "Cousin"
		}, {
			text : "Son",
			value : "Son"
		}, {
			text : "Daughter",
			value : "Daughter"
		}, {
			text : "Father",
			value : "Father"
		}, {
			text : "Mother",
			value : "Mother"
		}, {
			text : "Brother",
			value : "Brother"
		}, {
			text : "Sister",
			value : "Sister"
		}, {
			text : "Husband",
			value : "Husband"
		}, {
			text : "Relative",
			value : "Relative"
		} ];
		$('<input name="Relationship" data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '" required="true"/>')
				.appendTo(container).kendoComboBox({
					dataTextField : "text",
					dataValueField : "value",
					placeholder : "Select ",
					dataSource : data
		});
		$('<span class="k-invalid-msg" data-for="Relationship"></span>').appendTo(container);
	}
</script>

<br>

 
<br>

<div class='container'>
	<div class="span12">
		<section id="wizard">
			<form:form id="commentForm" method="get" action="./saveCustomerData" class="form-horizontal" commandName="customerOnBoardBean" modelAttribute="customerOnBoardBean">
				<div id="rootwizard" class="tabbable tabs-left">
					<div id="bar" class="progress progress-striped active">
					  <div class="bar"></div>
					</div>
					<ul>
						<li><a href="#tab1" data-toggle="tab">Personal	Information</a></li>
						<li><a href="#tab2" data-toggle="tab">Address and Contact</a></li>
						<li><a href="#tab5" data-toggle="tab">Vehicle Details</a></li>
						
						<li><a href="#tab9" data-toggle="tab">Arms and Emergency</a></li> 
						<li><a href="#tab7" data-toggle="tab">Pets Details</a></li>
						<li><a href="#tab6" data-toggle="tab">Access Card Details</a></li>
						 <li><a href="#tab3" data-toggle="tab">Family</a></li>
						<!--<li><a href="#tab4" data-toggle="tab">Domestic Help </a></li>
						<li><a href="#tab8" data-toggle="tab">Documents Details</a></li>
						
						
						-->
					</ul>

					<kendo:dropDownList name="color" dataTextField="property_No" optionLabel="Select Property   ................" select="propChange"
						dataValueField="propertyId" value="1">
						<kendo:dataSource data="${properties}"></kendo:dataSource>
					</kendo:dropDownList>
					<div class="tab-content" >
						<div class="tab-pane" id="tab1">
							<center><h3>Personal Information</h3></center>
							<br>
							<table>
								<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="firstName">First
												Name</label>
											<div class="controls">
												<form:input id="firstName" path="firstNameOwner" required="required"
													class="required firstName"/>
											</div>
										</div>
									</td>
									<td>
										<div class="control-group">
											<label class="control-label" for="lastName">Last Name</label>
											<div class="controls">
												<form:input required="required"   id="lastName" path="lastNameOwner" 
													class="required lastName" />
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="fatherName">Father
												name</label>
											<div class="controls">
												<form:input required="required"   id="emailfield" path="fatherNameOwner" class="required fatherName"/>
											</div>
										</div>
									</td>
									<td>
										<%-- <div class="control-group">
											<label class="control-label" for="dob">Date of Birth</label>
											<div class="controls">
												<form:input required="required"   id="dob" path="dobOwner" class="required dob"/>
											</div>
										</div> --%>

									</td>
								</tr>
								<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="name">Marital
												Status</label>
											<div class="controls">
												<form:select id="maritalStatus" path="maritalStatusOwner" required="required">
													<form:option value="">Select</form:option>
													<form:options items="${maritalStatus}"  />
												</form:select>
											</div>
										</div>
									</td>
									<td>
										<div class="control-group">
											<label class="control-label" for="name">Sex</label>
											<div class="controls">
												<form:select id="size" path="sexOwner">
													<form:option value="">Select</form:option>
													<form:options items="${sex}"  />
												</form:select>
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="name">Nationality</label>
											<div class="controls">
												<form:select path="nationalityOwner" id="nationality">
													<form:option value="">Select</form:option>
													<form:options items="${nationality}"  />
												</form:select>
											</div>
										</div>
									</td>
									<td>							
										<div class="control-group">
											<label class="control-label" for="name">Blood Group</label>
											<div class="controls">
												<form:select id="bloodGroup" path="bloodGroupOwner">
													<form:option value="">Select</form:option>
													<form:options items="${bloodGroup}" />
												</form:select>
											</div>
										</div>								
									</td>
								</tr>								
								<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="name">Occupation</label>
											<div class="controls">
												<form:select id="occupation" path="occupationOwner">
													<form:option value="">Select</form:option>
													<form:option value="">Service</form:option>
													<form:option value="Service">Retired</form:option>
													<form:option value="Consultant">Consultant</form:option>
													<form:option value="Doctor">Doctor</form:option>
													<form:option value="Lawyer">Lawyer</form:option>
												</form:select>
											</div>
										</div>
									</td>
									<td>								
										<div class="control-group">
											<label class="control-label" for="natureOfWork">Nature of Work</label>
											<div class="controls">
												<form:select id="workNature" path="natureOfWorkOwner">
													<form:option value="">Select</form:option>
													<form:options items="${workNature}"/>
												</form:select>
											</div>
										</div>									
									</td>
								</tr>							
							</table>
						</div>
						
						<div class="tab-pane" id="tab2">
							<center><h3>Contact Details</h3></center>
							<br>
							<table>
								<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="addressNo">Property No</label>
											<div class="controls">
												<form:input required="required"  id="addressNo" path="addressLine2Owner"
													class="required addressNo"/>
											</div>
										</div>
									</td>
									
									<td>
										<div class="control-group">
											<label class="control-label" for="emailfield">Email</label>
											<div class="controls">
												<form:input required="required"   id="emailfield" path="emailfieldOwner" class="required emailfield"/>
											</div>
										</div>
									</td>
								</tr>
								
								<tr>
									<td rowspan="3">
										<div class="control-group">
											<label class="control-label" for="address1">Address</label>
											<div class="controls">
												<form:textarea required="required" id="address1" path="addressLine1Owner" cssClass="addressTextArea"
													class="required address1"/>
											</div>
										</div>
									</td>
									<td>
										<div class="control-group">
											<label class="control-label" for="mobile">Mobile</label>
											<div class="controls">
												<form:input required="required" id="mobile" path="mobileOwner" class="required mobile"/>
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="country">Country</label>
											<div class="controls">
												<form:input required="required" id="country" path="countryOwner" class="required country"/>
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="state">State</label>
											<div class="controls">
												<form:input required="required"   id="state" path="stateOwner" class="required state"/>
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>							
										<div class="control-group">
											<label class="control-label" for="pincode">Pin Code</label>
											<div class="controls">
												<form:input required="required" id="pincode" path="pincodeOwner"
													class="required pincode"/>
											</div>
										</div>							
									</td>
									<td>
										<div class="control-group">
											<label class="control-label" for="city">City</label>
											<div class="controls">
												<form:input required="required"   id="city" path="cityOwner" class="required city"/>
											</div>
										</div>
									</td>
								</tr>
												
							</table>
						</div>
						
						<div class="tab-pane" id="tab5">
							<center><h3>Vehicles Details</h3></center>
							<br>
							<table>
								<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="registrationNoField">Registration No</label>
											<div class="controls">
												<form:input required="required" id="registrationNoField" path="registrationNoField" class="required registrationNoField"/>
											</div>
										</div>
									</td>
									<td>
										<div class="control-group">
											<label class="control-label" for="slotTypeField">Slot Type</label>
											<div class="controls">
												<form:select id="slotTypeField" path="slotTypeField" required="required">
													<form:option value="">Select</form:option>
													<form:option value="Fixed">Fixed</form:option>
													<form:option value="Floating">Floating</form:option>
												</form:select>
											</div>
										</div>
									</td>
							</tr>
									
							
								<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="slotNumberField">Slot Number</label>
											<div class="controls">
												<form:input required="required" id="slotNo" path="slotNumberField" class="required slotNumberField"/>
											</div>
										</div>
									</td>
									<td>
										<div class="control-group">
											<label class="control-label" for="vehicleMakeField">Vehicle Make</label>
											<div class="controls">
												<form:input required="required"   id="vehicleMake" path="vehicleMakeField" class="required vehicleMakeField"/>
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="vehicleModelField">Vehicle Model</label>
											<div class="controls">
												<form:input required="required"   id="vehicleModelField" path="vehicleModelField" class="required vehicleModelField"/>
											</div>
										</div>
									</td>
									<td>
										<div class="control-group">
											<label class="control-label" for="vehicleTagNumberField">Vehicle Tag Number</label>
											<div class="controls">
												<form:input required="required"   id="vehicleTagNumberField" path="vehicleTagNumberField" class="required vehicleTagNumberField"/>
											</div>
										</div>
									</td>
								</tr>							
							</table>
						</div>
						
						<div class="tab-pane" id="tab9">
							<center><h3>Medical Emergency Details</h3></center>
							<br>
							<table>
								<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="categoryNameField">Category Name</label>
											<div class="controls">
												<form:select id="categoryNameField" path="categoryNameField" required="required">
													<form:option value="">Select</form:option>
													<form:options items="${meCategory}"/>
												</form:select>
											
											
											
											</div>
										</div>
									</td>
									<td>
										<div class="control-group">
											<label class="control-label" for="disabilityTypeField">Disability&nbsp;Type</label>
											<div class="controls">
												<form:input required="required"   id="disabilityTypeField" path="disabilityTypeField" class="required disabilityTypeField"/>
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="descriptionField">Description</label>
											<div class="controls">
												<form:input required="required"   id="descriptionField" path="descriptionField" class="required descriptionField"/>
											</div>
										</div>
									</td>
									<td>
										<div class="control-group">
											<label class="control-label" for="hospitalNameField">Hospital Name</label>
											<div class="controls">
												<form:input required="required"   id="hospitalNameField" path="hospitalNameField" class="required hospitalNameField"/>
											</div>
										</div>

									</td>
								</tr>
								<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="hospitalContactNoField">Hospital&nbsp;Contact&nbsp;No</label>
											<div class="controls">
												<form:input required="required"   id="hospitalContactNoField" path="hospitalContactNoField" class="required hospitalContactNoField"/>
											</div>
										</div>
									</td>
									<td>							
										<div class="control-group">
											<label class="control-label" for="hospitalAddressField">Hospital Address</label>
											<div class="controls">
												<form:input required="required"   id="hospitalAddressField" path="hospitalAddressField" class="required hospitalAddressField"/>
											</div>
										</div>								
									</td>
								</tr>													
							</table>
							
							<center><h3>Arms Details</h3></center>
							<br>
							<table>
							   <tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="armType">Arm Type</label>
											<div class="controls">
												<form:select id="armType" path="armType" required="required">
													<form:option value="">Select</form:option>
													<form:options items="${typeOfArm}"/>
												</form:select>
											</div>
										</div>
									</td>
									<td>							
										<div class="control-group">
											<label class="control-label" for="armMake">Arm Make</label>
											<div class="controls">
												<form:input required="required"   id="armMake" path="armMake" class="required armMake"/>
											</div>
										</div>								
									</td>
								</tr>								
								<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="licenceNumber">Licence&nbsp;Number</label>
											<div class="controls">
												<form:input required="required"   id="licenceNumber" path="licenceNumber" class="required licenceNumber"/>
											</div>
										</div>
									</td>
									<td>								
										<%-- <div class="control-group">
											<label class="control-label" for="licenceValidity">Licence&nbsp;Validity</label>
											<div class="controls">
												<form:input required="required"   id="licenceValidity" path="licenceValidity" class="required licenceValidity"/>
											</div>
										</div>	 --%>								
									</td>
								</tr>
								<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="issuingAuthority">Issuing&nbsp;Authority</label>
											<div class="controls">
												<form:input required="required"   id="issuingAuthority" path="issuingAuthority" class="required issuingAuthority"/>
											</div>
										</div>
									</td>
									<td>								
										<div class="control-group">
											<label class="control-label" for="numberOfRounds">Number&nbsp;of&nbsp;Rounds</label>
											<div class="controls">
												<form:input required="required"   id="numberOfRounds" path="numberOfRounds" class="required numberOfRounds"/>
											</div>
										</div>									
									</td>
								</tr>
							</table>
							
							
						</div> 
						
						<div class="tab-pane" id="tab7">
							<center><h3>Pets Details</h3></center>
							<br>
							<table>
							
							<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="petNameField">Pet Name</label>
											<div class="controls">
												<form:input required="required"   id="petNameField" path="petNameField" class="required petNameField"/>
											</div>
										</div>
									</td>
									<td>
										<div class="control-group">
											<label class="control-label" for="petTypeField">Pet Type</label>
											<div class="controls">
												<form:select id="petType" path="petTypeField" required="required" >
													<form:option value="">Select</form:option>
													<form:option value="Dog">Dog</form:option>
													<form:option value="Cat">Cat</form:option>
													<form:option value="Bird">Bird</form:option>
													<form:option value="Other">Other</form:option>
												</form:select>
												
											</div>
										</div>
									</td>
							</tr>
									
							
								<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="petBreedField">Pet Breed</label>
											<div class="controls">
												<form:input required="required"   id="petBreedField" path="petBreedField" class="required petBreedField"/>
											</div>
										</div>
									</td>
									<td>
										<div class="control-group">
											<label class="control-label" for="petAgeField">Pet Age</label>
											<div class="controls">
												<form:input required="required"   id="petAgeField" path="petAgeField" class="required petAgeField"/>
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="petSexField">Pet Sex</label>
											<div class="controls">
												<form:select id="petSexField" path="petSexField" required="required" >
													<form:option value="">Select</form:option>
													<form:options items="${sex}"/>
												</form:select>
											</div>
										</div>
									</td>
									<td>
										<div class="control-group">
											<label class="control-label" for="emergencyContactField">Emergency&nbsp;Contact</label>
											<div class="controls">
												<form:input required="required"   id="emergencyContactField" path="emergencyContactField" class="required emergencyContactField"/>
											</div>
										</div>
									</td>
								</tr>							
							</table>
						</div>
						
						<div class="tab-pane" id="tab6">
							<center><h3>Domestic Help</h3></center>
							<br>
							<c:url value="/cob/create/dom" var="createFamUrl" />
							<kendo:grid name="cobDomGrid" groupable="true" sortable="true">
								<kendo:grid-pageable refresh="true" pageSizes="true"
									buttonCount="5">
								</kendo:grid-pageable>
								<kendo:grid-editable mode="popup" />
								<kendo:grid-toolbar>
									<kendo:grid-toolbarItem name="create" text="Add Domestic Help" />
								</kendo:grid-toolbar>
								<kendo:grid-columns>
									<kendo:grid-column title="Name" field="fullName" width="110" />
									<kendo:grid-column title="First Name" field="firstName" width="110" hidden="true"/>
									<kendo:grid-column title="Last Name" field="lastName" width="110" hidden="true"/>
									<kendo:grid-column title="Father Name" field="fatherName" width="110" />
									<kendo:grid-column title="Sex" field="sex" width="110" >
										<kendo:grid-column-values value="${sex}"/>
									</kendo:grid-column>
									<kendo:grid-column title="Date Of Birth" field="dob" width="110" format="{0:dd/MM/yyyy}"/>
									<kendo:grid-column title="Work Nature" field="workNature" width="110" editor="relationEditor" />
									
								</kendo:grid-columns>
								<kendo:dataSource pageSize="10">
									<kendo:dataSource-transport>
										<kendo:dataSource-transport-create url="${createDomUrl}" dataType="json" type="GET" contentType="application/json" />
										<kendo:dataSource-transport-update url="${updateUrl}"	dataType="json" type="GET" contentType="application/json" />
										<kendo:dataSource-transport-destroy url="${destroyUrl}/" dataType="json" type="GET" contentType="application/json" />
									</kendo:dataSource-transport>
									<kendo:dataSource-schema>
										<kendo:dataSource-schema-model id="personId">
												<kendo:dataSource-schema-model-fields>
												<kendo:dataSource-schema-model-field name="firstName"	type="string">
													<kendo:dataSource-schema-model-field-validation required="true" />
												</kendo:dataSource-schema-model-field>
												<kendo:dataSource-schema-model-field name="lastName"	type="string" >
													<kendo:dataSource-schema-model-field-validation required="true" />
												</kendo:dataSource-schema-model-field>
												<kendo:dataSource-schema-model-field name="fatherName"	type="string">
													 <kendo:dataSource-schema-model-field-validation required="true" />
												</kendo:dataSource-schema-model-field>
												<kendo:dataSource-schema-model-field name="sex"	type="string" >
													<kendo:dataSource-schema-model-field-validation required="true" />
												</kendo:dataSource-schema-model-field>
												<kendo:dataSource-schema-model-field name="dob"	type="date" >
													<kendo:dataSource-schema-model-field-validation required="true" />
												</kendo:dataSource-schema-model-field>
												<kendo:dataSource-schema-model-field name="workNature"	type="string" >
													<kendo:dataSource-schema-model-field-validation required="true" />
												</kendo:dataSource-schema-model-field>
											</kendo:dataSource-schema-model-fields>
										</kendo:dataSource-schema-model>
									</kendo:dataSource-schema>
								</kendo:dataSource>
							</kendo:grid>
						</div>	
						
						
						<div class="tab-pane" id="tab3">
							<center><h3>Family Details</h3></center>
							<br>
							<c:url value="/cob/create/family" var="createFamUrl" />
							<kendo:grid name="cobFamilyGrid" groupable="true" sortable="true" edit="familyCobEvent">
								<kendo:grid-pageable refresh="true" pageSizes="true"
									buttonCount="5">
								</kendo:grid-pageable>
								<kendo:grid-editable mode="popup" />
								<kendo:grid-toolbar>
									<kendo:grid-toolbarItem name="create" text="Add Family" />
								</kendo:grid-toolbar>
								<kendo:grid-columns>
									<kendo:grid-column title="Name" field="fullName" width="110" />
									<kendo:grid-column title="First Name" field="firstName" width="110" hidden="true"/>
									<kendo:grid-column title="Last Name" field="lastName" width="110" hidden="true"/>
									<kendo:grid-column title="Father Name" field="fatherName" width="110" />
									<kendo:grid-column title="Sex" field="sex" width="110" >
										<kendo:grid-column-values value="${sex}"/>
									</kendo:grid-column>
									<kendo:grid-column title="Date Of Birth" field="dob" width="110" format="{0:dd/MM/yyyy}"/>
									<kendo:grid-column title="Relationship" field="fpRelationship" width="110" editor="relationEditor" />
								</kendo:grid-columns>
								<kendo:dataSource pageSize="10">

									<kendo:dataSource-transport>
										<kendo:dataSource-transport-create url="${createFamUrl}" dataType="json" type="GET" contentType="application/json" />
										<kendo:dataSource-transport-update url="${updateUrl}"	dataType="json" type="GET" contentType="application/json" />
										<kendo:dataSource-transport-destroy url="${destroyUrl}/" dataType="json" type="GET" contentType="application/json" />
									</kendo:dataSource-transport>


									<kendo:dataSource-schema>
										<kendo:dataSource-schema-model id="personId">
												<kendo:dataSource-schema-model-fields>
												
												
												<kendo:dataSource-schema-model-field name="firstName"	type="string">
												
													<kendo:dataSource-schema-model-field-validation required="true" />
												</kendo:dataSource-schema-model-field>
												<kendo:dataSource-schema-model-field name="lastName"	type="string" >
													<kendo:dataSource-schema-model-field-validation required="true" />
												</kendo:dataSource-schema-model-field>
												<kendo:dataSource-schema-model-field name="fatherName"	type="string">
													 <kendo:dataSource-schema-model-field-validation required="true" />
												</kendo:dataSource-schema-model-field>
												<kendo:dataSource-schema-model-field name="sex"	type="string" >
													<kendo:dataSource-schema-model-field-validation required="true" />
												</kendo:dataSource-schema-model-field>
												<kendo:dataSource-schema-model-field name="dob"	type="date" >
													<kendo:dataSource-schema-model-field-validation required="true" />
												</kendo:dataSource-schema-model-field>
												<kendo:dataSource-schema-model-field name="fpRelationship"	type="string" >
													<kendo:dataSource-schema-model-field-validation required="true" />
												</kendo:dataSource-schema-model-field>
											</kendo:dataSource-schema-model-fields>
										</kendo:dataSource-schema-model>
									</kendo:dataSource-schema>
								</kendo:dataSource>
							</kendo:grid>


						</div>
						
						
						<%-- <div class="tab-pane" id="tab3">
							<center><h3>Family Details</h3></center>
							<br>
							<table>
							
							<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="firstNameField">First Name</label>
											<div class="controls">
												<form:input required="required"   id="firstNameField" path="firstNameField" class="required firstNameField"/>
											</div>
										</div>
									</td>
									<td>
										<div class="control-group">
											<label class="control-label" for="lastNameField">Last Name</label>
											<div class="controls">
												<form:input required="required"   id="lastNameField" path="lastNameField" class="required lastNameField"/>
											</div>
										</div>
									</td>
							</tr>
									
							
								<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="relationshipField">Relationship</label>
											<div class="controls">
												<form:input required="required"   id="relationshipField" path="relationshipField" class="required relationshipField"/>
											</div>
										</div>
									</td>
									<td>
										<div class="control-group">
											<label class="control-label" for="sexField">Sex</label>
											<div class="controls">
												<form:input required="required"   id="sexField" path="sexField"
													class="required sexField"/>
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="ageField">Age</label>
											<div class="controls">
												<form:input required="required"   id="ageField" path="ageField" class="required ageField"/>
											</div>
										</div>
									</td>
									<td>
										<div class="control-group">
											<label class="control-label" for="mobileField">Mobile</label>
											<div class="controls">
												<form:input required="required"   id="mobileField" path="mobileField" class="required mobileField"/>
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="bloodGroupField">Blood Group</label>
											<div class="controls">
												<form:input required="required"   id="bloodGroupField" path="bloodGroupField" class="required bloodGroupField"/>
											</div>
										</div>
									</td>
								</tr>							
							</table>
						</div>
						
						<div class="tab-pane" id="tab3">
							<center><h3>Family Details</h3></center>
							<br>
							<table>
							
							<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="firstNameField">First Name</label>
											<div class="controls">
												<form:input required="required"   id="firstNameField" path="firstNameField" class="required firstNameField"/>
											</div>
										</div>
									</td>
									<td>
										<div class="control-group">
											<label class="control-label" for="lastNameField">Last Name</label>
											<div class="controls">
												<form:input required="required"   id="lastNameField" path="lastNameField" class="required lastNameField"/>
											</div>
										</div>
									</td>
							</tr>
									
							
								<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="relationshipField">Relationship</label>
											<div class="controls">
												<form:input required="required"   id="relationshipField" path="relationshipField" class="required relationshipField"/>
											</div>
										</div>
									</td>
									<td>
										<div class="control-group">
											<label class="control-label" for="sexField">Sex</label>
											<div class="controls">
												<form:input required="required"   id="sexField" path="sexField"
													class="required sexField"/>
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="ageField">Age</label>
											<div class="controls">
												<form:input required="required"   id="ageField" path="ageField" class="required ageField"/>
											</div>
										</div>
									</td>
									<td>
										<div class="control-group">
											<label class="control-label" for="mobileField">Mobile</label>
											<div class="controls">
												<form:input required="required"   id="mobileField" path="mobileField" class="required mobileField"/>
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="bloodGroupField">Blood Group</label>
											<div class="controls">
												<form:input required="required"   id="bloodGroupField" path="bloodGroupField" class="required bloodGroupField"/>
											</div>
										</div>
									</td>
								</tr>							
							</table>
						</div>
						
						<div class="tab-pane" id="tab4">
							<center><h3>Domestic Help Details</h3></center>
							<br>
							<table>
							
							<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="firstNameField">First Name</label>
											<div class="controls">
												<form:input required="required"   id="firstNameField" path="firstNameField" class="required firstNameField"/>
											</div>
										</div>
									</td>
									<td>
										<div class="control-group">
											<label class="control-label" for="lastNameField">Last Name</label>
											<div class="controls">
												<form:input required="required"   id="lastNameField" path="lastNameField" class="required lastNameField"/>
											</div>
										</div>
									</td>
							</tr>
									
							
								<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="natureOfWorkField">Nature Of Work</label>
											<div class="controls">
												<form:input required="required"   id="natureOfWorkField" path="natureOfWorkField" class="required natureOfWorkField"/>
											</div>
										</div>
									</td>
									<td>
										<div class="control-group">
											<label class="control-label" for="sexField">Sex</label>
											<div class="controls">
												<form:input required="required"   id="sexField" path="sexField"
													class="required sexField"/>
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="ageField">Age</label>
											<div class="controls">
												<form:input required="required"   id="ageField" path="ageField" class="required ageField"/>
											</div>
										</div>
									</td>
									<td>
										<div class="control-group">
											<label class="control-label" for="mobileField">Mobile</label>
											<div class="controls">
												<form:input required="required"   id="mobileField" path="mobileField" class="required mobileField"/>
											</div>
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<div class="control-group">
											<label class="control-label" for="bloodGroupField">Blood Group</label>
											<div class="controls">
												<form:input required="required"   id="bloodGroupField" path="bloodGroupField" class="required bloodGroupField"/>
											</div>
										</div>
									</td>
								</tr>							
							</table>
						</div> --%>
						
						<%-- 
						
						
						
						
						
						<div class="tab-pane" id="tab8">
							<center><h3>Document Details</h3></center>
							<br>
							<table>					
							</table>
						</div>
						
						--%>
						
						
						<ul class="pager wizard">
							<li class="previous first" style="display: none;"><a href="#">First</a></li>
							<li class="previous"><a href="#">Previous</a></li>
							<li class="next last" style="display: none;"><a href="#">Last</a></li>
							<li class="next"><a href="#">Next</a></li>
						</ul>
					</div>					
				</div>
				
			</form:form>
		</section>
	</div>
</div>

<style>
.container {
	float: none;
	min-height: 0px;
	font-size:13px 
}

label, input, button, select, textarea {
    font-size: 13px;
    font-weight: normal;
    line-height: 20px;
}

.tabs-left>.nav-tabs .active>a {
	background: url("../images/backgrounds/top.jpg") repeat-x
}

.tabs-left>li>a {
	background: url("../images/backgrounds/top.jpg") repeat-x
}
.form-horizontal .controls {
    margin-left: 164px;
}

.nav-pills > li > a {
    height: 26px;
 }

.k-dropdown {
	width: 100%
}

.tab-content > .active, .pill-content > .active {
    display: block;
   min-height: 330px;
}
.tab-pane{
	border: 2px solid;
}

.progress {
    height: 8px;
     margin-bottom: 10px;
 }
    
h3{
    background: black;
    color: white
}
    
.k-window .k-widget {
    width: 175px;
    z-index: 0;
}

.addressTextArea{
	width: 219px; 
	height: 142px;
}
</style>