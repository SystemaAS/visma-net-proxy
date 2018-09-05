<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include.jsp" %>

<!-- ======================= header ===========================-->
<jsp:include page="/WEB-INF/views/headerVisma.jsp" />
<!-- =====================end header ==========================-->

<div class="container-fluid">

	<div class="padded-row-small"></div>	

	<nav>
	  <div class="nav nav-tabs" id="nav-tab" role="tablist">
	    <a class="nav-item nav-link" onClick="setBlockUI(this);" href="customer.do"><strong>Kunde</strong>
			<c:if test="${not empty customer_all_error}">
			    <span class="badge badge-danger">${customer_all_error}</span>
			</c:if>		    
	    </a>
		<a class="nav-item nav-link" onClick="setBlockUI(this);" href="supplier.do"><strong>Leverandør</strong>
			<c:if test="${not empty supplier_all_error}">
			    <span class="badge badge-danger">${supplier_all_error}</span>
			</c:if>			
		</a>
	    <a class="nav-item nav-link active" href="administration.do" role="tab">Administrasjon</a>
	    <a class="nav-item nav-link" onClick="setBlockUI(this);" href="log.do" role="tab">Log</a>
	  </div>
	</nav>

	<div class="padded-row"></div>		

	<div class="row">   <!-- dashboardFrameMainE2 -->
		<div class="col" align="right">
			Overfør Kunderegister til Visma.net Financials
		</div>
		<div class="col">
			<img src="resources/images/customer.png" height="300px" width="600px">
		</div>				
		<div class="col">
			<button class="dashboardElementsFrameE2" style="width: 100px; height: 50px" onclick="syncCustomers('${user.user}')">Overfør</button>
		</div>		
	</div>

	<div class="padded-row-small">&nbsp;</div>		
	
	<div class="row">  
		<div class="col" align="right">
			Overfør Kundefaktura til Visma.net Financials
		</div>
		<div class="col">
			<img src="resources/images/customer_invoice.png" height="300px" width="600px">
		</div>			
		<div class="col">
			<button class="dashboardElementsFrameE2" style="width: 100px; height: 50px" onclick="syncCustomerInvoices('${user.user}')">Overfør</button>
		</div>
	</div>		

	<div class="padded-row-small">&nbsp;</div>		
	
	<div class="row">   <!-- dashboardFrameMainE2 -->
		<div class="col" align="right">
			Overfør Leverandørregister til Visma.net Financials
		</div>
		<div class="col">
			<img src="resources/images/supplier.png" height="300px" width="600px">
		</div>				

		<div class="col">
			<button class="dashboardElementsFrameE2" style="width: 100px; height: 50px" onclick="syncSuppliers('${user.user}')">Overfør</button>
		</div>		
	</div>

	<div class="padded-row-small">&nbsp;</div>		
	
	<div class="row">  
		<div class="col" align="right">
			Overfør Leverandørfaktura til Visma.net Financials
		</div>
		<div class="col">
			<img  src="resources/images/supplier_invoice.png" height="300px" width="600px">
		</div>	
		<div class="col">
			<button class="dashboardElementsFrameE2" style="width: 100px; height: 50px" onclick="syncSupplierInvoices('${user.user}')">Overfør</button>
		</div>
	</div>		

	<div class="padded-row-small">&nbsp;</div>		
	
	<div class="row">  
		<div class="col" align="right">
			Overfør Hovedbok til Visma.net Financials
		</div>
		<div class="col">
			<img  src="resources/images/journal_transaction.png" height="300px" width="600px">
		</div>	
		<div class="col">
			<button class="dashboardElementsFrameE2" style="width: 100px; height: 50px" onclick="syncJournalTransactions('${user.user}')">Overfør</button>
		</div>
	</div>		

	<div class="padded-row-small">&nbsp;</div>			
	
	
	 <div class="row">  
		<div class="col" align="right">
			Generere sikkerhetsnøkler fra Visma.net
		</div>
		<div class="col">
			<img src="resources/images/visma_allow.png" height="300px" width="400px" >
		</div>			 
		<div class="col">
			<button name="generateButton" id="generateButton" class="buttonGrayWithGreenFrame" type="button" style="width: 100px; height: 50px">Generere</button>
		</div>			 
	 </div> 	
	  

</div> <!-- container-fluid --> 

<!-- ======================= footer ===========================-->
<jsp:include page="/WEB-INF/views/footer.jsp" />
<!-- =====================end footer ==========================-->