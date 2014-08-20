//Limits for textarea input
var lWordsElemetDesc = 150;
var lWordsIndicatorDesc = 50;

$(document).ready(function(){
  init();
});

function init(){
  // $(".idosIndicators").hide();
  applyWordCounter($("form .outcome > .textArea textarea"), lWordsElemetDesc);
  
  if ($("form .outcomeIndicatorsBlock textarea").length > 0) {
    applyWordCounter($("form .outcomeIndicatorsBlock textarea"), lWordsIndicatorDesc);
  }
  
  // Set input indicator relation
  $(".idosIndicators > .checkboxLabel").each(function(index,indLabel){
    var $parent = $(indLabel).parent();
    var indLabelFor = $(indLabel).attr("for");
    var $input = $parent.find("input[id^='" + indLabelFor + "']");
    $(indLabel).attr("for", indLabelFor + "-" + index);
    $input.attr("id", indLabelFor + "-" + index);
  });
  
  if ($("#isFPL").exists()) {
    initGraph($("#programID").val());
  } else {
    initGraph();
  }
  
  setIdoIndicatorsIndexes();
  setIDOsIndicatorsLabel();
  attachEvents();
}
function attachEvents(){
  $(".outcomeIndicatorsBlock input.addButton").click(addIndicatorEvent);
  $(".indicator a.removeOutcomeIndicator").click(removeIndicatorEvent);
  $("#idosBlock .idosIndicators input.indicatorsCheckbox").click(setIdoIndicatorsIndexes);
  $(".idosCheckbox").change(viewIDOsIndicators);
  $(".indicatorsCheckbox").change(verifyIDOcheck);
  $(".indicatorFPLCheckbox").change(rplIndicatorEvent);
}

function rplIndicatorEvent(event){
  $checkbox = $(event.target);
  if ($checkbox.attr("checked")) {
    $checkbox.parent().find("input[name$='target']").attr("disabled", false);
    $checkbox.parent().find("input[name$='id']").attr("disabled", false);
  } else {
    $checkbox.parent().find("input[name$='target']").attr("disabled", true);
    $checkbox.parent().find("input[name$='id']").attr("disabled", true);
  }
}

function setIdoIndicatorsIndexes(event){
  var outcomeIndicatorsSize = $(".outcomeIndicatorsBlock div.indicator").length;
  $("#outcomes .outcome").each(function(outcomeIndex,outcome){
    var indicatorsChecked = 0;
    $(outcome).find("#idosBlock .idosIndicators input.indicatorsCheckbox").each(function(indicatorIndex,indicator){
      if ($(indicator).attr("checked")) {
        $(indicator).attr("name", "outcomes[" + outcomeIndex + "].indicators[" + (indicatorsChecked + outcomeIndicatorsSize) + "].parent");
        indicatorsChecked++;
      } else {
        $(indicator).attr("name", "idoIndicator");
      }
    });
  });
}

function viewIDOsIndicators(event){
  var $target = $(event.target);
  var $indicatorsBlock = $target.parent().find(".idosIndicators");
  if ($target.prop('checked')) {
    $indicatorsBlock.show(300);
    $indicatorsBlock.find(".indicatorsCheckbox").removeAttr("disabled");
  } else {
    $indicatorsBlock.hide(300);
    $indicatorsBlock.find(".indicatorsCheckbox").attr("disabled", true);
  }
}

function setIDOsIndicatorsLabel(){
  $("[for^=ido-]").each(function(index,ido){
    var text = $(ido).text().split("-")[1] + $(ido).text().split("-")[2];
    $(ido).html("<strong>" + $(ido).text().split("-")[0] + "</strong> - " + text);
  });
}

function verifyIDOcheck(event){
  var $target = $(event.target);
  var $indicatorsBlock = $target.parent().parent();
  var $isCheck = $indicatorsBlock.find(".indicatorsCheckbox:checked");
  var $currentIdo = $indicatorsBlock.find(".idosCheckbox");
  if ($isCheck.length <= 0) {
    console.log("no: " + this);
    $currentIdo.removeAttr("checked").trigger("change");
  } else {
    console.log("yes: " + this);
    $currentIdo.attr("checked", true).trigger("change");
  }
}

function addIndicatorEvent(event){
  var $newIndicator = $("#outcomeTemplate div.indicator").clone(true);
  $("#addIndicatorBlock").before($newIndicator);
  
  $newIndicator.show("slow");
  setIndicatorsIndexes();
}

function removeIndicatorEvent(event){
  event.preventDefault();
  var pressedLink = event.target;
  var $indicatorDiv = $(event.target).parent().parent();
  $indicatorDiv.hide("slow", function(){
    $(this).remove();
    setIndicatorsIndexes();
  });
  
}

function setIndicatorsIndexes(){
  $(".outcomeIndicatorsBlock div.indicator").each(function(index,indicator){
    var elementName = "outcomes[0].indicators[" + index + "].";
    console.log(index + ": " + elementName);
    $(indicator).find("[id$='id']").attr("name", elementName + "description");
    $(indicator).find("[id$='description']").attr("name", elementName + "description");
    $(indicator).find("[id$='target']").attr("name", elementName + "target");
  });
}
