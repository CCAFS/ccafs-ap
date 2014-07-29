$(document).ready(function(){
	//init();
	attachEvents(); 
});

function init(){
	$(".idosIndicators").hide();
}

function attachEvents(){
  $(".outcomeIndicatorsBlock input.addButton").click(addIndicatorEvent);
  $(".indicator a.removeOutcomeIndicator").click(removeIndicatorEvent);
  
  $(".idosCheckbox").change(viewIDOsIndicators);
}

function viewIDOsIndicators(event){
	$target = $(event.target);
	$target.parent().find(".idosIndicators");
	console.log($target);
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