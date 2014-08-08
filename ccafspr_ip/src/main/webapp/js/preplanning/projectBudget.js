$(document).ready(function(){
  init();
});
function init(){
  //
  /*
   * $("#budgetTables").tabs({ active : 1, show : { duration : 500 } });
   */
  addChosen();
  attachEvents();
  $("#budgetTables").fadeIn("slow");
  // $("#budgetTables").tabs('option', 'active', "partnerTables-2016");
  
}

function attachEvents(){
  // Leveraged Events
  $("#leveraged .addLeveragedBlock .addButton").click(addLeveragedEvent);
  $("#leveraged .leveragedPartner .removeButton").click(removeLeveragedEvent);
  
  // Amount changes event
  $(".ccafsBudget input[name$='amount']").on("keyup", calculateCCAFSBudget);
  $("input[name$='amount']").on("keyup", calculateOverallBudget);
  $("input[name$='amount']").on("keypress", isNumber);
}

// Leveraged Functions //
function addLeveragedEvent(e){
  e.preventDefault();
  var $parent = $(e.target).parent();
  var $newElement = $("#leveragedPartnerTemplate").clone(true).removeAttr("id").addClass("leveragedPartner").addClass("budgetContent");
  var $optionSelected = $parent.find("select.leveraged").find('option:selected');
  if ($parent.find("select.leveraged option").length != 0) {
    $parent.before($newElement);
    $newElement.find("[id$='partnerName']").html($optionSelected.html());
    $newElement.find("input[id$='id']").attr("value", $optionSelected.val());
    $newElement.find(".removeButton").click(removeLeveragedEvent);
    $optionSelected.remove();
    $parent.find("select.leveraged").trigger("liszt:updated");
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
    $(element).find("[name$='id']").attr("name", elementName + ".id");
    $(element).find("[name$='year']").attr("name", elementName + ".year");
    $(element).find("[id$='institution.id']").attr("name", elementName + ".institution.id");
    $(element).find("[name$='amount']").attr("name", elementName + ".amount");
    $(element).find("[name$='type']").attr("name", elementName + ".type");
  });
}

// Activate the chosen plugin to the countries, partner types and
// partners lists.
function addChosen(){
  $("form select[name$='leveragedList']").each(function(){
    // Check if its not the template partner field
    $(this).chosen({
      no_results_text : $("#noResultText").val(),
      search_contains : true
    });
  });
}

function calculateOverallBudget(){
  var totalAmount = 0.0;
  $("form input[name$='amount']").each(function(index,amount){
    totalAmount += parseInt($(amount).val());
  });
  $("p#projectTotalBudget").text('US$ ' + parseFloat(totalAmount, 10).toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, "$1,").toString());
}

function calculateCCAFSBudget(){
  var totalAmount = 0.0;
  $("form .ccafsBudget input[name$='amount']").each(function(index,amount){
    totalAmount += parseInt($(amount).val());
  });
  $("p#projectTotalCCAFSBudget").text('US$ ' + parseFloat(totalAmount, 10).toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, "$1,").toString());
}
