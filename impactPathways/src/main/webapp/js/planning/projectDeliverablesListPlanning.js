$(document).ready(init);

function init() {
  addDataTable();
}

function addDataTable() {
  $('table#projectDeliverables').dataTable({
      "bPaginate": true, // This option enable the table pagination
      "bLengthChange": true, // This option disables the select table size option
      "bFilter": true, // This option enable the search
      "bSort": true, // this option enable the sort of contents by columns
      "bAutoWidth": false, // This option enables the auto adjust columns width
      "iDisplayLength": 10
  // Number of rows to show on the table
  });
}
