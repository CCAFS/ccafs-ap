// Global VARS
var $allBudgetInputs, $CCAFSBudgetInputs;
var editable = true;

$(document).ready(init);

function init() {
  // Setting vars
  $allBudgetInputs = $("input.projectBudget");

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
  // Leveraged Events
  $("select.leveraged").change(addLeveragedEvent);
  $("#leveraged .leveragedPartner .removeButton").click(removeLeveragedEvent);

  $allBudgetInputs.on("keyup", function(e) {
    calculateTotalBudgetByPartner();
    calculateCCAFSBudget(e);
    calculateOverallBudget(e);
    calculateLeveragedBudget(e);
    calculateW1W2Budget(e);
  });
  $allBudgetInputs.on("keydown", function(event) {
    isNumber(event);
  });
  $allBudgetInputs.on("focusout", setCurrency);
  $allBudgetInputs.on("focus", removeCurrency);

  $("form").submit(function(event) {
    $("input[name$='amount']").each(function() {
      $(this).attr("readonly", true);
      $(this).val(removeCurrencyFormat($(this).val()));
    });
    return;
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

// Leveraged Functions //
function addLeveragedEvent(e) {
  e.preventDefault();
  $("#selectLeveraged").hide();
  var $parent = $(e.target).parent();
  var $newElement =
      $("#leveragedPartnerTemplate").clone(true).removeAttr("id").addClass("leveragedPartner")
          .addClass("budgetContent");
  var $optionSelected = $(e.target).find('option:selected');
  if($parent.find("select.leveraged option").length != 0) {
    $parent.before($newElement);
    $newElement.find("[id$='partnerName']").html($optionSelected.html());
    $newElement.find("input[name$='institution.id']").attr("value", $optionSelected.val());
    $newElement.find(".removeButton").click(removeLeveragedEvent);
    $optionSelected.remove();
    $(e.target).trigger("liszt:updated");
    $newElement.show("slow");
    setAmountIndexes();
  }
}

function removeLeveragedEvent(e) {
  e.preventDefault();
  var $target = $(e.target).parent();
  var $parent = $target.parent();
  var option =
      '<option value="' + $parent.find("input[id$='id']").attr("value") + '">'
          + $parent.find("[id$='partnerName']").html() + '</option>';
  $parent.parent().find("select.leveraged").append(option);
  $parent.hide("slide", function() {
    $parent.remove();
    setAmountIndexes();
  });
}

function setAmountIndexes() {
  var className = "budgetContent";
  $("." + className).each(function(index,element) {
    var elementName = "project.budgets[" + index + "]";
    $(element).attr("id", className + "-" + index);
    // CSS selector div[id$=parent] Get any DIV element where the ID attribute value ends with "parent".
    $(element).find("[name$='].id']").attr("name", elementName + ".id");
    $(element).find("[name$='year']").attr("name", elementName + ".year");
    $(element).find("[name$='institution.id']").attr("name", elementName + ".institution.id");
    $(element).find("[name$='amount']").attr("name", elementName + ".amount");
    $(element).find("[name$='type']").attr("name", elementName + ".type");
  });
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
function calculateOverallBudget(e) {
  var Amount = totalBudget($("form input[name$='amount']"));
  var totalAmount = yearTotalBudget + Amount;
  $("span#projectTotalBudgetByYear").text(setCurrencyFormat(Amount));
  $("span#projectTotalBudget").text(setCurrencyFormat(totalAmount));
}

function calculateLeveragedBudget(e) {
  var Amount = totalBudget($("form .LEVERAGED input[name$='amount']"));
  var totalAmount = yearTotalLeveragedBudget + Amount;
  $("span#projectTotalLeveragedBudgetByYear").text(setCurrencyFormat(Amount));
  $("span#projectTotalLeveragedBudget").text(setCurrencyFormat(totalAmount));
}

function calculateCCAFSBudget(e) {
  var Amount = totalBudget($("form .W1_W2 input[name$='amount'],form .W3_BILATERAL input[name$='amount']"));
  var totalAmount = yearTotalCCAFSBudget + Amount;
  $("span#projectTotalCCAFSBudgetByYear").text(setCurrencyFormat(Amount));
  $("span#projectTotalCCAFSBudget").text(setCurrencyFormat(totalAmount));
}

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
  $input = $(event.target);
  if($input.val().length == 0) {
    $input.val("0");
  }
  $input.val(setCurrencyFormat($input.val()));
}

function removeCurrency(event) {
  $input = $(event.target);
  $input.val(removeCurrencyFormat($input.val()));
  if($input.val() == "0") {
    $input.val("");
  }
}
