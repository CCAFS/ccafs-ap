$(document).ready(function(){
  
  $(".addCaseStudies").click(function(event){
    event.preventDefault();
    // Cloning template.
    var $newCaseStudy = $("#caseStudy-999").clone(true);
    $("#addCaseStudiesBlock").before($newCaseStudy);
    renameCaseStudies();
    $newCaseStudy.fadeIn("slow");
  });
  
  $('.removeCaseStudy').click(function(event){
    event.preventDefault();
    // Getting the id.
    var removeId = $(event.target).attr("id").split("-")[1];
    $("#caseStudy-" + removeId).fadeOut("slow");
    // removing div.
    $("#caseStudy-" + removeId).hide("slow", function(){
      $(this).remove();
      renameCaseStudies();
    });
  });
  
  $('.checkbox input').change(function(event){
    event.preventDefault();
    // Getting the id.
    var elementId = $(event.target).attr("id").split("[")[1];
    elementId = elementId.split("]")[0];
    // Enable or unable the corresponding select
    if ($(event.target).attr('checked') == "checked") {
      // Hide the countries field
      $("#caseStudies_caseStudies_" + elementId + "__countries").parent().parent().parent().fadeOut("slow");
      $("#caseStudies_caseStudies_" + elementId + "__countries").attr('disabled', true).trigger("liszt:updated");
    } else {
      // Show the countries field
      $("#caseStudies_caseStudies_" + elementId + "__countries").parent().parent().parent().fadeIn("slow");
      $("#caseStudies_caseStudies_" + elementId + "__countries").attr('disabled', false).trigger("liszt:updated");
    }
  });
  
  $(".checkboxGroup input[name$='types']").on("change", checkCaseStudyTypes);
  
  addDatepicker();
  // Activate the chosen plugin to the existing case studies
  addChosen();
  hideCountries();
  
  // character counter to objective text areas.
  $("#caseStudiesGroup, #template").find("[id$='objectives']").each(function(){
    // applyCharCounter($(this), 400);
    applyWordCounter($(this), 100);
  });
  // character counter to description text areas.
  $("#caseStudiesGroup, #template").find("[id$='description']").each(function(){
    // applyCharCounter($(this), 1100);
    applyWordCounter($(this), 300);
  });
  // character counter to results text areas.
  $("#caseStudiesGroup, #template").find("[id$='results']").each(function(){
    // applyCharCounter($(this), 1100);
    applyWordCounter($(this), 300);
  });
  // character counter to partners text areas.
  $("#caseStudiesGroup, #template").find("[id$='partners']").each(function(){
    // applyCharCounter($(this), 250);
    applyWordCounter($(this), 300);
  });
});

// This function checks that a case study doesn't have more than 3 types
// selected
function checkCaseStudyTypes(evt){
  var elementName = $(evt.target).attr("name");
  
  if ($("input[name='" + elementName + "']:checked").length == $("#maxNumberCaseStudyTypes").val()) {
    $("input[name='" + elementName + "']:checkbox:not(:checked)").attr("disabled", true);
  } else if ($("input[name='" + elementName + "']:checked").length < $("#maxNumberCaseStudyTypes").val()) {
    $("input[name='" + elementName + "']").attr("disabled", false);
  }
  
}

// Hide countries field when the case study is global after the page load
function hideCountries(){
  $('.checkbox input').each(function(){
    // Check if the element is not the template
    if ($(this).attr("id") == "caseStudies_types") {
      var elementId = $(this).attr("id").split("[")[1];
      elementId = elementId.split("]")[0];
      if ($(this).attr('checked') == "checked") {
        // Hide the countries field
        $("#caseStudies_caseStudies_" + elementId + "__countries").parent().parent().parent().fadeOut("slow");
      }
    }
  });
}

// Attach the datepicker plugin to the date inputs
function addDatepicker(){
  var defaultMinDateValue = $("#minDateValue").val();
  var defaultMaxDateValue = $("#maxDateValue").val();
  var minDateValue = 0;
  var maxDateValue = 0;
  
  // Start date calendar
  $("[id$='startDate']").each(function(){
    // Check if its the template date field or
    // if the element has the datepicker attached
    if ($(this).attr("id") != 'startDate' && !$(this).hasClass('hasDatepicker')) {
      // Getting the id.
      var elementId = $(this).attr("id").split("[")[1];
      elementId = elementId.split("]")[0];
      if ($("#caseStudies\\[" + elementId + "\\]\\.endDate").val().length != 0) {
        maxDateValue = $("#caseStudies\\[" + elementId + "\\]\\.endDate").val();
      }
      // Add readonly attribute to prevent inappropriate user input
      $(this).attr('readonly', true);
      $(this).datepicker({
        dateFormat : "yy-mm-dd",
        minDate : defaultMinDateValue,
        maxDate : (maxDateValue != 0) ? maxDateValue : defaultMaxDateValue,
        changeMonth : true,
        changeYear : true,
        defaultDate : null,
        onClose : function(selectedDate){
          $("#caseStudies\\[" + elementId + "\\]\\.endDate").datepicker("option", "minDate", selectedDate);
        }
      });
    }
  });
  
  // End date calendar
  $("[id$='endDate']").each(function(){
    // Check if its the template date field or
    // if the element has the datepicker attached
    if ($(this).attr("id") != 'endDate' && !$(this).hasClass('hasDatepicker')) {
      // Getting the id.
      var elementId = $(this).attr("id").split("[")[1];
      elementId = elementId.split("]")[0];
      if ($("#caseStudies\\[" + elementId + "\\]\\.startDate").val() != 0) {
        minDateValue = $("#caseStudies\\[" + elementId + "\\]\\.startDate").val();
      }
      // Add readonly attribute to prevent inappropriate user input
      $(this).attr('readonly', true);
      $(this).datepicker({
        dateFormat : "yy-mm-dd",
        minDate : (minDateValue != 0) ? minDateValue : defaultMinDateValue,
        maxDate : defaultMaxDateValue,
        changeMonth : true,
        changeYear : true,
        defaultDate : null,
        onClose : function(selectedDate){
          $("#caseStudies\\[" + elementId + "\\]\\.startDate").datepicker("option", "maxDate", selectedDate);
        }
      });
    }
  });
}

