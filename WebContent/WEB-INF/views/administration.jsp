<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include.jsp" %>

<!-- ======================= header ===========================-->
<jsp:include page="/WEB-INF/views/headerVisma.jsp" />
<!-- =====================end header ==========================-->

<!-- 
<style type="text/css">
	.container-fluid {
		  max-width:1000px;
		  margin:auto;
	  }
</style>
 -->
<!--  
<table width="100%" class="text14">
	<tr height="15"><td></td></tr>
	<tr height="25">
		<td>&nbsp;</td>
		<td width="15%" valign="bottom" class="tabDisabled" align="center">
		<a onClick="setBlockUI(this);" href="customer.do"> <font class="tabDisabledLink">&nbsp;Kunde</font>&nbsp;
		</a></td>

		<td width="1px" class="tabFantomSpace" align="center" nowrap><font class="tabDisabledLink">&nbsp;</font></td>

		<td width="15%" valign="bottom" class="tabDisabled" align="center">
		<a onClick="setBlockUI(this);" href="supplier.do"> <font class="tabDisabledLink">&nbsp;Leverandør</font>&nbsp;
		</a></td>

		<td width="1px" class="tabFantomSpace" align="center" nowrap><font class="tabDisabledLink">&nbsp;</font></td>

		<td width="15%" valign="bottom" class="tab" align="center"><font class="tabLink">&nbsp;Administrasjon</font>&nbsp;
		</td>

		<td width="55%" class="tabFantomSpace" align="center" nowrap><font class="tabDisabledLink">&nbsp;</font></td>
	</tr>
</table>
-->

<div class="container-fluid text14">

	<div class="padded-row-small">&nbsp;</div>	

	<nav>
	  <div class="nav nav-tabs" id="nav-tab" role="tablist">
	    <a class="nav-item nav-link" onClick="setBlockUI(this);" href="customer.do" >Kunde</a>
		<a class="nav-item nav-link" onClick="setBlockUI(this);" href="supplier.do">Leverandør</a>
	    <a class="nav-item nav-link active" href="administration.do" role="tab">Administrasjon</a>
	  </div>
	</nav>

	<div class="padded-row">&nbsp;</div>		

	<div class="row">   <!-- dashboardFrameMainE2 -->
		<div class="col-3" align="right">
			Overfør Kunderegister
		</div>
		<div class="col-3">
			<button class="dashboardElementsFrameE2" style="width: 100px; height: 50px" onclick="syncCustomer('${user.user}')">Overfør</button>
		</div>		
		<div class="col-6">
			<img class="dashboardElementsImgCircleE2" src="resources/images/leaf.png" height="30px" width="30px" border="0" alt="test module">
			Skön bild....
		</div>				
	</div>

	<div class="padded-row-small">&nbsp;</div>		
	
	<div class="row">  
		<div class="col-3" align="right">
			Overfør Kundefaktura
		</div>
		<div class="col-3">
			<button class="dashboardElementsFrameE2" style="width: 100px; height: 50px" onclick="syncCustomerInvoice('${user.user}')">Overfør</button>
		</div>
		<div class="col-6">
			<img class="dashboardElementsImgCircleE2" src="resources/images/leaf.png" height="30px" width="30px" border="0" alt="test module">
			Skön bild....
		</div>			
	
	</div>		

	<div class="padded-row-small">&nbsp;</div>		
	
	 <div class="row">  
		<div class="col-3" align="right">
			Overfør SubAccount
		</div>
		<div class="col-3">
			<button class="dashboardElementsFrameE2" style="width: 100px; height: 50px" onclick="syncSubaccount('${user.user}')">Overfør</button>
		</div>			 
		<div class="col-6">
			<img class="dashboardElementsImgCircleE2" src="resources/images/leaf.png" height="30px" width="30px" border="0" alt="test module">
			Skön bild....
		</div>			 
	 </div> 

	<div class="padded-row-small">&nbsp;</div>		
	
	 <div class="row">  
		<div class="col-3" align="right">
			Generere sikkerhetsnøkler fra Visma.net
		</div>
		<div class="col-3">
			<button name="generateButton" id="generateButton" class="buttonGrayWithGreenFrame" type="button" style="width: 100px; height: 50px">Generere</button>
		</div>			 
		<div class="col-6">
			<img class="dashboardElementsImgCircleE2" src="resources/images/leaf.png" height="30px" width="30px" border="0" alt="test module">
			Skön bild....
		</div>			 
	 </div> 	
	  

</div> <!-- container-fluid --> 

<!-- ======================= footer ===========================-->
<jsp:include page="/WEB-INF/views/footer.jsp" />
<!-- =====================end footer ==========================-->