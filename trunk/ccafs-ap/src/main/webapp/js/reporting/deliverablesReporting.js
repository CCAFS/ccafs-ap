$(document).ready(function() {
  
  function renameDeliverables() {
    // getting the number of expected deliverables.
    var expectedDeliverablesCount = $("#expectedDeliverablesGroup .deliverable").length;    
    $("#items .deliverable").each(function(index, deliverable) {
      // Do nothing with expected deliverables.
      if(index >= expectedDeliverablesCount) {
        // Changing attributes of each component in order to match with the array order.
        // Main div.
        $(this).attr("id", "deliverable-"+index);
        // Remove link.
        $(this).find("[id^='removeDeliverable-']").attr("id", "removeDeliverable-"+index);
        // Description.
        $(this).find("[id$='description']").attr("id", "activity.deliverables["+index+"].description");
        $(this).find("[name$='description']").attr("name", "activity.deliverables["+index+"].description");
        $(this).find("[for$='description']").attr("for", "activity.deliverables["+index+"].description");
        // Type.
        $(this).find("[id$='type']").attr("id", "deliverables_activity_deliverables_"+index+"__type");
        $(this).find("[name$='type']").attr("name", "activity.deliverables["+index+"].type");
        // Year.
        $(this).find("[id$='year']").attr("id", "activity.deliverables["+index+"].year");
        $(this).find("[name$='year']").attr("name", "activity.deliverables["+index+"].year");
        $(this).find("[for$='year']").attr("for", "activity.deliverables["+index+"].year");
        // Status.
        $(this).find("[id$='status']").attr("id", "deliverables_activity_deliverables_"+index+"__status");
        $(this).find("[name$='status']").attr("name", "activity.deliverables["+index+"].status");
        // File formats.
        $(this).find(".checkbox").each(function(index2, fileFormat) {
          $(this).attr("id", "activity.deliverables["+index+"].fileFormats-"+index2);
          $(this).attr("name", "activity.deliverables["+index+"].fileFormats");
        });
      }
    });
  }
  $(".addDeliverable").click(function(event) {
    event.preventDefault();
    // Cloning tempalte.
    var $newDeliverable = $("#deliverable-9999").clone(true);
    $("#newDeliverablesGroup").append($newDeliverable);
    $("#newDeliverablesGroup").append("<hr />");
    renameDeliverables();
    $newDeliverable.fadeIn("slow");          
  
    
    //var num = $(".cloned").length; // how many "duplicatable" input fields we currently have
   // var newNum = new Number(num + 1); // the numeric ID of the new input field being added

    // create the new element via clone() 
    //var newElem = $('#reportingDeliverable').clone(true);

    // Add it's class and ID
    //newElem.attr('id', 'reportingDeliverable' + newNum);
    //newElem.attr('class', 'cloned');

    //$(newElem).hide();

    //if (num > 0) {
      // insert the new element after the last "duplicatable" input field
      //$('#reportingDeliverable' + num).after(newElem);
    //} else {
     // $('#newDeliverables legend').after(newElem);
    //}

    //$(newElem).show("slow");

  });

  $('.removeDeliverable').click(function(event) {
    event.preventDefault();
    var num = $('.cloned').length; // how many "duplicatable" input fields we currently have

    $(event.target).parent().parent().hide("slow", function() {
      $(this).remove(); // remove the element
    });

  });

});
