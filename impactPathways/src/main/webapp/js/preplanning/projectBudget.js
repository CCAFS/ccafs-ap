// Global VARS
var $allBudgetInputs, $overallInputs, $CCAFSBudgetInputs;
var editable = true;

$(document).ready(init);

function init() {
  // Setting vars
  $allBudgetInputs = $("input.projectBudget");
  $genderBudgetInputs = $('input.projectGenderBudget');
  $overallInputs = $("input[name$=isfullyInstitutionalCost]");
  // This function enables launch the pop up window
  popups();

  // Attach events
  attachEvents();

  // Show table when page is loaded
  $("#budgetTables").fadeIn("slow");

  // Active initial currency format to all inputs
  $allBudgetInputs.attr("autocomplete", "off").trigger("focusout");
  $genderBudgetInputs.attr("autocomplete", "off").trigger("focusout");

  // Validate justification and information
  validateEvent('[name=save], [name=next]', [
    "#justification"
  ]);

  // Regenerating hash from form information
  setFormHash();
}

function attachEvents() {
  $allBudgetInputs.on("keydown", isNumber).on("focusout", setCurrency).on("focus", removeCurrency).on("click",
      function() {
        $(this).select();
      }).on("keyup", function(e) {
  });

  $genderBudgetInputs.on("keydown", isNumber).on("focusout", setPercentage).on("focus", removePercentage).on("click",
      function() {
        $(this).select();
      }).on("keyup", function(e) {
  });

  $("form").submit(function(event) {
    $allBudgetInputs.each(function() {
      $(this).val(removeCurrencyFormat($(this).val())).attr("readonly", true);
    });
    $genderBudgetInputs.each(function() {
      $(this).val(removeCurrencyFormat($(this).val())).attr("readonly", true);
    });
    return;
  });

  $overallInputs.on("change", function(e) {
    var $content = $(e.target).parents('.budget').find('.overhead-block');
    if($(e.target).val() === "1") {
      $content.slideDown('slow');
    } else {
      $content.slideUp('slow');
    }
  });

  $(".handlediv").on("click", function(e) {
    $(e.target).parent().siblings().slideToggle("slow");
    $(e.target).toggleClass("down");
    $(e.target).parent().toggleClass("down");
  });

  // Enable save with tabs when is saveable and exist an target
  if($("#targetYear").exists()) {
    $("li.yearTab").click(function(e) {
      e.preventDefault();
      var yearTarget = $(this).attr("id").split("-")[1];
      $("input[name$='targetYear']").val(yearTarget);
      $("#budget_save").trigger("click");
    });
  }

}

function totalBudget($inputList) {
  var Amount = 0;
  $inputList.each(function(index,amount) {
    if(!$(amount).val().length == 0) {
      Amount += removeCurrencyFormat($(amount).val());
    }
  });
  return Amount;
}

function setCurrency(event) {
  var $input = $(event.target);
  if($input.val().length == 0) {
    $input.val("0");
  }
  $input.val(setCurrencyFormat($input.val()));
}

function removeCurrency(event) {
  var $input = $(event.target);
  $input.val(removeCurrencyFormat($input.val()));
  if($input.val() == "0") {
    $input.val("");
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
  var $input = $(event.target);
  $input.val(removePercentageFormat($input.val()));
}
