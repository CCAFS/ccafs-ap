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
    $("#addDeliverableBlock").before(newObjective);
    $(newObjective).attr("class", "objective");
    renameObjectives();
    newObjective.show("slow");
    $(newObjective).find("[id$='description']").focus();
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
    // Take the word 'Objective' from the template to 
    var label = $("#objectiveTemplate label").clone();
    // Insert the index
    $(label).find("span").before((index+1) + " ");
    
    $(this).find("label").html($(label).html());
    // description
    $(this).find("[name$='id']").attr("name",
        "activity.objectives[" + index + "].id");
    $(this).find("[name$='description']").attr("id",
        "activity.objectives[" + index + "].description");
    $(this).find("[name$='description']").attr("name",
        "activity.objectives[" + index + "].description");
    // Add the word counter
    if( ! $(this).find("[name$='description']").hasClass("wordCounter")){
      applyWordCounter($(this).find("[name$='description']"), 300);
      $(this).find("[name$='description']").addClass("wordCounter");
    }
  });
}