<%@include file="/common/taglibs.jsp"%>

<!--	Common Url		-->
<c:url value="/common/getAllChecks" var="allChecksUrl" />
<c:url value="/common/getFilterDropDownValues" var="filterDropDownUrl" />
<c:url value="/users/getVendors" var="vendors" />
<c:url value="/asset/getCommonGrouping" var="commonGroupingUrl" />


<c:url value="/users/getVendorNamesList" var="vendorNameFilterUrl" />
<c:url value="/asset/getAssetNamesList" var="assetNameFilterUrl" />

<!--	Asset Master Url	-->
<c:url value="/asset/read" var="readUrl" />
<c:url value="/asset/saveorupdate" var="assetSaveOrUpdateUrl" />
<c:url value="/asset/update" var="updateUrl" />
<c:url value="/asset/delete" var="destroyUrl" />

<!--	Asset Spares Url	-->
<c:url value="/asset/spares/read" var="readAssetSpareUrl" />
<c:url value="/asset/spares/create" var="createAssetSpareUrl" />
<c:url value="/asset/spares/update" var="updateAssetSpareUrl" />
<c:url value="/asset/spares/delete" var="deleteAssetSpareUrl" />
<c:url value="/asset/spares/itemmaster" var="itemmaster" />

<!--	Asset Warranty Url	-->
<c:url value="/asset/warranty/read" var="readAssetWarrantyUrl" />
<c:url value="/asset/warranty/create" var="createAssetWarrantyUrl" />
<c:url value="/asset/warranty/update" var="updateAssetWarrantyUrl" />
<c:url value="/asset/warranty/delete" var="deleteAssetWarrantyUrl" />

<!--	Asset M C Url	-->
<c:url value="/asset/maintenancecost/read" var="readAssetMCUrl" />
<c:url value="/asset/maintenancecost/create" var="createAssetMCUrl" />
<c:url value="/asset/maintenancecost/update" var="updateAssetMCUrl" />
<c:url value="/asset/maintenancecost/delete" var="deleteAssetMCUrl" />

<!--	Asset M P Url	-->
<c:url value="/asset/maintenance/read" var="readAssetMPUrl" />
<c:url value="/asset/maintenance/create" var="createAssetMPUrl" />
<c:url value="/asset/maintenance/update" var="updateAssetMPUrl" />
<c:url value="/asset/maintenance/delete" var="deleteAssetMPUrl" />

<!-- 	Asset Category & Location Url	 -->
<c:url value="/asset/cat/read" var="transportReadUrlCat" />
<c:url value="/asset/loc/read" var="transportReadUrlLoc" />

<!-- 	Cost Center Url -->
<c:url value="/asset/costcenter" var="costcenter" />

<c:url value="/asset/readMaintDept" var="readMaintDeptUrl" />

<c:url value="/assetdata/upload" var="uploadDataAsset" />

<kendo:grid name="gridAsset" resizable="true" pageable="true"
	edit="assetEvent" detailTemplate="assetTemplate" change="onChange"
	sortable="true" scrollable="true" selectable="true" groupable="true"
	dataBound="dataBoundAsset" remove="removeAsset">
	<kendo:grid-pageable pageSizes="true" buttonCount="5" pageSize="10"></kendo:grid-pageable>
	<kendo:grid-filterable extra="false">
		<kendo:grid-filterable-operators>
			<kendo:grid-filterable-operators-string eq="Is equal to" />
		</kendo:grid-filterable-operators>
	</kendo:grid-filterable>
	<kendo:grid-editable mode="popup"
		confirmation="Are you sure you want to remove this Asset?" />
	<kendo:grid-toolbar>
	<div class="toolbar">	
		<kendo:grid-toolbarItem name="create" text="Add Asset" />
		<kendo:grid-toolbarItem name="assetTemplatesDetailsExport" text="Export To Excel" />
			  <kendo:grid-toolbarItem name="assetPdfTemplatesDetailsExport" text="Export To PDF" /> 
		<kendo:grid-toolbarItem
			template="<a class='k-button' href='\\#' onclick=clearFilter() title='Clear Filter'><span class='k-icon k-i-funnel-clear'></span>Clear Filter</a>" />
		<kendo:grid-toolbarItem
			template="<a class='k-button' href='\\#' onclick=refresh() title='Refresh'><span class='k-icon k-i-refresh'></span></a>" />
		<kendo:grid-toolbarItem
			template="&nbsp;&nbsp;&nbsp;<span class='bgGreenColor' style='width:60px; height :20px'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;: Approved" />
		<kendo:grid-toolbarItem
			template="&nbsp;&nbsp;&nbsp;<span class='bgRedColor' style='width:60px; height :20px'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>&nbsp;&nbsp;: Rejected" />
			
			<kendo:grid-toolbarItem
			template="<a class='k-button' href='\\#'  onclick=OpenDiv()  title=''><span class='k-icon k-i-funnel-clear'></span>Upload Asset Data</a>" />
			 <!-- <a class='k-button' href='#' id="undo" type="file">Upload Owner Data</a> -->
			</div>
			</kendo:grid-toolbar>
	
	
	
	<kendo:grid-columns>


		<kendo:grid-column title="Asset&nbsp;Name&nbsp;*" field="assetName"
			width="140px">
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>function assetNameFilter(element){element.kendoAutoComplete({	placeholder : "Enter full name",dataTextField : "assetName", dataValueField : "assetId", dataSource : {transport : {	read : "${readUrl}"	}}});}</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>
		<kendo:grid-column title="Asset Type *" field="assetType"
			width="110px" editor="assetTypeChecksEditor">
			<kendo:grid-column-filterable>
				<kendo:grid-column-filterable-ui>
					<script>function assetTypeFilter(element) {element.kendoDropDownList({optionLabel: "Select",dataSource : {transport : {	read : "${filterDropDownUrl}/assetType"	}}});}</script>
				</kendo:grid-column-filterable-ui>
			</kendo:grid-column-filterable>
		</kendo:grid-column>
		
		<kendo:grid-column title="Department Name *" field="mtDpIt" editor="maintDeptEditor" width="130px"  hidden="true"/>
		<kendo:grid-column title="Department Name" field="department" width="130px" ><kendo:grid-column-filterable><kendo:grid-column-filterable-ui><script>function assetNameFilter(element){element.kendoAutoComplete({	placeholder : "Enter full name",dataTextField : "mtDpName", dataValueField : "mtDpIt", dataSource : {transport : {	read : "${readMaintDeptUrl}"	}}});}</script></kendo:grid-column-filterable-ui></kendo:grid-column-filterable></kendo:grid-column>
			
		
		<kendo:grid-column title="Asset&nbsp;Description" field="assetDesc"
			width="150px" filterable="false" editor="assetDescEditor" hidden="true"/>
		<kendo:grid-column title="Purchase&nbsp;Date&nbsp;*"
			field="purchaseDate" width="120px" format="{0:dd/MM/yyyy}" >
			<kendo:grid-column-filterable>
        <kendo:grid-column-filterable-ui>
         <script> 
          function fromDateFilter(element) {
        element.kendoDatePicker({
         format:"dd/MM/yyyy",
                  
                 });
         }         
        </script>  
        </kendo:grid-column-filterable-ui>
       </kendo:grid-column-filterable>
			</kendo:grid-column>
			
		<kendo:grid-column title="Cost&nbsp;Center&nbsp;*"	field="ccId" width="110px" editor="costCenterEditor" hidden="true"/>
		<kendo:grid-column title="Cost&nbsp;Center" field="name" width="120px"><kendo:grid-column-filterable><kendo:grid-column-filterable-ui><script>function nameFilter(element){element.kendoAutoComplete({	placeholder : "Enter name",dataSource : {transport : {	read : "${costcenter}"	}}});}</script></kendo:grid-column-filterable-ui></kendo:grid-column-filterable></kendo:grid-column>
		
			
		<kendo:grid-column title="PO Number *" field="assetPoDetail"		width="110px" />
		<kendo:grid-column title="Category Id" field="assetCatId"	width="110px" format="{0:n0}" hidden="true" />
		<kendo:grid-column title="Select&nbsp;Category&nbsp;*"	field="category" width="100px" hidden="true" editor="catEditor" />
		<kendo:grid-column title="Select&nbsp;Location&nbsp;*" hidden="true"
			field="location" width="100px" editor="locEditor" />
		<kendo:grid-column title="Category" field="assetCatHierarchy" width="140px" filterable="false"
			template="<a id='catedit' href='\\#' onclick='editAssetCategory()'>#=assetCatHierarchy#</a>" hidden="true"/>

		<kendo:grid-column title="Location" field="assetLocHierarchy" width="140px" filterable="false" hidden="true"/>

		<kendo:grid-column title="Ownership *" field="ownerShip" width="110px"	editor="dropDownChecksEditor"><kendo:grid-column-filterable><kendo:grid-column-filterable-ui><script>function ownerShipFilter(element) {element.kendoDropDownList({optionLabel: "Select",dataSource : {transport : {	read : "${filterDropDownUrl}/ownerShip"	}}});}</script></kendo:grid-column-filterable-ui></kendo:grid-column-filterable></kendo:grid-column>
		<kendo:grid-column title="Supplier *" field="supplier" width="90px" />
		<kendo:grid-column title="Vendor&nbsp;Name&nbsp;*"	field="assetVendorId" width="110px" editor="vendorNameEditor" hidden="true" />
		<kendo:grid-column title="Vendor&nbsp;Name" field="vendorName"	width="120px"><kendo:grid-column-filterable><kendo:grid-column-filterable-ui><script>function vendorNameFilter(element){element.kendoAutoComplete({	placeholder : "Enter full name",dataSource : {transport : {	read : "${vendorNameFilterUrl}"	}}});}</script></kendo:grid-column-filterable-ui></kendo:grid-column-filterable></kendo:grid-column>
		<kendo:grid-column title="Location Id" field="assetLocId"	width="110px" format="{0:n0}" hidden="true" />
		<kendo:grid-column title="Asset Geo&nbsp;Tag *" field="assetGeoTag"	width="110px" hidden="true"/>
		<kendo:grid-column title="Condition *" field="assetCondition" width="110px" editor="dropDownChecksEditor"><kendo:grid-column-filterable><kendo:grid-column-filterable-ui><script>function assetConditionFilter(element) {element.kendoDropDownList({optionLabel: "Select",dataSource : {transport : {	read : "${filterDropDownUrl}/assetCondition"}}});}</script></kendo:grid-column-filterable-ui></kendo:grid-column-filterable></kendo:grid-column>
		<%-- 	<kendo:grid-column title="Dr Id" 			field="drGroupId" width="110px" /> --%>
		<kendo:grid-column title="Asset Notes" field="assetNotes"	width="150px" filterable="false" editor="assetNotesEditor" hidden="true"/>
		<kendo:grid-column title="Manufacturers *" field="assetManufacturer"		width="110px" />
		<kendo:grid-column title="Manuf.&nbsp;Year&nbsp;*" field="assetYear"		width="95px" format="{0:dd/MM/yyyy}" ><kendo:grid-column-filterable><kendo:grid-column-filterable-ui><script>function fromDateFilter(element) { element.kendoDatePicker({ format:"dd/MM/yyyy",}); }</script></kendo:grid-column-filterable-ui></kendo:grid-column-filterable></kendo:grid-column>
		<kendo:grid-column title="Valid&nbsp;Till&nbsp;*" field="warrantyTill"		width="90px" format="{0:dd/MM/yyyy}" ><kendo:grid-column-filterable><kendo:grid-column-filterable-ui><script>function fromDateFilter(element) { element.kendoDatePicker({ format:"dd/MM/yyyy",}); }</script>   </kendo:grid-column-filterable-ui></kendo:grid-column-filterable></kendo:grid-column>
		<kendo:grid-column title="Model&nbsp;Number&nbsp;*"
			field="assetModelNo" width="110px" />
		<kendo:grid-column title="Manuf. Sl No&nbsp;*"
			field="assetManufacturerSlNo" width="110px" />
		<kendo:grid-column title="Asset&nbsp;Tag&nbsp;*" field="assetTag"
			width="110px" hidden="true"/>
		<kendo:grid-column title="Useful&nbsp;Life (In Years) *"
			format="{0:n0}" field="useFullLife" width="150px" hidden="true"/>
		<kendo:grid-column title="Expiry&nbsp;Date *" field="assetLifeExpiry"
			format="{0:dd/MM/yyyy}" width="150px" filterable="false" hidden="true">
			</kendo:grid-column>
		<kendo:grid-column title="Status" filterable="false"
			field="assetStatus" width="70px" />
		<kendo:grid-column title=" " field="dummy" width="90px" hidden="true" />
		<%-- <kendo:grid-column title=" " field="dummy1" width="90px" hidden="true" /> --%>
		<%-- <kendo:grid-column title=" " field="dummy2" width="90px" hidden="true" /> --%>


		<%-- <kendo:grid-column title="Barcode Generator"  template="<a href='\\\#' class='k-button' id='printBtn' onClick = 'generateBarcode()'>Print Asset Tag</a>" 	width="140px"		  />

			 --%>

		<kendo:grid-column title="&nbsp;" width="320px">
			<kendo:grid-column-command>
				<kendo:grid-column-commandItem name="Print Asset Tag"
					click="generateBarcode" className="k-grid-print" />
				<kendo:grid-column-commandItem name="Upload Doc" click="uploadAsset"
					className="k-grid-upload" />
				<kendo:grid-column-commandItem name="Download Doc"
					click="downloadAsset" className="k-grid-download" />
			</kendo:grid-column-command>
		</kendo:grid-column>

		<kendo:grid-column title="&nbsp;" width="100px">
			<kendo:grid-column-command>
				<kendo:grid-column-commandItem name="edit" />
			</kendo:grid-column-command>
		</kendo:grid-column>

