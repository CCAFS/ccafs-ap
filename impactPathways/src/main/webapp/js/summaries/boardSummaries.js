$(document).ready(init);

function init() {
  addChosen();
  attachEvents();
}

function attachEvents() {
  $('.summariesSection a, .summariesSection span').on('click', selectSummariesSection);
  $('input[name=formOptions]').on('change', selectTypeReport);
  $('#generateReport').on('click', generateReport);
}

function selectSummariesSection(e) {
  e.preventDefault();
  var $section = $(e.target).parents('.summariesSection');
  var $content = $('#' + $section.attr('id') + '-contentOptions');
  $section.siblings().removeClass('current');
  $section.addClass('current');
  $content.siblings().hide();
  $content.fadeIn();

  // Uncheck from formOptions the option selected
  $('input[name=formOptions]').attr('checked', false);
}

function selectTypeReport(e) {
  var $option = $(e.target).parent();
  $option.parent().parent().find('.extraOptions select').attr('disabled', true);
  $option.parent().parent().find('.extraOptions').hide();
  $option.find('.extraOptions select').attr('disabled', false).trigger("liszt:updated");
  $option.find('.extraOptions').fadeIn();
}
// https://172.22.98.87:8443/impactPathways/summaries/project.do?projectID=1

function generateReport(e) {
  e.preventDefault();
  var $formOptions = $('input[name=formOptions]:checked');
  var formOption = $formOptions.val() || 0;
  var extraOptions = $('form [name!="formOptions"][name!="phase"]').serialize() || 0;
  var generateUrl = "";
  if(formOption != 0) {
    generateUrl = baseURL + "/summaries/" + formOption + ".do";
    if(extraOptions != 0) {
      generateUrl += '?' + extraOptions;
    }
    $.ajax({
        url: generateUrl,
        type: 'POST',
        beforeSend: function() {
          $('.loading').fadeIn();
        },
        success: function() {
          window.open(generateUrl);
        },
        error: function(data) {
          var notyOptions = jQuery.extend({}, notyDefaultOptions);
          notyOptions.text = data.status + ': ' + data.statusText;
          noty(notyOptions);
        },
        complete: function() {
          $('.loading').hide();
        }
    });
  } else {
    var notyOptions = jQuery.extend({}, notyDefaultOptions);
    notyOptions.text = 'You must to select a report option';
    noty(notyOptions);
  }

}

// Activate the chosen plugin.
function addChosen() {
  $("form select").chosen({
    search_contains: true
  });
}