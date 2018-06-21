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
					<td width="15%" valign="bottom" class="tabDisabled" align="center"><a id="supplier" onClick="setBlockUI(this);" href="supplier.do"> <font class="tabDisabledLink">&nbsp;Leverandør</font>&nbsp;
					</a></td>
					<td width="1px" class="tabFantomSpace" align="center" nowrap><font class="tabDisabledLink">&nbsp;</font></td>
	
					<td width="15%" valign="bottom" class="tab" align="center"><font class="tabLink">&nbsp;Administrasjon</font>&nbsp;</td>

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
							<table>
								<tr>
									<td>&nbsp;</td>
									<td>&nbsp;</td>
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

