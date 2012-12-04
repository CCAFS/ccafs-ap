$(document).ready(function() {
  $("#viewMoreContacts").click(function(event) {
    event.preventDefault();
    $("#contactPersons").dialog();
  });

});
