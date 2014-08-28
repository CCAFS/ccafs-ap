$.fn.dataTableExt.sErrMode = 'throw';
$(document).ready(function(){
  
  initTabs();
  initSlidr();
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

function initTabs(){
  $("#dashboard").tabs({
    select : function(event,ui){
      var tabNumber = ui.index;
      var tabName = $(ui.tab).text();
      
      console.log('Tab number ' + tabNumber + ' - ' + tabName + ' - clicked');
      
      console.log("Loading map...");
      initGraph();
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
