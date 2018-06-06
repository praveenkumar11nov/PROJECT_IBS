<%@include file="/common/taglibs.jsp"%>

<script>
	$(document).ready(function() {
/* 		var qry="${qry}";
		alert("qry="+qry);
		if(qry==null||qry=''){
			$('#display').hide();
		}else{
			$('#display').show();
		}
		
		 */
	});

	function clearButton() {
		$('#qry').val("");
	}
</script>

<style>
.form-horizontal .form-group {
	margin-left: 0px !important;
	margin-right: 0px !important;
}

select {
	width: 100%;
	border: 1px solid #DDD;
	border-radius: 3px;
	height: 36px;
}

legend {
	text-transform: none;
	font-size: 16px;
	border-color: skyblue;
}

.datatable-header,.datatable-footer {
	padding: 8px 0px 0 0px;
}
</style>


<script type="text/javascript">

function goingToBack(){
	   window.history.back();
}
function finalSubmit() {
	if(document.getElementById("qry").value == "" || document.getElementById("qry").value == null){
		 var msg = "${msg}";
			$("#alertsBox").html("Please Enter The Query !!!");
			$("#alertsBox").dialog({
				modal : true,
				buttons : {
					"Close" : function() {
					$(this).dialog("close");
			 	}
			  }
		    });
		 return false;
	}else{
		$("#queryId").attr("action", "./getReport").submit(); 
	}
}
</script>
<div id="alertsBox" title="Alert"></div>

<div class="panel panel-flat">
	<div class="panel-body">

		<c:if test="${not empty msg}">
			<script>
				var msg = "${msg}";
				$("#alertsBox").html(msg);
				$("#alertsBox").dialog({
					modal : true,
					buttons : {
						"Close" : function() {
							$(this).dialog("close");
						}
					}
				});
			</script>
			<c:remove var="msg" scope="session" />
		</c:if>

		<c:if test="${not empty qry}">
			<script>
					var qry = "${qry}";
					//alert(qry);
					//$('#display').hide();
			</script>
			<c:remove var="qry" scope="session" />
		</c:if>

		<form action="#" method="POST" id="queryId" role="form" class="form-horizontal form-validate-jquery">
			<fieldset class="content-group">
				<legend class="text-bold" style="margin: auto; text-align: left; font-size: 18px;"><a href="./sql">SQL Editor</a></legend>
				<br> 
				<select name="dbaccess">
					<option value="LOCAL">LOCAL</option>
					<option value="LIVE">LIVE</option>
				</select>

				<div class="row">
					<div class="col-md-12 form-group">
						<label>Enter Query.<font color="red">*</font></label>
						<textarea class="form-control" name="qry" id="qry"
							placeholder="Enter The Select Query Only....">${qry}</textarea>
					</div>
				</div>
				<br>

				<div class="text-center">
					<button type="button" class="btn btn-primary"
						onclick="clearButton();">
						<span class="ladda-label">Clear</span>
					</button>
					<label>&nbsp;</label>
					<button type="submit" class="btn btn-primary"
						onclick="return finalSubmit();">
						Submit<i class="icon-arrow-right14 position-right"></i>
					</button>
				</div>


			</fieldset>
		</form>
		<br></br>
</div>
</div>
<div class="panel panel-flat" id="display">
	<div class="panel-body" id="gridId1">
		<fieldset class="content-group">
			<legend class="text-bold">Report Details</legend>
			<div class="row">
				<div class="col-md-12">
					<div class="table-responsive">
						<div class="panel-body" style="margin-top: -3px; padding: 6px;">
							<table id="tableForm" border="1">
								<thead style="width: 100px">

									<tr class="bg-blue">
									 <td bgcolor="#ccff33" align="center">SL No.</td>
										<c:forEach items="${rows[0]}" var="column">
											<th bgcolor="#0099ff"><c:out value="${column.key}" /></th>
										</c:forEach>


										<th hidden="true"></th>
										<th hidden="true"></th>
										<th hidden="true"></th>
										<th hidden="true"></th>
										<th hidden="true"></th>
										<th hidden="true"></th>


									</tr>

								</thead>
								<tbody>

									<c:forEach items="${rows}" var="columns" varStatus="counter">
										<tr>
										    <td bgcolor="#33cc33" align="center">${counter.count}</td>
											<c:forEach items="${columns}" var="column">
												<td><c:out value="${column.value}" /></td>
											</c:forEach>
											<td hidden="true"></td>
											<td hidden="true"></td>
											<td hidden="true"></td>
											<td hidden="true"></td>
											<td hidden="true"></td>
											<td hidden="true"></td>
										</tr>
									</c:forEach>


								</tbody>
							</table>

						</div>

					</div>
				</div>

			</div>

		</fieldset>
	</div>
</div>

