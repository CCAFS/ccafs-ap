//Limits for textarea input
var lWordsElemetDesc = 10;
var lWordsIndicatorDesc = 50;

$(document).ready(function(){
  init();
});

function init(){
  attachEvents();
  if (!$("div#MidOutcomeBlocks .midOutcome").length) {
    $("div#addMidOutcomeBlock").trigger("click");
  } else {
    applyWordCounter($("form .midOutcome > .textArea textarea"), lWordsElemetDesc);
    applyWordCounter($("form .indicatorsBlock textarea"), lWordsIndicatorDesc);
  }
  setMidOutcomesIndexes();
}

function attachEvents(){
  // Mid Outcomes
  $("div#addMidOutcomeBlock").click(addMidOutcomeEvent);
  $(".removeMidOutcomeBlock #removeMidOutcome").click(removeMidOutcomeEvent);
  // Contributes
  $(".addContributeBlock input.addButton").click(addContributeEvent);
  $(".removeContribute").click(removeContributeEvent);
  // Indicators
  $(".indicatorsBlock input.addButton").click(addIndicatorEvent);
  $(".removeMidOutcomeIndicator").click(removeIndicatorEvent);
}

// ----------------- Mid Outcomes Events ----------------------//
function addMidOutcomeEvent(event){
  event.preventDefault();
  var $newElement = $("#midOutcomeTemplate").clone(true).removeAttr("id");
  $("div#MidOutcomeBlocks").append($newElement);
  applyWordCounter($newElement.find("> .textArea textarea"), lWordsElemetDesc);
  applyWordCounter($newElement.find(".indicatorsBlock textarea"), lWordsIndicatorDesc);
  $newElement.show("slow");
  setMidOutcomesIndexes();
}

function removeMidOutcomeEvent(event){
  event.preventDefault();
  var $ElementDiv = $(event.target).parent().parent();
  var elementID = $ElementDiv.find("[id^='midOutcomeId']").val();
  var source = "../json/ipElementsByParent.do?elementID=" + elementID;
  $.getJSON(source, function(){
  }).done(function(data){
    if (data.IPElementsList.length == 0) {
      $ElementDiv.hide("slow", function(){
        $(this).remove();
        setMidOutcomesIndexes();
      });
    } else {
      $("#removeIPElementDialog").find(".elements").html(data.IPElementsList.length);
      $("#removeIPElementDialog").dialog({
        modal : true,
        buttons : {
          "Remove" : function(){
            $ElementDiv.hide("slow", function(){
              $(this).remove();
              setMidOutcomesIndexes();
            });
            $(this).dialog("close");
          },
          "Cancel" : function(){
            $(this).dialog("close");
          }
        }
      });
    }
  }).fail(function(){
    console.log("error");
  });
  
}

function setMidOutcomesIndexes(){
  // console.log($("div#MidOutcomeBlocks .midOutcome"));
  $("div#MidOutcomeBlocks .midOutcome").each(function(index,element){
    
    var elementName = "midOutcomes[" + index + "].";
    $(element).attr("id", "midOutcome-" + index);
    // Hidden inputs
    $(element).find("[id^='midOutcomeId']").attr("name", elementName + "id");
    $(element).find("[id^='midOutcomeProgramID']").attr("name", elementName + "program.id");
    $(element).find("[id^='midOutcomeTypeID']").attr("name", elementName + "type.id");
    // Visible inputs
    $(element).find("[id$='elementIndex']").html(index + 1);
    $(element).find("textarea[name$='description']").attr("name", elementName + "description").attr("placeholder", "Add outcome #" + (index + 1));
    // console.log('setMidOutcomesIndexes -->'+index+ ' -->'+$(element).attr('id') );
    setContributesIndexes(index);
    setIndicatorsIndexes(index);
    
  });
}

// ----------------- Contribute Events ----------------------//
function addContributeEvent(event){
  event.preventDefault();
  var $addButton = $(event.target).parent();
  var $selectElemet = $(event.target).siblings().find("select");
  var $optionSelected = $selectElemet.find('option:selected');
  
  if ($selectElemet.find('option').length != 0) {
    var $newElementClone = $("#contributeTemplate").clone(true).removeAttr("id");
    var grandParentId = $addButton.parent().parent().attr("id").split("-")[1];
    $newElementClone.find("[value]").attr("value", $optionSelected.attr("value"));
    $newElementClone.find('p').html($optionSelected.html());
    $addButton.before($newElementClone);
    $newElementClone.show("slow");
    $optionSelected.remove();
    setContributesIndexes(grandParentId);
  }
}

function removeContributeEvent(event){
  event.preventDefault();
  var $elementDiv = $(event.target).parent().parent();
  var $parentDiv = $elementDiv.parent().parent();
  console.log("remove contribute ");
  $elementDiv.hide("slow", function(){
    var i = $parentDiv.attr("id").split("-")[1];
    $parentDiv.find("select").append('<option value="' + $elementDiv.find("input").attr("value") + '">' + $elementDiv.find("p").html() + '</option>');
    $(this).remove();
    setContributesIndexes(i);
  });
}

function setContributesIndexes(i){
  $("#midOutcome-" + i + " div.contributions").each(function(index,element){
    var elementName = "midOutcomes[" + i + "].contributesTo";
    $(element).find("[id^='contributeId']").attr("name", elementName).attr("value", outcome.id);
  });
}

// ----------------- Indicators Events ----------------------//
function addIndicatorEvent(event){
  var grandParentId = $(event.target).parent().parent().parent().attr("id").split("-")[1];
  // console.log(grandParentId);
  var $newIndicator = $("#midOutcomeTemplate div.indicator").clone(true).css("display", "none");
  $(event.target).parent().before($newIndicator);
  applyWordCounter($newIndicator.find("textarea"), lWordsIndicatorDesc);
  $newIndicator.show("slow");
  setIndicatorsIndexes(grandParentId);
}

function removeIndicatorEvent(event){
  event.preventDefault();
  var grandParentId = $(event.target).parent().parent().parent().parent().attr("id").split("-")[1];
  var $indicatorDiv = $(event.target).parent().parent();
  var indicatorID = $indicatorDiv.find("[name$='id']").val();
  var source = "../json/ipIndicatorsByParent.do?indicatorID=" + indicatorID;
  $.getJSON(source, function(){
  }).done(function(data){
    if (data.indicators.length == 0) {
      $indicatorDiv.hide("slow", function(){
        $(this).remove();
        setIndicatorsIndexes(grandParentId);
      });
    } else {
      $("#removeIndicatorDialog").find(".elements").html(data.indicators.length);
      $("#removeIndicatorDialog").dialog({
        modal : true,
        buttons : {
          "Remove" : function(){
            $indicatorDiv.hide("slow", function(){
              $(this).remove();
              setIndicatorsIndexes(grandParentId);
            });
            $(this).dialog("close");
          },
          "Cancel" : function(){
            $(this).dialog("close");
          }
        }
      });
    }
  }).fail(function(){
    console.log("error");
  });
}

function setIndicatorsIndexes(i){
  $("#midOutcome-" + i + " div.indicator").each(function(index,indicator){
    var elementName = "midOutcomes[" + i + "].indicators[" + index + "].";
    $(indicator).find("[name$='id']").attr("name", elementName + "id");
    $(indicator).find("[id$='description']").attr("name", elementName + "description").attr("placeholder", "Add indicator #" + (index + 1));
    $(indicator).find("[id$='target']").attr("name", elementName + "target");
  });
}
