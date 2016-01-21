// Limits for textarea input
var $deliverablesTypes, $deliverablesSubTypes;
var hashRegenerated = false;
hashScroll = false;

$(document).ready(init);

function init() {
  $deliverablesTypes = $("#deliverable_mainType");
  $deliverablesSubTypes = $("#deliverable_deliverable_type");
  $disseminationChannels = $('#deliverable_deliverable_dissemination_disseminationChannel');

  attachEvents();

  // Initialize the tabs
  initDeliverableTabs();

  // Initialize metadata functions
  initMetadataFunctions();

  // Execute a change in deliverables types once
  $deliverablesTypes.trigger('change');

  // Set word limits to inputs that contains class limitWords-value, for example : <input class="limitWords-100" />
  setWordCounterToInputs('limitWords');

  // Create a dropzone to attach files
  addDropzone();
  // Check option selected
  checkOption();

  // Add some plugins
  addChosen();
  addHoverStar();
  addDeliverablesTypesDialog();

  // Set names to deliverable files already uploaded
  setDeliverableFilesIndexes();

  validateEvent([
    "#justification"
  ]);
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

  $disseminationChannels.on('change', changeDisseminationChannel);

  // Yes / No Event
  $('input.onoffswitch-checkbox').on('change', yesnoEvent);

  // This event is for check if files smaller than 30MB will be hosted in CCAFS
  $("#dataSharingOptions input[type=radio]").on("click", checkOption);
  // This event is for remove file upload fields and inputs
  $(".removeInput").on("click", removeFileUploaded);
  // This event is when will be add one URL
  $(".addFileURL").on("click", addfileURL);
}

