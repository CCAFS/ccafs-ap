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
  $allBudgetInputs.attr("autocomplete", "off");
  $allBudgetInputs.trigger("focusout");

  // Validate justification and information
  validateEvent('[name=save], [name=next]', [
    "#justification"
  ]);
}

function calculateTotalBudgetByPartner() {
  $(".partnerBudget").each(
      function(index,partnerBudget) {
        var Amount =
            totalBudget($(partnerBudget).find(".W1_W2 input[name$='amount'],.W3_BILATERAL input[name$='amount']"));
        $(partnerBudget).find("span.totalBudgetByPartner").text(setCurrencyFormat(Amount));
      });
}

function attachEvents() {
  $allBudgetInputs.on("keyup", function(e) {
    calculateW1W2Budget(e);
  });
  $allBudgetInputs.on("keydown", function(event) {
    isNumber(event);
  });
  $genderBudgetInputs.on("keydown", function(event) {
    isNumber(event);
  });
  $allBudgetInputs.on("focusout", setCurrency).on("focus", removeCurrency);
  $genderBudgetInputs.on("focusout", setPercentage).on("focus", removePercentage).on("click", function() {
    $(this).select();
  });

  $("form").submit(function(event) {
    $("input[name$='amount']").each(function() {
      $(this).attr("readonly", true);
      $(this).val(removeCurrencyFormat($(this).val()));
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

function verifyBudgetExceeded(e,type) {
  var $parent = $(e.target).parent().parent().parent();
  var budget =
      {
          W1W2: removeCurrencyFormat($parent.find(".W1_W2 input[name$='amount']").val() + ""),
          W3BILATERAL: removeCurrencyFormat($parent.find(".W3_BILATERAL input[name$='amount']").val() + ""),
          LEVERAGED: removeCurrencyFormat($parent.find(".LEVERAGED input[name$='amount']").val() + ""),
          W1_W2_PARTNERS: removeCurrencyFormat($parent.find(".W1_W2_PARTNERS input[name$='amount']").val() + ""),
          W1_W2_OTHER: removeCurrencyFormat($parent.find(".W1_W2_OTHER input[name$='amount']").val() + ""),
          W3_BILATERAL_PARTNERS: removeCurrencyFormat($parent.find(".W3_BILATERAL_PARTNERS input[name$='amount']")
              .val()
              + ""),
          W3_BILATERAL_OTHERS: removeCurrencyFormat($parent.find(".W3_BILATERAL_OTHERS input[name$='amount']").val()
              + ""),
          W1_W2_GENDER: removeCurrencyFormat($parent.find(".W1_W2_GENDER input[name$='amount']").val() + ""),
          W3_BILATERAL_GENDER: removeCurrencyFormat($parent.find(".W3_BILATERAL_GENDER input[name$='amount']").val()
              + "")
      };
  $allBudgetInputs.removeClass("fieldError");
  if(type == "W3BILATERAL") {
    if((budget.W3_BILATERAL_PARTNERS + budget.W3_BILATERAL_OTHERS + budget.W3_BILATERAL_GENDER) > budget.W3BILATERAL) {
      $(e.target).addClass("fieldError");
    } else {
      $(e.target).removeClass("fieldError");
    }
  } else if(type == "W1W2") {
    if((budget.W1_W2_PARTNERS + budget.W1_W2_OTHER + budget.W1_W2_GENDER) > budget.W1W2) {
      $(e.target).addClass("fieldError");
    } else {
      $(e.target).removeClass("fieldError");
    }
  }
}

// Activate the chosen plugin to leveraged institutions
function addChosen() {
  $("form select.leveraged").each(function() {
    $(this).chosen({
      search_contains: true
    });
  });
}

// Calculate budget functions
function calculateW1W2Budget(e) {
  var Amount = totalBudget($("form .W1_W2 input[name$='amount']"));
  var totalAmount = yearTotalW1W2Budget + Amount;
  $("span#projectTotalW1W2BudgetByYear").text(setCurrencyFormat(Amount));
  $("span#projectTotalW1W2Budget").text(setCurrencyFormat(totalAmount));
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
  $input = $(event.target);
  $input.val(removePercentageFormat($input.val()));
  if($input.val() == "0") {
    $input.val("");
  }
}
