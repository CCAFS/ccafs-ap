var crpContributionName;
var $contributionsBlock;
$(document).ready(init);

function init() {
  $contributionsBlock = $('ul#contributionsBlock');
  crpContributionName = $('#crpsName').val();
  initGraph();
  addSelect2();
  initItemListEvents();
  // Set word limits to inputs that contains class limitWords-value, for example : <input class="limitWords-100" />
  setWordCounterToInputs('limitWords');
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

function addSelect2() {
  $('form select').select2();
}
