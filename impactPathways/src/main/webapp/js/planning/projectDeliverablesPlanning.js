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
  $(".removeDeliverable, .removeNextUser").click(removeElementEvent);
  $("select[id$='mainType']").each(function() {
    console.log($(this).attr("id"));
  });
  $("select[id$='mainType']").change(updateDeliverableSubTypeList);
  // Next users events
  $(".addNextUser").on("click", addNextUserEvent);
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

function setDeliverablesIndexes() {
  $("#projectDeliverable .projectNextUser").each(function(i,nextUser) {
    var elementName = "project.deliverables[0].nextUsers[" + i + "].";
    $(nextUser).attr("id", "projectNextUser-" + index);
    $(nextUser).find("span#index").html(i + 1);

    $(nextUser).find("[name$='].id']").attr("name", elementName + "id");
    $(nextUser).find("[name$='user']").attr("name", elementName + "user");
    $(nextUser).find("[name$='expectedChanges']").attr("name", elementName + "expectedChanges");
    $(nextUser).find("[name$='strategies']").attr("name", elementName + "strategies");

  });
}

function updateDeliverableSubTypeList(event) {
  var $mainTypeSelect = $(event.target);
  var blockIndex = $("select[id$='mainType']").index($mainTypeSelect);
  var $subTypeSelect;

  // Check that the select is not inside the template
  if($mainTypeSelect.attr("id") != "none_mainType") {
    $subTypeSelect = $("#projectDeliverable-" + blockIndex + " select[name$='type'] ");
  } else {
    // The template has index 999
    $subTypeSelect = $("#projectDeliverable-999 select[name$='type'] ");
  }

  var source = baseURL + "/json/deliverablesByType.do?deliverableTypeID=" + $mainTypeSelect.val();
  $.getJSON(source).done(function(data) {
    // First delete all the options already present in the subtype select
    $subTypeSelect.find("option").remove();

    var selectedValue = $("#subTypeSelected_" + blockIndex).val();
    var optionElement;
    $.each(data.subTypes, function(index,subType) {
      if(selectedValue == subType.id) {
        optionElement = "<option value='" + subType.id + "' selected >" + subType.name + "</option>";
      } else {
        optionElement = "<option value='" + subType.id + "'>" + subType.name + "</option>";
      }

      $subTypeSelect.append(optionElement);
    });
    // Refresh the plugin in order to show the changes
    $subTypeSelect.trigger("liszt:updated");
  }).fail(function() {
    console.log("error");
  });

}
