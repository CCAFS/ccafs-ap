$(document).ready(function(){
  attachEvents();
});

function attachEvents(){
  //Mid Outcomes
  $("div#addMidOutcomeBlock").click(addMidOutcomeEvent);
  $("div#removeMidOutcomeBlock").click(removeMidOutcomeEvent);	
  //Indicators
  $(".indicatorsBlock input.addButton").click(addIndicatorEvent);
  $(".removeMidOutcomeIndicator").click(removeIndicatorEvent);
}
function addMidOutcomeEvent(event){
  event.preventDefault(); 
  var $newElement = $("#midOutcomeTemplate").clone(true).removeAttr("id"); 
  $("div#MidOutcomeBlocks").append($newElement);
  $newElement.show("slow");
  setMidOutcomesIndexes();
}

function removeMidOutcomeEvent(event){
  event.preventDefault(); 
  var pressedLink = event.target;
  var $ElementDiv = $(event.target).parent().parent();
  $ElementDiv.hide("slow", function() {
    $(this).remove();
    setMidOutcomesIndexes();
  });
}
	
function addIndicatorEvent(event){
  var $newIndicator = $("#midOutcomeTemplate div.indicator").clone(true); 
  $(event.target).parent().before($newIndicator);
  $newIndicator.show( "slow" );
  setMidOutcomesIndexes()
}

function removeIndicatorEvent(event){
	console.log("$indicatorDiv");
  event.preventDefault();
  var pressedLink = event.target;
  var $indicatorDiv = $(event.target).parent().parent();
  var $parentDiv = $indicatorDiv.parent().parent();
  $indicatorDiv.hide("slow", function() {
    $(this).remove();
    setMidOutcomesIndexes()
  }); 
}
function setMidOutcomesIndexes(){
  $("div#MidOutcomeBlocks .midOutcome").each(function(index, element){
      //console.log(index); 
      var elementName = "midOutcomes[" + index + "]."; 
      $(element).attr("id","midOutcome-"+index);
      $(element).find("[id^='midOutcomeDescription']").attr("name", elementName + "description").attr("placeholder", "Add outcome #"+ (index+1) );  
      setIndicatorsIndexes(index);
  });
}
function setIndicatorsIndexes(i){
  $("#midOutcome-"+i+" div.indicator").each(function(index, indicator){
      console.log(index);
      var elementName = "midOutcomes["+i+"].indicators[" + index + "].";  
      $(indicator).find("[id^='indicatorDescription']").attr("name", elementName + "description").attr("placeholder", "Add indicator #"+ (index+1) ); 
      $(indicator).find("[id^='indicatorTarget']").attr("name", elementName + "target");
  });
}