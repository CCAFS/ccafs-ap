$(document).ready(function(){
  attachEvents();
});

function attachEvents(){
  //Outputs
  $("#addOutputBlock").click(addOutputEvent);
  $("#removeOutputBlock").click(removeOutputEvent);
  //Indicators
  $(".outputIndicatorsBlock input.addButton").click(addIndicatorEvent);
  $(".indicator a.removeOutputIndicator").click(removeIndicatorEvent);
}

function addOutputEvent(event){
  event.preventDefault();
  console.log("add output");
}

function removeOutputEvent(event){
  event.preventDefault();
  console.log("remove output");
}


function addIndicatorEvent(event){
  var $newIndicator = $("#outputTemplate div.indicator").clone(true);
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
  $(".outputIndicatorsBlock div.indicator").each(
    function(index, indicator){
      console.log(index);
      var elementName = "outputs[0].indicators[" + index + "].";

      $(indicator).find("[id^='description']").attr("name", elementName + "description");
      $(indicator).find("[id^='target']").attr("name", elementName + "target");
  });
}