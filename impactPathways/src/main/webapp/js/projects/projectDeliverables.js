var $deliverablesTypes, $deliverablesSubTypes,$alreadyDisseminated, $disseminationChannels;
var $statuses, $statusDescription;
var implementationStatus
var hashRegenerated = false;
hashScroll = false;

$(document).ready(init);

function init() {
  $deliverablesTypes = $("#deliverable_mainType");
  $deliverablesSubTypes = $("#deliverable_deliverable_type");
  $alreadyDisseminated = $('input[name="deliverable.dissemination.alreadyDisseminated"]');
  $disseminationChannels = $('#deliverable_deliverable_dissemination_disseminationChannel');
  
  
  $statuses = $('#deliverable_deliverable_status');
  $statusDescription = $('#statusDescription');
  implementationStatus = $statuses.find('option[value="2"]').text();
  $endDate = $('#deliverable_deliverable_year');

  attachEvents();

  // Initialize the tabs
  initDeliverableTabs();

  // Initialize metadata functions
  initMetadataFunctions();
  
  // Initialize datepicker for period inputs
  $('input.period').datepicker();
  $('input.period').datepicker( "option", "dateFormat", "yy-mm-dd" );

  // Execute a change in deliverables types once
  $deliverablesTypes.trigger('change');

  // Set word limits to inputs that contains class limitWords-value, for example : <input class="limitWords-100" />
  setWordCounterToInputs('limitWords');

  // Create a dropzone to attach files
  addDropzone();
  // Check option selected
  checkOption();

  // Add some plugins
  addSelect2();
  addHoverStar();
  addDeliverablesTypesDialog();

  // Set names to deliverable files already uploaded
  setDeliverableFilesIndexes();

  // Validate justification at save
  if(isPlanningCycle()){validateEvent([ "#justification" ]);}
 
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
  
  $alreadyDisseminated.on('change', changeDisseminatedOption);

  // Yes / No Event
  $('input.onoffswitch-radio').on('change', yesnoEvent);

  // This event is for check if files smaller than 30MB will be hosted in CCAFS
  $("#dataSharingOptions input[type=radio]").on("click", checkOption);
  // This event is for remove file upload fields and inputs
  $(".removeInput").on("click", removeFileUploaded);
  // This event is when will be add one URL
  $(".addFileURL").on("click", addfileURL);
  
  
  // Status
  $statuses.on('change', function(e) {
    if(isStatusCancelled($(this).val())) {
      $statusDescription.show(400);
    } else {
      $statusDescription.hide(400);
    }
  });

  $endDate.on('change', changeStatus);
  $endDate.trigger('change');
}

function changeDisseminatedOption(){
  var isChecked = ($(this).val() === "true");
  if (isChecked){
    $('[href="#deliverable-dataSharing"]').parent().hide();
  }else{
    // Show metadata fields
    $('#deliverable-metadata').show(200);
    $('[href="#deliverable-dataSharing"]').parent().show().addClass('animated bounceInUp');
  }
}

function changeStatus(){ 
  checkImplementationStatus($(this).val());
}

function isStatusCancelled(statusId) {
  return(statusId == "5")
}

function checkImplementationStatus(year) {
  if(year <= currentReportingYear) {
    $statuses.removeOption(2);
  } else {
    $statuses.addOption(2, implementationStatus);
  }
  $statuses.select2();
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
  // var isChecked = $(this).is(':checked');
  var isChecked = ($(this).val() === "true");  
  $(this).siblings().removeClass('radio-checked');
  $(this).next().addClass('radio-checked');
  var array = (this.name).split('.');
  var $aditional = $('#aditional-' + array[array.length - 1]);
  if($(this).hasClass('inverse')) {
    isChecked = !isChecked;
  }
  if(isChecked) {
    $aditional.slideDown("slow");
  } else {
    $aditional.slideUp("slow");
    $aditional.find('input:text,textarea').val('');
  }
}

function addSelect2() {
  $("#projectDeliverable select").select2({
    search_contains: true
  });
}

function openDialog() {
  $("#dialog").dialog("open");
}

