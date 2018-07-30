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
	jq(function() {
		jq("#selectFradato").datepicker({
			dateFormat : 'yymmdd'
		});
		jq("#selectTildato").datepicker({
			dateFormat : 'yymmdd'
		});
	});
	var jq = jQuery.noConflict();
	var BLOCKUI_OVERLAY_MESSAGE_DEFAULT = "Vennligst vent...";
	var baseUrl = "/visma-net-proxy/vistransl?user=${user.user}";
	
	function load_data() {

		var runningUrl = baseUrl;

		var selectedLevnr = jq('#selectLevnr').val();
		var selectedBilnr = jq('#selectBilnr').val();	
		var selectedFradato = jq('#selectFradato').val();

		if (selectedLevnr != "") {
			runningUrl = runningUrl + "&levnr=" + selectedLevnr;
		} else {
			runningUrl = runningUrl + "&levnr=ALL";
		}
		if (selectedBilnr != "") {
			runningUrl = runningUrl + "&bilnr=" + selectedBilnr;
		} else {
			runningUrl = runningUrl + "&bilnr=ALL";
		}
		if (selectedFradato != null && selectedFradato != "") {
			runningUrl = runningUrl + "&fraDato=" + selectedFradato;
		} else {
			runningUrl = runningUrl + "&fraDato=ALL";
		}

		console.log("runningUrl=" + runningUrl);

		jq.blockUI({
			message : BLOCKUI_OVERLAY_MESSAGE_DEFAULT
		});


	var vistranslTable = jq('#vistranslTable').DataTable({
			"dom" : '<"top">t<"bottom"flip><"clear">',
			responsive : true,
			select : true,
			destroy : true,
			"sAjaxSource" : runningUrl,
			"sAjaxDataProp" : "",
			"order" : [ [ 4, "desc" ] ],

			"aoColumns" : [ {
				"mData" : "recnr"
			}, {
				"mData" : "bilnr"
			}, {
				"mData" : "posnr"
			},{
				"mData" : "biltxt"
			},{
				"mData" : "syncda"
			}, {
				"mData" : "syerro"
			}, {
				"mData" : "konto"
			}, {
				"mData" : "ksted"
			}, {
				"mData" : "kbarer"
			}, {
				"mData" : "betbet"
			}, {
				"mData" : "aktkod"
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
	    <a class="nav-item nav-link" onClick="setBlockUI(this);" href="customer.do">Kunde
			<c:if test="${not empty customer_all_error}">
			    <span class="badge badge-danger">${customer_all_error}</span>
			</c:if>		    
	    </a>
		<a class="nav-item nav-link active" onClick="setBlockUI(this);" href="supplier.do">Leverandør
			<c:if test="${not empty supplier_all_error}">
			    <span class="badge badge-danger">${supplier_all_error}</span>
			</c:if>			
		</a>
	    <a class="nav-item nav-link" onClick="setBlockUI(this);" href="administration.do" role="tab">Administrasjon</a>
	  </div>
	</nav>

  	<div class="padded-row-small">&nbsp;</div>	
 
		
	<nav>
	  <div class="nav nav-tabs" id="nav-tab" role="tablist">
	    <a class="nav-item nav-link"  onClick="setBlockUI(this);" href="supplier.do">Leverandør
			<c:if test="${not empty supplier_error}">
			    <span class="badge badge-danger">${supplier_error}</span>
			</c:if>	    
	    </a>
		<a class="nav-item nav-link active" href="supplierInvoice.do">Faktura
			<c:if test="${not empty supplier_invoice_error}">
			    <span class="badge badge-danger">${supplier_invoice_error}</span>
			</c:if>			
		</a>
		<a class="nav-item nav-link" onClick="setBlockUI(this);" href="vislelog.do">Leverandør - historikk</a>
	    <a class="nav-item nav-link " onClick="setBlockUI(this);" href="vistrlogl.do">Faktura - historikk</a>
	  </div>
	</nav>
	
	<div class="padded-row-small">&nbsp;</div>	
		
	<div class="row">
		<div class="col-1">
			<label for="selectKundenr">Leverandør:&nbsp;</label>
			<input type="text" class="inputText" name="selectLevnr" id="selectLevnr" size="9" maxlength="8"/> 
		</div>
		<div class="col-1">
			<label for="selectBilnr">Fakturanr:&nbsp;</label>
			<input type="text" class="inputText" name="selectBilnr" id="selectBilnr" size="8" maxlength="7">
		</div>		
		<div class="col-1">
			<label for="selectFradato">Fra&nbsp;feildato:&nbsp;</label>
			<input type="text" class="inputText" name="selectFradato" id="selectFradato" size="9" maxlength="8">
		</div>
		<div class="col-1">
		 	<br>
			<button class="btn inputFormSubmit" onclick="load_data()" autofocus>Søk</button>
		</div>
	</div>

	<div class="padded-row-small">&nbsp;</div>
	
	<div class="panel-body">
		<table class="table table-striped table-bordered table-hover" id="vistranslTable">
			<thead class="tableHeaderField">
				<tr>
					<th>Levnr</th>
					<th>Fakturanr</th>
					<th>Pos</th>
					<th>Text</th>
					<th>Feildato</th>
					<th>Feil (på faktura hode)</th>
					<th>Konto</th>
					<th>Kost.sted</th>
					<th>Kost.barer</th>
					<th>Bet.beting.</th>
					<th>Type</th>
				</tr>
			</thead>
		</table>
	</div>
  
</div>

<!-- ======================= footer ===========================-->
<jsp:include page="/WEB-INF/views/footer.jsp" />
<!-- =====================end footer ==========================-->
