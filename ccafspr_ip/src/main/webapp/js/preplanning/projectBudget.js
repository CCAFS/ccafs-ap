$(document).ready(function(){
  init();
});
function init(){
  //
  $("#budgetTables").tabs({
    show : {
    effect : "slide",
    direction : "up",
    duration : 500
    }
  });
  attachEvents();
}

function attachEvents(){
  // Leveraged Events
  $("#leveraged .addLeveragedBlock .addButton").click(addLeveragedEvent);
  $("#leveraged .leveragedPartner .removeButton").click(removeLeveragedEvent);
}

function addLeveragedEvent(e){
  e.preventDefault();
  var $parent = $(e.target).parent();
  var $newElement = $("#leveragedPartnerTemplate").clone(true).removeAttr("id").addClass("leveragedPartner");
  var $optionSelected = $parent.find("select.leveraged").find('option:selected');
  $parent.before($newElement);
  $newElement.show("slow");
  $newElement.find("[id$='partnerName']").html($optionSelected.html());
  $newElement.find("input[id$='id']").attr("value", $optionSelected.val());
  $newElement.click(removeLeveragedEvent);
  setLeveragedIndexes();
}

function removeLeveragedEvent(e){
  var $target = $(e.target).parent();
  var $parent = $target.parent();
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

function setAttrName(target,source){
  
}
