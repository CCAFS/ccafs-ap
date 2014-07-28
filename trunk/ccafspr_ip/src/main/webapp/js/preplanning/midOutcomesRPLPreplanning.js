$(document).ready(function(){
  attachEvents();
  if(!$("div#MidOutcomeBlocks .midOutcome").length){
      $("div#addMidOutcomeBlock").trigger( "click" );
  } 
  //setMidOutcomesIndexes();
});

function attachEvents(){
  //Mid Outcomes
  $("div#addMidOutcomeBlock").click(addMidOutcomeEvent);
  $(".removeMidOutcomeBlock #removeMidOutcome").click(removeMidOutcomeEvent);	
  
  //Select flagship
  $("select[id$='flagships']").change(updateMidOutcomes);
  
  //Contributes
  $(".addContributeBlock input.addButton").click(addContributeEvent);
  $(".removeContribute").click(removeContributeEvent); 
  //Regional Indicators
  $(".midOutcomeIndicator").click(indicatorVerify); 
}

//----------------- Regional Indicators ----------------------//

function indicatorVerify(event){ 
	var $parent = $(event.target).parent().parent().parent();
	var checkedIndicators = $("input[name^='"+event.currentTarget.name+"']:checked").length; 
	var $textArea = $parent.find(".textArea"); 
	console.log(event.currentTarget.name);
	if(checkedIndicators == 0){
		$textArea.fadeIn("slow");
	}else{
		$textArea.fadeOut("slow");
	}
}

//----------------- Mid Outcomes Events ----------------------//
function addMidOutcomeEvent(event){
  event.preventDefault(); 
  var $newElement = $("#midOutcomeRPLTemplate").clone(true).removeAttr("id");  
  $("div#MidOutcomeBlocks").append($newElement); 
  $newElement.find("select[id$='flagships']").trigger("change");
  $newElement.fadeIn("slow");
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
  $("div#MidOutcomeBlocks .midOutcome").each(function(index, element){ 
      var elementName = "midOutcomes[" + index + "]."; 
      $(element).attr("id","midOutcome-"+index);
      $(element).find("[id$='id']").attr("name", elementName + "id");
      $(element).find("[id$='ProgramId']").attr("name", elementName + "program.id");
      $(element).find("[id$='TypeId']").attr("name", elementName + "type.id");
      $(element).find("[id$='description']").attr("name", elementName + "description").attr("placeholder", "Add regional outcome #"+ (index+1) );
      $(element).find("select[id$='flagships']").trigger( "change" );
      setContributesIndexes(index); 
  });
}

function updateMidOutcomes(event){
    $target =$(event.target);
    $parent =$target.parent().parent().parent();
    console.log($target.find('option:selected').attr("value"));
    var programID =  $target.find('option:selected').attr("value");
    var elementTypeId = 3;
    $.getJSON("../json/ipElements.do?programID="+programID+"&elementTypeId="+elementTypeId, function(data) {
      $parent.find("select[id$='midOutcomesFPL'] option").remove(); 
      $.each(data.IPElementsList, function(){ 
	  $parent.find("select[id$='midOutcomesFPL']").append('<option value="'+ this.id +'">'+ this.description +'</option>');
      });
    }).fail(function() {
	    console.log( "error" );
    }).done(function() {
	 
    });
}
//----------------- Contribute Events ----------------------//
function addContributeEvent(event){
	event.preventDefault();  
	var $addButton = $(event.target).parent(); 
	var $selectElemet = $(event.target).siblings().find("select");
	if ($selectElemet.find('option').length != 0){
		var $optionSelected = $selectElemet.find('option:selected');
		var elementId= $optionSelected.attr("value");
		var programID= 1;
		var grandParentId = $addButton.parent().parent().attr("id").split("-")[1];
		var $newElementClone = $("#contributeTemplate").clone(true).removeAttr("id");  
		var elementName = "midOutcomes["+grandParentId+"].translatedOf["+elementId+"]."; 
		$newElementClone.find("[id$='contributeId']").attr("value", elementId);
		$.getJSON("../ipIndicators.do?programID="+programID+"&elementID="+elementId, function(data) { 
		  $.each(data.IPElementsList, function(index,element){ 
			$newElementClone.find("div.checkboxGroup").append('<input  id="'+elementName+'indicators-'+index+'" class="midOutcomeIndicator" name="'+elementName+'indicators" type="checkbox" value="'+ this.id +'">');
			$newElementClone.find("div.checkboxGroup").append('<label for="'+elementName+'indicators-'+index+'" class="checkboxLabel">'+ this.description +'</label>');
		  });
		}).done(function() { 
			$newElementClone.find("div.checkboxGroup").append('<input type="hidden" id="__multiselect_'+elementName+'indicators" name="" value="">');
			$addButton.before($newElementClone);
			$newElementClone.find(".midOutcomeIndicator").click(indicatorVerify); 
			$newElementClone.show("slow");  
			$optionSelected.remove(); 
			setContributesIndexes(grandParentId);
	    });
		
	}
}

function removeContributeEvent(event){
  event.preventDefault(); 
  var $elementDiv = $(event.target).parent().parent();
  var $parentDiv = $elementDiv.parent().parent();
  $elementDiv.hide("slow", function() {
	var i = $parentDiv.attr("id").split("-")[1];  
	$parentDiv.find("select.contributes").append('<option value="'+$elementDiv.find("input").attr("value")+'">'+$elementDiv.find("p").html()+'</option>');  
    $(this).remove();  
    setContributesIndexes(i);
  }); 
}

function setContributesIndexes(i){
  $("#midOutcome-"+i+" div.contributions").each(function(index, element){
      var elementName = "midOutcomes["+i+"].translateOf[" + index + "]."; 
      $(element).find("[id$='contributeId']").attr("name", elementName+"id");
      $(element).find("[id$='justification']").attr("name", elementName+"justification");
      $(element).find("[id^='__multiselect']").attr("name", "__multiselect_"+elementName+"indicators");
      //set Indicator indexes
      $(element).find(".midOutcomeIndicator").each(function(c, indicator){ 
    	  var inputId = $(indicator).attr('id');
    	  //set new id for label input
    	  $(element).find("label[for='"+inputId+"']").attr("for",elementName+"indicators-"+index+c);
    	  //set new index name for input indicator
    	  $(indicator).attr("name", elementName+"indicators").attr("id",elementName+"indicators-"+index+c);
      });
  });
}
