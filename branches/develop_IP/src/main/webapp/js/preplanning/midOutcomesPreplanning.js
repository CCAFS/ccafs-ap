$(document).ready(function(){
  attachEvents();
});

function attachEvents(){
  //Mid Outcomes
  $("div#addMidOutcomeBlock").click(addMidOutcomeEvent);
  $("div#removeMidOutcomeBlock").click(removeMidOutcomeEvent);	
  //Indicators
  $(".indicatorsBlock input.addButton").click(addIndicatorEvent);
  $("#removeMidOutcomeIndicator").click(removeIndicatorEvent);
}
function addMidOutcomeEvent(event){
  event.preventDefault(); 
  var $newElement = $("#midOutcomeTemplate").clone(true);
  $("div#addMidOutcomeBlock").before($newElement);
  $newElement.show("slow");
}

function removeMidOutcomeEvent(event){
  event.preventDefault(); 
  var pressedLink = event.target;
  var $ElementDiv = $(event.target).parent().parent();
  $ElementDiv.hide("slow", function() {
    $(this).remove();
    //setIndicatorsIndexes();
  });
}
	
function addIndicatorEvent(event){
  var $newIndicator = $("#midOutcomeTemplate div.indicator").clone(true);
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
  $(".midOutcomeIndicatorsBlock div.indicator").each(
    function(index, indicator){
      console.log(index);
      var elementName = "midOutcomes[0].indicators[" + index + "]."; 
      $(indicator).find("[id^='description']").attr("name", elementName + "description");
      $(indicator).find("[id^='target']").attr("name", elementName + "target");
  });
}