//Limits for textarea input
var lWordsElemetDesc = 150;

$(document).ready(function(){
  init();
});

function init(){
  attachEvents();
  if (!$("div#MidOutcomeBlocks .midOutcome").length) {
    $("div#addMidOutcomeBlock").trigger("click");
  } else {
    applyWordCounter($("form .midOutcome > .textArea textarea"), lWordsElemetDesc);
    setMidOutcomesIndexes();
    addChosen();
  }
}

function attachEvents(){
  // Mid Outcomes
  $("div#addMidOutcomeBlock").click(addMidOutcomeEvent);
  $(".removeMidOutcomeBlock #removeMidOutcome").click(removeMidOutcomeEvent);
  
  // Select flagship
  $("select[id$='flagships']").change(updateMidOutcomes);
  
  // Contributes
  $(".addContributeBlock input.addButton").click(addContributeEvent);
  $(".removeContribute").click(removeContributeEvent);
  
  // Regional Indicators
  $(".midOutcomeIndicator").click(indicatorVerify);
  
  // Indicator parent element
  $(".contributions .checkboxGroup input.midOutcomeIndicator").click(setFplIndicatorFields);
  
  // Indicator parent element within the template
  $(".elementIndicator > input.midOutcomeIndicator").click(setFplIndicatorFields);
}

// ----------------- Regional Indicators ----------------------//

function indicatorVerify(event){
  
  var checkedIndicators = $(event.target).parent().find("input[name^='" + event.currentTarget.name + "']:checked").length;
  var $textArea = $(event.target).parent().find(".fields");
  if (!checkedIndicators == 0) {
    $textArea.fadeIn("slow");
  } else {
    $textArea.hide("slow");
  }
}

// ----------------- Mid Outcomes Events ----------------------//
function addMidOutcomeEvent(event){
  event.preventDefault();
  var $newElement = $("#midOutcomeRPLTemplate").clone(true).removeAttr("id");
  $("div#MidOutcomeBlocks").append($newElement);
  $newElement.find("select[id$='flagships']").trigger("change");
  applyWordCounter($newElement.find($("textarea")), lWordsElemetDesc);
  $newElement.fadeIn("slow");
  $newElement.find("select[id$='flagships']").chosen({
    search_contains : true
  });
  $newElement.find("select[id$='midOutcomesFPL']").chosen({
    search_contains : true
  });
  setMidOutcomesIndexes();
}

function removeMidOutcomeEvent(event){
  event.preventDefault();
  var $ElementDiv = $(event.target).parent().parent();
  $ElementDiv.hide("slow", function(){
    $(this).remove();
    setMidOutcomesIndexes();
  });
}

function setMidOutcomesIndexes(){
  console.log("setMidOutcomesIndexes");
  $("div#MidOutcomeBlocks .midOutcome").each(function(index,element){
    console.log(index);
    var elementName = "midOutcomes[" + index + "].";
    $(element).attr("id", "midOutcome-" + index);
    $(element).find("[id$='elementIndex']").html(index + 1);
    $(element).find("[id$='id']").attr("name", elementName + "id");
    $(element).find("[id$='ProgramId']").attr("name", elementName + "program.id");
    $(element).find("[id$='TypeId']").attr("name", elementName + "type.id");
    $(element).find("[name$='contributesTo']").attr("name", elementName + "contributesTo");
    $(element).find("[id$='description']").attr("name", elementName + "description").attr("placeholder", "Add regional outcome #" + (index + 1));
    $(element).find("select[id$='flagships']").trigger("change");
    $(element).find("select[id$='midOutcomesFPL']").trigger("liszt:updated");
    setContributesIndexes();
  });
}

// Update midOutcomes List when chose a flagship.
function updateMidOutcomes(event){
  var $target = $(event.target);
  var $parent = $target.parent().parent().parent();
  var programID = $target.find('option:selected').attr("value");
  var midOutcomeTypeId = $("#midOutcomeTypeId").val();
  $.getJSON("../json/ipElementsByProgramAndType.do?programID=" + programID + "&elementTypeId=" + midOutcomeTypeId).done(function(data){
    var contributedOfIDs = new Array();
    $target.parent().parent().parent().find("input#contributeId").each(function(index,element){
      contributedOfIDs[index] = $(element).val();
    });
    $parent.find("select[id$='midOutcomesFPL'] option").remove();
    $.each(data.IPElementsList, function(){
      if ($.inArray(String(this.id), contributedOfIDs) == -1) {
        $parent.find("select[id$='midOutcomesFPL']").append('<option value="' + this.id + '">' + this.description + '</option>');
      }
    });
    $parent.find("select[id$='midOutcomesFPL']").trigger("liszt:updated");
  }).fail(function(){
    console.log("error");
  });
}

