// Global VARS
var $allBudgetInputs, $overallInputs, $CCAFSBudgetInputs;
var budgetByYear, genderBudgetByYear;
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
  $genderBudgetInputs.trigger("keyup");
  $budgetInputs.trigger("keyup");

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
    setPercentageCurrency($(this), budgetByYear);
    checkPercentages($(this), $budgetInputs, budgetByYear);

  });

  $genderBudgetInputs.on("keyup", function(e) {
    setPercentageCurrency($(this), genderBudgetByYear);
    checkPercentages($(e.target), $genderBudgetInputs, genderBudgetByYear);
  });

  $("form").submit(function(event) {
    $percentageInputs.each(function() {
      $(this).attr("readonly", true);
      $(this).val(removePercentageFormat($(this).val()));
    });
    return;
  });
}

function BudgetRemaining(budget) {
  this.initValue = $(budget).find('input').val();
  this.setValue = function(value) {
    $(budget).find('span').text(setCurrencyFormat(value));
  };
  this.calculateRemain = function(percentage) {
    var result = (this.initValue / 100) * percentage;
    this.setValue(this.initValue - result);
  };
}

function setPercentageCurrency(inputTarget,remainBudget) {
  var percentage = removePercentageFormat($(inputTarget).val());
  if(percentage > 100) {
    percentage = 100;
  }
  var value = (remainBudget.initValue / 100) * percentage;
  $(inputTarget).parents('.budget').find('span').text(setCurrencyFormat(value));
}

function checkPercentages(inputTarget,inputList,remainBudget) {
  var totalPercentage = 0;
  $(inputList).removeClass('fieldError');
  $(inputList).each(function(i,input) {
    totalPercentage += parseFloat(removePercentageFormat($(input).val() || 0));
  });
  if(totalPercentage > 100) {
    $(inputTarget).addClass('fieldError');
  } else {
    remainBudget.calculateRemain(totalPercentage);
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
