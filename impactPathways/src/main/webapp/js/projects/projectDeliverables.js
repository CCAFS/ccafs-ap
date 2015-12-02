// Limits for textarea input
var $deliverablesTypes, $deliverablesSubTypes;
var hashRegenerated = false;
hashScroll = false;

$(document).ready(init);

function init() {
  $deliverablesTypes = $("#deliverable_mainType");
  $deliverablesSubTypes = $("#deliverable_deliverable_type");
  $disseminationChannels = $('#deliverable_deliverable_disseminationChannel');

  attachEvents();

  // Initialize the tabs
  initDeliverableTabs();

  // Execute a change in deliverables types once
  $deliverablesTypes.trigger('change');

  validateEvent([
    "#justification"
  ]);

  // Set word limits to inputs that contains class limitWords-value, for example : <input class="limitWords-100" />
  setWordCounterToInputs('limitWords');
  // Add some plugins
  addChosen();
  addHoverStar();
  addDeliverablesTypesDialog();
}

function attachEvents() {
  // Deliverables Events
  $(".removeDeliverable, .removeNextUser, .removeElement").click(removeElementEvent);
  $deliverablesTypes.on("change", changeDeliverableTypes);
  $deliverablesSubTypes.on("change", changeDeliverableSubTypes);
  $('.helpMessage3').on("click", openDialog);

  // Next users events
  $(".addNextUser").on("click", addNextUserEvent);

  // Partnership contribution to deliverable
  $(".addPartner").on("click", addPartnerEvent);

  // Event when open access restriction is changed
  $('.openAccessRestrictionOption input').on('change', changeOARestriction);

  // Yes / No Event
  $('input.onoffswitch-checkbox').on('change', yesnoEvent);

}

function changeOARestriction() {
  var $period = $('#period-' + this.value);
  if($period.exists()) {
    $period.show().siblings().hide();
  } else {
    $('[id*="period"]').hide();
  }
}

function yesnoEvent() {
  var isChecked = $(this).is(':checked');
  var $aditional = $('#aditional-' + (this.name).split('.')[1]);
  if($(this).hasClass('inverse')) {
    isChecked = !isChecked;
  }
  if(isChecked) {
    $aditional.slideDown("slow");
  } else {
    $aditional.slideUp("slow");
  }
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

function initDeliverableTabs() {
  $('#projectDeliverable').tabs({
      active: $('#indexTabCurrentYear').val(),
      show: {
          effect: "fadeIn",
          duration: 200
      },
      hide: {
          effect: "fadeOut",
          duration: 100
      }
  });
}

function addNextUserEvent(e) {
  e.preventDefault();
  var $newElement = $("#projectNextUserTemplate").clone(true).removeAttr("id").addClass("projectNextUser");
  $(e.target).parent().before($newElement);
  $('#deliverable-nextUsers').find('.emptyText').hide();
  $newElement.fadeIn("slow");
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

function changeDeliverableTypes(event) {
  var typeId = $(event.target).val();
  updateDeliverableSubTypes(typeId);
}

function changeDeliverableSubTypes() {
  var typeId = $deliverablesSubTypes.val();
  checkRequiredBlock(typeId);
  checkOtherSubType(typeId);
}

function checkRequiredBlock(typeId) {
  $('[class*="requiredForType"]').hide();
  var $requiredTypeBlock = $('.requiredForType-' + typeId);
  $requiredTypeBlock.show();
}

function checkOtherSubType(typeId) {
  if(typeId == 38) {
    $(".input-otherType").show('slow').find('input').attr('disabled', false);
  } else {
    $(".input-otherType").hide('slow').find('input').attr('disabled', true);
  }
}

function updateDeliverableSubTypes(typeId) {
  var $subTypeSelect = $("#projectDeliverable select[name$='type'] ");
  var source = baseURL + "/json/deliverablesByType.do?deliverableTypeID=" + typeId;
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
    changeDeliverableSubTypes();
    // Regenerating hash from form information
    if(!hashRegenerated) {
      setFormHash();
      hashRegenerated = true;
    }
  }).fail(function() {
    console.log("Error updating deliverables sub types");
  });
}

function addHoverStar() {
  $('.hover-star').rating({
      cancel: 'Cancel',
      cancelValue: '0',
      focus: function(value,link) {
        var $tip = $(this).parent().find('.hover-test');
        $tip[0].data = $tip[0].data || $tip.html();
        $tip.html(link.title || 'value: ' + value);
      },
      blur: function(value,link) {
        var $tip = $(this).parent().find('.hover-test');
        $tip.html($tip[0].data || '');
      }
  });
}

function addDeliverablesTypesDialog() {
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
