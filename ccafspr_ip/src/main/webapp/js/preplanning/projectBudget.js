$(document).ready(function(){
  init();
});
function init(){
  //
  $("#budgetTables").tabs({
    show : {
      duration : 500
    }
  });
  addChosen();
  attachEvents();
}

function attachEvents(){
  // Leveraged Events
  $("#leveraged .addLeveragedBlock .addButton").click(addLeveragedEvent);
  $("#leveraged .leveragedPartner .removeButton").click(removeLeveragedEvent);
}

// Leveraged Functions //
function addLeveragedEvent(e){
  e.preventDefault();
  var $parent = $(e.target).parent();
  var $newElement = $("#leveragedPartnerTemplate").clone(true).removeAttr("id").addClass("leveragedPartner");
  var $optionSelected = $parent.find("select.leveraged").find('option:selected');
  if ($parent.find("select.leveraged option").length != 0) {
    $parent.before($newElement);
    $newElement.find("[id$='partnerName']").html($optionSelected.html());
    $newElement.find("input[id$='id']").attr("value", $optionSelected.val());
    $newElement.find(".removeButton").click(removeLeveragedEvent);
    $optionSelected.remove();
    $parent.find("select.leveraged").trigger("liszt:updated");
    $newElement.show("slow");
    setLeveragedIndexes();
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
    setLeveragedIndexes();
  });
}

function setLeveragedIndexes(){
  var className = "leveragedPartner";
  $("div." + className).each(function(index,element){
    var elementName = "leveragedInstitutions[" + index + "].budgets.";
    $(element).attr("id", className + "-" + index);
    // CSS selector div[id$=parent] Get any DIV element where the ID attribute value ends with "parent".
    $(element).find("[id$='id']").attr("name", elementName + "id");
    $(element).find("[id$='amount']").attr("name", elementName + "amount");
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
