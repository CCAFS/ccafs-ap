// deliverableTypeIdsNeeded contains the types of deliverable 
// that requires a file format specification

var deliverableTypeIdsNeeded = [ "1", "4" ];

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
              } else {
                // in another way copy it and show
                var fileFormatsDiv = $("#deliverable-9999").find(
                    ".checkboxGroup").parent().clone(true);

                $("#deliverable-" + selectedId).append(fileFormatsDiv);
                renameDeliverables();

                fileFormatsDiv.show("slow");
              }

            } else {
              var checkboxGroup = $("#deliverable-" + selectedId).find(
                  ".checkboxGroup");
              checkboxGroup.parent().hide('slow', function() {

                // After hide the file formats section uncheck all the checkboxes
                $(checkboxGroup).find(":checked").each(function() {
                  $(this).attr('checked', false);
                });
                //alert($(checkboxGroup).find(":checked"));

              });
            }
          });

    });

function renameDeliverables() {
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
                  });
              // File name
              $(this).find("[id$='fileName']").attr("id",
                  "activity.deliverables[" + index + "].fileName");
              $(this).find("[name$='fileName']").attr("name",
                  "activity.deliverables[" + index + "].fileName");
              // Achievements
              $(this).find("[id$='achievements']").attr("id",
                  "activity.deliverables[" + index + "].achievements");
              $(this).find("[name$='achievements']").attr("name",
                  "activity.deliverables[" + index + "].achievements");
              // Input hide that appears with file formats
              $(this).find("[id^='__multiselect_deliverables']").attr(
                  "name",
                  "__multiselect_activity.deliverables[" + index
                      + "].fileFormats");
            }
          });
}