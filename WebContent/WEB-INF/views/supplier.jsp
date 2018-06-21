<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include.jsp" %>

<!-- ======================= header ===========================-->
<jsp:include page="/WEB-INF/views/headerVisma.jsp" />
<!-- =====================end header ==========================-->
	<%-- specific jQuery functions for this JSP (must reside under the resource map since this has been
		specified in servlet.xml as static <mvc:resources mapping="/resources/**" location="WEB-INF/resources/" order="1"/> --%>
	<SCRIPT type="text/javascript" src="resources/js/mainmaintenancecundf_vkund.js?ver=${user.versionEspedsg}"></SCRIPT>	
	
	<style type = "text/css">
	.ui-datepicker { font-size:9pt;}
	</style>


<table width="100%" class="text14">
	<tr height="15"><td>&nbsp;</td></tr>
	<tr>
		<td>
		<%-- tab container component ø --%>
			<table width="100%" class="text14">
				<tr height="2"><td></td></tr>
				<tr height="25">
					<td>&nbsp;</td>

					<td width="15%" valign="bottom" class="tabDisabled" align="center">
					<a onClick="setBlockUI(this);" href="customer.do"> <font class="tabDisabledLink">&nbsp;Kunde</font>&nbsp;
					</a></td>
					<td width="1px" class="tabFantomSpace" align="center" nowrap><font class="tabDisabledLink">&nbsp;</font></td>

					<td width="15%" valign="bottom" class="tab" align="center"><font class="tabLink">&nbsp;Leverandør</font>&nbsp;</td>

					<td width="1px" class="tabFantomSpace" align="center" nowrap><font class="tabDisabledLink">&nbsp;</font></td>

					<td width="15%" valign="bottom" class="tabDisabled" align="center">
					<a onClick="setBlockUI(this);" href="configuration.do"> <font class="tabDisabledLink">&nbsp;Administrasjon</font>&nbsp;
					</a></td>
	
					<td width="55%" class="tabFantomSpace" align="center" nowrap><font class="tabDisabledLink">&nbsp;</font></td>
				</tr>
				
				
			</table>
		</td>
	</tr>
	
 	<tr> <!-- Second tab row... -->
		<td>
	 		<table width="100%" class="tabThinBorderWhite">
	 	    <tr height="20"><td>&nbsp;</td></tr>
			
 	   	 	<tr> 
 	   	 		<td>&nbsp;</td>
 	   	 	    <td>
 					<table class="formFrameHeaderTransparent" style="width:100%" cellspacing="0" border="0" cellpadding="0">
						<tr height="20"> 
								<td width="10%" valign="bottom" class="tabSub" align="center" nowrap>
									<font class="tabLinkMinor">&nbsp;
										Leverantør - feil
									</font>&nbsp;
								</td>
								<td width="1px" class="tabFantomSpace" align="center" nowrap><font class="tabDisabledLink">&nbsp;</font></td>
								<td width="10%" valign="bottom" class="tabDisabledSub" align="center" nowrap>
									<a href="mainmaintenancecundf_kontaktpersoner_list.do">
										<font class="tabDisabledLinkMinor">&nbsp;
										Faktura - feil
										</font>&nbsp;						
									</a>
								</td>
								<td width="1px" class="tabFantomSpace" align="center" nowrap><font class="tabDisabledLink">&nbsp;</font></td>
								<td width="10%" valign="bottom" class="tabDisabledSub" align="center" nowrap>
									<a href="mainmaintenancecundf_kontaktpersoner_list.do">
										<font class="tabDisabledLinkMinor">&nbsp;
										Leverantør - historikk
										</font>&nbsp;						
									</a>
								</td>	
								<td width="1px" class="tabFantomSpace" align="center" nowrap><font class="tabDisabledLink">&nbsp;</font></td>
								<td width="10%" valign="bottom" class="tabDisabledSub" align="center" nowrap>
									<a href="mainmaintenancecundf_kontaktpersoner_list.do">
										<font class="tabDisabledLinkMinor">&nbsp;
										Faktura - historikk
										</font>&nbsp;						
									</a>
								</td>	
	
							 	<td width="60%" class="tabFantomSpace" align="center" nowrap></td>

						</tr>
					</table>
				</td>
 	   	 	</tr>
 
		 	<tr>
				<td>&nbsp;</td>
				<td width="100%">
			 		<form action="viskunde.do" name="formRecord" id="formRecord" method="GET" >
						<table class="tabThinBorderWhite" width="100%" cellspacing="0" border="0" align="left">
				
					 	    <tr>
					 	    	<td width="5%">&nbsp;</td>
								<td width="95%" >&nbsp;
									
			
									<table border="0">
										<tr>
											<td class="text14" title="kundnr">&nbsp;<font class="text14RedBold" >*</font>
												TODO:
											</td>
											<td><input type="text" required oninvalid="this.setCustomValidity('Obligatoriskt')" onchange="setCustomValidity('')"  class="inputTextMediumBlueMandatoryField" name="vifirm" id="vifirm" size="3" maxlength="2" value='${model.firmvis.vifirm}'></td>
				
										</tr>
			
									</table>
							
								</td>
				
							</tr>
					 	    <tr height="20"><td>&nbsp;</td></tr>
					 	    </table>
			 		</form>
		
				</td>
			</tr>
 
 
 	   	 	</table>
 	   	</td>
 
 	</tr>
</table>	

<!-- ======================= footer ===========================-->
<jsp:include page="/WEB-INF/views/footer.jsp" />
<!-- =====================end footer ==========================-->

