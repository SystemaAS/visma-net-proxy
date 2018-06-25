<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include.jsp" %>

<!-- ======================= header ===========================-->
<jsp:include page="/WEB-INF/views/headerVisma.jsp" />
<!-- =====================end header ==========================-->

<div class="container-fluid">

	<div class="padded-row-small"></div>	

	<nav>
	  <div class="nav nav-tabs" id="nav-tab" role="tablist">
	    <a class="nav-item nav-link" onClick="setBlockUI(this);" href="customer.do">Kunde</a>
		<a class="nav-item nav-link" onClick="setBlockUI(this);" href="supplier.do">Leverandør</a>
	    <a class="nav-item nav-link active" href="administration.do" role="tab">Administrasjon</a>
	  </div>
	</nav>

	<div class="padded-row"></div>		

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
	
	<div class="row">   <!-- dashboardFrameMainE2 -->
		<div class="col-3" align="right">
			Overfør Leverandørregister
		</div>
		<div class="col-3">
			<button class="dashboardElementsFrameE2" style="width: 100px; height: 50px" onclick="syncSupplier('${user.user}')">Overfør</button>
		</div>		
		<div class="col-6">
			<img class="dashboardElementsImgCircleE2" src="resources/images/leaf.png" height="30px" width="30px" border="0" alt="test module">
			Skön bild....
		</div>				
	</div>

	<div class="padded-row-small">&nbsp;</div>		
	
	<div class="row">  
		<div class="col-3" align="right">
			Overfør Leverandørfaktura
		</div>
		<div class="col-3">
			<button class="dashboardElementsFrameE2" style="width: 100px; height: 50px" onclick="syncSupplierInvoice('${user.user}')">Overfør</button>
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