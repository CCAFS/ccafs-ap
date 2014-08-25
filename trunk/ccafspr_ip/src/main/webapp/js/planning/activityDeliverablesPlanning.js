//Limits for textarea input
var lWordsElemetDesc = 300;

$(document).ready(init);

function init(){
  attachEvents();
  addChosen();
  applyWordCounter($("textarea"), lWordsElemetDesc);
}

function attachEvents(){
  $(".removeDeliverable, .removeNextUser").click(removeDeliverableEvent);
}

function addChosen(){
  $("select").chosen({
    search_contains : true
  });
}

// Deliverables Events
function removeDeliverableEvent(e){
  e.preventDefault();
  var $parent = $(e.target).parent();
  $parent.hide("slow", function(){
    $parent.remove();
  });
}