// Activate the chosen plugin to the countries inputs
function addChosen(){
  $("select[name$='countries']").each(function(){
    // Check if its not the template countries field
    if ($(this).attr("name") != 'countries') {
      $(this).chosen();
    }
  });
}

function renameCaseStudies(){
  // getting the text of the index element.
  var itemText = $("#template").find(".itemIndex").text();
  $("#caseStudiesGroup .caseStudy").each(function(index,caseStudy){
    // Changing attributes of each component in order to match with the array order.
    // Main div.
    var elementName = "caseStudies[" + index + "].";
    $(this).attr("id", "caseStudy-" + index);
    // Item index
    $(this).find(".itemIndex").text(itemText + " " + (index + 1));
    // Remove link.
    $(this).find("[id^='removeCaseStudy-']").attr("id", "removeCaseStudy-" + index);
    
    // Metadata
    $(caseStudy).find("div.metadata input").each(function(i,metadataInput){
      var deliverableName = $(metadataInput).attr("name");
      var firstPart = deliverableName.substring(0, deliverableName.indexOf("[") + 1);
      var secondPart = deliverableName.substr(deliverableName.indexOf("]"));
      $(metadataInput).attr("id", firstPart + index + secondPart);
      $(metadataInput).attr("name", firstPart + index + secondPart);
    });
    
    // case study identifier.
    $(this).find("[name$='].id']").attr("name", elementName + "id");
    // title.
    $(this).find("[id$='title']").attr("id", elementName + "title");
    $(this).find("[name$='title']").attr("name", elementName + "title");
    $(this).find("[for$='title']").attr("for", elementName + "title");
    // Author.
    $(this).find("[id$='author']").attr("id", elementName + "author");
    $(this).find("[name$='author']").attr("name", elementName + "author");
    
    // Type.
    $(this).find("[name$='types']").each(function(index2){
      if (!$(this).is(":hidden")) {
        $(this).attr("id", elementName + "types-" + index2);
        $(this).attr("name", elementName + "types");
        $(this).next(".checkboxLabel").attr('for', elementName + "types-" + index2);
      } else {
        $(this).attr("id", "__multiselect_caseStudies_caseStudies_" + index + "__types");
        $(this).attr("name", "__multiselect_caseStudies[" + index + "].types");
      }
    });
    // Image.
    $(this).find("[id$='image']").attr("id", elementName + "image");
    $(this).find("[name$='image']").attr("name", elementName + "image");
    $(this).find("[for$='image']").attr("for", elementName + "image");
    // Start Date.
    $(this).find("[id$='startDate']").attr("id", elementName + "startDate");
    $(this).find("[name$='startDate']").attr("name", elementName + "startDate");
    // End Date.
    $(this).find("[id$='endDate']").attr("id", elementName + "endDate");
    $(this).find("[name$='endDate']").attr("name", elementName + "endDate");
    // Add the datepicker event
    addDatepicker();
    // is global checkbox
    $(this).find("[name$='global']").attr("id", elementName + "global");
    $(this).find("[name$='global']").attr("name", elementName + "global");
    // Countries.
    $(this).find("[id$='countries']").attr("id", "caseStudies_caseStudies_" + index + "__countries");
    $(this).find("[id$='countries']").attr("name", elementName + "countries");
    // Activate the chosen plugin
    $(this).find("select[id$='countries']").chosen();
    // Keywords.
    $(this).find("[id$='keywords']").attr("id", elementName + "keywords");
    $(this).find("[name$='keywords']").attr("name", elementName + "keywords");
    // Objectives.
    $(this).find("[id$='objectives']").attr("id", elementName + "objectives");
    $(this).find("[name$='objectives']").attr("name", elementName + "objectives");
    // Description.
    $(this).find("[id$='description']").attr("id", elementName + "description");
    $(this).find("[name$='description']").attr("name", elementName + "description");
    // Results.
    $(this).find("[id$='results']").attr("id", elementName + "results");
    $(this).find("[name$='results']").attr("name", elementName + "results");
    // Partners.
    $(this).find("[id$='partners']").attr("id", elementName + "partners");
    $(this).find("[name$='partners']").attr("name", elementName + "partners");
    // Links.
    $(this).find("[id$='links']").attr("id", elementName + "links");
    $(this).find("[name$='links']").attr("name", elementName + "links");
  });
}
