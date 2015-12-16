$(document).ready(function() {

  // Activate the select2 plugin to the existing case studies
  addSelect2();
  // Set word limits to inputs that contains class limitWords-value, for example : <input class="limitWords-100" />
  setWordCounterToInputs('limitWords');
  // Validate justification event
  validateEvent([
    "#justification"
  ]);

});

function addSelect2() {
  $('form select').select2();
}
