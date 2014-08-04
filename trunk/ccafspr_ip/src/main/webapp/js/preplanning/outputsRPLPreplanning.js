//Limits for textarea input
var lWordsElemetDesc = 10;

$(document).ready(function(){
  attachEvents();
  if (!$("div#outputBlocks .output").length) {
    $(".noOutputs.message").hide();
  } else {
    $("select#outputsRPL_flagships").trigger("change");
  }
});

function attachEvents(){
  // Outputs
  $("a#addNewOutput").click(addOutputEvent);
  $("a#addExistingOutput").click(addExistingOutputEvent);
  $(".removeOutputBlock a#removeOutput").click(removeOutputEvent);
  
  // Select flagship
  $("select#outputsRPL_flagships").change(updateMidOutcomesList);
  
  // Select Mid Outcome
  $("select#outputsRPL_midOutcomes").change(updateOutputsList);
  
  // Contributes
  $(".addContributeBlock input.addButton").click(addContributeEvent);
  $(".removeContribute").click(removeContributeEvent);
}

function updateMidOutcomesList(event){
  var $target = $(event.target);
  var $parent = $target.parent().parent().parent().parent();
  var programID = $target.find('option:selected').attr("value");
  var elementTypeId = $("#midOutcomeTypeID").val();
  $.getJSON("../json/ipElementsByProgramAndType.do?programID=" + programID + "&elementTypeId=" + elementTypeId, function(data){
    $parent.find("select#outputsRPL_midOutcomes option").remove();
    $.each(data.IPElementsList, function(){
      $parent.find("select#outputsRPL_midOutcomes").append('<option value="' + this.id + '">' + this.description + '</option>');
    });
  }).fail(function(){
    console.log("error");
  }).done(function(){
    $parent.find("select#outputsRPL_midOutcomes").attr("disabled", false);
    $parent.find("select#outputsRPL_midOutcomes").trigger("change");
  });
}

function updateOutputsList(event){
  console.log("outputs fired");
  var $target = $(event.target);
  var $parent = $target.parent().parent().parent().parent();
  var midOutcomeId = $target.val();
  
  $.getJSON("../json/ipElementsByParent.do?elementID=" + midOutcomeId, function(data){
    $parent.find("select[id^='outputsRPL_outputs_'] option").remove();
    $.each(data.IPElementsList, function(){
      if (objectsListContains(this.contributesTo, midOutcomeId)) {
        $parent.find("select[id^='outputsRPL_outputs_']").append('<option value="' + this.id + '">' + this.description + '</option>');
      }
    });
  }).fail(function(){
    console.log("error");
  }).done(function(){
    $parent.find("select#outputs").attr("disabled", false);
  });
}

function objectsListContains(objList,target){
  var hasTarget = false;
  $.each(objList, function(){
    if (this.id == target)
      hasTarget = true;
  });
  return hasTarget;
}

// ----------------- Outputs Events ----------------------//
function addOutputEvent(event){
  event.preventDefault();
  var $newElement = $("#outputTemplate").clone(true).removeAttr("id");
  $("div#outputBlocks").append($newElement);
  $newElement.show("slow");
  setOutputsIndexes();
}
function addExistingOutputEvent(){
  event.preventDefault();
  var $newElement = $("#newOutputTemplate").clone(true).removeAttr("id");
  $("div#outputBlocks").append($newElement);
  $newElement.find("select#flagships").trigger("change");
  $newElement.show("slow");
  setOutputsIndexes();
}

function removeOutputEvent(event){
  event.preventDefault();
  var $ElementDiv = $(event.target).parent().parent();
  $ElementDiv.hide("slow", function(){
    $(this).remove();
    setOutputsIndexes();
  });
}

function setOutputsIndexes(){
  $("div#outputBlocks .output").each(function(index,element){
    var elementName = "outputs[" + index + "].";
    $(element).attr("id", "output-" + index);
    $(element).find("[id$='elementIndex']").html(index + 1);
    $(element).find("[id^='outputId']").attr("name", elementName + "id");
    $(element).find("[id^='outputTypeID']").attr("name", elementName + "type.id");
    $(element).find("[id^='outputProgramID']").attr("name", elementName + "program.id");
    $(element).find("[id$='description']").attr("name", elementName + "description").attr("placeholder", "Add output #" + (index + 1));
    setContributesIndexes(index);
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
  $elementDiv.hide("slow", function(){
    var i = $parentDiv.attr("id").split("-")[1];
    $parentDiv.find("select").append('<option value="' + $elementDiv.find("input").attr("value") + '">' + $elementDiv.find("p").html() + '</option>');
    $(this).remove();
    setContributesIndexes(i);
  });
}

function setContributesIndexes(i){
  $("#output-" + i + " div.contributions, #output-" + i + " div.translations").each(function(index,element){
    // For new contributeTo outputs
    var elementName = "outputs[" + i + "].contributesTo[" + index + "].";
    $(element).find("[id^='contributeId']").attr("name", elementName + "id");
    // For existing translated outputs
    elementName = "outputs[" + i + "].translatedOf";
    $(element).find("[id$='outputs']").attr("name", elementName);
    console.log("update");
  });
}
