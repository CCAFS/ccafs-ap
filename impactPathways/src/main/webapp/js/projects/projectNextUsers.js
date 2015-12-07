var $elementsBlock;
var nextUsersName;

$(document).ready(init);

function init() {
  // Set initial variables
  $elementsBlock = $('#nextUsersList');
  nextUsersName = $('#nextUsersName').val();
  // Add events for project next users section
  attachEvents();
  // Set word limits to inputs that contains class limitWords-value, for example : <input class="limitWords-100" />
  setWordCounterToInputs('limitWords');
  // Validate justification event
  validateEvent([
    "#justification"
  ]);
}

function attachEvents() {
  // Remove a next user event
  $('.removeElement').on('click', removeElement);
  // Add new next user event
  $('#projectNextUsers_add .addButton').on('click', addElement);
}

function removeElement(e) {
  e.preventDefault();
  $(e.target).parent().hide('slow', function() {
    $(this).remove();
    setElementsIndexes();
  });
}

function addElement(e) {
  e.preventDefault();
  var $newElement = $('#nextUser-template').clone(true).removeAttr("id");
  $elementsBlock.append($newElement.fadeIn("slow"));
  setElementsIndexes();
  $newElement.find('textarea').autoGrow();
}

function setElementsIndexes() {
  $elementsBlock.find('.nextUser').each(setElementIndex);
}

function setElementIndex(i,element) {
  var name = nextUsersName + "[" + i + "].";
  $(element).attr("id", "nextUser-" + i);
  $(element).find("span.index").html(i + 1);
  $(element).find(".keyNextUser").attr("name", name + "keyNextUser");
  $(element).find(".strategies").attr("name", name + "strategies");
  $(element).find(".reportedDeliverables").attr("name", name + "reportedDeliverables");
  $(element).find(".lessonsImplications").attr("name", name + "lessonsImplications");
}