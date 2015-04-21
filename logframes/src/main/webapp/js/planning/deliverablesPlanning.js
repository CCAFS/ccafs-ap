// deliverableTypeIdsNeeded contains the types of deliverable 
// that requires a file format specification

var deliverableTypeIdsNeeded = [ "1", "4" ];
var publicationType = "5";

$(document).ready(
    function() {
      $(".addDeliverable").click(function(event) {
        event.preventDefault();
        // Cloning tempalte.
        var $newDeliverable = $("#deliverable-9999").clone(true);
        $("#addDeliverableBlock").before($newDeliverable);
        renameDeliverables();
        $newDeliverable.fadeIn("slow");
        $newDeliverable.find("[id$='description']").focus();
      });

      $('.removeDeliverable').click(function(event) {
        event.preventDefault();
        // Getting the id.
        var removeId = $(event.target).attr("id").split("-")[1];
        $("#deliverable-" + removeId).fadeOut("slow");
        // removing division line.
        $("#deliverable-" + removeId).next("hr").hide("slow", function() {
          $(this).remove();
        });
        // removing div.
        $("#deliverable-" + removeId).hide("slow", function() {
          $(this).remove();
          renameDeliverables();
        });
      });

    });

function renameDeliverables() {
  //getting the text of the index element.
  var itemText = $("#delvierableTemplate").find(".itemIndex").text();
  $("#deliverablesBlock .deliverable").each(
      function(index, deliverable) {
        // Changing attributes of each component in order to match with the array order.
        // Main div.
        $(this).attr("id", "deliverable-" + index);
        // Remove link.
        $(this).find("[id^='removeDeliverable-']").attr("id",
            "removeDeliverable-" + index);
        // Id
        $(this).find("[name$='id']").attr("name",
            "activity.deliverables[" + index + "].id");
        //Item index
        $(this).find(".itemIndex").text(itemText + " " + (index + 1));
        // Description.
        $(this).find("[id$='description']").attr("id",
            "activity.deliverables[" + index + "].description");
        $(this).find("[name$='description']").attr("name",
            "activity.deliverables[" + index + "].description");
        $(this).find("[for$='description']").attr("for",
            "activity.deliverables[" + index + "].description");
        // Type.
        $(this).find("[id$='type']").attr("id",
            "deliverables_activity_deliverables_" + index + "__type");
        $(this).find("[name$='type']").attr("name",
            "activity.deliverables[" + index + "].type");
        // Year.
        $(this).find("[id$='year']").attr("id",
            "activity.deliverables[" + index + "].year");
        $(this).find("[name$='year']").attr("name",
            "activity.deliverables[" + index + "].year");
        $(this).find("[for$='year']").attr("for",
            "activity.deliverables[" + index + "].year");
        // File formats.
        $(this).find(".checkbox")
            .each(
                function(index2, fileFormat) {
                  $(this).attr(
                      "id",
                      "activity.deliverables[" + index + "].fileFormats-"
                          + index2);
                  $(this).attr("name",
                      "activity.deliverables[" + index + "].fileFormats");
                  $(this).next(".checkboxLabel").attr(
                      'for',
                      "activity.deliverables[" + index + "].fileFormats-"
                          + index2);
                });
        // File name
        $(this).find("[id$='fileName']").attr("id",
            "activity.deliverables[" + index + "].fileName");
        $(this).find("[name$='fileName']").attr("name",
            "activity.deliverables[" + index + "].fileName");
        // File name message
        $(this).find("[id$='fileNameMessage']").attr("id",
            "fileNameMessage-" + index);
        // Description Update
        $(this).find("[id$='descriptionUpdate']").attr("id",
            "activity.deliverables[" + index + "].descriptionUpdate");
        $(this).find("[name$='descriptionUpdate']").attr("name",
            "activity.deliverables[" + index + "].descriptionUpdate");
        // Input hide that appears with file formats
        $(this).find("[id^='__multiselect_deliverables']").attr("name",
            "__multiselect_activity.deliverables[" + index + "].fileFormats");
      });
}