<kendo:grid-column  title=""
    template="<a href='\\\#' id='btn1_#=data.assetId#' class='k-button k-button-icontext btn-destroy k-grid-Activate#=data.assetId#' onClick=statusBtn(this.text)>Approve</a>&nbsp;<a href='\\\#' id='btn2_#=data.assetId#' class='k-button k-btn2' onClick=statusBtn(this.text)>Reject</a>"
    width="160px" />
    
	</kendo:grid-columns>
	<kendo:dataSource pageSize="20" requestEnd="onRequestEnd"
		requestStart="onRequestStart">
		<kendo:dataSource-transport>
			<kendo:dataSource-transport-create
				url="${assetSaveOrUpdateUrl}/create" dataType="json" type="GET"
				contentType="application/json" />
			<kendo:dataSource-transport-read url="${readUrl}" dataType="json"
				type="POST" contentType="application/json" />
			<kendo:dataSource-transport-update
				url="${assetSaveOrUpdateUrl}/update" dataType="json" type="GET"
				contentType="application/json" />
			<kendo:dataSource-transport-destroy url="${destroyUrl}"
				dataType="json" type="GET" contentType="application/json" />
		</kendo:dataSource-transport>
		<kendo:dataSource-schema>
			<kendo:dataSource-schema-model id="assetId">
				<kendo:dataSource-schema-model-fields>
					<kendo:dataSource-schema-model-field name="assetId" type="number" />
					<kendo:dataSource-schema-model-field name="ccId" type="number" />
					<kendo:dataSource-schema-model-field name="name" />
					<kendo:dataSource-schema-model-field name="assetName">
						<kendo:dataSource-schema-model-field-validation
							pattern="^.{0,45}$" />
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="purchaseDate"
						type="date" defaultValue="">
						<kendo:dataSource-schema-model-field-validation />
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="assetDesc">
						<kendo:dataSource-schema-model-field-validation />
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="assetCatHierarchy"
						editable="false" />
					<kendo:dataSource-schema-model-field name="assetLocHierarchy"
						editable="false" />
					<kendo:dataSource-schema-model-field name="assetPoDetail">
						<kendo:dataSource-schema-model-field-validation
							pattern="^.{0,225}$" />
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="assetCatId"
						type="number">
						<kendo:dataSource-schema-model-field-validation required="true" />
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="assetType">
						<kendo:dataSource-schema-model-field-validation />
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="ownerShip">
						<kendo:dataSource-schema-model-field-validation />
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="assetLocId"
						type="number">
						<kendo:dataSource-schema-model-field-validation />
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="assetGeoTag">
						<kendo:dataSource-schema-model-field-validation
							pattern="^.{0,45}$" />
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="assetCondition">
						<kendo:dataSource-schema-model-field-validation />
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="assetNotes">
						<kendo:dataSource-schema-model-field-validation />
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="assetManufacturer">
						<kendo:dataSource-schema-model-field-validation
							pattern="^.{0,100}$" />
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="assetYear" type="date"
						defaultValue="">
						<kendo:dataSource-schema-model-field-validation />
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="warrantyTill"
						type="date" defaultValue="">
						<kendo:dataSource-schema-model-field-validation />
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="assetModelNo">
						<kendo:dataSource-schema-model-field-validation
							pattern="^.{0,100}$" />
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="assetManufacturerSlNo">
						<kendo:dataSource-schema-model-field-validation
							pattern="^.{0,100}$" />
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="assetTag">
						<kendo:dataSource-schema-model-field-validation
							pattern="^.{0,100}$" />
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="assetVendorId"
						defaultValue="1" type="number">
						<kendo:dataSource-schema-model-field-validation />
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="supplier" />
					<kendo:dataSource-schema-model-field name="useFullLife"
						type="number" defaultValue="0" >
						<kendo:dataSource-schema-model-field-validation max="100" min="1" required="true"/>
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="assetLifeExpiry"
						type="date" />
					<kendo:dataSource-schema-model-field name="mtDpIt" type="number"/>	
					<kendo:dataSource-schema-model-field name="assetStatus">
						<kendo:dataSource-schema-model-field-validation />
					</kendo:dataSource-schema-model-field>
					<kendo:dataSource-schema-model-field name="createdBy" />
					<kendo:dataSource-schema-model-field name="updatedBy" />
					<%-- <kendo:dataSource-schema-model-field name="lastUpdatedDate" type="date"></kendo:dataSource-schema-model-field> --%>
				</kendo:dataSource-schema-model-fields>
			</kendo:dataSource-schema-model>
		</kendo:dataSource-schema>
	</kendo:dataSource>
