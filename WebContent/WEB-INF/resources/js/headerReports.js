//this variable is a global jQuery var instead of using "$" all the time. Very handy
var jq = jQuery.noConflict();
var BLOCKUI_OVERLAY_MESSAGE_DEFAULT = "Vennligst vent...";

var callbackWindow;

function setBlockUI(element){
  jq.blockUI({ message: BLOCKUI_OVERLAY_MESSAGE_DEFAULT});
}
  
function getRunningUrl(runningUrl, fraDato) {
	let today = new Date();
	let dato = new Date(fraDato);
	
	if (dato > today) {
		console.log("dato > today");
		alert('Du har valgt ett dato i framtiden:-)');
		return "-1";
	}
	var fraDatoRes = fraDato.replace(/-/g,"");
	return runningUrl + "&fraDato=" + fraDatoRes;			
	
}

jq(function() {
	jq("#selectFradato").datepicker({
		dateFormat : 'yy-mm-dd'
	});
	jq("#selectTildato").datepicker({
		dateFormat : 'yy-mm-dd'
	});
});


jq(function() {
	jq('a#kundenrLink').click(function() {
		jq('#kundenrLink').attr('target','_blank');
		window.open('childwindow_codes.do?caller=selectKundenr', "codeWin", "top=300px,left=500px,height=600px,width=800px,scrollbars=no,status=no,location=no");
	});  

  	jq('#generateButton').click(function() {
  		window.open('loginVisma.do', 'Sikkerhetsnøkler','top=150px,left=600px,height=550px,width=700px');
  	});
  	jq('#closeButton').click(function() {
  		window.close();
  	});  
  	
  	
});


function syncCustomers(user) {
	var syncCustomersUrl = "syncronizeCustomers.do?user="+user;
	
	jq.blockUI({
		message : BLOCKUI_OVERLAY_MESSAGE_DEFAULT
	});
	
	jq.ajax({
		url : syncCustomersUrl,
		method : "POST", //to avoid invalidate session
	}).done(function() {
		alert("Synkronisering er ferdig.");
	}).fail(function(data) {
		console.log("Error", data);
		alert("Synkronisering gikk feil.", data);
	}).always(function() {
		console.log("Always...should unblock");
		jq.unblockUI();
	});

}

function syncCustomerInvoices(user) {
	var syncCustomerInvoiceUrl = "syncronizeCustomerInvoices.do?user="+user;

	jq.blockUI({
		message : BLOCKUI_OVERLAY_MESSAGE_DEFAULT
	});
	
	jq.ajax({
		url : syncCustomerInvoiceUrl,
		method : "POST", //to avoid invalidate session
	}).done(function() {
		alert("Synkronisering er ferdig.");
	}).fail(function(data) {
		console.log("Error", data);
		alert("Synkronisering gikk feil.", data);
	}).always(function() {
		console.log("Always...should unblock");
		jq.unblockUI();
	});

}

function syncSuppliers(user) {
	var syncSuppliersUrl = "syncronizeSuppliers.do?user="+user;

	jq.blockUI({
		message : BLOCKUI_OVERLAY_MESSAGE_DEFAULT
	});
	
	jq.ajax({
		url : syncSuppliersUrl,
		method : "POST", //to avoid invalidate session
	}).done(function() {
		alert("Synkronisering er ferdig.");
	}).fail(function(data) {
		console.log("Error", data);
		alert("Synkronisering gikk feil.", data);
	}).always(function() {
		console.log("Always...should unblock");
		jq.unblockUI();
	});

}

function syncSupplierInvoices(user) {
	var syncSupplierInvoiceUrl = "syncronizeSupplierInvoices.do?user="+user;

	jq.blockUI({
		message : BLOCKUI_OVERLAY_MESSAGE_DEFAULT
	});
	
	jq.ajax({
		url : syncSupplierInvoiceUrl,
		method : "POST", //to avoid invalidate session
	}).done(function() {
		alert("Synkronisering er ferdig.");
	}).fail(function(data) {
		console.log("Error", data);
		alert("Synkronisering gikk feil.", data);
	}).always(function() {
		console.log("Always...should unblock");
		jq.unblockUI();
	});

}

function syncJournalTransactions(user) {
	var syncJournalTransactionsUrl = "syncronizeJournalTransactions.do?user="+user;

	jq.blockUI({
		message : BLOCKUI_OVERLAY_MESSAGE_DEFAULT
	});
	
	jq.ajax({
		url : syncJournalTransactionsUrl,
		method : "POST", //to avoid invalidate session
	}).done(function() {
		alert("Synkronisering er ferdig.");
	}).fail(function(data) {
		console.log("Error", data);
		alert("Synkronisering gikk feil.", data);
	}).always(function() {
		console.log("Always...should unblock");
		jq.unblockUI();
	});

}