$(document).ready(function() {
  
  $("#addActivity_activity_continuousActivity").on("change", checkIsContinuation);
  
  // If the activity is commisioned change the leader
  $("#addActivity_activity_leader_commisioned").on('change', changeLeader);
  
  datePickerConfig();
});


/**
 * If a TL/RPL commision an activity so the leader must change.
 * 
 * @param event
 */
function changeLeader(event){
  var element = $(event.target);

  if($(element).val() == -1){
    $("#addActivity_activity_leader").attr("name", "activity.leader");
    $("#addActivity_activity_leader_commisioned").attr("name", "activity.leader.null");
  }else{
    $("#addActivity_activity_leader").attr("name", "activity.leader.null");
    $("#addActivity_activity_leader_commisioned").attr("name", "activity.leader");
  }
}

/**
 * If the activity is a continuation, we don't need the dates
 */
function checkIsContinuation(event){
  var element = $(event.target);
  if($(element).val() == -1){
    $("#datesBlock").fadeIn("slow");
    // Enable the dates inputs 
    $("#activity\\.startDate").attr("disabled", false);
    $("#activity\\.endDate").attr("disabled", false);
  }else{
    // Disable the dates inputs 
    $("#activity\\.startDate").prop("disabled", true);
    $("#activity\\.endDate").prop("disabled", true);
    $("#datesBlock").fadeOut("slow");
  }
  
  console.log($(element).val());
}


/**
 * Attach to the date fields the datepicker plugin
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