</kendo:grid>
<kendo:grid-detailTemplate id="assetTemplate">
	<kendo:tabStrip name="tabStrip_#=assetId#">
		<kendo:tabStrip-items>



			<kendo:tabStrip-item text="Asset Spares" selected="true">
				<kendo:tabStrip-item-content>
					<kendo:grid name="gridAssetSpares_#=assetId#" resizable="true"
						pageable="true" sortable="true" selectable="true"
						scrollable="true" edit="assetSparesEvent" groupable="true"
						change="onSparesChange" dataBound="assetSparesDB"
						remove="removeSpares">
						<kendo:grid-editable mode="popup" />
						<kendo:grid-toolbar>
							<kendo:grid-toolbarItem name="create" text="Add Asset Spares" />
						</kendo:grid-toolbar>
						<kendo:grid-columns>

							<kendo:grid-column title="Asset&nbsp;Spare&nbsp;Name&nbsp;*"
								field="imId" width="100px" editor="itemMasterEditor"
								hidden="true" />
							<kendo:grid-column title="Asset Spare Name" field="imName"
								width="100px" />
							<kendo:grid-column title="Common&nbsp;Grouping&nbsp;*"
								field="commonGrouping" width="100px">
								<kendo:grid-column-filterable>
									<kendo:grid-column-filterable-ui>
										<script>function commonGroupingFilter(element) {element.kendoDropDownList({optionLabel: "Select",dataSource : {transport : {	read : "${filterDropDownUrl}/commonGrouping"}}});}</script>
									</kendo:grid-column-filterable-ui>
								</kendo:grid-column-filterable>
							</kendo:grid-column>
							<%-- <kendo:grid-column title="Part Name" field="partName" width="100px" /> --%>
							<kendo:grid-column title="Part Description"
								field="partDescription" editor="partDescriptionEditor"
								width="100px" />
							<kendo:grid-column title="Part Make *" field="partMake"
								width="100px" />
							<kendo:grid-column title="Part&nbsp;Model&nbsp;Number&nbsp;*"
								field="partModelNumber" width="100px" />
							<kendo:grid-column title="Part&nbsp;Year&nbsp;*" field="partYear"
								width="100px" format="{0:dd/MM/yyyy}" />
							<kendo:grid-column title="Serial&nbsp;Number&nbsp;*"
								field="partSlNumber" width="100px" />
							<kendo:grid-column title="&nbsp;" width="160px">
								<kendo:grid-column-command>
									<kendo:grid-column-commandItem name="edit" />
									<kendo:grid-column-commandItem name="destroy" />
								</kendo:grid-column-command>
							</kendo:grid-column>
						</kendo:grid-columns>
						<kendo:dataSource pageSize="5" requestEnd="onRequestEndSpares"
							requestStart="onRequestStartSpares">
							<kendo:dataSource-transport>
								<kendo:dataSource-transport-create
									url="${createAssetSpareUrl}/#=assetId#" dataType="json"
									type="GET" contentType="application/json" />
								<kendo:dataSource-transport-read
									url="${readAssetSpareUrl}/#=assetId#" dataType="json"
									type="POST" contentType="application/json" />
								<kendo:dataSource-transport-update
									url="${updateAssetSpareUrl}/#=assetId#" dataType="json"
									type="GET" contentType="application/json" />
								<kendo:dataSource-transport-destroy
									url="${deleteAssetSpareUrl}/" dataType="json" type="GET"
									contentType="application/json" />
							</kendo:dataSource-transport>
							<kendo:dataSource-schema>
								<kendo:dataSource-schema-model id="asId">
									<kendo:dataSource-schema-model-fields>
										<kendo:dataSource-schema-model-field name="asId" type="number"
											editable="false" />
										<kendo:dataSource-schema-model-field name="imId" type="number"
											defaultValue="" />
										<%-- <kendo:dataSource-schema-model-field name="commonGrouping"><kendo:dataSource-schema-model-field-validation pattern="^.{0,45}$"/></kendo:dataSource-schema-model-field>											
											<kendo:dataSource-schema-model-field name="partName"><kendo:dataSource-schema-model-field-validation pattern="^.{0,45}$"/></kendo:dataSource-schema-model-field>											
											<kendo:dataSource-schema-model-field name="partDescription"><kendo:dataSource-schema-model-field-validation /></kendo:dataSource-schema-model-field>											
											 --%>
										<kendo:dataSource-schema-model-field name="partMake">
											<kendo:dataSource-schema-model-field-validation
												required="true" pattern="^.{0,100}$" />
										</kendo:dataSource-schema-model-field>
										<kendo:dataSource-schema-model-field name="partModelNumber">
											<kendo:dataSource-schema-model-field-validation
												required="true" pattern="^.{0,100}$" />
										</kendo:dataSource-schema-model-field>
										<kendo:dataSource-schema-model-field name="partYear"
											type="date">
											<kendo:dataSource-schema-model-field-validation
												required="true" />
										</kendo:dataSource-schema-model-field>
										<kendo:dataSource-schema-model-field name="partSlNumber">
											<kendo:dataSource-schema-model-field-validation
												required="true" pattern="^.{0,45}$" />
										</kendo:dataSource-schema-model-field>
										<kendo:dataSource-schema-model-field name="createdBy" />
										<kendo:dataSource-schema-model-field name="lastUpdatedBy" />
										<kendo:dataSource-schema-model-field name="lastUpdatedDate"
											type="date" />
									</kendo:dataSource-schema-model-fields>
								</kendo:dataSource-schema-model>
							</kendo:dataSource-schema>
						</kendo:dataSource>
					</kendo:grid>
				</kendo:tabStrip-item-content>
			</kendo:tabStrip-item>

			<kendo:tabStrip-item text="Asset Warranty">
				<kendo:tabStrip-item-content>
					<kendo:grid name="gridAssetWarranty_#=assetId#" resizable="true"
						pageable="true" sortable="true" selectable="true"
						scrollable="true" edit="assetWarrantyEvent" groupable="true"
						dataBound="assetWarrantyDB" remove="removeWarranty">
						<kendo:grid-editable mode="inline" />
						<kendo:grid-toolbar>
							<kendo:grid-toolbarItem name="create" text="Add Asset Warranty" />
						</kendo:grid-toolbar>
						<kendo:grid-columns>
							<kendo:grid-column title="From&nbsp;Date&nbsp;*"
								field="warrantyFromDate" width="100px" format="{0:dd/MM/yyyy}" />
							<kendo:grid-column title="To&nbsp;Date&nbsp;*"
								field="warrantyToDate" width="100px" format="{0:dd/MM/yyyy}" />
							<kendo:grid-column title="Warranty&nbsp;Type&nbsp;*"
								field="warrantyType" width="100px" />
							<kendo:grid-column title="Validity *" field="warrantyValid"
								width="100px">
								<kendo:grid-column-filterable>
									<kendo:grid-column-filterable-ui>
										<script>function warrantyValidFilter(element) {element.kendoDropDownList({optionLabel: "Select",dataSource : {transport : {	read : "${filterDropDownUrl}/warrantyValid"}}});}</script>
									</kendo:grid-column-filterable-ui>
								</kendo:grid-column-filterable>
							</kendo:grid-column>
							<kendo:grid-column title="&nbsp;" width="160px">
								<kendo:grid-column-command>
									<kendo:grid-column-commandItem name="edit" />
									<kendo:grid-column-commandItem name="destroy" />
								</kendo:grid-column-command>
							</kendo:grid-column>
						</kendo:grid-columns>
						<kendo:dataSource pageSize="5" requestEnd="onRequestEndWarranty"
							requestStart="onRequestStartWarranty">
							<kendo:dataSource-transport>
								<kendo:dataSource-transport-create
									url="${createAssetWarrantyUrl}/#=assetId#" dataType="json"
									type="GET" contentType="application/json" />
								<kendo:dataSource-transport-read
									url="${readAssetWarrantyUrl}/#=assetId#" dataType="json"
									type="POST" contentType="application/json" />
								<kendo:dataSource-transport-update
									url="${updateAssetWarrantyUrl}/#=assetId#" dataType="json"
									type="GET" contentType="application/json" />
								<kendo:dataSource-transport-destroy
									url="${deleteAssetWarrantyUrl}/" dataType="json" type="GET"
									contentType="application/json" />
							</kendo:dataSource-transport>
							<kendo:dataSource-schema>
								<kendo:dataSource-schema-model id="awId">
									<kendo:dataSource-schema-model-fields>
										<kendo:dataSource-schema-model-field name="awId" type="number"
											editable="false" />
										<kendo:dataSource-schema-model-field name="warrantyFromDate"
											type="date" defaultValue="">
											<kendo:dataSource-schema-model-field-validation
												required="true" />
										</kendo:dataSource-schema-model-field>
										<kendo:dataSource-schema-model-field name="warrantyToDate"
											type="date" defaultValue="">
											<kendo:dataSource-schema-model-field-validation
												required="true" />
										</kendo:dataSource-schema-model-field>
										<kendo:dataSource-schema-model-field name="warrantyType"
											defaultValue="Extended" editable="false">
											<kendo:dataSource-schema-model-field-validation
												required="true" />
										</kendo:dataSource-schema-model-field>
										<kendo:dataSource-schema-model-field name="warrantyValid"
											defaultValue="Yes" editable="false">
											<kendo:dataSource-schema-model-field-validation
												required="true" pattern="^.{0,45}$" />
										</kendo:dataSource-schema-model-field>
										<kendo:dataSource-schema-model-field name="partSlNumber">
											<kendo:dataSource-schema-model-field-validation
												required="true" />
										</kendo:dataSource-schema-model-field>
										<kendo:dataSource-schema-model-field name="createdBy" />
										<kendo:dataSource-schema-model-field name="lastUpdatedBy" />
										<kendo:dataSource-schema-model-field name="lastUpdatedDate"
											type="date" />
									</kendo:dataSource-schema-model-fields>
								</kendo:dataSource-schema-model>
							</kendo:dataSource-schema>
						</kendo:dataSource>
					</kendo:grid>
				</kendo:tabStrip-item-content>
			</kendo:tabStrip-item>

			<%-- <kendo:tabStrip-item text="Asset Maintenance" >
					<kendo:tabStrip-item-content>
						<kendo:grid name="gridAssetMaintenance_#=assetId#" resizable="true" pageable="true" sortable="true" selectable="true" scrollable="true" edit="assetMEvent"	groupable="true">
							<kendo:grid-editable mode="popup" confirmation="Are you sure you want to remove this maintenance?"/>
							<kendo:grid-toolbar><kendo:grid-toolbarItem name="create" text="Add Asset Maintenance" /></kendo:grid-toolbar>
							<kendo:grid-columns>
								<kendo:grid-column title="Maintainence Type" field="maintainenceType" width="100px" editor="dropDownChecksEditor"><kendo:grid-column-filterable><kendo:grid-column-filterable-ui><script>function maintainenceTypeFilter(element) {element.kendoDropDownList({optionLabel: "Select",dataSource : {transport : {	read : "${filterDropDownUrl}/maintainenceType"	}}});}</script></kendo:grid-column-filterable-ui></kendo:grid-column-filterable></kendo:grid-column>
								<kendo:grid-column title="Maintenance Description" field="maintenanceDescription" width="150px" filterable="false" editor="maintenanceDescEditor"/>
								<kendo:grid-column title="Last Maintained Date" field="lastMaintained" width="100px" format="{0:dd/MM/yyyy}" />
								<kendo:grid-column title="Periodicity " field="periodicity" width="100px"  editor="dropDownChecksEditor"><kendo:grid-column-filterable><kendo:grid-column-filterable-ui><script>function periodicityFilter(element) {element.kendoDropDownList({optionLabel: "Select",dataSource : {transport : {	read : "${filterDropDownUrl}/periodicity"}}});}</script></kendo:grid-column-filterable-ui></kendo:grid-column-filterable></kendo:grid-column>
								<kendo:grid-column title="&nbsp;" width="160px">
									<kendo:grid-column-command>
										<kendo:grid-column-commandItem name="edit" />
										<kendo:grid-column-commandItem name="destroy" />
									</kendo:grid-column-command>
								</kendo:grid-column>
							</kendo:grid-columns>
							<kendo:dataSource pageSize="5" requestEnd="onRequestEndChild">
								<kendo:dataSource-transport>
									<kendo:dataSource-transport-create url="${createAssetMPUrl}/#=assetId#" dataType="json" type="GET" contentType="application/json" />
									<kendo:dataSource-transport-read url="${readAssetMPUrl}/#=assetId#" dataType="json" type="POST" contentType="application/json" />
									<kendo:dataSource-transport-update url="${updateAssetMPUrl}/#=assetId#" dataType="json" type="GET" contentType="application/json" />
									<kendo:dataSource-transport-destroy url="${deleteAssetMPUrl}" dataType="json" type="GET" contentType="application/json" />
								</kendo:dataSource-transport>
								<kendo:dataSource-schema>
									<kendo:dataSource-schema-model id="ampId">
										<kendo:dataSource-schema-model-fields>
											<kendo:dataSource-schema-model-field name="ampId" type="number" editable="false"/>
											<kendo:dataSource-schema-model-field name="maintainenceType"><kendo:dataSource-schema-model-field-validation required="true" pattern="^.{0,45}$"/></kendo:dataSource-schema-model-field>
											<kendo:dataSource-schema-model-field name="maintenanceDescription"><kendo:dataSource-schema-model-field-validation required="true" /></kendo:dataSource-schema-model-field>						
											<kendo:dataSource-schema-model-field name="lastMaintained" type="date"><kendo:dataSource-schema-model-field-validation required="true" /></kendo:dataSource-schema-model-field>
											<kendo:dataSource-schema-model-field name="periodicity"><kendo:dataSource-schema-model-field-validation required="true" pattern="^.{0,45}$"/></kendo:dataSource-schema-model-field>
											<kendo:dataSource-schema-model-field name="createdBy"/>
											<kendo:dataSource-schema-model-field name="updatedBy"/>
											<kendo:dataSource-schema-model-field name="lastUpdatedDate" type="date"/>										
										</kendo:dataSource-schema-model-fields>
									</kendo:dataSource-schema-model>
								</kendo:dataSource-schema>
							</kendo:dataSource>
						</kendo:grid>
					</kendo:tabStrip-item-content>
				</kendo:tabStrip-item> --%>
			<kendo:tabStrip-item text="Asset Maintenance Cost">
				<kendo:tabStrip-item-content>
					<kendo:grid name="gridAssetMaintenanceCost_#=assetId#"
						resizable="true" pageable="true" sortable="true" selectable="true"
						scrollable="true" edit="assetMCEvent" groupable="true"
						dataBound="dataBoundMC" remove="removeMC">
						<kendo:grid-editable mode="popup" />
						<kendo:grid-toolbar>
							<kendo:grid-toolbarItem name="create"
								text="Add Asset Maintenance Cost" />
							<kendo:grid-toolbarItem
								template="<span id='totlcst' style='background:black; color:white; padding: 4px 25px;'><b>Total Cost Incurred : </b><span id='sumTotal_#=assetId#'></span> Rs</span>" />
						</kendo:grid-toolbar>
						<kendo:grid-columns>
							<kendo:grid-column title="Maintained&nbsp;Date&nbsp;*"
								field="amcDate" width="100px" format="{0:dd/MM/yyyy}" />
							<kendo:grid-column title="Cost&nbsp;Incurred&nbsp;*"
								field="costIncurred" width="100px"
								groupFooterTemplate="Average SpentTime" />
							<kendo:grid-column title="Service&nbsp;Type&nbsp;*"
								field="mcType" width="100px" editor="comboBoxChecksEditor" />
							<kendo:grid-column title="&nbsp;" width="160px">
								<kendo:grid-column-command>
									<kendo:grid-column-commandItem name="edit" />
									<kendo:grid-column-commandItem name="destroy" />
								</kendo:grid-column-command>
							</kendo:grid-column>
						</kendo:grid-columns>
						<kendo:dataSource pageSize="5" requestEnd="onRequestEndCost"
							requestStart="onRequestStartCost">
							<kendo:dataSource-transport>
								<kendo:dataSource-transport-create
									url="${createAssetMCUrl}/#=assetId#" dataType="json" type="GET"
									contentType="application/json" />
								<kendo:dataSource-transport-read
									url="${readAssetMCUrl}/#=assetId#" dataType="json" type="POST"
									contentType="application/json" />
								<kendo:dataSource-transport-update
									url="${updateAssetMCUrl}/#=assetId#" dataType="json" type="GET"
									contentType="application/json" />
								<kendo:dataSource-transport-destroy url="${deleteAssetMCUrl}/"
									dataType="json" type="GET" contentType="application/json" />
							</kendo:dataSource-transport>
							<kendo:dataSource-schema>
								<kendo:dataSource-schema-model id="amcId">
									<kendo:dataSource-schema-model-fields>
										<kendo:dataSource-schema-model-field name="amcId"
											type="number" editable="false" />
										<kendo:dataSource-schema-model-field name="amcDate"
											type="date" defaultValue="">
											<kendo:dataSource-schema-model-field-validation />
										</kendo:dataSource-schema-model-field>
										<kendo:dataSource-schema-model-field name="costIncurred"
											type="number">
											<kendo:dataSource-schema-model-field-validation
												required="true" max="999999999" min="0" />
										</kendo:dataSource-schema-model-field>
										<kendo:dataSource-schema-model-field name="mcType">
											<kendo:dataSource-schema-model-field-validation
												required="true" pattern="^.{0,45}$" />
										</kendo:dataSource-schema-model-field>
										<%-- 		<kendo:dataSource-schema-model-field name="createdBy"/>
											<kendo:dataSource-schema-model-field name="updatedBy"/>
											<kendo:dataSource-schema-model-field name="lastUpdatedDate" type="date"/>	 --%>
									</kendo:dataSource-schema-model-fields>
								</kendo:dataSource-schema-model>
							</kendo:dataSource-schema>
						</kendo:dataSource>
					</kendo:grid>
					<!-- <br><div><b>Total Cost Incurred : </b><span id="sumTotal_#=assetId#"></span></div><br> -->
				</kendo:tabStrip-item-content>
			</kendo:tabStrip-item>


			<kendo:tabStrip-item text="Asset Details">
				<kendo:tabStrip-item-content>
					<br>
					<h3>Asset Name: #=assetName#</h3>
					<br>
					<i>Asset Tag: #=assetTag#</i>
					<br>
					<br>

					<table>
						<tr>
							<td><b>Model Number : </b></td>
							<td>#=assetModelNo#</td>
							<td><b>Serial Number :</b></td>
							<td>#=assetManufacturerSlNo#</td>
						</tr>
						<tr>
							<td><b>Asset Type : </b></td>
							<td>#=assetType#</td>
							<td><b>Geo Tag :</b></td>
							<td>#=assetGeoTag#</td>
						</tr>
						<tr>
							<td><b>Category : </b></td>
							<td>#=assetCatHierarchy#</td>
							<td><b>Location :</b></td>
							<td>#=assetLocHierarchy#</td>
						</tr>
						<tr>
							<td><b>Owner : </b></td>
							<td>#=owner#</td>
							<td><b>Maintenance Owner :</b></td>
							<td>#=maintowner#</td>
						</tr>
						<tr>
							<td><b>Asset Notes : </b></td>
							<td>#=assetNotes#</td>
							<td><b>Asset Description :</b></td>
							<td>#=assetDesc#</td>
						</tr>
						<tr>
							<td><b>Asset Expire Date : </b></td>
							<td>#=assetLifeExpiry#</td>
							<td><b>Useful Life (In Years):</b></td>
							<td>#=useFullLife#</td>
							
						</tr>
					
					</table>
					<br>
					<br>
				</kendo:tabStrip-item-content>
			</kendo:tabStrip-item>

		</kendo:tabStrip-items>
	</kendo:tabStrip>
