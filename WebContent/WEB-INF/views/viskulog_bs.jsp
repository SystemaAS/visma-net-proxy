<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include.jsp"%>

<!-- ======================= header ===========================-->
<jsp:include page="/WEB-INF/views/headerVisma.jsp" />
<!-- =====================end header ==========================-->

<style type="text/css">
.ui-datepicker {
	font-size: 9pt;
}
</style>

<script type="text/javascript">
	"use strict";

	var baseUrl = "/visma-net-proxy/viskulog?user=${user.user}";
	
	function load_data() {

		var runningUrl = baseUrl;

		var selectedKundenr = jq('#selectKundenr').val();
		var selectedFradato = jq('#selectFradato').val();

		if (selectedKundenr != "") {
			runningUrl = runningUrl + "&kundnr=" + selectedKundenr;
		} else {
			runningUrl = runningUrl + "&kundnr=ALL";
		}

		if (selectedFradato != null && selectedFradato != "") {
			runningUrl = getRunningUrl(runningUrl, selectedFradato);
			if (runningUrl == '-1') {
				return "no data found";
			}
		} else {
			alert('Dato er obligatorisk.'); 
			return "no data found";
		}		
		
		console.log("runningUrl=" + runningUrl);

		jq.blockUI({
			message : BLOCKUI_OVERLAY_MESSAGE_DEFAULT
		});

		var viskulogTable = jq('#viskulogTable').DataTable({
			"dom" : '<"top">t<"bottom"flip><"clear">',
			responsive : true,
			select : true,
			destroy : true,
			"sAjaxSource" : runningUrl,
			"sAjaxDataProp" : "",
			"order" : [ [ 3, "desc" ] ],
			"aoColumns" : [ {
				"mData" : "kundnr"
			}, {
				"mData" : "knavn"
			}, {
				"mData" : "syncda"
			}, {
				"mData" : "synctm"
			}, {
				"mData" : "syerro"
			} ],
			"lengthMenu" : [ 25, 75, 100 ],
			"language" : {
				"url" : getLanguage('NO')
			}

		});

		jq.unblockUI();

	}

	jq(document).ready(function() {

	});

	window.addEventListener('error', function(e) {
		var error = e.error;
		jq.unblockUI();
		console.log("Event e", e);

		alert('Uforutsett fel har intreffet.');

	});
</script>

<div class="container-fluid">

	<div class="padded-row-small"></div>	

	<nav>
	  <div class="nav nav-tabs" id="nav-tab" role="tablist">
	    <a class="nav-item nav-link active" onClick="setBlockUI(this);" href="customer.do"><strong>Kunde</strong>
			<c:if test="${not empty customer_all_error}">
			    <span class="badge badge-danger">${customer_all_error}</span>
			</c:if>		    
	    </a>
		<a class="nav-item nav-link" onClick="setBlockUI(this);" href="supplier.do"><strong>Leverandør</strong>
			<c:if test="${not empty supplier_all_error}">
			    <span class="badge badge-danger">${supplier_all_error}</span>
			</c:if>			
		</a>
	    <a class="nav-item nav-link" onClick="setBlockUI(this);" href="administration.do" role="tab">Administrasjon</a>
		<a class="nav-item nav-link" onClick="setBlockUI(this);" href="log.do" role="tab">Log</a>
	  </div>
	</nav>

  	<div class="padded-row-small">&nbsp;</div>	
 
		
	<nav>
	  <div class="nav nav-tabs" id="nav-tab" role="tablist">
	    <a class="nav-item nav-link" onClick="setBlockUI(this);" href="customer.do"><strong>Kunde</strong>
			<c:if test="${not empty customer_error}">
			    <span class="badge badge-danger">${customer_error}</span>
			</c:if>	    
	    </a>
		<a class="nav-item nav-link" onClick="setBlockUI(this);" href="customerInvoice.do"><strong>Faktura</strong>
			<c:if test="${not empty customer_invoice_error}">
			    <span class="badge badge-danger">${customer_invoice_error}</span>
			</c:if>			
		</a>
		<a class="nav-item nav-link active" href="viskulog.do">Kunde - feilhistorikk</a>
	    <a class="nav-item nav-link" onClick="setBlockUI(this);" href="vistrlogk.do">Faktura - feilhistorikk</a>
	  </div>
	</nav>
	
	<div class="padded-row-small">&nbsp;</div>	
		
	<div class="row">
		<div class="col-1">
			<label for="selectKundenr">Kunde:&nbsp;</label>
			<input type="text" class="inputText" name="selectKundenr" id="selectKundenr" size="9" maxlength="8"/> 
			<a tabindex="-1"
				id="kundenrLink"> <img style="cursor: pointer; vertical-align: middle;" src="resources/images/find.png" width="14px" height="14px" border="0" />
			</a>&nbsp;
		</div>
		<div class="col-1">
			<label for="selectFradato">Fra&nbsp;dato:&nbsp;</label>
			<input type="text" class="inputTextMediumBlueMandatoryField" name="selectFradato" id="selectFradato" size="11" maxlength="10">
		</div>
		<div class="col-1">
			<br>
			<button class="btn inputFormSubmit" onclick="load_data()" autofocus>Søk</button>
		</div>
	</div>

	<div class="padded-row-small">&nbsp;</div>
	
	<div class="panel-body">
		<table class="table table-striped table-bordered table-hover" id="viskulogTable">
			<thead class="tableHeaderField">
				<tr>
					<th>Kundnr</th>
					<th>Navn</th>
					<th>Dato</th>
					<th>Tid</th>
					<th>Feil</th>
				</tr>
			</thead>
		</table>
	</div>
  
</div>


<!-- ======================= footer ===========================-->
<jsp:include page="/WEB-INF/views/footer.jsp" />
<!-- =====================end footer ==========================-->

