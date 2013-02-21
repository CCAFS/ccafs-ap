$(document).ready(function() {  
  
  function renamePublications() {
    // getting the text of the index element.
    var itemText = $("#template").find(".itemIndex").text();
    $("#items .publication").each(function(index, activityPartner) {
          // Changing attributes of each component in order to match
          // with the array order.
          // Main div.
          $(this).attr("id", "publication-" + index);
          //Item index
          $(this).find(".itemIndex").text(itemText + " " + (index+1));
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
    $(".addLink").before($newPublication);
    $(".addLink").before("<hr />");
    renamePublications();
    $newPublication.fadeIn("slow");
  });

  $('.removePublication').click(function(event) {
    event.preventDefault();
    // Getting the id.
    var removeId = $(event.target).attr("id").split("-")[1];
    $("#publication-" + removeId).hide("slow", function() {
      // removing division line.
      $(this).next("hr").remove();
      // removing div.
      $(this).remove();
      renamePublications();
    });
  });  
});
