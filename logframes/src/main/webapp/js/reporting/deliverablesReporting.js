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
        $("#addDeliverableBlock").before("<hr />");
        renameDeliverables();
        $newDeliverable.fadeIn("slow");
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
        });

        // Jquery needs a second to change the dom, if dont give
        // that time the rename function dont take the remove change
        // the smaller time to take the change is 578
        setTimeout(renameDeliverables, 600);
        //renameDeliverables();

      });

      $('.deliverableType').change(
          function(event) {
            var selected = $(this).find('option:selected').val();

            // Getting the id.
            var selectedId = $(event.target).attr("id").split("_")[3];

            // Check if the selected type needs a file format to display or hide 
            // that section
            if (jQuery.inArray(selected, deliverableTypeIdsNeeded) != -1) {
              var checkboxGroup = $("#deliverable-" + selectedId).find(
                  ".checkboxGroup");
              // Check if the checkboxgroup exists
              if (checkboxGroup.length > 0) {
                // If exists show it
                $(checkboxGroup).parent().show('slow');
              }
            } else {
              var checkboxGroup = $("#deliverable-" + selectedId).find(
                  ".checkboxGroup");
              checkboxGroup.parent().hide('slow', function() {
                // After hide the file formats section uncheck all the checkboxes
                $(checkboxGroup).find(":checked").each(function() {
                  $(this).attr('checked', false);
                });
              });
            }

            // check if the File URL field and the help message need to be showed or hided.
            var messageBox = $("#fileNameMessage-" + selectedId);
            var fileUrlField = $("#activity\\.deliverables\\[" + selectedId
                + "\\]\\.fileName");
            if (jQuery.inArray(selected, publicationType) != -1) {
              messageBox.show("slow");
              fileUrlField.parent().hide("slow");
              fileUrlField.val("");
            } else {
              fileUrlField.parent().show("slow");
              messageBox.hide("slow");
            }
          });

    });

function renameDeliverables() {
  //getting the text of the index element.
  var itemText = $("#template").find(".itemIndex").text();
  // getting the number of expected deliverables.
  var expectedDeliverablesCount = $("#expectedDeliverablesGroup .deliverable").length;
  $("#items .deliverable")
      .each(
          function(index, deliverable) {
            // Do nothing with expected deliverables.
            if (index >= expectedDeliverablesCount) {
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
              // Status.
              $(this).find("[id$='status']").attr("id",
                  "deliverables_activity_deliverables_" + index + "__status");
              $(this).find("[name$='status']").attr("name",
                  "activity.deliverables[" + index + "].status");
              // File formats.
              $(this).find(".checkbox").each(
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
              $(this).find("[id^='__multiselect_deliverables']").attr(
                  "name",
                  "__multiselect_activity.deliverables[" + index
                      + "].fileFormats");
            }
          });
}