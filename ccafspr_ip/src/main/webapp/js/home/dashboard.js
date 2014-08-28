$.fn.dataTableExt.sErrMode = 'throw';
$(document).ready(function(){
  $("#dashboard").tabs();
  $('#projects-table').dataTable({
    "iDisplayLength" : 5
  });
  $("#activities-table").dataTable({
    "iDisplayLength" : 5
  });
  
  $("#deadlineDates table").dataTable();
  
  slidr.create('slider', {
    breadcrumbs : true,
    keyboard : true,
    overflow : true,
    pause : false,
    theme : '#444',
    touch : true
  }).start();
  
});

function workflowModal(){
  $("#showPandRWorkflowDialog").dialog({
    modal : true,
    width : 700,
    height : 770,
    buttons : {
      Ok : function(){
        $(this).dialog("close");
      }
    }
  });
  return false;
}
