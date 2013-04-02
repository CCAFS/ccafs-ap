$(document).ready(function() {

  $(".removeObjective").on("click", function(event) {
    event.preventDefault();
    $(event.target).parent().parent().hide("slow", function() {
      $(event.target).parent().parent().remove();
      renameObjectives();
    });
  });

  $(".addObjective").on("click", function(event) {
    event.preventDefault();
    var newObjective = $("#objectiveTemplate").clone(true);
    $("#objectivesBlock").append(newObjective);
    $(newObjective).attr("class", "objective");
    renameObjectives();
    newObjective.show("slow");
  });
  
  renameObjectives();

});

function renameObjectives() {
  $(".objective").each( function(index, objective) {
    // Block id
    $(this).attr("id", "objective-" + index);
    // remove link
    $(this).find(".removeObjective").attr("id",
        "removeObjective-" + index);
    // Label
    // TODO - Take the word objective from a field filled with i18n value
    $(this).find("label").text("Objective " + (index+1));
    // description
    $(this).find("[name$='id']").attr("name",
        "activity.objectives[" + index + "].id");
    $(this).find("[name$='description']").attr("id",
        "activity.objectives[" + index + "].description");
    $(this).find("[name$='description']").attr("name",
        "activity.objectives[" + index + "].description");
  });
}