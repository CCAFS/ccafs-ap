$(document).ready(function(){
  var programID =  1;
  var elementTypeId = 3;
  attachEvents();
  
  $.getJSON("../json/ipElements.do?programID="+programID+"&elementTypeId="+elementTypeId, function(data) {
	    $("#midOutcomes_.contributes option").remove(); 
	    $.each(data.IPElementsList, function(){ 
	        $("#midOutcomes_.contributes").append('<option value="'+ this.id +'">'+ this.description +'</option>');
	    });
	});
});

function attachEvents(){
  //Mid Outcomes
  $("div#addMidOutcomeBlock").click(addMidOutcomeEvent);
  $(".removeMidOutcomeBlock #removeMidOutcome").click(removeMidOutcomeEvent);	
  //Contributes
  $(".addContributeBlock input.addButton").click(addContributeEvent);
  $(".removeContribute").click(removeContributeEvent); 
  //Regional Indicators
  $(".midOutcomeIndicator").click(indicatorVerify); 
}

//----------------- Regional Indicators ----------------------//

function indicatorVerify(event){
	console.log(event.currentTarget.name);
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
      //console.log('setMidOutcomesIndexes -->'+index+ ' -->'+$(element).attr('id') ); 
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
		var grandParentId = $addButton.parent().parent().attr("id").split("-")[1];
		var $newElementClone = $("#midOutcome-"+elementValue).clone(true);  
		console.log(grandParentId); 
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
  $("#midOutcomeRPL-"+i+" div.contributions").each(function(index, element){
	  //console.log(i);
      var elementName = "midOutcomesRPL["+i+"]."; 
      $(element).find("[id^='contributeId']").attr("name", elementName+"contributesTo[" + index + "].id");
      $(element).find("[id^='justification']").attr("name", elementName+"justification");
      $(element).find("[id^='__multiselect']").attr("name", "__multiselect_"+elementName+"indicators");
      
      
      //set Indicator indexes
      $(element).find(".midOutcomeIndicator").each(function(c, indicator){
    	  console.log($(indicator).attr("id"));
    	  //set new id for label input
    	  $(element).find("label[for='"+$(indicator).attr('id')+"']").attr("for",elementName+"indicators-"+index+c);
    	  //set new index name for input indicator
    	  $(indicator).attr("name", elementName+"indicators").attr("id",elementName+"indicators-"+index+c);  
    	  
      });
  });
}

