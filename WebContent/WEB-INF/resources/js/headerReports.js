//this variable is a global jQuery var instead of using "$" all the time. Very handy
var jq = jQuery.noConflict();
var BLOCKUI_OVERLAY_MESSAGE_DEFAULT = "Vennligst vent...";

function setBlockUI(element){
  jq.blockUI({ message: BLOCKUI_OVERLAY_MESSAGE_DEFAULT});
}
  

jq(function() {
	jq('a#kundenrLink').click(function() {
		jq('#kundenrLink').attr('target','_blank');
		window.open('childwindow_codes.do?caller=selectKundenr', "codeWin", "top=300px,left=500px,height=600px,width=800px,scrollbars=no,status=no,location=no");
	});  

  	jq('#generateButton').click(function() {
  		window.open('loginVisma.do', 'Genere sikkerhetsnokkle','top=120px,left=100px,height=600px,width=900px,scrollbars=no,status=no,location=no');
  	});

});


function syncCustomer(user) {
	var syncCustomerUrl = "syncronizeCustomers.do?user="+user;
	
	jq.blockUI({
		message : BLOCKUI_OVERLAY_MESSAGE_DEFAULT
	});
	
	jq.ajax({
		url : syncCustomerUrl,
		method : "POST", //to avoid invalidate session
	}).done(function() {
		alert("Synkronisering er ferdig.");
		var formatTime = d3.timeFormat("%Y%m%d");
		jq('#selectFradato').val(formatTime(new Date) - 1);
		//load_data();
	}).fail(function(data) {
		console.log("Error", data);
		alert("Synkronisering gikk feil.", data);
	}).always(function() {
		jq.unblockUI();
	});

}

function syncCustomerInvoice(user) {
	var syncCustomerInvoiceUrl = "syncronizeCustomerInvoices.do?user="+user;

	jq.blockUI({
		message : BLOCKUI_OVERLAY_MESSAGE_DEFAULT
	});
	
	jq.ajax({
		url : syncCustomerInvoiceUrl,
		method : "POST", //to avoid invalidate session
	}).done(function() {
		alert("Synkronisering er ferdig.");
		var formatTime = d3.timeFormat("%Y%m%d");
		jq('#selectFradato').val(formatTime(new Date) - 1);
		//load_data();
	}).fail(function(data) {
		console.log("Error", data);
		alert("Synkronisering gikk feil.", data);
	}).always(function() {
		jq.unblockUI();
	});

}

function syncSubaccount(user) {
	var syncSubaccountUrl = "syncronizeSubaccounts.do?user="+user;

	jq.blockUI({
		message : BLOCKUI_OVERLAY_MESSAGE_DEFAULT
	});
	
	jq.ajax({
		url : syncSubaccountUrl,
		method : "POST", //to avoid invalidate session
	}).done(function() {
		alert("Synkronisering er ferdig.");
		var formatTime = d3.timeFormat("%Y%m%d");
		jq('#selectFradato').val(formatTime(new Date) - 1);
		//load_data();
	}).fail(function(data) {
		console.log("Error", data);
		alert("Synkronisering gikk feil.", data);
	}).always(function() {
		jq.unblockUI();
	});

}

