var $numericFields;

$(document).ready(init);

function init() {

  $numericFields = $('.isNumeric');

  attachEvents();

}

function attachEvents() {

  $numericFields.on("keydown", isNumber);

}