$.fn.dataTableExt.sErrMode = 'throw';
$(document).ready(function(){
  $("#dashboard").tabs();
  $('#projects-table').dataTable({
    "iDisplayLength" : 5
  });
  
  $("#activities-table").dataTable({
    "iDisplayLength" : 5
  });
});
