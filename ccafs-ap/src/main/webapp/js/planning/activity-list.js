$(document).ready(function() {
  
  $("#activityTables").tabs( );
  
  $( "#previousActivities" )
    .tabs( )
    .addClass('ui-tabs-vertical ui-helper-clearfix');

  $( "#futureActivities" )
    .tabs(  )
    .addClass('ui-tabs-vertical ui-helper-clearfix');
  
  $('table.activityList').dataTable({
    "bPaginate" : true, // This option enable the table pagination
    "bLengthChange" : false, // This option disables the select table size option
    "bFilter" : true, // This option enable the search
    "bSort" : true, // this option enable the sort of contents by columns
    "bAutoWidth" : true, // This option enables the auto adjust columns width
    "iDisplayLength" : 15 // Number of rows to show on the table
  });
});
