// Global vars
var $midOutcomesList, $targetValue, $midOutcomesList;

$(document).ready(init);

function init() {
  $contributionsBlock = $('#contributionsBlock');
  $midOutcomesList = $('#ccafsOutcomes_midOutcomesList');
  $targetValue = $('.projectIndicatorTarget');
  setWordCounters();
  setIndicatorIndexes();
  setMogsIndexes();
  attachEvents();
  addChosen();
  addTabs();
  initGraph();
  checkOutcomes();

  // Regenerating initial hash
  setFormHash();
}

function attachEvents() {
  $midOutcomesList.change(selectMidOutcomeEvent);
  $('.projectIndicatorCheckbox').click(toogleIndicatorInfo);
  $('input[name^="project.outputs"]').click(selectMogEvent);
  $(".removeContribution").click(removeContributionBlock);
  $targetValue.on("keydown", function(event) {
    isNumber(event);
  });
  validateEvent([
    "#justification"
  ]);
}

function setWordCounters() {
  var check = "limitWords-";
  $("textarea[id!='justification']").each(function(i,textarea) {
    var className = $(textarea).attr('class') || '';
    var cls = $.map(className.split(' '), function(val,i) {
      if(val.indexOf(check) > -1) {
        return val.slice(check.length, val.length);
      }
    });
    applyWordCounter($(textarea), (cls.join(' ')) || 100);

  });
}

function checkOutcomes() {
  var outcomes = $midOutcomesList.find('option').length;
  var contributions = $contributionsBlock.find('.contribution').length;
  if((outcomes == 2) && (contributions == 0)) {
    addOutcome($($midOutcomesList.find('option')[1]).val());
  }
}

function removeContributionBlock(event) {
  $(event.target).parent().fadeOut("slow", function() {
    var midOutcomeID = $(this).find("#midOutcomeID").val();
    var programID = $(this).find("#programID").val();
    var description = $(this).find("p.description").text();
    $midOutcomesList.append('<option value="' + midOutcomeID + '-' + programID + '">' + description + '</option>');
    $(this).remove();
    $midOutcomesList.trigger("liszt:updated");
    setIndicatorIndexes();
  });
}

function selectMogEvent(event) {
  var $checkbox = $(event.target);
  var $hiddenInput = $checkbox.prev();
  // var index = $checkbox.attr("id").split("-")[1];
  // var name;

  if($checkbox.is(":checked")) {
    $hiddenInput.attr("disabled", false);
  } else {
    $hiddenInput.attr("disabled", true);
  }

  setMogsIndexes();
}

/**
 * This function is called when the user selects an outcome 2019 in order to update the options showed in the MOG select
 * list. Also are loaded the indicators of the midOutcome selected.
 */
function selectMidOutcomeEvent(event) {
  addOutcome($midOutcomesList.val());
}

function addOutcome(outcomeSelectedVal) {
  var $optionSelected = $midOutcomesList.find("option[value='" + outcomeSelectedVal + "']");
  if(outcomeSelectedVal == -1) {
    return;
  }
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
  $("#contributionsBlock").append($newContribution).find('.emptyText').hide();

  $newContribution.show("slow", function() {
    $newContribution.find(".indicatorTargetsTemplate").removeClass().addClass("indicatorTargets").tabs();
  });

  $midOutcomesList.trigger("liszt:updated");
}

/**
 * This function load the MOGs which contributes to the midOutcome identified by the value received as parameter and put
 * them in the interface as a list of checkboxes
 * 
 * @param midOutcomeID - midOutcome identifier
 */
function addMOGs(midOutcomeID,$mogBlock) {

  var source = "../../json/mogsByOutcome.do?elementID=" + midOutcomeID;
  $.getJSON(source).done(function(data) {
    var $mogTemplate = $mogBlock.find("#mogTemplate");

    // Add the options received
    $.each(data.IPElementsList, function(index,mog) {
      var $newMog = $mogTemplate.clone(true);
      $newMog.removeAttr("id");

      $newMog.find("input[name$='contributesTo[0].id'] ").val(midOutcomeID);
      $newMog.find("input[type='checkbox']").val(mog.id);

      $newMog.find("label").html(mog.program.acronym + " - MOG #" + (index) + ": " + mog.description);

      $mogBlock.append($newMog);
    });

    // Once we have copied all the elements needed, remove the template
    $mogTemplate.remove();

    setMogsIndexes();
  }).fail(function() {
    console.log("error");
  }).always(function() {
    // Hide ajax-loader icon
    $mogBlock.find(".ajax-loader").fadeOut("slow");
  });
}

/**
 * This function load the indicators which belongs to the midOutcome identified by the value received as parameter and
 * put them in the interface as a list of checkboxes
 * 
 * @param midOutcomeID - midOutcome identifier
 * @param programID - the program to which the midOutcome belongs to
 * @param $indicatorsBlock - a jquery object that contains the block where the indicators should be append
 */
