//this variable is a global jQuery var instead of using "$" all the time. Very handy
var jq = jQuery.noConflict();
var BLOCKUI_OVERLAY_MESSAGE_DEFAULT = "Vennligst vent...";

var callbackWindow;

function setBlockUI(element){
  jq.blockUI({ message: BLOCKUI_OVERLAY_MESSAGE_DEFAULT});
}
  

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
		//var formatTime = d3.timeFormat("%Y%m%d");
		//jq('#selectFradato').val(formatTime(new Date) - 1);
		//load_data();
	}).fail(function(data) {
		console.log("Error", data);
		alert("Synkronisering gikk feil.", data);
	}).always(function() {
		console.log("Always...should unblock");
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
		//var formatTime = d3.timeFormat("%Y%m%d");
		//jq('#selectFradato').val(formatTime(new Date) - 1);
		//load_data();
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
		//var formatTime = d3.timeFormat("%Y%m%d");
		//jq('#selectFradato').val(formatTime(new Date) - 1);
		//load_data();
	}).fail(function(data) {
		console.log("Error", data);
		alert("Synkronisering gikk feil.", data);
	}).always(function() {
		console.log("Always...should unblock");
		jq.unblockUI();
	});

}

