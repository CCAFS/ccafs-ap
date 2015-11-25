$(document).ready(function() {

  // Activate the select2 plugin to the existing case studies
  addSelect2();

});

// Activate the select2 plugin to the countries inputs
function addSelect2() {
  $('form select').select2();
}
