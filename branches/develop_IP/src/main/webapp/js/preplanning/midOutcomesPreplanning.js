$(document).ready(function(){
  attachEvents();
});

function attachEvents(){
  //Mid Outcomes
  $("div#addMidOutcomeBlock").click(addMidOutcomeEvent);
  $(".removeMidOutcomeBlock").click(removeMidOutcomeEvent);	
  //Contributes
  $(".addContributeBlock input.addButton").click(addContributeEvent);
  $(".removeMidOutcomeIndicator").click(removeIndicatorEvent);  
  //Indicators
  $(".indicatorsBlock input.addButton").click(addIndicatorEvent);
  $(".removeMidOutcomeIndicator").click(removeIndicatorEvent);
}


// Mid Outcomes Events
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

function setMidOutcomesIndexes(){
  $("div#MidOutcomeBlocks .midOutcome").each(function(index, element){
      //console.log(index); 
      var elementName = "midOutcomes[" + index + "]."; 
      $(element).attr("id","midOutcome-"+index);
      $(element).find("[id^='midOutcomeDescription']").attr("name", elementName + "description").attr("placeholder", "Add outcome #"+ (index+1) );  
      //setIndicatorsIndexes(index);
  });
}

// Contribute Events
function addContributeEvent(event){
	event.preventDefault();  
	var $addButton = $(event.target).parent(); 
	var $selectElemet = $(event.target).siblings().find("select");
	var $optionSelected = $selectElemet.find('option:selected');
	
	if ($selectElemet.find('option').length != 0){
		var $newElementClone = $("#contributeTemplate").clone(true).removeAttr("id");
		//<input type="hidden" name="outputs[${midOutcome_index}].parents[${parent_index}].id" value="${value}" />
		var grandParentId = $addButton.parent().parent().attr("id").split("-")[1];
		$newElementClone.find("[value]").attr("value", $optionSelected.attr("value"));
		$newElementClone.find('p').html($optionSelected.html())
		$addButton.before($newElementClone);
		$newElementClone.show("slow");  
		$optionSelected.remove();
		setContributesIndexes(grandParentId);
	}
}

function setContributesIndexes(i){
  $("#midOutcome-"+i+" div.contributions").each(function(index, element){ 
      var elementName = "midOutcomes["+i+"].parents[" + index + "]."; 
      $(element).find("[name^='id']").attr("name", elementName+"id");
  });
}

// Indicators Events
function addIndicatorEvent(event){
  var grandParentId = $(event.target).parent().parent().parent().attr("id").split("-")[1];
  console.log(grandParentId);
  var $newIndicator = $("#midOutcomeTemplate div.indicator").clone(true); 
  $(event.target).parent().before($newIndicator);
  $newIndicator.show( "slow" );
  setIndicatorsIndexes(grandParentId)
}

function removeIndicatorEvent(event){
	console.log("$indicatorDiv");
  event.preventDefault();
  var pressedLink = event.target;
  var $indicatorDiv = $(event.target).parent().parent();
  var $parentDiv = $indicatorDiv.parent().parent();
  $indicatorDiv.hide("slow", function() {
    $(this).remove();
    setIndicatorsIndexes(index)
  }); 
}

function setIndicatorsIndexes(i){
  $("#midOutcome-"+i+" div.indicator").each(function(index, indicator){
      console.log(index);
      var elementName = "midOutcomes["+i+"].indicators[" + index + "].";  
      $(indicator).find("[name^='id']").attr("name", elementName + "id");
      $(indicator).find("[id^='indicatorDescription']").attr("name", elementName + "description").attr("placeholder", "Add indicator #"+ (index+1) ); 
      $(indicator).find("[id^='indicatorTarget']").attr("name", elementName + "target");
  });
}