</kendo:grid-detailTemplate>

<div id="dialogBoxforBarcode" hidden="true" title="Asset Tag"
	style="text-align: center;"></div>
<div id="uploadDialog" title="Upload Asset Document"
	style="display: none;">
	<kendo:upload name="files" multiple="false" upload="uploadExtraData"
		success="onDocSuccess">
		<kendo:upload-async autoUpload="true" saveUrl="./asset/upload" />
	</kendo:upload>
</div>



<div id="catKendoWindow" style="display: none;">
	<br>
	<div id='categoryEditWindow'></div>
	<br>
	<button style="float: right;" type="button" onclick='updateCategory()'
		class='k-button'>
		<span class="k-icon k-i-pencil"></span> Update Category
	</button>
	<br>
</div>

 <div id="fileupload" title="Upload Excel" hidden="true">
	<kendo:upload  name="files"  id="files1" select="onSelectupload" multiple="false" success="onExcelDocSuccess" error="errorRegardingUploadExcel" template="<span class='k-progress'></span><div class='file-wrapper'> <span class='file-icon #=addExtensionClass(files[0].extension)#'></span><h6 class='file-heading file-name-heading'>Name: #=name#</h6><h6 class='file-heading file-size-heading'>Size: #=size# bytes</h6><button type='button' class='k-upload-action'></button></div>">
				<kendo:upload-async autoUpload="false" saveUrl="${uploadDataAsset}"/>
	</kendo:upload>
</div>

<script>

$("#gridAsset").on("click",".k-grid-assetTemplatesDetailsExport", function(e) {
	  window.open("./assetInvoiceTemplate/assetInvoiceTemplatesDetailsExport");
});	  

$("#gridAsset").on("click",".k-grid-assetPdfTemplatesDetailsExport", function(e) {
	  window.open("./assetInvoicePdfTemplate/assetPdfTemplatesDetailsExport");
});

$("#gridAsset").on("click",".k-grid-importAsset", function(e) {
	  var window = $("#fileupload"),
      undo = $("#undo")
              .bind("click", function() {
                  window.data("kendoWindow").open().center();
                  undo.hide();
              });

  var onClose = function() {
  	undo.show();
  };

  if (!window.data("kendoWindow")) {
      window.kendoWindow({
          width: "600px",
          title: "Upload Owner Data",
          actions: [
              "Pin",
              "Minimize",
              "Maximize",
              "Close"
          ],
          close: onClose
      });
  }
});	

function OpenDiv(){
	//alert("ravi");
	var window = $("#fileupload");
	 window.kendoWindow({
         width: "600px",
         title: "Upload Asset Data",
         actions: [
             "Pin",
             "Minimize",
             "Maximize",
             "Close"
         ],
         //close: onClose
     });
 
	window.data("kendoWindow").open().center();
    //undo.hide();
}

	var texts = "";
	var nodeid = "";
	var assetId = "";
	var ach = "";
	var assetYear = "";
	var warrantyTill = "";
	var catText  = "";
	var locText = "";
	var addedit ="0";
	//var catSelected = 0;
	//var locSelected = 0;
	var catSelectedId = 0;
	var locSelectedId = 0;
	var assetStatus = "";
	var assetName = "";
	
	
	
