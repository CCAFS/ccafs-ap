//Limits for textarea input
var lWordsElemetDesc = 10;

$(document).ready(function(){
  init(); 
});

function init(){ 
	attachEvents();
	if(!$("div#outputBlocks .output").length){
		  $("div#addOutputBlock").trigger( "click" );
	}  
	applyWordCounter($("form .output > .textArea textarea"), lWordsElemetDesc);  
	setOutputsIndexes();
}

function attachEvents(){
  //Outputs
  $("div#addOutputBlock").click(addOutputEvent);
  $(".removeOutputBlock a#removeOutput").click(removeOutputEvent);	
  //Contributes
  $(".addContributeBlock input.addButton").click(addContributeEvent);
  $(".removeContribute").click(removeContributeEvent);  
}

//----------------- Outputs Events ----------------------//
function addOutputEvent(event){
  event.preventDefault();  
  var $newElement = $("#outputTemplate").clone(true).removeAttr("id");  
  $("div#outputBlocks").append($newElement); 
  $newElement.fadeIn("slow");
  setOutputsIndexes();
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
  $("div#outputBlocks .output").each(function(index, element){
      var elementName = "outputs[" + index + "]."; 
      $(element).attr("id","output-"+index);
      $(element).find("[id$='elementIndex']").html(index+1);
      $(element).find("[id^='outputId']").attr("name", elementName + "id");
      $(element).find("[id^='outputProgramID']").attr("name", elementName + "program.id");
      $(element).find("[id^='outputTypeID']").attr("name", elementName + "type.id");
      $(element).find("[id^='outputDescription']").attr("name", elementName + "description").attr("placeholder", "Add output #"+ (index+1) );
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