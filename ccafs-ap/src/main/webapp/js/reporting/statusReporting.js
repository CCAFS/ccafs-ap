$(document).ready(function() {
  // Add the full contacts dialog
  $("#viewMoreContacts").click(function(event) {
    event.preventDefault();
    $("#contactPersons").dialog();
  });

  // Call the needed function to open a link in a popup
  popups();
});
