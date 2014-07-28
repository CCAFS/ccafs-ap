$(document).ready(function(){
  attachEvents(); 
});

function attachEvents(){
  //Outputs
  $("a#addNewOutput").click(addOutputEvent);
  $("a#addExistingOutput").click(addExistingOutputEvent);
  $(".removeOutputBlock a#removeOutput").click(removeOutputEvent);
  
  //Select flagship
  $("select#flagships").change(updateMidOutcomesList);
  //Select Mid Outcome
  $("select#midOutcomes").change(updateOutputsList);
  //Select Outputs
  //Contributes
  $(".addContributeBlock input.addButton").click(addContributeEvent);
  $(".removeContribute").click(removeContributeEvent);  
}


function updateMidOutcomesList(event){
    $target =$(event.target);
    $parent =$target.parent().parent().parent().parent();
    var programID =  $target.find('option:selected').attr("value");
    var elementTypeId = 3;
    $.getJSON("../json/ipElements.do?programID="+programID+"&elementTypeId="+elementTypeId, function(data) {
      $parent.find("select#midOutcomes option").remove(); 
      $.each(data.IPElementsList, function(){ 
    	  $parent.find("select#midOutcomes").append('<option value="'+ this.id +'">'+ this.description +'</option>');
      });
    }).fail(function() {
	    console.log( "error" );
    }).done(function() {
	 $parent.find("select#midOutcomes").attr("disabled",false);
	 $parent.find("select#midOutcomes").trigger("change");
    });
}

function updateOutputsList(event){
    $target =$(event.target);
    $parent =$target.parent().parent().parent().parent(); 
    var programID =  $parent.find("select#flagships").find('option:selected').attr("value");
    var elementTypeId = 4;
    var midOutcomeId = $target.find('option:selected').attr("value");
    $.getJSON("../json/ipElements.do?programID="+programID+"&elementTypeId="+elementTypeId, function(data) {
      $parent.find("select#outputs option").remove(); 
      $.each(data.IPElementsList, function(){  
    	  if(objectsListContains(this.contributesTo,midOutcomeId)){
    		  $parent.find("select#outputs").append('<option value="'+ this.id +'">'+ this.description +'</option>');
    	  }
    	  
      });
    }).fail(function() {
	    console.log( "error" );
    }).done(function() {
	 $parent.find("select#outputs").attr("disabled",false);
    });
}

function objectsListContains(objList,target){
	var hasTarget = false;
	$.each(objList, function(){ 
		if(this.id == target) hasTarget = true;
	});
	return hasTarget;
}



//----------------- Outputs Events ----------------------//
function addOutputEvent(event){
  event.preventDefault();  
  var $newElement = $("#outputTemplate").clone(true).removeAttr("id");  
  $("div#outputBlocks").append($newElement); 
  $newElement.show("slow");
  setOutputsIndexes();
}
function addExistingOutputEvent(){
	event.preventDefault();  
	var $newElement = $("#newOutputTemplate").clone(true).removeAttr("id");
	$("div#outputBlocks").append($newElement); 
	$newElement.find("select#flagships").trigger("change");
	$newElement.show("slow");
	//setOutputsIndexes();
}

function removeOutputEvent(event){
  event.preventDefault(); 
  var $ElementDiv = $(event.target).parent().parent(); 
  $ElementDiv.hide("slow", function() { 
	$(this).remove(); 
	setOutputsIndexes();
  });  
}

function setOutputsIndexes(){
	//console.log($("div#OutputBlocks .output"));
  $("div#outputBlocks .output").each(function(index, element){
     
      var elementName = "outputs[" + index + "]."; 
      $(element).attr("id","output-"+index);
      $(element).find("[id^='outputId']").attr("name", elementName + "id");
      $(element).find("[id^='outputDescription']").attr("name", elementName + "description").attr("placeholder", "Add output #"+ (index+1) );
      //console.log('setMidOutcomesIndexes -->'+index+ ' -->'+$(element).attr('id') );  
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
  $("#output-"+i+" div.contributions").each(function(index, element){ 
      var elementName = "outputs["+i+"].contributesTo[" + index + "]."; 
      $(element).find("[id^='contributeId']").attr("name", elementName+"id"); 
  });
}