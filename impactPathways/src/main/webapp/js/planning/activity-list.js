$(document).ready(init);

function init() {
  // Setting initial parameters
  $elementsBlock = $('#activitiesList');
  $elementTemplate = $("#activity-template");
  elementClass = ".activity";
  elementName = $('#activitiesName').val();
  // Add chose widget to selects
  addChosen();
  // Add JQuery Calendar widget to start dates and end dates
  setDatadatePicker();
  // Add events for activities section
  attachEvents();

  validateEvent([
    "#justification"
  ]);
}

function attachEvents() {
  // Remove an activity event
  $('.removeElement').on('click', removeElement);
  // Add new activity event
  $('#activities_add').on('click', addElement);
}

function removeElement(e) {
  e.preventDefault();
  $(e.target).parent().hide('slow', function() {
    $(this).remove();
    setActivitiesIndexes();
  });
}

function addElement(e) {
  e.preventDefault();
  var $newElement = $elementTemplate.clone(true).removeAttr("id");
  $elementsBlock.append($newElement.fadeIn("slow"));
  setActivitiesIndexes();
  addChosen();
  datePickerConfig($newElement.find(".startDate"), $newElement.find(".endDate"));
  $newElement.find('textarea').autoGrow();
  $elementsBlock.find('p.emptyText').fadeOut();
}

function setActivitiesIndexes() {
  $elementsBlock.find(elementClass).each(function(i,element) {
    var name = elementName + "[" + i + "].";
    $(element).attr("id", "activity-" + i);
    $(element).find("span.index").html(i + 1);
    $(element).find(".id").attr("name", name + "id");
    $(element).find(".title").attr("name", name + "title");
    $(element).find(".description").attr("name", name + "description");
    $(element).find(".startDate").attr("name", name + "startDate").attr("id", name + "startDate");
    $(element).find(".endDate").attr("name", name + "endDate").attr("id", name + "endDate");
    $(element).find(".leader").attr("name", name + "leader");
  });
}

function setDatadatePicker() {
  $elementsBlock.find(elementClass).each(function(i,element) {
    datePickerConfig($(element).find(".startDate"), $(element).find(".endDate"));
  });
}

function addChosen() {
  $elementsBlock.find("select").chosen({
    search_contains: true
  });
}

function datePickerConfig($startDate,$endDate) {
  var defaultMinDateValue = "2010-01-01";
  var defaultMaxDateValue = "2015-12-31";
  var minDateValue = defaultMinDateValue;
  var maxDateValue = defaultMaxDateValue;

  // Start date calendar
  maxDateValue = $endDate.val();

  // Add readonly attribute to prevent inappropriate user input
  // $startDate.attr('readonly', true);
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
  // $endDate.attr('readonly', true);
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
          $startDate.datepicker("option", "maxDate", selectedDate);
        }
      }
  });
}
