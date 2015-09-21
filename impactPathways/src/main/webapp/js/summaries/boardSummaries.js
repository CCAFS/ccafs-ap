$(document).ready(init);

function init() {
  attachEvents();
}

function attachEvents() {
  $('.summariesSection a, .summariesSection span').on('click', selectSummariesSection);
  $('input[name=projectsOptions]').on('change', selectTypeReport);
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
  console.log(e.target.id + ' - ' + e.target.checked);
}