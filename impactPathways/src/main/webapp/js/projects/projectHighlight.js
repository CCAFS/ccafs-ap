$(document).ready(function() {

  // Activate the select2 plugin to the existing case studies
  addSelect2();
  // Add JQuery Calendar widget to start dates and end dates
  datePickerConfig($("form input.startDate"), $("form input.endDate"));
  // Set word limits to inputs that contains class limitWords-value, for example : <input class="limitWords-100" />
  setWordCounterToInputsList();

});

function setWordCounterToInputsList() {
  // Attribute contains certain value somewhere -> [class*="limitWords"]
  $('[class*="limitWords"]').each(function(i,input) {
    var check = "limitWords-";
    var className = $(input).attr('class') || '';
    var cls = $.map(className.split(' '), function(val,i) {
      if(val.indexOf(check) > -1) {
        return val.slice(check.length, val.length);
      }
    });
    applyWordCounter($(input), (cls.join(' ')) || 100);
  });
}

function addSelect2() {
  $('form select').select2();
}

function datePickerConfig($startDate,$endDate) {
  var defaultMinDateValue = $("#minDateValue").val();
  var defaultMaxDateValue = $("#maxDateValue").val();
  var minDateValue = defaultMinDateValue;
  var maxDateValue = defaultMaxDateValue;

  // Start date calendar
  maxDateValue = $endDate.val();

  // Add readonly attribute to prevent inappropriate user input
  $startDate.on('keydown', function(e) {
    e.preventDefault();
  });
  var finalMaxDate = (maxDateValue != 0) ? maxDateValue : defaultMaxDateValue;
  $startDate.datepicker({
      dateFormat: "yy-mm-dd",
      minDate: defaultMinDateValue,
      maxDate: finalMaxDate,
      changeMonth: true,
      changeYear: true,
      defaultDate: null,
      onClose: function(selectedDate) {
        if(selectedDate != "") {
          $endDate.datepicker("option", "minDate", selectedDate);
        }
      }
  });

  // End date calendar
  minDateValue = $startDate.val();

  // Add readonly attribute to prevent inappropriate user input
  $endDate.on('keydown', function(e) {
    e.preventDefault();
  });
  var finalMinDate = (minDateValue != 0) ? minDateValue : defaultMinDateValue;
  $endDate.datepicker({
      dateFormat: "yy-mm-dd",
      minDate: finalMinDate,
      maxDate: defaultMaxDateValue,
      changeMonth: true,
      changeYear: true,
      defaultDate: null,
      onClose: function(selectedDate) {
        if(selectedDate != "") {
          // $startDate.datepicker("option", "maxDate", selectedDate);
        }
      }
  });
}