// ----------------- Contribute Events ----------------------//
function addContributeEvent(event){
  event.preventDefault();
  var $addButton = $(event.target).parent();
  var $selectElemet = $(event.target).siblings().find("select");
  if ($selectElemet.find('option').length != 0) {
    var $optionSelected = $selectElemet.find('option:selected');
    var elementId = $optionSelected.attr("value");
    var programID = $addButton.parent().parent().find("select[id$='midOutcomesRPL_flagships'] ").val();
    var $newElementClone = $("#contributeTemplate").clone(true).removeAttr("id");
    $newElementClone.find("#description").html($optionSelected.html());
    $newElementClone.find("[id$='contributeId']").attr("value", elementId);
    $.getJSON("../ipIndicators.do?programID=" + programID + "&elementID=" + elementId).done(function(data){
      $.each(data.IPElementsList, function(index,element){
        var $newIndicator = $("#indicatorTemplate").clone(true).removeAttr("id");
        $newIndicator.find("input[name$='parent']").attr("value", element.id);
        $newIndicator.find("label.checkboxLabel").html(element.description);
        $newIndicator.find(".fields #target").attr("placeholder", element.target);
        $newElementClone.find("div.checkboxGroup").append($newIndicator);
        $newIndicator.show();
      });
      $addButton.before($newElementClone);
      $newElementClone.find(".midOutcomeIndicator").click(indicatorVerify);
      $newElementClone.show("slow");
      $optionSelected.remove();
      // Once the users select one contribution, they cannot change the flagship
      $addButton.parent().parent().find("select[id$='midOutcomesRPL_flagships'] ").attr("disabled", true).trigger("liszt:updated");
      $selectElemet.trigger("liszt:updated");
      setContributesIndexes();
    });
  }
}

function removeContributeEvent(event){
  event.preventDefault();
  var $elementDiv = $(event.target).parent().parent();
  var $parentDiv = $elementDiv.parent().parent();
  $elementDiv.hide("slow", function(){
    $parentDiv.find("select.contributes").append('<option value="' + $elementDiv.find("input").attr("value") + '">' + $elementDiv.find("p").html() + '</option>');
    $(this).remove();
    setContributesIndexes();
    // If users remove all the contributions, so they
    // can change the flagship selected again
    if ($parentDiv.find(".contributions").length == 0) {
      $parentDiv.find("select[id$='midOutcomesRPL_flagships']").attr("disabled", false);
    }
    $parentDiv.find("select").trigger("liszt:updated");
  });
}

function setContributesIndexes(){
  var elementName;
  var indicatorCount = -1;
  
  $(".midOutcome ").each(function(index,contribution){
    indicatorCount = -1;
    $(contribution).find("input#contributeId").attr("name", "midOutcomes[" + index + "].translatedOf");
    
    $(contribution).find("div.contributions .elementIndicator").each(function(indicatorIndex,element){
      
      // If the indicator is checked
      if ($(element).find("input[name$='parent']").attr("checked")) {
        indicatorCount++;
        elementName = "midOutcomes[" + index + "].";
      } else {
        elementName = "__midOutcomes[" + index + "].";
      }
      
      $(element).find("input[name$='id']").attr("name", elementName + "indicator[" + indicatorCount + "].id");
      $(element).find("input[name$='parent']").attr("name", elementName + "indicators[" + indicatorCount + "].parent");
      $(element).find("input[name$='parent']").attr("id", elementName + "indicatorFPL-" + indicatorCount + "" + indicatorIndex);
      $(element).find("label.checkboxLabel").attr("for", elementName + "indicatorFPL-" + indicatorCount + "" + indicatorIndex);
      $(element).find("input[name$='target']").attr("name", elementName + "indicators[" + indicatorCount + "].target");
      
      $(element).find("textarea[name$='description']").attr("name", elementName + "indicators[" + indicatorCount + "].description").attr("placeholder", "");
    });
  });
}

function setFplIndicatorFields(event){
  var $checkbox = $(event.target);
  var $indicatorBlock = $checkbox.parent();
  /*
   * if (!$indicatorBlock.hasClass("elementIndicator")) { $checkbox.add($checkbox.next('label')).wrapAll('<div class="elementIndicator">'); $indicatorBlock = $checkbox.parent(); }
   */

  if ($indicatorBlock.find(".fields").length == 0) {
    var $indicatorTemplate = $("#indicatorTemplate").find(".fields").clone();
    $indicatorTemplate.removeAttr("id");
    $indicatorBlock.append($indicatorTemplate);
    $indicatorBlock.find(".fields").show("slow");
  }
  setMidOutcomesIndexes();
}

// Activate the chosen plugin.
function addChosen(){
  $("form select").chosen({
    search_contains : true
  });
  
}
