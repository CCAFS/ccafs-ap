$(document).ready(init);

function init() {
  initGraph();
  addChosen();
  initItemListEvents();
}

// Items list functions

function initItemListEvents() {
  $('select.crpsSelect').on('change', function(e) {
    addItemList($(this).find('option:selected'));
  });
  $('ul li .remove').on('click', function(e) {
    removeItemList($(this).parents('li'));
  });

  validateEvent('[name=save], [name=next]', [
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
  });
}

function addItemList($option) {
  var $select = $option.parent();
  var $list = $option.parents('.panel').find('ul.list');
  var $li = $("#crpTemplate").clone(true).removeAttr("id");
  $li.find('.id').val($option.val());
  $li.find('.name').html($option.text());
  $li.appendTo($list).hide().show('slow');
  $option.remove();
  $select.trigger("liszt:updated");
  setIndexes($list, $('#crpsName').val());
  $list.find('.emptyText').fadeOut();
}

function setIndexes($element,elementName) {
  $element.find('li').each(function(i,item) {
    $(item).find('.id').attr('name', elementName);
  });
}

// Activate the chosen plugin to the countries, partner types and partners lists.
function addChosen() {
  $("form select").chosen({
    search_contains: true
  });
}