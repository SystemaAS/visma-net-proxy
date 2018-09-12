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

	var baseUrl = "/visma-net-proxy/vistransh?user=${user.user}";
	
	function load_data() {

		var runningUrl = baseUrl;

		var selectedBilnr = jq('#selectBilnr').val();	
		var selectedFradato = jq('#selectFradato').val();

		if (selectedBilnr != "") {
			runningUrl = runningUrl + "&bilnr=" + selectedBilnr;
		} else {
			runningUrl = runningUrl + "&bilnr=ALL";
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


	var vistranshTable = jq('#vistranshTable').DataTable({
			"dom" : '<"top">t<"bottom"flip><"clear">',
			responsive : true,
			select : true,
			destroy : true,
			"sAjaxSource" : runningUrl,
			"sAjaxDataProp" : "",
			"order" : [ [ 2, "desc" ] ],

			"aoColumns" : [  {
				"mData" : "bilnr"
			}, {
				"mData" : "biltxt"
			},{
				"mData" : "syncda"
			}, {
				"mData" : "syerro"
			}, {
				"mData" : "kontov"
			}, {
				"mData" : "ksted"
			}, {
				"mData" : "nbelpo"
			}, {
				"mData" : "fakkre"
			}, {
				"mData" : "valkox"
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

<div class="container-fluid">

	<div class="padded-row-small"></div>	

	<nav>
	  <div class="nav nav-tabs" id="nav-tab" role="tablist">
	    <a class="nav-item nav-link" onClick="setBlockUI(this);" href="customer.do"><strong>Kunde</strong>
			<c:if test="${not empty customer_all_error}">
			    <span class="badge badge-danger">${customer_all_error}</span>
			</c:if>		    
	    </a>
		<a class="nav-item nav-link active" onClick="setBlockUI(this);" href="supplier.do"><strong>Leverandør</strong>
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
	    <a class="nav-item nav-link" onClick="setBlockUI(this);" href="supplier.do"><strong>Leverandør</strong>
			<c:if test="${not empty supplier_error}">
			    <span class="badge badge-danger">${supplier_error}</span>
			</c:if>	    
	    </a>
		<a class="nav-item nav-link" onClick="setBlockUI(this);" href="supplierInvoice.do"><strong>Faktura</strong>
			<c:if test="${not empty supplier_invoice_error}">
			    <span class="badge badge-danger">${supplier_invoice_error}</span>
			</c:if>			
		</a>
		<a class="nav-item nav-link active" href="supplierInvoiceJournal.do"><strong>Hovedbok</strong>
			<c:if test="${not empty supplier_invoice_journal_error}">
			    <span class="badge badge-danger">${supplier_invoice_journal_error}</span>
			</c:if>	    
		</a>
		<a class="nav-item nav-link" onClick="setBlockUI(this);" href="vislelog.do">Leverandør - feilhistorikk</a>
	    <a class="nav-item nav-link " onClick="setBlockUI(this);" href="vistrlogl.do">Faktura - feilhistorikk</a>
		<a class="nav-item nav-link" onClick="setBlockUI(this);" href="vistrlogh.do">Hovedbok - feilhistorikk</a>
	  </div>
	</nav>
	
	<div class="padded-row-small">&nbsp;</div>	
		
	<div class="row">
		<div class="col-1">
			<label for="selectBilnr">Fakturanr:&nbsp;</label>
			<input type="text" class="inputText" name="selectBilnr" id="selectBilnr" size="8" maxlength="7">
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
		<table class="table table-striped table-bordered table-hover" id="vistranshTable">
			<thead class="tableHeaderField">
				<tr>
					<th>Fakturanr</th>
					<th>Text</th>
					<th>Feildato</th>
					<th>Feil (på faktura hode)</th>
					<th>Konto</th>
					<th>Kost.sted</th>
					<th>Beløp</th>
					<th>Type</th>
					<th>Valutakod</th>	
				</tr>
			</thead>
		</table>
	</div>
  
</div>

<!-- ======================= footer ===========================-->
<jsp:include page="/WEB-INF/views/footer.jsp" />
<!-- =====================end footer ==========================-->

