var leveragesName;
var $elementsBlock;

$(document).ready(init);

function init() {
  // Set initial variables
  $elementsBlock = $('#leveragesBlock');
  leveragesName = $('#leveragesName').val();
  // Add events
  attachEvents();
  // Add JQuery Calendar widget to start dates and end dates
  setDatadatePicker();
  // Validate justification event
  validateEvent([
    "#justification"
  ]);
  addSelect2();
}

function attachEvents() {
  // Remove a next user event
  $('.removeElement').on('click', removeElement);
  // Add new next user event
  $('#addLeverage .addButton').on('click', addElement);

}

function removeElement(e) {
  e.preventDefault();
  $(e.target).parent().hide('slow', function() {
    $(this).remove();
    setElementsIndexes();
  });
}

function addElement(e) {
  e.preventDefault();
  var $newElement = $('#leverage-template').clone(true).removeAttr("id");
  $elementsBlock.append($newElement.fadeIn("slow"));
  $newElement.find('select').select2();
  setElementsIndexes();
}

function setElementsIndexes() {
  $elementsBlock.find('.leverage').each(setElementIndex);
}

function setElementIndex(i,element) {
  var name = leveragesName + "[" + i + "].";
  $(element).find("span.index").html(i + 1);

  $(element).find(".leverageID").attr("name", name + "id");
}

function addSelect2() {
  $('form select').select2();
}

function setDatadatePicker() {
  $elementsBlock.find('.leverage').each(function(i,element) {
    datePickerConfig($(element).find(".startDate"), $(element).find(".endDate"));
  });
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
      minDate: '2015-01-01',
      maxDate: '2019-12-12',
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
      minDate: '2015-01-01',
      maxDate: '2019-12-12',
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