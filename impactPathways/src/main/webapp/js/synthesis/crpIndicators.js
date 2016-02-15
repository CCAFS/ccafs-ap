var $numericFields;

$(document).ready(init);

function init() {

  $numericFields = $('.isNumeric');

  attachEvents();

  initTabs();

}

function attachEvents() {

  $numericFields.on("keydown", isNumber);

}

function initTabs() {
  $("#crpIndicatorsTabs").tabs();
}