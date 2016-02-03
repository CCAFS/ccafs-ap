$.fn.dataTableExt.sErrMode = 'throw';
$(document).ready(function() {

  $('#newProject').on('click', function(e) {
    console.log(e.target);
    $('#decisionTree .addProjectButtons').show(0, function() {
      $(this).addClass('animated flipInX');
    });
  });

  $('.loadingBlock').hide().next().fadeIn(500);
  initTabs();
  // initSlidr();
  initDatatable();

});

function workflowModal() {
  $("#showPandRWorkflowDialog").dialog({
      modal: true,
      width: 700,
      height: 770,
      buttons: {
        Ok: function() {
          $(this).dialog("close");
        }
      }
  });
  return false;
}

var graphStarted = false;
function initTabs() {
  $("#dashboard-tabs").tabs({
    activate: function(event,ui) {
      if(ui.newTab.index() == 1) {
        if(!graphStarted) {
          callCytos(baseURL + "/json/prePlanningIpGraph.do", "ipGraph-content");
          graphStarted = true;
        }
      }
    }
  });
}

function initDatatable() {
  $('#projects-table').dataTable({
      "aLengthMenu": [
          [
              5, 10
          ], [
              5, 10
          ]
      ],
      "iDisplayLength": 5
  });
  $("#activities-table").dataTable({
      "aLengthMenu": [
          [
              5, 10
          ], [
              5, 10
          ]
      ],
      "iDisplayLength": 5
  });

  $("#deadlineDates table").dataTable();
}

function initSlidr() {
  slidr.create('slider', {
      breadcrumbs: true,
      keyboard: true,
      overflow: true,
      pause: false,
      theme: '#444',
      touch: true
  }).start();
}