function initDeliverableTabs() {
  $('#projectDeliverable').tabs({
      show: {
          effect: "fadeIn",
          duration: 200
      },
      hide: {
          effect: "fadeOut",
          duration: 100
      }
  });
  if(window.location.hash == ""){
    // Set index tab after save
    $( "#projectDeliverable" ).tabs({ active: $('#indexTab').val() });
  }
  
  // Check tab for justification field
  var currentIndexTab = $('#projectDeliverable ul.ui-tabs-nav li.ui-state-active').index();
  checkIndexTab(currentIndexTab);
  $('#indexTab').val(currentIndexTab);
  
  // Event when tabs are clicked
  $('#projectDeliverable ul.ui-tabs-nav li').on('click', function(){
    var indexTab = $(this).index();
    $('#indexTab').val(indexTab);
    checkIndexTab(indexTab);
  });
  
}

function checkIndexTab(indexTab){
  if(indexTab == "0"){
    $("#justification").parent().show();
  }else{
    $("#justification").parent().hide();
  }
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
  addSelect2();
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
    $subTypeSelect.select2();
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
var $disseminationUrl, $metadataOutput, $checkButton;
var timeoutID;

function initMetadataFunctions() {
  // Setting vars
  $disseminationUrl = $('#disseminationUrl input');
  $metadataOutput = $('#metadata-output');
  $checkButton = $('#fillMetadata');

  // Events
  $disseminationUrl.on('keyup', urlCheck);
  $checkButton.on("click", function(){
    getMetadata(true);
  });

  $disseminationChannels.on('change',function(e) {
    e.preventDefault();
    var optionSelected = $disseminationChannels.val();
    $checkButton.show(); 
    if(optionSelected == -1) {
      $('.example').fadeOut();
      $disseminationUrl.fadeOut(500);
      return;
    }else if(optionSelected == "other"){
      $('.example').fadeOut();
      $checkButton.fadeOut();
      $metadataOutput.html("");
      // Show metadata fields
      $('#deliverable-metadata').show(200);
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
    timeoutID = setTimeout(getMetadata(false), 1000);
  }
}

function getMetadata(fillData) {
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
    }else if(optionSelected == 'other'){
      return
    } else {
      ajaxData.metadataID = channelUrl;
    }
    
    // Show metadata fields
    $('#deliverable-metadata').show(200);
    
    $.ajax({
        'url': baseURL + '/metadataByLink.do',
        'type': "GET",
        'data': ajaxData,
        'dataType': "json",
        beforeSend: function() {
          $disseminationUrl.addClass('input-loading');
          $metadataOutput.html("Searching ... " + ajaxData.metadataID);
        },
        success: function(data) {
          if(data.errorMessage) {
            $metadataOutput.html(data.errorMessage);
          } else {
            data.metadata = JSON.parse(data.metadata);
            if (jQuery.isEmptyObject(data.metadata)){
              $metadataOutput.html("Metdata empty");
            }else{
              var fields = []; 
              $.each( data.metadata, function( key, value ) {
                fields.push(key.charAt(0).toUpperCase() + key.slice(1));
              });
              $metadataOutput.empty().append("Found metadata for " + ajaxData.metadataID +" <br /> " + fields.reverse().join(', '));
              if(fillData){setMetadata(data.metadata);}
            }
          }
        },
        complete: function() {
          $disseminationUrl.removeClass('input-loading');
        },
        error: function() {
          $metadataOutput.empty().append("Invalid URL for searching metadata");
        }
    });
  }
}

function setMetadata(data) {
  $(".descriptionMetadata").val(data.description).autoGrow();
  $(".creatorMetadata").val(data.authors);
  $(".identifierMetadata").val(data.identifier);
  $(".publishierMetadata").val(data.publishier);
  $(".relationMetadata").val(data.relation);
  $(".contributorMetadata").val(data.contributor);
  $(".subjectMetadata").val(data.subject);
  $(".sourceMetadata").val(data.source);
  $(".publicationMetada").val(data.publication);
  $(".languageMetadata").val(data.language);
  $(".coverageMetadata").val(data.coverage);
  $(".formatMetadata").val(data.format);
  $(".rigthsMetadata").val(data.rigths);
  $(".citation").val(data.citation);
}
