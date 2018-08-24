<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include.jsp"%>

<!-- ======================= header ===========================-->
<jsp:include page="/WEB-INF/views/headerVisma.jsp" />
<!-- =====================end header ==========================-->

<script type="text/javascript">
	"use strict";

	var baseUrl = "/visma-net-proxy/visleve?user=${user.user}";
	
	function load_data() {

		var runningUrl = baseUrl;

		var selectedLevnr = jq('#selectLevnr').val();
		var selectedFradato = jq('#selectFradato').val();

		if (selectedLevnr != "") {
			runningUrl = runningUrl + "&levnr=" + selectedLevnr;
		} else {
			runningUrl = runningUrl + "&levnr=ALL";
		}
		if (selectedFradato != null && selectedFradato != "") {
			runningUrl = getRunningUrl(runningUrl, selectedFradato);
			if (runningUrl == '-1') {
				return "no data found";
			}		
		} else {
			runningUrl = runningUrl + "&fraDato=ALL";
		}

		console.log("runningUrl=" + runningUrl);

		jq.blockUI({
			message : BLOCKUI_OVERLAY_MESSAGE_DEFAULT
		});


	var visleveTable = jq('#visleveTable').DataTable({
			"dom" : '<"top">t<"bottom"flip><"clear">',
			responsive : true,
			select : true,
			destroy : true,
			"sAjaxSource" : runningUrl,
			"sAjaxDataProp" : "",
			"order" : [ [ 2, "desc" ] ],
			"aoColumns" : [ {
				"mData" : "levnr"
			}, {
				"mData" : "lnavn"
			}, {
				"mData" : "syncda"
			}, {
				"mData" : "syerro"
			}, {
				"mData" : "postnr"
			}, {
				"mData" : "postnu"
			}, {
				"mData" : "land"
			}, {
				"mData" : "betbet"
			} ],
			"lengthMenu" : [ 25, 75, 100 ],
			"language" : {
				"url" : getLanguage('NO')
			}

		});

		jq.unblockUI();

		
	}

	window.addEventListener('error', function(e) {
		var error = e.error;
		jq.unblockUI();
		console.log("Event e", e);

		alert('Uforutsett fel har intreffet.');

	});
</script>

<!-- https://getbootstrap.com/docs/4.0/components/navs/   -->

<div class="container-fluid">

	<div class="padded-row-small"></div>	

	<nav>
	  <div class="nav nav-tabs" id="nav-tab" role="tablist">
	    <a class="nav-item nav-link" onClick="setBlockUI(this);" href="customer.do" >Kunde
			<c:if test="${not empty customer_all_error}">
			    <span class="badge badge-danger">${customer_all_error}</span>
			</c:if>		    
	    </a>
		<a class="nav-item nav-link active" href="supplier.do">Leverandør
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
	    <a class="nav-item nav-link active" href="supplier.do">Leverandør
			<c:if test="${not empty supplier_error}">
			    <span class="badge badge-danger">${supplier_error}</span>
			</c:if>	    
	    </a>
		<a class="nav-item nav-link" onClick="setBlockUI(this);" href="supplierInvoice.do">Faktura
			<c:if test="${not empty supplier_invoice_error}">
			    <span class="badge badge-danger">${supplier_invoice_error}</span>
			</c:if>	    
		</a>
		<a class="nav-item nav-link" onClick="setBlockUI(this);" href="vislelog.do">Leverandør - feilhistorikk</a>
	    <a class="nav-item nav-link" onClick="setBlockUI(this);" href="vistrlogl.do">Faktura - feilhistorikk</a>
	  </div>
	</nav>
	
	<div class="padded-row-small">&nbsp;</div>	
		
	<div class="row">
		<div class="col-1">
			<label for="selectLevnr">Leverandør:&nbsp;</label>
			<input type="text" class="inputText" name="selectLevnr" id="selectLevnr" size="9" maxlength="8"/> 
		</div>
		<div class="col-1">
			<label for="selectFradato">Fra&nbsp;feildato:&nbsp;</label>
			<input type="text" class="inputText" name="selectFradato" id="selectFradato" size="11" maxlength="10">
		</div>
		<div class="col-1">
			<br>
			<button class="btn inputFormSubmit" onclick="load_data()" autofocus>Søk</button>
		</div>
	</div>

	<div class="padded-row-small">&nbsp;</div>
	
	<div class="panel-body">
		<table class="table table-striped table-bordered table-hover" id="visleveTable">
			<thead class="tableHeaderField">
				<tr>
					<th>Levnr</th>
					<th>Navn</th>
					<th>Feildato</th>
					<th>Feil</th>
					<th>Postnr</th>
					<th>Postnr(utl.)</th>
					<th>Land</th>
					<th>Bet.beting.</th>
				</tr>
			</thead>
		</table>
	</div>	
  
</div>

<!-- ======================= footer ===========================-->
<jsp:include page="/WEB-INF/views/footer.jsp" />
<!-- =====================end footer ==========================-->

