// Limits for textarea input
var lWordsElemetTitle = 50;
var lWordsElemetDesc = 300;

$(document).ready(function() {

  datePickerConfig({
      "startDate": "#project\\.startDate",
      "endDate": "#project\\.endDate",
      defaultMinDateValue: $("#minDateValue").val(),
      defaultMaxDateValue: $("#maxDateValue").val()
  });
  // setProgramId();
  setDisabledCheckedBoxes();
  addChosen();
  applyWordCounter($("textarea.project-title"), lWordsElemetTitle);
  applyWordCounter($("textarea.project-description"), lWordsElemetDesc);

  validateEvent('[name=save], [name=next]', [
    "#justification"
  ]);

  /**
   * Upload files functions
   */

  $('.fileUpload .remove').on('click', function(e) {
    var context = $(this).attr('id').split('-')[1];
    var $parent = $(this).parent().parent();
    var $inputFile = $('[id$=' + context + '-template]').clone(true).removeAttr("id");
    $parent.empty().append($inputFile);
    $inputFile.hide().fadeIn('slow');
  });

  /**
   * CORE-Projects
   */

  var $coreSelect = $('#bilateralProjectsList select');
  var $coreProjects = $('#bilateralProjectsList .list');

  loadInitialCoreProjects();

  /** Events */

  // Event to add an item to core Project list from select option
  $coreSelect.on('change', function(e) {
    addItemList($(this).find('option:selected'));
  });

  // Event to remove an element 'li' from core project list
  $('ul li .remove').on('click', function(e) {
    removeItemList($(this).parents('li'));
  });

  /** Functions */

  // Function to load all core projects with ajax
  function loadInitialCoreProjects() {
    $.ajax({
        'url': '../../coreProjects.do',
        beforeSend: function() {
          $coreSelect.empty().append(setOption(-1, "Please select a bilateral project"));
        },
        success: function(data) {
          // Getting core projects previously selected
          var coreProjectsIds = [];
          $coreProjects.find('li input.id').each(function(i_id,id) {
            coreProjectsIds.push($(id).val().toString());
          });
          // Setting core projects allowed to select
          $.each(data.projects, function(i,project) {
            if($.inArray(project.id.toString(), coreProjectsIds) == -1) {
              $coreSelect.append(setOption(project.id, project.id + " - " + project.title));
            }
          });
        },
        complete: function() {
          $coreSelect.trigger("liszt:updated");
        }
    });
  }

  function addItemList($item) {
    var $listElement = $("#cpListTemplate").clone(true).removeAttr("id");
    $listElement.find('.id').val($item.val());
    $listElement.find('.name').html($item.text());
    $listElement.appendTo($coreProjects).hide().show('slow');
    $coreProjects.find('.emptyText').hide();
    $item.remove();
    $coreSelect.trigger("liszt:updated");
    setcoreProjectsIndexes();
  }

  function removeItemList($item) {
    // Adding to select list
    var data = {
        id: $item.find('.id').val(),
        'name': $item.find('.name').text()
    };
    var $select = $item.parents('.panel').find('select');
    $select.append(setOption(data.id, data.name));
    $select.trigger("liszt:updated");
    // Removing from list
    $item.hide("slow", function() {
      $item.remove();
      setcoreProjectsIndexes();
    });
  }

  function setcoreProjectsIndexes() {
    $coreProjects.find('li').each(function(i,item) {
      var elementName = "project.linkedProjects";
      $(item).find('.id').attr('name', elementName);
    });
  }

});

/**
 * Attach to the date fields the datepicker plugin
 */
function datePickerConfig(element) {
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
      dateFormat: "yy-mm-dd",
      minDate: defaultMinDateValue,
      maxDate: finalMaxDate,
      changeMonth: true,
      changeYear: true,
      defaultDate: null,
      onClose: function(selectedDate) {
        if(selectedDate != "") {
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
      dateFormat: "yy-mm-dd",
      minDate: finalMinDate,
      maxDate: defaultMaxDateValue,
      changeMonth: true,
      changeYear: true,
      defaultDate: null,
      onClose: function(selectedDate) {
        if(selectedDate != "") {
          $(element.startDate).datepicker("option", "maxDate", selectedDate);
        }
      }
  });
}

// Activate the chosen plugin.
function addChosen() {
  $("form select").chosen({
    search_contains: true
  });
}

// Set default Program ID
function setProgramId() {
  var programId = $("input#programID").val();
  $("#projectWorking input[value='" + programId + "']").attr("checked", true).attr("onclick", "return false");
}

function setDisabledCheckedBoxes() {
  $('#projectWorking input[type=checkbox]:checked').attr("onclick", "return false").addClass('disabled');
}
