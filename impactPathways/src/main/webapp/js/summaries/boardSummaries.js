$(document).ready(init);

function init() {
  addChosen();
  attachEvents();
}

function attachEvents() {
  $('.summariesSection a, .summariesSection span').on('click', selectSummariesSection);
  $('input[name=formOptions]').on('change', selectTypeReport);
}

function selectSummariesSection(e) {
  e.preventDefault();
  var $section = $(e.target).parents('.summariesSection');
  var $content = $('#' + $section.attr('id') + '-contentOptions');
  $section.siblings().removeClass('current');
  $section.addClass('current');
  $content.siblings().hide();
  $content.fadeIn();
}

function selectTypeReport(e) {
  var $option = $(e.target).parent();
  $option.parent().find('.extraOptions').hide();
  $option.find('.extraOptions').fadeIn();
}
// https://172.22.98.87:8443/impactPathways/summaries/project.do?projectID=1
// Activate the chosen plugin.
function addChosen() {
  $("form select").chosen({
    search_contains: true
  });
}