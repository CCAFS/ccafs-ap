$(document).ready(function() {

  /* ------ Resources events ---------- */

  $(".removeResource").on("click", function(event) {
    event.preventDefault();
    var elementID = $(event.target).attr("id").split("-")[1];
    
    $("#resource-"+elementID).fadeOut(function() {
      $(this).remove();
      renameResources();
    });

    // If there is no more elements, hide the remove option
    if ($(".resource").length == 0) {
      $("#removeResourceBlock").fadeOut();
    }
  });

  $(".addResource").on("click", function(event) {
    event.preventDefault();
    var newObjective = $("#resourceTemplate").clone(true);
    $(newObjective).addClass("resource");
    $("#resourcesList").append(newObjective);
    renameResources();
    newObjective.show("slow");
  });

  $(".keywords").attr('data-placeholder', $("#keywordsDefaultText").val());
  $(".keywords").chosen();
  renameResources();
});

function renameResources() {
  $(".resource").each(
      function(index, objective) {
        // Block id
        $(this).attr("id", "resource-" + index);
        // Label
        var labelText = $("#resourceTemplate label").text().split(" ")[0];
        $(this).find("label").text(labelText + " " + (parseInt(index)+1));
        // Hidden id
        $(this).find("[name$='resourceId']").attr("name",
            "activity.resources[" + index + "].id");
        // Description
        $(this).find("[name$='name']").attr("name",
            "activity.resources[" + index + "].name");
        // Remove image
        $(this).find("[class='removeResource']").attr("id",
            "removeResource-" + index );
      });

  // If it is the first option show the remove option again 
  if ($(".resource").length == 1) {
    $("#removeResourceBlock").fadeIn();
  }
}