$.fn.dataTableExt.sErrMode = 'throw';
$(document).ready(function(){
  
  initTabs();
  //initSlidr();
  initDatatable();
  
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

var graphStarted = false;
function initTabs(){
  $("#dashboard").tabs({
    activate : function(event, ui){
      
      if(ui.newTab.index() == 2){
        if (!graphStarted) {
          callCytos("json/ipComponents.do", "ipGraph-content");
          graphStarted = true;
        }
      }
    }
  });
}

function initDatatable(){
  $('#projects-table').dataTable({
    "iDisplayLength" : 5
  });
  $("#activities-table").dataTable({
    "iDisplayLength" : 5
  });
  
  $("#deadlineDates table").dataTable();
}

function initSlidr(){
  slidr.create('slider', {
    breadcrumbs : true,
    keyboard : true,
    overflow : true,
    pause : false,
    theme : '#444',
    touch : true
  }).start();
}
