$(document).ready(function(){
  attachEvents();
});

function attachEvents(){
  $(".outcomeIndicatorsBlock input.addButton").click(addIndicatorEvent);
  $(".indicator a.removeOutcomeIndicator").click(removeIndicatorEvent);
}

function addIndicatorEvent(event){
  var $newIndicator = $("#outcomeTemplate div.indicator").clone(true);
  $("#addIndicatorBlock").before($newIndicator);

  $newIndicator.show( "slow" );
  setIndicatorsIndexes();
}

function removeIndicatorEvent(event){
  event.preventDefault();
  var pressedLink = event.target;
  var $indicatorDiv = $(event.target).parent().parent();
  
  $indicatorDiv.hide("slow", function() {
    $(this).remove();
    setIndicatorsIndexes();
  });
  
}

function setIndicatorsIndexes(){
  $(".outcomeIndicatorsBlock div.indicator").each(
    function(index, indicator){
      var elementName = "outcomes[0].indicators[" + index + "].";
      console.log(index + ": " + elementName);

      $(indicator).find("[id^='description']").attr("name", elementName + "description");
      $(indicator).find("[id^='target']").attr("name", elementName + "target");
  });
}