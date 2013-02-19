$(document).ready(function() {

  $(".addCaseStudies").click(function(event) {
    event.preventDefault();
    // Cloning template.
    var $newCaseStudy = $("#caseStudy-999").clone(true);
    $("#addCaseStudiesBlock").before($newCaseStudy);   
    renameCaseStudies();
    $newCaseStudy.fadeIn("slow");
  });

  $('.removeCaseStudy').click(function(event) {
    event.preventDefault();
    // Getting the id.
    var removeId = $(event.target).attr("id").split("-")[1];
    $("#caseStudy-" + removeId).fadeOut("slow");
    // removing div.
    $("#caseStudy-" + removeId).hide("slow", function() {
      $(this).remove();
      renameCaseStudies();
    });
  });
  
  $('.checkbox input').change(function(event){
    event.preventDefault();
    // Getting the id.
    var elementId = $(event.target).attr("id").split("[")[1];
    elementId = elementId.split("]")[0];
    //Enable or unable the corresponding select
    if ($(event.target).attr('checked') == "checked" ){
      // Hide the countries field
      $("#caseStudies_caseStudies_" + elementId + "__countries").parent().parent().parent().fadeOut("slow");
      $("#caseStudies_caseStudies_" + elementId + "__countries").attr('disabled', true).trigger("liszt:updated");
    }else{
      // Show the countries field
      $("#caseStudies_caseStudies_" + elementId + "__countries").parent().parent().parent().fadeIn("slow");
      $("#caseStudies_caseStudies_" + elementId + "__countries").attr('disabled', false).trigger("liszt:updated");      
    }
  });

  addDatepicker();
  // Activate the chosen plugin to the existing case studies  
  addChosen();
  hideCountries();
});

// Hide countries field when the case study is global after the page load
function hideCountries(){
  $('.checkbox input').each(function(){
    // Check if the element is not the template
    if($(this).attr("id") == "caseStudies_types") {
      var elementId = $(this).attr("id").split("[")[1];
      elementId = elementId.split("]")[0];
      if ($(this).attr('checked') == "checked" ){
        // Hide the countries field
        $("#caseStudies_caseStudies_" + elementId + "__countries").parent().parent().parent().fadeOut("slow");   
      }
    }
  });
} 

// Attach the datepicker plugin to the date inputs
function addDatepicker() {
  var defaultMinDateValue = $("#minDateValue").val();
  var defaultMaxDateValue = $("#maxDateValue").val();
  var minDateValue = 0;
  var maxDateValue = 0;

  // Start date calendar
  $("[id$='startDate']").each(function() {
    // Check if its the template date field or
    // if the element has the datepicker attached
    if ($(this).attr("id") != 'startDate' && !$(this).hasClass('hasDatepicker')) {
      //Getting the id.    
      var elementId = $(this).attr("id").split("[")[1];
      elementId = elementId.split("]")[0];
      if( $( "#caseStudies\\[" + elementId + "\\]\\.endDate" ).val().length != 0){        
        maxDateValue = $( "#caseStudies\\[" + elementId + "\\]\\.endDate" ).val();
      }      
      // Add readonly attribute to prevent inappropriate user input
      $(this).attr('readonly', true);
      $(this).datepicker({
        dateFormat : "yy-mm-dd",
        minDate : defaultMinDateValue,
        maxDate : (maxDateValue != 0)? maxDateValue : defaultMaxDateValue,
        changeMonth : true,
        changeYear : true,
        defaultDate : null,
        onClose: function( selectedDate ) {
          $( "#caseStudies\\[" + elementId + "\\]\\.endDate" ).datepicker( "option", "minDate", selectedDate );
        }
      });
    }
  });

  // End date calendar
  $("[id$='endDate']").each(function() {
    // Check if its the template date field or
    // if the element has the datepicker attached
    if ($(this).attr("id") != 'endDate' && !$(this).hasClass('hasDatepicker')) {
      //Getting the id.
      var elementId = $(this).attr("id").split("[")[1];
      elementId = elementId.split("]")[0];
      if($( "#caseStudies\\[" + elementId + "\\]\\.startDate" ).val() != 0){        
        minDateValue = $( "#caseStudies\\[" + elementId + "\\]\\.startDate" ).val();
      }
      // Add readonly attribute to prevent inappropriate user input
      $(this).attr('readonly', true);
      $(this).datepicker({
        dateFormat : "yy-mm-dd",
        minDate : (minDateValue != 0)? minDateValue : defaultMinDateValue,
        maxDate : defaultMaxDateValue,
        changeMonth : true,
        changeYear : true,
        defaultDate : null,
        onClose: function( selectedDate ) {
          $( "#caseStudies\\[" + elementId + "\\]\\.startDate" ).datepicker( "option", "maxDate", selectedDate );
        }
      });
    }
  });
}

