//Global VARS
var projectTotalCCAFSBudget,projectTotalBudget,yearTotalCCAFSBudget,yearTotalBudget;

$(document).ready(init);

function init(){
  // This function enables launch the pop up window
  popups();
  projectTotalCCAFSBudget = parseFloat($("input#projectTotalCCAFSBudget").val());
  yearTotalCCAFSBudget = projectTotalCCAFSBudget - parseFloat($("input#yearTotalCCAFSBudget").val());
  projectTotalBudget = parseFloat($("input#projectTotalBudget").val());
  yearTotalBudget = projectTotalBudget - parseFloat($("input#yearTotalBudget").val());
  addChosen();
  attachEvents();
  // Show table when page is loaded
  $("#budgetTables").fadeIn("slow");
  // Active initial currency format to all inputs
  $("input[name$='amount']").trigger("focusout");
}

function attachEvents(){
  // Leveraged Events
  $("select.leveraged").change(addLeveragedEvent);
  $("#leveraged .leveragedPartner .removeButton").click(removeLeveragedEvent);
  // Amount changes event
  $(".ccafsBudget input[name$='amount']").on("keyup", calculateCCAFSBudget);
  $("input[name$='amount']").on("keyup", calculateOverallBudget);
  $("input[name$='amount']").on("keydown", isNumber);
  
  $("input[name$='amount']").on("focusout", setCurrency);
  $("input[name$='amount']").on("focus", removeCurrency);
  
  $("form").submit(function(event){
    $("input[name$='amount']").each(function(){
      $(this).attr("readonly", true);
      $(this).val(removeCurrencyFormat($(this).val()));
    });
    return;
  });
}

// Leveraged Functions //
function addLeveragedEvent(e){
  e.preventDefault();
  $("#selectLeveraged").hide();
  var $parent = $(e.target).parent();
  var $newElement = $("#leveragedPartnerTemplate").clone(true).removeAttr("id").addClass("leveragedPartner").addClass("budgetContent");
  var $optionSelected = $(e.target).find('option:selected');
  if ($parent.find("select.leveraged option").length != 0) {
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

function removeLeveragedEvent(e){
  e.preventDefault();
  var $target = $(e.target).parent();
  var $parent = $target.parent();
  var option = '<option value="' + $parent.find("input[id$='id']").attr("value") + '">' + $parent.find("[id$='partnerName']").html() + '</option>';
  $parent.parent().find("select.leveraged").append(option);
  $parent.hide("slide", function(){
    $parent.remove();
    setAmountIndexes();
  });
}

function setAmountIndexes(){
  var className = "budgetContent";
  $("." + className).each(function(index,element){
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
function addChosen(){
  $("form select.leveraged").each(function(){
    $(this).chosen({
      search_contains : true
    });
  });
}

// Calculate budget functions
function calculateOverallBudget(){
  var Amount = totalBudget("form input[name$='amount']");
  var totalAmount = yearTotalBudget + Amount;
  $("span#projectTotalBudget").text(setCurrencyFormat(totalAmount));
}

function calculateCCAFSBudget(){
  var Amount = totalBudget("form .ccafsBudget input[name$='amount']");
  var totalAmount = yearTotalCCAFSBudget + Amount;
  $("span#projectTotalCCAFSBudget").text(setCurrencyFormat(totalAmount));
}

function totalBudget(inputList){
  var Amount = 0;
  $(inputList).each(function(index,amount){
    if (!$(amount).val().length == 0) {
      Amount += removeCurrencyFormat($(amount).val());
    }
  });
  return Amount;
}

function setCurrency(event){
  $input = $(event.target);
  if ($input.val().length == 0)
    $input.val("0");
  $input.val(setCurrencyFormat($input.val()));
}

function removeCurrency(event){
  $input = $(event.target);
  $input.val(removeCurrencyFormat($input.val()));
  if ($input.val() == "0")
    $input.val("");
}
