$(document).ready(function() {
  
  $("#continuousActivity").on("change", showActivityList);
  $("#activity\\.commissioned").on('change', showLeaderList);
  $("#addActivity_activity_commissioned").on('change', changeLeader);
  
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
    $("#addActivity_activity_commissioned").attr("name", "activity.leader.null");
  }else{
    $("#addActivity_activity_leader").attr("name", "activity.leader.null");
    $("#addActivity_activity_commissioned").attr("name", "activity.leader");
  }
}

/**
 * If the user indicates that the new activity is a continuation
 * show the list of previous activities
 */
function showActivityList(event){
  var element = event.target;
  if($(element).prop("checked")){
    $("#addActivity_activity_continuousActivity").parent().parent().fadeIn();
  }else{
    $("#addActivity_activity_continuousActivity").parent().parent().fadeOut();
    $("#addActivity_activity_continuousActivity").val(-1);
  }
}

/**
 * If the user indicates that the new activity will be commisioned
 * show the list of activity leaders
 */
function showLeaderList(event){
  var element = event.target;
  if($(element).prop("checked")){
    $("#addActivity_activity_commissioned").parent().parent().fadeIn();
  }else{
    $("#addActivity_activity_commissioned").parent().parent().fadeOut();
    $("#addActivity_activity_commissioned").val(-1);
  }
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
