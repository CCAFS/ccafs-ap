$(document).ready(function() {  
  
  function renamePublications() {
    // getting the number of expected deliverables.
    var expectedDeliverablesCount = $("#publicationGroup .publication").length;
    $("#items .publication").each(function(index, activityPartner) {
          // Changing attributes of each component in order to match
          // with the array order.
          // Main div.
          $(this).attr("id", "publication-" + index);
          // Remove link.
          $(this).find("[id^='removePublication-']").attr("id","removePublication-" + index);
          // Publication id.
          $(this).find("[name$='id']").attr("name","publications[" + index + "].id");
          // Publication Type.
          $(this).find("[id$='type']").attr("id", "publications_publications_" + index + "__type");
          $(this).find("[name$='type']").attr("name", "publications[" + index + "].type");
          // Publication Identifier.
          $(this).find("[id$='identifier']").attr("id", "publications[" + index + "].identifier");
          $(this).find("[name$='identifier']").attr("name", "publications[" + index + "].identifier");
          $(this).find("[for$='identifier']").attr("for", "publications[" + index + "].identifier");
          // Publication Citation.
          $(this).find("[id$='citation']").attr("id", "publications[" + index + "].citation");
          $(this).find("[name$='citation']").attr("name", "publications[" + index + "].citation");
          $(this).find("[for$='citation']").attr("for", "publications[" + index + "].citation");
        }
    );
  }

  $(".addPublication").click(function(event) {
    event.preventDefault();
    // Cloning template.
    var $newPublication = $("#publication-9999").clone(true);
    $("#publicationGroup").append($newPublication);
    $("#publicationGroup").append("<hr />");
    renamePublications();
    $newPublication.fadeIn("slow");
  });

  $('.removePublication').click(function(event) {
    event.preventDefault();
    // Getting the id.
    var removeId = $(event.target).attr("id").split("-")[1];
    $("#publication-" + removeId).fadeOut("slow");
    // removing division line.
    $("#publication-" + removeId).next("hr").remove();
    // removing div.
    $("#publication-" + removeId).remove();
    renamePublications();
  });  
});
