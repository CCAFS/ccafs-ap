// Global VARS
var $allBudgetInputs, $overallInputs, $CCAFSBudgetInputs;

$(document).ready(init);

function init() {
  // Setting vars
  $percentageInputs = $("input.percentage");

  // Attach events
  attachEvents();

  // Active initial currency format to all inputs
  $percentageInputs.attr("autocomplete", "off");
  $percentageInputs.trigger("focusout");

  // Validate justification and information
  validateEvent('[name=save], [name=next]', [
    "#justification"
  ]);

  // Regenerating hash from form information
  setFormHash();
}

function attachEvents() {

  $percentageInputs.on("keydown", isNumber).on("focusout", setPercentage).on("focus", removePercentage).on("click",
      function() {
        $(this).select();
      });

  $("form").submit(function(event) {
    $percentageInputs.each(function() {
      $(this).attr("readonly", true);
      $(this).val(removeCurrencyFormat($(this).val()));
    });
    return;
  });

}

function setPercentage(event) {
  var $input = $(event.target);
  var value = $input.val();
  if(value < 0) {
    $input.val(0);
  }
  if(value > 100) {
    $input.val(100);
  }
  if($input.val().length == 0) {
    $input.val(0);
  }
  $input.val(setPercentageFormat($input.val()));
}

function removePercentage(event) {
  $input = $(event.target);
  $input.val(removePercentageFormat($input.val()));
}