// Activate the chosen plugin to the countries inputs
function addChosen() {
  $("select[name$='countries']").each(function() {
    // Check if its not the template countries field
    if ($(this).attr("name") != 'countries') {
      $(this).chosen();
    }
  });
}

function renameCaseStudies() {
  //getting the text of the index element.
  var itemText = $("#template").find(".itemIndex").text();
  $("#caseStudiesGroup .caseStudy").each(
      function(index, caseStudy) {
        // Changing attributes of each component in order to match with the array order.
        // Main div.
        $(this).attr("id", "caseStudy-" + index);
        //Item index
        $(this).find(".itemIndex").text(itemText + " " + (index+1));
        // Remove link.
        $(this).find("[id^='removeCaseStudy-']").attr("id",
            "removeCaseStudy-" + index);
        // case study identifier.
        $(this).find("[name$='id']").attr("name",
            "caseStudies[" + index + "].id");
        // title.
        $(this).find("[id$='title']").attr("id",
            "caseStudies[" + index + "].title");
        $(this).find("[name$='title']").attr("name",
            "caseStudies[" + index + "].title");
        $(this).find("[for$='title']").attr("for",
            "caseStudies[" + index + "].title");
        // Author.
        $(this).find("[id$='author']").attr("id",
            "caseStudies[" + index + "].author");
        $(this).find("[name$='author']").attr("name",
            "caseStudies[" + index + "].author");
        // Type.
        $(this).find("[name$='types']").attr("id",
            "caseStudies[" + index + "].types");
        $(this).find("[name$='types']").attr("name",
            "caseStudies[" + index + "].types");
        // Image.
        $(this).find("[id$='image']").attr("id",
            "caseStudies[" + index + "].image");
        $(this).find("[name$='image']").attr("name",
            "caseStudies[" + index + "].image");
        $(this).find("[for$='image']").attr("for",
            "caseStudies[" + index + "].image");
        // Start Date.
        $(this).find("[id$='startDate']").attr("id",
            "caseStudies[" + index + "].startDate");
        $(this).find("[name$='startDate']").attr("name",
            "caseStudies[" + index + "].startDate");
        // End Date.
        $(this).find("[id$='endDate']").attr("id",
            "caseStudies[" + index + "].endDate");
        $(this).find("[name$='endDate']").attr("name",
            "caseStudies[" + index + "].endDate");
        // Add the datepicker event
        addDatepicker();
        // is global checkbox
        $(this).find("[name$='global']").attr("id",
            "caseStudies[" + index + "].global");
        $(this).find("[name$='global']").attr("name",
            "caseStudies[" + index + "].global");
        // Countries.        
        $(this).find("[id$='countries']").attr("id",
            "caseStudies_caseStudies_" + index + "__countries");
        $(this).find("[id$='countries']").attr("name",
            "caseStudies[" + index + "].countries");
        // Activate the chosen plugin
        $(this).find("select[id$='countries']").chosen();
        // Keywords.
        $(this).find("[id$='keywords']").attr("id",
            "caseStudies[" + index + "].keywords");
        $(this).find("[name$='keywords']").attr("name",
            "caseStudies[" + index + "].keywords");
        // Objectives.
        $(this).find("[id$='objectives']").attr("id",
            "caseStudies[" + index + "].objectives");
        $(this).find("[name$='objectives']").attr("name",
            "caseStudies[" + index + "].objectives");
        // Description.
        $(this).find("[id$='description']").attr("id",
            "caseStudies[" + index + "].description");
        $(this).find("[name$='description']").attr("name",
            "caseStudies[" + index + "].description");
        // Results.
        $(this).find("[id$='results']").attr("id",
            "caseStudies[" + index + "].results");
        $(this).find("[name$='results']").attr("name",
            "caseStudies[" + index + "].results");
        // Partners.
        $(this).find("[id$='partners']").attr("id",
            "caseStudies[" + index + "].partners");
        $(this).find("[name$='partners']").attr("name",
            "caseStudies[" + index + "].partners");
        // Links.
        $(this).find("[id$='links']").attr("id",
            "caseStudies[" + index + "].links");
        $(this).find("[name$='links']").attr("name",
            "caseStudies[" + index + "].links");
      });
}