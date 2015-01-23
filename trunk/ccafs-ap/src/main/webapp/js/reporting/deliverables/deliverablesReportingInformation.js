$(document).ready(function(){
  // Add event listeners
  attachEvents();
  
  // Check deliverable status to justification
  checkDeliverableStatus();
  
  // Check if deliverable type is a publication
  if (isNewDelverable) {
    checkDeliverableType();
  }
  
  // Check if there be data access restriction imposed
  checkRestrictionImposed();
  
  // Check if adopting metadata harvesting protocols
  checkMetadataProtocols();
  
  // Add calendar to date inputs
  datePickerConfig({
    "startDate" : "#accessLimitStartDate input",
    "endDate" : "#accessLimitEndDate input",
    defaultMinDateValue : $("#minDateValue").val(),
    defaultMaxDateValue : $("#maxDateValue").val()
  });
  
  function attachEvents(){
    $("#deliverableStatus select").on("change", checkDeliverableStatus);
    $("#deliverableType select").on("change", checkDeliverableType);
    $("#deliverableSubtype select").on("change", checkDeliverableSubType);
    $("#restrictionImposed input[type=radio]").on("change", checkRestrictionImposed);
    $("#metadataProtocols input[type=radio]").on("change", checkMetadataProtocols);
    $("#accessLimitOptions input[type=radio]").on("change", checkAccessLimitOptions);
  }
  
  function checkDeliverableStatus(){
    if ($("#deliverableStatus select").val() == "1") {
      $("#deliverableStatusJustification").hide("slow");
    } else {
      $("#deliverableStatusJustification").show("slow");
    }
  }
  
  function checkDeliverableType(){
    
    var typeID = $("#deliverableType select").val();
    var source = "../json/deliverableSubTypes.do?deliverableTypeID=" + typeID;
    $.getJSON(source, function(){
    }).done(function(data){
      $("#deliverableSubtype select").empty();
      $.each(data.subTypes, function(i,subType){
        $("#deliverableSubtype select").append('<option value="' + subType.id + '">' + subType.name + '</option>');
      });
      checkDeliverableSubType();
    }).fail(function(){
      alert("Error");
    });
    
    // if deliverable type is Publication
    if (typeID == "3") {
      $(".publicationQuestions").show("slow");
    } else {
      $(".publicationQuestions").hide("slow");
    }
    // if deliverable type is Data
    if (typeID == "1") {
      $(".dataAccessQuestions").show("slow");
    } else {
      $(".dataAccessQuestions").hide("slow");
    }
    
  }
  
  function checkDeliverableSubType(){
    var subTypeID = $("#deliverableSubtype select").val();
    console.log(subTypeID);
    // if deliverable subtype is Journal Paper
    if (subTypeID == "21") {
      $("#JournalQuestions").show("slow");
    } else {
      $("#JournalQuestions").hide("slow");
    }
    
    var source = "../json/metadataRequiredByDeliverableType.do?deliverableTypeID=" + subTypeID;
    $.getJSON(source, function(){
    }).done(function(data){
      $.each(data.result, function(metadata,required){
        var metadata = metadata.replace("{", "").replace("}", "").split("=");
        adjustMetadataRestrictions(metadata[0], required);
      });
    }).fail(function(){
      alert("Error");
    });
  }
  
  function adjustMetadataRestrictions(metadataIndex,requirements){
    var $input = $("#deliverable\\.metadata\\[" + metadataIndex + "\\]\\.value");
    var $label = $input.prev().find("label");
    
    // First remove all the formating
    $input.attr("disabled", false);
    if ($label.find("span").length) {
      $label.remove("span");
    }
    
    // Then apply as required
    if (requirements == "Mandatory") {
      if ($label.find("span").length == 0) {
        $label.append('<span class="red">*</span>');
      }
    } else if (requirements == "NotRequired") {
      $input.attr("disabled", true);
    }
  }
  
  function checkMetadataProtocols(){
    if ($("#metadataProtocols input[type=radio]:checked").val() == "true") {
      $("#specifyProtocols").show("slow");
    } else {
      $("#specifyProtocols").hide("slow");
    }
  }
  
  function checkRestrictionImposed(){
    if ($("#restrictionImposed input[type=radio]:checked").val() == "Yes") {
      $("#accessLimitOptions").show("slow");
      checkAccessLimitOptions();
    } else {
      $("#accessLimitOptions").hide("slow");
    }
  }
  
  function checkAccessLimitOptions(){
    if ($("#accessLimitOptions input[type=radio]:checked").val() == "inmediate") {
      $(".accessLimit").hide("slow");
    } else {
      $(".accessLimit").show("slow");
    }
  }
  
  /**
   * Attach to the date fields the datepicker plugin
   * 
   */
  function datePickerConfig(element){
    var defaultMinDateValue = element.defaultMinDateValue;
    var defaultMaxDateValue = element.defaultMaxDateValue;
    var minDateValue = defaultMinDateValue;
    var maxDateValue = defaultMaxDateValue;
    
    // Start date calendar
    maxDateValue = $(element.endDate).val();
    
    // Add readonly attribute to prevent inappropriate user input
    // $(element.startDate).attr('readonly', true);
    var finalMaxDate = (maxDateValue != 0) ? maxDateValue : defaultMaxDateValue;
    $(element.startDate).datepicker({
      dateFormat : "yy-mm-dd",
      minDate : defaultMinDateValue,
      maxDate : finalMaxDate,
      changeMonth : true,
      changeYear : true,
      defaultDate : null,
      onClose : function(selectedDate){
        if (selectedDate != "") {
          $(element.endDate).datepicker("option", "minDate", selectedDate);
        }
      }
    });
    
    // End date calendar
    minDateValue = $(element.startDate).val();
    
    // Add readonly attribute to prevent inappropriate user input
    // $(element.endDate).attr('readonly', true);
    var finalMinDate = (minDateValue != 0) ? minDateValue : defaultMinDateValue;
    $(element.endDate).datepicker({
      dateFormat : "yy-mm-dd",
      minDate : finalMinDate,
      maxDate : defaultMaxDateValue,
      changeMonth : true,
      changeYear : true,
      defaultDate : null,
      onClose : function(selectedDate){
        if (selectedDate != "") {
          $(element.startDate).datepicker("option", "maxDate", selectedDate);
        }
      }
    });
  }
  
});
