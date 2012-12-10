$(document).ready(function() {
  // contacts dialog
  $("#viewMoreContacts").click(function(event) {
    event.preventDefault();
    $("#contactPersons").dialog();
  });

  // Call the needed function to open a link in a popup
  popups();

  $(".genderIntegrationOption").on("change", function() {
    if ($(".genderIntegration:checked").val() == 0) {      
      $("#genderIntegrationDescription").hide("slow");
    } else {      
      $("#genderIntegrationDescription").show("slow");
    }
  });
});
