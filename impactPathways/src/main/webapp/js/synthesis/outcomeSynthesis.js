$(document).ready(init);

function init() {
  // Attaching events
  attachEvents();
  // Set word limits to inputs that contains class limitWords-value, for example : <input class="limitWords-100" />
  setWordCounterToInputs('limitWords');
  // Adding DataTable plugin
  $(".projectContributions").dataTable({
      "bPaginate": false, // This option enable the table pagination
      "bLengthChange": false, // This option disables the select table size option
      "bFilter": false, // This option enable the search
      "bSort": true, // this option enable the sort of contents by columns
      "bAutoWidth": false, // This option enables the auto adjust columns width
      "iDisplayLength": 50
  });
}

function attachEvents() {
  // Validating numeric value
  $('.isNumeric').on("keydown", isNumber);
}
