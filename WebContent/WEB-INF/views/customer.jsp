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
	var baseUrl = "/visma-net-proxy/viskunde?user=${user.user}";
	
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
			runningUrl = runningUrl + "&fraDato=" + selectedFradato;
		} else {
			runningUrl = runningUrl + "&fraDato=ALL";
		}

		console.log("runningUrl=" + runningUrl);

		jq.blockUI({
			message : BLOCKUI_OVERLAY_MESSAGE_DEFAULT
		});


	var viskundeTable = jq('#viskundeTable').DataTable({
			"dom" : '<"top">t<"bottom"flip><"clear">',
			responsive : true,
			select : true,
			destroy : true,
			"sAjaxSource" : runningUrl,
			"sAjaxDataProp" : "",
			"order" : [ [ 2, "desc" ] ],
			"aoColumns" : [ {
				"mData" : "kundnr"
			}, {
				"mData" : "knavn"
			}, {
				"mData" : "syncda"
			}, {
				"mData" : "syerro"
			}, {
				"mData" : "postnr"
			}, {
				"mData" : "syland"
			}, {
				"mData" : "valkod"
			}, {
				"mData" : "betbet"
			}, {
				"mData" : "spraak"
			} ,{
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

<table width="100%" class="text14">
	<tr height="15"><td>&nbsp;</td></tr>
	<tr>
		<td>
			<%-- tab container component ø --%>
			<table width="100%" class="text14">
				<tr height="2"><td></td></tr>
				<tr height="25">
					<td>&nbsp;</td>
					<td width="15%" valign="bottom" class="tab" align="center"><font class="tabLink">&nbsp;Kunde</font>&nbsp;</td>

					<td width="1px" class="tabFantomSpace" align="center" nowrap><font class="tabDisabledLink">&nbsp;</font></td>

					<td width="15%" valign="bottom" class="tabDisabled" align="center">
					<a onClick="setBlockUI(this);" href="supplier.do"> <font class="tabDisabledLink">&nbsp;Leverandør</font>&nbsp;
					</a></td>

					<td width="1px" class="tabFantomSpace" align="center" nowrap><font class="tabDisabledLink">&nbsp;</font></td>

					<td width="15%" valign="bottom" class="tabDisabled" align="center">
					<a onClick="setBlockUI(this);" href="configuration.do"> <font class="tabDisabledLink">&nbsp;Administrasjon</font>&nbsp;
					</a></td>
	
					<td width="55%" class="tabFantomSpace" align="center" nowrap><font class="tabDisabledLink">&nbsp;</font></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<!-- Second tab row... -->
		<td>
			<table width="100%" class="tabThinBorderWhite">
				<tr height="20">
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td>
						<table class="formFrameHeaderTransparent" style="width: 100%">
							<tr height="20">
								<td width="10%" valign="bottom" class="tabSub" align="center" nowrap><font class="tabLinkMinor">&nbsp; Kunde </font>&nbsp;</td>
								<td width="1px" class="tabFantomSpace" align="center" nowrap><font class="tabDisabledLink">&nbsp;</font></td>
								<td width="10%" valign="bottom" class="tabDisabledSub" align="center" nowrap><a href="customerInvoice.do"> <font class="tabDisabledLinkMinor">&nbsp; Faktura </font>&nbsp;
								</a></td>
								<td width="1px" class="tabFantomSpace" align="center" nowrap><font class="tabDisabledLink">&nbsp;</font></td>
								<td width="10%" valign="bottom" class="tabDisabledSub" align="center" nowrap><a href="viskulog.do"> <font class="tabDisabledLinkMinor">&nbsp; Kunde - historikk </font>&nbsp;
								</a></td>
								<td width="1px" class="tabFantomSpace" align="center" nowrap><font class="tabDisabledLink">&nbsp;</font></td>
								<td width="10%" valign="bottom" class="tabDisabledSub" align="center" nowrap><a href="vistrlogk.do"> <font class="tabDisabledLinkMinor">&nbsp; Faktura - historikk </font>&nbsp;
								</a></td>

								<td width="60%" class="tabFantomSpace" align="center" nowrap></td>

							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td>&nbsp;</td>
					<td width="100%">
						<table width="100%">
							<tr>
								<td>
									<table width="100%" class="tabThinBorderWhite">
										<tr height="20">
											<td>&nbsp;
												<div class="container-fluid">
													<div class="row">
														<div class="col-md-1 text12 text-nowrap">
															<font class="text14">Kunde:</font><br> 
															<input type="text" class="inputText" name="selectKundenr" id="selectKundenr" size="9" maxlength="8"/> 
															<a tabindex="-1"
																id="kundenrLink"> <img style="cursor: pointer; vertical-align: middle;" src="resources/images/find.png" width="14px" height="14px" border="0" />
															</a>&nbsp;
														</div>
														<div class="col-md-1 text12">
															<font class="text14">Fra feildato:</font><br> <input type="text" class="inputText" name="selectFradato" id="selectFradato" size="9" maxlength="8">
														</div>

														<div class="col-md-2" align="right">
															<br>
															<button class="inputFormSubmit" onclick="load_data()" autofocus>Søk</button>
														</div>

													</div>

													<div class="padded-row-small">&nbsp;</div>
													<div class="padded-row-small">&nbsp;</div>

													<div class="panel-body">
														<table width="100%" class="table table-striped table-bordered table-hover" id="viskundeTable">
															<thead class="tableHeaderField">
																<tr>
																	<th>Kundnr</th>
																	<th>Navn</th>
																	<th>Feildato</th>
																	<th>Feil</th>
																	<th>Postnr</th>
																	<th>Land</th>
																	<th>Valuta</th>
																	<th>Språk</th>
																	<th>Bet.beting.</th>
																	<th>Type</th>
																</tr>
															</thead>
														</table>
													</div>
												</div>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>

	</tr>
</table>

<!-- ======================= footer ===========================-->
<jsp:include page="/WEB-INF/views/footer.jsp" />
<!-- =====================end footer ==========================-->

