$(document).ready(function() {
	  
	  $(".removeOutcomeIndicator").on("click", function(event) { 
	    event.preventDefault();
	    $(event.target).parent().parent().hide("fade", function() {
	      $(event.target).parent().parent().remove();
	      renameIndicators();
	    });
	  });

	  $(".addButton.Indicator").on("click", function(event) {
	    event.preventDefault();
	    console.log("add indicator");
	    var newObjective = $("#indicatorTemplate").clone(true);
	    $("#addDeliverableBlock").before(newObjective);
	    $(newObjective).attr("class", "indicator");
	    renameIndicators();
	    newObjective.show("slow");
	    $(newObjective).find("[id$='description']").focus();
	  });
	  
	  renameIndicators();

	});

	function renameIndicators() {
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