function addIndicators(midOutcomeID,programID,$indicatorsBlock) {
  var source = "../../json/ipIndicators.do?elementID=" + midOutcomeID + "&programID=" + programID;
  var onlyOneIndicator = false;
  $.getJSON(source).done(function(data) {
    var $indicatorTemplate = $indicatorsBlock.find("#midOutcomeIndicatorTemplate");
    if(data.IPElementsList.length == 1) {
      onlyOneIndicator = true;
    }
    // Iterate the list of indicators received.
    $.each(data.IPElementsList, function(index,indicator) {
      var $newIndicator = $indicatorTemplate.clone(true);
      $newIndicator.removeAttr("id");

      // indexes will be adjusted in function setIndicatorsIndexes
      $newIndicator.find("input[type='checkbox']").val(indicator.id);
      $newIndicator.find("input.projectIndicatorOutcome").val(midOutcomeID);

      if(!(indicator.parent)) {
        $newIndicator.find("input.projectIndicatorParent").val(indicator.id);
        $newIndicator.find("label.indicatorDescription").text(indicator.description);
      } else {
        $newIndicator.find("input.projectIndicatorParent").val(indicator.parent.id);
        $newIndicator.find("label.indicatorDescription").text(indicator.parent.description);
      }
      $indicatorsBlock.append($newIndicator);

      // If the there is only one indicator, the target must be already selected.
      if(onlyOneIndicator) {
        $newIndicator.find("input[type='hidden']").attr("disabled", false);
        $newIndicator.find("input[type='checkbox']").attr("checked", true);
        $newIndicator.find(".indicatorNarrative, .indicatorTargets, .indicatorTargetsTemplate").show("slow");
      }
    });

    // Once we have added all the indicators, remove the template
    $indicatorTemplate.remove();
    setIndicatorIndexes();
  }).fail(function() {
    console.log("error");
  }).always(function() {
    // Hide ajax-loader icon
    $indicatorsBlock.find(".ajax-loader").fadeOut("slow");
  });
}

function setIndicatorIndexes() {
  var $contributionsBlock = $("#contributionsBlock");
  var indicatorsName = "project.indicators";
  var index = 0;
  // Indicators indexes
  $contributionsBlock.find(".midOutcomeIndicator").each(function(indicatorIndex,indicator) {
    $(indicator).find(".targetIndicator").each(function(targetIndex,target) {

      // Label
      $(indicator).find("label").attr("for", indicatorsName + "-" + index);

      // Checkbox
      $(indicator).find("input[type='checkbox']").attr("id", "indicatorIndex-" + indicatorIndex);
      if($(indicator).find("input[type='checkbox']").is(":checked")) {

        $(target).find("input.projectIndicatorParent").attr("name", indicatorsName + "[" + index + "].parent.id");
        $(target).find("input[type='hidden']").attr("disabled", false);
        $(target).find(".projectIndicatorID").attr("id", indicatorsName + "-" + indicatorIndex);
        $(target).find(".projectIndicatorID").attr("name", indicatorsName + "[" + index + "].id");
        $(target).find(".projectIndicatorYear").attr("name", indicatorsName + "[" + index + "].year");
        $(target).find(".projectIndicatorOutcome").attr("name", indicatorsName + "[" + index + "].outcome.id");
        $(target).find(".projectIndicatorTarget").attr("name", indicatorsName + "[" + index + "].target");
        $(target).find(".projectIndicatorDescription").attr("name", indicatorsName + "[" + index + "].description");

        index++;
      } else {

        $(target).find("input[type='hidden']").attr("disabled", true);
        $(target).find(".indicatorNarrative textarea").attr("name", "");

      }

    });

  });
}

function setMogsIndexes() {
  var $contributionsBlock = $("#contributionsBlock");
  // Indicators indexes
  $contributionsBlock.find(".mog").each(function(index,mog) {
    var mogsName = "project.outputs[" + index + "]";

    // Checkbox
    $(mog).find("input[type='checkbox']").attr("id", "mog-" + index);
    $(mog).find("input[type='checkbox']").attr("name", mogsName + ".id");

    // Hidden input
    $(mog).find("input[type='hidden']").attr("name", mogsName + ".contributesTo[0].id");
    if($(mog).find("input[type='checkbox']").is(":checked")) {
      $(mog).find("input[type='hidden']").attr("disabled", false);
    } else {
      $(mog).find("input[type='hidden']").attr("disabled", true);
    }

    // Label
    $(mog).find("label").attr("for", "mog-" + index);
  });
}

function toogleIndicatorInfo(event) {
  var $indicatorBlock = $(event.target).parent();

  if(event.target.checked) {
    $indicatorBlock.find("input[type='hidden']").attr("disabled", false);

    // Show the block
    $indicatorBlock.find(".indicatorNarrative, .indicatorTargets").show("slow");
  } else {
    $indicatorBlock.find("input[type='hidden']").attr("disabled", true);

    // Hide the block
    $indicatorBlock.find(".indicatorNarrative, .indicatorTargets").hide("slow");
  }
  setIndicatorIndexes();
}

function addChosen() {
  $("select").chosen();
}

function addTabs() {
  $("form .indicatorTargets").tabs();
}
