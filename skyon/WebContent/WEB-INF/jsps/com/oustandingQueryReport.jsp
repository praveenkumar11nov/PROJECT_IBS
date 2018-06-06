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
		<kendo:grid-column title="Account&nbsp;ID *" field="account_Id" width="80px">
			</kendo:grid-column>
        <kendo:grid-column title="Account&nbsp;Number*" filterable="false"
			field="account_No" width="130px">
		</kendo:grid-column>
	
		<kendo:grid-column title="Property&nbsp;Number *" filterable="false"
			field="property_No" width="130px">
		</kendo:grid-column>

		<kendo:grid-column title="Person&nbsp;Name *" filterable="false"
			field="person_Name" width="130px">
		</kendo:grid-column>

		<kendo:grid-column title="Balance&nbsp; *" filterable="false"
			field="balance" width="130px">
		</kendo:grid-column>



		<kendo:grid-column title="Ledger Type" field="ledger_Type"
			filterable="true" width="110px" />

		<kendo:grid-column title="Email Id" field="email"
			filterable="true" width="110px" />

		<kendo:grid-column title="Mobile Number" field="mobile"
			filterable="true" width="110px" />

	</kendo:grid-columns>
        <kendo:dataSource  requestStart="requestStart" requestEnd="requestEnd">
            <kendo:dataSource-transport>
            
                <kendo:dataSource-transport-read url="${readUr}/${data}" dataType="json" type="POST" contentType="application/json"/>
             
            </kendo:dataSource-transport>
            <kendo:dataSource-schema>
                <kendo:dataSource-schema-model id="account_Id">
                 <kendo:dataSource-schema-model-fields>
                        <kendo:dataSource-schema-model-field name="account_Id" type="string"/>
                        	
                        
                        <kendo:dataSource-schema-model-field name="account_No" type="string">
                        </kendo:dataSource-schema-model-field>
                         
                         <kendo:dataSource-schema-model-field name="property_No"   type="string"/>
                       <kendo:dataSource-schema-model-field name="person_Name"   type="string"/>
                       <kendo:dataSource-schema-model-field name="balance"   type="string"/>
                       <kendo:dataSource-schema-model-field name="ledger_Type"   type="string"/>
                       <kendo:dataSource-schema-model-field name="email"   type="string"/>
                       <kendo:dataSource-schema-model-field name="mobile"   type="string"/>
                      
                   
                        
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

					

					function clearFilterOwner() {
						//custom actions
						$("form.k-filter-menu button[type='reset']").slice()
								.trigger("click");
						var grid = $("#grid").data("kendoGrid");
						grid.dataSource.read();
						grid.refresh();
					}

		/* 			$(document).ready(function() {

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

					}) */

					$("#grid").on(
							"click",
							".k-grid-ownerTemplatesDetailsExport",
							function(e) {
								window
										.open("./comAll/exportExcel/"
												+ "${data}");
							});
				</script>     
 	