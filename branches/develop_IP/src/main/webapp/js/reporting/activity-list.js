$(document).ready(function() {
  $('#activityList').dataTable({
    "bPaginate" : true, // This option enable the table pagination
    "bLengthChange" : false, // This option disables the select table size option
    "bFilter" : true, // This option enable the search
    "bSort" : true, // this option enable the sort of contents by columns
    "bAutoWidth" : true, // This option enables the auto adjust columns width
    "iDisplayLength" : 20 // Number of rows to show on the table
  });
});
