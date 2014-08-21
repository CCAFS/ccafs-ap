//Limits for textarea input
var lWordsElemetDesc = 300;

$(document).ready(function(){
  attachEvents();
  if (!$("div#outputBlocks .output").length == 0) {
    $(".noOutputs.message").hide();
    $("select[id^='outputsRPL_flagships']").trigger("change");
    addChosen();
  }
  applyWordCounter($("textarea"), lWordsElemetDesc);
  initGraph();
});

function attachEvents(){
  // Outputs
  $("a#addNewOutput").click(addOutputEvent);
  $("a#addExistingOutput").click(addExistingOutputEvent);
  $(".removeOutput").click(removeOutputEvent);
  
  // Select flagship
  $("select#outputsRPL_flagships").change(updateMidOutcomesList);
  
  // Select Mid Outcome
  $("select#outputsRPL_midOutcomes").change(updateOutputsList);
  
  // Contributes
  $("select[id$='contributions']").change(addContributeEvent);
  $(".removeContribute").click(removeContributeEvent);
  
  $("#outputsRPL").submit(showTranslationMessage);
}

/*
 * When users create a translated output but not select which flagship output is going to be translated A warning message should appear before complete the submission
 */
function showTranslationMessage(){
  
  var $contributionSelects = $("#outputsRPL select[name$='translatedOf']");
  
  $contributionSelects.each(function(index,selectElemment){
    // If the contribution select doesn't have a value defined
    var $select = $(selectElemment);
    if (!$select.val()) {
      event.preventDefault();
      var selectName = $select.attr("name");
      var mogIndex = selectName.split("[")[1].split("]")[0];
      
      $("#existentOutputDialog").find(".elements").html(parseInt(mogIndex) + 1);
      $("#existentOutputDialog").dialog({
        modal : true,
        buttons : {
          "Add as new MOG" : function(){
            $("#outputsRPL").submit();
            $(this).dialog("close");
          },
          "Cancel" : function(){
            $(this).dialog("close");
          }
        }
      });
    }
  });
  
  return;
}

function updateMidOutcomesList(event){
  var $target = $(event.target);
  var $parent = $target.parent().parent().parent().parent();
  var programID = $target.find('option:selected').attr("value");
  var elementTypeId = $("#midOutcomeTypeID").val();
  var midOutcomeSelected = $parent.find("#midOutcomeSelected").val();
  var $select = $parent.find("select#outputsRPL_midOutcomes");
  console.log("test: " + $("#midOutcomeTypeID").attr("id"));
  if (programID != -1) {
    $.getJSON("../json/ipElementsByProgramAndType.do?programID=" + programID + "&elementTypeId=" + elementTypeId, function(data){
      $select.find("option").remove();
      $.each(data.IPElementsList, function(){
        $select.append('<option value="' + this.id + '">' + this.description + '</option>');
      });
    }).fail(function(){
      console.log("error");
    }).done(function(){
      if (midOutcomeSelected) {
        $select.val(midOutcomeSelected);
      }
      $select.attr("disabled", false).trigger("change").trigger("liszt:updated");
    });
  }
}

/**
 * When users create a translated output but not select which flagship output is going to be translated A warning message should appear before complete the submission
 */
function showOutputTranslatedWarning(event){
  event.preventDefault();
  
  var $contributionSelects = $("#outputsRPL select[name$='translatedOf']");
  
  $contributionSelects.each(function(index,selectElemment){
    console.log(index);
    // If the contribution select doesn't have a value defined
    var $select = $(selectElemment);
    if (!$select.val()) {
      var selectName = $select.attr("name");
      var mogIndex = selectName.split("[")[1].split("]")[0];
      
      $("#existentOutputDialog").find(".elements").html(parseInt(mogIndex) + 1);
      $("#existentOutputDialog").dialog({
        modal : true,
        buttons : {
          "Add as new MOG" : function(){
            
            $("form").submit();
            $(this).dialog("close");
          },
          "Cancel" : function(){
            $(this).dialog("close");
          }
        }
      });
    }
  });
  
}

function updateOutputsList(event){
  var $target = $(event.target);
  var $parent = $target.parent().parent().parent().parent();
  var midOutcomeId = $target.val();
  var outputSelected = $parent.find("#outputSelected").val();
  $.getJSON("../json/ipElementsByParent.do?elementID=" + midOutcomeId, function(data){
    $parent.find("select[id^='outputsRPL_outputs'] option").remove();
    $.each(data.IPElementsList, function(){
      if (objectsListContains(this.contributesTo, midOutcomeId)) {
        $parent.find("select[id^='outputsRPL_outputs']").append('<option value="' + this.id + '">' + this.description + '</option>');
      }
    });
  }).fail(function(){
    console.log("error");
    $parent.find("select[id^='outputsRPL_outputs'] option").remove();
  }).done(function(){
    if (outputSelected) {
      $parent.find("select[id^='outputsRPL_outputs']").val(outputSelected);
    }
    $parent.find("select[id^='outputs']").attr("disabled", false).trigger("liszt:updated");
    ;
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
  $newElement.find("select[id$='contributions']").chosen({
    search_contains : true
  });
  setOutputsIndexes();
}
function addExistingOutputEvent(){
  event.preventDefault();
  var $newElement = $("#newOutputTemplate").clone(true).removeAttr("id");
  $("div#outputBlocks").append($newElement);
  $newElement.show("slow");
  $newElement.find("select[id$='flagships']").trigger("change");
  $newElement.find("select[id$='flagships']").chosen({
    search_contains : true
  });
  $newElement.find("select[id$='midOutcomes']").chosen({
    search_contains : true
  });
  $newElement.find("select[id^='outputs']").chosen({
    search_contains : true
  });
  setOutputsIndexes();
}

function removeOutputEvent(event){
  event.preventDefault();
  var $ElementDiv = $(event.target).parent();
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
  var $selectElemet = $(event.target);
  var $optionSelected = $selectElemet.find('option:selected');
  
  if ($selectElemet.find('option').length != 0) {
    var $newElementClone = $("#contributeTemplate").clone(true).removeAttr("id");
    var grandParentId = $selectElemet.parent().parent().parent().parent().parent().attr("id").split("-")[1];
    
    $newElementClone.find("[value]").attr("value", $optionSelected.attr("value"));
    $newElementClone.find('p').html($optionSelected.html());
    $selectElemet.parent().parent().parent().before($newElementClone);
    $newElementClone.show("slow");
    $optionSelected.remove();
    $selectElemet.trigger("liszt:updated");
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
    $parentDiv.find("select").trigger("liszt:updated");
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
    $(element).find("[id^='outputsRPL_outputs']").attr("name", elementName);
  });
}

// Activate the chosen plugin to the midOutcomes list.
function addChosen(){
  $("form select[id$='flagships']").each(function(){
    $(this).chosen({
      search_contains : true
    });
  });
  $("form select[id$='midOutcomes']").each(function(){
    $(this).chosen({
      search_contains : true
    });
  });
  $("form select[id^='outputs']").each(function(){
    $(this).chosen({
      search_contains : true
    });
  });
  $("form select[id$='contributions']").each(function(){
    $(this).chosen({
      search_contains : true
    });
  });
}
