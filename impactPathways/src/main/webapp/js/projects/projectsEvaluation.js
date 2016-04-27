$(document).ready(init);

function init() {
  initializeDataTable();
  $('.loadingBlock').hide().next().fadeIn(500);
  setScoresColor();

  attachEvents();
}

function attachEvents() {
  $('table.evaluationProjects').on('draw.dt', function() {
    setScoresColor();
  });

}

function initializeDataTable() {
  $('table.evaluationProjects').dataTable({
      "bPaginate": true, // This option enable the table pagination
      "bLengthChange": true, // This option disables the select table size option
      "bFilter": true, // This option enable the search
      "bSort": true, // this option enable the sort of contents by columns
      "bAutoWidth": false, // This option enables the auto adjust columns width
      "iDisplayLength": 15, // Number of rows to show on the table
      "fnDrawCallback": function() {
        // This function locates the add activity button at left to the filter box
        var table = $(this).parent().find("table");
        if($(table).attr("id") == "currentActivities") {
          $("#currentActivities_filter").prepend($("#addActivity"));
        }
      },
      aoColumnDefs: [
          {
              bSortable: false,
              aTargets: [

              ]
          }, {
              sType: "natural",
              aTargets: [
                0
              ]
          }
      ]
  });

}

/** Set colors to project scores * */

function setScoresColor() {
  $('p.totalScore').each(function(i,element) {
    var score = parseFloat($(element).text());
    $(element).removeClass('green yellow red')
    if(score > 3.5) {
      // 3.6 - 5.0 (Green)
      $(element).addClass('green');
    } else if((score > 2) && (score <= 3.5)) {
      // 2.1 - 3.5 (Yellow)
      $(element).addClass('yellow');
    } else if(score <= 2) {
      // 0,5 - 2.0 (Red)
      $(element).addClass('red');
    }
  });
}
