$(document).ready(function() {
  autoSaveSettings();
  
  // Set the event to radio button
  $(".genderIntegrationOption").on("change", toggleGenderIntegrationText);
  // Hide the genderIntegration area if the radio has value 'no'
  toggleGenderIntegrationText();

  $(".removeContactPerson").on("click", function(event) {
    event.preventDefault();
    $(event.target).parent().parent().hide("slow", function() {
      var elementID = $(event.target).parent().parent().attr("id").split("-")[1];
      $(event.target).parent().parent().remove();
      
      // Set the contact person element to null
      $removedElement = $("#removedContactPerson").clone();
      $removedElement.attr("name", "activity.contactPersons[" + elementID + "]");
      
      $("#removedContactPersons").append($removedElement);
      
    });
  });

  $(".addContactPerson").on("click", function(event) {
    
    event.preventDefault();
    var newContactPerson = $("#contactPersonTemplate").clone(true);
    $("#contactPersonBlock").append(newContactPerson);
    $(newContactPerson).attr("class", "contactPerson");
    newContactPerson.show("slow");
    
    renameContactPerson(newContactPerson);
    
  });
  
  // Set text 'No funds' in budget percentage list
  var nofundsMsg = $("#activity\\.budget\\.noFunds").val();
  $("#mainInformation_activity_budget_cgFund option").first().text(nofundsMsg);
  $("#mainInformation_activity_budget_bilateral option").first().text(nofundsMsg);
  
  // Format the budget field
  $("#activity\\.budget\\.usd").on("keypress", isNumber);

  // Add chosen plugin to the milestone select
  $(".milestones").chosen();
  datePickerConfig();
});


/**
 * This function is in charge of initialize the default autoSave method
 * but also to set the auto save function in the fields that don't use
 * the keyup (default) event.
 */
function autoSaveSettings(){
  setAutoSaveEvents();
  // As some changes are not registered with the keyup event, they should have a
  // different event to start the auto save process
  $("#activity\\.startDate").on("change", getDataModified);
  $("#activity\\.endDate").on("change", getDataModified);
  $(".removeContactPerson").on("click", getDataModified);
  $("#mainInformation_activity_milestone").on("change", getDataModified);
  
  
  // Both field of The contact person information (name and email) should be saved
  // one any of them are modified
  var emailEventTriggered = false,
      nameEventTriggered = false;
  
  $("form input[id$='name']").on("keyup", function(event){ 
    var elementID = $(event.target).attr("id");
    elementID = elementID.split("[")[1].split("]")[0];
    
    if(nameEventTriggered && emailEventTriggered){
      nameEventTriggered = emailEventTriggered = false;
      return false;
    }
    
    nameEventTriggered = true;
    if(!emailEventTriggered){
      $("#activity\\.contactPersons\\[" + elementID + "\\]\\.email").trigger("keyup"); 
    }
  });
  
  $("form input[id$='email']").on("keyup", function(event){ 
    var elementID = $(event.target).attr("id");
    elementID = elementID.split("[")[1].split("]")[0];
    
    if(nameEventTriggered && emailEventTriggered){
      nameEventTriggered = emailEventTriggered = false;
      return false;
    }
    
    emailEventTriggered = true;
    if(!nameEventTriggered){
      $("#activity\\.contactPersons\\[" + elementID + "\\]\\.name").trigger("keyup"); 
    }
  });
}

function renameContactPerson(newContactPerson) {
  var index = $("#contactPersonBlock .contactPerson").length -1;
  
  $(newContactPerson).attr("id", "contactPerson-" + index);
  // Contact person id
  $(newContactPerson).find("[name$='id']").attr("id",
      "activity.contactPersons[" + index + "].id");
  $(newContactPerson).find("[name$='id']").attr("name",
      "activity.contactPersons[" + index + "].id");
  // Contact name
  $(newContactPerson).find("[id$='name']").attr("id",
      "activity.contactPersons[" + index + "].name");
  $(newContactPerson).find("[name$='name']").attr("name",
      "activity.contactPersons[" + index + "].name");
  // Contact email
  $(newContactPerson).find("[id$='email']").attr("id",
      "activity.contactPersons[" + index + "].email");
  $(newContactPerson).find("[name$='email']").attr("name",
      "activity.contactPersons[" + index + "].email");
}

/**
 * Hide or show the gender integration text area according to the value of radio
 * 'genderIntegrationOption'
 * 
 */
function toggleGenderIntegrationText() {
  if ($(".genderIntegrationOption:checked").val() == 0) {
    $(".genderIntegrationsDescription").hide("slow");
    $("#activity\\.genderIntegrationsDescription").text("");
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
  var minDateValue = defaultMinDateValue;
  var maxDateValue = defaultMaxDateValue;

  // Start date calendar
  maxDateValue = $("#activity\\.endDate").val();
  
  // Add readonly attribute to prevent inappropriate user input
  $("#activity\\.startDate").attr('readonly', true);
  var finalMaxDate = (maxDateValue != 0) ? maxDateValue : defaultMaxDateValue; 
  $("#activity\\.startDate").datepicker({
    dateFormat : "yy-mm-dd",
    minDate : defaultMinDateValue,
    maxDate : finalMaxDate,
    changeMonth : true,
    changeYear : true,
    defaultDate : null,
    onClose : function(selectedDate) {
      if(selectedDate != ""){
        $("#activity\\.endDate").datepicker("option", "minDate", selectedDate);
      }
    }
  });

  // End date calendar
  minDateValue = $("#activity\\.startDate").val();
  
  // Add readonly attribute to prevent inappropriate user input
  $("#activity\\.endDate").attr('readonly', true);
  var finalMinDate = (minDateValue != 0) ? minDateValue : defaultMinDateValue;
  $("#activity\\.endDate").datepicker({
    dateFormat : "yy-mm-dd",
    minDate : finalMinDate,
    maxDate : defaultMaxDateValue,
    changeMonth : true,
    changeYear : true,
    defaultDate : null,
    onClose : function(selectedDate) {
      if(selectedDate != ""){
        $("#activity\\.startDate").datepicker("option", "maxDate", selectedDate);
      }
    }
  });
}
