var caseStudiesName;
var $elementsBlock;

$(document).ready(init);

function init() {
  // Set initial variables
  $elementsBlock = $('#caseStudiesBlock');
  caseStudiesName = $('#caseStudiesName').val();
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
  $('#addProjectPartner .addButton').on('click', addElement);
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
  var $newElement = $('#caseStudy-template').clone(true).removeAttr("id");
  $elementsBlock.append($newElement.fadeIn("slow"));
  setElementsIndexes();
}

function setElementsIndexes() {
  $elementsBlock.find('.caseStudy').each(setElementIndex);
}

function setElementIndex(i,element) {
  var name = caseStudiesName + "[" + i + "].";
  $(element).find("span.index").html(i + 1);
  // $(element).find(".keyNextUser").attr("name", name + "keyNextUser");

}