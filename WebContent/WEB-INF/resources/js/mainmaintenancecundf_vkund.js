  //this variable is a global jQuery var instead of using "$" all the time. Very handy
  var jq = jQuery.noConflict();
  var counterIndex = 0;
  var BLOCKUI_OVERLAY_MESSAGE_DEFAULT = "Please wait...";
  
  function setBlockUI(element){
	  jq.blockUI({ message: BLOCKUI_OVERLAY_MESSAGE_DEFAULT});
  }
  
  //-------------------
  //Datatables jquery
  //-------------------
  //private function
  function filterGlobal () {
    jq('#mainList').dataTable().search(
    	jq('#mainList_filter').val()
    ).draw();
  }
  
  jq(document).ready(function() {
	  var lang = jq('#language').val();
	  jq('#mainList').dataTable( {
    	  "dom": '<"top">t<"bottom"flip><"clear">',
    	  "scrollY": "500px",
    	  "scrollCollapse":  true,
    	  "columnDefs": [{ "type": "num", "targets": 1 }],
    	  "order": [[ 1, "asc" ]],
    	  "lengthMenu": [ 75, 100],
  		  "language": {
			 "url": getLanguage(lang)
          }
  	  });
      
      //event on input field for search
      jq('input.mainList_filter').on( 'keyup click', function () {
      		filterGlobal();
      });
  	
  });
  