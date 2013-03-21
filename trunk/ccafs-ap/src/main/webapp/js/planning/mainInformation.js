$(document).ready(function() {

  // Set the event to radio button
  $(".genderIntegrationOption").on("change", toggleGenderIntegrationText);
  // Hide the genderIntegration area if the radio has value 'no'
  toggleGenderIntegrationText();

  $(".removeContactPerson").on("click", function(event) {
    event.preventDefault();
    $(event.target).parent().parent().hide("slow", function() {
      $(event.target).parent().parent().remove();
      renameContactPersons();
    });
  });

  $(".addContactPerson").on("click", function(event) {
    event.preventDefault();
    var newContactPerson = $("#contactPersonTemplate").clone(true);
    $("#contactPersonBlock").append(newContactPerson);
    $(newContactPerson).attr("class", "contactPerson");
    renameContactPersons();
    newContactPerson.show("slow");
  });

  datePickerConfig();
});

function renameContactPersons() {
  $(".contactPerson").each(
      function(index, contactPerson) {
        // Block id
        $(this).attr("id", "contactPerson-" + index);
        // Contact name
        $(this).find("[id$='name']").attr("id",
            "activity\\.contactPersons[" + index + "]\\.name");
        $(this).find("[name$='name']").attr("name",
            "activity\\.contactPersons[" + index + "]\\.name");
        // Contact email
        $(this).find("[id$='email']").attr("id",
            "activity\\.contactPersons[" + index + "]\\.email");
        $(this).find("[name$='email']").attr("name",
            "activity\\.contactPersons[" + index + "]\\.email");
      });
}

/**
 * Hide or show the gender integration text area according to the value of radio
 * 'genderIntegrationOption'
 * 
 */
function toggleGenderIntegrationText() {
  if ($(".genderIntegrationOption:checked").val() == 0) {
    $(".genderIntegrationsDescription").hide("slow");
  } else {
    $(".genderIntegrationsDescription").show("slow");
  }
}

/**
 * Attach to the date fields the datepicker plugin
 * 
 */
function datePickerConfig() {
  var defaultMinDateValue = $("#minDateValue").val();
  var defaultMaxDateValue = $("#maxDateValue").val();
  var minDateValue = 0;
  var maxDateValue = 0;

  // Start date calendar
  if ($("#activity\\.endDate").val().length != 0) {
    maxDateValue = $("#activity\\.endDate").val();
  }
  // Add readonly attribute to prevent inappropriate user input
  $("#activity\\.startDate").attr('readonly', true);
  $("#activity\\.startDate").datepicker({
    dateFormat : "yy-mm-dd",
    minDate : defaultMinDateValue,
    maxDate : (maxDateValue != 0) ? maxDateValue : defaultMaxDateValue,
    changeMonth : true,
    changeYear : true,
    defaultDate : null,
    onClose : function(selectedDate) {
      $("#activity\\.endDate").datepicker("option", "minDate", selectedDate);
    }
  });

  // End date calendar
  if ($("#activity\\.startDate").val() != 0) {
    minDateValue = $("#activity\\.startDate").val();
  }
  // Add readonly attribute to prevent inappropriate user input
  $("#activity\\.endDate").attr('readonly', true);
  $("#activity\\.endDate").datepicker({
    dateFormat : "yy-mm-dd",
    minDate : (minDateValue != 0) ? minDateValue : defaultMinDateValue,
    maxDate : defaultMaxDateValue,
    changeMonth : true,
    changeYear : true,
    defaultDate : null,
    onClose : function(selectedDate) {
      $("#activity.startDate").datepicker("option", "maxDate", selectedDate);
    }
  });
}
