var tasksLength;
var sections =
    [
        "description", "partners", "locations", "outcomes", "ccafsOutcomes", "otherContributions", "outputs",
        "deliverablesList", "activities", "budget", "budgetByMog"
    ];

$(document).ready(function() {
  tasksLength = sections.length;
  $(".progressbar").progressbar({
    max: tasksLength
  });
  // Event for submit button inside each project
  $('.projectSubmitButton, .submitButton').on('click', submitButtonEvent);
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
              console.log(completed + " completed of " + tasksLength);
              if(completed == tasksLength) {
                console.log("Project completed");
              } else {
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