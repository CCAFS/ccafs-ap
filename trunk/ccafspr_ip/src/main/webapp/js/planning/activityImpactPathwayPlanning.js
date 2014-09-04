$(document).ready(init);

function init(){
  addChosen();
  setIndicatorIndexes();
  setMogsIndexes();
  attachEvents();
  initGraph();
}

function attachEvents(){
  $("#activityImpactPathway_midOutcomesList").change(selectMidOutcomeEvent);
  $('input[name^="activity.indicators"]').click(toogleIndicatorInfo);
  $('input[name^="activity.outputs"]').click(selectMogEvent);
  $(".removeContribution").click(removeContributionBlock);
}

function removeContributionBlock(event){
  $(event.target).parent().fadeOut("slow", function(){
    var midOutcomeID = $(this).find("#midOutcomeID").val();
    var programID = $(this).find("#programID").val();
    var description = $(this).find("p.description").text();
    $("#activityImpactPathway_midOutcomesList").append('<option value="' + midOutcomeID + '-' + programID + '">' + description + '</option>');
    $(this).remove();
    $("#activityImpactPathway_midOutcomesList").trigger("liszt:updated");
  });
}

function selectMogEvent(event){
  var $checkbox = $(event.target);
  var $hiddenInput = $checkbox.prev();
  var index = $checkbox.attr("id").split("-")[1];
  var name;
  
  if ($checkbox.is(":checked")) {
    $hiddenInput.attr("disabled", false);
  } else {
    $hiddenInput.attr("disabled", true);
  }
  
  setMogsIndexes();
}

/**
 * This function is called when the user selects an outcome 2019 in order to update the options showed in the MOG select list.
 * 
 * Also are loaded the indicators of the midOutcome selected.
 */
function selectMidOutcomeEvent(event){
  var $midOutcomesSelect = $(event.target);
  var outcomeSelectedVal = $midOutcomesSelect.val();
  var $optionSelected = $midOutcomesSelect.find("option[value='" + outcomeSelectedVal + "']");
  
  if (outcomeSelectedVal == -1)
    return;
  
  // We should separate the identifiers brought in the option value
  // because it comes composed by midOutcomeID-programID
  var midOutcomeID = outcomeSelectedVal.split("-")[0];
  var programID = outcomeSelectedVal.split("-")[1];
  
  var $newContribution = $("#contributionTemplate").clone(true);
  var $mogBlock = $newContribution.find(".mogsBlock");
  var $indicatorsBlock = $newContribution.find(".indicatorsBlock");
  
  // Add the midOutcome id
  $newContribution.find("#midOutcomeID").val(midOutcomeID);
  
  // Add the program id
  $newContribution.find("#programID").val(programID);
  
  // Add the midOutcome description
  $newContribution.find(".midOutcomeTitle p.description").html($optionSelected.text());
  
  // Add the elements into the contributesBlock
  addMOGs(midOutcomeID, $mogBlock);
  addIndicators(midOutcomeID, programID, $indicatorsBlock);
  
  // Remove the option selected from the select list
  $optionSelected.remove();
  
  // Remove the id, because is *template
  $newContribution.removeAttr("id");
  $("#contributionsBlock").append($newContribution);
  
  $newContribution.show("slow");
  $midOutcomesSelect.trigger("liszt:updated");
}

/**
 * This function load the MOGs which contributes to the midOutcome identified by the value received as parameter and put them in the interface as a list of checkboxes
 * 
 * @param midOutcomeID - midOutcome identifier
 */
function addMOGs(midOutcomeID,$mogBlock){
  
  var source = "../../../json/ipElementsByParent.do?elementID=" + midOutcomeID;
  $.getJSON(source).done(function(data){
    var $mogTemplate = $mogBlock.find("#mogTemplate");
    
    // Add the options received
    $.each(data.IPElementsList, function(index,mog){
      var $newMog = $mogTemplate.clone(true);
      $newMog.removeAttr("id");
      
      $newMog.find("input[name$='contributesTo[0].id'] ").val(midOutcomeID);
      $newMog.find("input[type='checkbox']").val(mog.id);
      $newMog.find("label").html(mog.description);
      
      $mogBlock.append($newMog);
    });
    
    // Once we have copied all the elements needed, remove the template
    $mogTemplate.remove();
    setMogsIndexes();
  }).fail(function(){
    console.log("error");
  });
}

/**
 * This function load the indicators which belongs to the midOutcome identified by the value received as parameter and put them in the interface as a list of checkboxes
 * 
 * @param midOutcomeID - midOutcome identifier
 * @param programID - the program to which the midOutcome belongs to
 * @param $indicatorsBlock - a jquery object that contains the block where the indicators should be append
 */
