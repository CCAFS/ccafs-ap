$(document).ready(function() {

  // Activate the chosen plugin to the existing case studies
  addChosen();
  hideCountries();

});

// Activate the chosen plugin to the countries inputs
function addChosen() {
  $("select[name$='countries']").each(function() {
    // Check if its not the template countries field
    if($(this).attr("name") != 'countries') {
      $(this).chosen();
    }
  });
}
