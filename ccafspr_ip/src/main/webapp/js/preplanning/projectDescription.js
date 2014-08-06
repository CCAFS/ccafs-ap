$(document).ready(function(){
  datePickerConfig({
    "startDate" : "#project\\.startDate",
    "endDate" : "#project\\.endDate",
    defaultMinDateValue : $("#minDateValue").val(),
    defaultMaxDateValue : $("#maxDateValue").val()
  });
  setProgramId();
  addChosen();
});

/**
 * Attach to the date fields the datepicker plugin
 * 
 */
function datePickerConfig(element){
  var defaultMinDateValue = element.defaultMinDateValue;
  var defaultMaxDateValue = element.defaultMaxDateValue;
  var minDateValue = defaultMinDateValue;
  var maxDateValue = defaultMaxDateValue;
  
  // Start date calendar
  maxDateValue = $(element.endDate).val();
  
  // Add readonly attribute to prevent inappropriate user input
  // $(element.startDate).attr('readonly', true);
  var finalMaxDate = (maxDateValue != 0) ? maxDateValue : defaultMaxDateValue;
  $(element.startDate).datepicker({
    dateFormat : "yy-mm-dd",
    minDate : defaultMinDateValue,
    maxDate : finalMaxDate,
    changeMonth : true,
    changeYear : true,
    defaultDate : null,
    onClose : function(selectedDate){
      if (selectedDate != "") {
        $(element.endDate).datepicker("option", "minDate", selectedDate);
      }
    }
  });
  
  // End date calendar
  minDateValue = $(element.startDate).val();
  
  // Add readonly attribute to prevent inappropriate user input
  // $(element.endDate).attr('readonly', true);
  var finalMinDate = (minDateValue != 0) ? minDateValue : defaultMinDateValue;
  $(element.endDate).datepicker({
    dateFormat : "yy-mm-dd",
    minDate : finalMinDate,
    maxDate : defaultMaxDateValue,
    changeMonth : true,
    changeYear : true,
    defaultDate : null,
    onClose : function(selectedDate){
      if (selectedDate != "") {
        $(element.startDate).datepicker("option", "maxDate", selectedDate);
      }
    }
  });
}

// Activate the chosen plugin.
function addChosen(){
  $("form select").chosen({
    search_contains : true
  });
  
}

function setProgramId(){
  var programId = $("input#programID").val();
  $("input[value='" + programId + "'][name$='regions'], input[value='" + programId + "'][name$='flagships']").attr("checked", true);
}