function addIndicators(midOutcomeID,programID,$indicatorsBlock){
  var source = "../../../json/ipIndicators.do?elementID=" + midOutcomeID + "&programID=" + programID;
  
  $.getJSON(source).done(function(data){
    var $indicatorTemplate = $indicatorsBlock.find("#midOutcomeIndicatorTemplate");
    
    // Iterate the list of indicators received.
    $.each(data.IPElementsList, function(index,indicator){
      var $newIndicator = $indicatorTemplate.clone(true);
      $newIndicator.removeAttr("id");
      
      // indexes will be adjusted in function setIndicatorsIndexes
      $newIndicator.find("input[type='checkbox']").val(indicator.id);
      if (!(indicator.parent)) {
        $newIndicator.find("label.indicatorDescription").text(indicator.description);
        console.log("Loading indicator for FP");
      } else {
        $newIndicator.find("label.indicatorDescription").text(indicator.parent.description);
        console.log("Loading indicator for RP");
      }
      $indicatorsBlock.append($newIndicator);
    });
    
    // Once we have added all the indicators, remove the template
    $indicatorTemplate.remove();
    setIndicatorIndexes();
  }).fail(function(){
    console.log("error");
  });
}

function setIndicatorIndexes(){
  var $contributionsBlock = $("#contributionsBlock");
  var indicatorsName = "activity.indicators";
  
  // Indicators indexes
  $contributionsBlock.find(".midOutcomeIndicator").each(function(index,indicator){
    // Checkbox
    $(indicator).find("input[type='checkbox']").attr("id", "activity.indicators-" + index);
    $(indicator).find("input[type='checkbox']").attr("name", indicatorsName + "[" + index + "].parent.id");
    
    if ($(indicator).find("input[type='checkbox']").is(":checked")) {
      
      $(indicator).find("input[type='hidden']").attr("disabled", false);
      $(indicator).find(".indicatorNarrative input").attr("name", indicatorsName + "[" + index + "].target");
      $(indicator).find(".indicatorNarrative textarea").attr("name", indicatorsName + "[" + index + "].description");
      
    } else {
      
      $(indicator).find("input[type='hidden']").attr("disabled", true);
      $(indicator).find(".indicatorNarrative input").attr("name", "");
      $(indicator).find(".indicatorNarrative textarea").attr("name", "");
      
    }
    
    // Hidden
    $(indicator).find("input[type='hidden']").attr("id", "activity.indicators-" + index);
    $(indicator).find("input[type='hidden']").attr("name", indicatorsName + "[" + index + "].id");
    
    // Label
    $(indicator).find("label").attr("for", "activity.indicators-" + index);
  });
}

function setMogsIndexes(){
  var $contributionsBlock = $("#contributionsBlock");
  // Indicators indexes
  $contributionsBlock.find(".mog").each(function(index,mog){
    var mogsName = "activity.outputs[" + index + "]";
    
    // Checkbox
    $(mog).find("input[type='checkbox']").attr("id", "mog-" + index);
    $(mog).find("input[type='checkbox']").attr("name", mogsName + ".id");
    
    // Hidden input
    $(mog).find("input[type='hidden']").attr("name", mogsName + ".contributesTo[0].id");
    if ($(mog).find("input[type='checkbox']").is(":checked")) {
      $(mog).find("input[type='hidden']").attr("disabled", false);
    } else {
      $(mog).find("input[type='hidden']").attr("disabled", true);
    }
    
    // Label
    $(mog).find("label").attr("for", "mog-" + index);
  });
}

function toogleIndicatorInfo(event){
  var $indicatorBlock = $(event.target).parent();
  var indicatorIndex = $(event.target).attr("name").split("[")[1].split("]")[0];
  var indicatorsName = "activity.indicators[" + indicatorIndex + "]";
  
  if (event.target.checked) {
    $indicatorBlock.find("input[type='hidden']").attr("disabled", false);
    $indicatorBlock.find(".indicatorNarrative input").attr("name", indicatorsName + ".target");
    $indicatorBlock.find(".indicatorNarrative textarea").attr("name", indicatorsName + ".description");
    
    // Show the block
    $indicatorBlock.find(".indicatorNarrative").show("slow");
  } else {
    $indicatorBlock.find("input[type='hidden']").attr("disabled", true);
    $indicatorBlock.find(".indicatorNarrative input").attr("name", "");
    $indicatorBlock.find(".indicatorNarrative textarea").attr("name", "");
    
    // Hide the block
    $indicatorBlock.find(".indicatorNarrative").hide("slow");
  }
}

function addChosen(){
  $("select").chosen();
}
