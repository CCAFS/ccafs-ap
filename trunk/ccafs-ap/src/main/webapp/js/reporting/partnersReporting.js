$(document).ready(function() {
  //This function enables launch the pop up window
  popups();
  
  function renameActivityPartners() {
    $("#items .activityPartner").each(
        function(index, activityPartner) {
          // Changing attributes of each component in order to match
          // with the array order.
          // Main div.
          $(this).attr("id", "activityPartner-" + index);
          // Remove id
          $(this).find("[name$='id']").attr("name",
              "activity.activityPartners[" + index + "].id");
          // Remove link.
          $(this).find("[id^='removeActivityPartner-']").attr("id",
              "removeActivityPartner-" + index);
          // Partner List.
          $(this).find(".partnerName").find("[id$='partner']").attr(
              "id",
              "partners_activity_activityPartners_" + index
                  + "__partner");
          $(this).find(".partnerName").find("[name$='partner']").attr(
              "name",
              "activity.activityPartners[" + index + "].partner");
          // Contact Name.
          $(this).find("[id$='contactName']").attr("id",
              "activity.activityPartners[" + index + "].contactName");
          $(this).find("[name$='contactName']").attr("name",
              "activity.activityPartners[" + index + "].contactName");
          $(this).find("[for$='contactName']").attr("for",
              "activity.activityPartners[" + index + "].contactName");
          // Contact Email.
          $(this).find("[id$='contactEmail']").attr("id",
              "activity.activityPartners[" + index + "].contactEmail");
          $(this).find("[name$='contactEmail']").attr("name",
              "activity.activityPartners[" + index + "].contactEmail");
          $(this).find("[for$='contactEmail']").attr("for",
              "activity.activityPartners[" + index + "].contactEmail");
        }
    );
  }

  $(".addActivityPartner").click(function(event) {
    event.preventDefault();
    // Cloning template.
    var $newActivityPartner = $("#activityPartner-9999").clone(true);
    $(".addLink").before($newActivityPartner);
    $(".addLink").before("<hr />");
    renameActivityPartners();
    $newActivityPartner.fadeIn("slow");
    // Activate the chosen plugin
    $newActivityPartner.find("select[name$='partner']").chosen({no_results_text: $("#noResultText").val(), search_contains:true});
  });

  $('.removeActivityPartner').click(function(event) {
    event.preventDefault();
    // Getting the id.
    var removeId = $(event.target).attr("id").split("-")[1];
    $("#activityPartner-" + removeId).hide("slow", function() {
      // removing division line.
      $(this).next("hr").remove();
      // removing div.
      $(this).remove();
      renameActivityPartners();
    });
  });
  
  //Activate the chosen plugin to the existing case studies  
  addChosen();
});

//Activate the chosen plugin to the countries inputs
function addChosen() {
  $("select[name$='partner']").each(function() {
    // Check if its not the template countries field
    if ($(this).attr("name") != '__partner') {
      $(this).chosen({no_results_text: $("#noResultText").val(),search_contains:true});
    }
  });
}
