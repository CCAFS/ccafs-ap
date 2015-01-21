$(document).ready(function(){
  
  // Add event listeners
  attachEvents();
  // Check deliverable status to justification
  checkDeliverableStatus();
  // Check if there be data access restriction imposed
  checkRestrictionImposed();
  // Check if adopting metadata harvesting protocols
  checkMetadataProtocols();
  
  function attachEvents(){
    $("#deliverableStatus select").on("change", checkDeliverableStatus);
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
  
});
