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

}

//----------------- Mid Outcomes Events ----------------------//
function addMidOutcomeEvent(event){
  event.preventDefault(); 
  var $newElement = $("#midOutcomeRPLTemplate").clone(true).removeAttr("id");  
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
     
      var elementName = "midOutcomesRPL[" + index + "]."; 
      $(element).attr("id","midOutcomeRPL-"+index);
      $(element).find("[id^='midOutcomeRPLId']").attr("name", elementName + "id");
      $(element).find("[id^='midOutcomeRPLDescription']").attr("name", elementName + "description").attr("placeholder", "Add regional outcome #"+ (index+1) );
      console.log('setMidOutcomesIndexes -->'+index+ ' -->'+$(element).attr('id') ); 
      setContributesIndexes(index);
       
     
  });
}

//----------------- Contribute Events ----------------------//
function addContributeEvent(event){
	event.preventDefault();  
	var $addButton = $(event.target).parent(); 
	var $selectElemet = $(event.target).siblings().find("select");
	var $optionSelected = $selectElemet.find('option:selected');
	
	if ($selectElemet.find('option').length != 0){
		var  elementValue= $optionSelected.attr("value");
		var $newElementClone = $("#midOutcome-"+elementValue).clone(true);  
		console.log($optionSelected.attr("value")); 
		$addButton.before($newElementClone);
		$newElementClone.show("slow");  
		$optionSelected.remove(); 
		//setContributesIndexes(grandParentId);
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
    //setContributesIndexes(i);
  }); 
}

function setContributesIndexes(i){
  $("#midOutcome-"+i+" div.contributions").each(function(index, element){
	  console.log(i);
      var elementName = "midOutcomes["+i+"].contributesTo[" + index + "]."; 
      $(element).find("[id^='contributeId']").attr("name", elementName+"id").attr("value", outcome.id);
  });
}

