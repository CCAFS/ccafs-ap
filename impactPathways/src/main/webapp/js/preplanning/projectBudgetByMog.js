// Global VARS
var $allBudgetInputs, $overallInputs, $CCAFSBudgetInputs;

$(document).ready(init);

function init() {
  // Setting vars
  $percentageInputs = $("input.percentage");
  $budgetInputs = $("input.budgetInput");
  $genderBudgetInputs = $("input.genderBudgetInput");

  budgetByYear = new BudgetRemaining('#budgetByYear');
  genderBudgetByYear = new BudgetRemaining('#genderBudgetByYear');

  // Attach events
  attachEvents();

  // Active initial currency format to all inputs
  $percentageInputs.attr("autocomplete", "off").trigger("focusout");

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

  $budgetInputs.on("keyup", function(e) {
    checkPercentages($(e.target), $budgetInputs, budgetByYear);
  });

  $genderBudgetInputs.on("keyup", function(e) {
    checkPercentages($(e.target), $genderBudgetInputs, genderBudgetByYear);
  });

  $("form").submit(function(event) {
    $percentageInputs.each(function() {
      $(this).attr("readonly", true);
      $(this).val(removeCurrencyFormat($(this).val()));
    });
    return;
  });
}

function BudgetRemaining(budget) {
  this.getValue = $(budget).find('input').val();
  this.setValue = function(value) {
    $(budget).find('span').text(setCurrencyFormat(value));
    $(budget).find('input').val(value);
  };
  this.calculateRemain = function(percentage) {
    var result = (this.getValue / 100) * percentage;
    this.setValue(this.getValue - result);
  };
}

function checkPercentages(inputTarget,inputList,remainBudget) {
  var result = 0;
  $(inputList).removeClass('fieldError');
  $(inputList).each(function(i,input) {
    result += parseFloat(removePercentageFormat($(input).val() || 0));
  });
  if(result > 100) {
    $(inputTarget).addClass('fieldError');
  } else {
    remainBudget.calculateRemain(result);
  }
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
