<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include.jsp"%>

<!-- ======================= header ===========================-->
<jsp:include page="/WEB-INF/views/headerVisma.jsp" />
<!-- =====================end header ==========================-->
<%-- specific jQuery functions for this JSP (must reside under the resource map since this has been
		specified in servlet.xml as static <mvc:resources mapping="/resources/**" location="WEB-INF/resources/" order="1"/> --%>
<SCRIPT type="text/javascript" src="resources/js/mainmaintenancecundf_vkund.js?ver=${user.versionEspedsg}"></SCRIPT>

<style type="text/css">
.ui-datepicker {
	font-size: 9pt;
}
</style>


<table width="100%" class="text12">
	<tr height="15">
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td>
			<%-- tab container component --%>
			<table width="100%" class="text12">
				<tr height="2">
					<td></td>
				</tr>
				<tr height="25">
					<td>&nbsp;</td>
					<td width="15%" valign="bottom" class="tabDisabled" align="center"><a id="customer" onClick="setBlockUI(this);" href="customer.do"> <font class="tabDisabledLink">&nbsp;Kunde</font>&nbsp;
					</a></td>
					<td width="1px" class="tabFantomSpace" align="center" nowrap><font class="tabDisabledLink">&nbsp;</font></td>
					<td width="15%" valign="bottom" class="tabDisabled" align="center"><a id="supplier" onClick="setBlockUI(this);" href="supplier.do"> <font class="tabDisabledLink">&nbsp;Leverantør</font>&nbsp;
					</a></td>
					<td width="1px" class="tabFantomSpace" align="center" nowrap><font class="tabDisabledLink">&nbsp;</font></td>
	
					<td width="15%" valign="bottom" class="tab" align="center"><font class="tabLink">&nbsp;Konfigurasjon</font>&nbsp;</td>

					<td width="55%" class="tabFantomSpace" align="center" nowrap><font class="tabDisabledLink">&nbsp;</font></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table class="tabThinBorderWhite" width="100%" border="0">
				<tr height="20">
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td width="5%">&nbsp;</td>
					<td width="95%">&nbsp;
						<form action="configuration.do" name="formRecord" id="formRecord" method="POST">

							<table border="0">
								<tr>
									<td class="text14" title="kundnr">&nbsp;<font class="text14RedBold">*</font> SYSPED Firma:
									</td>
									<td><input type="text" required oninvalid="this.setCustomValidity('Obligatoriskt')" onchange="setCustomValidity('')" class="inputTextMediumBlueMandatoryField" name="vifirm" id="vifirm"
										size="3" maxlength="2" value='${model.firmvis.vifirm}'></td>
								</tr>

								<tr>
									<td class="text14" title="kundnr">&nbsp;<font class="text14RedBold">*</font> Visma.net id:
									</td>
									<td><input type="text" required oninvalid="this.setCustomValidity('Obligatoriskt')" onchange="setCustomValidity('')" class="inputTextMediumBlueMandatoryField" name="vicoid" id="vicoid"
										size="12" maxlength="10" value='${model.firmvis.vicoid}'></td>
								</tr>

								<tr>
									<td class="text14" title="knavn">&nbsp;<font class="text14RedBold">*</font> Visma URL:
									</td>
									<td><input type="text" required oninvalid="this.setCustomValidity('Obligatoriskt')" onchange="setCustomValidity('')" class="inputTextMediumBlueMandatoryField" name="vibapa" id="vibapa"
										size="52" maxlength="50" value='${model.firmvis.vibapa}'></td>
								</tr>

								<tr>
									<td class="text14" title="knavn">&nbsp;<font class="text14RedBold">*</font> Applikasjons type:
									</td>
									<td><input type="text" required oninvalid="this.setCustomValidity('Obligatoriskt')" onchange="setCustomValidity('')" class="inputTextMediumBlueMandatoryField" name="viapty" id="viapty"
										size="32" maxlength="30" value='${model.firmvis.viapty}'></td>
								</tr>

								<tr>
									<td class="text14" title="knavn">&nbsp;<font class="text14RedBold">*</font> Authentication code:
									</td>
									<td><input type="text" required oninvalid="this.setCustomValidity('Obligatoriskt')" onchange="setCustomValidity('')" class="inputTextMediumBlueMandatoryField" name="viauco" id="viauco"
										size="52" maxlength="50" value='${model.firmvis.viauco}'></td>
								</tr>

								<tr>
									<td class="text14" title="knavn">&nbsp;<font class="text14RedBold">*</font> Access token:
									</td>
									<td><input type="text" required oninvalid="this.setCustomValidity('Obligatoriskt')" onchange="setCustomValidity('')" class="inputTextMediumBlueMandatoryField" name="viacto" id="viacto"
										size="52" maxlength="50" value='${model.firmvis.viacto}'></td>
								</tr>

								<tr>
									<td>&nbsp;</td>
									<td align="right"><input class="inputFormSubmit" type="submit" name="submit" id="submit" value='Lagre' /></td>
									<td>&nbsp;&nbsp;
								 	  <button name="generateButton" id="generateButton" class="buttonGrayWithGreenFrame" type="button" >Generere sikkerhetsnøkler</button>
									</td>

								</tr>

							</table>
						</form>
					</td>



				</tr>
				<tr height="20">
					<td>&nbsp;</td>
				</tr>
			</table>

		</td>
	</tr>
</table>

<!-- ======================= footer ===========================-->
<jsp:include page="/WEB-INF/views/footer.jsp" />
<!-- =====================end footer ==========================-->

