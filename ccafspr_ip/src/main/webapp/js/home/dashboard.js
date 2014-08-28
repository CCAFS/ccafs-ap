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

function workflowModal(){
  $( "#showPandRWorkflowDialog" ).dialog({
    modal: true,
    width: 700,
    height: 770,
    buttons: {
      Ok: function() {
        $( this ).dialog( "close" );
      }
    }
  });
  return false;
}
