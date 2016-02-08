var leveragesName;
var $elementsBlock;

$(document).ready(init);

function init() {
  // Set initial variables
  $elementsBlock = $('#leveragesBlock');
  $budgetInputs = $('.budget.budgetInput');
  leveragesName = $('#leveragesName').val();
  // Add events
  attachEvents();
  // Add JQuery Calendar widget to start dates and end dates
  setDatadatePicker();
  // Popup for partners
  popups();
  // Validate justification event
  /*
   * validateEvent([ "#justification" ]);
   */
  addSelect2();
}

function attachEvents() {
  // Remove a next user event
  $('.removeElement').on('click', removeElement);
  // Add new next user event
  $('#addLeverage .addButton').on('click', addElement);

  $budgetInputs.on("keydown", isNumber).on("focusout", setCurrency).on("focus", removeCurrency).on("click", function() {
    $(this).select();
  }).on("keyup", function(e) {
    // any special calculation here
  });
  // Active initial currency format to all inputs once
  $budgetInputs.attr("autocomplete", "off").trigger("focusout");

  $("form").submit(function(event) {
    $budgetInputs = $('.budgetInput');
    $budgetInputs.each(function() {
      $(this).val(removeCurrencyFormat($(this).val())).attr("readonly", true);
    });
    return;
  });

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
  setElementsIndexes();
  $newElement.find('select').select2();
  datePickerConfig($newElement.find(".startDate"), $newElement.find(".endDate"));
}

function setElementsIndexes() {
  $elementsBlock.find('.leverage').each(setElementIndex);
}

function setElementIndex(i,element) {
  var name = leveragesName + "[" + i + "].";
  $(element).find("span.index").html(i + 1);

  $(element).find(".leverageID").attr("name", name + "id");
  $(element).find(".leverageTitle").attr("name", name + "title");
  $(element).find(".institutionsList").attr("name", name + "institution");
  $(element).find(".startDate").attr("name", name + "startDateText").attr("id", name + "startDate");
  $(element).find(".endDate").attr("name", name + "endDateText").attr("id", name + "endDate");
  $(element).find(".flagship").attr("name", name + "flagship");
  $(element).find(".budget").attr("name", name + "budget");
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

/**
 * Currency functions
 */

function setCurrency(e) {
  var $input = $(e.target);
  if($input.val().length == 0) {
    $input.val("0");
  }
  console.log($input.val());
  $input.val(setCurrencyFormat($input.val()));
}

function removeCurrency(event) {
  var $input = $(event.target);
  $input.val(removeCurrencyFormat($input.val()));
  if($input.val() == "0") {
    $input.val("");
  }
}