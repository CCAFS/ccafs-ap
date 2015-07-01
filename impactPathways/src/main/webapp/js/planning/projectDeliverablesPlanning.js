// Limits for textarea input
var lWordsElemetDesc = 300;

$(document).ready(init);

function init() {
  attachEvents();
  addChosen();
  applyWordCounter($("textarea"), lWordsElemetDesc);
  $("select[id$='mainType']").trigger('change');
}

function attachEvents() {
  // Deliverables Events
  $(".removeDeliverable, .removeNextUser, .removeElement").click(removeElementEvent);
  $("select[id$='mainType']").change(updateDeliverableSubTypeList);

  // Next users events
  $(".addNextUser").on("click", addNextUserEvent);

  // Partnership contribution to deliverable
  $(".addPartner").on("click", addPartnerEvent);
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

function addNextUserEvent(e) {
  e.preventDefault();
  var $newElement = $("#projectNextUserTemplate").clone(true).removeAttr("id").addClass("projectNextUser");
  $(e.target).parent().before($newElement);
  $newElement.fadeIn("slow");
  setDeliverablesIndexes();
}

function addPartnerEvent(e) {
  e.preventDefault();
  var $newElement = $("#deliverablePartnerTemplate").clone(true).removeAttr("id");
  $(e.target).parent().before($newElement);
  $newElement.fadeIn("slow");
  addChosen();
  setDeliverablesIndexes();
}

function setDeliverablesIndexes() {
  $("#projectDeliverable .projectNextUser").each(function(i,nextUser) {
    var elementName = $('#nextUsersName').val() + "[" + i + "].";
    $(nextUser).attr("id", "projectNextUser-" + i);
    $(nextUser).find("span.index").html(i + 1);
    $(nextUser).find("[name$='].id']").attr("name", elementName + "id");
    $(nextUser).find("[name$='user']").attr("name", elementName + "user");
    $(nextUser).find("[name$='expectedChanges']").attr("name", elementName + "expectedChanges");
    $(nextUser).find("[name$='strategies']").attr("name", elementName + "strategies");
  });

  $('#projectDeliverable .deliverablePartner').each(function(i,element) {
    var elementName = $('#partnersName').val() + "[" + i + "].";
    $(element).find("span.index").html(i);
    $(element).find(".institution").attr("name", elementName + "institution");
    $(element).find(".type").attr("name", elementName + "type");
    $(element).find(".userId").attr("name", elementName + "user");
  });
}

function updateDeliverableSubTypeList(event) {
  var $mainTypeSelect = $(event.target);
  var $subTypeSelect = $("#projectDeliverable select[name$='type'] ");
  var source = baseURL + "/json/deliverablesByType.do?deliverableTypeID=" + $mainTypeSelect.val();
  $.getJSON(source).done(function(data) {
    // First delete all the options already present in the subtype select
    $subTypeSelect.empty();
    $.each(data.subTypes, function(index,subType) {
      var isSelected = (($("#subTypeSelected").val() == subType.id) ? "selected" : "");
      $subTypeSelect.append("<option value='" + subType.id + "' " + isSelected + ">" + subType.name + "</option>");
    });
    // Refresh the plugin in order to show the changes
    $subTypeSelect.trigger("liszt:updated");
  }).fail(function() {
    console.log("error");
  });

}
