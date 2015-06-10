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
  setProgramId();
  addChosen();
  applyWordCounter($("textarea.project-title"), lWordsElemetTitle);
  applyWordCounter($("textarea.project-description"), lWordsElemetDesc);

  /**
   * Tick Box functions
   */
  var $tickBoxWp = $('.tickBox-wrapper input[type=checkbox]');

  $tickBoxWp.on('change', toggleInputs);
  $tickBoxWp.each(function() {
    if(getTickState(this)) {
      $(this).closest('.tickBox-wrapper').find('.tickBox-toggle').show();
    } else {
      $(this).closest('.tickBox-wrapper').find('.tickBox-toggle').hide();
    }
  });

  function toggleInputs(e) {
    $(this).parent().parent().parent().find('.tickBox-toggle').slideToggle(getTickState(e.target));
  }

  function getTickState(item) {
    return $(item).is(':checked');
  }

  /**
   * CORE-Projects
   */

  var $coreSelect = $('#coreProjectsList select');
  var $coreProjects = $('#coreProjectsList .list');

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
          $coreSelect.empty().append(setOption(-1, "Please select a Core-Project"));
        },
        success: function(data) {
          $.each(data.projects, function(i,project) {
            $coreSelect.append(setOption(project.id, project.id + " - " + project.title));
          });
        },
        complete: function() {
          $coreSelect.trigger("liszt:updated");
        }
    });
  }

  function addItemList($item) {
    var $listElement = $("#cpListTemplate").clone(true).removeAttr("id");
    $listElement.find('.coreProject_name').html($item.text());
    $listElement.find('.coreProject_id').val($item.val());
    $coreProjects.prepend($listElement);
    $item.remove();
    $coreSelect.trigger("liszt:updated");
    setcoreProjectsIndexes();
  }

  function removeItemList($item) {
    var data = {
        id: $item.find('.coreProject_id').val(),
        'name': $item.find('.coreProject_name').text()
    };
    $coreSelect.append(setOption(data.id, data.name));
    $coreSelect.trigger("liszt:updated");
    $item.hide("slow", function() {
      $item.remove();
      setcoreProjectsIndexes();
    });
  }

  function setcoreProjectsIndexes() {
    $coreProjects.find('li').each(function(i,item) {
      var elementName = "project.coreProjects[" + i + "].";
      $(item).find('.coreProject_id').attr('name', elementName + 'id');
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
  $("input[value='" + programId + "'][name$='regions'], input[value='" + programId + "'][name$='flagships']").attr(
      "checked", true).attr("disabled", true);
}
