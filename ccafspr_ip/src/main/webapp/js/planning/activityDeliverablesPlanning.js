//Limits for textarea input
var lWordsElemetDesc = 300;

$(document).ready(init);

function init(){
  attachEvents();
  addChosen();
  applyWordCounter($("textarea"), lWordsElemetDesc);
}

function attachEvents(){
  // Deliverables Events
  $(".removeDeliverable, .removeNextUser").click(removeElementEvent);
  $("#addDeliverable").on("click", addDeliverableEvent);
  
  // Next users events
  $(".addActivityNextUser").on("click", addNextUserEvent);
}

function addChosen(){
  $(".activityDeliverable select").chosen({
    search_contains : true
  });
}

// Deliverables Events
function removeElementEvent(e){
  e.preventDefault();
  var $parent = $(e.target).parent();
  $parent.hide("slow", function(){
    $parent.remove();
    setDeliverablesIndexes();
  });
}

function addDeliverableEvent(e){
  e.preventDefault();
  var $newElement = $("#activityDeliverableTemplate").clone(true).removeAttr("id").addClass("activityDeliverable");
  $(e.target).parent().before($newElement);
  $newElement.find("select").chosen({
    search_contains : true
  });
  $newElement.show("slow", function(){
    $newElement.find(".addActivityNextUser").trigger("click");
    setDeliverablesIndexes();
  });
}

function addNextUserEvent(e){
  e.preventDefault();
  var $newElement = $("#activityNextUserTemplate").clone(true).removeAttr("id").addClass("activityNextUser");
  $(e.target).parent().before($newElement);
  $newElement.fadeIn("slow");
  setDeliverablesIndexes();
}

function setDeliverablesIndexes(){
  $("div.activityDeliverable").each(function(index,element){
    var elementName = "activity.deliverables[" + index + "].";
    $(element).attr("id", "activityDeliverable-" + index);
    // CSS selector div[id$=parent] Get any DIV element where the ID attribute value ends with "parent".
    $(element).find("[id$='deliverableIndex']").html(index + 1);
    
    $(element).find("[name$='].id']").attr("name", elementName + "id");
    $(element).find("[name$='title']").attr("name", elementName + "title");
    $(element).find("[name$='type']").attr("name", elementName + "type");
    $(element).find("[name$='year']").attr("name", elementName + "year");
    
    // Update index for nexts users
    $(element).find(".activityNextUser").each(function(i,nextUser){
      var elementName = "deliverables[" + index + "].nextUsers[" + i + "].";
      $(nextUser).attr("id", "activityNextUser-" + index);
      $(nextUser).find("span#index").html(i + 1);
      
      $(nextUser).find("[name$='].id']").attr("name", elementName + "id");
      $(nextUser).find("[name$='user']").attr("name", elementName + "user");
      $(nextUser).find("[name$='expectedChanges']").attr("name", elementName + "expectedChanges");
      $(nextUser).find("[name$='strategies']").attr("name", elementName + "strategies");
      
    });
  });
}
