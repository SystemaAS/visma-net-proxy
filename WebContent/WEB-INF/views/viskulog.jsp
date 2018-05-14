<%@ include file="/WEB-INF/views/include.jsp" %>
<!-- ======================= header ===========================-->
<jsp:include page="/WEB-INF/views/header.jsp" />
<!-- =====================end header ==========================-->
<style>
.ui-datepicker { font-size:9pt;}
</style>

<script type="text/javascript">
"use strict";
jq(function() {
	jq("#selectFradato").datepicker({ 
		  dateFormat: 'yymmdd'  
	});
	jq("#selectTildato").datepicker({ 
		  dateFormat: 'yymmdd'  
	});
});
var jq = jQuery.noConflict();
var BLOCKUI_OVERLAY_MESSAGE_DEFAULT = "Vennligst vent...";
var baseUrl = "/visma-net-proxy/viskulog?user=${user.user}";

var baseImportUrl = "/espedsgtvinnsad/logonAnalyseReports_toSad.do?user=${user.user}&dp=${user.encryptedPassword}"; 
var baseExportUrl = "/espedsgtvinnsad/logonAnalyseReports_toSad.do?sade=Y&user=${user.user}&dp=${user.encryptedPassword}";

function load_data() {

	var runningUrl = baseUrl;

	var selectedKundenr = jq('#selectKundenr').val();
	var selectedFradato = jq('#selectFradato').val();
	var selectedTildato = jq('#selectTildato').val();

	if (selectedKundenr != "" )	{
		runningUrl = runningUrl + "&kundnr="+selectedKundenr;
	} else {
		runningUrl = runningUrl + "&kundnr=ALL";
	}
	if (selectedFradato != null && selectedFradato != "") {
		runningUrl = runningUrl + "&fraDato="+selectedFradato;		
	} else {
		runningUrl = runningUrl + "&fraDato=ALL";		
	}
	if (selectedTildato != null && selectedTildato != "") {
		runningUrl = runningUrl + "&tilDato="+selectedTildato;
	} else {
		runningUrl = runningUrl + "&tilDato=ALL";
		
	}

	console.log("runningUrl="+runningUrl); 		
	
    jq.blockUI({message : BLOCKUI_OVERLAY_MESSAGE_DEFAULT});

    var viskulogTable = jq('#viskulogTable').DataTable({
    	"dom": '<"top">t<"bottom"flip><"clear">',
    	responsive: true,
    	select: true,
    	destroy : true,
    	"sAjaxSource": runningUrl,
    	"sAjaxDataProp": "",
		"order": [[ 1, "desc" ]],
		"aoColumns": [
			  { "mData": "kundnr"},
			  { "mData": "knavn"},
		      { "mData": "status"},
			  { "mData": "syncda" },
			  { "mData": "synctm" },
	          { "mData": "syerro" }
		],
		"lengthMenu" : [ 75, 100 ],
		"language": {
			"url": getLanguage('NO')
        }
    
    });
    
	jq.unblockUI();

}  
 
jq(document).ready(function() {
	
});	

window.addEventListener('error', function (e) {
	  var error = e.error;
	  jq.unblockUI();
	  console.log("Event e",e);

	  if (e instanceof TypeError) {
			//what todo  
	  } else {
		  alert('Uforutsett fel har intreffet.');
	  }
	  
});
</script>

<table id="fullTable" width="100%"  cellspacing="0" border="0" cellpadding="0">
	<tr>
		<td>
		<%-- tab container component --%>
		<table style="border-collapse:initial;" width="100%"  cellspacing="0" border="0" cellpadding="0">
			<tr height="2"><td></td></tr>
				<tr height="25"> 
					<td width="20%" valign="bottom" class="tab" align="center" nowrap>
						<font class="tabLink">&nbsp;VISKULOG</font>
						<img  style="vertical-align:middle;" src="resources/images/list.gif" border="0" alt="general list">
					</td>

					<td width="80%" class="tabFantomSpace" align="center" nowrap><font class="tabDisabledLink">&nbsp;</font></td>	
	
				</tr>
		</table>
		</td>
	</tr>

	<tr>
		<td>
	 	 <table width="100%" class="tabThinBorderWhite" border="0" cellspacing="0" cellpadding="0">
	 	    <tr height="20">
		 	    <td>&nbsp;
				<div class="container-fluid">
				  <div class="row">
					
					<div class="col-md-1 text12 text-nowrap">
  		    			<font class="text14">Kunde:</font><br>
						<input type="text" class="inputText" name="selectKundenr" id="selectKundenr" size="9" maxlength="8" >  	
						<a tabindex="-1" id="kundenrLink">
							<img style="cursor:pointer;vertical-align: middle;" src="resources/images/find.png" width="14px" height="14px" border="0">
						</a>&nbsp;	
					</div> 	
	
					<div class="col-md-1 text12">
						<font class="text14">Fra dato:</font><br>
						<input type="text" class="inputText" name="selectFradato" id="selectFradato" size="9" maxlength="8">
	  				</div>	 	
	
					<div class="col-md-1 text12">
						<font class="text14">Til dato:</font><br>
						<input type="text" class="inputText" name="selectTildato" id="selectTildato" size="9" maxlength="8">
	  				</div>	


	  		    	<div class="col-md-9" align="right">
	  		    		<br>
	   	              	<button class="inputFormSubmit" onclick="load_data()" autofocus>Hent data</button> 
					</div>	
	
				  </div>
  
	  			  <div class="padded-row-small">&nbsp;</div>
	  			  <div class="padded-row-small">&nbsp;</div>

                   <div class="panel-body">
                      <table width="100%" class="table table-striped table-bordered table-hover" id="viskulogTable">
						  <thead class="tableHeaderField">
				            <tr>
					            <th>Kundnr</th>
					            <th>Navn</th>
					            <th>Status</th>
					            <th>Dato</th>
					            <th>Tid</th>
					            <th>Feil</th>
				            </tr>
					      </thead>
						  <tfoot class="tableHeaderField">
				            <tr>
					            <th>Kundnr</th>
					            <th>Navn</th>
					            <th>Status</th>
					            <th>Dato</th>
					            <th>Tid</th>
					            <th>Feil</th>
				            </tr>
					      </tfoot>	
                      </table>
                    </div>

         		</div> <!-- container -->
		 	    </td>
	 	    </tr>
	 	 </table>
		</td>
	</tr>
</table>	
<!-- ======================= footer ===========================-->
<jsp:include page="/WEB-INF/views/footer.jsp" />
<!-- =====================end footer ==========================-->
