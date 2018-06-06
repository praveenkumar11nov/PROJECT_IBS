<%@include file="/common/taglibs.jsp"%>	

<c:url value="/com/readAllData" var="readUr" />


<div id="loading" ></div>
	<kendo:grid name="grid" pageable="true"
		filterable="true" groupable="true" selectable="true" scrollable="true" reorderable="true" resizable="true" >
		<kendo:grid-pageable pageSizes="true" buttonCount="5"  pageSize="20" input="true" numeric="true" refresh="true" ></kendo:grid-pageable>
		<kendo:grid-filterable extra="false">
		
		 <kendo:grid-filterable-operators>
		  	<kendo:grid-filterable-operators-string eq="Is equal to"/>
		 </kendo:grid-filterable-operators>
			
		</kendo:grid-filterable>


    
    	<kendo:grid-editable mode="popup" confirmation="Are you sure you want to remove this item?"/>
      <kendo:grid-toolbarTemplate>
      <div class="toolbar">
      
	        	<a class='k-button' href='\\#' onclick="clearFilterOwner()"><span class='k-icon k-i-funnel-clear'></span> Clear Filter</a>
	        	
	        	<a class="k-button k-button-icontext k-grid-ownerTemplatesDetailsExport" href="#">
                <span class=" "></span>
                 Export To Excel
                </a>
      
      
      </div>
      
      
      </kendo:grid-toolbarTemplate>
        <kendo:grid-columns>
		<kendo:grid-column title="Title &nbsp; *" field="title" width="80px">


		</kendo:grid-column>
		<kendo:grid-column title="Name &nbsp; *" filterable="false"
			field="name" width="130px">
		</kendo:grid-column>

		<kendo:grid-column title="Block  Name &nbsp; *" filterable="false"
			field="blockName" width="130px">
		</kendo:grid-column>

		<kendo:grid-column title="Flat No &nbsp; *" filterable="false"
			field="flatNo" width="130px">
		</kendo:grid-column>



		<kendo:grid-column title="Document Name" field="docName"
			filterable="true" width="110px" />

		<kendo:grid-column title="Document Number" field="docType"
			filterable="true" width="110px" />

		<kendo:grid-column title="Document Description" field="docNum"
			filterable="true" width="110px" />


		<kendo:grid-column title="Person Type" field="pType" filterable="true"
			width="110px">

		</kendo:grid-column>
		<kendo:grid-column title="Primary Mobile" field="mobile"
			filterable="true" width="150px">

		</kendo:grid-column>
		<kendo:grid-column title="Primary Email " field="email"
			filterable="true" width="150px">
		</kendo:grid-column>
		
		
		
		<kendo:grid-column title="Status " field="pStatus"
			filterable="false" width="150px">
		</kendo:grid-column>
		
		
		
		

		<kendo:grid-column title="Primary Address" field="address1"
			filterable="true" width="150px" />
		<kendo:grid-column title="Secondary Address" field="address2"
			filterable="true" width="150px" />
		<kendo:grid-column title="City" field="city" filterable="true"
			width="150px" />
		<kendo:grid-column title="State" field="state" filterable="true"
			width="150px" />
		<kendo:grid-column title="Country" field="country" filterable="true"
			width="150px" />

		<kendo:grid-column title="Registration Number" field="rNo"
			filterable="true" width="150px" />
		<kendo:grid-column title="Vehicle Make" field="vMk" filterable="true"
			width="150px" />
		<kendo:grid-column title="Vehicle Model" field="vModel"
			filterable="true" width="150px" />
		<kendo:grid-column title="Vehicle Tag" field="vTag" filterable="true"
			width="150px" />
		<kendo:grid-column title="Vehicle Slot" field="vSlot"
			filterable="true" width="150px" />


		<kendo:grid-column title="Driver Name" field="driverName"
			width="120px" />
		<kendo:grid-column title="Work Nature" field="workNature"
			width="120px" />


		<kendo:grid-column title=" Start Date" field="vStd" filterable="true"
			width="150px" />
		<kendo:grid-column title=" End Date" field="vEnd" filterable="true"
			width="150px" />



		<kendo:grid-column title="Block Name" field="block" width="120px" />

		<kendo:grid-column title="Parking Slots" field="parkingSlotsNo"
			width="130px" />

		<kendo:grid-column title="Property Number" field="property"
			width="130px" />


		<kendo:grid-column title="Department Name" field="dept" width="120px" />
		<kendo:grid-column title="Designation Name" field="desg" width="120px" />
		<kendo:grid-column title="Role Name" field="role" width="150px" />

		<kendo:grid-column title="Allotment Date From"
			field="allotmentDateFrom" width="185px" format="{0:dd/MM/yyyy}" />


		<kendo:grid-column title="Acquired Date" field="aquiredDate"
			width="185px" format="{0:dd/MM/yyyy}" />
		<kendo:grid-column title="Possession Date" field="possessionDate"
			width="185px" format="{0:dd/MM/yyyy}" />

		<kendo:grid-column title="Allotment Date To" field="allotmentDateTo"
			width="180px" format="{0:dd/MM/yyyy}" />
		<kendo:grid-column title="Rent Rate" field="psRent" width="90px" />


		<kendo:grid-column title="Pan Number" field="panNo" width="120px" />
		<kendo:grid-column title="Service Tax" field="serviceTax"
			width="120px" />

		<kendo:grid-column title="FatherName" field="fatherName" width="90px" />
		<kendo:grid-column title="Nationality" field="nationality"
			width="90px" />
		<kendo:grid-column title="Status" field="status" width="90px" />


		<kendo:grid-column title="Asset Name" field="assetName" width="120px" />
		<kendo:grid-column title="Asset Category " field="assetCatHierarchy"
			width="120px" />
		<kendo:grid-column title="Asset Type" field="assetType" width="120px" />
		<kendo:grid-column title="OwnerShip" field="ownerShip" width="120px" />
		<kendo:grid-column title="Asset Location" field="assetLocHierarchy"
			width="120px" />
		<kendo:grid-column title="Asset Tag" field="assetTag" width="120px" />
		<kendo:grid-column title="vendor Name" field="vendorName"
			width="120px" />
		<kendo:grid-column title="purchase date" field="purchaseDate"
			width="120px" format="{0:dd/MM/yyyy}" />
		<kendo:grid-column title="Status" field="assetStatus" width="120px" />

		<kendo:grid-column title="Asset Geo Tag" field="assetGeoTag"
			width="120px" />
		<kendo:grid-column title="Asset Manufacture" field="assetManufacturer"
			width="120px" />
		<kendo:grid-column title="Asset Model No" field="assetModelNo"
			width="120px" />
		<kendo:grid-column title="Asset Spare" field="assetSpare"
			width="120px" />
		<kendo:grid-column title="Spare Model No" field="partModelNumber"
			width="120px" />
		<kendo:grid-column title="Maintenance Cost" field="assetMainCost"
			width="120px" />



		<kendo:grid-column title="Visitor Name" field="visitorName"
			width="120px" />
		<kendo:grid-column title="Visitor Contact" field="visitorContact"
			width="120px" />
		<kendo:grid-column title="Visitor From" field="visitorFrom"
			width="120px" />
		<kendo:grid-column title="Visitor Purpose" field="visitorPurpose"
			width="120px" />
		<kendo:grid-column title="Visiting Property" field="vistorProperty"
			width="120px" />
		<kendo:grid-column title="Visitor In Date" field="visitorInDate"
			width="120px" />
		<kendo:grid-column title="Status" field="visitorStatus" width="120px" />
		<kendo:grid-column title="Visitor Out Date" field="visitorOutDate"
			width="120px" />
		<kendo:grid-column title="In Time" field="inTime" width="120px" />
		<kendo:grid-column title="Out Time" field="outTime" width="120px" />


		<kendo:grid-column title="Property Type" field="propertyType"
			width="120px" />
		<kendo:grid-column title="Property Floor" field="property_Floor"
			width="120px" />
		<kendo:grid-column title="Area" field="area" width="120px" />
		<kendo:grid-column title="Area Type" field="areaType" width="120px" />


		<kendo:grid-column title="Languages" field="languages" width="120px" />
		<kendo:grid-column title="DOB" field="dob" width="120px" />
		<kendo:grid-column title="Gender" field="gender" width="120px" />
		<kendo:grid-column title="Access CardNo" field="accessCardNo"
			width="120px" />

		<!--Advance Bill Amount  -->
		<kendo:grid-column title="Account&nbsp;No" field="accountNo"
			filterable="true" width="90px" />
		<kendo:grid-column title="Person&nbsp;Name" field="personName"
			width="120px" filterable="false" />
		<kendo:grid-column title="Property&nbsp;No&nbsp;*" field="property_No"
			filterable="true" width="90px" />
		<kendo:grid-column title="Bill&nbsp;No" field="abBillNo" width="80px"
			filterable="true" />
		<kendo:grid-column title="Post&nbsp;Type" field="postType"
			width="90px" filterable="true" />
		<kendo:grid-column title="Advance&nbsp;Amount" field="abBillAmount"
			format="{0:#.00}" width="130px" filterable="true" />
		<kendo:grid-column title="Advance Bill Amount" field="abBillDate"
			width="150px" filterable="true" />



	</kendo:grid-columns>
        <kendo:dataSource  requestStart="requestStart" requestEnd="requestEnd">
            <kendo:dataSource-transport>
            
                <kendo:dataSource-transport-read url="${readUr}/${data}" dataType="json" type="POST" contentType="application/json"/>
             
            </kendo:dataSource-transport>
            <kendo:dataSource-schema parse="parse">
                <kendo:dataSource-schema-model id="dept_Id">
                 <kendo:dataSource-schema-model-fields>
                        <kendo:dataSource-schema-model-field name="dept_Id" editable="false">
                        </kendo:dataSource-schema-model-field>
                    
                   
                        <kendo:dataSource-schema-model-field name="dept_Name" type="string"/>
                        	
                        
                        <kendo:dataSource-schema-model-field name="dept_Desc" type="string">
                        </kendo:dataSource-schema-model-field>
                         
                         <kendo:dataSource-schema-model-field name="dept_Status"   type="string"/>
                      
                   
                        
                    </kendo:dataSource-schema-model-fields>
                </kendo:dataSource-schema-model>
            </kendo:dataSource-schema>
        </kendo:dataSource>
    </kendo:grid>
    <div id="alertsBox" title="Alert"></div>   
    <script type="text/javascript">
					function requestStart() {

						kendo.ui.progress($("#loading"), true);

					}

					function requestEnd() {
						kendo.ui.progress($("#loading"), false);

					}

					function parse(response) {
						$.each(response, function(idx, elem) {
							if (elem.vStd === null) {
								elem.vStd = "";
							} else {
								elem.vStd = kendo.parseDate(
										new Date(elem.vStd), 'dd/MM/yyyy');
							}

							if (elem.vEnd === null) {
								elem.vEnd = "";
							} else {
								elem.vEnd = kendo.parseDate(
										new Date(elem.vEnd), 'dd/MM/yyyy');
							}
							/* if(elem.visitorInDate=== null){
							   elem.visitorInDate = "";
							}else{
							   elem.visitorInDate = kendo.parseDate(new Date(elem.visitorInDate),'dd/MM/yyyy');
							} */
						});

						return response;
					}

					function clearFilterOwner() {
						//custom actions
						$("form.k-filter-menu button[type='reset']").slice()
								.trigger("click");
						var grid = $("#grid").data("kendoGrid");
						grid.dataSource.read();
						grid.refresh();
					}

					$(document).ready(function() {

						$("#menu1thirdlevel").remove();
						$("#menu2thirdlevel").remove();
						$("#menu3thirdlevel").remove();
						$("#menu4thirdlevel").remove();
						$("#menu5thirdlevel").remove();
						$("#menu6thirdlevel").remove();
						$("#menu7thirdlevel").remove();
						$("#menu8thirdlevel").remove();
						$("#menu9thirdlevel").remove();
						$("#menu10thirdlevel").remove();
						$("#menu12thirdlevel").remove();
						$("#menu13thirdlevel").remove();
						$("#menu14thirdlevel").remove();
						$("#menu15thirdlevel").remove();
						$("#menu16thirdlevel").remove();
						$("#menu17thirdlevel").remove();
						$("#menu18thirdlevel").remove();
						$("#menu19thirdlevel").remove();
						$("#menu20thirdlevel").remove();
						$("#menu21thirdlevel").remove();
						$("#menu22thirdlevel").remove();
						var grid = $("#grid").data("kendoGrid");

						if ("${data}" == 8)//primary contacts
						{

							grid.hideColumn("docName");
							grid.hideColumn("docNum");
							grid.hideColumn("docType");

							grid.hideColumn("address1");
							grid.hideColumn("address2");
							grid.hideColumn("city");
							grid.hideColumn("state");
							grid.hideColumn("country");

							grid.hideColumn("rNo");
							grid.hideColumn("vMk");
							grid.hideColumn("vModel");
							grid.hideColumn("vSlot");
							grid.hideColumn("vTag");
							grid.hideColumn("vStd");
							grid.hideColumn("vEnd");

							grid.hideColumn("block");
							grid.hideColumn("parkingSlotsNo");
							grid.hideColumn("property");
							grid.hideColumn("allotmentDateFrom");
							grid.hideColumn("allotmentDateTo");
							grid.hideColumn("psRent");

							grid.hideColumn("fatherName");
							grid.hideColumn("nationality");
							grid.hideColumn("status");

							grid.hideColumn("blockName");
							grid.hideColumn("aquiredDate");
							grid.hideColumn("possessionDate");

							grid.hideColumn("assetName");
							grid.hideColumn("assetCatHierarchy");
							grid.hideColumn("assetType");
							grid.hideColumn("ownerShip");
							grid.hideColumn("assetLocHierarchy");
							grid.hideColumn("assetTag");
							grid.hideColumn("assetStatus");
							grid.hideColumn("vendorName");
							grid.hideColumn("purchaseDate");

							grid.hideColumn("panNo");
							grid.hideColumn("serviceTax");

							grid.hideColumn("visitorName");
							grid.hideColumn("visitorContact");
							grid.hideColumn("visitorFrom");
							grid.hideColumn("visitorPurpose");
							grid.hideColumn("vistorProperty");
							grid.hideColumn("visitorInDate");
							grid.hideColumn("visitorStatus");

							grid.hideColumn("dept");
							grid.hideColumn("desg");
							grid.hideColumn("role");

							grid.hideColumn("driverName");
							grid.hideColumn("workNature");

							grid.hideColumn("languages");
							grid.hideColumn("dob");
							grid.hideColumn("gender");
							grid.hideColumn("accessCardNo");
							grid.hideColumn("accountNo");
							grid.hideColumn("personName");

							grid.hideColumn("property_No");
							grid.hideColumn("abBillNo");
							grid.hideColumn("postType");
							grid.hideColumn("abBillAmount");
							grid.hideColumn("outTime");

							grid.hideColumn("propertyType");
							grid.hideColumn("property_Floor");
							grid.hideColumn("area");
							grid.hideColumn("areaType");
							grid.hideColumn("inTime");
							grid.hideColumn("outTime");
							grid.hideColumn("visitorOutDate");
							grid.hideColumn("assetGeoTag");
							grid.hideColumn("assetManufacturer");
							grid.hideColumn("assetModelNo");
							grid.hideColumn("assetSpare");
							grid.hideColumn("partModelNumber");
							grid.hideColumn("assetMainCost");
							grid.hideColumn("abBillDate");
							grid.hideColumn("pStatus");
							

						}

						if ("${data}" == 14)//primary contacts
						{

							grid.hideColumn("docName");
							grid.hideColumn("docNum");
							grid.hideColumn("docType");

							grid.hideColumn("address1");
							grid.hideColumn("address2");
							grid.hideColumn("city");
							grid.hideColumn("state");
							grid.hideColumn("country");

							grid.hideColumn("rNo");
							grid.hideColumn("vMk");
							grid.hideColumn("vModel");
							grid.hideColumn("vSlot");
							grid.hideColumn("vTag");
							grid.hideColumn("vStd");
							grid.hideColumn("vEnd");

							grid.hideColumn("block");
							grid.hideColumn("parkingSlotsNo");
							grid.hideColumn("property");
							grid.hideColumn("allotmentDateFrom");
							grid.hideColumn("allotmentDateTo");
							grid.hideColumn("psRent");

							grid.hideColumn("fatherName");
							grid.hideColumn("nationality");
							grid.hideColumn("status");

							grid.hideColumn("blockName");
							grid.hideColumn("aquiredDate");
							grid.hideColumn("possessionDate");

							grid.hideColumn("assetName");
							grid.hideColumn("assetCatHierarchy");
							grid.hideColumn("assetType");
							grid.hideColumn("ownerShip");
							grid.hideColumn("assetLocHierarchy");
							grid.hideColumn("assetTag");
							grid.hideColumn("assetStatus");
							grid.hideColumn("vendorName");
							grid.hideColumn("purchaseDate");

							grid.hideColumn("panNo");
							grid.hideColumn("serviceTax");

							grid.hideColumn("visitorName");
							grid.hideColumn("visitorContact");
							grid.hideColumn("visitorFrom");
							grid.hideColumn("visitorPurpose");
							grid.hideColumn("vistorProperty");
							grid.hideColumn("visitorInDate");
							grid.hideColumn("visitorStatus");

							grid.hideColumn("dept");
							grid.hideColumn("desg");
							grid.hideColumn("role");

							grid.hideColumn("driverName");
							grid.hideColumn("workNature");

							grid.hideColumn("languages");
							grid.hideColumn("dob");
							grid.hideColumn("gender");
							grid.hideColumn("accessCardNo");
							grid.hideColumn("visitorOutDate");
							grid.hideColumn("inTime");
							grid.hideColumn("outTime");
							grid.hideColumn("accountNo");
							grid.hideColumn("personName");

							grid.hideColumn("property_No");
							grid.hideColumn("abBillNo");
							grid.hideColumn("postType");
							grid.hideColumn("abBillAmount");
							grid.hideColumn("propertyType");
							grid.hideColumn("property_Floor");
							grid.hideColumn("area");
							grid.hideColumn("areaType");

							grid.hideColumn("assetGeoTag");
							grid.hideColumn("assetManufacturer");
							grid.hideColumn("assetModelNo");
							grid.hideColumn("assetSpare");
							grid.hideColumn("partModelNumber");
							grid.hideColumn("assetMainCost");
							grid.hideColumn("abBillDate");
							grid.hideColumn("pStatus");

						}

						if ("${data}" == 3)//owner address
						{

							grid.hideColumn("docName");
							grid.hideColumn("docNum");
							grid.hideColumn("docType");

							grid.hideColumn("mobile");
							grid.hideColumn("email");

							grid.hideColumn("rNo");
							grid.hideColumn("vMk");
							grid.hideColumn("vModel");
							grid.hideColumn("vSlot");
							grid.hideColumn("vTag");
							grid.hideColumn("vStd");
							grid.hideColumn("vEnd");

							grid.hideColumn("block");
							grid.hideColumn("parkingSlotsNo");
							grid.hideColumn("property");
							grid.hideColumn("allotmentDateFrom");
							grid.hideColumn("allotmentDateTo");
							grid.hideColumn("psRent");

							grid.hideColumn("fatherName");
							grid.hideColumn("nationality");
							grid.hideColumn("status");

							grid.hideColumn("blockName");
							grid.hideColumn("aquiredDate");
							grid.hideColumn("possessionDate");
							grid.hideColumn("assetName");
							grid.hideColumn("assetCatHierarchy");
							grid.hideColumn("assetType");
							grid.hideColumn("ownerShip");
							grid.hideColumn("assetLocHierarchy");
							grid.hideColumn("assetTag");
							grid.hideColumn("assetStatus");
							grid.hideColumn("vendorName");
							grid.hideColumn("purchaseDate");

							grid.hideColumn("panNo");
							grid.hideColumn("serviceTax");

							grid.hideColumn("visitorName");
							grid.hideColumn("visitorContact");
							grid.hideColumn("visitorFrom");
							grid.hideColumn("visitorPurpose");
							grid.hideColumn("vistorProperty");
							grid.hideColumn("visitorInDate");
							grid.hideColumn("visitorStatus");

							grid.hideColumn("dept");
							grid.hideColumn("desg");
							grid.hideColumn("role");

							grid.hideColumn("driverName");
							grid.hideColumn("workNature");

							grid.hideColumn("languages");
							grid.hideColumn("dob");
							grid.hideColumn("gender");
							grid.hideColumn("accessCardNo");
							grid.hideColumn("visitorOutDate");
							grid.hideColumn("inTime");

							grid.hideColumn("accountNo");
							grid.hideColumn("personName");

							grid.hideColumn("property_No");
							grid.hideColumn("abBillNo");
							grid.hideColumn("postType");
							grid.hideColumn("abBillAmount");

							grid.hideColumn("propertyType");
							grid.hideColumn("property_Floor");
							grid.hideColumn("area");
							grid.hideColumn("areaType");
							grid.hideColumn("outTime");

							grid.hideColumn("assetGeoTag");
							grid.hideColumn("assetManufacturer");
							grid.hideColumn("assetModelNo");
							grid.hideColumn("assetSpare");
							grid.hideColumn("partModelNumber");
							grid.hideColumn("assetMainCost");
							grid.hideColumn("abBillDate");
							grid.hideColumn("pStatus");
							
							
						}
						if ("${data}" == 4)//tenat adddress 
						{

							grid.hideColumn("docName");
							grid.hideColumn("docNum");
							grid.hideColumn("docType");

							grid.hideColumn("mobile");
							grid.hideColumn("email");

							grid.hideColumn("rNo");
							grid.hideColumn("vMk");
							grid.hideColumn("vModel");
							grid.hideColumn("vSlot");
							grid.hideColumn("vTag");
							grid.hideColumn("vStd");
							grid.hideColumn("vEnd");

							grid.hideColumn("block");
							grid.hideColumn("parkingSlotsNo");
							grid.hideColumn("property");
							grid.hideColumn("allotmentDateFrom");
							grid.hideColumn("allotmentDateTo");
							grid.hideColumn("psRent");

							grid.hideColumn("fatherName");
							grid.hideColumn("nationality");
							grid.hideColumn("status");

							grid.hideColumn("blockName");
							grid.hideColumn("aquiredDate");
							grid.hideColumn("possessionDate");
							grid.hideColumn("assetName");
							grid.hideColumn("assetCatHierarchy");
							grid.hideColumn("assetType");
							grid.hideColumn("ownerShip");
							grid.hideColumn("assetLocHierarchy");
							grid.hideColumn("assetTag");
							grid.hideColumn("assetStatus");
							grid.hideColumn("vendorName");
							grid.hideColumn("purchaseDate");

							grid.hideColumn("panNo");
							grid.hideColumn("serviceTax");

							grid.hideColumn("visitorName");
							grid.hideColumn("visitorContact");
							grid.hideColumn("visitorFrom");
							grid.hideColumn("visitorPurpose");
							grid.hideColumn("vistorProperty");
							grid.hideColumn("visitorInDate");
							grid.hideColumn("visitorStatus");

							grid.hideColumn("dept");
							grid.hideColumn("desg");
							grid.hideColumn("role");

							grid.hideColumn("driverName");
							grid.hideColumn("workNature");

							grid.hideColumn("languages");
							grid.hideColumn("dob");
							grid.hideColumn("gender");
							grid.hideColumn("accessCardNo");
							grid.hideColumn("visitorOutDate");
							grid.hideColumn("inTime");

							grid.hideColumn("accountNo");
							grid.hideColumn("personName");

							grid.hideColumn("property_No");
							grid.hideColumn("abBillNo");
							grid.hideColumn("postType");
							grid.hideColumn("abBillAmount");
							grid.hideColumn("outTime");
							grid.hideColumn("propertyType");
							grid.hideColumn("property_Floor");
							grid.hideColumn("area");
							grid.hideColumn("areaType");

							grid.hideColumn("assetGeoTag");
							grid.hideColumn("assetManufacturer");
							grid.hideColumn("assetModelNo");
							grid.hideColumn("assetSpare");
							grid.hideColumn("partModelNumber");
							grid.hideColumn("assetMainCost");
							grid.hideColumn("abBillDate");
							grid.hideColumn("pStatus");

						}
						if ("${data}" == 19)//vehicle information
						{

							grid.hideColumn("docName");
							grid.hideColumn("docNum");
							grid.hideColumn("docType");

							grid.hideColumn("mobile");
							grid.hideColumn("email");
							grid.hideColumn("address1");
							grid.hideColumn("address2");
							grid.hideColumn("city");
							grid.hideColumn("state");
							grid.hideColumn("country");

							grid.hideColumn("block");
							grid.hideColumn("parkingSlotsNo");
							grid.hideColumn("property");
							grid.hideColumn("allotmentDateFrom");
							grid.hideColumn("allotmentDateTo");
							grid.hideColumn("psRent");

							grid.hideColumn("fatherName");
							grid.hideColumn("nationality");
							grid.hideColumn("status");

							grid.hideColumn("blockName");
							grid.hideColumn("aquiredDate");
							grid.hideColumn("possessionDate");
							grid.hideColumn("assetName");
							grid.hideColumn("assetCatHierarchy");
							grid.hideColumn("assetType");
							grid.hideColumn("ownerShip");
							grid.hideColumn("assetLocHierarchy");
							grid.hideColumn("assetTag");
							grid.hideColumn("assetStatus");
							grid.hideColumn("vendorName");
							grid.hideColumn("purchaseDate");

							grid.hideColumn("panNo");
							grid.hideColumn("serviceTax");

							grid.hideColumn("visitorName");
							grid.hideColumn("visitorContact");
							grid.hideColumn("visitorFrom");
							grid.hideColumn("visitorPurpose");
							grid.hideColumn("vistorProperty");
							grid.hideColumn("visitorInDate");
							grid.hideColumn("visitorStatus");

							grid.hideColumn("dept");
							grid.hideColumn("desg");
							grid.hideColumn("role");

							grid.hideColumn("driverName");
							grid.hideColumn("workNature");

							grid.hideColumn("languages");
							grid.hideColumn("dob");
							grid.hideColumn("gender");
							grid.hideColumn("accessCardNo");
							grid.hideColumn("visitorOutDate");
							grid.hideColumn("inTime");

							grid.hideColumn("accountNo");
							grid.hideColumn("personName");

							grid.hideColumn("property_No");
							grid.hideColumn("abBillNo");
							grid.hideColumn("postType");
							grid.hideColumn("abBillAmount");
							grid.hideColumn("outTime");
							grid.hideColumn("propertyType");
							grid.hideColumn("property_Floor");
							grid.hideColumn("area");
							grid.hideColumn("areaType");

							grid.hideColumn("assetGeoTag");
							grid.hideColumn("assetManufacturer");
							grid.hideColumn("assetModelNo");
							grid.hideColumn("assetSpare");
							grid.hideColumn("partModelNumber");
							grid.hideColumn("assetMainCost");
							grid.hideColumn("abBillDate");
							grid.hideColumn("pStatus");

						}

						if ("${data}" == 2)//alloted parking
						{

							grid.hideColumn("docName");
							grid.hideColumn("docNum");
							grid.hideColumn("docType");

							grid.hideColumn("title");
							grid.hideColumn("name");
							grid.hideColumn("flatNo");
							grid.hideColumn("pType");

							grid.hideColumn("mobile");
							grid.hideColumn("email");

							grid.hideColumn("address1");
							grid.hideColumn("address2");
							grid.hideColumn("city");
							grid.hideColumn("state");
							grid.hideColumn("country");

							grid.hideColumn("rNo");
							grid.hideColumn("vMk");
							grid.hideColumn("vModel");
							grid.hideColumn("vSlot");
							grid.hideColumn("vTag");
							grid.hideColumn("vStd");
							grid.hideColumn("vEnd");

							grid.hideColumn("fatherName");
							grid.hideColumn("nationality");
							grid.hideColumn("status");

							grid.hideColumn("blockName");
							grid.hideColumn("aquiredDate");
							grid.hideColumn("possessionDate");
							grid.hideColumn("assetName");
							grid.hideColumn("assetCatHierarchy");
							grid.hideColumn("assetType");
							grid.hideColumn("ownerShip");
							grid.hideColumn("assetLocHierarchy");
							grid.hideColumn("assetTag");
							grid.hideColumn("assetStatus");
							grid.hideColumn("vendorName");
							grid.hideColumn("purchaseDate");

							grid.hideColumn("panNo");
							grid.hideColumn("serviceTax");

							grid.hideColumn("visitorName");
							grid.hideColumn("visitorContact");
							grid.hideColumn("visitorFrom");
							grid.hideColumn("visitorPurpose");
							grid.hideColumn("vistorProperty");
							grid.hideColumn("visitorInDate");
							grid.hideColumn("visitorStatus");

							grid.hideColumn("dept");
							grid.hideColumn("desg");
							grid.hideColumn("role");

							grid.hideColumn("driverName");
							grid.hideColumn("workNature");

							grid.hideColumn("languages");
							grid.hideColumn("dob");
							grid.hideColumn("gender");
							grid.hideColumn("accessCardNo");
							grid.hideColumn("visitorOutDate");
							grid.hideColumn("inTime");

							grid.hideColumn("accountNo");
							grid.hideColumn("personName");

							grid.hideColumn("property_No");
							grid.hideColumn("abBillNo");
							grid.hideColumn("postType");
							grid.hideColumn("abBillAmount");
							grid.hideColumn("outTime");
							grid.hideColumn("propertyType");
							grid.hideColumn("property_Floor");
							grid.hideColumn("area");
							grid.hideColumn("areaType");

							grid.hideColumn("assetGeoTag");
							grid.hideColumn("assetManufacturer");
							grid.hideColumn("assetModelNo");
							grid.hideColumn("assetSpare");
							grid.hideColumn("partModelNumber");
							grid.hideColumn("assetMainCost");
							grid.hideColumn("abBillDate");
							grid.hideColumn("pStatus");

						}

						if ("${data}" == 17)//un alloted parking slots
						{

							grid.hideColumn("docName");
							grid.hideColumn("docNum");
							grid.hideColumn("docType");

							grid.hideColumn("title");
							grid.hideColumn("name");
							grid.hideColumn("flatNo");
							grid.hideColumn("pType");

							grid.hideColumn("mobile");
							grid.hideColumn("email");

							grid.hideColumn("address1");
							grid.hideColumn("address2");
							grid.hideColumn("city");
							grid.hideColumn("state");
							grid.hideColumn("country");

							grid.hideColumn("rNo");
							grid.hideColumn("vMk");
							grid.hideColumn("vModel");
							grid.hideColumn("vSlot");
							grid.hideColumn("vTag");
							grid.hideColumn("vStd");
							grid.hideColumn("vEnd");

							grid.hideColumn("fatherName");
							grid.hideColumn("nationality");
							grid.hideColumn("status");

							grid.hideColumn("blockName");
							grid.hideColumn("aquiredDate");
							grid.hideColumn("possessionDate");
							grid.hideColumn("assetName");
							grid.hideColumn("assetCatHierarchy");
							grid.hideColumn("assetType");
							grid.hideColumn("ownerShip");
							grid.hideColumn("assetLocHierarchy");
							grid.hideColumn("assetTag");
							grid.hideColumn("assetStatus");
							grid.hideColumn("vendorName");
							grid.hideColumn("purchaseDate");

							grid.hideColumn("panNo");
							grid.hideColumn("serviceTax");

							grid.hideColumn("visitorName");
							grid.hideColumn("visitorContact");
							grid.hideColumn("visitorFrom");
							grid.hideColumn("visitorPurpose");
							grid.hideColumn("vistorProperty");
							grid.hideColumn("visitorInDate");
							grid.hideColumn("visitorStatus");

							grid.hideColumn("dept");
							grid.hideColumn("desg");
							grid.hideColumn("role");

							grid.hideColumn("driverName");
							grid.hideColumn("workNature");

							grid.hideColumn("languages");
							grid.hideColumn("dob");
							grid.hideColumn("gender");
							grid.hideColumn("accessCardNo");
							grid.hideColumn("visitorOutDate");
							grid.hideColumn("inTime");

							grid.hideColumn("accountNo");
							grid.hideColumn("personName");

							grid.hideColumn("property_No");
							grid.hideColumn("abBillNo");
							grid.hideColumn("postType");
							grid.hideColumn("abBillAmount");
							grid.hideColumn("outTime");
							grid.hideColumn("propertyType");
							grid.hideColumn("property_Floor");
							grid.hideColumn("area");
							grid.hideColumn("areaType");

							grid.hideColumn("assetGeoTag");
							grid.hideColumn("assetManufacturer");
							grid.hideColumn("assetModelNo");
							grid.hideColumn("assetSpare");
							grid.hideColumn("partModelNumber");
							grid.hideColumn("assetMainCost");
							grid.hideColumn("abBillDate");
							grid.hideColumn("pStatus");

						}
						if ("${data}" == 6)//Deactivate members
						{

							grid.hideColumn("docName");
							grid.hideColumn("docNum");
							grid.hideColumn("docType");

							grid.hideColumn("address1");
							grid.hideColumn("address2");
							grid.hideColumn("city");
							grid.hideColumn("state");
							grid.hideColumn("country");

							grid.hideColumn("rNo");
							grid.hideColumn("vMk");
							grid.hideColumn("vModel");
							grid.hideColumn("vSlot");
							grid.hideColumn("vTag");
							grid.hideColumn("vStd");
							grid.hideColumn("vEnd");

							grid.hideColumn("block");
							grid.hideColumn("parkingSlotsNo");
							grid.hideColumn("property");
							grid.hideColumn("allotmentDateFrom");
							grid.hideColumn("allotmentDateTo");
							grid.hideColumn("psRent");

							grid.hideColumn("mobile");
							grid.hideColumn("email");

							grid.hideColumn("blockName");
							grid.hideColumn("aquiredDate");
							grid.hideColumn("possessionDate");
							grid.hideColumn("assetName");
							grid.hideColumn("assetCatHierarchy");
							grid.hideColumn("assetType");
							grid.hideColumn("ownerShip");
							grid.hideColumn("assetLocHierarchy");
							grid.hideColumn("assetTag");
							grid.hideColumn("assetStatus");
							grid.hideColumn("vendorName");
							grid.hideColumn("purchaseDate");

							grid.hideColumn("panNo");
							grid.hideColumn("serviceTax");

							grid.hideColumn("visitorName");
							grid.hideColumn("visitorContact");
							grid.hideColumn("visitorFrom");
							grid.hideColumn("visitorPurpose");
							grid.hideColumn("vistorProperty");
							grid.hideColumn("visitorInDate");
							grid.hideColumn("visitorStatus");

							grid.hideColumn("dept");
							grid.hideColumn("desg");
							grid.hideColumn("role");

							grid.hideColumn("driverName");
							grid.hideColumn("workNature");

							grid.hideColumn("languages");
							grid.hideColumn("dob");
							grid.hideColumn("gender");
							grid.hideColumn("accessCardNo");
							grid.hideColumn("visitorOutDate");
							grid.hideColumn("inTime");

							grid.hideColumn("accountNo");
							grid.hideColumn("personName");

							grid.hideColumn("property_No");
							grid.hideColumn("abBillNo");
							grid.hideColumn("postType");
							grid.hideColumn("abBillAmount");
							grid.hideColumn("outTime");
							grid.hideColumn("propertyType");
							grid.hideColumn("property_Floor");
							grid.hideColumn("area");
							grid.hideColumn("areaType");

							grid.hideColumn("assetGeoTag");
							grid.hideColumn("assetManufacturer");
							grid.hideColumn("assetModelNo");
							grid.hideColumn("assetSpare");
							grid.hideColumn("partModelNumber");
							grid.hideColumn("assetMainCost");
							grid.hideColumn("abBillDate");
							grid.hideColumn("pStatus");

						}

						if ("${data}" == 1)//Resdiandial DataBase
						{

							grid.hideColumn("docName");
							grid.hideColumn("docNum");
							grid.hideColumn("docType");

							grid.hideColumn("address1");
							grid.hideColumn("address2");
							grid.hideColumn("city");
							grid.hideColumn("state");
							grid.hideColumn("country");

							grid.hideColumn("rNo");
							grid.hideColumn("vMk");
							grid.hideColumn("vModel");
							grid.hideColumn("vSlot");
							grid.hideColumn("vTag");
							grid.hideColumn("vStd");
							grid.hideColumn("vEnd");

							grid.hideColumn("block");
							grid.hideColumn("parkingSlotsNo");
							grid.hideColumn("property");
							grid.hideColumn("allotmentDateFrom");
							grid.hideColumn("allotmentDateTo");
							grid.hideColumn("psRent");

							grid.hideColumn("fatherName");
							grid.hideColumn("status");

							grid.hideColumn("assetName");
							grid.hideColumn("assetCatHierarchy");
							grid.hideColumn("assetType");
							grid.hideColumn("ownerShip");
							grid.hideColumn("assetLocHierarchy");
							grid.hideColumn("assetTag");
							grid.hideColumn("assetStatus");
							grid.hideColumn("vendorName");
							grid.hideColumn("purchaseDate");

							grid.hideColumn("panNo");
							grid.hideColumn("serviceTax");

							grid.hideColumn("visitorName");
							grid.hideColumn("visitorContact");
							grid.hideColumn("visitorFrom");
							grid.hideColumn("visitorPurpose");
							grid.hideColumn("vistorProperty");
							grid.hideColumn("visitorInDate");
							grid.hideColumn("visitorStatus");

							grid.hideColumn("dept");
							grid.hideColumn("desg");
							grid.hideColumn("role");
							grid.hideColumn("driverName");
							grid.hideColumn("workNature");

							grid.hideColumn("languages");
							grid.hideColumn("dob");
							grid.hideColumn("gender");
							grid.hideColumn("accessCardNo");
							grid.hideColumn("visitorOutDate");
							grid.hideColumn("inTime");

							grid.hideColumn("accountNo");
							grid.hideColumn("personName");

							grid.hideColumn("property_No");
							grid.hideColumn("abBillNo");
							grid.hideColumn("postType");
							grid.hideColumn("abBillAmount");

							grid.hideColumn("propertyType");
							grid.hideColumn("property_Floor");
							grid.hideColumn("area");
							grid.hideColumn("areaType");
							grid.hideColumn("possessionDate");
							grid.hideColumn("outTime");

							grid.hideColumn("assetGeoTag");
							grid.hideColumn("assetManufacturer");
							grid.hideColumn("assetModelNo");
							grid.hideColumn("assetSpare");
							grid.hideColumn("partModelNumber");
							grid.hideColumn("assetMainCost");
							grid.hideColumn("abBillDate");
							grid.hideColumn("pStatus");

						}

						if ("${data}" == 10) {

							grid.hideColumn("docName");
							grid.hideColumn("docNum");
							grid.hideColumn("docType");

							grid.hideColumn("address1");
							grid.hideColumn("address2");
							grid.hideColumn("city");
							grid.hideColumn("state");
							grid.hideColumn("country");

							grid.hideColumn("rNo");
							grid.hideColumn("vMk");
							grid.hideColumn("vModel");
							grid.hideColumn("vSlot");
							grid.hideColumn("vTag");
							grid.hideColumn("vStd");
							grid.hideColumn("vEnd");

							grid.hideColumn("block");
							grid.hideColumn("parkingSlotsNo");
							grid.hideColumn("property");
							grid.hideColumn("allotmentDateFrom");
							grid.hideColumn("allotmentDateTo");
							grid.hideColumn("psRent");

							grid.hideColumn("mobile");
							grid.hideColumn("email");

							grid.hideColumn("aquiredDate");
							grid.hideColumn("possessionDate");
							grid.hideColumn("assetName");
							grid.hideColumn("assetCatHierarchy");
							grid.hideColumn("assetType");
							grid.hideColumn("ownerShip");
							grid.hideColumn("assetLocHierarchy");
							grid.hideColumn("assetTag");
							grid.hideColumn("assetStatus");
							grid.hideColumn("vendorName");
							grid.hideColumn("purchaseDate");

							grid.hideColumn("panNo");
							grid.hideColumn("serviceTax");

							grid.hideColumn("visitorName");
							grid.hideColumn("visitorContact");
							grid.hideColumn("visitorFrom");
							grid.hideColumn("visitorPurpose");
							grid.hideColumn("vistorProperty");
							grid.hideColumn("visitorInDate");
							grid.hideColumn("visitorStatus");

							grid.hideColumn("dept");
							grid.hideColumn("desg");
							grid.hideColumn("role");

							grid.hideColumn("driverName");
							grid.hideColumn("workNature");

							grid.hideColumn("languages");
							grid.hideColumn("dob");
							grid.hideColumn("gender");
							grid.hideColumn("accessCardNo");
							grid.hideColumn("visitorOutDate");
							grid.hideColumn("inTime");

							grid.hideColumn("accountNo");
							grid.hideColumn("personName");

							grid.hideColumn("property_No");
							grid.hideColumn("abBillNo");
							grid.hideColumn("postType");
							grid.hideColumn("abBillAmount");
							grid.hideColumn("outTime");
							grid.hideColumn("propertyType");
							grid.hideColumn("property_Floor");
							grid.hideColumn("area");
							grid.hideColumn("areaType");

							grid.hideColumn("assetGeoTag");
							grid.hideColumn("assetManufacturer");
							grid.hideColumn("assetModelNo");
							grid.hideColumn("assetSpare");
							grid.hideColumn("partModelNumber");
							grid.hideColumn("assetMainCost");
							grid.hideColumn("abBillDate");
							grid.hideColumn("pStatus");

						}

						if ("${data}" == 15)//signature tenant List
						{

							grid.hideColumn("docName");
							grid.hideColumn("docNum");
							grid.hideColumn("docType");

							grid.hideColumn("address1");
							grid.hideColumn("address2");
							grid.hideColumn("city");
							grid.hideColumn("state");
							grid.hideColumn("country");

							grid.hideColumn("rNo");
							grid.hideColumn("vMk");
							grid.hideColumn("vModel");
							grid.hideColumn("vSlot");
							grid.hideColumn("vTag");
							grid.hideColumn("vStd");
							grid.hideColumn("vEnd");

							grid.hideColumn("block");
							grid.hideColumn("parkingSlotsNo");
							grid.hideColumn("property");
							grid.hideColumn("allotmentDateFrom");
							grid.hideColumn("allotmentDateTo");
							grid.hideColumn("psRent");

							grid.hideColumn("mobile");
							grid.hideColumn("email");

							grid.hideColumn("aquiredDate");
							grid.hideColumn("possessionDate");
							grid.hideColumn("assetName");
							grid.hideColumn("assetCatHierarchy");
							grid.hideColumn("assetType");
							grid.hideColumn("ownerShip");
							grid.hideColumn("assetLocHierarchy");
							grid.hideColumn("assetTag");
							grid.hideColumn("assetStatus");
							grid.hideColumn("vendorName");
							grid.hideColumn("purchaseDate");

							grid.hideColumn("panNo");
							grid.hideColumn("serviceTax");

							grid.hideColumn("visitorName");
							grid.hideColumn("visitorContact");
							grid.hideColumn("visitorFrom");
							grid.hideColumn("visitorPurpose");
							grid.hideColumn("vistorProperty");
							grid.hideColumn("visitorInDate");
							grid.hideColumn("visitorStatus");

							grid.hideColumn("dept");
							grid.hideColumn("desg");
							grid.hideColumn("role");

							grid.hideColumn("driverName");
							grid.hideColumn("workNature");

							grid.hideColumn("languages");
							grid.hideColumn("dob");
							grid.hideColumn("gender");
							grid.hideColumn("accessCardNo");
							grid.hideColumn("visitorOutDate");
							grid.hideColumn("inTime");

							grid.hideColumn("accountNo");
							grid.hideColumn("personName");

							grid.hideColumn("property_No");
							grid.hideColumn("abBillNo");
							grid.hideColumn("postType");
							grid.hideColumn("abBillAmount");
							grid.hideColumn("outTime");

							grid.hideColumn("propertyType");
							grid.hideColumn("property_Floor");
							grid.hideColumn("area");
							grid.hideColumn("areaType");

							grid.hideColumn("assetGeoTag");
							grid.hideColumn("assetManufacturer");
							grid.hideColumn("assetModelNo");
							grid.hideColumn("assetSpare");
							grid.hideColumn("partModelNumber");
							grid.hideColumn("assetMainCost");
							grid.hideColumn("abBillDate");
							grid.hideColumn("pStatus");

						}

						if ("${data}" == 5)//alternate address report
						{

							grid.hideColumn("docName");
							grid.hideColumn("docNum");
							grid.hideColumn("docType");

							grid.hideColumn("mobile");
							grid.hideColumn("email");

							grid.hideColumn("rNo");
							grid.hideColumn("vMk");
							grid.hideColumn("vModel");
							grid.hideColumn("vSlot");
							grid.hideColumn("vTag");
							grid.hideColumn("vStd");
							grid.hideColumn("vEnd");

							grid.hideColumn("block");
							grid.hideColumn("parkingSlotsNo");
							grid.hideColumn("property");
							grid.hideColumn("allotmentDateFrom");
							grid.hideColumn("allotmentDateTo");
							grid.hideColumn("psRent");

							grid.hideColumn("fatherName");
							grid.hideColumn("nationality");
							grid.hideColumn("status");

							grid.hideColumn("blockName");
							grid.hideColumn("aquiredDate");
							grid.hideColumn("possessionDate");
							grid.hideColumn("assetName");
							grid.hideColumn("assetCatHierarchy");
							grid.hideColumn("assetType");
							grid.hideColumn("ownerShip");
							grid.hideColumn("assetLocHierarchy");
							grid.hideColumn("assetTag");
							grid.hideColumn("assetStatus");
							grid.hideColumn("vendorName");
							grid.hideColumn("purchaseDate");

							grid.hideColumn("panNo");
							grid.hideColumn("serviceTax");

							grid.hideColumn("visitorName");
							grid.hideColumn("visitorContact");
							grid.hideColumn("visitorFrom");
							grid.hideColumn("visitorPurpose");
							grid.hideColumn("vistorProperty");
							grid.hideColumn("visitorInDate");
							grid.hideColumn("visitorStatus");

							grid.hideColumn("dept");
							grid.hideColumn("desg");
							grid.hideColumn("role");

							grid.hideColumn("driverName");
							grid.hideColumn("workNature");

							grid.hideColumn("languages");
							grid.hideColumn("dob");
							grid.hideColumn("gender");
							grid.hideColumn("accessCardNo");
							grid.hideColumn("visitorOutDate");
							grid.hideColumn("inTime");

							grid.hideColumn("accountNo");
							grid.hideColumn("personName");

							grid.hideColumn("property_No");
							grid.hideColumn("abBillNo");
							grid.hideColumn("postType");
							grid.hideColumn("abBillAmount");
							grid.hideColumn("outTime");
							grid.hideColumn("propertyType");
							grid.hideColumn("property_Floor");
							grid.hideColumn("area");
							grid.hideColumn("areaType");

							grid.hideColumn("assetGeoTag");
							grid.hideColumn("assetManufacturer");
							grid.hideColumn("assetModelNo");
							grid.hideColumn("assetSpare");
							grid.hideColumn("partModelNumber");
							grid.hideColumn("assetMainCost");
							grid.hideColumn("abBillDate");
							grid.hideColumn("pStatus");

						}

						if ("${data}" == 12) {

							grid.hideColumn("docName");
							grid.hideColumn("docNum");
							grid.hideColumn("docType");

							grid.hideColumn("address1");
							grid.hideColumn("address2");
							grid.hideColumn("city");
							grid.hideColumn("state");
							grid.hideColumn("country");

							grid.hideColumn("rNo");
							grid.hideColumn("vMk");
							grid.hideColumn("vModel");
							grid.hideColumn("vSlot");
							grid.hideColumn("vTag");
							grid.hideColumn("vStd");
							grid.hideColumn("vEnd");

							grid.hideColumn("block");
							grid.hideColumn("parkingSlotsNo");
							grid.hideColumn("property");
							grid.hideColumn("allotmentDateFrom");
							grid.hideColumn("allotmentDateTo");
							grid.hideColumn("psRent");

							grid.hideColumn("mobile");
							grid.hideColumn("email");

							grid.hideColumn("aquiredDate");
							grid.hideColumn("possessionDate");
							grid.hideColumn("assetName");
							grid.hideColumn("assetCatHierarchy");
							grid.hideColumn("assetType");
							grid.hideColumn("ownerShip");
							grid.hideColumn("assetLocHierarchy");
							grid.hideColumn("assetTag");
							grid.hideColumn("assetStatus");
							grid.hideColumn("vendorName");
							grid.hideColumn("purchaseDate");

							grid.hideColumn("panNo");
							grid.hideColumn("serviceTax");

							grid.hideColumn("visitorName");
							grid.hideColumn("visitorContact");
							grid.hideColumn("visitorFrom");
							grid.hideColumn("visitorPurpose");
							grid.hideColumn("vistorProperty");
							grid.hideColumn("visitorInDate");
							grid.hideColumn("visitorStatus");

							grid.hideColumn("dept");
							grid.hideColumn("desg");
							grid.hideColumn("role");

							grid.hideColumn("driverName");
							grid.hideColumn("workNature");

							grid.hideColumn("languages");
							grid.hideColumn("dob");
							grid.hideColumn("gender");
							grid.hideColumn("accessCardNo");
							grid.hideColumn("visitorOutDate");
							grid.hideColumn("inTime");
							grid.hideColumn("outTime");

							grid.hideColumn("propertyType");
							grid.hideColumn("property_Floor");
							grid.hideColumn("area");
							grid.hideColumn("areaType");

							grid.hideColumn("assetGeoTag");
							grid.hideColumn("assetManufacturer");
							grid.hideColumn("assetModelNo");
							grid.hideColumn("assetSpare");
							grid.hideColumn("partModelNumber");
							grid.hideColumn("assetMainCost");
							grid.hideColumn("accountNo");
							grid.hideColumn("personName");
							grid.hideColumn("property_No");
							grid.hideColumn("abBillNo");
							grid.hideColumn("postType");
							grid.hideColumn("abBillAmount");
							grid.hideColumn("abBillDate");
							grid.hideColumn("pStatus");

						}

						if ("${data}" == 21)//assets and inventory
						{

							grid.hideColumn("docName");
							grid.hideColumn("docNum");
							grid.hideColumn("docType");

							grid.hideColumn("address1");
							grid.hideColumn("address2");
							grid.hideColumn("city");
							grid.hideColumn("state");
							grid.hideColumn("country");

							grid.hideColumn("rNo");
							grid.hideColumn("vMk");
							grid.hideColumn("vModel");
							grid.hideColumn("vSlot");
							grid.hideColumn("vTag");
							grid.hideColumn("vStd");
							grid.hideColumn("vEnd");

							grid.hideColumn("block");
							grid.hideColumn("parkingSlotsNo");
							grid.hideColumn("property");
							grid.hideColumn("allotmentDateFrom");
							grid.hideColumn("allotmentDateTo");
							grid.hideColumn("psRent");

							grid.hideColumn("mobile");
							grid.hideColumn("email");

							grid.hideColumn("fatherName");
							grid.hideColumn("nationality");
							grid.hideColumn("status");

							grid.hideColumn("aquiredDate");
							grid.hideColumn("possessionDate");
							grid.hideColumn("title");
							grid.hideColumn("name");
							grid.hideColumn("flatNo");
							grid.hideColumn("pType");

							grid.hideColumn("blockName");

							grid.hideColumn("panNo");
							grid.hideColumn("serviceTax");

							grid.hideColumn("visitorName");
							grid.hideColumn("visitorContact");
							grid.hideColumn("visitorFrom");
							grid.hideColumn("visitorPurpose");
							grid.hideColumn("vistorProperty");
							grid.hideColumn("visitorInDate");
							grid.hideColumn("visitorStatus");

							grid.hideColumn("dept");
							grid.hideColumn("desg");
							grid.hideColumn("role");

							grid.hideColumn("driverName");
							grid.hideColumn("workNature");

							grid.hideColumn("languages");
							grid.hideColumn("dob");
							grid.hideColumn("gender");
							grid.hideColumn("accessCardNo");
							grid.hideColumn("visitorOutDate");
							grid.hideColumn("inTime");

							grid.hideColumn("accountNo");
							grid.hideColumn("personName");

							grid.hideColumn("property_No");
							grid.hideColumn("abBillNo");
							grid.hideColumn("postType");
							grid.hideColumn("abBillAmount");
							grid.hideColumn("outTime");
							grid.hideColumn("propertyType");
							grid.hideColumn("property_Floor");
							grid.hideColumn("area");
							grid.hideColumn("areaType");

							grid.hideColumn("assetGeoTag");
							grid.hideColumn("assetManufacturer");
							grid.hideColumn("assetModelNo");
							grid.hideColumn("assetSpare");
							grid.hideColumn("partModelNumber");
							grid.hideColumn("assetMainCost");
							grid.hideColumn("abBillDate");
							grid.hideColumn("pStatus");

						}
						if ("${data}" == 51)//asset Spare Parts
						{

							grid.hideColumn("docName");
							grid.hideColumn("docNum");
							grid.hideColumn("docType");

							grid.hideColumn("address1");
							grid.hideColumn("address2");
							grid.hideColumn("city");
							grid.hideColumn("state");
							grid.hideColumn("country");

							grid.hideColumn("rNo");
							grid.hideColumn("vMk");
							grid.hideColumn("vModel");
							grid.hideColumn("vSlot");
							grid.hideColumn("vTag");
							grid.hideColumn("vStd");
							grid.hideColumn("vEnd");

							grid.hideColumn("block");
							grid.hideColumn("parkingSlotsNo");
							grid.hideColumn("property");
							grid.hideColumn("allotmentDateFrom");
							grid.hideColumn("allotmentDateTo");
							grid.hideColumn("psRent");

							grid.hideColumn("mobile");
							grid.hideColumn("email");

							grid.hideColumn("fatherName");
							grid.hideColumn("nationality");
							grid.hideColumn("status");

							grid.hideColumn("aquiredDate");
							grid.hideColumn("possessionDate");
							grid.hideColumn("title");
							grid.hideColumn("name");
							grid.hideColumn("flatNo");
							grid.hideColumn("pType");

							grid.hideColumn("blockName");

							grid.hideColumn("panNo");
							grid.hideColumn("serviceTax");

							grid.hideColumn("visitorName");
							grid.hideColumn("visitorContact");
							grid.hideColumn("visitorFrom");
							grid.hideColumn("visitorPurpose");
							grid.hideColumn("vistorProperty");
							grid.hideColumn("visitorInDate");
							grid.hideColumn("visitorStatus");

							grid.hideColumn("dept");
							grid.hideColumn("desg");
							grid.hideColumn("role");

							grid.hideColumn("driverName");
							grid.hideColumn("workNature");

							grid.hideColumn("languages");
							grid.hideColumn("dob");
							grid.hideColumn("gender");
							grid.hideColumn("accessCardNo");
							grid.hideColumn("visitorOutDate");
							grid.hideColumn("inTime");

							grid.hideColumn("accountNo");
							grid.hideColumn("personName");

							grid.hideColumn("property_No");
							grid.hideColumn("abBillNo");
							grid.hideColumn("postType");
							grid.hideColumn("abBillAmount");
							grid.hideColumn("outTime");
							grid.hideColumn("propertyType");
							grid.hideColumn("property_Floor");
							grid.hideColumn("area");
							grid.hideColumn("areaType");
							grid.hideColumn("ownerShip");
							grid.hideColumn("assetStatus");
							grid.hideColumn("abBillDate");
							grid.hideColumn("pStatus");

						}
						if ("${data}" == 22) {

							grid.hideColumn("docName");
							grid.hideColumn("docNum");
							grid.hideColumn("docType");

							grid.hideColumn("address1");
							grid.hideColumn("address2");
							grid.hideColumn("city");
							grid.hideColumn("state");
							grid.hideColumn("country");

							grid.hideColumn("rNo");
							grid.hideColumn("vMk");
							grid.hideColumn("vModel");
							grid.hideColumn("vSlot");
							grid.hideColumn("vTag");
							grid.hideColumn("vStd");
							grid.hideColumn("vEnd");

							grid.hideColumn("block");
							grid.hideColumn("parkingSlotsNo");
							grid.hideColumn("property");
							grid.hideColumn("allotmentDateFrom");
							grid.hideColumn("allotmentDateTo");
							grid.hideColumn("psRent");

							grid.hideColumn("blockName");
							grid.hideColumn("aquiredDate");
							grid.hideColumn("possessionDate");
							grid.hideColumn("assetName");
							grid.hideColumn("assetCatHierarchy");
							grid.hideColumn("assetType");
							grid.hideColumn("ownerShip");
							grid.hideColumn("assetLocHierarchy");
							grid.hideColumn("assetTag");
							grid.hideColumn("assetStatus");
							grid.hideColumn("vendorName");
							grid.hideColumn("purchaseDate");
							grid.hideColumn("flatNo");
							grid.hideColumn("fatherName");
							grid.hideColumn("visitorName");
							grid.hideColumn("visitorContact");
							grid.hideColumn("visitorFrom");
							grid.hideColumn("visitorPurpose");
							grid.hideColumn("vistorProperty");
							grid.hideColumn("visitorInDate");
							grid.hideColumn("visitorStatus");

							grid.hideColumn("dept");
							grid.hideColumn("desg");
							grid.hideColumn("role");

							grid.hideColumn("driverName");
							grid.hideColumn("workNature");

							grid.hideColumn("languages");
							grid.hideColumn("dob");
							grid.hideColumn("gender");
							grid.hideColumn("accessCardNo");
							grid.hideColumn("visitorOutDate");
							grid.hideColumn("inTime");

							grid.hideColumn("accountNo");
							grid.hideColumn("personName");

							grid.hideColumn("property_No");
							grid.hideColumn("abBillNo");
							grid.hideColumn("postType");
							grid.hideColumn("abBillAmount");
							grid.hideColumn("outTime");
							grid.hideColumn("propertyType");
							grid.hideColumn("property_Floor");
							grid.hideColumn("area");
							grid.hideColumn("areaType");

							grid.hideColumn("assetGeoTag");
							grid.hideColumn("assetManufacturer");
							grid.hideColumn("assetModelNo");
							grid.hideColumn("assetSpare");
							grid.hideColumn("partModelNumber");
							grid.hideColumn("assetMainCost");
							grid.hideColumn("abBillDate");
							grid.hideColumn("pStatus");

						}

						if ("${data}" == 23) {

							grid.hideColumn("docName");
							grid.hideColumn("docNum");
							grid.hideColumn("docType");

							grid.hideColumn("address1");
							grid.hideColumn("address2");
							grid.hideColumn("city");
							grid.hideColumn("state");
							grid.hideColumn("country");

							grid.hideColumn("rNo");
							grid.hideColumn("vMk");
							grid.hideColumn("vModel");
							grid.hideColumn("vSlot");
							grid.hideColumn("vTag");
							grid.hideColumn("vStd");
							grid.hideColumn("vEnd");

							grid.hideColumn("block");
							grid.hideColumn("parkingSlotsNo");
							grid.hideColumn("property");
							grid.hideColumn("allotmentDateFrom");
							grid.hideColumn("allotmentDateTo");
							grid.hideColumn("psRent");

							grid.hideColumn("blockName");
							grid.hideColumn("aquiredDate");
							grid.hideColumn("possessionDate");
							grid.hideColumn("assetName");
							grid.hideColumn("assetCatHierarchy");
							grid.hideColumn("assetType");
							grid.hideColumn("ownerShip");
							grid.hideColumn("assetStatus");
							grid.hideColumn("assetLocHierarchy");
							grid.hideColumn("assetTag");
							grid.hideColumn("vendorName");
							grid.hideColumn("purchaseDate");

							grid.hideColumn("flatNo");

							grid.hideColumn("visitorName");
							grid.hideColumn("visitorContact");
							grid.hideColumn("visitorFrom");
							grid.hideColumn("visitorPurpose");
							grid.hideColumn("vistorProperty");
							grid.hideColumn("visitorInDate");
							grid.hideColumn("visitorStatus");

							grid.hideColumn("dept");
							grid.hideColumn("desg");
							grid.hideColumn("role");

							grid.hideColumn("driverName");
							grid.hideColumn("workNature");

							grid.hideColumn("languages");
							grid.hideColumn("dob");
							grid.hideColumn("gender");
							grid.hideColumn("accessCardNo");
							grid.hideColumn("visitorOutDate");
							grid.hideColumn("inTime");
							grid.hideColumn("outTime");
							grid.hideColumn("propertyType");
							grid.hideColumn("property_Floor");
							grid.hideColumn("area");
							grid.hideColumn("areaType");

							grid.hideColumn("assetGeoTag");
							grid.hideColumn("assetManufacturer");
							grid.hideColumn("assetModelNo");
							grid.hideColumn("assetSpare");
							grid.hideColumn("partModelNumber");
							grid.hideColumn("assetMainCost");
							grid.hideColumn("abBillDate");
							grid.hideColumn("accountNo");
							grid.hideColumn("personName");

							grid.hideColumn("property_No");
							grid.hideColumn("abBillNo");
							grid.hideColumn("postType");
							grid.hideColumn("abBillAmount");

							grid.hideColumn("mobile");
							grid.hideColumn("email");
							grid.hideColumn("pStatus");
						}

						if ("${data}" == 29)//*****visitor Information **********
						{

							grid.hideColumn("docName");
							grid.hideColumn("docNum");
							grid.hideColumn("docType");

							grid.hideColumn("mobile");
							grid.hideColumn("email");

							grid.hideColumn("address1");
							grid.hideColumn("address2");
							grid.hideColumn("city");
							grid.hideColumn("state");
							grid.hideColumn("country");

							grid.hideColumn("rNo");
							grid.hideColumn("vMk");
							grid.hideColumn("vModel");
							grid.hideColumn("vSlot");
							grid.hideColumn("vTag");
							grid.hideColumn("vStd");
							grid.hideColumn("vEnd");

							grid.hideColumn("block");
							grid.hideColumn("parkingSlotsNo");
							grid.hideColumn("property");
							grid.hideColumn("allotmentDateFrom");
							grid.hideColumn("allotmentDateTo");
							grid.hideColumn("psRent");

							grid.hideColumn("blockName");
							grid.hideColumn("aquiredDate");
							grid.hideColumn("possessionDate");
							grid.hideColumn("assetName");
							grid.hideColumn("assetCatHierarchy");
							grid.hideColumn("assetType");
							grid.hideColumn("ownerShip");
							grid.hideColumn("assetLocHierarchy");
							grid.hideColumn("assetTag");
							grid.hideColumn("assetStatus");
							grid.hideColumn("vendorName");
							grid.hideColumn("purchaseDate");
							grid.hideColumn("flatNo");

							grid.hideColumn("title");
							grid.hideColumn("name");

							grid.hideColumn("pType");

							grid.hideColumn("blockName");

							grid.hideColumn("panNo");
							grid.hideColumn("serviceTax");

							grid.hideColumn("fatherName");
							grid.hideColumn("nationality");
							grid.hideColumn("status");

							grid.hideColumn("dept");
							grid.hideColumn("desg");
							grid.hideColumn("role");

							grid.hideColumn("driverName");
							grid.hideColumn("workNature");

							grid.hideColumn("languages");
							grid.hideColumn("dob");
							grid.hideColumn("gender");
							grid.hideColumn("accessCardNo");
							grid.hideColumn("visitorOutDate");
							grid.hideColumn("inTime");

							grid.hideColumn("accountNo");
							grid.hideColumn("personName");

							grid.hideColumn("property_No");
							grid.hideColumn("abBillNo");
							grid.hideColumn("postType");
							grid.hideColumn("abBillAmount");
							grid.hideColumn("outTime");
							grid.hideColumn("propertyType");
							grid.hideColumn("property_Floor");
							grid.hideColumn("area");
							grid.hideColumn("areaType");

							grid.hideColumn("assetGeoTag");
							grid.hideColumn("assetManufacturer");
							grid.hideColumn("assetModelNo");
							grid.hideColumn("assetSpare");
							grid.hideColumn("partModelNumber");
							grid.hideColumn("assetMainCost");
							grid.hideColumn("abBillDate");
							grid.hideColumn("pStatus");

						}

						if ("${data}" == 42)//*****visitor check out History**********
						{

							grid.hideColumn("docName");
							grid.hideColumn("docNum");
							grid.hideColumn("docType");

							grid.hideColumn("mobile");
							grid.hideColumn("email");

							grid.hideColumn("address1");
							grid.hideColumn("address2");
							grid.hideColumn("city");
							grid.hideColumn("state");
							grid.hideColumn("country");

							grid.hideColumn("rNo");
							grid.hideColumn("vMk");
							grid.hideColumn("vModel");
							grid.hideColumn("vSlot");
							grid.hideColumn("vTag");
							grid.hideColumn("vStd");
							grid.hideColumn("vEnd");

							grid.hideColumn("block");
							grid.hideColumn("parkingSlotsNo");
							grid.hideColumn("property");
							grid.hideColumn("allotmentDateFrom");
							grid.hideColumn("allotmentDateTo");
							grid.hideColumn("psRent");

							grid.hideColumn("blockName");
							grid.hideColumn("aquiredDate");
							grid.hideColumn("possessionDate");
							grid.hideColumn("assetName");
							grid.hideColumn("assetCatHierarchy");
							grid.hideColumn("assetType");
							grid.hideColumn("ownerShip");
							grid.hideColumn("assetLocHierarchy");
							grid.hideColumn("assetTag");
							grid.hideColumn("assetStatus");
							grid.hideColumn("vendorName");
							grid.hideColumn("purchaseDate");
							grid.hideColumn("flatNo");

							grid.hideColumn("title");
							grid.hideColumn("name");

							grid.hideColumn("pType");

							grid.hideColumn("blockName");

							grid.hideColumn("panNo");
							grid.hideColumn("serviceTax");

							grid.hideColumn("fatherName");
							grid.hideColumn("nationality");
							grid.hideColumn("status");

							grid.hideColumn("dept");
							grid.hideColumn("desg");
							grid.hideColumn("role");

							grid.hideColumn("driverName");
							grid.hideColumn("workNature");

							grid.hideColumn("languages");
							grid.hideColumn("dob");
							grid.hideColumn("gender");
							grid.hideColumn("accessCardNo");
							grid.hideColumn("visitorFrom");

							grid.hideColumn("accountNo");
							grid.hideColumn("personName");

							grid.hideColumn("property_No");
							grid.hideColumn("abBillNo");
							grid.hideColumn("postType");
							grid.hideColumn("abBillAmount");
							grid.hideColumn("propertyType");
							grid.hideColumn("property_Floor");
							grid.hideColumn("area");
							grid.hideColumn("areaType");

							grid.hideColumn("assetGeoTag");
							grid.hideColumn("assetManufacturer");
							grid.hideColumn("assetModelNo");
							grid.hideColumn("assetSpare");
							grid.hideColumn("partModelNumber");
							grid.hideColumn("assetMainCost");
							grid.hideColumn("abBillDate");
							grid.hideColumn("pStatus");

						}
						if ("${data}" == 27)//staff In active
						{

							grid.hideColumn("docName");
							grid.hideColumn("docNum");
							grid.hideColumn("docType");

							grid.hideColumn("address1");
							grid.hideColumn("address2");
							grid.hideColumn("city");
							grid.hideColumn("state");
							grid.hideColumn("country");

							grid.hideColumn("rNo");
							grid.hideColumn("vMk");
							grid.hideColumn("vModel");
							grid.hideColumn("vSlot");
							grid.hideColumn("vTag");
							grid.hideColumn("vStd");
							grid.hideColumn("vEnd");

							grid.hideColumn("panNo");
							grid.hideColumn("serviceTax");

							grid.hideColumn("block");
							grid.hideColumn("parkingSlotsNo");
							grid.hideColumn("property");
							grid.hideColumn("allotmentDateFrom");
							grid.hideColumn("allotmentDateTo");
							grid.hideColumn("psRent");

							grid.hideColumn("blockName");
							grid.hideColumn("aquiredDate");
							grid.hideColumn("possessionDate");
							grid.hideColumn("assetName");
							grid.hideColumn("assetCatHierarchy");
							grid.hideColumn("assetType");
							grid.hideColumn("ownerShip");
							grid.hideColumn("assetLocHierarchy");
							grid.hideColumn("assetTag");
							grid.hideColumn("assetStatus");
							grid.hideColumn("vendorName");
							grid.hideColumn("purchaseDate");

							grid.hideColumn("flatNo");

							grid.hideColumn("visitorName");
							grid.hideColumn("visitorContact");
							grid.hideColumn("visitorFrom");
							grid.hideColumn("visitorPurpose");
							grid.hideColumn("vistorProperty");
							grid.hideColumn("visitorInDate");
							grid.hideColumn("visitorStatus");

							grid.hideColumn("driverName");
							grid.hideColumn("workNature");

							grid.hideColumn("languages");
							grid.hideColumn("dob");
							grid.hideColumn("gender");
							grid.hideColumn("accessCardNo");
							grid.hideColumn("visitorOutDate");
							grid.hideColumn("inTime");

							grid.hideColumn("accountNo");
							grid.hideColumn("personName");

							grid.hideColumn("property_No");
							grid.hideColumn("abBillNo");
							grid.hideColumn("postType");
							grid.hideColumn("abBillAmount");
							grid.hideColumn("outTime");
							grid.hideColumn("propertyType");
							grid.hideColumn("property_Floor");
							grid.hideColumn("area");
							grid.hideColumn("areaType");

							grid.hideColumn("assetGeoTag");
							grid.hideColumn("assetManufacturer");
							grid.hideColumn("assetModelNo");
							grid.hideColumn("assetSpare");
							grid.hideColumn("partModelNumber");
							grid.hideColumn("assetMainCost");
							grid.hideColumn("abBillDate");
							grid.hideColumn("pStatus");

						}

						if ("${data}" == 28) {

							grid.hideColumn("docName");
							grid.hideColumn("docNum");
							grid.hideColumn("docType");

							grid.hideColumn("address1");
							grid.hideColumn("address2");
							grid.hideColumn("city");
							grid.hideColumn("state");
							grid.hideColumn("country");

							grid.hideColumn("rNo");
							grid.hideColumn("vMk");
							grid.hideColumn("vModel");
							grid.hideColumn("vSlot");
							grid.hideColumn("vTag");
							grid.hideColumn("vStd");
							grid.hideColumn("vEnd");

							grid.hideColumn("panNo");
							grid.hideColumn("serviceTax");

							grid.hideColumn("block");
							grid.hideColumn("parkingSlotsNo");
							grid.hideColumn("property");
							grid.hideColumn("allotmentDateFrom");
							grid.hideColumn("allotmentDateTo");
							grid.hideColumn("psRent");

							grid.hideColumn("blockName");
							grid.hideColumn("aquiredDate");
							grid.hideColumn("possessionDate");
							grid.hideColumn("assetName");
							grid.hideColumn("assetCatHierarchy");
							grid.hideColumn("assetType");
							grid.hideColumn("ownerShip");
							grid.hideColumn("assetLocHierarchy");
							grid.hideColumn("assetTag");
							grid.hideColumn("assetStatus");
							grid.hideColumn("vendorName");
							grid.hideColumn("purchaseDate");

							grid.hideColumn("flatNo");

							grid.hideColumn("visitorName");
							grid.hideColumn("visitorContact");
							grid.hideColumn("visitorFrom");
							grid.hideColumn("visitorPurpose");
							grid.hideColumn("vistorProperty");
							grid.hideColumn("visitorInDate");
							grid.hideColumn("visitorStatus");

							grid.hideColumn("driverName");
							grid.hideColumn("workNature");

							grid.hideColumn("languages");
							grid.hideColumn("dob");
							grid.hideColumn("gender");
							grid.hideColumn("accessCardNo");
							grid.hideColumn("visitorOutDate");
							grid.hideColumn("inTime");

							grid.hideColumn("accountNo");
							grid.hideColumn("personName");

							grid.hideColumn("property_No");
							grid.hideColumn("abBillNo");
							grid.hideColumn("postType");
							grid.hideColumn("abBillAmount");
							grid.hideColumn("outTime");
							grid.hideColumn("propertyType");
							grid.hideColumn("property_Floor");
							grid.hideColumn("area");
							grid.hideColumn("areaType");

							grid.hideColumn("assetGeoTag");
							grid.hideColumn("assetManufacturer");
							grid.hideColumn("assetModelNo");
							grid.hideColumn("assetSpare");
							grid.hideColumn("partModelNumber");
							grid.hideColumn("assetMainCost");
							grid.hideColumn("abBillDate");
							grid.hideColumn("pStatus");

						}

						if ("${data}" == 24) {

							grid.hideColumn("docName");
							grid.hideColumn("docNum");
							grid.hideColumn("docType");

							grid.hideColumn("address1");
							grid.hideColumn("address2");
							grid.hideColumn("city");
							grid.hideColumn("state");
							grid.hideColumn("country");

							grid.hideColumn("rNo");
							grid.hideColumn("vMk");
							grid.hideColumn("vModel");
							grid.hideColumn("vSlot");
							grid.hideColumn("vTag");

							grid.hideColumn("panNo");
							grid.hideColumn("serviceTax");

							grid.hideColumn("block");
							grid.hideColumn("parkingSlotsNo");
							grid.hideColumn("property");
							grid.hideColumn("allotmentDateFrom");
							grid.hideColumn("allotmentDateTo");
							grid.hideColumn("psRent");

							grid.hideColumn("blockName");
							grid.hideColumn("aquiredDate");
							grid.hideColumn("possessionDate");
							grid.hideColumn("assetName");
							grid.hideColumn("assetCatHierarchy");
							grid.hideColumn("assetType");
							grid.hideColumn("ownerShip");
							grid.hideColumn("assetLocHierarchy");
							grid.hideColumn("assetTag");
							grid.hideColumn("assetStatus");
							grid.hideColumn("vendorName");
							grid.hideColumn("purchaseDate");

							grid.hideColumn("visitorName");
							grid.hideColumn("visitorContact");
							grid.hideColumn("visitorFrom");
							grid.hideColumn("visitorPurpose");
							grid.hideColumn("vistorProperty");
							grid.hideColumn("visitorInDate");
							grid.hideColumn("visitorStatus");

							grid.hideColumn("fatherName");
							grid.hideColumn("nationality");
							grid.hideColumn("status");

							grid.hideColumn("dept");
							grid.hideColumn("desg");
							grid.hideColumn("role");

							grid.hideColumn("languages");
							grid.hideColumn("dob");
							grid.hideColumn("gender");
							grid.hideColumn("accessCardNo");
							grid.hideColumn("visitorOutDate");
							grid.hideColumn("inTime");

							grid.hideColumn("accountNo");
							grid.hideColumn("personName");

							grid.hideColumn("property_No");
							grid.hideColumn("abBillNo");
							grid.hideColumn("postType");
							grid.hideColumn("abBillAmount");
							grid.hideColumn("outTime");
							grid.hideColumn("propertyType");
							grid.hideColumn("property_Floor");
							grid.hideColumn("area");
							grid.hideColumn("areaType");

							grid.hideColumn("assetGeoTag");
							grid.hideColumn("assetManufacturer");
							grid.hideColumn("assetModelNo");
							grid.hideColumn("assetSpare");
							grid.hideColumn("partModelNumber");
							grid.hideColumn("assetMainCost");
							grid.hideColumn("abBillDate");
							grid.hideColumn("pStatus");

						}

						if ("${data}" == 25)//for list of drivers
						{

							grid.hideColumn("docName");
							grid.hideColumn("docNum");
							grid.hideColumn("docType");

							grid.hideColumn("name");

							grid.hideColumn("flatNo");

							grid.hideColumn("address1");
							grid.hideColumn("address2");
							grid.hideColumn("city");
							grid.hideColumn("state");
							grid.hideColumn("country");

							grid.hideColumn("rNo");
							grid.hideColumn("vMk");
							grid.hideColumn("vModel");
							grid.hideColumn("vSlot");
							grid.hideColumn("vTag");

							grid.hideColumn("panNo");
							grid.hideColumn("serviceTax");

							grid.hideColumn("block");
							grid.hideColumn("parkingSlotsNo");
							grid.hideColumn("property");
							grid.hideColumn("allotmentDateFrom");
							grid.hideColumn("allotmentDateTo");
							grid.hideColumn("psRent");

							grid.hideColumn("blockName");
							grid.hideColumn("aquiredDate");
							grid.hideColumn("possessionDate");
							grid.hideColumn("assetName");
							grid.hideColumn("assetCatHierarchy");
							grid.hideColumn("assetType");
							grid.hideColumn("ownerShip");
							grid.hideColumn("assetLocHierarchy");
							grid.hideColumn("assetTag");
							grid.hideColumn("assetStatus");
							grid.hideColumn("vendorName");
							grid.hideColumn("purchaseDate");

							grid.hideColumn("visitorName");
							grid.hideColumn("visitorContact");
							grid.hideColumn("visitorFrom");
							grid.hideColumn("visitorPurpose");
							grid.hideColumn("vistorProperty");
							grid.hideColumn("visitorInDate");
							grid.hideColumn("visitorStatus");

							grid.hideColumn("fatherName");
							grid.hideColumn("nationality");
							grid.hideColumn("status");

							grid.hideColumn("dept");
							grid.hideColumn("desg");
							grid.hideColumn("role");

							grid.hideColumn("languages");
							grid.hideColumn("dob");
							grid.hideColumn("gender");
							grid.hideColumn("accessCardNo");
							grid.hideColumn("visitorOutDate");
							grid.hideColumn("inTime");

							grid.hideColumn("accountNo");
							grid.hideColumn("personName");

							grid.hideColumn("property_No");
							grid.hideColumn("abBillNo");
							grid.hideColumn("postType");
							grid.hideColumn("abBillAmount");
							grid.hideColumn("outTime");
							grid.hideColumn("propertyType");
							grid.hideColumn("property_Floor");
							grid.hideColumn("area");
							grid.hideColumn("areaType");

							grid.hideColumn("assetGeoTag");
							grid.hideColumn("assetManufacturer");
							grid.hideColumn("assetModelNo");
							grid.hideColumn("assetSpare");
							grid.hideColumn("partModelNumber");
							grid.hideColumn("assetMainCost");
							grid.hideColumn("abBillDate");
							grid.hideColumn("pStatus");

						}

						if ("${data}" == 26)//*****all staffs
						{

							grid.hideColumn("docName");
							grid.hideColumn("docNum");
							grid.hideColumn("docType");

							grid.hideColumn("address1");
							grid.hideColumn("address2");
							grid.hideColumn("city");
							grid.hideColumn("state");
							grid.hideColumn("country");

							grid.hideColumn("rNo");
							grid.hideColumn("vMk");
							grid.hideColumn("vModel");
							grid.hideColumn("vSlot");
							grid.hideColumn("vTag");
							grid.hideColumn("vStd");
							grid.hideColumn("vEnd");

							grid.hideColumn("panNo");
							grid.hideColumn("serviceTax");

							grid.hideColumn("block");
							grid.hideColumn("parkingSlotsNo");
							grid.hideColumn("property");
							grid.hideColumn("allotmentDateFrom");
							grid.hideColumn("allotmentDateTo");
							grid.hideColumn("psRent");

							grid.hideColumn("blockName");
							grid.hideColumn("aquiredDate");
							grid.hideColumn("possessionDate");
							grid.hideColumn("assetName");
							grid.hideColumn("assetCatHierarchy");
							grid.hideColumn("assetType");
							grid.hideColumn("ownerShip");
							grid.hideColumn("assetLocHierarchy");
							grid.hideColumn("assetTag");
							grid.hideColumn("assetStatus");
							grid.hideColumn("vendorName");
							grid.hideColumn("purchaseDate");
							grid.hideColumn("fatherName");
							grid.hideColumn("flatNo");

							grid.hideColumn("visitorName");
							grid.hideColumn("visitorContact");
							grid.hideColumn("visitorFrom");
							grid.hideColumn("visitorPurpose");
							grid.hideColumn("vistorProperty");
							grid.hideColumn("visitorInDate");
							grid.hideColumn("visitorStatus");

							grid.hideColumn("driverName");
							grid.hideColumn("workNature");

							grid.hideColumn("languages");
							grid.hideColumn("dob");
							grid.hideColumn("gender");
							grid.hideColumn("accessCardNo");
							grid.hideColumn("visitorOutDate");
							grid.hideColumn("inTime");

							grid.hideColumn("accountNo");
							grid.hideColumn("personName");

							grid.hideColumn("property_No");
							grid.hideColumn("abBillNo");
							grid.hideColumn("postType");
							grid.hideColumn("abBillAmount");
							grid.hideColumn("outTime");
							grid.hideColumn("propertyType");
							grid.hideColumn("property_Floor");
							grid.hideColumn("area");
							grid.hideColumn("areaType");

							grid.hideColumn("assetGeoTag");
							grid.hideColumn("assetManufacturer");
							grid.hideColumn("assetModelNo");
							grid.hideColumn("assetSpare");
							grid.hideColumn("partModelNumber");
							grid.hideColumn("assetMainCost");
							grid.hideColumn("abBillDate");
							grid.hideColumn("pStatus");

						}

						if ("${data}" == 20) {

							grid.hideColumn("address1");
							grid.hideColumn("address2");
							grid.hideColumn("city");
							grid.hideColumn("state");
							grid.hideColumn("country");

							grid.hideColumn("rNo");
							grid.hideColumn("vMk");
							grid.hideColumn("vModel");
							grid.hideColumn("vSlot");
							grid.hideColumn("vTag");
							grid.hideColumn("vStd");
							grid.hideColumn("vEnd");

							grid.hideColumn("panNo");
							grid.hideColumn("serviceTax");

							grid.hideColumn("block");
							grid.hideColumn("parkingSlotsNo");
							grid.hideColumn("property");
							grid.hideColumn("allotmentDateFrom");
							grid.hideColumn("allotmentDateTo");
							grid.hideColumn("psRent");

							grid.hideColumn("aquiredDate");
							grid.hideColumn("possessionDate");
							grid.hideColumn("assetName");
							grid.hideColumn("assetCatHierarchy");
							grid.hideColumn("assetType");
							grid.hideColumn("ownerShip");
							grid.hideColumn("assetLocHierarchy");
							grid.hideColumn("assetTag");
							grid.hideColumn("assetStatus");
							grid.hideColumn("vendorName");
							grid.hideColumn("purchaseDate");

							grid.hideColumn("visitorName");
							grid.hideColumn("visitorContact");
							grid.hideColumn("visitorFrom");
							grid.hideColumn("visitorPurpose");
							grid.hideColumn("vistorProperty");
							grid.hideColumn("visitorInDate");
							grid.hideColumn("visitorStatus");

							grid.hideColumn("driverName");
							grid.hideColumn("workNature");

							grid.hideColumn("fatherName");
							grid.hideColumn("nationality");
							grid.hideColumn("status");

							grid.hideColumn("dept");
							grid.hideColumn("desg");
							grid.hideColumn("role");

							grid.hideColumn("title");
							grid.hideColumn("name");

							grid.hideColumn("pType");

							grid.hideColumn("mobile");
							grid.hideColumn("email");

							grid.hideColumn("languages");
							grid.hideColumn("dob");
							grid.hideColumn("gender");
							grid.hideColumn("accessCardNo");
							grid.hideColumn("visitorOutDate");
							grid.hideColumn("inTime");

							grid.hideColumn("accountNo");
							grid.hideColumn("personName");

							grid.hideColumn("property_No");
							grid.hideColumn("abBillNo");
							grid.hideColumn("postType");
							grid.hideColumn("abBillAmount");
							grid.hideColumn("outTime");

							grid.hideColumn("propertyType");
							grid.hideColumn("property_Floor");
							grid.hideColumn("area");
							grid.hideColumn("areaType");

							grid.hideColumn("assetGeoTag");
							grid.hideColumn("assetManufacturer");
							grid.hideColumn("assetModelNo");
							grid.hideColumn("assetSpare");
							grid.hideColumn("partModelNumber");
							grid.hideColumn("assetMainCost");
							grid.hideColumn("abBillDate");
							grid.hideColumn("pStatus");

						}

						if ("${data}" == 30)//Multi Flat Users
						{

							grid.hideColumn("docName");
							grid.hideColumn("docNum");
							grid.hideColumn("docType");

							grid.hideColumn("address1");
							grid.hideColumn("address2");
							grid.hideColumn("city");
							grid.hideColumn("state");
							grid.hideColumn("country");

							grid.hideColumn("rNo");
							grid.hideColumn("vMk");
							grid.hideColumn("vModel");
							grid.hideColumn("vSlot");
							grid.hideColumn("vTag");
							grid.hideColumn("vStd");
							grid.hideColumn("vEnd");

							grid.hideColumn("block");
							grid.hideColumn("parkingSlotsNo");
							grid.hideColumn("property");
							grid.hideColumn("allotmentDateFrom");
							grid.hideColumn("allotmentDateTo");
							grid.hideColumn("psRent");

							grid.hideColumn("fatherName");
							grid.hideColumn("nationality");
							grid.hideColumn("status");

							grid.hideColumn("assetName");
							grid.hideColumn("assetCatHierarchy");
							grid.hideColumn("assetType");
							grid.hideColumn("ownerShip");
							grid.hideColumn("assetLocHierarchy");
							grid.hideColumn("assetTag");
							grid.hideColumn("assetStatus");
							grid.hideColumn("vendorName");
							grid.hideColumn("purchaseDate");

							grid.hideColumn("panNo");
							grid.hideColumn("serviceTax");

							grid.hideColumn("visitorName");
							grid.hideColumn("visitorContact");
							grid.hideColumn("visitorFrom");
							grid.hideColumn("visitorPurpose");
							grid.hideColumn("vistorProperty");
							grid.hideColumn("visitorInDate");
							grid.hideColumn("visitorStatus");

							grid.hideColumn("dept");
							grid.hideColumn("desg");
							grid.hideColumn("role");
							grid.hideColumn("driverName");
							grid.hideColumn("workNature");

							grid.hideColumn("languages");
							grid.hideColumn("dob");
							grid.hideColumn("gender");
							grid.hideColumn("accessCardNo");
							grid.hideColumn("visitorOutDate");
							grid.hideColumn("inTime");

							grid.hideColumn("accountNo");
							grid.hideColumn("personName");

							grid.hideColumn("property_No");
							grid.hideColumn("abBillNo");
							grid.hideColumn("postType");
							grid.hideColumn("abBillAmount");

							grid.hideColumn("propertyType");
							grid.hideColumn("property_Floor");
							grid.hideColumn("area");
							grid.hideColumn("areaType");
							grid.hideColumn("possessionDate");
							grid.hideColumn("outTime");
							grid.hideColumn("aquiredDate");
							grid.hideColumn("propertyType");
							grid.hideColumn("property_Floor");
							grid.hideColumn("area");
							grid.hideColumn("areaType");

							grid.hideColumn("assetGeoTag");
							grid.hideColumn("assetManufacturer");
							grid.hideColumn("assetModelNo");
							grid.hideColumn("assetSpare");
							grid.hideColumn("partModelNumber");
							grid.hideColumn("assetMainCost");
							grid.hideColumn("abBillDate");

						}

						if ("${data}" == 40)//*****all staffs
						{

							grid.hideColumn("docName");
							grid.hideColumn("docNum");
							grid.hideColumn("docType");

							grid.hideColumn("address1");
							grid.hideColumn("address2");
							grid.hideColumn("city");
							grid.hideColumn("state");
							grid.hideColumn("country");

							grid.hideColumn("rNo");
							grid.hideColumn("vMk");
							grid.hideColumn("vModel");
							grid.hideColumn("vSlot");
							grid.hideColumn("vTag");
							grid.hideColumn("vStd");
							grid.hideColumn("vEnd");

							grid.hideColumn("panNo");
							grid.hideColumn("serviceTax");

							grid.hideColumn("block");
							grid.hideColumn("parkingSlotsNo");
							grid.hideColumn("property");
							grid.hideColumn("allotmentDateFrom");
							grid.hideColumn("allotmentDateTo");
							grid.hideColumn("psRent");

							grid.hideColumn("blockName");
							grid.hideColumn("aquiredDate");
							grid.hideColumn("possessionDate");
							grid.hideColumn("assetName");
							grid.hideColumn("assetCatHierarchy");
							grid.hideColumn("assetType");
							grid.hideColumn("ownerShip");
							grid.hideColumn("assetLocHierarchy");
							grid.hideColumn("assetTag");
							grid.hideColumn("assetStatus");
							grid.hideColumn("vendorName");
							grid.hideColumn("purchaseDate");

							grid.hideColumn("flatNo");

							grid.hideColumn("visitorName");
							grid.hideColumn("visitorContact");
							grid.hideColumn("visitorFrom");
							grid.hideColumn("visitorPurpose");
							grid.hideColumn("vistorProperty");
							grid.hideColumn("visitorInDate");
							grid.hideColumn("visitorStatus");

							grid.hideColumn("driverName");
							grid.hideColumn("workNature");
							grid.hideColumn("visitorOutDate");
							grid.hideColumn("inTime");

							grid.hideColumn("accountNo");
							grid.hideColumn("personName");

							grid.hideColumn("property_No");
							grid.hideColumn("abBillNo");
							grid.hideColumn("postType");
							grid.hideColumn("abBillAmount");

							grid.hideColumn("propertyType");
							grid.hideColumn("property_Floor");
							grid.hideColumn("area");
							grid.hideColumn("areaType");
							grid.hideColumn("outTime");
							grid.hideColumn("propertyType");
							grid.hideColumn("property_Floor");
							grid.hideColumn("area");
							grid.hideColumn("areaType");

							grid.hideColumn("assetGeoTag");
							grid.hideColumn("assetManufacturer");
							grid.hideColumn("assetModelNo");
							grid.hideColumn("assetSpare");
							grid.hideColumn("partModelNumber");
							grid.hideColumn("assetMainCost");
							grid.hideColumn("abBillDate");
							grid.hideColumn("pStatus");

						}

						if ("${data}" == 44)//Flat Billing
						{

							grid.hideColumn("docName");
							grid.hideColumn("docNum");
							grid.hideColumn("docType");

							grid.hideColumn("address1");
							grid.hideColumn("address2");
							grid.hideColumn("city");
							grid.hideColumn("state");
							grid.hideColumn("country");

							grid.hideColumn("rNo");
							grid.hideColumn("vMk");
							grid.hideColumn("vModel");
							grid.hideColumn("vSlot");
							grid.hideColumn("vTag");
							grid.hideColumn("vStd");
							grid.hideColumn("vEnd");

							grid.hideColumn("panNo");
							grid.hideColumn("serviceTax");
							grid.hideColumn("allotmentDateFrom");
							grid.hideColumn("allotmentDateTo");
							grid.hideColumn("psRent");

							grid.hideColumn("aquiredDate");
							grid.hideColumn("possessionDate");
							grid.hideColumn("assetName");
							grid.hideColumn("assetCatHierarchy");
							grid.hideColumn("assetType");
							grid.hideColumn("ownerShip");
							grid.hideColumn("assetLocHierarchy");
							grid.hideColumn("assetTag");
							grid.hideColumn("assetStatus");
							grid.hideColumn("vendorName");
							grid.hideColumn("purchaseDate");

							grid.hideColumn("visitorName");
							grid.hideColumn("visitorContact");
							grid.hideColumn("visitorFrom");
							grid.hideColumn("visitorPurpose");
							grid.hideColumn("vistorProperty");
							grid.hideColumn("visitorInDate");
							grid.hideColumn("visitorStatus");

							grid.hideColumn("driverName");
							grid.hideColumn("workNature");
							grid.hideColumn("visitorOutDate");
							grid.hideColumn("inTime");
							grid.hideColumn("title");
							grid.hideColumn("block");

							grid.hideColumn("mobile");
							grid.hideColumn("email");
							grid.hideColumn("dept");
							grid.hideColumn("desg");
							grid.hideColumn("role");
							grid.hideColumn("fatherName");
							grid.hideColumn("nationality");
							grid.hideColumn("languages");
							grid.hideColumn("dob");
							grid.hideColumn("gender");
							grid.hideColumn("property");
							grid.hideColumn("accessCardNo");

							grid.hideColumn("accountNo");
							grid.hideColumn("personName");

							grid.hideColumn("property_No");
							grid.hideColumn("abBillNo");
							grid.hideColumn("postType");
							grid.hideColumn("abBillAmount");
							grid.hideColumn("outTime");

							grid.hideColumn("assetGeoTag");
							grid.hideColumn("assetManufacturer");
							grid.hideColumn("assetModelNo");
							grid.hideColumn("assetSpare");
							grid.hideColumn("partModelNumber");
							grid.hideColumn("assetMainCost");
							grid.hideColumn("abBillDate");
							grid.hideColumn("pStatus");

						}

						if ("${data}" == 46)//*****Advance Bill Amount
						{

							grid.hideColumn("docName");
							grid.hideColumn("docNum");
							grid.hideColumn("docType");

							grid.hideColumn("address1");
							grid.hideColumn("address2");
							grid.hideColumn("city");
							grid.hideColumn("state");
							grid.hideColumn("country");

							grid.hideColumn("rNo");
							grid.hideColumn("vMk");
							grid.hideColumn("vModel");
							grid.hideColumn("vSlot");
							grid.hideColumn("vTag");
							grid.hideColumn("vStd");
							grid.hideColumn("vEnd");

							grid.hideColumn("panNo");
							grid.hideColumn("serviceTax");
							grid.hideColumn("allotmentDateFrom");
							grid.hideColumn("allotmentDateTo");
							grid.hideColumn("psRent");

							grid.hideColumn("aquiredDate");
							grid.hideColumn("possessionDate");
							grid.hideColumn("assetName");
							grid.hideColumn("assetCatHierarchy");
							grid.hideColumn("assetType");
							grid.hideColumn("ownerShip");
							grid.hideColumn("assetLocHierarchy");
							grid.hideColumn("assetTag");
							grid.hideColumn("assetStatus");
							grid.hideColumn("vendorName");
							grid.hideColumn("purchaseDate");

							grid.hideColumn("visitorName");
							grid.hideColumn("visitorContact");
							grid.hideColumn("visitorFrom");
							grid.hideColumn("visitorPurpose");
							grid.hideColumn("vistorProperty");
							grid.hideColumn("visitorInDate");
							grid.hideColumn("visitorStatus");

							grid.hideColumn("driverName");
							grid.hideColumn("workNature");
							grid.hideColumn("visitorOutDate");
							grid.hideColumn("inTime");
							grid.hideColumn("title");
							grid.hideColumn("block");

							grid.hideColumn("mobile");
							grid.hideColumn("email");
							grid.hideColumn("dept");
							grid.hideColumn("desg");
							grid.hideColumn("role");
							grid.hideColumn("fatherName");
							grid.hideColumn("nationality");
							grid.hideColumn("languages");
							grid.hideColumn("dob");
							grid.hideColumn("gender");
							grid.hideColumn("property");
							grid.hideColumn("accessCardNo");
							grid.hideColumn("name");
							grid.hideColumn("flatNo");
							grid.hideColumn("pType");
							grid.hideColumn("parkingSlotsNo");
							grid.hideColumn("propertyType");
							grid.hideColumn("property_Floor");
							grid.hideColumn("area");
							grid.hideColumn("areaType");
							grid.hideColumn("outTime");
							grid.hideColumn("status");

							grid.hideColumn("assetGeoTag");
							grid.hideColumn("assetManufacturer");
							grid.hideColumn("assetModelNo");
							grid.hideColumn("assetSpare");
							grid.hideColumn("partModelNumber");
							grid.hideColumn("assetMainCost");
							grid.hideColumn("pStatus");

						}

					})

					$("#grid").on(
							"click",
							".k-grid-ownerTemplatesDetailsExport",
							function(e) {
								window
										.open("./comAll/exportExcel/"
												+ "${data}");
							});
				</script>     
 	