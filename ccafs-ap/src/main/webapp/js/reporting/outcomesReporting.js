$(document).ready(function() {  
  
  function renameOutcomes() {
    //getting the text of the index element.
    var itemText = $("#template").find(".itemIndex").text();
    $("#items .outcome").each(function(index, outcome) {
          // Changing attributes of each component in order to match
          // with the array order.
          // Main div.
          $(this).attr("id", "outcome-" + index);
          //Item index
          $(this).find(".itemIndex").text(itemText + " " + (index+1));
          // Remove link.
          $(this).find("[id^='removeOutcome-']").attr("id","removeOutcome-" + index);
          // Identifier
          $(this).find("[name$='id']").attr("name", "outcomes[" + index + "].id");
          // Title.
          $(this).find("[id$='title']").attr("id", "outcomes[" + index + "].title");
          $(this).find("[id$='title']").attr("name", "outcomes[" + index + "].title");
          // Outcome.
          $(this).find("[id$='outcome']").attr("id", "outcomes[" + index + "].outcome");
          $(this).find("[name$='outcome']").attr("name", "outcomes[" + index + "].outcome");
          $(this).find("[for$='outcome']").attr("for", "outcomes[" + index + "].outcome");
          // Outputs.
          $(this).find("[id$='outputs']").attr("id", "outcomes[" + index + "].outputs");
          $(this).find("[name$='outputs']").attr("name", "outcomes[" + index + "].outputs");
          $(this).find("[for$='outputs']").attr("for", "outcomes[" + index + "].outputs");
          // Partners.
          $(this).find("[id$='partners']").attr("id", "outcomes[" + index + "].partners");
          $(this).find("[name$='partners']").attr("name", "outcomes[" + index + "].partners");
          $(this).find("[for$='partners']").attr("for", "outcomes[" + index + "].partners");
          // Activities.
          $(this).find("[id$='activities']").attr("id", "outcomes[" + index + "].activities");
          $(this).find("[name$='activities']").attr("name", "outcomes[" + index + "].activities");
          $(this).find("[for$='activities']").attr("for", "outcomes[" + index + "].activities");
          // Non research partners.
          $(this).find("[id$='nonResearchPartners']").attr("id", "outcomes[" + index + "].nonResearchPartners");
          $(this).find("[name$='nonResearchPartners']").attr("name", "outcomes[" + index + "].nonResearchPartners");
          $(this).find("[for$='nonResearchPartners']").attr("for", "outcomes[" + index + "].nonResearchPartners");
          // Output User.
          $(this).find("[id$='outputUser']").attr("id", "outcomes[" + index + "].outputUser");
          $(this).find("[name$='outputUser']").attr("name", "outcomes[" + index + "].outputUser");
          $(this).find("[for$='outputUser']").attr("for", "outcomes[" + index + "].outputUser");
          // Outputs.
          $(this).find("[id$='outputs']").attr("id", "outcomes[" + index + "].outputs");
          $(this).find("[name$='outputs']").attr("name", "outcomes[" + index + "].outputs");
          $(this).find("[for$='outputs']").attr("for", "outcomes[" + index + "].outputs");
          // How Used.
          $(this).find("[id$='howUsed']").attr("id", "outcomes[" + index + "].howUsed");
          $(this).find("[name$='howUsed']").attr("name", "outcomes[" + index + "].howUsed");
          $(this).find("[for$='howUsed']").attr("for", "outcomes[" + index + "].howUsed");
          // Evidence.
          $(this).find("[id$='evidence']").attr("id", "outcomes[" + index + "].evidence");
          $(this).find("[name$='evidence']").attr("name", "outcomes[" + index + "].evidence");
          $(this).find("[for$='evidence']").attr("for", "outcomes[" + index + "].evidence");
        }
    );
  }

  $(".addOutcome").click(function(event) {
    event.preventDefault();
    // Cloning template.
    var $newOutcome = $("#outcome-9999").clone(true);
    $(".addLink").before($newOutcome);
    $(".addLink").before("<hr />");
    renameOutcomes();
    $newOutcome.fadeIn("slow");
  });

  $('.removeOutcome').click(function(event) {
    event.preventDefault();
    // Getting the id.
    var removeId = $(event.target).attr("id").split("-")[1];
    $("#outcome-" + removeId).hide("slow", function() {
      // removing division line.
      $(this).next("hr").remove();
      // removing div.
      $(this).remove();
      renameOutcomes();
    });
  });
  
  applyWordCounters();
  
});

function applyWordCounters(){
  
  // Title
  $("[id$='title']").each(function(index, textArea){
    applyWordCounter($(textArea), 15);
  });

  // Outcome statement
  $("[id$='outcome']").each(function(index, textArea){
    applyWordCounter($(textArea), 80);
  });

  // Research outputs
  $("[id$='outputs']").each(function(index, textArea){
    applyWordCounter($(textArea), 150);
  });

  // Research partners
  $("[id$='partners']").each(function(index, textArea){
    applyWordCounter($(textArea), 150);
  });

  // Activities
  $("[id$='activities']").each(function(index, textArea){
    applyWordCounter($(textArea), 150);
  });

  // Non research partners
  $("[id$='nonResearchPartners']").each(function(index, textArea){
    applyWordCounter($(textArea), 150);
  });

  // User of the output
  $("[id$='outputUser']").each(function(index, textArea){
    applyWordCounter($(textArea), 50);
  });

  // How the output was used
  $("[id$='howUsed']").each(function(index, textArea){
    applyWordCounter($(textArea), 50);
  });

  // Evidence of the outcome
  $("[id$='evidence']").each(function(index, textArea){
    applyWordCounter($(textArea), 50);
  });
}
