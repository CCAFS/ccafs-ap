var lWordsTitle = 15;
var lWordsDesc = 150;
var lWordsLessons = 150;

var $statuses, $statusDescription;

$(document).ready(init);

function init() {
  // Setting initial parameters
  $elementsBlock = $('#activitiesList');
  $elementTemplate = $("#activity-template");
  elementClass = ".activity";
  elementName = $('#activitiesName').val();

  // Add select2 widget to selects
  addSelect2();
  // Add JQuery Calendar widget to start dates and end dates
  setDatadatePicker();
  // Add events for activities section
  attachEvents();

  applyWordCounter($("form .activity input[type=text].title"), lWordsTitle);
  applyWordCounter($("form .activity textarea.description"), lWordsDesc);
  applyWordCounter($("#lessons textarea"), lWordsLessons);

  validateEvent([
    "#justification"
  ]);
}

function attachEvents() {
  // Remove an activity event
  $('.removeElement').on('click', removeElement);
  // Add new activity event
  $('#activities_add a').on('click', addElement);

  $('select.activityStatus').on('change', function(e) {
    var statusId = $(this).val();
    var $statusDescription = $(this).parents('.activity').find('.statusDescription');
    if(isStatusCancelled(statusId) || isStatusExtended(statusId) || isStatusOnGoing(statusId)) {
      $statusDescription.show(400);
      $statusDescription.find('label').html($('#status-' + statusId).html());
    } else {
      $statusDescription.hide(400);
    }
    $statusDescription.find('textarea').val('');
  });

  $('input.endDate').on('change', changeStatus);
  $('input.endDate').trigger('change');
}

function isStatusCancelled(statusId) {
  return(statusId == "5")
}

function isStatusComplete(statusId) {
  return(statusId == "3")
}

function isStatusExtended(statusId) {
  return(statusId == "4")
}

function isStatusOnGoing(statusId) {
  return(statusId == "2")
}

function changeStatus() {
  var d = new Date($(this).val());
  checkImplementationStatus(d.getFullYear(), $(this));
}

function checkImplementationStatus(year,element) {
  var $statuses = $(element).parents('.activity').find('select.activityStatus')
  if(year <= currentReportingYear) {
    $statuses.removeOption(2);
  } else {
    $statuses.addOption(2, "On-going");
  }
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
  $newElement.find('select').select2();
  applyWordCounter($newElement.find(".activity .title"), lWordsTitle);
  applyWordCounter($newElement.find(".activity .description"), lWordsDesc);
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
    // Reporting fields
    $(element).find(".activityStatus").attr("name", name + "activityStatus");
    $(element).find(".activityProgress").attr("name", name + "activityProgress");
  });
}

function setDatadatePicker() {
  $elementsBlock.find(elementClass).each(function(i,element) {
    datePickerConfig($(element).find(".startDate"), $(element).find(".endDate"));
  });
}

function addSelect2() {
  $("form select").select2();
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
