<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include.jsp"%>

<!-- ======================= header ===========================-->
<jsp:include page="/WEB-INF/views/headerVisma.jsp" />
<!-- =====================end header ==========================-->

<script type="text/javascript">
	"use strict";
	jq(function() {
		jq("#selectDato").datepicker({
			dateFormat : 'yy-mm-dd'
		});
	});
	var jq = jQuery.noConflict();
	var baseUrl = "/visma-net-proxy/showHistory.do?user=${user.user}&filename=log4j_visma-net-proxy-transaction.log";
	
	function load_data() {
		var runningUrl = baseUrl;
		var selectedDato = jq('#selectDato').val();

		//Adding date on filename
		if (selectedDato != null && selectedDato != "") {
			let today = new Date().toISOString().slice(0, 10);
			let dato = new Date(selectedDato).toISOString().slice(0, 10);
			
			if (dato > today) {
				console.log("dato > today");
				alert('Du har valgt ett dato i framtiden:-)');
				return "no data found";
			}
			if (dato == today) {
				console.log("dato == today");
			} else {
				runningUrl = runningUrl + "." + selectedDato;				
			}

		} else {
			alert('Dato er obligatorisk.'); 
			return "no data found";
		}

		console.log("runningUrl=" + runningUrl);

		jq.blockUI({
			message : BLOCKUI_OVERLAY_MESSAGE_DEFAULT
		});

		jq.ajax({
			url : runningUrl,
			method : "POST", //to avoid invalidate session
		}).done(function(data) {
			jq('#logText').text(data);
		}).fail(function(data) {
			console.log("Error", data);
			alert("Synkronisering gikk feil.", data);
		}).always(function() {
			jq.unblockUI();
		});
		
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
	    <a class="nav-item nav-link" onClick="setBlockUI(this);" href="customer.do" ><strong>Kunde</strong>
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
	    <a class="nav-item nav-link active" onClick="setBlockUI(this);" href="log.do" role="tab">Log</a>
	  </div>
	</nav>

	<div class="padded-row-small">&nbsp;</div>	
		
	<div class="row">
		<div class="col-md-2">
			<label for="selectFradato">Dato:&nbsp;</label>
			<input type="text" class="inputTextMediumBlueMandatoryField" name="selectDato" id="selectDato" size="11" maxlength="10">
		</div>
		<div class="col-md-2">
			<button class="btn inputFormSubmit" onclick="load_data()" autofocus>Søk</button>
		</div>
	</div>

	<div class="padded-row-small">&nbsp;</div>
		
	<div class="row">
		<div class="col-md-12">
			<textarea id="logText" style="min-width: 100%" rows="50">&nbsp;</textarea>
		</div>
	</div>

</div>

<!-- ======================= footer ===========================-->
<jsp:include page="/WEB-INF/views/footer.jsp" />
<!-- =====================end footer ==========================-->