/* 	
	$( document ).ready(function() {		
				
		var name = "assetName" + "=";
	    var ca = document.cookie.split(';');
	    alert(ca[0]);
	    for(var i=0; i<ca.length; i++) {
	        var c = ca[i];
	        while (c.charAt(0)==' ') c = c.substring(1);
	        if (c.indexOf(name) != -1) 
				alert(">>>>>>>"+c.substring(name.length, c.length));
	    }
	}); */
	
	
	function onChange(arg) {
		var gview = $("#gridAsset").data("kendoGrid");
		var selectedItem = gview.dataItem(gview.select());
		assetId = selectedItem.assetId;
		assetYear = selectedItem.assetYear;
		assetName = selectedItem.assetName;
		warrantyTill = selectedItem.warrantyTill;
		assetTag = selectedItem.assetTag;
		ach = selectedItem.assetCatHierarchy;
		assetStatus = selectedItem.assetStatus;
		this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
	}
	
	var asId= 0;	
	function onSparesChange(arg) {
		var spareGrid = $("#gridAssetSpares_"+assetId).data("kendoGrid");
		var selectedItem = spareGrid.dataItem(spareGrid.select());
		asId = selectedItem.asId;
		this.collapseRow(this.tbody.find(":not(tr.k-state-selected)"));
	}
	
	function clearFilter(){
		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
		var gridAsset = $("#gridAsset").data("kendoGrid");
		gridAsset.dataSource.read();
		gridAsset.refresh();
	}
	
	function generateBarcode(){
		
		securityCheckForActions("./asset/master/printButton");
		
    $("#dialogBoxforBarcode").kendoBarcode({
        value: assetTag,
        type: "code128",
        width: 280,
        height: 100
    }); 
	 $('#dialogBoxforBarcode').dialog({
         	width: 475,
         	position: 'center',
			modal : true,
			 buttons: {
				 "Print": function() {
					
					 var canvas=document.getElementsByTagName("canvas");
						var image=new Image();
						image.id="pic";
						image.src=canvas[0].toDataURL("image/png"); 
						///var divToPrint=document.getElementById("assetslip");
						var popupWin=window.open('','_blank','width=500 height=500');
						popupWin.document.open();	
						popupWin.document.write("<html><body onload=window.print()><div id=printpopup><table width=600px><tr><td><image src="+image.src+" /></td></tr></table></div></html>");
						popupWin.document.close();
					 
				  $( this ).dialog( "close" );
				 },
				 Cancel: function() {
				 $( this ).dialog( "close" );
				 }
			}
		});  
	}
	
	function uploadExtraData(e) {
		
		var files = e.files;
		// Check the extension of each file and abort the upload if it is not .jpg
		$.each(files, function() {
			if (this.extension.toLowerCase() == ".pdf") {
				e.data = {assetId : assetId , type : this.extension};
			}
			else if (this.extension.toLowerCase() == ".doc") {
				e.data = {assetId : assetId , type : this.extension};
			}
			else if (this.extension.toLowerCase() == ".docx") {
				e.data = {assetId : assetId , type : this.extension};
			}
			else if (this.extension.toLowerCase() == ".csv") {
				e.data = {assetId : assetId , type : this.extension};
			}
			else if (this.extension.toLowerCase() == ".xlsx") {
				e.data = {assetId : assetId , type : this.extension };
			}
			else if (this.extension.toLowerCase() == ".xls") {
				e.data = {assetId : assetId , type : this.extension };
			}
			else if (this.extension.toLowerCase() == ".jpg") {
				e.data = {assetId : assetId , type : this.extension };
			}
			else if (this.extension.toLowerCase() == ".jpeg") {
				e.data = {assetId : assetId , type : this.extension };
			}
			else if (this.extension.toLowerCase() == ".png") {
				e.data = {assetId : assetId , type : this.extension };
			}
			else {
				alert("Invalid Document Type:\nAcceptable formats are pdf, doc, docx, csv, xls, JPG, JPEG and PNG");
				e.preventDefault();
				return false;
			}
		});
	}
	
	function onDocSuccess(e){
		alert("Uploaded Successfully !!!");
		$(".k-upload-files.k-reset").find("li").remove();
		$(".k-upload-status-total").remove();
	}
	
	function downloadAsset() {
		securityCheckForActions("./asset/master/uploaddownloadButton");
		var gview = $("#gridAsset").data("kendoGrid");
		var selectedItem = gview.dataItem(gview.select());
		window.open("./asset/download/" + selectedItem.assetId);
	}
	
	
	
	function refresh(){
		window.location.reload();
	}
	
	function assetEvent(e) {
	
		  /*  $(".k-grid-update").click(function () {
				
			 	var purchaseDt = $('input[name="purchaseDate"]').val();
			 	var manufactureDt = $('input[name="assetYear"]').val();
			 	
			 	if ($.datepicker.parseDate('dd/mm/yy',purchaseDt) < $.datepicker.parseDate('dd/mm/yy',manufactureDt)) {
			 		alert("Manufacture date should be less than Purchase date");
				     return false;
			 	}
			
			});  */
		
		
		$('input[name=purchaseDate]').closest('span').closest('span').css("width","70%");
		$('input[name=assetYear]').closest('span').closest('span').css("width","70%");
		$('input[name=warrantyTill]').closest('span').closest('span').css("width","70%");
		$('input[name=useFullLife]').closest('span').closest('span').css("width","50%");
		$('input[name="assetTag"]').prop('disabled',true);	
		
		/* $('a[id=catedit]').parent('.k-edit-field').remove(); */
		
		$('label[for="department"]').closest('.k-edit-label').remove();
		$('div[data-container-for="department"]').remove();
		$('label[for="name"]').closest('.k-edit-label').remove();
		$('div[data-container-for="name"]').remove();
		$('label[for="assetCatId"]').closest('.k-edit-label').remove();
		$('div[data-container-for="assetCatId"]').remove();
		$('label[for="assetStatus"]').closest('.k-edit-label').remove();
		$('div[data-container-for="assetStatus"]').remove();
		$('label[for="assetLocId"]').closest('.k-edit-label').remove();
		$('div[data-container-for="assetLocId"]').remove();
		$('label[for="vendorName"]').closest('.k-edit-label').remove();
		$('div[data-container-for="vendorName"]').remove();
		
		$('label[for="dummy"]').closest('.k-edit-label').hide();
		$('div[data-container-for="dummy"]').hide();
		$('label[for="dummy1"]').closest('.k-edit-label').hide();
		$('div[data-container-for="dummy1"]').hide();
		$('label[for="dummy2"]').closest('.k-edit-label').hide();
		$('div[data-container-for="dummy2"]').hide();
		
		$('label[for="supplier"]').closest('.k-edit-label').hide();
		$('div[data-container-for="supplier"]').hide();
		$('label[for="assetVendorId"]').closest('.k-edit-label').hide();
		$('div[data-container-for="assetVendorId"]').hide();
		 /* $('a[id^=btn1_]').remove();
		 $('a[id^=btn2_]').remove(); */
		
		 $(".k-edit-field").each(function () {
			$(this).find('a[id^=]').parent().remove();
			$(this).find('a[id^=btn2_]').parent().remove();			
	   	}); 
		
	
		$('label[for="undefined"]').closest('.k-edit-label').remove();
		$('a[id=printBtn]').remove();
		$(".k-edit-form-container").css({"width" : "620px"});
		/* $(".k-window").css({"top" : "100px"}); */
		$('.k-edit-label:nth-child(26n+1)').each(function(e) {
					$(this).nextAll(':lt(25)').addBack().wrapAll(
							'<div class="wrappers"/>');
				});		
		
		$('label[for="assetLifeExpiry"]').closest('.k-edit-label').remove();
		$('div[data-container-for="assetLifeExpiry"]').remove();
		/* $(".k-window").css({"position" : "fixed"}); */
		$('input[name="useFullLife"]').change(function() {
			$('#fmtle').remove();
				 var totalLife = $( this ).val();
				 var manuFacDate = $( 'input[name=assetYear]').val();
				 if(manuFacDate != ''){
				 	var splitYear = manuFacDate.split("/");
				 	var formattedSplitYear = splitYear[0]+"/"+splitYear[1]+"/"+(parseInt(splitYear[2])+parseInt(totalLife));
				 	$( 'div[data-container-for="useFullLife"]').append("<span id='fmtle'>"+formattedSplitYear+"</span>");
				 }
		});
		$('input[name="assetYear"]').change(function() {
			$('#fmtle').remove();
				 var totalLife = $( 'input[name=useFullLife]').val();
				 var manuFacDate = $( this ).val(); 
				 if(totalLife != ''){
				 	var splitYear = manuFacDate.split("/");
				 	var formattedSplitYear = splitYear[0]+"/"+splitYear[1]+"/"+(parseInt(splitYear[2])+parseInt(totalLife));
				 	$( 'div[data-container-for="useFullLife"]').append("<span id='fmtle'>"+formattedSplitYear+"</span>");
				 }
		});
			
	
	if (e.model.isNew()) {

			securityCheckForActions("./asset/master/createButton");
			
			$('label[for="experiedDate"]').closest('.k-edit-label').hide();
			$('div[data-container-for="experiedDate"]').hide();
			$('label[for="assetLocHierarchy"]').closest('.k-edit-label')
					.remove();
			$('div[data-container-for="assetLocHierarchy"]').remove();
			$('label[for="assetCatHierarchy"]').closest('.k-edit-label')
					.remove();
			$('div[data-container-for="assetCatHierarchy"]').remove();
			$(".k-window-title").text("Add New Asset");
			$(".k-grid-update").text("Save");

			$('input[name="assetModelNo"]').blur(function() {
				$('input[name="assetTag"]')
									.val((catText
									+ "/"
									+ locText
									+ "/"
									+ $('input[name="assetModelNo"]').val().trim()
									+ "/"
									+ $('input[name="assetPoDetail"]').val().trim()
									+ "-" 
									+ ($('input[name="assetName"]')	.val()).substring(0, 3)).toUpperCase().trim());
							});

			$('input[name="assetName"]').blur(
					function() {
						$('input[name="assetTag"]')
								.val((catText 
									+ "/" 
									+ locText 
									+ "/"
									+ $('input[name="assetModelNo"]').val().trim()
									+ "/"
									+ $('input[name="assetPoDetail"]').val().trim()
									+ "-" 
									+ ($('input[name="assetName"]')	.val()).substring(0, 3)).toUpperCase().trim());
					});
			
			$('input[name="assetPoDetail"]')
			.blur(
					function() {
						$('input[name="assetTag"]')
								.val((catText
									+ "/"
									+ locText
									+ "/"
									+ $('input[name="assetModelNo"]').val().trim()
									+ "/"
									+ $('input[name="assetPoDetail"]').val().trim()
									+ "-" 
									+ ($('input[name="assetName"]')	.val()).substring(0, 3)).toUpperCase().trim());
					});

			$(".k-grid-update").click(function() {

				if (catSelectedId == 0) {
					alert("Please select Category");
					return false;
				} else {
					e.model.set("assetCatId", catSelectedId);
				}
				if (locSelectedId == 0) {
					alert("Please select Location");
					return false;
				} else {
					e.model.set("assetLocId", locSelectedId);
				}
				e.model.set("assetTag", $('input[name=assetTag]').val());
				//e.model.set("assetTag",(catText+"/"+locText+"/"+$('input[name="assetModelNo"]').val()+""+$('input[name="assetName"]').val().substring(0,3)).toUpperCase());

			});
		} else {
			addedit = "1";
			/* 	e.model.get("assetTag");
				e.model */
			securityCheckForActions("./asset/master/updateButton");

			if (e.model.get("ownerShip") == 'Owned') {
				$('label[for="supplier"]').closest('.k-edit-label').show();
				$('div[data-container-for="supplier"]').show();
				$('label[for="assetVendorId"]').closest('.k-edit-label').hide();
				$('div[data-container-for="assetVendorId"]').hide();

			} else {
				$('label[for="supplier"]').closest('.k-edit-label').hide();
				$('div[data-container-for="supplier"]').hide();
				$('label[for="assetVendorId"]').closest('.k-edit-label').show();
				$('div[data-container-for="assetVendorId"]').show();
			}

			$(".k-window-title").text("Edit Asset information");
			$(".k-grid-update").click(function() {
				if (catSelectedId > 0)
					e.model.set("assetCatId", catSelectedId);
				else
					e.model.set("assetCatId", e.model.get("assetCatId"));

				if (locSelectedId > 0)
					e.model.set("assetLocId", locSelectedId);
				else
					e.model.set("assetLocId", e.model.get("assetLocId"));

				e.model.set("assetTag", $('input[name=assetTag]').val());

			});

		}
		var assetYear = $('input[name="assetYear"]').kendoDatePicker().data(
				"kendoDatePicker");
		var warrantyTill = $('input[name="warrantyTill"]').kendoDatePicker()
				.data("kendoDatePicker");
		var purchaseDate = $('input[name="purchaseDate"]').kendoDatePicker()
				.data("kendoDatePicker");

		$('input[name="purchaseDate"]').keyup(function() {
			$('input[name="purchaseDate"]').val("");
		});

		$('input[name="purchaseDate"]').change(function() {
			assetYear.max($('input[name="purchaseDate"]').val());
			warrantyTill.min($('input[name="purchaseDate"]').val());
		});

		$('input[name="assetYear"]').keyup(function() {
			$('input[name="assetYear"]').val("");
		});

		$('input[name="assetYear"]').change(function() {
			purchaseDate.min($('input[name="assetYear"]').val());
			warrantyTill.min($('input[name="assetYear"]').val());
		});

		$('input[name="warrantyTill"]').keyup(function() {
			$('input[name="warrantyTill"]').val("");
		});

		$('input[name="warrantyTill"]').change(function() {
			purchaseDate.max($('input[name="warrantyTill"]').val());
			assetYear.max($('input[name="warrantyTill"]').val());
		});

		$('Year').appendTo('div[data-container-for="useFullLife"]');

	}
	
	var catWindow = "";
	function editAssetCategory(){
		$("#categoryEditWindow").html(" ");
		$("#catKendoWindow").show();
		
		$('<div style="max-height: 1000px; max-width: 500px; width: 300px; height:200px; overflow: auto;  border:1px solid black; border-radius:7px; "></div>')
		.appendTo('#categoryEditWindow')
		.kendoTreeView(
				{
					dataTextField : "assetcatText",
					template : " #: item.assetcatText # <input type='hidden' id='hiddenId' class='#: item.assetcatText ##: item.assetcatId#' value='#: item.assetcatId#'/>",
					select : selectToUpdate,
					name : "treeview",
					loadOnDemand : false,
					dataSource : {
						transport : {
							read : {
								url : "${transportReadUrlCat}",
								contentType : "application/json",
								type : "GET"
							}
						},
						schema : {
							model : {
								id : "assetcatId",
								hasChildren : "hasChilds"
							}
						}
					}
				});
		

		catWindow = $("#catKendoWindow").kendoWindow(
				{
					visible : false,
					resizable : false,
					modal : true,
					actions : ["Minimize",
							"Maximize", "Close" ],
					title : "Edit Category : "
				}).data("kendoWindow");

		catWindow.center().open();
		
	}
	var updatecategoryId = '';
	function selectToUpdate(e){
		updatecategoryId = $('.k-state-hover').find('#hiddenId').val();
		
	}
	function updateCategory(){
		if(updatecategoryId != ''){
		 $.ajax({
				type : "POST",
				dataType:"text",
				url : "./asset/updatecategory/"+updatecategoryId+"/"+assetId,
				dataType : 'text',
				success : function(response) {
					alert("Updated Successfully");
					catWindow.close();
					var grid = $("#gridAsset").data("kendoGrid");
					grid.dataSource.read();
					grid.refresh();
				}
		 });
		}
		else{
			alert("Please select Category");
		}
	}
	
	$("#gridAsset").on("click", ".k-grid-add", function(e) { 
	 	
		 /* if($("#gridAsset").data("kendoGrid").dataSource.filter()){
				
	   		//$("#grid").data("kendoGrid").dataSource.filter({});
	   		$("form.k-filter-menu button[type='reset']").slice().trigger("click");
				var grid = $("#gridAsset").data("kendoGrid");
				grid.dataSource.read();
				grid.refresh();
	       } */   
	});

	function removeAsset() {
		securityCheckForActions("./asset/master/deleteButton");
	}

	function assetSparesEvent(e) {

		$('label[for="imName"]').closest('.k-edit-label').remove();
		$('div[data-container-for="imName"]').remove();

		$('label[for="commonGrouping"]').closest('.k-edit-label').remove();
		$('div[data-container-for="commonGrouping"]').remove();

		$('label[for="partName"]').closest('.k-edit-label').remove();
		$('div[data-container-for="partName"]').remove();

		$('label[for="partDescription"]').closest('.k-edit-label').remove();
		$('div[data-container-for="partDescription"]').remove();

		var partYearPicker = $('input[name="partYear"]').kendoDatePicker()
				.data("kendoDatePicker");
		partYearPicker.min(assetYear);

		if (e.model.isNew()) {

			securityCheckForActions("./asset/spares/createButton");

			$(".k-window-title").text("Add New Asset Spare");
			$(".k-grid-update").text("Save");
		} else {

			securityCheckForActions("./asset/spares/updateButton");
			$(".k-window-title").text("Edit Spare information");
		}
	}

	function assetWarrantyEvent(e) {

		var warrantyFromDatePicker = $('input[name="warrantyFromDate"]')
				.kendoDatePicker().data("kendoDatePicker");
		warrantyFromDatePicker.min(warrantyTill);

		var warrantyToDatePicker = $('input[name="warrantyToDate"]')
				.kendoDatePicker().data("kendoDatePicker");
		warrantyToDatePicker.min(warrantyTill);

		if (e.model.isNew()) {

			securityCheckForActions("./asset/warranty/createButton");

			$(".k-window-title").text("Add New Asset Warranty");
			$(".k-grid-update").text("Save");
		} else {
			securityCheckForActions("./asset/warranty/updateButton");

			$(".k-window-title").text("Edit Warranty information");
		}

		/* $('input[name="warrantyFromDate"]').keyup(function() {
			$('input[name="warrantyFromDate"]').val("");
		});
		$('input[name="warrantyToDate"]').keyup(function() {
			$('input[name="warrantyToDate"]').val("");
		}); */
	}

	function removeSpares(e) {
		securityCheckForActions("./asset/spares/deleteButton");
		var conf = confirm("Are you Sure want to delete this Spare Record?");
		if (!conf) {
			$('#gridAssetSpares_' + assetId).data('kendoGrid').dataSource
					.read();
			$('#gridAssetSpares_' + assetId).data('kendoGrid').refresh();
			throw new Error('deletion aborted');
		}
	}

	function assetMEvent(e) {
		var lastMaintainedPicker = $('input[name="lastMaintained"]')
				.kendoDatePicker().data("kendoDatePicker");
		lastMaintainedPicker.min(assetYear);
		if (e.model.isNew()) {
			$(".k-window-title").text("Add New Asset Maintenance");
			$(".k-grid-update").text("Save");
		} else {
			$(".k-window-title").text("Edit Cost Details");
		}

	}

	function assetMCEvent(e) {
		var amcDatePicker = $('input[name="amcDate"]').kendoDatePicker().data(
				"kendoDatePicker");
		/* 	var today = new Date();
			var day = today.getDate();
			var month = today.getMonth() + 1;
			var year = today.getFullYear();
			alert(new Date(year,month,day)+"\n"+assetYear); */
		amcDatePicker.min(assetYear);
		$('label[for="mcTypeOther"]').closest('.k-edit-label').hide();
		$('div[data-container-for="mcTypeOther"]').hide();
		if (e.model.isNew()) {

			securityCheckForActions("./asset/maintcost/createButton");

			$(".k-window-title").text("Add New Asset Maintenance Cost");
			$(".k-grid-update").text("Save");
		} else {
			securityCheckForActions("./asset/maintcost/updateButton");

			$(".k-window-title").text("Edit Cost Details");
		}
		$('input[name="amcDate"]').keyup(function() {
			$('input[name="amcDate"]').val("");
		});
	}

	function removeWarranty(e) {
		securityCheckForActions("./asset/warranty/deleteButton");
		var conf = confirm("Are you sure want to delete this Warranty Record?");
		if (!conf) {
			$('#gridAssetWarranty_' + assetId).data('kendoGrid').dataSource
					.read();
			$('#gridAssetWarranty_' + assetId).data('kendoGrid').refresh();
			throw new Error('deletion aborted');
		}
	}
	function removeMC(e) {
		securityCheckForActions("./asset/maintcost/deleteButton");
		var conf = confirm("Are you sure want to delete this Warranty Record?");
		if (!conf) {
			$('#gridAssetMaintenanceCost_' + assetId).data('kendoGrid').dataSource
					.read();
			$('#gridAssetMaintenanceCost_' + assetId).data('kendoGrid')
					.refresh();
			throw new Error('deletion aborted');
		}
	}
	function onCatSelect(e) {
		nodeid = $('.k-state-hover').find('#hiddenId').val();
		var nn = $('.k-state-hover').html();
		var spli = nn.split(" <input");
		$('#editNodeText').val(spli[0].trim());
		var kitems = $(e.node).add(
				$(e.node).parentsUntil('.k-treeview', '.k-item'));
		texts = $.map(kitems, function(kitem) {
			return $(kitem).find('>div span.k-in').text();
		});

		catSelectedId = nodeid;

		catText = this.text(e.node).substring(0, 3);
		if (addedit == "0")
			$('input[name="assetTag"]').val(
					(catText 
							+ "/" 
							+ locText 
							+ "/"
							+ $('input[name="assetModelNo"]').val().trim()
							+ "/"
							+ $('input[name="assetPoDetail"]').val().trim()
							+ "-" 
							+ $('input[name="assetName"]').val().substring(0, 3)).toUpperCase().trim());

		//var catId = $('#gridAsset').data().kendoGrid.dataSource.data()[0];
		//catId.set('assetCatId', nodeid);
		//catSelected = 1;
	}

	function assetDescEditor(container, options) {
		$(
				'<textarea maxlength="500" data-text-field="assetDesc" data-value-field="assetDesc" data-bind="value:' + options.field + '" style="width: 148px; height: 40px;"/>')
				.appendTo(container);
	}

	function assetNotesEditor(container, options) {
		$(
				'<textarea maxlength="500" data-text-field="assetNotes" data-value-field="assetNotes" data-bind="value:' + options.field + '" style="width: 148px; height: 40px;"/>')
				.appendTo(container);
	}

	function partDescriptionEditor(container, options) {
		$(
				'<textarea maxlength="500" data-text-field="partDescription" data-value-field="partDescription" data-bind="value:' + options.field + '" style="width: 148px; height: 40px;"/>')
				.appendTo(container);
	}

	function maintenanceDescEditor(container, options) {
		$(
				'<textarea maxlength="500" data-text-field="maintenanceDescription" data-value-field="maintenanceDescription" data-bind="value:' + options.field + '" style="width: 148px; height: 40px;"/>')
				.appendTo(container);
	}

	function onLocSelect(e) {

		nodeid = $('.k-state-hover').find('#hiddenId').val();
		var nn = $('.k-state-hover').html();
		var spli = nn.split(" <input");
		$('#editNodeText').val(spli[0].trim());
		var kitems = $(e.node).add(
				$(e.node).parentsUntil('.k-treeview', '.k-item'));
		texts = $.map(kitems, function(kitem) {
			return $(kitem).find('>div span.k-in').text();
		});

		locSelectedId = nodeid;
		locText = this.text(e.node).substring(0, 3);
		if (addedit == "0")
			$('input[name="assetTag"]').val(
					(catText 
							+ "/" 
							+ locText 
							+ "/"
							+ $('input[name="assetModelNo"]').val().trim()
							+ "/"
							+ $('input[name="assetPoDetail"]').val().trim()
							+ "-" 
							+ $('input[name="assetName"]').val().substring(0, 3)).toUpperCase().trim());
		//var locId = $('#gridAsset').data().kendoGrid.dataSource.data()[0];
		//locId.set('assetLocId', nodeid);
		//locSelected = 1;
	}

	function dropDownChecksEditor(container, options) {
		var res = (container.selector).split("=");
		var attribute = res[1].substring(0, res[1].length - 1);
		$(
				'<select required="true" data-text-field="text" data-value-field="value" data-bind="value:' + options.field + '" name = "'+attribute+'" id = "'+attribute+'Id"/>')
				.appendTo(container).kendoDropDownList({
					optionLabel : {
						text : "Select",
						value : "",
					},
					defaultValue : false,
					sortable : true,
					dataSource : {
						transport : {
							read : "${allChecksUrl}/" + attribute,
						}
					}
				});
		$('<span class="k-invalid-msg" data-for="This"></span>')
				.appendTo(container);
	}
	
	
	function assetTypeChecksEditor(container, options) {
  	  
 		var booleanData = [ {
 		     text : 'Select',
 			 value : ""
 			    },{
 				   text : 'Movable',
 				   value : "Movable"
 				  },{
 				   text : 'Immovable',
 				   value : "Immovable"
 				  },];
 				  $('<input name="Asset Type" required="true"/>').attr('data-bind', 'value:assetType').appendTo(container).kendoDropDownList
 				  ({
 					    autoBind : false,
 					    defaultValue : false,
 						sortable : true,
 					  
	 					 dataSource : booleanData,
	 					 dataTextField : 'text',
	 					 dataValueField : 'value',
 				  });
 				  $('<span class="k-invalid-msg" data-for="Asset Type"></span>').appendTo(container);
 		
          }      

	function comboBoxChecksEditor(container, options) {
		var res = (container.selector).split("=");
		var attribute = res[1].substring(0, res[1].length - 1);

		$("<select id='combo' required='true' data-bind='value:" + attribute + "'/>")
				.appendTo(container).kendoComboBox({
					dataTextField : "text",
					dataValueField : "value",
					placeholder : "Enter or Select",
					dataSource : {
						transport : {
							read : "${allChecksUrl}/" + attribute,
						}
					},
					change : function (e) {
						//var re =  "/^[_A-Za-z0-9.@-]{6,20}$/";
						var re = /^[_A-Za-z0-9.'-]{0,40}$/;
						if (!re.test(this.value())){
							alert("Invalid Service Type!");
			                $("#combo").data("kendoComboBox").value("");
						}
						/* if (this.value() && this.selectedIndex == -1) {                    
							alert("Staff doesn't exist!");
			                $("#miantstaff").data("kendoComboBox").value("");
			        	} */
				    } 
				});
		$('<span class="k-invalid-msg" data-for="'+attribute+'"></span>')
				.appendTo(container);
	}

	function commonGroupingEditor(container, options) {

		$("<select required='true' data-bind='value:" + options.field + "'/>")
				.appendTo(container).kendoComboBox({
					dataTextField : "commonGrouping",
					dataValueField : "commonGrouping",
					placeholder : "Enter or Select",
					dataSource : {
						transport : {
							read : "${commonGroupingUrl}",
						}
					}
				});
		$('<span class="k-invalid-msg" data-for="'+options.field +'"></span>')
				.appendTo(container);
	}

	function vendorNameEditor(container, options) {
		$(
				'<input data-text-field="vendorName" data-value-field="vendorId" data-bind="value:' + options.field + '" />')
				.appendTo(container).kendoDropDownList({

					dataTextField : "vendorName",
					dataValueField : "vendorId",
					optionLabel : {
						vendorName : "Select",
						vendorId : "",
					},
					defaultValue : false,
					sortable : true,
					dataSource : {
						transport : {
							read : "${vendors}"
						}
					}
				});
		/* $('<span class="k-invalid-msg" data-for="Vendor Name"></span>').appendTo(container);  */
	}

	function itemMasterEditor(container, options) {
		if (options.model.asId == 0) {
			asId = "0";
		}
		$(
				'<input id="itemmaster" name="Asset spare" data-text-field="imName" data-value-field="imId" data-bind="value:' + options.field + '" required="true"/>')
				.appendTo(container)
				.kendoComboBox(
						{

							dataTextField : "imName",
							dataValueField : "imId",
							template : ''
									+ '<span class="k-state-default"><b>#:data.imName#</b></span><br>'
									+ '<span class="k-state-default"><i>Group : </i> #: data.imGroup#</span><br>'
									+ '<span class="k-state-default"><i>Description : </i><br> #: data.imDescription#</span>',
							optionLabel : {
								imName : "Select",
								imId : "",
							},
							defaultValue : false,
							sortable : true,
							dataSource : {
								transport : {
									read : "${itemmaster}?assetId=" + assetId
											+ "&asId=" + asId
								}
							},
							change : function(e) {
								if (this.value() && this.selectedIndex == -1) {
									alert("Asset Spare doesn't exist!");
									$("#itemmaster").data("kendoComboBox")
											.value("");
								}
							}
						});
		$('<span class="k-invalid-msg" data-for="Asset Spare"></span>')
				.appendTo(container);
	}

	function catEditor(container, options) {

		$(
				'<div style="max-height: 100px; overflow: auto;  border:1px solid red; border-radius:7px; "></div>')
				.appendTo(container)
				.kendoTreeView(
						{
							dataTextField : "assetcatText",
							template : " #: item.assetcatText # <input type='hidden' id='hiddenId' class='#: item.assetcatText ##: item.assetcatId#' value='#: item.assetcatId#'/>",
							select : onCatSelect,
							name : "treeview",
							loadOnDemand : false,
							dataSource : {
								transport : {
									read : {
										url : "${transportReadUrlCat}",
										contentType : "application/json",
										type : "GET"
									}
								},
								schema : {
									model : {
										id : "assetcatId",
										hasChildren : "hasChilds"
									}
								}
							}
						});
	}

	function locEditor(container, options) {
		$(
				'<div style="max-height: 100px; overflow: auto;  border:1px solid red; border-radius:7px; "></div>')
				.appendTo(container)
				.kendoTreeView(
						{
							dataTextField : "assetlocText",
							template : " #: item.assetlocText # <input type='hidden' id='hiddenId' class='#: item.assetlocText ##: item.assetlocId#' value='#: item.assetlocId#'/>",
							select : onLocSelect,
							name : "treeview2",
							loadOnDemand : false,
							dataSource : {
								transport : {
									read : {
										url : "${transportReadUrlLoc}",
										contentType : "application/json",
										type : "GET"
									}
								},
								schema : {
									model : {
										id : "assetlocId",
										hasChildren : "hasChilds"
									}
								}
							}
						});
	}

	function dataBoundAsset(e) {

		var data = this.dataSource.view(), row;
		var grid = $("#gridAsset").data("kendoGrid");
		for (var i = 0; i < data.length; i++) {

			row = this.tbody.children("tr[data-uid='" + data[i].uid + "']");
			var assetStatus = data[i].assetStatus;
			var assetCondition = data[i].assetCondition;
			var assetId = data[i].assetId;
			var currentUid = data[i].uid;

			if (assetStatus === 'Approved') {
				row.addClass("bgGreenColor");
				$('#btn3_' + assetId).show();
				var currenRow = grid.table.find("tr[data-uid='" + currentUid
						+ "']");
				var editButton = $(currenRow).find(".k-grid-edit");
				var printButton = $(currenRow).find(".k-grid-print");
				var uploadButton = $(currenRow).find(".k-grid-upload");
				var downloadButton = $(currenRow).find(".k-grid-download");
				editButton.hide();
				printButton.show();
				uploadButton.show();
				downloadButton.show();
				if (assetCondition == 'Destroyed') {
					$('#btn2_' + assetId).text("De activate");
					$('#btn2_' + assetId).show();
				}else{
					$('#btn2_' + assetId).hide();
				}
				$('#btn1_' + assetId).hide();
				
			} else if (assetStatus === 'Rejected') {
				row.addClass("bgRedColor");
				$('#btn3_' + assetId).show();
				var currenRow = grid.table.find("tr[data-uid='" + currentUid
						+ "']");
				var editButton = $(currenRow).find(".k-grid-edit");
				var printButton = $(currenRow).find(".k-grid-print");
				var uploadButton = $(currenRow).find(".k-grid-upload");
				var downloadButton = $(currenRow).find(".k-grid-download");
				editButton.hide();
				printButton.hide();
				uploadButton.hide();
				downloadButton.hide();
				$('#btn1_' + assetId).hide();
				$('#btn2_' + assetId).hide();
			
			} else {
	
				$('#btn1_' + assetId).show();
				$('#btn2_' + assetId).show();
				$('#btn3_' + assetId).hide();
				var currenRow = grid.table.find("tr[data-uid='" + currentUid
						+ "']");
				var editButton = $(currenRow).find(".k-grid-edit");
				var printButton = $(currenRow).find(".k-grid-print");
				var uploadButton = $(currenRow).find(".k-grid-upload");
				var downloadButton = $(currenRow).find(".k-grid-download");
				editButton.show();
				//printButton.hide();
				//uploadButton.hide();
				//downloadButton.hide();

			}
		}
	}

	function assetSparesDB(e) {
		if (assetStatus != "Approved") {
			$(".k-grid-add", "#gridAssetSpares_" + assetId).hide();
		}
	}

	function assetWarrantyDB(e) {
		var grid = $("#gridAssetWarranty_" + assetId).data("kendoGrid");
		var data = this.dataSource.view();
		for (var i = 0; i < data.length; i++) {
			var warrantyType = data[i].warrantyType;
			var currentUid = data[i].uid;
			if (warrantyType == 'Original') {
				var currenRow = grid.table.find("tr[data-uid='" + currentUid
						+ "']");
				var editButton = $(currenRow).find(".k-grid-edit");
				editButton.hide();
				var currenRow = grid.table.find("tr[data-uid='" + currentUid
						+ "']");
				var deleteButton = $(currenRow).find(".k-grid-delete");
				deleteButton.hide();
			}
			if (assetStatus != "Approved") {
				$(".k-grid-add", "#gridAssetWarranty_" + assetId).hide();
			}
		}
	}

	function dataBoundMC(e) {
		/* 	 // var grid = $("#gridAssetMaintenanceCost_" + assetId).data("kendoGrid"); */
		var data = this.dataSource.view();
		var sum = 0;
		if (assetStatus == "Approved") {
			for (var i = 0; i < data.length; i++) {
				row = this.tbody.children("tr[data-uid='" + data[i].uid + "']");
				sum = sum + data[i].costIncurred;
			}
			$('#sumTotal_' + assetId).text(sum);
			$('#totlcst').show();
		} else {
			$(".k-grid-add", "#gridAssetMaintenanceCost_" + assetId).hide();
			$('#totlcst').hide();
		}
	}

	function statusBtn(a) {
		
		 
		
		securityCheckForActions("./asset/master/approverejectButton");
		var ask = confirm("Are You Sure? This action cannot be undone");
		
		$('tr[aria-selected="true"]').find('td:nth-child(10)').html("");
		$('tr[aria-selected="true"]').find('td:nth-child(10)').html("<img src='./resources/images/progressbar.gif' width='100px' height='25px' />");
		 
		if (ask == true) {
			if (a == 'Approve')
				a = 'Approved';
			else
				a = 'Rejected';
			$.ajax({
				type : "POST",
				url : "./asset/updatestatus/" + assetId + "/" + a,
				dataType : "text",
				success : function(response) {
					alert("Asset gets " + response + " !!!");
					if (response.status === 'DENIED') {
						alert(response.result);
					} else {
						$('#gridAsset').data('kendoGrid').dataSource.read();
					}
				}
			});
		}
	}
	function statusBtn3(a) {
		securityCheckForActions("./asset/master/approverejectButton");
		$('#categoryTree').val("");
		
		$("#categoryTree").kendoTreeView(
				{
					dataTextField : "assetcatText",
					template : " #: item.assetcatText # <input type='hidden' id='hiddenId' class='#: item.assetcatText ##: item.assetcatId#' value='#: item.assetcatId#'/>",
					select : onCatSelect,
					name : "treeview",
					loadOnDemand : false,
					dataSource : {
						transport : {
							read : {
								url : "${transportReadUrlCat}",
								contentType : "application/json",
								type : "text"
							}
						},
						schema : {
							model : {
								id : "assetcatId",
								hasChildren : "hasChilds"
							}
						}
					}
				});
		
		 $('#categoryWindow').dialog({
	         	width: 475,
	         	position: 'center',
					modal : true,
				}); 
		 
				/* $( "#categoryWindow" ).dialog( "open" ); */	
		
	}

	$(document).on('change', 'select[id="ownerShipId"]', function() {
		var selectedText = $('#ownerShipId option:selected').eq(0).text();
		if (selectedText == 'Owned') {
			$('label[for="supplier"]').closest('.k-edit-label').show();
			$('div[data-container-for="supplier"]').show();
			$('input[name="supplier"]').prop("required", true);
			$('label[for="assetVendorId"]').closest('.k-edit-label').hide();
			$('div[data-container-for="assetVendorId"]').hide();
		} else {
			$('input[name="supplier"]').prop("required", false);
			$('label[for="supplier"]').closest('.k-edit-label').hide();
			$('div[data-container-for="supplier"]').hide();
			$('label[for="assetVendorId"]').closest('.k-edit-label').show();
			$('div[data-container-for="assetVendorId"]').show();
		}
	});

	function uploadAsset() {
		securityCheckForActions("./asset/master/uploaddownloadButton");
		var gview = $("#gridAsset").data("kendoGrid");
		var selectedItem = gview.dataItem(gview.select());
		if (selectedItem != null) {
			assetId = selectedItem.assetId;
		}
		$('#uploadDialog').dialog({
			modal : true,
		});
		return false;
	}

	function onRequestEnd(r) {
		if (r.type == 'create') {
			alert("Added Successfully");
			$('#gridAsset').data('kendoGrid').dataSource.read();
			$('#gridAsset').data('kendoGrid').refresh();
		} else if (r.type == 'update') {
			alert("Updated Successfully");
			$('#gridAsset').data('kendoGrid').dataSource.read();
			$('#gridAsset').data('kendoGrid').refresh();
		} else if (r.type == 'destroy') {
			alert("Deleted Successfully");
		}
	}

	function onRequestEndSpares(r) {
		if (r.type == 'create') {
			alert("Added Successfully");
			$('#gridAssetSpares_' + assetId).data('kendoGrid').dataSource
					.read();
			$('#gridAssetSpares_' + assetId).data('kendoGrid').refresh();
		} else if (r.type == 'update') {
			alert("Updated Successfully");
			$('#gridAssetSpares_' + assetId).data('kendoGrid').dataSource
					.read();
			$('#gridAssetSpares_' + assetId).data('kendoGrid').refresh();
		} else if (r.type == 'destroy') {
			alert("Deleted Successfully");
		}
	}

	function onRequestEndWarranty(r) {
		if (r.type == 'create') {
			alert("Added Successfully");
			$('#gridAssetWarranty_' + assetId).data('kendoGrid').dataSource
					.read();
			$('#gridAssetWarranty_' + assetId).data('kendoGrid').refresh();
		} else if (r.type == 'update') {
			alert("Updated Successfully");
			$('#gridAssetWarranty_' + assetId).data('kendoGrid').dataSource
					.read();
			$('#gridAssetWarranty_' + assetId).data('kendoGrid').refresh();
		} else if (r.type == 'destroy') {
			alert("Deleted Successfully");
		}
	}

	function onRequestEndCost(r) {
		if (r.type == 'create') {
			alert("Added Successfully");
			$('#gridAssetMaintenanceCost_' + assetId).data('kendoGrid').dataSource
					.read();
			$('#gridAssetMaintenanceCost_' + assetId).data('kendoGrid')
					.refresh();
		} else if (r.type == 'update') {
			alert("Updated Successfully");
			$('#gridAssetMaintenanceCost_' + assetId).data('kendoGrid').dataSource
					.read();
			$('#gridAssetMaintenanceCost_' + assetId).data('kendoGrid')
					.refresh();
		} else if (r.type == 'destroy') {
			alert("Deleted Successfully");
		}
	}

	function onRequestStart(e) {
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();
        
		/* var asset = $('#gridAsset').data("kendoGrid");
		if (asset != null) {
			asset.cancelRow();
		} */
	}

	function onRequestStartSpares(e) {
	    $('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();
        
		/* var res = (e.sender.options.transport.read.url).split("/");
		var assetSp = $('#gridAssetSpares_' + res[res.length - 1]).data(
				"kendoGrid");
		if (assetSp != null) {
			assetSp.cancelRow();
		} */
     
	}

	function onRequestStartWarranty(e) {
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();
        
		/* var res = (e.sender.options.transport.read.url).split("/");
		var assetWar = $('#gridAssetWarranty_' + res[res.length - 1]).data(
				"kendoGrid");
		if (assetWar != null) {
			assetWar.cancelRow();
		} */
	}

	function onRequestStartCost(e) {
		$('.k-grid-update').hide();
        $('.k-edit-buttons')
                .append(
                        '<img src="./resources/images/preloader.GIF" alt="please wait" style="verticle-align:middle"/>&nbsp;&nbsp;Please Wait....&nbsp;&nbsp;&nbsp;&nbsp;');
        $('.k-grid-cancel').hide();
        
		/* var res = (e.sender.options.transport.read.url).split("/");
		var assetCost = $('#gridAssetMaintenanceCost_' + res[res.length - 1])
				.data("kendoGrid");
		if (assetCost != null) {
			assetCost.cancelRow();
		} */
	}

	(function($, kendo) {
		$
				.extend(
						true,
						kendo.ui.validator,
						{
							rules : { // custom rules
								assetNameValidation : function(input, params) {
									if (input.filter("[name='assetName']").length
											&& input.val()) {
										return /^[-_a-zA-Z0-9]+(\s+[-_a-zA-Z0-9]+)*$/i.test(input
												.val());
									}
									return true;
								},
								amcDateValidation : function(input, params) {
									if (input.attr("name") == "amcDate") {
										return $.trim(input.val()) !== "";
									}
									return true;
								},
								assetNameEmpty : function(input, params){
				                      if (input.attr("name") == "assetName")
				                      {
				                       return $.trim(input.val()) !== "";
				                      }
				                      return true;
				                 },
				                 purchaseDateEmpty : function(input, params){
				                      if (input.attr("name") == "purchaseDate")
				                      {
				                       return $.trim(input.val()) !== "";
				                      }
				                      return true;
				                 },
				                 assetPoDetailEmpty : function(input, params){
				                      if (input.attr("name") == "assetPoDetail")
				                      {
				                       return $.trim(input.val()) !== "";
				                      }
				                      return true;
				                 },
				                 assetGeoTagEmpty : function(input, params){
				                      if (input.attr("name") == "assetGeoTag")
				                      {
				                       return $.trim(input.val()) !== "";
				                      }
				                      return true;
				                 },
				                 assetManufacturerEmpty : function(input, params){
				                      if (input.attr("name") == "assetManufacturer")
				                      {
				                       return $.trim(input.val()) !== "";
				                      }
				                      return true;
				                 },
				                 assetYearEmpty : function(input, params){
				                      if (input.attr("name") == "assetYear")
				                      {
				                       return $.trim(input.val()) !== "";
				                      }
				                      return true;
				                 },
				                 warrantyTillEmpty : function(input, params){
				                      if (input.attr("name") == "warrantyTill")
				                      {
				                       return $.trim(input.val()) !== "";
				                      }
				                      return true;
				                 },
				                 assetModelNoEmpty : function(input, params){
				                      if (input.attr("name") == "assetModelNo")
				                      {
				                       return $.trim(input.val()) !== "";
				                      }
				                      return true;
				                 },
				                 
				                assetModelNoValidation : function(input, params) {
										if (input.filter("[name='assetModelNo']").length
												&& input.val()) {
											return /^[-_a-zA-Z0-9]+(\s+[-_a-zA-Z0-9]+)*$/i.test(input
													.val());
										}
										return true;
									},
								assetManufacturerSlNoValidation : function(input, params) {
									if (input.filter("[name='assetManufacturerSlNo']").length
											&& input.val()) {
										return /^[-_a-zA-Z0-9]+(\s+[-_a-zA-Z0-9]+)*$/i.test(input
												.val());
									}
									return true;
								},
									
				                 assetManufacturerSlNoEmpty : function(input, params){
				                      if (input.attr("name") == "assetManufacturerSlNo")
				                      {
				                       return $.trim(input.val()) !== "";
				                      }
				                      return true;
				                 }
							},
							messages : {
								assetNameValidation : "Invalid Asset Name : Special Characters not allowed",
								amcDateValidation : "Maintenance Date is Required",
								assetNameEmpty : "Asset Name is important",
								purchaseDateEmpty : "Purchase Date can't be empty",
								assetPoDetailEmpty : "Enter Purchase order details",
								assetGeoTagEmpty : "GEO Tag can't be empty",
								assetManufacturerEmpty : "Manufacrurer Name is required",
								assetYearEmpty : "Manufactured date is required",
								warrantyTillEmpty : "Enter Valid Till Date",
								assetModelNoEmpty : "Asset Model Number is required",
								assetManufacturerSlNoEmpty : "Manufacturer Serial number is required",
								assetModelNoValidation : "Invalid Model No : Special Characters not allowed",
								assetManufacturerSlNoValidation : "Invalid Manufacturer SlNo : Special Characters not allowed",

								
							}
						});
	})(jQuery, kendo);
	
	
	function costCenterEditor(container, options) {
		$('<input id="ccId" name="Cost Center" data-text-field="name" data-value-field="ccId" data-bind="value:' + options.field + '" required="true"/>')
				.appendTo(container)
				.kendoDropDownList({
							optionLabel : {
								name : "Select",
								ccId : "",
							},
							defaultValue : false,
							sortable : true,
							dataSource : {
								transport : {
									read : "${costcenter}"
								}
							}
						});
		$('<span class="k-invalid-msg" data-for="Cost Center"></span>')
				.appendTo(container);
	}
	
	function maintDeptEditor(container, options) {
		$('<input name="Department Name" data-text-field="mtDpName" data-value-field="mtDpIt" id="mtDpIt" data-bind="value:' + options.field + '" required="true"/>')
				.appendTo(container).kendoDropDownList({
					optionLabel : {
						mtDpName : "Select",
						mtDpIt : "",
					},
					defaultValue : false,
					sortable : true,
					dataSource : {
						transport : {
							read : {
								url : "${readMaintDeptUrl}",
								type : "GET"
							}
						}
					}
				});
		$('<span class="k-invalid-msg" data-for="Department Name"></span>')
				.appendTo(container);
	}
	
	
	function onSelectupload(e){
		
		$.each(e.files, function (index, value) {			
	       // var ok = (value.extension == ".xlsx" || value.extension == ".xls") ;
	       var ok = (value.extension == ".xls") ;
	        if (!ok) {
	            e.preventDefault();
	            alert("Please upload XL  .xls file");
	        }
	    });

	}
	
	 function onExcelDocSuccess(e){
		/*  var obj = jQuery.parseJSON(e.response );
		 alert()
		 alert(e.cannotImport); */
		// alert(e.response.status);
	       var windowOwner = $("#fileupload").data("kendoWindow");
	      	    windowOwner.close();
		 if (e.response.status == "cannotImport") {
				errorInfo = "";
				errorInfo = e.response.result.cannotImport;
				for (var i = 0; i < e.response.result.length; i++) {
					errorInfo += (i + 1) + ". "
							+ e.response.result[i].defaultMessage;
				}
				alert(errorInfo);
				window.location.reload();

			/* 	$("#alertsBox").html("");
				$("#alertsBox").html(errorInfo);
				$("#alertsBox").dialog({
					modal : true,
					draggable: false,
					resizable: false,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						 	window.location.reload();
						}
					}
				});	 */
				
			  

			}
		 if (e.response.status ==null)
			 {
			 alert("Uploaded successfully");
			 window.location.reload();
			 }
		 
		 
	}
	 
	 function errorRegardingUploadExcel(e){
			var windowOwner = $("#fileupload").data("kendoWindow");
			    windowOwner.close();

			
					var errorInfo="Uploaded file does not contain valid data";
					
					alert(errorInfo);
				/* 	$("#alertsBox").html("");
					$("#alertsBox").html(errorInfo);
					$("#alertsBox").dialog({
						modal : true,
						draggable: false,
						resizable: false,
						buttons : {
							"Close" : function() {
								$(this).dialog("close");
							 	window.location.reload();
							}
						}
					}); */	
				 
		}
	 
		function addExtensionClass(extension) {
			switch (extension) {
			case '.jpg':
			case '.img':
			case '.png':
			case '.gif':
				return "img-file";
			case '.doc':
			case '.docx':
				return "doc-file";
			case '.xls':
			case '.xlsx':
				return "xls-file";
			case '.pdf':
				return "pdf-file";
			case '.zip':
			case '.rar':
				return "zip-file";
			default:
				return "default-file";
			}
		}
		
	

</script>

<style>
.k-edit-form-container {
	text-align: center;
	position: relative;
}

.wrappers {
	display: inline;
	float: left;
	width: 290px;
	padding-top: 10px;
}

.k-edit-form-container {
	text-align: left;
}

.k-window {
	z-index: 10px;
}

.k-datepicker span {
	width: 53%
}

.altRow {
	background: #D4D4D4;
	color: #000000;
}

canvas {
	margin-left: 100px;
}

.k-upload-button input {
	z-index: 10000
}

.k-tabstrip {
	width: 1150px
}

span.k-numerictextbox {
	background-color: transparent;
	width: 85px;
}

.bgGreenColor {
	/* background: #66b370 */
	background: #B4C594
}

.bgRedColor {
	background: #FF9980
}

.k-header {
	background: white
}

div.ui-dialog {
	position: fixed;
	overflow: "auto";
}
</style>


</style>

<style type="text/css">
.k-datepicker span {
	width: 70%
}

.k-datepicker {
	background: white;
}

#grid {
	font-size: 12px !important;
	font-weight: normal;
	color: black;
}

.ui-dialog {
	width: 1090px !important;
	height: auto !important
}

.ui-jqgrid .ui-jqgrid-bdiv {
	overflow-x: hidden;
	height: 180px !important;
	background: #fff;
}

.k-window-titlebar {
	height: 25px;
}
</style>