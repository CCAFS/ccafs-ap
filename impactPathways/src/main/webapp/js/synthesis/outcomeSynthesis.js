$(document).ready(init);

function init() {
  // Attaching events
  attachEvents();
  // Set word limits to inputs that contains class limitWords-value, for example : <input class="limitWords-100" />
  setWordCounterToInputs('limitWords');
}

function attachEvents() {
  // Validating numeric value
  $('.isNumeric').on("keydown", isNumber);
}
