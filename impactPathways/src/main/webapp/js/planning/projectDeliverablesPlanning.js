// Limits for textarea input
var lWordsDTitle = 15;
var lWordsNextUsers = 20;
var lWordsNextUsersDesc = 80;
var lWordsLessons = 100;
var $deliverablesTypes, $deliverablesSubTypes;
var hashRegenerated = false;

$(document).ready(init);

function init() {
  $deliverablesTypes = $("#deliverable_mainType");
  $deliverablesSubTypes = $("#deliverable_deliverable_type");
  attachEvents();
  addChosen();

  applyWordCounter($(".deliverableTitle"), lWordsDTitle);
  applyWordCounter($(".projectNextUser .input input"), lWordsNextUsers);
  applyWordCounter($(".projectNextUser .textArea textarea"), lWordsNextUsersDesc);
  applyWordCounter($("#lessons textarea"), lWordsLessons);
  $deliverablesTypes.trigger('change');

  validateEvent([
    "#justification"
  ]);

  $("#dialog").dialog({
      autoOpen: false,
      buttons: {
        Close: function() {
          $(this).dialog("close");
        }
      },
      width: 925,
      modal: true
  });
}

function attachEvents() {
  // Deliverables Events
  $(".removeDeliverable, .removeNextUser, .removeElement").click(removeElementEvent);
  $deliverablesTypes.on("change", updateDeliverableSubTypeList);

  $deliverablesSubTypes.on("change", checkOtherType);

  // Next users events
  $(".addNextUser").on("click", addNextUserEvent);

  // Partnership contribution to deliverable
  $(".addPartner").on("click", addPartnerEvent);

  $('.helpMessage3').on("click", openDialog);
}

function addChosen() {
  $("#projectDeliverable select").chosen({
    search_contains: true
  });
}

// Deliverables Events
function removeElementEvent(e) {
  e.preventDefault();
  var $parent = $(e.target).parent();
  $parent.hide("slow", function() {
    $parent.remove();
    setDeliverablesIndexes();
  });
}

function openDialog() {

  $("#dialog").dialog("open");

}

function addNextUserEvent(e) {
  e.preventDefault();
  var $newElement = $("#projectNextUserTemplate").clone(true).removeAttr("id").addClass("projectNextUser");
  $(e.target).parent().before($newElement);
  $('#deliverable-nextUsers').find('.emptyText').hide();
  $newElement.fadeIn("slow");
  applyWordCounter($newElement.find(".input input"), lWordsNextUsers);
  applyWordCounter($newElement.find(".textArea textarea"), lWordsNextUsersDesc);
  setDeliverablesIndexes();
}

function addPartnerEvent(e) {
  e.preventDefault();
  var $newElement = $("#deliverablePartnerTemplate").clone(true).removeAttr("id");
  $(e.target).parent().parent().find('.emptyText').hide();
  $(e.target).parent().before($newElement);
  $newElement.fadeIn("slow");
  addChosen();
  setDeliverablesIndexes();
}

function setDeliverablesIndexes() {
  // Updating next users names
  $("#projectDeliverable .projectNextUser").each(function(i,nextUser) {
    var elementName = $('#nextUsersName').val() + "[" + i + "].";
    $(nextUser).attr("id", "projectNextUser-" + i);
    $(nextUser).find("span.index").html(i + 1);
    $(nextUser).find("[name$='].id']").attr("name", elementName + "id");
    $(nextUser).find("[name$='user']").attr("name", elementName + "user");
    $(nextUser).find("[name$='expectedChanges']").attr("name", elementName + "expectedChanges");
    $(nextUser).find("[name$='strategies']").attr("name", elementName + "strategies");
  });

  // Updating partners contribution names
  $('#projectDeliverable .deliverablePartner').each(function(i,element) {
    var elementName = $('#partnersName').val() + "[" + i + "].";
    $(element).find("span.index").html(i + 1);
    $(element).find(".id").attr("name", elementName + "id");
    $(element).find(".type").attr("name", elementName + "type");
    $(element).find(".partner").attr("name", elementName + "partner");
  });
}

function updateDeliverableSubTypeList(event) {
  var $mainTypeSelect = $(event.target);
  var $subTypeSelect = $("#projectDeliverable select[name$='type'] ");
  var source = baseURL + "/json/deliverablesByType.do?deliverableTypeID=" + $mainTypeSelect.val();
  $.getJSON(source).done(function(data) {
    // First delete all the options already present in the subtype select
    $subTypeSelect.empty();
    $subTypeSelect.append(setOption('-1', 'Select an option'));
    $.each(data.subTypes, function(index,subType) {
      var isSelected = (($("#subTypeSelected").val() == subType.id) ? "selected" : "");
      $subTypeSelect.append("<option value='" + subType.id + "' " + isSelected + ">" + subType.name + "</option>");
    });
    // Refresh the plugin in order to show the changes
    $subTypeSelect.trigger("liszt:updated");
    // Check if other specify is selected
    checkOtherType();
    // Regenerating hash from form information
    if(!hashRegenerated) {
      setFormHash();
      hashRegenerated = true;
    }
  }).fail(function() {
    console.log("error");
  });
}

function checkOtherType() {
  if($deliverablesSubTypes.val() == 38) {
    $(".input-otherType").show('slow').find('input').attr('disabled', false);
  } else {
    $(".input-otherType").hide('slow').find('input').attr('disabled', true);
  }
}
