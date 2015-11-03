$(document).ready(init);

function init() {
  addChosen();
  attachEvents();
}

function attachEvents() {
  $('.summariesSection a, .summariesSection span').on('click', selectSummariesSection);
  $('input[name=formOptions]').on('change', selectTypeReport);
  $('select[name=projectID], input[name=q]').on('change', updateUrl);
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

  // Clean URL
  setUrl('#');
}

function selectTypeReport(e) {
  var $option = $(e.target).parent();
  $option.parent().parent().find('.extraOptions').find('select, input').attr('disabled', true);
  $option.parent().parent().find('.extraOptions').hide();
  $option.find('.extraOptions').find('select, input').attr('disabled', false).trigger("liszt:updated");
  $option.find('.extraOptions').fadeIn();

  updateUrl();
}

function generateReport(e) {
  var $formOptions = $('input[name=formOptions]:checked');
  var formOption = $formOptions.val() || 0;
  if(formOption == 0) {
    e.preventDefault();
    var notyOptions = jQuery.extend({}, notyDefaultOptions);
    notyOptions.text = 'You must to select a report option';
    noty(notyOptions);
  }
}

function updateUrl() {
  var generateUrl = "";
  var $formOptions = $('input[name=formOptions]:checked');
  var formOption = $formOptions.val() || 0;
  var extraOptions = $('form [name!="formOptions"][name!="phase"]').serialize() || 0;
  if(formOption != 0) {
    generateUrl = baseURL + "/summaries/" + formOption + ".do";
    if(extraOptions != 0) {
      generateUrl += '?' + extraOptions;
    }
    setUrl(generateUrl);
  } else {
    setUrl('#');
  }
}

function setUrl(url) {
  if(url == '#') {
    $('#generateReport').hide();
  } else {
    $('#generateReport').attr('href', url).fadeIn();
  }
}

// Activate the chosen plugin.
function addChosen() {
  $("form select").chosen({
    search_contains: true
  });
}