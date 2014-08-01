//Limits for textarea input
var lWordsElemetDesc = 10;

$(document).ready(function(){
  init();
});

function init(){
  attachEvents();
  if (!$("div#MidOutcomeBlocks .midOutcome").length) {
    $("div#addMidOutcomeBlock").trigger("click");
  }
  applyWordCounter($("form .midOutcome > .textArea textarea"), lWordsElemetDesc);
  setMidOutcomesIndexes();
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
}

// ----------------- Regional Indicators ----------------------//

function indicatorVerify(event){
  var $parent = $(event.target).parent().parent().parent();
  var checkedIndicators = $(event.target).parent().find("input[name^='" + event.currentTarget.name + "']:checked").length;
  var $textArea = $(event.target).parent().find(".fields");
  console.log($(event.target));
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
    setContributesIndexes(index);
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
    var grandParentId = $addButton.parent().parent().attr("id").split("-")[1];
    var $newElementClone = $("#contributeTemplate").clone(true).removeAttr("id");
    var elementName = "midOutcomes[" + grandParentId + "].";
    
    $newElementClone.find("#description").html($optionSelected.html());
    $newElementClone.find("[id$='contributeId']").attr("value", elementId);
    $.getJSON("../ipIndicators.do?programID=" + programID + "&elementID=" + elementId, function(data){
      $.each(data.IPElementsList, function(index,element){
        var $newIndicator = $("#indicatorTemplate").clone(true).removeAttr("id");
        $newIndicator.find("input").attr("value", this.value);
        $newIndicator.find("label.checkboxLabel").html(this.description);
        $newIndicator.find(".fields").find("#target").attr("placeholder", this.target);
        
        $newElementClone.find("div.checkboxGroup").append($newIndicator);
        $newIndicator.show();
      });
    }).done(function(){
      // $newElementClone.find("div.checkboxGroup").append('<input type="hidden" id="__multiselect_' + elementName + 'indicators" name="" value="">');
      $addButton.before($newElementClone);
      $newElementClone.find(".midOutcomeIndicator").click(indicatorVerify);
      $newElementClone.show("slow");
      $optionSelected.remove();
      setContributesIndexes(grandParentId);
    });
    
  }
}

function removeContributeEvent(event){
  event.preventDefault();
  var $elementDiv = $(event.target).parent().parent();
  var $parentDiv = $elementDiv.parent().parent();
  $elementDiv.hide("slow", function(){
    var i = $parentDiv.attr("id").split("-")[1];
    $parentDiv.find("select.contributes").append('<option value="' + $elementDiv.find("input").attr("value") + '">' + $elementDiv.find("p").html() + '</option>');
    $(this).remove();
    setContributesIndexes(i);
  });
}

function setContributesIndexes(i){
  $("#midOutcome-" + i + " div.contributions").each(function(index,element){
    var elementName = "midOutcomes[" + i + "].translateOf[" + index + "].";
    $(element).find("[id$='contributeId']").attr("name", elementName + "id");
    $(element).find("[id$='target']").attr("name", elementName + "target");
    $(element).find("[id$='justification']").attr("name", elementName + "justification");
    $(element).find("[id^='__multiselect']").attr("name", "__multiselect_" + elementName + "indicators");
    // set Indicator indexes
    $(element).find(".midOutcomeIndicator").each(function(c,indicator){
      var indicatorInputName = "midOutcomes[" + i + "].";
      var inputId = $(indicator).attr('id');
      console.log("Label before: " + $(indicator).next().attr("for"));
      console.log("Input before: " + inputId);
      // set new id for label input
      $(indicator).next().attr("for", indicatorInputName + "indicators-" + i + index + c);
      // console.log($(indicator));
      console.log("Label " + $(indicator).next().attr("for"));
      
      // set new index name for input indicator
      $(indicator).attr("name", indicatorInputName + "indicators").attr("id", indicatorInputName + "indicators-" + i + index + c);
      console.log("Input " + $(indicator).attr("id"));
    });
  });
}
