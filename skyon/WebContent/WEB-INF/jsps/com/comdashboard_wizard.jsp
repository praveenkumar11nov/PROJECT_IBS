<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- Usual wizard with ajax -->
		<div class="fluid">
			<div class="formRow">
                            <div class="grid3"><label>Person type:</label></div>
                            <div class="grid9">
								<select data-placeholder="Select" class="select" style="float:left;width:250px">
                                    <option value=""></option> 
                                    <option value="Individual">Individual</option> 
                                    <option value="Company">Company</option> 
                                    <option value="Proprietor">Proprietor</option> 
                                    <option value="LLP">LLP</option>
                                    <option value="Partnership">Partnership</option> 
                                </select>
              </div>
              </div>                  
            <div class="widget grid6">
                <div class="whead"><h6>Create Person</h6></div>
                <form:form id="wizard1" commandName="person" method="post" action="./createPersonDashboard" class="main">
                    <fieldset class="step" id="w1first">
                        <h1>Person Details</h1>
                        <div class="formRow">
                            <div class="grid3"><label>Title:</label></div>
                            <div class="grid9"><form:select path="title">
    												<form:options items="${title}" />
												</form:select>
						    </div>
                        </div>
                        <div class="formRow">
                            <div class="grid3"><label>First Name:</label></div>
                            <div class="grid9"><form:input path="firstName" /></div>
                        </div>
                        <div class="formRow">
                            <div class="grid3"><label>Middle Name :</label></div>
                            <div class="grid9"><form:input path="middleName" /></div>
                        </div>
                        <div class="formRow">
                            <div class="grid3"><label>Father Name :</label></div>
                            <div class="grid9"><form:input path="fatherName" /></div>
                        </div>
                        <div class="formRow">
                            <div class="grid3"><label>Marital Status :</label></div>
                            <div class="grid9"><form:select path="maritalStatus">
    												<form:options items="${maritalStatus}" />
												</form:select>
							</div>
                        </div>
                    
                        <div class="formRow">
                            <div class="grid3"><label>Sex :</label></div>
                            <div class="grid9"><form:select path="sex">
    												<form:options items="${sex}" />
												</form:select>
							</div>
						</div>
						<div class="formRow">	
							<div class="grid3"><label>Date of Birth :</label></div>
                            <div class="grid9"><form:input path="dob" cssClass="datepicker"/></div>
						</div>
						
						<div class="formRow">
                            <div class="grid3"><label>Nationality :</label></div>
                            <div class="grid9"><form:select path="nationality">
    												<form:options items="${nationality}" />
												</form:select>
							</div>
						</div>
						
						<div class="formRow">
                            <div class="grid3"><label>Blood Group :</label></div>
                            <div class="grid9"><form:select path="bloodGroup">
    												<form:options items="${bloodGroup}" />
												</form:select>
							</div>
						</div>
						
						<div class="formRow">
                            <div class="grid3"><label>Occupation/Profession :</label></div>
                            <div class="grid9"><form:select path="workNature">
    												<form:options items="${meCategory}" />
												</form:select>
							</div>
						</div>
						
						<div class="formRow">
                            <div class="grid3"><label>Nature of work :</label></div>
                            <div class="grid9"><form:select path="workNature">
    												<form:options items="${workNature}" />
												</form:select>
							</div>
						</div>
						
						<div class="formRow">
                            <div class="grid3"><label>Languages :</label></div>
                            <div class="grid9"><form:select path="languagesKnown">
    												<form:options items="${meCategory}" />
												</form:select>
							</div>
						</div>
                         
                    </fieldset>
                    <fieldset id="addressDetails" class="step">
                        <h1>Contact Details</h1>
                        <div class="formRow">
                            <div class="grid3"><label>Address Type :</label></div>
                            <div class="grid9">
                            <form:select path="address.addressLocation">
    												<form:options items="${addressType}" />
							</form:select>
							</div>
						</div>
						
						<div class="formRow">
                            <div class="grid3"><label>Property No :</label></div>
                            <div class="grid9">
                            <form:input path="address.addressNo" />
							</div>
						</div>
						
						<div class="formRow">
                            <div class="grid3"><label>Address 1 :</label></div>
                            <div class="grid9">
                            <form:textarea path="address.address1" />
							</div>
						</div>
						
						<div class="formRow">
                            <div class="grid3"><label>Address 2 :</label></div>
                            <div class="grid9">
                            <form:textarea path="address.address2" />
							</div>
						</div>
						
						<div class="formRow">
                            <div class="grid3"><label>Address 3 :</label></div>
                            <div class="grid9">
                            <form:textarea path="address.address3" />
							</div>
						</div>
						
						<div class="formRow">
                            <div class="grid3"><label>Country :</label></div>
                            <div class="grid9">
                            <form:select path="address.addressLocation">
    												<form:options items="${addressType}" />
							</form:select>
							</div>
						</div>
						
						<div class="formRow">
                            <div class="grid3"><label>State :</label></div>
                            <div class="grid9">
                            <form:select path="address.addressLocation">
    												<form:options items="${addressType}" />
							</form:select>
							</div>
						</div>
						
						<div class="formRow">
                            <div class="grid3"><label>City :</label></div>
                            <div class="grid9">
                            <form:select path="address.addressLocation">
    												<form:options items="${addressType}" />
							</form:select>
							</div>
						</div>
						
						
						<div class="formRow">
                            <div class="grid3"><label>Mobile :</label></div>
                            <div class="grid9">
                            <form:input path="address.addressNo" />
							</div>
						</div>
						
						<div class="formRow">
                            <div class="grid3"><label>E-mail :</label></div>
                            <div class="grid9">
                            <form:input path="address.addressNo" />
							</div>
						</div>
                     </fieldset> 
                    <fieldset id="propertyDetails" class="step">
                        <h1>Property Details</h1>
                        
						<div class="formRow">
                            <div class="grid3"><label>Tower/Block :</label></div>
                            <div class="grid9">
                            <form:select path="address.addressLocation">
    												<form:options items="${addressType}" />
							</form:select>
							</div>
						</div>
						
						<div class="formRow">
                            <div class="grid3"><label>Property Number :</label></div>
                            <div class="grid9">
                            <form:select path="address.addressLocation">
    												<form:options items="${addressType}" />
							</form:select>
							</div>
						</div>
						
						<div class="formRow">
                            <div class="grid3"><label>Primary Owner :</label></div>
                            <div class="grid9">
                            <form:select path="address.addressLocation">
    												<form:options items="${addressType}" />
							</form:select>
							</div>
						</div>
						
						<div class="formRow">
                            <div class="grid3"><label>Occupied :</label></div>
                            <div class="grid9">
                            <form:select path="address.addressLocation">
    												<form:options items="${addressType}" />
							</form:select>
							</div>
						</div>
						
						
						<div class="formRow">
                            <div class="grid3"><label>Property Acquired Date :</label></div>
                            <div class="grid9">
                            <form:input path="address.addressNo" />
							</div>
						</div>
						
						
						<div class="formRow">
                            <div class="grid3"><label>Vehicle Registration Required :</label></div>
                            <div class="grid9">
                            <form:select path="address.addressLocation">
    												<form:options items="${addressType}" />
							</form:select>
							</div>
						</div>



                     </fieldset> 
                     <fieldset id="DocumentsDetails" class="step">
                        <h1>Documents Upload</h1>
                        Documents Details 
                     </fieldset>   
                    <div class="formRow">
                        <div class="status" id="status1"></div>
                        <div class="formSubmit">
                            <input class="buttonM bDefault" id="back1" value="Back" type="reset" />
                            <input class="buttonM bRed ml10" id="next1" value="Next" type="submit" />
                        </div>
                    </div>
                </form:form>
                <div class="data" id="w1"></div>
            </div>
            </div>
            <script type="text/javascript"
	src="<c:url value='/resources/js/plugins/wizards/jquery.form.wizard.js'/>"></script>
	
            <script>
            
            
            $("#wizard1").formwizard({
        		formPluginEnabled: true, 
        		validationEnabled: false,
        		focusFirstInput : false,
        		disableUIStyles : true,
        	
        		formOptions :{
        			success: function(data){$("#status1").fadeTo(500,1,function(){ $(this).html("<span>Form was submitted!</span>").fadeTo(5000, 0); })},
        			beforeSubmit: function(data){$("#w1").html("<span>Form was submitted with ajax. Data sent to the server </span>");},
        			resetForm: true
        		}
        	});
            
            $(".select").select2();
            
            </script>
            <script type="text/javascript"
	src="<c:url value='/resources/js/plugins/forms/jquery.select2.min.js'/>"></script>