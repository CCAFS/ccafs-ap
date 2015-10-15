var tasksLength;
var sections;

$(document).ready(
    function() {
      sections =
          [
              "description", "partners", "locations", "outcomes", "ccafsOutcomes", "otherContributions", "outputs",
              "deliverablesList", "activities", "budget", "budgetByMog"
          ];
      tasksLength = sections.length;
      $(".progressbar").progressbar({
        max: tasksLength
      });
      // Event for validate button inside each project
      $('.projectValidateButton, .validateButton').on('click', submitButtonEvent);

      // Refresh event when table is reloaded in project list section
      $('table.projectsList').on('draw.dt', function() {
        $('.projectValidateButton, .validateButton').on('click', submitButtonEvent);
        $(".progressbar").progressbar({
          max: tasksLength
        });
      });
    });

function submitButtonEvent(e) {
  e.stopImmediatePropagation();
  e.preventDefault();
  var pID = $(e.target).attr('id').split('-')[1];
  // Execute Ajax process for each section
  processTasks(sections, pID, $(this));
}

function processTasks(tasks,projectId,button) {
  $(button).unbind('click');
  var completed = 0;
  var index = 0;
  $(button).fadeOut(function() {
    $(button).next().fadeIn();
  });
  function nextTask() {
    if(index < tasksLength) {
      var sectionName = tasks[index];
      var $sectionMenu = $('#menu-' + sectionName);
      $.ajax({
          url: baseURL + '/planning/validateProjectPlanningSection.do',
          data: {
              projectID: projectId,
              sectionName: sectionName
          },
          beforeSend: function() {
            $sectionMenu.removeClass('animated flipInX').addClass('loadingSection');
          },
          success: function(data) {
            // Process Ajax results here
            if(jQuery.isEmptyObject(data)) {
              $sectionMenu.removeClass('submitted');
            } else {
              if(data.sectionStatus.missingFieldsWithPrefix == "") {
                $sectionMenu.addClass('submitted').removeClass('toSubmit');
                completed++;
              } else {
                $sectionMenu.removeClass('submitted').addClass('toSubmit');
              }
            }
            $sectionMenu.removeClass('loadingSection');
          },
          complete: function(data) {
            $sectionMenu.addClass('animated flipInX');
            // Do next Ajax call
            $(button).next().progressbar("value", index + 1);
            ++index;
            if(index == tasksLength) {
              // completed = tasksLength;
              if(completed == tasksLength) {
                var notyOptions = jQuery.extend({}, notyDefaultOptions);
                notyOptions.text = 'The project can be submmited now';
                notyOptions.type = 'success';
                notyOptions.layout = 'center';
                noty(notyOptions);
                $(button).next().fadeOut(function() {
                  $(this).next().fadeIn("slow");
                });
              } else {
                var notyOptions = jQuery.extend({}, notyDefaultOptions);
                notyOptions.text = "The project progress is " + Math.round(completed / tasksLength * 100) + "%";
                notyOptions.type = 'alert';
                notyOptions.layout = 'center';
                noty(notyOptions);
                $(button).next().fadeOut(function() {
                  $(button).fadeIn("slow").on('click', submitButtonEvent);
                });
              }
            }
            nextTask();
          }
      });
    }
  }
  // Start first Ajax call
  nextTask();
}