function changeDisseminationChannel() {
  var channel = $disseminationChannels.val();
  if(channel != "-1") {
    if(channel == "other") {
      $('#disseminationName').slideDown("slow");
    } else {
      $('#disseminationName').slideUp("slow");
    }
    $('#disseminationUrl').slideDown("slow");
  } else {
    $('#disseminationUrl').slideUp("slow");
  }
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
  var array = (this.name).split('.');
  var $aditional = $('#aditional-' + array[array.length - 1]);
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

/* Deliverable information functions */

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

function removeElementEvent(e) {
  e.preventDefault();
  var $parent = $(e.target).parent();
  $parent.hide("slow", function() {
    $parent.remove();
    setDeliverablesIndexes();
  });
}

/* Deliverable Rating functions */

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

/* Deliverable Data sharing functions */

function removeFileUploaded() {
  $(this).parent().fadeOut(function() {
    $(this).remove();
    setDeliverableFilesIndexes();
  });

}

function checkOption() {
  var $optionSelectd = $('#dataSharingOptions input[type=radio]:checked');
  $(".uploadBlock").hide();
  $optionSelectd.parent().next().fadeIn();
}

function addDropzone() {
  $("div#dragAndDrop").dropzone({
      init: initDropzone,
      fallback: fallBackDropzone, // Run this function if the browser not support dropzone plugin
      forceFallback: false,
      paramName: "file", // The name that will be used to transfer the file
      addRemoveLinks: true,
      params: {
          projectID: $("input[name^='projectID']").val(),
          deliverableID: $("input[name^='deliverableID']").val()
      },
      url: baseURL + '/reporting/uploadDeliverable.do',
      maxFilesize: 30,
      accept: function(file,done) {
        canAddFile = true;
        console.log(file.name);
        // Check is file is already uploaded
        if(checkDuplicateFile(file.name)) {
          $.prompt("Do you want replace this file (" + file.name + ") ?", {
              title: "There is already a file with the same name.",
              buttons: {
                  "Yes": true,
                  "No": false
              },
              submit: function(e,v,m,f) {
                if(v == true) {
                  done();
                  canAddFile = false;
                } else {
                  done("There is already a file with the same name");
                }
              }
          });
        } else {
          done();
        }
      }
  });
}

function initDropzone() {
  this.on("success", function(file,done) {
    var result = jQuery.parseJSON(done);
    if(result.fileSaved) {
      file.hosted = "Locally";
      file.fileID = result.fileID;
      if(canAddFile) {
        addFileToUploaded(file);
      }
      this.removeFile(file);
    }
  });
}

function addfileURL(e) {
  var $target = $(e.target).parent();
  var $parent = $target.parent();
  var urlString = $target.find("input").val();
  if(checkUrl(urlString)) {
    var file = {
        "name": urlString,
        "hosted": $parent.find("input[type=radio]").val(),
        fileID: -1
    };
    addFileToUploaded(file);
    $target.find("input").val("http://");
  } else {
    var notyOptions = jQuery.extend({}, notyDefaultOptions);
    notyOptions.text = 'Invalid URL';
    noty(notyOptions);
  }
}

// This function run when the browser not support dropzone plugin
function fallBackDropzone() {
  $("#addFileInput").on("click", function() {
    var $newElement = $("#fileInputTemplate").clone(true).removeAttr("id");
    $(this).parent().prepend($newElement);
    $newElement.fadeIn();
  });
  $('#fileInputTemplate input').on("change", checkDublicateFileInput);
  $("#addFileInput").trigger("click", true);
}

function checkDublicateFileInput(e) {
  var $file = $(this);
  var fileName = $file.val().split('/').pop().split('\\').pop();

  if(checkDuplicateFile(fileName)) {
    $.prompt("Do you want replace this file (" + fileName + ") ?", {
        title: "There is already a file with the same name.",
        buttons: {
            "Yes": true,
            "No": false
        },
        submit: function(e,v,m,f) {
          if(v == true) {
          } else {
            $file.replaceWith($('#fileInputTemplate input').clone(true));
          }
        }
    });
  }
}

function addFileToUploaded(file) {
  var $newElement = $("#deliverableFileTemplate").clone(true).removeAttr("id");

  // Filling information obtained
  $newElement.find(".fileID").val(file.fileID);
  if(file.hosted == "Locally") {
    $newElement.find("input[type='hidden'].fileHosted").remove();
    $newElement.find("input[type='hidden'].fileLink").remove();
    $newElement.find(".fileSize").html((file.size / 1024).toFixed(1) + " KB");
  }
  $newElement.find(".fileHosted").val(file.hosted);
  $newElement.find(".fileLink").val(file.name);
  if((file.name).length > 70) {
    file.name = (file.name).substring(0, 70) + "...";
  }

  $newElement.find(".fileName").html(file.name);
  $newElement.find(".fileFormat").html(file.hosted);

  $("#filesUploaded .text").hide();
  // Show file uploaded
  $("#filesUploaded ul").prepend($newElement);
  $newElement.show("slow");
  setDeliverableFilesIndexes();
}

function setDeliverableFilesIndexes() {
  $("form .fileUploaded").each(function(i,element) {
    var elementName = "deliverable.files[" + i + "].";
    $(element).find("input[type='hidden'].fileID").attr("name", elementName + "id");
    $(element).find("input[type='hidden'].fileHosted").attr("name", elementName + "hosted");
    $(element).find("input[type='hidden'].fileLink").attr("name", elementName + "link");

    var fileName = $(element).find(".fileName").html();
    if((fileName).length > 70) {
      $(element).find(".fileName").html((fileName).substring(0, 70) + "...");
    }
    $(element).find(".fileName").attr("title", fileName);

  });
  if($("form .fileUploaded").length == 0) {
    $("#filesUploaded .text").show();
  }
}

function checkUrl(url) {
  return url.match(/(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&amp;:/~\+#]*[\w\-\@?^=%&amp;/~\+#])?/);
}

function checkDuplicateFile(fileName) {
  var alreadyExist = false;
  $("form .fileUploaded .fileName").each(function(i,element) {
    if(fileName == $(element).text()) {
      alreadyExist = true;
    }
  });
  return alreadyExist;
}

/*
 * Metadata functions
 */
var $disseminationUrl;
var timeoutID;

function initMetadataFunctions() {
  // Setting vars
  $disseminationUrl = $('#disseminationUrl input');

  // Events
  $disseminationUrl.on("keyup", urlCheck);
  $('#check-button').on("click", getMetadata);

  $disseminationChannels.change(function(e) {
    e.preventDefault();
    var optionSelected = $disseminationChannels.val();
    if(optionSelected == -1) {
      $('.example').fadeOut();
      $disseminationUrl.fadeOut(500);
      return;
    }
    $disseminationUrl.val('');
    $disseminationUrl.fadeIn(500);
    $('#info-' + optionSelected).siblings().hide();
    $('#info-' + optionSelected).fadeIn(500);
  });
}

function urlCheck(e) {
  if(($(this).val()).length > 1) {
    if(timeoutID) {
      clearTimeout(timeoutID);
    }
    // Start a timer that will search when finished
    timeoutID = setTimeout(getMetadata, 1000);
  }
}

function getMetadata() {
  var optionSelected = $disseminationChannels.val();
  var channelUrl = $disseminationUrl.val();
  if(channelUrl.length > 1) {
    var uri = new Uri(channelUrl);
    var uriPath = uri.path();
    var uriHost = uri.host();

    var ajaxData = {
      pageID: optionSelected
    };
    if(optionSelected == 'cgspace') {
      ajaxData.metadataID = "oai:" + uriHost + ":" + uriPath.slice(8, uriPath.length);
    } else {
      ajaxData.metadataID = channelUrl;
    }
    $.ajax({
        'url': baseURL + '/metadataByLink.do',
        'type': "GET",
        'data': ajaxData,
        'dataType': "json",
        beforeSend: function() {
          $("#output").html("Searching ... " + ajaxData.metadataID);
        },
        success: function(data) {
          if(data.errorMessage) {
            $("#output").html(data.errorMessage);
            console.log(data.errorMessage);
          } else {
            $('#title').val(data.title);
            $('#creator').val(data.creator);
            $('#subject').val(data.subject);
            $('#description').val(data.description);
            $('#publisher').val(data.publisher);
            $('#contributor').val(data.contributor);
            $('#date').val(data.date);
            $('#type').val(data.type);
            $('#format').val(data.format);
            $('#identifier').val(data.identifier);
            $('#source').val(data.source);
            $('#language').val(data.language);
            $('#relation').val(data.relation);
            $('#coverage').val(data.coverage);
            $('#rights').val(data.rights);

            $("#output").html("Found metadata for " + ajaxData.metadataID);
          }
        },
        complete: function() {
        }
    });
  }
}
