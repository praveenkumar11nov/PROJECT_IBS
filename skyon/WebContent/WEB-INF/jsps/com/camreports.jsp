<%@include file="/common/taglibs.jsp"%>	

<c:url value="/com/readCamData" var="readCAMBills" />

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
	       <a class='k-button' href='\\#' onclick="clearFilterOwner()"><span class='k-icon k-i-funnel-clear'></span>Clear Filter</a>
	       <a class="k-button k-button-icontext k-grid-ownerTemplatesDetailsExport" href="#"><span class=" "></span>Export To Excel</a>
      </div>
      
      </kendo:grid-toolbarTemplate>
        <kendo:grid-columns>
		<kendo:grid-column title="Account ID" field="accid" filterable="false" hidden="true" width="80px"></kendo:grid-column>
		
        <kendo:grid-column title="Elb Id" field="elbid" filterable="false" hidden="true" width="80px"/>
	
		<kendo:grid-column title="Property Number"  field="propertyno" filterable="true" width="130px"/>

		<kendo:grid-column title="Account Number"  field="accnumber" filterable="false" hidden="true" width="130px"/>

		<kendo:grid-column title="Bill Month"  field="billmonth" filterable="true" width="130px"/>

		<kendo:grid-column title="Cam Amount" field="camamount" filterable="false"  width="110px" />

		<kendo:grid-column title="CGST" field="cgstamt" filterable="false"  width="110px" />

		<kendo:grid-column title="SGST" field="sgstamt" filterable="false"  width="110px" />
		
		<kendo:grid-column title="CGST Late Pay" field="cgstltpay" filterable="false"  width="110px" />

		<kendo:grid-column title="SGST Late Pay" field="sgstltpay" filterable="false"  width="110px" />
		
		<kendo:grid-column title="Previous Late Paid" field="pldamt" filterable="false"  width="110px" />

		<kendo:grid-column title="Gst On PLD" field="gstonpld" filterable="false"  width="110px" />
		
		<kendo:grid-column title="Arrear Amount" field="arrearamt" filterable="false"  width="110px" />

		<kendo:grid-column title="Bill Amount" field="billamt" filterable="false"  width="110px" />
		
		<kendo:grid-column title="Net Amount" field="netamt" filterable="false"  width="110px" />

	</kendo:grid-columns>
        <kendo:dataSource  requestStart="requestStart" requestEnd="requestEnd">
            <kendo:dataSource-transport>
            
                <kendo:dataSource-transport-read url="${readCAMBills}" dataType="json" type="POST" contentType="application/json"/>
             
            </kendo:dataSource-transport>
            <kendo:dataSource-schema>
                <kendo:dataSource-schema-model id="account_Id">
                 <kendo:dataSource-schema-model-fields>
                         
                       <kendo:dataSource-schema-model-field name="accid"   type="string"/>
                       <kendo:dataSource-schema-model-field name="elbid"   type="string"/>
                       <kendo:dataSource-schema-model-field name="propertyno" type="string"/>
                       <kendo:dataSource-schema-model-field name="accnumber"  type="string"/>
                       
                       <kendo:dataSource-schema-model-field name="billmonth" type="string"/>
                       <kendo:dataSource-schema-model-field name="camamount" type="string"/>
                       <kendo:dataSource-schema-model-field name="cgstamt"   type="string"/>
                       <kendo:dataSource-schema-model-field name="sgstamt"   type="string"/>
                       <kendo:dataSource-schema-model-field name="cgstltpay" type="string"/>
                       <kendo:dataSource-schema-model-field name="sgstltpay" type="string"/>
                       
                       <kendo:dataSource-schema-model-field name="pldamt"    type="string"/>
                       <kendo:dataSource-schema-model-field name="gstonpld"  type="string"/>
                       <kendo:dataSource-schema-model-field name="arrearamt" type="string"/>
                       <kendo:dataSource-schema-model-field name="billamt"   type="string"/>
                       <kendo:dataSource-schema-model-field name="netamt"    type="string"/>
                        
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
						$("form.k-filter-menu button[type='reset']").slice().trigger("click");
						var grid = $("#grid").data("kendoGrid");
						grid.dataSource.read();
						grid.refresh();
					}

					$("#grid").on("click",".k-grid-ownerTemplatesDetailsExport",
							function(e) {
								//window.open("./ExportCamReportToExcel/" + "${data}");
								window.open("./ExportCamReportToExcel");
							});
				</script>     
 	