var crpContributionName;
var $contributionsBlock;
var textAreaLimitWords = 100;
var lWordsLessons = 100;
var lWordsCollaboration = 50;
$(document).ready(init);

function init() {
  $contributionsBlock = $('ul#contributionsBlock');
  crpContributionName = $('#crpsName').val();
  initGraph();
  addChosen();
  initItemListEvents();
  applyWordCounter($("form textarea.contribution,form textarea.additionalContribution"), textAreaLimitWords);
  applyWordCounter($("form textarea.crpCollaborationNature"), lWordsCollaboration);
  applyWordCounter($("#lessons textarea"), lWordsLessons);
}

// Items list functions

function initItemListEvents() {
  $('select.crpsSelect').on('change', function(e) {
    addItemList($(this).find('option:selected'));
  });
  $('ul li .remove').on('click', function(e) {
    removeItemList($(this).parents('li'));
  });

  validateEvent([
    "#justification"
  ]);

  setInitialList();
}

function setInitialList() {
  $("div.crpContribution").each(function(index,element) {
    // Getting previously selected by project partner
    var $select = $(element).find('select');
    $(element).find('li input.id').each(function(i_id,id) {
      $select.find('option[value=' + $(id).val() + ']').remove();
    });
    $select.trigger("liszt:updated");
  });
}

function removeItemList($item) {
  // Adding to select list
  var $select = $item.parents('.panel').find('select');
  $select.append(setOption($item.find('.id').val(), $item.find('.name').text()));
  $select.trigger("liszt:updated");
  // Removing from list
  $item.hide("slow", function() {
    $item.remove();
    setIndexes();
  });
}

function addItemList($option) {
  var $select = $option.parent();
  var $li = $("#crpTemplate").clone(true).removeAttr("id");
  $li.find('.id').val($option.val());
  $li.find('.name').html($option.text());
  $li.appendTo($contributionsBlock).hide().show('slow');
  applyWordCounter($li.find("textarea.crpCollaborationNature"), lWordsCollaboration);
  $option.remove();
  $select.trigger("liszt:updated");
  setIndexes();
  $contributionsBlock.find('.emptyText').fadeOut();
}

function setIndexes() {
  $contributionsBlock.find('li').each(function(i,item) {
    var elementName = crpContributionName + '[' + i + ']';
    $(item).find('.id').attr('name', elementName + '.crp.id');
    $(item).find('.crpContributionId').attr('name', elementName + '.id');

    $(item).find('.crpCollaborationNature').attr('name', elementName + '.natureCollaboration');

  });
}

// Activate the chosen plugin to the countries, partner types and partners lists.
function addChosen() {
  $("form select").chosen({
    search_contains: true
  });
}