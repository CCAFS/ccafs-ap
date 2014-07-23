$(document).ready(function(){
  attachEvents();
});

function attachEvents(){
  //Mid Outcomes
  $("div#addMidOutcomeBlock").click(addMidOutcomeEvent);
  $(".removeMidOutcomeBlock #removeMidOutcome").click(removeMidOutcomeEvent);	
  //Contributes
  $(".addContributeBlock input.addButton").click(addContributeEvent);
  $(".removeContribute").click(removeContributeEvent);  
  //Indicators
  $(".indicatorsBlock input.addButton").click(addIndicatorEvent);
  $(".removeMidOutcomeIndicator").click(removeIndicatorEvent);
}


//----------------- Mid Outcomes Events ----------------------//
function addMidOutcomeEvent(event){
  event.preventDefault(); 
  var $newElement = $("#midOutcomeTemplate").clone(true).removeAttr("id");  
  $("div#MidOutcomeBlocks").append($newElement);
  
  $newElement.show("slow");
  setMidOutcomesIndexes();
}

function removeMidOutcomeEvent(event){
  event.preventDefault(); 
  var $ElementDiv = $(event.target).parent().parent(); 
  $ElementDiv.hide("slow", function() { 
	$(this).remove(); 
	setMidOutcomesIndexes();
  });  
}


function setMidOutcomesIndexes(){
	//console.log($("div#MidOutcomeBlocks .midOutcome"));
  $("div#MidOutcomeBlocks .midOutcome").each(function(index, element){
     
      var elementName = "midOutcomes[" + index + "]."; 
      $(element).attr("id","midOutcome-"+index);
      // Hidden inputs
      $(element).find("[id^='midOutcomeId']").attr("name", elementName + "id");
      $(element).find("[id^='midOutcomeProgramID']").attr("name", elementName + "program.id");
      $(element).find("[id^='midOutcomeTypeID']").attr("name", elementName + "type.id");
      // Visible inputs
      $(element).find("[id^='midOutcomeDescription']").attr("name", elementName + "description").attr("placeholder", "Add outcome #"+ (index+1) );
      console.log('setMidOutcomesIndexes -->'+index+ ' -->'+$(element).attr('id') ); 
      setContributesIndexes(index);
      setIndicatorsIndexes(index);
     
  });
}

//----------------- Contribute Events ----------------------//
function addContributeEvent(event){
	event.preventDefault();  
	var $addButton = $(event.target).parent(); 
	var $selectElemet = $(event.target).siblings().find("select");
	var $optionSelected = $selectElemet.find('option:selected');
	
	if ($selectElemet.find('option').length != 0){
		var $newElementClone = $("#contributeTemplate").clone(true).removeAttr("id");
		var grandParentId = $addButton.parent().parent().attr("id").split("-")[1];
		$newElementClone.find("[value]").attr("value", $optionSelected.attr("value"));
		$newElementClone.find('p').html($optionSelected.html());
		$addButton.before($newElementClone);
		$newElementClone.show("slow");  
		$optionSelected.remove(); 
		setContributesIndexes(grandParentId);
	}
}

function removeContributeEvent(event){
  event.preventDefault(); 
  var $elementDiv = $(event.target).parent().parent();
  var $parentDiv = $elementDiv.parent().parent();
  $elementDiv.hide("slow", function() {
	var i = $parentDiv.attr("id").split("-")[1];  
	$parentDiv.find("select").append('<option value="'+$elementDiv.find("input").attr("value")+'">'+$elementDiv.find("p").html()+'</option>');  
    $(this).remove();  
    setContributesIndexes(i);
  }); 
}

function setContributesIndexes(i){
  $("#midOutcome-"+i+" div.contributions").each(function(index, element){
	  console.log(i);
      var elementName = "midOutcomes["+i+"].contributesTo[" + index + "]."; 
      $(element).find("[id^='contributeId']").attr("name", elementName+"id").attr("value", outcome.id);
  });
}

// -----------------   Indicators Events  ----------------------//
function addIndicatorEvent(event){
  var grandParentId = $(event.target).parent().parent().parent().attr("id").split("-")[1];
  //console.log(grandParentId);
  var $newIndicator = $("#midOutcomeTemplate div.indicator").clone(true).css("display","none"); 
  $(event.target).parent().before($newIndicator);
  $newIndicator.show("slow");
  setIndicatorsIndexes(grandParentId);
}

function removeIndicatorEvent(event){ 
  event.preventDefault();
  var grandParentId = $(event.target).parent().parent().parent().parent().attr("id").split("-")[1];
  var pressedLink = event.target;
  var $indicatorDiv = $(event.target).parent().parent();
  var $parentDiv = $indicatorDiv.parent().parent();
  $indicatorDiv.hide("slow", function() {
    $(this).remove();
    setIndicatorsIndexes(grandParentId);
  }); 
}

function setIndicatorsIndexes(i){
  $("#midOutcome-"+i+" div.indicator").each(function(index, indicator){
      //console.log(index);
      var elementName = "midOutcomes["+i+"].indicators[" + index + "].";  
      $(indicator).find("[name^='id']").attr("name", elementName + "id");
      $(indicator).find("[id^='indicatorDescription']").attr("name", elementName + "description").attr("placeholder", "Add indicator #"+ (index+1) ); 
      $(indicator).find("[id^='indicatorTarget']").attr("name", elementName + "target");
  });
}