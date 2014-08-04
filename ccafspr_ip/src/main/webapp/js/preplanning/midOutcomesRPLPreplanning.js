//Limits for textarea input
var lWordsElemetDesc = 10;

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
  
  $(".contributions .checkboxGroup input").click(setFplIndicatorFields);
}

// ----------------- Regional Indicators ----------------------//

function indicatorVerify(event){
  var $parent = $(event.target).parent().parent().parent();
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
  $("div#MidOutcomeBlocks .midOutcome").each(function(index,element){
    var elementName = "midOutcomes[" + index + "].";
    $(element).attr("id", "midOutcome-" + index);
    $(element).find("[id$='elementIndex']").html(index + 1);
    $(element).find("[id$='id']").attr("name", elementName + "id");
    $(element).find("[id$='ProgramId']").attr("name", elementName + "program.id");
    $(element).find("[id$='TypeId']").attr("name", elementName + "type.id");
    $(element).find("[id$='description']").attr("name", elementName + "description").attr("placeholder", "Add regional outcome #" + (index + 1));
    $(element).find("select[id$='flagships']").trigger("change");
    setContributesIndexes();
  });
}

function updateMidOutcomes(event){
  var $target = $(event.target);
  var $parent = $target.parent().parent().parent();
  var programID = $target.find('option:selected').attr("value");
  var elementTypeId = $("#midOutcomeTypeId").val();
  $.getJSON("../json/ipElementsByProgramAndType.do?programID=" + programID + "&elementTypeId=" + elementTypeId, function(){
    console.log("MidOutcome: " + $parent.attr("id"));
    console.log($target.find('option:selected').attr("value"));
  }).done(function(data){
    $parent.find("select[id$='midOutcomesFPL'] option").remove();
    $.each(data.IPElementsList, function(){
      $parent.find("select[id$='midOutcomesFPL']").append('<option value="' + this.id + '">' + this.description + '</option>');
    });
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
    var programID = $("#midOutcomesRPL_flagships").val();
    console.log($addButton.parent().parent().attr("id"));
    var $newElementClone = $("#contributeTemplate").clone(true).removeAttr("id");
    
    $newElementClone.find("#description").html($optionSelected.html());
    $newElementClone.find("[id$='contributeId']").attr("value", elementId);
    $.getJSON("../ipIndicators.do?programID=" + programID + "&elementID=" + elementId).done(function(data){
      $.each(data.IPElementsList, function(index,element){
        console.log(element);
        var $newIndicator = $("#indicatorTemplate").clone(true).removeAttr("id");
        $newIndicator.find("input").attr("value", element.id);
        $newIndicator.find("label.checkboxLabel").html(element.description);
        $newIndicator.find(".fields").find("#target").attr("placeholder", element.target);
        
        $newElementClone.find("div.checkboxGroup").append($newIndicator);
        $newIndicator.show();
      });

      $addButton.before($newElementClone);
      $newElementClone.find(".midOutcomeIndicator").click(indicatorVerify);
      $newElementClone.show("slow");
      $optionSelected.remove();
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
  });
}

function setContributesIndexes(){
  var elementName;
  var indicatorCount = 0;
  console.log("event");
  $(".midOutcome ").each(function(index,contribution){
    $(contribution).find("#contributeId").attr("name", "midOutcomes[" + index + "].translatedOf");
    
    $(contribution).find("div.contributions .elementIndicator").each(function(indicatorIndex,element){
      
      console.log("element " + indicatorCount);
      // If the indicator is checked
      if ($(element).find("input[name$='parent']").attr("checked")) {
        elementName = "midOutcomes[" + index + "].";
      } else {
        elementName = "__midOutcomes[" + index + "].";
      }
      
      $(element).find("input[id$='id']").attr("name", elementName + "id");
      $(element).find("input[name$='parent']").attr("name", elementName + "indicators[" + indicatorCount + "].parent");
      $(element).find("input[name$='target']").attr("name", elementName + "indicators[" + indicatorCount + "].target");
      
      $(element).find("textarea[name$='description']").attr("name", elementName + "indicators[" + indicatorCount + "].description")
                                                      .attr("placeholder", "");
      indicatorCount++;
    });
  });
}

function setFplIndicatorFields(event){
  var $checkbox= $(event.target);
  var $indicatorBlock = $checkbox.parent();
  
  if(! $indicatorBlock.hasClass("elementIndicator")){
    $checkbox.add( $checkbox.next('label') ).wrapAll('<div class="elementIndicator">');
    $indicatorBlock = $checkbox.parent();
  }
  
  if( $indicatorBlock.find(".fields").length == 0){
    var $indicatorTemplate = $("#indicatorTemplate").find(".fields").clone();  
    $indicatorTemplate.removeAttr("id");
    $indicatorBlock.append( $indicatorTemplate );
    $indicatorBlock.find(".fields").show("slow");    
  }
  
  setMidOutcomesIndexes();
}
