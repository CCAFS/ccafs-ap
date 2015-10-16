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
          max: tasksLength,
          complete: function(event,ui) {
            $(this).hide();
          }
      });
      // Event for validate button inside each project
      $('.projectValidateButton, .validateButton').on('click', validateButtonEvent);

      // Refresh event when table is reloaded in project list section
      $('table.projectsList').on('draw.dt', function() {
        $('.projectValidateButton, .validateButton').on('click', validateButtonEvent);
        $(".progressbar").progressbar({
          max: tasksLength
        });
      });

      // Click on submit button
      $('.submitButton, .projectSubmitButton').on('click', submitButtonEvent);
    });

function submitButtonEvent(e) {
  e.preventDefault();
  noty({
      text: 'Are you sure you want to submit the project now?  Once submitted, you will no longer have editing rights.',
      type: 'confirm',
      dismissQueue: true,
      layout: 'center',
      theme: 'defaultTheme',
      buttons: [
          {
              addClass: 'btn btn-primary',
              text: 'Ok',
              onClick: function($noty) {
                $noty.close();
                console.log('Ok');
              }
          }, {
              addClass: 'btn btn-danger',
              text: 'Cancel',
              onClick: function($noty) {
                $noty.close();
                console.log('Cancel');
              }
          }
      ]
  });
}

function validateButtonEvent(e) {
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
      $
          .ajax({
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
                  // completed = 1;
                  if(completed == 1) {
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
                    notyOptions.text =
                        "The project is still incomplete, please go to the sections without the green check mark and complete the missing fields before submitting your project.";
                    notyOptions.type = 'alert';
                    notyOptions.layout = 'center';
                    noty(notyOptions);
                    $(button).next().fadeOut(function() {
                      $(button).fadeIn("slow").on('click', validateButtonEvent);
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